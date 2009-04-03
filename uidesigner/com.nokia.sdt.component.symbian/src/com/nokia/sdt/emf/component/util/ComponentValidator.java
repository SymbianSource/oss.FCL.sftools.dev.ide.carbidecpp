/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.util;

import com.nokia.sdt.emf.component.*;
import com.nokia.sdt.emf.component.AbstractPropertyType;
import com.nokia.sdt.emf.component.ArrayPropertyType;
import com.nokia.sdt.emf.component.AttributeType;
import com.nokia.sdt.emf.component.AttributesType;
import com.nokia.sdt.emf.component.ChoiceType;
import com.nokia.sdt.emf.component.CodeType;
import com.nokia.sdt.emf.component.ComponentDefinitionType;
import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.ComponentReferencePropertyType;
import com.nokia.sdt.emf.component.ComponentType;
import com.nokia.sdt.emf.component.CompoundPropertyDeclarationType;
import com.nokia.sdt.emf.component.CompoundPropertyType;
import com.nokia.sdt.emf.component.ConditionalSourceGen;
import com.nokia.sdt.emf.component.ConditionalSourceGenString;
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

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.ecore.xml.type.util.XMLTypeValidator;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see com.nokia.sdt.emf.component.ComponentPackage
 * @generated
 */
public class ComponentValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final ComponentValidator INSTANCE = new ComponentValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "com.nokia.sdt.emf.component";

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * The cached base package validator.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XMLTypeValidator xmlTypeValidator;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentValidator() {
		super();
		xmlTypeValidator = XMLTypeValidator.INSTANCE;
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EPackage getEPackage() {
	  return ComponentPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresonding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map context) {
		switch (classifierID) {
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE:
				return validateAbstractPropertyType((AbstractPropertyType)value, diagnostics, context);
			case ComponentPackage.ARRAY_PROPERTY_TYPE:
				return validateArrayPropertyType((ArrayPropertyType)value, diagnostics, context);
			case ComponentPackage.ATTRIBUTES_TYPE:
				return validateAttributesType((AttributesType)value, diagnostics, context);
			case ComponentPackage.ATTRIBUTE_TYPE:
				return validateAttributeType((AttributeType)value, diagnostics, context);
			case ComponentPackage.CHOICE_TYPE:
				return validateChoiceType((ChoiceType)value, diagnostics, context);
			case ComponentPackage.CODE_TYPE:
				return validateCodeType((CodeType)value, diagnostics, context);
			case ComponentPackage.COMPONENT_DEFINITION_TYPE:
				return validateComponentDefinitionType((ComponentDefinitionType)value, diagnostics, context);
			case ComponentPackage.COMPONENT_REFERENCE_PROPERTY_TYPE:
				return validateComponentReferencePropertyType((ComponentReferencePropertyType)value, diagnostics, context);
			case ComponentPackage.COMPONENT_TYPE:
				return validateComponentType((ComponentType)value, diagnostics, context);
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE:
				return validateCompoundPropertyDeclarationType((CompoundPropertyDeclarationType)value, diagnostics, context);
			case ComponentPackage.COMPOUND_PROPERTY_TYPE:
				return validateCompoundPropertyType((CompoundPropertyType)value, diagnostics, context);
			case ComponentPackage.CONDITIONAL_SOURCE_GEN:
				return validateConditionalSourceGen((ConditionalSourceGen)value, diagnostics, context);
			case ComponentPackage.CONDITIONAL_SOURCE_GEN_STRING:
				return validateConditionalSourceGenString((ConditionalSourceGenString)value, diagnostics, context);
			case ComponentPackage.DEFINE_LOCATION_TYPE:
				return validateDefineLocationType((DefineLocationType)value, diagnostics, context);
			case ComponentPackage.DEFINE_MACRO_TYPE:
				return validateDefineMacroType((DefineMacroType)value, diagnostics, context);
			case ComponentPackage.DESIGNER_IMAGES_TYPE:
				return validateDesignerImagesType((DesignerImagesType)value, diagnostics, context);
			case ComponentPackage.DOCUMENTATION_TYPE:
				return validateDocumentationType((DocumentationType)value, diagnostics, context);
			case ComponentPackage.DOCUMENT_ROOT:
				return validateDocumentRoot((DocumentRoot)value, diagnostics, context);
			case ComponentPackage.ENUM_ELEMENT_TYPE:
				return validateEnumElementType((EnumElementType)value, diagnostics, context);
			case ComponentPackage.ENUM_PROPERTY_DECLARATION_TYPE:
				return validateEnumPropertyDeclarationType((EnumPropertyDeclarationType)value, diagnostics, context);
			case ComponentPackage.ENUM_PROPERTY_TYPE:
				return validateEnumPropertyType((EnumPropertyType)value, diagnostics, context);
			case ComponentPackage.EVENTS_TYPE:
				return validateEventsType((EventsType)value, diagnostics, context);
			case ComponentPackage.EVENT_TYPE:
				return validateEventType((EventType)value, diagnostics, context);
			case ComponentPackage.EXPAND_ARGUMENT_TYPE:
				return validateExpandArgumentType((ExpandArgumentType)value, diagnostics, context);
			case ComponentPackage.EXPAND_MACRO_TYPE:
				return validateExpandMacroType((ExpandMacroType)value, diagnostics, context);
			case ComponentPackage.EXTENSION_PROPERTIES_TYPE:
				return validateExtensionPropertiesType((ExtensionPropertiesType)value, diagnostics, context);
			case ComponentPackage.IMPLEMENTATIONS_TYPE:
				return validateImplementationsType((ImplementationsType)value, diagnostics, context);
			case ComponentPackage.IMPLEMENTATION_TYPE:
				return validateImplementationType((ImplementationType)value, diagnostics, context);
			case ComponentPackage.IMPORT_ARGUMENTS_TYPE:
				return validateImportArgumentsType((ImportArgumentsType)value, diagnostics, context);
			case ComponentPackage.INLINE_TYPE:
				return validateInlineType((InlineType)value, diagnostics, context);
			case ComponentPackage.INTERFACE_TYPE:
				return validateInterfaceType((InterfaceType)value, diagnostics, context);
			case ComponentPackage.MACRO_ARGUMENT_TYPE:
				return validateMacroArgumentType((MacroArgumentType)value, diagnostics, context);
			case ComponentPackage.MAP_ARRAY_MEMBER_TYPE:
				return validateMapArrayMemberType((MapArrayMemberType)value, diagnostics, context);
			case ComponentPackage.MAP_ARRAY_TYPE_TYPE:
				return validateMapArrayTypeType((MapArrayTypeType)value, diagnostics, context);
			case ComponentPackage.MAP_BITMASK_ELEMENT_TYPE:
				return validateMapBitmaskElementType((MapBitmaskElementType)value, diagnostics, context);
			case ComponentPackage.MAP_BITMASK_MEMBER_TYPE:
				return validateMapBitmaskMemberType((MapBitmaskMemberType)value, diagnostics, context);
			case ComponentPackage.MAP_BITMASK_TYPE_TYPE:
				return validateMapBitmaskTypeType((MapBitmaskTypeType)value, diagnostics, context);
			case ComponentPackage.MAP_BITMASK_VALUE_TYPE:
				return validateMapBitmaskValueType((MapBitmaskValueType)value, diagnostics, context);
			case ComponentPackage.MAP_ELEMENT_FROM_TYPE_TYPE:
				return validateMapElementFromTypeType((MapElementFromTypeType)value, diagnostics, context);
			case ComponentPackage.MAP_ENUM_ELEMENT_TYPE:
				return validateMapEnumElementType((MapEnumElementType)value, diagnostics, context);
			case ComponentPackage.MAP_ENUM_MEMBER_TYPE:
				return validateMapEnumMemberType((MapEnumMemberType)value, diagnostics, context);
			case ComponentPackage.MAP_ENUM_TYPE:
				return validateMapEnumType((MapEnumType)value, diagnostics, context);
			case ComponentPackage.MAP_ENUM_TYPE_TYPE:
				return validateMapEnumTypeType((MapEnumTypeType)value, diagnostics, context);
			case ComponentPackage.MAP_FIXED_ELEMENT_TYPE:
				return validateMapFixedElementType((MapFixedElementType)value, diagnostics, context);
			case ComponentPackage.MAP_FIXED_MEMBER_TYPE:
				return validateMapFixedMemberType((MapFixedMemberType)value, diagnostics, context);
			case ComponentPackage.MAP_FIXED_TYPE_TYPE:
				return validateMapFixedTypeType((MapFixedTypeType)value, diagnostics, context);
			case ComponentPackage.MAP_IDENTIFIER_ELEMENT_TYPE:
				return validateMapIdentifierElementType((MapIdentifierElementType)value, diagnostics, context);
			case ComponentPackage.MAP_IDENTIFIER_MEMBER_TYPE:
				return validateMapIdentifierMemberType((MapIdentifierMemberType)value, diagnostics, context);
			case ComponentPackage.MAP_IDENTIFIER_TYPE_TYPE:
				return validateMapIdentifierTypeType((MapIdentifierTypeType)value, diagnostics, context);
			case ComponentPackage.MAP_INSTANCE_ELEMENT_TYPE:
				return validateMapInstanceElementType((MapInstanceElementType)value, diagnostics, context);
			case ComponentPackage.MAP_INSTANCE_MEMBER_TYPE:
				return validateMapInstanceMemberType((MapInstanceMemberType)value, diagnostics, context);
			case ComponentPackage.MAP_INSTANCE_TYPE_TYPE:
				return validateMapInstanceTypeType((MapInstanceTypeType)value, diagnostics, context);
			case ComponentPackage.MAP_INTO_PROPERTY_TYPE:
				return validateMapIntoPropertyType((MapIntoPropertyType)value, diagnostics, context);
			case ComponentPackage.MAP_MEMBER_FROM_TYPE_TYPE:
				return validateMapMemberFromTypeType((MapMemberFromTypeType)value, diagnostics, context);
			case ComponentPackage.MAPPING_ARRAY_TYPE:
				return validateMappingArrayType((MappingArrayType)value, diagnostics, context);
			case ComponentPackage.MAPPING_BITMASK_TYPE:
				return validateMappingBitmaskType((MappingBitmaskType)value, diagnostics, context);
			case ComponentPackage.MAPPING_ENUM_TYPE:
				return validateMappingEnumType((MappingEnumType)value, diagnostics, context);
			case ComponentPackage.MAPPING_FIXED_TYPE:
				return validateMappingFixedType((MappingFixedType)value, diagnostics, context);
			case ComponentPackage.MAPPING_IDENTIFIER_TYPE:
				return validateMappingIdentifierType((MappingIdentifierType)value, diagnostics, context);
			case ComponentPackage.MAPPING_INSTANCE_TYPE:
				return validateMappingInstanceType((MappingInstanceType)value, diagnostics, context);
			case ComponentPackage.MAPPING_REFERENCE_TYPE:
				return validateMappingReferenceType((MappingReferenceType)value, diagnostics, context);
			case ComponentPackage.MAPPING_RESOURCE_TYPE:
				return validateMappingResourceType((MappingResourceType)value, diagnostics, context);
			case ComponentPackage.MAPPING_SIMPLE_TYPE:
				return validateMappingSimpleType((MappingSimpleType)value, diagnostics, context);
			case ComponentPackage.MAP_REFERENCE_ELEMENT_TYPE:
				return validateMapReferenceElementType((MapReferenceElementType)value, diagnostics, context);
			case ComponentPackage.MAP_REFERENCE_MEMBER_TYPE:
				return validateMapReferenceMemberType((MapReferenceMemberType)value, diagnostics, context);
			case ComponentPackage.MAP_REFERENCE_TYPE_TYPE:
				return validateMapReferenceTypeType((MapReferenceTypeType)value, diagnostics, context);
			case ComponentPackage.MAP_RESOURCE_ELEMENT_TYPE:
				return validateMapResourceElementType((MapResourceElementType)value, diagnostics, context);
			case ComponentPackage.MAP_RESOURCE_MEMBER_TYPE:
				return validateMapResourceMemberType((MapResourceMemberType)value, diagnostics, context);
			case ComponentPackage.MAP_RESOURCE_TYPE:
				return validateMapResourceType((MapResourceType)value, diagnostics, context);
			case ComponentPackage.MAP_RESOURCE_TYPE_TYPE:
				return validateMapResourceTypeType((MapResourceTypeType)value, diagnostics, context);
			case ComponentPackage.MAP_SIMPLE_ELEMENT_TYPE:
				return validateMapSimpleElementType((MapSimpleElementType)value, diagnostics, context);
			case ComponentPackage.MAP_SIMPLE_MEMBER_TYPE:
				return validateMapSimpleMemberType((MapSimpleMemberType)value, diagnostics, context);
			case ComponentPackage.MAP_SIMPLE_TYPE_TYPE:
				return validateMapSimpleTypeType((MapSimpleTypeType)value, diagnostics, context);
			case ComponentPackage.PROPERTIES_TYPE:
				return validatePropertiesType((PropertiesType)value, diagnostics, context);
			case ComponentPackage.PROPERTY_OVERRIDES_TYPE:
				return validatePropertyOverridesType((PropertyOverridesType)value, diagnostics, context);
			case ComponentPackage.PROPERTY_OVERRIDE_TYPE:
				return validatePropertyOverrideType((PropertyOverrideType)value, diagnostics, context);
			case ComponentPackage.SCRIPT_TYPE:
				return validateScriptType((ScriptType)value, diagnostics, context);
			case ComponentPackage.SELECT_TYPE:
				return validateSelectType((SelectType)value, diagnostics, context);
			case ComponentPackage.SIMPLE_PROPERTY_TYPE:
				return validateSimplePropertyType((SimplePropertyType)value, diagnostics, context);
			case ComponentPackage.SOURCE_GEN_TYPE:
				return validateSourceGenType((SourceGenType)value, diagnostics, context);
			case ComponentPackage.SOURCE_MAPPING_TYPE:
				return validateSourceMappingType((SourceMappingType)value, diagnostics, context);
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE:
				return validateSourceTypeMappingType((SourceTypeMappingType)value, diagnostics, context);
			case ComponentPackage.SYMBIAN_TYPE:
				return validateSymbianType((SymbianType)value, diagnostics, context);
			case ComponentPackage.TEMPLATE_GROUP_TYPE:
				return validateTemplateGroupType((TemplateGroupType)value, diagnostics, context);
			case ComponentPackage.TEMPLATE_TYPE:
				return validateTemplateType((TemplateType)value, diagnostics, context);
			case ComponentPackage.TWO_WAY_MAPPING_TYPE:
				return validateTwoWayMappingType((TwoWayMappingType)value, diagnostics, context);
			case ComponentPackage.USE_TEMPLATE_GROUP_TYPE:
				return validateUseTemplateGroupType((UseTemplateGroupType)value, diagnostics, context);
			case ComponentPackage.USE_TEMPLATE_TYPE:
				return validateUseTemplateType((UseTemplateType)value, diagnostics, context);
			case ComponentPackage.PROPERTY_DATA_TYPE:
				return validatePropertyDataType((PropertyDataType)value, diagnostics, context);
			case ComponentPackage.REFERENCE_SCOPE_TYPE:
				return validateReferenceScopeType((ReferenceScopeType)value, diagnostics, context);
			case ComponentPackage.STANDALONE_TYPE:
				return validateStandaloneType((StandaloneType)value, diagnostics, context);
			case ComponentPackage.LIST_OF_STRINGS:
				return validateListOfStrings((List)value, diagnostics, context);
			case ComponentPackage.PROPERTY_DATA_TYPE_OBJECT:
				return validatePropertyDataTypeObject((PropertyDataType)value, diagnostics, context);
			case ComponentPackage.REFERENCE_SCOPE_TYPE_OBJECT:
				return validateReferenceScopeTypeObject((ReferenceScopeType)value, diagnostics, context);
			case ComponentPackage.STANDALONE_TYPE_OBJECT:
				return validateStandaloneTypeObject((StandaloneType)value, diagnostics, context);
			default: 
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAbstractPropertyType(AbstractPropertyType abstractPropertyType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(abstractPropertyType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateArrayPropertyType(ArrayPropertyType arrayPropertyType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(arrayPropertyType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAttributesType(AttributesType attributesType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(attributesType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAttributeType(AttributeType attributeType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(attributeType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean validateChoiceType(ChoiceType choiceType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(choiceType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCodeType(CodeType codeType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(codeType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateComponentDefinitionType(ComponentDefinitionType componentDefinitionType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(componentDefinitionType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateComponentReferencePropertyType(ComponentReferencePropertyType componentReferencePropertyType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(componentReferencePropertyType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateComponentType(ComponentType componentType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(componentType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCompoundPropertyDeclarationType(CompoundPropertyDeclarationType compoundPropertyDeclarationType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(compoundPropertyDeclarationType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCompoundPropertyType(CompoundPropertyType compoundPropertyType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(compoundPropertyType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateConditionalSourceGen(ConditionalSourceGen conditionalSourceGen, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(conditionalSourceGen, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateConditionalSourceGenString(ConditionalSourceGenString conditionalSourceGenString, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(conditionalSourceGenString, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean validateDefineLocationType(DefineLocationType defineLocationType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(defineLocationType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDefineMacroType(DefineMacroType defineMacroType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(defineMacroType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDesignerImagesType(DesignerImagesType designerImagesType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(designerImagesType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDocumentationType(DocumentationType documentationType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(documentationType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDocumentRoot(DocumentRoot documentRoot, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(documentRoot, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEnumElementType(EnumElementType enumElementType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(enumElementType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEnumPropertyDeclarationType(EnumPropertyDeclarationType enumPropertyDeclarationType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(enumPropertyDeclarationType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEnumPropertyType(EnumPropertyType enumPropertyType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(enumPropertyType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEventsType(EventsType eventsType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(eventsType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEventType(EventType eventType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(eventType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateExpandArgumentType(ExpandArgumentType expandArgumentType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(expandArgumentType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateExpandMacroType(ExpandMacroType expandMacroType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(expandMacroType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateExtensionPropertiesType(ExtensionPropertiesType extensionPropertiesType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(extensionPropertiesType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateImplementationsType(ImplementationsType implementationsType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(implementationsType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateImplementationType(ImplementationType implementationType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(implementationType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateImportArgumentsType(ImportArgumentsType importArgumentsType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(importArgumentsType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInlineType(InlineType inlineType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(inlineType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInterfaceType(InterfaceType interfaceType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(interfaceType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMacroArgumentType(MacroArgumentType macroArgumentType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(macroArgumentType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMapArrayMemberType(MapArrayMemberType mapArrayMemberType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapArrayMemberType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMapArrayTypeType(MapArrayTypeType mapArrayTypeType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapArrayTypeType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean validateMapBitmaskElementType(MapBitmaskElementType mapBitmaskElementType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapBitmaskElementType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean validateMapBitmaskMemberType(MapBitmaskMemberType mapBitmaskMemberType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapBitmaskMemberType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMapBitmaskTypeType(MapBitmaskTypeType mapBitmaskTypeType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapBitmaskTypeType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean validateMapBitmaskValueType(MapBitmaskValueType mapBitmaskValueType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapBitmaskValueType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMapElementFromTypeType(MapElementFromTypeType mapElementFromTypeType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapElementFromTypeType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMapEnumElementType(MapEnumElementType mapEnumElementType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapEnumElementType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMapEnumMemberType(MapEnumMemberType mapEnumMemberType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapEnumMemberType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMapEnumType(MapEnumType mapEnumType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapEnumType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMapEnumTypeType(MapEnumTypeType mapEnumTypeType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapEnumTypeType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMapFixedElementType(MapFixedElementType mapFixedElementType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapFixedElementType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean validateMapFixedMemberType(MapFixedMemberType mapFixedMemberType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapFixedMemberType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMapFixedTypeType(MapFixedTypeType mapFixedTypeType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapFixedTypeType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean validateMapIdentifierElementType(MapIdentifierElementType mapIdentifierElementType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapIdentifierElementType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean validateMapIdentifierMemberType(MapIdentifierMemberType mapIdentifierMemberType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapIdentifierMemberType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMapIdentifierTypeType(MapIdentifierTypeType mapIdentifierTypeType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapIdentifierTypeType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean validateMapInstanceElementType(MapInstanceElementType mapInstanceElementType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapInstanceElementType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean validateMapInstanceMemberType(MapInstanceMemberType mapInstanceMemberType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapInstanceMemberType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMapInstanceTypeType(MapInstanceTypeType mapInstanceTypeType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapInstanceTypeType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMapIntoPropertyType(MapIntoPropertyType mapIntoPropertyType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapIntoPropertyType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMapMemberFromTypeType(MapMemberFromTypeType mapMemberFromTypeType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapMemberFromTypeType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMappingArrayType(MappingArrayType mappingArrayType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mappingArrayType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean validateMappingBitmaskType(MappingBitmaskType mappingBitmaskType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mappingBitmaskType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMappingEnumType(MappingEnumType mappingEnumType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mappingEnumType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean validateMappingFixedType(MappingFixedType mappingFixedType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mappingFixedType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean validateMappingIdentifierType(MappingIdentifierType mappingIdentifierType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mappingIdentifierType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean validateMappingInstanceType(MappingInstanceType mappingInstanceType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mappingInstanceType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean validateMappingReferenceType(MappingReferenceType mappingReferenceType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mappingReferenceType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMappingResourceType(MappingResourceType mappingResourceType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mappingResourceType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMappingSimpleType(MappingSimpleType mappingSimpleType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mappingSimpleType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean validateMapReferenceElementType(MapReferenceElementType mapReferenceElementType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapReferenceElementType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean validateMapReferenceMemberType(MapReferenceMemberType mapReferenceMemberType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapReferenceMemberType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMapReferenceTypeType(MapReferenceTypeType mapReferenceTypeType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapReferenceTypeType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMapResourceElementType(MapResourceElementType mapResourceElementType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapResourceElementType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMapResourceMemberType(MapResourceMemberType mapResourceMemberType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapResourceMemberType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMapResourceType(MapResourceType mapResourceType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapResourceType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMapResourceTypeType(MapResourceTypeType mapResourceTypeType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapResourceTypeType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMapSimpleElementType(MapSimpleElementType mapSimpleElementType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapSimpleElementType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMapSimpleMemberType(MapSimpleMemberType mapSimpleMemberType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapSimpleMemberType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMapSimpleTypeType(MapSimpleTypeType mapSimpleTypeType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(mapSimpleTypeType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePropertiesType(PropertiesType propertiesType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(propertiesType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePropertyOverridesType(PropertyOverridesType propertyOverridesType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(propertyOverridesType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePropertyOverrideType(PropertyOverrideType propertyOverrideType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(propertyOverrideType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateScriptType(ScriptType scriptType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(scriptType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean validateSelectType(SelectType selectType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(selectType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSimplePropertyType(SimplePropertyType simplePropertyType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(simplePropertyType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSourceGenType(SourceGenType sourceGenType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(sourceGenType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSourceMappingType(SourceMappingType sourceMappingType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(sourceMappingType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSourceTypeMappingType(SourceTypeMappingType sourceTypeMappingType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(sourceTypeMappingType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSymbianType(SymbianType symbianType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(symbianType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTemplateGroupType(TemplateGroupType templateGroupType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(templateGroupType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTemplateType(TemplateType templateType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(templateType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTwoWayMappingType(TwoWayMappingType twoWayMappingType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(twoWayMappingType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateUseTemplateGroupType(UseTemplateGroupType useTemplateGroupType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(useTemplateGroupType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateUseTemplateType(UseTemplateType useTemplateType, DiagnosticChain diagnostics, Map context) {
		return validate_EveryDefaultConstraint(useTemplateType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePropertyDataType(PropertyDataType propertyDataType, DiagnosticChain diagnostics, Map context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateReferenceScopeType(ReferenceScopeType referenceScopeType, DiagnosticChain diagnostics, Map context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStandaloneType(StandaloneType standaloneType, DiagnosticChain diagnostics, Map context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateListOfStrings(List listOfStrings, DiagnosticChain diagnostics, Map context) {
		boolean result = validateListOfStrings_ItemType(listOfStrings, diagnostics, context);
		return result;
	}

	/**
	 * Validates the ItemType constraint of '<em>List Of Strings</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateListOfStrings_ItemType(List listOfStrings, DiagnosticChain diagnostics, Map context) {
		boolean result = true;
		for (Iterator i = listOfStrings.iterator(); i.hasNext() && (result || diagnostics != null); ) {
			Object item = i.next();
			if (XMLTypePackage.Literals.STRING.isInstance(item)) {
				result &= xmlTypeValidator.validateString((String)item, diagnostics, context);
			}
			else {
				result = false;
				reportDataValueTypeViolation(XMLTypePackage.Literals.STRING, item, diagnostics, context);
			}
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePropertyDataTypeObject(PropertyDataType propertyDataTypeObject, DiagnosticChain diagnostics, Map context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateReferenceScopeTypeObject(ReferenceScopeType referenceScopeTypeObject, DiagnosticChain diagnostics, Map context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStandaloneTypeObject(StandaloneType standaloneTypeObject, DiagnosticChain diagnostics, Map context) {
		return true;
	}

} //ComponentValidator
