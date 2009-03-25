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

import junit.framework.Test;

/**
 * Extension of the Test interface
 * that accepts a Map containing initialized
 * data used as input to a test.
 *
 */
public interface IStatefulTest extends Test {
		
	/**
	 * Provides an unmodifiable Map containing
	 * state that can be used by the test
	 */
	void setState(Map state);
}
