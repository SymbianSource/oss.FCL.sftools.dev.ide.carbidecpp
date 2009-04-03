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
import com.nokia.sdt.sourcegen.core.ResourceTracker.ResourceInfo;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpressionList;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstLiteralExpression;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstMemberInitializer;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstResourceDefinition;

/**
 * Check that we can generate nested resources with a standalone
 * version accessible by identifier.
 * 
 *
 */
public class SrcMappingTestStandaloneResources extends SrcMappingBase {

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

    public void testBasic() throws Exception {
        exportInstance("com.nokia.examples.srcmapStandalone", "testStandalone");
        checkNoMessages();
        exportInstance("com.nokia.examples.srcmapDialogLabelStandalone", "testStandaloneLabel1");
        checkNoMessages();
        exportInstance("com.nokia.examples.srcmapDialogLabelStandalone", "testStandaloneLabel2");
        checkNoMessages();

        rewriteTu(tu);
        
        checkRefFile("ref/TestStandalone.rss", sf);
        
        // now check that the standalone struct definitions were written
        IAstResourceDefinition[] defs = tu.getResourceDefinitions();
        assertEquals(3, defs.length);

        checkMemberInit(defs[1], "text", IAstLiteralExpression.K_STRING, "\"literal string 1\"");
        checkMemberInit(defs[2], "text", IAstLiteralExpression.K_STRING, "\"literal string 2\"");
        
        IComponentInstance instance = getInstance("testStandaloneLabel1");
        assertNotNull(instance);
        ResourceInfo info = manipulator.getResourceTracker().findResourceInfo(instance, null);
        assertNotNull(info);
        IAstResourceDefinition rsrc = tu.findResourceDefinition(info.getName());
        assertNotNull(rsrc);
    }

    /** for default mode, referenced items are not emitted standalone */
    public void testBasicStandaloneDefaultReffed() throws Exception {
    	IComponentInstance label1 = getInstance("testStandaloneDefaultLabel1");
    	ModelUtils.getPropertySource(label1.getEObject()).setPropertyValue(
    			"flag", "true");
    	IComponentInstance label2 = getInstance("testStandaloneDefaultLabel2");
    	ModelUtils.getPropertySource(label2.getEObject()).setPropertyValue(
    			"flag", "true");
        exportInstance("testStandaloneDefault");
        checkNoMessages();
        exportInstance("testStandaloneDefaultLabel1");
        checkNoMessages();
        exportInstance("testStandaloneDefaultLabel2");
        checkNoMessages();

        rewriteTu(tu);
        
        checkRefFile("ref/TestStandaloneDefaultReffed.rss", sf);
        
        // now check that the non-standalone struct definitions were not written
        IAstResourceDefinition[] defs = tu.getResourceDefinitions();
        assertEquals(1, defs.length);
        
        IAstMemberInitializer init = defs[0].findMemberInitializer("lines");
        assertNotNull(init);
        
        checkArrayInit(init);
        IAstExpression[] lineExprs = ((IAstExpressionList) init.getInitializerExpression().getExpression()).getList();
        assertEquals(2, lineExprs.length);

        ResourceInfo info = manipulator.getResourceTracker().findResourceInfo(label1, null);
        assertNotNull(info);
        assertNull(info.getName());
        info = manipulator.getResourceTracker().findResourceInfo(label2, null);
        assertNotNull(info);
        assertNull(info.getName());
    }
    
    /** for default mode, referenced items are not emitted standalone */
    public void testBasicStandaloneDefaultNotReffed() throws Exception {
    	IComponentInstance label1 = getInstance("testStandaloneDefaultLabel1");
    	ModelUtils.getPropertySource(label1.getEObject()).setPropertyValue(
    			"flag", "false");
    	IComponentInstance label2 = getInstance("testStandaloneDefaultLabel2");
    	ModelUtils.getPropertySource(label2.getEObject()).setPropertyValue(
    			"flag", "false");
    	
        exportInstance("testStandaloneDefault");
        checkNoMessages();
        exportInstance("testStandaloneDefaultLabel1");
        checkNoMessages();
        exportInstance("testStandaloneDefaultLabel2");
        checkNoMessages();

        rewriteTu(tu);
        
        checkRefFile("ref/TestStandaloneDefaultNotReffed.rss", sf);
        
        // now check that the non-standalone struct definitions were not written
        IAstResourceDefinition[] defs = tu.getResourceDefinitions();
        assertEquals(3, defs.length);
        
        IAstMemberInitializer init = defs[0].findMemberInitializer("lines");
        assertNull(init);

        ResourceInfo info = manipulator.getResourceTracker().findResourceInfo(label1, null);
        assertNotNull(info);
        assertNotNull(info.getName());
        info = manipulator.getResourceTracker().findResourceInfo(label2, null);
        assertNotNull(info);
        assertNotNull(info.getName());
        
        
    }

    /** for never mode, referenced items are never emitted standalone */
    public void testBasicStandaloneNeverHalfReffed() throws Exception {
    	IComponentInstance label1 = getInstance("testStandaloneNeverLabel1");
    	ModelUtils.getPropertySource(label1.getEObject()).setPropertyValue(
    			"flag", "true");
    	IComponentInstance label2 = getInstance("testStandaloneNeverLabel2");
    	ModelUtils.getPropertySource(label2.getEObject()).setPropertyValue(
    			"flag", "false");
    	
        exportInstance("testStandaloneNever");
        checkNoMessages();
        exportInstance("testStandaloneNeverLabel1");
        checkNoMessages();
        exportInstance("testStandaloneNeverLabel2");
        checkNoMessages();

        rewriteTu(tu);
        
        checkRefFile("ref/TestStandaloneNeverHalfReffed.rss", sf);
        
        // now check that the non-standalone struct definitions were not written
        IAstResourceDefinition[] defs = tu.getResourceDefinitions();
        assertEquals(1, defs.length);
        
        IAstMemberInitializer init = defs[0].findMemberInitializer("lines");
        assertNotNull(init);
        
        checkArrayInit(init);
        IAstExpression[] lineExprs = ((IAstExpressionList) init.getInitializerExpression().getExpression()).getList();
        assertEquals(1, lineExprs.length);


        ResourceInfo info = manipulator.getResourceTracker().findResourceInfo(label1, null);
        assertNotNull(info);
        assertNull(info.getName());
        
        info = manipulator.getResourceTracker().findResourceInfo(label2, null);
        assertNull(info);
        
        
    }

    /** for never mode, referenced items are never emitted standalone */
    public void testBasicStandaloneNeverNotReffed() throws Exception {
    	IComponentInstance label1 = getInstance("testStandaloneNeverLabel1");
    	ModelUtils.getPropertySource(label1.getEObject()).setPropertyValue(
    			"flag", "false");
    	IComponentInstance label2 = getInstance("testStandaloneNeverLabel2");
    	ModelUtils.getPropertySource(label2.getEObject()).setPropertyValue(
    			"flag", "false");
    	
        exportInstance("testStandaloneNever");
        checkNoMessages();
        exportInstance("testStandaloneNeverLabel1");
        checkNoMessages();
        exportInstance("testStandaloneNeverLabel2");
        checkNoMessages();

        rewriteTu(tu);
        
        checkRefFile("ref/TestStandaloneNeverNotReffed.rss", sf);
        
        // now check that the non-standalone struct definitions were not written
        IAstResourceDefinition[] defs = tu.getResourceDefinitions();
        assertEquals(1, defs.length);
        
        IAstMemberInitializer init = defs[0].findMemberInitializer("lines");
        assertNull(init);

        ResourceInfo info = manipulator.getResourceTracker().findResourceInfo(label1, null);
        assertNull(info);
        info = manipulator.getResourceTracker().findResourceInfo(label2, null);
        assertNull(info);
        
        
    }

    /**
     * This case has a container referencing a child implicitly by array
     * mapping.  Both have ids and are standalone so neither should be
     * generated.
     * @throws Exception
     */
    public void testBasicStandaloneNeverContainer() throws Exception {
    	
        exportInstance("testStandaloneNeverContainer");
        checkNoMessages();
        exportInstance("testStandaloneNever1");
        checkNoMessages();
        exportInstance("testStandaloneNever2");
        checkNoMessages();

        rewriteTu(tu);
        
        checkRefFile("ref/TestStandaloneNeverContainer.rss", sf);
        
        // now check that the non-standalone struct definitions were not written
        IAstResourceDefinition[] defs = tu.getResourceDefinitions();
        assertEquals(1, defs.length);
        
        IAstMemberInitializer init = defs[0].findMemberInitializer("lines");
        assertNull(init);
        
        
    }

}
