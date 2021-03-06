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

/**
 * Base for statements with a list of literal text arguments
 *
 */
public interface IASTListArgumentStatement extends IASTStatement,
		IASTListHolder<IASTLiteralTextNode>{
	/**
	 * Get the argument list (which is modifiable), never null
	 */
	IASTListNode<IASTLiteralTextNode> getArguments();
	/**
	 * Set all arguments, may not be null
	 * @param arguments
	 */
	void setArguments(IASTListNode<IASTLiteralTextNode> arguments);
	
	
}
