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
package com.nokia.carbide.cpp.logging;

import java.util.ArrayList;

class DiagnosticLogGroup {

	private String name;
	private String id;
	private ArrayList<DiagnosticLog> logs = new ArrayList<DiagnosticLog>();

	public DiagnosticLogGroup(String name, String id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public void add(DiagnosticLog diagnosticLog) {
		logs.add(diagnosticLog);
	}

	public DiagnosticLog[] getLogs() {
		return logs.toArray(new DiagnosticLog[logs.size()]);
	}
}