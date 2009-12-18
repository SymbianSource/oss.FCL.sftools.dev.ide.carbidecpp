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
import org.osgi.framework.Version;

import com.nokia.carbide.cpp.internal.api.sdk.Messages;
import com.nokia.carbide.cpp.internal.api.sdk.SBSv2Utils;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.carbide.template.engine.ITemplate;
import com.nokia.carbide.templatewizard.process.AbstractProjectProcess;
import com.nokia.carbide.templatewizard.process.IParameter;
import com.nokia.cpp.internal.api.utils.core.HostOS;

/**
 * Process used to fill in S60 specific include symbols for INF/MMP files 
 */
public class CheckS60CustKitSupport extends AbstractProjectProcess {

	// copied from ProjectCreatedTasks (YUCK)
	private static final String SELECTED_BUILD_CONFIGS_VALUE_KEY = "selectedBuildConfigs"; //$NON-NLS-1$
	// copied from ProjectCreatedTasks (YUCK)
	private static final String SBSV2_BUILDER_ATTRIBUTE = "useSBSv2Builder"; //$NON-NLS-1$

	private static final String S60_50_BUILD_MACROS = "S60_50_CustomBuildIncludes";
	
	private static final String S60_INC_MACROS = "#include <data_caging_paths.hrh>\n#include <domain/osextensions/platform_paths.hrh>\nMW_LAYER_SYSTEMINCLUDE";
	private static final String S60_MIDDWARE_INC = "epoc32/include/middleware";
	
	private static final String S60_MIDDWARE_INC2 =  "epoc32/include/mw";
	private static final String S60_INC_MACROS2 = "#include <domain/osextensions/platform_paths.hrh>\nAPP_LAYER_SYSTEMINCLUDE";

	private static final String S60_SF_FOLDER =  "sf";
	private static final String S60_INC_MACROS_SF = "#include <platform_paths.hrh>\n#include <data_caging_paths.hrh>\nAPP_LAYER_SYSTEMINCLUDE";

	private static final String UNDEF_SBSV2 = "undefSBSV2";
	private static final String UNDEF_SBSV2_CODE = Messages.getString("CheckS60CustKitSupport.undefSBSV2Code"); //$NON-NLS-1$

	private static final String BUILD_HELP_PREFIX = "buildHelpPrefix";
	private static final String BUILD_HELP_SIS_PREFIX = "buildHelpSISPrefix";
	private static final String DISABLE_HELP_STRING = "//";
	private static final String DISABLE_HELP_PKG = ";";
	
	private static final String HELP_COMPILER_VARIABLE = "cshlpcmp";
	private static final String HELP_COMPILER_WIN32 = "cshlpcmp.bat";
	private static final String HELP_COMPILER_PERL = "cshlpcmp.pl";
	
	private static final String HELP_SUPPORT_MACRO = "helpSupport";
	private static final String HELP_SUPPORT_STRING = "MACRO _HELP_AVAILABLE_";
	private static final Version VERSION_9_5 = new Version(9, 5, 0);

	protected IProject project;

	@Override
	public void process(ITemplate template, List<IParameter> parameters, IProgressMonitor monitor) throws CoreException {
		super.process(template, parameters, monitor);
		
		String includesValue = "";
		includesValue = createCustKitIncludes(template);
		template.getTemplateValues().put(S60_50_BUILD_MACROS, includesValue);

		String helpCompiler = findHelpCompiler(template);
		String enableHelpString = "";
		String enableHelpSISString = "";
		String helpSupportString = "";
		if (helpCompiler != null) {
			helpSupportString = HELP_SUPPORT_STRING;
		}
		else {
			enableHelpString = DISABLE_HELP_STRING;
			enableHelpSISString = DISABLE_HELP_PKG;
		}
		template.getTemplateValues().put(HELP_COMPILER_VARIABLE, helpCompiler != null ? helpCompiler : "cshlpcmp"); //$NON-NLS-1$
		template.getTemplateValues().put(BUILD_HELP_PREFIX, enableHelpString);
		template.getTemplateValues().put(BUILD_HELP_SIS_PREFIX, enableHelpSISString);
		template.getTemplateValues().put(HELP_SUPPORT_MACRO, helpSupportString);
		
		if (usePrjExtensionsRequired(template)) {
			template.getTemplateValues().put(UNDEF_SBSV2, UNDEF_SBSV2_CODE);
		} else {
			template.getTemplateValues().put(UNDEF_SBSV2, ""); //$NON-NLS-1$
		}
	}

	@Override
	protected Plugin getPlugin() {
		return SDKCorePlugin.getDefault();
	}
	
	@SuppressWarnings("unchecked")
	private ISymbianBuildContext[] getBuildContexts(ITemplate template) {
		Object object = template.getTemplateValues().get(SELECTED_BUILD_CONFIGS_VALUE_KEY);
		if (object instanceof List) {
			List<ISymbianBuildContext> listOfBuildConfigs = (List<ISymbianBuildContext>) object;
			return (ISymbianBuildContext[]) listOfBuildConfigs
					.toArray(new ISymbianBuildContext[listOfBuildConfigs.size()]);
		}
		return new ISymbianBuildContext[0];
	}
	
	/**
	 * Check whether the help compiler is available, and what its name is.
	 * @param template
	 * @return
	 */
	private String findHelpCompiler(ITemplate template) {
		String[] helpCompilerNames;
		
		if (HostOS.IS_WIN32) {
			helpCompilerNames = new String[] { HELP_COMPILER_WIN32, HELP_COMPILER_PERL };
		} else {
			
			// TODO: the infrastructure for the techview/bldhelp.mk is a total mess 
			// currently, and it also appears that help is obsolete in recent devkits,
			// so just drop this
			if (!SBSv2Utils.enableSBSv1Support() && isSBSv2Project(template))
				return null;
			
			helpCompilerNames = new String[] { HELP_COMPILER_PERL };
		}
		for (ISymbianBuildContext symbianBuildContext : getBuildContexts(template)) {
			ISymbianSDK sdk = symbianBuildContext.getSDK();
			if (sdk != null) {
				File tools = new File(sdk.getEPOCROOT(), "epoc32/tools");
				for (String filename : helpCompilerNames) {
					File contextHelpCompiler = new File(tools, filename);
					if (contextHelpCompiler.exists()) {
						return contextHelpCompiler.getName();
					}
				}
			}
		}
		return null;
	}

	/**
	 * Check the SDK version and certain includes to figure out which include macros to add.
	 * Starting with S60 5.0 CustKits (2007 WK 24), many source bases are using macros for include paths.
	 * These may change over time with different SDK versions so make any tweaks to the include path
	 * values/logic here.
	 */
	private String createCustKitIncludes(ITemplate template){
		String S60_50_Macros_String = "";
		for (ISymbianBuildContext symbianBuildContext : getBuildContexts(template)) {
			ISymbianSDK sdk = symbianBuildContext.getSDK();
			if (sdk != null) {
				File middleWareInclude = new File(sdk.getEPOCROOT(), S60_MIDDWARE_INC);
				
				// NOTE: Here we need to check the SDK major version becuase
				// the 3.2 CustKit
				// has the middleware folder but doesn't use the new build
				// macros for include paths
				if (sdk.getSDKVersion().getMajor() >= 5 && middleWareInclude.exists()) {
					// add symbol as at least one build config is a CustKit
					S60_50_Macros_String = S60_INC_MACROS;
					break;
				}
				
				middleWareInclude = new File(sdk.getEPOCROOT(), S60_MIDDWARE_INC2);
				File sfoPath = new File(sdk.getEPOCROOT(), S60_SF_FOLDER);
				// check for middleware paths and /sf path (if SFO kit)
				if (sdk.getSDKVersion().getMajor() >= 5 && middleWareInclude.exists() && sfoPath.exists()) {
					// add symbol as at least one build config is a CustKit
					S60_50_Macros_String = S60_INC_MACROS_SF;
					break;
				}
				
				// try newer middleware paths moved to app layer includes
				
				if (sdk.getSDKVersion().getMajor() >= 5 && middleWareInclude.exists()) {
					// add symbol as at least one build config is a CustKit
					S60_50_Macros_String = S60_INC_MACROS2;
					break;
				}

			}
		}
		
		return S60_50_Macros_String;
	}
	
	/**
	 * Tell whether we should use PRJ_EXTENSIONS in bld.inf.  Raptor doesn't
	 * officially support [n|gnu]makefile statements in PRJ_MMPFILES anymore,
	 * and actively ignores them on Linux.
	 * @param template
	 * @return
	 */
	private boolean usePrjExtensionsRequired(ITemplate template) {
		
		// look for the directory housing the extension templates;
		// if this doesn't exist, then PRJ_EXTENSIONS won't work
		boolean makefileTemplatesAlwaysAvailable = true;

		boolean allPostSDK9_5 = true;

		for (ISymbianBuildContext symbianBuildContext : getBuildContexts(template)) {
			ISymbianSDK sdk = symbianBuildContext.getSDK();
			if (sdk != null) {
				File makefileTemplateDir = new File(sdk.getEPOCROOT(), "epoc32/tools/makefile_templates"); //$NON-NLS-1$
				if (!makefileTemplateDir.exists()) {
					// old location
					makefileTemplateDir = new File(sdk.getEPOCROOT(), "s60/tools/makefile_templates"); //$NON-NLS-1$
					if (!makefileTemplateDir.exists()) {
						makefileTemplatesAlwaysAvailable = false;
						break;
					}
				}
				
				if (sdk.getOSVersion().compareTo(VERSION_9_5) < 0) {
					allPostSDK9_5 = false;
				}
			}
		}
		
		if (!makefileTemplatesAlwaysAvailable)
			return false;
		
		// OS 9.5+ kits are supposed to be Raptorized.  Don't recommend extensions
		// unless we think they'll be present.
		//
		if (!allPostSDK9_5)
			return false;
		
		return true;
	}

	/**
	 * Tell whether the project was created for SBSv2.
	 * @param template
	 * @return
	 */
	private boolean isSBSv2Project(ITemplate template) {
		Object value = template.getTemplateValues().get(SBSV2_BUILDER_ATTRIBUTE);
		return value != null && value == Boolean.TRUE;
	}

}
