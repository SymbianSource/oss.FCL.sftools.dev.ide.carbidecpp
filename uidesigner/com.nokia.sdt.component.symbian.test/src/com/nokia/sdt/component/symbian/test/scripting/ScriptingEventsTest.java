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
import com.nokia.sdt.component.symbian.scripting.ComponentWrappers;
import com.nokia.sdt.component.symbian.scripting.WrappedInstance;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IEventBinding;
import com.nokia.sdt.scripting.IScriptContext;
import com.nokia.sdt.testsupport.AdapterHelpers;

import org.eclipse.ui.views.properties.IPropertySource;

/**
 * Test that the scripting API for events works.
 * 
 * 
 * 
 */
public class ScriptingEventsTest extends BaseScriptTest {

    private IComponent base;

    private IComponent derived;

    private IScriptContext baseContext;
    private IScriptContext derivedContext;
    
    public interface IComponentAccessorGlobal {
        public IEventBinding getEventBinding(String eventId);
        public String getEventEventId(String eventId);
        public String getEventEventName(String eventId);
        public String getEventMethod(String eventId);
        public String getEventSymbol(String eventId);
    }

    public interface IComponentAccessor {
        public IEventBinding getEventParamBinding(WrappedInstance instance, String eventId);
        public String getEventParamEventId(WrappedInstance instance, String eventId);
        public String getEventParamEventName(WrappedInstance instance, String eventId);
        public String getEventParamMethod(WrappedInstance instance, String eventId);
        public String getEventParamSymbol(WrappedInstance instance, String eventId);
    }

    public ScriptingEventsTest() throws Exception {
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
        
        baseContext = getScriptContext("events.js", base);
        derivedContext = getScriptContext("events.js", derived);
    }

    public void testEventDescriptors() throws Exception {
    	IComponentAccessorGlobal acc = (IComponentAccessorGlobal)getInstance
    		(baseContext, "Test", IComponentAccessorGlobal.class); 

    	registerEvents(baseContext, dataModel, "event0");
        
        String ret;
        IEventBinding binding;
        IEventBinding binding2;
        
        binding = acc.getEventBinding("eventChanged");
        assertNotNull(binding);
        
        // not bound
        binding2 = acc.getEventBinding("eventCreated");
        assertNull(binding2);

        ret = acc.getEventEventId("eventChanged");
        assertEquals("eventChanged",ret);

        ret = acc.getEventEventName("eventChanged");
        assertEquals("stuff changed",ret);

    }

    public void testEventBindings() throws Exception {
    	IComponentAccessorGlobal acc = (IComponentAccessorGlobal)getInstance
    		(baseContext, "Test", IComponentAccessorGlobal.class); 

    	registerEvents(baseContext, dataModel, "event0");
        
        String ret;

        ret = acc.getEventMethod("eventChanged");
        assertEquals("HandleThingyChanged", ret);
        ret = acc.getEventSymbol("eventChanged");
        assertEquals("MyClass::HandleThingyChanged", ret);
    }
    
    public void testEventDescriptorsDerived() throws Exception {
    	IComponentAccessorGlobal acc = (IComponentAccessorGlobal)getInstance
    		(derivedContext, "Test", IComponentAccessorGlobal.class); 

    	registerEvents(derivedContext, dataModel, "event1");
        
        String ret;
        IEventBinding binding;
        IEventBinding binding2;
        
        binding = acc.getEventBinding("eventCreated");
        assertNotNull(binding);
        
        binding2 = acc.getEventBinding("eventFrobbed");
        assertNotNull(binding2);

        ret = acc.getEventEventId("eventCreated");
        assertEquals("eventCreated",ret);

        ret = acc.getEventEventName("eventCreated");
        assertEquals("stuff created",ret);

        ret = acc.getEventEventId("eventFrobbed");
        assertEquals("eventFrobbed",ret);

        ret = acc.getEventEventName("eventFrobbed");
        assertEquals("stuff frobbed",ret);

    }

    public void testEventBindingsDerived() throws Exception {
    	IComponentAccessorGlobal acc = (IComponentAccessorGlobal)getInstance
    		(derivedContext, "Test", IComponentAccessorGlobal.class); 

        registerEvents(derivedContext, dataModel, "event1");
        
        String ret;

        ret = acc.getEventMethod("eventCreated");
        assertEquals("SuperFunction", ret);
        ret = acc.getEventSymbol("eventCreated");
        assertEquals("MyClass::SuperFunction", ret);

        ret = acc.getEventMethod("eventFrobbed");
        assertEquals("HandleThingyFrobbed", ret);
        ret = acc.getEventSymbol("eventFrobbed");
        assertEquals("MyClass::HandleThingyFrobbed", ret);
    }

    ///////////
    
    public void testEventDescriptorsParam() throws Exception {
    	IComponentAccessor acc = (IComponentAccessor)getInstance
    		(baseContext, "Test", IComponentAccessor.class); 

        IComponentInstance instance_ = AdapterHelpers.findComponentInstance(dataModel, 
        		"event0");
        IPropertySource properties_ = AdapterHelpers.getPropertySource(instance_);
        WrappedInstance instance = ComponentWrappers.getInstance(dataModel).getWrappedInstance(instance_, properties_);
        
        String ret;
        IEventBinding binding;
        IEventBinding binding2;
        
        binding = acc.getEventParamBinding(instance, "eventChanged");
        assertNotNull(binding);
        
        // not bound
        binding2 = acc.getEventParamBinding(instance, "eventCreated");
        assertNull(binding2);

        ret = acc.getEventParamEventId(instance, "eventChanged");
        assertEquals("eventChanged",ret);

        ret = acc.getEventParamEventName(instance, "eventChanged");
        assertEquals("stuff changed",ret);

    }

    public void testEventBindingsParam() throws Exception {
    	IComponentAccessor acc = (IComponentAccessor)getInstance
    		(baseContext, "Test", IComponentAccessor.class); 

        IComponentInstance instance_ = AdapterHelpers.findComponentInstance(dataModel, 
		"event0");
        IPropertySource properties_ = AdapterHelpers.getPropertySource(instance_);
        WrappedInstance instance = ComponentWrappers.getInstance(dataModel).getWrappedInstance(instance_, properties_);
        
        String ret;

        ret = acc.getEventParamMethod(instance, "eventChanged");
        assertEquals("HandleThingyChanged", ret);
        ret = acc.getEventParamSymbol(instance, "eventChanged");
        assertEquals("MyClass::HandleThingyChanged", ret);
    }
    
    public void testEventDescriptorsDerivedParam() throws Exception {
    	IComponentAccessor acc = (IComponentAccessor)getInstance
    		(derivedContext, "Test", IComponentAccessor.class); 

        IComponentInstance instance_ = AdapterHelpers.findComponentInstance(dataModel, 
		"event1");
        IPropertySource properties_ = AdapterHelpers.getPropertySource(instance_);
        WrappedInstance instance = ComponentWrappers.getInstance(dataModel).getWrappedInstance(instance_, properties_);
        
        String ret;
        IEventBinding binding;
        IEventBinding binding2;
        
        binding = acc.getEventParamBinding(instance, "eventCreated");
        assertNotNull(binding);
        
        binding2 = acc.getEventParamBinding(instance, "eventFrobbed");
        assertNotNull(binding2);

        ret = acc.getEventParamEventId(instance, "eventCreated");
        assertEquals("eventCreated",ret);

        ret = acc.getEventParamEventName(instance, "eventCreated");
        assertEquals("stuff created",ret);

        ret = acc.getEventParamEventId(instance, "eventFrobbed");
        assertEquals("eventFrobbed",ret);

        ret = acc.getEventParamEventName(instance, "eventFrobbed");
        assertEquals("stuff frobbed",ret);

    }

    public void testEventBindingsDerivedParam() throws Exception {
    	IComponentAccessor acc = (IComponentAccessor)getInstance
    		(derivedContext, "Test", IComponentAccessor.class); 

        IComponentInstance instance_ = AdapterHelpers.findComponentInstance(dataModel, 
		"event1");
        IPropertySource properties_ = AdapterHelpers.getPropertySource(instance_);
        WrappedInstance instance = ComponentWrappers.getInstance(dataModel).getWrappedInstance(instance_, properties_);
    	
        String ret;

        ret = acc.getEventParamMethod(instance, "eventCreated");
        assertEquals("SuperFunction", ret);
        ret = acc.getEventParamSymbol(instance, "eventCreated");
        assertEquals("MyClass::SuperFunction", ret);

        ret = acc.getEventParamMethod(instance, "eventFrobbed");
        assertEquals("HandleThingyFrobbed", ret);
        ret = acc.getEventParamSymbol(instance, "eventFrobbed");
        assertEquals("MyClass::HandleThingyFrobbed", ret);
    }
    
}
