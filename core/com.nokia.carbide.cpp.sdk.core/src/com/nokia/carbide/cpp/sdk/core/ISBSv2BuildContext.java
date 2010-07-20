package com.nokia.carbide.cpp.sdk.core;


public interface ISBSv2BuildContext extends ISymbianBuildContext {
	
	public static final String BUILDER_ID = "com.nokia.carbide.builder.raptor";
	
	public static final String TOOLCHAIN_ARM = "ARM";
	public static final String TOOLCHAIN_GCCE = "GCCE";
	public static final String TOOLCHAIN_WINSCW = "WINSCW";
	public static final String TOOLCHAIN_UNKNOWN = "UNKNOWN";
	
	public static final String MACRO_ARM = "__ARMCC__";
	public static final String MACRO_GCCE = "__GCCE__";
	public static final String MACRO_WINSCW = "__WINSCW__";
	
	/**
	 * Retrieve the build-able configuration; a valid command that cab be passed with Raptor's -c parameter.
	 * This should not be used and should return null for abld-configurations.
	 * @return the configuration name, or null if none.
	 */
	public String getSBSv2Alias();
	
	/**
	 * Get the unique configuration ID for the SBSv2 build configuration.
	 * @return configuration ID
	 */
	public String getConfigID();
	
	/**
	 * Get the implicit directory searched for *.def files by the DEFFILE statement.
	 * @return bare directory name (e.g. 'BWINS', 'BMARM', 'EABI')
	 */
	public String getDefaultDefFileDirectoryName();
	
	/**
	 * Get stored result from Raptor config query.
	 * @return ISBSv2ConfigQueryData object
	 */
	public ISBSv2ConfigQueryData getConfigQueryData();
	
	/**
	 * Get tool chain for the build context
	 * @return tool chain name (e.g. 'ARM', 'GCCE', 'WINSCW')
	 */
	public String getToolChain();
	
}
