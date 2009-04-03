/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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
* A wrapped AcceptedNodesViewFilter whose identity is tied to a particular build context.
*
*
*/
package com.nokia.carbide.cdt.builder;

import com.nokia.carbide.cpp.epoc.engine.preprocessor.AcceptedNodesViewFilter;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;

public class AcceptedBuildContextNodesViewFilter extends
		AcceptedNodesViewFilter {
	private final ISymbianBuildContext context;

	/**
	 * @param context
	 */
	public AcceptedBuildContextNodesViewFilter(ISymbianBuildContext context) {
		this.context = context;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Accepted nodes filter for " + context.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((context == null) ? 0 : context.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final AcceptedBuildContextNodesViewFilter other = (AcceptedBuildContextNodesViewFilter) obj;
		if (context == null) {
			if (other.context != null)
				return false;
		} else if (!context.equals(other.context))
			return false;
		return true;
	}
	
}