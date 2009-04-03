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

import org.eclipse.cdt.core.settings.model.extension.CFolderData;
import org.eclipse.cdt.core.settings.model.extension.CLanguageData;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;

/**
 * Part of the new CDT 4.0 project model requirements.  All this class
 * really does is provide a CLanguageData instance to CDT.
 *
 */
public class CarbideRootFolderData extends CFolderData {

	private ICarbideBuildConfiguration carbideBuildConfig;
	private static final String ROOT_FOLDER_DATA_ID = "RootFolderData"; //$NON-NLS-1$
	private static final String DOT = "."; //$NON-NLS-1$
	private CLanguageData[] languageDatas;
	
	
	public CarbideRootFolderData(ICarbideBuildConfiguration config) {
		carbideBuildConfig = config;
		languageDatas = new CLanguageData[1];
		languageDatas[0] = new CarbideLanguageData(carbideBuildConfig);
	}
	
	@Override
	public CLanguageData createLanguageDataForContentTypes(String languageId, String[] typesIds) {
		return null;
	}

	@Override
	public CLanguageData createLanguageDataForExtensions(String languageId, String[] extensions) {
		return null;
	}

	@Override
	public CLanguageData[] getLanguageDatas() {
		return languageDatas;
	}

	@Override
	public IPath getPath() {
		return Path.ROOT;
	}

	@Override
	public boolean hasCustomSettings() {
		return false;
	}

	@Override
	public void setPath(IPath path) {
	}

	@Override
	public String getId() {
		// plugin id + sdk id + plat id + target id + type id
		return CarbideBuilderPlugin.PLUGIN_ID + DOT +
			carbideBuildConfig.getSDK().getUniqueId() + DOT +
			carbideBuildConfig.getPlatformString() + DOT +
			carbideBuildConfig.getTargetString() + DOT +
			ROOT_FOLDER_DATA_ID;
	}

	@Override
	public String getName() {
		return carbideBuildConfig.getDisplayString() + " " + ROOT_FOLDER_DATA_ID; //$NON-NLS-1$
	}

	@Override
	public boolean isValid() {
		return (carbideBuildConfig != null);
	}

}
