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
*/
package com.nokia.carbide.cpp.internal.sdk.core.model;

import org.osgi.framework.Version;

import com.nokia.carbide.cpp.sdk.core.IRVCTToolChainInfo;

/**
 * Stores information about single RVCT
 * (=RealView Compiler Tools) toolchain 
 * installation
 */
public class RVCTToolChainInfo implements IRVCTToolChainInfo {
	
	/**
	 * Toolchain installation directory location.
	 */
	private String rvctToolBinariesDirectory = null;
	
	/**
	 * Toolchain version.
	 */
	private Version rvctToolsVersion = null;
	
	/**
	 * Default constructor.
	 * Cannot be constructed outside of this plugin.
	 */
	public RVCTToolChainInfo(){
		rvctToolBinariesDirectory = new String("");
		rvctToolsVersion = new Version("0.0");		
	}


	public String getRvctToolBinariesDirectory() {
		return rvctToolBinariesDirectory;
	}


	public void setRvctToolBinariesDirectory(String rvctToolBinariesDirectory) {
		this.rvctToolBinariesDirectory = rvctToolBinariesDirectory;
	}


	public Version getRvctToolsVersion() {
		return rvctToolsVersion;
	}
	
	/**
	 * Sets RVCT toolchain version.
	 * Setters cannot be used outside this package.
	 * @param rvctToolsVersion The rvctToolsVersion to set.
	 */
	public void setRvctToolsVersion(Version rvctToolsVersion) {
		this.rvctToolsVersion = rvctToolsVersion;
	}
}
