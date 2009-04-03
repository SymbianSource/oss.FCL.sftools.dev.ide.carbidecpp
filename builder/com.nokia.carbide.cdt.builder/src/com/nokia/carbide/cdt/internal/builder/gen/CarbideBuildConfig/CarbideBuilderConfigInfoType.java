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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Carbide Builder Config Info Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuilderConfigInfoType#getConfiguration <em>Configuration</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuilderConfigInfoType#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage#getCarbideBuilderConfigInfoType()
 * @model extendedMetaData="name='CarbideBuilderConfigInfo_._type' kind='elementOnly'"
 * @generated
 */
public interface CarbideBuilderConfigInfoType extends EObject {
	/**
	 * Returns the value of the '<em><b>Configuration</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ConfigurationType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Configuration</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Configuration</em>' containment reference list.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage#getCarbideBuilderConfigInfoType_Configuration()
	 * @model type="com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ConfigurationType" containment="true" required="true"
	 *        extendedMetaData="kind='element' name='configuration' namespace='##targetNamespace'"
	 * @generated
	 */
	EList getConfiguration();

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #isSetVersion()
	 * @see #unsetVersion()
	 * @see #setVersion(short)
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage#getCarbideBuilderConfigInfoType_Version()
	 * @model default="0" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Short"
	 *        extendedMetaData="kind='attribute' name='version' namespace='##targetNamespace'"
	 * @generated
	 */
	short getVersion();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuilderConfigInfoType#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #isSetVersion()
	 * @see #unsetVersion()
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(short value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuilderConfigInfoType#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetVersion()
	 * @see #getVersion()
	 * @see #setVersion(short)
	 * @generated
	 */
	void unsetVersion();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuilderConfigInfoType#getVersion <em>Version</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Version</em>' attribute is set.
	 * @see #unsetVersion()
	 * @see #getVersion()
	 * @see #setVersion(short)
	 * @generated
	 */
	boolean isSetVersion();

} // CarbideBuilderConfigInfoType