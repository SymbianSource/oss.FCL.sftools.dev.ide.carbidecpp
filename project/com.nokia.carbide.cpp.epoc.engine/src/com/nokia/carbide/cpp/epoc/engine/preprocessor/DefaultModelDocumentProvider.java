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
* A default implementation of IModelDocumentProvider which
* caches documents.  It keeps a maximum number of
* documents in memory at a time.  It refreshes a
* document when the underlying file's timestamp changes.
* 
* @see IModelDocumentProvider
*
*
*/
package com.nokia.carbide.cpp.epoc.engine.preprocessor;

import com.nokia.carbide.cpp.epoc.engine.DocumentFactory;
import com.nokia.carbide.cpp.epoc.engine.model.IModelDocumentProvider;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
public class DefaultModelDocumentProvider implements IModelDocumentProvider {

	private static DefaultModelDocumentProvider instance;

	// not final or static for debugging
	private boolean DUMP = false;
	
	/** count of entries allowed */
	private static final int DEFAULT_MAX_CACHE_SIZE = 32;
	/** the minimum number of hits (accesses) to the TU to keep it when flushing cache. */
	private static final int DEFAULT_MINIMUM_HITS_TO_KEEP = 8;
	
	private Object lock;
	private Map<File, IDocument> documentCache;
	private Map<File, Integer> cacheHits;
	private List<File> cacheOrder;
	private Map<File, Long> cacheTimes;

	private int maxCacheSize;

	private Integer minimumHitsToKeep;
	
	/**
	 * Get the singleton instanceof the translation unit provider.
	 * The provider owns the cache of TUs.
	 * @return instance, never null
	 */
	public static DefaultModelDocumentProvider getInstance() {
		if (instance == null) {
			instance = new DefaultModelDocumentProvider();
		}
		return instance;
	}
	
	private DefaultModelDocumentProvider() {
		maxCacheSize = DEFAULT_MAX_CACHE_SIZE;
		minimumHitsToKeep = DEFAULT_MINIMUM_HITS_TO_KEEP;
		lock = new Object();
		documentCache = new HashMap<File, IDocument>();
		cacheHits = new HashMap<File, Integer>();
		cacheOrder = new LinkedList<File>();
		cacheTimes = new HashMap<File, Long>();
	}
	
	public IDocument getDocument(final File file) {
		IDocument document = null;
		synchronized(lock) { 
			if (documentCache.containsKey(file)) {
				// see if it's valid
				if (!file.exists()) {
					flushEntry(file);
				} else  {
					long lastMod = file.lastModified();
					long cachedLastMod = cacheTimes.get(file);
					if (lastMod != cachedLastMod) {
						flushEntry(file);
					} else {
						if (DUMP)
							System.out.println("Used cached document for " + file); //$NON-NLS-1$
						document = documentCache.get(file);
						cacheHits.put(file, cacheHits.get(file) + 1);
						cacheOrder.remove(file);
						cacheOrder.add(0, file);
					}
				}
			} 
			if (document == null) {
				char[] text;
				try {
					text = FileUtils.readFileContents(file, null);
				} catch (CoreException e) {
					return null;
				}
				document = DocumentFactory.createDocument(new String(text));
				documentCache.put(file, document);
				cacheTimes.put(file, file.lastModified());
				cacheHits.put(file, 1);
				cacheOrder.add(file);
				/*
				document.addDocumentListener(new IDocumentListener() {
	
					public void documentAboutToBeChanged(DocumentEvent event) {
					}
	
					public void documentChanged(DocumentEvent event) {
						if (DUMP)
							System.out.println("Detected change to document " + file); //$NON-NLS-1$
						flushTu(file);
					}
					
				});*/
	
			}
			return document;
		}
	}
	
	/**
	 * Reduce cache size.
	 */
	private void reduceCache() {
		int toRemove = documentCache.size() - maxCacheSize;
		if (toRemove <= 0)
			return;
		
		// try initial scan, removing obviously changed or unreferenced entries
		for (ListIterator<File> iter = cacheOrder.listIterator(cacheOrder.size());
			toRemove > 0 && iter.hasPrevious();) 
		{
			File file = iter.previous();
			Long cacheTime = cacheTimes.get(file);
			Integer cacheHit = cacheHits.get(file);
			if (!file.exists()
					|| cacheTime == null || cacheTime != file.lastModified()
					|| cacheHit == null || cacheHit < minimumHitsToKeep) {
				flushEntry(file);
				toRemove--;
			}
		}
		
		// this second scan, which should usually be skipped
		// because toRemove==0, removes referenced entries too
		for (ListIterator<File> iter = cacheOrder.listIterator(cacheOrder.size());
			toRemove > 0 && iter.hasPrevious();) 
		{
			File file = iter.previous();
			flushEntry(file);
			toRemove--;
		}
		
	}

	/**
	 * Flush one file entry.
	 */
	private void flushEntry(File file) {
		if (DUMP)
			System.out.println("Releasing TU for " + file); //$NON-NLS-1$
		documentCache.remove(file);
		cacheHits.remove(file);
		cacheTimes.remove(file);
		cacheOrder.remove(file);
	}

	/**
	 * Configure the debugging flag.  Results are sent
	 * to System.out.
	 * @param flag
	 */
	public void setDebugFlag(boolean flag) {
		this.DUMP = flag;
	}
	
	/**
	 * Set the maximum number of TU's to keep.
	 * <p>
	 * This attempts to reduces the cache.
	 * @param count number of TUs, must be nonnegative
	 */
	public void setMaxCacheCount(int count) {
		Check.checkArg(count >= 0);
		synchronized(lock) {
			this.maxCacheSize = count;
			reduceCache();
		}
	}
	
	/**
	 * Set the minimum number of hits setting, which
	 * controls the lower limit at which a TU will be kept
	 * in the cache.
	 * <p>  
	 * This attempts to reduces the cache.
	 * @param minHits minimum hits, must be nonnegative
	 */
	public void setMinimumHitsToKeep(int minHits) {
		Check.checkArg(minHits >= 0);
		synchronized(lock) {
			this.minimumHitsToKeep = minHits;
			reduceCache();
		}
	}
}
