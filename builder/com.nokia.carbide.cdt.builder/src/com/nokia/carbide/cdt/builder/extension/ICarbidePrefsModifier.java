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

import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;

/**
 * Allows those using the Carbide Preferences Modifier extension point to to read
 * and/or modify certain project preferences
 * 
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 * @deprecated - abld support is deprecated
 *
 */
public interface ICarbidePrefsModifier {
	
	/**
	 * Get the abld build setting from the Carbide build config arguments preference.
	 * This only works with the SBSv1 buider and has no effect on other builders.
	 * @param newParam
	 * @return The String value. Or null if the the pref is not supported under the current context.
	 * @deprecated- abld support is deprecated
	 */
	String getAbdlBuildArg(ISymbianBuildContext newParam);
	
	/**
	 * Set the abld build setting from the Carbide build config arguments preference.
	 * This only works with the SBSv1 buider and has no effect on other builders.
	 * @param context
	 * @param arg
	 * @deprecated - abld support is deprecated
	 */
	void setAbldBuildArg(ISymbianBuildContext context, String arg);
}
