/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.DesignerImagesType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Designer Images Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DesignerImagesTypeImpl#getLargeIconFile <em>Large Icon File</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DesignerImagesTypeImpl#getLayoutImageFile <em>Layout Image File</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DesignerImagesTypeImpl#getSmallIconFile <em>Small Icon File</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DesignerImagesTypeImpl#getThumbnailFile <em>Thumbnail File</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DesignerImagesTypeImpl extends EObjectImpl implements DesignerImagesType {
	/**
	 * The default value of the '{@link #getLargeIconFile() <em>Large Icon File</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getLargeIconFile()
	 * @generated
	 * @ordered
	 */
    protected static final String LARGE_ICON_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLargeIconFile() <em>Large Icon File</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getLargeIconFile()
	 * @generated
	 * @ordered
	 */
    protected String largeIconFile = LARGE_ICON_FILE_EDEFAULT;

	/**
	 * The default value of the '{@link #getLayoutImageFile() <em>Layout Image File</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getLayoutImageFile()
	 * @generated
	 * @ordered
	 */
    protected static final String LAYOUT_IMAGE_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLayoutImageFile() <em>Layout Image File</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getLayoutImageFile()
	 * @generated
	 * @ordered
	 */
    protected String layoutImageFile = LAYOUT_IMAGE_FILE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSmallIconFile() <em>Small Icon File</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getSmallIconFile()
	 * @generated
	 * @ordered
	 */
    protected static final String SMALL_ICON_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSmallIconFile() <em>Small Icon File</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getSmallIconFile()
	 * @generated
	 * @ordered
	 */
    protected String smallIconFile = SMALL_ICON_FILE_EDEFAULT;

	/**
	 * The default value of the '{@link #getThumbnailFile() <em>Thumbnail File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThumbnailFile()
	 * @generated
	 * @ordered
	 */
	protected static final String THUMBNAIL_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getThumbnailFile() <em>Thumbnail File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThumbnailFile()
	 * @generated
	 * @ordered
	 */
	protected String thumbnailFile = THUMBNAIL_FILE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DesignerImagesTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.DESIGNER_IMAGES_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getLargeIconFile() {
		return largeIconFile;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setLargeIconFile(String newLargeIconFile) {
		String oldLargeIconFile = largeIconFile;
		largeIconFile = newLargeIconFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.DESIGNER_IMAGES_TYPE__LARGE_ICON_FILE, oldLargeIconFile, largeIconFile));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getLayoutImageFile() {
		return layoutImageFile;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setLayoutImageFile(String newLayoutImageFile) {
		String oldLayoutImageFile = layoutImageFile;
		layoutImageFile = newLayoutImageFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.DESIGNER_IMAGES_TYPE__LAYOUT_IMAGE_FILE, oldLayoutImageFile, layoutImageFile));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getSmallIconFile() {
		return smallIconFile;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setSmallIconFile(String newSmallIconFile) {
		String oldSmallIconFile = smallIconFile;
		smallIconFile = newSmallIconFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.DESIGNER_IMAGES_TYPE__SMALL_ICON_FILE, oldSmallIconFile, smallIconFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getThumbnailFile() {
		return thumbnailFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setThumbnailFile(String newThumbnailFile) {
		String oldThumbnailFile = thumbnailFile;
		thumbnailFile = newThumbnailFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.DESIGNER_IMAGES_TYPE__THUMBNAIL_FILE, oldThumbnailFile, thumbnailFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ComponentPackage.DESIGNER_IMAGES_TYPE__LARGE_ICON_FILE:
				return getLargeIconFile();
			case ComponentPackage.DESIGNER_IMAGES_TYPE__LAYOUT_IMAGE_FILE:
				return getLayoutImageFile();
			case ComponentPackage.DESIGNER_IMAGES_TYPE__SMALL_ICON_FILE:
				return getSmallIconFile();
			case ComponentPackage.DESIGNER_IMAGES_TYPE__THUMBNAIL_FILE:
				return getThumbnailFile();
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
			case ComponentPackage.DESIGNER_IMAGES_TYPE__LARGE_ICON_FILE:
				setLargeIconFile((String)newValue);
				return;
			case ComponentPackage.DESIGNER_IMAGES_TYPE__LAYOUT_IMAGE_FILE:
				setLayoutImageFile((String)newValue);
				return;
			case ComponentPackage.DESIGNER_IMAGES_TYPE__SMALL_ICON_FILE:
				setSmallIconFile((String)newValue);
				return;
			case ComponentPackage.DESIGNER_IMAGES_TYPE__THUMBNAIL_FILE:
				setThumbnailFile((String)newValue);
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
			case ComponentPackage.DESIGNER_IMAGES_TYPE__LARGE_ICON_FILE:
				setLargeIconFile(LARGE_ICON_FILE_EDEFAULT);
				return;
			case ComponentPackage.DESIGNER_IMAGES_TYPE__LAYOUT_IMAGE_FILE:
				setLayoutImageFile(LAYOUT_IMAGE_FILE_EDEFAULT);
				return;
			case ComponentPackage.DESIGNER_IMAGES_TYPE__SMALL_ICON_FILE:
				setSmallIconFile(SMALL_ICON_FILE_EDEFAULT);
				return;
			case ComponentPackage.DESIGNER_IMAGES_TYPE__THUMBNAIL_FILE:
				setThumbnailFile(THUMBNAIL_FILE_EDEFAULT);
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
			case ComponentPackage.DESIGNER_IMAGES_TYPE__LARGE_ICON_FILE:
				return LARGE_ICON_FILE_EDEFAULT == null ? largeIconFile != null : !LARGE_ICON_FILE_EDEFAULT.equals(largeIconFile);
			case ComponentPackage.DESIGNER_IMAGES_TYPE__LAYOUT_IMAGE_FILE:
				return LAYOUT_IMAGE_FILE_EDEFAULT == null ? layoutImageFile != null : !LAYOUT_IMAGE_FILE_EDEFAULT.equals(layoutImageFile);
			case ComponentPackage.DESIGNER_IMAGES_TYPE__SMALL_ICON_FILE:
				return SMALL_ICON_FILE_EDEFAULT == null ? smallIconFile != null : !SMALL_ICON_FILE_EDEFAULT.equals(smallIconFile);
			case ComponentPackage.DESIGNER_IMAGES_TYPE__THUMBNAIL_FILE:
				return THUMBNAIL_FILE_EDEFAULT == null ? thumbnailFile != null : !THUMBNAIL_FILE_EDEFAULT.equals(thumbnailFile);
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
		result.append(" (largeIconFile: ");
		result.append(largeIconFile);
		result.append(", layoutImageFile: ");
		result.append(layoutImageFile);
		result.append(", smallIconFile: ");
		result.append(smallIconFile);
		result.append(", thumbnailFile: ");
		result.append(thumbnailFile);
		result.append(')');
		return result.toString();
	}

} //DesignerImagesTypeImpl
