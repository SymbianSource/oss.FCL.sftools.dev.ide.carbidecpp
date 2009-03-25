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


package com.nokia.sdt.component.symbian.displaymodel;

import com.nokia.cpp.internal.api.utils.core.StatusBuilder;
import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.CommonAttributes;
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.adapter.IContainer;
import com.nokia.sdt.utils.StatusHolder;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.ArrayList;
import java.util.List;

class ContainmentUtilities {

	/*
	 * Check whether the component has any static constraints that would prevent
	 * it from being added to the container.
	 */
	static boolean canAddComponentToContainer(IDisplayModel displayModel,
				IComponent component, IContainer container, boolean forPalette, StatusHolder holder) {
		
		StatusBuilder builder = new StatusBuilder(ComponentSystemPlugin.getDefault());
		IAttributes attr = (IAttributes) component.getAdapter(IAttributes.class);
		if (attr == null) {
			if (holder != null) {
				String fmt = Messages.getString("Utilities.NoAttributesAdapterError"); //$NON-NLS-1$
				Object params[] = {component.getId()};
				builder.add(IStatus.ERROR, fmt, params);
				holder.setStatus(builder.createStatus("", null)); //$NON-NLS-1$
			}
			return false;
		}
		
		boolean isLayoutObject = attr.getBooleanAttribute(CommonAttributes.IS_LAYOUT_OBJECT, false);
		boolean isTopLevelOnly = attr.getBooleanAttribute(CommonAttributes.IS_TOP_LEVEL_ONLY_LAYOUT_OBJECT, false);
		boolean isExclusiveChild = attr.getBooleanAttribute(CommonAttributes.IS_EXCLUSIVE_CHILD_LAYOUT_OBJECT, false);

		IComponentInstance parentInstance = Utilities.getComponentInstance(container.getEObject());
		IAttributes parentAttributes = (IAttributes) parentInstance.getComponent().getAdapter(IAttributes.class);
		boolean parentIsTopLevelContentContainer = 
			parentAttributes.getBooleanAttribute(CommonAttributes.IS_TOP_LEVEL_CONTENT_CONTAINER, false);
		EObject[] potentialSiblings = getLayoutChildren(parentInstance);
		
		boolean currentExclusiveChildError = false;
		if (!forPalette)
			currentExclusiveChildError = isLayoutObject && containsExclusiveChild(potentialSiblings);
		if (currentExclusiveChildError && (holder != null)) {
			String fmt = Messages.getString("Utilities.CurrentExclusiveChildError"); //$NON-NLS-1$
			IComponentInstance containerInstance = 
				(IComponentInstance) EcoreUtil.getRegisteredAdapter(container.getEObject(), IComponentInstance.class);
			Object params[] = {containerInstance.getName()};
			builder.add(IStatus.ERROR, fmt, params);
		}
		
		boolean topLevelOnlyError = isTopLevelOnly && !parentIsTopLevelContentContainer;
		if (topLevelOnlyError && (holder != null)) {
			String fmt = Messages.getString("Utilities.TopLevelOnlyError"); //$NON-NLS-1$
			Object params[] = {component.getFriendlyName()};
			builder.add(IStatus.ERROR, fmt, params);
		}
		
		boolean exclusiveChildSiblingsError = false;
		if (!forPalette)
			exclusiveChildSiblingsError = isExclusiveChild && (potentialSiblings != null); 
		if (exclusiveChildSiblingsError && (holder != null)) {
			String fmt = Messages.getString("Utilities.ExclusiveChildSiblingsError"); //$NON-NLS-1$
			Object params[] = {component.getFriendlyName()};
			builder.add(IStatus.ERROR, fmt, params);
		}
		
		boolean result = !currentExclusiveChildError &&
							!topLevelOnlyError &&
							!exclusiveChildSiblingsError;
		
		
		if (!result && (holder != null)) {
			IComponentInstance containerInstance = (IComponentInstance) EcoreUtil
			.getRegisteredAdapter(container.getEObject(), IComponentInstance.class);
			Object params[] = { component.getFriendlyName(), containerInstance.getName() };
			holder.setStatus(builder.createMultiStatus(Messages.getString("Utilities.CantAddObjectError"), params)); //$NON-NLS-1$
		}
		return result;
	}
	
	/*
	 * Checks "never add" attribute and returns result and status
	 */
	static boolean requiresContainmentQuery(IComponent component, IContainer container, StatusHolder holder) {
		StatusBuilder builder = new StatusBuilder(ComponentSystemPlugin.getDefault());
		IAttributes attr = (IAttributes) component.getAdapter(IAttributes.class);
		if (attr == null) {
			if (holder != null) {
				String fmt = Messages.getString("Utilities.NoAttributesAdapterError"); //$NON-NLS-1$
				Object params[] = {component.getId()};
				builder.add(IStatus.ERROR, fmt, params);
				holder.setStatus(builder.createStatus("", null)); //$NON-NLS-1$
			}
			return false;
		}
		
		boolean isNeverAddComponent = attr.getBooleanAttribute(CommonAttributes.NEVER_ADD_COMPONENT, false);
		
		if (isNeverAddComponent && (holder != null)) {
			String fmt = Messages.getString("Utilities.NeverAddError"); //$NON-NLS-1$
			builder.add(IStatus.ERROR, fmt, null);
		}

		if (isNeverAddComponent && (holder != null)) {
			IComponentInstance containerInstance = (IComponentInstance) EcoreUtil
			.getRegisteredAdapter(container.getEObject(), IComponentInstance.class);
			Object params[] = { component.getFriendlyName(), containerInstance.getName() };
			holder.setStatus(builder.createMultiStatus(Messages.getString("Utilities.CantAddObjectError"), params)); //$NON-NLS-1$
		}
		return isNeverAddComponent;
	}

	private static boolean containsExclusiveChild(EObject[] potentialSiblings) {
		if (potentialSiblings == null)
			return false;
		
		for (int i = 0; i < potentialSiblings.length; i++) {
			IComponentInstance instance = 
				(IComponentInstance) EcoreUtil.getRegisteredAdapter(potentialSiblings[i], IComponentInstance.class);
			IAttributes attributes =  
				(IAttributes) instance.getComponent().getAdapter(IAttributes.class);
			if (attributes.getBooleanAttribute(CommonAttributes.IS_EXCLUSIVE_CHILD_LAYOUT_OBJECT, false))
				return true;
		}
		
		return false;
	}

	static EObject[] getLayoutChildren(IComponentInstance parentInstance) {
		List includedChildrenList = new ArrayList();
		EObject[] children = parentInstance.getChildren();
		if (children == null)
			return null;
		
		// include only non-transient layout objects
		for (int i = 0; i < children.length; i++) {
			IComponentInstance childInstance = 
				(IComponentInstance) EcoreUtil.getRegisteredAdapter(children[i], IComponentInstance.class);
			IComponent component = childInstance.getComponent();
			if (component != null) {
				IAttributes childAttributes =  
				(IAttributes) component.getAdapter(IAttributes.class);
				if ((childAttributes != null) &&
					childAttributes.getBooleanAttribute(CommonAttributes.IS_LAYOUT_OBJECT, false) &&
					!childAttributes.getBooleanAttribute(CommonAttributes.IS_TRANSIENT_OBJECT, false)) {
					includedChildrenList.add(children[i]);
				}
			}
		}
		
		if (includedChildrenList.isEmpty())
			return null;
		
		return (EObject[]) includedChildrenList.toArray(new EObject[includedChildrenList.size()]);
	}

	static boolean canRemoveChildrenOfTypeFromContainer(IComponent component, IContainer container) {
		return true;
	}

	static boolean allowIfNonLayoutComponent(IDisplayModel displayModel, EObject container, IComponent component) {
		if (component == null)
			return false;
		IAttributes attributes = (IAttributes) component.getAdapter(IAttributes.class);
		if (attributes.getBooleanAttribute(CommonAttributes.IS_NON_LAYOUT_OBJECT, false))
			return (container == null) || displayModel.getNonLayoutRoot().equals(container);
		else if (component.getId().startsWith("com.nokia.examples."))
			return true;
		
		return false;
	}
}
