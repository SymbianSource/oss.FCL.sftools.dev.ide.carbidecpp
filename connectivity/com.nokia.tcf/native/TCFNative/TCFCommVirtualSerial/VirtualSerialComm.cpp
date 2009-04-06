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
// VirtualSerialComm1.cpp: implementation of the VirtualSerialComm class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "VirtualSerialComm.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

VirtualSerialComm::VirtualSerialComm()
{
#ifdef _DEBUG
	if (gDoLogging)
	{
		FILE* f = fopen("c:\\tcf\\vscommlog.txt", "at");
		fprintf(f, "VirtualSerialComm::VirtualSerialComm() (default constructor)\n");
		fclose(f);
	}
#endif
}
VirtualSerialComm::VirtualSerialComm(ConnectData* connectSettings, DWORD connectionId, CBaseProtocol* protocol)
{
#ifdef _DEBUG
	if (gDoLogging)
	{
		FILE* f = fopen("c:\\tcf\\vscommlog.txt", "at");
		fprintf(f, "connectSettings=%x connectionId=%d, protocol=%x\n", connectSettings, connectionId, protocol);
		fprintf(f, "connectSettings->comPort=%s\n", connectSettings->virtualSerialSettings.comPort);
		fclose(f);
	}
#endif
	m_connId = connectionId;
	m_Protocol = protocol;

	m_ConnectSettings = new ConnectData();
	memcpy(m_ConnectSettings, connectSettings, sizeof(ConnectData));

#if (defined(LOG_COMM) || defined(LOG_PROCCOMM)) && defined(_DEBUG)
	if (gDoLogging)
	{
		m_CommDebugLog = new TCDebugLog("TCF_Comm", connectionId, 2000L);
		m_ProcDebugLog = new TCDebugLog("TCF_CommP", connectionId, 2000L);
	}
#endif
	pRealSerialConnectData pR = &m_ConnectSettings->realSerialSettings;
	pVirtualSerialConnectData pV = &m_ConnectSettings->virtualSerialSettings;

	// copy com port to real settings
	strcpy(pR->comPort, pV->comPort);

	// fill in real setting defaults
	pR->baudRate = 115200;
	pR->dataBits = 8;
	pR->flowControl = eFlowControlNone;
	pR->parity = eParityNone;
	pR->stopBits = eStopBits1;
}

VirtualSerialComm::~VirtualSerialComm()
{
#ifdef _DEBUG
	if (gDoLogging)
	{
		FILE* f = fopen("c:\\tcf\\vscommlog.txt", "at");
		fprintf(f, "VirtualSerialComm::~VirtualSerialComm()\n");
		fclose(f);
	}
#endif
}
bool VirtualSerialComm::IsConnectionEqual(ConnectData* pConn)
{
	bool equal = false;

	// forms accepted:
	//   "comNN", "NN"
	char* ptr1 = m_ConnectSettings->virtualSerialSettings.comPort;
	char* ptr2 = pConn->virtualSerialSettings.comPort;
	bool digit1found = false;
	while(!digit1found && *ptr1 != NULL) 
	{
		if (*ptr1 >= '0' && *ptr1 <= '9')
		{
			digit1found = true;
			break;
		}
		ptr1++;
	}
	bool digit2found = false;
	while(!digit2found && *ptr2 != NULL) 
	{
		if (*ptr2 >= '0' && *ptr2 <= '9')
		{
			digit2found = true;
			break;
		}
		ptr2++;
	}
	if (digit1found && digit2found)
	{
		if (strcmp(ptr1, ptr2) == 0)
			equal = true;
	}
	return equal;
}

