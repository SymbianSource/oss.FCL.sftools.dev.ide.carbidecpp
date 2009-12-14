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
/**
 * 
 */
package com.nokia.sdt.component.symbian.actions;

import com.nokia.cpp.internal.api.utils.ui.BrowseDialogUtils;
import com.nokia.sdt.component.ComponentSystemException;
import com.nokia.sdt.component.symbian.ComponentSystemPlugin;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/** Action to add a new component library to the component system.
 * A file dialog asks the user to specify the plugin directory
 * defining an appropriate componentLibrary extension.
 * 
 * 
 * 
 */
public class AddComponentLibraryAction implements
        IWorkbenchWindowActionDelegate {

    private IWorkbenchWindow window;

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
     */
    public void dispose() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
     */
    public void init(IWorkbenchWindow window) {
        this.window = window;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run(IAction action) {

        try {
            DirectoryDialog dlg = new DirectoryDialog(window.getShell());
            dlg.setMessage("Add a component library plugin"); //$NON-NLS-1$
            BrowseDialogUtils.initializeFrom(dlg, "c:/work/dynamicplugins/com.nokia.uidesigner.user0.componentlibrary"); //$NON-NLS-1$
            dlg.open();
            String dir = dlg.open();
            if (dir == null) {
                return;
            }
            dir = dir.trim();
            if (dir.length() == 0) {
                return;
            }

            try {
                ComponentSystemPlugin.getDefault().addComponentLibrary(dir);
                MessageDialog.openInformation(window.getShell(), "UI Designer", //$NON-NLS-1$
                        "Added component library " + dir); //$NON-NLS-1$
            } catch (ComponentSystemException e) {
                notFoundMessage(dir);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
    }

    private void notFoundMessage(String what) {
        MessageDialog.openError(window.getShell(), "UI Designer", //$NON-NLS-1$
                "Component library " + what + " not found or not activated"); //$NON-NLS-1$ //$NON-NLS-2$
    }
}
