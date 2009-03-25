/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.dm;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IEnum Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.dm.IEnumMapping#getInstanceName <em>Instance Name</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.IEnumMapping#getPropertyId <em>Property Id</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.IEnumMapping#getNameAlgorithm <em>Name Algorithm</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.IEnumMapping#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.dm.DmPackage#getIEnumMapping()
 * @model
 * @generated
 */
public interface IEnumMapping extends EObject{
	/**
	 * Returns the value of the '<em><b>Instance Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Instance Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Instance Name</em>' attribute.
	 * @see #setInstanceName(String)
	 * @see com.nokia.sdt.emf.dm.DmPackage#getIEnumMapping_InstanceName()
	 * @model unique="false"
	 * @generated
	 */
	String getInstanceName();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.dm.IEnumMapping#getInstanceName <em>Instance Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Instance Name</em>' attribute.
	 * @see #getInstanceName()
	 * @generated
	 */
	void setInstanceName(String value);

	/**
	 * Returns the value of the '<em><b>Property Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Id</em>' attribute.
	 * @see #setPropertyId(String)
	 * @see com.nokia.sdt.emf.dm.DmPackage#getIEnumMapping_PropertyId()
	 * @model unique="false"
	 * @generated
	 */
	String getPropertyId();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.dm.IEnumMapping#getPropertyId <em>Property Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Id</em>' attribute.
	 * @see #getPropertyId()
	 * @generated
	 */
	void setPropertyId(String value);

	/**
	 * Returns the value of the '<em><b>Name Algorithm</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name Algorithm</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name Algorithm</em>' attribute.
	 * @see #setNameAlgorithm(String)
	 * @see com.nokia.sdt.emf.dm.DmPackage#getIEnumMapping_NameAlgorithm()
	 * @model unique="false"
	 * @generated
	 */
	String getNameAlgorithm();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.dm.IEnumMapping#getNameAlgorithm <em>Name Algorithm</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name Algorithm</em>' attribute.
	 * @see #getNameAlgorithm()
	 * @generated
	 */
	void setNameAlgorithm(String value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see com.nokia.sdt.emf.dm.DmPackage#getIEnumMapping_Value()
	 * @model unique="false"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.dm.IEnumMapping#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

} // IEnumMapping
