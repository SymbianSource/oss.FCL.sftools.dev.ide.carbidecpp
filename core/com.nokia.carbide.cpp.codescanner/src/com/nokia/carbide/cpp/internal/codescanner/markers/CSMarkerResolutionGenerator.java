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

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;

/**
 * A class to generate resolutions for CodeScanner markers.
 */
public class CSMarkerResolutionGenerator implements IMarkerResolutionGenerator {

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IMarkerResolutionGenerator#getResolutions(org.eclipse.core.resources.IMarker)
	 */
	public IMarkerResolution[] getResolutions(IMarker marker) {
		try {
			if (marker.isSubtypeOf(CSMarker.CS_PROBLEM_MARKER) ||
				marker.isSubtypeOf(CSMarker.CS_MARKER_VARIABLE)) {
				return new IMarkerResolution[] {
					new CSMarkerResolution(marker)
				};
			}
		} catch (CoreException e) {
			return new IMarkerResolution[0];
		}
		return new IMarkerResolution[0];
	}

}
