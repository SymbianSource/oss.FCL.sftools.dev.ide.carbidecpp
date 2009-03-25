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
/**
 * 
 */
package com.nokia.sdt.sourcegen;

import com.nokia.carbide.templatewizard.TemplateWizardPlugin;
import com.nokia.sdt.preferences.PreferenceConstants;
import com.nokia.sdt.uimodel.UIModelPlugin;
import com.nokia.sdt.utils.DefaultMessageListener;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 *
 */
public class SourceGenUtils {
	
	/** Name of stored author preference */
	private static final String AUTHOR_PREFERENCE = "author"; //$NON-NLS-1$
	/** Name of stored copyright preference */
	private static final String COPYRIGHT_PREFERENCE = "copyright"; //$NON-NLS-1$
	/** Name of author variable in template */
	private static final String AUTHOR_VARIABLE = "author";
	/** Name of copyright variable in template */
	private static final String COPYRIGHT_VARIABLE = "copyright"; //$NON-NLS-1$
	/** Name of file name variable in template */
	private static final String FILENAME_VARIABLE = "fileName"; //$NON-NLS-1$
	
	private static ISourceGenProvider staticProvider;
	private static IPreferenceStore cachedPreferenceStore;

	public static ISourceGenProvider loadSourceGenProvider(String id, IProject project) throws CoreException {
		if (staticProvider != null)
			return staticProvider;
		
		ISourceGenProvider provider = loadSourceGenProviderExtension(id);
		if (provider != null)
			provider.setProject(project);
		return provider;
	}
	
	private static ISourceGenProvider loadSourceGenProviderExtension(String id) throws CoreException {
        ISourceGenProvider result = null;
        
        // Get implementors of the componentProvider extension point
        IExtensionRegistry er = Platform.getExtensionRegistry();
        if (er == null)
        	return null;
        IExtensionPoint ep = er.getExtensionPoint(ISourceGenProvider.SOURCEGEN_PROVIDER_EXTENSION);
        if (ep == null)
            return null;
        
        IExtension matched = null;
        
        // Iterate over all providers looking for the requested one
        IExtension[] extensions = ep.getExtensions();
        for (int i = 0; i < extensions.length; i++) {
            IExtension extension = extensions[i];
            IConfigurationElement[] ces = extension.getConfigurationElements();
            if (ces != null && ces.length >= 1) {
                IConfigurationElement providerElement = ces[0];
                String name = providerElement.getAttribute("name"); //$NON-NLS-1$
                if (name != null && name.equals(id)) {
                    if (providerElement.getAttribute("class") != null) { //$NON-NLS-1$
                        if (result != null) {
                            IStatus status = Logging.newStatus(UIModelPlugin.getDefault(),
                                    IStatus.ERROR,
                                    MessageFormat.format("DesignerDataModel.2", //$NON-NLS-1$
                                            new Object[] { id, extension.getUniqueIdentifier(), matched.getUniqueIdentifier() })
                                            
                                    ); 
                            Logging.log(UIModelPlugin.getDefault(), 
                                    status);
                        } else {
                            result = (ISourceGenProvider) providerElement.createExecutableExtension("class"); //$NON-NLS-1$
                            matched = extension;
                        }
                    }
                }
            }
        }
        return result;
    }

	/**
	 * Override the extension point with a custom sourcegen provider.
	 * @param provider
	 */
	public static void setDefaultSourceGenProvider(ISourceGenProvider provider) {
		staticProvider = provider;
	}

	private static IPreferenceStore getPreferenceStore() {
		if (cachedPreferenceStore == null) {
			IPreferenceStore preferenceStore = null;
			AbstractUIPlugin plugin = UIModelPlugin.getDefault();
			if (plugin != null)
				preferenceStore = plugin.getPreferenceStore();
			if (preferenceStore == null) {
				try {
					// fake it for unit tests
					File tempFile = File.createTempFile("prefstore", "dat");
					tempFile.deleteOnExit();
					preferenceStore = new PreferenceStore(tempFile.getAbsolutePath());
				} catch (IOException e) {
				}
			}
			cachedPreferenceStore = preferenceStore;
		}
		return cachedPreferenceStore;
	}


	public static String getAuthorPreference() {
		if (Platform.isRunning()) {
			return TemplateWizardPlugin.getDefault().getDialogSettings().get(AUTHOR_PREFERENCE);
		} else {
			IPreferenceStore preferenceStore = getPreferenceStore();
			if (preferenceStore == null)
				return null;
			return preferenceStore.getString(AUTHOR_PREFERENCE);
		}
	}

	public static void setAuthorPreference(String value) {
		if (Platform.isRunning()) {
			TemplateWizardPlugin.getDefault().getDialogSettings().put(AUTHOR_PREFERENCE, value);
		} else {
			IPreferenceStore preferenceStore = getPreferenceStore();
			if (preferenceStore == null)
				return;
			preferenceStore.setValue(AUTHOR_PREFERENCE, value);
		}
	}

	public static String getCopyrightPreference() {
		if (Platform.isRunning()) {
			return TemplateWizardPlugin.getDefault().getDialogSettings().get(COPYRIGHT_PREFERENCE);
		} else {
			IPreferenceStore preferenceStore = getPreferenceStore();
			if (preferenceStore == null)
				return null;
			return preferenceStore.getString(COPYRIGHT_PREFERENCE);
		}
	}

	public static void setCopyrightPreference(String value) {
		if (Platform.isRunning()) {
			TemplateWizardPlugin.getDefault().getDialogSettings().put(COPYRIGHT_PREFERENCE, value);
		} else {
			IPreferenceStore preferenceStore = getPreferenceStore();
			if (preferenceStore == null)
				return;
			preferenceStore.setValue(COPYRIGHT_PREFERENCE, value);
		}
	}

	
	public static File getSourceFileHeaderTemplate() {
		IPreferenceStore preferenceStore = getPreferenceStore();
		if (preferenceStore == null)
			return null;
		String filePath = preferenceStore.getString(
				PreferenceConstants.P_FILE_HEADER_TEMPLATE_LOCATION);
		if ((filePath == null) || (filePath.length() == 0))
			return null;
		return new File(filePath);
	}

	
	public static void setSourceFileHeaderTemplate(String path) {
		IPreferenceStore preferenceStore = getPreferenceStore();
		if (preferenceStore == null)
			return;
		preferenceStore.setValue(
				PreferenceConstants.P_FILE_HEADER_TEMPLATE_LOCATION,
				path == null ? "" : path); //$NON-NLS-1$
	}

	/**
	 * Load the file header template and expand it into a string
	 * @param file the file reference
	 * @return String for the new file contents
	 */
	public static String getNewFileContents(File file) {
		Check.checkArg(file);
		
		File templateFile = getSourceFileHeaderTemplate();
		if (templateFile == null)
			return ""; //$NON-NLS-1$
		
		try {
			String text = new String(FileUtils.readFileContents(templateFile, "UTF-8"));
			VariableSubstitutionEngine engine = new VariableSubstitutionEngine(
					DefaultMessageListener.INSTANCE, 
					new MessageLocation(new Path(templateFile.getAbsolutePath())));
			Map<String, String> variables = new HashMap<String, String>(getTemplateMap());
			variables.put(FILENAME_VARIABLE, file.getName());
			text = engine.substitute(variables, text);
			return text;
		} catch (CoreException e) {
			UIModelPlugin.log(e);
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * Get the built-in template variables
	 * @return
	 */
	private static Map<String, String> getTemplateMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(AUTHOR_VARIABLE, getAuthorPreference());
		map.put(COPYRIGHT_VARIABLE, getCopyrightPreference());
		return map;
	}
}
