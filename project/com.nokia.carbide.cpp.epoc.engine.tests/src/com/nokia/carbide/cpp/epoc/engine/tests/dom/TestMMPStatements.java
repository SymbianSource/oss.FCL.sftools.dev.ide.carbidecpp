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

package com.nokia.carbide.cpp.epoc.engine.tests.dom;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cpp.epoc.engine.tests.BaseTest;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.ASTMMPFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPAifStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPBitmapSourceStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPFlagStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPListArgumentStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPOptionStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPProblemStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPSingleArgumentStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPStartBlockStatement;
import com.nokia.cpp.internal.api.utils.core.IMessage;
import com.nokia.cpp.internal.api.utils.core.Message;
import com.nokia.cpp.internal.api.utils.core.MessageLocation;


@SuppressWarnings("hiding")
public class TestMMPStatements extends BaseTest {

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.tests.BaseTest#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testFlagStatement() {
		IASTMMPFlagStatement flag = ASTMMPFactory.createMMPFlagStatement("AIF_RULES");
		validateNewNode(flag);
		
		assertEquals("AIF_RULES\n", flag.getNewText());

		flag.getKeyword().setValue("AIF");
		assertTrue(flag.isDirtyTree());
		assertEquals("AIF\n", flag.getNewText());
		////
		
		flag = ASTMMPFactory.createMMPFlagStatement(ASTMMPFactory.createPreprocessorLiteralTextNode("AIF_RULES"));
		validateNewNode(flag);
		
		assertEquals("AIF_RULES\n", flag.getNewText());
		
	}

	public void testColorDepth() {
		String depth = ASTMMPFactory.createBitmapFormat(12, 1, true);
		assertEquals("c12,1", depth);
		depth = ASTMMPFactory.createBitmapFormat(12, 0, true);
		assertEquals("c12", depth);
		depth = ASTMMPFactory.createBitmapFormat(1, 0, false);
		assertEquals("1", depth);
		depth = ASTMMPFactory.createBitmapFormat(1, 1, false);
		assertEquals("1,1", depth);
	}
	
	public void testAifStatement() {
		String colorDepth = ASTMMPFactory.createBitmapFormat(12, 1, true);
		List<String> sourceBitmaps = new ArrayList<String>();
		sourceBitmaps.add("../gfx/list_icon.bmp");
		sourceBitmaps.add("../gfx/list_icon_mask.bmp");
		IASTMMPAifStatement aif = ASTMMPFactory.createMMPAifStatement("target.aif", "../aif/", "project_aif.rss", colorDepth, sourceBitmaps);
		validateNewNode(aif);
		assertEquals("AIF target.aif ../aif/ project_aif.rss c12,1 ../gfx/list_icon.bmp ../gfx/list_icon_mask.bmp\n", aif.getNewText());
		
		aif.getResource().setValue("myproject_aif.rss");
		assertTrue(aif.isDirtyTree());
		assertEquals("AIF target.aif ../aif/ myproject_aif.rss c12,1 ../gfx/list_icon.bmp ../gfx/list_icon_mask.bmp\n", aif.getNewText());
		
		aif.setColorDepth(ASTMMPFactory.createPreprocessorLiteralTextNode(ASTMMPFactory.createBitmapFormat(8, 0, true)));
		assertTrue(aif.isDirty());
		assertEquals("AIF target.aif ../aif/ myproject_aif.rss c8 ../gfx/list_icon.bmp ../gfx/list_icon_mask.bmp\n", aif.getNewText());
	}
	
	public void testBitmapSource() {
		String colorDepth = ASTMMPFactory.createBitmapFormat(12, 1, true);
		List<String> bitmaps = new ArrayList<String>();
		bitmaps.add("../gfx/list_icon.bmp");
		bitmaps.add("../gfx/list_icon_mask.bmp");
		IASTMMPBitmapSourceStatement bitmap = ASTMMPFactory.createMMPBitmapSourceStatement(
				colorDepth, bitmaps);
		validateNewNode(bitmap);
		assertEquals("SOURCE c12,1 ../gfx/list_icon.bmp ../gfx/list_icon_mask.bmp\n", bitmap.getNewText());

		bitmap.getArguments().remove(1);
		assertTrue(bitmap.isDirtyTree());
		assertEquals("SOURCE c12,1 ../gfx/list_icon.bmp\n", bitmap.getNewText());
	}
	
	public void testListArgumentStatement() {
		List<String> sources = new ArrayList<String>();
		sources.add("foo.cpp");
		sources.add("bar.cpp");
		IASTMMPListArgumentStatement list = ASTMMPFactory.createMMPListArgumentStatement(
				"SOURCE", sources);
		validateNewNode(list);
		assertEquals("SOURCE foo.cpp bar.cpp\n", list.getNewText());
		
		list.getArguments().add(1, ASTMMPFactory.createPreprocessorLiteralTextNode("baz.cpp"));
		assertTrue(list.isDirtyTree());
		assertEquals("SOURCE foo.cpp baz.cpp bar.cpp\n", list.getNewText());
		
		list.setDirtyTree(false);
		list.getKeyword().setValue("TEST_SOURCE");
		assertTrue(list.isDirtyTree());
		assertEquals("TEST_SOURCE foo.cpp baz.cpp bar.cpp\n", list.getNewText());
		
	}
	
	public void testOptionStatement() {
		IASTMMPOptionStatement option = ASTMMPFactory.createMMPOptionStatement(
					"OPTION", "WINS", new String[] {"-O4", "-g" });
		validateNewNode(option);
		assertEquals("OPTION WINS -O4 -g\n", option.getNewText());
		
		option.setCompiler(ASTMMPFactory.createPreprocessorLiteralTextNode("ARMV5"));
		assertTrue(option.isDirty());
		assertEquals("OPTION ARMV5 -O4 -g\n", option.getNewText());
		
		option.setKeyword(ASTMMPFactory.createPreprocessorLiteralTextNode("LINKER_OPTION"));
		assertEquals("LINKER_OPTION ARMV5 -O4 -g\n", option.getNewText());
	}
	
	public void testSingleArgument() {
		
		IASTMMPSingleArgumentStatement stmt = ASTMMPFactory.createMMPSingleArgumentStatement(
				"BaseAddress", "0x1000FFFF");
		validateNewNode(stmt);
		assertEquals("BaseAddress 0x1000FFFF\n", stmt.getNewText());
		stmt.setArgument(ASTMMPFactory.createPreprocessorLiteralTextNode("11"));
		assertTrue(stmt.isDirty());
		assertEquals("BaseAddress 11\n", stmt.getNewText());
		
		stmt = ASTMMPFactory.createMMPSingleArgumentStatement(
				ASTMMPFactory.createPreprocessorLiteralTextNode("POLICY"),
				ASTMMPFactory.createPreprocessorLiteralTextNode("ON"));
		validateNewNode(stmt);
		assertEquals("POLICY ON\n", stmt.getNewText());
		stmt.getArgument().setValue("OFF");
		assertTrue(stmt.isDirtyTree());
		assertEquals("POLICY OFF\n", stmt.getNewText());
		
	}
	
	public void testStartBlockStatement() {
		List<String> blockArguments = new ArrayList<String>();
		blockArguments.add("WINS");
		IASTMMPStartBlockStatement block = ASTMMPFactory.createMMPStartBlockStatement("PLATFORM", blockArguments);
		validateNewNode(block);
		assertEquals("START PLATFORM WINS\nEND\n", block.getNewText());
		
		IASTMMPFlagStatement stmt = ASTMMPFactory.createMMPFlagStatement("REBOOT");
		block.getStatements().add(stmt);
		
		assertFalse(block.isDirty());
		assertTrue(block.isDirtyTree());
		assertEquals("START PLATFORM WINS\n\tREBOOT\nEND\n", block.getNewText());
		
	}
	
	public void testProblemStatement() {
		IMessage message = new Message(IMessage.WARNING, 
				new MessageLocation(new Path("foo.mmp"), 1, 1), 
				"Exception",
				"There was a problem.");
		IASTMMPProblemStatement problem = ASTMMPFactory.createMMPProblemStatement(
				"FOO bar/baz.cpp\n", message);
		validateNewNode(problem);
		
		assertEquals("FOO bar/baz.cpp\n", problem.getNewText());
		assertEquals(message, problem.getMessage());
	}

}
