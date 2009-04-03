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

package com.nokia.sdt.uimodel.tests;

import com.nokia.sdt.symbian.workspace.impl.ProjectContextProvider;
import com.nokia.sdt.testsupport.FileHelpers;
import com.nokia.sdt.testsupport.TestHelpers;
import com.nokia.sdt.utils.*;
import com.nokia.sdt.workspace.*;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;

import java.io.File;

import junit.framework.TestCase;

/**
 * 
 *
 */
public class WorkspaceMessageHandlerTest extends TestCase {
    private IProject project;
    private File projectDir;
    private IResource dataModelFile;
    private ProjectMessageHandler projectMessageHandler;
    private WorkspaceMessageHandler workspaceMessageHandler;
 
    static final String PROJECT_NAME = "message-tests";
    static final String BASE_DIR = "data/messages/";
    static final String FILE_NAME = "model.nxd";
    
    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        
        TestHelpers.setPlugin(TestsPlugin.getDefault());
        
        project = ProjectUtils.createAndOpenProject(PROJECT_NAME);
        ProjectContextProvider.beginProjectCreation(project);
        WorkspaceContext wctx = WorkspaceContext.getContext();
        IProjectContext pctx = wctx.getContextForProject(project);
        assertNotNull(pctx);

        
        projectDir = new File(project.getLocation().toOSString());
        
        FileUtils.copyTreeNoParent(
                FileHelpers.projectRelativeFile(BASE_DIR + "project"),
                projectDir, null
                );
        project.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
//        SymbianProjectUtils.addUIDesignerProjectNatureToProject(project);
        
        project.getWorkspace().getRoot().deleteMarkers(null, true, IResource.DEPTH_INFINITE);
        
        dataModelFile = project.findMember("dummy.nxd");
        
        MessageReporting.reset();
        
        projectMessageHandler = new ProjectMessageHandler(project);
        MessageReporting.addListener(projectMessageHandler);
        workspaceMessageHandler = new WorkspaceMessageHandler();
        MessageReporting.addListener(workspaceMessageHandler);
        
        
        workspaceMessageHandler.reset();
        projectMessageHandler.reset();
    }

	/**
	 * Waste time so Eclipse will run resource listeners.
	 *
	 */
	protected void sleep() throws Exception {
		Job job = new WorkspaceJob("foo") {

			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				monitor.beginTask("foo", 2);
				monitor.worked(1);
				monitor.worked(1);
				monitor.done();
				return Status.OK_STATUS;
			}
			
		};
		job.setRule(ResourcesPlugin.getWorkspace().getRoot());
		job.schedule();
		job.join();
	}

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        try {
            workspaceMessageHandler.reset();
            projectMessageHandler.reset();
            sleep();
            MessageReporting.removeListener(projectMessageHandler);
            MessageReporting.removeListener(workspaceMessageHandler);
            ProjectUtils.closeAndDeleteProject(PROJECT_NAME);
        } catch (CoreException e) {
            
        }
    }

    public void testBasic() throws Exception {
        IMarker[] oldMarkers;
        

        oldMarkers = project.getWorkspace().getRoot().findMarkers(
                null, true, IResource.DEPTH_INFINITE);

        assertEquals(0, oldMarkers.length);
        
        IMessage msg = new Message(IStatus.ERROR,
                new MessageLocation(dataModelFile.getLocation(), 0, 0),
                "MyMessageKey",
                "This is a message");
        projectMessageHandler.emitMessage(msg);
        
        IMarker[] newMarkers;
        newMarkers = project.getWorkspace().getRoot().findMarkers(
                null, true, IResource.DEPTH_INFINITE);
        
        assertEquals(oldMarkers.length+1, newMarkers.length);

        IMarker last = newMarkers[newMarkers.length-1];
        assertEquals(WorkspaceMessageHandler.messageMarkerType, last.getType());
       

        assertTrue(last.getAttribute(IMarker.MESSAGE, "").endsWith("This is a message"));

    }
    
    public void testProjectFile() throws Exception {
        IMarker[] oldMarkers;
        
        oldMarkers = project.getWorkspace().getRoot().findMarkers(
                null, true, IResource.DEPTH_INFINITE);

        // this also checks the result of the previous run's tearDown()
        assertEquals(0, oldMarkers.length);
        
        File mainFile = new File(projectDir, "main.cpp"); 

        MessageLocation srcref = new MessageLocation(new Path(mainFile.getAbsolutePath()), 23, 5);
        
        assertEquals(mainFile.getAbsolutePath(), srcref.getLocation().toOSString());
        assertEquals(project.getName() + "/main.cpp", srcref.getPath().makeRelative().toString());
        
        IMessage msg = new Message(IStatus.ERROR,
                srcref,
                "MyMessageKey",
                "This is a message");
        MessageReporting.emitMessage(msg);
        //projectMessageHandler.emitMessage(msg);
        
        IMarker[] newMarkers;
        newMarkers = project.getWorkspace().getRoot().findMarkers(
                null, true, IResource.DEPTH_INFINITE);
        
        assertEquals(oldMarkers.length+1, newMarkers.length);

        IMarker last = newMarkers[newMarkers.length-1];
        assertEquals(CoreMessageHandler.messageMarkerType, last.getType());

        assertEquals(23, last.getAttribute(IMarker.LINE_NUMBER, 0));
        assertTrue(last.getAttribute(IMarker.MESSAGE, "").endsWith("This is a message"));
        
        // reset messages
        projectMessageHandler.reset();

        newMarkers = project.getWorkspace().getRoot().findMarkers(
                null, true, IResource.DEPTH_INFINITE);
        
        assertEquals(oldMarkers.length, newMarkers.length);

    }

    // all files not in a project are associated with the workspace
    public void testWorkspaceFile() throws Exception {
        IMarker[] oldMarkers;
        
        oldMarkers = project.getWorkspace().getRoot().findMarkers(
                null, true, IResource.DEPTH_INFINITE);

        // this also checks the result of the previous run's tearDown()
        assertEquals(0, oldMarkers.length);
        
        File mainFile = new File("c:/foo/bar/main.cpp"); 

        MessageLocation srcref = new MessageLocation(new Path(mainFile.getAbsolutePath()), 23, 5);
        
        assertEquals(mainFile.getAbsolutePath(), srcref.getLocation().toOSString());
        
        IMessage msg = new Message(IStatus.ERROR,
                srcref,
                "MyMessageKey",
                "This is a message");
        MessageReporting.emitMessage(msg);
        //projectMessageHandler.emitMessage(msg);
        
        IMarker[] newMarkers;
        newMarkers = project.getWorkspace().getRoot().findMarkers(
                null, true, IResource.DEPTH_INFINITE);
        
        assertEquals(oldMarkers.length+1, newMarkers.length);

        IMarker last = newMarkers[newMarkers.length-1];
        assertEquals(WorkspaceMessageHandler.messageMarkerType, last.getType());

        assertEquals(23, last.getAttribute(IMarker.LINE_NUMBER, 0));
        assertTrue(last.getAttribute(IMarker.MESSAGE, "").endsWith("This is a message"));

        // reset messages
        workspaceMessageHandler.reset();

        newMarkers = project.getWorkspace().getRoot().findMarkers(
                null, true, IResource.DEPTH_INFINITE);
        
        assertEquals(oldMarkers.length, newMarkers.length);


    }

}
