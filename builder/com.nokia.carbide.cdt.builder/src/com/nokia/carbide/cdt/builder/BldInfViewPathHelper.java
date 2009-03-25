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
* This class manages conversion of IPaths provided by an IBldInfView 
* back and forth to real-world paths in the filesystem or workspace.
* <p>
* In most cases, EpocEnginePathHelper is sufficient for this purpose.
* The routines here are for use with the IExtension API provided by
* {@link IBldInfView#getExtensions()}.
* <p>
* Instances of this class should be short-lived (e.g. if a project is
* deleted or renamed, it may cease to work).
*
*
*/
package com.nokia.carbide.cdt.builder;

import com.nokia.carbide.cpp.epoc.engine.model.bldinf.*;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.cpp.internal.api.utils.core.FileUtils;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

public class BldInfViewPathHelper {
	protected IPath epocRoot;
	protected IPath epocTemplatesRoot;

	/** 
	 * Construct an instance with the given epocRoot.
	 * This constructor is used when a project doesn't exist yet.
	 * <p> 
	 * In this configuration, if epocRoot==null,
	 * {@link #convertbld.infToFilesystem(Ebld.infPathContext, IPath)} will return
	 * null for EPOCROOT-relative paths.
	 * @path epocRoot path to EPOCROOT
	 * @param buildConfiguration
	 */ 
	public BldInfViewPathHelper(IBldInfData data, IPath epocRoot) {
		this.epocRoot = epocRoot;
		if (epocRoot != null)
			epocTemplatesRoot = epocRoot.append("epoc32/tools/makefile_templates");
	}
	

	/** 
	 * Construct an instance with the given configuration providing
	 * the project and EPOCROOT.
	 * @param buildConfiguration (may not be null)
	 */ 
	public BldInfViewPathHelper(IBldInfData data, ISymbianBuildContext buildContext) {
		this.epocRoot = new Path(buildContext.getSDK().getEPOCROOT());
		if (epocRoot != null)
			epocTemplatesRoot = epocRoot.append("epoc32/tools/makefile_templates");
	}
	
	/**
	 * Convert the given path (from an IExtension) into the full filesystem
	 * path where its *.mk and *.meta files live.
	 * @param path path from {@link IExtension#getTemplatePath()}
	 * @return absolute path, without file extension , or null if no EPOCROOT was passed.
	 */
	public IPath convertExtensionTemplateToFilesystem(IPath path) {
		if (path == null || epocRoot == null)
			return null;

		if (path.isAbsolute())
			return path;
		
		return epocTemplatesRoot.append(path); //$NON-NLS-1$
	}

	/**
	 * Convert the given full-path template makefile base path to the template
	 * makefile-relative path.
	 * @param path full path 
	 * @return relative path, without file extension, or null if not representable
	 */
	public IPath convertExtensionTemplateFromFilesystem(IPath path) {
		if (path == null || epocRoot == null)
			return null;

		if (!path.isAbsolute())
			return null;
		
		IPath relPath = FileUtils.removePrefixFromPath(epocTemplatesRoot, path);
		
		return relPath;
	}

	/**
	 * Determine whether an extension makefile is suitable for the given build configuration.
	 * This information is recorded in the *.meta file next to the *.mk file.
	 * @param extensionMakefilePath resolved full path to extension makefile base (no extension)
	 * @param buildContext the build context being tested, may not be null
	 * @return true if the makefile is appropriate to the build configuration
	 */
	public boolean isExtensionMakefileBuiltForPlatform(IPath extensionMakefilePath, ISymbianBuildContext buildContext) {
		// as per wrappermakefile.pm, the "platform" keyword is not used!
		return true;
		/*
		Map<String, String> info = parseMetaFile(extensionMakefilePath.addFileExtension("meta")); //$NON-NLS-1$
		String platform = info.get("platform"); //$NON-NLS-1$
		if (platform == null)
			return true;
		if (buildContext.getPlatformString().equals(ISymbianBuildContext.EMULATOR_PLATFORM)
				&& platform.equals("win32")) {
			return true;
		}
		// other ambiguous .meta platform -> ISymbianBuildContext#getPlatformString() matches here
		return false; 
		*/
	}
}
