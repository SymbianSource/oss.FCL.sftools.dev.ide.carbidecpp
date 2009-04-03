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

import com.nokia.carbide.cpp.internal.project.ui.images.providers.ProjectImageContentProvider;
import com.nokia.carbide.cpp.internal.project.ui.images.providers.ThumbnailImageLabelProvider;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.sdt.utils.ui.ThumbnailGridViewer;
import com.nokia.sdt.utils.ui.ThumbnailWithDescriptionComposite;

import org.eclipse.swt.widgets.*;

/**
 * Add images from the project.
 *
 */
public class AddProjectImagesDialog extends AddImagesDialog {

	private ThumbnailImageLabelProvider thumbnailImageLabelProvider;

	/**
	 * @param parentShell
	 * @param editorContext
	 */
	protected AddProjectImagesDialog(Shell parentShell, MultiImageEditorContextBase editorContext) {
		super(parentShell, editorContext);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.editors.images.AddImagesDialog#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.getString("AddProjectImagesDialog.AddImagesFromProjectTitle")); //$NON-NLS-1$

	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.editors.images.AddImagesDialog#createDialogTopHalf(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createDialogTopHalf(Composite parent) {
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.editors.images.AddImagesDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Control control = super.createDialogArea(parent);
		editorContext.setHelpContext(control, ImageEditorIds.ADD_PROJECT_IMAGES_DIALOG);
		return control;
	}
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.editors.images.AddImagesDialog#configureThumbnailViewer(com.nokia.sdt.utils.ui.ThumbnailWithDescriptionComposite)
	 */
	@Override
	protected void configureThumbnailViewer(
			ThumbnailWithDescriptionComposite thumbAndDesc) {
				
		super.configureThumbnailViewer(thumbAndDesc);
		thumbAndDesc.setViewerTitle(Messages.getString("AddProjectImagesDialog.AvailableProjectImagesLabel")); //$NON-NLS-1$

		ThumbnailGridViewer thumbnailViewer = thumbAndDesc.getThumbnailViewer();
		
		if (editorContext instanceof MultiImageEditorContext)
			thumbnailViewer.setContentProvider(new ProjectImageContentProvider(
					((MultiImageEditorContext) editorContext).getMultiImageSource(), true));
		else
			thumbnailViewer.setContentProvider(new ProjectImageContentProvider(
					((AIFEditorContext) editorContext).getMMPAIFInfo(), true));

		thumbnailImageLabelProvider = editorContext.getThumbnailImageLabelProvider();
		thumbnailViewer.setLabelProvider(thumbnailImageLabelProvider);
		
		if (editorContext.getProject() != null) {
			thumbnailViewer.setInput(editorContext.getProject());
		} else {
			thumbnailViewer.setInput(new Object()); 
		}

	}
	
}
