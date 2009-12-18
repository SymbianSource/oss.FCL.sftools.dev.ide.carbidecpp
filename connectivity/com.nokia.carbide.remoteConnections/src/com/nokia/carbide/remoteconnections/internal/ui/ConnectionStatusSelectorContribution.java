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

package com.nokia.carbide.remoteconnections.internal.ui;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.menus.WorkbenchWindowControlContribution;

import com.nokia.carbide.remoteconnections.Messages;
import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatusChangedListener;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager.IConnectionListener;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager.IConnectionsManagerListener;
import com.nokia.carbide.remoteconnections.internal.api.IConnection2;
import com.nokia.carbide.remoteconnections.internal.api.IConnection2.IConnectionStatus;
import com.nokia.carbide.remoteconnections.internal.api.IConnection2.IConnectionStatusChangedListener;
import com.nokia.carbide.remoteconnections.internal.registry.Registry;
import com.nokia.carbide.remoteconnections.view.ConnectionsView;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;


/**
 * This widget appears in the Eclipse trim and allows the user to select the
 * "default" device connection and also see its status at a glance. 
 */
@SuppressWarnings("deprecation")
public class ConnectionStatusSelectorContribution extends WorkbenchWindowControlContribution {

	private Composite container;
	private CLabel connectionInfo;
	private IConnectionsManager manager;
	private IConnection defaultConnection;
	private ListenerBlock listenerBlock;
	private ToolTip tooltip;

	/**
	 * Contains all the listeners.  In most cases we just recreate the contribution status item.
	 */
	class ListenerBlock implements IConnectionListener, IConnectionsManagerListener, IStatusChangedListener, IConnectionStatusChangedListener {

		/* (non-Javadoc)
		 * @see com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager.IConnectionListener#connectionAdded(com.nokia.carbide.remoteconnections.interfaces.IConnection)
		 */
		public void connectionAdded(IConnection connection) {
			updateUI();
			if (connection instanceof IConnection2 && ((IConnection2) connection).isDynamic())
				launchBubble(MessageFormat.format(
						Messages.getString("ConnectionStatusSelectorContribution.AddedConnectionFormat"),  //$NON-NLS-1$
						connection.getDisplayName()));
		}
		
		/* (non-Javadoc)
		 * @see com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager.IConnectionListener#connectionRemoved(com.nokia.carbide.remoteconnections.interfaces.IConnection)
		 */
		public void connectionRemoved(IConnection connection) {
			updateUI();
			if (connection instanceof IConnection2 && ((IConnection2) connection).isDynamic())
				launchBubble(MessageFormat.format(
						Messages.getString("ConnectionStatusSelectorContribution.RemovedConnectionFormat"),  //$NON-NLS-1$
						connection.getDisplayName()));
		}
		
		/* (non-Javadoc)
		 * @see com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager.IConnectionListener#defaultConnectionSet(com.nokia.carbide.remoteconnections.interfaces.IConnection)
		 */
		public void defaultConnectionSet(IConnection connection) {
			updateUI();
		}

		/* (non-Javadoc)
		 * @see com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager.IConnectionsManagerListener#connectionStoreChanged()
		 */
		public void connectionStoreChanged() {
			updateUI();
		}

		/* (non-Javadoc)
		 * @see com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager.IConnectionsManagerListener#displayChanged()
		 */
		public void displayChanged() {
			updateUI();
		}

		/* (non-Javadoc)
		 * @see com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatusChangedListener#statusChanged(com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus)
		 */
		public void statusChanged(IStatus status) {
			updateUI();
		}

		/* (non-Javadoc)
		 * @see com.nokia.carbide.remoteconnections.internal.api.IConnection2.IConnectionStatusChangedListener#statusChanged(com.nokia.carbide.remoteconnections.internal.api.IConnection2.IConnectionStatus)
		 */
		public void statusChanged(IConnectionStatus status) {
			updateConnectionStatus(status);
		}

	}
	
	public ConnectionStatusSelectorContribution() {
		manager = RemoteConnectionsActivator.getConnectionsManager();
		listenerBlock = new ListenerBlock();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.action.ControlContribution#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createControl(Composite parent) {
		
		// This UI is recreated whenever the default connection changes.
		
		container = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().margins(2, 0).applyTo(container);

		// Create a label for the trim.
		connectionInfo = new CLabel(container, SWT.FLAT);
		GridDataFactory.fillDefaults().grab(false, true).applyTo(connectionInfo);

		String text = Messages.getString("ConnectionStatusSelectorContribution_NoDefaultConnectionMessage"); //$NON-NLS-1$
		defaultConnection = manager.getDefaultConnection();
		if (defaultConnection != null)
			text = defaultConnection.getDisplayName();
		
		connectionInfo.setText(text);

		attachListeners();
		
		updateConnectionStatus(getConnectionStatus(defaultConnection));
		
		connectionInfo.addMouseListener (new MouseAdapter() {
			public void mouseDown(MouseEvent event) {
				Shell shell = connectionInfo.getShell();
				final Display display = shell.getDisplay();
				
				final Menu menu = new Menu(shell, SWT.POP_UP);
				populateConnectionMenu(menu);
				
				Point screenLoc = connectionInfo.toDisplay(event.x, event.y);
				menu.setLocation(screenLoc.x, screenLoc.y);
				menu.setVisible(true);
				
				while (!menu.isDisposed() && menu.isVisible()) {
					if (!display.readAndDispatch())
						display.sleep();
				}
				menu.dispose();
			}
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.MouseAdapter#mouseDoubleClick(org.eclipse.swt.events.MouseEvent)
			 */
			@Override
			public void mouseDoubleClick(MouseEvent ev) {
				// NOTE: the menu usually comes up before double-click is seen
				if (ev.button == 1) {
					openConnectionsView();
				}
			}
		});
		
		RemoteConnectionsActivator.setHelp(container, "ConnectionStatusSelector"); //$NON-NLS-1$
		return container;
	}

	private void attachListeners() {
		manager.addConnectionListener(listenerBlock);
		Registry.instance().addConnectionStoreChangedListener(listenerBlock);
		
		if (defaultConnection != null) {
			if (defaultConnection instanceof IConnection2) {
				((IConnection2) defaultConnection).addStatusChangedListener(listenerBlock);
			}
			for (IConnectedService service : manager.getConnectedServices(defaultConnection)) {
				service.addStatusChangedListener(listenerBlock);
			}
		}
	}

	private void removeListeners() {
		if (defaultConnection != null) {
			if (defaultConnection instanceof IConnection2) {
				((IConnection2) defaultConnection).removeStatusChangedListener(listenerBlock);
			}
			for (IConnectedService service : manager.getConnectedServices(defaultConnection)) {
				service.removeStatusChangedListener(listenerBlock);
			}
		}
		manager.removeConnectionListener(listenerBlock);
		Registry.instance().removeConnectionStoreChangedListener(listenerBlock);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.action.ContributionItem#dispose()
	 */
	public void dispose() {
		removeListeners();
		if (connectionInfo != null)
			connectionInfo.dispose();
		super.dispose();
	}

	/**
	 * @param ev
	 */
	protected void openConnectionsView() {
		try {
			WorkbenchUtils.getView(ConnectionsView.VIEW_ID);
        } 
        catch (PartInitException e) {
        	RemoteConnectionsActivator.logError(e);
        }
	}

	/**
	 * @param defaultConnection
	 * @param status
	 * @return
	 */
	private String createConnectionStatusTooltip(IConnection defaultConnection,
			IConnectionStatus status) {
		if (defaultConnection == null) {
			return Messages.getString("ConnectionStatusSelectorContribution.NoDynamicOrManualConnectionsTooltip"); //$NON-NLS-1$
		}
		
		String statusString = null;
		if (status != null) {
			statusString = status.getDescription();
		}
		
		if (TextUtils.isEmpty(statusString))
			statusString = Messages.getString("ConnectionStatusSelectorContribution.NotInUse"); //$NON-NLS-1$
		
		return MessageFormat.format(Messages.getString("ConnectionStatusSelectorContribution.ConnectionStatusFormat"), defaultConnection.getDisplayName(), statusString); //$NON-NLS-1$
	}

	/**
	 * Get the image representing the connection status.
	 * @param connection
	 * @return Image, to be disposed
	 */
	private IConnectionStatus getConnectionStatus(IConnection connection) {
		if (!(connection instanceof IConnection2)) {
			return null;
		} else {
			return ((IConnection2) connection).getStatus();
		}
	}
	
	/**
	 * @return
	 */
	protected void populateConnectionMenu(Menu menu) {
		// Display the connections with dynamic ones first, 
		// then static ones, separated by a separator

		List<IConnection> dynamicConnections = new ArrayList<IConnection>();
		List<IConnection> staticConnections = new ArrayList<IConnection>();
		for (IConnection connection : RemoteConnectionsActivator.getConnectionsManager().getConnections()) {
			if (connection instanceof IConnection2 && ((IConnection2)connection).isDynamic()) 
				dynamicConnections.add(connection);
			else
				staticConnections.add(connection);
		}
		

		Comparator<IConnection> connectionComparator = new Comparator<IConnection>() {
			public int compare(IConnection o1, IConnection o2) {
				return o1.getDisplayName().compareToIgnoreCase(o2.getDisplayName());
			}
		};
		Collections.sort(dynamicConnections, connectionComparator);
		Collections.sort(staticConnections, connectionComparator);

		MenuItem label = new MenuItem(menu, SWT.NONE);
		label.setEnabled(false);
		
		if (dynamicConnections.size() + staticConnections.size() == 0) {
			label.setText(Messages.getString("ConnectionStatusSelectorContribution.NoConnectionsDefinedOrDetected")); //$NON-NLS-1$
		} else {
			label.setText(Messages.getString("ConnectionStatusSelectorContribution_SelectTheDefaultConnectionMessage")); //$NON-NLS-1$
			
			for (IConnection connection : dynamicConnections) {
				createConnectionMenuItem(menu, connection, defaultConnection);
			}
			
			new MenuItem(menu, SWT.SEPARATOR);
			
			for (IConnection connection : staticConnections) {
				createConnectionMenuItem(menu, connection, defaultConnection);
			}
		}
		
		new MenuItem(menu, SWT.SEPARATOR);
		
		MenuItem openView = new MenuItem(menu, SWT.PUSH);
		openView.setText(Messages.getString("ConnectionStatusSelectorContribution.OpenRemoteConnectionsView")); //$NON-NLS-1$
		openView.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openConnectionsView();
			}
		});
	}

	/**
	 * @param menu
	 * @param connection
	 * @param defaultConnection 
	 */
	private MenuItem createConnectionMenuItem(Menu menu, final IConnection connection, IConnection defaultConnection) {
		MenuItem item = new MenuItem(menu, SWT.CHECK);
		
		boolean isDefault = false;
		isDefault = connection.equals(defaultConnection);
		
		item.setSelection(isDefault);
		
		item.setText(connection.getDisplayName());
		
		item.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				manager.setDefaultConnection(connection);
			}
		});		
		
		return item;
	}

	/**
	 * @param status
	 */
	private void updateConnectionStatus(IConnectionStatus status) {
		Image statusImage = ConnectionUIUtils.getConnectionStatusImage(status);
		connectionInfo.setImage(statusImage);
		connectionInfo.setToolTipText(createConnectionStatusTooltip(defaultConnection, status));
	}

	/**
	 * 
	 */
	private void updateUI() {
		// perform update in UI thread
		final IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
				if (window != null) {
					update();
				}
			}
		});		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.action.ContributionItem#isDynamic()
	 */
	@Override
	public boolean isDynamic() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.action.ContributionItem#update()
	 */
	@Override
	public void update() {
		getParent().update(true);
	}
	
	/**
	 * Show a status bubble for important changes.
	 * @param string
	 */
	public void launchBubble(final String string) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				if (tooltip != null)
					tooltip.dispose();
				
				if (connectionInfo == null || connectionInfo.isDisposed())
					return;
				
				tooltip = new ToolTip(connectionInfo.getShell(), SWT.BALLOON | SWT.ICON_INFORMATION);
				tooltip.setMessage(string);
				Point center = connectionInfo.getSize();
				center.x /= 2;
				Point location = connectionInfo.toDisplay(center);
				//System.out.println(connectionInfo.hashCode() + ": " + connectionInfo.getLocation() + " : " + location);
				tooltip.setLocation(location);
				tooltip.setVisible(true);
			}
		});
	}


}