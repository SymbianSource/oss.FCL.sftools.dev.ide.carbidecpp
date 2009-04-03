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

package com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedCacheFactory
 * @model kind="package"
 *        extendedMetaData="qualified='false'"
 * @generated
 */
public interface FeedCachePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "FeedCache";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/resource/com.nokia.carbide.cpp.news.reader/schema/FeedCache.xsd";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "FeedCache";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	FeedCachePackage eINSTANCE = com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedCachePackageImpl.init();

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.DocumentRootImpl
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedCachePackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 0;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MIXED = 0;

	/**
	 * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

	/**
	 * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DESCRIPTION = 3;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ENTRIES = 4;

	/**
	 * The feature id for the '<em><b>Entry</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ENTRY = 5;

	/**
	 * The feature id for the '<em><b>Feed</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__FEED = 6;

	/**
	 * The feature id for the '<em><b>Feed Cache</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__FEED_CACHE = 7;

	/**
	 * The feature id for the '<em><b>Feeds</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__FEEDS = 8;

	/**
	 * The feature id for the '<em><b>Link</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__LINK = 9;

	/**
	 * The feature id for the '<em><b>Pub Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PUB_DATE = 10;

	/**
	 * The feature id for the '<em><b>Read</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__READ = 11;

	/**
	 * The feature id for the '<em><b>Subscribed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SUBSCRIBED = 12;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TITLE = 13;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TYPE = 14;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 15;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.EntriesTypeImpl <em>Entries Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.EntriesTypeImpl
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedCachePackageImpl#getEntriesType()
	 * @generated
	 */
	int ENTRIES_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Entry</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRIES_TYPE__ENTRY = 0;

	/**
	 * The number of structural features of the '<em>Entries Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRIES_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.EntryTypeImpl <em>Entry Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.EntryTypeImpl
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedCachePackageImpl#getEntryType()
	 * @generated
	 */
	int ENTRY_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_TYPE__TITLE = 0;

	/**
	 * The feature id for the '<em><b>Link</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_TYPE__LINK = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_TYPE__DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Pub Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_TYPE__PUB_DATE = 3;

	/**
	 * The feature id for the '<em><b>Read</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_TYPE__READ = 4;

	/**
	 * The number of structural features of the '<em>Entry Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_TYPE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedCacheTypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedCacheTypeImpl
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedCachePackageImpl#getFeedCacheType()
	 * @generated
	 */
	int FEED_CACHE_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Feeds</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEED_CACHE_TYPE__FEEDS = 0;

	/**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEED_CACHE_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedsTypeImpl <em>Feeds Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedsTypeImpl
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedCachePackageImpl#getFeedsType()
	 * @generated
	 */
	int FEEDS_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Feed</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEEDS_TYPE__FEED = 0;

	/**
	 * The number of structural features of the '<em>Feeds Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEEDS_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedTypeImpl <em>Feed Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedTypeImpl
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedCachePackageImpl#getFeedType()
	 * @generated
	 */
	int FEED_TYPE = 5;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEED_TYPE__TITLE = 0;

	/**
	 * The feature id for the '<em><b>Link</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEED_TYPE__LINK = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEED_TYPE__DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Pub Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEED_TYPE__PUB_DATE = 3;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEED_TYPE__TYPE = 4;

	/**
	 * The feature id for the '<em><b>Subscribed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEED_TYPE__SUBSCRIBED = 5;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEED_TYPE__ENTRIES = 6;

	/**
	 * The number of structural features of the '<em>Feed Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEED_TYPE_FEATURE_COUNT = 7;


	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#getDescription()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Description();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#getEntries <em>Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Entries</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#getEntries()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Entries();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#getEntry <em>Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Entry</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#getEntry()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Entry();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#getFeed <em>Feed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Feed</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#getFeed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Feed();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#getFeedCache <em>Feed Cache</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Feed Cache</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#getFeedCache()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_FeedCache();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#getFeeds <em>Feeds</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Feeds</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#getFeeds()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Feeds();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#getLink <em>Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Link</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#getLink()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Link();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#getPubDate <em>Pub Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pub Date</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#getPubDate()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_PubDate();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#isRead <em>Read</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Read</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#isRead()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Read();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#isSubscribed <em>Subscribed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Subscribed</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#isSubscribed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Subscribed();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#getTitle()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Title();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot#getType()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Type();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.EntriesType <em>Entries Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entries Type</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.EntriesType
	 * @generated
	 */
	EClass getEntriesType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.EntriesType#getEntry <em>Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entry</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.EntriesType#getEntry()
	 * @see #getEntriesType()
	 * @generated
	 */
	EReference getEntriesType_Entry();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.EntryType <em>Entry Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entry Type</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.EntryType
	 * @generated
	 */
	EClass getEntryType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.EntryType#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.EntryType#getTitle()
	 * @see #getEntryType()
	 * @generated
	 */
	EAttribute getEntryType_Title();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.EntryType#getLink <em>Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Link</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.EntryType#getLink()
	 * @see #getEntryType()
	 * @generated
	 */
	EAttribute getEntryType_Link();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.EntryType#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.EntryType#getDescription()
	 * @see #getEntryType()
	 * @generated
	 */
	EAttribute getEntryType_Description();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.EntryType#getPubDate <em>Pub Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pub Date</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.EntryType#getPubDate()
	 * @see #getEntryType()
	 * @generated
	 */
	EAttribute getEntryType_PubDate();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.EntryType#isRead <em>Read</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Read</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.EntryType#isRead()
	 * @see #getEntryType()
	 * @generated
	 */
	EAttribute getEntryType_Read();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedCacheType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedCacheType
	 * @generated
	 */
	EClass getFeedCacheType();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedCacheType#getFeeds <em>Feeds</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Feeds</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedCacheType#getFeeds()
	 * @see #getFeedCacheType()
	 * @generated
	 */
	EReference getFeedCacheType_Feeds();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedsType <em>Feeds Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feeds Type</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedsType
	 * @generated
	 */
	EClass getFeedsType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedsType#getFeed <em>Feed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Feed</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedsType#getFeed()
	 * @see #getFeedsType()
	 * @generated
	 */
	EReference getFeedsType_Feed();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType <em>Feed Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feed Type</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType
	 * @generated
	 */
	EClass getFeedType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#getTitle()
	 * @see #getFeedType()
	 * @generated
	 */
	EAttribute getFeedType_Title();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#getLink <em>Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Link</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#getLink()
	 * @see #getFeedType()
	 * @generated
	 */
	EAttribute getFeedType_Link();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#getDescription()
	 * @see #getFeedType()
	 * @generated
	 */
	EAttribute getFeedType_Description();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#getPubDate <em>Pub Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pub Date</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#getPubDate()
	 * @see #getFeedType()
	 * @generated
	 */
	EAttribute getFeedType_PubDate();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#getType()
	 * @see #getFeedType()
	 * @generated
	 */
	EAttribute getFeedType_Type();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#isSubscribed <em>Subscribed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Subscribed</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#isSubscribed()
	 * @see #getFeedType()
	 * @generated
	 */
	EAttribute getFeedType_Subscribed();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#getEntries <em>Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Entries</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#getEntries()
	 * @see #getFeedType()
	 * @generated
	 */
	EReference getFeedType_Entries();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	FeedCacheFactory getFeedCacheFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.DocumentRootImpl
		 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedCachePackageImpl#getDocumentRoot()
		 * @generated
		 */
		EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

		/**
		 * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

		/**
		 * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getDocumentRoot_XMLNSPrefixMap();

		/**
		 * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getDocumentRoot_XSISchemaLocation();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__DESCRIPTION = eINSTANCE.getDocumentRoot_Description();

		/**
		 * The meta object literal for the '<em><b>Entries</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ENTRIES = eINSTANCE.getDocumentRoot_Entries();

		/**
		 * The meta object literal for the '<em><b>Entry</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ENTRY = eINSTANCE.getDocumentRoot_Entry();

		/**
		 * The meta object literal for the '<em><b>Feed</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__FEED = eINSTANCE.getDocumentRoot_Feed();

		/**
		 * The meta object literal for the '<em><b>Feed Cache</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__FEED_CACHE = eINSTANCE.getDocumentRoot_FeedCache();

		/**
		 * The meta object literal for the '<em><b>Feeds</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__FEEDS = eINSTANCE.getDocumentRoot_Feeds();

		/**
		 * The meta object literal for the '<em><b>Link</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__LINK = eINSTANCE.getDocumentRoot_Link();

		/**
		 * The meta object literal for the '<em><b>Pub Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__PUB_DATE = eINSTANCE.getDocumentRoot_PubDate();

		/**
		 * The meta object literal for the '<em><b>Read</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__READ = eINSTANCE.getDocumentRoot_Read();

		/**
		 * The meta object literal for the '<em><b>Subscribed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__SUBSCRIBED = eINSTANCE.getDocumentRoot_Subscribed();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__TITLE = eINSTANCE.getDocumentRoot_Title();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__TYPE = eINSTANCE.getDocumentRoot_Type();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.EntriesTypeImpl <em>Entries Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.EntriesTypeImpl
		 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedCachePackageImpl#getEntriesType()
		 * @generated
		 */
		EClass ENTRIES_TYPE = eINSTANCE.getEntriesType();

		/**
		 * The meta object literal for the '<em><b>Entry</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTRIES_TYPE__ENTRY = eINSTANCE.getEntriesType_Entry();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.EntryTypeImpl <em>Entry Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.EntryTypeImpl
		 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedCachePackageImpl#getEntryType()
		 * @generated
		 */
		EClass ENTRY_TYPE = eINSTANCE.getEntryType();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTRY_TYPE__TITLE = eINSTANCE.getEntryType_Title();

		/**
		 * The meta object literal for the '<em><b>Link</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTRY_TYPE__LINK = eINSTANCE.getEntryType_Link();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTRY_TYPE__DESCRIPTION = eINSTANCE.getEntryType_Description();

		/**
		 * The meta object literal for the '<em><b>Pub Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTRY_TYPE__PUB_DATE = eINSTANCE.getEntryType_PubDate();

		/**
		 * The meta object literal for the '<em><b>Read</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTRY_TYPE__READ = eINSTANCE.getEntryType_Read();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedCacheTypeImpl <em>Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedCacheTypeImpl
		 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedCachePackageImpl#getFeedCacheType()
		 * @generated
		 */
		EClass FEED_CACHE_TYPE = eINSTANCE.getFeedCacheType();

		/**
		 * The meta object literal for the '<em><b>Feeds</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEED_CACHE_TYPE__FEEDS = eINSTANCE.getFeedCacheType_Feeds();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedsTypeImpl <em>Feeds Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedsTypeImpl
		 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedCachePackageImpl#getFeedsType()
		 * @generated
		 */
		EClass FEEDS_TYPE = eINSTANCE.getFeedsType();

		/**
		 * The meta object literal for the '<em><b>Feed</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEEDS_TYPE__FEED = eINSTANCE.getFeedsType_Feed();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedTypeImpl <em>Feed Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedTypeImpl
		 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedCachePackageImpl#getFeedType()
		 * @generated
		 */
		EClass FEED_TYPE = eINSTANCE.getFeedType();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEED_TYPE__TITLE = eINSTANCE.getFeedType_Title();

		/**
		 * The meta object literal for the '<em><b>Link</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEED_TYPE__LINK = eINSTANCE.getFeedType_Link();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEED_TYPE__DESCRIPTION = eINSTANCE.getFeedType_Description();

		/**
		 * The meta object literal for the '<em><b>Pub Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEED_TYPE__PUB_DATE = eINSTANCE.getFeedType_PubDate();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEED_TYPE__TYPE = eINSTANCE.getFeedType_Type();

		/**
		 * The meta object literal for the '<em><b>Subscribed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEED_TYPE__SUBSCRIBED = eINSTANCE.getFeedType_Subscribed();

		/**
		 * The meta object literal for the '<em><b>Entries</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEED_TYPE__ENTRIES = eINSTANCE.getFeedType_Entries();

	}

} //FeedCachePackage
