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


/**
 * 
 * <br><br>
 * Listener is the provided listener interface on an <code>ILayoutObject</code>
 */
public interface LayoutObjectListener {
	
	/**
	 * @param object a <code>ILayoutObject</code><br>
	 * The image of this object has changed
	 */
	public void imageChanged(ILayoutObject object);
	
	/**
	 * @param object a <code>ILayoutObject</code><br>
	 * The bounds of this object have changed
	 */
	public void boundsChanged(ILayoutObject object);
	
}