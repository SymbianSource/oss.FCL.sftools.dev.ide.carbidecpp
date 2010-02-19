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
package com.nokia.carbide.cpp.internal.qt.ui.wizard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cpp.internal.api.sdk.ISDKManagerInternal;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.carbide.cpp.internal.qt.core.QtCorePlugin;
import com.nokia.carbide.cpp.internal.qt.core.QtSDKUtils;
import com.nokia.carbide.cpp.internal.qt.ui.QtUIPlugin;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.cpp.sdk.core.*;
import com.trolltech.qtcppproject.QtProject;
import com.trolltech.qtcppproject.qmake.QMakeRunner;

public class QtProFileImportWizard extends Wizard implements IImportWizard {
	
	private QtProFileSelectionPage proFileSelectionPage;
	private QtImporterBuildTargetsPage buildTargetsPage;


	public QtProFileImportWizard() {
		super();
		IDialogSettings workbenchSettings = QtUIPlugin.getDefault().getDialogSettings();
		IDialogSettings section = workbenchSettings.getSection("QtProFileImportWizard"); //$NON-NLS-1$
		if (section == null)
			section = workbenchSettings.addNewSection("QtProFileImportWizard"); //$NON-NLS-1$
		setDialogSettings(section);
		setNeedsProgressMonitor(true);
		
		ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
		if (!sdkMgr.checkDevicesXMLSynchronized()){
			if (sdkMgr instanceof ISDKManagerInternal){
				ISDKManagerInternal sdkMgrInternal = (ISDKManagerInternal)sdkMgr;
				sdkMgrInternal.fireDevicesXMLChanged();
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	public boolean performFinish() {
    	proFileSelectionPage.saveDialogSettings();
    	buildTargetsPage.saveDialogSettings();
    	
		final String projectName = getProFile().removeFileExtension().lastSegment();
		final IPath rootDirectory = getProFile().removeLastSegments(1);
		
		final List<ISymbianBuildContext> selectedConfigs = getSelectedConfigs();

		// run this in a workspace job
		WorkspaceJob job = new WorkspaceJob(Messages.QtProFileImportWizard_CreatingProjectJobName) {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				monitor.beginTask(Messages.QtProFileImportWizard_CreatingProjectJobName, IProgressMonitor.UNKNOWN);

				IProject newProject = null;
        		newProject = ProjectCorePlugin.createProject(projectName, rootDirectory.toOSString());
        		monitor.worked(1);

        		// add qt nature here
    			QtCorePlugin.addQtNature(newProject, monitor);

    			// enable the pro file listener by default
    			new QtProject(newProject).setRunQMakeWhenProFileChanges(true);

    			// Set the default Qt SDK, if any
    			ISymbianSDK sdk = selectedConfigs.get(0).getSDK();
    			String qtSDKName = QtSDKUtils.getQtSDKNameForSymbianSDK(sdk);
    			if (qtSDKName == null){
    				QtSDKUtils.addQtSDKForSymbianSDK(sdk, false);
    				qtSDKName = QtSDKUtils.getQtSDKNameForSymbianSDK(sdk);
    			}
    			
    			if (qtSDKName != null){
    				QtSDKUtils.setDefaultQtSDKForProject(newProject, qtSDKName);
    			}
    			
    			// set EPOCROOT to the default build config's SDK before calling qmake
    			IPath epocroot = new Path(selectedConfigs.get(0).getSDK().getEPOCROOT());
    			Map<String, String> envMods = new HashMap<String, String>();
    			envMods.put("EPOCROOT", epocroot.setDevice(null).toOSString());
    			
    			// run qmake
    			String errMsg = QMakeRunner.runQMake(newProject, envMods, monitor);
    			if (errMsg != null) {
    				throw new CoreException(new Status(IStatus.ERROR, QtUIPlugin.PLUGIN_ID, errMsg));
    			}

        		newProject.setSessionProperty(CarbideBuilderPlugin.SBSV2_PROJECT, Boolean.valueOf(proFileSelectionPage.useSBSv2Builder()));

    			ProjectCorePlugin.postProjectCreatedActions(newProject, "bld.inf", selectedConfigs, new ArrayList<String>(0), "", null, monitor); //$NON-NLS-1$ //$NON-NLS-2$

    			ProjectUIPlugin.projectCreated(newProject);

    			// switch to the Qt perspective
    			QtUIPlugin.switchToQtPerspective();

    			// set the qmake generated pkg files to be built
    			QtUIPlugin.setupSISBuilderSettings(newProject);
    			
    			if (monitor.isCanceled()) {
	    			// the user canceled the import so delete the project
	    			newProject.delete(false, true, null);
	        		monitor.done();
					return Status.CANCEL_STATUS;
	    		}
        		monitor.worked(1);
        		monitor.done();
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.setRule(ResourcesPlugin.getWorkspace().getRoot());
		job.schedule();

		return true;
	}
	 
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle(Messages.QtProFileImportWizard_title);
		setNeedsProgressMonitor(true);
		proFileSelectionPage = new QtProFileSelectionPage();
		buildTargetsPage = new QtImporterBuildTargetsPage(this);
	}
	
	/* (non-Javadoc)
     * @see org.eclipse.jface.wizard.IWizard#addPages()
     */
    public void addPages() {
        super.addPages(); 
        addPage(proFileSelectionPage);
        addPage(buildTargetsPage);
    }
    
    public IPath getProFile() {
    	if (proFileSelectionPage != null) {
    		return new Path(proFileSelectionPage.getProFilePath());
    	}
    	return null;
    }
    
    public List<ISymbianBuildContext> getSelectedConfigs() {
    	return buildTargetsPage.getSelectedBuildConfigs();
    }
}
