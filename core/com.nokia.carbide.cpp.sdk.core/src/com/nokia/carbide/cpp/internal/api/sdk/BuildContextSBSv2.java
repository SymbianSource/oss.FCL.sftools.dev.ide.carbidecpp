package com.nokia.carbide.cpp.internal.api.sdk;

import java.io.File;
import java.util.List;

import org.eclipse.core.runtime.IPath;

import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.sdk.core.ISBSv2BuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;

public class BuildContextSBSv2 implements ISBSv2BuildContext {
	
	private String platform;
	private String target;
	private String sbsv2Alias;
	ISymbianSDK sdk;
	
	public BuildContextSBSv2(ISymbianSDK theSDK, String thePlatform, String theTarget, String theSBSv2Alias) {
		sdk = theSDK;
		platform = thePlatform.toUpperCase();
		target = theTarget.toUpperCase();
		sbsv2Alias = theSBSv2Alias;
		
		getDisplayString();
	}

	@Override
	public ISymbianSDK getSDK() {
		return sdk;
	}

	@Override
	public String getPlatformString() {
		return platform;
	}

	@Override
	public String getTargetString() {
		return target;
	}

	@Override
	public String getDisplayString() {
		// TODO Auto-generated method stub
		return sbsv2Alias;
	}

	@Override
	public String getDefaultDefFileDirectoryName(boolean isASSP) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPath getCompilerPrefixFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IDefine> getVariantHRHDefines() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<File> getPrefixFileIncludes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IDefine> getCompilerMacros() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBuildVariationName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSymbianBinaryVariation() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * See if the build configuration is an SBSv2 alias, and if so get the build-able alias name 
	 * @param displayName
	 * @return The full SBSv2 alias that can be used with -c, otherwise null if not SBSv2
	 */
	private static String getSBSv2AliasFromConfigName(String displayName) {
		int indexBegin = displayName.indexOf("(");  //$NON-NLS-1$
		int indexEnd = displayName.indexOf(")");  //$NON-NLS-1$
		if (indexBegin > 0 && indexEnd > 0){
			String configPart =  displayName.substring(indexBegin+1, indexEnd);
			if (configPart.split("_").length > 1){
				return configPart;
			}
		} 
		
		return null;
	}

	@Override
	public String getSBSv2Alias() {
		return sbsv2Alias;
	}

	

}
