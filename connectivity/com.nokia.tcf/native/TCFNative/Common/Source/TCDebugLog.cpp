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
#include "TCDebugLog.h"

TCDebugLog::TCDebugLog()
{
	m_fLog = NULL;
}

TCDebugLog::TCDebugLog(char* baseName, DWORD pid)
{
	m_fLog = NULL;
	char name[30];
	sprintf(name, "%sMutex%d", baseName, pid);
	m_Mutex.Open(name, LOG_MUTEX_TIMEOUT);

	sprintf(m_FileName, "c:\\tcf\\%s%d.txt", baseName, pid);
	m_fLog = _fsopen(m_FileName, "at", _SH_DENYNO);
}

TCDebugLog::TCDebugLog(char* baseName, DWORD pid, DWORD timeout)
{
	m_fLog = NULL;
	char name[30];
	sprintf(name, "%sMutex%d", baseName, pid);
	m_Mutex.Open(name, timeout);

	sprintf(m_FileName, "c:\\tcf\\%s%d.txt", baseName, pid);
	m_fLog = _fsopen(m_FileName, "at", _SH_DENYNO);

#ifdef _DEBUG
//	FILE* f = fopen("c:\\tcf\\tcdebuglog.txt", "at");
//	fprintf(f, "name=%s m_FileName=%s\n", name, m_FileName);
//	fclose(f);
#endif
}
TCDebugLog::~TCDebugLog()
{
	if (m_fLog) 
	{
		fflush(m_fLog);
		fclose(m_fLog);
		m_fLog = NULL;
	}
	m_Mutex.Close();
}

void TCDebugLog::log(char* msg)
{
//	WaitForAccess();

	if (m_fLog)
	{
		SYSTEMTIME sTime;
		GetLocalTime(&sTime);
		fprintf(m_fLog, 
			"%02.2d%02.2d-%02.2d:%02.2d:%02.2d.%03.3d: %s",
			sTime.wDay, sTime.wMonth, sTime.wHour, sTime.wMinute, sTime.wSecond, sTime.wMilliseconds,
			msg);
		fflush(m_fLog);
	}
//	ReleaseAccess();
}
/*
void TCDebugLog::log(char* msg, argType type1, void* arg1)
{
	switch(type1)
	{
	case eCHAR:
		char t = *(char*)arg1;
		break;
	}
}

void TCDebugLog::log(char* msg, argType type1, void* arg1, argType type2, void* arg2)
{
}
void TCDebugLog::log(char* msg, argType type1, void* arg1, argType type2, void* arg2, argType type3, void* arg3)
{
}
*/
void TCDebugLog::logTime()
{
	SYSTEMTIME sTime;
	GetLocalTime(&sTime);
	fprintf(m_fLog, "%02.2d%02.2d-%02.2d:%02.2d:%02.2d.%03.3d: ", sTime.wDay, sTime.wMonth, sTime.wHour, sTime.wMinute, sTime.wSecond, sTime.wMilliseconds);
}
#ifdef TCF_SERVER
//#define _LOG_DEBUG_MUTEX
#else
#endif
BOOL TCDebugLog::WaitForAccess()
{
#ifdef _LOG_DEBUG_MUTEX
	BOOL ok = m_Mutex.Wait();
	if (ok == FALSE)
	{
		log("TCDebugLog::WaitForAccess failed\n");
	}
	else
	{
		log("TCDebugLog::WaitForAccess OK\n");
	}
	return ok;
#else
	return m_Mutex.Wait();
#endif
}

BOOL TCDebugLog::ReleaseAccess()
{
#ifdef _LOG_DEBUG_MUTEX
	BOOL ok = m_Mutex.Release();
	if (ok == FALSE)
	{
		log("TCDebugLog::ReleaseAccess failed\n");
	}
	else
	{
		log("TCDebugLog::ReleaseAccess OK\n");
	}
	return ok;
#else
	return m_Mutex.Release();
#endif
}
