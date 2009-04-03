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
package com.nokia.carbide.cpp.internal.project.ui.editors.inf;

import org.eclipse.core.resources.IProject;

import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.CarbideFormEditorContext;

/** 
 * Packages objects and utilities common to the bld.inf editor
 */
public class BldInfEditorContext extends CarbideFormEditorContext {
	
	public static final String OVERVIEW_PAGE_ID = "overview"; //$NON-NLS-1$
	public static final String EXPORTS_PAGE_ID = "exports"; //$NON-NLS-1$
	
	public IProject project;
	public IBldInfOwnedModel bldInfModel;
	public IBldInfView bldInfView;
	public ICarbideBuildConfiguration activeBuildConfig;
	
	public void dispose() {
		super.dispose();
		if (bldInfView != null) {
			bldInfView.dispose();
		}
		
		if (bldInfModel != null) {
			bldInfModel.dispose();
		}
	}
}
