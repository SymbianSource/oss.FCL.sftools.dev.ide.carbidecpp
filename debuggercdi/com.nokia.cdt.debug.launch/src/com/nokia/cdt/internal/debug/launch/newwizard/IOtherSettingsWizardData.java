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

/**
 *
 */
public interface IOtherSettingsWizardData {

	enum EBuildBeforeLaunchOption {
		ALWAYS,
		NEVER,
		USE_WORKSPACE_SETTING,
	}

	/**
	 * Set the build before launch option
	 * @param option EBuildBeforeLaunchOption
	 */
	void setBuildBeforeLaunchOption(EBuildBeforeLaunchOption option);

	/**
	 * Get the build before launch option
	 * @return EBuildBeforeLaunchOption
	 */
	EBuildBeforeLaunchOption getBuildBeforeLaunch();

	/**
	 * Return the workspace setting
	 * @return boolean
	 */
	boolean isWorkspaceBuildBeforeLaunch();

	/**
	 * Return whether to build before launch for this launch
	 * @return boolean
	 */
	boolean isCurrentBuildBeforeLaunch();

}
