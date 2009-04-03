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

import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.EntryType;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedCachePackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Entry Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.EntryTypeImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.EntryTypeImpl#getLink <em>Link</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.EntryTypeImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.EntryTypeImpl#getPubDate <em>Pub Date</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.impl.EntryTypeImpl#isRead <em>Read</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EntryTypeImpl extends EObjectImpl implements EntryType {
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
	 * The default value of the '{@link #isRead() <em>Read</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRead()
	 * @generated
	 * @ordered
	 */
	protected static final boolean READ_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isRead() <em>Read</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRead()
	 * @generated
	 * @ordered
	 */
	protected boolean read = READ_EDEFAULT;

	/**
	 * This is true if the Read attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean readESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EntryTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FeedCachePackage.Literals.ENTRY_TYPE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, FeedCachePackage.ENTRY_TYPE__TITLE, oldTitle, title));
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
			eNotify(new ENotificationImpl(this, Notification.SET, FeedCachePackage.ENTRY_TYPE__LINK, oldLink, link));
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
			eNotify(new ENotificationImpl(this, Notification.SET, FeedCachePackage.ENTRY_TYPE__DESCRIPTION, oldDescription, description));
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
			eNotify(new ENotificationImpl(this, Notification.SET, FeedCachePackage.ENTRY_TYPE__PUB_DATE, oldPubDate, pubDate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isRead() {
		return read;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRead(boolean newRead) {
		boolean oldRead = read;
		read = newRead;
		boolean oldReadESet = readESet;
		readESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FeedCachePackage.ENTRY_TYPE__READ, oldRead, read, !oldReadESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetRead() {
		boolean oldRead = read;
		boolean oldReadESet = readESet;
		read = READ_EDEFAULT;
		readESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, FeedCachePackage.ENTRY_TYPE__READ, oldRead, READ_EDEFAULT, oldReadESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetRead() {
		return readESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FeedCachePackage.ENTRY_TYPE__TITLE:
				return getTitle();
			case FeedCachePackage.ENTRY_TYPE__LINK:
				return getLink();
			case FeedCachePackage.ENTRY_TYPE__DESCRIPTION:
				return getDescription();
			case FeedCachePackage.ENTRY_TYPE__PUB_DATE:
				return getPubDate();
			case FeedCachePackage.ENTRY_TYPE__READ:
				return isRead() ? Boolean.TRUE : Boolean.FALSE;
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
			case FeedCachePackage.ENTRY_TYPE__TITLE:
				setTitle((String)newValue);
				return;
			case FeedCachePackage.ENTRY_TYPE__LINK:
				setLink((String)newValue);
				return;
			case FeedCachePackage.ENTRY_TYPE__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case FeedCachePackage.ENTRY_TYPE__PUB_DATE:
				setPubDate((String)newValue);
				return;
			case FeedCachePackage.ENTRY_TYPE__READ:
				setRead(((Boolean)newValue).booleanValue());
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
			case FeedCachePackage.ENTRY_TYPE__TITLE:
				setTitle(TITLE_EDEFAULT);
				return;
			case FeedCachePackage.ENTRY_TYPE__LINK:
				setLink(LINK_EDEFAULT);
				return;
			case FeedCachePackage.ENTRY_TYPE__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case FeedCachePackage.ENTRY_TYPE__PUB_DATE:
				setPubDate(PUB_DATE_EDEFAULT);
				return;
			case FeedCachePackage.ENTRY_TYPE__READ:
				unsetRead();
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
			case FeedCachePackage.ENTRY_TYPE__TITLE:
				return TITLE_EDEFAULT == null ? title != null : !TITLE_EDEFAULT.equals(title);
			case FeedCachePackage.ENTRY_TYPE__LINK:
				return LINK_EDEFAULT == null ? link != null : !LINK_EDEFAULT.equals(link);
			case FeedCachePackage.ENTRY_TYPE__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case FeedCachePackage.ENTRY_TYPE__PUB_DATE:
				return PUB_DATE_EDEFAULT == null ? pubDate != null : !PUB_DATE_EDEFAULT.equals(pubDate);
			case FeedCachePackage.ENTRY_TYPE__READ:
				return isSetRead();
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
		result.append(", read: ");
		if (readESet) result.append(read); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //EntryTypeImpl
