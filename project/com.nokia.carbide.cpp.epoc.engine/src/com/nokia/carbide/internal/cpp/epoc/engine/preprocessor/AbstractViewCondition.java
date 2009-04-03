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


public abstract class AbstractViewCondition implements IViewCondition {

	protected boolean inverted;

	public boolean equals(Object obj) {
		if (!(obj instanceof AbstractViewCondition))
			return false;
		return ((AbstractViewCondition) obj).inverted == inverted;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IViewCondition#inverted()
	 */
	public boolean inverted() {
		return inverted;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IViewCondition#setInverted(boolean)
	 */
	public void setInverted(boolean inverted) {
		this.inverted = inverted;
	}
}
