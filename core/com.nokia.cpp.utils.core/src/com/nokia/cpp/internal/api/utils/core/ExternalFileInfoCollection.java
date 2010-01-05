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

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.nokia.cpp.internal.api.utils.core.ExternalFileInfoCache.FileInfo;

/**
 * This object corrals a set of timestamp checks against a collection
 * of related files (using an {@link ExternalFileInfoCache} for
 * storage). 
 * <p>
 * Multiple instances of this object can (and should) share a cache.
 * <p>
 * As with {@link ExternalFileInfoCache}, we recommend using this 
 * tracker only for files outside the Eclipse workspace.  For workspace 
 * resources, use resource listeners to avoid polling for file changes.   
 */
public class ExternalFileInfoCollection implements Serializable {
	
	private static final long serialVersionUID = 3685308336593621639L;

	// the files to check and the last info we found
	private Map<File, ExternalFileInfoCache.FileInfo> fileMap = new HashMap<File, FileInfo>();
	
	// time in ms between checks of all the files
	private long quantum;
	
	// last time we checked
	private long lastCheckTime;

	private ExternalFileInfoCache cache;
	
	protected ExternalFileInfoCollection() {
		// for serialization
	}
	
	/**
	 * Create an object to check the states of related file timestamps and sizes.
	 * @param quantumMs time in ms between checking all the files 
	 */
	public ExternalFileInfoCollection(ExternalFileInfoCache cache,
			File[] files, long quantumMs) {
		Check.checkArg(cache);
		this.cache = cache;
		this.quantum = quantumMs;
		setFiles(files);
		this.lastCheckTime = System.currentTimeMillis();
	}
	
	/**
	 * If the collection's time quantum has passed, check all the files 
	 * to see if any have changed.
	 * <p>
	 * Note that by {@link ExternalFileInfoCache#isChanged(File, long)},
	 * a deleted file always appears to have changed, so it is assumed that if a 
	 * change is detected, a client will invoke {@link #setFiles(File[])} to 
	 * ensure deleted files are removed from the collection.
	 * @return true if any file in the collection has appeared to change
	 * since the last quantum
	 */
	public synchronized boolean anyChanged() {
		boolean changed = false;
		if (System.currentTimeMillis() > lastCheckTime + quantum) {
			for (File file : fileMap.keySet()) {
				if (checkForChange(file)) {
					changed = true;
					// continue to check all the files, so we update the info uniformly
				}
			}
			lastCheckTime = System.currentTimeMillis();
		}
		return changed;
	}
	
	/**
	 * Recheck one file in the collection and update data.
	 * @param file
	 * @return
	 */
	protected boolean checkForChange(File file) {
		FileInfo currentInfo = fileMap.get(file);
		FileInfo newInfo = cache.getFileInfo(file, lastCheckTime);
		
		fileMap.put(file, newInfo);
		
		return currentInfo == null || currentInfo.isChangedFrom(newInfo);
	}

	/**
	 * Update the files incorporated in the timestamp/size check.  
	 * @param files the files to set
	 */
	public synchronized void setFiles(File[] files) {
		this.fileMap.clear();
		
		// prime the cache so every file's current timestamp and size is known
		if (files != null) {
			for (File file : files) {
				checkForChange(file);
			}
		}
	}
	
	/**
	 * Get the files this collection checks.
	 * @return the files
	 */
	public File[] getFiles() {
		Set<File> files = fileMap.keySet();
		return (File[]) files.toArray(new File[files.size()]);
	}
	
	/**
	 * Refresh the timestamps for the affected files, ensuring that
	 * changes on disk are immediately detected for the next {@link #anyChanged()}
	 * call.
	 */
	public synchronized void refresh() {
		for (File file : fileMap.keySet()) {
			cache.refresh(file);
		}
		this.lastCheckTime = 0;
	}
	
	/**
	 * Set the quantum for the timestamp collection.
	 * @param quantumMs time is ms between checks of files
	 */
	public void setRecheckQuantum(long quantum) {
		this.quantum = quantum;
	}
}
