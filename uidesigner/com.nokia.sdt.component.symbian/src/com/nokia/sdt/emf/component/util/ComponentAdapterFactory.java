/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.util;

import com.nokia.sdt.emf.component.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.nokia.sdt.emf.component.ComponentPackage
 * @generated
 */
public class ComponentAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ComponentPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ComponentPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch the delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComponentSwitch modelSwitch =
		new ComponentSwitch() {
			public Object caseAbstractPropertyType(AbstractPropertyType object) {
				return createAbstractPropertyTypeAdapter();
			}
			public Object caseArrayPropertyType(ArrayPropertyType object) {
				return createArrayPropertyTypeAdapter();
			}
			public Object caseAttributesType(AttributesType object) {
				return createAttributesTypeAdapter();
			}
			public Object caseAttributeType(AttributeType object) {
				return createAttributeTypeAdapter();
			}
			public Object caseChoiceType(ChoiceType object) {
				return createChoiceTypeAdapter();
			}
			public Object caseCodeType(CodeType object) {
				return createCodeTypeAdapter();
			}
			public Object caseComponentDefinitionType(ComponentDefinitionType object) {
				return createComponentDefinitionTypeAdapter();
			}
			public Object caseComponentReferencePropertyType(ComponentReferencePropertyType object) {
				return createComponentReferencePropertyTypeAdapter();
			}
			public Object caseComponentType(ComponentType object) {
				return createComponentTypeAdapter();
			}
			public Object caseCompoundPropertyDeclarationType(CompoundPropertyDeclarationType object) {
				return createCompoundPropertyDeclarationTypeAdapter();
			}
			public Object caseCompoundPropertyType(CompoundPropertyType object) {
				return createCompoundPropertyTypeAdapter();
			}
			public Object caseConditionalSourceGen(ConditionalSourceGen object) {
				return createConditionalSourceGenAdapter();
			}
			public Object caseConditionalSourceGenString(ConditionalSourceGenString object) {
				return createConditionalSourceGenStringAdapter();
			}
			public Object caseDefineLocationType(DefineLocationType object) {
				return createDefineLocationTypeAdapter();
			}
			public Object caseDefineMacroType(DefineMacroType object) {
				return createDefineMacroTypeAdapter();
			}
			public Object caseDesignerImagesType(DesignerImagesType object) {
				return createDesignerImagesTypeAdapter();
			}
			public Object caseDocumentationType(DocumentationType object) {
				return createDocumentationTypeAdapter();
			}
			public Object caseDocumentRoot(DocumentRoot object) {
				return createDocumentRootAdapter();
			}
			public Object caseEnumElementType(EnumElementType object) {
				return createEnumElementTypeAdapter();
			}
			public Object caseEnumPropertyDeclarationType(EnumPropertyDeclarationType object) {
				return createEnumPropertyDeclarationTypeAdapter();
			}
			public Object caseEnumPropertyType(EnumPropertyType object) {
				return createEnumPropertyTypeAdapter();
			}
			public Object caseEventsType(EventsType object) {
				return createEventsTypeAdapter();
			}
			public Object caseEventType(EventType object) {
				return createEventTypeAdapter();
			}
			public Object caseExpandArgumentType(ExpandArgumentType object) {
				return createExpandArgumentTypeAdapter();
			}
			public Object caseExpandMacroType(ExpandMacroType object) {
				return createExpandMacroTypeAdapter();
			}
			public Object caseExtensionPropertiesType(ExtensionPropertiesType object) {
				return createExtensionPropertiesTypeAdapter();
			}
			public Object caseImplementationsType(ImplementationsType object) {
				return createImplementationsTypeAdapter();
			}
			public Object caseImplementationType(ImplementationType object) {
				return createImplementationTypeAdapter();
			}
			public Object caseImportArgumentsType(ImportArgumentsType object) {
				return createImportArgumentsTypeAdapter();
			}
			public Object caseInlineType(InlineType object) {
				return createInlineTypeAdapter();
			}
			public Object caseInterfaceType(InterfaceType object) {
				return createInterfaceTypeAdapter();
			}
			public Object caseMacroArgumentType(MacroArgumentType object) {
				return createMacroArgumentTypeAdapter();
			}
			public Object caseMapArrayMemberType(MapArrayMemberType object) {
				return createMapArrayMemberTypeAdapter();
			}
			public Object caseMapArrayTypeType(MapArrayTypeType object) {
				return createMapArrayTypeTypeAdapter();
			}
			public Object caseMapBitmaskElementType(MapBitmaskElementType object) {
				return createMapBitmaskElementTypeAdapter();
			}
			public Object caseMapBitmaskMemberType(MapBitmaskMemberType object) {
				return createMapBitmaskMemberTypeAdapter();
			}
			public Object caseMapBitmaskTypeType(MapBitmaskTypeType object) {
				return createMapBitmaskTypeTypeAdapter();
			}
			public Object caseMapBitmaskValueType(MapBitmaskValueType object) {
				return createMapBitmaskValueTypeAdapter();
			}
			public Object caseMapElementFromTypeType(MapElementFromTypeType object) {
				return createMapElementFromTypeTypeAdapter();
			}
			public Object caseMapEnumElementType(MapEnumElementType object) {
				return createMapEnumElementTypeAdapter();
			}
			public Object caseMapEnumMemberType(MapEnumMemberType object) {
				return createMapEnumMemberTypeAdapter();
			}
			public Object caseMapEnumType(MapEnumType object) {
				return createMapEnumTypeAdapter();
			}
			public Object caseMapEnumTypeType(MapEnumTypeType object) {
				return createMapEnumTypeTypeAdapter();
			}
			public Object caseMapFixedElementType(MapFixedElementType object) {
				return createMapFixedElementTypeAdapter();
			}
			public Object caseMapFixedMemberType(MapFixedMemberType object) {
				return createMapFixedMemberTypeAdapter();
			}
			public Object caseMapFixedTypeType(MapFixedTypeType object) {
				return createMapFixedTypeTypeAdapter();
			}
			public Object caseMapIdentifierElementType(MapIdentifierElementType object) {
				return createMapIdentifierElementTypeAdapter();
			}
			public Object caseMapIdentifierMemberType(MapIdentifierMemberType object) {
				return createMapIdentifierMemberTypeAdapter();
			}
			public Object caseMapIdentifierTypeType(MapIdentifierTypeType object) {
				return createMapIdentifierTypeTypeAdapter();
			}
			public Object caseMapInstanceElementType(MapInstanceElementType object) {
				return createMapInstanceElementTypeAdapter();
			}
			public Object caseMapInstanceMemberType(MapInstanceMemberType object) {
				return createMapInstanceMemberTypeAdapter();
			}
			public Object caseMapInstanceTypeType(MapInstanceTypeType object) {
				return createMapInstanceTypeTypeAdapter();
			}
			public Object caseMapIntoPropertyType(MapIntoPropertyType object) {
				return createMapIntoPropertyTypeAdapter();
			}
			public Object caseMapMemberFromTypeType(MapMemberFromTypeType object) {
				return createMapMemberFromTypeTypeAdapter();
			}
			public Object caseMappingArrayType(MappingArrayType object) {
				return createMappingArrayTypeAdapter();
			}
			public Object caseMappingBitmaskType(MappingBitmaskType object) {
				return createMappingBitmaskTypeAdapter();
			}
			public Object caseMappingEnumType(MappingEnumType object) {
				return createMappingEnumTypeAdapter();
			}
			public Object caseMappingFixedType(MappingFixedType object) {
				return createMappingFixedTypeAdapter();
			}
			public Object caseMappingIdentifierType(MappingIdentifierType object) {
				return createMappingIdentifierTypeAdapter();
			}
			public Object caseMappingInstanceType(MappingInstanceType object) {
				return createMappingInstanceTypeAdapter();
			}
			public Object caseMappingReferenceType(MappingReferenceType object) {
				return createMappingReferenceTypeAdapter();
			}
			public Object caseMappingResourceType(MappingResourceType object) {
				return createMappingResourceTypeAdapter();
			}
			public Object caseMappingSimpleType(MappingSimpleType object) {
				return createMappingSimpleTypeAdapter();
			}
			public Object caseMapReferenceElementType(MapReferenceElementType object) {
				return createMapReferenceElementTypeAdapter();
			}
			public Object caseMapReferenceMemberType(MapReferenceMemberType object) {
				return createMapReferenceMemberTypeAdapter();
			}
			public Object caseMapReferenceTypeType(MapReferenceTypeType object) {
				return createMapReferenceTypeTypeAdapter();
			}
			public Object caseMapResourceElementType(MapResourceElementType object) {
				return createMapResourceElementTypeAdapter();
			}
			public Object caseMapResourceMemberType(MapResourceMemberType object) {
				return createMapResourceMemberTypeAdapter();
			}
			public Object caseMapResourceType(MapResourceType object) {
				return createMapResourceTypeAdapter();
			}
			public Object caseMapResourceTypeType(MapResourceTypeType object) {
				return createMapResourceTypeTypeAdapter();
			}
			public Object caseMapSimpleElementType(MapSimpleElementType object) {
				return createMapSimpleElementTypeAdapter();
			}
			public Object caseMapSimpleMemberType(MapSimpleMemberType object) {
				return createMapSimpleMemberTypeAdapter();
			}
			public Object caseMapSimpleTypeType(MapSimpleTypeType object) {
				return createMapSimpleTypeTypeAdapter();
			}
			public Object casePropertiesType(PropertiesType object) {
				return createPropertiesTypeAdapter();
			}
			public Object casePropertyOverridesType(PropertyOverridesType object) {
				return createPropertyOverridesTypeAdapter();
			}
			public Object casePropertyOverrideType(PropertyOverrideType object) {
				return createPropertyOverrideTypeAdapter();
			}
			public Object caseScriptType(ScriptType object) {
				return createScriptTypeAdapter();
			}
			public Object caseSelectType(SelectType object) {
				return createSelectTypeAdapter();
			}
			public Object caseSimplePropertyType(SimplePropertyType object) {
				return createSimplePropertyTypeAdapter();
			}
			public Object caseSourceGenType(SourceGenType object) {
				return createSourceGenTypeAdapter();
			}
			public Object caseSourceMappingType(SourceMappingType object) {
				return createSourceMappingTypeAdapter();
			}
			public Object caseSourceTypeMappingType(SourceTypeMappingType object) {
				return createSourceTypeMappingTypeAdapter();
			}
			public Object caseSymbianType(SymbianType object) {
				return createSymbianTypeAdapter();
			}
			public Object caseTemplateGroupType(TemplateGroupType object) {
				return createTemplateGroupTypeAdapter();
			}
			public Object caseTemplateType(TemplateType object) {
				return createTemplateTypeAdapter();
			}
			public Object caseTwoWayMappingType(TwoWayMappingType object) {
				return createTwoWayMappingTypeAdapter();
			}
			public Object caseUseTemplateGroupType(UseTemplateGroupType object) {
				return createUseTemplateGroupTypeAdapter();
			}
			public Object caseUseTemplateType(UseTemplateType object) {
				return createUseTemplateTypeAdapter();
			}
			public Object defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	public Adapter createAdapter(Notifier target) {
		return (Adapter)modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.AbstractPropertyType <em>Abstract Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.AbstractPropertyType
	 * @generated
	 */
	public Adapter createAbstractPropertyTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.ArrayPropertyType <em>Array Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.ArrayPropertyType
	 * @generated
	 */
	public Adapter createArrayPropertyTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.AttributesType <em>Attributes Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.AttributesType
	 * @generated
	 */
	public Adapter createAttributesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.AttributeType <em>Attribute Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.AttributeType
	 * @generated
	 */
	public Adapter createAttributeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.ChoiceType <em>Choice Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.ChoiceType
	 * @generated
	 */
    public Adapter createChoiceTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.CodeType <em>Code Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.CodeType
	 * @generated
	 */
	public Adapter createCodeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.ComponentDefinitionType <em>Definition Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.ComponentDefinitionType
	 * @generated
	 */
	public Adapter createComponentDefinitionTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.ComponentReferencePropertyType <em>Reference Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.ComponentReferencePropertyType
	 * @generated
	 */
	public Adapter createComponentReferencePropertyTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.ComponentType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.ComponentType
	 * @generated
	 */
	public Adapter createComponentTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.CompoundPropertyDeclarationType <em>Compound Property Declaration Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.CompoundPropertyDeclarationType
	 * @generated
	 */
	public Adapter createCompoundPropertyDeclarationTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.CompoundPropertyType <em>Compound Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.CompoundPropertyType
	 * @generated
	 */
	public Adapter createCompoundPropertyTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.ConditionalSourceGen <em>Conditional Source Gen</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.ConditionalSourceGen
	 * @generated
	 */
	public Adapter createConditionalSourceGenAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.ConditionalSourceGenString <em>Conditional Source Gen String</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.ConditionalSourceGenString
	 * @generated
	 */
	public Adapter createConditionalSourceGenStringAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.DefineLocationType <em>Define Location Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.DefineLocationType
	 * @generated
	 */
    public Adapter createDefineLocationTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.DefineMacroType <em>Define Macro Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.DefineMacroType
	 * @generated
	 */
	public Adapter createDefineMacroTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.DesignerImagesType <em>Designer Images Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.DesignerImagesType
	 * @generated
	 */
	public Adapter createDesignerImagesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.DocumentationType <em>Documentation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.DocumentationType
	 * @generated
	 */
	public Adapter createDocumentationTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.DocumentRoot
	 * @generated
	 */
	public Adapter createDocumentRootAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.EnumElementType <em>Enum Element Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.EnumElementType
	 * @generated
	 */
	public Adapter createEnumElementTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.EnumPropertyDeclarationType <em>Enum Property Declaration Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.EnumPropertyDeclarationType
	 * @generated
	 */
	public Adapter createEnumPropertyDeclarationTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.EnumPropertyType <em>Enum Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.EnumPropertyType
	 * @generated
	 */
	public Adapter createEnumPropertyTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.EventsType <em>Events Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.EventsType
	 * @generated
	 */
	public Adapter createEventsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.EventType <em>Event Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.EventType
	 * @generated
	 */
	public Adapter createEventTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.ExpandArgumentType <em>Expand Argument Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.ExpandArgumentType
	 * @generated
	 */
	public Adapter createExpandArgumentTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.ExpandMacroType <em>Expand Macro Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.ExpandMacroType
	 * @generated
	 */
	public Adapter createExpandMacroTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.ExtensionPropertiesType <em>Extension Properties Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.ExtensionPropertiesType
	 * @generated
	 */
	public Adapter createExtensionPropertiesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.ImplementationsType <em>Implementations Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.ImplementationsType
	 * @generated
	 */
	public Adapter createImplementationsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.ImplementationType <em>Implementation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.ImplementationType
	 * @generated
	 */
	public Adapter createImplementationTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.ImportArgumentsType <em>Import Arguments Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.ImportArgumentsType
	 * @generated
	 */
	public Adapter createImportArgumentsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.InlineType <em>Inline Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.InlineType
	 * @generated
	 */
    public Adapter createInlineTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.InterfaceType <em>Interface Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.InterfaceType
	 * @generated
	 */
	public Adapter createInterfaceTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MacroArgumentType <em>Macro Argument Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MacroArgumentType
	 * @generated
	 */
	public Adapter createMacroArgumentTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapArrayMemberType <em>Map Array Member Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapArrayMemberType
	 * @generated
	 */
    public Adapter createMapArrayMemberTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapArrayTypeType <em>Map Array Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapArrayTypeType
	 * @generated
	 */
	public Adapter createMapArrayTypeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapBitmaskElementType <em>Map Bitmask Element Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapBitmaskElementType
	 * @generated
	 */
    public Adapter createMapBitmaskElementTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapBitmaskMemberType <em>Map Bitmask Member Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapBitmaskMemberType
	 * @generated
	 */
    public Adapter createMapBitmaskMemberTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapBitmaskTypeType <em>Map Bitmask Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapBitmaskTypeType
	 * @generated
	 */
	public Adapter createMapBitmaskTypeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapBitmaskValueType <em>Map Bitmask Value Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapBitmaskValueType
	 * @generated
	 */
    public Adapter createMapBitmaskValueTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapElementFromTypeType <em>Map Element From Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapElementFromTypeType
	 * @generated
	 */
	public Adapter createMapElementFromTypeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapEnumElementType <em>Map Enum Element Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapEnumElementType
	 * @generated
	 */
    public Adapter createMapEnumElementTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapEnumMemberType <em>Map Enum Member Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapEnumMemberType
	 * @generated
	 */
    public Adapter createMapEnumMemberTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapEnumType <em>Map Enum Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapEnumType
	 * @generated
	 */
    public Adapter createMapEnumTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapEnumTypeType <em>Map Enum Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapEnumTypeType
	 * @generated
	 */
	public Adapter createMapEnumTypeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapFixedElementType <em>Map Fixed Element Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapFixedElementType
	 * @generated
	 */
	public Adapter createMapFixedElementTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapFixedMemberType <em>Map Fixed Member Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapFixedMemberType
	 * @generated
	 */
    public Adapter createMapFixedMemberTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapFixedTypeType <em>Map Fixed Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapFixedTypeType
	 * @generated
	 */
	public Adapter createMapFixedTypeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapIdentifierElementType <em>Map Identifier Element Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapIdentifierElementType
	 * @generated
	 */
    public Adapter createMapIdentifierElementTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapIdentifierMemberType <em>Map Identifier Member Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapIdentifierMemberType
	 * @generated
	 */
    public Adapter createMapIdentifierMemberTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapIdentifierTypeType <em>Map Identifier Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapIdentifierTypeType
	 * @generated
	 */
	public Adapter createMapIdentifierTypeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapInstanceElementType <em>Map Instance Element Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapInstanceElementType
	 * @generated
	 */
    public Adapter createMapInstanceElementTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapInstanceMemberType <em>Map Instance Member Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapInstanceMemberType
	 * @generated
	 */
    public Adapter createMapInstanceMemberTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapInstanceTypeType <em>Map Instance Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapInstanceTypeType
	 * @generated
	 */
	public Adapter createMapInstanceTypeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapIntoPropertyType <em>Map Into Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapIntoPropertyType
	 * @generated
	 */
	public Adapter createMapIntoPropertyTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapMemberFromTypeType <em>Map Member From Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapMemberFromTypeType
	 * @generated
	 */
	public Adapter createMapMemberFromTypeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MappingArrayType <em>Mapping Array Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MappingArrayType
	 * @generated
	 */
    public Adapter createMappingArrayTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MappingBitmaskType <em>Mapping Bitmask Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MappingBitmaskType
	 * @generated
	 */
    public Adapter createMappingBitmaskTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MappingEnumType <em>Mapping Enum Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MappingEnumType
	 * @generated
	 */
    public Adapter createMappingEnumTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MappingFixedType <em>Mapping Fixed Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MappingFixedType
	 * @generated
	 */
    public Adapter createMappingFixedTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MappingIdentifierType <em>Mapping Identifier Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MappingIdentifierType
	 * @generated
	 */
    public Adapter createMappingIdentifierTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MappingInstanceType <em>Mapping Instance Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MappingInstanceType
	 * @generated
	 */
    public Adapter createMappingInstanceTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MappingReferenceType <em>Mapping Reference Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MappingReferenceType
	 * @generated
	 */
    public Adapter createMappingReferenceTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MappingResourceType <em>Mapping Resource Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MappingResourceType
	 * @generated
	 */
    public Adapter createMappingResourceTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MappingSimpleType <em>Mapping Simple Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MappingSimpleType
	 * @generated
	 */
    public Adapter createMappingSimpleTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapReferenceElementType <em>Map Reference Element Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapReferenceElementType
	 * @generated
	 */
    public Adapter createMapReferenceElementTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapReferenceMemberType <em>Map Reference Member Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapReferenceMemberType
	 * @generated
	 */
    public Adapter createMapReferenceMemberTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapReferenceTypeType <em>Map Reference Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapReferenceTypeType
	 * @generated
	 */
	public Adapter createMapReferenceTypeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapResourceElementType <em>Map Resource Element Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapResourceElementType
	 * @generated
	 */
    public Adapter createMapResourceElementTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapResourceMemberType <em>Map Resource Member Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapResourceMemberType
	 * @generated
	 */
    public Adapter createMapResourceMemberTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapResourceType <em>Map Resource Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapResourceType
	 * @generated
	 */
    public Adapter createMapResourceTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapResourceTypeType <em>Map Resource Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapResourceTypeType
	 * @generated
	 */
	public Adapter createMapResourceTypeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapSimpleElementType <em>Map Simple Element Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapSimpleElementType
	 * @generated
	 */
    public Adapter createMapSimpleElementTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapSimpleMemberType <em>Map Simple Member Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapSimpleMemberType
	 * @generated
	 */
    public Adapter createMapSimpleMemberTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.MapSimpleTypeType <em>Map Simple Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.MapSimpleTypeType
	 * @generated
	 */
	public Adapter createMapSimpleTypeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.PropertiesType <em>Properties Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.PropertiesType
	 * @generated
	 */
	public Adapter createPropertiesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.PropertyOverridesType <em>Property Overrides Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.PropertyOverridesType
	 * @generated
	 */
	public Adapter createPropertyOverridesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.PropertyOverrideType <em>Property Override Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.PropertyOverrideType
	 * @generated
	 */
	public Adapter createPropertyOverrideTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.ScriptType <em>Script Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.ScriptType
	 * @generated
	 */
	public Adapter createScriptTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.SelectType <em>Select Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.SelectType
	 * @generated
	 */
    public Adapter createSelectTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.SimplePropertyType <em>Simple Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.SimplePropertyType
	 * @generated
	 */
	public Adapter createSimplePropertyTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.SourceGenType <em>Source Gen Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.SourceGenType
	 * @generated
	 */
    public Adapter createSourceGenTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.SourceMappingType <em>Source Mapping Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.SourceMappingType
	 * @generated
	 */
    public Adapter createSourceMappingTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.SourceTypeMappingType <em>Source Type Mapping Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.SourceTypeMappingType
	 * @generated
	 */
	public Adapter createSourceTypeMappingTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.SymbianType <em>Symbian Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.SymbianType
	 * @generated
	 */
	public Adapter createSymbianTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.TemplateGroupType <em>Template Group Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.TemplateGroupType
	 * @generated
	 */
    public Adapter createTemplateGroupTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.TemplateType <em>Template Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.TemplateType
	 * @generated
	 */
    public Adapter createTemplateTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.TwoWayMappingType <em>Two Way Mapping Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.TwoWayMappingType
	 * @generated
	 */
    public Adapter createTwoWayMappingTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.UseTemplateGroupType <em>Use Template Group Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.UseTemplateGroupType
	 * @generated
	 */
    public Adapter createUseTemplateGroupTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.component.UseTemplateType <em>Use Template Type</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.component.UseTemplateType
	 * @generated
	 */
    public Adapter createUseTemplateTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //ComponentAdapterFactory
