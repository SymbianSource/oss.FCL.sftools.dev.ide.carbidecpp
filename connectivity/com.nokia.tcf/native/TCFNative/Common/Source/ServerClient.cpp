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
#include "ServerClient.h"
#include "TCErrorConstants.h"
#include <time.h>

#ifdef TCF_CLIENT
#include "..\..\TCFClient\ClientManager.h"
extern CClientManager* gManager;
#endif
#ifdef TCF_SERVER
#include "..\..\TCFServer\ServerManager.h"
extern CServerManager* gManager;
#endif

#ifdef _DEBUG
extern BOOL gDoLogging;
#endif

//#define LOG_SERVERCLIENT
#if defined(LOG_SERVERCLIENT) && defined(_DEBUG)
extern BOOL gDoLogging;
extern char TCDebugMsg[];
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

CServerCommand::CServerCommand()
{
	// Server commands/responses
	m_ServerCommandMutex.Open(SERVERCOMMANDDATA_MUTEX_NAME, SERVERCOMMANDDATA_MUTEX_TIMEOUT);
	m_ServerCommandData.Open(SERVERCOMMANDDATA_MAP_SIZE, SERVERCOMMANDDATA_MAP_NAME);
	m_ServerCommandData.Init();
	m_ServerMessageData.Open(SERVERMESSAGEDATA_MAP_SIZE, SERVERMESSAGEDATA_MAP_NAME);
	m_ServerMessageData.Init();

	m_ServerProcessData.Open(SERVERPROCESSDATA_MAP_SIZE, SERVERPROCESSDATA_MAP_NAME);
	m_ServerProcessData.Init();

	// General server access
	m_ServerPipeMutex.Open(SERVERPIPE_MUTEX_NAME, SERVERPIPE_MUTEX_TIMEOUT);

	// command/response events
	m_hServerCommandReadyEvent = ::CreateEvent(NULL, FALSE, FALSE, SERVER_COMMAND_READY_EVENTNAME);
	m_hServerResponseReadyEvent = ::CreateEvent(NULL, FALSE, FALSE, SERVER_RESPONSE_READY_EVENTNAME);
}

CServerCommand::~CServerCommand()
{
	m_ServerCommandMutex.Close(); 
	m_ServerCommandData.Close();
	m_ServerMessageData.Close();

	m_ServerProcessData.Close();

	m_ServerPipeMutex.Close();

	::CloseHandle(m_hServerCommandReadyEvent);
	::CloseHandle(m_hServerResponseReadyEvent);

}

// Client methods
BOOL CServerCommand::SendCommand(pServerCommandData pCmd, DWORD msgLength, BYTE* message)
{
	BOOL sent = FALSE;

//	if (pCmd == NULL) return sent;

	TCDEBUGOPEN();
	TCDEBUGLOGS("CServerCommand::SendCommand\n");
	TCDEBUGCLOSE();

	WaitForServerCommandAccess();
	
	pServerCommandData pData1 = GetDataPtr();

	if (pCmd->command != eCmdNone)
	{
		memcpy(pData1, pCmd, sizeof(ServerCommandData));
		if (msgLength > 0 && message != NULL)
		{
			pServerMessageData pData2 = GetMsgPtr();
			pData2->length = msgLength;
			if (msgLength > 0)
				memcpy(pData2->message, message, msgLength);
		}
		pData1->response = eRspNone; // setup for response
		sent = TRUE;
		::SetEvent(m_hServerCommandReadyEvent);
	}

	ReleaseServerCommandAccess();
	TCDEBUGOPEN();
	TCDEBUGLOGS("CServerCommand::SendCommand done\n");
	TCDEBUGCLOSE();

	return sent;
}

BOOL CServerCommand::GetResponse(pServerCommandData pRsp)
{
	BOOL found = FALSE;

	TCDEBUGOPEN();
	TCDEBUGLOGS("CServerCommand::GetResponse\n");
	TCDEBUGCLOSE();

	if (::WaitForSingleObject(m_hServerResponseReadyEvent, SERVER_CMDRSP_EVENT_TIMEOUT) == WAIT_OBJECT_0)
	{
		WaitForServerCommandAccess();
		pServerCommandData pData = GetDataPtr();
		if (pData->response != eRspNone)
		{
			// response is ready when command is cleared
			memcpy(pRsp, pData, sizeof(ServerCommandData));
			found = TRUE;
		}
		ReleaseServerCommandAccess();
	}
	else
	{
		TCDEBUGLOGS("CServerCommand::GetResponse timeout\n");
		pRsp->response = eRspError;
		pRsp->error = TCAPI_ERR_COMM_SERVER_RESPONSE_TIMEOUT;
	}
#if (0)

	BOOL timeoutoccurred = FALSE;
	pServerCommandData pData = GetDataPtr();
	time_t ctime = time(NULL);
	time_t timeout = ctime + 60; // wait 60 seconds only

//	if (pRsp == NULL) return found;

	TCDEBUGOPEN();
	TCDEBUGLOGA2("CServerCommand::GetResponse time = %d timeout = %d\n", ctime, timeout);
	TCDEBUGCLOSE();

	while(!found && !timeoutoccurred)
	{
		WaitForServerCommandAccess();
		if (pData->response != eRspNone)
		{
			// response is ready when command is cleared
			memcpy(pRsp, pData, sizeof(ServerCommandData));
			found = TRUE;
		}
		else
		{
			time_t ctime = time(NULL);
			if (ctime >= timeout)
			{
				TCDEBUGLOGS("CServerCommand::GetResponse timeout\n");
				pRsp->response = eRspError;
				pRsp->error = TCAPI_ERR_COMM_SERVER_RESPONSE_TIMEOUT;
				timeoutoccurred = TRUE;
			}
		}
		ReleaseServerCommandAccess();
		Sleep(1);
	}
#endif
	TCDEBUGOPEN();
	TCDEBUGLOGA1("CServerCommand::GetResponse waiting for response found=%d\n", found);
	TCDEBUGCLOSE();

	return found;
}
// Server methods
BOOL CServerCommand::GetCommand(pServerCommandData pCmd, pServerMessageData pMsg)
{
	BOOL found = FALSE;

	if (::WaitForSingleObject(m_hServerCommandReadyEvent, SERVER_CMDRSP_EVENT_TIMEOUT) == WAIT_OBJECT_0)
	{
		pServerCommandData pData1 = GetDataPtr();

		WaitForServerCommandAccess();
		if (pData1->command != eCmdNone)
		{
			memcpy(pCmd, pData1, sizeof(ServerCommandData));
			if (pMsg)
			{
				pServerMessageData pData2 = GetMsgPtr();
				pMsg->length = pData2->length;
				memcpy(pMsg->message, pData2->message, pData2->length);
			}
			found = TRUE;
		}
		ReleaseServerCommandAccess();
	}

	return found;
}

BOOL CServerCommand::SendResponse(pServerCommandData pRsp)
{
	BOOL sent = FALSE;
	pServerCommandData pData = GetDataPtr();

	WaitForServerCommandAccess();
	if (pRsp->response != eRspNone)
	{
		memcpy(pData, pRsp, sizeof(ServerCommandData));
		pData->command = eCmdNone; // setup for next command
		sent = TRUE;
		::SetEvent(m_hServerResponseReadyEvent);
	}

	ReleaseServerCommandAccess();

	return sent;
}
