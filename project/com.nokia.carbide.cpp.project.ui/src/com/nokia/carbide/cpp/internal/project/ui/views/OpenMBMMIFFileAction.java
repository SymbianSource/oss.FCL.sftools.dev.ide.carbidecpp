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

import com.nokia.carbide.cpp.internal.project.ui.actions.EditMBMFileEntryAction;
import com.nokia.carbide.cpp.internal.project.ui.actions.EditMIFFileEntryAction;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import java.util.Iterator;

public class OpenMBMMIFFileAction extends BaseSelectionListenerAction {

	private IWorkbenchPage page;

	public OpenMBMMIFFileAction(IWorkbenchPage page) {
		super(Messages.OpenMBMMIFFileAction_OpenText);
		setToolTipText(Messages.OpenMBMMIFFileAction_OpenToolTip);
		setDescription(Messages.OpenMBMMIFFileAction_OpenDescription);
		this.page = page;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		Iterator itr = getStructuredSelection().iterator();
        while (itr.hasNext()) {
        	Object element = itr.next();
        	if (element instanceof IMBMMIFFileEntry) {
        		editMBMMIFFile((IMBMMIFFileEntry) element);
        	}
        }
	}

	/**
	 * @param selection
	 */
	private void editMBMMIFFile(IMBMMIFFileEntry entry) {
		// workaround: forward to previously-written but disabled actions
		IWorkbenchWindowActionDelegate actionDelegate = null;
		if (entry instanceof IMBMFileEntry) {
			actionDelegate = new EditMBMFileEntryAction();
			actionDelegate.init(page.getWorkbenchWindow());
			actionDelegate.selectionChanged(this, new StructuredSelection(entry));
			actionDelegate.run(this);
		} else if (entry instanceof IMIFFileEntry) {
			actionDelegate = new EditMIFFileEntryAction();
			actionDelegate.init(page.getWorkbenchWindow());
			actionDelegate.selectionChanged(this, new StructuredSelection(entry));
			actionDelegate.run(this);
		} else {
			Check.checkState(false);
		}
	}
}
