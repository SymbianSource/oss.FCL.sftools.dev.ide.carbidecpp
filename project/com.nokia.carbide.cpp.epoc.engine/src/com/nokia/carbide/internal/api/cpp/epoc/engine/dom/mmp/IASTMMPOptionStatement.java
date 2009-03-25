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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;

/**
 * Covers “option”, “linkeroption”, and “option_replace”
 *
 */
public interface IASTMMPOptionStatement extends IASTMMPKeywordStatement {
	public static final String OPTION_KEYWORD = "OPTION"; //$NON-NLS-1$
	
	/**
	 * Get compiler/platform name
	 * @return
	 */
	IASTLiteralTextNode getCompiler();
	/**
	 * Set compiler name
	 * @param file
	 */
	void setCompiler(IASTLiteralTextNode compiler);
	/**
	 * Get options
	 * @return
	 */
	IASTListNode<IASTLiteralTextNode> getOptions();  
	/**
	 * Set compiler options
	 * @param file
	 */
	void setOptions(IASTListNode<IASTLiteralTextNode> options);

}
