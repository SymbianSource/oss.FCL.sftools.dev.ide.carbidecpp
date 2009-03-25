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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;

import java.util.List;

import com.nokia.cdt.debug.cw.symbian.SettingsData;

public class Trace32LaunchWizard extends AbstractLaunchWizard {

	    private Trace32WizardPage fTrace32Page;
	    private StopModeRomImageWizardPage fRomImgPage;
	    
		private boolean hasFinished = false;

		public Trace32LaunchWizard(List<IPath> mmps, List<IPath> exes, IPath defaultExecutable, IProject project, String configurationName) {
			super(project, configurationName, mmps, exes, defaultExecutable, true, false);
			setWindowTitle(Messages.getString("Trace32LaunchWizard.1")); //$NON-NLS-1$
	    }

	    public boolean performFinish() {
	    	hasFinished = true;
	    	return true;
	    }
	 
	    public void addPages() {
	    	super.addPages();
	    	fTrace32Page = new Trace32WizardPage(this);
	    	fRomImgPage = new StopModeRomImageWizardPage(this);
	        addPage(fTrace32Page);
	        addPage(fRomImgPage);
	        addPage(getSummaryPage());
	    }

	    public String toString() {
	    	return Messages.getString("Trace32LaunchWizard.2"); //$NON-NLS-1$
	    }
	    
	    public String getDescription() {
	    	return Messages.getString("Trace32LaunchWizard.3"); //$NON-NLS-1$
	    }
	    
	    public ILaunchConfigurationWorkingCopy createLaunchConfiguration(IPath mmpPath, IPath exePath, IPath processToLaunchTargetPath) {
	    	// if we haven't finished then don't create anything
	    	if (!hasFinished) {
	    		return null;
	    	}

	    	ILaunchConfigurationWorkingCopy config = null;
	    	try {
	    		// create our config
	    		config = DebugPlugin.getDefault().getLaunchManager().getLaunchConfigurationType(SettingsData.T32_LAUNCH_TYPE_ID).newInstance(null, getConfigName());
	    		
	    		// set the default values
	    		SettingsData.setDefaults(config, SettingsData.LaunchConfig_Trace32, getProject(), mmpPath, exePath);
	    		
	    		// now let the wizard pages update values 
	    		fTrace32Page.updateConfiguration(config);
	    		fRomImgPage.updateConfiguration(config);
	    		
			} catch (CoreException e) {
				e.printStackTrace();
			}
			return config;
	    }
}
