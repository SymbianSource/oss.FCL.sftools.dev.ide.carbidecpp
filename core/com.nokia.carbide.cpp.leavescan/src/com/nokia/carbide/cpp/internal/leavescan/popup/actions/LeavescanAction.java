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
*/
package com.nokia.carbide.cpp.internal.leavescan.popup.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.EpocEngineHelper;
import com.nokia.carbide.cdt.builder.builder.CarbideCPPBuilder;
import com.nokia.carbide.cdt.builder.builder.CarbideCommandLauncher;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.internal.leavescan.LeavescanPlugin;
import com.nokia.carbide.cpp.internal.leavescan.ui.LeavescanPreferenceConstants;

public class LeavescanAction implements IObjectActionDelegate {
	
	private ISelection selection;
	
	// id definied from plugin.xml
	public static final String LEAVE_SCAN_ACTION_POP_UP_ID = "com.nokia.carbide.cpp.leavescan.leaveScanAction";
	public static final String LEAVE_SCAN_ACTION_MMP_POP_UP_ID = "com.nokia.carbide.cpp.leavescan.leaveScanActionOnMMP";

	
	/**
	 * Constructor for Action1.
	 */
	public LeavescanAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		if (action.getId().equals(LEAVE_SCAN_ACTION_POP_UP_ID)){
			// scan the selected source file(s)
			handleLeaveScanAction(action);
		} else if (action.getId().equals(LEAVE_SCAN_ACTION_MMP_POP_UP_ID)){
			// scan the selected source file(s)
			handleLeaveScanActionOnMMP(action);
		}
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}
	
	/**
	 * Run leavescan on source file selection(s)
	 * @param action
	 */
	private void handleLeaveScanAction(IAction action){
		// get the project each file belongs to...
		HashMap<IProject, List<IPath>> projectSourceMap = new HashMap<IProject, List<IPath>>();
		if (selection != null && selection instanceof IStructuredSelection) {
			Iterator iter = ((IStructuredSelection)selection).iterator();
			while (iter.hasNext()) {
				Object selItem = iter.next();
				if (selItem instanceof IFile) {
					IProject currProject = ((IResource)selItem).getProject();
					if (projectSourceMap.get(currProject) == null){
						// project is not a key, create a new key/value entry
						List<IPath> pathList = new ArrayList<IPath>();
						pathList.add(((IResource)selItem).getLocation());
						projectSourceMap.put(currProject, pathList);

					} else {
						// this key already exists, update the source list
						List<IPath> pathList  = projectSourceMap.get(currProject);
						pathList.add(((IResource)selItem).getLocation());
						projectSourceMap.put(currProject, pathList);  // 2+ paths exist for this project
					}
				}
			}
		}
		
		// Get the leavescan preferences...
		IPreferenceStore store = LeavescanPlugin.getLeaveScanPrefsStore();
		final boolean noisyOutput =  store.getBoolean(LeavescanPreferenceConstants.LEAVESCAN_NOISY_OUTPUT);
		final String leaveScanFolder = store.getString(LeavescanPreferenceConstants.LEAVESCAN_FOLDER);
		
		for (final IProject project : projectSourceMap.keySet()){
			final List<IPath> finalPathList = projectSourceMap.get(project);	
			// Run a job on each project. The arguments for leavescan is:
			// leavescan [-h|-n|-v|-N] <iFilename.cpp> [<iFilename.cpp> ...]
			//
			//            -h: This help.
			//            -n: Noisy output - provides diagnostics (if available).
			//            -N: Very noisy output - provides diagnostics (if available).
			//            -v: Displays version (for build & automation systems).
			Job buildJob = new Job("Running Leave Scan on Project: " + project.getName()) { //$NON-NLS-1$
				protected IStatus run(IProgressMonitor monitor){
					
					final String[] leaveScanParserIds = new String[] {
				        "com.nokia.carbide.cpp.leavescan.LeaveScanErrorParser"
				        };
					
			        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
			        try {
			        	CarbideCPPBuilder.removeAllMarkers(project);
			        } catch (CoreException e){
			        	e.printStackTrace();
			        }
			        IPath workingDir = cpi.getINFWorkingDirectory();
					CarbideCommandLauncher cmdLauncher = new CarbideCommandLauncher(project, monitor, leaveScanParserIds, workingDir);
					cmdLauncher.startTimingStats();
					int fileCount = 1;
					int listSize = finalPathList.size();
					for (IPath currPath : finalPathList){
						
						//System.out.print("\nProject: " + project.getName() + " | Source File: " + currPath.toOSString()); // Debug
						
						ICarbideBuildConfiguration defaultConfig = cpi.getDefaultConfiguration();
						monitor.beginTask("Running leavescan.", 100);
						
						double dWorked = ((double)fileCount / (double)listSize) * 100;
						monitor.worked((int)dWorked);
						String taskName = "Running leavescan on file \"" + currPath.toOSString() + "\" for project \"" + project.getName() + ".";
						monitor.setTaskName(taskName);
						cmdLauncher.writeToConsole("\n***" + taskName + "\n");
						// Construct the leavescan arguments
						List<String> leaveScanArgList = new ArrayList<String>();
						leaveScanArgList.add("/c");
						
						if (leaveScanFolder.length() > 0){
							leaveScanArgList.add(leaveScanFolder + "leavescan.exe");
						} else {
							leaveScanArgList.add("leavescan.exe");
						}

						if (noisyOutput) {
							leaveScanArgList.add("-N");
						} else {
							leaveScanArgList.add("-n");
						}
						
						leaveScanArgList.add(currPath.toOSString());
						String[] args = new String[leaveScanArgList.size()];
						leaveScanArgList.toArray(args);
						cmdLauncher.showCommand(true);
						
						// executeCommand, a special extension to the regular execute which will handle
						// writing the console output, error parsing, and creating error markers.
						cmdLauncher.executeCommand(CarbideCommandLauncher.getCmdExeLocation(), args, CarbideCPPBuilder.getResolvedEnvVars(defaultConfig), workingDir);
						
						fileCount++;
					}
					cmdLauncher.writeToConsole(cmdLauncher.getTimingStats());
					return new Status(IStatus.OK, LeavescanPlugin.PLUGIN_ID, IStatus.OK, "LeaveScan Complete", null); 
				}
			};
			
			buildJob.setPriority(Job.BUILD);
			buildJob.schedule();
		}
	}
	
	/**
	 * Run leavescan on all the sources in the current project MMP file.
	 * The sources files will be build configuration specifc (i.e. MMP is preprocessed) to return
	 * platform specific source list.
	 * @param action - The IAction interface from the eclipse core
	 */
	private void handleLeaveScanActionOnMMP(IAction action){
		// get the project each file belongs to...
		IProject project = null;
		IPath mmpFile = null;
		if (selection != null && selection instanceof IStructuredSelection) {
			Iterator iter = ((IStructuredSelection)selection).iterator();
			while (iter.hasNext()) {
				Object selItem = iter.next();
				if (selItem instanceof IFile) {
					project = ((IResource)selItem).getProject();
					mmpFile = ((IResource)selItem).getLocation();
					//System.out.print("\nMMP File Selected: " + ((IResource)selItem).getLocation());
				}
			}
		}
		
		if (project == null){
			return;
		}
		
		// Get the leavescan preferences...
		IPreferenceStore store = LeavescanPlugin.getLeaveScanPrefsStore();
		final boolean noisyOutput =  store.getBoolean(LeavescanPreferenceConstants.LEAVESCAN_NOISY_OUTPUT);
		final String leaveScanFolder = store.getString(LeavescanPreferenceConstants.LEAVESCAN_FOLDER);
		
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		if (cpi.getDefaultConfiguration() == null)
        	return;
		
		List<IPath> sourceFileList = new ArrayList<IPath>();
		// Get the list of source files for the default configuration this MMP is associated with.
		sourceFileList = EpocEngineHelper.getSourceFilesForConfiguration(cpi.getDefaultConfiguration(), mmpFile);
		
		final List<IPath> finalPathList = new ArrayList<IPath>(sourceFileList);	
		final IProject finalProject = project;
		final Path finalMMPPath = new Path(mmpFile.toOSString());
		Job buildJob = new Job("Running Leave Scan on MMP: " + mmpFile.toOSString()) { //$NON-NLS-1$
			protected IStatus run(IProgressMonitor monitor){
				
				final String[] leaveScanParserIds = new String[] {
			        "com.nokia.carbide.cpp.leavescan.LeaveScanErrorParser"
			        };
				
		        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(finalProject);
		        try {
		        	CarbideCPPBuilder.removeAllMarkers(finalProject);
		        } catch (CoreException e){
		        	e.printStackTrace();
		        }
		        IPath workingDir = cpi.getINFWorkingDirectory();
				CarbideCommandLauncher cmdLauncher = new CarbideCommandLauncher(finalProject, monitor, leaveScanParserIds, workingDir);
				cmdLauncher.startTimingStats();
				int fileCount = 1;
				int listSize = finalPathList.size();
				for (IPath currPath : finalPathList){
					
					//System.out.print("\nMMP Project: " + finalMMPPath.lastSegment() + " | Source File: " + currPath.toOSString()); // Debug
					
					ICarbideBuildConfiguration defaultConfig = cpi.getDefaultConfiguration();
					monitor.beginTask("Running leavescan.", 100);
					
					double dWorked = ((double)fileCount / (double)listSize) * 100;
					monitor.worked((int)dWorked);
					String taskName = "Running leavescan on file \"" + currPath.toOSString() + "\" for MMP \"" + finalMMPPath.lastSegment() + ".";
					monitor.setTaskName(taskName);
					cmdLauncher.writeToConsole("\n***" + taskName + "\n");
					// Construct the leavescan arguments
					List<String> leaveScanArgList = new ArrayList<String>();
					leaveScanArgList.add("/c");
					
					if (leaveScanFolder.length() > 0){
						leaveScanArgList.add(leaveScanFolder + "leavescan.exe");
					} else {
						leaveScanArgList.add("leavescan.exe");
					}

					if (noisyOutput) {
						leaveScanArgList.add("-N");
					} else {
						leaveScanArgList.add("-n");
					}
					
					leaveScanArgList.add(currPath.toOSString());
					String[] args = new String[leaveScanArgList.size()];
					leaveScanArgList.toArray(args);
					cmdLauncher.showCommand(true);
					
					// executeCommand, a special extension to the regular execute which will handle
					// writing the console output, error parsing, and creating error markers.
					cmdLauncher.executeCommand(CarbideCommandLauncher.getCmdExeLocation(), args, CarbideCPPBuilder.getResolvedEnvVars(defaultConfig), workingDir);
					
					fileCount++;
				}
				cmdLauncher.writeToConsole(cmdLauncher.getTimingStats());
				return new Status(IStatus.OK, LeavescanPlugin.PLUGIN_ID, IStatus.OK, "LeaveScan Complete", null); 
			}
		};
		
		buildJob.setPriority(Job.BUILD);
		buildJob.schedule();
		
	}

}
