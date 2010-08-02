package com.nokia.carbide.cpp.sdk.core.test;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.nokia.carbide.cpp.internal.api.sdk.ISBSv2BuildContext;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv2BuildInfo;
import com.nokia.carbide.cpp.sdk.core.ISDKBuildInfo;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuilderID;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;

public class TestSBSv2BuildContext extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testBuildContextsInSDKs() throws Exception {
		List<ISymbianSDK> sdkList = SDKCorePlugin.getSDKManager().getSDKList();
		for (Iterator<ISymbianSDK> sdkItr = sdkList.iterator(); sdkItr.hasNext();) {
			ISymbianSDK sdk = sdkItr.next();
			ISDKBuildInfo buildInfo = sdk.getBuildInfo(ISymbianBuilderID.SBSV2_BUILDER);
			if (buildInfo != null) {
				assertTrue(buildInfo instanceof ISBSv2BuildInfo);
				List<ISymbianBuildContext> contextList = buildInfo.getFilteredBuildConfigurations();
				if (contextList != null && contextList.size() > 0) {
					for (Iterator<ISymbianBuildContext> cItr = contextList.iterator(); cItr.hasNext();) {
						ISymbianBuildContext context = cItr.next();
						assertTrue(context instanceof ISBSv2BuildContext);
						testBuildContext((ISBSv2BuildContext)context);
					}
				}
			}
		}
	}

	private void testBuildContext(ISBSv2BuildContext context) {
		// getConfigQueryData()
		assertNotNull(context.getConfigQueryData());

		// getConfigID()
		assertNotNull(context.getConfigID());

		// getDisplayString()
		assertNotNull(context.getDisplayString());

		// getDefaultDefFileDirectoryName()
		assertNotNull(context.getDefaultDefFileDirectoryName());

		// getPlatformString()
		assertNotNull(context.getPlatformString());

		// getSBSv2Alias()
		assertNotNull(context.getSBSv2Alias());
		String sbsv2Alias = context.getSBSv2Alias();
		
		// getTargetString()
		assertNotNull(context.getTargetString());

		// getCompilerPrefixFile()
		if (sbsv2Alias.toUpperCase().contains(ISBSv2BuildContext.TOOLCHAIN_GCCE) ||
			sbsv2Alias.toUpperCase().contains(ISBSv2BuildContext.TOOLCHAIN_ARM)) {
			assertNotNull(context.getCompilerPrefixFile());
		}

		// getVariantHRHDefines()
		assertNotNull(context.getVariantHRHDefines());

		// getPrefixFileIncludes()
		assertNotNull(context.getPrefixFileIncludes());
		for (Iterator<File> itr = context.getPrefixFileIncludes().iterator(); itr.hasNext();) {
			File includeFile = itr.next();
			assertTrue(includeFile.exists());
		}

		// getCompilerMacros()
		assertNotNull(context.getCompilerMacros());		

		// getToolChain()
		String toolChain = context.getToolChain();
		assertNotNull(toolChain);
		Map<String, String> buildMacros = context.getConfigQueryData().getBuildMacros();
		if (buildMacros != null) {
			if (buildMacros.containsKey(ISBSv2BuildContext.MACRO_ARM)) {
				assertTrue(toolChain.equals(ISBSv2BuildContext.TOOLCHAIN_ARM));
			} else if (buildMacros.containsKey(ISBSv2BuildContext.MACRO_GCCE)) {
				assertTrue(toolChain.equals(ISBSv2BuildContext.TOOLCHAIN_GCCE));
			} else if (buildMacros.containsKey(ISBSv2BuildContext.MACRO_WINSCW)) {
				assertTrue(toolChain.equals(ISBSv2BuildContext.TOOLCHAIN_WINSCW));
			} else {
				assertTrue(toolChain.equals(ISBSv2BuildContext.TOOLCHAIN_UNKNOWN));
			}
		}

	}

}
