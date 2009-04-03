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
import com.nokia.sdt.emf.dm.ISourceGenMappingState;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.symbian.dm.DesignerDataModel;

/**
 * Test that removing one optional part of a multi-resource
 * component works
 * 
 *
 */
public class SrcMappingTestRemoveRsrcs extends SrcMappingBase {

    
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

    /* (non-Javadoc)
     * @see com.nokia.sdt.component.symbian.test.srcmapping.SrcMappingBase#getDataModelPath()
     */
    @Override
    protected String getDataModelPath() {
    	return  BASE_DIR + "fixtureRemoveRsrc.nxd";
    }
    
    public void testRemovingRsrc() throws Exception {
    	
    	ISourceGenMappingState sourceMappingState = ((DesignerDataModel)dataModel).getDesignerData().getSourceMappingState();
    	assertEquals(2, sourceMappingState.getResourceMappings().getMappings().size());
    	
        parseRssFrom("user/TestRemovingRsrc.rss");
        setInstanceToResourceMapping("test0", "r_test0");
        setInstanceToResourceMapping("test0", "text", "r_test0_1");

        IComponentInstance instance = getInstance("test0");
        
        generator.generateResources(instance);
    	exportInstance("test0");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestRemovingRsrc.rss", sf);
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("LBUF");
        assertNull(def);

        // verify the extra text entry is gone from the data model
        generator.saveState(((DesignerDataModel)dataModel).getDesignerData());
        
        sourceMappingState = ((DesignerDataModel)dataModel).getDesignerData().getSourceMappingState();
    	assertEquals(1, sourceMappingState.getResourceMappings().getMappings().size());
        
    }
    
}
