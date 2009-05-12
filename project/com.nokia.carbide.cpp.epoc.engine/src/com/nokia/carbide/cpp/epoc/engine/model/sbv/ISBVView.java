/*
* Copyright (c) 2007-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.epoc.engine.model.sbv;

import java.util.Map;

import com.nokia.carbide.cpp.epoc.engine.model.IView;

/**
 * A view onto .VAR (Symbian Binary Variation) contents.  This is a parse over a single .VAR file.
 * <p>
 * Note: this view cannot be rewritten.
 * 
 * 
 */
public interface ISBVView extends IView<ISBVOwnedModel> {
	
	public static final String INCLUDE_FLAG_SET = "SET";
	public static final String INCLUDE_FLAG_PREPEND = "PREPEND";
	public static final String INCLUDE_FLAG_APPEND = "APPEND";
	
	/** Set the EXTENDS platform.
	 * @param binary variant platform may not be null, but may be "" */
	void setExtends(String platform);
	
	/** Get the EXTENDS platform. 
	 * @return binary variant platform this extends; never null, but may be the empty string if .var is invalid.  */
	String getExtends();
	
	/**
	 * Set whether or not the VIRTUAL flag defined?
	 * @param flag
	 */
	void setVirtualFlag(boolean flag);
	
	/**
	 * Get whether or not the virtual flag is defined.
	 * @return true if VIRTUAL is defined in the .var file
	 */
	boolean getVirtualFlag();
	
	/**
	 * Sets the BUILD_HRH value
	 * @param pathStr, the value of the path in the .var file
	 */
	void setBuildHRHFile(String pathStr);
	
	/**
	 * Get the BUILD_HRH value
	 * @return The string of the BUILD_HRH, null if not defined.
	 */
	String getBuildVariantHRH();
	
	/**
	 * Set the name of the VARIANT
	 * @param variantName
	 */
	void setVariantName(String variantName);
	
	/** 
	 * Get the name of the VARIANT
	 * 
	 * @return the variant name
	 */
	String getVariantName();
	
	
	/** 
	 * add a build include path
	 */
	void addBuildInclude(String arguments);
	
	/**
	 * Get the BUILD_INLCUDES
	 * @return A map of the build includes: <Include dir, flag>
	 */
	Map<String, String> getBuildIncludes();
	
	/** 
	 * add a rom build include path
	 */
	void addROMInclude(String arguments);
	
	/**
	 * Get the ROM_INLCUDES
	 * @return A map of the build includes: <Include dir, flag>
	 */
	Map<String, String> getROMBuildIncludes();
	
	
	
}
