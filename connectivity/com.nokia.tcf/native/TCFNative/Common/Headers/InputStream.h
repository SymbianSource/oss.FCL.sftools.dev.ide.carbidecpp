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
#ifndef __INPUTSTREAM_H__
#define __INPUTSTREAM_H__

#ifdef TCF_CLIENT
#include <jni.h>
#endif

#include "shareddata.h"
#include "mutex.h"
#include "TCDebugLog.h"
#include "ServerClient.h"

#define USE_CIRCULAR_BUFFER
//
//  Input Stream - one per client
//

//----------------------------------
// Input Stream Mutex
//----------------------------------
// Mutex name is basename + clientID
#define INPUTSTREAMDATA_MUTEX_BASENAME		"TCFInputStreamDataMutex"
#define INPUTSTREAMDATA_MUTEX_TIMEOUT		(60000L)

//----------------------------------
// Input Stream Buffer
//----------------------------------
typedef BYTE *pInputStreamData;
// Mapping name is basename + clientID
#define INPUTSTREAMDATA_MAP_BASENAME	"TCFInputStreamData"
class CInputStreamData : public CSharedData
{
public:
	BOOL Init();
};

#ifdef USE_CIRCULAR_BUFFER
//----------------------------------
// Input Stream Buffer & File Information
//----------------------------------
typedef struct tagInputStreamInfo
{
	DWORD numberMessages;			// total messages (buffer and file)
	DWORD numberBytes;				// total bytes (buffer and file)
	DWORD numberMessagesInFile;		// number of messages in file
	DWORD numberBytesInFile;		// number of bytes in file
	DWORD bufferRead;				// where to read from buffer
	DWORD bufferPeek;				// where to peek some data from buffer
	DWORD bufferWrite;				// where to write to buffer
	DWORD bufferSize;				// current size of buffer
	DWORD bufferCapacity;			// total capacity of buffer
} *pInputStreamInfo, InputStreamInfo;
#else
//----------------------------------
// Input Stream Buffer & File Information
//----------------------------------
typedef struct tagInputStreamInfo
{
	DWORD numberMessages;			// total messages (buffer and file)
	DWORD numberBytes;				// total bytes (buffer and file)
	DWORD numberMessagesInFile;		// number of messages in file
	DWORD numberBytesInFile;		// number of bytes in file
} *pInputStreamInfo, InputStreamInfo;
#endif
// Mapping name is basename + clientID
#define INPUTSTREAMINFO_MAP_BASENAME	"TCFInputStreamInfo"
#define INPUTSTREAMINFO_MAP_SIZE		(sizeof(InputStreamInfo))

class CInputStreamInfo : public CSharedData
{
public:
	BOOL Init();
};

//----------------------------------
// Input Stream Overflow Files
//----------------------------------
#define INPUTSTREAMOVERFLOW_FILE_SIZE		(20*1024*1024L)

typedef BYTE* pInputStreamFile;

// Mapping name is basename + clientID
#define INPUTSTREAMOVERFLOW_MAP_BASENAME	"TCFInputStreamOverflowFile"
class CInputStreamFile : public CSharedData
{
public:
	void SetClientId(long id);
	BOOL Open(DWORD dwSize, CHAR *filePath);
	BOOL Init();
	void Close();

	long m_ClientID;
	HANDLE m_hFile; // handle from CreateFile
};

//----------------------------------
// Input Stream class
//----------------------------------
class CInputStream
{
public:
	CInputStream();
	CInputStream(CHAR* pOverflowPath, DWORD inBufferSize, BOOL inOverFlowToFile, long inClientID);
	~CInputStream();

	BOOL CreateStream();
	long AddMessage(DWORD inLength, BYTE* inMessage);
	LONG GetNumberMessages();

	DWORD GetNextMessage(DWORD inLength, BYTE* outMessage);
	DWORD GetNextMessageSize();
	void GetMessageSizes(long inNumberMessages, DWORD* outMessageSizes);
	void GetTotalMessageSize(long inNumberMessages, DWORD& outTotalSize);
	BOOL IsThisClient(long inClientID) { return (inClientID == m_ClientID); }
	long GetClientId() { return m_ClientID; }
#ifdef TCF_CLIENT
	DWORD GetMessages(JNIEnv* env, long inNumberMessages, long inNumberMaxBytes, long& outNumberBytesRead, long& outNumberMessagesRead, jbyteArray outMessageData);
#endif
	void UnLockStream() { ReleaseAccess(); }

#ifdef USE_CIRCULAR_BUFFER
	void DoReadBuffer(pInputStreamInfo pInfo, BYTE* pBuffer, BYTE* outData, DWORD inLength);
	void DoWriteBuffer(pInputStreamInfo pInfo, BYTE* pBuffer, BYTE* inData, DWORD inLength);
	void IncrementReadPosition(pInputStreamInfo pInfo, DWORD inLength);
	void IncrementWritePosition(pInputStreamInfo pInfo, DWORD inLength);
	void DoPeekBuffer(pInputStreamInfo pInfo, BYTE* pBuffer, BYTE* outData, DWORD inLength);
	void IncrementPeekPosition(pInputStreamInfo pInfo, DWORD inLength);
#endif

private:
	pInputStreamData GetDataPtr() { return (pInputStreamData)m_Data.GetDataPtr(); }
	pInputStreamInfo GetInfoPtr() { return (pInputStreamInfo)m_Info.GetDataPtr(); }
	pInputStreamFile GetFilePtr() { if (m_File == NULL) return NULL; else return (pInputStreamFile)m_File->GetDataPtr(); }

	BOOL WaitForAccess() { if (m_StreamLocked) return TRUE; else { m_StreamLocked = TRUE; return m_Mutex.Wait();} };
	BOOL ReleaseAccess() { if (m_StreamLocked) { m_StreamLocked = FALSE; return m_Mutex.Release();} else return TRUE; };

	long m_ClientID;
	Mutex m_Mutex;
	CInputStreamData m_Data;
	CInputStreamInfo m_Info;
	DWORD m_BufferSize;
	DWORD m_FileSize;
	CHAR m_OverFlowBaseName[MAX_FILEPATH];
	BOOL m_OverFlowToFile;
	CInputStreamFile* m_File;
	BOOL m_StreamLocked;
};

#endif __INPUTSTREAM_H__
