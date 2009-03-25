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

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorPart;

import com.nokia.sdt.uidesigner.ui.UIDesignerPlugin;
import com.nokia.sdt.uidesigner.ui.utils.Strings;
import com.swtdesigner.ResourceManager;

/**
 * An action to show the events view part.
 */
public class ShowOutlineAction extends ShowViewPartAction {

	public final static String ID = "com.nokia.sdt.uidesigner.showOutline"; //$NON-NLS-1$
    public final static String OUTLINE_VIEW = "org.eclipse.ui.views.ContentOutline"; //$NON-NLS-1$
	
	public ShowOutlineAction(IEditorPart editor) {
		super(editor);
	}

	protected void init() {
		super.init();
		setId(ID);
		setText(Strings.getString("ShowOutlineAction.label")); //$NON-NLS-1$
		Image image = ResourceManager.getPluginImage(
				UIDesignerPlugin.getDefault(), "icons/outline_16.png"); //$NON-NLS-1$
		setImageDescriptor(ImageDescriptor.createFromImage(image));		
	}

	protected String getViewPartId() {
		return OUTLINE_VIEW;
	}
}
