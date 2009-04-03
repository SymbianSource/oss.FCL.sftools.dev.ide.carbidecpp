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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Colspec Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getAlign <em>Align</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getBase <em>Base</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getChar <em>Char</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getCharoff <em>Charoff</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getClass_ <em>Class</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getColname <em>Colname</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getColnum <em>Colnum</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getColsep <em>Colsep</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getColwidth <em>Colwidth</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getConref <em>Conref</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getDir <em>Dir</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getId <em>Id</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getLang <em>Lang</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getRowsep <em>Rowsep</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getTranslate <em>Translate</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getXtrc <em>Xtrc</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getXtrf <em>Xtrf</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getColspecType()
 * @model extendedMetaData="name='colspec_._type' kind='empty'"
 * @generated
 */
public interface ColspecType extends EObject {
	/**
	 * Returns the value of the '<em><b>Align</b></em>' attribute.
	 * The literals are from the enumeration {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.AlignType2}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Align</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Align</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.AlignType2
	 * @see #isSetAlign()
	 * @see #unsetAlign()
	 * @see #setAlign(AlignType2)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getColspecType_Align()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='align' namespace='##targetNamespace'"
	 * @generated
	 */
	AlignType2 getAlign();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getAlign <em>Align</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Align</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.AlignType2
	 * @see #isSetAlign()
	 * @see #unsetAlign()
	 * @see #getAlign()
	 * @generated
	 */
	void setAlign(AlignType2 value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getAlign <em>Align</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAlign()
	 * @see #getAlign()
	 * @see #setAlign(AlignType2)
	 * @generated
	 */
	void unsetAlign();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getAlign <em>Align</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Align</em>' attribute is set.
	 * @see #unsetAlign()
	 * @see #getAlign()
	 * @see #setAlign(AlignType2)
	 * @generated
	 */
	boolean isSetAlign();

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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getColspecType_Base()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='base' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getBase();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getBase <em>Base</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base</em>' attribute.
	 * @see #getBase()
	 * @generated
	 */
	void setBase(Object value);

	/**
	 * Returns the value of the '<em><b>Char</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Char</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Char</em>' attribute.
	 * @see #setChar(Object)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getColspecType_Char()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='char' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getChar();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getChar <em>Char</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Char</em>' attribute.
	 * @see #getChar()
	 * @generated
	 */
	void setChar(Object value);

	/**
	 * Returns the value of the '<em><b>Charoff</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Charoff</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Charoff</em>' attribute.
	 * @see #setCharoff(String)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getColspecType_Charoff()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN"
	 *        extendedMetaData="kind='attribute' name='charoff' namespace='##targetNamespace'"
	 * @generated
	 */
	String getCharoff();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getCharoff <em>Charoff</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Charoff</em>' attribute.
	 * @see #getCharoff()
	 * @generated
	 */
	void setCharoff(String value);

	/**
	 * Returns the value of the '<em><b>Class</b></em>' attribute.
	 * The default value is <code>"- topic/colspec "</code>.
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getColspecType_Class()
	 * @model default="- topic/colspec " unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='class' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getClass_();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getClass_ <em>Class</em>}' attribute.
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
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getClass_ <em>Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetClass()
	 * @see #getClass_()
	 * @see #setClass(Object)
	 * @generated
	 */
	void unsetClass();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getClass_ <em>Class</em>}' attribute is set.
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
	 * Returns the value of the '<em><b>Colname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Colname</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Colname</em>' attribute.
	 * @see #setColname(String)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getColspecType_Colname()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN"
	 *        extendedMetaData="kind='attribute' name='colname' namespace='##targetNamespace'"
	 * @generated
	 */
	String getColname();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getColname <em>Colname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Colname</em>' attribute.
	 * @see #getColname()
	 * @generated
	 */
	void setColname(String value);

	/**
	 * Returns the value of the '<em><b>Colnum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Colnum</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Colnum</em>' attribute.
	 * @see #setColnum(String)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getColspecType_Colnum()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN"
	 *        extendedMetaData="kind='attribute' name='colnum' namespace='##targetNamespace'"
	 * @generated
	 */
	String getColnum();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getColnum <em>Colnum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Colnum</em>' attribute.
	 * @see #getColnum()
	 * @generated
	 */
	void setColnum(String value);

	/**
	 * Returns the value of the '<em><b>Colsep</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Colsep</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Colsep</em>' attribute.
	 * @see #setColsep(String)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getColspecType_Colsep()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN"
	 *        extendedMetaData="kind='attribute' name='colsep' namespace='##targetNamespace'"
	 * @generated
	 */
	String getColsep();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getColsep <em>Colsep</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Colsep</em>' attribute.
	 * @see #getColsep()
	 * @generated
	 */
	void setColsep(String value);

	/**
	 * Returns the value of the '<em><b>Colwidth</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Colwidth</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Colwidth</em>' attribute.
	 * @see #setColwidth(Object)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getColspecType_Colwidth()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='colwidth' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getColwidth();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getColwidth <em>Colwidth</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Colwidth</em>' attribute.
	 * @see #getColwidth()
	 * @generated
	 */
	void setColwidth(Object value);

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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getColspecType_Conref()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='conref' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getConref();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getConref <em>Conref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Conref</em>' attribute.
	 * @see #getConref()
	 * @generated
	 */
	void setConref(Object value);

	/**
	 * Returns the value of the '<em><b>Dir</b></em>' attribute.
	 * The literals are from the enumeration {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DirType180}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dir</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dir</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DirType180
	 * @see #isSetDir()
	 * @see #unsetDir()
	 * @see #setDir(DirType180)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getColspecType_Dir()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='dir' namespace='##targetNamespace'"
	 * @generated
	 */
	DirType180 getDir();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getDir <em>Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dir</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DirType180
	 * @see #isSetDir()
	 * @see #unsetDir()
	 * @see #getDir()
	 * @generated
	 */
	void setDir(DirType180 value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getDir <em>Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDir()
	 * @see #getDir()
	 * @see #setDir(DirType180)
	 * @generated
	 */
	void unsetDir();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getDir <em>Dir</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Dir</em>' attribute is set.
	 * @see #unsetDir()
	 * @see #getDir()
	 * @see #setDir(DirType180)
	 * @generated
	 */
	boolean isSetDir();

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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getColspecType_Id()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN"
	 *        extendedMetaData="kind='attribute' name='id' namespace='##targetNamespace'"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getColspecType_Lang()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN"
	 *        extendedMetaData="kind='attribute' name='lang' namespace='##targetNamespace'"
	 * @generated
	 */
	String getLang();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getLang <em>Lang</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lang</em>' attribute.
	 * @see #getLang()
	 * @generated
	 */
	void setLang(String value);

	/**
	 * Returns the value of the '<em><b>Rowsep</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rowsep</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rowsep</em>' attribute.
	 * @see #setRowsep(String)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getColspecType_Rowsep()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN"
	 *        extendedMetaData="kind='attribute' name='rowsep' namespace='##targetNamespace'"
	 * @generated
	 */
	String getRowsep();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getRowsep <em>Rowsep</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rowsep</em>' attribute.
	 * @see #getRowsep()
	 * @generated
	 */
	void setRowsep(String value);

	/**
	 * Returns the value of the '<em><b>Translate</b></em>' attribute.
	 * The literals are from the enumeration {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TranslateType77}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Translate</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Translate</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TranslateType77
	 * @see #isSetTranslate()
	 * @see #unsetTranslate()
	 * @see #setTranslate(TranslateType77)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getColspecType_Translate()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='translate' namespace='##targetNamespace'"
	 * @generated
	 */
	TranslateType77 getTranslate();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getTranslate <em>Translate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Translate</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TranslateType77
	 * @see #isSetTranslate()
	 * @see #unsetTranslate()
	 * @see #getTranslate()
	 * @generated
	 */
	void setTranslate(TranslateType77 value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getTranslate <em>Translate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetTranslate()
	 * @see #getTranslate()
	 * @see #setTranslate(TranslateType77)
	 * @generated
	 */
	void unsetTranslate();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getTranslate <em>Translate</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Translate</em>' attribute is set.
	 * @see #unsetTranslate()
	 * @see #getTranslate()
	 * @see #setTranslate(TranslateType77)
	 * @generated
	 */
	boolean isSetTranslate();

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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getColspecType_Xtrc()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='xtrc' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getXtrc();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getXtrc <em>Xtrc</em>}' attribute.
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getColspecType_Xtrf()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='xtrf' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getXtrf();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType#getXtrf <em>Xtrf</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xtrf</em>' attribute.
	 * @see #getXtrf()
	 * @generated
	 */
	void setXtrf(Object value);

} // ColspecType
