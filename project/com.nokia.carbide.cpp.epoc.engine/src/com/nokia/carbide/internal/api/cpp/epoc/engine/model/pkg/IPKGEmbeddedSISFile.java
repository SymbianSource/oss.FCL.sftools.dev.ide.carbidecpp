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
package com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg;

import org.eclipse.core.runtime.IPath;

import java.util.Map;

/**
 * Interface representing an embedded sis file in a pkg file
 * @since 2.0
 */
public interface IPKGEmbeddedSISFile extends IPKGStatement {

	/**
	 * Get the container for the sis file statement or null if top-level
	 * statement
	 * 
	 * @return IPKGStatementContainer
	 */
	IPKGStatementContainer getContainer();

	/**
	 * Get the source files as a map of EPKGLanguage to IPath.  If language
	 * independent install file, only a single entry mapped to
	 * EPKGLanguage#INDEPENDENT returned
	 * 
	 * @see EPKGLanguage
	 * @return Map
	 */
	Map<EPKGLanguage, IPath> getSourceFiles();

	/**
	 * Set the uid
	 * @param uid the uid string
	 */
	public void setUid(String uid);

	/**
	 * Get the uid
	 * @return uid string
	 */
	public String getUid();

	/**
	 * @return a copy of this
	 */
	IPKGEmbeddedSISFile copy();

}
