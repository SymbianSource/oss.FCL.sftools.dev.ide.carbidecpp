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

package com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp;

import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;

/**
 * Interface used for nodes that depend on sourcepaths.
 *
 */
public interface IMMPSourcePathDependentContext {
	/** This setting for {@link #getSourcePathStatement()} indicates the SOURCEPATH is implicit (e.g. current file-relative) */
	static final IASTMMPSingleArgumentStatement DEFAULT_SOURCEPATH_STATEMENT = ASTMMPFactory.createMMPSingleArgumentStatement(EMMPStatement.SOURCEPATH.toString(), "."); //$NON-NLS-1$

	/** Get the source path statement we depend on -- may be multiply referenced 
	 *	@return SOURCEPATH statement or null 
	 */
	IASTMMPSingleArgumentStatement getSourcePathStatement();
	/** Set the source path statement we depend on -- may be multiply referenced
	 * @param stmt SOURCEPATH statement or null 
	 */
	void setSourcePathStatement(IASTMMPSingleArgumentStatement stmt);

}
