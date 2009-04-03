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
 * 
 *
 */
public class SrcMappingTestResources extends SrcMappingBase {

    
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

    public void testCompoundTypes() throws Exception {
    	exportInstance("com.nokia.examples.srcmapResources", "test3");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestCompoundTypes.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("COMPOUND_TYPES");
        assertNotNull(def);

        // check all the simple types for proper formatting and DOM expression
        IAstMemberInitializer init = def.findMemberInitializer("embed");
        assertNotNull(init);
        checkStructMemberInit(init, "SUBTYPE");
        
        checkStructMemberInit(init, "flag", IAstLiteralExpression.K_INTEGER, "1");
        checkStructMemberInit(init, "number", IAstLiteralExpression.K_INTEGER, "23");
        
    }

    public void testCompoundTypesNoInit() throws Exception {
    	exportInstance("com.nokia.examples.srcmapResources", "test3_n");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestCompoundTypesNoInit.rss", sf);
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("COMPOUND_TYPES");
        assertNotNull(def);

        IAstMemberInitializer init = def.findMemberInitializer("embed");
        assertNotNull(init);
        checkStructMemberInit(init, "SUBTYPE");
        
        // nothing was initialized, and the component property defaults
        // all match the resource defaults, so nothing should be emitted
        checkStructMemberNoInit(init, "flag");
        checkStructMemberNoInit(init, "number");
        
    }

    public void testCompoundTypesInitDefault() throws Exception {
    	exportInstance("com.nokia.examples.srcmapResources_defaults", "test3_d");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestCompoundTypesInitDefault.rss", sf);
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("COMPOUND_TYPES");
        assertNotNull(def);

        // although the struct's values are default, we still
        // have to emit the STRUCT member 
        
        // check all the simple types for proper formatting and DOM expression
        IAstMemberInitializer init = def.findMemberInitializer("embed");
        assertNotNull(init);
        
        checkStructMemberInit(init, "SUBTYPE_DEFAULTS");
        
        // all values initialized as defaults
        checkStructMemberNoInit(init, "flag");
        checkStructMemberNoInit(init, "number");
        
    }

    public void testPropertyPath() throws Exception {
    	exportInstance("com.nokia.examples.srcmapResources2", "testResources2");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestCTPropertyPath.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("COMPOUND_TYPES");
        assertNotNull(def);
        
        checkMemberInit(def, "unused", IAstLiteralExpression.K_INTEGER, "-1");

        // check embedded type
        IAstMemberInitializer init = def.findMemberInitializer("embed");
        assertNotNull(init);
        checkStructMemberInit(init, "SUBTYPE");
        
        checkStructMemberInit(init, "flag", IAstLiteralExpression.K_INTEGER, "1");
        checkStructMemberInit(init, "number", IAstLiteralExpression.K_INTEGER, "23");
        
    }

    public void testResourceInLink() throws Exception {
    	exportInstance("com.nokia.examples.srcmapResourceInLink", "testResourceInLink");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestResourceInLink.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("RESOURCE_LLINK");
        assertNotNull(def);

        // the link must be an id
        IAstMemberInitializer init = def.findMemberInitializer("link");
        assertNotNull(init);
        IAstExpression expr = init.getInitializerExpression().getExpression();
        assertTrue(expr instanceof IAstIdExpression);
        IAstIdExpression id = (IAstIdExpression) expr;
        
        // find the referenced resource
        IAstResourceDefinition ref = tu.findResourceDefinition(id.getName().getName());
        assertNotNull(ref);
        assertEquals("SUBTYPE", ref.getStructType().getStructName().getName());
        
        checkMemberInit(ref, "flag", IAstLiteralExpression.K_INTEGER, "1");
        checkMemberInit(ref, "number", IAstLiteralExpression.K_INTEGER, "23");
        
    }

    public void testResourceUnnamed() throws Exception {
    	exportInstance("com.nokia.examples.srcmapResourceUnnamed", "testResourceUnnamed");
        checkNoMessages();
        
        rewriteTu(tu);

        checkRefFile("ref/TestResourceUnnamed.rss", sf);

        // only named resources returned from TU
        IAstResourceDefinition[] defs = tu.getResourceDefinitions();
        assertEquals(0, defs.length);
        
        // unnamed one will be available this way
        defs = (IAstResourceDefinition[]) tu.getSourceFile().getFileNodes(IAstResourceDefinition.class);
        assertEquals(1, defs.length);
        assertNull(defs[0].getName());
        	
        
    }
    
    public void testResourceBaseName() throws Exception {
    	exportInstance("com.nokia.examples.srcmapResourceBaseName", "testResourceBaseName");
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref/TestResourceBaseName.rss", sf);

        IAstResourceDefinition[] defs = tu.getResourceDefinitions();
        assertEquals(1, defs.length);
        assertEquals("r_my_resource", defs[0].getName().getName());
        	
        
    }
    
}
