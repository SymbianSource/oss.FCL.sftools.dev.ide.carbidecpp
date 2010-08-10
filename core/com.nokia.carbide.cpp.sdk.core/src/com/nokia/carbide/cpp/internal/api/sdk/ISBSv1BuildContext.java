package com.nokia.carbide.cpp.internal.api.sdk;

import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;

/**
 * Build Context specific information for the SBS/Raptor Symbian Builder.
 * @see ICarbideBuildConfigation
 * @since 3.0
 * @deprecated - Temporary support exists for abld on Symbian^2 but will be removed, vFuture
 *
 */
public interface ISBSv1BuildContext extends ISymbianBuildContext {

	/**
	 * Platform constants
	 */
	public static final String EMULATOR_PLATFORM = "WINSCW";
	public static final String GCCE_PLATFORM = "GCCE";
	public static final String ARMV5_PLATFORM = "ARMV5";
	public static final String ARMV6_PLATFORM = "ARMV6";
	public static final String ARMV5_ABIV2_PLATFORM = "ARMV5_ABIV2";
	public static final String ARMV6_ABIV2_PLATFORM = "ARMV6_ABIV2";
	
	/**
	 * For Symbian Bianry Variation, platforms will be names <plat>.<variation>
	 * So in some cases you need to know only the platforms that the variant is based on.
	 * For example, a build platform name of "armv5.myvariant" will return "armv5".
	 * @return The base platform string.
	 * @see isSymbianBinaryVariation()
	 * @since 2.0
	 * @deprecated
	 */
	public String getBasePlatformForVariation();
	
	/**
	 * Get the (abld)build arguments info.  Contains pref settings from the Arguments tab.
	 * This only applies when building with SBSv1 (bldmake, abld)
	 * @return IBuildArgumentsInfo instance, never null
	 * @deprecated 
	 */
	IBuildArgumentsInfo getBuildArgumentsInfo();
	
	/**
	 * Get the (abld)build arguments info.  Contains pref settings from the Arguments tab.
	 * This only applies when building with SBSv1 (bldmake, abld)
	 * @return A copy of BuildArgumentsInfo instance, never null
	 * @deprecated
	 */
	BuildArgumentsInfo getBuildArgumentsInfoCopy();
	
	/**
	 * Set the build arguments info for SBSv2 build arguments. This only sets values in memory, does
	 * not write settings to disk. See 
	 * @return IBuildArgumentsInfo instance, never null
	 * @deprecated
	 */
	void setBuildArgumentsInfo(BuildArgumentsInfo bldArgInfo);
	
	
}
