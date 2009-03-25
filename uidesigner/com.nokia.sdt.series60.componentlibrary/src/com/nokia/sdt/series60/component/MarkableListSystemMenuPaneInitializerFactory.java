/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.series60.component;

import com.nokia.sdt.component.adapter.IImplFactory;

import org.eclipse.emf.ecore.EObject;

/**
 * Initialize the "Mark/Unmark" or "Edit list" menu pane component.
 * 
 *
 */
public class MarkableListSystemMenuPaneInitializerFactory implements IImplFactory {
	public static final String AVKON_MARKABLE_LIST_RESOURCE_ID = "R_AVKON_MENUPANE_MARKABLE_LIST_IMPLEMENTATION"; //$NON-NLS-1$

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.adapter.IImplFactory#getImpl(org.eclipse.emf.ecore.EObject)
	 */
	public Object getImpl(EObject componentInstance) {
		return new MarkableListSystemMenuPaneInitializer(componentInstance);
	}
}
