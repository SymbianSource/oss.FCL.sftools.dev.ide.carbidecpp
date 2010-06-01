package com.nokia.carbide.cpp.sdk.core;

public interface ISBSv1BuildContext extends ISymbianBuildContext {

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
