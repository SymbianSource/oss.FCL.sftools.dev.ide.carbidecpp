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
package com.nokia.carbide.cpp.internal.project.ui.sharedui;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.nokia.carbide.cpp.internal.project.ui.Messages;
import com.nokia.carbide.cpp.sdk.ui.shared.BuildTargetsPage;

public class BuilderSelectionComposite extends Composite {

    private static final String LAST_BUILDER_STORE = "NewProjectPage.LAST_BUILDER_STORE"; //$NON-NLS-1$

	private Combo builderCombo;
    private boolean useSBSv2Builder;

    
    public BuilderSelectionComposite(Composite parent) {
		super(parent, SWT.NONE);
	}
    
    public void createControls() {
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        setLayout(layout);
        setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label builderLabel = new Label(this, SWT.NONE);
		builderLabel.setText(Messages.getString("NewProjectPage.builderLabel")); //$NON-NLS-1$
		builderLabel.setToolTipText(Messages.getString("NewProjectPage.builderToolTip")); //$NON-NLS-1$
		builderLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
			
		builderCombo = new Combo(this, SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
		builderCombo.setToolTipText(Messages.getString("NewProjectPage.builderToolTip")); //$NON-NLS-1$
		builderCombo.setLayoutData(new GridData());
		builderCombo.setData(".uid", "builderCombo"); //$NON-NLS-1$ //$NON-NLS-2$
		builderCombo.add(Messages.getString("NewProjectPage.sbsv1")); //$NON-NLS-1$
		builderCombo.add(Messages.getString("NewProjectPage.sbsv2")); //$NON-NLS-1$
		builderCombo.setData(".uid", "builderCombo"); //$NON-NLS-1$ //$NON-NLS-2$
		builderCombo.select(0);
		builderCombo.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				validatePage();
			}
			
		});
    }

    public boolean validatePage() {
		useSBSv2Builder = false;
		if (builderCombo != null && builderCombo.getSelectionIndex() == 1) {
			useSBSv2Builder = true;
		}

		getShell().setData(BuildTargetsPage.SBSV2BUILDER, new Boolean(useSBSv2Builder));
		
		return true;
    }

    public void saveDialogSettings(IDialogSettings settings) {
        if (settings != null) {
            // remember their builder selection
            if (builderCombo != null) {
                settings.put(LAST_BUILDER_STORE, builderCombo.getSelectionIndex());
            }
        }
    }

    public void restoreDialogSettings(IDialogSettings settings) {
        if (settings != null) {
            // restore their builder selection
        	if (builderCombo != null) {
            	String builderIndexString = settings.get(LAST_BUILDER_STORE);
            	if (builderIndexString != null) {
            		try {
                    	builderCombo.select(Integer.parseInt(builderIndexString));
            		} catch (Exception e) {
            			e.printStackTrace();
            		}
            	}
        	}
        }
    }
    
    public boolean useSBSv2Builder() {
    	return useSBSv2Builder;
    }
    
    public void setUseSBSv2Builder(boolean useSBSv2Builder) {
		this.useSBSv2Builder = useSBSv2Builder;
	}
}
