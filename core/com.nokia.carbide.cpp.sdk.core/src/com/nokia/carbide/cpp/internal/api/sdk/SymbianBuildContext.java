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
*/
package com.nokia.carbide.cpp.internal.api.sdk;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.osgi.framework.Version;

import com.nokia.carbide.cpp.epoc.engine.preprocessor.DefaultModelDocumentProvider;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.DefaultTranslationUnitProvider;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.DefineFactory;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.internal.sdk.core.model.SymbianMissingSDKFactory;
import com.nokia.carbide.cpp.sdk.core.IBSFCatalog;
import com.nokia.carbide.cpp.sdk.core.IBSFPlatform;
import com.nokia.carbide.cpp.sdk.core.IRVCTToolChainInfo;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.BasicIncludeFileLocator;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.MacroScanner;

public class SymbianBuildContext implements ISymbianBuildContext {

	private String sdkId;
	private String platform;
	private String target;
	private String displayString = null;
	
	private static String EMULATOR_DISPLAY_TEXT = "Emulator"; //$NON-NLS-1$
	private static String PHONE_DISPLAY_TEXT = "Phone"; //$NON-NLS-1$
	private static String DEBUG_DISPLAY_TEXT = "Debug"; //$NON-NLS-1$
	private static String RELEASE_DISPLAY_TEXT = "Release"; //$NON-NLS-1$
	private static String SPACE_DISPLAY_TEXT = " "; //$NON-NLS-1$
	private static String SDK_NOT_INSTALLED = "SDK not installed"; //$NON-NLS-1$
	
	// a copy of bad SDK default to fall back
	private static ISymbianSDK fallbackForBadSdk = SymbianMissingSDKFactory.createInstance("dummy_ID"); //$NON-NLS-1$
	
	private File prefixFileParsed;
	private List<File> hrhFilesParsed = new ArrayList<File>();
	private List<IDefine> variantHRHMacros = new ArrayList<IDefine>();
	private long hrhCacheTimestamp;
	private List<IDefine> compilerPrefixMacros = new ArrayList<IDefine>();
	private long compilerCacheTimestamp;
	
	
	public SymbianBuildContext(ISymbianSDK theSDK, String thePlatform, String theTarget) {
		sdkId = theSDK.getUniqueId();
		platform = thePlatform;
		target = theTarget;
		getDisplayString();
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((platform == null) ? 0 : platform.hashCode());
		result = prime * result + ((getSDK() == null) ? 0 : getSDK().getEPOCROOT().hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SymbianBuildContext other = (SymbianBuildContext) obj;
		if (platform == null) {
			if (other.platform != null)
				return false;
		} else if (!platform.equals(other.platform))
			return false;
		if (getSDK() == null) {
			if (other.getSDK() != null)
				return false;
		} else if (!getSDK().getEPOCROOT().equals(other.getSDK().getEPOCROOT()))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		return true;
	}


	public ISymbianSDK getSDK() {
		
		ISymbianSDK sdk = SDKCorePlugin.getSDKManager().getSDK(sdkId, true);
		if (sdk == null){
			sdk = fallbackForBadSdk;
		}
		
		return sdk;
	}

	public String getPlatformString() {
		return platform;
	}

	public String getTargetString() {
		return target;
	}

	public String getDisplayString() {
		if (displayString == null) {
			// in the form Emulation Debug (WINSCW) [S60_3rd_MR] or
			// Phone Release (GCCE) [S60_3rd_MR]
			if (platform.compareTo(ISymbianBuildContext.EMULATOR_PLATFORM) == 0) {
				displayString = EMULATOR_DISPLAY_TEXT;
			} else {
				displayString = PHONE_DISPLAY_TEXT;
			}
			
			if (target.compareTo(ISymbianBuildContext.DEBUG_TARGET) == 0) {
				displayString = displayString + SPACE_DISPLAY_TEXT + DEBUG_DISPLAY_TEXT;
			} else {
				displayString = displayString + SPACE_DISPLAY_TEXT + RELEASE_DISPLAY_TEXT;
			}

			displayString = displayString + " (" + platform + ") [" + getSDK().getUniqueId() + "]"; //$NON-NLS-1$
		}
		return displayString;
	}
	
	public static ISymbianBuildContext getBuildContextFromDisplayName(String displayName) {
		if (isValidConfigName(displayName)) {
			String sdkId = getSDKIDFromConfigName(displayName);
			ISymbianSDK sdk = SDKCorePlugin.getSDKManager().getSDK(sdkId, true);
			if (sdk == null) {
				// add a dummy should a build context ask for a removed SDK
				sdk = SDKManagerInternalAPI.addMissingSdk(sdkId);
			}
						
			return new SymbianBuildContext(sdk, getPlatformFromBuildConfigName(displayName), getTargetFromBuildConfigName(displayName));
		}
		return new SymbianBuildContext(fallbackForBadSdk, SDK_NOT_INSTALLED, SDK_NOT_INSTALLED);
	}

	private static String getPlatformFromBuildConfigName(String configName) {
		String[] tokens = configName.split(SPACE_DISPLAY_TEXT);
		String sdkIdToken = tokens[2];
		return sdkIdToken.substring(1, sdkIdToken.length()-1);
	}

	public static String getSDKIDFromConfigName(String configName) {
		int indexBegin = configName.indexOf("[");  //$NON-NLS-1$
		int indexEnd = configName.indexOf("]");  //$NON-NLS-1$
		if (indexBegin > 0 && indexEnd > 0){
			return configName.substring(indexBegin+1, indexEnd);
		} else {
			return ""; //$NON-NLS-1$
		}
	}

	private static String getTargetFromBuildConfigName(String configName) {
		String[] tokens = configName.split(SPACE_DISPLAY_TEXT);
		if (tokens[1].compareTo(DEBUG_DISPLAY_TEXT) == 0) {
			return ISymbianBuildContext.DEBUG_TARGET;
		} else {
			return ISymbianBuildContext.RELEASE_TARGET;
		}
	}

	private static boolean isValidConfigName(String configName) {
		// <Phone | Emulator> <Target> (<Platform>) [<SDK ID>]
		if (configName != null && !configName.equals("")) { //$NON-NLS-1$
			String[] tokens = configName.split(SPACE_DISPLAY_TEXT);
			if (tokens.length >= 4) {
				if (tokens[0].compareTo(EMULATOR_DISPLAY_TEXT) == 0 || tokens[0].compareTo(PHONE_DISPLAY_TEXT) == 0) {
					if (tokens[1].compareTo(DEBUG_DISPLAY_TEXT) == 0 || tokens[1].compareTo(RELEASE_DISPLAY_TEXT) == 0) {
						if (tokens[2].matches("(.*)")) { //$NON-NLS-1$
							if (tokens[3].matches("\\[.*")) { //$NON-NLS-1$
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	public String toString() {
		return getDisplayString();
	}
	
	public String getDefaultDefFileDirectoryName(boolean isASSP) {
		// TODO: How the ASSP option affects the path?

		String dirName = getDefFileDirectoryNameForPlatform(platform);
		if (dirName == null) {
			// check BSF's
			IBSFCatalog catalog = getSDK().getBSFCatalog();
	    	if (catalog != null) {
	    		for (IBSFPlatform plat : catalog.getPlatforms()) {
	    			if (plat.getName().compareToIgnoreCase(platform) == 0) {
	    				String mainPlatform = catalog.getReleasePlatform(platform);
	    				if (mainPlatform != null) {
	    					dirName = getDefFileDirectoryNameForPlatform(mainPlatform);
	    					if (dirName == null || dirName.length() < 1) {
	    						// fallback - all BSF's should be EABI anyway
			    				return "EABI"; //$NON-NLS-1$
	    					}
	    				}
	    			}
	    		}
	    	}
		}
		
		if (dirName == null) {
			// fallback for unknown cases
			dirName = platform;
		}
		
		return dirName;
	}
	
	private String getDefFileDirectoryNameForPlatform(String platform) {
		if (platform.equals(THUMB_PLATFORM)
				|| platform.equals(ARM4_PLATFORM)
				|| platform.equals(ARMI_PLATFORM)) {
			return "BMARM"; //$NON-NLS-1$
		} else if (platform.equals(EMULATOR_PLATFORM)) {
			return "BWINS"; //$NON-NLS-1$
		} else if (platform.equals(ARMV5_PLATFORM)
					|| platform.equals(ARMV5_ABIV2_PLATFORM)
					|| platform.equals(ARMV6_PLATFORM)
					|| platform.equals(ARMV6_ABIV2_PLATFORM)
					|| platform.equals(GCCE_PLATFORM)) {
			return "EABI"; //$NON-NLS-1$
		}
		return null;
	}

	public IPath getCompilerPrefixFile() {
		if (platform.equals(GCCE_PLATFORM)) {
			return getGCCEPrefixFilePath();
		} else if (platform.equals(ARMV5_PLATFORM)
					|| platform.equals(ARMV5_ABIV2_PLATFORM)
					|| platform.equals(ARMV6_PLATFORM)
					|| platform.equals(ARMV6_ABIV2_PLATFORM)) {
			return getRVCTPrefixFilePath();
		} else if (platform.equals(EMULATOR_PLATFORM)
				|| platform.equals(ARMI_PLATFORM)
				|| platform.equals(ARM4_PLATFORM)
				|| platform.equals(THUMB_PLATFORM)) {
			return null;
		} else {
			// check BSF's
			IBSFCatalog catalog = getSDK().getBSFCatalog();
	    	if (catalog != null) {
	    		for (IBSFPlatform plat : catalog.getPlatforms()) {
	    			if (plat.getName().compareToIgnoreCase(platform) == 0) {
	    				String mainPlatform = catalog.getReleasePlatform(platform);
	    				if (mainPlatform != null) {
	    					if (mainPlatform.equals(GCCE_PLATFORM)) {
	    						return getGCCEPrefixFilePath();
	    					} else if (mainPlatform.equals(ARMV5_PLATFORM) 
	    								|| mainPlatform.equals(ARMV5_ABIV2_PLATFORM)
	    								|| mainPlatform.equals(ARMV6_PLATFORM)
	    								|| mainPlatform.equals(ARMV6_ABIV2_PLATFORM)) {
	    						return getRVCTPrefixFilePath();
	    					} else {
	    						// fallback - all BSF's should be EABI anyway
	    						return getRVCTPrefixFilePath();
	    					}
	    				}
	    			}
	    		}
	    	}
		}

		// fallback for WINSCW, MSVC, etc.
		return null;
	}

	private IPath getGCCEPrefixFilePath() {
		return getSDK().getIncludePath().append("gcce/gcce.h"); //$NON-NLS-1$
	}

	private IPath getRVCTPrefixFilePath() {
		IRVCTToolChainInfo[] installedRVCTTools = SDKCorePlugin.getSDKManager().getInstalledRVCTTools();
		// use default in case tools aren't installed
		String rvctFragment = "rvct2_2"; //$NON-NLS-1$
		if (installedRVCTTools.length > 0) {
			rvctFragment = getRVCTFragment(installedRVCTTools[0]);
		}
		return getSDK().getIncludePath().append(rvctFragment).append(rvctFragment + ".h"); //$NON-NLS-1$
	}

	private String getRVCTFragment(IRVCTToolChainInfo info) {
		int major = 0, minor = 0;
		if (info != null) {
			Version rvctToolsVersion = info.getRvctToolsVersion();
			if (rvctToolsVersion != null) {
				major = info.getRvctToolsVersion().getMajor();
				minor = info.getRvctToolsVersion().getMinor();
			}
		}
		return "rvct" + major + "_" + minor; //$NON-NLS-1$ //$NON-NLS-2$
	}

	public List<IDefine> getVariantHRHDefines() {

		// we parse the variant hrh file to gather macros.  this can be time consuming so do it
		// once and cache the values.  only reset the cache when the hrh or any of its includes
		// has changed.
		
		boolean buildCache = false;
		
		if (hrhCacheTimestamp == 0) {
			// hasn't been built yet
			buildCache = true;
		} else {
			// cache exists.  see if any of the files have changed
			ISymbianSDK sdk = getSDK();
			if (sdk != null) {
				// the prefix may have been added, removed, or changed.  in any case,
				// we would need to reset the cache
				File currentPrefixFile = sdk.getPrefixFile();
				if (currentPrefixFile == null) {
					if (prefixFileParsed != null) {
						// prefix file was removed from the SDK
						buildCache = true;
					}
				} else {
					if (prefixFileParsed == null) {
						// prefix file was added to the SDK
						buildCache = true;
					} else {
						// there was a prefix file before and now.  see if it's the same file
						// and if so, has it been modified?
						if (!currentPrefixFile.equals(prefixFileParsed) || currentPrefixFile.lastModified() > hrhCacheTimestamp) {
							buildCache = true;
						}
					}
				}
			}
			
			// now check to see if any of the included hrh files have changed
			if (!buildCache) {
				for (File file : hrhFilesParsed) {
					if (file.lastModified() > hrhCacheTimestamp) {
						buildCache = true;
						break;
					}
				}
			}
		}
		
		if (buildCache) {

			variantHRHMacros.clear();
			
			synchronized (this) {

				List<IDefine> macros = new ArrayList<IDefine>();
				Map<String, IDefine> namedMacros = new HashMap<String, IDefine>();
				File prefixFile = getSDK().getPrefixFile();
				if (prefixFile != null) {

					// add any BSF includes so the headers are picked up from the correct location
					List<File> systemPaths = new ArrayList<File>();
					IBSFPlatform plat = getSDK().getBSFCatalog().findPlatform(platform);
					if (plat != null) {
						for (IPath path : plat.getSystemIncludePaths()) {
							systemPaths.add(path.toFile());
						}
					}
					
					MacroScanner scanner = new MacroScanner(
							new BasicIncludeFileLocator(null, systemPaths.toArray(new File[systemPaths.size()])),
							DefaultModelDocumentProvider.getInstance(),
							DefaultTranslationUnitProvider.getInstance());
					scanner.scanFile(prefixFile);
		
					List<IDefine> scannedMacros = (List<IDefine>)scanner.getMacroDefinitions();
					for (IDefine scannedMacro : scannedMacros){
						// we don't want duplicate macros, so check to see if it's already there.
						// if it is, remove it and then add the newer one.  this is consistent with
						// how it would be from a compiler standpoint.
						IDefine macro = namedMacros.get(scannedMacro.getName());
						if (macro != null) {
							macros.remove(macro);
						}
						
						macros.add(scannedMacro);
						namedMacros.put(scannedMacro.getName(), scannedMacro);
					}
					
					hrhFilesParsed.clear();
					for (File inc : scanner.getIncludedFiles()) {
						hrhFilesParsed.add(inc);
					}
					
					List<String> variantCFGMacros = new ArrayList<String>();
					variantCFGMacros = getSDK().getVariantCFGMacros();
					for (String cfgMacros : variantCFGMacros){
						// we don't want duplicate macros, so check to see if it's already there.
						IDefine existingMacro = namedMacros.get(cfgMacros);
						if (existingMacro != null) {
							macros.remove(existingMacro);
						}
						
						IDefine macro = DefineFactory.createSimpleFreeformDefine(cfgMacros);
						macros.add(macro);
						namedMacros.put(macro.getName(), macro);
					}

					// store off the time when we created the cache
					hrhCacheTimestamp = System.currentTimeMillis();
				}
				
				// save the prefix file (which may be null)
				prefixFileParsed = prefixFile;

				variantHRHMacros = macros;
			}
		}
			
		return variantHRHMacros;
	}

	public List<File> getPrefixFileIncludes() {
		return hrhFilesParsed;
	}


	public List<IDefine> getCompilerMacros() {
		// we parse the compiler prefix file to gather macros.  this can be time consuming so do it
		// once and cache the values.  only reset the cache when the compiler prefix has changed.
		
		IPath prefixFile = getCompilerPrefixFile();
		if (prefixFile == null || !prefixFile.toFile().exists()) {
			compilerPrefixMacros.clear();
			return compilerPrefixMacros;
		}

		if ((compilerCacheTimestamp == 0) ||
				(prefixFile.toFile().lastModified() > compilerCacheTimestamp)) {

			compilerPrefixMacros.clear();
			
			synchronized (this) {

				List<IDefine> macros = new ArrayList<IDefine>();
				if (prefixFile != null) {

					List<File> userPaths = new ArrayList<File>();
					List<File> systemPaths = new ArrayList<File>();
					
					userPaths.add(prefixFile.removeLastSegments(1).toFile());
					systemPaths.add(prefixFile.removeLastSegments(1).toFile());
					IPath includePath = getSDK().getIncludePath();
					if (includePath != null) {
						File includeDir = includePath.toFile().getAbsoluteFile();
						userPaths.add(includeDir);
						systemPaths.add(includeDir);
					}

					
					// get macros from the compiler prefix file: note, this is a stupid
					// scan that will get the last version #defined, even if inside an #if.
					MacroScanner scanner = new MacroScanner(
							new BasicIncludeFileLocator(userPaths.toArray(new File[userPaths.size()]), systemPaths.toArray(new File[systemPaths.size()])),
							DefaultModelDocumentProvider.getInstance(), 
							DefaultTranslationUnitProvider.getInstance());
					scanner.scanFile(prefixFile.toFile());
					for (IDefine define : scanner.getMacroDefinitions()) {
						macros.add(define);
					}

					// store off the time when we created the cache
					compilerCacheTimestamp = System.currentTimeMillis();
				}
				
				compilerPrefixMacros = macros;
			}
		}
			
		return compilerPrefixMacros;
	}


	public String getBuildVariationName() {
		String varName = "";
		
		String[] tokens = getPlatformString().split("\\.");
		if (tokens.length == 2){
			varName = tokens[1];
		}
		
		return varName;
	}
}
