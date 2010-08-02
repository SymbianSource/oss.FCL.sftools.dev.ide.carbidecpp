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
* Contributors:
*
* Description: 
*
*/
package com.nokia.carbide.cdt.builder;

import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.IViewParserConfiguration;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.*;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv1BuildInfo;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv2BuildContext;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv2BuildInfo;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuilderID;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

import java.util.*;

public class DefaultViewConfiguration implements IViewConfiguration {
	protected IProject project;
	protected ISymbianBuildContext context;
	protected IPath bldInfPath;
	protected IViewFilter viewFilter;
	protected DefaultViewParserConfiguration viewParserConfiguration = null;
	protected List<IDefine> extraMacros = new ArrayList<IDefine>(0);
	protected IPath projectPath;
	
	/**
	 * Create an "all" view configuration for the given project info.
	 */
	public DefaultViewConfiguration(ICarbideProjectInfo projectInfo) {
		Check.checkArg(projectInfo);
		this.project = projectInfo.getProject();
		this.context = projectInfo.getDefaultConfiguration().getBuildContext();
		this.bldInfPath = null;
		this.viewFilter = new AllNodesViewFilter();
		wrapViewFilter();
	}

	/**
	 * Create a view configuration that obeys the settings for the given build context
	 * (may not be null).
	 */
	public DefaultViewConfiguration(ICarbideProjectInfo projectInfo, ISymbianBuildContext buildContext) {
		Check.checkArg(projectInfo);
		Check.checkArg(buildContext);
		this.project = projectInfo.getProject();
		this.context = buildContext;
		this.bldInfPath = null;
		this.viewFilter = new AcceptedNodesViewFilter();
		wrapViewFilter();
	}

	/**
	 * Create a view configuration for the given project and build context
	 * and view filter.
	 * <p>
	 * The build context may be null.
	 */
	public DefaultViewConfiguration(IProject project, ISymbianBuildContext context, IViewFilter viewFilter) {
		this.project = project;
		this.context = context;
		this.bldInfPath = null;
		this.viewFilter = viewFilter;
		wrapViewFilter();
	}

	/**
	 * Create a view configuration for the given full path to bld.inf,
	 * build context, and view filter.
	 * <p>
	 * The build context may be null.
	 */
	public DefaultViewConfiguration(ISymbianBuildContext context, IPath bldInfPath, IViewFilter viewFilter) {
		this.project = null;
		this.context = context;
		this.bldInfPath = bldInfPath;
		this.viewFilter = viewFilter;
		wrapViewFilter();
	}

	/**
	 * Create a view configuration for the given full path to bld.inf
	 * and view filter.
	 */
	public DefaultViewConfiguration(IPath bldInfPath, IViewFilter viewFilter) {
		this.project = null;
		this.context = null;
		this.bldInfPath = bldInfPath;
		this.viewFilter = viewFilter;
		wrapViewFilter();
	}

	/**
	 * Create an "all" view configuration for the given project and view filter.
	 */
	public DefaultViewConfiguration(IPath projectPath) {
		this.project = null;
		this.projectPath = projectPath;
		this.context = null;
		this.bldInfPath = null;
		this.viewFilter = new AllNodesViewFilter();
		wrapViewFilter();
	}


	/**
	 * For the purpose of caching view data, we need to distinguish view filters
	 * associated with ISymbianBuildContext from each other
	 */
	private void wrapViewFilter() {
		if (viewFilter instanceof AcceptedNodesViewFilter && context != null) {
			viewFilter = new AcceptedBuildContextNodesViewFilter(context);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bldInfPath == null) ? 0 : bldInfPath.hashCode());
		result = prime * result + ((context == null) ? 0 : context.hashCode());
		result = prime * result
				+ ((extraMacros == null) ? 0 : extraMacros.hashCode());
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		result = prime * result
				+ ((projectPath == null) ? 0 : projectPath.hashCode());
		result = prime * result
				+ ((viewFilter == null) ? 0 : viewFilter.hashCode());
		result = prime
				* result
				+ ((viewParserConfiguration == null) ? 0
						: viewParserConfiguration.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final DefaultViewConfiguration other = (DefaultViewConfiguration) obj;
		if (bldInfPath == null) {
			if (other.bldInfPath != null)
				return false;
		} else if (!bldInfPath.equals(other.bldInfPath))
			return false;
		if (context == null) {
			if (other.context != null)
				return false;
		} else if (!context.equals(other.context))
			return false;
		if (extraMacros == null) {
			if (other.extraMacros != null)
				return false;
		} else if (!extraMacros.equals(other.extraMacros))
			return false;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		if (projectPath == null) {
			if (other.projectPath != null)
				return false;
		} else if (!projectPath.equals(other.projectPath))
			return false;
		if (viewFilter == null) {
			if (other.viewFilter != null)
				return false;
		} else if (!viewFilter.equals(other.viewFilter))
			return false;
		if (viewParserConfiguration == null) {
			if (other.viewParserConfiguration != null)
				return false;
		} else if (!viewParserConfiguration
				.equals(other.viewParserConfiguration))
			return false;
		return true;
	}

	public Collection<IDefine> getMacros() {
		List<IDefine> macros = new ArrayList<IDefine>();
		if (context != null) {
			ISymbianSDK sdk = context.getSDK();

			if (context instanceof ISBSv2BuildContext){
				macros.add(DefineFactory.createDefine("SBSV2", null));
				ISBSv2BuildInfo sbsv2BuildInfo = (ISBSv2BuildInfo)sdk.getBuildInfo(ISymbianBuilderID.SBSV2_BUILDER);
				if (sbsv2BuildInfo != null) {
					Map<String, String> platMacros = sbsv2BuildInfo.getPlatformMacros(context.getPlatformString());
					for (Iterator<String> it = platMacros.keySet().iterator(); it.hasNext(); ) { 
						String name = it.next();
						String value = platMacros.get(name);
						macros.add(DefineFactory.createDefine(name, value));
					} 
				}
			} else {
				ISBSv1BuildInfo sbsv1BuildInfo = (ISBSv1BuildInfo)sdk.getBuildInfo(ISymbianBuilderID.SBSV1_BUILDER);
				if (sbsv1BuildInfo != null) {
					for (String platMacro : sbsv1BuildInfo.getPlatformMacros(context.getPlatformString())) {
						macros.add(DefineFactory.createDefine(platMacro.trim(), platMacro.trim()));
					}
					
					for (String vendorMacro : sbsv1BuildInfo.getVendorSDKMacros()){
						macros.add(DefineFactory.createDefine(vendorMacro.trim(), vendorMacro.trim()));
					}
				}
			}
			
			for (IDefine macro : context.getVariantHRHDefines()) {
				macros.add(macro);
			}
		}
		macros.addAll(extraMacros);
		
		// XXX: this is the only way to improve performance
		//return macros.subList(0, Math.min(10, macros.size()));
		return macros;
	}

	/** 
	 * Access/modify a list of additional macros provided to the view configuration,
	 * for testing purposes.
	 * */
	public List<IDefine> getExtraMacros() {
		return extraMacros ;
	}
	
	public IViewFilter getViewFilter() {
		return viewFilter;
	}

	public IViewParserConfiguration getViewParserConfiguration() {
		if (viewParserConfiguration == null) {
			if (project != null || bldInfPath != null) {
				viewParserConfiguration = new DefaultViewParserConfiguration(project, context, bldInfPath);
			} else {
				viewParserConfiguration = new DefaultViewParserConfiguration(projectPath);
			}
		}
		return viewParserConfiguration;
	}

}
