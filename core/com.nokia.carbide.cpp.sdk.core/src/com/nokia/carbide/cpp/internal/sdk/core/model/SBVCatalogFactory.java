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

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cpp.sdk.core.ISBVCatalog;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;

/**
 * This factory creates SBV catalogs. 
 *
 */
public class SBVCatalogFactory {
	
	/**
	 * Create a catalog of SBV information for the .var files detected 
	 * in the given SDK's directory.  The catalog is regenerated from scratch.
	 * @param sdk non-null SDK to scan
	 * @return a catalog, never null
	 */
	public static ISBVCatalog createCatalog(ISymbianSDK sdk) {
		SBVCatalog catalog = new SBVCatalog(new Path(sdk.getEPOCROOT()), sdk.getIncludePath());
		return catalog;
	}

	/**
	 * Create a catalog of BSV information for the VAR files detected 
	 * in the given directory.  The catalog is regenerated from scratch.
	 * @param sdkPath path to an SDK root
	 * @param sdkIncludePath path to the SDK's include directory
	 * @return a catalog, never null
	 */
	public static ISBVCatalog createCatalog(IPath sdkPath, IPath sdkIncludePath) {
		SBVCatalog catalog = new SBVCatalog(sdkPath, sdkIncludePath);
		return catalog;
	}

}
