/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.dm.impl;

import com.nokia.sdt.emf.dm.DmPackage;
import com.nokia.sdt.emf.dm.IEventBinding;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IEvent Binding</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IEventBindingImpl#getEventID <em>Event ID</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IEventBindingImpl#getEventHandlerDisplayText <em>Event Handler Display Text</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IEventBindingImpl#getEventHandlerInfo <em>Event Handler Info</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IEventBindingImpl extends EObjectImpl implements IEventBinding {
	/**
	 * The default value of the '{@link #getEventID() <em>Event ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventID()
	 * @generated
	 * @ordered
	 */
	protected static final String EVENT_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEventID() <em>Event ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventID()
	 * @generated
	 * @ordered
	 */
	protected String eventID = EVENT_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getEventHandlerDisplayText() <em>Event Handler Display Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventHandlerDisplayText()
	 * @generated
	 * @ordered
	 */
	protected static final String EVENT_HANDLER_DISPLAY_TEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEventHandlerDisplayText() <em>Event Handler Display Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventHandlerDisplayText()
	 * @generated
	 * @ordered
	 */
	protected String eventHandlerDisplayText = EVENT_HANDLER_DISPLAY_TEXT_EDEFAULT;

	/**
	 * The default value of the '{@link #getEventHandlerInfo() <em>Event Handler Info</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventHandlerInfo()
	 * @generated
	 * @ordered
	 */
	protected static final String EVENT_HANDLER_INFO_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEventHandlerInfo() <em>Event Handler Info</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventHandlerInfo()
	 * @generated
	 * @ordered
	 */
	protected String eventHandlerInfo = EVENT_HANDLER_INFO_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IEventBindingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return DmPackage.Literals.IEVENT_BINDING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEventID() {
		return eventID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEventID(String newEventID) {
		String oldEventID = eventID;
		eventID = newEventID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DmPackage.IEVENT_BINDING__EVENT_ID, oldEventID, eventID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEventHandlerDisplayText() {
		return eventHandlerDisplayText;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEventHandlerDisplayText(String newEventHandlerDisplayText) {
		String oldEventHandlerDisplayText = eventHandlerDisplayText;
		eventHandlerDisplayText = newEventHandlerDisplayText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DmPackage.IEVENT_BINDING__EVENT_HANDLER_DISPLAY_TEXT, oldEventHandlerDisplayText, eventHandlerDisplayText));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEventHandlerInfo() {
		return eventHandlerInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEventHandlerInfo(String newEventHandlerInfo) {
		String oldEventHandlerInfo = eventHandlerInfo;
		eventHandlerInfo = newEventHandlerInfo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DmPackage.IEVENT_BINDING__EVENT_HANDLER_INFO, oldEventHandlerInfo, eventHandlerInfo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DmPackage.IEVENT_BINDING__EVENT_ID:
				return getEventID();
			case DmPackage.IEVENT_BINDING__EVENT_HANDLER_DISPLAY_TEXT:
				return getEventHandlerDisplayText();
			case DmPackage.IEVENT_BINDING__EVENT_HANDLER_INFO:
				return getEventHandlerInfo();
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
			case DmPackage.IEVENT_BINDING__EVENT_ID:
				setEventID((String)newValue);
				return;
			case DmPackage.IEVENT_BINDING__EVENT_HANDLER_DISPLAY_TEXT:
				setEventHandlerDisplayText((String)newValue);
				return;
			case DmPackage.IEVENT_BINDING__EVENT_HANDLER_INFO:
				setEventHandlerInfo((String)newValue);
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
			case DmPackage.IEVENT_BINDING__EVENT_ID:
				setEventID(EVENT_ID_EDEFAULT);
				return;
			case DmPackage.IEVENT_BINDING__EVENT_HANDLER_DISPLAY_TEXT:
				setEventHandlerDisplayText(EVENT_HANDLER_DISPLAY_TEXT_EDEFAULT);
				return;
			case DmPackage.IEVENT_BINDING__EVENT_HANDLER_INFO:
				setEventHandlerInfo(EVENT_HANDLER_INFO_EDEFAULT);
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
			case DmPackage.IEVENT_BINDING__EVENT_ID:
				return EVENT_ID_EDEFAULT == null ? eventID != null : !EVENT_ID_EDEFAULT.equals(eventID);
			case DmPackage.IEVENT_BINDING__EVENT_HANDLER_DISPLAY_TEXT:
				return EVENT_HANDLER_DISPLAY_TEXT_EDEFAULT == null ? eventHandlerDisplayText != null : !EVENT_HANDLER_DISPLAY_TEXT_EDEFAULT.equals(eventHandlerDisplayText);
			case DmPackage.IEVENT_BINDING__EVENT_HANDLER_INFO:
				return EVENT_HANDLER_INFO_EDEFAULT == null ? eventHandlerInfo != null : !EVENT_HANDLER_INFO_EDEFAULT.equals(eventHandlerInfo);
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
		result.append(" (eventID: ");
		result.append(eventID);
		result.append(", eventHandlerDisplayText: ");
		result.append(eventHandlerDisplayText);
		result.append(", eventHandlerInfo: ");
		result.append(eventHandlerInfo);
		result.append(')');
		return result.toString();
	}

} //IEventBindingImpl
