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

package com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl;

import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.*;

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
public class FeedCacheFactoryImpl extends EFactoryImpl implements FeedCacheFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FeedCacheFactory init() {
		try {
			FeedCacheFactory theFeedCacheFactory = (FeedCacheFactory)EPackage.Registry.INSTANCE.getEFactory("platform:/resource/com.nokia.carbide.cpp.news.reader/schema/FeedCache.xsd"); 
			if (theFeedCacheFactory != null) {
				return theFeedCacheFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new FeedCacheFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeedCacheFactoryImpl() {
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
			case FeedCachePackage.DOCUMENT_ROOT: return createDocumentRoot();
			case FeedCachePackage.ENTRIES_TYPE: return createEntriesType();
			case FeedCachePackage.ENTRY_TYPE: return createEntryType();
			case FeedCachePackage.FEED_CACHE_TYPE: return createFeedCacheType();
			case FeedCachePackage.FEEDS_TYPE: return createFeedsType();
			case FeedCachePackage.FEED_TYPE: return createFeedType();
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
	public EntriesType createEntriesType() {
		EntriesTypeImpl entriesType = new EntriesTypeImpl();
		return entriesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntryType createEntryType() {
		EntryTypeImpl entryType = new EntryTypeImpl();
		return entryType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeedCacheType createFeedCacheType() {
		FeedCacheTypeImpl feedCacheType = new FeedCacheTypeImpl();
		return feedCacheType;
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
	public FeedCachePackage getFeedCachePackage() {
		return (FeedCachePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static FeedCachePackage getPackage() {
		return FeedCachePackage.eINSTANCE;
	}

} //FeedCacheFactoryImpl
