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
package com.nokia.carbide.cpp.project.ui.sharedui;

import java.io.File;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

import com.nokia.carbide.cpp.internal.api.sdk.SBSv2Utils;
import com.nokia.carbide.cpp.internal.api.sdk.ui.TemplateUtils;
import com.nokia.carbide.cpp.internal.project.ui.Messages;
import com.nokia.carbide.cpp.internal.project.ui.sharedui.BuilderSelectionComposite;
import com.nokia.carbide.internal.api.templatewizard.ui.IWizardDataPage;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.HostOS;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

/**
 * Page that gathers name and location for new project
 */
public class NewProjectPage extends WizardNewProjectCreationPage implements IWizardDataPage {

	private static final String USE_SBSV2_BUILDER = "useSBSv2Builder"; //$NON-NLS-1$

    private Map<String, Object> data;
	private Collection<IProject> projectCache;
	
	/** composite displayed when SBSv1 and SBSv2 are both available */
	private BuilderSelectionComposite builderComposite;
	
	public NewProjectPage(String title, String description) {
		super(Messages.getString("NewProjectPage.Name")); //$NON-NLS-1$
		setTitle(title);
		setDescription(description);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.templatewizard.ui.IWizardDataPage#getPageValues()
	 */
	public Map<String, Object> getPageValues() {
		if (data == null)
			data = new HashMap<String, Object>();

		String projectName = getProjectName();
		data.put(TemplateUtils.PROJECT_NAME, projectName);
		String baseName = TextUtils.legalizeIdentifier(projectName);
		data.put(TemplateUtils.BASE_NAME, baseName);
		data.put(TemplateUtils.BASE_NAME_UPPER, baseName.toUpperCase());
		data.put(TemplateUtils.BASE_NAME_LOWER, baseName.toLowerCase());
		IPath locationPath = getLocationPath();
		data.put(TemplateUtils.PROJECT_LOCATION, locationPath.toPortableString());
		data.put(TemplateUtils.USE_DEFAULT_LOCATION, new Boolean(useDefaults()));
		
		if (builderComposite != null) {
			data.put(USE_SBSV2_BUILDER, new Boolean(builderComposite.useSBSv2Builder()));
		} else if (!SBSv2Utils.enableSBSv1Support()) {
			data.put(USE_SBSV2_BUILDER, Boolean.TRUE);
		} else if (!SBSv2Utils.enableSBSv2Support()) {
			data.put(USE_SBSV2_BUILDER, Boolean.FALSE);
		}
		
		return data;
	}

	@Override
	protected boolean validatePage() {
		String projectName = getProjectName();
		
		if (projectName.length() == 0) {
			// don't report error for empty required fields, only disable next (per Eclipse guidlines)
			//setErrorMessage(Messages.getString("NewProjectPage.InvalidProjectNameError")); //$NON-NLS-1$
			return false;
		}
		// builds don't work if the project name begins with a digit - see bug #2225
		if (Character.isDigit(projectName.charAt(0))) {
			setErrorMessage(Messages.getString("NewProjectPage.NameBeginWithDigitError")); //$NON-NLS-1$
			return false;
		}
		// periods in project names can cause runtime panic 23 - see bug #5760
		// removed check for period in path to project and added check for project name
		if (projectName.contains(".")) { //$NON-NLS-1$
			setErrorMessage(Messages.getString("NewProjectPage.ProjectNameWithPeriodError")); //$NON-NLS-1$
			return false;
		}
		if (!FileUtils.isValidCarbideProjectPathSegment(projectName)) {
			String error = 
				MessageFormat.format(Messages.getString("NewProjectPage.InvalidProjectName"),  //$NON-NLS-1$
					new Object[] { projectName });
			setErrorMessage(error);
			return false;
		}
		if (!validateLocationForSpaces()) {
			setErrorMessage(Messages.getString("NewProjectPage.NameWithSpacesError")); //$NON-NLS-1$
			return false;
		}
		if (!validateLocationSegments()) {
			setErrorMessage(MessageFormat.format(Messages.getString("NewProjectPage.InvalidLocationError"),  //$NON-NLS-1$
					new Object[] { getLocationPath().toString() } ));
			return false;
		}
        if (HostOS.IS_WIN32 && !validateDeviceExists()) {
			setErrorMessage(Messages.getString("NewProjectPage.InvalidDriveError")); //$NON-NLS-1$
			return false;
        }
		if (projectExists(projectName)) {
			setErrorMessage(Messages.getString("NewProjectPage.DuplicateProjectNameError")); //$NON-NLS-1$
			return false;
		}
        // builds don't work if the project name if greater than 32 characters for
        // some reason - see bug #2231.
        if (projectName.length() > 32) {
			setErrorMessage(Messages.getString("NewProjectPage.NameOver32CharsError")); //$NON-NLS-1$
			return false;
        }
        setErrorMessage(null);
        boolean valid = super.validatePage();
        if (HostOS.IS_WIN32) {
	        if (locationGettingLong(projectName)) {
	        	setMessage(Messages.getString("NewProjectPage.ExcessivelyLongPathError"), WARNING); //$NON-NLS-1$
	        	return valid;
	        }
        }
        if (!projectDirEqualsProjectName(projectName)) {
        	setMessage(Messages.getString("NewProjectPage.DirectoryNameWarning"), WARNING); //$NON-NLS-1$
        	return valid;
        }
		
        if (builderComposite != null) {
        	
        	String msg = builderComposite.validatePage();
        	if (msg != null){
        		setMessage(msg, ERROR);
        		return false;
        	}
        }
        
        return true;
	}

	private boolean validateLocationSegments() {
		IPath locationPath = getLocationPath();
		if (!locationPath.isAbsolute())
			return false;
		
		String[] segments = locationPath.segments();
		for (int i = 0; i < segments.length; i++) {
			String segment = segments[i];
			if (!FileUtils.isValidCarbideProjectPathSegment(segment))
				return false;
		}
		
		return true;
	}

	private boolean validateLocationForSpaces() {
		String[] segments = getLocationPath().segments();
		for (int i = 0; i < segments.length; i++) {
			String segment = segments[i];
			if (segment.contains(" ")) //$NON-NLS-1$
				return false;
		}
		
		return true;
	}
	
	private boolean validateDeviceExists() {
		IPath locationPath = getLocationPath();
		String device = locationPath.getDevice();
		if (device != null) {
			return new File(device).exists();
		}
		return false;
	}
	
	private boolean locationGettingLong(String projectName) {
		IPath locationPath = getLocationPath();
		int locationLen = locationPath.toString().length();
		if (locationLen + projectName.length() > 128)
			return true;
		
		return false;
	}
	
	private boolean projectDirEqualsProjectName(String projectName) {
		if (useDefaults())
			return true;
		
		String lastSegment = getLocationPath().lastSegment();
		return lastSegment != null && lastSegment.equalsIgnoreCase(projectName);
	}

	/**
	 * @param projectName
	 * @return true if project with name found in workspace using case insensitive search
	 */
	private boolean projectExists(String projectName) {
		if (projectCache == null)
			projectCache = Arrays.asList(ResourcesPlugin.getWorkspace().getRoot().getProjects());
		
		for (IProject project : projectCache) {
			if (project.getName().equalsIgnoreCase(projectName))
				return true;
		}
		return false;
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);

		Control control = getControl();
		if (control instanceof Composite) {
			builderComposite = new BuilderSelectionComposite((Composite)control);
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

		getControl().setData(".uid", "NewProjectPage"); //$NON-NLS-1$ //$NON-NLS-2$
		getControl().setData("WizardPage", this); //$NON-NLS-1$
		
		restoreDialogSettings();
		setPageComplete(validatePage());
	}

    public void saveDialogSettings() {
    	if (builderComposite != null) {
            IDialogSettings settings = getDialogSettings();
            if (settings != null) {
            	builderComposite.saveDialogSettings(settings);
            }
    	}
    }

    private void restoreDialogSettings() {
    	if (builderComposite != null) {
            IDialogSettings settings = getDialogSettings();
            if (settings != null) {
            	builderComposite.restoreDialogSettings(settings);
            }
    	}
    }
}
