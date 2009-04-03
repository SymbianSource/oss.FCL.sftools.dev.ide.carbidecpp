/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.FeatureMap;

import java.util.List;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mapping Bitmask Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 		Base for mapping sets of boolean properties to a single bitmask expression in RSS.
 * 		
 * 		When mapping this element, a working set of the includedProperties is created.
 * 		Each mapBitmaskValue element is considered in turn, and if all the properties
 * 		it references are present in the working set and have the value "true", 
 * 		the matching value is OR'ed into the target expression, and those properties removed from the working set.
 * 		Once the working set is empty, the mapping is complete.  If all mapBitmaskValues are
 * 		exhausted but the working set is non-empty, this is a component error.
 * 		
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.MappingBitmaskType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MappingBitmaskType#getMapBitmaskValue <em>Map Bitmask Value</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MappingBitmaskType#getIncludedProperties <em>Included Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingBitmaskType()
 * @model extendedMetaData="name='mappingBitmaskType' kind='elementOnly'"
 * @generated
 */
public interface MappingBitmaskType extends TwoWayMappingType{
	/**
	 * Returns the value of the '<em><b>Group</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Group</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Group</em>' attribute list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingBitmaskType_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:0'"
	 * @generated
	 */
    FeatureMap getGroup();

	/**
	 * Returns the value of the '<em><b>Map Bitmask Value</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.MapBitmaskValueType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Bitmask Value</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a set of properties to a subexpression of the bitmask expression.  If all the given
	 * 		properties are "true", the given value is OR'ed into the target expression.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Bitmask Value</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingBitmaskType_MapBitmaskValue()
	 * @model type="com.nokia.sdt.emf.component.MapBitmaskValueType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapBitmaskValue' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
    EList getMapBitmaskValue();

	/**
	 * Returns the value of the '<em><b>Included Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Included Properties</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 					The list of properties included in the set.  If undefined, all the
	 * 					sibling properties (within a component or compound property) are included.
	 * 					Every included property must be referenced in a mapBitmaskValue element,
	 * 					or else the mapping is considered invalid, and an error reported.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Included Properties</em>' attribute.
	 * @see #setIncludedProperties(List)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingBitmaskType_IncludedProperties()
	 * @model unique="false" dataType="com.nokia.sdt.emf.component.ListOfStrings" many="false"
	 *        extendedMetaData="kind='attribute' name='includedProperties'"
	 * @generated
	 */
    List getIncludedProperties();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.MappingBitmaskType#getIncludedProperties <em>Included Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Included Properties</em>' attribute.
	 * @see #getIncludedProperties()
	 * @generated
	 */
    void setIncludedProperties(List value);

} // MappingBitmaskType
