/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Events Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.EventsType#getEvent <em>Event</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.EventsType#getDefaultEventName <em>Default Event Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getEventsType()
 * @model extendedMetaData="name='events_._type' kind='elementOnly'"
 * @generated
 */
public interface EventsType extends EObject{
	/**
	 * Returns the value of the '<em><b>Event</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.EventType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Event</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The definition of a single event.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Event</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getEventsType_Event()
	 * @model type="com.nokia.sdt.emf.component.EventType" containment="true"
	 *        extendedMetaData="kind='element' name='event' namespace='##targetNamespace'"
	 * @generated
	 */
	EList getEvent();

	/**
	 * Returns the value of the '<em><b>Default Event Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Event Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If defined, the default event will be identified in a component instance's context
	 * 				menu. In the case of inheritance the most derived definition of the default event is used.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Default Event Name</em>' attribute.
	 * @see #setDefaultEventName(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getEventsType_DefaultEventName()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='defaultEventName'"
	 * @generated
	 */
	String getDefaultEventName();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.EventsType#getDefaultEventName <em>Default Event Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Event Name</em>' attribute.
	 * @see #getDefaultEventName()
	 * @generated
	 */
	void setDefaultEventName(String value);

} // EventsType
