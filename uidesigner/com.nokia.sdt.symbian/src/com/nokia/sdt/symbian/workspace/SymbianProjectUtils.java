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
package com.nokia.sdt.symbian.workspace;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.osgi.framework.Version;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.sdt.symbian.SymbianPlugin;
import com.nokia.sdt.symbian.dm.DesignerDataModel;
import com.nokia.cpp.internal.api.utils.core.Logging;

public class SymbianProjectUtils {
	
	/**
	 * Check if a project has the UI Designer project.
	 * 
	 * @param project The project to check for the nature.
	 * @return <code>true</code> if the project has the nature.
	 */
	public static boolean isWellConfiguredUIDesignerProject(IProject project) {
		
		boolean hasCarbideNature = false;
		try {
			IProjectDescription description = project.getDescription();
			String[] natures = description.getNatureIds();

			for (int i = 0; i < natures.length; ++i) {
				if (CarbideBuilderPlugin.CARBIDE_PROJECT_NATURE_ID.equals(natures[i])) {
					hasCarbideNature = true;
					break;
				}
			}
			
		} 
		catch (CoreException e) {
            Logging.log(SymbianPlugin.getDefault(), e.getStatus());
		}
		
		if (hasCarbideNature) {
			return hasUIDesignerRootDataModelFile(project);
		}
		
		return false;
	}
	
	public static boolean hasUIDesignerRootDataModelFile(IProject project) {
		return project.findMember(DesignerDataModel.ROOT_DATA_MODEL_FILENAME) != null;
	}
	
	public enum ES60ProjectType {
		S602xOnly, S603xOnly, S60Mixed, NotS60;
	}
	
	public enum EUIQProjectType {
		UIQ30xOnly, UIQ31xOnly, UIQMixed, NotUIQ;
	}

	/**
	 * @param project the IProject
	 * @return ES60ProjectType enum
	 */
	public static ES60ProjectType getS60ProjectType(IProject project) {
		boolean has2x = false;
		boolean has3x = false;
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		if (cpi == null)
			return ES60ProjectType.NotS60;
		
		List<ICarbideBuildConfiguration> buildConfigurations = cpi.getBuildConfigurations();
		for (ICarbideBuildConfiguration buildConfiguration : buildConfigurations) {
			ISymbianSDK sdk = buildConfiguration.getSDK();
			Version version = sdk.getSDKVersion();
			String family = sdk.getFamily();
			if (version == null || 
					!(ISymbianSDK.S60_FAMILY_ID.equals(family) || 
							ISymbianSDK.SERIES60_FAMILY_ID.equals(family)))
				continue;
			int major = version.getMajor();
			if (major == 2)
				has2x = true;
			else if (major >= 3)
				has3x = true;
			
			if (has2x && has3x)
				break;
		}
		
		if (has2x && has3x)
			return ES60ProjectType.S60Mixed;
		else if (has2x)
			return ES60ProjectType.S602xOnly;
		else if (has3x)
			return ES60ProjectType.S603xOnly;
		
		return ES60ProjectType.NotS60;
	}

	/**
	 * @param project the IProject
	 * @return EUIQProjectType enum
	 */
	public static EUIQProjectType getUIQProjectType(IProject project) {
		boolean has30x = false;
		boolean has31x = false;
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		if (cpi == null)
			return EUIQProjectType.NotUIQ;
		
		List<ICarbideBuildConfiguration> buildConfigurations = cpi.getBuildConfigurations();
		for (ICarbideBuildConfiguration buildConfiguration : buildConfigurations) {
			ISymbianSDK sdk = buildConfiguration.getSDK();
			Version version = sdk.getSDKVersion();
			String family = sdk.getFamily();
			if (version == null || 
					!ISymbianSDK.UIQ_FAMILY_ID.equals(family))
				continue;
			int major = version.getMajor();
			int minor = version.getMinor();
			if (major == 3 && minor == 0)
				has30x = true;
			else if (major == 3 && minor >= 0)
				has31x = true;
			
			if (has30x && has31x)
				break;
		}
		
		if (has30x && has31x)
			return EUIQProjectType.UIQMixed;
		else if (has30x)
			return EUIQProjectType.UIQ30xOnly;
		else if (has31x)
			return EUIQProjectType.UIQ31xOnly;
		
		return EUIQProjectType.NotUIQ;
	}
}