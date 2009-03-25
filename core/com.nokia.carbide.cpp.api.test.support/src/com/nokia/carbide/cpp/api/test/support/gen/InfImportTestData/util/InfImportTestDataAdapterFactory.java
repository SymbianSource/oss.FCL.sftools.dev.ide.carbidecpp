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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage
 * @generated
 */
public class InfImportTestDataAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static InfImportTestDataPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfImportTestDataAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = InfImportTestDataPackage.eINSTANCE;
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
	@Override
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
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InfImportTestDataSwitch<Adapter> modelSwitch =
		new InfImportTestDataSwitch<Adapter>() {
			@Override
			public Adapter caseBldInfFilesType(BldInfFilesType object) {
				return createBldInfFilesTypeAdapter();
			}
			@Override
			public Adapter caseBldInfFileType(BldInfFileType object) {
				return createBldInfFileTypeAdapter();
			}
			@Override
			public Adapter caseBldInfImportDataType(BldInfImportDataType object) {
				return createBldInfImportDataTypeAdapter();
			}
			@Override
			public Adapter caseBuildConfigType(BuildConfigType object) {
				return createBuildConfigTypeAdapter();
			}
			@Override
			public Adapter caseBuildConfigurationsType(BuildConfigurationsType object) {
				return createBuildConfigurationsTypeAdapter();
			}
			@Override
			public Adapter caseDocumentRoot(DocumentRoot object) {
				return createDocumentRootAdapter();
			}
			@Override
			public Adapter caseInfComponentsType(InfComponentsType object) {
				return createInfComponentsTypeAdapter();
			}
			@Override
			public Adapter caseInfComponentType(InfComponentType object) {
				return createInfComponentTypeAdapter();
			}
			@Override
			public Adapter caseMakMakeRefsType(MakMakeRefsType object) {
				return createMakMakeRefsTypeAdapter();
			}
			@Override
			public Adapter caseMakMakeRefType(MakMakeRefType object) {
				return createMakMakeRefTypeAdapter();
			}
			@Override
			public Adapter caseRootDirectoryType(RootDirectoryType object) {
				return createRootDirectoryTypeAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
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
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFilesType <em>Bld Inf Files Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFilesType
	 * @generated
	 */
	public Adapter createBldInfFilesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType <em>Bld Inf File Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType
	 * @generated
	 */
	public Adapter createBldInfFileTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfImportDataType <em>Bld Inf Import Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfImportDataType
	 * @generated
	 */
	public Adapter createBldInfImportDataTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BuildConfigType <em>Build Config Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BuildConfigType
	 * @generated
	 */
	public Adapter createBuildConfigTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BuildConfigurationsType <em>Build Configurations Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BuildConfigurationsType
	 * @generated
	 */
	public Adapter createBuildConfigurationsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot
	 * @generated
	 */
	public Adapter createDocumentRootAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfComponentsType <em>Inf Components Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfComponentsType
	 * @generated
	 */
	public Adapter createInfComponentsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfComponentType <em>Inf Component Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfComponentType
	 * @generated
	 */
	public Adapter createInfComponentTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.MakMakeRefsType <em>Mak Make Refs Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.MakMakeRefsType
	 * @generated
	 */
	public Adapter createMakMakeRefsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.MakMakeRefType <em>Mak Make Ref Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.MakMakeRefType
	 * @generated
	 */
	public Adapter createMakMakeRefTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.RootDirectoryType <em>Root Directory Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.RootDirectoryType
	 * @generated
	 */
	public Adapter createRootDirectoryTypeAdapter() {
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

} //InfImportTestDataAdapterFactory
