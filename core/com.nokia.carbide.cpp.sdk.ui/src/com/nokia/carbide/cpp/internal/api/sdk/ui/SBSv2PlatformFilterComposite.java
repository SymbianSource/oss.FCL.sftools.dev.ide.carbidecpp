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
package com.nokia.carbide.cpp.internal.api.sdk.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TableItem;

import com.nokia.carbide.cpp.internal.api.sdk.SBSv2Utils;
import com.nokia.carbide.cpp.internal.sdk.ui.Messages;

/**
 * @since 1.4
 */
public class SBSv2PlatformFilterComposite extends Composite {

	private CheckboxTableViewer tableViewer;
	private Button refreshButton;

	
	SBSv2PlatformFilterComposite(Composite parent) {
		super(parent, SWT.NONE);
	}

	public void createControls() {
		GridLayout gridLayout = new GridLayout();
		setLayout(gridLayout);
		
		Group platformsGroup = new Group(this, SWT.NONE);
		platformsGroup.setText(Messages.getString("SBSv2PlatformFilterComposite.PlatformsGroupText")); //$NON-NLS-1$
		platformsGroup.setToolTipText(Messages.getString("SBSv2PlatformFilterComposite.PlatformsGroupToolTip")); //$NON-NLS-1$
		GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd.widthHint = 350;
		platformsGroup.setLayoutData(gd);
		platformsGroup.setLayout(new GridLayout());

		tableViewer = CheckboxTableViewer.newCheckList(platformsGroup, SWT.BORDER);
		tableViewer.getTable().setLayoutData(gd);
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setLabelProvider(new LabelProvider());
		
		refreshButton = new Button(this, SWT.NONE);		
		refreshButton.setText(Messages.getString("SBSv2PlatformFilterComposite.RefreshButtonText")); //$NON-NLS-1$
		refreshButton.setToolTipText(Messages.getString("SBSv2PlatformFilterComposite.RefreshButtonToolTip")); //$NON-NLS-1$
		refreshButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				initTable(true);
			}
			
		});
		
		initTable(false);
	}

	public void performOk() {
		// save the list of unchecked configs
		List<String> uncheckedConfigs = new ArrayList<String>();
		for (TableItem item : tableViewer.getTable().getItems()) {
			if (!tableViewer.getChecked(item.getData())) {
				uncheckedConfigs.add(item.getText());
			}
		}
		
		SBSv2Utils.setSBSv2ConfigurationsToFilter(uncheckedConfigs.toArray(new String[uncheckedConfigs.size()]));
	}
	
	private void initTable(boolean refreshList) {

		SBSv2Utils.initDefaultConfigsToFilter();
		
		Object[] keySet = SBSv2Utils.getUnfilteredSBSv2BuildConfigurations(refreshList).keySet().toArray();
		List<String> sbsAliases = new ArrayList<String>();
		for (Object key : keySet)
			sbsAliases.add((String)key);
		Collections.sort(sbsAliases);
		tableViewer.setInput(sbsAliases);
		
		// check all configs
		tableViewer.setAllChecked(true);
		
		// now uncheck the ones from the store
		String[] uncheckedConfigs = SBSv2Utils.getSBSv2ConfigurationsToFilter();
		for (String config : uncheckedConfigs) {
			for (TableItem item : tableViewer.getTable().getItems()) {
				if (item.getText().equals(config)) {
					tableViewer.setChecked(item.getData(), false);
					break;
				}
			}
		}
	}
	
	public void setDefaults(){
		initTable(true);
		for (TableItem item : tableViewer.getTable().getItems()) {
			if (item.getText().toLowerCase().startsWith("tool")  || 
				item.getText().toLowerCase().startsWith("gccxml") ||
				item.getText().toLowerCase().startsWith("armv6")  ||
				item.getText().toLowerCase().startsWith("armv7")  || 
				item.getText().toLowerCase().startsWith("armv7")  ||
				item.getText().toLowerCase().startsWith("armv9")) {
				tableViewer.setChecked(item.getData(), false);
			} else {
				tableViewer.setChecked(item.getData(), true);
			}
		}
	}
}
