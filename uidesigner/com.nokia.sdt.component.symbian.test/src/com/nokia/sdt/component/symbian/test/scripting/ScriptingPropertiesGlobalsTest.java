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
import com.nokia.sdt.component.symbian.scripting.*;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.scripting.IScriptContext;
import com.nokia.sdt.scripting.ScriptException;
import com.nokia.sdt.testsupport.AdapterHelpers;
import com.nokia.sdt.testsupport.FileHelpers;

/**
 * Test that the scripting API for properties works.
 * 
 * 
 * 
 */
public class ScriptingPropertiesGlobalsTest extends BaseScriptTest {
    IComponent base;

    private IComponent derived;

    private IComponent unrel;

    /**
     * 
     */
    public ScriptingPropertiesGlobalsTest() throws Exception {
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        
        base = set.lookupComponent("com.nokia.examples.base");
        assertNotNull(base);
        derived = set.lookupComponent("com.nokia.examples.derived");
        assertNotNull(derived);
        unrel = set.lookupComponent("com.nokia.examples.unrelated");
        assertNotNull(unrel);

    }

    public Object loadAndCreate(IJavaScriptContext context, String filename,
            String prototype, Class klass) throws ScriptException {
        Object script = context.loadAndCompileScript(getDataFile(filename));
        context.runScript(script);
        Object obj = context.createScriptObject(script, prototype);
        return context.wrapScriptObjectInInterface(script, obj, klass);
    }

    class ComponentPropertyTest1 {
        private String filename;

        public ComponentPropertyTest1(String filename) {
            this.filename = filename;
        }

        void test() throws Exception {
            IComponentScriptAdapter adapter = (IComponentScriptAdapter) base.getAdapter(IComponentScriptAdapter.class);
            IScriptContext context = adapter.getContextForFile(FileHelpers.projectRelativeFile(filename), null);
            IComponentInstance instance = AdapterHelpers.findComponentInstance(dataModel, "obj1");
            
            registerInstanceGlobals(context, dataModel, instance);
            
            // get script
            IScriptingPropertiesGlobalsComponentAccessor acc = (IScriptingPropertiesGlobalsComponentAccessor) getInstance(
                    context, "Test", IScriptingPropertiesGlobalsComponentAccessor.class);

            String ret;

            Object obj;

            obj = acc.getInstance();
            assertTrue(obj instanceof WrappedInstance);
            assertEquals(instance, ((WrappedInstance)obj).unwrap());

            obj = acc.getProperties();
            assertTrue(obj instanceof WrappedProperties);
            assertEquals(instance, ((WrappedProperties)obj).unwrap());

            ret = acc.getNamePlusFoo();
            assertEquals("obj1foo", ret);

            ret = acc.getSizeFormatted();
            assertEquals("10,20", ret);
        }
    }

    public void testComponentPropertyMapping1() throws Exception {
        new ComponentPropertyTest1("data/scripts/test1.js").test();
    }

    public void testComponentPropertyMapping2() throws Exception {
        new ComponentPropertyTest1("data/scripts/test2.js").test();
    }

    public void testComponentPropertyMapping3() throws Exception {
        new ComponentPropertyTest1("data/scripts/test3.js").test();
    }

    class ParentChildTester {
        String filename;

        ParentChildTester(String filename) {
            this.filename = filename;
        }

        void test() throws Exception {
            IComponentScriptAdapter adapter = (IComponentScriptAdapter) base.getAdapter(IComponentScriptAdapter.class);
            IScriptContext context = adapter.getContextForFile(FileHelpers.projectRelativeFile(filename), null);

            // make dummy instance
            IComponentInstance instanceParent = AdapterHelpers.findComponentInstance(dataModel, 
                    "obj0");
            IComponentInstance instance = AdapterHelpers.findComponentInstance(dataModel, "obj1");
            IComponentInstance instanceKid = AdapterHelpers.findComponentInstance(dataModel, "obj2");

            registerInstanceGlobals(context, dataModel, instance);

            // get script
            IScriptingPropertiesGlobalsComponentParentChildAccessor acc = (IScriptingPropertiesGlobalsComponentParentChildAccessor)
                getInstance(context,  "Test",
                        IScriptingPropertiesGlobalsComponentParentChildAccessor.class);

            Object obj;

            obj = acc.getParent();
            assertTrue(obj instanceof WrappedInstance);
            assertEquals(instanceParent, ((WrappedInstance)obj).unwrap());

            obj = acc.getChild0();
            assertTrue(obj instanceof WrappedInstance);
            assertEquals(instanceKid, ((WrappedInstance)obj).unwrap());
        }
    }

    public void testParentChild0() throws Exception {
        new ParentChildTester("data/scripts/parentchild.js").test();
    }

    public void testParentChild1() throws Exception {
        new ParentChildTester("data/scripts/parentchild2.js").test();
    }

    public void testComponentPropertyWriting1() throws Exception {
        new ReadWriteComponentPropertyTest(this, "data/scripts/propwriter1.js").test();
    }

}
