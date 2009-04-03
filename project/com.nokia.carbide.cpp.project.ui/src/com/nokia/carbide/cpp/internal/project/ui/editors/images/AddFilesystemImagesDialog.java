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

import com.nokia.carbide.cpp.epoc.engine.image.IBitmapSourceReference;
import com.nokia.carbide.cpp.epoc.engine.image.IImageSourceReference;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.carbide.cpp.internal.project.ui.images.providers.FilesystemImageContentProvider;
import com.nokia.carbide.cpp.internal.project.ui.images.providers.ThumbnailImageLabelProvider;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.cpp.internal.api.utils.ui.DirectorySelectorWithHistory;
import com.nokia.sdt.utils.ui.ThumbnailWithDescriptionComposite;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Add images from the filesystem.  This imports files and copies them to a
 * given location in the project.
 *
 */
public class AddFilesystemImagesDialog extends AddImagesDialog {

	private ThumbnailImageLabelProvider thumbnailImageLabelProvider;
	private DirectorySelectorWithHistory directories;
	private IProject project;
	
	/**
	 * @param parentShell
	 * @param editorContext
	 */
	protected AddFilesystemImagesDialog(Shell parentShell, MultiImageEditorContextBase editorContext) {
		super(parentShell, editorContext);
		project = editorContext.getProject();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.editors.images.AddImagesDialog#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.getString("AddFilesystemImagesDialog.AddImagesFromFilesystemTitle")); //$NON-NLS-1$
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.editors.images.AddImagesDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Control control = super.createDialogArea(parent);
		if (Platform.isRunning()) {
			PlatformUI.getWorkbench().getHelpSystem().setHelp(control, ImageEditorIds.ADD_FILESYSTEM_IMAGES_DIALOG);
		}
		return control;
	}
    
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.editors.images.AddImagesDialog#createDialogTopHalf(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createDialogTopHalf(Composite parent) {
		directories = createDirectorySelectionComposite(parent);
		directories.addListener(new DirectorySelectorWithHistory.DirectoryChangeListener() {

			public void setDirectory() {
				updateThumbnails();
			}
			
		});
	}
	
	/**
	 * 
	 */
	protected void updateThumbnails() {
		resetThumbnailLabelProvider();
		thumbnailViewer.setInput(directories.getCurrentDirectory());
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.editors.images.AddImagesDialog#configureThumbnailViewer(com.nokia.sdt.utils.ui.ThumbnailWithDescriptionComposite)
	 */
	@Override
	protected void configureThumbnailViewer(
			ThumbnailWithDescriptionComposite thumbAndDesc) {
				
		super.configureThumbnailViewer(thumbAndDesc);
		
		thumbAndDesc.setViewerTitle(Messages.getString("AddFilesystemImagesDialog.AvailableDirectoryImagesLabel")); //$NON-NLS-1$

		thumbnailViewer = thumbAndDesc.getThumbnailViewer();
		
		if (editorContext instanceof MultiImageEditorContext)
			thumbnailViewer.setContentProvider(
					new FilesystemImageContentProvider(
							editorContext.getImageLoader(),
							((MultiImageEditorContext) editorContext).getMultiImageSource()));
		else
			thumbnailViewer.setContentProvider(
					new FilesystemImageContentProvider(
							editorContext.getImageLoader(),
							((AIFEditorContext) editorContext).getMMPAIFInfo()));
		resetThumbnailLabelProvider();

	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.editors.images.AddImagesDialog#createDialogBottomHalf(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createDialogBottomHalf(Composite parent) {
		super.createDialogBottomHalf(parent);
		updateThumbnails();
		
		createCopyFilesToProjectComposite(parent);
	}

	/**
	 * When changing inputs, recreate the thumbnail label provider
	 * so it doesn't keep loading images from the previous directory
	 * before resolving the images we want to see.
	 */
	private void resetThumbnailLabelProvider() {
		if (thumbnailImageLabelProvider != null) {
			thumbnailImageLabelProvider.interrupt();
			thumbnailImageLabelProvider.flush();
		} else {
			thumbnailImageLabelProvider = editorContext.getThumbnailImageLabelProvider(); 
			thumbnailViewer.setLabelProvider(thumbnailImageLabelProvider);
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.ui.images.AddImagesDialogBase#getProject()
	 */
	@Override
	protected IProject getProject() {
		return project;
	}
	
	protected void copyImagesToProject() {
		List<IImageSourceReference> destImageSources = getImageSourceReferences();
		List<IImageSourceReference> sourceImageSources = new ArrayList<IImageSourceReference>(destImageSources);
		destImageSources.clear();

		ProgressBar copyingProgressBar = new ProgressBar(getShell(), SWT.HORIZONTAL);
		copyingProgressBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		copyingProgressBar.setMinimum(0);
		copyingProgressBar.setMaximum(sourceImageSources.size());
		int index = 0;
		
		StatusBuilder builder = new StatusBuilder(ProjectUIPlugin.getDefault());
		for (IImageSourceReference source : sourceImageSources) {
			copyingProgressBar.setSelection(index++);
			IImageSourceReference destSource = source.copy();
			if (source.getPath() != null) {
				destSource.setPath(copyImageToProject(source.getPath(), builder));
			}
			if (source instanceof IBitmapSourceReference) {
				IPath maskPath = ((IBitmapSourceReference) source).getMaskPath();
				if (maskPath != null) {
					((IBitmapSourceReference) destSource).setMaskPath(copyImageToProject(
							maskPath, builder));
				}
			}
			destImageSources.add(destSource);
		}
		
		//builder.add(Logging.newStatus(ProjectUIPlugin.getDefault(), new Exception("test")));
		
		if (builder.getTotalCount() != 0) {
			List<String> msgs = new ArrayList<String>();
			for (Object status : builder.getStatusList()) {
				msgs.add(((IStatus)status).getMessage());
			}
			MessageDialog.openError(
					getShell(), 
					Messages.getString("AddFilesystemImagesDialog.CopyingFailedTitle"),  //$NON-NLS-1$
					MessageFormat.format(Messages.getString("AddFilesystemImagesDialog.CopyingFailedDescription"), //$NON-NLS-1$
							new Object[] { 
							TextUtils.formatTabbedList(msgs) 
					}));
		}
	}
}
