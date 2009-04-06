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
/**
 * 
 */
package com.nokia.tcf.impl;

import com.nokia.tcf.Activator;
import com.nokia.tcf.api.*;

import org.eclipse.core.runtime.*;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

public class TCAPIConnection implements ITCAPIConnection {

	static {
		Activator plugin = Activator.getDefault();
		IPath path = null;
		String dePath = null;
		if (plugin != null)
			path = plugin.getDebuggerPath();
		if (path != null)
			dePath = path.toOSString();
		if (dePath == null) {
			System.loadLibrary("TCFClient");
		} else {
			try{
				System.load(dePath + java.io.File.separator + "TCFClient.dll");
			} catch (UnsatisfiedLinkError e) {
				// if Carbide DLL is not found in DE, 
				// try to load one from the plugin itself
				System.loadLibrary("TCFClient");
			}
		}
	}
	private TCErrorListenerList<ITCErrorListener> errorListenerList;
	protected ITCCookie cookie;		// this client's handle to native
	protected ITCMessageIds messageIds; // this client's message ids
	protected TCMessageInputStream inputStream; // this client's message input stream (not using message file)
	protected ITCMessageOptions messageOptions; // this client's message options
	protected TCFMonitorThread monitorThread; // the client's native monitor
	public boolean stopTCFMonitorThread;	//  stream monitor start/stop flag
	protected IStatus statusOK;
	protected ITCConnection connection; // the client's connection

	public ITCConnection getConnection() {
		return this.connection;
	}
	public ITCMessageOptions getMessageOptions() {
		return this.messageOptions;
	}
	/**
	 * Only constructor - fields are created using the public methods
	 */
	public TCAPIConnection() {
		super();
		this.errorListenerList = new TCErrorListenerList<ITCErrorListener>();
		this.errorListenerList.clear();
		this.connection = null;
		IStatus status = new Status(Status.OK, Activator.PLUGIN_ID, (int)TCErrorConstants.TCAPI_ERR_NONE, "OK", null);
		statusOK = status;
		this.cookie = new TCCookie(-1, status, false, false);
		this.inputStream = null;
		this.messageIds = null;
		this.messageOptions = null;
		this.monitorThread = null;
		this.stopTCFMonitorThread = false;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCAPIConnection#addErrorListener(com.nokia.tcf.api.ITCErrorListener)
	 */
	public IStatus addErrorListener(ITCErrorListener inErrorListener) {
		IStatus status = statusOK;
		try {
			errorListenerList.add(inErrorListener);
		} catch (NullPointerException e) {
			int err = (int)TCErrorConstants.TCAPI_ERR_ERRLISTENER_NULL;
			status = new Status(Status.ERROR, Activator.PLUGIN_ID, err,	TCErrorConstants.getErrorMessage(err), e);
		}
//		System.out.printf("addErrorListener status = %s\n", status.getMessage());
		return status;
	}

	private IStatus checkConnected() {
		IStatus status = statusOK;
		if (this.connection == null || this.cookie.isConnected() == false) {
			status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)TCErrorConstants.TCAPI_ERR_MEDIA_NOT_OPEN,
					TCErrorConstants.getErrorMessage(TCErrorConstants.TCAPI_ERR_MEDIA_NOT_OPEN), null);
		}
		return status;
	}
	protected IStatus checkDecodeFormat(String decodeFormat) {
		IStatus status = statusOK;
		return status;		
	}
	protected IStatus checkConnectionType(ITCConnection inConnection) {
		IStatus status = statusOK;
		String type = inConnection.getConnectionType();
		if (type.compareToIgnoreCase("virtualserial") == 0) {
			ITCVirtualSerialConnection c = (ITCVirtualSerialConnection)inConnection;
			String p = c.getComPort();
			if (p == null) {
				status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)TCErrorConstants.TCAPI_ERR_MISSING_MEDIA_DATA, 
						TCErrorConstants.getErrorMessage(TCErrorConstants.TCAPI_ERR_MISSING_MEDIA_DATA), null);
			}
		} else if (type.compareToIgnoreCase("tcp") == 0) {
			ITCRealTCPConnection c = (ITCRealTCPConnection)inConnection;
			String ip = c.getIpAddress();
			String p = c.getPort();
			if (ip == null || p == null) {
				status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)TCErrorConstants.TCAPI_ERR_MISSING_MEDIA_DATA, 
						TCErrorConstants.getErrorMessage(TCErrorConstants.TCAPI_ERR_MISSING_MEDIA_DATA), null);
			}
		} else if (type.compareToIgnoreCase("serial") == 0) {
			ITCRealSerialConnection c = (ITCRealSerialConnection)inConnection;
			long err = checkRealSerialSettings(c);
			if (err != TCErrorConstants.TCAPI_ERR_NONE) {
				status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)err, TCErrorConstants.getErrorMessage(err), null);
			}
		} else {
			// Add other connection types here
			status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)TCErrorConstants.TCAPI_ERR_UNKNOWN_MEDIA_TYPE,
					TCErrorConstants.getErrorMessage(TCErrorConstants.TCAPI_ERR_UNKNOWN_MEDIA_TYPE), null);
		}
		
		return status;
	}
	private IStatus checkConnection(ITCConnection inConnection) {
		IStatus status = statusOK;
		if (this.cookie.isConnected()) {
			status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)TCErrorConstants.TCAPI_ERR_ALREADY_CONNECTED, 
					TCErrorConstants.getErrorMessage(TCErrorConstants.TCAPI_ERR_ALREADY_CONNECTED), null);
		}
		if (inConnection == null) {
			status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)TCErrorConstants.TCAPI_ERR_MISSING_CONNECTION_SPEC, 
					TCErrorConstants.getErrorMessage(TCErrorConstants.TCAPI_ERR_MISSING_CONNECTION_SPEC), null);
		}
		if (status.isOK()) {
			String decodeFormat = inConnection.getDecodeFormat();
			status = checkDecodeFormat(decodeFormat);
		}
		if (status.isOK()) {
			long retryI = inConnection.getRetryInterval();
			long retryT = inConnection.getRetryTimeout();
			if (retryI == 0 || retryT == 0 || retryI > retryT) {
				status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)TCErrorConstants.TCAPI_ERR_INVALID_RETRY_PERIODS, 
						TCErrorConstants.getErrorMessage(TCErrorConstants.TCAPI_ERR_INVALID_RETRY_PERIODS), null);
			}
		}
		
		if (status.isOK()) {
			status = checkConnectionType(inConnection);
		}
	
		return status;
	}
	private long checkRealSerialSettings(ITCRealSerialConnection c) {
		String[] setting = new String[6];
		setting[0] = c.getBaudRate();
		setting[1] = c.getComPort();
		setting[2] = c.getDataBits();
		setting[3] = c.getFlowControl();
		setting[4] = c.getParity();
		setting[5] = c.getStopBits();
		for (int i = 0; i < 6; i++) {
			if (setting[i] == null) {
				return TCErrorConstants.TCAPI_ERR_MISSING_MEDIA_DATA;
			}
		}
		if ((setting[2] != ITCRealSerialConnection.DATABITS_4) && (setting[2] != ITCRealSerialConnection.DATABITS_5) && (setting[2] != ITCRealSerialConnection.DATABITS_6) &&
				(setting[2] != ITCRealSerialConnection.DATABITS_7) && (setting[2] != ITCRealSerialConnection.DATABITS_8)) {
			return TCErrorConstants.TCAPI_ERR_COMM_INVALID_DATABITS;
		}
		if ((setting[3] != ITCRealSerialConnection.FLOWCONTROL_HW) && (setting[3] != ITCRealSerialConnection.FLOWCONTROL_NONE) && (setting[3] != ITCRealSerialConnection.FLOWCONTROL_SW)) {
			return TCErrorConstants.TCAPI_ERR_COMM_INVALID_FLOWCONTROL;
		}
		if ((setting[4] != ITCRealSerialConnection.PARITY_EVEN) && (setting[4] != ITCRealSerialConnection.PARITY_NONE) && (setting[4] != ITCRealSerialConnection.PARITY_ODD)) {
			return TCErrorConstants.TCAPI_ERR_COMM_INVALID_PARITY;
		}
		if ((setting[5] != ITCRealSerialConnection.STOPBITS_1) && (setting[5] != ITCRealSerialConnection.STOPBITS_1_5) && (setting[5] != ITCRealSerialConnection.STOPBITS_2)) {
			return TCErrorConstants.TCAPI_ERR_COMM_INVALID_STOPBITS;
		}
		
		return TCErrorConstants.TCAPI_ERR_NONE;
	}
	private IStatus checkMessage(ITCMessage inMessage) {
		IStatus status = statusOK;

		if (inMessage == null) {
			// inMessage cannot be null
			status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)TCErrorConstants.TCAPI_ERR_MISSING_MESSAGE,
					TCErrorConstants.getErrorMessage(TCErrorConstants.TCAPI_ERR_MISSING_MESSAGE), null);
		} else if (inMessage.size() == 0) {
			// bytes to send may be 0 if we're doing the header (header is the message)
			if (this.messageOptions.getMessageEncodeFormat() == ITCMessageOptions.ENCODE_NO_FORMAT) {
				status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)TCErrorConstants.TCAPI_ERR_MISSING_MESSAGE,
						TCErrorConstants.getErrorMessage(TCErrorConstants.TCAPI_ERR_MISSING_MESSAGE), null);
			}
		}
		if (status.isOK()) {
			// check options
			if (inMessage.isUseMyMessageId()) {
				if (this.messageOptions.getMessageEncodeFormat() == ITCMessageOptions.ENCODE_NO_FORMAT) {
					// use my id, but don't encode = error
					status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)TCErrorConstants.TCAPI_ERR_MESSAGE_OPTIONS_CONFLICT,
							TCErrorConstants.getErrorMessage(TCErrorConstants.TCAPI_ERR_MESSAGE_OPTIONS_CONFLICT), null);
				} else {
					// use my id, and encode = OK
				}
			} else {
				if (this.messageOptions.getMessageEncodeFormat() == ITCMessageOptions.ENCODE_NO_FORMAT) {
					// don't use my id, and don't encode = OK
				} else {
					// don't use my id, but encode = warning (we'll go ahead send this message raw)
					status = new Status(Status.WARNING, Activator.PLUGIN_ID, (int)TCErrorConstants.TCAPI_ERR_MESSAGE_OPTIONS_CONFLICT,
							TCErrorConstants.getErrorMessage(TCErrorConstants.TCAPI_ERR_MESSAGE_OPTIONS_CONFLICT), null);
				}
			}
		}
		return status;
	}
	private IStatus checkMessageIds(ITCMessageIds inMessageIds) {
		IStatus status = statusOK;

		if (inMessageIds == null || inMessageIds.size() == 0) {
			status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)TCErrorConstants.TCAPI_ERR_NO_MESSAGESIDS_REGISTERED,
					TCErrorConstants.getErrorMessage(TCErrorConstants.TCAPI_ERR_NO_MESSAGESIDS_REGISTERED), null);
		}
		if (status.isOK()) {
			if (inMessageIds.size() > 256) {
				status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)TCErrorConstants.TCAPI_ERR_MESSAGEID_MAXIMUM,
						TCErrorConstants.getErrorMessage(TCErrorConstants.TCAPI_ERR_MESSAGEID_MAXIMUM), null);
			}
		}
		return status;
	}


	private IStatus checkMessageOptions(ITCMessageOptions inMessageOptions) {
		IStatus status = statusOK;

		if (inMessageOptions == null) {
			status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)TCErrorConstants.TCAPI_ERR_MISSING_MESSAGE_OPTIONS,
					TCErrorConstants.getErrorMessage(TCErrorConstants.TCAPI_ERR_MISSING_MESSAGE_OPTIONS), null);
		}
//		if (status.isOK()) {
//			long option = inMessageOptions.getMessageDestination();
//			String file = inMessageOptions.getMessageFile().toOSString();
//			if (option == ITCMessageOptions.DESTINATION_CLIENTFILE) {
//				status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)TCErrorConstants.TCAPI_ERR_FEATURE_NOT_IMPLEMENTED,
//					TCErrorConstants.getErrorMessage(TCErrorConstants.TCAPI_ERR_FEATURE_NOT_IMPLEMENTED), null);
//			}
//		}
		
		if (status.isOK()) {
			long option = inMessageOptions.getMessageEncodeFormat();
			switch ((int)option) {
			case (int)ITCMessageOptions.ENCODE_NO_FORMAT:
			case (int)ITCMessageOptions.ENCODE_FORMAT:
			case (int)ITCMessageOptions.ENCODE_TRK_FORMAT:
				break;
			default:
				status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)TCErrorConstants.TCAPI_ERR_INVALID_ENCODE_FORMAT,
						TCErrorConstants.getErrorMessage(TCErrorConstants.TCAPI_ERR_INVALID_ENCODE_FORMAT), null);
			}
		}
		if (status.isOK()) {
			long option = inMessageOptions.getUnWrapFormat();
			switch ((int)option) {
			case (int)ITCMessageOptions.UNWRAP_DELETE_HEADERS:
			case (int)ITCMessageOptions.UNWRAP_LEAVE_HEADERS:
				break;
			default:
				status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)TCErrorConstants.TCAPI_ERR_INVALID_MESSAGE_UNWRAP_OPTION,
						TCErrorConstants.getErrorMessage(TCErrorConstants.TCAPI_ERR_INVALID_MESSAGE_UNWRAP_OPTION), null);
			}
		}
		
		if (status.isOK()) {
			long bufferSize = inMessageOptions.getInputStreamSize();
			if (bufferSize <= 0) {
				status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)TCErrorConstants.TCAPI_ERR_INVALID_STREAM_BUFFER_SIZE,
						TCErrorConstants.getErrorMessage(TCErrorConstants.TCAPI_ERR_INVALID_STREAM_BUFFER_SIZE), null);
			}
		}
		
		return status;
	}

	protected IStatus checkConnectOptions(ITCConnection inConnection,
			ITCMessageOptions inMessageOptions, ITCMessageIds inMessageIds) {
		
		IStatus status = statusOK;

		status = checkConnection(inConnection);
		
		if (status.isOK()) {
			status = checkMessageOptions(inMessageOptions);
		}
		if (status.isOK()) {
			status = checkMessageIds(inMessageIds);
		}
		return status;
	}
	protected IStatus finishConnect(String type, String[] settings, 
			ITCConnection inConnection, ITCMessageOptions inMessageOptions, ITCMessageIds inMessageIds) {
		IStatus status = statusOK;
		// connect
		long[] clientId = new long[1];
		clientId[0] = -1;
		long[] options = new long[3];
		options[0] = inConnection.getRetryInterval();		// connection options
		options[1] = inConnection.getRetryTimeout();
		options[2] = 0;
		long[] moptions = new long[2];
		moptions[0] = inMessageOptions.getUnWrapFormat();
		moptions[1] = inMessageOptions.getOSTVersion();
		String filePath = null;
		if (inMessageOptions.getMessageDestination() == ITCMessageOptions.DESTINATION_CLIENTFILE) {
			filePath = inMessageOptions.getMessageFile().toOSString();
			try {
				ensureWritableFile(filePath);
			} catch (IOException e) {
				String msg = e.getMessage();
				long err = TCErrorConstants.TCAPI_ERR_CREATE_FILE;
				String tcErr = String.format("%s OSError: %s", TCErrorConstants.getErrorMessage(err), msg);
				status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, 
						(int)TCErrorConstants.TCAPI_ERR_CREATE_FILE, tcErr, e);
			}
		}
		
		try {
			long ret = nativeConnect(type, options, settings, moptions, filePath, clientId); 
			if (ret == TCErrorConstants.TCAPI_ERR_NONE) {
				this.cookie.setClientId(clientId[0]);
				this.cookie.setConnected(true);
				this.connection = inConnection;
				this.messageOptions = inMessageOptions;
			} else {
				status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)ret, TCErrorConstants.getErrorMessage(ret), null);
			}
		} catch (Exception e) {
			// exception is thrown ONLY when err = TCAPI_ERR_WHILE_CONFIGURING_MEDIA from native
			// and the message is the OS error generated
			String msg = e.getMessage();
			long ret = TCErrorConstants.TCAPI_ERR_WHILE_CONFIGURING_MEDIA;
			String tcErr = String.format("%s OSError: %s", TCErrorConstants.getErrorMessage(ret), msg);
			status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)ret, tcErr, null);
		}
		// send message ids to capture
		if (status.isOK()) {
			int number = (int)inMessageIds.size();
			byte[] ids = new byte[number];
			for (int i = 0; i < number; i++) {
				ids[i] = inMessageIds.getMessageIds().get(i);
			}
			long ret = nativeSetMessageIds(this.cookie.getClientId(), ids);
			if (ret == TCErrorConstants.TCAPI_ERR_NONE) {
				this.messageIds = inMessageIds;
			} else {
				status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)ret, TCErrorConstants.getErrorMessage(ret), null);
			}
		}
		// setup input stream
		if (status.isOK() && inMessageOptions.getMessageDestination() == ITCMessageOptions.DESTINATION_INPUTSTREAM) {
			inputStream = new TCMessageInputStream(this,
					inMessageOptions.getInputStreamSize(),
					cookie.getClientId());
			try {
				inputStream.open();
			} catch (IOException e) {
				// stream didn't open
				status = new Status(Status.ERROR, Activator.PLUGIN_ID, e.getMessage(), e);
				e.printStackTrace();
			}
		}
		// create error monitor
		if (status.isOK()) {
			String monitorName = String.format("TCFMonitor%d", this.cookie.getClientId());
			monitorThread = new TCFMonitorThread(this, monitorName);
			stopTCFMonitorThread = false;
			monitorThread.start();
		}
		
		// start capture
		if (status.isOK()) {
			long ret = nativeStart(this.cookie.getClientId());
			if (ret == TCErrorConstants.TCAPI_ERR_NONE) {
				this.cookie.setMessageProcessing(true);
			} else {
				// error in starting capture
				status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)ret, TCErrorConstants.getErrorMessage(ret), null);
			}
		}
		if (!status.isOK()) {
			// an error occurred along the way - unravel what we've done
			if (this.cookie.isMessageProcessing()) {
				long ret = nativeStop(this.cookie.getClientId());
				// ignore error
				this.cookie.setMessageProcessing(false);
			}
			if (this.monitorThread != null) {
				this.stopTCFMonitorThread = true;
				try {
					this.monitorThread.join();
				} catch (InterruptedException e) {
				}
				this.monitorThread = null;
			}
			if (this.inputStream != null && this.inputStream.isOpen()) {
				try {
					this.inputStream.close();
				} catch (IOException e) {
				}
				this.inputStream = null;
			}
			if (this.cookie.isConnected()) {
				long ret = nativeDisconnect(this.getClientId());
				this.cookie.setConnected(false);
				this.cookie.setClientId(-1);
			}
		}
		return status;
	}
	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCAPIConnection#connect(com.nokia.tcf.api.ITCConnection, com.nokia.tcf.api.ITCMessageOptions, com.nokia.tcf.api.ITCMessageIds)
	 */
	public IStatus connect(ITCConnection inConnection,
			ITCMessageOptions inMessageOptions, ITCMessageIds inMessageIds) {
		
		IStatus status = checkConnectOptions(inConnection, inMessageOptions, inMessageIds);
		String[] settings = null;
		String type = null;

		// do connect
		if (status.isOK()) {
			settings = null;
			type = inConnection.getConnectionType();
			if (type.compareToIgnoreCase("tcp") == 0) {
				settings = new String[3];
				ITCRealTCPConnection t = (ITCRealTCPConnection)inConnection;
				settings[0] = t.getIpAddress();
				settings[1] = t.getPort();
				settings[2] = t.getDecodeFormat().toLowerCase();
			} else if (type.compareToIgnoreCase("virtualserial") == 0) {
				settings = new String[2];
				ITCVirtualSerialConnection s = (ITCVirtualSerialConnection)inConnection;
				settings[0] = s.getComPort();
				settings[1] = s.getDecodeFormat().toLowerCase();
			} else if (type.compareToIgnoreCase("serial") == 0) {
				settings = new String[7];
				ITCRealSerialConnection s = (ITCRealSerialConnection)inConnection;
				settings[0] = s.getComPort();
				settings[1] = s.getBaudRate();
				settings[2] = s.getDataBits();
				settings[3] = s.getParity();
				settings[4] = s.getStopBits();
				settings[5] = s.getFlowControl();
				settings[6] = s.getDecodeFormat().toLowerCase();
			} else if (type.compareToIgnoreCase("usb") == 0) {
				settings = new String[1];
			} else {
				// Add other connections here
			}
		}
		return finishConnect(type, settings, inConnection, inMessageOptions, inMessageIds);
	}
	protected void ensureWritableFile(String filePath) throws IOException {
		// ensure file path points to a writable regular file
		IPath path = new Path(filePath);
		File file = path.toFile();
		if (!file.exists()) {
			file.createNewFile();
		}
		else { // file exists
			if (file.isDirectory()) {
				throw new IOException(MessageFormat.format("Path is a directory: {0}", filePath));
			}
			else if (!file.canWrite()) {
				throw new IOException(MessageFormat.format("File exists and is not writable: {0}", filePath));
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCAPIConnection#connect(com.nokia.tcf.api.ITCMessageOptions, com.nokia.tcf.api.ITCMessageIds)
	 */
	public IStatus connect(ITCMessageOptions inMessageOptions, ITCMessageIds inMessageIds) {
		// not currently implemented or tested
		long ret = TCErrorConstants.TCAPI_ERR_FEATURE_NOT_IMPLEMENTED;
		IStatus status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)ret, TCErrorConstants.getErrorMessage(ret), null);
		return status;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCAPIConnection#disconnect()
	 */
	public IStatus disconnect() {
		IStatus status = statusOK;
		
		IStatus connectStatus = checkConnected();
		// ignore error
		if (connectStatus.isOK()) {
			if (this.cookie.isMessageProcessing()) {
				long ret = nativeStop(this.cookie.getClientId());
				this.cookie.setMessageProcessing(false);
				if (ret != TCErrorConstants.TCAPI_ERR_NONE) {
					status = new Status(IStatus.ERROR,Activator.PLUGIN_ID, (int)ret, TCErrorConstants.getErrorMessage(ret), null);
				}
			}
			if (this.monitorThread != null) {
				this.stopTCFMonitorThread = true;
				this.monitorThread.stop = true;
				try {
					this.monitorThread.join();
				} catch (InterruptedException e) {
				}
				this.monitorThread = null;
			}
			if (this.inputStream != null && this.inputStream.isOpen()) {
				try {
					this.inputStream.close();
				} catch (IOException e) {
				}
				this.inputStream = null;
			}
			if (this.cookie.isConnected()) {
				long ret = nativeDisconnect(this.cookie.getClientId());
				if (ret == TCErrorConstants.TCAPI_ERR_NONE) {
					this.cookie.setConnected(false);
					this.cookie.setClientId(-1);
				} else {
					// error from nativeDisconnect
					status = new Status(Status.ERROR,Activator.PLUGIN_ID, (int)ret, TCErrorConstants.getErrorMessage(ret), null);
				}
			}
		}
		return status;
	}

	// private methods
	/**
	 * This is called from TCFMonitorThread to send errors to registered listeners
	 */
	public void fireErrorListeners(long errorCode, String errorString) {
		int s = this.errorListenerList.size();
//		System.out.printf("fireErrorListeners s = %d\n", s);
		for (int i = 0; i < s; i++) {
			ITCErrorListener e = (ITCErrorListener)this.errorListenerList.get(i);
			if (e != null) {
				int errorStatus = Status.ERROR;
				if (errorCode == TCErrorConstants.TCAPI_INFO_COMM_RECONNECTED)
					errorStatus = Status.INFO;
				IStatus status = new Status(errorStatus, Activator.PLUGIN_ID, (int)errorCode, errorString, null);
				e.errorOccurred(status);
			}
		}
	}

	public long getClientId() {
		return this.cookie.getClientId();
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCAPIConnection#getConnections()
	 */
	public ITCConnection[] getConnections() {
		IStatus status = statusOK;
		ITCConnection[] connections = null;
		long numberConnections = 0;
		long[] number = new long[1];
		long ret = nativeGetNumberConnections(number);
		if (ret == TCErrorConstants.TCAPI_ERR_NONE) {
			numberConnections = number[0];
		} else {
			// error processing from nativeGetNumberConnections
			status = new Status(Status.ERROR,Activator.PLUGIN_ID, (int)ret, TCErrorConstants.getErrorMessage(ret), null);
		}
		if (numberConnections > 0)
		{
			connections = new ITCConnection[(int)numberConnections];
			for (int inIndex = 0; inIndex < numberConnections; inIndex++)
			{
				String[] type = new String[1];
				ret = nativeGetTypeOfConnection(inIndex, type);
				if (type[0].compareToIgnoreCase("virtualserial") == 0) {
					ITCVirtualSerialConnection outConnection = (ITCVirtualSerialConnection)TCFClassFactory.createITCVirtualSerialConnection(null);
					String[] outType = new String[1];
					long[] outOptions = new long[3];
					String[] outSettings = new String[2];
					ret = nativeGetConnectionSettings(inIndex, outType, outOptions, outSettings);
					if (ret == TCErrorConstants.TCAPI_ERR_NONE) {
						outConnection.setConnectionType(outType[0]);
						outConnection.setRetryInterval(outOptions[0]);
						outConnection.setRetryTimeout(outOptions[1]);
						outConnection.setComPort(outSettings[0]);
						outConnection.setDecodeFormat(outSettings[1]);
						
						connections[inIndex] = outConnection;
					} else {
						// error processing from nativeGetConnectionSettings
						status = new Status(Status.ERROR,Activator.PLUGIN_ID, (int)ret, TCErrorConstants.getErrorMessage(ret), null);
					}
				} else if (type[0].compareToIgnoreCase("serial") == 0) {
					ITCRealSerialConnection outConnection = (ITCRealSerialConnection)TCFClassFactory.createITCRealSerialConnection(null);
					String[] outType = new String[1];
					long[] outOptions = new long[6];
					String[] outSettings = new String[7];
					ret = nativeGetConnectionSettings(inIndex, outType, outOptions, outSettings);
					if (ret == TCErrorConstants.TCAPI_ERR_NONE) {
						outConnection.setConnectionType(outType[0]);
						outConnection.setRetryInterval(outOptions[0]);
						outConnection.setRetryTimeout(outOptions[1]);
						outConnection.setComPort(outSettings[0]);
						outConnection.setBaudRate(outSettings[1]);
						outConnection.setDataBits(outSettings[2]);
						outConnection.setParity(outSettings[3]);
						outConnection.setStopBits(outSettings[4]);
						outConnection.setFlowControl(outSettings[5]);
						outConnection.setDecodeFormat(outSettings[6]);
						
						connections[inIndex] = outConnection;
					} else {
						// error processing from nativeGetConnectionSettings
						status = new Status(Status.ERROR,Activator.PLUGIN_ID, (int)ret, TCErrorConstants.getErrorMessage(ret), null);
					}
				} else if (type[0].compareToIgnoreCase("tcp") == 0) {
					ITCRealTCPConnection outConnection = (ITCRealTCPConnection)TCFClassFactory.createITCRealTCPConnection(null, null);
					String[] outType = new String[1];
					long[] outOptions = new long[3];
					String[] outSettings = new String[3];
					ret = nativeGetConnectionSettings(inIndex, outType, outOptions, outSettings);
					if (ret == TCErrorConstants.TCAPI_ERR_NONE) {
						outConnection.setConnectionType(outType[0]);
						outConnection.setRetryInterval(outOptions[0]);
						outConnection.setRetryTimeout(outOptions[1]);
						outConnection.setIpAddress(outSettings[0]);
						outConnection.setPort(outSettings[1]);
						outConnection.setDecodeFormat(outSettings[2]);
						
						connections[inIndex] = outConnection;
					} else {
						// error processing from nativeGetConnectionSettings
						status = new Status(Status.ERROR,Activator.PLUGIN_ID, (int)ret, TCErrorConstants.getErrorMessage(ret), null);
					}
				} else if (type[0].compareToIgnoreCase("usb") == 0) {
					// Finish this sometime when real USB is used on PC
				} else {
					// Add other connection types here
				}
			}
		}
		return connections;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCAPIConnection#getInputStream()
	 */
	public ITCMessageInputStream getInputStream() {
		return this.inputStream;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCAPIConnection#getVersions()
	 */
	public ITCVersion[] getVersions() {
		IStatus status = statusOK;
		ITCVersion[] versions = null;
		status = checkConnected();
		if (status.isOK()) {
			// we're connected call native
			long numberVersions = nativeGetNumberVersionEntities(this.cookie.getClientId());
			if (numberVersions > 0) {
				versions = new TCVersion[(int)numberVersions];
				String[] versStrings = new String[(int)numberVersions];
				long numberGotten = nativeGetVersion(this.cookie.getClientId(), numberVersions, versStrings);
				for (int i = 0; i < numberGotten; i++) {
					versions[i] = new TCVersion(versStrings[i]);
				}
			}
		}
		return versions;
	}
	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCAPIConnection#removeErrorListener(com.nokia.tcf.api.ITCErrorListener)
	 */
	public IStatus removeErrorListener(ITCErrorListener inErrorListener) {
		IStatus status = statusOK;
//		System.out.printf("removeErrorListener\n");
		this.errorListenerList.remove(inErrorListener);
		return status;
	}
	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCAPIConnection#sendMessage(com.nokia.tcf.api.ITCMessage)
	 */
	public IStatus sendMessage(ITCMessage inMessage) {
		IStatus status = statusOK;
		status = checkConnected();
		if (status.isOK()) {
			// we are connected are we processing?
			if (this.cookie.isMessageProcessing()) {
				// we are running, check message
				status = checkMessage(inMessage);
				if (status.getSeverity() != Status.ERROR) {
					// Status.OK or Status.WARNING (still attempt to send on warning)
					// Encode if option is set

					long[] formattingOptions = new long[5];
					formattingOptions[0] = 0;
					formattingOptions[1] = messageOptions.getMessageEncodeFormat(); // add protocol or not
					formattingOptions[2] = messageOptions.getOSTVersion();			// OST version byte to use if OST
					formattingOptions[3] = inMessage.getMyMessageId();				// message ID to use of adding protocol
					formattingOptions[4] = (inMessage.isUseMyMessageId() == true) ? 1 : 0; // use my ID or not
					String[] settings = new String[1];
					settings[0] = connection.getDecodeFormat().toLowerCase();
					try {
						long ret = TCErrorConstants.TCAPI_ERR_NONE;
						if (inMessage == null) { // OK if we're just sending a header
							ret = nativeSendMessage(this.cookie.getClientId(), formattingOptions, settings, null);
						} else {
							ret = nativeSendMessage(this.cookie.getClientId(), formattingOptions, settings, inMessage.getMessage());
						}
						if (ret != TCErrorConstants.TCAPI_ERR_NONE) {
							status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)ret, TCErrorConstants.getErrorMessage(ret), null);
						}
					} catch (Exception e) {
						// exception is thrown ONLY when err = TCAPI_ERR_COMM_ERROR from native
						// message is OS Error
						String msg = e.getMessage();
						long ret = TCErrorConstants.TCAPI_ERR_COMM_ERROR;
						String tcErr = String.format("%s OSError: %s", TCErrorConstants.getErrorMessage(ret), msg);
						status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)ret, tcErr, null);
					}
				}
			} else {
				// we are stopped
				status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)TCErrorConstants.TCAPI_ERR_ROUTING_STOPPED,
						TCErrorConstants.getErrorMessage(TCErrorConstants.TCAPI_ERR_ROUTING_STOPPED), null);
			}
		}
		return status;
	}
	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCAPIConnection#setTCMessageIds(com.nokia.tcf.api.ITCMessageIds)
	 */
	public IStatus setTCMessageIds(ITCMessageIds inMessageIds) {
		IStatus status = statusOK;
		status = checkMessageIds(inMessageIds);
		if (status.isOK()) {
			// input is OK
			status = checkConnected();
			if (status.isOK()) {
				// connected
				if (this.cookie.isMessageProcessing()) {
					// must be stopped to set new IDs
					status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)TCErrorConstants.TCAPI_ERR_ROUTING_IN_PROGRESS,
							TCErrorConstants.getErrorMessage(TCErrorConstants.TCAPI_ERR_ROUTING_IN_PROGRESS), null);
				}
			}
		}
		if (status.isOK()) {
			// everything OK, so we can save our IDs and register them
			this.messageIds = inMessageIds;
			int size = (int)inMessageIds.size();
			byte[] messageids = new byte[size];
			for (int i = 0; i < size; i++) {
				messageids[0] = inMessageIds.getMessageIds().get(i);
			}
			long ret = nativeSetMessageIds(this.cookie.getClientId(), messageids);
			if (ret != TCErrorConstants.TCAPI_ERR_NONE) {
				status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)ret,
						TCErrorConstants.getErrorMessage(ret), null);
			}
		}
		return status;
	}
	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCAPIConnection#start()
	 */
	public IStatus start() {
		IStatus status = statusOK;
		// check connected
		status = checkConnected();
		if (status.isOK()) {
			if (this.cookie.isMessageProcessing() == false) {
				// call native
				long ret = nativeStart(this.cookie.getClientId());
				if (ret == TCErrorConstants.TCAPI_ERR_NONE) {
					this.cookie.setMessageProcessing(true);
				} else {
					status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)ret, TCErrorConstants.getErrorMessage(ret), null);
				}
			}
		}
		
		return status;
	}
	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCAPIConnection#stop()
	 */
	public IStatus stop() {
		IStatus status = statusOK;
		status = checkConnected();
		if (status.isOK()) {
			if (this.cookie.isMessageProcessing()) {
				long ret = nativeStop(this.cookie.getClientId());
				if (ret == TCErrorConstants.TCAPI_ERR_NONE) {
					this.cookie.setMessageProcessing(false);
				} else {
					status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)ret, TCErrorConstants.getErrorMessage(ret), null);
				}
			}
		}
		return status;
	}
	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCAPIConnection#testConnection()
	 */
	public IStatus testConnection() {
		IStatus status = statusOK;
		status = checkConnected();
		if (status.isOK()) {
			long ret = nativeTestConnection(this.cookie.getClientId());
			if (ret != TCErrorConstants.TCAPI_ERR_NONE) {
				status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)(ret), TCErrorConstants.getErrorMessage((int)ret), null);
			}
		}
		return status;
	}
	protected IStatus doTestConnection(String type, String[] settings, ITCConnection inConnection) {
		IStatus status = statusOK;
		// test connection
		long[] options = new long[3];
		options[0] = inConnection.getRetryInterval();		// connection options
		options[1] = inConnection.getRetryTimeout();
		options[2] = 0;
		long ret = nativeTestConnection(type, options, settings);
		if (ret != TCErrorConstants.TCAPI_ERR_NONE) {
			status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)ret, TCErrorConstants.getErrorMessage(ret), null);
		}
		return status;
	}
	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCAPIConnection#testConnection(com.nokia.tcf.api.ITCConnection)
	 */
	public IStatus testConnection(ITCConnection inConnection) {
		IStatus status = statusOK;
		String[] settings = null;
		String type = inConnection.getConnectionType();
		if (type.compareToIgnoreCase("serial") == 0) {
			settings = new String[7];
			ITCRealSerialConnection s = (ITCRealSerialConnection)inConnection;
			settings[0] = s.getComPort();
			settings[1] = s.getBaudRate();
			settings[2] = s.getDataBits();
			settings[3] = s.getParity();
			settings[4] = s.getStopBits();
			settings[5] = s.getFlowControl();
			settings[6] = s.getDecodeFormat().toLowerCase();
		} else if (type.compareToIgnoreCase("tcp") == 0) {
			settings = new String[3];
			ITCRealTCPConnection s = (ITCRealTCPConnection)inConnection;
			settings[0] = s.getIpAddress();
			settings[1] = s.getPort();
			settings[2] = s.getDecodeFormat().toLowerCase();
		} else if (type.compareToIgnoreCase("usb") == 0) {
			settings = new String[1];
			// finish this sometime when real USB is used on PC
		} else if (type.compareToIgnoreCase("virtualserial") == 0) {
			settings = new String[2];
			ITCVirtualSerialConnection s = (ITCVirtualSerialConnection)inConnection;
			settings[0] = s.getComPort();
			settings[1] = s.getDecodeFormat().toLowerCase();
		} else {
			// add new connections here
			long err = TCErrorConstants.TCAPI_ERR_MEDIA_NOT_SUPPORTED;
			status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)err, TCErrorConstants.getErrorMessage((int)err), null);
		}
		if (status.isOK()) {
			status = doTestConnection(type, settings, inConnection);
/*			// test connection
			long[] options = new long[3];
			options[0] = inConnection.getRetryInterval();		// connection options
			options[1] = inConnection.getRetryTimeout();
			options[2] = inConnection.getDecodeFormat();
			long ret = nativeTestConnection(type, options, settings);
			if (ret != TCErrorConstants.TCAPI_ERR_NONE) {
				status = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)ret, TCErrorConstants.getErrorMessage(ret), null);
			} */
		}
		return status;
	}
	public IStatus clearMessageFile() {
		IStatus status = statusOK;
		IStatus connStatus = checkConnected();
		if (connStatus.isOK()) {
			if (messageOptions.getMessageDestination() == ITCMessageOptions.DESTINATION_CLIENTFILE) {
				if (messageOptions.getMessageFile() != null) {
					long ret = nativeClearFile(cookie.getClientId());
					if (ret != TCErrorConstants.TCAPI_ERR_NONE) {
						status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, (int)ret, TCErrorConstants.getErrorMessage(ret), null);
					}
				}
			}
		}
		return status;
	}

	// natives
	// connect/disconnect
	protected native long nativeConnect(String inType, long[] inOptions, String[] inSettings, long[] inMessageOptions, String inFilePath, long[] outClientId);
	protected native long nativeDisconnect(long inClientId); 
	// connections
	protected native long nativeGetNumberConnections(long[] outNumber);
	protected native long nativeGetTypeOfConnection(long inIndex, String[] outType);
	protected native long nativeGetConnectionSettings(long inIndex, String[] outType, long[] outOptions, String[] outSettings);
	// port handling errors
	public native boolean nativePollError(long inClienId, int[] outErrorCode, boolean[] outHasOSErrorCode, long[] outOSErrorCode);
	// versions
	protected native long nativeGetNumberVersionEntities(long inClientId);
	protected native long nativeGetVersion(long inClientId, long inNumToGet, String[] outVersion);
	// input stream
	public native long nativePollInputStream(long inClientId, long[] outNumberTotalMessages);
	public native long nativePollInputStream2(long inClientId, long inNumberMessagesToPeek, long[] outNumberMessagesPeeked, long[] outNumberBytesPeeked);
	public native long nativeGetInputStreamMessageBytes(long inClientId, long inNumberMessagesToPeek, long[] outNumberBytesPerMessage);
	public native long nativeReadInputStream(long inClientId, long inNumberMessagesToRead, long[] outNumberMessagesRead, long[] outNumberBytesRead, long inNumberMaxBytes, byte[] outMessages);
	public native long nativeOpenInputStream(long inClientId, String inBaseFilePath, long inInputStreamSize, boolean inOverFlowToFile);
	public native long nativeCloseInputStream(long inClientId);
	// message file
	protected native long nativeClearFile(long inClientId);
	// send message
	protected native long nativeSendMessage(long inClientId, long[] inFormattingOptions, String[] inSettings, byte[] inMessage);
	// register message IDs
	protected native long nativeSetMessageIds(long inClientId, byte[] inMessageIds);
	// start/stop processing
	protected native long nativeStart(long inClientId);
	protected native long nativeStop(long inClientId);
	// test connections
	protected native long nativeTestConnection(String inType, long[] inOptions, String[] inSettings);
	protected native long nativeTestConnection(long inClientId);
	// start/stop server - done from plugin activator start/stop methods
	public native long nativeStartServer();
	public native long nativeStopServer();
}
