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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.nokia.carbide.cpp.ui.CarbideUIPlugin;
import com.nokia.carbide.cpp.ui.ICarbideSharedImages;
import com.nokia.cdt.internal.debug.launch.LaunchPlugin;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

/**
 * 
 */
public abstract class AbstractLaunchSettingsDialog extends TitleAreaDialog {
	
	protected final static String UID = ".uid"; //$NON-NLS-1$
	
	protected final IWizardData data;
	protected int INDENT;
	private String title;

	protected abstract void validate();

	/**
	 * @param parentShell
	 * @param data 
	 */
	public AbstractLaunchSettingsDialog(Shell parentShell, IWizardData data) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		this.data = data;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.getString("AbstractLaunchSettingsDialog.Title")); //$NON-NLS-1$
		setTitleImage(CarbideUIPlugin.getSharedImages().getImage(ICarbideSharedImages.IMG_NEW_LAUNCH_CONFIG_WIZARD_BANNER));
	}

	protected Composite initDialogArea(Composite parent, String title, String helpId) {
		Composite composite = (Composite) super.createDialogArea(parent);
		GridLayoutFactory.fillDefaults().margins(6, 6).applyTo(composite);

		INDENT = convertWidthInCharsToPixels(4);
		
		setTitle(title);
		
		this.title = title;

		WorkbenchUtils.setHelpContextId(composite, helpId);
		
		return composite;
	}
	
	protected int severityToMsgType(int severity) {
		switch (severity) {
		case IStatus.OK:
		case IStatus.CANCEL:
		default:
			break;
		case IStatus.INFO:
			return IMessageProvider.INFORMATION;
		case IStatus.ERROR:
			return IMessageProvider.ERROR;
		case IStatus.WARNING:
			return IMessageProvider.WARNING;
		}
		return IMessageProvider.NONE;
	}

	protected IStatus error(String msg, Object... args) {
		return new Status(IStatus.ERROR, LaunchPlugin.PLUGIN_ID,
				MessageFormat.format(msg, args));
	}

	protected IStatus warning(String msg, Object... args) {
		return new Status(IStatus.WARNING, LaunchPlugin.PLUGIN_ID,
				MessageFormat.format(msg, args));
	}
	
	private void setOkEnabled(boolean enabled) {
		Button okButton = getButton(IDialogConstants.OK_ID); 
		if (okButton != null) 
			okButton.setEnabled(enabled);
	}

	protected void updateStatus(final IStatus status) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				setTitle(title);

				if (status.isOK()) {
					setMessage("", IMessageProvider.NONE); //$NON-NLS-1$
				} else {
					setMessage(status.getMessage(), severityToMsgType(status.getSeverity()));
				}
				
				setOkEnabled(!status.matches(IStatus.ERROR));
			}
		});
	}
	
}