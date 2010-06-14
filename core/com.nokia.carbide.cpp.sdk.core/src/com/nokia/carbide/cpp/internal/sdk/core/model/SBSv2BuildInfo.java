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
*/

package com.nokia.carbide.cpp.internal.sdk.core.model;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cpp.internal.api.sdk.ISBSv2BuildInfo;
import com.nokia.carbide.cpp.internal.api.sdk.SBSv2Utils;
import com.nokia.carbide.cpp.sdk.core.ISDKManager;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.cpp.internal.api.utils.core.PathUtils;

/**
 * SBSv2 specific build information.
 *
 */
public class SBSv2BuildInfo implements ISBSv2BuildInfo {

	private ISymbianSDK sdk;
	private boolean wasScanned = false;
	private Map<String, List<String>> cachedPlatformMacros = new HashMap<String, List<String>>();

	public SBSv2BuildInfo(ISymbianSDK sdk) {
		this.sdk = sdk;
	}

	@Override
	public void clearPlatformMacros() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ISymbianBuildContext> getAllBuildConfigurations() {
		return SBSv2Utils.getAllSBSv2BuildContexts(sdk);
	}

	@Override
	public List<ISymbianBuildContext> getFilteredBuildConfigurations() {
		// This is probably a bug, but the filtering only uses SBSv1 preferences if SBSv1 is enabled...
		List<ISymbianBuildContext> filteredContexts;
		if (SBSv2Utils.enableSBSv2Support()) {
			filteredContexts = SBSv2Utils.getFilteredSBSv2BuildContexts(sdk);
		} else {
			// be optimistic in this case... SBSv3? ;)
			filteredContexts = getAllBuildConfigurations();
		}
		return filteredContexts;
	}

	public List<String> getPlatformMacros(String platform) {
		// TODO: Need to get from Raptor Query
		// TODO: Are these the metadata macros or compiler macros?
		List<String> platformMacros = cachedPlatformMacros.get(platform.toUpperCase());
		if (platformMacros == null) {
			synchronized (cachedPlatformMacros) {
				ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
				platformMacros = sdkMgr.getSymbianMacroStore().getPlatformMacros(sdk.getOSVersion(), "", null, platform);
				cachedPlatformMacros.put(platform.toUpperCase(), platformMacros);
			}
		}
		return platformMacros;
	}

	/**
	 * Get the full path to the prefix file defined under \epoc32\tools\variant\variant.cfg
	 * @return A path object, or null if the variant.cfg does not exist. This routine does not check to see if the returned path exists.
	 */
	public IPath getPrefixFromVariantCfg(){
		File epocRoot = new File(sdk.getEPOCROOT());
		File variantCfg;
		variantCfg = new File(epocRoot, SymbianSDK.SPP_VARIANT_CFG_FILE);
		if (!variantCfg.exists()) {
			variantCfg = new File(epocRoot, SymbianSDK.VARIANT_CFG_FILE);
			if (!variantCfg.exists())
				return null;
		}
		
		String variantDir = null;
		String variantFile = null;
		try {
			char[] cbuf = new char[(int) variantCfg.length()];
			Reader reader = new FileReader(variantCfg);
			reader.read(cbuf);
			reader.close();
			String[] lines = new String(cbuf).split("\r\n|\r|\n");
			for (int i = 0; i < lines.length; i++) {
				// skip comments and blank lines
				String line = SymbianSDK.removeComments(lines[i]);
				if (line.matches("\\s*#.*") || line.trim().length() == 0) 
					continue;
				
				// parse the variant line, which is an EPOCROOT-relative
				// path to a bldvariant.hrh file
				Matcher matcher = SymbianSDK.VARIANT_HRH_LINE_PATTERN.matcher(line);
				if (matcher.matches()) {
					variantDir = matcher.group(1);
					variantFile = matcher.group(3); 
					File variantFullPathFile = new File(epocRoot, variantDir + File.separator + variantFile);
					IPath variantFilePath = new Path(PathUtils.convertPathToUnix(variantFullPathFile.getAbsolutePath()));
					return variantFilePath;
				}
			}
		} catch (IOException e) {
		}
		
		return null; // can't find the file...
	}

	public List<String> getTargetTypeMacros(String targettype) {
		// this is based on \epoc32\tools\trgtype.pm which changes from
		// OS version to OS version, but largely remains constant with
		// regards to the basic type.
		List<String> macros = new ArrayList<String>();
		
		// if it's not one of these then it's a DLL
		if (targettype.compareToIgnoreCase("EPOCEXE") == 0) {
			macros.add("__EXEDLL__");
		} else if (targettype.compareToIgnoreCase("EXEDLL") == 0) {
			macros.add("__EXEDLL__");
		} else if (targettype.compareToIgnoreCase("EXE") == 0) {
			macros.add("__EXE__");
		} else if (targettype.compareToIgnoreCase("EXEXP") == 0) {
			macros.add("__EXE__");
		} else if (targettype.compareToIgnoreCase("IMPLIB") == 0) {
			macros.add("__IMPLIB__");
		} else if (targettype.compareToIgnoreCase("KLIB") == 0) {
			macros.add("__LIB__");
		} else if (targettype.compareToIgnoreCase("LIB") == 0) {
			macros.add("__LIB__");
		} else {
			macros.add("__DLL__");
		}
		return macros;
	}

	public boolean isPreviouslyScanned() {
		return wasScanned;
	}

	public void setPreviouslyScanned(boolean wasScanned) {
		this.wasScanned = wasScanned;
	}

}
