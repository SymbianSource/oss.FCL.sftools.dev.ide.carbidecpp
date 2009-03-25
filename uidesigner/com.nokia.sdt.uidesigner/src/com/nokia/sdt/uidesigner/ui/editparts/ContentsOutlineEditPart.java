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

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.ContentsObject;
import com.nokia.sdt.uidesigner.ui.ViewEditorOutlinePage;
import com.nokia.sdt.uidesigner.ui.ContentsObject.RootContainerAddedListener;
import com.nokia.sdt.uidesigner.ui.command.CreateCommand;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.*;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.gef.editpolicies.ContainerEditPolicy;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import java.util.Iterator;
import java.util.List;

/**
 * 
 *
 */
public class ContentsOutlineEditPart extends AbstractTreeEditPart implements RootContainerAddedListener {

	private EditPartViewer viewer;
	/** 
	 * Create a new instance of this edit part using the given model element.
	 * @param model a non-null root IModelObject instance
	 */
	ContentsOutlineEditPart(Object model) {
		super(model);
		Check.checkArg(model instanceof ContentsObject);
		if (getContentsObject() != null) {
			getContentsObject().addListener(this);
		}
	}
	
	public void rootContainerAdded(EObject root) {
		addChild(createChild(root), -1);
	}

	protected void createEditPolicies() {
		// disallow removal
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());

		installEditPolicy(EditPolicy.CONTAINER_ROLE, new ContainerEditPolicy() {
			/* (non-Javadoc)
			 * @see org.eclipse.gef.editpolicies.ContainerEditPolicy#getCreateCommand(org.eclipse.gef.requests.CreateRequest)
			 */
			protected Command getCreateCommand(CreateRequest createRequest) {
				Object parent = getHost().getParent().getModel();
				if (parent instanceof EObject) {
					IDesignerEditor editor = ((ModelObjectEditPart) getHost()).getEditor();
					return new CreateCommand((EObject) parent, createRequest, IDesignerDataModel.AT_END,
							editor.getDataModel(), getHost(), editor);
				}
				return null;
			}
		});
	}

	public ContentsObject getContentsObject() {
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
		ViewEditorOutlinePage.setExpandedAll(this);
	}

	@Override
	public EditPartViewer getViewer() {
		if (viewer == null)
			viewer = super.getViewer();
		
		return viewer;
	}
	
	
}
