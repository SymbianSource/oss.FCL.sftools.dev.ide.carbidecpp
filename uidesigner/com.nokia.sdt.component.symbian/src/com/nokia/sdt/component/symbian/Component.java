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
import com.nokia.sdt.component.symbian.properties.TypeDescriptors;
import com.nokia.sdt.displaymodel.GlobalCache;
import com.nokia.sdt.emf.component.*;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.*;
import org.eclipse.emf.ecore.EObject;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

import java.io.File;
import java.text.MessageFormat;
import java.util.*;

public class Component implements IComponent, IFacetContainer, Cloneable, IDisposable {
	
	private ComponentProvider provider;
	private ComponentSet componentSet;
	private Bundle bundle;
	private ComponentType emfComponent;
	private File componentFileOrDir;
	private Version componentVersion;
	private Version minSDKVersion;
	private Version maxSDKVersion;
	private ILocalizedStrings strings;
    // key used to access adapters map
    private Object adapterKey;
			
    /**
     * Create a component for the given provider which comes from
     * the given file.  
     * @param provider
     * @param emfComponent
     * @param componentFileOrDir the XML file, or base directory if
     * no file exists
     * @param bundle
     * @param ls
     */
	public Component(ComponentProvider provider, ComponentType emfComponent,
					 File componentFileOrDir, Bundle bundle, ILocalizedStrings ls)
	{
	    Check.checkArg(componentFileOrDir);
        init(provider, emfComponent, componentFileOrDir, bundle, ls);
	}

	/**
	 * Constructs a Component without providing the EMF data. A new instance
	 * will be created. This constructor is provided for unit test, the component
	 * data must be further initialized to use the component.
	 */
	public Component(ComponentProvider provider, File componentFileOrDir, Bundle bundle,
			ComponentType emfComponent, ILocalizedStrings ls) {
        init(provider, emfComponent, componentFileOrDir, bundle, ls);
	}

	private void init(ComponentProvider provider, ComponentType emfComponent, File componentFileOrDir, 
			Bundle bundle, ILocalizedStrings strings) {
	    Check.checkArg(provider);
	    Check.checkArg(emfComponent);
	    Check.checkArg(strings);
	    //Check.checkArg(bundle); // may be null for user components
        
	    this.provider = provider;
	    this.emfComponent = emfComponent;
	    this.componentFileOrDir = componentFileOrDir;
	    this.bundle = bundle;
	    this.strings = strings;
        
	    initVersions();
        
        adapterKey = new Object();
	}
    
    /*
    public boolean equals(Object obj) {
        if (!(obj instanceof Component))
            return false;
        Component other = (Component) obj;
        return provider == other.provider
            && bundle == other.bundle
            && emfComponent == other.emfComponent;
    }*/
	
    synchronized Map getAdapters() {
        Map adapters = (Map)GlobalCache.getCache().get(adapterKey);
        if (adapters == null) {
            adapters = new HashMap();
            GlobalCache.getCache().put(adapterKey, adapters);
        }
        return adapters;
    }

    synchronized Object getExistingAdapter(Class adapterType) {
    	if (adapterType.isInstance(this))
    		return this;
    	
        Map adapters = (Map)GlobalCache.getCache().get(adapterKey);
        if (adapters == null)
            return null;
        return adapters.get(adapterType);
    }


    public void dispose() {
        Map adapters = (Map)GlobalCache.getCache().get(adapterKey);
        if (adapters != null)
            adapters.clear();
    }

	private void initVersions() {
		componentVersion = initVersion(emfComponent.getVersion());
		SymbianType st = emfComponent.getSymbian();
		if (st != null) {
			minSDKVersion = initVersion(st.getMinSDKVersion());
			maxSDKVersion = initVersion(st.getMaxSDKVersion());
		}
	}
	
	private Version initVersion(String versionString) {
		Version result = null;
		if (versionString != null) {
			try {
				result = Version.parseVersion(versionString);
			}
			catch (Exception x) {
				Object args[] = {getId(), versionString};
				String msg = MessageFormat.format(Messages.getString("Component.0"), args); //$NON-NLS-1$
				Plugin plugin = ComponentSystemPlugin.getDefault(); 
				Logging.log(plugin, Logging.newStatus(plugin, IStatus.ERROR, msg));
			}
		}
		return result;
	}
	
	public Object clone() {
		Component copy = null;
		try {
			copy = (Component) super.clone();
			copy.componentSet = null;
		}
		catch (CloneNotSupportedException x) {
			// should not happen since this class supports cloning
		}
		return copy;
	}

	public String getId() {
		return emfComponent.getQualifiedName();
	}
	
    public String getClassName() {
        return emfComponent.getSymbian() != null ?
                emfComponent.getSymbian().getClassName() : null;
    }

    public IComponentProvider getProvider() {
		return provider;
	}
	
	public IComponentSet getComponentSet() {
		return componentSet;
	}
	
	public void setComponentSet(ComponentSet set) {
		this.componentSet = set;
	}
	
	public Bundle getBundle() {
		return bundle;
	}
	
	public ComponentType getEMFComponent() {return emfComponent;}
	
	public Component getBaseComponent() {
		Component result = null;	
		if (componentSet != null) {	
			String baseID = emfComponent.getBaseComponent();
			if (baseID != null) {
				result = (Component) componentSet.lookupComponent(baseID);
                
                // sanity check
                if (result == this) {
                    RuntimeException thr = new IllegalStateException(Messages.getString("Component.1") + baseID); //$NON-NLS-1$
                    ComponentSystemPlugin.log(thr);
                    throw thr;
                }
			}
		}
		return result;
	}
    
    public String getBaseComponentId() {
    	return emfComponent.getBaseComponent();
	}

	public IComponent getComponentBase() {
        Check.checkState(componentSet != null);
        return getBaseComponent();
    }
	
	public boolean isAbstract() {
		return emfComponent.isAbstract();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	public synchronized Object getAdapter(Class adapterType) {
		Object adapter = getExistingAdapter(adapterType);
		if (adapter == null) {
			adapter = provider.adaptComponent(this, adapterType);
			if (adapter != null) {
				getAdapters().put(adapterType, adapter);
			}
		}
		return adapter;
	}
	
	public AbstractPropertyType getProperty(Object propertyID) {
		AbstractPropertyType result = null;
		PropertiesType properties = emfComponent.getProperties();
		if (properties != null) {
			List emfProperties = properties.getAbstractProperty();
			if (emfProperties != null) { 
				for (Iterator iter = emfProperties.iterator(); iter.hasNext();) {
					AbstractPropertyType abstractProperty = (AbstractPropertyType) iter.next();
					if (abstractProperty.getName().equals(propertyID)) {
						result = abstractProperty;
						break;
					}	
				}
			}
		}
		if (result == null) {
			Component base = getBaseComponent();
			if (base != null) {
				result = base.getProperty(propertyID);
			}
		}
		return result;
	}
	
	public ITypeDescriptor getPropertyTypeDescriptor(Object propertyID) {
		AbstractPropertyType propertyType = getProperty(propertyID);
		ITypeDescriptor result = null;
		if (propertyType != null) {
			if (propertyType instanceof SimplePropertyType) {
				SimplePropertyType simplePropertyType = ((SimplePropertyType)propertyType);
				result = TypeDescriptors.lookupPrimitiveDescriptor(
						provider, 
						simplePropertyType.getType(),
						componentSet.lookupTypeDescriptor(simplePropertyType.getExtendWithEnum()));
			}
			else if (propertyType instanceof CompoundPropertyType) {
				if (componentSet != null) {
					String typeID = ((CompoundPropertyType)propertyType).getType();
					result = componentSet.lookupTypeDescriptor(typeID);
				}
			}
		}
		return result;
	}

	public String getCategory() {
		// XML data is either a property key or an unlocalized value
		String result = strings.checkPercentKey(emfComponent.getCategory());
		if (!TextUtils.isEmpty(result)) {
			IComponentSet cs = getComponentSet();
			if (cs != null)
				result = cs.getProvider().getCategoryText(result);
		}
		return result;
	}

	public EObject getEMFContainer() {
		return emfComponent;
	}
	
    /**
     * Returns the file for the component. 
     */
    public File getComponentFile() {
        return componentFileOrDir;
    }

	/**
	 * Returns the top level directory for the component. Any relative path
	 * references references are relative to this directory.
	 */
	public File getBaseDirectory() {
		return (componentFileOrDir != null && componentFileOrDir.isFile()) 
                ? componentFileOrDir.getParentFile() 
                        : componentFileOrDir;
	}

	public Iterator getAdditionalFacetContainers() {
		IFacetContainer[] fca;
		Component base = getBaseComponent();
		if (base != null) {
			fca = new IFacetContainer[1];
			fca[0] = base;
		}
		else
			fca = new IFacetContainer[0];
		Iterator result = Arrays.asList(fca).iterator();
		return result;
	}

	public String getInstanceNameRoot() {
		// The component author can provide this value via an attribute,
		// otherwise it is the leaf part of the component ID.
		String result = emfComponent.getInstanceNameRoot();
		if (result == null) {
			String id = getId();
			int lastDot = id.lastIndexOf("."); //$NON-NLS-1$
			if (lastDot > 0) {
				result = id.substring(lastDot+1);
			}
			else 
				result = id;
		}
		return result;
	}
	
	public String getFriendlyName() {
		String result = strings.checkPercentKey(getEMFComponent().getFriendlyName());
		if (result == null) {
			result = getId();
		}
		return result;
	}
	
	public String getSDKName() {
        if (emfComponent.getSymbian() != null) {
            Object name = emfComponent.getSymbian().getSdkName();
            if (name != null)
                return name.toString();
        }
		return null;
	}

	public Version getMaxSDKVersion() {
		return maxSDKVersion;
	}

	public Version getMinSDKVersion() {
		return minSDKVersion;
	}

	public Version getComponentVersion() {
		return componentVersion;
	}
	
	/**
	 * Return true if this component's version is considered
	 * newer than the other component. Component versions are
	 * optional. When one is missing a version the unversioned
	 * component is considered newer. When both are missing or
	 * both are equal false is returned.
	 */
	public boolean isLaterComponentVersion(Component other) {
		if (other.componentVersion == null)
			return false;
		if (componentVersion == null)
			return true;
		return componentVersion.compareTo(other.componentVersion) > 0;
	}

	public ILocalizedStrings getLocalizedStrings() {
		return strings;
	}
	
	public String toString() {
		return "Component: "+getId();
	}

    public boolean isSameSDKNameAndRange(Component otherComponent) {
        if (!otherComponent.getSDKName().equals(getSDKName()))
            return false;
        Version other, mine;
        other = otherComponent.getMinSDKVersion();
        mine = getMinSDKVersion();
        if ((other == null) != (mine == null))
            return false;
        if (other != null) {
            if (!other.equals(mine))
                return false;
        }
        
        other = otherComponent.getMaxSDKVersion();
        mine = getMaxSDKVersion();
        if ((other == null) != (mine == null))
            return false;
        if (other != null) {
            if (!other.equals(mine))
                return false;
        }
        
        return true;
    }

    public MessageLocation createMessageLocation() {
        if (componentFileOrDir == null)
            return null;
        IPath path = new Path(componentFileOrDir.getAbsolutePath());
        return new MessageLocation(path, 0, 0);
    }
}
