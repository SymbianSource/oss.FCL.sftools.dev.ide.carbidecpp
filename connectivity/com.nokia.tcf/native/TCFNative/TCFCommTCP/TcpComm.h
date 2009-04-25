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
// TcpComm.h: interface for the CTcpComm class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_TCPCOMM_H__69657421_6D37_497A_A377_12E71365EDAB__INCLUDED_)
#define AFX_TCPCOMM_H__69657421_6D37_497A_A377_12E71365EDAB__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "BaseCom.h"
#include <winsock2.h>

#define MAX_MESSAGE_LENGTH	(64*1024L)
#define MAX_TCP_MESSAGE_BUFFER_LENGTH (8*MAX_MESSAGE_LENGTH)

#define DEFAULT_SOCKET_TIMEOUT	(2000000L)	// 2 seconds
#define TIMEOUT_SEC(x) (x/1000000L)
#define TIMEOUT_USEC(x) (x%1000000L)

class CTcpComm : public CBaseCom  
{
public:
	CTcpComm();
	CTcpComm(ConnectData* connectSettings, DWORD connectionId, CBaseProtocol* protocol);
	virtual ~CTcpComm();

	virtual long OpenPort();
	long ClosePort();
	long SendDataToPort(DWORD inSize, const void* inData);
	long PollPort(DWORD& outSize);
	long ReadPort(DWORD inSize, void* outData, DWORD& outSize);
	long ProcessBuffer(CConnection* pConn, CRegistry* pRegistry, long& numberProcessed);
//	long ProcessBuffer(NOTIFYCLIENTSCOMMERROR pNotify, ROUTEMESSAGE pRouteMessage, long& numberProcessed);
//	long ProcessBuffer(long& numberProcessed);
	void DeleteMsg(DWORD inMsgLength);
	bool GetVersion(char* outVersion) { return false; } // don't have enough information for this
	bool HasVersion() { return false; } // can we have a version?
	virtual long PreProcessMessage(int inMsgType, DWORD inMsgLength, BYTE* inMessage) { return TCAPI_ERR_NONE; }
	virtual long PreProcessMessage(BYTE msgId, DWORD inMsgLength, BYTE* inMessage) { return TCAPI_ERR_NONE; }
	bool IsConnectionEqual(ConnectData* pConn);

private:
	SOCKET m_socket;
	sockaddr_in m_clientService;
	TIMEVAL m_timeOut;
	WSAEVENT m_hSocketEvent; 
	DWORD m_pPeekBuffer;

};

#endif // !defined(AFX_TCPCOMM_H__69657421_6D37_497A_A377_12E71365EDAB__INCLUDED_)
