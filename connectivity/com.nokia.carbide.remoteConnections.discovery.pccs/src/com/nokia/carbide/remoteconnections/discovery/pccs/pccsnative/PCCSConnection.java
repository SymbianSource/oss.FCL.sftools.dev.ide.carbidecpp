/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.carbide.remoteconnections.discovery.pccs.pccsnative;

import java.nio.IntBuffer;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.cdt.utils.WindowsRegistry;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.swt.widgets.Display;
import org.osgi.service.prefs.BackingStoreException;

import com.nokia.carbide.remoteconnections.discovery.pccs.Activator;
import com.nokia.carbide.remoteconnections.discovery.pccs.Messages;
import com.nokia.carbide.remoteconnections.discovery.pccs.pccsnative.IConnAPILibrary.IConnAPIDeviceCallback;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.ShortByReference;

public class PCCSConnection {

	private static final String NOT_KNOWN = "not known"; //$NON-NLS-1$ // used for all string structure elements that come back from PCCS as null
	private boolean DEBUG = false;
	private boolean SWITCH_TO_RNDIS = false; // false for 2.x, true for 3.x
	private String DONT_ASK_AGAIN_KEY = "DontAskAgain"; //$NON-NLS-1$
	
	/**
	 *
	 */
	public class DeviceNotificationCallback implements IConnAPIDeviceCallback {

		/* (non-Javadoc)
		 * @see com.nokia.carbide.remoteconnections.discovery.pccs.pccsnative.IConnAPILibrary.IConnAPIDeviceCallback#invoke(int, com.sun.jna.ptr.ShortByReference)
		 */
		public int invoke(final int dwStatus, final ShortByReference pstrSerialNumber) {
			Display.getDefault().asyncExec(
				new Runnable ()	{
					public void run() {
						// check for NULL, since I am seeing bogus events coming from PCCS before the real one
						//  and the serial number is not filled in. If the serial number is null,
						//  everything else will be null (according to the PCCS docs)
						//  TODO: bug in PCCS API: 
						if (DEBUG) System.out.printf("DeviceNotificationCallback %x %s\n", dwStatus, (pstrSerialNumber == null ? "serNum: null" : pstrSerialNumber.getPointer().getString(0, true))); //$NON-NLS-1$ //$NON-NLS-2$
						String serialNumber = NOT_KNOWN;
						if (pstrSerialNumber != null) {
							serialNumber = pstrSerialNumber.getPointer().getString(0, true);
						}

						DeviceEventListener.DeviceEvent eventType = DeviceEventListener.DeviceEvent.DEVICE_LIST_UPDATED;
						// decode dwStatus per PCCS docs
						switch(DMAPIDefinitions.GET_CONAPI_CB_STATUS(dwStatus)) {
						case DMAPIDefinitions.CONAPI_DEVICE_LIST_UPDATED:
							eventType = DeviceEventListener.DeviceEvent.DEVICE_LIST_UPDATED;
							break;
						case DMAPIDefinitions.CONAPI_DEVICE_ADDED:
							eventType = DeviceEventListener.DeviceEvent.DEVICE_ADDED;
							break;
						case DMAPIDefinitions.CONAPI_DEVICE_REMOVED:
							eventType = DeviceEventListener.DeviceEvent.DEVICE_REMOVED;
							break;
						case DMAPIDefinitions.CONAPI_DEVICE_UPDATED:
							switch(DMAPIDefinitions.GET_CONAPI_CB_INFO(dwStatus)) {
							case DMAPIDefinitions.CONAPI_CONNECTION_ADDED:
								eventType = DeviceEventListener.DeviceEvent.DEVICE_UPDATED_ADDEDCONNECTION;
								break;
							case DMAPIDefinitions.CONAPI_CONNECTION_REMOVED:
								eventType = DeviceEventListener.DeviceEvent.DEVICE_UPDATED_REMOVEDCONNECTION;
								break;
							case DMAPIDefinitions.CONAPI_DEVICE_RENAMED:
								eventType = DeviceEventListener.DeviceEvent.DEVICE_UPDATED_RENAMED;
								break;
							}
						}
						// fire events
						Iterator<DeviceEventListener> iter = listeners.iterator();
						while (iter.hasNext()) {
							iter.next().onDeviceEvent(eventType, serialNumber);
						}
					}
				}
			);
			return PCCSErrors.CONA_OK;
		}
	}

	private static final Collection<DeviceEventListener> listeners = new LinkedList<DeviceEventListener>();
	private IConnAPILibrary library;
	private DeviceNotificationCallback pfnCallback = new DeviceNotificationCallback();
	public static final int PCCS_NOT_FOUND = 1;
	public static final int PCCS_WRONG_VERSION = 2;
	
	private APIHANDLE dmHandle = APIHANDLE.INVALID_HANDLE_VALUE;
	private APIHANDLE mcHandle = APIHANDLE.INVALID_HANDLE_VALUE;
	private APIHANDLE upHandle = APIHANDLE.INVALID_HANDLE_VALUE;
	
	private void storeDontAskAgain() {
		Activator.getDefault().getPreferenceStore().setValue(DONT_ASK_AGAIN_KEY, true);
		try {
			new InstanceScope().getNode(Activator.PLUGIN_ID).flush();
		} catch (BackingStoreException e) {
			Activator.logError(e);
		}
	}
	private boolean dontAskAgain() {
		return Activator.getDefault().getPreferenceStore().getBoolean(DONT_ASK_AGAIN_KEY);
	}
	/**
	 * 
	 */
	public PCCSConnection() {
	}

	public void open() throws CoreException {
		if (library == null) {
			library = ConnAPILibrary.getInstance();
		}
		// this uses the MCAPI only to enable required media
		ensureMediaEnabled();
		
		// load DMAPI
		loadDMAPI();
	}
	
	private void ensureMediaEnabled() throws CoreException {
		// open the MCAPI
		loadMCAPI();
		// TODO:
		// get media
		// enable media
		// close MCAPI
		closeMCAPI();
	}

	/**
	 * Initializes the Device Management API (DMAPI) for use.
	 * 
	 * @throws CoreException
	 */
	private void loadDMAPI() throws CoreException {

		int dwResult = library.DMAPI_Initialize(DMAPIDefinitions.DMAPI_VERSION_34, null);
    	if (dwResult != PCCSErrors.CONA_OK) {
    		library.DMAPI_Terminate(null);
    		String msg;
    		if (Activator.isSymSEELayout()) {
        		msg = String.format("PCCS DMAPI_Initialize API returned error on initialization %x", dwResult); //$NON-NLS-1$
        		if (dwResult == PCCSErrors.ECONA_UNSUPPORTED_API_VERSION) {
        			msg = Messages.PCCSConnection_PCCS_Version_Error + Activator.getLoadErrorURL();
        		}
    		} else {
    			msg = Messages.PCCSConnection_PCSuite_Version_Error + Activator.getLoadErrorURL();
    		}
    		throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, PCCS_WRONG_VERSION, msg, null));
    	}
    	
    	// initialize common API
//    	dwResult = library.CFAPI_Initialize(DMAPIDefinitions.CFAPI_VERSION_10, null); unnecessary
//    	if (dwResult != PCCSErrors.CONA_OK) {
//    		library.DMAPI_Terminate(null);
//    		String msg = String.format("PCCS CFAPI_Initialize API returned error on initialization %x", dwResult);
//    		throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, dwResult, msg, null));
//    	}
    	
    	// open a DM handle
    	dmHandle = APIHANDLE.INVALID_HANDLE_VALUE;
    	LPAPIHANDLE pHandle = new LPAPIHANDLE();
    	dwResult = library.CONAOpenDM(pHandle);
    	
    	if (dwResult != PCCSErrors.CONA_OK) {
    		library.DMAPI_Terminate(null);
    		String msg = String.format(Messages.PCCSConnection_PCCS_CONAOpenDM_Error, dwResult);
    		if (dwResult == PCCSErrors.ECONA_NOT_ENOUGH_MEMORY) {
    			msg = Messages.PCCSConnection_PCCS_Not_Enough_Memory_Error;
    		}
    		throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, dwResult, msg, null));
    	} else {
    		dmHandle = pHandle.getValue();
    	}
    	
    	// register a call back
    	dwResult = library.CONARegisterNotifyCallback(dmHandle, PCCSTypeDefinitions.API_REGISTER, pfnCallback);
    	if (dwResult != PCCSErrors.CONA_OK) {
//    		System.out.printf("CONAOpenDM returned: %x\n", dwResult);
    		library.DMAPI_Terminate(null);
    		library.CONACloseDM(dmHandle);
        	dmHandle = APIHANDLE.INVALID_HANDLE_VALUE;

        	String msg = String.format(Messages.PCCSConnection_PCCS_CONARegisterNotifyCallback_Error, dwResult);
    		if (dwResult == PCCSErrors.ECONA_INVALID_POINTER) {
    			msg = Messages.PCCSConnection_PCCS_CONARegisterNotifyCallback_Pointer_Error;
    		}
    		throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, dwResult, msg, null));
    	}
	}

	private void loadMCAPI() throws CoreException {
		int dwResult = PCCSErrors.CONA_OK;
		dwResult = library.MCAPI_Initialize(MCAPIDefinitions.MCAPI_VERSION_32, null);
    	if (dwResult != PCCSErrors.CONA_OK) {
    		library.MCAPI_Terminate(null);
    		String msg;
    		if (Activator.isSymSEELayout()) {
        		msg = String.format("PCCS MCAPI_Initialize API returned error on initialization %x", dwResult); //$NON-NLS-1$
        		if (dwResult == PCCSErrors.ECONA_UNSUPPORTED_API_VERSION) {
        			msg = Messages.PCCSConnection_PCCS_Version_Error + Activator.getLoadErrorURL();
        		}
    		} else {
    			msg = Messages.PCCSConnection_PCSuite_Version_Error + Activator.getLoadErrorURL();
    		}
    		throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, PCCS_WRONG_VERSION, msg, null));
    	}
    	LPAPIHANDLE pHandle = new LPAPIHANDLE();
    	dwResult = library.CONAOpenMM(pHandle, 0);
   
    	if (dwResult != PCCSErrors.CONA_OK) {
    		library.MCAPI_Terminate(null);
    		String msg = String.format("PCCS CONAOpenMM API returned error on initialization %x", dwResult); //$NON-NLS-1$
    		if (dwResult == PCCSErrors.ECONA_NOT_ENOUGH_MEMORY) {
    			msg = Messages.PCCSConnection_PCCS_Not_Enough_Memory_Error;
    		}
    		throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, dwResult, msg, null));
    	} else {
    		mcHandle = pHandle.getValue();
    	}
	}

	private void loadUPAPI() throws CoreException {
		int dwResult = PCCSErrors.CONA_OK;
		dwResult = library.UPAPI_Initialize(UPAPIDefinitions.UPAPI_VERSION_10, null);
    	if (dwResult != PCCSErrors.CONA_OK) {
    		library.UPAPI_Terminate(null);
    		String msg;
    		msg = String.format("PCCS UPAPI_Initialize API returned error on initialization %x", dwResult); //$NON-NLS-1$
    		if (DEBUG) System.out.println(msg);
    		if (Activator.isSymSEELayout()) {
        		if (dwResult == PCCSErrors.ECONA_UNSUPPORTED_API_VERSION) {
        			msg = Messages.PCCSConnection_PCCS_Version_Error + Activator.getLoadErrorURL();
        		}
    		} else {
    			msg = Messages.PCCSConnection_PCSuite_Version_Error + Activator.getLoadErrorURL();
    		}
    		throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, PCCS_WRONG_VERSION, msg, null));
    	}
    	LPAPIHANDLE pHandle = new LPAPIHANDLE();
    	dwResult = library.UPAPI_OpenUSBPersonality(0, pHandle);
    	if (dwResult != PCCSErrors.CONA_OK) {
    		library.UPAPI_Terminate(null);
    		String msg = String.format("PCCS OpenUSBPersonality API returned error on initialization %x", dwResult); //$NON-NLS-1$
    		if (DEBUG) System.out.println(msg);
    		if (dwResult == PCCSErrors.ECONA_NOT_ENOUGH_MEMORY) {
    			msg = Messages.PCCSConnection_PCCS_Not_Enough_Memory_Error;
    		}
    		throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, dwResult, msg, null));
    	} else {
    		upHandle = pHandle.getValue();
    	}
		if (DEBUG) System.out.println("\n-----loadUPAPI"); //$NON-NLS-1$

	}
	public void close() throws CoreException {
		if (library == null)
			return;

		// DMAPI should only one be open
		closeDMAPI();		
	}
	
	/**
	 * Closes the DMAPI.
	 * @throws CoreException
	 */
	private void closeDMAPI() throws CoreException {
		int dwResult = PCCSErrors.CONA_OK;
		if (!APIHANDLE.INVALID_HANDLE_VALUE.equals(dmHandle)) {
			// unregister callback
			dwResult = library.CONARegisterNotifyCallback(dmHandle, PCCSTypeDefinitions.API_UNREGISTER, pfnCallback);
			// close DM connection
			dwResult = library.CONACloseDM(dmHandle);
        	dmHandle = APIHANDLE.INVALID_HANDLE_VALUE;
        	// Terminate Common Functions API
//        	dwResult = library.CFAPI_Terminate(null); unnecessary
        	// Terminate Device management API
        	dwResult = library.DMAPI_Terminate(null);
		}
	}

	private void closeMCAPI() {
		int dwResult = PCCSErrors.CONA_OK;
		if (!APIHANDLE.INVALID_HANDLE_VALUE.equals(mcHandle)) {
			// close DM connection
			dwResult = library.CONACloseDM(mcHandle);
			mcHandle = APIHANDLE.INVALID_HANDLE_VALUE;
        	// Terminate Device management API
    		dwResult = library.MCAPI_Terminate(null);
		}
	}
	
	private void closeUPAPI() {
		int dwResult = PCCSErrors.CONA_OK;
		if (!APIHANDLE.INVALID_HANDLE_VALUE.equals(upHandle)) {
//		if (upHandle.getValue() != APIHANDLE.INVALID_HANDLE_VALUE) {
			// close DM connection
			dwResult = library.UPAPI_CloseUSBPersonality(upHandle);
			if (dwResult != PCCSErrors.CONA_OK) {
        		if (DEBUG) System.out.printf("UPAPI_CloseUSBPersonality dwResult = %x\n", dwResult); //$NON-NLS-1$
			}
			upHandle = APIHANDLE.INVALID_HANDLE_VALUE;
        	// Terminate Device management API
    		dwResult = library.UPAPI_Terminate(null);
			if (dwResult != PCCSErrors.CONA_OK) {
        		if (DEBUG) System.out.printf("UPAPI_Terminate dwResult = %x\n", dwResult); //$NON-NLS-1$
			}
		}
		if (DEBUG) System.out.println("closeUPAPI----\n"); //$NON-NLS-1$
	}

	public void getMediaList() {
		if (!APIHANDLE.INVALID_HANDLE_VALUE.equals(mcHandle)) {
			IntBuffer pdwCount = IntBuffer.allocate(1);
			
			CONAPI_MEDIA.ByReference[] ppMedia = (CONAPI_MEDIA.ByReference[])new CONAPI_MEDIA.ByReference().toArray(10);
			
			int dwResult = library.CONAMMGetMedia(mcHandle, pdwCount, ppMedia);
			if (DEBUG) System.out.printf("dwResult = %x\tpdwCount = %d\n", dwResult, pdwCount.get(0)); //$NON-NLS-1$
			int size = pdwCount.get(0);
			CONAPI_MEDIA[] pMedia = CONAPI_MEDIA.newArray(10);
			Pointer p0 = ppMedia[0].getPointer().getPointer(0);
			Pointer p1 = ppMedia[1].getPointer().getPointer(0);
			CONAPI_MEDIA media0 = ppMedia[0].newInstance();
			CONAPI_MEDIA media1 = ppMedia[1].newInstance();
			if (dwResult == PCCSErrors.CONA_OK) {
				for (int i = 0; i < size; i++) {
	//				if (DEBUG) {
	//					System.out.printf("dwSize=%d\tdwMedia=%x\tpstrDescription=%s\n", 
	//							media.dwMedia, 
	//							media.dwMedia, 
	//							(media.pstrDescription == null) ? "null" : media.pstrDescription.getPointer().getString(0, true));
	//					System.out.printf("dwState=%d\tdwOptions=%x\tdwMediaData=%d\tpstrID=%s\n", 
	//							media.dwState,
	//							media.dwOptions,
	//							media.dwMediaData,
	//							(media.pstrID == null) ? "null" : media.pstrID.getPointer().getString(0, true));
	//				}
	//
				}
				dwResult = library.CONAMMFreeMediaStructures(size, pMedia);
				if (dwResult != PCCSErrors.CONA_OK) {
					if (DEBUG) System.out.printf("CONAMMFreeMediaStructures = %x\n", dwResult); //$NON-NLS-1$
				}
			}
		}
	} 
	/**
	 * All devices detected are gotten here - BT, USB, etc.
	 *  No checking of a valid connection is done here
	 *  
	 * @return DeviceInfo[] - null if no devices connected
	 */
	private DeviceInfo[] getCompleteDeviceList() {
		DeviceInfo[] deviceInfo = null;
		
		IntByReference pdwCount = new IntByReference(0);
		int dwResult = library.CONAGetDeviceCount(dmHandle, pdwCount);
		if (DEBUG) System.out.printf("CONAGetDeviceCount: %x number of devices: %d\n", dwResult, pdwCount.getValue()); //$NON-NLS-1$
		if (dwResult != PCCSErrors.CONA_OK)
			return deviceInfo;
		
		int deviceCount = pdwCount.getValue();
		if (deviceCount > 0) {
			
			CONAPI_DEVICE[] pDevices = (CONAPI_DEVICE[])new CONAPI_DEVICE().toArray(deviceCount);
			dwResult = library.CONAGetDevices(dmHandle, pdwCount, pDevices);
			if (DEBUG) System.out.printf("CONAGetDevices: %x number of devices: %d\n", dwResult, deviceCount); //$NON-NLS-1$
			if (dwResult != PCCSErrors.CONA_OK) {
				return deviceInfo;
			}
			
			// Extract device information and connection info
			deviceInfo = new DeviceInfo[deviceCount];
			CONAPI_DEVICE[] devices = pDevices;
			for (int i = 0; i < deviceCount; i++) {
				deviceInfo[i] = new DeviceInfo();
				if (devices[i].pstrSerialNumber != null) {
					deviceInfo[i].serialNumber = devices[i].pstrSerialNumber.getPointer().getString(0, true);
				} else {
					// TODO: docs say if ser num == null --> device unsupported, but this is a bug in PCCS
					deviceInfo[i].serialNumber = NOT_KNOWN; //$NON-NLS-1$
				}
				if (devices[i].pstrFriendlyName != null) {
					deviceInfo[i].friendlyName = devices[i].pstrFriendlyName.getPointer().getString(0, true);
				} else {
					deviceInfo[i].friendlyName = NOT_KNOWN; //$NON-NLS-1$
				}
				if (devices[i].pstrModel != null) {
					deviceInfo[i].model = devices[i].pstrModel.getPointer().getString(0, true);
				} else {
					deviceInfo[i].model = NOT_KNOWN; //$NON-NLS-1$
				}
				if (devices[i].pstrManufacturer != null) {
					deviceInfo[i].mfr = devices[i].pstrManufacturer.getPointer().getString(0, true);
				} else {
					deviceInfo[i].mfr = NOT_KNOWN; //$NON-NLS-1$
				}
				int numConnections = deviceInfo[i].numberOfConnections = devices[i].dwNumberOfItems;

				CONAPI_CONNECTION_INFO[] conn = null;
				if (numConnections > 0)
					conn = (CONAPI_CONNECTION_INFO[])devices[i].pItems.toArray(numConnections);

				for (int j = 0; j < numConnections; j++) {

					DeviceConnectionInfo connInfo = new DeviceConnectionInfo();
					connInfo.deviceID = conn[j].dwDeviceID;
					switch(conn[j].dwMedia){
					case PCCSTypeDefinitions.API_MEDIA_BLUETOOTH:
						connInfo.media = "bluetooth"; //$NON-NLS-1$
						break;
					case PCCSTypeDefinitions.API_MEDIA_IRDA:
						connInfo.media = "irda"; //$NON-NLS-1$
						break;
					case PCCSTypeDefinitions.API_MEDIA_SERIAL:
						connInfo.media = "serial-ca42"; //$NON-NLS-1$
						break;
					default:
					case PCCSTypeDefinitions.API_MEDIA_USB:
						connInfo.media = "usb"; //$NON-NLS-1$
						break;
					}
					// fill-in friendly name with device name if the former was null
					if (conn[j].pstrDeviceName != null) {
						connInfo.deviceName = conn[j].pstrDeviceName.getPointer().getString(0,true);
						if (deviceInfo[i].friendlyName.equals(NOT_KNOWN)) { //$NON-NLS-1$
							deviceInfo[i].friendlyName = connInfo.deviceName;
						}
					} else {
						connInfo.deviceName = NOT_KNOWN; //$NON-NLS-1$
					}
					
					// if the address is not known, this is serious - we may weed out this connection later
					if (conn[j].pstrAddress != null) {
						connInfo.address = conn[j].pstrAddress.getPointer().getString(0, true);
					} else {
						connInfo.address = NOT_KNOWN; // $NON-NLS-1$
					}
					connInfo.state = conn[j].dwState;
					deviceInfo[i].connections.add(connInfo);
				}
			}
			dwResult = library.CONAFreeDeviceStructure(deviceCount, pDevices);
		}
		
		return deviceInfo;
	}

	public synchronized Collection<DeviceConnection> getGoodConnectionList() throws CoreException {
		DeviceInfo[] deviceList = getCompleteDeviceList();
		Collection<DeviceConnection> goodConnections = new ArrayList<DeviceConnection>();

		if (deviceList == null) 
			return goodConnections;
		
		try {
			loadUPAPI();
		} catch (CoreException e) {
			Activator.logError(e);
			return goodConnections;
		}
		boolean upapiOpen = true;
		Collection<DeviceUSBPersonalityInfo> personalityList = getAllDeviceUSBPersonalities();
		// sometimes on a phone connected to USB, this is failing at least a couple of times
		//   so retry a number of times
		// if there are no USB connections, this failure is expected
		if (personalityList == null || personalityList.size() == 0) {
			if (DEBUG) System.out.printf("Error 1 getting USB personalities\n"); //$NON-NLS-1$
			closeUPAPI();
			upapiOpen = false;
			for (int i = 2; i < 6; i++) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				try {
					loadUPAPI();
					upapiOpen = true;
				} catch (CoreException e) {
					Activator.logError(e);
				}
				if (upapiOpen)
					personalityList = getAllDeviceUSBPersonalities();
				if (personalityList == null || personalityList.size() == 0) {
					if (DEBUG) System.out.printf("Error %d getting USB personalities\n", i); //$NON-NLS-1$
					closeUPAPI();
					upapiOpen = false;
				} else {
					break;
				}
			}
		}
		// if we failed getting the USB personalities above - UPAPI will be closed
		//  so reopen it
		// else we finally got the USB personalities, UPAPI is still open
		if (!upapiOpen) {
			loadUPAPI();
			upapiOpen = true;
		}
		
		// go through each connected device and check for good connection modes (e.g. USB in debuggable mode)
		if (DEBUG) System.out.printf("getGoodConnectionList: sizeof deviceList: %d\n", deviceList.length); //$NON-NLS-1$
		for (DeviceInfo device : deviceList) {
			Collection<DeviceConnectionInfo> connectionList = device.connections;
			if (DEBUG) System.out.printf("getGoodConnectionList: sizeof connectionList: %d\n", connectionList.size()); //$NON-NLS-1$
			for (DeviceConnectionInfo connInfo : connectionList) {
				if (DEBUG) {
					System.out.printf("getGoodConnectionList: name: %s media: %s\n", device.friendlyName, connInfo.media); //$NON-NLS-1$
				}
				if (connInfo.media.equals("usb")) { //$NON-NLS-1$
					DeviceUSBPersonalityInfo personality = findPersonality(device.serialNumber, connInfo.address, personalityList);
					if (personality == null) {
						if (DEBUG) System.out.println("getGoodConnectionList: personality not found, continue"); //$NON-NLS-1$
						String msg = MessageFormat.format(Messages.PCCSConnection_Personality_Switch_Error,
								device.friendlyName);
						Activator.logMessage(msg, IStatus.ERROR);
						continue;
					}
					if (isGoodUSBPersonality(device, connInfo, personality)) {
						// if current mode was bad, and we switched, then we'll pick it up at next notification
						// if current mode is still bad, don't put into good list
						// if current mode is good, store it to good list
						DeviceConnection newConnection = new DeviceConnection();
						newConnection.friendlyName = device.friendlyName;
						if (!device.serialNumber.equals(NOT_KNOWN)) 
							newConnection.serialNumber = device.serialNumber;
						else if (!personality.serialNumber.equals(NOT_KNOWN))
							newConnection.serialNumber = personality.serialNumber;
						else
							newConnection.serialNumber = NOT_KNOWN;
						
						newConnection.media = connInfo.media;
						newConnection.port = connInfo.port; // port may be IP address or COM Port depending on personality
						newConnection.mode = connInfo.mode;
						newConnection.address = connInfo.address;
						if (!newConnection.friendlyName.equals(NOT_KNOWN) && !newConnection.address.equals(NOT_KNOWN))
							goodConnections.add(newConnection);
					}
				} else if (connInfo.media.equals("bluetooth")) { //$NON-NLS-1$
					// TODO: Bluetooth - not supported yet
				} else {
					// TODO: what else?
				}
			}
		}
		if (upapiOpen)
			closeUPAPI();
		
		return goodConnections;
	}
	/**
	 * Find a matching device in the personality list (all USB devices).<p>
	 * Might have to use a combination of the serial number and device ID to match with ID's from personality.
	 *
	 * @param serialNumber - serial number from connectivity API
	 * @param address - this contains the device ID from the connectivity API
	 * @param personalityList - all USB-connected devices
	 * @return - null if no personality found
	 */
	private DeviceUSBPersonalityInfo findPersonality(String serialNumber, String address, Collection<DeviceUSBPersonalityInfo> personalityList) {
	
		if (DEBUG) System.out.println("findPersonality: start"); //$NON-NLS-1$
		if (personalityList == null || personalityList.isEmpty())
			return null;
		
		for (DeviceUSBPersonalityInfo personality : personalityList) {
			if (DEBUG) {
				System.out.printf("findPersonality: serialNums: %s\t%s\n", serialNumber, personality.serialNumber); //$NON-NLS-1$
				System.out.printf("findPersonality: address: %s\tdeviceID: %s\n", address, personality.deviceID); //$NON-NLS-1$
			}
			if (!serialNumber.equals(NOT_KNOWN) && !personality.serialNumber.equals(NOT_KNOWN)) {
				// serial number not null from both DMAPI and UPAPI
				if (serialNumber.equals(personality.serialNumber)) {
					if (DEBUG) System.out.println("findPersonality: serialNums match"); //$NON-NLS-1$
					return personality;
				} else if (new String(serialNumber+"0").equals(personality.serialNumber)) { //$NON-NLS-1$
					if (DEBUG) System.out.println("findPersonality: serialNums match (by appending '0' to DMAPI)"); //$NON-NLS-1$
					return personality;
				}
			}
			// cannot use serial numbers! try using device IDs
			if (!address.equals(NOT_KNOWN) && !personality.deviceID.equals(NOT_KNOWN)) {
				// compare Device IDs
				String id = address.substring(address.indexOf('\\'), address.lastIndexOf('\\'));
				if (personality.deviceID.contains(id)) {
					if (DEBUG) System.out.println("findPersonality: address matches deviceID"); //$NON-NLS-1$
					return personality;
				}
			}
		}
		if (DEBUG) System.out.println("findPersonality end return null"); //$NON-NLS-1$
		return null;
	}

	/**
	 * This will do a switch if the user wants us to.
	 * 
	 * @param device - USB-connected device
	 * @param connInfo - connection information for this USB-connected device
	 * @param personalities - USB personalities of all devices (not just this one)
	 * @return true - iff we're in a 'good' USB personality already 
	 * 				if we have to switch, the device will disconnect and reconnect in the other personality
	 * 
	 * Only 'good' personalities are:
	 * PC Suite - Serial
	 * OVI Suite - Serial
	 * RNDIS - TCP/IP - currently unsupported
	 */
	private boolean isGoodUSBPersonality(DeviceInfo device,
			DeviceConnectionInfo connInfo,
			DeviceUSBPersonalityInfo personality) {

		if (DEBUG) System.out.printf("isGoodUSBPersonality: current personality: %d %s\n", personality.currentPersonalityCode, personality.currentPersonalityDescription); //$NON-NLS-1$
		// currently assume that mode is always 'serial'
		if (personality.currentPersonalityCode == UPAPIDefinitions.UPAPI_PERSONALITY_CODE_PC_SUITE ||
				personality.currentPersonalityCode == UPAPIDefinitions.UPAPI_PERSONALITY_CODE_OVI_SUITE) {
			connInfo.mode = "serial"; //$NON-NLS-1$
			connInfo.port = getUSBComPort(connInfo.address);
			// good personality
			return true;
		}

		// bad personality - switch? if dontAskAgain is true - return bad personality
		int goodCode = UPAPIDefinitions.UPAPI_PERSONALITY_CODE_PC_SUITE;
		if (personality.supportedPersonalities.containsKey(new Integer(UPAPIDefinitions.UPAPI_PERSONALITY_CODE_OVI_SUITE))) {
			goodCode = UPAPIDefinitions.UPAPI_PERSONALITY_CODE_OVI_SUITE;
		}
		String goodDesc = personality.supportedPersonalities.get(new Integer(goodCode));

		if (dontAskAgain()) {
			if (DEBUG) System.out.println("isGoodUSBPersonality: dont ask = true, return false"); //$NON-NLS-1$
			String fmt = Messages.PCCSConnection_Bad_Personality_DontSwitch_Warning1 +
			Messages.PCCSConnection_Bad_Personality_DontSwitch_Warning2;
			String message = MessageFormat.format(fmt, device.friendlyName, personality.currentPersonalityDescription, goodDesc);
			Activator.logMessage(message, IStatus.WARNING);
			return false;
		}

		// ask to switch
		askToSwitchPersonality(device, personality, goodCode, goodDesc);

		// was bad personality - if we switched, it will be good next notification
		if (DEBUG) System.out.println("isGoodUSBPersonality: return false"); //$NON-NLS-1$
		return false;
	}
	private void askToSwitchPersonality(DeviceInfo device,
			DeviceUSBPersonalityInfo personality, int goodCode, String goodDesc) {

		// ask the user and switch
		String fmt = Messages.PCCSConnection_Switch_Message1 +
		Messages.PCCSConnection_Swtich_Message2 +
		Messages.PCCSConnection_Switch_Message3 +
		Messages.PCCSConnection_Switch_Message4;
		String message = MessageFormat.format(fmt,
				device.friendlyName,
				personality.currentPersonalityDescription,
				goodDesc);

		MessageDialogWithToggle dlg = MessageDialogWithToggle.openYesNoQuestion(
				WorkbenchUtils.getSafeShell(), 
				Messages.PCCSConnection_Switch_Message_Title, 
				message, 
				Messages.PCCSConnection_DontAsk_CheckText, 
				false, 
				null, 
				null);
		boolean dontAskAgain = dlg.getToggleState();
		boolean doSwitch = (dlg.getReturnCode() == IDialogConstants.YES_ID);
		
		if (doSwitch) {
			WString pstrDeviceId = new WString(personality.deviceID);
			int dwResult = library.UPAPI_SetPersonality(upHandle, pstrDeviceId, goodCode);
			if (dwResult != PCCSErrors.CONA_OK) {
				if (DEBUG)
					System.out.printf("UPAPI_SetPersonality failed: %x\n", dwResult); //$NON-NLS-1$
			}
		}
		// store don't ask again only if selected
		if (dontAskAgain)
			storeDontAskAgain();
	}

	/**
	 * This function assumes the UPAPI has already been loaded by the caller
	 * @return - list of personalities
	 */
	private Collection<DeviceUSBPersonalityInfo> getAllDeviceUSBPersonalities() {
		Collection<DeviceUSBPersonalityInfo> p = new ArrayList<DeviceUSBPersonalityInfo>();
		boolean apiError = false;
		// how many USB devices are connected
		IntBuffer pdwDeviceCount = IntBuffer.allocate(1);
		int dwResult = library.UPAPI_QueryDeviceCount(upHandle, pdwDeviceCount);
		if (dwResult == PCCSErrors.CONA_OK) {
			int dwDeviceCount = pdwDeviceCount.get(0);
			if (DEBUG) System.out.printf("UPAPI_QueryDeviceCount: dwDeviceCount: %d\n", dwDeviceCount); //$NON-NLS-1$
			if (dwDeviceCount > 0) {
				UP_DEVICE_DESCRIPTOR[] pDeviceDescriptor = (UP_DEVICE_DESCRIPTOR[])new UP_DEVICE_DESCRIPTOR().toArray(dwDeviceCount);
				
				// get the descriptor for all USB device
				dwResult = library.UPAPI_QueryDevices(upHandle, pdwDeviceCount, pDeviceDescriptor);
				if (dwResult == PCCSErrors.CONA_OK) {
					if (DEBUG) System.out.printf("UPAPI_QueryDevices dwDeviceCount: %d\n", dwDeviceCount); //$NON-NLS-1$
					
					UP_DEVICE_DESCRIPTOR[] devices = pDeviceDescriptor;
					// 
			        for (int i = 0; i < dwDeviceCount; i++) {
						// save important device descriptor information for each device
			        	DeviceUSBPersonalityInfo deviceInfo = new DeviceUSBPersonalityInfo();
						
						// device ID is very important to get personalities and for matching with
						//  the connectivity API
						if (devices[i].pstrDeviceID != null) {
							deviceInfo.deviceID = devices[i].pstrDeviceID.getPointer().getString(0, true);
						} else {
							deviceInfo.deviceID = NOT_KNOWN;
						}
						// nice to have, but maybe null on some devices
						if (devices[i].pstrSerialNumber != null) {
							deviceInfo.serialNumber = devices[i].pstrSerialNumber.getPointer().getString(0, true);
						} else {
							deviceInfo.serialNumber = NOT_KNOWN;
						}
						// now get the personality descriptor for this device
						apiError = getPersonalityDescriptors(p, apiError, deviceInfo);
					}
			        if (DEBUG) System.out.println("getAllDeviceUSBPersonalities all devices read"); //$NON-NLS-1$
				} else {
					apiError = true;
					if (DEBUG)
						System.out.printf("UPAPI_QueryDevices dwResult = %x\n", dwResult); //$NON-NLS-1$
				}
				dwResult = library.UPAPI_FreeDeviceDescriptor(dwDeviceCount, pDeviceDescriptor);
			} else {
		        if (DEBUG) System.out.println("getAllDeviceUSBPersonalities no devices"); //$NON-NLS-1$
			}
		} else {
			apiError = true;
			if (DEBUG)
				System.out.printf("UPAPI_QueryDeviceCount dwResult = %x\n", dwResult); //$NON-NLS-1$
		}
		if (DEBUG) System.out.printf("getAllDeviceUSBPersonalities return p size : %s\n", p.size()); //$NON-NLS-1$
		return p;
	}
	private boolean getPersonalityDescriptors(
			Collection<DeviceUSBPersonalityInfo> p, boolean apiError,
			DeviceUSBPersonalityInfo deviceInfo) {
		int dwResult;

		// make device ID a wide string so JNA marshals it appropriately
		WString pid = new WString(deviceInfo.deviceID);
		UP_PERSONALITY_DESCRIPTORS[] persDesc = (UP_PERSONALITY_DESCRIPTORS[])new UP_PERSONALITY_DESCRIPTORS().toArray(1);
		persDesc[0].dwSize = 12; // important - won't work without this
		dwResult = library.UPAPI_GetPersonalityDescriptors(upHandle, pid, persDesc);
		if (dwResult == PCCSErrors.CONA_OK) {
			// store personality information
			deviceInfo.currentPersonalityCode = (persDesc[0].bCurrentPersonality & 0xff);

			// get num of supported personalities
			int numPers = (persDesc[0].bNumOfPersonalities & 0xff);
			if (DEBUG) System.out.printf("UPAPI_GetPersonalityDescriptors numPers = %d\n", numPers); //$NON-NLS-1$

			// get all the supported personalities for this device
			apiError = getSupportedPersonalities(apiError, deviceInfo, pid, persDesc, numPers);
		} else {
			apiError = true;
			if (DEBUG)
				System.out.printf("UPAPI_GetPersonalityDescriptors dwResult = %x\n", dwResult); //$NON-NLS-1$
		}
		dwResult = library.UPAPI_FreePersonalityDescriptors(persDesc);
		if (apiError == false) {
			if (DEBUG) System.out.println("getAllDeviceUSBPersonalities deviceInfo added"); //$NON-NLS-1$
			p.add(deviceInfo);
		}
		return apiError;
	}
	private boolean getSupportedPersonalities(boolean apiError,
			DeviceUSBPersonalityInfo deviceInfo, WString pid,
			UP_PERSONALITY_DESCRIPTORS[] persDesc, int numPers) {
		int dwResult;
		UP_PERSONALITY[] pSupportedPersonality = null;
		if (numPers > 0) {
			pSupportedPersonality = (UP_PERSONALITY[])persDesc[0].pPersonalities.toArray(numPers);
			deviceInfo.supportedPersonalities = new HashMap<Integer, String>();
		}
		// now get the string descriptor for each supported personality
		for (int j = 0; j < numPers; j++) {
			Integer code = new Integer(pSupportedPersonality[j].bPersonalityCode);
			int stringIndex = (pSupportedPersonality[j].bPersonalityIndex & 0xff);
			UP_STRING_DESCRIPTOR pStringDescriptor = new UP_STRING_DESCRIPTOR();
			String desc = NOT_KNOWN;
			pStringDescriptor.dwSize = 12; // this is important
			dwResult = library.UPAPI_GetStringDescriptor(upHandle, pid, stringIndex, 0, pStringDescriptor);
			if (dwResult == PCCSErrors.CONA_OK) {
				if (pStringDescriptor.pstrDescription != null) {
					desc = pStringDescriptor.pstrDescription.getPointer().getString(0, true);
				}
				if (DEBUG) System.out.printf("UPAPI_GetStringDescriptor code: %d, desc: %s\n", code.intValue(), desc); //$NON-NLS-1$
				dwResult = library.UPAPI_FreeStringDescriptor(pStringDescriptor);
			} else {
				apiError = true;
				if (DEBUG)
					System.out.printf("UPAPI_GetStringDescriptor dwResult = %x\n", dwResult); //$NON-NLS-1$
			}
			if (apiError == false)
				deviceInfo.supportedPersonalities.put(code, desc);
		}
		if (deviceInfo.supportedPersonalities.isEmpty()) {
			deviceInfo.currentPersonalityDescription = MessageFormat.format(Messages.PCCSConnection_Generic_Personality_Description, Integer.toString(deviceInfo.currentPersonalityCode));
		} else {
			deviceInfo.currentPersonalityDescription = deviceInfo.supportedPersonalities.get(new Integer(deviceInfo.currentPersonalityCode));
		}
		if (DEBUG) System.out.printf("current Desc found: %s\n", deviceInfo.currentPersonalityDescription); //$NON-NLS-1$
		return apiError;
	}

	/**
	 * @return null if no device found.
	 * @throws CoreException
	 * @deprecated Use {@link #getGoodConnectionList()} instead.
	 */
	public DeviceInfo[] getDeviceList() throws CoreException {
		
		DeviceInfo[] deviceInfo = null;
		
		int dwResult = PCCSErrors.CONA_OK;
		IntByReference pdwCount = new IntByReference(0);
		dwResult = library.CONAGetDeviceCount(dmHandle, pdwCount);
		if (DEBUG) System.out.printf("CONAGetDeviceCount: %x number of devices: %d\n", dwResult, pdwCount.getValue()); //$NON-NLS-1$

		int deviceCnt = pdwCount.getValue();
		if (dwResult != PCCSErrors.CONA_OK || deviceCnt == 0)
			return deviceInfo;
		
		// array of structs in contiguous memory required !
		CONAPI_DEVICE[] pDevices = (CONAPI_DEVICE[])new CONAPI_DEVICE().toArray(deviceCnt);
		dwResult = library.CONAGetDevices(dmHandle, pdwCount, pDevices);
		if (DEBUG) System.out.printf("CONAGetDevices: %x number of devices: %d\n", dwResult, deviceCnt); //$NON-NLS-1$
		
		if (dwResult == PCCSErrors.CONA_OK) {
			// extract to regular java types
			if (deviceCnt > 0) {
				deviceInfo = new DeviceInfo[deviceCnt];
				CONAPI_DEVICE[] devices = pDevices;
				for (int i = 0; i < deviceCnt; i++) {
					deviceInfo[i] = new DeviceInfo();
					if (devices[i].pstrSerialNumber != null) {
						deviceInfo[i].serialNumber = devices[i].pstrSerialNumber.getPointer().getString(0, true);
					} else {
						// TODO: docs say if ser num == null --> device unsupported, but this is a bug in PCCS
						deviceInfo[i].serialNumber = NOT_KNOWN; //$NON-NLS-1$
					}
					if (devices[i].pstrFriendlyName != null) {
						deviceInfo[i].friendlyName = devices[i].pstrFriendlyName.getPointer().getString(0, true);
					} else {
						deviceInfo[i].friendlyName = NOT_KNOWN; //$NON-NLS-1$
					}
					if (devices[i].pstrModel != null) {
						deviceInfo[i].model = devices[i].pstrModel.getPointer().getString(0, true);
					} else {
						deviceInfo[i].model = NOT_KNOWN; //$NON-NLS-1$
					}
					if (devices[i].pstrManufacturer != null) {
						deviceInfo[i].mfr = devices[i].pstrManufacturer.getPointer().getString(0, true);
					} else {
						deviceInfo[i].mfr = NOT_KNOWN; //$NON-NLS-1$
					}
					int numItems = deviceInfo[i].numberOfConnections = devices[i].dwNumberOfItems;
					
					CONAPI_CONNECTION_INFO[] conn = null;
					if (numItems > 0)
						 conn = (CONAPI_CONNECTION_INFO[])devices[i].pItems.toArray(numItems);
					for (int j = 0; j < numItems; j++) {
						DeviceConnectionInfo connInfo = new DeviceConnectionInfo();
						connInfo.deviceID = conn[j].dwDeviceID;
						switch(conn[j].dwMedia){
						case PCCSTypeDefinitions.API_MEDIA_BLUETOOTH:
							connInfo.media = "bluetooth"; //$NON-NLS-1$
							break;
						case PCCSTypeDefinitions.API_MEDIA_IRDA:
							connInfo.media = "irda"; //$NON-NLS-1$
							break;
						case PCCSTypeDefinitions.API_MEDIA_SERIAL:
							connInfo.media = "serial-ca42"; //$NON-NLS-1$
							break;
						default:
						case PCCSTypeDefinitions.API_MEDIA_USB:
							connInfo.media = "usb"; //$NON-NLS-1$
							break;
						}
						if (conn[j].pstrDeviceName != null) {
							connInfo.deviceName = conn[j].pstrDeviceName.getPointer().getString(0,true);
							if (deviceInfo[i].friendlyName.equals(NOT_KNOWN)) { //$NON-NLS-1$
								// use device name as friendly name (latter was null)
								deviceInfo[i].friendlyName = connInfo.deviceName;
							}
						} else {
							connInfo.deviceName = NOT_KNOWN; //$NON-NLS-1$
						}
						// Bomb if friendly name is not known or address is not known
						//   since these are essential to a connection
						if (conn[j].pstrAddress == null || deviceInfo[i].friendlyName.equals(NOT_KNOWN)) //$NON-NLS-1$
							continue;
						
						connInfo.address = conn[j].pstrAddress.getPointer().getString(0, true);
						connInfo.state = conn[j].dwState;
						
						if (connInfo.media.equals("usb")) { //$NON-NLS-1$
							if (checkUSBMode(deviceInfo[i], connInfo)){ 
								connInfo.port = getUSBComPort(connInfo.address);
							} else {
								// don't store - not in good USB personality mode
								continue;
							}
						} else if (connInfo.media.equals("bluetooth")) { //$NON-NLS-1$
							// TODO: implement BT
							connInfo.port = getBTComPort(connInfo.address);
						} else {
							// IRDA and Serial(CA42) probably will not come here anyway
							connInfo.port = null;
						}
						if (connInfo.port != null)
							deviceInfo[i].connections.add(connInfo);
					}
				}
			}
			dwResult = library.CONAFreeDeviceStructure(deviceCnt, pDevices);
			if (DEBUG) System.out.printf("CONAFreeDeviceStructure: %x\n", dwResult); //$NON-NLS-1$
		}
		return deviceInfo;
	}

	/**
	 * 
	 * @param deviceInfo
	 * @param connInfo
	 * @return
	 * 
	 * @deprecated not used anymore
	 */
	private boolean checkUSBMode(DeviceInfo deviceInfo,
			DeviceConnectionInfo connInfo) {
		return true;
	}

	private String getBTComPort(String address) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUSBComPort(String address) {
		if (address != null && address.length() > 0) {
			String id, key, subKey, parentIdPrefix = null, portName;

			WindowsRegistry wr = WindowsRegistry.getRegistry();
			key = "SYSTEM\\ControlSet001\\Enum\\USB" + address.substring(address.indexOf('\\'), address.lastIndexOf('\\')); //$NON-NLS-1$
			id = address.substring(address.lastIndexOf('\\') + 1);
			int i = 0;
			do {
				subKey = wr.getLocalMachineKeyName(key, i);
				if (subKey != null && subKey.equalsIgnoreCase(id)) {
					key += "\\" + subKey; //$NON-NLS-1$
					parentIdPrefix = wr.getLocalMachineValue(key, "ParentIdPrefix"); //$NON-NLS-1$
					break;
				}
				i++;
			} while (subKey != null);
			if (parentIdPrefix != null) {
				key = "SYSTEM\\ControlSet001\\Enum\\NMWCD"; //$NON-NLS-1$
				subKey = address.substring(address.indexOf('\\'), address.lastIndexOf('\\'));
				key += subKey + "&IF_JAVACOMM"; //$NON-NLS-1$
				i = 0;
				do {
					subKey = wr.getLocalMachineKeyName(key, i);
					if (subKey != null && subKey.contains(parentIdPrefix)) {
						key += "\\" + subKey + "\\Device Parameters"; //$NON-NLS-1$ //$NON-NLS-2$
						portName = wr.getLocalMachineValue(key, "PortName"); //$NON-NLS-1$
						if (portName != null) {
							portName = portName.substring(portName.indexOf('M') + 1);
							return portName;
						}
						break;
					}
					i++;
				} while (subKey != null);
			}
		}
		return null;
	}

	public void addEventListenter(DeviceEventListener listener) {
		listeners.add(listener);
	}

	public void removeEventListener(DeviceEventListener listener) {
		listeners.remove(listener);
	}

	public interface DeviceEventListener {
		public enum DeviceEvent {
			DEVICE_LIST_UPDATED,		// not any specific information 
			DEVICE_ADDED, 
			DEVICE_REMOVED, 
			DEVICE_UPDATED_ADDEDCONNECTION,
			DEVICE_UPDATED_REMOVEDCONNECTION,
			DEVICE_UPDATED_RENAMED, 
			DEVICE_UPDATED_UNKNOWN,
		}
		public void onDeviceEvent(DeviceEvent eventType, String serialNumber);
	}
	/**
	 *  Class to hold USB personality information from UPAPI per Device 
	 */
	private class DeviceUSBPersonalityInfo {
		String deviceID;		// from device descriptor
		String serialNumber;	// from device descriptor
		int currentPersonalityCode; // from personality descriptor
		String currentPersonalityDescription; // from list of supported personalities
		// list of supported personalities
		//  int = personality code
		//  string = personality description for code from device
		Map<Integer, String> supportedPersonalities;
	}
}
