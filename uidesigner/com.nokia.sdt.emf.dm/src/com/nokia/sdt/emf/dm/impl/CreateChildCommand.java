/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.emf.dm.impl;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IComponentInstancePropertyListener;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.emf.dm.INode;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.EMFEditPlugin;
import org.eclipse.ui.views.properties.IPropertySource;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;

/**
 *
 */
public class CreateChildCommand extends AbstractCommand {
	
	protected static final String LABEL = 
		EMFEditPlugin.INSTANCE.getString("_UI_CreateChildCommand_label");
	protected static final String DESCRIPTION = 
		EMFEditPlugin.INSTANCE.getString("_UI_CreateChildCommand_description");

	private IDesignerDataModel model;
	private EObject owner;
	private String ownerName;
	private INode child;
	private String childName; 
	private final int index;
	private ChildNameListener childNameListener;
	
	private class ChildNameListener implements IComponentInstancePropertyListener {
		public void propertyChanged(EObject object, Object propertyId) {
			if (object.equals(child) && propertyId.equals(INode.NAME_PROPERTY)) {
				IPropertySource ps = ModelUtils.getPropertySource(object);
				childName = (String) ps.getPropertyValue(propertyId);
			}
		}
	}

	public CreateChildCommand(EObject owner, INode child, IComponent component, int index) {
		this.owner = owner;
		this.child = child;
		this.index = index;
		model = ((INode) owner).getDesignerData().getDesignerDataModel();
		ownerName = ((INode) owner).getName();
		setLabel(MessageFormat.format(LABEL, new Object[] { component.getFriendlyName() } ));
		setDescription(DESCRIPTION);
		childNameListener = new ChildNameListener();
	}
	
	@Override
	protected boolean prepare() {
		return true;
	}

	public void execute() {
		EList children = getOwner().getChildren();
		if (index == IDesignerDataModel.AT_END)
			children.add(child);
		else
			children.add(index, child);
		childName = ((INode) child).getName();
		IComponentInstance ci = ModelUtils.getComponentInstance(child);
		ci.addPropertyListener(childNameListener);
	}

	@Override
	public void undo() {
		EObject childFromName = model.findByNameProperty(childName);
		if (!childFromName.equals(child)) {
			child = (INode) childFromName;
			IComponentInstance ci = ModelUtils.getComponentInstance(child);
			ci.addPropertyListener(childNameListener);
		}
		getOwner().getChildren().remove(child);
	}

	public void redo() {
		child.setConfigured(true);
		execute();
	}

	@Override
	public Collection<?> getResult() {
		return getAffectedObjects();
	}

	@Override
	public Collection<?> getAffectedObjects() {
		return Collections.singletonList(child);
	}

	private INode getOwner() {
		if (ownerName == null) {
			ownerName = ((INode) owner).getName(); // may not have been set early
		}
		EObject ownerFromName = null;
		if (ownerName != null)
			ownerFromName = model.findByNameProperty(ownerName);
		if (ownerFromName != null)
			owner = ownerFromName;

		return (INode) owner;
	}
}
