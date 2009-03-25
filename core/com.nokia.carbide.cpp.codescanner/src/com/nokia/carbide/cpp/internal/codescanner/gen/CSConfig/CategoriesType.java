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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Categories Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CategoriesType#getCanpanic <em>Canpanic</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CategoriesType#getCodereview <em>Codereview</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CategoriesType#getCodingstandards <em>Codingstandards</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CategoriesType#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CategoriesType#getFunctionality <em>Functionality</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CategoriesType#getLegal <em>Legal</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CategoriesType#getLocalisation <em>Localisation</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CategoriesType#getOther <em>Other</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CategoriesType#getPanic <em>Panic</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CategoriesType#getPerformance <em>Performance</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getCategoriesType()
 * @model extendedMetaData="name='categories_._type' kind='elementOnly'"
 * @generated
 */
public interface CategoriesType extends EObject {
	/**
	 * Returns the value of the '<em><b>Canpanic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Canpanic</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Canpanic</em>' containment reference.
	 * @see #setCanpanic(CanpanicType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getCategoriesType_Canpanic()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='canpanic' namespace='##targetNamespace'"
	 * @generated
	 */
	CanpanicType getCanpanic();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CategoriesType#getCanpanic <em>Canpanic</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Canpanic</em>' containment reference.
	 * @see #getCanpanic()
	 * @generated
	 */
	void setCanpanic(CanpanicType value);

	/**
	 * Returns the value of the '<em><b>Codereview</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Codereview</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Codereview</em>' containment reference.
	 * @see #setCodereview(CodereviewType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getCategoriesType_Codereview()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='codereview' namespace='##targetNamespace'"
	 * @generated
	 */
	CodereviewType getCodereview();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CategoriesType#getCodereview <em>Codereview</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Codereview</em>' containment reference.
	 * @see #getCodereview()
	 * @generated
	 */
	void setCodereview(CodereviewType value);

	/**
	 * Returns the value of the '<em><b>Codingstandards</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Codingstandards</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Codingstandards</em>' containment reference.
	 * @see #setCodingstandards(CodingstandardsType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getCategoriesType_Codingstandards()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='codingstandards' namespace='##targetNamespace'"
	 * @generated
	 */
	CodingstandardsType getCodingstandards();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CategoriesType#getCodingstandards <em>Codingstandards</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Codingstandards</em>' containment reference.
	 * @see #getCodingstandards()
	 * @generated
	 */
	void setCodingstandards(CodingstandardsType value);

	/**
	 * Returns the value of the '<em><b>Documentation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Documentation</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Documentation</em>' containment reference.
	 * @see #setDocumentation(DocumentationType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getCategoriesType_Documentation()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='documentation' namespace='##targetNamespace'"
	 * @generated
	 */
	DocumentationType getDocumentation();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CategoriesType#getDocumentation <em>Documentation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Documentation</em>' containment reference.
	 * @see #getDocumentation()
	 * @generated
	 */
	void setDocumentation(DocumentationType value);

	/**
	 * Returns the value of the '<em><b>Functionality</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Functionality</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Functionality</em>' containment reference.
	 * @see #setFunctionality(FunctionalityType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getCategoriesType_Functionality()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='functionality' namespace='##targetNamespace'"
	 * @generated
	 */
	FunctionalityType getFunctionality();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CategoriesType#getFunctionality <em>Functionality</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Functionality</em>' containment reference.
	 * @see #getFunctionality()
	 * @generated
	 */
	void setFunctionality(FunctionalityType value);

	/**
	 * Returns the value of the '<em><b>Legal</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Legal</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Legal</em>' containment reference.
	 * @see #setLegal(LegalType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getCategoriesType_Legal()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='legal' namespace='##targetNamespace'"
	 * @generated
	 */
	LegalType getLegal();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CategoriesType#getLegal <em>Legal</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Legal</em>' containment reference.
	 * @see #getLegal()
	 * @generated
	 */
	void setLegal(LegalType value);

	/**
	 * Returns the value of the '<em><b>Localisation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Localisation</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Localisation</em>' containment reference.
	 * @see #setLocalisation(LocalisationType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getCategoriesType_Localisation()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='localisation' namespace='##targetNamespace'"
	 * @generated
	 */
	LocalisationType getLocalisation();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CategoriesType#getLocalisation <em>Localisation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Localisation</em>' containment reference.
	 * @see #getLocalisation()
	 * @generated
	 */
	void setLocalisation(LocalisationType value);

	/**
	 * Returns the value of the '<em><b>Other</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Other</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Other</em>' containment reference.
	 * @see #setOther(OtherType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getCategoriesType_Other()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='other' namespace='##targetNamespace'"
	 * @generated
	 */
	OtherType getOther();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CategoriesType#getOther <em>Other</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Other</em>' containment reference.
	 * @see #getOther()
	 * @generated
	 */
	void setOther(OtherType value);

	/**
	 * Returns the value of the '<em><b>Panic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Panic</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Panic</em>' containment reference.
	 * @see #setPanic(PanicType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getCategoriesType_Panic()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='panic' namespace='##targetNamespace'"
	 * @generated
	 */
	PanicType getPanic();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CategoriesType#getPanic <em>Panic</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Panic</em>' containment reference.
	 * @see #getPanic()
	 * @generated
	 */
	void setPanic(PanicType value);

	/**
	 * Returns the value of the '<em><b>Performance</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Performance</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Performance</em>' containment reference.
	 * @see #setPerformance(PerformanceType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getCategoriesType_Performance()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='performance' namespace='##targetNamespace'"
	 * @generated
	 */
	PerformanceType getPerformance();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CategoriesType#getPerformance <em>Performance</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Performance</em>' containment reference.
	 * @see #getPerformance()
	 * @generated
	 */
	void setPerformance(PerformanceType value);

} // CategoriesType