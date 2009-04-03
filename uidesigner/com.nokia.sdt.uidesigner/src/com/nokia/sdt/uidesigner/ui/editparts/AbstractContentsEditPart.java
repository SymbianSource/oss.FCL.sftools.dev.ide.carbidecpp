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


package com.nokia.sdt.uidesigner.ui.editparts;

import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.ContentsObject;
import com.nokia.cpp.internal.api.utils.core.Check;

public abstract class AbstractContentsEditPart extends AbstractGraphicalEditPart
										implements IEditPartEditorProvider {

	private EditPartHelper helper;

	public AbstractContentsEditPart() {
		helper = new EditPartHelper(this);
	}

	public IDesignerEditor getEditor() {
		return helper.getEditor();
	}

	public ContentsObject getContentsObject() {
		Check.checkState(getModel() instanceof ContentsObject);
		return (ContentsObject) getModel();
	}

	protected List getModelChildren() {
		return getContentsObject().getRootContainers();
	}
	
	public void forceRefreshChildren() {
		int index = 0;
		for (Iterator iter = children.iterator(); iter.hasNext(); index++) {
			EditPart editPart = (EditPart) iter.next();
			Object model = editPart.getModel();
			removeChild(editPart);
			editPart = createChild(model);
			addChild(editPart, index);
		}
	}
}
