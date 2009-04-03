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

package com.nokia.carbide.internal.cpp.epoc.engine.dom.bsf;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorTokenStream;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bsf.IASTBSFProblemStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTProblemTopLevelNode;
import com.nokia.cpp.internal.api.utils.core.*;


public class ASTBSFProblemStatement extends ASTProblemTopLevelNode implements
		IASTBSFProblemStatement {

	/**
	 * @param tokenStream
	 * @param message
	 */
	public ASTBSFProblemStatement(IASTPreprocessorTokenStream tokenStream, IMessage message) {
		super(tokenStream, message);
	}

	/**
	 * @param statement
	 */
	public ASTBSFProblemStatement(ASTBSFProblemStatement statement) {
		super(statement);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTStatement#getKeywordName()
	 */
	public String getKeywordName() {
		return null;
	}

}
