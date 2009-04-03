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
package com.nokia.sdt.component.event;

/**
 * Encapsulates naming information for event handler methods
 *
 */
public class HandlerMethodInformation {
	
	private String displayText;

	public HandlerMethodInformation(String displayText) {
		this.displayText = displayText;
	}

	/**
	 * Get displayable text, e.g. the handler method name
	 */
	public String getDisplayText() {
		return displayText;
	}
}
