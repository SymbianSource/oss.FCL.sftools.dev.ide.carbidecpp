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


package com.nokia.carbide.cpp.internal.api.sdk.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDKFeatures;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.carbide.template.engine.ITemplate;

public class TemplateUtils {
		
	private static final String SDK_FEATURE_DELIM = ";"; //$NON-NLS-1$
	
	// Template flags. These are the template flags used for filtering
	// As of Carbide 3.0, we no longer use OS/SDK version or family name
	// to filter templates.
	private static final String FEATURE_SUPPORTS_AVKON = "supportsAvkon"; //$NON-NLS-1$

	public static final String PROJECT_NAME = "projectName"; //$NON-NLS-1$
	public static final String BASE_NAME = "baseName"; //$NON-NLS-1$
	public static final String BASE_NAME_UPPER = "baseNameUpper"; //$NON-NLS-1$
	public static final String BASE_NAME_LOWER = "baseNameLower"; //$NON-NLS-1$
	public static final String PROJECT_LOCATION = "location"; //$NON-NLS-1$
	public static final String USE_DEFAULT_LOCATION = "useDefaultLocation"; //$NON-NLS-1$

	/**
	 * @param symbianSDK
	 * @param template
	 * @return whether this sdk matches the template
	 * template filter arguments
	 */
	public static boolean sdkMatchesTemplate(ISymbianSDK symbianSDK, ITemplate template) {
		String filterArgs = template.getFilterArguments();
		Set supportedFeatures = symbianSDK.getSupportedFeatures();
		if (filterArgs != null && filterArgs.length() > 0) {
			String[] strings = filterArgs.split(SDK_FEATURE_DELIM);
			if (strings.length > 0) {
				// check for avkon support
				boolean hasAvkon = false;
				if (supportedFeatures.contains(ISymbianSDKFeatures.IS_AVKON_SUPPORTED)){
					
					for (String templateFeature : strings){
						if (templateFeature.equals(FEATURE_SUPPORTS_AVKON)){
							hasAvkon = true; // This template requires avkon support and it's there
							break;
						}
					}
				}
				if (!hasAvkon)
					return false;  // This template requires avkon support but is not there. Don't show
			}
		}
		
		return true;
	}
	
	public static List<ISymbianSDK> getEnabledSDKs() {
		List<ISymbianSDK> enabledSDKList = new ArrayList<ISymbianSDK>();
		
		List<ISymbianSDK> sdkList = SDKCorePlugin.getSDKManager().getSDKList();
		if (sdkList != null) {
			// Only add SDKs that are enabled
			for (ISymbianSDK currSDK : sdkList){
				if (currSDK.isEnabled()){
					enabledSDKList.add(currSDK);
				}
			}
		}
		
		return enabledSDKList;
	}

}
