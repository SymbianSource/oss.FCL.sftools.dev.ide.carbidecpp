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

package com.nokia.sdt.uimodel.tests;

import com.nokia.sdt.component.symbian.ComponentProvider;
import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.component.symbian.reconcileProperty.ReconcilePropertyImplementationFactory;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IReconcileProperty;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.datamodel.util.NodePathLookupResult;
import com.nokia.sdt.testsupport.*;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import junit.framework.TestCase;

/**
 * Test the NodePath and ModelUtils.lookupProperty() APIs
 * 
 *
 */
public class NodePathTest extends TestCase {

        protected static final String BASE_DIR = "data/nodepath/";
        static private ComponentProvider provider;
        private IDesignerDataModel dataModel;


     /* @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        
        TestHelpers.setPlugin(TestsPlugin.getDefault());

        if (provider == null)
            provider = TestDataModelHelpers.findOrCreateProviderForUserComponents(BASE_DIR);

        dataModel = TestDataModelHelpers.loadDataModelWithTestComponents(BASE_DIR + "fixture.nxd", provider); 
        
        // register the factory for reconciling properties
        ComponentSystemPlugin.initializeImplementationsRegistry();
        ReconcilePropertyImplementationFactory fac = new ReconcilePropertyImplementationFactory();
        
        ComponentSystemPlugin.getImplementationTypes().addInterface(
                IReconcileProperty.class.getName(),
                fac.getCodeImplAdapterClass(),
                fac.getScriptImplAdapterClass());

    }

    public IComponentInstance getInstance(String name) {
        EObject object = dataModel.findByNameProperty(name);
        if (object == null)
            return null;
        return AdapterHelpers.getComponentInstance(object);
    }

    public IPropertySource getProperties(IComponentInstance instance) {
        return ModelUtils.getPropertySource(instance.getEObject());
    }

    public void testSimpleProperty() throws Exception {
        IComponentInstance base = getInstance("base");
        assertNotNull(base);
        // finds the value of the count property
        NodePathLookupResult result = ModelUtils.readProperty(base.getEObject(), 
                "count", false);
        assertNull(result.status);
        assertEquals(base, result.instance);
        assertEquals(result.properties, result.instance);
        assertTrue(result.result instanceof Integer);
        assertEquals("count", result.propertyName);
        assertEquals(42, ((Integer)result.result).intValue());
    }

    public void testReferenceProperty1() throws Exception {
        IComponentInstance base = getInstance("base");
        assertNotNull(base);
        IComponentInstance other = getInstance("other");
        assertNotNull(other);
        
        // find the reference property -- the name, here
        NodePathLookupResult result = ModelUtils.readProperty(base.getEObject(), 
                "ref", false);
        assertNull(result.status);
        assertEquals(base, result.instance);
        assertEquals(result.properties, result.instance);
        assertEquals("ref", result.propertyName);
        assertEquals("other", result.result);
    }


    public void testReferenceProperty2() throws Exception {
        IComponentInstance base = getInstance("base");
        assertNotNull(base);
        IComponentInstance other = getInstance("other");
        assertNotNull(other);
        
        // find the reference property -- the value, here
        NodePathLookupResult result = ModelUtils.readProperty(base.getEObject(), 
                "ref->", false);
        assertNull(result.status);
        assertEquals(other, result.instance);
        assertEquals(other, result.instance);
        assertEquals(null, result.propertyName);
        assertEquals(other, result.result);
    }

    public void testReferenceProperty3() throws Exception {
        IComponentInstance base = getInstance("base");
        assertNotNull(base);
        IComponentInstance other = getInstance("other");
        assertNotNull(other);
        
        // find property through the reference property
        NodePathLookupResult result = ModelUtils.readProperty(base.getEObject(), 
                "ref->compound.text", false);
        assertNull(result.status);
        assertEquals(other, result.instance);
        assertEquals(other, result.instance);
        assertEquals("text", result.propertyName);
        assertEquals("Oops", result.result);
    }

    public void testUnresolvedCompoundProperty() throws Exception {
        IComponentInstance base = getInstance("testCBA_Builtin");
        assertNotNull(base);
        
        // do not resolve the compound property to a string
        NodePathLookupResult result = ModelUtils.readProperty(base.getEObject(), 
                "CBA", false);
        assertNull(result.status);
        assertEquals(base, result.instance);
        assertNotSame(result.result, result.properties);
        assertEquals("CBA", result.propertyName);
        assertTrue(result.result instanceof IPropertySource);
        
        IPropertySource ps = (IPropertySource) result.result;
        assertEquals("EAknSoftkeyOptions", ps.getPropertyValue("leftId"));
    }

    public void testResolvedCompoundProperty1() throws Exception {
        IComponentInstance base = getInstance("testCBA_Builtin");
        assertNotNull(base);
        
        // resolve the compound property to a string
        NodePathLookupResult result = ModelUtils.readProperty(base.getEObject(), 
                "CBA", true);
        assertNull(result.status);
        assertEquals(base, result.instance);
        assertEquals("CBA", result.propertyName);
        assertNotSame(result.result, result.properties);
        assertEquals("r_avkon_softkeys_options_exit", result.result);
        
    }

    public void testResolvedCompoundProperty2() throws Exception {
        IComponentInstance base = getInstance("testCBA_Custom");
        assertNotNull(base);
     
        // resolve the compound property to a string
        NodePathLookupResult result = ModelUtils.readProperty(base.getEObject(), 
                "CBA", true);
        assertNull(result.status);
        assertEquals(base, result.instance);
        assertNotSame(result.result, result.properties);
        assertEquals("CBA", result.propertyName);
        assertEquals("com.nokia.sdt.series60.test.CBA.Type.CUSTOM", result.result);
        
    }

    public void testDotRef() throws Exception {
        IComponentInstance base = getInstance("base");
        assertNotNull(base);
        // finds the instance itself
        NodePathLookupResult result = ModelUtils.readProperty(base.getEObject(), 
                ".", false);
        assertNull(result.status);
        assertEquals(base, result.instance);
        assertEquals(null, result.propertyName);
        assertEquals(result.properties, result.instance);
        assertEquals(base, result.result);
    }

    public void testChildOfType() throws Exception {
        IComponentInstance container = getInstance("container");
        assertNotNull(container);
        IComponentInstance child = getInstance("child");
        assertNotNull(child);
        
        // finds the child of the given type
        NodePathLookupResult result = ModelUtils.readProperty(container.getEObject(), 
                "[com.nokia.examples.base]", false);
        assertNull(result.status);
        assertEquals(child, result.instance);
        assertEquals(result.properties, result.instance);
        assertEquals(child, result.result);
        assertEquals(null, result.propertyName);
        
        // no child of this type
        result = ModelUtils.readProperty(container.getEObject(), 
                "[com.nokia.examples.wacky]", false);
        assertNotNull(result.status);
    }

    public void testChildOfTypeProperty() throws Exception {
        IComponentInstance container = getInstance("container");
        assertNotNull(container);
        IComponentInstance child = getInstance("child");
        assertNotNull(child);
        
        // finds the child of the given type
        NodePathLookupResult result = ModelUtils.readProperty(container.getEObject(), 
                "[com.nokia.examples.base].count", false);
        assertNull(result.status);
        assertEquals(child, result.instance);
        assertEquals(result.properties, result.instance);
        assertEquals(31, ((Integer)result.result).intValue());
        assertEquals("count", result.propertyName);
        
        // no child of this type
        result = ModelUtils.readProperty(container.getEObject(), 
                "[com.nokia.examples.wacky]", false);
        assertNotNull(result.status);
    }
    
    public void testParent() throws Exception {
        IComponentInstance container = getInstance("container");
        assertNotNull(container);
        IComponentInstance child = getInstance("child");
        assertNotNull(child);
        
        // finds the parent
        NodePathLookupResult result = ModelUtils.readProperty(
                child.getEObject(), 
                "[parent]", false);
        assertNull(result.status);
        assertEquals(container, result.instance);
        assertEquals(result.properties, result.instance);
        assertEquals(container, result.result);
        assertEquals(null, result.propertyName);
        
        // no parent of this 
        result = ModelUtils.readProperty(
                container.getEObject(), 
                "[parent]", false);
        assertNotNull(result.status);
        
    }

    public void testWriting() throws Exception {
        IComponentInstance base = getInstance("base");
        assertNotNull(base);
        IStatus status = ModelUtils.writeProperty(base.getEObject(), 
                "count", new Integer(123));
        assertNull(status);
        IPropertySource properties = getProperties(base);
        Object value = properties.getPropertyValue("count");
        assertEquals(123, ((Integer)value).intValue());
    }


    public void testWritingCompound() throws Exception {
        IComponentInstance other = getInstance("other");
        assertNotNull(other);
        IStatus status = ModelUtils.writeProperty(other.getEObject(), 
                "compound.text", "Never!");
        assertNull(status);
        IPropertySource properties = getProperties(other);
        IPropertySource compound = (IPropertySource) properties.getPropertyValue("compound");
        Object value = compound.getPropertyValue("text");
        assertEquals("Never!", value);
    }

    public void testWritingResolvable() throws Exception {
        IComponentInstance base = getInstance("testCBA_Builtin");
        assertNotNull(base);

        // builtin by default
        NodePathLookupResult result = ModelUtils.readProperty(base.getEObject(), 
                "CBA", true);
        assertNotSame("com.nokia.sdt.series60.test.CBA.Type.CUSTOM", result.result);
        
        // change text
        IStatus status = ModelUtils.writeProperty(base.getEObject(), 
                "CBA.leftText", "Thwack!");
        assertNull(status);

        // now should resolve to custom
        result = ModelUtils.readProperty(base.getEObject(), 
                "CBA", true);
        assertEquals("com.nokia.sdt.series60.test.CBA.Type.CUSTOM", result.result);
    }

    public void testWritingEditable() throws Exception {
        IComponentInstance base = getInstance("testCBA_Builtin");
        assertNotNull(base);

        // change enum directly
        IStatus status = ModelUtils.writeProperty(base.getEObject(), 
                "CBA", "r_avkon_softkeys_ok_cancel");
        assertNull(status);

        // now individual items should resolve
        NodePathLookupResult result;
        result = ModelUtils.readProperty(base.getEObject(), 
                "CBA.leftId", true);
        assertEquals("EAknSoftkeyOk", result.result);
        result = ModelUtils.readProperty(base.getEObject(), 
                "CBA.rightId", true);
        assertEquals("EAknSoftkeyCancel", result.result);
    }

    public void testAttributeSelf() throws Exception {
        IComponentInstance base = getInstance("base");
        assertNotNull(base);
        // finds the value of the "test-attribute" attribute
        NodePathLookupResult result = ModelUtils.readAttributeFromPath(base.getEObject(), "@test-attribute");
        assertNull(result.status);
        assertEquals(base, result.instance);
        assertEquals("false", result.result.toString());
    }

    public void testMissingAttribute() throws Exception {
        IComponentInstance base = getInstance("base");
        assertNotNull(base);
        // should not find the value of the "missing-attribute" attribute
        NodePathLookupResult result = ModelUtils.readAttributeFromPath(base.getEObject(), "@missing-attribute");
        assertNotNull(result.status);
    }
    
    public void testAttributeParent() throws Exception {
        IComponentInstance container = getInstance("container");
        assertNotNull(container);
        IComponentInstance child = getInstance("child");
        assertNotNull(child);
        // finds the value of the "test-attribute" attribute
        NodePathLookupResult result = ModelUtils.readAttributeFromPath(child.getEObject(), "[parent]@parent-attribute");
        assertNull(result.status);
        assertEquals(container, result.instance);
        assertEquals("true", result.result.toString());
    }

    public void testErrors() throws Exception {
        IComponentInstance base = getInstance("child");
        assertNotNull(base);

        NodePathLookupResult result;
        result = ModelUtils.readProperty(base.getEObject(), 
                "[parent]/[foo]", true);
        assertNotNull(result.status);
        assertTrue(result.key.indexOf("InvalidPropertyPath") >= 0);

        result = ModelUtils.readProperty(base.getEObject(), 
                "[parent].[foo]", true);
        assertNotNull(result.status);
        assertTrue(result.key.indexOf("NoSuchChildOfType") >= 0);

        result = ModelUtils.readProperty(base.getEObject(), 
                "[parent]@[foo]", true);
        assertNotNull(result.status);
    
        result = ModelUtils.readProperty(base.getEObject(), 
                "@@[foo]", true);
        assertNotNull(result.status);
        
        result = ModelUtils.readProperty(base.getEObject(), 
                "[parent]@parent-attribute.foo", true);
        assertNotNull(result.status);
    
   }
}
