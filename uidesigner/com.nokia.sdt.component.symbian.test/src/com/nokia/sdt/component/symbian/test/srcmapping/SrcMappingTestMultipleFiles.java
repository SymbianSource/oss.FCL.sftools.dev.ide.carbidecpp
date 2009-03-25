/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.component.symbian.test.srcmapping;

import com.nokia.sdt.sourcegen.core.ISourceFile;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.*;

import java.io.File;

/**
 * Check that we can generate and reference more than one
 * resource file.
 * 
 *
 */
public class SrcMappingTestMultipleFiles extends SrcMappingBase {

	private File dir;


	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.test.srcmapping.SrcMappingBase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		dir = sf.getFile().getParentFile();
		sgProvider.setProjectName("MyProject");
	}
	
    public void testGenerateTwoFile() throws Exception {
    	// go through generator so we get full-model visibility
    	
    	generator.setRssFileForTesting(rss);
    	generator.generateResources(getInstance("test_multifile0"));
    	checkNoMessages();
    	
        IAstResourceDefinition[] defs = tu.getResourceDefinitions();
        assertEquals(1, defs.length);

        checkMemberInit(defs[0], "text", IAstLiteralExpression.K_STRING, "\"text\"");


        ISourceFile std = fileManager.findSourceFile(
        		new File(dir, "standard.rss"));
        assertNotNull(std);
        assertTrue(std.getLength() > 0);
        ITranslationUnit stdTu = fileManager.findRssFile(std).getTranslationUnit();
        defs = stdTu.getResourceDefinitions();
        assertEquals(1, defs.length);
        
        checkMemberInit(defs[0], "text", IAstLiteralExpression.K_STRING, "\"alternate\"");

        checkRefFile("ref/TestGenerateTwoFile0.rss", sf);
        checkRefFile("ref/TestGenerateTwoFile0_b.rss", std);

    }

    public void testGenerateTwoFileVariable() throws Exception {
    	// go through generator so we get full-model visibility
    	
    	generator.setRssFileForTesting(rss);
    	generator.generateResources(getInstance("test_multifile1"));
    	checkNoMessages();
    	
        IAstResourceDefinition[] defs = tu.getResourceDefinitions();
        assertEquals(1, defs.length);

        checkMemberInit(defs[0], "text", IAstLiteralExpression.K_STRING, "\"text\"");

        // this file is named after the project -- there is none,
        // but it has a standard name for testing
        ISourceFile std = fileManager.findSourceFile(
        		new File(dir, "MyProject_caption.rss"));
        assertNotNull(std);
        assertTrue(std.getLength() > 0);
        ITranslationUnit stdTu = fileManager.findRssFile(std).getTranslationUnit();
        defs = stdTu.getResourceDefinitions();
        assertEquals(1, defs.length);
        
        checkMemberInit(defs[0], "text", IAstLiteralExpression.K_STRING, "\"alternate\"");

        checkRefFile("ref/TestGenerateTwoFileVariable.rss", sf);
        checkRefFile("ref/TestGenerateTwoFileVariable_b.rss", std);

    }

    public void testGenerateTwoFileWithLoc() throws Exception {
    	// make sure localized strings show up
    	
    	generator.setStringHandler(locHandler);
    	generator.setRssFileForTesting(rss);
    	generator.generateResources(getInstance("test_multifile1b"));
    	checkNoMessages();
    	
        checkRefFile("ref/TestGenerateTwoFileWithLoc.rss", sf);
        
        IAstResourceDefinition[] defs = tu.getResourceDefinitions();
        assertEquals(1, defs.length);

        checkMemberInit(defs[0], "text", IAstPreprocessorMacroExpression.class, "STR_1");

        IAstPreprocessorLocIncludeNode[] locs = (IAstPreprocessorLocIncludeNode[]) rss.getFileNodes(IAstPreprocessorLocIncludeNode.class);
        assertEquals(1, locs.length);
        
        String firstLocName = locs[0].getFilename();
        
        // this file is named after the project -- there is none,
        // but it has a standard name for testing
        ISourceFile std = fileManager.findSourceFile(
        		new File(dir, "MyProject_caption.rss"));
        assertNotNull(std);
        assertTrue(std.getLength() > 0);
        
        checkRefFile("ref/TestGenerateTwoFileWithLoc_b.rss", std);

        ITranslationUnit stdTu = fileManager.findRssFile(std).getTranslationUnit();
        defs = stdTu.getResourceDefinitions();
        assertEquals(1, defs.length);
        
        checkMemberInit(defs[0], "text", IAstPreprocessorMacroExpression.class, "STR_10");

        locs = (IAstPreprocessorLocIncludeNode[]) stdTu.getSourceFile().getFileNodes(IAstPreprocessorLocIncludeNode.class);
        assertEquals(1, locs.length);

        String secondLocName = locs[0].getFilename();

        // for now, we share the locs between the two files
        assertEquals(firstLocName, secondLocName);

    }

    public void testGenerateTwoFileWithRls() throws Exception {
    	// make sure localized strings show up
    	
    	generator.setStringHandler(rlsHandler);
    	generator.setRssFileForTesting(rss);
    	generator.generateResources(getInstance("test_multifile1b"));
    	checkNoMessages();

        checkRefFile("ref/TestGenerateTwoFileWithRls.rss", sf);
        
        IAstResourceDefinition[] defs = tu.getResourceDefinitions();
        assertEquals(1, defs.length);

        checkMemberInit(defs[0], "text", IAstPreprocessorMacroExpression.class, "STR_1");

        IAstPreprocessorRlsIncludeNode[] rlss = (IAstPreprocessorRlsIncludeNode[]) rss.getFileNodes(IAstPreprocessorRlsIncludeNode.class);
        assertEquals(2, rlss.length);
        
        String firstRlsName = rlss[0].getFilename();
        
        // this file is named after the project -- there is none,
        // but it has a standard name for testing
        ISourceFile std = fileManager.findSourceFile(
        		new File(dir, "MyProject_caption.rss"));
        assertNotNull(std);
        assertTrue(std.getLength() > 0);
        
        checkRefFile("ref/TestGenerateTwoFileWithRls_b.rss", std);

        ITranslationUnit stdTu = fileManager.findRssFile(std).getTranslationUnit();
        defs = stdTu.getResourceDefinitions();
        assertEquals(1, defs.length);
        
        checkMemberInit(defs[0], "text", IAstPreprocessorMacroExpression.class, "STR_10");

        rlss = (IAstPreprocessorRlsIncludeNode[]) stdTu.getSourceFile().getFileNodes(IAstPreprocessorRlsIncludeNode.class);
        assertEquals(2, rlss.length);

        String firstRlsName_2 = rlss[0].getFilename();

        // for now, we share the locs between the two files
        assertEquals(firstRlsName, firstRlsName_2);

    }

}
