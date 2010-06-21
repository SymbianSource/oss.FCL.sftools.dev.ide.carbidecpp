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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cpp.internal.api.sdk.BuildContextSBSv2;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv2BuildInfo;
import com.nokia.carbide.cpp.internal.api.sdk.SBSv2Utils;
import com.nokia.carbide.cpp.internal.api.sdk.sbsv2.SBSv2MinimumVersionException;
import com.nokia.carbide.cpp.internal.api.sdk.sbsv2.SBSv2QueryUtils;
import com.nokia.carbide.cpp.sdk.core.ISBSv2BuildContext;
import com.nokia.carbide.cpp.sdk.core.ISDKManager;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.core.PathUtils;

/**
 * SBSv2 specific build information.
 *
 */
public class SBSv2BuildInfo implements ISBSv2BuildInfo {

	private ISymbianSDK sdk;
	private List<ISymbianBuildContext> sbsv2FilteredConetxts = new ArrayList<ISymbianBuildContext>();
	private boolean wasScanned = false;
	private Map<String, List<String>> cachedPlatformMacros = new HashMap<String, List<String>>();

	private Map<String, String> aliasToMeaningMap = new HashMap<String, String>();
	private List<String> productList = null;
	
	public SBSv2BuildInfo(ISymbianSDK sdk) {
		this.sdk = sdk;
	}

	@Override
	public void clearPlatformMacros() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ISymbianBuildContext> getAllBuildConfigurations() {
		// TODO: Will get rid of this method. Only filtered configs will apply
		return sbsv2FilteredConetxts;
	}

	@Override
	public List<ISymbianBuildContext> getFilteredBuildConfigurations() {
		
		if (aliasToMeaningMap.size() == 0){
			try {
				aliasToMeaningMap = SBSv2QueryUtils.getAliasesForSDK(sdk);
			} catch (SBSv2MinimumVersionException e) {
				Logging.log( SDKCorePlugin.getDefault(),
							Logging.newSimpleStatus(0, IStatus.ERROR,
								MessageFormat.format(e.getMessage(), ""), e));
			}
		}
		
		if (productList == null){
			// Not all SDKs will have products, so size of 0 is acceptable
			productList = new ArrayList<String>();
			try {
				productList = SBSv2QueryUtils.getProductVariantsForSDK(sdk);
			} catch (SBSv2MinimumVersionException e) {
				// ignore
			}
		}
		List<String> allowedConfigs = SBSv2Utils.getSBSv2FilteredConfigPreferences(); // From global prefs	
		if ((sbsv2FilteredConetxts == null || sbsv2FilteredConetxts.size() == 0) 
			 && SBSv2Utils.enableSBSv2Support()){
			
			try {
				initSBSv2BuildContextList(allowedConfigs);
			} catch (SBSv2MinimumVersionException e) {
				// igore, would be caught above
			}
		} else if (SBSv2Utils.enableSBSv2Support()){
			
			try {
				updateSBSv2BuildContextList(allowedConfigs);
			} catch (SBSv2MinimumVersionException e) {
				// igore, would be caught above
			}
		}
		

		return sbsv2FilteredConetxts;
	}

	private void updateSBSv2BuildContextList(List<String> allowedConfigs) throws SBSv2MinimumVersionException {

		// Check and see if the filtered list has changed
		boolean contextExists = false;
		List<String> newContextsToQuery = new ArrayList<String>();
		for (String aliasName : allowedConfigs){
			for (ISymbianBuildContext context : sbsv2FilteredConetxts){
				ISBSv2BuildContext sbsv2Context = (ISBSv2BuildContext)context;
				if (sbsv2Context.getSBSv2Alias().equals(aliasName)){
					contextExists = true;
					continue;
				}
			}
			if (!contextExists){
				newContextsToQuery.add(aliasName);
			}
			contextExists = false;
		}
		
		String configQueryXML = "";
		try {
			configQueryXML = SBSv2QueryUtils.getConfigQueryXML(sdk, newContextsToQuery);
		} catch (SBSv2MinimumVersionException e) {
			// ignore, previous exception would have caught the error
		}
		for (String alias : newContextsToQuery){
			// TODO: Need to test for variants. Right now variants are not added
			if (aliasToMeaningMap.get(alias) == null){
				continue; // This alias is not valid with this SDK, ignore
			}
			ISBSv2BuildContext sbsv2Context = new BuildContextSBSv2(sdk, alias, aliasToMeaningMap.get(alias), configQueryXML);
			sbsv2FilteredConetxts.add(sbsv2Context);
		}
		
	}

	private void initSBSv2BuildContextList(List<String> allowedConfigs) throws SBSv2MinimumVersionException {
		List<String> filteredAliasList = new ArrayList<String>();
		
		for (String alias : aliasToMeaningMap.keySet()){
			for (String checkedAlias : allowedConfigs){
				if (checkedAlias.equalsIgnoreCase(alias)){
					filteredAliasList.add(alias);
					break;
				}
			}
		}
		
		if (productList != null && productList.size() > 0){
			for (String testConfig : allowedConfigs) {
				if (testConfig.contains(".")){
					String tokens[] = testConfig.split("\\.");
					for (String tok : tokens){
						if (productList.contains(tok)){
							filteredAliasList.add(testConfig);
							break;
						}
					}
				}
			}
		}
		
		String configQueryXML = SBSv2QueryUtils.getConfigQueryXML(sdk,
				filteredAliasList);

		for (String alias : filteredAliasList) {
			String meaning = "";
			if (alias.contains(".")){
				meaning = getMeaningForVariant(alias);
				if (meaning == null){
					continue; // TODO: How to handle this scenaio
				}
			} else {
				meaning = aliasToMeaningMap.get(alias);
			}
			ISBSv2BuildContext sbsv2Context = new BuildContextSBSv2(sdk, alias,
					meaning, configQueryXML);
			sbsv2FilteredConetxts.add(sbsv2Context);
		}
	}

	private String getMeaningForVariant(String alias) {
		String meaning = null;
		
		// TODO: Assuming now that the alias now is the first part and the last bits are the variant bits.
		String tokens[] = alias.split("\\.");
		if (tokens.length > 0){
			meaning = aliasToMeaningMap.get(tokens[0]);
			for (int i = 1; i < tokens.length; i++){
				meaning += "." + tokens[i];
			}
		}
		
		return meaning;
	}

	public List<String> getPlatformMacros(String platform) {
		// TODO: Need to get from Raptor Query
		// TODO: Are these the metadata macros or compiler macros? I presume these are the metadata macros
		// but double check on how the CPP macros are applied (e.g. adding '__" prefix to the name.
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
