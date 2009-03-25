/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.EventType;
import com.nokia.sdt.emf.component.EventsType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import java.util.Collection;
/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Events Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.EventsTypeImpl#getEvent <em>Event</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.EventsTypeImpl#getDefaultEventName <em>Default Event Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EventsTypeImpl extends EObjectImpl implements EventsType {
	/**
	 * The cached value of the '{@link #getEvent() <em>Event</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEvent()
	 * @generated
	 * @ordered
	 */
	protected EList event;

	/**
	 * The default value of the '{@link #getDefaultEventName() <em>Default Event Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultEventName()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_EVENT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultEventName() <em>Default Event Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultEventName()
	 * @generated
	 * @ordered
	 */
	protected String defaultEventName = DEFAULT_EVENT_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EventsTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.EVENTS_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getEvent() {
		if (event == null) {
			event = new EObjectContainmentEList(EventType.class, this, ComponentPackage.EVENTS_TYPE__EVENT);
		}
		return event;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDefaultEventName() {
		return defaultEventName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefaultEventName(String newDefaultEventName) {
		String oldDefaultEventName = defaultEventName;
		defaultEventName = newDefaultEventName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.EVENTS_TYPE__DEFAULT_EVENT_NAME, oldDefaultEventName, defaultEventName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.EVENTS_TYPE__EVENT:
				return ((InternalEList)getEvent()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ComponentPackage.EVENTS_TYPE__EVENT:
				return getEvent();
			case ComponentPackage.EVENTS_TYPE__DEFAULT_EVENT_NAME:
				return getDefaultEventName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ComponentPackage.EVENTS_TYPE__EVENT:
				getEvent().clear();
				getEvent().addAll((Collection)newValue);
				return;
			case ComponentPackage.EVENTS_TYPE__DEFAULT_EVENT_NAME:
				setDefaultEventName((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case ComponentPackage.EVENTS_TYPE__EVENT:
				getEvent().clear();
				return;
			case ComponentPackage.EVENTS_TYPE__DEFAULT_EVENT_NAME:
				setDefaultEventName(DEFAULT_EVENT_NAME_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ComponentPackage.EVENTS_TYPE__EVENT:
				return event != null && !event.isEmpty();
			case ComponentPackage.EVENTS_TYPE__DEFAULT_EVENT_NAME:
				return DEFAULT_EVENT_NAME_EDEFAULT == null ? defaultEventName != null : !DEFAULT_EVENT_NAME_EDEFAULT.equals(defaultEventName);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (defaultEventName: ");
		result.append(defaultEventName);
		result.append(')');
		return result.toString();
	}

} //EventsTypeImpl
