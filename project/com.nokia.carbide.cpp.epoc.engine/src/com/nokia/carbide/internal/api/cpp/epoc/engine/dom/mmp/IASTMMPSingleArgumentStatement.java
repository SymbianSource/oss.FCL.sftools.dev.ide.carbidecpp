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
 * A single-argument statement.
 *
 */
public interface IASTMMPSingleArgumentStatement extends IASTMMPKeywordStatement {
	/**
	 * Get the single argument (may contain spaces)
	 * @return
	 */
	IASTLiteralTextNode getArgument();
	/**
	 * Set the single argument
	 * @return
	 */
	void setArgument(IASTLiteralTextNode argument);

}
