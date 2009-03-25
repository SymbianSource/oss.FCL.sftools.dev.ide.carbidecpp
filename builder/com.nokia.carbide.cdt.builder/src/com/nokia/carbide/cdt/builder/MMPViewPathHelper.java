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
* This class manages conversion of IPaths provided by an IMMPView 
* back and forth to real-world paths in the filesystem or workspace.
* <p>
* This also handles correcting the capitalization in a path.  Eclipse's
* IPaths are not case-insensitive, as they should be, so we need to
* match up MMP file references to real references and provide IPaths
* that will find real content in the workspace.
* <p>
* If an IPath refers to a target directory (i.e. Z:-relative), this API is 
* meaningless and no conversions are likely to be made.
* <p>
* Instances of this class should be short-lived (e.g. if a project is
* deleted or renamed, it may cease to work).
*
*
*/
package com.nokia.carbide.cdt.builder;

import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPData;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;
import com.nokia.cpp.internal.api.utils.core.FileUtils;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

public class MMPViewPathHelper {
	private EpocEnginePathHelper epocHelper;
	protected IPath epocRoot;

	/** 
	 * Construct an instance with the project root and epocRoot.
	 * This constructor is used when a project doesn't exist yet.
	 * projectRoot may not be null, but epocRoot may be. 
	 * <p> 
	 * In this configuration, if epocRoot==null,
	 * {@link #convertMMPToFilesystem(EMMPPathContext, IPath)} will return
	 * null for EPOCROOT-relative paths.
	 * @path filesystem path to root of project 
	 * @param buildConfiguration
	 */ 
	public MMPViewPathHelper(IPath projectRoot, String epocRoot) {
		epocHelper = new EpocEnginePathHelper(projectRoot);
		this.epocRoot = epocRoot != null ? new Path(epocRoot) : null;
	}
	

	/** 
	 * Construct an instance with the given configuration providing
	 * the project and EPOCROOT.
	 * @param buildConfiguration
	 */ 
	public MMPViewPathHelper(ICarbideBuildConfiguration buildConfiguration) {
		epocHelper = new EpocEnginePathHelper(buildConfiguration);
		this.epocRoot = new Path(buildConfiguration.getSDK().getEPOCROOT());
	}
	
	/** 
	 * Construct an instance with the given project and EPOCROOT.
	 * <p>
	 * project may not be null, but epocRoot may be.
	 * <p> 
	 * In this configuration, if epocRoot==null,
	 * {@link #convertMMPToFilesystem(EMMPPathContext, IPath)} will return
	 * null for EPOCROOT-relative paths.
	 * @param buildConfiguration
	 */ 
	public MMPViewPathHelper(IProject project, String epocRoot) {
		this.epocRoot = epocRoot != null ? new Path(epocRoot) : null;
		epocHelper = new EpocEnginePathHelper(project);
	}
	
	/** 
	 * Construct an instance using the given MMP data, to provide the
	 * project root, and an EPOCROOT.
	 * <p>
	 * The mmpView may not be null, but epocRoot may be.
	 * <p> 
	 * In this configuration, if epocRoot==null,
	 * {@link #convertMMPToFilesystem(EMMPPathContext, IPath)} will return
	 * null for EPOCROOT-relative paths.
	 * @param buildConfiguration
	 */ 
	public MMPViewPathHelper(IMMPData data, IPath epocRoot) {
		this.epocRoot = epocRoot;
		epocHelper = new EpocEnginePathHelper(data.getProjectPath());
	}

	/** 
	 * Construct an instance using the given MMP data, to provide the
	 * project root, and an EPOCROOT.
	 * <p>
	 * The mmpView may not be null, but epocRoot may be.
	 * <p> 
	 * In this configuration, if epocRoot==null,
	 * {@link #convertMMPToFilesystem(EMMPPathContext, IPath)} will return
	 * null for EPOCROOT-relative paths.
	 * @param buildConfiguration
	 */ 
	public MMPViewPathHelper(IMMPView data, String epocRoot) {
		this.epocRoot = epocRoot != null ? new Path(epocRoot) : null;
		epocHelper = new EpocEnginePathHelper(data.getProjectPath());
	}

	/**
	 * Identify the new-style EPOCROOT\epoc32 relative path
	 * (used only in MMP?)
	 * @param path
	 * @return
	 */
	public boolean isEpoc32RelativeFormat(IPath path) {
		return path.segmentCount() > 0 && path.segment(0).equals("+");
	}
	

	/**
	 * Convert the given path (from an IMMPView API) into a workspace-relative
	 * path, if possible.
	 * @param context the context from which the path was retrieved
	 * @param path
	 * @return workspace-relative non-absolute path, or null if not in workspace 
	 */
	public IPath convertMMPToWorkspace(EMMPPathContext context, IPath path) {
		if (path == null)
			return null;

		if (!path.isAbsolute() && !isEpoc32RelativeFormat(path)) {
			// project-relative by design
			return epocHelper.resolve(epocHelper.projectName, path);
		}
		
		// if absolute, don't abandon hope yet -- may resolve to workspace
		// through linked resources
		IPath fullPath = convertMMPToFilesystem(context, path);

		IPath wsPath = epocHelper.convertToWorkspace(fullPath);

		// may still be null
		return wsPath;
	}

	/**
	 * Convert the given path (from an IMMPView API) into a project-relative
	 * path, if possible.
	 * @param context the context from which the path was retrieved
	 * @param path
	 * @return project-relative non-absolute path, or null 
	 */
	public IPath convertMMPToProject(EMMPPathContext context, IPath path) {
		if (path == null)
			return null;

		IPath wsPath = convertMMPToWorkspace(context, path);
		
		// don't make project-relative if path is not rooted at project
		if (wsPath != null 
				&& wsPath.segmentCount() > 1 
				&& wsPath.segment(0).equalsIgnoreCase(epocHelper.projectName)) {
			return wsPath.removeFirstSegments(1);
		}
		return null;
	}
	
	/**
	 * Convert the given path (from an IMMPView API) into a full path
	 * in the local filesystem.
	 * @param context the context from which the path was retrieved
	 * @param path
	 * @return absolute path, never null unless it came in null
	 */
	public IPath convertMMPToFilesystem(EMMPPathContext context, IPath path) {
		if (path != null && (path.isAbsolute() || isEpoc32RelativeFormat(path))) {
			if (context.useMakeEAbs()) {
				// this context interprets full paths as EPOCROOT\\epoc32 relative.
				String filename = null;
				if (context.isFile()) {
					filename = path.lastSegment();
					path = path.removeLastSegments(1);
				}
				path = makeEAbs(path);
				if (path == null)
					return null;
				
				if (filename != null)
					path = path.append(filename);
				
			} else if (context.useMakeAbs()){
				// full paths are local
			} else {
				// full paths not allowed or ignored
			}
		}
		
		return epocHelper.convertToFilesystem(path);
	}


	/**
	 * Handle logic of Path_MakeEAbs() in Symbian SDKs.  Can only be called with absolute
	 * or new-style EPOC32-relative path.
	 * @param path
	 * @return
	 */
	private IPath makeEAbs(IPath path) {
		if (path.segmentCount() == 0)
			return path;
		
		if (path.segment(0).equalsIgnoreCase("epoc32")) { //$NON-NLS-1$
			// no EPOCROOT --> no answers
			if (epocRoot == null)
				return null;

			return epocRoot.append(path);
		} else if (path.segment(0).equals("+")) {
			// no EPOCROOT --> no answers
			if (epocRoot == null)
				return null;

			// the new style for EPOC32-relative references
			return epocRoot.append("epoc32").append(path.removeFirstSegments(1)); //$NON-NLS-1$
		}
		
		return path;
	}

	/**
	 * Convert either a project-relative or a full filesystem path to an
	 * MMP-appropriate path, which may either a project-relative path,
	 * an EPOCROOT-relative path (with leading "/epoc32"), or an absolute (filesystem) path,
	 * depending on context.
	 * @param path
	 * @return path, never null unless it came in null
	 * @throws InvalidDriveInMMPPathException if the full path has a device
	 * which is incompatible with the SDK
	 */
	public IPath convertProjectOrFullPathToMMP(EMMPPathContext context, IPath path) 
		throws InvalidDriveInMMPPathException {
		if (path == null)
			return null;

		if (!path.isAbsolute()) {
			// project-relative already
			return path;
		}

		// absolute: see if it is EPOCROOT\epoc32-relative
		if (context.useMakeEAbs() && epocRoot != null) {
			IPath epocRootRelPath = FileUtils.removePrefixFromPath(
					epocRoot, path);
			if (epocRootRelPath != null) {
				return epocRootRelPath.makeAbsolute();
			}
		}		

		IPath viewPath = epocHelper.convertPathToView(path);

		// remove drive letters
		if (viewPath.getDevice() != null) {
			if (epocRoot == null || epocRoot.getDevice() == null ||
					!viewPath.getDevice().equalsIgnoreCase(epocRoot.getDevice())) {
				throw new InvalidDriveInMMPPathException(viewPath);
			}
			
			// drive matches, so remove it
			viewPath = viewPath.setDevice(null);
		}
		
		return viewPath;
	}
}
