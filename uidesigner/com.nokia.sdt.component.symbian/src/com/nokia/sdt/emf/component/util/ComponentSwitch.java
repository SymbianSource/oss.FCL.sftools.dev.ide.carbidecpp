/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.util;

import com.nokia.sdt.emf.component.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import java.util.List;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.nokia.sdt.emf.component.ComponentPackage
 * @generated
 */
public class ComponentSwitch {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ComponentPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentSwitch() {
		if (modelPackage == null) {
			modelPackage = ComponentPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public Object doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected Object doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch((EClass)eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected Object doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE: {
				AbstractPropertyType abstractPropertyType = (AbstractPropertyType)theEObject;
				Object result = caseAbstractPropertyType(abstractPropertyType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.ARRAY_PROPERTY_TYPE: {
				ArrayPropertyType arrayPropertyType = (ArrayPropertyType)theEObject;
				Object result = caseArrayPropertyType(arrayPropertyType);
				if (result == null) result = caseAbstractPropertyType(arrayPropertyType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.ATTRIBUTES_TYPE: {
				AttributesType attributesType = (AttributesType)theEObject;
				Object result = caseAttributesType(attributesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.ATTRIBUTE_TYPE: {
				AttributeType attributeType = (AttributeType)theEObject;
				Object result = caseAttributeType(attributeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.CHOICE_TYPE: {
				ChoiceType choiceType = (ChoiceType)theEObject;
				Object result = caseChoiceType(choiceType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.CODE_TYPE: {
				CodeType codeType = (CodeType)theEObject;
				Object result = caseCodeType(codeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.COMPONENT_DEFINITION_TYPE: {
				ComponentDefinitionType componentDefinitionType = (ComponentDefinitionType)theEObject;
				Object result = caseComponentDefinitionType(componentDefinitionType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.COMPONENT_REFERENCE_PROPERTY_TYPE: {
				ComponentReferencePropertyType componentReferencePropertyType = (ComponentReferencePropertyType)theEObject;
				Object result = caseComponentReferencePropertyType(componentReferencePropertyType);
				if (result == null) result = caseAbstractPropertyType(componentReferencePropertyType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.COMPONENT_TYPE: {
				ComponentType componentType = (ComponentType)theEObject;
				Object result = caseComponentType(componentType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.COMPOUND_PROPERTY_DECLARATION_TYPE: {
				CompoundPropertyDeclarationType compoundPropertyDeclarationType = (CompoundPropertyDeclarationType)theEObject;
				Object result = caseCompoundPropertyDeclarationType(compoundPropertyDeclarationType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.COMPOUND_PROPERTY_TYPE: {
				CompoundPropertyType compoundPropertyType = (CompoundPropertyType)theEObject;
				Object result = caseCompoundPropertyType(compoundPropertyType);
				if (result == null) result = caseAbstractPropertyType(compoundPropertyType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.CONDITIONAL_SOURCE_GEN: {
				ConditionalSourceGen conditionalSourceGen = (ConditionalSourceGen)theEObject;
				Object result = caseConditionalSourceGen(conditionalSourceGen);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.CONDITIONAL_SOURCE_GEN_STRING: {
				ConditionalSourceGenString conditionalSourceGenString = (ConditionalSourceGenString)theEObject;
				Object result = caseConditionalSourceGenString(conditionalSourceGenString);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.DEFINE_LOCATION_TYPE: {
				DefineLocationType defineLocationType = (DefineLocationType)theEObject;
				Object result = caseDefineLocationType(defineLocationType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.DEFINE_MACRO_TYPE: {
				DefineMacroType defineMacroType = (DefineMacroType)theEObject;
				Object result = caseDefineMacroType(defineMacroType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.DESIGNER_IMAGES_TYPE: {
				DesignerImagesType designerImagesType = (DesignerImagesType)theEObject;
				Object result = caseDesignerImagesType(designerImagesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.DOCUMENTATION_TYPE: {
				DocumentationType documentationType = (DocumentationType)theEObject;
				Object result = caseDocumentationType(documentationType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.DOCUMENT_ROOT: {
				DocumentRoot documentRoot = (DocumentRoot)theEObject;
				Object result = caseDocumentRoot(documentRoot);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.ENUM_ELEMENT_TYPE: {
				EnumElementType enumElementType = (EnumElementType)theEObject;
				Object result = caseEnumElementType(enumElementType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.ENUM_PROPERTY_DECLARATION_TYPE: {
				EnumPropertyDeclarationType enumPropertyDeclarationType = (EnumPropertyDeclarationType)theEObject;
				Object result = caseEnumPropertyDeclarationType(enumPropertyDeclarationType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.ENUM_PROPERTY_TYPE: {
				EnumPropertyType enumPropertyType = (EnumPropertyType)theEObject;
				Object result = caseEnumPropertyType(enumPropertyType);
				if (result == null) result = caseAbstractPropertyType(enumPropertyType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.EVENTS_TYPE: {
				EventsType eventsType = (EventsType)theEObject;
				Object result = caseEventsType(eventsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.EVENT_TYPE: {
				EventType eventType = (EventType)theEObject;
				Object result = caseEventType(eventType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.EXPAND_ARGUMENT_TYPE: {
				ExpandArgumentType expandArgumentType = (ExpandArgumentType)theEObject;
				Object result = caseExpandArgumentType(expandArgumentType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.EXPAND_MACRO_TYPE: {
				ExpandMacroType expandMacroType = (ExpandMacroType)theEObject;
				Object result = caseExpandMacroType(expandMacroType);
				if (result == null) result = caseConditionalSourceGen(expandMacroType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.EXTENSION_PROPERTIES_TYPE: {
				ExtensionPropertiesType extensionPropertiesType = (ExtensionPropertiesType)theEObject;
				Object result = caseExtensionPropertiesType(extensionPropertiesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.IMPLEMENTATIONS_TYPE: {
				ImplementationsType implementationsType = (ImplementationsType)theEObject;
				Object result = caseImplementationsType(implementationsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.IMPLEMENTATION_TYPE: {
				ImplementationType implementationType = (ImplementationType)theEObject;
				Object result = caseImplementationType(implementationType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.IMPORT_ARGUMENTS_TYPE: {
				ImportArgumentsType importArgumentsType = (ImportArgumentsType)theEObject;
				Object result = caseImportArgumentsType(importArgumentsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.INLINE_TYPE: {
				InlineType inlineType = (InlineType)theEObject;
				Object result = caseInlineType(inlineType);
				if (result == null) result = caseConditionalSourceGenString(inlineType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.INTERFACE_TYPE: {
				InterfaceType interfaceType = (InterfaceType)theEObject;
				Object result = caseInterfaceType(interfaceType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MACRO_ARGUMENT_TYPE: {
				MacroArgumentType macroArgumentType = (MacroArgumentType)theEObject;
				Object result = caseMacroArgumentType(macroArgumentType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_ARRAY_MEMBER_TYPE: {
				MapArrayMemberType mapArrayMemberType = (MapArrayMemberType)theEObject;
				Object result = caseMapArrayMemberType(mapArrayMemberType);
				if (result == null) result = caseMappingArrayType(mapArrayMemberType);
				if (result == null) result = caseTwoWayMappingType(mapArrayMemberType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_ARRAY_TYPE_TYPE: {
				MapArrayTypeType mapArrayTypeType = (MapArrayTypeType)theEObject;
				Object result = caseMapArrayTypeType(mapArrayTypeType);
				if (result == null) result = caseMappingArrayType(mapArrayTypeType);
				if (result == null) result = caseTwoWayMappingType(mapArrayTypeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_BITMASK_ELEMENT_TYPE: {
				MapBitmaskElementType mapBitmaskElementType = (MapBitmaskElementType)theEObject;
				Object result = caseMapBitmaskElementType(mapBitmaskElementType);
				if (result == null) result = caseMappingBitmaskType(mapBitmaskElementType);
				if (result == null) result = caseTwoWayMappingType(mapBitmaskElementType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_BITMASK_MEMBER_TYPE: {
				MapBitmaskMemberType mapBitmaskMemberType = (MapBitmaskMemberType)theEObject;
				Object result = caseMapBitmaskMemberType(mapBitmaskMemberType);
				if (result == null) result = caseMappingBitmaskType(mapBitmaskMemberType);
				if (result == null) result = caseTwoWayMappingType(mapBitmaskMemberType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_BITMASK_TYPE_TYPE: {
				MapBitmaskTypeType mapBitmaskTypeType = (MapBitmaskTypeType)theEObject;
				Object result = caseMapBitmaskTypeType(mapBitmaskTypeType);
				if (result == null) result = caseMappingBitmaskType(mapBitmaskTypeType);
				if (result == null) result = caseTwoWayMappingType(mapBitmaskTypeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_BITMASK_VALUE_TYPE: {
				MapBitmaskValueType mapBitmaskValueType = (MapBitmaskValueType)theEObject;
				Object result = caseMapBitmaskValueType(mapBitmaskValueType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_ELEMENT_FROM_TYPE_TYPE: {
				MapElementFromTypeType mapElementFromTypeType = (MapElementFromTypeType)theEObject;
				Object result = caseMapElementFromTypeType(mapElementFromTypeType);
				if (result == null) result = caseTwoWayMappingType(mapElementFromTypeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_ENUM_ELEMENT_TYPE: {
				MapEnumElementType mapEnumElementType = (MapEnumElementType)theEObject;
				Object result = caseMapEnumElementType(mapEnumElementType);
				if (result == null) result = caseMappingEnumType(mapEnumElementType);
				if (result == null) result = caseTwoWayMappingType(mapEnumElementType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_ENUM_MEMBER_TYPE: {
				MapEnumMemberType mapEnumMemberType = (MapEnumMemberType)theEObject;
				Object result = caseMapEnumMemberType(mapEnumMemberType);
				if (result == null) result = caseMappingEnumType(mapEnumMemberType);
				if (result == null) result = caseTwoWayMappingType(mapEnumMemberType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_ENUM_TYPE: {
				MapEnumType mapEnumType = (MapEnumType)theEObject;
				Object result = caseMapEnumType(mapEnumType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_ENUM_TYPE_TYPE: {
				MapEnumTypeType mapEnumTypeType = (MapEnumTypeType)theEObject;
				Object result = caseMapEnumTypeType(mapEnumTypeType);
				if (result == null) result = caseMappingEnumType(mapEnumTypeType);
				if (result == null) result = caseTwoWayMappingType(mapEnumTypeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_FIXED_ELEMENT_TYPE: {
				MapFixedElementType mapFixedElementType = (MapFixedElementType)theEObject;
				Object result = caseMapFixedElementType(mapFixedElementType);
				if (result == null) result = caseMappingFixedType(mapFixedElementType);
				if (result == null) result = caseTwoWayMappingType(mapFixedElementType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_FIXED_MEMBER_TYPE: {
				MapFixedMemberType mapFixedMemberType = (MapFixedMemberType)theEObject;
				Object result = caseMapFixedMemberType(mapFixedMemberType);
				if (result == null) result = caseMappingFixedType(mapFixedMemberType);
				if (result == null) result = caseTwoWayMappingType(mapFixedMemberType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_FIXED_TYPE_TYPE: {
				MapFixedTypeType mapFixedTypeType = (MapFixedTypeType)theEObject;
				Object result = caseMapFixedTypeType(mapFixedTypeType);
				if (result == null) result = caseMappingFixedType(mapFixedTypeType);
				if (result == null) result = caseTwoWayMappingType(mapFixedTypeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_IDENTIFIER_ELEMENT_TYPE: {
				MapIdentifierElementType mapIdentifierElementType = (MapIdentifierElementType)theEObject;
				Object result = caseMapIdentifierElementType(mapIdentifierElementType);
				if (result == null) result = caseMappingIdentifierType(mapIdentifierElementType);
				if (result == null) result = caseTwoWayMappingType(mapIdentifierElementType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_IDENTIFIER_MEMBER_TYPE: {
				MapIdentifierMemberType mapIdentifierMemberType = (MapIdentifierMemberType)theEObject;
				Object result = caseMapIdentifierMemberType(mapIdentifierMemberType);
				if (result == null) result = caseMappingIdentifierType(mapIdentifierMemberType);
				if (result == null) result = caseTwoWayMappingType(mapIdentifierMemberType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_IDENTIFIER_TYPE_TYPE: {
				MapIdentifierTypeType mapIdentifierTypeType = (MapIdentifierTypeType)theEObject;
				Object result = caseMapIdentifierTypeType(mapIdentifierTypeType);
				if (result == null) result = caseMappingIdentifierType(mapIdentifierTypeType);
				if (result == null) result = caseTwoWayMappingType(mapIdentifierTypeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_INSTANCE_ELEMENT_TYPE: {
				MapInstanceElementType mapInstanceElementType = (MapInstanceElementType)theEObject;
				Object result = caseMapInstanceElementType(mapInstanceElementType);
				if (result == null) result = caseMappingInstanceType(mapInstanceElementType);
				if (result == null) result = caseTwoWayMappingType(mapInstanceElementType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_INSTANCE_MEMBER_TYPE: {
				MapInstanceMemberType mapInstanceMemberType = (MapInstanceMemberType)theEObject;
				Object result = caseMapInstanceMemberType(mapInstanceMemberType);
				if (result == null) result = caseMappingInstanceType(mapInstanceMemberType);
				if (result == null) result = caseTwoWayMappingType(mapInstanceMemberType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_INSTANCE_TYPE_TYPE: {
				MapInstanceTypeType mapInstanceTypeType = (MapInstanceTypeType)theEObject;
				Object result = caseMapInstanceTypeType(mapInstanceTypeType);
				if (result == null) result = caseMappingInstanceType(mapInstanceTypeType);
				if (result == null) result = caseTwoWayMappingType(mapInstanceTypeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_INTO_PROPERTY_TYPE: {
				MapIntoPropertyType mapIntoPropertyType = (MapIntoPropertyType)theEObject;
				Object result = caseMapIntoPropertyType(mapIntoPropertyType);
				if (result == null) result = caseTwoWayMappingType(mapIntoPropertyType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_MEMBER_FROM_TYPE_TYPE: {
				MapMemberFromTypeType mapMemberFromTypeType = (MapMemberFromTypeType)theEObject;
				Object result = caseMapMemberFromTypeType(mapMemberFromTypeType);
				if (result == null) result = caseTwoWayMappingType(mapMemberFromTypeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAPPING_ARRAY_TYPE: {
				MappingArrayType mappingArrayType = (MappingArrayType)theEObject;
				Object result = caseMappingArrayType(mappingArrayType);
				if (result == null) result = caseTwoWayMappingType(mappingArrayType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAPPING_BITMASK_TYPE: {
				MappingBitmaskType mappingBitmaskType = (MappingBitmaskType)theEObject;
				Object result = caseMappingBitmaskType(mappingBitmaskType);
				if (result == null) result = caseTwoWayMappingType(mappingBitmaskType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAPPING_ENUM_TYPE: {
				MappingEnumType mappingEnumType = (MappingEnumType)theEObject;
				Object result = caseMappingEnumType(mappingEnumType);
				if (result == null) result = caseTwoWayMappingType(mappingEnumType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAPPING_FIXED_TYPE: {
				MappingFixedType mappingFixedType = (MappingFixedType)theEObject;
				Object result = caseMappingFixedType(mappingFixedType);
				if (result == null) result = caseTwoWayMappingType(mappingFixedType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAPPING_IDENTIFIER_TYPE: {
				MappingIdentifierType mappingIdentifierType = (MappingIdentifierType)theEObject;
				Object result = caseMappingIdentifierType(mappingIdentifierType);
				if (result == null) result = caseTwoWayMappingType(mappingIdentifierType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAPPING_INSTANCE_TYPE: {
				MappingInstanceType mappingInstanceType = (MappingInstanceType)theEObject;
				Object result = caseMappingInstanceType(mappingInstanceType);
				if (result == null) result = caseTwoWayMappingType(mappingInstanceType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAPPING_REFERENCE_TYPE: {
				MappingReferenceType mappingReferenceType = (MappingReferenceType)theEObject;
				Object result = caseMappingReferenceType(mappingReferenceType);
				if (result == null) result = caseTwoWayMappingType(mappingReferenceType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAPPING_RESOURCE_TYPE: {
				MappingResourceType mappingResourceType = (MappingResourceType)theEObject;
				Object result = caseMappingResourceType(mappingResourceType);
				if (result == null) result = caseTwoWayMappingType(mappingResourceType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAPPING_SIMPLE_TYPE: {
				MappingSimpleType mappingSimpleType = (MappingSimpleType)theEObject;
				Object result = caseMappingSimpleType(mappingSimpleType);
				if (result == null) result = caseTwoWayMappingType(mappingSimpleType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_REFERENCE_ELEMENT_TYPE: {
				MapReferenceElementType mapReferenceElementType = (MapReferenceElementType)theEObject;
				Object result = caseMapReferenceElementType(mapReferenceElementType);
				if (result == null) result = caseMappingReferenceType(mapReferenceElementType);
				if (result == null) result = caseTwoWayMappingType(mapReferenceElementType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_REFERENCE_MEMBER_TYPE: {
				MapReferenceMemberType mapReferenceMemberType = (MapReferenceMemberType)theEObject;
				Object result = caseMapReferenceMemberType(mapReferenceMemberType);
				if (result == null) result = caseMappingReferenceType(mapReferenceMemberType);
				if (result == null) result = caseTwoWayMappingType(mapReferenceMemberType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_REFERENCE_TYPE_TYPE: {
				MapReferenceTypeType mapReferenceTypeType = (MapReferenceTypeType)theEObject;
				Object result = caseMapReferenceTypeType(mapReferenceTypeType);
				if (result == null) result = caseMappingReferenceType(mapReferenceTypeType);
				if (result == null) result = caseTwoWayMappingType(mapReferenceTypeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_RESOURCE_ELEMENT_TYPE: {
				MapResourceElementType mapResourceElementType = (MapResourceElementType)theEObject;
				Object result = caseMapResourceElementType(mapResourceElementType);
				if (result == null) result = caseMappingResourceType(mapResourceElementType);
				if (result == null) result = caseTwoWayMappingType(mapResourceElementType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_RESOURCE_MEMBER_TYPE: {
				MapResourceMemberType mapResourceMemberType = (MapResourceMemberType)theEObject;
				Object result = caseMapResourceMemberType(mapResourceMemberType);
				if (result == null) result = caseMappingResourceType(mapResourceMemberType);
				if (result == null) result = caseTwoWayMappingType(mapResourceMemberType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_RESOURCE_TYPE: {
				MapResourceType mapResourceType = (MapResourceType)theEObject;
				Object result = caseMapResourceType(mapResourceType);
				if (result == null) result = caseMappingResourceType(mapResourceType);
				if (result == null) result = caseTwoWayMappingType(mapResourceType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_RESOURCE_TYPE_TYPE: {
				MapResourceTypeType mapResourceTypeType = (MapResourceTypeType)theEObject;
				Object result = caseMapResourceTypeType(mapResourceTypeType);
				if (result == null) result = caseMappingResourceType(mapResourceTypeType);
				if (result == null) result = caseTwoWayMappingType(mapResourceTypeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_SIMPLE_ELEMENT_TYPE: {
				MapSimpleElementType mapSimpleElementType = (MapSimpleElementType)theEObject;
				Object result = caseMapSimpleElementType(mapSimpleElementType);
				if (result == null) result = caseMappingSimpleType(mapSimpleElementType);
				if (result == null) result = caseTwoWayMappingType(mapSimpleElementType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_SIMPLE_MEMBER_TYPE: {
				MapSimpleMemberType mapSimpleMemberType = (MapSimpleMemberType)theEObject;
				Object result = caseMapSimpleMemberType(mapSimpleMemberType);
				if (result == null) result = caseMappingSimpleType(mapSimpleMemberType);
				if (result == null) result = caseTwoWayMappingType(mapSimpleMemberType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.MAP_SIMPLE_TYPE_TYPE: {
				MapSimpleTypeType mapSimpleTypeType = (MapSimpleTypeType)theEObject;
				Object result = caseMapSimpleTypeType(mapSimpleTypeType);
				if (result == null) result = caseMappingSimpleType(mapSimpleTypeType);
				if (result == null) result = caseTwoWayMappingType(mapSimpleTypeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.PROPERTIES_TYPE: {
				PropertiesType propertiesType = (PropertiesType)theEObject;
				Object result = casePropertiesType(propertiesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.PROPERTY_OVERRIDES_TYPE: {
				PropertyOverridesType propertyOverridesType = (PropertyOverridesType)theEObject;
				Object result = casePropertyOverridesType(propertyOverridesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.PROPERTY_OVERRIDE_TYPE: {
				PropertyOverrideType propertyOverrideType = (PropertyOverrideType)theEObject;
				Object result = casePropertyOverrideType(propertyOverrideType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.SCRIPT_TYPE: {
				ScriptType scriptType = (ScriptType)theEObject;
				Object result = caseScriptType(scriptType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.SELECT_TYPE: {
				SelectType selectType = (SelectType)theEObject;
				Object result = caseSelectType(selectType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.SIMPLE_PROPERTY_TYPE: {
				SimplePropertyType simplePropertyType = (SimplePropertyType)theEObject;
				Object result = caseSimplePropertyType(simplePropertyType);
				if (result == null) result = caseAbstractPropertyType(simplePropertyType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.SOURCE_GEN_TYPE: {
				SourceGenType sourceGenType = (SourceGenType)theEObject;
				Object result = caseSourceGenType(sourceGenType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.SOURCE_MAPPING_TYPE: {
				SourceMappingType sourceMappingType = (SourceMappingType)theEObject;
				Object result = caseSourceMappingType(sourceMappingType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE: {
				SourceTypeMappingType sourceTypeMappingType = (SourceTypeMappingType)theEObject;
				Object result = caseSourceTypeMappingType(sourceTypeMappingType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.SYMBIAN_TYPE: {
				SymbianType symbianType = (SymbianType)theEObject;
				Object result = caseSymbianType(symbianType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.TEMPLATE_GROUP_TYPE: {
				TemplateGroupType templateGroupType = (TemplateGroupType)theEObject;
				Object result = caseTemplateGroupType(templateGroupType);
				if (result == null) result = caseConditionalSourceGen(templateGroupType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.TEMPLATE_TYPE: {
				TemplateType templateType = (TemplateType)theEObject;
				Object result = caseTemplateType(templateType);
				if (result == null) result = caseConditionalSourceGenString(templateType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.TWO_WAY_MAPPING_TYPE: {
				TwoWayMappingType twoWayMappingType = (TwoWayMappingType)theEObject;
				Object result = caseTwoWayMappingType(twoWayMappingType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.USE_TEMPLATE_GROUP_TYPE: {
				UseTemplateGroupType useTemplateGroupType = (UseTemplateGroupType)theEObject;
				Object result = caseUseTemplateGroupType(useTemplateGroupType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ComponentPackage.USE_TEMPLATE_TYPE: {
				UseTemplateType useTemplateType = (UseTemplateType)theEObject;
				Object result = caseUseTemplateType(useTemplateType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Abstract Property Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Abstract Property Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseAbstractPropertyType(AbstractPropertyType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Array Property Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Array Property Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseArrayPropertyType(ArrayPropertyType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Attributes Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Attributes Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseAttributesType(AttributesType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Attribute Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Attribute Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseAttributeType(AttributeType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Choice Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Choice Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseChoiceType(ChoiceType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Code Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Code Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseCodeType(CodeType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Definition Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Definition Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseComponentDefinitionType(ComponentDefinitionType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Reference Property Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Reference Property Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseComponentReferencePropertyType(ComponentReferencePropertyType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseComponentType(ComponentType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Compound Property Declaration Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Compound Property Declaration Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseCompoundPropertyDeclarationType(CompoundPropertyDeclarationType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Compound Property Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Compound Property Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseCompoundPropertyType(CompoundPropertyType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Conditional Source Gen</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Conditional Source Gen</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseConditionalSourceGen(ConditionalSourceGen object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Conditional Source Gen String</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Conditional Source Gen String</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseConditionalSourceGenString(ConditionalSourceGenString object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Define Location Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Define Location Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseDefineLocationType(DefineLocationType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Define Macro Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Define Macro Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDefineMacroType(DefineMacroType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Designer Images Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Designer Images Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDesignerImagesType(DesignerImagesType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Documentation Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Documentation Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDocumentationType(DocumentationType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Document Root</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDocumentRoot(DocumentRoot object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Enum Element Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Enum Element Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseEnumElementType(EnumElementType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Enum Property Declaration Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Enum Property Declaration Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseEnumPropertyDeclarationType(EnumPropertyDeclarationType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Enum Property Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Enum Property Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseEnumPropertyType(EnumPropertyType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Events Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Events Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseEventsType(EventsType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Event Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Event Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseEventType(EventType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Expand Argument Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Expand Argument Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseExpandArgumentType(ExpandArgumentType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Expand Macro Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Expand Macro Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseExpandMacroType(ExpandMacroType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Extension Properties Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Extension Properties Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseExtensionPropertiesType(ExtensionPropertiesType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Implementations Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Implementations Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseImplementationsType(ImplementationsType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Implementation Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Implementation Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseImplementationType(ImplementationType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Import Arguments Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Import Arguments Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseImportArgumentsType(ImportArgumentsType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Inline Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Inline Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseInlineType(InlineType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Interface Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Interface Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseInterfaceType(InterfaceType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Macro Argument Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Macro Argument Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseMacroArgumentType(MacroArgumentType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Array Member Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Array Member Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseMapArrayMemberType(MapArrayMemberType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Array Type Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Array Type Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseMapArrayTypeType(MapArrayTypeType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Bitmask Element Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Bitmask Element Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseMapBitmaskElementType(MapBitmaskElementType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Bitmask Member Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Bitmask Member Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseMapBitmaskMemberType(MapBitmaskMemberType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Bitmask Type Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Bitmask Type Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseMapBitmaskTypeType(MapBitmaskTypeType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Bitmask Value Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Bitmask Value Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseMapBitmaskValueType(MapBitmaskValueType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Element From Type Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Element From Type Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseMapElementFromTypeType(MapElementFromTypeType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Enum Element Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Enum Element Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseMapEnumElementType(MapEnumElementType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Enum Member Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Enum Member Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseMapEnumMemberType(MapEnumMemberType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Enum Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Enum Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseMapEnumType(MapEnumType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Enum Type Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Enum Type Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseMapEnumTypeType(MapEnumTypeType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Fixed Element Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Fixed Element Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseMapFixedElementType(MapFixedElementType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Fixed Member Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Fixed Member Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseMapFixedMemberType(MapFixedMemberType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Fixed Type Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Fixed Type Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseMapFixedTypeType(MapFixedTypeType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Identifier Element Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Identifier Element Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseMapIdentifierElementType(MapIdentifierElementType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Identifier Member Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Identifier Member Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseMapIdentifierMemberType(MapIdentifierMemberType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Identifier Type Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Identifier Type Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseMapIdentifierTypeType(MapIdentifierTypeType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Instance Element Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Instance Element Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseMapInstanceElementType(MapInstanceElementType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Instance Member Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Instance Member Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseMapInstanceMemberType(MapInstanceMemberType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Instance Type Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Instance Type Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseMapInstanceTypeType(MapInstanceTypeType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Into Property Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Into Property Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseMapIntoPropertyType(MapIntoPropertyType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Member From Type Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Member From Type Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseMapMemberFromTypeType(MapMemberFromTypeType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Mapping Array Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Mapping Array Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseMappingArrayType(MappingArrayType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Mapping Bitmask Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Mapping Bitmask Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseMappingBitmaskType(MappingBitmaskType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Mapping Enum Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Mapping Enum Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseMappingEnumType(MappingEnumType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Mapping Fixed Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Mapping Fixed Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseMappingFixedType(MappingFixedType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Mapping Identifier Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Mapping Identifier Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseMappingIdentifierType(MappingIdentifierType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Mapping Instance Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Mapping Instance Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseMappingInstanceType(MappingInstanceType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Mapping Reference Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Mapping Reference Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseMappingReferenceType(MappingReferenceType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Mapping Resource Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Mapping Resource Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseMappingResourceType(MappingResourceType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Mapping Simple Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Mapping Simple Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseMappingSimpleType(MappingSimpleType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Reference Element Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Reference Element Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseMapReferenceElementType(MapReferenceElementType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Reference Member Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Reference Member Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseMapReferenceMemberType(MapReferenceMemberType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Reference Type Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Reference Type Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseMapReferenceTypeType(MapReferenceTypeType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Resource Element Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Resource Element Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseMapResourceElementType(MapResourceElementType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Resource Member Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Resource Member Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseMapResourceMemberType(MapResourceMemberType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Resource Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Resource Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseMapResourceType(MapResourceType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Resource Type Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Resource Type Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseMapResourceTypeType(MapResourceTypeType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Simple Element Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Simple Element Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseMapSimpleElementType(MapSimpleElementType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Simple Member Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Simple Member Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseMapSimpleMemberType(MapSimpleMemberType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Simple Type Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Simple Type Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseMapSimpleTypeType(MapSimpleTypeType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Properties Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Properties Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePropertiesType(PropertiesType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Property Overrides Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Property Overrides Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePropertyOverridesType(PropertyOverridesType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Property Override Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Property Override Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePropertyOverrideType(PropertyOverrideType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Script Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Script Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseScriptType(ScriptType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Select Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Select Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseSelectType(SelectType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Simple Property Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Simple Property Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseSimplePropertyType(SimplePropertyType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Source Gen Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Source Gen Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseSourceGenType(SourceGenType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Source Mapping Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Source Mapping Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseSourceMappingType(SourceMappingType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Source Type Mapping Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Source Type Mapping Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseSourceTypeMappingType(SourceTypeMappingType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Symbian Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Symbian Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseSymbianType(SymbianType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Template Group Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Template Group Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseTemplateGroupType(TemplateGroupType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Template Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Template Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseTemplateType(TemplateType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Two Way Mapping Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Two Way Mapping Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseTwoWayMappingType(TwoWayMappingType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Use Template Group Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Use Template Group Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseUseTemplateGroupType(UseTemplateGroupType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Use Template Type</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Use Template Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseUseTemplateType(UseTemplateType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public Object defaultCase(EObject object) {
		return null;
	}

} //ComponentSwitch
