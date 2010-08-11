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
package com.nokia.carbide.cpp.sdk.core;

import java.io.File;
import java.util.List;

import org.eclipse.cdt.core.settings.model.ICStorageElement;
import org.eclipse.core.runtime.IPath;

import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;

/**
 * ISymbianBuildContext represents a single buildable unit for a Symbian SDK and
 * is used to get the various parts of the build parameters (e.g. platform and target)
 * from the configuration display string
 * @see ICarbideBuildConfiguration
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ISymbianBuildContext {
	
	/**
	 * DEFAULT suffix to use for components that are specified as variant, but don't build as variants. For example, plain ARMV5, when specified as
	 * as a FEATUREVARIANT, will build as ARMV5.DEFAULT
	 * @since 2.0
	 */
	public static final String DEFAULT_VARIANT = "DEFAULT";
	
	/**
	 * Target constants
	 */

	public static final String DEBUG_TARGET = "UDEB";

	public static final String RELEASE_TARGET = "UREL";

	/**
	 * Get the SDK interface for this build context
	 * @return ISymbianSDK interface.
	 */
	public ISymbianSDK getSDK();
	
	/**
	 * Get the build platform that is used for this build context
	 * @return A build platform that can be use with 'abld build' (e.g. WINSWC, THUMB)
	 */
	public String getPlatformString();
	
	
	/**
	 * The debug or release target the platform is building for.
	 * @return UDEB or UREL.
	 * @see DEBUG_TARGET and RELEASE_TARGET
	 */
	public String getTargetString();
	
	/**
	 * Get the full display string for the configuration. This is what you see in the UI build configuration selection.
	 * @return String
	 */
	public String getDisplayString();
	
	/**
	 * Get the implicit directory searched for *.def files by the DEFFILE statement.
	 * @return bare directory name (e.g. 'BWINS', 'BMARM', 'EABI')
	 */
	public String getDefaultDefFileDirectoryName();
	
	/**
	 * Get the prefix file used at build time.  This usually provides
	 * definitions of common macros.
	 * @return full path, or null if no prefix file known
	 */
	public IPath getCompilerPrefixFile();

	/**
	 * Get the full path to the prefix file defined under \epoc32\tools\variant\variant.cfg
	 * @return A path object, or null if the variant.cfg does not exist. This routine does not check to see if the returned path exists.
	 * @since 3.0
	 */
	public IPath getPrefixFromVariantCfg();
	
	/**
	 * Returns the list of all vendor specific C/C++ macros for this SDK.  This is the result of preprocessing
	 * the HRH file defined under /epoc32/tools/variant/variant.cfg
	 * @return a list of macros which may be empty.
	 */
	public List<IDefine> getVariantHRHDefines();
	
	/**
	 * Returns the list of all header files recursively included by the SDK prefix file (defined in /epoc32/tools/variant/variant.cfg).  Note that the list of
	 * files could be different for different context's since the platform can potentially change the list of include
	 * paths.
	 * @return a list of header files which may be empty
	 */
	public List<File> getVariantHRHIncludes();

	/**
	 * Returns the list of compiler macros from the compiler prefix file (if any). This is the result
	 * of preprocessing the actual compiler pre-include file, e.g. rvct.h or gcce.h. WINSCW platform builds will return an empty list.
	 * @return a list of macros which may be empty.
	 */
	public List<IDefine> getCompilerPreincludeDefines();
	
	/**
	 * For platforms that are building with Symbian Binary Variation, this suffix will be included in the configuration name
	 * For example, if you are building variant armv5.product1, then this will return "product1" string.
	 * @return The name of the Symbian Binary Variation, or an empy String if the configuration is not a variation.
	 * @since 2.0
	 */
	public String getBuildVariationName();
	
	/**
	 * Load build context specific configuration settings
	 * @param ICStorageElement - a child of CARBIDE_STORAGE_ID ("CarbideConfigurationDataProvider") in .cproject data
	 * @since 3.0
	 *
	 */
	public void loadConfigurationSettings(ICStorageElement se);
	
	/**
	 * Save build context specific configuration settings
	 * @param ISymbianBuildContext 
	 * @param ICStorageElement - a child of CARBIDE_STORAGE_ID ("CarbideConfigurationDataProvider") in .cproject data
	 * @since 3.0
	 */
	public void saveConfigurationSettings(ICStorageElement se, ISymbianBuildContext ISymbianBuildContext);
	
	/**
	 * Get the <cconfiguration/> 'id' attribute for this configuration (from .cproject file). This is an ISymbianBuildContext wrapper.
	 * @return String
	 * @since 3.0
	 */
	String getConfigurationID();
	
}
