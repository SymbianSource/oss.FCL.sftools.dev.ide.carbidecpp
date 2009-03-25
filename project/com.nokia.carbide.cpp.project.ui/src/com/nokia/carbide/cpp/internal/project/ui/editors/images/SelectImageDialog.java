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

import com.nokia.carbide.cpp.internal.project.ui.images.providers.CurrentlyUsedImageContentProvider;
import com.nokia.carbide.cpp.internal.project.ui.images.providers.ThumbnailImageLabelProvider;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.sdt.utils.ui.ThumbnailGridViewer;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

import java.text.MessageFormat;

/**
 * This is the dialog that allows the user to select
 * a single image or mask file either from the
 * project or the filesystem.
 *
 */
public class SelectImageDialog extends TrayDialog {
	final int THUMB_WIDTH = 64;
	final int THUMB_HEIGHT = 64;
	protected MultiImageEditorContext editorContext;
	protected ThumbnailGridViewer thumbnailViewer;
	protected IPath selectedImage;
	private String usageLabel;
	private ThumbnailImageLabelProvider thumbnailImageLabelProvider;

	/**
	 * @param parentShell
	 * @param editorContext 
	 */
	protected SelectImageDialog(Shell parentShell, 
			MultiImageEditorContext editorContext,
			String usageLabel) {
		super(parentShell);
		this.usageLabel = usageLabel;
		setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MIN | SWT.MAX);
		this.editorContext = editorContext;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#getInitialSize()
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(640, 500);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		
		CLabel label = new CLabel(composite, SWT.NONE);
		label.setText(MessageFormat.format(Messages.getString("SelectImageDialog.SelectImageHelp"), //$NON-NLS-1$
				new Object[] { usageLabel }));
		label.setLayoutData(new GridData(SWT.TOP, SWT.LEFT, false, false));
		
		createThumbnailViewer(composite);

		configureThumbnailViewer();

		thumbnailViewer.getComposite().setFocus();
		if (selectedImage != null)
			thumbnailViewer.setSelection(new StructuredSelection(selectedImage));
		
		return composite;
	}
	
	/**
	 * Create the thumbnail viewer in which users select what files to add.
	 */
	private void createThumbnailViewer(Composite parent) {
		
		thumbnailViewer = new ThumbnailGridViewer(parent, SWT.FLAT);
		
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		thumbnailViewer.getViewer().setLayoutData(gridData);

		thumbnailViewer.addPostSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				selectedImage = (IPath) ((IStructuredSelection)event.getSelection()).getFirstElement();
			}
			
		});

	}
	
	protected void configureThumbnailViewer() {
				
		thumbnailViewer.setContentProvider(new CurrentlyUsedImageContentProvider(
				editorContext.getMultiImageSource()));
		thumbnailImageLabelProvider = editorContext.getThumbnailImageLabelProvider();
		thumbnailViewer.setLabelProvider(thumbnailImageLabelProvider);
		
		thumbnailViewer.setInput(editorContext.getMultiImageSource());

	}
	
	public IPath getImagePath() {
		return selectedImage;
	}

	/**
	 * @param path
	 */
	public void setSelectedImage(IPath path) {
		this.selectedImage = path;
		if (thumbnailViewer != null)
			thumbnailViewer.setSelection(new StructuredSelection(path));
	}
}
