/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.trk.support.service;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;

import org.eclipse.core.runtime.IProgressMonitor;

import com.freescale.cdt.debug.cw.core.SerialConnectionSettings;
import com.nokia.carbide.remoteconnections.interfaces.AbstractConnectedService2;
import com.nokia.carbide.remoteconnections.interfaces.AbstractSynchronizedConnection;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus.EStatus;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionType;
import com.nokia.carbide.remoteconnections.interfaces.IService;
import com.nokia.carbide.trk.support.Messages;
import com.nokia.carbide.trk.support.connection.IUSBConnectionType;
import com.nokia.carbide.trk.support.connection.TCPIPConnectionFactory;
import com.nokia.carbide.trk.support.connection.TCPIPConnectionType;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.tcf.api.ITCAPIConnection;
import com.nokia.tcf.api.ITCConnection;
import com.nokia.tcf.api.ITCMessage;
import com.nokia.tcf.api.ITCMessageIds;
import com.nokia.tcf.api.ITCMessageInputStream;
import com.nokia.tcf.api.ITCMessageOptions;
import com.nokia.tcf.api.TCFClassFactory;

public class TracingConnectedService extends AbstractConnectedService2 {
	
	private static final String OK_STATUS = 
		Messages.getString("TracingConnectedService.OKStatus"); //$NON-NLS-1$
	private static final String ERROR = 
		Messages.getString("TracingConnectedService.ErrorStatus"); //$NON-NLS-1$
	private static final String PORT_IN_USE =
		Messages.getString("TracingConnectedService.PortInUseStatus"); //$NON-NLS-1$
	private static final String TCPIP_IN_USE =
		Messages.getString("TracingConnectedService.TCPIPInUseStatus"); //$NON-NLS-1$
	
	private static final byte[] TC_REQ_OST = {0x0, 0x0, 0x0, 0x3, 0x0, 0x0, 0x1};
	private static final byte[] TC_RESP_OST = {0x0, 0x0, 0x0, 0x5, 0x0, 0x1, 0x0, 0x0, 0x1};
	static Class<?> startTCFServer = TCFClassFactory.class; // force the tcf plugin to load

	public interface IMessageValidator {
		boolean isValidMessage(byte[] message);
	}

	public TracingConnectedService(IService service, AbstractSynchronizedConnection connection) {
		super(service, connection);
		Check.checkContract(service instanceof TracingService);
		currentStatus = new Status();
	}
	
	public IStatus getStatus() {
		return currentStatus;
	}
	
	private String getSimplePortName(int portNum) {
		return "COM" + portNum; //$NON-NLS-1$
	}

	private boolean getTraceCoreResponse() throws ConnectionFailException {
		if (connection.getConnectionType() instanceof IUSBConnectionType) {
			String portNumStr = connection.getSettings().get(SerialConnectionSettings.PORT);
			int portNum = Integer.parseInt(portNumStr);
			return getTraceCoreResponseFromOSTUSB(getSimplePortName(portNum));
		}
		return false;
	}

	private boolean getTraceCoreResponseFromOSTUSB(String port) throws ConnectionFailException {
		ITCConnection conn = TCFClassFactory.createITCVirtualSerialConnection(port);
		conn.setDecodeFormat("ost"); //$NON-NLS-1$
		return getTraceCoreResponseUsingTCF(conn, true, (byte)0x0, (byte)0x0, TC_REQ_OST,
				new IMessageValidator() {
					public boolean isValidMessage(byte[] message) {
						return message != null && Arrays.equals(message, TC_RESP_OST);
					}
		});
	}
	
	private boolean getTraceCoreResponseUsingTCF(ITCConnection conn, boolean sendHeaders,
			byte tcResponseId, byte tcRequestId, byte[] inMessage, IMessageValidator validator) throws ConnectionFailException {
//		System.out.println("entering getTraceCoreResponseUsingTCF");
		
		ITCMessageOptions options = TCFClassFactory.createITCMessageOptions();
		if (sendHeaders) {
			options.setMessageEncodeFormat(ITCMessageOptions.ENCODE_NO_FORMAT);
			options.setUnWrapFormat(ITCMessageOptions.UNWRAP_LEAVE_HEADERS);
		}
		else {
			options.setMessageEncodeFormat(ITCMessageOptions.ENCODE_FORMAT);
			options.setUnWrapFormat(ITCMessageOptions.UNWRAP_DELETE_HEADERS);
		}
		options.setInputStreamSize(64);
		
		ITCMessageIds ids = TCFClassFactory.createITCMessageIds();
		ids.addMessageId(tcResponseId);
		
		ITCMessage tcMsgPing = TCFClassFactory.createITCMessage(inMessage);
		if (!sendHeaders) {
			tcMsgPing.setUseMyMessageId(true, tcRequestId);
		}
		
		// connect
		ITCAPIConnection api = TCFClassFactory.createITCAPIConnection();
		org.eclipse.core.runtime.IStatus connStatus = api.connect(conn, options, ids);

		// get a reference to the input stream
		ITCMessageInputStream stream = api.getInputStream();
		
		// send trk ping
		if (connStatus.isOK()) {
			try {
				org.eclipse.core.runtime.IStatus sendStatus = api.sendMessage(tcMsgPing);
				if (sendStatus.isOK()) {
					waitForSingleTCMessage(stream);
					
					if (stream.peekMessages() > 0) {
						ITCMessage tcMessage = stream.readMessage(); // version response
						byte[] message = tcMessage.getMessage();
//						printMessage(message);
						if (!validator.isValidMessage(message)) {
							throw new ConnectionFailException(Messages.getString("TracingConnectedService.BadVersionResponseError")); //$NON-NLS-1$
						}
					}
					else
						throw new ConnectionFailException(Messages.getString("TracingConnectedService.TimedOutError")); //$NON-NLS-1$
				}
				else
					throw new ConnectionFailException(sendStatus.getMessage());
			}
			catch (IOException e) {
				throw new ConnectionFailException(e.getMessage());
			}
			finally {
				api.disconnect();
//				System.out.println("leaving getTraceCoreResponseUsingTCF");
			}
		}
		else
			throw new ConnectionFailException(connStatus.getMessage());
		
		return true;
	}

//	private void printMessage(byte[] message) {
//		for (int i = 0; i < message.length; i++) {
//			String hexString = Integer.toHexString(message[i]);
//			if (hexString.length() == 1)
//				hexString = "0" + hexString;
//			else if (hexString.length() > 2)
//				hexString = hexString.substring(hexString.length() - 2);
//			System.out.print(hexString);
//			if (i + 1 < message.length)
//				System.out.print('-');
//		}
//		System.out.println();
//	}

	private void waitForSingleTCMessage(ITCMessageInputStream stream) throws IOException {
		int timeout = TIMEOUT;
		while (stream.peekMessages() == 0) {
			try {
				Thread.sleep(200);
				timeout -= 200;
				if (timeout <= 0)
					break;
			} catch (InterruptedException e) {
			}
		}
	}

	private String getShortDescription(EStatus estatus) {
		switch (estatus) {
		case UP:
			return Messages.getString("TracingConnectedService.RunningLabel"); //$NON-NLS-1$
		case DOWN:
			return Messages.getString("TracingConnectedService.DownLabel"); //$NON-NLS-1$
		case IN_USE:
			return Messages.getString("TracingConnectedService.InUseLabel"); //$NON-NLS-1$
		}
		return Messages.getString("TracingConnectedService.UnknownLabel"); //$NON-NLS-1$
	}
	
	protected TestResult runTestStatus(IProgressMonitor monitor) {
		String message = null;
		EStatus estatus = EStatus.DOWN;
		monitor.beginTask(Messages.getString("TracingConnectedService.TaskLabel"), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
		
		synchronized (connection) {
			boolean didTest = false;
			try {
				if (connection.isInUse()) {
					estatus = EStatus.IN_USE;
					IConnectionType connectionType = connection.getConnectionType();
					if (connectionType instanceof IUSBConnectionType)
						message = MessageFormat.format(PORT_IN_USE, 
								connection.getSettings().get(SerialConnectionSettings.PORT));
					else if (connectionType instanceof TCPIPConnectionType)
						message = MessageFormat.format(TCPIP_IN_USE, 
								connection.getSettings().get(TCPIPConnectionFactory.IP_ADDRESS),
								connection.getSettings().get(TCPIPConnectionFactory.IP_PORT));
				}
				else {
					didTest = true;
					connection.setServiceTestingAndInUse(true);
					boolean ok = getTraceCoreResponse();
					if (ok) {
						estatus = EStatus.UP;
						message = OK_STATUS;
					}
				}
			} 
			catch (Exception e) {
				message = ERROR + e.getMessage();
				estatus = EStatus.DOWN;
			}
			finally {
				if (didTest)
					connection.setServiceTestingAndInUse(false);
			}
		}
		monitor.done();
		
		return new TestResult(estatus, getShortDescription(estatus), message);
	}
}