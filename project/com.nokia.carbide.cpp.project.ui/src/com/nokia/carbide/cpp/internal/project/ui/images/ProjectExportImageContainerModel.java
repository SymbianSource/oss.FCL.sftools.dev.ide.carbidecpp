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
package com.nokia.carbide.cpp.internal.project.ui.images;

import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.internal.api.builder.ProjectExportsGatherer;
import com.nokia.carbide.cpp.ui.images.*;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.ProjectUtils;

import org.eclipse.core.runtime.IPath;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This model handles image files exported by the project through bld.inf or .pkg files.
 *
 */
public class ProjectExportImageContainerModel extends ModelFileBasedImageContainerModelBase implements IImageContainerModel {

	public final static Pattern IMAGE_EXTENSION_PATTERN = 
		Pattern.compile("(?i)png|gif|bmp|jpg|jpe|jpeg|tif|tiff|svg"); //$NON-NLS-1$

	private ArrayList<IImageModel> projectImageModels;
	private final IImageLoader imageLoader;
	private final IImageConverterFactory imageConverterFactory;

	/**
	 * @param path
	 * @param realProjectLocation
	 * @param projectInfo 
	 * @param list 
	 */
	public ProjectExportImageContainerModel(
			IImageLoader imageLoader,
			IImageConverterFactory imageConverterFactory,
			ICarbideProjectInfo projectInfo) {
		super(imageLoader, ProjectUtils.getRealProjectLocation(projectInfo.getProject()), 
				projectInfo.getAbsoluteBldInfPath());
		this.imageLoader = imageLoader;
		this.imageConverterFactory = imageConverterFactory;
		
		ProjectExportsGatherer gatherer = new ProjectExportsGatherer(projectInfo);
		
		projectImageModels = new ArrayList<IImageModel>();
		
		Set<IPath> visitedEntries = new HashSet<IPath>();
		for (Map.Entry<IPath, IPath> entry : gatherer.getFilesystemToEpocExportMap().entrySet()) {
			IPath sourcePath = entry.getKey();
			IPath targetPath = entry.getValue();
			
			// file may be exported multiple times; we only need one source to match
			if (visitedEntries.contains(targetPath))
				continue;
			
			visitedEntries.add(targetPath);
			
			// we need to test the extension both on the host and device 
			Matcher matcher1 = IMAGE_EXTENSION_PATTERN.matcher(FileUtils.getSafeFileExtension(sourcePath));
			Matcher matcher2 = IMAGE_EXTENSION_PATTERN.matcher(FileUtils.getSafeFileExtension(targetPath));
			if (matcher1.matches() && matcher2.matches()) {
				if (!targetPath.isAbsolute() || targetPath.getDevice() == null) {
					continue;
				}
				
				// see if the source file is in the project
				IPath projectRelativePath = FileUtils.removePrefixFromPath(baseLocation, sourcePath);
				
				projectImageModels.add(new ProjectExportImageModel(this, 
					baseLocation, 
					projectRelativePath != null ? projectRelativePath : sourcePath,
					targetPath
				));
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.images.IImageContainerModel#createImageModels()
	 */
	public IImageModel[] createImageModels() {
		return (IImageModel[]) projectImageModels.toArray(new IImageModel[projectImageModels.size()]);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.images.IImageContainerModel#getImageConverterFactory()
	 */
	public IImageConverterFactory getImageConverterFactory() {
		return imageConverterFactory;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.images.IImageContainerModel#getImageLoader()
	 */
	public IImageLoader getImageLoader() {
		return imageLoader;
	}

	
}
