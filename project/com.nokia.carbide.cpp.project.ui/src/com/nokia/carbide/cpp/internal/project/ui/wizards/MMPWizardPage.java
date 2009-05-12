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
package com.nokia.carbide.cpp.internal.project.ui.wizards;

import com.nokia.carbide.cpp.internal.project.ui.ProjectUIHelpIds;

import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (mmp).
 */

public class MMPWizardPage extends WizardPage {
	private Text containerText;
	private Text fileText;
	private Button browseButton;
	private Button isTestCheck;
	private ISelection selection;
	private boolean isTest;

	
	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public MMPWizardPage(ISelection selection, boolean isTest) {
		super("wizardPage"); //$NON-NLS-1$
		this.selection = selection;
		this.isTest = isTest;
		setTitle(Messages.getString("MMPWizardPage.wizardTitle")); //$NON-NLS-1$
		setDescription(Messages.getString("MMPWizardPage.wizardDescription")); //$NON-NLS-1$
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		Label label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("MMPWizardPage.folderLabel")); //$NON-NLS-1$

		containerText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		containerText.setLayoutData(gd);
		
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			Object element = ss.getFirstElement();
			IContainer selectedContainer = null;
			if (element != null) {
				selectedContainer = (IContainer) Platform.getAdapterManager().getAdapter(element, IContainer.class);
			}
			if (selectedContainer != null) {
				containerText.setText(selectedContainer.toString());
			} else if (element instanceof ICElement) {
				ICElement ce = (ICElement) element;
				IResource resource = ce.getResource();
				if (resource instanceof IContainer) {
					containerText.setText(resource.getFullPath().toString());
				} 
			}
		}
		
		containerText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		browseButton = new Button(container, SWT.PUSH);
		browseButton.setText(Messages.getString("MMPWizardPage.browseButton")); //$NON-NLS-1$
		browseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("MMPWizardPage.fileNameLabel")); //$NON-NLS-1$

		fileText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		fileText.setLayoutData(gd);
		fileText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		isTestCheck = new Button(container, SWT.CHECK);
		isTestCheck.setText(Messages.getString("MMPWizardPage.testLabel")); //$NON-NLS-1$
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		isTestCheck.setLayoutData(gd);
		isTestCheck.setSelection(isTest);
		
		initialize();
		dialogChanged();
		setControl(container);
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {
		if (selection != null && selection.isEmpty() == false
				&& selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1)
				return;
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				IContainer container;
				if (obj instanceof IContainer)
					container = (IContainer) obj;
				else
					container = ((IResource) obj).getParent();
				containerText.setText(container.getFullPath().toString());
			}
		}
		fileText.setText(Messages.getString("MMPWizardPage.defaultMMPName")); //$NON-NLS-1$
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */

	private void handleBrowse() {
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(
				getShell(), ResourcesPlugin.getWorkspace().getRoot(), false,
				Messages.getString("MMPWizardPage.selectFolderTitle")); //$NON-NLS-1$
		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				containerText.setText(((Path) result[0]).toString());
			}
		}
	}

	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {
		IResource container = ResourcesPlugin.getWorkspace().getRoot()
				.findMember(new Path(getContainerName()));
		String fileName = getFileName();

		if (getContainerName().length() == 0) {
			updateStatus(Messages.getString("MMPWizardPage.missingFolderStatus")); //$NON-NLS-1$
			return;
		}
		if (container == null
				|| (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0) {
			updateStatus(Messages.getString("MMPWizardPage.folderDoesntExistStatus")); //$NON-NLS-1$
			return;
		}
		if (!container.isAccessible()) {
			updateStatus(Messages.getString("MMPWizardPage.nonWriteableProjectStatus")); //$NON-NLS-1$
			return;
		}
		if (fileName.length() == 0) {
			updateStatus(Messages.getString("MMPWizardPage.noFileNameStatus")); //$NON-NLS-1$
			return;
		}
		if (fileName.replace('\\', '/').indexOf('/', 1) > 0) {
			updateStatus(Messages.getString("MMPWizardPage.invalidFileNameStatus")); //$NON-NLS-1$
			return;
		}
		int dotLoc = fileName.lastIndexOf('.');
		if (dotLoc != -1) {
			String ext = fileName.substring(dotLoc + 1);
			if (ext.equalsIgnoreCase("mmp") == false) { //$NON-NLS-1$
				updateStatus(Messages.getString("MMPWizardPage.wrongExtensionStatus")); //$NON-NLS-1$
				return;
			}
		}
		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getContainerName() {
		return containerText.getText();
	}

	public String getFileName() {
		return fileText.getText();
	}
	
	public boolean isTest() {
		return isTestCheck.getSelection();
	}

	@Override
	public void performHelp() {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl().getShell(), ProjectUIHelpIds.MMP_WIZARD_PAGE);
	}

	public Text getContainerText() {
		return containerText;
	}

	public Text getFileNameText() {
		return fileText;
	}

	public Button getBrowseButton() {
		return browseButton;
	}

	public Button getTestMMPButton() {
		return isTestCheck;
	}

}