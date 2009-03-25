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
package com.nokia.sdt.referenceprojects.test;

import java.util.Map;

import junit.framework.TestCase;

/**
 * Subclass of TestCase implementing IStatefulTest.
 * This is just for convenience, tests can also just implement
 * IStatefulTest, e.g. when they need to derive from some other
 * class.
 *
 */
public class StatefulTestCase extends TestCase implements IStatefulTest{

	protected Map stateMap;
	
	public void setState(Map state) {
		this.stateMap = state;
	}

	public Object getState(String key) {
		assertNotNull(stateMap);
		return stateMap.get(key);
	}
}
