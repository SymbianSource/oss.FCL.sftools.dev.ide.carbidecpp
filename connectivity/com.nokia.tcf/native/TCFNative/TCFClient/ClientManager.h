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
// ClientManager.h: interface for the CClientManager class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_CLIENTMANAGER_H__D8CD8281_5D57_43E9_922D_9532DC8669C4__INCLUDED_)
#define AFX_CLIENTMANAGER_H__D8CD8281_5D57_43E9_922D_9532DC8669C4__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
#include <vector>
#include <list>
#include "ServerClient.h"
#include "InputStream.h"
#include "ErrorMonitorData.h"
#include "TCErrorConstants.h"
#include "TCDebugLog.h"

typedef std::vector<CInputStream*> InputStreamList;
typedef std::vector<CErrorMonitor*> ErrorMonitorList;
//typedef std::vector<CInputStream> InputStreamList;

#define ERRORMONITORLIST_MUTEX_BASENAME		"TCFErrorMonitorList"
#define ERRORMONITORLIST_MUTEX_TIMEOUT		(60000L)
#define INPUTSTREAMLIST_MUTEX_BASENAME		"TCFInputStreamList"
#define INPUTSTREAMLIST_MUTEX_TIMEOUT		(60000L)

#ifdef WIN32
#define SERVER_PROCESS_NAME		"TCFServer.exe"
#define SERVER_LOCKFILE_NAME	"TCFServer.lock"
#define PATH_DELIMITER			'\\'
#else
#error not WIN32
#endif

#define MAX_DLLPATHNAME (2048)

class CClientManager  
{
public:
	CClientManager();
	CClientManager(HINSTANCE hinstDLL);
	virtual ~CClientManager();

	// starting/stopping server
	BOOL StartServer(pServerProcessData pData);
	BOOL StopServer(pServerProcessData pData);
	long StartServer();
	long StopServer();
	BOOL IsServerRunning(); // { return m_ServerRunning; }
	BOOL m_ServerRunning;
	void TerminateServerThroughLockFile(pServerProcessData pData);
	void CreateLockFile(DWORD processId);
	void AppendToLockFile(DWORD processId);
	void DeleteLockFile();
	void DeleteFromLockFile(DWORD processId);
	BOOL IsTCFServerActive(DWORD processId);

	// input stream
	CInputStream* FindInputStream(long inClientId);
	long InputStreamListSize();
	void RemoveInputStream(CInputStream* inputStream);
	void AddInputStream(CInputStream* stream);
	BOOL WaitForStreamListAccess() { return m_StreamListMutex.Wait(); }
	BOOL ReleaseStreamListAccess() { return m_StreamListMutex.Release(); }
	Mutex m_StreamListMutex;

	// error monitors
	CErrorMonitor* FindErrorMonitor(long inClientId);
	long ErrorMonitorListSize();
	void RemoveErrorMonitor(CErrorMonitor* errorMonitor);
	void AddErrorMonitor(CErrorMonitor* monitor);
	BOOL WaitForErrorMonitorListAccess() { return m_ErrorMonitorListMutex.Wait(); }
	BOOL ReleaseErrorMonitorListAccess() { return m_ErrorMonitorListMutex.Release(); }
	Mutex m_ErrorMonitorListMutex;

	CServerCommand* m_Server;
	TCDebugLog* m_DebugLog;
	InputStreamList* m_StreamList;
	ErrorMonitorList* m_ErrorMonitorList;
	char* m_DllLocation;
	HANDLE m_hServer;			// our handle to the server process (per process)
	HANDLE m_hServerThread;		// handle to server main thread (creator process only)
	char m_Version[MAX_VERSION_STRING]; // our version string

	char* m_ServerLockFile; // TCFServer lock file name at the DLL location
	char* m_ServerExeFile;	// TCFServer exe

};

#endif // !defined(AFX_CLIENTMANAGER_H__D8CD8281_5D57_43E9_922D_9532DC8669C4__INCLUDED_)
