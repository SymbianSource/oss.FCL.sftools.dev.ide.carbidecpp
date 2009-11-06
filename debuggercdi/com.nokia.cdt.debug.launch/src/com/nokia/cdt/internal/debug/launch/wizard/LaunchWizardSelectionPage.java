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

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;

class LaunchWizardSelectionPage extends WizardSelectionPage implements ISelectionChangedListener, IStructuredContentProvider {

	private LaunchCreationWizard mainWizard;
	private FormBrowser descriptionBrowser;
	private TableViewer wizardSelectionTableViewer = null;
	private ILaunchWizard selectedWizard = null;
	private boolean inputChanged = false;
	
	public LaunchWizardSelectionPage(LaunchCreationWizard mainWizard, List<IPath> mmps, List<IPath> exes, IPath defaultExecutable, IProject project, String configurationName, String mode) throws Exception {
		super(Messages.getString("LaunchWizardSelectionPage.0")); //$NON-NLS-1$
		setTitle(Messages.getString("LaunchWizardSelectionPage.1")); //$NON-NLS-1$
		setDescription(Messages.getString("LaunchWizardSelectionPage.2")); //$NON-NLS-1$
		this.mainWizard = mainWizard;
		descriptionBrowser = new FormBrowser();
		descriptionBrowser.setText(""); //$NON-NLS-1$
	}

	public void createDescriptionIn(Composite composite) {
		descriptionBrowser.createControl(composite);
		Control c = descriptionBrowser.getControl();
		c.setData(".uid", "LaunchWizardSelection.descriptionBrowser");
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.widthHint = 200;
		c.setLayoutData(gd);
	}

	public String getLabel() {
		return Messages.getString("LaunchWizardSelectionPage.4"); //$NON-NLS-1$
	}
	
	public void setDescriptionText(String text) {
		descriptionBrowser.setText(text);
	}
	
	public void moveToNextPage() {
		getContainer().showPage(getNextPage());
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.verticalSpacing = 10;
		container.setLayout(layout);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label label = new Label(container, SWT.NONE);
		label.setText(getLabel());
		GridData gd = new GridData();
		label.setLayoutData(gd);
		label.setData(".uid", "LaunchWizardSelectionPage.label");
		
		SashForm sashForm = new SashForm(container, SWT.VERTICAL);
		gd = new GridData(GridData.FILL_BOTH);
		gd.widthHint = 300;
		gd.heightHint = 300;
		gd.minimumHeight = 230;
		sashForm.setLayoutData(gd);
		
		wizardSelectionTableViewer = new TableViewer(sashForm, SWT.BORDER);
		wizardSelectionTableViewer.setContentProvider(this);
		wizardSelectionTableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				selectionChanged(new SelectionChangedEvent(wizardSelectionTableViewer, wizardSelectionTableViewer.getSelection()));
				moveToNextPage();
			}
		});
		wizardSelectionTableViewer.addSelectionChangedListener(this);

		createDescriptionIn(sashForm);
		sashForm.setWeights(new int[] {75, 25});

		Dialog.applyDialogFont(container);
		setControl(container);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), LaunchWizardHelpIds.WIZARD_SELECTION_PAGE);
	}
	
	public TableViewer getViewer() {
		return wizardSelectionTableViewer;
	}
	
	public void selectionChanged(SelectionChangedEvent event) {
		selectedWizard = null;
		setErrorMessage(null);
		IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		Object selectedObject = null;
		Iterator iter = selection.iterator();
		if (iter.hasNext()) {
			selectedObject = iter.next();
			if (selectedObject instanceof ILaunchWizard)
				selectedWizard = (ILaunchWizard)selectedObject;
		}
		if (selectedWizard == null) {
			setDescriptionText(""); //$NON-NLS-1$
			setSelectedNode(null);
			return;
		}
		setSelectedNode(new WizardNode(this, (Wizard)selectedWizard));
		setDescriptionText(selectedWizard.getDescription());
	}
	
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible && wizardSelectionTableViewer != null) {
			wizardSelectionTableViewer.setInput(mainWizard.getWizardsForCategory(mainWizard.getSelectedCategoryId()));
			if (inputChanged) {
				wizardSelectionTableViewer.setSelection(new StructuredSelection(wizardSelectionTableViewer.getElementAt(0)), true);
			}
			wizardSelectionTableViewer.getTable().setFocus();
		}
	}

	public Object[] getElements(Object inputElement) {
		List<Wizard> wizards = (List<Wizard>)inputElement;
		return wizards.toArray();
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (oldInput == null || newInput == null || !oldInput.equals(newInput)) {
			inputChanged = true;
		} else {
			inputChanged = false;
		}
	}
	
	public ILaunchWizard getSelectedWizard() {
		return selectedWizard;
	}
}
