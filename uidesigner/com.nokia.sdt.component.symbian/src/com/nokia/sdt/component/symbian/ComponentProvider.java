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

package com.nokia.sdt.component.symbian;

import com.nokia.sdt.component.*;
import com.nokia.sdt.component.event.IEventDescriptorProvider;
import com.nokia.sdt.component.symbian.attributes.AttributeAdapterFactory;
import com.nokia.sdt.component.symbian.builder.UserComponentProjectNature;
import com.nokia.sdt.component.symbian.designerimages.DesignerImageAdapterFactory;
import com.nokia.sdt.component.symbian.documentation.DocumentationAdapterFactory;
import com.nokia.sdt.component.symbian.documentation.InfoItemsAdapterFactory;
import com.nokia.sdt.component.symbian.events.EventDescriptorProvider;
import com.nokia.sdt.component.symbian.implementations.ComponentImplementationsAdapterFactory;
import com.nokia.sdt.component.symbian.properties.PropertySourceProvider;
import com.nokia.sdt.component.symbian.properties.TypeDescriptors;
import com.nokia.sdt.component.symbian.scripting.impl.ScriptAdapterFactory;
import com.nokia.sdt.component.symbian.sourcegen.SourceGenAdapterFactory;
import com.nokia.sdt.component.symbian.sourcemapping.SourceMappingAdapterFactory;
import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.ui.views.properties.IPropertySourceProvider;
import org.osgi.framework.Version;

import java.text.MessageFormat;
import java.util.*;


/**
 * 
 */
public class ComponentProvider implements IComponentProvider, IRegistryChangeListener {
	
    public static final String EXT_ID = "componentLibrary"; //$NON-NLS-1$
	 
    public static final String PROVIDER_ID = "com.nokia.sdt.component.symbian.Symbian-Provider"; //$NON-NLS-1$

    static final String PROPERTY_PREFIX = "com.nokia.sdt.component.symbian"; //$NON-NLS-1$
    static public final String VENDOR_PROPERTY = PROPERTY_PREFIX + ".vendor";
    static public final String VERSION_PROPERTY = PROPERTY_PREFIX + ".version";
    
	static final String COMPONENT_CATEGORY_PREFIX = "category.";
	
	// Turn this on to diagnose components that use non-localized categories.
	// But do not check in or ship with this turn on.
	static final boolean LOG_MISSING_CATEGORIES = false;
	
	/** map of unique plugin name -> IComponentLibrary (plugin component libraries) */
	protected Map pluginToLibraryMap = new HashMap();
    /** map of directory -> IComponentLibrary (user component libraries) */
    protected Map directoryToLibraryMap = new HashMap();
    /** list of all known component libraries (cached) */
    private List componentLibraries = new ArrayList();
    private boolean componentLibrariesDirty = true;

    /** delay checking the plugins until needed */
    private boolean performedStartupComponentScan = false;
    
    /** map of adapter factories (Class -> IAdapterFactory) */
	private Map	factories = new HashMap();
    /** map of types (String -> ITypeDescriptor) */ 
	private Map typeDescriptors = new HashMap();
	
    /// projects which are user component projects.
    private Set<IProject> userComponentProjects;

	public ComponentProvider() {
		registerDefaultAdapterFactories();
		TypeDescriptors.registerDefaultTypeHandlers(this);
		userComponentProjects = new HashSet<IProject>(1);
	}
	
    /** 
     * Update the cached list of component libraries from the
     * plugin and user component directory maps.
     */
	protected void updateCaches() {
        synchronized (componentLibraries) {
            componentLibraries.clear();
    	    for (Iterator iter = pluginToLibraryMap.values().iterator(); iter.hasNext();) {
                IComponentLibrary lib = (IComponentLibrary) iter.next();
                componentLibraries.add(lib);
            }
            for (Iterator iter = directoryToLibraryMap.values().iterator(); iter.hasNext();) {
                IComponentLibrary lib = (IComponentLibrary) iter.next();
                componentLibraries.add(lib);
            }
            Collections.sort(componentLibraries, new Comparator<IComponentLibrary>() {
				public int compare(IComponentLibrary o1, IComponentLibrary o2) {
					if (o1 instanceof ComponentLibrary)
						return -1;
					else if (o2 instanceof ComponentLibrary)
						return 1;
					return 0;
				}
            });
        }
        componentLibrariesDirty = false;
    }
	
	private void scanForPluginsAndProjects() {
        if (!Platform.isRunning())
            return;
        
		userComponentProjects.clear();

        // load any extensions in the plugin registry which are not yet loaded
        loadNewExtensions();
        
        // look for projects with the UI Designer user component project nature
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IProject projects[] = root.getProjects();
        
        for (int i = 0; i < projects.length; i++) {
            IProject project = projects[i];
            Exception caught = null;
            try {
                if (project.isOpen() && project.hasNature(UserComponentProjectNature.NATURE_ID)) {
                	addUserComponentProject(project);
                }
            } catch (CoreException e) {
                caught = e;
            }
            if (caught != null)
                ComponentSystemPlugin.log(caught, 
                        MessageFormat.format(Messages.getString("ComponentProvider.NotRegisteringUserComponentProject"), //$NON-NLS-1$
                                new Object[] { project.getName() }));
        }
    }

    /**
     * Called from test to avoid loading components from plugins.
     *
     */
    public void inhibitPluginScan() {
    	performedStartupComponentScan = true;
    }
    
    protected List getComponentLibraries() {
        if (!performedStartupComponentScan) {
            scanForPluginsAndProjects();
            performedStartupComponentScan = true;
        }
        if (componentLibrariesDirty) {
            updateCaches();
        }
        return componentLibraries;
    }
   

    public String getId() {
		return PROVIDER_ID;
	}
	
    /**
     * Select a subset of all the components from all the component
     * libraries.  This will load libraries if the filter doesn't implement
     * IComponentLibraryFilter or if the filter accepts it.
     * @param libfilter filter on component libraries (or null)
     * @param filter filter on individual components (or null)
     * @return map of String id -> IComponent
     */
    private Map selectComponents(IComponentFilter filter) {
        
        Map map = new HashMap();
        synchronized (getComponentLibraries()) {
            for (Iterator iter = getComponentLibraries().iterator(); iter.hasNext();) {
                IComponentLibrary lib = (IComponentLibrary) iter.next();

                boolean useLibrary = true;
                if (filter instanceof IComponentLibraryFilter) {
                	useLibrary = ((IComponentLibraryFilter) filter).accept(lib);
                }
            	if (useLibrary) {
            		ensureLibraryLoaded(lib);
	                if (lib.isLoaded()) {
	                    synchronized (lib) {
	                        for (Iterator cs = lib.iterator(); cs.hasNext();) {
	                            Component c = (Component) cs.next();
	                            if (filter == null || filter.accept(c)) {
	                                Component curr = (Component) map.get(c.getId());
	                                if (curr == null || c.isLaterComponentVersion(curr))
	                                    map.put(c.getId(), c);
	                            }
	                        }
	                    }
	                }
            	}
            }
        }
        return map;
    }
    
    /**
     * Query the component set, applying the given filter to 
     * select specific components.
     * <p>
     * This returns a set which has <i>shallow copies</i> of
     * IComponent objects.  We need this because an IComponent
     * has a backreference to the IComponentSet (thus, each
     * time a component appears in a set, it must return a
     * different value).
     * 
     * @see com.nokia.sdt.component.IComponentProvider#queryComponents(com.nokia.sdt.component.IComponentFilter)
     */
    public ComponentSetResult queryComponents(IComponentFilter filter) {
		ComponentSetResult result = null;
		
		ComponentSet cs = new ComponentSet(this);
        
		Map map = selectComponents(filter);
		for (Iterator iter = map.values().iterator(); iter.hasNext();) {
			Component element = (Component) iter.next();
			cs.addComponent((Component)element.clone());
		}
			
		for (Iterator iter = typeDescriptors.entrySet().iterator(); iter.hasNext();) {
			Map.Entry entry = (Map.Entry) iter.next();
			ITypeDescriptor td = (ITypeDescriptor)entry.getValue();
			cs.addTypeDescriptor((String)entry.getKey(), (ITypeDescriptor) td.clone());
		}
		
		// To add generalized support for IComponentFilter and persistence
		// properties we'd also need to update the IComponentFilter interface,
		// add a factory system for instantiating filters, etc.
		if (filter instanceof ComponentSDKSelector) {
			ComponentSDKSelector selector = (ComponentSDKSelector) filter;
			cs.setPersistenceProperties(selector.getPersistenceProperties());
		}
		
		IStatus status = cs.validate();
		result = new ComponentSetResult(cs, status);
		
		return result;
	}

	private void ensureLibraryLoaded(IComponentLibrary lib) {
		try {
		    lib.loadComponents();
		} catch (ComponentSystemException e) {
		    // report and continue trying to load other libraries
		    ComponentSystemPlugin.log(e);
		}
	}

    /**
     * Get an iterator over the component libraries.
     * 
     * The iterator refers to an unmodifiable collection, so 
     * synchronization is not required.
     */
    public Iterator iterator() {
        return Collections.unmodifiableCollection(getComponentLibraries()).iterator();
    }

    
	/**
	 * Return the component libraries in the component system.
	 * 
	 * @param libfilter
	 *            filter component library (can be null to accept all)
	 * @return list of component libraries
	 */
	public IComponentLibrary[] getComponentLibraries(
	        IComponentLibraryFilter libfilter) {
	
	    List clibs = new ArrayList();
	    synchronized (getComponentLibraries()) {
	        for (Iterator iter = getComponentLibraries().iterator(); iter.hasNext();) {
	            IComponentLibrary lib = (IComponentLibrary) iter.next();
	            if (libfilter == null || libfilter.accept(lib)) {
	                clibs.add(lib);
	            }
	        }
	    }
	    return (IComponentLibrary[]) clibs.toArray(new IComponentLibrary[clibs.size()]);
	}
 
/*
    public void startup() {
    	IExtensionRegistry reg = Platform.getExtensionRegistry();
    	reg.addRegistryChangeListener(this);
    }
    
    public void shutdown() {
    	unloadComponentLibraries();
    	IExtensionRegistry reg = Platform.getExtensionRegistry();
    	reg.removeRegistryChangeListener(this);
    }
*/
    
    /** Scan the extension points and load all the new component libraries.
     * @return true if any extensions registered
     */
    private synchronized void loadNewExtensions() {
    	// Get implementors of the componentLibrary extension point
    	IExtensionRegistry er = Platform.getExtensionRegistry();
    	IExtensionPoint ep = er.getExtensionPoint(
    			ComponentSystemPlugin.PLUGIN_ID, EXT_ID);
    	
    	// Iterate each, loading new extensions
    	IExtension[] extensions = ep.getExtensions();
    	for (int i = 0; i < extensions.length; i++) {
    		String uniqueID = extensions[i].getUniqueIdentifier();
    		IComponentLibrary componentLibrary = (IComponentLibrary) pluginToLibraryMap.get(uniqueID);
    		if (componentLibrary == null) {
    			loadExtension(extensions[i]);
    		}
    	}
    }

    /**
     * Unload all component libraries
     */
    protected void unloadComponentLibraries() {
    	pluginToLibraryMap.clear();
        directoryToLibraryMap.clear();
        getComponentLibraries().clear();
    }

    /**
     * Add a user component library to the provider.
     * @param dir root directory of component project or directory
     * @return the IComponentLibrary
     * @throw ComponentSystemException if library is already registered
     */
    public synchronized IComponentLibrary addUserComponentLibrary(String dir) throws ComponentSystemException {
        IComponentLibrary lib = (IComponentLibrary) directoryToLibraryMap.get(dir);
        if (lib == null) {
            try {
                lib = new ComponentLibrary(dir);
                lib.setProvider(this);
            } catch (CoreException e) {
                 throw new ComponentSystemException(e.getStatus());
            }
            directoryToLibraryMap.put(dir, lib);
            componentLibrariesDirty = true;
    
        }
        return lib;
    }

    /**
     * Remove a user component library from the provider.
     * @param dir root directory of component project or directory
     */
    public synchronized void removeUserComponentLibrary(String dir) throws ComponentSystemException {
        IComponentLibrary lib = (IComponentLibrary) directoryToLibraryMap.get(dir);
        if (lib != null) {
            directoryToLibraryMap.remove(dir);
            componentLibrariesDirty = true;
        }
    }

    /**
     * Refresh a user component library.
     * @param dir root directory of component project or directory
     */
    public void refreshUserComponentLibrary(String dir) throws ComponentSystemException {
        IComponentLibrary lib = (IComponentLibrary) directoryToLibraryMap.get(dir);
        if (lib != null) {
            lib.refreshComponents();
        } else {
            throw new ComponentSystemException(Logging.newStatus(
                    ComponentSystemPlugin.getDefault(), IStatus.ERROR,
                    MessageFormat.format(Messages.getString("ComponentProvider.CannotRefreshUnregistered"), new Object[] { dir }))); //$NON-NLS-1$
        }
    }
    
    /**
     * Look up a user component library.
     * @param dir directory providing the root of the library
     * @return IComponentLibrary, or null 
     */
    public synchronized IComponentLibrary findUserComponentLibrary(String dir) {
        for (Iterator iter = directoryToLibraryMap.entrySet().iterator(); iter.hasNext();) {
            Map.Entry entry = (Map.Entry) iter.next();
            if (entry.getKey().equals(dir))
                return (IComponentLibrary) entry.getValue();
        }
        return null;
    }

    /**
     * Look up a user component library's directory.
     * @param lib the component library
     * @return directory, or null 
     */
    public synchronized String findUserComponentLibrary(IComponentLibrary lib) {
        for (Iterator iter = directoryToLibraryMap.entrySet().iterator(); iter.hasNext();) {
            Map.Entry entry = (Map.Entry) iter.next();
            if (entry.getValue().equals(lib))
                return (String) entry.getKey();
        }
        return null;
    }

    /**
     * Find the component library for the given project, if registered.
     * @param project
     * @return
     */
    public IComponentLibrary findUserComponentProject(IProject project) {
    	IComponentLibrary result = null;
    	IPath location = project.getLocation();
    	if (location != null) {
    		result = findUserComponentLibrary(location.toOSString());
    	}
    	return result;
    }

    /**
     * Add a user component project.  
     * @param project
     * @return true if project added (false if already registered)
     */
    public boolean addUserComponentProject(IProject project) {
		userComponentProjects.add(project);
		String location = project.getLocation().toOSString();
	    if (findUserComponentLibrary(location) == null) {
	    	try {
				addUserComponentLibrary(location);
				return true;
			} catch (ComponentSystemException e) {
				ComponentSystemPlugin.log(e);
			}
	    }
		return false;
	}

    /**
     * Remove a user component project.
     * @param project
     * @return true if project removed (false if not present)
     */
	public boolean removeUserComponentProject(IProject project) {
		userComponentProjects.remove(project);
		String location = project.getLocation().toOSString();
	    if (findUserComponentLibrary(location) != null) {
	        try {
				removeUserComponentLibrary(location);
				return true;
			} catch (ComponentSystemException e) {
				ComponentSystemPlugin.log(e);
			}
	    }
		return false;
	}

	/**
     * Refresh all the component libraries
     * @see com.nokia.sdt.component.IComponentProvider#refresh()
     */
    public synchronized void refresh() {
        ComponentSystemPlugin.infoIf("debug", "refreshing component libraries"); //$NON-NLS-1$ //$NON-NLS-2$

        scanForPluginsAndProjects();

        // now refresh all the components
        synchronized (getComponentLibraries()) {
            for (Iterator iter = getComponentLibraries().iterator(); iter.hasNext();) {
                IComponentLibrary lib = (IComponentLibrary) iter.next();
                try {
                    lib.refreshComponents();
                } catch (ComponentSystemException e) {
                    ComponentSystemPlugin.log(e);
                }
            }
        }
    }

    /********************* IRegistryChangeListener */
    
    public void registryChanged(IRegistryChangeEvent event) {
    	ComponentSystemPlugin.info("=========== registryChanged"); //$NON-NLS-1$
    	IExtensionDelta[] deltas = event.getExtensionDeltas(
    			ComponentSystemPlugin.PLUGIN_ID, EXT_ID);
    	for (int i = 0; i < deltas.length; i++) {
    		IExtension ext = deltas[i].getExtension();
    		if (deltas[i].getKind() == IExtensionDelta.ADDED) {
    			ComponentSystemPlugin.info("=========== added " + ext.getUniqueIdentifier() ); //$NON-NLS-1$
    			loadExtension(ext);
    		} else if (deltas[i].getKind() == IExtensionDelta.REMOVED) {
    			ComponentSystemPlugin.info("=========== removed " + ext.getUniqueIdentifier() ); //$NON-NLS-1$
    			unloadExtension(ext);
    		}
    	}
    }
    
    /**
     * Load a new component library extension into the provider.
     * @param extension
     */
    private void loadExtension(IExtension extension) {
    	ComponentSystemPlugin.infoIf("debug", "loading extension " + extension.getUniqueIdentifier()); //$NON-NLS-1$ //$NON-NLS-2$
    	IConfigurationElement[] ces = extension.getConfigurationElements();
    	for (int j = 0; j < ces.length; j++) {
    		try {
    			IComponentLibrary lib;
    			
    			// component libraries are immutable, so don't modify
    			// an existing version
    			lib = ComponentLibraryExtensionFactory.create(extension, ces[j], this);
    			synchronized (pluginToLibraryMap) {
    				pluginToLibraryMap.put(extension.getUniqueIdentifier(), lib);
                    componentLibrariesDirty = true;
    			}
    		} catch (CoreException e) {
    			ComponentSystemPlugin.log(e);
    			ComponentSystemPlugin.info("could not load component " + ces[j]); //$NON-NLS-1$
    		}
    	}
    }
    
    /**
     * Unload a component library extension from the provider.
     * @param extension
     */
    private void unloadExtension(IExtension extension) {
    	ComponentSystemPlugin.infoIf("debug", "unloading extension " + extension.getUniqueIdentifier()); //$NON-NLS-1$ //$NON-NLS-2$
    	IConfigurationElement[] ces = extension.getConfigurationElements();
    	synchronized (pluginToLibraryMap) {
    		for (int j = 0; j < ces.length; j++) {
    			pluginToLibraryMap.remove(extension.getUniqueIdentifier());
                componentLibrariesDirty = true;
    		}
    	}
    }

    ////////////////////////////////////////////////////////////
    
    private void registerDefaultAdapterFactories() {
        
        // This adapter factory supports IPropertyDescriptorProvider
        registerAdapterFactory( new IAdapterFactory() {
            final Class[] adapterList = new Class[]{
            		IPropertySourceProvider.class,
            		IEventDescriptorProvider.class};
            public Object getAdapter(Object adaptableObject, Class adapterType) {
                Object adapter = null;
                ComponentProvider componentProvider = ComponentProvider.this;
                if (adaptableObject instanceof Component) {
                    Component component = (Component) adaptableObject;
                    if (adapterType.equals(IPropertySourceProvider.class)) {
                        adapter = new PropertySourceProvider(component, componentProvider);
                    }
                    else if (adapterType.equals(IEventDescriptorProvider.class)) {
                    	adapter = new EventDescriptorProvider(component);
                    }
                }
                return adapter;
            }

            public Class[] getAdapterList() {
                return adapterList;
            }
            
        }); 
        
        EStructuralFeature attributesFeature = 
            ComponentPackage.eINSTANCE.getComponentType().getEStructuralFeature(
                ComponentPackage.COMPONENT_TYPE__ATTRIBUTES);
        registerAdapterFactory( new AttributeAdapterFactory(ComponentSystemPlugin.getDefault(), 
                attributesFeature));

        EStructuralFeature designerImagesFeature = 
            ComponentPackage.eINSTANCE.getComponentType().getEStructuralFeature(
                ComponentPackage.COMPONENT_TYPE__DESIGNER_IMAGES);
        registerAdapterFactory( new DesignerImageAdapterFactory(ComponentSystemPlugin.getDefault(), 
                designerImagesFeature));

        EStructuralFeature documentationFeature = 
            ComponentPackage.eINSTANCE.getComponentType().getEStructuralFeature(
                ComponentPackage.COMPONENT_TYPE__DOCUMENTATION);
        registerAdapterFactory( new DocumentationAdapterFactory(ComponentSystemPlugin.getDefault(), 
                documentationFeature));

        EStructuralFeature symbianFeature = 
            ComponentPackage.eINSTANCE.getComponentType().getEStructuralFeature(
                ComponentPackage.COMPONENT_TYPE__SYMBIAN);
        registerAdapterFactory( new InfoItemsAdapterFactory(ComponentSystemPlugin.getDefault(), 
                symbianFeature));

        EStructuralFeature implementationsFeature = 
            ComponentPackage.eINSTANCE.getComponentType().getEStructuralFeature(
                ComponentPackage.COMPONENT_TYPE__IMPLEMENTATIONS);
        registerAdapterFactory( new ComponentImplementationsAdapterFactory(ComponentSystemPlugin.getDefault(), 
        		implementationsFeature));
        
        EStructuralFeature sourcegenFeature = 
            ComponentPackage.eINSTANCE.getComponentType().getEStructuralFeature(
                ComponentPackage.COMPONENT_TYPE__SOURCE_GEN);
        registerAdapterFactory( new SourceGenAdapterFactory(ComponentSystemPlugin.getDefault(), 
                sourcegenFeature));

        EStructuralFeature sourcemappingFeature = 
            ComponentPackage.eINSTANCE.getComponentType().getEStructuralFeature(
                ComponentPackage.COMPONENT_TYPE__SOURCE_MAPPING);
        registerAdapterFactory( new SourceMappingAdapterFactory(ComponentSystemPlugin.getDefault(), 
                sourcemappingFeature));
        
        registerAdapterFactory( new ScriptAdapterFactory(ComponentSystemPlugin.getDefault()));
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.component.symbian.IComponentProvider#registerAdapterFactory(org.eclipse.core.runtime.IAdapterFactory)
     */
    public void registerAdapterFactory(IAdapterFactory factory) {
        
        // It's possible that more than one registered factory
        // will provide the same adapter type. 
        Class[] types = factory.getAdapterList();
        if (types != null) {
            for (Class type : types) {
            	Object object = factories.get(type);
            	if (object == null)
            		object = factory; // optimize for single factory per type
            	else if (object instanceof IAdapterFactory) {
            		// if there's already a factory stored, store a list
            		IAdapterFactory exstingFactory = (IAdapterFactory) object;
            		List<IAdapterFactory> factories = new ArrayList<IAdapterFactory>();
            		factories.add(exstingFactory);
            		factories.add(factory);
            		object = factories;
            	}
            	else if (object instanceof List) {
            		// add to existing list
            		((List<IAdapterFactory>) object).add(factory);
            	}
            	else {
            		Check.checkContract(false); // unknown object stored!!
            	}
                factories.put(type, object);
            }
        }
    }

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.IComponentProvider#adaptComponent(com.nokia.sdt.component.IComponent, java.lang.Class)
	 */
	public Object adaptComponent(IComponent component, Class adapterType) {
		Object object = factories.get(adapterType);
		if (object instanceof IAdapterFactory)
			return ((IAdapterFactory) object).getAdapter(component, adapterType);
		else if (object instanceof List) {
			List<IAdapterFactory> factories = (List<IAdapterFactory>) object;
			for (IAdapterFactory factory : factories) {
				Object adapter = factory.getAdapter(component, adapterType);
				if (adapter != null)
					return adapter;
			}
		}
		
		return null;
	}

    public void registerTypeDescriptor(String typeName, ITypeDescriptor handler) {
    	typeDescriptors.put(typeName, handler);
    }
    
    public ITypeDescriptor lookupTypeDescriptor(String typeID) {
    	return (ITypeDescriptor) typeDescriptors.get(typeID);
    }
    
    public String getCategoryText(String categoryKey) {

    	String result = categoryKey;
		String key = COMPONENT_CATEGORY_PREFIX + categoryKey;
		if (Messages.hasString(key)) {
			result = Messages.getString(key);
		} else if (LOG_MISSING_CATEGORIES) {
			// intended for debug builds to catch missing strings.
			// All our categories should be localized, but user categories
			// do not have to be.
			IStatus status = Logging.newSimpleStatus(0, IStatus.ERROR, 
					"missing localized string for component category: "+categoryKey, null);//$NON-NLS-1$
			ComponentSystemPlugin.log(status);
		}
		return result;
    }

	public ComponentSetResult reQueryComponents(Map queryProperties) {
		Check.checkArg(queryProperties);
		ComponentSetResult result = null;
		Object vendor = queryProperties.get(VENDOR_PROPERTY);
		Object version = queryProperties.get(VERSION_PROPERTY);
		if (vendor != null && version != null) {
			Version v = Version.parseVersion(version.toString());
			ComponentSDKSelector selector = new ComponentSDKSelector(vendor.toString(), v);
			result = queryComponents(selector);
		}
		return result;
	}

	public String getPropertyPrefix() {
		return PROPERTY_PREFIX;
	}

}


