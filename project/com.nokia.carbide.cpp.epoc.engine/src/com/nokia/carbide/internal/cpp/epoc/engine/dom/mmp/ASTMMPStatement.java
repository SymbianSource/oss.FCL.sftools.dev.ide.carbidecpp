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

package com.nokia.carbide.internal.cpp.epoc.engine.dom.mmp;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPSingleArgumentStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IMMPSourcePathDependentContext;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTStatement;
import com.nokia.cpp.internal.api.utils.core.*;


public abstract class ASTMMPStatement extends ASTStatement implements
		IASTMMPStatement, IMMPSourcePathDependentContext {

	
	private IMMPSourcePathDependentContext sourcePathDependentContext;
	private IASTMMPSingleArgumentStatement sourcePathStatement;

	public ASTMMPStatement() {
		super();
	}
	
	/**
	 * @param statement
	 */
	public ASTMMPStatement(ASTMMPStatement statement) {
		super(statement);
		if (statement.sourcePathDependentContext != null) {
			this.sourcePathDependentContext = this;
			this.sourcePathStatement = statement.sourcePathStatement;
		}
	}
	
	/** Call if the instance of a statement depends on SOURCEPATH.
	 */
	protected void establishSourcePathDependence() {
		this.sourcePathDependentContext = this;
	}
	
	public IMMPSourcePathDependentContext getSourcePathDependentContext() {
		return sourcePathDependentContext;
	}
	
	public IASTMMPSingleArgumentStatement getSourcePathStatement() {
		return sourcePathStatement;
	}

	public void setSourcePathStatement(IASTMMPSingleArgumentStatement stmt) {
		Check.checkArg(stmt == null || stmt.getKeywordName().equalsIgnoreCase("SOURCEPATH")); //$NON-NLS-1$
		this.sourcePathStatement = stmt;
	}
	
	
}
