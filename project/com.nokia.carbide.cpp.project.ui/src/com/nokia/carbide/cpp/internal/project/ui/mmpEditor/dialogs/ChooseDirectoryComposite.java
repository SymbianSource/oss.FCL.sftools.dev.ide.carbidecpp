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

import com.nokia.carbide.cdt.builder.EMMPPathContext;
import com.nokia.carbide.cdt.builder.MMPViewPathHelper;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.cpp.internal.api.utils.ui.BrowseDialogUtils;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;
import java.util.List;

public class ChooseDirectoryComposite extends Composite {

	private final IProject project;
	private final EMMPPathContext pathContext;
	private final MMPViewPathHelper pathHelper;
	private final IPath initialPath;
	private final ICarbideBuildConfiguration buildConfiguration;
	
	private ComboViewer pathViewer;
	private Button browseButton;
	@SuppressWarnings("unused") //$NON-NLS-1$
	private boolean modifyingPathViewer;
	private IPath resultPath;
	
	static final String SETTINGS_PATH = ".settings"; //$NON-NLS-1$

	public ChooseDirectoryComposite(Composite parent, int style, 
			IProject project, IPath initialPath,
			EMMPPathContext pathContext, MMPViewPathHelper pathHelper,
			ICarbideBuildConfiguration buildConfiguration) {
		super(parent, style);
		this.project = project;
		this.pathContext = pathContext;
		this.pathHelper = pathHelper;
		this.initialPath = initialPath;
		this.buildConfiguration = buildConfiguration;
		createContents();
	}

	public void capturePath() {
		if (resultPath == null) {
			resultPath = currentPath();
		}
	}
	
	/**
	 * Result is returned as an MMP path
	 * @return
	 */
	public IPath getResult() {
		return resultPath;
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	private void createContents() {
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		setLayout(gridLayout);
		
		Label chooseAProjectLabel;
		chooseAProjectLabel = new Label(this, SWT.WRAP);
		chooseAProjectLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		chooseAProjectLabel.setText(Messages.getString("IncludeDirectoryDialog.promptLabel")); //$NON-NLS-1$
		
		Label fillerLable1;
		fillerLable1 = new Label(this, SWT.WRAP);
		fillerLable1.setLayoutData(new GridData(SWT.DEFAULT));
		
		Label includeDirectoryLabel = new Label(this, SWT.NONE);
		includeDirectoryLabel.setText(Messages.getString("IncludeDirectoryDialog.includeDirectoryLabel")); //$NON-NLS-1$

		pathViewer = new ComboViewer(this, SWT.NONE);
		pathViewer.setContentProvider(new ArrayContentProvider());
		pathViewer.setLabelProvider(new LabelProvider());
		pathViewer.setSorter(new ViewerSorter());
		Combo combo;
		combo = pathViewer.getCombo();
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		browseButton = new Button(this, SWT.NONE);
		browseButton.setText(Messages.getString("IncludeDirectoryDialog.browseButton")); //$NON-NLS-1$
		browseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				doBrowse();
			}
		});
		
		populate();
	}
	
	private void populate() {
		List<IPath> input = getProjectDirectories();
		if (initialPath != null && !input.contains(initialPath)) {
			input.add(0, initialPath);
		}
		pathViewer.setInput(input);
		if (initialPath != null) {
			IStructuredSelection ss = new StructuredSelection(initialPath);
			pathViewer.setSelection(ss);
		}
	}
	
	@SuppressWarnings("unchecked") //$NON-NLS-1$
	public void setViewerPath(IPath path) {
		modifyingPathViewer = true;
		try {
			List<IPath> paths = (List<IPath>) pathViewer.getInput();
			if (paths.contains(path)) {
				setViewerSelection(new StructuredSelection(path));
			} else {
				setViewerSelection(new StructuredSelection());
				pathViewer.getCombo().setText(path.toString());
			}
		} finally {
			modifyingPathViewer = false;
		}
	}
	
	private void setViewerSelection(ISelection selection) {
		modifyingPathViewer = true;
		try {
			pathViewer.setSelection(selection);
		} finally {
			modifyingPathViewer = false;
		}
	}

	private void doBrowse() {
		DirectoryDialog dialog = new DirectoryDialog(getShell());
		dialog.setMessage(Messages.getString("IncludeDirectoryDialog.browseForDirectoryPrompt")); //$NON-NLS-1$
		
		IPath currentPath = new Path(pathViewer.getCombo().getText());
		if (!currentPath.isEmpty()) {
			currentPath = pathHelper.convertMMPToFilesystem(pathContext, currentPath);
		} else if (pathContext == EMMPPathContext.SYSTEMINCLUDE 
				|| pathContext == EMMPPathContext.SYSTEMRESOURCE
				|| pathContext == EMMPPathContext.TARGETPATH) {
			currentPath = new Path(buildConfiguration.getSDK().getEPOCROOT());
		}
		BrowseDialogUtils.initializeFrom(dialog, currentPath);
		
		String fullPath = dialog.open();
		if (fullPath != null) {
			setViewerPath(new Path(fullPath));
		}
	}
	
	private List<IPath> getProjectDirectories() {
		final List<IPath> result = new ArrayList<IPath>();
		if (project != null) {
			try {
				project.accept(new IResourceVisitor() {
					public boolean visit(IResource resource) throws CoreException {
						if (resource instanceof IFolder) {
							if (!SETTINGS_PATH.equalsIgnoreCase(resource.getProjectRelativePath().toString())) {
								result.add(resource.getProjectRelativePath());
							}
						}
						return true;
					}
				});
			} catch (CoreException x) {
				ProjectUIPlugin.log(x);
			}
		}
		return result;
	}
	
	public IPath currentPath() {
		IPath result = null;
		IStructuredSelection ss = (IStructuredSelection) pathViewer.getSelection();
		Object element = ss.getFirstElement();
		if (element instanceof IPath) {
			result = (IPath) element;
		} else {
			String text = pathViewer.getCombo().getText();
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
						Messages.getString("ChooseDirectoryComposite.dirPathValidationErr"), //$NON-NLS-1$
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
		pathViewer.getControl().setFocus();
	}

	public Combo getPathViewerCombo() {
		return pathViewer.getCombo();
	}

	public Button getBrowseButton() {
		return browseButton;
	}

}
