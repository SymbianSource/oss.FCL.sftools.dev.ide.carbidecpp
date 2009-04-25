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
// VirtualSerialComm1.h: interface for the VirtualSerialComm class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_VIRTUALSERIALCOMM1_H__C5549E09_44AA_4DD2_9DD0_56054DCA0C20__INCLUDED_)
#define AFX_VIRTUALSERIALCOMM1_H__C5549E09_44AA_4DD2_9DD0_56054DCA0C20__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "RealSerialComm.h"

class VirtualSerialComm : public CRealSerialComm  
{
public:
	VirtualSerialComm();
	VirtualSerialComm(ConnectData* connectSettings, DWORD connectionId, CBaseProtocol* protocol);
	virtual ~VirtualSerialComm();

	virtual bool IsConnectionEqual(ConnectData* pConn);
};

#endif // !defined(AFX_VIRTUALSERIALCOMM1_H__C5549E09_44AA_4DD2_9DD0_56054DCA0C20__INCLUDED_)
