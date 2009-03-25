/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.dm;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IArray Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.dm.IArrayMapping#getInstanceName <em>Instance Name</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.IArrayMapping#getPropertyId <em>Property Id</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.IArrayMapping#getMemberId <em>Member Id</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.IArrayMapping#getElements <em>Elements</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.dm.DmPackage#getIArrayMapping()
 * @model
 * @generated
 */
public interface IArrayMapping extends EObject {
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
	 * @see com.nokia.sdt.emf.dm.DmPackage#getIArrayMapping_InstanceName()
	 * @model unique="false"
	 * @generated
	 */
	String getInstanceName();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.dm.IArrayMapping#getInstanceName <em>Instance Name</em>}' attribute.
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
	 * @see com.nokia.sdt.emf.dm.DmPackage#getIArrayMapping_PropertyId()
	 * @model unique="false"
	 * @generated
	 */
	String getPropertyId();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.dm.IArrayMapping#getPropertyId <em>Property Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Id</em>' attribute.
	 * @see #getPropertyId()
	 * @generated
	 */
	void setPropertyId(String value);

	/**
	 * Returns the value of the '<em><b>Member Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Member Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Member Id</em>' attribute.
	 * @see #setMemberId(String)
	 * @see com.nokia.sdt.emf.dm.DmPackage#getIArrayMapping_MemberId()
	 * @model unique="false"
	 * @generated
	 */
	String getMemberId();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.dm.IArrayMapping#getMemberId <em>Member Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Member Id</em>' attribute.
	 * @see #getMemberId()
	 * @generated
	 */
	void setMemberId(String value);

	/**
	 * Returns the value of the '<em><b>Elements</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.dm.IElementMapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Elements</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Elements</em>' containment reference list.
	 * @see com.nokia.sdt.emf.dm.DmPackage#getIArrayMapping_Elements()
	 * @model type="com.nokia.sdt.emf.dm.IElementMapping" containment="true"
	 * @generated
	 */
	EList getElements();

} // IArrayMapping
