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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;

/**
 * A statement with a variant keyword.
 *
 */
public interface IASTKeywordStatement extends IASTStatement {
	/** Get the flag keyword, never null */
	IASTLiteralTextNode getKeyword();
	/** Set the flag keyword, never null */
	void setKeyword(IASTLiteralTextNode keyword);
}
