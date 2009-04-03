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
package com.nokia.sdt.testsupport;

import com.nokia.sdt.component.ComponentSystem;
import com.nokia.sdt.component.IComponentProvider;
import com.nokia.sdt.component.symbian.*;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.LoadResult;
import com.nokia.sdt.datamodel.util.NamePropertySupport;
import com.nokia.sdt.emf.dm.INode;
import com.nokia.sdt.emf.dm.StringValue;
import com.nokia.sdt.emf.dm.impl.INodeImpl;
import com.nokia.sdt.emf.dm.xml.NXDResourceFactory;
import com.nokia.sdt.sourcegen.ISourceGenProvider;
import com.nokia.sdt.symbian.dm.DesignerDataModelProvider;
import com.nokia.sdt.symbian.workspace.impl.ProjectContextProvider;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.sdt.workspace.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

/**
 * These routines are useful for unit tests.
 * Provides methods to easily prime the component system and
 * get the data model with custom components.  These are not 
 * meant for normal use of installed component plugins, but
 * for test cases which need/want a small, limited set of
 * components to play with.
 * 
 * 
 *
 */
public class TestDataModelHelpers {

	private static Map<File, ComponentProvider> cachedUserComponentProviders = new HashMap<File, ComponentProvider>();
	private static ComponentProvider defaultProvider;;

	/**
	 * Creates a new data model at the given location
	 * @param saveLoc desired file path.  Any existing file is overwritten
	 * @param encoding optional file encoding, can be null for UTF-8
	 */
	public static IDesignerDataModel createTestModel(File saveLoc, String encoding) throws Exception {
		DesignerDataModelProvider provider = new DesignerDataModelProvider();
		URI uri = URI.createFileURI(saveLoc.toString());
		IDesignerDataModel result = provider.create(uri, encoding);
		return result;
	}
	
	/**
	 * Creates a temporary data model, i.e. one that is not
	 * saved to disk
	 * @throws IOException 
	 * @throws CoreException 
	 */
	public static IDesignerDataModel createTemporaryModel() throws CoreException, IOException {
		DesignerDataModelProvider provider = new DesignerDataModelProvider();
		return provider.createTemporary();
	}
	
	public static IDesignerDataModel loadDataModel(String file) throws Exception {
        File dmFile = FileHelpers.projectRelativeFile(file);
        return loadDataModel(dmFile);
 	}
	
	public static IDesignerDataModel loadDataModel(File file) throws Exception {
        
        // refresh the workspace in case the project was copied in with
        // java.io.File
        if (Platform.isRunning())
            ResourcesPlugin.getWorkspace().getRoot().refreshLocal(
                IResource.DEPTH_INFINITE, null);

        // register the NXD loader for .nxd and .uidesign
        Map extensionToFactoryMap = org.eclipse.emf.ecore.resource.Resource.Factory.Registry.INSTANCE
                .getExtensionToFactoryMap();

        extensionToFactoryMap.put("nxd", new NXDResourceFactory());
        extensionToFactoryMap.put("uidesign", new NXDResourceFactory());
        
        // load the data model
        IDesignerDataModel dm;
        URI uri = URI.createFileURI(file.toString());

        LoadResult lr = new DesignerDataModelProvider().load(uri, null, null);
        TestCase.assertNotNull(lr);
        TestCase.assertNotNull(lr.getModel());
        dm = lr.getModel();

        return dm;
	}

    /**
     * Load a data model and set its components from the given provider
     * Note: use TestHelpers.setPlugin(Plugin) first!
     * @param project the project holding the model, or null.
     * If not null, we copy the model into the project if it's not already there,
     * so the workspace and project contexts are valid.
     * @param dmFile relative file (to the test project) to the model
     * @param dmSpec model specifier
     * @param provider the component provider
     */
    public static IDesignerDataModel loadDataModelWithTestComponents(File dmFile,
    		IDesignerDataModelSpecifier dmSpec,
    		ComponentProvider provider,
    		ISourceGenProvider sgProvider) throws Exception {

        // refresh the workspace in case the project was copied in with
        // java.io.File
        if (Platform.isRunning()) 
            ResourcesPlugin.getWorkspace().getRoot().refreshLocal(
                    IResource.DEPTH_INFINITE, null);

        // register the NXD loader 
        org.eclipse.emf.ecore.resource.Resource.Factory.
            Registry.INSTANCE.getExtensionToFactoryMap().put("nxd", new NXDResourceFactory());

        // load the data model
        LoadResult lr;
        URI uri = URI.createFileURI(dmFile.toString());
            
        lr = new DesignerDataModelProvider().load(uri, dmSpec, sgProvider);

        TestCase.assertNotNull(lr);
        if (lr.getModel() == null) {
            TestCase.fail(lr.getStatus().getMessage());
        }
        
        IDesignerDataModel dm;
        dm = lr.getModel();
        
        // load all the components into the DM's component set,
        // not just the hardcoded Series 60 ones
        ComponentSet cs = ComponentHelpers.queryAllComponents(provider);
        dm.setComponentSet(cs);
    
        return dm;
    }

    /**
     * Load a data model and set its components from the given provider
     * Note: use TestHelpers.setPlugin(Plugin) first!
     * @param project the project holding the model, or null.
     * If not null, we copy the model into the project if it's not already there,
     * so the workspace and project contexts are valid.
     * @param dmFile relative file (to the test project) to the model
     * @param dmSpec model specifier
     * @param provider the component provider
     */
    public static IDesignerDataModel loadDataModelWithTestComponents(File dmFile,
    		IDesignerDataModelSpecifier dmSpec,
    		ComponentProvider provider) throws Exception {
    	return loadDataModelWithTestComponents(dmFile, dmSpec, provider, null);
    }
    
    /**
     * Load a data model and set its components from the given provider
     * Note: use TestHelpers.setPlugin(Plugin) first!
     * @param project the project holding the model, or null.
     * If not null, we copy the model into the project if it's not already there,
     * so the workspace and project contexts are valid.
     * @param dmFile relative file (to the test project) to the model
     * @param provider the component provider
     */
    public static IDesignerDataModel loadDataModelWithTestComponents(IProject project, File dmFile, ComponentProvider provider) throws Exception {
    	return loadDataModelWithTestComponents(project, dmFile, provider, null);
    }
    	

    /**
     * Load a data model and set its components from the given provider
     * Note: use TestHelpers.setPlugin(Plugin) first!
     * @param project the project holding the model, or null.
     * If not null, we copy the model into the project if it's not already there,
     * so the workspace and project contexts are valid.
     * @param dmFile relative file (to the test project) to the model
     * @param provider the component provider
     * @param sgProvider the sourcegen provider
     * 
     */
    public static IDesignerDataModel loadDataModelWithTestComponents(IProject project, File dmFile, ComponentProvider provider,
    		ISourceGenProvider sgProvider) throws Exception {

        // refresh the workspace in case the project was copied in with
        // java.io.File
        if (Platform.isRunning()) 
            ResourcesPlugin.getWorkspace().getRoot().refreshLocal(
                    IResource.DEPTH_INFINITE, null);

        // register the NXD loader 
        org.eclipse.emf.ecore.resource.Resource.Factory.
            Registry.INSTANCE.getExtensionToFactoryMap().put("nxd", new NXDResourceFactory());

        // load the data model
        LoadResult lr;
        if (project != null) {
        	ProjectContextProvider.beginProjectCreation(project);

        	// load so the full project context is available
            File projDir = project.getLocation().toFile();
            File prefixDir = projDir;
            while (prefixDir != null) {
                if (prefixDir.equals(dmFile.getParentFile()))
                    break;
                prefixDir = prefixDir.getParentFile();
            }
            if (prefixDir == null) {
                File oldFile = dmFile;
                dmFile = new File(projDir, dmFile.getName());
                FileUtils.copyFile(oldFile, dmFile);
                ResourcesPlugin.getWorkspace().getRoot().refreshLocal(
                            IResource.DEPTH_INFINITE, null);
            }
            
            IProjectContext context = WorkspaceContext.getContext().getContextForProject(project);
            TestCase.assertNotNull(context);
            
            context.refresh(null);
            if (sgProvider != null)
            	context.setSourceGenProvider(sgProvider);
            
            IDesignerDataModelSpecifier dmSpec = context.findSpecifierForPath(new Path(dmFile.getName()));
            TestCase.assertNotNull(dmSpec);
            
            // ensure the data model finds the appropriate provider
            ComponentSystem.getComponentSystem().setProvider(ComponentProvider.PROVIDER_ID, provider);
            
            lr = dmSpec.load();
        } else {
            URI uri = URI.createFileURI(dmFile.toString());
            
            lr = new DesignerDataModelProvider().load(uri, null, sgProvider);
        }

        TestCase.assertNotNull(lr);
        if (lr.getModel() == null) {
            TestCase.fail(lr.getStatus().getMessage());
        }
        
        IDesignerDataModel dm;
        dm = lr.getModel();
        
        // load all the components into the DM's component set,
        // not just the hardcoded Series 60 ones
        ComponentSet cs = ComponentHelpers.queryAllComponents(provider);
        dm.setComponentSet(cs);
    
        return dm;
    }

    /**
     * Load a data model and set its components from the given provider,
     * as if the model came from the given project, where other data models
     * may exist.
     * <p>
     * Note: use TestHelpers.setPlugin(Plugin) first!
     * @param project the project holding the model, or null
     * @param file relative file (to the test project) to the model
     * @param provider the component provider
     */
    public static IDesignerDataModel loadDataModelWithTestComponents(IProject project, String file, ComponentProvider provider, ISourceGenProvider sgProvider) throws Exception {
        File dmFile = FileHelpers.projectRelativeFile(file);
        return loadDataModelWithTestComponents(project, dmFile, provider, sgProvider);
    }
    
    /**
     * Load a data model and set its components from the given provider,
     * as if the model came from the given project, where other data models
     * may exist.
     * <p>
     * Note: use TestHelpers.setPlugin(Plugin) first!
     * @param project the project holding the model, or null
     * @param file relative file (to the test project) to the model
     * @param provider the component provider
     * @param sgProvider the sourcegen provider
     */
    public static IDesignerDataModel loadDataModelWithTestComponents(IProject project, String file, ComponentProvider provider) throws Exception {
    	return loadDataModelWithTestComponents(project, file, provider, null);
    }

    /**
     * Load a data model and set its components from the given provider.
     * <p>
     * This does not set up the workspace context, project context,
     * or the designer data model specifier.  Use the alternate form
     * taking IProject if you want that.
     * <p>
     * Note: use TestHelpers.setPlugin(Plugin) first!
     * @param file relative file (to the test project) to the model
     * @param provider the component provider
     */
    public static IDesignerDataModel loadDataModelWithTestComponents(String file, ComponentProvider provider) throws Exception {
        File dmFile = FileHelpers.projectRelativeFile(file);
        return loadDataModelWithTestComponents(null, dmFile, provider);
    }


    /**
     * Create and register a component provider with the components in the
     * given directory.
     * Note: use TestHelpers.setPlugin(Plugin) first!
     * @param directory project-relative path to component directory
     */
    public static ComponentProvider createProviderForUserComponents(String directory) throws Exception {
        ComponentProvider provider;
        
        provider = new ComponentProvider();
        provider.inhibitPluginScan();
    
        // register the component libraries
        File components = FileHelpers.projectRelativeFile(directory);
        IComponentLibrary lib = provider.addUserComponentLibrary(components.getCanonicalPath());
        if (TestHelpers.plugin != null)
            lib.setBundle(TestHelpers.plugin.getBundle());
        
        return provider;
    }

    /**
     * Find or create a component provider with the components in the
     * given directory.  This keeps a cached component set around.
     * Note: use TestHelpers.setPlugin(Plugin) first!
     * @param directory project-relative path to component directory
     */
    public static ComponentProvider findOrCreateProviderForUserComponents(String directory) throws Exception {

        File components = FileHelpers.projectRelativeFile(directory);
        ComponentProvider provider;
        provider = cachedUserComponentProviders.get(components);
        if (provider == null) {
        	provider = new ComponentProvider();
            // register the component libraries
            IComponentLibrary lib = provider.addUserComponentLibrary(components.getCanonicalPath());
            if (TestHelpers.plugin != null)
                lib.setBundle(TestHelpers.plugin.getBundle());
            cachedUserComponentProviders.put(components, provider);
            provider.inhibitPluginScan();
        }

        return provider;
    }

    public static void initDefaultComponentSet(IDesignerDataModel model) throws CoreException {
    	Check.checkArg(model);
		ComponentSet componentSet = ComponentHelpers.querySDKFilter(
				getDefaultComponentProvider(),
				"com.nokia.series60", "2.0");
		model.setComponentSet(componentSet);
    }

	/**
	 *	Set up the default component provider, which reads all
	 *  the plugins in the workspace. 
	 */
	public static ComponentProvider initDefaultComponentProvider() {
    	if (defaultProvider == null) {
    		defaultProvider = new ComponentProvider();
    	}
    	return defaultProvider;
	}

	public static void assignUniqueName(IDesignerDataModel model, EObject object) {
		Check.checkContract(object instanceof INodeImpl);
		INodeImpl nodeImpl = (INodeImpl) object;
		if (nodeImpl.validateName(nodeImpl.getName()) != null) {
			String name = NamePropertySupport.generateDefaultName(model, nodeImpl.getComponent());
			StringValue sv = new StringValue(StringValue.LITERAL, name);
			nodeImpl.getProperties().set(INode.NAME_PROPERTY, sv, true);
		}
	}

	/** Helper for non-plugin tests to allow creating data models */
	public static void setupResourceFactory() {
		if (!Platform.isRunning()) {
	        // register the NXD loader for .nxd and .uidesign
	        Map extensionToFactoryMap = org.eclipse.emf.ecore.resource.Resource.Factory.Registry.INSTANCE
	                .getExtensionToFactoryMap();

	        extensionToFactoryMap.put("nxd", new NXDResourceFactory());
	        extensionToFactoryMap.put("uidesign", new NXDResourceFactory());
		}
	}

	/** Helper for non-plugin tests to load the test component library */
	public static void setupTestComponents() throws Exception {
		if (!Platform.isRunning()) {
			defaultProvider = createProviderForUserComponents("../com.nokia.sdt.test.componentlibrary");
			ComponentSystem.getComponentSystem().setProvider(ComponentProvider.PROVIDER_ID, defaultProvider);
			
		} else {
			initDefaultComponentProvider();
		}
	}

	/** Get the provider for the components available as plugins in the workspace,
	ordinarily the S60 components and test components.  This will be initialized only
	once per process. */
	public static IComponentProvider getDefaultComponentProvider() {
		return initDefaultComponentProvider();
	}
}
