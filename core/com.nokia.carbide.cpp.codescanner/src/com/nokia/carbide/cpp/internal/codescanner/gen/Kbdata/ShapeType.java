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

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Shape Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getAudience <em>Audience</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getBase <em>Base</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getClass_ <em>Class</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getConref <em>Conref</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getDir <em>Dir</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getId <em>Id</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getImportance <em>Importance</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getKeyref <em>Keyref</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getLang <em>Lang</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getOtherprops <em>Otherprops</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getOutputclass <em>Outputclass</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getPlatform <em>Platform</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getProduct <em>Product</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getProps <em>Props</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getRev <em>Rev</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getStatus <em>Status</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getTranslate <em>Translate</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getXtrc <em>Xtrc</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getXtrf <em>Xtrf</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getShapeType()
 * @model extendedMetaData="name='shape_._type' kind='mixed'"
 * @generated
 */
public interface ShapeType extends EObject {
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getShapeType_Mixed()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='elementWildcard' name=':mixed'"
	 * @generated
	 */
	FeatureMap getMixed();

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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getShapeType_Audience()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='audience' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getAudience();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getAudience <em>Audience</em>}' attribute.
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getShapeType_Base()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='base' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getBase();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getBase <em>Base</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base</em>' attribute.
	 * @see #getBase()
	 * @generated
	 */
	void setBase(Object value);

	/**
	 * Returns the value of the '<em><b>Class</b></em>' attribute.
	 * The default value is <code>"+ topic/keyword ut-d/shape "</code>.
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getShapeType_Class()
	 * @model default="+ topic/keyword ut-d/shape " unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='class' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getClass_();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getClass_ <em>Class</em>}' attribute.
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
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getClass_ <em>Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetClass()
	 * @see #getClass_()
	 * @see #setClass(Object)
	 * @generated
	 */
	void unsetClass();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getClass_ <em>Class</em>}' attribute is set.
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getShapeType_Conref()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='conref' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getConref();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getConref <em>Conref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Conref</em>' attribute.
	 * @see #getConref()
	 * @generated
	 */
	void setConref(Object value);

	/**
	 * Returns the value of the '<em><b>Dir</b></em>' attribute.
	 * The literals are from the enumeration {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DirType67}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dir</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dir</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DirType67
	 * @see #isSetDir()
	 * @see #unsetDir()
	 * @see #setDir(DirType67)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getShapeType_Dir()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='dir' namespace='##targetNamespace'"
	 * @generated
	 */
	DirType67 getDir();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getDir <em>Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dir</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DirType67
	 * @see #isSetDir()
	 * @see #unsetDir()
	 * @see #getDir()
	 * @generated
	 */
	void setDir(DirType67 value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getDir <em>Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDir()
	 * @see #getDir()
	 * @see #setDir(DirType67)
	 * @generated
	 */
	void unsetDir();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getDir <em>Dir</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Dir</em>' attribute is set.
	 * @see #unsetDir()
	 * @see #getDir()
	 * @see #setDir(DirType67)
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getShapeType_Id()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN"
	 *        extendedMetaData="kind='attribute' name='id' namespace='##targetNamespace'"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Importance</b></em>' attribute.
	 * The literals are from the enumeration {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ImportanceType60}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Importance</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Importance</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ImportanceType60
	 * @see #isSetImportance()
	 * @see #unsetImportance()
	 * @see #setImportance(ImportanceType60)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getShapeType_Importance()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='importance' namespace='##targetNamespace'"
	 * @generated
	 */
	ImportanceType60 getImportance();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getImportance <em>Importance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Importance</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ImportanceType60
	 * @see #isSetImportance()
	 * @see #unsetImportance()
	 * @see #getImportance()
	 * @generated
	 */
	void setImportance(ImportanceType60 value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getImportance <em>Importance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetImportance()
	 * @see #getImportance()
	 * @see #setImportance(ImportanceType60)
	 * @generated
	 */
	void unsetImportance();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getImportance <em>Importance</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Importance</em>' attribute is set.
	 * @see #unsetImportance()
	 * @see #getImportance()
	 * @see #setImportance(ImportanceType60)
	 * @generated
	 */
	boolean isSetImportance();

	/**
	 * Returns the value of the '<em><b>Keyref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Keyref</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Keyref</em>' attribute.
	 * @see #setKeyref(Object)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getShapeType_Keyref()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='keyref' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getKeyref();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getKeyref <em>Keyref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Keyref</em>' attribute.
	 * @see #getKeyref()
	 * @generated
	 */
	void setKeyref(Object value);

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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getShapeType_Lang()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN"
	 *        extendedMetaData="kind='attribute' name='lang' namespace='##targetNamespace'"
	 * @generated
	 */
	String getLang();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getLang <em>Lang</em>}' attribute.
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getShapeType_Otherprops()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='otherprops' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getOtherprops();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getOtherprops <em>Otherprops</em>}' attribute.
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getShapeType_Outputclass()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='outputclass' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getOutputclass();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getOutputclass <em>Outputclass</em>}' attribute.
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getShapeType_Platform()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='platform' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getPlatform();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getPlatform <em>Platform</em>}' attribute.
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getShapeType_Product()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='product' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getProduct();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getProduct <em>Product</em>}' attribute.
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getShapeType_Props()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='props' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getProps();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getProps <em>Props</em>}' attribute.
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getShapeType_Rev()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='rev' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getRev();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getRev <em>Rev</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rev</em>' attribute.
	 * @see #getRev()
	 * @generated
	 */
	void setRev(Object value);

	/**
	 * Returns the value of the '<em><b>Status</b></em>' attribute.
	 * The literals are from the enumeration {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.StatusType145}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Status</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Status</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.StatusType145
	 * @see #isSetStatus()
	 * @see #unsetStatus()
	 * @see #setStatus(StatusType145)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getShapeType_Status()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='status' namespace='##targetNamespace'"
	 * @generated
	 */
	StatusType145 getStatus();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getStatus <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Status</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.StatusType145
	 * @see #isSetStatus()
	 * @see #unsetStatus()
	 * @see #getStatus()
	 * @generated
	 */
	void setStatus(StatusType145 value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getStatus <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetStatus()
	 * @see #getStatus()
	 * @see #setStatus(StatusType145)
	 * @generated
	 */
	void unsetStatus();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getStatus <em>Status</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Status</em>' attribute is set.
	 * @see #unsetStatus()
	 * @see #getStatus()
	 * @see #setStatus(StatusType145)
	 * @generated
	 */
	boolean isSetStatus();

	/**
	 * Returns the value of the '<em><b>Translate</b></em>' attribute.
	 * The default value is <code>"no"</code>.
	 * The literals are from the enumeration {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TranslateType69}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Translate</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Translate</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TranslateType69
	 * @see #isSetTranslate()
	 * @see #unsetTranslate()
	 * @see #setTranslate(TranslateType69)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getShapeType_Translate()
	 * @model default="no" unsettable="true"
	 *        extendedMetaData="kind='attribute' name='translate' namespace='##targetNamespace'"
	 * @generated
	 */
	TranslateType69 getTranslate();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getTranslate <em>Translate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Translate</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TranslateType69
	 * @see #isSetTranslate()
	 * @see #unsetTranslate()
	 * @see #getTranslate()
	 * @generated
	 */
	void setTranslate(TranslateType69 value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getTranslate <em>Translate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetTranslate()
	 * @see #getTranslate()
	 * @see #setTranslate(TranslateType69)
	 * @generated
	 */
	void unsetTranslate();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getTranslate <em>Translate</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Translate</em>' attribute is set.
	 * @see #unsetTranslate()
	 * @see #getTranslate()
	 * @see #setTranslate(TranslateType69)
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getShapeType_Xtrc()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='xtrc' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getXtrc();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getXtrc <em>Xtrc</em>}' attribute.
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getShapeType_Xtrf()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='xtrf' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getXtrf();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType#getXtrf <em>Xtrf</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xtrf</em>' attribute.
	 * @see #getXtrf()
	 * @generated
	 */
	void setXtrf(Object value);

} // ShapeType
