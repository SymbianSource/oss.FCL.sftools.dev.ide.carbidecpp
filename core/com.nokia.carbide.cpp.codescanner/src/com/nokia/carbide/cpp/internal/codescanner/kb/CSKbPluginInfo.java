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

package com.nokia.carbide.cpp.internal.codescanner.kb;

/**
 * A class for handling information of a knowledge base rules contributing plugin.
 *
 */
public class CSKbPluginInfo {

	private String pluginID;
	private String version;

	/**
	 * Constructor.
	 * @param pluginID - unique identifier of a plugin
	 * @param version - plugin version
	 */
	public CSKbPluginInfo(String pluginID, String version) {
		this.pluginID = pluginID;
		this.version = version;
	}

	/**
	 * Retrieve version of a plugin.
	 * @return version of a plugin
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * Retrieve ID of a plugin.
	 * @return ID of a plugin
	 */
	public String getpluginID() {
		return this.pluginID;
	}

}
