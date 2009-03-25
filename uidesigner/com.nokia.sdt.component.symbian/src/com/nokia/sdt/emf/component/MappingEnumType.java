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
 * A representation of the model object '<em><b>Mapping Enum Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 		Base for mapping enumerator properties.
 * 		If any mapEnum elements specified, they completely define the list of mappings 
 * 		of internal enumerator values  to external RSS values.  Otherwise, internal 
 * 		values are directly emitted as RSS values.
 * 		
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.MappingEnumType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MappingEnumType#getMapEnum <em>Map Enum</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MappingEnumType#getEnumeration <em>Enumeration</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MappingEnumType#getHeaders <em>Headers</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MappingEnumType#getNameAlgorithm <em>Name Algorithm</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MappingEnumType#getUniqueValue <em>Unique Value</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MappingEnumType#isValidate <em>Validate</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingEnumType()
 * @model extendedMetaData="name='mappingEnumType' kind='elementOnly'"
 * @generated
 */
public interface MappingEnumType extends TwoWayMappingType{
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
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingEnumType_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:0'"
	 * @generated
	 */
    FeatureMap getGroup();

	/**
	 * Returns the value of the '<em><b>Map Enum</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.MapEnumType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Enum</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		This defines the mapping of one particular property value to one
	 * 		particular RSS value.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Enum</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingEnumType_MapEnum()
	 * @model type="com.nokia.sdt.emf.component.MapEnumType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapEnum' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
    EList getMapEnum();

	/**
	 * Returns the value of the '<em><b>Enumeration</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Enumeration</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 					Unused.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Enumeration</em>' attribute.
	 * @see #setEnumeration(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingEnumType_Enumeration()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='enumeration'"
	 * @generated
	 */
    String getEnumeration();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.MappingEnumType#getEnumeration <em>Enumeration</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enumeration</em>' attribute.
	 * @see #getEnumeration()
	 * @generated
	 */
    void setEnumeration(String value);

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
	 * 					List of header files that must be included to provide the RSS enumerator symbols.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Headers</em>' attribute.
	 * @see #setHeaders(List)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingEnumType_Headers()
	 * @model unique="false" dataType="com.nokia.sdt.emf.component.ListOfStrings" many="false"
	 *        extendedMetaData="kind='attribute' name='headers'"
	 * @generated
	 */
    List getHeaders();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.MappingEnumType#getHeaders <em>Headers</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Headers</em>' attribute.
	 * @see #getHeaders()
	 * @generated
	 */
    void setHeaders(List value);

	/**
	 * Returns the value of the '<em><b>Name Algorithm</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name Algorithm</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 					If defined, mapped RSS values may be generated by an algorithm based on
	 * 					the current instance and property.  See the com.nokia.sdt.sourceGen.nameAlgorithm
	 * 					extension point for details.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Name Algorithm</em>' attribute.
	 * @see #setNameAlgorithm(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingEnumType_NameAlgorithm()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='nameAlgorithm'"
	 * @generated
	 */
    String getNameAlgorithm();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.MappingEnumType#getNameAlgorithm <em>Name Algorithm</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name Algorithm</em>' attribute.
	 * @see #getNameAlgorithm()
	 * @generated
	 */
    void setNameAlgorithm(String value);

	/**
	 * Returns the value of the '<em><b>Unique Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Unique Value</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 					When nameAlgorithm is defined, specifies that some or all mapped values
	 * 					are generated by the name algorithm.  If the value is "*", every value goes
	 * 					through the name algorithm and gets an enumerator in an *.hrh file.
	 * 					Otherwise, only properties matching the value are generated.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Unique Value</em>' attribute.
	 * @see #setUniqueValue(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingEnumType_UniqueValue()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='uniqueValue'"
	 * @generated
	 */
    String getUniqueValue();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.MappingEnumType#getUniqueValue <em>Unique Value</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unique Value</em>' attribute.
	 * @see #getUniqueValue()
	 * @generated
	 */
    void setUniqueValue(String value);

	/**
	 * Returns the value of the '<em><b>Validate</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Validate</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 					If true, ensure that mapped enumerators are declared in the RSS context,
	 * 					emitting warnings if not.  Set this to false if mapping #defines or
	 * 					expressions.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Validate</em>' attribute.
	 * @see #isSetValidate()
	 * @see #unsetValidate()
	 * @see #setValidate(boolean)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMappingEnumType_Validate()
	 * @model default="true" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='validate'"
	 * @generated
	 */
    boolean isValidate();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.MappingEnumType#isValidate <em>Validate</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Validate</em>' attribute.
	 * @see #isSetValidate()
	 * @see #unsetValidate()
	 * @see #isValidate()
	 * @generated
	 */
    void setValidate(boolean value);

	/**
	 * Unsets the value of the '{@link com.nokia.sdt.emf.component.MappingEnumType#isValidate <em>Validate</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isSetValidate()
	 * @see #isValidate()
	 * @see #setValidate(boolean)
	 * @generated
	 */
    void unsetValidate();

	/**
	 * Returns whether the value of the '{@link com.nokia.sdt.emf.component.MappingEnumType#isValidate <em>Validate</em>}' attribute is set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Validate</em>' attribute is set.
	 * @see #unsetValidate()
	 * @see #isValidate()
	 * @see #setValidate(boolean)
	 * @generated
	 */
    boolean isSetValidate();

} // MappingEnumType
