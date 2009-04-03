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
package com.nokia.carbide.cpp.internal.project.ui.editors.common;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * A viewer filter class that filters based on name pattern.
 */
public class NamePatternFilter extends ViewerFilter {
	
	private String namePattern;
	
	/**
	 * Get the pattern for the receiver.
	 */
	public String getPattern() {
		return namePattern;
	}

	/**
	 * Set the pattern to filter out for the receiver.
	 */
	public void setPattern(String newPattern) {
		namePattern = newPattern;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof String) {
			if (namePattern != null) {
				String elementName = (String)element;
				if (elementName.startsWith(namePattern)) {
					return true;
				}
				return false;
			}
		}
		return true;
	}

}
