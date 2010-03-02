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
package com.nokia.carbide.cpp.internal.project.ui.importWizards;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.PlatformUI;

import com.nokia.carbide.cpp.internal.project.ui.ProjectUIHelpIds;
import com.nokia.carbide.cpp.internal.project.ui.sharedui.BuilderSelectionComposite;

public class BldInfSelectionPage extends WizardPage implements Listener {
	
	private BldInfImportWizard theWizard;
    private Combo bldInfCombo;
    private Button browseButton;
    private BuilderSelectionComposite builderComposite;
    
    private static final String RECENT_BLD_INF_FILES_STORE = "BldInfSelectionPage.RECENT_BLD_INF_FILES_STORE"; //$NON-NLS-1$

    private String infFilePath;
    private List<String> recentInfFiles = new ArrayList<String>();
    
    public BldInfSelectionPage(BldInfImportWizard wizard) {
		super(Messages.BldInfSelectionPage_title);
		theWizard = wizard;
		setTitle(Messages.BldInfSelectionPage_title);
		setDescription(Messages.BldInfSelectionPage_description);
	}

	public void createControl(Composite parent) {
		setPageComplete(false);
		setErrorMessage(null);
		setMessage(Messages.BldInfSelectionPage_selectABLDINFToImport); 
		setMessage(null);

		initializeDialogUnits(parent);
        
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(2, false));
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));
        
        composite.setData(".uid", "BldInfSelectionPage"); //$NON-NLS-1$ //$NON-NLS-2$
        composite.setData("BldInfSelectionPage", this); //$NON-NLS-1$
        
        createControls(composite);
        setControl(composite);

		restoreDialogSettings();

		setPageComplete(validatePage());
	}
	
	private void createControls(Composite parent) {
		Font font = parent.getFont();
		
		Label infLabel = new Label(parent, SWT.NONE);
		infLabel.setFont(font);
		infLabel.setText(Messages.BldInfSelectionPage_infFileLabel);
		infLabel.setToolTipText(Messages.BldInfSelectionPage_infFileToolTip);
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gd.horizontalSpan = 2;
		infLabel.setLayoutData(gd);
			
		bldInfCombo = new Combo(parent, SWT.SINGLE | SWT.BORDER);
    	bldInfCombo.setFont(font);
    	bldInfCombo.setToolTipText(Messages.BldInfSelectionPage_infFileToolTip);
    	bldInfCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    	bldInfCombo.addListener(SWT.Modify, this);
    	bldInfCombo.setData(".uid", "bldInfCombo"); //$NON-NLS-1$ //$NON-NLS-2$
    	
    	browseButton = new Button(parent, SWT.PUSH);
    	browseButton.setFont(font);
    	browseButton.setText(Messages.BldInfSelectionPage_browseButtonLabel);
    	browseButton.setToolTipText(Messages.BldInfSelectionPage_browseButtonTooltip);
    	browseButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
    	browseButton.addListener(SWT.Selection, this);
    	browseButton.setData(".uid", "browseButton"); //$NON-NLS-1$ //$NON-NLS-2$
    	
		setButtonLayoutData(browseButton);
		
	    builderComposite = new BuilderSelectionComposite(parent);
	    builderComposite.createControls();
	    builderComposite.getBuilderCombo().addSelectionListener(new SelectionListener() { 
	    	public void widgetDefaultSelected(SelectionEvent e) { 
	    		widgetSelected(e); 
	    	}
	    	public void widgetSelected(SelectionEvent e) { 
	    		setPageComplete(validatePage()); 
	    	}
	    }); 
    }

	public void handleEvent(Event event) {
		if (event.widget == browseButton) {
			handleBrowse();
		} else if (event.widget == bldInfCombo) {
			handlebldInfComboChange();
		}
	}

	private void handleBrowse() {
	    String selectedFile = showBrowseDialog();
	    if (selectedFile != null) {
	        if (!selectedFile.equals(bldInfCombo.getText())) {
	        	bldInfCombo.setText(selectedFile);
	        }
	    }
	}

    private String showBrowseDialog() {
        FileDialog fileDialog = new FileDialog(bldInfCombo.getShell(), SWT.OPEN);
        fileDialog.setFilterExtensions(new String[] {"bld.inf", "*.*"}); //$NON-NLS-1$ //$NON-NLS-2$
        fileDialog.setFileName("bld.inf"); //$NON-NLS-1$
        fileDialog.setText(Messages.BldInfSelectionPage_browseDialogTitle);

        String currentSourceString = bldInfCombo.getText();
        int lastSeparatorIndex = currentSourceString.lastIndexOf(File.separator);
        if (lastSeparatorIndex != -1) {
        	fileDialog.setFilterPath(currentSourceString.substring(0, lastSeparatorIndex));
        }

        return fileDialog.open();
    }

    private void handlebldInfComboChange() {
		// the bld.inf file changed so set the wizard as incomplete so the finish button
		// gets disabled.  fixes bug #5259.
		theWizard.setWizardIncomplete();

		setPageComplete(validatePage());
		if (isPageComplete()) {
			// save the history right away in case user is forced to cancel
			saveRecentFiles(getDialogSettings());
		}
		bldInfCombo.setFocus();
    }

    private boolean validatePage() {
		setErrorMessage(null);
		setMessage(Messages.BldInfSelectionPage_selectABLDINFToImport); 
		
		infFilePath = bldInfCombo.getText().trim();
		if (infFilePath == null || infFilePath == "") { //$NON-NLS-1$
			return false;
		}
		
		// don't allow spaces in the path
		if (infFilePath.indexOf(" ") > 0) { //$NON-NLS-1$
			setErrorMessage(Messages.BldInfSelectionPage_noSpacesInPathError);
			return false;
		}

		File infFile = new File(infFilePath);
		if (!infFile.exists()) {
			setErrorMessage(Messages.BldInfSelectionPage_invalidInfError);
			return false;
		}

		if (!infFile.isFile() || (infFile.getName().compareToIgnoreCase("bld.inf") != 0)) { //$NON-NLS-1$
			setErrorMessage(Messages.BldInfSelectionPage_notInfError);
			return false;
		}
		
		// don't allow bld.inf file's at the workspace root as Eclipse will not allow it
		IPath infFileDirectory = new Path(infFile.getPath()).removeLastSegments(1).addTrailingSeparator();
		IPath workspaceRootDirectory = ResourcesPlugin.getWorkspace().getRoot().getLocation().addTrailingSeparator();
		if (infFileDirectory.toOSString().compareToIgnoreCase(workspaceRootDirectory.toOSString()) == 0) {
			setErrorMessage(Messages.BldInfSelectionPage_badLocationError);
			return false;
		}
		
		if (builderComposite != null) {
			IStatus status = builderComposite.validatePage(); 
        	if (status != null){ 
        		// Get the level from the status. 
        		int level = getMessageLevelFromIStatus(status); 
        		setMessage(status.getMessage(), level); 
        		if (level == ERROR){ 
        			return false; 
        		}
        	}
		}
		
		return true;
    }

    public String getInfFilePath() {
    	return infFilePath;
    }
    
    public boolean useSBSv2Builder() {
    	if (builderComposite != null) {
        	return builderComposite.useSBSv2Builder();
    	}
    	return false;
    }

    public void saveDialogSettings() {
        IDialogSettings settings = getDialogSettings();
        if (settings != null) {
        	saveRecentFiles(settings);
        	
        	if (builderComposite != null) {
            	builderComposite.saveDialogSettings(settings);
        	}
        }
    }
    
    private void saveRecentFiles(IDialogSettings settings) {
        if (settings != null) {
        	// remove the bld.inf file from the stack if it exists
        	recentInfFiles.remove(infFilePath);
        	
        	// add the selected bld.inf file to the top of the stack
        	recentInfFiles.add(0, infFilePath);
        	
        	// keep the stack at 20 strings
        	if (recentInfFiles.size() > 20) {
        		recentInfFiles.remove(20);
        	}
        	
            settings.put(RECENT_BLD_INF_FILES_STORE, recentInfFiles.toArray(new String[recentInfFiles.size()]));
        }
    }

    private void restoreDialogSettings() {
        IDialogSettings settings = getDialogSettings();
        if (settings != null) {
        	String[] files = settings.getArray(RECENT_BLD_INF_FILES_STORE);
        	if (files != null && files.length > 0) {
        		recentInfFiles.addAll(Arrays.asList(files));
            	bldInfCombo.setItems(files);
            	bldInfCombo.select(0);
            }
        	
        	if (builderComposite != null) {
            	builderComposite.restoreDialogSettings(settings);
        	}
        }
    }

	@Override
	public void performHelp() {
        PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl().getShell(), ProjectUIHelpIds.BLDINF_SELECTION_PAGE);
	}
	
    private int getMessageLevelFromIStatus(IStatus status){ 
    	if (status.getSeverity() == Status.ERROR) 
    		return ERROR;
    	else if (status.getSeverity() == Status.WARNING) 
    		return WARNING;
    	else if (status.getSeverity() == Status.INFO) 
    		return INFORMATION;
    	
    	return NONE;
    }
}
