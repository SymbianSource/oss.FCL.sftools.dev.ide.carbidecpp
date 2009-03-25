/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package com.nokia.sdt.uidesigner.derived.ui;

import org.eclipse.core.resources.IResource;
import org.eclipse.draw2d.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.*;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackListener;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.*;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.*;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.views.properties.PropertySheet;

import java.util.*;

/**
 * 
 *         org.eclipse.gef.ui.parts.GraphicalEditor
 * 
 */
public abstract class HorizontalSplitGraphicalEditor extends EditorPart
		implements CommandStackListener, ISelectionListener {

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

	private IPartListener partListener;
	
	/**
	 * Constructs the editor part
	 */
	public HorizontalSplitGraphicalEditor(boolean createLowerViewer) {
		hasLowerViewer = createLowerViewer;
	}
	
	protected boolean hasLowerViewer() {
		return hasLowerViewer;
	}

	/**
	 * When the command stack changes, the actions interested in the command
	 * stack are updated.
	 * 
	 * @param event
	 *            the change event
	 */
	public void commandStackChanged(EventObject event) {
		updateActions(stackActions);
	}

	/**
	 * Called to configure the graphical viewer before it receives its contents.
	 * This is where the root editpart should be configured. Subclasses should
	 * extend or override this method as needed.
	 */
	protected void configureGraphicalViewers() {
		getUpperGraphicalViewer().getControl().setBackground(
				ColorConstants.listBackground);
		if (hasLowerViewer()) {
			getLowerGraphicalViewer().getControl().setBackground(
					ColorConstants.listBackground);
		}
	}

	/**
	 * Creates actions for this editor. Subclasses should override this method
	 * to create and register actions with the {@link ActionRegistry}.
	 */
	protected void createActions() {
		ActionRegistry registry = getActionRegistry();
		IAction action;

		action = new UndoAction(this);
		registry.registerAction(action);
		getStackActions().add(action.getId());

		action = new RedoAction(this);
		registry.registerAction(action);
		getStackActions().add(action.getId());

		action = new SaveAction(this);
		registry.registerAction(action);
		getStackActions().add(action.getId());

		action = new PrintAction(this);
		registry.registerAction(action);

		// alignment and nudging
		
		action = new MatchWidthAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		action = new MatchHeightAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		action = new AlignmentAction((IWorkbenchPart)this, PositionConstants.LEFT);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart)this, PositionConstants.RIGHT);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart)this, PositionConstants.TOP);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart)this, PositionConstants.BOTTOM);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart)this, PositionConstants.CENTER);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart)this, PositionConstants.MIDDLE);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

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
	 * 
	 * @param parent
	 *            the parent composite
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

	/**
	 * Realizes the Editor by creating it's Control.
	 * <P>
	 * WARNING: This method may or may not be called by the workbench prior to
	 * {@link #dispose()}.
	 * 
	 * @param parent
	 *            the parent composite
	 */
	public void createPartControl(Composite parent) {
		createGraphicalViewers(parent);
		editorParent = parent;
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchPart#dispose()
	 */
	public void dispose() {
		getCommandStack().removeCommandStackListener(this);
		getSite().getWorkbenchWindow().getSelectionService()
				.removeSelectionListener(this);
		getEditDomain().setActiveTool(null);
		getActionRegistry().dispose();
		super.dispose();
	}
	
	public Composite getEditorParent() {
		return editorParent;
	}

	/**
	 * @see org.eclipse.ui.part.WorkbenchPart#firePropertyChange(int)
	 */
	protected void firePropertyChange(int property) {
		super.firePropertyChange(property);
		updateActions(propertyActions);
	}

	/**
	 * Lazily creates and returns the action registry.
	 * 
	 * @return the action registry
	 */
	public ActionRegistry getActionRegistry() {
		if (actionRegistry == null)
			actionRegistry = new ActionRegistry();
		return actionRegistry;
	}

	/**
	 * Returns the adapter for the specified key.
	 * 
	 * <P>
	 * <EM>IMPORTANT</EM> certain requests, such as the property sheet, may be
	 * made before or after {@link #createPartControl(Composite)} is called. The
	 * order is unspecified by the Workbench.
	 * 
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
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
		return super.getAdapter(type);
	}

	/**
	 * Returns the command stack.
	 * 
	 * @return the command stack
	 */
	protected CommandStack getCommandStack() {
        return editDomain != null ? editDomain.getCommandStack() : null;
	}

	/**
	 * Returns the edit domain.
	 * 
	 * @return the edit domain
	 */
	protected DefaultEditDomain getEditDomain() {
		return editDomain;
	}

	/**
	 * Returns the upper graphical viewer.
	 * 
	 * @return the upper graphical viewer
	 */
	public GraphicalViewer getUpperGraphicalViewer() {
		return upperGraphicalViewer;
	}

	/**
	 * Returns the lower graphical viewer.
	 * 
	 * @return the lower graphical viewer
	 */
	public GraphicalViewer getLowerGraphicalViewer() {
		return lowerGraphicalViewer;
	}
	
	/**
	 * Returns the tree viewer.
	 * 
	 * @return the tree viewer
	 */
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
	 * Hooks the GraphicalViewer to the rest of the Editor. By default, the
	 * viewer is added to the SelectionSynchronizer, which can be used to keep 2
	 * or more EditPartViewers in sync. The viewer is also registered as the
	 * ISelectionProvider for the Editor's PartSite.
	 */
	protected void hookGraphicalViewers() {
		// overriden
	}

    protected void loadInput() throws PartInitException {
        // override in subclasses
    }
    
	/**
	 * Sets the site and input for this editor then creates and initializes the
	 * actions. Subclasses may extend this method, but should always call
	 * <code>super.init(site, input)
	 * </code>.
	 * 
	 * @see org.eclipse.ui.IEditorPart#init(IEditorSite, IEditorInput)
	 */
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
        loadInput();
		getCommandStack().addCommandStackListener(this);
		getSite().getWorkbenchWindow().getSelectionService()
				.addSelectionListener(this);
		initializeActionRegistry();
		initPropertySheetListener();
	}

	private void initPropertySheetListener() {
		getSite().getPage().addPartListener(partListener = new IPartListener() {

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
				if (part.equals(HorizontalSplitGraphicalEditor.this)) {
					getSite().getPage().removePartListener(partListener);
					partListener = null;
				}
			}

			public void partDeactivated(IWorkbenchPart part) {
			}

			public void partOpened(IWorkbenchPart part) {
			}
			
		});
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

	/**
	 * Override to set the contents of the GraphicalViewers after creation.
	 * 
	 * @see #createGraphicalViewers(Composite)
	 */
	protected abstract void initializeGraphicalViewers();

	protected abstract boolean checkIsSameEditorPart(IEditorPart part);
	
	/**
	 * @see org.eclipse.ui.ISelectionListener#selectionChanged(IWorkbenchPart,
	 *      ISelection)
	 */
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// If not the active editor, ignore selection changed.
		if (checkIsSameEditorPart(getSite().getPage().getActiveEditor()))
			updateActions(selectionActions);
	}

	/**
	 * Sets the ActionRegistry for this EditorPart.
	 * 
	 * @param registry
	 *            the registry
	 */
	protected void setActionRegistry(ActionRegistry registry) {
		actionRegistry = registry;
	}

	/**
	 * Sets the EditDomain for this EditorPart.
	 * 
	 * @param ed
	 *            the domain
	 */
	protected void setEditDomain(DefaultEditDomain ed) {
		this.editDomain = ed;
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchPart#setFocus()
	 */
	public void setFocus() {
		getUpperGraphicalViewer().getControl().setFocus();
	}

	/**
	 * Sets the upper GraphicalViewer for this EditorPart.
	 * 
	 * @param viewer
	 *            the graphical viewer
	 */
	protected void setUpperGraphicalViewer(GraphicalViewer viewer) {
		getEditDomain().addViewer(viewer);
		upperGraphicalViewer = viewer;
	}

	/**
	 * Sets the lower GraphicalViewer for this EditorPart.
	 * 
	 * @param viewer
	 *            the graphical viewer
	 */
	protected void setLowerGraphicalViewer(GraphicalViewer viewer) {
		getEditDomain().addViewer(viewer);
		lowerGraphicalViewer = viewer;
	}

	/**
	 * A convenience method for updating a set of actions defined by the given
	 * List of action IDs. The actions are found by looking up the ID in the
	 * {@link #getActionRegistry() action registry}. If the corresponding action
	 * is an {@link UpdateAction}, it will have its <code>update()</code>
	 * method called.
	 * 
	 * @param actionIds
	 *            the list of IDs to update
	 */
	protected void updateActions(List actionIds) {
		// overriden
	}

	public abstract IResource getDataModelResource();
}
