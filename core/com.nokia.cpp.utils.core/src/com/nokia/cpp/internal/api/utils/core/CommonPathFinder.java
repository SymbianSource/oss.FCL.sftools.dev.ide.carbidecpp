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
package com.nokia.cpp.internal.api.utils.core;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import java.util.List;

/**
 * Find the common base path for a list of paths.
 *
 */
public class CommonPathFinder {

	private IPath common = null;
	
	/**
	 * Return the generated common path.
	 * @return common path, or null for empty set
	 */
	public IPath getCommonPath() {
		return common;
	}
	
	/**
	 * Reset the common path to the given directory.
	 * @param baseDir
	 */
	public void reset(IPath baseDir) {
		common = null;
		addDirectory(baseDir);
	}
	
	/**
	 * Add a path representing a directory to the finder.  The trailing
	 * slash, if any, will be removed.  
	 * The common path becomes the common prefix of the current
	 * common path and the new path.
	 * @param dirPath a path with a known root (e.g. a project), or null
	 */
	public void addDirectory(IPath dirPath) {
		if (dirPath == null)
			return;
		
		IPath dir = dirPath.removeTrailingSeparator();
		if (common == null)
			common = dir;
		else {
			int matching = FileUtils.matchingFirstSegments(common, dir);
			if (matching < common.segmentCount())
				common = dir.uptoSegment(matching);
		}
	}

	/**
	 * Add a path representing a file to the finder.  The trailing
	 * component (assumed to be a file) will be removed.  
	 * The common path becomes the common prefix of the current
	 * common path and the new path.
	 * @param filePath a path with a known root (e.g. a project), or null
	 */
	public void addFile(IPath filePath) {
		if (filePath == null)
			return;
		IPath dir = getDirectory(filePath);
		addDirectory(dir);
	}

	/**
	 * Change a file path to a directory path.  The empty path
	 * may be returned for a file without a directory.
	 * @param filePath
	 * @return
	 */
	private IPath getDirectory(IPath filePath) {
		filePath = filePath.removeTrailingSeparator();
		if (filePath.segmentCount() >= 1)
			return filePath.removeLastSegments(1);
		return new Path("");
	}

	/**
	 * Add a list of paths representing files to the finder to
	 * determine a common base path.
	 * @param filePaths a list of paths each with a known root (e.g. a project)
	 */
	public void addAllFiles(List<IPath> filePaths) {
		for (IPath path : filePaths)
			addFile(path);
	}

	/**
	 * Create a path relative to the common path.
	 * @param path one of the paths previously added to the finder
	 * @return common path relative path
	 */
	public IPath makeRelativePath(IPath path) {
		if (common == null)
			return path;
		return path.removeFirstSegments(common.segmentCount());
	}
}
