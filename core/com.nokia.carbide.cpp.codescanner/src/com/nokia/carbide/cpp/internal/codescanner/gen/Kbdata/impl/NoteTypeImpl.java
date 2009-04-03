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
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DirType85;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DlType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DraftCommentType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.FigType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.FilepathType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.FnType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ForeignType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.IType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ImageType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ImagemapType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ImportanceType72;
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
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.QType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.RequiredCleanupType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ScreenType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SimpletableType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SlType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.StateType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.StatusType54;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SubType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SupType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SynphType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SyntaxdiagramType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SystemoutputType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TableType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TermType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TmType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TranslateType100;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TtType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TypeType4;
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
 * An implementation of the model object '<em><b>Note Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getPh <em>Ph</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getUicontrol <em>Uicontrol</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getMenucascade <em>Menucascade</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getB <em>B</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getU <em>U</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getI <em>I</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getTt <em>Tt</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getSup <em>Sup</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getSub <em>Sub</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getCodeph <em>Codeph</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getSynph <em>Synph</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getFilepath <em>Filepath</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getMsgph <em>Msgph</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getUserinput <em>Userinput</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getSystemoutput <em>Systemoutput</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getTerm <em>Term</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getXref <em>Xref</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getCite <em>Cite</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getQ <em>Q</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getBoolean <em>Boolean</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getState <em>State</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getKeyword <em>Keyword</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getWintitle <em>Wintitle</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getOption <em>Option</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getParmname <em>Parmname</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getApiname <em>Apiname</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getCmdname <em>Cmdname</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getMsgnum <em>Msgnum</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getVarname <em>Varname</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getTm <em>Tm</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getP <em>P</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getLq <em>Lq</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getDl <em>Dl</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getParml <em>Parml</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getUl <em>Ul</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getOl <em>Ol</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getSl <em>Sl</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getPre <em>Pre</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getScreen <em>Screen</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getCodeblock <em>Codeblock</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getMsgblock <em>Msgblock</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getLines <em>Lines</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getFig <em>Fig</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getSyntaxdiagram <em>Syntaxdiagram</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getImagemap <em>Imagemap</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getImage <em>Image</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getObject <em>Object</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getTable <em>Table</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getSimpletable <em>Simpletable</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getDraftComment <em>Draft Comment</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getRequiredCleanup <em>Required Cleanup</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getFn <em>Fn</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getIndextermref <em>Indextermref</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getIndexterm <em>Indexterm</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getData <em>Data</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getDataAbout <em>Data About</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getForeign <em>Foreign</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getUnknown <em>Unknown</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getAudience <em>Audience</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getBase <em>Base</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getClass_ <em>Class</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getConref <em>Conref</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getDir <em>Dir</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getImportance <em>Importance</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getLang <em>Lang</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getOtherprops <em>Otherprops</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getOthertype <em>Othertype</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getOutputclass <em>Outputclass</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getPlatform <em>Platform</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getProduct <em>Product</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getProps <em>Props</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getRev <em>Rev</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getSpectitle <em>Spectitle</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getStatus <em>Status</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getTranslate <em>Translate</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getXtrc <em>Xtrc</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.NoteTypeImpl#getXtrf <em>Xtrf</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NoteTypeImpl extends EObjectImpl implements NoteType {
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
	protected static final Object CLASS_EDEFAULT = "- topic/note ";

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
	protected static final DirType85 DIR_EDEFAULT = DirType85.LTR;

	/**
	 * The cached value of the '{@link #getDir() <em>Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDir()
	 * @generated
	 * @ordered
	 */
	protected DirType85 dir = DIR_EDEFAULT;

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
	protected static final ImportanceType72 IMPORTANCE_EDEFAULT = ImportanceType72.OBSOLETE;

	/**
	 * The cached value of the '{@link #getImportance() <em>Importance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImportance()
	 * @generated
	 * @ordered
	 */
	protected ImportanceType72 importance = IMPORTANCE_EDEFAULT;

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
	 * The default value of the '{@link #getOthertype() <em>Othertype</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOthertype()
	 * @generated
	 * @ordered
	 */
	protected static final Object OTHERTYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOthertype() <em>Othertype</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOthertype()
	 * @generated
	 * @ordered
	 */
	protected Object othertype = OTHERTYPE_EDEFAULT;

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
	 * The default value of the '{@link #getSpectitle() <em>Spectitle</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpectitle()
	 * @generated
	 * @ordered
	 */
	protected static final Object SPECTITLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpectitle() <em>Spectitle</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpectitle()
	 * @generated
	 * @ordered
	 */
	protected Object spectitle = SPECTITLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getStatus() <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus()
	 * @generated
	 * @ordered
	 */
	protected static final StatusType54 STATUS_EDEFAULT = StatusType54.NEW;

	/**
	 * The cached value of the '{@link #getStatus() <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus()
	 * @generated
	 * @ordered
	 */
	protected StatusType54 status = STATUS_EDEFAULT;

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
	protected static final TranslateType100 TRANSLATE_EDEFAULT = TranslateType100.YES;

	/**
	 * The cached value of the '{@link #getTranslate() <em>Translate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTranslate()
	 * @generated
	 * @ordered
	 */
	protected TranslateType100 translate = TRANSLATE_EDEFAULT;

	/**
	 * This is true if the Translate attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean translateESet;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final TypeType4 TYPE_EDEFAULT = TypeType4.NOTE;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected TypeType4 type = TYPE_EDEFAULT;

	/**
	 * This is true if the Type attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean typeESet;

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
	protected NoteTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return KbdataPackage.eINSTANCE.getNoteType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, KbdataPackage.NOTE_TYPE__MIXED);
		}
		return mixed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getGroup() {
		return (FeatureMap)getMixed().<FeatureMap.Entry>list(KbdataPackage.eINSTANCE.getNoteType_Group());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PhType> getPh() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Ph());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<UicontrolType> getUicontrol() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Uicontrol());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MenucascadeType> getMenucascade() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Menucascade());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<BType> getB() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_B());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<UType> getU() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_U());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IType> getI() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_I());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TtType> getTt() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Tt());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SupType> getSup() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Sup());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SubType> getSub() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Sub());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CodephType> getCodeph() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Codeph());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SynphType> getSynph() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Synph());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FilepathType> getFilepath() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Filepath());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MsgphType> getMsgph() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Msgph());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<UserinputType> getUserinput() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Userinput());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SystemoutputType> getSystemoutput() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Systemoutput());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TermType> getTerm() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Term());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<XrefType> getXref() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Xref());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CiteType> getCite() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Cite());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<QType> getQ() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Q());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<BooleanType> getBoolean() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Boolean());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<StateType> getState() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_State());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<KeywordType> getKeyword() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Keyword());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<WintitleType> getWintitle() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Wintitle());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<OptionType> getOption() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Option());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ParmnameType> getParmname() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Parmname());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ApinameType> getApiname() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Apiname());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CmdnameType> getCmdname() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Cmdname());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MsgnumType> getMsgnum() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Msgnum());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<VarnameType> getVarname() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Varname());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TmType> getTm() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Tm());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PType> getP() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_P());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<LqType> getLq() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Lq());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DlType> getDl() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Dl());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ParmlType> getParml() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Parml());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<UlType> getUl() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Ul());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<OlType> getOl() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Ol());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SlType> getSl() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Sl());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PreType> getPre() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Pre());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ScreenType> getScreen() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Screen());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CodeblockType> getCodeblock() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Codeblock());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MsgblockType> getMsgblock() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Msgblock());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<LinesType> getLines() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Lines());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FigType> getFig() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Fig());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SyntaxdiagramType> getSyntaxdiagram() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Syntaxdiagram());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ImagemapType> getImagemap() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Imagemap());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ImageType> getImage() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Image());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ObjectType> getObject() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Object());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TableType> getTable() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Table());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SimpletableType> getSimpletable() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Simpletable());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DraftCommentType> getDraftComment() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_DraftComment());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<RequiredCleanupType> getRequiredCleanup() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_RequiredCleanup());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FnType> getFn() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Fn());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IndextermrefType> getIndextermref() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Indextermref());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IndextermType> getIndexterm() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Indexterm());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DataType> getData() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Data());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DataAboutType> getDataAbout() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_DataAbout());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ForeignType> getForeign() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Foreign());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<UnknownType> getUnknown() {
		return getGroup().list(KbdataPackage.eINSTANCE.getNoteType_Unknown());
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.NOTE_TYPE__AUDIENCE, oldAudience, audience));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.NOTE_TYPE__BASE, oldBase, base));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.NOTE_TYPE__CLASS, oldClass, class_, !oldClassESet));
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.NOTE_TYPE__CLASS, oldClass, CLASS_EDEFAULT, oldClassESet));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.NOTE_TYPE__CONREF, oldConref, conref));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DirType85 getDir() {
		return dir;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDir(DirType85 newDir) {
		DirType85 oldDir = dir;
		dir = newDir == null ? DIR_EDEFAULT : newDir;
		boolean oldDirESet = dirESet;
		dirESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.NOTE_TYPE__DIR, oldDir, dir, !oldDirESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetDir() {
		DirType85 oldDir = dir;
		boolean oldDirESet = dirESet;
		dir = DIR_EDEFAULT;
		dirESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.NOTE_TYPE__DIR, oldDir, DIR_EDEFAULT, oldDirESet));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.NOTE_TYPE__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImportanceType72 getImportance() {
		return importance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImportance(ImportanceType72 newImportance) {
		ImportanceType72 oldImportance = importance;
		importance = newImportance == null ? IMPORTANCE_EDEFAULT : newImportance;
		boolean oldImportanceESet = importanceESet;
		importanceESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.NOTE_TYPE__IMPORTANCE, oldImportance, importance, !oldImportanceESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetImportance() {
		ImportanceType72 oldImportance = importance;
		boolean oldImportanceESet = importanceESet;
		importance = IMPORTANCE_EDEFAULT;
		importanceESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.NOTE_TYPE__IMPORTANCE, oldImportance, IMPORTANCE_EDEFAULT, oldImportanceESet));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.NOTE_TYPE__LANG, oldLang, lang));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.NOTE_TYPE__OTHERPROPS, oldOtherprops, otherprops));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getOthertype() {
		return othertype;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOthertype(Object newOthertype) {
		Object oldOthertype = othertype;
		othertype = newOthertype;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.NOTE_TYPE__OTHERTYPE, oldOthertype, othertype));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.NOTE_TYPE__OUTPUTCLASS, oldOutputclass, outputclass));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.NOTE_TYPE__PLATFORM, oldPlatform, platform));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.NOTE_TYPE__PRODUCT, oldProduct, product));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.NOTE_TYPE__PROPS, oldProps, props));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.NOTE_TYPE__REV, oldRev, rev));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getSpectitle() {
		return spectitle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSpectitle(Object newSpectitle) {
		Object oldSpectitle = spectitle;
		spectitle = newSpectitle;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.NOTE_TYPE__SPECTITLE, oldSpectitle, spectitle));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatusType54 getStatus() {
		return status;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStatus(StatusType54 newStatus) {
		StatusType54 oldStatus = status;
		status = newStatus == null ? STATUS_EDEFAULT : newStatus;
		boolean oldStatusESet = statusESet;
		statusESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.NOTE_TYPE__STATUS, oldStatus, status, !oldStatusESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetStatus() {
		StatusType54 oldStatus = status;
		boolean oldStatusESet = statusESet;
		status = STATUS_EDEFAULT;
		statusESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.NOTE_TYPE__STATUS, oldStatus, STATUS_EDEFAULT, oldStatusESet));
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
	public TranslateType100 getTranslate() {
		return translate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTranslate(TranslateType100 newTranslate) {
		TranslateType100 oldTranslate = translate;
		translate = newTranslate == null ? TRANSLATE_EDEFAULT : newTranslate;
		boolean oldTranslateESet = translateESet;
		translateESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.NOTE_TYPE__TRANSLATE, oldTranslate, translate, !oldTranslateESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetTranslate() {
		TranslateType100 oldTranslate = translate;
		boolean oldTranslateESet = translateESet;
		translate = TRANSLATE_EDEFAULT;
		translateESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.NOTE_TYPE__TRANSLATE, oldTranslate, TRANSLATE_EDEFAULT, oldTranslateESet));
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
	public TypeType4 getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(TypeType4 newType) {
		TypeType4 oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		boolean oldTypeESet = typeESet;
		typeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.NOTE_TYPE__TYPE, oldType, type, !oldTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetType() {
		TypeType4 oldType = type;
		boolean oldTypeESet = typeESet;
		type = TYPE_EDEFAULT;
		typeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, KbdataPackage.NOTE_TYPE__TYPE, oldType, TYPE_EDEFAULT, oldTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetType() {
		return typeESet;
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.NOTE_TYPE__XTRC, oldXtrc, xtrc));
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
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.NOTE_TYPE__XTRF, oldXtrf, xtrf));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case KbdataPackage.NOTE_TYPE__MIXED:
				return ((InternalEList<?>)getMixed()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__GROUP:
				return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__PH:
				return ((InternalEList<?>)getPh()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__UICONTROL:
				return ((InternalEList<?>)getUicontrol()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__MENUCASCADE:
				return ((InternalEList<?>)getMenucascade()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__B:
				return ((InternalEList<?>)getB()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__U:
				return ((InternalEList<?>)getU()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__I:
				return ((InternalEList<?>)getI()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__TT:
				return ((InternalEList<?>)getTt()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__SUP:
				return ((InternalEList<?>)getSup()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__SUB:
				return ((InternalEList<?>)getSub()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__CODEPH:
				return ((InternalEList<?>)getCodeph()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__SYNPH:
				return ((InternalEList<?>)getSynph()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__FILEPATH:
				return ((InternalEList<?>)getFilepath()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__MSGPH:
				return ((InternalEList<?>)getMsgph()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__USERINPUT:
				return ((InternalEList<?>)getUserinput()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__SYSTEMOUTPUT:
				return ((InternalEList<?>)getSystemoutput()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__TERM:
				return ((InternalEList<?>)getTerm()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__XREF:
				return ((InternalEList<?>)getXref()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__CITE:
				return ((InternalEList<?>)getCite()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__Q:
				return ((InternalEList<?>)getQ()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__BOOLEAN:
				return ((InternalEList<?>)getBoolean()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__STATE:
				return ((InternalEList<?>)getState()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__KEYWORD:
				return ((InternalEList<?>)getKeyword()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__WINTITLE:
				return ((InternalEList<?>)getWintitle()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__OPTION:
				return ((InternalEList<?>)getOption()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__PARMNAME:
				return ((InternalEList<?>)getParmname()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__APINAME:
				return ((InternalEList<?>)getApiname()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__CMDNAME:
				return ((InternalEList<?>)getCmdname()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__MSGNUM:
				return ((InternalEList<?>)getMsgnum()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__VARNAME:
				return ((InternalEList<?>)getVarname()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__TM:
				return ((InternalEList<?>)getTm()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__P:
				return ((InternalEList<?>)getP()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__LQ:
				return ((InternalEList<?>)getLq()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__DL:
				return ((InternalEList<?>)getDl()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__PARML:
				return ((InternalEList<?>)getParml()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__UL:
				return ((InternalEList<?>)getUl()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__OL:
				return ((InternalEList<?>)getOl()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__SL:
				return ((InternalEList<?>)getSl()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__PRE:
				return ((InternalEList<?>)getPre()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__SCREEN:
				return ((InternalEList<?>)getScreen()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__CODEBLOCK:
				return ((InternalEList<?>)getCodeblock()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__MSGBLOCK:
				return ((InternalEList<?>)getMsgblock()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__LINES:
				return ((InternalEList<?>)getLines()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__FIG:
				return ((InternalEList<?>)getFig()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__SYNTAXDIAGRAM:
				return ((InternalEList<?>)getSyntaxdiagram()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__IMAGEMAP:
				return ((InternalEList<?>)getImagemap()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__IMAGE:
				return ((InternalEList<?>)getImage()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__OBJECT:
				return ((InternalEList<?>)getObject()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__TABLE:
				return ((InternalEList<?>)getTable()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__SIMPLETABLE:
				return ((InternalEList<?>)getSimpletable()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__DRAFT_COMMENT:
				return ((InternalEList<?>)getDraftComment()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__REQUIRED_CLEANUP:
				return ((InternalEList<?>)getRequiredCleanup()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__FN:
				return ((InternalEList<?>)getFn()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__INDEXTERMREF:
				return ((InternalEList<?>)getIndextermref()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__INDEXTERM:
				return ((InternalEList<?>)getIndexterm()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__DATA:
				return ((InternalEList<?>)getData()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__DATA_ABOUT:
				return ((InternalEList<?>)getDataAbout()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__FOREIGN:
				return ((InternalEList<?>)getForeign()).basicRemove(otherEnd, msgs);
			case KbdataPackage.NOTE_TYPE__UNKNOWN:
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
			case KbdataPackage.NOTE_TYPE__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case KbdataPackage.NOTE_TYPE__GROUP:
				if (coreType) return getGroup();
				return ((FeatureMap.Internal)getGroup()).getWrapper();
			case KbdataPackage.NOTE_TYPE__PH:
				return getPh();
			case KbdataPackage.NOTE_TYPE__UICONTROL:
				return getUicontrol();
			case KbdataPackage.NOTE_TYPE__MENUCASCADE:
				return getMenucascade();
			case KbdataPackage.NOTE_TYPE__B:
				return getB();
			case KbdataPackage.NOTE_TYPE__U:
				return getU();
			case KbdataPackage.NOTE_TYPE__I:
				return getI();
			case KbdataPackage.NOTE_TYPE__TT:
				return getTt();
			case KbdataPackage.NOTE_TYPE__SUP:
				return getSup();
			case KbdataPackage.NOTE_TYPE__SUB:
				return getSub();
			case KbdataPackage.NOTE_TYPE__CODEPH:
				return getCodeph();
			case KbdataPackage.NOTE_TYPE__SYNPH:
				return getSynph();
			case KbdataPackage.NOTE_TYPE__FILEPATH:
				return getFilepath();
			case KbdataPackage.NOTE_TYPE__MSGPH:
				return getMsgph();
			case KbdataPackage.NOTE_TYPE__USERINPUT:
				return getUserinput();
			case KbdataPackage.NOTE_TYPE__SYSTEMOUTPUT:
				return getSystemoutput();
			case KbdataPackage.NOTE_TYPE__TERM:
				return getTerm();
			case KbdataPackage.NOTE_TYPE__XREF:
				return getXref();
			case KbdataPackage.NOTE_TYPE__CITE:
				return getCite();
			case KbdataPackage.NOTE_TYPE__Q:
				return getQ();
			case KbdataPackage.NOTE_TYPE__BOOLEAN:
				return getBoolean();
			case KbdataPackage.NOTE_TYPE__STATE:
				return getState();
			case KbdataPackage.NOTE_TYPE__KEYWORD:
				return getKeyword();
			case KbdataPackage.NOTE_TYPE__WINTITLE:
				return getWintitle();
			case KbdataPackage.NOTE_TYPE__OPTION:
				return getOption();
			case KbdataPackage.NOTE_TYPE__PARMNAME:
				return getParmname();
			case KbdataPackage.NOTE_TYPE__APINAME:
				return getApiname();
			case KbdataPackage.NOTE_TYPE__CMDNAME:
				return getCmdname();
			case KbdataPackage.NOTE_TYPE__MSGNUM:
				return getMsgnum();
			case KbdataPackage.NOTE_TYPE__VARNAME:
				return getVarname();
			case KbdataPackage.NOTE_TYPE__TM:
				return getTm();
			case KbdataPackage.NOTE_TYPE__P:
				return getP();
			case KbdataPackage.NOTE_TYPE__LQ:
				return getLq();
			case KbdataPackage.NOTE_TYPE__DL:
				return getDl();
			case KbdataPackage.NOTE_TYPE__PARML:
				return getParml();
			case KbdataPackage.NOTE_TYPE__UL:
				return getUl();
			case KbdataPackage.NOTE_TYPE__OL:
				return getOl();
			case KbdataPackage.NOTE_TYPE__SL:
				return getSl();
			case KbdataPackage.NOTE_TYPE__PRE:
				return getPre();
			case KbdataPackage.NOTE_TYPE__SCREEN:
				return getScreen();
			case KbdataPackage.NOTE_TYPE__CODEBLOCK:
				return getCodeblock();
			case KbdataPackage.NOTE_TYPE__MSGBLOCK:
				return getMsgblock();
			case KbdataPackage.NOTE_TYPE__LINES:
				return getLines();
			case KbdataPackage.NOTE_TYPE__FIG:
				return getFig();
			case KbdataPackage.NOTE_TYPE__SYNTAXDIAGRAM:
				return getSyntaxdiagram();
			case KbdataPackage.NOTE_TYPE__IMAGEMAP:
				return getImagemap();
			case KbdataPackage.NOTE_TYPE__IMAGE:
				return getImage();
			case KbdataPackage.NOTE_TYPE__OBJECT:
				return getObject();
			case KbdataPackage.NOTE_TYPE__TABLE:
				return getTable();
			case KbdataPackage.NOTE_TYPE__SIMPLETABLE:
				return getSimpletable();
			case KbdataPackage.NOTE_TYPE__DRAFT_COMMENT:
				return getDraftComment();
			case KbdataPackage.NOTE_TYPE__REQUIRED_CLEANUP:
				return getRequiredCleanup();
			case KbdataPackage.NOTE_TYPE__FN:
				return getFn();
			case KbdataPackage.NOTE_TYPE__INDEXTERMREF:
				return getIndextermref();
			case KbdataPackage.NOTE_TYPE__INDEXTERM:
				return getIndexterm();
			case KbdataPackage.NOTE_TYPE__DATA:
				return getData();
			case KbdataPackage.NOTE_TYPE__DATA_ABOUT:
				return getDataAbout();
			case KbdataPackage.NOTE_TYPE__FOREIGN:
				return getForeign();
			case KbdataPackage.NOTE_TYPE__UNKNOWN:
				return getUnknown();
			case KbdataPackage.NOTE_TYPE__AUDIENCE:
				return getAudience();
			case KbdataPackage.NOTE_TYPE__BASE:
				return getBase();
			case KbdataPackage.NOTE_TYPE__CLASS:
				return getClass_();
			case KbdataPackage.NOTE_TYPE__CONREF:
				return getConref();
			case KbdataPackage.NOTE_TYPE__DIR:
				return getDir();
			case KbdataPackage.NOTE_TYPE__ID:
				return getId();
			case KbdataPackage.NOTE_TYPE__IMPORTANCE:
				return getImportance();
			case KbdataPackage.NOTE_TYPE__LANG:
				return getLang();
			case KbdataPackage.NOTE_TYPE__OTHERPROPS:
				return getOtherprops();
			case KbdataPackage.NOTE_TYPE__OTHERTYPE:
				return getOthertype();
			case KbdataPackage.NOTE_TYPE__OUTPUTCLASS:
				return getOutputclass();
			case KbdataPackage.NOTE_TYPE__PLATFORM:
				return getPlatform();
			case KbdataPackage.NOTE_TYPE__PRODUCT:
				return getProduct();
			case KbdataPackage.NOTE_TYPE__PROPS:
				return getProps();
			case KbdataPackage.NOTE_TYPE__REV:
				return getRev();
			case KbdataPackage.NOTE_TYPE__SPECTITLE:
				return getSpectitle();
			case KbdataPackage.NOTE_TYPE__STATUS:
				return getStatus();
			case KbdataPackage.NOTE_TYPE__TRANSLATE:
				return getTranslate();
			case KbdataPackage.NOTE_TYPE__TYPE:
				return getType();
			case KbdataPackage.NOTE_TYPE__XTRC:
				return getXtrc();
			case KbdataPackage.NOTE_TYPE__XTRF:
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
			case KbdataPackage.NOTE_TYPE__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case KbdataPackage.NOTE_TYPE__GROUP:
				((FeatureMap.Internal)getGroup()).set(newValue);
				return;
			case KbdataPackage.NOTE_TYPE__PH:
				getPh().clear();
				getPh().addAll((Collection<? extends PhType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__UICONTROL:
				getUicontrol().clear();
				getUicontrol().addAll((Collection<? extends UicontrolType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__MENUCASCADE:
				getMenucascade().clear();
				getMenucascade().addAll((Collection<? extends MenucascadeType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__B:
				getB().clear();
				getB().addAll((Collection<? extends BType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__U:
				getU().clear();
				getU().addAll((Collection<? extends UType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__I:
				getI().clear();
				getI().addAll((Collection<? extends IType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__TT:
				getTt().clear();
				getTt().addAll((Collection<? extends TtType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__SUP:
				getSup().clear();
				getSup().addAll((Collection<? extends SupType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__SUB:
				getSub().clear();
				getSub().addAll((Collection<? extends SubType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__CODEPH:
				getCodeph().clear();
				getCodeph().addAll((Collection<? extends CodephType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__SYNPH:
				getSynph().clear();
				getSynph().addAll((Collection<? extends SynphType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__FILEPATH:
				getFilepath().clear();
				getFilepath().addAll((Collection<? extends FilepathType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__MSGPH:
				getMsgph().clear();
				getMsgph().addAll((Collection<? extends MsgphType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__USERINPUT:
				getUserinput().clear();
				getUserinput().addAll((Collection<? extends UserinputType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__SYSTEMOUTPUT:
				getSystemoutput().clear();
				getSystemoutput().addAll((Collection<? extends SystemoutputType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__TERM:
				getTerm().clear();
				getTerm().addAll((Collection<? extends TermType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__XREF:
				getXref().clear();
				getXref().addAll((Collection<? extends XrefType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__CITE:
				getCite().clear();
				getCite().addAll((Collection<? extends CiteType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__Q:
				getQ().clear();
				getQ().addAll((Collection<? extends QType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__BOOLEAN:
				getBoolean().clear();
				getBoolean().addAll((Collection<? extends BooleanType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__STATE:
				getState().clear();
				getState().addAll((Collection<? extends StateType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__KEYWORD:
				getKeyword().clear();
				getKeyword().addAll((Collection<? extends KeywordType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__WINTITLE:
				getWintitle().clear();
				getWintitle().addAll((Collection<? extends WintitleType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__OPTION:
				getOption().clear();
				getOption().addAll((Collection<? extends OptionType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__PARMNAME:
				getParmname().clear();
				getParmname().addAll((Collection<? extends ParmnameType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__APINAME:
				getApiname().clear();
				getApiname().addAll((Collection<? extends ApinameType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__CMDNAME:
				getCmdname().clear();
				getCmdname().addAll((Collection<? extends CmdnameType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__MSGNUM:
				getMsgnum().clear();
				getMsgnum().addAll((Collection<? extends MsgnumType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__VARNAME:
				getVarname().clear();
				getVarname().addAll((Collection<? extends VarnameType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__TM:
				getTm().clear();
				getTm().addAll((Collection<? extends TmType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__P:
				getP().clear();
				getP().addAll((Collection<? extends PType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__LQ:
				getLq().clear();
				getLq().addAll((Collection<? extends LqType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__DL:
				getDl().clear();
				getDl().addAll((Collection<? extends DlType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__PARML:
				getParml().clear();
				getParml().addAll((Collection<? extends ParmlType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__UL:
				getUl().clear();
				getUl().addAll((Collection<? extends UlType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__OL:
				getOl().clear();
				getOl().addAll((Collection<? extends OlType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__SL:
				getSl().clear();
				getSl().addAll((Collection<? extends SlType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__PRE:
				getPre().clear();
				getPre().addAll((Collection<? extends PreType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__SCREEN:
				getScreen().clear();
				getScreen().addAll((Collection<? extends ScreenType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__CODEBLOCK:
				getCodeblock().clear();
				getCodeblock().addAll((Collection<? extends CodeblockType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__MSGBLOCK:
				getMsgblock().clear();
				getMsgblock().addAll((Collection<? extends MsgblockType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__LINES:
				getLines().clear();
				getLines().addAll((Collection<? extends LinesType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__FIG:
				getFig().clear();
				getFig().addAll((Collection<? extends FigType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__SYNTAXDIAGRAM:
				getSyntaxdiagram().clear();
				getSyntaxdiagram().addAll((Collection<? extends SyntaxdiagramType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__IMAGEMAP:
				getImagemap().clear();
				getImagemap().addAll((Collection<? extends ImagemapType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__IMAGE:
				getImage().clear();
				getImage().addAll((Collection<? extends ImageType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__OBJECT:
				getObject().clear();
				getObject().addAll((Collection<? extends ObjectType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__TABLE:
				getTable().clear();
				getTable().addAll((Collection<? extends TableType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__SIMPLETABLE:
				getSimpletable().clear();
				getSimpletable().addAll((Collection<? extends SimpletableType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__DRAFT_COMMENT:
				getDraftComment().clear();
				getDraftComment().addAll((Collection<? extends DraftCommentType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__REQUIRED_CLEANUP:
				getRequiredCleanup().clear();
				getRequiredCleanup().addAll((Collection<? extends RequiredCleanupType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__FN:
				getFn().clear();
				getFn().addAll((Collection<? extends FnType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__INDEXTERMREF:
				getIndextermref().clear();
				getIndextermref().addAll((Collection<? extends IndextermrefType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__INDEXTERM:
				getIndexterm().clear();
				getIndexterm().addAll((Collection<? extends IndextermType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__DATA:
				getData().clear();
				getData().addAll((Collection<? extends DataType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__DATA_ABOUT:
				getDataAbout().clear();
				getDataAbout().addAll((Collection<? extends DataAboutType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__FOREIGN:
				getForeign().clear();
				getForeign().addAll((Collection<? extends ForeignType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__UNKNOWN:
				getUnknown().clear();
				getUnknown().addAll((Collection<? extends UnknownType>)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__AUDIENCE:
				setAudience(newValue);
				return;
			case KbdataPackage.NOTE_TYPE__BASE:
				setBase(newValue);
				return;
			case KbdataPackage.NOTE_TYPE__CLASS:
				setClass(newValue);
				return;
			case KbdataPackage.NOTE_TYPE__CONREF:
				setConref(newValue);
				return;
			case KbdataPackage.NOTE_TYPE__DIR:
				setDir((DirType85)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__ID:
				setId((String)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__IMPORTANCE:
				setImportance((ImportanceType72)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__LANG:
				setLang((String)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__OTHERPROPS:
				setOtherprops(newValue);
				return;
			case KbdataPackage.NOTE_TYPE__OTHERTYPE:
				setOthertype(newValue);
				return;
			case KbdataPackage.NOTE_TYPE__OUTPUTCLASS:
				setOutputclass(newValue);
				return;
			case KbdataPackage.NOTE_TYPE__PLATFORM:
				setPlatform(newValue);
				return;
			case KbdataPackage.NOTE_TYPE__PRODUCT:
				setProduct(newValue);
				return;
			case KbdataPackage.NOTE_TYPE__PROPS:
				setProps(newValue);
				return;
			case KbdataPackage.NOTE_TYPE__REV:
				setRev(newValue);
				return;
			case KbdataPackage.NOTE_TYPE__SPECTITLE:
				setSpectitle(newValue);
				return;
			case KbdataPackage.NOTE_TYPE__STATUS:
				setStatus((StatusType54)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__TRANSLATE:
				setTranslate((TranslateType100)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__TYPE:
				setType((TypeType4)newValue);
				return;
			case KbdataPackage.NOTE_TYPE__XTRC:
				setXtrc(newValue);
				return;
			case KbdataPackage.NOTE_TYPE__XTRF:
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
			case KbdataPackage.NOTE_TYPE__MIXED:
				getMixed().clear();
				return;
			case KbdataPackage.NOTE_TYPE__GROUP:
				getGroup().clear();
				return;
			case KbdataPackage.NOTE_TYPE__PH:
				getPh().clear();
				return;
			case KbdataPackage.NOTE_TYPE__UICONTROL:
				getUicontrol().clear();
				return;
			case KbdataPackage.NOTE_TYPE__MENUCASCADE:
				getMenucascade().clear();
				return;
			case KbdataPackage.NOTE_TYPE__B:
				getB().clear();
				return;
			case KbdataPackage.NOTE_TYPE__U:
				getU().clear();
				return;
			case KbdataPackage.NOTE_TYPE__I:
				getI().clear();
				return;
			case KbdataPackage.NOTE_TYPE__TT:
				getTt().clear();
				return;
			case KbdataPackage.NOTE_TYPE__SUP:
				getSup().clear();
				return;
			case KbdataPackage.NOTE_TYPE__SUB:
				getSub().clear();
				return;
			case KbdataPackage.NOTE_TYPE__CODEPH:
				getCodeph().clear();
				return;
			case KbdataPackage.NOTE_TYPE__SYNPH:
				getSynph().clear();
				return;
			case KbdataPackage.NOTE_TYPE__FILEPATH:
				getFilepath().clear();
				return;
			case KbdataPackage.NOTE_TYPE__MSGPH:
				getMsgph().clear();
				return;
			case KbdataPackage.NOTE_TYPE__USERINPUT:
				getUserinput().clear();
				return;
			case KbdataPackage.NOTE_TYPE__SYSTEMOUTPUT:
				getSystemoutput().clear();
				return;
			case KbdataPackage.NOTE_TYPE__TERM:
				getTerm().clear();
				return;
			case KbdataPackage.NOTE_TYPE__XREF:
				getXref().clear();
				return;
			case KbdataPackage.NOTE_TYPE__CITE:
				getCite().clear();
				return;
			case KbdataPackage.NOTE_TYPE__Q:
				getQ().clear();
				return;
			case KbdataPackage.NOTE_TYPE__BOOLEAN:
				getBoolean().clear();
				return;
			case KbdataPackage.NOTE_TYPE__STATE:
				getState().clear();
				return;
			case KbdataPackage.NOTE_TYPE__KEYWORD:
				getKeyword().clear();
				return;
			case KbdataPackage.NOTE_TYPE__WINTITLE:
				getWintitle().clear();
				return;
			case KbdataPackage.NOTE_TYPE__OPTION:
				getOption().clear();
				return;
			case KbdataPackage.NOTE_TYPE__PARMNAME:
				getParmname().clear();
				return;
			case KbdataPackage.NOTE_TYPE__APINAME:
				getApiname().clear();
				return;
			case KbdataPackage.NOTE_TYPE__CMDNAME:
				getCmdname().clear();
				return;
			case KbdataPackage.NOTE_TYPE__MSGNUM:
				getMsgnum().clear();
				return;
			case KbdataPackage.NOTE_TYPE__VARNAME:
				getVarname().clear();
				return;
			case KbdataPackage.NOTE_TYPE__TM:
				getTm().clear();
				return;
			case KbdataPackage.NOTE_TYPE__P:
				getP().clear();
				return;
			case KbdataPackage.NOTE_TYPE__LQ:
				getLq().clear();
				return;
			case KbdataPackage.NOTE_TYPE__DL:
				getDl().clear();
				return;
			case KbdataPackage.NOTE_TYPE__PARML:
				getParml().clear();
				return;
			case KbdataPackage.NOTE_TYPE__UL:
				getUl().clear();
				return;
			case KbdataPackage.NOTE_TYPE__OL:
				getOl().clear();
				return;
			case KbdataPackage.NOTE_TYPE__SL:
				getSl().clear();
				return;
			case KbdataPackage.NOTE_TYPE__PRE:
				getPre().clear();
				return;
			case KbdataPackage.NOTE_TYPE__SCREEN:
				getScreen().clear();
				return;
			case KbdataPackage.NOTE_TYPE__CODEBLOCK:
				getCodeblock().clear();
				return;
			case KbdataPackage.NOTE_TYPE__MSGBLOCK:
				getMsgblock().clear();
				return;
			case KbdataPackage.NOTE_TYPE__LINES:
				getLines().clear();
				return;
			case KbdataPackage.NOTE_TYPE__FIG:
				getFig().clear();
				return;
			case KbdataPackage.NOTE_TYPE__SYNTAXDIAGRAM:
				getSyntaxdiagram().clear();
				return;
			case KbdataPackage.NOTE_TYPE__IMAGEMAP:
				getImagemap().clear();
				return;
			case KbdataPackage.NOTE_TYPE__IMAGE:
				getImage().clear();
				return;
			case KbdataPackage.NOTE_TYPE__OBJECT:
				getObject().clear();
				return;
			case KbdataPackage.NOTE_TYPE__TABLE:
				getTable().clear();
				return;
			case KbdataPackage.NOTE_TYPE__SIMPLETABLE:
				getSimpletable().clear();
				return;
			case KbdataPackage.NOTE_TYPE__DRAFT_COMMENT:
				getDraftComment().clear();
				return;
			case KbdataPackage.NOTE_TYPE__REQUIRED_CLEANUP:
				getRequiredCleanup().clear();
				return;
			case KbdataPackage.NOTE_TYPE__FN:
				getFn().clear();
				return;
			case KbdataPackage.NOTE_TYPE__INDEXTERMREF:
				getIndextermref().clear();
				return;
			case KbdataPackage.NOTE_TYPE__INDEXTERM:
				getIndexterm().clear();
				return;
			case KbdataPackage.NOTE_TYPE__DATA:
				getData().clear();
				return;
			case KbdataPackage.NOTE_TYPE__DATA_ABOUT:
				getDataAbout().clear();
				return;
			case KbdataPackage.NOTE_TYPE__FOREIGN:
				getForeign().clear();
				return;
			case KbdataPackage.NOTE_TYPE__UNKNOWN:
				getUnknown().clear();
				return;
			case KbdataPackage.NOTE_TYPE__AUDIENCE:
				setAudience(AUDIENCE_EDEFAULT);
				return;
			case KbdataPackage.NOTE_TYPE__BASE:
				setBase(BASE_EDEFAULT);
				return;
			case KbdataPackage.NOTE_TYPE__CLASS:
				unsetClass();
				return;
			case KbdataPackage.NOTE_TYPE__CONREF:
				setConref(CONREF_EDEFAULT);
				return;
			case KbdataPackage.NOTE_TYPE__DIR:
				unsetDir();
				return;
			case KbdataPackage.NOTE_TYPE__ID:
				setId(ID_EDEFAULT);
				return;
			case KbdataPackage.NOTE_TYPE__IMPORTANCE:
				unsetImportance();
				return;
			case KbdataPackage.NOTE_TYPE__LANG:
				setLang(LANG_EDEFAULT);
				return;
			case KbdataPackage.NOTE_TYPE__OTHERPROPS:
				setOtherprops(OTHERPROPS_EDEFAULT);
				return;
			case KbdataPackage.NOTE_TYPE__OTHERTYPE:
				setOthertype(OTHERTYPE_EDEFAULT);
				return;
			case KbdataPackage.NOTE_TYPE__OUTPUTCLASS:
				setOutputclass(OUTPUTCLASS_EDEFAULT);
				return;
			case KbdataPackage.NOTE_TYPE__PLATFORM:
				setPlatform(PLATFORM_EDEFAULT);
				return;
			case KbdataPackage.NOTE_TYPE__PRODUCT:
				setProduct(PRODUCT_EDEFAULT);
				return;
			case KbdataPackage.NOTE_TYPE__PROPS:
				setProps(PROPS_EDEFAULT);
				return;
			case KbdataPackage.NOTE_TYPE__REV:
				setRev(REV_EDEFAULT);
				return;
			case KbdataPackage.NOTE_TYPE__SPECTITLE:
				setSpectitle(SPECTITLE_EDEFAULT);
				return;
			case KbdataPackage.NOTE_TYPE__STATUS:
				unsetStatus();
				return;
			case KbdataPackage.NOTE_TYPE__TRANSLATE:
				unsetTranslate();
				return;
			case KbdataPackage.NOTE_TYPE__TYPE:
				unsetType();
				return;
			case KbdataPackage.NOTE_TYPE__XTRC:
				setXtrc(XTRC_EDEFAULT);
				return;
			case KbdataPackage.NOTE_TYPE__XTRF:
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
			case KbdataPackage.NOTE_TYPE__MIXED:
				return mixed != null && !mixed.isEmpty();
			case KbdataPackage.NOTE_TYPE__GROUP:
				return !getGroup().isEmpty();
			case KbdataPackage.NOTE_TYPE__PH:
				return !getPh().isEmpty();
			case KbdataPackage.NOTE_TYPE__UICONTROL:
				return !getUicontrol().isEmpty();
			case KbdataPackage.NOTE_TYPE__MENUCASCADE:
				return !getMenucascade().isEmpty();
			case KbdataPackage.NOTE_TYPE__B:
				return !getB().isEmpty();
			case KbdataPackage.NOTE_TYPE__U:
				return !getU().isEmpty();
			case KbdataPackage.NOTE_TYPE__I:
				return !getI().isEmpty();
			case KbdataPackage.NOTE_TYPE__TT:
				return !getTt().isEmpty();
			case KbdataPackage.NOTE_TYPE__SUP:
				return !getSup().isEmpty();
			case KbdataPackage.NOTE_TYPE__SUB:
				return !getSub().isEmpty();
			case KbdataPackage.NOTE_TYPE__CODEPH:
				return !getCodeph().isEmpty();
			case KbdataPackage.NOTE_TYPE__SYNPH:
				return !getSynph().isEmpty();
			case KbdataPackage.NOTE_TYPE__FILEPATH:
				return !getFilepath().isEmpty();
			case KbdataPackage.NOTE_TYPE__MSGPH:
				return !getMsgph().isEmpty();
			case KbdataPackage.NOTE_TYPE__USERINPUT:
				return !getUserinput().isEmpty();
			case KbdataPackage.NOTE_TYPE__SYSTEMOUTPUT:
				return !getSystemoutput().isEmpty();
			case KbdataPackage.NOTE_TYPE__TERM:
				return !getTerm().isEmpty();
			case KbdataPackage.NOTE_TYPE__XREF:
				return !getXref().isEmpty();
			case KbdataPackage.NOTE_TYPE__CITE:
				return !getCite().isEmpty();
			case KbdataPackage.NOTE_TYPE__Q:
				return !getQ().isEmpty();
			case KbdataPackage.NOTE_TYPE__BOOLEAN:
				return !getBoolean().isEmpty();
			case KbdataPackage.NOTE_TYPE__STATE:
				return !getState().isEmpty();
			case KbdataPackage.NOTE_TYPE__KEYWORD:
				return !getKeyword().isEmpty();
			case KbdataPackage.NOTE_TYPE__WINTITLE:
				return !getWintitle().isEmpty();
			case KbdataPackage.NOTE_TYPE__OPTION:
				return !getOption().isEmpty();
			case KbdataPackage.NOTE_TYPE__PARMNAME:
				return !getParmname().isEmpty();
			case KbdataPackage.NOTE_TYPE__APINAME:
				return !getApiname().isEmpty();
			case KbdataPackage.NOTE_TYPE__CMDNAME:
				return !getCmdname().isEmpty();
			case KbdataPackage.NOTE_TYPE__MSGNUM:
				return !getMsgnum().isEmpty();
			case KbdataPackage.NOTE_TYPE__VARNAME:
				return !getVarname().isEmpty();
			case KbdataPackage.NOTE_TYPE__TM:
				return !getTm().isEmpty();
			case KbdataPackage.NOTE_TYPE__P:
				return !getP().isEmpty();
			case KbdataPackage.NOTE_TYPE__LQ:
				return !getLq().isEmpty();
			case KbdataPackage.NOTE_TYPE__DL:
				return !getDl().isEmpty();
			case KbdataPackage.NOTE_TYPE__PARML:
				return !getParml().isEmpty();
			case KbdataPackage.NOTE_TYPE__UL:
				return !getUl().isEmpty();
			case KbdataPackage.NOTE_TYPE__OL:
				return !getOl().isEmpty();
			case KbdataPackage.NOTE_TYPE__SL:
				return !getSl().isEmpty();
			case KbdataPackage.NOTE_TYPE__PRE:
				return !getPre().isEmpty();
			case KbdataPackage.NOTE_TYPE__SCREEN:
				return !getScreen().isEmpty();
			case KbdataPackage.NOTE_TYPE__CODEBLOCK:
				return !getCodeblock().isEmpty();
			case KbdataPackage.NOTE_TYPE__MSGBLOCK:
				return !getMsgblock().isEmpty();
			case KbdataPackage.NOTE_TYPE__LINES:
				return !getLines().isEmpty();
			case KbdataPackage.NOTE_TYPE__FIG:
				return !getFig().isEmpty();
			case KbdataPackage.NOTE_TYPE__SYNTAXDIAGRAM:
				return !getSyntaxdiagram().isEmpty();
			case KbdataPackage.NOTE_TYPE__IMAGEMAP:
				return !getImagemap().isEmpty();
			case KbdataPackage.NOTE_TYPE__IMAGE:
				return !getImage().isEmpty();
			case KbdataPackage.NOTE_TYPE__OBJECT:
				return !getObject().isEmpty();
			case KbdataPackage.NOTE_TYPE__TABLE:
				return !getTable().isEmpty();
			case KbdataPackage.NOTE_TYPE__SIMPLETABLE:
				return !getSimpletable().isEmpty();
			case KbdataPackage.NOTE_TYPE__DRAFT_COMMENT:
				return !getDraftComment().isEmpty();
			case KbdataPackage.NOTE_TYPE__REQUIRED_CLEANUP:
				return !getRequiredCleanup().isEmpty();
			case KbdataPackage.NOTE_TYPE__FN:
				return !getFn().isEmpty();
			case KbdataPackage.NOTE_TYPE__INDEXTERMREF:
				return !getIndextermref().isEmpty();
			case KbdataPackage.NOTE_TYPE__INDEXTERM:
				return !getIndexterm().isEmpty();
			case KbdataPackage.NOTE_TYPE__DATA:
				return !getData().isEmpty();
			case KbdataPackage.NOTE_TYPE__DATA_ABOUT:
				return !getDataAbout().isEmpty();
			case KbdataPackage.NOTE_TYPE__FOREIGN:
				return !getForeign().isEmpty();
			case KbdataPackage.NOTE_TYPE__UNKNOWN:
				return !getUnknown().isEmpty();
			case KbdataPackage.NOTE_TYPE__AUDIENCE:
				return AUDIENCE_EDEFAULT == null ? audience != null : !AUDIENCE_EDEFAULT.equals(audience);
			case KbdataPackage.NOTE_TYPE__BASE:
				return BASE_EDEFAULT == null ? base != null : !BASE_EDEFAULT.equals(base);
			case KbdataPackage.NOTE_TYPE__CLASS:
				return isSetClass();
			case KbdataPackage.NOTE_TYPE__CONREF:
				return CONREF_EDEFAULT == null ? conref != null : !CONREF_EDEFAULT.equals(conref);
			case KbdataPackage.NOTE_TYPE__DIR:
				return isSetDir();
			case KbdataPackage.NOTE_TYPE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case KbdataPackage.NOTE_TYPE__IMPORTANCE:
				return isSetImportance();
			case KbdataPackage.NOTE_TYPE__LANG:
				return LANG_EDEFAULT == null ? lang != null : !LANG_EDEFAULT.equals(lang);
			case KbdataPackage.NOTE_TYPE__OTHERPROPS:
				return OTHERPROPS_EDEFAULT == null ? otherprops != null : !OTHERPROPS_EDEFAULT.equals(otherprops);
			case KbdataPackage.NOTE_TYPE__OTHERTYPE:
				return OTHERTYPE_EDEFAULT == null ? othertype != null : !OTHERTYPE_EDEFAULT.equals(othertype);
			case KbdataPackage.NOTE_TYPE__OUTPUTCLASS:
				return OUTPUTCLASS_EDEFAULT == null ? outputclass != null : !OUTPUTCLASS_EDEFAULT.equals(outputclass);
			case KbdataPackage.NOTE_TYPE__PLATFORM:
				return PLATFORM_EDEFAULT == null ? platform != null : !PLATFORM_EDEFAULT.equals(platform);
			case KbdataPackage.NOTE_TYPE__PRODUCT:
				return PRODUCT_EDEFAULT == null ? product != null : !PRODUCT_EDEFAULT.equals(product);
			case KbdataPackage.NOTE_TYPE__PROPS:
				return PROPS_EDEFAULT == null ? props != null : !PROPS_EDEFAULT.equals(props);
			case KbdataPackage.NOTE_TYPE__REV:
				return REV_EDEFAULT == null ? rev != null : !REV_EDEFAULT.equals(rev);
			case KbdataPackage.NOTE_TYPE__SPECTITLE:
				return SPECTITLE_EDEFAULT == null ? spectitle != null : !SPECTITLE_EDEFAULT.equals(spectitle);
			case KbdataPackage.NOTE_TYPE__STATUS:
				return isSetStatus();
			case KbdataPackage.NOTE_TYPE__TRANSLATE:
				return isSetTranslate();
			case KbdataPackage.NOTE_TYPE__TYPE:
				return isSetType();
			case KbdataPackage.NOTE_TYPE__XTRC:
				return XTRC_EDEFAULT == null ? xtrc != null : !XTRC_EDEFAULT.equals(xtrc);
			case KbdataPackage.NOTE_TYPE__XTRF:
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
		result.append(", othertype: ");
		result.append(othertype);
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
		result.append(", spectitle: ");
		result.append(spectitle);
		result.append(", status: ");
		if (statusESet) result.append(status); else result.append("<unset>");
		result.append(", translate: ");
		if (translateESet) result.append(translate); else result.append("<unset>");
		result.append(", type: ");
		if (typeESet) result.append(type); else result.append("<unset>");
		result.append(", xtrc: ");
		result.append(xtrc);
		result.append(", xtrf: ");
		result.append(xtrf);
		result.append(')');
		return result.toString();
	}

} //NoteTypeImpl
