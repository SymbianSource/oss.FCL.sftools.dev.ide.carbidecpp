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

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStorePackage
 * @generated
 */
public class SymbianMacroStoreSwitch {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static SymbianMacroStorePackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymbianMacroStoreSwitch() {
		if (modelPackage == null) {
			modelPackage = SymbianMacroStorePackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public Object doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected Object doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch((EClass)eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected Object doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case SymbianMacroStorePackage.DOCUMENT_ROOT: {
				DocumentRoot documentRoot = (DocumentRoot)theEObject;
				Object result = caseDocumentRoot(documentRoot);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SymbianMacroStorePackage.OS_MACROS_TYPE: {
				OsMacrosType osMacrosType = (OsMacrosType)theEObject;
				Object result = caseOsMacrosType(osMacrosType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SymbianMacroStorePackage.OS_VERSION_TYPE: {
				OsVersionType osVersionType = (OsVersionType)theEObject;
				Object result = caseOsVersionType(osVersionType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SymbianMacroStorePackage.PLATFORM_MACROS_TYPE: {
				PlatformMacrosType platformMacrosType = (PlatformMacrosType)theEObject;
				Object result = casePlatformMacrosType(platformMacrosType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SymbianMacroStorePackage.PLATFORM_TYPE: {
				PlatformType platformType = (PlatformType)theEObject;
				Object result = casePlatformType(platformType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SymbianMacroStorePackage.SDK_VENDOR_TYPE: {
				SdkVendorType sdkVendorType = (SdkVendorType)theEObject;
				Object result = caseSdkVendorType(sdkVendorType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SymbianMacroStorePackage.SYMBIAN_MACRO_STORE_TYPE: {
				SymbianMacroStoreType symbianMacroStoreType = (SymbianMacroStoreType)theEObject;
				Object result = caseSymbianMacroStoreType(symbianMacroStoreType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SymbianMacroStorePackage.SYMBIAN_OS_MACROS_TYPE: {
				SymbianOSMacrosType symbianOSMacrosType = (SymbianOSMacrosType)theEObject;
				Object result = caseSymbianOSMacrosType(symbianOSMacrosType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SymbianMacroStorePackage.VENDOR_MACROS_TYPE: {
				VendorMacrosType vendorMacrosType = (VendorMacrosType)theEObject;
				Object result = caseVendorMacrosType(vendorMacrosType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Document Root</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDocumentRoot(DocumentRoot object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Os Macros Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Os Macros Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseOsMacrosType(OsMacrosType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Os Version Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Os Version Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseOsVersionType(OsVersionType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Platform Macros Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Platform Macros Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePlatformMacrosType(PlatformMacrosType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Platform Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Platform Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePlatformType(PlatformType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Sdk Vendor Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Sdk Vendor Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseSdkVendorType(SdkVendorType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseSymbianMacroStoreType(SymbianMacroStoreType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Symbian OS Macros Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Symbian OS Macros Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseSymbianOSMacrosType(SymbianOSMacrosType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Vendor Macros Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Vendor Macros Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseVendorMacrosType(VendorMacrosType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public Object defaultCase(EObject object) {
		return null;
	}

} //SymbianMacroStoreSwitch
