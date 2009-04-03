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

package com.nokia.sdt.symbian.tests;

import com.nokia.sdt.component.symbian.scripting.ScriptingManager;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.testsupport.TestDataModelHelpers;
import com.nokia.sdt.testsupport.TestHelpers;

import junit.framework.TestCase;

/**
 * 
 *
 */
public class TestProperties extends TestCase {

    private IDesignerDataModel dataModel;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelpers.setPlugin(TestsPlugin.getDefault());

        ScriptingManager.getInstance().registerClassLoaderFor(TestsPlugin.getDefault());

        dataModel = TestDataModelHelpers.loadDataModel("data/testProperties.nxd"); 
    }
    
    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }
 
    public void testRead() throws Exception {
        String val = dataModel.getProperty("com.nokia.sdt.symbian.dm.RESOURCE_DIRECTORY_ID");
        assertEquals("group", val);
        
        val = dataModel.getProperty("com.nokia.sdt.component.symbian.vendor");
        assertEquals("com.nokia.series60", val);
    }

    public void testReadMissing() throws Exception {
        String val = dataModel.getProperty("not_present");
        assertNull(val);
    }

    public void testOverwrite() throws Exception {
        dataModel.setProperty("com.nokia.sdt.symbian.dm.RESOURCE_DIRECTORY_ID", "rsrc");
        
        String val = dataModel.getProperty("com.nokia.sdt.symbian.dm.RESOURCE_DIRECTORY_ID");
        assertEquals("rsrc", val);
    }
    
    public void testWriteNew() throws Exception {
        dataModel.setProperty("not_present", "something");
        String val = dataModel.getProperty("not_present");
        assertEquals("something", val);
    }
    
    public void testReset() throws Exception {
        dataModel.removeProperty("com.nokia.sdt.symbian.dm.RESOURCE_DIRECTORY_ID");
        String val = dataModel.getProperty("com.nokia.sdt.symbian.dm.RESOURCE_DIRECTORY_ID");
        assertNull(val);
    }
    
    
    public void testResetMissing() throws Exception {
        dataModel.removeProperty("not_present");
        String val = dataModel.getProperty("not_present");
        assertNull(val);
    }
    
}

