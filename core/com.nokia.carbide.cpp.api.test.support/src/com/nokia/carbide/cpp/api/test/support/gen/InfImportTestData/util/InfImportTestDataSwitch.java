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
package com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.util;

import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.*;

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
 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage
 * @generated
 */
public class InfImportTestDataSwitch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static InfImportTestDataPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfImportTestDataSwitch() {
		if (modelPackage == null) {
			modelPackage = InfImportTestDataPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case InfImportTestDataPackage.BLD_INF_FILES_TYPE: {
				BldInfFilesType bldInfFilesType = (BldInfFilesType)theEObject;
				T result = caseBldInfFilesType(bldInfFilesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE: {
				BldInfFileType bldInfFileType = (BldInfFileType)theEObject;
				T result = caseBldInfFileType(bldInfFileType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfImportTestDataPackage.BLD_INF_IMPORT_DATA_TYPE: {
				BldInfImportDataType bldInfImportDataType = (BldInfImportDataType)theEObject;
				T result = caseBldInfImportDataType(bldInfImportDataType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfImportTestDataPackage.BUILD_CONFIG_TYPE: {
				BuildConfigType buildConfigType = (BuildConfigType)theEObject;
				T result = caseBuildConfigType(buildConfigType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfImportTestDataPackage.BUILD_CONFIGURATIONS_TYPE: {
				BuildConfigurationsType buildConfigurationsType = (BuildConfigurationsType)theEObject;
				T result = caseBuildConfigurationsType(buildConfigurationsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfImportTestDataPackage.DOCUMENT_ROOT: {
				DocumentRoot documentRoot = (DocumentRoot)theEObject;
				T result = caseDocumentRoot(documentRoot);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfImportTestDataPackage.INF_COMPONENTS_TYPE: {
				InfComponentsType infComponentsType = (InfComponentsType)theEObject;
				T result = caseInfComponentsType(infComponentsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfImportTestDataPackage.INF_COMPONENT_TYPE: {
				InfComponentType infComponentType = (InfComponentType)theEObject;
				T result = caseInfComponentType(infComponentType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfImportTestDataPackage.MAK_MAKE_REFS_TYPE: {
				MakMakeRefsType makMakeRefsType = (MakMakeRefsType)theEObject;
				T result = caseMakMakeRefsType(makMakeRefsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfImportTestDataPackage.MAK_MAKE_REF_TYPE: {
				MakMakeRefType makMakeRefType = (MakMakeRefType)theEObject;
				T result = caseMakMakeRefType(makMakeRefType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfImportTestDataPackage.ROOT_DIRECTORY_TYPE: {
				RootDirectoryType rootDirectoryType = (RootDirectoryType)theEObject;
				T result = caseRootDirectoryType(rootDirectoryType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Bld Inf Files Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Bld Inf Files Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBldInfFilesType(BldInfFilesType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Bld Inf File Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Bld Inf File Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBldInfFileType(BldInfFileType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Bld Inf Import Data Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Bld Inf Import Data Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBldInfImportDataType(BldInfImportDataType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Build Config Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Build Config Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBuildConfigType(BuildConfigType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Build Configurations Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Build Configurations Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBuildConfigurationsType(BuildConfigurationsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Document Root</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDocumentRoot(DocumentRoot object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Inf Components Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Inf Components Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInfComponentsType(InfComponentsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Inf Component Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Inf Component Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInfComponentType(InfComponentType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mak Make Refs Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mak Make Refs Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMakMakeRefsType(MakMakeRefsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mak Make Ref Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mak Make Ref Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMakMakeRefType(MakMakeRefType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Root Directory Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Root Directory Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRootDirectoryType(RootDirectoryType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public T defaultCase(EObject object) {
		return null;
	}

} //InfImportTestDataSwitch
