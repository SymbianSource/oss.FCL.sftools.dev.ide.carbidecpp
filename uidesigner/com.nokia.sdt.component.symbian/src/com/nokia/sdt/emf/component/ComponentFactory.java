/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.nokia.sdt.emf.component.ComponentPackage
 * @generated
 */
public interface ComponentFactory extends EFactory{
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ComponentFactory eINSTANCE = com.nokia.sdt.emf.component.impl.ComponentFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Abstract Property Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Abstract Property Type</em>'.
	 * @generated
	 */
	AbstractPropertyType createAbstractPropertyType();

	/**
	 * Returns a new object of class '<em>Array Property Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Array Property Type</em>'.
	 * @generated
	 */
	ArrayPropertyType createArrayPropertyType();

	/**
	 * Returns a new object of class '<em>Attributes Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attributes Type</em>'.
	 * @generated
	 */
	AttributesType createAttributesType();

	/**
	 * Returns a new object of class '<em>Attribute Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute Type</em>'.
	 * @generated
	 */
	AttributeType createAttributeType();

	/**
	 * Returns a new object of class '<em>Choice Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Choice Type</em>'.
	 * @generated
	 */
    ChoiceType createChoiceType();

	/**
	 * Returns a new object of class '<em>Code Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Code Type</em>'.
	 * @generated
	 */
	CodeType createCodeType();

	/**
	 * Returns a new object of class '<em>Definition Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Definition Type</em>'.
	 * @generated
	 */
	ComponentDefinitionType createComponentDefinitionType();

	/**
	 * Returns a new object of class '<em>Reference Property Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Reference Property Type</em>'.
	 * @generated
	 */
	ComponentReferencePropertyType createComponentReferencePropertyType();

	/**
	 * Returns a new object of class '<em>Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Type</em>'.
	 * @generated
	 */
	ComponentType createComponentType();

	/**
	 * Returns a new object of class '<em>Compound Property Declaration Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Compound Property Declaration Type</em>'.
	 * @generated
	 */
	CompoundPropertyDeclarationType createCompoundPropertyDeclarationType();

	/**
	 * Returns a new object of class '<em>Compound Property Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Compound Property Type</em>'.
	 * @generated
	 */
	CompoundPropertyType createCompoundPropertyType();

	/**
	 * Returns a new object of class '<em>Define Location Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Define Location Type</em>'.
	 * @generated
	 */
    DefineLocationType createDefineLocationType();

	/**
	 * Returns a new object of class '<em>Define Macro Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Define Macro Type</em>'.
	 * @generated
	 */
	DefineMacroType createDefineMacroType();

	/**
	 * Returns a new object of class '<em>Designer Images Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Designer Images Type</em>'.
	 * @generated
	 */
	DesignerImagesType createDesignerImagesType();

	/**
	 * Returns a new object of class '<em>Documentation Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Documentation Type</em>'.
	 * @generated
	 */
	DocumentationType createDocumentationType();

	/**
	 * Returns a new object of class '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Document Root</em>'.
	 * @generated
	 */
	DocumentRoot createDocumentRoot();

	/**
	 * Returns a new object of class '<em>Enum Element Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Enum Element Type</em>'.
	 * @generated
	 */
	EnumElementType createEnumElementType();

	/**
	 * Returns a new object of class '<em>Enum Property Declaration Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Enum Property Declaration Type</em>'.
	 * @generated
	 */
	EnumPropertyDeclarationType createEnumPropertyDeclarationType();

	/**
	 * Returns a new object of class '<em>Enum Property Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Enum Property Type</em>'.
	 * @generated
	 */
	EnumPropertyType createEnumPropertyType();

	/**
	 * Returns a new object of class '<em>Events Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Events Type</em>'.
	 * @generated
	 */
	EventsType createEventsType();

	/**
	 * Returns a new object of class '<em>Event Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Event Type</em>'.
	 * @generated
	 */
	EventType createEventType();

	/**
	 * Returns a new object of class '<em>Expand Argument Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Expand Argument Type</em>'.
	 * @generated
	 */
	ExpandArgumentType createExpandArgumentType();

	/**
	 * Returns a new object of class '<em>Expand Macro Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Expand Macro Type</em>'.
	 * @generated
	 */
	ExpandMacroType createExpandMacroType();

	/**
	 * Returns a new object of class '<em>Extension Properties Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Extension Properties Type</em>'.
	 * @generated
	 */
	ExtensionPropertiesType createExtensionPropertiesType();

	/**
	 * Returns a new object of class '<em>Implementations Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Implementations Type</em>'.
	 * @generated
	 */
	ImplementationsType createImplementationsType();

	/**
	 * Returns a new object of class '<em>Implementation Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Implementation Type</em>'.
	 * @generated
	 */
	ImplementationType createImplementationType();

	/**
	 * Returns a new object of class '<em>Import Arguments Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Import Arguments Type</em>'.
	 * @generated
	 */
	ImportArgumentsType createImportArgumentsType();

	/**
	 * Returns a new object of class '<em>Inline Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Inline Type</em>'.
	 * @generated
	 */
    InlineType createInlineType();

	/**
	 * Returns a new object of class '<em>Interface Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Interface Type</em>'.
	 * @generated
	 */
	InterfaceType createInterfaceType();

	/**
	 * Returns a new object of class '<em>Macro Argument Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Macro Argument Type</em>'.
	 * @generated
	 */
	MacroArgumentType createMacroArgumentType();

	/**
	 * Returns a new object of class '<em>Map Array Member Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Array Member Type</em>'.
	 * @generated
	 */
    MapArrayMemberType createMapArrayMemberType();

	/**
	 * Returns a new object of class '<em>Map Array Type Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Array Type Type</em>'.
	 * @generated
	 */
	MapArrayTypeType createMapArrayTypeType();

	/**
	 * Returns a new object of class '<em>Map Bitmask Element Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Bitmask Element Type</em>'.
	 * @generated
	 */
    MapBitmaskElementType createMapBitmaskElementType();

	/**
	 * Returns a new object of class '<em>Map Bitmask Member Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Bitmask Member Type</em>'.
	 * @generated
	 */
    MapBitmaskMemberType createMapBitmaskMemberType();

	/**
	 * Returns a new object of class '<em>Map Bitmask Type Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Bitmask Type Type</em>'.
	 * @generated
	 */
	MapBitmaskTypeType createMapBitmaskTypeType();

	/**
	 * Returns a new object of class '<em>Map Bitmask Value Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Bitmask Value Type</em>'.
	 * @generated
	 */
    MapBitmaskValueType createMapBitmaskValueType();

	/**
	 * Returns a new object of class '<em>Map Element From Type Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Element From Type Type</em>'.
	 * @generated
	 */
	MapElementFromTypeType createMapElementFromTypeType();

	/**
	 * Returns a new object of class '<em>Map Enum Element Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Enum Element Type</em>'.
	 * @generated
	 */
    MapEnumElementType createMapEnumElementType();

	/**
	 * Returns a new object of class '<em>Map Enum Member Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Enum Member Type</em>'.
	 * @generated
	 */
    MapEnumMemberType createMapEnumMemberType();

	/**
	 * Returns a new object of class '<em>Map Enum Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Enum Type</em>'.
	 * @generated
	 */
    MapEnumType createMapEnumType();

	/**
	 * Returns a new object of class '<em>Map Enum Type Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Enum Type Type</em>'.
	 * @generated
	 */
	MapEnumTypeType createMapEnumTypeType();

	/**
	 * Returns a new object of class '<em>Map Fixed Element Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Fixed Element Type</em>'.
	 * @generated
	 */
	MapFixedElementType createMapFixedElementType();

	/**
	 * Returns a new object of class '<em>Map Fixed Member Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Fixed Member Type</em>'.
	 * @generated
	 */
    MapFixedMemberType createMapFixedMemberType();

	/**
	 * Returns a new object of class '<em>Map Fixed Type Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Fixed Type Type</em>'.
	 * @generated
	 */
	MapFixedTypeType createMapFixedTypeType();

	/**
	 * Returns a new object of class '<em>Map Identifier Element Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Identifier Element Type</em>'.
	 * @generated
	 */
    MapIdentifierElementType createMapIdentifierElementType();

	/**
	 * Returns a new object of class '<em>Map Identifier Member Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Identifier Member Type</em>'.
	 * @generated
	 */
    MapIdentifierMemberType createMapIdentifierMemberType();

	/**
	 * Returns a new object of class '<em>Map Identifier Type Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Identifier Type Type</em>'.
	 * @generated
	 */
	MapIdentifierTypeType createMapIdentifierTypeType();

	/**
	 * Returns a new object of class '<em>Map Instance Element Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Instance Element Type</em>'.
	 * @generated
	 */
    MapInstanceElementType createMapInstanceElementType();

	/**
	 * Returns a new object of class '<em>Map Instance Member Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Instance Member Type</em>'.
	 * @generated
	 */
    MapInstanceMemberType createMapInstanceMemberType();

	/**
	 * Returns a new object of class '<em>Map Instance Type Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Instance Type Type</em>'.
	 * @generated
	 */
	MapInstanceTypeType createMapInstanceTypeType();

	/**
	 * Returns a new object of class '<em>Map Into Property Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Into Property Type</em>'.
	 * @generated
	 */
	MapIntoPropertyType createMapIntoPropertyType();

	/**
	 * Returns a new object of class '<em>Map Member From Type Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Member From Type Type</em>'.
	 * @generated
	 */
	MapMemberFromTypeType createMapMemberFromTypeType();

	/**
	 * Returns a new object of class '<em>Mapping Array Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Mapping Array Type</em>'.
	 * @generated
	 */
    MappingArrayType createMappingArrayType();

	/**
	 * Returns a new object of class '<em>Mapping Bitmask Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Mapping Bitmask Type</em>'.
	 * @generated
	 */
    MappingBitmaskType createMappingBitmaskType();

	/**
	 * Returns a new object of class '<em>Mapping Enum Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Mapping Enum Type</em>'.
	 * @generated
	 */
    MappingEnumType createMappingEnumType();

	/**
	 * Returns a new object of class '<em>Mapping Fixed Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Mapping Fixed Type</em>'.
	 * @generated
	 */
    MappingFixedType createMappingFixedType();

	/**
	 * Returns a new object of class '<em>Mapping Identifier Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Mapping Identifier Type</em>'.
	 * @generated
	 */
    MappingIdentifierType createMappingIdentifierType();

	/**
	 * Returns a new object of class '<em>Mapping Instance Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Mapping Instance Type</em>'.
	 * @generated
	 */
    MappingInstanceType createMappingInstanceType();

	/**
	 * Returns a new object of class '<em>Mapping Reference Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Mapping Reference Type</em>'.
	 * @generated
	 */
    MappingReferenceType createMappingReferenceType();

	/**
	 * Returns a new object of class '<em>Mapping Resource Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Mapping Resource Type</em>'.
	 * @generated
	 */
    MappingResourceType createMappingResourceType();

	/**
	 * Returns a new object of class '<em>Mapping Simple Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Mapping Simple Type</em>'.
	 * @generated
	 */
    MappingSimpleType createMappingSimpleType();

	/**
	 * Returns a new object of class '<em>Map Reference Element Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Reference Element Type</em>'.
	 * @generated
	 */
    MapReferenceElementType createMapReferenceElementType();

	/**
	 * Returns a new object of class '<em>Map Reference Member Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Reference Member Type</em>'.
	 * @generated
	 */
    MapReferenceMemberType createMapReferenceMemberType();

	/**
	 * Returns a new object of class '<em>Map Reference Type Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Reference Type Type</em>'.
	 * @generated
	 */
	MapReferenceTypeType createMapReferenceTypeType();

	/**
	 * Returns a new object of class '<em>Map Resource Element Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Resource Element Type</em>'.
	 * @generated
	 */
    MapResourceElementType createMapResourceElementType();

	/**
	 * Returns a new object of class '<em>Map Resource Member Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Resource Member Type</em>'.
	 * @generated
	 */
    MapResourceMemberType createMapResourceMemberType();

	/**
	 * Returns a new object of class '<em>Map Resource Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Resource Type</em>'.
	 * @generated
	 */
    MapResourceType createMapResourceType();

	/**
	 * Returns a new object of class '<em>Map Resource Type Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Resource Type Type</em>'.
	 * @generated
	 */
	MapResourceTypeType createMapResourceTypeType();

	/**
	 * Returns a new object of class '<em>Map Simple Element Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Simple Element Type</em>'.
	 * @generated
	 */
    MapSimpleElementType createMapSimpleElementType();

	/**
	 * Returns a new object of class '<em>Map Simple Member Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Simple Member Type</em>'.
	 * @generated
	 */
    MapSimpleMemberType createMapSimpleMemberType();

	/**
	 * Returns a new object of class '<em>Map Simple Type Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Simple Type Type</em>'.
	 * @generated
	 */
	MapSimpleTypeType createMapSimpleTypeType();

	/**
	 * Returns a new object of class '<em>Properties Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Properties Type</em>'.
	 * @generated
	 */
	PropertiesType createPropertiesType();

	/**
	 * Returns a new object of class '<em>Property Overrides Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property Overrides Type</em>'.
	 * @generated
	 */
	PropertyOverridesType createPropertyOverridesType();

	/**
	 * Returns a new object of class '<em>Property Override Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property Override Type</em>'.
	 * @generated
	 */
	PropertyOverrideType createPropertyOverrideType();

	/**
	 * Returns a new object of class '<em>Script Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Script Type</em>'.
	 * @generated
	 */
	ScriptType createScriptType();

	/**
	 * Returns a new object of class '<em>Select Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Select Type</em>'.
	 * @generated
	 */
    SelectType createSelectType();

	/**
	 * Returns a new object of class '<em>Simple Property Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Simple Property Type</em>'.
	 * @generated
	 */
	SimplePropertyType createSimplePropertyType();

	/**
	 * Returns a new object of class '<em>Source Gen Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Source Gen Type</em>'.
	 * @generated
	 */
    SourceGenType createSourceGenType();

	/**
	 * Returns a new object of class '<em>Source Mapping Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Source Mapping Type</em>'.
	 * @generated
	 */
    SourceMappingType createSourceMappingType();

	/**
	 * Returns a new object of class '<em>Source Type Mapping Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Source Type Mapping Type</em>'.
	 * @generated
	 */
	SourceTypeMappingType createSourceTypeMappingType();

	/**
	 * Returns a new object of class '<em>Symbian Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Symbian Type</em>'.
	 * @generated
	 */
	SymbianType createSymbianType();

	/**
	 * Returns a new object of class '<em>Template Group Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Template Group Type</em>'.
	 * @generated
	 */
    TemplateGroupType createTemplateGroupType();

	/**
	 * Returns a new object of class '<em>Template Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Template Type</em>'.
	 * @generated
	 */
    TemplateType createTemplateType();

	/**
	 * Returns a new object of class '<em>Two Way Mapping Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Two Way Mapping Type</em>'.
	 * @generated
	 */
    TwoWayMappingType createTwoWayMappingType();

	/**
	 * Returns a new object of class '<em>Use Template Group Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Use Template Group Type</em>'.
	 * @generated
	 */
    UseTemplateGroupType createUseTemplateGroupType();

	/**
	 * Returns a new object of class '<em>Use Template Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Use Template Type</em>'.
	 * @generated
	 */
    UseTemplateType createUseTemplateType();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ComponentPackage getComponentPackage();

} //ComponentFactory
