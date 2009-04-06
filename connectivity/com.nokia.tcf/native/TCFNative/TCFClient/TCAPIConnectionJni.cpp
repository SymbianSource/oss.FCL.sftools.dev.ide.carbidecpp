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
#include "TCAPIConnectionJni.h"
#include "TCConstants.h"
#include "TCErrorConstants.h"
#include "ClientManager.h"
#include "ServerClient.h"
#include "TCDebugLog.h"
#include "InputStream.h"
#include "ErrorMonitorData.h"
#include <list>
#include <vector>

extern CClientManager* gManager;

#ifdef _DEBUG
extern BOOL gDoLogging;
char TCDebugMsg[100];
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

#ifdef _DEBUG
FILE* fLog1 = NULL;
FILE* fLog2 = NULL;
static void OpenLogFile1(char* filename);
static void CloseLogFile1();
static void OpenLogFile2(char* filename);
static void CloseLogFile2();
#define OPENLOGf1(f) OpenLogFile1(f)
#define CLOSELOG1() CloseLogFile1()
#define OPENLOGf2(f) OpenLogFile2(f)
#define CLOSELOG2() CloseLogFile2()
#else
#define OPENLOGf1(f)
#define CLOSELOG1()
#endif

static void ConvertRealSerialSettingsToHost(char* pBaud, char* pDataBits, char* pParity, char* pStopBits, char* pFlowControl, pRealSerialConnectData pData);
static void ConvertRealSerialSettingsToServer(const char* pBaud, const char* pDataBits, const char* pParity, const char* pStopBits, const char* pFlowControl, pRealSerialConnectData pData);
static const char* GetErrorText(unsigned long error);
/*
 * Class:     com_nokia_tcf_impl_TCAPIConnection
 * Method:    nativeConnect
 * Signature: (Ljava/lang/String;[J[Ljava/lang/String;[JLjava/lang/String;[J)J
 */
JNIEXPORT jlong JNICALL Java_com_nokia_tcf_impl_TCAPIConnection_nativeConnect
  (JNIEnv *env, jobject pThis, jstring inType, jlongArray inOptions, jobjectArray inSettings, jlongArray inMessageOptions, jstring inFilePath, jlongArray outClientId)
 {
 	// inOptions are connection options
 	// inMessageOptions are client's message options
	// inFilePath will be null if message destination is not DESTINATION_CLIENTFILE 
	long ret = TCAPI_ERR_NONE;
	unsigned long osError = 0;
	long clientId;

	TCDEBUGOPEN();

	TCDEBUGLOGS("nativeConnect\n");

	gManager->m_Server->WaitforServerPipeAccess();

	{
		jboolean isCopy = false;
		// options per connection
		jlong* pOptions = env->GetLongArrayElements(inOptions, &isCopy);
		DWORD retryInterval = (DWORD)pOptions[0];
		DWORD retryTimeout = (DWORD)pOptions[1];



		env->ReleaseLongArrayElements(inOptions, pOptions, 0);

		pOptions = env->GetLongArrayElements(inMessageOptions, &isCopy);

		// options per client
		long unWrapFormat = pOptions[0];
		long ostVersion = pOptions[1];
		env->ReleaseLongArrayElements(inMessageOptions, pOptions, 0);


		TCDEBUGLOGA2(" retryInterval=%d, retryTimeout=%d\n", retryInterval, retryTimeout);

		TCDEBUGLOGA3(" unWrapFormat=%d, ostVersion=%d destination=%s\n", unWrapFormat, ostVersion, ((inFilePath == NULL) ? "stream" : "file"));

		char* pType = (char*)env->GetStringUTFChars(inType, &isCopy);
		TCDEBUGLOGA1("  pType = %s\n", pType);

		if (strcmp(pType, "musti") == 0 || strcmp(pType, "platsim") == 0) 
		{
			jstring addString = (jstring)env->GetObjectArrayElement(inSettings, 0);
			jstring portString = (jstring)env->GetObjectArrayElement(inSettings, 1);
			jstring chanString = (jstring)env->GetObjectArrayElement(inSettings, 2);
			jstring decodeString = (jstring)env->GetObjectArrayElement(inSettings, 3);

			const char* pAddress = env->GetStringUTFChars(addString, NULL);
			const char* pPort = env->GetStringUTFChars(portString, NULL);
			const char* pChan = env->GetStringUTFChars(chanString, NULL);
			const char* pDecode = env->GetStringUTFChars(decodeString, NULL);

			TCDEBUGLOGA2(" TCP: ipAddress=%s, ipPort=%s\n", pAddress, pPort);
			TCDEBUGLOGA2(" TCP: channel=%s, decode=%s\n", pChan, pDecode);

			ServerCommandData cmd;
			cmd.command = eCmdConnect;
			strncpy(cmd.connectSettings.connectType, pType, MAX_CONNECTION_TYPE);
			strncpy(cmd.connectSettings.tcpSettings.ipAddress, pAddress, MAX_IPADDRESS_SIZE);
			strncpy(cmd.connectSettings.tcpSettings.ipPort, pPort, MAX_PORT_SIZE);

			cmd.connectSettings.retryInterval = retryInterval;
			cmd.connectSettings.retryTimeout = retryTimeout;
			strncpy(cmd.connectSettings.decodeFormat, pDecode, MAX_DECODE_FORMAT);

			if (pChan != NULL)
			{
				if (strcmp(pChan, "1") == 0)
				{
					cmd.connectSettings.traceBoxChannel = 1;
				}
				else
				{
					cmd.connectSettings.traceBoxChannel = 2;
				}
			}
			else
			{
				cmd.connectSettings.traceBoxChannel = 2;
			}


			cmd.clientOptions.unWrapFormat = unWrapFormat;
			cmd.clientOptions.ostVersion = ostVersion;

			gManager->m_Server->SendCommand(&cmd);
			gManager->m_Server->GetResponse(&cmd);

			if (cmd.response == eRspError)
			{
				TCDEBUGLOGA1("  from server: ret = %d\n", cmd.error);
				ret = cmd.error;
				osError = cmd.osError;
			}
			else
			{
				TCDEBUGLOGA1("  from server: id = %d\n", cmd.clientId);
				clientId = cmd.clientId;

				gManager->WaitForErrorMonitorListAccess();
				TCDEBUGLOGS(" TODO: create error monitor storage if server connected\n");
				CErrorMonitor* monitor = new CErrorMonitor(clientId);
				if (monitor != NULL)
				{
					monitor->CreateData();
					gManager->AddErrorMonitor(monitor);
				}

				gManager->ReleaseErrorMonitorListAccess();

				TCDEBUGLOGA1(" clientId=%d\n", clientId);
				jlong jClientId = clientId;
				env->SetLongArrayRegion(outClientId, 0, 1, &jClientId);

			}
			env->ReleaseStringUTFChars(addString, pAddress);
			env->ReleaseStringUTFChars(portString, pPort);
			env->ReleaseStringUTFChars(chanString, pChan);
			env->ReleaseStringUTFChars(decodeString, pDecode);
		}
		else if (strcmp(pType, "tcp") == 0)
		{
			jstring addString = (jstring)env->GetObjectArrayElement(inSettings, 0);
			jstring portString = (jstring)env->GetObjectArrayElement(inSettings, 1);
			jstring decodeString = (jstring)env->GetObjectArrayElement(inSettings, 2);

			const char* pAddress = env->GetStringUTFChars(addString, NULL);
			const char* pPort = env->GetStringUTFChars(portString, NULL);
			const char* pDecode = env->GetStringUTFChars(decodeString, NULL);

			TCDEBUGLOGA3(" TCP: ipAddress=%s, ipPort=%s decode=%s\n", pAddress, pPort, pDecode);

			ServerCommandData cmd;
			cmd.command = eCmdConnect;
			strncpy(cmd.connectSettings.connectType, pType, MAX_CONNECTION_TYPE);
			strncpy(cmd.connectSettings.tcpSettings.ipAddress, pAddress, MAX_IPADDRESS_SIZE);
			strncpy(cmd.connectSettings.tcpSettings.ipPort, pPort, MAX_PORT_SIZE);

			cmd.connectSettings.retryInterval = retryInterval;
			cmd.connectSettings.retryTimeout = retryTimeout;
			strncpy(cmd.connectSettings.decodeFormat, pDecode, MAX_DECODE_FORMAT);
			cmd.clientOptions.unWrapFormat = unWrapFormat;
			cmd.clientOptions.ostVersion = ostVersion;

			gManager->m_Server->SendCommand(&cmd);
			gManager->m_Server->GetResponse(&cmd);

			if (cmd.response == eRspError)
			{
				TCDEBUGLOGA1("  from server: ret = %d\n", cmd.error);
				ret = cmd.error;
				osError = cmd.osError;
			}
			else
			{
				TCDEBUGLOGA1("  from server: id = %d\n", cmd.clientId);
				clientId = cmd.clientId;

				gManager->WaitForErrorMonitorListAccess();
				TCDEBUGLOGS(" TODO: create error monitor storage if server connected\n");
				CErrorMonitor* monitor = new CErrorMonitor(clientId);
				if (monitor != NULL)
				{
					monitor->CreateData();
					gManager->AddErrorMonitor(monitor);
				}

				gManager->ReleaseErrorMonitorListAccess();

				TCDEBUGLOGA1(" clientId=%d\n", clientId);
				jlong jClientId = clientId;
				env->SetLongArrayRegion(outClientId, 0, 1, &jClientId);

			}
			env->ReleaseStringUTFChars(addString, pAddress);
			env->ReleaseStringUTFChars(portString, pPort);
			env->ReleaseStringUTFChars(decodeString, pDecode);
		}
		else if (strcmp(pType, "virtualserial") == 0)
		{
			jstring portString = (jstring)env->GetObjectArrayElement(inSettings, 0);
			const char* pPort = env->GetStringUTFChars(portString, NULL);
			jstring decodeString = (jstring)env->GetObjectArrayElement(inSettings, 1);
			const char* pDecode = env->GetStringUTFChars(decodeString, NULL);

			TCDEBUGLOGA2(" vserial: pPort=%s decode=%s\n", pPort, pDecode);

			ServerCommandData cmd;
			cmd.command = eCmdConnect;
			strncpy(cmd.connectSettings.connectType, pType, MAX_CONNECTION_TYPE); 
			strncpy(cmd.connectSettings.virtualSerialSettings.comPort, pPort, MAX_COMPORT_SIZE);

			cmd.connectSettings.retryInterval = retryInterval;
			cmd.connectSettings.retryTimeout = retryTimeout;
			strncpy(cmd.connectSettings.decodeFormat, pDecode, MAX_DECODE_FORMAT);
			cmd.clientOptions.unWrapFormat = unWrapFormat;
			cmd.clientOptions.ostVersion = ostVersion;

			gManager->m_Server->SendCommand(&cmd);
			gManager->m_Server->GetResponse(&cmd);

			if (cmd.response == eRspError)
			{
				TCDEBUGLOGA1("  from server: ret = %d\n", cmd.error);
				ret = cmd.error;
				osError = cmd.osError;
			}
			else
			{
				TCDEBUGLOGA1("  from server: id = %d\n", cmd.clientId);
				clientId = cmd.clientId;

				gManager->WaitForErrorMonitorListAccess();
				CErrorMonitor* monitor = new CErrorMonitor(clientId);
				if (monitor != NULL)
				{
					monitor->CreateData();
					gManager->AddErrorMonitor(monitor);
				}

				gManager->ReleaseErrorMonitorListAccess();

				TCDEBUGLOGA1(" clientId=%d\n", clientId);
				jlong jClientId = clientId;
				env->SetLongArrayRegion(outClientId, 0, 1, &jClientId);
			}
			env->ReleaseStringUTFChars(portString, pPort);
			env->ReleaseStringUTFChars(decodeString, pDecode);
		}
		else if (strcmp(pType, "serial") == 0)
		{
			jstring portString = (jstring)env->GetObjectArrayElement(inSettings, 0);
			const char* pPort = env->GetStringUTFChars(portString, NULL);

			jstring baudString = (jstring)env->GetObjectArrayElement(inSettings, 1);
			const char* pBaud = env->GetStringUTFChars(baudString, NULL);
			
			jstring dataBitsString = (jstring)env->GetObjectArrayElement(inSettings, 2);
			const char* pDataBits = env->GetStringUTFChars(dataBitsString, NULL);
			
			jstring parityString = (jstring)env->GetObjectArrayElement(inSettings, 3);
			const char* pParity = env->GetStringUTFChars(parityString, NULL);
			
			jstring stopBitsString = (jstring)env->GetObjectArrayElement(inSettings, 4);
			const char* pStopBits = env->GetStringUTFChars(stopBitsString, NULL);
			
			jstring flowControlString = (jstring)env->GetObjectArrayElement(inSettings, 5);
			const char* pFlowControl = env->GetStringUTFChars(flowControlString, NULL);

			jstring decodeString = (jstring)env->GetObjectArrayElement(inSettings, 6);
			const char* pDecode = env->GetStringUTFChars(decodeString, NULL);

			TCDEBUGLOGA3(" real serial: pPort=%s pBaud=%s pDataBits=%s\n", pPort, pBaud, pDataBits);

			ServerCommandData cmd;
			cmd.command = eCmdConnect;
			strncpy(cmd.connectSettings.connectType, pType, MAX_CONNECTION_TYPE); 
			strncpy(cmd.connectSettings.realSerialSettings.comPort, pPort, MAX_COMPORT_SIZE);
			ConvertRealSerialSettingsToServer(pBaud, pDataBits, pParity, pStopBits, pFlowControl, &cmd.connectSettings.realSerialSettings);

			cmd.connectSettings.retryInterval = retryInterval;
			cmd.connectSettings.retryTimeout = retryTimeout;
			strncpy(cmd.connectSettings.decodeFormat, pDecode, MAX_DECODE_FORMAT);
			cmd.clientOptions.unWrapFormat = unWrapFormat;
			cmd.clientOptions.ostVersion = ostVersion;

			gManager->m_Server->SendCommand(&cmd);
			gManager->m_Server->GetResponse(&cmd);

			if (cmd.response == eRspError)
			{
				TCDEBUGLOGA1("  from server: ret = %d\n", cmd.error);
				ret = cmd.error;
				osError = cmd.osError;
			}
			else
			{
				TCDEBUGLOGA1("  from server: id = %d\n", cmd.clientId);
				clientId = cmd.clientId;

				gManager->WaitForErrorMonitorListAccess();
				CErrorMonitor* monitor = new CErrorMonitor(clientId);
				if (monitor != NULL)
				{
					monitor->CreateData();
					gManager->AddErrorMonitor(monitor);
				}

				gManager->ReleaseErrorMonitorListAccess();

				TCDEBUGLOGA1(" clientId=%d\n", clientId);
				jlong jClientId = clientId;
				env->SetLongArrayRegion(outClientId, 0, 1, &jClientId);
			}
			env->ReleaseStringUTFChars(portString, pPort);
			env->ReleaseStringUTFChars(baudString, pBaud);
			env->ReleaseStringUTFChars(dataBitsString, pDataBits);
			env->ReleaseStringUTFChars(parityString, pParity);
			env->ReleaseStringUTFChars(stopBitsString, pStopBits);
			env->ReleaseStringUTFChars(flowControlString, pFlowControl);
			env->ReleaseStringUTFChars(decodeString, pDecode);
		}
		else
		{
			TCDEBUGLOGS(" TCAPI_ERR_MEDIA_NOT_SUPPORTED\n");
			ret = TCAPI_ERR_MEDIA_NOT_SUPPORTED;
		}

		env->ReleaseStringUTFChars(inType, pType);

		
		// handle message destinations
		//  input stream is done by java on open input stream not here
		//  message file is done here
		if (ret == TCAPI_ERR_NONE)
		{
			long destination = DESTINATION_INPUTSTREAM;
			char* pMessageFile = NULL;
			if (inFilePath != NULL)
			{
				destination = DESTINATION_CLIENTFILE;
				jboolean isCopy=FALSE;
				pMessageFile = (char*)env->GetStringUTFChars(inFilePath, &isCopy);

				// send to TCFServer
				ServerCommandData cmd;
				cmd.command = eCmdOpenMessageFile;
				cmd.clientId = clientId;
				cmd.destinationOptions.destination = eDestinationFile;
				strncpy(cmd.destinationOptions.destinationFile, pMessageFile, MAX_FILEPATH);
				gManager->m_Server->SendCommand(&cmd);
				gManager->m_Server->GetResponse(&cmd);

				env->ReleaseStringUTFChars(inFilePath, pMessageFile);
			}
		}
	}

	gManager->m_Server->ReleaseServerPipeAccess();

	TCDEBUGLOGA3("nativeConnect return ret=%d, osError=%d : %s\n", ret, osError, GetErrorText(osError));

	TCDEBUGCLOSE();

	if (ret != TCAPI_ERR_NONE && osError > 0)
	{
		jclass clazz = env->FindClass("Ljava/lang/Exception;");
		env->ThrowNew(clazz, GetErrorText(osError));
	}

	return ret;
}

/*
 * Class:     com_nokia_tcf_impl_TCAPIConnection
 * Method:    nativeDisconnect
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_nokia_tcf_impl_TCAPIConnection_nativeDisconnect
  (JNIEnv *env, jobject pThis, jlong inClientId)
{
	long ret = TCAPI_ERR_NONE;

	TCDEBUGOPEN();
	TCDEBUGLOGS("nativeDisconnect\n");

	if (gManager->m_Server == NULL)
	{
		TCDEBUGLOGS(" TCAPI_ERR_INVALID_HANDLE - gServer NULL\n");
		ret = TCAPI_ERR_INVALID_HANDLE;
	}
	else if (inClientId <= 0)
	{
		TCDEBUGLOGS(" TCAPI_ERR_INVALID_HANDLE - inClientId <= 0\n");
		ret = TCAPI_ERR_INVALID_HANDLE;
	}
	else
	{
		gManager->m_Server->WaitforServerPipeAccess();

		BOOL found = FALSE;
		long id = inClientId;

		TCDEBUGLOGA1(" look for clientId=%d\n", id);

		TCDEBUGLOGS(" TODO: tell server to disconnect this client\n");
		ServerCommandData cmdrsp;
		pServerCommandData pCmdrsp = &cmdrsp;
		pCmdrsp->command = eCmdDisconnect;
		pCmdrsp->clientId = id;

		gManager->m_Server->SendCommand(pCmdrsp);
		gManager->m_Server->GetResponse(pCmdrsp);
		if (pCmdrsp->response == eRspError)
		{
			ret = pCmdrsp->error;
		}

		{
			gManager->WaitForErrorMonitorListAccess();
			TCDEBUGLOGS(" TODO: destroy error monitor for this client\n");
			CErrorMonitor *monitor = gManager->FindErrorMonitor(id);
			if (monitor != NULL)
			{
				gManager->RemoveErrorMonitor(monitor);
				delete monitor;
			}
			gManager->ReleaseErrorMonitorListAccess();
		}
		gManager->m_Server->ReleaseServerPipeAccess();
	}

	TCDEBUGLOGA1(" return ret=%d\n", ret);
	TCDEBUGCLOSE();
	return ret;
}
/*
 * Class:     com_nokia_tcf_impl_TCAPIConnection
 * Method:    nativeGetNumberConnections
 * Signature: ([J)J
 */
JNIEXPORT jlong JNICALL Java_com_nokia_tcf_impl_TCAPIConnection_nativeGetNumberConnections
  (JNIEnv *env, jobject pThis, jlongArray outNumber)
{
	long ret = TCAPI_ERR_NONE;
	long number = 0;

	TCDEBUGOPEN();
	gManager->m_Server->WaitforServerPipeAccess();

	TCDEBUGLOGS("nativeGetNumberConnections\n");
	ServerCommandData cmdrsp;
	pServerCommandData pCmdrsp = &cmdrsp;
	pCmdrsp->command = eCmdGetNumberConnections;

	BOOL sent = gManager->m_Server->SendCommand(pCmdrsp);

	BOOL recd = gManager->m_Server->GetResponse(pCmdrsp);
	if (pCmdrsp->response == eRspError)
	{
		ret = pCmdrsp->error;
	}
	else
	{
		number = pCmdrsp->numConnections;

		jlong jNumber = number;
		env->SetLongArrayRegion(outNumber, 0, 1, &jNumber);

		TCDEBUGLOGA1(" number=%d\n", number);
	}

	gManager->m_Server->ReleaseServerPipeAccess();

	TCDEBUGLOGA1(" return ret=%d\n", ret);
	TCDEBUGCLOSE();
	return ret;
}

/*
 * Class:     com_nokia_tcf_impl_TCAPIConnection
 * Method:    nativeGetTypeOfConnection
 * Signature: (J[Ljava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_com_nokia_tcf_impl_TCAPIConnection_nativeGetTypeOfConnection
  (JNIEnv *env, jobject pThis, jlong inIndex, jobjectArray outType)
{
	long ret = TCAPI_ERR_NONE;

	TCDEBUGOPEN();
	gManager->m_Server->WaitforServerPipeAccess();

	TCDEBUGLOGS("nativeGetTypeOfConnection\n");
	ServerCommandData cmdrsp;
	pServerCommandData pCmdrsp = &cmdrsp;
	pCmdrsp->command = eCmdGetConnectionType;
	pCmdrsp->index = inIndex;

	BOOL sent = gManager->m_Server->SendCommand(pCmdrsp);

	BOOL recd = gManager->m_Server->GetResponse(pCmdrsp);
	if (pCmdrsp->response == eRspError)
	{
		ret = pCmdrsp->error;
	}
	else
	{
		env->SetObjectArrayElement(outType, 0, env->NewStringUTF(pCmdrsp->connectSettings.connectType));
	}


	gManager->m_Server->ReleaseServerPipeAccess();

	TCDEBUGLOGA1(" return ret=%d\n", ret);
	TCDEBUGCLOSE();

	return ret;
}


/*
 * Class:     com_nokia_tcf_impl_TCAPIConnection
 * Method:    nativeGetConnectionSettings
 * Signature: (J[Ljava/lang/String;[J[Ljava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_com_nokia_tcf_impl_TCAPIConnection_nativeGetConnectionSettings
  (JNIEnv *env, jobject pThis, jlong inIndex, jobjectArray outType, jlongArray outOptions, jobjectArray outSettings)
{
	long ret = TCAPI_ERR_FEATURE_NOT_IMPLEMENTED;

	TCDEBUGOPEN();
	gManager->m_Server->WaitforServerPipeAccess();

	TCDEBUGLOGS("nativeGetConnectionSettings\n");
	ServerCommandData cmdrsp;
	pServerCommandData pCmdrsp = &cmdrsp;
	pCmdrsp->command = eCmdGetConnection;
	pCmdrsp->index = inIndex;

	BOOL sent = gManager->m_Server->SendCommand(pCmdrsp);

	BOOL recd = gManager->m_Server->GetResponse(pCmdrsp);
	if (pCmdrsp->response == eRspError)
	{
		ret = pCmdrsp->error;
	}
	else
	{
		env->SetObjectArrayElement(outType, 0, env->NewStringUTF(pCmdrsp->connectSettings.connectType));
		if (strcmp(pCmdrsp->connectSettings.connectType, "musti") == 0 || strcmp(pCmdrsp->connectSettings.connectType, "platsim") == 0)
		{
			env->SetObjectArrayElement(outSettings, 0, env->NewStringUTF(pCmdrsp->connectSettings.tcpSettings.ipAddress));
			env->SetObjectArrayElement(outSettings, 1, env->NewStringUTF(pCmdrsp->connectSettings.tcpSettings.ipPort));
			char *pChan = NULL;
			if (pCmdrsp->connectSettings.traceBoxChannel == 1)
				pChan = "1";
			else
				pChan = "2";
			env->SetObjectArrayElement(outSettings, 2, env->NewStringUTF(pChan));
			env->SetObjectArrayElement(outSettings, 3, env->NewStringUTF(pCmdrsp->connectSettings.decodeFormat));
		}
		else if (strcmp(pCmdrsp->connectSettings.connectType, "tcp") == 0)
		{
			env->SetObjectArrayElement(outSettings, 0, env->NewStringUTF(pCmdrsp->connectSettings.tcpSettings.ipAddress));
			env->SetObjectArrayElement(outSettings, 1, env->NewStringUTF(pCmdrsp->connectSettings.tcpSettings.ipPort));
			env->SetObjectArrayElement(outSettings, 2, env->NewStringUTF(pCmdrsp->connectSettings.decodeFormat));
		}
		else if (strcmp(pCmdrsp->connectSettings.connectType, "virtualserial") == 0)
		{
			env->SetObjectArrayElement(outSettings, 0, env->NewStringUTF(pCmdrsp->connectSettings.virtualSerialSettings.comPort));
			env->SetObjectArrayElement(outSettings, 1, env->NewStringUTF(pCmdrsp->connectSettings.decodeFormat));
		}
		else if (strcmp(pCmdrsp->connectSettings.connectType, "serial") == 0)
		{
			env->SetObjectArrayElement(outSettings, 0, env->NewStringUTF(pCmdrsp->connectSettings.realSerialSettings.comPort));

			char baud[10], databits[10], parity[10], stopbits[10], flowcontrol[15];
			ConvertRealSerialSettingsToHost(baud, databits, parity, stopbits, flowcontrol, &pCmdrsp->connectSettings.realSerialSettings);
			env->SetObjectArrayElement(outSettings, 1, env->NewStringUTF(baud));
			env->SetObjectArrayElement(outSettings, 2, env->NewStringUTF(databits));
			env->SetObjectArrayElement(outSettings, 3, env->NewStringUTF(parity));
			env->SetObjectArrayElement(outSettings, 4, env->NewStringUTF(stopbits));
			env->SetObjectArrayElement(outSettings, 5, env->NewStringUTF(flowcontrol));
			env->SetObjectArrayElement(outSettings, 6, env->NewStringUTF(pCmdrsp->connectSettings.decodeFormat));
		}
		else if (strcmp(pCmdrsp->connectSettings.connectType, "usb") == 0)
		{
			env->SetObjectArrayElement(outSettings, 0, env->NewStringUTF(pCmdrsp->connectSettings.usbSettings.device));
		}
		jlong jRetryInterval = pCmdrsp->connectSettings.retryInterval;
		env->SetLongArrayRegion(outOptions, 0, 1, &jRetryInterval);
		jlong jRetryTimeout = pCmdrsp->connectSettings.retryTimeout;
		env->SetLongArrayRegion(outOptions, 1, 1, &jRetryTimeout);
	}

	gManager->m_Server->ReleaseServerPipeAccess();

	TCDEBUGLOGA1(" return ret=%d\n", ret);
	TCDEBUGCLOSE();
	return ret;
}

/*
 * Class:     com_nokia_tcf_impl_TCAPIConnection
 * Method:    nativePollError
 * Signature: (J[I[Z[J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_nokia_tcf_impl_TCAPIConnection_nativePollError
  (JNIEnv *env, jobject pThis, jlong inClientId, jintArray outErrorCode, 
	jbooleanArray outHasOSErrorCode, jlongArray outOSErrorCode)
{
	jboolean foundError = false;
	long id = inClientId;
	LONG tcfError = TCAPI_ERR_NONE;

	TCDEBUGOPEN();
	TCDEBUGLOGS("nativePollError\n");
//	TCDEBUGLOGA1(" inClientId=%d\n", inClientId);

	gManager->WaitForErrorMonitorListAccess();

	if (gManager->ErrorMonitorListSize() > 0)
	{
//		TCDEBUGLOGA1(" size of gManager->ErrorMonitorListSize=%d\n", gManager->ErrorMonitorListSize());

		CErrorMonitor* errorMonitor = gManager->FindErrorMonitor(id);
//		TCDEBUGLOGA1(" errorMonitor = %x\n", errorMonitor);

		if (errorMonitor != NULL)
		{
//			TCDEBUGLOGS(" found client\n");

			BOOL osErrorUsed = FALSE;
			DWORD osError = 0;
			BOOL found = errorMonitor->GetError(&tcfError, &osErrorUsed, &osError);
			if (found)
			{
//				TCDEBUGLOGA1("nativePollError error for client=%d\n", inClientId);
//				TCDEBUGLOGA3(" found tcfError=%d, osErrorUsed=%d, osError=%d\n",
//					tcfError, osErrorUsed, osError);

				foundError = true;
				jint jval = tcfError;
				env->SetIntArrayRegion(outErrorCode, 0, 1, &jval);
				jboolean jos = osErrorUsed;
				env->SetBooleanArrayRegion(outHasOSErrorCode, 0, 1, &jos);
				jlong jlval = osError;
				env->SetLongArrayRegion(outOSErrorCode, 0, 1, &jlval);

//				TCDEBUGOPEN();
//				TCDEBUGLOGS("nativePollError found error\n");
//				TCDEBUGCLOSE();
			}
		}
	}
	gManager->ReleaseErrorMonitorListAccess();

	TCDEBUGLOGA2(" return foundError=%d tcfError=%d\n", foundError, tcfError);

	TCDEBUGCLOSE();
	return foundError;
}

/*
 * Class:     com_nokia_tcf_impl_TCAPIConnection
 * Method:    nativeGetNumberVersionEntities
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_nokia_tcf_impl_TCAPIConnection_nativeGetNumberVersionEntities
  (JNIEnv *env, jobject pThis, jlong inClientId)
{
	long number = 0;
	long id = inClientId;

	TCDEBUGOPEN();
	TCDEBUGLOGS("nativeGetNumberVersionEntities\n");
	TCDEBUGLOGA1(" inClientId=%d\n", inClientId);

	gManager->m_Server->WaitforServerPipeAccess();

	// eCmdGetNumberVersions
	ServerCommandData cmdrsp;
	pServerCommandData pCmdrsp = &cmdrsp;
	pCmdrsp->command = eCmdGetNumberVersions;
	pCmdrsp->clientId = id;

	BOOL sent = gManager->m_Server->SendCommand(pCmdrsp);

	BOOL recd = gManager->m_Server->GetResponse(pCmdrsp);
	if (pCmdrsp->response == eRspError)
	{
	}
	else
	{
		number = pCmdrsp->number + 1; // + 1 for DLL version
	}

	gManager->m_Server->ReleaseServerPipeAccess();

	TCDEBUGLOGA1(" numberVersions = %d\n", number);
	TCDEBUGCLOSE();
	return number;
}

/*
 * Class:     com_nokia_tcf_impl_TCAPIConnection
 * Method:    nativeGetVersion
 * Signature: (JJ[Ljava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_com_nokia_tcf_impl_TCAPIConnection_nativeGetVersion
  (JNIEnv *env, jobject pThis, jlong inClientId, jlong inNumToGet, jobjectArray outVersion)
{
	long ret = TCAPI_ERR_NONE;
	long id = inClientId;

	TCDEBUGOPEN();
	TCDEBUGLOGS("nativeGetVersion\n");
	TCDEBUGLOGA1(" inClientId=%d\n", inClientId);

	gManager->m_Server->WaitforServerPipeAccess();

	// eCmdGetVersion
	ServerCommandData cmdrsp;
	pServerCommandData pCmdrsp = &cmdrsp;
	pCmdrsp->command = eCmdGetVersion;
	pCmdrsp->clientId = id;


	// index = 0 ==> TCFClient.dll version
	env->SetObjectArrayElement(outVersion, 0, env->NewStringUTF(gManager->m_Version));
	long numberGot = 1;

	if (inNumToGet > 0)
	{
		// index = 1 ==> TCFServer.exe version
		pCmdrsp->command = eCmdGetVersion;
		pCmdrsp->clientId = id;
		pCmdrsp->index = 1;
		BOOL sent = gManager->m_Server->SendCommand(pCmdrsp);
		BOOL recd = gManager->m_Server->GetResponse(pCmdrsp);
		if (pCmdrsp->response == eRspOK)
		{
			env->SetObjectArrayElement(outVersion, 1, env->NewStringUTF(pCmdrsp->version));
			numberGot++;

			if (inNumToGet > 1)
			{
				// index = 2 ==> connection version
				pCmdrsp->command = eCmdGetVersion;
				pCmdrsp->clientId = id;
				pCmdrsp->index = 2;
				BOOL sent = gManager->m_Server->SendCommand(pCmdrsp);
				BOOL recd = gManager->m_Server->GetResponse(pCmdrsp);
				if (pCmdrsp->response == eRspOK)
				{
					env->SetObjectArrayElement(outVersion, 2, env->NewStringUTF(pCmdrsp->version));
					numberGot++;
				}
			}
		}
	}

	gManager->m_Server->ReleaseServerPipeAccess();

	TCDEBUGLOGA1(" return numberGot=%d\n", numberGot);
	TCDEBUGCLOSE();
	return numberGot;
}

/*
 * Class:     com_nokia_tcf_impl_TCAPIConnection
 * Method:    nativePollInputStream
 * Signature: (J[J)J
 */
JNIEXPORT jlong JNICALL Java_com_nokia_tcf_impl_TCAPIConnection_nativePollInputStream
  (JNIEnv *env, jobject pThis, jlong inClientId, jlongArray outNumberMessages)
{
	long ret = TCAPI_ERR_NONE;
	long number = 0;
	long id = inClientId;

	TCDEBUGOPEN();
	TCDEBUGLOGS("nativePollInputStream\n");
	TCDEBUGLOGA1(" inClientId=%d\n", inClientId);

	TCDEBUGLOGS(" TODO: get number from client's input stream\n");

	gManager->WaitForStreamListAccess();

	CInputStream* inputStream = gManager->FindInputStream(id);

	if (inputStream != NULL)
	{
		TCDEBUGLOGA1(" -- found id inputStream=%x\n", inputStream);
		number = inputStream->GetNumberMessages();
	}

	gManager->ReleaseStreamListAccess();
	jlong jNumber = number;
	env->SetLongArrayRegion(outNumberMessages, 0, 1, &jNumber);

	TCDEBUGLOGA2(" numberMessages=%d ret=%d\n", number, ret);

	TCDEBUGCLOSE();
	return ret;
}
/*
 * Class:     com_nokia_tcf_impl_TCAPIConnection
 * Method:    nativePollInputStream2
 * Signature: (JJ[J[J)J
 */
JNIEXPORT jlong JNICALL Java_com_nokia_tcf_impl_TCAPIConnection_nativePollInputStream2
  (JNIEnv *env, jobject pThis, jlong inClientId, jlong inNumberMessagesToPeek, jlongArray outNumberMessagesPeeked, jlongArray outNumberBytesPeeked)
{
	long ret = TCAPI_ERR_NONE;
	long id = inClientId;
	long numberToGet = inNumberMessagesToPeek;
	long numberStored = 0;
	DWORD numberBytes = 0;

	TCDEBUGOPEN();
	TCDEBUGLOGS("nativePollInputStream2\n");
	TCDEBUGLOGA1(" inClientId=%d\n", inClientId);

	gManager->WaitForStreamListAccess();

	CInputStream* inputStream = gManager->FindInputStream(id);

	if (inputStream != NULL)
	{
		TCDEBUGLOGA1(" -- found id inputStream=%x\n", inputStream);
		numberStored = inputStream->GetNumberMessages();

		if ((numberToGet == 0) || (numberToGet > numberStored))
		{
			numberToGet = numberStored;
		}
		if (numberToGet > 0)
		{
			inputStream->GetTotalMessageSize(numberToGet, numberBytes);
		}
	}

	gManager->ReleaseStreamListAccess();

	jlong jNumber = numberToGet;
	env->SetLongArrayRegion(outNumberMessagesPeeked, 0, 1, &jNumber);
	jNumber = numberBytes;
	env->SetLongArrayRegion(outNumberBytesPeeked, 0, 1, &jNumber);

	TCDEBUGLOGA3(" numberMessages=%d numberBytes=%d ret=%d\n", numberToGet, numberBytes, ret);

	TCDEBUGCLOSE();
	return ret;
}


/*
 * Class:     com_nokia_tcf_impl_TCAPIConnection
 * Method:    nativeGetInputStreamMessageBytes
 * Signature: (JJ[J)J
 */
JNIEXPORT jlong JNICALL Java_com_nokia_tcf_impl_TCAPIConnection_nativeGetInputStreamMessageBytes
  (JNIEnv *env, jobject pThis, jlong inClientId, jlong inNumberMessages, jlongArray outMessageSizes)
{
	long ret = TCAPI_ERR_NONE;
	long id = inClientId;
	long numberToGet = inNumberMessages;

	TCDEBUGOPEN();
	TCDEBUGLOGS("nativeGetInputStreamMessageBytes\n");
	TCDEBUGLOGA1(" inClientId=%d\n", inClientId);

	gManager->WaitForStreamListAccess();

	CInputStream* inputStream = gManager->FindInputStream(id);

	if (inputStream != NULL)
	{
		TCDEBUGLOGA1(" -- found id inputStream=%x\n", inputStream);
		DWORD* pSizes = new DWORD[numberToGet];
		inputStream->GetMessageSizes(numberToGet, pSizes);
		for (int i = 0; i < numberToGet; i++)
		{
			TCDEBUGLOGA2(" -- message size[%d] = %d\n", i, pSizes[i]);
			jlong jsize = pSizes[i];
			env->SetLongArrayRegion(outMessageSizes, i, 1, &jsize);
		}
		delete[] pSizes;
	}

	gManager->ReleaseStreamListAccess();

	TCDEBUGLOGA1("nativeGetInputStreamMessageBytes ret=%d\n", ret);

	TCDEBUGCLOSE();

	return ret;
}
/*
 * Class:     com_nokia_tcf_impl_TCAPIConnection
 * Method:    nativeReadInputStream
 * Signature: (JJ[J[JJ[B)J
 */
JNIEXPORT jlong JNICALL Java_com_nokia_tcf_impl_TCAPIConnection_nativeReadInputStream
  (JNIEnv *env, jobject pThis, jlong inClientId, jlong inNumberMessages, jlongArray outNumberMessages, jlongArray outNumberBytesRead, jlong inNumberMaxBytes, jbyteArray outMessageData)
{
	long ret = TCAPI_ERR_NONE;
	long id = inClientId;
	long numberToGet = inNumberMessages;
	long numberMaxBytes = inNumberMaxBytes;

	TCDEBUGOPEN();
	TCDEBUGLOGS("nativeReadInputStream start\n");
	TCDEBUGLOGA1(" inClientId=%d\n", inClientId);

	gManager->WaitForStreamListAccess();

	CInputStream* inputStream = gManager->FindInputStream(id);
	if (inputStream != NULL)
	{
		long numberBytesRead = 0;
		long numberMessagesRead = 0;
#if (0)
		DWORD mSize = 0;
		DWORD outOffset = 0;
		BYTE* pData = new BYTE[64*1024];
		for (int i = 0; i < numberToGet; i++)
		{
			TCDEBUGLOGS("nativeReadInputStream 1\n");
			mSize = inputStream->GetNextMessageSize();
			if ((numberBytesRead + mSize) > numberMaxBytes)
				break;
			if (mSize != 0)
			{
				mSize = inputStream->GetNextMessage(mSize, pData);
				TCDEBUGLOGA3("outOffset = %d mSize = %d pData[0] = %02.2x\n", outOffset, mSize, pData[0]);
				jbyte* pByte = (jbyte*)pData;
				env->SetByteArrayRegion(outMessageData, outOffset, mSize, pByte);
				outOffset += mSize;
				numberBytesRead += mSize;
				numberMessagesRead++;

				if ((i % 500) == 0)
					Sleep(1);
			}
		}
		delete [] pData;
#endif
		numberMessagesRead = inputStream->GetMessages(env, numberToGet, numberMaxBytes, numberBytesRead, numberMessagesRead, outMessageData);
		jlong jMsgs = numberMessagesRead;
		env->SetLongArrayRegion(outNumberMessages, 0, 1, &jMsgs);
		jlong jNum = numberBytesRead;
		env->SetLongArrayRegion(outNumberBytesRead, 0, 1, &jNum);

		TCDEBUGLOGS("nativeReadInputStream 2\n");
	}

	gManager->ReleaseStreamListAccess();

	TCDEBUGLOGA1(" return ret=%d\n", ret);
	TCDEBUGCLOSE();
	return ret;
}

/*
 * Class:     com_nokia_tcf_impl_TCAPIConnection
 * Method:    nativeOpenInputStream
 * Signature: (JLjava/lang/String;JZ)J
 */
JNIEXPORT jlong JNICALL Java_com_nokia_tcf_impl_TCAPIConnection_nativeOpenInputStream
  (JNIEnv *env, jobject pThis, jlong inClientId, jstring inFileBaseName, jlong inStreamSize, jboolean inOverflowToFile)
{
	long ret = TCAPI_ERR_NONE;

	TCDEBUGOPEN();
	TCDEBUGLOGS("nativeOpenInputStream\n");
	TCDEBUGLOGA1(" inClientId=%d\n", inClientId);
	TCDEBUGLOGA1(" inFileBaseName=%x\n", inFileBaseName);
	TCDEBUGLOGA1(" inStreamSize=%d\n", inStreamSize);
	TCDEBUGLOGA1(" inOverflowToFile=%d\n", inOverflowToFile);

	gManager->m_Server->WaitforServerPipeAccess();
	gManager->WaitForStreamListAccess();

	char* pFileName = NULL;

	if (inFileBaseName != NULL)
	{
		TCDEBUGLOGS("nativeOpenInputStream GetStringUTFChars on inFileBaseName\n");
		jboolean isCopy=FALSE;
		pFileName = (char*)env->GetStringUTFChars(inFileBaseName, &isCopy);
		TCDEBUGLOGS("nativeOpenInputStream return GetStringUTFChars\n");
		TCDEBUGLOGA1(" pFileName=%x\n", pFileName);
//		if (pFileName != NULL)
//		{
//			TCDEBUGLOGA1(" pFileName=%s\n", pFileName);
//		}
	}
	else
	{
		TCDEBUGLOGS("nativeOpenInputStream inFileBaseName == NULL\n");
	}


	TCDEBUGLOGS("nativeOpenInputStream continue after GetStringUTFChars\n");
	DWORD streamSize = inStreamSize;
	TCDEBUGLOGA1(" streamSize=%d\n", streamSize);

	BOOL overflowOption = FALSE;
	TCDEBUGLOGA1(" overflowOption=%d\n", overflowOption);

	long id = inClientId;

	TCDEBUGLOGA1(" gManager->InputStreamListSize=%d\n", gManager->InputStreamListSize());

	// create stream
	CInputStream* stream = new CInputStream(pFileName, streamSize, overflowOption, id);
	stream->CreateStream();
	gManager->AddInputStream(stream);

	ServerCommandData cmdrsp; pServerCommandData pCmdrsp = &cmdrsp;
	pCmdrsp->command = eCmdOpenStream;
	pCmdrsp->clientId = id;
	pCmdrsp->destinationOptions.destination = eDestinationInputStream;
	pCmdrsp->destinationOptions.streamSize = streamSize;
	pCmdrsp->destinationOptions.overFlowToFile = FALSE;
	if (pFileName != NULL)
	{
		strncpy(pCmdrsp->destinationOptions.destinationFile, pFileName, MAX_FILEPATH);
	}
	else
	{
		pCmdrsp->destinationOptions.destinationFile[0] = NULL;
	}
	if (inFileBaseName != NULL)
	{
		TCDEBUGLOGS("nativeOpenInputStream ReleaseStringUTFChars on inFileBaseName\n");
		env->ReleaseStringUTFChars(inFileBaseName, pFileName);
	}

	gManager->m_Server->SendCommand(pCmdrsp);
	gManager->m_Server->GetResponse(pCmdrsp);

	if (pCmdrsp->response == eRspError)
	{
		ret = pCmdrsp->error;
	}

	TCDEBUGLOGA1(" gManager->InputStreamListSize=%d\n", gManager->InputStreamListSize());

	gManager->ReleaseStreamListAccess();

	gManager->m_Server->ReleaseServerPipeAccess();

	TCDEBUGLOGA1(" return ret=%d\n", ret);
	TCDEBUGCLOSE();

	return ret;
}

/*
 * Class:     com_nokia_tcf_impl_TCAPIConnection
 * Method:    nativeCloseInputStream
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_nokia_tcf_impl_TCAPIConnection_nativeCloseInputStream
  (JNIEnv *env, jobject pThis, jlong inClientId)
{
	long ret = TCAPI_ERR_NONE;

	TCDEBUGOPEN();
	TCDEBUGLOGS("nativeCloseInputStream\n");
	TCDEBUGLOGA1(" inClientId=%d\n", inClientId);
	
	gManager->m_Server->WaitforServerPipeAccess();
	gManager->WaitForStreamListAccess();

	long id = inClientId;

	TCDEBUGLOGA1(" gManager->InputStreamListSize=%d\n", gManager->InputStreamListSize());

	TCDEBUGLOGS(" TODO: tell server to close this stream\n");

	ServerCommandData cmdrsp; pServerCommandData pCmdrsp = &cmdrsp;
	pCmdrsp->command = eCmdCloseStream;
	pCmdrsp->clientId = id;

	gManager->m_Server->SendCommand(pCmdrsp);
	gManager->m_Server->GetResponse(pCmdrsp);

	if (pCmdrsp->response == eRspError)
	{
		ret = pCmdrsp->error;
	}

	CInputStream* inputStream = gManager->FindInputStream(id);
	if (inputStream != NULL)
	{
		TCDEBUGLOGA1(" -- found id inputStream=%x\n", inputStream);
		gManager->RemoveInputStream(inputStream);
		delete inputStream;
	}

	TCDEBUGLOGA1(" gManager->InputStreamListSize=%d\n", gManager->InputStreamListSize());
	gManager->ReleaseStreamListAccess();

	gManager->m_Server->ReleaseServerPipeAccess();

	TCDEBUGLOGA1(" return ret=%d\n", ret);
	TCDEBUGCLOSE();
	return ret;
}

/*
 * Class:     com_nokia_tcf_impl_TCAPIConnection
 * Method:    nativeSetMessageIds
 * Signature: (J[B)J
 */
JNIEXPORT jlong JNICALL Java_com_nokia_tcf_impl_TCAPIConnection_nativeSetMessageIds
  (JNIEnv *env, jobject pThis, jlong inClientId, jbyteArray inIds)
{
	long ret = TCAPI_ERR_NONE;

	TCDEBUGOPEN();
	TCDEBUGLOGS("nativeSetMessageIds\n");
	TCDEBUGLOGA1(" inClientId=%d\n", inClientId);

	gManager->m_Server->WaitforServerPipeAccess();

	ServerCommandData cmdrsp; pServerCommandData pCmdrsp = &cmdrsp;

	pCmdrsp->command = eCmdSetMessageIds;
	pCmdrsp->clientId = inClientId;
	jsize numberIds = env->GetArrayLength(inIds);
	pCmdrsp->number = numberIds;
	jboolean isCopy = FALSE;
	jbyte* bytes = env->GetByteArrayElements(inIds, &isCopy);
	for (int i = 0; i < numberIds; i++)
	{
		pCmdrsp->messageIds[i] = bytes[i];
		TCDEBUGLOGA1(" -- msgId = 0x%02.2X\n",  pCmdrsp->messageIds[i]);
	}
	env->ReleaseByteArrayElements(inIds, bytes, 0);
	gManager->m_Server->SendCommand(pCmdrsp);
	gManager->m_Server->GetResponse(pCmdrsp);

	if (pCmdrsp->response == eRspError)
	{
		ret = pCmdrsp->error;
	}

	gManager->m_Server->ReleaseServerPipeAccess();

	TCDEBUGLOGA1(" return ret=%d\n", ret);
	TCDEBUGCLOSE();
	return ret;
}

/*
 * Class:     com_nokia_tcf_impl_TCAPIConnection
 * Method:    nativeClearFile
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_nokia_tcf_impl_TCAPIConnection_nativeClearFile
  (JNIEnv *env, jobject pThis, jlong inClientId)
{
	long ret = TCAPI_ERR_NONE;
	gManager->m_Server->WaitforServerPipeAccess();

	long id = inClientId;

	ServerCommandData cmdrsp;
	pServerCommandData pCmdrsp = &cmdrsp;
	pCmdrsp->command = eCmdClearMessageFile;
	pCmdrsp->clientId = id;

	gManager->m_Server->SendCommand(pCmdrsp);
	gManager->m_Server->GetResponse(pCmdrsp);
	if (pCmdrsp->response == eRspError)
	{
		ret = pCmdrsp->error;
	}
	gManager->m_Server->ReleaseServerPipeAccess();

	return ret;
}

/*
 * Class:     com_nokia_tcf_impl_TCAPIConnection
 * Method:    nativeSendMessage
 * Signature: (J[J[Ljava/lang/String;[B)J
 */
JNIEXPORT jlong JNICALL Java_com_nokia_tcf_impl_TCAPIConnection_nativeSendMessage
  (JNIEnv *env, jobject pThis, jlong inClientId, jlongArray inFormattingOptions, jobjectArray inSettings, jbyteArray inMessage)
{
	long ret = TCAPI_ERR_NONE;
	unsigned long osError = 0;

	TCDEBUGOPEN();
	TCDEBUGLOGS("nativeSendMessage\n");
	TCDEBUGLOGA1(" inClientId=%d\n", inClientId);

	gManager->m_Server->WaitforServerPipeAccess();

	jboolean isCopy = false;
	// formatting options
	jlong* pOptions = env->GetLongArrayElements(inFormattingOptions, &isCopy);
//	long encodeFormat = (long)pOptions[0];	// not used
	long encodeOption = (long)pOptions[1];	// formatting option for protocol
	long protocolVersion = (long)pOptions[2];	// OST version byte to use if protocol is OST
	BYTE myId = (BYTE)(pOptions[3] & 0xff);	// my message ID to use of adding protocol
	BOOL useMyId = (pOptions[4] == 1) ? TRUE : FALSE;	// use my ID or not
	env->ReleaseLongArrayElements(inFormattingOptions, pOptions, 0);

	jstring decodeString = (jstring)env->GetObjectArrayElement(inSettings, 0);
	const char* pDecode = env->GetStringUTFChars(decodeString, NULL); // not used
	env->ReleaseStringUTFChars(decodeString, pDecode);

	jsize numberBytes = 0;
	if (inMessage != NULL)
		numberBytes = env->GetArrayLength(inMessage);
	long inLength = numberBytes;		// this can be null

	jbyte* inData = NULL;
	if (inLength > 0)
		inData = env->GetByteArrayElements(inMessage, &isCopy);

	ServerCommandData cmdrsp; pServerCommandData pCmdrsp = &cmdrsp;

	pCmdrsp->command = eCmdSendMessage;
	pCmdrsp->clientId = inClientId;
	pCmdrsp->encodeOption = encodeOption;
	pCmdrsp->useMyId = useMyId;
	pCmdrsp->myId = myId;
	pCmdrsp->protocolVersion = protocolVersion & 0xff;

	// send message to server
	pCmdrsp->osError = 0;
	gManager->m_Server->SendCommand(pCmdrsp, inLength, (BYTE*)inData);

	TCDEBUGLOGS(" nativeSendMessage  GetResponse\n");

	// get response from server
	gManager->m_Server->GetResponse(pCmdrsp);

	if (pCmdrsp->response == eRspError)
	{
		ret = pCmdrsp->error;
		osError = pCmdrsp->osError;
	}
	if (inData != NULL)
		env->ReleaseByteArrayElements(inMessage, inData, 0);

	gManager->m_Server->ReleaseServerPipeAccess();

	TCDEBUGLOGA3("nativeSendMessage return ret=%d, osError=%d : %s\n", ret, osError, GetErrorText(osError));
	TCDEBUGCLOSE();

	if (ret == TCAPI_ERR_COMM_ERROR && osError > 0)
	{
		jclass clazz = env->FindClass("Ljava/lang/Exception;");
		env->ThrowNew(clazz, GetErrorText(osError));
	}
	return ret;
}

/*
 * Class:     com_nokia_tcf_impl_TCAPIConnection
 * Method:    nativeStart
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_nokia_tcf_impl_TCAPIConnection_nativeStart
  (JNIEnv *env, jobject pThis, jlong inClientId)
{
	long ret = TCAPI_ERR_NONE;

	TCDEBUGOPEN();
	TCDEBUGLOGS("nativeStart\n");
	TCDEBUGLOGA1(" inClientId=%d\n", inClientId);

	gManager->m_Server->WaitforServerPipeAccess();

	TCDEBUGLOGS(" TODO: tell server to start this client\n");
	ServerCommandData cmdrsp; pServerCommandData pCmdrsp = &cmdrsp;

	pCmdrsp->command = eCmdStart;
	pCmdrsp->clientId = inClientId;

	gManager->m_Server->SendCommand(pCmdrsp);

	gManager->m_Server->GetResponse(pCmdrsp);

	if (pCmdrsp->response == eRspError)
	{
		ret = pCmdrsp->error;
	}
	gManager->m_Server->ReleaseServerPipeAccess();

	TCDEBUGLOGA1(" return ret=%d\n", ret);
	TCDEBUGCLOSE();
	return ret;
}

/*
 * Class:     com_nokia_tcf_impl_TCAPIConnection
 * Method:    nativeStop
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_nokia_tcf_impl_TCAPIConnection_nativeStop
  (JNIEnv *env, jobject pThis, jlong inClientId)
{
	long ret = TCAPI_ERR_NONE;

	TCDEBUGOPEN();
	TCDEBUGLOGS("nativeStop\n");
	TCDEBUGLOGA1(" inClientId=%d\n", inClientId);

	gManager->m_Server->WaitforServerPipeAccess();

	TCDEBUGLOGS(" TODO: tell server to stop this client\n");
	ServerCommandData cmdrsp; pServerCommandData pCmdrsp = &cmdrsp;

	pCmdrsp->command = eCmdStop;
	pCmdrsp->clientId = inClientId;

	gManager->m_Server->SendCommand(pCmdrsp);

	gManager->m_Server->GetResponse(pCmdrsp);

	if (pCmdrsp->response == eRspError)
	{
		ret = pCmdrsp->error;
	}

	gManager->m_Server->ReleaseServerPipeAccess();

	TCDEBUGLOGA1(" return ret=%d\n", ret);
	TCDEBUGCLOSE();
	return ret;
}

/*
 * Class:     com_nokia_tcf_impl_TCAPIConnection
 * Method:    nativeTestConnection
 * Signature: (I[J[Ljava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_com_nokia_tcf_impl_TCAPIConnection_nativeTestConnection__I_3J_3Ljava_lang_String_2
  (JNIEnv *env, jobject pThis, jint inType, jlongArray inOptions, jobjectArray inSettings)
{
	long ret = TCAPI_ERR_FEATURE_NOT_IMPLEMENTED;

	TCDEBUGOPEN();
	TCDEBUGLOGS("nativeTestConnection\n");

	gManager->m_Server->WaitforServerPipeAccess();

	TCDEBUGLOGS(" TODO: ask server to test this connection\n");

	gManager->m_Server->ReleaseServerPipeAccess();

	TCDEBUGLOGA1(" return ret=%d\n", ret);
	TCDEBUGCLOSE();

	return ret;
}

/*
 * Class:     com_nokia_tcf_impl_TCAPIConnection
 * Method:    nativeTestConnection
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_nokia_tcf_impl_TCAPIConnection_nativeTestConnection__J
  (JNIEnv *env, jobject pThis, jlong inClientId)
{
	long ret = TCAPI_ERR_FEATURE_NOT_IMPLEMENTED;

	TCDEBUGOPEN();
	TCDEBUGLOGS("nativeTestConnection\n");
	TCDEBUGLOGA1(" inClientId=%d\n", inClientId);

	gManager->m_Server->WaitforServerPipeAccess();

	TCDEBUGLOGS(" TODO: ask server to test this client's connection\n");

	gManager->m_Server->ReleaseServerPipeAccess();

	TCDEBUGLOGA1(" return ret=%d\n", ret);
	TCDEBUGCLOSE();

	return ret;
}
/*
 * Class:     com_nokia_tcf_impl_TCAPIConnection
 * Method:    nativeStartServer
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_nokia_tcf_impl_TCAPIConnection_nativeStartServer
  (JNIEnv *env, jobject pThis)
{
	long ret = TCAPI_ERR_NONE;

	TCDEBUGOPEN();
	TCDEBUGLOGS("nativeStartServer\n");

	gManager->m_Server->WaitforServerPipeAccess();

	ret = gManager->StartServer();

	gManager->m_Server->ReleaseServerPipeAccess();

	TCDEBUGLOGA1(" return ret=%d\n", ret);
	TCDEBUGCLOSE();

	return ret;
}

/*
 * Class:     com_nokia_tcf_impl_TCAPIConnection
 * Method:    nativeStopServer
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_nokia_tcf_impl_TCAPIConnection_nativeStopServer
  (JNIEnv *env, jobject pThis)
{
	long ret = TCAPI_ERR_NONE;

	TCDEBUGOPEN();
	TCDEBUGLOGS("nativeStopServer\n");

	gManager->m_Server->WaitforServerPipeAccess();

	ret = gManager->StopServer();

	gManager->m_Server->ReleaseServerPipeAccess();

	TCDEBUGLOGA1(" return ret=%d\n", ret);
	TCDEBUGCLOSE();

	return ret;
}

void ConvertRealSerialSettingsToServer(const char* pBaud, const char* pDataBits, const char* pParity, const char* pStopBits, const char* pFlowControl, pRealSerialConnectData pData)
{
	// no error checking - all error checking is done at the Java level
	pData->baudRate = atol(pBaud);
	pData->dataBits = atol(pDataBits);

	pData->parity = eParityNone;
	if (strcmp(pParity, "odd") == 0)
	{
		pData->parity = eParityOdd;
	}
	else if (strcmp(pParity, "even") == 0)
	{
		pData->parity = eParityEven;
	}

	pData->stopBits = eStopBits1;
	if (strcmp(pStopBits, "1.5") == 0)
	{
		pData->stopBits = eStopBits15;
	}
	else if (strcmp(pStopBits, "2") == 0)
	{
		pData->stopBits = eStopBits2;
	}

	pData->flowControl = eFlowControlNone;
	if (strcmp(pFlowControl, "software") == 0)
	{
		pData->flowControl = eFlowControlSW;
	} 
	else if (strcmp(pFlowControl, "hardware") == 0)
	{
		pData->flowControl = eFlowControlHW;
	}
}
void ConvertRealSerialSettingsToHost(char* pBaud, char* pDataBits, char* pParity, char* pStopBits, char* pFlowControl, pRealSerialConnectData pData)
{
	sprintf(pBaud, "%ld", pData->baudRate);
	sprintf(pDataBits, "%ld", pData->dataBits);

	switch (pData->parity)
	{
	default:
	case eParityNone:
		strcpy(pParity, "none");
		break;
	case eParityEven:
		strcpy(pParity, "even");
		break;
	case eParityOdd:
		strcpy(pParity, "odd");
		break;
	}

	switch (pData->stopBits)
	{
	default:
	case eStopBits1:
		strcpy(pStopBits, "1");
		break;
	case eStopBits15:
		strcpy(pStopBits, "1.5");
		break;
	case eStopBits2:
		strcpy(pStopBits, "2");
		break;
	}
	
	switch (pData->flowControl)
	{
	default:
	case eFlowControlNone:
		strcpy(pFlowControl, "none");
		break;
	case eFlowControlHW:
		strcpy(pFlowControl, "hardware");
		break;
	case eFlowControlSW:
		strcpy(pFlowControl, "software");
		break;
	}
}

#ifdef _DEBUG
void OpenLogFile1(char* filename)
{
	if (fLog1 == NULL)
		fLog1 = _fsopen(filename, "at", _SH_DENYNO);
}

void CloseLogFile1()
{
	if (fLog1 != NULL)
	{
		fclose(fLog1);
		fLog1 = NULL;
	}
}
void OpenLogFile2(char* filename)
{
	if (fLog2 == NULL)
		fLog2 = _fsopen(filename, "at", _SH_DENYNO);
}

void CloseLogFile2()
{
	if (fLog2 != NULL)
	{
		fclose(fLog2);
		fLog2 = NULL;
	}
}
void LogTime(FILE* f)
{
	SYSTEMTIME sTime;
	GetLocalTime(&sTime);
	if (f)
		fprintf(f, "%02.2d%02.2d-%02.2d:%02.2d:%02.2d.%03.3d: ", sTime.wDay, sTime.wMonth, sTime.wHour, sTime.wMinute, sTime.wSecond, sTime.wMilliseconds);
}
#endif

static const char* GetErrorText(unsigned long error)
{
	if (error == ERROR_FILE_NOT_FOUND)
		return "Could not open the device\n";

	else if (error == ERROR_ACCESS_DENIED)
		return "The device is currently in use\n";

	static char msg[256];
	FormatMessage(
		FORMAT_MESSAGE_FROM_SYSTEM | FORMAT_MESSAGE_IGNORE_INSERTS,
		NULL,
		error,
		MAKELANGID(LANG_NEUTRAL, SUBLANG_DEFAULT), // Default language
		(LPTSTR) &msg,
		sizeof(msg) - 1,
		NULL);

	return msg;
}
