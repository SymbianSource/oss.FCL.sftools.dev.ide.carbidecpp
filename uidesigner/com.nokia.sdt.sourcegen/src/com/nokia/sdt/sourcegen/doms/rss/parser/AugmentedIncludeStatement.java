/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.sourcegen.doms.rss.parser;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTPreprocessorStatement;

import org.eclipse.core.runtime.IPath;

/**
 * 
 *
 */
public class AugmentedIncludeStatement extends ASTPreprocessorStatement implements IASTPreprocessorStatement {

	private IASTPreprocessorIncludeStatement include;
	private IPath path;

	/**
	 * @param statement
	 */
	public AugmentedIncludeStatement(IASTPreprocessorIncludeStatement include, IPath path) {
		this.include = include;
		this.path = path;
	}
	
	/**
	 * 
	 */
	public AugmentedIncludeStatement(AugmentedIncludeStatement other) {
		super(other);
		this.include = other.include;
		this.path = other.path;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new AugmentedIncludeStatement(include, path);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		return NO_CHILDREN;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		// nothing
	}
	
	/**
	 * @return the path
	 */
	public IPath getPath() {
		return path;
	}
	
	/**
	 * @return the include
	 */
	public IASTPreprocessorIncludeStatement getInclude() {
		return include;
	}
}
