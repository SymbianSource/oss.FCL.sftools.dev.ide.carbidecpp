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

import org.eclipse.cdt.core.settings.model.extension.CTargetPlatformData;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;

/**
 * Part of the new CDT 4.0 project model requirements.  All this class
 * really does is provide binary parsers for this build configuration to CDT.
 * 
 */
public class CarbideTargetPlatformData extends CTargetPlatformData {

	private ICarbideBuildConfiguration carbideBuildConfig;
	private static final String TARGET_PLATFORM_DATA_ID = "CarbideTargetPlatformData"; //$NON-NLS-1$
	private static final String DOT = "."; //$NON-NLS-1$

	
	public CarbideTargetPlatformData(ICarbideBuildConfiguration config) {
		carbideBuildConfig = config;
	}
	
	@Override
	public String[] getBinaryParserIds() {
		return ICarbideProjectInfo.REQUIRED_BINARY_PARSER_IDS;
	}

	@Override
	public void setBinaryParserIds(String[] ids) {
	}

	@Override
	public String getId() {
		// plugin id + sdk id + plat id + target id + type id
		return CarbideBuilderPlugin.PLUGIN_ID + DOT +
			carbideBuildConfig.getSDK().getUniqueId() + DOT +
			carbideBuildConfig.getPlatformString() + DOT +
			carbideBuildConfig.getTargetString() + DOT +
			TARGET_PLATFORM_DATA_ID;
	}

	@Override
	public String getName() {
		return carbideBuildConfig.getDisplayString() + " " + TARGET_PLATFORM_DATA_ID; //$NON-NLS-1$
	}

	@Override
	public boolean isValid() {
		return (carbideBuildConfig != null);
	}

}
