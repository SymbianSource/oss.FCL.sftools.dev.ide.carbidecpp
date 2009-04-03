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

import com.nokia.carbide.cpp.epoc.engine.model.IView;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.ICarbideListProvider;

import java.util.List;


public enum EBldInfListSelector implements ICarbideListProvider {
	
	MAKMAKEFILES("MakMakeFiles"),  //$NON-NLS-1$
	TEST_MAKMAKEFILES("TestMakMakeFiles"),  //$NON-NLS-1$
	TEST_EXPORTS("TestExports"),  //$NON-NLS-1$
	EXPORTS("Exports"); //$NON-NLS-1$
	
	private final String displayText;
	
	EBldInfListSelector(String displayText) {
		this.displayText = displayText;
	}
	
	public String getDisplayText() {
		return displayText;
	}
	
	public List fetchList(IView view) {
		List result = null;
		
		IBldInfView bldInfView = (IBldInfView)view;
		switch (this) {
		case MAKMAKEFILES:
			result = bldInfView.getMakMakeReferences();
			break;
		case TEST_MAKMAKEFILES:
			result = bldInfView.getTestMakMakeReferences();
			break;
		case EXPORTS:
			result = bldInfView.getExports();
			break;
		case TEST_EXPORTS:
			result = bldInfView.getTestExports();
			break;
		}
		return result;
	}

}