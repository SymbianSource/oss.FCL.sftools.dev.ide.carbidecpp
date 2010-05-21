package com.nokia.carbide.cpp.p2;

import org.eclipse.equinox.p2.ui.Policy;

public class CarbideP2Policy extends Policy {
	
	public CarbideP2Policy() {
		// restart policy after install
		setRestartPolicy(RESTART_POLICY_PROMPT);
		// policy about install wizard
		setGroupByCategory(false);
		setShowLatestVersionsOnly(false);
	}

}
