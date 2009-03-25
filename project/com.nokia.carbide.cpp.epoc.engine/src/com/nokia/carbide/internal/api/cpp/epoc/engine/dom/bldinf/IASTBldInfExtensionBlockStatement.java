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
 * The start ... end statement within an PRJ_EXTENSIONS block.
 *
 */
public interface IASTBldInfExtensionBlockStatement extends
		IASTBldInfBlockStatement<IASTBldInfExtensionStatement> {
	/** Get the arguments following START, may not be null */
	IASTListNode<IASTLiteralTextNode> getArguments();
	/** Set the arguments following START, may not be null */
	void setArguments(IASTListNode<IASTLiteralTextNode> args);

}
