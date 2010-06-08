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

/**
 * Interface for build related information of a Symbian SDK.
 * 
 */
public interface ISDKBuildInfo {

	/**
	 * Returns platform-filtered build configurations for a Symbian SDK.
	 * @param sdk Symbian SDK
	 * @return list of ISymbianBuildContext
	 */
	List<ISymbianBuildContext> getFilteredBuildConfigurations(ISymbianSDK sdk);

	/**
	 * Returns all available build configurations for a Symbian SDK.
	 * @param sdk Symbian SDK
	 * @return list of ISymbianBuildContext
	 */
	List<ISymbianBuildContext> getAllBuildConfigurations(ISymbianSDK sdk);

}
