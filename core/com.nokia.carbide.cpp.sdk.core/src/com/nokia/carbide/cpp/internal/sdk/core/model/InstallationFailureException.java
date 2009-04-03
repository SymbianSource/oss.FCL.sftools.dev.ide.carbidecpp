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
*/
package com.nokia.carbide.cpp.internal.sdk.core.model;

/**
 * A checked exception representing a failure.
 * 
 */
public class InstallationFailureException extends Exception {
	
	/**
	 * All serializable objects should have a stable serialVersionUID
	 */
	private static final long serialVersionUID = 5811849709436495364L;
	
	/**
	 * Constructs a <code>InstallationFailureException</code> with the specified detail
	 * message.
	 * 
	 * @param cause The cause of the exception. May be <code>null</code>.
	 */
	public InstallationFailureException(Throwable cause) {
		super(cause);
	}
}
