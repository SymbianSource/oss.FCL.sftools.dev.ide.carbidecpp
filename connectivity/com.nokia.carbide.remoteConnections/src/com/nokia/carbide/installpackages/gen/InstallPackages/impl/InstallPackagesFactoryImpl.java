/**
 * <copyright>
 * </copyright>
 *
 */
package com.nokia.carbide.installpackages.gen.InstallPackages.impl;

import com.nokia.carbide.installpackages.gen.InstallPackages.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class InstallPackagesFactoryImpl extends EFactoryImpl implements InstallPackagesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static InstallPackagesFactory init() {
		try {
			InstallPackagesFactory theInstallPackagesFactory = (InstallPackagesFactory)EPackage.Registry.INSTANCE.getEFactory("platform:/resource/com.nokia.carbide.remoteConnections/schema/InstallPackages.xsd"); 
			if (theInstallPackagesFactory != null) {
				return theInstallPackagesFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new InstallPackagesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InstallPackagesFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case InstallPackagesPackage.DOCUMENT_ROOT: return createDocumentRoot();
			case InstallPackagesPackage.PACKAGES_TYPE: return createPackagesType();
			case InstallPackagesPackage.PACKAGE_TYPE: return createPackageType();
			case InstallPackagesPackage.SDK_FAMILY_TYPE: return createSDKFamilyType();
			case InstallPackagesPackage.SDK_VERSION_TYPE: return createSDKVersionType();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocumentRoot createDocumentRoot() {
		DocumentRootImpl documentRoot = new DocumentRootImpl();
		return documentRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PackagesType createPackagesType() {
		PackagesTypeImpl packagesType = new PackagesTypeImpl();
		return packagesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PackageType createPackageType() {
		PackageTypeImpl packageType = new PackageTypeImpl();
		return packageType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SDKFamilyType createSDKFamilyType() {
		SDKFamilyTypeImpl sdkFamilyType = new SDKFamilyTypeImpl();
		return sdkFamilyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SDKVersionType createSDKVersionType() {
		SDKVersionTypeImpl sdkVersionType = new SDKVersionTypeImpl();
		return sdkVersionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InstallPackagesPackage getInstallPackagesPackage() {
		return (InstallPackagesPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static InstallPackagesPackage getPackage() {
		return InstallPackagesPackage.eINSTANCE;
	}

} //InstallPackagesFactoryImpl
