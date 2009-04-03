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
package com.nokia.sdt.uidesigner.events;


import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.editor.IDesignerDataModelEditor;
import com.nokia.sdt.uidesigner.events.EventModel.CategoryItem;
import com.nokia.sdt.uidesigner.events.EventModel.EventItem;
import com.nokia.sdt.uidesigner.ui.UIDesignerPlugin;
import com.nokia.sdt.uidesigner.ui.command.DataModelCommandWrapper;
import com.nokia.sdt.uidesigner.ui.utils.Messages;
import com.nokia.sdt.uimodel.UIModelPlugin;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.*;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.jface.action.*;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.progress.IWorkbenchSiteProgressService;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

/**
 * Default implementation of the event editor UI
 */
public class EventPage extends Page implements IEventPage {

	private Shell parentShell;
	private EventView view;
	private EventTreeViewer viewer;
	private EventCellEditor cellEditor;
	EventModel model;
	private Action categoriesAction;
	private Action doubleClickAction;
	private boolean showCategories = true;
	String columnProperties[] = {"EventPage.0", "EventPage.1"}; //$NON-NLS-1$ //$NON-NLS-2$
	private IDesignerDataModelEditor designerDataModelEditor;
	private EventBindingListener eventBindingListener = new EventBindingListener();
	private IStatusLineManager statusLineManager;
	private boolean refreshModelPending;
	private final static String LAST_CONFIG = "EventPage.LastShowCategoriesSetting"; //$NON-NLS-1$
	private final static QualifiedName QUALIFIED_LAST_CONFIG = 
						new QualifiedName(UIDesignerPlugin.getDefault().toString(), LAST_CONFIG);
	
	public EventPage(EventView view) {
		this.view = view;
	}

	public void createControl(Composite parent) {
		parentShell = parent.getShell();
		initializeTreeViewer(parent);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setSorter(new NameSorter());
		viewer.setInput(getSite());
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
		hookTreeSelection();
		hookHelp();
	}
	
	private void initializeTreeViewer(Composite parent) {
		viewer = new EventTreeViewer(parent, SWT.FULL_SELECTION | SWT.SINGLE
                | SWT.HIDE_SELECTION | SWT.V_SCROLL);	
		final Tree tree = viewer.getTree();
		tree.setLinesVisible(true);
		tree.setHeaderVisible(true);
				
		TreeColumn column = new TreeColumn(tree, 0);
		column.setText(Messages.getString(columnProperties[0]));
		column = new TreeColumn(tree, 1);
		column.setText(Messages.getString(columnProperties[1]));
		
		// install a cell editor for column 1. We never edit column 0
		cellEditor = new EventCellEditor(viewer.getTree());
		cellEditor.addListener(new ICellEditorListener() {

			public void applyEditorValue() {
				setErrorMessage(null);
			}

			public void cancelEditor() {
				setErrorMessage(null);
			}

			public void editorValueChanged(boolean oldValidState, boolean newValidState) {
				String message = null;
				if (!newValidState) {
					message = cellEditor.getErrorMessage();
				}
				setErrorMessage(message);
			}
			
		});
		
		CellEditor editors[] = new CellEditor[2];
		editors[1] = cellEditor;
		viewer.setCellEditors(editors);
		viewer.setColumnProperties(columnProperties);
		viewer.setCellModifier(new EventCellModifier(this));
   
		// listen for initial resize to set column widths
        tree.addControlListener(new ControlAdapter() {
            public void controlResized(ControlEvent e) {
                Rectangle area = tree.getClientArea();
                TreeColumn[] columns = tree.getColumns();
                if (area.width > 0) {
                    columns[0].setWidth(area.width * 40 / 100);
                    columns[1].setWidth(area.width - columns[0].getWidth() - 4);
                    tree.removeControlListener(this);
                }
            }
        });
	}
	
	private IDesignerEditor getDesignerEditor() {
		if (designerDataModelEditor != null)
			return (IDesignerEditor) designerDataModelEditor.getAdapter(IDesignerEditor.class);
		
		return null;
	}
	
	/**
	 * Tries to execute the command in the context of the
	 * editor's GEF command stack. If not accessible
	 * then it directly executes the command without undo support
	 */
	boolean executeEMFCommand(Command command) {
		boolean success = true;
		try {
			org.eclipse.gef.commands.CommandStack commandStack = null;
			IDesignerEditor designerEditor = getDesignerEditor();
			if (designerEditor != null) {
				commandStack = designerEditor.getEditDomain().getCommandStack();
			}
			
			if (commandStack != null) {
				DataModelCommandWrapper wrapper = new DataModelCommandWrapper();
				wrapper.setDataModelCommand(command);
				commandStack.execute(wrapper);
			}
			else {
				if (command.canExecute()) {
					command.execute();
				}
			}
		}
		catch (Exception x) {
			IStatus status = Logging.newSimpleStatus(0, x);
			UIDesignerPlugin.getDefault().log(x);
			Logging.showErrorDialog(Messages.getString("EventPage.2"), status.getMessage(), status); //$NON-NLS-1$
			success = false;
		}
		return success;
	}
	
	private void runAsync(Runnable r) {
		Display d = getSite().getShell().getDisplay();
		d.asyncExec(r);
	}
	
	private void hookTreeSelection() {
		viewer.addSelectionChangedListener( new ISelectionChangedListener() {

			/* We can't invoke editing from the selection event, we
			 * must do it asynchronously
			 */
			Object objToEdit;
			private Runnable runnable= new Runnable() {
				public void run() {
					if (objToEdit != null && !viewer.getControl().isDisposed()) {						
						Object tmp = objToEdit;
						objToEdit = null;
						cellEditor.setDescriptor(null);
						//viewer.editElement(tmp, 1);
						if (tmp instanceof EventModel.EventItem) {
							EventModel.EventItem item = (EventModel.EventItem) tmp;
							String description = item.descriptor.getDescription();
							EventPage.this.statusLineManager.setMessage(description);
							cellEditor.setDescriptor(item.descriptor);
							if (item.binding == null) {
								cellEditor.markEphemeral();
							}
						}
					
					}
				}
			};
		
			public void selectionChanged(SelectionChangedEvent event) {
				objToEdit = getSelectedEventItem(event.getSelection());
				if (objToEdit != null) {
					EventPage.this.runAsync(runnable);						
				}
			}
		});
	}
	
	private void hookHelp() {
		viewer.getControl().addHelpListener(new HelpListener() {
			public void helpRequested(HelpEvent e) {
				EventModel.EventItem item = getSelectedEventItem(viewer.getSelection());
				if (item != null) {
					String helpKey = item.descriptor.getHelpKey();
					if (TextUtils.strlen(helpKey) > 0) {
                        getSite().getWorkbenchWindow().getWorkbench()
						.getHelpSystem().displayHelp(helpKey);
					}
				}
			}
		});
	}
	
	private EventModel.EventItem getSelectedEventItem(ISelection selection) {
		EventModel.EventItem result = null;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			Object obj = ss.getFirstElement();
			if (obj instanceof EventModel.EventItem) {
				result = (EventItem) obj;
			}
		}
		return result;
	}

	public Control getControl() {
		Control result = null;
		if (viewer != null) {
			result = viewer.getControl();
		}
		return result;
	}

	public void setFocus() {
		if (viewer != null) {
			viewer.getControl().setFocus();
		}
	}
		
	/*
	 * Tries to convert the object into an IComponentInstance
	 */
	private static IComponentInstance convertToInstance(Object obj)  {
		IComponentInstance result = null;
		if (obj instanceof IComponentInstance) {
			result = (IComponentInstance) obj;
		}
		else if (obj instanceof EObject) {
			result = ModelUtils.getComponentInstance((EObject)obj);
		}
		else if (obj instanceof IAdaptable) {
			IAdaptable adaptable = (IAdaptable) obj;
			result = (IComponentInstance) adaptable.getAdapter(IComponentInstance.class);
		}
		return result;
	}

	private IComponentInstance[] getInstancesFromSelection(ISelection selection) {
		IComponentInstance[] result = null;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
	
			// We must be able to get an IComponentInstance for each
			// object in the selection
			Object[] objects = ss.toArray();
			
			boolean selectionContainsNonInstance = false;
			// the same object can be selected in two parts of the editor, so use a set
			Set<IComponentInstance> instances = new HashSet<IComponentInstance>();
			for (int i = 0; i < objects.length; i++) {
				IComponentInstance ci = convertToInstance(objects[i]);
				if (ci != null) {
					instances.add(ci);
				}
				else {
					selectionContainsNonInstance = true;
					break;
				}
			}
			
			if (!selectionContainsNonInstance && (instances.size() > 0)) {
				result = (IComponentInstance[]) 
						instances.toArray(new IComponentInstance[instances.size()]);
			}
		}
		return result;	
	}

	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// the selection has changed in some other workbench part
		if (designerDataModelEditor == null) {
			return; // don't show anything if this is the default page
		}
			
		if (viewer != null) {
			EventModel newModel = null;
			IComponentInstance[] instances = getInstancesFromSelection(selection);
			if (instances != null) {
				newModel = new EventModel(instances);
			}
			setModel(newModel, true);
		}
	}
	
	class ViewContentProvider implements IStructuredContentProvider, 
										   ITreeContentProvider {

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			Object[] result = null;
			if (model != null) {
				if (parent.equals(getSite())) {
					if (showCategories) {
						result = model.getCategoryItems();
					}
					else {
						result = model.getEventItems();
					}
				}
				else if (parent instanceof EventModel.CategoryItem) {
					EventModel.CategoryItem item = (CategoryItem) parent;
					result = item.events;
				}
			}
			
			if (result == null)
				result = new Object[0];
			return result;
		}
		
		public Object getParent(Object child) {
			Object result = null;
			if (showCategories && model != null && child instanceof EventModel.EventItem) {
				result = model.findCategoryItem(((EventModel.EventItem)child));
			}
			return result;
		}
		public Object [] getChildren(Object parent) {
			Object result[] = null;
			if (parent instanceof EventModel.CategoryItem) {
				EventModel.CategoryItem item = (CategoryItem) parent;
				result = item.events;
			}
			return result;
		}
		public boolean hasChildren(Object parent) {
			boolean result = false;
			if (parent instanceof EventModel.CategoryItem) {
				EventModel.CategoryItem item = (EventModel.CategoryItem) parent;
				result = item.events != null;
			}
			return result;
		}
	}

	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
	
		public String getColumnText(Object element, int columnIndex) {
			String result = null;
			if (element instanceof EventModel.CategoryItem) {
				if (columnIndex == 0) {
					EventModel.CategoryItem item = (EventModel.CategoryItem) element;
					result = item.category;
				}
			}
			else if (element instanceof EventModel.EventItem) {
				EventModel.EventItem item = (EventModel.EventItem) element;
				if (columnIndex == 0) {
					result = item.descriptor.getDisplayText();
				}
				else if (item.binding != null){
					result = item.binding.getHandlerName();
				}
			}
			return result;
		}
		
		public String getText(Object element) {
			// getText is called for sorting
			return getColumnText(element, 0);
		}

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
	}

	class NameSorter extends ViewerSorter {
		
	}

	public void associateDesignerEditor(IDesignerDataModelEditor editor) {
		this.designerDataModelEditor = editor;
		IDesignerEditor designerEditor = getDesignerEditor();
		boolean savedShowCategories = (designerEditor != null) && getSavedShowCategories(designerEditor);
		showCategories(savedShowCategories);
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				EventPage.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
	//	getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
		
		statusLineManager = bars.getStatusLineManager();
		
		IDesignerEditor designerEditor = getDesignerEditor();
		if (designerEditor != null) {
			// Add the editor's revert, undo, and redo.
			ActionRegistry registry = designerEditor.getActionRegistry();
			IAction action = registry.getAction(ActionFactory.REVERT.getId());
			bars.setGlobalActionHandler(action.getId(), action);
			action = registry.getAction(ActionFactory.UNDO.getId());
			bars.setGlobalActionHandler(action.getId(), action);
			action = registry.getAction(ActionFactory.REDO.getId());
			bars.setGlobalActionHandler(action.getId(), action);
			bars.updateActionBars();
		}
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(categoriesAction);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(categoriesAction);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(categoriesAction);
	}

	private void makeActions() {
		categoriesAction = new CategoriesAction(this);
		categoriesAction.setChecked(isShowingCategories());
	
		// The behavior of double-click is:
		// - create a binding if none exists
		// - navigate to binding
		doubleClickAction = new Action() {
			public void run() {
				EventModel.EventItem item = getSelectedEventItem(viewer.getSelection());
				if (item != null) {
					viewer.cancelEditing();
					IEventBinding binding = item.binding;
					boolean isNewBinding = false;
					if (binding == null) {
						IComponentInstance instances[] = model.getInstances();
						IDesignerDataModel model = instances[0].getDesignerDataModel();
						isNewBinding = true;
						Command command = EventCommands.createEventBindingCommand(
								instances, item.descriptor, null);
						if (command != null) {
							executeEMFCommand(command);
							binding = EventCommands.getEventBindingFromCommandResult(command);
							
							boolean doSave = EventCommands.userConfirmationToSaveModelDialog(
											parentShell, model);
							if (!doSave || !saveDataModel()) {
								binding = null;
							}
						}
					}
					if (binding != null) {
						EventCommands.navigateToHandlerCode(EventPage.this, binding, isNewBinding);
					}
				}
			}
		};
		cellEditor.setDoubleClickAction(doubleClickAction);
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	public boolean isShowingCategories() {
		return showCategories;
	}

	public void showCategories(boolean show) {
		if (show != this.showCategories) {
			this.showCategories = show;
			refresh();
			saveShowCategories(getDesignerEditor(), show);
		}
	}
	
	public static void saveShowCategories(IDesignerEditor designerEditor, boolean show) {
		UIModelPlugin.getDefault().getPreferenceStore().setValue(LAST_CONFIG, show);
		// save in the resource
		if (designerEditor != null) {
			IResource resource = designerEditor.getDataModelResource();
			try {
				resource.setPersistentProperty(QUALIFIED_LAST_CONFIG, String.valueOf(show));
			} 
			catch (CoreException e) {} // don't do anything if this fails
		}
	}
	
	public static boolean getSavedShowCategories(IDesignerEditor designerEditor) {
		String savedStateString = null;
		
		// first try the resource
		IResource resource = designerEditor.getDataModelResource();
		try {
			savedStateString = resource.getPersistentProperty(QUALIFIED_LAST_CONFIG);
		} 
		catch (CoreException e) {} // don't do anything if this fails

		// then try the plugin setting
		if ((savedStateString == null) || (savedStateString.length() == 0))
			savedStateString = UIModelPlugin.getDefault().getPreferenceStore().getString(LAST_CONFIG);

		// if still no saved setting, then default to true
		if ((savedStateString == null) || (savedStateString.length() == 0))
			return true;
			
		return Boolean.parseBoolean(savedStateString);
	}
	
	void refresh() {
		if (viewer != null) {
			viewer.refresh();
			viewer.expandAll();
		}
	}
	
	public void dispose() {
		setModel(null, false);
		super.dispose();
	}

	void refreshModel(IComponentInstance[] instances) {
		if (model != null) {
			EventModel newModel = new EventModel(model.getInstances());
			setModel(newModel, true);
		}
	}
	
	void asynchRefreshModel() {
		Runnable r = new Runnable() {
			public void run() {
				EventPage.this.refreshModelPending = false;
				if (!viewer.getControl().isDisposed()) {
					if (model != null) {
						// attempt to preserve selected event
						String selectedEventID = null;
						EventModel.EventItem currSelected = getSelectedEventItem(viewer.getSelection());
						if (currSelected != null) {
							selectedEventID = currSelected.descriptor.getId();
						}

						refreshModel(model.getInstances());
						
						if (selectedEventID != null) {
							EventItem item = model.findEventItem(selectedEventID);
							if (item != null) {
								selectItem(item);
							}
						}

					}
				}
			}
		};
		// This is meant to avoid multiple refreshes queued on the
		// event thread (to avoid flashing). Thread safety is not needed
		if (!refreshModelPending) {
			refreshModelPending = true;
			runAsync(r);
		}
	}
	
	/**
	 * Tell Eclipse the content changed. In the default presentation
	 * this temporarily bolds the view's title tab.
	 */
	private void markViewChanged() {
		IWorkbenchPartSite site = view.getSite();
		IWorkbenchSiteProgressService wsps = 
			(IWorkbenchSiteProgressService)site.getAdapter(IWorkbenchSiteProgressService.class);
		if (wsps != null) {
			wsps.warnOfContentChange();
	 	}
	}
	
	private void setModel(EventModel newModel, boolean refresh) {
		IComponentInstance instances[];
		if (model != null) {
			instances = model.getInstances();
			for (int i = 0; i < instances.length; i++) {
				IComponentInstance instance = instances[i];
				instance.removeEventBindingListener(eventBindingListener);
			}
		}
		this.model = newModel;
		if (model != null) {
			instances = model.getInstances();
			for (int i = 0; i < instances.length; i++) {
				IComponentInstance instance = instances[i];
				instance.addEventBindingListener(eventBindingListener);
			}
		}
		if (refresh) {
			refresh();
		}
	}

	public void deactivateCellEditor() {
		viewer.cancelEditing();
	}
	
	boolean cellEditorIsDirty() {
		// we only ever edit column 1
		return viewer.getCellEditors()[1].isDirty();
	}
	
	void setErrorMessage(String message) {
		if (statusLineManager != null) {
			statusLineManager.setErrorMessage(message);
		}
	}
		
	// All view updating is driven by these events
	class EventBindingListener implements IEventBindingListener {
		public void bindingAdded(EObject instance, IEventBinding eventBinding) {
			asynchRefreshModel();
			markViewChanged();			
		}
		
		public void bindingRemoved(EObject instance, IEventBinding eventBinding) {
			asynchRefreshModel();			
		}
	}

	public Shell getParentShell() {
		return parentShell;
	}
	
	boolean saveDataModel() {
		/*
		UIJob job = new UIJob(Messages.getString("EventPage.3")) { //$NON-NLS-1$
			public IStatus runInUIThread(IProgressMonitor monitor) {
				designerEditor.doSave(monitor);
				return Logging.newStatus(UIDesignerPlugin.getDefault(), IStatus.OK, null);
			}
		};
		job.setUser(true);
		job.schedule();
		return true;*/
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				designerDataModelEditor.doSave(monitor);
			}
		};
		//ProgressMonitorDialog dialog = new ProgressMonitorDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		//dialog.run(false, true, runnable);
		try {
			PlatformUI.getWorkbench().getProgressService().runInUI(PlatformUI.getWorkbench().getActiveWorkbenchWindow(), runnable, null);
			return true;
		} catch (InvocationTargetException e) {
			UIDesignerPlugin.getDefault().log(e);
			return false;
		} catch (InterruptedException e) {
			return false;
		}
	}
	
	public void selectItem(EventItem item) {
		viewer.setSelection(new StructuredSelection(item));
	}
}
