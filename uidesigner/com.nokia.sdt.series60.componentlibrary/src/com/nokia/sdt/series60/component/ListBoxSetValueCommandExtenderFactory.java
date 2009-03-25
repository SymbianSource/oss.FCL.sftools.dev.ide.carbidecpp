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
 * When the list box type is changed to Markable list, 
 * add the appropriate system menu pane.
 * 
 *
 */
public class ListBoxSetValueCommandExtenderFactory implements IImplFactory {
	

	public Object getImpl(EObject componentInstance) {
		return new ListBoxSetValueCommandExtender(componentInstance);
	}
}
