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


package com.nokia.sdt.displaymodel.adapter;



import com.nokia.sdt.datamodel.adapter.IModelAdapter;

import org.eclipse.core.runtime.IStatus;


/**
 * 
 * <br><br>
 * The main interface to an object in the display model
 */
public interface IDisplayObject extends IModelAdapter {

	/**
	 * @return the <code>org.eclipse.core.runtime.IStatus</code> for this object
	 */
	public IStatus getStatus();

	/**
	 * @return <code>true</code> if this object can be removed
	 */
	public boolean isRemovable();
	
	/**
	 * @return <code>true</code> if this object should be placed in the non-layout area
	 */
	public boolean isNonLayoutObject();
}
