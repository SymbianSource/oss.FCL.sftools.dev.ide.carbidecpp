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
package com.nokia.cdt.internal.debug.launch.wizard;

import com.nokia.carbide.cpp.ui.CarbideUIPlugin;
import com.nokia.carbide.cpp.ui.ICarbideSharedImages;
import com.nokia.cdt.debug.cw.symbian.SettingsData;
import com.nokia.cdt.internal.debug.launch.LaunchPlugin;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;

import java.util.List;

public class LaunchCreationWizard extends Wizard {

	private MainExecutableSelectionWizardPage fBinarySelectionPage;
	private LaunchWizardSummaryPage fEmulationSummaryPage;
	private LaunchWizardSelectionPage fWizardSelectionPage;
	private ILaunchConfigurationWorkingCopy launchConfig;
	private boolean shouldOpenLaunchDialog;
	private IProject project;
	private String configurationName;
	
	public LaunchCreationWizard(IProject project, String configurationName, 
										List<IPath> mmps, List<IPath> exes, IPath defaultExecutable,  
										boolean isEmulation, boolean emulatorOnly) throws Exception {
		this.project = project;
		
		// show the binary selection page as only page in main wizard if emulation, 
		// otherwise it will be shown as needed by non-emulation wizards
		if (isEmulation)  {
			fEmulationSummaryPage = new LaunchWizardSummaryPage(configurationName);
			Check.checkState(exes.size() > 0);
			IPath emulatorPath = getEmulatorPathFromExePath(exes.get(0));
			fBinarySelectionPage = new MainExecutableSelectionWizardPage(mmps, exes, defaultExecutable, true, emulatorPath, emulatorOnly, fEmulationSummaryPage);
		}
		else {	
	        fWizardSelectionPage = new LaunchWizardSelectionPage(mmps, exes, defaultExecutable, project, configurationName);
		}

		this.configurationName = configurationName;
	}

	public boolean performFinish() {
		if (fBinarySelectionPage != null) {
			try {
				Pair<IPath, IPath> exeAndMmp = fBinarySelectionPage.getSelectedExeMmpPair();
				launchConfig = DebugPlugin.getDefault().getLaunchManager().getLaunchConfigurationType(LaunchPlugin.EMULATION_LAUNCH_TYPE).newInstance(null, configurationName);
				SettingsData.setDefaults(launchConfig, SettingsData.LaunchConfig_Emulator, project, exeAndMmp.second, exeAndMmp.first);
		    	IPath processToLaunchTargetPath = fBinarySelectionPage.getProcessToLaunchTargetPath();	
				if (processToLaunchTargetPath != null)
	    			SettingsData.setProcessToLaunch(launchConfig, processToLaunchTargetPath);
				shouldOpenLaunchDialog = fEmulationSummaryPage.shouldOpenLaunchConfigurationDialog();
			} catch (CoreException e) {
				LaunchPlugin.log(e);
			}
		}
		
		if (fWizardSelectionPage != null) {
			AbstractLaunchWizard wizard = ((AbstractLaunchWizard) fWizardSelectionPage.getSelectedWizard());
			wizard.performFinish();
			Pair<IPath, IPath> exeAndMmp = wizard.getSelectedExeMmpPair();
			IPath processToLaunchTargetPath = wizard.getProcessToLaunchTargetPath();
	    	launchConfig = 
	    		fWizardSelectionPage.getSelectedWizard().createLaunchConfiguration(exeAndMmp.second, exeAndMmp.first, processToLaunchTargetPath);
	    	shouldOpenLaunchDialog = fWizardSelectionPage.getSelectedWizard().shouldOpenLaunchConfigurationDialog();
		}
		
		return true;
    }
 
    public void addPages() {
    	if (fBinarySelectionPage != null) {
    		addPage(fBinarySelectionPage);
    		addPage(fEmulationSummaryPage);
    	} 
    	else if (fWizardSelectionPage != null) {
            addPage(fWizardSelectionPage);
    	}
    }
    
    @Override
	public boolean needsPreviousAndNextButtons() {
		return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
        setWindowTitle(Messages.getString("LaunchCreationWizard.0")); //$NON-NLS-1$
		setDefaultPageImageDescriptor(CarbideUIPlugin.getSharedImages().getImageDescriptor(ICarbideSharedImages.IMG_NEW_LAUNCH_CONFIG_WIZARD_BANNER));
    }

    public ILaunchConfigurationWorkingCopy getLaunchConfiguration() {
    	return launchConfig;
    }
    
	public static int openWizard(Shell shell, LaunchCreationWizard wizard) {
		WizardDialog dialog = new WizardDialog(shell, wizard);
		dialog.create();
		return dialog.open();
	}

	public static IPath getEmulatorPathFromExePath(IPath pathToExe) {
		IPath path = pathToExe.removeLastSegments(1);
		path = path.append("epoc.exe"); //$NON-NLS-1$
		if (path.toFile().exists())
			return path;
		
		return null;
	}

	public boolean shouldOpenLaunchConfigurationDialog() {
		return shouldOpenLaunchDialog;
	}
	
}
