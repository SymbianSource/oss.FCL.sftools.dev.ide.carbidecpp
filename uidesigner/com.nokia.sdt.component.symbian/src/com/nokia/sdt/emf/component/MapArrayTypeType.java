/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Map Array Type Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.MapArrayTypeType#getTypeId <em>Type Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getMapArrayTypeType()
 * @model extendedMetaData="name='mapArrayType_._type' kind='elementOnly'"
 * @generated
 */
public interface MapArrayTypeType extends MappingArrayType {

	/**
	 * Returns the value of the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Identifies the id for this particular kind of type mapping.
	 * 						
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type Id</em>' attribute.
	 * @see #setTypeId(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMapArrayTypeType_TypeId()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='typeId'"
	 * @generated
	 */
	String getTypeId();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.MapArrayTypeType#getTypeId <em>Type Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Id</em>' attribute.
	 * @see #getTypeId()
	 * @generated
	 */
	void setTypeId(String value);
} // MapArrayTypeType
