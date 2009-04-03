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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Experiencelevel Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getExperiencelevelType()
 * @model extendedMetaData="name='experiencelevel_._type'"
 * @generated
 */
public enum ExperiencelevelType implements Enumerator {
	/**
	 * The '<em><b>Novice</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NOVICE_VALUE
	 * @generated
	 * @ordered
	 */
	NOVICE(0, "novice", "novice"),

	/**
	 * The '<em><b>General</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GENERAL_VALUE
	 * @generated
	 * @ordered
	 */
	GENERAL(1, "general", "general"),

	/**
	 * The '<em><b>Expert</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXPERT_VALUE
	 * @generated
	 * @ordered
	 */
	EXPERT(2, "expert", "expert"),

	/**
	 * The '<em><b>Dita Use Conref Target</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DITA_USE_CONREF_TARGET_VALUE
	 * @generated
	 * @ordered
	 */
	DITA_USE_CONREF_TARGET(3, "ditaUseConrefTarget", "-dita-use-conref-target");

	/**
	 * The '<em><b>Novice</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Novice</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NOVICE
	 * @model name="novice"
	 * @generated
	 * @ordered
	 */
	public static final int NOVICE_VALUE = 0;

	/**
	 * The '<em><b>General</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>General</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #GENERAL
	 * @model name="general"
	 * @generated
	 * @ordered
	 */
	public static final int GENERAL_VALUE = 1;

	/**
	 * The '<em><b>Expert</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Expert</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #EXPERT
	 * @model name="expert"
	 * @generated
	 * @ordered
	 */
	public static final int EXPERT_VALUE = 2;

	/**
	 * The '<em><b>Dita Use Conref Target</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Dita Use Conref Target</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DITA_USE_CONREF_TARGET
	 * @model name="ditaUseConrefTarget" literal="-dita-use-conref-target"
	 * @generated
	 * @ordered
	 */
	public static final int DITA_USE_CONREF_TARGET_VALUE = 3;

	/**
	 * An array of all the '<em><b>Experiencelevel Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ExperiencelevelType[] VALUES_ARRAY =
		new ExperiencelevelType[] {
			NOVICE,
			GENERAL,
			EXPERT,
			DITA_USE_CONREF_TARGET,
		};

	/**
	 * A public read-only list of all the '<em><b>Experiencelevel Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ExperiencelevelType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Experiencelevel Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ExperiencelevelType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ExperiencelevelType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Experiencelevel Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ExperiencelevelType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ExperiencelevelType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Experiencelevel Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ExperiencelevelType get(int value) {
		switch (value) {
			case NOVICE_VALUE: return NOVICE;
			case GENERAL_VALUE: return GENERAL;
			case EXPERT_VALUE: return EXPERT;
			case DITA_USE_CONREF_TARGET_VALUE: return DITA_USE_CONREF_TARGET;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private ExperiencelevelType(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //ExperiencelevelType
