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

import java.util.ArrayList;
import java.util.Collection;

import com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.IViewParserConfiguration;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IViewFilter;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.DefineFactory;
import com.nokia.carbide.cpp.epoc.engine.tests.BaseTest;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ASTFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorElifStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorElseStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorEndifStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorIfdefStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorTokenStreamStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IConditionalBlock;

public abstract class BaseViewTests extends BaseTest {

	protected IASTPreprocessorTokenStreamStatement stmt_A;
	protected IASTPreprocessorIfdefStatement stmt_ifdef_FOO;
	protected IASTPreprocessorTokenStreamStatement stmt_B;
	protected IASTPreprocessorEndifStatement stmt_endif;
	protected IASTPreprocessorEndifStatement stmt_endif2;
	protected IASTPreprocessorTokenStreamStatement stmt_C;
	protected IASTPreprocessorIfdefStatement stmt_ifdef_BAR;
	protected IASTPreprocessorElifStatement stmt_elif_def_BAR;
	protected IASTPreprocessorElseStatement stmt_else;
	protected IASTPreprocessorTokenStreamStatement stmt_D;
	protected IASTPreprocessorTokenStreamStatement stmt_E;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setupNodes();
	}

	/**
	 * @param viewConfig 
	 */
	protected void dumpNodes(IViewConfiguration viewConfig, IASTNode[] viewNodes) {
		if (false) {
			System.out.println("dump of nodes for config " + viewConfig + ":");
			for (IASTNode node : viewNodes) {
				System.out.println("\t"+node.getNewText());
			}
		}
	}

	/**
	 * Compare actual nodes with expected nodes.
	 * @param viewConfig 
	 */
	protected void compareNodes(IViewConfiguration viewConfig, IASTNode[] viewNodes, IASTNode[] expNodeArray) {
		int idx = 0;
		StringBuffer buffer = new StringBuffer();
		for (IASTNode node : viewNodes) {
			if (idx >= expNodeArray.length) {
				buffer.append("unexpected extra node " + node.getNewText()+"\n");
			} else if (!node.equals(expNodeArray[idx])) {
				buffer.append("failed to match node " + node.getNewText() + " against " + expNodeArray[idx].getNewText()+"\n");
			}
			idx++;
		}
		for (; idx < expNodeArray.length; idx++) {
			buffer.append("missing expected node " + expNodeArray[idx].getNewText()+"\n");
		}
		if (buffer.length() > 0)
			fail("for config " + viewConfig + ": " + buffer.toString());
	}

	protected boolean equalObj(Object a, Object b) {
		if (a == null && b == null)
			return true;
		if ((a == null) != (b == null))
			return false;
		return a.equals(b);
	}
	
	/**
	 * @param viewConfig
	 * @param block
	 * @param regionArray
	*/
	protected void compareBlockTree(IViewConfiguration viewConfig, IConditionalBlock expBlock, IConditionalBlock block) {
		StringBuffer buffer = new StringBuffer();
		doCompareBlock(buffer, expBlock, block);
		if (buffer.length() > 0)
			fail("for config " + viewConfig + ": " + buffer.toString());
	}

	/**
	 * @param buffer
	 * @param block
	 * @param region2
	 */
	protected void doCompareBlock(StringBuffer buffer, IConditionalBlock expBlock, IConditionalBlock block) {
		if (!equalObj(block.getCondition(), expBlock.getCondition())) {
			buffer.append("different conditions: expected "+expBlock.getCondition()+" but got "+block.getCondition()+"\n");
 		}
		
		if (!equalObj(block.getStartNode(), expBlock.getStartNode())) {
			buffer.append("different start nodes: expected "+expBlock.getStartNode()+" but got "+block.getStartNode()+"\n");
		}
		if (!equalObj(block.getEndNode(), expBlock.getEndNode()) ){
			buffer.append("different end nodes: expected "+expBlock.getEndNode()+" but got "+block.getEndNode()+"\n");
		}

		if (!matchNodeList(expBlock.getNodes(), block.getNodes())) {
			buffer.append("mismatched node arrays: expected " + catenate(expBlock.getNodes())
					+ ", got " +catenate(block.getNodes())+"\n");
		} else {
			doCompareRegionLists(buffer, expBlock.getChildren(), block.getChildren());
		}
		
	}

	/**
	 * @param nodes
	 * @param nodes2
	 * @return
	 */
	protected boolean matchNodeList(IASTNode[] nodes, IASTNode[] nodes2) {
		int idx = 0;
		for (IASTNode node : nodes) {
			if (idx >= nodes2.length)
				return false;
			if (!node.equals(nodes2[idx]))
				return false;
			idx++;
		}
		if (idx != nodes2.length)
			return false;
		return true;
	}
	
	protected void doCompareRegionLists(StringBuffer buffer, IConditionalBlock[] regions, IConditionalBlock[] regions2) {
		int idx = 0;
		for (IConditionalBlock block : regions) {
			doCompareBlock(buffer, block, regions2[idx]);
			idx++;
		}
	}

	/**
	 * @param nodes
	 * @return
	 */
	protected String catenate(IASTNode[] nodes) {
		StringBuffer buffer = new StringBuffer();
		for (IASTNode node : nodes) {
			buffer.append(node.getNewText());
			buffer.append('\n');
		}
		return buffer.toString();
	}
	protected String catenate(IConditionalBlock[] regions) {
		StringBuffer buffer = new StringBuffer();
		for (IConditionalBlock block : regions) {
			buffer.append(block);
			buffer.append('\n');
		}
		return buffer.toString();
	}

	/**
	 * @param viewConfig
	 * @param regions
	 */
	protected void dumpBlockTree(IViewConfiguration viewConfig, IConditionalBlock block) {
		System.out.println("regions for "+viewConfig);
		dumpBlockTree(1, block);
	}
	
	protected String spaces(int count) {
		return "                          ".substring(0, count);
	}

	protected String textOf(IASTNode node){
		if (node == null) return null;
		return node.getNewText().trim();
	}
	
	protected void dumpBlockTree(int indent, IConditionalBlock block) {
		String spaces = spaces(indent);
		System.out.println(spaces + block + "[start="
				+textOf(block.getStartNode())+", end="
				+textOf(block.getEndNode())+"]");
		if (block == null)
			return;
		for (IASTNode node : block.getNodes())
			System.out.println(spaces + "-" +node.getNewText());
		IConditionalBlock[] children = block.getChildren();
		for (IConditionalBlock child : children) {
			dumpBlockTree(indent+1, child);
		}
	}

	/**
	 * Get configuration FOO
	 * @return
	 */
	protected IViewConfiguration getFooConfiguration(final IViewFilter filter) {
		final Collection<IDefine> macros = new ArrayList<IDefine>();
		macros.add(DefineFactory.createDefine("FOO"));
		IViewConfiguration viewConfig = new IViewConfiguration() {
			public String toString() {
				return "Foo";
			}
			
			public Collection<IDefine> getMacros() {
				return macros;
			}

			public IViewFilter getViewFilter() {
				return filter;
			}
			
			public IViewParserConfiguration getViewParserConfiguration() {
				return parserConfig;
			}
		};
		return viewConfig;
	}

	/**
	 * Get configuration BAR
	 * @return
	 */
	protected IViewConfiguration getBarConfiguration(final IViewFilter filter) {
		final Collection<IDefine> macros = 
			new ArrayList<IDefine>();
		macros.add(DefineFactory.createDefine("BAR"));
		IViewConfiguration viewConfig = new IViewConfiguration() {
			public String toString() {
				return "Bar";
			}

			public IViewFilter getViewFilter() {
				return filter;
			}

			public Collection<IDefine> getMacros() {
				return macros;
			}
			
			public IViewParserConfiguration getViewParserConfiguration() {
				return parserConfig;
			}

		};
		return viewConfig;
	}

	/**
	 * Get configuration FOO + BAR
	 * @return
	 */
	protected IViewConfiguration getFooBarConfiguration(final IViewFilter filter) {
		final Collection<IDefine> macros = 
			new ArrayList<IDefine>();
		macros.add(DefineFactory.createDefine("FOO"));
		macros.add(DefineFactory.createDefine("BAR"));
		IViewConfiguration viewConfig = new IViewConfiguration() {
			public String toString() {
				return "FooBar";
			}

			public IViewFilter getViewFilter() {
				return filter;
			}

			public Collection<IDefine> getMacros() {
				return macros;
			}
			
			public IViewParserConfiguration getViewParserConfiguration() {
				return parserConfig;
			}

		};
		return viewConfig;
	}

	/**
	 * @return
	 */
	protected void setupNodes() {
		stmt_A = ASTFactory.createPreprocessorTokenStreamStatement("A\n");
		stmt_ifdef_FOO = ASTFactory.createPreprocessorIfdefStatement(
				ASTFactory.createPreprocessorLiteralTextNode("FOO"));
		stmt_ifdef_BAR = ASTFactory.createPreprocessorIfdefStatement(
				ASTFactory.createPreprocessorLiteralTextNode("BAR"));
		stmt_elif_def_BAR = ASTFactory.createPreprocessorElifStatement(
				ASTFactory.createPreprocessorTokenStream("defined(BAR)"));
		stmt_else = ASTFactory.createPreprocessorElseStatement();
		stmt_B = ASTFactory.createPreprocessorTokenStreamStatement("B\n");
		stmt_endif = ASTFactory.createPreprocessorEndifStatement();
		stmt_endif2 = ASTFactory.createPreprocessorEndifStatement();
		stmt_C = ASTFactory.createPreprocessorTokenStreamStatement("C\n");
		stmt_D = ASTFactory.createPreprocessorTokenStreamStatement("D\n");
		stmt_E = ASTFactory.createPreprocessorTokenStreamStatement("E\n");
	}

}
