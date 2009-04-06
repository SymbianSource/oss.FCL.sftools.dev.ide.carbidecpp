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
// Client.cpp: implementation of the CClient class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "Client.h"
#include "ServerManager.h"

extern CServerManager* gManager;
#ifdef _DEBUG
extern BOOL gDoLogging;
#endif

//#define LOG_CLIENT
#if defined(LOG_CLIENT) && defined(_DEBUG)
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

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

CClient::CClient()
{
	TCDEBUGOPEN();
	TCDEBUGLOGS("CClient::CClient\n");
	
	m_InputStream = NULL;
	m_MessageFile = NULL;
	m_ErrorMonitor = NULL;
	m_Connection = NULL;
	m_Status = eStopped;
	m_ClientId = -1;
	m_Options.ostVersion = 1;
	m_Options.unWrapFormat = DEFAULT_UNWRAP_OPTION;
	m_MessageDestination = eDestinationInputStream;	// default - changed later

	TCDEBUGCLOSE();
}

CClient::CClient(CConnection* connection, ClientOptions& options, DWORD clientId)
{
	TCDEBUGOPEN();
	TCDEBUGLOGS("CClient::CClient\n");
	
	m_Connection = connection;
	m_Options.ostVersion = options.ostVersion;
	m_Options.unWrapFormat = options.unWrapFormat;
	m_ClientId = clientId;
	m_Status = eStopped;

	m_ErrorMonitor = new CErrorMonitor(m_ClientId);
	m_ErrorMonitor->CreateData();
	m_InputStream = NULL; // created on open instead
	m_MessageFile = NULL;
	m_MessageDestination = eDestinationInputStream;	// default - changed later


	TCDEBUGCLOSE();
}
CClient::~CClient()
{
	TCDEBUGOPEN();
	TCDEBUGLOGS("CClient::~CClient\n");
	
	if (m_ErrorMonitor)
		delete m_ErrorMonitor;

	if (m_InputStream)
		delete m_InputStream;

	if (m_MessageFile)
		delete m_MessageFile;

	TCDEBUGCLOSE();
}
BOOL CClient::OpenStream(DestinationOptions* streamOptions)
{
	TCDEBUGOPEN();
	TCDEBUGLOGS("CClient::OpenStream\n");
	
	BOOL done = TRUE;

	if (m_InputStream == NULL)
	{
		m_InputStream = new CInputStream(streamOptions->destinationFile, streamOptions->streamSize, streamOptions->overFlowToFile, m_ClientId);
		m_InputStream->CreateStream();
	}

	TCDEBUGCLOSE();
	return done;
}

BOOL CClient::CloseStream()
{
	TCDEBUGOPEN();
	TCDEBUGLOGS("CClient::CloseStream\n");
	
	BOOL done = TRUE;

	if (m_InputStream)
	{
		delete m_InputStream;
		m_InputStream = NULL;
	}

	TCDEBUGCLOSE();
	return done;
}
BOOL CClient::OpenMessageFile(DestinationOptions *messageFileOptions)
{
	BOOL done = TRUE;
	if (m_MessageFile == NULL)
	{
		m_MessageFile = new CMessageFile(messageFileOptions->destinationFile, m_ClientId);
		m_MessageFile->Open();
	}

	return done;
}
BOOL CClient::CloseMessageFile()
{
	BOOL done = TRUE;

	if (m_MessageFile)
	{
		delete m_MessageFile;
		m_MessageFile = NULL;
	}

	return done;
}
BOOL CClient::ClearMessageFile()
{
	BOOL done = TRUE;

	if (m_MessageFile)
	{
		m_MessageFile->ClearFile();
	}

	return done;
}

