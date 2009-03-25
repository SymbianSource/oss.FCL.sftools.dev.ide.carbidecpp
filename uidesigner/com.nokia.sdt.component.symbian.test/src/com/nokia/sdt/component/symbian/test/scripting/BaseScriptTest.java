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
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.component.adapter.IComponentScriptAdapter;
import com.nokia.sdt.component.symbian.ComponentProvider;
import com.nokia.sdt.component.symbian.scripting.*;
import com.nokia.sdt.component.symbian.test.PluginTest;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.displaymodel.GlobalCache;
import com.nokia.sdt.scripting.*;
import com.nokia.sdt.testsupport.*;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import java.io.*;

import junit.framework.TestCase;

/**
 * 
 *
 */
public abstract class BaseScriptTest extends TestCase {

    static protected final String BASE_DIR = "data/scripts/";
    protected static boolean DUMP = false;
    static ComponentProvider provider;
    protected IDesignerDataModel dataModel;
    protected IComponentSet set;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        Logging.consoleLog = null;
        TestHelpers.setPlugin(PluginTest.getDefault());
        ScriptingManager.getInstance().registerClassLoaderFor(PluginTest.getDefault());
        
        if (provider == null) {
            provider = TestDataModelHelpers.findOrCreateProviderForUserComponents("data/scripting");
            provider.inhibitPluginScan();
        }
        
        dataModel = TestDataModelHelpers.loadDataModelWithTestComponents("data/scripting/fixture.nxd", provider); 

        set = dataModel.getComponentSet();

        
    }
    
    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        dataModel.dispose();
        GlobalCache.disposeAll();
    }

    File getDataFile(String relpath) throws ScriptException {
        try {
            return FileHelpers.projectRelativeFile(relpath);
        } catch (Exception e) {
            throw new ScriptException(e);
        }
    }


    protected IScriptContext loadAndCompile(final File scriptFile, IScriptScope parentScope) throws Exception {
        IScriptContext context = ScriptingManager.getInstance().newScriptContext(parentScope, true);
        
        ScriptGlobals.wrapScriptHandlingCode(
                context,
                scriptFile.getParentFile(),
                new IScriptContextWrapper() {

                    public Object run(IScriptContext context) throws Exception {
                        context.compileAndExecute(scriptFile);
                        return null;
                    }});
        
        return context;
    }

    protected IScriptContext loadAndCompile(String id, String text, IScriptScope parentScope) throws Exception {
        IScriptContext context = ScriptingManager.getInstance().newScriptContext(parentScope, true);
        context.compileAndExecute(new Path(id), new StringReader(text));
        return context;
    }

    protected Object loadAndCreate(IComponentScriptAdapter adapter,
            String filename, String prototype, Class klass) throws Exception {
        
        IScriptContext context = adapter.getContextForFile(FileHelpers.projectRelativeFile(BASE_DIR + filename), 
                null);
        assertNotNull(context);
        
        IScriptObject object = context.getScope().createInstance(prototype);
        assertNotNull(object);
        
        return object.wrapObjectInInterface(klass);
    }
    
    protected IScriptContext getScriptContext(String filename, IComponent component) throws Exception {
        IComponentScriptAdapter adapter = (IComponentScriptAdapter) component.getAdapter(IComponentScriptAdapter.class);

        IScriptContext context = adapter.getContextForFile(FileHelpers.projectRelativeFile(BASE_DIR + filename), 
                null);
        assertNotNull(context);

        return context;
    }
    
    protected Object getInstance(IScriptContext context, String prototype, Class klass) throws ScriptException {
        IScriptObject object = context.getScope().createInstance(prototype);
        assertNotNull(object);

        return object.wrapObjectInInterface(klass);
    }
    
    protected WrappedInstance getWrappedInstance(IComponentInstance instance) {
        IPropertySource properties = AdapterHelpers.getPropertySource(instance);
        return ComponentWrappers.getInstance(instance).getWrappedInstance(instance, properties);
    }
    
    protected WrappedInstance[] getWrappedChildren(IComponentInstance instance) {
        return ComponentWrappers.getInstance(instance).getWrappedInstanceArray(instance.getChildren());
        
    }

    protected WrappedProperties getWrappedProperties(IComponentInstance instance) {
        IPropertySource properties = AdapterHelpers.getPropertySource(instance);
        return ComponentWrappers.getInstance(instance).getWrappedProperties(properties);
    }

    protected void registerInstanceGlobals(IScriptContext context, IDesignerDataModel dataModel, String name) throws ScriptException {
        IComponentInstance instance_ = AdapterHelpers.findComponentInstance(dataModel, name);
        IPropertySource properties_ = AdapterHelpers.getPropertySource(instance_);
        
        WrappedProperties properties = ComponentWrappers.getInstance(dataModel).getWrappedProperties(properties_);
        WrappedInstance instance = ComponentWrappers.getInstance(dataModel).getWrappedInstance(instance_, properties_);

        context.getTransientGlobalScope().defineObject("instance", instance);
        context.getTransientGlobalScope().defineObject("properties", properties);
        
    }

    protected void registerInstanceGlobals(IScriptContext context, IDesignerDataModel dataModel, IComponentInstance instance_) throws ScriptException {
        IPropertySource properties_ = AdapterHelpers.getPropertySource(instance_);
        
        WrappedProperties properties = ComponentWrappers.getInstance(dataModel).getWrappedProperties(properties_);
        WrappedInstance instance = ComponentWrappers.getInstance(dataModel).getWrappedInstance(instance_, properties_);

        context.getTransientGlobalScope().defineObject("instance", instance);
        context.getTransientGlobalScope().defineObject("properties", properties);

        EObject parent = instance_.getParent();
        context.getTransientGlobalScope().defineObject("parent", 
                ComponentWrappers.getInstance(dataModel).getWrappedInstance(parent));
        EObject[] children = instance_.getChildren();
        context.getTransientGlobalScope().defineObject("children", 
                ComponentWrappers.getInstance(dataModel).getWrappedInstanceArray(children));

    }

    protected void registerAttributes(IScriptContext context, IComponent component) throws ScriptException {

        IAttributes attrs_ = (IAttributes) component.getAdapter(IAttributes.class);
        WrappedAttributes attrs = ComponentWrappers.getInstance().getWrappedAttributes(attrs_);

        context.getTransientGlobalScope().defineObject("attributes", attrs);
        
    }

    protected void registerEvents(IScriptContext context, IDesignerDataModel dataModel, String instanceName) throws ScriptException {
        IComponentInstance instance_ = AdapterHelpers.findComponentInstance(dataModel, instanceName);
        IPropertySource properties_ = AdapterHelpers.getPropertySource(instance_);
        WrappedInstance instance = ComponentWrappers.getInstance(dataModel).getWrappedInstance(instance_, properties_);

        context.getTransientGlobalScope().defineObject("events", instance.jsGet_events());
        
    }


}
