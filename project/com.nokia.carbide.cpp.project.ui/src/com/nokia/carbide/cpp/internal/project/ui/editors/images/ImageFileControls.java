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

import com.nokia.carbide.cpp.epoc.engine.image.IBitmapSource;
import com.nokia.carbide.cpp.epoc.engine.image.IImageSource;
import com.nokia.cpp.internal.api.utils.core.ObjectUtils;
import com.nokia.cpp.internal.api.utils.core.Pair;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

/**
 * The file selector, enumerator, and "Choose..." button block.
 * These are expected to be added to a composite with a grid layout.
 *
 */
public class ImageFileControls {

	final static String MIXED = Messages.getString("ImageFileControls.MultiValueComboEntry"); //$NON-NLS-1$
	private Text imageSourceText;
	private Button selectImageSourceButton;
	private Text imageSourceEnumLabel;
	private SelectedImagePropertiesComposite selected;
	private boolean isMask;
	private MultiImageEditorContext editorContext;

	/**
	 * @param parent
	 * @param style
	 * @param label 
	 */
	public ImageFileControls(final SelectedImagePropertiesComposite selected, 
			Composite parent, int style, final boolean isMask) {
		this.editorContext = selected.getEditorContext();
		
		this.selected = selected;
		this.isMask = isMask;
		
		//setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false, 1, 1));
		//setLayout(new GridLayout(3, false));

		final Label label = new Label(parent, SWT.NONE);
		label.setLayoutData(new GridData(GridData.BEGINNING, GridData.FILL, false, false, 1, 1));
		label.setText(isMask ? Messages.getString("ImageFileControls.MaskLabel") : Messages.getString("ImageFileControls.ImageLabel")); //$NON-NLS-1$ //$NON-NLS-2$
		//parent.setText(isMask ? "Mask" : "Image");

		imageSourceText = new Text(parent, SWT.BORDER);
		GridData gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gridData.widthHint = 200;
		gridData.minimumWidth = 100;
		imageSourceText.setLayoutData(gridData);
		imageSourceText.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent e) {
			}

			public void focusLost(FocusEvent e) {
				fireFilenameChanged();
			}
			
		});

		selectImageSourceButton = new Button(parent, SWT.NONE);
		selectImageSourceButton.setText(Messages.getString("ImageFileControls.EllipsisButton")); //$NON-NLS-1$
		selectImageSourceButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectImageFile();
			}
		});
		
		////

		imageSourceEnumLabel = new Text(parent, SWT.RIGHT | SWT.READ_ONLY);
		imageSourceEnumLabel.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false, 2, 1));
		imageSourceEnumLabel.setText(""); //$NON-NLS-1$
		//imageSourceEnumLabel.setAlignment(SWT.RIGHT);
		new Label(parent, SWT.NONE);
	}
	
	/**
	 * Show dialog allowing user to select alternate image/mask file.
	 * This shows the set of known project images AND existing images
	 * in the multi-image source.
	 */
	protected void selectImageFile() {
		SelectImageDialog dialog = new SelectImageDialog(selected.getShell(),
				editorContext,
				isMask ? Messages.getString("ImageFileControls.MaskWordLabel") : Messages.getString("ImageFileControls.ImageWordLabel")); //$NON-NLS-1$ //$NON-NLS-2$
		dialog.setSelectedImage(new Path(imageSourceText.getText()));
		if (dialog.open() == IDialogConstants.OK_ID) {
			IPath path = dialog.getImagePath();
			imageSourceText.setText(path != null ? path.toString() : ""); //$NON-NLS-1$
			fireFilenameChanged();
		}
	}

	public void updateImage(final IPath path) {

		SelectedImageSourcesOperation operation = new SelectedImageSourcesOperation(
				Messages.getString("ImageFileControls.ChangeImagePathCommand"), //$NON-NLS-1$
				selected.getPage()) {

			@Override
			boolean operationAffectsImage(IImageSource imageSource) {
				saveState(imageSource, imageSource.getPath());
				return !ObjectUtils.equals(imageSource.getPath(), path);
			}

			@Override
			void redoImageOperation(IImageSource imageSource) {
				imageSource.setPath(path);

			}

			@Override
			void undoImageOperation(IImageSource imageSource) {
				imageSource.setPath((IPath)getState(imageSource));
			}

			@Override
			void changesMade() {
				refresh();
				selected.getPage().refreshSelected();
			}

		};
		
		editorContext.pushAndExecute(operation);
	}
	
	public void updateMask(final IPath path) {

		SelectedImageSourcesOperation operation = new SelectedImageSourcesOperation(
				Messages.getString("ImageFileControls.ChangeMaskPathCommand"), //$NON-NLS-1$
				selected.getPage()) {

			@Override
			boolean operationAffectsImage(IImageSource imageSource) {
				if (!(imageSource instanceof IBitmapSource)) {
					// exit early so we aren't called again with SVGs
					return false;
				}
				IBitmapSource bmSource = (IBitmapSource) imageSource;
				
				// need to ensure depth != 0 === maskPath != null
				IPath maskPath = bmSource.getMaskPath();
		
				saveState(imageSource, new Pair<IPath, Integer>(
						maskPath, bmSource.getMaskDepth()));
				return !ObjectUtils.equals(maskPath, path)
					|| (bmSource.getMaskDepth() != 0 && maskPath == null)
					|| (bmSource.getMaskDepth() == 0 && maskPath != null);
			}

			@Override
			void redoImageOperation(IImageSource imageSource) {
				IBitmapSource bitmapSource = (IBitmapSource) imageSource;
				bitmapSource.setMaskPath(path);
				
				// ensure mask path existence matches mask depth
				if (path != null) {
					if (bitmapSource.getMaskDepth() == 0) {
						// TODO: centralize logic like this
						bitmapSource.setMaskDepth(
								path.lastSegment().toLowerCase().indexOf("_soft") > 0 ? 8 : 1); //$NON-NLS-1$
					}
				} else {
					if (bitmapSource.getMaskDepth() != 0) {
						bitmapSource.setMaskDepth(0);
					}
				}
			}

			@Override
			void undoImageOperation(IImageSource imageSource) {
				IBitmapSource bitmapSource = (IBitmapSource) imageSource;
				Pair<IPath, Integer> oldState = (Pair<IPath, Integer>) getState(imageSource);
				bitmapSource.setMaskPath(oldState.first);
				bitmapSource.setMaskDepth(oldState.second);
			}

			@Override
			void changesMade() {
				refresh();
				selected.getPage().refreshSelected();
			}

		};
		
		editorContext.pushAndExecute(operation);
	}

	/**
	 * 
	 */
	protected void fireFilenameChanged() {
		String filename = imageSourceText.getText();
		IPath path = filename.length() > 0 ? selected.createPath(filename) : null;
		if (selected.getImageSources().length > 1) {
			// can't set filename for multiple items at once,
			// can only blank them out
			if (path != null)
				return;
		}
		if (isMask)
			updateMask(path);
		else
			updateImage(path);
	}

	public void refresh() {
		boolean first = true;
		String filename = ""; //$NON-NLS-1$
		boolean editable = false;
		String enumerator = ""; //$NON-NLS-1$
		for (IImageSource imageSource : selected.getImageSources()) {
			if (first) {
				IPath path;
				if (isMask) {
					path = (imageSource instanceof IBitmapSource) ?
							((IBitmapSource) imageSource).getMaskPath() : null;
					enumerator = selected.getEditorContext().getMultiImageSource().
						getGeneratedMaskEnumerator(imageSource);
				} else {
					path = imageSource.getPath();
					enumerator = selected.getEditorContext().getMultiImageSource().
						getGeneratedImageEnumerator(imageSource);
				}
				if (path != null) {
					filename = path.toString();
				}
				first = false;
				editable = true;
			} else {
				// can't have more than one
				filename = MIXED;
				enumerator = MIXED;
				editable = false;
			}
		}
		
		selectImageSourceButton.setEnabled(editable);
		imageSourceText.setEditable(editable);
		imageSourceText.setText(filename);
		if (enumerator == null)
			enumerator = ""; //$NON-NLS-1$
		imageSourceEnumLabel.setText(enumerator);
	}

	
}
