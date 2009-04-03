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
* This interface is used in conversions of model objects into list statements across multiple
* statements.
*
* @param <ElementType>
* @param <ModelType>
*
*/
package com.nokia.carbide.internal.cpp.epoc.engine.model;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListHolder;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
public interface ListStatementListConverter<ElementType extends IASTNode, ModelType> 
	extends ListConverter<ElementType, ModelType>, StatementListConverter<ElementType, ModelType> {
	/**
	 * Create a new statement holding elements of the list.
	 * @return new statement
	 */
	IASTListHolder<ElementType> createNewListStatement();
	

	
}
