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

package com.nokia.carbide.cpp.internal.codescanner.ui;

import java.io.File;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.nokia.carbide.cpp.internal.codescanner.CSPlugin;
import com.nokia.carbide.cpp.internal.codescanner.Messages;
import com.nokia.cpp.internal.api.utils.ui.BrowseDialogUtils;

/**
 * A class to create and control the CodeScanner General Options tab page.
 */
public class CSGeneralTabPage extends Composite {
	
	public static final String CS_TOOLS_DIRECTORY = "tools";

	// private members for various controls
	private Label csDirLabel = null;
	private Label resultsDirLabel = null;
	private Text csDirEditText = null;
	private Text resultsDirEditText = null;
	private Button browseCSDirButton = null;
	private Button browseResultsDirButton = null;
	private Button htmlResultsButton = null;
	private Button xmlResultsButton = null;
	private Button kbScanButton = null;
	private Button autoscanButton = null;

	/**
	 * Create contents of this tab page.
	 * @param parent - the parent composite
	 */
	public CSGeneralTabPage(Composite parent) {
		super(parent, SWT.NONE);

		this.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		this.setLayout(gridLayout);

		// CodeScanner directory selection
		csDirLabel = new Label(this, SWT.RIGHT);
		csDirLabel.setText(Messages.getString("CSGeneralTabPage.CSDirLabel"));

		csDirEditText = new Text(this, SWT.BORDER);
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.widthHint = 200;
		csDirEditText.setLayoutData(gridData);

		browseCSDirButton = new Button(this, SWT.NONE);
		browseCSDirButton.setText(Messages.getString("CSGeneralTabPage.CSDirBrowseLabel"));
		browseCSDirButton.setToolTipText(Messages.getString("CSGeneralTabPage.CSDirBrowseMessage"));
		browseCSDirButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				handleCSDirBrowse();
			}
		});

		// generate HTML results?
		htmlResultsButton = new Button(this, SWT.CHECK);
		htmlResultsButton.setText(Messages.getString("CSGeneralTabPage.HTMLResultsLabel"));
		htmlResultsButton.setToolTipText(Messages.getString("CSGeneralTabPage.HTMLResultsMessage"));
		htmlResultsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				boolean enable = (htmlResultsButton.getSelection() || xmlResultsButton.getSelection());
				resultsDirEditText.setEnabled(enable);
				browseResultsDirButton.setEnabled(enable);
			}
		});

		// place holder label(s) to fill up unused columns in the layout
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		// generate XML results?
		xmlResultsButton = new Button(this, SWT.CHECK);
		xmlResultsButton.setText(Messages.getString("CSGeneralTabPage.XMLResultsLabel"));
		xmlResultsButton.setToolTipText(Messages.getString("CSGeneralTabPage.XMLResultsMessage"));
		xmlResultsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				boolean enable = (htmlResultsButton.getSelection() || xmlResultsButton.getSelection());
				resultsDirEditText.setEnabled(enable);
				browseResultsDirButton.setEnabled(enable);
			}
		});
				
		// place holder label(s) to fill up unused columns in the layout
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		// results directory selection
		resultsDirLabel = new Label(this, SWT.RIGHT);
		resultsDirLabel.setLayoutData(new GridData());
		resultsDirLabel.setText(Messages.getString("CSGeneralTabPage.ResultsDirLabel"));

		resultsDirEditText = new Text(this, SWT.BORDER);
		resultsDirEditText.setLayoutData(gridData);

		browseResultsDirButton = new Button(this, SWT.NONE);
		browseResultsDirButton.setLayoutData(new GridData());
		browseResultsDirButton.setText(Messages.getString("CSGeneralTabPage.ResultsDirBrowseLabel"));
		browseResultsDirButton.setToolTipText(Messages.getString("CSGeneralTabPage.ResultsDirBrowseMessage"));
		browseResultsDirButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				handleResultsDirBrowse();
			}
		});

		kbScanButton = new Button(this, SWT.CHECK);
		kbScanButton.setLayoutData(new GridData());
		kbScanButton.setText(Messages.getString("CSGeneralTabPage.KbScanLabel"));
		kbScanButton.setToolTipText(Messages.getString("CSGeneralTabPage.KbScanMessage"));

		// place holder label(s) to fill up unused columns in the layout
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		autoscanButton = new Button(this, SWT.CHECK);
		autoscanButton.setText(Messages.getString("CSGeneralTabPage.AutoscanLabel"));
		autoscanButton.setToolTipText(Messages.getString("CSGeneralTabPage.AutoscanMessage"));
		autoscanButton.setVisible(false);
	}

	/**
	 * Enables the receiver if the argument is true, and disables it otherwise.
	 * @param enabled - the new enable state.
	 */
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		csDirLabel.setEnabled(enabled);
		resultsDirLabel.setEnabled(enabled);
		csDirEditText.setEnabled(enabled);
		browseCSDirButton.setEnabled(enabled);
		htmlResultsButton.setEnabled(enabled);
		xmlResultsButton.setEnabled(enabled);
		kbScanButton.setEnabled(enabled);
		autoscanButton.setEnabled(enabled);
		boolean htmlResults = htmlResultsButton.getSelection();
		boolean xmlResults = xmlResultsButton.getSelection();
		if (!enabled || htmlResults || xmlResults) {
			resultsDirEditText.setEnabled(enabled);
			browseResultsDirButton.setEnabled(enabled);
		}
	}

	/**
	 * Set the default values of this tab page.
	 */
	public void setDefaults() {
		csDirEditText.setText(getDefaultCSDirectory());
		htmlResultsButton.setSelection(false);
		xmlResultsButton.setSelection(false);
		resultsDirEditText.setText("");
		resultsDirEditText.setEnabled(false);
		browseResultsDirButton.setEnabled(false);
		kbScanButton.setSelection(false);
		autoscanButton.setSelection(false);
	}

	/**
	 * Retrieve the stored preference settings values of this tab page.
	 */
	public void getStoredPreferenceValues() {
		IPreferenceStore store = CSPlugin.getCSPrefsStore();

		String csDir = store.getString(CSPreferenceConstants.CODESCANNER_FOLDER);
		csDirEditText.setText(csDir);

		boolean htmlResults = store.getBoolean(CSPreferenceConstants.HTML_RESULTS);
		htmlResultsButton.setSelection(htmlResults);

		boolean xmlResults = store.getBoolean(CSPreferenceConstants.XML_RESULTS);
		xmlResultsButton.setSelection(xmlResults);

		String csResultsFolder = store.getString(CSPreferenceConstants.RESULTS_FOLDER);
		resultsDirEditText.setText(csResultsFolder);
		resultsDirEditText.setEnabled(htmlResults || xmlResults);
		browseResultsDirButton.setEnabled(htmlResults || xmlResults);

		boolean kbScan = store.getBoolean(CSPreferenceConstants.KBSCANNING);
		kbScanButton.setSelection(kbScan);
	}
	
	/**
	 * Retrieve the stored property settings values of this tab page.
	 */
	public void getStoredPropertyValues(IDialogSettings pageSettings) {
		String csDir = pageSettings.get(CSPreferenceConstants.CODESCANNER_FOLDER);
		csDirEditText.setText(csDir);

		boolean htmlResults = pageSettings.getBoolean(CSPreferenceConstants.HTML_RESULTS);
		htmlResultsButton.setSelection(htmlResults);

		boolean xmlResults = pageSettings.getBoolean(CSPreferenceConstants.XML_RESULTS);
		xmlResultsButton.setSelection(xmlResults);

		String csResultsFolder = pageSettings.get(CSPreferenceConstants.RESULTS_FOLDER);
		resultsDirEditText.setText(csResultsFolder);
		resultsDirEditText.setEnabled(htmlResults || xmlResults);
		browseResultsDirButton.setEnabled(htmlResults || xmlResults);

		boolean kbScan = pageSettings.getBoolean(CSPreferenceConstants.KBSCANNING);
		kbScanButton.setSelection(kbScan);

		autoscanButton.setSelection(pageSettings.getBoolean(CSPreferenceConstants.AUTOSCAN));
	}
	
	/**
	 * Set the stored preference settings values of this tab page.
	 */
	public boolean setStoredPreferenceValues(){
		IPreferenceStore store = CSPlugin.getCSPrefsStore();

		// check the CodeScanner directory selected by user
		String csDir = csDirEditText.getText();
		if (csDir.length() == 0){
			csDir = getDefaultCSDirectory();
		}

		if (!csDir.endsWith(File.separator)){
			csDir += File.separator;
			csDirEditText.setText(csDir);
		}

		if (!validateDirectory(csDir)){
			return false;
		}
				
		boolean htmlResults = htmlResultsButton.getSelection();
		boolean xmlResults = xmlResultsButton.getSelection();

		// check the results directory selected by user (only when generating HTML or XML results)
		String resultDir = resultsDirEditText.getText();
		if ((htmlResults || xmlResults)) {
			if (resultDir.length() > 0) {
				if (!validateDirectory(resultDir)){
					return false;
				}			
			}
			else {
				// must specify results directory when generating HTML or XML results
				reportMissingResultsDir();
				return false;
			}
		}

		boolean kbScan = kbScanButton.getSelection();

		store.setValue(CSPreferenceConstants.CODESCANNER_FOLDER, csDir);
		store.setValue(CSPreferenceConstants.HTML_RESULTS, htmlResults);
		store.setValue(CSPreferenceConstants.XML_RESULTS, xmlResults);
		store.setValue(CSPreferenceConstants.RESULTS_FOLDER, resultDir);
		store.setValue(CSPreferenceConstants.KBSCANNING, kbScan);
		return true;
	}

	/**
	 * Set the stored property settings values of this tab page.
	 */
	public boolean setStoredPropertyValues(IDialogSettings pageSettings) {
		// check the CodeScanner directory selected by user
		String csDir = csDirEditText.getText();
		if (csDir.length() == 0){
			csDir = getDefaultCSDirectory();
		}

		if (!csDir.endsWith(File.separator)){
			csDir += File.separator;
			csDirEditText.setText(csDir);
		}

		if (!validateDirectory(csDir)){
			return false;
		}

		boolean htmlResults = htmlResultsButton.getSelection();
		boolean xmlResults = xmlResultsButton.getSelection();

		// check the results directory selected by user (only when generating HTML or XML results)
		String resultDir = resultsDirEditText.getText();
		if ((htmlResults || xmlResults)) {
			if (resultDir.length() > 0) {
				if (!validateDirectory(resultDir)){
					return false;
				}			
			}
			else {
				// must specify results directory when generating HTML or XML results
				reportMissingResultsDir();
				return false;
			}
		}
		
		boolean kbScan = kbScanButton.getSelection();
		boolean autoscan = autoscanButton.getSelection();

		pageSettings.put(CSPreferenceConstants.CODESCANNER_FOLDER, csDir);
		pageSettings.put(CSPreferenceConstants.HTML_RESULTS, htmlResults);
		pageSettings.put(CSPreferenceConstants.XML_RESULTS, xmlResults);
		pageSettings.put(CSPreferenceConstants.RESULTS_FOLDER, resultDir);
		pageSettings.put(CSPreferenceConstants.KBSCANNING, kbScan);
		pageSettings.put(CSPreferenceConstants.AUTOSCAN, autoscan);
		return true;
	}

	/**
	 * Initialize the stored preference settings values of this tab page.
	 */
	public static void initializePreferenceValues() {
		IPreferenceStore store = CSPlugin.getCSPrefsStore();
		store.setDefault(CSPreferenceConstants.CODESCANNER_FOLDER, getDefaultCSDirectory());
		store.setDefault(CSPreferenceConstants.HTML_RESULTS, false);
		store.setDefault(CSPreferenceConstants.XML_RESULTS, false);
		store.setDefault(CSPreferenceConstants.RESULTS_FOLDER, "");
		store.setDefault(CSPreferenceConstants.KBSCANNING, false);
		store.setDefault(CSPreferenceConstants.AUTOSCAN, false);
	}

	/**
	 * Initialize the stored property settings values of this tab page.
	 */
	public static void initializePropertyValues(IDialogSettings pageSettings) {
		pageSettings.put(CSPreferenceConstants.CODESCANNER_FOLDER, getDefaultCSDirectory());
		pageSettings.put(CSPreferenceConstants.HTML_RESULTS, false);
		pageSettings.put(CSPreferenceConstants.XML_RESULTS, false);
		pageSettings.put(CSPreferenceConstants.RESULTS_FOLDER, "");
		pageSettings.put(CSPreferenceConstants.KBSCANNING, false);
		pageSettings.put(CSPreferenceConstants.AUTOSCAN, false);
	}

	/**
	 * Make the Autoscan check box visible/invisible.
	 */
	public void setVisibleAutoscan(boolean visiable) {
		autoscanButton.setVisible(visiable);
	}

	/**
	 * Retrieve value of the Autoscan check box.
	 * @return true if the Autoscan check box is checked.
	 */
	public boolean autoscanSelected() {
		return autoscanButton.getSelection();
	}

	/**
	 * Retrieve the default CodeScanner directory.
	 * @return default CodeScanner directory if it exists.
	 */
	private static String getDefaultCSDirectory() {
		String csDirString = "";
		try {
			String pluginLocation = CSPlugin.getPluginInstallLocation();
			csDirString = pluginLocation + CS_TOOLS_DIRECTORY + File.separator;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return csDirString;
	}

	/**
	 * Handle the selection of CodeScanner directory.
	 */
	private void handleCSDirBrowse() {
	    String selectedDir = showCSDirBrowseDialog();
	    if (selectedDir != null) {
	        if (!selectedDir.equals(csDirEditText.getText())) {
	        	csDirEditText.setText(selectedDir);
	        }
	    }
	}

	/**
	 * Handle the selection of results directory.
	 */
    private void handleResultsDirBrowse() {
	    String selectedDir = showResultsDirBrowseDialog();
	    if (selectedDir != null) {
	        if (!selectedDir.equals(resultsDirEditText.getText())) {
	        	resultsDirEditText.setText(selectedDir);
	        }
	    }
	}

    /**
     * Create and open a dialog to allow the selection of CodeScanner directory.
     * @return the path of the selected directory
     */
    private String showCSDirBrowseDialog() {
        DirectoryDialog dialog = new DirectoryDialog(csDirEditText.getShell(), SWT.OPEN);
        dialog.setText(Messages.getString("CSGeneralTabPage.SelectDirTitle"));
        dialog.setMessage(Messages.getString("CSGeneralTabPage.SelectCSDirMessage"));
        BrowseDialogUtils.initializeFrom(dialog, csDirEditText);
        return dialog.open();
    }

    /**
     * Create and open a dialog to allow the selection of results directory.
     * @return the path of the selected directory
     */
	private String showResultsDirBrowseDialog() {
        DirectoryDialog dialog = new DirectoryDialog(resultsDirEditText.getShell(), SWT.OPEN);
        dialog.setText(Messages.getString("CSGeneralTabPage.SelectDirTitle"));
        dialog.setMessage(Messages.getString("CSGeneralTabPage.SelectResultsDirMessage"));
        BrowseDialogUtils.initializeFrom(dialog, resultsDirEditText);
        return dialog.open();
    }

	/**
	 * Check whether a directory already exist.
	 * @param path - path of directory to be validated
	 * @return true if directory exists
	 */
	private boolean validateDirectory(String path) {
		boolean result = false;
		IPath dirPath = new Path(path);
		if (dirPath.toFile().exists()){
			result = true;
		}
		else {
			result = MessageDialog.openQuestion(getShell(), "Directory does not exist.", "The directory " + dirPath + " does not exist.\n\nDo you want to continue?");
		}
		return result;
	}

	/**
	 * Create an error dialog for missing results directory.
	 */
	private void reportMissingResultsDir() {
		MessageDialog.openError(getShell(), 
								Messages.getString("CSGeneralTabPage.MissingResultsDirTitle"),
								Messages.getString("CSGeneralTabPage.MissingResultsDirMessage"));		
	}
}
