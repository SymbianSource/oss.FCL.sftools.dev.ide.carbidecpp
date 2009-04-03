/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.ui.skin.impl;

import com.nokia.sdt.ui.skin.*;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.util.*;

import java.util.Collection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.ui.skin.impl.DocumentRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.impl.DocumentRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.impl.DocumentRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.impl.DocumentRootImpl#getBackgroundColor <em>Background Color</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.impl.DocumentRootImpl#getEditorArea <em>Editor Area</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.impl.DocumentRootImpl#getHotZone <em>Hot Zone</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.impl.DocumentRootImpl#getSkin <em>Skin</em>}</li>
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
	protected FeatureMap mixed = null;

	/**
	 * The cached value of the '{@link #getXMLNSPrefixMap() <em>XMLNS Prefix Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXMLNSPrefixMap()
	 * @generated
	 * @ordered
	 */
	protected EMap xMLNSPrefixMap = null;

	/**
	 * The cached value of the '{@link #getXSISchemaLocation() <em>XSI Schema Location</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXSISchemaLocation()
	 * @generated
	 * @ordered
	 */
	protected EMap xSISchemaLocation = null;

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
		return SkinPackage.eINSTANCE.getDocumentRoot();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, SkinPackage.DOCUMENT_ROOT__MIXED);
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
			xMLNSPrefixMap = new EcoreEMap(EcorePackage.eINSTANCE.getEStringToStringMapEntry(), EStringToStringMapEntryImpl.class, this, SkinPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
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
			xSISchemaLocation = new EcoreEMap(EcorePackage.eINSTANCE.getEStringToStringMapEntry(), EStringToStringMapEntryImpl.class, this, SkinPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		}
		return xSISchemaLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BackgroundColorType getBackgroundColor() {
		return (BackgroundColorType)getMixed().get(SkinPackage.eINSTANCE.getDocumentRoot_BackgroundColor(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBackgroundColor(BackgroundColorType newBackgroundColor, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(SkinPackage.eINSTANCE.getDocumentRoot_BackgroundColor(), newBackgroundColor, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBackgroundColor(BackgroundColorType newBackgroundColor) {
		((FeatureMap.Internal)getMixed()).set(SkinPackage.eINSTANCE.getDocumentRoot_BackgroundColor(), newBackgroundColor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EditorAreaType getEditorArea() {
		return (EditorAreaType)getMixed().get(SkinPackage.eINSTANCE.getDocumentRoot_EditorArea(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEditorArea(EditorAreaType newEditorArea, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(SkinPackage.eINSTANCE.getDocumentRoot_EditorArea(), newEditorArea, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEditorArea(EditorAreaType newEditorArea) {
		((FeatureMap.Internal)getMixed()).set(SkinPackage.eINSTANCE.getDocumentRoot_EditorArea(), newEditorArea);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HotZoneType getHotZone() {
		return (HotZoneType)getMixed().get(SkinPackage.eINSTANCE.getDocumentRoot_HotZone(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHotZone(HotZoneType newHotZone, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(SkinPackage.eINSTANCE.getDocumentRoot_HotZone(), newHotZone, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHotZone(HotZoneType newHotZone) {
		((FeatureMap.Internal)getMixed()).set(SkinPackage.eINSTANCE.getDocumentRoot_HotZone(), newHotZone);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SkinType getSkin() {
		return (SkinType)getMixed().get(SkinPackage.eINSTANCE.getDocumentRoot_Skin(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSkin(SkinType newSkin, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(SkinPackage.eINSTANCE.getDocumentRoot_Skin(), newSkin, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSkin(SkinType newSkin) {
		((FeatureMap.Internal)getMixed()).set(SkinPackage.eINSTANCE.getDocumentRoot_Skin(), newSkin);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case SkinPackage.DOCUMENT_ROOT__MIXED:
					return ((InternalEList)getMixed()).basicRemove(otherEnd, msgs);
				case SkinPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
					return ((InternalEList)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
				case SkinPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
					return ((InternalEList)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
				case SkinPackage.DOCUMENT_ROOT__BACKGROUND_COLOR:
					return basicSetBackgroundColor(null, msgs);
				case SkinPackage.DOCUMENT_ROOT__EDITOR_AREA:
					return basicSetEditorArea(null, msgs);
				case SkinPackage.DOCUMENT_ROOT__HOT_ZONE:
					return basicSetHotZone(null, msgs);
				case SkinPackage.DOCUMENT_ROOT__SKIN:
					return basicSetSkin(null, msgs);
				default:
					return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
			}
		}
		return eBasicSetContainer(null, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case SkinPackage.DOCUMENT_ROOT__MIXED:
				return getMixed();
			case SkinPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return getXMLNSPrefixMap();
			case SkinPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return getXSISchemaLocation();
			case SkinPackage.DOCUMENT_ROOT__BACKGROUND_COLOR:
				return getBackgroundColor();
			case SkinPackage.DOCUMENT_ROOT__EDITOR_AREA:
				return getEditorArea();
			case SkinPackage.DOCUMENT_ROOT__HOT_ZONE:
				return getHotZone();
			case SkinPackage.DOCUMENT_ROOT__SKIN:
				return getSkin();
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case SkinPackage.DOCUMENT_ROOT__MIXED:
				getMixed().clear();
				getMixed().addAll((Collection)newValue);
				return;
			case SkinPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				getXMLNSPrefixMap().clear();
				getXMLNSPrefixMap().addAll((Collection)newValue);
				return;
			case SkinPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				getXSISchemaLocation().clear();
				getXSISchemaLocation().addAll((Collection)newValue);
				return;
			case SkinPackage.DOCUMENT_ROOT__BACKGROUND_COLOR:
				setBackgroundColor((BackgroundColorType)newValue);
				return;
			case SkinPackage.DOCUMENT_ROOT__EDITOR_AREA:
				setEditorArea((EditorAreaType)newValue);
				return;
			case SkinPackage.DOCUMENT_ROOT__HOT_ZONE:
				setHotZone((HotZoneType)newValue);
				return;
			case SkinPackage.DOCUMENT_ROOT__SKIN:
				setSkin((SkinType)newValue);
				return;
		}
		eDynamicSet(eFeature, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case SkinPackage.DOCUMENT_ROOT__MIXED:
				getMixed().clear();
				return;
			case SkinPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				getXMLNSPrefixMap().clear();
				return;
			case SkinPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				getXSISchemaLocation().clear();
				return;
			case SkinPackage.DOCUMENT_ROOT__BACKGROUND_COLOR:
				setBackgroundColor((BackgroundColorType)null);
				return;
			case SkinPackage.DOCUMENT_ROOT__EDITOR_AREA:
				setEditorArea((EditorAreaType)null);
				return;
			case SkinPackage.DOCUMENT_ROOT__HOT_ZONE:
				setHotZone((HotZoneType)null);
				return;
			case SkinPackage.DOCUMENT_ROOT__SKIN:
				setSkin((SkinType)null);
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case SkinPackage.DOCUMENT_ROOT__MIXED:
				return mixed != null && !mixed.isEmpty();
			case SkinPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
			case SkinPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
			case SkinPackage.DOCUMENT_ROOT__BACKGROUND_COLOR:
				return getBackgroundColor() != null;
			case SkinPackage.DOCUMENT_ROOT__EDITOR_AREA:
				return getEditorArea() != null;
			case SkinPackage.DOCUMENT_ROOT__HOT_ZONE:
				return getHotZone() != null;
			case SkinPackage.DOCUMENT_ROOT__SKIN:
				return getSkin() != null;
		}
		return eDynamicIsSet(eFeature);
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
