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

import java.io.File;

import org.eclipse.cdt.core.settings.model.ICStorageElement;

import com.nokia.carbide.cdt.builder.project.IROMBuilderInfo;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.cpp.internal.api.utils.core.HostOS;


public class ROMBuilderInfo implements IROMBuilderInfo {

	private static final String ROMBUILDCOMMANDLINESTORAGE = "ROMBUILDCOMMANDLINESTORAGE"; //$NON-NLS-1$
	private static final String ROMBUILDWORKINGDIRECTORYSTORAGE = "ROMBUILDWORKINGDIRECTORYSTORAGE"; //$NON-NLS-1$
	
	
	private String romBuildCommandLine;
	private String romBuildWorkingDirectory;
	
	
	public ROMBuilderInfo(ISymbianSDK sdk) {

		this.romBuildCommandLine = ""; //$NON-NLS-1$
		this.romBuildWorkingDirectory = HostOS.IS_WIN32 ? "C:\\" : "/tmp"; //$NON-NLS-1$ //$NON-NLS-2$
		
		// now set epoc32\rom folder in the sdk as the default working dir 
		// this is most common folder that rom images are built from for most symbian kits..
		String dir = sdk.getEPOCROOT() + "epoc32/rom/";  //$NON-NLS-1$
		if (new File(dir).exists())
			romBuildWorkingDirectory = dir;
	}

	public void loadFromStorage(ICStorageElement rootStorage) {
		String value = rootStorage.getAttribute(ROMBUILDCOMMANDLINESTORAGE);
		if (value != null) {
			romBuildCommandLine = value;
		}
		
		value = rootStorage.getAttribute(ROMBUILDWORKINGDIRECTORYSTORAGE);
		if (value != null) {
			romBuildWorkingDirectory = value;
		}
	}
	
	public void saveToStorage(ICStorageElement rootStorage) {
		if (romBuildCommandLine.trim().length() > 0) {
			rootStorage.setAttribute(ROMBUILDCOMMANDLINESTORAGE, romBuildCommandLine);
		}

		if (romBuildWorkingDirectory.trim().length() > 0) {
			rootStorage.setAttribute(ROMBUILDWORKINGDIRECTORYSTORAGE, romBuildWorkingDirectory);
		}
	}

	public String getCommandLine() {
		return romBuildCommandLine;
	}

	public String getWorkingDirectory() {
		return romBuildWorkingDirectory;
	}
	
	public void setCommandLine(String romBuildCommandLine) {
		this.romBuildCommandLine = romBuildCommandLine;
	}

	public void setWorkingDirectory(String romBuildWorkingDirectory) {
		this.romBuildWorkingDirectory = romBuildWorkingDirectory;
	}
}
