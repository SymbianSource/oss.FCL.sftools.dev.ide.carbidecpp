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
// MessageFile.h: interface for the CMessageFile class.
//
// One per client
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_MESSAGEFILE_H__1049F760_2A8A_491F_A868_B063D52B18E8__INCLUDED_)
#define AFX_MESSAGEFILE_H__1049F760_2A8A_491F_A868_B063D52B18E8__INCLUDED_

#include <share.h>
#include "stdio.h"
#include "mutex.h"
//#include "ServerClient.h"

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000


//----------------------------------
// Message File Mutex
//----------------------------------
// Mutex name is basename + clientID
#define MESSAGEFILE_MUTEX_BASENAME		"TCFMessageFileMutex"
#define MESSAGEFILE_MUTEX_TIMEOUT		(60000L)

class CMessageFile  
{
public:
	CMessageFile(CHAR* pFilePath, long inClientID);
	virtual ~CMessageFile();


	long Open();
	long Close();
	long AddMessage(DWORD inLength, BYTE* inMessage);
	void UnLockMessageFile() { ReleaseAccess(); }

	BOOL WaitForAccess() { if (m_FileLocked) return TRUE; else { m_FileLocked = TRUE; return m_Mutex.Wait();} };
	BOOL ReleaseAccess() { if (m_FileLocked) { m_FileLocked = FALSE; return m_Mutex.Release();} else return TRUE; };
	BOOL isOpen() { return m_Open; }
	long ClearFile();
	void FlushFile(BOOL numberTimeOut = FALSE);

	long m_ClientID;
	BOOL m_Open;
	Mutex m_Mutex;
	CHAR m_FilePath[MAX_FILEPATH];
	BOOL m_FileLocked;
	FILE* m_hFile;
	DWORD m_numWritten;		// total number written
	DWORD m_numWrittenSinceLastFlush;	// number written and not flushed yet

	// logging performance
// for performance - independent of debug logging
//#define LOG_FILE_PERFORMANCE
#ifdef LOG_FILE_PERFORMANCE
	char* perfFileName;
	FILE *fpLog;
	int numLogged;
	void logPerf(char* msg);
	void openPerf();
	void closePerf();
#define OPENFILEPERF() openPerf()
#define LOGFILEPERF(s) logPerf(s)
#define CLOSEFILEPERF() closePerf()
#else
#define OPENFILEPERF()
#define LOGFILEPERF(s)
#define CLOSEFILEPERF()
#endif



};

#endif // !defined(AFX_MESSAGEFILE_H__1049F760_2A8A_491F_A868_B063D52B18E8__INCLUDED_)
