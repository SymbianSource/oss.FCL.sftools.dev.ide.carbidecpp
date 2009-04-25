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
// OSTProtocol.cpp: implementation of the COSTProtocol class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "OSTProtocol.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

COSTProtocol::COSTProtocol()
{

}

COSTProtocol::~COSTProtocol()
{

}
BOOL COSTProtocol::DecodeMessage(BYTE* fullMessage, DWORD& fullMessageLength, BYTE& msgId, BYTE*& rawMessage, DWORD& rawLength)
{
	BOOL found = FALSE;

	WORD msgLen = MAKEWORD(fullMessage[OST_LEN_BYTE_1+1], fullMessage[OST_LEN_BYTE_1]);
	if (fullMessageLength >= (WORD)(msgLen + OST_HDR_LEN_1))
	{
		msgId = fullMessage[OST_PROT_BYTE_1];
		rawMessage = &fullMessage[OST_MSG_BYTE_1];
		rawLength = msgLen;
		fullMessageLength = msgLen+OST_HDR_LEN_1;
		found = TRUE;
	}

	return found;
}

DWORD COSTProtocol::EncodeMessage(BYTE* rawMessage, DWORD rawLength, BYTE protocolVersion, BYTE msgId, BYTE* fullMessage, DWORD maxFullLength)
{
	DWORD outLength = 0;

	fullMessage[OST_VER_BYTE_1] = protocolVersion;
	fullMessage[OST_PROT_BYTE_1] = msgId;
	fullMessage[OST_LEN_BYTE_1] = (BYTE)((rawLength >> 8) & 0xff);
	fullMessage[OST_LEN_BYTE_1+1] = (BYTE)(rawLength & 0xff);
	if (rawLength > 0)
	{
		memcpy(&fullMessage[OST_MSG_BYTE_1], rawMessage, rawLength);
	}
	outLength = rawLength + OST_HDR_LEN_1;

	return outLength;

}
