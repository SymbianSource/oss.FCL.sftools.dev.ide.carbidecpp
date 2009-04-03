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
package com.nokia.sdt.uidesigner.ui.editparts;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.IDesignerImages;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IComponentInstancePropertyListener;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.actions.IPopupActionTarget;
import com.nokia.sdt.uidesigner.ui.editparts.policy.ComponentDeletePolicy;
import com.nokia.sdt.uidesigner.ui.editparts.policy.ComponentOutlineEditPolicy;
import com.nokia.sdt.uidesigner.ui.utils.Adapters;
import com.nokia.sdt.uidesigner.ui.utils.EditorUtils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IActionFilter;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

public class ModelObjectOutlineEditPart extends AbstractTreeEditPart
										implements IEditPartEditorProvider, 
										IComponentInstancePropertyListener,
										IPopupActionTarget {

	private static String labelFormat = "{0} : {1}"; //$NON-NLS-1$
	protected EditPartHelper helper;
	private EditPartViewer viewer;
	private FilterHelper filterHelper;
	
	/** 
	 * Create a new instance of this edit part using the given model element.
	 * @param model a non-null root IModelObject instance
	 */
	ModelObjectOutlineEditPart(Object model) {
		super(model);
		helper = new EditPartHelper(this);
		filterHelper = new FilterHelper();
		if (model instanceof EObject)
			filterHelper.setModel((EObject) model);
	}
	
	/**
	 * Upon activation, attach to the component as a listener.
	 */
	public void activate() {
		if (!isActive()) {
			super.activate();
			getComponentInstance().addPropertyListener(this);
		}
	}

	/**
	 * Upon deactivation, detach from the component as a listener.
	 */
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			getComponentInstance().removePropertyListener(this);
		}
	}


	protected IComponentInstance getComponentInstance() {
		return Adapters.getComponentInstance((EObject) getModel());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
	 */
	protected void createEditPolicies() {
		// allow removal of the associated model element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ComponentDeletePolicy(this));

		installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, new ComponentOutlineEditPolicy());
	}

	public IDesignerEditor getEditor() {
		return helper.getEditor();
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	protected Image getImage() {
		Image image = null;
		IDesignerImages images = Adapters.getDesignerImages((EObject) getModel());
		if (images != null)
			image = images.getSmallIcon();
		
		if (image == null)
			image = EditorUtils.getUnknownSmallIcon();
		
		return image;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getText()
	 */
	protected String getText() {
		IComponentInstance instance = getComponentInstance();
		String instanceName = instance.getName();
		instanceName = ((instanceName == null) ? "" : instanceName); //$NON-NLS-1$

        // try to use the friendly name and fall back to the component id
        // when a component is missing or broken
		String name = null;
		IComponent component = instance.getComponent();
		if (component != null) {
			name = component.getFriendlyName();
		} else {
		    name = instance.getComponentId();
        }
		String result;
		if (name != null) {
			Object params[] = {instanceName, name};
			result = MessageFormat.format(labelFormat, params);
		}
		else
			result = instanceName;
		return result;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	protected List getModelChildren() {
		return Collections.EMPTY_LIST;
	}

	public void propertyChanged(EObject componentInstance, Object propertyId) {
		refreshVisuals();
	}

	public EditPartViewer getViewer() {
		// cache the viewer, in case we're removed (transient)
		if (viewer == null)
			viewer = super.getViewer();
		
		return viewer;
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
}
