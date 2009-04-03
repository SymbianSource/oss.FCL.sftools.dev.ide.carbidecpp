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
