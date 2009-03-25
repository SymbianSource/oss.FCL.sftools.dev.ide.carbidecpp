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


package com.nokia.sdt.uidesigner.ui.command;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.displaymodel.adapter.IContainer;
import com.nokia.sdt.displaymodel.adapter.IDisplayObject;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.editparts.*;
import com.nokia.sdt.uidesigner.ui.utils.Adapters;
import com.nokia.sdt.uidesigner.ui.utils.Strings;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;

import java.util.*;

public class DeleteCommand extends DataModelCommandWrapper {
	
	private boolean executable; 
	private EObject objectToSelect;
	private EObject savedObjectToSelect;
	
	public DeleteCommand(List parts, IDesignerDataModel dataModel, IDesignerEditor editor) {
		Check.checkArg(parts);
		Check.checkArg(dataModel);
		Check.checkArg(editor);
		
		List objectsToDelete = new ArrayList();
		for (Iterator iter = parts.iterator(); iter.hasNext();) {
			EditPart childPart = (EditPart) iter.next();
			EObject child = (EObject) childPart.getModel();
			// check if removable
			IDisplayObject displayObject = Adapters.getDisplayObject(child);
			executable = (displayObject != null) && displayObject.isRemovable();
			EditPart parentPart = childPart.getParent();
			if (executable && ((parentPart instanceof ModelObjectEditPart) ||
					(parentPart instanceof ModelObjectOutlineEditPart))) {
				EObject parent = (EObject) parentPart.getModel();
				IContainer container = Adapters.getContainer(parent);
				IComponentInstance childInstance = Adapters.getComponentInstance(child);
				IComponent component = childInstance.getComponent();
				if (component != null)
					executable = container.canRemoveChild(childInstance);
				else
					executable = true;
			}
			if (!executable)
				return;
			
			objectsToDelete.add(child);
		}
		
		setObjectToSelect(objectsToDelete);
		setDataModelCommand(dataModel.createRemoveComponentInstancesCommand(objectsToDelete));
		setEditor(editor);

		setLabel(Strings.getString("DeleteCommand.DeletionLabel")); //$NON-NLS-1$
	}
	
	private void setObjectToSelect(List objectsToDelete) {
		for (Object object : objectsToDelete) {
			IComponentInstance ci = Adapters.getComponentInstance((EObject) object);
			EObject parent = ci.getParent();
			if (parent != null) {
				ci = Adapters.getComponentInstance(parent);
				EObject[] children = ci.getChildren();
				for (EObject sibling : children) {
					if (!objectsToDelete.contains(sibling)) {
						savedObjectToSelect = sibling;
						break;
					}
				}
			}
		}
	}

	public void execute() {
		objectToSelect = savedObjectToSelect;
		super.execute();
	}
	
	public void redo() {
		objectToSelect = savedObjectToSelect;
		super.redo();		
	}

	public boolean canExecute() {
		if (executable)
			return super.canExecute();
		
		return false;
	}
	
	public void undo() {
		objectToSelect = null;
		super.undo();
	}

	protected Collection getAffectedObjects() {
		if (objectToSelect != null)
			return Collections.singletonList(objectToSelect);
		
		return super.getAffectedObjects();
	}
}
