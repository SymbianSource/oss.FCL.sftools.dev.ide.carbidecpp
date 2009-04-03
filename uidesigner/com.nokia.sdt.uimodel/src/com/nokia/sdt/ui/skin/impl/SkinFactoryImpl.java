/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.ui.skin.impl;

import com.nokia.sdt.ui.skin.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EFactoryImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SkinFactoryImpl extends EFactoryImpl implements SkinFactory {
	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SkinFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case SkinPackage.BACKGROUND_COLOR_TYPE: return createBackgroundColorType();
			case SkinPackage.DOCUMENT_ROOT: return createDocumentRoot();
			case SkinPackage.EDITOR_AREA_TYPE: return createEditorAreaType();
			case SkinPackage.EVENT_TYPE: return createEventType();
			case SkinPackage.HOT_ZONE_TYPE: return createHotZoneType();
			case SkinPackage.SKIN_TYPE: return createSkinType();
			case SkinPackage.STATE_TYPE: return createStateType();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BackgroundColorType createBackgroundColorType() {
		BackgroundColorTypeImpl backgroundColorType = new BackgroundColorTypeImpl();
		return backgroundColorType;
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
	public EditorAreaType createEditorAreaType() {
		EditorAreaTypeImpl editorAreaType = new EditorAreaTypeImpl();
		return editorAreaType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventType createEventType() {
		EventTypeImpl eventType = new EventTypeImpl();
		return eventType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HotZoneType createHotZoneType() {
		HotZoneTypeImpl hotZoneType = new HotZoneTypeImpl();
		return hotZoneType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SkinType createSkinType() {
		SkinTypeImpl skinType = new SkinTypeImpl();
		return skinType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateType createStateType() {
		StateTypeImpl stateType = new StateTypeImpl();
		return stateType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SkinPackage getSkinPackage() {
		return (SkinPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	public static SkinPackage getPackage() {
		return SkinPackage.eINSTANCE;
	}

} //SkinFactoryImpl
