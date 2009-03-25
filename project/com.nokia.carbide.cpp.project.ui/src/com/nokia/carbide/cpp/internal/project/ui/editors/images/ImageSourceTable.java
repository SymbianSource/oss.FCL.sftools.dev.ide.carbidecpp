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
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.carbide.cpp.internal.project.ui.images.providers.ImageTableContentProvider;
import com.nokia.carbide.cpp.internal.project.ui.images.providers.ImageTableLabelProvider;
import com.nokia.cpp.internal.api.utils.core.Pair;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import java.util.*;
import java.util.List;

public class ImageSourceTable extends Composite {
	private TableViewer imageSourceTableViewer;
	private MultiImageEditorContext editorContext;
	private MultiImageListPage page;
	private Button upButton;
	private Button downButton;
	private Button removeButton;
	private ImageTableLabelProvider imageTableLabelProvider;
	private Button combineButton;
	private Button splitButton;
	private Clipboard clipboard;
	private Button swapButton;
	private ILabelProviderListener labelProviderListener;

	/**
	 * @param parent
	 * @param style
	 */
	public ImageSourceTable(MultiImageListPage page, Composite parent, int style) {
		super(parent, style);
		this.editorContext = page.getEditorContext();
		this.page = page;

		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		this.setLayout(gridLayout);

		imageSourceTableViewer = new TableViewer(this, 
				SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION | SWT.V_SCROLL);
		
		final Table table = imageSourceTableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		TableColumn thumbColumn = new TableColumn(table, SWT.CENTER);
		thumbColumn.setText(Messages.getString("ImageSourceTable.ThumbColumnHeader")); //$NON-NLS-1$
		thumbColumn.setToolTipText(Messages.getString("ImageSourceTable.ThumbColumnHeaderTooltip")); //$NON-NLS-1$
		
		TableColumn formatColumn = new TableColumn(table, SWT.LEFT);
		formatColumn.setText(Messages.getString("ImageSourceTable.FormatColumnHeader")); //$NON-NLS-1$
		formatColumn.setToolTipText(Messages.getString("ImageSourceTable.FormatColumnHeaderTooltip")); //$NON-NLS-1$

		TableColumn filesColumn = new TableColumn(table, SWT.LEFT);
		filesColumn.setText(Messages.getString("ImageSourceTable.FilesColumnHeader")); //$NON-NLS-1$
		filesColumn.setToolTipText(Messages.getString("ImageSourceTable.FilesColumnHeaderTooltip")); //$NON-NLS-1$

		TableColumn enumColumn = new TableColumn(table, SWT.LEFT);
		enumColumn.setText(Messages.getString("ImageSourceTable.EnumeratorsColumn")); //$NON-NLS-1$
		enumColumn.setToolTipText(Messages.getString("ImageSourceTable.EnumeratorsColumnHeaderTooltip")); //$NON-NLS-1$

		table.setHeaderVisible(true);

		imageSourceTableViewer.setContentProvider(new ImageTableContentProvider(editorContext.getMultiImageSource()));
		imageTableLabelProvider = new ImageTableLabelProvider(editorContext);
		imageSourceTableViewer.setLabelProvider(imageTableLabelProvider);
		imageSourceTableViewer.setInput(editorContext.getMultiImageSource());
		labelProviderListener = new ILabelProviderListener() {
		
			public void labelProviderChanged(LabelProviderChangedEvent event) {
				if (!imageSourceTableViewer.getTable().isDisposed())
					imageSourceTableViewer.update(event.getElements(), null);
			}
					
		};
		imageTableLabelProvider.addListener(labelProviderListener);
		clipboard = new Clipboard(getDisplay());

		addTableContextMenu(table);
		
		TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);

		tableLayout.addColumnData(new ColumnWeightData(20, ImageTableLabelProvider.THUMB_SIZE_X, false));
		tableLayout.addColumnData(new ColumnWeightData(20, 32, false));
		tableLayout.addColumnData(new ColumnWeightData(60, 128, true));
		tableLayout.addColumnData(new ColumnWeightData(60, 200, true));

		final Composite composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, false, false));
		composite.setLayout(new GridLayout());

		upButton = new Button(composite, SWT.NONE);
		upButton.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));
		upButton.setText(Messages.getString("ImageSourceTable.UpButton")); //$NON-NLS-1$
		upButton.setToolTipText(Messages.getString("ImageSourceTable.UpButtonTooltip")); //$NON-NLS-1$
		upButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				doMove(-1);
			}
			
		});

		downButton = new Button(composite, SWT.NONE);
		downButton.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));
		downButton.setText(Messages.getString("ImageSourceTable.DownButton")); //$NON-NLS-1$
		downButton.setToolTipText(Messages.getString("ImageSourceTable.DownButtonTooltip")); //$NON-NLS-1$
		downButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				doMove(1);
			}
			
		});

		final Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayout(new GridLayout());

		combineButton = new Button(composite, SWT.NONE);
		combineButton.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));
		combineButton.setText(Messages.getString("ImageSourceTable.PairImgMaskButton")); //$NON-NLS-1$
		combineButton.setToolTipText(Messages.getString("ImageSourceTable.PairImgMaskButtonTooltip")); //$NON-NLS-1$
		combineButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				doCombine();
			}
			
		});		

		splitButton = new Button(composite, SWT.NONE);
		splitButton.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));
		splitButton.setText(Messages.getString("ImageSourceTable.SplitImgMaskButton")); //$NON-NLS-1$
		splitButton.setToolTipText(Messages.getString("ImageSourceTable.SplitImgMaskButtonTooltip")); //$NON-NLS-1$
		splitButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				doSplit();
			}
			
		});		

		swapButton = new Button(composite, SWT.NONE);
		swapButton.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));
		swapButton.setText(Messages.getString("ImageSourceTable.SwapImgMaskButton")); //$NON-NLS-1$
		swapButton.setToolTipText(Messages.getString("ImageSourceTable.SwapImgMaskButtonTooltip")); //$NON-NLS-1$
		swapButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				doSwap();
			}
			
		});		

		final Composite composite_2 = new Composite(composite, SWT.NONE);
		composite_2.setLayout(new GridLayout());

		final Button addFromProjectButton = new Button(composite, SWT.NONE);
		addFromProjectButton.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));
		addFromProjectButton.setText(Messages.getString("ImageSourceTable.AddFromProjectButton")); //$NON-NLS-1$
		addFromProjectButton.setToolTipText(Messages.getString("ImageSourceTable.AddFromProjectButtonTooltip")); //$NON-NLS-1$
		addFromProjectButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				doAddFromProject();
			}
			
		});
		final Button addFromFilesystemButton = new Button(composite, SWT.NONE);
		addFromFilesystemButton.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));
		addFromFilesystemButton.setText(Messages.getString("ImageSourceTable.AddFromFilesystemButton")); //$NON-NLS-1$
		addFromFilesystemButton.setToolTipText(Messages.getString("ImageSourceTable.AddFromFilesystemButtonTooltip")); //$NON-NLS-1$
		addFromFilesystemButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				doAddFromFilesystem();
			}
			
		});
		
		removeButton = new Button(composite, SWT.NONE);
		removeButton.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));
		removeButton.setText(Messages.getString("ImageSourceTable.RemoveButton")); //$NON-NLS-1$
		removeButton.setToolTipText(Messages.getString("ImageSourceTable.RemoveButtonTooltip")); //$NON-NLS-1$
		removeButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				doRemove();
			}
			
		});

		table.addKeyListener(new KeyAdapter() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.KeyAdapter#keyPressed(org.eclipse.swt.events.KeyEvent)
			 */
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.character == SWT.DEL) {
					doRemove();
					e.doit = false;
				}
			}
		});

		
		refresh();
		refreshTable();
	}

	/**
	 * Add the context menu to the table that allows copying the
	 * enumerators.
	 * @param table
	 */
	private void addTableContextMenu(final Table table) {
		Menu menu = new Menu(table);
		final MenuItem item = new MenuItem(menu, SWT.NONE);
		item.setText(Messages.getString("ImageSourceTable.CopyEnumsMenuLabel")); //$NON-NLS-1$
		if (Platform.isRunning()) {
			item.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(
					ISharedImages.IMG_TOOL_COPY));
		}
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Point pt = Display.getDefault().getCursorLocation();
				Point point = table.toControl(pt);
				TableItem item = table.getItem(point);
				//System.out.println(pt.toString() + point.toString() + item);
				if (item != null) {
					doCopyEnumerators(item.getText(imageTableLabelProvider.getEnumeratorColumn()));
				}
			}
		});
		table.setMenu(menu);
	}

	/**
	 * @param enumInfo 
	 * 
	 */
	protected void doCopyEnumerators(String enumInfo) {
		try {
			clipboard.setContents(
					new Object[] { enumInfo },
					new Transfer[] { TextTransfer.getInstance()});
		} catch (SWTError e) {
			// if clipboard is busy...
			ProjectUIPlugin.log(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Widget#dispose()
	 */
	@Override
	public void dispose() {
		if (imageTableLabelProvider != null) {
			imageTableLabelProvider.removeListener(labelProviderListener);
			imageTableLabelProvider.dispose();
			imageTableLabelProvider = null;
		}
		super.dispose();
	}
	
	public void refresh() {
		if (isDisposed())
			return;
		int[] selectedIndices = page.getImageSourceIndices();
		if (selectedIndices.length > 0) {
			removeButton.setEnabled(true);
			upButton.setEnabled(selectedIndices[0] != 0);
			downButton.setEnabled(selectedIndices[selectedIndices.length - 1] != 
				editorContext.getMultiImageSource().getSources().size() - 1);
		} else {
			removeButton.setEnabled(false);
			upButton.setEnabled(false);
			downButton.setEnabled(false);
		}
		
		IImageSource[] imageSources = page.getImageSources();
		int loneImageCount = 0;
		for (IImageSource imageSource : imageSources) {
			if (imageSource instanceof IBitmapSource 
					&& ((IBitmapSource) imageSource).getMaskPath() == null) {
				loneImageCount++;
			}
		}
		if (loneImageCount >= 2) {
			combineButton.setEnabled(true);
		} else {
			combineButton.setEnabled(false);
		}
		
		boolean hasMaskedImages = false;
		for (IImageSource imageSource : imageSources) {
			if (imageSource instanceof IBitmapSource 
					&& ((IBitmapSource) imageSource).getMaskPath() != null) {
				hasMaskedImages = true;
				break;
			}
		}
		splitButton.setEnabled(hasMaskedImages);
		swapButton.setEnabled(hasMaskedImages);
	}

	/**
	 * @return
	 */
	public TableViewer getTableViewer() {
		return imageSourceTableViewer;
	}
	
	private void doCombine() {
		final List<Pair<Integer, IImageSource>> oldImageSources = new ArrayList<Pair<Integer,IImageSource>>();
		final List<Pair<Integer, IImageSource>> newImageSources = new ArrayList<Pair<Integer,IImageSource>>();

		int[] indices = page.getImageSourceIndices();
		int imageIdx = -1;
		IBitmapSource imageSource = null;
		for (int idx : indices) {
			IImageSource source = editorContext.getMultiImageSource().getSources().get(idx);
			if (!(source instanceof IBitmapSource))
				continue;
			IBitmapSource bmSource = (IBitmapSource) source;
			if (bmSource.getPath() != null && bmSource.getMaskPath() != null)
				continue;
			if (imageSource != null) {
				// got two
				IBitmapSource pairedSource = editorContext.getMultiImageSource().createBitmapSource();
				pairedSource.setColor(imageSource.isColor());
				pairedSource.setDepth(imageSource.getDepth());
				pairedSource.setPath(imageSource.getPath());
				pairedSource.setMaskPath(bmSource.getPath());
				pairedSource.setMaskDepth(bmSource.getDepth() > 1 ? 8 : 1);
				oldImageSources.add(new Pair<Integer, IImageSource>(imageIdx, imageSource));
				oldImageSources.add(new Pair<Integer, IImageSource>(idx, bmSource));
				newImageSources.add(new Pair<Integer, IImageSource>(imageIdx, pairedSource));
				imageSource = null;
			} else {
				// save for later
				imageIdx = idx;
				imageSource = bmSource;
			}
		}
		
		MultiImageListPageOperation operation = new MultiImageListPageOperation(
				Messages.getString("ImageSourceTable.PairImgMaskCommandLabel"), //$NON-NLS-1$
				page) {

			@Override
			protected boolean doesAnything() {
				return newImageSources.size() > 0;
			}

			@Override
			protected void redo() {
				for (ListIterator<Pair<Integer, IImageSource>> iter = 
					newImageSources.listIterator(newImageSources.size());
					iter.hasPrevious();) {
					Pair<Integer,IImageSource> imageSource = iter.previous();
					editorContext.getMultiImageSource().getSources().add(imageSource.first, imageSource.second);
					
				}
				for (ListIterator<Pair<Integer, IImageSource>> iter = 
					oldImageSources.listIterator(oldImageSources.size());
					iter.hasPrevious();) {
					Pair<Integer,IImageSource> imageSource = iter.previous();
					editorContext.getMultiImageSource().getSources().remove(imageSource.second);
				}
				refreshTable();
				imageSourceTableViewer.setSelection(new StructuredSelection(newImageSources));
				page.refreshSelection();
			}

			@Override
			protected void undo() {
				for (ListIterator<Pair<Integer, IImageSource>> iter = 
					newImageSources.listIterator(newImageSources.size());
					iter.hasPrevious();) {
					Pair<Integer,IImageSource> imageSource = iter.previous();
					editorContext.getMultiImageSource().getSources().remove(imageSource.second);
					
				}
				for (ListIterator<Pair<Integer, IImageSource>> iter = 
					oldImageSources.listIterator();
					iter.hasNext();) {
					Pair<Integer,IImageSource> imageSource = iter.next();
					editorContext.getMultiImageSource().getSources().add(imageSource.first, imageSource.second);
				}
				refreshTable();
				imageSourceTableViewer.setSelection(new StructuredSelection(
						oldImageSources));
				page.refreshSelection();
			}
	
		};
		
		editorContext.pushAndExecute(operation);
		
	}
	
	private void doSplit() {
		int[] imageSourceIndices = page.getImageSourceIndices();

		final List<Pair<Integer, IImageSource>> oldImageSources = 
			new ArrayList<Pair<Integer, IImageSource>>();

		final List<Pair<Integer, IImageSource>> newImageSources = 
			new ArrayList<Pair<Integer, IImageSource>>();
		
		for (int idx : imageSourceIndices) {
			IImageSource imageSource = editorContext.getMultiImageSource().
					getSources().get(idx);
			if (!(imageSource instanceof IBitmapSource))
				continue;
			IBitmapSource bmSource = (IBitmapSource) imageSource;
			if (bmSource.getPath() == null
					|| bmSource.getMaskPath() == null)
				continue;
			
			oldImageSources.add(new Pair<Integer, IImageSource>(idx, imageSource));
			
			IImageSource image = editorContext.getMultiImageSource().createBitmapSource();
			image.setColor(bmSource.isColor());
			image.setDepth(bmSource.getDepth());
			image.setPath(bmSource.getPath());
			
			IImageSource mask = editorContext.getMultiImageSource().createBitmapSource();
			mask.setColor(false);
			mask.setDepth(bmSource.getMaskDepth());
			mask.setPath(bmSource.getMaskPath());
			
			newImageSources.add(new Pair<Integer, IImageSource>(idx, image));
			newImageSources.add(new Pair<Integer, IImageSource>(idx, mask));
		}
		
		MultiImageListPageOperation operation = new MultiImageListPageOperation(
				Messages.getString("ImageSourceTable.SplitImgMaskCommandLabel"), //$NON-NLS-1$
				page) {

			@Override
			protected boolean doesAnything() {
				return newImageSources.size() > 0;
			}

			@Override
			protected void redo() {
				// remove the unpaired images and add new ones in their place
				for (ListIterator<Pair<Integer, IImageSource>> iter = 
					newImageSources.listIterator(newImageSources.size());
					iter.hasPrevious();) {
					Pair<Integer,IImageSource> imageSource = iter.previous();
					editorContext.getMultiImageSource().getSources().add(imageSource.first, imageSource.second);
					
				}
				for (ListIterator<Pair<Integer, IImageSource>> iter = 
					oldImageSources.listIterator(oldImageSources.size());
					iter.hasPrevious();) {
					Pair<Integer,IImageSource> imageSource = iter.previous();
					editorContext.getMultiImageSource().getSources().remove(imageSource.second);
				}
				
				refreshTable();
				imageSourceTableViewer.setSelection(new StructuredSelection(
						newImageSources));
				page.refreshSelection();
			}

			@Override
			protected void undo() {
				// remove the paired images and add original ones in their place
				for (ListIterator<Pair<Integer, IImageSource>> iter = 
					newImageSources.listIterator(newImageSources.size());
					iter.hasPrevious();) {
					Pair<Integer,IImageSource> imageSource = iter.previous();
					editorContext.getMultiImageSource().getSources().remove(imageSource.second);
					
				}
				for (ListIterator<Pair<Integer, IImageSource>> iter = 
					oldImageSources.listIterator();
					iter.hasNext();) {
					Pair<Integer,IImageSource> imageSource = iter.next();
					editorContext.getMultiImageSource().getSources().add(imageSource.first, imageSource.second);
				}
				
				refreshTable();
				imageSourceTableViewer.setSelection(new StructuredSelection(
						oldImageSources));
				page.refreshSelection();
			}
	
		};
		
		editorContext.pushAndExecute(operation);
		
	}	
	
	private void doSwap() {
		final List<IBitmapSource> changedImageSources = new ArrayList<IBitmapSource>();

		IImageSource[] imageSources = page.getImageSources();
		for (IImageSource source : imageSources) {
			if (!(source instanceof IBitmapSource))
				continue;
			IBitmapSource bmSource = (IBitmapSource) source;
			if (bmSource.getPath() == null || bmSource.getMaskPath() == null)
				continue;
			changedImageSources.add(bmSource);
		}
		
		MultiImageListPageOperation operation = new MultiImageListPageOperation(
				Messages.getString("ImageSourceTable.SwapImgMaskCommandLabel"), //$NON-NLS-1$
				page) {

			@Override
			protected boolean doesAnything() {
				return changedImageSources.size() > 0;
			}

			private void swapEm() {
				for (IBitmapSource bmSource : changedImageSources) {
					IPath path = bmSource.getPath();
					int depth = bmSource.getDepth();
					bmSource.setPath(bmSource.getMaskPath());
					bmSource.setDepth(bmSource.getMaskDepth());
					bmSource.setMaskDepth(depth > 1 ? 8 : 1);
					bmSource.setMaskPath(path);
				}
				refreshTable();
				imageSourceTableViewer.setSelection(new StructuredSelection(
						changedImageSources));
				page.refreshSelection();
				
			}

			@Override
			protected void redo() {
				swapEm();
			}

			@Override
			protected void undo() {
				swapEm();
			}
	
		};
		
		editorContext.pushAndExecute(operation);
		
	}
	
	
	private void doMove(int direction) {
		int[] selectedIndices = page.getImageSourceIndices();
		if (selectedIndices.length == 0)
			return;

		// get new insertion position after selected items are temporarily removed
		int newpos = direction < 0 ? selectedIndices[0] - 1
				: selectedIndices[selectedIndices.length - 1] + 1 - (selectedIndices.length - 1);
		
		List<IImageSource> sources = editorContext.getMultiImageSource().getSources();
		final java.util.List<IImageSource> oldOrdering = new ArrayList<IImageSource>(
				sources);
		final java.util.List<IImageSource> newOrdering = new ArrayList<IImageSource>(
				sources);
		
		// remove entries
		for (int i = selectedIndices.length - 1; i >= 0; i--)
			newOrdering.remove(selectedIndices[i]);
		
		// add entries back
		for (int i = 0; i < selectedIndices.length; i++)
			newOrdering.add(newpos++, sources.get(selectedIndices[i]));
		
		MultiImageListPageOperation operation = new MultiImageListPageOperation(
				Messages.getString("ImageSourceTable.ReorderImagesCommand"), //$NON-NLS-1$
				page) {

			@Override
			protected boolean doesAnything() {
				return true;
			}

			@Override
			protected void redo() {
				editorContext.getMultiImageSource().getSources().clear();
				editorContext.getMultiImageSource().getSources().addAll(newOrdering);
				refreshTable();
				page.refreshSelection();
			}

			@Override
			protected void undo() {
				editorContext.getMultiImageSource().getSources().clear();
				editorContext.getMultiImageSource().getSources().addAll(oldOrdering);
				refreshTable();
				page.refreshSelection();
			}
	
		};
		
		editorContext.pushAndExecute(operation);
		
	}
	
	private void doRemove() {
		int[] selectedIndices = page.getImageSourceIndices();
		if (selectedIndices.length == 0)
			return;
		
		List<IImageSource> sources = editorContext.getMultiImageSource().getSources();
		final java.util.List<IImageSource> oldOrdering = new ArrayList<IImageSource>(
				sources);
		final java.util.List<IImageSource> newOrdering = new ArrayList<IImageSource>(
				sources);
		
		// remove entries
		for (int i = selectedIndices.length - 1; i >= 0; i--)
			newOrdering.remove(selectedIndices[i]);
		
		MultiImageListPageOperation operation = new MultiImageListPageOperation(
				Messages.getString("ImageSourceTable.RemoveImagesCommand"), //$NON-NLS-1$
				page) {

			@Override
			protected boolean doesAnything() {
				return true;
			}

			@Override
			protected void redo() {
				editorContext.getMultiImageSource().getSources().clear();
				editorContext.getMultiImageSource().getSources().addAll(newOrdering);
				refreshTable();
				page.refreshSelection();
			}

			@Override
			protected void undo() {
				editorContext.getMultiImageSource().getSources().clear();
				editorContext.getMultiImageSource().getSources().addAll(oldOrdering);
				refreshTable();
				page.refreshSelection();
			}
	
		};
		
		editorContext.pushAndExecute(operation);
		
	}
	
	class AddImagesOperation extends MultiImageEditorOperation {

		private MultiImageEditorOperation setFormatsOperation;
		private final IImageSource[] newImageSources;

		/**
		 * @param label
		 * @param editor
		 */
		public AddImagesOperation(String label, MultiImageEditorContextBase editorContext,
				IImageSource[] newImageSources) {
			super(label, editorContext);
			this.newImageSources = newImageSources;
			setFormatsOperation = SelectedImageFormatControls.createSetDefaultImageFormatsOperation(page, 
					newImageSources);

		}

		@Override
		protected boolean doesAnything() {
			// get its state
			setFormatsOperation.doesAnything();
			return true;
		}

		@Override
		protected void redo() {
			for (IImageSource imageSource : newImageSources) {
				editorContext.getMultiImageSource().getSources().add(imageSource);
			}
			imageSourceTableViewer.setInput(editorContext.getMultiImageSource());
			imageSourceTableViewer.setSelection(new StructuredSelection(newImageSources));
			setFormatsOperation.redo();
		}

		@Override
		protected void undo() {
			// no need to undo setFormatsOperation since these items are deleted
			for (IImageSource imageSource : newImageSources) {
				editorContext.getMultiImageSource().getSources().remove(imageSource);
			}
			imageSourceTableViewer.setInput(editorContext.getMultiImageSource());
		}
	}
	
	private void doAddFromProject() {
		AddProjectImagesDialog dialog = new AddProjectImagesDialog(getShell(), editorContext);
		dialog.setBlockOnOpen(true);
		int ret = dialog.open();
		if (ret == IDialogConstants.OK_ID) {
			List<IImageSource> imageSourcesList = editorContext.createImageSources(dialog.getImageSourceReferences());
			final IImageSource[] newImageSources = 
				(IImageSource[]) imageSourcesList.toArray(new IImageSource[imageSourcesList.size()]);
			if (newImageSources.length == 0)
				return;

			MultiImageEditorOperation operation = new AddImagesOperation(
					Messages.getString("ImageSourceTable.AddImagesCommand"), editorContext, newImageSources); //$NON-NLS-1$
			editorContext.pushAndExecute(operation);
		}
	}
	
	private void doAddFromFilesystem() {
		AddFilesystemImagesDialog dialog = new AddFilesystemImagesDialog(getShell(), editorContext);
		dialog.setBlockOnOpen(true);
		int ret = dialog.open();
		if (ret == IDialogConstants.OK_ID) {
			List<IImageSource> imageSourcesList = editorContext.createImageSources(dialog.getImageSourceReferences());
			final IImageSource[] newImageSources = 
				(IImageSource[]) imageSourcesList.toArray(new IImageSource[imageSourcesList.size()]);
			if (newImageSources.length == 0)
				return;

			MultiImageEditorOperation operation = new AddImagesOperation(
					Messages.getString("ImageSourceTable.AddImagesCommand"), editorContext, newImageSources); //$NON-NLS-1$
			editorContext.pushAndExecute(operation);
		}
	}

	/**
	 * 
	 */
	public void refreshTable() {
		getTableViewer().refresh(editorContext.getMultiImageSource());
	}

}
