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
* This class manages conversion of IPaths provided by an EPOC engine view 
* back and forth to real-world paths in the filesystem or workspace.
* <p>
* <i>Use MMPViewPathHelper for any MMP-provided IPaths, which have
* interesting semantics for absolute paths.  This is only suitable
* for bld.inf or Makefiles.</i>
* <p>
* This also handles correcting the capitalization in a path.  Eclipse's
* IPaths are not case-insensitive, as they should be, so we need to
* match up file references to real references and provide IPaths
* that will find real content in the workspace.
*
*
*/
package com.nokia.carbide.cdt.builder;

import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.IData;
import com.nokia.carbide.cpp.epoc.engine.model.IView;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.PathUtils;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;

import java.io.IOException;

public class EpocEnginePathHelper {
	protected String projectName;
	protected IPath projectRoot;
	protected IProject project;	// may be null
	
	/** 
	 * Construct an instance with the project root.
	 * This constructor is used when a project doesn't exist yet.
	 * projectRoot may not be null. 
	 * @param projectRoot filesystem path to root of project 
	 */ 
	public EpocEnginePathHelper(IPath projectRoot) {
		Check.checkArg(projectRoot);
		this.projectName = projectRoot.lastSegment();
		this.projectRoot = projectRoot;
	}
	

	/** 
	 * Construct an instance with the given configuration providing
	 * the project.
	 * @param buildConfiguration
	 */ 
	public EpocEnginePathHelper(ICarbideBuildConfiguration buildConfiguration) {
		setupProjectInfo(buildConfiguration.getCarbideProject().getProject());
	}
	
	/** 
	 * Construct an instance with the given project.
	 * <p>
	 * project may not be null.
	 * @param project
	 */ 
	public EpocEnginePathHelper(IProject project) {
		Check.checkArg(project);
		setupProjectInfo(project);
	}
	
	/** 
	 * Construct an instance using the given view, to provide the
	 * project root.
	 * <p>
	 * The view may not be null.
	 * <p> 
	 * @param view
	 */ 
	public EpocEnginePathHelper(IView view) {
		Check.checkArg(view);
		this.projectRoot = view.getViewConfiguration().getViewParserConfiguration().getProjectLocation();
		IPath wsPath = convertToWorkspace(projectRoot);
		if (wsPath != null && wsPath.segmentCount() > 0) {
			this.project = ResourcesPlugin.getWorkspace().getRoot().getProject(wsPath.segment(0));
		}
	}

	/** 
	 * Construct an instance using the given data, to provide the
	 * project root.
	 * <p>
	 * The data may not be null.
	 * <p> 
	 * @param data
	 * @param unused data to distinguish IData from IView constructor
	 */ 
	public EpocEnginePathHelper(IData data, boolean unused) {
		Check.checkArg(data);
		this.projectRoot = data.getProjectPath();
		IPath wsPath = convertToWorkspace(projectRoot);
		if (wsPath != null && wsPath.segmentCount() > 0) {
			this.project = ResourcesPlugin.getWorkspace().getRoot().getProject(wsPath.segment(0));
		}
	}

	/**
	 * 
	 */
	private void setupProjectInfo(IProject project) {
		this.project = project; 
		projectName = project.getName();
		// try to handle linked resources
		projectRoot = project.getRawLocation();
		if (projectRoot == null)
			projectRoot = project.getLocation();
		Check.checkState(projectRoot != null);
		projectRoot = projectRoot.addTrailingSeparator();
	}

	/**
	 * Convert a filesystem path to the workspace, taking care
	 * to account for incorrect capitalization.  
	 * @param fullPath
	 * @return workspace-relative non-absolute path, or null if not resolvable to workspace
	 */
	public IPath convertFilesystemToWorkspace(IPath fullPath) {
		fullPath = PathUtils.findExistingPathIfCaseSensitive(fullPath);
		IPath wsPath = FileUtils.convertToWorkspacePath(fullPath, true);
		if (wsPath != null && wsPath.isAbsolute())
			wsPath = wsPath.makeRelative();
		return wsPath;
	}
	
	/**
	 * Convert the given path (from an IView API) into a workspace-relative
	 * path, if possible.
	 * @param path relative or absolute path
	 * @return workspace-relative non-absolute path, or null if not in workspace 
	 */
	public IPath convertToWorkspace(IPath path) {
		if (path == null)
			return null;

		if (!path.isAbsolute()) {
			// project-relative by design
			return resolve(projectName, path);
		}
		
		// if absolute, don't abandon hope yet -- may resolve to workspace
		// through linked resources
		IPath fullPath = convertToFilesystem(path);

		// note: copying logic in #convertFilesystemToWorkspace() because we don't
		// want to re-canonicalize
		IPath wsPath = FileUtils.convertToWorkspacePath(fullPath, false);
		if (wsPath != null && wsPath.isAbsolute())
			wsPath = wsPath.makeRelative();
		
		// may still be null
		return wsPath;
	}

	/**
	 * Convert the given path (from an IView API) into a project-relative
	 * path, if possible.
	 * @param path relative or absolute path
	 * @return project-relative non-absolute path, or null 
	 */
	public IPath convertToProject(IPath path) {
		if (path == null)
			return null;

		IPath wsPath = convertToWorkspace(path);
		
		// don't make project-relative if path is not rooted at project
		if (wsPath != null 
				&& wsPath.segmentCount() > 1 
				&& wsPath.segment(0).equalsIgnoreCase(projectName)) {
			return wsPath.removeFirstSegments(1);
		}
		return null;
	}
	
	/**
	 * Convert the given path (from an IView API) into a 
	 * canonical full path in the local filesystem.  This will resolve differences
	 * in case sensitivity.
	 * @param path relative or absolute path
	 * @return absolute path, never null unless it came in null
	 */
	public IPath convertToFilesystem(IPath path) {
		if (path == null)
			return null;
		
		IPath fullPath;
		if (path.isAbsolute()) {
			fullPath = path;
		} else {
			// relative path is project-relative
			fullPath = projectRoot.append(path);
		}
		
		try {
			// Since we have a filesystem path, try using the filesystem
			// and Java's smarter APIs to fix the caps early. 
			// Watch out for the device being set incorrectly, though.
			String device = fullPath.getDevice();
			if (device == null && projectRoot != null) {
				// in case the path is absolute without a drive letter
				device = projectRoot.getDevice();
			}
			
			// canonicalize (to remove .. , resolve links, etc)
			fullPath = PathUtils.createPath(fullPath.toFile().getCanonicalPath()).setDevice(device);
			fullPath = PathUtils.findExistingPathIfCaseSensitive(fullPath);
			
		} catch (IOException e) {
		}

		return fullPath;
		
	}

	/**
	 * Resolve a project-relative path to correct any problems in
	 * capitalization.  This version only works with a constructor
	 * that passes a real project.  
	 * @param projectName
	 * @param path project-relative path
	 * @return resolved path, as far as actual resources exist (the suffix
	 * may be unchanged if an intervening folder is missing) 
	 */
	public IPath resolve(String projectName, IPath path) {
		path = new Path(projectName).append(path);
		if (project == null)
			return path;
		return FileUtils.getCanonicalWorkspacePath(path);
	}


	/**
	 * Convert a project-relative or absolute path to the 
	 * format expected by the view.
	 * Inverse of #convertTo{Project|Filesystem}().
	 * @param path relative path for project-relative path, or full path
	 * for filesystem path
	 * @return project-relative path, if possible, or self 
	 */
	public IPath convertPathToView(IPath path) {
		if (path == null)
			return null;

		if (!path.isAbsolute()) {
			// project-relative already
			return path;
		}

		// see if absolute path is inside project
		IPath projectRelPath = FileUtils.removePrefixFromPath(
				projectRoot, path);
		if (projectRelPath != null)
			return projectRelPath;

		// unknown
		return path;
	}
	
}
