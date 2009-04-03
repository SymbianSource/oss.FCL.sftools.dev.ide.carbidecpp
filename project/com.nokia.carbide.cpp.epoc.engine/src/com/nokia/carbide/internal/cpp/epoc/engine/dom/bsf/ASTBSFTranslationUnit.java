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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bsf.IASTBSFTranslationUnit;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTTranslationUnit;


public class ASTBSFTranslationUnit extends ASTTranslationUnit implements
		IASTBSFTranslationUnit {

	public ASTBSFTranslationUnit(
			IASTListNode<? extends IASTTopLevelNode> nodes) {
		super(nodes);
	}

	public ASTBSFTranslationUnit(ASTBSFTranslationUnit unit) {
		super(unit);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTBSFTranslationUnit))
			return false;
		return super.equalValue(obj);
	}


}
