/*******************************************************************************
 * Copyright (c) 2004, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package com.nokia.sdt.uidesigner.derived.ui;

import com.nokia.sdt.uidesigner.derived.IPaletteItemInfoFormWidget;

import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.ui.palette.*;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.gef.ui.views.palette.PalettePage;
import org.eclipse.gef.ui.views.palette.PaletteViewerPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
* 
*         org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette
* 
*/
public abstract class GraphicalEditorWithFlyoutPalette extends
		HorizontalSplitGraphicalEditor {

	private PaletteViewerProvider provider;

	private FlyoutPaletteComposite splitter;

	private CustomPalettePage page;
	
	protected PaletteComposite paletteComposite;

	public GraphicalEditorWithFlyoutPalette(boolean createLowerViewer) {
		super(createLowerViewer);
	}

	/**
	 * @see HorizontalSplitGraphicalEditor#initializeGraphicalViewers()
	 */
	protected void initializeGraphicalViewers() {
		splitter.hookDropTargetListener(getUpperGraphicalViewer());
	}

	/**
	 * @return a newly-created {@link CustomPalettePage}
	 */
	protected CustomPalettePage createPalettePage() {
		return new CustomPalettePage(getPaletteViewerProvider());
	}

	/**
	 * @see HorizontalSplitGraphicalEditor#createPartControl(Composite)
	 */
	public void createPartControl(Composite parent) {
		splitter = new FlyoutPaletteComposite(parent, SWT.NONE, getSite().getPage(),
				getPaletteViewerProvider(), getPalettePreferences());

		super.createPartControl(splitter);
		splitter.setGraphicalControl(getGraphicalControl());
		if (page != null) {
			splitter.setExternalViewer(page.getPaletteViewer());
			page = null;
		}
	}

	/**
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(Class type) {
		if (type == PalettePage.class) {
			if (splitter == null) {
				page = createPalettePage();
				return page;
			}
			return createPalettePage();
		}
		return super.getAdapter(type);
	}

	/**
	 * @return the graphical viewer's control
	 */
	protected Control getGraphicalControl() {
		return getHorizontalSplitter();
	}

	/**
	 * @return the FlyoutPreferences object used to save the flyout palette's
	 *         preferences
	 */
	protected abstract FlyoutPreferences getPalettePreferences();

	/**
	 * Returns the palette viewer provider that is used to create palettes for
	 * the view and the flyout. Creates one if it doesn't already exist.
	 * 
	 * @return the PaletteViewerProvider that can be used to create
	 *         PaletteViewers for this editor
	 * @see #createPaletteViewerProvider()
	 */
	protected final PaletteViewerProvider getPaletteViewerProvider() {
		if (provider == null)
			provider = createPaletteViewerProvider();
		return provider;
	}

	class PaletteComposite extends Composite {
		Control paletteViewerControl;
		IPaletteItemInfoFormWidget infoWidget;
		
		public PaletteComposite(Composite parent) {
			super(parent, SWT.DEFAULT & ~SWT.H_SCROLL & ~SWT.V_SCROLL);
			
		}
		
		public void setPaletteViewerControl(Control control) {
			paletteViewerControl = control;
		}

		public void setInfoWidget(IPaletteItemInfoFormWidget widget) {
			infoWidget = widget;
		}


		public void setBounds(int x, int y, int width, int height) {
			super.setBounds(x, y, width, height);
			int infoHeight = infoWidget.getHeight();
			int paletteHeight = height - infoHeight;
			Rectangle area = getClientArea();
			paletteViewerControl.setBounds(area.x, area.y, area.width, paletteHeight);
			infoWidget.getControl().setBounds(area.x, area.y + paletteHeight, area.width, infoHeight);
			infoWidget.getControl().redraw();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette#createPaletteViewerProvider()
	 */
	protected PaletteViewerProvider createPaletteViewerProvider() {
		return new PaletteViewerProvider(getEditDomain()) {
			
			/* (non-Javadoc)
			 * @see org.eclipse.gef.ui.palette.PaletteViewerProvider#createPaletteViewer(org.eclipse.swt.widgets.Composite)
			 */
			public PaletteViewer createPaletteViewer(Composite parent) {
				paletteComposite = new PaletteComposite(parent);
				PaletteViewer viewer = super.createPaletteViewer(paletteComposite);
				paletteComposite.setPaletteViewerControl(viewer.getControl());
				IPaletteItemInfoFormWidget widget = createPaletteInfoWidget(paletteComposite);
				widget.setViewer(viewer);
				paletteComposite.setInfoWidget(widget);
				
				return viewer;
			}

			/* (non-Javadoc)
			 * @see org.eclipse.gef.ui.palette.PaletteViewerProvider#configurePaletteViewer(org.eclipse.gef.ui.palette.PaletteViewer)
			 */
			protected void configurePaletteViewer(PaletteViewer viewer) {
				super.configurePaletteViewer(viewer);
				// create a drag source listener for this palette viewer
				// together with an appropriate transfer drop target listener,
				// this will enable
				// model element creation by dragging a
				// CombinatedTemplateCreationEntries
				// from the palette into the editor
				// @see ViewEditor#createTransferDropTargetListener()
				viewer.addDragSourceListener(new TemplateTransferDragSourceListener(viewer));
			}
		};
	}

	/**
	 * A custom PalettePage that helps GraphicalEditorWithFlyoutPalette keep the
	 * two PaletteViewers (one displayed in the editor and the other displayed
	 * in the PaletteView) in sync when switching from one to the other (i.e.,
	 * it helps maintain state across the two viewers).
	 * 
	 * 
	 * @since 3.0
	 */
	protected class CustomPalettePage extends PaletteViewerPage {
		/**
		 * Constructor
		 * 
		 * @param provider
		 *            the provider used to create a PaletteViewer
		 */
		public CustomPalettePage(PaletteViewerProvider provider) {
			super(provider);
		}

		/**
		 * @see org.eclipse.ui.part.IPage#getControl()
		 */
		public Control getControl() {
			if (paletteComposite != null)
				return paletteComposite;
			
			return super.getControl();
		}
		
		/**
		 * @see org.eclipse.ui.part.IPage#createControl(org.eclipse.swt.widgets.Composite)
		 */
		public void createControl(Composite parent) {
			super.createControl(parent);
			if (splitter != null)
				splitter.setExternalViewer(viewer);
		}

		/**
		 * @see org.eclipse.ui.part.IPage#dispose()
		 */
		public void dispose() {
			if (splitter != null)
				splitter.setExternalViewer(null);
			super.dispose();
		}

		/**
		 * @return the PaletteViewer created and displayed by this page
		 */
		public PaletteViewer getPaletteViewer() {
			return viewer;
		}
	}

	protected abstract IPaletteItemInfoFormWidget createPaletteInfoWidget(Composite parent);
}