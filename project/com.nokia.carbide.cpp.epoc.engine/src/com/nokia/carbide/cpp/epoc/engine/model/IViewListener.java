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

package com.nokia.carbide.cpp.epoc.engine.model;

/**
 * This notifies of ongoing uncommitted changes to a view.  
 *
 */
public interface IViewListener {
	/**
	 * Called when changes made to view, either directly through its APIs,
	 * or indirectly, via an #update() or #revert()
	 * @param view
	 */
	void viewChanged(IView view); 
}
