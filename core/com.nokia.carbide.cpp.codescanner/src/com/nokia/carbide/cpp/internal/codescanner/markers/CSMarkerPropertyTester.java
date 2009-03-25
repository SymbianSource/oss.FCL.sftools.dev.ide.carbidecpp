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

package com.nokia.carbide.cpp.internal.codescanner.markers;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.views.markers.MarkerSupportView;

/**
 * A class to handle testing of CSMarker property.
 */
public class CSMarkerPropertyTester extends PropertyTester {

	private static final String PROPERTY_CSMARKER_TEST = "CSMarkerTest"; //$NON-NLS-1$

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String, java.lang.Object[], java.lang.Object)
	 */
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (!(receiver instanceof MarkerSupportView))
			return false;

		MarkerSupportView view = (MarkerSupportView) receiver;
		if (property.equals(PROPERTY_CSMARKER_TEST))
			return testMarker(view);

		return false;
	}

	/**
	 * Test whether a selected marker is a CodeScanner problem marker.
	 * @param view the view containing the markers
	 * @return true if selected marker is a CodeScanner problem marker; false otherwise
	 */
	@SuppressWarnings("restriction")
	private boolean testMarker(MarkerSupportView view) {
		final IMarker[] selected = view.getSelectedMarkers();
		if (selected.length > 0) {
			try {
				IMarker marker = selected[0];
				if (marker.isSubtypeOf(CSMarker.CS_PROBLEM_MARKER) ||
					marker.isSubtypeOf(CSMarker.CS_MARKER_VARIABLE)) {
					return true;
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

}
