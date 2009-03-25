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

import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CallType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ClassType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CommentType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DeviceType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DirType136;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.FiletypeType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ImportanceType137;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.InheritanceType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataKeywordType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataKeywordsType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPlatformType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.LocalType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MacroType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MemberType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MethodType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ParameterType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.StatusType101;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TranslateType154;

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
 * An implementation of the model object '<em><b>Keywords Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getCall <em>Call</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getClass_ <em>Class</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getComment <em>Comment</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getInheritance <em>Inheritance</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getMethod <em>Method</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getKbdataKeyword <em>Kbdata Keyword</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getLocal <em>Local</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getMacro <em>Macro</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getMember <em>Member</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getParameter <em>Parameter</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getKbdataPlatform <em>Kbdata Platform</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getDevice <em>Device</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getFiletype <em>Filetype</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getAudience <em>Audience</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getBase <em>Base</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getClass1 <em>Class1</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getConref <em>Conref</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getDir <em>Dir</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getImportance <em>Importance</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getLang <em>Lang</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getOtherprops <em>Otherprops</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getOutputclass <em>Outputclass</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getPlatform <em>Platform</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getProduct <em>Product</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getProps <em>Props</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getRev <em>Rev</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getStatus <em>Status</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getTranslate <em>Translate</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getXtrc <em>Xtrc</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsTypeImpl#getXtrf <em>Xtrf</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class KbdataKeywordsTypeImpl extends EObjectImpl implements KbdataKeywordsType {
	/**
	 * The cached value of the '{@link #getCall() <em>Call</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCall()
	 * @generated
	 * @ordered
	 */
	protected CallType call;

	/**
	 * The cached value of the '{@link #getClass_() <em>Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClass_()
	 * @generated
	 * @ordered
	 */
	protected ClassType class_;

	/**
	 * The cached value of the '{@link #getComment() <em>Comment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected CommentType comment;

	/**
	 * The cached value of the '{@link #getInheritance() <em>Inheritance</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInheritance()
	 * @generated
	 * @ordered
	 */
	protected InheritanceType inheritance;

	/**
	 * The cached value of the '{@link #getMethod() <em>Method</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethod()
	 * @generated
	 * @ordered
	 */
	protected MethodType method;

	/**
	 * The cached value of the '{@link #getKbdataKeyword() <em>Kbdata Keyword</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKbdataKeyword()
	 * @generated
	 * @ordered
	 */
	protected KbdataKeywordType kbdataKeyword;

	/**
	 * The cached value of the '{@link #getLocal() <em>Local</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocal()
	 * @generated
	 * @ordered
	 */
	protected LocalType local;

	/**
	 * The cached value of the '{@link #getMacro() <em>Macro</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMacro()
	 * @generated
	 * @ordered
	 */
	protected MacroType macro;

	/**
	 * The cached value of the '{@link #getMember() <em>Member</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMember()
	 * @generated
	 * @ordered
	 */
	protected MemberType member;

	/**
	 * The cached value of the '{@link #getParameter() <em>Parameter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameter()
	 * @generated
	 * @ordered
	 */
	protected ParameterType parameter;

	/**
	 * The cached value of the '{@link #getKbdataPlatform() <em>Kbdata Platform</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKbdataPlatform()
	 * @generated
	 * @ordered
	 */
	protected EList<KbdataPlatformType> kbdataPlatform;

	/**
	 * The cached value of the '{@link #getDevice() <em>Device</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDevice()
	 * @generated
	 * @ordered
	 */
	protected EList<DeviceType> device;

	/**
	 * The cached value of the '{@link #getFiletype() <em>Filetype</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFiletype()
	 * @generated
	 * @ordered
	 */
	protected EList<FiletypeType> filetype;

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
	 * The default value of the '{@link #getClass1() <em>Class1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClass1()
	 * @generated
	 * @ordered
	 */
	protected static final Object CLASS1_EDEFAULT = "- topic/p reference/P kbdata/kbdataKeywords ";

	/**
	 * The cached value of the '{@link #getClass1() <em>Class1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClass1()
	 * @generated
	 * @ordered
	 */
	protected Object class1 = CLASS1_EDEFAULT;

	/**
	 * This is true if the Class1 attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean class1ESet;

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
	protected static final DirType136 DIR_EDEFAULT = DirType136.LTR;

	/**
	 * The cached value of the '{@link #getDir() <em>Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDir()
	 * @generated
	 * @ordered
	 */
	protected DirType136 dir = DIR_EDEFAULT;

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
	protected static final ImportanceType137 IMPORTANCE_EDEFAULT = ImportanceType137.OBSOLETE;

	/**
	 * The cached value of the '{@link #getImportance() <em>Importance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImportance()
	 * @generated
	 * @ordered
	 */
	protected ImportanceType137 importance = IMPORTANCE_EDEFAULT;

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
	protected static final StatusType101 STATUS_EDEFAULT = StatusType101.NEW;

	/**
	 * The cached value of the '{@link #getStatus() <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus()
	 * @generated
	 * @ordered
	 */
	protected StatusType101 status = STATUS_EDEFAULT;

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
	protected static final TranslateType154 TRANSLATE_EDEFAULT = TranslateType154.YES;

	/**
	 * The cached value of the '{@link #getTranslate() <em>Translate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTranslate()
	 * @generated
	 * @ordered
	 */
	protected TranslateType154 translate = TRANSLATE_EDEFAULT;

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
	protected KbdataKeywordsTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return KbdataPackage.eINSTANCE.getKbdataKeywordsType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CallType getCall() {
		return call;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCall(CallType newCall, NotificationChain msgs) {
		CallType oldCall = call;
		call = newCall;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__CALL, oldCall, newCall);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCall(CallType newCall) {
		if (newCall != call) {
			NotificationChain msgs = null;
			if (call != null)
				msgs = ((InternalEObject)call).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.KBDATA_KEYWORDS_TYPE__CALL, null, msgs);
			if (newCall != null)
				msgs = ((InternalEObject)newCall).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.KBDATA_KEYWORDS_TYPE__CALL, null, msgs);
			msgs = basicSetCall(newCall, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__CALL, newCall, newCall));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ClassType getClass_() {
		return class_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetClass(ClassType newClass, NotificationChain msgs) {
		ClassType oldClass = class_;
		class_ = newClass;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__CLASS, oldClass, newClass);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClass(ClassType newClass) {
		if (newClass != class_) {
			NotificationChain msgs = null;
			if (class_ != null)
				msgs = ((InternalEObject)class_).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.KBDATA_KEYWORDS_TYPE__CLASS, null, msgs);
			if (newClass != null)
				msgs = ((InternalEObject)newClass).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.KBDATA_KEYWORDS_TYPE__CLASS, null, msgs);
			msgs = basicSetClass(newClass, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__CLASS, newClass, newClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CommentType getComment() {
		return comment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetComment(CommentType newComment, NotificationChain msgs) {
		CommentType oldComment = comment;
		comment = newComment;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__COMMENT, oldComment, newComment);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComment(CommentType newComment) {
		if (newComment != comment) {
			NotificationChain msgs = null;
			if (comment != null)
				msgs = ((InternalEObject)comment).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.KBDATA_KEYWORDS_TYPE__COMMENT, null, msgs);
			if (newComment != null)
				msgs = ((InternalEObject)newComment).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.KBDATA_KEYWORDS_TYPE__COMMENT, null, msgs);
			msgs = basicSetComment(newComment, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__COMMENT, newComment, newComment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InheritanceType getInheritance() {
		return inheritance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInheritance(InheritanceType newInheritance, NotificationChain msgs) {
		InheritanceType oldInheritance = inheritance;
		inheritance = newInheritance;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__INHERITANCE, oldInheritance, newInheritance);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInheritance(InheritanceType newInheritance) {
		if (newInheritance != inheritance) {
			NotificationChain msgs = null;
			if (inheritance != null)
				msgs = ((InternalEObject)inheritance).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.KBDATA_KEYWORDS_TYPE__INHERITANCE, null, msgs);
			if (newInheritance != null)
				msgs = ((InternalEObject)newInheritance).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.KBDATA_KEYWORDS_TYPE__INHERITANCE, null, msgs);
			msgs = basicSetInheritance(newInheritance, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__INHERITANCE, newInheritance, newInheritance));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MethodType getMethod() {
		return method;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMethod(MethodType newMethod, NotificationChain msgs) {
		MethodType oldMethod = method;
		method = newMethod;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__METHOD, oldMethod, newMethod);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMethod(MethodType newMethod) {
		if (newMethod != method) {
			NotificationChain msgs = null;
			if (method != null)
				msgs = ((InternalEObject)method).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.KBDATA_KEYWORDS_TYPE__METHOD, null, msgs);
			if (newMethod != null)
				msgs = ((InternalEObject)newMethod).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.KBDATA_KEYWORDS_TYPE__METHOD, null, msgs);
			msgs = basicSetMethod(newMethod, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__METHOD, newMethod, newMethod));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public KbdataKeywordType getKbdataKeyword() {
		return kbdataKeyword;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetKbdataKeyword(KbdataKeywordType newKbdataKeyword, NotificationChain msgs) {
		KbdataKeywordType oldKbdataKeyword = kbdataKeyword;
		kbdataKeyword = newKbdataKeyword;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__KBDATA_KEYWORD, oldKbdataKeyword, newKbdataKeyword);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKbdataKeyword(KbdataKeywordType newKbdataKeyword) {
		if (newKbdataKeyword != kbdataKeyword) {
			NotificationChain msgs = null;
			if (kbdataKeyword != null)
				msgs = ((InternalEObject)kbdataKeyword).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.KBDATA_KEYWORDS_TYPE__KBDATA_KEYWORD, null, msgs);
			if (newKbdataKeyword != null)
				msgs = ((InternalEObject)newKbdataKeyword).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.KBDATA_KEYWORDS_TYPE__KBDATA_KEYWORD, null, msgs);
			msgs = basicSetKbdataKeyword(newKbdataKeyword, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__KBDATA_KEYWORD, newKbdataKeyword, newKbdataKeyword));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalType getLocal() {
		return local;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLocal(LocalType newLocal, NotificationChain msgs) {
		LocalType oldLocal = local;
		local = newLocal;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__LOCAL, oldLocal, newLocal);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocal(LocalType newLocal) {
		if (newLocal != local) {
			NotificationChain msgs = null;
			if (local != null)
				msgs = ((InternalEObject)local).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.KBDATA_KEYWORDS_TYPE__LOCAL, null, msgs);
			if (newLocal != null)
				msgs = ((InternalEObject)newLocal).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.KBDATA_KEYWORDS_TYPE__LOCAL, null, msgs);
			msgs = basicSetLocal(newLocal, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__LOCAL, newLocal, newLocal));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MacroType getMacro() {
		return macro;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMacro(MacroType newMacro, NotificationChain msgs) {
		MacroType oldMacro = macro;
		macro = newMacro;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__MACRO, oldMacro, newMacro);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMacro(MacroType newMacro) {
		if (newMacro != macro) {
			NotificationChain msgs = null;
			if (macro != null)
				msgs = ((InternalEObject)macro).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.KBDATA_KEYWORDS_TYPE__MACRO, null, msgs);
			if (newMacro != null)
				msgs = ((InternalEObject)newMacro).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.KBDATA_KEYWORDS_TYPE__MACRO, null, msgs);
			msgs = basicSetMacro(newMacro, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__MACRO, newMacro, newMacro));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MemberType getMember() {
		return member;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMember(MemberType newMember, NotificationChain msgs) {
		MemberType oldMember = member;
		member = newMember;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__MEMBER, oldMember, newMember);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMember(MemberType newMember) {
		if (newMember != member) {
			NotificationChain msgs = null;
			if (member != null)
				msgs = ((InternalEObject)member).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.KBDATA_KEYWORDS_TYPE__MEMBER, null, msgs);
			if (newMember != null)
				msgs = ((InternalEObject)newMember).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.KBDATA_KEYWORDS_TYPE__MEMBER, null, msgs);
			msgs = basicSetMember(newMember, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__MEMBER, newMember, newMember));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParameterType getParameter() {
		return parameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParameter(ParameterType newParameter, NotificationChain msgs) {
		ParameterType oldParameter = parameter;
		parameter = newParameter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__PARAMETER, oldParameter, newParameter);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParameter(ParameterType newParameter) {
		if (newParameter != parameter) {
			NotificationChain msgs = null;
			if (parameter != null)
				msgs = ((InternalEObject)parameter).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.KBDATA_KEYWORDS_TYPE__PARAMETER, null, msgs);
			if (newParameter != null)
				msgs = ((InternalEObject)newParameter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.KBDATA_KEYWORDS_TYPE__PARAMETER, null, msgs);
			msgs = basicSetParameter(newParameter, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__PARAMETER, newParameter, newParameter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<KbdataPlatformType> getKbdataPlatform() {
		if (kbdataPlatform == null) {
			kbdataPlatform = new EObjectContainmentEList<KbdataPlatformType>(KbdataPlatformType.class, this, KbdataPackage.KBDATA_KEYWORDS_TYPE__KBDATA_PLATFORM);
		}
		return kbdataPlatform;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DeviceType> getDevice() {
		if (device == null) {
			device = new EObjectContainmentEList<DeviceType>(DeviceType.class, this, KbdataPackage.KBDATA_KEYWORDS_TYPE__DEVICE);
		}
		return device;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FiletypeType> getFiletype() {
		if (filetype == null) {
			filetype = new EObjectContainmentEList<FiletypeType>(FiletypeType.class, this, KbdataPackage.KBDATA_KEYWORDS_TYPE__FILETYPE);
		}
		return filetype;
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__AUDIENCE, oldAudience, audience));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__BASE, oldBase, base));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getClass1() {
		return class1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClass1(Object newClass1) {
		Object oldClass1 = class1;
		class1 = newClass1;
		boolean oldClass1ESet = class1ESet;
		class1ESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__CLASS1, oldClass1, class1, !oldClass1ESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetClass1() {
		Object oldClass1 = class1;
		boolean oldClass1ESet = class1ESet;
		class1 = CLASS1_EDEFAULT;
		class1ESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.KBDATA_KEYWORDS_TYPE__CLASS1, oldClass1, CLASS1_EDEFAULT, oldClass1ESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetClass1() {
		return class1ESet;
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__CONREF, oldConref, conref));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DirType136 getDir() {
		return dir;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDir(DirType136 newDir) {
		DirType136 oldDir = dir;
		dir = newDir == null ? DIR_EDEFAULT : newDir;
		boolean oldDirESet = dirESet;
		dirESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__DIR, oldDir, dir, !oldDirESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetDir() {
		DirType136 oldDir = dir;
		boolean oldDirESet = dirESet;
		dir = DIR_EDEFAULT;
		dirESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.KBDATA_KEYWORDS_TYPE__DIR, oldDir, DIR_EDEFAULT, oldDirESet));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImportanceType137 getImportance() {
		return importance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImportance(ImportanceType137 newImportance) {
		ImportanceType137 oldImportance = importance;
		importance = newImportance == null ? IMPORTANCE_EDEFAULT : newImportance;
		boolean oldImportanceESet = importanceESet;
		importanceESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__IMPORTANCE, oldImportance, importance, !oldImportanceESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetImportance() {
		ImportanceType137 oldImportance = importance;
		boolean oldImportanceESet = importanceESet;
		importance = IMPORTANCE_EDEFAULT;
		importanceESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.KBDATA_KEYWORDS_TYPE__IMPORTANCE, oldImportance, IMPORTANCE_EDEFAULT, oldImportanceESet));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__LANG, oldLang, lang));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__OTHERPROPS, oldOtherprops, otherprops));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__OUTPUTCLASS, oldOutputclass, outputclass));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__PLATFORM, oldPlatform, platform));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__PRODUCT, oldProduct, product));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__PROPS, oldProps, props));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__REV, oldRev, rev));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatusType101 getStatus() {
		return status;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStatus(StatusType101 newStatus) {
		StatusType101 oldStatus = status;
		status = newStatus == null ? STATUS_EDEFAULT : newStatus;
		boolean oldStatusESet = statusESet;
		statusESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__STATUS, oldStatus, status, !oldStatusESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetStatus() {
		StatusType101 oldStatus = status;
		boolean oldStatusESet = statusESet;
		status = STATUS_EDEFAULT;
		statusESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.KBDATA_KEYWORDS_TYPE__STATUS, oldStatus, STATUS_EDEFAULT, oldStatusESet));
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
	public TranslateType154 getTranslate() {
		return translate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTranslate(TranslateType154 newTranslate) {
		TranslateType154 oldTranslate = translate;
		translate = newTranslate == null ? TRANSLATE_EDEFAULT : newTranslate;
		boolean oldTranslateESet = translateESet;
		translateESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__TRANSLATE, oldTranslate, translate, !oldTranslateESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetTranslate() {
		TranslateType154 oldTranslate = translate;
		boolean oldTranslateESet = translateESet;
		translate = TRANSLATE_EDEFAULT;
		translateESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.KBDATA_KEYWORDS_TYPE__TRANSLATE, oldTranslate, TRANSLATE_EDEFAULT, oldTranslateESet));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__XTRC, oldXtrc, xtrc));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS_TYPE__XTRF, oldXtrf, xtrf));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__CALL:
				return basicSetCall(null, msgs);
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__CLASS:
				return basicSetClass(null, msgs);
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__COMMENT:
				return basicSetComment(null, msgs);
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__INHERITANCE:
				return basicSetInheritance(null, msgs);
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__METHOD:
				return basicSetMethod(null, msgs);
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__KBDATA_KEYWORD:
				return basicSetKbdataKeyword(null, msgs);
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__LOCAL:
				return basicSetLocal(null, msgs);
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__MACRO:
				return basicSetMacro(null, msgs);
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__MEMBER:
				return basicSetMember(null, msgs);
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__PARAMETER:
				return basicSetParameter(null, msgs);
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__KBDATA_PLATFORM:
				return ((InternalEList<?>)getKbdataPlatform()).basicRemove(otherEnd, msgs);
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__DEVICE:
				return ((InternalEList<?>)getDevice()).basicRemove(otherEnd, msgs);
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__FILETYPE:
				return ((InternalEList<?>)getFiletype()).basicRemove(otherEnd, msgs);
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
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__CALL:
				return getCall();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__CLASS:
				return getClass_();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__COMMENT:
				return getComment();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__INHERITANCE:
				return getInheritance();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__METHOD:
				return getMethod();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__KBDATA_KEYWORD:
				return getKbdataKeyword();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__LOCAL:
				return getLocal();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__MACRO:
				return getMacro();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__MEMBER:
				return getMember();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__PARAMETER:
				return getParameter();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__KBDATA_PLATFORM:
				return getKbdataPlatform();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__DEVICE:
				return getDevice();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__FILETYPE:
				return getFiletype();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__AUDIENCE:
				return getAudience();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__BASE:
				return getBase();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__CLASS1:
				return getClass1();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__CONREF:
				return getConref();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__DIR:
				return getDir();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__ID:
				return getId();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__IMPORTANCE:
				return getImportance();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__LANG:
				return getLang();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__OTHERPROPS:
				return getOtherprops();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__OUTPUTCLASS:
				return getOutputclass();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__PLATFORM:
				return getPlatform();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__PRODUCT:
				return getProduct();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__PROPS:
				return getProps();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__REV:
				return getRev();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__STATUS:
				return getStatus();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__TRANSLATE:
				return getTranslate();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__XTRC:
				return getXtrc();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__XTRF:
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
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__CALL:
				setCall((CallType)newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__CLASS:
				setClass((ClassType)newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__COMMENT:
				setComment((CommentType)newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__INHERITANCE:
				setInheritance((InheritanceType)newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__METHOD:
				setMethod((MethodType)newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__KBDATA_KEYWORD:
				setKbdataKeyword((KbdataKeywordType)newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__LOCAL:
				setLocal((LocalType)newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__MACRO:
				setMacro((MacroType)newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__MEMBER:
				setMember((MemberType)newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__PARAMETER:
				setParameter((ParameterType)newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__KBDATA_PLATFORM:
				getKbdataPlatform().clear();
				getKbdataPlatform().addAll((Collection<? extends KbdataPlatformType>)newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__DEVICE:
				getDevice().clear();
				getDevice().addAll((Collection<? extends DeviceType>)newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__FILETYPE:
				getFiletype().clear();
				getFiletype().addAll((Collection<? extends FiletypeType>)newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__AUDIENCE:
				setAudience(newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__BASE:
				setBase(newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__CLASS1:
				setClass1(newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__CONREF:
				setConref(newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__DIR:
				setDir((DirType136)newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__ID:
				setId((String)newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__IMPORTANCE:
				setImportance((ImportanceType137)newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__LANG:
				setLang((String)newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__OTHERPROPS:
				setOtherprops(newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__OUTPUTCLASS:
				setOutputclass(newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__PLATFORM:
				setPlatform(newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__PRODUCT:
				setProduct(newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__PROPS:
				setProps(newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__REV:
				setRev(newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__STATUS:
				setStatus((StatusType101)newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__TRANSLATE:
				setTranslate((TranslateType154)newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__XTRC:
				setXtrc(newValue);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__XTRF:
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
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__CALL:
				setCall((CallType)null);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__CLASS:
				setClass((ClassType)null);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__COMMENT:
				setComment((CommentType)null);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__INHERITANCE:
				setInheritance((InheritanceType)null);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__METHOD:
				setMethod((MethodType)null);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__KBDATA_KEYWORD:
				setKbdataKeyword((KbdataKeywordType)null);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__LOCAL:
				setLocal((LocalType)null);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__MACRO:
				setMacro((MacroType)null);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__MEMBER:
				setMember((MemberType)null);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__PARAMETER:
				setParameter((ParameterType)null);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__KBDATA_PLATFORM:
				getKbdataPlatform().clear();
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__DEVICE:
				getDevice().clear();
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__FILETYPE:
				getFiletype().clear();
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__AUDIENCE:
				setAudience(AUDIENCE_EDEFAULT);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__BASE:
				setBase(BASE_EDEFAULT);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__CLASS1:
				unsetClass1();
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__CONREF:
				setConref(CONREF_EDEFAULT);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__DIR:
				unsetDir();
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__ID:
				setId(ID_EDEFAULT);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__IMPORTANCE:
				unsetImportance();
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__LANG:
				setLang(LANG_EDEFAULT);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__OTHERPROPS:
				setOtherprops(OTHERPROPS_EDEFAULT);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__OUTPUTCLASS:
				setOutputclass(OUTPUTCLASS_EDEFAULT);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__PLATFORM:
				setPlatform(PLATFORM_EDEFAULT);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__PRODUCT:
				setProduct(PRODUCT_EDEFAULT);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__PROPS:
				setProps(PROPS_EDEFAULT);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__REV:
				setRev(REV_EDEFAULT);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__STATUS:
				unsetStatus();
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__TRANSLATE:
				unsetTranslate();
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__XTRC:
				setXtrc(XTRC_EDEFAULT);
				return;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__XTRF:
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
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__CALL:
				return call != null;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__CLASS:
				return class_ != null;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__COMMENT:
				return comment != null;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__INHERITANCE:
				return inheritance != null;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__METHOD:
				return method != null;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__KBDATA_KEYWORD:
				return kbdataKeyword != null;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__LOCAL:
				return local != null;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__MACRO:
				return macro != null;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__MEMBER:
				return member != null;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__PARAMETER:
				return parameter != null;
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__KBDATA_PLATFORM:
				return kbdataPlatform != null && !kbdataPlatform.isEmpty();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__DEVICE:
				return device != null && !device.isEmpty();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__FILETYPE:
				return filetype != null && !filetype.isEmpty();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__AUDIENCE:
				return AUDIENCE_EDEFAULT == null ? audience != null : !AUDIENCE_EDEFAULT.equals(audience);
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__BASE:
				return BASE_EDEFAULT == null ? base != null : !BASE_EDEFAULT.equals(base);
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__CLASS1:
				return isSetClass1();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__CONREF:
				return CONREF_EDEFAULT == null ? conref != null : !CONREF_EDEFAULT.equals(conref);
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__DIR:
				return isSetDir();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__IMPORTANCE:
				return isSetImportance();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__LANG:
				return LANG_EDEFAULT == null ? lang != null : !LANG_EDEFAULT.equals(lang);
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__OTHERPROPS:
				return OTHERPROPS_EDEFAULT == null ? otherprops != null : !OTHERPROPS_EDEFAULT.equals(otherprops);
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__OUTPUTCLASS:
				return OUTPUTCLASS_EDEFAULT == null ? outputclass != null : !OUTPUTCLASS_EDEFAULT.equals(outputclass);
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__PLATFORM:
				return PLATFORM_EDEFAULT == null ? platform != null : !PLATFORM_EDEFAULT.equals(platform);
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__PRODUCT:
				return PRODUCT_EDEFAULT == null ? product != null : !PRODUCT_EDEFAULT.equals(product);
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__PROPS:
				return PROPS_EDEFAULT == null ? props != null : !PROPS_EDEFAULT.equals(props);
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__REV:
				return REV_EDEFAULT == null ? rev != null : !REV_EDEFAULT.equals(rev);
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__STATUS:
				return isSetStatus();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__TRANSLATE:
				return isSetTranslate();
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__XTRC:
				return XTRC_EDEFAULT == null ? xtrc != null : !XTRC_EDEFAULT.equals(xtrc);
			case KbdataPackage.KBDATA_KEYWORDS_TYPE__XTRF:
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
		result.append(", class1: ");
		if (class1ESet) result.append(class1); else result.append("<unset>");
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

} //KbdataKeywordsTypeImpl
