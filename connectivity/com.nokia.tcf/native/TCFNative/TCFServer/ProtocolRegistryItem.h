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
// ProtocolRegistryItem.h: interface for the CProtocolRegistryItem class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_PROTOCOLREGISTRYITEM_H__BD3F800B_7676_4D8D_907E_AE324467968C__INCLUDED_)
#define AFX_PROTOCOLREGISTRYITEM_H__BD3F800B_7676_4D8D_907E_AE324467968C__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
#include "ServerClient.h"
#include "BaseProtocol.h"
//#include <vector>

#define MAX_DLLPATHNAME (2048)

class CProtocolRegistryItem  
{
public:
	CProtocolRegistryItem();
	virtual ~CProtocolRegistryItem();

	char m_ProtocolType[MAX_DECODE_FORMAT];
	char m_ProtocolLibrary[MAX_DLLPATHNAME];
	
};

//typdef std::vector<CProtocolRegistryItem*> ProtocolRegistryList;


#endif // !defined(AFX_PROTOCOLREGISTRYITEM_H__BD3F800B_7676_4D8D_907E_AE324467968C__INCLUDED_)
