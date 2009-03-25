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

package com.nokia.carbide.cpp.internal.codescanner.config;

/**
 * A class for handling a CodeScanner file filter.
 *
 */
public class CSFileFilter {

	private String filterRE;

	/**
	 * Create an instance of CSFileFilter.
	 * @param filter - string representing the file filter regular expression
	 */
	public CSFileFilter(String filterRE) {
		this.filterRE = filterRE;
	}

	/**
	 * Get the string representing the file filter regular expression.
	 * @return string representing the file filter regular expression
	 */
	public String getFilterRE() {
		return this.filterRE;
	}

	/**
	 * Set the string representing the file filter regular expression.
	 * @param filter - string representing the file filter regular expression
	 */
	public void setFilter(String filterRE) {
		this.filterRE = filterRE;
	}

}
