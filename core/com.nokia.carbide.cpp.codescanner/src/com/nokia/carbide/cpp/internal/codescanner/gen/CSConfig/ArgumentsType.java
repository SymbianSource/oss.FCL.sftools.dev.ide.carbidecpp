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

package com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Arguments Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ArgumentsType#getInput <em>Input</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ArgumentsType#getLxr <em>Lxr</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ArgumentsType#getLxrversion <em>Lxrversion</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ArgumentsType#getOutputformat <em>Outputformat</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ArgumentsType#getTimestampedoutput <em>Timestampedoutput</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getArgumentsType()
 * @model extendedMetaData="name='arguments_._type' kind='elementOnly'"
 * @generated
 */
public interface ArgumentsType extends EObject {
	/**
	 * Returns the value of the '<em><b>Input</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Input</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Input</em>' attribute list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getArgumentsType_Input()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='input' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<String> getInput();

	/**
	 * Returns the value of the '<em><b>Lxr</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lxr</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lxr</em>' attribute.
	 * @see #setLxr(String)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getArgumentsType_Lxr()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='lxr' namespace='##targetNamespace'"
	 * @generated
	 */
	String getLxr();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ArgumentsType#getLxr <em>Lxr</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lxr</em>' attribute.
	 * @see #getLxr()
	 * @generated
	 */
	void setLxr(String value);

	/**
	 * Returns the value of the '<em><b>Lxrversion</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lxrversion</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lxrversion</em>' attribute.
	 * @see #setLxrversion(String)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getArgumentsType_Lxrversion()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='lxrversion' namespace='##targetNamespace'"
	 * @generated
	 */
	String getLxrversion();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ArgumentsType#getLxrversion <em>Lxrversion</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lxrversion</em>' attribute.
	 * @see #getLxrversion()
	 * @generated
	 */
	void setLxrversion(String value);

	/**
	 * Returns the value of the '<em><b>Outputformat</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outputformat</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outputformat</em>' attribute.
	 * @see #setOutputformat(String)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getArgumentsType_Outputformat()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='outputformat' namespace='##targetNamespace'"
	 * @generated
	 */
	String getOutputformat();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ArgumentsType#getOutputformat <em>Outputformat</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Outputformat</em>' attribute.
	 * @see #getOutputformat()
	 * @generated
	 */
	void setOutputformat(String value);

	/**
	 * Returns the value of the '<em><b>Timestampedoutput</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Timestampedoutput</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Timestampedoutput</em>' attribute.
	 * @see #setTimestampedoutput(String)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getArgumentsType_Timestampedoutput()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='timestampedoutput' namespace='##targetNamespace'"
	 * @generated
	 */
	String getTimestampedoutput();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ArgumentsType#getTimestampedoutput <em>Timestampedoutput</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Timestampedoutput</em>' attribute.
	 * @see #getTimestampedoutput()
	 * @generated
	 */
	void setTimestampedoutput(String value);

} // ArgumentsType
