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
 * A representation of the literals of the enumeration '<em><b>Property Data Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * Simple, predefined property types.
 * 		
 * <!-- end-model-doc -->
 * @see com.nokia.sdt.emf.component.ComponentPackage#getPropertyDataType()
 * @model
 * @generated
 */
public final class PropertyDataType extends AbstractEnumerator {
	/**
	 * The '<em><b>Void</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Void</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The empty type. Do not use for real properties.
	 * 				
	 * <!-- end-model-doc -->
	 * @see #VOID_LITERAL
	 * @model name="void"
	 * @generated
	 * @ordered
	 */
	public static final int VOID = 0;

	/**
	 * The '<em><b>Boolean</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Boolean</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 				
	 * <!-- end-model-doc -->
	 * @see #BOOLEAN_LITERAL
	 * @model name="boolean"
	 * @generated
	 * @ordered
	 */
	public static final int BOOLEAN = 1;

	/**
	 * The '<em><b>Integer</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Integer</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Four byte integer properties. Ranges may be constrained on property declarations.
	 * 				
	 * <!-- end-model-doc -->
	 * @see #INTEGER_LITERAL
	 * @model name="integer"
	 * @generated
	 * @ordered
	 */
	public static final int INTEGER = 2;

	/**
	 * The '<em><b>Float</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Float</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Floating point values. Ranges may be constrained on property declarations.
	 * 				
	 * <!-- end-model-doc -->
	 * @see #FLOAT_LITERAL
	 * @model name="float"
	 * @generated
	 * @ordered
	 */
	public static final int FLOAT = 3;

	/**
	 * The '<em><b>String</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>String</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Non-localized string literals
	 * 				
	 * <!-- end-model-doc -->
	 * @see #STRING_LITERAL
	 * @model name="string"
	 * @generated
	 * @ordered
	 */
	public static final int STRING = 4;

	/**
	 * The '<em><b>Localized String</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Localized String</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Localized string literals. Users may enter different values for each language.
	 * 				
	 * <!-- end-model-doc -->
	 * @see #LOCALIZED_STRING_LITERAL
	 * @model name="localizedString"
	 * @generated
	 * @ordered
	 */
	public static final int LOCALIZED_STRING = 5;

	/**
	 * The '<em><b>Unique Name</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Unique Name</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Unique, non-localized string. Values are forced to be unique across the data model.
	 * 				
	 * <!-- end-model-doc -->
	 * @see #UNIQUE_NAME_LITERAL
	 * @model name="uniqueName"
	 * @generated
	 * @ordered
	 */
	public static final int UNIQUE_NAME = 6;

	/**
	 * The '<em><b>Void</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #VOID
	 * @generated
	 * @ordered
	 */
	public static final PropertyDataType VOID_LITERAL = new PropertyDataType(VOID, "void", "void");

	/**
	 * The '<em><b>Boolean</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BOOLEAN
	 * @generated
	 * @ordered
	 */
	public static final PropertyDataType BOOLEAN_LITERAL = new PropertyDataType(BOOLEAN, "boolean", "boolean");

	/**
	 * The '<em><b>Integer</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INTEGER
	 * @generated
	 * @ordered
	 */
	public static final PropertyDataType INTEGER_LITERAL = new PropertyDataType(INTEGER, "integer", "integer");

	/**
	 * The '<em><b>Float</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FLOAT
	 * @generated
	 * @ordered
	 */
	public static final PropertyDataType FLOAT_LITERAL = new PropertyDataType(FLOAT, "float", "float");

	/**
	 * The '<em><b>String</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STRING
	 * @generated
	 * @ordered
	 */
	public static final PropertyDataType STRING_LITERAL = new PropertyDataType(STRING, "string", "string");

	/**
	 * The '<em><b>Localized String</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LOCALIZED_STRING
	 * @generated
	 * @ordered
	 */
	public static final PropertyDataType LOCALIZED_STRING_LITERAL = new PropertyDataType(LOCALIZED_STRING, "localizedString", "localizedString");

	/**
	 * The '<em><b>Unique Name</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNIQUE_NAME
	 * @generated
	 * @ordered
	 */
	public static final PropertyDataType UNIQUE_NAME_LITERAL = new PropertyDataType(UNIQUE_NAME, "uniqueName", "uniqueName");

	/**
	 * An array of all the '<em><b>Property Data Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final PropertyDataType[] VALUES_ARRAY =
		new PropertyDataType[] {
			VOID_LITERAL,
			BOOLEAN_LITERAL,
			INTEGER_LITERAL,
			FLOAT_LITERAL,
			STRING_LITERAL,
			LOCALIZED_STRING_LITERAL,
			UNIQUE_NAME_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Property Data Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Property Data Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PropertyDataType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PropertyDataType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Property Data Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PropertyDataType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PropertyDataType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Property Data Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PropertyDataType get(int value) {
		switch (value) {
			case VOID: return VOID_LITERAL;
			case BOOLEAN: return BOOLEAN_LITERAL;
			case INTEGER: return INTEGER_LITERAL;
			case FLOAT: return FLOAT_LITERAL;
			case STRING: return STRING_LITERAL;
			case LOCALIZED_STRING: return LOCALIZED_STRING_LITERAL;
			case UNIQUE_NAME: return UNIQUE_NAME_LITERAL;
		}
		return null;
	}

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private PropertyDataType(int value, String name, String literal) {
		super(value, name, literal);
	}

} //PropertyDataType
