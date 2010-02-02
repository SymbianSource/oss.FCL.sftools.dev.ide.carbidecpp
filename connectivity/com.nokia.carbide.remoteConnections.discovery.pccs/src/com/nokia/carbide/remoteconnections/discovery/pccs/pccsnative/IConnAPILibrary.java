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
import java.nio.ShortBuffer;

import org.eclipse.core.runtime.CoreException;

import com.sun.jna.Callback;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.ShortByReference;
import com.sun.jna.win32.StdCallLibrary;

public interface IConnAPILibrary extends StdCallLibrary {

	// DMAPI Device Management
	// DMAPI Callbacks
	public interface IConnAPIDeviceCallback extends StdCallCallback {
		int invoke(int dwStatus, ShortByReference pstrSerialNumber);
	}

	// DMAPI APIs
	int DMAPI_Initialize(int dwAPIVersion, IntByReference pdwParam);
	int DMAPI_Terminate(IntByReference pdwParam);
	int DMAPI_GetAPIVersion();
	int CONAOpenDM(LPDMHANDLE mHDMHandle);
	int CONACloseDM(DMHANDLE mHDMHandle);
	int CONAGetDeviceCount(DMHANDLE hDMHandle, IntByReference pdwCount);
	int CONAGetDevices(DMHANDLE hDMHandle, IntByReference pdwCount, CONAPI_DEVICE[] pDevices );
	int CONAFreeDeviceStructure( int dwCount, CONAPI_DEVICE[] pDevices );
	int CONARegisterNotifyCallback(DMHANDLE hDMHandle, int dwState, IConnAPIDeviceCallback pfnNotify);

	// CFAPI Common APIs
	int CFAPI_Initialize(int dwAPIVersion, IntByReference pdwParam);
	int CFAPI_Terminate(IntByReference pdwParam);

	// MCAPI Media Control
	// MCAPI Callbacks
	public interface IConnAPIMediaCallback extends StdCallCallback {
		int invoke(int dwStatus, CONAPI_MEDIA[] pMedia);
	}
	// MCAPI APIs
	int MCAPI_Initialize(int dwAPIVersion, IntBuffer pdwParam);
	int MCAPI_Terminate(IntBuffer pdwParam);
	int MCAPI_GetAPIVersion();
	int CONAOpenMM(LPMCHANDLE phMCHandle, int dwValue);
	int CONACloseMM(MCHANDLE hMCHandle);
	int CONAMMGetMedia(MCHANDLE hMCHandle, IntBuffer pdwCountOfMedia, CONAPI_MEDIA.ByReference ppMedia[]); //TODO: c++ - CONAPI_MEDIA**	ppMedia
	int CONAMMSetMedia(MCHANDLE hMCHandle, CONAPI_MEDIA[] pMedia);
	int CONAMMFreeMediaStructures(int dwCountOfMedia, CONAPI_MEDIA[] pMedia);
	int MCAPI_GetMediaInfo(MCHANDLE hMCHandle, ShortBuffer pstrMediaID, CONAPI_MEDIA_INFO[] pMediaInfo);
	int MCAPI_FreeMediaInfo(CONAPI_MEDIA_INFO[] pMediaInfo);
	int CONARegisterMMNotifyCallback(MCHANDLE hMCHandle, int dwState, IConnAPIMediaCallback pfnNotify);

	
	// UPAPI USB Personality
	// UPAPI APIs
	int UPAPI_Initialize(int dwAPIVersion, IntBuffer pdwParam);
	int UPAPI_Terminate(IntBuffer pdwParam);
	int UPAPI_GetAPIVersion();
	int UPAPI_OpenUSBPersonality(int dwVendorId, LPUPHANDLE phUPHandle);
	int UPAPI_CloseUSBPersonality(UPHANDLE hUPHandle);
	int UPAPI_QueryDeviceCount(UPHANDLE hUPHandle, IntBuffer pdwDeviceCount);
	int UPAPI_QueryDevices(com.sun.jna.Pointer hUPHandle, IntBuffer pdwDeviceCount, UP_DEVICE_DESCRIPTOR[] pDeviceDescriptor);
	int UPAPI_FreeDeviceDescriptor(int dwDeviceCount, UP_DEVICE_DESCRIPTOR[] pDeviceDescriptor);
	int UPAPI_GetConfigurationDescriptor(UPHANDLE hUPHandle, ShortBuffer pstrDeviceId, UP_CONFIGURATION_DESCRIPTOR[] pConfigurationDescriptor);
	int UPAPI_GetStringDescriptor(UPHANDLE hUPHandle, ShortBuffer pstrDeviceId, int dwDescriptorIndex, int dwLanguageID, UP_STRING_DESCRIPTOR[] pStringDescriptor);
	int UPAPI_FreeStringDescriptor(UP_STRING_DESCRIPTOR[] pStringDescriptor);
	int UPAPI_GetPersonalityDescriptors(UPHANDLE hUPHandle, ShortBuffer pstrDeviceId, UP_PERSONALITY_DESCRIPTORS[] pPersonalityDescriptors);
	int UPAPI_FreePersonalityDescriptors(UP_PERSONALITY_DESCRIPTORS[] pPersonalityDescriptors);
	int UPAPI_SetPersonality(UPHANDLE hUPHandle, ShortBuffer pstrDeviceId, int dwPersonalityCode);
	int UPAPI_SendCommandToDevice(UPHANDLE hUPHandle, ShortBuffer pstrDeviceId, int dwDataDirection, UP_DATA_BUFFER[] pDataBuffer);
	

}
