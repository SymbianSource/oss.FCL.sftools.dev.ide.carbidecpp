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
package com.nokia.carbide.cpp.internal.project.ui.views;

import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;

import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.resources.FileStorage;
import org.eclipse.cdt.internal.ui.util.EditorUtility;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import java.util.Iterator;

public class OpenNonWorkspaceFileAction extends BaseSelectionListenerAction {

	public OpenNonWorkspaceFileAction(IWorkbenchPage page) {
		super(Messages.OpenNonWorkspaceFileAction_OpenActionText);
		setToolTipText(Messages.OpenNonWorkspaceFileAction_OpenActionToolTip);
		setDescription(Messages.OpenNonWorkspaceFileAction_OpenActionToolTip);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		Iterator itr = getStructuredSelection().iterator();
        while (itr.hasNext()) {
        	Object element = itr.next();
        	if (element instanceof INonWorkspaceFileEntry) {
        		IPath absoluteFilePath = ((INonWorkspaceFileEntry)element).getAbsolutePath();
				try {
					EditorUtility.openInEditor(absoluteFilePath, null);
				} catch (PartInitException e) {
					ProjectUIPlugin.log(e);
				}
        	}
        }
	}
}
