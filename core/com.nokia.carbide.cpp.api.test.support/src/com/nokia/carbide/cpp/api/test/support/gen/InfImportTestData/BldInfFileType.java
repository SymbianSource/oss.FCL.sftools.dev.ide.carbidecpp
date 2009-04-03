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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bld Inf File Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType#getRootDirectory <em>Root Directory</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType#getBuildConfigurations <em>Build Configurations</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType#getInfComponents <em>Inf Components</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType#getMakMakeRefs <em>Mak Make Refs</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType#getPath <em>Path</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType#getProjectName <em>Project Name</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType#getSdkId <em>Sdk Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getBldInfFileType()
 * @model extendedMetaData="name='bldInfFile_._type' kind='elementOnly'"
 * @generated
 */
public interface BldInfFileType extends EObject {
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
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getBldInfFileType_RootDirectory()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='rootDirectory' namespace='##targetNamespace'"
	 * @generated
	 */
	RootDirectoryType getRootDirectory();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType#getRootDirectory <em>Root Directory</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Root Directory</em>' containment reference.
	 * @see #getRootDirectory()
	 * @generated
	 */
	void setRootDirectory(RootDirectoryType value);

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
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getBldInfFileType_BuildConfigurations()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='buildConfigurations' namespace='##targetNamespace'"
	 * @generated
	 */
	BuildConfigurationsType getBuildConfigurations();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType#getBuildConfigurations <em>Build Configurations</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Build Configurations</em>' containment reference.
	 * @see #getBuildConfigurations()
	 * @generated
	 */
	void setBuildConfigurations(BuildConfigurationsType value);

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
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getBldInfFileType_InfComponents()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='infComponents' namespace='##targetNamespace'"
	 * @generated
	 */
	InfComponentsType getInfComponents();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType#getInfComponents <em>Inf Components</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inf Components</em>' containment reference.
	 * @see #getInfComponents()
	 * @generated
	 */
	void setInfComponents(InfComponentsType value);

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
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getBldInfFileType_MakMakeRefs()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='makMakeRefs' namespace='##targetNamespace'"
	 * @generated
	 */
	MakMakeRefsType getMakMakeRefs();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType#getMakMakeRefs <em>Mak Make Refs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mak Make Refs</em>' containment reference.
	 * @see #getMakMakeRefs()
	 * @generated
	 */
	void setMakMakeRefs(MakMakeRefsType value);

	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path</em>' attribute.
	 * @see #setPath(String)
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getBldInfFileType_Path()
	 * @model dataType="com.nokia.carbide.cpp.project.core.gen.InfImportTestData.PathType" required="true"
	 *        extendedMetaData="kind='attribute' name='path' namespace='##targetNamespace'"
	 * @generated
	 */
	String getPath();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #getPath()
	 * @generated
	 */
	void setPath(String value);

	/**
	 * Returns the value of the '<em><b>Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project Name</em>' attribute.
	 * @see #setProjectName(String)
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getBldInfFileType_ProjectName()
	 * @model dataType="com.nokia.carbide.cpp.project.core.gen.InfImportTestData.ProjectNameType" required="true"
	 *        extendedMetaData="kind='attribute' name='projectName' namespace='##targetNamespace'"
	 * @generated
	 */
	String getProjectName();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType#getProjectName <em>Project Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project Name</em>' attribute.
	 * @see #getProjectName()
	 * @generated
	 */
	void setProjectName(String value);

	/**
	 * Returns the value of the '<em><b>Sdk Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sdk Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sdk Id</em>' attribute.
	 * @see #setSdkId(String)
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getBldInfFileType_SdkId()
	 * @model dataType="com.nokia.carbide.cpp.project.core.gen.InfImportTestData.SdkIdType" required="true"
	 *        extendedMetaData="kind='attribute' name='sdkId' namespace='##targetNamespace'"
	 * @generated
	 */
	String getSdkId();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType#getSdkId <em>Sdk Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sdk Id</em>' attribute.
	 * @see #getSdkId()
	 * @generated
	 */
	void setSdkId(String value);

} // BldInfFileType
