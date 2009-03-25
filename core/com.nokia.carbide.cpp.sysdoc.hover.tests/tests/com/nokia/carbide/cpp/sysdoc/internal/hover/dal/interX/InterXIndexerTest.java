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
package com.nokia.carbide.cpp.sysdoc.internal.hover.dal.interX;

import java.net.URL;
import java.util.List;

import org.junit.Test;

import com.nokia.carbide.cpp.sysdoc.hover.BaseTest;
import com.nokia.carbide.cpp.sysdoc.hover.TestHelper;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverConstants;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverManager;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dao.PathNode;

public class InterXIndexerTest extends BaseTest {

	private static InterXIndexer indexer;

	static {
		initIndexer();
	}

	private static void initIndexer() {
		try {
			HoverManager.setTestMode(true);
			Runtime.getRuntime().gc();
			Runtime.getRuntime().gc();
			indexer = new InterXIndexer(TestHelper.getAnInterXProps());
			indexer.startIndexing();
			// wait a bit to kick of indexing
			Thread.sleep(15000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInvalidInterchangeFile() throws Exception {
		URL url = new URL(HoverConstants.SDL_WEB_ADDRESS);
		try {
			// an URL which does not refer to interX file
			InterXProperties anInterXProps = new InterXProperties(url);
			InterXIndexer indexer = new InterXIndexer(anInterXProps);
			indexer.startIndexing();
		} catch (Exception e) {
			return;
		}
		fail("Failed to report an invalid interchange indexing");
	}

	public void testSize() throws InterruptedException {
		if (indexer == null) {
			initIndexer();
		}
		assertNotNull(indexer);
		assertTrue(indexer.getSize() > 0);
	}

	public void testGetSingleConstantFQNEntity() throws Exception {
		List<PathNode> pathAsNodes = indexer.getPathAsNodes("__UHEAP_MARK");
		assertNotNull(pathAsNodes);
		assertTrue(pathAsNodes.size() > 0);
		System.out.println(" path:" + pathAsNodes.get(0).getPath());
	}

	public void testAddMultiFQNEntity() throws Exception {
		// initIndexer();
		String myPath = "MyPath";
		String myComponent = "My Component";
		// String myFQN="CTrapCleanup::NewXXX";
		String myFQN = "CActiveScheduler::Install";
		String fqnArray[] = myFQN.split("::");

		indexer.addEntry(myFQN, myComponent, myPath);
		List<PathNode> pathAsNodes = indexer.getPathAsNodes(myFQN);
		assertNotNull(pathAsNodes);
		assertTrue(pathAsNodes.size() > 0);
		PathNode node = pathAsNodes.get(0);
		assertTrue(node.getComponent().equals(myComponent));
		assertTrue(node.getName().equals(fqnArray[fqnArray.length - 1]));
		assertTrue(node.getPath().endsWith(myPath));
		System.out.println(" path:" + pathAsNodes.get(0).getPath());
	}

	public void testGetMultiFQNEntity() throws Exception {
		// initIndexer();
		List<PathNode> pathAsNodes = indexer.getPathAsNodes("time_t");
		assertNotNull(pathAsNodes);
		assertTrue(pathAsNodes.size() > 0);
		System.out.println(" path:" + pathAsNodes.get(0).getPath());
	}

}
