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

package com.nokia.carbide.cpp.internal.codescanner.markers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.markers.MarkerSupportView;
import org.eclipse.ui.views.markers.MarkerViewHandler;

import com.nokia.carbide.cpp.internal.codescanner.CSPlugin;
import com.nokia.carbide.cpp.internal.codescanner.Messages;
import com.nokia.carbide.cpp.internal.codescanner.config.CSRule;
import com.nokia.carbide.cpp.internal.codescanner.config.CSScript;
import com.nokia.carbide.cpp.internal.codescanner.kb.CSKbRule;

/**
 * A class to handle the "CodeScanner Error/Warning Details" pop-up command.
 */
public class CSMarkerDetailsHandler extends MarkerViewHandler {

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		MarkerSupportView view = getView(event);
		if (view == null)
			return this;

		final IMarker[] selected = getSelectedMarkers(event);
		if (selected.length > 0) {
			IMarker marker = selected[0];
			try {
				if (marker.isSubtypeOf(CSMarker.CS_PROBLEM_MARKER) ||
					marker.isSubtypeOf(CSMarker.CS_MARKER_VARIABLE)) {
					displayMarkerDetails(marker);					
				}
			} catch (CoreException e) {
				throw new ExecutionException(e.getMessage(), e);
			}			
		}

		return this;
	}

	/**
	 * Create a dialog and display detailed information of a CodeScanner marker.
	 * @param marker - CodeScanner marker
	 */
	public static void displayMarkerDetails(IMarker marker) {
    	Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		String title = Messages.getString("MarkerDetails.CSDialogTitle");
		String details = Messages.getString("MarkerDetails.UnknownRuleMessage");
    	String ruleName = marker.getAttribute(CSMarker.CS_MARKER_RULE_NAME, null);
    	String reference = null;
    	if (ruleName != null) {
    		CSScript script = CSScript.toScript("script_" + ruleName);
    		if (script != CSScript.script_unknown) {
    			// existing CodeScanner rule.
	    		CSRule rule = new CSRule(script, null, null, true);
				details = rule.getDetails();
    		}
    		else {
    			// try looking for a match from the knowledge base rules
    			CSKbRule rule = CSPlugin.getKbManager().getRule(ruleName);
    			if (rule == null) {
    				CSPlugin.getKbManager().clearRules();
    				CSPlugin.getKbManager().readRulesFromPlugins();
    				rule = CSPlugin.getKbManager().getRule(ruleName);
    			}
    			if (rule != null) {
    				title = Messages.getString("MarkerDetails.KBDialogTitle");
    				details = rule.getDescription();
    				reference = rule.getLink();
    			}
    		}
			if (details == null || details.length() < 1) {
				details = Messages.getString("MarkerDetails.UnknownRuleMessage");
			}
    	}
    	CSMarkerDetailsDialog dialog = new CSMarkerDetailsDialog(shell, title, details, reference);
    	dialog.open();
	}
}
