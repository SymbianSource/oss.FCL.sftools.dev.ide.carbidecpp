/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.ui.skin.impl;

import com.nokia.sdt.ui.skin.BackgroundColorType;
import com.nokia.sdt.ui.skin.SkinPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Background Color Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.ui.skin.impl.BackgroundColorTypeImpl#getBlue <em>Blue</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.impl.BackgroundColorTypeImpl#getGreen <em>Green</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.impl.BackgroundColorTypeImpl#getRed <em>Red</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BackgroundColorTypeImpl extends EObjectImpl implements BackgroundColorType {
	/**
	 * The default value of the '{@link #getBlue() <em>Blue</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBlue()
	 * @generated
	 * @ordered
	 */
	protected static final short BLUE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getBlue() <em>Blue</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBlue()
	 * @generated
	 * @ordered
	 */
	protected short blue = BLUE_EDEFAULT;

	/**
	 * This is true if the Blue attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean blueESet = false;

	/**
	 * The default value of the '{@link #getGreen() <em>Green</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGreen()
	 * @generated
	 * @ordered
	 */
	protected static final short GREEN_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getGreen() <em>Green</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGreen()
	 * @generated
	 * @ordered
	 */
	protected short green = GREEN_EDEFAULT;

	/**
	 * This is true if the Green attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean greenESet = false;

	/**
	 * The default value of the '{@link #getRed() <em>Red</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRed()
	 * @generated
	 * @ordered
	 */
	protected static final short RED_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getRed() <em>Red</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRed()
	 * @generated
	 * @ordered
	 */
	protected short red = RED_EDEFAULT;

	/**
	 * This is true if the Red attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean redESet = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BackgroundColorTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return SkinPackage.eINSTANCE.getBackgroundColorType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public short getBlue() {
		return blue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBlue(short newBlue) {
		short oldBlue = blue;
		blue = newBlue;
		boolean oldBlueESet = blueESet;
		blueESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SkinPackage.BACKGROUND_COLOR_TYPE__BLUE, oldBlue, blue, !oldBlueESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetBlue() {
		short oldBlue = blue;
		boolean oldBlueESet = blueESet;
		blue = BLUE_EDEFAULT;
		blueESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, SkinPackage.BACKGROUND_COLOR_TYPE__BLUE, oldBlue, BLUE_EDEFAULT, oldBlueESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetBlue() {
		return blueESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public short getGreen() {
		return green;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGreen(short newGreen) {
		short oldGreen = green;
		green = newGreen;
		boolean oldGreenESet = greenESet;
		greenESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SkinPackage.BACKGROUND_COLOR_TYPE__GREEN, oldGreen, green, !oldGreenESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetGreen() {
		short oldGreen = green;
		boolean oldGreenESet = greenESet;
		green = GREEN_EDEFAULT;
		greenESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, SkinPackage.BACKGROUND_COLOR_TYPE__GREEN, oldGreen, GREEN_EDEFAULT, oldGreenESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetGreen() {
		return greenESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public short getRed() {
		return red;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRed(short newRed) {
		short oldRed = red;
		red = newRed;
		boolean oldRedESet = redESet;
		redESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SkinPackage.BACKGROUND_COLOR_TYPE__RED, oldRed, red, !oldRedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetRed() {
		short oldRed = red;
		boolean oldRedESet = redESet;
		red = RED_EDEFAULT;
		redESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, SkinPackage.BACKGROUND_COLOR_TYPE__RED, oldRed, RED_EDEFAULT, oldRedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetRed() {
		return redESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case SkinPackage.BACKGROUND_COLOR_TYPE__BLUE:
				return new Short(getBlue());
			case SkinPackage.BACKGROUND_COLOR_TYPE__GREEN:
				return new Short(getGreen());
			case SkinPackage.BACKGROUND_COLOR_TYPE__RED:
				return new Short(getRed());
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
			case SkinPackage.BACKGROUND_COLOR_TYPE__BLUE:
				setBlue(((Short)newValue).shortValue());
				return;
			case SkinPackage.BACKGROUND_COLOR_TYPE__GREEN:
				setGreen(((Short)newValue).shortValue());
				return;
			case SkinPackage.BACKGROUND_COLOR_TYPE__RED:
				setRed(((Short)newValue).shortValue());
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
			case SkinPackage.BACKGROUND_COLOR_TYPE__BLUE:
				unsetBlue();
				return;
			case SkinPackage.BACKGROUND_COLOR_TYPE__GREEN:
				unsetGreen();
				return;
			case SkinPackage.BACKGROUND_COLOR_TYPE__RED:
				unsetRed();
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
			case SkinPackage.BACKGROUND_COLOR_TYPE__BLUE:
				return isSetBlue();
			case SkinPackage.BACKGROUND_COLOR_TYPE__GREEN:
				return isSetGreen();
			case SkinPackage.BACKGROUND_COLOR_TYPE__RED:
				return isSetRed();
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
		result.append(" (blue: ");
		if (blueESet) result.append(blue); else result.append("<unset>");
		result.append(", green: ");
		if (greenESet) result.append(green); else result.append("<unset>");
		result.append(", red: ");
		if (redESet) result.append(red); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //BackgroundColorTypeImpl
