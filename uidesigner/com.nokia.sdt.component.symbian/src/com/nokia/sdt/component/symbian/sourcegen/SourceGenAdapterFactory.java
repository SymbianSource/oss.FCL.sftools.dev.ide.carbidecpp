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
package com.nokia.sdt.component.symbian.sourcegen;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.component.symbian.IFacetContainer;
import com.nokia.sdt.emf.component.SourceGenType;
import com.nokia.sdt.scripting.ScriptException;
import com.nokia.sdt.sourcegen.IComponentSourceGen;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import java.io.File;
import java.util.*;

public class SourceGenAdapterFactory implements IAdapterFactory {
	
	private static List<String> debuggableComponents = new ArrayList<String>();

	private static IPath debugDirectory = null; //$NON-NLS-1$
	
    private static final QualifiedName DEBUGGABLE_COMPONENT_ID_ENTRIES =
    	new QualifiedName(ComponentSystemPlugin.PLUGIN_ID, 
    			"PropertiesForComponentsWithSourceGenDebugging");
    private static final QualifiedName DEBUG_OUTPUT_DIR =
    	new QualifiedName(ComponentSystemPlugin.PLUGIN_ID, 
    			"SourceGenDebugDirectory");
	private static final String COMPONENT_ENTRY_PREFIX = "ComponentsWithSourceGenDebugging_";
    
    static {
    	loadDebugInfo();
    }

	private EStructuralFeature sourceGenFeature;
	private Class adapterList[] = { IComponentSourceGen.class };
	
	/**
	 * @param sourceGenFeature the structural feature whose type is SrcgenType
	 */
	public SourceGenAdapterFactory(Plugin plugin, EStructuralFeature sourceGenFeature) {
	    //Check.checkArg(plugin); // EJS: not needed
		Check.checkArg(sourceGenFeature);
		this.sourceGenFeature = sourceGenFeature;
	}

	public Object getAdapter(Object adaptableObject, Class adapterType) {
		Object result = null;
		if (adaptableObject instanceof IFacetContainer &&
			adaptableObject instanceof IComponent &&
			IComponentSourceGen.class.equals(adapterType)) 
        {
            IComponent component = (IComponent) adaptableObject;
            SourceGenType srcgen = getSourceGenTypeFromContainer((IFacetContainer) component);
            if (srcgen != null) {
                try {
                    result = new SourceGenAdapterScript( 
                            component, 
                            srcgen);
                } catch (ScriptException e) {
                    result = null;
                }
            }
        }
        return result;
    }

    private SourceGenType getSourceGenTypeFromContainer(IFacetContainer fc) {
        SourceGenType srcgenObj = null;
        EObject container = fc.getEMFContainer();
        Object featureObj = container.eGet(sourceGenFeature);
        if (featureObj instanceof SourceGenType)
            srcgenObj = (SourceGenType) featureObj;
        
        return srcgenObj;
    }
    
    
    public Class[] getAdapterList() {
        return adapterList;
    }
    
    public static IPath getDebugOutputDir() {
    	return debugDirectory;
    }

    public static void setDebugOutputDir(IPath path) {
    	debugDirectory = path;
    }
    
	private static synchronized void loadDebugInfo() {
		try {
			debuggableComponents = new ArrayList<String>();
			if (Platform.isRunning() && ResourcesPlugin.getWorkspace().getRoot() != null) {
				// need to read an indirected list of properties which
				// hold sublists of the components to debug
				String entryProp = ResourcesPlugin.getWorkspace().getRoot().getPersistentProperty(
						DEBUGGABLE_COMPONENT_ID_ENTRIES);
				if (entryProp != null) {
					String[] entries = entryProp.split(","); //$NON-NLS-1$
					for (String entry : entries) {
						QualifiedName name = new QualifiedName(ComponentSystemPlugin.PLUGIN_ID, 
				    			entry);
						String info = ResourcesPlugin.getWorkspace().getRoot().getPersistentProperty(
								name);
						if (info != null) {
							String[] ids = info.split("/");
							for (String id : ids) {
								debuggableComponents.add(id);
							}
						}
					}
				}
			}

			debugDirectory = null;
			if (Platform.isRunning() && ResourcesPlugin.getWorkspace().getRoot() != null) {
				String pathVal = ResourcesPlugin.getWorkspace().getRoot().getPersistentProperty(DEBUG_OUTPUT_DIR);
				if (pathVal != null)
					debugDirectory = new Path(pathVal);
				else
					debugDirectory = null;
			} 

		} catch (CoreException e) {
		}
		
	}


    public static synchronized void saveDebugInfo() {
		if (Platform.isRunning() && ResourcesPlugin.getWorkspace().getRoot() != null) {
			// To persist the list of components, we need to split up the
			// list into several 2k or smaller lists.  Yuck!
			int entryNum = 0;
			StringBuffer entries = new StringBuffer();
	    	StringBuffer buffer = new StringBuffer();
	    	for (Iterator iter = debuggableComponents.iterator(); true;) {
				String id = null;
				if (iter.hasNext())
					id = (String) iter.next();
				if (id != null 
						? buffer.length() + id.length() > 2048
						: true) {
					// dump
					String entryProp = COMPONENT_ENTRY_PREFIX + (entryNum++);
					if (entries.length() > 0)
						entries.append(',');
					entries.append(entryProp);
					QualifiedName name = new QualifiedName(ComponentSystemPlugin.PLUGIN_ID, 
			    			entryProp);
					try {
						ResourcesPlugin.getWorkspace().getRoot().setPersistentProperty(name, buffer.toString());
					} catch (CoreException e) {
						//Logging.showErrorDialog("Error storing preferences", "Could not store list of debuggable components", e.getStatus());
						ComponentSystemPlugin.log(e.getStatus());
					}
					buffer.setLength(0);
				}
				if (id == null)
					break;
				buffer.append(id);
				if (iter.hasNext())
					buffer.append("/");
	    	}
			
			try {
				ResourcesPlugin.getWorkspace().getRoot().setPersistentProperty(DEBUGGABLE_COMPONENT_ID_ENTRIES, entries.toString());
				
				ResourcesPlugin.getWorkspace().getRoot().setPersistentProperty(DEBUG_OUTPUT_DIR, debugDirectory != null ? debugDirectory.toOSString() : null);
				
			} catch (CoreException e) {
				//Logging.showErrorDialog("Error storing preferences", "Could not store list of debuggable components", e.getStatus());
				ComponentSystemPlugin.log(e.getStatus());
			}
		}
	}

	public static boolean isDebuggableComponent(String id) {
		return debuggableComponents.contains(id);
	}
	
	public static List<String> getDebuggableComponents() {
		return debuggableComponents;
	}

	
	/** Get the path where we would write sourcegen Javascript when using &lt;sourceGen debug="true"&gt; */
	public static IPath getDebugJavaScriptLocation(IComponent component) {
		String fileName = "#sourcegen." + component.getId() + ".js"; //$NON-NLS-1$ //$NON-NLS-2$
		if (debugDirectory == null) {
			// write relative to component
	        IFacetContainer fc = (IFacetContainer) component;
	        if (fc != null) {
	            File base = fc.getBaseDirectory();
	            // name it so user can find it and so it's ignored by Eclipse Team support
	            return new Path(base.getAbsolutePath() + File.separator + fileName);
	        }
	        else
	        	return new Path("."); //$NON-NLS-1$
		}
		else {
			// always go to same dir
			return debugDirectory.append(fileName);
		}
	}
    
}
