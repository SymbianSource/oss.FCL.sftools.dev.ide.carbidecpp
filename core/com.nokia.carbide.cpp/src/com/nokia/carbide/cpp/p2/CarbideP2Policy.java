package com.nokia.carbide.cpp.p2;

import org.eclipse.equinox.p2.ui.Policy;

public class CarbideP2Policy extends Policy {
	
	public CarbideP2Policy() {
		setRestartPolicy(RESTART_POLICY_PROMPT);
		System.setProperty("eclipse.p2.unsignedPolicy", "allow");
	}

}
