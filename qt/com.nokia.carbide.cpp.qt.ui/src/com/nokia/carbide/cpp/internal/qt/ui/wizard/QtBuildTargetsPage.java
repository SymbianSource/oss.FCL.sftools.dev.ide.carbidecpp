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
package com.nokia.carbide.cpp.internal.qt.ui.wizard;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.nokia.carbide.cpp.internal.qt.core.QtConfigFilter;
import com.nokia.carbide.cpp.internal.qt.core.QtSDKFilter;
import com.nokia.carbide.cpp.sdk.ui.shared.BuildTargetsPage;


public class QtBuildTargetsPage extends BuildTargetsPage {

	private Button qtFilterCheckbox;
	private QtSDKFilter sdkFilter;
	private QtConfigFilter configFilter;

	
	public QtBuildTargetsPage() {
		super();
		setHideFilterCheckbox();
		sdkFilter = new QtSDKFilter();
		configFilter = new QtConfigFilter();
		setTitle(Messages.QtBuildTargetsPage_title);
	}

    @Override
	protected void addOtherControls(Composite parent) {

		qtFilterCheckbox = new Button(parent, SWT.CHECK);
		qtFilterCheckbox.setText(Messages.QtBuildTargetsPage_filterCheckboxText);
		qtFilterCheckbox.setToolTipText(Messages.QtBuildTargetsPage_filterCheckboxToolTip);
		qtFilterCheckbox.setSelection(true); // default to checked
       	filteringContentProviderWrapper.setFilter(sdkFilter);
       	filteringContentProviderWrapper.setConfigFilter(configFilter);
       	qtFilterCheckbox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (qtFilterCheckbox.getSelection()) {
			       	filteringContentProviderWrapper.setFilter(sdkFilter);
			       	filteringContentProviderWrapper.setConfigFilter(configFilter);
				} else {
			       	filteringContentProviderWrapper.setFilter(null);
			       	filteringContentProviderWrapper.setConfigFilter(null);
				}
				viewer.refresh();
				setPageComplete(validatePage());
			}
        });
       	qtFilterCheckbox.setData(UID, "qtFilterCheckbox"); //$NON-NLS-1$
	}

	protected boolean validatePage() {
		if (viewer.getTree().getItemCount() == 0) {
			if (qtFilterCheckbox.getSelection())
				setErrorMessage(Messages.QtBuildTargetsPage_noQtConfigsError);
			return false;
		}
		return super.validatePage();
	}	
}
