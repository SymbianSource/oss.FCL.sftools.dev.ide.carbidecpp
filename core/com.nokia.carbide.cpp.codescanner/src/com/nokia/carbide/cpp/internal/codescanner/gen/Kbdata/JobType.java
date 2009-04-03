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
 * A representation of the literals of the enumeration '<em><b>Job Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getJobType()
 * @model extendedMetaData="name='job_._type'"
 * @generated
 */
public enum JobType implements Enumerator {
	/**
	 * The '<em><b>Installing</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INSTALLING_VALUE
	 * @generated
	 * @ordered
	 */
	INSTALLING(0, "installing", "installing"),

	/**
	 * The '<em><b>Customizing</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CUSTOMIZING_VALUE
	 * @generated
	 * @ordered
	 */
	CUSTOMIZING(1, "customizing", "customizing"),

	/**
	 * The '<em><b>Administering</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ADMINISTERING_VALUE
	 * @generated
	 * @ordered
	 */
	ADMINISTERING(2, "administering", "administering"),

	/**
	 * The '<em><b>Programming</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROGRAMMING_VALUE
	 * @generated
	 * @ordered
	 */
	PROGRAMMING(3, "programming", "programming"),

	/**
	 * The '<em><b>Using</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #USING_VALUE
	 * @generated
	 * @ordered
	 */
	USING(4, "using", "using"),

	/**
	 * The '<em><b>Maintaining</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MAINTAINING_VALUE
	 * @generated
	 * @ordered
	 */
	MAINTAINING(5, "maintaining", "maintaining"),

	/**
	 * The '<em><b>Troubleshooting</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TROUBLESHOOTING_VALUE
	 * @generated
	 * @ordered
	 */
	TROUBLESHOOTING(6, "troubleshooting", "troubleshooting"),

	/**
	 * The '<em><b>Evaluating</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EVALUATING_VALUE
	 * @generated
	 * @ordered
	 */
	EVALUATING(7, "evaluating", "evaluating"),

	/**
	 * The '<em><b>Planning</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PLANNING_VALUE
	 * @generated
	 * @ordered
	 */
	PLANNING(8, "planning", "planning"),

	/**
	 * The '<em><b>Migrating</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MIGRATING_VALUE
	 * @generated
	 * @ordered
	 */
	MIGRATING(9, "migrating", "migrating"),

	/**
	 * The '<em><b>Other</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OTHER_VALUE
	 * @generated
	 * @ordered
	 */
	OTHER(10, "other", "other"),

	/**
	 * The '<em><b>Dita Use Conref Target</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DITA_USE_CONREF_TARGET_VALUE
	 * @generated
	 * @ordered
	 */
	DITA_USE_CONREF_TARGET(11, "ditaUseConrefTarget", "-dita-use-conref-target");

	/**
	 * The '<em><b>Installing</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Installing</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #INSTALLING
	 * @model name="installing"
	 * @generated
	 * @ordered
	 */
	public static final int INSTALLING_VALUE = 0;

	/**
	 * The '<em><b>Customizing</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Customizing</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CUSTOMIZING
	 * @model name="customizing"
	 * @generated
	 * @ordered
	 */
	public static final int CUSTOMIZING_VALUE = 1;

	/**
	 * The '<em><b>Administering</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Administering</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ADMINISTERING
	 * @model name="administering"
	 * @generated
	 * @ordered
	 */
	public static final int ADMINISTERING_VALUE = 2;

	/**
	 * The '<em><b>Programming</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Programming</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PROGRAMMING
	 * @model name="programming"
	 * @generated
	 * @ordered
	 */
	public static final int PROGRAMMING_VALUE = 3;

	/**
	 * The '<em><b>Using</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Using</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #USING
	 * @model name="using"
	 * @generated
	 * @ordered
	 */
	public static final int USING_VALUE = 4;

	/**
	 * The '<em><b>Maintaining</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Maintaining</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MAINTAINING
	 * @model name="maintaining"
	 * @generated
	 * @ordered
	 */
	public static final int MAINTAINING_VALUE = 5;

	/**
	 * The '<em><b>Troubleshooting</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Troubleshooting</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TROUBLESHOOTING
	 * @model name="troubleshooting"
	 * @generated
	 * @ordered
	 */
	public static final int TROUBLESHOOTING_VALUE = 6;

	/**
	 * The '<em><b>Evaluating</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Evaluating</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #EVALUATING
	 * @model name="evaluating"
	 * @generated
	 * @ordered
	 */
	public static final int EVALUATING_VALUE = 7;

	/**
	 * The '<em><b>Planning</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Planning</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PLANNING
	 * @model name="planning"
	 * @generated
	 * @ordered
	 */
	public static final int PLANNING_VALUE = 8;

	/**
	 * The '<em><b>Migrating</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Migrating</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MIGRATING
	 * @model name="migrating"
	 * @generated
	 * @ordered
	 */
	public static final int MIGRATING_VALUE = 9;

	/**
	 * The '<em><b>Other</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Other</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #OTHER
	 * @model name="other"
	 * @generated
	 * @ordered
	 */
	public static final int OTHER_VALUE = 10;

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
	public static final int DITA_USE_CONREF_TARGET_VALUE = 11;

	/**
	 * An array of all the '<em><b>Job Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final JobType[] VALUES_ARRAY =
		new JobType[] {
			INSTALLING,
			CUSTOMIZING,
			ADMINISTERING,
			PROGRAMMING,
			USING,
			MAINTAINING,
			TROUBLESHOOTING,
			EVALUATING,
			PLANNING,
			MIGRATING,
			OTHER,
			DITA_USE_CONREF_TARGET,
		};

	/**
	 * A public read-only list of all the '<em><b>Job Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<JobType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Job Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static JobType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			JobType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Job Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static JobType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			JobType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Job Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static JobType get(int value) {
		switch (value) {
			case INSTALLING_VALUE: return INSTALLING;
			case CUSTOMIZING_VALUE: return CUSTOMIZING;
			case ADMINISTERING_VALUE: return ADMINISTERING;
			case PROGRAMMING_VALUE: return PROGRAMMING;
			case USING_VALUE: return USING;
			case MAINTAINING_VALUE: return MAINTAINING;
			case TROUBLESHOOTING_VALUE: return TROUBLESHOOTING;
			case EVALUATING_VALUE: return EVALUATING;
			case PLANNING_VALUE: return PLANNING;
			case MIGRATING_VALUE: return MIGRATING;
			case OTHER_VALUE: return OTHER;
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
	private JobType(int value, String name, String literal) {
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
	
} //JobType
