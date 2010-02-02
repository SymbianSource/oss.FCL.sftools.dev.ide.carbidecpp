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

public class PCCSErrors {
///////////////////////////////////////////////////////////
	// Connectivity API errors
	///////////////////////////////////////////////////////////

	public static int		CONA_OK								=0x00000000;	// Everything ok
	public static int		CONA_OK_UPDATED_MEMORY_VALUES		=0x00000001;	// Everything ok, given data is updated because (free, used and total) memory values are changed!
	public static int		CONA_OK_UPDATED_MEMORY_AND_FILES	=0x00000002;	// Everything ok, given data is updated because files and memory values are changed!
	public static int		CONA_OK_UPDATED						=0x00000004;	// Everything ok, given data is updated, unknown reason.

	public static int		CONA_OK_BUT_USER_ACTION_NEEDED		=0x00000100;	// Everything ok, but operation needs 
																// some user action (device side)
	public static int		CONA_WAIT_CONNECTION_IS_BUSY		=0x00000101;	// Operation started ok but other application
																// is reserved connection, please wait. This 
																// result code comes via FS nofication when ConnAPI
																// is initialized by value 20 or bigger.
	// Common error codes:
	public static int		ECONA_INIT_FAILED					=0x80100000;	// DLL initialization failed
	public static int		ECONA_INIT_FAILED_COM_INTERFACE		=0x80100002;	// Failed to get connection to System.
	public static int		ECONA_NOT_INITIALIZED				=0x80100004;	// API is not initialized
	public static int		ECONA_UNSUPPORTED_API_VERSION		=0x80100005;	// Failed, not supported API version
	public static int		ECONA_NOT_SUPPORTED_MANUFACTURER	=0x80100006;	// Failed, not supported manufacturer

	public static int		ECONA_UNKNOWN_ERROR					=0x80100010;	// Failed, unknown error
	public static int		ECONA_UNKNOWN_ERROR_DEVICE			=0x80100011;	// Failed, unknown error from Device
	public static int		ECONA_INVALID_POINTER				=0x80100012;	// Required pointer is invalid
	public static int		ECONA_INVALID_PARAMETER				=0x80100013;	// Invalid Parameter value 
	public static int		ECONA_INVALID_HANDLE				=0x80100014;	// Invalid HANDLE
	public static int		ECONA_NOT_ENOUGH_MEMORY				=0x80100015;  // Memory allocation failed in PC
	public static int		ECONA_WRONG_THREAD					=0x80100016;	// Failed, Called interface was marshalled for a different thread.
	public static int		ECONA_REGISTER_ALREADY_DONE			=0x80100017;	// Failed, notification interface is already registered.

	public static int		ECONA_CANCELLED						=0x80100020;	// Operation cancelled by ConnectivityAPI-User
	public static int		ECONA_NOTHING_TO_CANCEL				=0x80100021;	// No running functions, or cancel has called too late.
	public static int		ECONA_FAILED_TIMEOUT				=0x80100022;	// Operation failed because of timeout
	public static int		ECONA_NOT_SUPPORTED_DEVICE			=0x80100023;	// Device do not support operation
	public static int		ECONA_NOT_SUPPORTED_PC				=0x80100024;	// ConnectivityAPI do not support operation (not implemented)
	public static int		ECONA_NOT_FOUND						=0x80100025;	// Item was not found
	public static int		ECONA_FAILED						=0x80100026;	// Failed, the called operation failed.

	public static int		ECONA_API_NOT_FOUND					=0x80100100;	// Needed API module was not found from the system
	public static int		ECONA_API_FUNCTION_NOT_FOUND		=0x80100101;	// Called API function was not found from the loaded API module

	// Device manager and device connection releated error:
	public static int		ECONA_DEVICE_NOT_FOUND				=0x80200000;  // Given phone is not connected (refresh device list)
	public static int		ECONA_NO_CONNECTION_VIA_MEDIA		=0x80200001;  // Phone is connected but not via given Media
	public static int		ECONA_NO_CONNECTION_VIA_DEVID		=0x80200002;  // Phone is not connected with given DevID
	public static int		ECONA_INVALID_CONNECTION_TYPE		=0x80200003;  // Connection type was invalid
	public static int		ECONA_NOT_SUPPORTED_CONNECTION_TYPE =0x80200004;  // Device do not support connection type
	public static int		ECONA_CONNECTION_BUSY				=0x80200005;  // Other application is recerved connection
	public static int		ECONA_CONNECTION_LOST				=0x80200006;  // Connection is lost to Device
	public static int		ECONA_CONNECTION_REMOVED			=0x80200007;  // Connection removed, other application is reserved connection.
	public static int		ECONA_CONNECTION_FAILED				=0x80200008;  // Connection failed, unknown reason
	public static int		ECONA_SUSPEND						=0x80200009;  // Connection removed, PC goes suspend state
	public static int		ECONA_NAME_ALREADY_EXISTS			=0x8020000A;  // Friendly name already exist
	public static int		ECONA_MEDIA_IS_NOT_WORKING			=0x8020000B;  // Failed, target media is active but it is not working (e.g. BT-hardware stopped or removed)
	public static int		ECONA_CACHE_IS_NOT_AVAILABLE		=0x8020000C;	// Failed, cache is not available (CONASearchDevices)
	public static int		ECONA_MEDIA_IS_NOT_ACTIVE			=0x8020000D;  // Failed, target media is not active (or ready yet)
	public static int		ECONA_PORT_OPEN_FAILED				=0x8020000E;  // Port opening failed (only when media is CONAPI_MEDIA_SERIAL and COM port is changed).

	// Device paring releated errors:
	public static int		ECONA_DEVICE_PAIRING_FAILED			=0x80200100; // Failed, pairing failed
	public static int		ECONA_DEVICE_PASSWORD_WRONG			=0x80200101; // Failed, wrong password on device. 
	public static int		ECONA_DEVICE_PASSWORD_INVALID		=0x80200102; // Failed, password includes invalid characters or missing. 


	// File System errors:
	public static int		ECONA_ALL_LISTED					=0x80300000;	// All items are listed
	public static int		ECONA_MEMORY_FULL					=0x80300001;	// Device memory full

	// File System error for file functions:
	public static int		ECONA_FILE_NAME_INVALID				=0x80400001;	// File name includes invalid characters in Device or PC
	public static int		ECONA_FILE_NAME_TOO_LONG			=0x80400002;	// Max unicode charaters in File name (includes current/target path) 
																// is limited to 256 charaters in device. (256 charaters includes 
																// two backlashs in begin of the path and one end of the path.)
	public static int		ECONA_FILE_ALREADY_EXIST			=0x80400003;	// File already exits in Device or PC
	public static int		ECONA_FILE_NOT_FOUND				=0x80400004;	// File does not exits in Device or PC
	public static int		ECONA_FILE_NO_PERMISSION			=0x80400005;	// Not allow to perform required operation to file in Device 
	public static int		ECONA_FILE_COPYRIGHT_PROTECTED		=0x80400006;	// Not allow to perform required operation to file in Device
	public static int		ECONA_FILE_BUSY						=0x80400007;	// Other application is recerved file in Device or PC
	public static int		ECONA_FILE_TOO_BIG_DEVICE			=0x80400008;	// Device reject the operation because file size is too big
	public static int		ECONA_FILE_TYPE_NOT_SUPPORTED		=0x80400009;	// Device reject the operation because file unsupported type
	public static int		ECONA_FILE_NO_PERMISSION_ON_PC		=0x8040000A;	// Not allow to perform required operation to file in PC 
	public static int		ECONA_FILE_EXIST					=0x8040000B;	// File move or rename: File is copied to target path with new name but removing the source file failed. 
	public static int		ECONA_FILE_CONTENT_NOT_FOUND		=0x8040000C;	// Specified file content does not found (e.g. unknown file section or stored position).
	public static int		ECONA_FILE_OLD_FORMAT				=0x8040000D;	// Specified file content supports old engine.
	public static int		ECONA_FILE_INVALID_DATA				=0x8040000E;	// Specified file data is invalid.

	// File System error for folder functions:
	public static int		ECONA_INVALID_DATA_DEVICE			=0x80500000;	// Device's folder contain invalid data
	public static int		ECONA_CURRENT_FOLDER_NOT_FOUND		=0x80500001;	// Current/Target folder is invalid in device (e.g MMC card removed). 
	public static int		ECONA_FOLDER_PATH_TOO_LONG			=0x80500002;	// Current/Target folder max unicode charaters count is limited to 
																// 245 charaters in device. (245 charaters includes two backlashs in 
																// begin of the path and one end of the path)
	public static int		ECONA_FOLDER_NAME_INVALID			=0x80500003;	// Folder name includes invalid characters in Device or PC
	public static int		ECONA_FOLDER_ALREADY_EXIST			=0x80500004;	// Folder is already exits in target folder
	public static int		ECONA_FOLDER_NOT_FOUND				=0x80500005;	// Folder not found (PC/Device)
	public static int		ECONA_FOLDER_NO_PERMISSION			=0x80500006;	// Not allow to perform required operation to folder in Devic
	public static int		ECONA_FOLDER_NOT_EMPTY				=0x80500007;	// Not allow to perform required operation because folder is not empty
	public static int		ECONA_FOLDER_NO_PERMISSION_ON_PC	=0x80500008;	// Not allow to perform required operation to folder in PC

	// Application Installation:
	public static int		ECONA_DEVICE_INSTALLER_BUSY			=0x80600000;	// Can not start Device's installer

	//Syncronization specific error codes :
	public static int		ECONA_UI_NOT_IDLE_DEVICE			=0x80700000;	// Failed, device rejects the operation. Maybe device's UI was not IDLE-state.
	public static int		ECONA_SYNC_CLIENT_BUSY_DEVICE		=0x80700001;	// Failed, device's SA sync client is busy.
	public static int		ECONA_UNAUTHORIZED_DEVICE 			=0x80700002;	// Failed, device rejects the operation. No permission.
	public static int		ECONA_DATABASE_LOCKED_DEVICE 		=0x80700003;	// Failed, device rejects the operation. Device is locked.
	public static int		ECONA_SETTINGS_NOT_OK_DEVICE 		=0x80700004;	// Failed, device rejects the operation. Maybe settings in Sync profile are wrong on Device.
	public static int		ECONA_SYNC_ITEM_TOO_BIG				=0x80700501; // 
	public static int		ECONA_SYNC_ITEM_REJECT				=0x80700502; // All commands,Device reject the operation...
	public static int		ECONA_SYNC_INSTALL_PLUGIN_FIRST		=0x80700506; // 

	// Versit conversion specific error codes :			
	public static int		ECONA_VERSIT_INVALID_PARAM				=0x80800001;	// Invalid parameters passed to versit converter 
	public static int		ECONA_VERSIT_UNKNOWN_TYPE				=0x80800002;	// Failed, trying to convert versit formats not supported in VersitConverter
	public static int		ECONA_VERSIT_INVALID_VERSIT_OBJECT		=0x80800003;	// Failed, validation of versit data not passed, contains invalid data

	// Database specific error codes :
	public static int		ECONA_DB_TRANSACTION_ALREADY_STARTED	=0x80800100;	// Another transaction is already in progress.
	public static int		ECONA_DB_TRANSACTION_FAILED				=0x80800101;	// Some of operations within a transaction failed and transaction was rolled back.

	//Backup specific error codes
	public static int		ECONA_DEVICE_BATTERY_LEVEL_TOO_LOW		=0x80900000;  // Failed, device rejects the restore operation. Device's battery level is low.
	public static int		ECONA_DEVICE_BUSY						=0x80900001;  // Failed, device rejects the backup/resore operation. Device's backup server busy.

}
