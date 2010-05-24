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
package com.nokia.carbide.cpp.internal.api.project.core;

import java.util.List;
import java.util.Map;

import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectModifier;
import com.nokia.carbide.cdt.builder.project.ISISBuilderInfo;
import com.nokia.carbide.cdt.internal.api.builder.SISBuilderInfo2;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;

public class ProjectCorePluginUtility {

	private static ResourceChangeListener resourceChangeListener;
	private static boolean listening = false;
		
	
	public ProjectCorePluginUtility() {
	}

	public void startup() {
	}

	public void shutdown() {
		stopProjectListener();
	}

	public static void startProjectListener() {
		if (!listening) {
			if (resourceChangeListener == null) {
				resourceChangeListener = new ResourceChangeListener();
			}
			ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceChangeListener);
			listening = true;
		}
	}

	public static void stopProjectListener() {
		if (listening) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceChangeListener);
			listening = false;
		}
	}

	public static void setupBuilderSettings(ICProjectDescription projDes, String projectRelativeBldInfPath, 
			List<ISymbianBuildContext> buildConfigs, List<String> infComponentsList, 
			Map<ISymbianBuildContext, String> pkgMappings) throws CoreException	{
		
		ICarbideProjectModifier cpi = CarbideBuilderPlugin.getBuildManager().createProjectInfo(projDes);
		if (cpi != null) {
			cpi.writeProjectSetting(ICarbideProjectInfo.PROJECT_RELATIVE_INFFILE_PROPS_KEY, projectRelativeBldInfPath);
			
    		String componentString = ""; //$NON-NLS-1$
    		for (String compName : infComponentsList){
    			componentString += compName + ";"; //$NON-NLS-1$
    		}
   			cpi.writeProjectSetting(ICarbideProjectInfo.INF_COMPONENTS_PROPS_KEY, componentString);
   			if (componentString.length() > 0) {
    			cpi.writeProjectSetting(ICarbideProjectInfo.BLD_FROM_INF_PROPS_KEY, "false"); //$NON-NLS-1$
   			} else {
    			cpi.writeProjectSetting(ICarbideProjectInfo.BLD_FROM_INF_PROPS_KEY, "true"); //$NON-NLS-1$
   			}
   			
    		// add the build configurations
    		boolean isDefaultConfig = true; // only the first one is default
    		for (ISymbianBuildContext config : buildConfigs) {
    			ICarbideBuildConfiguration buildConfiguration = cpi.createNewConfiguration(config, isDefaultConfig);
    			if (isDefaultConfig) // make all other configs not the default
    				isDefaultConfig = false;
				if (pkgMappings != null) {
					String pkgFilePath = pkgMappings.get(config);
					if (pkgFilePath != null) {
						SISBuilderInfo2 sisInfo = new SISBuilderInfo2(projDes.getProject());
						sisInfo.setPKGFile(pkgFilePath);
						if (config.getSDK().isEKA2()) {
							// set to self signing
							sisInfo.setSigningType(ISISBuilderInfo.SELF_SIGN);
						}
						buildConfiguration.getSISBuilderInfoList().add(sisInfo);
						buildConfiguration.saveConfiguration(false);
					}
				}
    		}
    		
    		cpi.saveChanges();
		}
	}
}
