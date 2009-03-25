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
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.component.symbian.scripting.ComponentWrappers;
import com.nokia.sdt.component.symbian.scripting.WrappedAttributes;
import com.nokia.sdt.displaymodel.GlobalCache;
import com.nokia.sdt.scripting.IScriptContext;
import com.nokia.sdt.testsupport.TestDataModelHelpers;

/**
 * Test that the scripting API for component attributes works.
 * 
 * 
 * 
 */
public class ScriptingAttributesTest extends BaseScriptTest {

    private IComponent base;

    private IComponent derived;

    private IScriptContext baseContext;
    private IScriptContext derivedContext;
    
    public interface IComponentAccessorGlobal {
        // returns the attribute with the given name
        public String getAttr(String name);
    }

    public interface IComponentAccessor {
        // returns the attribute with the given name
        public String getAttrParam(WrappedAttributes attrs, String name);
    }

    public ScriptingAttributesTest() throws Exception {
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
        
        baseContext = getScriptContext("attrs.js", base);
        derivedContext = getScriptContext("attrs.js", derived);
    }

    public void testAttributesBase() throws Exception {
        IComponentAccessorGlobal acc = (IComponentAccessorGlobal)getInstance(baseContext, "Test", IComponentAccessorGlobal.class); 

        registerAttributes(baseContext, base);
        String ret;
        
        ret = acc.getAttr("baseAttributeBool");
        assertEquals("true", ret);
        
        ret = acc.getAttr("baseAttributeString");
        assertEquals("Hello", ret);
        
        ret = acc.getAttr("baseAttributeMissing");
        assertNull("", ret);
        
    }

    public void testAttributesDerived() throws Exception {
        // objD
        IComponentAccessorGlobal acc = (IComponentAccessorGlobal)getInstance(derivedContext, "Test", IComponentAccessorGlobal.class); 

        registerAttributes(derivedContext, derived);
        String ret;
        
        ret = acc.getAttr("baseAttributeBool");
        assertEquals("true", ret);
        
        ret = acc.getAttr("baseAttributeString");
        assertEquals("Goodbye", ret);

        ret = acc.getAttr("derivedAttributeString");
        assertEquals("ThinkVision", ret);

        ret = acc.getAttr("derivedAttributeMissing");
        assertNull(ret);
        
    }

    public void testAttributesBaseParams() throws Exception {
        IComponentAccessor acc = (IComponentAccessor)getInstance(baseContext, "Test", IComponentAccessor.class); 

        IAttributes attrs_ = (IAttributes) base.getAdapter(IAttributes.class);
        WrappedAttributes attrs = ComponentWrappers.getInstance().getWrappedAttributes(attrs_);

        String ret;
        
        ret = acc.getAttrParam(attrs, "baseAttributeBool");
        assertEquals("true", ret);
        
        ret = acc.getAttrParam(attrs, "baseAttributeString");
        assertEquals("Hello", ret);
        
        ret = acc.getAttrParam(attrs, "baseAttributeMissing");
        assertNull("", ret);
        
    }

    public void testAttributesDerivedParams() throws Exception {
        // objD
        IComponentAccessor acc = (IComponentAccessor)getInstance(derivedContext, "Test", IComponentAccessor.class); 

        IAttributes attrs_ = (IAttributes) derived.getAdapter(IAttributes.class);
        WrappedAttributes attrs = ComponentWrappers.getInstance().getWrappedAttributes(attrs_);

        String ret;
        
        ret = acc.getAttrParam(attrs, "baseAttributeBool");
        assertEquals("true", ret);
        
        ret = acc.getAttrParam(attrs, "baseAttributeString");
        assertEquals("Goodbye", ret);

        ret = acc.getAttrParam(attrs, "derivedAttributeString");
        assertEquals("ThinkVision", ret);

        ret = acc.getAttrParam(attrs, "derivedAttributeMissing");
        assertNull(ret);
        
    }
    
    public void testCaching() throws Exception {
        registerAttributes(baseContext, base);
        
        IAttributes attrs = (IAttributes) base.getAdapter(IAttributes.class);
        WrappedAttributes wattrs = ComponentWrappers.getInstance().getWrappedAttributes(attrs);
        WrappedAttributes wattrs0 = ComponentWrappers.getInstance().getWrappedAttributes(attrs);
        assertEquals(wattrs, wattrs0);

        // this is independent of the data model, so cache should persist
        dataModel.dispose();
        dataModel = TestDataModelHelpers.loadDataModelWithTestComponents("data/scripting/fixture.nxd", provider); 
    	
        WrappedAttributes wattrs2 = ComponentWrappers.getInstance().getWrappedAttributes(attrs);
        assertEquals(wattrs, wattrs2);
        
        // get rid of it all
        GlobalCache.getCache().disposeAll();

        dataModel = TestDataModelHelpers.loadDataModelWithTestComponents("data/scripting/fixture.nxd", provider); 

        WrappedAttributes wattrs3 = ComponentWrappers.getInstance().getWrappedAttributes(attrs);
        assertFalse(wattrs == wattrs3);
        
    }
}
