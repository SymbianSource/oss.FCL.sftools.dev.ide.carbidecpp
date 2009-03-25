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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
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

import com.nokia.cdt.internal.debug.launch.LaunchPlugin;
import com.nokia.cpp.internal.api.utils.core.*;

class LaunchWizardSelectionPage extends WizardSelectionPage implements ISelectionChangedListener, IStructuredContentProvider {

	private FormBrowser descriptionBrowser;
	private TableViewer wizardSelectionTableViewer = null;
	private List<Wizard> wizards = new ArrayList<Wizard>();
	private ILaunchWizard selectedWizard = null;
	
	
	public LaunchWizardSelectionPage(List<IPath> mmps, List<IPath> exes, IPath defaultExecutable, IProject project, String configurationName) throws Exception {
		super(Messages.getString("LaunchWizardSelectionPage.0")); //$NON-NLS-1$
		setTitle(Messages.getString("LaunchWizardSelectionPage.1")); //$NON-NLS-1$
		setDescription(Messages.getString("LaunchWizardSelectionPage.2")); //$NON-NLS-1$
		descriptionBrowser = new FormBrowser();
		descriptionBrowser.setText(""); //$NON-NLS-1$
		
		AppTRKLaunchWizard appTRKWizard = new AppTRKLaunchWizard(mmps, exes, defaultExecutable, project, configurationName);
		appTRKWizard.addPages();
		wizards.add(appTRKWizard);
		
		SystemTRKLaunchWizard sysTRKWizard = new SystemTRKLaunchWizard(mmps, exes, defaultExecutable, project, configurationName);
		sysTRKWizard.addPages();
		wizards.add(sysTRKWizard);

		Trace32LaunchWizard trace32Wizard = new Trace32LaunchWizard(mmps, exes, defaultExecutable, project, configurationName); 
		trace32Wizard.addPages();
		wizards.add(trace32Wizard);

		SophiaLaunchWizard sophiaWizard = new SophiaLaunchWizard(mmps, exes, defaultExecutable, project, configurationName);
		sophiaWizard.addPages();
		wizards.add(sophiaWizard);

		AttachTRKLaunchWizard attachTRKWizard = new AttachTRKLaunchWizard(mmps, exes, defaultExecutable, project, configurationName);
		attachTRKWizard.addPages();
		wizards.add(attachTRKWizard);

		// load any wizard extensions
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = extensionRegistry.getExtensionPoint(LaunchPlugin.PLUGIN_ID + ".launchWizardExtension"); //$NON-NLS-1$
		IExtension[] extensions = extensionPoint.getExtensions();
		
		for (int i = 0; i < extensions.length; i++) {
			IExtension extension = extensions[i];
			IConfigurationElement[] elements = extension.getConfigurationElements();
			Check.checkContract(elements.length == 1);
			IConfigurationElement element = elements[0];
			
			boolean failed = false;
			try {
				Object extObject = element.createExecutableExtension("class"); //$NON-NLS-1$
				if (extObject instanceof ILaunchWizardContributor) {
					AbstractLaunchWizard wizard = ((ILaunchWizardContributor)extObject).getWizard(mmps, exes, defaultExecutable, project, configurationName);
					wizard.addPages();
					wizards.add(wizard);
				} else {
					failed = true;
				}
			} 
			catch (CoreException e) {
				failed = true;
			}
			
			if (failed) {
				LaunchPlugin.log(Logging.newStatus(LaunchPlugin.getDefault(), 
						IStatus.ERROR,
						"Unable to load launchWizardExtension extension from " + extension.getContributor().getName()));
			}
		}
	}

	public void createDescriptionIn(Composite composite) {
		descriptionBrowser.createControl(composite);
		Control c = descriptionBrowser.getControl();
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
		wizardSelectionTableViewer.setInput(wizards);
		wizardSelectionTableViewer.addSelectionChangedListener(this);

		createDescriptionIn(sashForm);
		sashForm.setWeights(new int[] {75, 25});

		Dialog.applyDialogFont(container);
		setControl(container);

		// select the first element by default
		wizardSelectionTableViewer.setSelection(new StructuredSelection(wizardSelectionTableViewer.getElementAt(0)), true);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), LaunchWizardHelpIds.WIZARD_SELECTION_PAGE);
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
			wizardSelectionTableViewer.getTable().setFocus();
		}
	}

	public Object[] getElements(Object inputElement) {
		return wizards.toArray();
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		
	}
	
	public ILaunchWizard getSelectedWizard() {
		return selectedWizard;
	}
}
