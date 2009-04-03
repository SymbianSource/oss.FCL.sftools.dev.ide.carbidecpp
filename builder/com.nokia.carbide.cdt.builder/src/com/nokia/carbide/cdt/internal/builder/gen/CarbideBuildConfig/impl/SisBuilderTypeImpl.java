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
package com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl;

import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage;
import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Sis Builder Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.SisBuilderTypeImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.SisBuilderTypeImpl#getAdditionalOptions <em>Additional Options</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.SisBuilderTypeImpl#getCert <em>Cert</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.SisBuilderTypeImpl#getContentSearchLocation <em>Content Search Location</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.SisBuilderTypeImpl#isCreateStubFormat <em>Create Stub Format</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.SisBuilderTypeImpl#getKey <em>Key</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.SisBuilderTypeImpl#getOutputFileName <em>Output File Name</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.SisBuilderTypeImpl#getPassword <em>Password</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.SisBuilderTypeImpl#getPkgFile <em>Pkg File</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.SisBuilderTypeImpl#getSignedFileName <em>Signed File Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SisBuilderTypeImpl extends EObjectImpl implements SisBuilderType {
	/**
	 * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMixed()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap mixed = null;

	/**
	 * The default value of the '{@link #getAdditionalOptions() <em>Additional Options</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdditionalOptions()
	 * @generated
	 * @ordered
	 */
	protected static final String ADDITIONAL_OPTIONS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAdditionalOptions() <em>Additional Options</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdditionalOptions()
	 * @generated
	 * @ordered
	 */
	protected String additionalOptions = ADDITIONAL_OPTIONS_EDEFAULT;

	/**
	 * The default value of the '{@link #getCert() <em>Cert</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCert()
	 * @generated
	 * @ordered
	 */
	protected static final String CERT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCert() <em>Cert</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCert()
	 * @generated
	 * @ordered
	 */
	protected String cert = CERT_EDEFAULT;

	/**
	 * The default value of the '{@link #getContentSearchLocation() <em>Content Search Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContentSearchLocation()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTENT_SEARCH_LOCATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getContentSearchLocation() <em>Content Search Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContentSearchLocation()
	 * @generated
	 * @ordered
	 */
	protected String contentSearchLocation = CONTENT_SEARCH_LOCATION_EDEFAULT;

	/**
	 * The default value of the '{@link #isCreateStubFormat() <em>Create Stub Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCreateStubFormat()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CREATE_STUB_FORMAT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCreateStubFormat() <em>Create Stub Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCreateStubFormat()
	 * @generated
	 * @ordered
	 */
	protected boolean createStubFormat = CREATE_STUB_FORMAT_EDEFAULT;

	/**
	 * This is true if the Create Stub Format attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean createStubFormatESet = false;

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
	 * The default value of the '{@link #getOutputFileName() <em>Output File Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputFileName()
	 * @generated
	 * @ordered
	 */
	protected static final String OUTPUT_FILE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOutputFileName() <em>Output File Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputFileName()
	 * @generated
	 * @ordered
	 */
	protected String outputFileName = OUTPUT_FILE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getPassword() <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPassword()
	 * @generated
	 * @ordered
	 */
	protected static final String PASSWORD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPassword() <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPassword()
	 * @generated
	 * @ordered
	 */
	protected String password = PASSWORD_EDEFAULT;

	/**
	 * The default value of the '{@link #getPkgFile() <em>Pkg File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPkgFile()
	 * @generated
	 * @ordered
	 */
	protected static final String PKG_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPkgFile() <em>Pkg File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPkgFile()
	 * @generated
	 * @ordered
	 */
	protected String pkgFile = PKG_FILE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSignedFileName() <em>Signed File Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSignedFileName()
	 * @generated
	 * @ordered
	 */
	protected static final String SIGNED_FILE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSignedFileName() <em>Signed File Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSignedFileName()
	 * @generated
	 * @ordered
	 */
	protected String signedFileName = SIGNED_FILE_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SisBuilderTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return CarbideBuildConfigPackage.Literals.SIS_BUILDER_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, CarbideBuildConfigPackage.SIS_BUILDER_TYPE__MIXED);
		}
		return mixed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAdditionalOptions() {
		return additionalOptions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAdditionalOptions(String newAdditionalOptions) {
		String oldAdditionalOptions = additionalOptions;
		additionalOptions = newAdditionalOptions;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CarbideBuildConfigPackage.SIS_BUILDER_TYPE__ADDITIONAL_OPTIONS, oldAdditionalOptions, additionalOptions));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCert() {
		return cert;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCert(String newCert) {
		String oldCert = cert;
		cert = newCert;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CarbideBuildConfigPackage.SIS_BUILDER_TYPE__CERT, oldCert, cert));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getContentSearchLocation() {
		return contentSearchLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContentSearchLocation(String newContentSearchLocation) {
		String oldContentSearchLocation = contentSearchLocation;
		contentSearchLocation = newContentSearchLocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CarbideBuildConfigPackage.SIS_BUILDER_TYPE__CONTENT_SEARCH_LOCATION, oldContentSearchLocation, contentSearchLocation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isCreateStubFormat() {
		return createStubFormat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCreateStubFormat(boolean newCreateStubFormat) {
		boolean oldCreateStubFormat = createStubFormat;
		createStubFormat = newCreateStubFormat;
		boolean oldCreateStubFormatESet = createStubFormatESet;
		createStubFormatESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CarbideBuildConfigPackage.SIS_BUILDER_TYPE__CREATE_STUB_FORMAT, oldCreateStubFormat, createStubFormat, !oldCreateStubFormatESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetCreateStubFormat() {
		boolean oldCreateStubFormat = createStubFormat;
		boolean oldCreateStubFormatESet = createStubFormatESet;
		createStubFormat = CREATE_STUB_FORMAT_EDEFAULT;
		createStubFormatESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, CarbideBuildConfigPackage.SIS_BUILDER_TYPE__CREATE_STUB_FORMAT, oldCreateStubFormat, CREATE_STUB_FORMAT_EDEFAULT, oldCreateStubFormatESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetCreateStubFormat() {
		return createStubFormatESet;
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
			eNotify(new ENotificationImpl(this, Notification.SET, CarbideBuildConfigPackage.SIS_BUILDER_TYPE__KEY, oldKey, key));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOutputFileName() {
		return outputFileName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOutputFileName(String newOutputFileName) {
		String oldOutputFileName = outputFileName;
		outputFileName = newOutputFileName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CarbideBuildConfigPackage.SIS_BUILDER_TYPE__OUTPUT_FILE_NAME, oldOutputFileName, outputFileName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPassword(String newPassword) {
		String oldPassword = password;
		password = newPassword;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CarbideBuildConfigPackage.SIS_BUILDER_TYPE__PASSWORD, oldPassword, password));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPkgFile() {
		return pkgFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPkgFile(String newPkgFile) {
		String oldPkgFile = pkgFile;
		pkgFile = newPkgFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CarbideBuildConfigPackage.SIS_BUILDER_TYPE__PKG_FILE, oldPkgFile, pkgFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSignedFileName() {
		return signedFileName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSignedFileName(String newSignedFileName) {
		String oldSignedFileName = signedFileName;
		signedFileName = newSignedFileName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CarbideBuildConfigPackage.SIS_BUILDER_TYPE__SIGNED_FILE_NAME, oldSignedFileName, signedFileName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__MIXED:
				return ((InternalEList)getMixed()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__ADDITIONAL_OPTIONS:
				return getAdditionalOptions();
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__CERT:
				return getCert();
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__CONTENT_SEARCH_LOCATION:
				return getContentSearchLocation();
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__CREATE_STUB_FORMAT:
				return isCreateStubFormat() ? Boolean.TRUE : Boolean.FALSE;
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__KEY:
				return getKey();
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__OUTPUT_FILE_NAME:
				return getOutputFileName();
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__PASSWORD:
				return getPassword();
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__PKG_FILE:
				return getPkgFile();
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__SIGNED_FILE_NAME:
				return getSignedFileName();
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
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__ADDITIONAL_OPTIONS:
				setAdditionalOptions((String)newValue);
				return;
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__CERT:
				setCert((String)newValue);
				return;
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__CONTENT_SEARCH_LOCATION:
				setContentSearchLocation((String)newValue);
				return;
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__CREATE_STUB_FORMAT:
				setCreateStubFormat(((Boolean)newValue).booleanValue());
				return;
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__KEY:
				setKey((String)newValue);
				return;
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__OUTPUT_FILE_NAME:
				setOutputFileName((String)newValue);
				return;
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__PASSWORD:
				setPassword((String)newValue);
				return;
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__PKG_FILE:
				setPkgFile((String)newValue);
				return;
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__SIGNED_FILE_NAME:
				setSignedFileName((String)newValue);
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
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__MIXED:
				getMixed().clear();
				return;
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__ADDITIONAL_OPTIONS:
				setAdditionalOptions(ADDITIONAL_OPTIONS_EDEFAULT);
				return;
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__CERT:
				setCert(CERT_EDEFAULT);
				return;
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__CONTENT_SEARCH_LOCATION:
				setContentSearchLocation(CONTENT_SEARCH_LOCATION_EDEFAULT);
				return;
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__CREATE_STUB_FORMAT:
				unsetCreateStubFormat();
				return;
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__KEY:
				setKey(KEY_EDEFAULT);
				return;
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__OUTPUT_FILE_NAME:
				setOutputFileName(OUTPUT_FILE_NAME_EDEFAULT);
				return;
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__PASSWORD:
				setPassword(PASSWORD_EDEFAULT);
				return;
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__PKG_FILE:
				setPkgFile(PKG_FILE_EDEFAULT);
				return;
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__SIGNED_FILE_NAME:
				setSignedFileName(SIGNED_FILE_NAME_EDEFAULT);
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
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__MIXED:
				return mixed != null && !mixed.isEmpty();
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__ADDITIONAL_OPTIONS:
				return ADDITIONAL_OPTIONS_EDEFAULT == null ? additionalOptions != null : !ADDITIONAL_OPTIONS_EDEFAULT.equals(additionalOptions);
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__CERT:
				return CERT_EDEFAULT == null ? cert != null : !CERT_EDEFAULT.equals(cert);
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__CONTENT_SEARCH_LOCATION:
				return CONTENT_SEARCH_LOCATION_EDEFAULT == null ? contentSearchLocation != null : !CONTENT_SEARCH_LOCATION_EDEFAULT.equals(contentSearchLocation);
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__CREATE_STUB_FORMAT:
				return isSetCreateStubFormat();
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__KEY:
				return KEY_EDEFAULT == null ? key != null : !KEY_EDEFAULT.equals(key);
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__OUTPUT_FILE_NAME:
				return OUTPUT_FILE_NAME_EDEFAULT == null ? outputFileName != null : !OUTPUT_FILE_NAME_EDEFAULT.equals(outputFileName);
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__PASSWORD:
				return PASSWORD_EDEFAULT == null ? password != null : !PASSWORD_EDEFAULT.equals(password);
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__PKG_FILE:
				return PKG_FILE_EDEFAULT == null ? pkgFile != null : !PKG_FILE_EDEFAULT.equals(pkgFile);
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE__SIGNED_FILE_NAME:
				return SIGNED_FILE_NAME_EDEFAULT == null ? signedFileName != null : !SIGNED_FILE_NAME_EDEFAULT.equals(signedFileName);
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
		result.append(" (mixed: ");
		result.append(mixed);
		result.append(", additionalOptions: ");
		result.append(additionalOptions);
		result.append(", cert: ");
		result.append(cert);
		result.append(", contentSearchLocation: ");
		result.append(contentSearchLocation);
		result.append(", createStubFormat: ");
		if (createStubFormatESet) result.append(createStubFormat); else result.append("<unset>");
		result.append(", key: ");
		result.append(key);
		result.append(", outputFileName: ");
		result.append(outputFileName);
		result.append(", password: ");
		result.append(password);
		result.append(", pkgFile: ");
		result.append(pkgFile);
		result.append(", signedFileName: ");
		result.append(signedFileName);
		result.append(')');
		return result.toString();
	}

} //SisBuilderTypeImpl