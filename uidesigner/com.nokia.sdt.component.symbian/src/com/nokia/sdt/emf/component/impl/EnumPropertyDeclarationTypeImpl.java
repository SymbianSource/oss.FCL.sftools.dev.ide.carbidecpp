/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.EnumElementType;
import com.nokia.sdt.emf.component.EnumPropertyDeclarationType;
import com.nokia.sdt.emf.component.SourceTypeMappingType;
import com.nokia.cpp.internal.api.utils.core.ILocalizedStrings;
import com.nokia.cpp.internal.api.utils.core.ObjectUtils;

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
import java.util.Iterator;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Enum Property Declaration Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.EnumPropertyDeclarationTypeImpl#getEnumElement <em>Enum Element</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.EnumPropertyDeclarationTypeImpl#getSourceTypeMapping <em>Source Type Mapping</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.EnumPropertyDeclarationTypeImpl#getDefaultValue <em>Default Value</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.EnumPropertyDeclarationTypeImpl#getQualifiedName <em>Qualified Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EnumPropertyDeclarationTypeImpl extends EObjectImpl implements EnumPropertyDeclarationType {
	/**
	 * The cached value of the '{@link #getEnumElement() <em>Enum Element</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnumElement()
	 * @generated
	 * @ordered
	 */
	protected EList enumElement;

	/**
	 * The cached value of the '{@link #getSourceTypeMapping() <em>Source Type Mapping</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceTypeMapping()
	 * @generated
	 * @ordered
	 */
	protected SourceTypeMappingType sourceTypeMapping;

	/**
	 * The default value of the '{@link #getDefaultValue() <em>Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDefaultValue()
	 * @generated
	 * @ordered
	 */
    protected static final String DEFAULT_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultValue() <em>Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDefaultValue()
	 * @generated
	 * @ordered
	 */
    protected String defaultValue = DEFAULT_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getQualifiedName() <em>Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getQualifiedName()
	 * @generated
	 * @ordered
	 */
    protected static final String QUALIFIED_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getQualifiedName() <em>Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getQualifiedName()
	 * @generated
	 * @ordered
	 */
    protected String qualifiedName = QUALIFIED_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EnumPropertyDeclarationTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.ENUM_PROPERTY_DECLARATION_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getEnumElement() {
		if (enumElement == null) {
			enumElement = new EObjectContainmentEList(EnumElementType.class, this, ComponentPackage.ENUM_PROPERTY_DECLARATION_TYPE__ENUM_ELEMENT);
		}
		return enumElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SourceTypeMappingType getSourceTypeMapping() {
		return sourceTypeMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSourceTypeMapping(SourceTypeMappingType newSourceTypeMapping, NotificationChain msgs) {
		SourceTypeMappingType oldSourceTypeMapping = sourceTypeMapping;
		sourceTypeMapping = newSourceTypeMapping;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ComponentPackage.ENUM_PROPERTY_DECLARATION_TYPE__SOURCE_TYPE_MAPPING, oldSourceTypeMapping, newSourceTypeMapping);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceTypeMapping(SourceTypeMappingType newSourceTypeMapping) {
		if (newSourceTypeMapping != sourceTypeMapping) {
			NotificationChain msgs = null;
			if (sourceTypeMapping != null)
				msgs = ((InternalEObject)sourceTypeMapping).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.ENUM_PROPERTY_DECLARATION_TYPE__SOURCE_TYPE_MAPPING, null, msgs);
			if (newSourceTypeMapping != null)
				msgs = ((InternalEObject)newSourceTypeMapping).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.ENUM_PROPERTY_DECLARATION_TYPE__SOURCE_TYPE_MAPPING, null, msgs);
			msgs = basicSetSourceTypeMapping(newSourceTypeMapping, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ENUM_PROPERTY_DECLARATION_TYPE__SOURCE_TYPE_MAPPING, newSourceTypeMapping, newSourceTypeMapping));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setDefaultValue(String newDefaultValue) {
		String oldDefaultValue = defaultValue;
		defaultValue = newDefaultValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ENUM_PROPERTY_DECLARATION_TYPE__DEFAULT_VALUE, oldDefaultValue, defaultValue));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getQualifiedName() {
		return qualifiedName;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setQualifiedName(String newQualifiedName) {
		String oldQualifiedName = qualifiedName;
		qualifiedName = newQualifiedName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ENUM_PROPERTY_DECLARATION_TYPE__QUALIFIED_NAME, oldQualifiedName, qualifiedName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.ENUM_PROPERTY_DECLARATION_TYPE__ENUM_ELEMENT:
				return ((InternalEList)getEnumElement()).basicRemove(otherEnd, msgs);
			case ComponentPackage.ENUM_PROPERTY_DECLARATION_TYPE__SOURCE_TYPE_MAPPING:
				return basicSetSourceTypeMapping(null, msgs);
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
			case ComponentPackage.ENUM_PROPERTY_DECLARATION_TYPE__ENUM_ELEMENT:
				return getEnumElement();
			case ComponentPackage.ENUM_PROPERTY_DECLARATION_TYPE__SOURCE_TYPE_MAPPING:
				return getSourceTypeMapping();
			case ComponentPackage.ENUM_PROPERTY_DECLARATION_TYPE__DEFAULT_VALUE:
				return getDefaultValue();
			case ComponentPackage.ENUM_PROPERTY_DECLARATION_TYPE__QUALIFIED_NAME:
				return getQualifiedName();
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
			case ComponentPackage.ENUM_PROPERTY_DECLARATION_TYPE__ENUM_ELEMENT:
				getEnumElement().clear();
				getEnumElement().addAll((Collection)newValue);
				return;
			case ComponentPackage.ENUM_PROPERTY_DECLARATION_TYPE__SOURCE_TYPE_MAPPING:
				setSourceTypeMapping((SourceTypeMappingType)newValue);
				return;
			case ComponentPackage.ENUM_PROPERTY_DECLARATION_TYPE__DEFAULT_VALUE:
				setDefaultValue((String)newValue);
				return;
			case ComponentPackage.ENUM_PROPERTY_DECLARATION_TYPE__QUALIFIED_NAME:
				setQualifiedName((String)newValue);
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
			case ComponentPackage.ENUM_PROPERTY_DECLARATION_TYPE__ENUM_ELEMENT:
				getEnumElement().clear();
				return;
			case ComponentPackage.ENUM_PROPERTY_DECLARATION_TYPE__SOURCE_TYPE_MAPPING:
				setSourceTypeMapping((SourceTypeMappingType)null);
				return;
			case ComponentPackage.ENUM_PROPERTY_DECLARATION_TYPE__DEFAULT_VALUE:
				setDefaultValue(DEFAULT_VALUE_EDEFAULT);
				return;
			case ComponentPackage.ENUM_PROPERTY_DECLARATION_TYPE__QUALIFIED_NAME:
				setQualifiedName(QUALIFIED_NAME_EDEFAULT);
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
			case ComponentPackage.ENUM_PROPERTY_DECLARATION_TYPE__ENUM_ELEMENT:
				return enumElement != null && !enumElement.isEmpty();
			case ComponentPackage.ENUM_PROPERTY_DECLARATION_TYPE__SOURCE_TYPE_MAPPING:
				return sourceTypeMapping != null;
			case ComponentPackage.ENUM_PROPERTY_DECLARATION_TYPE__DEFAULT_VALUE:
				return DEFAULT_VALUE_EDEFAULT == null ? defaultValue != null : !DEFAULT_VALUE_EDEFAULT.equals(defaultValue);
			case ComponentPackage.ENUM_PROPERTY_DECLARATION_TYPE__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? qualifiedName != null : !QUALIFIED_NAME_EDEFAULT.equals(qualifiedName);
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
		result.append(" (defaultValue: ");
		result.append(defaultValue);
		result.append(", qualifiedName: ");
		result.append(qualifiedName);
		result.append(')');
		return result.toString();
	}

	public EnumElementType findByValue(String value) {
		EnumElementType result = null;
		for (Iterator iter = enumElement.iterator(); iter.hasNext();) {
			EnumElementType curr = (EnumElementType) iter.next();
			if (curr.getValue().equals(value)) {
				result = curr;
				break;
			}
		}
		return result;
	}
	
	public EnumElementType findByDisplayValue(ILocalizedStrings strings, String value) {
		EnumElementType result = null;
		for (Iterator iter = enumElement.iterator(); iter.hasNext();) {
			EnumElementType curr = (EnumElementType) iter.next();
			Object displayValue = curr.getDisplayValue();
			if (displayValue != null) {
				displayValue = (strings.checkPercentKey(displayValue.toString()));
			}
			if (ObjectUtils.equals(displayValue, value)) {
				result = curr;
				break;
			}
		}
		return result;
	}
	

} //EnumPropertyDeclarationTypeImpl
