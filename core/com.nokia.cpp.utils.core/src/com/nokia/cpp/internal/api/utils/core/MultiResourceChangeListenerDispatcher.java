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
package com.nokia.cpp.internal.api.utils.core;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.IPath;

import java.util.*;

/**
 * Generic handler for reporting changes to registered paths 
 * in the workspace.<p>
 * This manages a single resource change listener
 * and has advantages over installing new listeners for each and every
 * path being tracked.  
 * <p>
 * This handles only {@link IResourceChangeEvent#POST_CHANGE}
 * events where the content of the given file is changed.
 *
 */
public class MultiResourceChangeListenerDispatcher {

	public interface IResourceChangeHandler {
		/** Reports a change to an entry at the given workspace path. */
		void resourceChanged(IPath workspacePath);
	}
	
	private List<Pair<IPath, IResourceChangeHandler>> trackedResources;
	private IResourceChangeListener listener;

	public MultiResourceChangeListenerDispatcher() {
		
	}
	
	/**
	 * Add a path to a resource and a handler to call when it is changed.
	 * Only simple {@link IResourceChangeEvent#POST_CHANGE} events for content
	 * change deltas are handled; use more specific listeners for other tasks. 
	 * <p>
	 * @param workspacePath workspace-relative path.  Existing combinations of workspacePath and handler are ignored.
	 * @param handler routine called to handle changes
	 */
	public synchronized void addResource(IPath workspacePath, IResourceChangeHandler handler) {
		Check.checkArg(workspacePath != null && workspacePath.getDevice() == null);
		Check.checkArg(handler);
		
		// canonicalize, as it were
		workspacePath = workspacePath.makeRelative().removeTrailingSeparator();
		
		if (trackedResources == null) {
			trackedResources = new LinkedList<Pair<IPath, IResourceChangeHandler>>();
			listener = new IResourceChangeListener() {
			
				public void resourceChanged(IResourceChangeEvent event) {
					Pair<IPath, IResourceChangeHandler>[] array;
					synchronized (MultiResourceChangeListenerDispatcher.this) {
						if (event.getDelta() == null
								|| event.getDelta().getKind() != IResourceDelta.CHANGED
								|| event.getType() != IResourceChangeEvent.POST_CHANGE
								|| trackedResources == null)
							return;
						array = trackedResources.toArray(new Pair[trackedResources.size()]);
					}
					for (Pair<IPath, IResourceChangeHandler> entry : array) {
						IResourceDelta delta = event.getDelta().findMember(entry.first);
						if (delta != null) {
							// honor any added and removed events.  for changed events, make sure it's the content
							// that changed and not the description or marker or something.
							if (delta.getKind() == IResourceDelta.ADDED || delta.getKind() == IResourceDelta.REMOVED ||
								(delta.getKind() == IResourceDelta.CHANGED && (delta.getFlags() & IResourceDelta.CONTENT) != 0)) {
									entry.second.resourceChanged(entry.first);
							}
						}
					}
				}
				
			};
			
			ResourcesPlugin.getWorkspace().addResourceChangeListener(
					listener, IResourceChangeEvent.POST_CHANGE);
			
		}
		
		Pair<IPath, IResourceChangeHandler> entry = new Pair<IPath, IResourceChangeHandler>(workspacePath, handler);
		if (!trackedResources.contains(entry))
			trackedResources.add(entry);
	}

	/**
	 * Stop listening for changes for the given workspace path and handler
	 * combination.
	 * @param workspacePath
	 */
	public synchronized void removeResource(IPath workspacePath, IResourceChangeHandler handler) {
		Check.checkArg(workspacePath);
		if (trackedResources == null) {
			return;
		}
		
		// canonicalize, as it were
		workspacePath = workspacePath.makeRelative().removeTrailingSeparator();
		
		Pair<IPath, IResourceChangeHandler> entry = new Pair<IPath, IResourceChangeHandler>(workspacePath, handler);
		trackedResources.remove(entry);
			
		if (trackedResources.isEmpty()) {
			trackedResources = null;
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(listener);
			listener = null;
		}
	}


	/**
	 * Remove all entries in the listener.
	 */
	public synchronized void removeAll() {
		if (trackedResources == null)
			return;
		Pair<IPath, IResourceChangeHandler>[] entries = (Pair<IPath, IResourceChangeHandler>[]) trackedResources.toArray(new Pair[trackedResources.size()]);
		for (Pair<IPath, IResourceChangeHandler> entry : entries) {
			removeResource(entry.first, entry.second);
		}
	}

	/**
	 * Remove all entries in the listener for paths with the given prefix.
	 */
	public void removeAllForPrefix(IPath path) {
		if (trackedResources == null)
			return;
		Pair<IPath, IResourceChangeHandler>[] entries = (Pair<IPath, IResourceChangeHandler>[]) trackedResources.toArray(new Pair[trackedResources.size()]);
		for (Pair<IPath, IResourceChangeHandler> entry : entries) {
			if (path.isPrefixOf(entry.first))
				removeResource(entry.first, entry.second);
		}
	}

}
