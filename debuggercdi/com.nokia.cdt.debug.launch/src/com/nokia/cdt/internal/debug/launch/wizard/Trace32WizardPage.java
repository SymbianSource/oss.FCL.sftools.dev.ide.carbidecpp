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
package com.nokia.cdt.internal.debug.launch.wizard;

import java.io.File;
import java.text.MessageFormat;

import org.eclipse.cdt.utils.ui.controls.ControlFactory;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.nokia.cdt.debug.cw.symbian.SettingsData;
import com.nokia.cpp.internal.api.utils.core.Check;

import cwdbg.PreferenceConstants;

public class Trace32WizardPage extends WizardPage {
    		
    private boolean isDisposed = false;
    
	private Label t32BootConfigLabel;
	private Text t32BootConfigFilePath;
	private Button t32BootConfigFileBrowse;
	private String t32CMMScript;
	
	private Combo processorsList;
	private int selectedProcIndex;

	private final ISummaryTextItemContainer summaryTextItemContainer;
	
    public Trace32WizardPage(ISummaryTextItemContainer summaryTextItemContainer) {
        super(Messages.getString("Trace32WizardPage.0")); //$NON-NLS-1$
		Check.checkArg(summaryTextItemContainer);
		this.summaryTextItemContainer = summaryTextItemContainer;
        setPageComplete(false);
        setTitle(Messages.getString("Trace32WizardPage.0")); //$NON-NLS-1$
        setDescription(Messages.getString("Trace32WizardPage.1")); //$NON-NLS-1$
    }
    
    public void dispose() {
    	isDisposed = true;
    	t32CMMScript = t32BootConfigFilePath.getText().trim();
    	selectedProcIndex = processorsList.getSelectionIndex();
    	super.dispose();
    }

	/*
     * @see IDialogPage#createControl(Composite)
     */
    public void createControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NULL);
        GridLayout layout = new GridLayout();
        layout.numColumns = 3;
        composite.setLayout(layout);
        
        GridData data = new GridData();
        composite.setLayoutData(data);

		t32BootConfigLabel = new Label(composite, SWT.NONE);
		t32BootConfigLabel.setText(Messages.getString("Trace32WizardPage.2")); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 3;
		t32BootConfigLabel.setLayoutData(data);
		t32BootConfigLabel.setToolTipText(Messages.getString("Trace32WizardPage.3")); //$NON-NLS-1$
		t32BootConfigLabel.setData(".uid", "Trace32WizardPage.t32BootConfigLabel");

		t32BootConfigFilePath = new Text(composite, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);	
		data.horizontalSpan = 2;
		t32BootConfigFilePath.setLayoutData(data);
		t32BootConfigFilePath.setToolTipText(Messages.getString("Trace32WizardPage.3")); //$NON-NLS-1$
		t32BootConfigFilePath.setData(".uid", "Trace32WizardPage.t32BootConfigFilePath");
		
		t32BootConfigFileBrowse = ControlFactory.createPushButton(composite, Messages.getString("Trace32WizardPage.9")); //$NON-NLS-1$
		t32BootConfigFileBrowse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				FileDialog dialog = new FileDialog(getShell(), SWT.NONE);

				dialog.setText(Messages.getString("Trace32WizardPage.4")); //$NON-NLS-1$
				dialog.setFilterExtensions(new String[] {"*.cmm*", "*.*"}); //$NON-NLS-1$ //$NON-NLS-2$
				dialog.setFilterNames(new String[] {Messages.getString("Trace32WizardPage.5"), Messages.getString("Trace32WizardPage.6")}); //$NON-NLS-1$ //$NON-NLS-2$

				String result = dialog.open();
				if (result != null && new File(result).exists()) {
					t32BootConfigFilePath.setText(result);					
				}
			}
		});
		t32BootConfigFileBrowse.setData(".uid", "Trace32WizardPage.t32BootConfigFileBrowse");
		
		final Label processorsLabel = new Label(composite, SWT.NONE);
		processorsLabel.setText(Messages.getString("Trace32WizardPage.7")); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 3;
		processorsLabel.setLayoutData(data);
		processorsLabel.setToolTipText(Messages.getString("Trace32WizardPage.8")); //$NON-NLS-1$
		processorsLabel.setData(".uid", "Trace32WizardPage.processorsLabel");
		
		processorsList = new Combo(composite, SWT.READ_ONLY);
		processorsList.setItems(new String[] { "ARM920T", "ARM926TEJ", "ARM1136EJ-S", "OMAP3xx", "OMAP15xx", "OMAP16xx", "OMAP24xx", "OMAP34xx", "Generic" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$
		processorsList.select(8); //by default, select the generic 
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.horizontalSpan = 3;
		processorsList.setLayoutData(data);
		processorsList.setToolTipText(Messages.getString("Trace32WizardPage.8")); //$NON-NLS-1$
		processorsList.setData(".uid", "Trace32WizardPage.processorsList");
		
		setControl(composite);
        Dialog.applyDialogFont(parent);
        setPageComplete(true);
    }
    
    void updateConfiguration(ILaunchConfigurationWorkingCopy config) {
    	if (isDisposed) {
    		if (t32CMMScript.length()>0 && new File(t32CMMScript).exists()) {
    			config.setAttribute(SettingsData.spn_Trace32Conn_BootScriptFile, t32CMMScript);
    		}    		
    		config.setAttribute(PreferenceConstants.J_PN_TargetProcessor, selectedProcIndex);
    	} else {    		
    		if ((t32BootConfigFilePath.getText().trim().length()>0) && (new File(t32BootConfigFilePath.getText().trim()).exists())) {
    			config.setAttribute(SettingsData.spn_Trace32Conn_BootScriptFile, t32BootConfigFilePath.getText());
    		}
    		config.setAttribute( PreferenceConstants.J_PN_TargetProcessor, processorsList.getSelectionIndex());
    	}
    }
    
    public void setVisible(boolean visible) {
    	super.setVisible(visible);
    	if (!visible) {
			summaryTextItemContainer.putSummaryTextItem("T32InitScript",  //$NON-NLS-1$
    				MessageFormat.format("{0} {1}", new Object[] { //$NON-NLS-1$
    						Messages.getString("Trace32WizardPage.2"), //$NON-NLS-1$
    						t32BootConfigFilePath.getText() } ));
    		summaryTextItemContainer.putSummaryTextItem("TargetProcessor",  //$NON-NLS-1$
    				MessageFormat.format("{0} {1}", new Object[] { //$NON-NLS-1$
    						Messages.getString("Trace32WizardPage.7"), //$NON-NLS-1$
    						processorsList.getItem(processorsList.getSelectionIndex()) } ));
    	}
    }
    
    @Override
    public void performHelp() {
		PlatformUI.getWorkbench().getHelpSystem().displayHelp(LaunchWizardHelpIds.WIZARD_TRACE32_PAGE);
    }
}