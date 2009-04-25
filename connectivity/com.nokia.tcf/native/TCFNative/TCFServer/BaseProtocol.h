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
// BaseProtocol.h: interface for the CBaseProtocol class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_BASEPROTOCOL_H__EABB80B6_353C_45AE_8976_AE0C0D93AC84__INCLUDED_)
#define AFX_BASEPROTOCOL_H__EABB80B6_353C_45AE_8976_AE0C0D93AC84__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

class CBaseProtocol  
{
public:
	CBaseProtocol();
	virtual ~CBaseProtocol();

	// used to decode a message into header/message parts
	virtual BOOL DecodeMessage(BYTE* fullMessage, DWORD& fullLength, BYTE& msgId, BYTE*& rawMessage, DWORD& rawLength)=0;

	// used to encode a raw message (prefixes any protocol headers)
	virtual DWORD EncodeMessage(BYTE* rawMessage, DWORD rawLength, BYTE protocolVersion, BYTE msgId, BYTE* fullMessage, DWORD maxFullLength)=0;

	// used to query how many bytes the header is so the caller can allocate enough memory
	virtual DWORD GetHeaderLength()=0;

};

typedef const char* (*PROTOCOLREGISTER)(void);
typedef CBaseProtocol* (*PROTOCOLCREATE)(void);

#define PROTOCOLREGISTER_FNNAME	"RegisterProtocol"
#define PROTOCOLCREATE_FNNAME	"CreateProtocol"

#define PROTOCOLDLL_BASENAME	"TCFProt"

#endif // !defined(AFX_BASEPROTOCOL_H__EABB80B6_353C_45AE_8976_AE0C0D93AC84__INCLUDED_)
