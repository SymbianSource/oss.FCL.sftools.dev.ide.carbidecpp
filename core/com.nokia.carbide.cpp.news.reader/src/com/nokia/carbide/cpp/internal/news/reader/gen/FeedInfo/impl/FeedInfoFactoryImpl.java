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

package com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl;

import com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class FeedInfoFactoryImpl extends EFactoryImpl implements FeedInfoFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FeedInfoFactory init() {
		try {
			FeedInfoFactory theFeedInfoFactory = (FeedInfoFactory)EPackage.Registry.INSTANCE.getEFactory("platform:/resource/com.nokia.carbide.cpp.news.reader/schema/FeedInfo.xsd"); 
			if (theFeedInfoFactory != null) {
				return theFeedInfoFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new FeedInfoFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeedInfoFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case FeedInfoPackage.DOCUMENT_ROOT: return createDocumentRoot();
			case FeedInfoPackage.FEED_INFO_TYPE: return createFeedInfoType();
			case FeedInfoPackage.FEEDS_TYPE: return createFeedsType();
			case FeedInfoPackage.FEED_TYPE: return createFeedType();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocumentRoot createDocumentRoot() {
		DocumentRootImpl documentRoot = new DocumentRootImpl();
		return documentRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeedInfoType createFeedInfoType() {
		FeedInfoTypeImpl feedInfoType = new FeedInfoTypeImpl();
		return feedInfoType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeedsType createFeedsType() {
		FeedsTypeImpl feedsType = new FeedsTypeImpl();
		return feedsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeedType createFeedType() {
		FeedTypeImpl feedType = new FeedTypeImpl();
		return feedType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeedInfoPackage getFeedInfoPackage() {
		return (FeedInfoPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static FeedInfoPackage getPackage() {
		return FeedInfoPackage.eINSTANCE;
	}

} //FeedInfoFactoryImpl
