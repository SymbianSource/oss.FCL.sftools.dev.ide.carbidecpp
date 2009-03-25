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

package com.nokia.sdt.component.symbian.displaymodel;

import com.nokia.sdt.component.adapter.CommonAttributes;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.adapter.*;
import com.nokia.sdt.uidesigner.ui.utils.EditorUtils;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.ObjectUtils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.*;

public abstract class LayoutContainerBase extends Container implements ILayoutContainer {

	private boolean isLayingOut;
	protected boolean isSingleVisibleChildContainer = false;
	protected Set<EObject> selectedObjects = null;
	private EObject previousVisibleChild = null;
	private ITargetFeedbackListener targetFeedbackListener;

	public LayoutContainerBase(IDisplayModel displayModel,
			IComponentInstance instance) {
		super(displayModel, instance);
		instance.addChildListener(new IComponentInstanceChildListener() {

			public void childAdded(EObject parent, EObject child) {
				layoutChildren();
				Utilities.fireImageChanged(parent);
			}

			public void childRemoved(EObject parent, EObject child) {
				layoutChildren();
				Utilities.fireImageChanged(parent);
			}

			public void childrenReordered(EObject parent) {
				layoutChildren();
				Utilities.fireImageChanged(parent);
			}
			
		});
		instance.addPropertyListener(new IComponentInstancePropertyListener() {
			public void propertyChanged(EObject componentInstance, Object propertyId) {
				handlePropertyChange(componentInstance, propertyId);
			}
		});
		isSingleVisibleChildContainer = 
			Utilities.booleanAttribute(displayModel, instance.getEObject(), 
					CommonAttributes.SWITCHABLE_CHILD_CONTAINER, false);
		if (isSingleVisibleChildContainer)
			selectedObjects = new LinkedHashSet<EObject>();
	}

	/** React to a property change on the container. */
	protected void handlePropertyChange(EObject componentInstance,
			Object propertyId) {
		
	}

	public boolean canResizeChild(ILayoutObject child) {
		// static component constraints
		boolean fixedSizeOrPositionChild = Utilities.booleanAttribute(displayModel, 
				child.getEObject(), CommonAttributes.IS_NON_RESIZABLE_OR_MOVEABLE_LAYOUT_OBJECT, false)
				|| Utilities.hasOrderedLayout(displayModel, getEObject());
	
		boolean fixedSizeChild = Utilities.booleanAttribute(displayModel, 
				child.getEObject(), CommonAttributes.IS_NON_RESIZABLE_LAYOUT_OBJECT, false);
		
		boolean result = !fixedSizeChild && !fixedSizeOrPositionChild;
	
		return result;
	}

	public boolean canMoveChild(ILayoutObject child) {
		Check.checkArg(child);
		// static component constraints
		boolean fixedPositionChild = Utilities.booleanAttribute(displayModel, 
				child.getEObject(), CommonAttributes.IS_NON_RESIZABLE_OR_MOVEABLE_LAYOUT_OBJECT, false)
				|| Utilities.hasOrderedLayout(displayModel, getEObject());
		boolean result = !fixedPositionChild;
	
		return result;
	}

	public boolean isAdapterForType(Object type) {
		return ILayoutContainer.class.equals(type) ||
			super.isAdapterForType(type);
	}

	public void layoutChildren() {
		if (isLayingOut)
			return;
		
		if (displayModel.getLookAndFeel() == null)
			return;
		
		isLayingOut = true;
		EObject[] children = instance.getChildren();
		if (children != null) {
			boolean isTransient = Utilities.isTransient(getEObject());
			for (int i = 0; i < children.length; i++) {
				EObject child = children[i];
				if (!isTransient || Utilities.findTransientObject(child).equals(getEObject())) {
					ILayoutContainer childContainer = Utilities.getLayoutContainer(child);
					if (childContainer != null)
						childContainer.layoutChildren();
					else {
						ILayout childLayout = (ILayout) EcoreUtil.getRegisteredAdapter(child, ILayout.class);
						if (childLayout != null)
							childLayout.layout(displayModel.getLookAndFeel());
					}
				}
			}
		}
		ILayout layout = (ILayout) EcoreUtil.getRegisteredAdapter(getEObject(), ILayout.class);
		if (layout != null)
			layout.layout(displayModel.getLookAndFeel());
		isLayingOut = false;
	}

	public boolean scrollsVertically() {
		return EditorUtils.isScrollableVertically(getEObject());
	}

	public boolean scrollsHorizontally() {
		return EditorUtils.isScrollableHorizontally(getEObject());
	}

	public EObject[] getVisibleChildren() {
		List<EObject> children = Utilities.getLayoutChildren(getEObject());
		if (!isSingleVisibleChildContainer) {
			return (EObject[]) children.toArray(new EObject[children.size()]);
		} else {
			if (children.isEmpty()) {
				previousVisibleChild = null;
				return null;
			}
			
			if (previousVisibleChild == null || !children.contains(previousVisibleChild))
				previousVisibleChild = children.get(0);
			
			Set<EObject> selectedChildren = new HashSet<EObject>();
			for (EObject object : selectedObjects) {
				EObject child = findDirectChild(object);
				if (child != null)
					selectedChildren.add(child);
			}
			if (!selectedChildren.isEmpty() && !selectedChildren.contains(previousVisibleChild))
				previousVisibleChild = selectedChildren.iterator().next();
			
			return new EObject[] { previousVisibleChild };
		}
	}

	private EObject findDirectChild(EObject descendent) {
		if (descendent == null)
			return null;
		
		IComponentInstance ci = Utilities.getComponentInstance(descendent);
		if (ci == null)
			return null;
		
		EObject[] children = instance.getChildren();
		List childList = children == null ? Collections.EMPTY_LIST : Arrays.asList(children);
		if (childList.contains(descendent))
			return descendent;
		
		return findDirectChild(ci.getParent());
	}

	public void setSelectedObjects(EObject[] objects) {
		if (isSingleVisibleChildContainer) {
			List<EObject> objectsList = Arrays.asList(objects);
			boolean needsRefresh = true;
			for (EObject object : objectsList) {
				if (ObjectUtils.equals(findDirectChild(object), previousVisibleChild)) {
					needsRefresh = false;
					break;
				}
			}
			selectedObjects.clear();
			selectedObjects.addAll(objectsList);
			if (needsRefresh && displayModel instanceof DisplayModelBase) {
				((DisplayModelBase)displayModel).refreshObjectInViewer(getEObject());
			}
		}
	}

	public ITargetFeedbackListener getTargetFeedbackListener() {
		if (targetFeedbackListener == null) {
			// get the feedback listener adapter from the object
			targetFeedbackListener = Utilities.getTargetFeedbackListener(getEObject());
			if (targetFeedbackListener == null) {
				boolean vertical = Utilities.hasVerticalRowLayout(displayModel, getEObject());
				boolean horizontal = Utilities.hasHorizontalRowLayout(displayModel, getEObject());
				Check.checkContract(!(vertical && horizontal)); // can't specify both!
				if (vertical || horizontal)
					targetFeedbackListener = 
						new RowLayoutTargetFeedbackListener(getEObject(), vertical);
			}
		}
		return targetFeedbackListener;
	}

	public ELayoutCategory getLayoutCategory() {
		boolean hasOrderedLayout = Utilities.hasOrderedLayout(displayModel, getEObject());
		return hasOrderedLayout ? ELayoutCategory.ORDERED : ELayoutCategory.ABSOLUTE;
	}

}
