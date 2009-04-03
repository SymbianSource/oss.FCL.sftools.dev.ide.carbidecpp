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
package com.nokia.carbide.cpp.sdk.core;

import org.osgi.framework.Version;

/**
 * Stores information about single RVCT
 * (=RealView Compiler Tools) toolchain 
 * installation
 */
public interface IRVCTToolChainInfo {
	
		/**
		 * Gets RVCT toolchain installation directory.
		 * @return Returns the rvctToolBinariesDirectory.
		 */
		public String getRvctToolBinariesDirectory();

		/**
		 * Gets RVCT toolchain version.
		 * @return Returns the rvctToolsVersion in a Version object.
		 */
		public Version getRvctToolsVersion();

}
