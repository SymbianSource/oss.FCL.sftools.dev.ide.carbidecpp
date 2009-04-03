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
package com.nokia.carbide.cpp.internal.project.ui.sharedui;

import java.util.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.ICarbideBuildManager;
import com.nokia.carbide.cpp.internal.api.sdk.ui.TemplateUtils;
import com.nokia.carbide.cpp.internal.project.ui.Messages;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIHelpIds;
import com.nokia.carbide.internal.api.templatewizard.ui.IWizardDataPage;
import com.nokia.cpp.internal.api.utils.core.Check;

public class ChooseProjectPage extends WizardPage implements IWizardDataPage {

	private TableViewer tableViewer;
	private IProject project;
	private ICarbideBuildManager buildManager;

	/**
	 * Create the wizard
	 */
	public ChooseProjectPage(String title, String description) {
		super(Messages.getString("ChooseProjectPage.Name")); //$NON-NLS-1$
		setTitle(title);
		setDescription(description);
		buildManager = CarbideBuilderPlugin.getBuildManager();
	}

	/**
	 * Create contents of the wizard
	 * @param parent
	 */
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);

		Composite container = new Composite(parent, SWT.NULL);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.marginTop = 10;
		gridLayout.marginRight = 10;
		gridLayout.marginLeft = 10;
		gridLayout.marginBottom = 10;
		gridLayout.horizontalSpacing = 10;
		container.setLayout(gridLayout);

		final Label selectAnExistingLabel = new Label(container, SWT.NONE);
		final GridData gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		selectAnExistingLabel.setLayoutData(gridData);
		selectAnExistingLabel.setText(Messages.getString("ChooseProjectPage.Label")); //$NON-NLS-1$

		tableViewer = new TableViewer(container, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		tableViewer.getTable().setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setLabelProvider(new WorkbenchLabelProvider());
		tableViewer.setInput(getEligibleProjects());
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = event.getSelection();
				if (selection.isEmpty())
					handleEmptySelection();
				else
					handleSelection(selection);
			}
		});
		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = event.getSelection();
				if (!selection.isEmpty()) {
					handleSelection(selection);
					getContainer().showPage(getNextPage());
				}
			}
		});
		if (project == null)
			handleEmptySelection();
		else
			setInitialSelection(project);

		//
		setControl(container);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), ProjectUIHelpIds.CHOOSE_PROJECT_PAGE);
	}
	
	public void setInitialSelection(IProject project) {
		if (!projectIsEligible(project))
			return;
		
		this.project = project;
		if (tableViewer != null)
			tableViewer.setSelection(new StructuredSelection(project));
	}

	protected void handleSelection(ISelection selection) {
		project = (IProject) ((StructuredSelection) selection).getFirstElement();
		setErrorMessage(null);
		setPageComplete(true);
		
		// check and see if project has changed. Some pages may need to be re-initied.
		// Note: Could add a listener here instead of having hard dependency on certain wizard pages.
		// but for now this is localized
		for (IWizardPage page : this.getWizard().getPages()){
			if (page instanceof ClassNameAndLocationPage){
				ClassNameAndLocationPage nextPage = (ClassNameAndLocationPage)page;
				nextPage.setProject(project);
				break;
			}
		}
	}

	protected void handleEmptySelection() {
		if (tableViewer.getTable().getItemCount() == 0)
			setErrorMessage(Messages.getString("ChooseProjectPage.NoProjectsError")); //$NON-NLS-1$
		else
			setErrorMessage(Messages.getString("ChooseProjectPage.NoSelectionError")); //$NON-NLS-1$
		setPageComplete(false);
	}

	private IProject[] getEligibleProjects() {
		List<IProject> eligibleProjects = new ArrayList<IProject>();
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (int i = 0; i < projects.length; i++) {
			IProject project = projects[i];
			if (projectIsEligible(project)) {
				eligibleProjects.add(project);
			}
		}
		
		return (IProject[]) eligibleProjects.toArray(new IProject[eligibleProjects.size()]);
	}

	private boolean projectIsEligible(IProject project) {
		return buildManager.isCarbideProject(project);
	}

	public Map<String, Object> getPageValues() {
		Check.checkState(project != null);
		return Collections.singletonMap(TemplateUtils.PROJECT_NAME, (Object) project.getName());
	}

	public IProject getProject() {
		return project;
	}
}
