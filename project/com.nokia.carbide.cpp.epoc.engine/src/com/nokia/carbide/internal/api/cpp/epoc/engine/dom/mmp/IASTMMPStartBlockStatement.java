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

public interface IASTMMPStartBlockStatement extends IASTMMPBlockStatement {
	public static final String START_TOKEN = "START"; //$NON-NLS-1$
	public static final String END_TOKEN = "END"; //$NON-NLS-1$
	
	/**
	 * Get block type token
	 * "bitmap", "resource", or a platform name
	 * @return
	 */
	IASTLiteralTextNode getBlockType();
	/**
	 * Set block type token
	 * "bitmap", "resource", or a platform name
	 * @return
	 */
	void setBlockType(IASTLiteralTextNode type);
	/**
	 * Get arguments after block type
	 * @return
	 */
	IASTListNode<IASTLiteralTextNode> getBlockArguments();
	/**
	 * Set arguments after block type, cannot be null
	 */
	void setBlockArguments(IASTListNode<IASTLiteralTextNode> arguments);

}
