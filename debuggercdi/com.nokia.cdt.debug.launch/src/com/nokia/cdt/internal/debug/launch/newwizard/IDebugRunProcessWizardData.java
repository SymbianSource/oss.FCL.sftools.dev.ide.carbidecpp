/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.cdt.internal.debug.launch.newwizard;

import java.util.List;

import org.eclipse.core.runtime.IPath;

/**
 *
 */
public interface IDebugRunProcessWizardData {

	enum EExeSelection {
		USE_PROJECT_EXECUTABLE,
		USE_REMOTE_EXECUTABLE,
		ATTACH_TO_PROCESS,
	};
	
	/**
	 * Set the SIS path string
	 * @param sisPath String
	 */
	void setSisPath(String sisPath);
	
	/**
	 * Return the SIS path string
	 * @return String
	 */
	String getSisPath();

	/**
	 * Return the install option
	 * @return boolean
	 */
	boolean isInstallPackage();

	/**
	 * Set the install option
	 * @param installPackage
	 */
	void setInstallPackage(boolean installPackage);
	
	/**
	 * Return the exe selection
	 * @return EExeSelection
	 */
	EExeSelection getExeSelection();

	/**
	 * Set exe selection
	 * @param exeSelection EExeSelection
	 */
	void setExeSelection(EExeSelection exeSelection);
	
	/**
	 * Derive the exe path using the exe selection
	 * @return IPath
	 */
	IPath getExePath();

	/**
	 * Return all exes
	 * @return List<IPath>
	 */
	List<IPath> getExes();

	/**
	 * Return a list of launchable exes
	 * @return List<IPath>
	 */
	List<IPath> getLaunchableExes();

	/**
	 * Return exe selection path value
	 * @return IPath
	 */
	IPath getExeSelectionPath();

	/**
	 * Set exe selection path value
	 * @param path IPath
	 */
	void setExeSelectionPath(IPath path);

	/**
	 * Get default executable
	 * @return IPath
	 */
	IPath getDefaultExecutable();

	/** 
	 * Return whether we can detect that the current connection is Sys TRK.
	 * @return Boolean.TRUE if Sys TRK, Boolean.FALSE if App TRK, or <code>null</code> if unknown
	 */
	Boolean isSysTRKConnection();

	/**
	 * Return whether Carbide is running in an internal layout.
	 * @return true if the installation includes known internal-only plugins
	 */
	boolean isInternalLayout();

	/**
	 * Return whether the installing is required for this launch
	 * @return boolean
	 */
	boolean requiresInstallPackage();

}
