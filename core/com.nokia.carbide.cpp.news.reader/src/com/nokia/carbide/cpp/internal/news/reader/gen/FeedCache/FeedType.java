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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feed Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#getTitle <em>Title</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#getLink <em>Link</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#getDescription <em>Description</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#getPubDate <em>Pub Date</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#getType <em>Type</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#isSubscribed <em>Subscribed</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#getEntries <em>Entries</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedCachePackage#getFeedType()
 * @model extendedMetaData="name='feed_._type' kind='elementOnly'"
 * @generated
 */
public interface FeedType extends EObject {
	/**
	 * Returns the value of the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Title</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Title</em>' attribute.
	 * @see #setTitle(String)
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedCachePackage#getFeedType_Title()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='title' namespace='##targetNamespace'"
	 * @generated
	 */
	String getTitle();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#getTitle <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Title</em>' attribute.
	 * @see #getTitle()
	 * @generated
	 */
	void setTitle(String value);

	/**
	 * Returns the value of the '<em><b>Link</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Link</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Link</em>' attribute.
	 * @see #setLink(String)
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedCachePackage#getFeedType_Link()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='link' namespace='##targetNamespace'"
	 * @generated
	 */
	String getLink();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#getLink <em>Link</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Link</em>' attribute.
	 * @see #getLink()
	 * @generated
	 */
	void setLink(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedCachePackage#getFeedType_Description()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='description' namespace='##targetNamespace'"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Pub Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pub Date</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pub Date</em>' attribute.
	 * @see #setPubDate(String)
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedCachePackage#getFeedType_PubDate()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='pubDate' namespace='##targetNamespace'"
	 * @generated
	 */
	String getPubDate();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#getPubDate <em>Pub Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pub Date</em>' attribute.
	 * @see #getPubDate()
	 * @generated
	 */
	void setPubDate(String value);

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
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedCachePackage#getFeedType_Type()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='type' namespace='##targetNamespace'"
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Subscribed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subscribed</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subscribed</em>' attribute.
	 * @see #isSetSubscribed()
	 * @see #unsetSubscribed()
	 * @see #setSubscribed(boolean)
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedCachePackage#getFeedType_Subscribed()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 *        extendedMetaData="kind='element' name='subscribed' namespace='##targetNamespace'"
	 * @generated
	 */
	boolean isSubscribed();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#isSubscribed <em>Subscribed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subscribed</em>' attribute.
	 * @see #isSetSubscribed()
	 * @see #unsetSubscribed()
	 * @see #isSubscribed()
	 * @generated
	 */
	void setSubscribed(boolean value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#isSubscribed <em>Subscribed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetSubscribed()
	 * @see #isSubscribed()
	 * @see #setSubscribed(boolean)
	 * @generated
	 */
	void unsetSubscribed();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#isSubscribed <em>Subscribed</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Subscribed</em>' attribute is set.
	 * @see #unsetSubscribed()
	 * @see #isSubscribed()
	 * @see #setSubscribed(boolean)
	 * @generated
	 */
	boolean isSetSubscribed();

	/**
	 * Returns the value of the '<em><b>Entries</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entries</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entries</em>' containment reference.
	 * @see #setEntries(EntriesType)
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedCachePackage#getFeedType_Entries()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='entries' namespace='##targetNamespace'"
	 * @generated
	 */
	EntriesType getEntries();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType#getEntries <em>Entries</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entries</em>' containment reference.
	 * @see #getEntries()
	 * @generated
	 */
	void setEntries(EntriesType value);

} // FeedType
