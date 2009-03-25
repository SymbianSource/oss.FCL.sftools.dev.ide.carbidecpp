/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.ui.skin;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.ui.skin.SkinType#getEditorArea <em>Editor Area</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.SkinType#getHotZone <em>Hot Zone</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.SkinType#getBackgroundColor <em>Background Color</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.SkinType#getHeight <em>Height</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.SkinType#getImageFilePath <em>Image File Path</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.SkinType#getName <em>Name</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.SkinType#getWidth <em>Width</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.ui.skin.SkinPackage#getSkinType()
 * @model extendedMetaData="name='skin_._type' kind='elementOnly'"
 * @generated
 */
public interface SkinType extends EObject{
	/**
	 * Returns the value of the '<em><b>Editor Area</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Editor Area</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Editor Area</em>' containment reference.
	 * @see #setEditorArea(EditorAreaType)
	 * @see com.nokia.sdt.ui.skin.SkinPackage#getSkinType_EditorArea()
	 * @model containment="true" resolveProxies="false" required="true"
	 *        extendedMetaData="kind='element' name='editorArea' namespace='##targetNamespace'"
	 * @generated
	 */
	EditorAreaType getEditorArea();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.ui.skin.SkinType#getEditorArea <em>Editor Area</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Editor Area</em>' containment reference.
	 * @see #getEditorArea()
	 * @generated
	 */
	void setEditorArea(EditorAreaType value);

	/**
	 * Returns the value of the '<em><b>Hot Zone</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.ui.skin.HotZoneType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hot Zone</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hot Zone</em>' containment reference list.
	 * @see com.nokia.sdt.ui.skin.SkinPackage#getSkinType_HotZone()
	 * @model type="com.nokia.sdt.ui.skin.HotZoneType" containment="true" resolveProxies="false"
	 *        extendedMetaData="kind='element' name='hotZone' namespace='##targetNamespace'"
	 * @generated
	 */
	EList getHotZone();

	/**
	 * Returns the value of the '<em><b>Background Color</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Background Color</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Background Color</em>' containment reference.
	 * @see #setBackgroundColor(BackgroundColorType)
	 * @see com.nokia.sdt.ui.skin.SkinPackage#getSkinType_BackgroundColor()
	 * @model containment="true" resolveProxies="false"
	 *        extendedMetaData="kind='element' name='backgroundColor' namespace='##targetNamespace'"
	 * @generated
	 */
	BackgroundColorType getBackgroundColor();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.ui.skin.SkinType#getBackgroundColor <em>Background Color</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Background Color</em>' containment reference.
	 * @see #getBackgroundColor()
	 * @generated
	 */
	void setBackgroundColor(BackgroundColorType value);

	/**
	 * Returns the value of the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Height</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Height</em>' attribute.
	 * @see #isSetHeight()
	 * @see #unsetHeight()
	 * @see #setHeight(short)
	 * @see com.nokia.sdt.ui.skin.SkinPackage#getSkinType_Height()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Short" required="true"
	 *        extendedMetaData="kind='attribute' name='height'"
	 * @generated
	 */
	short getHeight();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.ui.skin.SkinType#getHeight <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Height</em>' attribute.
	 * @see #isSetHeight()
	 * @see #unsetHeight()
	 * @see #getHeight()
	 * @generated
	 */
	void setHeight(short value);

	/**
	 * Unsets the value of the '{@link com.nokia.sdt.ui.skin.SkinType#getHeight <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetHeight()
	 * @see #getHeight()
	 * @see #setHeight(short)
	 * @generated
	 */
	void unsetHeight();

	/**
	 * Returns whether the value of the '{@link com.nokia.sdt.ui.skin.SkinType#getHeight <em>Height</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Height</em>' attribute is set.
	 * @see #unsetHeight()
	 * @see #getHeight()
	 * @see #setHeight(short)
	 * @generated
	 */
	boolean isSetHeight();

	/**
	 * Returns the value of the '<em><b>Image File Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Image File Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Image File Path</em>' attribute.
	 * @see #setImageFilePath(String)
	 * @see com.nokia.sdt.ui.skin.SkinPackage#getSkinType_ImageFilePath()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='imageFilePath'"
	 * @generated
	 */
	String getImageFilePath();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.ui.skin.SkinType#getImageFilePath <em>Image File Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Image File Path</em>' attribute.
	 * @see #getImageFilePath()
	 * @generated
	 */
	void setImageFilePath(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.nokia.sdt.ui.skin.SkinPackage#getSkinType_Name()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='name'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.ui.skin.SkinType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Width</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Width</em>' attribute.
	 * @see #isSetWidth()
	 * @see #unsetWidth()
	 * @see #setWidth(short)
	 * @see com.nokia.sdt.ui.skin.SkinPackage#getSkinType_Width()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Short" required="true"
	 *        extendedMetaData="kind='attribute' name='width'"
	 * @generated
	 */
	short getWidth();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.ui.skin.SkinType#getWidth <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Width</em>' attribute.
	 * @see #isSetWidth()
	 * @see #unsetWidth()
	 * @see #getWidth()
	 * @generated
	 */
	void setWidth(short value);

	/**
	 * Unsets the value of the '{@link com.nokia.sdt.ui.skin.SkinType#getWidth <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetWidth()
	 * @see #getWidth()
	 * @see #setWidth(short)
	 * @generated
	 */
	void unsetWidth();

	/**
	 * Returns whether the value of the '{@link com.nokia.sdt.ui.skin.SkinType#getWidth <em>Width</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Width</em>' attribute is set.
	 * @see #unsetWidth()
	 * @see #getWidth()
	 * @see #setWidth(short)
	 * @generated
	 */
	boolean isSetWidth();

	/**
	 * 
	 * @param path the directory in which the image files exist
	 */
	public void setDirectory(String path);

} // SkinType
