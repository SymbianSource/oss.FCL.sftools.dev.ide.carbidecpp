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

import com.nokia.sdt.datamodel.IDesignerDataModel;

import java.util.Iterator;
import java.util.Map;

/**
 * A component set represents a collection of components
 * from a given component provider. For example, the set
 * of components matching a given SDK version.
 */
public interface IComponentSet extends Iterable<IComponent> {
	
	/**
	 * Returns the provider that created this component set
	 */
	IComponentProvider	getProvider();
	
	/**
	 * Returns the component with the given id, if it's a
	 * member of this set.
	 */
	IComponent	lookupComponent(String id);
	
	/**
	 * Returns an iterator that returns all components
	 * in the set.
	 */
	Iterator<IComponent> iterator();
	
	/**
	 * Returns the type descriptor with the given id,
	 * if it's a member of this set.
	 * @param typeId the id of the type or null
	 * @return the type descriptor or null
	 */
	ITypeDescriptor lookupTypeDescriptor(String typeId);
	
	/**
	 * @return the number of contained components
	 */
	int numComponents();
	
	/**
	 * @return the number of contained type descriptors
	 */
	int numTypeDescriptors();
	
	/**
	 * Allow the component set to do any initialization tasks on the data model.
	 * 
	 * @param dataModel
	 */
	void initializeDataModel(IDesignerDataModel dataModel);
	
	/**
	 * Return a set of properties that may be used to
	 * recreate the query parameters of this component
	 * set. The list of actual components do not need to
	 * be saved. All properties must begin with the prefix
	 * returned by IComponentProvider.getPropertyPrefix.
	 * All values will be saved using the text returned by toString().
	 */
	Map  getPropertiesForPersistence();

	/**
	 * Return true if the given persistence properties are compatible with
	 * this set, i.e. the components selected by these properties are all in
	 * this component set.
	 */
	boolean arePropertiesCompatible(Map properties);
}
