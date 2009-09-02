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
// TcpComm.cpp: implementation of the CTcpComm class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "TcpComm.h"
//#include "pn_const.h"
//#include "OSTConstants.h"
#include "Connection.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
CTcpComm::CTcpComm()
{
#ifdef _DEBUG
	if (gDoLogging)
	{
//		FILE* f = fopen("c:\\tcf\\tcpcommlog.txt", "at");
//		fprintf(f, "CTcpComm::CTcpComm() (default constructor)\n");
//		fclose(f);
	}
#endif
	m_socket = INVALID_SOCKET;
	m_timeOut.tv_sec = TIMEOUT_SEC(DEFAULT_SOCKET_TIMEOUT);
	m_timeOut.tv_usec = TIMEOUT_USEC(DEFAULT_SOCKET_TIMEOUT);

	m_hSocketEvent = WSA_INVALID_EVENT;

}

CTcpComm::CTcpComm(ConnectData* connectSettings, DWORD connectionId, CBaseProtocol* protocol)
{
#ifdef _DEBUG
	if (gDoLogging)
	{
//		FILE* f = fopen("c:\\tcf\\tcpcommlog.txt", "at");
//		fprintf(f, "connectSettings=%x connectionId=%d, protocol=%x\n", connectSettings, connectionId, protocol);
//		fclose(f);
	}
#endif
	m_connId = connectionId;
	m_Protocol = protocol;

	m_ConnectSettings = new ConnectData();
	memcpy(m_ConnectSettings, connectSettings, sizeof(ConnectData));

#if (defined(LOG_COMM) || defined(LOG_PROCCOMM)) && defined(_DEBUG)
	if (gDoLogging)
	{
		m_CommDebugLog = new TCDebugLog("TCF_Comm", connectionId, 2000L);
		m_ProcDebugLog = new TCDebugLog("TCF_CommP", connectionId, 2000L);
	}
#endif
	m_socket = INVALID_SOCKET;
	m_timeOut.tv_sec = TIMEOUT_SEC(DEFAULT_SOCKET_TIMEOUT);
	m_timeOut.tv_usec = TIMEOUT_USEC(DEFAULT_SOCKET_TIMEOUT);

	m_hSocketEvent = WSA_INVALID_EVENT;

}
CTcpComm::~CTcpComm()
{
#ifdef _DEBUG
	if (gDoLogging)
	{
//		FILE* f = fopen("c:\\tcf\\tcpcommlog.txt", "at");
//		fprintf(f, "CTcpComm::~CTcpComm()\n");
//		fclose(f);
	}
#endif
	if (IsConnected())
	{
		if (m_socket != INVALID_SOCKET)
		{
			shutdown(m_socket, SD_BOTH);
			closesocket(m_socket);
			WSAClose();
		}
		m_isConnected = false;
	}
	if (m_pBuffer)
		delete[] m_pBuffer;

	if (m_hSocketEvent != WSA_INVALID_EVENT)
		WSACloseEvent(m_hSocketEvent);

}

//#define USE_EVENTS
;
long CTcpComm::OpenPort()
{
	COMMLOGOPEN();
	COMMLOGS("CTcpComm::OpenPort\n");

	long err = TCAPI_ERR_NONE;
	char* ipAddress = m_ConnectSettings->tcpSettings.ipAddress;
	char* ipPort = m_ConnectSettings->tcpSettings.ipPort;
	// set this to set socket to non-blocking
	// DWORD nonblock = 1;		// non-blocking
	DWORD nonblock = 0;		// blocking

	COMMLOGA2("CTcpComm::OpenPort ipAddress=%s ipPort=%s\n", ipAddress, ipPort);

	int wsaErr = 0;
	wsaErr = WSAInit();
	if (wsaErr != 0)
	{
		err = TCAPI_ERR_WHILE_CONFIGURING_MEDIA;
//		err = -1;
	}
	else
	{
		m_socket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
		COMMLOGA1("CTcpComm::OpenPort socket=%x\n", m_socket);
		if (m_socket == INVALID_SOCKET)
		{
			m_lastCommError = WSAGetLastError();
			WSASetLastError(0);
			WSAClose();
			err = TCAPI_ERR_WHILE_CONFIGURING_MEDIA;
		}
		else
		{
			if (ioctlsocket(m_socket, FIONBIO, &nonblock) == SOCKET_ERROR)
			{
				m_lastCommError = WSAGetLastError();
				WSASetLastError(0);
				closesocket(m_socket);
				m_socket = INVALID_SOCKET;
				WSAClose();
				err = TCAPI_ERR_WHILE_CONFIGURING_MEDIA;
			}
			else
			{
				int i = SO_MAX_MSG_SIZE;
				// set socket options
				BOOL keepAlive = TRUE;
				setsockopt(m_socket, SOL_SOCKET, SO_KEEPALIVE, (const char*)&keepAlive, sizeof(BOOL));

				struct linger l;
				l.l_onoff = 0; l.l_linger = 0;
				setsockopt(m_socket, SOL_SOCKET, SO_LINGER, (const char*)&l, sizeof(l));

				int sockRecvSize = MAX_TCP_MESSAGE_BUFFER_LENGTH;//(256*1024L);
				setsockopt(m_socket, SOL_SOCKET, SO_RCVBUF, (const char*)&sockRecvSize, sizeof(int));
				int sockSendSize = (64*1024L);
				setsockopt(m_socket, SOL_SOCKET, SO_SNDBUF, (const char*)&sockSendSize, sizeof(int));
				WSAGetLastError(); // ignore error for now
				WSASetLastError(0);

				// connect
				WORD wPort = atoi(ipPort);
				m_clientService.sin_family = AF_INET;
				m_clientService.sin_addr.S_un.S_addr = inet_addr(ipAddress);
				m_clientService.sin_port = htons(wPort);

				COMMLOGS("CTcpComm::OpenPort connect start\n");

				if (connect(m_socket, (SOCKADDR*)&m_clientService, sizeof(m_clientService)) == SOCKET_ERROR)
				{
					COMMLOGS("CTcpComm::OpenPort connect SOCKET_ERROR\n");
					int wsaErr = WSAGetLastError();
					WSASetLastError(0);
					COMMLOGA1("CTcpComm::OpenPort connect=wsaErr=%d\n", wsaErr);

					// socket is non-blocking
					if (wsaErr != WSAEWOULDBLOCK)
					{
						m_lastCommError = wsaErr;

						closesocket(m_socket);
						m_socket = INVALID_SOCKET;
						WSAClose();
						err = TCAPI_ERR_WHILE_CONFIGURING_MEDIA;
					}
					else // WSAEWOULDBLOCK error returned
					{
						// WSAEWOULDBLOCK use select now
						fd_set readfds, writefds, exceptfds;
						FD_ZERO(&readfds);
						FD_ZERO(&writefds);
						FD_ZERO(&exceptfds);
						FD_SET(m_socket, &readfds);
						FD_SET(m_socket, &writefds);
						FD_SET(m_socket, &exceptfds);

						int selRes = 0;
						while(1)
						{
							selRes = select(0, &readfds, &writefds, &exceptfds, &m_timeOut);
							if (selRes == SOCKET_ERROR)
							{
								wsaErr = WSAGetLastError();
								WSASetLastError(0);
								if (wsaErr != WSAEWOULDBLOCK)
								{
									// real error
									m_lastCommError = wsaErr;
									shutdown(m_socket, SD_BOTH);
									closesocket(m_socket);
									m_socket = INVALID_SOCKET;
									WSAClose();
									err = TCAPI_ERR_WHILE_CONFIGURING_MEDIA;
								}
								// else do another select
							}
							else if (selRes > 0)// select OK 
							{
								m_lastCommError = 0;
								m_isConnected = true;
								break; // done
							}
							else
							{
								// timed out
								m_lastCommError = WSAGetLastError();
								WSASetLastError(0);
								shutdown(m_socket, SD_BOTH);
								closesocket(m_socket);
								m_socket = INVALID_SOCKET;
								WSAClose();
								err = TCAPI_ERR_WHILE_CONFIGURING_MEDIA;
							}
						}
					}
				}
				else // connect return OK
				{
					COMMLOGS("CTcpComm::OpenPort connect OK\n");
					m_lastCommError = 0;
					m_isConnected = true;
				}
			}
		}
	}
	if (err == TCAPI_ERR_NONE)
	{
		// we are connected
		m_numberBytes = 0;
		m_pBuffer = new BYTE[MAX_TCP_MESSAGE_BUFFER_LENGTH];

#ifdef USE_EVENTS
		// create an event for the socket closing
		m_hSocketEvent = WSACreateEvent();
		::WSAEventSelect(m_socket, m_hSocketEvent, FD_CLOSE);
		// above call sets socket to non-blocking
		//  cannot reset to blocking after using WSAEventSelect
		//  thus this ioctlsocket call will fail
		ioctlsocket(m_socket, FIONBIO, &nonblock);
#endif
	}

	COMMLOGA1("CTcpComm::OpenPort err=%d\n", err);
	COMMLOGCLOSE();
	return err;
}

long CTcpComm::ClosePort()
{
	COMMLOGOPEN();
	COMMLOGS("CTcpComm::ClosePort\n");

	long err = TCAPI_ERR_NONE;

	if (!IsConnected())
	{
		err = TCAPI_ERR_MEDIA_NOT_OPEN;
	}
	else
	{
		if (m_socket != INVALID_SOCKET)
		{
			shutdown(m_socket, SD_BOTH);
			closesocket(m_socket);
			m_socket = INVALID_SOCKET;

			WSAClose();
		}
		m_isConnected = false;

		delete[] m_pBuffer;
		m_pBuffer = NULL;

		if (m_hSocketEvent != WSA_INVALID_EVENT)
		{
			WSACloseEvent(m_hSocketEvent);
			m_hSocketEvent = WSA_INVALID_EVENT;
		}
	}

	COMMLOGCLOSE();
	return err;
}

long CTcpComm::PollPort(DWORD &outSize)
{
	long err = TCAPI_ERR_NONE;
	DWORD numBytes = 0;
	outSize = 0;

	if (!IsConnected())
		return TCAPI_ERR_MEDIA_NOT_OPEN;

#ifdef USE_EVENTS
	int ret = ::WSAWaitForMultipleEvents(1, &m_hSocketEvent, FALSE, 0, FALSE);
	if (ret == WSA_WAIT_EVENT_0)
	{
		::WSAResetEvent(m_hSocketEvent);
		err = TCAPI_ERR_COMM_ERROR;
		m_lastCommError = WSAESHUTDOWN;
		return err;
	}
#endif
	int sockErr = 0; int optLen = sizeof(int);
	int getErr = getsockopt(m_socket, SOL_SOCKET, SO_ERROR, (char*)&sockErr, &optLen);
	if (getErr == 0)
	{
		if (sockErr)
		{
			err = TCAPI_ERR_COMM_ERROR;
			m_lastCommError = sockErr;
			return err;
		}
	}

	fd_set readfds, writefds, exceptfds;
	FD_ZERO(&readfds); 
	FD_ZERO(&writefds); 
	FD_ZERO(&exceptfds);
	FD_SET(m_socket, &readfds);
	FD_SET(m_socket, &writefds);
	FD_SET(m_socket, &exceptfds);

	bool portReady = false;
	{
		TIMEVAL pollTimeout = {0,0}; // just poll the status
		int selErr = select(0, &readfds, 0, 0, &pollTimeout);
		if (selErr > 0)
		{
			if (FD_ISSET(m_socket, &readfds))
			{
				m_lastCommError = 0;
				portReady = true;
			}
		}
		else if (selErr == SOCKET_ERROR)
		{
			m_lastCommError = WSAGetLastError();
			WSASetLastError(0);
			err = TCAPI_ERR_COMM_ERROR;
		}
	}

	if (portReady)
	{
		// read was signaled as ready
		int recvRet = recv(m_socket, (char*)&m_pPeekBuffer, sizeof(m_pPeekBuffer), MSG_PEEK);
		if (recvRet > 0)
		{
			if (ioctlsocket(m_socket, FIONREAD, &numBytes) == 0)
			{
				m_lastCommError = 0;
				outSize = numBytes;
			}
			else // SOCKET_ERROR
			{
				m_lastCommError = WSAGetLastError();
				WSASetLastError(0);
				err = TCAPI_ERR_COMM_ERROR;
			}
		}
		else if (recvRet == 0)
		{
			// read was signalled as ready but recv=0 signals that remote shutdown
			m_lastCommError = WSAESHUTDOWN;
			err = TCAPI_ERR_COMM_ERROR;
		}
		else
		{
			// SOCKET_ERROR: error on recv other than a shutdown
			m_lastCommError = WSAGetLastError();
			WSASetLastError(0);
			err = TCAPI_ERR_COMM_ERROR;
		}
	}
	return err;
}

long CTcpComm::ReadPort(DWORD inSize, void *outData, DWORD &outSize)
{
	long err = TCAPI_ERR_NONE;
	DWORD numBytes = 0;
	outSize = 0;

	if (!IsConnected())
		return TCAPI_ERR_MEDIA_NOT_OPEN;

	if (ioctlsocket(m_socket, FIONREAD, &numBytes) == 0)
	{
		if (numBytes > inSize)
			numBytes = inSize;
		int res = recv(m_socket, (char*)outData, numBytes, 0);
		if (res == SOCKET_ERROR)
		{
			long commErr = WSAGetLastError();
			WSASetLastError(0);
			if ((DWORD)commErr != m_lastCommError)
			{
				m_lastCommError = commErr;
			}
			err = TCAPI_ERR_COMM_ERROR;
		}
		else if (res == 0)
		{
			// recv=0 --> connection closed
			m_lastCommError = WSAESHUTDOWN;
			err = TCAPI_ERR_COMM_ERROR;
		}
		else
		{
			m_lastCommError = 0;
			outSize = numBytes;
		}
	}
	else
	{
		// SOCKET_ERROR on ioctlsocket
		m_lastCommError = WSAGetLastError();
		WSASetLastError(0);
		err = TCAPI_ERR_COMM_ERROR;
	}
	return err;
}
long CTcpComm::ProcessBuffer(CConnection* pConn, CRegistry* pRegistry, long& numberProcessed)
{

	long err = TCAPI_ERR_NONE;
	long routingErr = TCAPI_ERR_NONE;

	if (!IsConnected())
		return TCAPI_ERR_MEDIA_NOT_OPEN;

	if (!m_Protocol)
		return TCAPI_ERR_UNKNOWN_MEDIA_TYPE;

	DWORD protocolHeaderLength = m_Protocol->GetHeaderLength();

	// fill buffer
	if (m_numberBytes < MAX_TCP_MESSAGE_BUFFER_LENGTH)
	{
		DWORD outLen = MAX_TCP_MESSAGE_BUFFER_LENGTH - m_numberBytes;
		BYTE* ptr = &m_pBuffer[m_numberBytes];
		err = ReadPort(outLen, ptr, outLen);
		if (err == TCAPI_ERR_NONE && outLen > 0)
		{
			m_numberBytes += outLen;
		}
	}
	
	// now process buffer but only for complete messages
	if (err == TCAPI_ERR_NONE)
	{
		if (m_numberBytes >= protocolHeaderLength)
		{
			BYTE* ptr = m_pBuffer;
			long bytesRemaining = m_numberBytes;
			long usedLen = 0;
			bool done = false;
			long numberSkipped=0;

			while (!done)
			{
				DWORD fullMessageLength = bytesRemaining;
				DWORD rawLength = 0;
				BYTE* fullMessage = ptr;
				BYTE* rawMessage = ptr;
				BYTE msgId = 0;
				int result = m_Protocol->DecodeMessage(fullMessage, fullMessageLength, msgId, rawMessage, rawLength);
				if (result == DECODE_NOT_ENOUGH_BYTES_TO_SEARCH)
				{
					done = true;
				}
				else if (result == DECODE_MESSAGE_NOT_FOUND)
				{
					numberSkipped++;
					usedLen += fullMessageLength;
					bytesRemaining -= fullMessageLength;
					ptr += fullMessageLength;
					if (bytesRemaining < protocolHeaderLength)
						done = true;
				}
				else if (result == DECODE_MESSAGE_FOUND)
				{
					err = PreProcessMessage(msgId, fullMessageLength, fullMessage);
					if (err != TCAPI_ERR_NONE)
					{
						PROCLOGOPEN();
						PROCLOGA1("CTcpComm::ProcessBuffer Notify err = %x\n", err);
						PROCLOGCLOSE();
						// notify all clients right now 
						pConn->NotifyClientsCommError(err, false, 0);
						err = TCAPI_ERR_NONE;
					}
#ifdef _DEBUG
					int reallen = fullMessageLength;
					if (reallen > 80) reallen = 80;
					char msg[6];
					msg[0] = '\0';

					sTcpLogMsg[0] = '\0';
					if (reallen > 0)
					{
						sTcpLogMsg[0] = '\0';
						for (int i = 0; i < reallen; i++)
						{
							if (isalnum(ptr[i]))
							{
								sprintf(msg, "%c", ptr[i]);
							}
							else
							{
								sprintf(msg, "%02.2x ", ptr[i]);
							}
							strcat(sTcpLogMsg, msg);
						}
					}
#endif
					PROCLOGOPEN();
					PROCLOGA5("CTcpComm::ProcessBuffer - RouteMesssage pRegistry = %x id=%x len=%d len=%d msg=%s\n", pRegistry, msgId, fullMessageLength, rawLength, sTcpLogMsg);
					PROCLOGCLOSE();

					err = pRegistry->RouteMessage(msgId, fullMessage, fullMessageLength, rawMessage, rawLength);
					if (err != TCAPI_ERR_NONE) routingErr = err; // saved for future

					numberProcessed++;
					usedLen += fullMessageLength;
					bytesRemaining -= fullMessageLength;
					ptr += fullMessageLength;
					if (bytesRemaining < protocolHeaderLength)
						done = true;
				}
			}
			DeleteMsg(usedLen);
			PROCLOGOPEN();
			PROCLOGA2("CTcpComm::ProcessBuffer - numberSkipped=%d numberProcessed=%d\n", numberSkipped, numberProcessed);
			PROCLOGCLOSE();
		}
	}

	if (routingErr == TCAPI_ERR_NONE)
		return err;
	else
		return routingErr;
}


long CTcpComm::SendDataToPort(DWORD inSize, const void* inData)
{
	COMMLOGOPEN();
	COMMLOGS("CTcpComm::SendDataToPort\n");
	
	long err = TCAPI_ERR_NONE;

	if (!IsConnected())
	{
		COMMLOGCLOSE();
		return TCAPI_ERR_MEDIA_NOT_OPEN;
	}

#ifdef USE_EVENTS
	int ret = ::WSAWaitForMultipleEvents(1, &m_hSocketEvent, FALSE, 0, FALSE);
	if (ret == WSA_WAIT_EVENT_0)
	{
		::WSAResetEvent(m_hSocketEvent);
		err = TCAPI_ERR_COMM_ERROR;
		m_lastCommError = WSAESHUTDOWN;
		COMMLOGCLOSE();
		return err;
	}
#endif
	int sockErr = 0; int optLen = sizeof(int);
	int getErr = getsockopt(m_socket, SOL_SOCKET, SO_ERROR, (char*)&sockErr, &optLen);
	if (getErr == 0)
	{
		if (sockErr)
		{
			err = TCAPI_ERR_COMM_ERROR;
			m_lastCommError = sockErr;
			COMMLOGA1("CTcpComm::SendDataToPort getsockopt=%d\n", sockErr);
			COMMLOGCLOSE();
			return err;
		}
	}

	fd_set readfds, writefds, exceptfds;
	FD_ZERO(&readfds); 
	FD_ZERO(&writefds); 
	FD_ZERO(&exceptfds);
	FD_SET(m_socket, &readfds);
	FD_SET(m_socket, &writefds);
	FD_SET(m_socket, &exceptfds);

	COMMLOGS("CTcpComm::SendDataToPort select\n");
	bool portReady = false;
	{
		int selErr = select(0, &readfds, &writefds, &exceptfds, &m_timeOut);
		if (selErr > 0)
		{
			if (FD_ISSET(m_socket, &writefds))
			{
				m_lastCommError = 0;
				portReady = true;
			}
		}
		else if (selErr == SOCKET_ERROR)
		{
			m_lastCommError = WSAGetLastError();
			WSASetLastError(0);
			COMMLOGA1("CTcpComm::SendDataToPort select(SOCKET_ERROR)=%d\n", m_lastCommError);
			err = TCAPI_ERR_COMM_ERROR;
		}
		else if (selErr == 0) // timeout
		{
			m_lastCommError = WSAGetLastError();
			WSASetLastError(0);
			COMMLOGA1("CTcpComm::SendDataToPort select(timeout)=%d\n", m_lastCommError);
			err = TCAPI_ERR_COMM_ERROR;
		}
	}
	COMMLOGA1("CTcpComm::SendDataToPort portReady=%d\n", portReady);
	if (portReady)
	{
		COMMLOGS("CTcpComm::SendDataToPort send start\n");
		// loop until all bytes are sent
		DWORD bytesRemaining = inSize;
		DWORD nSent = 0;
		char* unsent = (char*)inData;
		while (bytesRemaining)
		{
			nSent = send(m_socket, unsent, bytesRemaining, 0);
			if (nSent == SOCKET_ERROR)
			{
				int wsaErr = WSAGetLastError();
				WSASetLastError(0);
				// ignore "would block" errors
				if (wsaErr != WSAEWOULDBLOCK)
				{
					// TODO: error handling
					COMMLOGA1("CTcpComm::SendDataToPort send(SOCKET_ERROR)=%d\n", wsaErr);
					m_lastCommError = wsaErr;
					err = TCAPI_ERR_COMM_ERROR;
					break;
				}
			}
			else
			{
				m_lastCommError = 0;
				unsent += nSent;
				bytesRemaining -= nSent;
			}
		} // end while
		COMMLOGS("CTcpComm::SendDataToPort send done\n");
#ifdef _DEBUG
		BYTE* ptr = (BYTE*)inData;
		long numBytes = (inSize > 20) ? 20 : inSize;
		char msg[200];
		sprintf(msg, "CTcpComm::SendDataToPort data = ");
		for (int i = 0; i < numBytes; i++)
		{
			sprintf(msg, "%s %02.2x", msg, ptr[i]);
		}
		sprintf(msg, "%s\n", msg);
		COMMLOGS(msg);
#endif
	}

	COMMLOGCLOSE();
	return err;
}

void CTcpComm::DeleteMsg(DWORD inMsgLength)
{
	// inMsgLength includes header
	// delete from beginning of buffer
	if (inMsgLength == 0)
		return;
	if (m_numberBytes > 0 && m_numberBytes >= inMsgLength)
	{
		size_t moveLen = m_numberBytes - inMsgLength;
		if (moveLen > 0)
			memcpy(&m_pBuffer[0], &m_pBuffer[inMsgLength], moveLen);
		m_numberBytes -= inMsgLength;
	}
}
bool CTcpComm::IsConnectionEqual(ConnectData* pConn)
{
	if ((strcmp(pConn->tcpSettings.ipAddress, m_ConnectSettings->tcpSettings.ipAddress) == 0))
	{
		if ((strcmp(pConn->tcpSettings.ipPort, m_ConnectSettings->tcpSettings.ipPort) == 0))
		{
			// same port and same IP
			return true;
		}
		else
		{
			// different port but same IP
			return false;
		}
	}
	else
	{
		// different IP
		return false;
	}
}

int CTcpComm::WSAInit() 
{
	int wsaErr = 0;

	COMMLOGOPEN();
	COMMLOGS("CTcpComm::WSAInit\n");

	WSADATA wsaData;
	wsaErr = WSAStartup(MAKEWORD(2,2), &wsaData);

	COMMLOGCLOSE();
	return wsaErr;
}

void CTcpComm::WSAClose()
{
	COMMLOGOPEN();
	COMMLOGS("CTcpComm::WSAClose\n");

	WSACleanup();

	COMMLOGCLOSE();
}

