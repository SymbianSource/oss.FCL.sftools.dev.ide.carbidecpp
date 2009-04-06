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
#include "mutex.h"

Mutex::Mutex()
{
	m_hMutex = NULL;
	m_waitTimeout = 0L;
	m_mutexOpen = FALSE;
}
Mutex::~Mutex()
{
	Close();
}

BOOL Mutex::Open(CHAR *mutexName, DWORD waitTimeout)
{
#ifdef WIN32
	m_hMutex = ::CreateMutex(NULL, FALSE, mutexName);
#else
#error non Win32
#endif
	if (m_hMutex == NULL) 
		m_mutexOpen = FALSE;
	else
	{
		m_mutexOpen = TRUE;
		m_waitTimeout = waitTimeout;
	}

	return m_mutexOpen;
}
void Mutex::Close()
{
	if (m_mutexOpen)
	{
#ifdef WIN32
		::ReleaseMutex(m_hMutex);
		::CloseHandle(m_hMutex);
#else
#error non WIN32
#endif
		m_hMutex = NULL;
		m_mutexOpen = FALSE;
	}
}
BOOL Mutex::Wait()
{
#ifdef WIN32
	DWORD dwWaitResult = ::WaitForSingleObject(m_hMutex, m_waitTimeout); 
	if (dwWaitResult == WAIT_OBJECT_0)
	{
		return TRUE;
	}
	return FALSE;
#else
#error non WIN32
#endif
}
BOOL Mutex::Release()
{
	BOOL ret = FALSE;
#ifdef WIN32
	ret = ::ReleaseMutex(m_hMutex);
#else
#error non WIN32
#endif

	return ret;
}
