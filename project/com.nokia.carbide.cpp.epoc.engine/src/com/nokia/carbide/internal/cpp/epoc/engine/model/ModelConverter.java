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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;

/**
 * This provides conversion between high-level objects in the model and
 * the IASTNodes that represent the content, and support for updating the
 * DOM when changes to the model occur.  
 *
 * @param <ElementType>
 * @param <ModelType>
 */
public interface ModelConverter<ElementType extends IASTNode, ModelType> {
	/**
	 * Take a high-level object and create an AST node.
	 * 
	 * @param elementObj
	 * @return node or null if error occurs (error logged) 
	 */
	ElementType toNode(ModelType elementObj);

	/**
	 * Take an AST node and create a high-level object.
	 * 
	 * @param node
	 * @return new model, or null if error occurs (error logged or reported to view's problem list)
	 */
	ModelType fromNode(ElementType node);
	
}
