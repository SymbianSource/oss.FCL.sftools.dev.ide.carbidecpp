/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mapping Array Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 		Base for mapping arrays from sequence properties or component instance children.
 * 		
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.MappingArrayType#getTwoWayMappingGroup <em>Two Way Mapping Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MappingArrayType#getTwoWayMapping <em>Two Way Mapping</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MappingArrayType#getSelect <em>Select</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingArrayType()
 * @model extendedMetaData="name='mappingArrayType' kind='elementOnly'"
 * @generated
 */
public interface MappingArrayType extends TwoWayMappingType{
	/**
	 * Returns the value of the '<em><b>Two Way Mapping Group</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Two Way Mapping Group</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 								This is restricted to mapXXXElement, mapElementFromType, or mapIntoProperty, but we can't represent this in XSD.
	 * 								
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Two Way Mapping Group</em>' attribute list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingArrayType_TwoWayMappingGroup()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="false"
	 *        extendedMetaData="kind='group' name='twoWayMapping:group' namespace='##targetNamespace'"
	 * @generated
	 */
    FeatureMap getTwoWayMappingGroup();

	/**
	 * Returns the value of the '<em><b>Two Way Mapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Two Way Mapping</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 								This is restricted to mapXXXElement, mapElementFromType, or mapIntoProperty, but we can't represent this in XSD.
	 * 								
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Two Way Mapping</em>' containment reference.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingArrayType_TwoWayMapping()
	 * @model containment="true" transient="true" changeable="false" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='twoWayMapping' namespace='##targetNamespace' group='twoWayMapping:group'"
	 * @generated
	 */
    TwoWayMappingType getTwoWayMapping();

	/**
	 * Returns the value of the '<em><b>Select</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Select</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		This element encapsulates choice elements which allow conditional
	 * 		source mapping.  One choice must match or an error results (you can
	 * 		use an empty choice to match the default case if necessary).  Only
	 * 		the first matching choice is considered.
	 * 			<p xmlns="http://www.nokia.com/sdt/emf/component">
	 * 				Only use one attribute (property, attribute, propertyExists, isComponentInstanceOf)
	 * 				 for the select statement.
	 * 			</p>
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Select</em>' containment reference.
	 * @see #setSelect(SelectType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingArrayType_Select()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='select' namespace='##targetNamespace'"
	 * @generated
	 */
    SelectType getSelect();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.MappingArrayType#getSelect <em>Select</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Select</em>' containment reference.
	 * @see #getSelect()
	 * @generated
	 */
    void setSelect(SelectType value);

} // MappingArrayType
