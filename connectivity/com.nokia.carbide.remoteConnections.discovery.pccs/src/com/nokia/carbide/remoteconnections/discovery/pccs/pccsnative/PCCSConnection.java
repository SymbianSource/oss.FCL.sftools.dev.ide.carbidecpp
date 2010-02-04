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
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.eclipse.cdt.utils.WindowsRegistry;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Display;

import com.nokia.carbide.remoteconnections.discovery.pccs.Activator;
import com.nokia.carbide.remoteconnections.discovery.pccs.Messages;
import com.nokia.carbide.remoteconnections.discovery.pccs.pccsnative.IConnAPILibrary.IConnAPIDeviceCallback;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.ShortByReference;

public class PCCSConnection {

	private boolean DEBUG = false;
	
	/**
	 * @author chpeckha
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
						String serialNumber = "unknown device"; //$NON-NLS-1$
						if (pstrSerialNumber != null) {
							serialNumber = pstrSerialNumber.getPointer().getString(0, true);
						}
						if (DEBUG) System.out.printf("DeviceNotificationCallback %x %s\n", dwStatus, serialNumber); //$NON-NLS-1$

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
	private LPAPIHANDLE dmHandle = new LPAPIHANDLE();
	private DeviceNotificationCallback pfnCallback = new DeviceNotificationCallback();
	public static final int PCCS_NOT_FOUND = 1;
	public static final int PCCS_WRONG_VERSION = 2;
	
	private LPAPIHANDLE mcHandle = new LPAPIHANDLE();
	
	/**
	 * 
	 */
	public PCCSConnection() {
	}

	public void open() throws CoreException {
		if (library == null) {
			library = ConnAPILibrary.getInstance();
		}
		loadDMAPI();
		loadMCAPI(); //TODO: not tested yet
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
    	dmHandle.setValue(APIHANDLE.INVALID_HANDLE_VALUE);
    	dwResult = library.CONAOpenDM(dmHandle);
    	if (dwResult != PCCSErrors.CONA_OK) {
    		library.DMAPI_Terminate(null);
    		String msg = String.format(Messages.PCCSConnection_PCCS_CONAOpenDM_Error, dwResult);
    		if (dwResult == PCCSErrors.ECONA_NOT_ENOUGH_MEMORY) {
    			msg = Messages.PCCSConnection_PCCS_Not_Enough_Memory_Error;
    		}
    		throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, dwResult, msg, null));
    	}
    	
    	// register a call back
    	dwResult = library.CONARegisterNotifyCallback(dmHandle.getValue(), PCCSTypeDefinitions.API_REGISTER, pfnCallback);
    	if (dwResult != PCCSErrors.CONA_OK) {
//    		System.out.printf("CONAOpenDM returned: %x\n", dwResult);
    		library.DMAPI_Terminate(null);
    		library.CONACloseDM(dmHandle.getValue());
        	dmHandle.setValue(APIHANDLE.INVALID_HANDLE_VALUE);

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
    	dwResult = library.CONAOpenMM(mcHandle, 0);
   
    	if (dwResult != PCCSErrors.CONA_OK) {
    		library.MCAPI_Terminate(null);
    		String msg = String.format("PCCS CONAOpenMM API returned error on initialization %x", dwResult);
    		if (dwResult == PCCSErrors.ECONA_NOT_ENOUGH_MEMORY) {
    			msg = Messages.PCCSConnection_PCCS_Not_Enough_Memory_Error;
    		}
    		throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, dwResult, msg, null));
    	}
	}

	public void close() throws CoreException {
		if (library == null)
			return;
		
		closeMCAPI(); //TODO: not tested yet
		closeDMAPI();
	}
	
	/**
	 * Closes the DMAPI.
	 * @throws CoreException
	 */
	private void closeDMAPI() throws CoreException {
		int dwResult = PCCSErrors.CONA_OK;
		if (dmHandle.getValue() != APIHANDLE.INVALID_HANDLE_VALUE) {
			// unregister callback
			dwResult = library.CONARegisterNotifyCallback(dmHandle.getValue(), PCCSTypeDefinitions.API_UNREGISTER, pfnCallback);
			// close DM connection
			dwResult = library.CONACloseDM(dmHandle.getValue());
        	dmHandle.setValue(APIHANDLE.INVALID_HANDLE_VALUE);
        	// Terminate Common Functions API
//        	dwResult = library.CFAPI_Terminate(null); unnecessary
        	// Terminate Device management API
        	dwResult = library.DMAPI_Terminate(null);
		}
	}

	private void closeMCAPI() {
		int dwResult = PCCSErrors.CONA_OK;
		if (mcHandle.getValue() != APIHANDLE.INVALID_HANDLE_VALUE) {
			// close DM connection
			dwResult = library.CONACloseDM(mcHandle.getValue());
			mcHandle.setValue(APIHANDLE.INVALID_HANDLE_VALUE);
        	// Terminate Device management API
    		dwResult = library.MCAPI_Terminate(null);
		}
	}
	
	public void getMediaList() {
		if (mcHandle.getValue() != APIHANDLE.INVALID_HANDLE_VALUE) {
			IntBuffer pdwCount = IntBuffer.allocate(1);
			CONAPI_MEDIA pMedia = new CONAPI_MEDIA();
			
			CONAPI_MEDIA.ByReference[] ppMedia = null;
			
			int dwResult = library.CONAMMGetMedia(mcHandle.getValue(), pdwCount, ppMedia);
			System.out.printf("dwResult = %x\tpdwCount = %d\n", dwResult, pdwCount.get());
		}
	}
	/**
	 * @return null if no device found.
	 * @throws CoreException
	 */
	public DeviceInfo[] getDeviceList() throws CoreException {
		
		DeviceInfo[] deviceInfo = null;
		
		int dwResult = PCCSErrors.CONA_OK;
		IntByReference pdwCount = new IntByReference(0);
		dwResult = library.CONAGetDeviceCount(dmHandle.getValue(), pdwCount);
		if (DEBUG) System.out.printf("CONAGetDeviceCount: %x number of devices: %d\n", dwResult, pdwCount.getValue()); //$NON-NLS-1$

		int deviceCnt = pdwCount.getValue();
		if (dwResult != PCCSErrors.CONA_OK || deviceCnt == 0)
			return deviceInfo;
		
		// array of structs in contiguous memory required !
		CONAPI_DEVICE[] pDevices = (CONAPI_DEVICE[])new CONAPI_DEVICE().toArray(deviceCnt);
		dwResult = library.CONAGetDevices(dmHandle.getValue(), pdwCount, pDevices);
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
						deviceInfo[i].serialNumber = "not known"; //$NON-NLS-1$
					}
					if (devices[i].pstrFriendlyName != null) {
						deviceInfo[i].friendlyName = devices[i].pstrFriendlyName.getPointer().getString(0, true);
					} else {
						deviceInfo[i].friendlyName = "not known"; //$NON-NLS-1$
					}
					if (devices[i].pstrModel != null) {
						deviceInfo[i].model = devices[i].pstrModel.getPointer().getString(0, true);
					} else {
						deviceInfo[i].model = "not known"; //$NON-NLS-1$
					}
					if (devices[i].pstrManufacturer != null) {
						deviceInfo[i].mfr = devices[i].pstrManufacturer.getPointer().getString(0, true);
					} else {
						deviceInfo[i].mfr = "not known"; //$NON-NLS-1$
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
							if (deviceInfo[i].friendlyName.equals("not known")) { //$NON-NLS-1$
								// use device name as friendly name (latter was null)
								deviceInfo[i].friendlyName = connInfo.deviceName;
							}
						} else {
							connInfo.deviceName = "not known"; //$NON-NLS-1$
						}
						// Bomb if friendly name is not known or address is not known
						//   since these are essential to a connection
						if (conn[j].pstrAddress == null || deviceInfo[i].friendlyName.equals("not known")) //$NON-NLS-1$
							continue;
						
						connInfo.address = conn[j].pstrAddress.getPointer().getString(0, true);
						connInfo.state = conn[j].dwState;
						
						if (connInfo.media.equals("usb")) { //$NON-NLS-1$
							if (isGoodUSBState(connInfo.state)){ 
								connInfo.comPort = getUSBComPort(connInfo.address);
							} else {
								// don't store - not in good USB personality mode
								continue;
							}
						} else if (connInfo.media.equals("bluetooth")) { //$NON-NLS-1$
							// TODO: implement BT
							connInfo.comPort = getBTComPort(connInfo.address);
						} else {
							// IRDA and Serial(CA42) probably will not come here anyway
							connInfo.comPort = null;
						}
						if (connInfo.comPort != null)
							deviceInfo[i].connections.add(connInfo);
					}
				}
			}
			dwResult = library.CONAFreeDeviceStructure(deviceCnt, pDevices);
			if (DEBUG) System.out.printf("CONAFreeDeviceStructure: %x\n", dwResult); //$NON-NLS-1$
		}
		return deviceInfo;
	}

	private boolean isGoodUSBState(int state) {
		// This test is necessary since we're seeing
		// on some devices PCSuite mode not set BUT PCSuite trusted flag is set
		// OR PCSuite mode on device is selected BUT only Paired flag is set
		// good if:
		// in PCSuite mode
		// OR is Paired
		// OR is PCSuite trusted
		// TODO: use UPAPI here instead
		if (DMAPIDefinitions.CONAPI_IS_IN_PCSUITE_MODE(state))
			return true;
		else if (DMAPIDefinitions.CONAPI_IS_DEVICE_PAIRED(state))
			return true;
		else if (DMAPIDefinitions.CONAPI_IS_PCSUITE_TRUSTED(state))
			return true;
		return false;
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
}
