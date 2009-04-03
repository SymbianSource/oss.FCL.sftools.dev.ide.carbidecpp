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
package com.nokia.sdt.component.symbian;

import com.nokia.sdt.component.*;
import com.nokia.sdt.component.symbian.builder.UserComponentProjectNature;
import com.nokia.sdt.component.symbian.implementations.ImplementationsTypesRegistry;
import com.nokia.sdt.displaymodel.GlobalCache;
import com.nokia.sdt.editor.EditorServices;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.ui.UITaskUtils;
import com.nokia.sdt.workspace.WorkspaceContext;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.progress.UIJob;
import org.osgi.framework.BundleContext;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * The plugin implementing the core of the component system.
 * This maintains a ComponentSystem instance.
 */
public class ComponentSystemPlugin extends AbstractUIPlugin implements IResourceChangeListener {
    // The shared instance.
    private static ComponentSystemPlugin plugin;

    private static ImplementationsTypesRegistry implementationsRegistry;

    // Resource bundle.
    private ResourceBundle resourceBundle;

    public static final String PLUGIN_ID = "com.nokia.sdt.component.symbian"; //$NON-NLS-1$

    static public void info(String str) {
        Logging.log(plugin, Logging.newSimpleStatus(
                1, IStatus.INFO, str, null));
    }

    static public void infoIf(String option, String str) {
        Logging.logIf(plugin, option, Logging.newSimpleStatus(
                1, IStatus.INFO, str, null));
    }
    
    static public void log(IStatus status) {
        Logging.log(plugin, status);
    }

    static public void log(Throwable thr) {
        Logging.log(plugin, Logging.newStatus(plugin, thr));
    }

    static public void log(Throwable thr, String msg) {
        Logging.log(plugin, Logging.newStatus(plugin, IStatus.ERROR, msg, thr));
    }

    private BundleContext pluginContext;
    

    /**
     * The constructor.
     */
    public ComponentSystemPlugin() {
        super();
        plugin = this;
        initializeImplementationsRegistry();
    }

    public static void initializeImplementationsRegistry() {
		implementationsRegistry = new ImplementationsTypesRegistry();
	}

	/**
     * This method is called upon plug-in activation
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
        pluginContext = context;  
        ResourcesPlugin.getWorkspace().addResourceChangeListener(this,
        		IResourceChangeEvent.PRE_CLOSE |
        		IResourceChangeEvent.POST_CHANGE);

    }

    /**
     * This method is called when the plug-in is stopped
     */
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        plugin = null;
        resourceBundle = null;
        ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
    }

    /**
     * @return Returns the plugin bundle context.
     */
    public BundleContext getPluginContext() {
        return pluginContext;
    }

    /**
     * Returns the shared instance.
     */
    public static ComponentSystemPlugin getDefault() {
        return plugin;
    }

    /**
     * Returns the string from the plugin's resource bundle, or 'key' if not
     * found.
     */
    public static String getResourceString(String key) {
        ResourceBundle bundle = ComponentSystemPlugin.getDefault()
                .getResourceBundle();
        try {
            return (bundle != null) ? bundle.getString(key) : key;
        } catch (MissingResourceException e) {
            return key;
        }
    }

    /**
     * Returns the plugin's resource bundle,
     */
    public ResourceBundle getResourceBundle() {
        try {
            if (resourceBundle == null)
                resourceBundle = ResourceBundle
                        .getBundle("com.nokia.sdt.component.symbian.ComponentSystemPluginResources"); //$NON-NLS-1$
        } catch (MissingResourceException x) {
            resourceBundle = null;
        }
        return resourceBundle;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path.
     * 
     * @param path
     *            the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return AbstractUIPlugin.imageDescriptorFromPlugin(
                "com.nokia.sdt.component.symbian", path); //$NON-NLS-1$
    }

    /**
     * Get the ComponentProvider for this plugin.  We expect this to succeed.
     * @return ComponentProvider
     */
    protected ComponentProvider getComponentProvider() {
        ComponentProvider provider;
        try {
            IComponentProvider iProvider;
            iProvider = ComponentSystem.getComponentSystem().getProvider(ComponentProvider.PROVIDER_ID);
            provider = (ComponentProvider) iProvider;
            return provider;
        } catch (ClassCastException e) {
            log(e);
            return null;
        } catch (CoreException e) {
            log(e.getStatus());
            return null;
        }
    }
    
    /**
     * Add a new component library (assume to the ComponentProvider) 
     */
    public void addComponentLibrary(String dir) throws ComponentSystemException {
        ComponentProvider provider = getComponentProvider();
        if (provider != null) {
            provider.addUserComponentLibrary(dir);
        }
    }

    public void refreshComponents(IProgressMonitor monitor) {
    	monitor.beginTask(Messages.getString("ComponentSystemPlugin.RefreshingComponentsMessage"), 2); //$NON-NLS-1$
    	try {
	        ComponentProvider provider = getComponentProvider();
	        if (provider != null) {
	            // save all the UI Designer editors
	            EditorServices.saveAllModified(new SubProgressMonitor(monitor, 1));
	            // unload any cached data models
	            WorkspaceContext.getContext().unloadCachedModels();
	            // get rid of cached stuff referring to the old components
	            GlobalCache.disposeAll();
	            // refresh/reload all the components for our provider
	            provider.refresh();
	            // reload the UI Designer documents
	            EditorServices.reloadAll(new SubProgressMonitor(monitor, 1));
	        }
    	} finally {
    		monitor.done();
    	}
    }

        
    public void dumpComponents() {
        ComponentProvider provider = getComponentProvider();
        if (provider != null) {
            IComponentLibrary libs[] = provider.getComponentLibraries(null);
            for (int i = 0; i < libs.length; i++) {
                IComponentLibrary lib = libs[i];
                if (!lib.isLoaded())
                    continue;
                System.out.println("Dump of component library " + lib.getId()); //$NON-NLS-1$
                for (Iterator it = lib.iterator(); it.hasNext();) {
                    IComponent c = (IComponent) it.next();
                    System.out.print("\t" + c.getId()); //$NON-NLS-1$
                    if (c instanceof Component) {
                        Component cmp = (Component) c;
                        System.out.print(" (" + cmp.getSDKName() + ", " + //$NON-NLS-1$ //$NON-NLS-2$
                                (cmp.getMinSDKVersion() != null ? cmp.getMinSDKVersion().toString() : "") //$NON-NLS-1$ 
                                + " - "  //$NON-NLS-1$
                                + (cmp.getMaxSDKVersion() != null ? cmp.getMaxSDKVersion().toString() : "") //$NON-NLS-1$
                                + ")"); //$NON-NLS-1$
                    }
                    System.out.println();
                }
            }
        }
    }

    public void addUserComponentProject(IProject project) {
    	Check.checkArg(project);
        try {
            ComponentProvider provider = (ComponentProvider) ComponentSystem.getComponentSystem().getProvider(ComponentProvider.PROVIDER_ID);
            Check.checkState(provider != null);
    
            if (provider.addUserComponentProject(project))
            	refreshComponents();
        } catch (CoreException e) {
            log(e);
        }
    }
        
    public void removeUserComponentProject(IProject project) {
    	Check.checkArg(project);
        try {
            ComponentProvider provider = (ComponentProvider) ComponentSystem.getComponentSystem().getProvider(ComponentProvider.PROVIDER_ID);
            Check.checkState(provider != null);

            if (provider.removeUserComponentProject(project))
            	refreshComponents();
        } catch (CoreException e) {
            log(e);
        }
    }
    
    /**
     * Refresh components and show progress if possible.
     * Do not call inside a resource listener!
     */
    public void refreshComponents() {
        if (Platform.isRunning()) {
        	try {
        		UITaskUtils.runImmediately(new IRunnableWithProgress() {

					public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
						refreshComponents(monitor);
					}
        			
        		});
				
			} catch (Exception e) {
				log(e);
			}
        } else {
        	refreshComponents(new NullProgressMonitor());
        }
		
	}

	public static ImplementationsTypesRegistry getImplementationTypes() {
    	return implementationsRegistry;
    }
	
	/**
	 * Synchronize user component projects when they are opened and closed.
	 */
	public void resourceChanged(IResourceChangeEvent event) {

		ComponentProvider provider = null;
		try {
			provider = (ComponentProvider) ComponentSystem.getComponentSystem().findProvider(ComponentProvider.PROVIDER_ID);
			if (provider == null)
				return;
		} catch (CoreException e) {
			return;
		}
		if (provider == null)
			return;
		
		boolean needRefresh = false;
		
		if (event.getDelta() != null) {
			IResourceDelta[] deltas = event.getDelta().getAffectedChildren();
			for (int i = 0; i < deltas.length; i++) {
				if (deltas[i].getResource() instanceof IProject) {
					needRefresh |= handleProjectChange((IProject) deltas[i].getResource(), event.getType(), provider);
				}
			}
		}
		else if (event.getResource() instanceof IProject)
			needRefresh |= handleProjectChange((IProject) event.getResource(), event.getType(), provider);
		
		if (needRefresh) {
			// refresh components once the resource listeners are done
    		UIJob job = new UIJob(Messages.getString("ComponentSystemPlugin.RefreshingComponentsMessage")) {

				@Override
				public IStatus runInUIThread(final IProgressMonitor monitor) {
					BusyIndicator.showWhile(null, new Runnable() {

						public void run() {
							refreshComponents(monitor);
						}
						
					});
					return Status.OK_STATUS;
				}
    			
    		};
    		job.setUser(true);
    		job.schedule();
		}
	}

	private boolean handleProjectChange(IProject project, int eventType, ComponentProvider provider) {
		switch (eventType) {
		case IResourceChangeEvent.PRE_DELETE:
		case IResourceChangeEvent.PRE_CLOSE:
			if (provider.findUserComponentProject(project) != null) {
				if (provider.removeUserComponentProject(project))
					return true;
			}
			break;
		case IResourceChangeEvent.POST_CHANGE:
			if (project.isOpen()) {
				if (provider.findUserComponentProject(project) == null
						&& UserComponentProjectNature.hasNature(project)) {
					if (provider.addUserComponentProject(project))
						return true;
				}
			} else {
				if (provider.findUserComponentProject(project) != null) {
					if (provider.removeUserComponentProject(project))
						return true;
				}
				
			}
			break;
		}
		return false;
	}


}
