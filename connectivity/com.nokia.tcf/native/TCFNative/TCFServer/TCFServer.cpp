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
// TCFServer.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include "ServerClient.h"
#include "ServerManager.h"
#include <vector>
#include <sys/stat.h>

CServerManager* gManager;
char gServerLocation[2048]={0};

#ifdef _DEBUG
BOOL gDoLogging=FALSE;
char TCDebugMsg[1000];
#define TCDEBUGOPEN() gManager->m_DebugLog->WaitForAccess();
#define TCDEBUGLOGS(s) sprintf(TCDebugMsg,"%s", s); gManager->m_DebugLog->log(TCDebugMsg);
#define TCDEBUGLOGA1(s, a1) sprintf(TCDebugMsg, s, a1); gManager->m_DebugLog->log(TCDebugMsg);
#define TCDEBUGLOGA2(s, a1, a2) sprintf(TCDebugMsg, s, a1, a2); gManager->m_DebugLog->log(TCDebugMsg);
#define TCDEBUGLOGA3(s, a1, a2, a3) sprintf(TCDebugMsg, s, a1, a2, a3); gManager->m_DebugLog->log(TCDebugMsg);
#define TCDEBUGCLOSE() gManager->m_DebugLog->ReleaseAccess();
#else
#define TCDEBUGOPEN()
#define TCDEBUGLOGS(s)
#define TCDEBUGLOGA1(s, a1)
#define TCDEBUGLOGA2(s, a1, a2)
#define TCDEBUGLOGA3(s, a1, a2, a3)
#define TCDEBUGCLOSE()
#endif
static void GetServerLocation(char* cl);
#ifdef _DEBUG
static void LogTime(FILE* f);
#endif
int main(int argc, char* argv[])
{
#ifdef _DEBUG
	struct _stat buf;
	char* dirname = "c:\\tcf";
	int result = _stat(dirname, &buf);
	if (result == 0)
	{
		gDoLogging = TRUE;
	}
	else
	{
		gDoLogging = FALSE;
	}
#endif


	if (argc == 2) // for running from the debugger
	{
		GetServerLocation(argv[1]);
	}
	else
	{
		GetServerLocation(argv[0]);
	}
#ifdef _DEBUG
	if (gDoLogging)
	{
//		FILE* f = fopen("c:\\tcf\\TCFServer_Main.txt", "at");
//		LogTime(f);
//		fprintf(f,"ExeLocation=%s\n", gServerLocation);
//		fclose(f);
	}
#endif
	gManager = new CServerManager(gServerLocation);
	gManager->CommandThread();
	delete gManager;

	return 0;
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
#ifdef _WIN32
void GetServerLocation(char* cl)
{
	char dir[_MAX_DIR];
	char drive[_MAX_DRIVE];
	_splitpath(cl, drive, dir, NULL, NULL);
	sprintf(gServerLocation, "%s%s", drive, dir);
}
#else
#error not Windows
#endif
