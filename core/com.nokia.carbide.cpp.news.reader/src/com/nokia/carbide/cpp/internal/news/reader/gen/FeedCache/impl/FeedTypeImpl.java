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

import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.EntriesType;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.EntryType;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedCachePackage;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
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
 * An implementation of the model object '<em><b>Feed Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedTypeImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedTypeImpl#getLink <em>Link</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedTypeImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedTypeImpl#getPubDate <em>Pub Date</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedTypeImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedTypeImpl#isSubscribed <em>Subscribed</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.FeedTypeImpl#getEntries <em>Entries</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FeedTypeImpl extends EObjectImpl implements FeedType {
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
	 * The cached value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected String title = TITLE_EDEFAULT;

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
	 * The cached value of the '{@link #getLink() <em>Link</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLink()
	 * @generated
	 * @ordered
	 */
	protected String link = LINK_EDEFAULT;

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
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

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
	 * The cached value of the '{@link #getPubDate() <em>Pub Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPubDate()
	 * @generated
	 * @ordered
	 */
	protected String pubDate = PUB_DATE_EDEFAULT;

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
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected String type = TYPE_EDEFAULT;

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
	 * The cached value of the '{@link #isSubscribed() <em>Subscribed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSubscribed()
	 * @generated
	 * @ordered
	 */
	protected boolean subscribed = SUBSCRIBED_EDEFAULT;

	/**
	 * This is true if the Subscribed attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean subscribedESet;

	/**
	 * The cached value of the '{@link #getEntries() <em>Entries</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntries()
	 * @generated
	 * @ordered
	 */
	protected EntriesType entries;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FeedTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FeedCachePackage.Literals.FEED_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTitle(String newTitle) {
		String oldTitle = title;
		title = newTitle;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeedCachePackage.FEED_TYPE__TITLE, oldTitle, title));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLink() {
		return link;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLink(String newLink) {
		String oldLink = link;
		link = newLink;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeedCachePackage.FEED_TYPE__LINK, oldLink, link));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeedCachePackage.FEED_TYPE__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPubDate() {
		return pubDate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPubDate(String newPubDate) {
		String oldPubDate = pubDate;
		pubDate = newPubDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeedCachePackage.FEED_TYPE__PUB_DATE, oldPubDate, pubDate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(String newType) {
		String oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeedCachePackage.FEED_TYPE__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSubscribed() {
		return subscribed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubscribed(boolean newSubscribed) {
		boolean oldSubscribed = subscribed;
		subscribed = newSubscribed;
		boolean oldSubscribedESet = subscribedESet;
		subscribedESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeedCachePackage.FEED_TYPE__SUBSCRIBED, oldSubscribed, subscribed, !oldSubscribedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetSubscribed() {
		boolean oldSubscribed = subscribed;
		boolean oldSubscribedESet = subscribedESet;
		subscribed = SUBSCRIBED_EDEFAULT;
		subscribedESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, FeedCachePackage.FEED_TYPE__SUBSCRIBED, oldSubscribed, SUBSCRIBED_EDEFAULT, oldSubscribedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetSubscribed() {
		return subscribedESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntriesType getEntries() {
		return entries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEntries(EntriesType newEntries, NotificationChain msgs) {
		EntriesType oldEntries = entries;
		entries = newEntries;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FeedCachePackage.FEED_TYPE__ENTRIES, oldEntries, newEntries);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntries(EntriesType newEntries) {
		if (newEntries != entries) {
			NotificationChain msgs = null;
			if (entries != null)
				msgs = ((InternalEObject)entries).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FeedCachePackage.FEED_TYPE__ENTRIES, null, msgs);
			if (newEntries != null)
				msgs = ((InternalEObject)newEntries).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - FeedCachePackage.FEED_TYPE__ENTRIES, null, msgs);
			msgs = basicSetEntries(newEntries, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeedCachePackage.FEED_TYPE__ENTRIES, newEntries, newEntries));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FeedCachePackage.FEED_TYPE__ENTRIES:
				return basicSetEntries(null, msgs);
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
			case FeedCachePackage.FEED_TYPE__TITLE:
				return getTitle();
			case FeedCachePackage.FEED_TYPE__LINK:
				return getLink();
			case FeedCachePackage.FEED_TYPE__DESCRIPTION:
				return getDescription();
			case FeedCachePackage.FEED_TYPE__PUB_DATE:
				return getPubDate();
			case FeedCachePackage.FEED_TYPE__TYPE:
				return getType();
			case FeedCachePackage.FEED_TYPE__SUBSCRIBED:
				return isSubscribed() ? Boolean.TRUE : Boolean.FALSE;
			case FeedCachePackage.FEED_TYPE__ENTRIES:
				return getEntries();
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
			case FeedCachePackage.FEED_TYPE__TITLE:
				setTitle((String)newValue);
				return;
			case FeedCachePackage.FEED_TYPE__LINK:
				setLink((String)newValue);
				return;
			case FeedCachePackage.FEED_TYPE__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case FeedCachePackage.FEED_TYPE__PUB_DATE:
				setPubDate((String)newValue);
				return;
			case FeedCachePackage.FEED_TYPE__TYPE:
				setType((String)newValue);
				return;
			case FeedCachePackage.FEED_TYPE__SUBSCRIBED:
				setSubscribed(((Boolean)newValue).booleanValue());
				return;
			case FeedCachePackage.FEED_TYPE__ENTRIES:
				setEntries((EntriesType)newValue);
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
			case FeedCachePackage.FEED_TYPE__TITLE:
				setTitle(TITLE_EDEFAULT);
				return;
			case FeedCachePackage.FEED_TYPE__LINK:
				setLink(LINK_EDEFAULT);
				return;
			case FeedCachePackage.FEED_TYPE__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case FeedCachePackage.FEED_TYPE__PUB_DATE:
				setPubDate(PUB_DATE_EDEFAULT);
				return;
			case FeedCachePackage.FEED_TYPE__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case FeedCachePackage.FEED_TYPE__SUBSCRIBED:
				unsetSubscribed();
				return;
			case FeedCachePackage.FEED_TYPE__ENTRIES:
				setEntries((EntriesType)null);
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
			case FeedCachePackage.FEED_TYPE__TITLE:
				return TITLE_EDEFAULT == null ? title != null : !TITLE_EDEFAULT.equals(title);
			case FeedCachePackage.FEED_TYPE__LINK:
				return LINK_EDEFAULT == null ? link != null : !LINK_EDEFAULT.equals(link);
			case FeedCachePackage.FEED_TYPE__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case FeedCachePackage.FEED_TYPE__PUB_DATE:
				return PUB_DATE_EDEFAULT == null ? pubDate != null : !PUB_DATE_EDEFAULT.equals(pubDate);
			case FeedCachePackage.FEED_TYPE__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
			case FeedCachePackage.FEED_TYPE__SUBSCRIBED:
				return isSetSubscribed();
			case FeedCachePackage.FEED_TYPE__ENTRIES:
				return entries != null;
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
		result.append(" (title: ");
		result.append(title);
		result.append(", link: ");
		result.append(link);
		result.append(", description: ");
		result.append(description);
		result.append(", pubDate: ");
		result.append(pubDate);
		result.append(", type: ");
		result.append(type);
		result.append(", subscribed: ");
		if (subscribedESet) result.append(subscribed); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //FeedTypeImpl
