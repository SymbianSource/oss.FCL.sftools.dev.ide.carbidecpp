/**
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