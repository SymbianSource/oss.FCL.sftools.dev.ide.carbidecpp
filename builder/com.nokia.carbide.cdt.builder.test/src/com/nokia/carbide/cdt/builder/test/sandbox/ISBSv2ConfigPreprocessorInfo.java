package com.nokia.carbide.cdt.builder.test.sandbox;

import java.util.List;

import org.eclipse.core.runtime.IPath;

public interface ISBSv2ConfigPreprocessorInfo {

	List<String> getMacroList();
	
	List<IPath>	getSystemIncludes();
	
	IPath getVariantConfig();
	
	String getCompiler();
	
	IPath getCompilerPrefix();
	
	ISBSv2ConfigData getSBSv2QueryConfigData();
	
}
