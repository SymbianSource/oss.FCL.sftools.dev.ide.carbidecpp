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
package com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.util;

import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStorePackage
 * @generated
 */
public class SymbianMacroStoreAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static SymbianMacroStorePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymbianMacroStoreAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = SymbianMacroStorePackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch the delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SymbianMacroStoreSwitch modelSwitch =
		new SymbianMacroStoreSwitch() {
			public Object caseDocumentRoot(DocumentRoot object) {
				return createDocumentRootAdapter();
			}
			public Object caseOsMacrosType(OsMacrosType object) {
				return createOsMacrosTypeAdapter();
			}
			public Object caseOsVersionType(OsVersionType object) {
				return createOsVersionTypeAdapter();
			}
			public Object casePlatformMacrosType(PlatformMacrosType object) {
				return createPlatformMacrosTypeAdapter();
			}
			public Object casePlatformType(PlatformType object) {
				return createPlatformTypeAdapter();
			}
			public Object caseSdkVendorType(SdkVendorType object) {
				return createSdkVendorTypeAdapter();
			}
			public Object caseSymbianMacroStoreType(SymbianMacroStoreType object) {
				return createSymbianMacroStoreTypeAdapter();
			}
			public Object caseSymbianOSMacrosType(SymbianOSMacrosType object) {
				return createSymbianOSMacrosTypeAdapter();
			}
			public Object caseVendorMacrosType(VendorMacrosType object) {
				return createVendorMacrosTypeAdapter();
			}
			public Object defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	public Adapter createAdapter(Notifier target) {
		return (Adapter)modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot
	 * @generated
	 */
	public Adapter createDocumentRootAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsMacrosType <em>Os Macros Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsMacrosType
	 * @generated
	 */
	public Adapter createOsMacrosTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsVersionType <em>Os Version Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsVersionType
	 * @generated
	 */
	public Adapter createOsVersionTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.PlatformMacrosType <em>Platform Macros Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.PlatformMacrosType
	 * @generated
	 */
	public Adapter createPlatformMacrosTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.PlatformType <em>Platform Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.PlatformType
	 * @generated
	 */
	public Adapter createPlatformTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SdkVendorType <em>Sdk Vendor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SdkVendorType
	 * @generated
	 */
	public Adapter createSdkVendorTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStoreType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStoreType
	 * @generated
	 */
	public Adapter createSymbianMacroStoreTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianOSMacrosType <em>Symbian OS Macros Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianOSMacrosType
	 * @generated
	 */
	public Adapter createSymbianOSMacrosTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.VendorMacrosType <em>Vendor Macros Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.VendorMacrosType
	 * @generated
	 */
	public Adapter createVendorMacrosTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //SymbianMacroStoreAdapterFactory
