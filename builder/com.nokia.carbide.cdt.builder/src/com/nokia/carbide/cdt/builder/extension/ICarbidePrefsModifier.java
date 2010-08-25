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
package com.nokia.carbide.cdt.builder.extension;

import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;

/**
 * Allows those using the Carbide Preferences Modifier extension point to to read
 * and/or modify certain project preferences
 * 
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 * @since 3.0
 *
 */
public interface ICarbidePrefsModifier {
	
	/**
	 * @deprecated - limited support for abld, String will be removed in the future so reference
	 * with your own local String.
	 * @since 3.0
	 */
	final String ABLD_BUILD_ARG_SETTING = "ABLD_BUILD_ARG_SETTING";
	
	/**
	 * Get the project configuration setting by ID.
	 * @param ISymbianBuildContext
	 * @param prefID
	 * @return The String value. Or null if the the pref id is unknown or no longer in use.
	 * @since 3.0
	 */
	String getConfigurationValue(ICarbideBuildConfiguration newParam, String prefID);
	
	/**
	 * Set the project configuration setting by ID.
	 * @param ISymbianBuildContext
	 * @param String - the argument value
	 * @param prefID
	 * @since 3.0
	 */
	void setConfigurationValue(ICarbideBuildConfiguration context, String arg, String prefID);
	
	/**
	 * Test whether or not a particular prefID is supported for read/write.
	 * The ID tests support first by the ID, then, if configuration specific, by the ISymbianBuildContext
	 * @param config
	 * @param prefID
	 * @return true if the prefID is supported, false otherwise.
	 */
	boolean isSupportedConfigurationPrefId(ICarbideBuildConfiguration config, String prefID);
	
}
