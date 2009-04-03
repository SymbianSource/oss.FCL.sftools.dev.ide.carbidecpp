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

public class AttachTRKLaunchWizard extends AbstractLaunchWizard {
    private TRKConnectionWizardPage fMainPage;
	private boolean hasFinished = false;
    
    public AttachTRKLaunchWizard(List<IPath> mmps, List<IPath> exes, IPath defaultExecutable, IProject project, String configurationName) {
    	super(project, configurationName, mmps, exes, defaultExecutable, false, false);
		setWindowTitle(Messages.getString("AttachTRKLaunchWizard.1")); //$NON-NLS-1$
    }

    public boolean performFinish() {
    	hasFinished = true;
    	return true;
    }
 
    public void addPages() {
    	super.addPages();
        fMainPage = new TRKConnectionWizardPage(this);
        addPage(fMainPage);
	    addPage(getSummaryPage());
   }

    public String toString() {
    	return Messages.getString("AttachTRKLaunchWizard.2"); //$NON-NLS-1$
    }
    
    public String getDescription() {
    	return Messages.getString("AttachTRKLaunchWizard.3"); //$NON-NLS-1$
    }
    
    public ILaunchConfigurationWorkingCopy createLaunchConfiguration(IPath mmpPath, IPath exePath, IPath processToLaunchTargetPath) {
    	// if we haven't finished then don't create anything
    	if (!hasFinished) {
    		return null;
    	}

    	ILaunchConfigurationWorkingCopy config = null;
    	try {
    		// create our config
    		config = DebugPlugin.getDefault().getLaunchManager().getLaunchConfigurationType(SettingsData.ATTACH_LAUNCH_TYPE_ID).newInstance(null, getConfigName());
    		
    		// set the default values
    		SettingsData.setDefaults(config, SettingsData.LaunchConfig_AppTRK, getProject(), mmpPath, exePath);
    		
    		// now let the wizard pages update values 
    		fMainPage.updateConfiguration(config);
 
    	} catch (CoreException e) {
			e.printStackTrace();
		}
		return config;
    }
}
