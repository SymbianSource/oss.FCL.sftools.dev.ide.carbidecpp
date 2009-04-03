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
 * Test updating existing RSS with source mapping
 * 
 *
 */
public class SrcMappingTestUpdatingLLinks extends SrcMappingBase {
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.component.symbian.test.srcmapping.SrcMappingBase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    protected IComponentInstance getReferenceFixture(String userFile) throws Exception {
    	parseRssFrom(userFile, new String[0], new String[0]);
    	
    	IComponentInstance instance = getInstance("test_resourceInLLink");
    	setInstanceToResourceMapping("test_resourceInLLink", "r_test_resource_llink");
    	return instance;
    	
    }

	/**
	 * Test that a resource link of value '0' will be properly replaced
	 * with a real resource on rewrite
	 * @throws Exception
	 */
	public void testReplacingExplicitMissingResource() throws Exception {
	    
		// first, the resource has no field setting
		IComponentInstance instance = getReferenceFixture("user/TestReplacingExplicitMissingResources.rss");
	
		// this should set the field to "0"
		exportInstance(instance);
        checkNoMessages();
        
	    rewriteTu(tu);
	    checkRefFile("ref2/TestReplacingExplicitMissingResources1.rss", sf);
	    
	    IAstResourceDefinition def = tu.findResourceDefinition("r_test_resource_llink");
	    assertNotNull(def);
	    
	    IAstMemberInitializer memberInitializer = def.findMemberInitializer("link1");
	    // needed for matching fixed member mappings against default field values
	    if (memberInitializer != null) {
		    IAstExpression expr = memberInitializer.getInitializerExpression().getExpression();
		    // we can't presume the type of this expression either
		    assertEquals("0", expr.getNewText(null));
	    }
	    
	    //////
	    
	    // now, set the reference property and go again, leaving the RSS in memory
	    
	    IPropertySource ps = getProperties(instance);
	    ps.setPropertyValue("one", "12");
	    
        generator.getModelManipulator().getResourceTracker().resetComplete();

	    exportInstance(instance);
        checkNoMessages();
        rewriteTu(tu);
	    checkRefFile("ref2/TestReplacingExplicitMissingResources2.rss", sf);

        def = tu.findResourceDefinition("r_test_resource_llink");
	    assertNotNull(def);
	    
	    // be sure the link was set to a real resource name
	    memberInitializer = def.findMemberInitializer("theLink");
	    assertNotNull(memberInitializer);
	    IAstExpression expr = memberInitializer.getInitializerExpression().getExpression();
	    assertTrue(expr instanceof IAstIdExpression);
	    assertFalse(((IAstIdExpression)expr).getName().getName().equals("0"));

	    assertNull(tu.findResourceDefinition("0"));

	}

}
