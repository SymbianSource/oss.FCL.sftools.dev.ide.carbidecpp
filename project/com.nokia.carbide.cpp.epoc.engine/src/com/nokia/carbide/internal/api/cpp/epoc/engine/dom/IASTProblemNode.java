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

package com.nokia.carbide.internal.api.cpp.epoc.engine.dom;

import com.nokia.cpp.internal.api.utils.core.*;

/**
 * This node represents a problem in the DOM.  It has the type
 * expected by the syntax (i.e. node, expression, statement).
 *
 */
public interface IASTProblemNode extends IASTNode {
	IASTProblemNode[] NO_PROBLEMS = new IASTProblemNode[0];
	
	/** Get the tokens making up the problem (may be null). */
	IASTPreprocessorTokenStream getTokenStream();
	
	/** Set the tokens making up the problem (may be null). */
	void setTokenStream(IASTPreprocessorTokenStream tokenStream);
	
	/** Get the message describing the problem */
	IMessage getMessage();
	
	/** Set the message describing the problem.
	 */
	void setMessage(IMessage message);
}
