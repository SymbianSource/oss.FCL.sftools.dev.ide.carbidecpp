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
import com.nokia.cpp.internal.api.utils.ui.BrowseDialogUtils;

import cwdbg.PreferenceConstants;

public class SophiaWizardPage extends WizardPage {
    		
    private boolean isDisposed = false;
    
	private Label sophiaSTIDLLLabel;
	private Text sophiaSTIDLLPath;
	private Button sophiaSTIDLLBrowse;
	private String sophiaSTIDLL;

	private Label targetInitFileLabel;
	private Text targetInitFilePath;
	private Button targetInitFileBrowse;
	private String targetInitFile;

	private Combo processorsList;
	private int selectedProcIndex;

	private final ISummaryTextItemContainer summaryTextItemContainer;
	
    public SophiaWizardPage(ISummaryTextItemContainer summaryTextItemContainer) {
        super(Messages.getString("SophiaWizardPage.0")); //$NON-NLS-1$
		Check.checkArg(summaryTextItemContainer);
		this.summaryTextItemContainer = summaryTextItemContainer;
        setPageComplete(false);
        setTitle(Messages.getString("SophiaWizardPage.0")); //$NON-NLS-1$
        setDescription(Messages.getString("SophiaWizardPage.1")); //$NON-NLS-1$
    }
    public void dispose() {
    	isDisposed = true;
    	sophiaSTIDLL = sophiaSTIDLLPath.getText().trim();
    	targetInitFile = targetInitFilePath.getText().trim();
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

        sophiaSTIDLLLabel = new Label(composite, SWT.NONE);
		sophiaSTIDLLLabel.setText(Messages.getString("SophiaWizardPage.2")); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 3;
		sophiaSTIDLLLabel.setLayoutData(data);
		sophiaSTIDLLLabel.setToolTipText(Messages.getString("SophiaWizardPage.3")); //$NON-NLS-1$
		sophiaSTIDLLLabel.setData(".uid", "SophiaWizardPage.sophiaSTIDLLLabel");

		sophiaSTIDLLPath = new Text(composite, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);	
		data.horizontalSpan = 2;
		sophiaSTIDLLPath.setLayoutData(data);
		sophiaSTIDLLPath.setToolTipText(Messages.getString("SophiaWizardPage.3")); //$NON-NLS-1$
		sophiaSTIDLLPath.setData(".uid", "SophiaWizardPage.sophiaSTIDLLPath");
		
		sophiaSTIDLLBrowse = ControlFactory.createPushButton(composite, Messages.getString("SophiaWizardPage.13")); //$NON-NLS-1$
		sophiaSTIDLLBrowse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				FileDialog dialog = new FileDialog(getShell(), SWT.NONE);

				dialog.setText(Messages.getString("SophiaWizardPage.4")); //$NON-NLS-1$
				dialog.setFilterExtensions(new String[] {"*.dll*", "*.*"}); //$NON-NLS-1$ //$NON-NLS-2$
				dialog.setFilterNames(new String[] {Messages.getString("SophiaWizardPage.5"), Messages.getString("SophiaWizardPage.6")}); //$NON-NLS-1$ //$NON-NLS-2$

				BrowseDialogUtils.initializeFrom(dialog, sophiaSTIDLLPath);

				String result = dialog.open();
				if (result != null && new File(result).exists()) {
					sophiaSTIDLLPath.setText(result);					
				}
			}
		});
		sophiaSTIDLLBrowse.setData(".uid", "SophiaWizardPage.sophiaSTIDLLBrowse");

		targetInitFileLabel = new Label(composite, SWT.NONE);
		targetInitFileLabel.setText(Messages.getString("SophiaWizardPage.7")); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 3;
		targetInitFileLabel.setLayoutData(data);
		targetInitFileLabel.setToolTipText(Messages.getString("SophiaWizardPage.8")); //$NON-NLS-1$
		targetInitFileLabel.setData(".uid", "SophiaWizardPage.targetInitFileLabel");

		targetInitFilePath = new Text(composite, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);	
		data.horizontalSpan = 2;
		targetInitFilePath.setLayoutData(data);
		targetInitFilePath.setToolTipText(Messages.getString("SophiaWizardPage.8")); //$NON-NLS-1$
		targetInitFilePath.setData(".uid", "SophiaWizardPage.targetInitFilePath");
		
		targetInitFileBrowse = ControlFactory.createPushButton(composite, Messages.getString("SophiaWizardPage.13")); //$NON-NLS-1$
		targetInitFileBrowse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				FileDialog dialog = new FileDialog(getShell(), SWT.NONE);

				dialog.setText(Messages.getString("SophiaWizardPage.9")); //$NON-NLS-1$
				dialog.setFilterExtensions(new String[] {"*.cfg*", "*.*"}); //$NON-NLS-1$ //$NON-NLS-2$
				dialog.setFilterNames(new String[] {Messages.getString("SophiaWizardPage.10"), Messages.getString("SophiaWizardPage.6")}); //$NON-NLS-1$ //$NON-NLS-2$

				BrowseDialogUtils.initializeFrom(dialog, targetInitFilePath);

				String result = dialog.open();
				if (result != null && new File(result).exists()) {
					targetInitFilePath.setText(result);					
				}
			}
		});
		targetInitFileBrowse.setData(".uid", "SophiaWizardPage.targetInitFileBrowse");

		final Label processorsLabel = new Label(composite, SWT.NONE);
		processorsLabel.setText(Messages.getString("SophiaWizardPage.11")); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 3;
		processorsLabel.setLayoutData(data);
		processorsLabel.setToolTipText(Messages.getString("SophiaWizardPage.12")); //$NON-NLS-1$
		processorsLabel.setData(".uid", "SophiaWizardPage.processorsLabel");

		processorsList = new Combo(composite, SWT.READ_ONLY);
		processorsList.setItems(new String[] { "ARM920T", "ARM926TEJ", "ARM1136EJ-S", "OMAP3xx", "OMAP15xx", "OMAP16xx", "OMAP24xx", "OMAP34xx", "Generic" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$
		processorsList.select(8); //by default, select the generic 
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.horizontalSpan = 3;
		processorsList.setLayoutData(data);
		processorsList.setToolTipText(Messages.getString("SophiaWizardPage.12")); //$NON-NLS-1$
		processorsList.setData(".uid", "SophiaWizardPage.processorsList");

		setControl(composite);
        Dialog.applyDialogFont(parent);
        setPageComplete(true);
    }
    
    void updateConfiguration(ILaunchConfigurationWorkingCopy config) {
    	if (isDisposed) {
    		if (sophiaSTIDLL.length()>0 && new File(sophiaSTIDLL).exists()) {
    			config.setAttribute(SettingsData.spn_SophiaSTIConn_DllPath, sophiaSTIDLL);
    		} 
    		
    		if (targetInitFile.length()>0 && new File(targetInitFile).exists()) {
    			config.setAttribute(PreferenceConstants.J_PN_TargetInitFilePath, targetInitFile);
    			config.setAttribute(PreferenceConstants.J_PN_RunTargetInitFile, true);
    		} 
  
    		config.setAttribute(PreferenceConstants.J_PN_TargetProcessor, selectedProcIndex);
    	} else {    		
    		if ((sophiaSTIDLLPath.getText().trim().length()>0) && (new File(sophiaSTIDLLPath.getText().trim()).exists())) {
    			config.setAttribute(SettingsData.spn_SophiaSTIConn_DllPath, sophiaSTIDLLPath.getText());
    		}
    		
      		if (targetInitFilePath.getText().trim().length()>0 && new File(targetInitFilePath.getText().trim()).exists()) {
    			config.setAttribute(PreferenceConstants.J_PN_TargetInitFilePath, targetInitFilePath.getText().trim());
    			config.setAttribute(PreferenceConstants.J_PN_RunTargetInitFile, true);
    		}    

    		config.setAttribute( PreferenceConstants.J_PN_TargetProcessor, processorsList.getSelectionIndex());
    	}
    }

    public void setVisible(boolean visible) {
    	super.setVisible(visible);
    	if (!visible) {
    		summaryTextItemContainer.putSummaryTextItem("SophiaTargetInterface",  //$NON-NLS-1$
    				MessageFormat.format("{0} {1}", new Object[] { //$NON-NLS-1$
    						Messages.getString("SophiaWizardPage.2"), //$NON-NLS-1$
    						sophiaSTIDLLPath.getText() } ));
    		summaryTextItemContainer.putSummaryTextItem("TargetInitFile",  //$NON-NLS-1$
    				MessageFormat.format("{0} {1}", new Object[] { //$NON-NLS-1$
    						Messages.getString("SophiaWizardPage.7"), //$NON-NLS-1$
    						targetInitFilePath.getText() } ));
    		summaryTextItemContainer.putSummaryTextItem("TargetProcessor",  //$NON-NLS-1$
    				MessageFormat.format("{0} {1}", new Object[] { //$NON-NLS-1$
    						Messages.getString("SophiaWizardPage.11"), //$NON-NLS-1$
    						processorsList.getItem(processorsList.getSelectionIndex()) } ));
    	}
    }
    
    @Override
    public void performHelp() {
		PlatformUI.getWorkbench().getHelpSystem().displayHelp(LaunchWizardHelpIds.WIZARD_SOPHIA_PAGE);
    }
}