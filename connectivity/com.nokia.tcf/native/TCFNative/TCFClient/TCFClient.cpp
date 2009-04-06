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
// TCFClient.cpp : Defines the entry point for the DLL application.
//

#include "stdafx.h"
#include <stdio.h>
#include <sys/stat.h>
#include "ClientManager.h"
#include "ServerClient.h"
#include "InputStream.h"
#include "ErrorMonitorData.h"
#include "TCDebugLog.h"
#include <vector>

// process wide data
CClientManager* gManager;
#ifdef _DEBUG
BOOL gDoLogging = FALSE;
#endif

#ifdef _DEBUG
static void LogTime(FILE* f);
#endif


BOOL APIENTRY DllMain( HINSTANCE hinstDLL, 
                       DWORD  fdwReason, 
                       LPVOID lpReserved
					 )
{
    // Perform actions based on the reason for calling.
    switch( fdwReason ) 
    { 
        case DLL_PROCESS_ATTACH:
			{
         // Initialize once for each new process.
         // Return FALSE to fail DLL load.
			DWORD currentProcessId = ::GetCurrentProcessId();

			// Create client manager for this process
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
			gManager = new CClientManager(hinstDLL);
#ifdef _DEBUG
			if (gDoLogging)
			{
				FILE* f = fopen("c:\\tcf\\TCF_DllMainDLL_PROCESS_ATTACH.txt", "at");
				LogTime(f);
				fprintf(f,"DLL_PROCESS_ATTACH:\n hinstDLL=%x\n processId=%d\n DllLocation=%s\n DebugLog->m_FileName=%s\n", 
					hinstDLL, 
					currentProcessId,
					gManager->m_DllLocation,
					gManager->m_DebugLog->m_FileName);
				fclose(f);
			}
#endif
			}
            break;

        case DLL_THREAD_ATTACH:
			{
         // Do thread-specific initialization.
			DWORD currentThreadId = ::GetCurrentThreadId();
#ifdef _DEBUG
			if (gDoLogging)
			{
				FILE* f = fopen("c:\\tcf\\TCF_DllMainDLL_THREAD_ATTACH.txt", "at");
				LogTime(f);
				fprintf(f,"DLL_THREAD_ATTACH: hinstDLL=%x currentThreadId=%d\n", hinstDLL, currentThreadId);
				fclose(f);
			}
#endif
			}
            break;

        case DLL_THREAD_DETACH:
			{
         // Do thread-specific cleanup.
			DWORD currentThreadId = ::GetCurrentThreadId();
#ifdef _DEBUG
			if (gDoLogging)
			{
				FILE* f = fopen("c:\\tcf\\TCF_DllMainDLL_THREAD_DETACH.txt", "at");
				LogTime(f);
				fprintf(f,"DLL_THREAD_DETTACH: hinstDLL=%x currentThreadId=%d\n", hinstDLL, currentThreadId);
				fclose(f);
			}
#endif
			}
            break;

        case DLL_PROCESS_DETACH:
			{
         // Perform any necessary cleanup.
			DWORD currentProcessId = ::GetCurrentProcessId();
			// delete the client manager for this process
#ifdef _DEBUG
			if (gDoLogging)
			{
				FILE* f = fopen("c:\\tcf\\TCF_DllMainDLL_PROCESS_DETACH.txt", "at");
				LogTime(f);
				fprintf(f,"DLL_PROCESS_DETACH processId=%d\n", currentProcessId);
				fclose(f);
			}
#endif
			if (gManager)
			{
				delete gManager;
			}
			}
            break;
    }
    return TRUE;  // Successful DLL_PROCESS_ATTACH.
}
#ifdef _DEBUG
static void LogTime(FILE* f)
{
	SYSTEMTIME sTime;
	GetLocalTime(&sTime);
	if (f)
		fprintf(f, "%02.2d%02.2d-%02.2d:%02.2d:%02.2d.%03.3d: ", sTime.wDay, sTime.wMonth, sTime.wHour, sTime.wMinute, sTime.wSecond, sTime.wMilliseconds);
}
#endif

