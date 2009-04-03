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

import com.nokia.sdt.component.symbian.scripting.ScriptingManager;
import com.nokia.sdt.component.symbian.scripting.ScriptingUtils;
import com.nokia.sdt.component.symbian.scripting.impl.ScriptScope;
import com.nokia.sdt.datamodel.adapter.ILayout;
import com.nokia.sdt.scripting.*;
import com.nokia.sdt.testsupport.FileHelpers;

import java.util.Collection;
import java.util.Iterator;

/**
 * 
 *
 */
public class ScriptBasicTest extends BaseScriptTest {

    public interface IComponentFiddle {
        public int fiddle();
    }

    public interface IComponentFaddle {
        public int faddle();
    }
    public void testContexts() throws Exception {
        IScriptContext context = loadAndCompile(FileHelpers.projectRelativeFile(
                BASE_DIR + "base.js"), null);
        assertNotNull(context);
        
        
    }
    public void testBasic() throws Exception {
        IScriptContext context = loadAndCompile(FileHelpers.projectRelativeFile(
                BASE_DIR + "base.js"), null);
        IScriptScope scope = context.getScope();
        // globals should be automatically added
        assertNotNull(scope.getParentScope());
        IScriptObject obj = scope.createInstance("Base");
        assertNotNull(obj);
        assertNotSame(obj.getScope(), scope);
        assertEquals(scope, obj.getScope().getParentScope());
        assertEquals(context.getScope(), obj.getScope().getParentScope());
    }
    
    public void testBasicText() throws Exception {
        IScriptContext context = loadAndCompile(
                "<Base>",
                "function Base() { }\n"
                +"Base.prototype = new Object()\n", null);
        IScriptObject obj = context.getScope().createInstance("Base");
        IScriptScope scope = context.getScope();
        
        assertNotNull(obj);
        assertEquals(scope, obj.getScope().getParentScope());
        assertEquals(context.getScope(), obj.getScope().getParentScope());
        
    }

    public interface IComponentRendering {
        public int draw(int value);
    }

    public void testClassLoader() throws Exception {
        IScriptContext context = loadAndCompile(FileHelpers.projectRelativeFile(
                BASE_DIR + "base.js"), null);
        IScriptObject obj = context.getScope().createInstance("Base");

        ILayout l = (ILayout) obj.wrapObjectInInterface(ILayout.class);
        assertNotNull(l);
    }
    
     private interface ISecretInterface {
         
     }
     
     public void testInvalidAccess() throws Exception {
         IScriptContext context = loadAndCompile(FileHelpers.projectRelativeFile(
                 BASE_DIR + "base.js"), null);
         IScriptObject obj = context.getScope().createInstance("Base");

         // this interface is not public
         try {
             obj.wrapObjectInInterface(ISecretInterface.class);
             fail();
         } catch (ScriptException e) {
             
         }
     }
    
     public void testObjectWrapping() throws Exception {
         IScriptContext context = loadAndCompile(FileHelpers.projectRelativeFile(
                 BASE_DIR + "base.js"), null);
         IScriptObject obj = context.getScope().createInstance("Base");

         IComponentRendering render = (IComponentRendering) obj.wrapObjectInInterface(IComponentRendering.class);
         assertNotNull(render);
         
         // the function keeps a counter which starts at 4 and
         // returns the product of this and the negative of the number passed in
         int ret;

         ret = render.draw(1);
         assertTrue(ret == -4);

         // the counter increments each time
         ret = render.draw(1);
         assertTrue(ret == -5);
     }

     
     /** Test whether we can wrap the same object in two different
      * interfaces
      * @throws Exception
      */
     public void testObjectWrapping2() throws Exception {
         IScriptContext context = loadAndCompile(FileHelpers.projectRelativeFile(
                 BASE_DIR + "twice.js"), null);

         IScriptObject obj = context.getScope().createInstance("Base");

         Object inst1 = obj.wrapObjectInInterface(IComponentFaddle.class);
         assertNotNull(inst1);
         assertTrue(inst1 instanceof IComponentFaddle);

         // the function keeps a global counter
         IComponentFaddle faddle = (IComponentFaddle) inst1;
         int ret;
         
         ret = faddle.faddle();
         assertTrue(ret == 23);

         ////////
         
         Object inst2 = obj.wrapObjectInInterface(IComponentFiddle.class);
         assertNotNull(inst2);
         assertTrue(inst2 instanceof IComponentFiddle);

         // the function keeps a counter which starts at 4 and
         // returns the product of this and the negative of the number passed in
         IComponentFiddle fiddle = (IComponentFiddle) inst2;
         
         ret = fiddle.fiddle();
         assertTrue(ret == 23);
         
         //////
         
         // ensure we're not fooling ourselves
         obj = context.getScope().createInstance("Base");
         faddle = (IComponentFaddle) obj.wrapObjectInInterface(IComponentFaddle.class);
         
         ret = faddle.faddle();
         assertEquals(46, ret);
         
     }
     
     public void testGlobalStateRetained1() throws Exception {
         IScriptContext context = loadAndCompile(FileHelpers.projectRelativeFile(
                 BASE_DIR + "base.js"), null);
         Object obj = context.getScope().createInstance("Base").wrapObjectInInterface(IComponentRendering.class);
         assertNotNull(obj);
         assertTrue(obj instanceof IComponentRendering);

         IComponentRendering renderBase = (IComponentRendering) obj;

         // the function keeps a counter which starts at 4 and
         // returns the product of this and the negative of the number passed in
         int ret;

         ret = renderBase.draw(1);
         assertTrue(ret == -4);

         // the counter increments each time
         ret = renderBase.draw(1);
         assertTrue(ret == -5);
     }

     // test that we can load the same script twice and keep its globals
     public void testGlobalStateRetained2() throws Exception {
         IScriptContext context = loadAndCompile(FileHelpers.projectRelativeFile(
                 BASE_DIR + "base.js"), null);
         Object obj = context.getScope().createInstance("Base").wrapObjectInInterface(IComponentRendering.class);
         assertNotNull(obj);
         assertTrue(obj instanceof IComponentRendering);

         IComponentRendering renderBase1 = (IComponentRendering) context.getScope().createInstance("Base").wrapObjectInInterface(IComponentRendering.class);
         IComponentRendering renderBase2 = (IComponentRendering) context.getScope().createInstance("Base").wrapObjectInInterface(IComponentRendering.class);

         // side check: must be different objects
         assertNotSame(renderBase1, renderBase2);
         
         // the function keeps a counter which starts at 4 and
         // returns the product of this and the negative of the number passed in
         int ret1, ret2;

         // make sure the two seemingly distinct objects use the same counter
         ret1 = renderBase1.draw(1);
         assertEquals(-4 * 1, ret1);

         ret2 = renderBase2.draw(1);
         assertEquals(-5 * 1, ret2);
     }

     public void testDerivedObjects() throws Exception {
         IScriptContext context = loadAndCompile(FileHelpers.projectRelativeFile(
                 BASE_DIR + "base.js"), null);
         IComponentRendering renderBase = (IComponentRendering) context.getScope().createInstance("Base").wrapObjectInInterface(IComponentRendering.class);

         // get derived script, which chains to the base script
         IScriptContext context2 = loadAndCompile(FileHelpers.projectRelativeFile(
                 BASE_DIR + "derived.js"), 
                 (ScriptScope) context.getScope());
         IComponentRendering renderDerived = (IComponentRendering) context2.getScope().createInstance("Derived").wrapObjectInInterface(IComponentRendering.class);

         assertNotSame(renderBase, renderDerived);

         // the base function keeps a counter which starts at 4 and
         // returns the product of this and the negative of the number passed in
         int ret;

         ret = renderBase.draw(3);
         assertEquals(4 * -3, ret);

         // the derived function keeps a counter which starts at 10 and
         // returns the sum of this times the number passed in
         // plus the base's value

         // base.counter = 5 now
         // derived.counter = 10
         ret = renderDerived.draw(1);
         assertEquals(-5 * 1 + 10 * 1, ret);

         // the base counter increments each time
         // base.counter = 6
         ret = renderBase.draw(2);
         assertEquals(-6 * 2, ret);

         // the derived counter increments by 10
         // base.counter = 7
         // derived.counter = 20
         ret = renderDerived.draw(10);
         assertEquals(-7 * 10 + 20 * 10, ret);
     }

     public void testScopes() throws Exception {
         IScriptContext context = loadAndCompile("<unique>", "", null);
         IScriptScope global = ScriptingManager.getInstance().getGlobalScope();
         assertNotNull(global);
         IScriptScope contextScope = context.getScope();
         assertNotSame(global, contextScope);
         
         global.defineObject("foo", this);
         Object val = global.findObject("foo");
         assertNotNull(val);
         assertTrue(val == this);
         
         assertFalse(contextScope.objectExists("foo"));
         assertNull(contextScope.findObject("foo"));
         assertTrue(contextScope.objectVisible("foo"));
         assertEquals(val, contextScope.searchObject("foo"));
         
     }

     public void testScopes2() throws Exception {
         IScriptContext context = loadAndCompile("<unique>", "", null);
         IScriptScope global = ScriptingManager.getInstance().getGlobalScope();
         assertNotNull(global);
         IScriptScope contextScope = context.getScope();
         assertNotSame(global, contextScope);
         
         IScriptScope subScope = contextScope.createScope();
         assertNotNull(subScope);
         assertEquals(subScope.getParentScope(), contextScope);
         
         subScope.defineObject("foo", this);
         Object val = subScope.findObject("foo");
         assertNotNull(val);
         assertTrue(val == this);
         
         assertFalse(contextScope.objectExists("foo"));
         assertNull(contextScope.findObject("foo"));
         
     }


     public void testScopes3() throws Exception {
         IScriptContext context = loadAndCompile("<unique>", "", null);
         IScriptScope global = ScriptingManager.getInstance().getGlobalScope();
         assertNotNull(global);
         IScriptScope contextScope = context.getScope();
         assertNotSame(global, contextScope);
         assertEquals(global, context.getParentScope());

         // adding a scope at this point does not make it visible to
         // other child scopes
         IScriptScope transientGlobals = global.createScope();
         assertNotNull(transientGlobals);
         
         assertNotSame(transientGlobals, contextScope.getParentScope());
         transientGlobals.defineObject("foo", this);
         assertNull(contextScope.searchObject("foo"));
     }

     public interface IMyTest {
         public String myfunc();
     }
     public void testTransientGlobals() throws Exception {
         IScriptContext context = loadAndCompile(FileHelpers.projectRelativeFile(
                 BASE_DIR + "transientGlobals.js"), null);

         IScriptScope transientGlobals = context.getTransientGlobalScope();

         IScriptObject instance = context.getScope().createInstance("Proto", "inst");
         
         Object val;
         
         IMyTest test = (IMyTest) instance.wrapObjectInInterface(IMyTest.class);

         transientGlobals.defineObject("globalString", "Hello!");

         if (DUMP) {
             System.out.println("---- Before run:");
             context.getScope().dump("context", System.out);
             transientGlobals.dump("transient", System.out);
             instance.getScope().dump("instance", System.out);
         }
         
         // each routine modifies the global
         val = instance.getScope().callFunction(context.getScope(), "myfunc", new Object[0] );
         
         if (DUMP) {
             System.out.println("---- After run:");
             context.getScope().dump("context", System.out);
             transientGlobals.dump("transient", System.out);
             instance.getScope().dump("instance", System.out);
         }
         
         assertEquals("77Hello!", val);
         
         
         // make sure it doesn't move around
         assertTrue(transientGlobals.objectExists("globalString"));
         
         // assert that new globals go to the right place
         assertTrue(transientGlobals.objectExists("myglobal"));
         assertTrue(transientGlobals.objectExists("anotherglobal"));

         // test that instance vars are correct
         assertTrue(instance.getScope().objectExists("foo"));
         
         // modify again (exact same routine)
         val = test.myfunc();
         assertEquals("7777Hello!", val);

         // make sure it doesn't move around
         assertTrue(transientGlobals.objectExists("globalString"));
         
         // assert that new globals go to the right place
         assertTrue(transientGlobals.objectExists("myglobal"));
         assertTrue(transientGlobals.objectExists("anotherglobal"));

         // clear
         transientGlobals.deleteAllObjects();
         assertFalse(transientGlobals.objectExists("globalString"));
         assertFalse(transientGlobals.objectExists("myglobal"));
         assertFalse(transientGlobals.objectExists("anotherglobal"));

         // run again -- should fail since globalString is gone
         try {
             val = test.myfunc();
             fail();
         } catch (Throwable e) {
             
         }
         
     }

     public void testTransientGlobals2() throws Exception {
         IScriptContext context = loadAndCompile(FileHelpers.projectRelativeFile(
                 BASE_DIR + "transientGlobals2.js"), null);

         IScriptScope transientGlobals = context.getTransientGlobalScope();
         IScriptObject instance = context.getScope().createInstance("Proto", "inst");
         
         Object val;
         IMyTest test = (IMyTest) instance.wrapObjectInInterface(IMyTest.class);

         transientGlobals.defineObject("globalString", "Hello!");

         if (DUMP) {
             System.out.println("---- Before run:");
             context.getScope().dump("context", System.out);
             transientGlobals.dump("transient", System.out);
             instance.getScope().dump("instance", System.out);
         }
         
         // modify object with same name, which is actually an instance var
         val = test.myfunc();
         assertEquals("InstanceVar1", val);

         // make sure it doesn't move around
         assertTrue(instance.getScope().objectExists("globalString"));
         assertTrue(transientGlobals.objectExists("globalString"));
         
         // assert that new globals go to the right place
         assertTrue(transientGlobals.objectExists("myglobal"));
         assertTrue(transientGlobals.objectExists("anotherglobal"));

         // clear
         transientGlobals.deleteAllObjects();
         assertFalse(transientGlobals.objectExists("globalString"));
         assertFalse(transientGlobals.objectExists("myglobal"));
         assertFalse(transientGlobals.objectExists("anotherglobal"));

         // run again -- should succeed with same result even through globalString is gone
         val = test.myfunc();
         assertEquals("InstanceVar2", val);
        
     }
     
     public void testTransientGlobals3() throws Exception {
         IScriptContext context = loadAndCompile(FileHelpers.projectRelativeFile(
                 BASE_DIR + "transientGlobals3.js"), null);

         IScriptScope transientGlobals = context.getTransientGlobalScope();
         IScriptObject instance = context.getScope().createInstance("Proto", "inst");
         
         Object val;
         IMyTest test = (IMyTest) instance.wrapObjectInInterface(IMyTest.class);

         transientGlobals.defineObject("globalString", "Hello!");

         if (DUMP) {
             System.out.println("---- Before run:");
             context.getScope().dump("context", System.out);
             transientGlobals.dump("transient", System.out);
             instance.getScope().dump("instance", System.out);
         }
         
         // modify object with same name, which is actually a script global
         val = test.myfunc();
         assertEquals("ScriptGlobal1", val);

         // make sure it doesn't move around
         assertTrue(context.getScope().objectExists("globalString"));

         // make sure it doesn't move around
         assertTrue(transientGlobals.objectExists("globalString"));
         
         // assert that new globals go to the right place
         assertTrue(transientGlobals.objectExists("myglobal"));
         assertTrue(transientGlobals.objectExists("anotherglobal"));

         // clear
         transientGlobals.deleteAllObjects();
         assertFalse(transientGlobals.objectExists("globalString"));
         assertFalse(transientGlobals.objectExists("myglobal"));
         assertFalse(transientGlobals.objectExists("anotherglobal"));

         // run again -- should succeed with same result even through globalString is gone
         val = test.myfunc();
         assertEquals("ScriptGlobal2", val);
        
     }
     
     public interface IGlobal {
         String getGlobal();
     }
     
     public void testIncludes() throws Exception {
         IScriptContext context = loadAndCompile(FileHelpers.projectRelativeFile(
                 BASE_DIR + "testinclude.js"), null);

         IScriptObject instance = context.getScope().createInstance("Test", "inst");
         IGlobal global = (IGlobal) instance.wrapObjectInInterface(IGlobal.class);
      
         assertEquals("library", global.getGlobal());
     }
     
     public void testIncludes2() throws Exception {
         IScriptContext context = loadAndCompile(FileHelpers.projectRelativeFile(
                 BASE_DIR + "testinclude2.js"), null);

         IScriptObject instance = context.getScope().createInstance("Test", "inst");
         IGlobal global = (IGlobal) instance.wrapObjectInInterface(IGlobal.class);
      
         assertEquals("library", global.getGlobal());
     }
     
     public interface IStuff {
         Object getStuff(int version);
     }

     public void testUnwrappingArrays() throws Exception {
         IScriptContext context = loadAndCompile(FileHelpers.projectRelativeFile(
                 BASE_DIR + "testarrays.js"), null);

         IScriptObject instance = context.getScope().createInstance("Test", "inst");
         IStuff stuff = (IStuff) instance.wrapObjectInInterface(IStuff.class);
      
         Object obj = stuff.getStuff(0);
         Object[] arr = ScriptingUtils.unwrapArray(obj, false);
    	 assertNotNull(arr);
    	 assertEquals(2, arr.length);
    	 assertEquals("foo", arr[0]);
    	 assertEquals("bar", arr[1]);
    	 
    	 obj = stuff.getStuff(1);
    	 arr = ScriptingUtils.unwrapArray(obj, false);
    	 assertNotNull(arr);
    	 assertEquals(2, arr.length);
    	 // could be float or int, not sure
    	 assertEquals(23, ((Number)arr[0]).intValue());
    	 assertEquals(45, ((Number)arr[1]).intValue());

    	 obj = stuff.getStuff(2);
    	 arr = ScriptingUtils.unwrapArray(obj, false);
    	 assertNotNull(arr);
    	 assertEquals(2, arr.length);
    	 assertEquals(new Double(23), arr[0]);
    	 assertEquals(new Double(45), arr[1]);
    	 
    	 obj = stuff.getStuff(3);
    	 arr = ScriptingUtils.unwrapArray(obj, false);
    	 assertNull(arr);
    	 arr = ScriptingUtils.unwrapArray(obj, true);
    	 assertNotNull(arr);
    	 assertEquals(1, arr.length);
    	 assertEquals("foo", arr[0]);
    	 

     }
     
     public void testUnwrappingCollections() throws Exception {
         IScriptContext context = loadAndCompile(FileHelpers.projectRelativeFile(
                 BASE_DIR + "testarrays.js"), null);

         IScriptObject instance = context.getScope().createInstance("Test", "inst");
         IStuff stuff = (IStuff) instance.wrapObjectInInterface(IStuff.class);
      
         Object obj = stuff.getStuff(0);
         Collection coll = ScriptingUtils.unwrapCollection(obj, false);
    	 assertNotNull(coll);
    	 assertEquals(2, coll.size());
    	 Iterator iter = coll.iterator();
    	 assertEquals("foo", iter.next());
    	 assertEquals("bar", iter.next());
    	 
    	 obj = stuff.getStuff(1);
    	 coll = ScriptingUtils.unwrapCollection(obj, false);
    	 assertNotNull(coll);
    	 assertEquals(2, coll.size());
    	 iter = coll.iterator();
    	 assertEquals(23, ((Number)iter.next()).intValue());
    	 assertEquals(45, ((Number)iter.next()).intValue());

    	 obj = stuff.getStuff(2);
    	 coll = ScriptingUtils.unwrapCollection(obj, false);
    	 assertNotNull(coll);
    	 iter = coll.iterator();
    	 assertEquals(2, coll.size());
    	 assertEquals(new Double(23), iter.next());
    	 assertEquals(new Double(45), iter.next());
    	 
    	 obj = stuff.getStuff(3);
    	 coll = ScriptingUtils.unwrapCollection(obj, false);
    	 assertNull(coll);
    	 coll = ScriptingUtils.unwrapCollection(obj, true);
    	 assertNotNull(coll);
    	 assertEquals(1, coll.size());
    	 iter = coll.iterator();
    	 assertEquals("foo", iter.next());
    	 

     }
          
}
