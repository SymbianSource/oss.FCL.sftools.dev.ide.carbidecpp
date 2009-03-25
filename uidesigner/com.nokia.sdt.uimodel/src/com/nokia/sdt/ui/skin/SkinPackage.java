/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.ui.skin;

import org.eclipse.emf.ecore.*;

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
 * @see com.nokia.sdt.ui.skin.SkinFactory
 * @model kind="package"
 * @generated
 */
public interface SkinPackage extends EPackage{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "skin";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.nokia.com/sdt/ui/skin";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "skin";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SkinPackage eINSTANCE = com.nokia.sdt.ui.skin.impl.SkinPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.nokia.sdt.ui.skin.impl.BackgroundColorTypeImpl <em>Background Color Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.ui.skin.impl.BackgroundColorTypeImpl
	 * @see com.nokia.sdt.ui.skin.impl.SkinPackageImpl#getBackgroundColorType()
	 * @generated
	 */
	int BACKGROUND_COLOR_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Blue</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKGROUND_COLOR_TYPE__BLUE = 0;

	/**
	 * The feature id for the '<em><b>Green</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKGROUND_COLOR_TYPE__GREEN = 1;

	/**
	 * The feature id for the '<em><b>Red</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKGROUND_COLOR_TYPE__RED = 2;

	/**
	 * The number of structural features of the the '<em>Background Color Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKGROUND_COLOR_TYPE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.ui.skin.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.ui.skin.impl.DocumentRootImpl
	 * @see com.nokia.sdt.ui.skin.impl.SkinPackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 1;

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
	 * The feature id for the '<em><b>Background Color</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BACKGROUND_COLOR = 3;

	/**
	 * The feature id for the '<em><b>Editor Area</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EDITOR_AREA = 4;

	/**
	 * The feature id for the '<em><b>Hot Zone</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__HOT_ZONE = 5;

	/**
	 * The feature id for the '<em><b>Skin</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SKIN = 6;

	/**
	 * The number of structural features of the the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.ui.skin.impl.EditorAreaTypeImpl <em>Editor Area Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.ui.skin.impl.EditorAreaTypeImpl
	 * @see com.nokia.sdt.ui.skin.impl.SkinPackageImpl#getEditorAreaType()
	 * @generated
	 */
	int EDITOR_AREA_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_AREA_TYPE__HEIGHT = 0;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_AREA_TYPE__WIDTH = 1;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_AREA_TYPE__X = 2;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_AREA_TYPE__Y = 3;

	/**
	 * The number of structural features of the the '<em>Editor Area Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_AREA_TYPE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.ui.skin.impl.EventTypeImpl <em>Event Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.ui.skin.impl.EventTypeImpl
	 * @see com.nokia.sdt.ui.skin.impl.SkinPackageImpl#getEventType()
	 * @generated
	 */
	int EVENT_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE__ID = 0;

	/**
	 * The number of structural features of the the '<em>Event Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.ui.skin.impl.HotZoneTypeImpl <em>Hot Zone Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.ui.skin.impl.HotZoneTypeImpl
	 * @see com.nokia.sdt.ui.skin.impl.SkinPackageImpl#getHotZoneType()
	 * @generated
	 */
	int HOT_ZONE_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Event</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOT_ZONE_TYPE__EVENT = 0;

	/**
	 * The feature id for the '<em><b>State</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOT_ZONE_TYPE__STATE = 1;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOT_ZONE_TYPE__HEIGHT = 2;

	/**
	 * The feature id for the '<em><b>Sticky</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOT_ZONE_TYPE__STICKY = 3;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOT_ZONE_TYPE__WIDTH = 4;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOT_ZONE_TYPE__X = 5;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOT_ZONE_TYPE__Y = 6;

	/**
	 * The number of structural features of the the '<em>Hot Zone Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOT_ZONE_TYPE_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.ui.skin.impl.SkinTypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.ui.skin.impl.SkinTypeImpl
	 * @see com.nokia.sdt.ui.skin.impl.SkinPackageImpl#getSkinType()
	 * @generated
	 */
	int SKIN_TYPE = 5;

	/**
	 * The feature id for the '<em><b>Editor Area</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN_TYPE__EDITOR_AREA = 0;

	/**
	 * The feature id for the '<em><b>Hot Zone</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN_TYPE__HOT_ZONE = 1;

	/**
	 * The feature id for the '<em><b>Background Color</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN_TYPE__BACKGROUND_COLOR = 2;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN_TYPE__HEIGHT = 3;

	/**
	 * The feature id for the '<em><b>Image File Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN_TYPE__IMAGE_FILE_PATH = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN_TYPE__NAME = 5;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN_TYPE__WIDTH = 6;

	/**
	 * The number of structural features of the the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN_TYPE_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.ui.skin.impl.StateTypeImpl <em>State Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.ui.skin.impl.StateTypeImpl
	 * @see com.nokia.sdt.ui.skin.impl.SkinPackageImpl#getStateType()
	 * @generated
	 */
	int STATE_TYPE = 6;

	/**
	 * The feature id for the '<em><b>Image File Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_TYPE__IMAGE_FILE_PATH = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_TYPE__NAME = 1;

	/**
	 * The number of structural features of the the '<em>State Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_TYPE_FEATURE_COUNT = 2;


	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.ui.skin.BackgroundColorType <em>Background Color Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Background Color Type</em>'.
	 * @see com.nokia.sdt.ui.skin.BackgroundColorType
	 * @generated
	 */
	EClass getBackgroundColorType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.ui.skin.BackgroundColorType#getBlue <em>Blue</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Blue</em>'.
	 * @see com.nokia.sdt.ui.skin.BackgroundColorType#getBlue()
	 * @see #getBackgroundColorType()
	 * @generated
	 */
	EAttribute getBackgroundColorType_Blue();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.ui.skin.BackgroundColorType#getGreen <em>Green</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Green</em>'.
	 * @see com.nokia.sdt.ui.skin.BackgroundColorType#getGreen()
	 * @see #getBackgroundColorType()
	 * @generated
	 */
	EAttribute getBackgroundColorType_Green();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.ui.skin.BackgroundColorType#getRed <em>Red</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Red</em>'.
	 * @see com.nokia.sdt.ui.skin.BackgroundColorType#getRed()
	 * @see #getBackgroundColorType()
	 * @generated
	 */
	EAttribute getBackgroundColorType_Red();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.ui.skin.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see com.nokia.sdt.ui.skin.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.sdt.ui.skin.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.nokia.sdt.ui.skin.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link com.nokia.sdt.ui.skin.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see com.nokia.sdt.ui.skin.DocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link com.nokia.sdt.ui.skin.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see com.nokia.sdt.ui.skin.DocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.ui.skin.DocumentRoot#getBackgroundColor <em>Background Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Background Color</em>'.
	 * @see com.nokia.sdt.ui.skin.DocumentRoot#getBackgroundColor()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_BackgroundColor();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.ui.skin.DocumentRoot#getEditorArea <em>Editor Area</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Editor Area</em>'.
	 * @see com.nokia.sdt.ui.skin.DocumentRoot#getEditorArea()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_EditorArea();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.ui.skin.DocumentRoot#getHotZone <em>Hot Zone</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Hot Zone</em>'.
	 * @see com.nokia.sdt.ui.skin.DocumentRoot#getHotZone()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_HotZone();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.ui.skin.DocumentRoot#getSkin <em>Skin</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Skin</em>'.
	 * @see com.nokia.sdt.ui.skin.DocumentRoot#getSkin()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Skin();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.ui.skin.EditorAreaType <em>Editor Area Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Editor Area Type</em>'.
	 * @see com.nokia.sdt.ui.skin.EditorAreaType
	 * @generated
	 */
	EClass getEditorAreaType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.ui.skin.EditorAreaType#getHeight <em>Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Height</em>'.
	 * @see com.nokia.sdt.ui.skin.EditorAreaType#getHeight()
	 * @see #getEditorAreaType()
	 * @generated
	 */
	EAttribute getEditorAreaType_Height();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.ui.skin.EditorAreaType#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see com.nokia.sdt.ui.skin.EditorAreaType#getWidth()
	 * @see #getEditorAreaType()
	 * @generated
	 */
	EAttribute getEditorAreaType_Width();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.ui.skin.EditorAreaType#getX <em>X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>X</em>'.
	 * @see com.nokia.sdt.ui.skin.EditorAreaType#getX()
	 * @see #getEditorAreaType()
	 * @generated
	 */
	EAttribute getEditorAreaType_X();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.ui.skin.EditorAreaType#getY <em>Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Y</em>'.
	 * @see com.nokia.sdt.ui.skin.EditorAreaType#getY()
	 * @see #getEditorAreaType()
	 * @generated
	 */
	EAttribute getEditorAreaType_Y();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.ui.skin.EventType <em>Event Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event Type</em>'.
	 * @see com.nokia.sdt.ui.skin.EventType
	 * @generated
	 */
	EClass getEventType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.ui.skin.EventType#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.nokia.sdt.ui.skin.EventType#getId()
	 * @see #getEventType()
	 * @generated
	 */
	EAttribute getEventType_Id();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.ui.skin.HotZoneType <em>Hot Zone Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Hot Zone Type</em>'.
	 * @see com.nokia.sdt.ui.skin.HotZoneType
	 * @generated
	 */
	EClass getHotZoneType();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.ui.skin.HotZoneType#getEvent <em>Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Event</em>'.
	 * @see com.nokia.sdt.ui.skin.HotZoneType#getEvent()
	 * @see #getHotZoneType()
	 * @generated
	 */
	EReference getHotZoneType_Event();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.ui.skin.HotZoneType#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>State</em>'.
	 * @see com.nokia.sdt.ui.skin.HotZoneType#getState()
	 * @see #getHotZoneType()
	 * @generated
	 */
	EReference getHotZoneType_State();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.ui.skin.HotZoneType#getHeight <em>Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Height</em>'.
	 * @see com.nokia.sdt.ui.skin.HotZoneType#getHeight()
	 * @see #getHotZoneType()
	 * @generated
	 */
	EAttribute getHotZoneType_Height();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.ui.skin.HotZoneType#isSticky <em>Sticky</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sticky</em>'.
	 * @see com.nokia.sdt.ui.skin.HotZoneType#isSticky()
	 * @see #getHotZoneType()
	 * @generated
	 */
	EAttribute getHotZoneType_Sticky();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.ui.skin.HotZoneType#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see com.nokia.sdt.ui.skin.HotZoneType#getWidth()
	 * @see #getHotZoneType()
	 * @generated
	 */
	EAttribute getHotZoneType_Width();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.ui.skin.HotZoneType#getX <em>X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>X</em>'.
	 * @see com.nokia.sdt.ui.skin.HotZoneType#getX()
	 * @see #getHotZoneType()
	 * @generated
	 */
	EAttribute getHotZoneType_X();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.ui.skin.HotZoneType#getY <em>Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Y</em>'.
	 * @see com.nokia.sdt.ui.skin.HotZoneType#getY()
	 * @see #getHotZoneType()
	 * @generated
	 */
	EAttribute getHotZoneType_Y();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.ui.skin.SkinType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type</em>'.
	 * @see com.nokia.sdt.ui.skin.SkinType
	 * @generated
	 */
	EClass getSkinType();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.ui.skin.SkinType#getEditorArea <em>Editor Area</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Editor Area</em>'.
	 * @see com.nokia.sdt.ui.skin.SkinType#getEditorArea()
	 * @see #getSkinType()
	 * @generated
	 */
	EReference getSkinType_EditorArea();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.ui.skin.SkinType#getHotZone <em>Hot Zone</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Hot Zone</em>'.
	 * @see com.nokia.sdt.ui.skin.SkinType#getHotZone()
	 * @see #getSkinType()
	 * @generated
	 */
	EReference getSkinType_HotZone();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.ui.skin.SkinType#getBackgroundColor <em>Background Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Background Color</em>'.
	 * @see com.nokia.sdt.ui.skin.SkinType#getBackgroundColor()
	 * @see #getSkinType()
	 * @generated
	 */
	EReference getSkinType_BackgroundColor();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.ui.skin.SkinType#getHeight <em>Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Height</em>'.
	 * @see com.nokia.sdt.ui.skin.SkinType#getHeight()
	 * @see #getSkinType()
	 * @generated
	 */
	EAttribute getSkinType_Height();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.ui.skin.SkinType#getImageFilePath <em>Image File Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Image File Path</em>'.
	 * @see com.nokia.sdt.ui.skin.SkinType#getImageFilePath()
	 * @see #getSkinType()
	 * @generated
	 */
	EAttribute getSkinType_ImageFilePath();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.ui.skin.SkinType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.nokia.sdt.ui.skin.SkinType#getName()
	 * @see #getSkinType()
	 * @generated
	 */
	EAttribute getSkinType_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.ui.skin.SkinType#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see com.nokia.sdt.ui.skin.SkinType#getWidth()
	 * @see #getSkinType()
	 * @generated
	 */
	EAttribute getSkinType_Width();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.ui.skin.StateType <em>State Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State Type</em>'.
	 * @see com.nokia.sdt.ui.skin.StateType
	 * @generated
	 */
	EClass getStateType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.ui.skin.StateType#getImageFilePath <em>Image File Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Image File Path</em>'.
	 * @see com.nokia.sdt.ui.skin.StateType#getImageFilePath()
	 * @see #getStateType()
	 * @generated
	 */
	EAttribute getStateType_ImageFilePath();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.ui.skin.StateType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.nokia.sdt.ui.skin.StateType#getName()
	 * @see #getStateType()
	 * @generated
	 */
	EAttribute getStateType_Name();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SkinFactory getSkinFactory();

} //SkinPackage
