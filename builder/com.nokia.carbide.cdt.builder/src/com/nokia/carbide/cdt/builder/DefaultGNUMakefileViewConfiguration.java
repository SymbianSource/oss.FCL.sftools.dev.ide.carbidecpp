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

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.epoc.engine.model.IViewParserConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.IMakefileViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IViewFilter;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * A default configuration for handling GNU makefiles.
 */
public class DefaultGNUMakefileViewConfiguration implements
		IMakefileViewConfiguration {

	private IProject project;
	private ISymbianBuildContext context;
	private IViewFilter viewFilter;
	private IPath bldinfPath;

	/**
	 * Create a view configuration for the given project's default build context
	 * and view filter.
	 * <p>
	 * The build context may be null.
	 */
	public DefaultGNUMakefileViewConfiguration(ICarbideProjectInfo info, IViewFilter viewFilter) {
		this(info.getProject(), info.getDefaultConfiguration(), viewFilter);
	}
	
	/**
	 * Create a view configuration for the given project and build context
	 * and view filter.
	 * <p>
	 * The build context may be null.
	 */
	public DefaultGNUMakefileViewConfiguration(IProject project, ISymbianBuildContext context, IViewFilter viewFilter) {
		Check.checkArg(project);
		this.project = project;
		this.bldinfPath = null;
		this.context = context;
		this.viewFilter = viewFilter;
	}

	/**
	 * Create a view configuration for the given project and build context
	 * and view filter.
	 * <p>
	 * The build context may be null.
	 */
	public DefaultGNUMakefileViewConfiguration(ISymbianBuildContext context, IPath bldInfPath, IViewFilter viewFilter) {
		this.project = null;
		this.bldinfPath = bldInfPath;
		this.context = context;
		this.viewFilter = viewFilter;
	}

	/**
	 * Create a view configuration for the given project and view filter.
	 */
	public DefaultGNUMakefileViewConfiguration(IPath bldInfPath, IViewFilter viewFilter) {
		this.project = null;
		this.bldinfPath = bldInfPath;
		this.context = null;
		this.viewFilter = viewFilter;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.makefile.IMakefileViewConfiguration#getMakefileStyle()
	 */
	public String getMakefileStyle() {
		return "GNU";
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration#getMacros()
	 */
	public Collection<IDefine> getMacros() {
		return Collections.EMPTY_LIST;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration#getViewFilter()
	 */
	public IViewFilter getViewFilter() {
		return viewFilter;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration#getViewParserConfiguration()
	 */
	public IViewParserConfiguration getViewParserConfiguration() {
		return new DefaultViewParserConfiguration(project, context, bldinfPath);
	}

}
