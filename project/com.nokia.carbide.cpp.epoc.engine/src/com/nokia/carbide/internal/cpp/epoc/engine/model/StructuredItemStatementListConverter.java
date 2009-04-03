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
* This extends {@link StatementListConverter} and {@link StructuredItemListConverter}
* to handle converting a list of structured items in a statement list.
*
* @param <ElementType>
* @param <ModelType>
*
*/
package com.nokia.carbide.internal.cpp.epoc.engine.model;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTStatement;
public interface StructuredItemStatementListConverter<ElementType extends IASTStatement, ModelType>  extends
		StructuredItemListConverter<ElementType, ModelType>, StatementListConverter<ElementType, ModelType> {

}
