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

import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.component.symbian.reconcileProperty.ReconcilePropertyImplementationFactory;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IReconcileProperty;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstResourceDefinition;

import org.eclipse.ui.views.properties.IPropertySource;

/**
 * Test updating existing RSS with source mapping
 * 
 *
 */
public class SrcMappingTestUpdatingResources extends SrcMappingBase {
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.component.symbian.test.srcmapping.SrcMappingBase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // register the factory for reconciling properties
        ComponentSystemPlugin.initializeImplementationsRegistry();
        ReconcilePropertyImplementationFactory fac = new ReconcilePropertyImplementationFactory();
        
        ComponentSystemPlugin.getImplementationTypes().addInterface(
                IReconcileProperty.class.getName(),
                fac.getCodeImplAdapterClass(),
                fac.getScriptImplAdapterClass());
    }
    
    protected IComponentInstance getCbaFixture(String userFile, 
    		String[] headers,
    		String[] genFiles) throws Exception {
    	parseRssFrom(userFile, headers, genFiles);

        IComponentInstance instance = getInstance("testCBAHolder_Custom");
        setInstanceToResourceMapping("testCBAHolder_Custom", "r_testcbaholder_custom");
        setInstanceToResourceMapping("testCBA_Custom", "r_testcba_custom");
        setInstanceToResourceMapping("testCBA_Custom", "member$buttons[0]", "r_testcba_custom_buttons_0");
        setInstanceToResourceMapping("testCBA_Custom", "member$buttons[1]", "r_testcba_custom_buttons_1");
    	
        return instance;
    	
    }

	/**
	 * Test that deleting unreferenced resources works
	 * @throws Exception
	 */
	public void testDeletingReferencedCbaButtons() throws Exception {
	    
	    IComponentInstance instance = getCbaFixture(
	    		"user/TestDeletingReferencedCbaButtons.rss", null, null);
	    
	    IComponentInstance cba = getInstance("testCBA_Custom");
	    IPropertySource ps = getProperties(cba);
	    IPropertySource info = (IPropertySource) ps.getPropertyValue("CBA");
	    
	    info.setPropertyValue("leftText", "Options");
	    info.setPropertyValue("leftId", "EAknSoftkeyOptions");
	    info.setPropertyValue("rightText", "Exit");
	    info.setPropertyValue("rightId", "EAknSoftkeyExit");
	
	    /*
	    exportInstance(instance);
	    checkNoMessages();
	    exportInstance(cba);
	    checkNoMessages();
	    */
	    generator.generateResources(instance);
	    
	    //rewriteTu(tu);
	    checkRefFile("ref2/TestDeletingReferencedCbaButtons.rss", sf);
	    
	    IAstResourceDefinition def;
	    def = tu.findResourceDefinition("r_testcba_custom");
	    assertNull(def);
	    def = tu.findResourceDefinition("r_testcba_custom_buttons_0");
	    assertNull(def);
	    def = tu.findResourceDefinition("r_testcba_custom_buttons_1");
	    assertNull(def);
	}



}
