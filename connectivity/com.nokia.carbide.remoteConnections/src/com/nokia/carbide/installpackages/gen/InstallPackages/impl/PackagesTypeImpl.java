/**
 * <copyright>
 * </copyright>
 *
 */
package com.nokia.carbide.installpackages.gen.InstallPackages.impl;

import com.nokia.carbide.installpackages.gen.InstallPackages.InstallPackagesPackage;
import com.nokia.carbide.installpackages.gen.InstallPackages.PackageType;
import com.nokia.carbide.installpackages.gen.InstallPackages.PackagesType;

import com.nokia.carbide.installpackages.gen.InstallPackages.SDKFamilyType;
import com.nokia.carbide.installpackages.gen.InstallPackages.SDKVersionType;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Packages Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.installpackages.gen.InstallPackages.impl.PackagesTypeImpl#getSDKFamily <em>SDK Family</em>}</li>
 *   <li>{@link com.nokia.carbide.installpackages.gen.InstallPackages.impl.PackagesTypeImpl#getSDKVersion <em>SDK Version</em>}</li>
 *   <li>{@link com.nokia.carbide.installpackages.gen.InstallPackages.impl.PackagesTypeImpl#getPackage <em>Package</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PackagesTypeImpl extends EObjectImpl implements PackagesType {
	/**
	 * The cached value of the '{@link #getSDKFamily() <em>SDK Family</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSDKFamily()
	 * @generated
	 * @ordered
	 */
	protected SDKFamilyType sDKFamily;
	/**
	 * The cached value of the '{@link #getSDKVersion() <em>SDK Version</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSDKVersion()
	 * @generated
	 * @ordered
	 */
	protected SDKVersionType sDKVersion;
	/**
	 * The cached value of the '{@link #getPackage() <em>Package</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackage()
	 * @generated
	 * @ordered
	 */
	protected EList<PackageType> package_;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PackagesTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InstallPackagesPackage.Literals.PACKAGES_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SDKFamilyType getSDKFamily() {
		return sDKFamily;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSDKFamily(SDKFamilyType newSDKFamily, NotificationChain msgs) {
		SDKFamilyType oldSDKFamily = sDKFamily;
		sDKFamily = newSDKFamily;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, InstallPackagesPackage.PACKAGES_TYPE__SDK_FAMILY, oldSDKFamily, newSDKFamily);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSDKFamily(SDKFamilyType newSDKFamily) {
		if (newSDKFamily != sDKFamily) {
			NotificationChain msgs = null;
			if (sDKFamily != null)
				msgs = ((InternalEObject)sDKFamily).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - InstallPackagesPackage.PACKAGES_TYPE__SDK_FAMILY, null, msgs);
			if (newSDKFamily != null)
				msgs = ((InternalEObject)newSDKFamily).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - InstallPackagesPackage.PACKAGES_TYPE__SDK_FAMILY, null, msgs);
			msgs = basicSetSDKFamily(newSDKFamily, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InstallPackagesPackage.PACKAGES_TYPE__SDK_FAMILY, newSDKFamily, newSDKFamily));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SDKVersionType getSDKVersion() {
		return sDKVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSDKVersion(SDKVersionType newSDKVersion, NotificationChain msgs) {
		SDKVersionType oldSDKVersion = sDKVersion;
		sDKVersion = newSDKVersion;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, InstallPackagesPackage.PACKAGES_TYPE__SDK_VERSION, oldSDKVersion, newSDKVersion);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSDKVersion(SDKVersionType newSDKVersion) {
		if (newSDKVersion != sDKVersion) {
			NotificationChain msgs = null;
			if (sDKVersion != null)
				msgs = ((InternalEObject)sDKVersion).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - InstallPackagesPackage.PACKAGES_TYPE__SDK_VERSION, null, msgs);
			if (newSDKVersion != null)
				msgs = ((InternalEObject)newSDKVersion).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - InstallPackagesPackage.PACKAGES_TYPE__SDK_VERSION, null, msgs);
			msgs = basicSetSDKVersion(newSDKVersion, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InstallPackagesPackage.PACKAGES_TYPE__SDK_VERSION, newSDKVersion, newSDKVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PackageType> getPackage() {
		if (package_ == null) {
			package_ = new EObjectContainmentEList<PackageType>(PackageType.class, this, InstallPackagesPackage.PACKAGES_TYPE__PACKAGE);
		}
		return package_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case InstallPackagesPackage.PACKAGES_TYPE__SDK_FAMILY:
				return basicSetSDKFamily(null, msgs);
			case InstallPackagesPackage.PACKAGES_TYPE__SDK_VERSION:
				return basicSetSDKVersion(null, msgs);
			case InstallPackagesPackage.PACKAGES_TYPE__PACKAGE:
				return ((InternalEList<?>)getPackage()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case InstallPackagesPackage.PACKAGES_TYPE__SDK_FAMILY:
				return getSDKFamily();
			case InstallPackagesPackage.PACKAGES_TYPE__SDK_VERSION:
				return getSDKVersion();
			case InstallPackagesPackage.PACKAGES_TYPE__PACKAGE:
				return getPackage();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case InstallPackagesPackage.PACKAGES_TYPE__SDK_FAMILY:
				setSDKFamily((SDKFamilyType)newValue);
				return;
			case InstallPackagesPackage.PACKAGES_TYPE__SDK_VERSION:
				setSDKVersion((SDKVersionType)newValue);
				return;
			case InstallPackagesPackage.PACKAGES_TYPE__PACKAGE:
				getPackage().clear();
				getPackage().addAll((Collection<? extends PackageType>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case InstallPackagesPackage.PACKAGES_TYPE__SDK_FAMILY:
				setSDKFamily((SDKFamilyType)null);
				return;
			case InstallPackagesPackage.PACKAGES_TYPE__SDK_VERSION:
				setSDKVersion((SDKVersionType)null);
				return;
			case InstallPackagesPackage.PACKAGES_TYPE__PACKAGE:
				getPackage().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case InstallPackagesPackage.PACKAGES_TYPE__SDK_FAMILY:
				return sDKFamily != null;
			case InstallPackagesPackage.PACKAGES_TYPE__SDK_VERSION:
				return sDKVersion != null;
			case InstallPackagesPackage.PACKAGES_TYPE__PACKAGE:
				return package_ != null && !package_.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //PackagesTypeImpl
