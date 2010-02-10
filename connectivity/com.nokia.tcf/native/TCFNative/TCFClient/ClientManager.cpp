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
// ClientManager.cpp: implementation of the CClientManager class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "ClientManager.h"
#include "TCErrorConstants.h"
#include "resource.h"
#include <stdio.h>

#ifdef _DEBUG
extern BOOL gDoLogging;
extern char TCDebugMsg[100];
#define TCDEBUGOPEN() if (gDoLogging) m_DebugLog->WaitForAccess();
#define TCDEBUGLOGS(s) if (gDoLogging) sprintf(TCDebugMsg,"%s", s); if (gDoLogging) m_DebugLog->log(TCDebugMsg);
#define TCDEBUGLOGA1(s, a1) if (gDoLogging) sprintf(TCDebugMsg, s, a1); if (gDoLogging) m_DebugLog->log(TCDebugMsg);
#define TCDEBUGLOGA2(s, a1, a2) if (gDoLogging) sprintf(TCDebugMsg, s, a1, a2); if (gDoLogging) m_DebugLog->log(TCDebugMsg);
#define TCDEBUGLOGA3(s, a1, a2, a3) if (gDoLogging) sprintf(TCDebugMsg, s, a1, a2, a3); if (gDoLogging) m_DebugLog->log(TCDebugMsg);
#define TCDEBUGCLOSE() if (gDoLogging) m_DebugLog->ReleaseAccess();
#else
#define TCDEBUGOPEN()
#define TCDEBUGLOGS(s)
#define TCDEBUGLOGA1(s, a1)
#define TCDEBUGLOGA2(s, a1, a2)
#define TCDEBUGLOGA3(s, a1, a2, a3)
#define TCDEBUGCLOSE()
#endif

#ifdef _DEBUG
static char* GetErrorText(DWORD inError);
#endif

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
CClientManager::CClientManager()
{
	m_Server = NULL;
	m_StreamList = NULL;
	m_ErrorMonitorList = NULL;
	m_DebugLog = NULL;
	m_DllLocation = NULL;
	m_hServer = NULL;
	m_hServerThread = NULL;
	m_Version[0] = NULL;
	m_ServerLockFile = NULL;
	m_ServerExeFile = NULL;
}
CClientManager::CClientManager(HINSTANCE hinstDLL)
{
#ifdef _DEBUG
	if (gDoLogging)
		m_DebugLog = new TCDebugLog("TCF_ClientLog", ::GetCurrentProcessId());
	else
		m_DebugLog = NULL;

#else
	m_DebugLog = NULL;
#endif
	TCDEBUGOPEN();
	TCDEBUGLOGS("CClientManager::CClientManager\n");

	m_Server = new CServerCommand();

	// lock server access (it might be running)
	m_Server->WaitforServerPipeAccess();

	m_StreamList = new InputStreamList();
	m_StreamList->clear();

	m_ErrorMonitorList = new ErrorMonitorList();
	m_ErrorMonitorList->clear();

	m_DllLocation = new char[MAX_DLLPATHNAME];
	::GetModuleFileName(hinstDLL, m_DllLocation, MAX_DLLPATHNAME);

	char exeDirectory[MAX_DLLPATHNAME] = {0};
	strncpy(exeDirectory, m_DllLocation, MAX_DLLPATHNAME);
	size_t len = strlen(exeDirectory);
	// remove file
	for (int i = len-1; i > 0; i--)
	{
		if (exeDirectory[i] == PATH_DELIMITER)
			break;
	}
	exeDirectory[i] = NULL;

	m_ServerExeFile = new char[MAX_DLLPATHNAME];
	sprintf(m_ServerExeFile, "\"%s%c%s\"", exeDirectory, PATH_DELIMITER, SERVER_PROCESS_NAME);

	m_ServerLockFile = new char[MAX_DLLPATHNAME];
	sprintf(m_ServerLockFile, "%s%c%s", exeDirectory, PATH_DELIMITER, SERVER_LOCKFILE_NAME);

	char name[100];
	sprintf(name, "%s%ld", ERRORMONITORLIST_MUTEX_BASENAME, ::GetCurrentProcessId());
	m_ErrorMonitorListMutex.Open(name, ERRORMONITORLIST_MUTEX_TIMEOUT);

	sprintf(name, "%s%ld", INPUTSTREAMLIST_MUTEX_BASENAME, ::GetCurrentProcessId());
	m_StreamListMutex.Open(name, INPUTSTREAMLIST_MUTEX_TIMEOUT);

	m_hServer = NULL;
	m_hServerThread = NULL;

	// release server access
	m_Server->ReleaseServerPipeAccess();
	int ret = ::LoadString(hinstDLL, IDS_VERSION, m_Version, MAX_VERSION_STRING);

	TCDEBUGCLOSE();
}

CClientManager::~CClientManager()
{
	TCDEBUGOPEN();
	TCDEBUGLOGS("CClientManager::~CClientManager\n");
	pServerProcessData pData = m_Server->GetProcessPtr();

	if (m_Server)
	{
		delete m_Server;
		m_Server = NULL;
	}

	WaitForStreamListAccess();
	TCDEBUGLOGA1("CClientManager::~CClientManager: erasing stream list size=%d\n", InputStreamListSize());
	if (m_StreamList)
	{
		InputStreamList::iterator iter;
		for (iter = m_StreamList->begin(); iter != m_StreamList->end(); iter++)
		{
			TCDEBUGLOGS("CClientManager::~CClientManager: erasing stream list next 1\n");
//			m_StreamList->erase(iter);
			delete *iter;
//			TCDEBUGLOGS("CClientManager::~CClientManager: erasing stream list next 2\n");
		}
		m_StreamList->clear();
		TCDEBUGLOGS("CClientManager::~CClientManager: erasing stream list done 1\n");
		delete m_StreamList;
		TCDEBUGLOGS("CClientManager::~CClientManager: erasing stream list done 2\n");
	}
	ReleaseStreamListAccess();
	m_StreamListMutex.Close();

	WaitForErrorMonitorListAccess();
	TCDEBUGLOGA1("CClientManager::~CClientManager: erasing monitor list size=%d\n", ErrorMonitorListSize());
	if (m_ErrorMonitorList)
	{
		ErrorMonitorList::iterator iter;
		for (iter = m_ErrorMonitorList->begin(); iter != m_ErrorMonitorList->end(); iter++)
		{
			TCDEBUGLOGS("CClientManager::~CClientManager: erasing monitor list next 1\n");
//			m_ErrorMonitorList->erase(iter);
			delete *iter;
//			TCDEBUGLOGS("CClientManager::~CClientManager: erasing monitor list next 1\n");
		}
		m_ErrorMonitorList->clear();
		TCDEBUGLOGS("CClientManager::~CClientManager: erasing monitor list done 1\n");
		delete m_ErrorMonitorList;
		TCDEBUGLOGS("CClientManager::~CClientManager: erasing monitor list done 2\n");
	}
	ReleaseErrorMonitorListAccess();
	m_ErrorMonitorListMutex.Close();

	if (m_DllLocation)
	{
		delete[] m_DllLocation;
		m_DllLocation = NULL;
	}

	if (m_ServerLockFile)
	{
		delete[] m_ServerLockFile;
		m_ServerLockFile = NULL;
	}

	if (m_ServerExeFile)
	{
		delete[] m_ServerExeFile;
		m_ServerExeFile = NULL;
	}
	TCDEBUGLOGS("CClientManager::~CClientManager: closing log\n");
	TCDEBUGCLOSE();
	if (m_DebugLog)
	{
		delete m_DebugLog;
		m_DebugLog = NULL;
	}
}
CErrorMonitor*
CClientManager::FindErrorMonitor(long inClientId)
{
	CErrorMonitor* errorMonitor = NULL;
	ErrorMonitorList::iterator iter;

	for (iter = m_ErrorMonitorList->begin(); iter != m_ErrorMonitorList->end(); iter++)
	{
		if ((*iter)->IsThisClient(inClientId))
		{
			errorMonitor = *iter;
			break;
		}
	}
	return errorMonitor;
}
long CClientManager::ErrorMonitorListSize()
{
	long size = m_ErrorMonitorList->size();

	return size;
}
void CClientManager::AddErrorMonitor(CErrorMonitor* monitor)
{
	m_ErrorMonitorList->push_back(monitor);
}
void CClientManager::RemoveErrorMonitor(CErrorMonitor* monitor)
{
	ErrorMonitorList::iterator iter;

	for (iter = m_ErrorMonitorList->begin(); iter != m_ErrorMonitorList->end(); iter++)
	{
		if ((*iter)->IsThisClient(monitor->GetClientId()))
		{
			m_ErrorMonitorList->erase(iter);
			break;
		}
	}
}

CInputStream*
CClientManager::FindInputStream(long inClientId)
{
	CInputStream* inputStream = NULL;
	InputStreamList::iterator iter;

	for (iter = m_StreamList->begin(); iter != m_StreamList->end(); iter++)
	{
		if ((*iter)->IsThisClient(inClientId))
//		if ((*iter).IsThisClient(inClientId))
		{
			inputStream = *iter;
//			inputStream = iter;
			break;
		}
	}
	return inputStream;
}
long CClientManager::InputStreamListSize()
{
	long size = m_StreamList->size();

	return size;
}
void CClientManager::AddInputStream(CInputStream* stream)
{
	m_StreamList->push_back(stream);
//	m_StreamList->push_back(*stream);

}
void CClientManager::RemoveInputStream(CInputStream* stream)
{
	InputStreamList::iterator iter;

	for (iter = m_StreamList->begin(); iter != m_StreamList->end(); iter++)
	{
		if ((*iter)->IsThisClient(stream->GetClientId()))
//		if ((*iter).IsThisClient(stream->GetClientId()))
		{
			m_StreamList->erase(iter);
			break;
		}
	}
}

BOOL CClientManager::StartServer(pServerProcessData pData)
{
	TCDEBUGLOGA1("CClientManager::StartServer numRefs = %d\n",pData->numRefs);

	BOOL serverStarted = FALSE;
	// server is ref counted
	// refcount = 0 => server is not running
	// refcount > 0 => server already started by some other process
	if (pData->numRefs == 0)
	{
		// server not running
		// get exe location
		char exeLocation[MAX_DLLPATHNAME] = {0};
		strncpy(exeLocation, m_DllLocation, MAX_DLLPATHNAME);
		size_t len = strlen(exeLocation);
		// remove file
		for (int i = len-1; i > 0; i--)
		{
			if (exeLocation[i] == PATH_DELIMITER)
				break;
		}
		exeLocation[i] = NULL;
		char quotedLocation[MAX_DLLPATHNAME] = {0};
		sprintf(quotedLocation, "\"%s%c%s\"", exeLocation, PATH_DELIMITER, SERVER_PROCESS_NAME);

		TCDEBUGLOGA1("  exeLocation=%s\n", quotedLocation);

		// create process
		STARTUPINFO si;
		memset(&si,0,sizeof(si));
		si.cb = sizeof(si);
		memset(&pData->serverProcess, 0, sizeof(pData->serverProcess));
		pData->serverProcess.hProcess = NULL;
		if (!::CreateProcess(
			NULL,			// module location
			quotedLocation,	// command line
			NULL,			// process attributes
			NULL,			// thread attributes
			FALSE,			// inherit our handles
			CREATE_NO_WINDOW,	// no window
			NULL,			// use our environment
			NULL,			// user our current directory
			&si,			// startup info
			&pData->serverProcess)) // process info
		{
			// TODO: error creating process
		}
		else
		{
			// we are the creator so save handles for later
			m_hServer = pData->serverProcess.hProcess;
			m_hServerThread = pData->serverProcess.hThread;
			// add a refcount
			pData->numRefs++;
			serverStarted = TRUE;
		}
	}
	else
	{
		// already running
		// add a refcount and open our process handle to it
		pData->numRefs++;
		m_hServer = ::OpenProcess(0, FALSE, pData->serverProcess.dwProcessId);
		serverStarted = TRUE;
	}
	TCDEBUGLOGA1("CClientManager::StartServer serverStarted=%d\n", serverStarted);
	return serverStarted;
}

BOOL CClientManager::StopServer(pServerProcessData pData)
{
	TCDEBUGLOGS("CClientManager::StopServer\n");

	BOOL serverStopped = FALSE;

	if (pData->serverProcess.hProcess == NULL || pData->numRefs <= 0)
	{
		serverStopped = TRUE;
	}
	else
	{
		TCDEBUGLOGA1(" numRefs = %d\n",pData->numRefs); 

		// substract ref count
		pData->numRefs--;
		// if refcount == 0 then really stop the server process
		if (pData->numRefs <= 0)
		{
			// last client process is closing
			// tell server to exit
			ServerCommandData cmdrsp;
			cmdrsp.command = eCmdExit;
		TCDEBUGLOGS(" SendCommand eCmdExit\n");
			m_Server->SendCommand(&cmdrsp);
		TCDEBUGLOGS(" GetResponse eExit\n");
			m_Server->GetResponse(&cmdrsp);
			// wait for process to exit
		TCDEBUGLOGS(" WaitForSingleObject start\n");
			WaitForSingleObject(m_hServer, 10000L /*INFINITE*/);
		TCDEBUGLOGS(" WaitForSingleObject found\n");

			if (m_hServer != NULL)
				CloseHandle(m_hServer);

			if (m_hServerThread != NULL)
				CloseHandle(m_hServerThread);
		}
		else
		{
			// just close our handle to server process
			if (m_hServer != NULL)
				CloseHandle(m_hServer);

			if (m_hServerThread != NULL)
				CloseHandle(m_hServerThread);
		}
	}

	TCDEBUGLOGS("CClientManager::StopServer end\n");
	return TRUE;
}
long CClientManager::StartServer()
{
	long ret = TCAPI_ERR_NONE;
	pServerProcessData pData = m_Server->GetProcessPtr();

	TCDEBUGLOGA3("CClientManager::StartServer this = %x m_hServer = %x numRefs = %d\n", this, m_hServer, pData->numRefs);
//	TCDEBUGLOGA1("  mgrRefs = %d\n", m_MgrServerRef);

	BOOL serverStarted = FALSE;
	// server is ref counted
	// refcount = 0 => server is not running
	// refcount > 0 => server already started by some other process

	// terminate the TCFServer if it is already running
	TerminateServerThroughLockFile(pData);

	if (pData->numRefs == 0)
	{
		// server not running
		TCDEBUGLOGA1("  TCFServer exe =%s\n", m_ServerExeFile);
		TCDEBUGLOGA1("  TCFServer lock=%s\n", m_ServerLockFile);


		// create process
		STARTUPINFO si;
		memset(&si,0,sizeof(si));
		si.cb = sizeof(si);
		memset(&pData->serverProcess, 0, sizeof(pData->serverProcess));
		pData->serverProcess.hProcess = NULL;
		if (!::CreateProcess(
			NULL,			// module location
			m_ServerExeFile,	// command line
			NULL,			// process attributes
			NULL,			// thread attributes
			FALSE,			// inherit our handles
			CREATE_NO_WINDOW,	// no window
			NULL,			// use our environment
			NULL,			// user our current directory
			&si,			// startup info
			&pData->serverProcess)) // process info
		{
			// TODO: error creating process
		}
		else
		{
			// we are the creator so save handles for later
			m_hServer = pData->serverProcess.hProcess;
			m_hServerThread = pData->serverProcess.hThread;
			// add a refcount
			pData->numRefs++;
			serverStarted = TRUE;
			TCDEBUGLOGA3("CClientManager::StartServer created m_hServer = %x processId = %d numRefs = %d\n", m_hServer, pData->serverProcess.dwProcessId, pData->numRefs);

			// create lock file and save process ID
			TCDEBUGLOGS("CClientManager::StartServer CreateLockFile\n");
			CreateLockFile(pData->serverProcess.dwProcessId);
		}
	}
	else
	{
		// already running
		// add a refcount and open our process handle to it only if we haven't opened it already
		pData->numRefs++;
		if (m_hServer == NULL)
			m_hServer = ::OpenProcess(SYNCHRONIZE|PROCESS_TERMINATE, FALSE, pData->serverProcess.dwProcessId);
		if (m_hServer == 0)
		{
			TCDEBUGLOGA1("CClientManager::StartServer opened m_hServer null error=%d\n", ::GetLastError());
		}
		m_hServerThread = NULL;		// only creator gets real thread handle
		serverStarted = TRUE;
		TCDEBUGLOGA3("CClientManager::StartServer opened m_hServer = %x processId = %d numRefs = %d\n", m_hServer, pData->serverProcess.dwProcessId, pData->numRefs);
		// save our process id to lock file
		AppendToLockFile(pData->serverProcess.dwProcessId);
	}
	if (serverStarted)
		m_ServerRunning = TRUE;

	TCDEBUGLOGA1("CClientManager::StartServer end numRefs = %d\n", pData->numRefs);
	return ret;
}
BOOL CClientManager::IsTCFServerActive(DWORD processId)
{
	HANDLE h = ::OpenProcess(PROCESS_QUERY_INFORMATION, FALSE, processId);
	if (h)
	{
		// is it really still alive?
		DWORD exitCode = -5;
		BOOL exitCall = ::GetExitCodeProcess(h, &exitCode);
		::CloseHandle(h);
		if (exitCall == TRUE && exitCode != STILL_ACTIVE)
		{
			// TCFServer is really dead
			return FALSE;
		}
		else
		{
			// TCFServer is still active
			return TRUE;
		}
	}
	else
	{
		// TCFServer is really dead
		return FALSE;
	}
}
long CClientManager::StopServer()
{
	long ret = TCAPI_ERR_NONE;
	pServerProcessData pData = m_Server->GetProcessPtr();

	TCDEBUGLOGA3("CClientManager::StopServer this = %x m_hServer = %x numRefs = %d\n", this, m_hServer, pData->numRefs);

	BOOL serverStopped = FALSE;

	if (pData->serverProcess.hProcess == NULL || pData->numRefs <= 0)
	{
		TCDEBUGLOGS("CClientManager::StopServer hProcess NULL or numRefs <= 0\n");
		serverStopped = TRUE;
	}
#if (0)
	else if (m_hServer == NULL)
	{
		// we've already closed our handle to server
		// don't close it again
		TCDEBUGLOGS("CClientManager::StopServer m_hServer null\n");
	}
#endif
	else
	{
		// substract ref count
		pData->numRefs--;
		if (pData->numRefs < 0) pData->numRefs = 0;

		bool sendStop = true;
		if (!IsTCFServerActive(pData->serverProcess.dwProcessId))
		{
			sendStop = false;
			pData->numRefs = 0;
		}

		// if refcount == 0 then really stop the server process
		if (pData->numRefs == 0)
		{
			if (sendStop)
			{
				// last client process is closing
				// tell server to exit
				ServerCommandData cmdrsp;
				cmdrsp.command = eCmdExit;
				
				TCDEBUGLOGS(" SendCommand eCmdExit\n");
				m_Server->SendCommand(&cmdrsp);
				TCDEBUGLOGS(" GetResponse eExit\n");
				m_Server->GetResponse(&cmdrsp);
				
				// wait for process to exit
				TCDEBUGLOGS(" WaitForSingleObject start\n");
				DWORD waitErr = ::WaitForSingleObject(m_hServer, 10000L /*INFINITE*/);
				TCDEBUGLOGA1("CClientManager::StopServer WaitForSingleObject = %d\n", waitErr);
			}

			// now close our handle to server process
			if (m_hServer != NULL)
			{
				CloseHandle(m_hServer);
				m_hServer = NULL;
			}

			if (m_hServerThread != NULL)
			{
				CloseHandle(m_hServerThread);
				m_hServerThread = NULL;
			}
			serverStopped = TRUE;

			// delete lock file
			TCDEBUGLOGS("CClientManager::StopServer DeleteLockFile\n");
			DeleteLockFile();
		}
		else
		{
			// just close our handle to server process

			if (m_hServer != NULL)
			{
				CloseHandle(m_hServer);
				m_hServer = NULL;
			}

			if (m_hServerThread != NULL)
			{
				CloseHandle(m_hServerThread);
				m_hServerThread = NULL;
			}
			DeleteFromLockFile(pData->serverProcess.dwProcessId);
		}
	}
	if (serverStopped)
		m_ServerRunning = FALSE;

	TCDEBUGLOGA1("CClientManager::StopServer end numRefs = %d\n", pData->numRefs);
	return ret;
}

BOOL CClientManager::IsServerRunning()
{
	pServerProcessData pData = m_Server->GetProcessPtr();
	if (IsTCFServerActive(pData->serverProcess.dwProcessId))
	{
		return TRUE;
	}
	else
	{
		return FALSE;
	}
}

void CClientManager::CreateLockFile(DWORD processId)
{
	if (m_ServerLockFile != NULL)
	{
		FILE* f = fopen(m_ServerLockFile, "wt");
		TCDEBUGLOGA1("CClientManager::CreateLockFile f=%x\n", f);

		if (f)
		{
			DWORD callingProcessId = ::GetCurrentProcessId();
			TCDEBUGLOGA2("CClientManager::CreateLockFile callingProcessId=%d processId=%d\n", callingProcessId, processId);
			fprintf(f, "%ld %ld\n", callingProcessId, processId);
			fclose(f);
		}
		else
		{
			DWORD err = ::GetLastError();
			TCDEBUGLOGA2("CClientManager::CreateLockFile fopenErr=%d:%s\n", err, GetErrorText(err));
		}
	}
}
void CClientManager::AppendToLockFile(DWORD processId)
{
	if (m_ServerLockFile != NULL)
	{
		FILE* f = fopen(m_ServerLockFile, "at");
		TCDEBUGLOGA1("CClientManager::AppendToLockFile f=%x\n", f);

		if (f)
		{
			DWORD callingProcessId = ::GetCurrentProcessId();
			TCDEBUGLOGA2("CClientManager::AppendToLockFile callingProcessId=%d processId=%d\n", callingProcessId, processId);
			fprintf(f, "%ld %ld\n", callingProcessId, processId);
			fclose(f);
		}
		else
		{
			DWORD err = ::GetLastError();
			TCDEBUGLOGA2("CClientManager::AppendToLockFile fopenErr=%d:%s\n", err, GetErrorText(err));
		}
	}
}
void CClientManager::DeleteLockFile()
{
	if (m_ServerLockFile != NULL)
	{
		TCDEBUGLOGS("CClientManager::DeleteLockFile\n");
		::remove(m_ServerLockFile);
	}
}

void CClientManager::DeleteFromLockFile(DWORD serverProcessId)
{
	DWORD creatorIds[10];
	DWORD serverIds[10];
	int numIds = 0;

	DWORD ourProcessId = ::GetCurrentProcessId();

	if (m_ServerLockFile != NULL)
	{
		DWORD attr = ::GetFileAttributes(m_ServerLockFile);
		TCDEBUGLOGA1("CClientManager::DeleteFromLockFile attr=%x\n", attr);

		if (attr != 0xffffffff) // error
		{
			// file exists
			// read the process Ids from it 

			FILE *f = fopen(m_ServerLockFile, "rt");
			TCDEBUGLOGA1("CClientManager::DeleteFromLockFile f=%x\n", f);
			if (f)
			{
				BOOL done = FALSE;
				while (!done)
				{
					DWORD creatorId = 0xffffffff;
					DWORD serverId = 0xffffffff;
					int n = fscanf(f, "%ld %ld\n", &creatorId, &serverId);
					if (n == 2)
					{
						TCDEBUGLOGA3("CClientManager::DeleteFromLockFile numIds=%d creatorId=%d serverId=%d\n", numIds, creatorId, serverId);
						if (creatorId != ourProcessId || serverId != serverProcessId)
						{
							creatorIds[numIds] = creatorId;
							serverIds[numIds] = serverId;
							numIds++;
							if (numIds > 9)
								done = TRUE;
						}
					}
					else
					{
						done = TRUE;
					}
				}
				fclose(f);
			}

			// now rewrite lock file without us
			::remove(m_ServerLockFile);
			if (numIds > 0)
			{
				f = fopen(m_ServerLockFile, "wt");
				if (f)
				{
					for (int i = 0; i < numIds; i++)
					{
						fprintf(f, "%ld %ld\n", creatorIds[i], serverIds[i]);
					}
					fclose(f);
				}
			}
		}
	}
}

// Currently assumes there is only ONE TCFServer, but multiple client processes (that use that server)
// we should not have more than a few Carbide processes connecting to the same TCFServer
void CClientManager::TerminateServerThroughLockFile(pServerProcessData pData)
{
	DWORD creatorIds[10];
	DWORD serverIds[10];
	BOOL liveCaller[10];
	int numIds = 0;
	if (m_ServerLockFile != NULL)
	{
		DWORD attr = ::GetFileAttributes(m_ServerLockFile);
		TCDEBUGLOGA1("CClientManager::TerminateServerThroughLockFile attr=%x\n", attr);

		if (attr != 0xffffffff) // error
		{
			// file exists
			// read the process Ids from it 

			FILE *f = fopen(m_ServerLockFile, "rt");
			TCDEBUGLOGA1("CClientManager::TerminateServerThroughLockFile f=%x\n", f);
			if (f)
			{
				BOOL done = FALSE;
				while (!done)
				{
					DWORD creatorId = 0xffffffff;
					DWORD serverId = 0xffffffff;
					int n = fscanf(f, "%ld %ld\n", &creatorId, &serverId);
					if (n == 2)
					{
						TCDEBUGLOGA3("CClientManager::TerminateServerThroughLockFile n=%d creatorId=%d serverId=%d\n", n, creatorId, serverId);
						creatorIds[numIds] = creatorId;
						serverIds[numIds] = serverId;
						numIds++;
						if (numIds > 9)
							done = TRUE;
					}
					else
					{
						done = TRUE;
					}
				}
				fclose(f);

				int numDeadCallers = 0;
				for (int i = 0; i < numIds; i++)
				{
					HANDLE h = ::OpenProcess(PROCESS_QUERY_INFORMATION, FALSE, creatorIds[i]);
					if (h)
					{
						// calling process is still alive
						liveCaller[i] = TRUE;
						DWORD exitCode = -5;
						BOOL exitCall = ::GetExitCodeProcess(h, &exitCode);
						DWORD id = ::GetCurrentProcessId();
						::CloseHandle(h);
						TCDEBUGLOGA3("CClientManager::TerminateServerThroughLockFile %d alive exitCall=%d currentId=%d\n", creatorIds[i], exitCall, id);
						if (exitCall == TRUE && exitCode != STILL_ACTIVE)
						{
							liveCaller[i] = FALSE;
							numDeadCallers++;
						}
						{
							TCDEBUGLOGA2("CClientManager::TerminateServerThroughLockFile exitCode=%d still_active=%d\n", exitCode, STILL_ACTIVE);
						}
					}
					else
					{
						liveCaller[i] = FALSE;
						numDeadCallers++;
						DWORD err = ::GetLastError();
						TCDEBUGLOGA3("CClientManager::TerminateServerThroughLockFile %d dead err=%d:%s\n", creatorIds[i], err, GetErrorText(err));
					}
				}
				if (numDeadCallers == numIds)
				{
					// All clients of this TCFServer are dead
					// terminate the TCFServer, and delete lock file
					pData->numRefs = 0;
					::remove(m_ServerLockFile);
					HANDLE h = ::OpenProcess(SYNCHRONIZE|PROCESS_TERMINATE, FALSE, serverIds[0]);
					if (h)
					{
						BOOL ret = ::TerminateProcess(h, -1);
						if (ret == 0)
						{
							DWORD err = ::GetLastError();
							TCDEBUGLOGA2("CClientManager::TerminateServerThroughLockFile TerminateProcess=%d:%s\n", err, GetErrorText(err));
						}
						::CloseHandle(h);
					}
				}
				else
				{
					// some java clients are still alive
					//   check to see if TCFServer is still alive
					if (IsTCFServerActive(serverIds[0]))
					{
						// TCFServer is still active
						// leave TCFServer running, recreate lock file and save live callers
						::remove(m_ServerLockFile);
						f = fopen(m_ServerLockFile, "wt");
						if (f)
						{
							for (int i = 0; i < numIds; i++)
							{
								if (liveCaller[i])
								{
									fprintf(f, "%ld %ld\n", creatorIds[i], serverIds[i]);
								}
							}
							fclose(f);
						}
						pData->numRefs -= numDeadCallers;
						if (pData->numRefs < 0) pData->numRefs = 0;
					}
					else
					{
						// TCFServer is really dead
						pData->numRefs = 0;
						::remove(m_ServerLockFile);
					}
				}
			}
			else
			{
				// error opening lock file
				// perhaps the user deleted it,
				//   if so, we assume he has also deleted the TCFServer as we now have no way of verifying if the 
				//   process is dead.
				pData->numRefs = 0;
				DWORD err = ::GetLastError();
				TCDEBUGLOGA2("CClientManager::TerminateServerThroughLockFile fopenErr=%d:%s\n", err, GetErrorText(err));
			}
		}
	}
}
#ifdef _DEBUG
static char* GetErrorText(DWORD inError)
{
	static char msg[256];
	FormatMessage(
		FORMAT_MESSAGE_FROM_SYSTEM | FORMAT_MESSAGE_IGNORE_INSERTS,
		NULL,
		inError,
		MAKELANGID(LANG_NEUTRAL, SUBLANG_DEFAULT), // Default language
		(LPTSTR) &msg,
		sizeof(msg) - 1,
		NULL);

	return msg;
}
#else
static char* GetErrorText(DWORD inError)
{
	return NULL;
}
#endif
