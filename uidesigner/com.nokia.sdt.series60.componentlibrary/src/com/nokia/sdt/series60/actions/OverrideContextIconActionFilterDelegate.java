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
import com.nokia.sdt.symbian.dm.SymbianModelUtils;
import com.nokia.sdt.symbian.ui.images.DirectEditingUtilities;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;

/**
 * 
 *
 */
public class OverrideContextIconActionFilterDelegate extends BaseAddComponentActionFilterDelegate {

	private static final String IMAGE_PROPERTY = "image"; //$NON-NLS-1$
	private static final String STATUS_PANE_CONTEXT_ID = "com.nokia.sdt.series60.StatusPaneContext"; //$NON-NLS-1$
	private static final String STATUS_PANE_CONTEXT_APP_ICON_ID = "com.nokia.sdt.series60.StatusPaneContextAppIcon"; //$NON-NLS-1$
	private static final String STATUS_PANE_ID = "com.nokia.sdt.series60.StatusPane"; //$NON-NLS-1$

	/* (non-Javadoc)
	 * @see com.nokia.sdt.series60.actions.BaseAddComponentActionFilterDelegate#getAddedComponentId(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected String getAddedComponentId(EObject target) {
		IComponentInstance instance = ModelUtils.getComponentInstance(target);
		if (instance == null || 
				SymbianModelUtils.isUnknownApplication(instance.getDesignerDataModel()))
			return STATUS_PANE_CONTEXT_ID;
		else
			return STATUS_PANE_CONTEXT_APP_ICON_ID;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.series60.actions.BaseAddComponentActionFilterDelegate#isLegalTarget(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected boolean isLegalTarget(EObject target) {
		return ModelUtils.isInstanceOf(target, STATUS_PANE_ID);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.actionFilter.BaseAddComponentActionFilterDelegate#getAddTarget(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected EObject getAddTarget(EObject target) {
		return target;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.series60.actions.BaseAddComponentActionFilterDelegate#getInitializeInstanceCommand(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected Command getInitializeInstanceCommand(EObject target, EObject instance) {
		return DirectEditingUtilities.editImageProperty(getShell(), instance, IMAGE_PROPERTY);
	}
}
