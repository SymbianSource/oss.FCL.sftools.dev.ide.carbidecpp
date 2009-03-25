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
package com.nokia.sdt.datamodel.util;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.component.property.*;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.datamodel.images.*;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.IDisplayModel.LayoutAreaConfiguration;
import com.nokia.sdt.displaymodel.adapter.ILayoutContainer;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.sdt.editor.IComponentEditor;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.ObjectUtils;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.Collection;
import java.util.Iterator;

/**
 * Utilities relying only on abstract model APIs
 *
 */
public abstract class ModelUtils {
	
	/**
	 * Get an IComponentInstance adapter
	 */
	public static IComponentInstance getComponentInstance(EObject object) {
        if (object == null)
            return null;
		IComponentInstance result = (IComponentInstance) EcoreUtil.getRegisteredAdapter(
				object, IComponentInstance.class);
		return result;
	}
	
	public static ILayoutObject getLayoutObject(EObject object) {
        if (object == null)
            return null;
		ILayoutObject result = (ILayoutObject) EcoreUtil.getRegisteredAdapter(
				object, ILayoutObject.class);
		return result;
	}
	
	public static IComponentEditor getComponentEditor(EObject object) {
        if (object == null)
            return null;
        IComponentEditor result = (IComponentEditor) EcoreUtil.getRegisteredAdapter(
				object, IComponentEditor.class);
		return result;
	}
	
	public static ILayoutContainer getLayoutContainer(EObject object) {
		ILayoutContainer result = null;
		if (object != null) {
			result = (ILayoutContainer) EcoreUtil.getRegisteredAdapter(
				object, ILayoutContainer.class);
		}
		return result;
	}

	/**
	 * Get the data model for an EObject
	 */
	public static IDesignerDataModel getModel(EObject object) {
		IDesignerDataModel result = null;
		IComponentInstance ci = getComponentInstance(object);
		if (ci != null)
			result = ci.getDesignerDataModel();
		return result;
	}
	
	public static EObject getEObject(Object object) {
		EObject result = null;
		if (object instanceof EObject) {
			result = (EObject) object;
		}
		else if (object instanceof IModelAdapter) {
			result = ((IModelAdapter)object).getEObject();
		}
		return result;
	}
		
	/**
	 * Get the display model for an EObject. The
	 * display model will be created if it doesn't 
	 * already exist.
	 * @throws CoreException 
	 */
	public static IDisplayModel getDisplayModel(EObject object) throws CoreException {
        if (object == null)
            return null;
		IDisplayModel result = null;
		IDesignerDataModel model = getModel(object);
		IComponentInstance ci = getComponentInstance(object);
		if (model != null && ci != null) {
			EObject rootContainer = ci.getRootContainer();
			if (rootContainer != null) {
				result = model.getDisplayModelForRootContainer(rootContainer);
			}
		}
		return result;
	}
	
	/**
	 * Get the display model for an EObject, if it already
	 * exists. Returns null if no display model exists.
	 */
	public static IDisplayModel getExistingDisplayModel(EObject object) {
        if (object == null)
            return null;
		IDisplayModel result = null;
		IDesignerDataModel model = getModel(object);
		IComponentInstance ci = getComponentInstance(object);
		if (model != null && ci != null) {
			EObject rootContainer = ci.getRootContainer();
			if (rootContainer != null) {
				result = model.getExistingDisplayModelForRootContainer(rootContainer);
			}
		}
		return result;
	}

	/**
	 * Apply the named skin to the data model by locating the LayoutConfiguration 
	 * associated with the skin and setting that configuration
	 * @param skinName
	 * @return true if skin was found and applied, or was already the current skin
	 * @throws CoreException 
	 */
	public static boolean applySkinToDisplayModel(IDisplayModel displayModel, String skinName) throws CoreException {
		boolean result = false;
		LayoutAreaConfiguration currentConfiguration = displayModel.getCurrentConfiguration();
		if (currentConfiguration != null && currentConfiguration.getSkin().getDisplayName().equals(skinName)) {
			result = true;
		}
		else {
			Collection configurations = displayModel.getLayoutAreaConfigurations();
			if (configurations != null) {
				for (Iterator iter = configurations.iterator(); iter.hasNext();) {
					LayoutAreaConfiguration lc = (LayoutAreaConfiguration) iter.next();
					if (lc.getSkin().getDisplayName().equals(skinName)) {
						displayModel.setCurrentConfiguration(lc);
						result = true;
						break;
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * Return the first instance of the given component ID
	 * @param container - search begins with this component and recurses depth-first
	 * @param componentID - qualifed component name
	 * @param considerDerived - if true, accept components derived from the given ID.
	 * @return object
	 */
	public static EObject findFirstComponentInstance(EObject container, String componentID, boolean considerDerived) {
		EObject result = null;
		IComponentInstance containerCI = getComponentInstance(container);
		if (containerCI != null) {
			if (considerDerived) {
				if (isInstanceOf(container, componentID))
					result = container;
			}
			else if (componentID.equals(containerCI.getComponentId())) {
				result = container;
			}
			
			if (result == null) {
				EObject[] objects = containerCI.getChildren();
				if (objects != null) {
					for (int i = 0; i < objects.length; i++) {
						result = findFirstComponentInstance(objects[i], componentID, considerDerived);
						if (result != null)
							break;
					}
				}
			}
		}
		return result;
	}
	
	public static boolean isInstanceOf(EObject object, String componentID) {
		IComponentInstance ci = getComponentInstance(object);
		if (ci != null)
			return isOfType(ci.getComponent(), componentID);

		return false;
	}
		
	public static boolean isOfType(IComponent component, String componentID) {
		boolean result = false;
		while (component != null && !result) {
			if (component.getId().equals(componentID))
				result = true;
			else 
				component = component.getComponentBase();
		}
		return result;
	}
	
	/**
	 * Returns true if child is containe anywhere in parent. Returns
	 * false if child == parent or is not contained in parent.
	 */
	public static boolean isChildOf(EObject parent, EObject child) {
		boolean result = false;
		IComponentInstance childCI = getComponentInstance(child);
		if (childCI != null) {
			EObject currParent = childCI.getParent();
			while (currParent != null) {
				if (currParent == parent) {
					result = true;
					break;
				}
				IComponentInstance currParentCI = getComponentInstance(currParent);
				if (currParentCI != null ) {
					currParent = currParentCI.getParent();
				} else {
					currParent = null;
				}
			}
		}
		return result;
	}

	
	/**
	 * Returns the first instance of the given parent that has a component matching
	 * the given attribute name and value
	 */
	public static EObject findImmediateChildWithAttributeValue(EObject container, String attribute, String value) {
		Check.checkArg(attribute != null);
		Check.checkArg(value != null);
		EObject result = null;
		IComponentInstance containerCI = getComponentInstance(container);
		if (containerCI != null) {
			EObject[] children = containerCI.getChildren();
			if (children != null) {
				for (int i = 0; i < children.length; i++) {
					IComponentInstance childCI = getComponentInstance(children[i]);
					if (childCI != null) {
						IComponent component = childCI.getComponent();
						if (component != null) {
							IAttributes attr = (IAttributes) component.getAdapter(IAttributes.class);
							if (attr != null) {
								String currVal = attr.getAttribute(attribute);
								if (value.equals(currVal)) {
									result = children[i];
									break;
								}
							}
						}
					}
				}
			}
		}
		return result;
	}
    
    /**
     * Read the value of a property along the given node path.
     * @param instObj the component instance
     * @param propertyPath path to the property
     * @param resolveEditableValue true: reconcile compound property, false: return its property source
     * @return result with status and property value (.result)
     * @see NodePath
     */
    public static NodePathLookupResult readProperty(EObject instObj, String propertyPath, boolean resolveEditableValue) {
        NodePath path = new NodePath(propertyPath);
        IComponentInstance ci = ModelUtils.getComponentInstance(instObj);
        IPropertySource ps = (IPropertySource) EcoreUtil.getRegisteredAdapter(instObj, IPropertySource.class);
        return path.readFromPath(ci, ps, resolveEditableValue);
        
    }

    /**
     * Read the value of an attribute along the given node path.
     * @param instObj the component instance
     * @param nodePath path to the attribute, e.g. "@attribute" or "[parent]@attribute", ...
     * @return result with status and attribute value (.result)
     * @see NodePath
     */
    public static NodePathLookupResult readAttributeFromPath(EObject instObj, String nodePath) {
        NodePath path = new NodePath(nodePath);
        IComponentInstance ci = ModelUtils.getComponentInstance(instObj);
        return path.readFromPath(ci, null, false);
        
    }

    /**
	 * Read the value of an attribute from the given instance.
	 * @param instObj the component instance
	 * @param attributeName name to the attribute
	 * @return String or null if not defined
	 * @see NodePath
	 */
	public static String readAttribute(IComponent component, String attributeName) {
		if (component == null)
			return null;
		IAttributes attributes = (IAttributes) component.getAdapter(IAttributes.class);
		if (attributes == null)
			return null;
		return attributes.getAttribute(attributeName);
	}

    /**
	 * Read the value of an attribute from the given instance.
	 * @param instObj the component instance
	 * @param attributeName name to the attribute
	 * @return String or null if not defined
	 * @see NodePath
	 */
	public static String readAttribute(EObject instObj, String attributeName) {
		IComponentInstance componentInstance = getComponentInstance(instObj);
		if (componentInstance == null)
			return null;
		return readAttribute(componentInstance.getComponent(), attributeName);
	}

	/**
     * Read the value of a property along the given node path.
     * @param instObj the component instance
     * @param properties a property source on or inside instObj
     * @param propertyPath path to the property from (instObj, properties)
     * @param resolveEditableValue true: reconcile compound property, false: return its property source
     * @return result with status and property value (.result)
     * @see NodePath
     */
    public static NodePathLookupResult readProperty(EObject instObj, IPropertySource properties, String propertyPath, boolean resolveEditableValue) {
        NodePath path = new NodePath(propertyPath);
        IComponentInstance ci = ModelUtils.getComponentInstance(instObj);
        return path.readFromPath(ci, properties, resolveEditableValue);
    }

    /**
     * Write the value of a property along the given node path.
     * @param instObj the component instance
     * @param propertyPath path to the property from (instObj, properties)
     * @param newValue the new value for the property (note: reconcilable properties must be set with their editable value, not a IPropertySource)
     * @return null for success or error result 
     * @see NodePath
     */
    public static IStatus writeProperty(EObject instObj, String propertyPath, Object newValue) {
        NodePath path = new NodePath(propertyPath);
        IComponentInstance ci = ModelUtils.getComponentInstance(instObj);
        return path.writeToPath(ci, ModelUtils.getPropertySource(instObj), newValue);
    }

    /**
     * Write the value of a property along the given node path.
     * @param instObj the component instance
     * @param properties a property source on or inside instObj
     * @param propertyPath path to the property from (instObj, properties)
     * @param newValue the new value for the property (note: reconcilable properties must be set with their editable value, not a IPropertySource)
     * @return null for success or error result 
     * @see NodePath
     */
    public static IStatus writeProperty(EObject instObj, IPropertySource properties, String propertyPath, Object newValue) {
        NodePath path = new NodePath(propertyPath);
        IComponentInstance ci = ModelUtils.getComponentInstance(instObj);
        return path.writeToPath(ci, properties, newValue);
    }

    static public IComponentInstance lookupReference(IDesignerDataModel dataModel, String refId) {
        EObject referent = dataModel.findByNameProperty(refId);
        if (referent == null) {
            return null;
        }
        IComponentInstance refInstance = (IComponentInstance) EcoreUtil.getRegisteredAdapter(referent, IComponentInstance.class);
        if (refInstance == null) {
            return null;
        }
        return refInstance;
    }

    public static IPropertySource getPropertySource(EObject object) {
        if (object == null)
            return null;
        IPropertySource result = (IPropertySource) EcoreUtil.getRegisteredAdapter(
                object, IPropertySource.class);
        return result;
    }
    
    public static IPropertyInformation getPropertyInformation(EObject object) {
        if (object == null)
            return null;
        IPropertyInformation result = (IPropertyInformation) EcoreUtil.getRegisteredAdapter(
                object, IPropertyInformation.class);
        return result;
    }

    /**
     * Given a property source lookup a property descriptor by property ID
     */
    public static IPropertyDescriptor findPropertyDescriptor(IPropertySource ps, Object propertyId) {
    	IPropertyDescriptor result = null;
    	IPropertyDescriptor[] descriptors = ps.getPropertyDescriptors();
    	for (int i = 0; i < descriptors.length; i++) {
			if (descriptors[i].getId().equals(propertyId)) {
				result = descriptors[i];
				break;
			}
		}
    	return result;
    }
   
    public static boolean hasProperty(IPropertySource ps, Object propertyId) {
    	return findPropertyDescriptor(ps, propertyId) != null;
    }
    
    /**
     * Return either a simple property value, or, for a compound property, 
     * the editable value.
     * @param container the owner of the property
     * @param propertyId the property id
     */
    public static Object getEditablePropertyValue(EObject container, Object propertyId) {
    	Object result = null;
    	IPropertySource ps = getPropertySource(container);
    	Check.checkState(ps != null);
    	result = ps.getPropertyValue(propertyId);
    	if (result instanceof IPropertySource) {
    		result = ((IPropertySource)result).getEditableValue();
    	}
    	return result;
    }

    /**
     * Return either a simple property value, or, for a compound property, 
     * the editable value.
     * @param container the owner of the property
     * @param propertyPath the property path
     */
    public static Object getEditablePropertyValueFromPath(EObject container, String propertyPath) {
    	NodePathLookupResult result = readProperty(container, propertyPath, true);
    	return result.result;
    }

    /**
     * Get the property descriptor for a property identified by a node path. 
     * @param instObj the component instance
     * @param propertyPath path to the property
     * @param resolveEditableValue true: reconcile compound property, false: return its property source
     * @return IPropertyDescriptor or null
     */
    public static IPropertyDescriptor getPropertyPathDescriptor(EObject instObj, String propertyPath, boolean resolveEditableValue) {
        NodePath path = new NodePath(propertyPath);
        IComponentInstance ci = ModelUtils.getComponentInstance(instObj);
        IPropertySource ps = (IPropertySource) EcoreUtil.getRegisteredAdapter(instObj, IPropertySource.class);
        NodePathLookupResult result = path.readFromPath(ci, ps, resolveEditableValue);
        if (result != null)
        	return result.descriptor;
        else
        	return null;
        
    }
    
    /**
     * Get the component instance's event descriptor provider.
     */
    public static IComponentEventDescriptorProvider getComponentEventDescriptorProvider(EObject instance) {
    	return (IComponentEventDescriptorProvider) EcoreUtil.getRegisteredAdapter(instance, IComponentEventDescriptorProvider.class);
    }

    /**
     * Ger the IIntializer adapter
     * @param object
     * @return IInitializer
     */
    public static IInitializer getInitializerAdapter(EObject object) {
		IInitializer result = null;
		if (object != null) {
			result = (IInitializer) EcoreUtil.getRegisteredAdapter(
				object, IInitializer.class);
		}
		return result;
	}
    
    public static org.eclipse.emf.common.command.Command createSetPropertyValueCommand(EObject instance, Object propertyID, Object value) {
    	org.eclipse.emf.common.command.Command result = null;
    	
    	return result;
    	
    }
    
    /**
     * Get the root IComponentInstance starting from the given node.  This
     * makes no other checks on root-ness.
     * @param instance
     * @return top-most IComponentInstance
     */
    public static IComponentInstance getRootInstance(IComponentInstance instance) {
    	IComponentInstance root = instance;
        while (root != null && root.getParent() != null) {
            IComponentInstance tmp = (IComponentInstance) EcoreUtil.getRegisteredAdapter(
            		root.getParent(), IComponentInstance.class);
            if (tmp != null)
            	root = tmp; 
            else
                break;
        }
        return root;
    }
    
    /**
     * Return the last segment of a potentially dotted property path
     */
    public static String getLeafFromPath(String propertyPath) {
    	String result;
    	int lastDot = propertyPath.lastIndexOf(".");
    	if (lastDot >= 0) {
    		result = propertyPath.substring(lastDot + 1);
    	} else {
    		result = propertyPath;
    	}
    	return result;
    }
    
    /**
     * Translate a property path composed of internal property names
     * to use display names.
     * If any internal property names can't be resolved null is returned
     */
    public static String makeDisplayPropertyPath(EObject object, String propertyPath) {
    	StringBuffer buf = new StringBuffer();
    	String[] segments = propertyPath.split("\\.");
    	if (segments != null && segments.length > 0) {
    		IPropertySource currPS = getPropertySource(object);
    		for (int i = 0; i < segments.length; i++) {
    			String id = segments[i];
    			if (currPS != null) {
	    			IPropertyDescriptor pd = findPropertyDescriptor(currPS, id);
	    			if (pd != null) {
	    				if (currPS instanceof ISequencePropertySource) {
	    					buf.append('[');
	    					buf.append(pd.getDisplayName());
	    					buf.append(']');
	    				}
	    				else {
	    					if (i > 0) {
	    						buf.append('.');
	    					}
	    					buf.append(pd.getDisplayName());
	    				}
	    				Object currValue = currPS.getPropertyValue(id);
	    				if (currValue instanceof IPropertySource) {
	    					currPS = (IPropertySource) currValue;
	    				} else {
	    					currPS = null;
	    				}
	    			}
	    			else {
	    				// couldn't resolve the path element to a property
	    				return null;
	    			}
    			} else {
    				// more path elements than compound properties
    				return null;
    			}
    		}
    	}
    	return buf.toString();
    }
    
    public static String getPropertyNameFromPropertyPath(String propertyPath) {
    	int lastSeparator = propertyPath.lastIndexOf('.');
    	if ((lastSeparator == -1) || (propertyPath.length() == (lastSeparator + 1)))
    		return propertyPath;
    	
    	return propertyPath.substring(lastSeparator + 1);
    }

	public static IChildCommandExtender getChildCommandExtender(EObject object) {
        if (object == null)
            return null;
        IChildCommandExtender result = (IChildCommandExtender) 
        	EcoreUtil.getRegisteredAdapter(object, IChildCommandExtender.class);
		return result;
	}

	public static IClipboardCommandExtender getClipboardCommandExtender(EObject object) {
        if (object == null)
            return null;
        IClipboardCommandExtender result = (IClipboardCommandExtender) 
        	EcoreUtil.getRegisteredAdapter(object, IClipboardCommandExtender.class);
		return result;
	}

	public static IModelUpdater getModelUpdater(EObject object) {
        if (object == null)
            return null;
        IModelUpdater result = (IModelUpdater) 
        	EcoreUtil.getRegisteredAdapter(object, IModelUpdater.class);
		return result;
	}

	/** Tell whether two EObjects refer to the same component instance. */
	public static boolean isSameComponentInstance(EObject obj1, EObject obj2) {
		IComponentInstance c1 = getComponentInstance(obj1);
		IComponentInstance c2 = getComponentInstance(obj2);
		if (ObjectUtils.equals(c1, c2))
			return true;
		if (c1 != null && c2 != null)
			return c1.getName().equals(c2.getName());
		return false;
	}

	public static boolean booleanAttribute(IComponent component, String key) {
		String attribute = readAttribute(component, key);
		return Boolean.valueOf(attribute).booleanValue();
	}

	/**
     * Get the project image info for the project owning this object.
     * @param object an object in a model
     */
	public static IProjectImageInfo getProjectImageInfo(EObject object) {
        IProjectImageInfoProvider provider = (IProjectImageInfoProvider) EcoreUtil.getRegisteredAdapter(object, IProjectImageInfoProvider.class);
        if (provider == null)
            return null;
        return provider.getProjectImageInfo();
    }

	/**
	 * Get the compound property converter for the given property.
	 * @param object 
	 * @param ps
	 * @return image property converter, or null if not an image property
	 */
	public static ICompoundPropertyValueConverter getCompoundPropertyValueConverter(
			EObject object, IPropertySource ps) {
		if (ps instanceof IPropertyInformation) {
			ICompoundPropertyValueConverter converter = ((IPropertyInformation) ps).getCompoundPropertyValueConverter();
			return converter;
		}
		return null;
	}
	
	/**
	 * Get image property info for the given image property source.
	 * @param object
	 * @param ps
	 * @return null if not an image property
	 */
	public static IImagePropertyInfo getImagePropertyInfo(
			EObject object, IPropertySource ps) {
		ICompoundPropertyValueConverter converter = getCompoundPropertyValueConverter(object, ps);
		if (converter == null)
			return null;
		
		Object info = converter.getEditableValue(object, ps);
		if (info instanceof IImagePropertyInfo)
			return ((IImagePropertyInfo) info);
		
		return null;
	}
	
	/**
	 * Get multi-image property info for the given image property source.
	 * @param object
	 * @param ps
	 * @return null if not a multi-image property
	 */
	public static IMultiImagePropertyInfo getMultiImagePropertyInfo(
			EObject object, IPropertySource ps) {
		ICompoundPropertyValueConverter converter = getCompoundPropertyValueConverter(object, ps);
		if (converter == null)
			return null;
		
		Object info = converter.getEditableValue(object, ps);
		if (info instanceof IMultiImagePropertyInfo)
			return ((IMultiImagePropertyInfo) info);
		
		return null;
	}

}
