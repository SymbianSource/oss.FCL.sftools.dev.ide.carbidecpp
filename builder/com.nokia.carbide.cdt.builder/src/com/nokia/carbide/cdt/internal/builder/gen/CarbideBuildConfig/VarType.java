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
 * A representation of the model object '<em><b>Var Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.VarType#getName <em>Name</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.VarType#getUse <em>Use</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.VarType#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage#getVarType()
 * @model extendedMetaData="name='var_._type' kind='empty'"
 * @generated
 */
public interface VarType extends EObject {
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
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage#getVarType_Name()
	 * @model unique="false" dataType="com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.NameType1" required="true"
	 *        extendedMetaData="kind='attribute' name='name' namespace='##targetNamespace'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.VarType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Use</b></em>' attribute.
	 * The default value is <code>"prepend"</code>.
	 * The literals are from the enumeration {@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.UseType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Use</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Use</em>' attribute.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.UseType
	 * @see #isSetUse()
	 * @see #unsetUse()
	 * @see #setUse(UseType)
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage#getVarType_Use()
	 * @model default="prepend" unique="false" unsettable="true" required="true"
	 *        extendedMetaData="kind='attribute' name='use' namespace='##targetNamespace'"
	 * @generated
	 */
	UseType getUse();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.VarType#getUse <em>Use</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Use</em>' attribute.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.UseType
	 * @see #isSetUse()
	 * @see #unsetUse()
	 * @see #getUse()
	 * @generated
	 */
	void setUse(UseType value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.VarType#getUse <em>Use</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetUse()
	 * @see #getUse()
	 * @see #setUse(UseType)
	 * @generated
	 */
	void unsetUse();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.VarType#getUse <em>Use</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Use</em>' attribute is set.
	 * @see #unsetUse()
	 * @see #getUse()
	 * @see #setUse(UseType)
	 * @generated
	 */
	boolean isSetUse();

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage#getVarType_Value()
	 * @model unique="false" dataType="com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ValueType" required="true"
	 *        extendedMetaData="kind='attribute' name='value' namespace='##targetNamespace'"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.VarType#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

} // VarType