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
	 * Platform constants
	 */
	public static final String EMULATOR_PLATFORM = "WINSCW";
	public static final String ARMI_PLATFORM = "ARMI";
	public static final String ARM4_PLATFORM = "ARM4";
	public static final String THUMB_PLATFORM = "THUMB";
	public static final String GCCE_PLATFORM = "GCCE";
	public static final String ARMV5_PLATFORM = "ARMV5";
	public static final String ARMV6_PLATFORM = "ARMV6";
	public static final String ARMV5_ABIV2_PLATFORM = "ARMV5_ABIV2";
	public static final String ARMV6_ABIV2_PLATFORM = "ARMV6_ABIV2";
	
	/**
	 * DEFAULT suffix to use for components that are specified as variant, but don't buid as variants. For example, plain ARMV5, when specified as
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
	 * For Symbian Bianry Variation, platforms will be names <plat>.<variation>
	 * So in some cases you need to know only the platforms that the variant is based on.
	 * For example, a build platform name of "armv5.myvariant" will return "armv5".
	 * @return The base platform string.
	 * @see isSymbianBinaryVariation()
	 * @since 2.0
	 */
	public String getBasePlatformForVariation();
	
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
	 * @param isASSP true if targeting ASSP
	 * @return bare directory name (e.g. 'BWINS', 'BMARM', 'EABI')
	 */
	public String getDefaultDefFileDirectoryName(boolean isASSP);
	
	/**
	 * Get the prefix file used at build time.  This usually provides
	 * definitions of common macros.
	 * @return full path, or null if no prefix file known
	 */
	public IPath getCompilerPrefixFile();

	/**
	 * Returns the list of all vendor specific C/C++ macros for this SDK.  The list of macros is defined in
	 * the variant configuration file inside \epoc32\tools\variant.cfg (or \epoc32\tools\spp_variant.cfg
	 * for platform variation SDKs).  The file itself contains an HRH file entry and may also contain macro definitions
	 * thereafter.
	 * @return a list of macros which may be empty.
	 */
	public List<IDefine> getVariantHRHDefines();
	
	/**
	 * Returns the list of all header files recursively included by the SDK prefix file.  Note that the list of
	 * files could be different for different context's since the platform can potentially change the list of include
	 * paths.
	 * @return a list of header files which may be empty
	 */
	public List<File> getPrefixFileIncludes();

	/**
	 * Returns the list of compiler macros from the compiler prefix file (if any).
	 * @return a list of macros which may be empty.
	 */
	public List<IDefine> getCompilerMacros();
	
	/**
	 * For platforms that are building with Symbian Binary Variation, this suffix will be included in the configuration name
	 * For example, if you are building variant armv5.product1, then this will return "product1" string.
	 * @return The name of the Symbian Binary Variation, or an empy String if the configuration is not a variation.
	 * @since 2.0
	 */
	public String getBuildVariationName();
	
	/**
	 * Is the current build context building as a Symbian Binary Variation? Not to be confused with BSF.
	 * @return true if this is a binary variant build
	 * @since 2.0
	 */
	public boolean isSymbianBinaryVariation();
	
	/**
	 * Retrieve the build-able configuration; a valid command that cab be passed with Raptor's -c parameter.
	 * This should not be used and should return null for abld-configurations.
	 * @return the configuration name, or null if none.
	 */
	public String getSBSv2Alias();
	
	public void setSBSv2Alias(String newAlias);
	
	public void setDisplayString(String newDisplayName);
}
