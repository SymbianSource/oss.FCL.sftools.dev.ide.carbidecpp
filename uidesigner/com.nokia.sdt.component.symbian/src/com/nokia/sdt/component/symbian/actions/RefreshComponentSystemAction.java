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
package com.nokia.sdt.component.symbian.actions;

import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.editor.IDesignerDataModelEditor;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.*;

/** Action to refresh the component system 
 * (check for added/removed *.component files) 
 * using @see IComponentLibrary.refresh().
 */
public class RefreshComponentSystemAction implements
        IWorkbenchWindowActionDelegate {
    private IWorkbenchWindow workbenchWindow;
     IWorkbenchPart workbenchPart;
    
    /**
     * The constructor.
     */
    public RefreshComponentSystemAction() {
    }

    /**
     * We will cache window object in order to be able to provide parent shell
     * for the message dialog.
     * 
     * @see IWorkbenchWindowActionDelegate#init
     */
    public void init(IWorkbenchWindow window) {
        this.workbenchWindow = window;
    }

    /**
     * The action has been activated. The argument of the method represents the
     * 'real' action sitting in the workbench UI.
     * 
     * @see IWorkbenchWindowActionDelegate#run(IAction)
     */
    public void run(IAction action) {
    	// if an editor is not active, try to activate frontmost editor 
    	// to avoid properties view from being active - 
    	// some of the property sheet entries may end up with stale objects before
    	// they are completely updated
    	IWorkbenchPage page = workbenchWindow.getActivePage();
		IWorkbenchPart part = page.getActivePart();
    	if (part.getAdapter(IDesignerDataModelEditor.class) == null) {
    		IEditorPart activeEditor = page.getActiveEditor();
    		if (activeEditor != null)
    			page.activate(activeEditor);
    	}
        final ComponentSystemPlugin plugin = ComponentSystemPlugin.getDefault();
        if (plugin != null) {
        	plugin.refreshComponents();
        }
    }

    /**
     * Selection in the workbench has been changed. We can change the state of
     * the 'real' action here if we want, but this can only happen after the
     * delegate has been created.
     * 
     * @see IWorkbenchWindowActionDelegate#selectionChanged(IAction, ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
        IWorkbenchPage workbenchPage= workbenchWindow.getActivePage();
        if (workbenchPage != null)
            workbenchPart = workbenchPage.getActivePart();
        else
            workbenchPart = null;
    }

    /**
     * We can use this method to dispose of any system resources we previously
     * allocated.
     * 
     * @see IWorkbenchWindowActionDelegate#dispose
     */
    public void dispose() {
    }


}