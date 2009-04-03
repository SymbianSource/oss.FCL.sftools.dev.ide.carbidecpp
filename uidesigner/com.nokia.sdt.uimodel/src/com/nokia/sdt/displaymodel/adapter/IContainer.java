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


package com.nokia.sdt.displaymodel.adapter;

import com.nokia.sdt.datamodel.adapter.IQueryContainment;

import org.eclipse.emf.ecore.EObject;

/**
 *
 */
public interface IContainer extends IQueryContainment {

	
	/**
	 * @return <code>true</code> if children can be reordered in this container
	 */
	boolean canReorderChild(EObject child);

	/**
	 * @return <code>true</code> if this object should be collapsed in the outline
	 */
	public boolean collapsedInOutline();

}
