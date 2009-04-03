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

package com.nokia.sdt.emf.dm;


import com.nokia.sdt.component.IComponent;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * @model
 */
public interface INode extends EObject{
	
		// required key for name property
	public static final String NAME_PROPERTY = "name";
	
	/**
	 * @model
	 * @return
	 */
	String	getComponentId();
		

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.dm.INode#getComponentId <em>Component Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component Id</em>' attribute.
	 * @see #getComponentId()
	 * @generated
	 */
	void setComponentId(String value);

	IComponent	getComponent();
	
	String getName();
	
	/**
	 * Validate a potential new name for the node. The new
	 * name must be a legal identifier and unique within
	 * the model. A legal identifier is composed of alphanumeric
	 * characters
	 * @param newName the proposed new name
	 * @return null if name is valid, or a localized error message if not.
	 */
	String validateName(String newName);
	
	/**
	 * @model changeable="false" containment="true"
	 */
	IPropertyContainer getProperties();

	/**
	 * @model type="com.nokia.sdt.emf.dm.INode" containment="true"
	 * @return
	 */
	EList getChildren();
	
	/**
	 * Return the topmost INode containing this one
	 */
	INode getRootContainer();
	
	/**
	 * Return the IDesignerData for the model
	 */
	IDesignerData getDesignerData();
	
	/**
	 * Looks for a node with the given name, by checking this
	 * node and all its children.
	 */
	INode findByNameProperty(String name); 
	
	/**
	 * @model type="com.nokia.sdt.emf.dm.IEventBinding" containment="true"
	 */
	EList getEventBindings();
	
	/**
	 * Search for an existing event binding
	 * @param eventID the event ID
	 */
	IEventBinding findBinding(String eventID);
	
	/**
	 * Visit INodes in preorder. Visitors returns can
	 * terminate traversal by returning a non-null result,
	 * which is in turn returned by this method. That could
	 * be a useful value, or a placeholder value like Boolean.TRUE.
	 * If all visitors returned null then this method returns null.
	 * @return result of last visitor visited
	 */
	Object visitPreorder(INodeVisitor visitor);	
	
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
	
	void setConfigured(boolean configured);


}
