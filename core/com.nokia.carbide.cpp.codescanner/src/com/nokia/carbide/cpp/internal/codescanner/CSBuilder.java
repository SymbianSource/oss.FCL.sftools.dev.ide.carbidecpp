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

package com.nokia.carbide.cpp.internal.codescanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.IDialogSettings;

import com.nokia.carbide.cpp.internal.codescanner.config.CSProjectSettings;
import com.nokia.carbide.cpp.internal.codescanner.markers.CSMarker;
import com.nokia.carbide.cpp.internal.codescanner.ui.CSPreferenceConstants;
import com.nokia.carbide.cpp.internal.featureTracker.FeatureUseTrackerConsts;
import com.nokia.carbide.cpp.internal.featureTracker.FeatureUseTrackerPlugin;

/**
 * A class to handle running CodeScanner as a project builder.
 */
public class CSBuilder extends IncrementalProjectBuilder {
	
	// IDs defined in plugin.xml
	public static final String BUILDER_ID = CSPlugin.PLUGIN_ID + ".CSBuilder";

	/**
	 * Constructor for CSAutoScanner.
	 */
	public CSBuilder () {
	}

	/**
	 * Associate this builder with a project.
	 * @param project - project in question
	 */
	public static void addBuilderToProject(IProject project) {
		// ignore closed projects
		if (!project.isOpen()) {
			return;
		}

		IProjectDescription description;
		try {
			description = project.getDescription();
		}
		catch(CoreException e) {
			e.printStackTrace();
			return;
		}

		ICommand[] commands = description.getBuildSpec();
		for (int i = 0; i < commands.length; i++) {
			// nothing to do if builder already associated with project
			if (commands[i].getBuilderName().equals(BUILDER_ID)) {
				return;
			}
		}

		// add builder to existing list of builders associated with project
		ICommand newCommand = description.newCommand();
		newCommand.setBuilderName(BUILDER_ID);
		List<ICommand> newCommands = new ArrayList<ICommand>();
		newCommands.addAll(Arrays.asList(commands));
		newCommands.add(newCommand);
		description.setBuildSpec((ICommand[])newCommands.toArray(new ICommand[newCommands.size()]));
		try {
			project.setDescription(description, new NullProgressMonitor());
		}
		catch (CoreException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.resources.IncrementalProjectBuilder#build(int, java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@SuppressWarnings("unchecked")
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor) 
		throws CoreException {
		FeatureUseTrackerPlugin.getFeatureUseProxy().startUsingFeature(FeatureUseTrackerConsts.CARBIDE_CODESCANNER);
		monitor.beginTask("Build", 100);
		monitor.worked(5);
		handleBuild(monitor);
		FeatureUseTrackerPlugin.getFeatureUseProxy().stopUsingFeature(FeatureUseTrackerConsts.CARBIDE_CODESCANNER);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.resources.IncrementalProjectBuilder#clean(org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected void clean(IProgressMonitor monitor) 
		throws CoreException {
		IProject currentProject = getProject();
		handleRemoveAllMarkers(currentProject);
	}

	/**
	 * Run CodeScanner on selected resource.
	 */
	private void handleBuild(IProgressMonitor monitor) {
		if (!autoscanProject()) {
			return;
		}

		IProject project = getProject();
		IPath filePath = project.getLocation();
		CSScanner scanner = new CSScanner();
		scanner.scanFile(project, filePath, CSScanner.ScanType.scan_other);
	}

	/**
	 * Remove all CodeScanner specific markers.
	 * @param currentProject - the currently selected project
	 */
	private void handleRemoveAllMarkers(IProject currentProject) {
		// remove CodeScanner specific markers
		CSMarker.removeAllMarkers(currentProject);
	}

	/**
	 * Check whether to run CodeScanner automatically when building a project.
	 * @return true if CodeScanner should run automatically when building a project
	 */
	private boolean autoscanProject() {
		IProject project = getProject();
		CSProjectSettings projectSettings = CSPlugin.getConfigManager().getProjectSettings(project);
		IDialogSettings cSettings = projectSettings.getDialogSettings();
		IDialogSettings pageSettings = cSettings.getSection(CSPreferenceConstants.PROPERTY_SETTINGS_ID);
		if (pageSettings == null) {
			return false;
		}
		else {
			if (pageSettings.getBoolean(CSPreferenceConstants.PROJ_SETTINGS)) {
				return pageSettings.getBoolean(CSPreferenceConstants.AUTOSCAN);
			}
			else {
				return false;
			}
		}
	}
}
