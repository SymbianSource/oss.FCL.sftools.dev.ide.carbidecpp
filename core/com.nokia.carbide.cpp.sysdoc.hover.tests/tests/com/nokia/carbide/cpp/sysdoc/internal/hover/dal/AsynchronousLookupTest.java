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
 *				Deniz TURAN
 * Description: 
 * 				
 */
package com.nokia.carbide.cpp.sysdoc.internal.hover.dal;

import java.util.List;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.nokia.carbide.cpp.sysdoc.hover.BaseTest;
import com.nokia.carbide.cpp.sysdoc.hover.TestHelper;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dao.DevLibContent;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dao.NotFoundMarker;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dao.PathNode;

public class AsynchronousLookupTest extends BaseTest {

	@Test
	public void testAsynchronousLookups() throws Exception {
		// wait 20 sec till indexing is completed
		TestHelper.waitIndexingComplete(20000);

		// Single option page
		String fqn = "LOCAL_C";
		FutureTask<DevLibContent> ft = TestHelper.createAsynchronousLookup(fqn);
		DevLibContent content = ft.get(2, TimeUnit.SECONDS);
		List<PathNode> pathAsNodesList = content.getPathAsNodesList();
		assertNotNull(pathAsNodesList);
		assertNotNull(pathAsNodesList.get(0).getPath());

		// not found page
		fqn = "myRubbishAPI::dfs";
		ft = TestHelper.createAsynchronousLookup(fqn);
		content = ft.get(2, TimeUnit.SECONDS);
		assertTrue(content instanceof NotFoundMarker);

		// multiple page
		fqn = "time_t";
		ft = TestHelper.createAsynchronousLookup(fqn);
		content = ft.get(2, TimeUnit.SECONDS);
		pathAsNodesList = content.getPathAsNodesList();
		assertTrue(pathAsNodesList.size() > 1);
	}
}
