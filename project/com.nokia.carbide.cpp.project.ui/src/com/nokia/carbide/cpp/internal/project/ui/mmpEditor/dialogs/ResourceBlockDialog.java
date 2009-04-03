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
import com.nokia.carbide.cdt.builder.InvalidDriveInMMPPathException;
import com.nokia.carbide.cdt.builder.MMPViewPathHelper;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.EGeneratedHeaderFlags;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPResource;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.HelpContexts;
import com.nokia.sdt.utils.ProjectFileResourceProxyVisitor;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.osgi.framework.Version;

import java.util.ArrayList;
import java.util.List;

public class ResourceBlockDialog extends ValidatingDialog {

	private final IProject project;
	private final IMMPResource resourceBlock;
	private final ICarbideBuildConfiguration buildConfiguration;
	private final MMPViewPathHelper pathHelper;
	private final String secureID;
	private final boolean supportHeaderOnly;
	
	private ChooseFileComposite chooseFileComposite;
	private Button generateHeaderCheckbox;
	private ComboViewer targetPathViewer;
	private Text uid3Text;
	private Text uid2Text;
	private Text targetFileText;
	private Button onlyGenerateHeaderButton;
	private Button generateHeaderButton;
	private Button dontGenerateHeaderButton;
	private Composite headerRadiosComposite;
	
	private final IPath defaultPath = new Path(""); //$NON-NLS-1$
	
	/**
	 * Create the dialog
	 * @param parentShell
	 * @param resourceBlock the resource info to be edited.
	 */
	public ResourceBlockDialog(Shell parentShell, IProject project, 
			IMMPResource resourceBlock,
			MMPViewPathHelper pathHelper, 
			ICarbideBuildConfiguration buildConfiguration,
			String secureID) {
		super(parentShell);
		this.project = project;
		this.resourceBlock = resourceBlock;
		this.pathHelper = pathHelper;
		this.buildConfiguration = buildConfiguration;
		this.secureID = secureID;
				
		// we'll enable UI for the headerOnly option if:
		// a) we're on Symbian 9.2 or later, or
		// b) it's already set in the input data
		Version version = buildConfiguration.getSDK().getOSVersion();
		if ((version.getMajor() == 9 && version.getMinor() >= 2) ||
			version.getMajor() > 9 ||
			resourceBlock.getHeaderFlags() == EGeneratedHeaderFlags.HeaderOnly) {
			supportHeaderOnly = true;
		} else {
			supportHeaderOnly = false;
		}
		setHelpAvailable(true);
	}
	
	public IStatus validate() {
		IStatus result = chooseFileComposite.validate();
		if (result.isOK()) {
			String targetPath = targetPathViewer.getCombo().getText();
			if (targetPath.length() > 0) {
				IPath path = Utilities.validateandConfirmMMPPath(new Path(targetPath),
						buildConfiguration, EMMPPathContext.TARGETPATH, pathHelper, getShell());
				if (path != null) {
					targetPathViewer.getCombo().setText(path.toString());
				} else {
					result = Status.CANCEL_STATUS;
				}
			} 
		}
		if (result.isOK() && uid2Text.getText().length() > 0) {
			if (!Utilities.validateUID(uid2Text.getText())) {
				result = makeStatus(IStatus.ERROR, Messages.getString("ResourceBlockDialog.uid2ValidationErr")); //$NON-NLS-1$
				uid2Text.setFocus();
			}
		}
		if (result.isOK() && uid3Text.getText().length() > 0) {
			if (!Utilities.validateUID(uid3Text.getText())) {
				result = makeStatus(IStatus.ERROR, Messages.getString("ResourceBlockDialog.uid3ValidationErr")); //$NON-NLS-1$
				uid3Text.setFocus();
			}
		}
		return result;
	}
	
	class TargetPathLabelProvider extends LabelProvider {

		@Override
		public String getText(Object element) {
			String result;
			if (defaultPath.equals(element)) {
				result = Messages.getString("ResourceBlockDialog.resourceBlockDefaultTargetDirectory"); //$NON-NLS-1$
			} else {
			 result = super.getText(element);
			}
			return result;
		}
		
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 10;
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);

		Label uid2Label;

		Label uid3Label;

		final Group sourcePathGroup = new Group(container, SWT.NONE);
		sourcePathGroup.setLayout(new FillLayout());
		sourcePathGroup.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		sourcePathGroup.setText(Messages.getString("ResourceBlockDialog.rssGroupLabel")); //$NON-NLS-1$

		chooseFileComposite = 
			new ChooseFileComposite(sourcePathGroup, SWT.NONE, 
					resourceBlock.getSource(), EMMPPathContext.START_RESOURCE, 
					pathHelper, buildConfiguration, getRSSFiles(), new String[] {"*.rss"},  //$NON-NLS-1$
					Messages.getString("ResourceBlockDialog.rssFilePathLabel")); //$NON-NLS-1$

		final Label targetFileNameLabel = new Label(container, SWT.NONE);
		targetFileNameLabel.setLayoutData(new GridData(110, SWT.DEFAULT));
		targetFileNameLabel.setText(Messages.getString("ResourceBlockDialog.targetNameLabel")); //$NON-NLS-1$

		targetFileText = new Text(container, SWT.BORDER);
		final GridData gridData = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		gridData.widthHint = 180;
		targetFileText.setLayoutData(gridData);

		final Label targetFilePathLabel = new Label(container, SWT.NONE);
		targetFilePathLabel.setText(Messages.getString("ResourceBlockDialog.targetPathLabel")); //$NON-NLS-1$

		targetPathViewer = new ComboViewer(container, SWT.BORDER);
		targetPathViewer.setContentProvider(new ArrayContentProvider());
		targetPathViewer.setLabelProvider(new TargetPathLabelProvider());
		Combo targetPathCombo = targetPathViewer.getCombo();
		final GridData gridData_1 = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		gridData_1.widthHint = 180;
		targetPathCombo.setLayoutData(gridData_1);
		targetPathViewer.setInput(new Object());
		
		uid2Label = new Label(container, SWT.NONE);
		uid2Label.setText(Messages.getString("ResourceBlockDialog.uid2Label")); //$NON-NLS-1$

		uid2Text = new Text(container, SWT.BORDER);
		final GridData gridData_2 = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		gridData_2.widthHint = 180;
		uid2Text.setLayoutData(gridData_2);
		uid3Label = new Label(container, SWT.NONE);
		uid3Label.setText(Messages.getString("ResourceBlockDialog.uid3Label")); //$NON-NLS-1$

		uid3Text = new Text(container, SWT.BORDER);
		uid3Text.setLayoutData(new GridData(180, SWT.DEFAULT));

		if (supportHeaderOnly) {
			headerRadiosComposite = new Composite(container, SWT.NONE);
			headerRadiosComposite.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
			final RowLayout headerRadiosComposite = new RowLayout();
			this.headerRadiosComposite.setLayout(headerRadiosComposite);
	
			dontGenerateHeaderButton = new Button(this.headerRadiosComposite, SWT.RADIO);
			dontGenerateHeaderButton.setText(Messages.getString("ResourceBlockDialog.noHeaderButton")); //$NON-NLS-1$
	
			generateHeaderButton = new Button(this.headerRadiosComposite, SWT.RADIO);
			generateHeaderButton.setText(Messages.getString("ResourceBlockDialog.generateHeaderButton")); //$NON-NLS-1$
	
			onlyGenerateHeaderButton = new Button(this.headerRadiosComposite, SWT.RADIO);
			onlyGenerateHeaderButton.setText(Messages.getString("ResourceBlockDialog.headerOnlyButton")); //$NON-NLS-1$
		} else {
			generateHeaderCheckbox = new Button(container, SWT.CHECK);
			generateHeaderCheckbox.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
			generateHeaderCheckbox.setText(Messages.getString("ResourceBlockDialog.generateHeaderCheckbox")); //$NON-NLS-1$
		}

		populate();
		//
		return container;
	}

	private void populate() {
		String targetFile = resourceBlock.getTargetFile();
		if (targetFile != null) {
			targetFileText.setText(targetFile);
		}
		targetPathViewer.setInput(getTargetFilePaths());
		IPath targetPath = resourceBlock.getTargetPath();
		if (targetPath == null) {
			targetPath = defaultPath;
		}
		targetPathViewer.setSelection(new StructuredSelection(targetPath));
		String uid2 = resourceBlock.getUid2();
		if (uid2 != null) {
			uid2Text.setText(uid2);
		}
		String uid3 = resourceBlock.getUid3();
		if (uid3 != null) {
			uid3Text.setText(uid3);
		}
		
		EGeneratedHeaderFlags headerFlags = resourceBlock.getHeaderFlags();
		if (supportHeaderOnly) {
			generateHeaderButton.setSelection(headerFlags == EGeneratedHeaderFlags.Header);
			onlyGenerateHeaderButton.setSelection(headerFlags == EGeneratedHeaderFlags.HeaderOnly);
			dontGenerateHeaderButton.setSelection(headerFlags == EGeneratedHeaderFlags.NoHeader);
		} else {
			generateHeaderCheckbox.setSelection(headerFlags == EGeneratedHeaderFlags.Header);
		}
	}
	

	private List<IPath> getRSSFiles() {
		ProjectFileResourceProxyVisitor visitor = new ProjectFileResourceProxyVisitor("rss", true); //$NON-NLS-1$
		List<IPath> result = null;
		try {
			project.accept(visitor, IResource.NONE);
			result = visitor.getRequestedFiles();
		} catch (CoreException x) {
			ProjectUIPlugin.log(x);
		}
		return result;
	}
	
	private List<IPath> getTargetFilePaths() {
		List<IPath> result = new ArrayList<IPath>();
		result.add(defaultPath); // for the default value
		Version version = buildConfiguration.getSDK().getOSVersion();
		if (version.getMajor() >= 9) {
			result.add(new Path("\\resource")); //$NON-NLS-1$
			result.add(new Path("\\private\\" + secureID)); //$NON-NLS-1$
		}
		IPath currTargetPath = resourceBlock.getTargetPath();
		if (currTargetPath != null && !result.contains(currTargetPath)) {
			result.add(currTargetPath);
		}
	
		return result;
	}


	/**
	 * Create contents of the button bar
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(444, 350);
	}
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.getString("ResourceBlockDialog.dialogTitle")); //$NON-NLS-1$
		WorkbenchUtils.setHelpContextId(newShell, HelpContexts.RESOURCE_BLOCK_DIALOG);
	}

	@Override
	protected void captureResults() {
		chooseFileComposite.capturePath();		
		resourceBlock.setSource(chooseFileComposite.getResultPath());
		
		String targetFile = targetFileText.getText();
		if (targetFile.length() > 0) {
			resourceBlock.setTargetFile(targetFile);
		} else {
			resourceBlock.setTargetFile(null);
		}
		
		IStructuredSelection ss = (IStructuredSelection) targetPathViewer.getSelection();
		Object element = ss.getFirstElement();
		if (element instanceof IPath) {
			resourceBlock.setTargetPath((IPath) element);
		} else {
			String text = targetPathViewer.getCombo().getText();
			if (text.length() > 0) {
				IPath mmpPath;
				try {
					mmpPath = pathHelper.convertProjectOrFullPathToMMP(EMMPPathContext.TARGETPATH, 
											new Path(text));
				} catch (InvalidDriveInMMPPathException e) {
					// should not happen
					ProjectUIPlugin.log(e);
					mmpPath = e.getPathNoDevice();
				}
				resourceBlock.setTargetPath(mmpPath);
			} else {
				resourceBlock.setTargetPath(defaultPath);
			}
		}
		
		String uid2 = uid2Text.getText();
		if (uid2.length() > 0) {
			resourceBlock.setUid2(uid2);
		} else {
			resourceBlock.setUid2(null);
		}
		String uid3 = uid3Text.getText();
		if (uid3.length() > 0 && uid2.length() > 0) {
			resourceBlock.setUid3(uid3);
		} else {
			resourceBlock.setUid3(null);
		}
		if (supportHeaderOnly) {
			EGeneratedHeaderFlags value;
			if (generateHeaderButton.getSelection()) {
				value = EGeneratedHeaderFlags.Header;
			} else if (dontGenerateHeaderButton.getSelection()) {
				value = EGeneratedHeaderFlags.NoHeader;
			} else {
				value = EGeneratedHeaderFlags.HeaderOnly;
			}
			resourceBlock.setHeaderFlags(value);
			
		} else {
			resourceBlock.setHeaderFlags(generateHeaderCheckbox.getSelection()?
					EGeneratedHeaderFlags.Header : EGeneratedHeaderFlags.NoHeader);
		}
	}
}
