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

import com.nokia.sdt.sourcegen.doms.rss.dom.*;

/**
 * Check that we can generate and reference more than one
 * resource per component.
 * 
 *
 */
public class SrcMappingTestMultipleResources extends SrcMappingBase {

    
    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGenerateTwo() throws Exception {
        exportInstance("com.nokia.examples.srcmapMulti0", "test_multi0");
        
        rewriteTu(tu);
        
        checkRefFile("ref/TestGenerateTwo0.rss", sf);
        
        
        IAstResourceDefinition[] defs = tu.getResourceDefinitions();
        assertEquals(2, defs.length);

        assertFalse(defs[0].getName().getName().equals(defs[1].getName().getName()));
        checkMemberInit(defs[0], "text", IAstLiteralExpression.K_STRING, "\"text\"");
        checkMemberInit(defs[1], "text", IAstLiteralExpression.K_STRING, "\"alternate\"");
    }


    public void testGenerateTwo1() throws Exception {
        exportInstance("com.nokia.examples.srcmapMulti1", "test_multi1");
        
        rewriteTu(tu);
        
        checkRefFile("ref/TestGenerateTwo1.rss", sf);
        
        
        IAstResourceDefinition[] defs = tu.getResourceDefinitions();
        assertEquals(2, defs.length);

        assertFalse(defs[0].getName().getName().equals(defs[1].getName().getName()));
        checkMemberInit(defs[0], "text", IAstLiteralExpression.K_STRING, "\"text\"");
        checkMemberInit(defs[1], "text", IAstLiteralExpression.K_STRING, "\"alternate\"");
    }

    public void testGenerateTwo2() throws Exception {
        exportInstance("com.nokia.examples.srcmapMulti2", "test_multi2");
        
        rewriteTu(tu);
        
        checkRefFile("ref/TestGenerateTwo2.rss", sf);
        
        
        IAstResourceDefinition[] defs = tu.getResourceDefinitions();
        assertEquals(2, defs.length);

        assertFalse(defs[0].getName().getName().equals(defs[1].getName().getName()));
        checkMemberInit(defs[0], "text", IAstLiteralExpression.K_STRING, "\"text\"");
        checkMemberInit(defs[1], "buf", IAstLiteralExpression.K_STRING, "\"alternate\"");
    }

    public void testReferenceMulti() throws Exception {
        exportInstance("com.nokia.examples.srcmapMultiRef", "test_multiRef");
        
        rewriteTu(tu);
        checkNoMessages();

        checkRefFile("ref/TestReferenceMulti.rss", sf);
        
        IAstResourceDefinition[] defs = tu.getResourceDefinitions();
        assertEquals(3, defs.length);

        checkMemberInit(defs[0], "link1", IAstIdExpression.class, defs[1].getName().getName());
        checkMemberInit(defs[0], "link2", IAstIdExpression.class, defs[2].getName().getName());
        checkMemberInit(defs[1], "text", IAstLiteralExpression.K_STRING, "\"text\"");
        checkMemberInit(defs[2], "buf", IAstLiteralExpression.K_STRING, "\"alternate\"");
    }

    public void testReferenceMulti2() throws Exception {
        exportInstance("com.nokia.examples.srcmapDialog", "testDialog");
        exportInstance("com.nokia.examples.srcmapMultiRef2", "test_multiRef2");
        
        rewriteTu(tu);
        checkNoMessages();

        checkRefFile("ref/TestReferenceMulti2.rss", sf);
        
        IAstResourceDefinition[] defs = tu.getResourceDefinitions();
        assertEquals(4, defs.length);

        IAstResourceDefinition def = tu.findResourceDefinitionOfType("TWO_REFS");
        IAstResourceDefinition defC = tu.findResourceDefinitionOfType("CHECKBOX");
        IAstResourceDefinition defL = tu.findResourceDefinitionOfType("LABEL");
        checkMemberInit(def, "link1", IAstIdExpression.class, defC.getName().getName());
        checkMemberInit(def, "link2", IAstIdExpression.class, defL.getName().getName());
    }
    
    /** This case is like QIK_CONTAINER + QIK_CONTAINER_SETTINGS coming form
     * a ViewLayout
     * @throws Exception
     */
    public void testReferenceMulti3() throws Exception {
    	exportInstance("com.nokia.examples.srcmapMultiRef3", "test_multiRef3");
        
        rewriteTu(tu);
        checkNoMessages();
        
        checkRefFile("ref/TestReferenceMulti3.rss", sf);
        
        IAstResourceDefinition[] defs = tu.getResourceDefinitions();
        assertEquals(3, defs.length);

        IAstResourceDefinition def = tu.findResourceDefinitionOfType("TWO_REFS");
        IAstResourceDefinition defC = tu.findResourceDefinitionOfType("ONE_STRING");
        IAstResourceDefinition defL = tu.findResourceDefinitionOfType("SIMPLE_TYPES");
        checkMemberInit(def, "link1", IAstIdExpression.class, defC.getName().getName());
        checkMemberInit(def, "link2", IAstIdExpression.class, defL.getName().getName());
    }
    
    
    public void testNestedGenerate() throws Exception {
    	// crasher test
        exportInstance("com.nokia.examples.srcmapForm", "testForm");
        exportInstance("testFormCheckbox");
        exportInstance("testFormLabel");
        rewriteTu(tu);
        checkRefFile("ref/TestNestedGenerate.rss", sf);

    }
}
