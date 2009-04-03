/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;

import org.eclipse.emf.common.util.AbstractEnumerator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Reference Scope Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * Enumeration used for scoping references within the model
 * 		
 * <!-- end-model-doc -->
 * @see com.nokia.sdt.emf.component.ComponentPackage#getReferenceScopeType()
 * @model
 * @generated
 */
public final class ReferenceScopeType extends AbstractEnumerator {
	/**
	 * The '<em><b>Model</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Property can reference any valid instance in the model
	 * 				
	 * <!-- end-model-doc -->
	 * @see #MODEL_LITERAL
	 * @model name="model"
	 * @generated
	 * @ordered
	 */
	public static final int MODEL = 0;

	/**
	 * The '<em><b>Children</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Property can reference any children of the referencing instance
	 * 				
	 * <!-- end-model-doc -->
	 * @see #CHILDREN_LITERAL
	 * @model name="children"
	 * @generated
	 * @ordered
	 */
	public static final int CHILDREN = 1;

	/**
	 * The '<em><b>Siblings</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Property can reference any siblings or children of the referencing instance
	 * 				
	 * <!-- end-model-doc -->
	 * @see #SIBLINGS_LITERAL
	 * @model name="siblings"
	 * @generated
	 * @ordered
	 */
	public static final int SIBLINGS = 2;

	/**
	 * The '<em><b>Model</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MODEL
	 * @generated
	 * @ordered
	 */
	public static final ReferenceScopeType MODEL_LITERAL = new ReferenceScopeType(MODEL, "model", "model");

	/**
	 * The '<em><b>Children</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHILDREN
	 * @generated
	 * @ordered
	 */
	public static final ReferenceScopeType CHILDREN_LITERAL = new ReferenceScopeType(CHILDREN, "children", "children");

	/**
	 * The '<em><b>Siblings</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SIBLINGS
	 * @generated
	 * @ordered
	 */
	public static final ReferenceScopeType SIBLINGS_LITERAL = new ReferenceScopeType(SIBLINGS, "siblings", "siblings");

	/**
	 * An array of all the '<em><b>Reference Scope Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ReferenceScopeType[] VALUES_ARRAY =
		new ReferenceScopeType[] {
			MODEL_LITERAL,
			CHILDREN_LITERAL,
			SIBLINGS_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Reference Scope Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Reference Scope Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ReferenceScopeType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ReferenceScopeType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Reference Scope Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ReferenceScopeType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ReferenceScopeType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Reference Scope Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ReferenceScopeType get(int value) {
		switch (value) {
			case MODEL: return MODEL_LITERAL;
			case CHILDREN: return CHILDREN_LITERAL;
			case SIBLINGS: return SIBLINGS_LITERAL;
		}
		return null;
	}

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private ReferenceScopeType(int value, String name, String literal) {
		super(value, name, literal);
	}

} //ReferenceScopeType
