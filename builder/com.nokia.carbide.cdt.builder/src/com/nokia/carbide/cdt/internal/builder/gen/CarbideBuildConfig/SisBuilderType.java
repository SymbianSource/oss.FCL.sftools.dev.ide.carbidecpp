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
package com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sis Builder Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getAdditionalOptions <em>Additional Options</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getCert <em>Cert</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getContentSearchLocation <em>Content Search Location</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#isCreateStubFormat <em>Create Stub Format</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getKey <em>Key</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getOutputFileName <em>Output File Name</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getPassword <em>Password</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getPkgFile <em>Pkg File</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getSignedFileName <em>Signed File Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage#getSisBuilderType()
 * @model extendedMetaData="name='sisBuilder_._type' kind='mixed'"
 * @generated
 */
public interface SisBuilderType extends EObject {
	/**
	 * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mixed</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mixed</em>' attribute list.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage#getSisBuilderType_Mixed()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='elementWildcard' name=':mixed'"
	 * @generated
	 */
	FeatureMap getMixed();

	/**
	 * Returns the value of the '<em><b>Additional Options</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Additional Options</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Additional Options</em>' attribute.
	 * @see #setAdditionalOptions(String)
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage#getSisBuilderType_AdditionalOptions()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='additionalOptions' namespace='##targetNamespace'"
	 * @generated
	 */
	String getAdditionalOptions();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getAdditionalOptions <em>Additional Options</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Additional Options</em>' attribute.
	 * @see #getAdditionalOptions()
	 * @generated
	 */
	void setAdditionalOptions(String value);

	/**
	 * Returns the value of the '<em><b>Cert</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cert</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cert</em>' attribute.
	 * @see #setCert(String)
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage#getSisBuilderType_Cert()
	 * @model unique="false" dataType="com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CertType"
	 *        extendedMetaData="kind='attribute' name='cert' namespace='##targetNamespace'"
	 * @generated
	 */
	String getCert();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getCert <em>Cert</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cert</em>' attribute.
	 * @see #getCert()
	 * @generated
	 */
	void setCert(String value);

	/**
	 * Returns the value of the '<em><b>Content Search Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Content Search Location</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Content Search Location</em>' attribute.
	 * @see #setContentSearchLocation(String)
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage#getSisBuilderType_ContentSearchLocation()
	 * @model unique="false" dataType="com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ContentSearchLocationType"
	 *        extendedMetaData="kind='attribute' name='contentSearchLocation' namespace='##targetNamespace'"
	 * @generated
	 */
	String getContentSearchLocation();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getContentSearchLocation <em>Content Search Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Content Search Location</em>' attribute.
	 * @see #getContentSearchLocation()
	 * @generated
	 */
	void setContentSearchLocation(String value);

	/**
	 * Returns the value of the '<em><b>Create Stub Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Create Stub Format</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Create Stub Format</em>' attribute.
	 * @see #isSetCreateStubFormat()
	 * @see #unsetCreateStubFormat()
	 * @see #setCreateStubFormat(boolean)
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage#getSisBuilderType_CreateStubFormat()
	 * @model unique="false" unsettable="true" dataType="com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CreateStubFormatType"
	 *        extendedMetaData="kind='attribute' name='createStubFormat' namespace='##targetNamespace'"
	 * @generated
	 */
	boolean isCreateStubFormat();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#isCreateStubFormat <em>Create Stub Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Create Stub Format</em>' attribute.
	 * @see #isSetCreateStubFormat()
	 * @see #unsetCreateStubFormat()
	 * @see #isCreateStubFormat()
	 * @generated
	 */
	void setCreateStubFormat(boolean value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#isCreateStubFormat <em>Create Stub Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetCreateStubFormat()
	 * @see #isCreateStubFormat()
	 * @see #setCreateStubFormat(boolean)
	 * @generated
	 */
	void unsetCreateStubFormat();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#isCreateStubFormat <em>Create Stub Format</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Create Stub Format</em>' attribute is set.
	 * @see #unsetCreateStubFormat()
	 * @see #isCreateStubFormat()
	 * @see #setCreateStubFormat(boolean)
	 * @generated
	 */
	boolean isSetCreateStubFormat();

	/**
	 * Returns the value of the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Key</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Key</em>' attribute.
	 * @see #setKey(String)
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage#getSisBuilderType_Key()
	 * @model unique="false" dataType="com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.KeyType"
	 *        extendedMetaData="kind='attribute' name='key' namespace='##targetNamespace'"
	 * @generated
	 */
	String getKey();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getKey <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key</em>' attribute.
	 * @see #getKey()
	 * @generated
	 */
	void setKey(String value);

	/**
	 * Returns the value of the '<em><b>Output File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Output File Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output File Name</em>' attribute.
	 * @see #setOutputFileName(String)
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage#getSisBuilderType_OutputFileName()
	 * @model unique="false" dataType="com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.OutputFileNameType"
	 *        extendedMetaData="kind='attribute' name='outputFileName' namespace='##targetNamespace'"
	 * @generated
	 */
	String getOutputFileName();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getOutputFileName <em>Output File Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Output File Name</em>' attribute.
	 * @see #getOutputFileName()
	 * @generated
	 */
	void setOutputFileName(String value);

	/**
	 * Returns the value of the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Password</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Password</em>' attribute.
	 * @see #setPassword(String)
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage#getSisBuilderType_Password()
	 * @model unique="false" dataType="com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.PasswordType"
	 *        extendedMetaData="kind='attribute' name='password' namespace='##targetNamespace'"
	 * @generated
	 */
	String getPassword();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getPassword <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Password</em>' attribute.
	 * @see #getPassword()
	 * @generated
	 */
	void setPassword(String value);

	/**
	 * Returns the value of the '<em><b>Pkg File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pkg File</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pkg File</em>' attribute.
	 * @see #setPkgFile(String)
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage#getSisBuilderType_PkgFile()
	 * @model unique="false" dataType="com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.PkgFileType" required="true"
	 *        extendedMetaData="kind='attribute' name='pkgFile' namespace='##targetNamespace'"
	 * @generated
	 */
	String getPkgFile();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getPkgFile <em>Pkg File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pkg File</em>' attribute.
	 * @see #getPkgFile()
	 * @generated
	 */
	void setPkgFile(String value);

	/**
	 * Returns the value of the '<em><b>Signed File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Signed File Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Signed File Name</em>' attribute.
	 * @see #setSignedFileName(String)
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage#getSisBuilderType_SignedFileName()
	 * @model unique="false" dataType="com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SignedFileNameType"
	 *        extendedMetaData="kind='attribute' name='signedFileName' namespace='##targetNamespace'"
	 * @generated
	 */
	String getSignedFileName();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getSignedFileName <em>Signed File Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Signed File Name</em>' attribute.
	 * @see #getSignedFileName()
	 * @generated
	 */
	void setSignedFileName(String value);

} // SisBuilderType