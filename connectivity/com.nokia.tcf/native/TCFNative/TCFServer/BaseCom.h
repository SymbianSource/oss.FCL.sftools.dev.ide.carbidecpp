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
#ifndef __BASECOM_H__
#define __BASECOM_H__
#include "Registry.h"
#include "ServerClient.h"
#include "TCConstants.h"
#include "TCErrorConstants.h"
#include "BaseProtocol.h"
#include "TCDebugLog.h"
#include <time.h>

#ifdef _DEBUG
extern BOOL gDoLogging;
#endif

#define LOG_COMM
#if defined(LOG_COMM) && defined(_DEBUG)

#define COMMLOGOPEN() if (gDoLogging) { if (m_CommDebugLog) m_CommDebugLog->WaitForAccess(); }
#define COMMLOGS(s) if (gDoLogging) { sprintf(m_CommDebugLogMsg,"%s", s); if (m_CommDebugLog) m_CommDebugLog->log(m_CommDebugLogMsg); }
#define COMMLOGA1(s, a1) if (gDoLogging) { sprintf(m_CommDebugLogMsg, s, a1); if (m_CommDebugLog) m_CommDebugLog->log(m_CommDebugLogMsg); }
#define COMMLOGA2(s, a1, a2) if (gDoLogging) { sprintf(m_CommDebugLogMsg, s, a1, a2); if (m_CommDebugLog) m_CommDebugLog->log(m_CommDebugLogMsg); }
#define COMMLOGA3(s, a1, a2, a3) if (gDoLogging) { sprintf(m_CommDebugLogMsg, s, a1, a2, a3); if (m_CommDebugLog) m_CommDebugLog->log(m_CommDebugLogMsg); }
#define COMMLOGA4(s, a1, a2, a3, a4) if (gDoLogging) { sprintf(m_CommDebugLogMsg, s, a1, a2, a3, a4); if (m_CommDebugLog) m_CommDebugLog->log(m_CommDebugLogMsg); }
#define COMMLOGCLOSE() if (gDoLogging) { if (m_CommDebugLog) m_CommDebugLog->ReleaseAccess(); }
#else
#define COMMLOGOPEN()
#define COMMLOGS(s)
#define COMMLOGA1(s, a1)
#define COMMLOGA2(s, a1, a2)
#define COMMLOGA3(s, a1, a2, a3)
#define COMMLOGA4(s, a1, a2, a3, a4)
#define COMMLOGCLOSE()
#endif

#define LOG_PROCCOMM
#if defined(LOG_PROCCOMM) && defined(_DEBUG)
#define PROCLOGOPEN() if (gDoLogging) { m_ProcDebugLog->WaitForAccess(); }
#define PROCLOGS(s) if (gDoLogging) { sprintf(m_ProcDebugLogMsg,"%s", s); m_ProcDebugLog->log(m_ProcDebugLogMsg); }
#define PROCLOGA1(s, a1) if (gDoLogging) { sprintf(m_ProcDebugLogMsg, s, a1); m_ProcDebugLog->log(m_ProcDebugLogMsg); }
#define PROCLOGA2(s, a1, a2) if (gDoLogging) { sprintf(m_ProcDebugLogMsg, s, a1, a2); m_ProcDebugLog->log(m_ProcDebugLogMsg); }
#define PROCLOGA3(s, a1, a2, a3) if (gDoLogging) { sprintf(m_ProcDebugLogMsg, s, a1, a2, a3); m_ProcDebugLog->log(m_ProcDebugLogMsg); }
#define PROCLOGA4(s, a1, a2, a3, a4) if (gDoLogging) { sprintf(m_ProcDebugLogMsg, s, a1, a2, a3, a4); m_ProcDebugLog->log(m_ProcDebugLogMsg); }
#define PROCLOGA5(s, a1, a2, a3, a4, a5) if (gDoLogging) { sprintf(m_ProcDebugLogMsg, s, a1, a2, a3, a4, a5); m_ProcDebugLog->log(m_ProcDebugLogMsg); }
#define PROCLOGCLOSE() if (gDoLogging) { m_ProcDebugLog->ReleaseAccess(); }
#else
#define PROCLOGOPEN()
#define PROCLOGS(s)
#define PROCLOGA1(s, a1)
#define PROCLOGA2(s, a1, a2)
#define PROCLOGA3(s, a1, a2, a3)
#define PROCLOGA4(s, a1, a2, a3, a4)
#define PROCLOGA5(s, a1, a2, a3, a4, a5)
#define PROCLOGCLOSE()
#endif

class CConnection;
class CBaseProtocol;

class CBaseCom
{
public:
	CBaseCom();
	CBaseCom(ConnectData* connectSettings, DWORD connectionId, CBaseProtocol* protocol);
	virtual ~CBaseCom();

//	void SetConnectSettings(ConnectData* connectSettings);
//	void SetProtocol(CBaseProtocol* protocol) { m_Protocol = protocol; }
//	void SetConnectionId(DWORD id);

	virtual long OpenPort()=0;
	virtual long ClosePort()=0;
	virtual long SendDataToPort(DWORD inSize, const void* inData)=0;
	virtual long PollPort(DWORD& outSize)=0;
	virtual long ReadPort(DWORD inSize, void* outData, DWORD& outSize)=0;
	virtual long ProcessBuffer(CConnection* pConn, CRegistry* pRegistry, long& numberProcessed)=0;
	virtual void DeleteMsg(DWORD inMsgLength)=0;
	virtual bool IsConnected() { return m_isConnected; }
	virtual bool GetVersion(char* outVersion)=0; // get version of whatever we're connected to
	virtual bool HasVersion()=0; // does this connection have a version?
	virtual long PreProcessMessage(int inMsgType, DWORD inMsgLength, BYTE* inMessage)=0;
	virtual long PreProcessMessage(BYTE msgId, DWORD inMsgLength, BYTE* inMessage)=0;
	virtual bool IsConnectionEqual(ConnectData* pConn)=0;

	BYTE* m_pBuffer;
	DWORD m_numberBytes;
	bool m_isConnected;
	DWORD m_connId;

	ConnectData* m_ConnectSettings; // from connection
	CBaseProtocol* m_Protocol;		// used for this connection

	// for Open/Close/Send thread
	char m_CommDebugLogMsg[2000];
	TCDebugLog* m_CommDebugLog;

	// for Poll/Read/Process/PreProcess thread
	char m_ProcDebugLogMsg[2000];
	TCDebugLog* m_ProcDebugLog;

	DWORD m_lastCommError;
};

typedef const char* (*COMMREGISTER)(void);
typedef CBaseCom* (*COMMCREATE)(ConnectData* connectSettings, DWORD connectionId, CBaseProtocol* protocol);

#define COMMREGISTER_FNNAME	"RegisterComm"
#define COMMCREATE_FNNAME	"CreateComm"

#define COMMDLL_BASENAME	"TCFComm"

#endif __BASECOM_H__
