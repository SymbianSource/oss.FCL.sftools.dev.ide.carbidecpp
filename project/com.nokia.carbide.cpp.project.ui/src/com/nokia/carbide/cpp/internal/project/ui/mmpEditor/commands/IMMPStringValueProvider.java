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
package com.nokia.carbide.cpp.internal.project.ui.mmpEditor.commands;

import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;

/**
 * Abstract object that can fetch a String value from an MMP view.
 */
public interface IMMPStringValueProvider {

	/**
	 * Returns a displayable description of the value
	 */
	String getDisplayText();
	
	/**
	 * Get the string from the view
	 */
	String fetchString(IMMPView view);
	
	/**
	 * Store a new value in the view
	 */
	void storeString(IMMPView view, String value);
}
