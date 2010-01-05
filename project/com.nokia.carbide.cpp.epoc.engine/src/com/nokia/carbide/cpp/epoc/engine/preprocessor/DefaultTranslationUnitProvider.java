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
* A default implementation of ITranslationUnitProvider which
* caches translation units.  It keeps a maximum number of
* translation units in memory at a time.  It refreshes a
* TU when the underlying file's timestamp changes.
* 
* @see ITranslationUnitProvider
*
*
*/
package com.nokia.carbide.cpp.epoc.engine.preprocessor;

import java.io.File;
import java.util.*;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.*;

import com.nokia.carbide.cpp.epoc.engine.model.IModelDocumentProvider;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.IDocumentParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ParserFactory;
import com.nokia.cpp.internal.api.utils.core.*;
public class DefaultTranslationUnitProvider implements ITranslationUnitProvider {

	private static DefaultTranslationUnitProvider instance = new DefaultTranslationUnitProvider();

	// not final or static for debugging
	private boolean DUMP = false;
	
	/** count of entries allowed */
	private static final int DEFAULT_MAX_CACHE_SIZE = 256;
	/** the minimum number of hits (accesses) to the TU to keep it when flushing cache. */
	private static final int DEFAULT_MINIMUM_HITS_TO_KEEP = 8;
	
	private Object lock;
	private Map<File, IASTTranslationUnit> tuCache;
	private Map<File, Integer> cacheHits;
	private List<File> cacheOrder;
	private Map<File, Long> cacheTimes;

	private int maxCacheSize;

	private int minimumHitsToKeep;
	
	/**
	 * Get the singleton instanceof the translation unit provider.
	 * The provider owns the cache of TUs.
	 * @return instance, never null
	 */
	public static DefaultTranslationUnitProvider getInstance() {
		return instance;
	}
	
	private DefaultTranslationUnitProvider() {
		maxCacheSize = DEFAULT_MAX_CACHE_SIZE;
		minimumHitsToKeep = DEFAULT_MINIMUM_HITS_TO_KEEP;
		lock = new Object();
		tuCache = new HashMap<File, IASTTranslationUnit>();
		cacheHits = new HashMap<File, Integer>();
		cacheOrder = new LinkedList<File>();
		cacheTimes = new HashMap<File, Long>();
	}
	
	public IASTTranslationUnit getTranslationUnit(final File file, final IModelDocumentProvider modelDocumentProvider) {
		IASTTranslationUnit tu = null;
		synchronized (lock) {
			final IDocument document = modelDocumentProvider.getDocument(file);
			if (document == null) {
				return null;
			}
			
			tu = tuCache.get(file);
			if (tu == null || tu.getMainDocument() != document) {
				if (DUMP)
					System.out.println("Parsing TU for " + file); //$NON-NLS-1$

				document.addDocumentListener(new IDocumentListener() {

					public void documentAboutToBeChanged(DocumentEvent event) {
					}

					public void documentChanged(DocumentEvent event) {
						synchronized (lock) {
							flushEntry(file);
							document.removeDocumentListener(this);
						}
					}
					
				});
				IDocumentParser parser = ParserFactory.createPreParser();
				tu = parser.parse(new Path(file.getAbsolutePath()), document);
				
				reduceCache();
				tuCache.put(file, tu);
				cacheHits.put(file, 0);
				cacheTimes.put(file, file.lastModified());
				cacheOrder.add(0, file);
			}
		}
		return tu;
	}

	/**
	 * Reduce cache size.
	 */
	private void reduceCache() {
		int toRemove = tuCache.size() - maxCacheSize;
		if (toRemove <= 0)
			return;
		
		// try initial scan, removing obviously changed or unreferenced entries
		File[] cached = (File[]) cacheOrder.toArray(new File[cacheOrder.size()]);
		int idx;
		for (idx = cached.length; toRemove > 0 && idx > 0;) {
			File file = cached[--idx];
			Long time = cacheTimes.get(file);
			Integer hits = cacheHits.get(file);
			if (!file.exists()
					|| time == null || time != file.lastModified()
					|| hits == null || hits < minimumHitsToKeep) {
				flushEntry(file);
				toRemove--;
			}
		}
		
		// this second scan, which should usually be skipped
		// because toRemove==0, removes referenced entries too
		cached = (File[]) cacheOrder.toArray(new File[cacheOrder.size()]);
		for (idx = cached.length; toRemove > 0 && idx > 0;) 
		{
			File file = cached[--idx];
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
		tuCache.remove(file);
		// do not lose info about file importance
		//cacheHits.remove(file);
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
