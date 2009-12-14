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
import java.net.MalformedURLException;
import java.net.URL;
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
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.service.datalocation.Location;
import org.osgi.framework.Version;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.SAXException;

import com.nokia.carbide.cpp.internal.api.sdk.BuildPlat;
import com.nokia.carbide.cpp.internal.api.sdk.ICarbideDevicesXMLChangeListener;
import com.nokia.carbide.cpp.internal.api.sdk.ISDKManagerInternal;
import com.nokia.carbide.cpp.internal.api.sdk.SBSv2Utils;
import com.nokia.carbide.cpp.internal.api.sdk.SDKManagerInternalAPI;
import com.nokia.carbide.cpp.internal.api.sdk.SymbianMacroStore;
import com.nokia.carbide.cpp.sdk.core.ICarbideInstalledSDKChangeListener;
import com.nokia.carbide.cpp.sdk.core.IRVCTToolChainInfo;
import com.nokia.carbide.cpp.sdk.core.ISDKManager;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.carbide.cpp.sdk.core.ICarbideInstalledSDKChangeListener.SDKChangeEventType;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.ListenerList;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.core.PathUtils;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;
import com.sun.org.apache.xpath.internal.XPathAPI;

public abstract class AbstractSDKManager implements ISDKManager, ISDKManagerInternal {
	
	protected static List<ISymbianSDK> sdkList = new ArrayList<ISymbianSDK>();
	protected HashMap<String,ISymbianSDK> missingSdkMap = new HashMap<String,ISymbianSDK>();

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
	
	/**
	 * Minimum SBSv2 version supported with Carbide
	 */
	public static final Version MINIMUM_RAPTOR_VERSION = new Version(2, 8, 6);

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
	
	
	public AbstractSDKManager() {
		macroStore = SymbianMacroStore.getInstance();
	}
	
	public SymbianMacroStore getSymbianMacroStore(){
		return macroStore;
	}
	
	public void scanSDKs() {
		synchronized (sdkList)
		{
			ArrayList<ISymbianSDK> oldSDkList = new ArrayList<ISymbianSDK>(sdkList);
			
			getSBSv2Version(true);
			
			if (sdkList != null){
				sdkList.clear();
			}
		
			if (!doScanSDKs())
				return;
			
			// now these SDK's are newly added, remove from internal list
			for (ISymbianSDK sdk : sdkList) {
				if (SDKManagerInternalAPI.getMissingSdk(sdk.getUniqueId()) != null) {
					SDKManagerInternalAPI.removeMissingSdk(sdk
							.getUniqueId());
				}
			}

			// now these SDK's are removed from the old list, add to
			// internal list
			for (ISymbianSDK oldSdk : oldSDkList) {
				boolean found = false;
				for (ISymbianSDK sdk : sdkList) {
					if (sdk.getUniqueId().equals(oldSdk.getUniqueId())) {
						found = true;
						break;
					}
				}
				if (found == false) {
					SDKManagerInternalAPI.addMissingSdk(oldSdk
							.getUniqueId());
				}
			}


		}

		// make sure we don't rescan over and over again
		hasScannedSDKs = true;
		
		// tell others about it
		fireInstalledSdkChanged(SDKChangeEventType.eSDKScanned);
		scanCarbideSDKCache();
	}

	/**
	 * Actually scan the SDKs available and populate sdkList.   
	 * @param oldSDkList old list of SDKs available for reference, e.g., detecting
	 * when SDKs are newly missing
	 * @return
	 */
	abstract protected boolean doScanSDKs();
	

	protected void ensureScannedSDKs() {
		if (!hasScannedSDKs) {
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
				updateSDK(sdk);
				sdkList.add(sdk);
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

	protected void scanCarbideSDKCache(){
		
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, SDKCorePlugin.PLUGIN_ID, IStatus.ERROR, e.getMessage(), e));
			throw new RuntimeException(e);
		}
		
		// NOTE: If debugging runtime workbench and you clear your configuraiton at each launch
		// the data in the configuration location will be lost
		Location configurationLocation = Platform.getConfigurationLocation();
		try {
			URL url = new URL(configurationLocation.getURL(), SDKCorePlugin.PLUGIN_ID);
			File configFolder = new File(url.getFile());
			if (!configFolder.exists()) {
				configFolder.mkdirs();
			}

		    File carbideSDKCacheFile = new File(configFolder, CARBIDE_SDK_CACHE_FILE_NAME);
		    if (!carbideSDKCacheFile.exists()){
		    	try {
		    	FileUtils.writeFileContents(carbideSDKCacheFile, EMPTY_STRING.toCharArray(), null);
		    	} catch (CoreException e){
		    		e.printStackTrace();
		    	}
		    }else if (carbideSDKCacheFile.length() > 0) {
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
				
				// get the os branch
				String osBranch = "";
				Node osBranchItem = attribs.getNamedItem(SDK_CACHE_OS_BRANCH_ATTRIB);
				if (osBranchItem != null)
					osBranch = osBranchItem.getNodeValue();
				
				// get the sdk version
				String sdkVersion = "";
				Node sdkVersionItem = attribs.getNamedItem(SDK_CACHE_SDK_VERSION_ATTRIB);
				if (sdkVersionItem != null)
					sdkVersion = sdkVersionItem.getNodeValue();
				
				// get the custom EPOCROOT, if allowed
				String customEpocroot = null;
				if (!isEPOCRootFixed()) {
					Node epocrootItem = attribs.getNamedItem(SDK_CACHE_EPOCROOT_ATTRIB);
					if (epocrootItem != null)
						customEpocroot = epocrootItem.getNodeValue();
				}
				
				// get whether or not this SDK has been scanned
				String wasScanned = "false";
				Node sdkScannedItem = attribs.getNamedItem(SDK_SCANNED_FOR_PLUGINS);
				if (sdkScannedItem != null)
					wasScanned = sdkScannedItem.getNodeValue();
				
				ISymbianSDK sdk = getSDK(id, false);
				if (sdk != null){
					
					if (wasScanned.equalsIgnoreCase("true")){
						sdk.setPreviouslyScanned(true);
					} else {
						sdk.setPreviouslyScanned(false);
					}
					
					if (sdkEnabled.equalsIgnoreCase("true")){
						sdk.setEnabled(true);
					} else {
						sdk.setEnabled(false);
					}
					
					if (!osVersion.equals("")){
						if (Version.parseVersion(osVersion).getMajor() != 0){
							sdk.setOSVersion(Version.parseVersion(osVersion));
						}
					}
					
					if (!osBranch.equals("")){
						sdk.setOSSDKBranch(osBranch);
					}
					
					if (!sdkVersion.equals("")){
						if (Version.parseVersion(sdkVersion).getMajor() != 0){
							sdk.setSDKVersion(Version.parseVersion(sdkVersion));
						}
					}
					
					if (customEpocroot != null) {
						sdk.setEPOCROOT(customEpocroot);
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
		
		Location configurationLocation = Platform.getConfigurationLocation();
		try {
					
			URL url = new URL(configurationLocation.getURL(), SDKCorePlugin.PLUGIN_ID);
			File configFolder = new File(url.getFile());
			if (!configFolder.exists()) {
				configFolder.mkdirs();
			}
			
			File carbideSDKCacheFile = new File(configFolder, CARBIDE_SDK_CACHE_FILE_NAME);
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
					
					Node wasScannedNode = d.createAttribute(SDK_SCANNED_FOR_PLUGINS);
					if (true == currSDK.isPreviouslyScanned()) {
						wasScannedNode.setNodeValue("true");
					} else {
						wasScannedNode.setNodeValue("false");
					}
					attribs.setNamedItem(wasScannedNode);
					
					Node osVerNode = d.createAttribute(SDK_CACHE_OS_VERSION_ATTRIB);
					osVerNode.setNodeValue(currSDK.getOSVersion().toString());
					attribs.setNamedItem(osVerNode);
					
					Node osBranchNode = d.createAttribute(SDK_CACHE_OS_BRANCH_ATTRIB);
					osBranchNode.setNodeValue(currSDK.getSDKOSBranch());
					attribs.setNamedItem(osBranchNode);
					
					Node sdkVerNode = d.createAttribute(SDK_CACHE_SDK_VERSION_ATTRIB);
					sdkVerNode.setNodeValue(currSDK.getSDKVersion().toString());
					attribs.setNamedItem(sdkVerNode);
					
					if (!isEPOCRootFixed()) {
						Node sdkEpocRootNode = d.createAttribute(SDK_CACHE_EPOCROOT_ATTRIB);
						sdkEpocRootNode.setNodeValue(currSDK.getEPOCROOT());
						attribs.setNamedItem(sdkEpocRootNode);
					}
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
		} catch (MalformedURLException e){
			
		}
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
			try {
				IPath sbsPath = SBSv2Utils.getSBSPath();
				Process p = rt.exec(new String[] { sbsPath.toOSString(), "-v" });
				
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String overallOutput = null;
				String stdErrLine = null;
				while ((stdErrLine = br.readLine()) != null) {
					overallOutput += stdErrLine;
				}
				
				if (overallOutput != null) {
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
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			
		}
		return sbsV2Version;
	}

	public Version getMinimumSupportedSBSv2Version() {
		return MINIMUM_RAPTOR_VERSION;
	}
	
	
}
