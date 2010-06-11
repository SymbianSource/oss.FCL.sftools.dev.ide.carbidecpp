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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.cdt.utils.WindowsRegistry;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Version;

import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DefaultType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesFactory;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesType;
import com.nokia.carbide.cpp.internal.sdk.core.xml.DevicesLoader;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.carbide.cpp.sdk.core.SDKEnvInfoFailureException;
import com.nokia.cpp.internal.api.utils.core.HostOS;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

public class SDKManager extends AbstractSDKManager {
	
	private static final String SYMBIAN_COMMON_REG_PATH = "SOFTWARE\\Symbian\\EPOC SDKs\\";
	private static final String SYMBIAN_COMMON_PATH = "CommonPath";
	
	private static final String WINDOWS_SYSTEM_ROOT_REG = "SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\";
	private static final String WINDOWS_SYSTEM_ROOT_KEY = "SystemRoot";

	private static final String EMPTY_DEVICES_XML_CONTENT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><devices version=\"1.0\"></devices>";

	private static final String QMAKE_FILE = "epoc32/tools/qt/qmake" + HostOS.EXE_EXT; //$NON-NLS-1$
	private static final String MIFCONV_FILE = "epoc32/tools/mifconv" + HostOS.EXE_EXT; //$NON-NLS-1$
	private static final String ABLD_FILE = "epoc32/tools/abld.pl"; //$NON-NLS-1$
	private static final long VALID_ABLD_SIZE = 1024;

	static boolean hasPromptedForDevicesXML = false; // make sure we only ask once at startup if devices.xml does not exist
	long devicesXLMLastModified;
	
	/**
	 * Registry key location for checking CSL (GCCE) Arm Toolchain installation directory. 
	 */
	private static final String CSL_ARM_TOOLCHAIN_REG_PATH="SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\CSL Arm Toolchain (arm-symbianelf)_is1";

	/**
	 * Registry key for checking CSL (GCCE) Arm Toolchain installation directory. 
	 */
	private static final String CSL_ARM_TOOLCHAIN_REG_KEY="InstallLocation";
	
	public SDKManager() {
		super();
		checkPerlInstallation();
	}
	
	protected boolean doScanSDKs(IProgressMonitor monitor) {
		boolean result = true;
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
				result = false; // no devices.xml file..
			} else {
				devicesXLMLastModified = devicesFile.lastModified();
				devicesType = DevicesLoader.loadDevices(devicesFile.toURL());
				EList devices = devicesType.getDevice();
				for (Iterator iter = devices.iterator(); iter.hasNext();) {
					SymbianSDK sdk = new SymbianSDK((DeviceType) iter.next());
					sdkList.add(sdk);
				}
			}
		} catch (Exception e) {
			logError("Failed to scan devices.xml", e);
			result = false;
		}

		doScanDrives(monitor);
		return result;
	}

	public void updateSDK(ISymbianSDK sdk) {
		try {
			File devicesFile = getDevicesXMLFile();

			if (devicesFile == null || !devicesFile.exists()) {
				// There is no devices.xml. Ask the user if he/she wants to
				// add it
				doAsynchPromptCreateDevicesXML();
				return;
			}

			// If file does not exist exception will catch it
			DevicesLoader.updateDevice(sdk, devicesFile.toURL());
			updateCarbideSDKCache();
			
		} catch (Exception e) { 
			// must catch and rethrow as unchecked exception this 
			// because no throws clause in API method
			throw new RuntimeException(e);
		}
	}
	
	protected boolean doRemoveSDK(String sdkId) {
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
			
			return true;
		} catch (Exception e) {
			logError("Failed to remove SDK", e);
		}
		return false;
		
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
		
		String[] gccBinToolList = { "arm-none-symbianelf-nm" + HostOS.EXE_EXT,
									"arm-none-symbianelf-readelf" + HostOS.EXE_EXT,
									"arm-none-symbianelf-c++filt" + HostOS.EXE_EXT
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

	@Override
	protected boolean isEPOCRootFixed() {
		return true;
	}

	/**
	 * Scan system drives for installed SDKs
	 */
	protected void doScanDrives(IProgressMonitor monitor) {
		File[] drives = getSystemDrives();
		monitor.beginTask("Scanning system drives for installed SDKs", drives.length);
		for (File drive : drives) {
			if (!isEPOCRoot(drive)) {
				continue;
			}

			String sdkId = getUniqueSDKId(drive);
			DeviceType deviceType = DevicesFactory.eINSTANCE.createDeviceType();
			deviceType.setAlias(drive.toString());
			deviceType.setDefault(DefaultType.NO_LITERAL);
			deviceType.setEpocroot(drive.getAbsolutePath());
			deviceType.setId(sdkId);
			deviceType.setName("com.nokia.s60");
			deviceType.setToolsroot(drive.getAbsolutePath());
			deviceType.setUserdeletable("false");
			deviceType.setUserdeletetable("false");
			ISymbianSDK sdk = new SymbianSDK(deviceType);
			if (sdk.getOSVersion().toString().equals("0.0.0")) {
				((SymbianSDK)sdk).setOSVersion(new Version("9.5"));
			}

			if (!isSupportedSDK(sdk)) {
				continue;
			}
			if (isInSDKList(sdk)) {
				continue;
			}

			sdkList.add(sdk);
			monitor.worked(1);
			if (monitor.isCanceled()) {
				monitor.done();
				return;
			}
		}
		monitor.done();
	}

	private File[] getSystemDrives() {
		if (HostOS.IS_WIN32) {
			return File.listRoots();
		}
		return new File[0];
	}

	private String getUniqueSDKId(File drive) {
		String sdkId = drive.toString().charAt(0) + "_SDK";
		int suffice = 1;
		while (!isUniqueSDKId(sdkId)) {
			sdkId = drive.toString().charAt(0) + "_SDK" + suffice;
			suffice++;
		}
		return sdkId;
	}

	private boolean hasAbldSupport(ISymbianSDK sdk) {
		File abld = new File(sdk.getEPOCROOT(), ABLD_FILE);
		if (abld.exists()) {
			long size = abld.length();
			if (size >= VALID_ABLD_SIZE)
				return true;
		}
		return false;
	}

	private boolean hasQmake(ISymbianSDK sdk) {
		File qmake = new File(sdk.getEPOCROOT(), QMAKE_FILE);
		if (qmake.exists()) {
			return true;
		}
		return false;
	}

	private boolean hasRaptor(ISymbianSDK sdk) {
		File mifconv = new File(sdk.getEPOCROOT(), MIFCONV_FILE);
		if (mifconv.exists()) {
			return true;
		}
		return false;
	}

	private boolean isEPOCRoot(File drive) {
		File epocRoot = new File(drive, "epoc32");
		if (epocRoot.exists()) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isInSDKList(ISymbianSDK sdk) {
		for (ISymbianSDK entry : sdkList) {
			if (entry.getEPOCROOT().equalsIgnoreCase(sdk.getEPOCROOT())) {
				return true;
			}
		}
		return false;
	}

	private boolean isSupportedSDK(ISymbianSDK sdk) {
		if (!hasAbldSupport(sdk) || hasQmake(sdk) || hasRaptor(sdk)) {
			return true;
		}
		return false;
	}

	private boolean isUniqueSDKId(String sdkId) {
		for (ISymbianSDK sdk : SDKCorePlugin.getSDKManager().getSDKList()){
			if (sdk.getUniqueId().equalsIgnoreCase(sdkId)){
				return false;
			}
		}
		return true;
	}
}
