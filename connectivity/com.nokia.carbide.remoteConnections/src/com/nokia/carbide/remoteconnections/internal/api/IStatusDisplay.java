/**
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.remoteconnections.internal.api;

import org.eclipse.core.runtime.IStatus;

public interface IStatusDisplay {

	/**
	 * Asynchronously displays status with notification UI
	 * @param status IStatus
	 */
	void displayStatus(IStatus status);
	
	/**
	 * Synchronously displays status with notification UI
	 * Displays a prompt and runs action if user accepts.
	 * Calling thread will block until notification is closed (either by timer or by user)
	 * and if action will be called on the calling thread.
	 * NOTE: This cannot be called on display thread!<br>
	 * <code>assert Display.getCurrent() == null;</code>
	 * @param status IStatus
	 * @param prompt String
	 * @param action Runnable
	 */
	void displayStatusWithAction(IStatus status, String prompt, Runnable action);
}
