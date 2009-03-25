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
 * An entry in the PRJ_MMPFILES block.
 *
 */
public interface IASTBldInfMakMakeStatement extends IASTBldInfStatement {
	/** Get the path of the file (never null) */
	IASTLiteralTextNode getPath();
	/** Set the path of the file (never null) */
	void setPath(IASTLiteralTextNode path);
	/** access/modify list of attributes for statement */
	IASTListNode<IASTLiteralTextNode> getAttributes();
	/** access/modify list of attributes for statement */
	void setAttributes(IASTListNode<IASTLiteralTextNode> list);

}
