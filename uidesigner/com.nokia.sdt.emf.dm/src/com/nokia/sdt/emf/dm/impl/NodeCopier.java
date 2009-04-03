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
package com.nokia.sdt.emf.dm.impl;

import com.nokia.sdt.component.property.IPropertyInformation;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.emf.dm.*;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.Iterator;

public class NodeCopier {
	
	public interface IFilter {
		/**
		 * @param node
		 * @return true if should be copied
		 */
		boolean test(INode node);
	}

	private INode theCopy;
	private IDesignerData localModelRoot;

	public NodeCopier(IDesignerData modelRoot, INode srcNode, boolean preserveLocalizedStringKeys, IFilter filter) {
		init(modelRoot, srcNode, preserveLocalizedStringKeys, false, filter);
	}
	
	public NodeCopier(IDesignerData modelRoot, INode srcNode, boolean preserveLocalizedStringKeys) {
		init(modelRoot, srcNode, preserveLocalizedStringKeys, false, null);
	}
	
	public NodeCopier(INode srcNode, boolean preserveEventBindings, IFilter filter) {
		// set up a simple temporary model with a simple root object
		// to hold the value we're copying
		IDesignerData modelRoot = DmFactory.eINSTANCE.createIDesignerData();
		// make a dummy root node, because our copy routine needs a parent
		INode rootNode = DmFactory.eINSTANCE.createINode();
		modelRoot.getRootContainers().add(rootNode);
		init(modelRoot, srcNode, true, preserveEventBindings, filter);
	}
	
	public NodeCopier(INode srcNode, IFilter filter) {
		this(srcNode, false, filter);
	}
	
	public NodeCopier(INode srcNode) {
		this(srcNode, null);
	}
	
	private void init(IDesignerData modelRoot, INode srcNode, 
			boolean preserveLocalizedStringKeys, boolean preserveEventBindings, IFilter filter) {
		Check.checkArg(modelRoot);
		Check.checkArg(srcNode);
		// need it to be in a model so we can get localized strings
		Check.checkArg(srcNode.getDesignerData() != null);
		localModelRoot = modelRoot;
		Check.checkState(!localModelRoot.getRootContainers().isEmpty());
		INode rootNode = (INode) localModelRoot.getRootContainers().get(0);
		theCopy = copyNodeToEnd(srcNode, rootNode, 
				preserveLocalizedStringKeys, preserveEventBindings, filter);
	}
	
	IDesignerData getRoot() {
		return localModelRoot;
	}
	
	public INode getCopy() {
		return theCopy;
	}
	
	private static INode copyNodeToEnd(INode srcNode, INode destParent, 
			boolean preserveLocalizedStringKeys, boolean preserveEventBindings, IFilter filter) {
		return copyNode(srcNode, destParent, IDesignerDataModel.AT_END, 
				preserveLocalizedStringKeys, preserveEventBindings, filter);
	}
	
	public static INode copyNode(INode srcNode, INode destParent, int index, 
			boolean preserveLocalizedStringKeys) {
		return copyNode(srcNode, destParent, index, preserveLocalizedStringKeys, false);
	}
	
	public static INode copyNode(INode srcNode, INode destParent, int index, 
			boolean preserveLocalizedStringKeys, boolean preserveEventBindings) {
		return copyNode(srcNode, destParent, index, 
				preserveLocalizedStringKeys, preserveEventBindings, null);
	}
	
	public static INode copyNode(INode srcNode, INode destParent, int index, 
				boolean preserveLocalizedStringKeys, IFilter filter) {
		return copyNode(srcNode, destParent, index, preserveLocalizedStringKeys, false, filter);
	}
	
	public static INode copyNode(INode srcNode, INode destParent, int index, 
				boolean preserveLocalizedStringKeys, boolean preserveEventBindings, IFilter filter) {
		// If a filter exists, ensure the srcNode passes the test
		if ((filter != null) && !filter.test(srcNode))
			return null;
		
		INode result = DmFactory.eINSTANCE.createINode();
		
		// Set the configured flag so initializers can
		// distinguish between brand new and already configured objects
		result.setConfigured(true);
		
		// First, set the component id, so parenting doesn't fail
		result.setComponentId(srcNode.getComponentId());
		
		// The new node should be parented before copying properties
		// so the string tables can be found
		EList children = destParent.getChildren();
		
		// turn off notifications until copy is complete
		boolean saveDeliver = destParent.eDeliver();
		destParent.eSetDeliver(false);
		
		if (index == IDesignerDataModel.AT_END)
			children.add(result);
		else
			children.add(index, result);
	
		// if the new node could not be parented, we should abort everything else
		if (!children.contains(result))
			return null;
		
		new PropertyContainerCopier(
				srcNode.getProperties(), result.getProperties(), false, preserveLocalizedStringKeys);
		
		// refresh the properties of the result, in case of promoted properties
		IPropertySource propertySource = ModelUtils.getPropertySource(result);
		if (propertySource instanceof IPropertyInformation)
			((IPropertyInformation) propertySource).refresh();
		
		// copy the event bindings
		if (preserveEventBindings) {
			for (Object object : srcNode.getEventBindings()) {
				IEventBinding binding = (IEventBinding) object;
				IEventBinding copy = DmFactory.eINSTANCE.createIEventBinding();
				copy.setEventID(binding.getEventID());
				copy.setEventHandlerDisplayText(binding.getEventHandlerDisplayText());
				copy.setEventHandlerInfo(binding.getEventHandlerInfo());
				result.getEventBindings().add(copy);
			}
		}
		
		for (Iterator iter = srcNode.getChildren().iterator(); iter.hasNext();) {
			INode srcChild = (INode) iter.next();
			copyNodeToEnd(srcChild, result, preserveLocalizedStringKeys, preserveEventBindings, filter);
		}
		
		// reset notifications and fire child added
		destParent.eSetDeliver(saveDeliver);
		if (children instanceof ValidatingEObjectContainmentEList) {
			ValidatingEObjectContainmentEList list = (ValidatingEObjectContainmentEList) children;
			NotificationImpl notification = list.createNotification(Notification.ADD, null, result, index, false);
			list.dispatchNotification(notification);
		}
		
		return result;
	}

}
