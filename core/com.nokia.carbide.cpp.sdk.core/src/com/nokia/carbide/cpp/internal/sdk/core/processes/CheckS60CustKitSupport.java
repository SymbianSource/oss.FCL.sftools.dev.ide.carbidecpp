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
package com.nokia.carbide.cpp.internal.sdk.core.processes;

import java.io.File;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Plugin;

import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.carbide.template.engine.ITemplate;
import com.nokia.carbide.templatewizard.process.AbstractProjectProcess;
import com.nokia.carbide.templatewizard.process.IParameter;
import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * Process used to fill in S60 specific include symbols for INF/MMP files 
 */
public class CheckS60CustKitSupport extends AbstractProjectProcess {
	
	private static final String SELECTED_BUILD_CONFIGS_VALUE_KEY = "selectedBuildConfigs"; //$NON-NLS-1$
	private static final String S60_50_BUILD_MACROS = "S60_50_CustomBuildIncludes";
	
	private static final String S60_INC_MACROS = "#include <data_caging_paths.hrh>\n#include <domain/osextensions/platform_paths.hrh>\nMW_LAYER_SYSTEMINCLUDE";
	private static final String S60_MIDDWARE_INC = "epoc32/include/middleware";
	
	private static final String S60_MIDDWARE_INC2 =  "epoc32/include/mw";

	private static final String S60_DOMAND_OSTEXT_PLAT_PATHS = "epoc32/include/domain/osextensions/platform_paths.hrh"; 
	private static final String S60_DOMAND_OSTEXT_PLAT_PATHS_INCLUDE = "#include <domain/osextensions/platform_paths.hrh>";
	private static final String APP_LAYER_SYSTEM_INCLUDES = "APP_LAYER_SYSTEMINCLUDE"; 
	
	private static final String S60_SF_FOLDER =  "sf";
	private static final String S60_INC_MACROS_SF = "#include <platform_paths.hrh>\n#include <data_caging_paths.hrh>\nAPP_LAYER_SYSTEMINCLUDE";

	private static final String BUILD_HELP_PREFIX = "buildHelpPrefix";
	private static final String BUILD_HELP_SIS_PREFIX = "buildHelpSISPrefix"; 
	private static final String DISABLE_HELP_STRING = "//";
	private static final String DISABLE_HELP_PKG = ";"; 
	private static final String HELP_COMPILER = "epoc32/tools/cshlpcmp.bat";
	private static final String HELP_SUPPORT_MACRO = "helpSupport";
	private static final String HELP_SUPPORT_STRING = "MACRO _HELP_AVAILABLE_";

	protected IProject project;

	@Override
	public void process(ITemplate template, List<IParameter> parameters, IProgressMonitor monitor) throws CoreException {
		super.process(template, parameters, monitor);
		
		String includesValue = "";
		includesValue = createCustKitIncludes(template);
		template.getTemplateValues().put(S60_50_BUILD_MACROS, includesValue);

		boolean hasHelp = isHelpCompilerAvailable(template);
		String enableHelpString = "";
		String helpSupportString = "";
		String enableHelpSISString = ""; 
		if (hasHelp) {
			helpSupportString = HELP_SUPPORT_STRING;
		}
		else {
			enableHelpString = DISABLE_HELP_STRING;
			enableHelpSISString = DISABLE_HELP_PKG; 
		}
		template.getTemplateValues().put(BUILD_HELP_PREFIX, enableHelpString);
		template.getTemplateValues().put(BUILD_HELP_SIS_PREFIX, enableHelpSISString); 
		template.getTemplateValues().put(HELP_SUPPORT_MACRO, helpSupportString);
	}

	@Override
	protected Plugin getPlugin() {
		return SDKCorePlugin.getDefault();
	}
	
	private boolean isHelpCompilerAvailable(ITemplate template) {
		Object object = template.getTemplateValues().get(SELECTED_BUILD_CONFIGS_VALUE_KEY);
		if (object instanceof List) {
			List listOfBuildConfigs = (List) object;
			for (Object obj : listOfBuildConfigs) {
				Check.checkContract(obj instanceof ISymbianBuildContext);
				ISymbianBuildContext symbianBuildContext = (ISymbianBuildContext)obj;
				ISymbianSDK sdk = symbianBuildContext.getSDK();
				if (sdk != null) {
					File contextHelpCompiler = new File(sdk.getEPOCROOT() + HELP_COMPILER);
					if (contextHelpCompiler.exists()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Check the SDK version and certain includes to figure out which include macros to add.
	 * Starting with S60 5.0 CustKits (2007 WK 24), many source bases are using macros for include paths.
	 * These may change over time with different SDK versions so make any tweaks to the include path
	 * values/logic here.
	 */
	private String createCustKitIncludes(ITemplate template){
		Object object = template.getTemplateValues().get(SELECTED_BUILD_CONFIGS_VALUE_KEY);
		String S60_50_Macros_String = "";
		if (object instanceof List) {
			List listOfBuildConfigs = (List) object;
			for (Object obj : listOfBuildConfigs) {
				Check.checkContract(obj instanceof ISymbianBuildContext);
				ISymbianBuildContext symbianBuildContext = (ISymbianBuildContext)obj;
				ISymbianSDK sdk = symbianBuildContext.getSDK();
				if (sdk != null) {
					File middleWareInclude = new File(sdk.getEPOCROOT() + S60_MIDDWARE_INC);
					
					// NOTE: Here we need to check the SDK major version becuase
					// the 3.2 CustKit
					// has the middleware folder but doesn't use the new build
					// macros for include paths
					if (sdk.getSDKVersion().getMajor() >= 5 && middleWareInclude.exists()) {
						// add symbol as at least one build config is a CustKit
						S60_50_Macros_String = S60_INC_MACROS;
						break;
					}
					
					middleWareInclude = new File(sdk.getEPOCROOT() + S60_MIDDWARE_INC2);
					File sfoPath = new File(sdk.getEPOCROOT() + S60_SF_FOLDER);
					// check for middleware paths and /sf path (if SFO kit)
					if (sdk.getSDKVersion().getMajor() >= 5 && middleWareInclude.exists() && sfoPath.exists()) {
						// add symbol as at least one build config is a CustKit
						S60_50_Macros_String = S60_INC_MACROS_SF;
						break;
					}
					
					// try newer middleware paths moved to app layer includes
					
					if (sdk.getSDKVersion().getMajor() >= 5 && middleWareInclude.exists()) {
						// add symbol as at least one build config is a CustKit
						File domainPath = new File(sdk.getEPOCROOT(), S60_DOMAND_OSTEXT_PLAT_PATHS); 
						if (domainPath.exists()){ 
							S60_50_Macros_String = S60_DOMAND_OSTEXT_PLAT_PATHS_INCLUDE + "\n"; 
						}
						//else { 
						// The platform_paths include is in the variant.cfg defined prefix file
						//} 
						
						S60_50_Macros_String += APP_LAYER_SYSTEM_INCLUDES; 
						
						break;
					}

				}
			}
		}
		
		return S60_50_Macros_String;
	}
	
}
