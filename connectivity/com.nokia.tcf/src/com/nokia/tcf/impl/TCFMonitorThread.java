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

import com.nokia.tcf.api.TCErrorConstants;

public class TCFMonitorThread extends Thread {

	TCAPIConnection api = null;
	boolean stop = false;

	@Override
	public void run() {
		while (true) {
			if (stop == true) {
				return;
			}
			int[] tcfErrorCode = new int[1];
			long[] osErrorCode = new long[1];
			boolean[] hasOSErrorCode = new boolean[1];
			boolean found = api.nativePollError(api.getClientId(), tcfErrorCode, hasOSErrorCode, osErrorCode);
			String errString = null;
			if (found) {
				if (hasOSErrorCode[0]) {
					errString = String.format(TCErrorConstants.getErrorMessage(tcfErrorCode[0]), osErrorCode[0]);
				} else {
					errString = TCErrorConstants.getErrorMessage(tcfErrorCode[0]);
				}
				api.fireErrorListeners(tcfErrorCode[0], errString);
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
	}

	public TCFMonitorThread(TCAPIConnection api, String name) {
		super(name);
		this.api = api;
	}
}
