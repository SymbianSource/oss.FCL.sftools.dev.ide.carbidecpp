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
 * A representation of the model object '<em><b>Mapping Resource Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 		Base for mapping resources in RSS.
 * 		
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.MappingResourceType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MappingResourceType#getMapSimpleMember <em>Map Simple Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MappingResourceType#getMapInstanceMember <em>Map Instance Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MappingResourceType#getMapReferenceMember <em>Map Reference Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MappingResourceType#getMapFixedMember <em>Map Fixed Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MappingResourceType#getMapEnumMember <em>Map Enum Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MappingResourceType#getMapIdentifierMember <em>Map Identifier Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MappingResourceType#getMapArrayMember <em>Map Array Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MappingResourceType#getMapResourceMember <em>Map Resource Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MappingResourceType#getMapBitmaskMember <em>Map Bitmask Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MappingResourceType#getMapMemberFromType <em>Map Member From Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MappingResourceType#getMapIntoProperty <em>Map Into Property</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MappingResourceType#getSelect <em>Select</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MappingResourceType#getHeaders <em>Headers</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MappingResourceType#getId <em>Id</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MappingResourceType#getStruct <em>Struct</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingResourceType()
 * @model extendedMetaData="name='mappingResourceType' kind='elementOnly'"
 * @generated
 */
public interface MappingResourceType extends TwoWayMappingType{
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
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingResourceType_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:0'"
	 * @generated
	 */
    FeatureMap getGroup();

	/**
	 * Returns the value of the '<em><b>Map Simple Member</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.MapSimpleMemberType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Simple Member</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a simple value to a member.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Simple Member</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingResourceType_MapSimpleMember()
	 * @model type="com.nokia.sdt.emf.component.MapSimpleMemberType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapSimpleMember' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
    EList getMapSimpleMember();

	/**
	 * Returns the value of the '<em><b>Map Instance Member</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.MapInstanceMemberType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Instance Member</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map resources for an instance.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Instance Member</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingResourceType_MapInstanceMember()
	 * @model type="com.nokia.sdt.emf.component.MapInstanceMemberType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapInstanceMember' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
    EList getMapInstanceMember();

	/**
	 * Returns the value of the '<em><b>Map Reference Member</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.MapReferenceMemberType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Reference Member</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a reference property to resources the instance generates.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Reference Member</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingResourceType_MapReferenceMember()
	 * @model type="com.nokia.sdt.emf.component.MapReferenceMemberType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapReferenceMember' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
    EList getMapReferenceMember();

	/**
	 * Returns the value of the '<em><b>Map Fixed Member</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.MapFixedMemberType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Fixed Member</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a fixed value to an RSS member.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Fixed Member</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingResourceType_MapFixedMember()
	 * @model type="com.nokia.sdt.emf.component.MapFixedMemberType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapFixedMember' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
    EList getMapFixedMember();

	/**
	 * Returns the value of the '<em><b>Map Enum Member</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.MapEnumMemberType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Enum Member</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map an enumerator to RSS.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Enum Member</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingResourceType_MapEnumMember()
	 * @model type="com.nokia.sdt.emf.component.MapEnumMemberType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapEnumMember' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
    EList getMapEnumMember();

	/**
	 * Returns the value of the '<em><b>Map Identifier Member</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.MapIdentifierMemberType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Identifier Member</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a simple property to a literal (identifier) in RSS.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Identifier Member</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingResourceType_MapIdentifierMember()
	 * @model type="com.nokia.sdt.emf.component.MapIdentifierMemberType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapIdentifierMember' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
    EList getMapIdentifierMember();

	/**
	 * Returns the value of the '<em><b>Map Array Member</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.MapArrayMemberType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Array Member</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a property or child list to an array in RSS.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Array Member</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingResourceType_MapArrayMember()
	 * @model type="com.nokia.sdt.emf.component.MapArrayMemberType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapArrayMember' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
    EList getMapArrayMember();

	/**
	 * Returns the value of the '<em><b>Map Resource Member</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.MapResourceMemberType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Resource Member</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a property to a RESOURCE expression or statement.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Resource Member</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingResourceType_MapResourceMember()
	 * @model type="com.nokia.sdt.emf.component.MapResourceMemberType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapResourceMember' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
    EList getMapResourceMember();

	/**
	 * Returns the value of the '<em><b>Map Bitmask Member</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.MapBitmaskMemberType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Bitmask Member</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a set of boolean properties to a bitmask expression in RSS.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Bitmask Member</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingResourceType_MapBitmaskMember()
	 * @model type="com.nokia.sdt.emf.component.MapBitmaskMemberType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapBitmaskMember' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
    EList getMapBitmaskMember();

	/**
	 * Returns the value of the '<em><b>Map Member From Type</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.MapMemberFromTypeType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map RSS from the type declaration attached to the given property.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Member From Type</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingResourceType_MapMemberFromType()
	 * @model type="com.nokia.sdt.emf.component.MapMemberFromTypeType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapMemberFromType' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getMapMemberFromType();

	/**
	 * Returns the value of the '<em><b>Map Into Property</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.MapIntoPropertyType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Delve into a property path without generating any resources.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Into Property</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingResourceType_MapIntoProperty()
	 * @model type="com.nokia.sdt.emf.component.MapIntoPropertyType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapIntoProperty' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getMapIntoProperty();

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
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingResourceType_Select()
	 * @model type="com.nokia.sdt.emf.component.SelectType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='select' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
    EList getSelect();

	/**
	 * Returns the value of the '<em><b>Headers</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Headers</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 					The list of headers required to declare the STRUCT.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Headers</em>' attribute.
	 * @see #setHeaders(List)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingResourceType_Headers()
	 * @model unique="false" dataType="com.nokia.sdt.emf.component.ListOfStrings" many="false"
	 *        extendedMetaData="kind='attribute' name='headers'"
	 * @generated
	 */
    List getHeaders();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.MappingResourceType#getHeaders <em>Headers</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Headers</em>' attribute.
	 * @see #getHeaders()
	 * @generated
	 */
    void setHeaders(List value);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 					The unique identifier for this resource within a component.
	 * 					
	 * 					Every mapResourceXXX must have an identifier, either implicitly
	 * 					or explicitly defined.
	 * 					
	 * 					If unspecified, an identifier is automatically generated for
	 * 					mapResourceMember or mapResourceElement elements.  Otherwise,
	 * 					missing identifiers are reported at errors at source mapping time.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingResourceType_Id()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='id'"
	 * @generated
	 */
    String getId();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.MappingResourceType#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
    void setId(String value);

	/**
	 * Returns the value of the '<em><b>Struct</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Struct</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 					The RSS STRUCT name to generate.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Struct</em>' attribute.
	 * @see #setStruct(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingResourceType_Struct()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='struct'"
	 * @generated
	 */
    String getStruct();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.MappingResourceType#getStruct <em>Struct</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Struct</em>' attribute.
	 * @see #getStruct()
	 * @generated
	 */
    void setStruct(String value);

    /**
     * Get a unique identifier for this MappingResourceType 
     * @generated NOT
     */
    String getUniqueId();
    
} // MappingResourceType
