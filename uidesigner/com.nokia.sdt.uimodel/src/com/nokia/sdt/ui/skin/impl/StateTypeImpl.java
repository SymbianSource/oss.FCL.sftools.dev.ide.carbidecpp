/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.ui.skin.impl;

import com.nokia.sdt.ui.skin.SkinPackage;
import com.nokia.sdt.ui.skin.StateType;
import com.nokia.sdt.ui.skin.ISkin.Hotzone.State;
import com.swtdesigner.ResourceManager;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.swt.graphics.Image;

import java.io.File;



/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.ui.skin.impl.StateTypeImpl#getImageFilePath <em>Image File Path</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.impl.StateTypeImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StateTypeImpl extends EObjectImpl implements StateType, State {
	/**
	 * The default value of the '{@link #getImageFilePath() <em>Image File Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImageFilePath()
	 * @generated
	 * @ordered
	 */
	protected static final String IMAGE_FILE_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImageFilePath() <em>Image File Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImageFilePath()
	 * @generated
	 * @ordered
	 */
	protected String imageFilePath = IMAGE_FILE_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return SkinPackage.eINSTANCE.getStateType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImageFilePath() {
		return imageFilePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImageFilePath(String newImageFilePath) {
		String oldImageFilePath = imageFilePath;
		imageFilePath = newImageFilePath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SkinPackage.STATE_TYPE__IMAGE_FILE_PATH, oldImageFilePath, imageFilePath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SkinPackage.STATE_TYPE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case SkinPackage.STATE_TYPE__IMAGE_FILE_PATH:
				return getImageFilePath();
			case SkinPackage.STATE_TYPE__NAME:
				return getName();
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case SkinPackage.STATE_TYPE__IMAGE_FILE_PATH:
				setImageFilePath((String)newValue);
				return;
			case SkinPackage.STATE_TYPE__NAME:
				setName((String)newValue);
				return;
		}
		eDynamicSet(eFeature, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case SkinPackage.STATE_TYPE__IMAGE_FILE_PATH:
				setImageFilePath(IMAGE_FILE_PATH_EDEFAULT);
				return;
			case SkinPackage.STATE_TYPE__NAME:
				setName(NAME_EDEFAULT);
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case SkinPackage.STATE_TYPE__IMAGE_FILE_PATH:
				return IMAGE_FILE_PATH_EDEFAULT == null ? imageFilePath != null : !IMAGE_FILE_PATH_EDEFAULT.equals(imageFilePath);
			case SkinPackage.STATE_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		}
		return eDynamicIsSet(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (imageFilePath: ");
		result.append(imageFilePath);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

	private Image image;
	private String parentPath;

	public Image getImage() {
		if (image == null) {
			String path = getImageFilePath();
			if ((path != null) && (path.length() > 0)) {
				File imageFile = new File(parentPath, path);
				image = ResourceManager.getImage(imageFile.getPath());
			}
		}
		
		return image;
	}
	
	/**
	 * 
	 * @param path the directory in which the image files exist
	 */
	public void setDirectory(String path) {
		parentPath = path;
	}


} //StateTypeImpl
