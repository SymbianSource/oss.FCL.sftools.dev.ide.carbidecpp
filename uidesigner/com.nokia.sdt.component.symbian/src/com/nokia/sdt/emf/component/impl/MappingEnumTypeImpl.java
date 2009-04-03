/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.MappingEnumType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import java.util.Collection;
import java.util.List;
/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mapping Enum Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingEnumTypeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingEnumTypeImpl#getMapEnum <em>Map Enum</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingEnumTypeImpl#getEnumeration <em>Enumeration</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingEnumTypeImpl#getHeaders <em>Headers</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingEnumTypeImpl#getNameAlgorithm <em>Name Algorithm</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingEnumTypeImpl#getUniqueValue <em>Unique Value</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingEnumTypeImpl#isValidate <em>Validate</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MappingEnumTypeImpl extends TwoWayMappingTypeImpl implements MappingEnumType {
	/**
	 * The cached value of the '{@link #getGroup() <em>Group</em>}' attribute list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getGroup()
	 * @generated
	 * @ordered
	 */
    protected FeatureMap group;

	/**
	 * The default value of the '{@link #getEnumeration() <em>Enumeration</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getEnumeration()
	 * @generated
	 * @ordered
	 */
    protected static final String ENUMERATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEnumeration() <em>Enumeration</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getEnumeration()
	 * @generated
	 * @ordered
	 */
    protected String enumeration = ENUMERATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getHeaders() <em>Headers</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getHeaders()
	 * @generated
	 * @ordered
	 */
    protected static final List HEADERS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHeaders() <em>Headers</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getHeaders()
	 * @generated
	 * @ordered
	 */
    protected List headers = HEADERS_EDEFAULT;

	/**
	 * The default value of the '{@link #getNameAlgorithm() <em>Name Algorithm</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getNameAlgorithm()
	 * @generated
	 * @ordered
	 */
    protected static final String NAME_ALGORITHM_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNameAlgorithm() <em>Name Algorithm</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getNameAlgorithm()
	 * @generated
	 * @ordered
	 */
    protected String nameAlgorithm = NAME_ALGORITHM_EDEFAULT;

	/**
	 * The default value of the '{@link #getUniqueValue() <em>Unique Value</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getUniqueValue()
	 * @generated
	 * @ordered
	 */
    protected static final String UNIQUE_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUniqueValue() <em>Unique Value</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getUniqueValue()
	 * @generated
	 * @ordered
	 */
    protected String uniqueValue = UNIQUE_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #isValidate() <em>Validate</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isValidate()
	 * @generated
	 * @ordered
	 */
    protected static final boolean VALIDATE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isValidate() <em>Validate</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isValidate()
	 * @generated
	 * @ordered
	 */
    protected boolean validate = VALIDATE_EDEFAULT;

	/**
	 * This is true if the Validate attribute has been set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    protected boolean validateESet;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected MappingEnumTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected EClass eStaticClass() {
		return ComponentPackage.Literals.MAPPING_ENUM_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, ComponentPackage.MAPPING_ENUM_TYPE__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getMapEnum() {
		return getGroup().list(ComponentPackage.Literals.MAPPING_ENUM_TYPE__MAP_ENUM);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getEnumeration() {
		return enumeration;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setEnumeration(String newEnumeration) {
		String oldEnumeration = enumeration;
		enumeration = newEnumeration;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.MAPPING_ENUM_TYPE__ENUMERATION, oldEnumeration, enumeration));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public List getHeaders() {
		return headers;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setHeaders(List newHeaders) {
		List oldHeaders = headers;
		headers = newHeaders;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.MAPPING_ENUM_TYPE__HEADERS, oldHeaders, headers));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getNameAlgorithm() {
		return nameAlgorithm;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setNameAlgorithm(String newNameAlgorithm) {
		String oldNameAlgorithm = nameAlgorithm;
		nameAlgorithm = newNameAlgorithm;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.MAPPING_ENUM_TYPE__NAME_ALGORITHM, oldNameAlgorithm, nameAlgorithm));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getUniqueValue() {
		return uniqueValue;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setUniqueValue(String newUniqueValue) {
		String oldUniqueValue = uniqueValue;
		uniqueValue = newUniqueValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.MAPPING_ENUM_TYPE__UNIQUE_VALUE, oldUniqueValue, uniqueValue));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isValidate() {
		return validate;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setValidate(boolean newValidate) {
		boolean oldValidate = validate;
		validate = newValidate;
		boolean oldValidateESet = validateESet;
		validateESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.MAPPING_ENUM_TYPE__VALIDATE, oldValidate, validate, !oldValidateESet));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void unsetValidate() {
		boolean oldValidate = validate;
		boolean oldValidateESet = validateESet;
		validate = VALIDATE_EDEFAULT;
		validateESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ComponentPackage.MAPPING_ENUM_TYPE__VALIDATE, oldValidate, VALIDATE_EDEFAULT, oldValidateESet));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isSetValidate() {
		return validateESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.MAPPING_ENUM_TYPE__GROUP:
				return ((InternalEList)getGroup()).basicRemove(otherEnd, msgs);
			case ComponentPackage.MAPPING_ENUM_TYPE__MAP_ENUM:
				return ((InternalEList)getMapEnum()).basicRemove(otherEnd, msgs);
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
			case ComponentPackage.MAPPING_ENUM_TYPE__GROUP:
				if (coreType) return getGroup();
				return ((FeatureMap.Internal)getGroup()).getWrapper();
			case ComponentPackage.MAPPING_ENUM_TYPE__MAP_ENUM:
				return getMapEnum();
			case ComponentPackage.MAPPING_ENUM_TYPE__ENUMERATION:
				return getEnumeration();
			case ComponentPackage.MAPPING_ENUM_TYPE__HEADERS:
				return getHeaders();
			case ComponentPackage.MAPPING_ENUM_TYPE__NAME_ALGORITHM:
				return getNameAlgorithm();
			case ComponentPackage.MAPPING_ENUM_TYPE__UNIQUE_VALUE:
				return getUniqueValue();
			case ComponentPackage.MAPPING_ENUM_TYPE__VALIDATE:
				return isValidate() ? Boolean.TRUE : Boolean.FALSE;
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
			case ComponentPackage.MAPPING_ENUM_TYPE__GROUP:
				((FeatureMap.Internal)getGroup()).set(newValue);
				return;
			case ComponentPackage.MAPPING_ENUM_TYPE__MAP_ENUM:
				getMapEnum().clear();
				getMapEnum().addAll((Collection)newValue);
				return;
			case ComponentPackage.MAPPING_ENUM_TYPE__ENUMERATION:
				setEnumeration((String)newValue);
				return;
			case ComponentPackage.MAPPING_ENUM_TYPE__HEADERS:
				setHeaders((List)newValue);
				return;
			case ComponentPackage.MAPPING_ENUM_TYPE__NAME_ALGORITHM:
				setNameAlgorithm((String)newValue);
				return;
			case ComponentPackage.MAPPING_ENUM_TYPE__UNIQUE_VALUE:
				setUniqueValue((String)newValue);
				return;
			case ComponentPackage.MAPPING_ENUM_TYPE__VALIDATE:
				setValidate(((Boolean)newValue).booleanValue());
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
			case ComponentPackage.MAPPING_ENUM_TYPE__GROUP:
				getGroup().clear();
				return;
			case ComponentPackage.MAPPING_ENUM_TYPE__MAP_ENUM:
				getMapEnum().clear();
				return;
			case ComponentPackage.MAPPING_ENUM_TYPE__ENUMERATION:
				setEnumeration(ENUMERATION_EDEFAULT);
				return;
			case ComponentPackage.MAPPING_ENUM_TYPE__HEADERS:
				setHeaders(HEADERS_EDEFAULT);
				return;
			case ComponentPackage.MAPPING_ENUM_TYPE__NAME_ALGORITHM:
				setNameAlgorithm(NAME_ALGORITHM_EDEFAULT);
				return;
			case ComponentPackage.MAPPING_ENUM_TYPE__UNIQUE_VALUE:
				setUniqueValue(UNIQUE_VALUE_EDEFAULT);
				return;
			case ComponentPackage.MAPPING_ENUM_TYPE__VALIDATE:
				unsetValidate();
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
			case ComponentPackage.MAPPING_ENUM_TYPE__GROUP:
				return group != null && !group.isEmpty();
			case ComponentPackage.MAPPING_ENUM_TYPE__MAP_ENUM:
				return !getMapEnum().isEmpty();
			case ComponentPackage.MAPPING_ENUM_TYPE__ENUMERATION:
				return ENUMERATION_EDEFAULT == null ? enumeration != null : !ENUMERATION_EDEFAULT.equals(enumeration);
			case ComponentPackage.MAPPING_ENUM_TYPE__HEADERS:
				return HEADERS_EDEFAULT == null ? headers != null : !HEADERS_EDEFAULT.equals(headers);
			case ComponentPackage.MAPPING_ENUM_TYPE__NAME_ALGORITHM:
				return NAME_ALGORITHM_EDEFAULT == null ? nameAlgorithm != null : !NAME_ALGORITHM_EDEFAULT.equals(nameAlgorithm);
			case ComponentPackage.MAPPING_ENUM_TYPE__UNIQUE_VALUE:
				return UNIQUE_VALUE_EDEFAULT == null ? uniqueValue != null : !UNIQUE_VALUE_EDEFAULT.equals(uniqueValue);
			case ComponentPackage.MAPPING_ENUM_TYPE__VALIDATE:
				return isSetValidate();
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
		result.append(" (group: ");
		result.append(group);
		result.append(", enumeration: ");
		result.append(enumeration);
		result.append(", headers: ");
		result.append(headers);
		result.append(", nameAlgorithm: ");
		result.append(nameAlgorithm);
		result.append(", uniqueValue: ");
		result.append(uniqueValue);
		result.append(", validate: ");
		if (validateESet) result.append(validate); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //MappingEnumTypeImpl
