/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.cpp.internal.api.sdk.ui;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import com.nokia.carbide.cpp.internal.api.sdk.SBSv2Utils;
import com.nokia.carbide.cpp.internal.sdk.ui.Messages;
import com.nokia.carbide.cpp.internal.sdk.ui.SDKUIHelpIds;

public class BuildPlatformFilterPage extends PreferencePage implements IWorkbenchPreferencePage { 
	
	SBSv1PlatformFilterComposite sbsv1Tab;
	SBSv2PlatformFilterComposite sbsv2Tab;
	
	public BuildPlatformFilterPage() {
		super();
	}
	
	
	public Control createContents(Composite parent) {
		Composite content = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		content.setLayout(gridLayout);
		
		TabFolder tabFolder = new TabFolder(content, SWT.NONE);
		GridData tabFolderGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		tabFolder.setLayoutData(tabFolderGridData);
		
		TabItem sbsv2TabItem = new TabItem(tabFolder, SWT.NONE);
		sbsv2TabItem.setText(Messages.getString("BuildPlatformFilterPage.SBSv2TabText")); //$NON-NLS-1$
		sbsv2TabItem.setToolTipText(Messages.getString("BuildPlatformFilterPage.SBSv2TabToolTip")); //$NON-NLS-1$

		sbsv2Tab = new SBSv2PlatformFilterComposite(tabFolder);
		sbsv2Tab.createControls();
		sbsv2TabItem.setControl(sbsv2Tab);
		
		if (SBSv2Utils.enableSBSv1Support()) {
			TabItem sbsv1TabItem = new TabItem(tabFolder, SWT.NONE);
			sbsv1TabItem.setText(Messages.getString("BuildPlatformFilterPage.SBSv1TabText")); //$NON-NLS-1$
			sbsv1TabItem.setToolTipText(Messages.getString("BuildPlatformFilterPage.SBSv1TabToolTip")); //$NON-NLS-1$
	
			sbsv1Tab = new SBSv1PlatformFilterComposite(tabFolder);
			sbsv1Tab.createControls();
			sbsv1TabItem.setControl(sbsv1Tab);
		}

		PlatformUI.getWorkbench().getHelpSystem().setHelp(super.getControl(), SDKUIHelpIds.SDK_BUILD_FILTER_PAGE);
		
		return content;
	}
	

	public void init(IWorkbench workbench) {
	}
	
	public boolean performOk() {
		if (sbsv1Tab != null) {
			sbsv1Tab.performOk();
		}
		
		if (sbsv2Tab != null) {
			sbsv2Tab.performOk();
		}

		return super.performOk();
	}

	public boolean performCancel() {
		if (sbsv2Tab != null) {
			sbsv2Tab.performCancel();
		}

		return super.performCancel();
	}

	@Override
	protected void performApply() {
		performOk();
		super.performApply();
	}

	@Override
	protected void performDefaults() {
		if (sbsv1Tab != null) {
			sbsv1Tab.setDefaults();
		}
		if (sbsv2Tab != null) {
			sbsv2Tab.setDefaults();
		}
		super.performDefaults();
	}
	
	
	
}
