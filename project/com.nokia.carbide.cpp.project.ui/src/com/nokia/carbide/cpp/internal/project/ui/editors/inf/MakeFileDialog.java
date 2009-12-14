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
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakMakeReference;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakefileReference;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakefileReference.EMakeEngine;
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

public class MakeFileDialog extends StatusDialog implements IMakMakeFileDialog {

	private IMakefileReference makefileReference;
	private final BldInfEditorContext editorContext;
	private boolean isTest;

	private Text filePath;
	private Button browseButton;
	private Button tidyButton;
	private Button buildAsArmButton;
	private Button manualButton;
	private Button supportButton;
	private Label makefileTypeLabel;
	private Combo makefileType;
	private EpocEnginePathHelper pathHelper;
	private boolean isEdit;

	
	public MakeFileDialog(Shell parentShell, IMakefileReference makefileReference, BldInfEditorContext editorContext, boolean isTest) {
		super(parentShell);
		this.makefileReference = makefileReference;
		this.editorContext = editorContext;
		this.isTest = isTest;
		pathHelper = new EpocEnginePathHelper(editorContext.project);
		isEdit = (makefileReference != null);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		setHelpAvailable(true);
	}
	
	@Override
	protected void okPressed() {
		if (!isEdit) {
			makefileReference = editorContext.bldInfView.createMakefileReference();
		}
		
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

		makefileReference.setPath(pathHelper.convertPathToView(relativePath));
		makefileReference.setTidy(tidyButton.getSelection());
		if (buildAsArmButton != null) {
			makefileReference.setBuildAsArm(buildAsArmButton.getSelection());
		}
		if (manualButton != null) {
			makefileReference.setManual(manualButton.getSelection());
		}
		if (supportButton != null) {
			makefileReference.setSupport(supportButton.getSelection());
		}

		switch(makefileType.getSelectionIndex()) {
		case 0:
			makefileReference.setMakeEngine(EMakeEngine.MAKEFILE);
			break;
		case 1:
			makefileReference.setMakeEngine(EMakeEngine.NMAKEFILE);
			break;
		case 2:
			makefileReference.setMakeEngine(EMakeEngine.GNUMAKEFILE);
			break;
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
		filePathLabel.setText(Messages.MakeFileDialog_FilePath);
		data = new GridData();
		data.horizontalSpan = 1;
		filePathLabel.setLayoutData(data);
		filePathLabel.setToolTipText(Messages.MakeFileDialog_FilePathToolTip);

		filePath = new Text(container, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		filePath.setLayoutData(data);
		filePath.setToolTipText(Messages.MakeFileDialog_FilePathToolTip);
		filePath.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				checkValues();
			}
		});

		if (makefileReference != null) {
			IPath fp = makefileReference.getPath();
			if (fp != null) {
				IPath fsPath = pathHelper.convertToFilesystem(fp);
				if (fsPath != null) {
					filePath.setText(fsPath.toOSString());
				}
			}
		}
		
		browseButton = new Button(container, SWT.PUSH);
		browseButton.setText(Messages.MakeFileDialog_Browse);
		browseButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent evt) {
				FileDialog dialog = new FileDialog(getShell(), SWT.NONE);

				dialog.setText(Messages.MakeFileDialog_FileDialogTitle);
				dialog.setFilterExtensions(new String[] {"*.mk", "*"}); //$NON-NLS-1$, //$NON-NLS-2$
				dialog.setFilterNames(new String[] {Messages.MakeFileDialog_MakeFiles, Messages.MakeFileDialog_AllFiles});

				// set the initial directory
				BrowseDialogUtils.initializeFrom(dialog, filePath, editorContext.project.getLocation());

				String result = dialog.open();
				if (result != null) {
					filePath.setText(result);
				}
			}
		});

		tidyButton = new Button(container, SWT.CHECK | SWT.LEFT);
		tidyButton.setText(Messages.MakeFileDialog_Tidy);
		tidyButton.setToolTipText(Messages.MakeFileDialog_TidyToolTip);
		data = new GridData();
		data.horizontalSpan = 3;
		tidyButton.setLayoutData(data);
		tidyButton.setSelection(makefileReference != null ? makefileReference.isTidy() : false);

		if (isTest) {
			manualButton = new Button(container, SWT.CHECK | SWT.LEFT);
			manualButton.setText(Messages.MakeFileDialog_Manual);
			manualButton.setToolTipText(Messages.MakeFileDialog_ManualTYoolTip);
			data = new GridData();
			data.horizontalSpan = 3;
			manualButton.setLayoutData(data);
			manualButton.setSelection(makefileReference != null ? makefileReference.isManual() : false);

			supportButton = new Button(container, SWT.CHECK | SWT.LEFT);
			supportButton.setText(Messages.MakeFileDialog_Support);
			supportButton.setToolTipText(Messages.MakeFileDialog_SupportToolTip);
			data = new GridData();
			data.horizontalSpan = 3;
			supportButton.setLayoutData(data);
			supportButton.setSelection(makefileReference != null ? makefileReference.isSupport() : false);
		} else {
			buildAsArmButton = new Button(container, SWT.CHECK | SWT.LEFT);
			buildAsArmButton.setText(Messages.MakeFileDialog_BuildAsArm);
			buildAsArmButton.setToolTipText(Messages.MakeFileDialog_BuildAsArmToolTip);
			data = new GridData();
			data.horizontalSpan = 3;
			buildAsArmButton.setLayoutData(data);
			buildAsArmButton.setSelection(makefileReference != null ? makefileReference.isBuildAsArm() : false);
		}

		makefileTypeLabel = new Label(container, SWT.NONE);
		makefileTypeLabel.setText(Messages.MakeFileDialog_MakefileType);
		data = new GridData();
		data.horizontalSpan = 1;
		makefileTypeLabel.setLayoutData(data);
		makefileTypeLabel.setToolTipText(Messages.MakeFileDialog_MakefileTypeToolTip);

		makefileType = new Combo(container, SWT.READ_ONLY);
		makefileType.add(Messages.MakeFileDialog_MakeFile_text);
		makefileType.add(Messages.MakeFileDialog_NMakeFile_text);
		makefileType.add(Messages.MakeFileDialog_GNUMakeFile_text);
		data = new GridData();
		data.horizontalSpan = 2;
		makefileType.setLayoutData(data);
		makefileType.setToolTipText(Messages.MakeFileDialog_MakefileTypeToolTip);
		makefileType.select(2);	// GNU makefile is default
		
		if (makefileReference != null) {
			EMakeEngine makeEngine = makefileReference.getMakeEngine();
			if (makeEngine.equals(EMakeEngine.MAKEFILE)) {
				makefileType.select(0); 
			} else if (makeEngine.equals(EMakeEngine.NMAKEFILE)) {
				makefileType.select(1);
			} else if (makeEngine.equals(EMakeEngine.GNUMAKEFILE)) {
				makefileType.select(2);
			}
		}

		return container;
	}

	public void create() {
		super.create();
		checkValues();
	}
	
	public IMakMakeReference getMakeMakeFile() {
		return makefileReference;
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(600, isTest ? 230 : 210);
	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(isEdit ? Messages.MakeFileDialog_EditMakeEntry : Messages.MakeFileDialog_AddMakeEntry);
		WorkbenchUtils.setHelpContextId(newShell, HelpContexts.MAKE_DIALOG);
	}

	protected void checkValues() {
		StatusInfo status = new StatusInfo();
		status.setOK();
		
		String sp = filePath.getText().trim();
		if (sp.length() > 0) {
			if (!new File(sp).exists()) {
				status.setError(Messages.MakeFileDialog_MakeDoesNotExistError);
			} else if (!editorContext.project.getLocation().isPrefixOf(new Path(sp))) {
				// make sure it's under the project root.  we can't support adding of files outside of the project since all
				// of the engine and path helpers is project-relative.
				status.setError(Messages.MakeFileDialog_MakeFileOutsideOfProject);
			}
		}
		else {
			status.setError(Messages.MakeFileDialog_NoMakeSpecifiedError);
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

	public Combo getMakeFileTypeCombo() {
		return makefileType;
	}

}
