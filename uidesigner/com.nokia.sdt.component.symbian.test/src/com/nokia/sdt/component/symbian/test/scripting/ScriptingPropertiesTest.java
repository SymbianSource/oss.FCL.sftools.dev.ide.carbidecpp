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
import com.nokia.sdt.displaymodel.GlobalCache;
import com.nokia.sdt.scripting.IScriptContext;
import com.nokia.sdt.scripting.ScriptException;
import com.nokia.sdt.testsupport.*;

import org.eclipse.ui.views.properties.IPropertySource;

/**
 * Test that the scripting API for properties works.
 * 
 * 
 * 
 */
public class ScriptingPropertiesTest extends BaseScriptTest {
    private IComponent base;

    private IComponent derived;

    private IComponent unrel;
    
    public interface IComponentAccessor {
        // returns the instance global
        public IComponentInstance getInstance(WrappedInstance instance);

        // returns name property plus "foo"
        public String getNamePlusFoo(WrappedInstance instance);

        // returns size property as "x,y"
        public String getSizeFormatted(WrappedProperties properties);

        public IPropertySource getProperties(WrappedProperties properties);
    }

    public interface IComponentParentChildAccessor {
        public IComponentInstance getParent(WrappedInstance instance);

        public IComponentInstance getChild0(WrappedInstance instance);
        public IComponentInstance getChild1(WrappedInstance[] children);
    }

    public interface IComponentChanger {
        // returns name property
        public String getName(WrappedInstance instance);
        
        // sets name property to "_" + name
        public void setName(WrappedInstance instance, String name);

        // returns size property as "x,y"
        public String getSizeFormatted(WrappedProperties properties);

        // set size property
        public void setSize(WrappedProperties properties, int x, int y);
    }

    /**
     * 
     */
    public ScriptingPropertiesTest() throws Exception {
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
            IComponentAccessor acc = (IComponentAccessor) getInstance(
                    context, "Test", IComponentAccessor.class);

            String ret;

            Object obj;

            WrappedInstance wrappedInstance = getWrappedInstance(instance);
            obj = acc.getInstance(wrappedInstance);
            assertTrue(obj instanceof WrappedInstance);
            assertEquals(instance, ((WrappedInstance)obj).unwrap());

            WrappedProperties wrappedProperties = getWrappedProperties(instance);
            obj = acc.getProperties(wrappedProperties);
            assertTrue(obj instanceof WrappedProperties);
            assertEquals(instance, ((WrappedProperties)obj).unwrap());

            ret = acc.getNamePlusFoo(wrappedInstance);
            assertEquals("obj1foo", ret);

            ret = acc.getSizeFormatted(wrappedProperties);
            assertEquals("10,20", ret);
        }
    }

    public void testComponentPropertyMapping1() throws Exception {
        new ComponentPropertyTest1("data/scripts/test1_params.js").test();
    }

    public void testComponentPropertyMapping2() throws Exception {
        new ComponentPropertyTest1("data/scripts/test2_params.js").test();
    }

    public void testComponentPropertyMapping3() throws Exception {
        new ComponentPropertyTest1("data/scripts/test3_params.js").test();
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
            IComponentParentChildAccessor acc = (IComponentParentChildAccessor)
                getInstance(context,  "Test",
                        IComponentParentChildAccessor.class);

            Object obj;

            obj = acc.getParent(getWrappedInstance(instance));
            assertTrue(obj instanceof WrappedInstance);
            assertEquals(instanceParent, ((WrappedInstance)obj).unwrap());

            obj = acc.getChild0(getWrappedInstance(instance));
            assertTrue(obj instanceof WrappedInstance);
            assertEquals(instanceKid, ((WrappedInstance)obj).unwrap());
            obj = acc.getChild1(getWrappedChildren(instance));
            assertTrue(obj instanceof WrappedInstance);
            assertEquals(instanceKid, ((WrappedInstance)obj).unwrap());
        }
    }

    public void testParentChild0() throws Exception {
        new ParentChildTester("data/scripts/parentchild_params.js").test();
    }

    class ReadWriteComponentPropertyTest {
        private String filename;

        public ReadWriteComponentPropertyTest(String filename) {
            this.filename = filename;
        }

        void test() throws Exception {
            IComponentScriptAdapter adapter = (IComponentScriptAdapter) base.getAdapter(IComponentScriptAdapter.class);
            IScriptContext context = adapter.getContextForFile(FileHelpers.projectRelativeFile(filename), null);

            IComponentInstance instance = AdapterHelpers.findComponentInstance(dataModel, "obj1");
            IPropertySource properties = AdapterHelpers.getPropertySource(instance);

            registerInstanceGlobals(context, dataModel, instance);

            // get script
            IComponentChanger acc = (IComponentChanger) getInstance(
                    context, "Test", IComponentChanger.class);

            String ret;

            WrappedInstance wrappedInstance = getWrappedInstance(instance);
            WrappedProperties wrappedProperties = getWrappedProperties(instance);
            
            // ensure default
            ret = acc.getName(wrappedInstance);
            assertEquals("obj1", ret);
            
            // change name from script
            acc.setName(wrappedInstance, "billy");
            ret = acc.getName(wrappedInstance);
            assertEquals("_billy", ret);
            
            // ensure it changed in real property source
            ret = (String) properties.getPropertyValue("name");
            assertNotNull(ret);
            assertEquals("_billy", ret);

            // ensure default
            ret = acc.getSizeFormatted(wrappedProperties);
            assertEquals("10,20", ret);
            
            // change size from script
            acc.setSize(wrappedProperties, 4, 5);
            ret = acc.getSizeFormatted(wrappedProperties);
            assertEquals("4,5", ret);

            // ensure real property source changed
            IPropertySource ps = (IPropertySource) properties.getPropertyValue("size");
            assertNotNull(ps);
            assertEquals("4", (String)ps.getPropertyValue("x").toString());
            assertEquals("5", (String)ps.getPropertyValue("y").toString());
            
        }
    }

    public void testComponentPropertyWriting1() throws Exception {
        new ReadWriteComponentPropertyTest("data/scripts/propwriter1_params.js").test();
    }

    public void testCachingInstances() throws Exception {
        IComponentInstance instance = AdapterHelpers.findComponentInstance(dataModel, "obj1");
        
        WrappedInstance wrappedInstance = getWrappedInstance(instance);
        WrappedInstance wrappedInstance0 = getWrappedInstance(instance);

        assertEquals(wrappedInstance, wrappedInstance0);

        // reload
        instance.getDesignerDataModel().dispose();
        dataModel = TestDataModelHelpers.loadDataModelWithTestComponents("data/scripting/fixture.nxd", provider); 
        
        
        // instances and caches should be gone
        IComponentInstance instance2 = AdapterHelpers.findComponentInstance(dataModel, "obj1");
        assertFalse(instance == instance2);
        
        WrappedInstance wrappedInstance2 = getWrappedInstance(instance2);
        assertFalse(wrappedInstance == wrappedInstance2);

        // get rid of it all
        GlobalCache.getCache().disposeAll();

        dataModel = TestDataModelHelpers.loadDataModelWithTestComponents("data/scripting/fixture.nxd", provider); 

        IComponentInstance instance3 = AdapterHelpers.findComponentInstance(dataModel, "obj1");
        assertFalse(instance == instance3);
        
        WrappedInstance wrappedInstance3 = getWrappedInstance(instance3);
        assertFalse(wrappedInstance == wrappedInstance3);

    }

    /*
     * No point caching properties.  We can't map IPropertySource back to
     * a component instance, so no way to tell they've gone out of scope
    public void testCachingProperties() throws Exception {
        IComponentInstance instance = AdapterHelpers.findComponentInstance(dataModel, "obj1");
        
        WrappedProperties wrappedProperties = getWrappedProperties(instance);

        WrappedProperties wrappedProperties0 = getWrappedProperties(instance);
        assertEquals(wrappedProperties, wrappedProperties0);

        // reload
        instance.getDesignerDataModel().dispose();
        dataModel = TestDataModelHelpers.loadDataModelWithTestComponents("data/scripting/fixture.nxd", provider); 
        
        
        // instances and caches should be gone
        IComponentInstance instance2 = AdapterHelpers.findComponentInstance(dataModel, "obj1");
        assertFalse(instance == instance2);
        
        WrappedProperties wrappedProperties2 = getWrappedProperties(instance2);
        assertFalse(wrappedProperties == wrappedProperties2);

        // get rid of it all
        GlobalCache.getCache().disposeAll();

        dataModel = TestDataModelHelpers.loadDataModelWithTestComponents("data/scripting/fixture.nxd", provider); 

        IComponentInstance instance3 = AdapterHelpers.findComponentInstance(dataModel, "obj1");
        assertFalse(instance == instance3);
        
        WrappedProperties wrappedProperties3 = getWrappedProperties(instance3);
        assertFalse(wrappedProperties == wrappedProperties3);

    }
	*/
}
