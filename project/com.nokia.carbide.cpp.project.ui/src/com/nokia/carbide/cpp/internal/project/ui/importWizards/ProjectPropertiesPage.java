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

import com.nokia.carbide.cdt.builder.EpocEngineHelper;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIHelpIds;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ProjectPropertiesPage extends WizardPage implements Listener {
	
    private Text projectName;
    private Text rootDirectory;
    private Button browseButton;
    
    String projectNameText = ""; //$NON-NLS-1$
    IPath rootDirectoryPath = null;
    IPath rootPathContainingAllProjectFiles = null;
    IPath rootPathContainingAllProjectAndSourceFiles = null;
    
    private BldInfImportWizard theWizard;

    // for caching
	private String parsedBldInfFile = null;
	private List<ISymbianBuildContext> parsedWithConfigs = null;
	private List<String> selectedMakMakeRefs = null;

	
	public ProjectPropertiesPage(BldInfImportWizard wizard) {
		super(Messages.ProjectPropertiesPage_title);
		setTitle(Messages.ProjectPropertiesPage_title);
		setDescription(Messages.ProjectPropertiesPage_description);
		theWizard = wizard;
	}

	public void createControl(Composite parent) {
		setPageComplete(false);
		setErrorMessage(null);
		setMessage(null);

		initializeDialogUnits(parent);
        
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(3, false));
        
        createControls(composite);
        setControl(composite);        

        composite.setData(".uid", "ProjectPropertiesPage"); //$NON-NLS-1$ //$NON-NLS-2$
		getControl().setData("ProjectPropertiesPage", this); //$NON-NLS-1$
	}
	
	private void createControls(Composite parent) {
		Font font = parent.getFont();
		
		Label nameLabel = new Label(parent, SWT.NONE);
		nameLabel.setFont(font);
		nameLabel.setText(Messages.ProjectPropertiesPage_projectNameLabel);
		nameLabel.setToolTipText(Messages.ProjectPropertiesPage_projectNameTooltip);
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gd.horizontalSpan = 1;
		nameLabel.setLayoutData(gd);

		projectName = new Text(parent, SWT.SINGLE | SWT.BORDER);
    	projectName.setFont(font);
    	projectName.setToolTipText(Messages.ProjectPropertiesPage_projectNameTooltip);
    	gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		gd.horizontalSpan = 2;
    	projectName.setLayoutData(gd);
		projectName.addListener(SWT.Modify, this);
		projectName.setData(".uid", "projectName"); //$NON-NLS-1$ //$NON-NLS-2$
    	
		Label dirLabel = new Label(parent, SWT.NONE);
		dirLabel.setFont(font);
		dirLabel.setText(Messages.ProjectPropertiesPage_rootDirectoryLabel);
		dirLabel.setToolTipText(Messages.ProjectPropertiesPage_rootDirectoryTooltip);
		gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gd.horizontalSpan = 1;
		dirLabel.setLayoutData(gd);

		rootDirectory = new Text(parent, SWT.SINGLE | SWT.BORDER);
		rootDirectory.setFont(font);
		rootDirectory.setToolTipText(Messages.ProjectPropertiesPage_rootDirectoryTooltip);
		gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		gd.horizontalSpan = 1;
    	rootDirectory.setLayoutData(gd);
    	rootDirectory.addListener(SWT.Modify, this);
    	rootDirectory.setData(".uid", "rootDirectory"); //$NON-NLS-1$ //$NON-NLS-2$

    	browseButton = new Button(parent, SWT.PUSH);
    	browseButton.setFont(font);
    	browseButton.setText(Messages.ProjectPropertiesPage_browseButtonLabel);
    	browseButton.setToolTipText(Messages.ProjectPropertiesPage_browseButtonTooltip);
    	browseButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
    	browseButton.addListener(SWT.Selection, this);
    	browseButton.setData(".uid", "browseButton"); //$NON-NLS-1$ //$NON-NLS-2$
    	
		Label helpText = new Label(parent, SWT.NONE);
		helpText.setFont(font);
		helpText.setText(Messages.ProjectPropertiesPage_helpText);
		gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gd.horizontalSpan = 3;
		helpText.setLayoutData(gd);

		setButtonLayoutData(browseButton);
	}

	public void handleEvent(Event event) {
		if (event.widget == browseButton) {
			handleBrowse();
		} else if (event.widget == rootDirectory && rootDirectory.isVisible()) {
			setPageComplete(validatePage());
		} else if (event.widget == projectName && projectName.isVisible()) {
			setPageComplete(validatePage());
		}
	}

	private void handleBrowse() {
	    String selectedDir = showBrowseDialog();
	    if (selectedDir != null) {
	        if (!selectedDir.equals(rootDirectory.getText())) {
	        	rootDirectory.setText(selectedDir);
	        }
	    }
	}

    private String showBrowseDialog() {
        DirectoryDialog dialog = new DirectoryDialog(rootDirectory.getShell(), SWT.OPEN);
        dialog.setText(Messages.ProjectPropertiesPage_browseDialogTitle);

        dialog.setFilterPath(rootDirectory.getText());

        return dialog.open();
    }

    private boolean validatePage() {
    	setMessage(null);
		setErrorMessage(null);

		projectNameText = projectName.getText().trim();

		if (projectNameText == null || projectNameText == "") { //$NON-NLS-1$
			setErrorMessage(Messages.ProjectPropertiesPage_noProjectSpecifiedError);
			return false;
		}
		
        IStatus nameStatus = ResourcesPlugin.getWorkspace().validateName(projectNameText, IResource.PROJECT);
        if (!nameStatus.isOK()) {
            setErrorMessage(nameStatus.getMessage());
            return false;
        }

        // builds don't work if the project name if greater than 32 characters for
        // some reason - see bug #2231.
        if (projectNameText.length() > 32) {
			setErrorMessage(Messages.ProjectPropertiesPage_projectNameTooLong);
			return false;
        }
        
        // make sure a project with that name doesn't already exist in the workspace (ignoring case)
        for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
        	if (project.getName().compareToIgnoreCase(projectNameText) == 0) {
    			setErrorMessage(Messages.ProjectPropertiesPage_projectExistsError);
    			return false;
        	}
        }
		
		// check the root directory
		if (rootDirectory.getText() == null || rootDirectory.getText() == "") { //$NON-NLS-1$
			setErrorMessage(Messages.ProjectPropertiesPage_noDirectorySpecifiedError);
			return false;
		}
		rootDirectoryPath = new Path(rootDirectory.getText());
		
		if (!rootDirectoryPath.toFile().exists()) {
			setErrorMessage(Messages.ProjectPropertiesPage_directoryDoesNotExitError);
			return false;
		}

		// make sure the root directory they chose at least contains all project files.  ignore case as well.
		if (rootPathContainingAllProjectFiles != null && !rootPathContainingAllProjectFiles.toOSString().toLowerCase().startsWith(rootDirectoryPath.removeTrailingSeparator().toOSString().toLowerCase())) {
			setErrorMessage(Messages.ProjectPropertiesPage_directoryDoesNotContainRequiredFiles);
			return false;
		}
		
		// see if there is already a .project file at this location.  note that above check only
		// checks for projects in the workspace.  this checks to see if there is another project
		// at the location where the .project file would be created.  this will see if a project
		// that the workspace knows about (open or close) exists in that location.  note that there
		// could be a .project file at that location in the file system.  we'll allow that as the
		// user will be prompted after they click finish if they want to overwrite that project.  but
		// if there is a project in the workbench for that .project file then we can't let them overwrite
		// it, so we post the error here.
		IContainer container = ResourcesPlugin.getWorkspace().getRoot().getContainerForLocation(rootDirectoryPath);
		if (container != null && container.getType() == IResource.PROJECT) {
			setErrorMessage(String.format(Messages.ProjectPropertiesPage_projectFileExistsError, new Object[]{container.getName(), rootDirectoryPath.toOSString()}));
			return false;
		}

		// the max path length is 256 characters.  we will create {root}\.settings\org.eclipse.cdt.core.prefs, so we need to
		// make sure that the project root is not more than 221 characters.  we'll add a little extra wiggle room though so
		// call it 215.
		if  (rootDirectoryPath.toOSString().length() > 215) {
			setErrorMessage(Messages.ProjectPropertiesPage_directoryTooLong);
			return false;
		}
		
		// warn if the project root doesn't contain sources (bug 5527)
		if (rootPathContainingAllProjectAndSourceFiles != null && !rootPathContainingAllProjectAndSourceFiles.toOSString().toLowerCase().startsWith(rootDirectoryPath.removeTrailingSeparator().toOSString().toLowerCase())) {
			setMessage(Messages.ProjectPropertiesPage_directoryDoesNotContainSourceFiles, IStatus.WARNING);
			
			// still legal
			return true;
		}
		
		return true;
    }
    
    public String getProjectName() {
    	return projectNameText;
    }
    
    public IPath getRootDirectory() {
    	return rootDirectoryPath;
    }

	@Override
	public void setVisible(boolean visible) {
		// this gets called just before the page goes in or out of view.  if it's
		// going into view then get the project name and root directory.  when
		// we do save the bld.inf file and list of build configs we parsed with so we
		// know if we need to re-parse again if either of these changes, e.g. the user
		// hits back and selects a different bld.inf or set of configs
		if (visible) {
			if (parsedBldInfFile == null || parsedWithConfigs == null ||
					!parsedBldInfFile.equals(theWizard.getBldInfFile()) ||
					!parsedWithConfigs.equals(theWizard.getSelectedConfigs()) ||
					!selectedMakMakeRefs.equals(theWizard.getSelectedMakMakeReferences())) {
				parsedBldInfFile = theWizard.getBldInfFile();
				parsedWithConfigs = theWizard.getSelectedConfigs();
				selectedMakMakeRefs = theWizard.getSelectedMakMakeReferences();

				try {
					getContainer().run(true, true, new IRunnableWithProgress() {
						public void run(IProgressMonitor monitor) {
							List<IPath> projectRoots = EpocEngineHelper.getProjectRoots(new Path(parsedBldInfFile), parsedWithConfigs, monitor);
							rootDirectoryPath = projectRoots.get(0);
							rootPathContainingAllProjectFiles = projectRoots.get(1);
							rootPathContainingAllProjectAndSourceFiles = projectRoots.get(2);
							
							// do not use the group directory by default if possible
							if (rootDirectoryPath.segmentCount() > 1 && rootDirectoryPath.lastSegment().compareToIgnoreCase("group") == 0) { //$NON-NLS-1$
								rootDirectoryPath = rootDirectoryPath.removeLastSegments(1);
							}
							
							// do this in the ui thread
							getControl().getDisplay().asyncExec(new Runnable(){
								public void run() {
									rootDirectory.setText(rootDirectoryPath.toOSString());
									projectName.setText(getProposedProjectName());
									setPageComplete(validatePage());
								}
							});
						}
					});
				} catch (InvocationTargetException e) {
				} catch (InterruptedException e) {
					// Nothing to do if the user interrupts.
				}
			}
		}

        super.setVisible(visible);
	}
	
	private String getProposedProjectName() {
		// if there is only one makmake reference selected then use that name, otherwise
		// use the last segment of the proposed project root path.
		List<String> selectedMakMakeRefs = theWizard.getSelectedMakMakeReferences();
		String name = ""; //$NON-NLS-1$
		if (selectedMakMakeRefs.size() == 1) {
			name = selectedMakMakeRefs.get(0); 
			// remove file extension if present
			int loc = name.lastIndexOf("."); //$NON-NLS-1$
			if (loc > 0) {
				name = name.substring(0, loc);
			}
		} else {
			if (rootDirectoryPath.segmentCount() > 0) {
				name = rootDirectoryPath.lastSegment();
			}
		}
		return name;
	}

	@Override
	public void performHelp() {
        PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl().getShell(), ProjectUIHelpIds.PROJECT_PROPERTIES_PAGE);
	}
}
