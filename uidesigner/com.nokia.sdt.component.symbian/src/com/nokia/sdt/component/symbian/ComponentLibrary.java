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
import com.nokia.sdt.component.symbian.properties.CompoundPropertyTypeDescriptor;
import com.nokia.sdt.component.symbian.properties.EnumPropertyTypeDescriptor;
import com.nokia.sdt.emf.component.*;
import com.nokia.sdt.emf.component.loader.Loader;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.osgi.framework.Bundle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Pattern;

/** 
 * A default implementation of the component library with 
 * search semantics specified in the functional spec
 * 
 * 
 *
 */
public class ComponentLibrary 
    implements IComponentLibrary  {

    private static final String COMPONENT_EXTENSION = ".component"; //$NON-NLS-1$
	/** Unique name of library, either based on the plugin + id from
     * an extension point, or the directory for a user component project.
     */
    String libName;
    String path;
    private File basedir;
	ComponentProvider provider;
	Bundle bundle;
    
    private boolean loaded = false;
	private Pattern sdkVendorPattern;
    
    /** Construct from an extension */
    public ComponentLibrary(IExtension extension, IConfigurationElement config)
    throws CoreException
    {
        ComponentSystemPlugin.infoIf("debug", "ComponentLibrary.__init__: " + extension.getUniqueIdentifier()); //$NON-NLS-1$
        
        /* gather attributes from extension XML */
        String id = extension.getSimpleIdentifier();
        if (id == null)
            id = "???"; //$NON-NLS-1$
        
        String path = config.getAttribute("path"); //$NON-NLS-1$
        if (path == null)
            path = "."; //$NON-NLS-1$
        
        //String userString = config.getAttribute("user"); 
        //this.bUser = userString != null && userString.equals("true");
    
        /* cache some useful info about the extension */
        String extensionName = extension.getUniqueIdentifier();
        int idx = extensionName.lastIndexOf('.');
        String plugin = extensionName.substring(0, idx);
        //String extid = extensionName.substring(idx);

        ComponentSystemPlugin.infoIf("debug", "creating component library for "+plugin); //$NON-NLS-1$

        this.libName = plugin + "." + id; //$NON-NLS-1$
        
        String sdkVendorPatternString = config.getAttribute("sdkPattern"); //$NON-NLS-1$
        if (sdkVendorPatternString == null)
        	sdkVendorPatternString = ".*"; //$NON-NLS-1$
        this.sdkVendorPattern = Pattern.compile(sdkVendorPatternString);
        
        /* we REQUIRE plugins to be in the filesystem */
        this.bundle = Platform.getBundle(plugin);
        URL url;
        url = FileLocator.find(bundle, Path.fromOSString(path), null);
        if (url == null) {
        	throw new CoreException(Logging.newStatus(ComponentSystemPlugin.getDefault(), IStatus.ERROR, 
        			"Could not find component library for " + bundle.getSymbolicName() + " under " + path)); //$NON-NLS-1$ //$NON-NLS-2$
        }
        this.path = resolveFileURL(url);

        this.basedir = new File(this.path);
        if (!basedir.exists() || !basedir.isDirectory()) {
            throw new ComponentSystemException(Logging.newStatus(
                    ComponentSystemPlugin.getDefault(),
                    IStatus.ERROR,
                    Messages.getString("ComponentLibrary.CannotLocateDirectory") + basedir, //$NON-NLS-1$
                    null));
        }
        
    }

    /** Construct from scratch */ 
    public ComponentLibrary(String path) throws CoreException
    {
        Check.checkArg(path);
        this.libName = path;
        this.path = path;
        this.sdkVendorPattern = Pattern.compile(".*"); //$NON-NLS-1$
        
        this.basedir = new File(this.path);
        if (!basedir.exists() || !basedir.isDirectory()) {
            throw new ComponentSystemException(Logging.newStatus(
                    ComponentSystemPlugin.getDefault(),
                    IStatus.ERROR,
                    MessageFormat.format(Messages.getString("ComponentLibrary.CannotLocateDirectory"), new Object[] { basedir }), //$NON-NLS-1$
                    null));
        }
    }

    /* Library-wide filter information */
    private Object sdkfilter;
    
    /* All the loaded components (filename -> IComponent) */
    private Map components;
    
    
    /************* IComponentLibrary */

    public String getId() {
        return libName;
    }

    /*
     *  (non-Javadoc)
     * @see com.nokia.sdt.component.symbian.IComponentLibrary#isLoaded()
     */
    public boolean isLoaded() {
        return loaded;
    }
    
    /** Load components by recursively iterating from the "path" relative
     * to the plugin's root directory.  Try to avoid reloading unchanged
     * components.
     * 
     * @see com.nokia.sdt.component.symbian.IComponentLibrary#loadComponents()
     */
    public synchronized void loadComponents()
            throws ComponentSystemException {

        if (loaded)
            return;

        String s = "loading components from " + basedir; // + ", user = " + bUser; //$NON-NLS-1$
        ComponentSystemPlugin.infoIf("debug", s); //$NON-NLS-1$
        Logging.timeStart(s);

        // check for SDK info
        File sdkinfo = new File(basedir, ".sdkinfo"); //$NON-NLS-1$
        if (sdkinfo.exists() && sdkinfo.isFile()) {
            loadSdkInfo(sdkinfo);
        } else {
            loadSdkInfo(null);
        }

        // scan for all components
        components = new HashMap();
        
        // be smarter in the scan to handle linked folders, etc. in user component projects
        IResource rsrc = FileUtils.convertFileToExistingResource(basedir);
        if (rsrc instanceof IContainer) {
        	try {
				scanForWorkspaceComponents((IContainer) rsrc, components);
			} catch (CoreException e) {
				ComponentSystemPlugin.log(e);
			}
        } else {
        	scanForComponents(basedir, components);
        }
        
        // load them all
        for (Iterator iter = components.entrySet().iterator(); iter.hasNext();) {
            Map.Entry entry = (Map.Entry) iter.next();
            String name = (String) entry.getKey();
            ComponentSystemPlugin.infoIf("debug", "--> added component "+name); //$NON-NLS-1$
            IComponent component = loadComponent(name);
            if (component != null)
            	entry.setValue(component);
            else
            	iter.remove();
        }
        
        loaded = true;
        Logging.timeEnd();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.component.symbian.IComponentLibrary#refreshComponents()
     */
    public synchronized void refreshComponents() throws ComponentSystemException {
        ComponentSystemPlugin.infoIf("debug", "refreshing component library from " + basedir); //$NON-NLS-1$

        // check for SDK info (always reload)
        File sdkinfo = new File(basedir, ".sdkinfo"); //$NON-NLS-1$
        if (sdkinfo.exists() && sdkinfo.isFile()) {
            loadSdkInfo(sdkinfo);
        } else {
            loadSdkInfo(null);
        }

        // scan for components
        Map newComponents = new HashMap();

        // be smarter in the scan to handle linked folders, etc. in user component projects
        IResource rsrc = FileUtils.convertFileToExistingResource(basedir);
        if (rsrc instanceof IContainer) {
        	try {
				scanForWorkspaceComponents((IContainer) rsrc, newComponents);
			} catch (CoreException e) {
				ComponentSystemPlugin.log(e);
			}
        } else {
        	scanForComponents(basedir, newComponents);
        }

        // reload any that changed
        for (Iterator iter = newComponents.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            if (components != null && components.keySet().contains(name)) {
                ComponentSystemPlugin.infoIf("debug", "--> kept component "+name); //$NON-NLS-1$
                // maybe use a smarter check (e.g. timestamps)?
                // always reload the component
                IComponent component = loadComponent(name);
                if (component != null)
                	newComponents.put(name, component);
                else
                	iter.remove();
            }
            else {
                // added component
                ComponentSystemPlugin.infoIf("debug", "--> added component "+name); //$NON-NLS-1$
                IComponent component = loadComponent(name);
                if (component != null)
                	newComponents.put(name, component);
                else
                	iter.remove();
            } 
        }
        
        // show removed components for debugging
        if (Logging.isDebugOptionEnabled(ComponentSystemPlugin.getDefault(), "debug") //$NON-NLS-1$
                && components != null) {
            for (Iterator iter = components.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                if (!newComponents.keySet().contains(name)) {
                    // removed component
                    ComponentSystemPlugin.info("--> removed component "+name); //$NON-NLS-1$
                }
            }
        }
        
        // update library in one fell swoop
        components = newComponents;
    }
	
    /* (non-Javadoc)
     * @see com.nokia.sdt.component.symbian.IComponentLibrary#iterator()
     */
    public Iterator iterator() {
        if (!loaded)
            throw new AssertionError("loadComponents() not called"); //$NON-NLS-1$
        Iterator result;
        synchronized(this) {
        	result = Collections.unmodifiableCollection(components.values()).iterator(); 
        }
        return result;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.component.symbian.IComponentLibrary#getComponents(IComponentFilter filter)
     */
    public IComponent[] getComponents(IComponentFilter filter) {
        List clist = new ArrayList();
        synchronized (this) {
            for (Iterator cs = components.values().iterator(); cs.hasNext();) {
                IComponent c = (IComponent) cs.next();
                if (filter == null || filter.accept(c)) {
                    clist.add(c);
                }
            }
        }
        return (IComponent[]) clist.toArray(new IComponent[clist.size()]);
    }
    
    /** Load the .sdkinfo file which describes a version filter
     * over all the contained components.
     * 
     * @param sdkinfo
     */
    private void loadSdkInfo(File sdkinfo) {
        if (sdkinfo != null) {
            ComponentSystemPlugin.infoIf("debug", "Loading SDK information " + sdkinfo); //$NON-NLS-1$
            // TODO: implement SDK info if necessary
            this.sdkfilter = "foo";  //$NON-NLS-1$
            Check.checkArg(this.sdkfilter);   // warning suppression; not used
        } else
            this.sdkfilter = null;
    }

    /** Load a single component 
     *  @param filename the full path to the .component file 
     */
    private IComponent loadComponent(String filename) throws ComponentSystemException {
        return loadComponent(new File(filename));
    }
	
	private void registerCompoundTypes(ComponentDefinitionType cdt, File file, ILocalizedStrings ls) {
		EList compoundTypes = cdt.getCompoundPropertyDeclaration();
		if (compoundTypes != null) {
			for (Iterator iter = compoundTypes.iterator(); iter.hasNext();) {
				CompoundPropertyDeclarationType cpdt = (CompoundPropertyDeclarationType) iter.next();
				String qualifiedName = cpdt.getQualifiedName();
				if (qualifiedName != null) {
					ITypeDescriptor td = new CompoundPropertyTypeDescriptor(cpdt, bundle, ls);
					provider.registerTypeDescriptor(qualifiedName, td);
				}
				else {
					String format = Messages.getString("ComponentLibrary.7"); //$NON-NLS-1$
					Object params[] = {file.getAbsolutePath()};
					String message = MessageFormat.format(format, params);
					Logging.newSimpleStatus(ComponentSystemPlugin.getDefault(), IStatus.ERROR, message, null);
				}
			}
		}
	}
	
	private void registerEnumTypes(ComponentDefinitionType cdt, File file, ILocalizedStrings ls) {
		EList enumTypes = cdt.getEnumPropertyDeclaration();
		if (enumTypes != null) {
			for (Iterator iter = enumTypes.iterator(); iter.hasNext();) {
				EnumPropertyDeclarationType epdt = (EnumPropertyDeclarationType) iter.next();
				String qualifiedName = epdt.getQualifiedName();
				if (qualifiedName != null) {
					ITypeDescriptor td = new EnumPropertyTypeDescriptor(epdt, ls);
					provider.registerTypeDescriptor(qualifiedName, td);
				}
				else {
					String format = Messages.getString("ComponentLibrary.8"); //$NON-NLS-1$
					Object params[] = {file.getAbsolutePath()};
					String message = MessageFormat.format(format, params);
					Logging.newSimpleStatus(ComponentSystemPlugin.getDefault(), IStatus.ERROR, message, null);
				
				}
			}
		}
	}

    /** Load a single component 
     * @param file the .component file
     */
    private IComponent loadComponent(File file) throws ComponentSystemException {
		IComponent result = null;
		ComponentSystemPlugin.infoIf("debug", "Loading component " + file); //$NON-NLS-1$
        String fName;
        try {
            fName = file.getCanonicalPath();
        } catch(IOException e) {
            fName = "???"; //$NON-NLS-1$
        }
		
		URI fileURI = URI.createFileURI(fName);
		Loader l = new Loader();
		DocumentRoot root = l.load(fileURI);
		if (root != null) {
			ComponentDefinitionType cdt = root.getComponentDefinition();
			if (cdt != null)
			{
				String baseName;
				int lastDotPos = file.getName().lastIndexOf(".");
				if (lastDotPos > 0)
					baseName = file.getName().substring(0, lastDotPos);
				else
					baseName = file.getName();
				
				ILocalizedStrings ls = new LocalizedStrings(file.getParentFile(), baseName);
				
				registerCompoundTypes(cdt, file, ls);
				registerEnumTypes(cdt, file, ls);
				ComponentType ct = cdt.getComponent();
				if (ct != null) {
					result = new Component((ComponentProvider)getProvider(), ct, file, bundle, ls);
				}
			}
			// else, perhaps the file has only compound property definitions
		}
		else
            ComponentSystemPlugin.info(Messages.getString("ComponentLibrary.NoRootComponent") + fName); //$NON-NLS-1$

		return result;
    }

    /** Recursively find all the *.component definitions,
     * ignoring directories with (parenthesized names) 
     */
    private void scanForComponents(File dir, Map components) {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(COMPONENT_EXTENSION)) { //$NON-NLS-1$
                ComponentSystemPlugin.infoIf("debug", "... found " + file); //$NON-NLS-1$
                try {
                    components.put(file.getCanonicalPath(), null);
                } catch (IOException e) {
                    Logging.log(ComponentSystemPlugin.getDefault(), 
                            Logging.newStatus(ComponentSystemPlugin.getDefault(), e));
                    ComponentSystemPlugin.infoIf("debug", "  guess not!"); //$NON-NLS-1$
                }
            } else if (file.isDirectory()) {
                if (file.getName().startsWith("(") && file.getName().endsWith(")")) { //$NON-NLS-1$
                    ComponentSystemPlugin.infoIf("debug", "Ignoring hidden directory " + file); //$NON-NLS-1$
                } else {
                    scanForComponents(file, components);
                }
            }
        }
    }

    /** Recursively find all the *.component definitions,
     * ignoring directories with (parenthesized names) 
     * @throws CoreException 
     */
    private void scanForWorkspaceComponents(IContainer dir, final Map components) throws CoreException {
    	dir.accept(new IResourceProxyVisitor() {

			public boolean visit(IResourceProxy proxy) throws CoreException {
				if ((proxy.getType() & IResource.FILE) != 0 && proxy.getName().endsWith(COMPONENT_EXTENSION)) {
					IResource rsrc = proxy.requestResource();
					if (rsrc != null) {
						IPath path = rsrc.getLocation();
						if (path != null) {
							File file = path.toFile().getAbsoluteFile();
							ComponentSystemPlugin.infoIf("debug", "... found " + file); //$NON-NLS-1$
							components.put(file.getAbsolutePath(), null);
						}
					}
					return false;
				} else if ((proxy.getType() & IResource.FOLDER + IResource.PROJECT) != 0) {
					 if (proxy.getName().startsWith("(") && proxy.getName().endsWith(")")) { //$NON-NLS-1$
						 ComponentSystemPlugin.infoIf("debug", "Ignoring hidden directory " + proxy.getName()); //$NON-NLS-1$
						 return false;
					 }
					return true;
				} else {
					// dunno what this is... try it out
					return true;
				}
			}
    		
    	}, 0);
    }
    
    /**
     * @param url
     * @throws ComponentSystemException
     */
    private String resolveFileURL(URL url) throws ComponentSystemException {
        try {
            url = FileLocator.resolve(url);
        } catch (IOException e) {
            IStatus status = Logging.newStatus(
                    ComponentSystemPlugin.getDefault(),
                    IStatus.ERROR,
                    Messages.getString("ComponentLibrary.CannotResolveURL") + url, //$NON-NLS-1$
                    e);

            ComponentSystemPlugin.log(status);
            throw new ComponentSystemException(status);
        }
        
        // get filesystem path
        if (!url.getProtocol().equals("file")) { //$NON-NLS-1$
            IStatus status = Logging.newStatus(
                    ComponentSystemPlugin.getDefault(),
                    IStatus.ERROR,
                    Messages.getString("ComponentLibrary.CannotHandleNonFileURL") + url, //$NON-NLS-1$
                    null);
            ComponentSystemPlugin.log(status);
            throw new ComponentSystemException(status);
       }
        
       String path = url.getPath();
       if (Platform.getOS().equals(Platform.OS_WIN32) && path.startsWith("/")) { //$NON-NLS-1$
    	   path = path.substring(1);
       }
       return path;
    }
	
	public void setProvider(IComponentProvider provider) {
		this.provider = (ComponentProvider) provider;
	}

    public IComponentProvider getProvider() {
		return provider;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public Bundle getBundle() {
        return bundle;
    }
    
    public Pattern getSDKVendorPattern() {
    	return sdkVendorPattern;
    }
}
