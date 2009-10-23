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
package com.nokia.carbide.cpp.internal.scripting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;

import com.nokia.carbide.cpp.internal.api.sdk.SymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISDKManager;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;


public class ImportBldInfWrapper {
	
	private static final String KEY_SDKID = "KEY_SDKID";
	private static final String KEY_PLATFORM = "KEY_PLATFORM";
	private static final String KEY_TARGET = "KEY_TARGET";
	
	static public String importBldInf(String projectName, String rootDirectory,
		String bldInfFile, String[] buildComponents,
		Map<String, String>[] buildContextMap) {

		List<ISymbianBuildContext> contexts = new ArrayList<ISymbianBuildContext>();
		ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
		for (Map<String, String> currMap : buildContextMap) {
			ISymbianSDK sdk = sdkMgr.getSDK(currMap.get(KEY_SDKID), false);
			if (sdk != null) {
				if (!ImporterScritpingUtils.checkProjectIsOnSDKDrive(
						rootDirectory, sdk)) {
					// AssertTestCase.fail("!" + projectPath +
					// " is not on the same drive as SDK: " + sdkName
					// + " and could not import: " + projectName);
				}
				contexts.add(new SymbianBuildContext(sdk, currMap
						.get(KEY_PLATFORM), currMap.get(KEY_TARGET)));
			}
		}

		IProject project = ImporterScritpingUtils.importINF(projectName,
				bldInfFile, contexts, 0, false);

		if (project == null)
			return null;
		else
			return projectName;
	}
	
	static public Map<String, String> createSymbianBuildContext(String sdkID, String platform, String target){
		Map<String,String> mp = new HashMap<String, String>();

		mp.put(KEY_SDKID, sdkID);
		mp.put(KEY_PLATFORM, platform);
		mp.put(KEY_TARGET, target);
		
		return mp;
	}
	
	static public boolean buildProject(String projectName){
		// TODO:
		return false;
	}
	
	static public boolean cleanProject(int cleanLevel){
		// TODO:
		return false;
	}
	
	static public boolean changeBuildConfiguration(Map<String, String> buildContextMap){
		// TODO:
		return false;
	}
	
}
