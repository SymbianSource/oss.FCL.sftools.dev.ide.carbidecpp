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
 * A representation of the model object '<em><b>Source Type Mapping Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.SourceTypeMappingType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SourceTypeMappingType#getMapResourceType <em>Map Resource Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SourceTypeMappingType#getMapEnumType <em>Map Enum Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SourceTypeMappingType#getMapSimpleType <em>Map Simple Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SourceTypeMappingType#getMapFixedType <em>Map Fixed Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SourceTypeMappingType#getMapBitmaskType <em>Map Bitmask Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SourceTypeMappingType#getMapIdentifierType <em>Map Identifier Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SourceTypeMappingType#getMapReferenceType <em>Map Reference Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SourceTypeMappingType#getMapArrayType <em>Map Array Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SourceTypeMappingType#getSelect <em>Select</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getSourceTypeMappingType()
 * @model extendedMetaData="name='sourceTypeMapping_._type' kind='elementOnly'"
 * @generated
 */
public interface SourceTypeMappingType extends EObject {
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
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSourceTypeMappingType_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:0'"
	 * @generated
	 */
	FeatureMap getGroup();

	/**
	 * Returns the value of the '<em><b>Map Resource Type</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.MapResourceTypeType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a property to a RESOURCE expression or statement.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Resource Type</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSourceTypeMappingType_MapResourceType()
	 * @model type="com.nokia.sdt.emf.component.MapResourceTypeType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapResourceType' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getMapResourceType();

	/**
	 * Returns the value of the '<em><b>Map Enum Type</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.MapEnumTypeType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map an enumerator to RSS.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Enum Type</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSourceTypeMappingType_MapEnumType()
	 * @model type="com.nokia.sdt.emf.component.MapEnumTypeType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapEnumType' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getMapEnumType();

	/**
	 * Returns the value of the '<em><b>Map Simple Type</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.MapSimpleTypeType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a simple value.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Simple Type</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSourceTypeMappingType_MapSimpleType()
	 * @model type="com.nokia.sdt.emf.component.MapSimpleTypeType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapSimpleType' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getMapSimpleType();

	/**
	 * Returns the value of the '<em><b>Map Fixed Type</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.MapFixedTypeType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a fixed value.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Fixed Type</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSourceTypeMappingType_MapFixedType()
	 * @model type="com.nokia.sdt.emf.component.MapFixedTypeType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapFixedType' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getMapFixedType();

	/**
	 * Returns the value of the '<em><b>Map Bitmask Type</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.MapBitmaskTypeType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a set of boolean properties to a bitmask expression in RSS.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Bitmask Type</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSourceTypeMappingType_MapBitmaskType()
	 * @model type="com.nokia.sdt.emf.component.MapBitmaskTypeType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapBitmaskType' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getMapBitmaskType();

	/**
	 * Returns the value of the '<em><b>Map Identifier Type</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.MapIdentifierTypeType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a simple property to a literal (identifier) in RSS.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Identifier Type</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSourceTypeMappingType_MapIdentifierType()
	 * @model type="com.nokia.sdt.emf.component.MapIdentifierTypeType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapIdentifierType' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getMapIdentifierType();

	/**
	 * Returns the value of the '<em><b>Map Reference Type</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.MapReferenceTypeType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a reference property to resources the instance generates.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Reference Type</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSourceTypeMappingType_MapReferenceType()
	 * @model type="com.nokia.sdt.emf.component.MapReferenceTypeType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapReferenceType' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getMapReferenceType();

	/**
	 * Returns the value of the '<em><b>Map Array Type</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.MapArrayTypeType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a property or child list to an array in RSS.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Array Type</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSourceTypeMappingType_MapArrayType()
	 * @model type="com.nokia.sdt.emf.component.MapArrayTypeType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapArrayType' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getMapArrayType();

	/**
	 * Returns the value of the '<em><b>Select</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.SelectType}.
	 * <!-- begin-user-doc -->
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
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSourceTypeMappingType_Select()
	 * @model type="com.nokia.sdt.emf.component.SelectType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='select' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getSelect();

} // SourceTypeMappingType
