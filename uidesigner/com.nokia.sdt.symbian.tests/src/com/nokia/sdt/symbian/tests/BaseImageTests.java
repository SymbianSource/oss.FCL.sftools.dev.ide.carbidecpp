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
import com.nokia.sdt.component.symbian.ComponentProvider;
import com.nokia.sdt.sourcegen.SourceGenUtils;
import com.nokia.sdt.testsupport.FileHelpers;
import com.nokia.sdt.testsupport.TestDataModelHelpers;
import com.nokia.sdt.testsupport.TestHelpers;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import java.io.File;
import java.io.FileFilter;

import junit.framework.TestCase;

/**
 * Test the image information in com.nokia.sdt.symbian.images.
 * 
 * 
 * 
 *
 */
public abstract class BaseImageTests extends TestCase {
    protected static IProject project;
    protected static File projectDir;
    protected static ComponentProvider provider;
    
    static final String PROJECT_NAME = "image-tests";
    static final String BASE_DIR = "/data/images/";
    static final String FILE_NAME = "project/fixture.nxd";

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        TestHelpers.setPlugin(TestsPlugin.getDefault());

        SourceGenUtils.setSourceFileHeaderTemplate(null);
        
        if (project == null) {
            project = ProjectUtils.createAndOpenProject(PROJECT_NAME);
            projectDir = new File(project.getLocation().toOSString());
            
            FileUtils.copyTreeNoParent(
                    FileHelpers.projectRelativeFile(BASE_DIR + "project"),
                    projectDir, new FileFilter() {

                        public boolean accept(File pathname) {
                            if (pathname.getName().startsWith("."))
                                return false;
                            else
                                return true;
                        }
                        
                    }
                    );
            project.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
            
            provider = TestDataModelHelpers.findOrCreateProviderForUserComponents(
                    BASE_DIR + "components");

        }

        
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
    	super.tearDown();
    }
}
