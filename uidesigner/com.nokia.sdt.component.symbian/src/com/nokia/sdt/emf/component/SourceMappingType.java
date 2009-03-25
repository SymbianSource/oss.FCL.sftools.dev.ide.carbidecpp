/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Source Mapping Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.SourceMappingType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SourceMappingType#getMapResource <em>Map Resource</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SourceMappingType#getSelect <em>Select</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getSourceMappingType()
 * @model extendedMetaData="name='sourceMapping_._type' kind='elementOnly'"
 * @generated
 */
public interface SourceMappingType extends EObject{
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
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSourceMappingType_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:0'"
	 * @generated
	 */
    FeatureMap getGroup();

	/**
	 * Returns the value of the '<em><b>Map Resource</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.MapResourceType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Resource</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map an instance to a RESOURCE.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Resource</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSourceMappingType_MapResource()
	 * @model type="com.nokia.sdt.emf.component.MapResourceType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapResource' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
    EList getMapResource();

	/**
	 * Returns the value of the '<em><b>Select</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.SelectType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Select</em>' containment reference list isn't clear,
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
	 * @return the value of the '<em>Select</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSourceMappingType_Select()
	 * @model type="com.nokia.sdt.emf.component.SelectType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='select' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
    EList getSelect();

} // SourceMappingType
