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
// TCFCommSerial.cpp : Defines the entry point for the DLL application.
//

#include "stdafx.h"
#include <sys/stat.h>
#include "TCFCommSerial.h"
#include "RealSerialComm.h"

static const char* pCommType="serial";
static CBaseCom* pCommClass=NULL;
#ifdef _DEBUG
BOOL gDoLogging = FALSE;
#endif

BOOL APIENTRY DllMain( HANDLE hModule, 
                       DWORD  ul_reason_for_call, 
                       LPVOID lpReserved
					 )
{
    switch (ul_reason_for_call)
	{
		case DLL_PROCESS_ATTACH:
			{
#ifdef _DEBUG
				struct _stat buf;
				char* dirname = "c:\\tcf";
				int result = _stat(dirname, &buf);
				if (result == 0) // exists
				{
					gDoLogging = TRUE;
				}
				else
				{
					gDoLogging = FALSE;
				}
#endif
			}
			break;
		case DLL_THREAD_ATTACH:
		case DLL_THREAD_DETACH:
		case DLL_PROCESS_DETACH:
			break;
    }
    return TRUE;
}

// register this connection type
TCFCOMMSERIAL_API const char* RegisterComm()
{
	return pCommType;
}

// create this connection type
TCFCOMMSERIAL_API CBaseCom* CreateComm(ConnectData* connectSettings, DWORD connectionId, CBaseProtocol* protocol)
{
	pCommClass = new CRealSerialComm(connectSettings, connectionId, protocol);

	return pCommClass;
}

