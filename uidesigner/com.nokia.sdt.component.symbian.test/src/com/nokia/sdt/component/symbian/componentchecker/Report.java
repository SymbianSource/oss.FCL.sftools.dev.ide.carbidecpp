/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.component.symbian.componentchecker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A single reported issue, either a failure or an expected problem.
 * 
 *
 */
class Report {
	static final Pattern reportPattern = Pattern.compile("(.*):(.*)");

	
	public String componentId;
	public String testName;
	public String message;
	public Severity severity;
	public Report(String componentId, String testName, Severity severity, String message) {
		this.componentId = componentId;
		this.testName = testName;
		this.severity = severity;
		this.message = message;
	}
	public Report(String componentId, String testName) {
		this.componentId = componentId;
		this.testName = testName;
		this.severity = Severity.EXPECTED;
		this.message = "<expected>";
	}
	
	public boolean equals(Object reportObj) {
		if (reportObj instanceof Report) {
			Report report = (Report) reportObj;
			if (this.componentId.equals(report.componentId)
					&& this.testName.equals(report.testName))
				return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (componentId.hashCode() << 8)
		 ^ (testName.hashCode() << 4)
		 ^ message.hashCode()
		 ^ 0x84998459;
	}

	/**
	 * Tell whether a report matches, by testing
	 * componentId and testName and passing if this is
	 * an expected error.
	 * @param failureObj
	 * @return
	 */
	public boolean matches(Report report) {
		if (this.componentId.equals(report.componentId)
				&& this.testName.equals(report.testName)) {
			if (this.severity == Severity.EXPECTED)
				return true;
			if (this.severity == report.severity
					&& this.message.equals(report.message))
				return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return /*componentId + ": " +*/ severity + ": " /*+ testName+ ": "*/  + message;
	}
	
	/**
	 * Parse a report line into a new Report
	 * @param string
	 * @return Report or null
	 */
	public static Report parse(String line) {
		Matcher matcher = reportPattern.matcher(line);
		if (matcher.matches()) {
			Report report = new Report(matcher.group(1), matcher.group(2));
			return report;
		}
		return null;
	}
	
	/** 
	 * Get a text line representing the report
	 * @return
	 */
	public CharSequence toLine() {
		return componentId + ":" + testName;
	}

}