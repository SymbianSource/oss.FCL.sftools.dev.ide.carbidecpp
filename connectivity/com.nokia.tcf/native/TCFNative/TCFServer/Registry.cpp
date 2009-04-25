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
// Registry.cpp: implementation of the CRegistry class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "Registry.h"
#include "ServerManager.h"

//#define LOG_REGISTRY
#if defined(LOG_REGISTRY) && defined(_DEBUG)
extern BOOL gDoLogging;
extern char TCDebugMsg[];
extern CServerManager* gManager;
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
CRegistry::CRegistry()
{
}
CRegistry::CRegistry(DWORD connectionId)
{
}
CRegistry::~CRegistry()
{
}
#if (0)
CRegistry::CRegistry()
{
	TCDEBUGOPEN();
	TCDEBUGLOGS("CRegistry::CRegistry\n");
	TCDEBUGCLOSE();
}

CRegistry::CRegistry(DWORD connectionId)
{
	TCDEBUGOPEN();
	TCDEBUGLOGA1("CRegistry::CRegistry connectionId = %d\n", connectionId);

	for (int i = 0; i < MAX_MESSAGE_IDS; i++)
	{
		m_Registry[i].clist = new IdClientList();
		m_Registry[i].clist->clear();
	}

	char mutexName[200];

	sprintf(mutexName, "%s%d", REGISTRY_MUTEX_BASENAME, connectionId);
	m_Mutex.Open(mutexName, REGISTRY_MUTEX_TIMEOUT);
	TCDEBUGCLOSE();
}

CRegistry::~CRegistry()
{
	TCDEBUGOPEN();
	TCDEBUGLOGS("CRegistry::~CRegistry\n");

	for (int i = 0; i < MAX_MESSAGE_IDS; i++)
	{
		if (m_Registry[i].clist != NULL)
		{
			m_Registry[i].clist->clear();
			delete m_Registry[i].clist;
		}
	}
	m_Mutex.Close();
	TCDEBUGCLOSE();
}
BOOL CRegistry::AddClient(CClient* newClient, long numberIds, BYTE* ids)
{
	TCDEBUGOPEN();
	TCDEBUGLOGS("CRegistry::AddClient\n");

	m_Mutex.Wait();

	for (int i = 0; i < numberIds; i++)
	{
		TCDEBUGLOGA1(" id=%x\n", ids[i]);
		m_Registry[ids[i]].clist->push_back(newClient);
	}

	DumpRegistry();
	m_Mutex.Release();
	TCDEBUGCLOSE();
	return TRUE;
}
BOOL CRegistry::RemoveClient(CClient* client)
{
	TCDEBUGOPEN();
	TCDEBUGLOGS("CRegistry::RemoveClient\n");

	m_Mutex.Wait();

	BOOL found = FALSE;

	for (int i = 0; i < MAX_MESSAGE_IDS; i++)
	{
		long num = m_Registry[i].clist->size();
		if (num != 0)
		{
			TCDEBUGLOGA3(" CRegistry::RemoveClient client = %x i = %x num = %d\n", client, i, num);
			IdClientList::iterator iter;
			for (iter = m_Registry[i].clist->begin(); iter != m_Registry[i].clist->end(); iter++)
			{
				TCDEBUGLOGA2(" CRegistry::RemoveClient iter = %x *iter = %x\n", iter, *iter);
				if (client == *iter)
				{
					m_Registry[i].clist->erase(iter);
					found = TRUE;
					break;
				}
			}
		}
	}

	m_Mutex.Release();
	TCDEBUGCLOSE();
	return found;
}

void CRegistry::DumpRegistry()
{
	for (int i = 0; i < MAX_MESSAGE_IDS; i++)
	{
		long num = m_Registry[i].clist->size();
		if (num != 0)
		{
			TCDEBUGLOGA2(" CRegistry::DumpRegistry i = %x num = %d\n", i, num);
			IdClientList::iterator iter;
			for (iter = m_Registry[i].clist->begin(); iter != m_Registry[i].clist->end(); iter++)
			{
				TCDEBUGLOGA2(" CRegistry::DumpRegistry iter = %x *iter = %x\n", iter, *iter);
			}
		}
	}
}

// fullmessage includes the protocol header
// message is unwrapped from the protocol header
// this is because some clients want the full message and some clients just want the actual message
long CRegistry::RouteMessage(BYTE msgId, BYTE* fullMessage, DWORD fullLen, BYTE* realMessage, DWORD realMessageLen)
{

	long err = TCAPI_ERR_NONE;

//	TCDEBUGOPEN();
//	TCDEBUGLOGS("CRegistry::RouteMessage\n");
//	TCDEBUGCLOSE();
	m_Mutex.Wait();

	if (msgId >= 0 && msgId < MAX_MESSAGE_IDS)
	{
		long numClients = m_Registry[msgId&0xff].clist->size();

		TCDEBUGOPEN();
		TCDEBUGLOGA2(" CRegistry::RouteMessage msgId = %x numClients = %d\n", msgId, numClients);
		TCDEBUGCLOSE();

		if (numClients > 0)
		{
			IdClientList::iterator iter;
			for (iter = m_Registry[msgId&0xff].clist->begin(); iter != m_Registry[msgId&0xff].clist->end(); iter++)
			{
				CClient* client = *iter;
				if (client && client->IsStarted())
				{
					if (client->IsStreamOpen())
					{
						CInputStream* stream = client->GetInputStream();
						// get unwrap format
						if (client->m_Options.unWrapFormat == UNWRAP_LEAVE_HEADERS)
						{
							// use full message
							err = stream->AddMessage(fullLen, fullMessage);
							// routing errors here can be input stream overflows
							//  notify this client right now (no OS error)
							if (err != TCAPI_ERR_NONE)
							{
								client->m_ErrorMonitor->PutError(err, false, 0);
								err = TCAPI_ERR_NONE;
							}
						}
						else
						{
							// use raw message
							err = stream->AddMessage(realMessageLen, realMessage);
							// routing errors here can be input stream overflows
							//  notify this client right now (no OS error)
							if (err != TCAPI_ERR_NONE)
							{
								client->m_ErrorMonitor->PutError(err, false, 0);
								err = TCAPI_ERR_NONE;
							}
						}
					}
					else if (client->IsMessageFileOpen())
					{
						CMessageFile* file = client->GetMessageFile();
						// get unwrap format
						if (client->m_Options.unWrapFormat == UNWRAP_LEAVE_HEADERS)
						{
							// use full message
							err = file->AddMessage(fullLen, fullMessage);
							// routing errors here can be input stream overflows
							//  notify this client right now (no OS error)
							if (err != TCAPI_ERR_NONE)
							{
								client->m_ErrorMonitor->PutError(err, false, 0);
								err = TCAPI_ERR_NONE;
							}
						}
						else
						{
							// use raw message
							err = file->AddMessage(realMessageLen, realMessage);
							// routing errors here can be input stream overflows
							//  notify this client right now (no OS error)
							if (err != TCAPI_ERR_NONE)
							{
								client->m_ErrorMonitor->PutError(err, false, 0);
								err = TCAPI_ERR_NONE;
							}
						}
					}
				}
			}
		}
	}

	m_Mutex.Release();
	return err;
}
#endif

