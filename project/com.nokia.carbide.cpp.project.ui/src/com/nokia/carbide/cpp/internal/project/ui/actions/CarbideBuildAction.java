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
package com.nokia.carbide.cpp.internal.project.ui.actions;

import org.eclipse.core.commands.*;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.commands.ActionHandler;
import org.eclipse.ui.*;
import org.eclipse.ui.actions.BuildAction;

/**
 * This class exists to allow CTRL+B to build the current project to be decoupled from the platform's
 * build project action, because the latter has enablement bugs (shown disabled when should be enabled)
 * that will probably not be addressed in the immediate future. The idea is that in the com.nokia.carbide.cpp 
 * plugin, we now create a binding to an internal command id (com.nokia.carbide.cpp.buildProject) 
 * rather than the platform's build project command id. Then in this plugin,
 * a command is declared to handle it with this class as the handler.
 *  
 * This class wraps the same object as the platform's build project action, and enhances enablement
 * such that if a cdt editor is the active part, the command is enabled.
 * The decision about which projects to build and the actual building is delegated to the original build action.
 */
public class CarbideBuildAction implements IHandler {
	private ActionHandler buildActionHandler;

	public CarbideBuildAction() {
	}

	/**
	 * Create the action handler lazily, since it requires a non-null shell at construction time,
	 * and by the time the user invokes this command, we should be certain that the workbench is fully open.
	 * @return ActionHandler
	 */
	private ActionHandler getBuildAction() {
		if (buildActionHandler == null) {
			IWorkbench workbench = PlatformUI.getWorkbench();
			if (workbench == null)
				return null;
			IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
			if (window == null)
				return null;
			IAction action = new BuildAction(window, IncrementalProjectBuilder.INCREMENTAL_BUILD);
			buildActionHandler = new ActionHandler(action);
		}
		
		return buildActionHandler;
	}

	public boolean isEnabled() {
		if (getBuildAction() == null)
			return false;
		
		// allow the build action to check enablement
		boolean enabled = getBuildAction().isEnabled();
		if (!enabled) {
			// if showing disabled (potentially due to the bug), check the active editor
			IWorkbench workbench = PlatformUI.getWorkbench();
			if (workbench != null) {
				IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
				if (workbenchWindow != null) {
					IWorkbenchPage activePage = workbenchWindow.getActivePage();
					if (activePage != null) {
						IWorkbenchPart activePart = activePage.getActivePart();
						// enabled if active part is an editor and it is a cdt editor
						enabled = activePart instanceof IEditorPart && 
							activePart.getSite().getId().contains("org.eclipse.cdt"); //$NON-NLS-1$
					}
				}
			}
		}
		return enabled;
	}
	
	public boolean isHandled() {
		if (getBuildAction() == null)
			return false;
		
		return getBuildAction().isHandled();
	}
	
	public void addHandlerListener(IHandlerListener handlerListener) {
		if (getBuildAction() != null)
			getBuildAction().addHandlerListener(handlerListener);
	}
	
	public void removeHandlerListener(IHandlerListener handlerListener) {
		if (getBuildAction() != null)
			getBuildAction().removeHandlerListener(handlerListener);
	}
	
	public void dispose() {
		if (getBuildAction() != null)
			getBuildAction().dispose();
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		if (getBuildAction() != null)
			return getBuildAction().execute(event);
		
		return null;
	}

}
