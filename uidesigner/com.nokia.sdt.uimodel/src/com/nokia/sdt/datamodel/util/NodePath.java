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

package com.nokia.sdt.datamodel.util;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This path supports iteration through property sources,
 * child instances, and component references to locate
 * a property or instance.
 * 
 * 
 */
public class NodePath {

     class NodePathState {
        /** original instance */
        IComponentInstance startInstance;
        /** current instance */
        IComponentInstance instance;
        /** current properties */
        IPropertySource properties;
        /** current property id */
        String propertyId;
        /** current property descriptor */
        IPropertyDescriptor propertyDescriptor;
        /** current attribute name */
        String attributeName;
    }
    
    interface NodeEntry {
        /** 
         * Modify the state by walking this node
         * with respect to the current instance and property source. 
         * @param state
         * @return null if successful, else a failure result
         */
        NodePathLookupResult walk(NodePathState state);
    }

    /**
     * Reference a property
     * 
     *
     */
    static class RefPropertyEntry implements NodeEntry {
        private String propertyName;
        RefPropertyEntry(String name) {
            this.propertyName = name;
        }
        public NodePathLookupResult walk(NodePathState state) {
            Check.checkState(state.propertyId == null);
            IPropertyDescriptor[] descs = state.properties.getPropertyDescriptors();
            if (descs != null) for (int i = 0; i < descs.length; i++) {
                if (descs[i].getId().equals(propertyName)) {
                    state.propertyId = propertyName;
                    state.propertyDescriptor = descs[i];
                    return null;
                }
            }
            return new NodePathLookupResult("ModelUtils.MissingProperty",  //$NON-NLS-1$
                    new String[] { propertyName, state.instance.getComponentId() });
        }
    }

    /**
     * Represents the dereference of a compound property to
     * set a new property source.
     * 
     *
     */
    class ReadCompoundPropertyEntry implements NodeEntry {
        ReadCompoundPropertyEntry() {
        }
        
        public NodePathLookupResult walk(NodePathState state) {
            // already in a compound property?
            if (state.propertyId == null && state.properties != null)
                return null;
            
            Check.checkState(state.instance != null && state.propertyId != null);
            
            // descend into property source
            NodePathLookupResult result = readProperty(state, state.propertyId);
            if (result.status != null)
                return result;
            
            if (!(result.result instanceof IPropertySource)) {
                return new NodePathLookupResult("ModelUtils.IllegalPropertyPath",  //$NON-NLS-1$
                        new String[] { thePropertyPath, 
                                state.instance.getName(), state.instance.getComponentId(), 
                                state.startInstance.getName(), state.startInstance.getComponentId() });
            }

            state.properties = (IPropertySource) result.result;
            state.propertyId = null;
            state.propertyDescriptor = null;
            return null;
        }
    }

    /**
     * Represents the indirection of a component reference to
     * find a new instance and property.
     * 
     *
     */
    class ReadReferenceEntry implements NodeEntry {
        ReadReferenceEntry() {
        }
        public NodePathLookupResult walk(NodePathState state) {
            Check.checkState(state.instance != null);

            // read value
            NodePathLookupResult result = readProperty(state, state.propertyId);
            if (result.status != null)
                return result;

            // indirect a referenced property
            IComponentInstance instance = ModelUtils.lookupReference(state.instance.getDesignerDataModel(), 
                    result.result.toString());
            IPropertySource properties = null;
            if (instance != null)
                properties = ModelUtils.getPropertySource(instance.getEObject()); 
            if (properties == null) {
                return new NodePathLookupResult("ModelUtils.UnresolvedReference", //$NON-NLS-1$
                        new String[] { thePropertyPath, 
                        state.instance.getName(), state.instance.getComponentId() ,
                        state.startInstance.getName(), state.startInstance.getComponentId() });
            }
            
            state.instance = instance;
            state.properties = properties;
            state.propertyId = null;
            state.propertyDescriptor = null;
            
            return null;
        }
    }

    /**
     * Represents finding a child derived from the given 
     * component id and updating the instance and property source.
     * 
     *
     */
    class FindChildOfTypeEntry implements NodeEntry {
        private String componentId;

        public FindChildOfTypeEntry(String componentId) {
            this.componentId = componentId;
        }
        
        public NodePathLookupResult walk(NodePathState state) {
            Check.checkState(state.instance != null);
            
            IComponentInstance instance = ModelUtils.getComponentInstance(
                    ModelUtils.findFirstComponentInstance(state.instance.getEObject(), 
                            componentId, true));
            IPropertySource properties = null;
            if (instance != null)
                properties = ModelUtils.getPropertySource(instance.getEObject()); 
            if (instance == null || properties == null) {
                return new NodePathLookupResult("ModelUtils.NoSuchChildOfType", //$NON-NLS-1$
                        new String[] { componentId, thePropertyPath, 
                        state.instance.getName(), state.instance.getComponentId() ,
                        state.startInstance.getName(), state.startInstance.getComponentId() });
            }
            
            state.instance = instance;
            state.properties = properties;
            state.propertyId = null;
            state.propertyDescriptor = null;
            
            return null;
        }
    }

    /**
     * Represents finding a child derived from the given 
     * component id and updating the instance and property source.
     * 
     *
     */
    class GoToParentEntry implements NodeEntry {
        public GoToParentEntry() {
        }
        
        public NodePathLookupResult walk(NodePathState state) {
             Check.checkState(state.instance != null);
            
            IComponentInstance instance = null;
            if (state.instance.getEObject() != null)
                instance = ModelUtils.getComponentInstance(state.instance.getParent());
            IPropertySource properties = null;
            if (instance != null)
                properties = (IPropertySource) EcoreUtil.getRegisteredAdapter(instance.getEObject(), IPropertySource.class); 
            if (instance == null || properties == null) {
                return new NodePathLookupResult("ModelUtils.NoParent", //$NON-NLS-1$
                        new String[] { thePropertyPath, 
                        state.instance.getName(), state.instance.getComponentId() ,
                        state.startInstance.getName(), state.startInstance.getComponentId() });
            }
            
            state.instance = instance;
            state.properties = properties;
            state.propertyId = null;
            state.propertyDescriptor = null;
            
            return null;
        }
    }

    public static class RefAttributeEntry implements NodeEntry {
        private String attributeName;
        
        RefAttributeEntry(String name) {
            this.attributeName = name;
        }
        
        public NodePathLookupResult walk(NodePathState state) {
            Check.checkState(state.attributeName == null);
        	IComponent component = state.instance.getComponent();
        	if (component != null) {
	        	IAttributes attributes = (IAttributes) component.getAdapter(IAttributes.class);
	        	Check.checkContract(attributes != null);
	            if (attributes != null) {
	                if (attributes.isAttributeDefined(attributeName)) {
	                    state.attributeName = attributeName;
	                    return null;
	                }
	            }
        	}
        	return new NodePathLookupResult("ModelUtils.MissingAttribute", //$NON-NLS-1$
                    new String[] { attributeName, state.instance.getComponentId() });
        }
    }
    
	private NodePathLookupResult readProperty(NodePathState state, String propertyName) {
        Object property = state.properties.getPropertyValue(propertyName);
        if (property == null) {
            // TODO: this should probably be fixed in the component system --
            // IPropertySource#getPropertyValue() should never return null for known properties
            if (state.properties.isPropertySet(propertyName))
                return new NodePathLookupResult("ModelUtils.InvalidPropertyValue",  //$NON-NLS-1$
                        new String[] { propertyName, state.instance.getName(), state.instance.getComponentId() });
            else
                return new NodePathLookupResult("ModelUtils.MissingProperty",  //$NON-NLS-1$
                        new String[] { propertyName, state.instance.getComponentId() });
        }
        
        return new NodePathLookupResult(state.instance, state.properties, state.propertyDescriptor, state.propertyId, property);
    }
	
    private NodePathLookupResult readAttribute(NodePathState state, String attributeName) {
    	IComponent component = state.instance.getComponent();
    	IAttributes attributes = (IAttributes) component.getAdapter(IAttributes.class);
    	Check.checkContract(attributes != null);
        String value = attributes.getAttribute(state.attributeName);
        if (value == null)
        	return new NodePathLookupResult("ModelUtils.MissingAttribute", //$NON-NLS-1$
                    new String[] { state.attributeName, state.instance.getComponentId() });
        return new NodePathLookupResult(state.instance, null, null, null, value);
	}

    private IStatus writeProperty(NodePathState state, String propertyName, Object value) {
        IPropertyDescriptor[] descs = state.properties.getPropertyDescriptors();
        for (int i = 0; i < descs.length; i++) {
            if (descs[i].getId().equals(propertyName)) {
                state.properties.setPropertyValue(propertyName, value);
                return null;
            }
        }
        return NodePathLookupResult.makeStatus("ModelUtils.MissingProperty", //$NON-NLS-1$
                new String[] { propertyName, state.instance.getComponentId() });
    }


    private List entries;
    private String thePropertyPath;

    /**
     * Construct a node path with the given property path.<p>
     * The syntax is:<p>
     * <pre>
     * path := "." | path-element [ dot-or-arrow-or-at path ]<br>
     * path-element := read-property | find-child | goto-parent<br>
     * read-property := &lt;property name&gt; (find property of the given name)<br>
     * find-child := "[" &lt;component id&gt; "]" (find child component with given id)<br>
     * goto-parent := "[parent]" (find parent)<br>
     * dot-or-arrow-or-at := "." (enter compound property) | "->" (dereference reference property) | "@" (attribute) <br>
     * </pre> 
     */
    public NodePath(String propertyPath) {
        Check.checkArg(propertyPath);
        this.thePropertyPath = propertyPath;
        this.entries = new ArrayList();
    }
    
    /**
     * Compile the path, given the component id which hosted it
     * (for error tracking purposes)
     * @param contextComponentId the component id which contains the path reference
     * @return null for success, else error result
     */
    private NodePathLookupResult compile(String contextComponentId) {
        entries.clear();
        
        // early exit: current instance
        if (thePropertyPath.equals(".")) //$NON-NLS-1$
            return null;
        
        String tmpPath = thePropertyPath;
        Pattern pattern = Pattern.compile(
               //123               4   5         6          7 8
                "((([A-Za-z0-9_]+)|(\\[(.*?)\\]))(\\.|->)?)|(@([A-Za-z0-9_\\-]+))"); //$NON-NLS-1$
        Matcher matcher = pattern.matcher(thePropertyPath);
        matcher.reset(tmpPath);
        while (matcher.lookingAt()) {
            String group;
            if (matcher.group(1) != null) {
	            if ((group = matcher.group(3)) != null) {
	                entries.add(new RefPropertyEntry(group));
	            } else {
	                group = matcher.group(5);
	                if (group.equals("parent")) {
	                    entries.add(new GoToParentEntry());
	                } else {
	                    entries.add(new FindChildOfTypeEntry(group));
	                }
	            }
	            
	            if ((group = matcher.group(6)) != null) {
	                boolean indirect = group.equals("->"); //$NON-NLS-1$
	                if (indirect) {
	                    entries.add(new ReadReferenceEntry());
	                } else {
	                    entries.add(new ReadCompoundPropertyEntry());
	                }
	            }
            }
            else { // if ((group = matcher.group(7)) != null)
           		entries.add(new RefAttributeEntry(matcher.group(8)));
            }
            
            tmpPath = tmpPath.substring(matcher.end());
            matcher.reset(tmpPath);
        }
    
        if (tmpPath.length() != 0) {
            return new NodePathLookupResult("ModelUtils.InvalidPropertyPath",  //$NON-NLS-1$
                    new String[] { thePropertyPath, contextComponentId });
        }

        return null;
    }

    /**
     * Read the property from the given instance and property source. 
     * @param instance current instance
     * @param properties current property source 
     * @param resolveEditableValue true: get the editable value if a compound property results, false: leave result alone 
     * @return lookup result 
     */
    public NodePathLookupResult readFromPath(IComponentInstance instance, IPropertySource properties, boolean resolveEditableValue) {
        Check.checkArg(instance);
        
        NodePathLookupResult result;

        result = compile(instance.getComponentId());
        if (result != null)
            return result;

        NodePathState state = new NodePathState();
        state.startInstance = state.instance = instance;
        state.properties = properties;
        state.propertyId = null;
        state.propertyDescriptor = null;
        state.attributeName = null;
        
        for (Iterator iter = entries.iterator(); iter.hasNext();) {
            NodeEntry entry = (NodeEntry) iter.next();
            result = entry.walk(state);
            if (result != null)
                return result;
        }
        
        // read the value
        Object propertyValue;
        if (state.propertyId != null) {
            result = readProperty(state, state.propertyId);
            if (result.status != null)
                return result;
            
            propertyValue = result.result;
        } else if (state.attributeName != null){
        	return readAttribute(state, state.attributeName);
        } else {
            propertyValue = state.properties;
        }
        
        // check for properties whose editable values are simpler
        if (resolveEditableValue && propertyValue instanceof IPropertySource) {
            return new NodePathLookupResult(state.instance, state.properties, state.propertyDescriptor, 
            		state.propertyId, ((IPropertySource) propertyValue).getEditableValue());
        }

        return new NodePathLookupResult(state.instance, state.properties, state.propertyDescriptor, state.propertyId, propertyValue);
    }

    /**
     * Write the property to the given instance and property source. 
     * @param instance current instance
     * @param properties current property source relative to instance
     * @param newValue the new value for the property 
     * @return null for success or error IStatus
     */
    public IStatus writeToPath(IComponentInstance instance, IPropertySource properties, Object newValue) {
        Check.checkArg(instance);
        Check.checkArg(properties);
        
        NodePathLookupResult result;

        result = compile(instance.getComponentId());
        if (result != null)
            return result.status;

        NodePathState state = new NodePathState();
        state.startInstance = state.instance = instance;
        state.properties = properties;
        state.propertyId = null;
        
        for (Iterator iter = entries.iterator(); iter.hasNext();) {
            NodeEntry entry = (NodeEntry) iter.next();
            result = entry.walk(state);
            if (result != null)
                return result.status;
        }
        
        if (state.attributeName != null) {
            return NodePathLookupResult.makeStatus("ModelUtils.CannotWriteAttribute", //$NON-NLS-1$
                    new String[] { state.attributeName, 
                    state.instance.getName(), state.instance.getComponentId() ,
                    state.startInstance.getName(), state.startInstance.getComponentId() });
        }
        
        // write the value
        if (state.propertyId == null) {
            return NodePathLookupResult.makeStatus("ModelUtils.CannotWriteCompoundProperty", //$NON-NLS-1$
                    new String[] { thePropertyPath, 
                    state.instance.getName(), state.instance.getComponentId() ,
                    state.startInstance.getName(), state.startInstance.getComponentId() });
        }
        return writeProperty(state, state.propertyId, newValue);
    }
    
}



    