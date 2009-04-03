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

import org.osgi.framework.Version;

import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.carbide.template.engine.ITemplate;

public class TemplateUtils {
	
	private static final String FAMILY_DELIM = ":"; //$NON-NLS-1$
	private static final String RANGE_DELIM = "-"; //$NON-NLS-1$
	private static final String VERSION_DELIM = ";"; //$NON-NLS-1$

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
	 * template filter arguments:= framework[:versionSpec]
	 * versionSpec is a list of version or versionRange delimited by ;
	 * versionRange is a minVersion and maxVersion delimited by - 
	 */
	public static boolean sdkMatchesTemplate(ISymbianSDK symbianSDK, ITemplate template) {
		Version sdkVersion = symbianSDK.getSDKVersion();
		String family = symbianSDK.getFamily(); // ??? is this S60, UIQ, etc. ???
		return sdkMatchesTemplate(sdkVersion, family, template);
	}
	
	
	private static boolean isSameFamily(String f1, String f2) {
		if (f1.equalsIgnoreCase(f2))
			return true;
		
		if ((f1.equalsIgnoreCase(ISymbianSDK.S60_FAMILY_ID) &&
				f2.equalsIgnoreCase(ISymbianSDK.SERIES60_FAMILY_ID)) ||
				(f2.equalsIgnoreCase(ISymbianSDK.S60_FAMILY_ID) &&
						f1.equalsIgnoreCase(ISymbianSDK.SERIES60_FAMILY_ID)))
			return true;
		
		return false;
	}
	
	/**
	 * @param symbianSDK
	 * @param template
	 * @return whether this sdk matches the template
	 * template filter arguments:= family[:versionSpec]
	 * versionSpec is a list of version or versionRange delimited by ;
	 * versionRange is a minVersion and maxVersion delimited by - 
	 */
	public static boolean sdkMatchesTemplate(Version sdkVersion, String family, ITemplate template) {
		String filterArgs = template.getFilterArguments();
		if (filterArgs != null) {
			String[] strings = filterArgs.split(FAMILY_DELIM);
			if (strings.length > 0) {
				if (!isSameFamily(family, strings[0]))
					return false;
				
				if (strings.length > 1) {
					String[] versions = strings[1].split(VERSION_DELIM);
					for (int i = 0; i < versions.length; i++) {
						String[] versionRange = versions[i].split(RANGE_DELIM);
						if (versionRange.length == 1) {
							Version version = Version.parseVersion(versionRange[0]);
							if (sdkVersion.equals(version))
								return true;
						}
						else if (versionRange.length > 1) {
							Version minVersion = Version.parseVersion(versionRange[0]);
							Version maxVersion = Version.parseVersion(versionRange[1]);
							if (sdkVersion.compareTo(minVersion) >= 0 &&
									sdkVersion.compareTo(maxVersion) <= 0)
								return true;
						}
					}
					return false;
				}
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
