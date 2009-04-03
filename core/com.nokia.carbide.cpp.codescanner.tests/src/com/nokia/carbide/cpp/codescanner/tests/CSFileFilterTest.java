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

import com.nokia.carbide.cpp.internal.codescanner.config.CSFileFilter;

/**
 * Test cases for class CSFileFilter.
 */
public class CSFileFilterTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetFilterRE() throws Exception {
		String reString = "abcd*.\\.123";
		CSFileFilter fileFilter1 = new CSFileFilter(null);
		if (fileFilter1.getFilterRE() != null)
			fail();
		CSFileFilter fileFilter2 = new CSFileFilter(reString);
		assertEquals(fileFilter2.getFilterRE(), reString);
	}

	public void testSetFilterRE() throws Exception {
		String reString = "abcd*.\\.123";
		CSFileFilter fileFilter1 = new CSFileFilter(reString);
		fileFilter1.setFilter(null);
		if (fileFilter1.getFilterRE() != null)
			fail();
		fileFilter1.setFilter(reString);
		assertEquals(fileFilter1.getFilterRE(), reString);
	}

}
