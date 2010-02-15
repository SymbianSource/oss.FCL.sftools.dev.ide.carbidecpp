/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.cdt.internal.debug.launch.newwizard;

import java.text.MessageFormat;
import java.util.ArrayList;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.nokia.cdt.internal.debug.launch.LaunchPlugin;
import com.nokia.cdt.internal.debug.launch.newwizard.IWizardSection.ISectionChangeListener;

/**
 * This page presents three sections:
 * <p>
 * Connection to use:  container for the Remote Connection selection UI, plus a label
 * explaining how to handle the case of no connections defined.
 * <p>
 * Debug process: section explaining how the launch will happen, with a combo
 * allowing selecting different process to launch, and a button allowing more
 * in-depth configuration.
 * <p>
 * Build before debug: section with the build-before-debug preference for this
 * launch configuration.
 */
public class UnifiedLaunchOptionsPage extends WizardPage implements ISectionChangeListener {

	private final LaunchWizardData data;
	private ArrayList<IWizardSection> sections;
	
	
	/**
	 * @param mmps
	 * @param exes
	 * @param defaultExecutable
	 * @param project
	 * @param configurationName
	 */
	public UnifiedLaunchOptionsPage(LaunchWizardData data) {
		super("Configure Launch Settings");
		
		setDescription("Configure the connection and process to launch.");
		
		this.data = data;
		this.sections = new ArrayList<IWizardSection>();
		

		IWizardSection section;
		
		section = new ConnectToDeviceSection(data, this);
		sections.add(section);
		
		section = new DebugRunProcessSection(data);
		sections.add(section);
		
		section = new OtherSettingsSection(data);
		sections.add(section);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		
		GridLayoutFactory.fillDefaults().applyTo(composite);
		
		setPageComplete(false);
		
		for (IWizardSection section : sections) {
			section.createControl(composite);
			section.setChangeListener(this);
			GridDataFactory.fillDefaults().grab(true, true).applyTo(section.getControl());
		}
		
		
		setControl(composite);
	}

	/**
	 * @return
	 */
	public void validatePage() {
		setMessage(null, INFORMATION);
		setErrorMessage(null);
		setPageComplete(true);
		
		IStatus pageStatus = null;
		
		// validate the subsections
		StringBuilder builder = new StringBuilder();
		int severity = IStatus.OK;
		for (IWizardSection section : sections) {
			IStatus status = section.getStatus();
			if (status.isOK())
				continue;
			if (builder.length() > 0)
				builder.append("\n");
			
			builder.append(MessageFormat.format("{0}: {1}",
					section.getSectionName(), 
					status.getMessage()));
			severity = Math.max(severity, status.getSeverity());
		}
		if (severity != 0 || builder.length() > 0) {
			// error from one or more sections
			pageStatus = new Status(severity, LaunchPlugin.PLUGIN_ID, builder.toString());
		} else {
			// sections are good; validate the page as a whole
			pageStatus = data.validate();
		}
		
		setTitle("Configure launch configuration");
		
		if (pageStatus != null && !pageStatus.isOK()) {
			setMessage(pageStatus.getMessage(), severityToMsgType(pageStatus.getSeverity()));
			setPageComplete(false);
		}
	}

	private int severityToMsgType(int severity) {
		switch (severity) {
		case IStatus.OK:
		case IStatus.INFO:
			return INFORMATION;
		case IStatus.WARNING:
			return WARNING;
		case IStatus.ERROR:
		default:
			return ERROR;
		}
	}

	/*
	private String getSeverityTag(int severity) {
		if (severity == IStatus.OK || severity == IStatus.INFO || severity == IStatus.CANCEL)
			return "";
		if (severity == IStatus.WARNING)
			return "warning";
		return "error";
	}
	*/
	
	public void initializeSettings() {
		for (IWizardSection section : sections) {
			section.initializeSettings();
		}
		validatePage();
	}

	/* (non-Javadoc)
	 * @see com.nokia.cdt.internal.debug.launch.wizard2.IWizardSection.ISectionChangeListener#changed()
	 */
	public void changed() {
		validatePage();
	}
}
