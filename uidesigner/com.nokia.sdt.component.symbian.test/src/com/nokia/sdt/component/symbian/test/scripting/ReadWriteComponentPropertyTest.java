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

import com.nokia.sdt.component.adapter.IComponentScriptAdapter;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.scripting.IScriptContext;
import com.nokia.sdt.testsupport.AdapterHelpers;
import com.nokia.sdt.testsupport.FileHelpers;

import org.eclipse.ui.views.properties.IPropertySource;

class ReadWriteComponentPropertyTest {
    /**
     * 
     */
    private final ScriptingPropertiesGlobalsTest test;
    private String filename;

    public ReadWriteComponentPropertyTest(ScriptingPropertiesGlobalsTest test, String filename) {
        this.test = test;
        this.filename = filename;
    }

    void test() throws Exception {
        IComponentScriptAdapter adapter = (IComponentScriptAdapter) this.test.base.getAdapter(IComponentScriptAdapter.class);
        IScriptContext context = adapter.getContextForFile(FileHelpers.projectRelativeFile(filename), null);

        IComponentInstance instance = AdapterHelpers.findComponentInstance(this.test.dataModel, "obj1");
        IPropertySource properties = AdapterHelpers.getPropertySource(instance);

        this.test.registerInstanceGlobals(context, this.test.dataModel, instance);

        // get script
        IScriptingPropertiesGlobalsComponentChanger acc = (IScriptingPropertiesGlobalsComponentChanger) this.test.getInstance(
                context, "Test", IScriptingPropertiesGlobalsComponentChanger.class);

        String ret;

        // ensure default
        ret = acc.getName();
        ScriptingPropertiesGlobalsTest.assertEquals("obj1", ret);
        
        // change name from script
        acc.setName("billy");
        ret = acc.getName();
        ScriptingPropertiesGlobalsTest.assertEquals("_billy", ret);
        
        // ensure it changed in real property source
        ret = (String) properties.getPropertyValue("name");
        ScriptingPropertiesGlobalsTest.assertNotNull(ret);
        ScriptingPropertiesGlobalsTest.assertEquals("_billy", ret);

        // ensure default
        ret = acc.getSizeFormatted();
        ScriptingPropertiesGlobalsTest.assertEquals("10,20", ret);
        
        // change size from script
        acc.setSize(4, 5);
        ret = acc.getSizeFormatted();
        ScriptingPropertiesGlobalsTest.assertEquals("4,5", ret);

        // ensure real property source changed
        IPropertySource ps = (IPropertySource) properties.getPropertyValue("size");
        ScriptingPropertiesGlobalsTest.assertNotNull(ps);
        ScriptingPropertiesGlobalsTest.assertEquals("4", (String)ps.getPropertyValue("x").toString());
        ScriptingPropertiesGlobalsTest.assertEquals("5", (String)ps.getPropertyValue("y").toString());
        
    }
}