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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cpp.internal.api.sdk.BuildContextSBSv1;
import com.nokia.carbide.cpp.internal.api.sdk.BuildContextSBSv2;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv2BuildInfo;
import com.nokia.carbide.cpp.internal.api.sdk.SBSv2Utils;
import com.nokia.carbide.cpp.sdk.core.IBSFCatalog;
import com.nokia.carbide.cpp.sdk.core.IBSFPlatform;
import com.nokia.carbide.cpp.sdk.core.ISBVCatalog;
import com.nokia.carbide.cpp.sdk.core.ISBVPlatform;
import com.nokia.carbide.cpp.sdk.core.ISDKManager;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDKFeatures;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;

/**
 * SBSv2 specific build information.
 *
 */
public class SBSv2BuildInfo implements ISBSv2BuildInfo {

	private File prefixFile;
	private IBSFCatalog bsfCatalog;
	private ISBVCatalog sbvCatalog;
	private List<ISymbianBuildContext> binaryVariantContextList = new ArrayList<ISymbianBuildContext>(0);
	private List<ISymbianBuildContext> bsfContextList = new ArrayList<ISymbianBuildContext>(0);

	@Override
	public List<ISymbianBuildContext> getFilteredBuildConfigurations(ISymbianSDK sdk) {
		// This is probably a bug, but the filtering only uses SBSv1 preferences if SBSv1 is enabled...
		List<ISymbianBuildContext> filteredContexts;
		if (SBSv2Utils.enableSBSv2Support()) {
			filteredContexts = SBSv2Utils.getFilteredSBSv2BuildContexts(sdk);
		} else {
			// be optimistic in this case... SBSv3? ;)
			filteredContexts = getAllBuildConfigurations(sdk);
		}
		return filteredContexts;
	}

	@Override
	public List<ISymbianBuildContext> getAllBuildConfigurations(ISymbianSDK sdk) {
		Set sdkFeatures = sdk.getSupportedFeatures();
		List<ISymbianBuildContext> buildTargets = new ArrayList<ISymbianBuildContext>();
		
		// note that this gets variant platforms but not regular BSF's
		List <String>buildPlats =  getAvailablePlatforms(sdk);
		
		if (buildPlats.size() == 0){
			return Collections.emptyList();
		}
		// TODO: Hard code build context hack
		buildTargets.add(new BuildContextSBSv2(sdk, ISymbianBuildContext.EMULATOR_PLATFORM, ISymbianBuildContext.DEBUG_TARGET, ""));
		
		if (sdkFeatures.contains(ISymbianSDKFeatures.IS_WINSCW_UREL_SUPPORTED)){
			// TODO: Hard code build context hack
			buildTargets.add(new BuildContextSBSv2(sdk, ISymbianBuildContext.EMULATOR_PLATFORM, ISymbianBuildContext.RELEASE_TARGET, ""));
		}
		
		for (String currPlat : buildPlats){
			if (currPlat.equals(ISymbianBuildContext.EMULATOR_PLATFORM) ) { 
				// emulation targets already determined (some SDKs don't get WISNCW UREL
				continue;
			}
			// TODO: Hard code build context hack
			buildTargets.add(new BuildContextSBSv2(sdk, currPlat, ISymbianBuildContext.DEBUG_TARGET, ""));
			
			// everything gets release except for WINSCW
			// TODO: Hard code build context hack
			buildTargets.add(new BuildContextSBSv2(sdk, currPlat, ISymbianBuildContext.RELEASE_TARGET, ""));
		}
		
		ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
		if (sdkMgr.getBSFScannerEnabled()){
			buildTargets.addAll(getBSFPlatformContexts(sdk));
			buildTargets.addAll(getBinaryVariationPlatformContexts(sdk)); // Symbian Binary Variation (.var)
		}
		
		return buildTargets;
	}

	public List<String> getPlatformMacros(ISymbianSDK sdk, String platform) {
		if (sdk instanceof SymbianSDK) {
			return ((SymbianSDK)sdk).getPlatformMacros(platform);
		}
		return null;
	}

	public List<String> getTargetTypeMacros(ISymbianSDK sdk, String targettype) {
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

	public List<String> getAvailablePlatforms(ISymbianSDK sdk) {
		ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
		return sdkMgr.getSymbianMacroStore().getSupportedPlatforms(((SymbianSDK)sdk).getOSVersion(), "", null);
	}

	public File getPrefixFile(ISymbianSDK sdk) {
		return prefixFile;
	}

	public IPath getIncludePath(ISymbianSDK sdk) {
		String epocRoot = sdk.getEPOCROOT();
		if (epocRoot.length() > 0) {
			IPath epoc32IncPath = new Path(epocRoot).append("epoc32/include");
			// try to canonicalize it so it matches actual file system case
			try {
				epoc32IncPath = new Path(epoc32IncPath.toFile().getCanonicalPath());
			} catch (IOException e) {
			}
			return epoc32IncPath;
		}
		return null;
	}

	public boolean isPreviouslyScanned(ISymbianSDK sdk) {
		if (sdk instanceof SymbianSDK) {
			return ((SymbianSDK)sdk).isPreviouslyScanned();
		}
		return true;
	}

	public IBSFCatalog getBSFCatalog(ISymbianSDK sdk) {
		synchronized (sdk) {
			if (bsfCatalog == null) {
				bsfCatalog = BSFCatalogFactory.createCatalog(sdk);
			}
		}
		return bsfCatalog;
	}

	public ISBVCatalog getSBVCatalog(ISymbianSDK sdk) {
		synchronized (sdk) {
			if (sbvCatalog == null) {
				sbvCatalog = SBVCatalogFactory.createCatalog(sdk);
			}
		}
		return sbvCatalog;
	}

	public void setPreviouslyScanned(ISymbianSDK sdk, boolean wasScanned) {
		if (sdk instanceof SymbianSDK) {
			((SymbianSDK)sdk).setPreviouslyScanned(wasScanned);
		}
	}

	public void setPrefixFile(ISymbianSDK sdk, IPath prefixFile) {
		this.prefixFile = new File(prefixFile.toOSString());
	}

	protected List<ISymbianBuildContext> getBinaryVariationPlatformContexts(ISymbianSDK sdk) {
		synchronized (binaryVariantContextList) {
			if (!binaryVariantContextList.isEmpty()){
				return binaryVariantContextList;
			}
			
			ISBVCatalog catalog = getSBVCatalog(sdk);
			for (ISBVPlatform sbvPlatform : catalog.getPlatforms()) {
				// Currently only variation of ARMV5 is supported... So just hard code the variated platform
				// Only add the build platform if it's not virtual.
				if (!sbvPlatform.isVirtual()){
					// TODO: Hard code build context hack
					binaryVariantContextList.add(new BuildContextSBSv2(sdk, BuildContextSBSv1.ARMV5_PLATFORM + "." + sbvPlatform.getName(), ISymbianBuildContext.DEBUG_TARGET, ""));
					// TODO: Hard code build context hack
					binaryVariantContextList.add(new BuildContextSBSv2(sdk, BuildContextSBSv1.ARMV5_PLATFORM + "." + sbvPlatform.getName(), ISymbianBuildContext.RELEASE_TARGET, ""));
				}
			}
		}
		return binaryVariantContextList;
	}

	protected List<ISymbianBuildContext> getBSFPlatformContexts(ISymbianSDK sdk) {
		synchronized (bsfContextList) {
			if (!bsfContextList.isEmpty()){
				return bsfContextList;
			}
			
			IBSFCatalog catalog = getBSFCatalog(sdk);
			for (IBSFPlatform platform : catalog.getPlatforms()) {
				// only return non-variant style BSF's.  see boog #4533 for details.
				if (!platform.isVariant()) {
					// TODO: Hard code build context hack
					bsfContextList.add(new BuildContextSBSv2(sdk, platform.getName().toUpperCase(), ISymbianBuildContext.DEBUG_TARGET, ""));
					// TODO: Hard code build context hack
					bsfContextList.add(new BuildContextSBSv2(sdk, platform.getName().toUpperCase(), ISymbianBuildContext.RELEASE_TARGET, ""));
				}
			}
		}
		return bsfContextList;
	}

}
