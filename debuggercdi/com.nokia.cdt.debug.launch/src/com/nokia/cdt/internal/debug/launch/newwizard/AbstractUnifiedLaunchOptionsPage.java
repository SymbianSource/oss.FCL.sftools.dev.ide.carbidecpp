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
import org.eclipse.swt.widgets.Control;

import com.nokia.cdt.internal.debug.launch.LaunchPlugin;
import com.nokia.cdt.internal.debug.launch.newwizard.IWizardSection.ISectionChangeListener;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

public abstract class AbstractUnifiedLaunchOptionsPage extends WizardPage implements ISectionChangeListener {

	protected IWizardData data;
	protected ArrayList<IWizardSection> sections;

	protected AbstractUnifiedLaunchOptionsPage(String pageName, IWizardData data) {
		super(pageName);
		this.data = data;
		this.sections = new ArrayList<IWizardSection>();
	}

	protected void addSection(IWizardSection section) {
		sections.add(section);
	}
	
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		
		GridLayoutFactory.fillDefaults().applyTo(composite);
		
		setPageComplete(false);
		
		for (IWizardSection section : sections) {
			section.createControl(composite);
			section.setChangeListener(this);
			GridDataFactory.fillDefaults().grab(true, true).applyTo(section.getControl());
		}
		
		WorkbenchUtils.setHelpContextId(composite, LaunchWizardHelpIds.WIZARD_DIALOG_LAUNCH_OPTIONS_PAGE);
		
		setControl(composite);
	}

	public void initializeSettings() {
		for (IWizardSection section : sections) {
			section.initializeSettings();
		}
		validatePage();
	}

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
				builder.append("\n"); //$NON-NLS-1$
			
			builder.append(MessageFormat.format("{0}: {1}", //$NON-NLS-1$
					section.getSectionName(), 
					status.getMessage()));
			severity = Math.max(severity, status.getSeverity());
		}
		if (severity == IStatus.OK) {
			IStatus status = getStatus();
			if (!status.isOK()) {
				builder.append(status.getMessage());
				severity = status.getSeverity();
			}
		}
		if (severity != 0 || builder.length() > 0) {
			// error from one or more sections
			pageStatus = new Status(severity, LaunchPlugin.PLUGIN_ID, builder.toString());
		} else {
			// sections are good; validate the page as a whole
			pageStatus = data.validate();
		}
		
		setTitle(Messages.getString("UnifiedLaunchOptionsPage.TitleText")); //$NON-NLS-1$
		
		if (!pageStatus.isOK()) {
			setMessage(pageStatus.getMessage(), severityToMsgType(pageStatus.getSeverity()));
			setPageComplete(pageStatus.getSeverity() < IStatus.ERROR);
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

	protected abstract IStatus getStatus();

	public void changed() {
		validatePage();
		Control control = getControl();
		if (control != null && !control.isDisposed())
			getWizard().getContainer().getShell().pack();
	}
}