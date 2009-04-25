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
#ifndef __SERVERCLIENT_H__
#define __SERVERCLIENT_H__

#include "shareddata.h"
#include "mutex.h"

#define MAX_IPADDRESS_SIZE (20)
#define MAX_PORT_SIZE	(6)
typedef struct tagTcpConnectData 
{
	char ipAddress[MAX_IPADDRESS_SIZE];
	char ipPort[MAX_PORT_SIZE];
} *pTcpConnectData, TcpConnectData;

enum eFlowControl
{
	eFlowControlNone,
	eFlowControlHW,
	eFlowControlSW,
};
enum eStopBits
{
	eStopBits1,
	eStopBits15,
	eStopBits2,
};
enum eParity
{
	eParityNone,
	eParityOdd,
	eParityEven,
};

#define MAX_COMPORT_SIZE	(20)
typedef struct tagRealSerialConnectData
{
	eFlowControl flowControl;
	eStopBits stopBits;
	eParity parity;
	DWORD baudRate;
	DWORD dataBits;
	char comPort[MAX_COMPORT_SIZE];
} *pRealSerialConnectData, RealSerialConnectData;

typedef struct tagVirtualSerialConnectData
{
	char comPort[MAX_COMPORT_SIZE];
} *pVirtualSerialConnectData, VirtualSerialConnectData;

#define MAX_USBDEVICE_SIZE	(100)
typedef struct tagUSBConnectData
{
	// TODO
	char device[MAX_USBDEVICE_SIZE];
} *pUSBConnectData, USBConnectData;

#define MAX_NUMBER_OPTIONS	(20)
#define MAX_DECODE_FORMAT	(16)
#define MAX_CONNECTION_TYPE	(16)

// per connection settings
typedef struct tagConnectData 
{
	DWORD retryInterval;				// retry interval option
	DWORD retryTimeout;					// retry timeout option
	DWORD traceBoxChannel;					// added TraceBox information
	char  decodeFormat[MAX_DECODE_FORMAT];	// decode format (e.g. "ost") option
	char connectType[MAX_CONNECTION_TYPE];	// connection type (e.g. "tcp") 
	TcpConnectData tcpSettings;			// TCP/IP settings
	RealSerialConnectData realSerialSettings;	// real serial
	VirtualSerialConnectData virtualSerialSettings;	// virtual serial
	USBConnectData usbSettings;			// real USB

} *pConnectData, ConnectData;

// per client options - done at connect time
typedef struct tagClientOptions
{
	DWORD unWrapFormat;					// message unwrapping option (PN or OST)
	DWORD ostVersion;					// which OST version to use on sendmessage (unused in native)

} *pClientOptions, ClientOptions;

// per client options - done on opening stream
enum eMessageDestination
{
	eDestinationInputStream = 0,		// input stream
	eDestinationFile,					// message file
};
#define MAX_FILEPATH (2048L)
typedef struct tagDestinationOptions
{
	eMessageDestination destination;	// eMessageDestination
	DWORD streamSize;					// input stream size
	BOOL overFlowToFile;				// overflow stream to file option
	CHAR destinationFile[MAX_FILEPATH];	// stream overflow file or message file

} *pDestinationOptions, DestinationOptions;

enum eServerCommand
{
	eCmdNone = 0,						// no command to process
	eCmdConnect,							// connect (connectSettings clientOptions *clientId)
	eCmdDisconnect,						// disconnect (clientId)
	eCmdGetClientStatus,					// getclientstatus (clientId *clientStatus)
	eCmdGetConnectionStatus,				// getconnstatus
	eCmdSetMessageIds,						// setmessageIds (clientId number messageIds)
	eCmdGetNumberVersions,					// getnumberversions (clientId *number)
	eCmdGetVersion,						// getversion (clientId index *version)
	eCmdGetNumberConnections,				// getnumberconnections (*number)
	eCmdGetConnectionType,					// getconnectiontype (index *connectionSettings.connectType)
	eCmdGetConnection,						// getconnection (index *connectionSettings)
	eCmdGetNumberClients,					// getnumclients (*number)
	eCmdOpenStream,							// openstream (clientId destinationoptions)
	eCmdCloseStream,						// closestream (clientId)
	eCmdStart,								// start (clientId)
	eCmdStop,								// stop (clientId)
	eCmdSendMessage,						// sendmessage (clientId number message)
	eCmdTestClientConnection,				// testconnection (clientId)
	eCmdTestConnection,					// testconnection (connectSettings)
	eCmdExit,								// tell server to exit its main command thread
	eCmdOpenMessageFile,					// openfile (clientId destinationoptions)
	eCmdCloseMessageFile,					// closefile (clientId)
	eCmdClearMessageFile,					// clearfile (clientId)

};

enum eServerResponse
{
	eRspNone = 0,						// no response generated
	eRspOK,								// response with no errors
	eRspError,							// error response: error
	eRspExitted,						// server has exitted
};

enum eClientStatus
{
	eStarted,							// message processing started
	eStopped,							// message processing stopped
	eUnknownClient,						// client not found
};

enum eConnectionStatus
{
	eConnected,							// connection is OK
	eDisconnected,						// disconnected
	eRetryInProgress,					// inside retry interval (connected)
	eRetryTimedOut,						// retry timedout (disconnected)
};

// ----------- Command/Response Data -------------------
#define MAX_VERSION_STRING		(80)
#define MAX_MESSAGEIDS			(256)
typedef struct tagServerCommandData
{
	eServerCommand command;				// command type
	eServerResponse response;			// response type
	eClientStatus clientStatus;			// client status
	eConnectionStatus connectionStatus;	// connection status
	long error;							// response error
	unsigned long osError;				// error from the OS if applicable
	long clientId;						// clientID for command/response
	long index;							// eGetVersion command/eGetConnection command
	long number;						// length or other number value (see commands)
	char version[MAX_VERSION_STRING];	// eGetVersion response
	ConnectData connectSettings;		// eConnect command/eGetConnection response
	ClientOptions clientOptions;		// per client options
	DestinationOptions destinationOptions;		// input stream or message file options
	long numClients;					// eGetNumberClients response
	long numConnections;				// eGetNumberConnections response
	long encodeOption;					// eSendMessage - what to do with protocol headers
	BYTE protocolVersion;				// eSendMessage - OST version byte if OST protocol
	BOOL useMyId;						// eSendMessage
	BYTE myId;							// eSendMessage
	BYTE messageIds[MAX_MESSAGEIDS];	// eSetMessageIds command

} *pServerCommandData, ServerCommandData;

#define SERVERCOMMANDDATA_MAP_SIZE	(sizeof(ServerCommandData))
#define SERVERCOMMANDDATA_MAP_NAME	"TCFServerCommandData"

#define SERVERCOMMANDDATA_MUTEX_NAME	"TCFServerCommandDataMutex"
//#define SERVERCOMMANDDATA_MUTEX_TIMEOUT	(60000L)
#define SERVERCOMMANDDATA_MUTEX_TIMEOUT	(1000L)

class CServerCommandData : public CSharedData
{
public:
	BOOL Init() { 	if (IsCreator()) \
	{\
		pServerCommandData pData = (pServerCommandData)GetDataPtr();\
		pData->command = eCmdNone;\
		pData->response = eRspNone;\
		pData->clientStatus = eStopped;\
		pData->connectionStatus = eDisconnected;\
		pData->clientId = -1;\
		pData->index = 0;\
		pData->number = 0;\
		pData->version[0] = 0;\
		pData->numClients = 0;\
		pData->numConnections = 0;\
		memset(&pData->messageIds, 0, MAX_MESSAGEIDS);\
	}\
	return TRUE; }
};
// ----------- Command/Response Data -------------------

// ----------- Send Message Data -------------------
#define MAX_SENDMESSAGE			(64*1024L+12)
typedef struct tagServerMessageData
{
	long length;
	BYTE message[MAX_SENDMESSAGE];	// eSendMessage command
} *pServerMessageData, ServerMessageData;

#define SERVERMESSAGEDATA_MAP_SIZE	(sizeof(ServerMessageData))
#define SERVERMESSAGEDATA_MAP_NAME	"TCFServerMessageData"

class CServerMessageData : public CSharedData
{
public:
	BOOL Init() { if (IsCreator()) \
	{\
		pServerMessageData pData = (pServerMessageData)GetDataPtr();\
		memset(&pData->message, 0, MAX_SENDMESSAGE);\
		pData->length = 0;\
	}\
	return TRUE; }
};
// ----------- Send Message Data -------------------

// ----------- Server Process Data -------------------
typedef struct tagServerProcessData
{
	long numRefs;						// reference count (first creates server process/last destroys server process)
	PROCESS_INFORMATION serverProcess;	// Server process information
} *pServerProcessData, ServerProcessData;

#define SERVERPROCESSDATA_MAP_SIZE	(sizeof(ServerProcessData))
#define SERVERPROCESSDATA_MAP_NAME	"TCFServerProcessData"

class CServerProcessData : public CSharedData
{
public:
	BOOL Init() { if (IsCreator()) \
	{\
		pServerProcessData pData = (pServerProcessData)GetDataPtr();\
		pData->numRefs = 0;\
		memset(&pData->serverProcess, 0, sizeof(pData->serverProcess));\
		pData->serverProcess.hProcess = NULL;\
	}\
	return TRUE; }
};
// ----------- Server Process Data -------------------


// Main server command/response class
#define SERVERPIPE_MUTEX_NAME	"TCFServerPipeMutex"
#define SERVERPIPE_MUTEX_TIMEOUT	(60000L)

// Server command/response events
#define SERVER_COMMAND_READY_EVENTNAME	"TCFServerCommandReadyEvent"
#define SERVER_RESPONSE_READY_EVENTNAME	"TCFServerResponseReadyEvent"
#define SERVER_CMDRSP_EVENT_TIMEOUT	60000L

class CServerCommand
{
public:
	CServerCommand();
	~CServerCommand();

	// Client methods
	BOOL SendCommand(pServerCommandData pCmd, DWORD msgLength=0, BYTE* message=NULL);
	BOOL GetResponse(pServerCommandData pRsp);

	// Server methods
	BOOL GetCommand(pServerCommandData pCmd, pServerMessageData pMsg);
	BOOL SendResponse(pServerCommandData pRsp);

	BOOL WaitforServerPipeAccess() { return m_ServerPipeMutex.Wait(); }
	BOOL ReleaseServerPipeAccess() { return m_ServerPipeMutex.Release(); }

	pServerProcessData GetProcessPtr() { return (pServerProcessData)m_ServerProcessData.GetDataPtr(); }

private:
	pServerCommandData GetDataPtr() { return (pServerCommandData)m_ServerCommandData.GetDataPtr(); }
	pServerMessageData GetMsgPtr() { return (pServerMessageData)m_ServerMessageData.GetDataPtr(); }

private:
	// Server Commands/Responses
	BOOL WaitForServerCommandAccess() { return m_ServerCommandMutex.Wait(); };
	BOOL ReleaseServerCommandAccess() { return m_ServerCommandMutex.Release(); };
//	BOOL WaitForServerCommandAccess() { return TRUE; };
//	BOOL ReleaseServerCommandAccess() { return TRUE; };
	CServerCommandData m_ServerCommandData;
	CServerMessageData m_ServerMessageData;
	CServerProcessData m_ServerProcessData;

	Mutex m_ServerCommandMutex;
	Mutex m_ServerPipeMutex;

	// client
	void SetCommandReady();
	// server
	void SetResponseReady();
	HANDLE m_hServerCommandReadyEvent;
	HANDLE m_hServerResponseReadyEvent;
};

#endif// __SERVERCLIENT_H__
