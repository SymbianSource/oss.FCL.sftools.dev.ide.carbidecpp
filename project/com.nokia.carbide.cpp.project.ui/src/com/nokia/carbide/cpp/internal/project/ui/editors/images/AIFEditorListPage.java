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
import com.nokia.carbide.cpp.epoc.engine.image.IImageSourceReference;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The top-level editor page.
 * 
 */
public class AIFEditorListPage extends Composite {

	private AIFSourceTable imageSourceTable;
	private AIFImagePropertiesComposite imagePropertiesComposite;
	private AIFEditorContext editorContext;
	/** Selected images */
	protected ArrayList<IImageSourceReference> imageSourceReferences;
	/** Indices of selected images, paired with imageSources */
	private ArrayList<Integer> imageIndices;
	private Listener globalKeyListener;
	private AIFOutputFileParametersComposite outputFileParametersComposite;

	/**
	 * @param parent
	 * @param style
	 */
	public AIFEditorListPage(Composite parent, int style,
			final AIFEditorContext editorContext) {
		super(parent, style);
		this.editorContext = editorContext;
		
		this.imageSourceReferences = new ArrayList<IImageSourceReference>();
		this.imageIndices = new ArrayList<Integer>();
		
		final GridLayout gridLayout = new GridLayout();
		setLayout(gridLayout);

		Group outputGroup = new Group(this, SWT.SHADOW_NONE);
		outputGroup.setText(Messages.getString("AIFEditorListPage.AIFDetailsLabel")); //$NON-NLS-1$
		outputGroup.setLayout(new FillLayout());
		
		outputFileParametersComposite = new AIFOutputFileParametersComposite(outputGroup, SWT.NONE, this);
		outputGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		outputGroup.setData(ImageEditorIds.NAME_KEY, "outputGroup"); //$NON-NLS-1$

		final SashForm sashForm = new SashForm(this, SWT.HORIZONTAL);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		imageSourceTable = new AIFSourceTable(this, sashForm, SWT.NONE); 
		imageSourceTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		imagePropertiesComposite = new AIFImagePropertiesComposite(this,
				sashForm, SWT.NONE);

		imagePropertiesComposite.setLayoutData(
				new GridData(SWT.RIGHT, SWT.FILL, false, true));
		imagePropertiesComposite.setData(ImageEditorIds.NAME_KEY, "selectedImageProperties"); //$NON-NLS-1$

		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		sashForm.setWeights(new int[] { 2, 1 });
		
		refresh();
		
		imageSourceTable.getTableViewer().addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				refreshSelection();
			}
			
		});
		imageSourceTable.getTableViewer().setData(ImageEditorIds.NAME_KEY, "imageSourceTable"); //$NON-NLS-1$

		
		// workaround to support undo/redo in a dialog.
		// SWT has the lovely feature of sending key events only to
		// widgets that specifically hook up KeyListeners, and only
		// if that widget ITSELF generated the key.  We'd like something
		// better -- listen for any key pressed when any widget is focused
		// and pre-filter it.
		
		globalKeyListener = new Listener() {
		
			public void handleEvent(Event event) {
				//System.out.println(event);
				if (event.type == SWT.KeyDown && isInPage(event.widget, AIFEditorListPage.this)) {
					if ((event.stateMask & SWT.CTRL) != 0) {
						if (event.keyCode == 'z') {
							editorContext.undo();
							event.doit = false;
						} else if (event.keyCode == 'y') {
							editorContext.redo();
							event.doit = false;
						}
					}
				}
			}

			/** See if the widget that was active when the key was
			 * pressed is in our page -- we get events for everything
			 * in the display (instance, i.e. anything this app created)!
			 * @param widget
			 * @param owner
			 * @return
			 */
			private boolean isInPage(Widget widget, Widget owner) {
				while (widget != null) {
					if (widget == owner)
						return true;
					// don't eat up its commands
					if (widget instanceof Text)
						return false;
					if (widget instanceof Control) {
						widget = ((Control) widget).getParent();
					} else 
						break;
				}
				return false;
			}
			
		};
		getDisplay().addFilter(SWT.KeyDown, globalKeyListener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Widget#dispose()
	 */
	@Override
	public void dispose() {
		getDisplay().removeFilter(SWT.KeyDown, globalKeyListener);
		super.dispose();
	}
	
	public void refresh() {
		if (isDisposed())
			return;
		imagePropertiesComposite.refresh();
		outputFileParametersComposite.refresh();
	}
	
	/**
	 * Refresh the whole table.
	 */
	public void refreshTable() {
		if (isDisposed())
			return;
		imageSourceTable.getTableViewer().refresh();
	}

	/**
	 * Refresh an element of the table.
	 */
	public void refreshElement(IImageSource imageSource) {
		if (isDisposed())
			return;
		imageSourceTable.refreshTable();
	}

	/**
	 * 
	 */
	public void refreshSelected() {
		if (isDisposed())
			return;
		
		// refresh whole table
		//multiImageListPane.getTableViewer().refresh();
		imageSourceTable.getTableViewer().update(
				imageSourceReferences.toArray(), null);
		
		// and restore selection (which is probably lost because
		// the items are no longer equal to their previous values)
		imageSourceTable.getTableViewer().setSelection(
				new StructuredSelection(imageSourceReferences));
	}

	/**
	 * Create a path from the given filename
	 * @param filename
	 * @return
	 */
	public IPath createPath(String filename) {
		return editorContext.createPath(filename);
	}
	
	/**
	 * @return
	 */
	public AIFEditorContext getEditorContext() {
		return editorContext;
	}

	/**
	 * @return 
	 */
	public TableViewer getImageSourceTable() {
		return imageSourceTable.getTableViewer();
	}

	/**
	  * @return the indices of selected image sources in the multiimagesource
	 */
	public int[] getImageSourceIndices() {
		int[] vals = new int[imageIndices.size()];
		int idx = 0;
		for (Integer i : imageIndices) {
			vals[idx++] = i;
		}
		return vals;
	}

	/**
	 * Refresh the selection after the list changes.
	 */
	public void refreshSelection() {
		IStructuredSelection selection = (IStructuredSelection) imageSourceTable.getTableViewer().getSelection();
		IMMPAIFInfo mis = AIFEditorListPage.this.editorContext.getMMPAIFInfo();
		imageSourceReferences.clear();
		imageIndices.clear();
		for (Object obj : selection.toList()) {
			if (obj instanceof IImageSourceReference) {
				imageSourceReferences.add((IImageSourceReference) obj);
				imageIndices.add(mis.getSourceBitmaps().indexOf(obj));
			}
		}
		Collections.sort(imageIndices);
		imageSourceTable.refresh();
		imagePropertiesComposite.refresh();
		
	}

	/**
	 * @return
	 */
	public IImageSourceReference[] getImageSourceReferences() {
		return (IImageSourceReference[]) imageSourceReferences.toArray(new IImageSourceReference[imageSourceReferences.size()]);
	}

}
