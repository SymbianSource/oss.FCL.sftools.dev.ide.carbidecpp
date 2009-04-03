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

package com.nokia.sdt.component;

import org.eclipse.core.runtime.IAdapterFactory;

import java.util.Map;

/**
 * A component provider represents an implementation of a
 * particular type of component with its own mechanism
 * for discovering and loading those components.
 * 
 * Each provider also has its own its own mean of querying
 * the available components to product component sets.
 */
public interface IComponentProvider {
	
	/**
	 * The unique identifier for this provider
	 */
	String getId();

	/**
	 * Installs an adapter factory. This is used to extend
	 * the set of adapters that are available for this provider's
	 * components
	 */
	void registerAdapterFactory(IAdapterFactory factory);
	
	
	/**
	 * Retrieves an adapter of adapterType for a component from registered factories
	 * @param component an IComponent
	 * @param adapterType a Class
	 * @return the adapter or null
	 */
	Object adaptComponent(IComponent component, Class adapterType);
	
	/**
	 * Registers a type descriptor. All data types, such as
	 * primitive types, enums, and compound properties must
	 * have a type descriptor. 
	 * The details of how properties are handled is specific 
	 * to a component system.
	 */
	void registerTypeDescriptor(String typeId, ITypeDescriptor handler);
	
	/**
	 * Returns a displayable string corresponding to the
	 * given component category
	 * @param category value returned from IComponent.getCategory
	 */
	String getCategoryText(String category);
		
	/**
	 * Select a set of components into a new IComponentSet. The returned
	 * component set will have at most one entry with a given qualified name.
	 * When there a multiple versions of a component the highest version accepted
	 * by the filter is chosen.
	 * @param filter determines the components selected into the set, 
	 * can be null to select all
	 */
	ComponentSetResult queryComponents(IComponentFilter filter);
	
	/**
	 * Create a component set using a previously saved
	 * set of properties.
	 */
	ComponentSetResult reQueryComponents(Map queryProperties);

    /**
     * Refresh the component provider by adding new components,
     * removing old components, and replacing changed components.
     */
    void refresh();
    
    /**
     * The properties used to create a component set may be
     * stored into a data model. These properties must have a 
     * unique prefix to allow the data model to determine which
     * properties are contributed by the component set.
     * @return
     */
    String getPropertyPrefix();
}
