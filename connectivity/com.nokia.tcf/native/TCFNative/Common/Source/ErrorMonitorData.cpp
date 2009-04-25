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

#include "stdafx.h"
#include "ErrorMonitorData.h"
#include "TCErrorConstants.h"
#include <stdio.h>
#include <sys/stat.h>

#ifdef TCF_CLIENT
#include "..\..\TCFClient\ClientManager.h"
extern CClientManager* gManager;
#endif

#ifdef TCF_SERVER
#include "..\..\TCFServer\ServerManager.h"
extern CServerManager* gManager;
#endif

#ifdef _DEBUG
extern BOOL gDoLogging;
#endif

//#define LOG_PERFORMANCE
#ifdef LOG_PERFORMANCE
# ifdef TCF_CLIENT
static char* perfFileName="c:\\tcf\\clienterrorperf.txt";
# else
static char* perfFileName="c:\\tcf\\servererrorperf.txt";
# endif
static FILE *fpLog = NULL;
static int numLogged=0;
static void logPerf(char* msg);
static void openPerf();
static void closePerf();
#define OPENPERF() openPerf()
#define LOGPERF(s) logPerf(s)
#define CLOSEPERF() closePerf()
#else
#define OPENPERF()
#define LOGPERF(s)
#define CLOSEPERF()
#endif

//#define LOG_ERRORMONITOR
#if defined(LOG_ERRORMONITOR) && defined(_DEBUG)
extern char TCDebugMsg[];
#define TCDEBUGOPEN() if (gDoLogging) gManager->m_DebugLog->WaitForAccess();
#define TCDEBUGLOGS(s) if (gDoLogging) sprintf(TCDebugMsg,"%s", s); if (gDoLogging) gManager->m_DebugLog->log(TCDebugMsg);
#define TCDEBUGLOGA1(s, a1) if (gDoLogging) sprintf(TCDebugMsg, s, a1); if (gDoLogging) gManager->m_DebugLog->log(TCDebugMsg);
#define TCDEBUGLOGA2(s, a1, a2) if (gDoLogging) sprintf(TCDebugMsg, s, a1, a2); if (gDoLogging) gManager->m_DebugLog->log(TCDebugMsg);
#define TCDEBUGLOGA3(s, a1, a2, a3) if (gDoLogging) sprintf(TCDebugMsg, s, a1, a2, a3); if (gDoLogging) gManager->m_DebugLog->log(TCDebugMsg);
#define TCDEBUGCLOSE() if (gDoLogging) gManager->m_DebugLog->ReleaseAccess();
#else
#define TCDEBUGOPEN()
#define TCDEBUGLOGS(s)
#define TCDEBUGLOGA1(s, a1)
#define TCDEBUGLOGA2(s, a1, a2)
#define TCDEBUGLOGA3(s, a1, a2, a3)
#define TCDEBUGCLOSE()
#endif

CErrorMonitor::CErrorMonitor()
{
}
CErrorMonitor::CErrorMonitor(long inClientID)
{
	TCDEBUGOPEN();
	TCDEBUGLOGA2("CErrorMonitor::CErrorMonitor clientId = %d this = %x\n", inClientID, this);

	m_ClientID = inClientID;

	TCDEBUGLOGS("CErrorMonitor::CErrorMonitor done\n");
	TCDEBUGCLOSE();

	OPENPERF();
}
CErrorMonitor::~CErrorMonitor()
{
	TCDEBUGOPEN();
	TCDEBUGLOGA2("CErrorMonitor::~CErrorMonitor clientId = %d this = %x\n", m_ClientID, this);

	m_Data.Close();
	m_Mutex.Close();

	TCDEBUGLOGS("CErrorMonitor::~CErrorMonitor done\n");
	TCDEBUGCLOSE();

	CLOSEPERF();
}

BOOL CErrorMonitor::CreateData()
{
	TCDEBUGOPEN();
	TCDEBUGLOGA2("CErrorMonitor::CreateData clientId = %d this = %x\n", m_ClientID, this);

	// create shared data location
	char toString[30];
	sprintf(toString, "%s%04.4ld",ERRORMONITORDATA_MAP_BASENAME, m_ClientID);
	m_Data.Open(ERRORMONITORDATA_MAP_SIZE, toString);
	m_Data.Init();

	sprintf(toString, "%s%04.4ld",ERRORMONITORDATA_MUTEX_BASENAME, m_ClientID);

	m_Mutex.Open(toString, ERRORMONITORDATA_MUTEX_TIMEOUT);

	TCDEBUGLOGS("CErrorMonitor::CreateData done\n");
	TCDEBUGCLOSE();

	return TRUE;
}

BOOL CErrorMonitor::GetError(LONG* tcfError, BOOL* osErrorUsed, DWORD* osError)
{
	TCDEBUGOPEN();
//	TCDEBUGLOGA2("CErrorMonitor::GetError clientId = %d this = %x\n", m_ClientID, this);
	BOOL found = FALSE;

	WaitForAccess();

	pErrorData pData = GetDataPtr();

#if (0)
	if (pData->errorOccurred)
	{
		TCDEBUGLOGA3("CErrorMonitor::GetError tcfError = %d osErrorUsed = %d osError = %d\n", 
			pData->tcfError, pData->osErrorUsed, pData->osError);

		found = TRUE;
		*tcfError = pData->tcfError;
		*osErrorUsed = pData->osErrorUsed;
		*osError = pData->osError;

		pData->errorOccurred = FALSE;
	}
#endif
#ifdef LOG_PERFORMANCE
	char msg[100];
	sprintf(msg, "GetError numErrors=%d\n", pData->numberErrors);
	LOGPERF(msg);
#endif
	if (!IsErrorQueueEmpty(pData))
	{
		LOGPERF("GetError\n");
		found = TRUE;
		*tcfError = pData->errors[pData->first].tcfError;
		*osErrorUsed = pData->errors[pData->first].osErrorUsed;
		*osError = pData->errors[pData->first].osError;
		TCDEBUGLOGA3("CErrorMonitor::GetError tcfError = %d osErrorUsed = %d osError = %d\n", 
			*tcfError, *osErrorUsed, *osError);
		pData->numberErrors--;
		if (pData->numberErrors <= 0)
		{
			ResetErrorQueue(pData);
		}
		else
		{
			pData->first++;
			if (pData->first >= MAX_ERRORS)
				pData->first = 0;
		}
	}
#ifdef LOG_PERFORMANCE
//	char msg[100];
	sprintf(msg, "GetError numErrors=%d tcfError=%d\n", pData->numberErrors, *tcfError);
	LOGPERF(msg);
#endif
	ReleaseAccess();

//	TCDEBUGLOGS("CErrorMonitor::GetError done\n");
	TCDEBUGCLOSE();
	return found;
}

BOOL CErrorMonitor::PutError(LONG tcfError, BOOL osErrorUsed, DWORD osError)
{
	TCDEBUGOPEN();
//	TCDEBUGLOGA2("CErrorMonitor::PutError clientId = %d this = %x\n", m_ClientID, this);

	BOOL done = FALSE;

	WaitForAccess();

	pErrorData pData = GetDataPtr();
	if (pData == NULL)
		return done;
#if (0)
	if (!pData->errorOccurred)
	{
		done = TRUE;
		pData->tcfError = tcfError;
		pData->osErrorUsed = osErrorUsed;
		pData->osError = osError;

//		TCDEBUGLOGA3("CErrorMonitor::PutError tcfError = %d osErrorUsed = %d osError = %d\n", 
//			pData->tcfError, pData->osErrorUsed, pData->osError);

		pData->errorOccurred = TRUE;
	}
#endif
#ifdef LOG_PERFORMANCE
	char msg[100];
	sprintf(msg, "PutError numErrors=%d tcfError=%d\n", pData->numberErrors, tcfError);
	LOGPERF(msg);
#endif
	if (!IsErrorQueueFull(pData))
	{
		LOGPERF("PutError\n");
		TCDEBUGLOGA3("CErrorMonitor::PutError tcfError = %d osErrorUsed = %d osError = %d\n", 
			tcfError, osErrorUsed, osError);
		done = TRUE;
		pData->errors[pData->next].tcfError = tcfError;
		pData->errors[pData->next].osErrorUsed = osErrorUsed;
		pData->errors[pData->next].osError = osError;
		pData->numberErrors++;
		pData->next++;
		if (pData->next >= MAX_ERRORS)
			pData->next = 0;
	}
#ifdef LOG_PERFORMANCE
//	char msg[100];
	sprintf(msg, "PutError numErrors=%d\n", pData->numberErrors);
	LOGPERF(msg);
#endif
	ReleaseAccess();

//	TCDEBUGLOGS("CErrorMonitor::GetError done\n");
	TCDEBUGCLOSE();
	return done;
}

BOOL CErrorMonitorData::Init()
{
	if (IsCreator())
	{
		pErrorData pData = (pErrorData)GetDataPtr();
//		pData->errorOccurred = FALSE;
//		pData->osErrorUsed = FALSE;
//		pData->osError = 0;
//		pData->tcfError = TCAPI_ERR_NONE;
		pData->first = pData->next = pData->numberErrors = 0;
	}
	return TRUE;
}
#ifdef LOG_PERFORMANCE
static void logPerf(char* msg)
{
	if (fpLog)
	{
		SYSTEMTIME sTime;
		GetLocalTime(&sTime);
		fprintf(fpLog, 
			"%02.2d%02.2d-%02.2d:%02.2d:%02.2d.%03.3d: %s",
			sTime.wDay, sTime.wMonth, sTime.wHour, sTime.wMinute, sTime.wSecond, sTime.wMilliseconds,
			msg);

		numLogged++;
		if ((numLogged % 1000) == 0)
			fflush(fpLog);
	}
}
static void openPerf()
{
	struct _stat buf;
	char* dirname = "c:\\tcf";
	int result = _stat(dirname, &buf);
	if (result == 0) // exists
	{
		if (fpLog == NULL)
			fpLog = _fsopen(perfFileName, "at", _SH_DENYNO);
	}
	else
	{
		fpLog = NULL;
	}
}
static void closePerf()
{
	if (fpLog)
	{
		fflush(fpLog);
		fclose(fpLog);
	}
	fpLog = NULL;
}
#endif
