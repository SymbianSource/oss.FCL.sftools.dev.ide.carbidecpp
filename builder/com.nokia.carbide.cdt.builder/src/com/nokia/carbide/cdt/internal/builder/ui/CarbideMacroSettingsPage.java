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
package com.nokia.carbide.cdt.internal.builder.ui;

import java.io.File;

import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.internal.builder.CarbideProjectInfo;
import com.nokia.carbide.cdt.internal.builder.CarbideProjectModifier;

public class CarbideMacroSettingsPage extends PropertyPage {
	
	private Text fMacroFileEdit;
	private Button fMacroFileBrowseButton;
	private Button fUseMMPMacros;
	private Link fLink;
	
	
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));
		composite.setLayoutData(new GridData());
		
		Label macroFileLabel = new Label(composite, SWT.NONE);
		macroFileLabel.setText(Messages.getString("CarbideMacroSettingsPage.MacroFileLabel")); //$NON-NLS-1$
		macroFileLabel.setToolTipText(Messages.getString("CarbideMacroSettingsPage.MacroFileToolTip")); //$NON-NLS-1$

		fMacroFileEdit = new Text(composite, SWT.BORDER);
		fMacroFileEdit.setToolTipText(Messages.getString("CarbideMacroSettingsPage.MacroFileToolTip")); //$NON-NLS-1$
		fMacroFileEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		fMacroFileEdit.addListener(SWT.Modify, new Listener() {
			public void handleEvent(Event event) {
				setValid(isValid());
			}
		});

		fMacroFileBrowseButton = new Button(composite, SWT.NONE);
		fMacroFileBrowseButton.setText(Messages.getString("CarbideMacroSettingsPage.Browse")); //$NON-NLS-1$
		fMacroFileBrowseButton.setToolTipText(Messages.getString("CarbideMacroSettingsPage.Browse_ToolTip")); //$NON-NLS-1$
		fMacroFileBrowseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(getShell(), SWT.OPEN);
		        fileDialog.setFilterExtensions(new String[] {"*.*"});
		        fileDialog.setText(Messages.getString("CarbideMacroSettingsPage.BrowseDialogTitle")); //$NON-NLS-1$
		        
		        String path = fileDialog.open();
		        if (path != null) {
		        	fMacroFileEdit.setText(path);
		        }
			}
		});

		fUseMMPMacros = new Button(composite, SWT.CHECK);
		fUseMMPMacros.setText(Messages.getString("CarbideMacroSettingsPage.UseMMPMacros")); //$NON-NLS-1$
		fUseMMPMacros.setToolTipText(Messages.getString("CarbideMacroSettingsPage.UseMMPMacrosToolTip")); //$NON-NLS-1$
		fUseMMPMacros.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
	
		fLink = new Link(composite, SWT.NONE);
		fLink.setText("<a>" + Messages.getString("CarbideMacroSettingsPage.ShowPathsAndSymbolsPage") + "...</a>"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		fLink.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		fLink.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// I don't see a way to open it to the paths and symbols tab, only the page
				((IWorkbenchPreferenceContainer)getContainer()).openPage("com.nokia.carbide.cdt.internal.builder.ui.CarbideBuildConfigurationsPage", null);
			}
		});

		// read in the settings and populate the prefs...
		IProject project = getProject();
		if (project != null){
			loadProjectSettings(project);
		}
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, CarbideCPPBuilderUIHelpIds.CARBIDE_MACRO_SETTINGS_PAGE);

		return composite;
	}
	
	public IProject getProject(){
		Object tempElement = getElement();
		IProject project = null;
		if (tempElement != null) {
			if (tempElement instanceof IProject) {
				project = (IProject) tempElement;
			} else if (tempElement instanceof ICProject) {
				project = ((ICProject)tempElement).getProject();
			}
		}
			
		return project;
	}
	
	private boolean savePageSettings(IProject project){
		
		boolean success = false;
		
		if (project == null){
			return false;
		}
		
        CarbideProjectModifier cpi = (CarbideProjectModifier)CarbideBuilderPlugin.getBuildManager().getProjectModifier(getProject());
		if (cpi != null){
			
			cpi.writeProjectSetting(CarbideProjectInfo.MACROS_FILE, fMacroFileEdit.getText().trim());
			
			String useMacrosValue = "true"; //$NON-NLS-1$
			if (!fUseMMPMacros.getSelection()){
				useMacrosValue = "false"; //$NON-NLS-1$
			}
			cpi.writeProjectSetting(CarbideProjectInfo.USE_MMP_MACROS, useMacrosValue);
			success = cpi.saveChanges();

			// call paths and symbols page to refresh itself
			PathsAndSymbolsTabComposite.refresh();
		}
		
		return success;
	}
	
	private void loadProjectSettings(IProject project) {
		
        CarbideProjectInfo cpi = (CarbideProjectInfo)CarbideBuilderPlugin.getBuildManager().getProjectInfo(getProject());
        if (cpi != null) {
			fMacroFileEdit.setText(cpi.getMacrosFile());
			fUseMMPMacros.setSelection(cpi.shouldUseMMPMacros());
        }
	}
	
	public boolean performOk() {
		IProject project = getProject();
		if (project != null){
			savePageSettings(project);
		}
		super.performOk();
		return true;
	}
	
	@Override
	public boolean isValid() {
		setErrorMessage(null);

		String macroFile = fMacroFileEdit.getText().trim();
		if (macroFile != null && macroFile.length() > 0) {
			File file = new File(macroFile);
			if (!file.exists()) {
				setErrorMessage(Messages.getString("CarbideMacroSettingsPage.BadMacroFileError")); //$NON-NLS-1$
				return false;
			}
		}
		return true;
	}

	@Override
	protected void performDefaults() {
		fMacroFileEdit.setText(""); //$NON-NLS-1$
		fUseMMPMacros.setSelection(false);

		super.performDefaults();
	}
}
