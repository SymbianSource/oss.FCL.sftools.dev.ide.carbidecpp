/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.CompoundPropertyDeclarationType;
import com.nokia.sdt.emf.component.SourceTypeMappingType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Compound Property Declaration Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.CompoundPropertyDeclarationTypeImpl#getAbstractPropertyGroup <em>Abstract Property Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.CompoundPropertyDeclarationTypeImpl#getAbstractProperty <em>Abstract Property</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.CompoundPropertyDeclarationTypeImpl#getSourceTypeMapping <em>Source Type Mapping</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.CompoundPropertyDeclarationTypeImpl#getConverterClass <em>Converter Class</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.CompoundPropertyDeclarationTypeImpl#getEditableType <em>Editable Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.CompoundPropertyDeclarationTypeImpl#getEditorClass <em>Editor Class</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.CompoundPropertyDeclarationTypeImpl#getQualifiedName <em>Qualified Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CompoundPropertyDeclarationTypeImpl extends EObjectImpl implements CompoundPropertyDeclarationType {
	/**
	 * The cached value of the '{@link #getAbstractPropertyGroup() <em>Abstract Property Group</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAbstractPropertyGroup()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap abstractPropertyGroup;

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
	 * The default value of the '{@link #getConverterClass() <em>Converter Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConverterClass()
	 * @generated
	 * @ordered
	 */
	protected static final String CONVERTER_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getConverterClass() <em>Converter Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConverterClass()
	 * @generated
	 * @ordered
	 */
	protected String converterClass = CONVERTER_CLASS_EDEFAULT;

	/**
	 * The default value of the '{@link #getEditableType() <em>Editable Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditableType()
	 * @generated
	 * @ordered
	 */
	protected static final String EDITABLE_TYPE_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getEditableType() <em>Editable Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditableType()
	 * @generated
	 * @ordered
	 */
	protected String editableType = EDITABLE_TYPE_EDEFAULT;

	/**
	 * This is true if the Editable Type attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean editableTypeESet;

	/**
	 * The default value of the '{@link #getEditorClass() <em>Editor Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditorClass()
	 * @generated
	 * @ordered
	 */
	protected static final String EDITOR_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEditorClass() <em>Editor Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditorClass()
	 * @generated
	 * @ordered
	 */
	protected String editorClass = EDITOR_CLASS_EDEFAULT;

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
	protected CompoundPropertyDeclarationTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.COMPOUND_PROPERTY_DECLARATION_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getAbstractPropertyGroup() {
		if (abstractPropertyGroup == null) {
			abstractPropertyGroup = new BasicFeatureMap(this, ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__ABSTRACT_PROPERTY_GROUP);
		}
		return abstractPropertyGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getAbstractProperty() {
		return getAbstractPropertyGroup().list(ComponentPackage.Literals.COMPOUND_PROPERTY_DECLARATION_TYPE__ABSTRACT_PROPERTY);
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__SOURCE_TYPE_MAPPING, oldSourceTypeMapping, newSourceTypeMapping);
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
				msgs = ((InternalEObject)sourceTypeMapping).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__SOURCE_TYPE_MAPPING, null, msgs);
			if (newSourceTypeMapping != null)
				msgs = ((InternalEObject)newSourceTypeMapping).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__SOURCE_TYPE_MAPPING, null, msgs);
			msgs = basicSetSourceTypeMapping(newSourceTypeMapping, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__SOURCE_TYPE_MAPPING, newSourceTypeMapping, newSourceTypeMapping));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getConverterClass() {
		return converterClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConverterClass(String newConverterClass) {
		String oldConverterClass = converterClass;
		converterClass = newConverterClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__CONVERTER_CLASS, oldConverterClass, converterClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEditableType() {
		return editableType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEditableType(String newEditableType) {
		String oldEditableType = editableType;
		editableType = newEditableType;
		boolean oldEditableTypeESet = editableTypeESet;
		editableTypeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__EDITABLE_TYPE, oldEditableType, editableType, !oldEditableTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetEditableType() {
		String oldEditableType = editableType;
		boolean oldEditableTypeESet = editableTypeESet;
		editableType = EDITABLE_TYPE_EDEFAULT;
		editableTypeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__EDITABLE_TYPE, oldEditableType, EDITABLE_TYPE_EDEFAULT, oldEditableTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetEditableType() {
		return editableTypeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEditorClass() {
		return editorClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEditorClass(String newEditorClass) {
		String oldEditorClass = editorClass;
		editorClass = newEditorClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__EDITOR_CLASS, oldEditorClass, editorClass));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__QUALIFIED_NAME, oldQualifiedName, qualifiedName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__ABSTRACT_PROPERTY_GROUP:
				return ((InternalEList)getAbstractPropertyGroup()).basicRemove(otherEnd, msgs);
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__ABSTRACT_PROPERTY:
				return ((InternalEList)getAbstractProperty()).basicRemove(otherEnd, msgs);
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__SOURCE_TYPE_MAPPING:
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
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__ABSTRACT_PROPERTY_GROUP:
				if (coreType) return getAbstractPropertyGroup();
				return ((FeatureMap.Internal)getAbstractPropertyGroup()).getWrapper();
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__ABSTRACT_PROPERTY:
				return getAbstractProperty();
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__SOURCE_TYPE_MAPPING:
				return getSourceTypeMapping();
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__CONVERTER_CLASS:
				return getConverterClass();
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__EDITABLE_TYPE:
				return getEditableType();
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__EDITOR_CLASS:
				return getEditorClass();
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__QUALIFIED_NAME:
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
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__ABSTRACT_PROPERTY_GROUP:
				((FeatureMap.Internal)getAbstractPropertyGroup()).set(newValue);
				return;
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__SOURCE_TYPE_MAPPING:
				setSourceTypeMapping((SourceTypeMappingType)newValue);
				return;
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__CONVERTER_CLASS:
				setConverterClass((String)newValue);
				return;
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__EDITABLE_TYPE:
				setEditableType((String)newValue);
				return;
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__EDITOR_CLASS:
				setEditorClass((String)newValue);
				return;
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__QUALIFIED_NAME:
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
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__ABSTRACT_PROPERTY_GROUP:
				getAbstractPropertyGroup().clear();
				return;
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__SOURCE_TYPE_MAPPING:
				setSourceTypeMapping((SourceTypeMappingType)null);
				return;
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__CONVERTER_CLASS:
				setConverterClass(CONVERTER_CLASS_EDEFAULT);
				return;
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__EDITABLE_TYPE:
				unsetEditableType();
				return;
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__EDITOR_CLASS:
				setEditorClass(EDITOR_CLASS_EDEFAULT);
				return;
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__QUALIFIED_NAME:
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
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__ABSTRACT_PROPERTY_GROUP:
				return abstractPropertyGroup != null && !abstractPropertyGroup.isEmpty();
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__ABSTRACT_PROPERTY:
				return !getAbstractProperty().isEmpty();
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__SOURCE_TYPE_MAPPING:
				return sourceTypeMapping != null;
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__CONVERTER_CLASS:
				return CONVERTER_CLASS_EDEFAULT == null ? converterClass != null : !CONVERTER_CLASS_EDEFAULT.equals(converterClass);
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__EDITABLE_TYPE:
				return isSetEditableType();
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__EDITOR_CLASS:
				return EDITOR_CLASS_EDEFAULT == null ? editorClass != null : !EDITOR_CLASS_EDEFAULT.equals(editorClass);
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE__QUALIFIED_NAME:
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
		result.append(" (abstractPropertyGroup: ");
		result.append(abstractPropertyGroup);
		result.append(", converterClass: ");
		result.append(converterClass);
		result.append(", editableType: ");
		if (editableTypeESet) result.append(editableType); else result.append("<unset>");
		result.append(", editorClass: ");
		result.append(editorClass);
		result.append(", qualifiedName: ");
		result.append(qualifiedName);
		result.append(')');
		return result.toString();
	}

} //CompoundPropertyDeclarationTypeImpl
