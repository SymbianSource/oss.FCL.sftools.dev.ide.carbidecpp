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
package com.nokia.carbide.cpp.internal.qt.core;

import java.io.File;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IFilter;

import com.nokia.carbide.cpp.internal.api.sdk.ISBSv1BuildInfo;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuilderID;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;

public abstract class QtFilter implements IFilter {

	protected File getLibFile(ISymbianBuildContext context) {
		ISymbianSDK  sdk = context.getSDK();
		IPath releaseRoot;
		ISBSv1BuildInfo sbsv1BuildInfo = (ISBSv1BuildInfo)context.getSDK().getBuildInfo(ISymbianBuilderID.SBSV1_BUILDER);
		if (sbsv1BuildInfo != null) {
			releaseRoot = sbsv1BuildInfo.getReleaseRoot(sdk);
		} else {
			releaseRoot = new Path(sdk.getEPOCROOT()).append("epoc32/release");
		}
		if (context.getPlatformString().equals(ISymbianBuildContext.EMULATOR_PLATFORM)) {
			return releaseRoot.append(context.getPlatformString()).
			append(context.getTargetString()).append("QtCore.dll").toFile(); //$NON-NLS-1$
		} else {
			// for non-emulator targets, check in the armv5/lib directory (ARMV5 and GCCE)
			return releaseRoot.append("armv5/lib/QtCore.lib").toFile(); //$NON-NLS-1$
		}
	}
	
	public abstract boolean select(Object toTest);

}
