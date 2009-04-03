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
package com.nokia.sdt.datamodel.adapter;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.datamodel.IDesignerDataModel;

import org.eclipse.emf.ecore.EObject;


	/**
	 * Adapter for objects that represent model objects
	 * reflecting "instantiated" components. Not every
	 * object in a model needs to support IComponentInstance, 
	 * but every object supported by the UI designer is
	 * assumed to support it.
	 * 
	 */
public interface IComponentInstance extends IModelAdapter {

	/**
	 * Returns the component ID for the componenent associated
	 * with this model object. Should always return a valid
	 * component id. However, the component set for the model
	 * may not contain a component for each id, i.e. components
	 * may be missing.
	 */
	String	getComponentId();
	
	/**
	 * Sets the component ID. Setting the id to a null or
	 * empty string should throw IllegalArgumentException
	 * @param componentID
	 */
	void setComponentId(String componentID);
	
	/**
	 * Returns the component object, if it is available
	 * within the current component set
	 */
	IComponent getComponent();
	
	/**
	 * Returns the unique name for the instance
	 */
	String getName();
	
	/**
	 * Validate a potential new name for the instance. The new
	 * name must be a legal identifier and unique within
	 * the model.
	 * @param newName the proposed new name
	 * @return null if name is valid, or a localized error message if not.
	 */
	String validateProposedName(String newName);
	
	/**
	 * Returns the containing model object.
	 * If non-null, the parent will be an object supporting IComponentInstance
	 */
	EObject getParent();
	
	/**
	 * Returns any contained component instance model objects
	 * Returns non-null array of children
	 */
	EObject[] getChildren();
	
	/**
	 * Returns the bound events for this instance
	 * @return
	 */
	IEventBinding[] getEventBindings();
	
	/**
	 * Return the binding for the given event, if one exists
	 * @param eventID the event descriptor's unique ID
	 */
	IEventBinding findEventBinding(String eventID);
	
	/**
	 * Add a listener for component child events
	 */
	void addChildListener(IComponentInstanceChildListener listener);
	
	/**
	 * Add a listener for component property events
	 */
	void addPropertyListener(IComponentInstancePropertyListener listener);
	
	/**
	 * Removes a previously added child listener
	 */
	void removeChildListener(IComponentInstanceChildListener listener);
	
	/**
	 * Removes a previously added property listener
	 */
	void removePropertyListener(IComponentInstancePropertyListener listener);
	
	/**
	 * Add a listener for event binding events
	 */
	void addEventBindingListener(IEventBindingListener listener);

	/**
	 * Removes a previously added event binding listener
	 */
	void removeEventBindingListener(IEventBindingListener listener);
	
	/**
	 * @return the IDesignerDataModel this instance is part of
	 */
	IDesignerDataModel getDesignerDataModel();
	
	/**
	 * @return the root container containing this object
	 */
	EObject getRootContainer();
	
	/**
	 * Request that the IPropertySource information be
	 * recalculated, possibly changing the set of property
	 * descriptors.
	 */
	void updatePropertySource();
	
	/**
	 * The 'configured' flag is used to mark instances as
	 * already configured or initialized. This is a hint to code
	 * listening for model changes that a newly added instance 
	 * should not be modified, or that the modification code should 
	 * at least be aware of the difference. Undo, paste, etc will
	 * first add and object and then configure properties and children.
	 * 
	 * Note that isConfigured does not always return true for objects
	 * in the model. It is set as needed when objects are being added
	 * into a model but is not generally maintained.
	 */
	boolean isConfigured();

	/** Fire a property change from an external source, e.g. a promoted reference property */
	void firePropertyChanged(Object id);
	
	/** Fire a child added from an external source, e.g., node copier */
	void fireChildAdded(EObject child);
}
