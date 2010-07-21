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
// RealSerialComm.cpp: implementation of the CRealSerialComm class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "RealSerialComm.h"
//#include "pn_const.h"
//#include "OSTConstants.h"
#include "Connection.h"

#ifdef _DEBUG
//static char sLogMsg[3000];
#endif
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
#ifdef _DEBUG
#define LogErrorText(err) { if (err > 0) GetErrorText(err); }
#define LogErrorText2(err) { if (err > 0) GetErrorText2(err); }
#else
#define LogErrorText(err) {}
#define LogErrorText2(err) {}
#endif

#ifdef _DEBUG
#define DUMPCOMSTAT(x) DumpComStat(x)
#define DUMPCOMSTATP(x) DumpComStatP(x)
#else
#define DUMPCOMSTAT(x)
#define DUMPCOMSTATP(x)
#endif

CRealSerialComm::CRealSerialComm()
{
#ifdef _DEBUG
	if (gDoLogging)
	{
		FILE* f = fopen("c:\\tcf\\rscommlog.txt", "at");
		fprintf(f, "CRealSerialComm::CRealSerialComm() (default constructor)\n");
		fclose(f);
	}
#endif
	m_hSerial = INVALID_HANDLE_VALUE;
	m_serialPortName[0] = 0;
	m_pBuffer = NULL;
	m_ProcDebugLog = NULL;

}
CRealSerialComm::CRealSerialComm(ConnectData* connectSettings, DWORD connectionId, CBaseProtocol* protocol)
{
#ifdef _DEBUG
	if (gDoLogging)
	{
		FILE* f = fopen("c:\\tcf\\rscommlog.txt", "at");
		fprintf(f, "connectSettings=%x connectionId=%d, protocol=%x\n", connectSettings, connectionId, protocol);
		fclose(f);
	}
#endif
	m_hSerial = INVALID_HANDLE_VALUE;
	m_serialPortName[0] = 0;
	m_pBuffer = NULL;

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
}
CRealSerialComm::~CRealSerialComm()
{
#ifdef _DEBUG
	if (gDoLogging)
	{
		FILE* f = fopen("c:\\tcf\\rscommlog.txt", "at");
		fprintf(f, "CRealSerialComm::~CRealSerialComm()\n");
		fclose(f);
	}
#endif
	if (m_hSerial != INVALID_HANDLE_VALUE)
		::CloseHandle(m_hSerial);

	if (m_pBuffer)
		delete[] m_pBuffer;

}

long CRealSerialComm::OpenPort()
{
	COMMLOGOPEN();
	COMMLOGS("CRealSerialComm::OpenPort\n");

	long err = TCAPI_ERR_NONE;

	char* comPort = m_ConnectSettings->realSerialSettings.comPort;
	DWORD baudRate = m_ConnectSettings->realSerialSettings.baudRate;
	DWORD dataBits = m_ConnectSettings->realSerialSettings.dataBits;
	eParity parity = m_ConnectSettings->realSerialSettings.parity;
	eStopBits stopBits = m_ConnectSettings->realSerialSettings.stopBits;
	eFlowControl flow = m_ConnectSettings->realSerialSettings.flowControl;

	COMMLOGA2("CRealSerialComm::OpenPort comPort=%s baudRate=%d\n", comPort, baudRate);
	COMMLOGA2("CRealSerialComm::OpenPort dataBits=%d parity=%d\n", dataBits, parity);
	COMMLOGA2("CRealSerialComm::OpenPort stopBits=%d flow=%d\n", stopBits, flow);

	// fill in DCB
	m_dcb.DCBlength = sizeof(DCB);
	m_dcb.BaudRate = baudRate;
	m_dcb.ByteSize = dataBits;

	// parity
	switch(parity)
	{
	default:
	case eParityNone:
		m_dcb.fParity = FALSE;
		m_dcb.Parity = NOPARITY;
		break;
	case eParityEven:
		m_dcb.fParity = TRUE;
		m_dcb.Parity = EVENPARITY;
		break;
	case eParityOdd:
		m_dcb.fParity = TRUE;
		m_dcb.Parity = ODDPARITY;
		break;
	}

	// stop bits
	switch(stopBits)
	{
	default:
	case eStopBits1:
		m_dcb.StopBits = ONESTOPBIT;
		break;
	case eStopBits15:
		m_dcb.StopBits = ONE5STOPBITS;
		break;
	case eStopBits2:
		m_dcb.StopBits = TWOSTOPBITS;
		break;
	}

	// flow control
	switch(flow)
	{
	default:
	case eFlowControlNone:
		m_dcb.fRtsControl = RTS_CONTROL_DISABLE;
		m_dcb.fOutxCtsFlow = FALSE;
		m_dcb.fInX = m_dcb.fOutX = FALSE;
		break;
	case eFlowControlHW:
		m_dcb.fRtsControl = RTS_CONTROL_HANDSHAKE;
		m_dcb.fOutxCtsFlow = TRUE;
		m_dcb.fInX = m_dcb.fOutX = FALSE;
		break;
	case eFlowControlSW:
		m_dcb.fRtsControl = RTS_CONTROL_DISABLE;
		m_dcb.fOutxCtsFlow = FALSE;
		m_dcb.fInX = m_dcb.fOutX = TRUE;
		m_dcb.XonChar = '\021';	// Ctrl-Q;
		m_dcb.XoffChar = '\023';	// Ctrl-S;
		m_dcb.XonLim = 100;
		m_dcb.XoffLim = 100;
		break;
	}

	// other things in DCB
	m_dcb.fDtrControl = DTR_CONTROL_ENABLE;	
	m_dcb.fDsrSensitivity = FALSE;
	m_dcb.fBinary = TRUE;
	m_dcb.fNull = FALSE;
	m_dcb.fAbortOnError = TRUE;		// reads & writes will terminate with errors if one occurs

	// translate serial port
	char p[20]; char* pp = p;
	strncpy(p, comPort, 20);
	int len = (int)strlen(p);
	for (int i = 0; i < len; i++)
	{
		p[i] = toupper(p[i]);
	}
	if (strncmp(p, "COM", 3) == 0)
	{
		pp+=3;
	}
	int val = atoi((const char*)pp);
	if (val == INT_MIN || val == INT_MAX)
	{
		err = TCAPI_ERR_INVALID_MEDIA_DATA;
	}
	else
	{
		// must translate for CreatFile
		_snprintf(m_serialPortName, MAX_COMPORT_SIZE, "\\\\.\\COM%d", val);
	}


	if (err == TCAPI_ERR_NONE)
	{
		m_hSerial = CreateFile(m_serialPortName,
			GENERIC_READ|GENERIC_WRITE,	// dwDesiredAccess = read & write
			0,							// dwSharedMode = 0 ==> device not shared
			NULL,						// lpSecurityAttributes = NULL ==> not inheritable
			OPEN_EXISTING,				// dwCreationDisposition ==> required for devices
			0,							// dwFlagsAndAttributes ==> no special flags or attributes (not overlapped)
			NULL );						// hTemplateFile = NULL ==> required for devices

		if (m_hSerial != INVALID_HANDLE_VALUE)
		{
			// TODO: this is really not needed as we're not doing overlapped IO
			//   and we're not creating an event nor waiting on that event
			if (!SetCommMask(m_hSerial, EV_RXCHAR))
			{
				::CloseHandle(m_hSerial);
				m_hSerial = INVALID_HANDLE_VALUE;
				m_lastCommError = GetLastError();
				err = TCAPI_ERR_WHILE_CONFIGURING_MEDIA;
			}
			else
			{
				// no error from SetCommMask
				if (!SetupComm(m_hSerial,MAX_MESSAGE_LENGTH,MAX_SERIAL_MESSAGE_BUFFER_LENGTH))
				{
					CloseHandle(m_hSerial);
					m_hSerial = INVALID_HANDLE_VALUE;
					m_lastCommError = GetLastError();
					err = TCAPI_ERR_WHILE_CONFIGURING_MEDIA;
				}
				else
				{
					// no error from SetupComm
					// Get rid of any junk that might be sitting there.
					PurgeComm( m_hSerial, PURGE_TXABORT | PURGE_RXABORT |
										  PURGE_TXCLEAR | PURGE_RXCLEAR );

					// Using these settings, the ReadFile command will return immediately
					// rather than waiting for a timeout.
					COMMTIMEOUTS lclCommTimeOuts;

					lclCommTimeOuts.ReadIntervalTimeout			= MAXDWORD;	// we don't care about time between chars
					lclCommTimeOuts.ReadTotalTimeoutMultiplier	= 100;
					lclCommTimeOuts.ReadTotalTimeoutConstant	= 0;
					lclCommTimeOuts.WriteTotalTimeoutMultiplier	= 100;
					lclCommTimeOuts.WriteTotalTimeoutConstant	= 0;
					
					if (!SetCommTimeouts( m_hSerial, &lclCommTimeOuts ))
					{
						CloseHandle(m_hSerial); 
						m_hSerial = INVALID_HANDLE_VALUE;
						m_lastCommError = GetLastError();
						err = TCAPI_ERR_WHILE_CONFIGURING_MEDIA;
					}
					else
					{
						// no error from SetCommTimeouts
						err = SetDCB();
						if (err != TCAPI_ERR_NONE)
						{
							CloseHandle(m_hSerial); 
							m_hSerial = INVALID_HANDLE_VALUE;
						}
						else
						{
							// no error from SetDCB 
							err = TCAPI_ERR_NONE;
							m_numberBytes = 0;
							m_lastCommError = 0;
							m_isConnected = true;
							m_pBuffer = new BYTE[MAX_SERIAL_MESSAGE_BUFFER_LENGTH];
						}
					}
				}
			}
		}
		else
		{
			// error from CreateFile
			// couldn't open serial port
			m_lastCommError = GetLastError();
			err = TCAPI_ERR_WHILE_CONFIGURING_MEDIA;
		}
	}

	COMMLOGA2("CRealSerialComm::OpenPort err=%d osError=%d\n", err, m_lastCommError);
	LogErrorText(m_lastCommError);
//	if (m_lastCommError > 0)
//		LogErrorText(m_lastCommError);
	COMMLOGCLOSE();
	return err;
}

long CRealSerialComm::SetDCB()
{
	// assumes serial port is open
	long err = TCAPI_ERR_NONE;
	if (m_hSerial == INVALID_HANDLE_VALUE)
		return err;

	// setup DCB
	DCB lcldcb;
	lcldcb.DCBlength = sizeof(DCB);

	if (!GetCommState( m_hSerial, &lcldcb ))
	{
		m_lastCommError = GetLastError();
		err = TCAPI_ERR_WHILE_CONFIGURING_MEDIA;
	}

//	LogDCB(pInfo);
	// copy only the ones that Connect() set initially
	lcldcb.BaudRate = m_dcb.BaudRate;
	lcldcb.ByteSize = m_dcb.ByteSize;
	lcldcb.Parity = m_dcb.Parity;
	lcldcb.StopBits = m_dcb.StopBits;
	lcldcb.fRtsControl = m_dcb.fRtsControl;
	lcldcb.fOutxCtsFlow = m_dcb.fOutxCtsFlow;
	lcldcb.fDtrControl = m_dcb.fDtrControl;
	lcldcb.fDsrSensitivity = m_dcb.fDsrSensitivity;
	lcldcb.fInX = m_dcb.fInX;
	lcldcb.fOutX = m_dcb.fOutX;
	lcldcb.XonChar = m_dcb.XonChar;
	lcldcb.XoffChar = m_dcb.XoffChar;
	lcldcb.XonLim = m_dcb.XonLim;
	lcldcb.XoffLim = m_dcb.XoffLim;
	lcldcb.fBinary = m_dcb.fBinary;
	lcldcb.fParity = m_dcb.fParity;
	lcldcb.fNull = m_dcb.fNull;
	lcldcb.fAbortOnError = m_dcb.fAbortOnError;

	// DCB has been changed
	// If setting the port went well then we are connected properly!
	if (!SetCommState( m_hSerial, &lcldcb))
	{
		m_lastCommError = GetLastError();
		err = TCAPI_ERR_WHILE_CONFIGURING_MEDIA;
	}

	return err;
}

long CRealSerialComm::ClosePort()
{
	COMMLOGOPEN();
	COMMLOGA1("CRealSerialComm::ClosePort connected=%d\n", IsConnected());

	long err = TCAPI_ERR_NONE;

	if (!IsConnected()) 
	{

		err = TCAPI_ERR_MEDIA_NOT_OPEN;
	}
	else if (m_hSerial != INVALID_HANDLE_VALUE)
	{
		// disable event notification 
		SetCommMask( m_hSerial, 0 );

		// drop DTR
		EscapeCommFunction( m_hSerial, CLRDTR );

		// purge any outstanding reads/writes and close device handle
		PurgeComm(	m_hSerial, 
					PURGE_TXABORT | PURGE_RXABORT | PURGE_TXCLEAR | PURGE_RXCLEAR );
		
		CloseHandle( m_hSerial );
		m_hSerial = INVALID_HANDLE_VALUE;

		if (m_pBuffer)
		{
			delete[] m_pBuffer;
			m_pBuffer = NULL;
		}
		m_isConnected = false;
	}

	COMMLOGCLOSE();
	return err;
}

void CRealSerialComm::DeleteMsg(DWORD inMsgLength)
{
	if (!IsConnected())
		return;
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

long CRealSerialComm::SendDataToPort(DWORD inSize, const void *inData)
{

	long err = TCAPI_ERR_NONE;
	DWORD lclNumBytes=0;
	COMMLOGOPEN();
	COMMLOGS("CRealSerialComm::SendDataToPort\n");
	COMMLOGCLOSE();
	if (!IsConnected())
	{

		COMMLOGOPEN();
		COMMLOGS("CRealSerialComm::SendDataToPort notConnected\n");
		COMMLOGCLOSE();
		return TCAPI_ERR_MEDIA_NOT_OPEN;
	}

	if (WriteFile(m_hSerial, inData, inSize, &lclNumBytes, NULL))
	{
		// we were successful, but did we send all data? (i.e., a timeout occurred)
		// we are not doing overlapped I/O so if not all data went, then we timed out
		//   and there was some kind of error
		if (lclNumBytes != inSize)
		{
			m_lastCommError = 0;
			COMMLOGOPEN();
			COMMLOGA3("CRealSerialComm::SendDataToPort WriteFile not all bytes sent: lclNumBytes=%d inSize=%d err=%d\n", lclNumBytes, inSize, m_lastCommError);
			COMMLOGCLOSE();

			COMSTAT lclComStat;
			DWORD lclErrorFlags = 0;
			if (!ClearCommError(m_hSerial, &lclErrorFlags, &lclComStat))
			{
				// clear comm error returned error (this doesn't normally happen if the handle is valid and port is still open)
				m_lastCommError = GetLastError();
				err = TCAPI_ERR_COMM_ERROR;
				COMMLOGOPEN();
				COMMLOGA1("CRealSerialComm::SendDataToPort ClearCommError failed=%d\n", m_lastCommError);
				COMMLOGCLOSE();
			}
			else
			{
				// clear comm error returned OK
				// check error flags
				if (lclErrorFlags)
				{
					// there really was an error
					err = TCAPI_ERR_COMM_ERROR;
					COMMLOGOPEN();
					COMMLOGA1("CRealSerialComm::SendDataToPort ClearCommError succeeded lclErrorFlags=%d\n", lclErrorFlags);
					COMMLOGCLOSE();
				}
				else
				{
					// No OS error returned, but WriteFile failed to write out all bytes
					//  therefore, since we are not doing overlapped I/O, this is an error.
//					DUMPCOMSTAT(&lclComStat);
					BOOL flush = FlushFileBuffers(m_hSerial); // flush transmit buffer
//					ClearCommError(m_hSerial, &lclErrorFlags, &lclComStat);
//					if (WriteFile(m_hSerial, inData, inSize, &lclNumBytes, NULL))
//					{
//						COMMLOGOPEN();
//						COMMLOGA1("CRealSerialComm::SendDataToPort WriteFile#2 succeeded lclNumBytes=%d\n", lclNumBytes);
//						COMMLOGCLOSE();
//					}
					err = TCAPI_ERR_COMM_ERROR_DEVICE_NOT_READING;
					COMMLOGOPEN();
					COMMLOGA2("CRealSerialComm::SendDataToPort ClearCommError succeeded lclErrorFlags=0 err=%d flush=%d\n", m_lastCommError, flush);
					COMMLOGCLOSE();
//					DUMPCOMSTAT(&lclComStat);
				}
			}
		}
		else
		{
			// we sent all the data we requested
			err = TCAPI_ERR_NONE;
#ifdef _DEBUG
			COMMLOGOPEN();
			COMMLOGS("CRealSerialComm::SendDataToPort WriteFile successful\n");
			BYTE* ptr = (BYTE*)inData;
			long numBytes = (inSize > 80) ? 80 : inSize;
			char msg[300];
			sprintf(msg, "CRealSerialComm::SendDataToPort = ");
			for (int i = 0; i < numBytes; i++)
			{
				sprintf(msg, "%s %02.2x", msg, ptr[i]);
			}
			sprintf(msg, "%s\n", msg);
			COMMLOGS(msg);
			COMMLOGCLOSE();
#endif
		}
	}
	else
	{
		// write failed
		m_lastCommError = GetLastError();
		err = TCAPI_ERR_COMM_ERROR;
		COMMLOGOPEN();
		COMMLOGA1("CRealSerialComm::SendDataToPort WriteFile failed = %d\n", m_lastCommError);
		COMMLOGCLOSE();
	}
	
	return err;
}
long CRealSerialComm::PollPort(DWORD &outSize)
{
	long err = TCAPI_ERR_NONE;
	outSize = 0;

	COMSTAT lclComStat;
	DWORD lclErrorFlags=0;

	if (!IsConnected() || m_hSerial == INVALID_HANDLE_VALUE)
		return TCAPI_ERR_MEDIA_NOT_OPEN;


//	Sleep(1);
	if (!ClearCommError( m_hSerial, &lclErrorFlags, &lclComStat ))
	{
		if (!IsConnected() || m_hSerial == INVALID_HANDLE_VALUE)
			return TCAPI_ERR_MEDIA_NOT_OPEN;

		m_lastCommError = GetLastError();
		err = TCAPI_ERR_COMM_ERROR;

		PROCLOGOPEN();
		PROCLOGA1("CRealSerialComm::PollPort ClearCommError failed=%d\n", m_lastCommError);
//		if (m_lastCommError > 0)
		LogErrorText2(m_lastCommError);
		PROCLOGCLOSE();
	}
	else
	{
		// ClearCommError succeeded
		if (lclErrorFlags)
		{
			m_lastCommError = lclErrorFlags;
			err = TCAPI_ERR_COMM_ERROR;
			PROCLOGOPEN();
			PROCLOGA1("CRealSerialComm::PollPort ClearCommError succeeded but lclErrorFlags=%d\n", m_lastCommError);
			PROCLOGCLOSE();
		}
		else
		{
//			DUMPCOMSTATP(&lclComStat);
//			PROCLOGOPEN();
//			PROCLOGA1("CRealSerialComm::PollPort ClearCommError succeeded cbInQue=%d\n", lclComStat.cbInQue);
//			PROCLOGCLOSE();
			m_lastCommError = 0;
		}
		outSize = lclComStat.cbInQue;
	}

	return err;
}
#ifdef _DEBUG
void CRealSerialComm::DumpComStat(COMSTAT* stat)
{
	COMMLOGOPEN();
	COMMLOGA3(" comstat fCtsHold =%d fDsrHold =%d fRlsdHold=%d\n", stat->fCtsHold, stat->fDsrHold, stat->fRlsdHold);
	COMMLOGA3(" comstat fXoffHold=%d fXoffSent=%d fEof     =%d\n", stat->fXoffHold, stat->fXoffSent, stat->fEof);
	COMMLOGA3(" comstat fTxim    =%d cbInQue  =%d cbOutQue =%d\n", stat->fTxim, stat->cbInQue, stat->cbOutQue);
	COMMLOGCLOSE();
}
void CRealSerialComm::DumpComStatP(COMSTAT* stat)
{
	PROCLOGOPEN();
	PROCLOGA3(" comstat fCtsHold =%d fDsrHold =%d fRlsdHold=%d\n", stat->fCtsHold, stat->fDsrHold, stat->fRlsdHold);
	PROCLOGA3(" comstat fXoffHold=%d fXoffSent=%d fEof     =%d\n", stat->fXoffHold, stat->fXoffSent, stat->fEof);
	PROCLOGA3(" comstat fTxim    =%d cbInQue  =%d cbOutQue =%d\n", stat->fTxim, stat->cbInQue, stat->cbOutQue);
	PROCLOGCLOSE();
}
#endif
long CRealSerialComm::ReadPort(DWORD inSize, void *outData, DWORD &outSize)
{
	long err = TCAPI_ERR_NONE;
	outSize = 0;

	COMSTAT lclComStat;
	DWORD lclErrorFlags=0;
	DWORD lclLength;
	if (!IsConnected())
		return TCAPI_ERR_MEDIA_NOT_OPEN;

	// clear out any errors in the channel and get the length of the buffer
	if (!ClearCommError( m_hSerial, &lclErrorFlags, &lclComStat ))
	{
		// ClearCommError failed
		m_lastCommError = GetLastError();
		err = TCAPI_ERR_COMM_ERROR;
		PROCLOGOPEN();
		PROCLOGA1("CRealSerialComm::ReadPort ClearCommError failed=%d\n", m_lastCommError);
		PROCLOGCLOSE();
	}
	else
	{
		if (lclErrorFlags)
		{
			m_lastCommError = lclErrorFlags;
			err = TCAPI_ERR_COMM_ERROR;
			PROCLOGOPEN();
			PROCLOGA1("CRealSerialComm::ReadPort ClearCommError succeeded but lclErrorFlags=%d\n", m_lastCommError);
			PROCLOGCLOSE();
		}
		else
		{
			m_lastCommError = 0;

			lclLength = min( inSize, lclComStat.cbInQue );

			if (lclLength > 0)
			{
				// Read lclLength number of bytes into outData.
				if (!ReadFile(m_hSerial,outData,lclLength,&outSize,NULL))
				{
					m_lastCommError = GetLastError();
					err = TCAPI_ERR_COMM_ERROR;
					PROCLOGOPEN();
					PROCLOGA1("CRealSerialComm::ReadPort ReadFile failed = %d\n", m_lastCommError);
					PROCLOGCLOSE();
				}
				else
				{
					// ReadFile returned successful, check to see all our bytes came in
					//  If a timeout happened - we may not get all the data
					if (lclLength != outSize)
					{
						lclErrorFlags = 0;
						if (!ClearCommError( m_hSerial, &lclErrorFlags, &lclComStat ))
						{
							// ClearCommError failed
							m_lastCommError = GetLastError();
							err = TCAPI_ERR_COMM_ERROR;
							PROCLOGOPEN();
							PROCLOGA1("CRealSerialComm::ReadPort ClearCommError failed=%d\n", m_lastCommError);
							PROCLOGCLOSE();
						}
						else
						{
							// ClearCommError succeeded
							if (lclErrorFlags)
							{
								// there really was an error
								m_lastCommError = lclErrorFlags;
								err = TCAPI_ERR_COMM_ERROR;
								PROCLOGOPEN();
								PROCLOGA1("CRealSerialComm::ReadPort ReadFile succeeded-not all data read lclErrorFlags=%d\n", m_lastCommError);
								PROCLOGCLOSE();
							}
							else
							{
								// Since we are not doing overlapped I/O 
								//  and our timeout values say to timeout, we should read all the bytes
								//  that the last Poll told us, if not this is an error
								err = TCAPI_ERR_COMM_ERROR;
								PROCLOGOPEN();
								PROCLOGS("CRealSerialComm::ReadPort ReadFile succeeded-not all data read lclErrorFlags=0\n");
								PROCLOGCLOSE();
							}
						}
					}
					else
					{
						// all data read
						m_lastCommError = 0;
						PROCLOGOPEN();
						PROCLOGS("CRealSerialComm::ReadPort ReadFile successful\n");
						PROCLOGCLOSE();
					}
				}
			}
		}
	}

	return err;
}

long CRealSerialComm::ProcessBuffer(CConnection* pConn, CRegistry* pRegistry, long& numberProcessed)
{
	PROCLOGOPEN();
	PROCLOGS("CRealSerialComm::ProcessBuffer\n");
	PROCLOGCLOSE();

	long err = TCAPI_ERR_NONE;
	long routingErr = TCAPI_ERR_NONE;

	if (!IsConnected())
		return TCAPI_ERR_MEDIA_NOT_OPEN;

	if (!m_Protocol)
		return TCAPI_ERR_UNKNOWN_MEDIA_TYPE;

	DWORD protocolHeaderLength = m_Protocol->GetHeaderLength();

	// fill buffer
	if (m_numberBytes < MAX_SERIAL_MESSAGE_BUFFER_LENGTH)
	{
		DWORD outLen = 0;
		err = PollPort(outLen);
		if (err == TCAPI_ERR_NONE && outLen > 0)
		{
			if (outLen > (MAX_SERIAL_MESSAGE_BUFFER_LENGTH - m_numberBytes))
				outLen = MAX_SERIAL_MESSAGE_BUFFER_LENGTH - m_numberBytes;
			BYTE *ptr = &m_pBuffer[m_numberBytes];
			err = ReadPort(outLen, ptr, outLen);
			if (err == TCAPI_ERR_NONE && outLen > 0)
			{
				m_numberBytes += outLen;
			}
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

					sLogMsg[0] = '\0';
					if (reallen > 0)
					{
						sLogMsg[0] = '\0';
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
							strcat(sLogMsg, msg);
						}
					}
#endif
					PROCLOGOPEN();
					PROCLOGA5("CRealSerialComm::ProcessBuffer - RouteMesssage pRegistry = %x id=%x len=%d len=%d msg=%s\n", pRegistry, msgId, fullMessageLength, rawLength, sLogMsg);
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
		}
	}
//	PROCLOGOPEN();
//	PROCLOGA1("CRealSerialComm::ProcessBuffer err = %d\n", err);
//	PROCLOGCLOSE();
	if (routingErr == TCAPI_ERR_NONE)
		return err;
	else
		return routingErr;
}
bool CRealSerialComm::IsConnectionEqual(ConnectData* pConn)
{
	bool equal = false;

	// forms accepted:
	//   "comNN", "NN"
	char* ptr1 = m_ConnectSettings->realSerialSettings.comPort;
	char* ptr2 = pConn->realSerialSettings.comPort;
	bool digit1found = false;
	while(!digit1found && *ptr1 != NULL) 
	{
		if (*ptr1 >= '0' && *ptr1 <= '9')
		{
			digit1found = true;
			break;
		}
		ptr1++;
	}
	bool digit2found = false;
	while(!digit2found && *ptr2 != NULL) 
	{
		if (*ptr2 >= '0' && *ptr2 <= '9')
		{
			digit2found = true;
			break;
		}
		ptr2++;
	}
	if (digit1found && digit2found)
	{
		if (strcmp(ptr1, ptr2) == 0)
			equal = true;
	}
	return equal;
}

#ifdef _DEBUG
DWORD CRealSerialComm::GetErrorText(DWORD inError)
{
	LPVOID lpMsgBuf;
	
	if (inError == 0) return inError;

	FormatMessage( 
		FORMAT_MESSAGE_ALLOCATE_BUFFER | FORMAT_MESSAGE_FROM_SYSTEM,
		NULL,
		inError,
		MAKELANGID(LANG_NEUTRAL, SUBLANG_DEFAULT), 	// Default language
		(LPTSTR) &lpMsgBuf,
		0,
		NULL );

	COMMLOGA1(" -- GetErrorText=%s", lpMsgBuf);
	// Free the buffer.
	LocalFree( lpMsgBuf );
	
	return inError;
}
DWORD CRealSerialComm::GetErrorText2(DWORD inError)
{
	LPVOID lpMsgBuf;
	
	if (inError == 0) return inError;

	FormatMessage( 
		FORMAT_MESSAGE_ALLOCATE_BUFFER | FORMAT_MESSAGE_FROM_SYSTEM,
		NULL,
		inError,
		MAKELANGID(LANG_NEUTRAL, SUBLANG_DEFAULT), 	// Default language
		(LPTSTR) &lpMsgBuf,
		0,
		NULL );

	PROCLOGA1(" -- GetErrorText=%s", lpMsgBuf);
	// Free the buffer.
	LocalFree( lpMsgBuf );
	
	return inError;
}

void CRealSerialComm::DumpBuffer(BYTE* ptr, long length)
{
	char msg[256] = {0};
	if (length > 50) length = 50;
	for (int i = 0; i < length; i++)
	{
		sprintf(msg, "%s%02.2X ", msg, ptr[i]);
	}
	sprintf(msg, "%s\n", msg);
	PROCLOGS(msg);
}
#endif
