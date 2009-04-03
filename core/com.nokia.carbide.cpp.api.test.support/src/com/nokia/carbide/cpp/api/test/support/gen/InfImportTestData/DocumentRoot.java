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
package com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getBldInfFile <em>Bld Inf File</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getBldInfFiles <em>Bld Inf Files</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getBldInfImportData <em>Bld Inf Import Data</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getBuildConfig <em>Build Config</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getBuildConfigurations <em>Build Configurations</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getInfComponent <em>Inf Component</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getInfComponents <em>Inf Components</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getMakMakeRef <em>Mak Make Ref</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getMakMakeRefs <em>Mak Make Refs</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getRootDirectory <em>Root Directory</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getDocumentRoot()
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface DocumentRoot extends EObject {
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
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getDocumentRoot_Mixed()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='elementWildcard' name=':mixed'"
	 * @generated
	 */
	FeatureMap getMixed();

	/**
	 * Returns the value of the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XMLNS Prefix Map</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XMLNS Prefix Map</em>' map.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getDocumentRoot_XMLNSPrefixMap()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
	 *        extendedMetaData="kind='attribute' name='xmlns:prefix'"
	 * @generated
	 */
	EMap<String, String> getXMLNSPrefixMap();

	/**
	 * Returns the value of the '<em><b>XSI Schema Location</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XSI Schema Location</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XSI Schema Location</em>' map.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getDocumentRoot_XSISchemaLocation()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
	 *        extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
	 * @generated
	 */
	EMap<String, String> getXSISchemaLocation();

	/**
	 * Returns the value of the '<em><b>Bld Inf File</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bld Inf File</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bld Inf File</em>' containment reference.
	 * @see #setBldInfFile(BldInfFileType)
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getDocumentRoot_BldInfFile()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='bldInfFile' namespace='##targetNamespace'"
	 * @generated
	 */
	BldInfFileType getBldInfFile();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getBldInfFile <em>Bld Inf File</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bld Inf File</em>' containment reference.
	 * @see #getBldInfFile()
	 * @generated
	 */
	void setBldInfFile(BldInfFileType value);

	/**
	 * Returns the value of the '<em><b>Bld Inf Files</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bld Inf Files</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bld Inf Files</em>' containment reference.
	 * @see #setBldInfFiles(BldInfFilesType)
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getDocumentRoot_BldInfFiles()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='bldInfFiles' namespace='##targetNamespace'"
	 * @generated
	 */
	BldInfFilesType getBldInfFiles();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getBldInfFiles <em>Bld Inf Files</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bld Inf Files</em>' containment reference.
	 * @see #getBldInfFiles()
	 * @generated
	 */
	void setBldInfFiles(BldInfFilesType value);

	/**
	 * Returns the value of the '<em><b>Bld Inf Import Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bld Inf Import Data</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bld Inf Import Data</em>' containment reference.
	 * @see #setBldInfImportData(BldInfImportDataType)
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getDocumentRoot_BldInfImportData()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='BldInfImportData' namespace='##targetNamespace'"
	 * @generated
	 */
	BldInfImportDataType getBldInfImportData();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getBldInfImportData <em>Bld Inf Import Data</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bld Inf Import Data</em>' containment reference.
	 * @see #getBldInfImportData()
	 * @generated
	 */
	void setBldInfImportData(BldInfImportDataType value);

	/**
	 * Returns the value of the '<em><b>Build Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Build Config</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Build Config</em>' containment reference.
	 * @see #setBuildConfig(BuildConfigType)
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getDocumentRoot_BuildConfig()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='buildConfig' namespace='##targetNamespace'"
	 * @generated
	 */
	BuildConfigType getBuildConfig();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getBuildConfig <em>Build Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Build Config</em>' containment reference.
	 * @see #getBuildConfig()
	 * @generated
	 */
	void setBuildConfig(BuildConfigType value);

	/**
	 * Returns the value of the '<em><b>Build Configurations</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Build Configurations</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Build Configurations</em>' containment reference.
	 * @see #setBuildConfigurations(BuildConfigurationsType)
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getDocumentRoot_BuildConfigurations()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='buildConfigurations' namespace='##targetNamespace'"
	 * @generated
	 */
	BuildConfigurationsType getBuildConfigurations();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getBuildConfigurations <em>Build Configurations</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Build Configurations</em>' containment reference.
	 * @see #getBuildConfigurations()
	 * @generated
	 */
	void setBuildConfigurations(BuildConfigurationsType value);

	/**
	 * Returns the value of the '<em><b>Inf Component</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inf Component</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inf Component</em>' containment reference.
	 * @see #setInfComponent(InfComponentType)
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getDocumentRoot_InfComponent()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='infComponent' namespace='##targetNamespace'"
	 * @generated
	 */
	InfComponentType getInfComponent();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getInfComponent <em>Inf Component</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inf Component</em>' containment reference.
	 * @see #getInfComponent()
	 * @generated
	 */
	void setInfComponent(InfComponentType value);

	/**
	 * Returns the value of the '<em><b>Inf Components</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inf Components</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inf Components</em>' containment reference.
	 * @see #setInfComponents(InfComponentsType)
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getDocumentRoot_InfComponents()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='infComponents' namespace='##targetNamespace'"
	 * @generated
	 */
	InfComponentsType getInfComponents();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getInfComponents <em>Inf Components</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inf Components</em>' containment reference.
	 * @see #getInfComponents()
	 * @generated
	 */
	void setInfComponents(InfComponentsType value);

	/**
	 * Returns the value of the '<em><b>Mak Make Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mak Make Ref</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mak Make Ref</em>' containment reference.
	 * @see #setMakMakeRef(MakMakeRefType)
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getDocumentRoot_MakMakeRef()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='makMakeRef' namespace='##targetNamespace'"
	 * @generated
	 */
	MakMakeRefType getMakMakeRef();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getMakMakeRef <em>Mak Make Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mak Make Ref</em>' containment reference.
	 * @see #getMakMakeRef()
	 * @generated
	 */
	void setMakMakeRef(MakMakeRefType value);

	/**
	 * Returns the value of the '<em><b>Mak Make Refs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mak Make Refs</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mak Make Refs</em>' containment reference.
	 * @see #setMakMakeRefs(MakMakeRefsType)
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getDocumentRoot_MakMakeRefs()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='makMakeRefs' namespace='##targetNamespace'"
	 * @generated
	 */
	MakMakeRefsType getMakMakeRefs();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getMakMakeRefs <em>Mak Make Refs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mak Make Refs</em>' containment reference.
	 * @see #getMakMakeRefs()
	 * @generated
	 */
	void setMakMakeRefs(MakMakeRefsType value);

	/**
	 * Returns the value of the '<em><b>Root Directory</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root Directory</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Root Directory</em>' containment reference.
	 * @see #setRootDirectory(RootDirectoryType)
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getDocumentRoot_RootDirectory()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='rootDirectory' namespace='##targetNamespace'"
	 * @generated
	 */
	RootDirectoryType getRootDirectory();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getRootDirectory <em>Root Directory</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Root Directory</em>' containment reference.
	 * @see #getRootDirectory()
	 * @generated
	 */
	void setRootDirectory(RootDirectoryType value);

} // DocumentRoot
