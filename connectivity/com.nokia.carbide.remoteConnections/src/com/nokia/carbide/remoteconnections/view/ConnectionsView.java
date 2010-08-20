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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.LegacyActionTools;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
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
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
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
import org.eclipse.swt.graphics.Font;
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
import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator.IDiscoveryAgentsLoadedListener;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus.EStatus;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatusChangedListener;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionType;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager.IConnectionListener;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager.IConnectionsManagerListener;
import com.nokia.carbide.remoteconnections.internal.ToggleDiscoveryAgentAction;
import com.nokia.carbide.remoteconnections.internal.api.IConnection2;
import com.nokia.carbide.remoteconnections.internal.api.IToggleServicesTestingListener;
import com.nokia.carbide.remoteconnections.internal.api.IConnection2.IConnectionStatus;
import com.nokia.carbide.remoteconnections.internal.api.IConnection2.IConnectionStatus.EConnectionStatus;
import com.nokia.carbide.remoteconnections.internal.api.IConnection2.IConnectionStatusChangedListener;
import com.nokia.carbide.remoteconnections.internal.api.IDeviceDiscoveryAgent;
import com.nokia.carbide.remoteconnections.internal.registry.Registry;
import com.nokia.carbide.remoteconnections.internal.ui.ConnectionUIUtils;
import com.nokia.carbide.remoteconnections.settings.ui.SettingsWizard;
import com.nokia.cpp.internal.api.utils.core.ObjectUtils;
import com.nokia.cpp.internal.api.utils.core.TextUtils;


/**
 * The view part for Remote connections
 */
@SuppressWarnings("deprecation")
public class ConnectionsView extends ViewPart {
	public static final String VIEW_ID = "com.nokia.carbide.remoteconnections.view.ConnectionsView"; //$NON-NLS-1$

	private TreeViewer viewer;
	private IConnectionsManagerListener connectionStoreChangedListener;
	private IConnectionListener connectionListener;
	private IToggleServicesTestingListener toggleServicesTestingListener;
	private Map<IConnectedService, IStatusChangedListener> serviceToListenerMap;
	private Map<IConnection2, IConnectionStatusChangedListener> connectionToListenerMap;
	private List<Action> actions;
	private List<Action> connectionSelectedActions;
	private List<Action> serviceSelectedActions;
	private List<Action> discoveryAgentActions;
	private static final String UID = ".uid"; //$NON-NLS-1$

	private static final ImageDescriptor CONNECTION_NEW_IMGDESC = RemoteConnectionsActivator.getImageDescriptor("icons/connectionNew.png"); //$NON-NLS-1$
	private static final ImageDescriptor CONNECTION_EDIT_IMGDESC = RemoteConnectionsActivator.getImageDescriptor("icons/connectionEdit.png"); //$NON-NLS-1$
	private static final ImageDescriptor SERVICE_TEST_IMGDESC = RemoteConnectionsActivator.getImageDescriptor("icons/serviceTest.png"); //$NON-NLS-1$
	private static final ImageDescriptor SERVICE_TEST_DISABLED_IMGDESC = RemoteConnectionsActivator.getImageDescriptor("icons/serviceTest_No.png"); //$NON-NLS-1$
	private static final ImageDescriptor CONNECTION_REFRESH_IMGDESC = RemoteConnectionsActivator.getImageDescriptor("icons/connectionRefresh.png"); //$NON-NLS-1$

	private static final String NEW_ACTION = "ConnectionsView.new"; //$NON-NLS-1$
	private static final String EDIT_ACTION = "ConnectionsView.edit"; //$NON-NLS-1$
	private static final String RENAME_ACTION = "ConnectionsView.rename"; //$NON-NLS-1$
	private static final String ENABLE_SERVICE_ACTION = "ConnectionsView.enableService"; //$NON-NLS-1$
	private static final String REFRESH_ACTION = "ConnectionsView.refresh"; //$NON-NLS-1$
	private static final String DELETE_ACTION = "ConnectionsView.delete"; //$NON-NLS-1$
	private static final String HELP_ACTION = "ConnectionsView.help"; //$NON-NLS-1$
	private static final String SET_CURRENT_ACTION = "ConnectionsView.makeCurrent"; //$NON-NLS-1$
	private static final String TOGGLE_SERVICES_ACTION = "ConnectionsView.toggleServices"; //$NON-NLS-1$
	private KeyAdapter keyListener;
	private boolean isDisposed;

	// handle, do not dispose
	private Font boldViewerFont;
	private TextCellEditor nameEditor;
	private boolean refreshPending;

	private TreeNode[] loadConnections() {
		if (serviceToListenerMap == null)
			serviceToListenerMap = new HashMap<IConnectedService, IStatusChangedListener>();
		if (connectionToListenerMap == null)
			connectionToListenerMap = new HashMap<IConnection2, IConnectionStatusChangedListener>();
		removeStatusListeners();
		List<TreeNode> connectionNodes = new ArrayList<TreeNode>();
		Collection<IConnection> connections = 
			Registry.instance().getConnections();
		for (IConnection connection : connections) {
			if (connection instanceof IConnection2) {
				IConnectionStatusChangedListener statusChangedListener = 
					new IConnectionStatusChangedListener() {
						public void statusChanged(IConnectionStatus status) {
							refreshViewer();
						}
					};
				IConnection2 connection2 = (IConnection2) connection;
				connection2.addStatusChangedListener(statusChangedListener);
				connectionToListenerMap.put(connection2, statusChangedListener);
			}
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
						refreshViewer();
					}
				};
				connectedService.addStatusChangedListener(statusChangedListener);
				serviceToListenerMap.put(connectedService, statusChangedListener);
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

	private void refreshViewer() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				if (nameEditor != null && nameEditor.isActivated()) {
					refreshPending = true;
					return;
				}
				
				if (!isDisposed && !viewer.getControl().isDisposed()) {
					viewer.refresh(true);
					packColumns();
				}
				refreshPending = false;
			}
		});
	}

	private class NameEditingSupport extends EditingSupport {

		private NameEditingSupport(ColumnViewer viewer) {
			super(viewer);
			nameEditor = new TextCellEditor((Composite) viewer.getControl(), SWT.BORDER);
			nameEditor.addListener(new ICellEditorListener() {
				public void editorValueChanged(boolean oldValidState, boolean newValidState) {
				}
				
				public void cancelEditor() {
					if (refreshPending)
						refreshViewer();
				}
				
				public void applyEditorValue() {
					if (refreshPending)
						refreshViewer();
				}
			});
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
			return nameEditor;
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
			Registry.instance().updateDisplays();
			Registry.instance().storeConnections();
		}
	}

	private class NameLabelProvider extends ColumnLabelProvider {

		public String getText(Object obj) {
			return getNodeDisplayName(obj);
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getFont(java.lang.Object)
		 */
		@Override
		public Font getFont(Object element) {
			if (element instanceof TreeNode) {
				if (((TreeNode)element).getValue().equals(Registry.instance().getCurrentConnection())) {
					return boldViewerFont;
				}
			}
			return super.getFont(element);
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
				IConnectionStatus connectionStatus = getConnectionStatus((IConnection) value);
				if (connectionStatus != null) {
					return connectionStatus.getShortDescription();
				}
				else {	
					IStatus status = ConnectionUIUtils.getFirstInUseServiceStatus((IConnection) value);
					if (status != null)
						return status.getShortDescription();
				}
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
		
		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getFont(java.lang.Object)
		 */
		@Override
		public Font getFont(Object element) {
			// we need this to avoid letting the bold name column influence the others 
			return JFaceResources.getDefaultFont();
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
				IConnectionStatus status = getConnectionStatus((IConnection) value);
				if (status != null) {
					return status.getLongDescription();
				}
				else if (ConnectionUIUtils.isSomeServiceInUse((IConnection) value)) {
					return Messages.getString("ConnectionsView.InUseDesc"); //$NON-NLS-1$
				}
			}
			
			return null;
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getFont(java.lang.Object)
		 */
		@Override
		public Font getFont(Object element) {
			// we need this to avoid letting the bold name column influence the others 
			return JFaceResources.getDefaultFont();
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
		
		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getFont(java.lang.Object)
		 */
		@Override
		public Font getFont(Object element) {
			// we need this to avoid letting the bold name column influence the others 
			return JFaceResources.getDefaultFont();
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
			return connectedService != null && connectedService.getService().isTestable() &&
				RemoteConnectionsActivator.getDefault().getShouldTestServices();
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
		
		boldViewerFont = JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);

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
						if (RemoteConnectionsActivator.getDefault().getShouldTestServices()) {
							IConnectedService connectedService = (IConnectedService) value;
							connectedService.setEnabled(true);
							connectedService.testStatus();
							((EnableConnectedServiceAction) getAction(ENABLE_SERVICE_ACTION)).updateLabel();
						}
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
				refreshViewer();
			}
		};
		Registry.instance().addConnectionStoreChangedListener(connectionStoreChangedListener);
		
		connectionListener = new IConnectionListener() {
			
			public void currentConnectionSet(IConnection connection) {
				refreshViewer();
			}
			
			public void connectionRemoved(IConnection connection) {
				// presumably the viewer itself handles this...
			}
			
			public void connectionAdded(IConnection connection) {
				// presumably the viewer itself handles this...
			}
		};
		Registry.instance().addConnectionListener(connectionListener);

		RemoteConnectionsActivator.setHelp(parent, ".connections_view"); //$NON-NLS-1$
	}

	// returns non-null status when status is not EConnectionStatus.NONE
	private IConnectionStatus getConnectionStatus(IConnection connection) {
		if (connection instanceof IConnection2) {
			IConnectionStatus status = ((IConnection2) connection).getStatus();
			if (status != null && status.getEConnectionStatus() != EConnectionStatus.NONE)
				return status;
		}
		return null;
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

	private void fillLocalPullDown(final IMenuManager manager) {
		if (discoveryAgentActions.isEmpty()) {
			IDiscoveryAgentsLoadedListener listener = new IDiscoveryAgentsLoadedListener() {
				public void agentsAreLoaded() {
					makeToggleDiscoveryAgentActions();
					addDiscoveryAgentActions(manager);
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							manager.update(true);
						}
					});
				}
			};
			RemoteConnectionsActivator.getDefault().addDiscoveryAgentsLoadedListener(listener);
		}
		else {
			addDiscoveryAgentActions(manager);
		}
	}

	private void addDiscoveryAgentActions(IMenuManager manager) {
		for (Action action : discoveryAgentActions) {
			manager.add(action);
		}
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(getAction(NEW_ACTION));
		ISelection selection = viewer.getSelection();
		if (selection.isEmpty())
			return;
		TreeNode node = (TreeNode) ((IStructuredSelection) selection).getFirstElement();
		Object value = node.getValue();
		if (value instanceof IConnectedService) {
			manager.add(new Separator());
			manager.add(getAction(ENABLE_SERVICE_ACTION));
		}
		else if (value instanceof IConnection) {
			manager.add(new Separator());
			manager.add(getAction(RENAME_ACTION));
			manager.add(getAction(EDIT_ACTION));
			manager.add(getAction(DELETE_ACTION));
			IAction helpAction = getAction(HELP_ACTION);
			if (helpAction.isEnabled()) {
				helpAction.setText(helpAction.getText());
				manager.add(helpAction);
			}
			manager.add(getAction(SET_CURRENT_ACTION));
		}
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(getAction(TOGGLE_SERVICES_ACTION));
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
		
		String editLabel = Messages.getString("ConnectionsView.EditActionLabel"); //$NON-NLS-1$
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
		action.setEnabled(RemoteConnectionsActivator.getDefault().getShouldTestServices());
		actions.add(action);
		
		action = new Action(Messages.getString("ConnectionsView.DeleteActionLabel"),  //$NON-NLS-1$
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_DELETE)) {
			@Override
			public void run() {
				ISelection selection = viewer.getSelection();
				if (selection.isEmpty() || !canBeEdited(selection))
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
		action = new Action(Messages.getString("ConnectionsView.NoHelpActionLabel"), desc) { //$NON-NLS-1$
			
			private String getHelpContextFromSelection() {
				IConnection connection = getSelectedConnection();
				if (connection != null) {
					return connection.getConnectionType().getHelpContext();
				}
				return null;
			}
			
			@Override
			public String getText() {
				if (isEnabled()) {
					IConnection connection = getSelectedConnection();
					IConnectionType connectionType = connection.getConnectionType();
					String displayName = connectionType.getDisplayName();
					String pattern = Messages.getString("ConnectionsView.HelpActionLabel"); //$NON-NLS-1$
					return MessageFormat.format(pattern, displayName);
				}
				return super.getText();
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
		
		desc = ConnectionUIUtils.CONNECTION_IMGDESC;
		action = new Action(Messages.getString("ConnectionsView.SetCurrentActionLabel"), desc) { //$NON-NLS-1$
			
			@Override
			public boolean isEnabled() {
				return !ObjectUtils.equals(Registry.instance().getCurrentConnection(), getSelectedConnection());
			}

			@Override
			public void run() {
				Registry.instance().setCurrentConnection(getSelectedConnection());
				setEnabled(false);
			}
		};
		action.setId(SET_CURRENT_ACTION);
		actions.add(action);
		connectionSelectedActions.add(action);		
		
		action = new Action(Messages.getString("ConnectionsView.ToggleServicesLabel"), IAction.AS_CHECK_BOX) { //$NON-NLS-1$
			public void setChecked(boolean checked) {
				if (isChecked() != checked) {
					super.setChecked(checked);
					RemoteConnectionsActivator.getDefault().setShouldTestServices(checked);
					setImageDescriptor(checked ? SERVICE_TEST_IMGDESC : SERVICE_TEST_DISABLED_IMGDESC);
				}
			};
		};
		action.setId(TOGGLE_SERVICES_ACTION);
		action.setChecked(RemoteConnectionsActivator.getDefault().getShouldTestServices());
		action.setImageDescriptor(action.isChecked() ? SERVICE_TEST_IMGDESC : SERVICE_TEST_DISABLED_IMGDESC);
		actions.add(action);
		
		enableConnectionSelectedActions(false);
		enableServiceSelectedActions(false);
		
		makeToggleDiscoveryAgentActions();

		toggleServicesTestingListener = new IToggleServicesTestingListener() {
			public void servicesTestingToggled(boolean enabled) {
				getAction(TOGGLE_SERVICES_ACTION).setChecked(enabled);
				getAction(REFRESH_ACTION).setEnabled(enabled);
			}
		};
		RemoteConnectionsActivator.getDefault().addToggleServicesTestingListener(toggleServicesTestingListener);
	}
	
	private void makeToggleDiscoveryAgentActions() {
		discoveryAgentActions = new ArrayList<Action>();
		Collection<IDeviceDiscoveryAgent> discoveryAgents = RemoteConnectionsActivator.getDefault().getDiscoveryAgents();
		for (IDeviceDiscoveryAgent agent : discoveryAgents) {
			discoveryAgentActions.add(new ToggleDiscoveryAgentAction(agent));
		}
		
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
	
	private void removeStatusListeners() {
		for (IConnectedService connectedService : serviceToListenerMap.keySet()) {
			IStatusChangedListener listener = serviceToListenerMap.get(connectedService);
			connectedService.removeStatusChangedListener(listener);
		}
		serviceToListenerMap.clear();
		for (IConnection2 connection : connectionToListenerMap.keySet()) {
			IConnectionStatusChangedListener listener = connectionToListenerMap.get(connection);
			connection.removeStatusChangedListener(listener);
		}
		connectionToListenerMap.clear();
	}
	
	@Override
	public void dispose() {
		removeStatusListeners();
		Registry.instance().removeConnectionStoreChangedListener(connectionStoreChangedListener);
		Registry.instance().removeConnectionListener(connectionListener);
		RemoteConnectionsActivator.getDefault().removeToggleServicesTestingListener(toggleServicesTestingListener);
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

	private static boolean isDynamicConnection(Object object) {
		return object instanceof IConnection2 && ((IConnection2) object).isDynamic();
	}

	private boolean selectionCanBeEdited() {
		ISelection selection = viewer.getSelection();
		return canBeEdited(selection);
	}

	private static boolean canBeEdited(ISelection selection) {
		if (selection.isEmpty())
			return false;
		TreeNode node = (TreeNode) ((IStructuredSelection) selection).getFirstElement();
		return !isDynamicConnection(node.getValue());
	}

	private IConnection getSelectedConnection() {
		ISelection selection = viewer.getSelection();
		if (!selection.isEmpty()) {
			TreeNode treeNode = (TreeNode) ((IStructuredSelection) selection).getFirstElement();
			Object value = treeNode.getValue();
			if (value instanceof IConnection) {
				return (IConnection) value;
			}
		}
		return null;
	}

	public void setSelectedConnection(IConnection connection) {
		if (viewer != null && !viewer.getControl().isDisposed()) {
			if (connection != null) {
				TreeNode node = new TreeNode(connection);
				viewer.setSelection(new StructuredSelection(node));
			} else {
				viewer.setSelection(null);
			}
		}
	}
}

