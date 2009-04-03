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

import com.nokia.sdt.component.symbian.displaymodel.Utilities;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.editor.IComponentEditor;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.command.DataModelCommandWrapper;
import com.nokia.sdt.uidesigner.ui.utils.EditorUtils;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import java.util.*;

public abstract class VerticalMoveActionDelegate implements IObjectActionDelegate {
	
	protected EObject object;
	protected IDesignerEditor editor;
	
	protected abstract boolean isApplicableForDirection(EObject object, EObject[] children);
	protected abstract int getNewInsertPosition(EObject object, EObject[] children);

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		editor = (IDesignerEditor) targetPart.getAdapter(IDesignerEditor.class);
	}

	public void run(IAction action) {
		if (object != null) {
			IComponentInstance instance = Utilities.getComponentInstance(object);
			EObject parent = instance.getParent();
			IComponentInstance parentInstance = Utilities.getComponentInstance(parent);
			IDesignerDataModel dataModel = parentInstance.getDesignerDataModel();
			int pos = getNewInsertPosition(object, parentInstance.getChildren());
			org.eclipse.emf.common.command.Command command = dataModel.createMoveComponentInstanceCommand(object, parent, pos);
			CommandStack stack = (CommandStack) editor.getAdapter(CommandStack.class);
			DataModelCommandWrapper commandWrapper = new DataModelCommandWrapper();
			commandWrapper.setDataModelCommand(command);
			stack.execute(commandWrapper);
			refreshTransients(parent);
		}
	}
	
	private void refreshTransients(EObject parent) {
		if (editor.isTransientMode()) {
			IFigure transientLayer = editor.getTransientLayerRootFigure().getParent();
			transientLayer.repaint();
			Utilities.getLayoutContainer(parent).layoutChildren();
		}
	}
	
	protected boolean isTemporaryEditor(EObject object) {
		IComponentEditor componentEditor = Utilities.getComponentEditor(object);
		return (componentEditor != null) && componentEditor.isTemporaryObject();
	}

	private EObject[] removeTemporaryObjects(EObject[] children) {
		List<EObject> result = new ArrayList<EObject>();
		List<EObject> temp = Arrays.asList(children);
		for (Iterator<EObject> iter = temp.iterator(); iter.hasNext();) {
			EObject next = iter.next();
			if (!isTemporaryEditor(next))
				result.add(next);
		}
		return (EObject[]) result.toArray(new EObject[result.size()]);
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.size() == 1) {
				Object firstElement = structuredSelection.getFirstElement();
				if (firstElement instanceof IAdaptable) {
					IAdaptable adaptable = (IAdaptable) firstElement;
					IComponentInstance instance = 
						(IComponentInstance) adaptable.getAdapter(IComponentInstance.class);
					if (instance != null) {
						EObject object = instance.getEObject();
						EObject parent = instance.getParent();
						if (EditorUtils.canReorderChildren(parent, getEObjectListFromSelection(structuredSelection))) {
							IComponentInstance parentInstance = Utilities.getComponentInstance(parent);
							EObject[] children = removeTemporaryObjects(parentInstance.getChildren());
							if (isApplicableForDirection(object, children)) {
								this.object = object;
								action.setEnabled(true);
								return;
							}
						}
					}
				}
			}
		}

		this.object = null;
		action.setEnabled(false);
	}
	
	private List<EObject> getEObjectListFromSelection(IStructuredSelection structuredSelection) {
		List<EObject> list = new ArrayList<EObject>();
		for(Iterator iterator = structuredSelection.iterator(); iterator.hasNext();) {
			Object next = iterator.next();
			if (next instanceof IAdaptable) {
				IComponentInstance ci = 
					(IComponentInstance) ((IAdaptable) next).getAdapter(IComponentInstance.class);
				if (ci != null)
					list.add(ci.getEObject());
			}
		}
		
		return list;
	}
}
