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


package com.nokia.sdt.uidesigner.ui;

import com.nokia.sdt.uidesigner.derived.IPaletteItemInfoFormWidget;

import org.eclipse.core.resources.IResource;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.*;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackListener;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.*;
import org.eclipse.gef.ui.palette.*;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.gef.ui.views.palette.PalettePage;
import org.eclipse.gef.ui.views.palette.PaletteViewerPage;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.*;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.views.properties.PropertySheet;

import java.util.*;

public abstract class DualGraphicalViewerPageWithPalette extends EditorPart
									implements CommandStackListener, ISelectionListener {

	protected class PaletteComposite extends Composite {
		
		private Control paletteViewerControl;
		private IPaletteItemInfoFormWidget infoWidget;
		
		public PaletteComposite(Composite parent) {
			super(parent, SWT.DEFAULT & ~SWT.H_SCROLL & ~SWT.V_SCROLL);
		}
		
		public void setPaletteViewerControl(Control control) {
			paletteViewerControl = control;
		}

		public void setInfoWidget(IPaletteItemInfoFormWidget widget) {
			infoWidget = widget;
		}

		@Override
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

	protected class DualGraphicalEditorPalettePage extends PaletteViewerPage {

		public DualGraphicalEditorPalettePage(PaletteViewerProvider provider) {
			super(provider);
		}

		@Override
		public Control getControl() {
			if (paletteComposite != null)
				return paletteComposite;
			
			return super.getControl();
		}
		
		@Override
		public void createControl(Composite parent) {
			super.createControl(parent);
			if (splitter != null)
				splitter.setExternalViewer(viewer);
		}

		@Override
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

	private boolean hasLowerViewer;
	private DefaultEditDomain editDomain;
	private GraphicalViewer upperGraphicalViewer;
	private GraphicalViewer lowerGraphicalViewer;
	private ActionRegistry actionRegistry;
	private List selectionActions = new ArrayList();
	private List stackActions = new ArrayList();
	private List propertyActions = new ArrayList();
	private SashForm horizontalSplitter;
	private Composite editorParent;
	private PaletteViewerProvider provider;
	private FlyoutPaletteComposite splitter;
	private DualGraphicalEditorPalettePage page;
	protected PaletteComposite paletteComposite;
	private IPartListener propertySheetListener;
	
	protected abstract FlyoutPreferences getPalettePreferences();

	protected abstract IPaletteItemInfoFormWidget createPaletteInfoWidget(Composite parent);

	protected abstract void createActions();
	
	protected abstract void hookGraphicalViewers();
	
	protected abstract boolean checkIsSameEditorPart(IEditorPart part);
	
	protected abstract void updateActions(List actionIds);
	
	public abstract IResource getDataModelResource();
	
	public DualGraphicalViewerPageWithPalette(boolean createLowerViewer) {
		super();
		hasLowerViewer = createLowerViewer;
	}
	
	protected boolean hasLowerViewer() {
		return hasLowerViewer;
	}

	/**
	 * When the command stack changes, the actions interested in the command stack are updated.
	 */
	public void commandStackChanged(EventObject event) {
		updateActions(stackActions);
	}

	/**
	 * Called to configure the graphical viewers before they receive their contents.
	 * This is where the root editpart should be configured. 
	 */
	protected void configureGraphicalViewers() {
		getUpperGraphicalViewer().getControl().setBackground(
				ColorConstants.listBackground);
		if (hasLowerViewer()) {
			getLowerGraphicalViewer().getControl().setBackground(
					ColorConstants.listBackground);
		}
	}

	protected void resetViewer(EditPartViewer viewer) {
		if (viewer != null) {
			Map editPartRegistry = viewer.getEditPartRegistry();
			Collection models = new ArrayList(editPartRegistry.keySet());
			for (Iterator iter = models.iterator(); iter.hasNext();) {
				Object model = iter.next();
				if (model instanceof EObject)
					editPartRegistry.remove(model);
			}
		}
	}
	
	protected void resetViewers() {
		resetViewer(getUpperGraphicalViewer());
		resetViewer(getLowerGraphicalViewer());
	}

	/**
	 * Creates the GraphicalViewers on the specified <code>Composite</code>.
	 * @param parent {@link Composite}
	 */
	protected void createGraphicalViewers(Composite parent) {
		horizontalSplitter = createHorizontalSplitter(parent);
		GraphicalViewer upperViewer = new ScrollingGraphicalViewer();
		upperViewer.createControl(horizontalSplitter);
		setUpperGraphicalViewer(upperViewer);
		if (hasLowerViewer()) {
			GraphicalViewer lowerViewer = new ScrollingGraphicalViewer();
			lowerViewer.createControl(horizontalSplitter);
			setLowerGraphicalViewer(lowerViewer);
		}
		configureGraphicalViewers();
		hookGraphicalViewers();
		initializeGraphicalViewers();
		configureSplitter();
	}

	private SashForm createHorizontalSplitter(Composite parent) {
		horizontalSplitter = new SashForm(parent, SWT.VERTICAL | SWT.SMOOTH
				| SWT.BORDER);
		horizontalSplitter.SASH_WIDTH = 2;
		return horizontalSplitter;
	}

	private void configureSplitter() {
		if (hasLowerViewer())
			horizontalSplitter.setWeights(new int[] { 8, 2 });
	}

	public SashForm getHorizontalSplitter() {
		return horizontalSplitter;
	}

	@Override
	public void createPartControl(Composite parent) {
		splitter = new FlyoutPaletteComposite(parent, SWT.NONE, getSite().getPage(),
				getPaletteViewerProvider(), getPalettePreferences());

		createGraphicalViewers(splitter);
		editorParent = parent;
		splitter.setGraphicalControl(getHorizontalSplitter());
		if (page != null) {
			splitter.setExternalViewer(page.getPaletteViewer());
			page = null;
		}
		initializeActionRegistry();
		initPropertySheetListener();
	}

	@Override
	public void dispose() {
		getSite().getPage().removePartListener(propertySheetListener);
		propertySheetListener = null;
		getCommandStack().removeCommandStackListener(this);
		getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(this);
		getEditDomain().setActiveTool(null);
		getActionRegistry().dispose();
		super.dispose();
	}
	
	public Composite getEditorParent() {
		return editorParent;
	}

	@Override
	protected void firePropertyChange(int property) {
		super.firePropertyChange(property);
		updateActions(propertyActions);
	}

	public ActionRegistry getActionRegistry() {
		if (actionRegistry == null)
			actionRegistry = new ActionRegistry();
		return actionRegistry;
	}

	protected PaletteViewerProvider getPaletteViewerProvider() {
		if (provider == null)
			provider = createPaletteViewerProvider();
		return provider;
	}

	protected PaletteViewerProvider createPaletteViewerProvider() {
		return new PaletteViewerProvider(getEditDomain()) {
			
			@Override
			public PaletteViewer createPaletteViewer(Composite parent) {
				paletteComposite = new PaletteComposite(parent);
				PaletteViewer viewer = super.createPaletteViewer(paletteComposite);
				paletteComposite.setPaletteViewerControl(viewer.getControl());
				IPaletteItemInfoFormWidget widget = createPaletteInfoWidget(paletteComposite);
				widget.setViewer(viewer);
				paletteComposite.setInfoWidget(widget);
				
				return viewer;
			}

			@Override
			protected void configurePaletteViewer(PaletteViewer viewer) {
				super.configurePaletteViewer(viewer);
				// create a drag source listener for this palette viewer
				// together with an appropriate transfer drop target listener,
				// this will enable
				// model element creation by dragging a
				// CombinatedTemplateCreationEntries
				// from the palette into the editor
				// @see DesignerEditor#createTransferDropTargetListener()
				viewer.addDragSourceListener(new TemplateTransferDragSourceListener(viewer));
			}
		};
	}

	protected DualGraphicalEditorPalettePage createPalettePage() {
		return new DualGraphicalEditorPalettePage(getPaletteViewerProvider());
	}

	@Override
	public Object getAdapter(Class type) {
		if (type == GraphicalViewer.class)
			return getUpperGraphicalViewer();
		if (type == CommandStack.class)
			return getCommandStack();
		if (type == ActionRegistry.class)
			return getActionRegistry();
		if (type == EditPart.class && getUpperGraphicalViewer() != null)
			return getUpperGraphicalViewer().getRootEditPart();
		if (type == IFigure.class && getUpperGraphicalViewer() != null)
			return ((GraphicalEditPart) getUpperGraphicalViewer()
					.getRootEditPart()).getFigure();
		if (type == ZoomManager.class && getUpperGraphicalViewer() != null) {
			RootEditPart rootPart = getUpperGraphicalViewer().getRootEditPart();
			if (rootPart instanceof ScalableRootEditPart) {
				ScalableRootEditPart scalableRoot = (ScalableRootEditPart) rootPart;
				return scalableRoot.getZoomManager();
			}
		}
		if (type == PalettePage.class) {
			if (splitter == null) {
				page = createPalettePage();
				return page;
			}
			return createPalettePage();
		}
		return super.getAdapter(type);
	}

	protected CommandStack getCommandStack() {
        return editDomain != null ? editDomain.getCommandStack() : null;
	}

	public DefaultEditDomain getEditDomain() {
		return editDomain;
	}

	public GraphicalViewer getUpperGraphicalViewer() {
		return upperGraphicalViewer;
	}

	public GraphicalViewer getLowerGraphicalViewer() {
		return lowerGraphicalViewer;
	}
	
	public abstract EditPartViewer getOutlineViewer();

	/**
	 * Returns the list of {@link IAction IActions} dependant on property
	 * changes in the Editor. These actions should implement the
	 * {@link UpdateAction} interface so that they can be updated in response to
	 * property changes. An example is the "Save" action.
	 * 
	 * @return the list of property-dependant actions
	 */
	protected List getPropertyActions() {
		return propertyActions;
	}

	/**
	 * Returns the list of {@link IAction IActions} dependant on changes in the
	 * workbench's {@link ISelectionService}. These actions should implement
	 * the {@link UpdateAction} interface so that they can be updated in
	 * response to selection changes. An example is the Delete action.
	 * 
	 * @return the list of selection-dependant actions
	 */
	protected List getSelectionActions() {
		return selectionActions;
	}

	/**
	 * Returns the list of {@link IAction IActions} dependant on the
	 * CommmandStack's state. These actions should implement the
	 * {@link UpdateAction} interface so that they can be updated in response to
	 * command stack changes. An example is the "undo" action.
	 * 
	 * @return the list of stack-dependant actions
	 */
	protected List getStackActions() {
		return stackActions;
	}

	/**
	 * Initializes the ActionRegistry. This registry may be used by {@link
	 * ActionBarContributor ActionBarContributors} and/or
	 * {@link ContextMenuProvider ContextMenuProviders}.
	 * <P>
	 * This method may be called on Editor creation, or lazily the first time
	 * {@link #getActionRegistry()} is called.
	 */
	protected void initializeActionRegistry() {
		createActions();
		updateActions(propertyActions);
		updateActions(stackActions);
	}

	protected void initializeGraphicalViewers() {
		splitter.hookDropTargetListener(getUpperGraphicalViewer());
	}

	/**
	 * @see org.eclipse.ui.ISelectionListener#selectionChanged(IWorkbenchPart,
	 *      ISelection)
	 */
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// If not the active editor, ignore selection changed.
		if (checkIsSameEditorPart(getSite().getPage().getActiveEditor()))
			updateActions(selectionActions);
	}

	protected void setEditDomain(DefaultEditDomain ed) {
		this.editDomain = ed;
	}

	@Override
	public void setFocus() {
		getUpperGraphicalViewer().getControl().setFocus();
	}

	protected void setUpperGraphicalViewer(GraphicalViewer viewer) {
		getEditDomain().addViewer(viewer);
		upperGraphicalViewer = viewer;
	}

	protected void setLowerGraphicalViewer(GraphicalViewer viewer) {
		getEditDomain().addViewer(viewer);
		lowerGraphicalViewer = viewer;
	}

	private void initPropertySheetListener() {
		getSite().getPage().addPartListener(propertySheetListener = new IPartListener() {

			public void partActivated(IWorkbenchPart part) {
				if (part instanceof PropertySheet) {
					PropertySheet propertySheet = (PropertySheet) part;
					IViewSite site = (IViewSite) propertySheet.getSite();
					if (site != null) {
						ActionRegistry registry = getActionRegistry();
						IActionBars bars = site.getActionBars();
						// add revert, undo and redo - save added automatically
						IAction action = registry.getAction(ActionFactory.REVERT.getId());
						bars.setGlobalActionHandler(action.getId(), action);
						action = registry.getAction(ActionFactory.UNDO.getId());
						bars.setGlobalActionHandler(action.getId(), action);
						action = registry.getAction(ActionFactory.REDO.getId());
						bars.setGlobalActionHandler(action.getId(), action);
						bars.updateActionBars();
					}
				}
			}

			public void partBroughtToTop(IWorkbenchPart part) {
			}

			public void partClosed(IWorkbenchPart part) {
			}

			public void partDeactivated(IWorkbenchPart part) {
			}

			public void partOpened(IWorkbenchPart part) {
			}
			
		});
	}
}
