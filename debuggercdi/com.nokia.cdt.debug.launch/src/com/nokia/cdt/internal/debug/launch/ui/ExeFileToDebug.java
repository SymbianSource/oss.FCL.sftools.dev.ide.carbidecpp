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
package com.nokia.cdt.internal.debug.launch.ui;

public class ExeFileToDebug {
	private String fExePath = ""; //$NON-NLS-1$	
	private boolean fEnabled = true;
	
	ExeFileToDebug() {
	}
	
	ExeFileToDebug(String exePath, boolean enabled) {
		fExePath = exePath;
		fEnabled = enabled;
	}

	String getExePath() {
		return fExePath;
	}
	
	void setExePath(String path) {
		fExePath = path;
	}


	boolean getEnabled() {
		return fEnabled;
	}

	void setEnabled(boolean enabled) {
		fEnabled = enabled;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ExeFileToDebug)
			return fExePath.equals(((ExeFileToDebug) obj).getExePath());
		else
			return false;
	}

}
