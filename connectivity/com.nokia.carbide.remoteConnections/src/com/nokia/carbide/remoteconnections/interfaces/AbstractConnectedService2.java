/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;

import com.nokia.carbide.remoteconnections.Messages;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus.EStatus;
import com.nokia.carbide.remoteconnections.internal.ServiceTester;
import com.nokia.carbide.remoteconnections.internal.api.IConnectedService2;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.ListenerList;
import com.nokia.cpp.internal.api.utils.core.ObjectUtils;

/**
 * @since 2.5
 */
public abstract class AbstractConnectedService2 implements IConnectedService2 {
	
	public final static int TIMEOUT = 2000;

	public static class ConnectionFailException extends Exception {
		public ConnectionFailException(String string) {
			super(string);
		}

		private static final long serialVersionUID = -5438012263174866437L;
	}

	public class Status implements IStatus {
		private volatile EStatus estatus;
		private String shortDescription;
		private String longDescription;
		
		public IConnectedService getConnectedService() {
			return AbstractConnectedService2.this;
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
	protected boolean externalTesting;
	private Map<String, String> properties;

	public AbstractConnectedService2(IService service, AbstractSynchronizedConnection connection) {
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
		if (listeners != null) {
			for (IStatusChangedListener listener : listeners) {
				listeners.remove(listener);
			}
		}
		ServiceTester.getInstance().unregister(this);
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
		if (!(isEnabled() || externalTesting))
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
		
		if (!(isEnabled() || externalTesting)) // could be waiting for response past when service testing was disabled
			return;
		if (result[0].shortDescription != null && result[0].longDescription != null) {
			currentStatus.setEStatus(result[0].estatus, result[0].shortDescription, result[0].longDescription);
		}
	}
	
	public void setExternalTesting() {
		externalTesting = true;
	}

	public boolean isEnabled() {
		return ServiceTester.getInstance().isRegistered(this);
	}

	public void setEnabled(boolean enabled) {
		if (!service.isTestable())
			return;
		if (enabled && !isEnabled()) {
			Check.checkState(connection.getSettings() != null);
			ServiceTester.getInstance().register(this);
		}
		else if (!enabled && isEnabled()) {
			ServiceTester.getInstance().unregister(this);
			currentStatus.setEStatus(EStatus.UNKNOWN, 
					Messages.getString("AbstractConnectedService.NoTestingLabel"), //$NON-NLS-1$
					Messages.getString("AbstractConnectedService.UserDisabledMessage")); //$NON-NLS-1$
		}
	}
	
	public Map<String, String> getProperties() {
		return properties;
	}

	public void setStatus(Status status) {
		if (currentStatus == null)
			currentStatus = new Status();
		currentStatus.setEStatus(status.internalGetEStatus(), status.getShortDescription(), status.getLongDescription());
	}
}