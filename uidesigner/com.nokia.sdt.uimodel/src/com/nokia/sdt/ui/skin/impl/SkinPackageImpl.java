/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.ui.skin.impl;

import com.nokia.sdt.ui.skin.*;

import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.ecore.xml.type.impl.XMLTypePackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SkinPackageImpl extends EPackageImpl implements SkinPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass backgroundColorTypeEClass = null;

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
	private EClass editorAreaTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass hotZoneTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass skinTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateTypeEClass = null;

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
	 * @see com.nokia.sdt.ui.skin.SkinPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SkinPackageImpl() {
		super(eNS_URI, SkinFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this
	 * model, and for any others upon which it depends.  Simple
	 * dependencies are satisfied by calling this method on all
	 * dependent packages before doing anything else.  This method drives
	 * initialization for interdependent packages directly, in parallel
	 * with this package, itself.
	 * <p>Of this package and its interdependencies, all packages which
	 * have not yet been registered by their URI values are first created
	 * and registered.  The packages are then initialized in two steps:
	 * meta-model objects for all of the packages are created before any
	 * are initialized, since one package's meta-model objects may refer to
	 * those of another.
	 * <p>Invocation of this method will not affect any packages that have
	 * already been initialized.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SkinPackage init() {
		if (isInited) return (SkinPackage)EPackage.Registry.INSTANCE.getEPackage(SkinPackage.eNS_URI);

		// Obtain or create and register package
		SkinPackageImpl theSkinPackage = (SkinPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof SkinPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new SkinPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackageImpl.init();

		// Create package meta-data objects
		theSkinPackage.createPackageContents();

		// Initialize created meta-data
		theSkinPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSkinPackage.freeze();

		return theSkinPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBackgroundColorType() {
		return backgroundColorTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBackgroundColorType_Blue() {
		return (EAttribute)backgroundColorTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBackgroundColorType_Green() {
		return (EAttribute)backgroundColorTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBackgroundColorType_Red() {
		return (EAttribute)backgroundColorTypeEClass.getEStructuralFeatures().get(2);
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
	public EReference getDocumentRoot_BackgroundColor() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_EditorArea() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_HotZone() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Skin() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEditorAreaType() {
		return editorAreaTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEditorAreaType_Height() {
		return (EAttribute)editorAreaTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEditorAreaType_Width() {
		return (EAttribute)editorAreaTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEditorAreaType_X() {
		return (EAttribute)editorAreaTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEditorAreaType_Y() {
		return (EAttribute)editorAreaTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEventType() {
		return eventTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEventType_Id() {
		return (EAttribute)eventTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getHotZoneType() {
		return hotZoneTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHotZoneType_Event() {
		return (EReference)hotZoneTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHotZoneType_State() {
		return (EReference)hotZoneTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHotZoneType_Height() {
		return (EAttribute)hotZoneTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHotZoneType_Sticky() {
		return (EAttribute)hotZoneTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHotZoneType_Width() {
		return (EAttribute)hotZoneTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHotZoneType_X() {
		return (EAttribute)hotZoneTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHotZoneType_Y() {
		return (EAttribute)hotZoneTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSkinType() {
		return skinTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSkinType_EditorArea() {
		return (EReference)skinTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSkinType_HotZone() {
		return (EReference)skinTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSkinType_BackgroundColor() {
		return (EReference)skinTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSkinType_Height() {
		return (EAttribute)skinTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSkinType_ImageFilePath() {
		return (EAttribute)skinTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSkinType_Name() {
		return (EAttribute)skinTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSkinType_Width() {
		return (EAttribute)skinTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStateType() {
		return stateTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStateType_ImageFilePath() {
		return (EAttribute)stateTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStateType_Name() {
		return (EAttribute)stateTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SkinFactory getSkinFactory() {
		return (SkinFactory)getEFactoryInstance();
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
		backgroundColorTypeEClass = createEClass(BACKGROUND_COLOR_TYPE);
		createEAttribute(backgroundColorTypeEClass, BACKGROUND_COLOR_TYPE__BLUE);
		createEAttribute(backgroundColorTypeEClass, BACKGROUND_COLOR_TYPE__GREEN);
		createEAttribute(backgroundColorTypeEClass, BACKGROUND_COLOR_TYPE__RED);

		documentRootEClass = createEClass(DOCUMENT_ROOT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__BACKGROUND_COLOR);
		createEReference(documentRootEClass, DOCUMENT_ROOT__EDITOR_AREA);
		createEReference(documentRootEClass, DOCUMENT_ROOT__HOT_ZONE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__SKIN);

		editorAreaTypeEClass = createEClass(EDITOR_AREA_TYPE);
		createEAttribute(editorAreaTypeEClass, EDITOR_AREA_TYPE__HEIGHT);
		createEAttribute(editorAreaTypeEClass, EDITOR_AREA_TYPE__WIDTH);
		createEAttribute(editorAreaTypeEClass, EDITOR_AREA_TYPE__X);
		createEAttribute(editorAreaTypeEClass, EDITOR_AREA_TYPE__Y);

		eventTypeEClass = createEClass(EVENT_TYPE);
		createEAttribute(eventTypeEClass, EVENT_TYPE__ID);

		hotZoneTypeEClass = createEClass(HOT_ZONE_TYPE);
		createEReference(hotZoneTypeEClass, HOT_ZONE_TYPE__EVENT);
		createEReference(hotZoneTypeEClass, HOT_ZONE_TYPE__STATE);
		createEAttribute(hotZoneTypeEClass, HOT_ZONE_TYPE__HEIGHT);
		createEAttribute(hotZoneTypeEClass, HOT_ZONE_TYPE__STICKY);
		createEAttribute(hotZoneTypeEClass, HOT_ZONE_TYPE__WIDTH);
		createEAttribute(hotZoneTypeEClass, HOT_ZONE_TYPE__X);
		createEAttribute(hotZoneTypeEClass, HOT_ZONE_TYPE__Y);

		skinTypeEClass = createEClass(SKIN_TYPE);
		createEReference(skinTypeEClass, SKIN_TYPE__EDITOR_AREA);
		createEReference(skinTypeEClass, SKIN_TYPE__HOT_ZONE);
		createEReference(skinTypeEClass, SKIN_TYPE__BACKGROUND_COLOR);
		createEAttribute(skinTypeEClass, SKIN_TYPE__HEIGHT);
		createEAttribute(skinTypeEClass, SKIN_TYPE__IMAGE_FILE_PATH);
		createEAttribute(skinTypeEClass, SKIN_TYPE__NAME);
		createEAttribute(skinTypeEClass, SKIN_TYPE__WIDTH);

		stateTypeEClass = createEClass(STATE_TYPE);
		createEAttribute(stateTypeEClass, STATE_TYPE__IMAGE_FILE_PATH);
		createEAttribute(stateTypeEClass, STATE_TYPE__NAME);
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
		XMLTypePackageImpl theXMLTypePackage = (XMLTypePackageImpl)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(backgroundColorTypeEClass, BackgroundColorType.class, "BackgroundColorType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBackgroundColorType_Blue(), theXMLTypePackage.getShort(), "blue", null, 1, 1, BackgroundColorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBackgroundColorType_Green(), theXMLTypePackage.getShort(), "green", null, 1, 1, BackgroundColorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBackgroundColorType_Red(), theXMLTypePackage.getShort(), "red", null, 1, 1, BackgroundColorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_BackgroundColor(), this.getBackgroundColorType(), null, "backgroundColor", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_EditorArea(), this.getEditorAreaType(), null, "editorArea", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_HotZone(), this.getHotZoneType(), null, "hotZone", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Skin(), this.getSkinType(), null, "skin", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(editorAreaTypeEClass, EditorAreaType.class, "EditorAreaType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEditorAreaType_Height(), theXMLTypePackage.getShort(), "height", null, 1, 1, EditorAreaType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEditorAreaType_Width(), theXMLTypePackage.getShort(), "width", null, 1, 1, EditorAreaType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEditorAreaType_X(), theXMLTypePackage.getShort(), "x", null, 1, 1, EditorAreaType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEditorAreaType_Y(), theXMLTypePackage.getShort(), "y", null, 1, 1, EditorAreaType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eventTypeEClass, EventType.class, "EventType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEventType_Id(), theXMLTypePackage.getString(), "id", null, 1, 1, EventType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(hotZoneTypeEClass, HotZoneType.class, "HotZoneType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getHotZoneType_Event(), this.getEventType(), null, "event", null, 1, 1, HotZoneType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHotZoneType_State(), this.getStateType(), null, "state", null, 0, -1, HotZoneType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHotZoneType_Height(), theXMLTypePackage.getShort(), "height", null, 1, 1, HotZoneType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHotZoneType_Sticky(), theXMLTypePackage.getBoolean(), "sticky", "false", 0, 1, HotZoneType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHotZoneType_Width(), theXMLTypePackage.getShort(), "width", null, 1, 1, HotZoneType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHotZoneType_X(), theXMLTypePackage.getShort(), "x", null, 1, 1, HotZoneType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHotZoneType_Y(), theXMLTypePackage.getShort(), "y", null, 1, 1, HotZoneType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(skinTypeEClass, SkinType.class, "SkinType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSkinType_EditorArea(), this.getEditorAreaType(), null, "editorArea", null, 1, 1, SkinType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSkinType_HotZone(), this.getHotZoneType(), null, "hotZone", null, 0, -1, SkinType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSkinType_BackgroundColor(), this.getBackgroundColorType(), null, "backgroundColor", null, 0, 1, SkinType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSkinType_Height(), theXMLTypePackage.getShort(), "height", null, 1, 1, SkinType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSkinType_ImageFilePath(), theXMLTypePackage.getString(), "imageFilePath", null, 1, 1, SkinType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSkinType_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, SkinType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSkinType_Width(), theXMLTypePackage.getShort(), "width", null, 1, 1, SkinType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stateTypeEClass, StateType.class, "StateType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStateType_ImageFilePath(), theXMLTypePackage.getString(), "imageFilePath", null, 1, 1, StateType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStateType_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, StateType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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
		  (backgroundColorTypeEClass, 
		   source, 
		   new String[] {
			 "name", "backgroundColor_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getBackgroundColorType_Blue(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "blue"
		   });		
		addAnnotation
		  (getBackgroundColorType_Green(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "green"
		   });		
		addAnnotation
		  (getBackgroundColorType_Red(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "red"
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
		  (getDocumentRoot_BackgroundColor(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "backgroundColor",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_EditorArea(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "editorArea",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_HotZone(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "hotZone",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_Skin(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "skin",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (editorAreaTypeEClass, 
		   source, 
		   new String[] {
			 "name", "editorArea_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getEditorAreaType_Height(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "height"
		   });		
		addAnnotation
		  (getEditorAreaType_Width(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "width"
		   });		
		addAnnotation
		  (getEditorAreaType_X(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "x"
		   });		
		addAnnotation
		  (getEditorAreaType_Y(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "y"
		   });		
		addAnnotation
		  (eventTypeEClass, 
		   source, 
		   new String[] {
			 "name", "event_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getEventType_Id(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "id"
		   });		
		addAnnotation
		  (hotZoneTypeEClass, 
		   source, 
		   new String[] {
			 "name", "hotZone_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getHotZoneType_Event(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "event",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getHotZoneType_State(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "state",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getHotZoneType_Height(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "height"
		   });		
		addAnnotation
		  (getHotZoneType_Sticky(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "sticky"
		   });		
		addAnnotation
		  (getHotZoneType_Width(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "width"
		   });		
		addAnnotation
		  (getHotZoneType_X(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "x"
		   });		
		addAnnotation
		  (getHotZoneType_Y(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "y"
		   });		
		addAnnotation
		  (skinTypeEClass, 
		   source, 
		   new String[] {
			 "name", "skin_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getSkinType_EditorArea(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "editorArea",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getSkinType_HotZone(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "hotZone",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getSkinType_BackgroundColor(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "backgroundColor",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getSkinType_Height(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "height"
		   });		
		addAnnotation
		  (getSkinType_ImageFilePath(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "imageFilePath"
		   });		
		addAnnotation
		  (getSkinType_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name"
		   });		
		addAnnotation
		  (getSkinType_Width(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "width"
		   });		
		addAnnotation
		  (stateTypeEClass, 
		   source, 
		   new String[] {
			 "name", "state_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getStateType_ImageFilePath(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "imageFilePath"
		   });		
		addAnnotation
		  (getStateType_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name"
		   });
	}

} //SkinPackageImpl
