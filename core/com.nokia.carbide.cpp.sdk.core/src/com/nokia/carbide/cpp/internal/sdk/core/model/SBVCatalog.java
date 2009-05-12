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
*/
package com.nokia.carbide.cpp.internal.sdk.core.model;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cpp.sdk.core.ISBVCatalog;
import com.nokia.carbide.cpp.sdk.core.ISBVPlatform;
import com.nokia.cpp.internal.api.utils.core.IMessage;

/**
 * This class defines the hierarchy of VAR files detected for a given SDK.
 *
 */
public class SBVCatalog implements ISBVCatalog {

	private List<IMessage> messages;
	private List<SBVPlatform> platforms;
	
	public SBVCatalog(IPath sdkPath, IPath sdkIncludePath) {
		this.messages = new ArrayList<IMessage>(1);
		this.platforms = new ArrayList<SBVPlatform>(10);
		
		File epoc32ToolsDir = sdkPath.append("epoc32").append("tools").append("variant").toFile(); //$NON-NLS-1$ //$NON-NLS-2$ 
		File[] sbvFiles = epoc32ToolsDir.listFiles(new FilenameFilter() {
			public boolean accept(File file, String str) {
				return str.toLowerCase().endsWith(".var"); //$NON-NLS-1$
			}
		});
		
		// cannot read directory; return empty catalog
		if (sbvFiles == null){
			return;
		}
		
		// gather the individual SBV platforms
		for (File sbvFile : sbvFiles) {
			IPath sbvPath = new Path(sbvFile.getAbsolutePath());
			SBVPlatform platform = SBVPlatform.createPlatform(this, sbvPath, messages, sdkIncludePath);
			if (platform != null) {
				platforms.add(platform);
			}
		}
		
		// TODO: Do we need to bother with a hierarchy?
		// now wire up the platforms
		//establishPlatformHierarchy();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.ISBVCatalog#getMessages()
	 */
	public IMessage[] getMessages() {
		return (IMessage[]) messages.toArray(new IMessage[messages.size()]);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.ISBVCatalog#findPlatform(java.lang.String)
	 */
	public ISBVPlatform findPlatform(String platformName) {
		for (ISBVPlatform platform : platforms) {
			if (platform.getName().equalsIgnoreCase(platformName))
				return platform;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.ISBVCatalog#getPlatforms()
	 */
	public ISBVPlatform[] getPlatforms() {
		return (ISBVPlatform[]) platforms.toArray(new ISBVPlatform[platforms.size()]);
	}
	
}
