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
 * An unknown statement.  Used for any statement whose keyword
 * is unrecognized.
 *
 */
public interface IASTMMPUnknownStatement extends IASTMMPKeywordStatement {
	/** Get all the arguments. */
	IASTListNode<IASTLiteralTextNode> getArguments();
	/** Set all the arguments. */
	void setArguments(IASTListNode<IASTLiteralTextNode> arguments);
}
