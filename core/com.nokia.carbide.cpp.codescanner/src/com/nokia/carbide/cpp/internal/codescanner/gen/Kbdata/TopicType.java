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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Topic Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getTitle <em>Title</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getTitlealts <em>Titlealts</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getShortdesc <em>Shortdesc</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getAbstract <em>Abstract</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getProlog <em>Prolog</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getBody <em>Body</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getRelatedLinks <em>Related Links</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getTopic <em>Topic</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getAudience <em>Audience</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getBase <em>Base</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getClass_ <em>Class</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getConref <em>Conref</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getDir <em>Dir</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getDITAArchVersion <em>DITA Arch Version</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getDomains <em>Domains</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getId <em>Id</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getImportance <em>Importance</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getLang <em>Lang</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getOtherprops <em>Otherprops</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getOutputclass <em>Outputclass</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getPlatform <em>Platform</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getProduct <em>Product</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getProps <em>Props</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getRev <em>Rev</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getStatus <em>Status</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getTranslate <em>Translate</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getXtrc <em>Xtrc</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getXtrf <em>Xtrf</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getTopicType()
 * @model extendedMetaData="name='topic_._type' kind='elementOnly'"
 * @generated
 */
public interface TopicType extends EObject {
	/**
	 * Returns the value of the '<em><b>Title</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Title</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Title</em>' containment reference.
	 * @see #setTitle(TitleType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getTopicType_Title()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='title' namespace='##targetNamespace'"
	 * @generated
	 */
	TitleType getTitle();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getTitle <em>Title</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Title</em>' containment reference.
	 * @see #getTitle()
	 * @generated
	 */
	void setTitle(TitleType value);

	/**
	 * Returns the value of the '<em><b>Titlealts</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Titlealts</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Titlealts</em>' containment reference.
	 * @see #setTitlealts(TitlealtsType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getTopicType_Titlealts()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='titlealts' namespace='##targetNamespace'"
	 * @generated
	 */
	TitlealtsType getTitlealts();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getTitlealts <em>Titlealts</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Titlealts</em>' containment reference.
	 * @see #getTitlealts()
	 * @generated
	 */
	void setTitlealts(TitlealtsType value);

	/**
	 * Returns the value of the '<em><b>Shortdesc</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shortdesc</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shortdesc</em>' containment reference.
	 * @see #setShortdesc(ShortdescType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getTopicType_Shortdesc()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='shortdesc' namespace='##targetNamespace'"
	 * @generated
	 */
	ShortdescType getShortdesc();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getShortdesc <em>Shortdesc</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shortdesc</em>' containment reference.
	 * @see #getShortdesc()
	 * @generated
	 */
	void setShortdesc(ShortdescType value);

	/**
	 * Returns the value of the '<em><b>Abstract</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Abstract</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Abstract</em>' containment reference.
	 * @see #setAbstract(AbstractType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getTopicType_Abstract()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='abstract' namespace='##targetNamespace'"
	 * @generated
	 */
	AbstractType getAbstract();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getAbstract <em>Abstract</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Abstract</em>' containment reference.
	 * @see #getAbstract()
	 * @generated
	 */
	void setAbstract(AbstractType value);

	/**
	 * Returns the value of the '<em><b>Prolog</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Prolog</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Prolog</em>' containment reference.
	 * @see #setProlog(PrologType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getTopicType_Prolog()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='prolog' namespace='##targetNamespace'"
	 * @generated
	 */
	PrologType getProlog();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getProlog <em>Prolog</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Prolog</em>' containment reference.
	 * @see #getProlog()
	 * @generated
	 */
	void setProlog(PrologType value);

	/**
	 * Returns the value of the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Body</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Body</em>' containment reference.
	 * @see #setBody(BodyType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getTopicType_Body()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='body' namespace='##targetNamespace'"
	 * @generated
	 */
	BodyType getBody();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getBody <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Body</em>' containment reference.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(BodyType value);

	/**
	 * Returns the value of the '<em><b>Related Links</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Related Links</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Related Links</em>' containment reference.
	 * @see #setRelatedLinks(RelatedLinksType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getTopicType_RelatedLinks()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='related-links' namespace='##targetNamespace'"
	 * @generated
	 */
	RelatedLinksType getRelatedLinks();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getRelatedLinks <em>Related Links</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Related Links</em>' containment reference.
	 * @see #getRelatedLinks()
	 * @generated
	 */
	void setRelatedLinks(RelatedLinksType value);

	/**
	 * Returns the value of the '<em><b>Topic</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Topic</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Topic</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getTopicType_Topic()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='topic' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<TopicType> getTopic();

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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getTopicType_Audience()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='audience' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getAudience();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getAudience <em>Audience</em>}' attribute.
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getTopicType_Base()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='base' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getBase();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getBase <em>Base</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base</em>' attribute.
	 * @see #getBase()
	 * @generated
	 */
	void setBase(Object value);

	/**
	 * Returns the value of the '<em><b>Class</b></em>' attribute.
	 * The default value is <code>"- topic/topic "</code>.
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getTopicType_Class()
	 * @model default="- topic/topic " unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='class' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getClass_();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getClass_ <em>Class</em>}' attribute.
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
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getClass_ <em>Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetClass()
	 * @see #getClass_()
	 * @see #setClass(Object)
	 * @generated
	 */
	void unsetClass();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getClass_ <em>Class</em>}' attribute is set.
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getTopicType_Conref()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='conref' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getConref();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getConref <em>Conref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Conref</em>' attribute.
	 * @see #getConref()
	 * @generated
	 */
	void setConref(Object value);

	/**
	 * Returns the value of the '<em><b>Dir</b></em>' attribute.
	 * The literals are from the enumeration {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DirType165}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dir</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dir</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DirType165
	 * @see #isSetDir()
	 * @see #unsetDir()
	 * @see #setDir(DirType165)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getTopicType_Dir()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='dir' namespace='##targetNamespace'"
	 * @generated
	 */
	DirType165 getDir();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getDir <em>Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dir</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DirType165
	 * @see #isSetDir()
	 * @see #unsetDir()
	 * @see #getDir()
	 * @generated
	 */
	void setDir(DirType165 value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getDir <em>Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDir()
	 * @see #getDir()
	 * @see #setDir(DirType165)
	 * @generated
	 */
	void unsetDir();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getDir <em>Dir</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Dir</em>' attribute is set.
	 * @see #unsetDir()
	 * @see #getDir()
	 * @see #setDir(DirType165)
	 * @generated
	 */
	boolean isSetDir();

	/**
	 * Returns the value of the '<em><b>DITA Arch Version</b></em>' attribute.
	 * The default value is <code>"1.1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>DITA Arch Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>DITA Arch Version</em>' attribute.
	 * @see #isSetDITAArchVersion()
	 * @see #unsetDITAArchVersion()
	 * @see #setDITAArchVersion(Object)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getTopicType_DITAArchVersion()
	 * @model default="1.1" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='DITAArchVersion' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getDITAArchVersion();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getDITAArchVersion <em>DITA Arch Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>DITA Arch Version</em>' attribute.
	 * @see #isSetDITAArchVersion()
	 * @see #unsetDITAArchVersion()
	 * @see #getDITAArchVersion()
	 * @generated
	 */
	void setDITAArchVersion(Object value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getDITAArchVersion <em>DITA Arch Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDITAArchVersion()
	 * @see #getDITAArchVersion()
	 * @see #setDITAArchVersion(Object)
	 * @generated
	 */
	void unsetDITAArchVersion();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getDITAArchVersion <em>DITA Arch Version</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>DITA Arch Version</em>' attribute is set.
	 * @see #unsetDITAArchVersion()
	 * @see #getDITAArchVersion()
	 * @see #setDITAArchVersion(Object)
	 * @generated
	 */
	boolean isSetDITAArchVersion();

	/**
	 * Returns the value of the '<em><b>Domains</b></em>' attribute.
	 * The default value is <code>"(topic ui-d)                            (topic hi-d)                            (topic pr-d)                            (topic sw-d)                            (topic ut-d)                            (topic indexing-d)                            a(base kbdataFw)"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domains</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domains</em>' attribute.
	 * @see #isSetDomains()
	 * @see #unsetDomains()
	 * @see #setDomains(Object)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getTopicType_Domains()
	 * @model default="(topic ui-d)                            (topic hi-d)                            (topic pr-d)                            (topic sw-d)                            (topic ut-d)                            (topic indexing-d)                            a(base kbdataFw)" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='domains' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getDomains();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getDomains <em>Domains</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domains</em>' attribute.
	 * @see #isSetDomains()
	 * @see #unsetDomains()
	 * @see #getDomains()
	 * @generated
	 */
	void setDomains(Object value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getDomains <em>Domains</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDomains()
	 * @see #getDomains()
	 * @see #setDomains(Object)
	 * @generated
	 */
	void unsetDomains();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getDomains <em>Domains</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Domains</em>' attribute is set.
	 * @see #unsetDomains()
	 * @see #getDomains()
	 * @see #setDomains(Object)
	 * @generated
	 */
	boolean isSetDomains();

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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getTopicType_Id()
	 * @model id="true" dataType="org.eclipse.emf.ecore.xml.type.ID" required="true"
	 *        extendedMetaData="kind='attribute' name='id' namespace='##targetNamespace'"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Importance</b></em>' attribute.
	 * The literals are from the enumeration {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ImportanceType171}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Importance</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Importance</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ImportanceType171
	 * @see #isSetImportance()
	 * @see #unsetImportance()
	 * @see #setImportance(ImportanceType171)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getTopicType_Importance()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='importance' namespace='##targetNamespace'"
	 * @generated
	 */
	ImportanceType171 getImportance();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getImportance <em>Importance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Importance</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ImportanceType171
	 * @see #isSetImportance()
	 * @see #unsetImportance()
	 * @see #getImportance()
	 * @generated
	 */
	void setImportance(ImportanceType171 value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getImportance <em>Importance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetImportance()
	 * @see #getImportance()
	 * @see #setImportance(ImportanceType171)
	 * @generated
	 */
	void unsetImportance();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getImportance <em>Importance</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Importance</em>' attribute is set.
	 * @see #unsetImportance()
	 * @see #getImportance()
	 * @see #setImportance(ImportanceType171)
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getTopicType_Lang()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN"
	 *        extendedMetaData="kind='attribute' name='lang' namespace='##targetNamespace'"
	 * @generated
	 */
	String getLang();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getLang <em>Lang</em>}' attribute.
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getTopicType_Otherprops()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='otherprops' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getOtherprops();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getOtherprops <em>Otherprops</em>}' attribute.
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getTopicType_Outputclass()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='outputclass' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getOutputclass();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getOutputclass <em>Outputclass</em>}' attribute.
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getTopicType_Platform()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='platform' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getPlatform();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getPlatform <em>Platform</em>}' attribute.
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getTopicType_Product()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='product' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getProduct();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getProduct <em>Product</em>}' attribute.
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getTopicType_Props()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='props' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getProps();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getProps <em>Props</em>}' attribute.
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getTopicType_Rev()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='rev' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getRev();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getRev <em>Rev</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rev</em>' attribute.
	 * @see #getRev()
	 * @generated
	 */
	void setRev(Object value);

	/**
	 * Returns the value of the '<em><b>Status</b></em>' attribute.
	 * The literals are from the enumeration {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.StatusType108}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Status</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Status</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.StatusType108
	 * @see #isSetStatus()
	 * @see #unsetStatus()
	 * @see #setStatus(StatusType108)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getTopicType_Status()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='status' namespace='##targetNamespace'"
	 * @generated
	 */
	StatusType108 getStatus();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getStatus <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Status</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.StatusType108
	 * @see #isSetStatus()
	 * @see #unsetStatus()
	 * @see #getStatus()
	 * @generated
	 */
	void setStatus(StatusType108 value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getStatus <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetStatus()
	 * @see #getStatus()
	 * @see #setStatus(StatusType108)
	 * @generated
	 */
	void unsetStatus();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getStatus <em>Status</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Status</em>' attribute is set.
	 * @see #unsetStatus()
	 * @see #getStatus()
	 * @see #setStatus(StatusType108)
	 * @generated
	 */
	boolean isSetStatus();

	/**
	 * Returns the value of the '<em><b>Translate</b></em>' attribute.
	 * The literals are from the enumeration {@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TranslateType174}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Translate</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Translate</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TranslateType174
	 * @see #isSetTranslate()
	 * @see #unsetTranslate()
	 * @see #setTranslate(TranslateType174)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getTopicType_Translate()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='translate' namespace='##targetNamespace'"
	 * @generated
	 */
	TranslateType174 getTranslate();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getTranslate <em>Translate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Translate</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TranslateType174
	 * @see #isSetTranslate()
	 * @see #unsetTranslate()
	 * @see #getTranslate()
	 * @generated
	 */
	void setTranslate(TranslateType174 value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getTranslate <em>Translate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetTranslate()
	 * @see #getTranslate()
	 * @see #setTranslate(TranslateType174)
	 * @generated
	 */
	void unsetTranslate();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getTranslate <em>Translate</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Translate</em>' attribute is set.
	 * @see #unsetTranslate()
	 * @see #getTranslate()
	 * @see #setTranslate(TranslateType174)
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getTopicType_Xtrc()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='xtrc' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getXtrc();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getXtrc <em>Xtrc</em>}' attribute.
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
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getTopicType_Xtrf()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='xtrf' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getXtrf();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType#getXtrf <em>Xtrf</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xtrf</em>' attribute.
	 * @see #getXtrf()
	 * @generated
	 */
	void setXtrf(Object value);

} // TopicType
