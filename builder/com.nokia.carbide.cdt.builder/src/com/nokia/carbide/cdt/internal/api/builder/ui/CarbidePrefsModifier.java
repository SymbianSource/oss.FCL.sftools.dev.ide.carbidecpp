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
package com.nokia.carbide.cdt.internal.api.builder.ui;

import com.nokia.carbide.cdt.builder.extension.ICarbidePrefsModifier;
import com.nokia.carbide.cpp.internal.api.sdk.BuildArgumentsInfo;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv1BuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;

public class CarbidePrefsModifier implements ICarbidePrefsModifier {

	public String getValue(ISymbianBuildContext context,  String prefID) {
		
		if (prefID.equals(ICarbidePrefsModifier.ABLD_BUILD_ARG_SETTING)){
			if (context instanceof ISBSv1BuildContext){
				BuildArgumentsInfo info = ((ISBSv1BuildContext)context).getBuildArgumentsInfoCopy();
				return info.getAbldBuildArgs();
			}
		}
		
		return null;
	}

	public void setValue(ISymbianBuildContext context, String arg,  String prefID) {
		
		if (prefID.equals(ICarbidePrefsModifier.ABLD_BUILD_ARG_SETTING)){
			if (context instanceof ISBSv1BuildContext){
				BuildArgumentsInfo info = ((ISBSv1BuildContext)context).getBuildArgumentsInfoCopy();
				info.abldBuildArgs = arg;
				((ISBSv1BuildContext)context).setBuildArgumentsInfo(info);
			}
		}
	}

}
