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

import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.displaymodel.adapter.*;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.actions.IPopupActionTarget;
import com.nokia.sdt.uidesigner.ui.editparts.policy.ComponentDeletePolicy;
import com.nokia.sdt.uidesigner.ui.utils.Adapters;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.*;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.ui.IActionFilter;

import java.util.*;

/**
 * 
 *
 */
public abstract class ModelObjectEditPart extends AbstractGraphicalEditPart 
									implements IEditPartEditorProvider, 
												IComponentInstanceChildListener,
												IPopupActionTarget {

	public static final String INITIAL_KEY_EVENT = "ModelObjectEditPart.InitialEvent"; //$NON-NLS-1$
	private EditPartHelper helper;
	private EditPartViewer viewer;
	private FilterHelper filterHelper;

	public ModelObjectEditPart() {
		helper = new EditPartHelper(this);
		filterHelper = new FilterHelper();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	protected void createEditPolicies() {
		if (getDisplayObject().isRemovable()) // allow removal of the associated model element
			installEditPolicy(EditPolicy.COMPONENT_ROLE, new ComponentDeletePolicy(this));
	}

	protected IComponentInstance getComponentInstance() {
		return Adapters.getComponentInstance((EObject) getModel());
	}
	
	protected IDisplayObject getDisplayObject() {
		return Adapters.getDisplayObject((EObject) getModel());
	}
	
	protected ILayoutObject getLayoutObject() {
		Object model = getModel();
		if (model instanceof EObject)
			return Adapters.getLayoutObject((EObject) model);
//		System.out.println("no model");
		return null;
	}
	
	protected ILayoutContainer getLayoutContainer() {
		EObject model = (EObject) getModel();
		if (model != null)
			return Adapters.getLayoutContainer(model);
		
		return null;
	}
	
	/**
	 * Upon activation, attach to the model element as a child listener.
	 */
	public void activate() {
		if (!isActive()) {
			super.activate();
			getComponentInstance().addChildListener(this);
		}
	}

	/**
	 * Upon deactivation, detach from the model element as a child listener.
	 */
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			getComponentInstance().removeChildListener(this);
		}
	}

	protected abstract boolean isApplicableChild(EObject object);

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	protected List getAllModelChildren() {
		// only return the applicable children
		IComponentInstance ci = getComponentInstance();
		EObject[] allChildren = ci.getChildren();
		if (allChildren != null) {
			List applicableChildren = new ArrayList();
			for (int i = 0; i < allChildren.length; i++) {
				EObject child = allChildren[i];
				if (isApplicableChild(child))
					applicableChildren.add(child);
			}
			return applicableChildren;
		}
		
		return Collections.EMPTY_LIST;
	}
	
	protected List getModelChildren() {
		return getAllModelChildren();
	}
	
	public IDesignerEditor getEditor() {
		return helper.getEditor();
	}

	public void setStatusLineErrorMessage(String errorMessage) {
		helper.setStatusLineErrorMessage(errorMessage);
	}
	
	public void childAdded(EObject parent, EObject child) {
		refreshChildren();
		ICreationToolProvider childProvider = Adapters.getCreationToolProvider(child);
		ICreationToolProvider parentProvider = Adapters.getCreationToolProvider(parent);
		getEditor().updatePalette(childProvider != null || parentProvider != null);
	}

	public void childRemoved(EObject parent, EObject child) {
		refreshChildren();
		getEditor().updatePalette(false);
	}

	public void childrenReordered(EObject parent) {
		((EditPart) getViewer().getEditPartRegistry().get(parent)).refresh();
	}
	
	public EditPartViewer getViewer() {
		// cache the viewer, in case we're removed (transient)
		if (viewer == null)
			viewer = super.getViewer();
		
		return viewer;
	}
	
	public void setModel(Object model) {
		super.setModel(model);
		if (model instanceof EObject)
			filterHelper.setModel((EObject) model);
	}

	public Object getAdapter(Class key) {
		if (key.equals(IComponentInstance.class)) {
			return helper.getComponentInstance();
		}
		if (key.equals(IActionFilter.class)) {
			return filterHelper.getFilter();
		}
		if (key.equals(IDesignerEditor.class)) {
			return getEditor();
		}
		return super.getAdapter(key);
	}

	@Override
	public void removeNotify() {
		clearModel(this);
		super.removeNotify();
	}

	private static void clearModel(EditPart editPart) {
		for (EditPart child : (List<EditPart>) editPart.getChildren()) {
			clearModel(child);
		}
		editPart.setModel(null);
	}
}
