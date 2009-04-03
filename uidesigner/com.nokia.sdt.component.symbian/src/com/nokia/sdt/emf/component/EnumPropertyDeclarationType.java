/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;

import com.nokia.cpp.internal.api.utils.core.ILocalizedStrings;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Enum Property Declaration Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.EnumPropertyDeclarationType#getEnumElement <em>Enum Element</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.EnumPropertyDeclarationType#getSourceTypeMapping <em>Source Type Mapping</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.EnumPropertyDeclarationType#getDefaultValue <em>Default Value</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.EnumPropertyDeclarationType#getQualifiedName <em>Qualified Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getEnumPropertyDeclarationType()
 * @model extendedMetaData="name='enumPropertyDeclaration_._type' kind='elementOnly'"
 * @generated
 */
public interface EnumPropertyDeclarationType extends EObject{
	/**
	 * Returns the value of the '<em><b>Enum Element</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.EnumElementType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enum Element</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This sequence is the list of values comprising the enumerated type. Each
	 * 						value consists of an internal string value and a displayable string value.
	 * 						
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Enum Element</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getEnumPropertyDeclarationType_EnumElement()
	 * @model type="com.nokia.sdt.emf.component.EnumElementType" containment="true" required="true"
	 *        extendedMetaData="kind='element' name='enumElement' namespace='##targetNamespace'"
	 * @generated
	 */
	EList getEnumElement();

	/**
	 * Returns the value of the '<em><b>Source Type Mapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 					This element provides source mapping for the type, for use by
	 * 					map*FromType elements.  Added post 1.2
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Source Type Mapping</em>' containment reference.
	 * @see #setSourceTypeMapping(SourceTypeMappingType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getEnumPropertyDeclarationType_SourceTypeMapping()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='sourceTypeMapping' namespace='##targetNamespace'"
	 * @generated
	 */
	SourceTypeMappingType getSourceTypeMapping();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.EnumPropertyDeclarationType#getSourceTypeMapping <em>Source Type Mapping</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Type Mapping</em>' containment reference.
	 * @see #getSourceTypeMapping()
	 * @generated
	 */
	void setSourceTypeMapping(SourceTypeMappingType value);

	/**
	 * Returns the value of the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Default Value</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A default internal value for the enumeration. It can be overriden in property declarations.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Default Value</em>' attribute.
	 * @see #setDefaultValue(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getEnumPropertyDeclarationType_DefaultValue()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='defaultValue'"
	 * @generated
	 */
    String getDefaultValue();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.EnumPropertyDeclarationType#getDefaultValue <em>Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Value</em>' attribute.
	 * @see #getDefaultValue()
	 * @generated
	 */
    void setDefaultValue(String value);

	/**
	 * Returns the value of the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Qualified Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The globally unique name for this type. By convention this is a dotted name, e.g. com.example.MyEnumType.
	 * 				This value is used in enumPropertyDeclaration.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Qualified Name</em>' attribute.
	 * @see #setQualifiedName(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getEnumPropertyDeclarationType_QualifiedName()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='qualifiedName'"
	 * @generated
	 */
    String getQualifiedName();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.EnumPropertyDeclarationType#getQualifiedName <em>Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Qualified Name</em>' attribute.
	 * @see #getQualifiedName()
	 * @generated
	 */
    void setQualifiedName(String value);

	EnumElementType findByValue(String value);
	EnumElementType findByDisplayValue(ILocalizedStrings strings, String value);

} // EnumPropertyDeclarationType
