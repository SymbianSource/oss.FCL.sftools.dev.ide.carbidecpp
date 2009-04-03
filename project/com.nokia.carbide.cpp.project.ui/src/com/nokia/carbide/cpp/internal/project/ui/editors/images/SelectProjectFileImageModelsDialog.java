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
package com.nokia.carbide.cpp.internal.project.ui.editors.images;

import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.carbide.cpp.internal.project.ui.images.providers.DelayedImageLoadingLabelProviderBase;
import com.nokia.carbide.cpp.internal.project.ui.images.providers.ThumbnailImageModelLabelProvider;
import com.nokia.carbide.cpp.internal.ui.images.FilesystemImageModelContentProvider;
import com.nokia.carbide.cpp.ui.images.IFileImageModel;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.sdt.utils.ui.ThumbnailGridViewer;
import com.nokia.sdt.utils.ui.ThumbnailWithDescriptionComposite;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import java.io.File;
import java.util.regex.Pattern;

/**
 * This dialog prompts the user to select one of more images from the project.
 *
 */
public class SelectProjectFileImageModelsDialog extends SelectOrAddImagesDialogBase {

	protected Pattern IMAGE_FILE_EXTENSION_PATTERN = Pattern.compile(
			".*\\.(bmp|png|gif|jpg|jpeg|svg(t|z)?|tiff?)", //$NON-NLS-1$ 
			Pattern.CASE_INSENSITIVE);
	
	protected DelayedImageLoadingLabelProviderBase thumbnailImageLabelProvider;
	protected final IProject project;
	protected final String helpText;

	protected IPath projectPath;

	protected SelectProjectFileImageModelsDialog(Shell shell, IProject project,
			String helpText) {
		super(shell);
		this.project = project;
		this.projectPath = ProjectUtils.getRealProjectLocation(project);
		this.helpText = helpText;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.ui.internal.images.AddImagesDialogBase#getDialogSettings()
	 */
	@Override
	protected IDialogSettings getDialogSettings() {
		if (ProjectUIPlugin.getDefault() != null) {
			return ProjectUIPlugin.getDefault().getDialogSettings();
		} else {
			return new DialogSettings("root"); //$NON-NLS-1$
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.ui.images.AddImagesDialogBase#createDialogContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createDialogContents(Composite composite) {
		createThumbnailViewer(composite);
		
		updateThumbnails();

	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.ui.internal.images.AddImagesDialogBase#getProject()
	 */
	@Override
	protected IProject getProject() {
		return project;
	}
	
	/**
	 * When changing inputs, recreate the thumbnail label provider
	 * so it doesn't keep loading images from the previous directory
	 * before resolving the images we want to see.
	 */
	protected void resetThumbnailLabelProvider() {
		if (thumbnailImageLabelProvider != null) {
			thumbnailImageLabelProvider.interrupt();
			thumbnailImageLabelProvider.flush();
		} else {
			thumbnailImageLabelProvider = new ThumbnailImageModelLabelProvider(new Point(64, 64)); 
			thumbnailViewer.setLabelProvider(thumbnailImageLabelProvider);
		}
	}

	/**
	 * 
	 */
	protected void updateThumbnails() {
		resetThumbnailLabelProvider();
		thumbnailViewer.setInput(project.getLocation().toFile());
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.ui.images.AddImagesDialogBase#configureThumbnailViewer(com.nokia.sdt.utils.ui.ThumbnailWithDescriptionComposite)
	 */
	@Override
	protected void configureThumbnailViewer(
			ThumbnailWithDescriptionComposite thumbAndDesc) {
		thumbAndDesc.getDescriptionText().setText(helpText);

		ThumbnailGridViewer thumbnailViewer = thumbAndDesc.getThumbnailViewer();
		
		// recurse to find all the images in the project
		thumbnailViewer.setContentProvider(new AvailableFileImageModelContentProvider(
				IMAGE_FILE_EXTENSION_PATTERN, true));
	}
	
	public class AvailableFileImageModelContentProvider extends FilesystemImageModelContentProvider {

		public AvailableFileImageModelContentProvider(Pattern pattern, boolean recursive) {
			super(pattern, recursive);
		}

		/* (non-Javadoc)
		 * @see com.nokia.carbide.cpp.ui.internal.images.FilesystemImageModelContentProvider#accept(java.io.File)
		 */
		@Override
		protected boolean accept(File file) {
			return acceptFile(file);
		}
	}


	/**
	 * Override to further filter the files available in the thumbnail viewer
	 * (e.g. those that have illegal names or which are already in use).
	 * Files have already been filtered according to image filenames.
	 * @param file
	 * @return true to keep file; false to reject
	 */
	public boolean acceptFile(File file) {
		return true;
	}

	/**
	 * Get the array of image models selected from the viewer
	 * @return array, never null
	 */
	public IFileImageModel[] getSelectedImageModels() {
		return (IFileImageModel[]) selectedImages.toArray(new IFileImageModel[selectedImages.size()]);
	}
}
