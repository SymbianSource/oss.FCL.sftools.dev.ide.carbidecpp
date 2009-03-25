/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.component.symbian.implementations;

import com.nokia.sdt.component.symbian.ComponentSystemPlugin;

import org.eclipse.core.runtime.*;

import java.util.*;

/**
 * 
 * Singleton owned by the plugin.
 */
public class ImplementationsTypesRegistry {
	
	static class AdapterClassPair {
		public Class codeAdapter;
		public Class scriptAdapter;
		public AdapterClassPair(Class codeAdapter, Class scriptAdapter) {
			this.codeAdapter = codeAdapter;
			this.scriptAdapter = scriptAdapter;
		}
	}
	
	private Map interfacesToAdapters;
	
	private final static String EXT_ID = "implementation";
	
	public ImplementationsTypesRegistry() {
		interfacesToAdapters = new HashMap();
		loadNewExtensions();
	}
	
    /** Scan the extension points and load all the new component libraries. */
    public synchronized void loadNewExtensions() {
    	// Get implementors of the implementation extension point
    	IExtensionRegistry er = Platform.getExtensionRegistry();
        if (er == null)
            return;
    	IExtensionPoint ep = er.getExtensionPoint(ComponentSystemPlugin.PLUGIN_ID, EXT_ID);
        if (ep == null)
            return;

    	// Iterate each, loading new extensions
    	IExtension[] extensions = ep.getExtensions();
    	for (int i = 0; i < extensions.length; i++) {
   			loadExtension(extensions[i]);
    	}
    }

    /**
     * Load a new implementation extension into the registry.
     * @param extension
     */
    private void loadExtension(IExtension extension) {
    	ComponentSystemPlugin.infoIf("debug", "loading extension " + extension.getUniqueIdentifier()); //$NON-NLS-1$ //$NON-NLS-2$
    	IConfigurationElement[] ces = extension.getConfigurationElements();
    	for (int i = 0; i < ces.length; i++) {
    		try {
    			IImplementationTypeFactory factory = 
    				(IImplementationTypeFactory) ces[i].createExecutableExtension("class");
    			Class interfaceClass = factory.getInterface();
    			Class codeAdapterClass = factory.getCodeImplAdapterClass();
    			Class scriptAdapterClass = factory.getScriptImplAdapterClass();
    			String interfaceId = getInterfaceId(interfaceClass);
    			addInterface(interfaceId, codeAdapterClass, scriptAdapterClass);
    		} 
    		catch (CoreException e) {
    			ComponentSystemPlugin.log(e);
    			ComponentSystemPlugin.info("could not load component " + ces[i]); //$NON-NLS-1$
    		}
    	}
    }
    
	public static String getInterfaceId(Class interfaceClass) {
		return interfaceClass.getName();
	}
	
	public Set getInterfaces() {
		return interfacesToAdapters.keySet();
	}
	
	public boolean containsType(Class interfaceClass) {
		return getInterfaces().contains(getInterfaceId(interfaceClass));
	}
	
	public Class getCodeAdapterClass(String interfaceId) {
		AdapterClassPair adapterClassPair = (AdapterClassPair) interfacesToAdapters.get(interfaceId);
        if (adapterClassPair == null) {
            ComponentSystemPlugin.log(new IllegalStateException("interface " + interfaceId + " not implemented as expected by its com.nokia.sdt.component.symbian.implementation extension"));
            return null;
        }
        return adapterClassPair.codeAdapter;
	}
	
	public Class getScriptAdapterClass(String interfaceId) {
		AdapterClassPair adapterClassPair = ((AdapterClassPair) interfacesToAdapters.get(interfaceId));
        if (adapterClassPair == null) {
            ComponentSystemPlugin.log(new IllegalStateException("Interface " + interfaceId + " not registered via a com.nokia.sdt.component.symbian.implementation extension"));
            return null;
        }
        return adapterClassPair.scriptAdapter;
	}
	
	public void addInterface(String interfaceId, Class codeAdapterClass, Class scriptAdapterClass) {
		interfacesToAdapters.put(interfaceId, 
				new AdapterClassPair(codeAdapterClass, scriptAdapterClass));
	}
}
