/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.preprocessor;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;

public class IfViewRegion extends ConditionalBlock {

	/**
	 * 
	 */
	public IfViewRegion() {
		super();
	}
	/**
	 * @param start
	 * @param end
	 */
	public IfViewRegion(IASTNode start, IASTNode end) {
		super(null, start, null, end, null);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.ViewRegion#toString()
	 */
	@Override
	public String toString() {
		return "region:if, " + super.toString(); //$NON-NLS-1$
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.ConditionalBlock#getIfDepth()
	 */
	@Override
	public int getIfDepth() {
		if (parent != null)
			return parent.getIfDepth() + 1; 
		return 1;
	}
}
