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
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.osgi.framework.Version;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.SAXException;

import com.nokia.carbide.cpp.internal.api.sdk.BuildPlat;
import com.nokia.carbide.cpp.internal.api.sdk.ICarbideDevicesXMLChangeListener;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv1BuildInfo;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv2BuildInfo;
import com.nokia.carbide.cpp.internal.api.sdk.ISDKManagerInternal;
import com.nokia.carbide.cpp.internal.api.sdk.ISDKManagerLoadedHook;
import com.nokia.carbide.cpp.internal.api.sdk.SBSv2Utils;
import com.nokia.carbide.cpp.internal.api.sdk.SDKManagerInternalAPI;
import com.nokia.carbide.cpp.internal.api.sdk.SymbianBuildContextDataCache;
import com.nokia.carbide.cpp.internal.api.sdk.SymbianMacroStore;
import com.nokia.carbide.cpp.sdk.core.ICarbideInstalledSDKChangeListener;
import com.nokia.carbide.cpp.sdk.core.ICarbideInstalledSDKChangeListener.SDKChangeEventType;
import com.nokia.carbide.cpp.sdk.core.IRVCTToolChainInfo;
import com.nokia.carbide.cpp.sdk.core.ISDKManager;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuilderID;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.carbide.cpp.sdk.core.SymbianSDKFactory;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.ListenerList;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.core.PathUtils;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;
import com.sun.org.apache.xpath.internal.XPathAPI;

public abstract class AbstractSDKManager implements ISDKManager, ISDKManagerInternal {
	
	protected static List<ISymbianSDK> sdkList = new ArrayList<ISymbianSDK>();
	protected HashMap<String,ISymbianSDK> missingSdkMap = new HashMap<String,ISymbianSDK>();
	protected Job scanJob;

	protected static final String CARBIDE_SDK_CACHE_FILE_NAME = "carbideSDKCache.xml";
	protected static final String SDK_CACHE_ID_ATTRIB = "id";
	protected static final String SDK_CACHE_ENABLED_ATTRIB = "isEnabled";
	protected static final String SDK_CACHE_OS_VERSION_ATTRIB = "osVersion";
	protected static final String SDK_CACHE_OS_BRANCH_ATTRIB = "osBranch";
	protected static final String SDK_CACHE_SDK_VERSION_ATTRIB = "sdkVersion";
	protected static final String SDK_SCANNED_FOR_PLUGINS = "sdkScanned";

	protected static final String SDK_CACHE_EPOCROOT_ATTRIB = "epocroot";

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
		scanJob = new Job ("Scan System Drives") {
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
					SDKManagerInternalAPI.removeMissingSdk(sdk
							.getUniqueId());
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
					if (sdk.getEPOCROOT().toLowerCase().equals(oldSdk.getEPOCROOT().toLowerCase())) {
						// use the existing SDK name
						((SymbianSDK)sdk).setUniqueId(oldSdk.getUniqueId());
						found = true;
						break;
					}
				}
				if (found == false) {
					SDKManagerInternalAPI.addMissingSdk(oldSdk
							.getUniqueId());
					// flush cache
					SymbianBuildContextDataCache.refreshForSDKs(new ISymbianSDK[] { oldSdk });
				}
			}
		}

		// make sure we don't rescan over and over again
		hasScannedSDKs = true;
		
		// tell others about it
		scanCarbideSDKCache();
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
			// load sdk list from cache during start up, this way we don't have to wait
			// for sdk scanning job to be completed.
			loadCarbideSDKCache();
			scanSDKs();
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
				SDKManagerInternalAPI.removeMissingSdk(sdk.getUniqueId());
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
						
						SDKManagerInternalAPI.addMissingSdk(currSDK.getUniqueId());
						
						// tell others about it
						fireInstalledSdkChanged(SDKChangeEventType.eSDKRemoved);
						
						doRemoveSDK(sdkId);
						
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

	protected void loadCarbideSDKCache(){
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, SDKCorePlugin.PLUGIN_ID, IStatus.ERROR, e.getMessage(), e));
			throw new RuntimeException(e);
		}
		
		try {
			File carbideSDKCacheFile = getCardbieSDKCacheFile();
		    if (!carbideSDKCacheFile.exists()){
		    	try {
		    	FileUtils.writeFileContents(carbideSDKCacheFile, EMPTY_STRING.toCharArray(), null);
		    	} catch (CoreException e){
		    		e.printStackTrace();
		    	}
		    } else if (carbideSDKCacheFile.length() > 0) {
				Document lastKnownDoc = docBuilder.parse(carbideSDKCacheFile);
				
				NodeIterator ni = XPathAPI.selectNodeIterator(lastKnownDoc, "/sdks/sdk");
				for (Node n = ni.nextNode(); n != null; n = ni.nextNode()) {
					
					// get the unique ID
					NamedNodeMap attribs = n.getAttributes();
					String id = attribs.getNamedItem(SDK_CACHE_ID_ATTRIB).getNodeValue();
					
					// get whether or not the SDK is enabled
					String sdkEnabled = "true";
					Node sdkEnabledItem = attribs.getNamedItem(SDK_CACHE_ENABLED_ATTRIB);
					if (sdkEnabledItem != null)
						sdkEnabled = sdkEnabledItem.getNodeValue();
					
					// get the os version
					String osVersion = "";
					Node osVersionItem = attribs.getNamedItem(SDK_CACHE_OS_VERSION_ATTRIB);
					if (osVersionItem != null)
						osVersion = osVersionItem.getNodeValue();
					
					// get the sdk version
					String sdkVersion = "";
					Node sdkVersionItem = attribs.getNamedItem(SDK_CACHE_SDK_VERSION_ATTRIB);
					if (sdkVersionItem != null)
						sdkVersion = sdkVersionItem.getNodeValue();
					
					// get the custom EPOCROOT, if allowed
					String epocRoot = null;
					Node epocrootItem = attribs.getNamedItem(SDK_CACHE_EPOCROOT_ATTRIB);
					if (epocrootItem != null)
						epocRoot = epocrootItem.getNodeValue();
					
					// get whether or not this SDK has been scanned
					String wasScanned = "false";
					Node sdkScannedItem = attribs.getNamedItem(SDK_SCANNED_FOR_PLUGINS);
					if (sdkScannedItem != null)
						wasScanned = sdkScannedItem.getNodeValue();
					
					ISymbianSDK sdk = SymbianSDKFactory.createInstance(id, 
																	   epocRoot, 
																	   ISBSv1BuildInfo.S60_SDK_NAME, 
																	   new Version(osVersion), 
																	   new Version(sdkVersion));
					if (sdkEnabled.equalsIgnoreCase("true")){
						((SymbianSDK)sdk).setEnabled(true);
					} else {
						((SymbianSDK)sdk).setEnabled(false);
					}
					ISBSv1BuildInfo sbsv1BuildInfo = (ISBSv1BuildInfo)sdk.getBuildInfo(ISymbianBuilderID.SBSV1_BUILDER);
					ISBSv2BuildInfo sbsv2BuildInfo = (ISBSv2BuildInfo)sdk.getBuildInfo(ISymbianBuilderID.SBSV2_BUILDER);
					if (wasScanned.equalsIgnoreCase("true")){
						sbsv1BuildInfo.setPreviouslyScanned(true);
						sbsv2BuildInfo.setPreviouslyScanned(true);
					} else {
						sbsv1BuildInfo.setPreviouslyScanned(false);
						sbsv2BuildInfo.setPreviouslyScanned(false);
					}
					synchronized (sdkList) {
						sdkList.add(sdk);
					}
				} // for
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void scanCarbideSDKCache(){
		
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, SDKCorePlugin.PLUGIN_ID, IStatus.ERROR, e.getMessage(), e));
			throw new RuntimeException(e);
		}
		
		try {
			File carbideSDKCacheFile = getCardbieSDKCacheFile();
		    if (!carbideSDKCacheFile.exists()){
		    	try {
		    	FileUtils.writeFileContents(carbideSDKCacheFile, EMPTY_STRING.toCharArray(), null);
		    	} catch (CoreException e){
		    		e.printStackTrace();
		    	}
		    } else if (carbideSDKCacheFile.length() > 0) {
				Document lastKnownDoc = docBuilder.parse(carbideSDKCacheFile);
				
				NodeIterator ni = XPathAPI.selectNodeIterator(lastKnownDoc, "/sdks/sdk");
				for (Node n = ni.nextNode(); n != null; n = ni.nextNode()) {
					
					// get the unique ID
					NamedNodeMap attribs = n.getAttributes();
					String id = attribs.getNamedItem(SDK_CACHE_ID_ATTRIB).getNodeValue();
					
					// get whether or not the SDK is enabled
					String sdkEnabled = "true";
					Node sdkEnabledItem = attribs.getNamedItem(SDK_CACHE_ENABLED_ATTRIB);
					if (sdkEnabledItem != null)
						sdkEnabled = sdkEnabledItem.getNodeValue();
					
					// get the os version
					String osVersion = "";
					Node osVersionItem = attribs.getNamedItem(SDK_CACHE_OS_VERSION_ATTRIB);
					if (osVersionItem != null)
						osVersion = osVersionItem.getNodeValue();
					
					// get the sdk version
					String sdkVersion = "";
					Node sdkVersionItem = attribs.getNamedItem(SDK_CACHE_SDK_VERSION_ATTRIB);
					if (sdkVersionItem != null)
						sdkVersion = sdkVersionItem.getNodeValue();
					
					// get the custom EPOCROOT
					String epocRoot = null;
					Node epocrootItem = attribs.getNamedItem(SDK_CACHE_EPOCROOT_ATTRIB);
					if (epocrootItem != null)
						epocRoot = epocrootItem.getNodeValue();
					
					// get whether or not this SDK has been scanned
					String wasScanned = "false";
					Node sdkScannedItem = attribs.getNamedItem(SDK_SCANNED_FOR_PLUGINS);
					if (sdkScannedItem != null)
						wasScanned = sdkScannedItem.getNodeValue();
					
					ISymbianSDK sdk = getSDK(id, false);
					if (sdk != null){
	
						if (sdkEnabled.equalsIgnoreCase("true")){
							((SymbianSDK)sdk).setEnabled(true);
						} else {
							((SymbianSDK)sdk).setEnabled(false);
						}
						
						if (!osVersion.equals("")){
							if (Version.parseVersion(osVersion).getMajor() != 0){
								((SymbianSDK)sdk).setOSVersion(Version.parseVersion(osVersion));
							}
						}
						
						if (epocRoot != null) {
							((SymbianSDK)sdk).setEPOCROOT(epocRoot);
						}
						
						ISBSv1BuildInfo sbsv1BuildInfo = (ISBSv1BuildInfo)sdk.getBuildInfo(ISymbianBuilderID.SBSV1_BUILDER);
						ISBSv2BuildInfo sbsv2BuildInfo = (ISBSv2BuildInfo)sdk.getBuildInfo(ISymbianBuilderID.SBSV2_BUILDER);
						if (wasScanned.equalsIgnoreCase("true")){
							sbsv1BuildInfo.setPreviouslyScanned(true);
							sbsv2BuildInfo.setPreviouslyScanned(true);
						} else {
							sbsv1BuildInfo.setPreviouslyScanned(false);
							sbsv2BuildInfo.setPreviouslyScanned(false);
						}
						
						if (!sdkVersion.equals("")){
							if (Version.parseVersion(sdkVersion).getMajor() != 0){
								((SymbianSDK)sdk).setSDKVersion(Version.parseVersion(sdkVersion));
							}
						}
					}
				} // for
		    } 
		} catch (TransformerException e) {
		} catch (SAXException e) {
		} catch (IOException e) {
		}
	}
	
	public void updateCarbideSDKCache() {
		if (!Platform.isRunning())
			return;
			
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, SDKCorePlugin.PLUGIN_ID, IStatus.ERROR, e.getMessage(), e));
			return;
		}
		
		File carbideSDKCacheFile = getCardbieSDKCacheFile();
		if (!carbideSDKCacheFile.exists()){
			try {
			FileUtils.writeFileContents(carbideSDKCacheFile, EMPTY_STRING.toCharArray(), null);
			} catch (CoreException e){
				e.printStackTrace();
			}
		}
		
		Document d = docBuilder.newDocument();
		Node sdks = d.appendChild(d.createElement("sdks"));
			
		synchronized(sdkList)
		{
			for (ISymbianSDK currSDK: sdkList) {
				Node sdk = sdks.appendChild(d.createElement("sdk"));
				NamedNodeMap attribs = sdk.getAttributes();
				Node idNode = d.createAttribute(SDK_CACHE_ID_ATTRIB);
				idNode.setNodeValue(currSDK.getUniqueId());
				attribs.setNamedItem(idNode);

				// Hide the build config from view in the build config list?
				Node enabledNode = d.createAttribute(SDK_CACHE_ENABLED_ATTRIB);
				if (true == currSDK.isEnabled()) {
					enabledNode.setNodeValue("true");
				} else {
					enabledNode.setNodeValue("false");
				}
				attribs.setNamedItem(enabledNode);
				
				Node osVerNode = d.createAttribute(SDK_CACHE_OS_VERSION_ATTRIB);
				osVerNode.setNodeValue(currSDK.getOSVersion().toString());
				attribs.setNamedItem(osVerNode);
				
				Node sdkVerNode = d.createAttribute(SDK_CACHE_SDK_VERSION_ATTRIB);
				sdkVerNode.setNodeValue(currSDK.getSDKVersion().toString());
				attribs.setNamedItem(sdkVerNode);

				Node sdkEpocRootNode = d.createAttribute(SDK_CACHE_EPOCROOT_ATTRIB);
				sdkEpocRootNode.setNodeValue(currSDK.getEPOCROOT());
				attribs.setNamedItem(sdkEpocRootNode);

				ISBSv1BuildInfo sbsv1BuildInfo = (ISBSv1BuildInfo)currSDK.getBuildInfo(ISymbianBuilderID.SBSV1_BUILDER);
				ISBSv2BuildInfo sbsv2BuildInfo = (ISBSv2BuildInfo)currSDK.getBuildInfo(ISymbianBuilderID.SBSV2_BUILDER);
				Node wasScannedNode = d.createAttribute(SDK_SCANNED_FOR_PLUGINS);
				if (true == sbsv1BuildInfo.isPreviouslyScanned() ||
					true == sbsv2BuildInfo.isPreviouslyScanned()) {
					wasScannedNode.setNodeValue("true");
				} else {
					wasScannedNode.setNodeValue("false");
				}
				attribs.setNamedItem(wasScannedNode);
			}
		}
		DOMSource domSource = new DOMSource(d);
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Result fileResult = new StreamResult(carbideSDKCacheFile);
		try {
			transFactory.newTransformer().transform(domSource, fileResult);
		} catch (TransformerConfigurationException e) {
			ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, SDKCorePlugin.PLUGIN_ID, IStatus.ERROR, e.getMessage(), e));
		} catch (TransformerException e) {
			ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, SDKCorePlugin.PLUGIN_ID, IStatus.ERROR, e.getMessage(), e));
		}
	}

	protected File getCardbieSDKCacheFile() {
		IPath path = new Path(System.getProperty("user.home"));
		return path.append(CARBIDE_SDK_CACHE_FILE_NAME).toFile();
	}

	/**
	 * Tell whether EPOCROOT can be changed for a given ISymbianSDK
	 * @return flag
	 */
	abstract protected boolean isEPOCRootFixed();

	public boolean getBSFScannerEnabled(){
		return enableBSFScanner;
	}
	
	public void enableBSFScanner(boolean enabled){
		enableBSFScanner = enabled;
	}
	
	public void setPlatformList(List<BuildPlat> inPlatList){
		platList = inPlatList;
	}
	
	public List<BuildPlat> getPlatformList(){
		return platList;
	}

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
				// intervals for 40 seconds and see if we get a response. On the first response we break out
				// of the loop and read the output. So in most normal circumstances it will take 1/2 to 1 seconds.
				int maxTries = 80;
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
	
    public ISymbianSDK addMissingSdk(String uid) {
		ISymbianSDK sdk = getMissingSdk(uid);
		if (sdk == null) {
			sdk = SymbianMissingSDKFactory.createInstance(uid);
			missingSdkMap.put(uid, sdk);
		}
    	return sdk;
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
		for (ICarbideInstalledSDKChangeListener l : listeners) {
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

	
}
