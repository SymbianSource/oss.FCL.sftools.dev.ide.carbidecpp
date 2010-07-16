/**
 * <copyright>
 * </copyright>
 *
 */
package com.nokia.carbide.installpackages.gen.InstallPackages;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Packages Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.installpackages.gen.InstallPackages.PackagesType#getSDKFamily <em>SDK Family</em>}</li>
 *   <li>{@link com.nokia.carbide.installpackages.gen.InstallPackages.PackagesType#getSDKVersion <em>SDK Version</em>}</li>
 *   <li>{@link com.nokia.carbide.installpackages.gen.InstallPackages.PackagesType#getPackage <em>Package</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.installpackages.gen.InstallPackages.InstallPackagesPackage#getPackagesType()
 * @model extendedMetaData="name='Packages_._type' kind='elementOnly'"
 * @generated
 */
public interface PackagesType extends EObject {
	/**
	 * Returns the value of the '<em><b>SDK Family</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>SDK Family</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>SDK Family</em>' containment reference.
	 * @see #setSDKFamily(SDKFamilyType)
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.InstallPackagesPackage#getPackagesType_SDKFamily()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='SDKFamily' namespace='##targetNamespace'"
	 * @generated
	 */
	SDKFamilyType getSDKFamily();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.installpackages.gen.InstallPackages.PackagesType#getSDKFamily <em>SDK Family</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>SDK Family</em>' containment reference.
	 * @see #getSDKFamily()
	 * @generated
	 */
	void setSDKFamily(SDKFamilyType value);

	/**
	 * Returns the value of the '<em><b>SDK Version</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>SDK Version</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>SDK Version</em>' containment reference.
	 * @see #setSDKVersion(SDKVersionType)
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.InstallPackagesPackage#getPackagesType_SDKVersion()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='SDKVersion' namespace='##targetNamespace'"
	 * @generated
	 */
	SDKVersionType getSDKVersion();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.installpackages.gen.InstallPackages.PackagesType#getSDKVersion <em>SDK Version</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>SDK Version</em>' containment reference.
	 * @see #getSDKVersion()
	 * @generated
	 */
	void setSDKVersion(SDKVersionType value);

	/**
	 * Returns the value of the '<em><b>Package</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.installpackages.gen.InstallPackages.PackageType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Package</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package</em>' containment reference list.
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.InstallPackagesPackage#getPackagesType_Package()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Package' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<PackageType> getPackage();

} // PackagesType
