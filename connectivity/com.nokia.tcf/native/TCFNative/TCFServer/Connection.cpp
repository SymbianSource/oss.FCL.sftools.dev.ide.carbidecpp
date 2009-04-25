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
// Connection.cpp: implementation of the CConnection class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "Connection.h"
#include "ServerManager.h"

#ifdef _DEBUG
extern BOOL gDoLogging;
#define TCDEBUGOPEN() if (gDoLogging) { this->m_DebugLog->WaitForAccess(); }
#define TCDEBUGLOGS(s) if (gDoLogging) { sprintf(this->m_DebugLogMsg,"%s", s); this->m_DebugLog->log(this->m_DebugLogMsg); }
#define TCDEBUGLOGA1(s, a1) if (gDoLogging) { sprintf(this->m_DebugLogMsg, s, a1); this->m_DebugLog->log(this->m_DebugLogMsg); }
#define TCDEBUGLOGA2(s, a1, a2) if (gDoLogging) { sprintf(this->m_DebugLogMsg, s, a1, a2); this->m_DebugLog->log(this->m_DebugLogMsg); }
#define TCDEBUGLOGA3(s, a1, a2, a3) if (gDoLogging) { sprintf(this->m_DebugLogMsg, s, a1, a2, a3); this->m_DebugLog->log(this->m_DebugLogMsg); }
#define TCDEBUGCLOSE() if (gDoLogging) { this->m_DebugLog->ReleaseAccess(); }
#else
#define TCDEBUGOPEN()
#define TCDEBUGLOGS(s)
#define TCDEBUGLOGA1(s, a1)
#define TCDEBUGLOGA2(s, a1, a2)
#define TCDEBUGLOGA3(s, a1, a2, a3)
#define TCDEBUGCLOSE()
#endif

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
CConnection::CConnection()
{
}

CConnection::CConnection(ConnectData conData, DWORD connectionId)
{
}

CConnection::~CConnection()
{
}
