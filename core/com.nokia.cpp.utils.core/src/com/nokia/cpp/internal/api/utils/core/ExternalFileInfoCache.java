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

/**
 * Use this object to cache the timestamps and sizes for files which
 * are often queried. On some OSes or types of filesystems, attribute checking
 * is very slow, and on others, the resolution of a timestamp is so low that
 * checking too often is likely to be a waste of effort.
 * <p>
 * We only recommend using this tracker for files outside the Eclipse
 * workspace.  For workspace resources, use resource listeners to avoid 
 * polling for file changes.   
 */
public class ExternalFileInfoCache implements Serializable {
	
	private static final long serialVersionUID = -2810901674805567105L;
	
	public static boolean DEBUG = false;
	public static boolean DEBUG_VERBOSE = false;
	
	// time in ms between checks of any given file
	private int quantum;
	
	static class FileInfo extends Tuple implements Serializable {
		
		private static final long serialVersionUID = -1226913205921924292L;
		
		protected FileInfo() {
			// empty for serialization
		}
		public FileInfo(long lastModified, long lastQueried, long size) {
			super(lastModified, lastQueried, size);
		}
		public long getLastModified() {
			return (Long) get(0);
		}
		public long getLastQueried() {
			return (Long) get(1);
		}
		public long getLength() { 
			return (Long) get(2);
		}
		
		public boolean isChangedFrom(FileInfo other) {
			long origTime = getLastModified();
			long origSize = getLength();
			
			long newTime = other.getLastModified();
			long newSize = other.getLength();
			
			if (newTime == 0) { // 0 if deleted
				return true;
			}
			if (origTime != newTime
				|| origSize != newSize)
				return true;
			
			return false;
		}
	}
	
	/** map of file to file size + last queried timestamp + time of last query 
	 * (use File, not IPath, so we canonicalize for the OS) */
	private Map<File, FileInfo> info = new HashMap<File, FileInfo>();		
	
	
	/**
	 * Create a cache to track the states of file timestamps and sizes.
	 * @param quantumMs time in ms between updating the status of any given file
	 */
	public ExternalFileInfoCache(int quantumMs) {
		this.quantum = quantumMs;
	}

	/**
	 * Create a cache to track the states of file timestamps and sizes,
	 * using an OS-dependent default quantum.
	 */
	public ExternalFileInfoCache() {
		// this is based on the expensiveness of the attribute lookup, not the 
		// resolution of the timestamps
		this.quantum = HostOS.IS_WIN32 ? 50 : 10;
	}

	/** 
	 * Tell if the file's timestamp or size changed since the basis time,
	 * or otherwise changed in the past quantum,  
	 * and update the record.
	 * <p>
	 * If a file does not exist, we always consider it to have changed
	 * (since otherwise, we cannot tell if a client saw the transition
	 * from existing to not existing).  
	 * @param basis the system time in ms from which to judge the change
	 * @return true if file size or timestamp changed since basis, or
	 * the file does not exist
	 */
	public FileInfo getFileInfo(File file, long basis) {
		FileInfo finfo = info.get(file);
		if (finfo == null) {
			finfo = new FileInfo(file.lastModified(), basis, file.length());
			synchronized(info) {
				info.put(file, finfo);
			}
			if (DEBUG) System.out.println("First info for " + file + ": " + finfo);
			return finfo;
		} else if (finfo.getLastQueried() + quantum < basis) {
			// note: if a file no longer exists, these return distinct values
			// that also appear as changes
			finfo = new FileInfo(file.lastModified(), basis, file.length());
			synchronized(info) {
				info.put(file, finfo);
			}
			return finfo;
		} else {
			return finfo;
		}
	}
	
	/** 
	 * Tell if the file's timestamp or size changed in the past quantum 
	 * and update the record 
	 * @return true if file size or timestamp changed.
	 */
	public boolean isChanged(File file) {
		return isChanged(file, System.currentTimeMillis());
	}
	
	/** 
	 * Tell if the file's timestamp or size changed since the basis time,
	 * or otherwise changed in the past quantum,  
	 * and update the record.
	 * <p>
	 * If a file does not exist, we always consider it to have changed
	 * (since otherwise, we cannot tell if a client saw the transition
	 * from existing to not existing).  
	 * @param basis the system time in ms from which to judge the change
	 * @return true if file size or timestamp changed since basis, or
	 * the file does not exist
	 */
	public boolean isChanged(File file, long basis) {
		FileInfo finfo = info.get(file);
		FileInfo newInfo = getFileInfo(file, basis);
		if (finfo == newInfo)
			return false;
		
		// update info
		info.put(file, newInfo);
		
		return finfo.isChangedFrom(newInfo);
	}
	
	/**
	 * Force a refresh of the given file's status on the next #isChanged() call.
	 * @param file
	 */
	public void refresh(File file) {
		synchronized(info) {
			info.remove(file);
		}
	}

}
