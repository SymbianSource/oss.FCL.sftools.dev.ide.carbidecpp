/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.dm.impl;

import com.nokia.sdt.emf.dm.DmPackage;
import com.nokia.sdt.emf.dm.IPropertyContainer;
import com.nokia.sdt.emf.dm.IPropertyValue;
import com.nokia.sdt.emf.dm.StringValue;

import com.nokia.sdt.emf.dm.*;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import java.util.Collection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IProperty Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IPropertyValueImpl#getValue <em>Value</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IPropertyValueImpl#getStringValue <em>String Value</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IPropertyValueImpl#getCompoundValue <em>Compound Value</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IPropertyValueImpl#getSequenceValue <em>Sequence Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IPropertyValueImpl extends EObjectImpl implements IPropertyValue {
	
	// IPropertyValue represents a union of the different types of
	// property values. Once an instance has been given a type the
	// type is immutable.
	private final static int NO_VARIANT = 0;
	private final static int STRING_VARIANT = 1;
	private final static int COMPOUND_VARIANT = 2;
	private final static int SEQUENCE_VARIANT = 3;
	
	private int variantType = NO_VARIANT;
	
	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final Object VALUE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getStringValue() <em>String Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStringValue()
	 * @generated
	 * @ordered
	 */
	protected static final StringValue STRING_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStringValue() <em>String Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStringValue()
	 * @generated
	 * @ordered
	 */
	protected StringValue stringValue = STRING_VALUE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCompoundValue() <em>Compound Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompoundValue()
	 * @generated
	 * @ordered
	 */
	protected IPropertyContainer compoundValue = null;

	/**
	 * The cached value of the '{@link #getSequenceValue() <em>Sequence Value</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSequenceValue()
	 * @generated
	 * @ordered
	 */
	protected EList sequenceValue = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IPropertyValueImpl() {
		super();
	}

	public boolean equals(Object o) {
		boolean result = false;
		if (o instanceof IPropertyValueImpl) {
			IPropertyValueImpl opv = (IPropertyValueImpl) o;
			if (variantType == opv.variantType) {
				result = ObjectUtils.equals(getValue(), opv.getValue());
			}
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return DmPackage.Literals.IPROPERTY_VALUE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Object getValue() {
		if (stringValue != null)
			return stringValue;
		if (compoundValue != null)
			return compoundValue;
		return sequenceValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringValue getStringValue() {
		return stringValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setStringValue(StringValue newStringValue) {
		if (variantType == COMPOUND_VARIANT || variantType == SEQUENCE_VARIANT)
			throw new IllegalStateException();
				
		variantType = STRING_VARIANT;
		StringValue oldStringValue = stringValue;
		stringValue = newStringValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DmPackage.IPROPERTY_VALUE__STRING_VALUE, oldStringValue, stringValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IPropertyContainer getCompoundValue() {
		return compoundValue;
	}

	// for package internal use only, the container is set by the
	// containing property container
	void setCompoundValue(IPropertyContainer container) {
		Check.checkArg(container);
		if (variantType == STRING_VARIANT || variantType == COMPOUND_VARIANT)
			throw new IllegalStateException();

		variantType = COMPOUND_VARIANT;
		basicSetCompoundValue(container, null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCompoundValue(IPropertyContainer newCompoundValue, NotificationChain msgs) {
		IPropertyContainer oldCompoundValue = compoundValue;
		compoundValue = newCompoundValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DmPackage.IPROPERTY_VALUE__COMPOUND_VALUE, oldCompoundValue, newCompoundValue);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList getSequenceValue() {	
		return sequenceValue;
	}
		
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DmPackage.IPROPERTY_VALUE__COMPOUND_VALUE:
				return basicSetCompoundValue(null, msgs);
			case DmPackage.IPROPERTY_VALUE__SEQUENCE_VALUE:
				return ((InternalEList)getSequenceValue()).basicRemove(otherEnd, msgs);
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
			case DmPackage.IPROPERTY_VALUE__VALUE:
				return getValue();
			case DmPackage.IPROPERTY_VALUE__STRING_VALUE:
				return getStringValue();
			case DmPackage.IPROPERTY_VALUE__COMPOUND_VALUE:
				return getCompoundValue();
			case DmPackage.IPROPERTY_VALUE__SEQUENCE_VALUE:
				return getSequenceValue();
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
			case DmPackage.IPROPERTY_VALUE__STRING_VALUE:
				setStringValue((StringValue)newValue);
				return;
			case DmPackage.IPROPERTY_VALUE__SEQUENCE_VALUE:
				getSequenceValue().clear();
				getSequenceValue().addAll((Collection)newValue);
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
			case DmPackage.IPROPERTY_VALUE__STRING_VALUE:
				setStringValue(STRING_VALUE_EDEFAULT);
				return;
			case DmPackage.IPROPERTY_VALUE__SEQUENCE_VALUE:
				getSequenceValue().clear();
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
			case DmPackage.IPROPERTY_VALUE__VALUE:
				return VALUE_EDEFAULT == null ? getValue() != null : !VALUE_EDEFAULT.equals(getValue());
			case DmPackage.IPROPERTY_VALUE__STRING_VALUE:
				return STRING_VALUE_EDEFAULT == null ? stringValue != null : !STRING_VALUE_EDEFAULT.equals(stringValue);
			case DmPackage.IPROPERTY_VALUE__COMPOUND_VALUE:
				return compoundValue != null;
			case DmPackage.IPROPERTY_VALUE__SEQUENCE_VALUE:
				return sequenceValue != null && !sequenceValue.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	IPropertyContainer getPropertyContainer() {
		IPropertyContainer result = null;
		EObject obj = eContainer();
		while (obj != null) {
			if (obj instanceof IPropertyContainer) {
				result = (IPropertyContainer) obj;
				break;
			}
			obj = obj.eContainer();
		}
		return result;
	}
	
	INode getNode() {
		INode result = null;
		IPropertyContainer container = getPropertyContainer();
		if (container != null) {
			EObject containerOwner = container.getOwner();
			if (containerOwner instanceof INode)
				result = (INode) containerOwner;
		}
		return result;
	}
	
	ILocalizedStringBundle getBundle() {
		ILocalizedStringBundle result = null;
		INode node = getNode();
		if (node != null)
			result = node.getDesignerData().getStringBundle();
		return result;
	}

	// for package internal use only, call by parent container
	public void initSequenceValue(IPropertyContainerImpl propertyContainer, String path, Object id) {
		if (variantType == STRING_VARIANT || variantType == COMPOUND_VARIANT)
			throw new IllegalArgumentException();

		variantType = SEQUENCE_VARIANT;
		if (hasStringValue() || hasCompoundValue())
			throw new IllegalStateException();
		PropertyValueSequence pvs = new PropertyValueSequence(
				IPropertyValue.class, this, 
				DmPackage.IPROPERTY_VALUE__SEQUENCE_VALUE, 
				propertyContainer);
		pvs.setPath(path);
		pvs.setID(id);
		sequenceValue = pvs;
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (stringValue: ");
		result.append(stringValue);
		result.append(')');
		return result.toString();
	}

	public boolean hasStringValue() {
		return stringValue != null;
	}

	public boolean hasCompoundValue() {
		return compoundValue != null;
	}

	public boolean hasSequenceValue() {
		return sequenceValue != null;
	}

} //IPropertyValueImpl
