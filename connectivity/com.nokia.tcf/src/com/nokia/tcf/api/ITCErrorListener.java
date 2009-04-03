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
/**
 * 
 */
package com.nokia.tcf.api;

import org.eclipse.core.runtime.IStatus;

/**
 * This interface must be implemented by the client to handle communication errors
 * from the TCF.
 */
public interface ITCErrorListener {
	
	/**
	 * This method is called from the TCF when a target communication error occurs.
	 * 
	 * @param status
	 */
	void errorOccurred(IStatus status);
}
