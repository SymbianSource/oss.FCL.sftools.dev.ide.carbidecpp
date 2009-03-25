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

package com.nokia.carbide.cpp.internal.codescanner.popup.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.nokia.carbide.cpp.internal.codescanner.CSPlugin;
import com.nokia.carbide.cpp.internal.codescanner.markers.CSMarker;

/**
 * A class to handle actions associated with the "Delete CodeScanner Markers" pop-up menu.
 */
public class CSDeleteMarkerAction implements IObjectActionDelegate {

	private IStructuredSelection  selection;
	
	// IDs definied in plugin.xml
	public static final String CS_DELETE_MARKER_POP_UP_ID = CSPlugin.PLUGIN_ID + ".CSDeleteMarkers";
	
	/**
	 * Constructor for CSDeleteMarkerAction.
	 */
	public CSDeleteMarkerAction() {
		super();
	}

	/**
	 * Set the active part for this delegate class.
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	/**
	 * Perform any work needed when the action has been triggered.
	 */
	public void run(IAction action) {
		if (action.getId().equals(CS_DELETE_MARKER_POP_UP_ID)){
			// delete any selected CodeScanner markers
			handleCSDeleteMarkerAction(action);
		}
	}

	/**
	 * Perform any work needed when the selection in the workbench has changed.
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		boolean enable = false;
		if (selection instanceof IStructuredSelection) {
			Object object = ((IStructuredSelection) selection).getFirstElement();
			if (object instanceof IMarker) {
				try {
					// enable this pop-up menu for CodeScanner markers only
					IMarker marker = (IMarker) object;
					if (marker.isSubtypeOf(CSMarker.CS_PROBLEM_MARKER) ||
						marker.isSubtypeOf(CSMarker.CS_MARKER_VARIABLE)) {
						enable = true;
					}
					this.selection = (IStructuredSelection)selection;
					action.setEnabled(enable);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Remove any selected CodeScanner markers.
	 */
	@SuppressWarnings("unchecked")
	private void handleCSDeleteMarkerAction(IAction action){
		if (selection != null) {
			if (selection.isEmpty()) {
				return;
			}
			try {
				// look for CodeScanner markers
				List list = selection.toList();
				List<IMarker> listMarkers = new ArrayList<IMarker>();
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					IMarker marker = (IMarker)iterator.next();
					if (marker.isSubtypeOf(CSMarker.CS_PROBLEM_MARKER) ||
						marker.isSubtypeOf(CSMarker.CS_MARKER_VARIABLE)) {
						listMarkers.add(marker);
					}
				}

				// return if no CodeScanner markers is selected
				if (listMarkers.isEmpty()) {
					return;
				}

				// remove all CodeScanner markers selected
				IMarker[] markers = new IMarker[listMarkers.size()];
				listMarkers.toArray(markers);
				ResourcesPlugin.getWorkspace().deleteMarkers(markers);
				selection = null;
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}

}
