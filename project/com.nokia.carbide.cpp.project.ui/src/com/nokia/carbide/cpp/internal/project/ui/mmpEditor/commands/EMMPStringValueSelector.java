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

import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

public enum EMMPStringValueSelector implements IMMPStringValueProvider {
	
	UID2(Messages.getString("EMMPStringValueSelector.uid2")), //$NON-NLS-1$
	UID3(Messages.getString("EMMPStringValueSelector.uid3")); //$NON-NLS-1$
	
	private final String displayText;
	
	EMMPStringValueSelector(String displayText) {
		this.displayText = displayText;
	}

	public String fetchString(IMMPView view) {
		String result = null;
		switch (this) {
		case UID2:
			result = view.getUid2();
			break;
		case UID3:
			result = view.getUid3();
			break;
		}
		return result;
	}
	
	public void storeString(IMMPView view, String value) {
		if (TextUtils.strlen(value) == 0) {
			value = null;
		}
		switch (this) {
		case UID2:
			view.setUid2(value);
			break;
		case UID3:
			view.setUid3(value);
			break;
		}
	}

	public String getDisplayText() {
		return displayText;
	}
}
