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
package com.nokia.cdt.internal.debug.launch.ui;

import java.io.File;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.nokia.cpp.internal.api.utils.ui.BrowseDialogUtils;

public class AddEditFileToTransferDialog extends StatusDialog {

	private FileToTransfer fFile;
	private Text hostPath;
	private Button hostBrowse;
	private Text targetPath;

	/**
	 * Create the dialog
	 * @param parent
	 * @param file
	 */
	public AddEditFileToTransferDialog(Shell parent, FileToTransfer file) {
		super(parent);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		fFile = file;
	}

	/**
	 * @see Windows#configureShell
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(newShell, LaunchTabHelpIds.RUN_MODE_FILE_TRANSFER_DIALOG);
	}		

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		initializeDialogUnits(parent);
		Composite composite= (Composite) super.createDialogArea(parent);
		
		GridLayout layout = (GridLayout) composite.getLayout();
		layout.numColumns = 2;
		
		final Label hostLabel = new Label(composite, SWT.NONE);
		hostLabel.setText(Messages.getString("AddEditFileToTransferDialog.1")); //$NON-NLS-1$
		GridData data = new GridData(GridData.FILL, GridData.CENTER, true, false);
		data.horizontalSpan = 2;
		hostLabel.setLayoutData(data);
		hostLabel.setToolTipText(Messages.getString("AddEditFileToTransferDialog.2")); //$NON-NLS-1$

		hostPath = new Text(composite, SWT.BORDER);
		hostPath.setText(fFile.getHostPath());
		data = new GridData(GridData.FILL, GridData.CENTER, true, false);
		hostPath.setLayoutData(data);
		hostPath.setToolTipText(Messages.getString("AddEditFileToTransferDialog.2")); //$NON-NLS-1$
		hostPath.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				checkValues();
			}
		});

		hostBrowse = new Button(composite, SWT.PUSH);
		hostBrowse.setFont(parent.getFont());
		hostBrowse.setText(Messages.getString("AddEditFileToTransferDialog.3")); //$NON-NLS-1$
		data = new GridData();
		hostBrowse.setLayoutData(data);	
		hostBrowse.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent evt) {
				FileDialog dialog = new FileDialog(getShell(), SWT.NONE);

				dialog.setText(Messages.getString("AddEditFileToTransferDialog.4")); //$NON-NLS-1$
				dialog.setFilterExtensions(new String[] {"*"}); //$NON-NLS-1$
				dialog.setFilterNames(new String[] {Messages.getString("AddEditFileToTransferDialog.11")}); //$NON-NLS-1$
				
				BrowseDialogUtils.initializeFrom(dialog, hostPath);
				
				String result = dialog.open();
				if (result != null) {
					IPath path = new Path(result);
					if (path.toFile().exists()) {
						hostPath.setText(result);
						
						// if target path is empty, set it to something useful here.
						if (targetPath.getText().trim().length() < 1) {
							String tp = "C:\\"; //$NON-NLS-1$
							
							if (path.getFileExtension().compareToIgnoreCase("app") == 0) { //$NON-NLS-1$
								// this is an EKA1 app
								tp += "system\\apps\\" + path.removeFirstSegments(path.segmentCount()-2).setDevice(null).toOSString(); //$NON-NLS-1$
							} else if (path.toOSString().toLowerCase().indexOf("\\epoc32\\release\\") >= 0) { //$NON-NLS-1$
								// this is a binary
								tp += "sys\\bin\\" + path.lastSegment(); //$NON-NLS-1$
							} else {
								// see if this is a resource
								final String dataZDir = "\\epoc32\\data\\z\\"; //$NON-NLS-1$
								int index = path.toOSString().toLowerCase().indexOf(dataZDir);
								if (index >= 0) {
									tp += path.toOSString().substring(index + dataZDir.length());
								} else {
									// fallback - just add filename
									tp += path.lastSegment();
								}
							}

							targetPath.setText(tp);
						}
					}
				}
			}
		});

		final Label targetLabel = new Label(composite, SWT.NONE);
		targetLabel.setText(Messages.getString("AddEditFileToTransferDialog.5")); //$NON-NLS-1$
		data = new GridData(GridData.FILL, GridData.CENTER, true, false);
		data.horizontalSpan = 2;
		targetLabel.setLayoutData(data);
		targetLabel.setToolTipText(Messages.getString("AddEditFileToTransferDialog.6")); //$NON-NLS-1$

		targetPath = new Text(composite, SWT.BORDER);
		targetPath.setText(fFile.getTargetPath());
		data = new GridData(GridData.FILL, GridData.CENTER, true, false);
		targetPath.setLayoutData(data);
		targetPath.setToolTipText(Messages.getString("AddEditFileToTransferDialog.6")); //$NON-NLS-1$
		targetPath.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				checkValues();
			}
		});

		applyDialogFont(composite);		

		return composite;
	}

	public void create() {
		super.create();
		checkValues();
	}

	protected void okPressed() {
		fFile.setHostPath(hostPath.getText());
		fFile.setTargetPath(targetPath.getText());

		super.okPressed();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#getInitialSize()
	 */
	protected Point getInitialSize() {
		Point size = super.getInitialSize();
		
		// set the initial size for the add dialog.  note that when editing it
		// automatically sizes it based on the length of the text in the edit boxes
		if (hostPath.getText().trim().length() == 0) {
			size.x *= 3;
		}

		return size;
	}

	protected void checkValues() {
		StatusInfo status= new StatusInfo();
		status.setOK();
		
		String hp = hostPath.getText().trim();
		if (hp.length() > 0) {
			if (!new File(hp).exists()) {
				status.setError(Messages.getString("AddEditFileToTransferDialog.7")); //$NON-NLS-1$
			}
		}
		else {
			status.setError(Messages.getString("AddEditFileToTransferDialog.8")); //$NON-NLS-1$
		}

		// if we already have an error then no need to check the rest of the stuff
		if (status.isOK()) {
			String tp = targetPath.getText().trim();
			if (tp.length() > 0) {
				if (tp.length() < 3) {
					status.setError(Messages.getString("AddEditFileToTransferDialog.9")); //$NON-NLS-1$					
				} else {
					char drive = tp.charAt(0);
					char colon = tp.charAt(1);
					if (!Character.isLetter(drive) || colon != ':') { 
						status.setError(Messages.getString("AddEditFileToTransferDialog.9")); //$NON-NLS-1$
					}
					
					// we really want the target file path, not just a directory.  but we do allow directories for
					// backwards compatibility, but only when they end in a path delimiter.  so if the target path
					// does not end in a path delimiter, and there is no file extension, warn them that we think
					// this is just a file without an extension and not a directory.
					IPath path = new Path(tp);
					if (!path.hasTrailingSeparator() && path.getFileExtension() == null) {
						status.setWarning(Messages.getString("AddEditFileToTransferDialog.12")); //$NON-NLS-1$
					}
				}
			}
			else {
				status.setError(Messages.getString("AddEditFileToTransferDialog.10")); //$NON-NLS-1$
			}
		}

		updateStatus(status);		
	}

}
