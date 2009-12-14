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
package com.nokia.carbide.cpp.internal.project.ui.editors.inf;

import com.nokia.carbide.cdt.builder.EpocEnginePathHelper;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExport;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.StatusInfo;
import com.nokia.cpp.internal.api.utils.ui.BrowseDialogUtils;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.io.File;

public class ExportFileDialog extends StatusDialog {

	private final String dialogTitle;
	private final IExport export;
	private final IProject project;

	private Text sourcePath;
	private Button browseButton;
	private Text destinationPath;
	private Button isArchiveButton;
	private EpocEnginePathHelper pathHelper;
	private static final String EPOC32_INCLUDE_DIR = "/epoc32/include/"; //$NON-NLS-1$

	
	public ExportFileDialog(Shell parentShell, String title, IExport export, IProject project) {
		super(parentShell);
		this.dialogTitle = title;
		this.export = export;
		this.project = project;
		pathHelper = new EpocEnginePathHelper(project);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		setHelpAvailable(true);
	}
	
	@Override
	protected void okPressed() {
		IPath sp = new Path(sourcePath.getText().trim());
		export.setSourcePath(pathHelper.convertPathToView(sp));
		
		// We need to make sure the destination path is not empty.
		// the default location is /epoc32/include/ so we set it to this if empty. 
		// bug 4935: it can be relative now, which indicates a project-relative destination.
		String destPath = destinationPath.getText().trim();
		if (destPath.length() < 1) {
			export.setTargetPath(null);
		} else {
			IPath dp = new Path(destPath);
			if (dp.hasTrailingSeparator()) {
				dp = dp.append(sp.lastSegment());
			}
			export.setTargetPath(pathHelper.convertPathToView(dp));
		}
		export.setZipped(isArchiveButton.getSelection());
		super.okPressed();
	}
	
	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginTop = 0;
		layout.marginBottom = 0;
		layout.marginWidth = 10;
		container.setLayout(layout);
		
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 1;
		container.setLayoutData(data);

		final Label sourcePathLabel = new Label(container, SWT.NONE);
		sourcePathLabel.setText(Messages.ExportFileDialog_SouecePath);
		data = new GridData();
		data.horizontalSpan = 2;
		sourcePathLabel.setLayoutData(data);
		sourcePathLabel.setToolTipText(Messages.ExportFileDialog_SourcePathToolTip);

		sourcePath = new Text(container, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		sourcePath.setLayoutData(data);
		sourcePath.setToolTipText(Messages.ExportFileDialog_SourcePathToolTip);
		sourcePath.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				checkValues();
			}
		});

		IPath sp = export.getSourcePath();
		if (sp != null) {
			IPath fsPath = pathHelper.convertToFilesystem(sp);
			if (fsPath != null) {
				sourcePath.setText(fsPath.toOSString());
			}
		}
		
		browseButton = new Button(container, SWT.PUSH);
		browseButton.setText(Messages.ExportFileDialog_Browse);
		browseButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent evt) {
				FileDialog dialog = new FileDialog(getShell(), SWT.NONE);

				dialog.setText(Messages.ExportFileDialog_FileDialogTitle);
				dialog.setFilterExtensions(new String[] {"*"}); //$NON-NLS-1$
				dialog.setFilterNames(new String[] {Messages.ExportFileDialog_AllFiles});
				
				// set the initial directory
				String currentFile = sourcePath.getText().trim();
				if (currentFile.length() == 0) {
					BrowseDialogUtils.initializeFrom(dialog, project.getLocation());
				} else {
					BrowseDialogUtils.initializeFrom(dialog, currentFile);
				}

				String result = dialog.open();
				if (result != null && new File(result).exists()) {
					sourcePath.setText(result);
				}
			}
		});

		final Label destinationPathLabel = new Label(container, SWT.NONE);
		destinationPathLabel.setText(Messages.ExportFileDialog_DestinationPath);
		data = new GridData();
		data.horizontalSpan = 2;
		destinationPathLabel.setLayoutData(data);
		destinationPathLabel.setToolTipText(Messages.ExportFileDialog_DestinationPathToolTip);

		destinationPath = new Text(container, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		destinationPath.setLayoutData(data);
		destinationPath.setToolTipText(Messages.ExportFileDialog_DestinationPathToolTip);
		destinationPath.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				checkValues();
			}
		});

		IPath dp = export.getTargetPath();
		if (dp != null) {
			destinationPath.setText(dp.toString());
		} else {
			destinationPath.setText(EPOC32_INCLUDE_DIR);
		}

		isArchiveButton = new Button(container, SWT.CHECK | SWT.LEFT);
		isArchiveButton.setText(Messages.ExportFileDialog_ZExportIsZip);
		isArchiveButton.setToolTipText(Messages.ExportFileDialog_ZExportIsZipToolTip);
		data = new GridData();
		data.horizontalSpan = 2;
		isArchiveButton.setLayoutData(data);
		isArchiveButton.setSelection(export.isZipped());

		return container;
	}

	public void create() {
		super.create();
		checkValues();
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(600, 225);
	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(dialogTitle);
		WorkbenchUtils.setHelpContextId(newShell, HelpContexts.EXPORTS_DIALOG);
	}

	protected void checkValues() {
		StatusInfo status = new StatusInfo();
		status.setOK();
		
		String sp = sourcePath.getText().trim();
		if (sp.length() > 0) {
			if (!new File(sp).exists()) {
				status.setError(Messages.ExportFileDialog_FileDoesNotExistError);
			}
		}
		else {
			status.setError(Messages.ExportFileDialog_FileNotSpecifiedError);
		}

		// if we already have an error then no need to check the rest of the stuff
		if (status.isOK() && destinationPath != null) {
			String dp = destinationPath.getText().trim();
			if (dp.length() > 0) {
				// the symbian docs say this can be empty, relative, or absolute.  I'm not sure there's
				// anything here for us to check.
			}
		}

		updateStatus(status);
		
	}

	public Text getSourcePathText() {
		return sourcePath;
	}

	public Button getBrowseButton() {
		return browseButton;
	}

	public Text getDestinationPath() {
		return destinationPath;
	}

	public Button getIsArchiveButton() {
		return isArchiveButton;
	}

}
