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

import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DirType183;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ImportanceType178;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.StatusType178;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TmType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TmtypeType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TranslateType183;

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
 * An implementation of the model object '<em><b>Tm Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.TmTypeImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.TmTypeImpl#getTm <em>Tm</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.TmTypeImpl#getAudience <em>Audience</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.TmTypeImpl#getBase <em>Base</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.TmTypeImpl#getClass_ <em>Class</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.TmTypeImpl#getConref <em>Conref</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.TmTypeImpl#getDir <em>Dir</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.TmTypeImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.TmTypeImpl#getImportance <em>Importance</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.TmTypeImpl#getLang <em>Lang</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.TmTypeImpl#getOtherprops <em>Otherprops</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.TmTypeImpl#getPlatform <em>Platform</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.TmTypeImpl#getProduct <em>Product</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.TmTypeImpl#getProps <em>Props</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.TmTypeImpl#getRev <em>Rev</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.TmTypeImpl#getStatus <em>Status</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.TmTypeImpl#getTmclass <em>Tmclass</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.TmTypeImpl#getTmowner <em>Tmowner</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.TmTypeImpl#getTmtype <em>Tmtype</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.TmTypeImpl#getTrademark <em>Trademark</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.TmTypeImpl#getTranslate <em>Translate</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.TmTypeImpl#getXtrc <em>Xtrc</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.TmTypeImpl#getXtrf <em>Xtrf</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TmTypeImpl extends EObjectImpl implements TmType {
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
	protected static final Object CLASS_EDEFAULT = "- topic/tm ";

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
	protected static final DirType183 DIR_EDEFAULT = DirType183.LTR;

	/**
	 * The cached value of the '{@link #getDir() <em>Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDir()
	 * @generated
	 * @ordered
	 */
	protected DirType183 dir = DIR_EDEFAULT;

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
	protected static final ImportanceType178 IMPORTANCE_EDEFAULT = ImportanceType178.OBSOLETE;

	/**
	 * The cached value of the '{@link #getImportance() <em>Importance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImportance()
	 * @generated
	 * @ordered
	 */
	protected ImportanceType178 importance = IMPORTANCE_EDEFAULT;

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
	protected static final StatusType178 STATUS_EDEFAULT = StatusType178.NEW;

	/**
	 * The cached value of the '{@link #getStatus() <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus()
	 * @generated
	 * @ordered
	 */
	protected StatusType178 status = STATUS_EDEFAULT;

	/**
	 * This is true if the Status attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean statusESet;

	/**
	 * The default value of the '{@link #getTmclass() <em>Tmclass</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTmclass()
	 * @generated
	 * @ordered
	 */
	protected static final Object TMCLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTmclass() <em>Tmclass</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTmclass()
	 * @generated
	 * @ordered
	 */
	protected Object tmclass = TMCLASS_EDEFAULT;

	/**
	 * The default value of the '{@link #getTmowner() <em>Tmowner</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTmowner()
	 * @generated
	 * @ordered
	 */
	protected static final Object TMOWNER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTmowner() <em>Tmowner</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTmowner()
	 * @generated
	 * @ordered
	 */
	protected Object tmowner = TMOWNER_EDEFAULT;

	/**
	 * The default value of the '{@link #getTmtype() <em>Tmtype</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTmtype()
	 * @generated
	 * @ordered
	 */
	protected static final TmtypeType TMTYPE_EDEFAULT = TmtypeType.TM;

	/**
	 * The cached value of the '{@link #getTmtype() <em>Tmtype</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTmtype()
	 * @generated
	 * @ordered
	 */
	protected TmtypeType tmtype = TMTYPE_EDEFAULT;

	/**
	 * This is true if the Tmtype attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean tmtypeESet;

	/**
	 * The default value of the '{@link #getTrademark() <em>Trademark</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTrademark()
	 * @generated
	 * @ordered
	 */
	protected static final Object TRADEMARK_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTrademark() <em>Trademark</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTrademark()
	 * @generated
	 * @ordered
	 */
	protected Object trademark = TRADEMARK_EDEFAULT;

	/**
	 * The default value of the '{@link #getTranslate() <em>Translate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTranslate()
	 * @generated
	 * @ordered
	 */
	protected static final TranslateType183 TRANSLATE_EDEFAULT = TranslateType183.YES;

	/**
	 * The cached value of the '{@link #getTranslate() <em>Translate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTranslate()
	 * @generated
	 * @ordered
	 */
	protected TranslateType183 translate = TRANSLATE_EDEFAULT;

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
	protected TmTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return KbdataPackage.eINSTANCE.getTmType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, KbdataPackage.TM_TYPE__MIXED);
		}
		return mixed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TmType> getTm() {
		return getMixed().list(KbdataPackage.eINSTANCE.getTmType_Tm());
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.TM_TYPE__AUDIENCE, oldAudience, audience));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.TM_TYPE__BASE, oldBase, base));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.TM_TYPE__CLASS, oldClass, class_, !oldClassESet));
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.TM_TYPE__CLASS, oldClass, CLASS_EDEFAULT, oldClassESet));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.TM_TYPE__CONREF, oldConref, conref));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DirType183 getDir() {
		return dir;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDir(DirType183 newDir) {
		DirType183 oldDir = dir;
		dir = newDir == null ? DIR_EDEFAULT : newDir;
		boolean oldDirESet = dirESet;
		dirESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.TM_TYPE__DIR, oldDir, dir, !oldDirESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetDir() {
		DirType183 oldDir = dir;
		boolean oldDirESet = dirESet;
		dir = DIR_EDEFAULT;
		dirESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.TM_TYPE__DIR, oldDir, DIR_EDEFAULT, oldDirESet));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.TM_TYPE__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImportanceType178 getImportance() {
		return importance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImportance(ImportanceType178 newImportance) {
		ImportanceType178 oldImportance = importance;
		importance = newImportance == null ? IMPORTANCE_EDEFAULT : newImportance;
		boolean oldImportanceESet = importanceESet;
		importanceESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.TM_TYPE__IMPORTANCE, oldImportance, importance, !oldImportanceESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetImportance() {
		ImportanceType178 oldImportance = importance;
		boolean oldImportanceESet = importanceESet;
		importance = IMPORTANCE_EDEFAULT;
		importanceESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.TM_TYPE__IMPORTANCE, oldImportance, IMPORTANCE_EDEFAULT, oldImportanceESet));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.TM_TYPE__LANG, oldLang, lang));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.TM_TYPE__OTHERPROPS, oldOtherprops, otherprops));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.TM_TYPE__PLATFORM, oldPlatform, platform));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.TM_TYPE__PRODUCT, oldProduct, product));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.TM_TYPE__PROPS, oldProps, props));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.TM_TYPE__REV, oldRev, rev));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatusType178 getStatus() {
		return status;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStatus(StatusType178 newStatus) {
		StatusType178 oldStatus = status;
		status = newStatus == null ? STATUS_EDEFAULT : newStatus;
		boolean oldStatusESet = statusESet;
		statusESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.TM_TYPE__STATUS, oldStatus, status, !oldStatusESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetStatus() {
		StatusType178 oldStatus = status;
		boolean oldStatusESet = statusESet;
		status = STATUS_EDEFAULT;
		statusESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.TM_TYPE__STATUS, oldStatus, STATUS_EDEFAULT, oldStatusESet));
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
	public Object getTmclass() {
		return tmclass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTmclass(Object newTmclass) {
		Object oldTmclass = tmclass;
		tmclass = newTmclass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.TM_TYPE__TMCLASS, oldTmclass, tmclass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getTmowner() {
		return tmowner;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTmowner(Object newTmowner) {
		Object oldTmowner = tmowner;
		tmowner = newTmowner;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.TM_TYPE__TMOWNER, oldTmowner, tmowner));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TmtypeType getTmtype() {
		return tmtype;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTmtype(TmtypeType newTmtype) {
		TmtypeType oldTmtype = tmtype;
		tmtype = newTmtype == null ? TMTYPE_EDEFAULT : newTmtype;
		boolean oldTmtypeESet = tmtypeESet;
		tmtypeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.TM_TYPE__TMTYPE, oldTmtype, tmtype, !oldTmtypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetTmtype() {
		TmtypeType oldTmtype = tmtype;
		boolean oldTmtypeESet = tmtypeESet;
		tmtype = TMTYPE_EDEFAULT;
		tmtypeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.TM_TYPE__TMTYPE, oldTmtype, TMTYPE_EDEFAULT, oldTmtypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetTmtype() {
		return tmtypeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getTrademark() {
		return trademark;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTrademark(Object newTrademark) {
		Object oldTrademark = trademark;
		trademark = newTrademark;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.TM_TYPE__TRADEMARK, oldTrademark, trademark));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TranslateType183 getTranslate() {
		return translate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTranslate(TranslateType183 newTranslate) {
		TranslateType183 oldTranslate = translate;
		translate = newTranslate == null ? TRANSLATE_EDEFAULT : newTranslate;
		boolean oldTranslateESet = translateESet;
		translateESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.TM_TYPE__TRANSLATE, oldTranslate, translate, !oldTranslateESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetTranslate() {
		TranslateType183 oldTranslate = translate;
		boolean oldTranslateESet = translateESet;
		translate = TRANSLATE_EDEFAULT;
		translateESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.TM_TYPE__TRANSLATE, oldTranslate, TRANSLATE_EDEFAULT, oldTranslateESet));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.TM_TYPE__XTRC, oldXtrc, xtrc));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.TM_TYPE__XTRF, oldXtrf, xtrf));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case KbdataPackage.TM_TYPE__MIXED:
				return ((InternalEList<?>)getMixed()).basicRemove(otherEnd, msgs);
			case KbdataPackage.TM_TYPE__TM:
				return ((InternalEList<?>)getTm()).basicRemove(otherEnd, msgs);
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
			case KbdataPackage.TM_TYPE__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case KbdataPackage.TM_TYPE__TM:
				return getTm();
			case KbdataPackage.TM_TYPE__AUDIENCE:
				return getAudience();
			case KbdataPackage.TM_TYPE__BASE:
				return getBase();
			case KbdataPackage.TM_TYPE__CLASS:
				return getClass_();
			case KbdataPackage.TM_TYPE__CONREF:
				return getConref();
			case KbdataPackage.TM_TYPE__DIR:
				return getDir();
			case KbdataPackage.TM_TYPE__ID:
				return getId();
			case KbdataPackage.TM_TYPE__IMPORTANCE:
				return getImportance();
			case KbdataPackage.TM_TYPE__LANG:
				return getLang();
			case KbdataPackage.TM_TYPE__OTHERPROPS:
				return getOtherprops();
			case KbdataPackage.TM_TYPE__PLATFORM:
				return getPlatform();
			case KbdataPackage.TM_TYPE__PRODUCT:
				return getProduct();
			case KbdataPackage.TM_TYPE__PROPS:
				return getProps();
			case KbdataPackage.TM_TYPE__REV:
				return getRev();
			case KbdataPackage.TM_TYPE__STATUS:
				return getStatus();
			case KbdataPackage.TM_TYPE__TMCLASS:
				return getTmclass();
			case KbdataPackage.TM_TYPE__TMOWNER:
				return getTmowner();
			case KbdataPackage.TM_TYPE__TMTYPE:
				return getTmtype();
			case KbdataPackage.TM_TYPE__TRADEMARK:
				return getTrademark();
			case KbdataPackage.TM_TYPE__TRANSLATE:
				return getTranslate();
			case KbdataPackage.TM_TYPE__XTRC:
				return getXtrc();
			case KbdataPackage.TM_TYPE__XTRF:
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
			case KbdataPackage.TM_TYPE__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case KbdataPackage.TM_TYPE__TM:
				getTm().clear();
				getTm().addAll((Collection<? extends TmType>)newValue);
				return;
			case KbdataPackage.TM_TYPE__AUDIENCE:
				setAudience(newValue);
				return;
			case KbdataPackage.TM_TYPE__BASE:
				setBase(newValue);
				return;
			case KbdataPackage.TM_TYPE__CLASS:
				setClass(newValue);
				return;
			case KbdataPackage.TM_TYPE__CONREF:
				setConref(newValue);
				return;
			case KbdataPackage.TM_TYPE__DIR:
				setDir((DirType183)newValue);
				return;
			case KbdataPackage.TM_TYPE__ID:
				setId((String)newValue);
				return;
			case KbdataPackage.TM_TYPE__IMPORTANCE:
				setImportance((ImportanceType178)newValue);
				return;
			case KbdataPackage.TM_TYPE__LANG:
				setLang((String)newValue);
				return;
			case KbdataPackage.TM_TYPE__OTHERPROPS:
				setOtherprops(newValue);
				return;
			case KbdataPackage.TM_TYPE__PLATFORM:
				setPlatform(newValue);
				return;
			case KbdataPackage.TM_TYPE__PRODUCT:
				setProduct(newValue);
				return;
			case KbdataPackage.TM_TYPE__PROPS:
				setProps(newValue);
				return;
			case KbdataPackage.TM_TYPE__REV:
				setRev(newValue);
				return;
			case KbdataPackage.TM_TYPE__STATUS:
				setStatus((StatusType178)newValue);
				return;
			case KbdataPackage.TM_TYPE__TMCLASS:
				setTmclass(newValue);
				return;
			case KbdataPackage.TM_TYPE__TMOWNER:
				setTmowner(newValue);
				return;
			case KbdataPackage.TM_TYPE__TMTYPE:
				setTmtype((TmtypeType)newValue);
				return;
			case KbdataPackage.TM_TYPE__TRADEMARK:
				setTrademark(newValue);
				return;
			case KbdataPackage.TM_TYPE__TRANSLATE:
				setTranslate((TranslateType183)newValue);
				return;
			case KbdataPackage.TM_TYPE__XTRC:
				setXtrc(newValue);
				return;
			case KbdataPackage.TM_TYPE__XTRF:
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
			case KbdataPackage.TM_TYPE__MIXED:
				getMixed().clear();
				return;
			case KbdataPackage.TM_TYPE__TM:
				getTm().clear();
				return;
			case KbdataPackage.TM_TYPE__AUDIENCE:
				setAudience(AUDIENCE_EDEFAULT);
				return;
			case KbdataPackage.TM_TYPE__BASE:
				setBase(BASE_EDEFAULT);
				return;
			case KbdataPackage.TM_TYPE__CLASS:
				unsetClass();
				return;
			case KbdataPackage.TM_TYPE__CONREF:
				setConref(CONREF_EDEFAULT);
				return;
			case KbdataPackage.TM_TYPE__DIR:
				unsetDir();
				return;
			case KbdataPackage.TM_TYPE__ID:
				setId(ID_EDEFAULT);
				return;
			case KbdataPackage.TM_TYPE__IMPORTANCE:
				unsetImportance();
				return;
			case KbdataPackage.TM_TYPE__LANG:
				setLang(LANG_EDEFAULT);
				return;
			case KbdataPackage.TM_TYPE__OTHERPROPS:
				setOtherprops(OTHERPROPS_EDEFAULT);
				return;
			case KbdataPackage.TM_TYPE__PLATFORM:
				setPlatform(PLATFORM_EDEFAULT);
				return;
			case KbdataPackage.TM_TYPE__PRODUCT:
				setProduct(PRODUCT_EDEFAULT);
				return;
			case KbdataPackage.TM_TYPE__PROPS:
				setProps(PROPS_EDEFAULT);
				return;
			case KbdataPackage.TM_TYPE__REV:
				setRev(REV_EDEFAULT);
				return;
			case KbdataPackage.TM_TYPE__STATUS:
				unsetStatus();
				return;
			case KbdataPackage.TM_TYPE__TMCLASS:
				setTmclass(TMCLASS_EDEFAULT);
				return;
			case KbdataPackage.TM_TYPE__TMOWNER:
				setTmowner(TMOWNER_EDEFAULT);
				return;
			case KbdataPackage.TM_TYPE__TMTYPE:
				unsetTmtype();
				return;
			case KbdataPackage.TM_TYPE__TRADEMARK:
				setTrademark(TRADEMARK_EDEFAULT);
				return;
			case KbdataPackage.TM_TYPE__TRANSLATE:
				unsetTranslate();
				return;
			case KbdataPackage.TM_TYPE__XTRC:
				setXtrc(XTRC_EDEFAULT);
				return;
			case KbdataPackage.TM_TYPE__XTRF:
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
			case KbdataPackage.TM_TYPE__MIXED:
				return mixed != null && !mixed.isEmpty();
			case KbdataPackage.TM_TYPE__TM:
				return !getTm().isEmpty();
			case KbdataPackage.TM_TYPE__AUDIENCE:
				return AUDIENCE_EDEFAULT == null ? audience != null : !AUDIENCE_EDEFAULT.equals(audience);
			case KbdataPackage.TM_TYPE__BASE:
				return BASE_EDEFAULT == null ? base != null : !BASE_EDEFAULT.equals(base);
			case KbdataPackage.TM_TYPE__CLASS:
				return isSetClass();
			case KbdataPackage.TM_TYPE__CONREF:
				return CONREF_EDEFAULT == null ? conref != null : !CONREF_EDEFAULT.equals(conref);
			case KbdataPackage.TM_TYPE__DIR:
				return isSetDir();
			case KbdataPackage.TM_TYPE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case KbdataPackage.TM_TYPE__IMPORTANCE:
				return isSetImportance();
			case KbdataPackage.TM_TYPE__LANG:
				return LANG_EDEFAULT == null ? lang != null : !LANG_EDEFAULT.equals(lang);
			case KbdataPackage.TM_TYPE__OTHERPROPS:
				return OTHERPROPS_EDEFAULT == null ? otherprops != null : !OTHERPROPS_EDEFAULT.equals(otherprops);
			case KbdataPackage.TM_TYPE__PLATFORM:
				return PLATFORM_EDEFAULT == null ? platform != null : !PLATFORM_EDEFAULT.equals(platform);
			case KbdataPackage.TM_TYPE__PRODUCT:
				return PRODUCT_EDEFAULT == null ? product != null : !PRODUCT_EDEFAULT.equals(product);
			case KbdataPackage.TM_TYPE__PROPS:
				return PROPS_EDEFAULT == null ? props != null : !PROPS_EDEFAULT.equals(props);
			case KbdataPackage.TM_TYPE__REV:
				return REV_EDEFAULT == null ? rev != null : !REV_EDEFAULT.equals(rev);
			case KbdataPackage.TM_TYPE__STATUS:
				return isSetStatus();
			case KbdataPackage.TM_TYPE__TMCLASS:
				return TMCLASS_EDEFAULT == null ? tmclass != null : !TMCLASS_EDEFAULT.equals(tmclass);
			case KbdataPackage.TM_TYPE__TMOWNER:
				return TMOWNER_EDEFAULT == null ? tmowner != null : !TMOWNER_EDEFAULT.equals(tmowner);
			case KbdataPackage.TM_TYPE__TMTYPE:
				return isSetTmtype();
			case KbdataPackage.TM_TYPE__TRADEMARK:
				return TRADEMARK_EDEFAULT == null ? trademark != null : !TRADEMARK_EDEFAULT.equals(trademark);
			case KbdataPackage.TM_TYPE__TRANSLATE:
				return isSetTranslate();
			case KbdataPackage.TM_TYPE__XTRC:
				return XTRC_EDEFAULT == null ? xtrc != null : !XTRC_EDEFAULT.equals(xtrc);
			case KbdataPackage.TM_TYPE__XTRF:
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
		result.append(" (mixed: ");
		result.append(mixed);
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
		result.append(", tmclass: ");
		result.append(tmclass);
		result.append(", tmowner: ");
		result.append(tmowner);
		result.append(", tmtype: ");
		if (tmtypeESet) result.append(tmtype); else result.append("<unset>");
		result.append(", trademark: ");
		result.append(trademark);
		result.append(", translate: ");
		if (translateESet) result.append(translate); else result.append("<unset>");
		result.append(", xtrc: ");
		result.append(xtrc);
		result.append(", xtrf: ");
		result.append(xtrf);
		result.append(')');
		return result.toString();
	}

} //TmTypeImpl
