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
// ServerManager.cpp: implementation of the CServerManager class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "ServerManager.h"
#include "ConnectionImpl.h"
#include "resource.h"


#ifdef _DEBUG
extern BOOL gDoLogging;
#endif

#define LOG_SERVERMANAGER
#if defined(LOG_SERVERMANAGER) && defined(_DEBUG)
extern char TCDebugMsg[];
#define TCDEBUGOPEN() if (gDoLogging) { m_DebugLog->WaitForAccess(); }
#define TCDEBUGLOGS(s) if (gDoLogging) { sprintf(TCDebugMsg,"%s", s); m_DebugLog->log(TCDebugMsg); }
#define TCDEBUGLOGA1(s, a1) if (gDoLogging) { sprintf(TCDebugMsg, s, a1); m_DebugLog->log(TCDebugMsg); }
#define TCDEBUGLOGA2(s, a1, a2) if (gDoLogging) { sprintf(TCDebugMsg, s, a1, a2); m_DebugLog->log(TCDebugMsg); }
#define TCDEBUGLOGA3(s, a1, a2, a3) if (gDoLogging) { sprintf(TCDebugMsg, s, a1, a2, a3); m_DebugLog->log(TCDebugMsg); }
#define TCDEBUGCLOSE() if (gDoLogging) { m_DebugLog->ReleaseAccess(); }
#else
#define TCDEBUGOPEN()
#define TCDEBUGLOGS(s)
#define TCDEBUGLOGA1(s, a1)
#define TCDEBUGLOGA2(s, a1, a2)
#define TCDEBUGLOGA3(s, a1, a2, a3)
#define TCDEBUGCLOSE()
#endif

extern char* gServerLocation;
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
CServerManager::CServerManager()
{
	m_DebugLog = NULL;
	m_Server = NULL;
	m_ConnectionList = NULL;
	m_ClientList = NULL;
	m_NextClientId = 0;
	m_NextConnectionId = 0;
	m_Version[0] = NULL;
	m_ExeLocation = NULL;
	m_ProtocolList = NULL;
	m_CommList = NULL;

}
CServerManager::CServerManager(const char* exeLocation)
{
#ifdef _DEBUG
	if (gDoLogging)
		m_DebugLog = new TCDebugLog("TCF_ServerLog", ::GetCurrentProcessId());
	else
		m_DebugLog = NULL;
#else
	m_DebugLog = NULL;
#endif
	TCDEBUGOPEN();
	TCDEBUGLOGS("CServerManager::CServerManager\n");

	m_Server = new CServerCommand();

	m_ConnectionList = new ConnectionList();
	m_ConnectionList->clear();

	m_ClientList = new ClientList();
	m_ClientList->clear();

	m_NextClientId = ::GetCurrentProcessId() * 100;
	m_NextConnectionId = (::GetCurrentProcessId() * 100) + 100;

	int ret = ::LoadString(::GetModuleHandle(NULL), IDS_VERSION, m_Version, MAX_VERSION_STRING);
	TCDEBUGLOGA1(" version=%s\n", m_Version);

	TCDEBUGLOGA1(" exeLocation=%s\n", exeLocation);
	m_ExeLocation = new char[MAX_EXEPATHNAME];
	m_ExeLocation[0] = '\0';
	strcpy(m_ExeLocation, exeLocation);
	TCDEBUGLOGA1(" m_ExeLocation=%s\n", m_ExeLocation);

	m_ProtocolList = new ProtocolRegistry();
	m_ProtocolList->clear();

	m_CommList = new CommRegistry();
	m_CommList->clear();

	TCDEBUGCLOSE();
}

CServerManager::~CServerManager()
{
	TCDEBUGOPEN();
	TCDEBUGLOGS("CServerManager::~CServerManager\n");

	if (m_Server)
		delete m_Server;

	if (m_ConnectionList)
	{
		m_ConnectionList->clear();
		delete m_ConnectionList;
	}

	if (m_ClientList)
	{
		m_ClientList->clear();
		delete m_ClientList;
	}

	if (m_ExeLocation)
		delete[] m_ExeLocation;

	if (m_ProtocolList)
	{
		m_ProtocolList->clear();
		delete m_ProtocolList;
	}

	if (m_CommList)
	{
		m_CommList->clear();
		delete m_CommList;
	}

	TCDEBUGCLOSE();

	if (m_DebugLog)
		delete m_DebugLog;

}
void CServerManager::CommandThread()
{
	TCDEBUGOPEN();
	TCDEBUGLOGS("CServerManager::CommandThread\n");
	TCDEBUGCLOSE();
	
	bool done = false;
	eServerCommand command = eCmdNone;
	ServerCommandData cmdrsp; pServerCommandData pCmdrsp = &cmdrsp;
	pServerMessageData pMsg = new ServerMessageData();

	TCDEBUGOPEN();
	RegisterAllProtocols();
	RegisterAllComms();
	TCDEBUGCLOSE();

	while(!done)
	{
		while(!m_Server->GetCommand(pCmdrsp, pMsg))
		{
			Sleep(1);
		}
		command = pCmdrsp->command;

		switch(command)
		{
		case eCmdConnect:
			{
	TCDEBUGOPEN();
				TCDEBUGLOGS(" eCmdConnect\n");
	TCDEBUGCLOSE();

				long ret = TCAPI_ERR_NONE;
				BOOL connCreated = FALSE;
				CConnectionImpl* conn = (CConnectionImpl*)FindConnection(&pCmdrsp->connectSettings);
				if (conn == NULL)
				{
					// create new one
					DWORD connId = m_NextConnectionId;
					conn = new CConnectionImpl(pCmdrsp->connectSettings, connId);

					TCDEBUGOPEN();
					const char* commPath = FindCommPath(pCmdrsp->connectSettings.connectType);
					TCDEBUGCLOSE();
					TCDEBUGOPEN();
					const char* protPath = FindProtocolPath(pCmdrsp->connectSettings.decodeFormat);
					TCDEBUGCLOSE();

					if (protPath == NULL || commPath == NULL)
					{
						ret = TCAPI_ERR_UNKNOWN_MEDIA_TYPE;
					}
					else
					{
						if (conn->CreateCommProtocols(commPath, protPath))
						{
							connCreated = TRUE;
						}
						else
						{
							ret = TCAPI_ERR_UNKNOWN_MEDIA_TYPE;
						}
					}
				}
				if (ret == TCAPI_ERR_NONE && (conn->IsDisconnected() || conn->IsRetryTimedOut()))
				{
					ret = conn->DoConnect();
				}
				if (ret == TCAPI_ERR_NONE)
				{
					DWORD id = m_NextClientId++;
					// create client
					CClient* client = new CClient(conn, pCmdrsp->clientOptions, id);
					// add client to connection's list
					conn->AddClient(client);
					// add client to total list
					m_ClientList->push_back(client);
					// add connection to connection list
					if (connCreated)
						m_ConnectionList->push_back(conn);

					m_NextConnectionId++;
					pCmdrsp->response = eRspOK;
					pCmdrsp->clientId = id;
					m_Server->SendResponse(pCmdrsp);
				}
				else
				{
					if (conn->m_OsError > 0)
						pCmdrsp->osError = conn->m_OsError;
					else
						pCmdrsp->osError = 0;
					if (connCreated)
						delete conn;
					pCmdrsp->response = eRspError;
					pCmdrsp->error = ret;
					m_Server->SendResponse(pCmdrsp);
				}
			}
			break;
		case eCmdDisconnect:
			{
	TCDEBUGOPEN();
				TCDEBUGLOGS(" eCmdDisconnect\n");
	TCDEBUGCLOSE();
				DWORD id = pCmdrsp->clientId;
				// find this client in our list
				CClient* client = FindClient(id);
				if (client)
				{
					// get the connection for this client
					CConnectionImpl* conn = (CConnectionImpl*)client->GetConnection();
					// pause the processing so we can delete the client 
					conn->PauseProcessing();
					// stop processing this client
					client->Stop();
					// remove client from registry
					conn->RemoveClientFromRegistry(client);
					// remove from connections client list
					conn->RemoveClient(client);
					// remove from total client list
					RemoveClient(client);
					// delete client
					delete client;
					// no more clients on this connection, disconnect this connection
					if (conn->GetNumberClients() == 0)
					{
						conn->ExitProcessing();
						conn->DoDisconnect();
						RemoveConnection(conn);
						delete conn;
					}
					else
					{
						conn->RestartProcessing();
					}
				}
				pCmdrsp->response = eRspOK;
				m_Server->SendResponse(pCmdrsp);
			}
			break;
		case eCmdSetMessageIds:
			{
	TCDEBUGOPEN();
				TCDEBUGLOGS(" eCmdSetMessageIds\n");
	TCDEBUGCLOSE();

				long ret = TCAPI_ERR_NONE;
				DWORD id = pCmdrsp->clientId;
				bool restart = false;
				// find client
				CClient* client = FindClient(id);
				if (client)
				{
					// get connection
					CConnectionImpl* conn = (CConnectionImpl*)client->GetConnection();
					if (conn->IsProcessingContinuing())
					{
						conn->PauseProcessing();
						restart = true;
					}
					if (conn->IsDisconnected())
					{
						ret = TCAPI_ERR_MEDIA_NOT_OPEN;
					}
					else if (conn->IsRetryInProgress())
					{
						ret = TCAPI_ERR_COMM_RETRY_IN_PROGRESS;
					}
					else if (conn->IsRetryTimedOut())
					{
						ret = TCAPI_ERR_COMM_TIMEOUT;
					}

					if (ret == TCAPI_ERR_NONE)
					{
						// add client to connection's registry
						conn->AddClientToRegistry(client, pCmdrsp->number, pCmdrsp->messageIds);
					}
					if (restart)
						conn->RestartProcessing();
				}
				if (ret == TCAPI_ERR_NONE)
				{
					pCmdrsp->response = eRspOK;
				}
				else
				{
					pCmdrsp->response = eRspError;
					pCmdrsp->error = ret;
				}
				m_Server->SendResponse(pCmdrsp);
			}
			break;
		case eCmdGetNumberConnections:
			{
	TCDEBUGOPEN();
				TCDEBUGLOGS(" eCmdGetNumberConnections\n");
	TCDEBUGCLOSE();
				long num = m_ConnectionList->size();
				pCmdrsp->response = eRspOK;
				pCmdrsp->numConnections = num;
				m_Server->SendResponse(pCmdrsp);
			}
			break;
		case eCmdGetConnectionType:
			{
	TCDEBUGOPEN();
				TCDEBUGLOGS(" eCmdGetConnectionType\n");
	TCDEBUGCLOSE();
				long index = pCmdrsp->index;
				CConnectionImpl* pConn = (CConnectionImpl*)FindConnection(index);
				if (pConn != NULL)
				{
					pCmdrsp->response = eRspOK;
					strncpy(pCmdrsp->connectSettings.connectType, pConn->m_ConnectSettings->connectType, MAX_CONNECTION_TYPE);
//					pCmdrsp->connectSettings.connectType = pConn->m_ConnectSettings->connectType;
					m_Server->SendResponse(pCmdrsp);
				}
				else
				{
					pCmdrsp->response = eRspError;
					pCmdrsp->error = TCAPI_ERR_MISSING_CONNECTION_SPEC;
					m_Server->SendResponse(pCmdrsp);
				}
			}
			break;
		case eCmdOpenStream:
			{
	TCDEBUGOPEN();
				TCDEBUGLOGS(" eCmdOpenStream\n");
	TCDEBUGCLOSE();

				long ret = TCAPI_ERR_NONE;
				// find client
				DWORD id = pCmdrsp->clientId;
				CClient* client = FindClient(id);
				if (client)
				{
					// get connection
					CConnectionImpl* conn = (CConnectionImpl*)client->GetConnection();
					if (conn->IsDisconnected())
					{
						ret = TCAPI_ERR_MEDIA_NOT_OPEN;
					}
					else if (conn->IsRetryInProgress())
					{
						ret = TCAPI_ERR_COMM_RETRY_IN_PROGRESS;
					}
					else if (conn->IsRetryTimedOut())
					{
						ret = TCAPI_ERR_COMM_TIMEOUT;
					}

					if (ret == TCAPI_ERR_NONE)
					{
						bool restart = false;
						if (conn->IsProcessingContinuing())
						{
							restart = true;
							conn->PauseProcessing();
						}
						client->OpenStream(&pCmdrsp->destinationOptions);
						if (restart)
							conn->RestartProcessing();
					}
				}
				if (ret == TCAPI_ERR_NONE)
				{
					pCmdrsp->response = eRspOK;
				}
				else
				{
					pCmdrsp->response = eRspError;
					pCmdrsp->error = ret;
				}
				m_Server->SendResponse(pCmdrsp);
			}
			break;
		case eCmdCloseStream:
			{
	TCDEBUGOPEN();
				TCDEBUGLOGS(" eCmdCloseStream\n");
	TCDEBUGCLOSE();

				long ret = TCAPI_ERR_NONE;
				DWORD id = pCmdrsp->clientId;
				bool restart = false;
				CClient* client = FindClient(id);
				if (client)
				{
					// get connection
					CConnectionImpl* conn = (CConnectionImpl*)client->GetConnection();
					if (conn->IsConnected())
					{
						if (!conn->IsRetryTimedOut() && !conn->IsRetryInProgress())
						{
							if (conn->IsProcessingContinuing())
							{
								conn->PauseProcessing();
								restart = true;
							}
						}
						client->CloseStream();
						if (!conn->IsRetryTimedOut() && !conn->IsRetryInProgress())
						{
							if (restart)
								conn->RestartProcessing();
						}

					}
				}
				if (ret == TCAPI_ERR_NONE)
				{
					pCmdrsp->response = eRspOK;
				}
				else
				{
					pCmdrsp->response = eRspError;
					pCmdrsp->error = ret;
				}
				m_Server->SendResponse(pCmdrsp);
			}
			break;
		case eCmdOpenMessageFile:
			{
	TCDEBUGOPEN();
				TCDEBUGLOGS(" eCmdOpenMessageFile\n");
	TCDEBUGCLOSE();

				long ret = TCAPI_ERR_NONE;
				// find client
				DWORD id = pCmdrsp->clientId;
				CClient* client = FindClient(id);
				if (client)
				{
					// get connection
					CConnectionImpl* conn = (CConnectionImpl*)client->GetConnection();
					if (conn->IsDisconnected())
					{
						ret = TCAPI_ERR_MEDIA_NOT_OPEN;
					}
					else if (conn->IsRetryInProgress())
					{
						ret = TCAPI_ERR_COMM_RETRY_IN_PROGRESS;
					}
					else if (conn->IsRetryTimedOut())
					{
						ret = TCAPI_ERR_COMM_TIMEOUT;
					}

					if (ret == TCAPI_ERR_NONE)
					{
						bool restart = false;
						if (conn->IsProcessingContinuing())
						{
							restart = true;
							conn->PauseProcessing();
						}
						client->OpenMessageFile(&pCmdrsp->destinationOptions);
						if (restart)
							conn->RestartProcessing();
					}
				}
				if (ret == TCAPI_ERR_NONE)
				{
					pCmdrsp->response = eRspOK;
				}
				else
				{
					pCmdrsp->response = eRspError;
					pCmdrsp->error = ret;
				}
				m_Server->SendResponse(pCmdrsp);
			}
			break;
		case eCmdCloseMessageFile:
			{
	TCDEBUGOPEN();
				TCDEBUGLOGS(" eCmdCloseMessageFile\n");
	TCDEBUGCLOSE();

				long ret = TCAPI_ERR_NONE;
				DWORD id = pCmdrsp->clientId;
				bool restart = false;
				CClient* client = FindClient(id);
				if (client)
				{
					// get connection
					CConnectionImpl* conn = (CConnectionImpl*)client->GetConnection();
					if (conn->IsConnected())
					{
						if (!conn->IsRetryTimedOut() && !conn->IsRetryInProgress())
						{
							if (conn->IsProcessingContinuing())
							{
								conn->PauseProcessing();
								restart = true;
							}
						}
						client->CloseMessageFile();
						if (!conn->IsRetryTimedOut() && !conn->IsRetryInProgress())
						{
							if (restart)
								conn->RestartProcessing();
						}

					}
				}
				if (ret == TCAPI_ERR_NONE)
				{
					pCmdrsp->response = eRspOK;
				}
				else
				{
					pCmdrsp->response = eRspError;
					pCmdrsp->error = ret;
				}
				m_Server->SendResponse(pCmdrsp);
			}
			break;
		case eCmdClearMessageFile:
			{
	TCDEBUGOPEN();
				TCDEBUGLOGS(" eCmdClearMessageFile\n");
	TCDEBUGCLOSE();

				long ret = TCAPI_ERR_NONE;
				DWORD id = pCmdrsp->clientId;
				bool restart = false;
				CClient* client = FindClient(id);
				if (client)
				{
					// get connection
					CConnectionImpl* conn = (CConnectionImpl*)client->GetConnection();
					if (conn->IsConnected())
					{
						if (!conn->IsRetryTimedOut() && !conn->IsRetryInProgress())
						{
							if (conn->IsProcessingContinuing())
							{
								conn->PauseProcessing();
								restart = true;
							}
						}
						client->ClearMessageFile();
						if (restart)
							conn->RestartProcessing();
					}
				}
				if (ret == TCAPI_ERR_NONE)
				{
					pCmdrsp->response = eRspOK;
				}
				else
				{
					pCmdrsp->response = eRspError;
					pCmdrsp->error = ret;
				}
				m_Server->SendResponse(pCmdrsp);
			}
			break;
		case eCmdStart:
			{
	TCDEBUGOPEN();
				TCDEBUGLOGS(" eCmdStart\n");
	TCDEBUGCLOSE();

				long ret = TCAPI_ERR_NONE;
				DWORD id = pCmdrsp->clientId;
				CClient* client = FindClient(id);
				if (client)
				{
					// get connection
					CConnectionImpl* conn = (CConnectionImpl*)client->GetConnection();
					conn->PauseProcessing();
					if (conn->IsDisconnected())
					{
						ret = TCAPI_ERR_MEDIA_NOT_OPEN;
					}
					else if (conn->IsRetryInProgress())
					{
						ret = TCAPI_ERR_COMM_RETRY_IN_PROGRESS;
					}
					else if (conn->IsRetryTimedOut())
					{
						ret = TCAPI_ERR_COMM_TIMEOUT;
					}

					if (ret == TCAPI_ERR_NONE)
					{
						client->Start();
						conn->RestartProcessing();
					}
				}
				if (ret == TCAPI_ERR_NONE)
				{
					pCmdrsp->response = eRspOK;
				}
				else
				{
					pCmdrsp->response = eRspError;
					pCmdrsp->error = ret;
				}
				m_Server->SendResponse(pCmdrsp);
			}
			break;
		case eCmdStop:
			{
	TCDEBUGOPEN();
				TCDEBUGLOGS(" eCmdStop\n");
	TCDEBUGCLOSE();

				long ret = TCAPI_ERR_NONE;
				DWORD id = pCmdrsp->clientId;
				bool restart = false;
				CClient* client = FindClient(id);
				if (client)
				{
					// get connection
					CConnectionImpl* conn = (CConnectionImpl*)client->GetConnection();
					if (conn->IsConnected())
					{
						if (!conn->IsRetryTimedOut() && !conn->IsRetryInProgress())
						{
							if (conn->IsProcessingContinuing())
							{
								restart = true;
								conn->PauseProcessing();
							}
						}
						client->Stop();
						if (!conn->IsRetryTimedOut() && !conn->IsRetryInProgress())
						{
							if (restart)
								conn->RestartProcessing();
						}

					}
				}
				if (ret == TCAPI_ERR_NONE)
				{
					pCmdrsp->response = eRspOK;
				}
				else
				{
					pCmdrsp->response = eRspError;
					pCmdrsp->error = ret;
				}
				m_Server->SendResponse(pCmdrsp);
	TCDEBUGOPEN();
				TCDEBUGLOGS(" eCmdStop done\n");
	TCDEBUGCLOSE();
			}
			break;
		case eCmdSendMessage:
			{
	TCDEBUGOPEN();
				TCDEBUGLOGS(" eCmdSendMessage\n");
	TCDEBUGCLOSE();

				long ret = TCAPI_ERR_NONE;
				DWORD osErr = 0;
				DWORD id = pCmdrsp->clientId;
				CClient* client = FindClient(id);
				if (client)
				{
					CConnectionImpl* conn = (CConnectionImpl*)client->GetConnection();
					if (conn->IsDisconnected())
					{
						ret = TCAPI_ERR_MEDIA_NOT_OPEN;
					}
					else if (conn->IsRetryInProgress())
					{
						ret = TCAPI_ERR_COMM_RETRY_IN_PROGRESS;
					}
					else if (conn->IsRetryTimedOut())
					{
						ret = TCAPI_ERR_COMM_TIMEOUT;
					}

					if (ret == TCAPI_ERR_NONE)
					{
	TCDEBUGOPEN();
				TCDEBUGLOGS(" eCmdSendMessage DoSendMessage\n");
	TCDEBUGCLOSE();
						ret = conn->DoSendMessage(pCmdrsp->encodeOption, pCmdrsp->protocolVersion, pCmdrsp->useMyId, pCmdrsp->myId, pMsg->length, pMsg->message);
//						ret = conn->DoSendMessage(pMsg->length, pMsg->message);
						if (ret != TCAPI_ERR_NONE)
							osErr = conn->m_OsError;
	TCDEBUGOPEN();
				TCDEBUGLOGS(" eCmdSendMessage DoSendMessage done\n");
	TCDEBUGCLOSE();
					}
				}
				if (ret == TCAPI_ERR_NONE)
				{
//	TCDEBUGOPEN();
//				TCDEBUGLOGS(" eCmdSendMessage OK\n");
//	TCDEBUGCLOSE();
					pCmdrsp->response = eRspOK;
				}
				else
				{
//	TCDEBUGOPEN();
//				TCDEBUGLOGS(" eCmdSendMessage ERROR\n");
//	TCDEBUGCLOSE();
					pCmdrsp->response = eRspError;
					pCmdrsp->error = ret;
					if (osErr > 0)
						pCmdrsp->osError = osErr;
					else
						pCmdrsp->osError = 0;
				}
				m_Server->SendResponse(pCmdrsp);
			}
			break;
		case eCmdExit:
			{
				TCDEBUGOPEN();
				TCDEBUGLOGS(" eCmdExit\n");
				TCDEBUGCLOSE();
				DoShutdown();
				done = true;
			}
			break;
		case eCmdGetNumberVersions:
			{
				TCDEBUGOPEN();
				TCDEBUGLOGS(" eCmdGetNumberVersions\n");
				TCDEBUGCLOSE();

				long ret = TCAPI_ERR_NONE;
				long numberVersions = 1; // 1 for server
				DWORD id = pCmdrsp->clientId;
				CClient* client = FindClient(id);
				if (client)
				{
					CConnectionImpl* conn = (CConnectionImpl*)client->GetConnection();
					if (conn->IsConnected())
					{
						if (conn->HasVersion())
							numberVersions++;
					}
				}
				if (ret == TCAPI_ERR_NONE)
				{
					pCmdrsp->response = eRspOK;
					pCmdrsp->number = numberVersions;
				}
				else
				{
					pCmdrsp->response = eRspError;
					pCmdrsp->error = ret;
				}
				m_Server->SendResponse(pCmdrsp);
			}
			break;
		case eCmdGetVersion:
			{
				TCDEBUGOPEN();
				TCDEBUGLOGS(" eCmdGetVersion\n");
				TCDEBUGCLOSE();
				// index = 1 ==> TCFServer version
				// index = 2 ==> Connection version if it exists
				long ret = TCAPI_ERR_NONE;
				long index = pCmdrsp->index;
				pCmdrsp->response = eRspOK;
				pCmdrsp->version[0] = NULL;
				if (index == 1)
				{
					pCmdrsp->response = eRspOK;
					strncpy(pCmdrsp->version, m_Version, MAX_VERSION_STRING);
				}
				else if (index == 2)
				{
					DWORD id = pCmdrsp->clientId;
					CClient* client = FindClient(id);
					if (client)
					{
						CConnectionImpl* conn = (CConnectionImpl*)client->GetConnection();
						if (conn->IsConnected())
						{
							pCmdrsp->response = eRspOK;
							if (conn->HasVersion())
							{
								conn->GetVersion(pCmdrsp->version);
							}
						}
						else
						{
							pCmdrsp->response = eRspError;
							pCmdrsp->error = TCAPI_ERR_MEDIA_NOT_OPEN;
						}
					}
				}
				m_Server->SendResponse(pCmdrsp);
			}
			break;
		case eCmdGetClientStatus:
			{
				TCDEBUGOPEN();
				TCDEBUGLOGS(" eCmdGetClientStatus\n");
				TCDEBUGCLOSE();
				long ret = TCAPI_ERR_NONE;
				DWORD id = pCmdrsp->clientId;
				CClient* client = FindClient(id);
				pCmdrsp->response = eRspOK;
				pCmdrsp->clientStatus = eUnknownClient;
				if (client)
				{
					if (client->IsStarted())
					{
						pCmdrsp->clientStatus = eStarted;
					}
					else
					{
						pCmdrsp->clientStatus = eStopped;
					}
				}
				m_Server->SendResponse(pCmdrsp);
			}
			break;
		default:
			{
				TCDEBUGOPEN();
				TCDEBUGLOGA1(" unknown command = %d\n", command);
				TCDEBUGCLOSE();
				pCmdrsp->response = eRspOK;//eRspError;
				pCmdrsp->error = TCAPI_ERR_FEATURE_NOT_IMPLEMENTED;
				m_Server->SendResponse(pCmdrsp);
			}
			break;
		}
	}

	pCmdrsp->response = eRspExitted;
	m_Server->SendResponse(&cmdrsp);

	if (pMsg)
		delete pMsg;

	TCDEBUGOPEN();
	TCDEBUGLOGS("CServerManager::CommandThread exitting\n");
	TCDEBUGCLOSE();
}

CConnection* CServerManager::FindConnection(pConnectData pConData)
{
	TCDEBUGLOGS("CServerManager::FindConnection1\n");
	CConnection* connection = NULL;

	if (m_ConnectionList->size() != 0)
	{
		ConnectionList::iterator pConn;
		for (pConn = m_ConnectionList->begin(); pConn != m_ConnectionList->end(); pConn++)
		{
			if ((*pConn)->IsEqual(pConData))
			{
				connection = *pConn;
				break;
			}
		}
	}

	TCDEBUGLOGA1("CServerManager::FindConnection1 connection=%x\n", connection);
	return connection;

}
CConnection* CServerManager::FindConnection(long index)
{
	TCDEBUGLOGS("CServerManager::FindConnection2\n");
	CConnection* connection = NULL;

	if (m_ConnectionList->size() >= index)
	{
		connection = m_ConnectionList->at(index);
	}

	TCDEBUGLOGA1("CServerManager::FindConnection2 connection=%x\n", connection);
	return connection;
}
CClient* CServerManager::FindClient(DWORD id)
{
	CClient* found = NULL;

	if (m_ClientList->size() != 0)
	{
		ClientList::iterator iter;
		for (iter = m_ClientList->begin(); iter != m_ClientList->end(); iter++)
		{
			if ((*iter)->GetClientId() == id)
			{
				found = *iter;
				break;
			}
		}
	}
	return found;
}

BOOL CServerManager::RemoveClient(CClient* client)
{
	BOOL found = FALSE;

	if (m_ClientList->size() != 0)
	{
		ClientList::iterator iter;
		for (iter = m_ClientList->begin(); iter != m_ClientList->end(); iter++)
		{
			if ((*iter)->GetClientId() == client->GetClientId())
			{
				m_ClientList->erase(iter);
				found = TRUE;
				break;
			}
		}
	}
	return found;
}

BOOL CServerManager::RemoveConnection(CConnection* conn)
{
	BOOL found = FALSE;

	if (m_ConnectionList->size() != 0)
	{
		ConnectionList::iterator iter;
		for (iter = m_ConnectionList->begin(); iter != m_ConnectionList->end(); iter++)
		{
			if ((*iter)->GetConnectionId() == conn->GetConnectionId())
			{
				m_ConnectionList->erase(iter);
				found = TRUE;
				break;
			}
		}
	}
	return found;
}

void CServerManager::DoShutdown()
{
	// for each connection
	// stop processing on that connection
	// disconnect that connection
	if (m_ConnectionList->size() != 0)
	{
		ConnectionList::iterator pConn;
		for (pConn = m_ConnectionList->begin(); pConn != m_ConnectionList->end(); pConn++)
		{
			(*pConn)->ExitProcessing();
			(*pConn)->DoDisconnect();
			delete (*pConn);
		}
	}
}
void CServerManager::RegisterAllComms()
{
	TCDEBUGLOGS("CServerManager::RegisterAllComms\n");

	m_CommList->clear();
	if (m_ExeLocation && (m_ExeLocation[0] != '\0'))
	{
		char* searchPath = new char[MAX_EXEPATHNAME];
		char* loadPath = new char[MAX_EXEPATHNAME];
		strncpy(searchPath, m_ExeLocation, MAX_EXEPATHNAME);
		sprintf(searchPath, "%s%c*", searchPath, PATH_DELIMITER);

		TCDEBUGLOGA1("CServerManager::RegisterAllComms searchPath=%s\n", searchPath);
		
		WIN32_FIND_DATA fileData;

		HANDLE sh = ::FindFirstFile(searchPath, &fileData);
		if (sh != INVALID_HANDLE_VALUE)
		{
			BOOL done = FALSE;
			while (!done)
			{
				TCDEBUGLOGA1("CServerManager::RegisterAllComms file=%s\n", fileData.cFileName);
				if (strncmp(fileData.cFileName, COMMDLL_BASENAME, strlen(COMMDLL_BASENAME)) == 0)
				{
					sprintf(loadPath, "%s%c%s", m_ExeLocation, PATH_DELIMITER, fileData.cFileName);
					TCDEBUGLOGA1("CServerManager::RegisterAllComms loadPath=%s\n", loadPath);

					HINSTANCE hLib = ::LoadLibrary(loadPath);
					TCDEBUGLOGA1("CServerManager::RegisterAllComms hLib=%x\n", hLib);

					if (hLib)
					{
						TCDEBUGLOGS("CServerManager::RegisterAllComms - library loaded\n");

						COMMREGISTER lpFn = (COMMREGISTER)::GetProcAddress(hLib, COMMREGISTER_FNNAME);
						TCDEBUGLOGA1("CServerManager::RegisterAllComms lpFn=%x\n", lpFn);

						if (lpFn)
						{
							TCDEBUGLOGS("CServerManager::RegisterAllComms - function found\n");
							const char* pType = lpFn();
							if (pType)
							{
								TCDEBUGLOGA1("CServerManager::RegisterAllComms pType=%s\n", pType);
								CCommRegistryItem* pComm = new CCommRegistryItem();
								strcpy(pComm->m_CommLibrary, loadPath);
								strcpy(pComm->m_CommType, pType);
								m_CommList->push_back(pComm);
							}
						}
						::FreeLibrary(hLib);
					}
				}
				BOOL fNext = ::FindNextFile(sh, &fileData);
				if (fNext == FALSE)
					done = TRUE;
			}
			::FindClose(sh);
		}
		delete[] searchPath;
		delete[] loadPath;
	}
	TCDEBUGLOGS("CServerManager::RegisterAllComms\n");
}

void CServerManager::RegisterAllProtocols()
{
	TCDEBUGLOGS("CServerManager::RegisterAllProtocols\n");

	m_ProtocolList->clear();
	if (m_ExeLocation && (m_ExeLocation[0] != '\0'))
	{
		char* searchPath = new char[MAX_EXEPATHNAME];
		char* loadPath = new char[MAX_EXEPATHNAME];
		strncpy(searchPath, m_ExeLocation, MAX_EXEPATHNAME);
		sprintf(searchPath, "%s%c*", searchPath, PATH_DELIMITER);

		TCDEBUGLOGA1("CServerManager::RegisterAllProtocols searchPath=%s\n", searchPath);
		
		WIN32_FIND_DATA fileData;

		HANDLE sh = ::FindFirstFile(searchPath, &fileData);
		BOOL done = FALSE;

		if (sh != INVALID_HANDLE_VALUE)
		{
			BOOL done = FALSE;
			while (!done)
			{
				TCDEBUGLOGA1("CServerManager::RegisterAllProtocols file=%s\n", fileData.cFileName);
				if (strncmp(fileData.cFileName, PROTOCOLDLL_BASENAME, strlen(PROTOCOLDLL_BASENAME)) == 0)
				{
					sprintf(loadPath, "%s%c%s", m_ExeLocation, PATH_DELIMITER, fileData.cFileName);
					TCDEBUGLOGA1("CServerManager::RegisterAllProtocols loadPath=%s\n", loadPath);

					HINSTANCE hLib = ::LoadLibrary(loadPath);
					TCDEBUGLOGA1("CServerManager::RegisterAllProtocols hLib=%x\n", hLib);

					if (hLib)
					{
						TCDEBUGLOGS("CServerManager::RegisterAllProtocols - library loaded\n");

						PROTOCOLREGISTER lpFn = (PROTOCOLREGISTER)::GetProcAddress(hLib, PROTOCOLREGISTER_FNNAME);
						TCDEBUGLOGA1("CServerManager::RegisterAllProtocols lpFn=%x\n", lpFn);

						if (lpFn)
						{
							TCDEBUGLOGS("CServerManager::RegisterAllProtocols - function found\n");
							const char* pType = lpFn();
							if (pType)
							{
								TCDEBUGLOGA1("CServerManager::RegisterAllProtocols pType=%s\n", pType);
								CProtocolRegistryItem* pProt = new CProtocolRegistryItem();
								strcpy(pProt->m_ProtocolLibrary, loadPath);
								strcpy(pProt->m_ProtocolType, pType);
								m_ProtocolList->push_back(pProt);
							}
						}
						::FreeLibrary(hLib);
					}
				}
				BOOL fNext = ::FindNextFile(sh, &fileData);
				if (fNext == FALSE)
					done = TRUE;
			}
			::FindClose(sh);
		}
		delete[] searchPath;
		delete[] loadPath;
	}
	TCDEBUGLOGS("CServerManager::RegisterAllProtocols\n");
}

void CServerManager::UnRegisterAllComms()
{
}

void CServerManager::UnRegisterAllProtocols()
{
}

const char* CServerManager::FindProtocolPath(char* protocolType)
{
	char* path = NULL;

	TCDEBUGLOGS("CServerManager::FindProtocolPath\n");
	if (m_ProtocolList->size() != 0)
	{
		ProtocolRegistry::iterator iter;
		for (iter = m_ProtocolList->begin(); iter != m_ProtocolList->end(); iter++)
		{
			if (strcmp((*iter)->m_ProtocolType, protocolType) == 0)
			{
				path = (*iter)->m_ProtocolLibrary;
				break;
			}
		}
	}

	TCDEBUGLOGA1("CServerManager::FindProtocolPath path=%s\n", path);
	return path;
}

const char* CServerManager::FindCommPath(char* commType)
{
	char* path = NULL;

	TCDEBUGLOGS("CServerManager::FindCommPath\n");
	if (m_CommList->size() != 0)
	{
		CommRegistry::iterator iter;
		for (iter = m_CommList->begin(); iter != m_CommList->end(); iter++)
		{
			if (strcmp((*iter)->m_CommType, commType) == 0)
			{
				path = (*iter)->m_CommLibrary;
				break;
			}
		}
	}

	TCDEBUGLOGA1("CServerManager::FindCommPath path=%s\n", path);
	return path;
}

