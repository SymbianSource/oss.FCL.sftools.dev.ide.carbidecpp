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
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.nokia.carbide.cdt.builder.project.ISISBuilderInfo;
import com.nokia.carbide.cdt.internal.api.builder.SISBuilderInfo2;
import com.nokia.sdt.utils.ProjectFileResourceProxyVisitor;

public class AddEditSisFileToBuildDialog extends StatusDialog {

	private ISISBuilderInfo sisInfo;

	// SIS Builder
	private Combo pkgFileCombo;
	private Button pkgFileBrowseButton;
	private Text outputFileNameEdit;
	private Text contentSearchLocationEdit;
	private Button contentSearchLocationButton;
	private Button partialUpgradeCheckbox;

	// SISX Builder
	private Button dontSign;
	private Button selfSign;
	private Button certSign;
	private Label signedSisFileLabel;
	private Text signedSISFileNameEdit;
	private Label passwordLabel;
	private Text passwordEdit;
	private Label additionalOptionsLabel;
	private Text createSisOptionsEdit;
	private Label certificateLabel;
	private Text certificateEdit;
	private Button browseCertButton;
	private Label keyLabel;
	private Text keyEdit;
	private Button browseKeyButton;

	private IProject project;
	private boolean isEKA2;

	
	/**
	 * Create the dialog
	 * @param parent
	 * @param file
	 */
	public AddEditSisFileToBuildDialog(Shell parent, ISISBuilderInfo sisInfo, IProject project, boolean isEKA2) {
		super(parent);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		this.sisInfo = sisInfo;
		this.project = project;
		this.isEKA2 = isEKA2;
	}

	/**
	 * @see Windows#configureShell
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(newShell, CarbideCPPBuilderUIHelpIds.CARBIDE_BUILDER_SIS_DIALOG);
	}		

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		initializeDialogUnits(parent);

		Composite composite = (Composite) super.createDialogArea(parent);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Group sisFileCreationGroup = new Group(composite, SWT.NONE);
		sisFileCreationGroup.setText(Messages.getString("CarbideBuildConfigurationsPage.SIS_File_Creation_Options")); //$NON-NLS-1$
		sisFileCreationGroup.setLayout(new GridLayout(3, false));
		sisFileCreationGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label pkgFileLabel = new Label(sisFileCreationGroup, SWT.RIGHT);
		pkgFileLabel.setText(Messages.getString("CarbideBuildConfigurationsPage.PKG_File")); //$NON-NLS-1$
		pkgFileLabel.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.PKG_File_ToolTip")); //$NON-NLS-1$

		pkgFileCombo = new Combo(sisFileCreationGroup, SWT.NONE);
		pkgFileCombo.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.PKG_File_ToolTip")); //$NON-NLS-1$
		pkgFileCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		pkgFileCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				checkValues();
			}
		});
		
		for (IPath pkg : getProjectRelativePKGFiles()) {
			pkgFileCombo.add(pkg.toOSString());
		}

		pkgFileBrowseButton = new Button(sisFileCreationGroup, SWT.NONE);
		pkgFileBrowseButton.setText(Messages.getString("CarbideBuildConfigurationsPage.Browse")); //$NON-NLS-1$
		pkgFileBrowseButton.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.Browse_PKG_ToolTip")); //$NON-NLS-1$
		pkgFileBrowseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowseForPKG();
			}
		});
		
		Label outputFileNameLabel = new Label(sisFileCreationGroup, SWT.RIGHT);
		outputFileNameLabel.setText(Messages.getString("CarbideBuildConfigurationsPage.Output_SIS_Name")); //$NON-NLS-1$
		outputFileNameLabel.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.Output_SIS_ToolTip")); //$NON-NLS-1$
		
		outputFileNameEdit = new Text(sisFileCreationGroup, SWT.BORDER);
		outputFileNameEdit.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.Output_SIS_ToolTip")); //$NON-NLS-1$
		outputFileNameEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		outputFileNameEdit.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				checkValues();
			}
		});
		
		Label contentSearchLocationLabel = new Label(sisFileCreationGroup, SWT.RIGHT);
		contentSearchLocationLabel.setText(Messages.getString("CarbideBuildConfigurationsPage.Content_Search_Location")); //$NON-NLS-1$
		contentSearchLocationLabel.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.Content_Search_Location_ToolTip")); //$NON-NLS-1$
		
		contentSearchLocationEdit = new Text(sisFileCreationGroup, SWT.BORDER);
		contentSearchLocationEdit.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.Content_Search_Location_ToolTip")); //$NON-NLS-1$
		contentSearchLocationEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		contentSearchLocationEdit.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				checkValues();
			}
		});
		
		contentSearchLocationButton = new Button(sisFileCreationGroup, SWT.NONE);
		contentSearchLocationButton.setText(Messages.getString("CarbideBuildConfigurationsPage.Browse")); //$NON-NLS-1$
		contentSearchLocationButton.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.Browse_Content_Search_ToolTip")); //$NON-NLS-1$
		contentSearchLocationButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowseForContentSearchLocation();
			}
		});
		
		if (isEKA2) {
			partialUpgradeCheckbox = new Button(sisFileCreationGroup, SWT.CHECK);
			partialUpgradeCheckbox.setText(Messages.getString("CarbideBuildConfigurationsPage.Partial_Upgrade_Checkbox")); //$NON-NLS-1$
			partialUpgradeCheckbox.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.Partial_Upgrade_Checkbox_ToolTip")); //$NON-NLS-1$
			partialUpgradeCheckbox.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, true, false, 2, 1));
		}

		Group signingOptionsGroup = new Group(composite, SWT.NONE);
		signingOptionsGroup.setText(Messages.getString("CarbideBuildConfigurationsPage.Signing_Options")); //$NON-NLS-1$
		signingOptionsGroup.setLayout(new GridLayout(3, false));
		signingOptionsGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		dontSign = new Button(signingOptionsGroup, SWT.RADIO);
		dontSign.setText(Messages.getString("CarbideBuildConfigurationsPage.Dont_Sign")); //$NON-NLS-1$
		dontSign.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.Dont_Sign_ToolTip")); //$NON-NLS-1$
		dontSign.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		dontSign.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateControls();
				checkValues();
			}
		});
		
		selfSign = new Button(signingOptionsGroup, SWT.RADIO);
		selfSign.setText(Messages.getString("CarbideBuildConfigurationsPage.Self_Sign")); //$NON-NLS-1$
		selfSign.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.Self_Sign_ToolTip")); //$NON-NLS-1$
		selfSign.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		selfSign.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateControls();
				checkValues();
			}
		});

		certSign = new Button(signingOptionsGroup, SWT.RADIO);
		certSign.setText(Messages.getString("CarbideBuildConfigurationsPage.Cert_Sign")); //$NON-NLS-1$
		certSign.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.Cert_Sign_ToolTip")); //$NON-NLS-1$
		certSign.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		certSign.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateControls();
				checkValues();
			}
		});

		signedSisFileLabel = new Label(signingOptionsGroup, SWT.RIGHT);
		signedSisFileLabel.setText(Messages.getString("CarbideBuildConfigurationsPage.Signed_SIS_File_Name")); //$NON-NLS-1$
		signedSisFileLabel.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.SIS_File_ToolTip")); //$NON-NLS-1$

		signedSISFileNameEdit = new Text(signingOptionsGroup, SWT.BORDER);
		signedSISFileNameEdit.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.SIS_File_ToolTip")); //$NON-NLS-1$
		signedSISFileNameEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		passwordLabel = new Label(signingOptionsGroup, SWT.RIGHT);
		passwordLabel.setText(Messages.getString("CarbideBuildConfigurationsPage.Password")); //$NON-NLS-1$
		passwordLabel.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.Password_ToolTip")); //$NON-NLS-1$

		passwordEdit = new Text(signingOptionsGroup, SWT.BORDER);
		passwordEdit.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.Password_ToolTip")); //$NON-NLS-1$
		passwordEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		additionalOptionsLabel = new Label(signingOptionsGroup, SWT.RIGHT);
		additionalOptionsLabel.setText(Messages.getString("CarbideBuildConfigurationsPage.Additional_Options")); //$NON-NLS-1$
		additionalOptionsLabel.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.Additional_Options_ToolTip")); //$NON-NLS-1$

		createSisOptionsEdit = new Text(signingOptionsGroup, SWT.BORDER);
		createSisOptionsEdit.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.Additional_Options_ToolTip")); //$NON-NLS-1$
		createSisOptionsEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		certificateLabel = new Label(signingOptionsGroup, SWT.RIGHT);
		certificateLabel.setText(Messages.getString("CarbideBuildConfigurationsPage.Certificate")); //$NON-NLS-1$
		certificateLabel.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.Cert_ToolTip")); //$NON-NLS-1$

		certificateEdit = new Text(signingOptionsGroup, SWT.BORDER);
		certificateEdit.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.Cert_ToolTip")); //$NON-NLS-1$
		certificateEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		certificateEdit.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				checkValues();
			}
		});

		browseCertButton = new Button(signingOptionsGroup, SWT.NONE);
		browseCertButton.setText(Messages.getString("CarbideBuildConfigurationsPage.Browse")); //$NON-NLS-1$
		browseCertButton.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.Cert_Browse_ToolTip")); //$NON-NLS-1$
		browseCertButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowseForCert();
			}
		});
		
		keyLabel = new Label(signingOptionsGroup, SWT.RIGHT);
		keyLabel.setText(Messages.getString("CarbideBuildConfigurationsPage.Key")); //$NON-NLS-1$
		keyLabel.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.Key_ToolTip")); //$NON-NLS-1$

		keyEdit = new Text(signingOptionsGroup, SWT.BORDER);
		keyEdit.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.Key_ToolTip")); //$NON-NLS-1$
		keyEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		keyEdit.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				checkValues();
			}
		});

		browseKeyButton = new Button(signingOptionsGroup, SWT.NONE);
		browseKeyButton.setText(Messages.getString("CarbideBuildConfigurationsPage.Browse")); //$NON-NLS-1$
		browseKeyButton.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.Browse_Key_ToolTip")); //$NON-NLS-1$
		browseKeyButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowseForKey();
			}
		});

		initValues();
		
		applyDialogFont(composite);		

		return composite;
	}

	public void create() {
		super.create();
		checkValues();
	}

	protected void okPressed() {
		sisInfo.setPKGFile(pkgFileCombo.getText());
		sisInfo.setOutputSISFileName(outputFileNameEdit.getText());
		sisInfo.setContentSearchLocation(contentSearchLocationEdit.getText());
		
		if (isEKA2 && sisInfo instanceof SISBuilderInfo2) {
			((SISBuilderInfo2)sisInfo).setPartialUpgrade(partialUpgradeCheckbox.getSelection());
		}

		int type = ISISBuilderInfo.DONT_SIGN;
		if (selfSign.getSelection()) {
			type = ISISBuilderInfo.SELF_SIGN;
		} else if (certSign.getSelection()) {
			type = ISISBuilderInfo.KEY_CERT_SIGN;
		}
		sisInfo.setSigningType(type);
		
		sisInfo.setSignedSISFileName(signedSISFileNameEdit.getText());
		sisInfo.setPassword(passwordEdit.getText());
		sisInfo.setAdditionalOptions(createSisOptionsEdit.getText());
		sisInfo.setCertificate(certificateEdit.getText());
		sisInfo.setKey(keyEdit.getText());

		super.okPressed();
	}

	private void initValues() {
		
		pkgFileCombo.setText(sisInfo.getPKGFileString());
		outputFileNameEdit.setText(sisInfo.getUnsignedSISFileName());
		contentSearchLocationEdit.setText(sisInfo.getContentSearchLocation());
		
		if (isEKA2 && sisInfo instanceof SISBuilderInfo2) {
			partialUpgradeCheckbox.setSelection(((SISBuilderInfo2)sisInfo).isPartialUpgrade());
		}

		dontSign.setSelection(sisInfo.getSigningType() == ISISBuilderInfo.DONT_SIGN);
		selfSign.setSelection(sisInfo.getSigningType() == ISISBuilderInfo.SELF_SIGN);
		certSign.setSelection(sisInfo.getSigningType() == ISISBuilderInfo.KEY_CERT_SIGN);
		signedSISFileNameEdit.setText(sisInfo.getSignedSISFileName());
		passwordEdit.setText(sisInfo.getPassword());
		createSisOptionsEdit.setText(sisInfo.getAdditionalOptions());
		certificateEdit.setText(sisInfo.getCertificate());
		keyEdit.setText(sisInfo.getKey());
		
		updateControls();
	}
	
	private void updateControls() {
		boolean enableSigningControls = selfSign.getSelection() || certSign.getSelection();
		boolean enableCertSigningControls = certSign.getSelection();
		
		signedSisFileLabel.setEnabled(enableSigningControls);
		signedSISFileNameEdit.setEnabled(enableSigningControls);
		passwordLabel.setEnabled(enableSigningControls);
		passwordEdit.setEnabled(enableSigningControls);
		additionalOptionsLabel.setEnabled(enableSigningControls);
		createSisOptionsEdit.setEnabled(enableSigningControls);
		certificateLabel.setEnabled(enableCertSigningControls);
		certificateEdit.setEnabled(enableCertSigningControls);
		browseCertButton.setEnabled(enableCertSigningControls);
		keyLabel.setEnabled(enableCertSigningControls);
		keyEdit.setEnabled(enableCertSigningControls);
		browseKeyButton.setEnabled(enableCertSigningControls);
	}

	private void handleBrowseForPKG() {
		String[] extFilter = new String[] {"*.pkg", "*.*"}; //$NON-NLS-1$
		String selectedFile = showBrowseDialog(pkgFileCombo.getText(), Messages.getString("CarbideBuildConfigurationsPage.Select_a_PKG_File"), extFilter); //$NON-NLS-1$
		if (selectedFile != null) {
			if (!selectedFile.equals(pkgFileCombo.getText())) {
				pkgFileCombo.setText(selectedFile);
			}
		}
	}
	
	private void handleBrowseForContentSearchLocation() {
		
		DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.OPEN);
		dialog.setText("Choose a folder...");
		dialog.setFilterPath(contentSearchLocationEdit.getText());
		String selectedDir = dialog.open();
		
		if (selectedDir != null && selectedDir.length() > 0) {
			contentSearchLocationEdit.setText(selectedDir);
		}
	}
	
	private void handleBrowseForKey() {
		String[] extFilter = new String[] {"*.*"}; //$NON-NLS-1$
		String selectedFile = showBrowseDialog(keyEdit.getText(), Messages.getString("CarbideBuildConfigurationsPage.Select_a_key"), extFilter); //$NON-NLS-1$
		if (selectedFile != null) {
			if (!selectedFile.equals(keyEdit.getText())) {
				keyEdit.setText(selectedFile);

				// check the cert and if it's empty check to see if a .key file
				// exists in the same location and set it if so
				if (certificateEdit.getText().length() == 0) {
					IPath file = new Path(selectedFile);
					file = file.removeFileExtension();
					file = file.addFileExtension("cer"); //$NON-NLS-1$
					if (file.toFile().exists()) {
						certificateEdit.setText(file.toOSString());
					} else {
						file = file.removeFileExtension();
						file.addFileExtension("cert"); //$NON-NLS-1$
						if (file.toFile().exists()) {
							certificateEdit.setText(file.toOSString());
						}
					}
				}
			}
		}
	}
	
	private void handleBrowseForCert() {
		String[] extFilter = new String[] {"*.*"}; //$NON-NLS-1$
		String selectedFile = showBrowseDialog(certificateEdit.getText(), Messages.getString("CarbideBuildConfigurationsPage.Select_a_certificate"), extFilter); //$NON-NLS-1$
		if (selectedFile != null) {
			if (!selectedFile.equals(certificateEdit.getText())) {
				certificateEdit.setText(selectedFile);

				// check the cert and if it's empty check to see if a .cert file
				// exists in the same location and set it if so
				if (keyEdit.getText().length() == 0) {
					IPath file = new Path(selectedFile);
					file = file.removeFileExtension();
					file = file.addFileExtension("key"); //$NON-NLS-1$
					if (file.toFile().exists()) {
						keyEdit.setText(file.toOSString());
					}
				}
			}
		}
	}

	protected void checkValues() {
		StatusInfo status = new StatusInfo();
		status.setOK();
		updateStatus(status);		
		
		String pkgFile = pkgFileCombo.getText().trim();
		if (pkgFile.length() < 1) {
			status.setError(Messages.getString("AddEditSisFileToBuildDialog.NoPKGError")); //$NON-NLS-1$
			updateStatus(status);
			return;
		}
		
		IPath pkgPath = new Path(pkgFile);
		if (!pkgPath.isAbsolute()) {
			pkgPath = project.getLocation().append(pkgPath);
		}
		
		if (!pkgPath.toFile().exists()) {
			status.setError(Messages.getString("AddEditSisFileToBuildDialog.PKGDoesNotExist")); //$NON-NLS-1$
			updateStatus(status);
			return;
		}

		String outputName = outputFileNameEdit.getText().trim();
		if (outputName.length() > 0) {
			if (!outputName.toLowerCase().endsWith(".sis")) {
				status.setWarning(Messages.getString("AddEditSisFileToBuildDialog.OutputNameShouldEndWithSis")); //$NON-NLS-1$
				updateStatus(status);
			}
		}

		String searchLocation = contentSearchLocationEdit.getText().trim();
		
		if (searchLocation.length() > 0) {
			if (!new Path(searchLocation).toFile().exists()) {
				status.setError(Messages.getString("AddEditSisFileToBuildDialog.SearchDirectoryDoesNotExist")); //$NON-NLS-1$
				updateStatus(status);
				return;
			}
		}
		
		if (certSign.getSelection()) {
			// if they're signing then there must be a valid certifcate and key
			String cert = certificateEdit.getText().trim();
			if (cert.length() < 1) {
				status.setError(Messages.getString("AddEditSisFileToBuildDialog.NoCertError")); //$NON-NLS-1$
				updateStatus(status);
				return;
			}
			
			if (!new Path(cert).toFile().exists()) {
				status.setError(Messages.getString("AddEditSisFileToBuildDialog.CertDoesNotExist")); //$NON-NLS-1$
				updateStatus(status);
				return;
			}
			
			String key = keyEdit.getText().trim();
			if (key.length() < 1) {
				status.setError(Messages.getString("AddEditSisFileToBuildDialog.NoKeyError")); //$NON-NLS-1$
				updateStatus(status);
				return;
			}
			
			if (!new Path(key).toFile().exists()) {
				status.setError(Messages.getString("AddEditSisFileToBuildDialog.KeyDoesNotExist")); //$NON-NLS-1$
				updateStatus(status);
				return;
			}
		}
	}

	List<IPath> getProjectRelativePKGFiles() {
		List<IPath> pkgFileList = new ArrayList<IPath>();
		try {
			// get all the (non-derived) PKG files from the project
			ProjectFileResourceProxyVisitor projectVisitor = new ProjectFileResourceProxyVisitor("pkg", true, true); //$NON-NLS-1$
			project.accept(projectVisitor, IResource.NONE);
			pkgFileList.addAll(projectVisitor.getRequestedFiles());
			
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		return pkgFileList;
	}
	
    private String showBrowseDialog(String startDir, String title, String[] extFilter) {
        FileDialog fileDialog = new FileDialog(getShell(), SWT.OPEN);
        fileDialog.setFilterExtensions(extFilter);
        fileDialog.setText(title);

        int lastSeparatorIndex = startDir.lastIndexOf(File.separator);
        if (lastSeparatorIndex != -1) {
        	fileDialog.setFilterPath(startDir.substring(0, lastSeparatorIndex));
        }

        return fileDialog.open();
    }
}
