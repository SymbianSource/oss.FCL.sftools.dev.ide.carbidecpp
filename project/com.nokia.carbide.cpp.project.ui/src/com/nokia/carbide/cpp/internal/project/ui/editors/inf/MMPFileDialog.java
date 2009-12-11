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
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMMPReference;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakMakeReference;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.StatusInfo;
import com.nokia.cpp.internal.api.utils.ui.BrowseDialogUtils;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.io.File;

public class MMPFileDialog extends StatusDialog implements IMakMakeFileDialog {

	private IMMPReference mmpFileReference;
	private final BldInfEditorContext editorContext;
	private boolean isTest;

	private Text filePath;
	private Button browseButton;
	private Button tidyButton;
	private Button buildAsArmButton;
	private Button manualButton;
	private Button supportButton;
	private EpocEnginePathHelper pathHelper;
	private boolean isEdit;

	
	public MMPFileDialog(Shell parentShell, IMMPReference mmpFileReference, BldInfEditorContext editorContext, boolean isTest) {
		super(parentShell);
		this.mmpFileReference = editorContext.bldInfView.createMMPReference();
		if (mmpFileReference != null) {
			// copy values from reference being edited
			this.mmpFileReference.setPath(mmpFileReference.getPath());
			this.mmpFileReference.setBuildAsArm(mmpFileReference.isBuildAsArm());
			this.mmpFileReference.setManual(mmpFileReference.isManual());
			this.mmpFileReference.setSupport(mmpFileReference.isSupport());
			this.mmpFileReference.setTidy(mmpFileReference.isTidy());
		}

		this.editorContext = editorContext;
		this.isTest = isTest;
		pathHelper = new EpocEnginePathHelper(editorContext.project);
		isEdit = (mmpFileReference != null);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		setHelpAvailable(true);
	}
	
	@Override
	protected void okPressed() {
		// get the project relative path of the new file to pass to pathHelper.convertPathToView
		IPath projectLocation = editorContext.project.getLocation();
		IPath newFilePath = new Path(filePath.getText());

		int segments = projectLocation.matchingFirstSegments(newFilePath);
        IPath prefix = projectLocation.removeFirstSegments(segments);
        IPath suffix = newFilePath.removeFirstSegments(segments);
        IPath relativePath = new Path(""); //$NON-NLS-1$
        for (int i = 0; i < prefix.segmentCount(); ++i) {
            relativePath = relativePath.append(".." + IPath.SEPARATOR); //$NON-NLS-1$
        }
        relativePath = relativePath.append(suffix);
		
		mmpFileReference.setPath(pathHelper.convertPathToView(relativePath));
		mmpFileReference.setTidy(tidyButton.getSelection());
		if (buildAsArmButton != null) {
			mmpFileReference.setBuildAsArm(buildAsArmButton.getSelection());
		}
		if (manualButton != null) {
			mmpFileReference.setManual(manualButton.getSelection());
		}
		if (supportButton != null) {
			mmpFileReference.setSupport(supportButton.getSelection());
		}
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
		layout.numColumns = 3;
		layout.marginTop = 0;
		layout.marginBottom = 0;
		layout.marginWidth = 10;
		container.setLayout(layout);
		
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 1;
		container.setLayoutData(data);

		final Label filePathLabel = new Label(container, SWT.NONE);
		filePathLabel.setText(Messages.MMPFileDialog_FilePath);
		data = new GridData();
		data.horizontalSpan = 1;
		filePathLabel.setLayoutData(data);
		filePathLabel.setToolTipText(Messages.MMPFileDialog_FilePathToolTip);

		filePath = new Text(container, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		filePath.setLayoutData(data);
		filePath.setToolTipText(Messages.MMPFileDialog_FilePathToolTip);
		filePath.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				checkValues();
			}
		});
		
		browseButton = new Button(container, SWT.PUSH);
		browseButton.setText(Messages.MMPFileDialog_Browse);
		browseButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent evt) {
				FileDialog dialog = new FileDialog(getShell(), SWT.NONE);

				dialog.setText(Messages.MMPFileDialog_FileDialogTitle);
				dialog.setFilterExtensions(new String[] {"*.mmp", "*"}); //$NON-NLS-1$, //$NON-NLS-2$
				dialog.setFilterNames(new String[] {Messages.MMPFileDialog_MMPFiles, Messages.MMPFileDialog_AllFiles});
				
				// set the initial directory
				BrowseDialogUtils.initializeFrom(dialog, filePath, editorContext.project.getLocation());
				
				String result = dialog.open();
				if (result != null) {
					filePath.setText(result);
				}
			}
		});

		tidyButton = new Button(container, SWT.CHECK | SWT.LEFT);
		tidyButton.setText(Messages.MMPFileDialog_Tidy);
		tidyButton.setToolTipText(Messages.MMPFileDialog_TidyToolTip);
		data = new GridData();
		data.horizontalSpan = 3;
		tidyButton.setLayoutData(data);

		if (isTest) {
			manualButton = new Button(container, SWT.CHECK | SWT.LEFT);
			manualButton.setText(Messages.MMPFileDialog_Manual);
			manualButton.setToolTipText(Messages.MMPFileDialog_ManualToolTip);
			data = new GridData();
			data.horizontalSpan = 3;
			manualButton.setLayoutData(data);

			supportButton = new Button(container, SWT.CHECK | SWT.LEFT);
			supportButton.setText(Messages.MMPFileDialog_Support);
			supportButton.setToolTipText(Messages.MMPFileDialog_SupportToolTip);
			data = new GridData();
			data.horizontalSpan = 3;
			supportButton.setLayoutData(data);
		} else {
			buildAsArmButton = new Button(container, SWT.CHECK | SWT.LEFT);
			buildAsArmButton.setText(Messages.MMPFileDialog_BuildAsArm);
			buildAsArmButton.setToolTipText(Messages.MMPFileDialog_BuildAsArmToolTip);
			data = new GridData();
			data.horizontalSpan = 3;
			buildAsArmButton.setLayoutData(data);
		}

		// initialize values
		IPath fp = mmpFileReference.getPath();
		if (fp != null) {
			IPath fsPath = pathHelper.convertToFilesystem(fp);
			if (fsPath != null) {
				filePath.setText(fsPath.toOSString());
			}
		}

		tidyButton.setSelection(mmpFileReference.isTidy());

		if (isTest) {
			manualButton.setSelection(mmpFileReference.isManual());
			supportButton.setSelection(mmpFileReference.isSupport());
		} else {
			buildAsArmButton.setSelection(mmpFileReference.isBuildAsArm());
		}

		return container;
	}

	public void create() {
		super.create();
		checkValues();
	}
	
	public IMakMakeReference getMakeMakeFile() {
		return mmpFileReference;
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(600, isTest ? 200 : 180);
	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(isEdit ? Messages.MMPFileDialog_EditMMPEntry : Messages.MMPFileDialog_AddMMPEntry);
		WorkbenchUtils.setHelpContextId(newShell, HelpContexts.MMP_DIALOG);
	}

	protected void checkValues() {
		StatusInfo status = new StatusInfo();
		status.setOK();
		
		String sp = filePath.getText().trim();
		if (sp.length() > 0) {
			if (!new File(sp).exists()) {
				status.setError(Messages.MMPFileDialog_MMPFileDoesNotExistError);
			} else if (!editorContext.project.getLocation().isPrefixOf(new Path(sp))) {
				// make sure it's under the project root.  we can't support adding of files outside of the project since all
				// of the engine and path helpers is project-relative.
				status.setError(Messages.MMPFileDialog_MMPFileOutsideOfProject);
			}
		}
		else {
			status.setError(Messages.MMPFileDialog_MMPFileNotSpecifiedError);
		}

		updateStatus(status);		
	}

	public int show() {
		return open();
	}

	public Text getFilePathText() {
		return filePath;
	}

	public Button getBrowseButton() {
		return browseButton;
	}

	public Button getTidyButton() {
		return tidyButton;
	}

	public Button getBuildAsARMButton() {
		return buildAsArmButton;
	}

	public Button getManualButton() {
		return manualButton;
	}

	public Button getSupportButton() {
		return supportButton;
	}

}
