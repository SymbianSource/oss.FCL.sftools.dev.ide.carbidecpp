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
// ConnectionImpl.cpp: implementation of the CConnectionImpl class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "ConnectionImpl.h"
#include "RegistryImpl.h"
#include "ServerManager.h"

#ifdef _DEBUG
extern BOOL gDoLogging;
#endif

#define LOG_CONNECTION
#if defined(LOG_CONNECTION) && defined(_DEBUG)
#define TCDEBUGOPEN() if (gDoLogging) { this->m_DebugLog->WaitForAccess(); }
#define TCDEBUGLOGS(s) if (gDoLogging) { sprintf(this->m_DebugLogMsg,"%s", s); this->m_DebugLog->log(this->m_DebugLogMsg); }
#define TCDEBUGLOGA1(s, a1) if (gDoLogging) { sprintf(this->m_DebugLogMsg, s, a1); this->m_DebugLog->log(this->m_DebugLogMsg); }
#define TCDEBUGLOGA2(s, a1, a2) if (gDoLogging) { sprintf(this->m_DebugLogMsg, s, a1, a2); this->m_DebugLog->log(this->m_DebugLogMsg); }
#define TCDEBUGLOGA3(s, a1, a2, a3) if (gDoLogging) { sprintf(this->m_DebugLogMsg, s, a1, a2, a3); this->m_DebugLog->log(this->m_DebugLogMsg); }
#define TCDEBUGLOGA4(s, a1, a2, a3, a4) if (gDoLogging) { sprintf(this->m_DebugLogMsg, s, a1, a2, a3, a4); this->m_DebugLog->log(this->m_DebugLogMsg); }
#define TCDEBUGCLOSE() if (gDoLogging) { this->m_DebugLog->ReleaseAccess(); }
#else
#define TCDEBUGOPEN()
#define TCDEBUGLOGS(s)
#define TCDEBUGLOGA1(s, a1)
#define TCDEBUGLOGA2(s, a1, a2)
#define TCDEBUGLOGA3(s, a1, a2, a3)
#define TCDEBUGLOGA4(s, a1, a2, a3, a4)
#define TCDEBUGCLOSE()
#endif

//#define LOG_MPROCESSOR

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

CConnectionImpl::CConnectionImpl()
{
	m_ConnectSettings = NULL;
	m_ClientList = NULL;
	m_Status = eDisconnected;
	m_Registry = NULL;
	m_ConnectionID = 0;
	m_OsError = 0;
	m_BaseComm = NULL;
	m_BaseProtocol = NULL;
	m_BaseCommHandle = NULL;
	m_BaseProtocolHandle = NULL;

	// message processing thread flags and handles
	m_MessageProcessorState = MP_NONE;
	m_ExitMessageProcessor = false;
	m_PauseMessageProcessing = false;
	m_StartMessageProcessing = false;
	m_hMessageProcessorExittedEvent = NULL;
	m_hMessageProcessorStoppedEvent = NULL;
	m_hMessageProcessorStartedEvent = NULL;
	m_hMessageProcessorThread = NULL;
	m_dwMessageProcessorThreadId = 0;

	m_NextRetryTime = m_RetryTimeoutTime = 0;

	m_NextFlushFileTime = 0;
}

CConnectionImpl::CConnectionImpl(ConnectData conData, DWORD connectionId)
{
#ifdef _DEBUG
	if (gDoLogging)
	{
#if defined(LOG_CONNECTION)
		m_DebugLog = new TCDebugLog("TCF_ConnectionLog", connectionId);
#else
		m_DebugLog = NULL;
#endif
#if defined(LOG_MPROCESSOR)
		m_DebugLog2 = new TCDebugLog("TCF_ProcessorLog", connectionId);
#else
		m_DebugLog2 = NULL;
#endif
	}
	else
	{
		m_DebugLog = NULL;
		m_DebugLog2 = NULL;
	}
#else
	m_DebugLog = NULL;
	m_DebugLog2 = NULL;
#endif

	TCDEBUGOPEN();
	TCDEBUGLOGA1("CConnectionImpl::CConnectionImpl id = %d\n", connectionId);

	m_ConnectSettings = new ConnectData();

	memcpy(m_ConnectSettings, &conData, sizeof(ConnectData));

	m_ClientList = new ClientList();
	m_ClientList->clear();
	m_Status = eDisconnected;
	m_Registry = new CRegistryImpl(connectionId);
	m_ConnectionID = connectionId;
	m_BaseComm = NULL;
	m_BaseProtocol = NULL;
	m_BaseCommHandle = NULL;
	m_BaseProtocolHandle = NULL;

	// message processing thread flags and handles
	m_MessageProcessorState = MP_NONE;
	m_ExitMessageProcessor = false;
	m_PauseMessageProcessing = false;
	m_StartMessageProcessing = false;

	// create named events
	char eventName[100];

	sprintf(eventName, "%s%d", MESSAGEPROCESSOR_EXITEVENT_BASENAME, connectionId);
	m_hMessageProcessorExittedEvent = ::CreateEvent(NULL, FALSE, FALSE, eventName);

	sprintf(eventName, "%s%d", MESSAGEPROCESSOR_STOPEVENT_BASENAME, connectionId);
	m_hMessageProcessorStoppedEvent = ::CreateEvent(NULL, FALSE, FALSE, eventName);

	sprintf(eventName, "%s%d", MESSAGEPROCESSOR_STARTEVENT_BASENAME, connectionId);
	m_hMessageProcessorStartedEvent = ::CreateEvent(NULL, FALSE, FALSE, eventName);

	m_hMessageProcessorThread = NULL;
	m_dwMessageProcessorThreadId = 0;

	m_NextRetryTime = m_RetryTimeoutTime = 0;

	m_NextFlushFileTime = 0;
	m_OsError = 0;

	TCDEBUGCLOSE();
}
CConnectionImpl::~CConnectionImpl()
{
	TCDEBUGOPEN();
	TCDEBUGLOGS("CConnectionImpl::~CConnectionImpl\n");

	// terminate the message processor thread if running

	if (m_hMessageProcessorThread != NULL)
	{
		BOOL t = ::TerminateThread(m_hMessageProcessorThread, 0);
		::CloseHandle(m_hMessageProcessorThread);
	}

	if (m_hMessageProcessorExittedEvent != NULL)
	{
		::CloseHandle(m_hMessageProcessorExittedEvent);
	}

	if (m_hMessageProcessorStoppedEvent != NULL)
	{
		::CloseHandle(m_hMessageProcessorStoppedEvent);
	}

	if (m_hMessageProcessorStartedEvent != NULL)
	{
		::CloseHandle(m_hMessageProcessorStartedEvent);
	}

	if (m_ConnectSettings)
		delete m_ConnectSettings;


	if (m_ClientList)
	{
		m_ClientList->clear();
		delete m_ClientList;
	}

	if (m_Registry)
	{
		delete m_Registry;
	}

	if (m_BaseComm)
	{
		delete m_BaseComm;
	}

	if (m_BaseCommHandle)
	{
		::FreeLibrary(m_BaseCommHandle);
	}
	if (m_BaseProtocol)
	{
		delete m_BaseProtocol;
	}

	if (m_BaseProtocolHandle)
	{
		::FreeLibrary(m_BaseProtocolHandle);
	}

	TCDEBUGCLOSE();
	if (m_DebugLog)
		delete m_DebugLog;
	if (m_DebugLog2)
		delete m_DebugLog2;

}

BOOL CConnectionImpl::IsEqual(CConnection* connection)
{
	TCDEBUGOPEN();
	TCDEBUGLOGS("CConnectionImpl::IsEqual\n");

	BOOL equal = FALSE;

	if (strcmp(m_ConnectSettings->connectType, connection->m_ConnectSettings->connectType) == 0)
	{
		if (m_BaseComm)
		{
			if (m_BaseComm->IsConnectionEqual(connection->m_ConnectSettings))
			{
				equal = TRUE;
			}
		}
		else
		{
			equal = TRUE;
		}
	}

	TCDEBUGCLOSE();
	return equal;
}

BOOL CConnectionImpl::IsEqual(pConnectData pConData)
{
	TCDEBUGOPEN();
	TCDEBUGLOGS("CConnectionImpl::IsEqual\n");

	BOOL equal = FALSE;

	if (strcmp(m_ConnectSettings->connectType, pConData->connectType) == 0)
	{
		if (m_BaseComm)
		{
			if (m_BaseComm->IsConnectionEqual(pConData))
			{
				equal = TRUE;
			}
		}
		else
		{
			equal = TRUE;
		}
	}
	TCDEBUGCLOSE();
	return equal;
}

long CConnectionImpl::DoConnect()
{
	TCDEBUGOPEN();
	TCDEBUGLOGS("CConnectionImpl::DoConnect\n");

	long ret = TCAPI_ERR_NONE;

	if (m_BaseComm && m_BaseProtocol)
	{
		ret = m_BaseComm->OpenPort();
		if (ret != TCAPI_ERR_NONE)
		{
			m_OsError = m_BaseComm->m_lastCommError;
			TCDEBUGLOGA1(" m_BaseComm->OpenPort = %d\n", ret);
		}
	}
	else
	{
		ret = TCAPI_ERR_UNKNOWN_MEDIA_TYPE;
	}

	if (ret == TCAPI_ERR_NONE)
	{
		m_Status = eConnected;

		TCDEBUGCLOSE();

		StartProcessing();
	}
	else
	{
//		if (m_BaseComm != NULL)
//		{
//			delete m_BaseComm;
//			m_BaseComm = NULL;
//		}
		TCDEBUGCLOSE();
	}
	return ret;
}

long CConnectionImpl::DoDisconnect()
{
	TCDEBUGOPEN();
	TCDEBUGLOGS("CConnectionImpl::DoDisconnect\n");

	long ret = TCAPI_ERR_NONE;
	if (IsConnected())
	{
		ret = m_BaseComm->ClosePort();
//		delete m_BaseComm;
//		m_BaseComm = NULL;
	}
	m_Status = eDisconnected;

	TCDEBUGCLOSE();
	return ret;
}

BOOL CConnectionImpl::AddClient(CClient* client)
{
	TCDEBUGOPEN();
	TCDEBUGLOGS("CConnectionImpl::AddClient\n");

	BOOL ok = TRUE;

	m_ClientList->push_back(client);

	TCDEBUGCLOSE();
	return ok;
}

long CConnectionImpl::DoSendMessage(long encodeOption, BYTE protocolVersion, BOOL useMsgId, BYTE msgId, DWORD msgLength, BYTE* pMsg)
{
	TCDEBUGOPEN();
	TCDEBUGLOGS("CConnectionImpl::DoSendMessage\n");

	long err = TCAPI_ERR_NONE;
	if (IsRetryInProgress())
	{
		err = TCAPI_ERR_COMM_RETRY_IN_PROGRESS;
	}
	else if (IsRetryTimedOut())
	{
		err = TCAPI_ERR_COMM_TIMEOUT;
	}
	else if (m_Status == eConnected)
	{
		BYTE* encodedMessage = new BYTE[msgLength + 40]; // add enough for header (msgLength may be 0)
		// if msgLength == 0, then encodeOption SHOULD be ENCODE_FORMAT since com expects to send something!
		if (encodeOption == ENCODE_FORMAT)
		{
#ifdef _DEBUG
			char msg[200]; msg[0] = '\0';
			int len = (msgLength > 30) ? 30 : msgLength;
			for (int i = 0; i < len; i ++)
			{
				sprintf(msg, "%s%02.2x ", msg, pMsg[i]);
			}
			sprintf(msg, "%s\n", msg);
			TCDEBUGLOGS(msg);
#endif
			// msgLength maybe 0 and pMsg maybe NULL (we're not sending a raw message, just a protocol header)
			msgLength = m_BaseProtocol->EncodeMessage(pMsg, msgLength, protocolVersion, msgId, encodedMessage, msgLength+40);
#ifdef _DEBUG
			msg[0] = '\0';
			len = (msgLength > 30) ? 30 : msgLength;
			for (i = 0; i < len; i ++)
			{
				sprintf(msg, "%s%02.2x ", msg, encodedMessage[i]);
			}
			sprintf(msg, "%s\n", msg);
			TCDEBUGLOGS(msg);
#endif
			err = m_BaseComm->SendDataToPort(msgLength, encodedMessage);
		}
		else
		{
#ifdef _DEBUG
			char msg[200]; msg[0] = '\0';
			int len = (msgLength > 30) ? 30 : msgLength;
			for (int i = 0; i < len; i ++)
			{
				sprintf(msg, "%s%02.2x ", msg, pMsg[i]);
			}
			sprintf(msg, "%s\n", msg);
			TCDEBUGLOGS(msg);
#endif
			// msgLength != 0 and pMsg != NULL
			err = m_BaseComm->SendDataToPort(msgLength, pMsg);
		}
		delete[] encodedMessage;

		TCDEBUGLOGS("CConnectionImpl::DoSendMessage done\n");
		if (err != TCAPI_ERR_NONE)
		{
//			EnterRetryPeriod(err, true, m_BaseComm->m_lastCommError);
			HandleFatalPortError(err, true, m_BaseComm->m_lastCommError);
			m_OsError = m_BaseComm->m_lastCommError;
		}
	}
	else
	{
		err = TCAPI_ERR_MEDIA_NOT_OPEN;
	}

	TCDEBUGLOGA1("CConnectionImpl::DoSendMessage err = %d\n", err);
	TCDEBUGCLOSE();
	return err;
}

long CConnectionImpl::DoRetryProcessing()
{
	long err = TCAPI_ERR_NONE;

	// if not connected
	//   return no error
	if (m_BaseComm == NULL /*|| m_BaseComm->IsConnected() == false*/)
		return TCAPI_ERR_MEDIA_NOT_OPEN;

	// if retry not in progress && retry not timed out
	//   return no error
	if (!IsRetryInProgress() && !IsRetryTimedOut())
		return TCAPI_ERR_NONE;

//	TCDEBUGOPEN();
//	TCDEBUGLOGS("CConnectionImpl::DoRetryProcessing\n");
//	TCDEBUGCLOSE();
	// if retry timeout flag already set
	//   return timeout error
	if (IsRetryTimedOut())
		return TCAPI_ERR_COMM_TIMEOUT;

	// get current time
	time_t ctime;
	time(&ctime);
	// if retry timeout period has expired
	if (ctime >= m_RetryTimeoutTime)
	{
		TCDEBUGOPEN();
		TCDEBUGLOGS("CConnectionImpl::DoRetryProcessing retry timeout\n");
		TCDEBUGCLOSE();
		// send timeout error to all clients
		NotifyClientsCommError(TCAPI_ERR_COMM_TIMEOUT);
		// close comm port
		m_BaseComm->ClosePort();
		// set retry timeout flag
		SetRetryTimedOut();
		// return retry timeout error
		err = TCAPI_ERR_COMM_TIMEOUT;
	}
	// else if retry time has passed
	else if (ctime >= m_NextRetryTime)
	{
		TCDEBUGOPEN();
		TCDEBUGLOGS("CConnectionImpl::DoRetryProcessing retry time\n");
		TCDEBUGCLOSE();
		// close comm port
		// reopen comm port
		m_BaseComm->ClosePort();
		int openErr = m_BaseComm->OpenPort();
		// if comm error
		if (openErr != TCAPI_ERR_NONE)
		{
			// set next retry time
			// return comm error
			m_NextRetryTime = ctime + m_ConnectSettings->retryInterval;
			err = TCAPI_ERR_COMM_RETRY_IN_PROGRESS;
			m_OsError = m_BaseComm->m_lastCommError;
		}
		else
		{
		TCDEBUGOPEN();
		TCDEBUGLOGS("CConnectionImpl::DoRetryProcessing reconnected\n");
		TCDEBUGCLOSE();
			// send reconnect warning to all clients
			NotifyClientsCommError(TCAPI_INFO_COMM_RECONNECTED);
			// set connected
			SetConnected();
			err = TCAPI_ERR_NONE;
		}
	} 
	else // still in retry
	{
		err = TCAPI_ERR_COMM_RETRY_IN_PROGRESS;
	}


//	TCDEBUGOPEN();
//	TCDEBUGLOGA1("CConnectionImpl::DoRetryProcessing err = %d\n", err);
//	TCDEBUGCLOSE();
	return err;
}
long CConnectionImpl::EnterRetryPeriod(long commErr, bool passOsErr, DWORD osErr)
{
	TCDEBUGOPEN();
	TCDEBUGLOGA3("CConnectionImpl::EnterRetryPeriod commErr=%d passOsErr=%d osErr=%d\n", commErr, passOsErr, osErr);
	TCDEBUGCLOSE();

	long err = TCAPI_ERR_NONE;

	// set next retry time
	time_t ctime;
	time(&ctime);
	m_NextRetryTime = ctime + m_ConnectSettings->retryInterval;
	// set retry timeout time
	m_RetryTimeoutTime = ctime + m_ConnectSettings->retryTimeout;
	// send comm error to all clients
	NotifyClientsCommError(commErr, passOsErr, osErr);
	// set retry in progress flag
	SetRetryInProgress();

	return err;
}

BOOL CConnectionImpl::RemoveClient(CClient* client)
{
	TCDEBUGOPEN();
	TCDEBUGLOGS("CConnectionImpl::RemoveClient\n");

	BOOL found = FALSE;

	if (m_ClientList->size() != 0)
	{
		ClientList::iterator iter;
		for (iter = m_ClientList->begin(); iter != m_ClientList->end(); iter++)
		{
			if ((*iter)->GetClientId() == client->GetClientId())
			{
				m_ClientList->erase(iter);
				found = TRUE;
				break;
			}
		}
	}

	TCDEBUGCLOSE();
	return found;
}

BOOL CConnectionImpl::ExitProcessing()
{
	TCDEBUGOPEN();
	TCDEBUGLOGS("CConnectionImpl::ExitProcessing\n");

	// exit the messageprocessing thread
	if (m_hMessageProcessorThread != NULL)
	{
		m_MessageProcessorState = MP_EXIT;

		m_StartMessageProcessing = false;
		m_PauseMessageProcessing = true;
		m_ExitMessageProcessor = true;
		DWORD waitStatus = ::WaitForSingleObject(m_hMessageProcessorExittedEvent, MESSAGEPROCESSOR_EVENTWAIT_TIMEOUT);
		TCDEBUGLOGA1("CConnectionImpl::ExitProcessing waitStatus=%x\n", waitStatus);
		::CloseHandle(m_hMessageProcessorThread);
		m_hMessageProcessorThread = NULL;
	}
	
	TCDEBUGCLOSE();
	return TRUE;
}

BOOL CConnectionImpl::StartProcessing()
{
	TCDEBUGOPEN();
	TCDEBUGLOGS("CConnectionImpl::StartProcessing\n");

	// starts processing thread
	if (m_hMessageProcessorThread == NULL)
	{
		m_MessageProcessorState = MP_PAUSE;

		m_ExitMessageProcessor = false;
		m_StartMessageProcessing = false;
		m_PauseMessageProcessing = false;
		// TODO: create thread
		m_hMessageProcessorThread = ::CreateThread(
			NULL,
			0,
			(LPTHREAD_START_ROUTINE) MessageProcessor,
			this,
			0,
			&m_dwMessageProcessorThreadId);
	}

	TCDEBUGCLOSE();
	return PauseProcessing();//RestartProcessing();
}

BOOL CConnectionImpl::PauseProcessing()
{
	TCDEBUGOPEN();
	TCDEBUGLOGS("CConnectionImpl::PauseProcessing\n");

	// tells the processing thread to pause
	if (m_hMessageProcessorThread != NULL)
	{
		m_MessageProcessorState = MP_PAUSE;

		m_ExitMessageProcessor = false;
		m_StartMessageProcessing = false;
		m_PauseMessageProcessing = true;
		DWORD waitStatus = ::WaitForSingleObject(m_hMessageProcessorStoppedEvent, MESSAGEPROCESSOR_EVENTWAIT_TIMEOUT);
		TCDEBUGLOGA1("CConnectionImpl::PauseProcessing waitStatus=%x\n", waitStatus);
	}
	
	TCDEBUGCLOSE();
	return TRUE;
}

BOOL CConnectionImpl::RestartProcessing()
{
	TCDEBUGOPEN();
	TCDEBUGLOGS("CConnectionImpl::RestartProcessing\n");

	// tell the processing thread to restart
	if (m_hMessageProcessorThread != NULL)
	{
		m_MessageProcessorState = MP_START;

		m_ExitMessageProcessor = false;
		m_StartMessageProcessing = true;
		m_PauseMessageProcessing = false;
		DWORD waitStatus = ::WaitForSingleObject(m_hMessageProcessorStartedEvent, MESSAGEPROCESSOR_EVENTWAIT_TIMEOUT);
		TCDEBUGLOGA1("CConnectionImpl::RestartProcessing waitStatus=%x\n", waitStatus);
	}
	
	TCDEBUGCLOSE();
	return TRUE;
}

BOOL CConnectionImpl::RemoveClientFromRegistry(CClient* client)
{
	TCDEBUGOPEN();
	TCDEBUGLOGS("CConnectionImpl::RemoveClientFromRegistry\n");
	TCDEBUGCLOSE();

	return m_Registry->RemoveClient(client);
}

BOOL CConnectionImpl::AddClientToRegistry(CClient* client, long numberIds, BYTE* ids)
{
	TCDEBUGOPEN();
	TCDEBUGLOGS("CConnectionImpl::AddClientToRegistry\n");
	TCDEBUGCLOSE();

	return m_Registry->AddClient(client, numberIds, ids);
}

long CConnectionImpl::HandleFatalPortError(long err, bool passOsErr, DWORD osErr)
{
	TCDEBUGOPEN();
	TCDEBUGLOGA3("CConnectionImpl::HandleFatalPortError err=%d passOsErr=%d osErr=%d\n", err, passOsErr, osErr);
	TCDEBUGCLOSE();

	m_BaseComm->ClosePort();
	m_Status = eDisconnected;

	NotifyClientsCommError(err);

	return TCAPI_ERR_NONE;
}
void CConnectionImpl::NotifyClientsCommError(long tcfError, bool passOsError, DWORD osError)
{
//	TCDEBUGOPEN();
//	TCDEBUGLOGS("CConnectionImpl::NotifyClientsCommError\n");
//	TCDEBUGCLOSE();

	if (m_ClientList->size() != 0)
	{
		ClientList::iterator iter;
		for (iter = m_ClientList->begin(); iter != m_ClientList->end(); iter++)
		{
			CErrorMonitor* errorMonitor = (*iter)->m_ErrorMonitor;
			errorMonitor->PutError(tcfError, passOsError, osError);
		}
	}
}
BOOL CConnectionImpl::HasVersion()
{
	BOOL found = FALSE;

	if (m_BaseComm && m_BaseComm->HasVersion())
		found = TRUE;

	return found;
}
void CConnectionImpl::GetVersion(char* version)
{
	if (HasVersion()) {
		m_BaseComm->GetVersion(version);
	}
}

void CConnectionImpl::UnLockAllDestinations()
{
	if (m_ClientList->size() != 0)
	{
		ClientList::iterator iter;
		for (iter = m_ClientList->begin(); iter != m_ClientList->end(); iter++)
		{
			CInputStream* inputStream = (*iter)->m_InputStream;
			CMessageFile* file = (*iter)->m_MessageFile;
			if (inputStream != NULL) 
			{
				inputStream->UnLockStream();
			}
			else if (file != NULL)
			{
				file->UnLockMessageFile();
			}
		}
	}
}

#if defined(LOG_MPROCESSOR) && defined(_DEBUG)
#define MPLOGOPEN() if (gDoLogging) { pThis->m_DebugLog2->WaitForAccess(); }
#define MPLOGS(s) if (gDoLogging) { sprintf(pThis->m_DebugLogMsg2,"%s", s); pThis->m_DebugLog2->log(pThis->m_DebugLogMsg2); }
#define MPLOGA1(s, a1) if (gDoLogging) { sprintf(pThis->m_DebugLogMsg2, s, a1); pThis->m_DebugLog2->log(pThis->m_DebugLogMsg2); }
#define MPLOGA2(s, a1, a2) if (gDoLogging) { sprintf(pThis->m_DebugLogMsg2, s, a1, a2); pThis->m_DebugLog2->log(pThis->m_DebugLogMsg2); }
#define MPLOGA3(s, a1, a2, a3) if (gDoLogging) { sprintf(pThis->m_DebugLogMsg2, s, a1, a2, a3); pThis->m_DebugLog2->log(pThis->m_DebugLogMsg2); }
#define MPLOGCLOSE() if (gDoLogging) { pThis->m_DebugLog2->ReleaseAccess(); }
#else
#define MPLOGOPEN()
#define MPLOGS(s)
#define MPLOGA1(s, a1)
#define MPLOGA2(s, a1, a2)
#define MPLOGA3(s, a1, a2, a3)
#define MPLOGCLOSE()
#endif

DWORD WINAPI CConnectionImpl::MessageProcessor(LPVOID lpParam)
{
	CConnectionImpl* pThis = (CConnectionImpl*)lpParam;

	MPLOGOPEN();
	MPLOGS("MessageProcessor start thread\n");

	bool processing = false;
	long err = TCAPI_ERR_NONE;
	DWORD pollSize = 0;

	while (pThis->m_MessageProcessorState != MP_EXIT)
	{
		if (pThis->m_MessageProcessorState == MP_PAUSE)
		{
			MPLOGS("MessageProcessor pause\n");

			processing = false; 
			pThis->m_PauseMessageProcessing = false;
			pThis->m_MessageProcessorState = MP_NONE;
			BOOL ok = ::SetEvent(pThis->m_hMessageProcessorStoppedEvent);
		}

		if (pThis->IsRetryInProgress())
			err = pThis->DoRetryProcessing();
		else if (pThis->IsRetryTimedOut())
			err = TCAPI_ERR_COMM_TIMEOUT;

		if (processing && err == TCAPI_ERR_NONE)
		{
			if (pThis->m_BaseComm && pThis->m_BaseComm->IsConnected())
			{
				err = pThis->m_BaseComm->PollPort(pollSize);
				MPLOGA2("MessageProcessor PollPort = %d pollsize = %d\n", err, pollSize);
				if (err != TCAPI_ERR_NONE)
				{
					MPLOGA2("MessageProcessor  err = %d osError = %d\n", err, pThis->m_BaseComm->m_lastCommError);
//					pThis->EnterRetryPeriod(err, true, pThis->m_BaseComm->m_lastCommError);
					if (err == TCAPI_ERR_COMM_ERROR)
						pThis->HandleFatalPortError(err, true, pThis->m_BaseComm->m_lastCommError);
				}
				else
				{
					if (pollSize == 0)
					{
						Sleep(1);
					}
					else
					{
						long numberProcessed = 0;
//						MPLOGA1("MessageProcessor ProcessBuffer pRegistry = %x\n", pThis->m_Registry);
						err = pThis->m_BaseComm->ProcessBuffer(pThis, pThis->m_Registry, numberProcessed);

						MPLOGA2("MessageProcessor ProcessBuffer err = %d number = %d\n", err, numberProcessed);

						if (err == TCAPI_ERR_COMM_ERROR)
						{
							MPLOGA2("MessageProcessor  err = %d osError = %d\n", err, pThis->m_BaseComm->m_lastCommError);
							// for this error we have os error, but we probably caught this in PollPort already
//							pThis->EnterRetryPeriod(err, true, pThis->m_BaseComm->m_lastCommError);
							if (err == TCAPI_ERR_COMM_ERROR)
								pThis->HandleFatalPortError(err, true, pThis->m_BaseComm->m_lastCommError);
						}
						else if (err != TCAPI_ERR_NONE)
						{
							// all clients already notified in ProcessBuffer
							err = TCAPI_ERR_NONE;
						}
						pThis->UnLockAllDestinations(); // unlock all input streams, if they became locked during AddMessage()
//						Sleep(1);
					}
				}
//				MPLOGS("MessageProcessor FlushAllClientMessageFiles\n");
				pThis->FlushAllClientMessageFiles();
			}
			else
			{
				// basecom not connected
				Sleep(1);
			}
		}
		else
		{
			// processing is not being done
			Sleep(1);
		}
		if (pThis->m_MessageProcessorState == MP_START)
		{
			MPLOGS("MessageProcessor start\n");

			processing = true;
			pThis->m_StartMessageProcessing = false;
			pThis->m_MessageProcessorState = MP_PROCESSING;
			BOOL ok = ::SetEvent(pThis->m_hMessageProcessorStartedEvent);
		}
	}
	// signal we're stopping
	pThis->m_ExitMessageProcessor = false;
	pThis->m_MessageProcessorState = MP_NONE;
	::SetEvent(pThis->m_hMessageProcessorExittedEvent);

	MPLOGS("MessageProcessor exit thread\n");
	MPLOGCLOSE();

	return 0;
}

void CConnectionImpl::FlushAllClientMessageFiles()
{
	DWORD cTick = GetTickCount();

//	MPLOGA2("CConnectionImpl::FlushAllClientMessageFiles cTick=%d m_NextFlushFileTime=%d\n", cTick, m_NextFlushFileTime);

	if (cTick > m_NextFlushFileTime)
	{
//	MPLOGS("CConnectionImpl::FlushAllClientMessageFiles flush timeout\n");
		if (m_ClientList->size() != 0)
		{
			ClientList::iterator iter;
			for (iter = m_ClientList->begin(); iter != m_ClientList->end(); iter++)
			{
				CMessageFile* file = (*iter)->m_MessageFile;
				if (file != NULL)
				{
//	MPLOGS("CConnectionImpl::FlushAllClientMessageFiles flush client\n");
					file->FlushFile();
				}
			}
		}
		m_NextFlushFileTime = GetTickCount() + FLUSH_TIME;		
	}
}

BOOL CConnectionImpl::CreateCommProtocols(const char* commPath, const char* protPath)
{
	BOOL loaded = FALSE;

	TCDEBUGOPEN();
	TCDEBUGLOGS("CConnectionImpl::CreateCommProtocols\n");

	TCDEBUGLOGA2(" commPath=%s protPath=%s\n", commPath, protPath);

	m_BaseCommHandle = ::LoadLibrary(commPath);
	m_BaseProtocolHandle = ::LoadLibrary(protPath);
	if (m_BaseCommHandle == NULL || m_BaseProtocolHandle == NULL)
	{
		TCDEBUGLOGA2(" error loading library, m_BaseCommHandle=%x m_BaseProtocolHandle=%x\n", m_BaseCommHandle, m_BaseProtocolHandle);
		if (m_BaseCommHandle) ::FreeLibrary(m_BaseCommHandle); m_BaseCommHandle = NULL;
		if (m_BaseProtocolHandle) ::FreeLibrary(m_BaseProtocolHandle); m_BaseProtocolHandle = NULL;

	}
	else
	{
		COMMCREATE lpCommFn = (COMMCREATE)::GetProcAddress(m_BaseCommHandle, COMMCREATE_FNNAME);
		PROTOCOLCREATE lpProtFn = (PROTOCOLCREATE)::GetProcAddress(m_BaseProtocolHandle, PROTOCOLCREATE_FNNAME);
		if (lpCommFn == NULL || lpProtFn == NULL)
		{
			TCDEBUGLOGA2(" error finding function, lpCommFn=%x lpProtFn=%x\n", lpCommFn, lpProtFn);
			if (m_BaseCommHandle) ::FreeLibrary(m_BaseCommHandle); m_BaseCommHandle = NULL;
			if (m_BaseProtocolHandle) ::FreeLibrary(m_BaseProtocolHandle); m_BaseProtocolHandle = NULL;
		}
		else
		{
			m_BaseProtocol = lpProtFn();
			if (m_BaseProtocol == NULL)
			{
				TCDEBUGLOGA1(" error creating protocol, m_BaseProtocol=%x\n", m_BaseProtocol);
				if (m_BaseCommHandle) ::FreeLibrary(m_BaseCommHandle); m_BaseCommHandle = NULL;
				if (m_BaseProtocolHandle) ::FreeLibrary(m_BaseProtocolHandle); m_BaseProtocolHandle = NULL;
			}
			else
			{
				m_BaseComm = lpCommFn(m_ConnectSettings, m_ConnectionID, m_BaseProtocol);
				if (m_BaseComm == NULL)
				{
					TCDEBUGLOGA1(" error creating comm, m_BaseComm=%x\n", m_BaseComm);
					if (m_BaseProtocol) delete m_BaseProtocol; m_BaseProtocol = NULL;

					if (m_BaseCommHandle) ::FreeLibrary(m_BaseCommHandle); m_BaseCommHandle = NULL;
					if (m_BaseProtocolHandle) ::FreeLibrary(m_BaseProtocolHandle); m_BaseProtocolHandle = NULL;
				}
				else
				{
					loaded = TRUE;
					TCDEBUGLOGA4(" created class, m_BaseComm=%x m_BaseProtocol=%x m_BaseCommHandle=%x m_BaseProtocolHandle=%x\n", m_BaseComm, m_BaseProtocol, m_BaseCommHandle, m_BaseProtocolHandle);
				}
			}
		}
	}

	TCDEBUGCLOSE();
	return loaded;
}
