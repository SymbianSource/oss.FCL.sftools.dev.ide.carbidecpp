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
*/

package com.nokia.carbide.cpp.sdk.core;

import java.util.List;

import org.eclipse.core.runtime.IPath;

/**
 * Interface for build related information of a Symbian SDK.
 * @since 3.0
 */
public interface ISDKBuildInfo {

	/**
	 * Returns all available build configurations for a Symbian SDK.
	 * @return list of ISymbianBuildContext
	 */
	List<ISymbianBuildContext> getAllBuildConfigurations();

	/**
	 * Returns platform-filtered build configurations for a Symbian SDK.
	 * @return list of ISymbianBuildContext
	 */
	List<ISymbianBuildContext> getFilteredBuildConfigurations();

	/**
	 * Get the full path to the prefix file defined under \epoc32\tools\variant\variant.cfg
	 * @return A path object, or null if the variant.cfg does not exist. This routine does not check to see if the returned path exists.
	 */
	public IPath getPrefixFromVariantCfg();

}
