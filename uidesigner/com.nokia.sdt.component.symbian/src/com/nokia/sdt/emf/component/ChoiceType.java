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
 * A representation of the model object '<em><b>Choice Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.ChoiceType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ChoiceType#getTwoWayMappingGroup <em>Two Way Mapping Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ChoiceType#getTwoWayMapping <em>Two Way Mapping</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ChoiceType#getMapResource <em>Map Resource</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ChoiceType#getSelect <em>Select</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ChoiceType#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getChoiceType()
 * @model extendedMetaData="name='choice_._type' kind='elementOnly'"
 * @generated
 */
public interface ChoiceType extends EObject{
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
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getChoiceType_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:0'"
	 * @generated
	 */
    FeatureMap getGroup();

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
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getChoiceType_TwoWayMappingGroup()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='group' name='twoWayMapping:group' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
    FeatureMap getTwoWayMappingGroup();

	/**
	 * Returns the value of the '<em><b>Two Way Mapping</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.TwoWayMappingType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Two Way Mapping</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Two Way Mapping</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getChoiceType_TwoWayMapping()
	 * @model type="com.nokia.sdt.emf.component.TwoWayMappingType" containment="true" transient="true" changeable="false" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='twoWayMapping' namespace='##targetNamespace' group='twoWayMapping:group'"
	 * @generated
	 */
    EList getTwoWayMapping();

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
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getChoiceType_MapResource()
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
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getChoiceType_Select()
	 * @model type="com.nokia.sdt.emf.component.SelectType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='select' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
    EList getSelect();

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
	 * 					The value to match.  If unspecified, the choice always matches.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getChoiceType_Value()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='value'"
	 * @generated
	 */
    String getValue();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ChoiceType#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
    void setValue(String value);

} // ChoiceType
