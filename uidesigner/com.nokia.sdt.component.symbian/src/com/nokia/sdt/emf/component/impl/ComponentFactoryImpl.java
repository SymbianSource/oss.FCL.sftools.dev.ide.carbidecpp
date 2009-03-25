/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.*;
import com.nokia.sdt.emf.component.AbstractPropertyType;
import com.nokia.sdt.emf.component.ArrayPropertyType;
import com.nokia.sdt.emf.component.AttributeType;
import com.nokia.sdt.emf.component.AttributesType;
import com.nokia.sdt.emf.component.ChoiceType;
import com.nokia.sdt.emf.component.CodeType;
import com.nokia.sdt.emf.component.ComponentDefinitionType;
import com.nokia.sdt.emf.component.ComponentFactory;
import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.ComponentReferencePropertyType;
import com.nokia.sdt.emf.component.ComponentType;
import com.nokia.sdt.emf.component.CompoundPropertyDeclarationType;
import com.nokia.sdt.emf.component.CompoundPropertyType;
import com.nokia.sdt.emf.component.DefineLocationType;
import com.nokia.sdt.emf.component.DefineMacroType;
import com.nokia.sdt.emf.component.DesignerImagesType;
import com.nokia.sdt.emf.component.DocumentRoot;
import com.nokia.sdt.emf.component.DocumentationType;
import com.nokia.sdt.emf.component.EnumElementType;
import com.nokia.sdt.emf.component.EnumPropertyDeclarationType;
import com.nokia.sdt.emf.component.EnumPropertyType;
import com.nokia.sdt.emf.component.EventType;
import com.nokia.sdt.emf.component.EventsType;
import com.nokia.sdt.emf.component.ExpandArgumentType;
import com.nokia.sdt.emf.component.ExpandMacroType;
import com.nokia.sdt.emf.component.ExtensionPropertiesType;
import com.nokia.sdt.emf.component.ImplementationType;
import com.nokia.sdt.emf.component.ImplementationsType;
import com.nokia.sdt.emf.component.ImportArgumentsType;
import com.nokia.sdt.emf.component.InlineType;
import com.nokia.sdt.emf.component.InterfaceType;
import com.nokia.sdt.emf.component.MacroArgumentType;
import com.nokia.sdt.emf.component.MapArrayMemberType;
import com.nokia.sdt.emf.component.MapArrayTypeType;
import com.nokia.sdt.emf.component.MapBitmaskElementType;
import com.nokia.sdt.emf.component.MapBitmaskMemberType;
import com.nokia.sdt.emf.component.MapBitmaskTypeType;
import com.nokia.sdt.emf.component.MapBitmaskValueType;
import com.nokia.sdt.emf.component.MapElementFromTypeType;
import com.nokia.sdt.emf.component.MapEnumElementType;
import com.nokia.sdt.emf.component.MapEnumMemberType;
import com.nokia.sdt.emf.component.MapEnumType;
import com.nokia.sdt.emf.component.MapEnumTypeType;
import com.nokia.sdt.emf.component.MapFixedMemberType;
import com.nokia.sdt.emf.component.MapFixedTypeType;
import com.nokia.sdt.emf.component.MapIdentifierElementType;
import com.nokia.sdt.emf.component.MapIdentifierMemberType;
import com.nokia.sdt.emf.component.MapIdentifierTypeType;
import com.nokia.sdt.emf.component.MapInstanceElementType;
import com.nokia.sdt.emf.component.MapInstanceMemberType;
import com.nokia.sdt.emf.component.MapInstanceTypeType;
import com.nokia.sdt.emf.component.MapIntoPropertyType;
import com.nokia.sdt.emf.component.MapMemberFromTypeType;
import com.nokia.sdt.emf.component.MapReferenceElementType;
import com.nokia.sdt.emf.component.MapReferenceMemberType;
import com.nokia.sdt.emf.component.MapReferenceTypeType;
import com.nokia.sdt.emf.component.MapResourceElementType;
import com.nokia.sdt.emf.component.MapResourceMemberType;
import com.nokia.sdt.emf.component.MapResourceType;
import com.nokia.sdt.emf.component.MapResourceTypeType;
import com.nokia.sdt.emf.component.MapSimpleElementType;
import com.nokia.sdt.emf.component.MapSimpleMemberType;
import com.nokia.sdt.emf.component.MapSimpleTypeType;
import com.nokia.sdt.emf.component.MappingArrayType;
import com.nokia.sdt.emf.component.MappingBitmaskType;
import com.nokia.sdt.emf.component.MappingEnumType;
import com.nokia.sdt.emf.component.MappingFixedType;
import com.nokia.sdt.emf.component.MappingIdentifierType;
import com.nokia.sdt.emf.component.MappingInstanceType;
import com.nokia.sdt.emf.component.MappingReferenceType;
import com.nokia.sdt.emf.component.MappingResourceType;
import com.nokia.sdt.emf.component.MappingSimpleType;
import com.nokia.sdt.emf.component.PropertiesType;
import com.nokia.sdt.emf.component.PropertyDataType;
import com.nokia.sdt.emf.component.PropertyOverrideType;
import com.nokia.sdt.emf.component.PropertyOverridesType;
import com.nokia.sdt.emf.component.ReferenceScopeType;
import com.nokia.sdt.emf.component.ScriptType;
import com.nokia.sdt.emf.component.SelectType;
import com.nokia.sdt.emf.component.SimplePropertyType;
import com.nokia.sdt.emf.component.SourceGenType;
import com.nokia.sdt.emf.component.SourceMappingType;
import com.nokia.sdt.emf.component.SourceTypeMappingType;
import com.nokia.sdt.emf.component.SymbianType;
import com.nokia.sdt.emf.component.TemplateGroupType;
import com.nokia.sdt.emf.component.TemplateType;
import com.nokia.sdt.emf.component.TwoWayMappingType;
import com.nokia.sdt.emf.component.UseExpandMacroType;
import com.nokia.sdt.emf.component.UseTemplateGroupType;
import com.nokia.sdt.emf.component.UseTemplateType;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ComponentFactoryImpl extends EFactoryImpl implements ComponentFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ComponentFactory init() {
		try {
			ComponentFactory theComponentFactory = (ComponentFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.nokia.com/sdt/emf/component"); 
			if (theComponentFactory != null) {
				return theComponentFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ComponentFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE: return createAbstractPropertyType();
			case ComponentPackage.ARRAY_PROPERTY_TYPE: return createArrayPropertyType();
			case ComponentPackage.ATTRIBUTES_TYPE: return createAttributesType();
			case ComponentPackage.ATTRIBUTE_TYPE: return createAttributeType();
			case ComponentPackage.CHOICE_TYPE: return createChoiceType();
			case ComponentPackage.CODE_TYPE: return createCodeType();
			case ComponentPackage.COMPONENT_DEFINITION_TYPE: return createComponentDefinitionType();
			case ComponentPackage.COMPONENT_REFERENCE_PROPERTY_TYPE: return createComponentReferencePropertyType();
			case ComponentPackage.COMPONENT_TYPE: return createComponentType();
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE: return createCompoundPropertyDeclarationType();
			case ComponentPackage.COMPOUND_PROPERTY_TYPE: return createCompoundPropertyType();
			case ComponentPackage.DEFINE_LOCATION_TYPE: return createDefineLocationType();
			case ComponentPackage.DEFINE_MACRO_TYPE: return createDefineMacroType();
			case ComponentPackage.DESIGNER_IMAGES_TYPE: return createDesignerImagesType();
			case ComponentPackage.DOCUMENTATION_TYPE: return createDocumentationType();
			case ComponentPackage.DOCUMENT_ROOT: return createDocumentRoot();
			case ComponentPackage.ENUM_ELEMENT_TYPE: return createEnumElementType();
			case ComponentPackage.ENUM_PROPERTY_DECLARATION_TYPE: return createEnumPropertyDeclarationType();
			case ComponentPackage.ENUM_PROPERTY_TYPE: return createEnumPropertyType();
			case ComponentPackage.EVENTS_TYPE: return createEventsType();
			case ComponentPackage.EVENT_TYPE: return createEventType();
			case ComponentPackage.EXPAND_ARGUMENT_TYPE: return createExpandArgumentType();
			case ComponentPackage.EXPAND_MACRO_TYPE: return createExpandMacroType();
			case ComponentPackage.EXTENSION_PROPERTIES_TYPE: return createExtensionPropertiesType();
			case ComponentPackage.IMPLEMENTATIONS_TYPE: return createImplementationsType();
			case ComponentPackage.IMPLEMENTATION_TYPE: return createImplementationType();
			case ComponentPackage.IMPORT_ARGUMENTS_TYPE: return createImportArgumentsType();
			case ComponentPackage.INLINE_TYPE: return createInlineType();
			case ComponentPackage.INTERFACE_TYPE: return createInterfaceType();
			case ComponentPackage.MACRO_ARGUMENT_TYPE: return createMacroArgumentType();
			case ComponentPackage.MAP_ARRAY_MEMBER_TYPE: return createMapArrayMemberType();
			case ComponentPackage.MAP_ARRAY_TYPE_TYPE: return createMapArrayTypeType();
			case ComponentPackage.MAP_BITMASK_ELEMENT_TYPE: return createMapBitmaskElementType();
			case ComponentPackage.MAP_BITMASK_MEMBER_TYPE: return createMapBitmaskMemberType();
			case ComponentPackage.MAP_BITMASK_TYPE_TYPE: return createMapBitmaskTypeType();
			case ComponentPackage.MAP_BITMASK_VALUE_TYPE: return createMapBitmaskValueType();
			case ComponentPackage.MAP_ELEMENT_FROM_TYPE_TYPE: return createMapElementFromTypeType();
			case ComponentPackage.MAP_ENUM_ELEMENT_TYPE: return createMapEnumElementType();
			case ComponentPackage.MAP_ENUM_MEMBER_TYPE: return createMapEnumMemberType();
			case ComponentPackage.MAP_ENUM_TYPE: return createMapEnumType();
			case ComponentPackage.MAP_ENUM_TYPE_TYPE: return createMapEnumTypeType();
			case ComponentPackage.MAP_FIXED_ELEMENT_TYPE: return createMapFixedElementType();
			case ComponentPackage.MAP_FIXED_MEMBER_TYPE: return createMapFixedMemberType();
			case ComponentPackage.MAP_FIXED_TYPE_TYPE: return createMapFixedTypeType();
			case ComponentPackage.MAP_IDENTIFIER_ELEMENT_TYPE: return createMapIdentifierElementType();
			case ComponentPackage.MAP_IDENTIFIER_MEMBER_TYPE: return createMapIdentifierMemberType();
			case ComponentPackage.MAP_IDENTIFIER_TYPE_TYPE: return createMapIdentifierTypeType();
			case ComponentPackage.MAP_INSTANCE_ELEMENT_TYPE: return createMapInstanceElementType();
			case ComponentPackage.MAP_INSTANCE_MEMBER_TYPE: return createMapInstanceMemberType();
			case ComponentPackage.MAP_INSTANCE_TYPE_TYPE: return createMapInstanceTypeType();
			case ComponentPackage.MAP_INTO_PROPERTY_TYPE: return createMapIntoPropertyType();
			case ComponentPackage.MAP_MEMBER_FROM_TYPE_TYPE: return createMapMemberFromTypeType();
			case ComponentPackage.MAPPING_ARRAY_TYPE: return createMappingArrayType();
			case ComponentPackage.MAPPING_BITMASK_TYPE: return createMappingBitmaskType();
			case ComponentPackage.MAPPING_ENUM_TYPE: return createMappingEnumType();
			case ComponentPackage.MAPPING_FIXED_TYPE: return createMappingFixedType();
			case ComponentPackage.MAPPING_IDENTIFIER_TYPE: return createMappingIdentifierType();
			case ComponentPackage.MAPPING_INSTANCE_TYPE: return createMappingInstanceType();
			case ComponentPackage.MAPPING_REFERENCE_TYPE: return createMappingReferenceType();
			case ComponentPackage.MAPPING_RESOURCE_TYPE: return createMappingResourceType();
			case ComponentPackage.MAPPING_SIMPLE_TYPE: return createMappingSimpleType();
			case ComponentPackage.MAP_REFERENCE_ELEMENT_TYPE: return createMapReferenceElementType();
			case ComponentPackage.MAP_REFERENCE_MEMBER_TYPE: return createMapReferenceMemberType();
			case ComponentPackage.MAP_REFERENCE_TYPE_TYPE: return createMapReferenceTypeType();
			case ComponentPackage.MAP_RESOURCE_ELEMENT_TYPE: return createMapResourceElementType();
			case ComponentPackage.MAP_RESOURCE_MEMBER_TYPE: return createMapResourceMemberType();
			case ComponentPackage.MAP_RESOURCE_TYPE: return createMapResourceType();
			case ComponentPackage.MAP_RESOURCE_TYPE_TYPE: return createMapResourceTypeType();
			case ComponentPackage.MAP_SIMPLE_ELEMENT_TYPE: return createMapSimpleElementType();
			case ComponentPackage.MAP_SIMPLE_MEMBER_TYPE: return createMapSimpleMemberType();
			case ComponentPackage.MAP_SIMPLE_TYPE_TYPE: return createMapSimpleTypeType();
			case ComponentPackage.PROPERTIES_TYPE: return createPropertiesType();
			case ComponentPackage.PROPERTY_OVERRIDES_TYPE: return createPropertyOverridesType();
			case ComponentPackage.PROPERTY_OVERRIDE_TYPE: return createPropertyOverrideType();
			case ComponentPackage.SCRIPT_TYPE: return createScriptType();
			case ComponentPackage.SELECT_TYPE: return createSelectType();
			case ComponentPackage.SIMPLE_PROPERTY_TYPE: return createSimplePropertyType();
			case ComponentPackage.SOURCE_GEN_TYPE: return createSourceGenType();
			case ComponentPackage.SOURCE_MAPPING_TYPE: return createSourceMappingType();
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE: return createSourceTypeMappingType();
			case ComponentPackage.SYMBIAN_TYPE: return createSymbianType();
			case ComponentPackage.TEMPLATE_GROUP_TYPE: return createTemplateGroupType();
			case ComponentPackage.TEMPLATE_TYPE: return createTemplateType();
			case ComponentPackage.TWO_WAY_MAPPING_TYPE: return createTwoWayMappingType();
			case ComponentPackage.USE_TEMPLATE_GROUP_TYPE: return createUseTemplateGroupType();
			case ComponentPackage.USE_TEMPLATE_TYPE: return createUseTemplateType();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case ComponentPackage.PROPERTY_DATA_TYPE:
				return createPropertyDataTypeFromString(eDataType, initialValue);
			case ComponentPackage.REFERENCE_SCOPE_TYPE:
				return createReferenceScopeTypeFromString(eDataType, initialValue);
			case ComponentPackage.STANDALONE_TYPE:
				return createStandaloneTypeFromString(eDataType, initialValue);
			case ComponentPackage.LIST_OF_STRINGS:
				return createListOfStringsFromString(eDataType, initialValue);
			case ComponentPackage.PROPERTY_DATA_TYPE_OBJECT:
				return createPropertyDataTypeObjectFromString(eDataType, initialValue);
			case ComponentPackage.REFERENCE_SCOPE_TYPE_OBJECT:
				return createReferenceScopeTypeObjectFromString(eDataType, initialValue);
			case ComponentPackage.STANDALONE_TYPE_OBJECT:
				return createStandaloneTypeObjectFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case ComponentPackage.PROPERTY_DATA_TYPE:
				return convertPropertyDataTypeToString(eDataType, instanceValue);
			case ComponentPackage.REFERENCE_SCOPE_TYPE:
				return convertReferenceScopeTypeToString(eDataType, instanceValue);
			case ComponentPackage.STANDALONE_TYPE:
				return convertStandaloneTypeToString(eDataType, instanceValue);
			case ComponentPackage.LIST_OF_STRINGS:
				return convertListOfStringsToString(eDataType, instanceValue);
			case ComponentPackage.PROPERTY_DATA_TYPE_OBJECT:
				return convertPropertyDataTypeObjectToString(eDataType, instanceValue);
			case ComponentPackage.REFERENCE_SCOPE_TYPE_OBJECT:
				return convertReferenceScopeTypeObjectToString(eDataType, instanceValue);
			case ComponentPackage.STANDALONE_TYPE_OBJECT:
				return convertStandaloneTypeObjectToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractPropertyType createAbstractPropertyType() {
		AbstractPropertyTypeImpl abstractPropertyType = new AbstractPropertyTypeImpl();
		return abstractPropertyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayPropertyType createArrayPropertyType() {
		ArrayPropertyTypeImpl arrayPropertyType = new ArrayPropertyTypeImpl();
		return arrayPropertyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributesType createAttributesType() {
		AttributesTypeImpl attributesType = new AttributesTypeImpl();
		return attributesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeType createAttributeType() {
		AttributeTypeImpl attributeType = new AttributeTypeImpl();
		return attributeType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public ChoiceType createChoiceType() {
		ChoiceTypeImpl choiceType = new ChoiceTypeImpl();
		return choiceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CodeType createCodeType() {
		CodeTypeImpl codeType = new CodeTypeImpl();
		return codeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentDefinitionType createComponentDefinitionType() {
		ComponentDefinitionTypeImpl componentDefinitionType = new ComponentDefinitionTypeImpl();
		return componentDefinitionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentReferencePropertyType createComponentReferencePropertyType() {
		ComponentReferencePropertyTypeImpl componentReferencePropertyType = new ComponentReferencePropertyTypeImpl();
		return componentReferencePropertyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentType createComponentType() {
		ComponentTypeImpl componentType = new ComponentTypeImpl();
		return componentType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompoundPropertyDeclarationType createCompoundPropertyDeclarationType() {
		CompoundPropertyDeclarationTypeImpl compoundPropertyDeclarationType = new CompoundPropertyDeclarationTypeImpl();
		return compoundPropertyDeclarationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompoundPropertyType createCompoundPropertyType() {
		CompoundPropertyTypeImpl compoundPropertyType = new CompoundPropertyTypeImpl();
		return compoundPropertyType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public DefineLocationType createDefineLocationType() {
		DefineLocationTypeImpl defineLocationType = new DefineLocationTypeImpl();
		return defineLocationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DefineMacroType createDefineMacroType() {
		DefineMacroTypeImpl defineMacroType = new DefineMacroTypeImpl();
		return defineMacroType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DesignerImagesType createDesignerImagesType() {
		DesignerImagesTypeImpl designerImagesType = new DesignerImagesTypeImpl();
		return designerImagesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocumentationType createDocumentationType() {
		DocumentationTypeImpl documentationType = new DocumentationTypeImpl();
		return documentationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocumentRoot createDocumentRoot() {
		DocumentRootImpl documentRoot = new DocumentRootImpl();
		return documentRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumElementType createEnumElementType() {
		EnumElementTypeImpl enumElementType = new EnumElementTypeImpl();
		return enumElementType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumPropertyDeclarationType createEnumPropertyDeclarationType() {
		EnumPropertyDeclarationTypeImpl enumPropertyDeclarationType = new EnumPropertyDeclarationTypeImpl();
		return enumPropertyDeclarationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumPropertyType createEnumPropertyType() {
		EnumPropertyTypeImpl enumPropertyType = new EnumPropertyTypeImpl();
		return enumPropertyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventsType createEventsType() {
		EventsTypeImpl eventsType = new EventsTypeImpl();
		return eventsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventType createEventType() {
		EventTypeImpl eventType = new EventTypeImpl();
		return eventType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExpandArgumentType createExpandArgumentType() {
		ExpandArgumentTypeImpl expandArgumentType = new ExpandArgumentTypeImpl();
		return expandArgumentType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExpandMacroType createExpandMacroType() {
		ExpandMacroTypeImpl expandMacroType = new ExpandMacroTypeImpl();
		return expandMacroType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExtensionPropertiesType createExtensionPropertiesType() {
		ExtensionPropertiesTypeImpl extensionPropertiesType = new ExtensionPropertiesTypeImpl();
		return extensionPropertiesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImplementationsType createImplementationsType() {
		ImplementationsTypeImpl implementationsType = new ImplementationsTypeImpl();
		return implementationsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImplementationType createImplementationType() {
		ImplementationTypeImpl implementationType = new ImplementationTypeImpl();
		return implementationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImportArgumentsType createImportArgumentsType() {
		ImportArgumentsTypeImpl importArgumentsType = new ImportArgumentsTypeImpl();
		return importArgumentsType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public InlineType createInlineType() {
		InlineTypeImpl inlineType = new InlineTypeImpl();
		return inlineType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InterfaceType createInterfaceType() {
		InterfaceTypeImpl interfaceType = new InterfaceTypeImpl();
		return interfaceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MacroArgumentType createMacroArgumentType() {
		MacroArgumentTypeImpl macroArgumentType = new MacroArgumentTypeImpl();
		return macroArgumentType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapArrayMemberType createMapArrayMemberType() {
		MapArrayMemberTypeImpl mapArrayMemberType = new MapArrayMemberTypeImpl();
		return mapArrayMemberType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MapArrayTypeType createMapArrayTypeType() {
		MapArrayTypeTypeImpl mapArrayTypeType = new MapArrayTypeTypeImpl();
		return mapArrayTypeType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapBitmaskElementType createMapBitmaskElementType() {
		MapBitmaskElementTypeImpl mapBitmaskElementType = new MapBitmaskElementTypeImpl();
		return mapBitmaskElementType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapBitmaskMemberType createMapBitmaskMemberType() {
		MapBitmaskMemberTypeImpl mapBitmaskMemberType = new MapBitmaskMemberTypeImpl();
		return mapBitmaskMemberType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MapBitmaskTypeType createMapBitmaskTypeType() {
		MapBitmaskTypeTypeImpl mapBitmaskTypeType = new MapBitmaskTypeTypeImpl();
		return mapBitmaskTypeType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapBitmaskValueType createMapBitmaskValueType() {
		MapBitmaskValueTypeImpl mapBitmaskValueType = new MapBitmaskValueTypeImpl();
		return mapBitmaskValueType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MapElementFromTypeType createMapElementFromTypeType() {
		MapElementFromTypeTypeImpl mapElementFromTypeType = new MapElementFromTypeTypeImpl();
		return mapElementFromTypeType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapEnumElementType createMapEnumElementType() {
		MapEnumElementTypeImpl mapEnumElementType = new MapEnumElementTypeImpl();
		return mapEnumElementType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapEnumMemberType createMapEnumMemberType() {
		MapEnumMemberTypeImpl mapEnumMemberType = new MapEnumMemberTypeImpl();
		return mapEnumMemberType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapEnumType createMapEnumType() {
		MapEnumTypeImpl mapEnumType = new MapEnumTypeImpl();
		return mapEnumType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MapEnumTypeType createMapEnumTypeType() {
		MapEnumTypeTypeImpl mapEnumTypeType = new MapEnumTypeTypeImpl();
		return mapEnumTypeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MapFixedElementType createMapFixedElementType() {
		MapFixedElementTypeImpl mapFixedElementType = new MapFixedElementTypeImpl();
		return mapFixedElementType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapFixedMemberType createMapFixedMemberType() {
		MapFixedMemberTypeImpl mapFixedMemberType = new MapFixedMemberTypeImpl();
		return mapFixedMemberType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MapFixedTypeType createMapFixedTypeType() {
		MapFixedTypeTypeImpl mapFixedTypeType = new MapFixedTypeTypeImpl();
		return mapFixedTypeType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapIdentifierElementType createMapIdentifierElementType() {
		MapIdentifierElementTypeImpl mapIdentifierElementType = new MapIdentifierElementTypeImpl();
		return mapIdentifierElementType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapIdentifierMemberType createMapIdentifierMemberType() {
		MapIdentifierMemberTypeImpl mapIdentifierMemberType = new MapIdentifierMemberTypeImpl();
		return mapIdentifierMemberType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MapIdentifierTypeType createMapIdentifierTypeType() {
		MapIdentifierTypeTypeImpl mapIdentifierTypeType = new MapIdentifierTypeTypeImpl();
		return mapIdentifierTypeType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapInstanceElementType createMapInstanceElementType() {
		MapInstanceElementTypeImpl mapInstanceElementType = new MapInstanceElementTypeImpl();
		return mapInstanceElementType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapInstanceMemberType createMapInstanceMemberType() {
		MapInstanceMemberTypeImpl mapInstanceMemberType = new MapInstanceMemberTypeImpl();
		return mapInstanceMemberType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MapInstanceTypeType createMapInstanceTypeType() {
		MapInstanceTypeTypeImpl mapInstanceTypeType = new MapInstanceTypeTypeImpl();
		return mapInstanceTypeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MapIntoPropertyType createMapIntoPropertyType() {
		MapIntoPropertyTypeImpl mapIntoPropertyType = new MapIntoPropertyTypeImpl();
		return mapIntoPropertyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MapMemberFromTypeType createMapMemberFromTypeType() {
		MapMemberFromTypeTypeImpl mapMemberFromTypeType = new MapMemberFromTypeTypeImpl();
		return mapMemberFromTypeType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MappingArrayType createMappingArrayType() {
		MappingArrayTypeImpl mappingArrayType = new MappingArrayTypeImpl();
		return mappingArrayType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MappingBitmaskType createMappingBitmaskType() {
		MappingBitmaskTypeImpl mappingBitmaskType = new MappingBitmaskTypeImpl();
		return mappingBitmaskType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MappingEnumType createMappingEnumType() {
		MappingEnumTypeImpl mappingEnumType = new MappingEnumTypeImpl();
		return mappingEnumType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MappingFixedType createMappingFixedType() {
		MappingFixedTypeImpl mappingFixedType = new MappingFixedTypeImpl();
		return mappingFixedType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MappingIdentifierType createMappingIdentifierType() {
		MappingIdentifierTypeImpl mappingIdentifierType = new MappingIdentifierTypeImpl();
		return mappingIdentifierType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MappingInstanceType createMappingInstanceType() {
		MappingInstanceTypeImpl mappingInstanceType = new MappingInstanceTypeImpl();
		return mappingInstanceType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MappingReferenceType createMappingReferenceType() {
		MappingReferenceTypeImpl mappingReferenceType = new MappingReferenceTypeImpl();
		return mappingReferenceType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MappingResourceType createMappingResourceType() {
		MappingResourceTypeImpl mappingResourceType = new MappingResourceTypeImpl();
		return mappingResourceType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MappingSimpleType createMappingSimpleType() {
		MappingSimpleTypeImpl mappingSimpleType = new MappingSimpleTypeImpl();
		return mappingSimpleType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapReferenceElementType createMapReferenceElementType() {
		MapReferenceElementTypeImpl mapReferenceElementType = new MapReferenceElementTypeImpl();
		return mapReferenceElementType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapReferenceMemberType createMapReferenceMemberType() {
		MapReferenceMemberTypeImpl mapReferenceMemberType = new MapReferenceMemberTypeImpl();
		return mapReferenceMemberType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MapReferenceTypeType createMapReferenceTypeType() {
		MapReferenceTypeTypeImpl mapReferenceTypeType = new MapReferenceTypeTypeImpl();
		return mapReferenceTypeType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapResourceElementType createMapResourceElementType() {
		MapResourceElementTypeImpl mapResourceElementType = new MapResourceElementTypeImpl();
		return mapResourceElementType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapResourceMemberType createMapResourceMemberType() {
		MapResourceMemberTypeImpl mapResourceMemberType = new MapResourceMemberTypeImpl();
		return mapResourceMemberType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapResourceType createMapResourceType() {
		MapResourceTypeImpl mapResourceType = new MapResourceTypeImpl();
		return mapResourceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MapResourceTypeType createMapResourceTypeType() {
		MapResourceTypeTypeImpl mapResourceTypeType = new MapResourceTypeTypeImpl();
		return mapResourceTypeType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapSimpleElementType createMapSimpleElementType() {
		MapSimpleElementTypeImpl mapSimpleElementType = new MapSimpleElementTypeImpl();
		return mapSimpleElementType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapSimpleMemberType createMapSimpleMemberType() {
		MapSimpleMemberTypeImpl mapSimpleMemberType = new MapSimpleMemberTypeImpl();
		return mapSimpleMemberType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MapSimpleTypeType createMapSimpleTypeType() {
		MapSimpleTypeTypeImpl mapSimpleTypeType = new MapSimpleTypeTypeImpl();
		return mapSimpleTypeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertiesType createPropertiesType() {
		PropertiesTypeImpl propertiesType = new PropertiesTypeImpl();
		return propertiesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyOverridesType createPropertyOverridesType() {
		PropertyOverridesTypeImpl propertyOverridesType = new PropertyOverridesTypeImpl();
		return propertyOverridesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyOverrideType createPropertyOverrideType() {
		PropertyOverrideTypeImpl propertyOverrideType = new PropertyOverrideTypeImpl();
		return propertyOverrideType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScriptType createScriptType() {
		ScriptTypeImpl scriptType = new ScriptTypeImpl();
		return scriptType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public SelectType createSelectType() {
		SelectTypeImpl selectType = new SelectTypeImpl();
		return selectType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimplePropertyType createSimplePropertyType() {
		SimplePropertyTypeImpl simplePropertyType = new SimplePropertyTypeImpl();
		return simplePropertyType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public SourceGenType createSourceGenType() {
		SourceGenTypeImpl sourceGenType = new SourceGenTypeImpl();
		return sourceGenType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public SourceMappingType createSourceMappingType() {
		SourceMappingTypeImpl sourceMappingType = new SourceMappingTypeImpl();
		return sourceMappingType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SourceTypeMappingType createSourceTypeMappingType() {
		SourceTypeMappingTypeImpl sourceTypeMappingType = new SourceTypeMappingTypeImpl();
		return sourceTypeMappingType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymbianType createSymbianType() {
		SymbianTypeImpl symbianType = new SymbianTypeImpl();
		return symbianType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public TemplateGroupType createTemplateGroupType() {
		TemplateGroupTypeImpl templateGroupType = new TemplateGroupTypeImpl();
		return templateGroupType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public TemplateType createTemplateType() {
		TemplateTypeImpl templateType = new TemplateTypeImpl();
		return templateType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public TwoWayMappingType createTwoWayMappingType() {
		TwoWayMappingTypeImpl twoWayMappingType = new TwoWayMappingTypeImpl();
		return twoWayMappingType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public UseTemplateGroupType createUseTemplateGroupType() {
		UseTemplateGroupTypeImpl useTemplateGroupType = new UseTemplateGroupTypeImpl();
		return useTemplateGroupType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public UseTemplateType createUseTemplateType() {
		UseTemplateTypeImpl useTemplateType = new UseTemplateTypeImpl();
		return useTemplateType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyDataType createPropertyDataTypeFromString(EDataType eDataType, String initialValue) {
		PropertyDataType result = PropertyDataType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPropertyDataTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReferenceScopeType createReferenceScopeTypeFromString(EDataType eDataType, String initialValue) {
		ReferenceScopeType result = ReferenceScopeType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertReferenceScopeTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StandaloneType createStandaloneTypeFromString(EDataType eDataType, String initialValue) {
		StandaloneType result = StandaloneType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertStandaloneTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List createListOfStringsFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null) return null;
		List result = new ArrayList();
		for (StringTokenizer stringTokenizer = new StringTokenizer(initialValue); stringTokenizer.hasMoreTokens(); ) {
			String item = stringTokenizer.nextToken();
			result.add((String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, item));
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertListOfStringsToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null) return null;
		List list = (List)instanceValue;
		if (list.isEmpty()) return "";
		StringBuffer result = new StringBuffer();
		for (Iterator i = list.iterator(); i.hasNext(); ) {
			result.append(XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, i.next()));
			result.append(' ');
		}
		return result.substring(0, result.length() - 1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyDataType createPropertyDataTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createPropertyDataTypeFromString(ComponentPackage.Literals.PROPERTY_DATA_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPropertyDataTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertPropertyDataTypeToString(ComponentPackage.Literals.PROPERTY_DATA_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReferenceScopeType createReferenceScopeTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createReferenceScopeTypeFromString(ComponentPackage.Literals.REFERENCE_SCOPE_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertReferenceScopeTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertReferenceScopeTypeToString(ComponentPackage.Literals.REFERENCE_SCOPE_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StandaloneType createStandaloneTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createStandaloneTypeFromString(ComponentPackage.Literals.STANDALONE_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertStandaloneTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertStandaloneTypeToString(ComponentPackage.Literals.STANDALONE_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentPackage getComponentPackage() {
		return (ComponentPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	public static ComponentPackage getPackage() {
		return ComponentPackage.eINSTANCE;
	}

} //ComponentFactoryImpl
