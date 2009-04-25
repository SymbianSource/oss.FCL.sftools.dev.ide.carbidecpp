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
// Client.h: interface for the CClient class.
//
// one of these per client
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_CLIENT_H__6BC8ADEC_683A_4924_ABD0_28B449E927C6__INCLUDED_)
#define AFX_CLIENT_H__6BC8ADEC_683A_4924_ABD0_28B449E927C6__INCLUDED_

#include "ServerClient.h"
#include "InputStream.h"
#include "MessageFile.h"
#include "ErrorMonitorData.h"
#include "TCConstants.h"

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

class CConnection;

class CClient  
{
public:
	CClient();
	CClient(CConnection* connection, ClientOptions& options, DWORD clientId);
	virtual ~CClient();

	void Start() { m_Status = eStarted; }
	void Stop() { m_Status = eStopped; }

	BOOL IsStarted() { return (m_Status == eStarted); }
	DWORD GetClientId() { return m_ClientId; }
	CConnection* GetConnection() { return m_Connection; }
	BOOL OpenStream(DestinationOptions* streamOptions);
	BOOL CloseStream();
	BOOL IsStreamOpen() { return (m_InputStream != NULL); }
	CInputStream* GetInputStream() { return m_InputStream; }

	BOOL OpenMessageFile(DestinationOptions* messageFileOptions);
	BOOL CloseMessageFile();
	BOOL ClearMessageFile();
	BOOL IsMessageFileOpen() { return (m_MessageFile != NULL); }
	CMessageFile* GetMessageFile() { return m_MessageFile; }

	eMessageDestination m_MessageDestination;
	CMessageFile* m_MessageFile;
	CInputStream* m_InputStream;
	CErrorMonitor* m_ErrorMonitor;
	CConnection* m_Connection;
	ClientOptions m_Options;
	eClientStatus m_Status;
	DWORD m_ClientId;
};

#endif // !defined(AFX_CLIENT_H__6BC8ADEC_683A_4924_ABD0_28B449E927C6__INCLUDED_)
