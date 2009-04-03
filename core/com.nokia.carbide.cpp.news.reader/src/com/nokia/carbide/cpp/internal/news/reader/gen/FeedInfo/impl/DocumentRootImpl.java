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

import com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.DocumentRoot;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedInfoPackage;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedInfoType;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedType;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedsType;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.DocumentRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.DocumentRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.DocumentRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.DocumentRootImpl#getFeed <em>Feed</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.DocumentRootImpl#getFeedInfo <em>Feed Info</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.DocumentRootImpl#getFeeds <em>Feeds</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.DocumentRootImpl#getSubscribed <em>Subscribed</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.DocumentRootImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.DocumentRootImpl#getUrl <em>Url</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DocumentRootImpl extends EObjectImpl implements DocumentRoot {
	/**
	 * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMixed()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap mixed;

	/**
	 * The cached value of the '{@link #getXMLNSPrefixMap() <em>XMLNS Prefix Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXMLNSPrefixMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> xMLNSPrefixMap;

	/**
	 * The cached value of the '{@link #getXSISchemaLocation() <em>XSI Schema Location</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXSISchemaLocation()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> xSISchemaLocation;

	/**
	 * The default value of the '{@link #getSubscribed() <em>Subscribed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubscribed()
	 * @generated
	 * @ordered
	 */
	protected static final String SUBSCRIBED_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getUrl() <em>Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUrl()
	 * @generated
	 * @ordered
	 */
	protected static final String URL_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DocumentRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FeedInfoPackage.Literals.DOCUMENT_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, FeedInfoPackage.DOCUMENT_ROOT__MIXED);
		}
		return mixed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getXMLNSPrefixMap() {
		if (xMLNSPrefixMap == null) {
			xMLNSPrefixMap = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, FeedInfoPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		}
		return xMLNSPrefixMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getXSISchemaLocation() {
		if (xSISchemaLocation == null) {
			xSISchemaLocation = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, FeedInfoPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		}
		return xSISchemaLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeedType getFeed() {
		return (FeedType)getMixed().get(FeedInfoPackage.Literals.DOCUMENT_ROOT__FEED, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFeed(FeedType newFeed, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(FeedInfoPackage.Literals.DOCUMENT_ROOT__FEED, newFeed, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeed(FeedType newFeed) {
		((FeatureMap.Internal)getMixed()).set(FeedInfoPackage.Literals.DOCUMENT_ROOT__FEED, newFeed);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeedInfoType getFeedInfo() {
		return (FeedInfoType)getMixed().get(FeedInfoPackage.Literals.DOCUMENT_ROOT__FEED_INFO, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFeedInfo(FeedInfoType newFeedInfo, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(FeedInfoPackage.Literals.DOCUMENT_ROOT__FEED_INFO, newFeedInfo, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeedInfo(FeedInfoType newFeedInfo) {
		((FeatureMap.Internal)getMixed()).set(FeedInfoPackage.Literals.DOCUMENT_ROOT__FEED_INFO, newFeedInfo);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeedsType getFeeds() {
		return (FeedsType)getMixed().get(FeedInfoPackage.Literals.DOCUMENT_ROOT__FEEDS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFeeds(FeedsType newFeeds, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(FeedInfoPackage.Literals.DOCUMENT_ROOT__FEEDS, newFeeds, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeeds(FeedsType newFeeds) {
		((FeatureMap.Internal)getMixed()).set(FeedInfoPackage.Literals.DOCUMENT_ROOT__FEEDS, newFeeds);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSubscribed() {
		return (String)getMixed().get(FeedInfoPackage.Literals.DOCUMENT_ROOT__SUBSCRIBED, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubscribed(String newSubscribed) {
		((FeatureMap.Internal)getMixed()).set(FeedInfoPackage.Literals.DOCUMENT_ROOT__SUBSCRIBED, newSubscribed);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getType() {
		return (String)getMixed().get(FeedInfoPackage.Literals.DOCUMENT_ROOT__TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(String newType) {
		((FeatureMap.Internal)getMixed()).set(FeedInfoPackage.Literals.DOCUMENT_ROOT__TYPE, newType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUrl() {
		return (String)getMixed().get(FeedInfoPackage.Literals.DOCUMENT_ROOT__URL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUrl(String newUrl) {
		((FeatureMap.Internal)getMixed()).set(FeedInfoPackage.Literals.DOCUMENT_ROOT__URL, newUrl);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FeedInfoPackage.DOCUMENT_ROOT__MIXED:
				return ((InternalEList<?>)getMixed()).basicRemove(otherEnd, msgs);
			case FeedInfoPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return ((InternalEList<?>)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
			case FeedInfoPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return ((InternalEList<?>)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
			case FeedInfoPackage.DOCUMENT_ROOT__FEED:
				return basicSetFeed(null, msgs);
			case FeedInfoPackage.DOCUMENT_ROOT__FEED_INFO:
				return basicSetFeedInfo(null, msgs);
			case FeedInfoPackage.DOCUMENT_ROOT__FEEDS:
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
			case FeedInfoPackage.DOCUMENT_ROOT__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case FeedInfoPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				if (coreType) return getXMLNSPrefixMap();
				else return getXMLNSPrefixMap().map();
			case FeedInfoPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				if (coreType) return getXSISchemaLocation();
				else return getXSISchemaLocation().map();
			case FeedInfoPackage.DOCUMENT_ROOT__FEED:
				return getFeed();
			case FeedInfoPackage.DOCUMENT_ROOT__FEED_INFO:
				return getFeedInfo();
			case FeedInfoPackage.DOCUMENT_ROOT__FEEDS:
				return getFeeds();
			case FeedInfoPackage.DOCUMENT_ROOT__SUBSCRIBED:
				return getSubscribed();
			case FeedInfoPackage.DOCUMENT_ROOT__TYPE:
				return getType();
			case FeedInfoPackage.DOCUMENT_ROOT__URL:
				return getUrl();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case FeedInfoPackage.DOCUMENT_ROOT__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case FeedInfoPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
				return;
			case FeedInfoPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
				return;
			case FeedInfoPackage.DOCUMENT_ROOT__FEED:
				setFeed((FeedType)newValue);
				return;
			case FeedInfoPackage.DOCUMENT_ROOT__FEED_INFO:
				setFeedInfo((FeedInfoType)newValue);
				return;
			case FeedInfoPackage.DOCUMENT_ROOT__FEEDS:
				setFeeds((FeedsType)newValue);
				return;
			case FeedInfoPackage.DOCUMENT_ROOT__SUBSCRIBED:
				setSubscribed((String)newValue);
				return;
			case FeedInfoPackage.DOCUMENT_ROOT__TYPE:
				setType((String)newValue);
				return;
			case FeedInfoPackage.DOCUMENT_ROOT__URL:
				setUrl((String)newValue);
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
			case FeedInfoPackage.DOCUMENT_ROOT__MIXED:
				getMixed().clear();
				return;
			case FeedInfoPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				getXMLNSPrefixMap().clear();
				return;
			case FeedInfoPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				getXSISchemaLocation().clear();
				return;
			case FeedInfoPackage.DOCUMENT_ROOT__FEED:
				setFeed((FeedType)null);
				return;
			case FeedInfoPackage.DOCUMENT_ROOT__FEED_INFO:
				setFeedInfo((FeedInfoType)null);
				return;
			case FeedInfoPackage.DOCUMENT_ROOT__FEEDS:
				setFeeds((FeedsType)null);
				return;
			case FeedInfoPackage.DOCUMENT_ROOT__SUBSCRIBED:
				setSubscribed(SUBSCRIBED_EDEFAULT);
				return;
			case FeedInfoPackage.DOCUMENT_ROOT__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case FeedInfoPackage.DOCUMENT_ROOT__URL:
				setUrl(URL_EDEFAULT);
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
			case FeedInfoPackage.DOCUMENT_ROOT__MIXED:
				return mixed != null && !mixed.isEmpty();
			case FeedInfoPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
			case FeedInfoPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
			case FeedInfoPackage.DOCUMENT_ROOT__FEED:
				return getFeed() != null;
			case FeedInfoPackage.DOCUMENT_ROOT__FEED_INFO:
				return getFeedInfo() != null;
			case FeedInfoPackage.DOCUMENT_ROOT__FEEDS:
				return getFeeds() != null;
			case FeedInfoPackage.DOCUMENT_ROOT__SUBSCRIBED:
				return SUBSCRIBED_EDEFAULT == null ? getSubscribed() != null : !SUBSCRIBED_EDEFAULT.equals(getSubscribed());
			case FeedInfoPackage.DOCUMENT_ROOT__TYPE:
				return TYPE_EDEFAULT == null ? getType() != null : !TYPE_EDEFAULT.equals(getType());
			case FeedInfoPackage.DOCUMENT_ROOT__URL:
				return URL_EDEFAULT == null ? getUrl() != null : !URL_EDEFAULT.equals(getUrl());
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (mixed: ");
		result.append(mixed);
		result.append(')');
		return result.toString();
	}

} //DocumentRootImpl
