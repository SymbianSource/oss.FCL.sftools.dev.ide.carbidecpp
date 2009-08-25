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
// OSTProtocol.h: interface for the COSTProtocol class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_OSTPROTOCOL_H__6A8FE0C0_A365_4649_8665_EFCCA002A707__INCLUDED_)
#define AFX_OSTPROTOCOL_H__6A8FE0C0_A365_4649_8665_EFCCA002A707__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "BaseProtocol.h"
// Version 1 offsets
#define OST_HDR_LEN_1	4	// header length
#define OST_VER_BYTE_1	0	// byte that contains the version
#define OST_PROT_BYTE_1	1	// protocol byte
#define OST_LEN_BYTE_1	2	// first byte of length (bytes 2-3)
#define OST_MSG_BYTE_1	4	// start of message bytes
	
// Protocol constants
#define OST_PROT_OST_SYSTEM			(0x00)	// OST System messages
#define OST_PROT_OST_ACTIVATION		(0x01)	// OST Activation messages
#define OST_PROT_OST_ASCII			(0x02)	// OST ASCII messages
#define OST_PROT_OST_SIMPLE			(0x03)	// OST Simple messages
#define OST_PROT_OST_EXTENSIBLE		(0x04)	// OST Extensible messages 
#define OST_PROT_OST_SYMBIAN		(0x05)	// OST Symbian messages
#define OST_PROT_TRK				(0x90)	// OST TRK messages
#define OST_PROT_TC					(0x91)	// OST TraceCore messages

class COSTProtocol : public CBaseProtocol  
{
public:
	COSTProtocol();
	virtual ~COSTProtocol();

	int DecodeMessage(BYTE* fullMessage, DWORD& fullLength, BYTE& msgId, BYTE*& rawMessage, DWORD& rawLength);
	DWORD EncodeMessage(BYTE* rawMessage, DWORD rawLength, BYTE protocolVersion, BYTE msgId, BYTE* fullMessage, DWORD maxFullLength);
	DWORD GetHeaderLength() { return OST_HDR_LEN_1; }
};

#endif // !defined(AFX_OSTPROTOCOL_H__6A8FE0C0_A365_4649_8665_EFCCA002A707__INCLUDED_)
