/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.uidesigner.ui.actions;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.gef.ui.actions.EditorPartAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.nokia.sdt.uidesigner.ui.UIDesignerPlugin;
import com.nokia.sdt.uidesigner.ui.utils.Strings;
import com.nokia.cpp.internal.api.utils.core.Logging;

/**
 * An action to show a view part.
 */
public abstract class ShowViewPartAction extends EditorPartAction {

	public ShowViewPartAction(IEditorPart editor) {
		super(editor);
	}

	protected abstract String getViewPartId();

	/**
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	protected boolean calculateEnabled() {
		return true;
	}
	
	private IWorkbenchPage getWorkbenchPage() {
		return getWorkbenchPart().getSite().getPage().getWorkbenchWindow().getActivePage();
	}
	
	protected void init() {
		super.init();
		Image image = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_DEF_VIEW);
		setImageDescriptor(ImageDescriptor.createFromImage(image));
	}
	
	/**
	 * Shows the part.
	 */
	public void run() {
        IWorkbenchPage page = getWorkbenchPage();
        if (page != null) {
            try {
                page.showView(getViewPartId());
            } 
            catch (PartInitException e) {
    			String message = Strings.getString("ShowViewPartAction.ShowPageError"); //$NON-NLS-1$
    			IStatus status = Logging.newSimpleStatus(0, IStatus.ERROR, message + e.getMessage(), e);
    			Logging.log(UIDesignerPlugin.getDefault(), status);
            }
        }
	}

}
