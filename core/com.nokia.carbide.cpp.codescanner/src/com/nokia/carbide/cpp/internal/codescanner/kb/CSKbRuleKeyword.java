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

package com.nokia.carbide.cpp.internal.codescanner.kb;

/**
 * A class for handling keywords from a CodeScanner knowledge base rule.
 *
 */
public class CSKbRuleKeyword {

	// private members
	private String content;
	private String type;

	/**
	 * Constructor.
	 * @param content - content of a keyword
	 * @param type - type of a keyword
	 */
	public CSKbRuleKeyword(String content, String type) {
		this.content = content;
		this.type = type;
	}

	/**
	 * Retrieve keyword content.
	 * @return keyword content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Retrieve keyword type.
	 * @return keyword type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Set keyword content.
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Set keyword type.
	 */
	public void setType(String type) {
		this.type = type;
	}

}
