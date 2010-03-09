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

import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.discovery.pccs.Activator;
import com.nokia.carbide.remoteconnections.discovery.pccs.Messages;
import com.nokia.carbide.remoteconnections.discovery.pccs.pccsnative.IConnAPILibrary.IConnAPIDeviceCallback;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.ShortByReference;

public class PCCSConnection {

	private static final String NOT_KNOWN = "not known"; //$NON-NLS-1$ // used for all string structure elements that come back from PCCS as null
	private boolean DEBUG = false;
	
	public class DeviceNotificationCallback implements IConnAPIDeviceCallback {

		/* (non-Javadoc)
		 * @see com.nokia.carbide.remoteconnections.discovery.pccs.pccsnative.IConnAPILibrary.IConnAPIDeviceCallback#invoke(int, com.sun.jna.ptr.ShortByReference)
		 */
		public int invoke(final int dwStatus, final ShortByReference pstrSerialNumber) {
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
			if (DEBUG) System.out.println("DeviceNotificationCallback: fire events");
			Iterator<DeviceEventListener> iter = listeners.iterator();
			while (iter.hasNext()) {
				iter.next().onDeviceEvent(eventType, serialNumber);
			}
			return PCCSErrors.CONA_OK;
		}
	}

	private static final Collection<DeviceEventListener> listeners = new LinkedList<DeviceEventListener>();
	private IConnAPILibrary library;
	private DeviceNotificationCallback pfnCallback = new DeviceNotificationCallback();
	public static final int PCCS_NOT_FOUND = 1;
	public static final int PCCS_WRONG_VERSION = 2;
	private static final int DMAPI_VERSION = DMAPIDefinitions.DMAPI_VERSION_34;
	
	private APIHANDLE dmHandle = APIHANDLE.INVALID_HANDLE_VALUE;
	private APIHANDLE mcHandle = APIHANDLE.INVALID_HANDLE_VALUE;
	private APIHANDLE upHandle = APIHANDLE.INVALID_HANDLE_VALUE;
	
	private Collection<String> noSwitchConnections = new ArrayList<String>();
	
	public PCCSConnection() {
	}

	public void open() throws CoreException {
		if (library == null) {
			try {
				library = IConnAPILibrary.INSTANCE;
			} catch (UnsatisfiedLinkError e) {
				String msg;
				if (Activator.isSymSEELayout()) {
					msg = Messages.ConnAPILibrary_PCCS_Not_Found_Error + Activator.getLoadErrorURL();
				} else {
					msg = Messages.ConnAPILibrary_PCSuite_Not_Found_Error + Activator.getLoadErrorURL();
				}
				throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, PCCSConnection.PCCS_NOT_FOUND, msg, e));
			} catch (NoClassDefFoundError e) {
				String msg;
				if (Activator.isSymSEELayout()) {
					msg = Messages.ConnAPILibrary_PCCS_Not_Found_Error + Activator.getLoadErrorURL();
				} else {
					msg = Messages.ConnAPILibrary_PCSuite_Not_Found_Error + Activator.getLoadErrorURL();
				}
				throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, PCCSConnection.PCCS_NOT_FOUND, msg, e));
			}
		}
		// this uses the MCAPI only to enable required media
		ensureMediaEnabled();
		
		// load DMAPI
		initDMAPI();
		dmHandle = loadDMAPI();
		if (!APIHANDLE.INVALID_HANDLE_VALUE.equals(dmHandle)) {
			startDMNotifications(dmHandle);
		}
	}
	
	private void initDMAPI() throws CoreException {
		int dwResult = library.DMAPI_Initialize(DMAPI_VERSION, null);
    	if (dwResult != PCCSErrors.CONA_OK) {
    		terminateDMAPI();
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
		if (DEBUG) System.out.println("initDMAPI"); //$NON-NLS-1$
	}
	private void startDMNotifications(APIHANDLE handle) throws CoreException {
    	// register a call back
    	int dwResult = library.CONARegisterNotifyCallback(handle, PCCSTypeDefinitions.API_REGISTER, pfnCallback);
    	if (dwResult != PCCSErrors.CONA_OK) {
    		library.CONACloseDM(handle);
        	handle = APIHANDLE.INVALID_HANDLE_VALUE;

        	String msg = String.format(Messages.PCCSConnection_PCCS_CONARegisterNotifyCallback_Error, dwResult);
    		if (dwResult == PCCSErrors.ECONA_INVALID_POINTER) {
    			msg = Messages.PCCSConnection_PCCS_CONARegisterNotifyCallback_Pointer_Error;
    		}
    		throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, dwResult, msg, null));
    	}
		if (DEBUG) System.out.println("startDMNotifications"); //$NON-NLS-1$
	
	}
	private void ensureMediaEnabled() throws CoreException {
		// open the MCAPI
		loadMCAPI();
		// get media list
		IntBuffer pdwCount = IntBuffer.allocate(1);
		CONAPI_MEDIA.ByReference ppMedia = new CONAPI_MEDIA.ByReference();

		int dwResult = library.CONAMMGetMedia(mcHandle, pdwCount, ppMedia);
		if (DEBUG)
			System.out.printf("CONAMMGetMedia dwResult = %x\tpdwCount = %d\n", dwResult, pdwCount.get(0)); //$NON-NLS-1$

		int count = pdwCount.get(0);
		
		Pointer pMedia = ppMedia.getPointer();
		Pointer nativePointer = pMedia.getPointer(0); // save this for freeing media
		int size = ppMedia.size();
		
		if (count > 0) {
			CONAPI_MEDIA media[] = new CONAPI_MEDIA[count];
			for (int i = 0; i < count; i++) {
				// get pointer from offset of native pointer
				Pointer sharedP = nativePointer.share(0 + i*size);
				media[i] = new CONAPI_MEDIA(sharedP);
				// and read the data from native memory
				media[i].read();
			}
			for (int i = 0; i < count; i++) {
				if (MCAPIDefinitions.CONAPI_GET_MEDIA_TYPE(media[i].dwMedia) == MCAPIDefinitions.CONAPI_MEDIA_USB) {
					if (MCAPIDefinitions.CONAPI_IS_MEDIA_UNACTIVE(media[i].dwState)) {
						media[i].dwState = MCAPIDefinitions.CONAPI_MEDIA_ACTIVE;
						dwResult = library.CONAMMSetMedia(mcHandle, media);
						if (DEBUG)
							System.out.printf("CONAMMSetMedia dwResult = %x\n", dwResult); //$NON-NLS-1$
					}
				}
			}
			dwResult = library.CONAMMFreeMediaStructures(count, nativePointer);
			if (DEBUG)
				System.out.printf("CONAMMFreeMediaStructures dwResult = %x\n", dwResult); //$NON-NLS-1$
		}

		// close MCAPI
		closeMCAPI();
	}

	/**
	 * Opens Device Management API (DMAPI) for use. Note: initDMAPI must be called prior to this.<br>
	 * initDMAPI need be called only once, but this can be called whenever we need a handle to
	 * the DMAPI.
	 * 
	 * @throws CoreException
	 */
	private APIHANDLE loadDMAPI() throws CoreException {

    	// open a DM handle
    	APIHANDLE handle = APIHANDLE.INVALID_HANDLE_VALUE;
    	LPAPIHANDLE pHandle = new LPAPIHANDLE();
    	int dwResult = library.CONAOpenDM(pHandle);
    	
    	if (dwResult != PCCSErrors.CONA_OK) {
    		String msg = String.format(Messages.PCCSConnection_PCCS_CONAOpenDM_Error, dwResult);
    		if (dwResult == PCCSErrors.ECONA_NOT_ENOUGH_MEMORY) {
    			msg = Messages.PCCSConnection_PCCS_Not_Enough_Memory_Error;
    		}
    		throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, dwResult, msg, null));
    	} else {
    		handle = pHandle.getValue();
    	}
    	
		if (DEBUG) System.out.printf("loadDMAPI handle=%s\n", handle.toString()); //$NON-NLS-1$
    	return handle;
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
		if (DEBUG) System.out.println("\n-----loadMCAPI"); //$NON-NLS-1$
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

		stopDMNotifications(dmHandle);
		
		// DMAPI should only one be open
		dmHandle = closeDMAPI(dmHandle);
		
		terminateDMAPI();
	}
	
	private void terminateDMAPI() {
    	// Terminate Device management API
    	/*int dwResult =*/ library.DMAPI_Terminate(null);
		if (DEBUG) System.out.println("terminateDMAPI"); //$NON-NLS-1$
	}
	private void stopDMNotifications(APIHANDLE handle) {
//		int dwResult = PCCSErrors.CONA_OK;
		if (!APIHANDLE.INVALID_HANDLE_VALUE.equals(handle)) {
			// unregister callback
			/*dwResult =*/ library.CONARegisterNotifyCallback(handle, PCCSTypeDefinitions.API_UNREGISTER, pfnCallback);
		}		
		if (DEBUG) System.out.println("stopDMNotifications"); //$NON-NLS-1$
	}
	/**
	 * Closes the DMAPI.
	 * @throws CoreException
	 */
	private APIHANDLE closeDMAPI(APIHANDLE handle) throws CoreException {
		int dwResult = PCCSErrors.CONA_OK;
		if (!APIHANDLE.INVALID_HANDLE_VALUE.equals(handle)) {
			// close DM connection
			dwResult = library.CONACloseDM(handle);
			if (DEBUG) System.out.printf("closeDMAPI ret=%x handle=%s\n", dwResult, handle.toString()); //$NON-NLS-1$
			handle = APIHANDLE.INVALID_HANDLE_VALUE;
		}
		return handle;
	}

	private void closeMCAPI() {
//		int dwResult = PCCSErrors.CONA_OK;
		if (!APIHANDLE.INVALID_HANDLE_VALUE.equals(mcHandle)) {
			// close DM connection
			/*dwResult =*/ library.CONACloseDM(mcHandle);
			mcHandle = APIHANDLE.INVALID_HANDLE_VALUE;
        	// Terminate Device management API
    		/*dwResult =*/ library.MCAPI_Terminate(null);
		}
		if (DEBUG) System.out.println("closeMCAPI----\n"); //$NON-NLS-1$
	}
	
	private void closeUPAPI() {
		int dwResult = PCCSErrors.CONA_OK;
		if (!APIHANDLE.INVALID_HANDLE_VALUE.equals(upHandle)) {
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

	/**
	 * All devices detected are gotten here - BT, USB, etc.
	 *  No checking of a valid connection is done here
	 *  
	 * @return DeviceInfo[] - null if no devices connected
	 */
	private DeviceInfo[] getCompleteDeviceList() {
		DeviceInfo[] deviceInfo = null;
		APIHANDLE handle = APIHANDLE.INVALID_HANDLE_VALUE;
		try {
			handle = loadDMAPI();
		} catch (CoreException e) {
			return deviceInfo;
		}
		IntByReference pdwCount = new IntByReference(0);
		int dwResult = library.CONAGetDeviceCount(handle, pdwCount);
		if (DEBUG) System.out.printf("CONAGetDeviceCount: %x number of devices: %d\n", dwResult, pdwCount.getValue()); //$NON-NLS-1$
		if (dwResult != PCCSErrors.CONA_OK) {
			try {
				closeDMAPI(handle);
			} catch (CoreException e) {
			}
			return deviceInfo;
		}
		
		int deviceCount = pdwCount.getValue();
		if (deviceCount > 0) {
			
			CONAPI_DEVICE[] pDevices = (CONAPI_DEVICE[])new CONAPI_DEVICE().toArray(deviceCount);
			dwResult = library.CONAGetDevices(handle, pdwCount, pDevices);
			if (DEBUG) System.out.printf("CONAGetDevices: %x number of devices: %d\n", dwResult, deviceCount); //$NON-NLS-1$
			if (dwResult != PCCSErrors.CONA_OK) {
				try {
					closeDMAPI(handle);
				} catch (CoreException e) {
				}
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
		try {
			closeDMAPI(handle);
		} catch (CoreException e) {
		}

		return deviceInfo;
	}

	/**
	 * Gets the complete list of connections and weeds out "good" connections (i.e., non-debuggable)
	 * 
	 * @return
	 * @throws CoreException
	 */
	public synchronized Collection<DeviceConnection> getGoodConnectionList() throws CoreException {
		DeviceInfo[] deviceList = getCompleteDeviceList();
		Collection<DeviceConnection> goodConnections = new ArrayList<DeviceConnection>();

		if (deviceList == null) { 
			// forget all non switched devices
			forgetAllNoSwitchConnectionsNotInCurrentList(null);
			return goodConnections;
		}
		
		try {
			loadUPAPI();
		} catch (CoreException e) {
			Activator.logError(e);
			return goodConnections;
		}
		boolean upapiOpen = true;
		int numUSBDevicesExpected = 0;
		for (DeviceInfo device : deviceList) {
			Collection<DeviceConnectionInfo> connectionList = device.connections;
			for (DeviceConnectionInfo connInfo : connectionList) {
				if (connInfo.media.equals("usb")) {
					numUSBDevicesExpected++;
				}
			}
		}
		if (DEBUG) System.out.println("numDevices: "+ deviceList.length + " numUSBDevicesExpected: " + numUSBDevicesExpected);
		if (deviceList.length < numUSBDevicesExpected) {
			deviceList = trySplittingDevices(deviceList);
			if (deviceList.length < numUSBDevicesExpected) {
				// error - number of total devices should be equal to or more than number of USB devices
				//   i.e., only 1 USB connection is permitted per device
				String message = MessageFormat.format(
						"PCSuite is reporting more USB connections ({0}) than the number of connected devices ({1}). Carbide cannot match devices to USB connections.", 
						numUSBDevicesExpected, deviceList.length);
				Activator.logMessage(message, IStatus.ERROR);
				closeUPAPI();
				return goodConnections;
			}
		}
		
		if (deviceList.length == 1) {
			// forget all non switched devices
			forgetAllNoSwitchConnectionsNotInCurrentList(null);
		}

		Collection<DeviceUSBPersonalityInfo> personalityList = null;
		if (numUSBDevicesExpected > 0) {
			int attempt = 1;
			do {
				personalityList = getAllDeviceUSBPersonalities();
				if (personalityList == null || personalityList.size() < numUSBDevicesExpected) {
					if (DEBUG) System.out.printf("Error %d getting USB personalities: %d of %d total\n", attempt, (personalityList != null) ? personalityList.size() : 0, numUSBDevicesExpected); //$NON-NLS-1$
					if (attempt > 10) {
						break; // bomb - leave UPAPI open, we need it later
					}
					attempt++;
					// UPAPI seems to need a reload
					closeUPAPI();
					upapiOpen = false;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
					loadUPAPI();
					upapiOpen = true;
				}
			} while (personalityList == null || personalityList.size() < numUSBDevicesExpected);
		}
		// if we failed getting the USB personalities above - UPAPI will be closed
		//  so reopen it
		// else we finally got the USB personalities, UPAPI is still open
		if (!upapiOpen) {
			loadUPAPI();
			upapiOpen = true;
		}
		
		forgetAllNoSwitchConnectionsNotInCurrentList(personalityList);
		
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
					DeviceUSBPersonalityInfo personality = findPersonality30(numUSBDevicesExpected, device.serialNumber, connInfo.address, personalityList);
					if (personality == null) {
						if (DEBUG) System.out.println("getGoodConnectionList: personality not found for device: " + device.friendlyName + "-- continue"); //$NON-NLS-1$
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
	private DeviceInfo[] trySplittingDevices(DeviceInfo[] deviceList) {
		// Assumption: one device can at most one USB connection
		// sometimes, PCSuite reports only one device with multiple USB connections (e.g., when serialNumber is null on more than one device)
		//  we attempt to take multiple USB connections and create separate devices
		Collection<DeviceInfo> newList = new ArrayList<DeviceInfo>();
		for (DeviceInfo device : deviceList) {
			if (device.numberOfConnections > 1) {
				DeviceConnectionInfo[] connection = (DeviceConnectionInfo[]) device.connections.toArray(new DeviceConnectionInfo[device.numberOfConnections]);
				int numUSBConnections = 0;
				for (int i = 0; i < device.numberOfConnections; i++) {
					if (connection[i].media.equals("usb"))
						numUSBConnections++;
				}
				if (numUSBConnections > 1) {
					for(int i = 0; i < device.numberOfConnections; i++) {
						if (connection[i].media.equals("usb")) {
							DeviceInfo newDevice = new DeviceInfo();
							newDevice.serialNumber = NOT_KNOWN;
							newDevice.friendlyName = connection[i].deviceName;
							newDevice.mfr = device.mfr;
							newDevice.model = NOT_KNOWN;
							newDevice.numberOfConnections = 1;
							newDevice.connections.add(connection[i]);
							newList.add(newDevice);
						}
					}
				} else {
					newList.add(device);
				}
			} else {
				newList.add(device);
			}
		}
		return newList.toArray(new DeviceInfo[newList.size()]);
//		return deviceList;
	}

	/**
	 * Forget all previous "no-switch-personality" devices that are not in current list (e.g., device is now disconnected)
	 * 
	 * @param personalityList
	 */
	private void forgetAllNoSwitchConnectionsNotInCurrentList(
			Collection<DeviceUSBPersonalityInfo> personalityList) {
		if (personalityList == null || personalityList.isEmpty()) {
			if (DEBUG) System.out.println("forgetAllNoSwitchConnectionsNotInCurrentList: all");
			noSwitchConnections.clear();
		} else if (noSwitchConnections == null || noSwitchConnections.isEmpty()) {
			if (DEBUG) System.out.println("forgetAllNoSwitchConnectionsNotInCurrentList: noSwitchConnections already empty");
				return;
		} else {
			for (String id : new ArrayList<String>(noSwitchConnections)) {
				boolean found = false;
				for (DeviceUSBPersonalityInfo personality : personalityList) {
					if (id.equals(personality.deviceID)) {
						found = true;
					}
				}
				if (!found) {
					if (DEBUG) System.out.println("forgetAllNoSwitchConnectionsNotInCurrentList: " + id);
					noSwitchConnections.remove(id);
				}
			}
		}		
	}
	/**
	 * Find a matching device in the personality list (all USB devices).<p>
	 * Might have to use a combination of the serial number and device ID to match with ID's from personality.
	 * @param numUSBPersonalities 
	 *
	 * @param serialNumber - serial number from connectivity API
	 * @param address - this contains the device ID from the connectivity API
	 * @param personalityList - all USB-connected devices
	 * @return - null if no personality found
	 */
	// 2.5 functionality
	private DeviceUSBPersonalityInfo findPersonality(int numUSBPersonalities, String serialNumber, String address, Collection<DeviceUSBPersonalityInfo> personalityList) {
		
		if (DEBUG) System.out.println("findPersonality: start"); //$NON-NLS-1$
		if (personalityList == null || personalityList.isEmpty()) {
			if (DEBUG) System.out.println("findPersonality: list is empty");
			return null;
		}

		for (DeviceUSBPersonalityInfo personality : personalityList) {
			if (DEBUG) {
				System.out.printf("findPersonality: serialNums: %s\t%s\n", serialNumber, personality.serialNumber); //$NON-NLS-1$
				System.out.printf("findPersonality: address: %s\tdeviceID: %s\n", address, personality.deviceID); //$NON-NLS-1$
			}
			// sometimes the serial numbers match except the personality one has an added 0
			if (!serialNumber.equals(NOT_KNOWN) && !personality.serialNumber.equals(NOT_KNOWN)) {
				// serial number not null from both DMAPI and UPAPI
				if (serialNumber.equals(personality.serialNumber)) {
					if (DEBUG) System.out.println("findPersonality: serialNums match"); //$NON-NLS-1$
					return personality;
				} else if (new String(serialNumber+"0").equals(personality.serialNumber)) { //$NON-NLS-1$
					if (DEBUG) System.out.println("findPersonality: serialNums match (by appending '0' to DMAPI)"); //$NON-NLS-1$
					return personality;
				} else {
					if (DEBUG) System.out.println("findPersonality: both serialNums != null && serialNums do not match");  //$NON-NLS-1$
				}
			}
			// cannot use serial numbers! try using device IDs
			if (!address.equals(NOT_KNOWN) && !personality.deviceID.equals(NOT_KNOWN)) {
				// example device ids:
				//   0\VID_0421&PID_00AB\0 (no serial number as part of id)
				//   004401011418023\VID_0421&PID_0500\0 (serial number comes at front)
				// compare Device IDs
				String id = address.substring(address.indexOf('\\'), address.lastIndexOf('\\'));
				if (personality.deviceID.contains(id) && personality.deviceID.contains(serialNumber)) {
					if (DEBUG) System.out.println("findPersonality: address matches deviceID with serial number"); //$NON-NLS-1$
					return personality;
				} else {
					String begin = personality.deviceID.substring(0, personality.deviceID.indexOf('\\'));
					if (begin.equals("0")) {
						// no serial number at beginning
						if (personality.deviceID.contains(id)) {
							if (DEBUG) System.out.println("findPersonality: address matches deviceID without serial number"); //$NON-NLS-1$
							return personality;
						}
					}
				}
			}
			// sometimes the serial number is part of the address!
			if (!address.equals(NOT_KNOWN) && !personality.serialNumber.equals(NOT_KNOWN)) {
				if (address.endsWith(personality.serialNumber)) {
					if (DEBUG) System.out.println("findPersonality: address contains serialNumber");
					return personality;
				}
			}
		}
		if (DEBUG) System.out.println("findPersonality end return null"); //$NON-NLS-1$
		return null;
	}
	// 3.0 experimental functionality
	private DeviceUSBPersonalityInfo findPersonality30(int numUSBDevicesExpected, String serialNumber, String address, Collection<DeviceUSBPersonalityInfo> personalityList) {
	
		if (DEBUG) System.out.println("\nfindPersonality: start"); //$NON-NLS-1$
		if (personalityList == null || personalityList.isEmpty()) {
			if (DEBUG) System.out.println("findPersonality: list is empty\n");
			return null;
		}

		for (DeviceUSBPersonalityInfo personality : personalityList) {
			if (DEBUG) {
				System.out.printf("findPersonality: serialNums: device:%s\t usb:%s\n", serialNumber, personality.serialNumber); //$NON-NLS-1$
				System.out.printf("findPersonality: address: %s\tdeviceID: %s\n", address, personality.deviceID); //$NON-NLS-1$
			}
			if (personality.matchedToDMDevice) {
				if (DEBUG) System.out.println("device matched already -- continue");
				continue;
			}
			// sometimes the serial numbers match except the personality one has an added 0
			if (!serialNumber.equals(NOT_KNOWN) && !personality.serialNumber.equals(NOT_KNOWN)) {
				// serial number not null from both DMAPI and UPAPI
				if (serialNumber.equals(personality.serialNumber)) {
					if (DEBUG) System.out.println("findPersonality: serialNums match\n"); //$NON-NLS-1$
					personality.matchedToDMDevice = true;
					return personality;
				} else if (new String(serialNumber+"0").equals(personality.serialNumber)) { //$NON-NLS-1$
					if (DEBUG) System.out.println("findPersonality: serialNums match (by appending '0' to DMAPI)\n"); //$NON-NLS-1$
					personality.matchedToDMDevice = true;
					return personality;
				} else {
					if (DEBUG) System.out.println("findPersonality: both serialNums != null && serialNums do not match\n");  //$NON-NLS-1$
				}
			}
			// cannot use serial numbers! try using device IDs
			if (!address.equals(NOT_KNOWN) && !personality.deviceID.equals(NOT_KNOWN)) {
				// example device ids:
				//   0\VID_0421&PID_00AB\0 (no serial number as part of id)
				//   004401011418023\VID_0421&PID_0500\0 (serial number comes at front)
				// compare Device IDs
				String vidpid = address.substring(address.indexOf('\\'), address.lastIndexOf('\\'));
				String endid = address.substring(address.lastIndexOf('\\')+1);
				if (personality.deviceID.contains(vidpid) && personality.deviceID.contains(endid)) {
					if (DEBUG) System.out.println("findPersonality: address matches deviceID with end number\n"); //$NON-NLS-1$
					personality.matchedToDMDevice = true;
					return personality;
				}
				else if (personality.deviceID.contains(vidpid) && personality.deviceID.contains(serialNumber)) {
					if (DEBUG) System.out.println("findPersonality: address matches deviceID with serial number\n"); //$NON-NLS-1$
					personality.matchedToDMDevice = true;
					return personality;
				} else {
					if (serialNumber.equals(NOT_KNOWN)) {
						if (personality.deviceID.contains(vidpid) && numUSBDevicesExpected == 1) {
							if (DEBUG) System.out.println("findPersonality: serial number not known, but VID/PID match\n"); //$NON-NLS-1$
							personality.matchedToDMDevice = true;
							return personality;
						}
					}
					String begin = personality.deviceID.substring(0, personality.deviceID.indexOf('\\'));
					if (begin.equals("0") || numUSBDevicesExpected == 1) {
						// no serial number at beginning
						if (personality.deviceID.contains(vidpid)) {
							if (DEBUG) System.out.println("findPersonality: address matches deviceID without serial number\n"); //$NON-NLS-1$
							personality.matchedToDMDevice = true;
							return personality;
						}
					}
				}
			}
			// sometimes the serial number is part of the address!
			if (!personality.serialNumber.equals(NOT_KNOWN)) {
				if (address.endsWith(personality.serialNumber)) {
					if (DEBUG) System.out.println("findPersonality: address contains serialNumber\n");
					personality.matchedToDMDevice = true;
					return personality;
				}
			}
		}
		if (DEBUG) System.out.println("findPersonality return no match\n"); //$NON-NLS-1$
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
			forgetNoSwitchConnections(personality.deviceID);
			return true;
		}

		// bad personality - switch? if dontAskAgain is true - return bad personality
		int goodCode = UPAPIDefinitions.UPAPI_PERSONALITY_CODE_PC_SUITE;
		if (personality.supportedPersonalities.containsKey(new Integer(UPAPIDefinitions.UPAPI_PERSONALITY_CODE_OVI_SUITE))) {
			goodCode = UPAPIDefinitions.UPAPI_PERSONALITY_CODE_OVI_SUITE;
		}
		String goodDesc = personality.supportedPersonalities.get(new Integer(goodCode));

		if (!noSwitchConnectionsContains(personality.deviceID))
			// ask to switch
			askToSwitchPersonality(device, personality, goodCode, goodDesc);

		// was bad personality - if we switched, it will be good next notification
		if (DEBUG) System.out.println("isGoodUSBPersonality: return false"); //$NON-NLS-1$
		return false;
	}
	
	/**
	 * Search previous "no-switch-personality" list for a new device (e.g., user already said no before - don't ask again unless he reconnects)
	 *  
	 * @param deviceID
	 * @return
	 */
	private boolean noSwitchConnectionsContains(String deviceID) {
		if (!noSwitchConnections.isEmpty()) {
			for (String id : noSwitchConnections) {
				if (id.equals(deviceID)) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Forget one device in previous "no-switch-personality" list (e.g., device is now in a good personality and doesn't need switching)
	 * 
	 * @param deviceID
	 */
	private void forgetNoSwitchConnections(String deviceID) {
		if (noSwitchConnections.isEmpty()) {
			if (DEBUG) System.out.println("forgetNoSwitchConnections empty");
			return;
		}
		if (DEBUG) System.out.println("forgetNoSwitchConnections: " + deviceID);
		noSwitchConnections.remove(deviceID);
	}
	
	private void askToSwitchPersonality(final DeviceInfo device,
			final DeviceUSBPersonalityInfo personality, final int goodCode, final String goodDesc) {
		
		// remember this device so we don't ask again unless it changes personality or is reconnected
		rememberNoSwitchConnections(personality.deviceID);

		String message = MessageFormat.format(
				"''{0}'' is currently in {1} mode\nand must be in {2} mode to be usable in Carbide.",
				device.friendlyName,
				personality.currentPersonalityDescription,
				goodDesc);
		final IStatus status = new Status(IStatus.WARNING, Activator.PLUGIN_ID, message);
		
		String prompt = MessageFormat.format("Switch to {0} mode now.", goodDesc);
		
		RemoteConnectionsActivator.getStatusDisplay().displayStatusWithAction(status, prompt, new Runnable() {
			public void run() {
				WString pstrDeviceId = new WString(personality.deviceID);
				int dwResult = library.UPAPI_SetPersonality(upHandle, pstrDeviceId, goodCode);
				if (dwResult != PCCSErrors.CONA_OK) {
					forgetNoSwitchConnections(personality.deviceID);
					String message = status.getMessage() + "\nThe device returned an error when trying to switch. Disconnect and reconnect in the proper mode.";
					Activator.logMessage(message, IStatus.ERROR);
					if (DEBUG) System.out.printf("UPAPI_SetPersonality failed: %x\n", dwResult); //$NON-NLS-1$
				}
			}
		});

	}

	/**
	 * Device is bad personality and user said 'no' to switch (we won't ask again for this device unless it is reconnected or now in a good mode)
	 * 
	 * @param deviceID
	 */
	private void rememberNoSwitchConnections(String deviceID) {
		if (DEBUG) System.out.println("rememberNoSwitchConnections: " + deviceID);
		noSwitchConnections.add(deviceID);
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
						if (DEBUG) System.out.println("UPAPI_QueryDevices: ID found: " + deviceInfo.deviceID);
						// nice to have, but maybe null on some devices
						if (devices[i].pstrSerialNumber != null) {
							deviceInfo.serialNumber = devices[i].pstrSerialNumber.getPointer().getString(0, true);
						} else {
							deviceInfo.serialNumber = NOT_KNOWN;
						}
						// now get the personality descriptor for this device
						apiError = getPersonalityDescriptors(p, deviceInfo);
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
		if (DEBUG) System.out.printf("getAllDeviceUSBPersonalities return size : %s\n", p.size()); //$NON-NLS-1$
		return p;
	}
	private boolean getPersonalityDescriptors(
			Collection<DeviceUSBPersonalityInfo> p,
			DeviceUSBPersonalityInfo deviceInfo) {
		int dwResult;

		boolean apiError = false;
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
			apiError = getSupportedPersonalities(deviceInfo, pid, persDesc, numPers);
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
	private boolean getSupportedPersonalities(DeviceUSBPersonalityInfo deviceInfo, WString pid,
			UP_PERSONALITY_DESCRIPTORS[] persDesc, int numPers) {
		int dwResult;
		UP_PERSONALITY[] pSupportedPersonality = null;
		if (numPers > 0) {
			pSupportedPersonality = (UP_PERSONALITY[])persDesc[0].pPersonalities.toArray(numPers);
			deviceInfo.supportedPersonalities = new HashMap<Integer, String>();
		}
		boolean apiError = false;
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


//	private String getBTComPort(String address) {
//		// TODO Auto-generated method stub
//		return null;
//	}

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
		boolean matchedToDMDevice;
	}
	public void testPrerequisites() throws CoreException {
		try {
			if (library == null)
				library = IConnAPILibrary.INSTANCE;
		} catch (UnsatisfiedLinkError e) {
			String msg;
			if (Activator.isSymSEELayout()) {
				msg = Messages.ConnAPILibrary_PCCS_Not_Found_Error + Activator.getLoadErrorURL();
			} else {
				msg = Messages.ConnAPILibrary_PCSuite_Not_Found_Error + Activator.getLoadErrorURL();
			}
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, PCCSConnection.PCCS_NOT_FOUND, msg, e));
		} catch (NoClassDefFoundError e) {
			String msg;
			if (Activator.isSymSEELayout()) {
				msg = Messages.ConnAPILibrary_PCCS_Not_Found_Error + Activator.getLoadErrorURL();
			} else {
				msg = Messages.ConnAPILibrary_PCSuite_Not_Found_Error + Activator.getLoadErrorURL();
			}
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, PCCSConnection.PCCS_NOT_FOUND, msg, e));
		}
		testDMAPI();
		testUPAPI();
		testMCAPI();
	}
	/*
	 * Tests version of DMAPI and leaves it closed
	 */
	private void testDMAPI() throws CoreException {
		int dwResult = library.DMAPI_Initialize(DMAPI_VERSION, null);
		library.DMAPI_Terminate(null);
    	if (dwResult != PCCSErrors.CONA_OK) {
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
	}
	/*
	 * Test version of UPAPI and leaves it closed
	 */
	private void testUPAPI() throws CoreException {
		int dwResult = PCCSErrors.CONA_OK;
		dwResult = library.UPAPI_Initialize(UPAPIDefinitions.UPAPI_VERSION_10, null);
		library.UPAPI_Terminate(null);
    	if (dwResult != PCCSErrors.CONA_OK) {
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
	}
	/*
	 * Test version of MCAPI and leaves it closed
	 */
	private void testMCAPI() throws CoreException {
		int dwResult = PCCSErrors.CONA_OK;
		dwResult = library.MCAPI_Initialize(MCAPIDefinitions.MCAPI_VERSION_32, null);
		library.MCAPI_Terminate(null);
    	if (dwResult != PCCSErrors.CONA_OK) {
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
	}
}
