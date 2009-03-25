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
 * UID statement
 *
 */
public interface IASTMMPUidStatement extends IASTMMPStatement {
	public static final String UID_KEYWORD = "UID"; //$NON-NLS-1$
	
	/**
	 * Get UID2, may be null
	 * @return
	 */
	IASTLiteralTextNode getUid2(); 
	/**
	 * Set UID2, may be null
	 */
	void setUid2(IASTLiteralTextNode uid2);
	/**
	 * Get UID3, may be null
	 * @return
	 */
	IASTLiteralTextNode getUid3();
	/**
	 * Set UID3, may be null
	 */
	void setUid3(IASTLiteralTextNode uid3);

}
