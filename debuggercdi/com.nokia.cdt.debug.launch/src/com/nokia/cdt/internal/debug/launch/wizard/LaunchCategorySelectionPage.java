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
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
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
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;

import com.nokia.cdt.internal.debug.launch.LaunchPlugin;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;

class LaunchCategorySelectionPage extends WizardPage implements ISelectionChangedListener, IStructuredContentProvider {

	private LaunchCreationWizard wizard;
	private FormBrowser descriptionBrowser;
	private TableViewer categorySelectionTableViewer = null;
	
	private class LaunchCategory {
		private String id;
		private String name;
		private String description;
		
		LaunchCategory(String id, String name, String description) {
			this.id = id;
			this.name = name;
			this.description = description;
		}
		
		public String getId() {
			return id;
		}
		
		public String getName() {
			return name;
		}
		
		public String getDescription() {
			return description;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof LaunchCategory) {
				LaunchCategory category = (LaunchCategory)obj;
				
				// just compare the id and name to check for uniqueness
				return category.getId().equals(id) && category.getName().equals(name);
			}
			return false;
		}

		@Override
		public String toString() {
			return name;
		}
	}
	
	private List<LaunchCategory> categories = new ArrayList<LaunchCategory>();

	
	public LaunchCategorySelectionPage(LaunchCreationWizard wizard) throws Exception {
		super(Messages.getString("LaunchCategorySelectionPage.title")); //$NON-NLS-1$
		setTitle(Messages.getString("LaunchCategorySelectionPage.title")); //$NON-NLS-1$
		setDescription(Messages.getString("LaunchCategorySelectionPage.description")); //$NON-NLS-1$
		
		this.wizard = wizard;
		descriptionBrowser = new FormBrowser();
		descriptionBrowser.setText(""); //$NON-NLS-1$
		
		// add the built in categories
		if (wizard.getWizardsForCategory(AbstractLaunchWizard.PHONE_CATEGORY_ID).size() > 0) {
			categories.add(new LaunchCategory(AbstractLaunchWizard.PHONE_CATEGORY_ID,
					Messages.getString("LaunchCategorySelectionPage.phone"), //$NON-NLS-1$));
					Messages.getString("LaunchCategorySelectionPage.phoneDesc"))); //$NON-NLS-1$));
		}

		if (wizard.getWizardsForCategory(AbstractLaunchWizard.BOARD_CATEGORY_ID).size() > 0) {
			categories.add(new LaunchCategory(AbstractLaunchWizard.BOARD_CATEGORY_ID,
					Messages.getString("LaunchCategorySelectionPage.board"), //$NON-NLS-1$));
					Messages.getString("LaunchCategorySelectionPage.boardDesc"))); //$NON-NLS-1$));
		}
		
		loadCategoryExtensions();
	}

	public void createDescriptionIn(Composite composite) {
		descriptionBrowser.createControl(composite);
		Control c = descriptionBrowser.getControl();
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.widthHint = 200;
		c.setLayoutData(gd);
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
		label.setText(Messages.getString("LaunchCategorySelectionPage.categoryLabel")); //$NON-NLS-1$
		GridData gd = new GridData();
		label.setLayoutData(gd);
		
		SashForm sashForm = new SashForm(container, SWT.VERTICAL);
		gd = new GridData(GridData.FILL_BOTH);
		gd.widthHint = 300;
		gd.heightHint = 300;
		gd.minimumHeight = 230;
		sashForm.setLayoutData(gd);
		
		categorySelectionTableViewer = new TableViewer(sashForm, SWT.BORDER);
		categorySelectionTableViewer.setContentProvider(this);
		categorySelectionTableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				selectionChanged(new SelectionChangedEvent(categorySelectionTableViewer, categorySelectionTableViewer.getSelection()));
				moveToNextPage();
			}
		});
		categorySelectionTableViewer.setInput(categories);
		categorySelectionTableViewer.addSelectionChangedListener(this);

		createDescriptionIn(sashForm);
		sashForm.setWeights(new int[] {75, 25});

		Dialog.applyDialogFont(container);
		setControl(container);

		// select the first element by default
		categorySelectionTableViewer.setSelection(new StructuredSelection(categorySelectionTableViewer.getElementAt(0)), true);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), LaunchWizardHelpIds.CATEGORY_SELECTION_PAGE);
	}
	
	public void selectionChanged(SelectionChangedEvent event) {
		String description = null;
		IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		if (!selection.isEmpty()) {
			LaunchCategory selectedCategory = (LaunchCategory)selection.getFirstElement();
			description = selectedCategory.getDescription();
		}
		setDescriptionText(description);
	}
	
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible && categorySelectionTableViewer != null) {
			categorySelectionTableViewer.getTable().setFocus();
		}
	}

	public Object[] getElements(Object inputElement) {
		return categories.toArray();
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}
	
	public String getSelectedCategoryId() {
		IStructuredSelection selection = (IStructuredSelection)categorySelectionTableViewer.getSelection();
		LaunchCategory selectedCategory = (LaunchCategory)selection.getFirstElement();
		return selectedCategory.getId();
	}
	
	private void loadCategoryExtensions() {
		// load any category extensions
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = extensionRegistry.getExtensionPoint(LaunchPlugin.PLUGIN_ID + ".launchCategoryExtension"); //$NON-NLS-1$
		IExtension[] extensions = extensionPoint.getExtensions();
		
		for (int i = 0; i < extensions.length; i++) {
			IExtension extension = extensions[i];
			IConfigurationElement[] elements = extension.getConfigurationElements();
			Check.checkContract(elements.length == 1);
			IConfigurationElement element = elements[0];
			
			boolean failed = false;
			try {
				String id = element.getAttribute("id"); //$NON-NLS-1$
				String name = element.getAttribute("name"); //$NON-NLS-1$
				String description = element.getAttribute("description"); //$NON-NLS-1$
				if (id == null || name == null || description == null) {
					failed = true;
				} else {
					if (wizard.getWizardsForCategory(id).size() > 0) {
						LaunchCategory category = new LaunchCategory(id, name, description);
						if (!categories.contains(category)) {
							categories.add(category);
						} else {
							failed = true;
						}
					}
				}
			} 
			catch (Exception e) {
				failed = true;
			}
			
			if (failed) {
				LaunchPlugin.log(Logging.newStatus(LaunchPlugin.getDefault(), 
						IStatus.ERROR,
						"Unable to load launchCategoryExtension extension from " + extension.getContributor().getName()));
			}
		}
	}
}
