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
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.osgi.framework.Version;

import com.freescale.cdt.debug.cw.core.SerialConnectionSettings;
import com.nokia.carbide.remoteconnections.interfaces.AbstractConnectedService2;
import com.nokia.carbide.remoteconnections.interfaces.AbstractSynchronizedConnection;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionType;
import com.nokia.carbide.remoteconnections.interfaces.IService;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus.EStatus;
import com.nokia.carbide.remoteconnections.interfaces.IRemoteAgentInstallerProvider.IRemoteAgentInstaller;
import com.nokia.carbide.trk.support.Messages;
import com.nokia.carbide.trk.support.connection.IUSBConnectionType;
import com.nokia.carbide.trk.support.connection.SerialConnectionType;
import com.nokia.carbide.trk.support.connection.TCPIPConnectionFactory;
import com.nokia.carbide.trk.support.connection.TCPIPConnectionType;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Pair;
import com.nokia.tcf.api.ITCAPIConnection;
import com.nokia.tcf.api.ITCConnection;
import com.nokia.tcf.api.ITCMessage;
import com.nokia.tcf.api.ITCMessageIds;
import com.nokia.tcf.api.ITCMessageInputStream;
import com.nokia.tcf.api.ITCMessageOptions;
import com.nokia.tcf.api.TCFClassFactory;

/**
 *
 */
public class TRKConnectedService extends AbstractConnectedService2 {
	
	public static final String PROP_SYS_TRK = "is-system-trk"; //$NON-NLS-1$
	
	static {
		try {
			System.loadLibrary("GetTRKVersion"); //$NON-NLS-1$
		} catch (UnsatisfiedLinkError e) {
			// no such library, e.g., not on Windows or in a misconfigured dev layout
			e.printStackTrace();
		}
	}
	
	public native static void getTRKVersionFromSerial(String portName, 
			int baud, int dataBits, int parity, int stopBits, int flowControl,
			int[] version) throws Exception;

	private static final String OK_STATUS = 
		Messages.getString("TRKConnectedService.OKStatus"); //$NON-NLS-1$
	private static final String IS_LASTEST = 
		Messages.getString("TRKConnectedService.IsLatestStatus"); //$NON-NLS-1$
	private static final String NEEDS_INSTALL = 
		Messages.getString("TRKConnectedService.NeedsInstallStatus1") + //$NON-NLS-1$
		Messages.getString("TRKConnectedService.NeedsInstallStatus2"); //$NON-NLS-1$
	private static final String ERROR = 
		Messages.getString("TRKConnectedService.ErrorStatus"); //$NON-NLS-1$
	private static final String PORT_IN_USE =
		Messages.getString("TRKConnectedService.PortInUseStatus"); //$NON-NLS-1$
	private static final String TCPIP_IN_USE =
		Messages.getString("TRKConnectedService.TCPIPInUseStatus"); //$NON-NLS-1$
	
	private static final Version VERSIONS3_VERSION = new Version(3, 2, 4);
	private static final byte[] TRK_PING = {0x7e, 0x0, 0x0, (byte) 0xff, 0x7e};
	private static final byte[] TRK_VERSION = {0x7e, 0x08, 0x01, (byte) 0xf6, 0x7e};
	private static final byte[] TRK_VERSIONS3 = {0x7e, 0x51, 0x02, (byte) 0xac, 0x7e};
	private static final int SYS_TRK_RESPONSE_STR_OFFSET = 10;
	private static final byte[] SYS_TRK_RESPONSE_STR = 
					{0x0a, 0x53, 0x79, 0x73, 0x74, 0x65, 0x6d, 0x20, 0x54, 0x52, 0x4b};
	private static final int REQUIRED_MSG_LEN = 
					SYS_TRK_RESPONSE_STR.length + SYS_TRK_RESPONSE_STR_OFFSET;
	
	private Pair<String, Version> deviceOS;
	private TRKService trkService;
	static Class<?> startTCFServer = TCFClassFactory.class; // force the tcf plugin to load

	public TRKConnectedService(IService service, AbstractSynchronizedConnection connection) {
		super(service, connection);
		Check.checkContract(service instanceof TRKService);
		trkService = (TRKService) service;
		currentStatus = new Status();
	}

	public IStatus getStatus() {
		return currentStatus;
	}

	private String getSimplePortName(int portNum) {
		return "COM" + portNum; //$NON-NLS-1$
	}

	private String getPortNameForSerial(int portNum) {
		String portNamePrefix = portNum > 9 ? "\\\\.\\" : ""; //$NON-NLS-1$ //$NON-NLS-2$
		return portNamePrefix + getSimplePortName(portNum);
	}
	
	private Version getTRKVersionFromDevice() throws ConnectionFailException {
		if (connection.getConnectionType() instanceof SerialConnectionType) {
			int[] versInts = {0,0,0,0};
			String portNumStr = connection.getSettings().get(SerialConnectionSettings.PORT);
			int portNum = Integer.parseInt(portNumStr);
			int baud = getIndexValue(SerialConnectionSettings.BAUD);
			int dataBits = getIndexValue(SerialConnectionSettings.DATA_BITS);
			int parity = getIndexValue(SerialConnectionSettings.PARITY);
			int stopBits = getIndexValue(SerialConnectionSettings.STOP_BITS);
			int flowControl = getIndexValue(SerialConnectionSettings.FLOW_CONTROL);
			try {
				getTRKVersionFromSerial(getPortNameForSerial(portNum), baud, dataBits, parity, stopBits, flowControl, versInts);
			}
			catch (Exception e) {
				throw new ConnectionFailException(e.getMessage());
			}
			if (versInts[3] > 0) {
				boolean isSysTrk = versInts[3] > 1;
				getProperties().put(PROP_SYS_TRK, Boolean.valueOf(isSysTrk).toString());
			}
			return new Version(versInts[0], versInts[1], versInts[2]);
		}
		else if (connection.getConnectionType() instanceof IUSBConnectionType) {
			String portNumStr = connection.getSettings().get(SerialConnectionSettings.PORT);
			int portNum = Integer.parseInt(portNumStr);
			return getTRKVersionFromOSTUSB(getSimplePortName(portNum));
		}
		return null;
	}

	private Version getTRKVersionFromOSTUSB(String port) throws ConnectionFailException {
		ITCConnection conn = TCFClassFactory.createITCVirtualSerialConnection(port);
		conn.setDecodeFormat("ost"); //$NON-NLS-1$
		return getTRKVersionUsingTCF(conn, (byte)0x90, (byte)0x90);
	}
	
	private Version getTRKVersionUsingTCF(ITCConnection conn, byte trkResponseId, byte trkRequestId) throws ConnectionFailException {
//		System.out.println("entering getTRKVersionUsingTCF");
		Version version = null;
		
		ITCMessageOptions options = TCFClassFactory.createITCMessageOptions();
		options.setMessageEncodeFormat(ITCMessageOptions.ENCODE_FORMAT); // we send raw trk
		options.setUnWrapFormat(ITCMessageOptions.UNWRAP_DELETE_HEADERS); // return raw trk
		options.setInputStreamSize(64);
		
		// message Ids to capture (we must capture TRK response messages = id=0x45)
		ITCMessageIds ids = TCFClassFactory.createITCMessageIds();
		ids.addMessageId(trkResponseId); // TRK response
		
		ITCMessage tcMsgPing = TCFClassFactory.createITCMessage(TRK_PING);
		tcMsgPing.setUseMyMessageId(true, trkRequestId);
		
		ITCMessage tcMsgVersion = TCFClassFactory.createITCMessage(TRK_VERSION);
		tcMsgVersion.setUseMyMessageId(true, trkRequestId);
		
		ITCMessage tcMsgVersions3 = TCFClassFactory.createITCMessage(TRK_VERSIONS3);
		tcMsgVersions3.setUseMyMessageId(true, trkRequestId);
		
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
						/*ITCMessage message =*/ stream.readMessage(); // ping response
					}
					else
						throw new ConnectionFailException(Messages.getString("TRKConnectedService.NoPingError")); //$NON-NLS-1$
					
					// send trk version
					sendStatus = api.sendMessage(tcMsgVersion);
	
					waitForSingleTCMessage(stream);
					if (stream.peekMessages() > 0) {
						ITCMessage tcMessage = stream.readMessage(); // version response
						byte[] message = tcMessage.getMessage();
//						printMessage(message);
						if (message != null && message.length == 11) {
							version = new Version(message[4], message[5], message[8]);
							if (version.compareTo(VERSIONS3_VERSION) >= 0) {
								// send trk versions3
								sendStatus = api.sendMessage(tcMsgVersions3);
								
								waitForSingleTCMessage(stream);
								if (stream.peekMessages() > 0) {
									tcMessage = stream.readMessage(); // version response
									message = tcMessage.getMessage();
//									printMessage(message);
									boolean isSysTrk = false;
									if (message.length >= REQUIRED_MSG_LEN) {
										byte[] trkStr = new byte[SYS_TRK_RESPONSE_STR.length];
										System.arraycopy(message, SYS_TRK_RESPONSE_STR_OFFSET, trkStr, 0, trkStr.length);
//										printMessage(trkStr);
//										printMessage(SYS_TRK_RESPONSE_STR);
										isSysTrk = Arrays.equals(trkStr, SYS_TRK_RESPONSE_STR);
									}
									getProperties().put(PROP_SYS_TRK, Boolean.valueOf(isSysTrk).toString());
								}
							}
						}
						else
							throw new ConnectionFailException(Messages.getString("TRKConnectedService.BadVersionResponseError")); //$NON-NLS-1$
					}
					else
						throw new ConnectionFailException(Messages.getString("TRKConnectedService.NoPingErrorNoVersionError")); //$NON-NLS-1$
				}
				else
					throw new ConnectionFailException(sendStatus.getMessage());
			}
			catch (IOException e) {
				throw new ConnectionFailException(e.getMessage());
			}
			finally {
				api.disconnect();
//				System.out.println("leaving getTRKVersionUsingTCF");
			}
		}
		else
			throw new ConnectionFailException(connStatus.getMessage());
		
		return version;
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

	private int getIndexValue(String key) {
		String val = connection.getSettings().get(key);
		return SerialConnectionSettings.toIndex(key, val);
	}
	
	private Version getLatestVersionFromServer() {
		if (deviceOS != null) {
			List<IRemoteAgentInstaller> installers = 
				trkService.getInstallerProvider().getRemoteAgentInstallers(deviceOS.first, deviceOS.second);
			if (installers != null && !installers.isEmpty()) {
				// use the first one
				return installers.get(0).getInstallerVersion();
			}
		}
		
		return null;
	}

	private String getShortDescription(EStatus estatus) {
		switch (estatus) {
		case UP:
			return Messages.getString("TRKConnectedService.RunningLabel"); //$NON-NLS-1$
		case DOWN:
			return Messages.getString("TRKConnectedService.DownLabel"); //$NON-NLS-1$
		case IN_USE:
			return Messages.getString("TRKConnectedService.InUseLabel"); //$NON-NLS-1$
		}
		return Messages.getString("TRKConnectedService.UnknownLabel"); //$NON-NLS-1$
	}
	
	protected TestResult runTestStatus(IProgressMonitor monitor) {
		String message = null;
		EStatus estatus = EStatus.DOWN;
		monitor.beginTask(Messages.getString("TRKConnectedService.TaskLabel"), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
		
		synchronized (connection) {
			boolean didTest = false;
			try {
				if (connection.isInUse()) {
					estatus = EStatus.IN_USE;
					IConnectionType connectionType = connection.getConnectionType();
					if (connectionType instanceof SerialConnectionType ||
							connectionType instanceof IUSBConnectionType)
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
					Version deviceVersion = getTRKVersionFromDevice();
					if (deviceVersion != null) {
						estatus = EStatus.UP;
						Version serverVersion = getLatestVersionFromServer();
						if (serverVersion == null) {
							StringBuilder trkVersionString = new StringBuilder();
							String sysTrkProp = getProperties().get(PROP_SYS_TRK);
							if (sysTrkProp != null)
								trkVersionString.append(Boolean.parseBoolean(sysTrkProp) ? 
										Messages.getString("TRKConnectedService.SysTRKName") : //$NON-NLS-1$
											Messages.getString("TRKConnectedService.AppTRKName")); //$NON-NLS-1$
							trkVersionString.append(deviceVersion.toString());
							message = MessageFormat.format(OK_STATUS, trkVersionString);
						}
						else if (deviceVersion.compareTo(serverVersion) >= 0) {
							message = MessageFormat.format(IS_LASTEST, deviceVersion.toString());
						}
						else {
							message = MessageFormat.format(NEEDS_INSTALL,
											deviceVersion, serverVersion);
						}
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
	
	public void setDeviceOS(String familyName, Version version) {
		deviceOS = new Pair<String, Version>(familyName, version);
	}
}
