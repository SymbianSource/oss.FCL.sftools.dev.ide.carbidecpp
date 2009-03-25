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

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.*;
import com.nokia.cpp.internal.api.utils.core.Check;

import cwdbg.PreferenceConstants;

import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.settings.model.CProjectDescriptionEvent;
import org.eclipse.cdt.core.settings.model.ICProjectDescriptionListener;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;

import java.io.File;
import java.text.MessageFormat;

public class TRKSISSelectionWizardPage extends WizardPage implements ICProjectDescriptionListener {
    
    private boolean isDisposed = false;
	private Combo sisFile;
	private String sisPath = ""; //$NON-NLS-1$
	private Text sisEdit;
	private Button sisBrowse;
	private final ISummaryTextItemContainer summaryTextItemContainer;
	
    
    public TRKSISSelectionWizardPage(ISummaryTextItemContainer summaryTextItemContainer) {
        super(Messages.getString("TRKSISSelectionWizardPage.0")); //$NON-NLS-1$
		Check.checkArg(summaryTextItemContainer);
		this.summaryTextItemContainer = summaryTextItemContainer;
        setPageComplete(false);
        setTitle(Messages.getString("TRKSISSelectionWizardPage.0")); //$NON-NLS-1$
        setDescription(Messages.getString("TRKSISSelectionWizardPage.1")); //$NON-NLS-1$
    }
    
    public void dispose() {
    	isDisposed = true;
    	setSisPath();
    	
		CoreModel.getDefault().getProjectDescriptionManager().removeCProjectDescriptionListener(this);

    	super.dispose();
    }
    
	/*
     * @see IDialogPage#createControl(Composite)
     */
    public void createControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NULL);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        composite.setLayout(layout);
        
        GridData data = new GridData();
        composite.setLayoutData(data);
            
		AbstractLaunchWizard wizard = (AbstractLaunchWizard) getWizard();
		final IProject project = wizard.getProject();
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		if (cpi != null) {
			final Label sisLabel = new Label(composite, SWT.NONE);
			sisLabel.setText(Messages.getString("TRKSISSelectionWizardPage.2")); //$NON-NLS-1$
			data = new GridData();
			data.horizontalSpan = 1;
			sisLabel.setLayoutData(data);
			sisLabel.setToolTipText(Messages.getString("TRKSISSelectionWizardPage.3")); //$NON-NLS-1$

			sisFile = new Combo(composite, SWT.READ_ONLY);
			data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
			data.horizontalSpan = 1;
			sisFile.setLayoutData(data);
			sisFile.setToolTipText(Messages.getString("TRKSISSelectionWizardPage.3")); //$NON-NLS-1$
			sisFile.add(Messages.getString("TRKSISSelectionWizardPage.5")); //$NON-NLS-1$
			
			ICarbideBuildConfiguration config = cpi.getDefaultConfiguration();
			for (ISISBuilderInfo info : config.getSISBuilderInfoList()) {
				IPath sisPath = info.getSigningType() == ISISBuilderInfo.DONT_SIGN ? info.getUnsignedSISFullPath() : info.getSignedSISFullPath();
				sisFile.add(sisPath.toOSString());
			}
			
			// select the first sis file if any, otherwise select none
			if (sisFile.getItemCount() > 1) {
				sisFile.select(1);
			} else {
				sisFile.select(0);
			}

			// listen for events so we can detect if they click on the link below and add new sis info.
			CoreModel.getDefault().getProjectDescriptionManager().addCProjectDescriptionListener(this, CProjectDescriptionEvent.DATA_APPLIED);

			Link link = new Link(composite, SWT.NONE);
			link.setText("<a>" + Messages.getString("TRKSISSelectionWizardPage.4") + "...</a>"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			link.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
			link.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					PreferencesUtil.createPropertyDialogOn(getShell(), project, "com.nokia.carbide.cdt.internal.builder.ui.CarbideBuildConfigurationsPage", null, null).open(); //$NON-NLS-1$
				}
			});
		} else {
			// not a Carbide project, just an executable.  show a browse/edit combo
			// to let them select a sis file if they want to.
			final Label sisLabel = new Label(composite, SWT.NONE);
			sisLabel.setText(Messages.getString("TRKSISSelectionWizardPage.2")); //$NON-NLS-1$
			data = new GridData();
			data.horizontalSpan = 2;
			sisLabel.setLayoutData(data);
			sisLabel.setToolTipText(Messages.getString("TRKSISSelectionWizardPage.3")); //$NON-NLS-1$

			sisEdit = new Text(composite, SWT.BORDER);
			sisEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			sisEdit.setToolTipText(Messages.getString("TRKSISSelectionWizardPage.3")); //$NON-NLS-1$
			sisEdit.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					checkValid();
				}
			});

			sisBrowse = new Button(composite, SWT.NONE);
			sisBrowse.setText(Messages.getString("TRKSISSelectionWizardPage.9")); //$NON-NLS-1$
			sisBrowse.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
			sisBrowse.addSelectionListener(new SelectionAdapter() {

				public void widgetSelected(SelectionEvent evt) {
					FileDialog dialog = new FileDialog(getShell(), SWT.NONE);

					dialog.setText(Messages.getString("TRKSISSelectionWizardPage.6")); //$NON-NLS-1$
					dialog.setFilterExtensions(new String[] {"*.sis*", "*.*"}); //$NON-NLS-1$ //$NON-NLS-2$
					dialog.setFilterNames(new String[] {Messages.getString("TRKSISSelectionWizardPage.7"), Messages.getString("TRKSISSelectionWizardPage.8")}); //$NON-NLS-1$ //$NON-NLS-2$

					String result = dialog.open();
					if (result != null && new File(result).exists()) {
						sisEdit.setText(result);
						checkValid();
					}
				}
			});
		}

		setControl(composite);
        Dialog.applyDialogFont(parent);
        setPageComplete(true);
    }
    
    private void checkValid() {
    	setErrorMessage(null);
    	
    	if (sisEdit != null) {
    		String text = sisEdit.getText().trim();
    		if (text.length() > 0) {
    			// empty is allowed, but if they specify something, make sure
    			// it exists
    			File file = new File(text);
    			if (!file.exists()) {
    				setErrorMessage(Messages.getString("TRKSISSelectionWizardPage.10")); //$NON-NLS-1$
    			}
    		}
    	}
    }
    
    private void setSisPath() {
    	if (sisFile != null) {
        	sisPath = sisFile.getSelectionIndex() == 0 ? "" : sisFile.getText(); //$NON-NLS-1$
    	} else if (sisEdit != null) {
    		sisPath = sisEdit.getText();
    	}
    }
    
    void updateConfiguration(ILaunchConfigurationWorkingCopy config) {
    	if (!isDisposed)
    		setSisPath();
    		
   		config.setAttribute(PreferenceConstants.J_PN_SisFileHostPath, sisPath);
    }

    @Override
    public void performHelp() {
		PlatformUI.getWorkbench().getHelpSystem().displayHelp(LaunchWizardHelpIds.WIZARD_TRK_SIS_SELECTION_PAGE);
    }

	public void handleEvent(CProjectDescriptionEvent event) {
		if (isDisposed || !isCurrentPage()) {
			return;
		}
		
		IProject project = event.getProject() ;
		AbstractLaunchWizard wizard = (AbstractLaunchWizard) getWizard();
		
		if (project != wizard.getProject()) {
			return;
		}
		
		if (sisFile != null) {
			ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
			if (cpi != null) {
				sisFile.removeAll();

				sisFile.add(Messages.getString("TRKSISSelectionWizardPage.5")); //$NON-NLS-1$
				
				ICarbideBuildConfiguration config = cpi.getDefaultConfiguration();
				for (ISISBuilderInfo info : config.getSISBuilderInfoList()) {
					IPath sisPath = info.getSigningType() == ISISBuilderInfo.DONT_SIGN ? info.getUnsignedSISFullPath() : info.getSignedSISFullPath();
					sisFile.add(sisPath.toOSString());
				}
				
				// select the first sis file if any, otherwise select none
				if (sisFile.getItemCount() > 1) {
					sisFile.select(1);
				} else {
					sisFile.select(0);
				}
			}
		}
	}

    public void setVisible(boolean visible) {
    	super.setVisible(visible);
    	if (!visible) {
    		setSisPath();
    		summaryTextItemContainer.putSummaryTextItem("SisFile",  //$NON-NLS-1$
    				MessageFormat.format("{0} {1}", new Object[] { //$NON-NLS-1$
    						Messages.getString("TRKSISSelectionWizardPage.2"), //$NON-NLS-1$
    						sisPath.length() == 0 ? Messages.getString("TRKSISSelectionWizardPage.5") : sisPath})); //$NON-NLS-1$
    	}
    }
    
}