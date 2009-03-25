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

package com.nokia.carbide.cpp.internal.codescanner.popup.actions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import com.nokia.carbide.cpp.internal.codescanner.CSPlugin;
import com.nokia.carbide.cpp.internal.codescanner.Messages;
import com.nokia.carbide.cpp.internal.codescanner.config.CSRule;
import com.nokia.carbide.cpp.internal.codescanner.config.CSScript;
import com.nokia.carbide.cpp.internal.codescanner.markers.CSMarker;

/**
 * A class to handle actions associated with the "CodeScanner Marker Details" pop-up menu.
 */
public class CSMarkerDetailsAction implements IObjectActionDelegate {

	// IDs definied in plugin.xml
	public static final String CS_MARKER_DETAILS_POP_UP_ID = CSPlugin.PLUGIN_ID + ".CSMarkerDetails";
	//public static final String CS_UPDATE_RULES_POP_UP_ID = CSPlugin.PLUGIN_ID + ".CSUpdateRules";

	private IStructuredSelection  selection;

	/**
	 * Constructor for CSMarkerDetailsAction.
	 */
	public CSMarkerDetailsAction() {
		super();
	}

	/**
	 * Perform any work needed when the action has been triggered.
	 */
	public void run(IAction action) {
		if (action.getId().equals(CS_MARKER_DETAILS_POP_UP_ID)) {
			// provide details on any selected CodeScanner marker
			handleCSMarkerDetailsAction(action);
		}
		/*
		else if (action.getId().equals(CS_UPDATE_RULES_POP_UP_ID)) {
			// let user update the rules via CodeScanner preference page
			handleCSUpdateRulesAction(action);
		}
		*/
	}

	/**
	 * Perform any work needed when the selection in the workbench has changed.
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		boolean enable = false;
		if (selection instanceof IStructuredSelection) {
			Object object = ((IStructuredSelection) selection).getFirstElement();
			if (object instanceof IMarker) {
				try {
					// enable this pop-up menu for CodeScanner markers only
					IMarker marker = (IMarker) object;
					if (marker.isSubtypeOf(CSMarker.CS_PROBLEM_MARKER) ||
						marker.isSubtypeOf(CSMarker.CS_MARKER_VARIABLE)) {
						enable = true;
					}
					this.selection = (IStructuredSelection)selection;
					action.setEnabled(enable);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Set the active part for this delegate class.
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	/**
	 * Provide details of any selected CodeScanner marker.
	 */
	private void handleCSMarkerDetailsAction(IAction action){
		if (selection != null && selection instanceof IStructuredSelection) {
			Object object = ((IStructuredSelection) selection).getFirstElement();
			if (object instanceof IMarker) {
				IMarker marker = (IMarker) object;
		    	Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				String title = Messages.getString("MarkerDetails.CSDialogTitle");
				String details = Messages.getString("MarkerDetails.UnknownRuleMessage");
		    	String ruleName = marker.getAttribute(CSMarker.CS_MARKER_RULE_NAME, null);
		    	if (ruleName != null) {
		    		CSScript script = CSScript.toScript("script_" + ruleName);
		    		CSRule rule = new CSRule(script, null, null, true);
					details = rule.getDetails();
					if (details == null || details.length() < 1) {
						details = Messages.getString("MarkerDetails.UnknownRuleMessage");
					}
					else {
						title = Messages.getString("MarkerDetails.KBDialogTitle");
					}
		    	}
		    	MessageDialog.openInformation(shell, title, details);
			}
		}
	}

	/*
	private void handleCSUpdateRulesAction(IAction action) {
		if (selection != null && selection instanceof IStructuredSelection) {
			Object object = ((IStructuredSelection) selection).getFirstElement();
			if (object instanceof IMarker) {
				IMarker marker = (IMarker) object;
		    	Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		    	PreferenceDialog preferenceDialog = null;
				Map data = new HashMap();
				data.put(CSPreferenceConstants.NO_LINK, Boolean.TRUE);
				IResource resource = marker.getResource();
				IProject project = resource.getProject();
				CSConfigManager configManager = CSPlugin.getConfigManager();
				CSProjectSettings projectSettings = configManager.getProjectSettings(project);
				IDialogSettings cSettings = projectSettings.getDialogSettings();
				IDialogSettings pageSettings = cSettings.getSection(CSPreferenceConstants.PROPERTY_SETTINGS_ID);
				if (pageSettings == null || !pageSettings.getBoolean(CSPreferenceConstants.PROJ_SETTINGS)) {
					// open CodeScanner global preference page
					preferenceDialog = PreferencesUtil.createPreferenceDialogOn(shell, CSPreferenceConstants.PREFERENCE_PAGE_ID, null, data);
				}
				else {
					// open CodeScanner project property page
					preferenceDialog = PreferencesUtil.createPropertyDialogOn(shell, project, CSPreferenceConstants.PROPERTY_PAGE_ID, null, data);					
				}
			}
		}
	}
	*/

}
