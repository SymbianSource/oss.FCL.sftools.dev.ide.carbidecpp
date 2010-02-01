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

public class DMAPIDefinitions {
	//=========================================================
	// CFAPI version
	//
	public static final int CFAPI_VERSION_10 = 10;
	//=========================================================
	//=========================================================
	// Device Management API versions 
	//
	public static final int DMAPI_VERSION_30		=30;
	public static final int DMAPI_VERSION_31		=31;
	public static final int DMAPI_VERSION_32		=32;
	public static final int DMAPI_VERSION_33		=33;
	public static final int DMAPI_VERSION_34		=34;
	public static final int DMAPI_VERSION_35		=35;
	public static final int DMAPI_VERSION_36		=36;
	public static final int DMAPI_VERSION_37		=37;
	//=========================================================
	// Device callback status values
	public static final int CONAPI_DEVICE_LIST_UPDATED		=0x00;	// List is updated. No any specific information.
	public static final int CONAPI_DEVICE_ADDED				=0x01;	// A new device is added to the list.
	public static final int CONAPI_DEVICE_REMOVED			=0x02;	// Device is removed from the list.
	public static final int CONAPI_DEVICE_UPDATED			=0x04;	// Device is updated. A connection is added or removed
													// or device is renamed. Device still exist in the list.
	// Device callback info values
	public static final int CONAPI_CONNECTION_ADDED			=0x01;	// Note! HIBYTE == media, LOBYTE == CONAPI_CONNECTION_ADDED
	public static final int CONAPI_CONNECTION_REMOVED		=0x02;	// Note! HIBYTE == media, LOBYTE == CONAPI_CONNECTION_REMOVED
	public static final int CONAPI_DEVICE_RENAMED			=0x04;	// Friendly name of the device is changed

	// Device callback macros
//	#define	GET_CONAPI_CB_STATUS(Status)	(0x0000FFFF & Status)
//	#define	GET_CONAPI_CB_INFO(Status)		((0x00FF0000 & Status)>>16)
//	#define	GET_CONAPI_CB_INFO_DATA(Status)	((0xFF000000 & Status)>>24)
	public static final int GET_CONAPI_CB_STATUS(int status) {
		return (0x0000FFFF & status);
	}
	public static final int GET_CONAPI_CB_INFO(int status) {
		return ((0x00FF0000 & status) >>16);
	}
	public static final int GET_CONAPI_CB_INFO_DATA(int status) {
		return ((0xFF000000 & status)>>24);
	}
	// Definitions used with dwState value in CONAPI_CONNECTION_INFO stucture:
//	#define	CONAPI_DEVICE_NOT_FUNCTIONAL		0x00000000	// Device is not working or unsupported device.
//	#define	CONAPI_DEVICE_UNPAIRED				0x00000001	// Device is not paired
//	#define	CONAPI_DEVICE_PAIRED				0x00000002	// Device is paired
//	#define	CONAPI_DEVICE_PCSUITE_TRUSTED		0x00000004	// Device is PC Suite trusted
//	#define	CONAPI_DEVICE_WRONG_MODE			0x00000008	// Device is connected in wrong mode.
	public static final int CONAPI_DEVICE_NOT_FUNCTIONAL = 0x00000000;
	public static final int CONAPI_DEVICE_UNPAIRED = 0x00000001;
	public static final int CONAPI_DEVICE_PAIRED = 0x00000002;
	public static final int CONAPI_DEVICE_PCSUITE_TRUSTED = 0x00000004;
	public static final int CONAPI_DEVICE_WRONG_MODE = 0x00000008;

	// Definitions used also with dwState value in CONAPI_CONNECTION_INFO stucture if 
	// CONASetDeviceListOption function is called with DMAPI_OPTION_INCLUDE_USB_MODE_IN_LIST parameter:
//	#define CONAPI_DEVICE_IN_PCSUITE_MODE		0x01000000	// PC Suite mode
//	#define CONAPI_DEVICE_IN_MASSSTORAGE_MODE	0x02000000  // Mass storage mode
//	#define CONAPI_DEVICE_IN_MTP_MODE			0x03000000	// Media Transfer mode
//	#define CONAPI_DEVICE_IN_RNDIS_MODE			0x04000000  // RNDIS mode
//	#define CONAPI_DEVICE_IN_PICTBRIDGE_MODE	0x05000000  // Picture bridge mode
//	#define CONAPI_DEVICE_IN_UNKNOWN_MODE		0x0E000000  // Unknown mode
	public static final int CONAPI_DEVICE_IN_PCSUITE_MODE = 0x01000000;
	public static final int CONAPI_DEVICE_IN_MASSSTORAGE_MODE = 0x02000000;
	public static final int CONAPI_DEVICE_IN_MTP_MODE = 0x03000000;
	public static final int CONAPI_DEVICE_IN_RNDIS_MODE = 0x04000000;
	public static final int CONAPI_DEVICE_IN_PICTBRIDGE_MODE = 0x05000000;
	public static final int CONAPI_DEVICE_IN_UNKNOWN_MODE = 0x0E000000;
	

	// Macros used to check device's swState value: 
//	#define	CONAPI_IS_DEVICE_UNPAIRED(dwState)	(dwState & 0x01)		// Returns 1 if true
//	#define	CONAPI_IS_DEVICE_PAIRED(dwState)	((dwState >> 1) & 0x01) // Returns 1 if true
//	#define	CONAPI_IS_PCSUITE_TRUSTED(dwState)	((dwState >> 2) & 0x01) // Returns 1 if true
//	#define	CONAPI_IS_DEVICE_WRONG_MODE(dwState)((dwState >> 3) & 0x01)	// Returns 1 if true
//	#define	CONAPI_GET_DEVICE_MODE(dwState)     (dwState & 0x0F000000)	// Returns CONAPI_DEVICE_IN_xxxx_MODE value

	public static final boolean CONAPI_IS_DEVICE_UNPAIRED(int state) {
		return (state & 0x01) != 0;
	}
	public static final boolean CONAPI_IS_DEVICE_PAIRED(int state) {
		return ((state >> 1) & 0x01) != 0;
	}
	public static final boolean CONAPI_IS_PCSUITE_TRUSTED(int state) {
		return ((state >> 2) & 0x01) != 0;
	}
	public static final boolean CONAPI_IS_DEVICE_WRONG_MODE(int state) {
		return ((state >> 3) & 0x01) != 0;
	}
	public static final int CONAPI_GET_DEVICE_MODE(int state) {
		return (state & 0x0F000000);
	}
	public static final boolean CONAPI_IS_IN_PCSUITE_MODE(int state) {
		return CONAPI_GET_DEVICE_MODE(state) == CONAPI_DEVICE_IN_PCSUITE_MODE;
	}
	public static final boolean CONAPI_IS_IN_MASSSTORAGE_MODE(int state) {
		return CONAPI_GET_DEVICE_MODE(state) == CONAPI_DEVICE_IN_MASSSTORAGE_MODE;
	}
	public static final boolean CONAPI_IS_IN_RNDIS_MODE(int state) {
		return CONAPI_GET_DEVICE_MODE(state) == CONAPI_DEVICE_IN_RNDIS_MODE;
	}

	
	public static final int CONAPI_SERIES60_2ED_DEVICE = 33554448;
	public static final int CONAPI_FS_FILE_CONVERSION = 256;
	public static final int CONAPI_DEVICE_PRODUCT_INFO = 1048576;
	public static final int CONAPI_DEVICE_IS_APP_SUPPORTED = 2;
	public static final int CONAPI_ALLOW_TO_USE_CACHE = 4096;
	public static final int DMAPI_OPTION_SET_MANUFACTURER = 1;
	public static final int CONAPI_UNKNOWN_DEVICE = 0;
	public static final int CONAPI_SET_PCSUITE_UNTRUSTED = 2048;
	public static final int CONAPI_SET_PCSUITE_TRUSTED = 1024;
	public static final int CONAPI_DEVICE_GET_VERSION = 1048580;
	public static final int CONAPI_DS_MMS_SERVICE = 20480;
	public static final int CONAPI_SYNC_SA_DM = 2;
	public static final int CONAPI_GET_PAIRED_PHONES = 16384;
	public static final int CONAPI_DEVICE_GET_FOLDEREXCLUDE = 7340036;
	public static final int CONAPI_NEF_SERVICE = 12288;
	public static final int CONAPI_DEVICE_GENERAL_INFO = 65536;
	public static final int CONAPI_SERIES80_DEVICE = 33554688;
	public static final int CONAPI_FOLDER_BROWSING_SERVICE = 28672;
	public static final int CONAPI_DS_SERVICE = 4096;
	public static final int CONAPI_DEVICE_GET_PROPERTY = 1;
	public static final int CONAPI_FS_INSTALL_JAVA_APPLICATIONS = 16;
	public static final int CONAPI_GET_ALL_PHONES = 8192;
	public static final int CONAPI_SYNC_CI_DS = 16;
	public static final int CONAPI_DEVICE_GET_OBJTYPE = 3145732;
	public static final int CONAPI_FS_LIST_APPLICATIONS = 512;
	public static final int CONAPI_FS_INSTALL_SIS_APPLICATIONS = 32;
	public static final int CONAPI_DEVICE_GET_NETWORK_ID = 50331652;
	public static final int CONAPI_DS_BOOKMARKS_SERVICE = 24576;
	public static final int CONAPI_DM_SERVICE = 8192;
	public static final int CONAPI_DEVICE_GET_FOLDERMEMTYPE = 6291460;
	public static final int CONAPI_FS_NOT_SUPPORTED = 0;
	public static final int CONAPI_FS_SUPPORTED = 1;
	public static final int CONAPI_NOKIA7710_DEVICE = 33558528;
	public static final int CONAPI_USER_DEFINED_SERVICE = 32768;
	public static final int CONAPI_GET_TRUSTED_PHONES = 32768;
	public static final int CONAPI_DS_SMS_SERVICE = 16384;
	public static final int CONAPI_SYNC_NOT_SUPPORTED = 0;
	public static final int CONAPI_FS_INSTALL_SISX_APPLICATIONS = 64;
	public static final int CONAPI_SERIES60_3ED_DEVICE = 33554464;
	public static final int CONAPI_DEVICE_GET_FOLDERPATH = 5242884;
	public static final int CONAPI_SYNC_SA_DS = 1;
	public static final int CONAPI_DEVICE_GET_COUNTRY_CODE = 33554436;
	public static final int CONAPI_FS_UNINSTALL_APPLICATIONS = 1024;
	public static final int CONAPI_FS_EXTENDED_OPERATIONS = 2048;
	public static final int CONAPI_UNPAIR_DEVICE = 512;
	public static final int CONAPI_DEVICE_PROPERTIES_INFO = 16777216;
	public static final int CONAPI_DEVICE_GET_CURRENT_NETWORK = 16777220;
	public static final int CONAPI_DEVICE_GET_ALL_VALUES = 8388612;
	public static final int CONAPI_SERIES40_DEVICE = 16777217;
	public static final int CONAPI_DEVICE_GET_FILEPATH = 4194308;
	public static final int CONAPI_DEVICE_GET_UUID = 2097156;
	public static final int CONAPI_PAIR_DEVICE = 256;
	public static final int CONAPI_DEVICE_ICON_INFO = 268435456;

}
