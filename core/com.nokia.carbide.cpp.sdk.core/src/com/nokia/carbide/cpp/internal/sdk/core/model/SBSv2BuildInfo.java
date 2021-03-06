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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import com.nokia.carbide.cpp.internal.api.sdk.BuildContextSBSv2;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv2BuildContext;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv2BuildInfo;
import com.nokia.carbide.cpp.internal.api.sdk.SBSv2Utils;
import com.nokia.carbide.cpp.internal.api.sdk.sbsv2.SBSv2ConfigQueryData;
import com.nokia.carbide.cpp.internal.api.sdk.sbsv2.SBSv2MinimumVersionException;
import com.nokia.carbide.cpp.internal.api.sdk.sbsv2.SBSv2QueryUtils;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDKFeatures;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

/**
 * SBSv2 specific build information. Serves as a container for build data container for a Symbian SDK using SBSv2
 * @since 3.0
 *
 */
public class SBSv2BuildInfo implements ISBSv2BuildInfo {

	private ISymbianSDK sdk;
	private List<ISymbianBuildContext> sbsv2FilteredContexts = new ArrayList<ISymbianBuildContext>();
	private boolean wasScanned = false;
	/** from <metadata> element from sbs --query=config[] */
	private Map<String, Map<String, String>> cachedMetadataMacros = new HashMap<String, Map<String, String>>();
	/** from <build> element from sbs --query=config[] */
	private Map<String, Map<String, String>> cachedBuildMacros = new HashMap<String, Map<String, String>>();

	private Map<String, String> aliasToMeaningMap = new HashMap<String, String>();
	private List<String> productList = null;
	private IPath cachedVariantHRHFile = null;
	private static boolean hasShownDialog;
	
	public SBSv2BuildInfo(ISymbianSDK sdk) {
		this.sdk = sdk;
	}

	public List<ISymbianBuildContext> getAllBuildConfigurations() {
		// This really only applies to SBSv1. We never return the full list of configs for SBSv2, only the filtered ones
		return getFilteredBuildConfigurations();
	}

	public void clearDataFromBuildCache(){
		aliasToMeaningMap.clear();
		if (productList != null) productList.clear();
		sbsv2FilteredContexts.clear();
		cachedBuildMacros.clear();
		cachedMetadataMacros.clear();
		cachedVariantHRHFile = null;
	}
	
	public List<ISymbianBuildContext> getFilteredBuildConfigurations() {
		
		if (aliasToMeaningMap.size() == 0){
			try {
				aliasToMeaningMap = SBSv2QueryUtils.getAliasesForSDK(sdk);
			} catch (final SBSv2MinimumVersionException e) {
				if (hasShownDialog == false){
					
					PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
						public void run() {
							MessageDialog.openError(WorkbenchUtils.getSafeShell(), "Minimum sbs version not met.", e.getMessage());
						}
					});	
					
				Logging.log( SDKCorePlugin.getDefault(),
							Logging.newSimpleStatus(0, IStatus.ERROR,
								MessageFormat.format(e.getMessage(), ""), e));
				
				hasShownDialog = true;
				}
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
		if (SBSv2Utils.enableSBSv2Support()){
			
			try {
				initSBSv2BuildContextList(allowedConfigs);
			} catch (SBSv2MinimumVersionException e) {
				// igore, would be caught above
			}
		} 
		
		return sortContexts(sbsv2FilteredContexts);
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

		List<String> processedAliasList = new ArrayList<String>();
		sbsv2FilteredContexts.clear();
		// First check if we have scanned configs already this IDE session.
		// We don't want to scan broken configs over and over
		boolean isStartUpScan = !((SymbianSDK)sdk).hasScannedRaptor();
		
		for (String alias : filteredAliasList) {
			SBSv2ConfigQueryData configQueryData = SBSv2QueryUtils.getConfigQueryDataForSDK(sdk, alias);
			if (configQueryData != null) {
				if (configQueryData.getConfigurationErrorMessage().trim().length() == 0 || isStartUpScan == false){
					ISBSv2BuildContext sbsv2Context = new BuildContextSBSv2(sdk, alias, configQueryData);
					sbsv2FilteredContexts.add(sbsv2Context);
					processedAliasList.add(alias);
				}
			}
		}

		if (!processedAliasList.isEmpty()) {
			filteredAliasList.removeAll(processedAliasList); // Get the configs that had errors
		}

		if (!filteredAliasList.isEmpty()) {
			// These configs have no data or had errors in them reported by Raptor
			
			String configQueryXML = SBSv2QueryUtils.getConfigQueryXMLforSDK(sdk, filteredAliasList);

			for (String alias : filteredAliasList) {
				String meaning = "";
				if (alias.contains(".")){
					meaning = getMeaningForVariant(alias);
				} else {
					meaning = aliasToMeaningMap.get(alias);
				}
				if (meaning == null){
					continue; 
				}
				SBSv2ConfigQueryData configQueryData = new SBSv2ConfigQueryData(alias, meaning, configQueryXML);
				ISBSv2BuildContext sbsv2Context = new BuildContextSBSv2(sdk, alias, configQueryData);
				sbsv2FilteredContexts.add(sbsv2Context);
				SBSv2QueryUtils.storeConfigQueryDataForSDK(sdk, alias, configQueryData);
			}
		}

		checkWINSCWSupport();
		if (!((SymbianSDK)sdk).hasScannedRaptor()){
			((SymbianSDK)sdk).setScannedRaptor(true);
			SBSv2QueryUtils.flushAllSBSv2Caches();
		}
		
	}

	private void checkWINSCWSupport() {
		List<ISymbianBuildContext> contextList = new ArrayList<ISymbianBuildContext>();
		for (Iterator<ISymbianBuildContext> itr = sbsv2FilteredContexts.iterator(); itr.hasNext();) {
			ISBSv2BuildContext context = (ISBSv2BuildContext) itr.next();
			if (context.getPlatformString().equalsIgnoreCase(ISBSv2BuildContext.TOOLCHAIN_WINSCW)) {
				if (context.getTargetString().equalsIgnoreCase(ISymbianBuildContext.DEBUG_TARGET)) {
					if (sdk.getSupportedFeatures().contains(ISymbianSDKFeatures.IS_WINSCW_UDEB_SUPPORTED)){
						contextList.add(context);
					}
				}
				else
					if (context.getTargetString().equalsIgnoreCase(ISymbianBuildContext.RELEASE_TARGET)) {
						if (sdk.getSupportedFeatures().contains(ISymbianSDKFeatures.IS_WINSCW_UREL_SUPPORTED)){
							contextList.add(context);
						}
					}
			} else {
				contextList.add(context);
			}
		}
		sbsv2FilteredContexts = contextList;
	}

	private String getMeaningForVariant(String alias) {
		String meaning = null;
		
		// The alias is the first token when splitting on a dot.
		String tokens[] = alias.split("\\.");
		if (tokens.length > 0){
			meaning = aliasToMeaningMap.get(tokens[0]);
			for (int i = 1; i < tokens.length; i++){
				meaning += "." + tokens[i];
			}
		}
		
		return meaning;
	}

	public Map<String, String> getMetadataMacros(String buildAlias) {
		Map<String, String> platformMacros = cachedMetadataMacros.get(buildAlias);
		if (platformMacros == null) {
			platformMacros = new HashMap<String, String>();
			synchronized (cachedMetadataMacros) {
				if (sbsv2FilteredContexts == null || sbsv2FilteredContexts.size() == 0) {
					getFilteredBuildConfigurations();
				}
				if (sbsv2FilteredContexts.size() > 0) {
					for (ISymbianBuildContext context : sbsv2FilteredContexts) {
						if (((ISBSv2BuildContext)context).getSBSv2Alias().equalsIgnoreCase(buildAlias)) {
							platformMacros.putAll(((ISBSv2BuildContext)context).getConfigQueryData().getMetaDataMacros());
						}
					}
					cachedMetadataMacros.put(buildAlias, platformMacros);
				}
			}
		}
		return platformMacros;
	}
	
	public Map<String, String> getBuildMacros(String buildAlias) {
		Map<String, String> buildMacros = cachedBuildMacros.get(buildAlias);
		
		if (buildMacros == null || buildMacros.size() == 0) {
			buildMacros = new HashMap<String, String>();
			synchronized (cachedBuildMacros) {
				if (sbsv2FilteredContexts == null || sbsv2FilteredContexts.size() == 0) {
					getFilteredBuildConfigurations();
				}
				if (sbsv2FilteredContexts.size() > 0) {
					for (ISymbianBuildContext context : sbsv2FilteredContexts) {
						if (((ISBSv2BuildContext)context).getSBSv2Alias().equalsIgnoreCase(buildAlias)) {
							buildMacros.putAll(((ISBSv2BuildContext)context).getConfigQueryData().getBuildMacros());
						}
					}
					cachedBuildMacros.put(buildAlias, buildMacros);
				}
			}
		}
		return buildMacros;
	}

	/**
	 * Get the full path to the prefix file defined under \epoc32\tools\variant\variant.cfg
	 * @return A path object, or null if the variant.cfg does not exist. This routine does not check to see if the returned path exists.
	 */
	public IPath getPrefixFromVariantCfg(){
		if (cachedVariantHRHFile == null || cachedVariantHRHFile.toOSString().length() == 0) {
			if (sbsv2FilteredContexts == null || sbsv2FilteredContexts.size() == 0) {
				getFilteredBuildConfigurations();
			}
			if (sbsv2FilteredContexts.size() > 0) {
				for (ISymbianBuildContext context : sbsv2FilteredContexts) {
					String vStr = ((ISBSv2BuildContext)context).getConfigQueryData().getMetaDataVariantHRH();
					if (vStr != null) {
						cachedVariantHRHFile = new Path(vStr);
						break;
					}
				}
			}
		}
		return cachedVariantHRHFile;
	}

	public String getTargetTypeMacro(String targettype) {
		// this is based on \epoc32\tools\trgtype.pm which changes from
		// OS version to OS version, but largely remains constant with
		// regards to the basic type.
		
		// if it's not one of these then it's a DLL
		if (targettype.compareToIgnoreCase("EPOCEXE") == 0) {
			return "__EXEDLL__";
		} else if (targettype.compareToIgnoreCase("EXEDLL") == 0) {
			return "__EXEDLL__";
		} else if (targettype.compareToIgnoreCase("EXE") == 0) {
			return "__EXE__";
		} else if (targettype.compareToIgnoreCase("EXEXP") == 0) {
			return "__EXE__";
		} else if (targettype.compareToIgnoreCase("IMPLIB") == 0) {
			return "__IMPLIB__";
		} else if (targettype.compareToIgnoreCase("KLIB") == 0) {
			return "__LIB__";
		} else if (targettype.compareToIgnoreCase("LIB") == 0) {
			return "__LIB__";
		} else {
			return "__DLL__";
		}

	}

	public boolean isPreviouslyScanned() {
		return wasScanned;
	}

	public void setPreviouslyScanned(boolean wasScanned) {
		this.wasScanned = wasScanned;
	}
	
	private static List<ISymbianBuildContext> sortContexts(List<ISymbianBuildContext> contexts){ 
		Collections.sort(contexts, new Comparator<ISymbianBuildContext>() {
			
			public int compare(ISymbianBuildContext o1, ISymbianBuildContext o2) {
				
				ISBSv2BuildContext sbs1 = (ISBSv2BuildContext)o1;
				ISBSv2BuildContext sbs2 = (ISBSv2BuildContext)o2;
				
				//String[] sbs1Prefix = sbs1.getSBSv2Alias().split("_");
				String[] sbs2Prefix = sbs2.getSBSv2Alias().split("_");
				
				if (sbs1.getSBSv2Alias().length() == sbs2.getSBSv2Alias().length()){
					return sbs1.getSBSv2Alias().compareTo(sbs2.getSBSv2Alias());
				}
				
				if (sbs2Prefix.length > 0){
					if (sbs1.getSBSv2Alias().startsWith(sbs2Prefix[0])){
						return -1;
					}
				}
				
				
				return sbs1.getSBSv2Alias().compareTo(sbs2.getSBSv2Alias());
			}
		});
		
		return contexts; 
	}

}
