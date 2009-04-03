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
 * A representation of the model object '<em><b>Prodinfo Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getProdname <em>Prodname</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getVrmlist <em>Vrmlist</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getBrand <em>Brand</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getSeries <em>Series</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getPlatform <em>Platform</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getPrognum <em>Prognum</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getFeatnum <em>Featnum</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getComponent <em>Component</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getAudience <em>Audience</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getBase <em>Base</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getClass_ <em>Class</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getConref <em>Conref</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getDir <em>Dir</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getId <em>Id</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getImportance <em>Importance</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getLang <em>Lang</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getOtherprops <em>Otherprops</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getPlatform1 <em>Platform1</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getProduct <em>Product</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getProps <em>Props</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getRev <em>Rev</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getStatus <em>Status</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getTranslate <em>Translate</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getXtrc <em>Xtrc</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getXtrf <em>Xtrf</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getProdinfoType()
 * @model extendedMetaData="name='prodinfo_._type' kind='elementOnly'"
 * @generated
 */
public interface ProdinfoType extends EObject {
	/**
	 * Returns the value of the '<em><b>Prodname</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Prodname</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Prodname</em>' containment reference.
	 * @see #setProdname(ProdnameType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getProdinfoType_Prodname()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='prodname' namespace='##targetNamespace'"
	 * @generated
	 */
	ProdnameType getProdname();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getProdname <em>Prodname</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Prodname</em>' containment reference.
	 * @see #getProdname()
	 * @generated
	 */
	void setProdname(ProdnameType value);

	/**
	 * Returns the value of the '<em><b>Vrmlist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vrmlist</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vrmlist</em>' containment reference.
	 * @see #setVrmlist(VrmlistType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getProdinfoType_Vrmlist()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='vrmlist' namespace='##targetNamespace'"
	 * @generated
	 */
	VrmlistType getVrmlist();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getVrmlist <em>Vrmlist</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vrmlist</em>' containment reference.
	 * @see #getVrmlist()
	 * @generated
	 */
	void setVrmlist(VrmlistType value);

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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getProdinfoType_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:2'"
	 * @generated
	 */
	FeatureMap getGroup();

	/**
	 * Returns the value of the '<em><b>Brand</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.BrandType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Brand</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Brand</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getProdinfoType_Brand()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='brand' namespace='##targetNamespace' group='group:2'"
	 * @generated
	 */
	EList<BrandType> getBrand();

	/**
	 * Returns the value of the '<em><b>Series</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SeriesType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Series</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Series</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getProdinfoType_Series()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='series' namespace='##targetNamespace' group='group:2'"
	 * @generated
	 */
	EList<SeriesType> getSeries();

	/**
	 * Returns the value of the '<em><b>Platform</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PlatformType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Platform</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Platform</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getProdinfoType_Platform()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='platform' namespace='##targetNamespace' group='group:2'"
	 * @generated
	 */
	EList<PlatformType> getPlatform();

	/**
	 * Returns the value of the '<em><b>Prognum</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PrognumType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Prognum</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Prognum</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getProdinfoType_Prognum()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='prognum' namespace='##targetNamespace' group='group:2'"
	 * @generated
	 */
	EList<PrognumType> getPrognum();

	/**
	 * Returns the value of the '<em><b>Featnum</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.FeatnumType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Featnum</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Featnum</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getProdinfoType_Featnum()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='featnum' namespace='##targetNamespace' group='group:2'"
	 * @generated
	 */
	EList<FeatnumType> getFeatnum();

	/**
	 * Returns the value of the '<em><b>Component</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ComponentType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getProdinfoType_Component()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='component' namespace='##targetNamespace' group='group:2'"
	 * @generated
	 */
	EList<ComponentType> getComponent();

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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getProdinfoType_Audience()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='audience' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getAudience();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getAudience <em>Audience</em>}' attribute.
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getProdinfoType_Base()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='base' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getBase();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getBase <em>Base</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base</em>' attribute.
	 * @see #getBase()
	 * @generated
	 */
	void setBase(Object value);

	/**
	 * Returns the value of the '<em><b>Class</b></em>' attribute.
	 * The default value is <code>"- topic/prodinfo "</code>.
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getProdinfoType_Class()
	 * @model default="- topic/prodinfo " unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='class' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getClass_();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getClass_ <em>Class</em>}' attribute.
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
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getClass_ <em>Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetClass()
	 * @see #getClass_()
	 * @see #setClass(Object)
	 * @generated
	 */
	void unsetClass();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getClass_ <em>Class</em>}' attribute is set.
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getProdinfoType_Conref()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='conref' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getConref();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getConref <em>Conref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Conref</em>' attribute.
	 * @see #getConref()
	 * @generated
	 */
	void setConref(Object value);

	/**
	 * Returns the value of the '<em><b>Dir</b></em>' attribute.
	 * The literals are from the enumeration {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DirType122}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dir</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dir</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DirType122
	 * @see #isSetDir()
	 * @see #unsetDir()
	 * @see #setDir(DirType122)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getProdinfoType_Dir()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='dir' namespace='##targetNamespace'"
	 * @generated
	 */
	DirType122 getDir();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getDir <em>Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dir</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DirType122
	 * @see #isSetDir()
	 * @see #unsetDir()
	 * @see #getDir()
	 * @generated
	 */
	void setDir(DirType122 value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getDir <em>Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDir()
	 * @see #getDir()
	 * @see #setDir(DirType122)
	 * @generated
	 */
	void unsetDir();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getDir <em>Dir</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Dir</em>' attribute is set.
	 * @see #unsetDir()
	 * @see #getDir()
	 * @see #setDir(DirType122)
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getProdinfoType_Id()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN"
	 *        extendedMetaData="kind='attribute' name='id' namespace='##targetNamespace'"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Importance</b></em>' attribute.
	 * The literals are from the enumeration {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ImportanceType120}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Importance</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Importance</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ImportanceType120
	 * @see #isSetImportance()
	 * @see #unsetImportance()
	 * @see #setImportance(ImportanceType120)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getProdinfoType_Importance()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='importance' namespace='##targetNamespace'"
	 * @generated
	 */
	ImportanceType120 getImportance();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getImportance <em>Importance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Importance</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ImportanceType120
	 * @see #isSetImportance()
	 * @see #unsetImportance()
	 * @see #getImportance()
	 * @generated
	 */
	void setImportance(ImportanceType120 value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getImportance <em>Importance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetImportance()
	 * @see #getImportance()
	 * @see #setImportance(ImportanceType120)
	 * @generated
	 */
	void unsetImportance();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getImportance <em>Importance</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Importance</em>' attribute is set.
	 * @see #unsetImportance()
	 * @see #getImportance()
	 * @see #setImportance(ImportanceType120)
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getProdinfoType_Lang()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN"
	 *        extendedMetaData="kind='attribute' name='lang' namespace='##targetNamespace'"
	 * @generated
	 */
	String getLang();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getLang <em>Lang</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lang</em>' attribute.
	 * @see #getLang()
	 * @generated
	 */
	void setLang(String value);

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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getProdinfoType_Otherprops()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='otherprops' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getOtherprops();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getOtherprops <em>Otherprops</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Otherprops</em>' attribute.
	 * @see #getOtherprops()
	 * @generated
	 */
	void setOtherprops(Object value);

	/**
	 * Returns the value of the '<em><b>Platform1</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Platform1</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Platform1</em>' attribute.
	 * @see #setPlatform1(Object)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getProdinfoType_Platform1()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='platform' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getPlatform1();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getPlatform1 <em>Platform1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Platform1</em>' attribute.
	 * @see #getPlatform1()
	 * @generated
	 */
	void setPlatform1(Object value);

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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getProdinfoType_Product()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='product' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getProduct();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getProduct <em>Product</em>}' attribute.
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getProdinfoType_Props()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='props' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getProps();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getProps <em>Props</em>}' attribute.
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getProdinfoType_Rev()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='rev' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getRev();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getRev <em>Rev</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rev</em>' attribute.
	 * @see #getRev()
	 * @generated
	 */
	void setRev(Object value);

	/**
	 * Returns the value of the '<em><b>Status</b></em>' attribute.
	 * The literals are from the enumeration {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.StatusType93}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Status</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Status</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.StatusType93
	 * @see #isSetStatus()
	 * @see #unsetStatus()
	 * @see #setStatus(StatusType93)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getProdinfoType_Status()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='status' namespace='##targetNamespace'"
	 * @generated
	 */
	StatusType93 getStatus();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getStatus <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Status</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.StatusType93
	 * @see #isSetStatus()
	 * @see #unsetStatus()
	 * @see #getStatus()
	 * @generated
	 */
	void setStatus(StatusType93 value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getStatus <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetStatus()
	 * @see #getStatus()
	 * @see #setStatus(StatusType93)
	 * @generated
	 */
	void unsetStatus();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getStatus <em>Status</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Status</em>' attribute is set.
	 * @see #unsetStatus()
	 * @see #getStatus()
	 * @see #setStatus(StatusType93)
	 * @generated
	 */
	boolean isSetStatus();

	/**
	 * Returns the value of the '<em><b>Translate</b></em>' attribute.
	 * The literals are from the enumeration {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TranslateType60}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Translate</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Translate</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TranslateType60
	 * @see #isSetTranslate()
	 * @see #unsetTranslate()
	 * @see #setTranslate(TranslateType60)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getProdinfoType_Translate()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='translate' namespace='##targetNamespace'"
	 * @generated
	 */
	TranslateType60 getTranslate();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getTranslate <em>Translate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Translate</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TranslateType60
	 * @see #isSetTranslate()
	 * @see #unsetTranslate()
	 * @see #getTranslate()
	 * @generated
	 */
	void setTranslate(TranslateType60 value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getTranslate <em>Translate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetTranslate()
	 * @see #getTranslate()
	 * @see #setTranslate(TranslateType60)
	 * @generated
	 */
	void unsetTranslate();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getTranslate <em>Translate</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Translate</em>' attribute is set.
	 * @see #unsetTranslate()
	 * @see #getTranslate()
	 * @see #setTranslate(TranslateType60)
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getProdinfoType_Xtrc()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='xtrc' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getXtrc();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getXtrc <em>Xtrc</em>}' attribute.
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getProdinfoType_Xtrf()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='xtrf' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getXtrf();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType#getXtrf <em>Xtrf</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xtrf</em>' attribute.
	 * @see #getXtrf()
	 * @generated
	 */
	void setXtrf(Object value);

} // ProdinfoType
