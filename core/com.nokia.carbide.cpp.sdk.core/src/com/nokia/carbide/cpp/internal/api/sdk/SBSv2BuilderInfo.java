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
package com.nokia.carbide.cpp.internal.api.sdk;

import java.util.HashMap;

import org.eclipse.cdt.core.settings.model.ICStorageElement;

import com.nokia.carbide.cpp.internal.api.sdk.ISBSv2BuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;

public class SBSv2BuilderInfo implements ISBSv2BuildConfigInfo {

	HashMap<String, String> sbsv2ConfigDataMap = new HashMap<String, String>();
	
	ISymbianBuildContext context;
	
	public SBSv2BuilderInfo(ISBSv2BuildContext context) {
		String buildAlias = context.getSBSv2Alias() != null ? context.getSBSv2Alias() : "";
		sbsv2ConfigDataMap.put(ISBSv2BuildConfigInfo.ATTRIB_SBSV2_BUILD_ALIAS, buildAlias);
		sbsv2ConfigDataMap.put(ISBSv2BuildConfigInfo.ATRRIB_CONFIG_BASE_PLATFORM, context.getPlatformString());
		sbsv2ConfigDataMap.put(ISBSv2BuildConfigInfo.ATTRIB_CONFIG_TARGET, context.getTargetString());
		sbsv2ConfigDataMap.put(ISBSv2BuildConfigInfo.ATTRIB_SBSV2_CONFIG_DISPLAY_STRING, context.getDisplayString());
		sbsv2ConfigDataMap.put(ISBSv2BuildConfigInfo.ATTRIB_SBSV2_SDK_ID, context.getSDK().getUniqueId());
		this.context = context;
	}
	
	/**
	 * Using the default constructor presumes the sbsv2ConfigDataMap will be filled out by subsequent calls to loadFromStorage(ICStorageElement rootStorage)
	 * or setSBSv2Setting(String id, String value)
	 */
	public SBSv2BuilderInfo(){
		
	}
	
	public void loadFromStorage(ICStorageElement rootStorage) {
			
		String value = rootStorage.getAttribute(ATRRIB_CONFIG_BASE_PLATFORM);
		if (value != null) {
			sbsv2ConfigDataMap.put(ATRRIB_CONFIG_BASE_PLATFORM, value);
		}
		
		value = rootStorage.getAttribute(ATTRIB_CONFIG_TARGET);
		if (value != null) {
			sbsv2ConfigDataMap.put(ATTRIB_CONFIG_TARGET, value);
		}
		
		value = rootStorage.getAttribute(ATTRIB_SBSV2_BUILD_ALIAS);
		if (value != null) {
			sbsv2ConfigDataMap.put(ATTRIB_SBSV2_BUILD_ALIAS, value);
		}
		
		value = rootStorage.getAttribute(ATTRIB_SBSV2_CONFIG_DISPLAY_STRING);
		if (value != null) {
			sbsv2ConfigDataMap.put(ATTRIB_SBSV2_CONFIG_DISPLAY_STRING, value);
		}
		
		value = rootStorage.getAttribute(ATTRIB_SBSV2_SDK_ID);
		if (value != null) {
			sbsv2ConfigDataMap.put(ATTRIB_SBSV2_SDK_ID, value);
		}
	}
	
	public void saveToStorage(ICStorageElement rootStorage) {
		
		String value = sbsv2ConfigDataMap.get(ATRRIB_CONFIG_BASE_PLATFORM);
		if (value != null && value.trim().length() > 0){
			rootStorage.setAttribute(ATRRIB_CONFIG_BASE_PLATFORM, value);
		}
		
		value = sbsv2ConfigDataMap.get(ATTRIB_CONFIG_TARGET);
		if (value != null && value.trim().length() > 0){
			rootStorage.setAttribute(ATTRIB_CONFIG_TARGET, value);
		}
		
		value = sbsv2ConfigDataMap.get(ATTRIB_SBSV2_BUILD_ALIAS);
		if (value != null && value.trim().length() > 0){
			rootStorage.setAttribute(ATTRIB_SBSV2_BUILD_ALIAS, value);
		}
		
		value = sbsv2ConfigDataMap.get(ATTRIB_SBSV2_CONFIG_DISPLAY_STRING);
		if (value != null && value.trim().length() > 0){
			rootStorage.setAttribute(ATTRIB_SBSV2_CONFIG_DISPLAY_STRING, value);
		}
		
		value = sbsv2ConfigDataMap.get(ATTRIB_SBSV2_SDK_ID);
		if (value != null && value.trim().length() > 0){
			rootStorage.setAttribute(ATTRIB_SBSV2_SDK_ID, value);
		}

	}

	/**
	 * @see {@link ISBSv2BuildConfigInfo} for id values
	 */
	public String getSBSv2Setting(String id) {
		return sbsv2ConfigDataMap.get(id);
	}

	/**
	 * @see {@link ISBSv2BuildConfigInfo} for id values
	 */
	public void setSBSv2Setting(String id, String value) {
		sbsv2ConfigDataMap.put(id, value);
	}
	
}
