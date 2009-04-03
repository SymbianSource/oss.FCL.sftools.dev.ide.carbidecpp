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
package com.nokia.sdt.component.symbian;

import com.nokia.cpp.internal.api.utils.core.ILocalizedStrings;

import org.eclipse.emf.ecore.EObject;

import java.io.File;
import java.util.Iterator;

/**
 * IFacetContainer supports the implementation of
 * EMF-based component facets. The idea is that the concrete component
 * definition schema is composed of standalone schema building blocks.
 * When imported into EMF this schema is used to generated the component
 * code. The implementation of a facet relies on the specific Java types generated
 * for the schema that it knows about, but not on any other aspects of the generated 
 * code. Therefore, the facet implementation needs to get to its data reflectively.
 * The IFacetContainer interface lets the facet get to the containing EObject generically.
 * The facet must also be provided with the metadata needed to find its data
 * within that containing object at runtime.
 */
public interface IFacetContainer {

	/**
	 * Returns the EObject containing all the component information
	 */
	EObject	getEMFContainer();
	
	/**
	 * Returns the canonical base directory used for any relative paths defined
	 * in a component definition
	 */
	File	getBaseDirectory();
	
	/**
	 * Provides an iterator over any additional facet containers 
	 * accessible from the main container. Used to provide inheritance or
	 * other re-use mechanisms. The Iterator must return IFacetContainer
	 * instances.
	 * @returns an Iterator, even when there are no additional containers
	 */
	Iterator getAdditionalFacetContainers();
	
	/**
	 * Provides a service for looking up localized strings
	 */
	ILocalizedStrings getLocalizedStrings();
}
