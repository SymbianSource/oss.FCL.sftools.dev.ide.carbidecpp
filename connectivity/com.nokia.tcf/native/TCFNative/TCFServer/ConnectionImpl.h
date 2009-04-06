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
// ConnectionImpl.h: interface for the CConnectionImpl class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_CONNECTIONIMPL_H__1D0D5B50_3DDD_49B8_834B_5996D9CC0124__INCLUDED_)
#define AFX_CONNECTIONIMPL_H__1D0D5B50_3DDD_49B8_834B_5996D9CC0124__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "Connection.h"

class CConnectionImpl : public CConnection  
{
public:
	CConnectionImpl();
	CConnectionImpl(ConnectData conData, DWORD connectionId);
	virtual ~CConnectionImpl();

	BOOL IsEqual(CConnection* connection);
	BOOL IsEqual(pConnectData pConData);

	BOOL AddClient(CClient* client);	// add a client to this connection
	BOOL RemoveClient(CClient* client);	// remove a client from this connection
	BOOL AddClientToRegistry(CClient* client, long numberIds, BYTE* ids); 
	BOOL RemoveClientFromRegistry(CClient* client); // remove a client from this registry
	BOOL StartClient(CClient* client){ return TRUE; }	// start processing for a specified client
	BOOL StopClient(CClient* client){ return TRUE; }	// stop processing for a specified client
	long DoConnect();
	long DoDisconnect();
	long DoSendMessage(long encodeOption, BYTE protocolVersion, BOOL useMsgId, BYTE msgId, DWORD msgLength, BYTE* msg);
	long DoRetryProcessing();
	long EnterRetryPeriod(long err, bool passOsErr, DWORD osErr);

	BOOL PauseProcessing();				// pause processing thread (not exit)
	BOOL RestartProcessing();			// restart processing after a pause
	BOOL StartProcessing();				// start processing thread
	BOOL ExitProcessing();				// exit processing thread
	BOOL IsProcessingPaused() { return (m_MessageProcessorState == MP_PAUSE); }
	BOOL IsProcessingStarted() { return (m_MessageProcessorState == MP_START); }
	BOOL IsProcessingContinuing() { return (m_MessageProcessorState == MP_PROCESSING); }

	long GetConnectionId() { return m_ConnectionID; }

	// m_Status operations
	BOOL IsConnected() { return ((m_Status == eConnected) || (m_Status == eRetryInProgress)); }
	BOOL IsDisconnected() { return (m_Status == eDisconnected); }
	BOOL IsRetryInProgress() { return (m_Status== eRetryInProgress); }
	BOOL IsRetryTimedOut() { return (m_Status == eRetryTimedOut); }
	void SetConnected() { m_Status = eConnected; }
	void SetDisconnected() { m_Status = eDisconnected; }
	void SetRetryInProgress() { m_Status = eRetryInProgress; }
	void SetRetryTimedOut() { m_Status = eRetryTimedOut; }
	void NotifyClientsCommError(long tcfError, bool passOsError=false, DWORD osError=0);
	BOOL CreateCommProtocols(const char* commLibraryPath, const char* protocolLibraryPath);
	BOOL HasVersion();
	void GetVersion(char* outVersion);
	
	long GetNumberClients() { return m_ClientList->size(); }

	void UnLockAllDestinations();
	void FlushAllClientMessageFiles();

	ConnectData* m_ConnectSettings;	// connection settings
	ClientList* m_ClientList;		// this connection only
	eConnectionStatus m_Status;		// this connection status
	CRegistry* m_Registry;			// registry for this connection
	long m_ConnectionID;			// id for this connection

	CBaseCom* m_BaseComm;			// communication port handler
	HINSTANCE m_BaseCommHandle;
	CBaseProtocol* m_BaseProtocol;	// protocol for port
	HINSTANCE m_BaseProtocolHandle;

	unsigned long m_OsError;		// error from OS if applicable


	char m_DebugLogMsg[2000];
	TCDebugLog* m_DebugLog;
	char m_DebugLogMsg2[2000];
	TCDebugLog* m_DebugLog2;

	// MessageProcessor thread
	static DWORD WINAPI MessageProcessor(LPVOID lpParam);

	int m_MessageProcessorState;
	bool m_ExitMessageProcessor;		// flag to tell MessageProcessor thread to exit
	bool m_PauseMessageProcessing; // flag to tell MessageProcessor thread to stop all message processing (temporarily)
	bool m_StartMessageProcessing;	// flag to tell MessageProcessor thread to start message processing
	HANDLE m_hMessageProcessorExittedEvent; // event to tell main thread that MessageProcessor thread has exitted
	HANDLE m_hMessageProcessorStoppedEvent; // event to tell main thread that MessageProcessor thread has stopped processing
	HANDLE m_hMessageProcessorStartedEvent; // event to tell main thread that MessageProcessor thread has started processing
	
	HANDLE m_hMessageProcessorThread;	// handle to MessageProcessor thread
	DWORD m_dwMessageProcessorThreadId;	// ID for          "			"

	// com errors and retry stuff
	time_t m_NextRetryTime;
	time_t m_RetryTimeoutTime;

	// client flush times
	DWORD m_NextFlushFileTime;

};

#endif // !defined(AFX_CONNECTIONIMPL_H__1D0D5B50_3DDD_49B8_834B_5996D9CC0124__INCLUDED_)
