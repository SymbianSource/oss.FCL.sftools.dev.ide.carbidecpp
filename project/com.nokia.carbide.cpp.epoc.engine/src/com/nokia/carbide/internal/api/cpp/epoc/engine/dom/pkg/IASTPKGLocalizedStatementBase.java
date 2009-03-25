/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListArgumentStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;

/**
 * 
 * Use list argument for a list of language variants
 * 
 */
public interface IASTPKGLocalizedStatementBase extends IASTPKGStatement,
		IASTListArgumentStatement {
	/**
	 * Get the argument list (which is modifiable), never null
	 */
	IASTListNode<IASTLiteralTextNode> getLanguageVariants();

	/**
	 * Set all arguments, may not be null
	 * 
	 * @param arguments
	 */
	void setLanguageVariants(IASTListNode<IASTLiteralTextNode> languageVariants);

}
