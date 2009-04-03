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

package com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl;

import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.AbstractType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DirType166;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ImportanceType172;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PrologType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.RefbodyType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ReferenceType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.RelatedLinksType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShortdescType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.StatusType109;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TitleType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TitlealtsType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TranslateType175;

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
 * An implementation of the model object '<em><b>Reference Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.ReferenceTypeImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.ReferenceTypeImpl#getTitlealts <em>Titlealts</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.ReferenceTypeImpl#getShortdesc <em>Shortdesc</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.ReferenceTypeImpl#getAbstract <em>Abstract</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.ReferenceTypeImpl#getProlog <em>Prolog</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.ReferenceTypeImpl#getRefbody <em>Refbody</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.ReferenceTypeImpl#getRelatedLinks <em>Related Links</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.ReferenceTypeImpl#getTopic <em>Topic</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.ReferenceTypeImpl#getAudience <em>Audience</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.ReferenceTypeImpl#getBase <em>Base</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.ReferenceTypeImpl#getClass_ <em>Class</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.ReferenceTypeImpl#getConref <em>Conref</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.ReferenceTypeImpl#getDir <em>Dir</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.ReferenceTypeImpl#getDITAArchVersion <em>DITA Arch Version</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.ReferenceTypeImpl#getDomains <em>Domains</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.ReferenceTypeImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.ReferenceTypeImpl#getImportance <em>Importance</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.ReferenceTypeImpl#getLang <em>Lang</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.ReferenceTypeImpl#getOtherprops <em>Otherprops</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.ReferenceTypeImpl#getOutputclass <em>Outputclass</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.ReferenceTypeImpl#getPlatform <em>Platform</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.ReferenceTypeImpl#getProduct <em>Product</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.ReferenceTypeImpl#getProps <em>Props</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.ReferenceTypeImpl#getRev <em>Rev</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.ReferenceTypeImpl#getStatus <em>Status</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.ReferenceTypeImpl#getTranslate <em>Translate</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.ReferenceTypeImpl#getXtrc <em>Xtrc</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.ReferenceTypeImpl#getXtrf <em>Xtrf</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ReferenceTypeImpl extends EObjectImpl implements ReferenceType {
	/**
	 * The cached value of the '{@link #getTitle() <em>Title</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected TitleType title;

	/**
	 * The cached value of the '{@link #getTitlealts() <em>Titlealts</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitlealts()
	 * @generated
	 * @ordered
	 */
	protected TitlealtsType titlealts;

	/**
	 * The cached value of the '{@link #getShortdesc() <em>Shortdesc</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShortdesc()
	 * @generated
	 * @ordered
	 */
	protected ShortdescType shortdesc;

	/**
	 * The cached value of the '{@link #getAbstract() <em>Abstract</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAbstract()
	 * @generated
	 * @ordered
	 */
	protected AbstractType abstract_;

	/**
	 * The cached value of the '{@link #getProlog() <em>Prolog</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProlog()
	 * @generated
	 * @ordered
	 */
	protected PrologType prolog;

	/**
	 * The cached value of the '{@link #getRefbody() <em>Refbody</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRefbody()
	 * @generated
	 * @ordered
	 */
	protected RefbodyType refbody;

	/**
	 * The cached value of the '{@link #getRelatedLinks() <em>Related Links</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelatedLinks()
	 * @generated
	 * @ordered
	 */
	protected RelatedLinksType relatedLinks;

	/**
	 * The cached value of the '{@link #getTopic() <em>Topic</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTopic()
	 * @generated
	 * @ordered
	 */
	protected EList<TopicType> topic;

	/**
	 * The default value of the '{@link #getAudience() <em>Audience</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAudience()
	 * @generated
	 * @ordered
	 */
	protected static final Object AUDIENCE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAudience() <em>Audience</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAudience()
	 * @generated
	 * @ordered
	 */
	protected Object audience = AUDIENCE_EDEFAULT;

	/**
	 * The default value of the '{@link #getBase() <em>Base</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBase()
	 * @generated
	 * @ordered
	 */
	protected static final Object BASE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBase() <em>Base</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBase()
	 * @generated
	 * @ordered
	 */
	protected Object base = BASE_EDEFAULT;

	/**
	 * The default value of the '{@link #getClass_() <em>Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClass_()
	 * @generated
	 * @ordered
	 */
	protected static final Object CLASS_EDEFAULT = "- topic/topic       reference/reference ";

	/**
	 * The cached value of the '{@link #getClass_() <em>Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClass_()
	 * @generated
	 * @ordered
	 */
	protected Object class_ = CLASS_EDEFAULT;

	/**
	 * This is true if the Class attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean classESet;

	/**
	 * The default value of the '{@link #getConref() <em>Conref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConref()
	 * @generated
	 * @ordered
	 */
	protected static final Object CONREF_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getConref() <em>Conref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConref()
	 * @generated
	 * @ordered
	 */
	protected Object conref = CONREF_EDEFAULT;

	/**
	 * The default value of the '{@link #getDir() <em>Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDir()
	 * @generated
	 * @ordered
	 */
	protected static final DirType166 DIR_EDEFAULT = DirType166.LTR;

	/**
	 * The cached value of the '{@link #getDir() <em>Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDir()
	 * @generated
	 * @ordered
	 */
	protected DirType166 dir = DIR_EDEFAULT;

	/**
	 * This is true if the Dir attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean dirESet;

	/**
	 * The default value of the '{@link #getDITAArchVersion() <em>DITA Arch Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDITAArchVersion()
	 * @generated
	 * @ordered
	 */
	protected static final Object DITA_ARCH_VERSION_EDEFAULT = "1.1";

	/**
	 * The cached value of the '{@link #getDITAArchVersion() <em>DITA Arch Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDITAArchVersion()
	 * @generated
	 * @ordered
	 */
	protected Object dITAArchVersion = DITA_ARCH_VERSION_EDEFAULT;

	/**
	 * This is true if the DITA Arch Version attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean dITAArchVersionESet;

	/**
	 * The default value of the '{@link #getDomains() <em>Domains</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomains()
	 * @generated
	 * @ordered
	 */
	protected static final Object DOMAINS_EDEFAULT = "(topic ui-d)                            (topic hi-d)                            (topic pr-d)                            (topic sw-d)                            (topic ut-d)                            (topic indexing-d)                            a(base kbdataFw)";

	/**
	 * The cached value of the '{@link #getDomains() <em>Domains</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomains()
	 * @generated
	 * @ordered
	 */
	protected Object domains = DOMAINS_EDEFAULT;

	/**
	 * This is true if the Domains attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean domainsESet;

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getImportance() <em>Importance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImportance()
	 * @generated
	 * @ordered
	 */
	protected static final ImportanceType172 IMPORTANCE_EDEFAULT = ImportanceType172.OBSOLETE;

	/**
	 * The cached value of the '{@link #getImportance() <em>Importance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImportance()
	 * @generated
	 * @ordered
	 */
	protected ImportanceType172 importance = IMPORTANCE_EDEFAULT;

	/**
	 * This is true if the Importance attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean importanceESet;

	/**
	 * The default value of the '{@link #getLang() <em>Lang</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLang()
	 * @generated
	 * @ordered
	 */
	protected static final String LANG_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLang() <em>Lang</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLang()
	 * @generated
	 * @ordered
	 */
	protected String lang = LANG_EDEFAULT;

	/**
	 * The default value of the '{@link #getOtherprops() <em>Otherprops</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOtherprops()
	 * @generated
	 * @ordered
	 */
	protected static final Object OTHERPROPS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOtherprops() <em>Otherprops</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOtherprops()
	 * @generated
	 * @ordered
	 */
	protected Object otherprops = OTHERPROPS_EDEFAULT;

	/**
	 * The default value of the '{@link #getOutputclass() <em>Outputclass</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputclass()
	 * @generated
	 * @ordered
	 */
	protected static final Object OUTPUTCLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOutputclass() <em>Outputclass</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputclass()
	 * @generated
	 * @ordered
	 */
	protected Object outputclass = OUTPUTCLASS_EDEFAULT;

	/**
	 * The default value of the '{@link #getPlatform() <em>Platform</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlatform()
	 * @generated
	 * @ordered
	 */
	protected static final Object PLATFORM_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPlatform() <em>Platform</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlatform()
	 * @generated
	 * @ordered
	 */
	protected Object platform = PLATFORM_EDEFAULT;

	/**
	 * The default value of the '{@link #getProduct() <em>Product</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProduct()
	 * @generated
	 * @ordered
	 */
	protected static final Object PRODUCT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProduct() <em>Product</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProduct()
	 * @generated
	 * @ordered
	 */
	protected Object product = PRODUCT_EDEFAULT;

	/**
	 * The default value of the '{@link #getProps() <em>Props</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProps()
	 * @generated
	 * @ordered
	 */
	protected static final Object PROPS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProps() <em>Props</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProps()
	 * @generated
	 * @ordered
	 */
	protected Object props = PROPS_EDEFAULT;

	/**
	 * The default value of the '{@link #getRev() <em>Rev</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRev()
	 * @generated
	 * @ordered
	 */
	protected static final Object REV_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRev() <em>Rev</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRev()
	 * @generated
	 * @ordered
	 */
	protected Object rev = REV_EDEFAULT;

	/**
	 * The default value of the '{@link #getStatus() <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus()
	 * @generated
	 * @ordered
	 */
	protected static final StatusType109 STATUS_EDEFAULT = StatusType109.NEW;

	/**
	 * The cached value of the '{@link #getStatus() <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus()
	 * @generated
	 * @ordered
	 */
	protected StatusType109 status = STATUS_EDEFAULT;

	/**
	 * This is true if the Status attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean statusESet;

	/**
	 * The default value of the '{@link #getTranslate() <em>Translate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTranslate()
	 * @generated
	 * @ordered
	 */
	protected static final TranslateType175 TRANSLATE_EDEFAULT = TranslateType175.YES;

	/**
	 * The cached value of the '{@link #getTranslate() <em>Translate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTranslate()
	 * @generated
	 * @ordered
	 */
	protected TranslateType175 translate = TRANSLATE_EDEFAULT;

	/**
	 * This is true if the Translate attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean translateESet;

	/**
	 * The default value of the '{@link #getXtrc() <em>Xtrc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXtrc()
	 * @generated
	 * @ordered
	 */
	protected static final Object XTRC_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getXtrc() <em>Xtrc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXtrc()
	 * @generated
	 * @ordered
	 */
	protected Object xtrc = XTRC_EDEFAULT;

	/**
	 * The default value of the '{@link #getXtrf() <em>Xtrf</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXtrf()
	 * @generated
	 * @ordered
	 */
	protected static final Object XTRF_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getXtrf() <em>Xtrf</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXtrf()
	 * @generated
	 * @ordered
	 */
	protected Object xtrf = XTRF_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReferenceTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return KbdataPackage.eINSTANCE.getReferenceType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TitleType getTitle() {
		return title;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTitle(TitleType newTitle, NotificationChain msgs) {
		TitleType oldTitle = title;
		title = newTitle;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__TITLE, oldTitle, newTitle);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTitle(TitleType newTitle) {
		if (newTitle != title) {
			NotificationChain msgs = null;
			if (title != null)
				msgs = ((InternalEObject)title).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.REFERENCE_TYPE__TITLE, null, msgs);
			if (newTitle != null)
				msgs = ((InternalEObject)newTitle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.REFERENCE_TYPE__TITLE, null, msgs);
			msgs = basicSetTitle(newTitle, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__TITLE, newTitle, newTitle));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TitlealtsType getTitlealts() {
		return titlealts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTitlealts(TitlealtsType newTitlealts, NotificationChain msgs) {
		TitlealtsType oldTitlealts = titlealts;
		titlealts = newTitlealts;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__TITLEALTS, oldTitlealts, newTitlealts);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTitlealts(TitlealtsType newTitlealts) {
		if (newTitlealts != titlealts) {
			NotificationChain msgs = null;
			if (titlealts != null)
				msgs = ((InternalEObject)titlealts).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.REFERENCE_TYPE__TITLEALTS, null, msgs);
			if (newTitlealts != null)
				msgs = ((InternalEObject)newTitlealts).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.REFERENCE_TYPE__TITLEALTS, null, msgs);
			msgs = basicSetTitlealts(newTitlealts, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__TITLEALTS, newTitlealts, newTitlealts));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ShortdescType getShortdesc() {
		return shortdesc;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetShortdesc(ShortdescType newShortdesc, NotificationChain msgs) {
		ShortdescType oldShortdesc = shortdesc;
		shortdesc = newShortdesc;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__SHORTDESC, oldShortdesc, newShortdesc);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShortdesc(ShortdescType newShortdesc) {
		if (newShortdesc != shortdesc) {
			NotificationChain msgs = null;
			if (shortdesc != null)
				msgs = ((InternalEObject)shortdesc).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.REFERENCE_TYPE__SHORTDESC, null, msgs);
			if (newShortdesc != null)
				msgs = ((InternalEObject)newShortdesc).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.REFERENCE_TYPE__SHORTDESC, null, msgs);
			msgs = basicSetShortdesc(newShortdesc, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__SHORTDESC, newShortdesc, newShortdesc));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractType getAbstract() {
		return abstract_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAbstract(AbstractType newAbstract, NotificationChain msgs) {
		AbstractType oldAbstract = abstract_;
		abstract_ = newAbstract;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__ABSTRACT, oldAbstract, newAbstract);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAbstract(AbstractType newAbstract) {
		if (newAbstract != abstract_) {
			NotificationChain msgs = null;
			if (abstract_ != null)
				msgs = ((InternalEObject)abstract_).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.REFERENCE_TYPE__ABSTRACT, null, msgs);
			if (newAbstract != null)
				msgs = ((InternalEObject)newAbstract).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.REFERENCE_TYPE__ABSTRACT, null, msgs);
			msgs = basicSetAbstract(newAbstract, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__ABSTRACT, newAbstract, newAbstract));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PrologType getProlog() {
		return prolog;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProlog(PrologType newProlog, NotificationChain msgs) {
		PrologType oldProlog = prolog;
		prolog = newProlog;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__PROLOG, oldProlog, newProlog);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProlog(PrologType newProlog) {
		if (newProlog != prolog) {
			NotificationChain msgs = null;
			if (prolog != null)
				msgs = ((InternalEObject)prolog).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.REFERENCE_TYPE__PROLOG, null, msgs);
			if (newProlog != null)
				msgs = ((InternalEObject)newProlog).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.REFERENCE_TYPE__PROLOG, null, msgs);
			msgs = basicSetProlog(newProlog, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__PROLOG, newProlog, newProlog));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RefbodyType getRefbody() {
		return refbody;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRefbody(RefbodyType newRefbody, NotificationChain msgs) {
		RefbodyType oldRefbody = refbody;
		refbody = newRefbody;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__REFBODY, oldRefbody, newRefbody);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRefbody(RefbodyType newRefbody) {
		if (newRefbody != refbody) {
			NotificationChain msgs = null;
			if (refbody != null)
				msgs = ((InternalEObject)refbody).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.REFERENCE_TYPE__REFBODY, null, msgs);
			if (newRefbody != null)
				msgs = ((InternalEObject)newRefbody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.REFERENCE_TYPE__REFBODY, null, msgs);
			msgs = basicSetRefbody(newRefbody, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__REFBODY, newRefbody, newRefbody));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RelatedLinksType getRelatedLinks() {
		return relatedLinks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRelatedLinks(RelatedLinksType newRelatedLinks, NotificationChain msgs) {
		RelatedLinksType oldRelatedLinks = relatedLinks;
		relatedLinks = newRelatedLinks;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__RELATED_LINKS, oldRelatedLinks, newRelatedLinks);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRelatedLinks(RelatedLinksType newRelatedLinks) {
		if (newRelatedLinks != relatedLinks) {
			NotificationChain msgs = null;
			if (relatedLinks != null)
				msgs = ((InternalEObject)relatedLinks).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.REFERENCE_TYPE__RELATED_LINKS, null, msgs);
			if (newRelatedLinks != null)
				msgs = ((InternalEObject)newRelatedLinks).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.REFERENCE_TYPE__RELATED_LINKS, null, msgs);
			msgs = basicSetRelatedLinks(newRelatedLinks, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__RELATED_LINKS, newRelatedLinks, newRelatedLinks));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TopicType> getTopic() {
		if (topic == null) {
			topic = new EObjectContainmentEList<TopicType>(TopicType.class, this, KbdataPackage.REFERENCE_TYPE__TOPIC);
		}
		return topic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getAudience() {
		return audience;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAudience(Object newAudience) {
		Object oldAudience = audience;
		audience = newAudience;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__AUDIENCE, oldAudience, audience));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getBase() {
		return base;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBase(Object newBase) {
		Object oldBase = base;
		base = newBase;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__BASE, oldBase, base));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getClass_() {
		return class_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClass(Object newClass) {
		Object oldClass = class_;
		class_ = newClass;
		boolean oldClassESet = classESet;
		classESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__CLASS, oldClass, class_, !oldClassESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetClass() {
		Object oldClass = class_;
		boolean oldClassESet = classESet;
		class_ = CLASS_EDEFAULT;
		classESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.REFERENCE_TYPE__CLASS, oldClass, CLASS_EDEFAULT, oldClassESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetClass() {
		return classESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getConref() {
		return conref;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConref(Object newConref) {
		Object oldConref = conref;
		conref = newConref;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__CONREF, oldConref, conref));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DirType166 getDir() {
		return dir;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDir(DirType166 newDir) {
		DirType166 oldDir = dir;
		dir = newDir == null ? DIR_EDEFAULT : newDir;
		boolean oldDirESet = dirESet;
		dirESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__DIR, oldDir, dir, !oldDirESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetDir() {
		DirType166 oldDir = dir;
		boolean oldDirESet = dirESet;
		dir = DIR_EDEFAULT;
		dirESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.REFERENCE_TYPE__DIR, oldDir, DIR_EDEFAULT, oldDirESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetDir() {
		return dirESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getDITAArchVersion() {
		return dITAArchVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDITAArchVersion(Object newDITAArchVersion) {
		Object oldDITAArchVersion = dITAArchVersion;
		dITAArchVersion = newDITAArchVersion;
		boolean oldDITAArchVersionESet = dITAArchVersionESet;
		dITAArchVersionESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__DITA_ARCH_VERSION, oldDITAArchVersion, dITAArchVersion, !oldDITAArchVersionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetDITAArchVersion() {
		Object oldDITAArchVersion = dITAArchVersion;
		boolean oldDITAArchVersionESet = dITAArchVersionESet;
		dITAArchVersion = DITA_ARCH_VERSION_EDEFAULT;
		dITAArchVersionESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.REFERENCE_TYPE__DITA_ARCH_VERSION, oldDITAArchVersion, DITA_ARCH_VERSION_EDEFAULT, oldDITAArchVersionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetDITAArchVersion() {
		return dITAArchVersionESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getDomains() {
		return domains;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDomains(Object newDomains) {
		Object oldDomains = domains;
		domains = newDomains;
		boolean oldDomainsESet = domainsESet;
		domainsESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__DOMAINS, oldDomains, domains, !oldDomainsESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetDomains() {
		Object oldDomains = domains;
		boolean oldDomainsESet = domainsESet;
		domains = DOMAINS_EDEFAULT;
		domainsESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.REFERENCE_TYPE__DOMAINS, oldDomains, DOMAINS_EDEFAULT, oldDomainsESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetDomains() {
		return domainsESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImportanceType172 getImportance() {
		return importance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImportance(ImportanceType172 newImportance) {
		ImportanceType172 oldImportance = importance;
		importance = newImportance == null ? IMPORTANCE_EDEFAULT : newImportance;
		boolean oldImportanceESet = importanceESet;
		importanceESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__IMPORTANCE, oldImportance, importance, !oldImportanceESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetImportance() {
		ImportanceType172 oldImportance = importance;
		boolean oldImportanceESet = importanceESet;
		importance = IMPORTANCE_EDEFAULT;
		importanceESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.REFERENCE_TYPE__IMPORTANCE, oldImportance, IMPORTANCE_EDEFAULT, oldImportanceESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetImportance() {
		return importanceESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLang(String newLang) {
		String oldLang = lang;
		lang = newLang;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__LANG, oldLang, lang));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getOtherprops() {
		return otherprops;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOtherprops(Object newOtherprops) {
		Object oldOtherprops = otherprops;
		otherprops = newOtherprops;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__OTHERPROPS, oldOtherprops, otherprops));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getOutputclass() {
		return outputclass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOutputclass(Object newOutputclass) {
		Object oldOutputclass = outputclass;
		outputclass = newOutputclass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__OUTPUTCLASS, oldOutputclass, outputclass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getPlatform() {
		return platform;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPlatform(Object newPlatform) {
		Object oldPlatform = platform;
		platform = newPlatform;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__PLATFORM, oldPlatform, platform));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getProduct() {
		return product;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProduct(Object newProduct) {
		Object oldProduct = product;
		product = newProduct;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__PRODUCT, oldProduct, product));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getProps() {
		return props;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProps(Object newProps) {
		Object oldProps = props;
		props = newProps;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__PROPS, oldProps, props));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getRev() {
		return rev;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRev(Object newRev) {
		Object oldRev = rev;
		rev = newRev;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__REV, oldRev, rev));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatusType109 getStatus() {
		return status;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStatus(StatusType109 newStatus) {
		StatusType109 oldStatus = status;
		status = newStatus == null ? STATUS_EDEFAULT : newStatus;
		boolean oldStatusESet = statusESet;
		statusESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__STATUS, oldStatus, status, !oldStatusESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetStatus() {
		StatusType109 oldStatus = status;
		boolean oldStatusESet = statusESet;
		status = STATUS_EDEFAULT;
		statusESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.REFERENCE_TYPE__STATUS, oldStatus, STATUS_EDEFAULT, oldStatusESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetStatus() {
		return statusESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TranslateType175 getTranslate() {
		return translate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTranslate(TranslateType175 newTranslate) {
		TranslateType175 oldTranslate = translate;
		translate = newTranslate == null ? TRANSLATE_EDEFAULT : newTranslate;
		boolean oldTranslateESet = translateESet;
		translateESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__TRANSLATE, oldTranslate, translate, !oldTranslateESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetTranslate() {
		TranslateType175 oldTranslate = translate;
		boolean oldTranslateESet = translateESet;
		translate = TRANSLATE_EDEFAULT;
		translateESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.REFERENCE_TYPE__TRANSLATE, oldTranslate, TRANSLATE_EDEFAULT, oldTranslateESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetTranslate() {
		return translateESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getXtrc() {
		return xtrc;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setXtrc(Object newXtrc) {
		Object oldXtrc = xtrc;
		xtrc = newXtrc;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__XTRC, oldXtrc, xtrc));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getXtrf() {
		return xtrf;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setXtrf(Object newXtrf) {
		Object oldXtrf = xtrf;
		xtrf = newXtrf;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.REFERENCE_TYPE__XTRF, oldXtrf, xtrf));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case KbdataPackage.REFERENCE_TYPE__TITLE:
				return basicSetTitle(null, msgs);
			case KbdataPackage.REFERENCE_TYPE__TITLEALTS:
				return basicSetTitlealts(null, msgs);
			case KbdataPackage.REFERENCE_TYPE__SHORTDESC:
				return basicSetShortdesc(null, msgs);
			case KbdataPackage.REFERENCE_TYPE__ABSTRACT:
				return basicSetAbstract(null, msgs);
			case KbdataPackage.REFERENCE_TYPE__PROLOG:
				return basicSetProlog(null, msgs);
			case KbdataPackage.REFERENCE_TYPE__REFBODY:
				return basicSetRefbody(null, msgs);
			case KbdataPackage.REFERENCE_TYPE__RELATED_LINKS:
				return basicSetRelatedLinks(null, msgs);
			case KbdataPackage.REFERENCE_TYPE__TOPIC:
				return ((InternalEList<?>)getTopic()).basicRemove(otherEnd, msgs);
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
			case KbdataPackage.REFERENCE_TYPE__TITLE:
				return getTitle();
			case KbdataPackage.REFERENCE_TYPE__TITLEALTS:
				return getTitlealts();
			case KbdataPackage.REFERENCE_TYPE__SHORTDESC:
				return getShortdesc();
			case KbdataPackage.REFERENCE_TYPE__ABSTRACT:
				return getAbstract();
			case KbdataPackage.REFERENCE_TYPE__PROLOG:
				return getProlog();
			case KbdataPackage.REFERENCE_TYPE__REFBODY:
				return getRefbody();
			case KbdataPackage.REFERENCE_TYPE__RELATED_LINKS:
				return getRelatedLinks();
			case KbdataPackage.REFERENCE_TYPE__TOPIC:
				return getTopic();
			case KbdataPackage.REFERENCE_TYPE__AUDIENCE:
				return getAudience();
			case KbdataPackage.REFERENCE_TYPE__BASE:
				return getBase();
			case KbdataPackage.REFERENCE_TYPE__CLASS:
				return getClass_();
			case KbdataPackage.REFERENCE_TYPE__CONREF:
				return getConref();
			case KbdataPackage.REFERENCE_TYPE__DIR:
				return getDir();
			case KbdataPackage.REFERENCE_TYPE__DITA_ARCH_VERSION:
				return getDITAArchVersion();
			case KbdataPackage.REFERENCE_TYPE__DOMAINS:
				return getDomains();
			case KbdataPackage.REFERENCE_TYPE__ID:
				return getId();
			case KbdataPackage.REFERENCE_TYPE__IMPORTANCE:
				return getImportance();
			case KbdataPackage.REFERENCE_TYPE__LANG:
				return getLang();
			case KbdataPackage.REFERENCE_TYPE__OTHERPROPS:
				return getOtherprops();
			case KbdataPackage.REFERENCE_TYPE__OUTPUTCLASS:
				return getOutputclass();
			case KbdataPackage.REFERENCE_TYPE__PLATFORM:
				return getPlatform();
			case KbdataPackage.REFERENCE_TYPE__PRODUCT:
				return getProduct();
			case KbdataPackage.REFERENCE_TYPE__PROPS:
				return getProps();
			case KbdataPackage.REFERENCE_TYPE__REV:
				return getRev();
			case KbdataPackage.REFERENCE_TYPE__STATUS:
				return getStatus();
			case KbdataPackage.REFERENCE_TYPE__TRANSLATE:
				return getTranslate();
			case KbdataPackage.REFERENCE_TYPE__XTRC:
				return getXtrc();
			case KbdataPackage.REFERENCE_TYPE__XTRF:
				return getXtrf();
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
			case KbdataPackage.REFERENCE_TYPE__TITLE:
				setTitle((TitleType)newValue);
				return;
			case KbdataPackage.REFERENCE_TYPE__TITLEALTS:
				setTitlealts((TitlealtsType)newValue);
				return;
			case KbdataPackage.REFERENCE_TYPE__SHORTDESC:
				setShortdesc((ShortdescType)newValue);
				return;
			case KbdataPackage.REFERENCE_TYPE__ABSTRACT:
				setAbstract((AbstractType)newValue);
				return;
			case KbdataPackage.REFERENCE_TYPE__PROLOG:
				setProlog((PrologType)newValue);
				return;
			case KbdataPackage.REFERENCE_TYPE__REFBODY:
				setRefbody((RefbodyType)newValue);
				return;
			case KbdataPackage.REFERENCE_TYPE__RELATED_LINKS:
				setRelatedLinks((RelatedLinksType)newValue);
				return;
			case KbdataPackage.REFERENCE_TYPE__TOPIC:
				getTopic().clear();
				getTopic().addAll((Collection<? extends TopicType>)newValue);
				return;
			case KbdataPackage.REFERENCE_TYPE__AUDIENCE:
				setAudience(newValue);
				return;
			case KbdataPackage.REFERENCE_TYPE__BASE:
				setBase(newValue);
				return;
			case KbdataPackage.REFERENCE_TYPE__CLASS:
				setClass(newValue);
				return;
			case KbdataPackage.REFERENCE_TYPE__CONREF:
				setConref(newValue);
				return;
			case KbdataPackage.REFERENCE_TYPE__DIR:
				setDir((DirType166)newValue);
				return;
			case KbdataPackage.REFERENCE_TYPE__DITA_ARCH_VERSION:
				setDITAArchVersion(newValue);
				return;
			case KbdataPackage.REFERENCE_TYPE__DOMAINS:
				setDomains(newValue);
				return;
			case KbdataPackage.REFERENCE_TYPE__ID:
				setId((String)newValue);
				return;
			case KbdataPackage.REFERENCE_TYPE__IMPORTANCE:
				setImportance((ImportanceType172)newValue);
				return;
			case KbdataPackage.REFERENCE_TYPE__LANG:
				setLang((String)newValue);
				return;
			case KbdataPackage.REFERENCE_TYPE__OTHERPROPS:
				setOtherprops(newValue);
				return;
			case KbdataPackage.REFERENCE_TYPE__OUTPUTCLASS:
				setOutputclass(newValue);
				return;
			case KbdataPackage.REFERENCE_TYPE__PLATFORM:
				setPlatform(newValue);
				return;
			case KbdataPackage.REFERENCE_TYPE__PRODUCT:
				setProduct(newValue);
				return;
			case KbdataPackage.REFERENCE_TYPE__PROPS:
				setProps(newValue);
				return;
			case KbdataPackage.REFERENCE_TYPE__REV:
				setRev(newValue);
				return;
			case KbdataPackage.REFERENCE_TYPE__STATUS:
				setStatus((StatusType109)newValue);
				return;
			case KbdataPackage.REFERENCE_TYPE__TRANSLATE:
				setTranslate((TranslateType175)newValue);
				return;
			case KbdataPackage.REFERENCE_TYPE__XTRC:
				setXtrc(newValue);
				return;
			case KbdataPackage.REFERENCE_TYPE__XTRF:
				setXtrf(newValue);
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
			case KbdataPackage.REFERENCE_TYPE__TITLE:
				setTitle((TitleType)null);
				return;
			case KbdataPackage.REFERENCE_TYPE__TITLEALTS:
				setTitlealts((TitlealtsType)null);
				return;
			case KbdataPackage.REFERENCE_TYPE__SHORTDESC:
				setShortdesc((ShortdescType)null);
				return;
			case KbdataPackage.REFERENCE_TYPE__ABSTRACT:
				setAbstract((AbstractType)null);
				return;
			case KbdataPackage.REFERENCE_TYPE__PROLOG:
				setProlog((PrologType)null);
				return;
			case KbdataPackage.REFERENCE_TYPE__REFBODY:
				setRefbody((RefbodyType)null);
				return;
			case KbdataPackage.REFERENCE_TYPE__RELATED_LINKS:
				setRelatedLinks((RelatedLinksType)null);
				return;
			case KbdataPackage.REFERENCE_TYPE__TOPIC:
				getTopic().clear();
				return;
			case KbdataPackage.REFERENCE_TYPE__AUDIENCE:
				setAudience(AUDIENCE_EDEFAULT);
				return;
			case KbdataPackage.REFERENCE_TYPE__BASE:
				setBase(BASE_EDEFAULT);
				return;
			case KbdataPackage.REFERENCE_TYPE__CLASS:
				unsetClass();
				return;
			case KbdataPackage.REFERENCE_TYPE__CONREF:
				setConref(CONREF_EDEFAULT);
				return;
			case KbdataPackage.REFERENCE_TYPE__DIR:
				unsetDir();
				return;
			case KbdataPackage.REFERENCE_TYPE__DITA_ARCH_VERSION:
				unsetDITAArchVersion();
				return;
			case KbdataPackage.REFERENCE_TYPE__DOMAINS:
				unsetDomains();
				return;
			case KbdataPackage.REFERENCE_TYPE__ID:
				setId(ID_EDEFAULT);
				return;
			case KbdataPackage.REFERENCE_TYPE__IMPORTANCE:
				unsetImportance();
				return;
			case KbdataPackage.REFERENCE_TYPE__LANG:
				setLang(LANG_EDEFAULT);
				return;
			case KbdataPackage.REFERENCE_TYPE__OTHERPROPS:
				setOtherprops(OTHERPROPS_EDEFAULT);
				return;
			case KbdataPackage.REFERENCE_TYPE__OUTPUTCLASS:
				setOutputclass(OUTPUTCLASS_EDEFAULT);
				return;
			case KbdataPackage.REFERENCE_TYPE__PLATFORM:
				setPlatform(PLATFORM_EDEFAULT);
				return;
			case KbdataPackage.REFERENCE_TYPE__PRODUCT:
				setProduct(PRODUCT_EDEFAULT);
				return;
			case KbdataPackage.REFERENCE_TYPE__PROPS:
				setProps(PROPS_EDEFAULT);
				return;
			case KbdataPackage.REFERENCE_TYPE__REV:
				setRev(REV_EDEFAULT);
				return;
			case KbdataPackage.REFERENCE_TYPE__STATUS:
				unsetStatus();
				return;
			case KbdataPackage.REFERENCE_TYPE__TRANSLATE:
				unsetTranslate();
				return;
			case KbdataPackage.REFERENCE_TYPE__XTRC:
				setXtrc(XTRC_EDEFAULT);
				return;
			case KbdataPackage.REFERENCE_TYPE__XTRF:
				setXtrf(XTRF_EDEFAULT);
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
			case KbdataPackage.REFERENCE_TYPE__TITLE:
				return title != null;
			case KbdataPackage.REFERENCE_TYPE__TITLEALTS:
				return titlealts != null;
			case KbdataPackage.REFERENCE_TYPE__SHORTDESC:
				return shortdesc != null;
			case KbdataPackage.REFERENCE_TYPE__ABSTRACT:
				return abstract_ != null;
			case KbdataPackage.REFERENCE_TYPE__PROLOG:
				return prolog != null;
			case KbdataPackage.REFERENCE_TYPE__REFBODY:
				return refbody != null;
			case KbdataPackage.REFERENCE_TYPE__RELATED_LINKS:
				return relatedLinks != null;
			case KbdataPackage.REFERENCE_TYPE__TOPIC:
				return topic != null && !topic.isEmpty();
			case KbdataPackage.REFERENCE_TYPE__AUDIENCE:
				return AUDIENCE_EDEFAULT == null ? audience != null : !AUDIENCE_EDEFAULT.equals(audience);
			case KbdataPackage.REFERENCE_TYPE__BASE:
				return BASE_EDEFAULT == null ? base != null : !BASE_EDEFAULT.equals(base);
			case KbdataPackage.REFERENCE_TYPE__CLASS:
				return isSetClass();
			case KbdataPackage.REFERENCE_TYPE__CONREF:
				return CONREF_EDEFAULT == null ? conref != null : !CONREF_EDEFAULT.equals(conref);
			case KbdataPackage.REFERENCE_TYPE__DIR:
				return isSetDir();
			case KbdataPackage.REFERENCE_TYPE__DITA_ARCH_VERSION:
				return isSetDITAArchVersion();
			case KbdataPackage.REFERENCE_TYPE__DOMAINS:
				return isSetDomains();
			case KbdataPackage.REFERENCE_TYPE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case KbdataPackage.REFERENCE_TYPE__IMPORTANCE:
				return isSetImportance();
			case KbdataPackage.REFERENCE_TYPE__LANG:
				return LANG_EDEFAULT == null ? lang != null : !LANG_EDEFAULT.equals(lang);
			case KbdataPackage.REFERENCE_TYPE__OTHERPROPS:
				return OTHERPROPS_EDEFAULT == null ? otherprops != null : !OTHERPROPS_EDEFAULT.equals(otherprops);
			case KbdataPackage.REFERENCE_TYPE__OUTPUTCLASS:
				return OUTPUTCLASS_EDEFAULT == null ? outputclass != null : !OUTPUTCLASS_EDEFAULT.equals(outputclass);
			case KbdataPackage.REFERENCE_TYPE__PLATFORM:
				return PLATFORM_EDEFAULT == null ? platform != null : !PLATFORM_EDEFAULT.equals(platform);
			case KbdataPackage.REFERENCE_TYPE__PRODUCT:
				return PRODUCT_EDEFAULT == null ? product != null : !PRODUCT_EDEFAULT.equals(product);
			case KbdataPackage.REFERENCE_TYPE__PROPS:
				return PROPS_EDEFAULT == null ? props != null : !PROPS_EDEFAULT.equals(props);
			case KbdataPackage.REFERENCE_TYPE__REV:
				return REV_EDEFAULT == null ? rev != null : !REV_EDEFAULT.equals(rev);
			case KbdataPackage.REFERENCE_TYPE__STATUS:
				return isSetStatus();
			case KbdataPackage.REFERENCE_TYPE__TRANSLATE:
				return isSetTranslate();
			case KbdataPackage.REFERENCE_TYPE__XTRC:
				return XTRC_EDEFAULT == null ? xtrc != null : !XTRC_EDEFAULT.equals(xtrc);
			case KbdataPackage.REFERENCE_TYPE__XTRF:
				return XTRF_EDEFAULT == null ? xtrf != null : !XTRF_EDEFAULT.equals(xtrf);
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
		result.append(" (audience: ");
		result.append(audience);
		result.append(", base: ");
		result.append(base);
		result.append(", class: ");
		if (classESet) result.append(class_); else result.append("<unset>");
		result.append(", conref: ");
		result.append(conref);
		result.append(", dir: ");
		if (dirESet) result.append(dir); else result.append("<unset>");
		result.append(", dITAArchVersion: ");
		if (dITAArchVersionESet) result.append(dITAArchVersion); else result.append("<unset>");
		result.append(", domains: ");
		if (domainsESet) result.append(domains); else result.append("<unset>");
		result.append(", id: ");
		result.append(id);
		result.append(", importance: ");
		if (importanceESet) result.append(importance); else result.append("<unset>");
		result.append(", lang: ");
		result.append(lang);
		result.append(", otherprops: ");
		result.append(otherprops);
		result.append(", outputclass: ");
		result.append(outputclass);
		result.append(", platform: ");
		result.append(platform);
		result.append(", product: ");
		result.append(product);
		result.append(", props: ");
		result.append(props);
		result.append(", rev: ");
		result.append(rev);
		result.append(", status: ");
		if (statusESet) result.append(status); else result.append("<unset>");
		result.append(", translate: ");
		if (translateESet) result.append(translate); else result.append("<unset>");
		result.append(", xtrc: ");
		result.append(xtrc);
		result.append(", xtrf: ");
		result.append(xtrf);
		result.append(')');
		return result.toString();
	}

} //ReferenceTypeImpl
