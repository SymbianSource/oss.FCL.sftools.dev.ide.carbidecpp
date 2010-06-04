package com.nokia.carbide.cpp.sdk.core;

public interface ISBSv2BuildContext extends ISymbianBuildContext {
	
	public static final String BUILDER_ID = "com.nokia.carbide.builder.raptor";
	
	/**
	 * Retrieve the build-able configuration; a valid command that cab be passed with Raptor's -c parameter.
	 * This should not be used and should return null for abld-configurations.
	 * @return the configuration name, or null if none.
	 */
	public String getSBSv2Alias();
	
	/**
	 * Get the unique configuration ID for the SBSv2 build configuration
	 * @return
	 */
	public String getConfigID();
	
}
