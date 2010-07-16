/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.carbide.installpackages.gen.InstallPackages;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>SDK Version Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.installpackages.gen.InstallPackages.SDKVersionType#getOrder <em>Order</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.installpackages.gen.InstallPackages.InstallPackagesPackage#getSDKVersionType()
 * @model extendedMetaData="name='SDKVersion_._type' kind='empty'"
 * @generated
 */
public interface SDKVersionType extends EObject {
	/**
	 * Returns the value of the '<em><b>Order</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Order</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Order</em>' attribute.
	 * @see #setOrder(String)
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.InstallPackagesPackage#getSDKVersionType_Order()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='order' namespace='##targetNamespace'"
	 * @generated
	 */
	String getOrder();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.installpackages.gen.InstallPackages.SDKVersionType#getOrder <em>Order</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Order</em>' attribute.
	 * @see #getOrder()
	 * @generated
	 */
	void setOrder(String value);

} // SDKVersionType
