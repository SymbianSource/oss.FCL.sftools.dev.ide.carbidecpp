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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.nokia.cpp.internal.api.utils.core.Check;

import cwdbg.PreferenceConstants;

public class StopModeRomImageWizardPage extends WizardPage {
    		
    private boolean isDisposed = false;
    
    private boolean softAttachOption = false;
    private Long startAddressValue;
    private String romLogFile;
	private String romImageFile;	
	
	private Button softAttachButton;
	private Button debugFromStartButton;
	
	private Label startAddressLabel;
	private Text startAddress; 
	private Label romLogFileLabel;
	private Text romLogFilePath;
	private Button romLogFileBrowse;
	private Label romImageLabel;
	private Text romImagePath;
	private Button romImageBrowse;

	private final ISummaryTextItemContainer summaryTextItemContainer;

	
    public StopModeRomImageWizardPage(ISummaryTextItemContainer summaryTextItemContainer) {
        super(Messages.getString("StopModeRomImageWizardPage.0")); //$NON-NLS-1$
		Check.checkArg(summaryTextItemContainer);
		this.summaryTextItemContainer = summaryTextItemContainer;
        setPageComplete(false);
        setTitle(Messages.getString("StopModeRomImageWizardPage.0")); //$NON-NLS-1$
        setDescription(Messages.getString("StopModeRomImageWizardPage.1")); //$NON-NLS-1$
    }
    public void dispose() {
    	isDisposed = true;
    	
    	softAttachOption = softAttachButton.getSelection();
    	
		String startAddressText = startAddress.getText().trim().toLowerCase();
		
		int index = startAddressText.indexOf('x');
		if (index > 0)
		{
			startAddressText = startAddressText.substring(2); //ignore 0x or 0X.
		}
		startAddressValue = Long.parseLong(startAddressText, 16);
		
    	romLogFile = romLogFilePath.getText().trim();
    	romImageFile = romImagePath.getText().trim();
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

    	softAttachButton = new Button(composite, SWT.RADIO);
		softAttachButton.setText(Messages.getString("StopModeRomImageWizardPage.15")); //$NON-NLS-1$
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		softAttachButton.setLayoutData(data);
		softAttachButton.setToolTipText(Messages.getString("StopModeRomImageWizardPage.16")); //$NON-NLS-1$
		softAttachButton.setSelection(false);
		softAttachButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				checkControlState();
			}
		});
		softAttachButton.setData(".uid", "StopModeRunImageWizard.softAttachButton");
        
		debugFromStartButton = new Button(composite, SWT.RADIO);
		debugFromStartButton.setText(Messages.getString("StopModeRomImageWizardPage.17")); //$NON-NLS-1$
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		debugFromStartButton.setLayoutData(data);
		debugFromStartButton.setToolTipText(Messages.getString("StopModeRomImageWizardPage.18")); //$NON-NLS-1$
		debugFromStartButton.setSelection(true);
		debugFromStartButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				checkControlState();
			}
		});
		debugFromStartButton.setData(".uid", "StopModeRunImageWizard.debugFromStartButton");

		startAddressLabel = new Label(composite, SWT.NONE);
		startAddressLabel.setText(Messages.getString("StopModeRomImageWizardPage.3")); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 3;
		startAddressLabel.setLayoutData(data);
		startAddressLabel.setToolTipText(Messages.getString("StopModeRomImageWizardPage.4")); //$NON-NLS-1$
		startAddressLabel.setData(".uid", "StopModeRunImageWizard.startAddressLabel");
		
		startAddress = new Text(composite, SWT.BORDER);
		startAddress.setText("0x0"); //$NON-NLS-1$
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 1;
		startAddress.setLayoutData(data);
		startAddress.setToolTipText(Messages.getString("StopModeRomImageWizardPage.4")); //$NON-NLS-1$
		startAddress.setData(".uid", "StopModeRunImageWizard.startAddress");
		
		romImageLabel = new Label(composite, SWT.NONE);
		romImageLabel.setText(Messages.getString("StopModeRomImageWizardPage.11")); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 3;
		romImageLabel.setLayoutData(data);
		romImageLabel.setToolTipText(Messages.getString("StopModeRomImageWizardPage.12")); //$NON-NLS-1$
		romImageLabel.setData(".uid", "StopModeRunImageWizard.romImageLabel");
		
		romImagePath = new Text(composite, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		romImagePath.setLayoutData(data);
		romImagePath.setToolTipText(Messages.getString("StopModeRomImageWizardPage.12")); //$NON-NLS-1$
		romImagePath.setData(".uid", "StopModeRunImageWizard.romImagePath");
		
		romImageBrowse = ControlFactory.createPushButton(composite, Messages.getString("StopModeRomImageWizardPage.7")); //$NON-NLS-1$
		romImageBrowse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				FileDialog dialog = new FileDialog(getShell(), SWT.NONE);

				dialog.setText(Messages.getString("StopModeRomImageWizardPage.13")); //$NON-NLS-1$
				dialog.setFilterExtensions(new String[] {"*.img*", "*.*"}); //$NON-NLS-1$ //$NON-NLS-2$
				dialog.setFilterNames(new String[] {Messages.getString("StopModeRomImageWizardPage.14"), Messages.getString("StopModeRomImageWizardPage.10")}); //$NON-NLS-1$ //$NON-NLS-2$

				String result = dialog.open();
				if (result != null && new File(result).exists()) {
					romImagePath.setText(result);					
				}
			}
		});
		romImageBrowse.setData(".uid", "StopModeRunImageWizard.romImageBrowse");
		
		romLogFileLabel = new Label(composite, SWT.NONE);
		romLogFileLabel.setText(Messages.getString("StopModeRomImageWizardPage.5")); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 3;
		romLogFileLabel.setLayoutData(data);
		romLogFileLabel.setToolTipText(Messages.getString("StopModeRomImageWizardPage.6")); //$NON-NLS-1$
		romLogFileLabel.setData(".uid", "StopModeRunImageWizard.romLogFileLabel");
		
		romLogFilePath = new Text(composite, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		romLogFilePath.setLayoutData(data);
		romLogFilePath.setToolTipText(Messages.getString("StopModeRomImageWizardPage.6")); //$NON-NLS-1$		
		romLogFilePath.setData(".uid", "StopModeRunImageWizard.romLogFilePath");
		
		romLogFileBrowse = ControlFactory.createPushButton(composite, Messages.getString("StopModeRomImageWizardPage.7")); //$NON-NLS-1$
		romLogFileBrowse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				FileDialog dialog = new FileDialog(getShell(), SWT.NONE);

				dialog.setText(Messages.getString("StopModeRomImageWizardPage.8")); //$NON-NLS-1$
				dialog.setFilterExtensions(new String[] {"*.log*", "*.*"}); //$NON-NLS-1$ //$NON-NLS-2$
				dialog.setFilterNames(new String[] {Messages.getString("StopModeRomImageWizardPage.9"), Messages.getString("StopModeRomImageWizardPage.10")}); //$NON-NLS-1$ //$NON-NLS-2$

				String result = dialog.open();
				if (result != null && new File(result).exists()) {
					romLogFilePath.setText(result);					
				}
			}
		});
		romLogFileBrowse.setData(".uid", "StopModeRunImageWizard.romLogFileBrowse");
	
		setControl(composite);
        Dialog.applyDialogFont(parent);
        setPageComplete(true);
    }
    
    void updateConfiguration(ILaunchConfigurationWorkingCopy config) {
    	if (isDisposed) {
    		if (softAttachOption)
    			config.setAttribute( PreferenceConstants.J_PN_DebugRunFromStart, PreferenceConstants.J_PV_DebugRunFromStart_Attach);
    		else
    			config.setAttribute( PreferenceConstants.J_PN_DebugRunFromStart, PreferenceConstants.J_PV_DebugRunFromStart_Debug);

    		config.setAttribute( PreferenceConstants.J_PN_RomImgStartAddress , startAddressValue.intValue());
    		config.setAttribute( PreferenceConstants.J_PN_DownloadAddress , startAddressValue.intValue());
    		config.setAttribute(PreferenceConstants.J_PN_RomLogFilePath, romLogFile);
    		if (romLogFile.length()>0 && new File(romLogFile).exists()) {
    			config.setAttribute(PreferenceConstants.J_PN_ParseRomLogFile, true);
    			setEpoc32DirFromLogFile(config, romLogFile);    		
    		}
    		
    		config.setAttribute(PreferenceConstants.J_PN_RomImagePath, romImageFile);
     		if (romImageFile.length()>0 && new File(romImageFile).exists()) {
    			config.setAttribute(PreferenceConstants.J_PN_DownloadRomImage, true);
    		}

    	} else {     		
    		if (softAttachButton.getSelection())
    			config.setAttribute( PreferenceConstants.J_PN_DebugRunFromStart, PreferenceConstants.J_PV_DebugRunFromStart_Attach);
    		else
    			config.setAttribute( PreferenceConstants.J_PN_DebugRunFromStart, PreferenceConstants.J_PV_DebugRunFromStart_Debug);

    		String startAddressText = startAddress.getText().trim().toLowerCase();
			
    		int index = startAddressText.indexOf('x');
    		if (index > 0)
    		{
    			startAddressText = startAddressText.substring(2); //ignore 0x or 0X.
    		}
    		Long longValue = Long.parseLong(startAddressText, 16);
    		config.setAttribute( PreferenceConstants.J_PN_RomImgStartAddress , longValue.intValue());
    		config.setAttribute( PreferenceConstants.J_PN_DownloadAddress , longValue.intValue());

    		config.setAttribute(PreferenceConstants.J_PN_RomLogFilePath, romLogFilePath.getText().trim());    		
    		if ((romLogFilePath.getText().trim().length()>0) && (new File(romLogFilePath.getText().trim()).exists())) {
    			config.setAttribute(PreferenceConstants.J_PN_ParseRomLogFile, true);
    			setEpoc32DirFromLogFile(config, romLogFilePath.getText().trim());
    		}
    		
    		config.setAttribute(PreferenceConstants.J_PN_RomImagePath, romImagePath.getText());
    		if ( (romImagePath.getText().trim().length()>0) && (new File(romImagePath.getText().trim()).exists()) ) {
    			config.setAttribute(PreferenceConstants.J_PN_DownloadRomImage, true);
    		}
    	}
    }
    
    // This function sets the epoc32 dir only if its not already set by default
    void setEpoc32DirFromLogFile(ILaunchConfigurationWorkingCopy config, String romLogFile) {
    	// now check to see if the epoc32 directory is set from the project
		// If not, try to set it from log file path.
		try {
			String epoc32Dir = config.getAttribute( PreferenceConstants.J_PN_SymbianKitEpoc32Dir , "" ); //$NON-NLS-1$
			if (epoc32Dir.length() <= 0) {
				
				int epoc32Index = romLogFile.toLowerCase().indexOf("\\epoc32"); //$NON-NLS-1$
				if (epoc32Index > 0) {
					epoc32Dir = romLogFile.substring(0, epoc32Index) + "\\epoc32\\"; //$NON-NLS-1$
					config.setAttribute(PreferenceConstants.J_PN_SymbianKitEpoc32Dir, epoc32Dir);
				}    					    					
			}
			
		} catch (CoreException e) {
			e.printStackTrace();
		}
    }

    public void setVisible(boolean visible) {
    	super.setVisible(visible);
    	if (!visible) {
    		if (softAttachButton.getSelection()) {
	    		summaryTextItemContainer.putSummaryTextItem("SoftAttach",  //$NON-NLS-1$
	    				MessageFormat.format("{0}", new Object[] { //$NON-NLS-1$
	    						Messages.getString("StopModeRomImageWizardPage.15") } )); //$NON-NLS-1$
	    		summaryTextItemContainer.putSummaryTextItem("StartAddress", null);  //$NON-NLS-1$
	    		summaryTextItemContainer.putSummaryTextItem("RomImage", null); //$NON-NLS-1$
    		}
    		else {
    			summaryTextItemContainer.putSummaryTextItem("SoftAttach", null); //$NON-NLS-1$
	    		summaryTextItemContainer.putSummaryTextItem("StartAddress",  //$NON-NLS-1$
	    				MessageFormat.format("{0} {1}", new Object[] { //$NON-NLS-1$
	    						Messages.getString("StopModeRomImageWizardPage.17"), //$NON-NLS-1$
	    						startAddress.getText() } ));
	    		summaryTextItemContainer.putSummaryTextItem("RomImage",  //$NON-NLS-1$
	    				MessageFormat.format("{0} {1}", new Object[] { //$NON-NLS-1$
	    						Messages.getString("StopModeRomImageWizardPage.11"), //$NON-NLS-1$
	    						romImagePath.getText() } ));
    		}
    		summaryTextItemContainer.putSummaryTextItem("RomLogFile",  //$NON-NLS-1$
    				MessageFormat.format("{0} {1}", new Object[] { //$NON-NLS-1$
    						Messages.getString("StopModeRomImageWizardPage.5"), //$NON-NLS-1$
    						romLogFilePath.getText() } ));
    	}
    }
    
    @Override
    public void performHelp() {
		PlatformUI.getWorkbench().getHelpSystem().displayHelp(LaunchWizardHelpIds.WIZARD_STOP_MODE_ROM_IMG_PAGE);
    }
    
    private void checkControlState() {    	
    	startAddress.setEnabled(!softAttachButton.getSelection());
    	romImagePath.setEnabled(!softAttachButton.getSelection());    	    	
    }
}