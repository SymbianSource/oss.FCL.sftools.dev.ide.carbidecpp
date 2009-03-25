/**
 * <copyright>
 * </copyright>
 *
 */
package com.nokia.carbide.installpackages.gen.InstallPackages;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.nokia.carbide.installpackages.gen.InstallPackages.InstallPackagesFactory
 * @model kind="package"
 *        extendedMetaData="qualified='false'"
 * @generated
 */
public interface InstallPackagesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "InstallPackages";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/resource/com.nokia.carbide.remoteConnections/schema/InstallPackages.xsd";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "InstallPackages";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	InstallPackagesPackage eINSTANCE = com.nokia.carbide.installpackages.gen.InstallPackages.impl.InstallPackagesPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.nokia.carbide.installpackages.gen.InstallPackages.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.impl.DocumentRootImpl
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.impl.InstallPackagesPackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 0;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MIXED = 0;

	/**
	 * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

	/**
	 * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Packages</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PACKAGES = 3;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.installpackages.gen.InstallPackages.impl.PackagesTypeImpl <em>Packages Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.impl.PackagesTypeImpl
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.impl.InstallPackagesPackageImpl#getPackagesType()
	 * @generated
	 */
	int PACKAGES_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Package</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGES_TYPE__PACKAGE = 0;

	/**
	 * The number of structural features of the '<em>Packages Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGES_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.installpackages.gen.InstallPackages.impl.PackageTypeImpl <em>Package Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.impl.PackageTypeImpl
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.impl.InstallPackagesPackageImpl#getPackageType()
	 * @generated
	 */
	int PACKAGE_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Information</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_TYPE__INFORMATION = 0;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_TYPE__DISPLAY_NAME = 1;

	/**
	 * The feature id for the '<em><b>Install File Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_TYPE__INSTALL_FILE_PATH = 2;

	/**
	 * The feature id for the '<em><b>Package Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_TYPE__PACKAGE_VERSION = 3;

	/**
	 * The feature id for the '<em><b>Sdk Family</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_TYPE__SDK_FAMILY = 4;

	/**
	 * The feature id for the '<em><b>Sdk Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_TYPE__SDK_VERSION = 5;

	/**
	 * The number of structural features of the '<em>Package Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_TYPE_FEATURE_COUNT = 6;


	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.installpackages.gen.InstallPackages.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.carbide.installpackages.gen.InstallPackages.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link com.nokia.carbide.installpackages.gen.InstallPackages.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.DocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link com.nokia.carbide.installpackages.gen.InstallPackages.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.DocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.installpackages.gen.InstallPackages.DocumentRoot#getPackages <em>Packages</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Packages</em>'.
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.DocumentRoot#getPackages()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Packages();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.installpackages.gen.InstallPackages.PackagesType <em>Packages Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Packages Type</em>'.
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.PackagesType
	 * @generated
	 */
	EClass getPackagesType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.carbide.installpackages.gen.InstallPackages.PackagesType#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Package</em>'.
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.PackagesType#getPackage()
	 * @see #getPackagesType()
	 * @generated
	 */
	EReference getPackagesType_Package();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.installpackages.gen.InstallPackages.PackageType <em>Package Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Package Type</em>'.
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.PackageType
	 * @generated
	 */
	EClass getPackageType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.installpackages.gen.InstallPackages.PackageType#getInformation <em>Information</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Information</em>'.
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.PackageType#getInformation()
	 * @see #getPackageType()
	 * @generated
	 */
	EAttribute getPackageType_Information();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.installpackages.gen.InstallPackages.PackageType#getDisplayName <em>Display Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Name</em>'.
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.PackageType#getDisplayName()
	 * @see #getPackageType()
	 * @generated
	 */
	EAttribute getPackageType_DisplayName();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.installpackages.gen.InstallPackages.PackageType#getInstallFilePath <em>Install File Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Install File Path</em>'.
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.PackageType#getInstallFilePath()
	 * @see #getPackageType()
	 * @generated
	 */
	EAttribute getPackageType_InstallFilePath();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.installpackages.gen.InstallPackages.PackageType#getPackageVersion <em>Package Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Package Version</em>'.
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.PackageType#getPackageVersion()
	 * @see #getPackageType()
	 * @generated
	 */
	EAttribute getPackageType_PackageVersion();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.installpackages.gen.InstallPackages.PackageType#getSdkFamily <em>Sdk Family</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sdk Family</em>'.
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.PackageType#getSdkFamily()
	 * @see #getPackageType()
	 * @generated
	 */
	EAttribute getPackageType_SdkFamily();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.installpackages.gen.InstallPackages.PackageType#getSdkVersion <em>Sdk Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sdk Version</em>'.
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.PackageType#getSdkVersion()
	 * @see #getPackageType()
	 * @generated
	 */
	EAttribute getPackageType_SdkVersion();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	InstallPackagesFactory getInstallPackagesFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.nokia.carbide.installpackages.gen.InstallPackages.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.installpackages.gen.InstallPackages.impl.DocumentRootImpl
		 * @see com.nokia.carbide.installpackages.gen.InstallPackages.impl.InstallPackagesPackageImpl#getDocumentRoot()
		 * @generated
		 */
		EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

		/**
		 * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

		/**
		 * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getDocumentRoot_XMLNSPrefixMap();

		/**
		 * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getDocumentRoot_XSISchemaLocation();

		/**
		 * The meta object literal for the '<em><b>Packages</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__PACKAGES = eINSTANCE.getDocumentRoot_Packages();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.installpackages.gen.InstallPackages.impl.PackagesTypeImpl <em>Packages Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.installpackages.gen.InstallPackages.impl.PackagesTypeImpl
		 * @see com.nokia.carbide.installpackages.gen.InstallPackages.impl.InstallPackagesPackageImpl#getPackagesType()
		 * @generated
		 */
		EClass PACKAGES_TYPE = eINSTANCE.getPackagesType();

		/**
		 * The meta object literal for the '<em><b>Package</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PACKAGES_TYPE__PACKAGE = eINSTANCE.getPackagesType_Package();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.installpackages.gen.InstallPackages.impl.PackageTypeImpl <em>Package Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.installpackages.gen.InstallPackages.impl.PackageTypeImpl
		 * @see com.nokia.carbide.installpackages.gen.InstallPackages.impl.InstallPackagesPackageImpl#getPackageType()
		 * @generated
		 */
		EClass PACKAGE_TYPE = eINSTANCE.getPackageType();

		/**
		 * The meta object literal for the '<em><b>Information</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PACKAGE_TYPE__INFORMATION = eINSTANCE.getPackageType_Information();

		/**
		 * The meta object literal for the '<em><b>Display Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PACKAGE_TYPE__DISPLAY_NAME = eINSTANCE.getPackageType_DisplayName();

		/**
		 * The meta object literal for the '<em><b>Install File Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PACKAGE_TYPE__INSTALL_FILE_PATH = eINSTANCE.getPackageType_InstallFilePath();

		/**
		 * The meta object literal for the '<em><b>Package Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PACKAGE_TYPE__PACKAGE_VERSION = eINSTANCE.getPackageType_PackageVersion();

		/**
		 * The meta object literal for the '<em><b>Sdk Family</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PACKAGE_TYPE__SDK_FAMILY = eINSTANCE.getPackageType_SdkFamily();

		/**
		 * The meta object literal for the '<em><b>Sdk Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PACKAGE_TYPE__SDK_VERSION = eINSTANCE.getPackageType_SdkVersion();

	}

} //InstallPackagesPackage
