package com.nokia.carbide.cpp.sdk.core;

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
	 */
	public String getBasePlatformForVariation();
	
}
