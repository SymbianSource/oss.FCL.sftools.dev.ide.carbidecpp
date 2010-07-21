/**
 * <copyright>
 * </copyright>
 *
 */
package com.nokia.carbide.installpackages.gen.InstallPackages;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.nokia.carbide.installpackages.gen.InstallPackages.InstallPackagesPackage
 * @generated
 */
public interface InstallPackagesFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	InstallPackagesFactory eINSTANCE = com.nokia.carbide.installpackages.gen.InstallPackages.impl.InstallPackagesFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Document Root</em>'.
	 * @generated
	 */
	DocumentRoot createDocumentRoot();

	/**
	 * Returns a new object of class '<em>Packages Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Packages Type</em>'.
	 * @generated
	 */
	PackagesType createPackagesType();

	/**
	 * Returns a new object of class '<em>Package Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Package Type</em>'.
	 * @generated
	 */
	PackageType createPackageType();

	/**
	 * Returns a new object of class '<em>SDK Family Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>SDK Family Type</em>'.
	 * @generated
	 */
	SDKFamilyType createSDKFamilyType();

	/**
	 * Returns a new object of class '<em>SDK Version Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>SDK Version Type</em>'.
	 * @generated
	 */
	SDKVersionType createSDKVersionType();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	InstallPackagesPackage getInstallPackagesPackage();

} //InstallPackagesFactory
