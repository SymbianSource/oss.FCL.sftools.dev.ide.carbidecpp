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

import com.nokia.carbide.internal.api.templatewizard.ui.IExtraPagesProvider;
import com.nokia.carbide.internal.template.gen.Template.TemplateType;
import com.nokia.carbide.template.engine.ILoadedTemplate;
import com.nokia.carbide.template.engine.ITemplate;
import com.nokia.carbide.templatewizard.Messages;
import com.nokia.carbide.templatewizard.TemplateWizardPlugin;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.IActivityManager;
import org.osgi.framework.Bundle;

import java.net.URL;
import java.text.MessageFormat;
import java.util.*;

/**
 * Singleton class that reads wizard template extensions and maintains list of available templates
 * for use by the templates wizard.
 */
public class TemplateEngine {

	private static final String EXTENSION_ID = TemplateWizardPlugin.ID + ".wizardTemplate"; //$NON-NLS-1$
	
	private static final String TEMPLATE_ID = "templateId"; //$NON-NLS-1$
	private static final String LOCATION = "location"; //$NON-NLS-1$
	private static final String WIZARD_ID = "wizardId"; //$NON-NLS-1$
	private static final String GROUP_LABEL = "groupLabel"; //$NON-NLS-1$
	private static final String FILTER_ARGS = "filterArguments"; //$NON-NLS-1$
	private static final String EXTRA_PAGES_PROVIDER = "extraPagesProvider"; //$NON-NLS-1$
	private static final String IMAGE = "image"; //$NON-NLS-1$

	private static final String DISPLAY_NAME = "displayName"; //$NON-NLS-1$
	
	private static TemplateEngine instance;
	
	private List<ITemplate> templates;
	
	public static TemplateEngine getInstance() {
		if (instance == null) {
			instance = new TemplateEngine();
		}
		
		return instance;
	}
	
	public static boolean isLoaded() {
		return instance != null;
	}
	
	private TemplateEngine() {
		templates = readTemplates(true);
	}

	public static List<ITemplate> readTemplates(boolean validate) {
		List<ITemplate> templates = new ArrayList<ITemplate>();
		
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = extensionRegistry.getExtensionPoint(EXTENSION_ID);
		IExtension[] extensions = extensionPoint.getExtensions();
		
		for (int i = 0; i < extensions.length; i++) {
			IExtension extension = extensions[i];
			String pluginId = extension.getNamespaceIdentifier();
			IConfigurationElement[] elements = extension.getConfigurationElements();
			for (int j = 0; j < elements.length; j++) {
				IConfigurationElement element = elements[j];

				String templateId = element.getAttribute(TEMPLATE_ID);
				String location = element.getAttribute(LOCATION);
				String wizardId = element.getAttribute(WIZARD_ID);
				String name = element.getAttribute(DISPLAY_NAME);
				String groupLabel = element.getAttribute(GROUP_LABEL);
				String filterArgs = element.getAttribute(FILTER_ARGS);
				String imageAttribute = element.getAttribute(IMAGE);

				Bundle bundle = Platform.getBundle(pluginId);
				URL templateUrl = bundle.getEntry(location);
				
				Map<String, String> metadata =	new LinkedHashMap<String, String>();
				IConfigurationElement[] metadatas = element.getChildren("metadata");
				for (IConfigurationElement meta : metadatas) {
					metadata.put(meta.getAttribute("name"), meta.getValue());
				}
					
				Template template = new Template(templateUrl, bundle, name, groupLabel, 
												filterArgs, templateId, wizardId,
												metadata,
												element);
				
				if (imageAttribute != null) {
					URL iconUrl = template.getBundle().getEntry(imageAttribute);
					if (iconUrl == null) {
						Logging.log(TemplateWizardPlugin.getDefault(), 
								Logging.newStatus(TemplateWizardPlugin.getDefault(), IStatus.ERROR, 
										MessageFormat.format(Messages.getString("TemplateEngine.ImageNotFoundError"),  //$NON-NLS-1$
												new Object[] { imageAttribute, template.getBundle().getSymbolicName() })));
					}
					ImageDescriptor imageDescriptor = ImageDescriptor.createFromURL(iconUrl);
					template.setImageDescriptor(imageDescriptor);
				}
				
				templates.add(template);
			}
		}
		
		return templates;
	}
	
	private boolean isEnabledActivity(String templateId) {
		try {
			IActivityManager activityManager = PlatformUI.getWorkbench().getActivitySupport().getActivityManager();
			Set definedActivityIds = activityManager.getDefinedActivityIds();
			Set enabledActivityIds = activityManager.getEnabledActivityIds();
	
			return !definedActivityIds.contains(templateId) || enabledActivityIds.contains(templateId);
		} catch (IllegalStateException e) {
			// PlatformUI.getWorkbench() throws if running headless: assume everything's enabled
			return true;
		}
	}
	
	/**
	 * Get all the templates for a given wizard registered with the template engine.
	 * @param wizardId the "new project" wizard identifier, or <code>null</code> for all
	 * @return non-<code>null</code> list of templates
	 */
	public List<ITemplate> getTemplates(String wizardId) {
		List<ITemplate> filteredTemplates = new ArrayList<ITemplate>();
		for (Iterator<ITemplate> iter = templates.iterator(); iter.hasNext();) {
			ITemplate template = iter.next();
			if (isEnabledActivity(template.getTemplateId()) &&
					(wizardId == null || wizardId.equals(template.getWizardId())))
				filteredTemplates.add(template);
		}
		
		return filteredTemplates;
	}
	
	/**
	 * Get all the templates registered with the template engine passing a given filter
	 * @param filter the filter to apply, or <code>null</code> for all
	 * @return non-<code>null</code> list of templates
	 */
	public List<ITemplate> getFilteredTemplates(ITemplateFilter filter) {
		List<ITemplate> filteredTemplates = new ArrayList<ITemplate>();
		for (Iterator<ITemplate> iter = templates.iterator(); iter.hasNext();) {
			ITemplate template = iter.next();
			if (isEnabledActivity(template.getTemplateId()) &&
					filter.accept(template)) {
				filteredTemplates.add(template);
			}
		}
		
		return filteredTemplates;
	}
	
	
	public static void logError(String message, Throwable t) {
		Logging.log(TemplateWizardPlugin.getDefault(), Logging.newSimpleStatus(IStatus.ERROR, t));
	}
	
	public static IStatus runProcesses(ITemplate template, IProgressMonitor monitor) {
		try {
			ILoadedTemplate loadedTemplate = template.getLoadedTemplate();
			return loadedTemplate.runProcesses(monitor);
		} catch (CoreException e) {
			return e.getStatus();
		}
	}
	
	static ILoadedTemplate loadTemplate(ITemplate template, IConfigurationElement element) throws CoreException {
		TemplateType templateType = TemplateLoader.loadTemplate(template.getBundle(), template.getTemplateUrl(), true);
		LoadedTemplate loadedTemplate = new LoadedTemplate(template, templateType);
		
		String providerAttribute = element != null ? element.getAttribute(EXTRA_PAGES_PROVIDER) : null;

		if (providerAttribute != null) {
			try {
				IExtraPagesProvider extraPagesProvider = 
					(IExtraPagesProvider) element.createExecutableExtension(TemplateEngine.EXTRA_PAGES_PROVIDER);
				loadedTemplate.setExtraPagesProvider(extraPagesProvider);
			} 
			catch (InvalidRegistryObjectException e) {
				// if the configuration element is out of date
				throw new CoreException(Logging.newStatus(TemplateWizardPlugin.getDefault(), e));
			}
		}		
		
		return loadedTemplate;
	}

}
