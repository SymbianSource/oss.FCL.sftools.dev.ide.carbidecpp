/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.remoteconnections.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.LegacyActionTools;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeColumnViewerLabelProvider;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.TreeViewerEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.nokia.carbide.remoteconnections.Messages;
import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatusChangedListener;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus.EStatus;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager.IConnectionsManagerListener;
import com.nokia.carbide.remoteconnections.internal.api.IConnection2;
import com.nokia.carbide.remoteconnections.internal.registry.Registry;
import com.nokia.carbide.remoteconnections.internal.ui.ConnectionUIUtils;
import com.nokia.carbide.remoteconnections.settings.ui.SettingsWizard;
import com.nokia.cpp.internal.api.utils.core.TextUtils;


/**
 * The view part for Remote connections
 */
@SuppressWarnings("deprecation")
public class ConnectionsView extends ViewPart {
	public static final String VIEW_ID = "com.nokia.carbide.remoteconnections.view.ConnectionsView"; //$NON-NLS-1$

	private TreeViewer viewer;
	private IConnectionsManagerListener connectionStoreChangedListener;
	private Map<IConnectedService, IStatusChangedListener> serviceToListenerMap;
	private List<Action> actions;
	private List<Action> connectionSelectedActions;
	private List<Action> serviceSelectedActions;
	private static final String UID = ".uid"; //$NON-NLS-1$

	private static final ImageDescriptor CONNECTION_NEW_IMGDESC = RemoteConnectionsActivator.getImageDescriptor("icons/connectionNew.png"); //$NON-NLS-1$
	private static final ImageDescriptor CONNECTION_EDIT_IMGDESC = RemoteConnectionsActivator.getImageDescriptor("icons/connectionEdit.png"); //$NON-NLS-1$
	private static final ImageDescriptor SERVICE_TEST_IMGDESC = RemoteConnectionsActivator.getImageDescriptor("icons/serviceTest.png"); //$NON-NLS-1$
	private static final ImageDescriptor CONNECTION_REFRESH_IMGDESC = RemoteConnectionsActivator.getImageDescriptor("icons/connectionRefresh.png"); //$NON-NLS-1$

	private static final String NEW_ACTION = "ConnectionsView.new"; //$NON-NLS-1$
	private static final String EDIT_ACTION = "ConnectionsView.edit"; //$NON-NLS-1$
	private static final String RENAME_ACTION = "ConnectionsView.rename"; //$NON-NLS-1$
	private static final String ENABLE_SERVICE_ACTION = "ConnectionsView.enableService"; //$NON-NLS-1$
	private static final String REFRESH_ACTION = "ConnectionsView.refresh"; //$NON-NLS-1$
	private static final String DELETE_ACTION = "ConnectionsView.delete"; //$NON-NLS-1$
	private static final String HELP_ACTION = "ConnectionsView.help"; //$NON-NLS-1$
	private KeyAdapter keyListener;
	private boolean isDisposed;
	
	private TreeNode[] loadConnections() {
		if (serviceToListenerMap == null)
			serviceToListenerMap = new HashMap<IConnectedService, IStatusChangedListener>();
		removeServiceListeners();
		List<TreeNode> connectionNodes = new ArrayList<TreeNode>();
		Collection<IConnection> connections = 
			Registry.instance().getConnections();
		for (IConnection connection : connections) {
			// create a node for the connection
			TreeNode connectionNode = new TreeNode(connection);
			// create subnodes for the connected services
			List<TreeNode> serviceNodes = new ArrayList<TreeNode>();
			Collection<IConnectedService> connectedServicesForConnection = 
				Registry.instance().getConnectedServices(connection);
			for (IConnectedService connectedService : connectedServicesForConnection) {
				final TreeNode treeNode = new TreeNode(connectedService);
				IStatusChangedListener statusChangedListener = new IStatusChangedListener() {
					public void statusChanged(final IStatus status) {
						Display.getDefault().asyncExec(new Runnable() {
							public void run() {
								if (!isDisposed) {
									viewer.refresh(true);
									packColumns();
								}
							}
						});
					}
				};
				connectedService.addStatusChangedListener(statusChangedListener);
				serviceToListenerMap.put(connectedService, statusChangedListener);
				connectedService.setEnabled(true);
				serviceNodes.add(treeNode);
			}
			for (TreeNode serviceNode : serviceNodes) {
				serviceNode.setParent(connectionNode);
			}
			connectionNode.setChildren((TreeNode[]) serviceNodes.toArray(new TreeNode[serviceNodes.size()]));
			connectionNodes.add(connectionNode);
		}
		
		return (TreeNode[]) connectionNodes.toArray(new TreeNode[connectionNodes.size()]);
	}

	private class NameEditingSupport extends EditingSupport {
		private TextCellEditor editor;

		private NameEditingSupport(ColumnViewer viewer) {
			super(viewer);
			editor = new TextCellEditor((Composite) viewer.getControl(), SWT.BORDER);
		}
		
		@Override
		protected boolean canEdit(Object element) {
			Object object = ((TreeNode) element).getValue();
			if (object instanceof IConnection && !isDynamicConnection(object))
				return true;
			return false;
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			return editor;
		}

		@Override
		protected Object getValue(Object element) {
			IConnection connection = (IConnection) ((TreeNode) element).getValue();
			return connection.getDisplayName();
		}

		@Override
		protected void setValue(Object element, Object value) {
			IConnection connection = (IConnection) ((TreeNode) element).getValue();
			connection.setDisplayName(value.toString());
			//viewer.refresh(true);
			//packColumns();
			Registry.instance().updateDisplays();
			Registry.instance().storeConnections();
		}
	}

	private class NameLabelProvider extends ColumnLabelProvider {

		public String getText(Object obj) {
			return getNodeDisplayName(obj);
		}

		public Image getImage(Object obj) {
			TreeNode node = (TreeNode) obj;
			Object value = node.getValue();
			if (value instanceof IConnection) {
				return ConnectionUIUtils.getConnectionImage((IConnection) value);
			}
			else if (value instanceof IConnectedService) {
				EStatus status = ((IConnectedService) value).getStatus().getEStatus();
				IConnection connection = findConnection((IConnectedService) value);
				if (connection != null && ConnectionUIUtils.isSomeServiceInUse(connection))
					status = EStatus.IN_USE;
				return ConnectionUIUtils.getConnectedServiceStatusImage(status);
			}
			return null;
		}
	}
	
	private class StatusLabelProvider extends ColumnLabelProvider {
		public String getText(Object obj) {
			TreeNode node = (TreeNode) obj;
			Object value = node.getValue();
			if (value instanceof IConnectedService) {
				IStatus status = null;
				IConnection connection = findConnection((IConnectedService) value);
				if (connection != null)
					status = ConnectionUIUtils.getFirstInUseServiceStatus(connection);
				if (status == null) {
					status = ((IConnectedService) value).getStatus();
					return status.getShortDescription();
				}
			}
			else if (value instanceof IConnection) {
				IStatus status = ConnectionUIUtils.getFirstInUseServiceStatus((IConnection) value);
				if (status != null)
					return status.getShortDescription();
			}
				
			return null;
		}

		@Override
		public Color getForeground(Object obj) {
			TreeNode node = (TreeNode) obj;
			Object value = node.getValue();
			if (value instanceof IConnectedService) {
				EStatus status = ((IConnectedService) value).getStatus().getEStatus();
				switch (status) {
				case DOWN:
					return ConnectionUIUtils.COLOR_RED;
				case UP:
					return ConnectionUIUtils.COLOR_GREEN;
				case UNKNOWN:
					return ConnectionUIUtils.COLOR_GREY;
				}
			}
			else if (value instanceof IConnection) // only showing in-use for connections
				return ConnectionUIUtils.COLOR_ELECTRIC;
			
			return null;
		}
	}
	
	public class DescriptionLabelProvider extends ColumnLabelProvider {
		
		@Override
		public String getText(Object obj) {
			TreeNode node = (TreeNode) obj;
			Object value = node.getValue();
			if (value instanceof IConnectedService) {
				IStatus status = ((IConnectedService) value).getStatus();
				IConnection connection = findConnection((IConnectedService) value);
				if (!status.getEStatus().equals(EStatus.IN_USE) ||
						!(connection != null && ConnectionUIUtils.isSomeServiceInUse(connection))) { // if in-use, we show it in the connection row
					String longDescription = status.getLongDescription();
					if (longDescription != null)
						longDescription = TextUtils.canonicalizeNewlines(longDescription, " "); //$NON-NLS-1$
					return longDescription;
				}
			}
			else if (value instanceof IConnection) {
				if (ConnectionUIUtils.isSomeServiceInUse((IConnection) value)) {
					return Messages.getString("ConnectionsView.InUseDesc");
				}
			}
			
			return null;
		}
	}

	private class TypeLabelProvider extends ColumnLabelProvider {
		
		public String getText(Object obj) {
			TreeNode node = (TreeNode) obj;
			Object value = node.getValue();
			if (value instanceof IConnection) {
				return ((IConnection) value).getConnectionType().getDisplayName();
			}
			return null;
		}
	}
	
	private class EnableConnectedServiceAction extends Action {
		private EnableConnectedServiceAction() {
			super("", SERVICE_TEST_IMGDESC); //$NON-NLS-1$
		}

		private IConnectedService getSelectedConnectedService() {
			ISelection selection = viewer.getSelection();
			if (selection.isEmpty())
				return null;
			TreeNode node = (TreeNode) ((IStructuredSelection) selection).getFirstElement();
			Object value = node.getValue();
			if (value instanceof IConnectedService) {
				return (IConnectedService) value;
			}
			return null;
		}

		@Override
		public void run() {
			IConnectedService connectedService = getSelectedConnectedService();
			if (connectedService != null) {
				connectedService.setEnabled(!connectedService.isEnabled());
				updateLabel();
			}
		}

		public void updateLabel() {
			IConnectedService connectedService = getSelectedConnectedService();
			if (connectedService == null)
				return;
			String label = connectedService.isEnabled() ?
					Messages.getString("ConnectionsView.DisableTestActionLabel") : //$NON-NLS-1$
					Messages.getString("ConnectionsView.EnableTestActionLabel"); //$NON-NLS-1$
			setText(label);
		}

		@Override
		public void setEnabled(boolean enabled) {
			super.setEnabled(enabled);
			updateLabel();
		}

		@Override
		public boolean isEnabled() {
			IConnectedService connectedService = getSelectedConnectedService();
			return connectedService != null && connectedService.getService().isTestable();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		
		TreeViewerColumn nameColumn = new TreeViewerColumn(viewer, SWT.LEFT);
		nameColumn.setLabelProvider(new TreeColumnViewerLabelProvider(new NameLabelProvider()));
		nameColumn.getColumn().setText(Messages.getString("ConnectionsView.NameColumnHeader")); //$NON-NLS-1$
		nameColumn.setEditingSupport(new NameEditingSupport(nameColumn.getViewer()));
		ColumnViewerEditorActivationStrategy activationStrategy = new ColumnViewerEditorActivationStrategy(nameColumn.getViewer()) {
			@Override
			protected boolean isEditorActivationEvent(ColumnViewerEditorActivationEvent event) {
				return event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC;
			}
		};
		TreeViewerEditor.create(viewer, activationStrategy, ColumnViewerEditor.DEFAULT);

		TreeViewerColumn typeColumn = new TreeViewerColumn(viewer, SWT.LEFT);
		typeColumn.setLabelProvider(new TypeLabelProvider());
		typeColumn.getColumn().setText(Messages.getString("ConnectionsView.TypeColumnHeader")); //$NON-NLS-1$
		
		TreeViewerColumn statusColumn = new TreeViewerColumn(viewer, SWT.LEFT);
		statusColumn.setLabelProvider(new StatusLabelProvider());
		statusColumn.getColumn().setText(Messages.getString("ConnectionsView.StatusColumnHeader")); //$NON-NLS-1$
		
		TreeViewerColumn descriptionColumn = new TreeViewerColumn(viewer, SWT.LEFT);
		descriptionColumn.setLabelProvider(new DescriptionLabelProvider());
		descriptionColumn.getColumn().setText(Messages.getString("ConnectionsView.DescriptionColumnHeader")); //$NON-NLS-1$
		
		viewer.setContentProvider(new TreeNodeContentProvider());
		viewer.setInput(loadConnections());
		viewer.expandAll();
		viewer.getTree().setHeaderVisible(true);
		viewer.getControl().setData(UID, "viewer"); //$NON-NLS-1$
		viewer.setSorter(new ViewerSorter() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				return getNodeDisplayName(e1).compareToIgnoreCase(getNodeDisplayName(e2));
			}
		});
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				enableConnectionSelectedActions(false);
				enableServiceSelectedActions(false);
				ISelection selection = event.getSelection();
				if (!selection.isEmpty()) {
					IStructuredSelection structuredSelection = (IStructuredSelection) selection;
					TreeNode treeNode = (TreeNode) structuredSelection.getFirstElement();
					Object value = treeNode.getValue();
					if (value instanceof IConnection) {
						enableConnectionSelectedActions(true);
					}
					else if (value instanceof IConnectedService) {
						enableServiceSelectedActions(true);
					}
				}
			}
		});
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = event.getSelection();
				if (!selection.isEmpty()) {
					IStructuredSelection structuredSelection = (IStructuredSelection) selection;
					TreeNode treeNode = (TreeNode) structuredSelection.getFirstElement();
					Object value = treeNode.getValue();
					if (value instanceof IConnection) {
						SettingsWizard wizard = new SettingsWizard((IConnection) value);
						wizard.open(getViewSite().getShell());
					}
					else if (value instanceof IConnectedService) {
						IConnectedService connectedService = (IConnectedService) value;
						connectedService.setEnabled(true);
						connectedService.testStatus();
						((EnableConnectedServiceAction) getAction(ENABLE_SERVICE_ACTION)).updateLabel();
					}
				}
			}
		});
		
		packColumns();
		
		makeActions();
		hookContextMenu();
		contributeToActionBars();
		hookAccelerators();
		
		connectionStoreChangedListener = new IConnectionsManagerListener() {
			public void connectionStoreChanged() {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						viewer.setInput(loadConnections());
						viewer.expandAll();
						packColumns();
						if (viewer.getSelection().isEmpty() && viewer.getTree().getItemCount() > 0) {
							TreeItem item = viewer.getTree().getItem(0);
							if (item != null) {
								viewer.getTree().select(item);
							}
						}
						viewer.setSelection(viewer.getSelection()); // force selection changed
					}
				});
			}

			public void displayChanged() {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						viewer.refresh(true);
						packColumns();
					}
				});
			}
		};
		Registry.instance().addConnectionStoreChangedListener(connectionStoreChangedListener);

		RemoteConnectionsActivator.setHelp(parent, ".connections_view"); //$NON-NLS-1$
	}

	private void packColumns() {
		TreeColumn[] columns = viewer.getTree().getColumns();
		for (TreeColumn column : columns) {
			column.pack();
		}
	}

	private String getNodeDisplayName(Object obj) {
		Object value = ((TreeNode) obj).getValue();
		if (value instanceof IConnection)
			return ((IConnection) value).getDisplayName();
		else if (value instanceof IConnectedService)
			return ((IConnectedService) value).getService().getDisplayName();

		return ""; //$NON-NLS-1$
	}
	
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				ConnectionsView.this.fillContextMenu(manager);
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
		// nothing for now
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(getAction(NEW_ACTION));
		ISelection selection = viewer.getSelection();
		if (selection.isEmpty())
			return;
		TreeNode node = (TreeNode) ((IStructuredSelection) selection).getFirstElement();
		Object value = node.getValue();
		if (value instanceof IConnectedService)
			manager.add(getAction(ENABLE_SERVICE_ACTION));
		else {
			manager.add(getAction(RENAME_ACTION));
			manager.add(getAction(EDIT_ACTION));
			manager.add(getAction(DELETE_ACTION));
			manager.add(getAction(HELP_ACTION));
		}
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(getAction(REFRESH_ACTION));
		manager.add(getAction(NEW_ACTION));
		manager.add(getAction(EDIT_ACTION));
	}

	private void makeActions() {
		actions = new ArrayList<Action>();
		connectionSelectedActions = new ArrayList<Action>();
		serviceSelectedActions = new ArrayList<Action>();
		
		Action action = new Action(Messages.getString("ConnectionsView.NewActionLabel"), CONNECTION_NEW_IMGDESC) { //$NON-NLS-1$
			@Override
			public void run() {
				SettingsWizard wizard = new SettingsWizard();
				wizard.open(getViewSite().getShell());
			}
			
		};
		action.setId(NEW_ACTION);
		actions.add(action);
		action.setEnabled(!Registry.instance().getConnectionTypes().isEmpty());
		
		String editLabel = Messages.getString("ConnectionsView.EditActionLabel");
		action = new Action(editLabel, CONNECTION_EDIT_IMGDESC) { //$NON-NLS-1$
			@Override
			public void run() {
				ISelection selection = viewer.getSelection();
				if (selection.isEmpty())
					return;
				TreeNode node = (TreeNode) ((IStructuredSelection) selection).getFirstElement();
				Object value = node.getValue();
				if (value instanceof IConnection) {
					SettingsWizard wizard = new SettingsWizard((IConnection) value);
					wizard.open(getViewSite().getShell());
				}
			}
		};
		action.setId(EDIT_ACTION);
		actions.add(action);
		connectionSelectedActions.add(action);
		
		action = new Action() {
			@Override
			public void run() {
				ISelection selection = viewer.getSelection();
				if (selection.isEmpty())
					return;
				TreeNode node = (TreeNode) ((IStructuredSelection) selection).getFirstElement();
				Object value = node.getValue();
				if (value instanceof IConnection) {
					viewer.editElement(node, 0);
				}
			}

			@Override
			public boolean isEnabled() {
				return selectionCanBeEdited();
			}
		};
		action.setId(RENAME_ACTION);
		action.setAccelerator(SWT.F2);
		action.setText(Messages.getString("ConnectionsView.RenameMenuLabel") +  //$NON-NLS-1$
				"\t" +  //$NON-NLS-1$
				LegacyActionTools.convertAccelerator(action.getAccelerator()));
		actions.add(action);
		connectionSelectedActions.add(action);
		
		action = new EnableConnectedServiceAction();
		action.setId(ENABLE_SERVICE_ACTION);
		actions.add(action);
		serviceSelectedActions.add(action);
		
		action = new Action(Messages.getString("ConnectionsView.RefreshActionLabel"), CONNECTION_REFRESH_IMGDESC) { //$NON-NLS-1$
			@Override
			public void run() {
				IConnectionsManager connectionsManager = Registry.instance();
				for (IConnection connection : connectionsManager.getConnections()) {
					Collection<IConnectedService> connectedServices = connectionsManager.getConnectedServices(connection);
					for (IConnectedService connectedService : connectedServices) {
						connectedService.setEnabled(true);
						connectedService.testStatus();
					}
				}
				((EnableConnectedServiceAction) getAction(ENABLE_SERVICE_ACTION)).updateLabel();
			}
		};
		action.setAccelerator(SWT.F5);
		action.setId(REFRESH_ACTION);
		actions.add(action);
		
		action = new Action(Messages.getString("ConnectionsView.DeleteActionLabel"),  //$NON-NLS-1$
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_DELETE)) {
			@Override
			public void run() {
				ISelection selection = viewer.getSelection();
				if (selection.isEmpty())
					return;
				TreeNode node = (TreeNode) ((IStructuredSelection) selection).getFirstElement();
				Object value = node.getValue();
				if (value instanceof IConnection) {
					Registry.instance().removeConnection((IConnection) value);
					Registry.instance().storeConnections();
				}
			}

			@Override
			public boolean isEnabled() {
				return selectionCanBeEdited();
			}
		};
		action.setAccelerator(SWT.DEL);
		action.setId(DELETE_ACTION);
		actions.add(action);
		connectionSelectedActions.add(action);
		
		Image image = JFaceResources.getImage(org.eclipse.jface.dialogs.Dialog.DLG_IMG_HELP);
		ImageDescriptor desc = ImageDescriptor.createFromImage(image);
		action = new Action(Messages.getString("ConnectionsView.HelpActionLabel"), desc) { //$NON-NLS-1$
			
			private String getHelpContextFromSelection() {
				ISelection selection = viewer.getSelection();
				if (!selection.isEmpty()) {
					TreeNode treeNode = (TreeNode) ((IStructuredSelection) selection).getFirstElement();
					Object value = treeNode.getValue();
					if (value instanceof IConnection) {
						return ((IConnection) value).getConnectionType().getHelpContext();
					}
				}
				return null;
			}
			
			@Override
			public boolean isEnabled() {
				return getHelpContextFromSelection() != null;
			}

			@Override
			public void run() {
				PlatformUI.getWorkbench().getHelpSystem().displayHelp(getHelpContextFromSelection());
			}
		};
		action.setId(HELP_ACTION);
		actions.add(action);
		connectionSelectedActions.add(action);
		enableConnectionSelectedActions(false);
		enableServiceSelectedActions(false);
	}
	
	private void enableConnectionSelectedActions(boolean enable) {
		for (Action action : connectionSelectedActions) {
			action.setEnabled(enable);
		}
	}
	
	private void enableServiceSelectedActions(boolean enable) {
		for (Action action : serviceSelectedActions) {
			action.setEnabled(enable);
		}
	}
	
	private Action getAction(String id) {
		for (Action action : actions) {
			if (action.getId().equals(id))
				return action;
		}
		
		return null;
	}

	private void hookAccelerators() {
		keyListener = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				for (Action action : actions) {
					if (e.keyCode == action.getAccelerator()) {
						action.run();
						break;
					}
				}
			}
		};
		viewer.getControl().addKeyListener(keyListener);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
	
	private void removeServiceListeners() {
		for (IConnectedService connectedService : serviceToListenerMap.keySet()) {
			IStatusChangedListener listener = serviceToListenerMap.get(connectedService);
			connectedService.removeStatusChangedListener(listener);
		}
		serviceToListenerMap.clear();
	}
	
	private void disableAllConnectedServices() {
		Collection<IConnection> connections = 
			Registry.instance().getConnections();
		for (IConnection connection : connections) {
			Collection<IConnectedService> connectedServicesForConnection = 
				Registry.instance().getConnectedServices(connection);
			for (IConnectedService connectedService : connectedServicesForConnection) {
				connectedService.setEnabled(false);
			}
		}
	}
	
	@Override
	public void dispose() {
		removeServiceListeners();
		Registry.instance().removeConnectionStoreChangedListener(connectionStoreChangedListener);
		disableAllConnectedServices();
		isDisposed = true;
		super.dispose();
	}
	
	private static IConnection findConnection(IConnectedService cs) {
		for (IConnection connection : Registry.instance().getConnections()) {
			for (IConnectedService connectedService : Registry.instance().getConnectedServices(connection)) {
				if (cs.equals(connectedService))
					return connection;
			}
		}
		return null;
	}

	private boolean isDynamicConnection(Object object) {
		return object instanceof IConnection2 && ((IConnection2) object).isDynamic();
	}

	private boolean selectionCanBeEdited() {
		ISelection selection = viewer.getSelection();
		if (selection.isEmpty())
			return false;
		TreeNode node = (TreeNode) ((IStructuredSelection) selection).getFirstElement();
		return !isDynamicConnection(node.getValue());
	}
}

