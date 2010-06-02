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
* This default include file locator provides system #include directories
* based on an SDK's include directory and variant.hrh directory.
*
*
*/
package com.nokia.carbide.cdt.builder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;

import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.internal.api.sdk.BuildContextSBSv1;
import com.nokia.carbide.cpp.internal.api.sdk.BuildContextSBSv2;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.BasicIncludeFileLocator;
import com.nokia.cpp.internal.api.utils.core.Check;

public class DefaultIncludeFileLocator extends BasicIncludeFileLocator {
	/**
	 * Create default #include locator that searches the same directory
	 * as an #including file and optionally the given
	 * SDK's epoc32\include directory.  
	 * @param project the project providing the bld.inf path
	 * @param buildContext the build context, or null
	 */
	public DefaultIncludeFileLocator(IProject project, ISymbianBuildContext buildContext) {
		super(null, null);
		List<File> systemPaths = new ArrayList<File>();
		if (buildContext != null && buildContext.getSDK() != null) {
			// search implicit bld.inf directory if known
			if (project != null) {
				ICarbideBuildManager buildManager = CarbideBuilderPlugin.getBuildManager();
				ICarbideProjectInfo cpi = buildManager.getProjectInfo(project);
				if (cpi != null && cpi.getAbsoluteBldInfPath() != null) {
					systemPaths.add(cpi.getAbsoluteBldInfPath().removeLastSegments(1).toFile());
				}
			}

			// get info from context
			// TODO: HACK HACK. Hard coded build context to get working....
			Check.checkState(buildContext instanceof BuildContextSBSv1 || buildContext instanceof BuildContextSBSv2);
			
			if (buildContext instanceof BuildContextSBSv1){
				systemPaths.addAll(((BuildContextSBSv1) buildContext).getCachedSystemIncludePaths());
			} else if (buildContext instanceof BuildContextSBSv2){
				systemPaths.addAll(((BuildContextSBSv2) buildContext).getCachedSystemIncludePaths());
			}
		}
		setPaths(null, (File[]) systemPaths.toArray(new File[systemPaths.size()]));
	}
}
