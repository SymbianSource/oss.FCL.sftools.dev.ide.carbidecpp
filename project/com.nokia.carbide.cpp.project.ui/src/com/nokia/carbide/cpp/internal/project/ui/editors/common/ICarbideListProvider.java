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
package com.nokia.carbide.cpp.internal.project.ui.editors.common;

import com.nokia.carbide.cpp.epoc.engine.model.IView;

import java.util.List;

/**
 * Abstract object that can fetch a list from a view.
 * Used to fetch lists on demand, rather than caching references
 * all through the undo stack. The goal is to avoid putting
 * obstacles in the path of reloading, where objects may be recreated
 * and cached references become invalid
 */
public interface ICarbideListProvider {

	/**
	 * Returns a displayable description of the list
	 */
	String getDisplayText();
	
	/**
	 * Get the list from the view
	 */
	List<Object> fetchList(IView view);
}
