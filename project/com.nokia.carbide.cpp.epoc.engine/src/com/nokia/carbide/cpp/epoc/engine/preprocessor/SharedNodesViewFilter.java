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

package com.nokia.carbide.cpp.epoc.engine.preprocessor;


/**
 * This filter provides only the shared settings, i.e.
 * those outside of conditional #if statements.
 *
 */
public class SharedNodesViewFilter implements IViewFilter {

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return obj instanceof SharedNodesViewFilter;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return 3;
	}
	
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IViewFilter#combineBranches()
	 */
	public boolean combineBranches() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IViewFilter#evaluateConditionalStatements()
	 */
	public boolean evaluateConditionalStatements() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IViewFilter#evaluateUnconditionalStatements()
	 */
	public boolean evaluateUnconditionalStatements() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IViewFilter#expandVariantMacros()
	 */
	public boolean expandVariantMacros() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IViewFilter#invertSuccess()
	 */
	public boolean invertSuccess() {
		return false;
	}

}
