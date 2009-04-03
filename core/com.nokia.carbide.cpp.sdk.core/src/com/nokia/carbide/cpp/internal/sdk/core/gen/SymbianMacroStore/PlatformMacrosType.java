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
*/
package com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Platform Macros Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.PlatformMacrosType#getPlatform <em>Platform</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStorePackage#getPlatformMacrosType()
 * @model extendedMetaData="name='platformMacros_._type' kind='elementOnly'"
 * @generated
 */
public interface PlatformMacrosType extends EObject {
	/**
	 * Returns the value of the '<em><b>Platform</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.PlatformType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Platform</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Platform</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStorePackage#getPlatformMacrosType_Platform()
	 * @model type="com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.PlatformType" containment="true" required="true"
	 *        extendedMetaData="kind='element' name='platform' namespace='##targetNamespace'"
	 * @generated
	 */
	EList getPlatform();

} // PlatformMacrosType