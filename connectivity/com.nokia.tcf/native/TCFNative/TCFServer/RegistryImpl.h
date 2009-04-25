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
// RegistryImpl.h: interface for the CRegistryImpl class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_REGISTRYIMPL_H__2C724EE6_DB27_4511_A934_EF3CDBBECADB__INCLUDED_)
#define AFX_REGISTRYIMPL_H__2C724EE6_DB27_4511_A934_EF3CDBBECADB__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "Registry.h"

class CRegistryImpl : public CRegistry  
{
public:
	CRegistryImpl();
	CRegistryImpl(DWORD connectionId);
	virtual ~CRegistryImpl();

	BOOL AddClient(CClient* newClient, long numberIds, BYTE* ids);
	BOOL RemoveClient(CClient* client);
	void DumpRegistry();
	long RouteMessage(BYTE msgId, BYTE* fullMessage, DWORD fullLen, BYTE* realMessage, DWORD realMessageLen);

};

#endif // !defined(AFX_REGISTRYIMPL_H__2C724EE6_DB27_4511_A934_EF3CDBBECADB__INCLUDED_)
