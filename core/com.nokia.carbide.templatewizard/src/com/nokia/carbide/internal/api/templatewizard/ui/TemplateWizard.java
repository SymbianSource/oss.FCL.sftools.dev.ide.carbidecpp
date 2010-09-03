/*
* Copyright (c) 2006, 2008 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.internal.api.templatewizard.ui;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;

import com.nokia.carbide.cpp.internal.featureTracker.FeatureUseTrackerPlugin;
import com.nokia.carbide.internal.api.template.engine.TemplateEngine;
import com.nokia.carbide.template.engine.DialogSettingsPersistedStorage;
import com.nokia.carbide.template.engine.ILoadedTemplate;
import com.nokia.carbide.template.engine.ILoadedTemplateUI;
import com.nokia.carbide.template.engine.IPersistedSettingStorage;
import com.nokia.carbide.template.engine.ITemplate;
import com.nokia.carbide.templatewizard.TemplateWizardPlugin;
import com.nokia.cpp.internal.api.utils.core.Check;

public abstract class TemplateWizard extends BasicNewResourceWizard {

	private boolean finishPressed;
	private IFilter templateFilter;
	private String filterCheckboxLabel;
	private boolean hideFilterCheckbox;
	private ChooseTemplatePage chooseTemplatePage;

	@Override
	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
		super.init(workbench, currentSelection);
		setDialogSettings(TemplateWizardPlugin.getDefault().getDialogSettings());
		setForcePreviousAndNextButtons(true);
		setNeedsProgressMonitor(true);
	}
	
	@Override
	public void addPages() {
		chooseTemplatePage = new ChooseTemplatePage();
		chooseTemplatePage.setTemplateFilter(templateFilter);
		if (filterCheckboxLabel != null)
			chooseTemplatePage.setFilterCheckboxLabel(filterCheckboxLabel);
		chooseTemplatePage.setHideFilterCheckbox(hideFilterCheckbox);
		addPage(chooseTemplatePage);
	}
	
	@Override
	public boolean canFinish() {
		if (finishPressed)
			return false;
		
		return super.canFinish();
	}

	@Override
	public boolean performFinish() {
		finishPressed = true;
		getContainer().updateButtons();
		
		final ILoadedTemplate loadedTemplate = getSelectedLoadedTemplate();
		final ILoadedTemplateUI loadedTemplateUI = getSelectedTemplateUI();
		Check.checkState(loadedTemplate != null);
		
		// gather the settings from all wizard pages and any we may have skipped
		storeAllPageValuesInMap(loadedTemplate.getTemplate().getTemplateValues());
		
		persistValues(loadedTemplateUI);
		
		performPreprocessingTask();
		
		WorkspaceJob job = new WorkspaceJob(getProcessingTitle()) {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				try {
					return TemplateEngine.runProcesses(loadedTemplate.getTemplate(), monitor);
				}
				finally {
					loadedTemplateUI.dispose();
				}
			}
		};
		job.setUser(true);
		job.setRule(getJobSchedulingRule());
		job.schedule();
		
		String featureName = getFeatureName();
		if (featureName != null && !featureName.isEmpty()) {
			FeatureUseTrackerPlugin.getFeatureUseProxy().useFeature(featureName);
		}
		
	    return true;
	}

	/**
	 * Override to handle any processing before the template processes are run.  By default does nothing.
	 */
	protected void performPreprocessingTask() {
		
	}

	@Override
	public boolean performCancel() {
		ILoadedTemplateUI selectedTemplateUI = getSelectedTemplateUI();
		if (selectedTemplateUI != null)
			selectedTemplateUI.dispose();
		
		return true;
	}

	private void persistValues(ILoadedTemplateUI loadedTemplateUI) {
		loadedTemplateUI.saveSettings(getPersistedSettingsStorage());
	}

	/**
	 * Get the instance of storage for reading/writing persistent settings
	 * for a template.  This store for settings is expected to remain the same for the lifetime
	 * of a selected template, since it is read and written.  It may have a longer
	 * lifetime as well.
	 * <p>
	 * The default implementation uses this dialog's IDialogSettings as a store.
	 * @return {@link IPersistedSettingStorage}
	 */
	protected IPersistedSettingStorage getPersistedSettingsStorage() {
		return new DialogSettingsPersistedStorage(getDialogSettings());
	}

	/**
	 * Augment the template value map with the values from the template and non-template data pages.
	 * The map should already contain entries from template pages the user has navigated.
	 * This step gathers other IWizardDataPage contributions and also ensures that unnavigated
	 * template pages are probably initialized and queried.
	 * @param valueMap
	 */
	private void storeAllPageValuesInMap(Map<String, Object> valueMap) {
		IWizardPage[] wizardPages = getPages();
		storePageListValuesInMap(valueMap, Arrays.asList(wizardPages));

		List<IWizardDataPage> pagesAfterTemplateChoice = getPagesAfterTemplateChoice();
		if (pagesAfterTemplateChoice != null) {
			storePageListValuesInMap(valueMap, pagesAfterTemplateChoice);
		}
		
		List<IWizardDataPage> extraPagesProviderPages = getSelectedTemplateUI().getExtraPagesProviderPages(this);
		storePageListValuesInMap(valueMap, extraPagesProviderPages);
		
		List<TemplateWizardPage> templatePages = getSelectedTemplateUI().getWizardPages(this);
		storePageListValuesInMap(valueMap, templatePages);
	}

	private void storePageListValuesInMap(Map<String, Object> valueMap, Collection<?> wizardPages) {
		if (wizardPages == null)
			return;
		
		for (Object page : wizardPages) {
			if (page instanceof IWizardDataPage) {
				IWizardDataPage wizardDataPage = (IWizardDataPage) page;
				Map values = wizardDataPage.getPageValues();
				if (values != null) {
					valueMap.putAll(wizardDataPage.getPageValues());
				}
			}
		}
	}

	/**
	 * @param filterCheckboxLabel The filterCheckboxLabel to set.
	 */
	public void setFilterCheckboxLabel(String filterCheckboxLabel) {
		this.filterCheckboxLabel = filterCheckboxLabel;
	}

	/**
	 * @param templateFilter The templateFilter to set.
	 */
	public void setTemplateFilter(IFilter templateFilter) {
		this.templateFilter = templateFilter;
	}

	/**
	 * @param hideFilterCheckbox whether to hide the filter checkbox - default false
	 */
	public void setHideFilterCheckbox(boolean hideFilterCheckbox) {
		this.hideFilterCheckbox = hideFilterCheckbox;
	}
	
	public ITemplate getSelectedTemplate() {
		if (chooseTemplatePage == null)
			return null;
		return chooseTemplatePage.getSelectedTemplate();
	}


	public ILoadedTemplate getSelectedLoadedTemplate() {
		if (chooseTemplatePage == null)
			return null;
		return chooseTemplatePage.getSelectedLoadedTemplate();
	}

	/**
	 * @return
	 */
	public ILoadedTemplateUI getSelectedTemplateUI() {
		if (chooseTemplatePage == null)
			return null;
		return chooseTemplatePage.getSelectedTemplateUI();
	}

	public WizardSelectionPage getChooseTemplatePage() {
		return chooseTemplatePage;
	}

	/**
	 * Return the list of pages which follow the template selection page, 
	 * before the template-provided pages.
	 * @return list of pages or <code>null</code>
	 */
	public List<IWizardDataPage> getPagesAfterTemplateChoice() {
		return null;
	}

	/**
	 * Return the list of pages which follow the template-provided pages.
	 * @return list of pages or <code>null</code>
	 */
	public List<IWizardDataPage> getPagesAfterTemplatePages() {
		return null;
	}

	public abstract String getChooseTemplatePageTitle();
	
	public abstract String getChooseTemplatePageDescription();
	
	public abstract String getWizardId();
	
	public abstract ISchedulingRule getJobSchedulingRule();
	
	public abstract String getProcessingTitle();
	
	public abstract String getFeatureName();

	public void notifyTemplateChanged() {
		// nothing to do in base class
	}

}
