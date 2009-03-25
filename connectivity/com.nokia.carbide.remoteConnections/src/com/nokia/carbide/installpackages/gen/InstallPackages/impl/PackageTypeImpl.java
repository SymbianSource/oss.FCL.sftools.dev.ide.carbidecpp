/**
 * <copyright>
 * </copyright>
 *
 */
package com.nokia.carbide.installpackages.gen.InstallPackages.impl;

import com.nokia.carbide.installpackages.gen.InstallPackages.InstallPackagesPackage;
import com.nokia.carbide.installpackages.gen.InstallPackages.PackageType;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Package Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.installpackages.gen.InstallPackages.impl.PackageTypeImpl#getInformation <em>Information</em>}</li>
 *   <li>{@link com.nokia.carbide.installpackages.gen.InstallPackages.impl.PackageTypeImpl#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.nokia.carbide.installpackages.gen.InstallPackages.impl.PackageTypeImpl#getInstallFilePath <em>Install File Path</em>}</li>
 *   <li>{@link com.nokia.carbide.installpackages.gen.InstallPackages.impl.PackageTypeImpl#getPackageVersion <em>Package Version</em>}</li>
 *   <li>{@link com.nokia.carbide.installpackages.gen.InstallPackages.impl.PackageTypeImpl#getSdkFamily <em>Sdk Family</em>}</li>
 *   <li>{@link com.nokia.carbide.installpackages.gen.InstallPackages.impl.PackageTypeImpl#getSdkVersion <em>Sdk Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PackageTypeImpl extends EObjectImpl implements PackageType {
	/**
	 * The default value of the '{@link #getInformation() <em>Information</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInformation()
	 * @generated
	 * @ordered
	 */
	protected static final String INFORMATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInformation() <em>Information</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInformation()
	 * @generated
	 * @ordered
	 */
	protected String information = INFORMATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected static final String DISPLAY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected String displayName = DISPLAY_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getInstallFilePath() <em>Install File Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstallFilePath()
	 * @generated
	 * @ordered
	 */
	protected static final String INSTALL_FILE_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInstallFilePath() <em>Install File Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstallFilePath()
	 * @generated
	 * @ordered
	 */
	protected String installFilePath = INSTALL_FILE_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getPackageVersion() <em>Package Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String PACKAGE_VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPackageVersion() <em>Package Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageVersion()
	 * @generated
	 * @ordered
	 */
	protected String packageVersion = PACKAGE_VERSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getSdkFamily() <em>Sdk Family</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSdkFamily()
	 * @generated
	 * @ordered
	 */
	protected static final String SDK_FAMILY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSdkFamily() <em>Sdk Family</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSdkFamily()
	 * @generated
	 * @ordered
	 */
	protected String sdkFamily = SDK_FAMILY_EDEFAULT;

	/**
	 * The default value of the '{@link #getSdkVersion() <em>Sdk Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSdkVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String SDK_VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSdkVersion() <em>Sdk Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSdkVersion()
	 * @generated
	 * @ordered
	 */
	protected String sdkVersion = SDK_VERSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PackageTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InstallPackagesPackage.Literals.PACKAGE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInformation() {
		return information;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInformation(String newInformation) {
		String oldInformation = information;
		information = newInformation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InstallPackagesPackage.PACKAGE_TYPE__INFORMATION, oldInformation, information));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDisplayName(String newDisplayName) {
		String oldDisplayName = displayName;
		displayName = newDisplayName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InstallPackagesPackage.PACKAGE_TYPE__DISPLAY_NAME, oldDisplayName, displayName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInstallFilePath() {
		return installFilePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInstallFilePath(String newInstallFilePath) {
		String oldInstallFilePath = installFilePath;
		installFilePath = newInstallFilePath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InstallPackagesPackage.PACKAGE_TYPE__INSTALL_FILE_PATH, oldInstallFilePath, installFilePath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPackageVersion() {
		return packageVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPackageVersion(String newPackageVersion) {
		String oldPackageVersion = packageVersion;
		packageVersion = newPackageVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InstallPackagesPackage.PACKAGE_TYPE__PACKAGE_VERSION, oldPackageVersion, packageVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSdkFamily() {
		return sdkFamily;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSdkFamily(String newSdkFamily) {
		String oldSdkFamily = sdkFamily;
		sdkFamily = newSdkFamily;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InstallPackagesPackage.PACKAGE_TYPE__SDK_FAMILY, oldSdkFamily, sdkFamily));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSdkVersion() {
		return sdkVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSdkVersion(String newSdkVersion) {
		String oldSdkVersion = sdkVersion;
		sdkVersion = newSdkVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InstallPackagesPackage.PACKAGE_TYPE__SDK_VERSION, oldSdkVersion, sdkVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case InstallPackagesPackage.PACKAGE_TYPE__INFORMATION:
				return getInformation();
			case InstallPackagesPackage.PACKAGE_TYPE__DISPLAY_NAME:
				return getDisplayName();
			case InstallPackagesPackage.PACKAGE_TYPE__INSTALL_FILE_PATH:
				return getInstallFilePath();
			case InstallPackagesPackage.PACKAGE_TYPE__PACKAGE_VERSION:
				return getPackageVersion();
			case InstallPackagesPackage.PACKAGE_TYPE__SDK_FAMILY:
				return getSdkFamily();
			case InstallPackagesPackage.PACKAGE_TYPE__SDK_VERSION:
				return getSdkVersion();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case InstallPackagesPackage.PACKAGE_TYPE__INFORMATION:
				setInformation((String)newValue);
				return;
			case InstallPackagesPackage.PACKAGE_TYPE__DISPLAY_NAME:
				setDisplayName((String)newValue);
				return;
			case InstallPackagesPackage.PACKAGE_TYPE__INSTALL_FILE_PATH:
				setInstallFilePath((String)newValue);
				return;
			case InstallPackagesPackage.PACKAGE_TYPE__PACKAGE_VERSION:
				setPackageVersion((String)newValue);
				return;
			case InstallPackagesPackage.PACKAGE_TYPE__SDK_FAMILY:
				setSdkFamily((String)newValue);
				return;
			case InstallPackagesPackage.PACKAGE_TYPE__SDK_VERSION:
				setSdkVersion((String)newValue);
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
			case InstallPackagesPackage.PACKAGE_TYPE__INFORMATION:
				setInformation(INFORMATION_EDEFAULT);
				return;
			case InstallPackagesPackage.PACKAGE_TYPE__DISPLAY_NAME:
				setDisplayName(DISPLAY_NAME_EDEFAULT);
				return;
			case InstallPackagesPackage.PACKAGE_TYPE__INSTALL_FILE_PATH:
				setInstallFilePath(INSTALL_FILE_PATH_EDEFAULT);
				return;
			case InstallPackagesPackage.PACKAGE_TYPE__PACKAGE_VERSION:
				setPackageVersion(PACKAGE_VERSION_EDEFAULT);
				return;
			case InstallPackagesPackage.PACKAGE_TYPE__SDK_FAMILY:
				setSdkFamily(SDK_FAMILY_EDEFAULT);
				return;
			case InstallPackagesPackage.PACKAGE_TYPE__SDK_VERSION:
				setSdkVersion(SDK_VERSION_EDEFAULT);
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
			case InstallPackagesPackage.PACKAGE_TYPE__INFORMATION:
				return INFORMATION_EDEFAULT == null ? information != null : !INFORMATION_EDEFAULT.equals(information);
			case InstallPackagesPackage.PACKAGE_TYPE__DISPLAY_NAME:
				return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
			case InstallPackagesPackage.PACKAGE_TYPE__INSTALL_FILE_PATH:
				return INSTALL_FILE_PATH_EDEFAULT == null ? installFilePath != null : !INSTALL_FILE_PATH_EDEFAULT.equals(installFilePath);
			case InstallPackagesPackage.PACKAGE_TYPE__PACKAGE_VERSION:
				return PACKAGE_VERSION_EDEFAULT == null ? packageVersion != null : !PACKAGE_VERSION_EDEFAULT.equals(packageVersion);
			case InstallPackagesPackage.PACKAGE_TYPE__SDK_FAMILY:
				return SDK_FAMILY_EDEFAULT == null ? sdkFamily != null : !SDK_FAMILY_EDEFAULT.equals(sdkFamily);
			case InstallPackagesPackage.PACKAGE_TYPE__SDK_VERSION:
				return SDK_VERSION_EDEFAULT == null ? sdkVersion != null : !SDK_VERSION_EDEFAULT.equals(sdkVersion);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (information: ");
		result.append(information);
		result.append(", displayName: ");
		result.append(displayName);
		result.append(", installFilePath: ");
		result.append(installFilePath);
		result.append(", packageVersion: ");
		result.append(packageVersion);
		result.append(", sdkFamily: ");
		result.append(sdkFamily);
		result.append(", sdkVersion: ");
		result.append(sdkVersion);
		result.append(')');
		return result.toString();
	}

} //PackageTypeImpl
