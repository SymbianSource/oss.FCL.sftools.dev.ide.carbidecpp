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
package com.nokia.carbide.cpp.internal.project.ui.views;

import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.cdt.internal.ui.cview.CViewElementComparer;
import org.eclipse.cdt.internal.ui.cview.CViewLabelProvider;
import org.eclipse.cdt.internal.ui.util.ProblemTreeViewer;
import org.eclipse.cdt.internal.ui.util.RemoteTreeViewer;
import org.eclipse.cdt.internal.ui.viewsupport.AppearanceAwareLabelProvider;
import org.eclipse.cdt.internal.ui.viewsupport.CElementImageProvider;
import org.eclipse.cdt.internal.ui.viewsupport.DecoratingCLabelProvider;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.IWorkingSetManager;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ResourceWorkingSetFilter;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.IShowInSource;
import org.eclipse.ui.part.IShowInTargetList;
import org.eclipse.ui.part.ResourceTransfer;
import org.eclipse.ui.part.ShowInContext;
import org.eclipse.ui.part.ViewPart;

import com.nokia.carbide.cpp.internal.project.ui.ProjectUIHelpIds;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.carbide.cpp.internal.project.ui.actions.EditMBMFileEntryAction;
import com.nokia.carbide.cpp.internal.project.ui.actions.EditMIFFileEntryAction;
import com.nokia.carbide.cpp.ui.CarbideUIPlugin;
import com.nokia.carbide.cpp.ui.ICarbideSharedImages;
import com.nokia.cpp.internal.api.utils.core.Check;

public class SymbianProjectNavigatorView extends ViewPart implements IShowInSource, IShowInTargetList {
	private ProblemTreeViewer viewer;
	private SPNViewContentProvider contentProvider;
	private DrillDownAdapter drillDownAdapter;
	private Action filteringAction;
	private Action displaySourcePathsAction;
	private Action sortMMPsAction;
	private SPNViewActionGroup actionGroup;
	private WorkspaceListener workspaceListener;
	private IMemento memento;
	private Listener dragDetectListener;
	private ResourceWorkingSetFilter workingSetFilter = new ResourceWorkingSetFilter();

	public static final String SPN_VIEW_ID = "com.nokia.carbide.cpp.project.ui.views.SymbianProjectNavigatorView"; //$NON-NLS-1$
	
	// Persistence tags.
	private static final String TAG_VERTICAL_POSITION = "verticalPosition"; //$NON-NLS-1$
	private static final String TAG_HORIZONTAL_POSITION = "horizontalPosition"; //$NON-NLS-1$
	private static final String TAG_WORKINGSET = "workingSet"; //$NON-NLS-1$

	private IPropertyChangeListener workingSetListener = new IPropertyChangeListener() {

		public void propertyChange(PropertyChangeEvent ev) {
			String property = ev.getProperty();
			Object newValue = ev.getNewValue();
			Object oldValue = ev.getOldValue();
			IWorkingSet filterWorkingSet = workingSetFilter.getWorkingSet();

			if (property == null) {
				return;
			}
			if (IWorkingSetManager.CHANGE_WORKING_SET_REMOVE.equals(property) && oldValue == filterWorkingSet) {
				setWorkingSet(null);
			} else if (IWorkingSetManager.CHANGE_WORKING_SET_CONTENT_CHANGE.equals(property) && newValue == filterWorkingSet) {
				getViewer().refresh();
			}
		}
	};
	
	public SymbianProjectNavigatorView() {
	}

	/*
	 * (non-Javadoc) Method declared on IViewPart.
	 */
	public void init(IViewSite site, IMemento memento) throws PartInitException {
		super.init(site, memento);
		this.memento = memento;
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new RemoteTreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.getTree().setData(".uid", "SPNViewTree"); //$NON-NLS-1$ //$NON-NLS-2$

		viewer.setUseHashlookup(true);
		viewer.setComparer(new CViewElementComparer());

		getSite().setSelectionProvider(viewer);

		drillDownAdapter = new DrillDownAdapter(viewer);
		contentProvider = new SPNViewContentProvider(viewer);
		viewer.setContentProvider(contentProvider);
		
		// just use the same label provider CView does
		viewer.setLabelProvider(new DecoratingCLabelProvider(new CViewLabelProvider(AppearanceAwareLabelProvider.DEFAULT_TEXTFLAGS, AppearanceAwareLabelProvider.DEFAULT_IMAGEFLAGS | CElementImageProvider.SMALL_ICONS), true));

		viewer.setSorter(contentProvider.getViewSorter());
        
		viewer.addFilter(workingSetFilter);
		initWorkingSetFilter();

		viewer.setInput(getViewSite());
		
		if (memento != null) {
			final IMemento theMemento = memento;
			// this takes a heckuva long time at startup since it leads to loading
			// all the CDT metadata, possibly re-parsing bld.inf/mmps, etc...
			Job job = new Job("Restoring Symbian Project Navigator") {
				/* (non-Javadoc)
				 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
				 */
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					restoreState(theMemento);
					return Status.OK_STATUS;
				}
			};
			job.setUser(false);
			job.schedule();
		}
		memento = null;

		initDrag();
		
		makeActions();
		getActionGroup().fillActionBars(getViewSite().getActionBars());
		hookContextMenu();
		contributeToActionBars();
		initListeners(viewer);

		// add the property changes after all of the UI work has been done.
		IWorkingSetManager wsmanager = getViewSite().getWorkbenchWindow().getWorkbench().getWorkingSetManager();
		wsmanager.addPropertyChangeListener(workingSetListener);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, ProjectUIHelpIds.SPN_VIEW);
	}

	/**
	 * Add listeners to the viewer.
	 */
	protected void initListeners(TreeViewer viewer) {
		viewer.addDoubleClickListener(new IDoubleClickListener() {

			public void doubleClick(DoubleClickEvent event) {
				handleDoubleClick(event);
			}
		});

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				final IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				updateStatusLine(selection);
				updateActionBars(selection);
			}
		});

		viewer.getControl().addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				if (getActionGroup() != null) {
					getActionGroup().handleKeyPressed(e);
				}
			}

			public void keyReleased(KeyEvent e) {
				if (getActionGroup() != null) {
					getActionGroup().handleKeyReleased(e);
				}
			}
		});

		viewer.addOpenListener(new IOpenListener() {
			public void open(OpenEvent event) {
				handleOpen(event);
			}
		});
		
		workspaceListener = new WorkspaceListener(viewer, getViewSite(), contentProvider);
		ResourcesPlugin.getWorkspace().addResourceChangeListener(workspaceListener);
	}

	/**
	 * Restores the working set filter from the persistence store.
	 */
	private void initWorkingSetFilter() {
		if (memento == null) {
			return;
		}
		String wsname = memento.getString(TAG_WORKINGSET);

		if (wsname != null && wsname.equals("") == false) { //$NON-NLS-1$
			IWorkingSetManager wsmanager = getViewSite().getWorkbenchWindow().getWorkbench().getWorkingSetManager();
			IWorkingSet workingSet = wsmanager.getWorkingSet(wsname);
			if (workingSet != null) {
				// Only initialize filter. Don't set working set into viewer.
				// Working set is set via WorkingSetFilterActionGroup
				// during action creation.
				workingSetFilter.setWorkingSet(workingSet);
			}
		}
	}

	private void updateActionBars(IStructuredSelection selection) {
		SPNViewActionGroup group = getActionGroup();
		if (group != null) {
			group.setContext(new ActionContext(selection));
			group.updateActionBars();
		}
	}

	void updateStatusLine(IStructuredSelection selection) {
		String msg = ""; //$NON-NLS-1$
		if (selection.size() == 1) {
			Object o = selection.getFirstElement();
			
			if (o instanceof IResource) {
				msg = ((IResource)o).getFullPath().makeRelative().toString();
			} else if (o instanceof ISPNStatusLineDecorator) {
				msg = ((ISPNStatusLineDecorator)o).getStatusLineText();
			} else if (o instanceof IWorkbenchAdapter) {
				IWorkbenchAdapter wAdapter = (IWorkbenchAdapter)o;
				msg = wAdapter.getLabel(o);
			} else if (o instanceof ITranslationUnit) {
				IPath location = ((ITranslationUnit)o).getLocation();
				if (location != null) {
					msg = location.toString();
				}
			}
		}

		getViewSite().getActionBars().getStatusLineManager().setMessage(msg);
	}

	private void hookContextMenu() {
		// context menu should have: open, open with, refresh, open command window, show in explorer
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				SymbianProjectNavigatorView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
	}

	private void fillContextMenu(IMenuManager manager) {
		drillDownAdapter.addNavigationActions(manager);
		manager.add(new Separator());

		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		SPNViewActionGroup actionGroup = getActionGroup();
		if (actionGroup != null) {
			actionGroup.setContext(new ActionContext(selection));
			actionGroup.fillContextMenu(manager);
			actionGroup.setContext(null);
		}
		manager.add(new Separator());

		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		drillDownAdapter.addNavigationActions(manager);
		manager.add(displaySourcePathsAction);
		manager.add(filteringAction);
		manager.add(sortMMPsAction);
		manager.add(new Separator());
	}

	private void makeActions() {
		setActionGroup(new MainActionGroup(this));

		displaySourcePathsAction = new Action(Messages.SymbianProjectNavigatorView_DisplaySourcePathsText) {
			public void run() {
				contentProvider.enableDisplaySourcePaths(!contentProvider.isSourcePathsDisplayed());
			}
		};
		displaySourcePathsAction.setToolTipText(Messages.SymbianProjectNavigatorView_DisplaySourcePathsToolTip);
		displaySourcePathsAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER));
		displaySourcePathsAction.setChecked(contentProvider.isSourcePathsDisplayed());

		filteringAction = new Action(Messages.SymbianProjectNavigatorView_FilterText) {
			public void run() {
				contentProvider.enableFiltering(!contentProvider.isFilteringEnabled());
			}
		};
		filteringAction.setToolTipText(Messages.SymbianProjectNavigatorView_FilterToolTip);
		filteringAction.setImageDescriptor(CarbideUIPlugin.getSharedImages().
			getImageDescriptor(ICarbideSharedImages.IMG_FILTER_16_16));
		filteringAction.setChecked(contentProvider.isFilteringEnabled());

		sortMMPsAction = new Action(Messages.SymbianProjectNavigatorView_SortMMPsText) {
			public void run() {
				contentProvider.enableMMPSorting(!contentProvider.isMMPSortingEnabled());
			}
		};
		sortMMPsAction.setToolTipText(Messages.SymbianProjectNavigatorView_SortMMPsToolTip);
		sortMMPsAction.setImageDescriptor(ProjectUIPlugin.getImageDescriptor("icons/alphab_sort_co.gif")); //$NON-NLS-1$
		sortMMPsAction.setChecked(contentProvider.isMMPSortingEnabled());
	}

	/**
	 * Handles double clicks in viewer. Opens editor if file double-clicked.
	 */
	protected void handleDoubleClick(DoubleClickEvent event) {
		IStructuredSelection s = (IStructuredSelection) event.getSelection();
		Object o = s.getFirstElement();
		if (viewer.isExpandable(o) && !contentProvider.doubleClickShouldNotExpand(o)) {
			viewer.setExpandedState(o, !viewer.getExpandedState(o));
		}
	}

	/**
	 * Handles an open event from the viewer.
	 * Opens an editor on the selected element(s).
	 *
	 * @param event the open event
	 */
	protected void handleOpen(OpenEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		if (!selection.isEmpty() && selection.getFirstElement() instanceof IMBMMIFFileEntry) {
			editMBMMIFFile(selection);
		} else {
			getActionGroup().runDefaultAction(selection);
		}
		
	}

	/**
	 * @param selection
	 */
	private void editMBMMIFFile(IStructuredSelection selection) {
		IMBMMIFFileEntry entry = (IMBMMIFFileEntry) selection.getFirstElement();
		IWorkbenchWindowActionDelegate actionDelegate = null;
		IAction action = new Action() {
		};
		if (entry instanceof IMBMFileEntry) {
			actionDelegate = new EditMBMFileEntryAction();
			actionDelegate.init(getSite().getWorkbenchWindow());
			actionDelegate.selectionChanged(action, selection);
			actionDelegate.run(action);
		} else if (entry instanceof IMIFFileEntry) {
			actionDelegate = new EditMIFFileEntryAction();
			actionDelegate.init(getSite().getWorkbenchWindow());
			actionDelegate.selectionChanged(action, selection);
			actionDelegate.run(action);
		} else {
			Check.checkState(false);
		}
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	protected SPNViewActionGroup getActionGroup() {
		return actionGroup;
	}

	protected void setActionGroup(SPNViewActionGroup actionGroup) {
		this.actionGroup = actionGroup;
	}

	public void dispose() {
		if (workspaceListener != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(workspaceListener);
		}

		if (getActionGroup() != null) {
			getActionGroup().dispose();
		}

		IWorkingSetManager wsmanager = getViewSite().getWorkbenchWindow().getWorkbench().getWorkingSetManager();
		wsmanager.removePropertyChangeListener(workingSetListener);

		Control control = viewer.getControl();
		if (dragDetectListener != null && control != null && control.isDisposed() == false) {
			control.removeListener(SWT.DragDetect, dragDetectListener);
		}
		super.dispose();
	}
	
	public TreeViewer getViewer() {
		return viewer;
	}

	public void collapseAll() {
		viewer.getControl().setRedraw(false);
		viewer.collapseToLevel(viewer.getInput(), AbstractTreeViewer.ALL_LEVELS);
		viewer.getControl().setRedraw(true);
	}

	void restoreState(final IMemento memento) {
		contentProvider.restoreTreeStateAndSelection(memento);
		
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				Tree tree = viewer.getTree();
				ScrollBar bar = tree.getVerticalBar();
				if (bar != null) {
					try {
						String posStr = memento.getString(TAG_VERTICAL_POSITION);
						int position = new Integer(posStr).intValue();
						bar.setSelection(position);
					} catch (NumberFormatException e) {
					}
				}
				bar = tree.getHorizontalBar();
				if (bar != null) {
					try {
						String posStr = memento.getString(TAG_HORIZONTAL_POSITION);
						int position = new Integer(posStr).intValue();
						bar.setSelection(position);
					} catch (NumberFormatException e) {
					}
				}
			}
		});
	}

	public void saveState(IMemento memento) {
		if (viewer == null) {
			if (this.memento != null) { //Keep the old state;
				memento.putMemento(this.memento);
			}
			return;
		}

		contentProvider.saveTreeStateAndSelection(memento);

		//save vertical position
		ScrollBar bar = viewer.getTree().getVerticalBar();
		int position = bar != null ? bar.getSelection() : 0;
		memento.putString(TAG_VERTICAL_POSITION, String.valueOf(position));
		//save horizontal position
		bar = viewer.getTree().getHorizontalBar();
		position = bar != null ? bar.getSelection() : 0;
		memento.putString(TAG_HORIZONTAL_POSITION, String.valueOf(position));

		// save the working set away
		if (workingSetFilter.getWorkingSet() != null) {
			String wsname = workingSetFilter.getWorkingSet().getName();
			if (wsname != null) {
				memento.putString(TAG_WORKINGSET, wsname);
			}
		}
	}
	
	/**
	 * Adds drag support to the navigator.
	 */
	void initDrag() {
		Transfer[] transfers = new Transfer[] {
				ResourceTransfer.getInstance(),
				FileTransfer.getInstance()
		};
		viewer.addDragSupport(DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK, transfers, new SPNDragSourceListener(viewer));
	}

	public ShowInContext getShowInContext() {
		IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
		Object element = selection.getFirstElement();
		if (element instanceof IAdaptable) {
			Object resource = ((IAdaptable)element).getAdapter(IResource.class);
			if (resource != null) {
				selection = new StructuredSelection(resource);
			}
		}
		return new ShowInContext(getViewer().getInput(), selection);
	}

	public String[] getShowInTargetIds() {
        return new String[]{ProjectExplorer.VIEW_ID, IPageLayout.ID_RES_NAV};
	}
	
	public void refreshProject(ICProject project) {
		contentProvider.refreshProject(project.getProject());
		viewer.refresh(project);
	}

	public IWorkingSet getWorkingSet() {
		return workingSetFilter.getWorkingSet();
	}

	public void setWorkingSet(IWorkingSet workingSet) {
		TreeViewer treeViewer = getViewer();
		Object[] expanded = treeViewer.getExpandedElements();
		ISelection selection = treeViewer.getSelection();

		workingSetFilter.setWorkingSet(workingSet);

		treeViewer.refresh();
		treeViewer.setExpandedElements(expanded);
		if (selection.isEmpty() == false && selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			treeViewer.reveal(structuredSelection.getFirstElement());
		}
	}
}