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
package com.nokia.carbide.cpp.internal.sdk.core.scripting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nokia.carbide.cpp.sdk.core.ISDKManager;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;

public class SymbianSDKs {
	
	private static final String KEY_SDK_ID = "KEY_SDK_ID";
	private static final String KEY_SDK_EPOCROOT = "KEY_SDK_EPOCROOT";
	private static final String KEY_SDK_NAME = "KEY_SDK_NAME";
	
	static private ISDKManager getSDKManager(){
		return SDKCorePlugin.getSDKManager();
	}
		
	static public Map<?, ?>[] getSDKMapList(){
		ArrayList<Map<String,String>> sdkMapArray = new ArrayList<Map<String,String>>();
		List<ISymbianSDK> sdkList = getSDKManager().getSDKList();
		for (ISymbianSDK sdk : sdkList){
			Map<String,String> mp = new HashMap<String, String>();
			mp.put(KEY_SDK_ID, sdk.getUniqueId());
			mp.put(KEY_SDK_EPOCROOT, sdk.getEPOCROOT());
			mp.put(KEY_SDK_NAME, sdk.getName());
			sdkMapArray.add(mp);
		}
		
		
		return sdkMapArray.toArray(new HashMap<?, ?>[sdkMapArray.size()]);
	}
	
}
