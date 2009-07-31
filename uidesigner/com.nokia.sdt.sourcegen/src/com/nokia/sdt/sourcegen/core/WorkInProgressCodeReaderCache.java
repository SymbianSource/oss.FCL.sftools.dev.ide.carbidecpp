/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.sourcegen.core;

import java.io.IOException;
import java.util.HashMap;

import org.eclipse.cdt.core.index.IIndexFileLocation;
import org.eclipse.cdt.core.parser.CodeReader;
import org.eclipse.cdt.core.parser.ICodeReaderCache;
import org.eclipse.core.runtime.CoreException;

/**
 * ICodeReaderCache that has infinite storage.  Used during sourcegen.
 * 
 *
 */
public class WorkInProgressCodeReaderCache implements ICodeReaderCache {

	private HashMap<String, CodeReader> map;

	/**
	 * 
	 */
	public WorkInProgressCodeReaderCache() {
		map = new HashMap<String, CodeReader>();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.cdt.core.parser.ICodeReaderCache#get(java.lang.String)
	 */
	public synchronized CodeReader get(String key) {
		return map.get(key); 
	}

	/* (non-Javadoc)
	 * @see org.eclipse.cdt.core.parser.ICodeReaderCache#remove(java.lang.String)
	 */
	public synchronized CodeReader remove(String key) {
		CodeReader reader = map.get(key);
		map.remove(key);
		return reader;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.cdt.core.parser.ICodeReaderCache#getCurrentSpace()
	 */
	public synchronized int getCurrentSpace() {
		return Integer.MAX_VALUE;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.cdt.core.parser.ICodeReaderCache#flush()
	 */
	public synchronized void flush() {
		map.clear();
	}
	
	/**
	 * Private API: replace a file with a new version, 
	 * which is not necessarily the same as on disk.
	 * @param key
	 * @param reader
	 */
	public synchronized void put(String key, CodeReader reader) {
		map.put(key, reader);
	}
	
	public CodeReader get(String key, IIndexFileLocation ifl) throws CoreException, IOException { 
		// TODO this method added in CDT 6.0 - do we need to do anything with ifl? 
		return get(key); 
	}
}