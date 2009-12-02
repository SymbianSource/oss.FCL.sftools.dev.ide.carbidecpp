/*
* Copyright (c) 2007-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.model;

import com.nokia.carbide.cpp.epoc.engine.model.*;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.*;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * This cache manages the data associated with an {@link IViewConfiguration} over a particular IModel.
 * A model's IView must implement #getData() meaningfully for this to be used.
 *
 */
public class ViewDataCache<OwnedModel extends IOwnedModel, Model extends IModel, View extends IView, Data extends IData<View>> {

	public static boolean DEBUG = false;
	
	/** A key which can retrieve the current state of a model for a given filter. */
	static class ViewConfigKey extends Pair<IPath, IViewFilter> {
		public ViewConfigKey(IPath first, IViewFilter second) {
			super(first, second);
		}
	}
	
	/** The state of a view configuration, excluding the view filter. */
	static class ViewConfigState {
		private Object manifest;
		public ViewConfigState(IViewConfiguration second) {
			//manifest = getMD5(getUniqueKey(second));
			manifest = getUniqueKey(second);
		}

		/**
		 * @param uniqueKey
		 * @return
		 */
		/*private*/ Object getMD5(String uniqueKey) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				return md.digest(uniqueKey.getBytes());
			} catch (NoSuchAlgorithmException e) {
				return uniqueKey;
			}
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return manifest.toString();
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((manifest == null) ? 0 : manifest.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final ViewConfigState other = (ViewConfigState) obj;
			if (manifest == null) {
				if (other.manifest != null)
					return false;
			} else if (!manifest.equals(other.manifest))
				return false;
			return true;
		}



		/**
		 * Get a unique key from the view configuration so we can compare its state to another.
		 * @param second
		 * @return Object implementing #equals and #hashCode properly
		 */
		private static String getUniqueKey(IViewConfiguration viewConfiguration) {
			String macroState = getMacroState(viewConfiguration.getMacros());
			String filterState = "" + viewConfiguration.getViewFilter().hashCode(); //$NON-NLS-1$
			IViewParserConfiguration parserConfig = viewConfiguration.getViewParserConfiguration();
			String includeState = getIncludeState(parserConfig.getIncludeFileLocator());
			String projectState = parserConfig.getProjectLocation().toOSString();
			return filterState + "/" + projectState + "/" + includeState + "/" + macroState;
		}

		/**
		 * @param includeFileLocator
		 * @return
		 */
		private static String getIncludeState(
				IIncludeFileLocator includeFileLocator) {
			StringBuilder builder = new StringBuilder();
			File[] dirs = includeFileLocator.getUserPaths();
			for (File dir : dirs) {
				builder.append(dir.getAbsolutePath());
				builder.append('\u0001');
			}
			dirs = includeFileLocator.getSystemPaths();
			for (File dir : dirs) {
				builder.append(dir.getAbsolutePath());
				builder.append('\u0001');
			}
			return builder.toString();
		}

		/**
		 * @param macros
		 * @return
		 */
		private static String getMacroState(Collection<IDefine> macros) {
			StringBuilder builder = new StringBuilder();
			
			// assume that the macros are in a sensible order to begin with
			/*
			ArrayList<IDefine> sortedMacros = new ArrayList<IDefine>(macros);
			Collections.sort(sortedMacros, new Comparator<IDefine> () {

				public int compare(IDefine o1, IDefine o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
			for (IDefine define : sortedMacros) {
			*/
			for (IDefine define : macros) {
				builder.append(define.getDefinitionText());
				builder.append('\u0001');
			}
			return builder.toString();
		}
		
	}

	static class FileTimestampSizeCollection {
		static FileTimestampSizeCollection INSTANCE = new FileTimestampSizeCollection();
		/** Check timestamps only once in this number of milliseconds */
		final int QUANTUM = 0;
		
		static class FileInfo extends Tuple {
			public FileInfo(long lastModified, long lastQueried, long size) {
				super(lastModified, lastQueried, size);
			}
			public long getLastModified() {
				return (Long) get(0);
			}
			public long getLastQueried() {
				return (Long) get(0);
			}
			public long getLength() { 
				return (Long) get(2);
			}
		}
		/** map of file to file size + last queried timestamp + time of last query 
		 * (use File, not IPath, so we canonicalize for the OS) */
		private Map<File, FileInfo> info = new HashMap<File, FileInfo>();		
		
		
		/** Tell if the file's timestamp changed in the past quantum 
		 * and update the record */
		public boolean changed(File file) {
			long now = System.currentTimeMillis();
			FileInfo finfo = info.get(file);
			if (finfo == null) {
				finfo = new FileInfo(file.lastModified(), now, file.length());
				info.put(file, finfo);
				if (DEBUG) System.out.println("First info for " + file + ": " + finfo);
				return true;
			} else if (finfo.getLastQueried() + QUANTUM < now) {
				// don't check times more than QUANTUM
				long origTime = finfo.getLastModified();
				long origSize = finfo.getLength();
				finfo = new FileInfo(file.lastModified(), now, file.length());
				info.put(file, finfo);
				if (DEBUG) System.out.println("Updated info for " + file + ": " + origTime + "/" + origSize + " <=> " 
						+ finfo.getLastModified() + "/" + finfo.getLength());
				return origTime != finfo.getLastModified() || finfo.getLastModified() == 0 // 0 if deleted
					|| origSize != finfo.getLength();		
			} else {
				// not changed, as far as we assume
				if (DEBUG) System.out.println("Assuming no change for " + file);
				return false;
			}
		}
	}
	
	public static class ModelFileTimestampCollection {
		/**
		 * The minimum timestamp resolution for a file in ms (based on heuristics for the OS).
		 * VFAT on Win32 uses 2 second increments.  Linux ext2/3 uses 1 second resolution, 
		 * until ext4, where it becomes nanoseconds.
		 * Assume the worst format in all cases.
		 */
		public static final long MIN_TIMESTAMP_RESOLUTION = HostOS.IS_WIN32 ? 2000 : 1000;
		/**
		 * Delay in ms between successive checks of the filesystem, to avoid wasting time
		 * when such checks are slow, and in cases where it's unlikely the human will edit files
		 * fast enough to care.
		 */
		public static final long QUANTUM = HostOS.IS_WIN32 ? 50 : 10;
		private File[] files;
		private long lastQuery;

		public ModelFileTimestampCollection(IView view) {
			IPath[] paths = view.getReferencedFiles();
			this.files = new File[paths.length];
			int idx = 0;
			for (IPath path : paths) {
				files[idx] = path.toFile();
				// prime the cache
				FileTimestampSizeCollection.INSTANCE.changed(files[idx]);
				idx++;
			}
			this.lastQuery = System.currentTimeMillis();
		}

		/**
		 * Tell if any of the files have changed
		 * @return
		 */
		public boolean changed() {
			long prevQuery = lastQuery;
			lastQuery = System.currentTimeMillis();
			
			// don't check more often than the resolution of the file allows
			if (prevQuery + QUANTUM > lastQuery) {
				if (DEBUG) System.out.println("Skipping fileinfo check");
				return false;
			}
			
			for (File file : files) {
				if (FileTimestampSizeCollection.INSTANCE.changed(file)) {
					return true;
				}
			}
			return false;
		}
	}
	
	/** the minimum number of hits (accesses) to the entry to keep it when flushing cache. */
	private static final int DEFAULT_MINIMUM_HITS_TO_KEEP = 8;

	private IModelProvider<OwnedModel, Model> modelProvider;
	private Map<ViewConfigKey, Pair<ViewConfigState, Data>> cachedData;
	private Map<ViewConfigKey, ModelFileTimestampCollection> cachedTimestamps;
	private Map<ViewConfigKey, Integer> cacheHits;
	private List<ViewConfigKey> cacheOrder;

	private int maxCacheSize;

	private int minimumHitsToKeep;

	public ViewDataCache(IModelProvider<OwnedModel, Model> provider, int maxCacheSize) {
		this.maxCacheSize = maxCacheSize;
		this.minimumHitsToKeep = DEFAULT_MINIMUM_HITS_TO_KEEP;
		
		this.modelProvider = provider;
		this.cachedData = new HashMap<ViewConfigKey, Pair<ViewConfigState,Data>>();
		this.cachedTimestamps = new HashMap<ViewConfigKey, ModelFileTimestampCollection>();
		this.cacheHits = new HashMap<ViewConfigKey, Integer>();
		this.cacheOrder = new LinkedList<ViewConfigKey>();
	}
	
	/**
	 * Get cached data for the model and the given configuration.
	 * @param modelPath full filesystem path to model
	 * @param configuration view configuration to use
	 * @return cached data for the model/configuration tuple or <code>null</code> if data could not be determined
	 * @throws CoreException
	 */
	public Data getData(IPath modelPath, IViewConfiguration configuration) throws CoreException {
		// handle incorrect use of non-absolute paths
		if (Platform.isRunning()) {
			if (modelPath.segmentCount() > 0
					&& (!modelPath.isAbsolute() || !modelPath.toFile().exists())) {
				IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
				IPath resourcePath = null;
				IResource resource = root.getFile(modelPath);
				if (resource != null) {
					resourcePath = resource.getLocation();
				}
				
				if (resourcePath != null) {
					modelPath = resourcePath;
				} else {
					return null;
					/*
					// Kinda tricky: get the real project location first
					IProject project = root.getProject(modelPath.segment(0));
					if (project == null)
						return null;
					IPath projectPath = ProjectUtils.getRealProjectLocation(project);
					if (projectPath != null) {
						modelPath = projectPath.append(modelPath.removeFirstSegments(1));
					}
					*/
				}
			}
		}
		try {
			modelPath = new Path(modelPath.toFile().getCanonicalPath());
		} catch (IOException e) {
		}
		
		Data data = null;
		ViewConfigState state = new ViewConfigState(configuration);
		ViewConfigKey key = new ViewConfigKey(modelPath, configuration.getViewFilter());

		synchronized (cachedData) {
			reduceCache(key);
			data = findData(modelPath, configuration, state, key);
		}
		
		// do not lock while fetching data
		if (data == null) {
			data = getViewData(modelPath, configuration, state, key);
		}
		
		
		return data;
	}

	/**
	 * Find data in cache.  Called with cachedData locked.
	 * @param modelPath
	 * @param configuration
	 * @param state
	 * @param key
	 * @return the data, or <code>null</code> if not cached
	 * @throws CoreException
	 */
	private Data findData(IPath modelPath,
			IViewConfiguration configuration, ViewConfigState state,
			ViewConfigKey key) throws CoreException {
		
		Data data = null;
		Pair<ViewConfigState, Data> statefulData;
		
		if (DEBUG) {
			//System.out.println("State = " + state);
		}
		statefulData = cachedData.get(key);
		if (statefulData != null) {
			// now check that the file timestamps are valid.
			ModelFileTimestampCollection timestamps = cachedTimestamps.get(key);
			if (timestamps.changed()) {
				if (DEBUG) {
					System.out.println("One or more relevant files changed for " + modelPath);
				}
				removeAllEntriesForModel(modelPath);
				statefulData = null;
			} else {
				// see if the parse context is still the same
				if (!statefulData.first.equals(state)) {
					if (DEBUG) {
						String orig = statefulData.first.toString();
						String curr = state.toString();
						System.out.println("State changed (from:\n" 
								+ orig.substring(0, Math.min(100, orig.length())) + "\nto:\n" + 
								curr.substring(0, Math.min(100, curr.length())) + ")\n");
					}
					removeEntry(key);
					cachedData.remove(key);
					statefulData = null;
				}
			}
		}
		
		if (statefulData != null) {
			if (DEBUG) {
				System.out.println("Found entry for " + key);
			}
			cacheHits.put(key, cacheHits.get(key) + 1);
			data = statefulData.second;
		}
		return data;
	}

	/**
	 * Get the data from a view.  This does not hold cachedData -- instead,
	 * we allow the risk of caching data twice to avoid deadlocking when
	 * one thread attempts to get cached data on a view which has trigged
	 * code on a save (boog 7882).
	 * <p>
	 * TODO: may be possible to lock only for a particular use of ViewConfigKey.
	 * As it is, this locks the whole cache for each model.
	 * </p>
	 * @param modelPath
	 * @param configuration
	 * @param state
	 * @param key
	 * @return
	 * @throws CoreException
	 */
	private Data getViewData(IPath modelPath, IViewConfiguration configuration,
			ViewConfigState state, ViewConfigKey key) throws CoreException {
		Data data;
		if (DEBUG) {
			System.out.println("Fetching view data for " + key);
		}
		Model model = modelProvider.getSharedModel(modelPath);
		if (model == null)
			return null;
		try {
			View view = (View) model.createView(configuration);
			if (view == null)
				return null;
			
			try {
				data = (Data) view.getData();
				if (data == null)
					return null;
				
				ModelFileTimestampCollection timestamps = new ModelFileTimestampCollection(view);
				synchronized (cachedData) {
					// the data may have already been registered... oh well
					cachedData.put(key, new Pair<ViewConfigState, Data>(state, data));
					cachedTimestamps.put(key, timestamps);
					cacheOrder.add(0, key);
					cacheHits.put(key, 0);
				}
			} finally {
				view.dispose();
			}
		} finally {
			modelProvider.releaseSharedModel(model);
		}
		return data;
	}

	/**
	 * @param modelPath
	 */
	private void removeAllEntriesForModel(IPath modelPath) {
		ViewConfigKey[] keys = (ViewConfigKey[]) cachedData.keySet().toArray(new ViewConfigKey[cachedData.keySet().size()]);
		for (ViewConfigKey key : keys) {
			if (key.first.equals(modelPath)) {
				removeEntry(key);
			}
		}
	}

	/**
	 * @param key
	 */
	private void removeEntry(ViewConfigKey key) {
		cachedTimestamps.remove(key);
		cacheHits.remove(key);
		cacheOrder.remove(key);
		cachedData.remove(key);
	}

	/**
	 * Reduce the cache, leaving items for 'retainKey' alone
	 * @param key
	 */
	private void reduceCache(ViewConfigKey retainKey) {
		if (cachedData.size() < maxCacheSize)
			return;
		
		// threshold to remove enough from cache for the next run
		int toRemove = maxCacheSize / 8;
		
		// first pass, to get stragglers
		ViewConfigKey[] keys = (ViewConfigKey[]) cacheOrder.toArray(new ViewConfigKey[cacheOrder.size()]);
		for (int idx = keys.length;
			toRemove > 0 && idx > 0; ) {
			ViewConfigKey key = keys[--idx];
			if (key.equals(retainKey))
				continue;
			Integer hits = cacheHits.get(key);
			if (hits == null || hits < minimumHitsToKeep) {
				if (DEBUG) {
					System.out.println("*** Flushing " + key);
				}
				removeEntry(key);
				toRemove--;
			}
		}
		
		// second pass, remove any to reduce size
		keys = (ViewConfigKey[]) cacheOrder.toArray(new ViewConfigKey[cacheOrder.size()]);
		for (int idx = keys.length;
			toRemove > 0 && idx > 0; ) {
			ViewConfigKey key = keys[--idx];
			if (key.equals(retainKey))
				continue;
			if (DEBUG) {
				System.out.println("*** Flushing " + key);
			}
			removeEntry(key);
			toRemove--;
		}

	}
	
}
