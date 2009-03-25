/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Standalone Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * 
 * 		The selector for controlling standalone resource mapping.
 * 		
 * <!-- end-model-doc -->
 * @see com.nokia.sdt.emf.component.ComponentPackage#getStandaloneType()
 * @model
 * @generated
 */
public final class StandaloneType extends AbstractEnumerator {
	/**
	 * The '<em><b>False</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 				Deprecated value corresponding to "default".
	 * 				
	 * <!-- end-model-doc -->
	 * @see #FALSE_LITERAL
	 * @model name="false"
	 * @generated
	 * @ordered
	 */
	public static final int FALSE = 0;

	/**
	 * The '<em><b>True</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 				Depecated value corresponding to "always".
	 * 				
	 * <!-- end-model-doc -->
	 * @see #TRUE_LITERAL
	 * @model name="true"
	 * @generated
	 * @ordered
	 */
	public static final int TRUE = 1;

	/**
	 * The '<em><b>Never</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 				Never emit the resource by itself.  Only emit it if another resource
	 * 				references it.  By using this, a component may generate two forms of
	 * 				resource (with different resource ids) and let a container or reference
	 * 				select which resource it wants, without also generating the unused variant.
	 * 				
	 * <!-- end-model-doc -->
	 * @see #NEVER_LITERAL
	 * @model name="never"
	 * @generated
	 * @ordered
	 */
	public static final int NEVER = 2;

	/**
	 * The '<em><b>Default</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 				Emit the resource by itself if it is not generated as an expression or
	 * 				by a LINK or LLINK.
	 * 				
	 * <!-- end-model-doc -->
	 * @see #DEFAULT_LITERAL
	 * @model name="default"
	 * @generated
	 * @ordered
	 */
	public static final int DEFAULT = 3;

	/**
	 * The '<em><b>Always</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 				Always emit the resource by itself, even if already generated as an expression.
	 * 				
	 * <!-- end-model-doc -->
	 * @see #ALWAYS_LITERAL
	 * @model name="always"
	 * @generated
	 * @ordered
	 */
	public static final int ALWAYS = 4;

	/**
	 * The '<em><b>False</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FALSE
	 * @generated
	 * @ordered
	 */
	public static final StandaloneType FALSE_LITERAL = new StandaloneType(FALSE, "false", "false");

	/**
	 * The '<em><b>True</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TRUE
	 * @generated
	 * @ordered
	 */
	public static final StandaloneType TRUE_LITERAL = new StandaloneType(TRUE, "true", "true");

	/**
	 * The '<em><b>Never</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NEVER
	 * @generated
	 * @ordered
	 */
	public static final StandaloneType NEVER_LITERAL = new StandaloneType(NEVER, "never", "never");

	/**
	 * The '<em><b>Default</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DEFAULT
	 * @generated
	 * @ordered
	 */
	public static final StandaloneType DEFAULT_LITERAL = new StandaloneType(DEFAULT, "default", "default");

	/**
	 * The '<em><b>Always</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ALWAYS
	 * @generated
	 * @ordered
	 */
	public static final StandaloneType ALWAYS_LITERAL = new StandaloneType(ALWAYS, "always", "always");

	/**
	 * An array of all the '<em><b>Standalone Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final StandaloneType[] VALUES_ARRAY =
		new StandaloneType[] {
			FALSE_LITERAL,
			TRUE_LITERAL,
			NEVER_LITERAL,
			DEFAULT_LITERAL,
			ALWAYS_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Standalone Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Standalone Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static StandaloneType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			StandaloneType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Standalone Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static StandaloneType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			StandaloneType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Standalone Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static StandaloneType get(int value) {
		switch (value) {
			case FALSE: return FALSE_LITERAL;
			case TRUE: return TRUE_LITERAL;
			case NEVER: return NEVER_LITERAL;
			case DEFAULT: return DEFAULT_LITERAL;
			case ALWAYS: return ALWAYS_LITERAL;
		}
		return null;
	}

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private StandaloneType(int value, String name, String literal) {
		super(value, name, literal);
	}

} //StandaloneType
