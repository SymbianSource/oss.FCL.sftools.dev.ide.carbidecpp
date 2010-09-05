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
package com.nokia.carbide.cpp.internal.project.ui.importWizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cpp.internal.api.sdk.ISDKManagerInternal;
import com.nokia.carbide.cpp.internal.featureTracker.FeatureUseTrackerPlugin;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.carbide.cpp.internal.sdk.core.model.SDKManager;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.cpp.sdk.core.ISDKManager;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.carbide.cpp.ui.CarbideUIPlugin;
import com.nokia.carbide.cpp.ui.ICarbideSharedImages;
import com.nokia.cpp.internal.api.utils.core.HostOS;

public class BldInfImportWizard extends Wizard implements IImportWizard {
	
	private BldInfSelectionPage bldInfSelectionPage;
	private ImporterBuildTargetsPage buildTargetsPage;
	private MMPSelectionPage mmpSelectionPage;
	private ProjectPropertiesPage projectPropertiesPage;

	private static final String CARBIDE_BLDINF_IMFPORTER_FEATURE = "CARBIDE_BLDINF_IMPORTER"; //$NON-NLS-1$

	public BldInfImportWizard() {
		super();
		IDialogSettings workbenchSettings = ProjectUIPlugin.getDefault().getDialogSettings();
		IDialogSettings section = workbenchSettings.getSection("BldInfImportWizard"); //$NON-NLS-1$
		if (section == null)
			section = workbenchSettings.addNewSection("BldInfImportWizard"); //$NON-NLS-1$
		setDialogSettings(section);
		setNeedsProgressMonitor(true);
		setDefaultPageImageDescriptor(CarbideUIPlugin.getSharedImages().getImageDescriptor(ICarbideSharedImages.IMG_IMPORT_BLDINF_WIZARD_BANNER));
		
		ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
		if (HostOS.IS_WIN32){
			((SDKManager)sdkMgr).ensureSystemDrivesSynchronized();
			if (!((SDKManager)sdkMgr).checkDevicesXMLSynchronized()){
				if (sdkMgr instanceof ISDKManagerInternal){
					ISDKManagerInternal sdkMgrInternal = (ISDKManagerInternal)sdkMgr;
					sdkMgrInternal.fireDevicesXMLChanged();
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	public boolean performFinish() {
    	bldInfSelectionPage.saveDialogSettings();
    	buildTargetsPage.saveDialogSettings();
    	
		final String projectName = projectPropertiesPage.getProjectName();
		final IPath rootDirectory = projectPropertiesPage.getRootDirectory();
		
		// calculate the project relative path to the bld.inf file.
		IPath absoluteBldInfPath = new Path(getBldInfFile());
		assert(rootDirectory.isPrefixOf(absoluteBldInfPath));
		final String projectRelativePath = absoluteBldInfPath.removeFirstSegments(rootDirectory.segmentCount()).setDevice(null).toOSString();
		final String absoluteInfPath = absoluteBldInfPath.toOSString();
		
		// if all mmps are checked then don't pass any to createProject.  that
		// way the project setting will be set to build bld.inf.
		final List<String> components = mmpSelectionPage.areAllMakMakeReferencesChecked() ? new ArrayList<String>(0) : mmpSelectionPage.getSelectedMakMakeReferences();

		final List<ISymbianBuildContext> selectedConfigs = getSelectedConfigs();

		// run this in a workspace job
		WorkspaceJob job = new WorkspaceJob(Messages.BldInfImportWizard_CreatingProjectJobName) {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				monitor.beginTask(Messages.BldInfImportWizard_CreatingProjectJobName, IProgressMonitor.UNKNOWN);

				IProject newProject = null;
				newProject = ProjectCorePlugin.createProject(projectName, rootDirectory.toOSString());
				
        		monitor.worked(1);
        		
    			newProject.setSessionProperty(CarbideBuilderPlugin.SBSV2_PROJECT, Boolean.valueOf(useSBSv2Builder()));

    			ProjectCorePlugin.postProjectCreatedActions(newProject, projectRelativePath, selectedConfigs, components, null, null, monitor);
        		
        		if (monitor.isCanceled()) {
	    			// the user canceled the import so delete the project
	    			newProject.delete(false, true, null);
	        		monitor.done();
					return Status.CANCEL_STATUS;
	    		}
        		monitor.worked(1);
        		ProjectUIPlugin.projectCreated(newProject);
        		monitor.done();
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.setRule(ResourcesPlugin.getWorkspace().getRoot());
		job.schedule();

		FeatureUseTrackerPlugin.getFeatureUseProxy().useFeature(CARBIDE_BLDINF_IMFPORTER_FEATURE);
		
		return true;
	}
	 
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle(Messages.BldInfImportWizard_title);
		setNeedsProgressMonitor(true);
		bldInfSelectionPage = new BldInfSelectionPage(this);
		buildTargetsPage = new ImporterBuildTargetsPage(this);
		mmpSelectionPage = new MMPSelectionPage(this);
		projectPropertiesPage = new ProjectPropertiesPage(this);
	}
	
	/* (non-Javadoc)
     * @see org.eclipse.jface.wizard.IWizard#addPages()
     */
    public void addPages() {
        super.addPages(); 
        addPage(bldInfSelectionPage);
        addPage(buildTargetsPage);
        addPage(mmpSelectionPage);
        addPage(projectPropertiesPage);
    }
    
    public String getBldInfFile() {
    	if (bldInfSelectionPage != null) {
    		return bldInfSelectionPage.getInfFilePath();
    	}
    	return ""; //$NON-NLS-1$
    }
    
    public List<ISymbianBuildContext> getSelectedConfigs() {
    	return buildTargetsPage.getSelectedBuildConfigs();
    }
    
    public List<String> getSelectedMakMakeReferences() {
    	return mmpSelectionPage.getSelectedMakMakeReferences();
    }
    
    public void setWizardIncomplete() {
    	// setting the last page is good enough
    	projectPropertiesPage.setPageComplete(false);
    }
    
    public boolean useSBSv2Builder() {
    	return bldInfSelectionPage.useSBSv2Builder();
    }
}
