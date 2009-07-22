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
// RealSerialComm.h: interface for the CRealSerialComm class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_REALSERIALCOMM_H__B20F73BE_260A_4A99_B7F5_E4F7C42FE05F__INCLUDED_)
#define AFX_REALSERIALCOMM_H__B20F73BE_260A_4A99_B7F5_E4F7C42FE05F__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "BaseCom.h"
#define MAX_MESSAGE_LENGTH	(64*1024L)
#define MAX_SERIAL_MESSAGE_BUFFER_LENGTH (2*MAX_MESSAGE_LENGTH)

class CRealSerialComm : public CBaseCom  
{
public:
	CRealSerialComm();
	CRealSerialComm(ConnectData* connectSettings, DWORD connectionId, CBaseProtocol* protocol);
	virtual ~CRealSerialComm();

	virtual long OpenPort();
	long ClosePort();
	long SendDataToPort(DWORD inSize, const void* inData);
	long PollPort(DWORD& outSize);
	long ReadPort(DWORD inSize, void* outData, DWORD& outSize);
	long ProcessBuffer(CConnection* pConn, CRegistry* pRegistry, long& numberProcessed);
	void DeleteMsg(DWORD inMsgLength);
	bool GetVersion(char* outVersion) { return false; } // don't have enough information for this
	bool HasVersion() { return false; } // can we have a version?
	virtual long PreProcessMessage(int inMsgType, DWORD inMsgLength, BYTE* inMessage) { return TCAPI_ERR_NONE; }
	virtual long PreProcessMessage(BYTE msgId, DWORD inMsgLength, BYTE* inMessage) { return TCAPI_ERR_NONE; }
	virtual bool IsConnectionEqual(ConnectData* pConn);

#ifdef _DEBUG
	DWORD GetErrorText(DWORD inError);
	DWORD GetErrorText2(DWORD inError);
	void DumpComStat(COMSTAT* stat);
	void DumpComStatP(COMSTAT* stat);
	void DumpBuffer(BYTE* ptr, long length);
#endif

private:
	long SetDCB();
	HANDLE m_hSerial;
	DCB m_dcb;
	char m_serialPortName[MAX_COMPORT_SIZE];
	char sLogMsg[3000];

};

#endif // !defined(AFX_REALSERIALCOMM_H__B20F73BE_260A_4A99_B7F5_E4F7C42FE05F__INCLUDED_)
