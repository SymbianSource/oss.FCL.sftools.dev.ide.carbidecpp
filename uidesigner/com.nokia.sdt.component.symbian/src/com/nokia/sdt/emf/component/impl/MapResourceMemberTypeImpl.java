/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.MapResourceMemberType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Map Resource Member Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MapResourceMemberTypeImpl#getMember <em>Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MapResourceMemberTypeImpl#getProperty <em>Property</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MapResourceMemberTypeImpl#isSuppressDefault <em>Suppress Default</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MapResourceMemberTypeImpl extends MappingResourceTypeImpl implements MapResourceMemberType {
	/**
	 * The default value of the '{@link #getMember() <em>Member</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getMember()
	 * @generated
	 * @ordered
	 */
    protected static final String MEMBER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMember() <em>Member</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getMember()
	 * @generated
	 * @ordered
	 */
    protected String member = MEMBER_EDEFAULT;

	/**
	 * The default value of the '{@link #getProperty() <em>Property</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getProperty()
	 * @generated
	 * @ordered
	 */
    protected static final String PROPERTY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProperty() <em>Property</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getProperty()
	 * @generated
	 * @ordered
	 */
    protected String property = PROPERTY_EDEFAULT;

	/**
	 * The default value of the '{@link #isSuppressDefault() <em>Suppress Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSuppressDefault()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SUPPRESS_DEFAULT_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isSuppressDefault() <em>Suppress Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSuppressDefault()
	 * @generated
	 * @ordered
	 */
	protected boolean suppressDefault = SUPPRESS_DEFAULT_EDEFAULT;

	/**
	 * This is true if the Suppress Default attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean suppressDefaultESet;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected MapResourceMemberTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected EClass eStaticClass() {
		return ComponentPackage.Literals.MAP_RESOURCE_MEMBER_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getMember() {
		return member;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setMember(String newMember) {
		String oldMember = member;
		member = newMember;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.MAP_RESOURCE_MEMBER_TYPE__MEMBER, oldMember, member));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getProperty() {
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setProperty(String newProperty) {
		String oldProperty = property;
		property = newProperty;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.MAP_RESOURCE_MEMBER_TYPE__PROPERTY, oldProperty, property));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSuppressDefault() {
		return suppressDefault;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSuppressDefault(boolean newSuppressDefault) {
		boolean oldSuppressDefault = suppressDefault;
		suppressDefault = newSuppressDefault;
		boolean oldSuppressDefaultESet = suppressDefaultESet;
		suppressDefaultESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.MAP_RESOURCE_MEMBER_TYPE__SUPPRESS_DEFAULT, oldSuppressDefault, suppressDefault, !oldSuppressDefaultESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetSuppressDefault() {
		boolean oldSuppressDefault = suppressDefault;
		boolean oldSuppressDefaultESet = suppressDefaultESet;
		suppressDefault = SUPPRESS_DEFAULT_EDEFAULT;
		suppressDefaultESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ComponentPackage.MAP_RESOURCE_MEMBER_TYPE__SUPPRESS_DEFAULT, oldSuppressDefault, SUPPRESS_DEFAULT_EDEFAULT, oldSuppressDefaultESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetSuppressDefault() {
		return suppressDefaultESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ComponentPackage.MAP_RESOURCE_MEMBER_TYPE__MEMBER:
				return getMember();
			case ComponentPackage.MAP_RESOURCE_MEMBER_TYPE__PROPERTY:
				return getProperty();
			case ComponentPackage.MAP_RESOURCE_MEMBER_TYPE__SUPPRESS_DEFAULT:
				return isSuppressDefault() ? Boolean.TRUE : Boolean.FALSE;
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
			case ComponentPackage.MAP_RESOURCE_MEMBER_TYPE__MEMBER:
				setMember((String)newValue);
				return;
			case ComponentPackage.MAP_RESOURCE_MEMBER_TYPE__PROPERTY:
				setProperty((String)newValue);
				return;
			case ComponentPackage.MAP_RESOURCE_MEMBER_TYPE__SUPPRESS_DEFAULT:
				setSuppressDefault(((Boolean)newValue).booleanValue());
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
			case ComponentPackage.MAP_RESOURCE_MEMBER_TYPE__MEMBER:
				setMember(MEMBER_EDEFAULT);
				return;
			case ComponentPackage.MAP_RESOURCE_MEMBER_TYPE__PROPERTY:
				setProperty(PROPERTY_EDEFAULT);
				return;
			case ComponentPackage.MAP_RESOURCE_MEMBER_TYPE__SUPPRESS_DEFAULT:
				unsetSuppressDefault();
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
			case ComponentPackage.MAP_RESOURCE_MEMBER_TYPE__MEMBER:
				return MEMBER_EDEFAULT == null ? member != null : !MEMBER_EDEFAULT.equals(member);
			case ComponentPackage.MAP_RESOURCE_MEMBER_TYPE__PROPERTY:
				return PROPERTY_EDEFAULT == null ? property != null : !PROPERTY_EDEFAULT.equals(property);
			case ComponentPackage.MAP_RESOURCE_MEMBER_TYPE__SUPPRESS_DEFAULT:
				return isSetSuppressDefault();
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
		result.append(" (member: ");
		result.append(member);
		result.append(", property: ");
		result.append(property);
		result.append(", suppressDefault: ");
		if (suppressDefaultESet) result.append(suppressDefault); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //MapResourceMemberTypeImpl
