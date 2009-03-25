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


package com.nokia.carbide.template.engine;

import com.nokia.carbide.templatewizard.process.IProcess;

import org.eclipse.core.runtime.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.Bundle;

import java.net.URL;
import java.util.Map;

/**
 * Runtime access for the metadata of a single template in a 
 * <i>com.nokia.carbide.templatewizard.wizardTemplate</i> extension.
 */
public interface ITemplate {

	/**
	 * The file URL representing the absolute path of the template xml file.
	 * 
	 * @return <code>java.net.URL</code>
	 */
	URL getTemplateUrl();

	/**
	 * The Bundle of the Plugin where this template is declared.
	 * 
	 * @return <code>org.osgi.framework.Bundle</code>
	 */
	Bundle getBundle();

	/**
	 * The value of the templateId attribute for this template.
	 * 
	 * @return <code>java.lang.String</code>
	 */
	String getTemplateId();
	
	/**
	 * The wizardId attribute for this template.
	 * 
	 * @return identifier for an Eclipse org.eclipse.ui.newWizards extension
	 */
	String getWizardId();
	
	/**
	 * Get the name attribute for this template.  If it is not defined,
	 * read it from the 'label' in template XML.
	 */
	String getDisplayName();
	
	/**
	 * The groupLabel attribute for this template, used to categorize templates.
	 * 
	 * @return the string or unlocalized key for the group label.
	 */
	String getGroupLabel();

	/**
	 * The filterArguments attribute for this template.  The semantics
	 * of this string is up to the specific instanceof TemplateWizard. 
	 * 
	 * @return the string used for filtering
	 */
	String getFilterArguments();
	
	/**
	 * Get metadata for the template from the extension point definition.
	 * @return the map of key-value pairs
	 */
	Map<String, String> getExtensionMetadata();
	
	/**
	 * Get the map containing the key/value pairs associated with the template.
	 * The keys are those specified in the template XML's &lt;wizardPage&gt;'s &lt;...Field&gt;'s id attributes.
	 * The map should be initialized with persisted defaults before the wizard's template UI is invoked,
	 * via XXX, and saved 
	 * After the wizard that this template specified has completed successfully,
	 * the Map (from field id to value) containing the actual user values from the wizard.
	 * This is passed to processes via {@link IProcess} 
	 * 
	 * @return <code>java.util.Map<String, Object></code>
	 */
	Map<String, Object> getTemplateValues();

	/**
	 * Get the localized version of the given text.  If the text is a 
	 * percent-key referring to a property file entry (e.g. "%StringEntry"), 
	 * return the entry for the current language.  Otherwise, return the text itself.
	 * @param text 
	 * @return original text or localized string 
	 */
	public String getLocalizedString(String text);
	
	/**
	 * Get the image descriptor for the template
	 * @return {@link ImageDescriptor}, never <code>null</code>
	 */
	public ImageDescriptor getImageDescriptor();

	/**
	 * Get the loaded template information, loading the XML into memory if necessary.
	 * @return ILoadedTemplate
	 * @throws CoreException if template.xml could not be loaded
	 */
	ILoadedTemplate getLoadedTemplate() throws CoreException;

	/**
	 * Reset a template for use by a new wizard.  
	 */
	void reset();
}
