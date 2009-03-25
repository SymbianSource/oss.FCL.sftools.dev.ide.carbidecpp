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
package com.nokia.cpp.internal.api.utils.core;

import org.eclipse.core.runtime.*;

import java.text.MessageFormat;
import java.util.*;

public class StatusBuilder {
	
	List<IStatus> statusList = new ArrayList<IStatus>();
	String pluginID;
	int defaultCode = 1;
	int okCount;
	int cancelCount;
	int infoCount;
	int warningCount;
	int errorCount;

	public StatusBuilder(Plugin plugin) {
        if (plugin != null)
            pluginID = plugin.getBundle().getSymbolicName();
        else {
            Check.checkState(!Platform.isRunning());
            pluginID = "<no plugin>"; //$NON-NLS-1$
        }
	}
	
	public StatusBuilder(String pluginId) {
		this.pluginID = pluginId;
	}
	
	public void reset() {
		statusList.clear();
		okCount = 0;
		cancelCount = 0;
		infoCount = 0;
		warningCount = 0;
		errorCount = 0;
	}
	
	public void add(int severity, String msgPattern, Object[] msgParams) {
		add(severity, defaultCode, msgPattern, msgParams, null);
	}
	
	public void add(int severity, int code, 
				String msgPattern, Object[] msgParams, Throwable t) {
		String message = MessageFormat.format(msgPattern, msgParams);
		Status s = new Status(severity, pluginID, code, message, t);
		add(s);
	}
	
	public void add(IStatus status) {
		int severity = status.getSeverity();
		switch (severity) {
			case IStatus.OK:
				++okCount;
				break;
			case IStatus.INFO:
				++infoCount;
				break;
			case IStatus.WARNING:
				++warningCount;
				break;
			case IStatus.ERROR:
				++errorCount;
				break;
			case IStatus.CANCEL:
				++cancelCount;
				break;
		}
		statusList.add(status);
	}
	
	public MultiStatus createMultiStatus(String msgPattern, Object params[]) {
		String msg = MessageFormat.format(msgPattern, params);
		MultiStatus result = new MultiStatus(pluginID, defaultCode,
				msg, null);
		for (Iterator iter = statusList.iterator(); iter.hasNext();) {
			IStatus element = (IStatus) iter.next();
			result.add(element);
		}
		return result;
	}
	
	/**
	 * Returns a MultiStatus, a single IStatus or null,
	 * depending on the contents.  The msgPattern and params
	 * parameters are used only if there is >1 IStatus and a MultiStatus
	 * is created.
	 */
	public IStatus createStatus(String msgPattern, Object params[]) {
		IStatus result = null;
		if (statusList.size() == 1)
			result = (IStatus) statusList.get(0);
		else if (statusList.size() > 1)
			result = createMultiStatus(msgPattern, params);
		return result;
	}
	
	public List getStatusList() {
		return statusList;
	}
	
	public int getTotalCount() {
		return statusList.size();
	}

	public int getCancelCount() {
		return cancelCount;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public int getInfoCount() {
		return infoCount;
	}

	public int getOkCount() {
		return okCount;
	}

	public int getWarningCount() {
		return warningCount;
	}

	public static String getMergedMessage(IStatus status, String delim) {
		Check.checkArg(status != null);
		Check.checkArg(delim != null);
		Check.checkState(!status.isOK()); // no message needed if status == OK
		
		String message = status.getMessage();
		if (status.isMultiStatus()) {
			IStatus[] children = ((MultiStatus) status).getChildren();
			for (int i = 0; i < children.length; i++) {
				message += delim;
				message += children[i].getMessage(); 
			}
		}
		
		return message;
	}
}
