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
 *				Deniz TURAN
 * Description: 
 * 				
 */
package com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;

import com.nokia.carbide.cpp.sysdoc.hover.Activator;

/**
 * Provides a central login facilities. It uses Activator's logs. Exceptions can
 * be seen in Error view.
 */
public class Logger {

	public static void logDebug(final Throwable t) {
		if (isDebug()) {
			t.printStackTrace();
			logDebug(t.toString());
		}
	}

	public static void logDebug(final String msg) {
		if (isDebug()) {
			System.out.println(msg);
		}
	}

	public static void logSysOut(final long msg) {
		logDebug(Long.toString(msg));
	}

	public static void logError(final Exception e) {
		logDebug(e);
		logSystem(e, IStatus.ERROR);
	}

	public static void logError(final Exception e, String msg) {
		logDebug(e);
		logSystem(e, msg, IStatus.ERROR);
	}

	public static void logWarn(String msg) {
		logSystem(msg, IStatus.WARNING);
	}

	public static void logInfo(String msg) {
		logSystem(msg, IStatus.INFO);
	}

	public static void printTrace(final String msg) {
		try {
			throw new Exception(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean isDebug() {
		if (Platform.inDebugMode()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Log an Exception to the eclipse log
	 * 
	 * @param e
	 */
	private static void logSystem(Exception e, String msg, final int logType) {
		Activator.getDefault().getLog().log(
				new Status(logType, Activator.PLUGIN_ID, e.getMessage() + msg,
						e));
	}

	private static void logSystem(Exception e, final int logType) {
		logSystem(e, "", logType);
	}

	private static void logSystem(String msg, final int logType) {
		Activator.getDefault().getLog().log(
				new Status(logType, Activator.PLUGIN_ID, msg));
	}

}
