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

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;

import org.eclipse.ui.views.properties.IPropertySource;

/**
 * Test updating existing RSS with source mapping
 * 
 *
 */
public class SrcMappingTestUpdating extends SrcMappingBase {
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.component.symbian.test.srcmapping.SrcMappingBase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    public void testEmptyFile() throws Exception {
        
        parseRssFrom("user/TestUpdatingEmptyFile.rss");
        
        exportInstance(getInstance("test0"));
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref2/TestUpdatingEmptyFile.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("ONE_STRING");
        assertNotNull(def);
        
        // the value should reference the string
        checkMemberInit(def, "text", IAstLiteralExpression.K_STRING, "\"check this textual data\"");
    }

    public void testEmptyResource() throws Exception {
        
        parseRssFrom("user/TestUpdatingEmptyFile.rss");
        
        IComponentInstance instance=  getInstance("test0");
        IPropertySource ps = ModelUtils.getPropertySource(instance.getEObject());
        ps.setPropertyValue("textdata", "");
        
        exportInstance(instance);
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref2/TestUpdatingEmptyResource.rss", sf);
    }
    
    /**
     * Test that rewriting a known resource with a known field
     * works.  Basic test to ensure we don't, e.g., make a new resource.
     * @throws Exception
     */
    public void testBasicKnownContents() throws Exception {
        
        parseRssFrom("user/TestUpdatingBasicKnownContents.rss");
        setInstanceToResourceMapping("test0", "r_test0");

        // now check that the struct definition exists
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("ONE_STRING");
        assertNotNull(def);
        
        ///
        
        exportInstance(getInstance("test0"));
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref2/TestUpdatingBasicKnownContents.rss", sf);
        
        
        def = tu.findResourceDefinitionOfType("ONE_STRING");
        assertNotNull(def);
        
        // the value should reference the string
        checkMemberInit(def, "text", IAstLiteralExpression.K_STRING, "\"check this textual data\"");
    }

    /**
     * Test that rewriting a known resource with a known field
     * and unknown field works.
     * @throws Exception
     */
    public void testBasicKnownContents2() throws Exception {
        
        parseRssFrom("user/TestUpdatingBasicKnownContents2.rss");
        setInstanceToResourceMapping("test0", "r_test0");

        exportInstance(getInstance("test0"));
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref2/TestUpdatingBasicKnownContents2.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("ONE_STRING");
        assertNotNull(def);
        
        checkMemberInit(def, "text", IAstLiteralExpression.K_STRING, "\"check this textual data\"");
        checkMemberInit(def, "flags", IAstLiteralExpression.K_INTEGER, "42");
    }

    /**
     * Test that rewriting a known resource with a known field
     * and unknown field works.
     * @throws Exception
     */
    public void testBasicKnownContents3() throws Exception {
        
        parseRssFrom("user/TestUpdatingBasicKnownContents3.rss");
        setInstanceToResourceMapping("test0", "r_test0");

        exportInstance(getInstance("test0"));
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref2/TestUpdatingBasicKnownContents3.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("ONE_STRING");
        assertNotNull(def);
        
        checkMemberInit(def, "text", IAstLiteralExpression.K_STRING, "\"check this textual data\"");
        checkMemberInit(def, "flags", IAstLiteralExpression.K_INTEGER, "42");
        checkMemberInit(def, "stag", IAstLiteralExpression.K_INTEGER, "23");
    }

    /**
     * Test that rewriting a known resource with all unknown
     * fields works.
     * @throws Exception
     */
    public void testBasicKnownContents4() throws Exception {
        
        parseRssFrom("user/TestUpdatingBasicKnownContents4.rss");
        setInstanceToResourceMapping("test0", "r_test0");

        exportInstance(getInstance("test0"));
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref2/TestUpdatingBasicKnownContents4.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("ONE_STRING");
        assertNotNull(def);
        
        checkMemberInit(def, "text", IAstLiteralExpression.K_STRING, "\"check this textual data\"");
        checkMemberInit(def, "flags", IAstLiteralExpression.K_INTEGER, "42");
        checkMemberInit(def, "stag", IAstLiteralExpression.K_INTEGER, "23");
    }

    /**
     * Test rewriting a file with an unknown resource.  
     * Basic test to ensure we don't overwrite a resource
     * with the same name.
     * @throws Exception
     */
    public void testBasicUnknownContents() throws Exception {
        
        parseRssFrom("user/TestUpdatingBasicUnknownContents.rss");
        assertFalse(rss.isDirtyTree());
        
        // no mappings: all unknown
        
        // now check that the struct definition exists
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("FOO");
        assertNotNull(def);
        
        ///
        
        exportInstance(getInstance("test0"));
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref2/TestUpdatingBasicUnknownContents.rss", sf);
        
        
        def = tu.findResourceDefinitionOfType("ONE_STRING");
        assertNotNull(def);

        // make sure it isn't the same name as the existing one
        assertNotSame("r_test0", def.getName().getName());
        
        // the value should reference the string
        checkMemberInit(def, "text", IAstLiteralExpression.K_STRING, "\"check this textual data\"");

        IAstResourceDefinition origDef = tu.findResourceDefinitionOfType("FOO");
        assertNotNull(origDef);

    }

    /**
     * Test rewriting a file with an unknown resource.  
     * Basic test to ensure we don't overwrite that resource.
     * @throws Exception
     */
    public void testBasicUnknownContents2() throws Exception {
        
        parseRssFrom("user/TestUpdatingBasicUnknownContents2.rss");
        assertFalse(rss.isDirtyTree());

        // no mappings: all unknown

        // assure that the struct definition exists
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("ONE_STRING");
        assertNotNull(def);

        checkMemberInit(def, "text", IAstLiteralExpression.K_STRING, "\"blargh\"");

        ///
        
        exportInstance(getInstance("test0"));
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref2/TestUpdatingBasicUnknownContents2.rss", sf);
        
        // now check that a new struct definition was written
        def = tu.findResourceDefinition("r_test0_2");
        assertNotNull(def);

        // the value should reference the string
        checkMemberInit(def, "text", IAstLiteralExpression.K_STRING, "\"check this textual data\"");

        IAstResourceDefinition origDef = tu.findResourceDefinition("r_test0");
        assertNotNull(origDef);

    }

    /**
     * Test updating arrays
     * @throws Exception
     */
    public void testUpdatingArrays() throws Exception {
        
        parseRssFrom("user/TestUpdatingArrays.rss");
        // double test: the resource updating PLUS ensuring this
        // strangely named resource name is retained
        setInstanceToResourceMapping("test5", "r_testArr");

        exportInstance(getInstance("test5"));
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref2/TestUpdatingArrays.rss", sf);
        
        IAstResourceDefinition def = tu.findResourceDefinition("r_test5");
        assertNull(def);
        def = tu.findResourceDefinition("r_testArr");
        assertNotNull(def);
        
        checkMemberInit(def, "ints", IAstExpressionList.class, "2, 3, 5");
        assertNotNull(def.findMemberInitializer("structs"));
    }

    /**
     * Test bug 1064 where a component whose resource type changes
     * triggers an IllegalArgumentException.
     * @throws Exception
     */
    public void testUpdatingChangedResourceBug1064() throws Exception {
        parseRssFrom("user/TestUpdatingChangedResourceBug1064.rss");
        setInstanceToResourceMapping("testUpdatingChangedResourceBug1064", "0", "r_test");

        exportInstance(getInstance("testUpdatingChangedResourceBug1064"));
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref2/TestUpdatingChangedResourceBug1064.rss", sf);
        
        assertEquals(1, tu.getResourceDefinitions().length);
        IAstResourceDefinition def = tu.findResourceDefinition("r_test");
        assertNotNull(def);
        
        // changing the resource type clears the members
        assertEquals("SIMPLE_TYPES_DEFAULTS", def.getStructType().getStructName().getName());
        checkMemberInit(def, "byte", IAstLiteralExpression.K_INTEGER, "11");
        checkMemberInit(def, "long", IAstLiteralExpression.K_INTEGER, "5");
        checkNoMemberInit(def, "word");
    	
    }
}
