/*
* Copyright (c) 2007-2009 Nokia Corporation and/or its subsidiary(-ies). 
* All rights reserved.
* This component and the accompanying materials are made available
* under the terms of the License "Eclipse Public License v1.0"
* which accompanies this distribution, and is available
* at the URL "http://www.eclipse.org/legal/epl-v10.html".
*
* Initial Contributors:
* Nokia Corporation - initial contribution.
*
*/
package com.nokia.carbide.cpp.internal.leavescan.ui;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import com.nokia.carbide.cpp.internal.leavescan.LeavescanPlugin;

public class LeavescanPreferences extends PreferencePage implements
		IWorkbenchPreferencePage,Listener {

	private Text leaveScanDirEditText;
	private Button browseButton;
	private Button veryNoisyOuputButton;
	public static final String LEAVESCAN_PREF_HELP_ID = LeavescanPlugin.PLUGIN_ID + ".leavescan_prefs_page"; //$NON-NLS-1$

	/**
	 * Create the preference page
	 */
	public LeavescanPreferences() {
		super();
	}

	/**
	 * Create contents of the preference page
	 * @param parent
	 */
	@Override
	public Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		container.setLayout(gridLayout);

		final Label leavescanDirectoryLabel = new Label(container, SWT.NONE);
		leavescanDirectoryLabel.setToolTipText("Choose the directory where leavescan.exe lives.");
		leavescanDirectoryLabel.setText("Leavescan Directory:");

		leaveScanDirEditText = new Text(container, SWT.BORDER);
		leaveScanDirEditText.setLayoutData(new GridData(250, SWT.DEFAULT));

		browseButton = new Button(container, SWT.NONE);
		browseButton.setToolTipText("Choose the directory where leavescan.exe lives.");
		browseButton.setText("Browse...");
		browseButton.addListener(SWT.Selection, this);
		
		veryNoisyOuputButton = new Button(container, SWT.CHECK);
		veryNoisyOuputButton.setToolTipText("When enabled, -N is passed to leavescan for very verbose output. Otherwise -n is passed for minimal output.");
		veryNoisyOuputButton.setText("Very noisy ouput");
		
		getPrefsStoreValues();
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(super.getControl(), LEAVESCAN_PREF_HELP_ID);
		
		return container;
	}

	/**
	 * Initialize the preference page
	 */
	public void init(IWorkbench workbench) {
		// Initialize the preference page
	}
	
	public void handleEvent(Event event) {
		if (event.widget == browseButton) {
			handleBrowse();
		} 
		
	}
	
	private void getPrefsStoreValues(){
		IPreferenceStore store = LeavescanPlugin.getLeaveScanPrefsStore();
		boolean noisyOutput =  store.getBoolean(LeavescanPreferenceConstants.LEAVESCAN_NOISY_OUTPUT);
		veryNoisyOuputButton.setSelection(noisyOutput);
		
		String leaveScanFolder = store.getString(LeavescanPreferenceConstants.LEAVESCAN_FOLDER);
		leaveScanDirEditText.setText(leaveScanFolder);
	}

	@Override
	protected void performDefaults() {
		veryNoisyOuputButton.setSelection(false);
		leaveScanDirEditText.setText("");
		super.performDefaults();
	}

	@Override
	public boolean performOk() {
			
		IPreferenceStore store = LeavescanPlugin.getLeaveScanPrefsStore();
	
		String leaveScanDir = leaveScanDirEditText.getText();
		if (leaveScanDir.length() > 0){
			if (!leaveScanDir.endsWith("\\")){
				leaveScanDir += "\\";
				leaveScanDirEditText.setText(leaveScanDir);
			}
		}
		
		if (leaveScanDir.length() > 0){
			IPath leaveScanPath = new Path(leaveScanDir);
			if (!leaveScanPath.toFile().exists()){
				if (!MessageDialog.openQuestion(getShell(), "Directory does not exist.", "The directory " + leaveScanPath + " does not exist.\n\nDo you want to continue?")){
					return false;
				}
			}
		}
		
		store.setValue(LeavescanPreferenceConstants.LEAVESCAN_FOLDER, leaveScanDir);
		store.setValue(LeavescanPreferenceConstants.LEAVESCAN_NOISY_OUTPUT, veryNoisyOuputButton.getSelection());
		
		return super.performOk();
	}
	
	private void handleBrowse() {
	    String selectedDir = showBrowseDialog();
	    if (selectedDir != null) {
	        if (!selectedDir.equals(leaveScanDirEditText.getText())) {
	        	leaveScanDirEditText.setText(selectedDir);
	        }
	    }
	}

    private String showBrowseDialog() {
        DirectoryDialog dialog = new DirectoryDialog(leaveScanDirEditText.getShell(), SWT.OPEN);
        dialog.setText("Choose a folder...");
        dialog.setFilterPath(leaveScanDirEditText.getText());
        return dialog.open();
    }

}
