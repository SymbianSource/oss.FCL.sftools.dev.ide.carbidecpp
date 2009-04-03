/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.epoc.engine.tests.misc;

import junit.framework.TestCase;

import org.eclipse.core.runtime.Path;

import com.nokia.cpp.internal.api.utils.core.CommonPathFinder;


public class TestPathFinder extends TestCase {
	public void testBasic() {
		CommonPathFinder finder = new CommonPathFinder();
		assertNull(finder.getCommonPath());
		
		finder.addFile(new Path("foo"));
		assertEquals(new Path(""), finder.getCommonPath());
		finder.addFile(new Path("bar"));
		assertEquals(new Path(""), finder.getCommonPath());
	}
	
	public void testCommon1() {
		CommonPathFinder finder = new CommonPathFinder();
		finder.addFile(new Path("src/foo.cpp"));
		assertEquals(new Path("src"), finder.getCommonPath());

		finder.addFile(new Path("src/bar.cpp"));
		assertEquals(new Path("src"), finder.getCommonPath());
	}
	
	public void testCommon2() {
		CommonPathFinder finder = new CommonPathFinder();
		finder.addFile(new Path("src/foo.cpp"));
		assertEquals(new Path("src"), finder.getCommonPath());

		finder.addFile(new Path("src/pop/bar.cpp"));
		assertEquals(new Path("src"), finder.getCommonPath());
	}
	
	public void testCommon3() {
		CommonPathFinder finder = new CommonPathFinder();
		finder.addFile(new Path("src/pop/foo.cpp"));
		assertEquals(new Path("src/pop"), finder.getCommonPath());

		finder.addFile(new Path("src/bar.cpp"));
		assertEquals(new Path("src"), finder.getCommonPath());
	}
	
	public void testUncommon1() {
		CommonPathFinder finder = new CommonPathFinder();
		finder.addFile(new Path("src/foo.cpp"));
		assertEquals(new Path("src"), finder.getCommonPath());

		finder.addFile(new Path("zap/bar.cpp"));
		assertEquals(new Path(""), finder.getCommonPath());
	}

	
	
}
