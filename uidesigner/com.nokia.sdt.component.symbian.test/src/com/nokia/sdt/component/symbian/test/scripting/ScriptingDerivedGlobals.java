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

package com.nokia.sdt.component.symbian.test.scripting;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.IComponentScriptAdapter;
import com.nokia.sdt.scripting.IScriptContext;
import com.nokia.sdt.testsupport.FileHelpers;

/**
 * 
 *
 */
public class ScriptingDerivedGlobals extends BaseScriptTest {
    private IComponent derived;
    private IScriptContext baseContext;
    private IComponent base;
    private IScriptContext derivedContext;
 
    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        
        base = set.lookupComponent("com.nokia.examples.base");
        assertNotNull(base);

        IComponentScriptAdapter baseAdapter = (IComponentScriptAdapter) base.getAdapter(IComponentScriptAdapter.class);

        baseContext = baseAdapter.getContextForFile(FileHelpers.projectRelativeFile(BASE_DIR + "sgbase.js"), 
                null);
        assertNotNull(baseContext);

        derived = set.lookupComponent("com.nokia.examples.derived");
        assertNotNull(derived);

        IComponentScriptAdapter derivedAdapter = (IComponentScriptAdapter) derived.getAdapter(IComponentScriptAdapter.class);

        derivedContext = derivedAdapter.getContextForFile(FileHelpers.projectRelativeFile(BASE_DIR + "sgderived.js"), 
                baseContext.getScope());
        assertNotNull(derivedContext);

    }
    
    public interface IDerivedTest {
        String act();
    }
    public void testBase() throws Exception {
        IDerivedTest test = (IDerivedTest)getInstance(baseContext, "Base", IDerivedTest.class); 

        String ret;
   
        baseContext.getTransientGlobalScope().defineObject("form", "TheForm");
        
        ret = test.act();
        assertEquals("Base-Act-TheForm", ret);
   
    }

    public void testDerived() throws Exception {
        IDerivedTest test = (IDerivedTest)getInstance(derivedContext, "Derived", IDerivedTest.class); 

        String ret;

        // the transient scope for derived has this variable -- 
        // base can only see it when the FEATURE_DYNAMIC_SCOPES feature is enabled.
        derivedContext.getTransientGlobalScope().defineObject("form", "AnotherForm");

        ret = test.act();
        assertEquals("Derived-Act-AnotherForm/Base-Shared-AnotherForm", ret);
   
    }

}
