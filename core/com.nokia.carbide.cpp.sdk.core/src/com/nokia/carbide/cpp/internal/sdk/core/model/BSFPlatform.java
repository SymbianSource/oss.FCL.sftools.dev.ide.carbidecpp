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

import com.nokia.carbide.cpp.epoc.engine.*;
import com.nokia.carbide.cpp.epoc.engine.model.ETristateFlag;
import com.nokia.carbide.cpp.epoc.engine.model.bsf.IBSFView;
import com.nokia.carbide.cpp.internal.api.sdk.Messages;
import com.nokia.carbide.cpp.sdk.core.*;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.*;

import java.text.MessageFormat;
import java.util.*;

/**
 * This specifies a single BSF platform.  
 *
 */
public class BSFPlatform implements IBSFPlatform {

	/** The exact basename of the .bsf */
	private String name;
	private boolean variant;
	private boolean virtualVariant;
	private IPath path;
	private IBSFPlatform customizedPlatform;
	private String customizes;
	private IPath[] systemIncludePaths;
	private IPath sdkIncludePath;
	private IPath systemIncludePath;
	private Map<String, String> customizationOptions;
	private ETristateFlag compileWithParent;
	private IBSFCatalog catalog;

	/**
	 * @param sdk
	 * @param bsfPath
	 * @param enableAbiV2Mode used to remap ARMV5, ARMV6, or ARMV6_ABIV1 to an appropriate canonical name  
 
	 */
	BSFPlatform(IBSFCatalog catalog, IPath sdkIncludePath, /*boolean enableAbiV2Mode,*/ IBSFView view) {
		this.catalog = catalog;
		this.sdkIncludePath = sdkIncludePath;
		this.name = view.getName();
		this.customizes = view.getCustomizes().toUpperCase();
		
		/*
		// Fudge names and customizes (parents) for ABIV2 switch.
		// We don't expect to see this in newer kits.
		//
		if (!enableAbiV2Mode) {
			if (name.equalsIgnoreCase("ARMV6_ABIV1")) {
				name = "ARMV6";
			} else if (name.equalsIgnoreCase("ARMV6")) {
				name = "ARMV6_ABIV2";
				customizes = "ABIV2";
			}
		} else {
			if (name.equalsIgnoreCase("ARMV6")) {
				customizes = "ABIV2";
			}
		}*/
		
		this.variant = view.isVariant();
		this.virtualVariant = view.isVirtualVariant();
		this.path = view.getModel().getPath();
		this.compileWithParent = view.getCompileWithParent();
		this.customizationOptions = new HashMap<String, String>(view.getCustomizationOptions());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BSF platform: " + name; //$NON-NLS-1$
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return obj instanceof IBSFPlatform && ((IBSFPlatform) obj).getName().equalsIgnoreCase(name);
	}
	
	
	/**
	 * Set the customized platform.
	 * @param customized
	 */
	void setCustomizedPlatform(IBSFPlatform customized) {
		Check.checkState(customized != this);
		this.customizedPlatform = customized;
		if (customized != null) {
			this.customizes = customized.getName();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.IBSFPlatform#getCatalog()
	 */
	public IBSFCatalog getCatalog() {
		return catalog;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.IBSFPlatform#getBSFPath()
	 */
	public IPath getBSFPath() {
		return path;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.IBSFPlatform#getName()
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.IBSFPlatform#getCustomizedPlatformName()
	 */
	public String getCustomizedPlatformName() {
		return customizes;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.IBSFPlatform#getCustomizedPlatform()
	 */
	public IBSFPlatform getCustomizedPlatform() {
		return customizedPlatform;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.IBSFPlatform#getSystemIncludePath()
	 */
	public IPath getSystemIncludePath() {
		if (systemIncludePath == null) {
			IPath customizedPlatformPath = null;
			if (customizedPlatform != null) {
				customizedPlatformPath = customizedPlatform.getSystemIncludePath();
			}
			
			// VIRTUALVARIANT does not have its own path
			if (virtualVariant) {
				systemIncludePath = customizedPlatformPath;
			} else if (variant) {
				// else, construct the system include path from the customized
				// platform by appending our platform name
				if (customizedPlatformPath == null) {
					systemIncludePath = sdkIncludePath.append(name); //$NON-NLS-1$
				} else {
					systemIncludePath = customizedPlatformPath.append(name);
				}
			}
		}
		
		return systemIncludePath;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.IBSFPlatform#getSystemIncludePaths()
	 */
	public IPath[] getSystemIncludePaths() {
		if (systemIncludePaths == null) {
			synchronized (this) {
				// get unique set of paths in the proper order (most specific to least)
				Set<IPath> paths = new LinkedHashSet<IPath>();
				IBSFPlatform platform = this;
				while (platform != null) {
					IPath path = platform.getSystemIncludePath();
					if (path != null)
						paths.add(path);
					platform = platform.getCustomizedPlatform();
				}
				systemIncludePaths = (IPath[]) paths.toArray(new IPath[paths.size()]);
			}
		}
		return systemIncludePaths;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.IBSFPlatform#isVariant()
	 */
	public boolean isVariant() {
		return variant;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.IBSFPlatform#isVirtualVariant()
	 */
	public boolean isVirtualVariant() {
		return virtualVariant;
	}

	/**
	 * Create one BSF platform, partially initialized in isolation from the
	 * content of one BSF file.
	 * @param catalog the owning catalog
	 * @param bsfPath full path to .bsf file
	 * @param messages array of messages to which to add any messages found while parsing
	 * @return new platform, or null on error
	 */
	public static BSFPlatform createPlatform(
			final IBSFCatalog catalog, 
			final IPath bsfPath, 
			final List<IMessage> messages, 
			final IPath sdkIncludePath) {
		IBSFViewRunnable runnable = new IBSFViewRunnable() {

			/* (non-Javadoc)
			 * @see com.nokia.carbide.cpp.epoc.engine.IViewRunnable#failedLoad(org.eclipse.core.runtime.CoreException)
			 */
			public Object failedLoad(CoreException exception) {
				if (exception != null)
					EpocEnginePlugin.log(exception);
				messages.add(new Message(IMessage.ERROR,
						new MessageLocation(bsfPath),
						"BSFCatalog.BSFLoadError", //$NON-NLS-1$
						MessageFormat.format(
								Messages.getString("BSFCatalog.BSFLoadError"), //$NON-NLS-1$
								new Object[] { exception != null ? exception.getLocalizedMessage() : "file not found" }) //$NON-NLS-1$
				));
				return null;
			}
			public Object run(IBSFView view) {
				IMessage[] viewMessages = view.getMessages();
				for (IMessage message : viewMessages) {
					messages.add(message);
				}
				return new BSFPlatform(catalog, sdkIncludePath, /*enableAbiV2Mode,*/ view);
			}
		};
		
		return (BSFPlatform) EpocEnginePlugin.runWithBSFView(bsfPath, runnable);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.IBSFPlatform#getCustomizationOptions()
	 */
	public Map<String, String> getCustomizationOptions() {
		return customizationOptions;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.IBSFPlatform#getCompileWithParent()
	 */
	public ETristateFlag getCompileWithParent() {
		return compileWithParent;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.IBSFPlatform#getEffectiveCompileWithParent()
	 */
	public boolean getEffectiveCompileWithParent() {
		 return compileWithParent == ETristateFlag.ENABLED
			|| (compileWithParent == ETristateFlag.UNSPECIFIED 
				&& customizedPlatform != null
				&& customizedPlatform.getEffectiveCompileWithParent());
	}

	
}

