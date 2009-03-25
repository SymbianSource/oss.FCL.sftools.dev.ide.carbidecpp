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

import junit.framework.*;

public class StatefulTestSuite extends TestSuite implements IStatefulTest {

	private Map stateMap;
	
	public StatefulTestSuite() {
		super();
	}

	public StatefulTestSuite(Class theClass, String name) {
		super(theClass, name);
	}

	public StatefulTestSuite(Class theClass) {
		super(theClass);
	}

	public StatefulTestSuite(String name) {
		super(name);
	}

	public void setState(Map state) {
		this.stateMap = state;
	}

	@Override
	public void runTest(Test test, TestResult result) {
		if (test instanceof IStatefulTest) {
			IStatefulTest st = (IStatefulTest) test;
			st.setState(stateMap);
		}
		super.runTest(test, result);
	}
}
