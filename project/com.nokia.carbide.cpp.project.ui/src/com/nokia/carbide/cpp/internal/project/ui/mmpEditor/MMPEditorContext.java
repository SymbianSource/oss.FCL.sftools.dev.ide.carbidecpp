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
package com.nokia.carbide.cpp.internal.project.ui.mmpEditor;

import org.eclipse.core.resources.IProject;

import com.nokia.carbide.cdt.builder.MMPViewPathHelper;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.CarbideFormEditorContext;

/** 
 * Packages objects and utilities common to the MMP editor
 */
public class MMPEditorContext extends CarbideFormEditorContext {
	
	public static final String OVERVIEW_PAGE_ID = "overview"; //$NON-NLS-1$
	public static final String SOURCES_PAGE_ID = "sources"; //$NON-NLS-1$
	public static final String LIBRARIES_PAGE_ID = "libraries"; //$NON-NLS-1$
	public static final String RESOURCES_PAGE_ID = "resources"; //$NON-NLS-1$
	public static final String OPTIONS_PAGE_ID = "options"; //$NON-NLS-1$
	static final long maxUID = 0xFFFFFFFFL;
	
	public IProject project;
	public IMMPOwnedModel mmpModel;
	public IMMPView mmpView;
	public MMPViewPathHelper pathHelper;
	public ICarbideBuildConfiguration activeBuildConfig;
	
	/**
	 * Return the Secure ID based on the current mmp view settings
	 */
	String getSecureID() {
		String result = mmpView.getSingleArgumentSettings().get(EMMPStatement.SECUREID);
		if (result == null) {
			result = mmpView.getUid3();
			if (result == null) {
				result = "0"; // KNullUID //$NON-NLS-1$
			}
		}
		return result;
	}
}
