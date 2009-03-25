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
package com.nokia.carbide.cpp.debug.kernelaware;

import java.util.ArrayList;

import org.eclipse.cdt.debug.core.cdi.ICDISession;
import org.eclipse.cdt.debug.core.cdi.event.ICDIEvent;
import org.eclipse.cdt.debug.core.cdi.event.ICDIResumedEvent;
import org.eclipse.cdt.debug.core.cdi.event.ICDISuspendedEvent;
import org.eclipse.cdt.debug.core.cdi.model.ICDITarget;
import org.eclipse.cdt.debug.core.cdi.model.ICDIThread;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunchConfiguration;

import com.freescale.cdt.debug.cw.core.cdi.Session;
import com.freescale.cdt.debug.cw.core.os.IOSDataManager;
import com.freescale.cdt.debug.cw.core.os.OSObjectStream;

import cwdbg.EclipseDEConstants;
import cwdbg.PreferenceConstants;

public class OSDataManager implements IOSDataManager {

	// OSDataManager is machine (session) specific.
	private Session m_session = null;

	// Does the session (launch config) support the data view ?
	private boolean m_supportsOSDataView = false;

	private int m_dataUpdated;

	private OSObjectProcess m_processList[] = new OSObjectProcess[0];

	private OSObjectThread m_threadList[] = new OSObjectThread[0];

	private OSObjectChunk m_chunkList[] = new OSObjectChunk[0];

	private OSObjectLibrary m_libraryList[] = new OSObjectLibrary[0];

	private ListenerList m_listenerList = new ListenerList(
			ListenerList.IDENTITY);

	public OSDataManager(Session dbgSession) {
		m_session = dbgSession;

		// Check if the launch supports OS data view.
		//
		ILaunchConfiguration lc = m_session.getLaunch()
				.getLaunchConfiguration();
		if (lc != null)
			try {
				m_supportsOSDataView = lc.getAttribute(
						PreferenceConstants.J_PN_SupportOSView, false);
			} catch (CoreException e) {
			}
	}

	public OSObjectChunk[] getChunksForProcess(OSObjectProcess process)
			throws DebugException {
		// Use cache
		if (process.getChunks().length > 0)
			return process.getChunks();

		OSObjectChunk[] all = getChunks();

		// The cache may be filled by the above call.
		if (process.getChunks().length > 0)
			return process.getChunks();

		// Now from our all-thread list, find those that belongs to the given
		// process
		//
		ArrayList<OSObjectChunk> list = new ArrayList<OSObjectChunk>(10);

		String procName = process.getName();
		for (int i = 0; i < all.length; i++) {
			String owner = (String) all[i]
					.getPropertyValue(IOSObjectProperties.ID.OwningProcessName);
			if (owner == null) {
				// Hmm, we got an orphan. Something wrong.
				assert (false);
			}

			if (owner != null && owner.equals(procName))
				list.add(all[i]);
		}

		OSObjectChunk[] result = new OSObjectChunk[list.size()];
		list.toArray(result);

		process.setChunks(result); // cache it.

		return result;
	}

	/**
	 * Given a list of threads, find those belonging to given process and cache
	 * them. This is actually only for stop-mode debugger where threads from
	 * backen only contains owner process name but no process ID.
	 */
	private void assignThreadsToProcess(OSObjectProcess process,
			OSObjectThread[] threads) {

		// already done.
		if (process.getThreads().length > 0)
			return;

		OSObjectThread[] all = threads;

		// Now from our all-thread list, find those that belongs to the given
		// process
		//
		ArrayList<OSObjectThread> list = new ArrayList<OSObjectThread>(10);

		String procName = process.getName();
		int procID = process.getID();
		for (int i = 0; i < all.length; i++) {
			OSObjectThread thread = all[i];

			String owner = (String) thread
					.getPropertyValue(IOSObjectProperties.ID.OwningProcessName);
			if (owner == null) {
				// Hmm, we got an orphan. Something wrong.
				assert (false);
			}

			if (owner != null && owner.equals(procName)) {
				list.add(thread);

				// Record process ID in the thread object.
				if (thread
						.getPropertyValue(IOSObjectProperties.ID.OwningProcessID) == null)
					thread.setPropertyValue(
							IOSObjectProperties.ID.OwningProcessID, procID);
			}
		}

		OSObjectThread[] result = new OSObjectThread[list.size()];
		list.toArray(result);

		// cache it.
		process.setThreads(result);
	}

	/**
	 * Get threads belonging to a given process. This method also assign owner
	 * process info to each thread, if not yet.
	 */
	public OSObjectThread[] getThreadsForProcess(OSObjectProcess process)
			throws DebugException {

		if (process.getThreads().length == 0) {
			// This should fill the caches.
			getThreads();
		}

		return process.getThreads();
	}

	public Session getSession() {
		return m_session;
	}

	/**
	 * Get list of OS objects of specified type from device.
	 * 
	 * @param objectType -
	 *            type of OS object to get, e.g.
	 *            EclipseDEConstants.J_OSObjectType_Thread.
	 * @return array of the objects
	 * @throws DebugException
	 */
	private OSObject[] getObjectList(int objectType, int ownerID)
			throws DebugException {
		OSObject[] result = new OSObject[0];

		if (m_session == null || ! m_session.isActive())
			return result;

		// Check if the launch supports OS data view.
		//
		if (!m_supportsOSDataView)
			return result;

		OSObjectStream objStream = m_session.getOSObjectList(objectType,
				ownerID);

		if (objStream.getObjectStream().length() == 0)
			return result;

		OSObjectBag objBag = new OSObjectBag(objectType, objStream);
		result = objBag.getObjectList();

		return result;
	}

	/**
	 * @return an array on sucess or empty array on error.
	 * @throws DebugException
	 */
	public OSObjectProcess[] getProcesses() throws DebugException {
		int objectType = EclipseDEConstants.J_OSObjectType_Process;

		if ((getUpdatedFlag() & objectType) != 0) {
			return m_processList;
		}

		// Read from device
		OSObject[] list = getObjectList(objectType,
				EclipseDEConstants.J_OSObjectID_Invalid /* don't care */);

		if (list.length > 0) {
			assert (list instanceof OSObjectProcess[]);
			m_processList = (OSObjectProcess[]) list;
		} else
			m_processList = new OSObjectProcess[0];

		setPartialUpdatedFlag(objectType);

		return m_processList;
	}

	/**
	 * Get all threads from target device and cache them in our all-thread list
	 * and in each owner process object.
	 * 
	 * @return an array on success or empty array on error.
	 * @throws DebugException
	 */
	public OSObjectThread[] getThreads() throws DebugException {
		int objectType = EclipseDEConstants.J_OSObjectType_Thread;

		if ((getUpdatedFlag() & objectType) != 0) {
			return m_threadList;
		}

		// First try reading whole thread list from device.
		//
		// Currently stop-mode debugger (essentially the CW kernel aware plugin)
		// supports that.
		OSObject[] list = null;
		try {
			list = getObjectList(objectType,
					EclipseDEConstants.J_OSObjectID_Invalid /* get all threads */);
		} catch (Exception e) {
			// ignore error here as we'll try another way below.
		}

		if (list != null && list.length > 0) {
			assert (list instanceof OSObjectThread[]);
			m_threadList = (OSObjectThread[]) list;

			// Make sure each thread has its owner info (e.g. process ID)
			// assigned.
			// E.g. The thread properties from stop mode backend (CW kernelaware
			// plugin) may not have owner process ID.
			OSObjectProcess[] procs = getProcesses();
			for (int i = 0; i < procs.length; i++)
				assignThreadsToProcess(procs[i], m_threadList);
		} else {

			// Then try getting threads per process and combine.
			// E.g. with TRK, it only supports getting threads per process.
			//
			OSObjectProcess[] procList = getProcesses();
			if (procList.length == 0)
				return new OSObjectThread[0];

			ArrayList<OSObjectThread> allThreads = new ArrayList<OSObjectThread>(
					30);

			// Get threads for each process and combine them...
			//
			for (int i = 0; i < procList.length; i++) {
				int procID = procList[i].getID();

				// Get from device.
				OSObject[] objs = getObjectList(
						EclipseDEConstants.J_OSObjectType_Thread, procID);

				if (objs.length == 0) // possible when the process has dies.
					continue;

				OSObjectThread[] threads = (OSObjectThread[]) objs;

				// Record the owner info for each thread, if not available yet.
				// 
				if (threads[0]
						.getRawPropertyValue(IOSObjectProperties.ID.OwningProcessName) == null) {
					String ownerName = procList[i].getName();
					for (int j = 0; j < threads.length; j++) {
						threads[j].setPropertyValue(
								IOSObjectProperties.ID.OwningProcessName,
								ownerName);
						threads[j].setPropertyValue(
								IOSObjectProperties.ID.OwningProcessID,
								new Integer(procID));
					}
				}

				procList[i].setThreads(threads); // cache it.

				for (int j = 0; j < threads.length; j++)
					allThreads.add(threads[j]);
			}

			m_threadList = new OSObjectThread[allThreads.size()];
			allThreads.toArray(m_threadList);
		}

		setPartialUpdatedFlag(objectType);

		return m_threadList;
	}

	public OSObjectChunk[] getChunks() throws DebugException {
		int objectType = EclipseDEConstants.J_OSObjectType_Chunk;

		// Use cache
		if ((getUpdatedFlag() & objectType) != 0) {
			return m_chunkList;
		}

		// Currently non stop-mode debugger does not support getting these
		// objects, so don't bother.
		if (!m_session.isSystemModeDebug()) {
			m_chunkList = new OSObjectChunk[0];
			setPartialUpdatedFlag(objectType);
			return m_chunkList;
		}

		// Read from device
		OSObject[] list = getObjectList(objectType,
				EclipseDEConstants.J_OSObjectID_Invalid /* don't care */);

		if (list.length > 0) {
			assert (list instanceof OSObjectChunk[]);
			m_chunkList = (OSObjectChunk[]) list;
		} else
			m_chunkList = new OSObjectChunk[0];

		setPartialUpdatedFlag(objectType);

		return m_chunkList;
	}

	public OSObjectLibrary[] getLibraries() throws DebugException {
		int objectType = EclipseDEConstants.J_OSObjectType_Library;

		// Use cache
		if ((getUpdatedFlag() & objectType) != 0) {
			return m_libraryList;
		}

		// Currently non stop-mode debugger does not support getting these
		// objects, so don't bother.
		if (!m_session.isSystemModeDebug()) {
			m_libraryList = new OSObjectLibrary[0];
			setPartialUpdatedFlag(objectType);
			return m_libraryList;
		}

		// Read from device
		OSObject[] list = getObjectList(objectType,
				EclipseDEConstants.J_OSObjectID_Invalid /* don't care */);

		if (list.length > 0) {
			assert (list instanceof OSObjectLibrary[]);
			m_libraryList = (OSObjectLibrary[]) list;
		} else
			m_libraryList = new OSObjectLibrary[0];

		setPartialUpdatedFlag(objectType);

		return m_libraryList;
	}

	synchronized public void setDataDirty() {
		if (getUpdatedFlag() == 0)
			return;
		setUpdatedFlag(0);

		// Notify listeners
		Object[] listeners = m_listenerList.getListeners();
		for (int i = 0; i < listeners.length; ++i) {
			final IDataChangedListener lt = (IDataChangedListener) listeners[i];
			SafeRunner.run(new ISafeRunnable() {
				public void run() {
					lt.dataDirty();
				}

				public void handleException(Throwable exception) {
				}
			});
		}
	}

	public boolean isDataDirty() {
		return getUpdatedFlag() == 0;
	}

	public void addDataChangedListener(IDataChangedListener listener) {
		m_listenerList.add(listener);
	}

	public void removeDataChangedListener(IDataChangedListener listener) {
		m_listenerList.remove(listener);
	}

	private boolean m_sessionRunning = false;
	
	public void handleDebugEvents(ICDIEvent[] events) {
		// If we get a ResumedEvent (either thread or process), set cache dirty.
		// For stop-mode debug only.
		if (! m_session.isSystemModeDebug())
			return;
		
		for (int i = 0; i < events.length; i++) {
			ICDIEvent event = events[i];
			ICDISession is = null;
			
			// Only need to care about events for processes (not threads).
			if (event.getSource() instanceof ICDITarget)
				is = ((ICDITarget)event.getSource()).getSession();
			else 
				continue;
			
			if (! (is instanceof Session))	// our Session
				continue;
				
			Session session = (Session) is;
			if (session == null || session != m_session)
				return;
			
			boolean statusChanged = false;
			
			// Note we may get a series of resume events (for threads and processes)
			// but we only want to honor the first one. Same with suspend event.
			if (event instanceof ICDIResumedEvent)
			{
				if (! m_sessionRunning) {
					statusChanged = true;
					m_sessionRunning = true;
				}
			}
			else if (event instanceof ICDISuspendedEvent) 
			{
				if (m_sessionRunning) {
					statusChanged = true;
					m_sessionRunning = false;
				}
			}
			
			if (statusChanged) {
				setDataDirty();
				return;
			}
		}
	}

	/**
	 * Update all OS kernel aware data. This method is synchronized to prevent
	 * race condition.
	 * 
	 * @return IStatus - status of the update: user canceled or not.
	 * @throws DebugException
	 */
	synchronized public IStatus updateAll(IProgressMonitor monitor)
			throws DebugException {

		monitor.beginTask("Refreshing Symbian OS Data", 4);

		getProcesses();
		if (monitor.isCanceled())
			return Status.CANCEL_STATUS;
		monitor.worked(1);

		getThreads();
		if (monitor.isCanceled())
			return Status.CANCEL_STATUS;
		monitor.worked(1);

		getChunks();
		if (monitor.isCanceled())
			return Status.CANCEL_STATUS;
		monitor.worked(1);

		getLibraries();
		if (monitor.isCanceled())
			return Status.CANCEL_STATUS;
		monitor.worked(1);

		return Status.OK_STATUS;
	}

	synchronized public int getUpdatedFlag() {
		return m_dataUpdated;
	}

	synchronized public void setUpdatedFlag(int flag) {
		m_dataUpdated = flag;
	}

	synchronized public void setPartialUpdatedFlag(int objectTypeUpdated) {
		m_dataUpdated |= objectTypeUpdated;
	}
}
