/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.adapter.ILayoutContainer;
import com.nokia.sdt.editor.*;
import com.nokia.sdt.uidesigner.derived.IExtendedFlyoutPreferences;
import com.nokia.sdt.uidesigner.derived.IPaletteItemInfoFormWidget;
import com.nokia.sdt.uidesigner.events.BindDefaultEventAction;
import com.nokia.sdt.uidesigner.ui.actions.*;
import com.nokia.sdt.uidesigner.ui.command.CreationFactory;
import com.nokia.sdt.uidesigner.ui.editparts.*;
import com.nokia.sdt.uidesigner.ui.figure.LayoutContentsFigure;
import com.nokia.sdt.uidesigner.ui.utils.*;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.*;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.*;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.dnd.TemplateTransfer;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.editparts.*;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.ui.actions.*;
import org.eclipse.gef.ui.palette.DefaultPaletteViewerPreferences;
import org.eclipse.gef.ui.palette.PaletteViewerPreferences;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.*;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.properties.*;

import java.text.MessageFormat;
import java.util.*;

/**
 * 
 * 
 */
public class DesignerEditorPage extends DualGraphicalViewerPageWithPalette implements IDesignerEditor {

	public static final String LAST_FEEDBACK_TARGET = "LAST_FEEDBACK_TARGET"; //$NON-NLS-1$

	private PaletteRoot paletteRoot;
	private boolean currentPaletteState;

	/** KeyHandler with common bindings for both the Outline View and the Editor. */
	private KeyHandler sharedKeyHandler;

	/** OutlinePage instance for this editor. */
	private IContentOutlinePage outlinePage;

	/* This is the root of the editor's model. */
	protected ContentsObject contentsObject;

	private IDisplayModel displayModel;
	
	private List transientListeners;
	
	private IKeyEventProvider keyEventProvider;
	
	private SelectionManager selectionManager;
	
	private OpenEditorActions activationListener;
	
	private DefaultPaletteViewerPreferences paletteViewerPreferences;
	
	private IDesignerDataModelEditor dmEditor;
	
	private ViewEditorActionBarContributor contributor;

	/**
	 * Since updatePalette is exposed from IDesignerEditor, it can be called
	 * more than once before the palette is shown, thereby clearing the state we read
	 * from the open palette. So we cache the names until we show the palette.
	 */
	private List<String> cachedOpenPaletteDrawerNames;

	/**
	 * This class enables the use of ICreationTool as the creation factory template
	 * and also keeps the CreateRequest constant, so that target feedback listeners 
	 * can update its extended data and have it remain available for the CreateCommand.
	 */
	private static class DesignerEditorPageTemplateTransferDropTargetListener extends TemplateTransferDropTargetListener {
		CreateRequest request;
		
		private DesignerEditorPageTemplateTransferDropTargetListener(EditPartViewer viewer) {
			super(viewer);
			request = new CreateRequest();
		}

		@Override
		protected Request createTargetRequest() {
			request.setFactory(getFactory(TemplateTransfer.getInstance().getTemplate()));
			return request;
		}

		protected CreationFactory getFactory(Object template) {
			setEnablementDeterminedByCommand(true);
			Check.checkState(template instanceof ICreationTool);
			ICreationTool tool = (ICreationTool) template;
			return new CreationFactory(tool);
		}
	}

	private class OpenEditorActions implements IPageListener, IPartListener {
		// The reason for the page listener, is that if the editor is already open
		// when the workspace opens, then the part opened event precedes the perspective
		// arranging of views. In order to override the perspective, we must listen to 
		// perform the action at the last possible moment which seems to be page activation.

		private IEditorPart editorPart;

		public OpenEditorActions(IEditorPart editorPart) {
			editorPart.getSite().getWorkbenchWindow().addPageListener(this);
			editorPart.getSite().getPage().addPartListener(this);
			this.editorPart = (IEditorPart) editorPart.getAdapter(IDesignerDataModelEditor.class);
		}
		
		private void performOpenActions(IWorkbenchPage page) {
			// surface the Outline view, if it's open
			IViewPart part = page.findView(ShowOutlineAction.OUTLINE_VIEW);
			if (part != null) {
				page.bringToTop(part);
			}
		}
		
		public void pageActivated(IWorkbenchPage page) {
			if (page.isPartVisible(editorPart)) {
				performOpenActions(page);
			}
		}

		public void pageClosed(IWorkbenchPage page) {}
		public void pageOpened(IWorkbenchPage page) {}

		public void partActivated(IWorkbenchPart part) {}
		public void partBroughtToTop(IWorkbenchPart part) {}

		public void partClosed(IWorkbenchPart part) {
			if (!part.equals(editorPart))
				return;
			editorPart.getSite().getWorkbenchWindow().removePageListener(activationListener);
			editorPart.getSite().getPage().removePartListener(activationListener);
			activationListener = null;
		}

		public void partDeactivated(IWorkbenchPart part) {}
		
		public void partOpened(IWorkbenchPart part) {
			if (part.equals(editorPart)) {
				performOpenActions(editorPart.getSite().getPage());
			}
		}
		
	}
	
	/** Create a new ViewEditor instance. This is called by the Workspace. */
	public DesignerEditorPage(FormEditor formEditor) {
		super(true);
		dmEditor = (IDesignerDataModelEditor) formEditor.getAdapter(IDesignerDataModelEditor.class);
		contentsObject = new ContentsObject();
	}

	/**
	 * Configure the graphical viewer before it receives contents.
	 * <p>
	 * This is the place to choose an appropriate RootEditPart and
	 * EditPartFactory for your editor. The RootEditPart determines the behavior
	 * of the editor's "work-area". For example, GEF includes zoomable and
	 * scrollable root edit parts. The EditPartFactory maps model elements to
	 * edit parts (controllers).
	 * </p>
	 */
	protected void configureGraphicalViewers() {

		GraphicalViewer layoutGraphicalViewer = getUpperGraphicalViewer();
		layoutGraphicalViewer.setRootEditPart(new MultipleLayerRootEditPart());
		layoutGraphicalViewer
				.setEditPartFactory(new LayoutObjectEditPartFactory());
		layoutGraphicalViewer.setKeyHandler(new GraphicalViewerKeyHandler(
				layoutGraphicalViewer).setParent(getCommonKeyHandler()));

		KeyHandler keyHandler = getUpperGraphicalViewer().getKeyHandler();
		keyHandler.put(KeyStroke.getPressed(SWT.F5, 0), 
				getActionRegistry().getAction(NudgeAction.UP_ID));
		keyHandler.put(KeyStroke.getPressed(SWT.F6, 0), 
				getActionRegistry().getAction(NudgeAction.DOWN_ID));
		keyHandler.put(KeyStroke.getPressed(SWT.F7, 0), 
				getActionRegistry().getAction(NudgeAction.LEFT_ID));
		keyHandler.put(KeyStroke.getPressed(SWT.F8, 0), 
				getActionRegistry().getAction(NudgeAction.RIGHT_ID));
		keyHandler.put(KeyStroke.getPressed(SWT.F5, SWT.ALT), 
				getActionRegistry().getAction(NudgeAction.UP_BIG_ID));
		keyHandler.put(KeyStroke.getPressed(SWT.F6, SWT.ALT), 
				getActionRegistry().getAction(NudgeAction.DOWN_BIG_ID));
		keyHandler.put(KeyStroke.getPressed(SWT.F7, SWT.ALT), 
				getActionRegistry().getAction(NudgeAction.LEFT_BIG_ID));
		keyHandler.put(KeyStroke.getPressed(SWT.F8, SWT.ALT), 
				getActionRegistry().getAction(NudgeAction.RIGHT_BIG_ID));

		super.configureGraphicalViewers();
		if (hasLowerViewer()) {
			GraphicalViewer nonLayoutGraphicalViewer = getLowerGraphicalViewer();
			nonLayoutGraphicalViewer.setRootEditPart(new ScalableRootEditPart());
			nonLayoutGraphicalViewer
					.setEditPartFactory(new NonLayoutObjectEditPartFactory());
			nonLayoutGraphicalViewer.setKeyHandler(new GraphicalViewerKeyHandler(
					nonLayoutGraphicalViewer).setParent(getCommonKeyHandler()));
		}
		
		// configure the context menu providers
		ContextMenuProvider cmProvider = new ViewEditorContextMenuProvider(
				layoutGraphicalViewer, getActionRegistry());
		layoutGraphicalViewer.setContextMenu(cmProvider);
		getSite().registerContextMenu(cmProvider, getSelectionManager());

		if (hasLowerViewer()) {
			GraphicalViewer nonLayoutGraphicalViewer = getLowerGraphicalViewer();
			nonLayoutGraphicalViewer.setContextMenu(cmProvider);
		}
	}
	
	private void resetContentsToViewers() {
		GraphicalViewer layoutGraphicalViewer = getUpperGraphicalViewer();
		layoutGraphicalViewer.setContents(contentsObject); // set the contents of
														// this editor
		
		if (hasLowerViewer()) {
			GraphicalViewer nonLayoutGraphicalViewer = getLowerGraphicalViewer();
			nonLayoutGraphicalViewer.setContents(contentsObject); // set the contents
																// of this editor
		}
		
		if (outlinePage instanceof ViewEditorOutlinePage)
			((ViewEditorOutlinePage) outlinePage).initializeOutlineViewer();
		
		updatePalette(false);
	}

	/**
	 * Set up the editor's inital content (after creation).
	 */
	protected void initializeGraphicalViewers() {
		super.initializeGraphicalViewers();

		GraphicalViewer layoutGraphicalViewer = getUpperGraphicalViewer();
		layoutGraphicalViewer.setContents(contentsObject); // set the contents of
														// this editor
		// listen for dropped parts
		layoutGraphicalViewer
				.addDropTargetListener(createTransferDropTargetListener(layoutGraphicalViewer));

		if (hasLowerViewer()) {
			GraphicalViewer nonLayoutGraphicalViewer = getLowerGraphicalViewer();
			nonLayoutGraphicalViewer.setContents(contentsObject); // set the contents
																// of this editor
			// listen for dropped parts
			nonLayoutGraphicalViewer
					.addDropTargetListener(createTransferDropTargetListener(nonLayoutGraphicalViewer));
		}
	}
	
	/**
	 * Create a transfer drop target listener. When using a
	 * CombinedTemplateCreationEntry tool in the palette, this will enable model
	 * element creation by dragging from the palette.
	 * 
	 * @see #createPaletteViewerProvider()
	 */
	public static TransferDropTargetListener createTransferDropTargetListener(final EditPartViewer viewer) {
		return new DesignerEditorPageTemplateTransferDropTargetListener(viewer);
	}

	/**
	 * Forget the outline page for this editor. Note that this is called from
	 * ViewEditorOutlinePage#dispose, so the outline page is already disposed.
	 * 
	 * @see ViewEditorOutlinePage#dispose()
	 */
	public void disposeOutlinePage() {
		outlinePage = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchPart#dispose()
	 */
	public void dispose() {
		if (getControl() != null)
			setLayoutMode();
		
		getSelectionManager().dispose();
		
		// if initialized badly, this just crashes everywhere
		if (displayModel != null) {
			super.dispose();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette#getAdapter(java.lang.Class)
	 */
	/* (non-Javadoc)
	 * @see com.nokia.sdt.uidesigner.ui.IDesignerEditor#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(Class type) {
		if (type.isInstance(this))
			return this;
		
		if (type == IDesignerDataModelEditor.class)
			return dmEditor;
		if (type == org.eclipse.ui.views.properties.IPropertySheetPage.class) {
			PropertySheetPage page = new PropertySheetPage();
			IPropertySheetEntry rootEntry = new UndoablePropertySheetEntry(getCommandStack());
			((PropertySheetEntry) rootEntry).setPropertySourceProvider(new IPropertySourceProvider(){
				public IPropertySource getPropertySource(Object object) {
					if (object instanceof IPropertySource) {
						// if it's already an IPropertySource, return it
						return (IPropertySource) object;
					}
					else if ((object instanceof EditPart) && ((EditPart)object).getModel() instanceof EObject) { 
						// if it's an editpart, wrap its model
						return new PropertySourceWrapper((EObject) ((EditPart)object).getModel());
					}
					else if (object instanceof EObject) {
						// if it's an EObject (less likely), wrap it
						return new PropertySourceWrapper((EObject) object);
					}
					else if (object instanceof IAdaptable) {
						// if it's an IAdaptable (least likely), adapt and return it
						return (IPropertySource)((IAdaptable)object).getAdapter(IPropertySource.class);
					}
					
					return null;
				}
			});
			page.setRootEntry(rootEntry);
			return page;
		}

		// returns the content outline page for this editor
		if (type == IContentOutlinePage.class) {
			if (outlinePage == null) {
				outlinePage = new ViewEditorOutlinePage(this);
			}
			return outlinePage;
		}
		if (type == IWorkbenchPart.class) {
			return getSite().getPart();
		}
		if (type == IDisplayModel.class) {
			return displayModel;
		}
		if (type == IEditorSite.class) {
			return getEditorSite();
		}
		if (type == Shell.class) {
			return getSite() != null ? getSite().getShell() : null;
		}
		if (type == IEditorActionBarContributor.class) {
			if (contributor != null)
				return contributor;
			else {
				IEditorActionBarContributor contrib = getEditorSite().getActionBarContributor();
				if (contrib instanceof ViewEditorActionBarContributor)
					return contributor = (ViewEditorActionBarContributor) contrib;
				else
					return contributor = new ViewEditorActionBarContributor();
			}
		}
			
		return super.getAdapter(type);
	}

	private boolean isOutlineShowing() {
		return getSite().getPage().findView(ShowOutlineAction.OUTLINE_VIEW) != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette#getPalettePreferences()
	 */
	protected FlyoutPreferences getPalettePreferences() {
		return PaletteFactory.getCreatePalettePreferences();
	}

	public boolean shouldCollapsePalette() {
		if (paletteViewerPreferences == null)
			paletteViewerPreferences = new DefaultPaletteViewerPreferences();
		int autoCollapse = paletteViewerPreferences.getAutoCollapseSetting();
		return autoCollapse == PaletteViewerPreferences.COLLAPSE_ALWAYS;
	}

	private PaletteRoot getPaletteRoot(boolean restrictive, List<String> openDrawerNames) {
		boolean shouldCollapsePalette = openDrawerNames == null ? shouldCollapsePalette() : true;
		PaletteRoot newRoot = PaletteFactory.createPalette(this, 
					openDrawerNames, shouldCollapsePalette, restrictive);
		
		currentPaletteState = restrictive;
		paletteRoot = newRoot;
		
		return paletteRoot;
	}

	/**
	 * Returns the KeyHandler with common bindings for both the Outline and
	 * Graphical Views. For example, delete is a common action.
	 */
	public KeyHandler getCommonKeyHandler() {
		if (sharedKeyHandler == null) {
			sharedKeyHandler = new KeyHandler();

			// Add key and action pairs to sharedKeyHandler
			sharedKeyHandler
					.put(KeyStroke.getPressed(SWT.DEL, 127, 0),
							getActionRegistry().getAction(
									ActionFactory.DELETE.getId()));
		}
		return sharedKeyHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	/* (non-Javadoc)
	 * @see com.nokia.sdt.uidesigner.ui.IDesignerEditor#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void doSave(IProgressMonitor monitor) {
		EditorUtils.saveCurrentLayout(this);
	}
	
	private void saveSelectionForReload() {
		// SelectionManager checks this when part is opened
		try {
			// store the selection in a session property
			getDataModelResource().setSessionProperty(
					new QualifiedName(UIDesignerPlugin.getDefault().toString(), dmEditor.getPartName()),
					getSelectionManager().getSelectedModelNames());
		} 
		catch (CoreException ce) {
			IStatus status = Logging.newStatus(UIDesignerPlugin.getDefault(), ce);
			Logging.log(UIDesignerPlugin.getDefault(), status);
		}
	}
	
	protected void resetViewers() {
		super.resetViewers();
		resetViewer(getOutlineViewer());
	}
	
	private void resetLayers() {
		MultipleLayerRootEditPart rootEditPart = 
			(MultipleLayerRootEditPart) getUpperGraphicalViewer().getRootEditPart();
		Check.checkState(rootEditPart != null);
		rootEditPart.reset();
	}
	
	
	public void prepareForReload() {
		// don't do anything else if the editor is broken
		if (getUpperGraphicalViewer() == null)
			return;
		
		EditorUtils.saveCurrentLayout(this);
		saveSelectionForReload();
		setLayoutMode();
		getSelectionManager().setSelection(StructuredSelection.EMPTY);
		resetLayers();
		getSelectionManager().setSync(false);
		resetViewers();
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.uidesigner.ui.IDesignerEditor#reload()
	 */
	public void reload() {
		// don't do anything else if the editor is broken
		if (getUpperGraphicalViewer() == null)
			return;
		
		try {
			initDataModel();
		} 
		catch (CoreException e) {
			IStatus status = Logging.newStatus(UIDesignerPlugin.getDefault(), e);
			Logging.log(UIDesignerPlugin.getDefault(), status);
			Logging.showErrorDialog(null, Strings.getString("AbstractDesignerDataModelEditor.LoadFailedError"), status); //$NON-NLS-1$
		}
		resetContentsToViewers();
		getSelectionManager().setSync(true);
		getSelectionManager().reloadSelection();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#commandStackChanged(java.util.EventObject)
	 */
	public void commandStackChanged(EventObject event) {
		dmEditor.getFormEditor().editorDirtyStateChanged();
		super.commandStackChanged(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#doSaveAs()
	 */
	public void doSaveAs() {
		// do nothing, since not allowed
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
	 */
	public boolean isSaveAsAllowed() {
		return false;
	}

	public Object getModelObject() {
		return contentsObject;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.uidesigner.ui.IDesignerEditor#getDataModel()
	 */
	public IDesignerDataModel getDataModel() {
		return dmEditor.getDataModel();
	}
	
	public IDisplayModel getDisplayModel() {
		return displayModel;
	}
	
	@Override
	public boolean isDirty() {
		CommandStack commandStack = getCommandStack();
		return commandStack != null && commandStack.isDirty();
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.uidesigner.ui.IDesignerEditor#getDataModelResource()
	 */
	public IResource getDataModelResource() {
		IDesignerDataModel dataModel = getDataModel();
		if (dataModel != null) {
			IDesignerDataModelSpecifier modelSpecifier = dataModel.getModelSpecifier();
			if (modelSpecifier != null)
				return modelSpecifier.getPrimaryResource();
		}
		return null;
	}
	
	public void initDataModel() throws CoreException {
		IDesignerDataModel dataModel = getDataModel();
		if (dataModel != null) {
			EObject rootContainer = null;
			EObject[] containers = dataModel.getRootContainers();
			if (containers.length > 0) {
				rootContainer = containers[0];
				contentsObject.setRootContainer(rootContainer);
				displayModel = dataModel.getDisplayModelForRootContainer(rootContainer);
			}
			if (displayModel == null) {
                IComponentInstance ci = null;
                if (rootContainer != null)
                	ci = (IComponentInstance) EcoreUtil.getRegisteredAdapter(rootContainer, IComponentInstance.class);
			    IStatus status = Logging.newStatus(UIDesignerPlugin.getDefault(), IStatus.ERROR, 
                        MessageFormat.format(Strings.getString("AbstractDesignerDataModelEditor.CouldNotCreateDisplayModel"), //$NON-NLS-1$
                                new Object[] { ci != null ? ci.getName() : Strings.getString("AbstractDesignerDataModelEditor.Unknown"),  //$NON-NLS-1$
                                        ci != null ? ci.getComponentId() : Strings.getString("AbstractDesignerDataModelEditor.Unknown") })); //$NON-NLS-1$
                                
			    if (EditorServices.isEditorVisible(null))
			    	Logging.showErrorDialog(Strings.getString("AbstractDesignerDataModelEditor.Error"), Strings.getString("AbstractDesignerDataModelEditor.FailedToInitialize"), status); //$NON-NLS-1$ //$NON-NLS-2$
                throw new CoreException(status);
            }
			setDisplayModel(displayModel);
		}
	}
	
	/**
	 * Sets the site and input for this editor then creates and initializes the
	 * actions. Subclasses may extend this method, but should always call
	 * <code>super.init(site, input)
	 * </code>.
	 * 
	 * @see org.eclipse.ui.IEditorPart#init(IEditorSite, IEditorInput)
	 */
	public void init(IEditorSite site, IEditorInput input) {
		setSite(site);
		setInput(input);
		loadInput();
		getCommandStack().addCommandStackListener(this);
		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(this);
	}
	
	private IStatus loadInput() {
		try { 
			initDataModel();
		}
		catch (CoreException x) {
			return x.getStatus();
		}

		DefaultEditDomain defaultEditDomain = new DefaultEditDomain(this);
		defaultEditDomain.setCommandStack(dmEditor.getCommandStack());
		setEditDomain(defaultEditDomain); // loads palette
		
		return Status.OK_STATUS;
	}
	
	public EditPartViewer getOutlineViewer() {
		if (!isOutlineShowing())
			return null;
		
		ContentOutlinePage outline = (ContentOutlinePage) getAdapter(IContentOutlinePage.class);
		if (outline != null) {
			return ((ViewEditorOutlinePage) outline).getViewer();
		}
		return null;
	}
	
	public void updateSelectionActions() {
		updateActions(getSelectionActions());
	}

	public void setTransientMode(Object transientRootObject) {
		Check.checkArg(transientRootObject instanceof EObject);
		getMultipleLayerRootEditPart().setTransientMode((EObject) transientRootObject);
		fireEnteredTransientMode();
	}
	
	public Object getTransientRootObject() {
		TransientObjectEditPart part = getMultipleLayerRootEditPart().getTransientRootEditPart();
		if (part != null)
			return part.getModel();
		
		return null;
	}
	
	public void setLayoutMode() {
		if (displayModel == null)
			return;
		firePrepareToExitTransientMode();
		fireExitingTransientMode();
		getMultipleLayerRootEditPart().setLayoutMode();
	}
	
	public boolean isTransientMode() {
		return getMultipleLayerRootEditPart().isTransientMode();
	}
	
	public boolean isComponentEditing() {
		return getSelectionManager().isComponentEditing();
	}

	public LayoutContentsFigure getLayoutContentsFigure() {
		return (LayoutContentsFigure) ((GraphicalEditPart) getUpperGraphicalViewer().getContents()).getFigure();
	}
	
	public IFigure getTransientLayerRootFigure() {
		return getMultipleLayerRootEditPart().getTransientLayerRootFigure();
	}

	public Object getNonLayoutRoot() {
		return getDisplayModel().getNonLayoutRoot();
	}

	public void addTransientListener(TransientListener listener) {
		if (transientListeners == null)
			transientListeners = new ArrayList();
		
		transientListeners.add(listener);
	}

	public void removeTransientListener(TransientListener listener) {
		if (transientListeners == null)
			return;
		
		transientListeners.remove(listener);
	}
	
	private void fireEnteredTransientMode() {
		if (transientListeners == null)
			return;
		
		for (Iterator iter = (new ArrayList(transientListeners)).iterator(); iter.hasNext();) {
			TransientListener listener = (TransientListener) iter.next();
			listener.enteredTransientMode();
		}
	}
	
	private void firePrepareToExitTransientMode() {
		if (transientListeners == null)
			return;
		
		for (Iterator iter = (new ArrayList(transientListeners)).iterator(); iter.hasNext();) {
			TransientListener listener = (TransientListener) iter.next();
			listener.prepareToExitTransientMode();
		}
	}

	private void fireExitingTransientMode() {
		if (transientListeners == null)
			return;
		
		for (Iterator iter = (new ArrayList(transientListeners)).iterator(); iter.hasNext();) {
			TransientListener listener = (TransientListener) iter.next();
			listener.exitingTransientMode();
		}
	}
	
	public void setKeyEventProvider(IKeyEventProvider provider) {
		if (keyEventProvider != null) {
			List<KeyListener> listeners = keyEventProvider.getListeners();
			if (listeners != null) {
				for (Iterator<KeyListener> iter = listeners.iterator(); iter.hasNext();) {
					provider.addKeyListener(iter.next());
				}
			}
		}
		keyEventProvider = provider;
	}
	
	public IKeyEventProvider getKeyEventProvider() {
		ensureKeyEventProvider();
		return keyEventProvider;
	}
	
	private void ensureKeyEventProvider() {
		if (keyEventProvider == null) {
			Tool defaultTool = getEditDomain().getDefaultTool();
			Check.checkContract(defaultTool instanceof IKeyEventProvider);
			keyEventProvider = (IKeyEventProvider) defaultTool;
		}
	}

	public void addKeyListener(KeyListener listener) {
		ensureKeyEventProvider();
		keyEventProvider.addKeyListener(listener);
	}

	public void removeKeyListener(KeyListener listener) {
		ensureKeyEventProvider();
		keyEventProvider.removeKeyListener(listener);
	}

	protected IPaletteItemInfoFormWidget createPaletteInfoWidget(Composite parent) {
		return new PaletteItemInfoFormWidget(paletteComposite, 
				(IExtendedFlyoutPreferences) getPalettePreferences());
	}
	
//	public boolean isEditor() {
//		return true;
//	}
	
	/**
	 * Returns the selection manager object. 
	 * 
	 * @return selectionManager
	 */
	public SelectionManager getSelectionManager() {
		if (selectionManager == null) {
			selectionManager = new SelectionManager((IDesignerEditor) this, getSite().getPage());
		}
		return selectionManager;
	}

	protected MultipleLayerRootEditPart getMultipleLayerRootEditPart() {
		RootEditPart part = getUpperGraphicalViewer().getRootEditPart();
		Check.checkState(part instanceof MultipleLayerRootEditPart);
		return (MultipleLayerRootEditPart) part;
	}

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

		action = new UnifiedSelectAllAction(this);
		registry.registerAction(action);

		action = new MultiDeleteAction((IWorkbenchPart) this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new RevertAction(this);
		registry.registerAction(action);
		getStackActions().add(action.getId());

		action = new NudgeAction((IWorkbenchPart)this, SWT.UP, false);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new NudgeAction((IWorkbenchPart)this, SWT.DOWN, false);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new NudgeAction((IWorkbenchPart)this, SWT.LEFT, false);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new NudgeAction((IWorkbenchPart)this, SWT.RIGHT, false);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new NudgeAction((IWorkbenchPart)this, SWT.UP, true);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new NudgeAction((IWorkbenchPart)this, SWT.DOWN, true);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new NudgeAction((IWorkbenchPart)this, SWT.LEFT, true);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new NudgeAction((IWorkbenchPart)this, SWT.RIGHT, true);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		String id = ActionFactory.CUT.getId();
		IAction handler = new CutActionCommandHandler((IDesignerEditor) this);
		handler.setId(id);
		getActionRegistry().registerAction(handler);
		getSelectionActions().add(id);
		
		id = ActionFactory.COPY.getId();
		handler = new CopyActionCommandHandler((IDesignerEditor) this);
		handler.setId(id);
		getActionRegistry().registerAction(handler);
		getSelectionActions().add(id);

		id = ActionFactory.PASTE.getId();
		handler = new PasteActionCommandHandler((IDesignerEditor) this);
		handler.setId(id);
		getActionRegistry().registerAction(handler);
		getSelectionActions().add(id);

		getActionRegistry().registerAction(new ShowPropertiesAction(this));
		getActionRegistry().registerAction(new ShowEventsAction(this));
		getActionRegistry().registerAction(new ShowOutlineAction(this));
		
		action = new BindDefaultEventAction((IEditorPart) dmEditor.getAdapter(IEditorPart.class));
		id = action.getId();
		getActionRegistry().registerAction(action);
		getSelectionActions().add(id);
	}

	protected void hookGraphicalViewers() {
		getSelectionManager().addViewer(getUpperGraphicalViewer());
		ZoomManager zoomer = (ZoomManager) getAdapter(ZoomManager.class);
		zoomer.addZoomListener(new ZoomListener() {
			public void zoomChanged(double zoom) {
				EditorUtils.saveLastZoom((IDesignerEditor) DesignerEditorPage.this, zoom);
				getUpperGraphicalViewer().getControl().setFocus();
			}
		});
		zoomer.setZoomLevels(new double[] {0.5, 0.75, 1.0, 2.0, 4.0});
		zoomer.setZoom(EditorUtils.getSavedZoom((IDesignerEditor) DesignerEditorPage.this));
		if (hasLowerViewer()) {
			getSelectionManager().addViewer(getLowerGraphicalViewer());
		}
		dmEditor.getSite().setSelectionProvider(getSelectionManager());
	}
	
	protected void updateActions(List actionIds) {
		if (actionIds == null || actionIds.isEmpty())
			return;
		
		ActionRegistry registry = getActionRegistry();
		Iterator iter = actionIds.iterator();
		while (iter.hasNext()) {
			IAction action = registry.getAction(iter.next());
			if (action instanceof UpdateAction)
				((UpdateAction) action).update();
			else if (action instanceof BaseSelectionListenerAction)
				((BaseSelectionListenerAction) action).selectionChanged(
						(IStructuredSelection) getSelectionManager().getSelection());
		}
	}
	
	public void setDisplayModel(IDisplayModel displayModel) {
		this.displayModel = displayModel;
	}

	protected void setSite(IWorkbenchPartSite site) {
		super.setSite(site);
		activationListener = new OpenEditorActions(this);
	}

	private void refreshViewer(EditPartViewer viewer) {
		if (viewer != null) {
			EditPart part = (EditPart) viewer.getEditPartRegistry().get(contentsObject);
			if (part instanceof AbstractContentsEditPart)
				((AbstractContentsEditPart) part).forceRefreshChildren();
			else if (part instanceof ContentsOutlineEditPart)
				((ContentsOutlineEditPart) part).forceRefreshChildren();
		}
	}

	public void refreshFromModel() {
		boolean wasTransient = isTransientMode();
		ISelection selection = null;
		if (wasTransient) {
			selection = getSelectionManager().getSelection();
			setLayoutMode();
		}
		refreshViewer(getUpperGraphicalViewer());
		refreshViewer(getLowerGraphicalViewer());
		refreshViewer(getOutlineViewer());
		if (wasTransient) {
			getSelectionManager().setSelection(selection);
		}
		EObject rootContainer = getDisplayModel().getRootContainer();
		ILayoutContainer layoutContainer = Adapters.getLayoutContainer(rootContainer);
		if (layoutContainer != null)
			layoutContainer.layoutChildren();
	}

	protected boolean checkIsSameEditorPart(IEditorPart part) {
		if (part != null) {
			Object partAdapter = part.getAdapter(IDesignerDataModelEditor.class);
			return this.getAdapter(IDesignerDataModelEditor.class).equals(partAdapter);
		}
		
		return false;
	}

	/**
	 * Sets the edit domain for this editor.
	 * 
	 * @param ed
	 *            The new EditDomain
	 */
	protected void setEditDomain(DefaultEditDomain ed) {
		super.setEditDomain(ed);
		boolean restrictive = 
			FilterPaletteContributionItem.getSavedPaletteFiltering(getDataModelResource());		
		getEditDomain().setPaletteRoot(getPaletteRoot(restrictive, null));
	}

	public void updatePalette(boolean force) {
		boolean restrictive = 
			FilterPaletteContributionItem.getSavedPaletteFiltering(getDataModelResource());
		if (force || restrictive || (restrictive != currentPaletteState)) {
			List<String> openDrawerNames = EditorUtils.getOpenPaletteDrawerNames(this, paletteRoot);
			EditorUtils.replacePaletteRoot(this, getPaletteRoot(restrictive, openDrawerNames));
		}
	}
	
	public void setOpenDrawerNames(List<String> openDrawerNames) {
		cachedOpenPaletteDrawerNames = openDrawerNames;
	}
	
	public List<String> getOpenDrawerNames() {
		return cachedOpenPaletteDrawerNames;
	}

	public org.eclipse.swt.widgets.Control getControl() {
		return getHorizontalSplitter();
	}
}
