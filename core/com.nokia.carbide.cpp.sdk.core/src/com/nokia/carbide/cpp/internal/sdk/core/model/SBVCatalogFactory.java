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

import java.io.File;

import com.nokia.carbide.cpp.internal.api.sdk.ISBSv1BuildInfo;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv2BuildInfo;
import com.nokia.carbide.cpp.sdk.core.ISBVCatalog;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuilderID;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

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
		IPath sdkIncludePath;
		ISBSv1BuildInfo sbsv1BuildInfo = (ISBSv1BuildInfo)sdk.getBuildInfo(ISymbianBuilderID.SBSV1_BUILDER);
		ISBSv2BuildInfo sbsv2BuildInfo = (ISBSv2BuildInfo)sdk.getBuildInfo(ISymbianBuilderID.SBSV2_BUILDER);
		if (sbsv1BuildInfo != null) {
			sdkIncludePath = sbsv1BuildInfo.getIncludePath(sdk);
		} else if (sbsv2BuildInfo != null) {
			sdkIncludePath = sbsv2BuildInfo.getIncludePath(sdk);
		} else {
			sdkIncludePath = new Path(sdk.getEPOCROOT()).append("include");
		}
		SBVCatalog catalog = new SBVCatalog(new Path(sdk.getEPOCROOT()), sdkIncludePath);
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
