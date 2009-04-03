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

import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.nokia.carbide.cpp.internal.codescanner.CSBuilder;
import com.nokia.carbide.cpp.internal.codescanner.CSPlugin;
import com.nokia.carbide.cpp.internal.codescanner.CSScanner;
import com.nokia.carbide.cpp.internal.featureTracker.FeatureUseTrackerConsts;
import com.nokia.carbide.cpp.internal.featureTracker.FeatureUseTrackerPlugin;

/**
 * A class to handle actions associated with the "Run CodeScanner" pop-up menu.
 */
public class CSScanAction implements IObjectActionDelegate, IWorkbenchWindowActionDelegate {

	// IDs defined in plugin.xml
	public static final String CS_SCAN_POP_UP_ID = CSPlugin.PLUGIN_ID + ".CSScanAction";
	public static final String CS_SCAN_INF_POP_UP_ID = CSPlugin.PLUGIN_ID + ".CSScanActionOnINF";
	public static final String CS_SCAN_MMP_POP_UP_ID = CSPlugin.PLUGIN_ID + ".CSScanActionOnMMP";
	public static final String CS_SCAN_PROJECT_ID = CSPlugin.PLUGIN_ID + ".CSScanProjectAction";

	// Maximum argument size
	public static final long CS_MAX_ARG_LENGTH = 1024;

	// private members
	private ISelection selection;

	/**
	 * Constructor for CSScanAction.
	 */
	public CSScanAction() {
		super();
	}

	/**
     * Dispose this action delegate and unhook any references
     * to itself so that garbage collection can occur.
     */
	public void dispose() {
	}

    /**
     * Initialize this action delegate with the workbench window it will work in.
     * @param window the window that provides the context for this delegate
     */
	public void init(IWorkbenchWindow window) {
	}

	/**
	 * Perform any work needed when the action has been triggered.
	 */
	public void run(IAction action) {
		FeatureUseTrackerPlugin.getFeatureUseProxy().startUsingFeature(FeatureUseTrackerConsts.CARBIDE_CODESCANNER);
		if (action.getId().equals(CS_SCAN_POP_UP_ID)){
			// scan the selected resource
			handleCSScanAction();
		}
		else if (action.getId().equals(CS_SCAN_INF_POP_UP_ID)){
			// scan the selected INF file
			handleCSScanAction();
		}
		else if (action.getId().equals(CS_SCAN_MMP_POP_UP_ID)){
			// scan the selected MMP file
			handleCSScanAction();
		}
		else if (action.getId().equals(CS_SCAN_PROJECT_ID)){
			// scan the selected project
			handleCSScanProjectAction();
		}
		FeatureUseTrackerPlugin.getFeatureUseProxy().stopUsingFeature(FeatureUseTrackerConsts.CARBIDE_CODESCANNER);
	}

	/**
	 * Perform any work needed when the selection in the workbench has changed.
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	/**
	 * Set the active part for this delegate class.
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	/**
	 * Return the resource associated with an object.
	 * @param element - object in question
	 * @return associated resource if there is one
	 */
	private IResource getResourceFromObject(Object element) {
		if (element instanceof IResource) {
			return (IResource)element;
		} else if (element instanceof IAdaptable) {
			return (IResource)((IAdaptable)element).getAdapter(IResource.class);
		}
		return null;
	}

	/**
	 * Run CodeScanner on selected resource.
	 */
	@SuppressWarnings("unchecked")
	private void handleCSScanAction(){
		if (selection != null && selection instanceof IStructuredSelection) {
			Iterator iter = ((IStructuredSelection)selection).iterator();
			while (iter.hasNext()) {
				Object selItem = iter.next();
				IResource resource = getResourceFromObject(selItem);
				if (resource != null) {
					// find the project associated with the selected resource
					IProject project = resource.getProject();
					if (project == null){
						return;
					}

					// add CodeScanner to project builder list
					CSBuilder.addBuilderToProject(project);

					// figure out the type of resource to be scanned
					CSScanner.ScanType scanType = CSScanner.ScanType.scan_other;
			        String extension = resource.getFileExtension();
			        if (extension != null) {
			        	if (extension.toLowerCase().equals("inf")) {
			        		scanType = CSScanner.ScanType.scan_INF;
			        	}
			        	else if (extension.toLowerCase().equals("mmp")) {
			        		scanType = CSScanner.ScanType.scan_MMP;
			        	}
			        }

					IPath filePath = resource.getLocation();
					CSScanner scanner = new CSScanner();
					scanner.scanFile(project, filePath, scanType);
				}
			}
		}
	}

	/**
	 * Run CodeScanner on the project associated with the selected resource.
	 */
	@SuppressWarnings("unchecked")
	private void handleCSScanProjectAction(){
		if (selection != null && selection instanceof IStructuredSelection) {
			Iterator iter = ((IStructuredSelection)selection).iterator();
			while (iter.hasNext()) {
				Object selItem = iter.next();
				IResource resource = getResourceFromObject(selItem);
				if (resource != null) {
					// find the project associated with the selected resource
					IProject project = resource.getProject();
					if (project == null){
						return;
					}

					// add CodeScanner to project builder list
					CSBuilder.addBuilderToProject(project);

					IPath filePath = project.getLocation();
					CSScanner scanner = new CSScanner();
					scanner.scanFile(project, filePath, CSScanner.ScanType.scan_other);
				}
			}
		}
	}

}
