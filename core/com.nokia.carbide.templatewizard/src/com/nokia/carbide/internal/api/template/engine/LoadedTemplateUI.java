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


package com.nokia.carbide.internal.api.template.engine;

import com.nokia.carbide.internal.api.templatewizard.ui.*;
import com.nokia.carbide.internal.template.gen.Template.*;
import com.nokia.carbide.template.engine.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

import java.util.*;

/**
 * Default implementation of ILoadedTemplateUI interface.
 *
 */
public class LoadedTemplateUI implements ILoadedTemplateUI {
	private ITemplate template;
	private List<TemplateWizardPage> wizardPages;
	private TemplateType templateType;
	private IExtraPagesProvider extraPagesProvider;

	public LoadedTemplateUI(ITemplate template, 
					TemplateType templateType, IExtraPagesProvider extraPagesProvider) {
		this.template = template;
		this.templateType = templateType;
		this.extraPagesProvider = extraPagesProvider;
	}
	
	public void dispose() {
		if (wizardPages != null) {
			for (IWizardPage page : wizardPages) {
				page.dispose();
			}
			wizardPages.clear();
			wizardPages = null;
		}
	}


	public List<TemplateWizardPage> getWizardPages(IWizard wizard) {
		if (wizardPages != null)
			return wizardPages;
		
		if (templateType != null) {
			if (wizardPages == null)
				wizardPages = new ArrayList();
			List<WizardPageType> wizardPageTypes = templateType.getWizardPage();
			for (WizardPageType wizardPageType : wizardPageTypes) {
				wizardPages.add(new TemplateWizardPage(wizard, template, wizardPageType));
			}
		}
		return wizardPages;
	}
	
	private BaseFieldType findFirstFieldById(String id) {
		if (templateType != null) {
			List<WizardPageType> wizardPageTypes = templateType.getWizardPage();
			for (WizardPageType wizardPageType : wizardPageTypes) {
				for (Iterator<EObject> iter = wizardPageType.eContents().iterator(); iter.hasNext(); ) {
		    		EObject obj = iter.next();
					if (obj instanceof BaseFieldType) {
						BaseFieldType baseFieldType = (BaseFieldType) obj;
						if (baseFieldType.getId().equals(id))
							return baseFieldType;
					}
				}
			}
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.template.engine.ILoadedTemplateUI#loadSettings(com.nokia.carbide.template.engine.IPersistedSettingStorage)
	 */
	public void loadSettings(IPersistedSettingStorage storage) {
		if (templateType == null)
			return;
			
		Map<String, Object> templateValues = template.getTemplateValues();
		List<WizardPageType> wizardPageTypes = templateType.getWizardPage();
		for (WizardPageType wizardPageType : wizardPageTypes) {
			for (Iterator<EObject> iter = wizardPageType.eContents().iterator(); iter.hasNext(); ) {
	    		EObject obj = iter.next();
				if (obj instanceof BaseFieldType) {
					BaseFieldType baseFieldType = (BaseFieldType) obj;
					String id = baseFieldType.getId();
					if (baseFieldType.isPersist()) {
						String value = storage.read(id);
						if (value != null) {
							templateValues.put(id, value);
						}
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.template.engine.ILoadedTemplateUI#saveSettings(com.nokia.carbide.template.engine.IPersistedSettingStorage)
	 */
	public void saveSettings(IPersistedSettingStorage storage) {
		Map<String, Object> templateValues = template.getTemplateValues();
		for (Iterator<String> iter = templateValues.keySet().iterator(); iter.hasNext();) {
			String id = iter.next();
			Object value = templateValues.get(id);
			
			if (value instanceof String) {
				// only want String values if persist is true
				BaseFieldType baseFieldType = findFirstFieldById(id);
				if (baseFieldType != null && baseFieldType.isPersist())
					storage.write(id, value.toString());
			}
		}
	}
	
	public void initExtraPagesProvider(IWorkbenchWizard wizard, IWorkbench workbench, IStructuredSelection selection) {
		if (extraPagesProvider != null)
			extraPagesProvider.init(wizard, workbench, selection);
	}
	
	public List<IWizardDataPage> getExtraPagesProviderPages(IWorkbenchWizard wizard) {
		if (extraPagesProvider != null)
			return extraPagesProvider.getPages(wizard);
		
		return Collections.emptyList();
	}

	@Override
	public String toString() {
		if (template == null)
			return super.toString();
		
		return "Template UI for " + template.toString(); //$NON-NLS-1$
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.template.engine.ILoadedTemplate#getTemplate()
	 */
	public ITemplate getTemplate() {
		return template;
	}
}
