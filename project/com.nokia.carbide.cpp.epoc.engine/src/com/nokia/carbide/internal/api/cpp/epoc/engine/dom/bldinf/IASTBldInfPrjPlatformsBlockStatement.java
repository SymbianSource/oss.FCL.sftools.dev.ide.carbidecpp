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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;

/**
 * This block encompasses the PRJ_PLATFORMS statement and its contents.  
 * The platforms are tokens and may span multiple lines.
 *
 */
public interface IASTBldInfPrjPlatformsBlockStatement extends 
		IASTBldInfBlockStatement<IASTLiteralTextNode> {
	/** Access/modify platform list; no error checking */
	IASTListNode<IASTLiteralTextNode> getList();
	/** Set list, may not be null */
	void setList(IASTListNode<IASTLiteralTextNode> list);

}
