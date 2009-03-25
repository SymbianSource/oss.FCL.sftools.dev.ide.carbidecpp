/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.model;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTStatement;

/**
 * This converter extends {@link ModelConverter} by providing additional support
 * for structured model items.  In these, one aspect of the model counts as a key;
 * this is used with {@link #elementMatches(ModelType, ModelType)} to determine whether
 * two model elements match closely enough to allow changes to the same DOM node.
 * If so, {@link #updateNode(ElementType, ElementType)} will be used to update that
 * node.  
 *
 * @param <ElementType>
 * @param <ModelType>
 */
public interface StructuredItemListConverter<ElementType extends IASTStatement, ModelType>
	extends ModelConverter<ElementType, ModelType> {

	/**
	 * Tell if the given model element matches another. This is different
	 * from equality checking, and is different because block statements are
	 * identified by key rather than by full content.
	 */
	boolean elementMatches(ModelType element, ModelType another);
	
	/** Update an existing node from a newly generated element. */
	void updateNode(ElementType existing, ElementType newElement);
	

}
