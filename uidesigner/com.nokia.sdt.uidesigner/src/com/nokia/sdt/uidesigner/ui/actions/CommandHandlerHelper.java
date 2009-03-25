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

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.displaymodel.adapter.IContainer;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.command.DataModelCommandWrapper;
import com.nokia.sdt.uidesigner.ui.utils.Adapters;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ISelectionChangedListener;

import java.util.*;

/**
 * 
 *
 */
public class CommandHandlerHelper {

	IDesignerEditor editor;

	/**
	 * 
	 */
	public CommandHandlerHelper(IDesignerEditor editor) {
		this.editor = editor;
	}
	
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		editor.getSelectionManager().addSelectionChangedListener(listener);
	}
	
	public void doExecute(Command command) {
		DataModelCommandWrapper wrapper = new DataModelCommandWrapper(){};
		wrapper.setDataModelCommand(command);
		wrapper.setEditor(editor);
		((CommandStack) editor.getAdapter(CommandStack.class)).execute(wrapper);
	}

	public Collection makeEObjectList(Collection list) {
		HashSet objects = new HashSet();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Object object = iter.next();
			if (object instanceof EObject)
				objects.add(object);
			else if (object instanceof EditPart)
				objects.add(((EditPart) object).getModel());
		}
		
		return new ArrayList(objects);
	}
	
	public IDesignerDataModel getDataModel() {
		return editor.getDataModel();
	}
	
	public EObject getRootContainer() {
		return getDataModel().getRootContainers()[0];
	}
	
	public boolean isRemovablePart(EditPart toTest) {
		// select only objects that have models of type EObject
		// and that either have no display object, or are removable
		EditPart editPart = (EditPart) toTest;
		Object object = editPart.getModel();
		if (!(object instanceof EObject))
			return false;
		if (object.equals(getRootContainer()))
			return false;
		EObject eobject = (EObject) object;
		IComponentInstance componentInstance = Adapters.getComponentInstance(eobject);
		EObject parent = componentInstance.getParent();
		if (parent == null)
			return false;
		
		IContainer container = Adapters.getContainer(parent);
		return (container != null) && container.canRemoveChild(componentInstance);
	}

	public void updateSelectionActions() {
		editor.updateSelectionActions();
	}
	
}
