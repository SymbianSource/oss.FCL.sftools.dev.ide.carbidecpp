/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.preprocessor;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTExpression;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;



public class ConditionalNode implements IConditionalNode {

	private IASTExpression condition;
	private IASTNode node;

	/**
	 * 
	 */
	public ConditionalNode(IASTNode node, IASTExpression condition) {
		this.condition = condition;
		this.node = node;
	}
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IConditionalNode#getCondition()
	 */
	public IASTExpression getCondition() {
		return condition;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IConditionalNode#getNode()
	 */
	public IASTNode getNode() {
		return node;
	}

}
