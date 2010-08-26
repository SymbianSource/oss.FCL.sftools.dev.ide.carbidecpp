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
package com.nokia.carbide.cpp.internal.sdk.core.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.osgi.framework.Version;

import com.nokia.carbide.cpp.internal.api.sdk.BuildPlat;
import com.nokia.carbide.cpp.internal.api.sdk.ICarbideDevicesXMLChangeListener;
import com.nokia.carbide.cpp.internal.api.sdk.ISDKManagerInternal;
import com.nokia.carbide.cpp.internal.api.sdk.ISDKManagerLoadedHook;
import com.nokia.carbide.cpp.internal.api.sdk.SBSv2Utils;
import com.nokia.carbide.cpp.internal.api.sdk.SDKCacheUtils;
import com.nokia.carbide.cpp.internal.api.sdk.SDKManagerInternalAPI;
import com.nokia.carbide.cpp.internal.api.sdk.SymbianBuildContextDataCache;
import com.nokia.carbide.cpp.internal.api.sdk.SymbianMacroStore;
import com.nokia.carbide.cpp.internal.api.sdk.sbsv2.SBSv2QueryUtils;
import com.nokia.carbide.cpp.sdk.core.ICarbideInstalledSDKChangeListener;
import com.nokia.carbide.cpp.sdk.core.ICarbideInstalledSDKChangeListener.SDKChangeEventType;
import com.nokia.carbide.cpp.sdk.core.IRVCTToolChainInfo;
import com.nokia.carbide.cpp.sdk.core.ISDKManager;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDKFeatures;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.carbide.cpp.sdk.core.SymbianSDKFactory;
import com.nokia.cpp.internal.api.utils.core.ListenerList;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.core.PathUtils;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

public abstract class AbstractSDKManager implements ISDKManager, ISDKManagerInternal {
	
	protected static List<ISymbianSDK> sdkList = new ArrayList<ISymbianSDK>();
	protected HashMap<String,ISymbianSDK> missingSdkMap = new HashMap<String,ISymbianSDK>();
	protected Job scanJob;

	public static final String SDK_MANAGER_CACHE_KEY = "sdk_manager_cache";

	protected static final String EMPTY_STRING = "";
	protected static boolean enableBSFScanner;
	protected static List<BuildPlat> platList = new ArrayList<BuildPlat>();
	protected static SymbianMacroStore macroStore;
	
	protected static final String[] knownRVCTVersions = {"3.1", "3.0", "2.2", "2.1"};
	protected Version sbsV2Version;
	
	static boolean sdkHookExtenstionsNotified;
	public static final String SDK_MANAGER_LOADED_HOOK = SDKCorePlugin.PLUGIN_ID
	+ ".sdkManagerLoadedHook"; //$NON-NLS-1$
	
	
	/**
	 * Minimum SBSv2 version supported with Carbide
	 */
	public static final Version MINIMUM_RAPTOR_VERSION = new Version(2, 15, 0);

	static boolean hasScannedSDKs = false; // make sure we only scan SDKs when needed
	
	protected static List<IRVCTToolChainInfo> rvctInfoList = null;
	
	/**
	 * Implement listener so other class that use this can listen to SDK 
	 * change event (e.g. validate project config description)
	 */
	protected static ListenerList<ICarbideInstalledSDKChangeListener> listeners = new ListenerList<ICarbideInstalledSDKChangeListener>();
	
	/*
	 * Implement listener so other class that use this can listen for when a change to the devices.xml
	 * has been modified outside of Carbide
	 */
	protected static ListenerList<ICarbideDevicesXMLChangeListener> devicesXMLListeners = new ListenerList<ICarbideDevicesXMLChangeListener>();
	
	IJobChangeListener scanJobListener = new IJobChangeListener() {
		public void sleeping(IJobChangeEvent event) {}
		public void scheduled(IJobChangeEvent event) {}
		public void running(IJobChangeEvent event) {}
		public void awake(IJobChangeEvent event) {}
		public void aboutToRun(IJobChangeEvent event) {}
		public void done(IJobChangeEvent event) {
			fireInstalledSdkChanged(SDKChangeEventType.eSDKScanned);
		}
	};

		
	
	public AbstractSDKManager() {
		macroStore = SymbianMacroStore.getInstance();
		scanJob = new Job ("Scan for installed SDKs") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				return handleScan(monitor);
			}
		};
		
		addScanJobListner(scanJobListener);
	}
	
	public SymbianMacroStore getSymbianMacroStore(){
		return macroStore;
	}
	
	public void scanSDKs() {
		SBSv2QueryUtils.removeAllCachedQueries();
		// do the real sdk scanning in a job.
		if (scanJob.getState() == Job.NONE) {
			scanJob.schedule();
		}
	}

	private IStatus handleScan(IProgressMonitor monitor) {
		synchronized (sdkList)
		{
			ArrayList<ISymbianSDK> oldSDKList = new ArrayList<ISymbianSDK>(sdkList);
			
			getSBSv2Version(true);
			
			if (sdkList != null){
				sdkList.clear();
			}
		
			if (!doScanSDKs(monitor))
				return Status.OK_STATUS;;

			// now these SDK's are newly added, remove from internal list
			for (ISymbianSDK sdk : sdkList) {
				if (SDKManagerInternalAPI.getMissingSdk(sdk.getUniqueId()) != null) {
					SDKManagerInternalAPI.removeMissingSdk(sdk);
				}
			}

			// now these SDK's are removed from the old list, add to
			// internal list
			for (ISymbianSDK oldSdk : oldSDKList) {
				boolean found = false;
				for (ISymbianSDK sdk : sdkList) {
					if (sdk.getUniqueId().equals(oldSdk.getUniqueId())) {
						found = true;
						break;
					}
				}
				if (found == false) {
					SDKManagerInternalAPI.addMissingSdk(oldSdk);
					// flush cache
					SymbianBuildContextDataCache.refreshForSDKs(new ISymbianSDK[] { oldSdk });
				}
			}
		}

		// make sure we don't rescan over and over again
		hasScannedSDKs = true;
		
		updateCarbideSDKCache();
		
		// Notify any plugins that want to know if the SDKManager has scanned plugins.
		if (!sdkHookExtenstionsNotified) {
			notifySDKManagerLoaded();
			sdkHookExtenstionsNotified = true;
		}
		if (monitor.isCanceled()) {
			return Status.CANCEL_STATUS;
		}
		return Status.OK_STATUS;
	}

	/**
	 * Actually scan the SDKs available and populate sdkList.   
	 * @param oldSDkList old list of SDKs available for reference, e.g., detecting
	 * when SDKs are newly missing
	 * @return true if scan succeeded
	 */
	abstract protected boolean doScanSDKs(IProgressMonitor monitor);
	
	public void addScanJobListner(IJobChangeListener listener) {
		if (scanJob != null && listener != null) {
			scanJob.addJobChangeListener(listener);
		}
	}

	public void removeScanJobLisner(IJobChangeListener listener) {
		if (scanJob != null && listener != null) {
			scanJob.removeJobChangeListener(listener);
		}
	}

	protected void ensureScannedSDKs() {
		if (!hasScannedSDKs) {
			handleScan(new NullProgressMonitor());
			fireInstalledSdkChanged(SDKChangeEventType.eSDKScanned);
		}
	}
	
	public List<ISymbianSDK> getSDKList() {
		
		synchronized(sdkList)
		{
			if (sdkList.size() < 1) {
				ensureScannedSDKs();
			}
			List<ISymbianSDK> listCopy = new ArrayList<ISymbianSDK>(sdkList);
			return listCopy;			
		}
	}

	public ISymbianSDK getSDK(String sdkId, boolean scanIfNecessary) {
		if (scanIfNecessary) {
			getSDKList();
		}
		synchronized(sdkList)
		{
			for (Iterator iter = sdkList.iterator(); iter.hasNext();) {
				ISymbianSDK sdk = (ISymbianSDK)iter.next();
				if (sdk.getUniqueId().compareTo(sdkId) == 0) {
					return sdk;
				}
			}
		}
		return null;
	}

	
	public void addSDK(ISymbianSDK sdk) {
		synchronized(sdkList)
		{
			try {
				sdkList.add(sdk);
				updateSDK(sdk);
				SDKManagerInternalAPI.removeMissingSdk(sdk);
				// tell others about it
				fireInstalledSdkChanged(SDKChangeEventType.eSDKAdded);
			}
			catch (Exception e) {
				logError("Could not add SDK", e);
				String message = "Could not add this SDK. Your devices.xml file may be corrupt. If you remove the file from its current location and then rescan SDKs, Carbide will offer to create a new one.";
				Logging.showErrorDialog(WorkbenchUtils.getSafeShell(), null, message, Logging.newSimpleStatus(1, e));
			}
		}
	}
	
	public boolean removeSDK(String sdkId) {
		synchronized (sdkList)
		{
			if (sdkList != null){
				for (ISymbianSDK currSDK : sdkList){
					if (currSDK.getUniqueId().equals(sdkId)){
						sdkList.remove(currSDK);
						
						SDKManagerInternalAPI.addMissingSdk(currSDK);
						
						// tell others about it
						fireInstalledSdkChanged(SDKChangeEventType.eSDKRemoved);

						// only remove sdk from devices.xml if the sdk is defined in it
						if (((SymbianSDK)currSDK).getSupportedFeatures().contains(ISymbianSDKFeatures.IS_FROM_DEVICES_XML)) {
							doRemoveSDK(sdkId);
						}
						
						break;
					}
				}
				
				updateCarbideSDKCache();
				
			} else {
				return false;
			}			
		}
		return true;
	}

	abstract protected boolean doRemoveSDK(String sdkId);

	protected void scanCarbideSDKCache(){
		List<String> idList = getSDKCacheIdList();
		for (Iterator<String> itr = idList.iterator(); itr.hasNext();) {
			String id = itr.next();
			SDKManagerCacheEntry entry = getSDKCacheEntry(id);
			ISymbianSDK sdk = getSDK(id, false);
			if (sdk == null) {
				Version osVersion = new Version(entry.getOsVersion());
				if (osVersion.getMajor() == 0) {
					osVersion = new Version("9.5");
				}
				sdk = SymbianSDKFactory.createInstance(id, 
						   entry.getEpocRoot(),
						   osVersion);
				((SymbianSDK)sdk).setEnabled(entry.isEnabled());
				synchronized (sdkList) {
					sdkList.add(sdk);
				}
			} else {
				((SymbianSDK)sdk).setEnabled(entry.isEnabled());
			}
		}
	}
	
	public void updateCarbideSDKCache() {
		if (!Platform.isRunning())
			return;

		clearSDKCache();
		synchronized(sdkList)
		{
			for (ISymbianSDK currSDK: sdkList) {
				SDKManagerCacheEntry entry = new SDKManagerCacheEntry();
				entry.setId(currSDK.getUniqueId());
				entry.setEpocRoot(currSDK.getEPOCROOT());
				entry.setOsVersion(currSDK.getOSVersion().toString());
				entry.setEnabled(currSDK.isEnabled());
				setSDKCacheEntry(entry);
			}
			flushSDKCache();
		}
	}

	/**
	 * Tell whether EPOCROOT can be changed for a given ISymbianSDK
	 * @return flag
	 */
	abstract protected boolean isEPOCRootFixed();

	/**
	 * Get whether or not the UI has enabled BSF scanning.
	 * @return true if BSF scanning is enabled.
	 */
	public boolean getBSFScannerEnabled(){
		return enableBSFScanner;
	}
	
	/**
	 * Set whether or not build platforms should be added for SDKs that contain .bsf extensions.
	 * @param enabled - When true, add BSF platforms.
	 */
	public void enableBSFScanner(boolean enabled){
		enableBSFScanner = enabled;
	}
	
	public void setPlatformList(List<BuildPlat> inPlatList){
		platList = inPlatList;
	}
	
	public List<BuildPlat> getPlatformList(){
		return platList;
	}

	/**
	 * Returns toolchain info for all detected RVCT tools.
	 * @return Array of toolchain information objects.
	 */
	public synchronized IRVCTToolChainInfo[] getInstalledRVCTTools() {
		// the path wn't change inside one invocation so cache the results
		if (rvctInfoList == null) {
			rvctInfoList = scanForInstalledRVCTCompilers();
		}
		return (IRVCTToolChainInfo[]) rvctInfoList.toArray(new IRVCTToolChainInfo[rvctInfoList.size()]);
	}
	
	/**
	 * Get a list of IRVCTToolChainInfo objects for all RVCT installations detected on the PATH env var.
	 */
	protected static List<IRVCTToolChainInfo> scanForInstalledRVCTCompilers() {
		
		Runtime rt=Runtime.getRuntime();
		List<IRVCTToolChainInfo> rvctToolList = new ArrayList<IRVCTToolChainInfo>();
		
		IPath[] pathEntries = PathUtils.getPathEntries(null);
		
		for(IPath path : pathEntries) {
			try {
				String command = path.append("armcc").toOSString() + " --vsn";
				
				Process p = rt.exec(command);
				
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
				
				String overallOutput = null;
				String stdErrLine = null;

				// RVCT waits for like 4 minutes trying to find a license when the computer is
				// not connected to the network.  in such cases, the call to br.readline doesn't
				// return for 4 minutes which is unacceptable here.  Instead we'll poll at 1/2 second
				// intervals for 5 seconds and see if we get a response. On the first response we break out
				// of the loop and read the output. So in most normal circumstances it will take 1/2 to 1 seconds.
				int maxTries = 10;
				int numTries = 0;
				while (numTries < maxTries) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// ignore
					}
					if (br.ready()) {
						while ((stdErrLine = br.readLine()) != null) {
							overallOutput += stdErrLine;
						}
						break;
					}
					numTries++;
				}
				
				p.destroy();
				
				if (overallOutput != null && overallOutput.length() > 0){
					for (String currVersion : knownRVCTVersions) {
						
						if(overallOutput.contains(currVersion)) {
							RVCTToolChainInfo rvctInfo = new RVCTToolChainInfo();
							rvctInfo.setRvctToolBinariesDirectory(path.toOSString());
							rvctInfo.setRvctToolsVersion(new Version(currVersion));
							rvctToolList.add(rvctInfo);
							break; // tool successfully added
						}
					}
				}
			}
			catch (IOException e) {
				// armcc isn't in this directory, ignore....
			}
		}
				
		return rvctToolList;
	}
	
    public void addMissingSdk(ISymbianSDK sdk) {
		missingSdkMap.put(sdk.getUniqueId(), sdk);
    }
    
    public ISymbianSDK addMissingSdk(String uid) {
		ISymbianSDK sdk = getMissingSdk(uid);
		if (sdk == null) {
			sdk = SymbianMissingSDKFactory.createInstance(uid);
			missingSdkMap.put(uid, sdk);
		}
    	return sdk;
    }
    
    public void removeMissingSdk(ISymbianSDK sdk) {
    	missingSdkMap.remove(sdk.getUniqueId());
    }
    
    public void removeMissingSdk(String uid) {
    	missingSdkMap.remove(uid);
    }
    
    public ISymbianSDK getMissingSdk(String uid) {
    	return missingSdkMap.get(uid);
    }
    
	public List<ISymbianSDK> getMissingSDKList() {
		List<ISymbianSDK> listCopy = new ArrayList<ISymbianSDK>();
		synchronized(missingSdkMap) {
			for (ISymbianSDK sdk : missingSdkMap.values()) {
				listCopy.add(sdk);
			}
		}
		return listCopy;			
	}
	
	public void addInstalledSdkChangeListener(ICarbideInstalledSDKChangeListener listener) {
		listeners.add(listener);
	}
	
	public void removeInstalledSdkChangeListener(ICarbideInstalledSDKChangeListener listener) {
		listeners.remove(listener);
	}
	
	public void fireInstalledSdkChanged(SDKChangeEventType eventType) {
		ListenerList<ICarbideInstalledSDKChangeListener> lList = listeners;
		for (ICarbideInstalledSDKChangeListener l : lList) {
			l.installedSdkChanged(eventType);
		}
	}
	
	public void addDevicesXMLChangeListener(ICarbideDevicesXMLChangeListener listener) {
		devicesXMLListeners.add(listener);
	}
	
	public void removeDevicesXMLChangeListener(ICarbideDevicesXMLChangeListener listener) {
		devicesXMLListeners.remove(listener);
	}
	
	public void fireDevicesXMLChanged() {
		for (ICarbideDevicesXMLChangeListener l : devicesXMLListeners) {
			l.devicesXMLOutOfSync();
		}
	}
	
	protected void logError(String message, Throwable t) {
		SDKCorePlugin.getDefault().getLog().log(new Status(IStatus.ERROR, SDKCorePlugin.getPluginId(), message, t));		
	}

	public Version getSBSv2Version(boolean forceScan) {
		if (sbsV2Version == null || forceScan){
			sbsV2Version = new Version(0, 0, 0);
			
			Runtime rt=Runtime.getRuntime();
			IPath sbsPath = SBSv2Utils.getSBSPath();
			Process p = null;
			try {
				p = rt.exec(new String[] { sbsPath.toOSString(), "-v" });
			} catch (IOException e) {
				// no such process, SBSv2 not available
				Logging.log(SDKCorePlugin.getDefault(), Logging.newSimpleStatus(
						0, IStatus.WARNING, 
						MessageFormat.format(
							"Could not find or launch Raptor script ''{0}''; SBSv2 support will not be available",
							sbsPath), e));
			} 
			if (p != null) {
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String overallOutput = "";
				String stdErrLine = null;
				try {

					// Only try for 10 seconds then bail in case Raptor hangs
					int maxTries = 20;
					int numTries = 0;
					while (numTries < maxTries) {
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// ignore
						}
						if (br.ready()) {
							while ((stdErrLine = br.readLine()) != null) {
								overallOutput += stdErrLine;
								numTries = maxTries;
							}
							
						}
						numTries++;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (overallOutput.length() > 0) {
				{
					String[] tokens = overallOutput.split(" ");
					if (tokens.length >= 3) {
						if (tokens[2].split("\\.").length == 3) {
							sbsV2Version = Version.parseVersion(tokens[2]);
						}
					}
						if (sbsV2Version.compareTo(MINIMUM_RAPTOR_VERSION) < 0 && sbsV2Version.getMajor() > 0) {

							String incorrectRaptorVersionStr = "SBSv2 version detected: "
									+ sbsV2Version.toString()
									+ ". The minimum version suggested for Carbide is: "
									+ MINIMUM_RAPTOR_VERSION.getMajor()
									+ "."
									+ MINIMUM_RAPTOR_VERSION.getMinor()
									+ "."
									+ MINIMUM_RAPTOR_VERSION.getMicro();

							ResourcesPlugin.getPlugin().getLog().log(
									new Status(IStatus.WARNING,
											SDKCorePlugin.PLUGIN_ID,
											IStatus.WARNING,
											incorrectRaptorVersionStr, null));
						}
					
					p.destroy();
					}
				}
			}
		}
		return sbsV2Version;
	}

	/**
	 * Retrieve the minimum supported version of SBSv2 for Carbide.c++
	 * @return Version
	 * @since 2.3
	 */
	public Version getMinimumSupportedSBSv2Version() {
		return MINIMUM_RAPTOR_VERSION;
	}
	
	/**
	 * Find clients of the 'sdkManagerLoadedHook' extension point so their plug-ins can be loaded
	 */
	private void notifySDKManagerLoaded() {

		ISDKManagerLoadedHook sdkHook = null;
		IExtensionRegistry er = Platform.getExtensionRegistry();
		IExtensionPoint ep = er.getExtensionPoint(SDK_MANAGER_LOADED_HOOK);
		IExtension[] extensions = ep.getExtensions();

		for (int i = 0; i < extensions.length; i++) {
			IExtension extension = extensions[i];
			IConfigurationElement[] ces = extension.getConfigurationElements();
			if (ces != null && ces.length >= 1) {
				IConfigurationElement providerElement = ces[0];
				String name = providerElement.getAttribute("name"); //$NON-NLS-1$
				if (name != null) {
					if (providerElement.getAttribute("class") != null) { //$NON-NLS-1$

						try {
							sdkHook = (ISDKManagerLoadedHook) providerElement
									.createExecutableExtension("class"); //$NON-NLS-1$
							sdkHook.symbianSDKManagerLoaded();
						} catch (CoreException e) {
							// ignore
							// e.printStackTrace();
						}
					}
				}

			}
		}
		return;

	}

	protected void clearSDKCache() {
		SDKCacheUtils.getCache().removeCache(SDK_MANAGER_CACHE_KEY, true);
	}

	protected void flushSDKCache() {
		try {
			SDKCacheUtils.getCache().flushCache(SDK_MANAGER_CACHE_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	protected List<String> getSDKCacheIdList() {
		Map<String, SDKManagerCacheEntry> cacheMap = SDKCacheUtils.getCache().getCachedData(SDK_MANAGER_CACHE_KEY, Map.class, 0);
		List<String> idList = new ArrayList<String>();

		if (cacheMap != null) {
			idList.addAll(cacheMap.keySet()); 
		}

		return idList;
	}

	@SuppressWarnings("unchecked")
	protected SDKManagerCacheEntry getSDKCacheEntry(String id) {
		SDKManagerCacheEntry entry = null;
		Map<String, SDKManagerCacheEntry> cacheMap = SDKCacheUtils.getCache().getCachedData(SDK_MANAGER_CACHE_KEY, Map.class, 0);

		if (cacheMap != null) {
			entry = cacheMap.get(id);
		}

		return entry;
	}

	@SuppressWarnings("unchecked")
	protected void setSDKCacheEntry(SDKManagerCacheEntry entry) {
		String id = entry.getId();
		Map<String, SDKManagerCacheEntry> cacheMap = SDKCacheUtils.getCache().getCachedData(SDK_MANAGER_CACHE_KEY, Map.class, 0);

		if (cacheMap == null) {
			cacheMap = new HashMap<String, SDKManagerCacheEntry>();
		}

		cacheMap.put(id, entry);
		SDKCacheUtils.getCache().putCachedData(SDK_MANAGER_CACHE_KEY, (Serializable)cacheMap, 0);
	}

}
