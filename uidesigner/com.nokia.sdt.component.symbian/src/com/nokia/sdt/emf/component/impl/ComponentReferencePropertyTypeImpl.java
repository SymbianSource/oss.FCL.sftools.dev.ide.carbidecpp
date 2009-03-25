/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.ComponentReferencePropertyType;
import com.nokia.sdt.emf.component.ReferenceScopeType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Reference Property Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ComponentReferencePropertyTypeImpl#getConstraint <em>Constraint</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ComponentReferencePropertyTypeImpl#getCreationKeys <em>Creation Keys</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ComponentReferencePropertyTypeImpl#isPromoteReferenceProperties <em>Promote Reference Properties</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ComponentReferencePropertyTypeImpl#getScope <em>Scope</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ComponentReferencePropertyTypeImpl extends AbstractPropertyTypeImpl implements ComponentReferencePropertyType {
	/**
	 * The default value of the '{@link #getConstraint() <em>Constraint</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraint()
	 * @generated
	 * @ordered
	 */
	protected static final String CONSTRAINT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getConstraint() <em>Constraint</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraint()
	 * @generated
	 * @ordered
	 */
	protected String constraint = CONSTRAINT_EDEFAULT;

	/**
	 * The default value of the '{@link #getCreationKeys() <em>Creation Keys</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreationKeys()
	 * @generated
	 * @ordered
	 */
	protected static final String CREATION_KEYS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCreationKeys() <em>Creation Keys</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreationKeys()
	 * @generated
	 * @ordered
	 */
	protected String creationKeys = CREATION_KEYS_EDEFAULT;

	/**
	 * The default value of the '{@link #isPromoteReferenceProperties() <em>Promote Reference Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPromoteReferenceProperties()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PROMOTE_REFERENCE_PROPERTIES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPromoteReferenceProperties() <em>Promote Reference Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPromoteReferenceProperties()
	 * @generated
	 * @ordered
	 */
	protected boolean promoteReferenceProperties = PROMOTE_REFERENCE_PROPERTIES_EDEFAULT;

	/**
	 * This is true if the Promote Reference Properties attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean promoteReferencePropertiesESet;

	/**
	 * The default value of the '{@link #getScope() <em>Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScope()
	 * @generated
	 * @ordered
	 */
	protected static final ReferenceScopeType SCOPE_EDEFAULT = ReferenceScopeType.MODEL_LITERAL;

	/**
	 * The cached value of the '{@link #getScope() <em>Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScope()
	 * @generated
	 * @ordered
	 */
	protected ReferenceScopeType scope = SCOPE_EDEFAULT;

	/**
	 * This is true if the Scope attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean scopeESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComponentReferencePropertyTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.COMPONENT_REFERENCE_PROPERTY_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getConstraint() {
		return constraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConstraint(String newConstraint) {
		String oldConstraint = constraint;
		constraint = newConstraint;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_REFERENCE_PROPERTY_TYPE__CONSTRAINT, oldConstraint, constraint));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCreationKeys() {
		return creationKeys;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCreationKeys(String newCreationKeys) {
		String oldCreationKeys = creationKeys;
		creationKeys = newCreationKeys;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_REFERENCE_PROPERTY_TYPE__CREATION_KEYS, oldCreationKeys, creationKeys));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isPromoteReferenceProperties() {
		return promoteReferenceProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPromoteReferenceProperties(boolean newPromoteReferenceProperties) {
		boolean oldPromoteReferenceProperties = promoteReferenceProperties;
		promoteReferenceProperties = newPromoteReferenceProperties;
		boolean oldPromoteReferencePropertiesESet = promoteReferencePropertiesESet;
		promoteReferencePropertiesESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_REFERENCE_PROPERTY_TYPE__PROMOTE_REFERENCE_PROPERTIES, oldPromoteReferenceProperties, promoteReferenceProperties, !oldPromoteReferencePropertiesESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetPromoteReferenceProperties() {
		boolean oldPromoteReferenceProperties = promoteReferenceProperties;
		boolean oldPromoteReferencePropertiesESet = promoteReferencePropertiesESet;
		promoteReferenceProperties = PROMOTE_REFERENCE_PROPERTIES_EDEFAULT;
		promoteReferencePropertiesESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ComponentPackage.COMPONENT_REFERENCE_PROPERTY_TYPE__PROMOTE_REFERENCE_PROPERTIES, oldPromoteReferenceProperties, PROMOTE_REFERENCE_PROPERTIES_EDEFAULT, oldPromoteReferencePropertiesESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetPromoteReferenceProperties() {
		return promoteReferencePropertiesESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReferenceScopeType getScope() {
		return scope;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScope(ReferenceScopeType newScope) {
		ReferenceScopeType oldScope = scope;
		scope = newScope == null ? SCOPE_EDEFAULT : newScope;
		boolean oldScopeESet = scopeESet;
		scopeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_REFERENCE_PROPERTY_TYPE__SCOPE, oldScope, scope, !oldScopeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetScope() {
		ReferenceScopeType oldScope = scope;
		boolean oldScopeESet = scopeESet;
		scope = SCOPE_EDEFAULT;
		scopeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ComponentPackage.COMPONENT_REFERENCE_PROPERTY_TYPE__SCOPE, oldScope, SCOPE_EDEFAULT, oldScopeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetScope() {
		return scopeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ComponentPackage.COMPONENT_REFERENCE_PROPERTY_TYPE__CONSTRAINT:
				return getConstraint();
			case ComponentPackage.COMPONENT_REFERENCE_PROPERTY_TYPE__CREATION_KEYS:
				return getCreationKeys();
			case ComponentPackage.COMPONENT_REFERENCE_PROPERTY_TYPE__PROMOTE_REFERENCE_PROPERTIES:
				return isPromoteReferenceProperties() ? Boolean.TRUE : Boolean.FALSE;
			case ComponentPackage.COMPONENT_REFERENCE_PROPERTY_TYPE__SCOPE:
				return getScope();
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
			case ComponentPackage.COMPONENT_REFERENCE_PROPERTY_TYPE__CONSTRAINT:
				setConstraint((String)newValue);
				return;
			case ComponentPackage.COMPONENT_REFERENCE_PROPERTY_TYPE__CREATION_KEYS:
				setCreationKeys((String)newValue);
				return;
			case ComponentPackage.COMPONENT_REFERENCE_PROPERTY_TYPE__PROMOTE_REFERENCE_PROPERTIES:
				setPromoteReferenceProperties(((Boolean)newValue).booleanValue());
				return;
			case ComponentPackage.COMPONENT_REFERENCE_PROPERTY_TYPE__SCOPE:
				setScope((ReferenceScopeType)newValue);
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
			case ComponentPackage.COMPONENT_REFERENCE_PROPERTY_TYPE__CONSTRAINT:
				setConstraint(CONSTRAINT_EDEFAULT);
				return;
			case ComponentPackage.COMPONENT_REFERENCE_PROPERTY_TYPE__CREATION_KEYS:
				setCreationKeys(CREATION_KEYS_EDEFAULT);
				return;
			case ComponentPackage.COMPONENT_REFERENCE_PROPERTY_TYPE__PROMOTE_REFERENCE_PROPERTIES:
				unsetPromoteReferenceProperties();
				return;
			case ComponentPackage.COMPONENT_REFERENCE_PROPERTY_TYPE__SCOPE:
				unsetScope();
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
			case ComponentPackage.COMPONENT_REFERENCE_PROPERTY_TYPE__CONSTRAINT:
				return CONSTRAINT_EDEFAULT == null ? constraint != null : !CONSTRAINT_EDEFAULT.equals(constraint);
			case ComponentPackage.COMPONENT_REFERENCE_PROPERTY_TYPE__CREATION_KEYS:
				return CREATION_KEYS_EDEFAULT == null ? creationKeys != null : !CREATION_KEYS_EDEFAULT.equals(creationKeys);
			case ComponentPackage.COMPONENT_REFERENCE_PROPERTY_TYPE__PROMOTE_REFERENCE_PROPERTIES:
				return isSetPromoteReferenceProperties();
			case ComponentPackage.COMPONENT_REFERENCE_PROPERTY_TYPE__SCOPE:
				return isSetScope();
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
		result.append(" (constraint: ");
		result.append(constraint);
		result.append(", creationKeys: ");
		result.append(creationKeys);
		result.append(", promoteReferenceProperties: ");
		if (promoteReferencePropertiesESet) result.append(promoteReferenceProperties); else result.append("<unset>");
		result.append(", scope: ");
		if (scopeESet) result.append(scope); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //ComponentReferencePropertyTypeImpl
