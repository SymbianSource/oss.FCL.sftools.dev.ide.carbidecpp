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

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.ProjectUtils;
import com.nokia.sdt.component.*;
import com.nokia.sdt.component.symbian.*;
import com.nokia.sdt.component.symbian.builder.UserComponentProjectNature;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.osgi.framework.Bundle;

import java.io.File;
import java.net.URL;
import java.util.Iterator;

import junit.framework.TestCase;

/**
 * Test the component loader
 * 
 * 
 * 
 */
public class LoaderTest extends TestCase {
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
        IComponentLibrary clib;
        try {
            clib = new ComponentLibrary(
                    componentsPath.toOSString());
        } catch (CoreException e) {
             throw new ComponentSystemException(e.getStatus());
        }
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
    
    public void testSimple() throws Exception {
        assertEquals(componentsPath.toOSString(), clib.getId());
    }

    public void testBadIterate() throws Exception {
        try {
            // iterate without .loadComponents()
            Iterator iter = clib.iterator();
            fail("should not be able to iterate without .loadComponents()");
            Check.checkArg(iter);   // warning suppression; not reached
        } catch (AssertionError e) {
        }
    }

    public void testIterate() throws Exception {
        // load components
        clib.loadComponents();
        
        // verify non-null components
        int count = countComponents(clib);

        // verify # components is constant
        int count2 = countComponents(clib);
        
        assertEquals(count, count2);
    }

    /** Test that we can explicitly call IComponentLibrary.refreshComponents()
     * and notice changes.
     * 
     * @throws Exception
     */
    public void testRefresh1() throws Exception {
        clib.loadComponents();
        
        // count once
        int count = countComponents(clib);
        assertEquals(2, count);
        
        // Add a new component. 
        createComponent("third");
        
        // New component should NOT show up until we explicitly refresh.
        count = countComponents(clib);
        assertEquals(2, count);

        // now refresh
        clib.refreshComponents();

        // and expect to find the component
        count = countComponents(clib);
        assertEquals(3, count);
        
        // now delete...
        removeComponent("third");

        // verify not changed...
        count = countComponents(clib);
        assertEquals(3, count);

        // refresh...
        clib.refreshComponents();
        
        // and count
        count = countComponents(clib);
        assertEquals(2, count);

    }
    
    /** 
     * @deprecated -- only explicit commands refresh component libraries now
     * 
     * Test a user component project which has components
     * being edited in real time.  
     * 
     * This relies on the "user component project" nature 
     * detecting changes to resources belonging to components
     * in an open project. 
     * 
     * @throws Exception
     */
    public void doNotTestRefreshViaAutobuild() throws Exception {
        final String userPlugin = "com.mycompany.components";
        
        // delete project in case it's still there
        try {
//            ProjectUtils.closeAndDeleteProject(PluginTest.PLUGIN_ID + "$testRefresh2");
            ProjectUtils.closeAndDeleteProject(userPlugin);
        } catch (CoreException e) {
            // ignore
        }
        
        // create a project
//        IProject project = ProjectUtils.createAndOpenProject(PluginTest.PLUGIN_ID + "$testRefresh2");
        final IProject project = ProjectUtils.createAndOpenProject(userPlugin);

        // copy contents
        File pluginDir = new File(dataDir, userPlugin);
        File projectDir = new File(project.getLocation().toOSString());
        FileUtils.copyTreeNoParent(
                pluginDir,
                projectDir,
                null);
        
        // notice changes
        project.refreshLocal(IResource.DEPTH_INFINITE, null);
        
        // turn on UI Designer project nature
        UserComponentProjectNature.setNature(project, true);
        
        IProgressMonitor progressMonitor = new NullProgressMonitor();
        project.build(IncrementalProjectBuilder.FULL_BUILD, progressMonitor);

        // add library to working set
		//ComponentProvider provider = (ComponentProvider) ComponentSystem.getComponentSystem().getProvider(ComponentProvider.PROVIDER_ID);
        //provider.addComponentLibrary(project.getLocation().toOSString());
            
        // make sure the plugin was registered 
        IComponentLibrary clib = null;
        for (Iterator iter = provider.iterator(); iter.hasNext();) {
            IComponentLibrary c = (IComponentLibrary) iter.next();
            if (c.getId().equals(userPlugin + ".mycomponentlibrary")) {
                clib = c;
                break;
            }
        }  
        assertNotNull(clib);
        
        // be sure its components are registered
        final File componentsDir = new File(projectDir, "components");
       
        int count = 0;
        for (Iterator iter = clib.iterator(); iter.hasNext();) {
            Component c = (Component) iter.next();
            count++;
            assertEquals(c.getFriendlyName(), "myveryfirst");
        }
        assertEquals(1, count);
        
        // add a new component
        FileUtils.copyTree(new File(new File(pluginDir, "newcomponents"), "mysecond.component"),
                componentsDir, null);

        // notice changes, which should prompt an autobuild
        // which makes the user component project plugin
        // aske the component system to reload the plugin 
        project.refreshLocal(IResource.DEPTH_INFINITE, null);

        if (!ResourcesPlugin.getWorkspace().isAutoBuilding())
        {
            // build it manually
            project.build(IncrementalProjectBuilder.FULL_BUILD, null);
        }
        
        class Verifier extends WorkspaceJob {
            private IComponentLibrary cLib;
            public Verifier(IProject proj, IComponentLibrary clib) {
                super("test");
                this.cLib = clib;
            }
            public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
                System.out.println("checking workspace");
                
                // be sure the changes are noted
                int count = 0;
                for (Iterator iter = cLib.iterator(); iter.hasNext();) {
                    Component c = (Component) iter.next();
                    count++;
                    if (!c.getFriendlyName().equals("myveryfirst") ||
                    	!c.getFriendlyName().equals("mysecond"))
                        fail("unknown component " + c.getId());
                }
                assertEquals(2, count);
                return null;
            }
        }

        // now run this job asychronously so the resource change
        // events can propagate to the component system plugin
        new Verifier(project, clib).schedule();
    }

    public void testComponentQualifiedNames() throws Exception {
        clib.loadComponents();
        
        Iterator iter;
        iter = clib.iterator();

        while (iter.hasNext()) {
            IComponent c = (IComponent) iter.next();
            assertNotNull(c);
            assertNotNull(c.getId());
            if (!c.getId().equals("com.nokia.examples.baseComponent") 
                    && !c.getId().equals("com.nokia.examples.example1"))
                fail("unexpected component name:" + c.getId());
        }
    }

    public void testComponentBasics() throws Exception {
        // create...
        clib.loadComponents();
        
        Iterator iter;
        iter = clib.iterator();

        while (iter.hasNext()) {
            IComponent c = (IComponent) iter.next();
            assertNotNull(c);
            assertEquals(provider, c.getProvider());
            assertEquals(bundle, c.getBundle());
            if (c.getId().equals("com.nokia.examples.baseComponent")) {
                assertNull(c.getCategory());
            } else {
                assertEquals("Spelunking", c.getCategory());
            }
        }
    }

}
