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

package com.nokia.sdt.component.symbian.test;

import com.nokia.sdt.component.symbian.ComponentProvider;
import com.nokia.sdt.testsupport.TestDataModelHelpers;
import com.nokia.sdt.testsupport.TestHelpers;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;

import junit.framework.TestCase;

/**
 * Test various bad components and ensure errors generated 
 * from the loader are logged.
 * 
 *
 */
public class BadComponents extends TestCase {

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelpers.setPlugin(PluginTest.getDefault());
    }

    static int found;
    public void testBad() throws Exception {
        
        // listen to log messages
        ILogListener listener = new ILogListener() {

            public void logging(IStatus status, String pluginName) {
            	System.out.println(status);
                found++;
            }};
            
        Logging.addListener(listener);
        
        // load the bad components
        ComponentProvider provider = TestDataModelHelpers.createProviderForUserComponents("data/badComponents");
        provider.queryComponents(null);
        
        // each bad component should generate two messages
        assertEquals(6, found);
        
        Logging.removeListener(listener);
        
    }
}
