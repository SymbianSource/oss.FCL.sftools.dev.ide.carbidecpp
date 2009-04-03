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
import com.nokia.carbide.cpp.ui.images.IFileImageModel;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.sdt.utils.ui.ThumbnailWithDescriptionComposite;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.text.MessageFormat;
import java.util.*;
import java.util.List;

/**
 * This is the dialog that allows the user to add images from the
 * project or the filesystem to a multi-image file.
 *
 */
public abstract class AddImagesDialog extends SelectOrAddImagesDialogBase {
	protected MultiImageEditorContextBase editorContext;
	TableViewer imageMaskPairTableViewer;
	private List<IImageSourceReference> newImageSources;

	/**
	 * @param parentShell
	 * @param editorContext 
	 */
	protected AddImagesDialog(Shell parentShell, MultiImageEditorContextBase editorContext) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MIN | SWT.MAX);
		this.editorContext = editorContext;
		this.newImageSources = new ArrayList<IImageSourceReference>();
	}
	 
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.ui.internal.images.AddImagesDialogBase#getDialogSettings()
	 */
	@Override
	protected IDialogSettings getDialogSettings() {
		return editorContext.getDialogSettings();
	}
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.editors.images.AddImagesToProjectDialogBase#createDialogContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createDialogContents(Composite composite) {
		createDialogTopHalf(composite);
		createDialogBottomHalf(composite);
	}

	/** Create the bottom half of the dialog,. */
	protected abstract void createDialogTopHalf(Composite parent);

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.editors.images.AddImagesToProjectDialogBase#createDialogBottomHalf(org.eclipse.swt.widgets.Composite)
	 */
	protected void createDialogBottomHalf(Composite parent) {
		SashForm sash = new SashForm(parent, SWT.VERTICAL | SWT.SMOOTH | SWT.BORDER);
		sash.setLayout(new GridLayout());
		sash.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		createThumbnailViewer(sash);
		
		Composite tableInfo = new Composite(sash, SWT.NONE);
		tableInfo.setLayout(new GridLayout());
		tableInfo.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		
		CLabel label2 = new CLabel(tableInfo, SWT.WRAP);
		label2.setText(Messages.getString("AddImagesDialog.DetectedImagePairsLabel")); //$NON-NLS-1$
		label2.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, false, false));
	
		createTableViewer(tableInfo);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.editors.images.AddImagesToProjectDialogBase#configureThumbnailViewer(com.nokia.sdt.utils.ui.ThumbnailWithDescriptionComposite)
	 */
	@Override
	protected void configureThumbnailViewer(
			ThumbnailWithDescriptionComposite thumbAndDesc) {
		thumbAndDesc.getDescriptionText().setText(
				MessageFormat.format(
						Messages.getString("AddImagesDialog.HelpDescription"), //$NON-NLS-1$
						new Object[] { editorContext.getTargetFileName() }));

	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.editors.images.AddImagesDialogBase#handleThumbnailViewerSelectionChanged(org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	protected void handleThumbnailViewerSelectionChanged(
			IStructuredSelection selection) {
		super.handleThumbnailViewerSelectionChanged(selection);
		BusyIndicator.showWhile(getShell().getDisplay(), new Runnable() {

			public void run() {
				rePairImages();
				if (imageMaskPairTableViewer != null && !imageMaskPairTableViewer.getControl().isDisposed())
					imageMaskPairTableViewer.refresh();
			}
			
		});
		
	}
	
	/**
	 * Create the table viewer in which the pairings of images + masks
	 * are shown.
	 * @param parent
	 */
	protected void createTableViewer(Composite parent) {
		imageMaskPairTableViewer = new TableViewer(parent, SWT.READ_ONLY | SWT.FLAT | SWT.FULL_SELECTION);
		final Table table = imageMaskPairTableViewer.getTable();
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.minimumHeight = 75;
		table.setLayoutData(gridData);
		//table.setEnabled(false); // yuck
		imageMaskPairTableViewer.addPostSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				// need to ignore this...
			}
			
		});
		
		TableColumn column = new TableColumn(table, SWT.LEFT);
		column.setResizable(true);
		column.setText(Messages.getString("AddImagesDialog.ImageColumnHeader")); //$NON-NLS-1$
		column = new TableColumn(table, SWT.LEFT);
		column.setResizable(true);
		column.setText(Messages.getString("AddImagesDialog.MaskColumnHeader")); //$NON-NLS-1$
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		imageMaskPairTableViewer.setContentProvider(new IStructuredContentProvider() {
	
			public Object[] getElements(Object inputElement) {
				return newImageSources.toArray();
			}
	
			public void dispose() {
			}
	
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				viewer.refresh();
			}
			
		});
		imageMaskPairTableViewer.setLabelProvider(new ITableLabelProvider() {
	
			public Image getColumnImage(Object element, int columnIndex) {
				return null;
			}
	
			public String getColumnText(Object element, int columnIndex) {
				IImageSourceReference source = (IImageSourceReference) element;
				if (columnIndex == 0)
					return source.getPath().lastSegment();
				else if (source instanceof IBitmapSourceReference && ((IBitmapSourceReference) source).getMaskPath() != null) {
					return ((IBitmapSourceReference) source).getMaskPath().lastSegment();
				}
				else
					return null;
			}
	
			public void dispose() {
				
			}
	
			public boolean isLabelProperty(Object element, String property) {
				return true;
			}
	
			public void addListener(ILabelProviderListener listener) {
			}
	
			public void removeListener(ILabelProviderListener listener) {
			}
			
		});
		imageMaskPairTableViewer.setInput(new Object());
	
		TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);
	
		tableLayout.addColumnData(new ColumnWeightData(100));
		tableLayout.addColumnData(new ColumnWeightData(100));
	
	}

	/**
	 * Pair image and mask files automatically.
	 */
	void rePairImages() {
		Set<Object> remainingImages = new HashSet<Object>(selectedImages);
		newImageSources.clear();
		for (Object item : selectedImages) {
			IPath path;
			if (!remainingImages.contains(item))
				continue;
			if (item instanceof IPath)
				path = (IPath) item;
			else if (item instanceof IFileImageModel) 
				path = ((IFileImageModel) item).getSourceLocation();
			else
				continue;
			if (isMaskFile(path))
				continue;
			if (isBitmapFile(path)) {
				Object maskItem = findMatchingFile(remainingImages, path);
				IPath maskPath;
				if (maskItem instanceof IPath)
					maskPath = (IPath) maskItem;
				else if (maskItem instanceof IFileImageModel) 
					maskPath = ((IFileImageModel) maskItem).getSourceLocation();
				else
					continue;
				// maskPath may be null
				IBitmapSourceReference bmSource = editorContext.createBitmapSourceReference();
				bmSource.setPath(path);
				bmSource.setMaskPath(maskPath);
				newImageSources.add(bmSource);
				remainingImages.remove(item);
				if (maskPath != null)
					remainingImages.remove(maskItem);
			} else if (isSVGFile(path)) {
				ISVGSourceReference svgSource = editorContext.createSVGSourceReference();
				svgSource.setPath(path);
				newImageSources.add(svgSource);
				remainingImages.remove(item);
			}
		}
		
		// add remaining entries 
		for (Object item : remainingImages) {
			IPath path;
			if (item instanceof IPath)
				path = (IPath) item;
			else if (item instanceof IFileImageModel) 
				path = ((IFileImageModel) item).getSourceLocation();
			else
				continue;
			if (isBitmapFile(path)) {
				IBitmapSourceReference bmSource = editorContext.createBitmapSourceReference();
				bmSource.setPath(path);
				newImageSources.add(bmSource);
			} else if (isSVGFile(path)) {
				ISVGSourceReference svgSource = editorContext.createSVGSourceReference();
				svgSource.setPath(path);
				newImageSources.add(svgSource);
			}
		}
	}
	
	/**
	 * Find a matching file 
	 * @param pattern
	 * @return
	 */
	private Object findMatchingFile(Collection<Object> images, IPath fileName) {
		String baseName = fileName.removeFileExtension().lastSegment();
		for (Object item : images) {
			IPath path;
			if (item instanceof IPath)
				path = (IPath) item;
			else if (item instanceof IFileImageModel)
				path = ((IFileImageModel) item).getSourceLocation();
			else
				continue;
			String lowerFile = path.lastSegment().toLowerCase();
			//_mask.bmp|_mask_soft.bmp|M.bmp
			if (lowerFile.startsWith(baseName)) {
				if (lowerFile.equals(baseName + "_mask.bmp")
						|| lowerFile.equals(baseName + "_mask_soft.bmp")
						|| lowerFile.equals(baseName + "m.bmp")) {
					return item;
				}
			}
		}
		return null;
	}

	/**
	 * @param path
	 * @return
	 */
	private boolean isSVGFile(IPath path) {
		return FileUtils.getSafeFileExtension(path).toLowerCase().equals("svg") //$NON-NLS-1$
		&& editorContext.isValidPath(path);
	}

	/**
	 * @param path
	 * @return
	 */
	private boolean isBitmapFile(IPath path) {
		return FileUtils.getSafeFileExtension(path).toLowerCase().equals("bmp") //$NON-NLS-1$
		&& editorContext.isValidPath(path);

	}
	/**
	 * @param path
	 * @return
	 */
	private boolean isMaskFile(IPath path) {
		return path.lastSegment().toLowerCase().matches(".*(_mask|_mask_soft)\\.bmp") //$NON-NLS-1$
		|| path.lastSegment().matches(".*M\\.bmp"); // another rare style //$NON-NLS-1$
	}

	public List<IImageSourceReference> getImageSourceReferences() {
		return newImageSources;
	}
}
