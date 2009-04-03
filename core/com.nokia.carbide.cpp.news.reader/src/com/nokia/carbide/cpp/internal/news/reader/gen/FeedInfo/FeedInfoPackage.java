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

package com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo;

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
 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedInfoFactory
 * @model kind="package"
 *        extendedMetaData="qualified='false'"
 * @generated
 */
public interface FeedInfoPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "FeedInfo";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/resource/com.nokia.carbide.cpp.news.reader/schema/FeedInfo.xsd";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "FeedInfo";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	FeedInfoPackage eINSTANCE = com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.FeedInfoPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.DocumentRootImpl
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.FeedInfoPackageImpl#getDocumentRoot()
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
	 * The feature id for the '<em><b>Feed</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__FEED = 3;

	/**
	 * The feature id for the '<em><b>Feed Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__FEED_INFO = 4;

	/**
	 * The feature id for the '<em><b>Feeds</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__FEEDS = 5;

	/**
	 * The feature id for the '<em><b>Subscribed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SUBSCRIBED = 6;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TYPE = 7;

	/**
	 * The feature id for the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__URL = 8;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 9;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.FeedInfoTypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.FeedInfoTypeImpl
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.FeedInfoPackageImpl#getFeedInfoType()
	 * @generated
	 */
	int FEED_INFO_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Feeds</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEED_INFO_TYPE__FEEDS = 0;

	/**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEED_INFO_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.FeedsTypeImpl <em>Feeds Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.FeedsTypeImpl
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.FeedInfoPackageImpl#getFeedsType()
	 * @generated
	 */
	int FEEDS_TYPE = 2;

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
	 * The meta object id for the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.FeedTypeImpl <em>Feed Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.FeedTypeImpl
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.FeedInfoPackageImpl#getFeedType()
	 * @generated
	 */
	int FEED_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEED_TYPE__URL = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEED_TYPE__TYPE = 1;

	/**
	 * The feature id for the '<em><b>Subscribed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEED_TYPE__SUBSCRIBED = 2;

	/**
	 * The number of structural features of the '<em>Feed Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEED_TYPE_FEATURE_COUNT = 3;


	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getFeed <em>Feed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Feed</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getFeed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Feed();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getFeedInfo <em>Feed Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Feed Info</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getFeedInfo()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_FeedInfo();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getFeeds <em>Feeds</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Feeds</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getFeeds()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Feeds();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getSubscribed <em>Subscribed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Subscribed</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getSubscribed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Subscribed();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getType()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Type();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getUrl <em>Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Url</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getUrl()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Url();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedInfoType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedInfoType
	 * @generated
	 */
	EClass getFeedInfoType();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedInfoType#getFeeds <em>Feeds</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Feeds</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedInfoType#getFeeds()
	 * @see #getFeedInfoType()
	 * @generated
	 */
	EReference getFeedInfoType_Feeds();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedsType <em>Feeds Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feeds Type</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedsType
	 * @generated
	 */
	EClass getFeedsType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedsType#getFeed <em>Feed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Feed</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedsType#getFeed()
	 * @see #getFeedsType()
	 * @generated
	 */
	EReference getFeedsType_Feed();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedType <em>Feed Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feed Type</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedType
	 * @generated
	 */
	EClass getFeedType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedType#getUrl <em>Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Url</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedType#getUrl()
	 * @see #getFeedType()
	 * @generated
	 */
	EAttribute getFeedType_Url();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedType#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedType#getType()
	 * @see #getFeedType()
	 * @generated
	 */
	EAttribute getFeedType_Type();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedType#getSubscribed <em>Subscribed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Subscribed</em>'.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedType#getSubscribed()
	 * @see #getFeedType()
	 * @generated
	 */
	EAttribute getFeedType_Subscribed();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	FeedInfoFactory getFeedInfoFactory();

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
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.DocumentRootImpl
		 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.FeedInfoPackageImpl#getDocumentRoot()
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
		 * The meta object literal for the '<em><b>Feed</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__FEED = eINSTANCE.getDocumentRoot_Feed();

		/**
		 * The meta object literal for the '<em><b>Feed Info</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__FEED_INFO = eINSTANCE.getDocumentRoot_FeedInfo();

		/**
		 * The meta object literal for the '<em><b>Feeds</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__FEEDS = eINSTANCE.getDocumentRoot_Feeds();

		/**
		 * The meta object literal for the '<em><b>Subscribed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__SUBSCRIBED = eINSTANCE.getDocumentRoot_Subscribed();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__TYPE = eINSTANCE.getDocumentRoot_Type();

		/**
		 * The meta object literal for the '<em><b>Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__URL = eINSTANCE.getDocumentRoot_Url();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.FeedInfoTypeImpl <em>Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.FeedInfoTypeImpl
		 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.FeedInfoPackageImpl#getFeedInfoType()
		 * @generated
		 */
		EClass FEED_INFO_TYPE = eINSTANCE.getFeedInfoType();

		/**
		 * The meta object literal for the '<em><b>Feeds</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEED_INFO_TYPE__FEEDS = eINSTANCE.getFeedInfoType_Feeds();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.FeedsTypeImpl <em>Feeds Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.FeedsTypeImpl
		 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.FeedInfoPackageImpl#getFeedsType()
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
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.FeedTypeImpl <em>Feed Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.FeedTypeImpl
		 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.FeedInfoPackageImpl#getFeedType()
		 * @generated
		 */
		EClass FEED_TYPE = eINSTANCE.getFeedType();

		/**
		 * The meta object literal for the '<em><b>Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEED_TYPE__URL = eINSTANCE.getFeedType_Url();

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

	}

} //FeedInfoPackage
