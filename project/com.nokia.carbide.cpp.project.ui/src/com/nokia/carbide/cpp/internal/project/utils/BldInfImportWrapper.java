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
package com.nokia.carbide.cpp.internal.project.utils;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;

import com.nokia.carbide.cdt.builder.EpocEngineHelper;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;

/*
 * Helper class to create Carbide projects from bld.inf without having to use the project import wizard.
 * STATUS: Currently experimental. This class is being used to speed up development of test cases.
 */
public class BldInfImportWrapper {
	
	IProject project;
	String projectName;
	IPath rootDirectory; 
	IPath bldInfFile;
	List<String> components;
	List<String> refs;
	List<ISymbianBuildContext> selectedConfigs;
	
	/**
	 * Construct the bld.inf warpper object with necessary data to create a complete Carbide.c++ project.
	 * @param projectName - Arbitrary name of project you want to give. Must be unique in workspace.
	 * @param rootDirectory - Location of all sources
	 * @param bldInfFile - The full path to the bld.inf file
	 * @param components - components you want to build. make empty list to build from bld.inf (i.e. all components)
	 * @param refs - makmake references. can be empty list. This is just used to set the debug MMP target
	 * @param selectedConfigs - The ISymbianBuildContex(s) use to create the build configruations from. Must have at least one.
	 */
	public BldInfImportWrapper(String projectName, IPath rootDirectory, IPath bldInfFile, List<String> components,
			List<String> refs, List<ISymbianBuildContext> selectedConfigs){
		this.projectName = projectName;
		this.bldInfFile = bldInfFile;
		if (rootDirectory == null){
			// calculate the root directory as it's not defined
			List<IPath> projectRoots = EpocEngineHelper.getProjectRoots(bldInfFile, selectedConfigs, new NullProgressMonitor());
			this.rootDirectory = projectRoots.get(0);
		} else {
			this.rootDirectory = rootDirectory;
		}
		
		this.components = components;
		this.refs = refs;
		this.selectedConfigs = selectedConfigs;
	}
	
	/**
	 * Create a Carbide project from member variable data. This is basically the same code as from com.nokia.carbide.cpp.internal.project.ui.importWizards#performFinish()
	 */
	public void createProjectFromBldInf(){
		
		final String projectRelativePath = bldInfFile.removeFirstSegments(rootDirectory.segmentCount()).setDevice(null).toOSString();
		
		// run this in a workspace job
		WorkspaceJob job = new WorkspaceJob("Creating Carbide Project Wrapper") {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				monitor.beginTask("Creating Carbide Project Wrapper", IProgressMonitor.UNKNOWN);

				// write the debug target mmp setting - use the last mmp in
				// the list of selected mak make files.
				// We also need to check for project test mmpfiles and add that only if the project is only comprised of test mmp files
				String debugMMP = ""; //$NON-NLS-1$
				boolean hasOneNormalMMP = false; // Don't add test mmp if there's a regular MMP
				for (String ref : refs) {
					if (ref.toLowerCase().endsWith(".mmp")){
						hasOneNormalMMP = true;
						debugMMP = ref;
					}
					
					if (hasOneNormalMMP == false){
						if (ref.toLowerCase().endsWith(".mmp " + ICarbideProjectInfo.TEST_COMPONENT_LABEL)) { //$NON-NLS-1$
			    			debugMMP = ref;
		    				debugMMP = debugMMP.substring(0, debugMMP.indexOf( " " + ICarbideProjectInfo.TEST_COMPONENT_LABEL));
			    		}
					}
				} // for

        		project = ProjectCorePlugin.createProject(projectName, rootDirectory.toOSString());
        		monitor.worked(1);
        		// TODO pass PKG file path to postProjectCreatedActions, currently passing null
        		ProjectCorePlugin.postProjectCreatedActions(project, projectRelativePath, selectedConfigs, components, debugMMP, null, monitor);

        		if (monitor.isCanceled()) {
	    			// the user cancelled the import so delete the project
        			project.delete(false, true, null);
	        		monitor.done();
					return Status.CANCEL_STATUS;
	    		}
        		monitor.worked(1);
        		ProjectUIPlugin.projectCreated(project);
        		monitor.done();
        		
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.setRule(ResourcesPlugin.getWorkspace().getRoot());
		job.schedule();
		try {
			job.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the IProject created from createProjectFromBldInf()
	 * @return IProject. May be null if errors exist.
	 */
	public IProject getProject() {
		return project;
	}

}
