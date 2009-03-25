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

import java.util.HashMap;
import java.util.Map;

import junit.extensions.TestSetup;
import junit.framework.*;

/**
 * Extend TestSetup with support for a Map with test state.
 * Since this class implements IStatefulTest is can receive,
 * and optionally supplement, state from an outer context,
 * or it can create a new map with state.
 * 
 * This class also passes state into any tests it wraps.
 */
public class StatefulTestSetup extends TestSetup implements IStatefulTest {

	private Map stateMap;
	
	public StatefulTestSetup(Test test) {
		super(test);
	}
	
	public void setState(Map state) {
		// Check we don't have some inconsistency where we've
		// the state is set more than once, or we've creating a map
		// and then later another map is set.
		Assert.assertNull(stateMap);
		stateMap = state;

	}
	
	public Map getStateMap() {
		if (stateMap == null) {
			stateMap = new HashMap();
		}
		return stateMap;
	}
	
	public void putState(String key, Object value) {
		getStateMap().put(key, value);
	}
	
	public Object getState(String key) {
		return getStateMap().get(key);
	}
	
	public void removeState(String key) {
		getStateMap().remove(key);
	}
	
	@Override
	public void basicRun(TestResult result) {
		if (fTest instanceof IStatefulTest) {
			((IStatefulTest)fTest).setState(stateMap);
		}
		super.basicRun(result);
	}
}
