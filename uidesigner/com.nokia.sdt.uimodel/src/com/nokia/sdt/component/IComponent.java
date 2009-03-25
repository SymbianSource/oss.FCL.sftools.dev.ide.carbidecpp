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

import com.nokia.cpp.internal.api.utils.core.MessageLocation;

import org.eclipse.core.runtime.IAdaptable;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

import java.io.*;

/** 
 * The main interface for a component.
 * Each aspect of component information and behavior
 * is implemented by one or more adapters. Adapters
 * are obtained from each component's provider. 
 * <p>
 * This approach allows the provider to specify how adapters
 * are implemented for the components in provides.
 * <p>
 * The set of adapters can be extended by getting
 * component provider from the component system
 * and registering new adapter factories.
 * <p>
 * Here's an example of obtaining an adapter:
 * <pre>
 * IComponent myComponent = ...
 * IPropertyDescriptorProvider pdProvider =
 *     myComponent.getAdapter(IPropertyDescriptorProvider.class);
 * </pre>
 */
public interface IComponent extends IAdaptable {
	
	/**
	 * Returns the provider that implements this component type
	 */
	IComponentProvider getProvider();
	
	/**
	 * Returns the bundle providing this component (may be null)
	 */
	Bundle getBundle();
	
    /** 
     * Returns the component's unique id. The convention
     * for component naming is specific to a provider, but
     * each id must be unique to the provider.
     */
    String getId();
	
	/**
	 * Returns the component's category, if any.
	 * The result should be already localized, if possible.
	 */
	String getCategory();
	
	/**
	 * Returns the component's version as an OSGI version object.
	 * @return
	 */
	public Version getComponentVersion();
	
	/**
	 * Returns a string used as the root part of a
	 * component instance name. This root can have a prefix
	 * and suffix added for uniqueness and source generation purposes.
	 * This method should just return a base name that makes sense
	 * for the component without regard to uniqueness or source
	 * generation issues.
	 */
	String getInstanceNameRoot();
    
	/**
	 * Returns the "friendly name" attribute of the component.
	 * @return friendly name String
	 */
	String getFriendlyName();
	
	/**
	 * Returns the base component's unique id, or null if no base component
	 */
	String getBaseComponentId();
	
	/**
     * Returns the immediate base component for this component.
     */
    IComponent getComponentBase();
    
    /**
     * An abstract component can act as a base component but cannot be instantiated
     */
    boolean isAbstract();
    
    /**
     * Returns the component set this component belongs to.
     * Each component is shallow-copied into a component set.
     * @returns IComponentSet or null if this component comes straight
     * from a component provider.   
     */
    IComponentSet getComponentSet();
    
    /**
     * Create a message location based on this component,
     * e.g. for reporting errors that come from a component definition.
     */
    MessageLocation createMessageLocation();

    /**
     * Get the base directory of the component.
     * May return null if component is not located on disk.
     */
    File getBaseDirectory();
}
