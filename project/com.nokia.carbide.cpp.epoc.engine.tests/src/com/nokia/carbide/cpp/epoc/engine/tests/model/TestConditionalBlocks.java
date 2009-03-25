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
import com.nokia.carbide.cpp.epoc.engine.tests.ParserConfigurationBase;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ASTFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTopLevelNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.IDocumentParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ParserFactory;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.ASTPreprocessor;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.ConditionalBlock;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IConditionalBlock;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IfViewRegion;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.IDocument;

/**
 * Test that the expected regions and contents are generated in
 * the filtered view.
 * <p>
 * Note: checking of regions is done in TestPreprocessor, since
 * we actually parse stuff there.
 *
 */
public class TestConditionalBlocks extends BaseViewTests {
	private IViewConfiguration[] allConfigurationsAccepted;
	private IViewConfiguration[] allConfigurationsAll;

	/* (non-Javadoc)
	 * @see src.BaseViewTests#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		AcceptedNodesViewFilter accepted = new AcceptedNodesViewFilter();
		AllNodesViewFilter all = new AllNodesViewFilter();
		allConfigurationsAll = new IViewConfiguration[] {
			getFooConfiguration(all), getFooBarConfiguration(all), getBarConfiguration(all)	
		};
		allConfigurationsAccepted = new IViewConfiguration[] {
				getFooConfiguration(accepted), getFooBarConfiguration(accepted), getBarConfiguration(accepted)	
		};
		
		parserConfig = new ParserConfigurationBase(projectPath) {

			@Override
			protected IASTTranslationUnit parse(IPath path, IDocument document) {
				return null;
			}
			
		};

	}
	
	/**
	 * Using the view config and filter, ensure that the expected
	 * regions are generated.
	 */
	private void runTest(IViewConfiguration viewConfig, 
			IASTNode[] sourceNodeArray,
			IConditionalBlock expBlock) {

		IASTTranslationUnit ppTu = ASTFactory.createTranslationUnit();
		for (IASTNode node : sourceNodeArray)
			ppTu.getNodes().add((IASTTopLevelNode) node.copy());
		
		ASTPreprocessor preprocessor = new ASTPreprocessor(
				parserConfig.getTranslationUnitProvider(),
				parserConfig.getIncludeFileLocator(),
				parserConfig.getModelDocumentProvider());
		
		preprocessor.preprocess(ppTu, viewConfig.getViewFilter(), viewConfig.getMacros());

		IConditionalBlock rootBlock = preprocessor.getRootBlock();
		if (false)
			dumpBlockTree(viewConfig, rootBlock);
		compareBlockTree(viewConfig, expBlock, rootBlock);
		
	}

	public void testFlat() throws Exception {
		runTest(getFooConfiguration(new AllNodesViewFilter()), 
				new IASTNode[] { },
			new ConditionalBlock()
			);
		IConditionalBlock rootBlock = new ConditionalBlock();
		rootBlock.addChild(new ConditionalBlock(null, 
				new IASTNode[] { stmt_A, stmt_B, stmt_C }, new IConditionalBlock[] {}));
		runTest(allConfigurationsAll,  
				new IASTNode[] {
			stmt_A,
			stmt_B,
			stmt_C },
			rootBlock
			);
	}
	
	public void testIf() throws Exception {
		IASTNode[] nodes = new IASTNode[] {
				stmt_ifdef_FOO,
				stmt_B,
				stmt_endif
				};
		IConditionalBlock rootBlock = new ConditionalBlock((IASTPreprocessorStatement) null); 
		IConditionalBlock ifBlock = new IfViewRegion(stmt_ifdef_FOO, stmt_endif);
		IConditionalBlock defFooBlock = new ConditionalBlock(stmt_ifdef_FOO,
				stmt_ifdef_FOO, new IASTNode[] { stmt_B}, stmt_endif,
				null);
		ifBlock.addChild(defFooBlock);
		rootBlock.addChild(ifBlock);
		runTest(allConfigurationsAll, 
				nodes,
				rootBlock);
	}
	public void testIf2() throws Exception {
		IASTNode[] nodes = new IASTNode[] {
				stmt_A,
				stmt_ifdef_FOO,
				stmt_B,
				stmt_endif,
				stmt_C };
		IConditionalBlock rootBlock = new ConditionalBlock(); 
		IConditionalBlock preBlock = new ConditionalBlock(null, 
				new IASTNode[] { stmt_A }, null);
		IConditionalBlock ifBlock = new IfViewRegion(stmt_ifdef_FOO, stmt_endif);
		IConditionalBlock defFooBlock = new ConditionalBlock(stmt_ifdef_FOO,
				stmt_ifdef_FOO,
				new IASTNode[] { stmt_B },
				stmt_endif,
				null);
		ifBlock.addChild(defFooBlock);
		IConditionalBlock postBlock = new ConditionalBlock(null, 
				new IASTNode[] { stmt_C }, null);
		rootBlock.addChild(preBlock);
		rootBlock.addChild(ifBlock);
		rootBlock.addChild(postBlock);
		runTest(allConfigurationsAll, 
				nodes,
				rootBlock);
	}
	public void testIf3() throws Exception {
		IASTNode[] nodes = new IASTNode[] {
				stmt_A,
				stmt_ifdef_FOO,
				stmt_B,
				stmt_endif,
				stmt_ifdef_BAR,
				stmt_C,
				stmt_endif2,
				stmt_D };
		IConditionalBlock rootBlock = new ConditionalBlock(); 
		IConditionalBlock preBlock = new ConditionalBlock(null, 
				new IASTNode[] { stmt_A }, null);
		IConditionalBlock ifBlock = new IfViewRegion(stmt_ifdef_FOO, stmt_endif);
		IConditionalBlock defFooBlock = new ConditionalBlock(stmt_ifdef_FOO, 
				stmt_ifdef_FOO, new IASTNode[] { stmt_B }, stmt_endif, 
				null);
		ifBlock.addChild(defFooBlock);

		IConditionalBlock ifRegion2 = new IfViewRegion(stmt_ifdef_BAR, stmt_endif2);
		IConditionalBlock defBarBlock = new ConditionalBlock(stmt_ifdef_BAR, 
				stmt_ifdef_BAR, new IASTNode[] { stmt_C },stmt_endif2, 
				null);
		ifRegion2.addChild(defBarBlock);

		IConditionalBlock postBlock = new ConditionalBlock(null, 
				new IASTNode[] { stmt_D }, null);
		rootBlock.addChild(preBlock);
		rootBlock.addChild(ifBlock);
		rootBlock.addChild(ifRegion2);
		rootBlock.addChild(postBlock);
		runTest(allConfigurationsAll, 
				nodes,
				rootBlock);
	}
	public void testIfElse() throws Exception {
		IASTNode[] nodes = new IASTNode[] {
				stmt_A,
				stmt_ifdef_FOO,
				stmt_B,
				stmt_else,
				stmt_C,
				stmt_endif,
				stmt_D };
		IConditionalBlock rootBlock = new ConditionalBlock(); 
		IConditionalBlock preBlock = new ConditionalBlock(null, 
				new IASTNode[] { stmt_A }, null);
		IConditionalBlock ifBlock = new IfViewRegion(stmt_ifdef_FOO, stmt_endif);
		IConditionalBlock defFooBlock = new ConditionalBlock(stmt_ifdef_FOO, 
				stmt_ifdef_FOO, new IASTNode[] { stmt_B }, stmt_else,
				null);
		ifBlock.addChild(defFooBlock);
		IConditionalBlock elseBlock = new ConditionalBlock(stmt_else, 
				stmt_else, new IASTNode[] { stmt_C }, stmt_endif,
				null);
		IConditionalBlock postBlock = new ConditionalBlock(null, 
				new IASTNode[] { stmt_D }, null);
		ifBlock.addChild(elseBlock);
		rootBlock.addChild(preBlock);
		rootBlock.addChild(ifBlock);
		rootBlock.addChild(postBlock);
		runTest(allConfigurationsAll, 
				nodes,
				rootBlock);
	}
	public void testIfElif() throws Exception {
		IASTNode[] nodes = new IASTNode[] {
				stmt_A,
				stmt_ifdef_FOO,
				stmt_B,
				stmt_elif_def_BAR,
				stmt_C,
				stmt_endif,
				stmt_D };
		IConditionalBlock rootBlock = new ConditionalBlock(); 
		IConditionalBlock preBlock = new ConditionalBlock(null, 
				new IASTNode[] { stmt_A }, null);
		IConditionalBlock ifBlock = new IfViewRegion(stmt_ifdef_FOO, stmt_endif);
		IConditionalBlock defFooBlock = new ConditionalBlock(stmt_ifdef_FOO, 
				stmt_ifdef_FOO, new IASTNode[] { stmt_B }, stmt_elif_def_BAR, 
				null);
		ifBlock.addChild(defFooBlock);
		IConditionalBlock elifDefBarBlock = new ConditionalBlock(stmt_elif_def_BAR, 
				stmt_elif_def_BAR, new IASTNode[] { stmt_C }, stmt_endif,
				null);
		ifBlock.addChild(elifDefBarBlock);
		IConditionalBlock postBlock = new ConditionalBlock(null, 
				new IASTNode[] { stmt_D }, null);
		rootBlock.addChild(preBlock);
		rootBlock.addChild(ifBlock);
		rootBlock.addChild(postBlock);
		runTest(allConfigurationsAll, 
				nodes,
				rootBlock);
	}

	public void testIfElif2() throws Exception {
		IASTNode[] nodes = new IASTNode[] {
				stmt_A,
				stmt_ifdef_FOO,
				stmt_B,
				stmt_elif_def_BAR,
				stmt_C,
				stmt_endif,
				stmt_D };
		IConditionalBlock rootBlock = new ConditionalBlock(); 
		IConditionalBlock preBlock = new ConditionalBlock(null, 
				new IASTNode[] { stmt_A }, null);
		IConditionalBlock ifBlock = new IfViewRegion(stmt_ifdef_FOO, stmt_endif);
		IConditionalBlock defFooBlock = new ConditionalBlock(stmt_ifdef_FOO, 
				stmt_ifdef_FOO, new IASTNode[] { stmt_B }, stmt_elif_def_BAR, 
				null);
		ifBlock.addChild(defFooBlock);
		IConditionalBlock elifDefBarBlock = new ConditionalBlock(stmt_elif_def_BAR, 
				stmt_elif_def_BAR, new IASTNode[] { stmt_C }, stmt_endif, null);
		ifBlock.addChild(elifDefBarBlock);
		IConditionalBlock postBlock = new ConditionalBlock(null, 
				new IASTNode[] { stmt_D }, null);
		rootBlock.addChild(preBlock);
		rootBlock.addChild(ifBlock);
		rootBlock.addChild(postBlock);
		runTest(allConfigurationsAccepted, 
				nodes,
				rootBlock);
	}
	
	public void testIfElseNested() throws Exception {
		IASTNode[] nodes = new IASTNode[] {
				stmt_A,
				stmt_ifdef_FOO,
					stmt_B,
					stmt_ifdef_BAR,
						stmt_C,
					stmt_endif2,
				stmt_else,
					stmt_D,
				stmt_endif,
				stmt_E };
		IConditionalBlock rootBlock = new ConditionalBlock(); 
		IConditionalBlock preBlock = new ConditionalBlock(null, 
				new IASTNode[] { stmt_A }, null);
		IConditionalBlock ifBlock = new IfViewRegion(stmt_ifdef_FOO, stmt_endif);
		IConditionalBlock defFooBlock = new ConditionalBlock(stmt_ifdef_FOO, 
				stmt_ifdef_FOO, new IASTNode[] { stmt_B }, stmt_ifdef_BAR,
				null);
		ifBlock.addChild(defFooBlock);

		IConditionalBlock ifRegion2 = new IfViewRegion(stmt_ifdef_BAR, stmt_endif2);
		IConditionalBlock defBarBlock = new ConditionalBlock(stmt_ifdef_BAR, 
				stmt_ifdef_BAR, new IASTNode[] { stmt_C }, stmt_endif2, null);
		ifRegion2.addChild(defBarBlock);
		
		ifBlock.addChild(ifRegion2);

		IConditionalBlock elseBlock = new ConditionalBlock(stmt_else,
				stmt_else, new IASTNode[] { stmt_D }, stmt_endif, null);
		ifBlock.addChild(elseBlock);

		IConditionalBlock postBlock = new ConditionalBlock(null, 
				new IASTNode[] { stmt_E }, null);
		rootBlock.addChild(preBlock);
		rootBlock.addChild(ifBlock);
		rootBlock.addChild(postBlock);
		
		runTest(allConfigurationsAll, 
				nodes,
				rootBlock);
	}

	private void runTest(IViewConfiguration[] viewConfigs, 
			IASTNode[] sourceNodeArray,
			IConditionalBlock expBlock) {
		for (IViewConfiguration config : viewConfigs)
			runTest(config, sourceNodeArray, expBlock);
	}
	
	/////////////////////////////////////////////
	
	protected void regionTest(IViewConfiguration config, String text) {
		IDocumentParser parser = ParserFactory.createPreParser();
		IASTTranslationUnit tu = parser.parse(new Path("test.txt"), DocumentFactory.createDocument(text));
		assertFalse(parser.hadErrors());

		ASTPreprocessor preprocessor = createPreprocessor(parserConfig);
		preprocessor.preprocess(tu, config.getViewFilter(), config.getMacros());
		checkBlockInfo(preprocessor.getRootBlock());
	}
	
	private void regionTest(IViewConfiguration[] viewConfigs, 
			String text) {
		for (IViewConfiguration config : viewConfigs)
			regionTest(config,text);
	}
	
	public void testRegEmpty() {
		regionTest(allConfigurationsAll, "");
	}
	
	public void testRegSimple() {
		regionTest(allConfigurationsAll, 
				"line 1\n"+
				"line 2\n");
		
	}
	
	public void testRegAllIf() {
		regionTest(allConfigurationsAll, 
				"#if 1\n"+
				"line 1\n"+
				"line 2\n"+
				"#endif");
		
	}

	public void testRegSomeIf() {
		regionTest(allConfigurationsAll, 
				"line 1\n"+
				"#if 1\n"+
				"line 2\n"+
				"#endif\n"+
				"line 3\n"
				);
		
	}


	public void testRegSomeIfElse() {
		regionTest(allConfigurationsAll, 
				"line 1\n"+
				"#if 1\n"+
				"line 2\n"+
				"#else\n"+
				"better line 2\n"+
				"#endif\n"+
				"line 3\n"
				);
	}

	public void testRegSomeIfElif() {
		regionTest(allConfigurationsAll, 
				"line 1\n"+
				"#if 1\n"+
				"line 2\n"+
				"#elif 2\n"+
				"better line 2\n"+
				"#endif\n"+
				"line 3\n"
				);
	}

	public void testRegSomeIfElifElse() {
		regionTest(allConfigurationsAll, 
				"line 1\n"+
				"#if 1\n"+
				"line 2\n"+
				"#elif 2\n"+
				"better line 2\n"+
				"#else\n"+
				"best line 2\n"+
				"#endif\n"+
				"line 3\n"
				);
	}
	
	public void testRegIfTwice() {
		regionTest(allConfigurationsAll, 
				"line 1\n"+
				"#if 1\n"+
				"line 2\n"+
				"#endif 2\n"+
				"better line 2\n"+
				"#if 0\n"+
				"more stuff\n"+
				"#endif\n"+
				"line 3\n"
				);
	}	
	
	public void testRegIfNsted() {
		regionTest(allConfigurationsAll, 
				"line 1\n"+
				"#if 1\n"+
				"line 2\n"+
				"	#if 0\n"+
				"	line 33\n"+
				"	#endif 2\n"+
				"better line 2\n"+
				"	#if 0\n"+
				"	more stuff\n"+
				"	#endif\n"+
				"#else\n"+
				"// nothing\n"+
				"#endif\n"+
				"line 3\n"
				);
	}	
}
