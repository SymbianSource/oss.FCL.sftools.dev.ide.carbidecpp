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
package com.nokia.sdt.datamodel;

import com.nokia.sdt.sourcegen.ISourceGenProvider;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;

	/**
	 * Factory interface for loading a data model from
	 * a file.
	 * Used as part of the modelLoader extension point
	 * exposed by the UI designer for loading the
	 * data model.
	 */
public interface IDesignerDataModelProvider {
	
	/**
	 * Create a new data model to be saved at the
	 * given file.
	 * @param fileURI desired file path.  Any existing file is overwritten
	 * @param encoding optional file encoding, can be null for UTF-8
	 */
	IDesignerDataModel create(URI fileURI, String encoding) throws Exception;

	/**
	 * Load a previously saved data model from the URI.
	 * <p>
	 * If a sourcegen provider is given, create a
	 * sourcegen session and load source state necessary.
	 * <p>
	 * It may be useful to pass null for the provider for
	 * a short-term, read-only view of the model ignoring
	 * any pending two-way user changes.
	 * @param fileURI
	 * @param specifier data model specifier or null
	 * @param provider sourcegen provider or null
	 */
	LoadResult load(URI fileURI, IDesignerDataModelSpecifier specifier, ISourceGenProvider provider);
	
	/**
	 * Creates a temporary data model. The returned model
	 * will not yet have a component set.
	 */
	IDesignerDataModel createTemporary();
	
	/**
	 * Creates a temporary data model compatible with the given
	 * model. The returned model will have an equivalent component
	 * set and can therefore be used to hold copies of objects
	 * from the source model.
	 * @throws CoreException 
	 */
	IDesignerDataModel createCompatibleTemporary(IDesignerDataModel sourceModel) throws CoreException;
}
