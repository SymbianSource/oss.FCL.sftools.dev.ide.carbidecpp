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
package com.nokia.carbide.cpp.internal.project.ui.mmpEditor.commands;

import java.util.List;

import com.nokia.carbide.cpp.epoc.engine.model.IView;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.ICarbideListProvider;


public enum EMMPListSelector implements ICarbideListProvider {
	
	SOURCES(Messages.getString("MMPListSelector.sourcesText")),  //$NON-NLS-1$
	MAIN_LIBRARIES(Messages.getString("MMPListSelector.librariesText")), //$NON-NLS-1$
	STATIC_LIBRARIES(Messages.getString("MMPListSelector.staticLibsText")), //$NON-NLS-1$
	DEBUG_LIBRARIES(Messages.getString("MMPListSelector.debugLibsText")), //$NON-NLS-1$
	WIN32_LIBRARIES(Messages.getString("MMPListSelector.win32LibsText")), //$NON-NLS-1$
	ASSP_LIBRARIES(Messages.getString("MMPListSelector.asspLibsText")), //$NON-NLS-1$
	CAPABILITIES(Messages.getString("MMPListSelector.capabilitiesText")), //$NON-NLS-1$
	HEAP_SIZE(Messages.getString("MMPListSelector.heapSizeText")), //$NON-NLS-1$
	USER_INCLUDES(Messages.getString("MMPListSelector.userIncludes")), //$NON-NLS-1$
	SYS_INCLUDES(Messages.getString("MMPListSelector.sysIncludes")), //$NON-NLS-1$
	MACROS(Messages.getString("MMPListSelector.macros")), //$NON-NLS-1$
	COMPILER_OPTIONS(Messages.getString("MMPListSelector.compilerOptions")), //$NON-NLS-1$
	START_RESOURCE(Messages.getString("MMPListSelector.resources")), //$NON-NLS-1$
	START_BITMAP(Messages.getString("MMPListSelector.bitmaps")), //$NON-NLS-1$
	LANGUAGES(Messages.getString("MMPListSelector.languages")), //$NON-NLS-1$
	USER_RESOURCES(Messages.getString("MMPListSelector.userResourceList")), //$NON-NLS-1$
	SYSTEM_RESOURCES(Messages.getString("MMPListSelector.systemResourceList")), //$NON-NLS-1$
	AIF(Messages.getString("MMPListSelector.aifList")); //$NON-NLS-1$
	
	private final String displayText;
	
	EMMPListSelector(String displayText) {
		this.displayText = displayText;
	}
	
	public String getDisplayText() {
		return displayText;
	}
	
	@SuppressWarnings("unchecked")
	public List fetchList(IView view) {
		List result = null;
		
		IMMPView mmpView = (IMMPView)view;
		
		switch (this) {
		case SOURCES:
			result = mmpView.getSources();
			break;
		case MAIN_LIBRARIES:
			result = mmpView.getLibraries();
			break;
		case STATIC_LIBRARIES:
			result = mmpView.getStaticLibraries();
			break;
		case DEBUG_LIBRARIES:
			result = mmpView.getDebugLibraries();
			break;
		case WIN32_LIBRARIES:
			result = mmpView.getWin32Libraries();
			break;
		case ASSP_LIBRARIES:
			result = mmpView.getASSPLibraries();
			break;
		case CAPABILITIES:
			result = mmpView.getListArgumentSettings().get(EMMPStatement.CAPABILITY);
			break;
		case HEAP_SIZE:
			result = mmpView.getListArgumentSettings().get(EMMPStatement.EPOCHEAPSIZE);
			break;
		case USER_INCLUDES:
			result = mmpView.getUserIncludes();
			break;
		case SYS_INCLUDES:
			result = mmpView.getSystemIncludes();
			break;
		case MACROS:
			result = mmpView.getListArgumentSettings().get(EMMPStatement.MACRO);
			break;
		case COMPILER_OPTIONS:
			result = mmpView.getListArgumentSettings().get(EMMPStatement.OPTION);
			break;
		case START_RESOURCE:
			result = mmpView.getResourceBlocks();
			break;
		case START_BITMAP:
			result = mmpView.getBitmaps();
			break;
		case LANGUAGES:
			result = mmpView.getLanguages();
			break;
		case USER_RESOURCES:
			result = mmpView.getUserResources();
			break;
		case SYSTEM_RESOURCES:
			result = mmpView.getSystemResources();
			break;
		case AIF:
			result = mmpView.getAifs();
			break;
		}
		return result;
	}

}