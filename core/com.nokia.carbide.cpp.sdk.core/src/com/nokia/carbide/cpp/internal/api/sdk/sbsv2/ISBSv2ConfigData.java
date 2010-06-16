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
* Test the BldInfViewPathHelper class.
*
*/
package com.nokia.carbide.cpp.internal.api.sdk.sbsv2;

import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;

/** 
 * Data that describes the meaning of a single build configuration, as discovered by SBS
 *  
 *  @noimplement
 */
public interface ISBSv2ConfigData {

	/**
	 * Get the usable SBS build alias that can be used for the -c parameter
	 * @return the unique build alias
	 */
	String getBuildAlias();
	
	/**
	 * Get the meaning (aka dotted name, component name) fo the build alias. 
	 * Can also be used as a -c argument in SBS
	 * @return the meaning
	 */
	String getMeaning();
	
	/**
	 * Get the directory for a given SDK where binaries are output
	 * @param sdk - use null if for base configuration
	 * @return the portable OS string starting from /epoc32/
	 */
	String getReleaseDirectory();
	
	/** 
	 * TODO: This API is not yet defined.
	 * @param sdk
	 * @return
	 */
	ISBSv2ConfigPreprocessorInfo getCPPPreprocessorData();
	
	/**
	 * Get the name of the folder where executable binaries are written (typically 'debug' or 'release')
	 * @param sdk - use null for base configuration
	 * @return string of folder name
	 */
	String getTraditionalTarget();
	
	/**
	 * Get the name of the folder where executable binaries are written (component before the target)
	 * @param sdk - use null for base configuration
	 * @return string of folder name
	 */
	String getTraditionalPlatform();
	
	/**
	 * Get the SDK for which this configuration was qeuried.
	 * @return the SDK, or null if it's a base sbs configuration
	 */
	ISymbianSDK getSupportingSDK();
	
}
