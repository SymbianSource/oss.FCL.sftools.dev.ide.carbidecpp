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
 * A representation of the model object '<em><b>Map Enum Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.MapEnumType#getEnumerator <em>Enumerator</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MapEnumType#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getMapEnumType()
 * @model extendedMetaData="name='mapEnum_._type' kind='empty'"
 * @generated
 */
public interface MapEnumType extends EObject{
	/**
	 * Returns the value of the '<em><b>Enumerator</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Enumerator</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 					The RSS enumerator.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Enumerator</em>' attribute.
	 * @see #setEnumerator(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMapEnumType_Enumerator()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='enumerator'"
	 * @generated
	 */
    String getEnumerator();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.MapEnumType#getEnumerator <em>Enumerator</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enumerator</em>' attribute.
	 * @see #getEnumerator()
	 * @generated
	 */
    void setEnumerator(String value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Value</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 					The property value.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMapEnumType_Value()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='value'"
	 * @generated
	 */
    String getValue();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.MapEnumType#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
    void setValue(String value);

} // MapEnumType
