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
package com.nokia.carbide.internal.updater.ui;

import com.nokia.carbide.internal.updater.Messages;
import com.nokia.carbide.updater.CarbideUpdaterPlugin;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import java.util.ArrayList;
import java.util.List;

public class ProjectUpdateDialog extends TitleAreaDialog {

	private static final String TITLE = Messages.getString("ProjectUpdateDialog.Title"); //$NON-NLS-1$
	private static final String HTML_DOC_HEADER = "<HEAD><TITLE></TITLE>" +  //$NON-NLS-1$
				"<style type=\"text/css\">\r\n" + //$NON-NLS-1$
				"body {margin-top: 3; margin-left: 3}\r\n" +  //$NON-NLS-1$
				"p {font-family: arial;font-size: x-small;}" + //$NON-NLS-1$
				"</style></HEAD>" + //$NON-NLS-1$
				"<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\" LINK=\"#0000FF\" VLINK=\"#800080\" ALINK=\"#FF0000\">"; //$NON-NLS-1$
	private static final String HTML_DOC_FOOTER = 
		"<p>" +  //$NON-NLS-1$
		Messages.getString("ProjectUpdateDialog.Footer1") + //$NON-NLS-1$
		Messages.getString("ProjectUpdateDialog.Footer2") + //$NON-NLS-1$
		"<p>" +  //$NON-NLS-1$
		Messages.getString("ProjectUpdateDialog.Footer3") + //$NON-NLS-1$
		"</p></BODY></HTML>"; //$NON-NLS-1$
	
	private static final String HELP_CONTEXT_ID = 
		CarbideUpdaterPlugin.PLUGIN_ID + ".projectUpdateDialog"; //$NON-NLS-1$

	private CheckboxTableViewer tableViewer;
	private List<IProject> checkedProjects;
	private IProject[] projects;
	private String documentation;
	
	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public ProjectUpdateDialog(Shell parentShell, List<IProject> projectList, String documentation) {
		super(parentShell);
		this.projects = (IProject[]) projectList.toArray(new IProject[projectList.size()]);
		this.documentation = documentation;
		setShellStyle(getShellStyle()| SWT.RESIZE);
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new FormLayout());
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		Browser browser;

		tableViewer = CheckboxTableViewer.newCheckList(container, SWT.BORDER);
		final FormData formData = new FormData();
		formData.right = new FormAttachment(0, 255);
		formData.top = new FormAttachment(0, 3);
		formData.bottom = new FormAttachment(100, -5);
		formData.left = new FormAttachment(0, 5);
		tableViewer.getTable().setLayoutData(formData);
		tableViewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				updateEnabled();
			}
		});
		tableViewer.setSorter(new ViewerSorter());
		
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setInput(projects);
		tableViewer.setLabelProvider(new WorkbenchLabelProvider());
		browser = new Browser(container, SWT.BORDER);
		final FormData formData_1 = new FormData();
		formData_1.left = new FormAttachment(tableViewer.getTable(), 0, SWT.DEFAULT);
		formData_1.bottom = new FormAttachment(100, -5);
		formData_1.right = new FormAttachment(100, -5);
		formData_1.top = new FormAttachment(0, 3);
		browser.setLayoutData(formData_1);
		browser.setText(getProjectUIText());
		setTitle(getProjectUpdaterTitle());
		setMessage(getProjectUpdaterMessage());

		WorkbenchUtils.setHelpContextId(area, HELP_CONTEXT_ID);

		return area;
	}

	private String getProjectUIText() {
		return HTML_DOC_HEADER + documentation + HTML_DOC_FOOTER;
	}
	
	private String getProjectUpdaterMessage() {
		return Messages.getString("ProjectUpdateDialog.UpdaterMessage"); //$NON-NLS-1$
	}

	private String getProjectUpdaterTitle() {
		return Messages.getString("ProjectUpdateDialog.UpdaterTitle"); //$NON-NLS-1$
	}
	
	/**
	 * Create contents of the button bar (change OK to Update)
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button ok = createButton(parent, IDialogConstants.OK_ID, Messages.getString("ProjectUpdateDialog.UpdateButtonTitle"), true); //$NON-NLS-1$
		ok.setToolTipText(Messages.getString("ProjectUpdateDialog.OkButtonTooltip")); //$NON-NLS-1$
		Button skip = createButton(parent, IDialogConstants.NEXT_ID, Messages.getString("ProjectUpdateDialog.SkipButtonTitle"), false); //$NON-NLS-1$
		skip.setToolTipText(Messages.getString("ProjectUpdateDialog.SkipButtonTooltip")); //$NON-NLS-1$
		Button cancel = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		cancel.setToolTipText(Messages.getString("ProjectUpdateDialog.CancelButtonTooltip")); //$NON-NLS-1$
		updateEnabled();
	}

	/**
	 * Override the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(650, 450);
	}
	
	/**
	 * Update the title of the window
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(TITLE);
	}

	private void updateEnabled() {
		boolean enabled = tableViewer.getCheckedElements().length > 0;
		getButton(IDialogConstants.OK_ID).setEnabled(enabled);
	}
	
	@Override
	protected void okPressed() {
		Object[] checkedElements = tableViewer.getCheckedElements();
		for (int i = 0; i < checkedElements.length; i++) {
			if (checkedProjects == null)
				checkedProjects = new ArrayList();
			IProject project = (IProject) checkedElements[i];
			checkedProjects.add(project);
		}
		super.okPressed();
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.NEXT_ID) {
			setReturnCode(buttonId);
			close();
		}
			
		super.buttonPressed(buttonId);
	}
	
	public List<IProject> getCheckedProjects() {
		return checkedProjects;
	}

}
