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
// GetTRKVersion.cpp : Defines the entry point for the DLL application.
//

#include "stdafx.h"
#include "GetTRKVersion.h"
#include "com_nokia_carbide_trk_support_service_TRKConnectedService.h"


BOOL APIENTRY DllMain( HANDLE hModule, 
                       DWORD  ul_reason_for_call, 
                       LPVOID lpReserved
					 )
{
    switch (ul_reason_for_call)
	{
		case DLL_PROCESS_ATTACH:
		case DLL_THREAD_ATTACH:
		case DLL_THREAD_DETACH:
		case DLL_PROCESS_DETACH:
			break;
    }
    return TRUE;
}

#define MIN(x, y) (((x) < (y)) ? (x) : (y))

static const DWORD kNoPingError = -1;
static const DWORD kNoVersionError = -2;

const DWORD kBaudRates[] = { 300, 1200, 2400, 4800, 9600, 19200, 38400, 57600, 115200, 230400 };
enum { FlowHardware = 1, FlowSoftware = 2 }; 

static void SetDCB(DCB* ioDCB, int baudIndex, int dataBits, int parity, int stopBits, int flowControl)
{
	ioDCB->DCBlength = sizeof( DCB );

	ioDCB->BaudRate = kBaudRates[baudIndex];

	ioDCB->ByteSize 		= dataBits+4;
	ioDCB->Parity 			= parity;
	ioDCB->StopBits 		= stopBits;

	// setup hardware flow control
	if (flowControl == FlowHardware)
		ioDCB->fRtsControl	= RTS_CONTROL_HANDSHAKE;
	else
		ioDCB->fRtsControl	= RTS_CONTROL_DISABLE;

	ioDCB->fOutxCtsFlow		= (flowControl == FlowHardware);
	ioDCB->fDtrControl		= DTR_CONTROL_ENABLE;	
	ioDCB->fDsrSensitivity	= false;


	// setup software flow control

	if (flowControl == FlowSoftware)
	{
		ioDCB->fInX 		= ioDCB->fOutX = 1;
		ioDCB->XonChar		= '\021';	// Ctrl-Q;
		ioDCB->XoffChar		= '\023';	// Ctrl-S;
		ioDCB->XonLim		= 100;
		ioDCB->XoffLim		= 100;
	}
	else
	{
		ioDCB->fInX 		= ioDCB->fOutX = 0;
	}

	// other various settings

	ioDCB->fBinary			= TRUE;
	ioDCB->fParity			= TRUE;
	ioDCB->fNull 			= FALSE;
	ioDCB->fAbortOnError	= FALSE;
	
}

static DWORD OpenSerialPort(const char* inPortName, int baudIndex, int dataBits, int parity, int stopBits, int flowControl, HANDLE& serialPortHandle)
{
	serialPortHandle = CreateFile(inPortName,
								GENERIC_READ|GENERIC_WRITE,	
								0,							// lock the port so no one else can get it
								NULL,						// no attributes
								OPEN_EXISTING,
								0, 
								NULL );


	if (serialPortHandle == (HANDLE)-1)
	{
		return GetLastError();
	}

	if (!SetCommMask(serialPortHandle, EV_RXCHAR))	// WaitCommEvent notified by RX Events
	{
		CloseHandle(serialPortHandle); 
		return GetLastError();
	}
	
	if (!SetupComm(serialPortHandle, 4096, 4096))	// 4K Tx and Rx Buffers
	{
		CloseHandle(serialPortHandle); 
		return GetLastError();
	}
	
	// Get rid of any junk that might be sitting there.
	PurgeComm(serialPortHandle, PURGE_TXABORT | PURGE_RXABORT |
		                  PURGE_TXCLEAR | PURGE_RXCLEAR );

	// Using these settings, the ReadFile command will return immediately
	// rather than waiting for a timeout.

	COMMTIMEOUTS  lclCommTimeOuts;
	lclCommTimeOuts.ReadIntervalTimeout			= 0xFFFFFFFF;
	lclCommTimeOuts.ReadTotalTimeoutMultiplier	= 0;
	lclCommTimeOuts.ReadTotalTimeoutConstant	= 0;
	lclCommTimeOuts.WriteTotalTimeoutMultiplier	= 0;
	lclCommTimeOuts.WriteTotalTimeoutConstant	= 2001UL;
	
	if (!SetCommTimeouts(serialPortHandle, &lclCommTimeOuts))
	{
		CloseHandle(serialPortHandle); 
		return GetLastError();
	}

	if (baudIndex >= 0)
	{
		DCB dcb;
		
		if (!GetCommState(serialPortHandle, &dcb))
		{
			CloseHandle(serialPortHandle); 
			return GetLastError();
		}
		
		SetDCB(&dcb, baudIndex, dataBits, parity, stopBits, flowControl);

		if (!SetCommState(serialPortHandle, &dcb))
		{
			CloseHandle(serialPortHandle); 
			return GetLastError();
		}
	}
	
	return ERROR_SUCCESS;
}

static void Disconnect(HANDLE serialPortHandle)
{
	// disable event notification 
	SetCommMask(serialPortHandle, 0);
	
	// drop DTR
	EscapeCommFunction(serialPortHandle, CLRDTR);
	
	// purge any outstanding reads/writes and close device handle
	PurgeComm(serialPortHandle, PURGE_TXABORT | PURGE_RXABORT | PURGE_TXCLEAR | PURGE_RXCLEAR);
							
	CloseHandle(serialPortHandle);
}

// inData: Pointer to data buffer to send
// inSize: Size of data to send
static DWORD SendData(HANDLE serialPortHandle, const void* inData, unsigned long inSize)
{
	DWORD lclNumBytes;

	if (WriteFile(serialPortHandle, inData, inSize, &lclNumBytes, NULL))
		return ERROR_SUCCESS;

	return GetLastError();
}

//	inSize: Size of data to read in bytes
//	outData: Pointer to data buffer to read data into
//	outSize: Size actually read in bytes
static DWORD ReadData(HANDLE serialPortHandle, unsigned long inSize, void* outData, unsigned long &outSize)
{
	// Init our number of bytes read to zero
	outSize = 0;
	
	COMSTAT    lclComStat;
	DWORD      lclErrorFlags;
	DWORD      lclLength;
	
	// clear out any errors in the channel and get the length of the buffer
	ClearCommError(serialPortHandle, &lclErrorFlags, &lclComStat);
	lclLength = MIN(inSize, lclComStat.cbInQue );

	if (lclLength > 0)
	{
		// Read lclLength number of bytes into outData.
		if (!ReadFile(serialPortHandle, outData, lclLength, &outSize, NULL))
			return GetLastError();
	}

	return ERROR_SUCCESS;
}


static DWORD ReceiveData(HANDLE serialPortHandle, unsigned long timeout, unsigned long inSize, void* outData, unsigned long &outSize)
{
	const unsigned long kSleepMillis = 10;

	DWORD error = 0;

	int maxIters = timeout/kSleepMillis;
	int i = 0;
	for (; i < maxIters; i++)
	{
		error = ReadData(serialPortHandle, inSize, outData, outSize);
		if (error != ERROR_SUCCESS)
			return error;
		
		if (outSize > 0)
			break;
		else
			Sleep(kSleepMillis);
	}
	if (i == maxIters)
		return kNoPingError;
	
	return ERROR_SUCCESS;
}

static bool IsVersions3EnabledTRK(long major, long minor, long micro)
{
	return major > 3 || (major == 3 && minor > 2) || (major == 3 && minor == 2 && micro >= 4);
}


__declspec(dllexport)
DWORD GetTRKVersion(const char* inPortName, int baudIndex, int dataBits, int parity, int stopBits, int flowControl, long version[4])
{
	// open the serial port
	HANDLE serialPortHandle = NULL;
	DWORD error = OpenSerialPort(inPortName, baudIndex, dataBits, parity, stopBits, flowControl, serialPortHandle);
	if (error != ERROR_SUCCESS)
		return error;

	// send a ping command
	unsigned char pingTxBuf[] = { 0x7e, 0x00, 0x00, 0xff, 0x7e };
	error = SendData(serialPortHandle, &pingTxBuf, 5);
	if (error != ERROR_SUCCESS) {
		Disconnect(serialPortHandle);
		return error;
	}

	// receive response
	unsigned char pingRxBuf[16];
	unsigned long pingRxSize = 0;
	error = ReceiveData(serialPortHandle, 2001UL, 16, pingRxBuf, pingRxSize);
	if (error != ERROR_SUCCESS) {
		Disconnect(serialPortHandle);
		return error;
	}
	
	// send get version command
	unsigned char versTxBuf[] = { 0x7e, 0x08, 0x01, 0xf6, 0x7e };
	error = SendData(serialPortHandle, &versTxBuf, 5);
	if (error != ERROR_SUCCESS) {
		Disconnect(serialPortHandle);
		return error;
	}

	// receive response
	unsigned char versRxBuf[16];
	unsigned long versRxSize = 0;
	error = ReceiveData(serialPortHandle, 2001UL, 16, versRxBuf, versRxSize);
	if (error != ERROR_SUCCESS) {
		Disconnect(serialPortHandle);
		if (error == kNoPingError)
			return kNoVersionError; // ping ok, but no version
		return error;
	}
	
	if (versRxSize >= 9) {
		version[0] = versRxBuf[4];
		version[1] = versRxBuf[5];
		version[2] = versRxBuf[8];
		version[3] = 0; // unknown TRK

		if (IsVersions3EnabledTRK(version[0], version[1], version[2])) {
			// send versions3 command
			unsigned char vers3TxBuf[] = {0x7e, 0x51, 0x02, 0xac, 0x7e};
			error = SendData(serialPortHandle, &vers3TxBuf, 5);
			if (error != ERROR_SUCCESS) {
				Disconnect(serialPortHandle);
				return error;
			}

			// receive response
			unsigned char vers3RxBuf[64];
			unsigned long vers3RxSize = 0;
			error = ReceiveData(serialPortHandle, 2001UL, 64, vers3RxBuf, vers3RxSize);
			if (error != ERROR_SUCCESS) {
				Disconnect(serialPortHandle);
				return error;
			}

			version[3] = 1; // AppTRK
			unsigned char SYS_TRK_NAME[] = {0x0a, 0x53, 0x79, 0x73, 0x74, 0x65, 0x6d, 0x20, 0x54, 0x52, 0x4b};
			unsigned long SYS_TRK_NAME_LEN = sizeof(SYS_TRK_NAME);
			const int SYS_TRK_NAME_OFFSET = 10;
			if (vers3RxSize >= (SYS_TRK_NAME_LEN + SYS_TRK_NAME_OFFSET)) {
				unsigned char nameBuf[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
				memcpy(nameBuf, vers3RxBuf + SYS_TRK_NAME_OFFSET, SYS_TRK_NAME_LEN);
				if (memcmp(nameBuf, SYS_TRK_NAME, SYS_TRK_NAME_LEN) == 0)
					version[3] = 2; // SysTRK
			}
		}
	}


	Disconnect(serialPortHandle);
	return ERROR_SUCCESS;
}

static const char* GetErrorText(DWORD error)
{
	if (error == kNoPingError)
		return "TRK did not respond";

	else if (error == kNoVersionError)
		return "TRK responded to PING, but not to GETVERSION command";

	else if (error == ERROR_FILE_NOT_FOUND)
		return "Could not open the serial port";

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


/*
 * Class:     com_nokia_carbide_trk_support_service_TRKConnectedService
 * Method:    getTRKVersionFromSerial
 * Signature: (Ljava/lang/String;IIIII[I)V
 */
JNIEXPORT void JNICALL Java_com_nokia_carbide_trk_support_service_TRKConnectedService_getTRKVersionFromSerial
  (JNIEnv* env, jclass, jstring jPortName, jint jBaud, jint jDataBits, jint jParity, jint jStopBits, jint jFlowControl, jintArray jVersionInts)
{
	const char* portName = env->GetStringUTFChars(jPortName, NULL);

	jint versionInts[4];
	DWORD error = GetTRKVersion(portName, jBaud, jDataBits, jParity, jStopBits, jFlowControl, versionInts);
	env->SetIntArrayRegion(jVersionInts, 0, 4, versionInts);
	
	env->ReleaseStringUTFChars(jPortName, portName);

	if (error > ERROR_SUCCESS)
	{

		jclass clazz = env->FindClass("Ljava/lang/Exception;");
		env->ThrowNew(clazz, GetErrorText(error));
	}
}


