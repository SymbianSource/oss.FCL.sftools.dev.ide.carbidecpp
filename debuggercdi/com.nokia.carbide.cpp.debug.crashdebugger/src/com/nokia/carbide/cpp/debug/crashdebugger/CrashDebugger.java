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
package com.nokia.carbide.cpp.debug.crashdebugger;

import org.eclipse.cdt.core.IBinaryParser.IBinaryObject;
import org.eclipse.cdt.debug.core.cdi.CDIException;
import org.eclipse.cdt.debug.core.cdi.ICDISession;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.swt.widgets.Display;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import com.freescale.cdt.debug.cw.CWCorbaMgr;
import com.freescale.cdt.debug.cw.CWException;
import com.freescale.cdt.debug.cw.core.CWPlugin;
import com.freescale.cdt.debug.cw.core.Debugger;
import com.freescale.cdt.debug.cw.core.cdi.DeviceCommandSession;
import com.freescale.cdt.debug.cw.core.cdi.ISessionOutputReceiver;
import com.freescale.cdt.debug.cw.core.cdi.Session;

import cwdbg.ConnectionClient;
import cwdbg.ConnectionClientHelper;
import cwdbg.ConnectionClientPOA;
import cwdbg.ConnectionContext;
import cwdbg.ConnectionContextHolder;
import cwdbg.ConnectionEvent;
import cwdbg.IllegalAccess;
import cwdbg.Machine;
import cwdbg.PreferenceConstants;

public class CrashDebugger extends Debugger implements ISessionOutputReceiver {

	public static final String DEBUGGER_ID = "com.nokia.carbide.cpp.debug.crashdebugger.CrashDebugger"; //$NON-NLS-1$

	private static DeviceCommandSession session = null;

	private static CrashDebuggerConsole console = null;

	private static ListenerList sessionChangedListeners = new ListenerList();

	private static String lastCommandSent = ""; //$NON-NLS-1$

	public class TargetConnectionClient extends ConnectionClientPOA {

		public void NotifyEvent(Machine machin, ConnectionEvent event) {
			return;
		}
	};

	public static void addSessionChangedListener(
			ISessionChangedListener listener) {
		sessionChangedListeners.add(listener);
	}

	public static void removeSessionChangedListener(
			ISessionChangedListener listener) {
		sessionChangedListeners.remove(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.debug.core.ICDIDebugger#createDebuggerSession(org.eclipse.debug.core.ILaunch,
	 *      org.eclipse.cdt.core.IBinaryParser.IBinaryObject,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public ICDISession createDebuggerSession(ILaunch launch, IBinaryObject bin,
			IProgressMonitor monitor) throws CoreException {
		// get the global prefs which control whether or not we automatically
		// launch the DE, and if so, how long to wait for it to initialize and
		// handshake with us
		boolean bLaunchDE = CWPlugin.getDefault().getPluginPreferences()
				.getBoolean(PreferenceConstants.J_PN_AutoLaunchDE);
		// if we don't automatically launch the DE, the timeout value is
		// meaningless
		int timeout = bLaunchDE ? CWPlugin.getDefault().getPluginPreferences()
				.getInt(PreferenceConstants.J_PN_DELaunchTimeout) : 0;

		boolean failed = false;
		try {
			// no-op if previously done
			CWCorbaMgr.init(launch, bLaunchDE, timeout, monitor);

			ILaunchConfiguration cnf = launch.getLaunchConfiguration();

			ConnectionClient client = null;
			ConnectionContext context = null;

			try {
				context = CWCorbaMgr.getConnectionContext(cnf, monitor);
				String str = new String();

				str = "ARM"; //$NON-NLS-1$
				context.SetAttributeString("PROCESSOR_NAME", str); //$NON-NLS-1$
				context.SetAttributeString("INIT_FILE_PATH", ""); //$NON-NLS-1$ //$NON-NLS-2$

				// set other context attributes, if necessary, which are
				// available by debugger plugin (see BaseDebugNub::Connect()
				// for example)

			} catch (IllegalAccess e) {
				throw newCoreException(new Exception(Messages
						.getString("CrashDebugger.5"))); //$NON-NLS-1$
			} catch (CoreException e) {
				throw e;
			}

			if (client == null) {
				TargetConnectionClient lclClient = new TargetConnectionClient();
				try {
					CWCorbaMgr.getPOA().activate_object(lclClient);
					client = ConnectionClientHelper.narrow(CWCorbaMgr.getPOA()
							.servant_to_reference(lclClient));
				} catch (ServantAlreadyActive e) {
					e.printStackTrace();
				} catch (WrongPolicy e) {
					e.printStackTrace();
				} catch (ServantNotActive e) {
					e.printStackTrace();
				}
			}

			if (client == null)
				throw newCoreException(new Exception(Messages
						.getString("CrashDebugger.6"))); //$NON-NLS-1$

			ConnectionContextHolder contextHolder = new ConnectionContextHolder(
					context);

			CWCorbaMgr.connectRemote(launch, client, contextHolder, monitor);

			context = contextHolder.value;

			// This would create a dummy cwTarget and cwProcess for us.
			CWCorbaMgr.AsyncInitializeTarget(context);

			cwdbg.Machine cwMachine = context.GetMachine();

			Session existingSession = Session.getSessionForMachine(cwMachine);

			if (existingSession == null) {
				session = new CrashDebuggerSession(this, launch, context,
						client, cwMachine, this);
			} else
				session = (CrashDebuggerSession) existingSession;

			// pass on the notification to listeners
			Object[] listeners = sessionChangedListeners.getListeners();
			for (int i = 0; i < listeners.length; ++i) {
				final ISessionChangedListener l = (ISessionChangedListener) listeners[i];
				SafeRunner.run(new SafeRunnable() {
					public void run() {
						l.sessionStarted();
					}
				});
			}

			/*
			 * Things go well, fake an "Enter" key press. If crash debugger
			 * monitor on device is running, this allows user to see either
			 * command prompt (".") or password promt ("Password:"). Otherwise,
			 * this would also make sure the "liaison process" is known to crash
			 * debugger protocol plugin and thus the banner and password prompt
			 * from CD monitor can be displayed in our console when crasher
			 * occurs on device.
			 */
			sendCommand("\r\n"); //$NON-NLS-1$

			return session;
		} catch (CWException e) {
			failed = true;
			throw newCoreException(e);
		} finally {
			if (failed && session != null) {
				session.debuggingStopped(null);

				// Make sure Corba connection is shutdown so that we won't have
				// a stale connection after, say, DE is killed externally.
				if (!Session.hasActiveSession())
					CWCorbaMgr.terminate(false);
			}
		}
	}

	public static void terminateSession() {
		try {
			session.exitSession(); // kill all targets, if any
			
			session.terminate();

			session.debuggingStopped(null); // disconnect
		} catch (CDIException e) {
			e.printStackTrace();
		}
	}

	public static void sessionEnded() {
		session = null;

		// pass on the notification to listeners
		Object[] listeners = sessionChangedListeners.getListeners();
		for (int i = 0; i < listeners.length; ++i) {
			final ISessionChangedListener l = (ISessionChangedListener) listeners[i];
			SafeRunner.run(new SafeRunnable() {
				public void run() {
					l.sessionEnded();
				}
			});
		}
	}

	public static DeviceCommandSession getSession() {
		return session;
	}

	public static CrashDebuggerConsole getConsole() {
		if (console == null) {
			console = new CrashDebuggerConsole(Messages
					.getString("CrashDebugger.8")); //$NON-NLS-1$
			console.setWaterMarks(-1, -1);
		}
		return console;
	}

	public static void sendCommand(String text) {
		try {
			if (session != null) {
				// Remove the command prompt character.
				if (text.charAt(0) == '.') {
					text = text.substring(1);
				}
				// If at Password prompt..
				if (text.startsWith(Messages.getString("CrashDebugger.9"))) { //$NON-NLS-1$
					text = text.substring(10);
				}

				lastCommandSent = text;
				session.consoleInputReceived(text);
			}
		} catch (CDIException e) {
			e.printStackTrace();
		}
	}

	public void outputReceived(String text) {
		// if we hit enter we'll get "\r\n." back, which is the command prompt
		// from crash debugger monitor on device. Just display the prompt.

		// the text is returned in the form: command\r\nresponse, e.g.
		// f\r\nFault Category:.
		// we want to strip the command\r\n part
		if (text.startsWith(lastCommandSent)) {
			text = text.substring(lastCommandSent.length(), text.length());
		}

		// After each command output, crash debugger sends a "\r\n." as the
		// command prompt.
		// Sometimes there are a few junk characters after that, remove those.
		int lastPeriod = text.lastIndexOf("\r\n."); //$NON-NLS-1$
		if (lastPeriod >= 0 && lastPeriod < text.length() - 3) {
			// there's something after the "."
			text = text.substring(0, lastPeriod + 3);
		}

		final String response = text;

		// pass on the notification to listeners
		Object[] listeners = sessionChangedListeners.getListeners();
		for (int i = 0; i < listeners.length; ++i) {
			final ISessionChangedListener l = (ISessionChangedListener) listeners[i];

			Display display = Display.getCurrent();
			if (display == null) {
				display = Display.getDefault();
			}
			display.asyncExec(new Runnable() {
				public void run() {
					l.sessionOutputReceived(response);
				}
			});
		}
	}
}
