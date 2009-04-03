/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Enum Element Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.EnumElementType#getDisplayValue <em>Display Value</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.EnumElementType#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getEnumElementType()
 * @model extendedMetaData="name='enumElement_._type' kind='empty'"
 * @generated
 */
public interface EnumElementType extends EObject{
	/**
	 * Returns the value of the '<em><b>Display Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Display Value</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The displayable value, which should be localized.
	 * 							
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Display Value</em>' attribute.
	 * @see #setDisplayValue(Object)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getEnumElementType_DisplayValue()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
	 *        extendedMetaData="kind='attribute' name='displayValue'"
	 * @generated
	 */
    Object getDisplayValue();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.EnumElementType#getDisplayValue <em>Display Value</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Value</em>' attribute.
	 * @see #getDisplayValue()
	 * @generated
	 */
    void setDisplayValue(Object value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Value</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The internal value, which is not localizable.
	 * 							
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(Object)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getEnumElementType_Value()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType" required="true"
	 *        extendedMetaData="kind='attribute' name='value'"
	 * @generated
	 */
    Object getValue();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.EnumElementType#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
    void setValue(Object value);

} // EnumElementType
