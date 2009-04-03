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
import com.nokia.sdt.sourcegen.doms.rss.dom.*;

import org.eclipse.ui.views.properties.IPropertySource;

/**
 * Test bitmask mapping
 * 
 *
 */
public class SrcMappingTestBitmasks extends SrcMappingBase {

    
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


    public void testZero() throws Exception {
        exportInstance("com.nokia.test.srcmapBitmasks", "testBitmasks");
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref/TestBitmasksZero.rss", sf);
                
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("BITMASK");
        assertNotNull(def);

        // check all the simple types for proper formatting and DOM expression
        
        // no mapping, so default is zero
        checkMemberInit(def, "numbers", IAstLiteralExpression.K_INTEGER, "0");
        
        // this has a mapping for no true values
        checkMemberInit(def, "colors", IAstIdExpression.class, "EAknBlack");
    }

    public void testSingle() throws Exception {
        
        IComponentInstance instance = getInstance("testBitmasks");
        IPropertySource properties = getProperties(instance);

        IPropertySource numbers = (IPropertySource) properties.getPropertyValue("numbers");
        IPropertySource colors = (IPropertySource) properties.getPropertyValue("colors");
        
        numbers.setPropertyValue("oneBit", new Boolean(true));
        colors.setPropertyValue("red", new Boolean(true));
        
        exportInstance(instance);
        checkNoMessages();
        rewriteTu(tu);
        checkRefFile("ref/TestBitmasksSingle.rss", sf);
                
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("BITMASK");
        assertNotNull(def);

        // check all the simple types for proper formatting and DOM expression
        checkMemberInit(def, "numbers", IAstIdExpression.class, "EAknOne");
        checkMemberInit(def, "colors", IAstIdExpression.class, "EAknRed");
    }

    public void testSingle2() throws Exception {
        
        IComponentInstance instance = getInstance("testBitmasks");
        IPropertySource properties = getProperties(instance);

        IPropertySource numbers = (IPropertySource) properties.getPropertyValue("numbers");
        IPropertySource colors = (IPropertySource) properties.getPropertyValue("colors");
        
        numbers.setPropertyValue("twoBit", new Boolean(true));
        colors.setPropertyValue("blue", new Boolean(true));
        
        exportInstance(instance);
        checkNoMessages();
        rewriteTu(tu);
        checkRefFile("ref/TestBitmasksSingle2.rss", sf);
                
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("BITMASK");
        assertNotNull(def);

        // check all the simple types for proper formatting and DOM expression
        checkMemberInit(def, "numbers", IAstIdExpression.class, "EAknTwo");
        checkMemberInit(def, "colors", IAstIdExpression.class, "EAknBlue");
    }

    public void testAll() throws Exception {
        
        IComponentInstance instance = getInstance("testBitmasks");
        IPropertySource properties = getProperties(instance);

        IPropertySource numbers = (IPropertySource) properties.getPropertyValue("numbers");
        IPropertySource colors = (IPropertySource) properties.getPropertyValue("colors");
        
        numbers.setPropertyValue("oneBit", new Boolean(true));
        numbers.setPropertyValue("twoBit", new Boolean(true));
        colors.setPropertyValue("red", new Boolean(true));
        colors.setPropertyValue("green", new Boolean(true));
        colors.setPropertyValue("blue", new Boolean(true));
        
        exportInstance(instance);
        checkNoMessages();
        rewriteTu(tu);
        checkRefFile("ref/TestBitmasksAll.rss", sf);
                
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("BITMASK");
        assertNotNull(def);

        // check all the simple types for proper formatting and DOM expression
        checkMemberInit(def, "numbers", IAstIdExpression.class, "EAknThree");
        checkMemberInit(def, "colors", IAstIdExpression.class, "EAknWhite");
    }

    public void testSome() throws Exception {
        
        IComponentInstance instance = getInstance("testBitmasks");
        IPropertySource properties = getProperties(instance);

        IPropertySource colors = (IPropertySource) properties.getPropertyValue("colors");
        
        colors.setPropertyValue("red", new Boolean(true));
        colors.setPropertyValue("blue", new Boolean(true));
        
        exportInstance(instance);
        checkNoMessages();
        rewriteTu(tu);
        checkRefFile("ref/TestBitmasksSome.rss", sf);
                
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("BITMASK");
        assertNotNull(def);

        // check all the simple types for proper formatting and DOM expression
        checkMemberInit(def, "colors", IAstBinaryExpression.class, "EAknRed | EAknBlue");
    }

    public void testSome2() throws Exception {
        
        IComponentInstance instance = getInstance("testBitmasks");
        IPropertySource properties = getProperties(instance);

        IPropertySource colors = (IPropertySource) properties.getPropertyValue("colors");
        
        colors.setPropertyValue("blue", new Boolean(true));
        colors.setPropertyValue("green", new Boolean(true));
        
        exportInstance(instance);
        checkNoMessages();
        rewriteTu(tu);
        checkRefFile("ref/TestBitmasksSome2.rss", sf);
                
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("BITMASK");
        assertNotNull(def);

        // check all the simple types for proper formatting and DOM expression
        checkMemberInit(def, "colors", IAstIdExpression.class, "EAknCyan");
    }

    private void doTestPartialBitmasks(String instanceId, String refFile, String refFile2) throws Exception {
        exportInstance(instanceId);
        checkNoMessages();

        rewriteTu(tu);
        checkRefFile(refFile, sf);

        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("BITMASK");
        assertNotNull(def);

        // check all the simple types for proper formatting and DOM expression
        checkMemberInit(def, "numbers", IAstLiteralExpression.K_INTEGER, "0");
        checkMemberInit(def, "colors", IAstIdExpression.class, "EAknBlack");

        ////////
        
        IComponentInstance instance = getInstance(instanceId);
        IPropertySource properties = getProperties(instance);

        IPropertySource numbersAndColors = (IPropertySource) properties.getPropertyValue("numbersAndColors");
        
        numbersAndColors.setPropertyValue("twoBit", new Boolean(true));

        numbersAndColors.setPropertyValue("blue", new Boolean(true));
        numbersAndColors.setPropertyValue("green", new Boolean(true));

        generator.getModelManipulator().getResourceTracker().resetComplete();

        exportInstance(instance);
        rewriteTu(tu);
        checkRefFile(refFile2, sf);
        
        
        def = tu.findResourceDefinitionOfType("BITMASK");
        assertNotNull(def);

        checkMemberInit(def, "numbers", IAstIdExpression.class, "EAknTwo");
        checkMemberInit(def, "colors", IAstIdExpression.class, "EAknCyan");
    }
    public void testPartialBitmasks() throws Exception {
    	doTestPartialBitmasks("testBitmasksPartial", 
    			"ref/TestBitmasksPartial.rss",
    			"ref/TestBitmasksPartial2.rss");
    }

    public void testPartialBitmasksTypeMapped() throws Exception {
    	doTestPartialBitmasks("testBitmasksPartialTypeMapped", 
    			"ref/TestBitmasksPartialTypeMapped.rss",
    			"ref/TestBitmasksPartialTypeMapped2.rss");
    }
    public void testBitmaskErrors() throws Exception {
        exportInstance("com.nokia.test.srcmapBitmasksErrors", "testBitmasksErrors");
        checkMessage("IncompleteMapping");
        checkMessage("NonBooleanProperty");
    }

    public void testBitmasksAsChildren() throws Exception {
        exportInstance("com.nokia.test.srcmapBitmasksChildren", "testBitmasksChildren");
        checkNoMessages();

        rewriteTu(tu);
        checkRefFile("ref/TestBitmasksChildren.rss", sf);

        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("BITMASK");
        assertNotNull(def);

        // check all the simple types for proper formatting and DOM expression
        checkMemberInit(def, "numbers", IAstLiteralExpression.K_INTEGER, "0");
        checkMemberInit(def, "colors", IAstIdExpression.class, "EAknBlack");

        ////////
        
        IComponentInstance instance = getInstance("testBitmasksChildren");
        IPropertySource properties = getProperties(instance);

        properties.setPropertyValue("twoBit", new Boolean(true));

        properties.setPropertyValue("blue", new Boolean(true));
        properties.setPropertyValue("green", new Boolean(true));

        generator.getModelManipulator().getResourceTracker().resetComplete();

        exportInstance(instance);
        rewriteTu(tu);
        checkRefFile("ref/TestBitmasksChildren2.rss", sf);
        
        
        def = tu.findResourceDefinitionOfType("BITMASK");
        assertNotNull(def);

        checkMemberInit(def, "numbers", IAstIdExpression.class, "EAknTwo");
        checkMemberInit(def, "colors", IAstIdExpression.class, "EAknCyan");
    }
    
    public void testDefaults() throws Exception {
        exportInstance("com.nokia.test.srcmapBitmasksDefaults", "testBitmasksDefaults");
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref/TestBitmasksDefaults.rss", sf);
                
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("BITMASK_DEFAULTS");
        assertNotNull(def);

        // check that nothing was mapped
        
        checkNoMemberInit(def, "numbers");
        checkMemberInit(def, "colors", IAstIdExpression.class, "EAknBlackEnum");
    }

    public void testDefaults2() throws Exception {
        IComponentInstance instance = getInstance("testBitmasksDefaults");
        IPropertySource properties = getProperties(instance);

        IPropertySource colors = (IPropertySource) properties.getPropertyValue("colors");
        
        colors.setPropertyValue("green", new Boolean(true));

        exportInstance(instance);
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref/TestBitmasksDefaults2.rss", sf);
                
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("BITMASK_DEFAULTS");
        assertNotNull(def);

        // check that nothing was mapped
        
        checkNoMemberInit(def, "numbers");
        checkNoMemberInit(def, "colors");
    }

}
