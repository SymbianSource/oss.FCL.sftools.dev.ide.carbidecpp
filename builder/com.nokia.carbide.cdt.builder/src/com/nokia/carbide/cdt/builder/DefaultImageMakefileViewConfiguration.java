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
* A default configuration for handling image makefiles.
* <p>
* This only provides useful information for S60.
*
*
*/
package com.nokia.carbide.cdt.builder;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageBuilderCommandLineConverter;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.ImageBuilderCommandLineConverterFactory;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IViewFilter;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;

public class DefaultImageMakefileViewConfiguration extends DefaultGNUMakefileViewConfiguration
		implements IImageMakefileViewConfiguration {

	/**
	 * Create a view configuration for the given project's information
	 * and view filter.
	 * <p>
	 * The build context may not be null.
	 */
	public DefaultImageMakefileViewConfiguration(ICarbideProjectInfo info, IViewFilter viewFilter) {
		super(info.getProject(), info.getDefaultConfiguration(), viewFilter);
	}
	
	/**
	 * Create a view configuration for the given project and build context
	 * and view filter.
	 * <p>
	 * The build context may be null.
	 */
	public DefaultImageMakefileViewConfiguration(IProject project, ISymbianBuildContext context, IViewFilter viewFilter) {
		super(project, context, viewFilter);
	}

	/**
	 * Create a view configuration for the given project and build context
	 * and view filter.
	 * <p>
	 * The build context may be null.
	 */
	public DefaultImageMakefileViewConfiguration(ISymbianBuildContext context, IPath bldInfPath, IViewFilter viewFilter) {
		super(context, bldInfPath, viewFilter);
	}

	/**
	 * Create a view configuration for the given project and view filter.
	 */
	public DefaultImageMakefileViewConfiguration(IPath bldInfPath, IViewFilter viewFilter) {
		super(bldInfPath, viewFilter);
	}


	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileViewConfiguration#getImageBuilderCommandLineConverter()
	 */
	public IImageBuilderCommandLineConverter getImageBuilderCommandLineConverter() {
		return ImageBuilderCommandLineConverterFactory.createMifConvConverter();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileViewConfiguration#getImageBuilderName()
	 */
	public String getImageBuilderName() {
		return "mifconv";
	}

}
