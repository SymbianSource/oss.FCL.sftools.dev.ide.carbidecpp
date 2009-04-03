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
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstLiteralExpression;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstResourceDefinition;

/**
 * 
 *
 */
public class SrcMappingTestSimple extends SrcMappingBase {

    
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


    public void testSimpleTypes() throws Exception {
    	exportInstance("com.nokia.examples.srcmapSimple", "test1");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestSimpleTypes.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("SIMPLE_TYPES");
        assertNotNull(def);

        // check all the simple types for proper formatting and DOM expression
        checkMemberInit(def, "byte", IAstLiteralExpression.K_INTEGER, "12");
        checkMemberInit(def, "word", IAstLiteralExpression.K_INTEGER, "23");
        checkMemberInit(def, "long", IAstLiteralExpression.K_INTEGER, "34");
        checkMemberInit(def, "text", IAstLiteralExpression.K_STRING, "\"45\"");
        checkMemberInit(def, "double", IAstLiteralExpression.K_FLOAT, "56.0");
        checkMemberInit(def, "ltext", IAstLiteralExpression.K_STRING, "\"67\"");
        checkMemberInit(def, "buf", IAstLiteralExpression.K_STRING, "\"78\"");
        checkMemberInit(def, "buf8", IAstLiteralExpression.K_STRING, "\"89\"");
    }

    public void testSimpleTypesNoInit() throws Exception {
    	exportInstance("com.nokia.examples.srcmapSimple", "test1_n");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestSimpleTypesNoInit.rss", sf);

        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("SIMPLE_TYPES");
        assertNotNull(def);

        // nothing was initialized, and the component property defaults
        // all match the resource defaults, so nothing should be emitted
        checkNoMemberInit(def, "byte");
        checkNoMemberInit(def, "word");
        checkNoMemberInit(def, "long");
        checkNoMemberInit(def, "text");
        checkNoMemberInit(def, "double");
        checkNoMemberInit(def, "ltext");
        checkNoMemberInit(def, "buf");
        checkNoMemberInit(def, "buf8");
    }

    
    public void testSimpleTypesInitDefault() throws Exception {
    	exportInstance("com.nokia.examples.srcmapSimple_defaults", "test1_d");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestSimpleTypesInitDefault.rss", sf);

        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("SIMPLE_TYPES_DEFAULTS");
        assertNotNull(def);

        // all values initialized as defaults
        checkNoMemberInit(def, "byte");
        checkNoMemberInit(def, "word");
        checkNoMemberInit(def, "long");
        checkNoMemberInit(def, "text");
        checkNoMemberInit(def, "double");
        checkNoMemberInit(def, "ltext");
        checkNoMemberInit(def, "buf");
        checkNoMemberInit(def, "buf8");
    }

    public void testRect() throws Exception {
    	exportInstance("com.nokia.examples.srcmapRect", "testRect");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestRect.rss", sf);

        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("RECT");
        assertNotNull(def);

        // this case involves property="compound.field" references
        checkMemberInit(def, "x", IAstLiteralExpression.K_INTEGER, "15");
        checkMemberInit(def, "y", IAstLiteralExpression.K_INTEGER, "10");
        checkMemberInit(def, "width", IAstLiteralExpression.K_INTEGER, "45");
        checkMemberInit(def, "height", IAstLiteralExpression.K_INTEGER, "50");
    }
    
    public void testEscapedStrings() throws Exception {
        IComponentInstance instance = getInstance("test1_esc");
        exportInstance(instance);
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref/TestSimpleTypes_escaped.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("SIMPLE_TYPES");
        assertNotNull(def);

        // For now, we assume that the data model has an exact
        // representation of the string as it should appear in
        // source.
        checkMemberInit(def, "buf", IAstLiteralExpression.K_STRING, "\"dir\\\\file\"");
        checkMemberInit(def, "buf8", IAstLiteralExpression.K_STRING, "\"escaped\\ttab\"");
        
    }

}
