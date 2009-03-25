/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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
/** Test the ComponentLibrary and ComponentSystem loaders
 *  TODO: support more than one CL!
 */
package com.nokia.sdt.component.symbian.test;

import com.nokia.sdt.component.*;
import com.nokia.sdt.component.symbian.*;
import com.nokia.cpp.internal.api.utils.core.FileUtils;

import org.eclipse.core.runtime.*;
import org.osgi.framework.Bundle;

import java.io.File;
import java.net.URL;
import java.util.Iterator;

import junit.framework.TestCase;

/**
 * Test the component provider, refreshing, etc.
 * 
 * 
 * 
 */
public class ProviderTest extends TestCase {
    // Change this once ComponentLibrary changes to store
    // components in directories rather than in empty files.
    static boolean COMPONENT_LIBRARY_USES_DIRECTORIES = false;
    
    static IPath getWorkingPath() {
        return ComponentSystemPlugin.getDefault().getStateLocation();
    }

    /** The runtime location of one temporary component library */
    IPath componentsPath;
 
    /* N.B.: we use java.io.File, etc., because our plugin is
     * not in the workspace.
     */
    /** The directory for componentsPath */
    File componentsDir;
    /** The plugin-relative read-only data directory */
    File dataDir;
    /** The dataDir-relative location for prebuilt components */
    File prebuiltComponentsDir;
	
	ComponentProvider provider;
	Bundle bundle;

    IComponentLibrary clib;

    // we only care about test components here
    IComponentFilter testSdkFilter = new IComponentFilter() {
        public boolean accept(IComponent component) {
            if (!(component instanceof Component))
                return false;
            Component c = (Component) component;
            return c.getSDKName() != null && c.getSDKName().equals("com.nokia.test");
        }  
    };

    protected void setUp() throws Exception {
        // get our prebuilt component data
        bundle = Platform.getBundle(PluginTest.PLUGIN_ID);
        URL url = Platform.find(bundle, new Path("data"));
        assertNotNull(url);
        url = Platform.resolve(url);
        assertEquals("file", url.getProtocol());
        dataDir = new File(url.getPath());
        prebuiltComponentsDir = new File(dataDir, "components");
		
		provider = (ComponentProvider) ComponentSystem.getComponentSystem().getProvider(ComponentProvider.PROVIDER_ID);
		provider.inhibitPluginScan();

        // create a place for our components to live inside
        // our plugin's working space
        componentsPath = getWorkingPath().append("cs").append("components");
        componentsDir = componentsPath.toFile();
        componentsDir.mkdirs();

        // make two components
        createComponent("first");
        createComponent("second");
		
        clib = createStdComponentLibrary();
    }

    protected void tearDown() throws Exception {
        if (provider.findUserComponentLibrary(componentsPath.toOSString()) != null)
            provider.removeUserComponentLibrary(componentsPath.toOSString());

        // get rid of our junk
        IPath wp = getWorkingPath().append("cs");
        FileUtils.delTree(wp.toFile());
    }
    
    /** Create a component in the components directory
     * by copying an implementation from our data directory.
     * 
     * @throws Exception
     */
    void createComponent(String name) throws Exception {
        File from;
        if (COMPONENT_LIBRARY_USES_DIRECTORIES) {
            // 'name' names the directory
            from = new File(prebuiltComponentsDir, name);
        }
        else {
            // 'name' is the base name 
            from = new File(prebuiltComponentsDir, name + ".component");
        }
        
        FileUtils.copyTree(from, componentsDir, null);
    }

    /** Delete a component in the components directory
     * 
     * @throws Exception
     */
    void removeComponent(String name) throws Exception {
        File f;
        if (COMPONENT_LIBRARY_USES_DIRECTORIES) {
            // 'name' names the directory
            f = new File(componentsDir, name);
        }
        else {
            // 'name' is the base name 
            f = new File(componentsDir, name + ".component");
        }
        f.delete();
    }

    /** Overwrite a component in the components directory
     * by copying an implementation from our data directory.
     * 
     * @throws Exception
     */
    void overwriteComponent(String name, String overname) throws Exception {
        File from;
        File to;
        if (COMPONENT_LIBRARY_USES_DIRECTORIES) {
            // 'name' names the directory
            from = new File(prebuiltComponentsDir, overname);
            to = new File(componentsDir, name);
            FileUtils.copyTreeNoParent(from, to, null);
        }
        else {
            // 'name' is the base name 
            from = new File(prebuiltComponentsDir, overname + ".component");
            to = new File(componentsDir, name + ".component");
            FileUtils.copyFile(from, to);
        }
        
    }

    IComponentLibrary createStdComponentLibrary() throws ComponentSystemException {
        IComponentLibrary clib = provider.addUserComponentLibrary(componentsPath.toOSString());
        clib.setBundle(bundle);
        clib.setProvider(provider);
        return clib;
    }

    int countComponents(IComponentLibrary clib) throws ComponentSystemException {
        Iterator iter = clib.iterator();
        assertNotNull(iter);
        
        int count = 0;
        while (iter.hasNext()) {
            IComponent c = (IComponent) iter.next();
            assertNotNull(c);
            count++;
        }
        return count;
    }
    
    public void testComponentSets() throws Exception {
        clib.loadComponents();

        Iterator iter;
        iter = clib.iterator();

        IComponent origbase = null, origex1 = null;
        while (iter.hasNext()) {
            IComponent c = (IComponent) iter.next();
            if (c.getId().equals("com.nokia.examples.baseComponent"))
                origbase = c;
            else if (c.getId().equals("com.nokia.examples.example1"))
                origex1 = c;
            else
                fail("unknown component " + c.getId());
            
            // when coming directly from a clib or provider,
            // the component set is null
            assertNull(c.getComponentSet());
        }

        // make a component set (req'd for lookup of base component)
        ComponentSetResult csr = provider.queryComponents(testSdkFilter);
        assertNotNull(csr);
        assertNotNull(csr.getComponentSet());
        ComponentSet set = (ComponentSet) csr.getComponentSet();
        assertEquals(2, csr.getComponentSet().numComponents());
        
        // the components in the set are shallow copies tied to
        // the component set
        IComponent base = set.lookupComponent("com.nokia.examples.baseComponent");
        assertNotNull(base);
        assertNotSame(base, origbase);
        assertEquals("com.nokia.examples.baseComponent", base.getId());
        assertEquals(set, base.getComponentSet());
        
        IComponent ex1 = set.lookupComponent("com.nokia.examples.example1");
        assertNotNull(ex1);
        assertNotSame(ex1, origex1);
        assertEquals("com.nokia.examples.example1", ex1.getId());
        assertEquals(set, ex1.getComponentSet());
    }

    public void testComponentSets2() throws Exception {
        clib.loadComponents();

        IComponentFilter baseFinder = new IComponentFilter() {

            public boolean accept(IComponent com) {
                if (!(com instanceof Component))
                    return false;
                Component component = (Component) com;
                return component.getSDKName() != null
                && component.getSDKName().equals("com.nokia.test")
                && component.getId().equals("com.nokia.examples.baseComponent");
            }
        };

       IComponent origbase = clib.getComponents(baseFinder)[0];
       assertNotNull(origbase);
       
        // make a component set (req'd for lookup of base component)
        ComponentSetResult csr = provider.queryComponents(testSdkFilter);
        assertNotNull(csr);
        assertNotNull(csr.getComponentSet());
        ComponentSet set = (ComponentSet) csr.getComponentSet();
  
        // the components in the set are shallow copies tied to
        // the component set
        IComponent base = set.lookupComponent("com.nokia.examples.baseComponent");
        assertNotNull(base);
        assertNotSame(base, origbase);

        // check a fact
        assertNull(base.getCategory());
        
        // now change the component on disk (the category is set to "Foo")
        overwriteComponent("first", "altfirst");
        
        // basic check: component is not loaded yet
        assertNull(base.getCategory());
        assertNull(origbase.getCategory());
        
        // refresh clib
        provider.refreshUserComponentLibrary(componentsPath.toOSString());
        
        // component in set is not changed
        assertNull(base.getCategory());
        // the original has not changed either
        assertNull(origbase.getCategory());

        // make sure the new set has the new component
        IComponent currbase = clib.getComponents(baseFinder)[0];
        assertNotNull(currbase);
        assertNotNull(currbase.getCategory());
        assertEquals("Foo", currbase.getCategory());
        
        // make a new component set
        ComponentSetResult csr2 = provider.queryComponents(testSdkFilter);
        assertNotNull(csr2);
        assertNotNull(csr2.getComponentSet());
        ComponentSet newSet = (ComponentSet) csr2.getComponentSet();
         
        // make sure it has the component
        IComponent newbase = newSet.lookupComponent("com.nokia.examples.baseComponent");
        assertNotNull(newbase);
        
        // and again that it doesn't conflict with the old copies
        assertNotSame(newbase, origbase);
        assertNotSame(newbase, base);
        assertNotSame(newbase, currbase);

        // and that it is indeed new
        assertEquals("Foo", newbase.getCategory());
        
    }

    public void testComponentBases() throws Exception {
        clib.loadComponents();

        // check for sanity against component base lookup before set creation
        try {
            ((IComponent)clib.iterator().next()).getComponentBase();
            fail("should not be able to query component base without component set");
        } catch (IllegalStateException e) {
            
        }
        
        // make a component set (req'd for lookup of base component)
        ComponentSetResult csr = provider.queryComponents(null);
        assertNotNull(csr);
        assertNotNull(csr.getComponentSet());
        ComponentSet set = (ComponentSet) csr.getComponentSet();
         
        IComponent base = set.lookupComponent("com.nokia.examples.baseComponent");
        assertNotNull(base);
        IComponent derived = set.lookupComponent("com.nokia.examples.example1");
        assertNotNull(derived);

        assertEquals(base, derived.getComponentBase());
        assertNull(base.getComponentBase());
    }

    public void testComponentSetsAndRefreshing() throws Exception {

        // the initial query should load all the plugins component libraries
        
        // only get the test components
        ComponentSetResult csr = provider.queryComponents(testSdkFilter);
        assertNotNull(csr);
        assertNotNull(csr.getComponentSet());
        ComponentSet set = (ComponentSet) csr.getComponentSet();
        assertEquals(2, set.numComponents());

        // add a component
        createComponent("third");
        
        // should definitely not show up yet
        assertEquals(2, set.numComponents());
        
        // refresh
        provider.refresh();
        
        // should show up here, in the lower levels
        assertEquals(3, clib.getComponents(null).length);

        // but not here yet, either!
        assertEquals(2, set.numComponents());

        // now re-query
        csr = provider.queryComponents(testSdkFilter);
        set = (ComponentSet) csr.getComponentSet();

        // now we should see it
        assertEquals(3, set.numComponents());
    }

    public void testBrokenComponents() throws Exception {
        // only get the test components
        ComponentSetResult csr = provider.queryComponents(testSdkFilter);
        assertNotNull(csr);
        assertNotNull(csr.getComponentSet());
        ComponentSet set = (ComponentSet) csr.getComponentSet();
        assertEquals(2, set.numComponents());

        ////////
        
        // now change the component on disk to a broken one
        overwriteComponent("first", "brokenfirst");

        // refresh
        provider.refresh();

        // now re-query
        csr = provider.queryComponents(testSdkFilter);
        set = (ComponentSet) csr.getComponentSet();
        
        // now we should lose one (but not ALL)
        // actually, both are lost because the first/brokenfirst is 
        // the base of the "second"
        assertEquals(0, set.numComponents());

        ////////
        
        // now change the component on disk to fixed version
        createComponent("first");

        // refresh
        provider.refresh();

        // now re-query
        csr = provider.queryComponents(testSdkFilter);
        set = (ComponentSet) csr.getComponentSet();

        // now we should have all
        assertEquals(2, set.numComponents());

        ////////
        
        // now change the component on disk to another broken one
        overwriteComponent("first", "brokenfirst2");

        // refresh
        provider.refresh();

        // now re-query
        csr = provider.queryComponents(testSdkFilter);
        set = (ComponentSet) csr.getComponentSet();

        // now we should lose one (but not ALL)
        // (actually, both are lost because the first/brokenfirst is 
        // the base of the "second")
        assertEquals(0, set.numComponents());

        ////////
        
        // now change the component on disk to fixed version
        createComponent("first");

        // refresh
        provider.refresh();

        // now re-query
        csr = provider.queryComponents(testSdkFilter);
        set = (ComponentSet) csr.getComponentSet();
        
        // now we should have all
        assertEquals(2, set.numComponents());

        ////////
        
        // now change the component on disk to another broken one
        overwriteComponent("first", "brokenfirst3");

        // refresh
        provider.refresh();

        // now re-query
        csr = provider.queryComponents(testSdkFilter);
        set = (ComponentSet) csr.getComponentSet();

        // now we should lose one (but not ALL)
        // (actually, both are lost because the first/brokenfirst is 
        // the base of the "second")
        assertEquals(0, set.numComponents());

        ////////
        
        // now change the component on disk to fixed version
        createComponent("first");

        // refresh
        provider.refresh();

        // now re-query
        csr = provider.queryComponents(testSdkFilter);
        set = (ComponentSet) csr.getComponentSet();

        // now we should have all
        assertEquals(2, set.numComponents());

    }
}
