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

import com.nokia.carbide.cpp.epoc.engine.image.IImageSource;
import com.nokia.carbide.cpp.epoc.engine.image.ImageFormat;
import com.nokia.carbide.cpp.internal.project.ui.images.IImageSourceModel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

/**
 * The color/grayscale, color/mask depth, and "default" button block
 *
 */
public class SelectedImageFormatControls extends ImageFormatControlsBase {

	private SelectedImagePropertiesComposite selected;
	private Button setFromImageButton;

	/**
	 * @param selected
	 * @param style
	 * @param label 
	 */
	public SelectedImageFormatControls(final SelectedImagePropertiesComposite selected, 
			Composite group, int style) {
		super(group, style, true, true);
		
		new Label(this, SWT.NONE);
		setFromImageButton = new Button(this, SWT.PUSH);
		setFromImageButton.setLayoutData(new GridData(GridData.CENTER, GridData.FILL, false, false, 1, 1));
		setFromImageButton.setText(Messages.getString("ImageFormatControls.SetFromImageButton")); //$NON-NLS-1$
		setFromImageButton.setToolTipText(Messages.getString("ImageFormatControls.SetFromImageTooltip")); //$NON-NLS-1$

		this.selected = selected;

		colorDepthCombo.getCCombo().addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				// ignore attempt to set depth to "---"
				if (colorDepthCombo.getDepth() == -1)
					refresh();
				else
					selected.updateColorDepths(colorDepthCombo.getDepth());
			}
			
		});

		maskDepthCombo.getCCombo().addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				// ignore attempt to set depth to "---"
				if (maskDepthCombo.getDepth() == -1)
					refresh();
				else
					selected.updateMaskDepths(maskDepthCombo.getDepth());
			}
			
		});

		colorButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				selected.updateColorFlags(true);
				updateColorDepths(true, false);
			}
			
		});
		
		greyButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				selected.updateColorFlags(false);
				updateColorDepths(false, true);
			}
			
		});
		
		setFromImageButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent e) {
				setDefaultImageFormats();
			}
			
		});
	}

	public static MultiImageEditorOperation createSetDefaultImageFormatsOperation(
			final MultiImageListPage page, IImageSource[] images) {
		class Info {
			boolean color;
			int depth, maskDepth;
			public Info(boolean color, int depth, int maskDepth) {
				this.color = color;
				this.depth = depth;
				this.maskDepth = maskDepth;
			}
			
		};
		SelectedImageSourcesOperation operation = new SelectedImageSourcesOperation(
				Messages.getString("ImageFormatControls.SetFormatFromImageCommand"), //$NON-NLS-1$
				page, images) {

			@Override
			boolean operationAffectsImage(IImageSource imageSource) {
				saveState(imageSource, new Info(imageSource.isColor(), 
						imageSource.getDepth(), imageSource.getMaskDepth()));
				return true;
			}

			@Override
			void redoImageOperation(IImageSource imageSource) {
				IImageSourceModel model = getEditorContext().wrapImageSource(imageSource); 
				ImageFormat format = ImageFormatUtils.getDefaultImageFormat(model);

				if (imageSource.isColor() != format.isColor) {
					imageSource.setColor(format.isColor);
				}
				if (imageSource.getDepth() != format.depth) {
					imageSource.setDepth(format.depth);
				}
				if (imageSource.getMaskDepth() != format.maskDepth) {
					imageSource.setMaskDepth(format.maskDepth);
				}
			}

			@Override
			void undoImageOperation(IImageSource imageSource) {
				Info info = (Info) getState(imageSource);
				imageSource.setColor(info.color);
				imageSource.setDepth(info.depth);
				imageSource.setMaskDepth(info.maskDepth);
			}

			@Override
			void changesMade() {
				page.refresh();
				page.refreshSelected();
			}

		};
		return operation;
	}
	
	/**
	 * 
	 */
	protected void setDefaultImageFormats() {
		MultiImageEditorOperation operation = createSetDefaultImageFormatsOperation(selected.getPage(), selected.getImageSources());
		selected.getEditorContext().pushAndExecute(operation);
	}
	
	public void refresh() {
		boolean first = true;
		int depth = -1;
		int maskDepth = -1;
		boolean isColor = false;
		boolean isGrey = false;
		for (IImageSource imageSource : selected.getImageSources()) {
			if (first) {
				depth = imageSource.getDepth();
				maskDepth = imageSource.getMaskDepth();
				first = false;
				isColor = imageSource.isColor();
				isGrey = !isColor;
			} else {
				if (imageSource.getDepth() != depth) 
					depth = -1;
				if (imageSource.getMaskDepth() != maskDepth) 
					maskDepth = -1;
				if (imageSource.isColor() != isColor) {
					isColor = isGrey = false;
				}
			}
		}
		
		colorDepthCombo.getCCombo().setEnabled(!first);
		maskDepthCombo.getCCombo().setEnabled(!first);
		colorButton.setEnabled(!first);
		greyButton.setEnabled(!first);
		setFromImageButton.setEnabled(!first);
		
		colorDepthCombo.setDepth(depth);
		maskDepthCombo.setDepth(maskDepth);
		
		colorButton.setSelection(isColor);
		greyButton.setSelection(isGrey);
		
		updateColorDepths(isColor, isGrey);
	}
	

}
