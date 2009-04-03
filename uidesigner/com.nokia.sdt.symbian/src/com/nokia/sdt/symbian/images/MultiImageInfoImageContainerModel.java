/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.symbian.images;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.internal.project.ui.images.*;
import com.nokia.carbide.cpp.ui.images.IImageContainerEditorProvider;
import com.nokia.carbide.cpp.ui.images.IImageModel;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.ProjectUtils;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.progress.UIJob;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for MultiImageInfo images
 *
 */

public class MultiImageInfoImageContainerModel extends ModelFileBasedImageContainerModelBase implements IMultiImageSourceImageContainerModel {

	private final MultiImageInfo mii;
	private IImageConverterFactory imageConverterFactory;
	private ProjectImageInfo info;

	/**
	 * @param info
	 * @param mii
	 */
	public MultiImageInfoImageContainerModel(ProjectImageInfo info,
			MultiImageInfo mii) {
		super(info.getImageLoader(),
				ProjectUtils.getRealProjectLocation(info.getProject()),
				ProjectUtils.getRealProjectLocation(info.getProject()).append(mii.getSourceFile())
				);
		mii.setImageContainerModel(this);
		imageConverterFactory = CarbideImageModelFactory.getImageConverterFactory(info.getCarbideProjectInfo());
		this.mii = mii;
		this.info = info;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.project.ui.images.IImageContainerModel#createImageModels()
	 */
	public IImageModel[] createImageModels() {
		List<IImageModel> models = new ArrayList<IImageModel>();
		ImageInfo[] imageInfos = mii.getImageInfos();
		
		for (ImageInfo imageInfo : imageInfos) {
			// try to add image+mask pair first
			ImageInfo maskInfo = mii.getMaskForImage(imageInfo);
			if (maskInfo != null) {
				models.add(new ImageInfoImageModel(this, baseLocation, mii, imageInfo, maskInfo));
			} 
			
			// and add an entry for the images by itself
			models.add(new ImageInfoImageModel(this, baseLocation, mii, imageInfo));
		}
		return (IImageModel[]) models.toArray(new IImageModel[models.size()]);
	}

	public IPath getTargetPath() {
		return mii.getBinaryFile();
	}

	/**
	 * @return
	 */
	public IImageConverterFactory getImageConverterFactory() {
		return imageConverterFactory;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.project.ui.images.ModelFileBasedImageContainerModelBase#createEditorProvider()
	 */
	@Override
	public IImageContainerEditorProvider createEditorProvider() {
		if (modelPath == null)
			return null;
		
		final IFile wsFile = FileUtils.convertFileToIFile(modelPath.toFile());
		if (wsFile == null)
			return null;
		
		return new IImageContainerEditorProvider() {

			public Job createEditingJob(final Shell shell) {
				return new UIJob(Messages.getString("MultiImageInfoImageContainerModel.EditingJobLabel")) { //$NON-NLS-1$

					@Override
					public IStatus runInUIThread(IProgressMonitor monitor) {
						ICarbideProjectInfo projectInfo = CarbideBuilderPlugin.getBuildManager().
							getProjectInfo(wsFile.getProject());
						if (projectInfo != null) {
							ImageModelEditingUtilities.editMbmMifFile(shell, info.getCarbideProjectInfo(), mii); 
							return Status.OK_STATUS;
						} else {
							return Status.CANCEL_STATUS;
						}
					}
				};
			}
			
		};
	}
}
