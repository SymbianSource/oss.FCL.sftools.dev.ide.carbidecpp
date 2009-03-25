/*
* Copyright (c) 2008-2009 Nokia Corporation and/or its subsidiary(-ies).
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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorIncludeStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorFilter;

import org.eclipse.core.runtime.IPath;

/**
 * Default handling for outgoing preprocessor nodes is to throw away
 * directives.  (Note: #error/#warning are separately converted to
 * IASTProblemTopLevelNodes.)
 *
 */
public class DefaultPreprocessorFilter implements IPreprocessorFilter {

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorFilter#handleDirective(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorStatement)
	 */
	public IASTPreprocessorStatement filterDirective(
			IASTPreprocessorStatement directive) {
		// toss away all directives
		return null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorFilter#handleIncludeEntry(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorIncludeStatement, org.eclipse.core.runtime.IPath)
	 */
	public IASTPreprocessorStatement filterIncludeEntry(
			IASTPreprocessorIncludeStatement include, IPath path) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorFilter#handleIncludeExit()
	 */
	public IASTPreprocessorStatement filterIncludeExit() {
		return null;
	}
}
