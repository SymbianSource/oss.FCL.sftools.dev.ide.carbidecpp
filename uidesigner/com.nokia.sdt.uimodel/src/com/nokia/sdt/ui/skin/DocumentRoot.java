/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.ui.skin;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.ui.skin.DocumentRoot#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.DocumentRoot#getBackgroundColor <em>Background Color</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.DocumentRoot#getEditorArea <em>Editor Area</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.DocumentRoot#getHotZone <em>Hot Zone</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.DocumentRoot#getSkin <em>Skin</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.ui.skin.SkinPackage#getDocumentRoot()
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface DocumentRoot extends EObject{
	/**
	 * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mixed</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mixed</em>' attribute list.
	 * @see com.nokia.sdt.ui.skin.SkinPackage#getDocumentRoot_Mixed()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='elementWildcard' name=':mixed'"
	 * @generated
	 */
	FeatureMap getMixed();

	/**
	 * Returns the value of the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XMLNS Prefix Map</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XMLNS Prefix Map</em>' map.
	 * @see com.nokia.sdt.ui.skin.SkinPackage#getDocumentRoot_XMLNSPrefixMap()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry" keyType="java.lang.String" valueType="java.lang.String" transient="true"
	 *        extendedMetaData="kind='attribute' name='xmlns:prefix'"
	 * @generated
	 */
	EMap getXMLNSPrefixMap();

	/**
	 * Returns the value of the '<em><b>XSI Schema Location</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XSI Schema Location</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XSI Schema Location</em>' map.
	 * @see com.nokia.sdt.ui.skin.SkinPackage#getDocumentRoot_XSISchemaLocation()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry" keyType="java.lang.String" valueType="java.lang.String" transient="true"
	 *        extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
	 * @generated
	 */
	EMap getXSISchemaLocation();

	/**
	 * Returns the value of the '<em><b>Background Color</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Defines the background color the editor should take to match the skin
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Background Color</em>' containment reference.
	 * @see #setBackgroundColor(BackgroundColorType)
	 * @see com.nokia.sdt.ui.skin.SkinPackage#getDocumentRoot_BackgroundColor()
	 * @model containment="true" resolveProxies="false" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='backgroundColor' namespace='##targetNamespace'"
	 * @generated
	 */
	BackgroundColorType getBackgroundColor();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.ui.skin.DocumentRoot#getBackgroundColor <em>Background Color</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Background Color</em>' containment reference.
	 * @see #getBackgroundColor()
	 * @generated
	 */
	void setBackgroundColor(BackgroundColorType value);

	/**
	 * Returns the value of the '<em><b>Editor Area</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Defines the size and location of the visual layout area
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Editor Area</em>' containment reference.
	 * @see #setEditorArea(EditorAreaType)
	 * @see com.nokia.sdt.ui.skin.SkinPackage#getDocumentRoot_EditorArea()
	 * @model containment="true" resolveProxies="false" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='editorArea' namespace='##targetNamespace'"
	 * @generated
	 */
	EditorAreaType getEditorArea();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.ui.skin.DocumentRoot#getEditorArea <em>Editor Area</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Editor Area</em>' containment reference.
	 * @see #getEditorArea()
	 * @generated
	 */
	void setEditorArea(EditorAreaType value);

	/**
	 * Returns the value of the '<em><b>Hot Zone</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Rectangular region of the skin that can have different visual states and receive mouse events
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Hot Zone</em>' containment reference.
	 * @see #setHotZone(HotZoneType)
	 * @see com.nokia.sdt.ui.skin.SkinPackage#getDocumentRoot_HotZone()
	 * @model containment="true" resolveProxies="false" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='hotZone' namespace='##targetNamespace'"
	 * @generated
	 */
	HotZoneType getHotZone();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.ui.skin.DocumentRoot#getHotZone <em>Hot Zone</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hot Zone</em>' containment reference.
	 * @see #getHotZone()
	 * @generated
	 */
	void setHotZone(HotZoneType value);

	/**
	 * Returns the value of the '<em><b>Skin</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Skin used to define the appearance and layout of the UI design surface
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Skin</em>' containment reference.
	 * @see #setSkin(SkinType)
	 * @see com.nokia.sdt.ui.skin.SkinPackage#getDocumentRoot_Skin()
	 * @model containment="true" resolveProxies="false" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='skin' namespace='##targetNamespace'"
	 * @generated
	 */
	SkinType getSkin();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.ui.skin.DocumentRoot#getSkin <em>Skin</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Skin</em>' containment reference.
	 * @see #getSkin()
	 * @generated
	 */
	void setSkin(SkinType value);

} // DocumentRoot
