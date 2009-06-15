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

import java.io.*;
import java.net.*;
import java.util.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.cdt.utils.WindowsRegistry;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Version;
import org.w3c.dom.*;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.SAXException;

import com.nokia.carbide.cpp.internal.api.sdk.*;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesType;
import com.nokia.carbide.cpp.internal.sdk.core.xml.DevicesLoader;
import com.nokia.carbide.cpp.sdk.core.*;
import com.nokia.carbide.cpp.sdk.core.ICarbideInstalledSDKChangeListener.SDKChangeEventType;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.ListenerList;
import com.sun.org.apache.xpath.internal.XPathAPI;

public class SDKManager implements ISDKManager, ISDKManagerInternal {
	
	private static List<ISymbianSDK> sdkList = new ArrayList<ISymbianSDK>();
	private HashMap<String,ISymbianSDK> missingSdkMap = new HashMap<String,ISymbianSDK>();

	public final String SYMBIAN_COMMON_REG_PATH="SOFTWARE\\Symbian\\EPOC SDKs\\";
	public final String SYMBIAN_COMMON_PATH = "CommonPath";
	
	public final String WINDOWS_SYSTEM_ROOT_REG = "SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\";
	public final String WINDOWS_SYSTEM_ROOD_KEY = "SystemRoot";
	
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

	
	public void updateSDK(ISymbianSDK sdk){
		try {
			File devicesFile = getDevicesXMLFile();
			
			// If file does not exist exception will catch it
			DevicesLoader.updateDevice(sdk, devicesFile.toURL());
			updateCarbideSDKCache();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addSDK(ISymbianSDK sdk) {
		synchronized(sdkList)
		{
			updateSDK(sdk);
			sdkList.add(sdk);
			SDKManagerInternalAPI.removeMissingSdk(sdk.getUniqueId());
			// tell others about it
			fireInstalledSdkChanged(SDKChangeEventType.eSDKAdded);
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

	/**
	 * Read the devices.xml locaiton from the local machine registry.
	 * @return String with full path to file if it exists. Empty string File (may be null) if file does not exist or regisry not read.
	 */
	private File getDevicesXMLFromRegistry(){
		WindowsRegistry wr = WindowsRegistry.getRegistry();
		String regPath = wr.getLocalMachineValue(SYMBIAN_COMMON_REG_PATH, SYMBIAN_COMMON_PATH);
		boolean devicesFileExists = true;
		
		if (regPath == null || !new File(regPath).exists()){
			// No registry entry found...
			String errMsg = "Could not read registry for local machine key: " +  SYMBIAN_COMMON_REG_PATH + " Cannot get devices.xml for installed SDKs.";
			ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, SDKCorePlugin.getPluginId(), IStatus.ERROR, errMsg, null));
			devicesFileExists = false;
		}
		
		if (devicesFileExists){
			// path exists, not try to check for fullpath + file
			int len = regPath.length();
			if (regPath.charAt(len-1) != '\\'){
				regPath += "\\";
			}
			regPath += "devices.xml";
			if (!new File(regPath).exists()){
				String errMsg = "Devices.xml does not exist at: " + regPath;
				ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, SDKCorePlugin.getPluginId(), IStatus.ERROR, errMsg, null));
				devicesFileExists = false;
			}
		}
		
		if (!devicesFileExists){
			regPath = "";
		}
		
		return new File(regPath);
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
		File devicesFile = getDevicesXMLFromRegistry();
		
		if (!devicesFile.exists()) {
			// Not in registry, get the OS drive from the windows registry
			WindowsRegistry wr = WindowsRegistry.getRegistry();
			String regPath = "";
			if (wr != null) {
				regPath = wr.getLocalMachineValue(WINDOWS_SYSTEM_ROOT_REG,
						WINDOWS_SYSTEM_ROOD_KEY);
			}

			String osDriveSpec;
			if (regPath.length() > 2 && regPath.substring(1, 2).equals(":")) {
				osDriveSpec = regPath.substring(0, 2);
			} else {
				osDriveSpec = DEFAULT_DEVICES_DRIVE_SPEC; // Just use the default drive spec, some problem reading the registry
			}

			devicesFile = new File(osDriveSpec + DEFAULT_DEVICES_XML_DIR
					+ DEVICES_FILE_NAME);
		}

		return devicesFile;
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
				// intervals for 40 seconds and see if we get a response. On the first reposon we break out
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
				// armcc isnt' in this directory, ignore....
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
	
	private boolean checkDevicesXMLExistAndCreate(){
		boolean fileCreated = false;
		ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
		if (sdkMgr == null){
			return false; 
		}
		
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		Shell shell = null;
		if (window != null) {
			shell = window.getShell();
		} else {
			return false; 
		}
		
		File devicesFile = sdkMgr.getDevicesXMLFile();
		if (!devicesFile.exists()){
			if (true == MessageDialog.openQuestion(shell, "Cannot find devices.xml.", "Cannot find devices.xml under:\n\n" + DEFAULT_DEVICES_XML_DIR + DEVICES_FILE_NAME + "\n or \nRegistry: HKEY_LOCAL_MACHINE\\SOFTWARE\\Symbian\\EPOC SDKs\\\n\nThis file is required for Carbide.c++ use.\n\nDo you want Carbide to create this file?\n\n")){
				try {
					// First check to make sure the directory exists....
					File devicesPath = new File(DEFAULT_DEVICES_XML_DIR);
					if (!devicesPath.exists()){
						devicesPath.mkdirs();
					}
					
					devicesFile = new File(DEFAULT_DEVICES_XML_DIR + DEVICES_FILE_NAME);
					devicesFile.createNewFile();
					FileWriter fw = new FileWriter(devicesFile);
					fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?><devices version=\"1.0\"></devices>");
					fw.close();
					fileCreated = true;
					MessageDialog.openInformation(shell, "Devices.xml added", "Devices.xml was created successfully. Please add an SDK under the SDK Preferences page with the \"Add\" button before you attempt to create a project.");
					
				} catch (IOException e){
					MessageDialog.openError(shell, "Cannot create file.", "Could not create file: " + devicesFile.toString());
					e.printStackTrace();
				}
				
			} else {
				MessageDialog.openError(shell, "File not created.", "Devices.xml not created. You will be unable to create projects in Carbide.c++ until this file exists.");
			}
		}
		
		return fileCreated;
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
}
