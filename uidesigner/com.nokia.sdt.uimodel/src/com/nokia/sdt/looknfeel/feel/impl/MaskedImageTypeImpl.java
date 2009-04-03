/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
* All rights reserved.
* This component and the accompanying materials are made available
* under the terms of the License "Eclipse Public License v1.0"
* which accompanies this distribution, and is available
* at the URL "http://www.eclipse.org/legal/epl-v10.html".
*
* Initial Contributors:
* Nokia Corporation - initial contribution.
*
* Contributors:
*
* Description: 
*
*/

package com.nokia.sdt.looknfeel.feel.impl;

import com.nokia.sdt.looknfeel.feel.LookAndFeelPackage;
import com.nokia.sdt.looknfeel.feel.MaskedImageType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Masked Image Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.MaskedImageTypeImpl#getImageFile <em>Image File</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.MaskedImageTypeImpl#getKey <em>Key</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.MaskedImageTypeImpl#getMaskFile <em>Mask File</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MaskedImageTypeImpl extends EObjectImpl implements MaskedImageType {
    /**
	 * The default value of the '{@link #getImageFile() <em>Image File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImageFile()
	 * @generated
	 * @ordered
	 */
	protected static final String IMAGE_FILE_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getImageFile() <em>Image File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImageFile()
	 * @generated
	 * @ordered
	 */
	protected String imageFile = IMAGE_FILE_EDEFAULT;

    /**
	 * The default value of the '{@link #getKey() <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKey()
	 * @generated
	 * @ordered
	 */
	protected static final String KEY_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getKey() <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKey()
	 * @generated
	 * @ordered
	 */
	protected String key = KEY_EDEFAULT;

    /**
	 * The default value of the '{@link #getMaskFile() <em>Mask File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaskFile()
	 * @generated
	 * @ordered
	 */
	protected static final String MASK_FILE_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getMaskFile() <em>Mask File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaskFile()
	 * @generated
	 * @ordered
	 */
	protected String maskFile = MASK_FILE_EDEFAULT;

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MaskedImageTypeImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return LookAndFeelPackage.Literals.MASKED_IMAGE_TYPE;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImageFile() {
		return imageFile;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImageFile(String newImageFile) {
		String oldImageFile = imageFile;
		imageFile = newImageFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LookAndFeelPackage.MASKED_IMAGE_TYPE__IMAGE_FILE, oldImageFile, imageFile));
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getKey() {
		return key;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKey(String newKey) {
		String oldKey = key;
		key = newKey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LookAndFeelPackage.MASKED_IMAGE_TYPE__KEY, oldKey, key));
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMaskFile() {
		return maskFile;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaskFile(String newMaskFile) {
		String oldMaskFile = maskFile;
		maskFile = newMaskFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LookAndFeelPackage.MASKED_IMAGE_TYPE__MASK_FILE, oldMaskFile, maskFile));
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LookAndFeelPackage.MASKED_IMAGE_TYPE__IMAGE_FILE:
				return getImageFile();
			case LookAndFeelPackage.MASKED_IMAGE_TYPE__KEY:
				return getKey();
			case LookAndFeelPackage.MASKED_IMAGE_TYPE__MASK_FILE:
				return getMaskFile();
		}
		return super.eGet(featureID, resolve, coreType);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case LookAndFeelPackage.MASKED_IMAGE_TYPE__IMAGE_FILE:
				setImageFile((String)newValue);
				return;
			case LookAndFeelPackage.MASKED_IMAGE_TYPE__KEY:
				setKey((String)newValue);
				return;
			case LookAndFeelPackage.MASKED_IMAGE_TYPE__MASK_FILE:
				setMaskFile((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case LookAndFeelPackage.MASKED_IMAGE_TYPE__IMAGE_FILE:
				setImageFile(IMAGE_FILE_EDEFAULT);
				return;
			case LookAndFeelPackage.MASKED_IMAGE_TYPE__KEY:
				setKey(KEY_EDEFAULT);
				return;
			case LookAndFeelPackage.MASKED_IMAGE_TYPE__MASK_FILE:
				setMaskFile(MASK_FILE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case LookAndFeelPackage.MASKED_IMAGE_TYPE__IMAGE_FILE:
				return IMAGE_FILE_EDEFAULT == null ? imageFile != null : !IMAGE_FILE_EDEFAULT.equals(imageFile);
			case LookAndFeelPackage.MASKED_IMAGE_TYPE__KEY:
				return KEY_EDEFAULT == null ? key != null : !KEY_EDEFAULT.equals(key);
			case LookAndFeelPackage.MASKED_IMAGE_TYPE__MASK_FILE:
				return MASK_FILE_EDEFAULT == null ? maskFile != null : !MASK_FILE_EDEFAULT.equals(maskFile);
		}
		return super.eIsSet(featureID);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (imageFile: ");
		result.append(imageFile);
		result.append(", key: ");
		result.append(key);
		result.append(", maskFile: ");
		result.append(maskFile);
		result.append(')');
		return result.toString();
	}

} //MaskedImageTypeImpl
