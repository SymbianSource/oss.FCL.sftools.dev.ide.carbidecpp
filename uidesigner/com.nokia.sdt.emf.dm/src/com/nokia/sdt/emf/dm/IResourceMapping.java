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
 * A representation of the model object '<em><b>IResource Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.dm.IResourceMapping#getInstanceName <em>Instance Name</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.IResourceMapping#getRsrcFile <em>Rsrc File</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.IResourceMapping#getRsrcId <em>Rsrc Id</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.IResourceMapping#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.dm.DmPackage#getIResourceMapping()
 * @model
 * @generated
 */
public interface IResourceMapping extends EObject{
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
	 * @see com.nokia.sdt.emf.dm.DmPackage#getIResourceMapping_InstanceName()
	 * @model unique="false"
	 * @generated
	 */
	String getInstanceName();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.dm.IResourceMapping#getInstanceName <em>Instance Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Instance Name</em>' attribute.
	 * @see #getInstanceName()
	 * @generated
	 */
	void setInstanceName(String value);

	/**
	 * Returns the value of the '<em><b>Rsrc File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rsrc File</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rsrc File</em>' attribute.
	 * @see #setRsrcFile(String)
	 * @see com.nokia.sdt.emf.dm.DmPackage#getIResourceMapping_RsrcFile()
	 * @model
	 * @generated
	 */
	String getRsrcFile();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.dm.IResourceMapping#getRsrcFile <em>Rsrc File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rsrc File</em>' attribute.
	 * @see #getRsrcFile()
	 * @generated
	 */
	void setRsrcFile(String value);

	/**
	 * Returns the value of the '<em><b>Rsrc Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rsrc Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rsrc Id</em>' attribute.
	 * @see #setRsrcId(String)
	 * @see com.nokia.sdt.emf.dm.DmPackage#getIResourceMapping_RsrcId()
	 * @model unique="false"
	 * @generated
	 */
	String getRsrcId();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.dm.IResourceMapping#getRsrcId <em>Rsrc Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rsrc Id</em>' attribute.
	 * @see #getRsrcId()
	 * @generated
	 */
	void setRsrcId(String value);

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
	 * @see com.nokia.sdt.emf.dm.DmPackage#getIResourceMapping_Value()
	 * @model
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.dm.IResourceMapping#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

} // IResourceMapping
