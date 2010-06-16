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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IViewFilter;
import com.nokia.carbide.cpp.sdk.core.ISBSv1BuildContext;
import com.nokia.carbide.cpp.sdk.core.ISBSv2BuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;

public class DefaultMMPViewConfiguration extends DefaultViewConfiguration implements IMMPViewConfiguration {

	/** Configuration for the default build configuration of the project */
	public DefaultMMPViewConfiguration(ICarbideProjectInfo info, IViewFilter viewFilter) {
		super(info.getProject(), info.getDefaultConfiguration().getBuildContext(), viewFilter);
	}
	/** Configuration for the given build configuration of the project with the given filter */
	public DefaultMMPViewConfiguration(IProject project, ISymbianBuildContext context, IViewFilter viewFilter) {
		super(project, context, viewFilter);
	}

	/** Configuration for the given SDK using the given full path to bld.inf and the given filter */
	public DefaultMMPViewConfiguration(ISymbianBuildContext context, IPath bldInfPath, IViewFilter viewFilter) {
		super(context, bldInfPath, viewFilter);
	}

	/** Configuration for no SDK and the given full path to bld.inf and the given filter */
	public DefaultMMPViewConfiguration(IPath bldInfPath, IViewFilter viewFilter) {
		super(bldInfPath, viewFilter);
	}

	/** Configuration for the given build configuration of the project with the given filter */
	public DefaultMMPViewConfiguration(ICarbideBuildConfiguration buildConfiguration, IViewFilter viewFilter) {
		super(buildConfiguration.getCarbideProject().getProject(), buildConfiguration.getBuildContext(), viewFilter);
	}

	/**
	 * Configuration for "all" filtering based at the given location. 
	 */
	public DefaultMMPViewConfiguration(IPath projectPath) {
		super(projectPath);
	}
	
	public boolean isStatementSupported(EMMPStatement statement) {
		// we're not interested in this level of detail.
		return true;
	}

	public String getDefaultDefFileBase() {
		if (context != null)
			return context.getDefaultDefFileDirectoryName();
		return null;
	}
	
	public boolean isEmulatorBuild() {
		if (context != null){
			if (context instanceof ISBSv1BuildContext){
				return context.getPlatformString().equals(ISBSv1BuildContext.EMULATOR_PLATFORM);
			} else if (context instanceof ISBSv2BuildContext){
				return ((ISBSv2BuildContext)context).getSBSv2Alias().toUpperCase().contains(ISBSv2BuildContext.TOOLCHAIN_WINSCW);
			}
		}
		return false;
	}
}
