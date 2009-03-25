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
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.*;

import com.nokia.carbide.cpp.sdk.core.*;
import com.nokia.carbide.template.engine.ITemplate;
import com.nokia.carbide.templatewizard.process.AbstractProjectProcess;
import com.nokia.carbide.templatewizard.process.IParameter;
import com.nokia.cpp.internal.api.utils.core.*;

/**
 * Process used to fill in S60 specific include symbols for INF/MMP files 
 */
public class CheckS60CustKitSupport extends AbstractProjectProcess {
	
	private static final String SELECTED_BUILD_CONFIGS_VALUE_KEY = "selectedBuildConfigs"; //$NON-NLS-1$
	private static final String S60_50_BUILD_MACROS = "S60_50_CustomBuildIncludes";
	private static final String S60_INC_MACROS = "#include <data_caging_paths.hrh>\n#include <domain/osextensions/platform_paths.hrh>\nMW_LAYER_SYSTEMINCLUDE";
	private static final String S60_MIDDWARE_INC = "epoc32/include/middleware";
	protected IProject project;

	@Override
	public void process(ITemplate template, List<IParameter> parameters, IProgressMonitor monitor) throws CoreException {
		super.process(template, parameters, monitor);
		
		String includesValue = "";
		includesValue = createCustKitIncludes(template);
		template.getTemplateValues().put(S60_50_BUILD_MACROS, includesValue);
	}

	@Override
	protected Plugin getPlugin() {
		return SDKCorePlugin.getDefault();
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
				if (sdk != null){
					File middleWareInclude = new File(sdk.getEPOCROOT() + S60_MIDDWARE_INC);
					// NOTE: Here we need to check the SDK major version becuase the 3.2 CustKit
					// has the middleware folder but doesn't use the new build macros for include paths
					if (sdk.getSDKVersion().getMajor() >= 5 && middleWareInclude.exists()){
						// add symbol as at least one build config is a CustKit
						S60_50_Macros_String = S60_INC_MACROS;
						break;
					}
				}
			}
		}
		
		return S60_50_Macros_String;
	}
	
}
