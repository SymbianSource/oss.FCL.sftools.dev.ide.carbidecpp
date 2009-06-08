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
import com.nokia.cpp.internal.api.utils.core.Pair;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.jface.wizard.Wizard;

import java.util.List;

public abstract class AbstractLaunchWizard extends Wizard implements ILaunchWizard {

	public static final String PHONE_CATEGORY_ID = "com.nokia.cdt.debug.launch.phoneCategory"; //$NON-NLS-1$
	public static final String BOARD_CATEGORY_ID = "com.nokia.cdt.debug.launch.boardCategory"; //$NON-NLS-1$
	
	private LaunchWizardSummaryPage summaryPage;
	private String configName = ""; //$NON-NLS-1$
	private IProject project;
	private MainExecutableSelectionWizardPage binarySelectionPage;
	private List<IPath> mmps;
	private List<IPath> exes;
	private IPath defaultExecutable;
	private boolean wantsBinarySelectionPage;
	private boolean asProcessToLaunch;

	public AbstractLaunchWizard(IProject project, String configurationName, 
			List<IPath> mmps, List<IPath> exes, IPath defaultExecutable, boolean wantsBinarySelectionPage, boolean asProcessToLaunch) {
		this.project = project;
		configName = configurationName;
		this.mmps = mmps;
		this.exes = exes;
		this.defaultExecutable = defaultExecutable;
		this.wantsBinarySelectionPage = wantsBinarySelectionPage;
		this.asProcessToLaunch = asProcessToLaunch;
		summaryPage = new LaunchWizardSummaryPage(configName);
		setDefaultPageImageDescriptor(CarbideUIPlugin.getSharedImages().getImageDescriptor(ICarbideSharedImages.IMG_NEW_LAUNCH_CONFIG_WIZARD_BANNER));
	}

	public abstract String getLaunchTypeID();
	
	public abstract boolean supportsCategory(String categoryId);
	
	public boolean supportsMode(String mode) {
		return DebugPlugin.getDefault().getLaunchManager().getLaunchConfigurationType(getLaunchTypeID()).supportsMode(mode);
	}
	
	public String getConfigName() {
		return configName;
	}

	public IProject getProject() {
		return project;
	}
	
	public void addBinarySelectionPage(List<IPath> mmps, List<IPath> exes, IPath defaultExecutable, boolean asProcessToLaunch) {
		if (binarySelectionPage == null) {
			binarySelectionPage = 
				new MainExecutableSelectionWizardPage(mmps, exes, defaultExecutable, asProcessToLaunch, null, false, this);
			addPage(binarySelectionPage);
		}
	}

	@Override
	public void addPages() {
		super.addPages();
		if (wantsBinarySelectionPage)
			addBinarySelectionPage(mmps, exes, defaultExecutable, asProcessToLaunch);
	}

	public LaunchWizardSummaryPage getSummaryPage() {
		return summaryPage;
	}

	public boolean shouldOpenLaunchConfigurationDialog() {
		return summaryPage.shouldOpenLaunchConfigurationDialog();
	}

	public void putSummaryTextItem(String key, String summaryItem) {
		summaryPage.putSummaryTextItem(key, summaryItem);
	}

	public Pair<IPath, IPath> getSelectedExeMmpPair() {
		if (binarySelectionPage != null)
			return binarySelectionPage.getSelectedExeMmpPair();
		
		if (exes != null && !exes.isEmpty())
			return new Pair<IPath, IPath>(exes.get(0), mmps.isEmpty() ? null : mmps.get(0));
		
		return new Pair<IPath, IPath>(null, null);
	}
	
	public IPath getProcessToLaunchTargetPath() {
		if (binarySelectionPage != null)
			return binarySelectionPage.getProcessToLaunchTargetPath();
		
		return null;
	}

	public MainExecutableSelectionWizardPage getBinarySelectionPage() {
		return binarySelectionPage;
	}
}
