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

import com.nokia.sdt.editor.EditorServices;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.*;

import java.text.MessageFormat;

public class ForceSaveAction implements IWorkbenchWindowActionDelegate {
    private IWorkbenchWindow workbenchWindow;
    IWorkbenchPart workbenchPart;
    IEditorPart editor;
    
    public void init(IWorkbenchWindow window) {
        workbenchWindow= window;
    }
    
    public void dispose() {

    }

    public void run(IAction action) {
    	EditorServices.saveEditor(editor);
    }

    public void selectionChanged(IAction action, ISelection selection) {
        boolean handled= false;
        editor = null;
        IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
        workbenchPart = null;
        if (workbenchPage != null) {
            workbenchPart = workbenchPage.getActivePart();
            IEditorPart editor= workbenchPage.getActiveEditor();
            if (editor != null && editor == workbenchPart) {
                if (editor.getEditorInput() != null && 
                        isDesignerDocument(editor.getEditorInput().getName())) {
                    this.editor = editor;
                    handled = true;
                    action.setText(MessageFormat.format(Messages.getString("ForceSaveAction.ForceSaveDesign"), //$NON-NLS-1$
                    		new Object[] { this.editor.getEditorInput().getName() }));
                }
            }
            if (!handled) {
                IEditorReference[] refs = workbenchPage.getEditorReferences();
                for (int i = 0; i < refs.length; i++) {
                    if (refs[i].getName() != null && isDesignerDocument(refs[i].getName())) {
                        handled = true;
                        action.setText(Messages.getString("ForceSaveAction.ForceSaveAllDesigns")); //$NON-NLS-1$
                        break;
                    }
                }
            }
        }
        
        action.setEnabled(handled);
    }

	private boolean isDesignerDocument(String name) {
		return name.matches(".*(\\.nxd|\\.uidesign)"); //$NON-NLS-1$
	}

}
