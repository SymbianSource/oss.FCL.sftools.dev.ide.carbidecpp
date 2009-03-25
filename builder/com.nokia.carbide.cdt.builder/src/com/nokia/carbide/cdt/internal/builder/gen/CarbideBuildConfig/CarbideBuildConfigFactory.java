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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage
 * @generated
 */
public interface CarbideBuildConfigFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CarbideBuildConfigFactory eINSTANCE = com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Carbide Builder Config Info Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Carbide Builder Config Info Type</em>'.
	 * @generated
	 */
	CarbideBuilderConfigInfoType createCarbideBuilderConfigInfoType();

	/**
	 * Returns a new object of class '<em>Configuration Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Configuration Type</em>'.
	 * @generated
	 */
	ConfigurationType createConfigurationType();

	/**
	 * Returns a new object of class '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Document Root</em>'.
	 * @generated
	 */
	DocumentRoot createDocumentRoot();

	/**
	 * Returns a new object of class '<em>Env Vars Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Env Vars Type</em>'.
	 * @generated
	 */
	EnvVarsType createEnvVarsType();

	/**
	 * Returns a new object of class '<em>Sis Builder Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Sis Builder Type</em>'.
	 * @generated
	 */
	SisBuilderType createSisBuilderType();

	/**
	 * Returns a new object of class '<em>Var Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Var Type</em>'.
	 * @generated
	 */
	VarType createVarType();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	CarbideBuildConfigPackage getCarbideBuildConfigPackage();

} //CarbideBuildConfigFactory
