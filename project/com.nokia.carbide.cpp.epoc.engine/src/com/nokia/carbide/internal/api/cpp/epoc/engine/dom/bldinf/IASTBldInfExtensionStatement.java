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

package com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;

/**
 * This is a single line inside the PRJ_EXTENSIONS / start ... end block
 *
 */
public interface IASTBldInfExtensionStatement extends IASTBldInfStatement {
	/** Get the keyword, may not be null */
	IASTLiteralTextNode getKeyword();
	/** Set keyword, may not be null */
	void setKeyword(IASTLiteralTextNode keyword);
	/** Get arguments, may be null */
	IASTListNode<IASTLiteralTextNode> getArguments();
	/** Set arguments, may be null */
	void setArguments(IASTListNode<IASTLiteralTextNode> args);

}
