/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.FeatureMap;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Map Into Property Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.MapIntoPropertyType#getTwoWayMappingGroup <em>Two Way Mapping Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MapIntoPropertyType#getTwoWayMapping <em>Two Way Mapping</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MapIntoPropertyType#getProperty <em>Property</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getMapIntoPropertyType()
 * @model extendedMetaData="name='mapIntoProperty_._type' kind='elementOnly'"
 * @generated
 */
public interface MapIntoPropertyType extends TwoWayMappingType {
	/**
	 * Returns the value of the '<em><b>Two Way Mapping Group</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Two Way Mapping Group</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Two Way Mapping Group</em>' attribute list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMapIntoPropertyType_TwoWayMappingGroup()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" required="true" many="false"
	 *        extendedMetaData="kind='group' name='twoWayMapping:group' namespace='##targetNamespace'"
	 * @generated
	 */
	FeatureMap getTwoWayMappingGroup();

	/**
	 * Returns the value of the '<em><b>Two Way Mapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Two Way Mapping</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Two Way Mapping</em>' containment reference.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMapIntoPropertyType_TwoWayMapping()
	 * @model containment="true" required="true" transient="true" changeable="false" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='twoWayMapping' namespace='##targetNamespace' group='twoWayMapping:group'"
	 * @generated
	 */
	TwoWayMappingType getTwoWayMapping();

	/**
	 * Returns the value of the '<em><b>Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						The property path providing the value.
	 * 						
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Property</em>' attribute.
	 * @see #setProperty(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMapIntoPropertyType_Property()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='property'"
	 * @generated
	 */
	String getProperty();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.MapIntoPropertyType#getProperty <em>Property</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property</em>' attribute.
	 * @see #getProperty()
	 * @generated
	 */
	void setProperty(String value);

} // MapIntoPropertyType
