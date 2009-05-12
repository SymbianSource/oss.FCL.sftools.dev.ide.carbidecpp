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
import java.text.MessageFormat;
import java.util.*;

import org.eclipse.core.runtime.*;

import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.ISBVViewRunnable;
import com.nokia.carbide.cpp.epoc.engine.model.sbv.ISBVView;
import com.nokia.carbide.cpp.internal.api.sdk.Messages;
import com.nokia.carbide.cpp.sdk.core.*;
import com.nokia.cpp.internal.api.utils.core.*;

/**
 * This specifies a single SBV platform.  
 *
 */
public class SBVPlatform implements ISBVPlatform {

	/** The exact basename of the .var */
	private String name;
	private IPath path;
	private ISBVPlatform extendedPlatform;
	private String extendedPlatName;
	private List<IPath> systemBuildIncludePaths = new ArrayList<IPath>();
	private List<IPath> romBuildIncludePaths = new ArrayList<IPath>();
	private IPath sdkIncludePath;
	private IPath bldVarintHRH;
	private ISBVCatalog catalog;
	private boolean virtual;
	
	/** Create a Symbian Binary Variation platform from parse results of a .var file
	 * @param sdk
	 * @param sbvPath
	 * @param enableAbiV2Mode used to remap ARMV5, ARMV6, or ARMV6_ABIV1 to an appropriate canonical name  
 
	 */
	SBVPlatform(ISBVCatalog catalog, IPath sdkIncludePath, ISBVView view ) {
		this.catalog = catalog;
		this.sdkIncludePath = sdkIncludePath;
		this.extendedPlatName = view.getExtends().toUpperCase();
		this.path = view.getModel().getPath();
		this.virtual = view.getVirtualFlag();
		this.name = view.getVariantName();
		
		String temp = view.getBuildVariantHRH();
		String epocRoot = getEPOCRoot();
		
		bldVarintHRH = new Path(epocRoot + temp);
		
		setBuildIncludePaths(view.getBuildIncludes());
		setROMBuildIncludePaths(view.getROMBuildIncludes());

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SBV platform: " + name; //$NON-NLS-1$
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return obj instanceof ISBVPlatform && ((ISBVPlatform) obj).getName().equalsIgnoreCase(name);
	}
	
	
	/**
	 * Set the customized platform.
	 * @param customized
	 */
	void setCustomizedPlatform(ISBVPlatform customized) {
		Check.checkState(customized != this);
		this.extendedPlatform = customized;
		if (customized != null) {
			this.extendedPlatName = customized.getName();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.ISBVPlatform#getCatalog()
	 */
	public ISBVCatalog getCatalog() {
		return catalog;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.ISBVPlatform#getSBVPath()
	 */
	public IPath getSBVPath() {
		return path;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.ISBVPlatform#getName()
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.ISBVPlatform#getExtendedPlatformName()
	 */
	public String getExtendedVariantName() {
		return extendedPlatName;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.ISBVPlatform#getExtendedVariant()()
	 */
	public ISBVPlatform getExtendedVariant() {
		return catalog.findPlatform(getExtendedVariantName());
	}

	/**
	 * Create one SBV platform, partially initialized in isolation from the
	 * content of one SBV file.
	 * @param catalog the owning catalog
	 * @param sbvPath full path to .sbv file
	 * @param messages array of messages to which to add any messages found while parsing
	 * @return new platform, or null on error
	 */
	public static SBVPlatform createPlatform(
			final ISBVCatalog catalog, 
			final IPath sbvPath, 
			final List<IMessage> messages, 
			final IPath sdkIncludePath) {
		
		ISBVViewRunnable runnable = new ISBVViewRunnable() {

			/* (non-Javadoc)
			 * @see com.nokia.carbide.cpp.epoc.engine.IViewRunnable#failedLoad(org.eclipse.core.runtime.CoreException)
			 */
			public Object failedLoad(CoreException exception) {
				EpocEnginePlugin.log(exception);
				messages.add(new Message(IMessage.ERROR,
						new MessageLocation(sbvPath),
						"SBVCatalog.SBVLoadError", //$NON-NLS-1$
						MessageFormat.format(
								Messages.getString("SBVCatalog.SBVLoadError"), //$NON-NLS-1$
								new Object[] { exception.getLocalizedMessage() })
				));
				return null;
			}
			public Object run(ISBVView view) {
				IMessage[] viewMessages = view.getMessages();
				for (IMessage message : viewMessages) {
					messages.add(message);
				}
				return new SBVPlatform(catalog, sdkIncludePath, view);
			}
		};
		
		return (SBVPlatform) EpocEnginePlugin.runWithSBVView(sbvPath, runnable);
	}

	public boolean isVirtual() {
		return virtual;
	}

	public IPath getBuildVariantHRHFile() {
		
		return bldVarintHRH;
		
	}
	
	protected void setBuildIncludePaths(Map<String, String> incPaths){
		synchronized (this)
		{
			Set<String> set = incPaths.keySet();
			for (String currPath : set) {
				IPath path = new Path(getEPOCRoot() + currPath);
				systemBuildIncludePaths.add(path);
			}
		}
		
	}
	
	protected List<IPath> getBuildIncludePathsFromParents(){
		
		List<IPath> parentBuildIncludes = new ArrayList<IPath>();
		
		ISBVPlatform platform = getExtendedVariant();
		ISBVPlatform prevPlat;
		while (platform != null) {
			parentBuildIncludes.addAll(platform.getBuildIncludePaths());
			prevPlat = platform;
			platform = getExtendedVariant();
			if (prevPlat.getName().equalsIgnoreCase(platform.getName())){
				break;
			}
		}
		
		return parentBuildIncludes;
	}
	
	protected void setROMBuildIncludePaths(Map<String, String> incPaths){
		synchronized (this)
		{
			Set<String> set = incPaths.keySet();
			for (String currPath : set){
				IPath path = new Path(getEPOCRoot() + currPath);
				romBuildIncludePaths.add(path);
			}
		}
	}
	
	protected List<IPath> getROMBuildIncludePathsFromParents(){
		
		List<IPath> parentROMBuildIncludes = new ArrayList<IPath>();
		
		ISBVPlatform platform = getExtendedVariant();
		ISBVPlatform prevPlat;
		while (platform != null) {
			parentROMBuildIncludes.addAll(platform.getROMBuildIncludePaths());
			prevPlat = platform;
			platform = getExtendedVariant();
			if (prevPlat.getName().equalsIgnoreCase(platform.getName())){
				break;
			}
		}
		
		return parentROMBuildIncludes;
	}

	protected String getEPOCRoot(){

		String EPOC32_INCLUDE = "epoc32" + File.separator + "include";
		if (sdkIncludePath.toOSString().contains(EPOC32_INCLUDE)){
			return sdkIncludePath.toOSString().substring(0, sdkIncludePath.toOSString().indexOf(EPOC32_INCLUDE));
		} 
		
		return sdkIncludePath.toOSString();
	}
	
	public List<IPath> getBuildIncludePaths(){
		List<IPath> fullList = new ArrayList<IPath>();
		
		fullList.addAll(systemBuildIncludePaths);
		fullList.addAll(getBuildIncludePathsFromParents());
		
		return fullList;
	}
	
	public List<IPath> getROMBuildIncludePaths(){
		List<IPath> fullList = new ArrayList<IPath>();
		
		fullList.addAll(romBuildIncludePaths);
		fullList.addAll(getROMBuildIncludePathsFromParents());
		
		return fullList;
	}
	
	
}

