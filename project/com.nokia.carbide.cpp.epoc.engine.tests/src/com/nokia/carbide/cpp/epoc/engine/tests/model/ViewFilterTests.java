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

package com.nokia.carbide.cpp.epoc.engine.tests.model;

import com.nokia.carbide.cpp.epoc.engine.DocumentFactory;
import com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AcceptedNodesViewFilter;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AllNodesViewFilter;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IViewFilter;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.SharedNodesViewFilter;
import com.nokia.carbide.cpp.epoc.engine.tests.ParserConfigurationBase;
import com.nokia.carbide.cpp.epoc.engine.tests.model.dummy.DummyModel;
import com.nokia.carbide.cpp.epoc.engine.tests.model.dummy.IDummyView;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ASTFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTopLevelNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorResults;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.ASTPreprocessor;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.IDocument;

/**
 * Test that filtering source into views provides the expected
 * nodes.
 *
 */
public class ViewFilterTests extends BaseViewTests {

	private DummyModel model;

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.tests.model.BaseViewTests#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		parserConfig = new ParserConfigurationBase(projectPath) {

			@Override
			protected IASTTranslationUnit parse(IPath path, IDocument document) {
				return null;
			}
			
		};

	}
	
	public void testAllNodesFilter() throws Exception {
		// all nodes are included
		runTestAllConfigs(new AllNodesViewFilter(), 
				new IASTNode[] { }, 
				new IASTNode[] { });
		
		runTestAllConfigs(new AllNodesViewFilter(), 
				new IASTNode[] { stmt_A, stmt_B, stmt_C }, 
				new IASTNode[] { stmt_A, stmt_B, stmt_C } 
		);
		runTestAllConfigs(new AllNodesViewFilter(), 
				new IASTNode[] { stmt_A, stmt_ifdef_FOO, stmt_B, stmt_endif, stmt_C }, 
				new IASTNode[] { stmt_A, stmt_B, stmt_C } 
		);
		runTestAllConfigs(new AllNodesViewFilter(), 
				new IASTNode[] { 
			stmt_A, 
			stmt_ifdef_FOO, 
			stmt_B, 
			stmt_else,
			stmt_C,
			stmt_endif, 
			stmt_D }, 
				new IASTNode[] { stmt_A, stmt_B, stmt_C, stmt_D } 
		);

	}

	/**
	 * @param filter
	 * @param nodes
	 * @param nodes2
	 */
	protected void runTestAllConfigs(IViewFilter filter, IASTNode[] nodes, IASTNode[] viewNodes) {
		runTestForConfigs(new IViewConfiguration[] {
				getFooConfiguration(filter), 
				getBarConfiguration(filter), 
				getFooBarConfiguration(filter)
			}, nodes, viewNodes);
	}

	/**
	 * @param filter
	 * @param nodes
	 * @param nodes2
	 */
	protected void runTestForConfigs(IViewConfiguration[] viewConfigs, IASTNode[] nodes, IASTNode[] viewNodes) {
		for (IViewConfiguration config : viewConfigs) {
			runTest(config, nodes, viewNodes);
		}
	}

	public void testSharedNodesFilter() throws Exception {
		// nodes inside conditionals are excluded
		runTestAllConfigs(new SharedNodesViewFilter(), 
				new IASTNode[] { stmt_A, stmt_ifdef_FOO, stmt_B, stmt_endif, stmt_C }, 
				new IASTNode[] { stmt_A, stmt_C } 
		);
		
	}
	public void testSharedNodesFilter2() throws Exception {
		// nodes inside conditionals are excluded
		runTestAllConfigs(new SharedNodesViewFilter(), 
				new IASTNode[] { 
			stmt_A, 
			stmt_ifdef_FOO, 
			stmt_B, 
			stmt_else,
			stmt_C,
			stmt_endif, 
			stmt_D }, 
				new IASTNode[] { stmt_A, stmt_D } 
		);
	}

	public void testAcceptedNodesFilter1() throws Exception {
		runTest(getFooConfiguration(new AcceptedNodesViewFilter()), 
				new IASTNode[] { stmt_A, stmt_ifdef_FOO, stmt_B, stmt_endif, stmt_C }, 
				new IASTNode[] { stmt_A, stmt_B, stmt_C } 
		);
		runTest(getFooConfiguration(new AcceptedNodesViewFilter()), 
				new IASTNode[] { stmt_A, stmt_ifdef_BAR, stmt_B, stmt_endif, stmt_C }, 
				new IASTNode[] { stmt_A, stmt_C } 
		);
		runTest(getBarConfiguration(new AcceptedNodesViewFilter()), 
				new IASTNode[] { stmt_A, stmt_ifdef_FOO, stmt_B, stmt_endif, stmt_C }, 
				new IASTNode[] { stmt_A, stmt_C } 
		);
		runTest(getBarConfiguration(new AcceptedNodesViewFilter()), 
				new IASTNode[] { stmt_A, stmt_ifdef_BAR, stmt_B, stmt_endif, stmt_C }, 
				new IASTNode[] { stmt_A, stmt_B, stmt_C } 
		);
		
	}

	public void testAcceptedNodesFilter2() throws Exception {
		runTest(getFooConfiguration(new AcceptedNodesViewFilter()), 
				new IASTNode[] { 
				stmt_A, 
				stmt_ifdef_FOO, 
				stmt_B,
				stmt_else,
				stmt_C,
				stmt_endif,
				stmt_D
				}, 
				new IASTNode[] { stmt_A, stmt_B, stmt_D } 
		);
		runTest(getFooConfiguration(new AcceptedNodesViewFilter()), 
				new IASTNode[] { 
			stmt_A, 
			stmt_ifdef_BAR, 
			stmt_B,
			stmt_else,
			stmt_C,
			stmt_endif,
			stmt_D
			}, 
			new IASTNode[] { stmt_A, stmt_C, stmt_D } 
		);
		
	}

	public void testAcceptedNodesFilterNested() throws Exception {
		// make sure nesting doesn't confuse the filter
		IASTNode[] nodes = new IASTNode[] { 
						stmt_A, 
						stmt_ifdef_FOO,
							stmt_B,
							stmt_ifdef_BAR,
							stmt_C,
							stmt_else,
							stmt_D,
							stmt_endif,
						stmt_else,
							stmt_E,
						stmt_endif,
						};
		
		runTest(getFooConfiguration(new AcceptedNodesViewFilter()), 
				nodes, 
				new IASTNode[] { stmt_A, stmt_B, stmt_D } 
		);

		runTest(getBarConfiguration(new AcceptedNodesViewFilter()), 
				nodes, 
				new IASTNode[] { stmt_A, stmt_E } 
		);

		runTest(getFooBarConfiguration(new AcceptedNodesViewFilter()), 
				nodes, 
				new IASTNode[] { stmt_A, stmt_B, stmt_C } 
		);
	
	}

	public void testAcceptedNodesFilterDualTarg() throws Exception {
		// TODO: make a test with FOO || BAR
		runTestForConfigs(
				new IViewConfiguration[] {
						//getFooConfiguration(), 
						//getBarConfiguration(), 
						getFooBarConfiguration(new AcceptedNodesViewFilter())
				},
				new IASTNode[] { 
				stmt_A, 
				stmt_ifdef_FOO, 
				stmt_B,
				stmt_endif,
				stmt_ifdef_BAR,
				stmt_C,
				stmt_endif,
				stmt_D
				}, 
				new IASTNode[] { stmt_A, stmt_B, stmt_C, stmt_D } 
		);
		
	}
	
	protected void makeModel(String text) {
		IDocument document = DocumentFactory.createDocument(text);
		model = new DummyModel(new Path("test.txt"), document);

		model.parse();
	}
	
	protected IDummyView getView(IViewConfiguration config) {
		IDummyView view = (IDummyView) model.createView(config);
		assertNotNull(view);
		return view;
	}

	/**
	 * Using the view config and filter, ensure that the source
	 * nodes are filtered into the given view nodes.
	 */
	private void runTest(IViewConfiguration viewConfig, 
			IASTNode[] sourceNodeArray, IASTNode[] viewNodeArray) {
		
		IASTTranslationUnit ppTu = ASTFactory.createTranslationUnit();
		for (IASTNode node : sourceNodeArray)
			ppTu.getNodes().add((IASTTopLevelNode) node.copy());
		
		ASTPreprocessor preprocessor = createPreprocessor(parserConfig);
		
		IPreprocessorResults results = preprocessor.preprocess(ppTu, viewConfig.getViewFilter(), viewConfig.getMacros());
		
		IASTTranslationUnit tu = results.getFilteredTranslationUnit();
		
		IASTTopLevelNode[] viewNodes = tu.getNodes().toArray(new IASTTopLevelNode[0]);
		
		dumpNodes(viewConfig, viewNodes);
		compareNodes(viewConfig, viewNodes, viewNodeArray);
		
	}

}
