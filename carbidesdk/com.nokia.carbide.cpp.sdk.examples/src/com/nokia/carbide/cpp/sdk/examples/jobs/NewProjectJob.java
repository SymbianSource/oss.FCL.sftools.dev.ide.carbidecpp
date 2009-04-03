/*
* Copyright (c) 2007-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.sdk.examples.jobs;

import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.cpp.sdk.core.*;
import com.nokia.carbide.cpp.sdk.examples.Activator;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.osgi.framework.Bundle;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * This job performs all the steps needed to create our project,
 * create the files, and configure the project for use with Carbide.c++.
 */
public class NewProjectJob extends WorkspaceJob {
	
	private final String projectName;
	private IProject project;
	private IProgressMonitor monitor;
	
	private static class CanceledException extends RuntimeException {
		private static final long serialVersionUID = -2555091150628708940L;
	}

	public NewProjectJob(String projectName) {
		super("");
		this.projectName = projectName;
		String fmt = "Create Carbide.c++ project ''{0}''";
		setName(MessageFormat.format(fmt, projectName));
		setRule(ResourcesPlugin.getWorkspace().getRoot());
		setUser(true);
	}

	@Override
	public IStatus runInWorkspace(IProgressMonitor monitor) {
		this.monitor = monitor;
		try {
			monitor.beginTask("Creating project", 100);
			
			// create a new, basic Eclipse project
			project = ProjectCorePlugin.createProject(projectName, null);
			worked(20);
			
			// add our folders
			createFolder("src");
			worked(10);
			
			createFolder("inc");
			worked(10);

			createFolder("data");
			worked(10);

			IFolder groupFolder = createFolder("group");
			worked(10);
			
			// create our files
			createFile(groupFolder, "bld.inf", "/data/bld.inf");
			worked(10);
			
			createFile(groupFolder, "empty.mmp", "/data/empty.mmp");
			worked(10);
			
			List<ISymbianBuildContext> buildConfigurations = getSDKConfigurations();

			// now that our project file structure is complete, ask Carbide to finish
			// configuring the project
			ProjectCorePlugin.postProjectCreatedActions(project, "group/bld.inf",
					buildConfigurations, new ArrayList<String>(), "empty.mmp",
					null, monitor);
			worked(20);
			
		} catch (CoreException x) {
			return statusFromException(x);
		} catch (IOException x) {
			return statusFromException(x);
		} catch (CanceledException x) {
			return Status.CANCEL_STATUS;
		}
		
		return Status.OK_STATUS;
	}
	
	private void worked(int amount) {
		monitor.worked(amount);
		if (monitor.isCanceled()) {
			throw new CanceledException();
		}
	}
	
	private IFolder createFolder(String name) throws CoreException {
		IFolder folder = project.getFolder(name);
		folder.create(false, false, monitor);
		return folder;
	}
	
	private IFile createFile(IFolder folder, String name, String initialContentsPath) throws CoreException, IOException {
		Bundle bundle = Activator.getDefault().getBundle();
		InputStream is = FileLocator.openStream(bundle, new Path(initialContentsPath), false);
		IFile file;
		try {
			file = folder.getFile(name);
			file.create(is, false, monitor);
		} finally {
			try {
				is.close();
			} catch (IOException x) {
			}
		}
		return file;
	}
	
	/**
	 * Get the build configurations used to configure this project. 
	 * This method just returns all the configurations for the first
	 * SDK in the SDK manager's list.
	 */
	private List<ISymbianBuildContext> getSDKConfigurations() {
		List<ISymbianBuildContext> result = new ArrayList<ISymbianBuildContext>();
		List<ISymbianSDK> sdks = SDKCorePlugin.getSDKManager().getSDKList();
		ISymbianSDK sdk = sdks.get(0);
		result.addAll(sdk.getFilteredBuildConfigurations());
		return result;
	}
	
	private IStatus statusFromException(Exception x) {
		return new Status(IStatus.ERROR, Activator.PLUGIN_ID, 
				0, x.getLocalizedMessage(), x);
	}

}
