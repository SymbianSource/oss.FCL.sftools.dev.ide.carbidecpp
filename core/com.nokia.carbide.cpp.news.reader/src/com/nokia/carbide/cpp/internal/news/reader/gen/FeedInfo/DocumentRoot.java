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

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getFeed <em>Feed</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getFeedInfo <em>Feed Info</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getFeeds <em>Feeds</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getSubscribed <em>Subscribed</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getType <em>Type</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getUrl <em>Url</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedInfoPackage#getDocumentRoot()
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface DocumentRoot extends EObject {
	/**
	 * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mixed</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mixed</em>' attribute list.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedInfoPackage#getDocumentRoot_Mixed()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='elementWildcard' name=':mixed'"
	 * @generated
	 */
	FeatureMap getMixed();

	/**
	 * Returns the value of the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XMLNS Prefix Map</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XMLNS Prefix Map</em>' map.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedInfoPackage#getDocumentRoot_XMLNSPrefixMap()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
	 *        extendedMetaData="kind='attribute' name='xmlns:prefix'"
	 * @generated
	 */
	EMap<String, String> getXMLNSPrefixMap();

	/**
	 * Returns the value of the '<em><b>XSI Schema Location</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XSI Schema Location</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XSI Schema Location</em>' map.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedInfoPackage#getDocumentRoot_XSISchemaLocation()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
	 *        extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
	 * @generated
	 */
	EMap<String, String> getXSISchemaLocation();

	/**
	 * Returns the value of the '<em><b>Feed</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Feed</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feed</em>' containment reference.
	 * @see #setFeed(FeedType)
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedInfoPackage#getDocumentRoot_Feed()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='feed' namespace='##targetNamespace'"
	 * @generated
	 */
	FeedType getFeed();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getFeed <em>Feed</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feed</em>' containment reference.
	 * @see #getFeed()
	 * @generated
	 */
	void setFeed(FeedType value);

	/**
	 * Returns the value of the '<em><b>Feed Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Feed Info</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feed Info</em>' containment reference.
	 * @see #setFeedInfo(FeedInfoType)
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedInfoPackage#getDocumentRoot_FeedInfo()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='feedInfo' namespace='##targetNamespace'"
	 * @generated
	 */
	FeedInfoType getFeedInfo();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getFeedInfo <em>Feed Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feed Info</em>' containment reference.
	 * @see #getFeedInfo()
	 * @generated
	 */
	void setFeedInfo(FeedInfoType value);

	/**
	 * Returns the value of the '<em><b>Feeds</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Feeds</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feeds</em>' containment reference.
	 * @see #setFeeds(FeedsType)
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedInfoPackage#getDocumentRoot_Feeds()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='feeds' namespace='##targetNamespace'"
	 * @generated
	 */
	FeedsType getFeeds();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getFeeds <em>Feeds</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feeds</em>' containment reference.
	 * @see #getFeeds()
	 * @generated
	 */
	void setFeeds(FeedsType value);

	/**
	 * Returns the value of the '<em><b>Subscribed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subscribed</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subscribed</em>' attribute.
	 * @see #setSubscribed(String)
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedInfoPackage#getDocumentRoot_Subscribed()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='subscribed' namespace='##targetNamespace'"
	 * @generated
	 */
	String getSubscribed();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getSubscribed <em>Subscribed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subscribed</em>' attribute.
	 * @see #getSubscribed()
	 * @generated
	 */
	void setSubscribed(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedInfoPackage#getDocumentRoot_Type()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='type' namespace='##targetNamespace'"
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Url</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Url</em>' attribute.
	 * @see #setUrl(String)
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedInfoPackage#getDocumentRoot_Url()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='url' namespace='##targetNamespace'"
	 * @generated
	 */
	String getUrl();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot#getUrl <em>Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Url</em>' attribute.
	 * @see #getUrl()
	 * @generated
	 */
	void setUrl(String value);

} // DocumentRoot
