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
package com.nokia.carbide.remoteconnections.interfaces;

import com.nokia.carbide.remoteconnections.Messages;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus.EStatus;
import com.nokia.carbide.remoteconnections.internal.IConnectedService2;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.ListenerList;
import com.nokia.cpp.internal.api.utils.core.ObjectUtils;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractConnectedService implements IConnectedService2 {
	
	public final static int TIMEOUT = 2000;
	
	public static class ConnectionFailException extends Exception {
		public ConnectionFailException(String string) {
			super(string);
		}

		private static final long serialVersionUID = -5438012263174866433L;
	}

	public final class Tester extends Thread {
		private volatile boolean run = true;
		
		@Override
		public void run() {
			while (run) {
				testStatus();
				try {
					if (run)
						sleep(TIMEOUT);
				} catch (InterruptedException e) {
					stopRunning();
				}
			}
		}
		
		public void stopRunning() {
			run = false;
		}
	}

	public class Status implements IStatus {
		private volatile EStatus estatus;
		private String shortDescription;
		private String longDescription;
		
		public IConnectedService getConnectedService() {
			return AbstractConnectedService.this;
		}

		public EStatus getEStatus() {
			return internalGetEStatus();
		}

		protected EStatus internalGetEStatus() {
			if (estatus == null)
				estatus = EStatus.UNKNOWN;
			return estatus;
		}
		
		public String getLongDescription() {
			return longDescription;
		}
		
		public String getShortDescription() {
			return shortDescription;
		}
		
		public void setEStatus(EStatus estatus, String shortDescription, String longDescription) {
			// if everything is the same don't fire status changed
			if (estatus.equals(this.estatus) &&
					ObjectUtils.equals(shortDescription, this.shortDescription) &&
					ObjectUtils.equals(longDescription, this.longDescription))
				return;
			this.estatus = estatus;
			this.shortDescription = shortDescription;
			this.longDescription = longDescription;
			fireStatusChanged();
		}
	}
	
	public static class TestResult {
		public TestResult(EStatus estatus, String shortDescription, String longDescription) {
			this.estatus = estatus;
			this.shortDescription = shortDescription;
			this.longDescription = longDescription;
		}
		private EStatus estatus;
		private String shortDescription;
		private String longDescription;
	}
	
	protected abstract TestResult runTestStatus(IProgressMonitor monitor);
	
	protected IService service;
	protected AbstractSynchronizedConnection connection;
	private ListenerList<IStatusChangedListener> listeners;
	protected IRunnableContext runnableContext;
	protected Status currentStatus;
	protected Tester tester;
	protected boolean manualTesting;
	private Map<String, String> properties;

	public AbstractConnectedService(IService service, AbstractSynchronizedConnection connection) {
		this.service = service;
		this.connection = connection;
		properties = new HashMap<String, String>();
	}
	
	public void setRunnableContext(IRunnableContext runnableContext) {
		this.runnableContext = runnableContext;
	}

	public void addStatusChangedListener(IStatusChangedListener listener) {
		if (listeners == null)
			listeners = new ListenerList<IStatusChangedListener>();
		listeners.add(listener);
	}

	public void dispose() {
		if (tester != null) {
			tester.stopRunning();
			tester = null;
		}

		if (listeners != null) {
			for (IStatusChangedListener listener : listeners) {
				listeners.remove(listener);
			}
		}
	}

	public IService getService() {
		return service;
	}

	protected void fireStatusChanged() {
		if (listeners != null) {
			for (IStatusChangedListener listener : listeners) {
				listener.statusChanged(getStatus());
			}
		}
	}

	public void removeStatusChangedListener(IStatusChangedListener listener) {
		if (listeners != null)
			listeners.remove(listener);
	}

	public IConnection getConnection() {
		return connection;
	}

	public void testStatus() {
		if (!(isEnabled() || manualTesting))
			return;
			
		final TestResult result[] = { null };
		if (runnableContext != null && (!(runnableContext instanceof WizardDialog) || ((WizardDialog) runnableContext).getShell() != null)) {
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					try {
						runnableContext.run(true, false, new IRunnableWithProgress() {
							public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
								result[0] = runTestStatus(monitor);
							}
						});
					} catch (InvocationTargetException e) {
					} catch (InterruptedException e) {
					}
				}
			});
		}
		else {
			result[0] = runTestStatus(new NullProgressMonitor());
		}
		
		if (!(isEnabled() || manualTesting)) // could be waiting for response past when service testing was disabled
			return;
		if (result[0].shortDescription != null && result[0].longDescription != null) {
			currentStatus.setEStatus(result[0].estatus, result[0].shortDescription, result[0].longDescription);
		}
	}
	
	public void setManualTesting() {
		manualTesting = true;
	}

	public boolean isEnabled() {
		return tester != null;
	}

	public void setEnabled(boolean enabled) {
		if (!service.isTestable())
			return;
		if (enabled && tester == null) {
			Check.checkState(connection.getSettings() != null);
			tester = new Tester();
			tester.start();
		}
		else if (!enabled && tester != null) {
			tester.stopRunning();
			tester = null;
			currentStatus.setEStatus(EStatus.UNKNOWN, 
					Messages.getString("AbstractConnectedService.NoTestingLabel"), //$NON-NLS-1$
					Messages.getString("AbstractConnectedService.UserDisabledMessage")); //$NON-NLS-1$
		}
	}
	
	public Map<String, String> getProperties() {
		return properties;
	}
}