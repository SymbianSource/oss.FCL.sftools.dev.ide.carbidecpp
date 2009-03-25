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
package com.nokia.sdt.component.symbian.scripting;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.component.symbian.displaymodel.Utilities;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.displaymodel.adapter.ILayoutContainer;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.sdt.editor.EditorServices;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.sourcegen.contributions.SourceGenGlobals;
import com.nokia.sdt.uidesigner.ui.utils.EditorUtils;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.views.properties.IPropertySource;
import org.mozilla.javascript.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * This class wraps IComponentInstance for Javascript
 * 
 * 
 * @see com.nokia.sdt.datamodel.adapter.IComponentInstance
 */
public class WrappedInstance extends ScriptableObject implements IComponentInstance /*implements Wrapper*/ {

    private static final long serialVersionUID = 1L;
    private static final EObject[] EMPTY_EOBJECT_ARRAY = new EObject[0];
    
    IComponentInstance instance;
    private IPropertySource propertySource; // may be lazily initialized
    private IComponent component;
    private IAttributes attributes;
    private WrappedEventBindings bindings;
    
    /** 
     * This constructor is needed to prevent Rhino from barfing on our Java-only constructors --
     * don't call this directly!
     */
    public WrappedInstance() {
    }
    
    // needed to prevent Rhino from barfing on our Java-only constructors
    public void jsConstructor() {
    }
    
    public void init() {
        // for foo.properties.value
        ScriptableObject.putProperty(this, "properties", jsGet_properties()); //$NON-NLS-1$
        ScriptableObject.putProperty(this, "attributes", jsGet_attributes()); //$NON-NLS-1$
        ScriptableObject.putProperty(this, "componentId", jsGet_componentId()); //$NON-NLS-1$
        //ScriptableObject.putProperty(this, "component", jsGet_component()); //$NON-NLS-1$
        ScriptableObject.putProperty(this, "events", jsGet_events()); //$NON-NLS-1$
        
        try {
	        Class cls = getClass();
	        int propertyFlags = ScriptableObject.READONLY|ScriptableObject.PERMANENT;
	        Method getterMethod = cls.getMethod("jsGet_children", (Class[])null);//$NON-NLS-1$
	        defineProperty("children", null, getterMethod, null, propertyFlags);//$NON-NLS-1$
	        getterMethod = cls.getMethod("jsGet_parent", (Class[])null);//$NON-NLS-1$
	        defineProperty("parent", null, getterMethod, null, propertyFlags);//$NON-NLS-1$
            getterMethod = cls.getMethod("jsGet_instanceName", (Class[])null);//$NON-NLS-1$
            defineProperty("name", null, getterMethod, null, propertyFlags);//$NON-NLS-1$
            getterMethod = cls.getMethod("jsGet_instanceMemberName", (Class[])null);//$NON-NLS-1$
            defineProperty("memberName", null, getterMethod, null, propertyFlags);//$NON-NLS-1$
            getterMethod = cls.getMethod("jsGet_className", (Class[])null);//$NON-NLS-1$
            defineProperty("className", null, getterMethod, null, propertyFlags);//$NON-NLS-1$
            getterMethod = cls.getMethod("jsGet_component", (Class[])null);//$NON-NLS-1$
            defineProperty("component", null, getterMethod, null, propertyFlags);//$NON-NLS-1$
            getterMethod = cls.getMethod("jsGet_rootContainer", (Class[])null);//$NON-NLS-1$
            defineProperty("rootContainer", null, getterMethod, null, propertyFlags);//$NON-NLS-1$
            getterMethod = cls.getMethod("jsGet_EObject", (Class[])null);//$NON-NLS-1$
            defineProperty("EObject", null, getterMethod, null, propertyFlags);//$NON-NLS-1$
       }
        catch (NoSuchMethodException x) {
        	// shouldn't happen
        	ComponentSystemPlugin.log(x);
        }

        
        defineFunctionProperties(new String[] { 
                "toString",             // for printing foo 
                "findChildrenOfType",   // for locating specific children by component id
                "findChildOfType",      // ditto, but only one
                "forceLayout",          // force layout of self
                "forceRedraw",          // force redraw of self
                "isInstanceOf",			// is instance of component type
                "updatePropertySource",  // force rebuild of property list for extension properties
                "getRenderingBounds",	// get the bounds for rendering, with x=y=0
                "getLayoutBounds",		// get the layout bounds
                "setLayoutBounds",		// set the layout bounds
                "getPreferredSize",		// get the preferred size from ILayout or IVisualAppearance
                "getVisibleChildren",	// get the visible children of a container (only one if single vis child container)
                }, WrappedInstance.class, ScriptableObject.READONLY); //$NON-NLS-1$
    }
    
    private IComponent getComponent(IComponentInstance componentInstance) {
        IComponentSet componentSet = componentInstance.getDesignerDataModel().getComponentSet();
        Check.checkContract(componentSet != null);
        return componentSet.lookupComponent(componentInstance.getComponentId());
    }
    
    
    /**
     * Create a Rhino wrapper for the instance and properties.
     * 
     * Do not call this from client code!
     * 
     * @param instance
     * @param propertySource
     * @see ComponentWrappers#getWrappedInstance(IComponentInstance, IPropertySource)
     */
    WrappedInstance(IComponentInstance instance, IPropertySource propertySource) {
        this();
        this.instance = instance;
        this.propertySource = propertySource;
        this.component = getComponent(instance);
        this.attributes = component != null ?(IAttributes) component.getAdapter(IAttributes.class) : null;
        this.bindings = new WrappedEventBindings(instance);
    }

    public String getClassName() {
        return "Instance"; //$NON-NLS-1$
    }
    
    public String jsGet_instanceName() {
        return instance.getName();
    }
    
    public String jsGet_instanceMemberName() {
        return "i" + SourceGenGlobals.titleCase(instance.getName());
    }

    public String jsGet_className() {
    	String result = null;
    	initPropertySource();
    	if (propertySource != null) {
    		result = (String) propertySource.getPropertyValue("className");
    	}
        return result;
    }
    
    private IPropertySource initPropertySource() {
    	if (propertySource == null) {
    		propertySource = ModelUtils.getPropertySource(instance.getEObject());
    	}
    	return propertySource;
    }

    public WrappedInstance jsGet_parent() {
    	WrappedInstance result = null;
        EObject parent = instance.getParent();
        if (parent != null && ModelUtils.getComponentInstance(parent) != null) {
           result = ComponentWrappers.getInstance(instance).getWrappedInstance(parent);
        }
        return result;
    }

    public WrappedInstance[] jsGet_children() {
        EObject[] children = instance.getChildren();
        if (children == null)
            children = EMPTY_EOBJECT_ARRAY;
        return ComponentWrappers.getInstance(instance).getWrappedInstanceArray(children);
    }

    /**
     * Find children with the given component id 
     * @param componentId
     * @return children matching the type, never null
     */
    public WrappedInstance[] findChildrenOfType(String componentId) {
        
        EObject[] children = instance.getChildren();
        if (children == null)
            children = EMPTY_EOBJECT_ARRAY;
        List kids = new ArrayList();
        for (int i = 0; i < children.length; i++) {
            if (ModelUtils.isInstanceOf(children[i], componentId))
                kids.add(children[i]);
        }
        return ComponentWrappers.getInstance(instance).getWrappedInstanceArray((EObject[]) kids.toArray(new EObject[kids.size()]));
    }

    /**
     * Find a child with the given component id 
     * @param componentId
     * @return child matching the type, or null
     */
    public WrappedInstance findChildOfType(String componentId) {
        WrappedInstance[] kids = findChildrenOfType(componentId);
        if (kids.length > 0)
            return kids[0];
        else
            return null;
    }
    
	public boolean isInstanceOf(String componentID) {
		return ModelUtils.isInstanceOf(instance.getEObject(), componentID);
	}

    // for foo.properties["value"]
    public WrappedProperties jsGet_properties() {
    	initPropertySource();
        return propertySource != null ? ComponentWrappers.getInstance(instance).getWrappedProperties(propertySource) : null;
    }

    public WrappedAttributes jsGet_attributes() {
        return attributes != null ? ComponentWrappers.getInstance().getWrappedAttributes(attributes) : null;
    }
    
    public String jsGet_componentId() {
    	return instance.getComponentId();
    }

    public WrappedComponent jsGet_component() {
        return component != null ? ComponentWrappers.getInstance().getWrappedComponent(component) : null;
    }
    
    public Object jsGet_events() {
        return bindings; 
    }
    
    public WrappedInstance jsGet_rootContainer() {
    	WrappedInstance result = null;
        EObject rootContainer = instance.getRootContainer();
        if (rootContainer != null && ModelUtils.getComponentInstance(rootContainer) != null) {
           result = ComponentWrappers.getInstance(instance).getWrappedInstance(rootContainer);
        }
        return result;
    }
    
    public EObject jsGet_EObject() {
    	return getEObject();
    }
    
    /** Return copy of layout bounds for the object.  This has x and y coordinates indicating the location
     * relative to the parent.
     * @return a Rectangle (Object here because we don't want to wrap it) */
    public Object getLayoutBounds() {
    	ILayoutObject layoutObject = ModelUtils.getLayoutObject(getEObject());
    	if (layoutObject == null)
    		return null;
    	return Context.javaToJS(layoutObject.getBounds(), this);
    }

    /** Return copy of rendering bounds for the object.  This  is the layout bounds
     * with x and y coordinates fixed to 0 to match the configuration of the GC.
     * @return a Rectangle (Object here because we don't want to wrap it) */
    public Object getRenderingBounds() {
    	ILayoutObject layoutObject = ModelUtils.getLayoutObject(getEObject());
    	if (layoutObject == null)
    		return null;
    	Rectangle rect = layoutObject.getBounds();
    	return Context.javaToJS(new Rectangle(0, 0, rect.width, rect.height),
    			this);
    }

    /** Set bounds for the object, which are parent-relative coordinates
     * from the top-left corner. 
     * @param rectangle a Rectangle instance (Object here to avoid wrapping it) */
    public void setLayoutBounds(Object rectangle) {
    	ILayoutObject layoutObject = ModelUtils.getLayoutObject(getEObject());
    	if (layoutObject == null)
    		Context.throwAsScriptRuntimeEx(new UnsupportedOperationException("This object does not have layout bounds: " + getEObject()));
    	if (rectangle instanceof Wrapper)
    		rectangle = ((Wrapper) rectangle).unwrap();
    	layoutObject.setBounds((Rectangle)rectangle);
    }
    
    private ILookAndFeel getLAF() {
		IDisplayModel displayModel = ModelUtils.getExistingDisplayModel(getEObject());
		if (displayModel != null)
			return displayModel.getLookAndFeel();
    	
    	return null;
    }
    
    /**
     * Tries to get the preferred size first from ILayout, then from IVisualAppearance
     * @param wHint int
     * @param hHint int
     * @return Point the preferred size or null if not fully implemented
     */
    public Object getPreferredSize(int wHint, int hHint) {
    	ILayout layout = (ILayout) EcoreUtil.getRegisteredAdapter(getEObject(), ILayout.class);
    	if (layout != null)
			return layout.getPreferredSize(wHint, hHint, getLAF());

    	IVisualAppearance visual = 
    		(IVisualAppearance) EcoreUtil.getRegisteredAdapter(getEObject(), IVisualAppearance.class);
    	if (visual != null)
    		return visual.getPreferredSize(wHint, hHint, getLAF());
    	
    	return null;
    }
    
    public WrappedInstance[] getVisibleChildren() {
    	ILayoutContainer container = Utilities.getLayoutContainer(getEObject());
    	if (container != null) {
    		List<WrappedInstance> list = new ArrayList<WrappedInstance>();
    		EObject[] objects = container.getVisibleChildren();
    		for (EObject object : objects) {
				WrappedInstance wrappedInstance = 
					ComponentWrappers.getInstance(instance).getWrappedInstance(object);
				list.add(wrappedInstance);
			}
    		return (WrappedInstance[]) list.toArray(new WrappedInstance[list.size()]);
    	}
    	
    	return new WrappedInstance[0];
    }
    
    /* Needed to convert WrappedInstance to IComponentInstance */
    public Object unwrap() {
        return instance;
    }
    
    public String toString() {
        return instance.getComponentId() + ":'" + instance.getName() + "'"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    
    /**
     * Use ILayout directly because this may be called on transient objects, rather than containers
     * Usually, it would be best to call ILayoutContainer.layoutChildren();
     */
    public void forceLayout() {
        IDisplayModel displayModel = null;
        try {
            displayModel = ModelUtils.getDisplayModel(instance.getEObject());
        } catch (CoreException e) {
            Context.throwAsScriptRuntimeEx(e);
        }
        ILayout layout = (ILayout) EcoreUtil.getRegisteredAdapter(instance.getEObject(), ILayout.class);
        if (displayModel != null && layout != null)
            layout.layout(displayModel.getLookAndFeel());
    }
    
    public void forceRedraw() {
    	ILayoutObject layoutObject = Utilities.getLayoutObject(instance.getEObject());
    	if (layoutObject != null)
    		layoutObject.forceRedraw();
    	ILayoutContainer layoutContainer = Utilities.getLayoutContainer(instance.getEObject());
    	if (layoutContainer != null) {
    		if (layoutContainer.scrollsHorizontally() || layoutContainer.scrollsVertically()) {
    			IEditorPart editorPart = EditorServices.findEditor(getDesignerDataModel());
    			if (editorPart == null)
    				return;
    			IDesignerEditor designerEditor = 
    				(IDesignerEditor) editorPart.getAdapter(IDesignerEditor.class);
    			if (designerEditor != null && designerEditor.getUpperGraphicalViewer() != null) {
	    			GraphicalEditPart editPart = 
	    				(GraphicalEditPart) EditorUtils.getPrimaryEditPartForObject(designerEditor, instance.getEObject());
	    			IFigure figure = editPart.getFigure();
					figure.invalidate();
					figure.validate();
    			}
    		}
    	}
    }
    
    //////////////////////////////////
    
	public String getComponentId() {
		return instance.getComponentId();
	}

	public void setComponentId(String componentID) {
		instance.setComponentId(componentID);
	}

	public IComponent getComponent() {
		return instance.getComponent();
	}

	public String getName() {
		return instance.getName();
	}

	public String validateProposedName(String newName) {
		return instance.validateProposedName(newName);
	}

	public EObject getParent() {
		return instance.getParent();
	}

	public EObject[] getChildren() {
		return instance.getChildren();
	}

	public void addChildListener(IComponentInstanceChildListener listener) {
		instance.addChildListener(listener);
	}

	public void addPropertyListener(IComponentInstancePropertyListener listener) {
		instance.addPropertyListener(listener);
	}

	public void removeChildListener(IComponentInstanceChildListener listener) {
		instance.removeChildListener(listener);
	}

	public void removePropertyListener(IComponentInstancePropertyListener listener) {
		instance.removePropertyListener(listener);
	}

	public IDesignerDataModel getDesignerDataModel() {
		return instance.getDesignerDataModel();
	}

	public EObject getRootContainer() {
		return instance.getRootContainer();
	}

	public EObject getEObject() {
		return instance.getEObject();
	}

	public void addEventBindingListener(IEventBindingListener listener) {
		instance.addEventBindingListener(listener);
	}

	public IEventBinding[] getEventBindings() {
		return instance.getEventBindings();
	}

	public IEventBinding findEventBinding(String eventID) {
		return instance.findEventBinding(eventID);
	}

	public void removeEventBindingListener(IEventBindingListener listener) {
		instance.removeEventBindingListener(listener);
	}

	public void updatePropertySource() {
		instance.updatePropertySource();
	}

	public boolean isConfigured() {
		return instance.isConfigured();
	}
	
	public void firePropertyChanged(Object id) {
		instance.firePropertyChanged(id);
	}

	public void fireChildAdded(EObject child) {
		instance.fireChildAdded(child);
	}
}
