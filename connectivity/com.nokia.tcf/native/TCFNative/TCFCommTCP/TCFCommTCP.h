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
#include "BaseCom.h"
// The following ifdef block is the standard way of creating macros which make exporting 
// from a DLL simpler. All files within this DLL are compiled with the TCFCOMMTCP_EXPORTS
// symbol defined on the command line. this symbol should not be defined on any project
// that uses this DLL. This way any other project whose source files include this file see 
// TCFCOMMTCP_API functions as being imported from a DLL, wheras this DLL sees symbols
// defined with this macro as being exported.
#ifdef TCFCOMMTCP_EXPORTS
#define TCFCOMMTCP_API __declspec(dllexport)
#else
#define TCFCOMMTCP_API __declspec(dllimport)
#endif

#ifdef __cplusplus
extern "C" {
#endif

TCFCOMMTCP_API const char* RegisterComm();
TCFCOMMTCP_API CBaseCom* CreateComm(ConnectData* connectSettings, DWORD connectionId, CBaseProtocol* protocol);

#ifdef __cplusplus
}
#endif
