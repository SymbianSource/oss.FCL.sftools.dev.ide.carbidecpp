/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.template.engine;

import com.nokia.carbide.internal.api.templatewizard.ui.IWizardDataPage;
import com.nokia.carbide.internal.api.templatewizard.ui.TemplateWizardPage;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

import java.util.List;

/**
 * This interface, available from {@link ILoadedTemplate#createLoadedTemplateUI()}, 
 * provides access to the UI created and consumed by a wizard.
 * <p>
 * The UI is created such that all fields have default valus, unless {@link #loadSettings(IPersistedSettingStorage)}
 * is called to initialize from persisted storage.
 *
 */
public interface ILoadedTemplateUI {
	/**
	 * Get the template the UI is associated with
	 * @return ITemplate, never <code>null</code>
	 */
	ITemplate getTemplate();

	/**
	 * Load the template variables from the given storage.  This may be called anytime.
	 * @param storage the implementation of {@link IPersistedSettingStorage} which maintains
	 * the settings
	 */
	void loadSettings(IPersistedSettingStorage storage);

	/**
	 * Initialize the extra pages provider when the template has been selected by the wizard.
	 * @param wizard
	 * @param workbench
	 * @param selection
	 */
	void initExtraPagesProvider(IWorkbenchWizard wizard, IWorkbench workbench, IStructuredSelection selection);
	
	/**
	 * Get the pages created by the extra pages provider from the extraPagesProvider
	 * attribute of the wizard.  These are typically inserted between the template
	 * selection page and the pages provided by the wizard itself.
	 * @return List<IWizardDataPage>, never <code>null</code>
	 */
	List<IWizardDataPage> getExtraPagesProviderPages(IWorkbenchWizard wizard);
	
	/** 
	 * Construct or retrieve the previously-created wizard pages specified by the template.
	 * 
	 * @param wizard the wizard into which to add the pages
	 * @return list of constructed pages, added to the wizard 
	 */
	List<TemplateWizardPage> getWizardPages(IWizard wizard);
	
	/**
	 * Persist the template variables to the given storage.  This may be called any time, but
	 * is only meaningful when a wizard has been accepted by the user.
	 * @param storage the implementation of {@link IPersistedSettingStorage} which maintains
	 * the settings
	 */
	void saveSettings(IPersistedSettingStorage storage);
	
	/**
	 * Dispose the created wizard pages for a template
	 */
	void dispose();

}
