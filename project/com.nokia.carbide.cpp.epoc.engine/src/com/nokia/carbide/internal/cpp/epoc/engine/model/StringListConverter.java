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

package com.nokia.carbide.internal.cpp.epoc.engine.model;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ASTFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode.EStyle;

public class StringListConverter implements
		ModelConverter<IASTLiteralTextNode, String> {

	private EStyle style;

	public StringListConverter(EStyle style) {
		this.style = style;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase.ListConverter#elementMatches(java.lang.Object,
	 *      java.lang.Object)
	 */
	public boolean elementMatches(String element, String another) {
		return element.equals(another);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.bldinf.BldInfView.ListConverter#fromNode(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode)
	 */
	public String fromNode(IASTLiteralTextNode node) {
		return node.getValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.bldinf.BldInfView.ListConverter#toNode(java.lang.Object)
	 */
	public IASTLiteralTextNode toNode(String elementObj) {
		return ASTFactory.createLiteralTextNode(style, elementObj.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.bldinf.BldInfView.ListConverter#allowEmptyStatements()
	 */
	public boolean allowEmptyStatements() {
		return false;
	}
	
	public IASTStatement createContextStatement(String model) {
		return null;
	}
}