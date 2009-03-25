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

import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DelimType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DirType59;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.FragrefType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.GroupchoiceType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.GroupcompType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.GroupseqType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ImportanceType56;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KwdType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.OperType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.RepsepType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SepType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.StatusType34;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SynnoteType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SynnoterefType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TitleType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TranslateType66;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.VarType;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Groupchoice Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getRepsep <em>Repsep</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getGroupseq <em>Groupseq</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getGroupchoice <em>Groupchoice</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getGroupcomp <em>Groupcomp</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getFragref <em>Fragref</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getKwd <em>Kwd</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getVar <em>Var</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getDelim <em>Delim</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getOper <em>Oper</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getSep <em>Sep</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getSynnote <em>Synnote</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getSynnoteref <em>Synnoteref</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getAudience <em>Audience</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getBase <em>Base</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getClass_ <em>Class</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getConref <em>Conref</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getDir <em>Dir</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getImportance <em>Importance</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getLang <em>Lang</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getOtherprops <em>Otherprops</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getOutputclass <em>Outputclass</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getPlatform <em>Platform</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getProduct <em>Product</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getProps <em>Props</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getRev <em>Rev</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getStatus <em>Status</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getTranslate <em>Translate</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getXtrc <em>Xtrc</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.GroupchoiceTypeImpl#getXtrf <em>Xtrf</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GroupchoiceTypeImpl extends EObjectImpl implements GroupchoiceType {
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
	 * The cached value of the '{@link #getRepsep() <em>Repsep</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRepsep()
	 * @generated
	 * @ordered
	 */
	protected RepsepType repsep;

	/**
	 * The cached value of the '{@link #getGroup() <em>Group</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroup()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap group;

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
	protected static final Object CLASS_EDEFAULT = "+ topic/figgroup pr-d/groupchoice ";

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
	protected static final DirType59 DIR_EDEFAULT = DirType59.LTR;

	/**
	 * The cached value of the '{@link #getDir() <em>Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDir()
	 * @generated
	 * @ordered
	 */
	protected DirType59 dir = DIR_EDEFAULT;

	/**
	 * This is true if the Dir attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean dirESet;

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
	protected static final ImportanceType56 IMPORTANCE_EDEFAULT = ImportanceType56.OPTIONAL;

	/**
	 * The cached value of the '{@link #getImportance() <em>Importance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImportance()
	 * @generated
	 * @ordered
	 */
	protected ImportanceType56 importance = IMPORTANCE_EDEFAULT;

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
	protected static final StatusType34 STATUS_EDEFAULT = StatusType34.NEW;

	/**
	 * The cached value of the '{@link #getStatus() <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus()
	 * @generated
	 * @ordered
	 */
	protected StatusType34 status = STATUS_EDEFAULT;

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
	protected static final TranslateType66 TRANSLATE_EDEFAULT = TranslateType66.YES;

	/**
	 * The cached value of the '{@link #getTranslate() <em>Translate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTranslate()
	 * @generated
	 * @ordered
	 */
	protected TranslateType66 translate = TRANSLATE_EDEFAULT;

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
	protected GroupchoiceTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return KbdataPackage.eINSTANCE.getGroupchoiceType();
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KbdataPackage.GROUPCHOICE_TYPE__TITLE, oldTitle, newTitle);
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
				msgs = ((InternalEObject)title).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.GROUPCHOICE_TYPE__TITLE, null, msgs);
			if (newTitle != null)
				msgs = ((InternalEObject)newTitle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.GROUPCHOICE_TYPE__TITLE, null, msgs);
			msgs = basicSetTitle(newTitle, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.GROUPCHOICE_TYPE__TITLE, newTitle, newTitle));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RepsepType getRepsep() {
		return repsep;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRepsep(RepsepType newRepsep, NotificationChain msgs) {
		RepsepType oldRepsep = repsep;
		repsep = newRepsep;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KbdataPackage.GROUPCHOICE_TYPE__REPSEP, oldRepsep, newRepsep);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRepsep(RepsepType newRepsep) {
		if (newRepsep != repsep) {
			NotificationChain msgs = null;
			if (repsep != null)
				msgs = ((InternalEObject)repsep).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.GROUPCHOICE_TYPE__REPSEP, null, msgs);
			if (newRepsep != null)
				msgs = ((InternalEObject)newRepsep).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.GROUPCHOICE_TYPE__REPSEP, null, msgs);
			msgs = basicSetRepsep(newRepsep, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.GROUPCHOICE_TYPE__REPSEP, newRepsep, newRepsep));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, KbdataPackage.GROUPCHOICE_TYPE__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GroupseqType> getGroupseq() {
		return getGroup().list(KbdataPackage.eINSTANCE.getGroupchoiceType_Groupseq());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GroupchoiceType> getGroupchoice() {
		return getGroup().list(KbdataPackage.eINSTANCE.getGroupchoiceType_Groupchoice());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GroupcompType> getGroupcomp() {
		return getGroup().list(KbdataPackage.eINSTANCE.getGroupchoiceType_Groupcomp());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FragrefType> getFragref() {
		return getGroup().list(KbdataPackage.eINSTANCE.getGroupchoiceType_Fragref());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<KwdType> getKwd() {
		return getGroup().list(KbdataPackage.eINSTANCE.getGroupchoiceType_Kwd());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<VarType> getVar() {
		return getGroup().list(KbdataPackage.eINSTANCE.getGroupchoiceType_Var());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DelimType> getDelim() {
		return getGroup().list(KbdataPackage.eINSTANCE.getGroupchoiceType_Delim());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<OperType> getOper() {
		return getGroup().list(KbdataPackage.eINSTANCE.getGroupchoiceType_Oper());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SepType> getSep() {
		return getGroup().list(KbdataPackage.eINSTANCE.getGroupchoiceType_Sep());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SynnoteType> getSynnote() {
		return getGroup().list(KbdataPackage.eINSTANCE.getGroupchoiceType_Synnote());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SynnoterefType> getSynnoteref() {
		return getGroup().list(KbdataPackage.eINSTANCE.getGroupchoiceType_Synnoteref());
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.GROUPCHOICE_TYPE__AUDIENCE, oldAudience, audience));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.GROUPCHOICE_TYPE__BASE, oldBase, base));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.GROUPCHOICE_TYPE__CLASS, oldClass, class_, !oldClassESet));
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.GROUPCHOICE_TYPE__CLASS, oldClass, CLASS_EDEFAULT, oldClassESet));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.GROUPCHOICE_TYPE__CONREF, oldConref, conref));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DirType59 getDir() {
		return dir;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDir(DirType59 newDir) {
		DirType59 oldDir = dir;
		dir = newDir == null ? DIR_EDEFAULT : newDir;
		boolean oldDirESet = dirESet;
		dirESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.GROUPCHOICE_TYPE__DIR, oldDir, dir, !oldDirESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetDir() {
		DirType59 oldDir = dir;
		boolean oldDirESet = dirESet;
		dir = DIR_EDEFAULT;
		dirESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.GROUPCHOICE_TYPE__DIR, oldDir, DIR_EDEFAULT, oldDirESet));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.GROUPCHOICE_TYPE__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImportanceType56 getImportance() {
		return importance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImportance(ImportanceType56 newImportance) {
		ImportanceType56 oldImportance = importance;
		importance = newImportance == null ? IMPORTANCE_EDEFAULT : newImportance;
		boolean oldImportanceESet = importanceESet;
		importanceESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.GROUPCHOICE_TYPE__IMPORTANCE, oldImportance, importance, !oldImportanceESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetImportance() {
		ImportanceType56 oldImportance = importance;
		boolean oldImportanceESet = importanceESet;
		importance = IMPORTANCE_EDEFAULT;
		importanceESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.GROUPCHOICE_TYPE__IMPORTANCE, oldImportance, IMPORTANCE_EDEFAULT, oldImportanceESet));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.GROUPCHOICE_TYPE__LANG, oldLang, lang));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.GROUPCHOICE_TYPE__OTHERPROPS, oldOtherprops, otherprops));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.GROUPCHOICE_TYPE__OUTPUTCLASS, oldOutputclass, outputclass));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.GROUPCHOICE_TYPE__PLATFORM, oldPlatform, platform));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.GROUPCHOICE_TYPE__PRODUCT, oldProduct, product));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.GROUPCHOICE_TYPE__PROPS, oldProps, props));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.GROUPCHOICE_TYPE__REV, oldRev, rev));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatusType34 getStatus() {
		return status;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStatus(StatusType34 newStatus) {
		StatusType34 oldStatus = status;
		status = newStatus == null ? STATUS_EDEFAULT : newStatus;
		boolean oldStatusESet = statusESet;
		statusESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.GROUPCHOICE_TYPE__STATUS, oldStatus, status, !oldStatusESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetStatus() {
		StatusType34 oldStatus = status;
		boolean oldStatusESet = statusESet;
		status = STATUS_EDEFAULT;
		statusESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.GROUPCHOICE_TYPE__STATUS, oldStatus, STATUS_EDEFAULT, oldStatusESet));
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
	public TranslateType66 getTranslate() {
		return translate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTranslate(TranslateType66 newTranslate) {
		TranslateType66 oldTranslate = translate;
		translate = newTranslate == null ? TRANSLATE_EDEFAULT : newTranslate;
		boolean oldTranslateESet = translateESet;
		translateESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.GROUPCHOICE_TYPE__TRANSLATE, oldTranslate, translate, !oldTranslateESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetTranslate() {
		TranslateType66 oldTranslate = translate;
		boolean oldTranslateESet = translateESet;
		translate = TRANSLATE_EDEFAULT;
		translateESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.GROUPCHOICE_TYPE__TRANSLATE, oldTranslate, TRANSLATE_EDEFAULT, oldTranslateESet));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.GROUPCHOICE_TYPE__XTRC, oldXtrc, xtrc));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.GROUPCHOICE_TYPE__XTRF, oldXtrf, xtrf));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case KbdataPackage.GROUPCHOICE_TYPE__TITLE:
				return basicSetTitle(null, msgs);
			case KbdataPackage.GROUPCHOICE_TYPE__REPSEP:
				return basicSetRepsep(null, msgs);
			case KbdataPackage.GROUPCHOICE_TYPE__GROUP:
				return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
			case KbdataPackage.GROUPCHOICE_TYPE__GROUPSEQ:
				return ((InternalEList<?>)getGroupseq()).basicRemove(otherEnd, msgs);
			case KbdataPackage.GROUPCHOICE_TYPE__GROUPCHOICE:
				return ((InternalEList<?>)getGroupchoice()).basicRemove(otherEnd, msgs);
			case KbdataPackage.GROUPCHOICE_TYPE__GROUPCOMP:
				return ((InternalEList<?>)getGroupcomp()).basicRemove(otherEnd, msgs);
			case KbdataPackage.GROUPCHOICE_TYPE__FRAGREF:
				return ((InternalEList<?>)getFragref()).basicRemove(otherEnd, msgs);
			case KbdataPackage.GROUPCHOICE_TYPE__KWD:
				return ((InternalEList<?>)getKwd()).basicRemove(otherEnd, msgs);
			case KbdataPackage.GROUPCHOICE_TYPE__VAR:
				return ((InternalEList<?>)getVar()).basicRemove(otherEnd, msgs);
			case KbdataPackage.GROUPCHOICE_TYPE__DELIM:
				return ((InternalEList<?>)getDelim()).basicRemove(otherEnd, msgs);
			case KbdataPackage.GROUPCHOICE_TYPE__OPER:
				return ((InternalEList<?>)getOper()).basicRemove(otherEnd, msgs);
			case KbdataPackage.GROUPCHOICE_TYPE__SEP:
				return ((InternalEList<?>)getSep()).basicRemove(otherEnd, msgs);
			case KbdataPackage.GROUPCHOICE_TYPE__SYNNOTE:
				return ((InternalEList<?>)getSynnote()).basicRemove(otherEnd, msgs);
			case KbdataPackage.GROUPCHOICE_TYPE__SYNNOTEREF:
				return ((InternalEList<?>)getSynnoteref()).basicRemove(otherEnd, msgs);
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
			case KbdataPackage.GROUPCHOICE_TYPE__TITLE:
				return getTitle();
			case KbdataPackage.GROUPCHOICE_TYPE__REPSEP:
				return getRepsep();
			case KbdataPackage.GROUPCHOICE_TYPE__GROUP:
				if (coreType) return getGroup();
				return ((FeatureMap.Internal)getGroup()).getWrapper();
			case KbdataPackage.GROUPCHOICE_TYPE__GROUPSEQ:
				return getGroupseq();
			case KbdataPackage.GROUPCHOICE_TYPE__GROUPCHOICE:
				return getGroupchoice();
			case KbdataPackage.GROUPCHOICE_TYPE__GROUPCOMP:
				return getGroupcomp();
			case KbdataPackage.GROUPCHOICE_TYPE__FRAGREF:
				return getFragref();
			case KbdataPackage.GROUPCHOICE_TYPE__KWD:
				return getKwd();
			case KbdataPackage.GROUPCHOICE_TYPE__VAR:
				return getVar();
			case KbdataPackage.GROUPCHOICE_TYPE__DELIM:
				return getDelim();
			case KbdataPackage.GROUPCHOICE_TYPE__OPER:
				return getOper();
			case KbdataPackage.GROUPCHOICE_TYPE__SEP:
				return getSep();
			case KbdataPackage.GROUPCHOICE_TYPE__SYNNOTE:
				return getSynnote();
			case KbdataPackage.GROUPCHOICE_TYPE__SYNNOTEREF:
				return getSynnoteref();
			case KbdataPackage.GROUPCHOICE_TYPE__AUDIENCE:
				return getAudience();
			case KbdataPackage.GROUPCHOICE_TYPE__BASE:
				return getBase();
			case KbdataPackage.GROUPCHOICE_TYPE__CLASS:
				return getClass_();
			case KbdataPackage.GROUPCHOICE_TYPE__CONREF:
				return getConref();
			case KbdataPackage.GROUPCHOICE_TYPE__DIR:
				return getDir();
			case KbdataPackage.GROUPCHOICE_TYPE__ID:
				return getId();
			case KbdataPackage.GROUPCHOICE_TYPE__IMPORTANCE:
				return getImportance();
			case KbdataPackage.GROUPCHOICE_TYPE__LANG:
				return getLang();
			case KbdataPackage.GROUPCHOICE_TYPE__OTHERPROPS:
				return getOtherprops();
			case KbdataPackage.GROUPCHOICE_TYPE__OUTPUTCLASS:
				return getOutputclass();
			case KbdataPackage.GROUPCHOICE_TYPE__PLATFORM:
				return getPlatform();
			case KbdataPackage.GROUPCHOICE_TYPE__PRODUCT:
				return getProduct();
			case KbdataPackage.GROUPCHOICE_TYPE__PROPS:
				return getProps();
			case KbdataPackage.GROUPCHOICE_TYPE__REV:
				return getRev();
			case KbdataPackage.GROUPCHOICE_TYPE__STATUS:
				return getStatus();
			case KbdataPackage.GROUPCHOICE_TYPE__TRANSLATE:
				return getTranslate();
			case KbdataPackage.GROUPCHOICE_TYPE__XTRC:
				return getXtrc();
			case KbdataPackage.GROUPCHOICE_TYPE__XTRF:
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
			case KbdataPackage.GROUPCHOICE_TYPE__TITLE:
				setTitle((TitleType)newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__REPSEP:
				setRepsep((RepsepType)newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__GROUP:
				((FeatureMap.Internal)getGroup()).set(newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__GROUPSEQ:
				getGroupseq().clear();
				getGroupseq().addAll((Collection<? extends GroupseqType>)newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__GROUPCHOICE:
				getGroupchoice().clear();
				getGroupchoice().addAll((Collection<? extends GroupchoiceType>)newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__GROUPCOMP:
				getGroupcomp().clear();
				getGroupcomp().addAll((Collection<? extends GroupcompType>)newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__FRAGREF:
				getFragref().clear();
				getFragref().addAll((Collection<? extends FragrefType>)newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__KWD:
				getKwd().clear();
				getKwd().addAll((Collection<? extends KwdType>)newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__VAR:
				getVar().clear();
				getVar().addAll((Collection<? extends VarType>)newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__DELIM:
				getDelim().clear();
				getDelim().addAll((Collection<? extends DelimType>)newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__OPER:
				getOper().clear();
				getOper().addAll((Collection<? extends OperType>)newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__SEP:
				getSep().clear();
				getSep().addAll((Collection<? extends SepType>)newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__SYNNOTE:
				getSynnote().clear();
				getSynnote().addAll((Collection<? extends SynnoteType>)newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__SYNNOTEREF:
				getSynnoteref().clear();
				getSynnoteref().addAll((Collection<? extends SynnoterefType>)newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__AUDIENCE:
				setAudience(newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__BASE:
				setBase(newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__CLASS:
				setClass(newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__CONREF:
				setConref(newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__DIR:
				setDir((DirType59)newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__ID:
				setId((String)newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__IMPORTANCE:
				setImportance((ImportanceType56)newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__LANG:
				setLang((String)newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__OTHERPROPS:
				setOtherprops(newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__OUTPUTCLASS:
				setOutputclass(newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__PLATFORM:
				setPlatform(newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__PRODUCT:
				setProduct(newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__PROPS:
				setProps(newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__REV:
				setRev(newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__STATUS:
				setStatus((StatusType34)newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__TRANSLATE:
				setTranslate((TranslateType66)newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__XTRC:
				setXtrc(newValue);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__XTRF:
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
			case KbdataPackage.GROUPCHOICE_TYPE__TITLE:
				setTitle((TitleType)null);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__REPSEP:
				setRepsep((RepsepType)null);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__GROUP:
				getGroup().clear();
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__GROUPSEQ:
				getGroupseq().clear();
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__GROUPCHOICE:
				getGroupchoice().clear();
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__GROUPCOMP:
				getGroupcomp().clear();
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__FRAGREF:
				getFragref().clear();
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__KWD:
				getKwd().clear();
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__VAR:
				getVar().clear();
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__DELIM:
				getDelim().clear();
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__OPER:
				getOper().clear();
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__SEP:
				getSep().clear();
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__SYNNOTE:
				getSynnote().clear();
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__SYNNOTEREF:
				getSynnoteref().clear();
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__AUDIENCE:
				setAudience(AUDIENCE_EDEFAULT);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__BASE:
				setBase(BASE_EDEFAULT);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__CLASS:
				unsetClass();
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__CONREF:
				setConref(CONREF_EDEFAULT);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__DIR:
				unsetDir();
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__ID:
				setId(ID_EDEFAULT);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__IMPORTANCE:
				unsetImportance();
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__LANG:
				setLang(LANG_EDEFAULT);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__OTHERPROPS:
				setOtherprops(OTHERPROPS_EDEFAULT);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__OUTPUTCLASS:
				setOutputclass(OUTPUTCLASS_EDEFAULT);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__PLATFORM:
				setPlatform(PLATFORM_EDEFAULT);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__PRODUCT:
				setProduct(PRODUCT_EDEFAULT);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__PROPS:
				setProps(PROPS_EDEFAULT);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__REV:
				setRev(REV_EDEFAULT);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__STATUS:
				unsetStatus();
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__TRANSLATE:
				unsetTranslate();
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__XTRC:
				setXtrc(XTRC_EDEFAULT);
				return;
			case KbdataPackage.GROUPCHOICE_TYPE__XTRF:
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
			case KbdataPackage.GROUPCHOICE_TYPE__TITLE:
				return title != null;
			case KbdataPackage.GROUPCHOICE_TYPE__REPSEP:
				return repsep != null;
			case KbdataPackage.GROUPCHOICE_TYPE__GROUP:
				return group != null && !group.isEmpty();
			case KbdataPackage.GROUPCHOICE_TYPE__GROUPSEQ:
				return !getGroupseq().isEmpty();
			case KbdataPackage.GROUPCHOICE_TYPE__GROUPCHOICE:
				return !getGroupchoice().isEmpty();
			case KbdataPackage.GROUPCHOICE_TYPE__GROUPCOMP:
				return !getGroupcomp().isEmpty();
			case KbdataPackage.GROUPCHOICE_TYPE__FRAGREF:
				return !getFragref().isEmpty();
			case KbdataPackage.GROUPCHOICE_TYPE__KWD:
				return !getKwd().isEmpty();
			case KbdataPackage.GROUPCHOICE_TYPE__VAR:
				return !getVar().isEmpty();
			case KbdataPackage.GROUPCHOICE_TYPE__DELIM:
				return !getDelim().isEmpty();
			case KbdataPackage.GROUPCHOICE_TYPE__OPER:
				return !getOper().isEmpty();
			case KbdataPackage.GROUPCHOICE_TYPE__SEP:
				return !getSep().isEmpty();
			case KbdataPackage.GROUPCHOICE_TYPE__SYNNOTE:
				return !getSynnote().isEmpty();
			case KbdataPackage.GROUPCHOICE_TYPE__SYNNOTEREF:
				return !getSynnoteref().isEmpty();
			case KbdataPackage.GROUPCHOICE_TYPE__AUDIENCE:
				return AUDIENCE_EDEFAULT == null ? audience != null : !AUDIENCE_EDEFAULT.equals(audience);
			case KbdataPackage.GROUPCHOICE_TYPE__BASE:
				return BASE_EDEFAULT == null ? base != null : !BASE_EDEFAULT.equals(base);
			case KbdataPackage.GROUPCHOICE_TYPE__CLASS:
				return isSetClass();
			case KbdataPackage.GROUPCHOICE_TYPE__CONREF:
				return CONREF_EDEFAULT == null ? conref != null : !CONREF_EDEFAULT.equals(conref);
			case KbdataPackage.GROUPCHOICE_TYPE__DIR:
				return isSetDir();
			case KbdataPackage.GROUPCHOICE_TYPE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case KbdataPackage.GROUPCHOICE_TYPE__IMPORTANCE:
				return isSetImportance();
			case KbdataPackage.GROUPCHOICE_TYPE__LANG:
				return LANG_EDEFAULT == null ? lang != null : !LANG_EDEFAULT.equals(lang);
			case KbdataPackage.GROUPCHOICE_TYPE__OTHERPROPS:
				return OTHERPROPS_EDEFAULT == null ? otherprops != null : !OTHERPROPS_EDEFAULT.equals(otherprops);
			case KbdataPackage.GROUPCHOICE_TYPE__OUTPUTCLASS:
				return OUTPUTCLASS_EDEFAULT == null ? outputclass != null : !OUTPUTCLASS_EDEFAULT.equals(outputclass);
			case KbdataPackage.GROUPCHOICE_TYPE__PLATFORM:
				return PLATFORM_EDEFAULT == null ? platform != null : !PLATFORM_EDEFAULT.equals(platform);
			case KbdataPackage.GROUPCHOICE_TYPE__PRODUCT:
				return PRODUCT_EDEFAULT == null ? product != null : !PRODUCT_EDEFAULT.equals(product);
			case KbdataPackage.GROUPCHOICE_TYPE__PROPS:
				return PROPS_EDEFAULT == null ? props != null : !PROPS_EDEFAULT.equals(props);
			case KbdataPackage.GROUPCHOICE_TYPE__REV:
				return REV_EDEFAULT == null ? rev != null : !REV_EDEFAULT.equals(rev);
			case KbdataPackage.GROUPCHOICE_TYPE__STATUS:
				return isSetStatus();
			case KbdataPackage.GROUPCHOICE_TYPE__TRANSLATE:
				return isSetTranslate();
			case KbdataPackage.GROUPCHOICE_TYPE__XTRC:
				return XTRC_EDEFAULT == null ? xtrc != null : !XTRC_EDEFAULT.equals(xtrc);
			case KbdataPackage.GROUPCHOICE_TYPE__XTRF:
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
		result.append(" (group: ");
		result.append(group);
		result.append(", audience: ");
		result.append(audience);
		result.append(", base: ");
		result.append(base);
		result.append(", class: ");
		if (classESet) result.append(class_); else result.append("<unset>");
		result.append(", conref: ");
		result.append(conref);
		result.append(", dir: ");
		if (dirESet) result.append(dir); else result.append("<unset>");
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

} //GroupchoiceTypeImpl
