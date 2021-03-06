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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import com.nokia.carbide.cpp.ui.CarbideUIPlugin;
import com.nokia.carbide.cpp.ui.ICarbideSharedImages;
import com.nokia.cdt.debug.cw.symbian.SettingsData;
import com.nokia.cdt.internal.debug.launch.LaunchPlugin;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.core.Pair;

public class LaunchCreationWizard extends Wizard implements ILaunchCreationWizard {

	private MainExecutableSelectionWizardPage fBinarySelectionPage;
	private LaunchWizardSummaryPage fEmulationSummaryPage;
	private LaunchWizardSelectionPage fWizardSelectionPage;
    private BuildOptionsSelectionPage fBuildOptionsSelectionPage;
	private ILaunchConfigurationWorkingCopy launchConfig;
	private boolean shouldOpenLaunchDialog;
	private IProject project;
	private String configurationName;
	private List<AbstractLaunchWizard> wizards = new ArrayList<AbstractLaunchWizard>();
	private String categoryId;
	private AbstractLaunchWizard selectedWizard;
	
	public LaunchCreationWizard(IProject project, String configurationName, 
										List<IPath> mmps, List<IPath> exes, IPath defaultExecutable,  
										boolean isEmulation, boolean emulatorOnly, String mode) throws Exception {
		this.project = project;
		
		this.configurationName = configurationName;
		
		loadWizards(mmps, exes, defaultExecutable, mode);

		// show the binary selection page as only page in main wizard if emulation, 
		// otherwise it will be shown as needed by non-emulation wizards
		if (isEmulation)  {
			fEmulationSummaryPage = new LaunchWizardSummaryPage(configurationName);
			fBuildOptionsSelectionPage = new BuildOptionsSelectionPage();
			Check.checkState(exes.size() > 0);
			IPath emulatorPath = getEmulatorPathFromExePath(exes.get(0));
			fBinarySelectionPage = new MainExecutableSelectionWizardPage(mmps, exes, defaultExecutable, true, emulatorPath, emulatorOnly, fEmulationSummaryPage);
		}
		else {
	        fWizardSelectionPage = new LaunchWizardSelectionPage(this, mmps, exes, defaultExecutable, project, configurationName, mode);
		}
	}

	public boolean performFinish() {
		if (fBinarySelectionPage != null) {
			try {
				Pair<IPath, IPath> exeAndMmp = fBinarySelectionPage.getSelectedExeMmpPair();
				launchConfig = DebugPlugin.getDefault().getLaunchManager().getLaunchConfigurationType(LaunchPlugin.EMULATION_LAUNCH_TYPE).newInstance(null, configurationName);
				SettingsData.setDefaults(launchConfig, SettingsData.LaunchConfig_Emulator, project, exeAndMmp.second, exeAndMmp.first);
				fBuildOptionsSelectionPage.updateConfiguration(launchConfig);
				IPath processToLaunchTargetPath = fBinarySelectionPage.getProcessToLaunchTargetPath();	
				if (processToLaunchTargetPath != null)
	    			SettingsData.setProcessToLaunch(launchConfig, processToLaunchTargetPath);
				shouldOpenLaunchDialog = fEmulationSummaryPage.shouldOpenLaunchConfigurationDialog();
			} catch (CoreException e) {
				LaunchPlugin.log(e);
			}
		}
		
		if (selectedWizard != null) {
			selectedWizard.performFinish();
			Pair<IPath, IPath> exeAndMmp = selectedWizard.getSelectedExeMmpPair();
			IPath processToLaunchTargetPath = selectedWizard.getProcessToLaunchTargetPath();
	    	launchConfig = 
	    		selectedWizard.createLaunchConfiguration(exeAndMmp.second, exeAndMmp.first, processToLaunchTargetPath);
	    	shouldOpenLaunchDialog = selectedWizard.shouldOpenLaunchConfigurationDialog();
		}
		
		return true;
    }
 
    public void addPages() {
    	if (fBinarySelectionPage != null) {
    		addPage(fBinarySelectionPage);
    		addPage(fBuildOptionsSelectionPage);
    		addPage(fEmulationSummaryPage);
    	} 
    	else if (fWizardSelectionPage != null) {
    		List<AbstractLaunchWizard> wizards = getWizardsForCategory(getCategoryId());
    		if (wizards.size() > 1) {
    			addPage(fWizardSelectionPage);
    		} else {
    			// just directly "select" the single wizard and add its pages instead of using
    			// a wizard selection node, to avoid a needless intermediate selection page
    			AbstractLaunchWizard wizard = wizards.get(0);
    			for (IWizardPage page : wizard.getPages()) {
    				page.setWizard(null);
    				addPage(page);
    			}
    			setWindowTitle(wizard.getWindowTitle());
    			fWizardSelectionPage = null;
    			selectedWizard = wizard;
    		}
    	}
    }
    
    @Override
	public boolean needsPreviousAndNextButtons() {
		return true;
	}

	public void init() {
        setWindowTitle(Messages.getString("LaunchCreationWizard.0")); //$NON-NLS-1$
		setDefaultPageImageDescriptor(CarbideUIPlugin.getSharedImages().getImageDescriptor(ICarbideSharedImages.IMG_NEW_LAUNCH_CONFIG_WIZARD_BANNER));
    }

    public ILaunchConfigurationWorkingCopy getLaunchConfiguration() {
    	return launchConfig;
    }
    
	public int openWizard(Shell shell) {
		WizardDialog dialog = new WizardDialog(shell, this);
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
	
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getCategoryId() {
		return categoryId;
	}
	
	public List<AbstractLaunchWizard> getWizardsForCategory(String categoryId) {
		List<AbstractLaunchWizard> wizardsInCatgegory = new ArrayList<AbstractLaunchWizard>();
		for (AbstractLaunchWizard wizard : wizards) {
			if (wizard.supportsCategory(categoryId)) {
				wizardsInCatgegory.add(wizard);
			}
		}
		return wizardsInCatgegory;
	}
	
	private void loadWizards(List<IPath> mmps, List<IPath> exes, IPath defaultExecutable, String mode) {
		// load any wizard extensions
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = extensionRegistry.getExtensionPoint(LaunchPlugin.PLUGIN_ID + ".launchWizardExtension"); //$NON-NLS-1$
		IExtension[] extensions = extensionPoint.getExtensions();
		
		for (int i = 0; i < extensions.length; i++) {
			IExtension extension = extensions[i];
			IConfigurationElement[] elements = extension.getConfigurationElements();
			Check.checkContract(elements.length == 1);
			IConfigurationElement element = elements[0];
			
			boolean failed = false;
			try {
				Object extObject = element.createExecutableExtension("class"); //$NON-NLS-1$
				if (extObject instanceof ILaunchWizardContributor) {
					AbstractLaunchWizard wizard = ((ILaunchWizardContributor)extObject).getWizard(mmps, exes, defaultExecutable, project, configurationName);
					if (wizard.supportsMode(mode)) {
						wizard.addPages();
						wizards.add(wizard);
					}
				} else {
					failed = true;
				}
			} 
			catch (CoreException e) {
				failed = true;
			}
			
			if (failed) {
				LaunchPlugin.log(Logging.newStatus(LaunchPlugin.getDefault(), 
						IStatus.ERROR,
						"Unable to load launchWizardExtension extension from " + extension.getContributor().getName()));
			}
		}
	}

	public void setSelectedWizard(AbstractLaunchWizard selectedWizard) {
		this.selectedWizard = selectedWizard;
	}
}
