/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

import com.nokia.carbide.cpp.epoc.engine.model.IView;

import java.util.List;

/**
 * A view onto PKG contents.
 */
public interface IPKGView extends IView<IPKGOwnedModel> {

	/**
	 * Get the owning PKG (convenience for IView#getModel())
	 * 
	 * @return IPKGModel
	 */
	IPKGModel getPKGModel();

	/**
	 * Get the list of languages
	 * 
	 * @return List
	 */
	List<EPKGLanguage> getLanguages();

	/**
	 * Create a new install files statement
	 * 
	 * @param container
	 *            IPKGStatementContainer can be null if top-level
	 * @return IPKGInstallFile
	 */
	IPKGInstallFile createInstallFile(IPKGStatementContainer container);

	/**
	 * Adds an install file statement
	 * 
	 * @param installFile
	 */
	void addInstallFile(IPKGInstallFile installFile);

	/**
	 * Removes an install file statement
	 * 
	 * @param installFile
	 * @since 2.0
	 */
	void removeInstallFile(IPKGInstallFile installFile);

	/**
	 * Get a flattened read-only list of all the install file statements. This
	 * is a convenience method, traversing all conditions
	 * 
	 * @return IPKGInstallFile[]
	 */
	IPKGInstallFile[] getAllInstallFiles();
	
	/**
	 * Gets the pkg header if any
	 * 
	 * @return pkg header or null
	 * @since 2.0
	 */
	IPKGHeader getPackageHeader();

	/**
	 * Sets the pkg header to the given header
	 * @param new header
	 * @since 2.0
	 */
	void setPackageHeader(IPKGHeader header);

	/**
	 * Adds an embedded sis file statement
	 * 
	 * @param installFile
	 * @since 2.0
	 */
	void addEmbeddedSISFile(IPKGEmbeddedSISFile embeddedSisFile);

	/**
	 * Removes an embedded sis file statement
	 * 
	 * @param installFile
	 * @since 2.0
	 */
	void removeEmbeddedSISFile(IPKGEmbeddedSISFile embeddedSisFile);

	/**
	 * Get a flattened read-only list of all the embedded sis file statements. This
	 * is a convenience method, traversing all conditions
	 * 
	 * @return IPKGInstallFile[]
	 * @since 2.0
	 */
	IPKGEmbeddedSISFile[] getAllEmbeddedSISFiles();
}
