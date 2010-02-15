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
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
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

import org.eclipse.cdt.utils.WindowsRegistry;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Version;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.SAXException;

import com.nokia.carbide.cpp.internal.api.sdk.BuildPlat;
import com.nokia.carbide.cpp.internal.api.sdk.ICarbideDevicesXMLChangeListener;
import com.nokia.carbide.cpp.internal.api.sdk.ISDKManagerInternal;
import com.nokia.carbide.cpp.internal.api.sdk.ISDKManagerLoadedHook;
import com.nokia.carbide.cpp.internal.api.sdk.SBSv2Utils;
import com.nokia.carbide.cpp.internal.api.sdk.SDKManagerInternalAPI;
import com.nokia.carbide.cpp.internal.api.sdk.SymbianMacroStore;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesType;
import com.nokia.carbide.cpp.internal.sdk.core.xml.DevicesLoader;
import com.nokia.carbide.cpp.sdk.core.ICarbideInstalledSDKChangeListener;
import com.nokia.carbide.cpp.sdk.core.IRVCTToolChainInfo;
import com.nokia.carbide.cpp.sdk.core.ISDKManager;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.carbide.cpp.sdk.core.SDKEnvInfoFailureException;
import com.nokia.carbide.cpp.sdk.core.ICarbideInstalledSDKChangeListener.SDKChangeEventType;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.ListenerList;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;
import com.sun.org.apache.xpath.internal.XPathAPI;

public class SDKManager implements ISDKManager, ISDKManagerInternal {
	
	private static List<ISymbianSDK> sdkList = new ArrayList<ISymbianSDK>();
	private HashMap<String,ISymbianSDK> missingSdkMap = new HashMap<String,ISymbianSDK>();

	private static final String SYMBIAN_COMMON_REG_PATH = "SOFTWARE\\Symbian\\EPOC SDKs\\";
	private static final String SYMBIAN_COMMON_PATH = "CommonPath";
	
	private static final String WINDOWS_SYSTEM_ROOT_REG = "SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\";
	private static final String WINDOWS_SYSTEM_ROOT_KEY = "SystemRoot";

	private static final String EMPTY_DEVICES_XML_CONTENT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><devices version=\"1.0\"></devices>";
	
	private static final String CARBIDE_SDK_CACHE_FILE_NAME = "carbideSDKCache.xml";
	private static final String SDK_CACHE_ID_ATTRIB = "id";
	private static final String SDK_CACHE_ENABLED_ATTRIB = "isEnabled";
	private static final String SDK_CACHE_OS_VERSION_ATTRIB = "osVersion";
	private static final String SDK_CACHE_OS_BRANCH_ATTRIB = "osBranch";
	private static final String SDK_CACHE_SDK_VERSION_ATTRIB = "sdkVersion";
	private static final String SDK_SCANNED_FOR_PLUGINS = "sdkScanned";
	
	private static final String EMPTY_STRING = "";
	private static boolean enableBSFScanner;
	private static List<BuildPlat> platList = new ArrayList<BuildPlat>();
	private static SymbianMacroStore macroStore;
	
	private static final String[] knownRVCTVersions = {"3.1", "3.0", "2.2", "2.1"};
	private Version sbsV2Version;
	
	/**
	 * Minimum SBSv2 version supported with Carbide
	 */
	public static final Version MINIMUM_RAPTOR_VERSION = new Version(2, 8, 6);

	static boolean hasPromptedForDevicesXML = false; // make sure we only ask once at startup if devices.xml does not exist
	static boolean hasScannedSDKs = false; // make sure we only scan SDKs when needed
	long devicesXLMLastModified;
	
	private static List<IRVCTToolChainInfo> rvctInfoList = null;
	
	/**
	 * Registry key location for checking CSL (GCCE) Arm Toolchain installation directory. 
	 */
	private static final String CSL_ARM_TOOLCHAIN_REG_PATH="SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\CSL Arm Toolchain (arm-symbianelf)_is1";

	/**
	 * Registry key for checking CSL (GCCE) Arm Toolchain installation directory. 
	 */
	private static final String CSL_ARM_TOOLCHAIN_REG_KEY="InstallLocation";
	
	/**
	 * Implement listener so other class that use this can listen to SDK 
	 * change event (e.g. validate project config description)
	 */
	private static ListenerList<ICarbideInstalledSDKChangeListener> listeners = new ListenerList<ICarbideInstalledSDKChangeListener>();
	
	/*
	 * Implement listener so other class that use this can listen for when a change to the devices.xml
	 * has been modified outside of Carbide
	 */
	private static ListenerList<ICarbideDevicesXMLChangeListener> devicesXMLListeners = new ListenerList<ICarbideDevicesXMLChangeListener>();
	
	static boolean sdkHookExtenstionsNotified;
	public static final String SDK_MANAGER_LOADED_HOOK = SDKCorePlugin.PLUGIN_ID + ".sdkManagerLoadedHook"; //$NON-NLS-1$
	
	public SDKManager() {
		macroStore = SymbianMacroStore.getInstance();
		
		checkPerlInstallation();
		
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
			DevicesType devicesType;
			try {
				File devicesFile = getDevicesXMLFile();

				if (devicesFile == null || !devicesFile.exists()) {
					// There is no devices.xml. Ask the user if he/she wants to
					// add it
					if (hasPromptedForDevicesXML == false) {
						hasPromptedForDevicesXML = true;
						doAsynchPromptCreateDevicesXML();
					}
					return; // no devices.xml file..
				}

				devicesXLMLastModified = devicesFile.lastModified();
				devicesType = DevicesLoader.loadDevices(devicesFile.toURL());
				EList devices = devicesType.getDevice();
				for (Iterator iter = devices.iterator(); iter.hasNext();) {
					SymbianSDK sdk = new SymbianSDK((DeviceType) iter.next());
					sdkList.add(sdk);
				}

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

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// make sure we don't rescan over and over again
		hasScannedSDKs = true;
		
		// tell others about it
		fireInstalledSdkChanged(SDKChangeEventType.eSDKScanned);
		scanCarbideSDKCache();
	}

	private void ensureScannedSDKs() {
		if (!hasScannedSDKs) {
			scanSDKs();
			
			// Notify any plugins that want to know if the SDKManager has scanned plugins.
			if (!sdkHookExtenstionsNotified) {
				notifySDKManagerLoaded();
				sdkHookExtenstionsNotified = true;
			}
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

	
	public void updateSDK(ISymbianSDK sdk) {
		try {
			File devicesFile = getDevicesXMLFile();
			
			// If file does not exist exception will catch it
			DevicesLoader.updateDevice(sdk, devicesFile.toURL());
			updateCarbideSDKCache();
			
		} catch (Exception e) { 
			// must catch and rethrow as unchecked exception this 
			// because no throws clause in API method
			throw new RuntimeException(e);
		}
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
	
	public void setDefaultSDK(ISymbianSDK sdk){
		try {
			File devicesFile = getDevicesXMLFile();
			
			synchronized(sdkList)
			{
				for (ISymbianSDK currSDK : sdkList){
					if (!currSDK.getUniqueId().equals(sdk.getUniqueId())){
						currSDK.setIsDefaultSDK(false);  // set all to false, except the input one
					}
				}
			}
			
			DevicesLoader.setDefaultDevice(sdk, devicesFile.toURL());
			updateCarbideSDKCache();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
						
						// Now actually remove it from the file...
						DevicesType devicesType;
						try {
							File devicesFile = getDevicesXMLFile();
							 
							// if file does not exist, let exception cath it...
							devicesType = DevicesLoader.loadDevices(devicesFile.toURL());
							EList devices = devicesType.getDevice();
							for (Iterator iter = devices.iterator(); iter.hasNext();) {
								DeviceType device = (DeviceType)iter.next();
								if (device.getId().equals(sdkId)){
									if (!DevicesLoader.deleteDeviceEntry(device, devicesFile.toURL())){
										return false; // write failed
									}
									break;
								}
							}
						} catch (MalformedURLException e) {
							e.printStackTrace();
						} catch (URISyntaxException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
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

	// Read the devices.xml locaiton from the local machine registry.
	// return IPath with absolute path to file if it exists or null if file does not exist or registry entry not found.
	private IPath getDevicesXMLFromRegistry(){
		String regValue = WindowsRegistry.getRegistry().getLocalMachineValue(SYMBIAN_COMMON_REG_PATH, SYMBIAN_COMMON_PATH);
		IPath regPath = regValue != null ? new Path(regValue) : null;
		
		if (regPath == null){
			// No registry entry found...
			String errMsg = MessageFormat.format(
							"Could not read registry for local machine key: {0} Cannot get devices.xml for installed SDKs.",
							SYMBIAN_COMMON_REG_PATH);
			logError(errMsg, null);
			return null;
		}

		// registry entry exists, check existence of file
		regPath = regPath.append(DEVICES_FILE_NAME);
		if (!regPath.toFile().exists()){
			String errMsg = MessageFormat.format("Devices.xml does not exist at: {0}", regPath);
			logError(errMsg, null);
			return null;
		}
		
		return regPath;
	}
	
	private void scanCarbideSDKCache(){
		
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

	public File getDevicesXMLFile() {
		IPath devicesPath = getDevicesXMLFromRegistry();
		
		if (devicesPath != null && devicesPath.toFile().exists()) {
			return devicesPath.toFile();
		}

		// Not in registry, get the OS drive from the windows registry
		String regValue = WindowsRegistry.getRegistry().getLocalMachineValue(WINDOWS_SYSTEM_ROOT_REG, WINDOWS_SYSTEM_ROOT_KEY);

		String osDriveSpec = regValue != null ? new Path(regValue).getDevice() : DEFAULT_DEVICES_DRIVE_SPEC;

		IPath deviceDirPath = new Path(osDriveSpec, DEFAULT_DEVICES_XML_DIR);
		return deviceDirPath.append(DEVICES_FILE_NAME).toFile();
	}
	
	public String getCSLArmToolchainInstallPathAndCheckReqTools() throws SDKEnvInfoFailureException{
		
		String installPath = null;
		
		try {			
			WindowsRegistry wr = WindowsRegistry.getRegistry();
			installPath = wr.getLocalMachineValue(CSL_ARM_TOOLCHAIN_REG_PATH, 
													 CSL_ARM_TOOLCHAIN_REG_KEY);			
		} catch (Exception e) {			
			//TODO: Localise
			String errMsg = "Could not read registry for local machine key: '" +  CSL_ARM_TOOLCHAIN_REG_PATH 
								+ " (" + e.getMessage() +").";
			throw new SDKEnvInfoFailureException(errMsg);
		}
		
		if (!new File(installPath).exists()){
			//TODO: Localise
			String errMsg = "CSL Arm Toolchain installation path pointed by registry key '" 
							+  CSL_ARM_TOOLCHAIN_REG_PATH 
							+ "' does not exist.";
			throw new SDKEnvInfoFailureException(errMsg);
		}

		String gcceToolDir = installPath + "\\bin";
		
		String[] gccBinToolList = { "arm-none-symbianelf-nm.exe",
									"arm-none-symbianelf-readelf.exe",
									"arm-none-symbianelf-c++filt.exe"
									};
		String toolName = null;
		String toolPathName = null;
		for (int i = 0; i < gccBinToolList.length; i++) {
			toolName = gccBinToolList[i];
			toolPathName = gcceToolDir + "\\" + toolName;
			
			if (!new File(toolPathName).exists()){
				//TODO: Localise
				String errMsg = "Required tool from CSL Arm Toolchain is missing: " 
								+ toolPathName;
				throw new SDKEnvInfoFailureException(errMsg);
			}			
		}
		
		return gcceToolDir;
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
	private static List<IRVCTToolChainInfo> scanForInstalledRVCTCompilers() {
		
		Runtime rt=Runtime.getRuntime();
		List<IRVCTToolChainInfo> rvctToolList = new ArrayList<IRVCTToolChainInfo>();
		String pathStr = System.getenv("PATH");
		String[] pathTokens = pathStr.split(";");
		
		for(String currTok : pathTokens) {
			try {
				String command = currTok;
				command += (currTok.endsWith("\\") || currTok.endsWith("/")) ? "" : File.separator;
				command += "armcc.exe --vsn";
				
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
							rvctInfo.setRvctToolBinariesDirectory(currTok);
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
	

	public void doAsynchPromptCreateDevicesXML() {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				checkDevicesXMLExistAndCreate();
			}
		});
		
	}
	
	public boolean checkDevicesXMLExistAndCreate() {
		Shell shell = WorkbenchUtils.getSafeShell();
		File devicesFile = getDevicesXMLFile();
		if (!devicesFile.exists()){
			if (MessageDialog.openQuestion(shell, "Devices.xml Not Found", 
					"Carbide.c++ requires a valid devices.xml file to manage SDKs.\n\nDo you want Carbide to create this file?")) {
				try {
					// First check to make sure the directory exists....
					if (!devicesFile.getParentFile().exists()){
						devicesFile.getParentFile().mkdirs();
					}
					
					devicesFile.createNewFile();

					FileWriter fw = new FileWriter(devicesFile);
					fw.write(EMPTY_DEVICES_XML_CONTENT);
					fw.close();

					MessageDialog.openInformation(shell, "Devices.xml File Created", 
							MessageFormat.format(
								"{0} was created successfully. Please add an SDK under the SDK Preferences page with the \"Add\" button before you attempt to create a project.",
								devicesFile.getAbsolutePath()));
					return true;
				} catch (IOException e){
					String message = "Could not create file: " + devicesFile.getAbsolutePath();
					MessageDialog.openError(shell, "Cannot Create File", message);
					logError(message, e);
				}
			}
		}
		
		return false;
	}
	
	protected void checkPerlInstallation(){
		
		Runtime rt=Runtime.getRuntime();
		
		// check for Perl
		try {
			Process p = rt.exec("perl.exe -v");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String overallOutput = null;
			String stdErrLine = null;
			while ((stdErrLine = br.readLine()) != null) {
				overallOutput += stdErrLine;
			}
			
			if (overallOutput != null && !overallOutput.contains("v5.6.1")){
				ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.WARNING, SDKCorePlugin.PLUGIN_ID, IStatus.WARNING, "Perl v5.6.1 was not detected. Some SDKs do not work with other Perl versions.", null));
			}
			
			p.destroy();
			
		}
		catch (IOException e) {
			//	report error to PDE log
			ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, SDKCorePlugin.PLUGIN_ID, IStatus.ERROR, "Perl was not found on the PATH environment variable. Some tools will not function properly. Perl 5.6.1 is recommended for Carbide use (free download at www.activestate.com).", e));
		 	// Report dialog since this is always fatal for future work and the error log
			// may be hidden
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				public void run() {
					IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
					MessageDialog.openError(window.getShell(), "Missing Perl", "Perl was not found on your PATH. The Symbian build tools cannot work successfully without Perl. Please install Perl (v5.6.1 recommended).");
				}
			});
		}
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
	
	public boolean checkDevicesXMLSynchronized(){
		if (devicesXLMLastModified == 0){
			return true; // no devices.xml file
		}
		
		File deviceFile = getDevicesXMLFile();
		if (deviceFile.exists()){
			if (deviceFile.lastModified() <= devicesXLMLastModified){
				return true;  // file is up to date, nothing to do.
			} else {
				// file is out of date but let's make sure some data
				// has actually changed...
				boolean needsRescan = false;
				devicesXLMLastModified = deviceFile.lastModified();
				
				// Read the devices.xml and see if our current SDK list differs from the list
				// we get from devices.xml
				List<ISymbianSDK> localSDKList = new ArrayList<ISymbianSDK>();
				DevicesType devicesType;
				try {
					devicesType = DevicesLoader.loadDevices(deviceFile.toURL());
					EList devices = devicesType.getDevice();
					for (Iterator iter = devices.iterator(); iter.hasNext();) {
						SymbianSDK sdk = new SymbianSDK((DeviceType) iter.next());
						localSDKList.add(sdk);
					}
				}
				catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if (localSDKList.size() != sdkList.size()){
					needsRescan = true;
				} else {
					for (ISymbianSDK sdk : localSDKList){
						ISymbianSDK currSDK = getSDK(sdk.getUniqueId(), false);
						if (currSDK == null){
							// sdk id has been changed
							needsRescan = true;
							break;
						}
						// check that the data are the same.
						// Other than the 'id' attrib, all we really care is whether or not
						// the EPOCROOT or vendor 'name' has changed.
						if (!sdk.getEPOCROOT().equalsIgnoreCase(currSDK.getEPOCROOT())){
							needsRescan = true;
							break;
						}
						if (!sdk.getName().equalsIgnoreCase(currSDK.getName())){
							needsRescan = true;
							break;
						}
					}
				}
				
				if (!needsRescan){
					return true; // file has changed but data is up to date.
				}

				hasScannedSDKs = false;
				return false;  // SDKs were not up to date.
			}
		} else {
			return true;
		}
	}
	
	private void logError(String message, Throwable t) {
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
				
			} 
			catch (IOException e) {  
				// no such process, SBSv2 not available  
				Logging.log(SDKCorePlugin.getDefault(), Logging.newSimpleStatus(0, IStatus.WARNING,
						MessageFormat.format("Could not find or launch Raptor script ''{0}''; SBSv2 support will not be available",
								sbsPath), e));  
				} 
			if (p != null)	 {
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String overallOutput = null;
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
							}
							break;
						}
						numTries++;
					}

				} catch (IOException e) { 
					e.printStackTrace();
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
							sdkHook = (ISDKManagerLoadedHook) providerElement.createExecutableExtension("class"); //$NON-NLS-1$
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
