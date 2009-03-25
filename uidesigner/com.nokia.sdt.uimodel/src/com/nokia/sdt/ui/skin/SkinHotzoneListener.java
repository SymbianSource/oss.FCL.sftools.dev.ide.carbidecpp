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

package com.nokia.sdt.ui.skin;

import com.nokia.sdt.ui.skin.ISkin.Hotzone;

/**
 * 
 * <br><br>
 * The provided listener interface on a <code>Hotzone</code>
 */
public interface SkinHotzoneListener {
	
	/**
	 * @param hotzone as <code>ISkin.Hotzone</code><br>
	 * The hotzone was pressed
	 */
	public void pressed(Hotzone hotzone);
}