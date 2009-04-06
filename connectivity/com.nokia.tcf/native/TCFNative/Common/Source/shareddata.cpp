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
#include "shareddata.h"

CSharedData::CSharedData()
{
	m_fCreator = FALSE;
	m_hSharedData = NULL;
	m_lpDataPtr = NULL;

}
CSharedData::~CSharedData()
{
	Close();
}

// open map backed by OS paging file
BOOL
CSharedData::Open(DWORD dwSize, CHAR* name)
{
	BOOL fOK = FALSE;
	m_hSharedData = m_lpDataPtr = NULL;

	m_hSharedData = CreateFileMapping((HANDLE)0xFFFFFFFF, NULL, PAGE_READWRITE, 0, dwSize, name);

	m_fCreator = (GetLastError() != ERROR_ALREADY_EXISTS);
	if (!m_fCreator)
	{
		// not creator, re-open using Open
		CloseHandle(m_hSharedData);
		m_hSharedData = OpenFileMapping(FILE_MAP_ALL_ACCESS, FALSE, name);

	}
	if (m_hSharedData)
	{
		m_lpDataPtr = MapViewOfFile(m_hSharedData, FILE_MAP_ALL_ACCESS, 0, 0, 0);
		if (m_lpDataPtr == NULL)
		{
			CloseHandle(this->m_hSharedData);
			m_hSharedData = NULL;
		}
		else
			fOK = TRUE;
	}
	return fOK;
}

// open map backed by a real file handle
BOOL
CSharedData::Open(HANDLE hFile, DWORD dwSize, CHAR* name)
{
	BOOL fOK = FALSE;

	m_hSharedData = m_lpDataPtr = NULL;

	// if hFile is invalid do not create mapping - go directly to open mapping by name
	if (hFile == INVALID_HANDLE_VALUE)
	{
		m_hSharedData = OpenFileMapping(FILE_MAP_ALL_ACCESS, FALSE, name);
		m_fCreator = FALSE;
	}
	else
	{
		m_hSharedData = CreateFileMapping(hFile, NULL, PAGE_READWRITE, 0, dwSize, name);
		if (m_hSharedData == NULL) // failed
		{
		}
		else
		{
			// succeeded
			m_fCreator = (GetLastError() != ERROR_ALREADY_EXISTS);
			if (!m_fCreator)
			{
				// not creator, re-open using Open
				CloseHandle(m_hSharedData);
				m_hSharedData = OpenFileMapping(FILE_MAP_ALL_ACCESS, FALSE, name);
			}
		}

	}
	if (m_hSharedData)
	{
		m_lpDataPtr = MapViewOfFile(m_hSharedData, FILE_MAP_ALL_ACCESS, 0, 0, 0);
		if (m_lpDataPtr == NULL)
		{
			CloseHandle(m_hSharedData);
			m_hSharedData = NULL;
		}
		else
			fOK = TRUE;
	}
	return fOK;
}
CSharedData::Close()
{
	BOOL fIgnore = FALSE;

	if (m_lpDataPtr != NULL)
	{
		fIgnore = UnmapViewOfFile(m_lpDataPtr);
		m_lpDataPtr = NULL;
	}

	if (m_hSharedData != NULL)
	{
		fIgnore = CloseHandle(m_hSharedData);
		m_hSharedData = NULL;
	}
}
BOOL CSharedData::Init()
{
	// intended to be overridden
	return TRUE;
}

LPVOID CSharedData::GetDataPtr()
{
	return m_lpDataPtr;
}

BOOL CSharedData::IsCreator()
{
	return m_fCreator;
}

