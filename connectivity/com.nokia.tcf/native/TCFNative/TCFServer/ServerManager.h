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
// ServerManager.h: interface for the CServerManager class.
//
// Only one of these exists
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_SERVERMANAGER_H__30A5EF01_35BA_4B83_AA68_91F7DA0249B7__INCLUDED_)
#define AFX_SERVERMANAGER_H__30A5EF01_35BA_4B83_AA68_91F7DA0249B7__INCLUDED_

#include "ServerClient.h"
#include "TCErrorConstants.h"
#include "TCDebugLog.h"
#include "Connection.h"
#include "Client.h"
#include "ProtocolRegistryItem.h"
#include "CommRegistryItem.h"
#include <vector>

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

typedef std::vector<CConnection*> ConnectionList;
typedef std::vector<CClient*> ClientList;
typedef std::vector<CProtocolRegistryItem*> ProtocolRegistry;
typedef std::vector<CCommRegistryItem*> CommRegistry;

#define MAX_EXEPATHNAME (2048)

#ifdef _WIN32
#define PATH_DELIMITER '\\'
#else
#error not Windows
#endif

class CServerManager  
{
public:
	CServerManager();
	virtual ~CServerManager();
	CServerManager(const char* exeLocation);

	void CommandThread();
	CConnection* FindConnection(pConnectData pConData);
	CConnection* FindConnection(long index);
	CClient* FindClient(DWORD id);
	BOOL RemoveClient(CClient* client);		// remove from m_ClientList
	BOOL RemoveConnection(CConnection* conn);	// remove from m_ConnectionList
	void DoShutdown();
private:
	void RegisterAllProtocols();
	void RegisterAllComms();
	void UnRegisterAllProtocols();
	void UnRegisterAllComms();
	const char* FindProtocolPath(char* protocolType);
	const char* FindCommPath(char* commType);

public:
	CServerCommand* m_Server;			// client/server commands/responses
	TCDebugLog* m_DebugLog;
	ConnectionList* m_ConnectionList;	// all connections
	ClientList* m_ClientList;			// all clients
	DWORD m_NextClientId;				// next client ID
	DWORD m_NextConnectionId;			// next connection ID
	char m_Version[MAX_VERSION_STRING]; // our version string
	ProtocolRegistry* m_ProtocolList;	// protocols (e.g., OST, etc)
	CommRegistry* m_CommList;			// comm connections (e.g., TCP, etc)
	char* m_ExeLocation;				// current location of TCFServer
};

#endif // !defined(AFX_SERVERMANAGER_H__30A5EF01_35BA_4B83_AA68_91F7DA0249B7__INCLUDED_)
