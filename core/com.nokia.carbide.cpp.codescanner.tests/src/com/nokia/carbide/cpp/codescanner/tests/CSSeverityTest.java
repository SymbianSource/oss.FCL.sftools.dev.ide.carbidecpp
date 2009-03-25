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

package com.nokia.carbide.cpp.codescanner.tests;

import junit.framework.TestCase;

import com.nokia.carbide.cpp.internal.codescanner.config.CSSeverity;

/**
 * Test cases for Enum CSSeverity.
 *
 */
public class CSSeverityTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testToString() throws Exception {
		for (CSSeverity severity : CSSeverity.values()) {
			assertTrue(severity.toString().length() > 0);
		}
	}

}
