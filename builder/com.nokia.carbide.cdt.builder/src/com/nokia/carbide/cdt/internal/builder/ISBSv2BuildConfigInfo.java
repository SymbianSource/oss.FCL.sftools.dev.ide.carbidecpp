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
package com.nokia.carbide.cdt.internal.builder;

/**
 * Interface to SBSv2 build configuration specific data
 */
public interface ISBSv2BuildConfigInfo {

	// Data attribute id's saved in .cproject file
	public final static String ATRRIB_CONFIG_BASE_PLATFORM = "CONFIG_BASE_PLATFORM"; //$NON-NLS-1$ 
	public final static String ATTRIB_CONFIG_TARGET = "CONFIG_TARGET"; //$NON-NLS-1$ 
	public final static String ATTRIB_SBSV2_BUILD_ALIAS = "SBSV2_BUILD_ALIAS"; //$NON-NLS-1$ 
	public final static String ATTRIB_SBSV2_CONFIG_DISPLAY_STRING = "SBSV2_CONFIG_DISPLAY_STRING"; //$NON-NLS-1$ 
	public final static String ATTRIB_SBSV2_SDK_ID = "ATTRIB_SBSV2_SDK_ID";
	
	/** Retrieve a specfic .cproject SBSv2 data value from a given ID */
	String getSBSv2Setting(String id);
	
	/** 
	 * Set a specific SBSv2 configuration specific data value
	 * @param id
	 * @param value
	 */
	void setSBSv2Setting(String id, String value);
}
