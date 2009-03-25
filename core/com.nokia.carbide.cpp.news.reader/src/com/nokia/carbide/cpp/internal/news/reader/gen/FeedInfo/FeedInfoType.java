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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedInfoType#getFeeds <em>Feeds</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedInfoPackage#getFeedInfoType()
 * @model extendedMetaData="name='feedInfo_._type' kind='elementOnly'"
 * @generated
 */
public interface FeedInfoType extends EObject {
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
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedInfoPackage#getFeedInfoType_Feeds()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='feeds' namespace='##targetNamespace'"
	 * @generated
	 */
	FeedsType getFeeds();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedInfoType#getFeeds <em>Feeds</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feeds</em>' containment reference.
	 * @see #getFeeds()
	 * @generated
	 */
	void setFeeds(FeedsType value);

} // FeedInfoType
