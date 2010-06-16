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
* Contributors:
*
* Description: 
*
*/
#ifndef __TCERRORCONSTANTS_H__
#define __TCERRORCONSTANTS_H__

#include <stdio.h>

// these must match com.nokia.tcf.api.TCErrorContants.java

	#define TCAPI_ERR_NONE								0	// no error
	#define TCAPI_ERR_CANNOT_OPEN						1	// cannot open port
	#define TCAPI_ERR_ALREADY_OPEN						2	// port already open
	#define TCAPI_ERR_INVALID_HANDLE					3	// invalid handle
	#define TCAPI_ERR_TIMEOUT							4	// timeout on port
	#define TCAPI_ERR_FILE_IS_OPEN						5	// a file is already open
	#define TCAPI_ERR_FILE_DOES_NOT_EXIST				6	// file does not exist
	#define TCAPI_ERR_NO_FILE							7	// no file is open
	#define TCAPI_ERR_ROUTING_STOPPED					8	// routing is stopped
	#define TCAPI_ERR_ROUTING_IN_PROGRESS				9	// routing in progress
	#define TCAPI_ERR_WRITING_FILE						10	// could not write to file
	#define TCAPI_ERR_NO_MESSAGESIDS_REGISTERED			11	// no message Ids are registered
	#define TCAPI_ERR_MEDIA_NOT_OPEN					12	// handle indicates openMedia not called
	#define TCAPI_ERR_MEDIA_NOT_SUPPORTED				13	// media type is not supported yet
	#define TCAPI_ERR_UNKNOWN_MEDIA_TYPE				14	// media not known
	#define TCAPI_ERR_MISSING_MEDIA_DATA				15	// media type known, but missing data
	#define TCAPI_ERR_INVALID_MEDIA_DATA				16	// media type known, but has invalid prefs
	#define TCAPI_ERR_WHILE_CONFIGURING_MEDIA			17	// media type known, but couldn't be configured
	#define TCAPI_ERR_CANNOT_FIND_ROUTER				18	// cannot find TCFServer.exe
	#define TCAPI_ERR_CANNOT_CREATE_ROUTER_PROCESS		19	// found " but error returned on CreateProcess
	#define TCAPI_ERR_MEDIA_IS_BUSY						20	// used for Trace bpx when someone else is connected
	#define TCAPI_ERR_PROTOCOL_NOT_SUPPORTED_BY_MEDIA	21	// TraceBox does not support protocol
	#define TCAPI_ERR_FEATURE_NOT_IMPLEMENTED			22	// API feature not implemented yet
	#define TCAPI_ERR_COMM_ERROR						23	// error while writing/polling/reading COMM port (h/w)
	#define TCAPI_ERR_COMM_TIMEOUT						24	// comm error retry timeout
	#define TCAPI_ERR_COMM_MULTIPLE_OPEN				25	// there are multiple connections open - cannot attach
	#define TCAPI_ERR_NO_COMM_OPEN						26	// there are no connections open - cannot attach
	#define TCAPI_ERR_ALREADY_CONNECTED					27	// this client is already connected to some target
	#define TCAPI_ERR_INVALID_DECODE_FORMAT				28	// invalid decode format (PN or OST is required)
	#define TCAPI_ERR_INVALID_RETRY_PERIODS				29	// invalid retry timeout or interval
	#define TCAPI_ERR_INVALID_STREAM_OVERFLOW_OPTION	30	// invalid option for overflowing input stream
	#define TCAPI_ERR_INVALID_ENCODE_FORMAT				31	// invalid trace encoding format option
	#define TCAPI_ERR_INVALID_MESSAGE_UNWRAP_OPTION		32	// invalid trace message unwrapping option
	#define TCAPI_ERR_INVALID_STREAM_BUFFER_SIZE		33	// input stream buffer size is restricted (> 0 currently)
	#define TCAPI_ERR_MISSING_MESSAGE_OPTIONS			34	// messages options (ITCMessageOptions) not specified
	#define TCAPI_ERR_MISSING_CONNECTION_SPEC			35	// missing ITCConnection specification
	#define TCAPI_ERR_MISSING_MESSAGE					36 	// ITCMessage is missing on send Message
	#define TCAPI_ERR_MESSAGE_OPTIONS_CONFLICT			37	// ITCMessage message options conflict with client's options
	#define TCAPI_ERR_MESSAGEID_MAXIMUM					38	// ITCMessageIds number > 256 (probably duplicates)
	#define TCAPI_ERR_INPUTSTREAM_FILECREATE			39	// cannot create overflow file
	#define TCAPI_ERR_INPUTSTREAM_CLOSED				40	// operation not allowed - input stream is closed
	#define TCAPI_ERR_PLATFORM_CONFIG					41	// Platform configuration not found
	#define TCAPI_ERR_ERRLISTENER_NULL					42	// error listener cannot be null (add)
	#define TCAPI_ERR_COMM_RETRY_IN_PROGRESS			43	// comm retry in progress
	#define TCAPI_INFO_COMM_RECONNECTED					44	// reconnected during retry
	#define TCAPI_ERR_COMM_INVALID_BAUDRATE				45	// Real Serial parameter checking
	#define TCAPI_ERR_COMM_INVALID_DATABITS				46	// Real Serial parameter checking
	#define TCAPI_ERR_COMM_INVALID_PARITY				47	// Real Serial parameter checking
	#define TCAPI_ERR_COMM_INVALID_STOPBITS				48	// Real Serial parameter checking
	#define TCAPI_ERR_COMM_INVALID_FLOWCONTROL			49	// Real Serial parameter checking
	#define TCAPI_ERR_COMM_SERVER_RESPONSE_TIMEOUT		50	// TCFServer response timed out
	#define TCAPI_ERR_INPUTSTREAM_BUFFER_OVERFLOW_MISSED_MSGS	51	// buffer overflowed (no overflow file) - msgs missed
	#define TCAPI_INFO_INPUTSTREAM_BUFFER_OVERFLOW_TO_FILE	52	// buffer overflowed (overflow file in use)
	#define TCAPI_ERR_INPUTSTREAM_FILE_OVERFLOW_MISSED_MSGS	53	// file overflowed - msgs missed
	#define TCAPI_ERR_FILE_SPEC_MISSING					54	// Message file not specified
	#define TCAPI_ERR_CREATE_FILE						55	// Message file could not be created
	#define TCAPI_INFO_TRACEBOX_MEMORY_WARNING			56	// TRACEBOX buffer is close to overflowing
	#define TCAPI_ERR_TRACEBOX_MEMORY_IS_CLOSED			57	// TRACEBOX buffer overflowed and is now closed
	#define TCAPI_INFO_TRACEBOX_MEMORY_IS_NORMAL		58	// TRACEBOX buffer overflowed and is now closed
	#define TCAPI_ERR_TRACEBOX_DATA_CORRUPTED			59	// TRACEBOX received corrupted trace data from phone
	#define TCAPI_ERR_TRACEBOX_PROTOCOL_MEMORY_OVERFLOW	60	// TRACEBOX protocol processing buffer overflowed - fatal
	#define TCAPI_ERR_COMM_ERROR_DEVICE_NOT_READING		61	// target device not reading output from TCF (USB)

#endif //__TCERRORCONSTANTS_H__
