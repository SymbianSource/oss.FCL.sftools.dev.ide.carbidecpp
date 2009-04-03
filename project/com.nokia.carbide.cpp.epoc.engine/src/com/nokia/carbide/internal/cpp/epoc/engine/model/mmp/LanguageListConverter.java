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

package com.nokia.carbide.internal.cpp.epoc.engine.model.mmp;

import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPLanguage;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ASTFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ModelConverter;

class LanguageListConverter implements ModelConverter<IASTLiteralTextNode, EMMPLanguage> {
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase.ListConverter#elementMatches(java.lang.Object, java.lang.Object)
	 */
	public boolean elementMatches(EMMPLanguage element, EMMPLanguage another) {
		return element.equals(another);
	}
	
	public EMMPLanguage fromNode(IASTLiteralTextNode node) {
		try {
			return EMMPLanguage.fromCode(node.getValue());
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	public IASTLiteralTextNode toNode(EMMPLanguage lang) {
		return ASTFactory.createPreprocessorLiteralTextNode(lang.getCodeString());
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase.ListConverter#allowEmptyStatements()
	 */
	public boolean allowEmptyStatements() {
		return false;
	}

	public IASTStatement createContextStatement(EMMPLanguage model) {
		return null;
	}
}