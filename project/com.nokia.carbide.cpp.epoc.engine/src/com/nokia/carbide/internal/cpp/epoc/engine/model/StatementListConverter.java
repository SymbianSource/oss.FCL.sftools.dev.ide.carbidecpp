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
* This interface is used in conversions of model objects across multiple
* statements.
*
* @param <ElementType>
* @param <ModelType>
*
*/
package com.nokia.carbide.internal.cpp.epoc.engine.model;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTStatement;
import com.nokia.cpp.internal.api.utils.core.*;
public interface StatementListConverter<ElementType extends IASTNode, ModelType> extends ModelConverter<ElementType, ModelType> {

	/** Get the insert anchors for the new node(s).  This is called before {@link #createContextStatement(Object)}
	 * or {@link #toNode(ModelType)}.
	 * @return Pair of anchors; may be null
	 */
	Pair<IASTNode, IASTNode> getInsertAnchors();
	
	/** Tell whether an update of a node will require a new context statement
	 * in the current statement ordering. */
	boolean changeRequiresNewContext(ModelType existing, ModelType newElement);	
	
	/** Optionally create a new statement that is needed for the given model
	 * to be correctly specified in the current statement ordering. 
	 * @param model new item in new statement
	 * @return new context statement or null
	 */
	IASTStatement createContextStatement(ModelType model);
	
	/** Do any necessary work to associate a context statement with the statement whose conversion used it. */
	void associateContextStatement(IASTStatement stmt, IASTStatement contextStmt);
	
}
