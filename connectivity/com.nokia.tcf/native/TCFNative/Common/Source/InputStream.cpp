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
#include "InputStream.h"
#include <stdio.h>
#include <sys/stat.h>

#ifdef TCF_CLIENT
#include "..\..\TCFClient\ClientManager.h"
extern CClientManager* gManager;
#endif
#ifdef TCF_SERVER
#include "..\..\TCFServer\ServerManager.h"
extern CServerManager* gManager;
//#define LOG_PERFORMANCE
#endif

#ifdef _DEBUG
extern BOOL gDoLogging;
#endif

// for performance - independent of debug logging
//#define LOG_PERFORMANCE
#ifdef LOG_PERFORMANCE
# ifdef TCF_CLIENT
static char* perfFileName="c:\\tcf\\clientperf.txt";
# else
static char* perfFileName="c:\\tcf\\serverperf.txt";
# endif
static FILE *fpLog = NULL;
static int numLogged=0;
static void logPerf(char* msg);
static void openPerf();
static void closePerf();
#define OPENPERF() openPerf()
#define LOGPERF(s) logPerf(s)
#define CLOSEPERF() closePerf()
#else
#define OPENPERF()
#define LOGPERF(s)
#define CLOSEPERF()
#endif

//#define LOG_INPUTSTREAM
#if defined(LOG_INPUTSTREAM) && defined(_DEBUG)
extern char TCDebugMsg[];
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

CInputStream::CInputStream()
{
}

CInputStream::CInputStream(CHAR* pOverflowPath, DWORD inBufferSize, BOOL inOverFlowToFile, long inClientID)
{
	TCDEBUGOPEN();
	TCDEBUGLOGA2("CInputStream::CInputStream clientId = %d this = %x\n", inClientID, this);

	m_ClientID = inClientID;
	m_BufferSize = inBufferSize;
	m_FileSize = INPUTSTREAMOVERFLOW_FILE_SIZE;
//	m_FileSize = 2*1024L;				for testing only

	if (pOverflowPath == NULL)
	{
		m_OverFlowBaseName[0] = NULL;
		m_OverFlowToFile = FALSE;
	}
	else
	{
		strncpy(m_OverFlowBaseName, pOverflowPath, MAX_FILEPATH);
		m_OverFlowToFile = inOverFlowToFile;
	}

	m_File = NULL;

	m_StreamLocked = FALSE;

	TCDEBUGLOGS("CInputStream::CInputStream done\n");
	TCDEBUGCLOSE();
}

CInputStream::~CInputStream()
{
	TCDEBUGOPEN();
	TCDEBUGLOGA2("CInputStream::~CInputStream clientId = %d this = %x\n", m_ClientID, this);

	m_Mutex.Close();
	m_Data.Close();
	m_Info.Close();

	if (m_File != NULL)
	{
		m_File->Close();
		delete m_File;
	}

	TCDEBUGLOGS("CInputStream::~CInputStream done\n");
	TCDEBUGCLOSE();

	CLOSEPERF();
}

BOOL CInputStream::CreateStream()
{
	BOOL ok = TRUE;
#if (0)
#ifdef _DEBUGLOG
	#ifdef TCF_CLIENT
		m_DebugLog = new TCDebugLog("TCF_ClientStreamLog", m_ClientID);
	#else
		m_DebugLog = new TCDebugLog("TCF_ServerStreamLog", m_ClientID);
	#endif
#else
	m_DebugLog = NULL;
#endif
#endif
	TCDEBUGOPEN();
	TCDEBUGLOGA2("CInputStream::CreateStream clientId = %d this = %x\n", m_ClientID, this);

	char toString[30];
	sprintf(toString, "%s%04.4d", INPUTSTREAMDATA_MAP_BASENAME, m_ClientID);

	TCDEBUGLOGA1("CInputStream::CreateStream dataname = %s\n", toString);

	m_Data.Open(m_BufferSize, toString);
	m_Data.Init();

	sprintf(toString, "%s%04.4d", INPUTSTREAMDATA_MUTEX_BASENAME, m_ClientID);
	TCDEBUGLOGA1("CInputStream::CreateStream mutexname = %s\n", toString);

	m_Mutex.Open(toString, INPUTSTREAMDATA_MUTEX_TIMEOUT);

	sprintf(toString, "%s%04.4d", INPUTSTREAMINFO_MAP_BASENAME, m_ClientID);
	TCDEBUGLOGA1("CInputStream::CreateStream infoname = %s\n", toString);

	m_Info.Open(INPUTSTREAMINFO_MAP_SIZE, toString);
	m_Info.Init();
#ifdef USE_CIRCULAR_BUFFER
	GetInfoPtr()->bufferCapacity = m_BufferSize;
#endif

	// overflow file?
	if (m_OverFlowToFile)
	{
		m_File = new CInputStreamFile();
		m_File->SetClientId(m_ClientID);
		if (!m_File->Open(m_FileSize, m_OverFlowBaseName))
		{
			delete m_File;
			m_File = NULL;
			ok = FALSE;
		}
	}
	TCDEBUGLOGS("CInputStream::CreateStream done\n");
	TCDEBUGCLOSE();

	OPENPERF();

	return ok;
}
#ifdef USE_CIRCULAR_BUFFER
void CInputStream::DoReadBuffer(pInputStreamInfo pInfo, BYTE* pBuffer, BYTE* outData, DWORD inLength)
{
	DWORD lenToEnd = pInfo->bufferCapacity - pInfo->bufferRead;
	if (lenToEnd > inLength)
		lenToEnd = inLength;

	if (lenToEnd > 0)
		memcpy(outData, &pBuffer[pInfo->bufferRead], lenToEnd);

	DWORD lenRemaining = inLength - lenToEnd;
	if (lenRemaining > 0)
		memcpy(&outData[lenToEnd], &pBuffer[0], lenRemaining);

	pInfo->bufferSize -= inLength;

	IncrementReadPosition(pInfo, inLength);
}
void CInputStream::DoWriteBuffer(pInputStreamInfo pInfo, BYTE* pBuffer, BYTE* inData, DWORD inLength)
{
	DWORD lenToEnd = pInfo->bufferCapacity - pInfo->bufferWrite;
	if (lenToEnd > inLength)
		lenToEnd = inLength;
	memcpy(&pBuffer[pInfo->bufferWrite], inData, lenToEnd);
	DWORD lenRemaining = inLength - lenToEnd;
	if (lenRemaining > 0)
		memcpy(&pBuffer[0], (const void*)&inData[lenToEnd], lenRemaining);

	pInfo->bufferSize += inLength;

	IncrementWritePosition(pInfo, inLength);
}
void CInputStream::IncrementReadPosition(pInputStreamInfo pInfo, DWORD inLength)
{
	DWORD lenToEnd = pInfo->bufferCapacity - pInfo->bufferRead;
	if (inLength <= lenToEnd)
		pInfo->bufferRead += inLength;
	else
		pInfo->bufferRead = inLength - lenToEnd;
}
void CInputStream::IncrementWritePosition(pInputStreamInfo pInfo, DWORD inLength)
{
	DWORD lenToEnd = pInfo->bufferCapacity - pInfo->bufferWrite;
	if (inLength <= lenToEnd)
		pInfo->bufferWrite += inLength;
	else
		pInfo->bufferWrite = inLength - lenToEnd;
}
void CInputStream::DoPeekBuffer(pInputStreamInfo pInfo, BYTE* pBuffer, BYTE* outData, DWORD inLength)
{
	DWORD lenToEnd = pInfo->bufferCapacity - pInfo->bufferPeek;
	if (lenToEnd > inLength)
		lenToEnd = inLength;

	if (lenToEnd > 0)
		memcpy(outData, &pBuffer[pInfo->bufferPeek], lenToEnd);

	DWORD lenRemaining = inLength - lenToEnd;
	if (lenRemaining > 0)
		memcpy(&outData[lenToEnd], &pBuffer[0], lenRemaining);
}
void CInputStream::IncrementPeekPosition(pInputStreamInfo pInfo, DWORD inLength)
{
	DWORD lenToEnd = pInfo->bufferCapacity - pInfo->bufferPeek;
	if (inLength <= lenToEnd)
		pInfo->bufferPeek += inLength;
	else
		pInfo->bufferPeek = inLength - lenToEnd;
}
long CInputStream::AddMessage(DWORD inLength, BYTE* inMessage)
{
	TCDEBUGOPEN();
	TCDEBUGLOGA3("CInputStream::AddMessage clientId = %d inLength = %d this = %x\n", m_ClientID, inLength, this);

	long err = TCAPI_ERR_NONE;

	BOOL done = FALSE;
	if (inLength == 0 || inMessage == NULL)
		return TCAPI_ERR_NONE;

	BOOL gotIt = WaitForAccess(); // will lock on first access only

	pInputStreamInfo pInfo = GetInfoPtr();
	pInputStreamData pData = GetDataPtr();
	pInputStreamFile pFile = GetFilePtr();

	long nfile = pInfo->numberBytesInFile;						// number bytes in file
	long nbuffer = pInfo->numberBytes - pInfo->numberBytesInFile;	// number bytes in buffer

	TCDEBUGLOGA2("CInputStream::AddMessage numberMessages      =%d numberBytes      =%d\n",pInfo->numberMessages, pInfo->numberBytes);

	DWORD nWriteSize = pInfo->bufferCapacity - pInfo->bufferSize;
	if (nWriteSize > (inLength+sizeof(DWORD)))
	{
		DoWriteBuffer(pInfo, pData, (BYTE*)&inLength, sizeof(DWORD));
		DoWriteBuffer(pInfo, pData, inMessage, inLength);
		pInfo->numberBytes += inLength + sizeof(DWORD);
		pInfo->numberMessages++;
		done = TRUE;
	}
	else
	{
		// not enough room
		// we just lost a message
		TCDEBUGLOGS("CInputStream::AddMessage buffer overflowed and no file - msg lost\n");
		LOGPERF("AddMessage buffer overflowed\n");
		err = TCAPI_ERR_INPUTSTREAM_BUFFER_OVERFLOW_MISSED_MSGS;
	}

	TCDEBUGLOGA2("CInputStream::AddMessage numberMessages      =%d numberBytes      =%d\n",pInfo->numberMessages, pInfo->numberBytes);

#ifdef LOG_PERFORMANCE
	char msg[200];
	sprintf(msg, "AddMessage numMsgs = %d numByts = %d\n", pInfo->numberMessages, pInfo->numberBytes);
	LOGPERF(msg);
#endif
//	ReleaseAccess(); // server will unlock all streams once the buffer is processed if any added

	TCDEBUGLOGA1("CInputStream::AddMessage err=%d\n", err);
	TCDEBUGCLOSE();

	return err;
}
// now only used still by the C++ api
DWORD CInputStream::GetNextMessage(DWORD inLength, BYTE* outMessage)
{
	TCDEBUGOPEN();
	TCDEBUGLOGA3("CInputStream::GetNextMessage clientId = %d inLength = %d this = %x\n", m_ClientID, inLength, this);

	DWORD outMsgLen = 0;

	BOOL gotIt = WaitForAccess();
	if (gotIt)
	{
		DWORD dwMsgSize = 0;

		pInputStreamInfo pInfo = GetInfoPtr();
		pInputStreamData pData = GetDataPtr();
		pInputStreamFile pFile = GetFilePtr();

		TCDEBUGLOGA2("CInputStream::GetNextMessage numberMessages      =%d numberBytes      =%d\n",pInfo->numberMessages, pInfo->numberBytes);

		if (pInfo->numberBytes > 0)
		{
			DoReadBuffer(pInfo, pData, (BYTE*)&dwMsgSize, sizeof(DWORD));
			if (inLength > dwMsgSize)
				inLength = dwMsgSize;

			DoReadBuffer(pInfo, pData, outMessage, inLength);
			outMsgLen = inLength;
			pInfo->numberMessages--;
			pInfo->numberBytes = pInfo->numberBytes - dwMsgSize - sizeof(DWORD);
		}

		TCDEBUGLOGA2("CInputStream::GetNextMessage numberMessages      =%d numberBytes      =%d\n",pInfo->numberMessages, pInfo->numberBytes);

		ReleaseAccess();
	}

#ifdef LOG_PERFORMANCE
//	char msg[200];
//	sprintf(msg, "GetNextMessage.outMsgLen = %d\n", outMsgLen);
//	LOGPERF(msg);
	LOGPERF("GetNextMessage\n");
#endif

	TCDEBUGLOGS("CInputStream::GetNextMessage done\n");
	TCDEBUGCLOSE();

	return outMsgLen;
}

DWORD CInputStream::GetNextMessageSize()
{
	TCDEBUGOPEN();
	TCDEBUGLOGA2("CInputStream::GetNextMessageSize clientId = %d this = %x\n", m_ClientID, this);

	DWORD length = 0;

	BOOL gotIt = WaitForAccess();
	if (gotIt)
	{

		pInputStreamInfo pInfo = GetInfoPtr();
		pInputStreamData pData = GetDataPtr();

		if (pInfo->numberMessages > 0)
		{
			pInfo->bufferPeek = pInfo->bufferRead;
			DoPeekBuffer(pInfo, pData, (BYTE*)&length, sizeof(DWORD));
		}

		ReleaseAccess();
	}

	TCDEBUGLOGA1("CInputStream::GetNextMessageSize length = %d\n", length);
	TCDEBUGCLOSE();

#ifdef LOG_PERFORMANCE
//	char msg[200];
//	sprintf(msg, "GetNextMessageSize.length = %d\n", length);
//	LOGPERF(msg);
	LOGPERF("GetNextMessageSize\n");
#endif
	return length;
}
void CInputStream::GetMessageSizes(long inNumberMessages, DWORD* outMessageSizes)
{
	TCDEBUGOPEN();
	TCDEBUGLOGA3("CInputStream::GetMessageSizes clientId = %d this = %x inNum = %d\n", m_ClientID, this, inNumberMessages);

	BOOL gotIt = WaitForAccess();
	if (gotIt)
	{

		pInputStreamData pData = GetDataPtr();
		pInputStreamFile pFile = GetFilePtr();
		pInputStreamInfo pInfo = GetInfoPtr();

		DWORD numberToGet = inNumberMessages;
		if (numberToGet > pInfo->numberMessages) numberToGet = pInfo->numberMessages;

		if (numberToGet > 0)
		{
			pInfo->bufferPeek = pInfo->bufferRead;
			for (DWORD i = 0; i < numberToGet; i++)
			{
				DWORD len = 0;
				DoPeekBuffer(pInfo, pData, (BYTE*)&len, sizeof(DWORD));
				outMessageSizes[i] = len;
				IncrementPeekPosition(pInfo, len+sizeof(DWORD));
			}
		}

		ReleaseAccess();
	}

	TCDEBUGLOGS("CInputStream::GetMessageSizes done\n");
	TCDEBUGCLOSE();
	LOGPERF("GetMessageSizes\n");
}

#ifdef TCF_CLIENT
DWORD CInputStream::GetMessages(JNIEnv* env, long inNumberMessages, long inNumberMaxBytes, long& outNumberBytesRead, long& outNumberMessagesRead, jbyteArray outMessageData)
{
	TCDEBUGOPEN();
	TCDEBUGLOGA2("CInputStream::GetMessages inNumberMaxBytes = %d inNumberMessages = %d\n", inNumberMaxBytes, inNumberMessages);

	DWORD dwMsgSize = 0;
	outNumberBytesRead = outNumberMessagesRead = 0;

	BOOL gotIt = WaitForAccess();
	if (gotIt)
	{

		pInputStreamInfo pInfo = GetInfoPtr();
		pInputStreamData pData = GetDataPtr();

		if (pInfo->numberMessages > 0)
		{
			if (inNumberMessages == 0 || (inNumberMessages > pInfo->numberMessages))
				inNumberMessages = pInfo->numberMessages;
			for (long i = 0; i < inNumberMessages; i++)
			{
				pInfo->bufferPeek = pInfo->bufferRead;
				DoPeekBuffer(pInfo, pData, (BYTE*)&dwMsgSize, sizeof(DWORD));

				if ((outNumberBytesRead + dwMsgSize) > inNumberMaxBytes)
					break;

				IncrementReadPosition(pInfo, sizeof(DWORD));
				pInfo->numberBytes -= sizeof(DWORD);

				DWORD lenToEnd = pInfo->bufferCapacity - pInfo->bufferRead;
				if (lenToEnd > dwMsgSize)
					lenToEnd = dwMsgSize;

				if (lenToEnd > 0)
					env->SetByteArrayRegion(outMessageData, outNumberBytesRead, lenToEnd, (jbyte*)&pData[pInfo->bufferRead]);

				DWORD lenRemaining = dwMsgSize - lenToEnd;
				if (lenRemaining > 0)
					env->SetByteArrayRegion(outMessageData, outNumberBytesRead+lenToEnd, lenRemaining, (jbyte*)&pData[0]);

				pInfo->bufferSize -= dwMsgSize;

				IncrementReadPosition(pInfo, dwMsgSize);

				outNumberBytesRead += dwMsgSize;
				outNumberMessagesRead++;
				pInfo->numberBytes -= dwMsgSize;
				pInfo->numberMessages--;	

				if ((i % 500) == 0)
					Sleep(1);
			}
		}
		if (pInfo->numberBytes == 0)
		{
			pInfo->bufferRead = pInfo->bufferWrite = 0;
		}
		ReleaseAccess();
	}

#ifdef LOG_PERFORMANCE
	char msg[200];
	sprintf(msg, "GetMessages numMsgs = %d numByts = %d\n", outNumberMessagesRead, outNumberBytesRead);
	LOGPERF(msg);
#endif

	TCDEBUGLOGA2("CInputStream::GetMessages outNumberBytesRead = %d outNumberMessagesRead = %d\n", outNumberBytesRead, outNumberMessagesRead);
	TCDEBUGCLOSE();
	return outNumberMessagesRead;
}
#endif // TCF_CLIENT

void CInputStream::GetTotalMessageSize(long inNumberMessages, DWORD& outTotalSize)
{
	TCDEBUGOPEN();
	TCDEBUGLOGA3("CInputStream::GetTotalMessageSize clientId = %d this = %x inNum = %d\n", m_ClientID, this, inNumberMessages);

	outTotalSize = 0;
	BOOL gotIt = WaitForAccess();
	if (gotIt)
	{

		DWORD len = 0;

		pInputStreamData pData = GetDataPtr();
		pInputStreamFile pFile = GetFilePtr();
		pInputStreamInfo pInfo = GetInfoPtr();

		DWORD numberToGet = inNumberMessages;
		if (numberToGet > pInfo->numberMessages) numberToGet = pInfo->numberMessages;

		if (numberToGet > 0)
		{
			TCDEBUGLOGA1("CInputStream::GetTotalMessageSize numberToGet = %d\n", numberToGet);
			pInfo->bufferPeek = pInfo->bufferRead;
			for (DWORD i = 0; i < numberToGet; i++)
			{
				DoPeekBuffer(pInfo, pData, (BYTE*)&len, sizeof(DWORD));
				outTotalSize += len;
				IncrementPeekPosition(pInfo, len+sizeof(DWORD));
			}
		}


		ReleaseAccess();
	}

#ifdef LOG_PERFORMANCE
//	char msg[200];
//	sprintf(msg, "GetTotalMessageSize.outTotalSize = %d\n", outTotalSize);
	LOGPERF("GetTotalMessageSize\n");
#endif
	TCDEBUGLOGA1("CInputStream::GetTotalMessageSize done = %d\n", outTotalSize);
	TCDEBUGCLOSE();

}

#else // !USE_CIRCULAR_BUFFER

long CInputStream::AddMessage(DWORD inLength, BYTE* inMessage)
{
	TCDEBUGOPEN();
	TCDEBUGLOGA3("CInputStream::AddMessage clientId = %d inLength = %d this = %x\n", m_ClientID, inLength, this);

	long err = TCAPI_ERR_NONE;

	BOOL done = FALSE;
	if (inLength == 0 || inMessage == NULL)
		return TCAPI_ERR_NONE;

	WaitForAccess();

	pInputStreamInfo pInfo = GetInfoPtr();
	pInputStreamData pData = GetDataPtr();
	pInputStreamFile pFile = GetFilePtr();

	long nfile = pInfo->numberBytesInFile;						// number bytes in file
	long nbuffer = pInfo->numberBytes - pInfo->numberBytesInFile;	// number bytes in buffer

	TCDEBUGLOGA2("CInputStream::AddMessage numberMessages      =%d numberBytes      =%d\n",pInfo->numberMessages, pInfo->numberBytes);
	TCDEBUGLOGA2("CInputStream::AddMessage numberMessagesInBuff=%d numberBytesInBuff=%d\n",pInfo->numberMessages-pInfo->numberMessagesInFile, pInfo->numberBytes-pInfo->numberBytesInFile);
	TCDEBUGLOGA2("CInputStream::AddMessage numberMessagesInFile=%d numberBytesInFile=%d\n",pInfo->numberMessagesInFile, pInfo->numberBytesInFile);

	if (nfile > 0)
	{
		// we've already overflowed
		//  and we are using an overflow file
		//  so attempt to put message there
		if ((nfile + inLength + sizeof(DWORD)) <= m_FileSize)
		{
			// room to put msg into file
			BYTE* ptr = pFile;
			ptr += nfile;
			*(DWORD*)ptr = inLength;
			ptr += sizeof(DWORD);
			memcpy(ptr, inMessage, inLength);
			pInfo->numberMessages++;
			pInfo->numberMessagesInFile++;
			pInfo->numberBytes += inLength + sizeof(DWORD);
			pInfo->numberBytesInFile += inLength + sizeof(DWORD);
			done = TRUE;
		}
		else
		{
			// we just lost a message
			TCDEBUGLOGS("CInputStream::AddMessage file overflowed - msg lost\n");
			err = TCAPI_ERR_INPUTSTREAM_FILE_OVERFLOW_MISSED_MSGS;
		}
	}
	else // number of bytes in file == 0 ==> either not overflowed or not using file
	{
		// attempt to put message into buffer
		if ((nbuffer + inLength + sizeof(DWORD)) <= m_BufferSize)
		{
			// room to put msg into buffer
			BYTE* ptr = pData;
			ptr += nbuffer;
			*(DWORD*)ptr = inLength;
			ptr += sizeof(DWORD);
			memcpy(ptr, inMessage, inLength);
			pInfo->numberMessages++;
			pInfo->numberBytes += inLength + sizeof(DWORD);
			done = TRUE;
		}
		else
		{
			// overflow to file?
			if (m_File != NULL)
			{
				// room to put msg into file
				BYTE* ptr = pFile;
				ptr += nfile;
				*(DWORD*)ptr = inLength;
				ptr += sizeof(DWORD);
				memcpy(ptr, inMessage, inLength);
				pInfo->numberMessages++;
				pInfo->numberMessagesInFile++;
				pInfo->numberBytes += inLength + sizeof(DWORD);
				pInfo->numberBytesInFile += inLength + sizeof(DWORD);
				done = TRUE;
				TCDEBUGLOGS("CInputStream::AddMessage buffer overflowed to file\n");
				LOGPERF("AddMessage buffer overflowed\n");
				err = TCAPI_INFO_INPUTSTREAM_BUFFER_OVERFLOW_TO_FILE;
			}
			else
			{
				// we just lost a message
				TCDEBUGLOGS("CInputStream::AddMessage buffer overflowed and no file - msg lost\n");
				err = TCAPI_ERR_INPUTSTREAM_BUFFER_OVERFLOW_MISSED_MSGS;
			}
		}
	}
	TCDEBUGLOGA2("CInputStream::AddMessage numberMessages      =%d numberBytes      =%d\n",pInfo->numberMessages, pInfo->numberBytes);
	TCDEBUGLOGA2("CInputStream::AddMessage numberMessagesInBuff=%d numberBytesInBuff=%d\n",pInfo->numberMessages-pInfo->numberMessagesInFile, pInfo->numberBytes-pInfo->numberBytesInFile);
	TCDEBUGLOGA2("CInputStream::AddMessage numberMessagesInFile=%d numberBytesInFile=%d\n",pInfo->numberMessagesInFile, pInfo->numberBytesInFile);

	LOGPERF("AddMessage\n");

	ReleaseAccess();

	TCDEBUGLOGA1("CInputStream::AddMessage err=%d\n", err);
	TCDEBUGCLOSE();

	return err;
}
DWORD CInputStream::GetNextMessage(DWORD inLength, BYTE* outMessage)
{
	TCDEBUGOPEN();
	TCDEBUGLOGA3("CInputStream::GetNextMessage clientId = %d inLength = %d this = %x\n", m_ClientID, inLength, this);

	BYTE* ptr, *ptrStart;
	DWORD outMsgLen = 0;

	WaitForAccess();

	DWORD dwMsgSize = 0;

	pInputStreamInfo pInfo = GetInfoPtr();
	pInputStreamData pData = GetDataPtr();
	pInputStreamFile pFile = GetFilePtr();

	ptr = ptrStart = pData;
	TCDEBUGLOGA2("CInputStream::GetNextMessage numberMessages      =%d numberBytes      =%d\n",pInfo->numberMessages, pInfo->numberBytes);
	TCDEBUGLOGA2("CInputStream::GetNextMessage numberMessagesInBuff=%d numberBytesInBuff=%d\n",pInfo->numberMessages-pInfo->numberMessagesInFile, pInfo->numberBytes-pInfo->numberBytesInFile);
	TCDEBUGLOGA2("CInputStream::GetNextMessage numberMessagesInFile=%d numberBytesInFile=%d\n",pInfo->numberMessagesInFile, pInfo->numberBytesInFile);

	if (pInfo->numberBytes > 0)
	{
		// get 1 msg from buffer
		dwMsgSize = *(DWORD*)pData;
		TCDEBUGLOGA1("CInputStream::GetNextMessage 1st msg in buffer = %d\n", dwMsgSize);
		ptr += sizeof(DWORD);
		if (inLength > dwMsgSize)
			inLength = dwMsgSize;

		memcpy(outMessage, ptr, inLength);
		outMsgLen = inLength;
		ptr += dwMsgSize;

		// move up rest of buffer msgs to beginning of buffer
		long moveLen = m_BufferSize - dwMsgSize - sizeof(DWORD);
//		memcpy(ptrStart, ptr, moveLen);
		TCDEBUGLOGA1("CInputStream::GetNextMessage move bytes up in buffer = %d\n", moveLen);
		
		// adjust totals
		pInfo->numberMessages--;
		pInfo->numberBytes = pInfo->numberBytes - dwMsgSize - sizeof(DWORD);
		
		// move next file message up to buffer
		if (m_File != NULL)
		{
			// using file
			if (pInfo->numberBytesInFile > 0)
			{
				// messages exist in file
				dwMsgSize = *(DWORD*)pFile;
				TCDEBUGLOGA1("CInputStream::GetNextMessage 1st msg in file   = %d\n", dwMsgSize);
				long nbuffer = pInfo->numberBytes - pInfo->numberBytesInFile; // number bytes in buffer
				if ((nbuffer + dwMsgSize + sizeof(DWORD)) <= m_BufferSize)
				{
					// room in buffer - move to buffer
					ptrStart = &pData[nbuffer];
					moveLen = dwMsgSize + sizeof(DWORD);
					memcpy(ptrStart, pFile, moveLen);
					TCDEBUGLOGA1("CInputStream::GetNextMessage move from file to buffer = %d\n", moveLen);
					LOGPERF("GetNextMessage move from file to buffer\n");

					// adjust file totals
					pInfo->numberMessagesInFile--;
					pInfo->numberBytesInFile = pInfo->numberBytesInFile - moveLen;

					// move msgs in file up
					if (pInfo->numberMessagesInFile > 0)
					{
						ptr = &pFile[moveLen];	// new end
						moveLen = pInfo->numberBytesInFile;
						memcpy(pFile, ptr, moveLen);
						TCDEBUGLOGA1("CInputStream::GetNextMessage move bytes up in file = %d\n", moveLen);
					}
				}
				else
				{
					// no room in buffer for next file msg - leave it there
					TCDEBUGLOGS("CInputStream::GetNextMessage no room in buffer for message in file - leave it there\n");
				}
			}
		}
	}

	TCDEBUGLOGA2("CInputStream::GetNextMessage numberMessages      =%d numberBytes      =%d\n",pInfo->numberMessages, pInfo->numberBytes);
	TCDEBUGLOGA2("CInputStream::GetNextMessage numberMessagesInBuff=%d numberBytesInBuff=%d\n",pInfo->numberMessages-pInfo->numberMessagesInFile, pInfo->numberBytes-pInfo->numberBytesInFile);
	TCDEBUGLOGA2("CInputStream::GetNextMessage numberMessagesInFile=%d numberBytesInFile=%d\n",pInfo->numberMessagesInFile, pInfo->numberBytesInFile);

	LOGPERF("GetNextMessage\n");

	ReleaseAccess();

	TCDEBUGLOGS("CInputStream::GetNextMessage done\n");
	TCDEBUGCLOSE();

	return outMsgLen;
}
DWORD CInputStream::GetNextMessageSize()
{
	TCDEBUGOPEN();
	TCDEBUGLOGA2("CInputStream::GetNextMessageSize clientId = %d this = %x\n", m_ClientID, this);

	DWORD length = 0;

	WaitForAccess();

	pInputStreamInfo pInfo = GetInfoPtr();
	pInputStreamData pData = GetDataPtr();

	if (pInfo->numberMessages > 0)
	{
		BYTE* ptr = pData;
		length = *(DWORD*)ptr;
	}

	ReleaseAccess();

	TCDEBUGLOGA1("CInputStream::GetNextMessageSize length = %d\n", length);
	TCDEBUGCLOSE();

	LOGPERF("GetNextMessageSize\n");
	return length;
}
void CInputStream::GetMessageSizes(long inNumberMessages, DWORD* outMessageSizes)
{
	TCDEBUGOPEN();
	TCDEBUGLOGA3("CInputStream::GetMessageSizes clientId = %d this = %x inNum = %d\n", m_ClientID, this, inNumberMessages);

	WaitForAccess();
	pInputStreamData pData = GetDataPtr();
	pInputStreamFile pFile = GetFilePtr();
	pInputStreamInfo pInfo = GetInfoPtr();

	long ntotal = pInfo->numberMessages;			// number of total messages (buffer + file)
	long nfile = pInfo->numberMessagesInFile;		// number of file messages
	long nbuffer = ntotal - nfile;				// number of buffer messages
	long nreadtotal = min(ntotal, inNumberMessages);	// number of total messages to read
	long nreadbuffer = min(nbuffer, nreadtotal);		// number of buffer messages to read
	long nreadfile = nreadtotal - nreadbuffer;			// number of file messages to read
	TCDEBUGLOGA3("CInputStream::GetMessageSizes ntotal=%d nfile=%d nbuffer=%d\n", ntotal, nfile, nbuffer);
	TCDEBUGLOGA3("CInputStream::GetMessageSizes nreadtotal=%d nreadfile=%d nreadbuffer=%d\n", nreadtotal, nreadfile, nreadbuffer);


	if (nreadtotal > 0)
	{
		if (nreadbuffer > 0)
		{
			BYTE* ptr = pData;
			DWORD prevSize = 0;
			for (long i = 0; i < nreadbuffer; i++)
			{
				DWORD len = *(DWORD*)&ptr[prevSize];
				outMessageSizes[i] = len;
				prevSize += len + sizeof(DWORD);
			}
		}
		if (nreadfile > 0)
		{
			BYTE* ptr = pFile;
			DWORD prevSize = 0;
			for (long i = 0; i < nreadfile; i++)
			{
				DWORD len = *(DWORD*)&ptr[prevSize];
				outMessageSizes[nreadbuffer+i] = len;
				prevSize += len + sizeof(DWORD);
			}
		}
	}


	ReleaseAccess();

	TCDEBUGLOGS("CInputStream::GetMessageSizes done\n");
	TCDEBUGCLOSE();
	LOGPERF("GetMessageSizes\n");
}

void CInputStream::GetTotalMessageSize(long inNumberMessages, DWORD& outTotalSize)
{
	TCDEBUGOPEN();
	TCDEBUGLOGA3("CInputStream::GetTotalMessageSize clientId = %d this = %x inNum = %d\n", m_ClientID, this, inNumberMessages);

	WaitForAccess();
	pInputStreamData pData = GetDataPtr();
	pInputStreamFile pFile = GetFilePtr();
	pInputStreamInfo pInfo = GetInfoPtr();

	long ntotal = pInfo->numberMessages;			// number of total messages (buffer + file)
	long nfile = pInfo->numberMessagesInFile;		// number of file messages
	long nbuffer = ntotal - nfile;				// number of buffer messages
	long nreadtotal = min(ntotal, inNumberMessages);	// number of total messages to read
	long nreadbuffer = min(nbuffer, nreadtotal);		// number of buffer messages to read
	long nreadfile = nreadtotal - nreadbuffer;			// number of file messages to read
	TCDEBUGLOGA3("CInputStream::GetTotalMessageSize ntotal=%d nfile=%d nbuffer=%d\n", ntotal, nfile, nbuffer);
	TCDEBUGLOGA3("CInputStream::GetTotalMessageSize nreadtotal=%d nreadfile=%d nreadbuffer=%d\n", nreadtotal, nreadfile, nreadbuffer);

	outTotalSize = 0;

	if (nreadtotal > 0)
	{
		if (nreadbuffer > 0)
		{
			BYTE* ptr = pData;
			DWORD prevSize = 0;
			for (long i = 0; i < nreadbuffer; i++)
			{
				DWORD len = *(DWORD*)&ptr[prevSize];
				outTotalSize += len;
				prevSize += len + sizeof(DWORD);
			}
		}
		if (nreadfile > 0)
		{
			BYTE* ptr = pFile;
			DWORD prevSize = 0;
			for (long i = 0; i < nreadfile; i++)
			{
				DWORD len = *(DWORD*)&ptr[prevSize];
				outTotalSize += len;
				prevSize += len + sizeof(DWORD);
			}
		}
	}


	ReleaseAccess();

	TCDEBUGLOGS("CInputStream::GetTotalMessageSize done\n");
	TCDEBUGCLOSE();
	LOGPERF("GetTotalMessageSize\n");
}
#endif // USE_CIRCULAR_BUFFER

LONG CInputStream::GetNumberMessages()
{
	TCDEBUGOPEN();
	TCDEBUGLOGA2("CInputStream::GetNumberMessages clientId = %d this = %x\n", m_ClientID, this);

	LONG number = 0;

	WaitForAccess();

	pInputStreamInfo pInfo = GetInfoPtr();

	number = pInfo->numberMessages;

	ReleaseAccess();

	TCDEBUGLOGA1("CInputStream::GetNumberMessages number = %d\n", number);
	TCDEBUGCLOSE();

	LOGPERF("GetNumberMessages\n");
	return number;
}



BOOL CInputStreamData::Init()
{
	if (IsCreator())
	{
	}
	return TRUE;
}
BOOL CInputStreamInfo::Init()
{
	if (IsCreator())
	{
		pInputStreamInfo pInfo = (pInputStreamInfo)GetDataPtr();
		pInfo->numberBytes = 0;
		pInfo->numberMessages = 0;
		pInfo->numberBytesInFile = 0;
		pInfo->numberMessagesInFile = 0;
#ifdef USE_CIRCULAR_BUFFER
		pInfo->bufferRead = 0;				// where to read from buffer
		pInfo->bufferPeek = 0;				// where to peek some data from buffer
		pInfo->bufferWrite = 0;				// where to write to buffer
		pInfo->bufferSize = 0;				// current size of buffer
		pInfo->bufferCapacity = 0;			// total capacity of buffer
#endif
	}
	return TRUE;
}

// real file on disk (ie, not tied to swap file)
void CInputStreamFile::SetClientId(long clientId)
{
	m_ClientID = clientId;
}
BOOL CInputStreamFile::Open(DWORD dwSize, CHAR* filePath)
{
	BOOL fOk = FALSE;

	// first process (clients) will create and map file
	// second process (server) will get an error on create, but will go ahead and map
	m_hFile = CreateFile(
		filePath, 
		GENERIC_READ|GENERIC_WRITE, 
		FILE_SHARE_READ|FILE_SHARE_WRITE,
		0,
		CREATE_ALWAYS, 
		FILE_ATTRIBUTE_NORMAL|FILE_FLAG_DELETE_ON_CLOSE,
		0);

	char mapname[80];
	sprintf(mapname, "%s%d", INPUTSTREAMOVERFLOW_MAP_BASENAME, m_ClientID);
	if (m_hFile != INVALID_HANDLE_VALUE)
	{
		// we got a good handle (we might have gotten an error, but create was successful)
		// create a mapping to this file
		fOk = CSharedData::Open(m_hFile, dwSize, mapname);
		TCDEBUGOPEN();
		TCDEBUGLOGS("CInputStreamFile::Open CreateFile successful\n");
		TCDEBUGCLOSE();
	}
	else
	{
		// this causes the open to do an OpenFileMapping instead of a CreateFileMapping (file handle not needed in former)
		fOk = CSharedData::Open(INVALID_HANDLE_VALUE, dwSize, mapname);
		TCDEBUGOPEN();
		TCDEBUGLOGS("CInputStreamFile::Open CreateFile failed\n");
		TCDEBUGCLOSE();
	}

	return fOk;
}

CInputStreamFile::Close()
{
	// close all mapping handles
	CSharedData::Close();
	
	// close file
	if (m_hFile != INVALID_HANDLE_VALUE)
	{
		CloseHandle(m_hFile);
		m_hFile = INVALID_HANDLE_VALUE;
	}
}

BOOL CInputStreamFile::Init()
{
	return TRUE;
}

#ifdef LOG_PERFORMANCE
static void logPerf(char* msg)
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
static void openPerf()
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
static void closePerf()
{
	if (fpLog)
	{
		fflush(fpLog);
		fclose(fpLog);
	}
	fpLog = NULL;
}
#endif