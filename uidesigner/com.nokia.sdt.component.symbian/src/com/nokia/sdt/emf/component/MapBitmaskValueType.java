/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;

import org.eclipse.emf.ecore.EObject;

import java.util.List;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Map Bitmask Value Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.MapBitmaskValueType#getProperties <em>Properties</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MapBitmaskValueType#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getMapBitmaskValueType()
 * @model extendedMetaData="name='mapBitmaskValue_._type' kind='empty'"
 * @generated
 */
public interface MapBitmaskValueType extends EObject{
	/**
	 * Returns the value of the '<em><b>Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Properties</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 					List of properties to consider.  May be property paths.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Properties</em>' attribute.
	 * @see #setProperties(List)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMapBitmaskValueType_Properties()
	 * @model unique="false" dataType="com.nokia.sdt.emf.component.ListOfStrings" required="true" many="false"
	 *        extendedMetaData="kind='attribute' name='properties'"
	 * @generated
	 */
    List getProperties();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.MapBitmaskValueType#getProperties <em>Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Properties</em>' attribute.
	 * @see #getProperties()
	 * @generated
	 */
    void setProperties(List value);

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
	 * 					Value to OR into the target expression.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMapBitmaskValueType_Value()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='value'"
	 * @generated
	 */
    String getValue();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.MapBitmaskValueType#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
    void setValue(String value);

} // MapBitmaskValueType
