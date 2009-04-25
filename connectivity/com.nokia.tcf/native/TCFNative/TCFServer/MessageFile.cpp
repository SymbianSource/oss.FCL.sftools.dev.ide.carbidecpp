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
// MessageFile.cpp: implementation of the CMessageFile class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "ServerClient.h"
#include "MessageFile.h"
#include "ServerManager.h"
#include "TCErrorConstants.h"
#include <stdio.h>
#include <sys/stat.h>

//#define USE_TEXT_FILE
#ifdef USE_TEXT_FILE
#define WRITE_MODE		"w+t"
#define APPEND_MODE		"a+t"
#else
#define WRITE_MODE		"w+b"
#define APPEND_MODE		"a+b"
#endif

#define FLUSH_FREQ	2000	// # messages to write between flushing (if buffered)
#ifdef _DEBUG
extern BOOL gDoLogging;
#endif

//#define LOG_MESSAGEFILE
#if defined(LOG_MESSAGEFILE) && defined(_DEBUG)
extern CServerManager* gManager;
#define TCDEBUGOPEN() if (gDoLogging) { gManager->m_DebugLog->WaitForAccess(); }
#define TCDEBUGLOGS(s) if (gDoLogging) { sprintf(TCDebugMsg,"%s", s); gManager->m_DebugLog->log(TCDebugMsg); }
#define TCDEBUGLOGA1(s, a1) if (gDoLogging) { sprintf(TCDebugMsg, s, a1); gManager->m_DebugLog->log(TCDebugMsg); }
#define TCDEBUGLOGA2(s, a1, a2) if (gDoLogging) { sprintf(TCDebugMsg, s, a1, a2); gManager->m_DebugLog->log(TCDebugMsg); }
#define TCDEBUGLOGA3(s, a1, a2, a3) if (gDoLogging) { sprintf(TCDebugMsg, s, a1, a2, a3); gManager->m_DebugLog->log(TCDebugMsg); }
#define TCDEBUGCLOSE() if (gDoLogging) { gManager->m_DebugLog->ReleaseAccess(); }
#else
#define TCDEBUGOPEN()
#define TCDEBUGLOGS(s)
#define TCDEBUGLOGA1(s, a1)
#define TCDEBUGLOGA2(s, a1, a2)
#define TCDEBUGLOGA3(s, a1, a2, a3)
#define TCDEBUGCLOSE()
#endif

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

CMessageFile::CMessageFile(CHAR* pFilePath, long inClientID)
{
	m_ClientID = inClientID;
	m_FileLocked = FALSE;
	strncpy(m_FilePath, pFilePath, MAX_FILEPATH);

	char toString[30];
	sprintf(toString, "%s%04.4d", MESSAGEFILE_MUTEX_BASENAME, m_ClientID);

	m_Mutex.Open(toString, MESSAGEFILE_MUTEX_TIMEOUT);
	m_hFile = NULL;
	m_numWritten = 0;
	m_numWrittenSinceLastFlush = 0;
	m_Open = FALSE;

#ifdef LOG_FILE_PERFORMANCE
	perfFileName="c:\\tcf\\serverfileperf.txt";
	fpLog = NULL;
	numLogged=0;
#endif
	OPENFILEPERF();
}

CMessageFile::~CMessageFile()
{
	CLOSEFILEPERF();
	Close();
	m_Mutex.Close();

}

long CMessageFile::AddMessage(DWORD inLength, BYTE* inMessage)
{
	long err = TCAPI_ERR_NONE;

	BOOL gotIt = WaitForAccess(); // will lock on first access only
	size_t lenWritten = 0;

#ifdef USE_TEXT_FILE
	lenWritten = inLength;
	int textStart = 20;
	if (inMessage[9] == 0x5a) textStart = 64;
	for (int i = 0; i < textStart; i++)
		fprintf(m_hFile, "%02.2X ", inMessage[i]);

	for (i = textStart; i < (int)inLength; i++)
	{
		if (isprint(inMessage[i]))
			fprintf(m_hFile, "%c", inMessage[i]);
		else
			fprintf(m_hFile, "%02.2X ", inMessage[i]);
	}
	fprintf(m_hFile,"\n");
#else
	lenWritten = fwrite(inMessage, 1, inLength, m_hFile);
#endif
	if (lenWritten < inLength)
	{
		err = TCAPI_ERR_WRITING_FILE;
	}
	else
	{
		m_numWrittenSinceLastFlush++;
		m_numWritten++;
		if ((m_numWrittenSinceLastFlush % FLUSH_FREQ) == 0)
		{
			FlushFile(TRUE);
		}
	}
	LOGFILEPERF("AddMessage\n");

	// no ReleaseAccess this is done by Connection when all bytes processed in buffer and
	//   calls all clients unlock method

	return err;
}

long CMessageFile::Open()
{
	TCDEBUGOPEN();
	long err = TCAPI_ERR_NONE;

	TCDEBUGLOGS("CMessageFile::Open\n");
	WaitForAccess();

	if (m_hFile)
		fclose(m_hFile);

	m_hFile = _fsopen(m_FilePath, APPEND_MODE, _SH_DENYNO);

	if (m_hFile == NULL)
	{
		err = TCAPI_ERR_FILE_DOES_NOT_EXIST;
	}
	else
		m_Open = TRUE;


	ReleaseAccess();

	TCDEBUGCLOSE();
	return err;
}

long CMessageFile::Close()
{
	TCDEBUGOPEN();
	long err = TCAPI_ERR_NONE;

	TCDEBUGLOGA1("CMessageFile::Close numWritten=%d\n", m_numWritten);
	WaitForAccess();

	if (m_hFile)
	{
		fclose(m_hFile);
		m_hFile = NULL;
	}

	ReleaseAccess();

	TCDEBUGCLOSE();
	return err;
}

long CMessageFile::ClearFile()
{
	TCDEBUGOPEN();
	long err = TCAPI_ERR_NONE;

	TCDEBUGLOGA2("CMessageFile::ClearFile m_hFile=%x numWritten=%d\n", m_hFile, m_numWritten);
	WaitForAccess();

	if (m_hFile)
	{
		fclose(m_hFile);
		m_hFile = NULL;
		m_Open = FALSE;
	}
	m_numWritten = 0;
	m_numWrittenSinceLastFlush = 0;

	m_hFile = _fsopen(m_FilePath, WRITE_MODE, _SH_DENYNO);

	if (m_hFile == NULL)
	{
		err = TCAPI_ERR_FILE_DOES_NOT_EXIST;
	}
	else
		m_Open = TRUE;

	LOGFILEPERF("ClearFile\n");

	TCDEBUGLOGA1("CMessageFile::ClearFile m_hFile=%x\n", m_hFile);
	ReleaseAccess();

	TCDEBUGCLOSE();
	return err;
}
void CMessageFile::FlushFile(BOOL numberTimeOut)
{
	if (m_hFile && m_numWrittenSinceLastFlush > 0)
	{
		fflush(m_hFile);
		m_numWrittenSinceLastFlush = 0;
		if (numberTimeOut)
			LOGFILEPERF("FlushFile <number>\n");
		else
			LOGFILEPERF("FlushFile <time>\n");
	}
}
#ifdef LOG_FILE_PERFORMANCE
void CMessageFile::logPerf(char* msg)
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
void CMessageFile::openPerf()
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
void CMessageFile::closePerf()
{
	if (fpLog)
	{
		fflush(fpLog);
		fclose(fpLog);
	}
	fpLog = NULL;
}
#endif
