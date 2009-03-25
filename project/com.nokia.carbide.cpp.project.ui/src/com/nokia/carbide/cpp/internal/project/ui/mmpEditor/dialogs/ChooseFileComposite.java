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
package com.nokia.carbide.cpp.internal.project.ui.mmpEditor.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;

import com.nokia.carbide.cdt.builder.EMMPPathContext;
import com.nokia.carbide.cdt.builder.MMPViewPathHelper;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;

/**
 * Composite allowing the user to add or edit an MMP path
 * dialog via any of three methods: 
 * - choosing an eligible file from the project in a dropdown list
 * - browsing the file system for a file
 * - typing in a path
 */
public class ChooseFileComposite extends Composite {

	private final IPath initialPath;
	private final EMMPPathContext pathContext;
	private final MMPViewPathHelper pathHelper;
	private final ICarbideBuildConfiguration buildConfiguration;
	private final String[] fileFilters;
	private final List<IPath> eligibleProjectFiles;
	private final String fileViewerLabel;
	
	private ComboViewer filePathViewer;

	@SuppressWarnings("unused") //$NON-NLS-1$
	private boolean modifyingFilePathViewer;
	private IPath resultPath;
		
	/**
	 * Create the dialog
	 * @param parentShell
	 * @param initialPath starting value, if editing an existing value. Can be null
	 * @param pathContext used for converting the result path to an MMP path
	 * @param pathHelper used for convert the result path to an MMP path
	 * @param eligibleProjectFiles used to populate the dropdown list
	 * @param fileFilters used for filtering files when browsing the file system
	 */
	public ChooseFileComposite(Composite parent, int style,
			IPath initialPath, EMMPPathContext pathContext, MMPViewPathHelper pathHelper,
			ICarbideBuildConfiguration buildConfiguration,
			List<IPath> eligibleProjectFiles, String fileFilters[],
			String fileViewerLabel) {
		super(parent, style);
		this.initialPath = initialPath;
		this.pathContext = pathContext;
		this.pathHelper = pathHelper;
		this.buildConfiguration = buildConfiguration;
		this.eligibleProjectFiles = eligibleProjectFiles;
		this.fileFilters = fileFilters;	
		this.fileViewerLabel = fileViewerLabel;
		
		createContents();
		populate();
	}
	
	/**
	 * Returns the user entered path if OK was pressed.
	 */
	public IPath getResultPath() {
		return resultPath;
	}

	public void capturePath() {
		if (resultPath == null) {
			resultPath = currentPath();
		}
	}

	private void createContents() {
		final GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 10;
		gridLayout.numColumns = 3;
		setLayout(gridLayout);

		Label projectFilesLabel;

		final Label chooseAProjectLabel = new Label(this, SWT.WRAP);
		final GridData gridData_3 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1);
		gridData_3.widthHint = 471;
		chooseAProjectLabel.setLayoutData(gridData_3);
		chooseAProjectLabel.setText(Messages.getString("ChooseFileComposite.prompt")); //$NON-NLS-1$
		projectFilesLabel = new Label(this, SWT.NONE);
		projectFilesLabel.setLayoutData(new GridData(102, SWT.DEFAULT));
		projectFilesLabel.setText(fileViewerLabel);

		filePathViewer = new ComboViewer(this, SWT.NONE);
		filePathViewer.setContentProvider(new ArrayContentProvider());
		filePathViewer.setLabelProvider(new LabelProvider());
		filePathViewer.setSorter(new ViewerSorter());
		Combo fileCombo = filePathViewer.getCombo();
		fileCombo.setLayoutData(new GridData(180, SWT.DEFAULT));
		filePathViewer.setInput(new Object());

		final Button browseButton = new Button(this, SWT.NONE);
		final GridData gridData_5 = new GridData();
		gridData_5.horizontalIndent = 10;
		browseButton.setLayoutData(gridData_5);
		browseButton.setText(Messages.getString("ResourceBlockDialog.browseButton")); //$NON-NLS-1$
		browseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				doBrowse();
			}
		});
	}
	
	public IPath currentPath() {
		IPath result = null;
		IStructuredSelection ss = (IStructuredSelection) filePathViewer.getSelection();
		Object element = ss.getFirstElement();
		if (element instanceof IPath) {
			result = (IPath) element;
		} else {
			String text = filePathViewer.getCombo().getText();
			if (text.length() > 0) {
				result = new Path(text);
			}
		}
		return result;
	}
	
	public IStatus validate() {
		IStatus result = Status.OK_STATUS;
		IPath path = currentPath();
		if (path == null) {
			result = new Status(IStatus.ERROR, ProjectUIPlugin.PLUGIN_ID, 0,
					Messages.getString("ChooseFileComposite.validationErr"), //$NON-NLS-1$
					null);
			setDefaultFocus();
		} else {
			path = Utilities.validateandConfirmMMPPath(path, buildConfiguration, 
					pathContext, pathHelper, getShell());
			if (path != null) {
				resultPath = path;
			} else {
				// we've displayed a message, just keep the dialog open
				result = Status.CANCEL_STATUS;
			}
		}
		return result;
	}
	
	public void setDefaultFocus() {
		filePathViewer.getControl().setFocus();
	}
		
	public void setFileViewerSelection(StructuredSelection selection) {
		modifyingFilePathViewer = true;
		try {
			filePathViewer.setSelection(selection);
		} finally {
			modifyingFilePathViewer = false;
		}		
	}
	
	public void setFileViewerPath(IPath path) {
		modifyingFilePathViewer = true;
		try {
			if (eligibleProjectFiles != null && eligibleProjectFiles.contains(path)) {
				setFileViewerSelection(new StructuredSelection(path));
			} else {
				setFileViewerSelection(new StructuredSelection());
				filePathViewer.getCombo().setText(path.toString());
			}
		} finally {
			modifyingFilePathViewer = false;
		}
	}

	private void populate() {
		List<IPath> input = new ArrayList<IPath>();
		if (eligibleProjectFiles != null) {
			input.addAll(eligibleProjectFiles);
		}
		if (initialPath != null && initialPath.segmentCount() > 0 && !input.contains(initialPath)) {
			input.add(initialPath);
		}
		filePathViewer.setInput(input);
		if (initialPath != null) {
			IStructuredSelection ss = new StructuredSelection(initialPath);
			filePathViewer.setSelection(ss);
		}
	}
	
	private void doBrowse() {
		FileDialog dialog = new FileDialog(getShell());
		// start the browse dialog at the directory of the initial path, or at the project's root directory
		String pathString = initialPath != null ? pathHelper.convertMMPToFilesystem(pathContext, initialPath).toOSString() :
			buildConfiguration.getCarbideProject().getProject().getLocation().addTrailingSeparator().append("filename").toOSString(); //$NON-NLS-1$
		dialog.setFileName(pathString);
		dialog.setFilterExtensions(fileFilters);
		String fullPath = dialog.open();
		if (fullPath != null) {
			setFileViewerPath(new Path(fullPath));
		}
	}

	public void addFocusListener(FocusListener listener) {
		filePathViewer.getCombo().addFocusListener(listener);
	}

	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		filePathViewer.addSelectionChangedListener(listener);
	}
}
