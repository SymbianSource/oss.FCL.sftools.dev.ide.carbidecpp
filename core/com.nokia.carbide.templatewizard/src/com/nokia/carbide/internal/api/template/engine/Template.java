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

import com.nokia.carbide.template.engine.*;
import com.nokia.carbide.templatewizard.Messages;
import com.nokia.carbide.templatewizard.TemplateWizardPlugin;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.Bundle;

import java.net.URL;
import java.text.MessageFormat;
import java.util.*;

public class Template implements ITemplate {
	
	private final URL templateUrl;
	private final Bundle bundle;
	private final String groupLabel;
	private final String filterArgs;
	private final String templateId;
	private final String wizardId;
	private Map<String, Object> templateValues;
	private ILocalizedStrings strings;
	private ImageDescriptor imageDescriptor;

	private ILoadedTemplate loadedTemplate;
	private final IConfigurationElement element;
	private String name;
	private Map<String, String> metadata;
	
	public Template(URL templateUrl, Bundle bundle, String name, String groupLabel, 
								String filterArgs, String templateId, String wizardId,
								Map<String, String> metadata,
								IConfigurationElement element) {
		this.templateUrl = templateUrl;
		this.bundle = bundle;
		this.name = name;
		this.groupLabel = groupLabel;
		this.filterArgs = filterArgs;
		this.templateId = templateId;
		this.wizardId = wizardId;
		this.element = element;
		this.metadata = metadata;
		
		try {
			createLocalizedStringSupport();
		} catch (Exception e) {
			TemplateEngine.logError(Messages.getString("Template.LocalizedStringsError"), e); //$NON-NLS-1$
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.template.engine.ITemplate#getTemplateUrl()
	 */
	public URL getTemplateUrl() {
		return templateUrl;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.template.engine.ITemplate#getBundle()
	 */
	public Bundle getBundle() {
		return bundle;
	}
	
	public String getFilterArguments() {
		return filterArgs;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.template.engine.ITemplate#getExtensionMetadata()
	 */
	public Map<String, String> getExtensionMetadata() {
		return metadata;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.template.engine.ITemplate#getDisplayName()
	 */
	public String getDisplayName() {
		if (name != null) {
			return getLocalizedString(name);
		}
		
		// fallback for old Carbide projects: you don't want to get here!
		try {
			name = getLoadedTemplate().getLabel();
			Logging.log(TemplateWizardPlugin.getDefault(), 
					Logging.newStatus(TemplateWizardPlugin.getDefault(), IStatus.WARNING, 
							MessageFormat.format("No ''displayName'' attribute defined on <wizardTemplate> extension ''{0}'', forcing a load of XML", //$NON-NLS-1$
									templateId)));
			return name;
		} catch (CoreException e) {
			Logging.log(TemplateWizardPlugin.getDefault(), 
					Logging.newStatus(TemplateWizardPlugin.getDefault(), IStatus.WARNING, 
							MessageFormat.format("Failed to load template.xml for extension ''{0}''", //$NON-NLS-1$
									templateId), e));
			return null;
		}
	}

	public String getGroupLabel() {
		return groupLabel;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.template.engine.ITemplate#getTemplateId()
	 */
	public String getTemplateId() {
		return templateId;
	}

	public String getWizardId() {
		return wizardId;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.template.engine.ITemplate#getTemplateValues()
	 */
	public Map<String, Object> getTemplateValues() {
		if (templateValues == null)
			templateValues = new HashMap<String, Object>();
		return templateValues;
	}
	
	@Override
	public String toString() {
		return "Template " + templateId + " at " + templateUrl; //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	private void createLocalizedStringSupport() throws CoreException {
		String fileName = templateUrl.getFile();
		IPath path = new Path(fileName);
		String baseName = path.removeFileExtension().toString();
		URL directoryUrl = FileUtils.getParentPathURL(templateUrl);
		strings = new LocalizedStrings(directoryUrl, baseName);
	}
	
	public String getLocalizedString(String templateString) {
		return strings != null ? strings.checkPercentKey(templateString) : templateString;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.template.engine.ITemplate#getLoadedTemplate()
	 */
	public synchronized ILoadedTemplate getLoadedTemplate() throws CoreException {
		if (loadedTemplate == null) {
			loadedTemplate = TemplateEngine.loadTemplate(this, element);
		}
		return loadedTemplate;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.template.engine.ITemplate#reset()
	 */
	public synchronized void reset() {
		if (templateValues != null)
			templateValues.clear();
	}

	public void setImageDescriptor(ImageDescriptor imageDescriptor) {
		this.imageDescriptor = imageDescriptor;
	}
	
	public ImageDescriptor getImageDescriptor() {
		return imageDescriptor;
	}
	

}
