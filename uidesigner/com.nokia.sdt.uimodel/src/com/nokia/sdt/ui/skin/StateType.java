/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.ui.skin;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.ui.skin.StateType#getImageFilePath <em>Image File Path</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.StateType#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.ui.skin.SkinPackage#getStateType()
 * @model extendedMetaData="name='state_._type' kind='empty'"
 * @generated
 */
public interface StateType extends EObject{
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
	 * @see com.nokia.sdt.ui.skin.SkinPackage#getStateType_ImageFilePath()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='imageFilePath'"
	 * @generated
	 */
	String getImageFilePath();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.ui.skin.StateType#getImageFilePath <em>Image File Path</em>}' attribute.
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
	 * @see com.nokia.sdt.ui.skin.SkinPackage#getStateType_Name()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='name'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.ui.skin.StateType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * 
	 * @param path the directory in which the image files exist
	 */
	public void setDirectory(String path);

} // StateType
