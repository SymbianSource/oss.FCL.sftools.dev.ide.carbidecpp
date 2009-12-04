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

import com.nokia.carbide.cpp.internal.api.sdk.Messages;
import com.nokia.carbide.cpp.sdk.core.*;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import java.io.File;
import java.io.FilenameFilter;
import java.text.MessageFormat;
import java.util.*;

/**
 * This class defines the hierarchy of BSF files detected for a given SDK.
 *
 */
public class BSFCatalog implements IBSFCatalog {

	private List<IMessage> messages;
	private List<BSFPlatform> platforms;
	
	public BSFCatalog(IPath sdkPath, IPath sdkIncludePath) {
		this.messages = new ArrayList<IMessage>(1);
		this.platforms = new ArrayList<BSFPlatform>(10);
		
		File epoc32ToolsDir = sdkPath.append("epoc32").append("tools").toFile(); //$NON-NLS-1$ //$NON-NLS-2$ 
		File[] bsfFiles = epoc32ToolsDir.listFiles(new FilenameFilter() {
			public boolean accept(File file, String str) {
				return str.toLowerCase().endsWith(".bsf"); //$NON-NLS-1$
			}
		});
		
		// cannot read directory; return empty catalog
		if (bsfFiles == null){
			return;
		}
		
		// sort by alphabetical order
		Arrays.sort(bsfFiles); 
		
		// gather the individual BSF platforms
		for (File bsfFile : bsfFiles) {
			IPath bsfPath = new Path(bsfFile.getAbsolutePath());
			BSFPlatform platform = BSFPlatform.createPlatform(this, bsfPath, messages, sdkIncludePath);
			if (platform != null) {
				platforms.add(platform);
			}
		}

		// now wire up the platforms
		establishPlatformHierarchy();
	}

	/**
	 * Iterate the platforms and connect them together
	 */
	private void establishPlatformHierarchy() {
		List<IBSFPlatform> remaining = new ArrayList<IBSFPlatform>(platforms);
		while (!remaining.isEmpty()) {
			BSFPlatform current = (BSFPlatform) remaining.remove(0);
			String customizedPlatformName = current.getCustomizedPlatformName();
			if (customizedPlatformName != null) {
				IBSFPlatform customizedPlatform = findPlatform(customizedPlatformName);
				if (customizedPlatform == null) {
					messages.add(new Message(IMessage.ERROR,
							new MessageLocation(current.getBSFPath()),
							"BSFCatalog.MissingCustomizedPlatform", //$NON-NLS-1$
							MessageFormat.format(
									Messages.getString("BSFCatalog.MissingCustomizedPlatform"), //$NON-NLS-1$
									new Object[] { current.getName(), customizedPlatformName })
							));
				} else {
					current.setCustomizedPlatform(customizedPlatform);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.IBSFCatalog#getMessages()
	 */
	public IMessage[] getMessages() {
		return (IMessage[]) messages.toArray(new IMessage[messages.size()]);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.IBSFCatalog#findPlatform(java.lang.String)
	 */
	public IBSFPlatform findPlatform(String platformName) {
		for (IBSFPlatform platform : platforms) {
			if (platform.getName().equalsIgnoreCase(platformName))
				return platform;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.IBSFCatalog#getPlatforms()
	 */
	public IBSFPlatform[] getPlatforms() {
		return (IBSFPlatform[]) platforms.toArray(new IBSFPlatform[platforms.size()]);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.IBSFCatalog#getVariantPlatforms()
	 */
	public IBSFPlatform[] getVariantPlatforms() {
		List<IBSFPlatform> variants = new ArrayList<IBSFPlatform>(platforms.size());
		for (IBSFPlatform platform : platforms) {
			if (platform.isVariant() && !platform.isVirtualVariant()) {
				variants.add(platform);
			}
		}
		return (IBSFPlatform[]) variants.toArray(new IBSFPlatform[variants.size()]);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.IBSFPlatform#getBuiltPlatforms()
	 */
	public IBSFPlatform[] getAdditionalBuiltPlatforms(String platformName) {
		Set<IBSFPlatform> built = new HashSet<IBSFPlatform>();
		gatherBuiltPlatforms(built, platformName);
		return (IBSFPlatform[]) built.toArray(new IBSFPlatform[built.size()]);
	}

	/**
	 * Bottom-up scan to find all the platforms that customize a given one.
	 * @param built
	 * @param customizes2
	 */
	private void gatherBuiltPlatforms(Set<IBSFPlatform> built, String platformName) {
		for (IBSFPlatform platform : platforms) {
			String customizes = platform.getCustomizedPlatformName();
			if (customizes != null && customizes.equalsIgnoreCase(platformName)
					&& platform.getEffectiveCompileWithParent()) {
				if (!platform.isVirtualVariant()) {
					built.add(platform);
				}
				gatherBuiltPlatforms(built, platform.getName());
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.IBSFCatalog#getReleasePlatform(java.lang.String)
	 */
	public String getReleasePlatform(String platformName) {
		while (true) {
			IBSFPlatform platform = findPlatform(platformName);
			if (platform == null || !(platform.isVariant() || platform.isVirtualVariant()))
				break;
			platformName = platform.getCustomizedPlatformName();
		}
		return platformName;
	}

	public IBSFPlatform[] getVirtualVariantPlatforms() {
		List<IBSFPlatform> variants = new ArrayList<IBSFPlatform>(platforms.size());
		for (IBSFPlatform platform : platforms) {
			if (platform.isVirtualVariant() && !platform.isVariant()) {
				variants.add(platform);
			}
		}
		return (IBSFPlatform[]) variants.toArray(new IBSFPlatform[variants.size()]);
	}
	
	
	
}
