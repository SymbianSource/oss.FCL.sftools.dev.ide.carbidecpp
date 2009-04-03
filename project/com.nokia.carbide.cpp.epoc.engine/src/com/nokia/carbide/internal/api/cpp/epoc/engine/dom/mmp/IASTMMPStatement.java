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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTStatement;

/**
 * A basic MMP statement.
 * <p>
 * All the MMP statements will derive from this interface.
 * <p>
 * The regions for these statements will be clamped to the full line
 * containing them.
 * 
 * 
 */
public interface IASTMMPStatement extends IASTStatement {
	/** Get keyword -- first token on line, never null.
	 * This may be in any case the user happened to type;
	 * use String#equalsIgnoreCase() to compare. */ 
	String getKeywordName();
	
	/** Get the IMMPSourcePathDependentContext interface for the 
	 * statement, if any.
	 * @return IMMPSourcePathDependentContext or null
	 */
	IMMPSourcePathDependentContext getSourcePathDependentContext();
}
