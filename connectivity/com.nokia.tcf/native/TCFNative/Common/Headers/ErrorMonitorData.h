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
#ifndef __ERRRORMONITORDATA_H__
#define __ERRRORMONITORDATA_H__

#include "shareddata.h"
#include "mutex.h"

// Error memory written by Server for 1 client
#define MAX_ERRORS			200
typedef struct tagErrorEntry
{
	BOOL osErrorUsed;
	LONG tcfError;
	DWORD osError;
} *pErrorEntry, ErrorEntry;
typedef struct tagErrorData
{
	int first;			// first error in queue
	int next;			// last error in queue
	int numberErrors;	// number of errors in queue
	ErrorEntry errors[MAX_ERRORS];
} *pErrorData, ErrorData;


// base name (each client has different name)
#define ERRORMONITORDATA_MAP_BASENAME	"TCFErrorMonitorData"
#define ERRORMONITORDATA_MAP_SIZE		(sizeof(ErrorData))

// mutex to restrict access (each client has different name)
#define ERRORMONITORDATA_MUTEX_BASENAME		"TCFErrorMonitorMutex"
#define ERRORMONITORDATA_MUTEX_TIMEOUT		(60000L)

class CErrorMonitorData : public CSharedData
{
public:
	BOOL Init();
};

class CErrorMonitor
{
public:
	CErrorMonitor();
	CErrorMonitor(long inClientID);
	~CErrorMonitor();

	BOOL CreateData();
	BOOL IsThisClient(long inClientID) { return (inClientID == m_ClientID); }
	long GetClientId() { return m_ClientID; }

	BOOL PutError(LONG tcfError, BOOL osErrorUsed=FALSE, DWORD osError=0);
	BOOL GetError(LONG* tcfError, BOOL* osErrorUsed=NULL, DWORD* osError=NULL);
	void ResetErrorQueue(pErrorData pData) { pData->first = pData->next = pData->numberErrors = 0; }
	BOOL IsErrorQueueEmpty(pErrorData pData) { return (pData->numberErrors <= 0); }
	BOOL IsErrorQueueFull(pErrorData pData) { return (pData->numberErrors >= MAX_ERRORS); }

private:
	pErrorData GetDataPtr() { return (pErrorData)m_Data.GetDataPtr(); }

	BOOL WaitForAccess() { return m_Mutex.Wait(); };
	BOOL ReleaseAccess() { return m_Mutex.Release(); };

	long m_ClientID;
	CErrorMonitorData m_Data;
	Mutex m_Mutex;
};
#endif// __ERRRORMONITORDATA_H__
