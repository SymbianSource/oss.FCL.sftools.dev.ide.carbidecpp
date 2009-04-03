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

import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ApinameType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.BType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.BooleanType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CiteType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CmdnameType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CodeblockType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CodephType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataAboutType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DirType153;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DlType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DraftCommentType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.FigType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.FilepathType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.FnType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ForeignType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.IType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ImageType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ImagemapType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ImportanceType161;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.IndextermType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.IndextermrefType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KeywordType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.LinesType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.LqType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MenucascadeType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MsgblockType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MsgnumType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MsgphType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.NoteType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ObjectType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.OlType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.OptionType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ParmlType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ParmnameType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PhType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PreType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PropdeschdType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.QType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.RequiredCleanupType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ScreenType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SlType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.StateType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.StatusType114;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SubType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SupType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SynphType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SyntaxdiagramType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SystemoutputType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TermType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TmType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TranslateType166;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TtType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.UType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.UicontrolType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.UlType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.UnknownType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.UserinputType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.VarnameType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.WintitleType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.XrefType;

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
 * An implementation of the model object '<em><b>Propdeschd Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getPh <em>Ph</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getUicontrol <em>Uicontrol</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getMenucascade <em>Menucascade</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getB <em>B</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getU <em>U</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getI <em>I</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getTt <em>Tt</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getSup <em>Sup</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getSub <em>Sub</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getCodeph <em>Codeph</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getSynph <em>Synph</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getFilepath <em>Filepath</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getMsgph <em>Msgph</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getUserinput <em>Userinput</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getSystemoutput <em>Systemoutput</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getTerm <em>Term</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getXref <em>Xref</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getCite <em>Cite</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getQ <em>Q</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getBoolean <em>Boolean</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getState <em>State</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getKeyword <em>Keyword</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getWintitle <em>Wintitle</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getOption <em>Option</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getParmname <em>Parmname</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getApiname <em>Apiname</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getCmdname <em>Cmdname</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getMsgnum <em>Msgnum</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getVarname <em>Varname</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getTm <em>Tm</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getP <em>P</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getLq <em>Lq</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getNote <em>Note</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getDl <em>Dl</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getParml <em>Parml</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getUl <em>Ul</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getOl <em>Ol</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getSl <em>Sl</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getPre <em>Pre</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getScreen <em>Screen</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getCodeblock <em>Codeblock</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getMsgblock <em>Msgblock</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getLines <em>Lines</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getFig <em>Fig</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getSyntaxdiagram <em>Syntaxdiagram</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getImagemap <em>Imagemap</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getImage <em>Image</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getObject <em>Object</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getDraftComment <em>Draft Comment</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getRequiredCleanup <em>Required Cleanup</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getFn <em>Fn</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getIndextermref <em>Indextermref</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getIndexterm <em>Indexterm</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getData <em>Data</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getDataAbout <em>Data About</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getForeign <em>Foreign</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getUnknown <em>Unknown</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getAudience <em>Audience</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getBase <em>Base</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getClass_ <em>Class</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getConref <em>Conref</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getDir <em>Dir</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getImportance <em>Importance</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getLang <em>Lang</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getOtherprops <em>Otherprops</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getOutputclass <em>Outputclass</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getPlatform <em>Platform</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getProduct <em>Product</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getProps <em>Props</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getRev <em>Rev</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getSpecentry <em>Specentry</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getStatus <em>Status</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getTranslate <em>Translate</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getXtrc <em>Xtrc</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.PropdeschdTypeImpl#getXtrf <em>Xtrf</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PropdeschdTypeImpl extends EObjectImpl implements PropdeschdType {
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
	protected static final Object CLASS_EDEFAULT = "- topic/stentry     reference/propdeschd ";

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
	protected static final DirType153 DIR_EDEFAULT = DirType153.LTR;

	/**
	 * The cached value of the '{@link #getDir() <em>Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDir()
	 * @generated
	 * @ordered
	 */
	protected DirType153 dir = DIR_EDEFAULT;

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
	protected static final ImportanceType161 IMPORTANCE_EDEFAULT = ImportanceType161.OBSOLETE;

	/**
	 * The cached value of the '{@link #getImportance() <em>Importance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImportance()
	 * @generated
	 * @ordered
	 */
	protected ImportanceType161 importance = IMPORTANCE_EDEFAULT;

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
	 * The default value of the '{@link #getSpecentry() <em>Specentry</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecentry()
	 * @generated
	 * @ordered
	 */
	protected static final Object SPECENTRY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpecentry() <em>Specentry</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecentry()
	 * @generated
	 * @ordered
	 */
	protected Object specentry = SPECENTRY_EDEFAULT;

	/**
	 * The default value of the '{@link #getStatus() <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus()
	 * @generated
	 * @ordered
	 */
	protected static final StatusType114 STATUS_EDEFAULT = StatusType114.NEW;

	/**
	 * The cached value of the '{@link #getStatus() <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus()
	 * @generated
	 * @ordered
	 */
	protected StatusType114 status = STATUS_EDEFAULT;

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
	protected static final TranslateType166 TRANSLATE_EDEFAULT = TranslateType166.YES;

	/**
	 * The cached value of the '{@link #getTranslate() <em>Translate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTranslate()
	 * @generated
	 * @ordered
	 */
	protected TranslateType166 translate = TRANSLATE_EDEFAULT;

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
	protected PropdeschdTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return KbdataPackage.eINSTANCE.getPropdeschdType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, KbdataPackage.PROPDESCHD_TYPE__MIXED);
		}
		return mixed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getGroup() {
		return (FeatureMap)getMixed().<FeatureMap.Entry>list(KbdataPackage.eINSTANCE.getPropdeschdType_Group());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PhType> getPh() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Ph());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<UicontrolType> getUicontrol() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Uicontrol());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MenucascadeType> getMenucascade() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Menucascade());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<BType> getB() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_B());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<UType> getU() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_U());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IType> getI() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_I());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TtType> getTt() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Tt());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SupType> getSup() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Sup());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SubType> getSub() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Sub());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CodephType> getCodeph() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Codeph());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SynphType> getSynph() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Synph());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FilepathType> getFilepath() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Filepath());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MsgphType> getMsgph() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Msgph());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<UserinputType> getUserinput() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Userinput());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SystemoutputType> getSystemoutput() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Systemoutput());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TermType> getTerm() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Term());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<XrefType> getXref() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Xref());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CiteType> getCite() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Cite());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<QType> getQ() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Q());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<BooleanType> getBoolean() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Boolean());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<StateType> getState() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_State());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<KeywordType> getKeyword() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Keyword());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<WintitleType> getWintitle() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Wintitle());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<OptionType> getOption() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Option());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ParmnameType> getParmname() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Parmname());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ApinameType> getApiname() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Apiname());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CmdnameType> getCmdname() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Cmdname());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MsgnumType> getMsgnum() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Msgnum());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<VarnameType> getVarname() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Varname());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TmType> getTm() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Tm());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PType> getP() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_P());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<LqType> getLq() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Lq());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<NoteType> getNote() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Note());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DlType> getDl() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Dl());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ParmlType> getParml() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Parml());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<UlType> getUl() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Ul());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<OlType> getOl() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Ol());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SlType> getSl() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Sl());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PreType> getPre() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Pre());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ScreenType> getScreen() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Screen());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CodeblockType> getCodeblock() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Codeblock());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MsgblockType> getMsgblock() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Msgblock());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<LinesType> getLines() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Lines());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FigType> getFig() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Fig());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SyntaxdiagramType> getSyntaxdiagram() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Syntaxdiagram());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ImagemapType> getImagemap() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Imagemap());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ImageType> getImage() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Image());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ObjectType> getObject() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Object());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DraftCommentType> getDraftComment() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_DraftComment());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<RequiredCleanupType> getRequiredCleanup() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_RequiredCleanup());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FnType> getFn() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Fn());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IndextermrefType> getIndextermref() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Indextermref());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IndextermType> getIndexterm() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Indexterm());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DataType> getData() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Data());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DataAboutType> getDataAbout() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_DataAbout());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ForeignType> getForeign() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Foreign());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<UnknownType> getUnknown() {
		return getGroup().list(KbdataPackage.eINSTANCE.getPropdeschdType_Unknown());
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.PROPDESCHD_TYPE__AUDIENCE, oldAudience, audience));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.PROPDESCHD_TYPE__BASE, oldBase, base));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.PROPDESCHD_TYPE__CLASS, oldClass, class_, !oldClassESet));
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.PROPDESCHD_TYPE__CLASS, oldClass, CLASS_EDEFAULT, oldClassESet));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.PROPDESCHD_TYPE__CONREF, oldConref, conref));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DirType153 getDir() {
		return dir;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDir(DirType153 newDir) {
		DirType153 oldDir = dir;
		dir = newDir == null ? DIR_EDEFAULT : newDir;
		boolean oldDirESet = dirESet;
		dirESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.PROPDESCHD_TYPE__DIR, oldDir, dir, !oldDirESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetDir() {
		DirType153 oldDir = dir;
		boolean oldDirESet = dirESet;
		dir = DIR_EDEFAULT;
		dirESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.PROPDESCHD_TYPE__DIR, oldDir, DIR_EDEFAULT, oldDirESet));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.PROPDESCHD_TYPE__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImportanceType161 getImportance() {
		return importance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImportance(ImportanceType161 newImportance) {
		ImportanceType161 oldImportance = importance;
		importance = newImportance == null ? IMPORTANCE_EDEFAULT : newImportance;
		boolean oldImportanceESet = importanceESet;
		importanceESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.PROPDESCHD_TYPE__IMPORTANCE, oldImportance, importance, !oldImportanceESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetImportance() {
		ImportanceType161 oldImportance = importance;
		boolean oldImportanceESet = importanceESet;
		importance = IMPORTANCE_EDEFAULT;
		importanceESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.PROPDESCHD_TYPE__IMPORTANCE, oldImportance, IMPORTANCE_EDEFAULT, oldImportanceESet));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.PROPDESCHD_TYPE__LANG, oldLang, lang));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.PROPDESCHD_TYPE__OTHERPROPS, oldOtherprops, otherprops));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.PROPDESCHD_TYPE__OUTPUTCLASS, oldOutputclass, outputclass));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.PROPDESCHD_TYPE__PLATFORM, oldPlatform, platform));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.PROPDESCHD_TYPE__PRODUCT, oldProduct, product));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.PROPDESCHD_TYPE__PROPS, oldProps, props));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.PROPDESCHD_TYPE__REV, oldRev, rev));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getSpecentry() {
		return specentry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSpecentry(Object newSpecentry) {
		Object oldSpecentry = specentry;
		specentry = newSpecentry;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.PROPDESCHD_TYPE__SPECENTRY, oldSpecentry, specentry));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatusType114 getStatus() {
		return status;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStatus(StatusType114 newStatus) {
		StatusType114 oldStatus = status;
		status = newStatus == null ? STATUS_EDEFAULT : newStatus;
		boolean oldStatusESet = statusESet;
		statusESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.PROPDESCHD_TYPE__STATUS, oldStatus, status, !oldStatusESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetStatus() {
		StatusType114 oldStatus = status;
		boolean oldStatusESet = statusESet;
		status = STATUS_EDEFAULT;
		statusESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.PROPDESCHD_TYPE__STATUS, oldStatus, STATUS_EDEFAULT, oldStatusESet));
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
	public TranslateType166 getTranslate() {
		return translate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTranslate(TranslateType166 newTranslate) {
		TranslateType166 oldTranslate = translate;
		translate = newTranslate == null ? TRANSLATE_EDEFAULT : newTranslate;
		boolean oldTranslateESet = translateESet;
		translateESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.PROPDESCHD_TYPE__TRANSLATE, oldTranslate, translate, !oldTranslateESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetTranslate() {
		TranslateType166 oldTranslate = translate;
		boolean oldTranslateESet = translateESet;
		translate = TRANSLATE_EDEFAULT;
		translateESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.PROPDESCHD_TYPE__TRANSLATE, oldTranslate, TRANSLATE_EDEFAULT, oldTranslateESet));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.PROPDESCHD_TYPE__XTRC, oldXtrc, xtrc));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.PROPDESCHD_TYPE__XTRF, oldXtrf, xtrf));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case KbdataPackage.PROPDESCHD_TYPE__MIXED:
				return ((InternalEList<?>)getMixed()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__GROUP:
				return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__PH:
				return ((InternalEList<?>)getPh()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__UICONTROL:
				return ((InternalEList<?>)getUicontrol()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__MENUCASCADE:
				return ((InternalEList<?>)getMenucascade()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__B:
				return ((InternalEList<?>)getB()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__U:
				return ((InternalEList<?>)getU()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__I:
				return ((InternalEList<?>)getI()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__TT:
				return ((InternalEList<?>)getTt()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__SUP:
				return ((InternalEList<?>)getSup()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__SUB:
				return ((InternalEList<?>)getSub()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__CODEPH:
				return ((InternalEList<?>)getCodeph()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__SYNPH:
				return ((InternalEList<?>)getSynph()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__FILEPATH:
				return ((InternalEList<?>)getFilepath()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__MSGPH:
				return ((InternalEList<?>)getMsgph()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__USERINPUT:
				return ((InternalEList<?>)getUserinput()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__SYSTEMOUTPUT:
				return ((InternalEList<?>)getSystemoutput()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__TERM:
				return ((InternalEList<?>)getTerm()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__XREF:
				return ((InternalEList<?>)getXref()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__CITE:
				return ((InternalEList<?>)getCite()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__Q:
				return ((InternalEList<?>)getQ()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__BOOLEAN:
				return ((InternalEList<?>)getBoolean()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__STATE:
				return ((InternalEList<?>)getState()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__KEYWORD:
				return ((InternalEList<?>)getKeyword()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__WINTITLE:
				return ((InternalEList<?>)getWintitle()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__OPTION:
				return ((InternalEList<?>)getOption()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__PARMNAME:
				return ((InternalEList<?>)getParmname()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__APINAME:
				return ((InternalEList<?>)getApiname()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__CMDNAME:
				return ((InternalEList<?>)getCmdname()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__MSGNUM:
				return ((InternalEList<?>)getMsgnum()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__VARNAME:
				return ((InternalEList<?>)getVarname()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__TM:
				return ((InternalEList<?>)getTm()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__P:
				return ((InternalEList<?>)getP()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__LQ:
				return ((InternalEList<?>)getLq()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__NOTE:
				return ((InternalEList<?>)getNote()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__DL:
				return ((InternalEList<?>)getDl()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__PARML:
				return ((InternalEList<?>)getParml()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__UL:
				return ((InternalEList<?>)getUl()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__OL:
				return ((InternalEList<?>)getOl()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__SL:
				return ((InternalEList<?>)getSl()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__PRE:
				return ((InternalEList<?>)getPre()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__SCREEN:
				return ((InternalEList<?>)getScreen()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__CODEBLOCK:
				return ((InternalEList<?>)getCodeblock()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__MSGBLOCK:
				return ((InternalEList<?>)getMsgblock()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__LINES:
				return ((InternalEList<?>)getLines()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__FIG:
				return ((InternalEList<?>)getFig()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__SYNTAXDIAGRAM:
				return ((InternalEList<?>)getSyntaxdiagram()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__IMAGEMAP:
				return ((InternalEList<?>)getImagemap()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__IMAGE:
				return ((InternalEList<?>)getImage()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__OBJECT:
				return ((InternalEList<?>)getObject()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__DRAFT_COMMENT:
				return ((InternalEList<?>)getDraftComment()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__REQUIRED_CLEANUP:
				return ((InternalEList<?>)getRequiredCleanup()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__FN:
				return ((InternalEList<?>)getFn()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__INDEXTERMREF:
				return ((InternalEList<?>)getIndextermref()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__INDEXTERM:
				return ((InternalEList<?>)getIndexterm()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__DATA:
				return ((InternalEList<?>)getData()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__DATA_ABOUT:
				return ((InternalEList<?>)getDataAbout()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__FOREIGN:
				return ((InternalEList<?>)getForeign()).basicRemove(otherEnd, msgs);
			case KbdataPackage.PROPDESCHD_TYPE__UNKNOWN:
				return ((InternalEList<?>)getUnknown()).basicRemove(otherEnd, msgs);
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
			case KbdataPackage.PROPDESCHD_TYPE__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case KbdataPackage.PROPDESCHD_TYPE__GROUP:
				if (coreType) return getGroup();
				return ((FeatureMap.Internal)getGroup()).getWrapper();
			case KbdataPackage.PROPDESCHD_TYPE__PH:
				return getPh();
			case KbdataPackage.PROPDESCHD_TYPE__UICONTROL:
				return getUicontrol();
			case KbdataPackage.PROPDESCHD_TYPE__MENUCASCADE:
				return getMenucascade();
			case KbdataPackage.PROPDESCHD_TYPE__B:
				return getB();
			case KbdataPackage.PROPDESCHD_TYPE__U:
				return getU();
			case KbdataPackage.PROPDESCHD_TYPE__I:
				return getI();
			case KbdataPackage.PROPDESCHD_TYPE__TT:
				return getTt();
			case KbdataPackage.PROPDESCHD_TYPE__SUP:
				return getSup();
			case KbdataPackage.PROPDESCHD_TYPE__SUB:
				return getSub();
			case KbdataPackage.PROPDESCHD_TYPE__CODEPH:
				return getCodeph();
			case KbdataPackage.PROPDESCHD_TYPE__SYNPH:
				return getSynph();
			case KbdataPackage.PROPDESCHD_TYPE__FILEPATH:
				return getFilepath();
			case KbdataPackage.PROPDESCHD_TYPE__MSGPH:
				return getMsgph();
			case KbdataPackage.PROPDESCHD_TYPE__USERINPUT:
				return getUserinput();
			case KbdataPackage.PROPDESCHD_TYPE__SYSTEMOUTPUT:
				return getSystemoutput();
			case KbdataPackage.PROPDESCHD_TYPE__TERM:
				return getTerm();
			case KbdataPackage.PROPDESCHD_TYPE__XREF:
				return getXref();
			case KbdataPackage.PROPDESCHD_TYPE__CITE:
				return getCite();
			case KbdataPackage.PROPDESCHD_TYPE__Q:
				return getQ();
			case KbdataPackage.PROPDESCHD_TYPE__BOOLEAN:
				return getBoolean();
			case KbdataPackage.PROPDESCHD_TYPE__STATE:
				return getState();
			case KbdataPackage.PROPDESCHD_TYPE__KEYWORD:
				return getKeyword();
			case KbdataPackage.PROPDESCHD_TYPE__WINTITLE:
				return getWintitle();
			case KbdataPackage.PROPDESCHD_TYPE__OPTION:
				return getOption();
			case KbdataPackage.PROPDESCHD_TYPE__PARMNAME:
				return getParmname();
			case KbdataPackage.PROPDESCHD_TYPE__APINAME:
				return getApiname();
			case KbdataPackage.PROPDESCHD_TYPE__CMDNAME:
				return getCmdname();
			case KbdataPackage.PROPDESCHD_TYPE__MSGNUM:
				return getMsgnum();
			case KbdataPackage.PROPDESCHD_TYPE__VARNAME:
				return getVarname();
			case KbdataPackage.PROPDESCHD_TYPE__TM:
				return getTm();
			case KbdataPackage.PROPDESCHD_TYPE__P:
				return getP();
			case KbdataPackage.PROPDESCHD_TYPE__LQ:
				return getLq();
			case KbdataPackage.PROPDESCHD_TYPE__NOTE:
				return getNote();
			case KbdataPackage.PROPDESCHD_TYPE__DL:
				return getDl();
			case KbdataPackage.PROPDESCHD_TYPE__PARML:
				return getParml();
			case KbdataPackage.PROPDESCHD_TYPE__UL:
				return getUl();
			case KbdataPackage.PROPDESCHD_TYPE__OL:
				return getOl();
			case KbdataPackage.PROPDESCHD_TYPE__SL:
				return getSl();
			case KbdataPackage.PROPDESCHD_TYPE__PRE:
				return getPre();
			case KbdataPackage.PROPDESCHD_TYPE__SCREEN:
				return getScreen();
			case KbdataPackage.PROPDESCHD_TYPE__CODEBLOCK:
				return getCodeblock();
			case KbdataPackage.PROPDESCHD_TYPE__MSGBLOCK:
				return getMsgblock();
			case KbdataPackage.PROPDESCHD_TYPE__LINES:
				return getLines();
			case KbdataPackage.PROPDESCHD_TYPE__FIG:
				return getFig();
			case KbdataPackage.PROPDESCHD_TYPE__SYNTAXDIAGRAM:
				return getSyntaxdiagram();
			case KbdataPackage.PROPDESCHD_TYPE__IMAGEMAP:
				return getImagemap();
			case KbdataPackage.PROPDESCHD_TYPE__IMAGE:
				return getImage();
			case KbdataPackage.PROPDESCHD_TYPE__OBJECT:
				return getObject();
			case KbdataPackage.PROPDESCHD_TYPE__DRAFT_COMMENT:
				return getDraftComment();
			case KbdataPackage.PROPDESCHD_TYPE__REQUIRED_CLEANUP:
				return getRequiredCleanup();
			case KbdataPackage.PROPDESCHD_TYPE__FN:
				return getFn();
			case KbdataPackage.PROPDESCHD_TYPE__INDEXTERMREF:
				return getIndextermref();
			case KbdataPackage.PROPDESCHD_TYPE__INDEXTERM:
				return getIndexterm();
			case KbdataPackage.PROPDESCHD_TYPE__DATA:
				return getData();
			case KbdataPackage.PROPDESCHD_TYPE__DATA_ABOUT:
				return getDataAbout();
			case KbdataPackage.PROPDESCHD_TYPE__FOREIGN:
				return getForeign();
			case KbdataPackage.PROPDESCHD_TYPE__UNKNOWN:
				return getUnknown();
			case KbdataPackage.PROPDESCHD_TYPE__AUDIENCE:
				return getAudience();
			case KbdataPackage.PROPDESCHD_TYPE__BASE:
				return getBase();
			case KbdataPackage.PROPDESCHD_TYPE__CLASS:
				return getClass_();
			case KbdataPackage.PROPDESCHD_TYPE__CONREF:
				return getConref();
			case KbdataPackage.PROPDESCHD_TYPE__DIR:
				return getDir();
			case KbdataPackage.PROPDESCHD_TYPE__ID:
				return getId();
			case KbdataPackage.PROPDESCHD_TYPE__IMPORTANCE:
				return getImportance();
			case KbdataPackage.PROPDESCHD_TYPE__LANG:
				return getLang();
			case KbdataPackage.PROPDESCHD_TYPE__OTHERPROPS:
				return getOtherprops();
			case KbdataPackage.PROPDESCHD_TYPE__OUTPUTCLASS:
				return getOutputclass();
			case KbdataPackage.PROPDESCHD_TYPE__PLATFORM:
				return getPlatform();
			case KbdataPackage.PROPDESCHD_TYPE__PRODUCT:
				return getProduct();
			case KbdataPackage.PROPDESCHD_TYPE__PROPS:
				return getProps();
			case KbdataPackage.PROPDESCHD_TYPE__REV:
				return getRev();
			case KbdataPackage.PROPDESCHD_TYPE__SPECENTRY:
				return getSpecentry();
			case KbdataPackage.PROPDESCHD_TYPE__STATUS:
				return getStatus();
			case KbdataPackage.PROPDESCHD_TYPE__TRANSLATE:
				return getTranslate();
			case KbdataPackage.PROPDESCHD_TYPE__XTRC:
				return getXtrc();
			case KbdataPackage.PROPDESCHD_TYPE__XTRF:
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
			case KbdataPackage.PROPDESCHD_TYPE__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__GROUP:
				((FeatureMap.Internal)getGroup()).set(newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__PH:
				getPh().clear();
				getPh().addAll((Collection<? extends PhType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__UICONTROL:
				getUicontrol().clear();
				getUicontrol().addAll((Collection<? extends UicontrolType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__MENUCASCADE:
				getMenucascade().clear();
				getMenucascade().addAll((Collection<? extends MenucascadeType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__B:
				getB().clear();
				getB().addAll((Collection<? extends BType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__U:
				getU().clear();
				getU().addAll((Collection<? extends UType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__I:
				getI().clear();
				getI().addAll((Collection<? extends IType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__TT:
				getTt().clear();
				getTt().addAll((Collection<? extends TtType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__SUP:
				getSup().clear();
				getSup().addAll((Collection<? extends SupType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__SUB:
				getSub().clear();
				getSub().addAll((Collection<? extends SubType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__CODEPH:
				getCodeph().clear();
				getCodeph().addAll((Collection<? extends CodephType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__SYNPH:
				getSynph().clear();
				getSynph().addAll((Collection<? extends SynphType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__FILEPATH:
				getFilepath().clear();
				getFilepath().addAll((Collection<? extends FilepathType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__MSGPH:
				getMsgph().clear();
				getMsgph().addAll((Collection<? extends MsgphType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__USERINPUT:
				getUserinput().clear();
				getUserinput().addAll((Collection<? extends UserinputType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__SYSTEMOUTPUT:
				getSystemoutput().clear();
				getSystemoutput().addAll((Collection<? extends SystemoutputType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__TERM:
				getTerm().clear();
				getTerm().addAll((Collection<? extends TermType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__XREF:
				getXref().clear();
				getXref().addAll((Collection<? extends XrefType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__CITE:
				getCite().clear();
				getCite().addAll((Collection<? extends CiteType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__Q:
				getQ().clear();
				getQ().addAll((Collection<? extends QType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__BOOLEAN:
				getBoolean().clear();
				getBoolean().addAll((Collection<? extends BooleanType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__STATE:
				getState().clear();
				getState().addAll((Collection<? extends StateType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__KEYWORD:
				getKeyword().clear();
				getKeyword().addAll((Collection<? extends KeywordType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__WINTITLE:
				getWintitle().clear();
				getWintitle().addAll((Collection<? extends WintitleType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__OPTION:
				getOption().clear();
				getOption().addAll((Collection<? extends OptionType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__PARMNAME:
				getParmname().clear();
				getParmname().addAll((Collection<? extends ParmnameType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__APINAME:
				getApiname().clear();
				getApiname().addAll((Collection<? extends ApinameType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__CMDNAME:
				getCmdname().clear();
				getCmdname().addAll((Collection<? extends CmdnameType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__MSGNUM:
				getMsgnum().clear();
				getMsgnum().addAll((Collection<? extends MsgnumType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__VARNAME:
				getVarname().clear();
				getVarname().addAll((Collection<? extends VarnameType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__TM:
				getTm().clear();
				getTm().addAll((Collection<? extends TmType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__P:
				getP().clear();
				getP().addAll((Collection<? extends PType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__LQ:
				getLq().clear();
				getLq().addAll((Collection<? extends LqType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__NOTE:
				getNote().clear();
				getNote().addAll((Collection<? extends NoteType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__DL:
				getDl().clear();
				getDl().addAll((Collection<? extends DlType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__PARML:
				getParml().clear();
				getParml().addAll((Collection<? extends ParmlType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__UL:
				getUl().clear();
				getUl().addAll((Collection<? extends UlType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__OL:
				getOl().clear();
				getOl().addAll((Collection<? extends OlType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__SL:
				getSl().clear();
				getSl().addAll((Collection<? extends SlType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__PRE:
				getPre().clear();
				getPre().addAll((Collection<? extends PreType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__SCREEN:
				getScreen().clear();
				getScreen().addAll((Collection<? extends ScreenType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__CODEBLOCK:
				getCodeblock().clear();
				getCodeblock().addAll((Collection<? extends CodeblockType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__MSGBLOCK:
				getMsgblock().clear();
				getMsgblock().addAll((Collection<? extends MsgblockType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__LINES:
				getLines().clear();
				getLines().addAll((Collection<? extends LinesType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__FIG:
				getFig().clear();
				getFig().addAll((Collection<? extends FigType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__SYNTAXDIAGRAM:
				getSyntaxdiagram().clear();
				getSyntaxdiagram().addAll((Collection<? extends SyntaxdiagramType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__IMAGEMAP:
				getImagemap().clear();
				getImagemap().addAll((Collection<? extends ImagemapType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__IMAGE:
				getImage().clear();
				getImage().addAll((Collection<? extends ImageType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__OBJECT:
				getObject().clear();
				getObject().addAll((Collection<? extends ObjectType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__DRAFT_COMMENT:
				getDraftComment().clear();
				getDraftComment().addAll((Collection<? extends DraftCommentType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__REQUIRED_CLEANUP:
				getRequiredCleanup().clear();
				getRequiredCleanup().addAll((Collection<? extends RequiredCleanupType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__FN:
				getFn().clear();
				getFn().addAll((Collection<? extends FnType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__INDEXTERMREF:
				getIndextermref().clear();
				getIndextermref().addAll((Collection<? extends IndextermrefType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__INDEXTERM:
				getIndexterm().clear();
				getIndexterm().addAll((Collection<? extends IndextermType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__DATA:
				getData().clear();
				getData().addAll((Collection<? extends DataType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__DATA_ABOUT:
				getDataAbout().clear();
				getDataAbout().addAll((Collection<? extends DataAboutType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__FOREIGN:
				getForeign().clear();
				getForeign().addAll((Collection<? extends ForeignType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__UNKNOWN:
				getUnknown().clear();
				getUnknown().addAll((Collection<? extends UnknownType>)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__AUDIENCE:
				setAudience(newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__BASE:
				setBase(newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__CLASS:
				setClass(newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__CONREF:
				setConref(newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__DIR:
				setDir((DirType153)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__ID:
				setId((String)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__IMPORTANCE:
				setImportance((ImportanceType161)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__LANG:
				setLang((String)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__OTHERPROPS:
				setOtherprops(newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__OUTPUTCLASS:
				setOutputclass(newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__PLATFORM:
				setPlatform(newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__PRODUCT:
				setProduct(newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__PROPS:
				setProps(newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__REV:
				setRev(newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__SPECENTRY:
				setSpecentry(newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__STATUS:
				setStatus((StatusType114)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__TRANSLATE:
				setTranslate((TranslateType166)newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__XTRC:
				setXtrc(newValue);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__XTRF:
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
			case KbdataPackage.PROPDESCHD_TYPE__MIXED:
				getMixed().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__GROUP:
				getGroup().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__PH:
				getPh().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__UICONTROL:
				getUicontrol().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__MENUCASCADE:
				getMenucascade().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__B:
				getB().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__U:
				getU().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__I:
				getI().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__TT:
				getTt().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__SUP:
				getSup().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__SUB:
				getSub().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__CODEPH:
				getCodeph().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__SYNPH:
				getSynph().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__FILEPATH:
				getFilepath().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__MSGPH:
				getMsgph().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__USERINPUT:
				getUserinput().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__SYSTEMOUTPUT:
				getSystemoutput().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__TERM:
				getTerm().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__XREF:
				getXref().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__CITE:
				getCite().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__Q:
				getQ().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__BOOLEAN:
				getBoolean().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__STATE:
				getState().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__KEYWORD:
				getKeyword().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__WINTITLE:
				getWintitle().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__OPTION:
				getOption().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__PARMNAME:
				getParmname().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__APINAME:
				getApiname().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__CMDNAME:
				getCmdname().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__MSGNUM:
				getMsgnum().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__VARNAME:
				getVarname().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__TM:
				getTm().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__P:
				getP().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__LQ:
				getLq().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__NOTE:
				getNote().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__DL:
				getDl().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__PARML:
				getParml().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__UL:
				getUl().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__OL:
				getOl().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__SL:
				getSl().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__PRE:
				getPre().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__SCREEN:
				getScreen().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__CODEBLOCK:
				getCodeblock().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__MSGBLOCK:
				getMsgblock().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__LINES:
				getLines().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__FIG:
				getFig().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__SYNTAXDIAGRAM:
				getSyntaxdiagram().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__IMAGEMAP:
				getImagemap().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__IMAGE:
				getImage().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__OBJECT:
				getObject().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__DRAFT_COMMENT:
				getDraftComment().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__REQUIRED_CLEANUP:
				getRequiredCleanup().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__FN:
				getFn().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__INDEXTERMREF:
				getIndextermref().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__INDEXTERM:
				getIndexterm().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__DATA:
				getData().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__DATA_ABOUT:
				getDataAbout().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__FOREIGN:
				getForeign().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__UNKNOWN:
				getUnknown().clear();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__AUDIENCE:
				setAudience(AUDIENCE_EDEFAULT);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__BASE:
				setBase(BASE_EDEFAULT);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__CLASS:
				unsetClass();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__CONREF:
				setConref(CONREF_EDEFAULT);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__DIR:
				unsetDir();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__ID:
				setId(ID_EDEFAULT);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__IMPORTANCE:
				unsetImportance();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__LANG:
				setLang(LANG_EDEFAULT);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__OTHERPROPS:
				setOtherprops(OTHERPROPS_EDEFAULT);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__OUTPUTCLASS:
				setOutputclass(OUTPUTCLASS_EDEFAULT);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__PLATFORM:
				setPlatform(PLATFORM_EDEFAULT);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__PRODUCT:
				setProduct(PRODUCT_EDEFAULT);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__PROPS:
				setProps(PROPS_EDEFAULT);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__REV:
				setRev(REV_EDEFAULT);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__SPECENTRY:
				setSpecentry(SPECENTRY_EDEFAULT);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__STATUS:
				unsetStatus();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__TRANSLATE:
				unsetTranslate();
				return;
			case KbdataPackage.PROPDESCHD_TYPE__XTRC:
				setXtrc(XTRC_EDEFAULT);
				return;
			case KbdataPackage.PROPDESCHD_TYPE__XTRF:
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
			case KbdataPackage.PROPDESCHD_TYPE__MIXED:
				return mixed != null && !mixed.isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__GROUP:
				return !getGroup().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__PH:
				return !getPh().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__UICONTROL:
				return !getUicontrol().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__MENUCASCADE:
				return !getMenucascade().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__B:
				return !getB().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__U:
				return !getU().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__I:
				return !getI().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__TT:
				return !getTt().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__SUP:
				return !getSup().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__SUB:
				return !getSub().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__CODEPH:
				return !getCodeph().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__SYNPH:
				return !getSynph().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__FILEPATH:
				return !getFilepath().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__MSGPH:
				return !getMsgph().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__USERINPUT:
				return !getUserinput().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__SYSTEMOUTPUT:
				return !getSystemoutput().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__TERM:
				return !getTerm().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__XREF:
				return !getXref().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__CITE:
				return !getCite().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__Q:
				return !getQ().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__BOOLEAN:
				return !getBoolean().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__STATE:
				return !getState().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__KEYWORD:
				return !getKeyword().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__WINTITLE:
				return !getWintitle().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__OPTION:
				return !getOption().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__PARMNAME:
				return !getParmname().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__APINAME:
				return !getApiname().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__CMDNAME:
				return !getCmdname().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__MSGNUM:
				return !getMsgnum().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__VARNAME:
				return !getVarname().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__TM:
				return !getTm().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__P:
				return !getP().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__LQ:
				return !getLq().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__NOTE:
				return !getNote().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__DL:
				return !getDl().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__PARML:
				return !getParml().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__UL:
				return !getUl().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__OL:
				return !getOl().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__SL:
				return !getSl().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__PRE:
				return !getPre().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__SCREEN:
				return !getScreen().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__CODEBLOCK:
				return !getCodeblock().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__MSGBLOCK:
				return !getMsgblock().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__LINES:
				return !getLines().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__FIG:
				return !getFig().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__SYNTAXDIAGRAM:
				return !getSyntaxdiagram().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__IMAGEMAP:
				return !getImagemap().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__IMAGE:
				return !getImage().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__OBJECT:
				return !getObject().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__DRAFT_COMMENT:
				return !getDraftComment().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__REQUIRED_CLEANUP:
				return !getRequiredCleanup().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__FN:
				return !getFn().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__INDEXTERMREF:
				return !getIndextermref().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__INDEXTERM:
				return !getIndexterm().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__DATA:
				return !getData().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__DATA_ABOUT:
				return !getDataAbout().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__FOREIGN:
				return !getForeign().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__UNKNOWN:
				return !getUnknown().isEmpty();
			case KbdataPackage.PROPDESCHD_TYPE__AUDIENCE:
				return AUDIENCE_EDEFAULT == null ? audience != null : !AUDIENCE_EDEFAULT.equals(audience);
			case KbdataPackage.PROPDESCHD_TYPE__BASE:
				return BASE_EDEFAULT == null ? base != null : !BASE_EDEFAULT.equals(base);
			case KbdataPackage.PROPDESCHD_TYPE__CLASS:
				return isSetClass();
			case KbdataPackage.PROPDESCHD_TYPE__CONREF:
				return CONREF_EDEFAULT == null ? conref != null : !CONREF_EDEFAULT.equals(conref);
			case KbdataPackage.PROPDESCHD_TYPE__DIR:
				return isSetDir();
			case KbdataPackage.PROPDESCHD_TYPE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case KbdataPackage.PROPDESCHD_TYPE__IMPORTANCE:
				return isSetImportance();
			case KbdataPackage.PROPDESCHD_TYPE__LANG:
				return LANG_EDEFAULT == null ? lang != null : !LANG_EDEFAULT.equals(lang);
			case KbdataPackage.PROPDESCHD_TYPE__OTHERPROPS:
				return OTHERPROPS_EDEFAULT == null ? otherprops != null : !OTHERPROPS_EDEFAULT.equals(otherprops);
			case KbdataPackage.PROPDESCHD_TYPE__OUTPUTCLASS:
				return OUTPUTCLASS_EDEFAULT == null ? outputclass != null : !OUTPUTCLASS_EDEFAULT.equals(outputclass);
			case KbdataPackage.PROPDESCHD_TYPE__PLATFORM:
				return PLATFORM_EDEFAULT == null ? platform != null : !PLATFORM_EDEFAULT.equals(platform);
			case KbdataPackage.PROPDESCHD_TYPE__PRODUCT:
				return PRODUCT_EDEFAULT == null ? product != null : !PRODUCT_EDEFAULT.equals(product);
			case KbdataPackage.PROPDESCHD_TYPE__PROPS:
				return PROPS_EDEFAULT == null ? props != null : !PROPS_EDEFAULT.equals(props);
			case KbdataPackage.PROPDESCHD_TYPE__REV:
				return REV_EDEFAULT == null ? rev != null : !REV_EDEFAULT.equals(rev);
			case KbdataPackage.PROPDESCHD_TYPE__SPECENTRY:
				return SPECENTRY_EDEFAULT == null ? specentry != null : !SPECENTRY_EDEFAULT.equals(specentry);
			case KbdataPackage.PROPDESCHD_TYPE__STATUS:
				return isSetStatus();
			case KbdataPackage.PROPDESCHD_TYPE__TRANSLATE:
				return isSetTranslate();
			case KbdataPackage.PROPDESCHD_TYPE__XTRC:
				return XTRC_EDEFAULT == null ? xtrc != null : !XTRC_EDEFAULT.equals(xtrc);
			case KbdataPackage.PROPDESCHD_TYPE__XTRF:
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
		result.append(", specentry: ");
		result.append(specentry);
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

} //PropdeschdTypeImpl
