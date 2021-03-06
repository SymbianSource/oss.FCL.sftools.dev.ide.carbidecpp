/**
 * <copyright>
 * </copyright>
 *
 */
package com.nokia.carbide.installpackages.gen.InstallPackages.impl;

import com.nokia.carbide.installpackages.gen.InstallPackages.DocumentRoot;
import com.nokia.carbide.installpackages.gen.InstallPackages.InstallPackagesFactory;
import com.nokia.carbide.installpackages.gen.InstallPackages.InstallPackagesPackage;
import com.nokia.carbide.installpackages.gen.InstallPackages.PackageType;
import com.nokia.carbide.installpackages.gen.InstallPackages.PackagesType;

import com.nokia.carbide.installpackages.gen.InstallPackages.SDKFamilyType;
import com.nokia.carbide.installpackages.gen.InstallPackages.SDKVersionType;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class InstallPackagesPackageImpl extends EPackageImpl implements InstallPackagesPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass documentRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass packagesTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass packageTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sdkFamilyTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sdkVersionTypeEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.InstallPackagesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private InstallPackagesPackageImpl() {
		super(eNS_URI, InstallPackagesFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link InstallPackagesPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static InstallPackagesPackage init() {
		if (isInited) return (InstallPackagesPackage)EPackage.Registry.INSTANCE.getEPackage(InstallPackagesPackage.eNS_URI);

		// Obtain or create and register package
		InstallPackagesPackageImpl theInstallPackagesPackage = (InstallPackagesPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof InstallPackagesPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new InstallPackagesPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theInstallPackagesPackage.createPackageContents();

		// Initialize created meta-data
		theInstallPackagesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theInstallPackagesPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(InstallPackagesPackage.eNS_URI, theInstallPackagesPackage);
		return theInstallPackagesPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDocumentRoot() {
		return documentRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_Mixed() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_XMLNSPrefixMap() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_XSISchemaLocation() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Packages() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPackagesType() {
		return packagesTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPackagesType_SDKFamily() {
		return (EReference)packagesTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPackagesType_SDKVersion() {
		return (EReference)packagesTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPackagesType_Package() {
		return (EReference)packagesTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPackageType() {
		return packageTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPackageType_Information() {
		return (EAttribute)packageTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPackageType_DisplayName() {
		return (EAttribute)packageTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPackageType_InstallFilePath() {
		return (EAttribute)packageTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPackageType_PackageVersion() {
		return (EAttribute)packageTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPackageType_SdkFamily() {
		return (EAttribute)packageTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPackageType_SdkVersion() {
		return (EAttribute)packageTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSDKFamilyType() {
		return sdkFamilyTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSDKFamilyType_Order() {
		return (EAttribute)sdkFamilyTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSDKVersionType() {
		return sdkVersionTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSDKVersionType_Order() {
		return (EAttribute)sdkVersionTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InstallPackagesFactory getInstallPackagesFactory() {
		return (InstallPackagesFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		documentRootEClass = createEClass(DOCUMENT_ROOT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__PACKAGES);

		packagesTypeEClass = createEClass(PACKAGES_TYPE);
		createEReference(packagesTypeEClass, PACKAGES_TYPE__SDK_FAMILY);
		createEReference(packagesTypeEClass, PACKAGES_TYPE__SDK_VERSION);
		createEReference(packagesTypeEClass, PACKAGES_TYPE__PACKAGE);

		packageTypeEClass = createEClass(PACKAGE_TYPE);
		createEAttribute(packageTypeEClass, PACKAGE_TYPE__INFORMATION);
		createEAttribute(packageTypeEClass, PACKAGE_TYPE__DISPLAY_NAME);
		createEAttribute(packageTypeEClass, PACKAGE_TYPE__INSTALL_FILE_PATH);
		createEAttribute(packageTypeEClass, PACKAGE_TYPE__PACKAGE_VERSION);
		createEAttribute(packageTypeEClass, PACKAGE_TYPE__SDK_FAMILY);
		createEAttribute(packageTypeEClass, PACKAGE_TYPE__SDK_VERSION);

		sdkFamilyTypeEClass = createEClass(SDK_FAMILY_TYPE);
		createEAttribute(sdkFamilyTypeEClass, SDK_FAMILY_TYPE__ORDER);

		sdkVersionTypeEClass = createEClass(SDK_VERSION_TYPE);
		createEAttribute(sdkVersionTypeEClass, SDK_VERSION_TYPE__ORDER);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Packages(), this.getPackagesType(), null, "packages", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(packagesTypeEClass, PackagesType.class, "PackagesType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPackagesType_SDKFamily(), this.getSDKFamilyType(), null, "sDKFamily", null, 0, 1, PackagesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPackagesType_SDKVersion(), this.getSDKVersionType(), null, "sDKVersion", null, 0, 1, PackagesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPackagesType_Package(), this.getPackageType(), null, "package", null, 0, -1, PackagesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(packageTypeEClass, PackageType.class, "PackageType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPackageType_Information(), theXMLTypePackage.getString(), "information", null, 0, 1, PackageType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPackageType_DisplayName(), theXMLTypePackage.getString(), "displayName", null, 1, 1, PackageType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPackageType_InstallFilePath(), theXMLTypePackage.getString(), "installFilePath", null, 1, 1, PackageType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPackageType_PackageVersion(), theXMLTypePackage.getString(), "packageVersion", null, 1, 1, PackageType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPackageType_SdkFamily(), theXMLTypePackage.getString(), "sdkFamily", null, 1, 1, PackageType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPackageType_SdkVersion(), theXMLTypePackage.getString(), "sdkVersion", null, 1, 1, PackageType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sdkFamilyTypeEClass, SDKFamilyType.class, "SDKFamilyType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSDKFamilyType_Order(), theXMLTypePackage.getString(), "order", null, 1, 1, SDKFamilyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sdkVersionTypeEClass, SDKVersionType.class, "SDKVersionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSDKVersionType_Order(), theXMLTypePackage.getString(), "order", null, 1, 1, SDKVersionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";		
		addAnnotation
		  (this, 
		   source, 
		   new String[] {
			 "qualified", "false"
		   });		
		addAnnotation
		  (documentRootEClass, 
		   source, 
		   new String[] {
			 "name", "",
			 "kind", "mixed"
		   });		
		addAnnotation
		  (getDocumentRoot_Mixed(), 
		   source, 
		   new String[] {
			 "kind", "elementWildcard",
			 "name", ":mixed"
		   });		
		addAnnotation
		  (getDocumentRoot_XMLNSPrefixMap(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xmlns:prefix"
		   });		
		addAnnotation
		  (getDocumentRoot_XSISchemaLocation(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xsi:schemaLocation"
		   });		
		addAnnotation
		  (getDocumentRoot_Packages(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Packages",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (packagesTypeEClass, 
		   source, 
		   new String[] {
			 "name", "Packages_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getPackagesType_SDKFamily(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "SDKFamily",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getPackagesType_SDKVersion(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "SDKVersion",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getPackagesType_Package(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Package",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (packageTypeEClass, 
		   source, 
		   new String[] {
			 "name", "Package_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getPackageType_Information(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "information",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getPackageType_DisplayName(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "displayName",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getPackageType_InstallFilePath(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "installFilePath",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getPackageType_PackageVersion(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "packageVersion",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getPackageType_SdkFamily(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "sdkFamily",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getPackageType_SdkVersion(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "sdkVersion",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (sdkFamilyTypeEClass, 
		   source, 
		   new String[] {
			 "name", "SDKFamily_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getSDKFamilyType_Order(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "order",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (sdkVersionTypeEClass, 
		   source, 
		   new String[] {
			 "name", "SDKVersion_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getSDKVersionType_Order(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "order",
			 "namespace", "##targetNamespace"
		   });
	}

} //InstallPackagesPackageImpl
