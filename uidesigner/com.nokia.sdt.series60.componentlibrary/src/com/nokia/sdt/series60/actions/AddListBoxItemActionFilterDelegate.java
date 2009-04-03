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
/**
 * 
 */
package com.nokia.sdt.series60.actions;

import com.nokia.sdt.component.symbian.actionFilter.BaseAddComponentActionFilterDelegate;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;

import org.eclipse.emf.ecore.EObject;

/**
 * 
 *
 */
public class AddListBoxItemActionFilterDelegate extends BaseAddComponentActionFilterDelegate {

	private static final String LIST_BOX_ITEM_ID = "com.nokia.sdt.series60.ListBoxItem"; //$NON-NLS-1$
	private static final String LIST_BOX_ID_PREFIX = "com.nokia.sdt.series60.CAknList"; //$NON-NLS-1$

	private EObject getListbox(EObject object) {
		IComponentInstance instance = ModelUtils.getComponentInstance(object);
		if (instance == null)
			return null;
		if (instance.getComponentId().startsWith(LIST_BOX_ID_PREFIX))
			return object;
		
		EObject parent = instance.getParent();
		instance = ModelUtils.getComponentInstance(parent);
		if (instance == null)
			return null;
		if (instance.getComponentId().startsWith(LIST_BOX_ID_PREFIX))
			return parent;
		
		return null;
		
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.series60.actions.BaseAddComponentActionFilterDelegate#getAddedComponentId(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected String getAddedComponentId(EObject target) {
		return LIST_BOX_ITEM_ID;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.series60.actions.BaseAddComponentActionFilterDelegate#isLegalTarget(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected boolean isLegalTarget(EObject target) {
		return getListbox(target) != null;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.actionFilter.BaseAddComponentActionFilterDelegate#getAddTarget(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected EObject getAddTarget(EObject target) {
		return getListbox(target);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.actionFilter.BaseAddComponentActionFilterDelegate#getInsertPosition(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected int getInsertPosition(EObject target) {
		IComponentInstance instance = ModelUtils.getComponentInstance(target);
		if (instance != null && instance.getComponentId().equals(LIST_BOX_ITEM_ID)) {
			EObject listBox = getListbox(target);
			IComponentInstance list = ModelUtils.getComponentInstance(listBox);
			EObject[] kids = list.getChildren();
			for (int i = 0; i < kids.length; i++) {
				if (kids[i] == target)
					return i + 1;
			}
		}
		return super.getInsertPosition(target);
	}
}
