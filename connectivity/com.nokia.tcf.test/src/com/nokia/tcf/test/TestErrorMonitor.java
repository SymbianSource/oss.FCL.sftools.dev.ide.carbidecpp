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
package com.nokia.tcf.test;

import org.eclipse.core.runtime.IStatus;

import com.nokia.tcf.api.ITCErrorListener;

public class TestErrorMonitor implements ITCErrorListener {

	public void errorOccurred(IStatus status) {
		System.out.printf("TestErrorMonitor.errorOccurred = %s\n", status.getMessage());
	}

}
