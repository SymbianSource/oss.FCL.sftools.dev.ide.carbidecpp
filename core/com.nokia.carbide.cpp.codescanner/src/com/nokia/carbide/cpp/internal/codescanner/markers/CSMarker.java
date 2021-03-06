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
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;

import com.nokia.carbide.cpp.internal.codescanner.CSPlugin;

/**
 * A class to handle markers generated by the CodeScanner plugin.
 */
public class CSMarker {

	/**
	 * CodeScanner problem marker type. This can be used to recognize those markers
	 * in the workspace that flag problems detected by the CodeScanner plugin.
	 */
	public static final String CS_PROBLEM_MARKER = CSPlugin.PLUGIN_ID + ".CSMarker"; //$NON-NLS-1$
	
	/**
	 * CodeScanner extension to the marker problem markers which may hold a hint on
	 * the variable name that caused the error. Used by the ui to highlight the variable
	 * itself if it can be found.
	 */
	public static final String CS_MARKER_VARIABLE = "problem.variable"; //$NON-NLS-1$
	
	/**
	 * CodeScanner extension to the marker problem markers which may hold 
	 * the path to the workspace external location of the file containing the problem 
	 */
	public static final String CS_MARKER_EXTERNAL_LOCATION = "problem.externalLocation"; //$NON-NLS-1$

	/**
	 * CodeScanner extension to the marker problem markers which may hold 
	 * the name of the CodeScanner rule applicable to the problem 
	 */
	public static final String CS_MARKER_RULE_NAME = "problem.ruleName"; //$NON-NLS-1$

	/**
	 * Remove all CodeScanner markers from a project.
	 * @param currProject - Project containing CodeScanner markers.
	 */
	 public static void removeAllMarkers(IProject currProject) {
		try {
			IWorkspace workspace = currProject.getWorkspace();

			// remove all CodeScanner markers
			IMarker[] markers = currProject.findMarkers(CSMarker.CS_PROBLEM_MARKER, true, IResource.DEPTH_INFINITE);
			if ((markers != null) && (markers.length > 0)) {
				workspace.deleteMarkers(markers);
			}		
		} catch (CoreException e){
			e.printStackTrace();
        }
	 }

}


