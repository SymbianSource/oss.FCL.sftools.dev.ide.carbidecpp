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
#ifndef __MUTEX_H__
#define __MUTEX_H__

class Mutex
{
public:
	Mutex();
	~Mutex();

	BOOL Open(CHAR* mutexName, DWORD waitTimeout);
	void Close();
	BOOL Wait();
	BOOL Release();

private:
#ifdef WIN32
	HANDLE m_hMutex;
#else
#error non WIN32
#endif

	DWORD m_waitTimeout;
	BOOL m_mutexOpen;
};
#endif __MUTEX_H__
