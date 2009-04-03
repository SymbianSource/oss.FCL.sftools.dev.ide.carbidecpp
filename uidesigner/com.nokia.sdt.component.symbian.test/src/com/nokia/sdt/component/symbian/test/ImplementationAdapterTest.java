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

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.symbian.ComponentProvider;
import com.nokia.sdt.component.symbian.implementations.ICodeImplAdapter;
import com.nokia.sdt.component.symbian.implementations.IScriptImplAdapter;
import com.nokia.sdt.component.symbian.scripting.ScriptingManager;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.testsupport.*;

import org.eclipse.emf.ecore.util.EcoreUtil;

import junit.framework.TestCase;

/**
 * 
 *
 */
public class ImplementationAdapterTest extends TestCase {

	static ComponentProvider provider;

	private IComponentSet set;

	private IComponent codeComponent;
	private IComponent scriptComponent;

	private IComponent codeDerivedComponent;
	private IComponent scriptDerivedComponent;

	private IDesignerDataModel dataModel;

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		TestHelpers.setPlugin(PluginTest.getDefault());
        if (provider == null)
            provider = TestDataModelHelpers.findOrCreateProviderForUserComponents("data/display");

        ScriptingManager.getInstance().registerClassLoaderFor(PluginTest.getDefault());
        
        dataModel = TestDataModelHelpers.loadDataModelWithTestComponents("data/display/display.nxd", provider); 
        set = dataModel.getComponentSet();

        codeComponent = set.lookupComponent("com.nokia.examples.codeComp");
        assertNotNull(codeComponent);

        scriptComponent = set.lookupComponent("com.nokia.examples.scriptComp");
        assertNotNull(scriptComponent);

        codeDerivedComponent = set.lookupComponent("com.nokia.examples.codeDerived");
        assertNotNull(codeDerivedComponent);

        scriptDerivedComponent = set.lookupComponent("com.nokia.examples.scriptDerived");
        assertNotNull(scriptDerivedComponent);
	}
	
	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testOneCodeAdapterPerInstance() {
		IComponentInstance instance1 = AdapterHelpers.findComponentInstance(dataModel, "code1");
		IFoo foo1 = (IFoo) EcoreUtil.getRegisteredAdapter(instance1.getEObject(), IFoo.class);
		assertNotNull(foo1);

        IComponentInstance instance2 = AdapterHelpers.findComponentInstance(dataModel, "code2");
		IFoo foo2 = (IFoo) EcoreUtil.getRegisteredAdapter(instance2.getEObject(), IFoo.class);
		assertNotNull(foo2);
		
		ICodeImplAdapter codeAdapter1 = (ICodeImplAdapter) foo1;
		assertNotNull(codeAdapter1);
		Object impl1 = codeAdapter1.getImpl();
		
		ICodeImplAdapter codeAdapter2 = (ICodeImplAdapter) foo2;
		assertNotNull(codeAdapter2);
		Object impl2 = codeAdapter2.getImpl();
		
		assertNotSame(impl2, impl1);
	}
	
	public void testSameCodeImplMultipleInterfaces() {
		IComponentInstance instance = AdapterHelpers.findComponentInstance(dataModel, "code1");
	
		IFoo foo = 
			(IFoo) EcoreUtil.getRegisteredAdapter(instance.getEObject(), 
															IFoo.class);
		assertNotNull(foo);

		IBar bar = 
			(IBar) EcoreUtil.getRegisteredAdapter(instance.getEObject(), 
															IBar.class);
		assertNotNull(bar);

		assertEquals(foo.doFoo(), bar.doBar());

		ICodeImplAdapter codeAdapterFoo = (ICodeImplAdapter) foo;
		assertNotNull(codeAdapterFoo);
		Object implFoo = codeAdapterFoo.getImpl();
		
		ICodeImplAdapter codeAdapterBar = (ICodeImplAdapter) bar;
		assertNotNull(codeAdapterBar);
		Object implBar = codeAdapterBar.getImpl();
		
		assertEquals(implFoo, implBar);
	}

	public void testOneScriptAdapterPerInstance() {
		IComponentInstance instance1 = AdapterHelpers.findComponentInstance(dataModel, "script1");
		IFoo foo1 = (IFoo) EcoreUtil.getRegisteredAdapter(instance1.getEObject(), IFoo.class);
		assertNotNull(foo1);

        IComponentInstance instance2 = AdapterHelpers.findComponentInstance(dataModel, "script2");
		IFoo foo2 = (IFoo) EcoreUtil.getRegisteredAdapter(instance2.getEObject(), IFoo.class);
		assertNotNull(foo2);
		
		IScriptImplAdapter scriptAdapter1 = (IScriptImplAdapter) foo1;
		assertNotNull(scriptAdapter1);
		Object impl1 = scriptAdapter1.getScriptObject();
		
		IScriptImplAdapter scriptAdapter2 = (IScriptImplAdapter) foo2;
		assertNotNull(scriptAdapter2);
		Object impl2 = scriptAdapter2.getScriptObject();
		
		assertNotSame(impl2, impl1);

		IBar bar1 = 
			(IBar) EcoreUtil.getRegisteredAdapter(instance1.getEObject(), 
															IBar.class);
		assertNotNull(bar1);

		IBar bar2 = 
			(IBar) EcoreUtil.getRegisteredAdapter(instance2.getEObject(), 
															IBar.class);
		assertNotNull(bar2);
		
		assertNotSame(bar1, bar2);
		assertTrue(bar1.doBar() != bar2.doBar());
	}
	
	public void testSameScriptImplMultipleInterfaces() {
		IComponentInstance instance = AdapterHelpers.findComponentInstance(dataModel, "script1");
	
		IFoo foo = 
			(IFoo) EcoreUtil.getRegisteredAdapter(instance.getEObject(), 
															IFoo.class);
		assertNotNull(foo);

		IBar bar = 
			(IBar) EcoreUtil.getRegisteredAdapter(instance.getEObject(), 
															IBar.class);
		assertNotNull(bar);
		
		assertEquals(foo.doFoo(), bar.doBar());

		IScriptImplAdapter scriptAdapterFoo = (IScriptImplAdapter) foo;
		assertNotNull(scriptAdapterFoo);
		Object implFoo = scriptAdapterFoo.getScriptObject();
		
		IScriptImplAdapter scriptAdapterBar = (IScriptImplAdapter) bar;
		assertNotNull(scriptAdapterBar);
		Object implBar = scriptAdapterBar.getScriptObject();
		
		assertEquals(implFoo, implBar);
	}
	
	public void testDerivedComponents() {
		IComponentInstance instance1 = AdapterHelpers.findComponentInstance(dataModel, "codeDerived");
		IFoo foo = (IFoo) EcoreUtil.getRegisteredAdapter(instance1.getEObject(), IFoo.class);
		assertNotNull(foo);
		assertTrue(foo.doFoo() > 0);
		
		IComponentInstance instance2 = AdapterHelpers.findComponentInstance(dataModel, "scriptDerived");
		IBar bar = (IBar) EcoreUtil.getRegisteredAdapter(instance2.getEObject(), IBar.class);
		assertNotNull(bar);
		assertTrue(bar.doBar() > 0);
	}
}
