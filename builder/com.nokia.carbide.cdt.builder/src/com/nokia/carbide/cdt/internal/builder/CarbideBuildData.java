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
package com.nokia.carbide.cdt.internal.builder;

import org.eclipse.cdt.core.envvar.IEnvironmentContributor;
import org.eclipse.cdt.core.settings.model.COutputEntry;
import org.eclipse.cdt.core.settings.model.ICOutputEntry;
import org.eclipse.cdt.core.settings.model.extension.CBuildData;
import org.eclipse.core.runtime.IPath;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;

/**
 * Part of the new CDT 4.0 project model requirements.  All this class
 * really does is provide error parsers for this build configuration to CDT.
 * 
 */
public class CarbideBuildData extends CBuildData {

	private ICarbideBuildConfiguration carbideBuildConfig;
	private static final String BUILD_DATA_ID = "CarbideBuildData"; //$NON-NLS-1$
	private static final String DOT = "."; //$NON-NLS-1$

	
	public CarbideBuildData(ICarbideBuildConfiguration carbideBuildConfig) {
		this.carbideBuildConfig = carbideBuildConfig;
	}

	@Override
	public IEnvironmentContributor getBuildEnvironmentContributor() {
		return null;
	}

	@Override
	public IPath getBuilderCWD() {
		return carbideBuildConfig.getCarbideProject().getAbsoluteBldInfPath().removeLastSegments(1);
	}

	@Override
	public String[] getErrorParserIDs() {
		return carbideBuildConfig.getErrorParserList();
	}

	@Override
	public ICOutputEntry[] getOutputDirectories() {
		String thePlatform = carbideBuildConfig.getPlatformString();
		if (CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(carbideBuildConfig.getCarbideProject().getProject())){
			ISBSv2BuildConfigInfo sbsv2Info = ((CarbideBuildConfiguration)carbideBuildConfig).getSBSv2BuildConfigInfo();
			if ( sbsv2Info != null && sbsv2Info.getVariantOutputDirModifier() != null ){
				thePlatform = thePlatform + sbsv2Info.getVariantOutputDirModifier();
			}
		} 
		IPath outputPath = carbideBuildConfig.getSDK().getReleaseRoot().append(thePlatform).append(carbideBuildConfig.getTargetString());
		return new ICOutputEntry[]{new COutputEntry(outputPath, null, 0)};
	}

	@Override
	public void setBuilderCWD(IPath path) {
	}

	@Override
	public void setErrorParserIDs(String[] ids) {
	}

	@Override
	public void setOutputDirectories(ICOutputEntry[] entries) {
	}

	@Override
	public String getId() {
		// plugin id + sdk id + plat id + target id + type id
		return CarbideBuilderPlugin.PLUGIN_ID + DOT +
			carbideBuildConfig.getSDK().getUniqueId() + DOT +
			carbideBuildConfig.getPlatformString() + DOT +
			carbideBuildConfig.getTargetString() + DOT +
			BUILD_DATA_ID;
	}

	@Override
	public String getName() {
		return carbideBuildConfig.getDisplayString() + " " + BUILD_DATA_ID; //$NON-NLS-1$
	}

	@Override
	public boolean isValid() {
		return (carbideBuildConfig != null);
	}

}
