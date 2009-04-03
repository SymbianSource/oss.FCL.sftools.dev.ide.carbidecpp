/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getAbstractProperty <em>Abstract Property</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getArrayProperty <em>Array Property</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getChoice <em>Choice</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getCode <em>Code</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getComponent <em>Component</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getComponentDefinition <em>Component Definition</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getComponentReferenceProperty <em>Component Reference Property</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getCompoundProperty <em>Compound Property</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getCompoundPropertyDeclaration <em>Compound Property Declaration</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getDefineLocation <em>Define Location</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getDefineMacro <em>Define Macro</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getDesignerImages <em>Designer Images</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getEnumProperty <em>Enum Property</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getEnumPropertyDeclaration <em>Enum Property Declaration</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getEvent <em>Event</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getEvents <em>Events</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getExpandArgument <em>Expand Argument</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getExpandMacro <em>Expand Macro</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getExtensionProperties <em>Extension Properties</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getImplementation <em>Implementation</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getImplementations <em>Implementations</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getImportArguments <em>Import Arguments</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getInline <em>Inline</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMacroArgument <em>Macro Argument</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapArrayMember <em>Map Array Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getTwoWayMapping <em>Two Way Mapping</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapArrayType <em>Map Array Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapBitmaskElement <em>Map Bitmask Element</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapBitmaskMember <em>Map Bitmask Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapBitmaskType <em>Map Bitmask Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapBitmaskValue <em>Map Bitmask Value</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapElementFromType <em>Map Element From Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapEnum <em>Map Enum</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapEnumElement <em>Map Enum Element</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapEnumMember <em>Map Enum Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapEnumType <em>Map Enum Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapFixedElement <em>Map Fixed Element</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapFixedMember <em>Map Fixed Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapFixedType <em>Map Fixed Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapIdentifierElement <em>Map Identifier Element</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapIdentifierMember <em>Map Identifier Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapIdentifierType <em>Map Identifier Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapInstanceElement <em>Map Instance Element</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapInstanceMember <em>Map Instance Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapInstanceType <em>Map Instance Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapIntoProperty <em>Map Into Property</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapMemberFromType <em>Map Member From Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapReferenceElement <em>Map Reference Element</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapReferenceMember <em>Map Reference Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapReferenceType <em>Map Reference Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapResource <em>Map Resource</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapResourceElement <em>Map Resource Element</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapResourceMember <em>Map Resource Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapResourceType <em>Map Resource Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapSimpleElement <em>Map Simple Element</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapSimpleMember <em>Map Simple Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getMapSimpleType <em>Map Simple Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getProperties <em>Properties</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getProperty <em>Property</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getPropertyOverrides <em>Property Overrides</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getScript <em>Script</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getSelect <em>Select</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getSourceGen <em>Source Gen</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getSourceMapping <em>Source Mapping</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getSourceTypeMapping <em>Source Type Mapping</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getSymbian <em>Symbian</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getTemplate <em>Template</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getTemplateGroup <em>Template Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getUseTemplate <em>Use Template</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentRoot#getUseTemplateGroup <em>Use Template Group</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot()
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface DocumentRoot extends EObject{
	/**
	 * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mixed</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mixed</em>' attribute list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_Mixed()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='elementWildcard' name=':mixed'"
	 * @generated
	 */
	FeatureMap getMixed();

	/**
	 * Returns the value of the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XMLNS Prefix Map</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XMLNS Prefix Map</em>' map.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_XMLNSPrefixMap()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry" keyType="java.lang.String" valueType="java.lang.String" transient="true"
	 *        extendedMetaData="kind='attribute' name='xmlns:prefix'"
	 * @generated
	 */
	EMap getXMLNSPrefixMap();

	/**
	 * Returns the value of the '<em><b>XSI Schema Location</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XSI Schema Location</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XSI Schema Location</em>' map.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_XSISchemaLocation()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry" keyType="java.lang.String" valueType="java.lang.String" transient="true"
	 *        extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
	 * @generated
	 */
	EMap getXSISchemaLocation();

	/**
	 * Returns the value of the '<em><b>Abstract Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Abstract Property</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Abstract Property</em>' containment reference.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_AbstractProperty()
	 * @model containment="true" upper="-2" transient="true" changeable="false" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='abstractProperty' namespace='##targetNamespace'"
	 * @generated
	 */
	AbstractPropertyType getAbstractProperty();

	/**
	 * Returns the value of the '<em><b>Array Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Array Property</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Array Property</em>' containment reference.
	 * @see #setArrayProperty(ArrayPropertyType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_ArrayProperty()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='arrayProperty' namespace='##targetNamespace' affiliation='abstractProperty'"
	 * @generated
	 */
	ArrayPropertyType getArrayProperty();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getArrayProperty <em>Array Property</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Array Property</em>' containment reference.
	 * @see #getArrayProperty()
	 * @generated
	 */
	void setArrayProperty(ArrayPropertyType value);

	/**
	 * Returns the value of the '<em><b>Attribute</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Attribute</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute</em>' containment reference.
	 * @see #setAttribute(AttributeType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_Attribute()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='attribute' namespace='##targetNamespace'"
	 * @generated
	 */
    AttributeType getAttribute();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getAttribute <em>Attribute</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute</em>' containment reference.
	 * @see #getAttribute()
	 * @generated
	 */
    void setAttribute(AttributeType value);

	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributes</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * Attribute elements can hold any static character data that is common to all instances of a component. Attributes are inherited from their base components.
	 * 			
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Attributes</em>' containment reference.
	 * @see #setAttributes(AttributesType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_Attributes()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='attributes' namespace='##targetNamespace'"
	 * @generated
	 */
	AttributesType getAttributes();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getAttributes <em>Attributes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attributes</em>' containment reference.
	 * @see #getAttributes()
	 * @generated
	 */
	void setAttributes(AttributesType value);

	/**
	 * Returns the value of the '<em><b>Choice</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Choice</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		This defines a decision in the select element.  If the "value" attribute
	 * 		matches the value obtained in the select element, the choice is matched
	 * 		and its mapping elements instantiated.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Choice</em>' containment reference.
	 * @see #setChoice(ChoiceType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_Choice()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='choice' namespace='##targetNamespace'"
	 * @generated
	 */
    ChoiceType getChoice();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getChoice <em>Choice</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Choice</em>' containment reference.
	 * @see #getChoice()
	 * @generated
	 */
    void setChoice(ChoiceType value);

	/**
	 * Returns the value of the '<em><b>Code</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Code</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Define a reference to an interface implemented in Java.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Code</em>' containment reference.
	 * @see #setCode(CodeType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_Code()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='code' namespace='##targetNamespace'"
	 * @generated
	 */
    CodeType getCode();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getCode <em>Code</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Code</em>' containment reference.
	 * @see #getCode()
	 * @generated
	 */
    void setCode(CodeType value);

	/**
	 * Returns the value of the '<em><b>Component</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 *         This is the container element for all the information about a single component.
	 *         
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Component</em>' containment reference.
	 * @see #setComponent(ComponentType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_Component()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='component' namespace='##targetNamespace'"
	 * @generated
	 */
	ComponentType getComponent();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getComponent <em>Component</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component</em>' containment reference.
	 * @see #getComponent()
	 * @generated
	 */
	void setComponent(ComponentType value);

	/**
	 * Returns the value of the '<em><b>Component Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Definition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * componentDefinition must be the root element of a component definition document.
	 * 			
	 * 			It includes zero or more compound property declarations, zero or more enum declarations and zero or one
	 * 			component declarations.
	 * 			
	 * 			Displayable strings can either be included literally within the XML or referenced in the 
	 * 			component.properties file by prefixing the string with %. For example, "%foo" references the 
	 * 			value of the "foo" property in component.properties. To obtain the string "%foo" 
	 * 			use a double %, e.g. "%%foo".
	 * 			
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Component Definition</em>' containment reference.
	 * @see #setComponentDefinition(ComponentDefinitionType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_ComponentDefinition()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='componentDefinition' namespace='##targetNamespace'"
	 * @generated
	 */
	ComponentDefinitionType getComponentDefinition();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getComponentDefinition <em>Component Definition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component Definition</em>' containment reference.
	 * @see #getComponentDefinition()
	 * @generated
	 */
	void setComponentDefinition(ComponentDefinitionType value);

	/**
	 * Returns the value of the '<em><b>Component Reference Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Reference Property</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component Reference Property</em>' containment reference.
	 * @see #setComponentReferenceProperty(ComponentReferencePropertyType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_ComponentReferenceProperty()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='componentReferenceProperty' namespace='##targetNamespace' affiliation='abstractProperty'"
	 * @generated
	 */
	ComponentReferencePropertyType getComponentReferenceProperty();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getComponentReferenceProperty <em>Component Reference Property</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component Reference Property</em>' containment reference.
	 * @see #getComponentReferenceProperty()
	 * @generated
	 */
	void setComponentReferenceProperty(ComponentReferencePropertyType value);

	/**
	 * Returns the value of the '<em><b>Compound Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Compound Property</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compound Property</em>' containment reference.
	 * @see #setCompoundProperty(CompoundPropertyType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_CompoundProperty()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='compoundProperty' namespace='##targetNamespace' affiliation='abstractProperty'"
	 * @generated
	 */
	CompoundPropertyType getCompoundProperty();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getCompoundProperty <em>Compound Property</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Compound Property</em>' containment reference.
	 * @see #getCompoundProperty()
	 * @generated
	 */
	void setCompoundProperty(CompoundPropertyType value);

	/**
	 * Returns the value of the '<em><b>Compound Property Declaration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Compound Property Declaration</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 			Declares a compound type that may be referenced from one or more
	 * 			compound property declarations in this or other component documents. All compound property declarations are in a global 
	 * 			namespace and must be declared as global elements.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Compound Property Declaration</em>' containment reference.
	 * @see #setCompoundPropertyDeclaration(CompoundPropertyDeclarationType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_CompoundPropertyDeclaration()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='compoundPropertyDeclaration' namespace='##targetNamespace'"
	 * @generated
	 */
	CompoundPropertyDeclarationType getCompoundPropertyDeclaration();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getCompoundPropertyDeclaration <em>Compound Property Declaration</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Compound Property Declaration</em>' containment reference.
	 * @see #getCompoundPropertyDeclaration()
	 * @generated
	 */
	void setCompoundPropertyDeclaration(CompoundPropertyDeclarationType value);

	/**
	 * Returns the value of the '<em><b>Define Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Define Location</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		This element defines a location in source, such as a function in the main 
	 * 		file or a region of text in a class declaration.  
	 * 		This is a slight misnomer as this element does not by its presence realize
	 * 		the location.  Only when a contribution references
	 * 		location id (or post 1.1: when realize="true" is used)
	 * 		will the location be added to source.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Define Location</em>' containment reference.
	 * @see #setDefineLocation(DefineLocationType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_DefineLocation()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='defineLocation' namespace='##targetNamespace'"
	 * @generated
	 */
    DefineLocationType getDefineLocation();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getDefineLocation <em>Define Location</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Define Location</em>' containment reference.
	 * @see #getDefineLocation()
	 * @generated
	 */
    void setDefineLocation(DefineLocationType value);

	/**
	 * Returns the value of the '<em><b>Define Macro</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Added post 1.1.  
	 * 		This element defines a macro consisting of a set of templates 
	 * 		and inlines which may be variable-substituted.
	 * 			
	 * 		If a given defineMacro imports multiple arguments
	 * 		of the same name, then they must be explicitly redefined,
	 * 		to resolve any ambiguities about the default value.  
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Define Macro</em>' containment reference.
	 * @see #setDefineMacro(DefineMacroType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_DefineMacro()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='defineMacro' namespace='##targetNamespace'"
	 * @generated
	 */
	DefineMacroType getDefineMacro();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getDefineMacro <em>Define Macro</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Define Macro</em>' containment reference.
	 * @see #getDefineMacro()
	 * @generated
	 */
	void setDefineMacro(DefineMacroType value);

	/**
	 * Returns the value of the '<em><b>Designer Images</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Designer Images</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This element is specifies images for use by the UI designer. Only those images that apply need to be defined, e.g.
	 * 		if a component never appears in the UI design wizard or palette then it doesn't need those images.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Designer Images</em>' containment reference.
	 * @see #setDesignerImages(DesignerImagesType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_DesignerImages()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='designerImages' namespace='##targetNamespace'"
	 * @generated
	 */
	DesignerImagesType getDesignerImages();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getDesignerImages <em>Designer Images</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Designer Images</em>' containment reference.
	 * @see #getDesignerImages()
	 * @generated
	 */
	void setDesignerImages(DesignerImagesType value);

	/**
	 * Returns the value of the '<em><b>Documentation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Documentation</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The optional documentation element provides brief description information about the component and a link to more detailed documentation
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Documentation</em>' containment reference.
	 * @see #setDocumentation(DocumentationType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_Documentation()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='documentation' namespace='##targetNamespace'"
	 * @generated
	 */
	DocumentationType getDocumentation();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getDocumentation <em>Documentation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Documentation</em>' containment reference.
	 * @see #getDocumentation()
	 * @generated
	 */
	void setDocumentation(DocumentationType value);

	/**
	 * Returns the value of the '<em><b>Enum Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enum Property</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enum Property</em>' containment reference.
	 * @see #setEnumProperty(EnumPropertyType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_EnumProperty()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='enumProperty' namespace='##targetNamespace' affiliation='abstractProperty'"
	 * @generated
	 */
	EnumPropertyType getEnumProperty();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getEnumProperty <em>Enum Property</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enum Property</em>' containment reference.
	 * @see #getEnumProperty()
	 * @generated
	 */
	void setEnumProperty(EnumPropertyType value);

	/**
	 * Returns the value of the '<em><b>Enum Property Declaration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enum Property Declaration</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 			Declares an enumerated type that may be referenced from one or more
	 * 			enumerated property declarations in this or other component documents. All enum property declarations are in a global 
	 * 			namespace and must be declared as global elements.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Enum Property Declaration</em>' containment reference.
	 * @see #setEnumPropertyDeclaration(EnumPropertyDeclarationType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_EnumPropertyDeclaration()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='enumPropertyDeclaration' namespace='##targetNamespace'"
	 * @generated
	 */
	EnumPropertyDeclarationType getEnumPropertyDeclaration();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getEnumPropertyDeclaration <em>Enum Property Declaration</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enum Property Declaration</em>' containment reference.
	 * @see #getEnumPropertyDeclaration()
	 * @generated
	 */
	void setEnumPropertyDeclaration(EnumPropertyDeclarationType value);

	/**
	 * Returns the value of the '<em><b>Event</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Event</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The definition of a single event.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Event</em>' containment reference.
	 * @see #setEvent(EventType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_Event()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='event' namespace='##targetNamespace'"
	 * @generated
	 */
	EventType getEvent();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getEvent <em>Event</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Event</em>' containment reference.
	 * @see #getEvent()
	 * @generated
	 */
	void setEvent(EventType value);

	/**
	 * Returns the value of the '<em><b>Events</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Events</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The list of events defined for this component. Components also inherit the events of
	 * 			their base components. Events are named notifications or hooks to which code can be attached. The data here is
	 * 			used to drive the user interface. The details of source code generation are part of the source code generation templates.
	 * 			
	 * 			The set of available events, and default event, may be overriden via code or script. See the 
	 * 			com.nokia.sdt.datamodel.adapter.IComponentEventInfo interface for more information.
	 * 			
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Events</em>' containment reference.
	 * @see #setEvents(EventsType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_Events()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='events' namespace='##targetNamespace'"
	 * @generated
	 */
	EventsType getEvents();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getEvents <em>Events</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Events</em>' containment reference.
	 * @see #getEvents()
	 * @generated
	 */
	void setEvents(EventsType value);

	/**
	 * Returns the value of the '<em><b>Expand Argument</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expand Argument</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Added post 1.1.  
	 * 		Provides multiline text content for an argument.
	 * 		This is semantically identical to setting an attribute of the same
	 * 		name="..." value in the expandMacro element, with any standalone 
	 * 		leading whitespace and standalone trailing whitespace removed
	 * 		(as with &lt;template&gt;).
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Expand Argument</em>' containment reference.
	 * @see #setExpandArgument(ExpandArgumentType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_ExpandArgument()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='expandArgument' namespace='##targetNamespace'"
	 * @generated
	 */
	ExpandArgumentType getExpandArgument();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getExpandArgument <em>Expand Argument</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expand Argument</em>' containment reference.
	 * @see #getExpandArgument()
	 * @generated
	 */
	void setExpandArgument(ExpandArgumentType value);

	/**
	 * Returns the value of the '<em><b>Expand Macro</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		<p xmlns="http://www.nokia.com/sdt/emf/component">
	 * 		Added post 1.1.
	 * 		Expands a given macro into the sourceGen of the caller.  
	 * 		This has the same effect as inserting the same templates and inlines 
	 * 		from the macro's definition at the point of call.  
	 * 		Variable references from those templates and inlines
	 * 		are substituted with the values provided in attributes 
	 * 		(e.g. variableName="value") or expandArgument child elements.  
	 * 		The latter may be preferred for cases where code is substituted,
	 * 		so the formatting may be retained.
	 * 		</p>
	 * 		<p xmlns="http://www.nokia.com/sdt/emf/component">
	 * 		As a special case, variable references may have modifiers to
	 * 		modify the formatting of a variable when it is expanded.  
	 * 		Modifiers are appended
	 * 		to the variable name as in: $(varName::modifier).  
	 * 		</p>
	 * 		<p xmlns="http://www.nokia.com/sdt/emf/component">
	 * 		Currently supported modifiers are all for tweaking canonical
	 * 		function argument lists, which match the format of 
	 * 		declaration, with spaces, argument names, default values, etc:
	 * 		</p>
	 * 		<ul xmlns="http://www.nokia.com/sdt/emf/component">
	 * 			<li>as-function-declaration-args: identity
	 * 			</li>
	 * 			<li>as-function-definition-args: removes default argument values
	 * 			</li>
	 * 			<li>as-function-location-args: removes argument names, default arguments, and spaces
	 * 			</li>
	 * 		</ul>
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Expand Macro</em>' containment reference.
	 * @see #setExpandMacro(ExpandMacroType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_ExpandMacro()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='expandMacro' namespace='##targetNamespace'"
	 * @generated
	 */
	ExpandMacroType getExpandMacro();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getExpandMacro <em>Expand Macro</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expand Macro</em>' containment reference.
	 * @see #getExpandMacro()
	 * @generated
	 */
	void setExpandMacro(ExpandMacroType value);

	/**
	 * Returns the value of the '<em><b>Extension Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extension Properties</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Extension properties may be dynamically added, based on the state of an instance of this
	 * 		component, its container, or other objects. There may be more than one set of extension properties defined in a component.
	 * 		See the com.nokia.sdt.datamodel.adapter.IPropertyExtenders interface.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Extension Properties</em>' containment reference.
	 * @see #setExtensionProperties(ExtensionPropertiesType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_ExtensionProperties()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='extensionProperties' namespace='##targetNamespace'"
	 * @generated
	 */
	ExtensionPropertiesType getExtensionProperties();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getExtensionProperties <em>Extension Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extension Properties</em>' containment reference.
	 * @see #getExtensionProperties()
	 * @generated
	 */
	void setExtensionProperties(ExtensionPropertiesType value);

	/**
	 * Returns the value of the '<em><b>Implementation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implementation</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Implementation</em>' containment reference.
	 * @see #setImplementation(ImplementationType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_Implementation()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='implementation' namespace='##targetNamespace'"
	 * @generated
	 */
	ImplementationType getImplementation();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getImplementation <em>Implementation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Implementation</em>' containment reference.
	 * @see #getImplementation()
	 * @generated
	 */
	void setImplementation(ImplementationType value);

	/**
	 * Returns the value of the '<em><b>Implementations</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implementations</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Implementation elements declare code or script implementations of interfaces by name.
	 * 			
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Implementations</em>' containment reference.
	 * @see #setImplementations(ImplementationsType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_Implementations()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='implementations' namespace='##targetNamespace'"
	 * @generated
	 */
	ImplementationsType getImplementations();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getImplementations <em>Implementations</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Implementations</em>' containment reference.
	 * @see #getImplementations()
	 * @generated
	 */
	void setImplementations(ImplementationsType value);

	/**
	 * Returns the value of the '<em><b>Import Arguments</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Added post 1.1.  
	 * 		This element specifies that a list of arguments (name, type, defaults,
	 * 		optional flags) will be imported from another macro. 
	 * 		If this element is omitted, then all the arguments from the macro
	 * 		are imported.  
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Import Arguments</em>' containment reference.
	 * @see #setImportArguments(ImportArgumentsType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_ImportArguments()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='importArguments' namespace='##targetNamespace'"
	 * @generated
	 */
	ImportArgumentsType getImportArguments();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getImportArguments <em>Import Arguments</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Import Arguments</em>' containment reference.
	 * @see #getImportArguments()
	 * @generated
	 */
	void setImportArguments(ImportArgumentsType value);

	/**
	 * Returns the value of the '<em><b>Inline</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Inline</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Provide Javascript in-line with templates and other sourceGen elements.
	 * 		Be sure to supply a "forms" attribute, otherwise the code may execute multiple times
	 * 		during the source generation contribution-gathering phase.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Inline</em>' containment reference.
	 * @see #setInline(InlineType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_Inline()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='inline' namespace='##targetNamespace'"
	 * @generated
	 */
    InlineType getInline();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getInline <em>Inline</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inline</em>' containment reference.
	 * @see #getInline()
	 * @generated
	 */
    void setInline(InlineType value);

	/**
	 * Returns the value of the '<em><b>Macro Argument</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Added post 1.1.
	 * 		Defines an argument for use with the macro.
	 * 		The default value may be specified in the 'default' attribute or in the text of the element.
	 * 		The text supercedes the attribute.  
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Macro Argument</em>' containment reference.
	 * @see #setMacroArgument(MacroArgumentType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MacroArgument()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='macroArgument' namespace='##targetNamespace'"
	 * @generated
	 */
	MacroArgumentType getMacroArgument();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMacroArgument <em>Macro Argument</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Macro Argument</em>' containment reference.
	 * @see #getMacroArgument()
	 * @generated
	 */
	void setMacroArgument(MacroArgumentType value);

	/**
	 * Returns the value of the '<em><b>Map Array Member</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Array Member</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a property or child list to an array in RSS.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Array Member</em>' containment reference.
	 * @see #setMapArrayMember(MapArrayMemberType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapArrayMember()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapArrayMember' namespace='##targetNamespace' affiliation='twoWayMapping'"
	 * @generated
	 */
    MapArrayMemberType getMapArrayMember();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapArrayMember <em>Map Array Member</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Array Member</em>' containment reference.
	 * @see #getMapArrayMember()
	 * @generated
	 */
    void setMapArrayMember(MapArrayMemberType value);

	/**
	 * Returns the value of the '<em><b>Map Enum</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Enum</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		This defines the mapping of one particular property value to one
	 * 		particular RSS value.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Enum</em>' containment reference.
	 * @see #setMapEnum(MapEnumType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapEnum()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapEnum' namespace='##targetNamespace'"
	 * @generated
	 */
    MapEnumType getMapEnum();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapEnum <em>Map Enum</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Enum</em>' containment reference.
	 * @see #getMapEnum()
	 * @generated
	 */
    void setMapEnum(MapEnumType value);

	/**
	 * Returns the value of the '<em><b>Map Enum Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Enum Element</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map an enumerator to an array element.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Enum Element</em>' containment reference.
	 * @see #setMapEnumElement(MapEnumElementType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapEnumElement()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapEnumElement' namespace='##targetNamespace' affiliation='twoWayMapping'"
	 * @generated
	 */
    MapEnumElementType getMapEnumElement();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapEnumElement <em>Map Enum Element</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Enum Element</em>' containment reference.
	 * @see #getMapEnumElement()
	 * @generated
	 */
    void setMapEnumElement(MapEnumElementType value);

	/**
	 * Returns the value of the '<em><b>Two Way Mapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Two Way Mapping</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Two Way Mapping</em>' containment reference.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_TwoWayMapping()
	 * @model containment="true" upper="-2" transient="true" changeable="false" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='twoWayMapping' namespace='##targetNamespace'"
	 * @generated
	 */
    TwoWayMappingType getTwoWayMapping();

	/**
	 * Returns the value of the '<em><b>Map Array Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a property or child list to an array in RSS.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Array Type</em>' containment reference.
	 * @see #setMapArrayType(MapArrayTypeType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapArrayType()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapArrayType' namespace='##targetNamespace' affiliation='twoWayMapping'"
	 * @generated
	 */
	MapArrayTypeType getMapArrayType();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapArrayType <em>Map Array Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Array Type</em>' containment reference.
	 * @see #getMapArrayType()
	 * @generated
	 */
	void setMapArrayType(MapArrayTypeType value);

	/**
	 * Returns the value of the '<em><b>Map Bitmask Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Bitmask Element</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a set of boolean properties to an expression in an array element.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Bitmask Element</em>' containment reference.
	 * @see #setMapBitmaskElement(MapBitmaskElementType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapBitmaskElement()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapBitmaskElement' namespace='##targetNamespace' affiliation='twoWayMapping'"
	 * @generated
	 */
    MapBitmaskElementType getMapBitmaskElement();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapBitmaskElement <em>Map Bitmask Element</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Bitmask Element</em>' containment reference.
	 * @see #getMapBitmaskElement()
	 * @generated
	 */
    void setMapBitmaskElement(MapBitmaskElementType value);

	/**
	 * Returns the value of the '<em><b>Map Bitmask Member</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Bitmask Member</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a set of boolean properties to a bitmask expression in RSS.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Bitmask Member</em>' containment reference.
	 * @see #setMapBitmaskMember(MapBitmaskMemberType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapBitmaskMember()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapBitmaskMember' namespace='##targetNamespace' affiliation='twoWayMapping'"
	 * @generated
	 */
    MapBitmaskMemberType getMapBitmaskMember();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapBitmaskMember <em>Map Bitmask Member</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Bitmask Member</em>' containment reference.
	 * @see #getMapBitmaskMember()
	 * @generated
	 */
    void setMapBitmaskMember(MapBitmaskMemberType value);

	/**
	 * Returns the value of the '<em><b>Map Bitmask Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a set of boolean properties to a bitmask expression in RSS.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Bitmask Type</em>' containment reference.
	 * @see #setMapBitmaskType(MapBitmaskTypeType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapBitmaskType()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapBitmaskType' namespace='##targetNamespace' affiliation='twoWayMapping'"
	 * @generated
	 */
	MapBitmaskTypeType getMapBitmaskType();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapBitmaskType <em>Map Bitmask Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Bitmask Type</em>' containment reference.
	 * @see #getMapBitmaskType()
	 * @generated
	 */
	void setMapBitmaskType(MapBitmaskTypeType value);

	/**
	 * Returns the value of the '<em><b>Map Bitmask Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Bitmask Value</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a set of properties to a subexpression of the bitmask expression.  If all the given
	 * 		properties are "true", the given value is OR'ed into the target expression.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Bitmask Value</em>' containment reference.
	 * @see #setMapBitmaskValue(MapBitmaskValueType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapBitmaskValue()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapBitmaskValue' namespace='##targetNamespace'"
	 * @generated
	 */
    MapBitmaskValueType getMapBitmaskValue();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapBitmaskValue <em>Map Bitmask Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Bitmask Value</em>' containment reference.
	 * @see #getMapBitmaskValue()
	 * @generated
	 */
    void setMapBitmaskValue(MapBitmaskValueType value);

	/**
	 * Returns the value of the '<em><b>Map Element From Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map RSS from the type declaration attached to the current element.
	 * 		You may need to use &lt;mapProperty&gt; to descend into a child of the current
	 * 		element for this to work.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Element From Type</em>' containment reference.
	 * @see #setMapElementFromType(MapElementFromTypeType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapElementFromType()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapElementFromType' namespace='##targetNamespace' affiliation='twoWayMapping'"
	 * @generated
	 */
	MapElementFromTypeType getMapElementFromType();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapElementFromType <em>Map Element From Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Element From Type</em>' containment reference.
	 * @see #getMapElementFromType()
	 * @generated
	 */
	void setMapElementFromType(MapElementFromTypeType value);

	/**
	 * Returns the value of the '<em><b>Map Enum Member</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Enum Member</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map an enumerator to RSS.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Enum Member</em>' containment reference.
	 * @see #setMapEnumMember(MapEnumMemberType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapEnumMember()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapEnumMember' namespace='##targetNamespace' affiliation='twoWayMapping'"
	 * @generated
	 */
    MapEnumMemberType getMapEnumMember();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapEnumMember <em>Map Enum Member</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Enum Member</em>' containment reference.
	 * @see #getMapEnumMember()
	 * @generated
	 */
    void setMapEnumMember(MapEnumMemberType value);

	/**
	 * Returns the value of the '<em><b>Map Enum Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map an enumerator to RSS.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Enum Type</em>' containment reference.
	 * @see #setMapEnumType(MapEnumTypeType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapEnumType()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapEnumType' namespace='##targetNamespace' affiliation='twoWayMapping'"
	 * @generated
	 */
	MapEnumTypeType getMapEnumType();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapEnumType <em>Map Enum Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Enum Type</em>' containment reference.
	 * @see #getMapEnumType()
	 * @generated
	 */
	void setMapEnumType(MapEnumTypeType value);

	/**
	 * Returns the value of the '<em><b>Map Fixed Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a fixed value to an array element.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Fixed Element</em>' containment reference.
	 * @see #setMapFixedElement(MapFixedElementType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapFixedElement()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapFixedElement' namespace='##targetNamespace' affiliation='twoWayMapping'"
	 * @generated
	 */
	MapFixedElementType getMapFixedElement();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapFixedElement <em>Map Fixed Element</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Fixed Element</em>' containment reference.
	 * @see #getMapFixedElement()
	 * @generated
	 */
	void setMapFixedElement(MapFixedElementType value);

	/**
	 * Returns the value of the '<em><b>Map Fixed Member</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Fixed Member</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a fixed value to an RSS member.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Fixed Member</em>' containment reference.
	 * @see #setMapFixedMember(MapFixedMemberType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapFixedMember()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapFixedMember' namespace='##targetNamespace' affiliation='twoWayMapping'"
	 * @generated
	 */
    MapFixedMemberType getMapFixedMember();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapFixedMember <em>Map Fixed Member</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Fixed Member</em>' containment reference.
	 * @see #getMapFixedMember()
	 * @generated
	 */
    void setMapFixedMember(MapFixedMemberType value);

	/**
	 * Returns the value of the '<em><b>Map Fixed Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a fixed value.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Fixed Type</em>' containment reference.
	 * @see #setMapFixedType(MapFixedTypeType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapFixedType()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapFixedType' namespace='##targetNamespace' affiliation='twoWayMapping'"
	 * @generated
	 */
	MapFixedTypeType getMapFixedType();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapFixedType <em>Map Fixed Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Fixed Type</em>' containment reference.
	 * @see #getMapFixedType()
	 * @generated
	 */
	void setMapFixedType(MapFixedTypeType value);

	/**
	 * Returns the value of the '<em><b>Map Identifier Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Identifier Element</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a simple value to a literal (identifier) in an array element.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Identifier Element</em>' containment reference.
	 * @see #setMapIdentifierElement(MapIdentifierElementType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapIdentifierElement()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapIdentifierElement' namespace='##targetNamespace' affiliation='twoWayMapping'"
	 * @generated
	 */
    MapIdentifierElementType getMapIdentifierElement();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapIdentifierElement <em>Map Identifier Element</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Identifier Element</em>' containment reference.
	 * @see #getMapIdentifierElement()
	 * @generated
	 */
    void setMapIdentifierElement(MapIdentifierElementType value);

	/**
	 * Returns the value of the '<em><b>Map Identifier Member</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Identifier Member</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a simple property to a literal (identifier) in RSS.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Identifier Member</em>' containment reference.
	 * @see #setMapIdentifierMember(MapIdentifierMemberType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapIdentifierMember()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapIdentifierMember' namespace='##targetNamespace' affiliation='twoWayMapping'"
	 * @generated
	 */
    MapIdentifierMemberType getMapIdentifierMember();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapIdentifierMember <em>Map Identifier Member</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Identifier Member</em>' containment reference.
	 * @see #getMapIdentifierMember()
	 * @generated
	 */
    void setMapIdentifierMember(MapIdentifierMemberType value);

	/**
	 * Returns the value of the '<em><b>Map Identifier Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a simple property to a literal (identifier) in RSS.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Identifier Type</em>' containment reference.
	 * @see #setMapIdentifierType(MapIdentifierTypeType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapIdentifierType()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapIdentifierType' namespace='##targetNamespace' affiliation='twoWayMapping'"
	 * @generated
	 */
	MapIdentifierTypeType getMapIdentifierType();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapIdentifierType <em>Map Identifier Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Identifier Type</em>' containment reference.
	 * @see #getMapIdentifierType()
	 * @generated
	 */
	void setMapIdentifierType(MapIdentifierTypeType value);

	/**
	 * Returns the value of the '<em><b>Map Instance Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Instance Element</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map an instance's resources to an array element.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Instance Element</em>' containment reference.
	 * @see #setMapInstanceElement(MapInstanceElementType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapInstanceElement()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapInstanceElement' namespace='##targetNamespace' affiliation='twoWayMapping'"
	 * @generated
	 */
    MapInstanceElementType getMapInstanceElement();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapInstanceElement <em>Map Instance Element</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Instance Element</em>' containment reference.
	 * @see #getMapInstanceElement()
	 * @generated
	 */
    void setMapInstanceElement(MapInstanceElementType value);

	/**
	 * Returns the value of the '<em><b>Map Instance Member</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Instance Member</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map resources for an instance.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Instance Member</em>' containment reference.
	 * @see #setMapInstanceMember(MapInstanceMemberType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapInstanceMember()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapInstanceMember' namespace='##targetNamespace' affiliation='twoWayMapping'"
	 * @generated
	 */
    MapInstanceMemberType getMapInstanceMember();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapInstanceMember <em>Map Instance Member</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Instance Member</em>' containment reference.
	 * @see #getMapInstanceMember()
	 * @generated
	 */
    void setMapInstanceMember(MapInstanceMemberType value);

	/**
	 * Returns the value of the '<em><b>Map Instance Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map resources for an instance.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Instance Type</em>' containment reference.
	 * @see #setMapInstanceType(MapInstanceTypeType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapInstanceType()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapInstanceType' namespace='##targetNamespace' affiliation='twoWayMapping'"
	 * @generated
	 */
	MapInstanceTypeType getMapInstanceType();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapInstanceType <em>Map Instance Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Instance Type</em>' containment reference.
	 * @see #getMapInstanceType()
	 * @generated
	 */
	void setMapInstanceType(MapInstanceTypeType value);

	/**
	 * Returns the value of the '<em><b>Map Into Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Delve into a property path without generating any resources.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Into Property</em>' containment reference.
	 * @see #setMapIntoProperty(MapIntoPropertyType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapIntoProperty()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapIntoProperty' namespace='##targetNamespace' affiliation='twoWayMapping'"
	 * @generated
	 */
	MapIntoPropertyType getMapIntoProperty();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapIntoProperty <em>Map Into Property</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Into Property</em>' containment reference.
	 * @see #getMapIntoProperty()
	 * @generated
	 */
	void setMapIntoProperty(MapIntoPropertyType value);

	/**
	 * Returns the value of the '<em><b>Map Member From Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map RSS from the type declaration attached to the given property.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Member From Type</em>' containment reference.
	 * @see #setMapMemberFromType(MapMemberFromTypeType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapMemberFromType()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapMemberFromType' namespace='##targetNamespace' affiliation='twoWayMapping'"
	 * @generated
	 */
	MapMemberFromTypeType getMapMemberFromType();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapMemberFromType <em>Map Member From Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Member From Type</em>' containment reference.
	 * @see #getMapMemberFromType()
	 * @generated
	 */
	void setMapMemberFromType(MapMemberFromTypeType value);

	/**
	 * Returns the value of the '<em><b>Map Reference Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Reference Element</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a reference property to an array element.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Reference Element</em>' containment reference.
	 * @see #setMapReferenceElement(MapReferenceElementType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapReferenceElement()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapReferenceElement' namespace='##targetNamespace' affiliation='twoWayMapping'"
	 * @generated
	 */
    MapReferenceElementType getMapReferenceElement();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapReferenceElement <em>Map Reference Element</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Reference Element</em>' containment reference.
	 * @see #getMapReferenceElement()
	 * @generated
	 */
    void setMapReferenceElement(MapReferenceElementType value);

	/**
	 * Returns the value of the '<em><b>Map Reference Member</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Reference Member</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a reference property to resources the instance generates.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Reference Member</em>' containment reference.
	 * @see #setMapReferenceMember(MapReferenceMemberType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapReferenceMember()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapReferenceMember' namespace='##targetNamespace' affiliation='twoWayMapping'"
	 * @generated
	 */
    MapReferenceMemberType getMapReferenceMember();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapReferenceMember <em>Map Reference Member</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Reference Member</em>' containment reference.
	 * @see #getMapReferenceMember()
	 * @generated
	 */
    void setMapReferenceMember(MapReferenceMemberType value);

	/**
	 * Returns the value of the '<em><b>Map Reference Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a reference property to resources the instance generates.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Reference Type</em>' containment reference.
	 * @see #setMapReferenceType(MapReferenceTypeType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapReferenceType()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapReferenceType' namespace='##targetNamespace' affiliation='twoWayMapping'"
	 * @generated
	 */
	MapReferenceTypeType getMapReferenceType();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapReferenceType <em>Map Reference Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Reference Type</em>' containment reference.
	 * @see #getMapReferenceType()
	 * @generated
	 */
	void setMapReferenceType(MapReferenceTypeType value);

	/**
	 * Returns the value of the '<em><b>Map Resource</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Resource</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map an instance to a RESOURCE.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Resource</em>' containment reference.
	 * @see #setMapResource(MapResourceType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapResource()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapResource' namespace='##targetNamespace'"
	 * @generated
	 */
    MapResourceType getMapResource();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapResource <em>Map Resource</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Resource</em>' containment reference.
	 * @see #getMapResource()
	 * @generated
	 */
    void setMapResource(MapResourceType value);

	/**
	 * Returns the value of the '<em><b>Map Resource Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Resource Element</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a compound property or instance to resources in an array element.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Resource Element</em>' containment reference.
	 * @see #setMapResourceElement(MapResourceElementType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapResourceElement()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapResourceElement' namespace='##targetNamespace' affiliation='twoWayMapping'"
	 * @generated
	 */
    MapResourceElementType getMapResourceElement();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapResourceElement <em>Map Resource Element</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Resource Element</em>' containment reference.
	 * @see #getMapResourceElement()
	 * @generated
	 */
    void setMapResourceElement(MapResourceElementType value);

	/**
	 * Returns the value of the '<em><b>Map Resource Member</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Resource Member</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a property to a RESOURCE expression or statement.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Resource Member</em>' containment reference.
	 * @see #setMapResourceMember(MapResourceMemberType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapResourceMember()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapResourceMember' namespace='##targetNamespace' affiliation='twoWayMapping'"
	 * @generated
	 */
    MapResourceMemberType getMapResourceMember();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapResourceMember <em>Map Resource Member</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Resource Member</em>' containment reference.
	 * @see #getMapResourceMember()
	 * @generated
	 */
    void setMapResourceMember(MapResourceMemberType value);

	/**
	 * Returns the value of the '<em><b>Map Resource Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a property to a RESOURCE expression or statement.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Resource Type</em>' containment reference.
	 * @see #setMapResourceType(MapResourceTypeType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapResourceType()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapResourceType' namespace='##targetNamespace' affiliation='twoWayMapping'"
	 * @generated
	 */
	MapResourceTypeType getMapResourceType();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapResourceType <em>Map Resource Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Resource Type</em>' containment reference.
	 * @see #getMapResourceType()
	 * @generated
	 */
	void setMapResourceType(MapResourceTypeType value);

	/**
	 * Returns the value of the '<em><b>Map Simple Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Simple Element</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a simple value to an array element.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Simple Element</em>' containment reference.
	 * @see #setMapSimpleElement(MapSimpleElementType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapSimpleElement()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapSimpleElement' namespace='##targetNamespace' affiliation='twoWayMapping'"
	 * @generated
	 */
    MapSimpleElementType getMapSimpleElement();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapSimpleElement <em>Map Simple Element</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Simple Element</em>' containment reference.
	 * @see #getMapSimpleElement()
	 * @generated
	 */
    void setMapSimpleElement(MapSimpleElementType value);

	/**
	 * Returns the value of the '<em><b>Map Simple Member</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Map Simple Member</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a simple value to a member.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Simple Member</em>' containment reference.
	 * @see #setMapSimpleMember(MapSimpleMemberType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapSimpleMember()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapSimpleMember' namespace='##targetNamespace' affiliation='twoWayMapping'"
	 * @generated
	 */
    MapSimpleMemberType getMapSimpleMember();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapSimpleMember <em>Map Simple Member</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Simple Member</em>' containment reference.
	 * @see #getMapSimpleMember()
	 * @generated
	 */
    void setMapSimpleMember(MapSimpleMemberType value);

	/**
	 * Returns the value of the '<em><b>Map Simple Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Map a simple value.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Map Simple Type</em>' containment reference.
	 * @see #setMapSimpleType(MapSimpleTypeType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_MapSimpleType()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mapSimpleType' namespace='##targetNamespace' affiliation='twoWayMapping'"
	 * @generated
	 */
	MapSimpleTypeType getMapSimpleType();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapSimpleType <em>Map Simple Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Simple Type</em>' containment reference.
	 * @see #getMapSimpleType()
	 * @generated
	 */
	void setMapSimpleType(MapSimpleTypeType value);

	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Properties</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The list of properties defined for this component. Components also inherit the properties of their base
	 * 		components, and can have properties added dynamically, see extensionProperties.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference.
	 * @see #setProperties(PropertiesType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_Properties()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='properties' namespace='##targetNamespace'"
	 * @generated
	 */
	PropertiesType getProperties();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getProperties <em>Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Properties</em>' containment reference.
	 * @see #getProperties()
	 * @generated
	 */
	void setProperties(PropertiesType value);

	/**
	 * Returns the value of the '<em><b>Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property</em>' containment reference.
	 * @see #setProperty(SimplePropertyType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_Property()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='property' namespace='##targetNamespace' affiliation='abstractProperty'"
	 * @generated
	 */
	SimplePropertyType getProperty();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getProperty <em>Property</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property</em>' containment reference.
	 * @see #getProperty()
	 * @generated
	 */
	void setProperty(SimplePropertyType value);

	/**
	 * Returns the value of the '<em><b>Property Overrides</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A list of property overrides defined for this component. Components also
	 * 		inherit the property overrides of their base components, with changes applied by the most derived component taking
	 * 		precedence. 
	 * 		
	 * 		Property overrides allow certain characteristics of a property to be modified, such as changing a modifiable property
	 * 		to read-only.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Property Overrides</em>' containment reference.
	 * @see #setPropertyOverrides(PropertyOverridesType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_PropertyOverrides()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='propertyOverrides' namespace='##targetNamespace'"
	 * @generated
	 */
	PropertyOverridesType getPropertyOverrides();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getPropertyOverrides <em>Property Overrides</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Overrides</em>' containment reference.
	 * @see #getPropertyOverrides()
	 * @generated
	 */
	void setPropertyOverrides(PropertyOverridesType value);

	/**
	 * Returns the value of the '<em><b>Script</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Script</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Define a reference to an interface implemented in Javascript.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Script</em>' containment reference.
	 * @see #setScript(ScriptType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_Script()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='script' namespace='##targetNamespace'"
	 * @generated
	 */
    ScriptType getScript();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getScript <em>Script</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Script</em>' containment reference.
	 * @see #getScript()
	 * @generated
	 */
    void setScript(ScriptType value);

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
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_Select()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='select' namespace='##targetNamespace'"
	 * @generated
	 */
    SelectType getSelect();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getSelect <em>Select</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Select</em>' containment reference.
	 * @see #getSelect()
	 * @generated
	 */
    void setSelect(SelectType value);

	/**
	 * Returns the value of the '<em><b>Source Gen</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Source Gen</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		This section controls C/C++ source generation (with the potential to support 
	 * 		other one-way languages).  In a sourceGen element, all the elements in linear 
	 * 		order comprise the component's contributions.  
	 * 		
	 * 		Conditional sourcegen is implemented by using the "form" attribute on 
	 * 		templates or templateGroups, or explicitly checking the regex "form" in inline code.  
	 * 		
	 * 		Another form of conditional sourcegen is event handling code.  The "ifEvents" attribute completely filters out defineLocation/template elements if a given event is not bound to an instance.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Source Gen</em>' containment reference.
	 * @see #setSourceGen(SourceGenType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_SourceGen()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='sourceGen' namespace='##targetNamespace'"
	 * @generated
	 */
    SourceGenType getSourceGen();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getSourceGen <em>Source Gen</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Gen</em>' containment reference.
	 * @see #getSourceGen()
	 * @generated
	 */
    void setSourceGen(SourceGenType value);

	/**
	 * Returns the value of the '<em><b>Source Mapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Source Mapping</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * The sourceMapping element defines a particular style of source generation
	 * which consists of automatic generation of source by structurally
	 * mapping a component instance to a source definition.  
	 * 			
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Source Mapping</em>' containment reference.
	 * @see #setSourceMapping(SourceMappingType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_SourceMapping()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='sourceMapping' namespace='##targetNamespace'"
	 * @generated
	 */
    SourceMappingType getSourceMapping();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getSourceMapping <em>Source Mapping</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Mapping</em>' containment reference.
	 * @see #getSourceMapping()
	 * @generated
	 */
    void setSourceMapping(SourceMappingType value);

	/**
	 * Returns the value of the '<em><b>Source Type Mapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * The sourceTypeMapping element mirrors sourceMapping but provides stored source
	 * mapping data for use by compound or enum types.  
	 * 			
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Source Type Mapping</em>' containment reference.
	 * @see #setSourceTypeMapping(SourceTypeMappingType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_SourceTypeMapping()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='sourceTypeMapping' namespace='##targetNamespace'"
	 * @generated
	 */
	SourceTypeMappingType getSourceTypeMapping();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getSourceTypeMapping <em>Source Type Mapping</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Type Mapping</em>' containment reference.
	 * @see #getSourceTypeMapping()
	 * @generated
	 */
	void setSourceTypeMapping(SourceTypeMappingType value);

	/**
	 * Returns the value of the '<em><b>Symbian</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Symbian</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The optional Symbian element provides SDK compatibility information and information about the related 
	 * 	C++ classes and resource structures. When this element is omitted the component will be considered incompatible
	 * 	with all Symbian SDKs.
	 * 			
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Symbian</em>' containment reference.
	 * @see #setSymbian(SymbianType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_Symbian()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='symbian' namespace='##targetNamespace'"
	 * @generated
	 */
	SymbianType getSymbian();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getSymbian <em>Symbian</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Symbian</em>' containment reference.
	 * @see #getSymbian()
	 * @generated
	 */
	void setSymbian(SymbianType value);

	/**
	 * Returns the value of the '<em><b>Template</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Template</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		This defines a contribution.  The text element is template-expanded 
	 * 		with ${ ... } expression escapes and may contain Javascript in &lt;% ... %&gt; sections.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Template</em>' containment reference.
	 * @see #setTemplate(TemplateType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_Template()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='template' namespace='##targetNamespace'"
	 * @generated
	 */
    TemplateType getTemplate();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getTemplate <em>Template</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Template</em>' containment reference.
	 * @see #getTemplate()
	 * @generated
	 */
    void setTemplate(TemplateType value);

	/**
	 * Returns the value of the '<em><b>Template Group</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Template Group</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		A logical grouping of templates and locations.  Attributes
	 * 		present on the group are automatically applied to children.
	 * 		Groups may not be nested.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Template Group</em>' containment reference.
	 * @see #setTemplateGroup(TemplateGroupType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_TemplateGroup()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='templateGroup' namespace='##targetNamespace'"
	 * @generated
	 */
    TemplateGroupType getTemplateGroup();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getTemplateGroup <em>Template Group</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Template Group</em>' containment reference.
	 * @see #getTemplateGroup()
	 * @generated
	 */
    void setTemplateGroup(TemplateGroupType value);

	/**
	 * Returns the value of the '<em><b>Use Template</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Use Template</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		In a derived component, selects a template from the base by id.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Use Template</em>' containment reference.
	 * @see #setUseTemplate(UseTemplateType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_UseTemplate()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='useTemplate' namespace='##targetNamespace'"
	 * @generated
	 */
    UseTemplateType getUseTemplate();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getUseTemplate <em>Use Template</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Use Template</em>' containment reference.
	 * @see #getUseTemplate()
	 * @generated
	 */
    void setUseTemplate(UseTemplateType value);

	/**
	 * Returns the value of the '<em><b>Use Template Group</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Use Template Group</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		In a derived component, selects a template group from the base by id.
	 * 		If no subelements are specified, all the named templates (those with ids)
	 * 		are inherited.  No templates without ids are inherited.
	 * 		
	 * 		If subelements are specified, these describe the specific templates
	 * 		to inherit from the group.  The id "*" may be used to bring in all
	 * 		named templates.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Use Template Group</em>' containment reference.
	 * @see #setUseTemplateGroup(UseTemplateGroupType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentRoot_UseTemplateGroup()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='useTemplateGroup' namespace='##targetNamespace'"
	 * @generated
	 */
    UseTemplateGroupType getUseTemplateGroup();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentRoot#getUseTemplateGroup <em>Use Template Group</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Use Template Group</em>' containment reference.
	 * @see #getUseTemplateGroup()
	 * @generated
	 */
    void setUseTemplateGroup(UseTemplateGroupType value);

} // DocumentRoot
