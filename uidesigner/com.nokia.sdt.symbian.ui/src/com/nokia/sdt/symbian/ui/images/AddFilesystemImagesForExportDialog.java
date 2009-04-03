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

package com.nokia.sdt.symbian.ui.images;

import com.nokia.carbide.cpp.ui.images.IFileImageModel;
import com.nokia.cpp.internal.api.utils.ui.DirectorySelectorWithHistory;
import com.nokia.sdt.utils.ui.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Map;

/**
 * This dialog allows the user to select a set of images from a directory
 * on the filesystem and assign them to target locations for the bld.inf PRJ_EXPORTS
 * and the PKG file.
 * 
 *
 */
public class AddFilesystemImagesForExportDialog extends AddProjectImagesForExportDialog {
	
	private DirectorySelectorWithHistory directorySelector;

	/**
	 * @param shell
	 */
	public AddFilesystemImagesForExportDialog(Shell shell, IProject project) {
		super(shell, project, 
				Messages.getString("AddFilesystemImagesForExportDialog.AddFilesystemImagesHelpText")); //$NON-NLS-1$
	}

	
	@Override
	protected void establishNewExports() {
		// for the filesystem model, we need to find files that were
		// copied in
		newExports = new ArrayList<NewImageExport>();
		for (Map.Entry<IFileImageModel, IPath> entry : exportsTable.getMappings().entrySet()) {
			// copy may have failed
			CopiedImageModel copiedImage = findCopiedImage(entry.getKey());
			if (copiedImage != null) {
				NewImageExport export = new NewImageExport(copiedImage.getDestinationPath(), 
						entry.getValue());
				newExports.add(export);
			}
		}
	}

	private CopiedImageModel findCopiedImage(IFileImageModel fileModel) {
		for (CopiedImageModel model : copiedImages) {
			if (model.getSourceLocation().equals(fileModel.getSourceLocation()))
				return model;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.project.ui.editors.images.SelectOrAddImagesDialogBase#createDialogContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createDialogContents(Composite composite) {
		super.createDialogContents(composite);
		
		getShell().setText(Messages.getString("AddFilesystemImagesForExportDialog.AddFilesystemImagesTitle")); //$NON-NLS-1$
		createCopyFilesToProjectComposite(topHalf);

	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.ui.images.AddProjectImagesForExportDialog#initializeTopHalf()
	 */
	@Override
	protected void initializeTopHalf() {
		directorySelector = createDirectorySelectionComposite(topHalf);
		directorySelector.addListener(new DirectorySelectorWithHistory.DirectoryChangeListener() {

			public void setDirectory() {
				thumbnailViewer.setInput(directorySelector.getCurrentDirectory());
			}
			
		});
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.ui.images.AddImagesDialogBase#configureThumbnailViewer(com.nokia.sdt.utils.ui.ThumbnailWithDescriptionComposite)
	 */
	@Override
	protected void configureThumbnailViewer(
			ThumbnailWithDescriptionComposite thumbAndDesc) {
		thumbAndDesc.getDescriptionText().setText(helpText);

		ThumbnailGridViewer thumbnailViewer = thumbAndDesc.getThumbnailViewer();
		
		// don't recurse in filesystem!
		thumbnailViewer.setContentProvider(new AvailableFileImageModelContentProvider(
				IMAGE_FILE_EXTENSION_PATTERN, false));
		
	}

	/**
	 * 
	 */
	protected void updateThumbnails() {
		resetThumbnailLabelProvider();
		thumbnailViewer.setInput(directorySelector.getCurrentDirectory());
	}
	
	private boolean checkDirectory() {
		// check if the directory exists
		IFolder folder = getProject().getFolder(projectDestination);
		if (folder == null || !folder.exists()) {
			MessageDialog.openError(getShell(), Messages.getString("AddFilesystemImagesForExportDialog.DirectoryErrorTitle"),  //$NON-NLS-1$
					MessageFormat.format(Messages.getString("AddFilesystemImagesForExportDialog.DirectoryError"), //$NON-NLS-1$
							new Object[] { projectDestination.toOSString() }));
			return false;
		}
		
		return true;
	}

	@Override
	protected void okPressed() {
		if (checkDirectory())
			super.okPressed();
	}

}
