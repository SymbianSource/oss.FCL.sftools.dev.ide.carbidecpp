/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
* All rights reserved.
* This component and the accompanying materials are made available
* under the terms of the License "Eclipse Public License v1.0"
* which accompanies this distribution, and is available
* at the URL "http://www.eclipse.org/legal/epl-v10.html".
*
* Initial Contributors:
* Nokia Corporation - initial contribution.
*
* Contributors:
*
* Description: 
*
*/

package com.nokia.sdt.symbian.tests;

import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.ProjectUtils;
import com.nokia.sdt.emf.dm.IDesignerData;
import com.nokia.sdt.testsupport.FileHelpers;
import com.nokia.sdt.testsupport.TestHelpers;
import com.nokia.sdt.utils.*;

import org.eclipse.core.resources.*;
import org.eclipse.emf.ecore.resource.ResourceSet;

import java.io.*;
import java.util.Arrays;

import junit.framework.TestCase;

/**
 * 
 *
 */
public class WorkspaceFileTrackerTest extends TestCase {

    IDesignerData designerData;
    ResourceSet resourceSet;
    File outputFile;
    private static IProject project;
    private static File projectDir;
    private WorkspaceFileTracker filehandler;
    
    static final String PROJECT_NAME = "srcgen-workspace-file-tests";
    static final String BASE_DIR = "/data/srcgen/";
    static final String FILE_NAME = "model.nxd";

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        TestHelpers.setPlugin(TestsPlugin.getDefault());
        
        if (project == null) {
            project = ProjectUtils.createAndOpenProject(PROJECT_NAME);
            projectDir = new File(project.getLocation().toOSString());
            
            FileUtils.copyTreeNoParent(
                    FileHelpers.projectRelativeFile(BASE_DIR + "project"),
                    projectDir, null
                    );
            project.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
        }
        
        filehandler = new WorkspaceFileTracker();
    }
    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        /*
        try {
            ProjectUtils.closeAndDeleteProject(PROJECT_NAME);
        } catch (CoreException e) {
            Thread.sleep(1000);
            try {
                ProjectUtils.closeAndDeleteProject(PROJECT_NAME);
            } catch (CoreException e2) {
                
            }
        }*/
    }
    
    public void testLoad() throws Exception {
        File mainFile = new File(projectDir, "main.cpp"); 
        char[] text = filehandler.loadFileText(mainFile);
        assertEquals("test file", new String(text));
    }

    public void testLoad2() throws Exception {
        // test that this is using Eclipse by changing contents
        // through Resource APIs
        File mainFile = new File(projectDir, "main.cpp"); 
        char[] text = filehandler.loadFileText(mainFile);
        assertEquals("test file", new String(text));

        // modify via resources
        IFile file = project.getFile("main.cpp");
        assertNotNull(file);
        assertTrue(file.exists());
        InputStream is = new ByteArrayInputStream("\nnew line".getBytes());
        file.appendContents(is, false, false, null);
        
        // load again
        char[] text2 = filehandler.loadFileText(mainFile);
        assertEquals("test file\nnew line", new String(text2));
    }

    public void testSave() throws Exception {
        File mainFile = new File(projectDir, "main.rss");
        String text =  "generated file";
        filehandler.saveFileText(mainFile, "UTF-8", text.toCharArray());

        // access via resources
        IFile file = project.getFile("main.rss");
        assertNotNull(file);
        assertTrue(file.exists());
        InputStream is = file.getContents();
        assertNotNull(is);
        InputStreamReader isr = new InputStreamReader(is);
        char[] cbuf = new char[32];
        int len = isr.read(cbuf);
        is.close();
        assertEquals(text.length(), len);
        assertEquals(text, new String(cbuf, 0, len));
    }

    public void testSaveEncoded() throws Exception {
        File mainFile = new File(projectDir, "main2.rss");
        String text =  "Hi\u00C7";
        filehandler.saveFileText(mainFile, "UTF-8", text.toCharArray());

        // access via resources
        IFile file = project.getFile("main2.rss");
        assertNotNull(file);
        assertTrue(file.exists());
        InputStream is = file.getContents();
        assertNotNull(is);
        byte[] bbuf = new byte[4];
        int len = is.read(bbuf, 0, 4);
        assertEquals(4, len);
        assertEquals(-1, is.read(bbuf));
        is.close();
        assertTrue(Arrays.equals(new byte[] { 'H', 'i', (byte) 0xc3, (byte) 0x87 }, bbuf));
        
        // rewrite
        filehandler.saveFileText(mainFile, "CP1252", text.toCharArray());
        
        file = project.getFile("main2.rss");
        assertNotNull(file);
        assertTrue(file.exists());
        is = file.getContents();
        assertNotNull(is);
        bbuf = new byte[3];
        len = is.read(bbuf, 0, 3);
        assertEquals(3, len);
        assertEquals(-1, is.read(bbuf));
        is.close();
        assertTrue(Arrays.equals(new byte[] { 'H', 'i', (byte) 0xc7 }, bbuf));
    }

}
