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
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;

import com.nokia.carbide.cpp.internal.codescanner.CSPlugin;
import com.nokia.carbide.cpp.internal.codescanner.Messages;
import com.nokia.cpp.internal.api.utils.ui.BrowseDialogUtils;

/**
 * A class to create and control the CodeScanner preference page.
 */
public class CSPreferencePage extends PreferencePage 
	implements IWorkbenchPreferencePage{

	// private members for various controls of this property page
	private Composite settingsGroup = null;
	private Link projectSettingsLink = null;
	private Button importButton = null;
	private Button exportButton = null;
	private TabFolder optionsTabFolder = null;
	private CSGeneralTabPage generalTabPage = null;
	private CSFileFiltersTabPage fileFiltersTabPage = null;
	private CSRulesTabPage rulesTabPage = null;
	private TabItem generalTab = null;
	private TabItem fileFiltersTab = null;
	private TabItem rulesTab = null;
	@SuppressWarnings("unchecked")
	private Map pageData = null;

	private static String dialogFilterPath = null;

	private final int generalTabIndex = 0;
	private final int fileFiltersTabIndex = 1;
	private final int rulesTabIndex = 2;

	/**
	 * Create an instance of CSPreferencePage.
	 */
	public CSPreferencePage() {
		super();
	}
	
	/**
	 * Create contents of this preference page.
	 * @param parent - the parent composite
	 */
	protected Control createContents(Composite parent) {
		final Composite content = new Composite(parent, SWT.NONE);
		content.setLayout(new GridLayout());
		content.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		settingsGroup = new Composite(content, SWT.NONE);
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.widthHint = 460;
		settingsGroup.setLayoutData(gridData);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		settingsGroup.setLayout(gridLayout);

		// place holder label(s) to fill up unused columns in the layout
		Label label = new Label(settingsGroup, SWT.NONE);
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.widthHint = 460;
		label.setLayoutData(gridData);

		// link to CodeScanner project property page
		if (offerLink()) {
			projectSettingsLink = new Link(settingsGroup, SWT.NONE);
			projectSettingsLink.setText(Messages.getString("CSPreferencPage.ProjectSettingsLabel"));
			projectSettingsLink.addSelectionListener(new SelectionAdapter() {
				@SuppressWarnings("unchecked")
				public void widgetSelected(SelectionEvent arg0) {
					Map data= new HashMap();
					data.put(CSPreferenceConstants.NO_LINK, Boolean.TRUE);
					IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
					CSProjectSelectionDialog dialog = new CSProjectSelectionDialog(content.getShell(), projects);
					if (dialog.open() == Window.OK) {
						IProject project = (IProject)dialog.getFirstResult();
						PreferencesUtil.createPropertyDialogOn(content.getShell(), project, CSPreferenceConstants.PROPERTY_PAGE_ID, null, data).open();					
					}
				}
			});
		}
		
		Label horizontalLine= new Label(content, SWT.SEPARATOR | SWT.HORIZONTAL);
		horizontalLine.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));

		// tab folder for various CodeScanner options
		optionsTabFolder = new TabFolder(content, SWT.TOP);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.heightHint = 360;
		gridData.widthHint = 460;
		optionsTabFolder.setLayoutData(gridData);
		optionsTabFolder.setLayout(new GridLayout());

		// general CodeScanner options
		generalTabPage = new CSGeneralTabPage(optionsTabFolder);
		generalTab = new TabItem(optionsTabFolder, SWT.NONE, generalTabIndex);
		generalTab.setText(Messages.getString("CSPreferencPage.GeneralTabLabel"));
		generalTab.setToolTipText(Messages.getString("CSPreferencPage.GeneralTabMessage"));
		generalTab.setControl(generalTabPage);

		// CodeScanner file filters
		fileFiltersTabPage = new CSFileFiltersTabPage(optionsTabFolder);
		fileFiltersTab = new TabItem(optionsTabFolder, SWT.NONE, fileFiltersTabIndex);
		fileFiltersTab.setText(Messages.getString("CSPreferencPage.FileFiltersTabLabel"));
		fileFiltersTab.setToolTipText(Messages.getString("CSPreferencPage.FileFiltersTabMessage"));
		fileFiltersTab.setControl(fileFiltersTabPage);

		// CodeScanner rules
		rulesTabPage = new CSRulesTabPage(optionsTabFolder);
		rulesTab = new TabItem(optionsTabFolder, SWT.NONE, rulesTabIndex);
		rulesTab.setText(Messages.getString("CSPreferencPage.RulesTabLabel"));
		rulesTab.setToolTipText(Messages.getString("CSPreferencPage.RulesTabMessage"));
		rulesTab.setControl(rulesTabPage);

		getPageStoredValues();
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, CSUIHelpIds.CODESCANNER_PREFERENCE_PAGE);
		generalTabPage.setFocus();
		return content;
	}

	/**
	 * Add buttons to the property page.
	 * @param parent - the parent composite
	 */
	protected void contributeButtons(Composite parent) {
		// import configuration settings
		importButton = new Button(parent, SWT.NONE);
		importButton.setText(Messages.getString("CSPreferencPage.ImportSettingsLabel"));
		importButton.setToolTipText(Messages.getString("CSPreferencPage.ImportSettingsMessage"));
		importButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				handleImport();
			}
		});

		// export configuration settings
		exportButton = new Button(parent, SWT.NONE);
		exportButton.setText(Messages.getString("CSPreferencPage.ExportSettingsLabel"));
		exportButton.setToolTipText(Messages.getString("CSPreferencPage.ExportSettingsMessage"));
		exportButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				handleExport();
			}
		});

		// add two columns to the parent's layout
		((GridLayout) parent.getLayout()).numColumns += 2;
	}

	/**
	 * Initialize this preference page for the given workbench.
	 * @param workbench - the workbench
	 */
	public void init(IWorkbench workbench) {
		if (dialogFilterPath == null) {
			dialogFilterPath = ResourcesPlugin.getWorkspace().getRoot().getLocation().toOSString();
		}
	}

	/**
	 * Apply the data to the receiver.
	 * @param data - incoming data
	 */
	@SuppressWarnings("unchecked")
	public void applyData(Object data) {
		if (data instanceof Map) {
			pageData = (Map)data;
		}
		if (projectSettingsLink != null) {
			if (!offerLink()) {
				projectSettingsLink.setVisible(false);
			}
		}
 	}
	
	/**
	 * Things to do when user hit the "OK" button.
	 */
	public boolean performOk() {
		boolean result = setPageStoredValues();
		if (result) {
			return super.performOk();
		}
		else {
			return result;
		}
	}
	
	/**
	 * Things to do when user hit the "Default" button.
	 */
	protected void performDefaults() {
		int selection = optionsTabFolder.getSelectionIndex();
		switch (selection) {
			case generalTabIndex:
				generalTabPage.setDefaults();
				break;
			case fileFiltersTabIndex:
				fileFiltersTabPage.setDefaults();
				break;
			case rulesTabIndex:
				rulesTabPage.setDefaults();
				break;
		};
		super.performDefaults();
	}

	/**
	 * Retrieve the stored values for this preference page.
	 */
	private void getPageStoredValues() {
		generalTabPage.getStoredPreferenceValues();
		fileFiltersTabPage.getStoredPreferenceValues();
		rulesTabPage.getStoredPreferenceValues();		
	}

	/**
	 * Store the values for this preference page.
	 */
	private boolean setPageStoredValues() {
		if (!generalTabPage.setStoredPreferenceValues()) {
			return false;
		}
		if (!fileFiltersTabPage.setStoredPreferenceValues()) {
			return false;
		}
		if (!rulesTabPage.setStoredPreferenceValues()) {
			return false;
		}
		return true;
	}

    /**
     * Things to do when user hit the "Import Settings" button.
     */
    private void handleImport() {
    	String filePath = selectConfigFile(importButton, false);
    	if (filePath != null && filePath.length() > 0) {
    		File configFile = new File(filePath);
    		if (configFile.exists()) {
        		CSPlugin.getConfigManager().loadGlobalConfigFile(filePath);
        		// import CodeScanner file filters and rules only
        		fileFiltersTabPage.getStoredPreferenceValues();
        		rulesTabPage.getStoredPreferenceValues();
    		}
    		else {
    			importConfigFileError(filePath);
    		}
    	}
    }

    /**
     * Things to do when user hit the "Export Settings" button.
     */
	private void handleExport() {
    	String filePath = selectConfigFile(exportButton, true);
    	if (filePath != null && filePath.length() > 0) {
    		// export CodeScanner file filters and rules only
    		fileFiltersTabPage.setStoredPreferenceValues();
    		rulesTabPage.setStoredPreferenceValues();
    		CSPlugin.getConfigManager().createGlobalConfigFile(null, filePath);
    	}
	}

	/**
	 * Create and open a dialog to allow selection of a configuration settings file
	 * for import/export.
	 * @param button - the button widget associated with this dialog
	 * @param export - indicate whether the dialog is used to export a file
	 * @return the path of the selected file
	 */
	private String selectConfigFile(Button button, boolean export) {
        FileDialog dialog;
        if (export) {
        	dialog = new FileDialog(button.getShell(), SWT.SAVE);
            dialog.setText(Messages.getString("CSPreferencPage.SelectExportSettingsMessage"));
            dialog.setFileName(Messages.getString("CSPreferencPage.ExportDefaultFilename"));
        }
        else {
        	dialog = new FileDialog(button.getShell(), SWT.OPEN);
            dialog.setText(Messages.getString("CSPreferencPage.SelectImportSettingsMessage"));
        }
        String[] extensions = new String[] {"*.xml"};
        dialog.setFilterExtensions(extensions);
        BrowseDialogUtils.initializeFrom(dialog, dialogFilterPath != null ? dialogFilterPath + File.separator : null);
		String configFilePath = dialog.open();
		dialogFilterPath = dialog.getFilterPath();
        return configFilePath;
	}

	/**
	 * Generate error if an import config file cannot be found.
	 * @param fileName - impoert Config file name
	 */
	private void importConfigFileError(String fileName) {
		IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (workbenchWindow != null) {
			MessageDialog.openError(workbenchWindow.getShell(), 
				Messages.getString("CSPreferencPage.ImportConfigFileErrorTitle"), 
				Messages.getString("CSPreferencPage.ImportConfigFileErrorMessage") + fileName);
		}
	}

	/**
	 * Determine whether to create a link to CodeScanner project property page.
	 * @return true if a link to CodeScanner project property page is to be created
	 */
	private boolean offerLink() {
		return pageData == null || !Boolean.TRUE.equals(pageData.get(CSPreferenceConstants.NO_LINK));
	}

}
