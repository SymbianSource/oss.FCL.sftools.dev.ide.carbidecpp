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
* 
*/
package com.nokia.carbide.cpp.debug.kernelaware.ui;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.dialogs.SearchPattern;

public class ItemFilter extends ViewerFilter {

	private SearchPattern searchPattern;

	public ItemFilter() {
		searchPattern = new SearchPattern();
		searchPattern.setPattern(""); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
	 *      java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		return match(element.toString());
	}

	public boolean match(String str) {
		return searchPattern.matches(str);
	}
	
	public void setPattern(String pattern) {
		searchPattern.setPattern(pattern);
	}
}
