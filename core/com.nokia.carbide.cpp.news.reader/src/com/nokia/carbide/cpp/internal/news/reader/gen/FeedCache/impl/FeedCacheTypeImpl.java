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

import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedCachePackage;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedCacheType;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedsType;

import org.eclipse.emf.common.notify.Notification;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedCacheTypeImpl#getFeeds <em>Feeds</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FeedCacheTypeImpl extends EObjectImpl implements FeedCacheType {
	/**
	 * The cached value of the '{@link #getFeeds() <em>Feeds</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeeds()
	 * @generated
	 * @ordered
	 */
	protected FeedsType feeds;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FeedCacheTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FeedCachePackage.Literals.FEED_CACHE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeedsType getFeeds() {
		return feeds;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFeeds(FeedsType newFeeds, NotificationChain msgs) {
		FeedsType oldFeeds = feeds;
		feeds = newFeeds;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FeedCachePackage.FEED_CACHE_TYPE__FEEDS, oldFeeds, newFeeds);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeeds(FeedsType newFeeds) {
		if (newFeeds != feeds) {
			NotificationChain msgs = null;
			if (feeds != null)
				msgs = ((InternalEObject)feeds).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FeedCachePackage.FEED_CACHE_TYPE__FEEDS, null, msgs);
			if (newFeeds != null)
				msgs = ((InternalEObject)newFeeds).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - FeedCachePackage.FEED_CACHE_TYPE__FEEDS, null, msgs);
			msgs = basicSetFeeds(newFeeds, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeedCachePackage.FEED_CACHE_TYPE__FEEDS, newFeeds, newFeeds));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FeedCachePackage.FEED_CACHE_TYPE__FEEDS:
				return basicSetFeeds(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FeedCachePackage.FEED_CACHE_TYPE__FEEDS:
				return getFeeds();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case FeedCachePackage.FEED_CACHE_TYPE__FEEDS:
				setFeeds((FeedsType)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case FeedCachePackage.FEED_CACHE_TYPE__FEEDS:
				setFeeds((FeedsType)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case FeedCachePackage.FEED_CACHE_TYPE__FEEDS:
				return feeds != null;
		}
		return super.eIsSet(featureID);
	}

} //FeedCacheTypeImpl
