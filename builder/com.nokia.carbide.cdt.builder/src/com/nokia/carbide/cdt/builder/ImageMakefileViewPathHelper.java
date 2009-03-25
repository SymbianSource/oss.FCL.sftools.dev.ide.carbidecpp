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
* Helper to resolve paths used in image makefiles.
* <p>
* mifconv has some implicit search paths, as evidenced by this
* standard comment:
* <pre>
# NOTE 2: Usually, source paths should not be included in the bitmap
# definitions. MifConv searches for the icons in all icon directories in a
# predefined order, which is currently \s60\icons, \s60\bitmaps2.
# The directory \s60\icons is included in the search only if the feature flag
# __SCALABLE_ICONS is defined.
</pre>
* (We ignore the distinction about __SCALABLE_ICONS and always search this directory.)
* <p>
* This helper class is needed because the to-be-searched files do not have source paths
* in the makefile, but are resolved in the view into project-relative paths anyway.  
* We need to un-projectize such paths to see if their files are in the standard directories or not
* and do the reverse when converting back to the view.
*
*
*
*/
package com.nokia.carbide.cdt.builder;

import com.nokia.carbide.cpp.epoc.engine.image.*;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileData;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileView;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.FileUtils;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import java.io.File;
import java.util.*;

public class ImageMakefileViewPathHelper {
	private EpocEnginePathHelper epocHelper;
	private IPath[] stdPaths;
	private IImageMakefileData data;

	/** 
	 * Construct an instance using the given image makefile view, to provide the
	 * project root, and the build configurations of interest.
	 * <p> 
	 * @param view the non-null image makefile view (does not take ownership)
	 * @param configurations the build configurations (may not be null)
	 */ 
	public ImageMakefileViewPathHelper(IImageMakefileView view, ISymbianBuildContext[] configurations) {
		Check.checkArg(view);
		this.data = view;
		gatherConfigurationStandardPaths(configurations);
		epocHelper = new EpocEnginePathHelper(data, false);
	}
	
	/** 
	 * Construct an instance using the given image makefile data, to provide the
	 * project root, and the build configurations of interest.
	 * <p> 
	 * @param view the non-null image makefile view (does not take ownership)
	 * @param configurations the build configurations (may not be null)
	 */ 
	public ImageMakefileViewPathHelper(IImageMakefileData data, ISymbianBuildContext[] configurations) {
		Check.checkArg(data);
		this.data = data;
		gatherConfigurationStandardPaths(configurations);
		epocHelper = new EpocEnginePathHelper(data, false);
	}

	/** 
	 * Construct an instance using the given image makefile view, to provide the
	 * project root, and an array of EPOCROOTs.  
	 * <p> 
	 * @param view the non-null image makefile view (does not take ownership)
	 * @param sdkRoots the directories of SDKs in use (may not be null)
	 */ 
	public ImageMakefileViewPathHelper(IImageMakefileView view, IPath[] sdkRoots) {
		Check.checkArg(view);
		this.data = view;
		gatherStandardPaths(sdkRoots);
		epocHelper = new EpocEnginePathHelper(view);
	}

	/**
	 * Get the filesystem paths to the standard icon/bitmap search directories.
	 * @param configs 
	 * @return
	 */
	private void findImageStandardPaths(Collection<IPath> paths, IPath sdkRoot) {
		// in older SDKs we find this under the s60 folder
		IPath path = sdkRoot.append("s60/icons"); //$NON-NLS-1$
		if (path.toFile().exists())
			paths.add(path);
		path = sdkRoot.append("s60/bitmaps2"); //$NON-NLS-1$
		if (path.toFile().exists())
			paths.add(path);

		// in later SDKs these move into epoc32/s60
		IPath includeDir = sdkRoot.append("epoc32/s60");
		path = includeDir.append("icons"); //$NON-NLS-1$
		if (path.toFile().exists())
			paths.add(path);
		path = includeDir.append("bitmaps"); //$NON-NLS-1$
		if (path.toFile().exists())
			paths.add(path);
	}

	/**
	 * Get the filesystem paths to the standard icon/bitmap search directories.
	 * @param configs 
	 * @return
	 */
	private void gatherStandardPaths(IPath[] sdkRoots) {
		Set<IPath> paths = new LinkedHashSet<IPath>();
		for (IPath sdkRoot : sdkRoots) {
			findImageStandardPaths(paths, sdkRoot);
		}
		this.stdPaths = paths.toArray(new IPath[paths.size()]);
	}

	/**
	 * Get the filesystem paths to the standard icon/bitmap search directories.
	 * @param configurations 
	 * @return
	 */
	private void gatherConfigurationStandardPaths(ISymbianBuildContext[] configurations) {
		Set<IPath> paths = new LinkedHashSet<IPath>();
		for (ISymbianBuildContext config : configurations) {
			IPath sdkRoot = new Path(config.getSDK().getEPOCROOT());
			findImageStandardPaths(paths, sdkRoot);
		}
		this.stdPaths = paths.toArray(new IPath[paths.size()]);
	}

	/**
	 * Get the filesystem paths to the standard icon/bitmap search directories.
	 * @return
	 */
	private IPath[] getStandardPaths() {
		return stdPaths;
	}
	
	/**
	 * Look up a candidate for the image referenced by the path from an
	 * IImageSource.<p>
	 * This first tries to resolve a relative path to the
	 * expected project-relative location, then searches the standard
	 * locations.  If nothing is found, the non-existing project-relative file is
	 * returned.  For absolute paths, the path itself 
	 * (existing or not) is returned.
	 * @param path relative or absolute path from a IImageSource
	 * @return path for an existing or candidate file, either relative for project-relative
	 * files or absolute for filesystem path
	 */
	public IPath findCandidateImagePath(IPath path) {
		if (path == null)
			return null;
		
		if (path.isAbsolute())
			return path;
		
		// see if this path is in the same place as the Makefile
		IPath makefilePath = data.getModelPath();
		IPath projectRoot = data.getProjectPath();
		IPath suffix = FileUtils.removePrefixFromPath(makefilePath.removeLastSegments(1), projectRoot.append(path));
		if (suffix == null || suffix.segmentCount() > 1)
			return path;

		if (makefilePath.append(suffix).toFile().exists()) {
			return path;
		}
		
		// try searching paths in the SDK
		IPath[] stdPaths = getStandardPaths();
		for (IPath stdPath : stdPaths) {
			for (int ext = 0; ext < 3; ext++) {
				IPath thePath = stdPath.append(suffix);
				if (ext == 1)
					thePath = thePath.addFileExtension("svg"); //$NON-NLS-1$
				else if (ext == 2)
					thePath = thePath.addFileExtension("bmp"); //$NON-NLS-1$
				File fsFile = thePath.toFile();
				if (fsFile.exists()) {
					// try to resolve to workspace
					IPath wsPath = FileUtils.convertToWorkspacePath(thePath, true);
					if (wsPath == null)
						return thePath;
					IPath projPath = FileUtils.removePrefixFromPath(projectRoot, wsPath);
					if (projPath == null || projPath.segmentCount() > 1)
						return thePath;
					return projPath;
				}
				if (ext == 0 && suffix.getFileExtension() != null)
					break;
			}
		}
		
		// not a standard file, and not otherwise existing: default to project-relative
		return path;
	}
	
	/**
	 * Look up a candidate for the mask referenced by the given
	 * IImageSource.  The source's maskpath may be non-null or null.
	 * If null, we try to find anything that conceivably goes with
	 * the image.  If non-null, we look for this file, possibly changing
	 * the extension.  (Blame this on mifconv.)
	 * <p>
	 * The file search behavior otherwise follows {@link #findCandidateImagePath(IPath)}.
	 * @param path relative or absolute path from a IImageSource
	 * @return path for an existing or candidate file, either relative for project-relative
	 * files or absolute for filesystem path
	 */
	public IPath findCandidateMaskPath(IImageSource imageSource) {
		// never has a real mask file
		if (imageSource instanceof ISVGSourceReference)
			return null;
		
		// no mask if no depth
		if (imageSource.getMaskDepth() == 0)
			return null;
		
		// use the bitmap's paired mask if present, else 
		// it's either an unknown image source or the filename is not specified 
		IPath maskPath = null;
		if (imageSource instanceof IBitmapSource)
			maskPath = ((IBitmapSource) imageSource).getMaskPath();
		if (maskPath == null)
			maskPath = imageSource.getDefaultMaskPath();
		
		if (maskPath == null)
			return null;

		return findCandidateImagePath(maskPath);
	}
	
	/**
	 * Convert a path that may reference an EPOCROOT-relative image file
	 * into a view path.  Finally, convert the path to one suitable
	 * for the view's IImageSource.  This is the inverse of
	 * #findCandidateImagePath().
	 * @param path either a relative path, taken as project-relative,
	 * or a full path, which is a filesystem full path
	 * @return if path is absolute and resolves to
	 * standard image lookup directory, a file relative to the Makefile; else, 
	 * the IImageSource-appropriate path. 
	 */
	public IPath simplifyStandardImagePath(IPath path) {
		if (path == null)
			return null;
		
		if (path.isAbsolute()) {
			// try searching paths in the SDK
			IPath[] stdPaths = getStandardPaths();
			for (IPath stdPath : stdPaths) {
				IPath suffix = FileUtils.removePrefixFromPath(stdPath, path);
				if (suffix == null)
					continue;
				
				// make it look like it's Makefile-relative, so
				// it will resolve to a bare filename in the end (yes, gross)
				IPath projectRoot = data.getProjectPath();
				IPath makefilePath = data.getModelPath().removeLastSegments(1);
				IPath makefileRelPath = FileUtils.removePrefixFromPath(projectRoot, makefilePath);
				if (makefileRelPath != null)
					return makefileRelPath.append(suffix);
				
				return suffix;
			}
			
			// unknown absolute path
			return epocHelper.convertPathToView(path);
		}

		// already project-relative
		return path;
	}
}
