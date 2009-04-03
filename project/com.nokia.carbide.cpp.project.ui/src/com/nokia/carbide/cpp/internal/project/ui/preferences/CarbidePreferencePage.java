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
package com.nokia.carbide.cpp.internal.project.ui.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import com.freescale.swt.widgets.CheckboxGroup;
import com.nokia.carbide.cpp.internal.api.project.core.ResourceChangeListener;
import com.nokia.carbide.cpp.internal.project.ui.Messages;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIHelpIds;
import com.nokia.carbide.cpp.project.ui.utils.ProjectUIUtils;

public class CarbidePreferencePage extends PreferencePage implements IWorkbenchPreferencePage { 
	
	private CheckboxGroup keepProjectFilesInSync;

	private Group addGroup;
	private Button addAskFirst;
	private Button addAlways;
	private Button addNever;

	private Group deleteGroup;
	private Button deleteAskFirst;
	private Button deleteAlways;
	private Button deleteNever;


	public CarbidePreferencePage() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite content = new Composite(parent, SWT.NONE);
		setControl(content);
		final GridLayout gridLayout = new GridLayout();
		content.setLayout(gridLayout);

		keepProjectFilesInSync = new CheckboxGroup(content, SWT.CHECK);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		keepProjectFilesInSync.setLayoutData(data);
		keepProjectFilesInSync.setText(Messages.getString("CarbidePreferencePage.EnableResourceListenerText")); //$NON-NLS-1$
		keepProjectFilesInSync.setToolTipText(Messages.getString("CarbidePreferencePage.EnableResourceListenerTooltip")); //$NON-NLS-1$
		keepProjectFilesInSync.getGroup().setLayout(gridLayout);
		keepProjectFilesInSync.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateProjectSyncOptions();
			}
		});

		keepProjectFilesInSync.setSelection(ProjectUIUtils.keepProjectsInSync());

		addGroup = new Group(keepProjectFilesInSync.getGroup(), SWT.NONE);
		GridLayout layout = new GridLayout();
		addGroup.setLayout(layout);
		addGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		addGroup.setText(Messages.getString("CarbidePreferencePage.AddedFilesText")); //$NON-NLS-1$
		addGroup.setFont(parent.getFont());
		addGroup.setToolTipText(Messages.getString("CarbidePreferencePage.AddedFilesToolTip")); //$NON-NLS-1$

		int addOption = ProjectUIUtils.getAddFilesToProjectOption();

		addAskFirst = new Button(addGroup, SWT.RADIO);
		addAskFirst.setText(Messages.getString("CarbidePreferencePage.AskFirstText")); //$NON-NLS-1$
		addAskFirst.setToolTipText(Messages.getString("CarbidePreferencePage.AskFirstToolTip")); //$NON-NLS-1$
		addAskFirst.setSelection(addOption == ResourceChangeListener.UPDATE_FILES_OPTION_ASK);

		addAlways = new Button(addGroup, SWT.RADIO);
		addAlways.setText(Messages.getString("CarbidePreferencePage.AlwaysText")); //$NON-NLS-1$
		addAlways.setToolTipText(Messages.getString("CarbidePreferencePage.AlwaysToolTip")); //$NON-NLS-1$
		addAlways.setSelection(addOption == ResourceChangeListener.UPDATE_FILES_OPTION_ALWAYS);

		addNever = new Button(addGroup, SWT.RADIO);
		addNever.setText(Messages.getString("CarbidePreferencePage.NeverText")); //$NON-NLS-1$
		addNever.setToolTipText(Messages.getString("CarbidePreferencePage.NeverToolTip")); //$NON-NLS-1$
		addNever.setSelection(addOption == ResourceChangeListener.UPDATE_FILES_OPTION_NEVER);

		int deleteOption = ProjectUIUtils.getChangedFilesInProjectOption();

		deleteGroup = new Group(keepProjectFilesInSync.getGroup(), SWT.NONE);
		GridLayout layout2 = new GridLayout();
		deleteGroup.setLayout(layout2);
		GridData gd2 = new GridData(GridData.FILL_HORIZONTAL);
		deleteGroup.setLayoutData(gd2);
		deleteGroup.setText(Messages.getString("CarbidePreferencePage.ChangedFilesText")); //$NON-NLS-1$
		deleteGroup.setFont(parent.getFont());
		deleteGroup.setToolTipText(Messages.getString("CarbidePreferencePage.ChangedFilesToolTip")); //$NON-NLS-1$

		deleteAskFirst = new Button(deleteGroup, SWT.RADIO);
		deleteAskFirst.setText(Messages.getString("CarbidePreferencePage.AskFirstText")); //$NON-NLS-1$
		deleteAskFirst.setToolTipText(Messages.getString("CarbidePreferencePage.AskFirstToolTip")); //$NON-NLS-1$
		deleteAskFirst.setSelection(deleteOption == ResourceChangeListener.UPDATE_FILES_OPTION_ASK);

		deleteAlways = new Button(deleteGroup, SWT.RADIO);
		deleteAlways.setText(Messages.getString("CarbidePreferencePage.AlwaysText")); //$NON-NLS-1$
		deleteAlways.setToolTipText(Messages.getString("CarbidePreferencePage.AlwaysToolTip")); //$NON-NLS-1$
		deleteAlways.setSelection(deleteOption == ResourceChangeListener.UPDATE_FILES_OPTION_ALWAYS);

		deleteNever = new Button(deleteGroup, SWT.RADIO);
		deleteNever.setText(Messages.getString("CarbidePreferencePage.NeverText")); //$NON-NLS-1$
		deleteNever.setToolTipText(Messages.getString("CarbidePreferencePage.NeverToolTip")); //$NON-NLS-1$
		deleteNever.setSelection(deleteOption == ResourceChangeListener.UPDATE_FILES_OPTION_NEVER);
		
		updateProjectSyncOptions();
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(super.getControl(), ProjectUIHelpIds.CARBIDE_PREFS_PAGE);
	}
	
	private void updateProjectSyncOptions() {
		boolean enabled = keepProjectFilesInSync.getSelection();
		addGroup.setEnabled(enabled);
		addAskFirst.setEnabled(enabled);
		addAlways.setEnabled(enabled);
		addNever.setEnabled(enabled);
		deleteGroup.setEnabled(enabled);
		deleteAskFirst.setEnabled(enabled);
		deleteAlways.setEnabled(enabled);
		deleteNever.setEnabled(enabled);
	}
	
	@Override
	protected Control createContents(Composite parent) {
		return null;
	}

	public void init(IWorkbench workbench) {
	}
	
	
	public boolean performOk() {
		ProjectUIUtils.setKeepProjectsInSync(keepProjectFilesInSync.getSelection());

		if (addAskFirst.getSelection()) {
			ProjectUIUtils.setAddFilesToProjectOption(ResourceChangeListener.UPDATE_FILES_OPTION_ASK);
		} else if (addAlways.getSelection()) {
			ProjectUIUtils.setAddFilesToProjectOption(ResourceChangeListener.UPDATE_FILES_OPTION_ALWAYS);
		} else {
			ProjectUIUtils.setAddFilesToProjectOption(ResourceChangeListener.UPDATE_FILES_OPTION_NEVER);
		}
		
		if (deleteAskFirst.getSelection()) {
			ProjectUIUtils.setChangedFilesInProjectOption(ResourceChangeListener.UPDATE_FILES_OPTION_ASK);
		} else if (deleteAlways.getSelection()) {
			ProjectUIUtils.setChangedFilesInProjectOption(ResourceChangeListener.UPDATE_FILES_OPTION_ALWAYS);
		} else {
			ProjectUIUtils.setChangedFilesInProjectOption(ResourceChangeListener.UPDATE_FILES_OPTION_NEVER);
		}

		return super.performOk();
	}

}
