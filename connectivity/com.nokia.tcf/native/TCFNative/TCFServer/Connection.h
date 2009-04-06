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
// Connection.h: interface for the CConnection class.
// Implemented by CConnectionImpl
//
// one of these per connection
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_CONNECTION_H__C7E97807_97B0_4BF7_AEC7_FA246A751509__INCLUDED_)
#define AFX_CONNECTION_H__C7E97807_97B0_4BF7_AEC7_FA246A751509__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "ServerClient.h"
#include "TCErrorConstants.h"
#include "Client.h"
#include "Registry.h"
#include "BaseCom.h"
#include <vector>
#include <time.h>

// basename is suffixed with connection ID to make it unique per CConnection
#define MESSAGEPROCESSOR_EXITEVENT_BASENAME	"TCFServerMessageProcessorExittedEvent"
#define MESSAGEPROCESSOR_STOPEVENT_BASENAME	"TCFServerMessageProcessorStoppedEvent"
#define MESSAGEPROCESSOR_STARTEVENT_BASENAME	"TCFServerMessageProcessorStartedEvent"
#define MESSAGEPROCESSOR_EVENTWAIT_TIMEOUT	60000L

typedef std::vector<CClient*> ClientList;

// m_MessageProcessorState states
#define MP_NONE (0)
#define MP_EXIT (1)
#define MP_START (2)
#define MP_PAUSE (3)
#define MP_PROCESSING (4)


#define FLUSH_TIME		(100)		// 100 ms
class CConnection  
{
public:
	CConnection();
	CConnection(ConnectData conData, DWORD connectionId);
	virtual ~CConnection();

	virtual BOOL IsEqual(CConnection* connection)=0;
	virtual BOOL IsEqual(pConnectData pConData)=0;
	virtual long GetConnectionId() { return m_ConnectionID; }
	virtual BOOL ExitProcessing()=0;				// exit processing thread
	virtual long DoDisconnect()=0;
	virtual void NotifyClientsCommError(long tcfError, bool passOsError=false, DWORD osError=0)=0;
	long m_ConnectionID;			// id for this connection

	ConnectData* m_ConnectSettings;	// connection settings
};

#endif // !defined(AFX_CONNECTION_H__C7E97807_97B0_4BF7_AEC7_FA246A751509__INCLUDED_)
