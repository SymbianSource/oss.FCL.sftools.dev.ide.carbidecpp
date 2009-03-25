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

package com.nokia.carbide.cpp.internal.codescanner.config;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.DialogSettings;

/**
 * A class for handling project specific CodeScanner settings.
 */
public class CSProjectSettings {

	// name of project settings folder
	protected static final String CS_CONFIG_SETTINGS_FOLDER = ".settings";

	// name of CodeScanner project settings file
	protected static final String CS_CONFIG_SETTINGS_FILE = "carbide_cs_settings.xml";

	// private members
	private IProject project = null;
	private String settingsFileName = null;
	private DialogSettings dialogSettings = null;

	/**
	 * Create an instance of CSProjectSettings.
	 * @param project - the project associated with this instance of CSProjectSettings
	 */
	public CSProjectSettings(IProject project) {
		super();
		this.project = project;
		settingsFileName = getSettingsFileName();
		File settingsFile = new File(settingsFileName);
		dialogSettings = new DialogSettings("root");
		if (settingsFile.exists()) {
			try {
				dialogSettings.load(settingsFileName);
			}
			catch (IOException eIO){
				eIO.printStackTrace();
			}
		}
	}
		
	/**
	 * Get the IProject object associated with this project.
	 * @return IProject object
	 */
	public IProject getProject() {
		return project;
	}

	/**
	 * Get the DialogSettings object associated with this project.
	 * @return DialogSettings object
	 */
	public DialogSettings getDialogSettings() {
		return dialogSettings;
	}
	
	/**
	 * Save the DialogSettings object associated with this project to file.
	 * @return true on success
	 */
	public boolean saveDialogSettings() {
		boolean success = true;
		try {
			dialogSettings.save(settingsFileName);			
		}
		catch (IOException eIO){
			eIO.printStackTrace();
			success = false;
		}
		return success;
	}

	/**
	 * Get the project specific CodeScanner settings file name.
	 * @return file name of the settings file.
	 */
	private String getSettingsFileName() {
		final IFolder settingsFolder = project.getFolder(new Path(CS_CONFIG_SETTINGS_FOLDER));
		if (!settingsFolder.exists()) {
			settingsFolder.getLocation().toFile().mkdirs();
		}
		return (settingsFolder.getLocation().append(CS_CONFIG_SETTINGS_FILE).toString());
	}

}
