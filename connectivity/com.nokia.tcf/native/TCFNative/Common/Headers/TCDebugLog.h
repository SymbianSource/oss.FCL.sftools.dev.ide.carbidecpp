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
#ifndef __TCDEBUGLOG_H__
#define __TCDEBUGLOG_H__
#include <stdio.h>
#include <share.h>
#include "mutex.h"


#define LOG_BASENAME	"c:\\TCDebugLog"
#define LOG_MUTEX_BASENAME	"TCDebugLogMutex"
#define LOG_MUTEX_TIMEOUT	(60000L)

class TCDebugLog
{
public:
	TCDebugLog();
	TCDebugLog(char* baseName, DWORD id);
	TCDebugLog(char* baseName, DWORD id, DWORD timeout);
	~TCDebugLog();

	void log(char* msg);

//private:
//	BOOL WaitForAccess() { return m_Mutex.Wait(); };
//	BOOL ReleaseAccess() { return m_Mutex.Release(); };
	BOOL WaitForAccess();
	BOOL ReleaseAccess();

	void logTime();
	Mutex m_Mutex;
	FILE* m_fLog;
	char m_FileName[80];

};
#endif //__TCDEBUGLOG_H__
