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
#ifndef __SHAREDDATA_H__
#define __SHAREDDATA_H__

class CSharedData
{
public:
	CSharedData();
	~CSharedData();

	BOOL Open(DWORD dwSize, CHAR *sharedName);
	BOOL Open(HANDLE hFile, DWORD dwSize, CHAR *sharedName);
	void Close();
	virtual BOOL Init();
	LPVOID GetDataPtr();
	BOOL IsCreator();

private:
	HANDLE m_hSharedData;
	LPVOID m_lpDataPtr;		// pointer to mapped data
	BOOL m_fCreator;		// am i the creator?
};

#endif // __SHAREDDATA_H__
