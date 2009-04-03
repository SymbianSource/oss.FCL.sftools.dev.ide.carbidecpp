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

public class FileToTransfer {

	private String fHostPath = ""; //$NON-NLS-1$
	private String fTargetPath = ""; //$NON-NLS-1$
	private boolean fEnabled = true;
	
	FileToTransfer() {
	}
	
	FileToTransfer(String hostPath, String targetPath, boolean enabled) {
		fHostPath = hostPath;
		fTargetPath = targetPath;
		fEnabled = enabled;
	}

	String getHostPath() {
		return fHostPath;
	}
	
	void setHostPath(String path) {
		fHostPath = path;
	}

	String getTargetPath() {
		return fTargetPath;
	}
	
	void setTargetPath(String path) {
		fTargetPath = path;
	}

	boolean getEnabled() {
		return fEnabled;
	}

	void setEnabled(boolean enabled) {
		fEnabled = enabled;
	}
}
