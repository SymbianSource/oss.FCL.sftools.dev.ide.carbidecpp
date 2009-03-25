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

import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.DocumentRoot;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.EntriesType;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.EntryType;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedCachePackage;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedCacheType;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedsType;

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
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.DocumentRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.DocumentRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.DocumentRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.DocumentRootImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.DocumentRootImpl#getEntries <em>Entries</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.DocumentRootImpl#getEntry <em>Entry</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.DocumentRootImpl#getFeed <em>Feed</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.DocumentRootImpl#getFeedCache <em>Feed Cache</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.DocumentRootImpl#getFeeds <em>Feeds</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.DocumentRootImpl#getLink <em>Link</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.DocumentRootImpl#getPubDate <em>Pub Date</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.DocumentRootImpl#isRead <em>Read</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.DocumentRootImpl#isSubscribed <em>Subscribed</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.DocumentRootImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.DocumentRootImpl#getType <em>Type</em>}</li>
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
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getLink() <em>Link</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLink()
	 * @generated
	 * @ordered
	 */
	protected static final String LINK_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getPubDate() <em>Pub Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPubDate()
	 * @generated
	 * @ordered
	 */
	protected static final String PUB_DATE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #isRead() <em>Read</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRead()
	 * @generated
	 * @ordered
	 */
	protected static final boolean READ_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isSubscribed() <em>Subscribed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSubscribed()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SUBSCRIBED_EDEFAULT = false;

	/**
	 * The default value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected static final String TITLE_EDEFAULT = null;

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
		return FeedCachePackage.Literals.DOCUMENT_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, FeedCachePackage.DOCUMENT_ROOT__MIXED);
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
			xMLNSPrefixMap = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, FeedCachePackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
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
			xSISchemaLocation = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, FeedCachePackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		}
		return xSISchemaLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return (String)getMixed().get(FeedCachePackage.Literals.DOCUMENT_ROOT__DESCRIPTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		((FeatureMap.Internal)getMixed()).set(FeedCachePackage.Literals.DOCUMENT_ROOT__DESCRIPTION, newDescription);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntriesType getEntries() {
		return (EntriesType)getMixed().get(FeedCachePackage.Literals.DOCUMENT_ROOT__ENTRIES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEntries(EntriesType newEntries, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(FeedCachePackage.Literals.DOCUMENT_ROOT__ENTRIES, newEntries, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntries(EntriesType newEntries) {
		((FeatureMap.Internal)getMixed()).set(FeedCachePackage.Literals.DOCUMENT_ROOT__ENTRIES, newEntries);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntryType getEntry() {
		return (EntryType)getMixed().get(FeedCachePackage.Literals.DOCUMENT_ROOT__ENTRY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEntry(EntryType newEntry, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(FeedCachePackage.Literals.DOCUMENT_ROOT__ENTRY, newEntry, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntry(EntryType newEntry) {
		((FeatureMap.Internal)getMixed()).set(FeedCachePackage.Literals.DOCUMENT_ROOT__ENTRY, newEntry);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeedType getFeed() {
		return (FeedType)getMixed().get(FeedCachePackage.Literals.DOCUMENT_ROOT__FEED, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFeed(FeedType newFeed, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(FeedCachePackage.Literals.DOCUMENT_ROOT__FEED, newFeed, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeed(FeedType newFeed) {
		((FeatureMap.Internal)getMixed()).set(FeedCachePackage.Literals.DOCUMENT_ROOT__FEED, newFeed);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeedCacheType getFeedCache() {
		return (FeedCacheType)getMixed().get(FeedCachePackage.Literals.DOCUMENT_ROOT__FEED_CACHE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFeedCache(FeedCacheType newFeedCache, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(FeedCachePackage.Literals.DOCUMENT_ROOT__FEED_CACHE, newFeedCache, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeedCache(FeedCacheType newFeedCache) {
		((FeatureMap.Internal)getMixed()).set(FeedCachePackage.Literals.DOCUMENT_ROOT__FEED_CACHE, newFeedCache);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeedsType getFeeds() {
		return (FeedsType)getMixed().get(FeedCachePackage.Literals.DOCUMENT_ROOT__FEEDS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFeeds(FeedsType newFeeds, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(FeedCachePackage.Literals.DOCUMENT_ROOT__FEEDS, newFeeds, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeeds(FeedsType newFeeds) {
		((FeatureMap.Internal)getMixed()).set(FeedCachePackage.Literals.DOCUMENT_ROOT__FEEDS, newFeeds);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLink() {
		return (String)getMixed().get(FeedCachePackage.Literals.DOCUMENT_ROOT__LINK, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLink(String newLink) {
		((FeatureMap.Internal)getMixed()).set(FeedCachePackage.Literals.DOCUMENT_ROOT__LINK, newLink);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPubDate() {
		return (String)getMixed().get(FeedCachePackage.Literals.DOCUMENT_ROOT__PUB_DATE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPubDate(String newPubDate) {
		((FeatureMap.Internal)getMixed()).set(FeedCachePackage.Literals.DOCUMENT_ROOT__PUB_DATE, newPubDate);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isRead() {
		return ((Boolean)getMixed().get(FeedCachePackage.Literals.DOCUMENT_ROOT__READ, true)).booleanValue();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRead(boolean newRead) {
		((FeatureMap.Internal)getMixed()).set(FeedCachePackage.Literals.DOCUMENT_ROOT__READ, new Boolean(newRead));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSubscribed() {
		return ((Boolean)getMixed().get(FeedCachePackage.Literals.DOCUMENT_ROOT__SUBSCRIBED, true)).booleanValue();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubscribed(boolean newSubscribed) {
		((FeatureMap.Internal)getMixed()).set(FeedCachePackage.Literals.DOCUMENT_ROOT__SUBSCRIBED, new Boolean(newSubscribed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTitle() {
		return (String)getMixed().get(FeedCachePackage.Literals.DOCUMENT_ROOT__TITLE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTitle(String newTitle) {
		((FeatureMap.Internal)getMixed()).set(FeedCachePackage.Literals.DOCUMENT_ROOT__TITLE, newTitle);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getType() {
		return (String)getMixed().get(FeedCachePackage.Literals.DOCUMENT_ROOT__TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(String newType) {
		((FeatureMap.Internal)getMixed()).set(FeedCachePackage.Literals.DOCUMENT_ROOT__TYPE, newType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FeedCachePackage.DOCUMENT_ROOT__MIXED:
				return ((InternalEList<?>)getMixed()).basicRemove(otherEnd, msgs);
			case FeedCachePackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return ((InternalEList<?>)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
			case FeedCachePackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return ((InternalEList<?>)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
			case FeedCachePackage.DOCUMENT_ROOT__ENTRIES:
				return basicSetEntries(null, msgs);
			case FeedCachePackage.DOCUMENT_ROOT__ENTRY:
				return basicSetEntry(null, msgs);
			case FeedCachePackage.DOCUMENT_ROOT__FEED:
				return basicSetFeed(null, msgs);
			case FeedCachePackage.DOCUMENT_ROOT__FEED_CACHE:
				return basicSetFeedCache(null, msgs);
			case FeedCachePackage.DOCUMENT_ROOT__FEEDS:
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
			case FeedCachePackage.DOCUMENT_ROOT__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case FeedCachePackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				if (coreType) return getXMLNSPrefixMap();
				else return getXMLNSPrefixMap().map();
			case FeedCachePackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				if (coreType) return getXSISchemaLocation();
				else return getXSISchemaLocation().map();
			case FeedCachePackage.DOCUMENT_ROOT__DESCRIPTION:
				return getDescription();
			case FeedCachePackage.DOCUMENT_ROOT__ENTRIES:
				return getEntries();
			case FeedCachePackage.DOCUMENT_ROOT__ENTRY:
				return getEntry();
			case FeedCachePackage.DOCUMENT_ROOT__FEED:
				return getFeed();
			case FeedCachePackage.DOCUMENT_ROOT__FEED_CACHE:
				return getFeedCache();
			case FeedCachePackage.DOCUMENT_ROOT__FEEDS:
				return getFeeds();
			case FeedCachePackage.DOCUMENT_ROOT__LINK:
				return getLink();
			case FeedCachePackage.DOCUMENT_ROOT__PUB_DATE:
				return getPubDate();
			case FeedCachePackage.DOCUMENT_ROOT__READ:
				return isRead() ? Boolean.TRUE : Boolean.FALSE;
			case FeedCachePackage.DOCUMENT_ROOT__SUBSCRIBED:
				return isSubscribed() ? Boolean.TRUE : Boolean.FALSE;
			case FeedCachePackage.DOCUMENT_ROOT__TITLE:
				return getTitle();
			case FeedCachePackage.DOCUMENT_ROOT__TYPE:
				return getType();
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
			case FeedCachePackage.DOCUMENT_ROOT__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case FeedCachePackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
				return;
			case FeedCachePackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
				return;
			case FeedCachePackage.DOCUMENT_ROOT__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case FeedCachePackage.DOCUMENT_ROOT__ENTRIES:
				setEntries((EntriesType)newValue);
				return;
			case FeedCachePackage.DOCUMENT_ROOT__ENTRY:
				setEntry((EntryType)newValue);
				return;
			case FeedCachePackage.DOCUMENT_ROOT__FEED:
				setFeed((FeedType)newValue);
				return;
			case FeedCachePackage.DOCUMENT_ROOT__FEED_CACHE:
				setFeedCache((FeedCacheType)newValue);
				return;
			case FeedCachePackage.DOCUMENT_ROOT__FEEDS:
				setFeeds((FeedsType)newValue);
				return;
			case FeedCachePackage.DOCUMENT_ROOT__LINK:
				setLink((String)newValue);
				return;
			case FeedCachePackage.DOCUMENT_ROOT__PUB_DATE:
				setPubDate((String)newValue);
				return;
			case FeedCachePackage.DOCUMENT_ROOT__READ:
				setRead(((Boolean)newValue).booleanValue());
				return;
			case FeedCachePackage.DOCUMENT_ROOT__SUBSCRIBED:
				setSubscribed(((Boolean)newValue).booleanValue());
				return;
			case FeedCachePackage.DOCUMENT_ROOT__TITLE:
				setTitle((String)newValue);
				return;
			case FeedCachePackage.DOCUMENT_ROOT__TYPE:
				setType((String)newValue);
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
			case FeedCachePackage.DOCUMENT_ROOT__MIXED:
				getMixed().clear();
				return;
			case FeedCachePackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				getXMLNSPrefixMap().clear();
				return;
			case FeedCachePackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				getXSISchemaLocation().clear();
				return;
			case FeedCachePackage.DOCUMENT_ROOT__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case FeedCachePackage.DOCUMENT_ROOT__ENTRIES:
				setEntries((EntriesType)null);
				return;
			case FeedCachePackage.DOCUMENT_ROOT__ENTRY:
				setEntry((EntryType)null);
				return;
			case FeedCachePackage.DOCUMENT_ROOT__FEED:
				setFeed((FeedType)null);
				return;
			case FeedCachePackage.DOCUMENT_ROOT__FEED_CACHE:
				setFeedCache((FeedCacheType)null);
				return;
			case FeedCachePackage.DOCUMENT_ROOT__FEEDS:
				setFeeds((FeedsType)null);
				return;
			case FeedCachePackage.DOCUMENT_ROOT__LINK:
				setLink(LINK_EDEFAULT);
				return;
			case FeedCachePackage.DOCUMENT_ROOT__PUB_DATE:
				setPubDate(PUB_DATE_EDEFAULT);
				return;
			case FeedCachePackage.DOCUMENT_ROOT__READ:
				setRead(READ_EDEFAULT);
				return;
			case FeedCachePackage.DOCUMENT_ROOT__SUBSCRIBED:
				setSubscribed(SUBSCRIBED_EDEFAULT);
				return;
			case FeedCachePackage.DOCUMENT_ROOT__TITLE:
				setTitle(TITLE_EDEFAULT);
				return;
			case FeedCachePackage.DOCUMENT_ROOT__TYPE:
				setType(TYPE_EDEFAULT);
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
			case FeedCachePackage.DOCUMENT_ROOT__MIXED:
				return mixed != null && !mixed.isEmpty();
			case FeedCachePackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
			case FeedCachePackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
			case FeedCachePackage.DOCUMENT_ROOT__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? getDescription() != null : !DESCRIPTION_EDEFAULT.equals(getDescription());
			case FeedCachePackage.DOCUMENT_ROOT__ENTRIES:
				return getEntries() != null;
			case FeedCachePackage.DOCUMENT_ROOT__ENTRY:
				return getEntry() != null;
			case FeedCachePackage.DOCUMENT_ROOT__FEED:
				return getFeed() != null;
			case FeedCachePackage.DOCUMENT_ROOT__FEED_CACHE:
				return getFeedCache() != null;
			case FeedCachePackage.DOCUMENT_ROOT__FEEDS:
				return getFeeds() != null;
			case FeedCachePackage.DOCUMENT_ROOT__LINK:
				return LINK_EDEFAULT == null ? getLink() != null : !LINK_EDEFAULT.equals(getLink());
			case FeedCachePackage.DOCUMENT_ROOT__PUB_DATE:
				return PUB_DATE_EDEFAULT == null ? getPubDate() != null : !PUB_DATE_EDEFAULT.equals(getPubDate());
			case FeedCachePackage.DOCUMENT_ROOT__READ:
				return isRead() != READ_EDEFAULT;
			case FeedCachePackage.DOCUMENT_ROOT__SUBSCRIBED:
				return isSubscribed() != SUBSCRIBED_EDEFAULT;
			case FeedCachePackage.DOCUMENT_ROOT__TITLE:
				return TITLE_EDEFAULT == null ? getTitle() != null : !TITLE_EDEFAULT.equals(getTitle());
			case FeedCachePackage.DOCUMENT_ROOT__TYPE:
				return TYPE_EDEFAULT == null ? getType() != null : !TYPE_EDEFAULT.equals(getType());
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
