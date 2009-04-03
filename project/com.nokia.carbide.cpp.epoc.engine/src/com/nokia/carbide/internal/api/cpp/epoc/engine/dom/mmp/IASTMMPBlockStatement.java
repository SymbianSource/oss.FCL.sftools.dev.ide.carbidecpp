/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListHolder;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode;

/**
 * List of statements making up a unit.  
 * 
 */
public interface IASTMMPBlockStatement extends IASTMMPStatement, IASTListHolder<IASTMMPStatement> {
	/**
	 * Get the statement list (modifiable)
	 * @return
	 */
	IASTListNode<IASTMMPStatement> getStatements();
	
	/**
	 * Set all the statements
	 * @param statements
	 */
	void setStatements(IASTListNode<IASTMMPStatement> statements);
}
