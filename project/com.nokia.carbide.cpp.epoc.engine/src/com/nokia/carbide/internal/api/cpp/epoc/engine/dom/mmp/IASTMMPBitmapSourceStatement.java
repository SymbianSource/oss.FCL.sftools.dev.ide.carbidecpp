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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;

/**
 * An element of the START BITMAP statement.
 * <p>
 * In this list argument statement, the #getArgument() list 
 * contains only the filenames.
 * The format is controlled separately.
 *
 */
public interface IASTMMPBitmapSourceStatement extends IASTMMPListArgumentStatementBase {
	public static final String SOURCE_KEYWORD = "SOURCE"; //$NON-NLS-1$
	
	/**
	 * Get the bitmap format, e.g. "c12,1"
	 * @return
	 */
	IASTLiteralTextNode getFormat();
	/**
	 * Set the bitmap format, e.g. "c12,1"
	 * @return
	 */
	void setFormat(IASTLiteralTextNode format);
}
