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

#include "stdafx.h"
#include "TCFCppApi.h"
#include "ServerClient.h"
#include "ClientManager.h"
#include "TCConstants.h"
#include <vector>

extern CClientManager* gManager;
#ifdef _DEBUG
extern BOOL gDoLogging;
extern char TCDebugMsg[100];
#define TCDEBUGOPEN() if (gDoLogging) { gManager->m_DebugLog->WaitForAccess(); }
#define TCDEBUGLOGS(s) if (gDoLogging) { sprintf(TCDebugMsg,"%s", s); gManager->m_DebugLog->log(TCDebugMsg); }
#define TCDEBUGLOGA1(s, a1) if (gDoLogging) { sprintf(TCDebugMsg, s, a1); gManager->m_DebugLog->log(TCDebugMsg); }
#define TCDEBUGLOGA2(s, a1, a2) if (gDoLogging) { sprintf(TCDebugMsg, s, a1, a2); gManager->m_DebugLog->log(TCDebugMsg); }
#define TCDEBUGLOGA3(s, a1, a2, a3) if (gDoLogging) { sprintf(TCDebugMsg, s, a1, a2, a3); gManager->m_DebugLog->log(TCDebugMsg); }
#define TCDEBUGCLOSE() if (gDoLogging) { gManager->m_DebugLog->ReleaseAccess(); }
#else
#define TCDEBUGOPEN()
#define TCDEBUGLOGS(s)
#define TCDEBUGLOGA1(s, a1)
#define TCDEBUGLOGA2(s, a1, a2)
#define TCDEBUGLOGA3(s, a1, a2, a3)
#define TCDEBUGCLOSE()
#endif

// for internal testing with 2.x USB TRK define this
//#define FOR_2X_USB

// client Ids connected for this C++ process
static std::vector<long> *pcppClientIds = NULL;

static long CheckClient(long id);
static long CheckConnection(pTCFCppConnectData inConnection);
static long CheckRealSerialSettings(pTCFCppConnectData inConnection);
static long CheckMessageOptions(pTCFCppMessageOptions inMessageOptions);
static long CheckMessageIds(pTCFCppMessageIds inMessageIds);
static long CheckMessage(pTCFCppMessage inMessage);
static void ConvertRealSerialSettingsToServer(pTCFCppConnectData inConnection, pRealSerialConnectData pData); 
static void ConvertRealSerialSettingsToHost(pTCFCppConnectData inConnection, pRealSerialConnectData pData); 

TCF_EXP long TCF_CALL TCFConnect(pTCFCppConnectData inConnection, pTCFCppMessageOptions inMessageOptions, pTCFCppMessageIds inMessageIds, long* outClientId)
{
	long ret = TCAPI_ERR_NONE;
	ServerCommandData cmdrsp;
	pServerCommandData pCmdrsp = &cmdrsp;

	TCDEBUGOPEN();
	TCDEBUGLOGS("TCFConnect\n");
//	if (!gManager->IsServerRunning())
//		return TCAPI_ERR_COMM_SERVER_RESPONSE_TIMEOUT;

	gManager->m_Server->WaitforServerPipeAccess();

	ret = CheckConnection(inConnection);
#ifdef FOR_2X_USB
	inMessageOptions->unWrapFormat = eTCFCppNone;
#endif
	if (ret == TCAPI_ERR_NONE)
	{
		ret = CheckMessageOptions(inMessageOptions);
	}

	if (ret == TCAPI_ERR_NONE)
	{
		ret = CheckMessageIds(inMessageIds);
	}

	if (ret == TCAPI_ERR_NONE)
	{
		if (strcmp(inConnection->connectType, "tcp") == 0)
		{
			char* pAddress = inConnection->tcpSettings.ipAddress;
			char* pPort = inConnection->tcpSettings.ipPort;

			pCmdrsp->command = eCmdConnect;
			strncpy(pCmdrsp->connectSettings.connectType, inConnection->connectType, MAX_CONNECTION_TYPE);
			strncpy(pCmdrsp->connectSettings.tcpSettings.ipAddress, pAddress, MAX_IPADDRESS_SIZE);
			strncpy(pCmdrsp->connectSettings.tcpSettings.ipPort, pPort, MAX_PORT_SIZE);

			pCmdrsp->connectSettings.retryInterval = inConnection->retryInterval;
			pCmdrsp->connectSettings.retryTimeout = inConnection->retryTimeout;
			strncpy(pCmdrsp->connectSettings.decodeFormat, inConnection->decodeFormat, MAX_DECODE_FORMAT);
			pCmdrsp->clientOptions.unWrapFormat = inMessageOptions->unWrapFormat;
			pCmdrsp->clientOptions.ostVersion = inMessageOptions->ostVersion;
		}
		else if (strcmp(inConnection->connectType, "virtualserial") == 0)
		{
			char* pComPort = inConnection->virtualSerialSettings.comPort;

			pCmdrsp->command = eCmdConnect;
			strncpy(pCmdrsp->connectSettings.connectType, inConnection->connectType, MAX_CONNECTION_TYPE);
			strncpy(pCmdrsp->connectSettings.virtualSerialSettings.comPort, pComPort, MAX_COMPORT_SIZE);

			pCmdrsp->connectSettings.retryInterval = inConnection->retryInterval;
			pCmdrsp->connectSettings.retryTimeout = inConnection->retryTimeout;
			strncpy(pCmdrsp->connectSettings.decodeFormat, inConnection->decodeFormat, MAX_DECODE_FORMAT);
			pCmdrsp->clientOptions.unWrapFormat = inMessageOptions->unWrapFormat;
			pCmdrsp->clientOptions.ostVersion = inMessageOptions->ostVersion;
		}
		else if (strcmp(inConnection->connectType, "serial") == 0)
		{
			char* pComPort = inConnection->realSerialSettings.comPort;

			pCmdrsp->command = eCmdConnect;
			strncpy(pCmdrsp->connectSettings.connectType, inConnection->connectType, MAX_CONNECTION_TYPE);
			strncpy(pCmdrsp->connectSettings.realSerialSettings.comPort, pComPort, MAX_COMPORT_SIZE);

			ConvertRealSerialSettingsToServer(inConnection, &pCmdrsp->connectSettings.realSerialSettings);

			pCmdrsp->connectSettings.retryInterval = inConnection->retryInterval;
			pCmdrsp->connectSettings.retryTimeout = inConnection->retryTimeout;
			strncpy(pCmdrsp->connectSettings.decodeFormat, inConnection->decodeFormat, MAX_DECODE_FORMAT);
			pCmdrsp->clientOptions.unWrapFormat = inMessageOptions->unWrapFormat;
			pCmdrsp->clientOptions.ostVersion = inMessageOptions->ostVersion;
		}
		else
		{
			// Add other connections here
		}
		// send connect command
		long id = 0;

		if (ret == TCAPI_ERR_NONE)
		{
			gManager->m_Server->SendCommand(pCmdrsp);
			gManager->m_Server->GetResponse(pCmdrsp);
			if (pCmdrsp->response == eRspError)
			{
				ret = pCmdrsp->error;
				TCDEBUGLOGA1("TCFConnect eCmdConnect: ret = %d\n", ret);
			}
			else
			{
				id = pCmdrsp->clientId;
				TCDEBUGLOGA1("TCFConnect eCmdConnect: id = %d\n", id);
			}
		}
		
		// send message Ids to capture
		if (ret == TCAPI_ERR_NONE)
		{
			pCmdrsp->command = eCmdSetMessageIds;
			pCmdrsp->clientId = id;
			pCmdrsp->number = inMessageIds->numberIds;
			for (int i = 0; i < inMessageIds->numberIds; i++)
			{
				pCmdrsp->messageIds[i] = inMessageIds->messageIds[i];
			}

			gManager->m_Server->SendCommand(pCmdrsp);
			gManager->m_Server->GetResponse(pCmdrsp);
			if (pCmdrsp->response == eRspError)
			{
				ret = pCmdrsp->error;
				TCDEBUGLOGA1("TCFConnect eCmdSetMessageIds: ret = %d\n", ret);
			}
			else
			{
				TCDEBUGLOGA1("TCFConnect eCmdSetMessageIds: ret = %d\n", ret);
			}
		}

		// create input stream overflow temp file
		// create input stream
		if (ret == TCAPI_ERR_NONE)
		{
//			eTCPCppStreamOverflowOption overflowOption = inMessageOptions->streamOverflowOption;
//			char* pFileName = inMessageOptions->overflowFile;
			long streamSize = inMessageOptions->inputStreamSize;
			CInputStream* stream = new CInputStream(NULL, streamSize, eTCPCppStreamOverflowOff, id);
			stream->CreateStream();
			gManager->AddInputStream(stream);

			pCmdrsp->command = eCmdOpenStream;
			pCmdrsp->clientId = id;
			// TODO: implement message file in the future?
			pCmdrsp->destinationOptions.destination = eDestinationInputStream;
			pCmdrsp->destinationOptions.streamSize = streamSize;
			pCmdrsp->destinationOptions.overFlowToFile = FALSE;//(overflowOption == eTCPCppStreamOverflowOn);
//			if (pFileName != NULL)
//			{
//				strncpy(pCmdrsp->destinationOptions.destinationFile, pFileName, MAX_INPUTSTREAMPATH);
//			}
//			else
			{
				pCmdrsp->destinationOptions.destinationFile[0] = NULL;
			}
			gManager->m_Server->SendCommand(pCmdrsp);
			gManager->m_Server->GetResponse(pCmdrsp);

			if (pCmdrsp->response == eRspError)
			{
				ret = pCmdrsp->error;
				TCDEBUGLOGA1("TCFConnect eCmdOpenStream: ret = %d\n", ret);
			}
			else
			{
				TCDEBUGLOGA1("TCFConnect eCmdOpenStream: ret = %d\n", ret);
			}
		}

		// create error monitor
		if (ret == TCAPI_ERR_NONE)
		{
			CErrorMonitor* monitor = new CErrorMonitor(id);
			if (monitor != NULL)
			{
				monitor->CreateData();
				gManager->AddErrorMonitor(monitor);
			}
		}

		// start client capture
		if (ret == TCAPI_ERR_NONE)
		{
			pCmdrsp->command = eCmdStart;
			pCmdrsp->clientId = id;
			gManager->m_Server->SendCommand(pCmdrsp);
			gManager->m_Server->GetResponse(pCmdrsp);

			if (pCmdrsp->response == eRspError)
			{
				ret = pCmdrsp->error;
				TCDEBUGLOGA1("TCFConnect eCmdStart: ret = %d\n", ret);
			}
			else
			{
				TCDEBUGLOGA1("TCFConnect eCmdStart: ret = %d\n", ret);
			}
		}

		if (ret == TCAPI_ERR_NONE)
		{
			TCDEBUGLOGA1("TCFConnect pcppClientIds: %x\n", pcppClientIds); 
			if (pcppClientIds == NULL)
			{
				pcppClientIds = new std::vector<long>;
				pcppClientIds->empty();
			}
			pcppClientIds->push_back(id);
			*outClientId = id;
			TCDEBUGLOGA1("TCFConnect pcppClientIds: size = %d\n", pcppClientIds->size());
		}
	}

	gManager->m_Server->ReleaseServerPipeAccess();
	TCDEBUGLOGA1("TCFConnect return ret=%d\n", ret);
	TCDEBUGCLOSE();
	return ret;
}


TCF_EXP long TCF_CALL TCFDisconnect(long inClientId)
{
	long ret = TCAPI_ERR_NONE;
	ServerCommandData cmdrsp;
	pServerCommandData pCmdrsp = &cmdrsp;

	TCDEBUGOPEN();
	TCDEBUGLOGA1("TCFDisconnect id=%d\n", inClientId);

	gManager->m_Server->WaitforServerPipeAccess();
	gManager->WaitForErrorMonitorListAccess();
	gManager->WaitForStreamListAccess();

	// check client ID
	ret = CheckClient(inClientId);
	TCDEBUGLOGA1("TCFDisconnect CheckClient: ret = %d\n", ret);
	
	// stop client
	if (ret == TCAPI_ERR_NONE)
	{
		pCmdrsp->clientId = inClientId;
		pCmdrsp->command = eCmdStop;
		gManager->m_Server->SendCommand(pCmdrsp);
		gManager->m_Server->GetResponse(pCmdrsp);
	}

	// close error monitor
	TCDEBUGLOGS("TCFDisconnect clear error monitor\n");
	CErrorMonitor *monitor = gManager->FindErrorMonitor(inClientId);
	if (monitor != NULL)
	{
		gManager->RemoveErrorMonitor(monitor);
		delete monitor;
	}

	// close input stream
	TCDEBUGLOGS("TCFDisconnect remove input stream\n");
	CInputStream* inputStream = gManager->FindInputStream(inClientId);
	if (inputStream != NULL)
	{
		gManager->RemoveInputStream(inputStream);
		delete inputStream;
	}

	// send disconnect
	TCDEBUGLOGS("TCFDisconnect send disconnect\n");
	if (ret == TCAPI_ERR_NONE)
	{
		pCmdrsp->command = eCmdDisconnect;
		pCmdrsp->clientId = inClientId;

		gManager->m_Server->SendCommand(pCmdrsp);
		gManager->m_Server->GetResponse(pCmdrsp);
		if (pCmdrsp->response == eRspError)
		{
			ret = pCmdrsp->error;
		}
	}
	
	TCDEBUGLOGS("TCFDisconnect clear erase id\n");
	if (pcppClientIds != NULL && ret == TCAPI_ERR_NONE)
	{
		std::vector<long>::iterator idIter;
		for (idIter = pcppClientIds->begin(); idIter != pcppClientIds->end(); idIter++)
		{
			if (*idIter == inClientId)
			{
				pcppClientIds->erase(idIter);
				break;
			}
		}
		if (pcppClientIds->size() == 0)
		{
			pcppClientIds->empty();
			delete pcppClientIds;
			pcppClientIds = NULL;
		}
	}

	gManager->ReleaseStreamListAccess();
	gManager->ReleaseErrorMonitorListAccess();

	TCDEBUGLOGS("TCFDisconnect stop server\n");
	gManager->m_Server->ReleaseServerPipeAccess();

	TCDEBUGLOGA1("TCFDisconnect return ret=%d\n", ret);
	TCDEBUGCLOSE();
	return ret;
}

TCF_EXP long TCF_CALL TCFGetVersions(long inClientId, long& outNumberVersions, char** outVersions)
{
	long ret = TCAPI_ERR_FEATURE_NOT_IMPLEMENTED;
	outNumberVersions = 0;

	// check client ID

	// get # versions from server

	// get version[i] from server


	return ret;
}
TCF_EXP long TCF_CALL TCFGetConnections(long& outNumberConnections, pTCFCppConnectData* outConnections)
{
	long ret = TCAPI_ERR_FEATURE_NOT_IMPLEMENTED;

	outNumberConnections = 0;

	return ret;
}
TCF_EXP long TCF_CALL TCFSendMessage(long inClientId, pTCFCppMessage inMessage, long inLength, BYTE* inData)
{
	long ret = TCAPI_ERR_NONE;
	ServerCommandData cmdrsp; pServerCommandData pCmdrsp = &cmdrsp;

#ifdef FOR_2X_USB
	inMessage->useMyId = FALSE;
#endif

	TCDEBUGOPEN();
	TCDEBUGLOGA1("TCFSendMessage id=%d\n", inClientId);

	// check client ID
	ret = CheckClient(inClientId);
	TCDEBUGLOGA1("TCFSendMessage CheckClient: ret=%d\n", ret);

	gManager->m_Server->WaitforServerPipeAccess();
	// send message to server
	if (ret == TCAPI_ERR_NONE)
	{
		ret = CheckMessage(inMessage);
		if (ret == TCAPI_ERR_NONE)
		{
			pCmdrsp->command = eCmdSendMessage;
			pCmdrsp->clientId = inClientId;
			pCmdrsp->encodeOption = (inMessage->encodeFormat == eTCFCppEncodeNone) ? ENCODE_NO_FORMAT : ENCODE_FORMAT;
			pCmdrsp->useMyId = inMessage->useMyId;
			pCmdrsp->protocolVersion = inMessage->ostVersion;
			pCmdrsp->myId = inMessage->myId;

			gManager->m_Server->SendCommand(pCmdrsp, inLength, inData);
			gManager->m_Server->GetResponse(pCmdrsp);

			if (pCmdrsp->response == eRspError)
			{
				ret = pCmdrsp->error;
				TCDEBUGLOGA1("TCFSendMessage eCmdSendMessage: ret=%d\n", ret);
			}
		}
	}

	gManager->m_Server->ReleaseServerPipeAccess();

	TCDEBUGLOGA1("TCFSendMessage return ret=%d\n", ret);
	TCDEBUGCLOSE();
	return ret;
}
TCF_EXP long TCF_CALL TCFStart(long inClientId)
{
	long ret = TCAPI_ERR_NONE;

	// check client Id
	ret = CheckClient(inClientId);
	if (ret == TCAPI_ERR_NONE)
	{
		gManager->m_Server->WaitforServerPipeAccess();

		ServerCommandData cmdrsp; pServerCommandData pCmdrsp = &cmdrsp;

		pCmdrsp->command = eCmdStart;
		pCmdrsp->clientId = inClientId;

		gManager->m_Server->SendCommand(pCmdrsp);
		gManager->m_Server->GetResponse(pCmdrsp);

		if (pCmdrsp->response == eRspError)
		{
			ret = pCmdrsp->error;
		}
		gManager->m_Server->ReleaseServerPipeAccess();

	}

	return ret;
}

TCF_EXP long TCF_CALL TCFStop(long inClientId)
{
	long ret = TCAPI_ERR_NONE;

	// check client Id
	ret = CheckClient(inClientId);
	if (ret == TCAPI_ERR_NONE)
	{
		gManager->m_Server->WaitforServerPipeAccess();

		ServerCommandData cmdrsp; pServerCommandData pCmdrsp = &cmdrsp;

		pCmdrsp->command = eCmdStop;
		pCmdrsp->clientId = inClientId;

		gManager->m_Server->SendCommand(pCmdrsp);
		gManager->m_Server->GetResponse(pCmdrsp);

		if (pCmdrsp->response == eRspError)
		{
			ret = pCmdrsp->error;
		}
		gManager->m_Server->ReleaseServerPipeAccess();

	}

	return ret;
}

TCF_EXP long TCF_CALL TCFSetMessageIds(long inClientId, pTCFCppMessageIds inMessageIds)
{
	long ret = TCAPI_ERR_NONE;
	ServerCommandData cmdrsp; pServerCommandData pCmdrsp = &cmdrsp;

	// check client Id
	ret = CheckClient(inClientId);

	// check message ids
	if (ret == TCAPI_ERR_NONE)
	{
		ret = CheckMessageIds(inMessageIds);
	}

	gManager->m_Server->WaitforServerPipeAccess();
	// check client status
	if (ret == TCAPI_ERR_NONE)
	{
		pCmdrsp->command = eCmdGetClientStatus;
		pCmdrsp->clientId = inClientId;

		gManager->m_Server->SendCommand(pCmdrsp);
		gManager->m_Server->GetResponse(pCmdrsp);

		if (pCmdrsp->response == eRspError)
		{
			ret = pCmdrsp->error;
		}
		else
		{
			if (pCmdrsp->clientStatus == eStarted)
			{
				ret = TCAPI_ERR_ROUTING_IN_PROGRESS;
			}
			else if (pCmdrsp->clientStatus == eUnknownClient)
			{
				ret = TCAPI_ERR_MEDIA_NOT_OPEN;
			}
		}
	}

	// set message ids
	if (ret == TCAPI_ERR_NONE)
	{
		pCmdrsp->command = eCmdSetMessageIds;
		pCmdrsp->clientId = inClientId;
		pCmdrsp->number = inMessageIds->numberIds;
		for (int i = 0; i < inMessageIds->numberIds; i++)
		{
			pCmdrsp->messageIds[i] = inMessageIds->messageIds[i];
		}
		gManager->m_Server->SendCommand(pCmdrsp);
		gManager->m_Server->GetResponse(pCmdrsp);

		if (pCmdrsp->response == eRspError)
		{
			ret = pCmdrsp->error;
		}

	}
	gManager->m_Server->ReleaseServerPipeAccess();

	return ret;
}

TCF_EXP long TCF_CALL TCFPollInputStream(long inClientId, long& outLength)
{
	long ret = TCAPI_ERR_NONE;
	outLength = 0;

	TCDEBUGOPEN();
	TCDEBUGLOGA1("TCFPollInputStream id=%d\n", inClientId);

	gManager->WaitForStreamListAccess();

	// check client ID
	ret = CheckClient(inClientId);
	if (ret == TCAPI_ERR_NONE)
	{
		// get client's input stream
		CInputStream* inputStream = gManager->FindInputStream(inClientId);

		if (inputStream != NULL)
		{
			outLength = inputStream->GetNextMessageSize();
		}
		else
		{
			ret = TCAPI_ERR_INPUTSTREAM_CLOSED;
		}
	}

	gManager->ReleaseStreamListAccess();

	TCDEBUGLOGA2("TCFPollInputStream return ret=%d outLength=%d\n", ret, outLength);
	TCDEBUGCLOSE();
	return ret;
}
TCF_EXP long TCF_CALL TCFReadInputStream(long inClientId, pTCFCppMessage outMessage, long& inLength, BYTE* outData)
{
	long ret = TCAPI_ERR_NONE;

	if (outData == NULL || inLength <= 0)
		return ret;

	gManager->WaitForStreamListAccess();

	// check client ID
	ret = CheckClient(inClientId);
	if (ret == TCAPI_ERR_NONE)
	{
		// get client's input stream
		CInputStream* inputStream = gManager->FindInputStream(inClientId);

		if (inputStream != NULL)
		{
			DWORD length = inputStream->GetNextMessageSize();
			if (length > inLength) length = inLength;
			if (length > 0)
			{
				inLength = length;
				inputStream->GetNextMessage(inLength, outData);

			}
			else
			{
				inLength = 0;
			}
		}
		else
		{
			ret = TCAPI_ERR_INPUTSTREAM_CLOSED;
		}
	}

	gManager->ReleaseStreamListAccess();

	return ret;
}
TCF_EXP BOOL TCF_CALL TCFPollError(long inClientId, int* outErrorCode, BOOL* outHasOSErrorCode, long* outOSErrorCode)
{
	BOOL foundError = FALSE;
	gManager->WaitForErrorMonitorListAccess();

	if (gManager->ErrorMonitorListSize() > 0)
	{
		CErrorMonitor* errorMonitor = gManager->FindErrorMonitor(inClientId);
		if (errorMonitor != NULL)
		{
			LONG tcfError = TCAPI_ERR_NONE;
			BOOL osErrorUsed = FALSE;
			DWORD osError = 0;
			BOOL found = errorMonitor->GetError(&tcfError, &osErrorUsed, &osError);
			if (found)
			{
				foundError = true;
				*outErrorCode = (int)tcfError;
				*outHasOSErrorCode = osErrorUsed;
				*outOSErrorCode = osError;
			}
		}
	}
	gManager->ReleaseErrorMonitorListAccess();

	return foundError;
}

long CheckClient(long id)
{
	long ret = TCAPI_ERR_NONE;
	BOOL found = FALSE;

	if (id <= 0)
	{
		ret = TCAPI_ERR_INVALID_HANDLE;
	}
	else if (gManager->IsServerRunning() == FALSE)
	{
		ret = TCAPI_ERR_MEDIA_NOT_OPEN;
	}
	else
	{
		if (pcppClientIds == NULL)
		{
			ret = TCAPI_ERR_MEDIA_NOT_OPEN;
		}
		else
		{
			std::vector<long>::iterator idIter;
			for (idIter = pcppClientIds->begin(); idIter != pcppClientIds->end(); idIter++)
			{
				if (*idIter == id)
				{
					found = TRUE;
					break;
				}
			}
			if (!found)
			{
				ret = TCAPI_ERR_INVALID_HANDLE;
			}
		}
	}

	return ret;
}
long CheckConnection(pTCFCppConnectData inConnection)
{
	long ret = TCAPI_ERR_NONE;

	if (inConnection == NULL)
	{
		ret = TCAPI_ERR_MISSING_CONNECTION_SPEC;
	} 
	else
	{
		long retryI = inConnection->retryInterval;
		long retryT = inConnection->retryTimeout;
		if (retryI == 0 || retryT == 0 || retryI > retryT)
			ret = TCAPI_ERR_INVALID_RETRY_PERIODS;
	}
	if (ret == TCAPI_ERR_NONE)
	{
		if (strcmp(inConnection->connectType, "tcp") == 0)
		{
			if (inConnection->tcpSettings.ipAddress == NULL)
			{
				ret = TCAPI_ERR_MISSING_MEDIA_DATA;
			}
			else if (inConnection->tcpSettings.ipPort == NULL)
			{
				ret = TCAPI_ERR_MISSING_MEDIA_DATA;
			}
		}
		else if (strcmp(inConnection->connectType, "virtualserial") == 0)
		{
			if (inConnection->virtualSerialSettings.comPort == NULL)
			{
				ret = TCAPI_ERR_MISSING_MEDIA_DATA;
			}
		}
		else if (strcmp(inConnection->connectType, "serial") == 0)
		{
			ret = CheckRealSerialSettings(inConnection);
		}
		else
		{
		}
	}

	return ret;
}

long CheckRealSerialSettings(pTCFCppConnectData inConnection)
{
	long ret = TCAPI_ERR_NONE;

	if (inConnection->realSerialSettings.comPort == NULL)
	{
		ret = TCAPI_ERR_MISSING_MEDIA_DATA;
	}
	else if (inConnection->realSerialSettings.dataBits < 4 || inConnection->realSerialSettings.dataBits > 8)
	{
		ret = TCAPI_ERR_COMM_INVALID_DATABITS;
	}
	else if (inConnection->realSerialSettings.baudRate < 110UL || inConnection->realSerialSettings.baudRate > 256000UL)
	{
		ret = TCAPI_ERR_MISSING_MEDIA_DATA;
	}
	else
	{
		switch(inConnection->realSerialSettings.stopBits)
		{
		case eTCFCppStopBits1:
		case eTCFCppStopBits15:
		case eTCFCppStopBits2:
			break;
		default:
			ret = TCAPI_ERR_COMM_INVALID_STOPBITS;
			break;
		}

	}
	if (ret == TCAPI_ERR_NONE)
	{
		switch(inConnection->realSerialSettings.flowControl)
		{
		case eTCFCppFlowControlNone:
		case eTCFCppFlowControlHW:
		case eTCFCppFlowControlSW:
			break;
		default:
			ret = TCAPI_ERR_COMM_INVALID_FLOWCONTROL;
			break;
		}
	}
	if (ret == TCAPI_ERR_NONE)
	{
		switch(inConnection->realSerialSettings.parity)
		{
		case eTCFCppParityNone:
		case eTCFCppParityOdd:
		case eTCFCppParityEven:
			break;
		default:
			ret = TCAPI_ERR_COMM_INVALID_PARITY;
			break;
 		}
	}
	
	return ret;
}
long CheckMessageOptions(pTCFCppMessageOptions inMessageOptions)
{
	long ret = TCAPI_ERR_NONE;

	if (inMessageOptions == NULL)
	{
		ret = TCAPI_ERR_MISSING_MESSAGE_OPTIONS;
	}
	else
	{
		if (ret == TCAPI_ERR_NONE)
		{
			if (inMessageOptions->unWrapFormat != eTCFCppNone && 
				inMessageOptions->unWrapFormat != eTCFCppDeleteHeader)
			{
				ret = TCAPI_ERR_INVALID_MESSAGE_UNWRAP_OPTION;
			}
		}
		if (ret == TCAPI_ERR_NONE)
		{
			if (inMessageOptions->inputStreamSize <= 0) 
			{
				ret = TCAPI_ERR_INVALID_STREAM_BUFFER_SIZE;
			}
		}
	}
	return ret;
}
long CheckMessageIds(pTCFCppMessageIds inMessageIds)
{
	long ret = TCAPI_ERR_NONE;

	if (inMessageIds == NULL)
	{
		ret = TCAPI_ERR_NO_MESSAGESIDS_REGISTERED;
	}
	else if (inMessageIds->numberIds <= 0 || inMessageIds->messageIds == NULL)
	{
		ret = TCAPI_ERR_NO_MESSAGESIDS_REGISTERED;
	}
	else if (inMessageIds->numberIds > 256)
	{
		ret = TCAPI_ERR_MESSAGEID_MAXIMUM;
	}

	return ret;
}
long CheckMessage(pTCFCppMessage inMessage)
{
	long ret = TCAPI_ERR_NONE;

	if (inMessage == NULL)
		return TCAPI_ERR_MISSING_MESSAGE;

	if (inMessage->useMyId)
	{
		if ((inMessage->encodeFormat != eTCFCppEncodeNone) && (inMessage->encodeFormat != eTCFCppEncode))
		{
			ret = TCAPI_ERR_INVALID_ENCODE_FORMAT;
		}
	}

	return ret;
}

void ConvertRealSerialSettingsToServer(pTCFCppConnectData inConnection, pRealSerialConnectData pData)
{
	pData->baudRate = inConnection->realSerialSettings.baudRate;
	pData->dataBits = inConnection->realSerialSettings.dataBits;
	switch(inConnection->realSerialSettings.flowControl)
	{
	default:
	case eTCFCppFlowControlNone:
		pData->flowControl = eFlowControlNone;
		break;
	case eTCFCppFlowControlHW:
		pData->flowControl = eFlowControlHW;
		break;
	case eTCFCppFlowControlSW:
		pData->flowControl = eFlowControlSW;
		break;
	}
	switch(inConnection->realSerialSettings.parity)
	{
	default:
	case eTCFCppParityNone:
		pData->parity = eParityNone;
		break;
	case eTCFCppParityOdd:
		pData->parity = eParityOdd;
		break;
	case eTCFCppParityEven:
		pData->parity = eParityEven;
		break;
	}
	switch(inConnection->realSerialSettings.stopBits)
	{
	default:
	case eTCFCppStopBits1:
		pData->stopBits = eStopBits1;
		break;
	case eTCFCppStopBits15:
		pData->stopBits = eStopBits15;
		break;
	case eTCFCppStopBits2:
		pData->stopBits = eStopBits2;
		break;
	}
}

void ConvertRealSerialSettingsToHost(pTCFCppConnectData inConnection, pRealSerialConnectData pData)
{
	inConnection->realSerialSettings.baudRate = pData->baudRate;
	inConnection->realSerialSettings.dataBits = pData->dataBits;
	switch(pData->flowControl)
	{
	default:
	case eFlowControlNone:
		inConnection->realSerialSettings.flowControl = eTCFCppFlowControlNone;
		break;
	case eFlowControlHW:
		inConnection->realSerialSettings.flowControl = eTCFCppFlowControlHW;
		break;
	case eFlowControlSW:
		inConnection->realSerialSettings.flowControl = eTCFCppFlowControlSW;
		break;
	}

	switch(pData->parity)
	{
	default:
	case eParityNone:
		inConnection->realSerialSettings.parity = eTCFCppParityNone;
		break;
	case eParityEven:
		inConnection->realSerialSettings.parity = eTCFCppParityEven;
		break;
	case eParityOdd:
		inConnection->realSerialSettings.parity = eTCFCppParityOdd;
		break;
	}

	switch(pData->stopBits)
	{
	default:
	case eStopBits1:
		inConnection->realSerialSettings.stopBits = eTCFCppStopBits1;
		break;
	case eStopBits15:
		inConnection->realSerialSettings.stopBits = eTCFCppStopBits15;
		break;
	case eStopBits2:
		inConnection->realSerialSettings.stopBits = eTCFCppStopBits2;
		break;
	}
}
