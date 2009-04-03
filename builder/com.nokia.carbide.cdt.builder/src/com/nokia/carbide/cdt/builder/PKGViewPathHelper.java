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
* Helper to resolve paths used in pkg files.
*
*/
package com.nokia.carbide.cdt.builder;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ISISBuilderInfo;
import com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg.IPKGView;
import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * Helper to resolve paths used in pkg files.
 * <p>
 * When building a pkg file, you can pass a search directory to makesis (-d) and it will
 * look for any files without paths in that search directory.  Carbide also lets you specify
 * macros in the PKG file that need to be expanded, e.g. $(EPOCROOT)
 * 
 * @since 1.3
 *
 */
public class PKGViewPathHelper {

	// Carbide PKG macro strings
    private static final String PKG_SYMBOL_EPOCROOT = "$(EPOCROOT)"; //$NON-NLS-1$
    private static final String PKG_SYMBOL_PLATFORM = "$(PLATFORM)"; //$NON-NLS-1$
    private static final String PKG_SYMBOL_TARGET = "$(TARGET)"; //$NON-NLS-1$

    private IPath pkgFilePath;
	private ICarbideBuildConfiguration buildConfig;
	private ISISBuilderInfo sisInfo;
	private IPath epocRoot;
	private String platform;
	private String target;
	
	// the main directory to use as the basis for relative path lookups.
	// this is typically where the pkg file is but may be changed by the
	// client in case it needs to move the pkg file from its original location.
    private IPath mainDirectory;
	

	/** 
	 * Construct an instance using the given PKG view, to provide the
	 * content search location.
	 * <p> 
	 * @param view the non-null PKG view (does not take ownership)
	 * @param buildConfig the build configuration context.  this will be used for expanding PKG macros
	 * and obtaining the content search location, unless set from 
	 */ 
	public PKGViewPathHelper(IPKGView view, ICarbideBuildConfiguration buildConfig) {
		Check.checkArg(view);
		Check.checkArg(buildConfig);
		this.pkgFilePath = view.getModel().getPath();
		this.buildConfig = buildConfig;
		this.epocRoot = new Path(buildConfig.getSDK().getEPOCROOT());
		this.platform = buildConfig.getPlatformString();
		this.target = buildConfig.getTargetString();
		this.mainDirectory = pkgFilePath.removeLastSegments(1);
	}
	
	/**
	 * Constructor for test purposes or for out-of-project experiences.
	 */
	public PKGViewPathHelper(IPath pkgFilePath, IPath epocRoot, String platform, String target) {
		Check.checkArg(pkgFilePath);
		Check.checkArg(epocRoot);
		Check.checkArg(platform);
		Check.checkArg(target);
		this.pkgFilePath = pkgFilePath;
		this.epocRoot = epocRoot;
		this.platform = platform;
		this.target = target;
		this.mainDirectory = pkgFilePath.removeLastSegments(1);
	}

	public void setSISBuilderInfo(ISISBuilderInfo sisInfo) {
		Check.checkArg(sisInfo);
		this.sisInfo = sisInfo;
	}
	
	/**
	 * Returns the main directory used as the basis for relative path lookups
	 * @since 2.0
	 */
	public IPath getMainDirectory() {
		return mainDirectory;
	}

	/**
	 * Sets the main directory to use as the basis for relative path lookups
	 * @since 2.0
	 */
	public void setMainDirectory(IPath mainDirectory) {
		this.mainDirectory = mainDirectory;
	}
	
	/**
	 * Given a source install-file path from an IPKGInstallFile, convert it to an absolute 
	 * file system path.  Any Carbide macros in the path will be expanded 
	 * (see {@value #PKG_SYMBOL_EPOCROOT}, {@value #PKG_SYMBOL_PLATFORM},
	 * {@value #PKG_SYMBOL_TARGET}.
	 * <p>
	 * Non-absolute paths will first try to be resolved relative to the pkg file directory.  If the file
	 * does not exist, we will try to resolve it relative to the search location (when available).  If
	 * still not found, the path will be returned relative to the pkg file location.
	 * 
	 * The content search location will be obtained from the ISISBuilderInfo.  The ISISBuilderInfo set from
	 * {@link #setSISBuilderInfo(ISISBuilderInfo)} will be used when set, otherwise the first ISISBuilderInfo
	 * in the build configuration that matches the pkg file will be used.
	 * <p>
	 * 
	 * Note that the makesis source code was examined to determine the search order, and it it looks relative
	 * to the pkg file directory before checking the search directory.  It will of course bail though if either
	 * does not exist.  Since this helper is not designed to check for the existence of the file, but rather to
	 * return the path where the file should be, there may be cases where we return the wrong file path.
	 * 
	 * @param path relative or absolute path from a IPKGInstallFile
	 * @return absolute path for an existing or candidate file
	 */
	public IPath getAbsolutePathFromViewPath(IPath path) {
		if (path == null)
			return null;
		
		path = replaceMacrosInPath(path);
		
		if (path.isAbsolute())
			return path;
		
		// check in the main directory first
		IPath pkgDirRelPath = mainDirectory.append(path);
		if (pkgDirRelPath.toFile().exists()) {
			return pkgDirRelPath;
		}
		
		// the file does not exist.  see if we have a search location to check
		IPath searchDirectory = getContentSearchLocation();
		if (searchDirectory != null) {
			// check the search location
			IPath searchLocationRelPatth = searchDirectory.append(path);
			if (searchLocationRelPatth.toFile().exists()) {
				return searchLocationRelPatth;
			}
		}
		
		// the file doesn't exist in either location.  default is to return the pkg relative path
		return pkgDirRelPath;
	}
	
	private IPath getContentSearchLocation() {
		// get the content search location
		IPath searchDirectory = null;
		
		if (sisInfo != null) {
			String dir = sisInfo.getContentSearchLocation();
			if (dir.trim().length() > 0) {
				searchDirectory = new Path(dir);
			}
		} else if (buildConfig != null) {
			// find the first sis info from the build config that matches the pkg
			for (ISISBuilderInfo info : buildConfig.getSISBuilderInfoList()) {
				if (info.getPKGFullPath().equals(pkgFilePath)) {
					String dir = info.getContentSearchLocation();
					if (dir.trim().length() > 0) {
						searchDirectory = new Path(dir);
						break;
					}
				}
			}
		}
		
		return searchDirectory;
	}
	
	private IPath replaceMacrosInPath(IPath path) {
		String pathStr = path.toOSString();
		if (pathStr.contains(PKG_SYMBOL_EPOCROOT)) {
			// remove duplicate slashes that might emerge from incorrectly using 
			// the syntax "$(EPOCROOT)\..." in the pkg file;
			// normally this is innocuous, unless EPOCROOT is a substed drive, in which
			// case, e.g. K:\\epoc32 ... looks like a UNC path to IPath (for some reason).
			//
			// note: this is not addressed as a single \\ -> \ conversion
			// since, who knows, UNC paths proper may be legal in some cases.
			// This replacement ensures we only find this particular use of 
			// repeated slashes.
			String epocRootStr = epocRoot.addTrailingSeparator().toOSString();
			pathStr = pathStr.replace(PKG_SYMBOL_EPOCROOT + "\\", epocRootStr);
			pathStr = pathStr.replace(PKG_SYMBOL_EPOCROOT + "/", epocRootStr);
			pathStr = pathStr.replace(PKG_SYMBOL_EPOCROOT, epocRootStr);
		}
		
		if (pathStr.contains(PKG_SYMBOL_PLATFORM)) {
			pathStr = pathStr.replace(PKG_SYMBOL_PLATFORM, platform);
		}
		
		if (pathStr.contains(PKG_SYMBOL_TARGET)) {
			pathStr = pathStr.replace(PKG_SYMBOL_TARGET, target);
		}
		
		return new Path(pathStr);
	}
	
	/**
	 * Converts an absolute path to a pkg view path.  If the content search location is a prefix
	 * of the absolute path, a path relative to the search location will be returned.  Otherwise
	 * the absolute path will be returned.
	 * @param path absolute path
	 * @return view path
	 */
	public IPath getViewPathFromAbsolutePath(IPath path) {
		if (path == null)
			return null;
		
		if (path.isAbsolute()) {
			IPath searchLocation = getContentSearchLocation();
			if (searchLocation != null) {
				if (searchLocation.isPrefixOf(path)) {
					return path.removeFirstSegments(searchLocation.segmentCount()).makeRelative().setDevice(null);
				}
			}
		}

		// already project-relative
		return path;
	}
}
