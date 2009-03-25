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

import com.nokia.carbide.cpp.epoc.engine.image.*;
import com.nokia.carbide.cpp.ui.images.PreviewComposite;
import com.nokia.cpp.internal.api.utils.core.Pair;

import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class SelectedImagePropertiesComposite extends Composite {

	//private ImageFileControls imageControls;
	//private ImageFileControls maskControls;
	private SelectedImageFormatControls formatControls;
	private MultiImageListPage page;
	private PreviewComposite previewComposite;
	private MultiImageEditorContextBase editorContext;
	
	/**
	 * @param parent
	 * @param style
	 */
	public SelectedImagePropertiesComposite(MultiImageListPage page, 
			Composite parent, int style) {
		super(parent, style);
		
		this.editorContext = page.getEditorContext();
		this.page = page;

		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 1;
		setLayout(gridLayout_1);

		Group group = new Group(this, SWT.SHADOW_NONE);
		group.setText(Messages.getString("SelectedImagePropertiesComposite.SelectedImagesLabel")); //$NON-NLS-1$
		GridLayout groupLayout = new GridLayout(2, false);
		groupLayout.horizontalSpacing = 15;

		//Composite group = new Composite(this, SWT.NONE);
		group.setLayout(groupLayout);
		
		//Composite fileGroup = new Composite(group, SWT.NONE);
		//fileGroup.setLayout(new GridLayout(3, false));
		//imageControls = new ImageFileControls(this, fileGroup, SWT.NONE, false);
		//maskControls = new ImageFileControls(this, fileGroup, SWT.NONE, true);
		//fileGroup.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1));

		formatControls = new SelectedImageFormatControls(this, group, SWT.NONE);
		formatControls.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false, 1, 1));

		previewComposite = new PreviewComposite(group, SWT.NONE);
		previewComposite.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true, 2, 1));
		
		group.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true, 1, 1));
	}


	public void updateColorDepths(final int depth) {
		SelectedImageSourcesOperation operation = new SelectedImageSourcesOperation(
				Messages.getString("SelectedImagePropertiesComposite.ChangeColorDepthsCommand"), //$NON-NLS-1$
				page) {

			@Override
			boolean operationAffectsImage(IImageSource imageSource) {
				saveState(imageSource, imageSource.getDepth());
				int validDepth = depth;
				if (!imageSource.isValidDepth(depth)) {
					validDepth = 8;
				}
				return validDepth > 0 && imageSource.getDepth() != validDepth;
			}

			@Override
			void redoImageOperation(IImageSource imageSource) {
				int validDepth = depth;
				if (!imageSource.isValidDepth(depth)) {
					validDepth = 8;
				}
				imageSource.setDepth(validDepth);
			}

			@Override
			void undoImageOperation(IImageSource imageSource) {
				imageSource.setDepth((Integer)getState(imageSource));
			}
			
			@Override
			void changesMade() {
				refresh();
				page.refreshSelected();
			}
			
		};
		
		editorContext.pushAndExecute(operation);
	}
	
	public void updateMaskDepths(final int depth) {

		SelectedImageSourcesOperation operation = new SelectedImageSourcesOperation(
				Messages.getString("SelectedImagePropertiesComposite.ChangeMaskDepthsCommand"), //$NON-NLS-1$
				page) {

			@Override
			boolean operationAffectsImage(IImageSource imageSource) {
				// need to ensure depth != 0 === maskPath != null
				if (depth < 0)
					return false;
				
				IPath maskPath = null;
				if (imageSource instanceof IBitmapSource) {
					maskPath = ((IBitmapSource) imageSource).getMaskPath();
				}
		
				saveState(imageSource, new Pair<Integer, IPath>(imageSource.getMaskDepth(), maskPath));
				return imageSource.getMaskDepth() != depth
					|| (depth != 0 && maskPath == null)
					|| (depth == 0 && maskPath != null);
			}

			@Override
			void redoImageOperation(IImageSource imageSource) {
				if (imageSource instanceof ISVGSource) { 
					imageSource.setMaskDepth(depth);
					return;
				}
				
				// ensure mask depth matches existence of mask path
				IBitmapSource bitmapSource = ((IBitmapSource)imageSource);
				if (depth == 0) {
					if (bitmapSource.getMaskPath() != null) {
						bitmapSource.setMaskPath(null);
					}
					imageSource.setMaskDepth(depth);
				} else {
					if (bitmapSource.getMaskPath() == null) {
						// can't make up a mask file, and don't want to duplicate bmp entry
						return;
					}
					imageSource.setMaskDepth(depth);
				}
			}

			@Override
			void undoImageOperation(IImageSource imageSource) {
				Pair<Integer, IPath> oldState = (Pair<Integer, IPath>) getState(imageSource);
				imageSource.setMaskDepth(oldState.first);
				if (imageSource instanceof IBitmapSource) 
					((IBitmapSource) imageSource).setMaskPath(oldState.second);
			}

			@Override
			void changesMade() {
				refresh();
				page.refreshSelected();
			}

		};
		
		editorContext.pushAndExecute(operation);
	}
	
	public void updateColorFlags(final boolean color) {

		SelectedImageSourcesOperation operation = new SelectedImageSourcesOperation(
				Messages.getString("SelectedImagePropertiesComposite.ChangeColorFlagCommand"), //$NON-NLS-1$
				page) {

			@Override
			boolean operationAffectsImage(IImageSource imageSource) {
				saveState(imageSource, new Pair<Integer, Boolean>(imageSource.getDepth(), imageSource.isColor()));
				return imageSource.isColor() != color;
			}

			@Override
			void redoImageOperation(IImageSource imageSource) {
				imageSource.setColor(color);
				
				// ensure depths are valid
				if (!imageSource.isValidDepth(imageSource.getDepth())) {
					imageSource.setDepth(8);
				}
			}

			@Override
			void undoImageOperation(IImageSource imageSource) {
				Pair<Integer, Boolean> info = (Pair<Integer, Boolean>) getState(imageSource);
				imageSource.setColor(info.second);
				imageSource.setDepth(info.first);
			}

			@Override
			void changesMade() {
				refresh();
				page.refreshSelected();
			}

		};
		
		editorContext.pushAndExecute(operation);
	}

	/**
	 * Create a path from the given filename
	 * @param filename
	 * @return
	 */
	public IPath createPath(String filename) {
		return page.createPath(filename);
	}

	/**
	 * 
	 */
	public void refresh() {
		if (isDisposed())
			return;
		//imageControls.refresh();
		//maskControls.refresh();
		formatControls.refresh();
		IImageSource[] imageSources = getImageSources();
		if (imageSources.length == 1) {
			previewComposite.refresh(
					editorContext.wrapImageSource(imageSources[0]));
		} else {
			previewComposite.refresh(null);
		}
	}

	public MultiImageEditorContext getEditorContext() {
		return page.getEditorContext();
	}

	public IImageSource[] getImageSources() {
		return page.getImageSources();
	}

	protected MultiImageListPage getPage() {
		return page;
	}


	
}
