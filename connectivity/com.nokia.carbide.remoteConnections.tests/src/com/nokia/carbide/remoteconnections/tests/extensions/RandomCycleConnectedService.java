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


package com.nokia.carbide.remoteconnections.tests.extensions;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Version;

import com.nokia.carbide.remoteconnections.interfaces.IConnectedService;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus.EStatus;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.interfaces.IService;
import com.nokia.cpp.internal.api.utils.core.ListenerList;

public class RandomCycleConnectedService implements IConnectedService {
	
	public static class TestStatus implements IStatus {
		private EStatus eStatus;
		private final IConnectedService service;
		
		public TestStatus(IConnectedService service, EStatus status) {
			this.service = service;
			this.eStatus = status;
		}

		public String getLongDescription() {
			return "The status is " + getShortDescription();
		}

		public String getShortDescription() {
			switch (eStatus) {
			case UP:
				return "Up";
			case DOWN:
				return "Down";
			case IN_USE:
				return "Active";
			case UNKNOWN:
				return "Unknown";
			}
			return null;
		}

		public EStatus getEStatus() {
			return eStatus;
		}
		
		public boolean equals(Object object) {
			return super.equals(object) ||
			((object instanceof TestStatus) &&
					this.service.equals(((TestStatus) object).getConnectedService()) &&
					this.eStatus.equals(((TestStatus) object).getEStatus()));
		}

		public IConnectedService getConnectedService() {
			return service;
		}
		
	}
	
	private class StatusCycler extends Thread {
		
		private final RandomCycleConnectedService service;

		public StatusCycler(RandomCycleConnectedService service) {
			this.service = service;
		}

		public void run() {
			IConnection testConnection = service.getConnection();
			int msInterval = testConnection instanceof IntervalConnection ? ((IntervalConnection) testConnection).getInterval() : 1000;
			try {
				Thread.sleep(msInterval);
			} catch (InterruptedException e) {
			}
			
			EStatus nextStatus = EStatus.UNKNOWN;
			if (this.service.enabled) {
				EStatus status = service.getEStatus();
				switch (status) {
				case UNKNOWN:
				case DOWN:
					nextStatus = EStatus.UP;
					break;
				case UP:
					nextStatus = EStatus.IN_USE;
					break;
				case IN_USE:
					nextStatus = EStatus.DOWN;
					break;
				}
			}
			service.setEStatus(nextStatus);
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					IWorkbench workbench = PlatformUI.getWorkbench();
					if (workbench != null) {
						IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
						if (activeWorkbenchWindow != null) {
							IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
							if (activePage != null) {
								IWorkbenchPart part = activePage.getActivePart();
								if (part != null) {
									IActionBars ab = null;
									if (part instanceof IViewPart) {
										IViewPart vp = (IViewPart) part;
										ab = vp.getViewSite().getActionBars();
									} else if (part instanceof IEditorPart) {
										IEditorPart ep = (IEditorPart) part;
										ab = ep.getEditorSite().getActionBars();
									}
									if (ab != null) {
										IStatusLineManager manager = ab.getStatusLineManager();
										String statusLine = "Status of '" + 
										RandomCycleConnectedService.this.toString() + 
										"' updated at " + 
										DateFormat.getDateTimeInstance().format(new Date());
										manager.setMessage(statusLine);
									}
								}
							}
						}
					}
				}
			});
		}
		
	}
	
	private class RandomCycleScheduler extends Thread {
		private int minDelay;
		private int maxDelay;
		private boolean run;

		public RandomCycleScheduler() {
			if (rand == null)
				rand = new Random(System.currentTimeMillis());
			minDelay = 100;
			maxDelay = 60000;
			run = true;
		}

		public void run() {
			while (run) {
				int msSleep = minDelay + rand.nextInt(maxDelay);
				try {
					Thread.sleep(msSleep);
				} catch (InterruptedException e) {
				}
				testStatus();
			}
		}
		
		public void stopRunning() {
			run = false;
		}
	}

	private ListenerList<IStatusChangedListener> listeners;
	private IConnection connection;
	private EStatus status;
	private RandomCycleScheduler downageScheduler;
	private final IService service;
	private boolean enabled = true;
	private static Random rand;

	public RandomCycleConnectedService(IService service, IConnection connection) {
		this.connection = connection;
		this.service = service;
		status = EStatus.DOWN;
		if (downageScheduler == null) {
			downageScheduler = new RandomCycleScheduler();
			downageScheduler.start();
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.extensions.IConnectedService#addStatusChangedListener(com.nokia.carbide.remoteconnections.extensions.IConnectedService.IStatusChangedListener)
	 */
	public void addStatusChangedListener(IStatusChangedListener listener) {
		if (listeners == null)
			listeners = new ListenerList<IStatusChangedListener>();
		listeners.add(listener);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.extensions.IConnectedService#removeStatusChangedListener(com.nokia.carbide.remoteconnections.extensions.IConnectedService.IStatusChangedListener)
	 */
	public void removeStatusChangedListener(IStatusChangedListener listener) {
		if (listeners != null)
			listeners.remove(listener);
	}
	
	private void fireStatusChanged() {
		if (listeners == null)
			return;
		TestStatus testStatus = new TestStatus(this, getEStatus());
		for (IStatusChangedListener listener : listeners) {
			listener.statusChanged(testStatus);
		}
	}
	
	private synchronized EStatus getEStatus() {
		return status;
	}
	
	private synchronized void setEStatus(EStatus status) {
		if (!this.status.equals(status)) {
			this.status = status;
			fireStatusChanged();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.extensions.IConnectedService#testStatus()
	 */
	public void testStatus() {
		Thread t = new StatusCycler(this);
		t.start();
	}

	public IConnection getConnection() {
		return connection;
	}

	public void dispose() {
		downageScheduler.stopRunning();
	}

	public IService getService() {
		return service;
	}

	public IStatus getStatus() {
		return new TestStatus(this, getEStatus());
	}

	@Override
	public String toString() {
		return service.getDisplayName() + " on " + connection;
	}

	public void setDeviceOS(String familyName, Version version) {
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled  = enabled;
	}
}
