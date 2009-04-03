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

package com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getKeyword <em>Keyword</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getWintitle <em>Wintitle</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getOption <em>Option</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getParmname <em>Parmname</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getApiname <em>Apiname</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getCmdname <em>Cmdname</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getMsgnum <em>Msgnum</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getVarname <em>Varname</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getTerm <em>Term</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getData <em>Data</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getDataAbout <em>Data About</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getForeign <em>Foreign</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getUnknown <em>Unknown</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getImage <em>Image</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getObject <em>Object</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getPh <em>Ph</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getUicontrol <em>Uicontrol</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getMenucascade <em>Menucascade</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getB <em>B</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getU <em>U</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getI <em>I</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getTt <em>Tt</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getSup <em>Sup</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getSub <em>Sub</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getCodeph <em>Codeph</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getSynph <em>Synph</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getFilepath <em>Filepath</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getMsgph <em>Msgph</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getUserinput <em>Userinput</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getSystemoutput <em>Systemoutput</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getTitle <em>Title</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getAudience <em>Audience</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getBase <em>Base</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getClass_ <em>Class</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getConref <em>Conref</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getDatatype <em>Datatype</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getDir <em>Dir</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getFormat <em>Format</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getHref <em>Href</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getId <em>Id</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getImportance <em>Importance</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getLang <em>Lang</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getName <em>Name</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getOtherprops <em>Otherprops</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getOutputclass <em>Outputclass</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getPlatform <em>Platform</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getProduct <em>Product</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getProps <em>Props</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getRev <em>Rev</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getScope <em>Scope</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getStatus <em>Status</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getTranslate <em>Translate</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getType <em>Type</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getValue <em>Value</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getXtrc <em>Xtrc</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getXtrf <em>Xtrf</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType()
 * @model extendedMetaData="name='data_._type' kind='mixed'"
 * @generated
 */
public interface DataType extends EObject {
	/**
	 * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mixed</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mixed</em>' attribute list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Mixed()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='elementWildcard' name=':mixed'"
	 * @generated
	 */
	FeatureMap getMixed();

	/**
	 * Returns the value of the '<em><b>Group</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Group</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group</em>' attribute list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='group' name='group:1'"
	 * @generated
	 */
	FeatureMap getGroup();

	/**
	 * Returns the value of the '<em><b>Keyword</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KeywordType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Keyword</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Keyword</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Keyword()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='keyword' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<KeywordType> getKeyword();

	/**
	 * Returns the value of the '<em><b>Wintitle</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.WintitleType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Wintitle</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Wintitle</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Wintitle()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='wintitle' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<WintitleType> getWintitle();

	/**
	 * Returns the value of the '<em><b>Option</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.OptionType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Option</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Option</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Option()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='option' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<OptionType> getOption();

	/**
	 * Returns the value of the '<em><b>Parmname</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ParmnameType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parmname</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parmname</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Parmname()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='parmname' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<ParmnameType> getParmname();

	/**
	 * Returns the value of the '<em><b>Apiname</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ApinameType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Apiname</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Apiname</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Apiname()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='apiname' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<ApinameType> getApiname();

	/**
	 * Returns the value of the '<em><b>Cmdname</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CmdnameType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cmdname</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cmdname</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Cmdname()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='cmdname' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<CmdnameType> getCmdname();

	/**
	 * Returns the value of the '<em><b>Msgnum</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MsgnumType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Msgnum</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Msgnum</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Msgnum()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='msgnum' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<MsgnumType> getMsgnum();

	/**
	 * Returns the value of the '<em><b>Varname</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.VarnameType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Varname</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Varname</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Varname()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='varname' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<VarnameType> getVarname();

	/**
	 * Returns the value of the '<em><b>Term</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TermType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Term</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Term</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Term()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='term' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<TermType> getTerm();

	/**
	 * Returns the value of the '<em><b>Data</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Data()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='data' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<DataType> getData();

	/**
	 * Returns the value of the '<em><b>Data About</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataAboutType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data About</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data About</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_DataAbout()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='data-about' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<DataAboutType> getDataAbout();

	/**
	 * Returns the value of the '<em><b>Foreign</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ForeignType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Foreign</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Foreign</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Foreign()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='foreign' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<ForeignType> getForeign();

	/**
	 * Returns the value of the '<em><b>Unknown</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.UnknownType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unknown</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unknown</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Unknown()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='unknown' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<UnknownType> getUnknown();

	/**
	 * Returns the value of the '<em><b>Image</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ImageType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Image</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Image</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Image()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='image' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<ImageType> getImage();

	/**
	 * Returns the value of the '<em><b>Object</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ObjectType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Object</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Object()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='object' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<ObjectType> getObject();

	/**
	 * Returns the value of the '<em><b>Ph</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PhType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ph</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ph</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Ph()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ph' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<PhType> getPh();

	/**
	 * Returns the value of the '<em><b>Uicontrol</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.UicontrolType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uicontrol</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uicontrol</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Uicontrol()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='uicontrol' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<UicontrolType> getUicontrol();

	/**
	 * Returns the value of the '<em><b>Menucascade</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MenucascadeType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Menucascade</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Menucascade</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Menucascade()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='menucascade' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<MenucascadeType> getMenucascade();

	/**
	 * Returns the value of the '<em><b>B</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.BType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>B</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>B</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_B()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='b' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<BType> getB();

	/**
	 * Returns the value of the '<em><b>U</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.UType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>U</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>U</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_U()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='u' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<UType> getU();

	/**
	 * Returns the value of the '<em><b>I</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.IType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>I</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>I</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_I()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='i' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<IType> getI();

	/**
	 * Returns the value of the '<em><b>Tt</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TtType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tt</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tt</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Tt()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='tt' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<TtType> getTt();

	/**
	 * Returns the value of the '<em><b>Sup</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SupType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sup</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sup</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Sup()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='sup' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<SupType> getSup();

	/**
	 * Returns the value of the '<em><b>Sub</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SubType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sub</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Sub()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='sub' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<SubType> getSub();

	/**
	 * Returns the value of the '<em><b>Codeph</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CodephType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Codeph</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Codeph</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Codeph()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='codeph' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<CodephType> getCodeph();

	/**
	 * Returns the value of the '<em><b>Synph</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SynphType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Synph</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Synph</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Synph()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='synph' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<SynphType> getSynph();

	/**
	 * Returns the value of the '<em><b>Filepath</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.FilepathType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Filepath</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Filepath</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Filepath()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='filepath' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<FilepathType> getFilepath();

	/**
	 * Returns the value of the '<em><b>Msgph</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MsgphType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Msgph</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Msgph</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Msgph()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='msgph' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<MsgphType> getMsgph();

	/**
	 * Returns the value of the '<em><b>Userinput</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.UserinputType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Userinput</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Userinput</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Userinput()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='userinput' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<UserinputType> getUserinput();

	/**
	 * Returns the value of the '<em><b>Systemoutput</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SystemoutputType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Systemoutput</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Systemoutput</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Systemoutput()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='systemoutput' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<SystemoutputType> getSystemoutput();

	/**
	 * Returns the value of the '<em><b>Title</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TitleType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Title</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Title</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Title()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='title' namespace='##targetNamespace' group='group:1'"
	 * @generated
	 */
	EList<TitleType> getTitle();

	/**
	 * Returns the value of the '<em><b>Audience</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Audience</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Audience</em>' attribute.
	 * @see #setAudience(Object)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Audience()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='audience' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getAudience();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getAudience <em>Audience</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Audience</em>' attribute.
	 * @see #getAudience()
	 * @generated
	 */
	void setAudience(Object value);

	/**
	 * Returns the value of the '<em><b>Base</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base</em>' attribute.
	 * @see #setBase(Object)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Base()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='base' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getBase();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getBase <em>Base</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base</em>' attribute.
	 * @see #getBase()
	 * @generated
	 */
	void setBase(Object value);

	/**
	 * Returns the value of the '<em><b>Class</b></em>' attribute.
	 * The default value is <code>"- topic/data "</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Class</em>' attribute.
	 * @see #isSetClass()
	 * @see #unsetClass()
	 * @see #setClass(Object)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Class()
	 * @model default="- topic/data " unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='class' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getClass_();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getClass_ <em>Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Class</em>' attribute.
	 * @see #isSetClass()
	 * @see #unsetClass()
	 * @see #getClass_()
	 * @generated
	 */
	void setClass(Object value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getClass_ <em>Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetClass()
	 * @see #getClass_()
	 * @see #setClass(Object)
	 * @generated
	 */
	void unsetClass();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getClass_ <em>Class</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Class</em>' attribute is set.
	 * @see #unsetClass()
	 * @see #getClass_()
	 * @see #setClass(Object)
	 * @generated
	 */
	boolean isSetClass();

	/**
	 * Returns the value of the '<em><b>Conref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Conref</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Conref</em>' attribute.
	 * @see #setConref(Object)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Conref()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='conref' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getConref();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getConref <em>Conref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Conref</em>' attribute.
	 * @see #getConref()
	 * @generated
	 */
	void setConref(Object value);

	/**
	 * Returns the value of the '<em><b>Datatype</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Datatype</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Datatype</em>' attribute.
	 * @see #setDatatype(Object)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Datatype()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='datatype' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getDatatype();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getDatatype <em>Datatype</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Datatype</em>' attribute.
	 * @see #getDatatype()
	 * @generated
	 */
	void setDatatype(Object value);

	/**
	 * Returns the value of the '<em><b>Dir</b></em>' attribute.
	 * The literals are from the enumeration {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DirType90}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dir</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dir</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DirType90
	 * @see #isSetDir()
	 * @see #unsetDir()
	 * @see #setDir(DirType90)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Dir()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='dir' namespace='##targetNamespace'"
	 * @generated
	 */
	DirType90 getDir();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getDir <em>Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dir</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DirType90
	 * @see #isSetDir()
	 * @see #unsetDir()
	 * @see #getDir()
	 * @generated
	 */
	void setDir(DirType90 value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getDir <em>Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDir()
	 * @see #getDir()
	 * @see #setDir(DirType90)
	 * @generated
	 */
	void unsetDir();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getDir <em>Dir</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Dir</em>' attribute is set.
	 * @see #unsetDir()
	 * @see #getDir()
	 * @see #setDir(DirType90)
	 * @generated
	 */
	boolean isSetDir();

	/**
	 * Returns the value of the '<em><b>Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Format</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Format</em>' attribute.
	 * @see #setFormat(Object)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Format()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='format' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getFormat();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getFormat <em>Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Format</em>' attribute.
	 * @see #getFormat()
	 * @generated
	 */
	void setFormat(Object value);

	/**
	 * Returns the value of the '<em><b>Href</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Href</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Href</em>' attribute.
	 * @see #setHref(Object)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Href()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='href' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getHref();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getHref <em>Href</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Href</em>' attribute.
	 * @see #getHref()
	 * @generated
	 */
	void setHref(Object value);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Id()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN"
	 *        extendedMetaData="kind='attribute' name='id' namespace='##targetNamespace'"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Importance</b></em>' attribute.
	 * The literals are from the enumeration {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ImportanceType80}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Importance</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Importance</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ImportanceType80
	 * @see #isSetImportance()
	 * @see #unsetImportance()
	 * @see #setImportance(ImportanceType80)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Importance()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='importance' namespace='##targetNamespace'"
	 * @generated
	 */
	ImportanceType80 getImportance();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getImportance <em>Importance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Importance</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ImportanceType80
	 * @see #isSetImportance()
	 * @see #unsetImportance()
	 * @see #getImportance()
	 * @generated
	 */
	void setImportance(ImportanceType80 value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getImportance <em>Importance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetImportance()
	 * @see #getImportance()
	 * @see #setImportance(ImportanceType80)
	 * @generated
	 */
	void unsetImportance();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getImportance <em>Importance</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Importance</em>' attribute is set.
	 * @see #unsetImportance()
	 * @see #getImportance()
	 * @see #setImportance(ImportanceType80)
	 * @generated
	 */
	boolean isSetImportance();

	/**
	 * Returns the value of the '<em><b>Lang</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lang</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lang</em>' attribute.
	 * @see #setLang(String)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Lang()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN"
	 *        extendedMetaData="kind='attribute' name='lang' namespace='##targetNamespace'"
	 * @generated
	 */
	String getLang();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getLang <em>Lang</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lang</em>' attribute.
	 * @see #getLang()
	 * @generated
	 */
	void setLang(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(Object)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='name' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getName();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(Object value);

	/**
	 * Returns the value of the '<em><b>Otherprops</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Otherprops</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Otherprops</em>' attribute.
	 * @see #setOtherprops(Object)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Otherprops()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='otherprops' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getOtherprops();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getOtherprops <em>Otherprops</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Otherprops</em>' attribute.
	 * @see #getOtherprops()
	 * @generated
	 */
	void setOtherprops(Object value);

	/**
	 * Returns the value of the '<em><b>Outputclass</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outputclass</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outputclass</em>' attribute.
	 * @see #setOutputclass(Object)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Outputclass()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='outputclass' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getOutputclass();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getOutputclass <em>Outputclass</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Outputclass</em>' attribute.
	 * @see #getOutputclass()
	 * @generated
	 */
	void setOutputclass(Object value);

	/**
	 * Returns the value of the '<em><b>Platform</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Platform</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Platform</em>' attribute.
	 * @see #setPlatform(Object)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Platform()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='platform' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getPlatform();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getPlatform <em>Platform</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Platform</em>' attribute.
	 * @see #getPlatform()
	 * @generated
	 */
	void setPlatform(Object value);

	/**
	 * Returns the value of the '<em><b>Product</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Product</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Product</em>' attribute.
	 * @see #setProduct(Object)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Product()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='product' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getProduct();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getProduct <em>Product</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Product</em>' attribute.
	 * @see #getProduct()
	 * @generated
	 */
	void setProduct(Object value);

	/**
	 * Returns the value of the '<em><b>Props</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Props</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Props</em>' attribute.
	 * @see #setProps(Object)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Props()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='props' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getProps();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getProps <em>Props</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Props</em>' attribute.
	 * @see #getProps()
	 * @generated
	 */
	void setProps(Object value);

	/**
	 * Returns the value of the '<em><b>Rev</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rev</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rev</em>' attribute.
	 * @see #setRev(Object)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Rev()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='rev' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getRev();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getRev <em>Rev</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rev</em>' attribute.
	 * @see #getRev()
	 * @generated
	 */
	void setRev(Object value);

	/**
	 * Returns the value of the '<em><b>Scope</b></em>' attribute.
	 * The literals are from the enumeration {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ScopeType6}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Scope</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scope</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ScopeType6
	 * @see #isSetScope()
	 * @see #unsetScope()
	 * @see #setScope(ScopeType6)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Scope()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='scope' namespace='##targetNamespace'"
	 * @generated
	 */
	ScopeType6 getScope();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getScope <em>Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scope</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ScopeType6
	 * @see #isSetScope()
	 * @see #unsetScope()
	 * @see #getScope()
	 * @generated
	 */
	void setScope(ScopeType6 value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getScope <em>Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetScope()
	 * @see #getScope()
	 * @see #setScope(ScopeType6)
	 * @generated
	 */
	void unsetScope();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getScope <em>Scope</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Scope</em>' attribute is set.
	 * @see #unsetScope()
	 * @see #getScope()
	 * @see #setScope(ScopeType6)
	 * @generated
	 */
	boolean isSetScope();

	/**
	 * Returns the value of the '<em><b>Status</b></em>' attribute.
	 * The literals are from the enumeration {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.StatusType140}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Status</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Status</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.StatusType140
	 * @see #isSetStatus()
	 * @see #unsetStatus()
	 * @see #setStatus(StatusType140)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Status()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='status' namespace='##targetNamespace'"
	 * @generated
	 */
	StatusType140 getStatus();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getStatus <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Status</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.StatusType140
	 * @see #isSetStatus()
	 * @see #unsetStatus()
	 * @see #getStatus()
	 * @generated
	 */
	void setStatus(StatusType140 value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getStatus <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetStatus()
	 * @see #getStatus()
	 * @see #setStatus(StatusType140)
	 * @generated
	 */
	void unsetStatus();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getStatus <em>Status</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Status</em>' attribute is set.
	 * @see #unsetStatus()
	 * @see #getStatus()
	 * @see #setStatus(StatusType140)
	 * @generated
	 */
	boolean isSetStatus();

	/**
	 * Returns the value of the '<em><b>Translate</b></em>' attribute.
	 * The literals are from the enumeration {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TranslateType111}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Translate</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Translate</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TranslateType111
	 * @see #isSetTranslate()
	 * @see #unsetTranslate()
	 * @see #setTranslate(TranslateType111)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Translate()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='translate' namespace='##targetNamespace'"
	 * @generated
	 */
	TranslateType111 getTranslate();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getTranslate <em>Translate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Translate</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TranslateType111
	 * @see #isSetTranslate()
	 * @see #unsetTranslate()
	 * @see #getTranslate()
	 * @generated
	 */
	void setTranslate(TranslateType111 value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getTranslate <em>Translate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetTranslate()
	 * @see #getTranslate()
	 * @see #setTranslate(TranslateType111)
	 * @generated
	 */
	void unsetTranslate();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getTranslate <em>Translate</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Translate</em>' attribute is set.
	 * @see #unsetTranslate()
	 * @see #getTranslate()
	 * @see #setTranslate(TranslateType111)
	 * @generated
	 */
	boolean isSetTranslate();

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(Object)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Type()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='type' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getType();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(Object value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(Object)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Value()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='value' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getValue();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(Object value);

	/**
	 * Returns the value of the '<em><b>Xtrc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xtrc</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xtrc</em>' attribute.
	 * @see #setXtrc(Object)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Xtrc()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='xtrc' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getXtrc();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getXtrc <em>Xtrc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xtrc</em>' attribute.
	 * @see #getXtrc()
	 * @generated
	 */
	void setXtrc(Object value);

	/**
	 * Returns the value of the '<em><b>Xtrf</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xtrf</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xtrf</em>' attribute.
	 * @see #setXtrf(Object)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getDataType_Xtrf()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='xtrf' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getXtrf();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType#getXtrf <em>Xtrf</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xtrf</em>' attribute.
	 * @see #getXtrf()
	 * @generated
	 */
	void setXtrf(Object value);

} // DataType
