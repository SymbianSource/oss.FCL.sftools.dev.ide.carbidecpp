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

package com.nokia.carbide.cpp.internal.api.sdk;

import org.osgi.framework.Version;

import com.nokia.carbide.cpp.sdk.core.ISDKBuildInfo;

/**
 * Interface for modifying various attributes of Symbian OS SDK.
 *
 */
public interface ISymbianSDKModifier {

	/**
	 * Sets the build info for a particular builder.
	 * @param buildInfo build info
	 * @param builderId id string of a builder
	 */
	void setBuildInfo(ISDKBuildInfo buildInfo, String builderId);

	/**
	 * Add a feature supported by the SDK.
	 * @param feature supported feature
	 */
	void addSupportedFeature(Object feature);

	/**
	 * Marks the SDK as enabled or disabled.
	 * @param enable whether to enable or disable the SDK
	 */
	void setEnabled(boolean enable);

	/**
	 * Sets the unique id of a SDK.
	 * @param id id string of a SDK
	 */
	void setUniqueId(String id);

	/**
	 * Sets the OS version string of a SDK.
	 * @param osVer OS version object
	 */
	public void setOSVersion(Version osVer);

}
