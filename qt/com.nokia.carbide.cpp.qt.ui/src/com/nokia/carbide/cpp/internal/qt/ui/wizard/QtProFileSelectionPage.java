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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
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

import com.nokia.carbide.cpp.internal.project.ui.sharedui.BuilderSelectionComposite;
import com.nokia.carbide.cpp.internal.qt.ui.QtUIHelpIds;
import com.nokia.cpp.internal.api.utils.ui.BrowseDialogUtils;


public class QtProFileSelectionPage extends WizardPage implements Listener {
	
    private Combo proFileCombo;
    private Button browseButton;
    private BuilderSelectionComposite builderComposite;

    private static final String RECENT_PRO_FILES_STORE = "QtProFileSelectionPage.RECENT_PRO_FILES_STORE"; //$NON-NLS-1$

    private String proFilePath;
    private List<String> recentProFiles = new ArrayList<String>();
    
    public QtProFileSelectionPage() {
		super(Messages.QtProFileSelectionPage_title);
		setTitle(Messages.QtProFileSelectionPage_title);
		setDescription(Messages.QtProFileSelectionPage_description);
	}

	public void createControl(Composite parent) {
		setPageComplete(false);
		setErrorMessage(null);
		setMessage(Messages.QtProFileSelectionPage_selectAProFileInfo);

		initializeDialogUnits(parent);
        
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(2, false));
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));
        
        composite.setData(".uid", "QtProFileSelectionPage"); //$NON-NLS-1$ //$NON-NLS-2$
        composite.setData("QtProFileSelectionPage", this); //$NON-NLS-1$
        
        createControls(composite);
        setControl(composite);

		restoreDialogSettings();

		setPageComplete(validatePage());
	}
	
	private void createControls(Composite parent) {
		Font font = parent.getFont();
		
		Label proLabel = new Label(parent, SWT.NONE);
		proLabel.setFont(font);
		proLabel.setText(Messages.QtProFileSelectionPage_proFileLabel);
		proLabel.setToolTipText(Messages.QtProFileSelectionPage_proFileToolTip);
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gd.horizontalSpan = 2;
		proLabel.setLayoutData(gd);
			
		proFileCombo = new Combo(parent, SWT.SINGLE | SWT.BORDER);
    	proFileCombo.setFont(font);
    	proFileCombo.setToolTipText(Messages.QtProFileSelectionPage_proFileToolTip);
    	proFileCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    	proFileCombo.addListener(SWT.Modify, this);
    	proFileCombo.setData(".uid", "proFileCombo"); //$NON-NLS-1$ //$NON-NLS-2$
    	
    	browseButton = new Button(parent, SWT.PUSH);
    	browseButton.setFont(font);
    	browseButton.setText(Messages.QtProFileSelectionPage_browseButtonLabel);
    	browseButton.setToolTipText(Messages.QtProFileSelectionPage_browseButtonTooltip);
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
		} else if (event.widget == proFileCombo) {
			handleProFileComboChange();
		}
	}

	private void handleBrowse() {
	    String selectedFile = showBrowseDialog();
	    if (selectedFile != null) {
	        if (!selectedFile.equals(proFileCombo.getText())) {
	        	proFileCombo.setText(selectedFile);
	        }
	    }
	}

    private String showBrowseDialog() {
        FileDialog fileDialog = new FileDialog(proFileCombo.getShell(), SWT.OPEN);
        fileDialog.setFilterExtensions(new String[] {"*.pro", "*.*"}); //$NON-NLS-1$ //$NON-NLS-2$
        fileDialog.setText(Messages.QtProFileSelectionPage_browseDialogTitle);

        BrowseDialogUtils.initializeFrom(fileDialog, proFileCombo.getText());

        return fileDialog.open();
    }

    private void handleProFileComboChange() {
		setPageComplete(validatePage());
		if (isPageComplete()) {
			// save the history right away in case user is forced to cancel
			saveRecentFiles(getDialogSettings());
		}
		proFileCombo.setFocus();
    }

    private boolean validatePage() {
		setErrorMessage(null);
		setMessage(Messages.QtProFileSelectionPage_selectAProFileInfo);
		proFilePath = proFileCombo.getText().trim();
		if (proFilePath == null || proFilePath == "") { //$NON-NLS-1$
			return false;
		}
		
		// don't allow spaces in the path
		if (proFilePath.indexOf(" ") > 0) { //$NON-NLS-1$
			setErrorMessage(Messages.QtProFileSelectionPage_noSpacesInPathError);
			return false;
		}

		File proFile = new File(proFilePath);
		if (!proFile.exists()) {
			setErrorMessage(Messages.QtProFileSelectionPage_invalidProError);
			return false;
		}

		if (!proFile.isFile() || (!proFile.getName().toLowerCase().endsWith(".pro"))) { //$NON-NLS-1$
			setErrorMessage(Messages.QtProFileSelectionPage_notProError);
			return false;
		}
		
		// don't allow .pro file's at the workspace root as Eclipse will not allow it
		IPath proFileDirectory = new Path(proFile.getPath()).removeLastSegments(1).addTrailingSeparator();
		IPath workspaceRootDirectory = ResourcesPlugin.getWorkspace().getRoot().getLocation().addTrailingSeparator();
		if (proFileDirectory.toOSString().compareToIgnoreCase(workspaceRootDirectory.toOSString()) == 0) {
			setErrorMessage(Messages.QtProFileSelectionPage_badLocationError);
			return false;
		}
		
		String projectName = new Path(proFilePath).removeFileExtension().lastSegment();
		
        IStatus nameStatus = ResourcesPlugin.getWorkspace().validateName(projectName, IResource.PROJECT);
        if (!nameStatus.isOK()) {
            setErrorMessage(nameStatus.getMessage());
            return false;
        }

        // builds don't work if the project name if greater than 32 characters for
        // some reason - see bug #2231.
        if (projectName.length() > 32) {
			setErrorMessage(Messages.QtProFileSelectionPage_projectNameTooLong);
			return false;
        }
        
        // make sure a project with that name doesn't already exist in the workspace (ignoring case)
        for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
        	if (project.getName().compareToIgnoreCase(projectName) == 0) {
    			setErrorMessage(Messages.QtProFileSelectionPage_projectExistsError + " " + projectName);
    			return false;
        	}
        }

		// see if there is already a .project file at this location.  note that above check only
		// checks for projects in the workspace.  this checks to see if there is another project
		// at the location where the .project file would be created.  this will see if a project
		// that the workspace knows about (open or close) exists in that location.  note that there
		// could be a .project file at that location in the file system.  we'll allow that as the
		// user will be prompted after they click finish if they want to overwrite that project.  but
		// if there is a project in the workbench for that .project file then we can't let them overwrite
		// it, so we post the error here.
		IContainer container = ResourcesPlugin.getWorkspace().getRoot().getContainerForLocation(proFileDirectory);
		if (container != null && container.getType() == IResource.PROJECT) {
			setErrorMessage(String.format(Messages.QtProFileSelectionPage_projectFileExistsError, new Object[]{container.getName(), proFileDirectory.toOSString()}));
			return false;
		}

		// the max path length is 256 characters.  we will create {root}\.settings\org.eclipse.cdt.core.prefs, so we need to
		// make sure that the project root is not more than 221 characters.  we'll add a little extra wiggle room though so
		// call it 215.
		if  (proFileDirectory.toOSString().length() > 215) {
			setErrorMessage(Messages.QtProFileSelectionPage_directoryTooLong);
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

    public String getProFilePath() {
    	return proFilePath;
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
        	// remove the pro file from the stack if it exists
        	recentProFiles.remove(proFilePath);
        	
        	// add the selected pro file to the top of the stack
        	recentProFiles.add(0, proFilePath);
        	
        	// keep the stack at 20 strings
        	if (recentProFiles.size() > 20) {
        		recentProFiles.remove(20);
        	}
        	
            settings.put(RECENT_PRO_FILES_STORE, recentProFiles.toArray(new String[recentProFiles.size()]));
        }
    }

    private void restoreDialogSettings() {
        IDialogSettings settings = getDialogSettings();
        if (settings != null) {
        	String[] files = settings.getArray(RECENT_PRO_FILES_STORE);
        	if (files != null && files.length > 0) {
        		recentProFiles.addAll(Arrays.asList(files));
            	proFileCombo.setItems(files);
            	proFileCombo.select(0);
            }

        	if (builderComposite != null) {
            	builderComposite.restoreDialogSettings(settings);
        	}
        }
    }

	@Override
	public void performHelp() {
        PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl().getShell(), QtUIHelpIds.QT_PRO_FILE_SELECTION_PAGE);
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
