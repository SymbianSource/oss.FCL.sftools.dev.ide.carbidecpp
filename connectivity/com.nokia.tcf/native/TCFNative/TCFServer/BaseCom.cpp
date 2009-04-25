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
#include "BaseCom.h"

CBaseCom::CBaseCom()
{
#ifdef _DEBUG
	if (gDoLogging)
	{
		FILE* f = fopen("c:\\tcf\\basecommlog.txt", "at");
		fprintf(f, "CBaseCom::CBaseCom() (default constructor)\n");
		fclose(f);
	}
#endif
	m_isConnected = false;
	m_pBuffer = NULL;
	m_numberBytes = 0;
	m_ConnectSettings = NULL;
	m_lastCommError = 0;
	m_CommDebugLog = NULL;
	m_ProcDebugLog = NULL;
	m_connId = -1;
	m_Protocol = NULL;
}

CBaseCom::CBaseCom(ConnectData* connectSettings, DWORD connectionId, CBaseProtocol* protocol)
{
#ifdef _DEBUG
	if (gDoLogging)
	{
		FILE* f = fopen("c:\\tcf\\basecommlog.txt", "at");
		fprintf(f, "connectSettings=%x connectionId=%d, protocol=%x\n", connectSettings, connectionId, protocol);
		fclose(f);
	}
#endif
	m_isConnected = false;
	m_pBuffer = NULL;
	m_numberBytes = 0;
	m_ConnectSettings = NULL;
	m_lastCommError = 0;
	m_CommDebugLog = NULL;
	m_ProcDebugLog = NULL;

	m_connId = connectionId;
	m_Protocol = protocol;

	m_ConnectSettings = new ConnectData();
	memcpy(m_ConnectSettings, connectSettings, sizeof(ConnectData));

#if (defined(LOG_COMM) || defined(LOG_PROCCOMM)) && defined(_DEBUG)
	if (gDoLogging)
	{
		m_CommDebugLog = new TCDebugLog("TCF_Comm", connectionId, 2000L);
		m_ProcDebugLog = new TCDebugLog("TCF_CommP", connectionId, 2000L);
	}
#endif
}

CBaseCom::~CBaseCom()
{
#ifdef _DEBUG
	if (gDoLogging)
	{
		FILE* f = fopen("c:\\tcf\\basecommlog.txt", "at");
		fprintf(f, "CBaseCom::~CBaseCom()\n");
		fclose(f);
	}
#endif
	if (m_pBuffer)
		delete[] m_pBuffer;

	if (m_ConnectSettings)
		delete m_ConnectSettings;

	if (m_CommDebugLog)
		delete m_CommDebugLog;

	if (m_ProcDebugLog)
		delete m_ProcDebugLog;
}
