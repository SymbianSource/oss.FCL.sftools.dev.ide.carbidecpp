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
// TCFProtOST.cpp : Defines the entry point for the DLL application.
//

#include "stdafx.h"
#include "TCFProtOST.h"
#include "OSTProtocol.h"

static const char* pProtocol="ost";
static COSTProtocol* pProtocolClass=NULL;

BOOL APIENTRY DllMain( HANDLE hModule, 
                       DWORD  ul_reason_for_call, 
                       LPVOID lpReserved
					 )
{
    switch (ul_reason_for_call)
	{
		case DLL_PROCESS_ATTACH:
		case DLL_THREAD_ATTACH:
		case DLL_THREAD_DETACH:
		case DLL_PROCESS_DETACH:
			break;
    }
    return TRUE;
}

TCFPROTOST_API const char* RegisterProtocol()
{
	return pProtocol;
}

TCFPROTOST_API CBaseProtocol* CreateProtocol()
{
	pProtocolClass = new COSTProtocol();
	return pProtocolClass;
}
