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


package com.nokia.sdt.component.symbian.documentation;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.IInfoItems;
import com.nokia.sdt.component.symbian.IFacetContainer;
import com.nokia.sdt.emf.component.SymbianType;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * 
 *
 */
public class InfoItemsAdapter implements IInfoItems {

	private IComponent component;
	private EStructuralFeature symbianFeature;
	private static final int MAX_ITEMS = 2;
	private static final int CLASS_INDEX = 0;
	private static final int RESOURCE_INDEX = 1;

	/**
	 * Initialize the adapter with the symbian item.
	 * Elements are of type SymbianType
	 * @param symbianFeature
	 */
	InfoItemsAdapter(Plugin plugin, IComponent component,
			EStructuralFeature symbianFeature) {
		//Check.checkArg(plugin); // EJS: not needed
		Check.checkArg(component);
		this.component = component;
		this.symbianFeature = symbianFeature;
	}

	private SymbianType getSymbianTypeFromContainer(IFacetContainer fc) {
		SymbianType symbianObj = null;
		EObject container = fc.getEMFContainer();
		Object featureObj = container.eGet(symbianFeature);
		if (featureObj instanceof SymbianType)
			symbianObj = (SymbianType) featureObj;
		
		return symbianObj;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.adapter.IHelpItems#getNumItems()
	 */
	public int getNumItems(int numDisplayableItems) {
		if (numDisplayableItems > MAX_ITEMS)
			return 2; // we return at most the class name and resource type items
		
		return numDisplayableItems;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.adapter.IHelpItems#getItemTypeLabel(int)
	 */
	public String getItemTypeLabel(int index) {
		Check.checkContract(index < MAX_ITEMS);
		if (index == CLASS_INDEX)
			return Messages.getString("InfoItemsAdapter.classNameLabel"); //$NON-NLS-1$
		if (index == RESOURCE_INDEX)
			return Messages.getString("InfoItemsAdapter.resourceNameLabel"); //$NON-NLS-1$
		return null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.adapter.IHelpItems#getItemLabel(int)
	 */
	public String getItemLabel(int index) {
		Check.checkContract(index < MAX_ITEMS);
		SymbianType symbianObj = getSymbianTypeFromContainer((IFacetContainer) component);
		String label = null;
		if (index == CLASS_INDEX)
			label = symbianObj.getClassName();
		if (index == RESOURCE_INDEX)
			label = symbianObj.getResourceType();
		if (label == null)
			label = Messages.getString("InfoItemsAdapter.notApplicable"); //$NON-NLS-1$
		return label;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.adapter.IHelpItems#getItemHelpTopic(int)
	 */
	public String getItemHelpTopic(int index) {
		Check.checkContract(index < MAX_ITEMS);
		SymbianType symbianObj = getSymbianTypeFromContainer((IFacetContainer) component);
		if (index == CLASS_INDEX)
			return symbianObj.getClassHelpTopic();
		if (index == RESOURCE_INDEX)
			return symbianObj.getResourceHelpTopic();
		return null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.adapter.IComponentAdapter#getComponent()
	 */
	public IComponent getComponent() {
		return component;
	}

}
