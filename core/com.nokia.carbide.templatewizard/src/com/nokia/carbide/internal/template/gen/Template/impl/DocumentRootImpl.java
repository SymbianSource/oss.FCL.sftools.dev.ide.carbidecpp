/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.template.gen.Template.impl;

import com.nokia.carbide.internal.template.gen.Template.*;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.util.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.impl.DocumentRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.impl.DocumentRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.impl.DocumentRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.impl.DocumentRootImpl#getBaseField <em>Base Field</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.impl.DocumentRootImpl#getFilenameField <em>Filename Field</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.impl.DocumentRootImpl#getTemplate <em>Template</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.impl.DocumentRootImpl#getTextField <em>Text Field</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.impl.DocumentRootImpl#getUidField <em>Uid Field</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DocumentRootImpl extends EObjectImpl implements DocumentRoot {
	/**
	 * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMixed()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap mixed;

	/**
	 * The cached value of the '{@link #getXMLNSPrefixMap() <em>XMLNS Prefix Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXMLNSPrefixMap()
	 * @generated
	 * @ordered
	 */
	protected EMap xMLNSPrefixMap;

	/**
	 * The cached value of the '{@link #getXSISchemaLocation() <em>XSI Schema Location</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXSISchemaLocation()
	 * @generated
	 * @ordered
	 */
	protected EMap xSISchemaLocation;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DocumentRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return TemplatePackage.Literals.DOCUMENT_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, TemplatePackage.DOCUMENT_ROOT__MIXED);
		}
		return mixed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap getXMLNSPrefixMap() {
		if (xMLNSPrefixMap == null) {
			xMLNSPrefixMap = new EcoreEMap(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, TemplatePackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		}
		return xMLNSPrefixMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap getXSISchemaLocation() {
		if (xSISchemaLocation == null) {
			xSISchemaLocation = new EcoreEMap(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, TemplatePackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		}
		return xSISchemaLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BaseFieldType getBaseField() {
		return (BaseFieldType)getMixed().get(TemplatePackage.Literals.DOCUMENT_ROOT__BASE_FIELD, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBaseField(BaseFieldType newBaseField, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(TemplatePackage.Literals.DOCUMENT_ROOT__BASE_FIELD, newBaseField, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FilenameFieldType getFilenameField() {
		return (FilenameFieldType)getMixed().get(TemplatePackage.Literals.DOCUMENT_ROOT__FILENAME_FIELD, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFilenameField(FilenameFieldType newFilenameField, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(TemplatePackage.Literals.DOCUMENT_ROOT__FILENAME_FIELD, newFilenameField, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFilenameField(FilenameFieldType newFilenameField) {
		((FeatureMap.Internal)getMixed()).set(TemplatePackage.Literals.DOCUMENT_ROOT__FILENAME_FIELD, newFilenameField);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TemplateType getTemplate() {
		return (TemplateType)getMixed().get(TemplatePackage.Literals.DOCUMENT_ROOT__TEMPLATE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTemplate(TemplateType newTemplate, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(TemplatePackage.Literals.DOCUMENT_ROOT__TEMPLATE, newTemplate, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTemplate(TemplateType newTemplate) {
		((FeatureMap.Internal)getMixed()).set(TemplatePackage.Literals.DOCUMENT_ROOT__TEMPLATE, newTemplate);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TextFieldType getTextField() {
		return (TextFieldType)getMixed().get(TemplatePackage.Literals.DOCUMENT_ROOT__TEXT_FIELD, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTextField(TextFieldType newTextField, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(TemplatePackage.Literals.DOCUMENT_ROOT__TEXT_FIELD, newTextField, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTextField(TextFieldType newTextField) {
		((FeatureMap.Internal)getMixed()).set(TemplatePackage.Literals.DOCUMENT_ROOT__TEXT_FIELD, newTextField);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UidFieldType getUidField() {
		return (UidFieldType)getMixed().get(TemplatePackage.Literals.DOCUMENT_ROOT__UID_FIELD, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUidField(UidFieldType newUidField, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(TemplatePackage.Literals.DOCUMENT_ROOT__UID_FIELD, newUidField, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUidField(UidFieldType newUidField) {
		((FeatureMap.Internal)getMixed()).set(TemplatePackage.Literals.DOCUMENT_ROOT__UID_FIELD, newUidField);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TemplatePackage.DOCUMENT_ROOT__MIXED:
				return ((InternalEList)getMixed()).basicRemove(otherEnd, msgs);
			case TemplatePackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return ((InternalEList)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
			case TemplatePackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return ((InternalEList)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
			case TemplatePackage.DOCUMENT_ROOT__BASE_FIELD:
				return basicSetBaseField(null, msgs);
			case TemplatePackage.DOCUMENT_ROOT__FILENAME_FIELD:
				return basicSetFilenameField(null, msgs);
			case TemplatePackage.DOCUMENT_ROOT__TEMPLATE:
				return basicSetTemplate(null, msgs);
			case TemplatePackage.DOCUMENT_ROOT__TEXT_FIELD:
				return basicSetTextField(null, msgs);
			case TemplatePackage.DOCUMENT_ROOT__UID_FIELD:
				return basicSetUidField(null, msgs);
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
			case TemplatePackage.DOCUMENT_ROOT__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case TemplatePackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				if (coreType) return getXMLNSPrefixMap();
				else return getXMLNSPrefixMap().map();
			case TemplatePackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				if (coreType) return getXSISchemaLocation();
				else return getXSISchemaLocation().map();
			case TemplatePackage.DOCUMENT_ROOT__BASE_FIELD:
				return getBaseField();
			case TemplatePackage.DOCUMENT_ROOT__FILENAME_FIELD:
				return getFilenameField();
			case TemplatePackage.DOCUMENT_ROOT__TEMPLATE:
				return getTemplate();
			case TemplatePackage.DOCUMENT_ROOT__TEXT_FIELD:
				return getTextField();
			case TemplatePackage.DOCUMENT_ROOT__UID_FIELD:
				return getUidField();
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
			case TemplatePackage.DOCUMENT_ROOT__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case TemplatePackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
				return;
			case TemplatePackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
				return;
			case TemplatePackage.DOCUMENT_ROOT__FILENAME_FIELD:
				setFilenameField((FilenameFieldType)newValue);
				return;
			case TemplatePackage.DOCUMENT_ROOT__TEMPLATE:
				setTemplate((TemplateType)newValue);
				return;
			case TemplatePackage.DOCUMENT_ROOT__TEXT_FIELD:
				setTextField((TextFieldType)newValue);
				return;
			case TemplatePackage.DOCUMENT_ROOT__UID_FIELD:
				setUidField((UidFieldType)newValue);
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
			case TemplatePackage.DOCUMENT_ROOT__MIXED:
				getMixed().clear();
				return;
			case TemplatePackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				getXMLNSPrefixMap().clear();
				return;
			case TemplatePackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				getXSISchemaLocation().clear();
				return;
			case TemplatePackage.DOCUMENT_ROOT__FILENAME_FIELD:
				setFilenameField((FilenameFieldType)null);
				return;
			case TemplatePackage.DOCUMENT_ROOT__TEMPLATE:
				setTemplate((TemplateType)null);
				return;
			case TemplatePackage.DOCUMENT_ROOT__TEXT_FIELD:
				setTextField((TextFieldType)null);
				return;
			case TemplatePackage.DOCUMENT_ROOT__UID_FIELD:
				setUidField((UidFieldType)null);
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
			case TemplatePackage.DOCUMENT_ROOT__MIXED:
				return mixed != null && !mixed.isEmpty();
			case TemplatePackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
			case TemplatePackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
			case TemplatePackage.DOCUMENT_ROOT__BASE_FIELD:
				return getBaseField() != null;
			case TemplatePackage.DOCUMENT_ROOT__FILENAME_FIELD:
				return getFilenameField() != null;
			case TemplatePackage.DOCUMENT_ROOT__TEMPLATE:
				return getTemplate() != null;
			case TemplatePackage.DOCUMENT_ROOT__TEXT_FIELD:
				return getTextField() != null;
			case TemplatePackage.DOCUMENT_ROOT__UID_FIELD:
				return getUidField() != null;
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
		result.append(" (mixed: ");
		result.append(mixed);
		result.append(')');
		return result.toString();
	}

} //DocumentRootImpl