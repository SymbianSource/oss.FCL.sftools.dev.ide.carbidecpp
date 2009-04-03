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
package com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Use Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage#getUseType()
 * @model
 * @generated
 */
public final class UseType extends AbstractEnumerator {
	/**
	 * The '<em><b>Prepend</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Prepend</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PREPEND_LITERAL
	 * @model name="prepend"
	 * @generated
	 * @ordered
	 */
	public static final int PREPEND = 0;

	/**
	 * The '<em><b>Replace</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Replace</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #REPLACE_LITERAL
	 * @model name="replace"
	 * @generated
	 * @ordered
	 */
	public static final int REPLACE = 1;

	/**
	 * The '<em><b>Append</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Append</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #APPEND_LITERAL
	 * @model name="append"
	 * @generated
	 * @ordered
	 */
	public static final int APPEND = 2;

	/**
	 * The '<em><b>Undefine</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Undefine</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #UNDEFINE_LITERAL
	 * @model name="undefine"
	 * @generated
	 * @ordered
	 */
	public static final int UNDEFINE = 3;

	/**
	 * The '<em><b>Prepend</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PREPEND
	 * @generated
	 * @ordered
	 */
	public static final UseType PREPEND_LITERAL = new UseType(PREPEND, "prepend", "prepend");

	/**
	 * The '<em><b>Replace</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #REPLACE
	 * @generated
	 * @ordered
	 */
	public static final UseType REPLACE_LITERAL = new UseType(REPLACE, "replace", "replace");

	/**
	 * The '<em><b>Append</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #APPEND
	 * @generated
	 * @ordered
	 */
	public static final UseType APPEND_LITERAL = new UseType(APPEND, "append", "append");

	/**
	 * The '<em><b>Undefine</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNDEFINE
	 * @generated
	 * @ordered
	 */
	public static final UseType UNDEFINE_LITERAL = new UseType(UNDEFINE, "undefine", "undefine");

	/**
	 * An array of all the '<em><b>Use Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final UseType[] VALUES_ARRAY =
		new UseType[] {
			PREPEND_LITERAL,
			REPLACE_LITERAL,
			APPEND_LITERAL,
			UNDEFINE_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Use Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Use Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static UseType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			UseType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Use Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static UseType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			UseType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Use Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static UseType get(int value) {
		switch (value) {
			case PREPEND: return PREPEND_LITERAL;
			case REPLACE: return REPLACE_LITERAL;
			case APPEND: return APPEND_LITERAL;
			case UNDEFINE: return UNDEFINE_LITERAL;
		}
		return null;	
	}

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private UseType(int value, String name, String literal) {
		super(value, name, literal);
	}

} //UseType
