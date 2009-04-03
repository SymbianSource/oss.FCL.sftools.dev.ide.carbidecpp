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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Configuration Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ConfigurationType#getSisBuilder <em>Sis Builder</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ConfigurationType#getEnvVars <em>Env Vars</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ConfigurationType#getEpocRoot <em>Epoc Root</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ConfigurationType#getErrorParsers <em>Error Parsers</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ConfigurationType#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage#getConfigurationType()
 * @model extendedMetaData="name='configuration_._type' kind='elementOnly'"
 * @generated
 */
public interface ConfigurationType extends EObject {
	/**
	 * Returns the value of the '<em><b>Sis Builder</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sis Builder</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sis Builder</em>' containment reference.
	 * @see #setSisBuilder(SisBuilderType)
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage#getConfigurationType_SisBuilder()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='sisBuilder' namespace='##targetNamespace'"
	 * @generated
	 */
	SisBuilderType getSisBuilder();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ConfigurationType#getSisBuilder <em>Sis Builder</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sis Builder</em>' containment reference.
	 * @see #getSisBuilder()
	 * @generated
	 */
	void setSisBuilder(SisBuilderType value);

	/**
	 * Returns the value of the '<em><b>Env Vars</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Env Vars</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Env Vars</em>' containment reference.
	 * @see #setEnvVars(EnvVarsType)
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage#getConfigurationType_EnvVars()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='envVars' namespace='##targetNamespace'"
	 * @generated
	 */
	EnvVarsType getEnvVars();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ConfigurationType#getEnvVars <em>Env Vars</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Env Vars</em>' containment reference.
	 * @see #getEnvVars()
	 * @generated
	 */
	void setEnvVars(EnvVarsType value);

	/**
	 * Returns the value of the '<em><b>Epoc Root</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Epoc Root</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Epoc Root</em>' attribute.
	 * @see #setEpocRoot(String)
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage#getConfigurationType_EpocRoot()
	 * @model unique="false" dataType="com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.EpocRootType"
	 *        extendedMetaData="kind='attribute' name='epocRoot' namespace='##targetNamespace'"
	 * @generated
	 */
	String getEpocRoot();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ConfigurationType#getEpocRoot <em>Epoc Root</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Epoc Root</em>' attribute.
	 * @see #getEpocRoot()
	 * @generated
	 */
	void setEpocRoot(String value);

	/**
	 * Returns the value of the '<em><b>Error Parsers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Error Parsers</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Error Parsers</em>' attribute.
	 * @see #setErrorParsers(String)
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage#getConfigurationType_ErrorParsers()
	 * @model unique="false" dataType="com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ErrorParsersType" required="true"
	 *        extendedMetaData="kind='attribute' name='errorParsers' namespace='##targetNamespace'"
	 * @generated
	 */
	String getErrorParsers();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ConfigurationType#getErrorParsers <em>Error Parsers</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Error Parsers</em>' attribute.
	 * @see #getErrorParsers()
	 * @generated
	 */
	void setErrorParsers(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage#getConfigurationType_Name()
	 * @model unique="false" dataType="com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.NameType" required="true"
	 *        extendedMetaData="kind='attribute' name='name' namespace='##targetNamespace'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ConfigurationType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // ConfigurationType