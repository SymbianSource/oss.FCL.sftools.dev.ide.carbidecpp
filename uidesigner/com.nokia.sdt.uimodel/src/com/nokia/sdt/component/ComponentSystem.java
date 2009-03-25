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
package com.nokia.sdt.component;

import com.nokia.sdt.uimodel.Messages;
import com.nokia.sdt.uimodel.UIModelPlugin;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.*;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * The interface for the component system.
 * 
 * This maintains a collection of component providers. Each provider
 * represents a specific implementation of components and
 * can provide a unique set of IComponent instances.
 * 
 * @see IComponent
 * 
 * 
 */
public class ComponentSystem {
	
		/**
		 * Name of the component provider extension point
		 */
	public static final String PROVIDER_EXTENSION = "componentProvider"; //$NON-NLS-1$
	
	static private ComponentSystem instance;
	
        /**
         * Map of String -> IComponentProvider
         */
	private Map providers = new HashMap();
	
		/**
		 * Returns the singleton instance of the component system
		 */
	static public ComponentSystem getComponentSystem() {
		if (instance == null) {
			instance = new ComponentSystem();
		}
		return instance;
	}
	
	private ComponentSystem() {
	}

	/**
	 * Returns the requested component provider implementation, if loaded.
	 * @param id a string representing the provider
	 * @return provider or null if not yet loaded
	 * @throws CoreException
	 */
	public synchronized IComponentProvider findProvider(String id) throws CoreException {
		return (IComponentProvider) providers.get(id);
	}

	/**
	 * Returns the requested component provider implementation,
	 * loading its extension if necessary.
	 * @param id a string representing the provider
	 * @return provider or null if not found
	 * @throws CoreException
	 */
	public synchronized IComponentProvider getProvider(String id) throws CoreException {
			// check if already loaded this provider
		IComponentProvider result = (IComponentProvider) providers.get(id);
		if (result == null) {
	        result = loadExtensions(id);
	        providers.put(id, result);
		}
		return result;
	}

    /**
     * Set the component provider
     * <p>
     * For testing to set a provider without requiring a running workbench.
     * @param id a string representing the provider
     * @param provider the provider instance (must not be null)
     */
    public synchronized void setProvider(String id, IComponentProvider provider) {
        Check.checkArg(id);
        Check.checkArg(provider);
        providers.put(id, provider);
    }
    
    private IComponentProvider loadExtensions(String id) throws CoreException {
        IComponentProvider result = null;
        
        // Get implementors of the componentProvider extension point
        IExtensionRegistry er = Platform.getExtensionRegistry();
        if (er == null)
            return null;
        
        IExtensionPoint ep = er.getExtensionPoint(
                UIModelPlugin.PLUGIN_ID, PROVIDER_EXTENSION);

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
                            Logging.log(UIModelPlugin.getDefault(), 
                                    Logging.newStatus(UIModelPlugin.getDefault(),
                                            IStatus.ERROR,
                                            MessageFormat.format(Messages.getString("ComponentSystem.1"),
                                                    new Object[] { id, extension.getUniqueIdentifier(), matched.getUniqueIdentifier() })
                                                    
                                            ));
                        } else {
                            result = (IComponentProvider) providerElement.createExecutableExtension("class"); //$NON-NLS-1$
                            matched = extension;
                        }
					}
				}
			}
        }
        return result;
    }
}
