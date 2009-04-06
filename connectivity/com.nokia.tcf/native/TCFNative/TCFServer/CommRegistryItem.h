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
// CommRegistryItem.h: interface for the CCommRegistryItem class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_COMMREGISTRYITEM_H__3100D179_F754_4A4C_910D_7EB827E8DEA0__INCLUDED_)
#define AFX_COMMREGISTRYITEM_H__3100D179_F754_4A4C_910D_7EB827E8DEA0__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "ServerClient.h"
#include "BaseCom.h"

#define MAX_DLLPATHNAME (2048)

class CCommRegistryItem  
{
public:
	CCommRegistryItem();
	virtual ~CCommRegistryItem();

	char m_CommType[MAX_CONNECTION_TYPE];
	char m_CommLibrary[MAX_DLLPATHNAME];
};

#endif // !defined(AFX_COMMREGISTRYITEM_H__3100D179_F754_4A4C_910D_7EB827E8DEA0__INCLUDED_)
