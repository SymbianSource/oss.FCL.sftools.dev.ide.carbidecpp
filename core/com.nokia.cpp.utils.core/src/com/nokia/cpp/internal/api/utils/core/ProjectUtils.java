/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.cpp.internal.api.utils.core;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;

import java.io.File;
import java.io.FileNotFoundException;

public class ProjectUtils {
    static public IProject createAndOpenProject(String projname) throws CoreException {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot root = workspace.getRoot();
        IProject newProjectHandle = root.getProject(projname);
        IProjectDescription description = workspace.newProjectDescription(projname);
        newProjectHandle.create(description, null);
        newProjectHandle.open(null);
        return newProjectHandle;
    }
    
    public static IProject importProject(File containingFolder) throws FileNotFoundException, CoreException {
    	Check.checkArg(isProjectDirectory(containingFolder));
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot root = workspace.getRoot();
        
        File projectFile = new File(containingFolder, ".project");
        IPath path = new Path(projectFile.getPath());
        IProjectDescription description = workspace.loadProjectDescription(path);
        IProject newProjectHandle = root.getProject(description.getName());
        newProjectHandle.create(description, null);
        newProjectHandle.open(null);
        return newProjectHandle;
    }
    
    /**
     * Return true if the given directory seems to be an Eclipse project directory
     */
    public static boolean isProjectDirectory(File projectFolder) {
    	boolean result = false;
    	if (projectFolder.isDirectory()) {
    		File projectFile = new File(projectFolder, ".project");
    		result = projectFile.exists();
    	}
    	return result;
    }

    static public void closeAndDeleteProject(String projname) throws CoreException {
    	closeAndDeleteProject(projname, true);
    }
    
    static public void closeAndDeleteProject(String projname, boolean deleteContent) throws CoreException {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot root = workspace.getRoot();
        // When this method was called from unit tests we were seeing
        // sporadic failures. Under the theory that this might be to do
        // with file system changes underneath Eclipse we make sure the
        // workspace is in synch before deleting the project.
        root.refreshLocal(IResource.DEPTH_INFINITE, null);
        
        IProject projectHandle = root.getProject(projname);
        projectHandle.close(null);
        try {
            projectHandle.delete(deleteContent, false, new NullProgressMonitor());
        } catch (CoreException e) {
            try {
                root.refreshLocal(IResource.DEPTH_INFINITE, null);
                Thread.sleep(1000);
            } catch (InterruptedException e2) {
                
            }
            projectHandle.delete(deleteContent, deleteContent, new NullProgressMonitor());
        }
    }

    /**
     * Get the project location where its contents live on disk,
     * accounting for linked resources.
     * @param project
     * @return full path on filesystem to project contents
     */
    public static IPath getRealProjectLocation(IProject project) {
    	if (project == null)
    		return null;
    	IPath path = project.getRawLocation();
    	if (path == null)
    		path = project.getLocation();
    	return path;
    }
}
