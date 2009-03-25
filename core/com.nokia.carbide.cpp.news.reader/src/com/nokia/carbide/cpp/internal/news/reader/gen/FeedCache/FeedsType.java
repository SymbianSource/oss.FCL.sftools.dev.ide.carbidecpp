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
 * A representation of the model object '<em><b>Feeds Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedsType#getFeed <em>Feed</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedCachePackage#getFeedsType()
 * @model extendedMetaData="name='feeds_._type' kind='elementOnly'"
 * @generated
 */
public interface FeedsType extends EObject {
	/**
	 * Returns the value of the '<em><b>Feed</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Feed</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feed</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedCachePackage#getFeedsType_Feed()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='feed' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<FeedType> getFeed();

} // FeedsType
