/**
 * <copyright>
 * </copyright>
 *
 */
package com.nokia.carbide.installpackages.gen.InstallPackages;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Package Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.installpackages.gen.InstallPackages.PackageType#getInformation <em>Information</em>}</li>
 *   <li>{@link com.nokia.carbide.installpackages.gen.InstallPackages.PackageType#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.nokia.carbide.installpackages.gen.InstallPackages.PackageType#getInstallFilePath <em>Install File Path</em>}</li>
 *   <li>{@link com.nokia.carbide.installpackages.gen.InstallPackages.PackageType#getPackageVersion <em>Package Version</em>}</li>
 *   <li>{@link com.nokia.carbide.installpackages.gen.InstallPackages.PackageType#getSdkFamily <em>Sdk Family</em>}</li>
 *   <li>{@link com.nokia.carbide.installpackages.gen.InstallPackages.PackageType#getSdkVersion <em>Sdk Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.installpackages.gen.InstallPackages.InstallPackagesPackage#getPackageType()
 * @model extendedMetaData="name='Package_._type' kind='elementOnly'"
 * @generated
 */
public interface PackageType extends EObject {
	/**
	 * Returns the value of the '<em><b>Information</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Information</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Information</em>' attribute.
	 * @see #setInformation(String)
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.InstallPackagesPackage#getPackageType_Information()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='information' namespace='##targetNamespace'"
	 * @generated
	 */
	String getInformation();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.installpackages.gen.InstallPackages.PackageType#getInformation <em>Information</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Information</em>' attribute.
	 * @see #getInformation()
	 * @generated
	 */
	void setInformation(String value);

	/**
	 * Returns the value of the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Display Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Display Name</em>' attribute.
	 * @see #setDisplayName(String)
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.InstallPackagesPackage#getPackageType_DisplayName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='displayName' namespace='##targetNamespace'"
	 * @generated
	 */
	String getDisplayName();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.installpackages.gen.InstallPackages.PackageType#getDisplayName <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Name</em>' attribute.
	 * @see #getDisplayName()
	 * @generated
	 */
	void setDisplayName(String value);

	/**
	 * Returns the value of the '<em><b>Install File Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Install File Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Install File Path</em>' attribute.
	 * @see #setInstallFilePath(String)
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.InstallPackagesPackage#getPackageType_InstallFilePath()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='installFilePath' namespace='##targetNamespace'"
	 * @generated
	 */
	String getInstallFilePath();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.installpackages.gen.InstallPackages.PackageType#getInstallFilePath <em>Install File Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Install File Path</em>' attribute.
	 * @see #getInstallFilePath()
	 * @generated
	 */
	void setInstallFilePath(String value);

	/**
	 * Returns the value of the '<em><b>Package Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Package Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package Version</em>' attribute.
	 * @see #setPackageVersion(String)
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.InstallPackagesPackage#getPackageType_PackageVersion()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='packageVersion' namespace='##targetNamespace'"
	 * @generated
	 */
	String getPackageVersion();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.installpackages.gen.InstallPackages.PackageType#getPackageVersion <em>Package Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package Version</em>' attribute.
	 * @see #getPackageVersion()
	 * @generated
	 */
	void setPackageVersion(String value);

	/**
	 * Returns the value of the '<em><b>Sdk Family</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sdk Family</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sdk Family</em>' attribute.
	 * @see #setSdkFamily(String)
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.InstallPackagesPackage#getPackageType_SdkFamily()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='sdkFamily' namespace='##targetNamespace'"
	 * @generated
	 */
	String getSdkFamily();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.installpackages.gen.InstallPackages.PackageType#getSdkFamily <em>Sdk Family</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sdk Family</em>' attribute.
	 * @see #getSdkFamily()
	 * @generated
	 */
	void setSdkFamily(String value);

	/**
	 * Returns the value of the '<em><b>Sdk Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sdk Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sdk Version</em>' attribute.
	 * @see #setSdkVersion(String)
	 * @see com.nokia.carbide.installpackages.gen.InstallPackages.InstallPackagesPackage#getPackageType_SdkVersion()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='sdkVersion' namespace='##targetNamespace'"
	 * @generated
	 */
	String getSdkVersion();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.installpackages.gen.InstallPackages.PackageType#getSdkVersion <em>Sdk Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sdk Version</em>' attribute.
	 * @see #getSdkVersion()
	 * @generated
	 */
	void setSdkVersion(String value);

} // PackageType
