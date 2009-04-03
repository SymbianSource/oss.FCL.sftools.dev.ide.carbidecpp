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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.displaymodel.adapter.IDisplayObject;
import com.nokia.sdt.uidesigner.ui.editparts.policy.ComponentDeletePolicy;
import com.nokia.sdt.uidesigner.ui.editparts.policy.ComponentOutlineEditPolicy;
import com.nokia.sdt.uidesigner.ui.editparts.policy.ContainerOutlineEditPolicy;
import com.nokia.sdt.uidesigner.ui.utils.Adapters;


/**
 * 
 *
 */
public class ModelContainerOutlineEditPart extends ModelObjectOutlineEditPart 
										implements IComponentInstanceChildListener {

	/** 
	 * Create a new instance of this edit part using the given model element.
	 * @param model a non-null root IModelObject instance
	 */
	ModelContainerOutlineEditPart(Object model) {
		super(model);
	}
	
	/**
	 * Upon activation, attach to the component as a listener.
	 */
	public void activate() {
		if (!isActive()) {
			super.activate();
			getComponentInstance().addChildListener(this);
		}
	}

	/**
	 * Upon deactivation, detach from the component as a listener.
	 */
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			getComponentInstance().removeChildListener(this);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
	 */
	protected void createEditPolicies() {
		// If this editpart is non-removable, then disallow removal
		IDisplayObject displayObject = Adapters.getDisplayObject((EObject) getModel());
		boolean shouldInhibitRemoval = !displayObject.isRemovable();
		if (shouldInhibitRemoval) {
			installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		}
		else {
			// allow removal of the associated model element
			installEditPolicy(EditPolicy.COMPONENT_ROLE, new ComponentDeletePolicy(this));
		}

		installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, new ComponentOutlineEditPolicy());

		installEditPolicy(EditPolicy.TREE_CONTAINER_ROLE, new ContainerOutlineEditPolicy());
	}

	/**
	 * Convenience method that returns the EditPart corresponding to a given child.
	 * @param child a component instance
	 * @return the corresponding EditPart or null
	 */
	private EditPart getEditPartForChild(Object child) {
		return (EditPart) getViewer().getEditPartRegistry().get(child);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	protected List getModelChildren() {
		IComponentInstance ci = getComponentInstance();
		EObject[] children = ci.getChildren();
		if (children != null)
			return Arrays.asList(children);
		
		return Collections.EMPTY_LIST;
	}

	public void childAdded(EObject parent, EObject child) {
		ICreationToolProvider childProvider = Adapters.getCreationToolProvider(child);
		ICreationToolProvider parentProvider = Adapters.getCreationToolProvider(parent);
		getEditor().updatePalette(childProvider != null || parentProvider != null);
		((EditPart) getViewer().getEditPartRegistry().get(parent)).refresh();
	}

	public void childRemoved(EObject parent, EObject child) {
		ICreationToolProvider childProvider = Adapters.getCreationToolProvider(child);
		ICreationToolProvider parentProvider = Adapters.getCreationToolProvider(parent);
		getEditor().updatePalette(childProvider != null || parentProvider != null);
		EditPart childEditPart = getEditPartForChild(child);
		if (childEditPart != null)
			removeChild(childEditPart);
	}

	public void childrenReordered(EObject parent) {
		((EditPart) getViewer().getEditPartRegistry().get(parent)).refresh();
	}
}
