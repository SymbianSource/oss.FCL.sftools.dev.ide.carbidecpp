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
*/
package com.nokia.carbide.cpp.internal.api.sdk;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.ObjectStreamException;
import java.util.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;

import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.model.sbv.ISBVView;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.*;
import com.nokia.carbide.cpp.internal.sdk.core.model.SymbianSDK;
import com.nokia.carbide.cpp.sdk.core.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.BasicIncludeFileLocator;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.MacroScanner;
import com.nokia.cpp.internal.api.utils.core.ExternalFileInfoCollection;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.core.ObjectUtils;

/**
 * This class holds the externally gathered data for a build context,
 * such as the #include paths and macros defined in the SDK.
 * <p> 
 * Unlike CarbideBuildConfiguration (which, unfortunately extends 
 * SymbianBuildContext), this will not be created multiple times for
 * multiple projects, but only once for a different build context
 * in an SDK or devkit.
 */
public class SymbianBuildContextDataCache {

	public static boolean DEBUG = false;
	
	// by default, only check HRH-included files if changed in last second (for ordinary operations)
	// or the last minute (when doing a long project operation).  see #startThrottle() and #stopThrottle()
	private static final long DEFAULT_HRH_INFO_CHECK_QUANTUM = 1000; // 1 sec
	private static final long THROTTLED_HRH_INFO_CHECK_QUANTUM = 60000; // 60 sec

	// compiler prefixes are very unlikely to change, but we need to check
	// occasionally in case a user installs a new one...
	private static final long DEFAULT_COMPILER_PREFIX_INFO_CHECK_QUANTUM = 15 * 60 * 1000;	// 15 minutes
	
	// This is a count of times #startProjectOperation() was called without
	// balancing #endProjectOperation().  
	private static int inProjectOperationCount;
	
	private static Map<String, SymbianBuildContextDataCache> cacheMap = new HashMap<String, SymbianBuildContextDataCache>();
	
	public static synchronized SymbianBuildContextDataCache getCache(ISymbianBuildContext context) {
		// don't hash on ISymbianBuildContext itself since it is sometimes a ICarbideBuildConfiguration
		String key = getBuildContextKey(context);
		
		SymbianBuildContextDataCache cache = cacheMap.get(key);
		if (cache == null) {
			cache = new SymbianBuildContextDataCache(context);
			cache.loadCacheFile();
			cacheMap.put(key, cache);
		}
		return cache;
	}

	/**
	 * @param context
	 * @return
	 */
	private static String getBuildContextKey(ISymbianBuildContext context) {
		String key;
		if (context instanceof ISBSv2BuildContext) {
			// use config ID instead of platform + target since
			// platform and target can be the same for different build contexts
			ISBSv2BuildContext v2Context = (ISBSv2BuildContext) context;
			key = v2Context.getConfigID() + "/";
		}
		else {
			key = context.getPlatformString() + "/" + context.getTargetString() + "/";
		}
		ISymbianSDK sdk = context.getSDK();
		if (sdk != null)
			key += sdk.getEPOCROOT();
		return key;
	}

	//private File prefixFileParsed;
	private List<File> hrhFilesParsed = new ArrayList<File>();
	private ExternalFileInfoCollection hrhFileInfo = null; 
	private List<IDefine> variantHRHMacros = new ArrayList<IDefine>();
	private List<IDefine> compilerPrefixMacros = new ArrayList<IDefine>();
	private ExternalFileInfoCollection compilerPrefixFileInfo = null; 
	private List<File> systemIncludes;
	private ISymbianSDK sdk;
	private ISymbianBuildContext context;
	private IPath compilerPrefixFile;

	private String platformString;

	private String displayString;

	private String contextKey;

	/**
	 * One of {@link ISymbianBuilderID}
	 */
	private String builderId;

	private boolean changed;

	private File cacheFile;

	private SymbianBuildContextDataCache(ISymbianBuildContext context) {
		if (DEBUG) System.out.println("Creating cache for " + context.getDisplayString());
		this.context = context;
		this.platformString = context.getPlatformString();
		this.displayString = context.getDisplayString();
		this.sdk = context.getSDK();
		this.contextKey = getBuildContextKey(context);
		if (context instanceof ISBSv1BuildContext) {
			builderId = ISymbianBuilderID.SBSV1_BUILDER;
		} else {
			builderId = ISymbianBuilderID.SBSV2_BUILDER;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Cache for " + displayString;
	}
	
	public List<IDefine> getVariantHRHDefines() {

		// we parse the variant hrh file to gather macros.  this can be time consuming so do it
		// once and cache the values.  only reset the cache when the hrh or any of its includes
		// has changed.
		
		boolean buildCache = false;
		
		if (hrhFileInfo == null) {
			// hasn't been built yet, or was flushed
			buildCache = true;
		} else {
			// Cache exists.  See if any of the files have changed
			if (sdk != null && hrhFileInfo.anyChanged()) {
				buildCache = true;
			}
		}
		
		if (buildCache) {
			gatherVariantHRHDefines();
		}
			
		return variantHRHMacros;
	}

	/**
	 * Re-gather the #defines from the variant HRH file 
	 */
	private void gatherVariantHRHDefines() {
		changed = true;
		variantHRHMacros.clear();
		
		synchronized (this) {

			List<IDefine> macros = new ArrayList<IDefine>();
			Map<String, IDefine> namedMacros = new HashMap<String, IDefine>();
			
			ISDKBuildInfo sdkBuildInfo = sdk.getBuildInfo(builderId);
			File prefixFile = sdkBuildInfo.getPrefixFromVariantCfg().toFile();
			ISDKBuildInfo buildInfo = sdk.getBuildInfo(builderId);
			
			if (prefixFile == null){
				// Check that the prefix file may have become available since the SDK was scanned last.
				// This can happen, for e.g., if the user opens the IDE _then_ does a subst on a drive that already has an SDK entry.
				IPath prefixCheck = buildInfo.getPrefixFromVariantCfg();
				if (prefixCheck != null){
					prefixFile = prefixCheck.toFile();
					((SymbianSDK)sdk).setPrefixFile(prefixCheck, builderId);
				}
			}
			
			File[] includedFiles = null;

			if (prefixFile != null) {
				
				List<File> systemPaths = new ArrayList<File>();
				// Always add epoc32/include to the search path as this is implicit for includes in the HRH
				systemPaths.add(new File(sdk.getEPOCROOT() + "epoc32/include"));
				
				if (buildInfo instanceof ISBSv1BuildInfo) {
					// add any BSF/SBV includes so the headers are picked up from the correct location
					// SBSv1 only
					ISBSv1BuildInfo sbsv1BuildInfo = (ISBSv1BuildInfo)buildInfo;
					IBSFPlatform bsfPlat = sbsv1BuildInfo.getBSFCatalog().findPlatform(platformString);
					ISBVPlatform sbvPlat = sbsv1BuildInfo.getSBVCatalog().findPlatform(platformString);

					if (bsfPlat != null) {
						for (IPath path : bsfPlat.getSystemIncludePaths()) {
							systemPaths.add(path.toFile());
						}
					} else if (sbvPlat != null) {
						LinkedHashMap<IPath, String> platPaths = sbvPlat.getBuildIncludePaths();
						Set<IPath> set = platPaths.keySet();
						for (IPath path : set) {
							String pathType = platPaths.get(path);
							if (pathType.equalsIgnoreCase(ISBVView.INCLUDE_FLAG_PREPEND) || pathType.equalsIgnoreCase(ISBVView.INCLUDE_FLAG_SET)){
								systemPaths.add(path.toFile());
							}
						}
					}
				} 
				
				MacroScanner scanner = new MacroScanner(
						new BasicIncludeFileLocator(null, systemPaths.toArray(new File[systemPaths.size()])),
						DefaultModelDocumentProvider.getInstance(),
						DefaultTranslationUnitProvider.getInstance());
				scanner.scanFile(prefixFile);

				List<IDefine> scannedMacros = (List<IDefine>)scanner.getMacroDefinitions();
				for (IDefine scannedMacro : scannedMacros){
					// we don't want duplicate macros, so check to see if it's already there.
					// if it is, remove it and then add the newer one.  this is consistent with
					// how it would be from a compiler standpoint.
					IDefine macro = namedMacros.get(scannedMacro.getName());
					if (macro != null) {
						macros.remove(macro);
					}
					
					macros.add(scannedMacro);
					namedMacros.put(scannedMacro.getName(), scannedMacro);
				}
				
				hrhFilesParsed.clear();
				includedFiles = scanner.getIncludedFiles();
				for (File inc : includedFiles) {
					hrhFilesParsed.add(inc);
				}
				
				if (buildInfo instanceof ISBSv1BuildInfo) {
					// SBSv2 does not parse the variant.cfg file to collect macros.
					List<String> variantCFGMacros = new ArrayList<String>();
					
					variantCFGMacros = ((ISBSv1BuildInfo)buildInfo).getVariantCFGMacros();
					for (String cfgMacros : variantCFGMacros){
						// we don't want duplicate macros, so check to see if it's already there.
						IDefine existingMacro = namedMacros.get(cfgMacros);
						if (existingMacro != null) {
							macros.remove(existingMacro);
						}
						
						IDefine macro = DefineFactory.createSimpleFreeformDefine(cfgMacros);
						macros.add(macro);
						namedMacros.put(macro.getName(), macro);
					}
				}
			} 
			
			// cache the info about when we created the cache
			if (hrhFileInfo == null) {
				hrhFileInfo = new ExternalFileInfoCollection(
						EpocEnginePlugin.getExternalFileInfoCache(),
						includedFiles,
						DEFAULT_HRH_INFO_CHECK_QUANTUM);
			} else {
				hrhFileInfo.setFiles(includedFiles);
			}

			variantHRHMacros = macros;
			saveCacheFile();
		}
	}

	public List<File> getPrefixFileIncludes() {
		return hrhFilesParsed;
	}


	public synchronized List<IDefine> getCompilerMacros(IPath prefixFile) {
		
		// we assume that the prefix file will not change often,
		// (if at all) for a build context, so dump the cache if the prefix file changes.
		
		if (compilerPrefixFile != null && prefixFile != null && 
			!compilerPrefixFile.equals(prefixFile)) {
			compilerPrefixFileInfo = null;
		}
		
		compilerPrefixFile = prefixFile;

		if (compilerPrefixFileInfo == null ||
				compilerPrefixFileInfo.anyChanged()) {

			changed = true;

			compilerPrefixMacros.clear();
			
			synchronized (this) {

				List<IDefine> macros = new ArrayList<IDefine>();
				if (prefixFile != null) {

					List<File> userPaths = new ArrayList<File>();
					List<File> systemPaths = new ArrayList<File>();
					
					userPaths.add(prefixFile.removeLastSegments(1).toFile());
					systemPaths.add(prefixFile.removeLastSegments(1).toFile());
					IPath includePath = sdk.getIncludePath();
					if (includePath != null) {
						File includeDir = includePath.toFile().getAbsoluteFile();
						userPaths.add(includeDir);
						systemPaths.add(includeDir);
					}

					
					// get macros from the compiler prefix file: note, this is a stupid
					// scan that will get the last version #defined, even if inside an #if.
					MacroScanner scanner = new MacroScanner(
							new BasicIncludeFileLocator(userPaths.toArray(new File[userPaths.size()]), systemPaths.toArray(new File[systemPaths.size()])),
							DefaultModelDocumentProvider.getInstance(), 
							DefaultTranslationUnitProvider.getInstance());
					scanner.scanFile(prefixFile.toFile());
					for (IDefine define : scanner.getMacroDefinitions()) {
						macros.add(define);
					}

					// store off the info about what we read for this cache
					File[] files = scanner.getIncludedFiles();
					
					if (compilerPrefixFileInfo == null)
						compilerPrefixFileInfo = new ExternalFileInfoCollection(
								EpocEnginePlugin.getExternalFileInfoCache(),
								files,
								DEFAULT_COMPILER_PREFIX_INFO_CHECK_QUANTUM);
					else
						compilerPrefixFileInfo.setFiles(files);
				}
				
				if (context instanceof ISBSv2BuildContext) {
					// add macros from raptor query
					ISBSv2BuildContext v2Context = (ISBSv2BuildContext) context;
					ISBSv2ConfigQueryData configData = v2Context.getConfigQueryData();
					if (configData != null) {
						Map<String, String> buildMacros = configData.getBuildMacros();
						if (buildMacros != null) {
							for (Iterator<String> itr = buildMacros.keySet().iterator(); itr.hasNext(); ) { 
								String name = itr.next();
								String value = buildMacros.get(name);
								macros.add(DefineFactory.createDefine(name, value));
							}
						}
					}
				}

				compilerPrefixMacros = macros;
				
				saveCacheFile();
			}
		}
			
		return compilerPrefixMacros;
	}

	/**
	 * Get the list of #include paths detected for this context.
	 * @return List or <code>null</code>
	 */
	public synchronized List<File> getSystemIncludePaths() {
		if (systemIncludes == null) {
			gatherBuildContextSystemIncludePaths();
		}
		return systemIncludes;
	}

	/**
	 * Fetch the list of include paths for the build context
	 */
	private void gatherBuildContextSystemIncludePaths() {
		changed = true;
		
		systemIncludes = new ArrayList<File>();
		
		if (DEBUG) System.out.println("Scanning include paths for " + displayString);
		
		IBSFPlatform bsfplatform = null;
		ISBVPlatform sbvPlatform = null;
		ISDKBuildInfo buildInfo = sdk.getBuildInfo(builderId);
		if (buildInfo instanceof ISBSv1BuildInfo) {
			// SBSv1 only
			ISBSv1BuildInfo sbsv1BuildInfo = (ISBSv1BuildInfo)buildInfo;
			bsfplatform = sbsv1BuildInfo.getBSFCatalog().findPlatform(platformString);
			sbvPlatform = sbsv1BuildInfo.getSBVCatalog().findPlatform(platformString);
		} 

		// look in the epoc32 directory of the SDK
		IPath includePath = sdk.getIncludePath();
		if (includePath != null) {
			File includeDir = includePath.toFile().getAbsoluteFile();
			File dir;
			
			// get additional include directories from BSF platform, if defined
			if (bsfplatform != null) {
				IPath[] systemIncludePaths = bsfplatform.getSystemIncludePaths();
				for (IPath path : systemIncludePaths) {
					dir = path.toFile();
					if (dir.exists() && dir.isDirectory()) {
						systemIncludes.add(dir);
					}
				}
			} else if (sbvPlatform != null){
				
				LinkedHashMap<IPath, String> platPaths = sbvPlatform.getBuildIncludePaths();
				Set<IPath> set = platPaths.keySet();
				for (IPath path : set) {
					String pathType = platPaths.get(path);
					if (pathType.equalsIgnoreCase(ISBVView.INCLUDE_FLAG_PREPEND) || pathType.equalsIgnoreCase(ISBVView.INCLUDE_FLAG_SET)){
						dir = path.toFile();
						systemIncludes.add(dir);
					}
				}
			}
			else {
				// legacy behavior 
				if (platformString.equals(ISBSv1BuildContext.EMULATOR_PLATFORM)) {
					dir = new File(includeDir, "wins"); //$NON-NLS-1$
					if (dir.exists() && dir.isDirectory()) {
						systemIncludes.add(dir);
					}
				}
			}

			// add OEM dir
			dir = new File(includeDir, "oem"); //$NON-NLS-1$
			if (dir.exists() && dir.isDirectory()) {
				systemIncludes.add(dir);
			}

			// and finally the normal include dir
			systemIncludes.add(includeDir);
			
			// and finally, finally, if this is an SBV add any paths with the append flag
			if (sbvPlatform != null){
				
				Map<IPath, String> platPaths = sbvPlatform.getBuildIncludePaths();
				Set<IPath> set = platPaths.keySet();
				for (IPath path : set) {
					String pathType = platPaths.get(path);
					if (pathType.equalsIgnoreCase(ISBVView.INCLUDE_FLAG_APPEND)){
						dir = path.toFile();
						systemIncludes.add(dir);
					}
				}	
			}
		}
		
		// also search files in same folder as variant.hrh
		ISDKBuildInfo sdkBuildInfo = sdk.getBuildInfo(builderId);
		File prefix = sdkBuildInfo.getPrefixFromVariantCfg().toFile();
		if (sbvPlatform != null){
			// might be an alternate HRH file to use
			IPath varVarHRH = sbvPlatform.getBuildVariantHRHFile();
			if (!varVarHRH.toFile().equals(prefix) && varVarHRH.toFile().exists()){
				prefix = varVarHRH.toFile();
			} 
		}
		if (prefix != null && prefix.getParentFile() != null) {
			systemIncludes.add(prefix.getParentFile());
		}
		
		saveCacheFile();
	}

	/**
	 * Let cache know that the client is beginning an operation that
	 * will iterate all the build contexts.  We use this information
	 * to optimize file info checks.
	 * <p>
	 * Each call must be balanced by an {@link #endProjectOperation()} call.
	 * Use a try ... finally block to make sure.
	 */
	public static synchronized void startProjectOperation() {
		inProjectOperationCount++;
		if (inProjectOperationCount == 1) {
			for (SymbianBuildContextDataCache cache : cacheMap.values()) {
				cache.startThrottle();
			}
		}
	}

	/**
	 * Let cache know that a project-wide operation is done, so
	 * we can resume normal info checking behavior.
	 */
	public static synchronized void endProjectOperation() {
		inProjectOperationCount--;
		if (inProjectOperationCount < 0) {
			Logging.log(SDKCorePlugin.getDefault(), 
					Logging.newStatus(SDKCorePlugin.getDefault(), 
							new IllegalStateException("project operation count not balanced")));
			inProjectOperationCount = 0;
		}
		if (inProjectOperationCount == 0) {
			for (SymbianBuildContextDataCache cache : cacheMap.values()) {
				cache.stopThrottle();
				cache.saveCacheFile();
			}
		}
	}

	/**
	 * Throttle file info checks on this cache if we suspect the same
	 * files will be checked over and over again in a short time (e.g. through
	 * multiple contexts on the same SDK).
	 */
	private void startThrottle() {
		if (hrhFileInfo != null) {
			hrhFileInfo.setRecheckQuantum(THROTTLED_HRH_INFO_CHECK_QUANTUM);
		}
		// note: compiler prefix infos already have a long delay, but
		// this is a good place to refresh
		if (compilerPrefixFileInfo != null) {
			compilerPrefixFileInfo.refresh();
		}
	}

	/**
	 * End file info throttling.
	 */
	private void stopThrottle() {
		if (hrhFileInfo != null)
			hrhFileInfo.setRecheckQuantum(DEFAULT_HRH_INFO_CHECK_QUANTUM);
		// note: compiler prefix infos already have a long delay
	}

	/**
	 * Refresh the cached data when there are substantial changes in the given SDKs.
	 * 
	 * @param sdks SDKs for whose contexts the caches should be removed, or <code>null</code> for all of them
	 */
	public static synchronized void refreshForSDKs(ISymbianSDK[] sdks) {
		// refresh each context cache, meaning, delete its memory and disk values
		Collection<String> values = cacheMap.keySet();
		String[] keyArray = (String[]) values.toArray(new String[values.size()]);
		for (String key : keyArray) {
			SymbianBuildContextDataCache cache = cacheMap.get(key);
			boolean forSDK = sdkInArray(sdks, cache.sdk);
			if (forSDK) {
				cache.reset();
				cacheMap.remove(key);
			}
		}
	}

	private static boolean sdkInArray(ISymbianSDK[] sdks, ISymbianSDK anSDK) {
		if (sdks == null)
			return true;
		if (anSDK == null)
			return false;
		// object identity is not appropriate; user may have renamed or moved an SDK
		for (ISymbianSDK sdk : sdks) {
			if (ObjectUtils.equals(sdk.getEPOCROOT(), anSDK.getEPOCROOT())
					|| ObjectUtils.equals(sdk.getUniqueId(), anSDK.getUniqueId()))
				return true;
		}
		return false;
	}
	
	/**
	 * Reset the cached data for this context, ensuring it will be
	 * freshly gathered.  This deletes anything stored on disk.
	 */
	public synchronized void reset() {
		hrhFileInfo = null;
		compilerPrefixFileInfo = null;
		systemIncludes = null;
		getCacheFile().delete();
	}

	/**
	 * Get the file where we store cached data
	 * @return File in workspace plugin state storage
	 */
	protected File getCacheFile() {
		if (cacheFile == null) {
			IPath statePath;
			if (Platform.isRunning())
				statePath = Platform.getStateLocation(SDKCorePlugin.getDefault().getBundle());
			else
				statePath = new Path(FileUtils.getTemporaryDirectory().getAbsolutePath());
			cacheFile = statePath.append(contextKey.replaceAll("[^A-Za-z0-9_]", "_") + ".dat").toFile(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		return cacheFile;
	}
	
	/**
	 * Save cache to disk if changed.
	 */
	protected void saveCacheFile() {
		if (!changed)
			return;
			
		ObjectOutputStream os = null;
		try {
			File cacheFile = getCacheFile();
			if (DEBUG) System.out.print("Saving to " + cacheFile + "... "); //$NON-NLS-1$ //$NON-NLS-2$
			os = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(cacheFile)));
			doSaveCache(os);
			changed = false;
			if (DEBUG) System.out.println("done."); //$NON-NLS-1$
		} catch (ObjectStreamException e) {
			Logging.log(SDKCorePlugin.getDefault(), 
					Logging.newStatus(SDKCorePlugin.getDefault(), 
							IStatus.WARNING,
							"Tried to save uncacheable data for " + displayString, //$NON-NLS-1$
							e));
		} catch (IOException e) {
			// oh well
			Logging.log(SDKCorePlugin.getDefault(), 
					Logging.newStatus(SDKCorePlugin.getDefault(), 
							IStatus.WARNING,
							"Failed to save cache state for " + displayString, //$NON-NLS-1$
							e));
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	protected void loadCacheFile() {
		ObjectInputStream is = null;
		// Use the class loader that knows how to span plugins
		final ClassLoader classLoader = SDKCorePlugin.getDefault().getClass().getClassLoader();
		File cacheFile = getCacheFile();
		try {
			is = new ObjectInputStream(new BufferedInputStream(new FileInputStream(cacheFile))) {
				/* (non-Javadoc)
				 * @see java.io.ObjectInputStream#resolveClass(java.io.ObjectStreamClass)
				 */
				@Override
				protected Class<?> resolveClass(ObjectStreamClass desc)
						throws IOException, ClassNotFoundException {
					String name = desc.getName();
					try {
						return classLoader.loadClass(name);
					} catch (ClassNotFoundException e) {
						return super.resolveClass(desc);
					}
				}
			};
			doLoadCache(is);
			if (DEBUG) System.out.println("Loaded cache from " + cacheFile); //$NON-NLS-1$
		} catch (ClassNotFoundException e) {
			// this is probably a bug
			e.printStackTrace();
		} catch (ObjectStreamException e) {
			// assume it's just out of sync (e.g. object signatures changed), so nothing in Error Log
			e.printStackTrace();
			cacheFile.delete();
		} catch (IOException e) {
			// oh well, not made yet
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
			changed = false;
		}
	}

	/**
	 * @param os
	 */
	private void doSaveCache(ObjectOutputStream os) throws IOException {
		os.writeObject(systemIncludes);
		savePath(os, compilerPrefixFile);
		os.writeObject(compilerPrefixFileInfo);
		os.writeObject(compilerPrefixMacros);
		os.writeObject(hrhFileInfo);
		os.writeObject(hrhFilesParsed);
		os.writeObject(variantHRHMacros);
	}

	
	/**
	 * Load entries from the cache.  This must match the ordering in
	 * {@link #doSaveCache(ObjectOutputStream)}.
	 * @param is
	 */
	private void doLoadCache(ObjectInputStream is) throws IOException, ClassCastException, ClassNotFoundException {
		systemIncludes = loadList(is, File.class);
		compilerPrefixFile = loadPath(is);
		compilerPrefixFileInfo = (ExternalFileInfoCollection) is.readObject();
		compilerPrefixMacros = loadList(is, IDefine.class);
		hrhFileInfo = (ExternalFileInfoCollection) is.readObject();
		hrhFilesParsed = loadList(is, File.class);
		variantHRHMacros = loadList(is, IDefine.class);
	}

	/**
	 * Read an IPath from a portable string
	 * @param is
	 * @return an IPath constructed from a String
	 */
	private IPath loadPath(ObjectInputStream is) throws IOException, ClassCastException, ClassNotFoundException {	
		String path;
		path = (String) is.readObject();
		if (path != null)
			return Path.fromPortableString(path);
		return null;
	}
	
	/**
	 * Save an IPath as a portable string
	 * @param os
	 * @param path
	 * @throws IOException
	 */
	private void savePath(ObjectOutputStream os, IPath path) throws IOException {
		os.writeObject(path != null ? path.toPortableString() : null);
	}

	/**
	 * Load a list from the object stream, throwing if it appears to contain
	 * the wrong kind of data.
	 * @param is
	 * @param klass
	 * @return a List or <code>null</code>
	 */
	@SuppressWarnings("unchecked")
	private <T> List<T> loadList(ObjectInputStream is, Class<T> klass) throws IOException, ClassCastException, ClassNotFoundException { 
		List<T> list = (List) is.readObject();
		if (list == null || klass == null || list.size() == 0)
			return list;
		if (!klass.isInstance(list.get(0)))
			throw new IOException("Class contains " + list.get(0).getClass() + ", not " + klass); //$NON-NLS-1$ //$NON-NLS-2$
		return list;
	}
}
