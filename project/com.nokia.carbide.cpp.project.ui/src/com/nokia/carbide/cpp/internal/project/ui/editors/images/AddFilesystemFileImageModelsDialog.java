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

import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.cpp.internal.api.utils.ui.*;
import com.nokia.sdt.utils.ui.ThumbnailGridViewer;
import com.nokia.sdt.utils.ui.ThumbnailWithDescriptionComposite;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * This dialog prompts the user to select one of more images from somewhere
 * in the filesystem and adds them to the project.
 *
 */
public class AddFilesystemFileImageModelsDialog extends SelectProjectFileImageModelsDialog {
	protected DirectorySelectorWithHistory directories;

	public AddFilesystemFileImageModelsDialog(Shell shell, IProject project,
			String helpText) {
		super(shell, project, helpText);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.ui.images.AddImagesDialogBase#createDialogContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createDialogContents(Composite composite) {
		Composite group = new Composite(composite, SWT.NONE);
		group.setLayout(new GridLayout(1, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		directories = createDirectorySelectionComposite(group);
		directories.addListener(new DirectorySelectorWithHistory.DirectoryChangeListener() {

			public void setDirectory() {
				updateThumbnails();
			}
			
		});
		
		createThumbnailViewer(group);
		
		createCopyFilesToProjectComposite(group);
		
		updateThumbnails();

	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.ui.images.AddImagesDialogBase#configureThumbnailViewer(com.nokia.sdt.utils.ui.ThumbnailWithDescriptionComposite)
	 */
	@Override
	protected void configureThumbnailViewer(
			ThumbnailWithDescriptionComposite thumbAndDesc) {
		thumbAndDesc.getDescriptionText().setText(helpText);

		ThumbnailGridViewer thumbnailViewer = thumbAndDesc.getThumbnailViewer();
		thumbnailViewer.setContentProvider(new AvailableFileImageModelContentProvider(
				IMAGE_FILE_EXTENSION_PATTERN, false));
	}

	/**
	 * 
	 */
	protected void updateThumbnails() {
		resetThumbnailLabelProvider();
		thumbnailViewer.setInput(directories.getCurrentDirectory());
	}
}
