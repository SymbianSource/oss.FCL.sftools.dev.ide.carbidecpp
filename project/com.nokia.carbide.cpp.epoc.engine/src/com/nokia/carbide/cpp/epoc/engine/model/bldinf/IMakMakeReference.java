/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.epoc.engine.model.bldinf;

import org.eclipse.core.runtime.IPath;

import java.util.List;

/**
 * IMakMakeReference is the base interface for a build file in prj_mmpfiles or
 * prj_testmmpfiles.
 * <p>
 * It provides convenience APIs for the commonly used keywords, which really
 * work on the generic attribute list.
 * <p>
 * As mentioned in the Shiner_Build_APIs_Overview, the model doesn’t validate
 * anything. The various attributes here may not apply for a given reference
 * (depending on the presence in prj_mmpfiles vs. prj_testmmpfiles, or based on
 * SDK version, like build_as_arm), but it’s up to the client to manage the list
 * properly.
 * 
 * 
 */
public interface IMakMakeReference {
	/** Tell if valid, e.g. initialized */
	boolean isValid();
	
	/** Get project relative path, never null */
	IPath getPath();
	/** Set project-relative path, never null */
	void setPath(IPath path);
	/** 
	 * Access/modify; with case-insensitive membership tests
	 */
	List<String> getAttributes();
	/**
	 * Set attributes
	 */
	void setAttributes(List<String> attributes);
	/**
	 * Check for TIDY attribute
	 * @return
	 */
	boolean isTidy();
	/**
	 * Add/remove TIDY attribute
	 * @param tidy
	 */
	void setTidy(boolean tidy);
	/**
	 * Check for MANUAL attribute
	 * @return
	 */
	boolean isManual();
	/**
	 * Add/remove MANUAL attribute
	 * @param manual
	 */
	void setManual(boolean manual);
	/**
	 * Check for SUPPORT attribute
	 * @return
	 */
	boolean isSupport();
	/**
	 * Add/remove SUPPORT attribute
	 * @param support
	 */
	void setSupport(boolean support); 
	/** 
	 * Check for BUILD_AS_ARM attribute
	 * @return
	 */
	boolean isBuildAsArm();
	/**
	 * Add/remove BUILD_AS_ARM attribute
	 * @param build_as_arm
	 */
	void setBuildAsArm(boolean build_as_arm);

	/**
	 * @return a copy of the data
	 */
	IMakMakeReference copy(); 

}
