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


package com.nokia.carbide.remoteconnections.interfaces;

import java.util.Map;

/**
 * Abstract base class providing implementation of in-use semantics of IConnection
 * using a counted map of potential resource strings and implements 
 * {@link IConnection#useConnection(boolean)} allowing external clients to wait
 * for internal clients (service testing) to complete
 */
public abstract class AbstractSynchronizedConnection extends AbstractConnection {

	private Object testingMutex = new Object();
	private boolean testing;

	public AbstractSynchronizedConnection(IConnectionType connectionType, Map<String, String> settings) {
		super(connectionType, settings);
	}
	
	/**
	 * @return the current resource string value of this connection
	 */
	protected abstract String getCurrentResourceString();
	
	/**
	 * @return the map of values to counts - should be static to the concrete connection class
	 */
	protected abstract Map<String, Integer> getResourcesMap();

	public boolean isInUse() {
		String resource = getCurrentResourceString();
		synchronized (getResourcesMap()) {
			return getResourcesMap().containsKey(resource);
		}
	}

	public void setInUse(boolean inUse) {
		String resource = getCurrentResourceString();
		synchronized (getResourcesMap()) {
//			System.out.println((inUse?"+":"-")+resource+", thread="+Thread.currentThread().getId());
			if (inUse) {
				Integer count = getResourcesMap().get(resource);
				if (count == null)
					count = 0;
				getResourcesMap().put(resource, ++count);
			}
			else {
				Integer count = getResourcesMap().get(resource);
				if (count != null) {
					count--;
					if (count > 0)
						getResourcesMap().put(resource, count);
					else
						getResourcesMap().remove(resource);
				}
			}
		}
	}
	
	public void setServiceTestingAndInUse(boolean value) {
		synchronized (testingMutex) {
			testing = value;
			setInUse(value);
		}
	}
	
	private boolean isTesting() {
		synchronized (testingMutex) {
			return testing;
		}
	}

	public synchronized void useConnection(boolean use) {
		if (use) {
			while (isTesting()) {
				try {
					Thread.sleep(200);
//					System.out.println(getDisplayName() + " was service testing");
				} catch (InterruptedException e) {
				}
			}
		}
		setInUse(use);
	}
	
	public Object getCurrentResource() {
		return getCurrentResourceString();
	}
}
