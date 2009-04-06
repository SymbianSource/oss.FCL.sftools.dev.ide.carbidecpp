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
// Registry.h: interface for the CRegistry class.
//
// one of these per connection
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_REGISTRY_H__BB7103A0_5492_472D_86B4_C9BF4C25CD20__INCLUDED_)
#define AFX_REGISTRY_H__BB7103A0_5492_472D_86B4_C9BF4C25CD20__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "Client.h"
#include "mutex.h"
#include <vector>

#define REGISTRY_MUTEX_BASENAME		"TCFRegistry"
#define REGISTRY_MUTEX_TIMEOUT		(60000L)

typedef std::vector<CClient*> IdClientList;

#define MAX_MESSAGE_IDS	(256)
typedef struct tagMessageIdRegistry
{
	IdClientList* clist;
} *pMessageIdRegistry, MessageIdRegistry;

class CRegistry  
{
public:
	CRegistry();
	CRegistry(DWORD connectionId);
	virtual ~CRegistry();

	virtual BOOL AddClient(CClient* newClient, long numberIds, BYTE* ids)=0;
	virtual BOOL RemoveClient(CClient* client)=0;
	virtual void DumpRegistry()=0;
	virtual long RouteMessage(BYTE msgId, BYTE* fullMessage, DWORD fullLen, BYTE* realMessage, DWORD realMessageLen)=0;

	MessageIdRegistry m_Registry[MAX_MESSAGE_IDS];
	Mutex m_Mutex;
};

#endif // !defined(AFX_REGISTRY_H__BB7103A0_5492_472D_86B4_C9BF4C25CD20__INCLUDED_)
