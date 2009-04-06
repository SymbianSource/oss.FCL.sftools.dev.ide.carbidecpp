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
#ifndef __TCFCPPAPI_H__
#define __TCFCPPAPI_H__

#include "TCErrorConstants.h"

#ifdef __cplusplus
extern "C" {
#endif

#ifdef TCFCLIENT_EXPORTS
#define TCF_EXP __declspec(dllexport)
#else
#define TCF_EXP __declspec(dllimport)
#endif

#define TCF_CALL

// TCP/IP
#define MAX_IPADDRESS_SIZE (20)
#define MAX_PORT_SIZE	(6)
typedef struct tagTCFCppTcpConnectData 
{
	char ipAddress[MAX_IPADDRESS_SIZE];	// e.g., "127.0.0.1"
	char ipPort[MAX_PORT_SIZE];		// e.g., "7654"
} *pTCFCppTcpConnectData, TCFCppTcpConnectData;

// Virtual Serial (BT and USB over Serial port)
#define MAX_COMPORT_SIZE	(20)
typedef struct tagTCFCppVirtualSerialConnectData
{
	char comPort[MAX_COMPORT_SIZE];		// only COM port required: e.g. COM0
} *pTCFCppVirtualSerialConnectData, TCFCppVirtualSerialConnectData;

// Real Serial
enum eTCFCppFlowControl
{
	eTCFCppFlowControlNone,
	eTCFCppFlowControlHW,
	eTCFCppFlowControlSW,
};
enum eTCFCppStopBits
{
	eTCFCppStopBits1,
	eTCFCppStopBits15,
	eTCFCppStopBits2,
};
enum eTCFCppParity
{
	eTCFCppParityNone,
	eTCFCppParityOdd,
	eTCFCppParityEven,
};

typedef struct tagTCFCppRealSerialConnectData
{
	eTCFCppFlowControl flowControl;
	eTCFCppStopBits stopBits;
	eTCFCppParity parity;
	DWORD baudRate;
	DWORD dataBits;
	char comPort[MAX_COMPORT_SIZE];
} *pTCFCppRealSerialConnectData, TCFCppRealSerialConnectData;

// Real USB
#define MAX_USBDEVICE_SIZE	(100)
typedef struct tagTCFCppUSBConnectData
{
	char device[MAX_USBDEVICE_SIZE];
} *pTCFCppUSBConnectData, TCFCppUSBConnectData;

#define MAX_DECODE_FORMAT		(16)
#define MAX_CONNECTION_TYPE		(16)
typedef struct tagTCFCppConnectData 
{
	long retryInterval;								// retry interval in seconds when port access is lost
	long retryTimeout;								// retry timeout in seconds when port access is lost
	long traceBoxChannel;							// Tracebox parameter
	char decodeFormat[MAX_DECODE_FORMAT];				// protocol decode format on incoming messages
	char connectType[MAX_CONNECTION_TYPE];					// connection type
	TCFCppTcpConnectData tcpSettings;				// TCP/IP
	TCFCppVirtualSerialConnectData virtualSerialSettings;	// Virtual serial
	TCFCppRealSerialConnectData realSerialSettings;			// Real Serial
	TCFCppUSBConnectData usbSettings;				// Real USB
} *pTCFCppConnectData, TCFCppConnectData;

// Various options for this client
// Incoming message handling
enum eTCFCppUnWrapFormat
{
	eTCFCppNone,									// return whole message (including protocol)
	eTCFCppDeleteHeader,							// return only message data (excluding headers)
};
// Outgoing message encoding options for this client
enum eTCFCppEncodeFormat
{
	eTCFCppEncodeNone,					// leave message as-is
	eTCFCppEncode,					// encode message using decode format
};
// input stream overflow
enum eTCPCppStreamOverflowOption
{
	eTCPCppStreamOverflowOff,			// no overflow to file
	eTCPCppStreamOverflowOn,			// overflow to file
};
#define MAX_INPUTSTREAMPATH (2048L)

typedef struct tagTCFCppMessageOptions
{
	long inputStreamSize;							// input stream size
//	eTCPCppStreamOverflowOption streamOverflowOption;	// stream overflow option
//	char overflowFile[MAX_INPUTSTREAMPATH];						// overflow file to use
	eTCFCppUnWrapFormat unWrapFormat;				// message unwrapping option
	long ostVersion;								// OST version to use for decoding messages
} *pTCFCppMessageOptions, TCFCppMessageOptions;

#define MAX_VERSION_STRING		(80)
#define MAX_MESSAGEIDS			(256)
typedef struct tagTCFCppMessageIds
{
	long numberIds;
	BYTE messageIds[MAX_MESSAGEIDS];
} *pTCFCppMessageIds, TCFCppMessageIds;

#define MAX_SENDMESSAGE			(64*1024L+12)
typedef struct tagTCFCppMessage
{
	eTCFCppEncodeFormat encodeFormat;	// encode or do not encode protocol using current protocol
	long ostVersion;						// OST version to use when above and encodeFormat = "ost"
	BOOL useMyId;							// format for protocol
	BYTE myId;								// my message ID to use (if useMyId=true)
} *pTCFCppMessage, TCFCppMessage;

// APIs

TCF_EXP long TCF_CALL TCFConnect(pTCFCppConnectData inConnection, pTCFCppMessageOptions inMessageOptions, pTCFCppMessageIds inMessageIds, long* outClientId);
TCF_EXP long TCF_CALL TCFDisconnect(long inClientId);
TCF_EXP long TCF_CALL TCFGetVersions(long inClientId, long& outNumberVersions, char** outVersions);
TCF_EXP long TCF_CALL TCFGetConnections(long& outNumberConnections, pTCFCppConnectData* outConnections);
TCF_EXP long TCF_CALL TCFSendMessage(long inClientId, pTCFCppMessage inMessage, long length, BYTE* data);
TCF_EXP long TCF_CALL TCFStart(long inClientId);
TCF_EXP long TCF_CALL TCFStop(long inClientId);
TCF_EXP long TCF_CALL TCFSetMessageIds(long inClientId, pTCFCppMessageIds inMessageIds);
TCF_EXP long TCF_CALL TCFPollInputStream(long inClientId, long& outLength);
TCF_EXP long TCF_CALL TCFReadInputStream(long inClientId, pTCFCppMessage outMessage, long& inLength, BYTE* outData);
TCF_EXP BOOL TCF_CALL TCFPollError(long inClientId, int* outErrorCode, BOOL* outHasOSErrorCode, long* outOSErrorCode);


typedef long (*TCFCONNECT)(pTCFCppConnectData inConnection, pTCFCppMessageOptions inMessageOptions, pTCFCppMessageIds inMessageIds, long* outClientId);
#define TCFCONNECT_FNNAME	"TCFConnect"

typedef long (*TCFDISCONNECT)(long inClientId);
#define TCFDISCONNECT_FNNAME	"TCFDisconnect"

typedef long (*TCFGETVERIONS)(long inClientId, long& outNumberVersions, char** outVersions);
#define TCFGETVERIONS_FNNAME	"TCFGetVersions"

typedef long (*TCFGETCONNECTIONS)(long& outNumberConnections, pTCFCppConnectData* outConnections);
#define TCFGETCONNECTIONS_FNNAME	"TCFGetConnections"

typedef long (*TCFSENDMESSAGE)(long inClientId, pTCFCppMessage inMessage, long length, BYTE* data);
#define TCFSENDMESSAGE_FNNAME	"TCFSendMessage"

typedef long (*TCFSTART)(long inClientId);
#define TCFSTART_FNNAME	"TCFStart"

typedef long (*TCFSTOP)(long inClientId);
#define TCFSTOP_FNNAME	"TCFStop"

typedef long (*TCFSETMESSAGEIDS)(long inClientId, pTCFCppMessageIds inMessageIds);
#define TCFSETMESSAGEIDS_FNNAME	"TCFSetMessageIds"

typedef long (*TCFPOLLINPUTSTREAM)(long inClientId, long& outLength);
#define TCFPOLLINPUTSTREAM_FNNAME	"TCFPollInputStream"

typedef long (*TCFREADINPUTSTREAM)(long inClientId, pTCFCppMessage outMessage, long& inLength, BYTE* outData);
#define TCFREADINPUTSTREAM_FNNAME	"TCFReadInputStream"

typedef long (*TCFPOLLERROR)(long inClientId, int* outErrorCode, BOOL* outHasOSErrorCode, long* outOSErrorCode);
#define TCFPOLLERROR_FNNAME	"TCFPollError"

#ifdef __cplusplus
}
#endif


#endif // __TCFCPPAPI_H__
