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

import com.nokia.carbide.cpp.internal.api.project.core.ResourceChangeListener;
import com.nokia.carbide.cpp.internal.project.ui.Messages;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIHelpIds;
import com.nokia.carbide.cpp.project.ui.utils.ProjectUIUtils;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;

public class CarbidePreferencePage extends PreferencePage implements IWorkbenchPreferencePage { 
	
	private Button keepProjectFilesInSync;

	private Composite addGroup;
	private Button addAskFirst;
	private Button addAlways;
	private Button addNever;

	private Composite deleteGroup;
	private Button deleteAskFirst;
	private Button deleteAlways;
	private Button deleteNever;

	private Button indexBuildOnly;

	private Button indexAll;


	public CarbidePreferencePage() {
		super();
	}
	
	protected Control createContents(Composite parent) {
		Composite content = new Composite(parent, SWT.NONE);
		content.setLayout(new GridLayout());

		Group inSyncGroup = new Group(content, SWT.NONE);
		inSyncGroup.setLayout(new GridLayout());
		inSyncGroup.setText(Messages.getString("CarbidePreferencePage.ProjectSyncTitle")); //$NON-NLS-1$
		GridDataFactory.fillDefaults().grab(true, false).applyTo(inSyncGroup);
		
		keepProjectFilesInSync = new Button(inSyncGroup, SWT.CHECK);
		GridDataFactory.defaultsFor(keepProjectFilesInSync).indent(0, 10).applyTo(keepProjectFilesInSync);
		keepProjectFilesInSync.setText(Messages.getString("CarbidePreferencePage.EnableResourceListenerText")); //$NON-NLS-1$
		keepProjectFilesInSync.setToolTipText(Messages.getString("CarbidePreferencePage.EnableResourceListenerTooltip")); //$NON-NLS-1$
		keepProjectFilesInSync.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateProjectSyncEnabledState();
			}
		});

		addGroup = new Composite(inSyncGroup, SWT.NONE);
		addGroup.setFont(parent.getFont());
		addGroup.setLayout(new GridLayout());
		GridDataFactory.fillDefaults().grab(true, false).applyTo(addGroup);
		addGroup.setToolTipText(Messages.getString("CarbidePreferencePage.AddedFilesToolTip")); //$NON-NLS-1$

		Label addLabel = new Label(addGroup, SWT.NONE);
		addLabel.setText(Messages.getString("CarbidePreferencePage.AddedFilesText")); //$NON-NLS-1$
		
		addAskFirst = new Button(addGroup, SWT.RADIO);
		addAskFirst.setText(Messages.getString("CarbidePreferencePage.AskFirstText")); //$NON-NLS-1$
		addAskFirst.setToolTipText(Messages.getString("CarbidePreferencePage.AskFirstToolTip")); //$NON-NLS-1$
		GridDataFactory.defaultsFor(addAskFirst).indent(10, 0).applyTo(addAskFirst);

		addAlways = new Button(addGroup, SWT.RADIO);
		addAlways.setText(Messages.getString("CarbidePreferencePage.AlwaysText")); //$NON-NLS-1$
		addAlways.setToolTipText(Messages.getString("CarbidePreferencePage.AlwaysToolTip")); //$NON-NLS-1$
		GridDataFactory.defaultsFor(addAlways).indent(10, 0).applyTo(addAlways);

		addNever = new Button(addGroup, SWT.RADIO);
		addNever.setText(Messages.getString("CarbidePreferencePage.NeverText")); //$NON-NLS-1$
		addNever.setToolTipText(Messages.getString("CarbidePreferencePage.NeverToolTip")); //$NON-NLS-1$
		GridDataFactory.defaultsFor(addNever).indent(10, 0).applyTo(addNever);


		deleteGroup = new Composite(inSyncGroup, SWT.NONE);
		deleteGroup.setLayout(new GridLayout());
		GridDataFactory.fillDefaults().grab(true, false).applyTo(deleteGroup);
		deleteGroup.setFont(parent.getFont());
		deleteGroup.setToolTipText(Messages.getString("CarbidePreferencePage.ChangedFilesToolTip")); //$NON-NLS-1$

		Label deleteLabel = new Label(deleteGroup, SWT.NONE);
		deleteLabel.setText(Messages.getString("CarbidePreferencePage.ChangedFilesText")); //$NON-NLS-1$

		deleteAskFirst = new Button(deleteGroup, SWT.RADIO);
		deleteAskFirst.setText(Messages.getString("CarbidePreferencePage.AskFirstText")); //$NON-NLS-1$
		deleteAskFirst.setToolTipText(Messages.getString("CarbidePreferencePage.AskFirstToolTip")); //$NON-NLS-1$
		GridDataFactory.defaultsFor(deleteAskFirst).indent(10, 0).applyTo(deleteAskFirst);

		deleteAlways = new Button(deleteGroup, SWT.RADIO);
		deleteAlways.setText(Messages.getString("CarbidePreferencePage.AlwaysText")); //$NON-NLS-1$
		deleteAlways.setToolTipText(Messages.getString("CarbidePreferencePage.AlwaysToolTip")); //$NON-NLS-1$
		GridDataFactory.defaultsFor(deleteAlways).indent(10, 0).applyTo(deleteAlways);

		deleteNever = new Button(deleteGroup, SWT.RADIO);
		deleteNever.setText(Messages.getString("CarbidePreferencePage.NeverText")); //$NON-NLS-1$
		deleteNever.setToolTipText(Messages.getString("CarbidePreferencePage.NeverToolTip")); //$NON-NLS-1$
		GridDataFactory.defaultsFor(deleteNever).indent(10, 0).applyTo(deleteNever);

		boolean keepInSync = ProjectUIUtils.keepProjectsInSync();
		int addOption = ProjectUIUtils.getAddFilesToProjectOption();
		int deleteOption = ProjectUIUtils.getChangedFilesInProjectOption();
		setUpdateFilesOptions(keepInSync, addOption, deleteOption);
		updateProjectSyncEnabledState();

		Group indexGroup = new Group(content, SWT.NONE);
		GridLayoutFactory.swtDefaults().margins(10, 10).applyTo(indexGroup);
		indexGroup.setText(Messages.getString("CarbidePreferencePage.IndexingTitle")); //$NON-NLS-1$
		GridDataFactory.fillDefaults().grab(true, false).applyTo(indexGroup);
		
		indexBuildOnly = new Button(indexGroup, SWT.RADIO);
		indexBuildOnly.setText(Messages.getString("CarbidePreferencePage.IndexOnlyBuildLabel")); //$NON-NLS-1$
		indexBuildOnly.setToolTipText(Messages.getString("CarbidePreferencePage.IndexOnlyBuildTooltip")); //$NON-NLS-1$
		GridDataFactory.defaultsFor(indexBuildOnly).applyTo(indexBuildOnly);
		
		indexAll = new Button(indexGroup, SWT.RADIO);
		indexAll.setText(Messages.getString("CarbidePreferencePage.IndexAllLabel")); //$NON-NLS-1$
		indexAll.setToolTipText(Messages.getString("CarbidePreferencePage.IndexAllToolTip")); //$NON-NLS-1$
		GridDataFactory.defaultsFor(indexAll).applyTo(indexAll);
		
		boolean indexAllOption = ProjectUIUtils.getIndexAllOption();
		indexBuildOnly.setSelection(!indexAllOption);
		indexAll.setSelection(indexAllOption);
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(content, ProjectUIHelpIds.CARBIDE_PREFS_PAGE);
	
		return content;
	}
	
	private void updateProjectSyncEnabledState() {
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
	
	public void init(IWorkbench workbench) {
	}
	
	@Override
	protected void performDefaults() {
		setUpdateFilesOptions(true, 0, 0);
		updateProjectSyncEnabledState();
		indexBuildOnly.setSelection(true);
		indexAll.setSelection(false);
		super.performDefaults();
	}

	private void setUpdateFilesOptions(boolean keepInSync, int addOptions, int deleteOptions) {
		keepProjectFilesInSync.setSelection(keepInSync);
		addAskFirst.setSelection(ResourceChangeListener.UPDATE_FILES_OPTION_ASK == addOptions);
		addAlways.setSelection(ResourceChangeListener.UPDATE_FILES_OPTION_ALWAYS == addOptions);
		addNever.setSelection(ResourceChangeListener.UPDATE_FILES_OPTION_NEVER == addOptions);
		deleteAskFirst.setSelection(ResourceChangeListener.UPDATE_FILES_OPTION_ASK == deleteOptions);
		deleteAlways.setSelection(ResourceChangeListener.UPDATE_FILES_OPTION_ALWAYS == deleteOptions);
		deleteNever.setSelection(ResourceChangeListener.UPDATE_FILES_OPTION_NEVER == deleteOptions);
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

		ProjectUIUtils.setIndexAllOption(indexAll.getSelection());
		
		return super.performOk();
	}

}
