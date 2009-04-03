/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

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
import com.nokia.sdt.emf.component.MapFixedElementType;
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
import com.nokia.sdt.emf.component.StandaloneType;
import com.nokia.sdt.emf.component.SymbianType;
import com.nokia.sdt.emf.component.TemplateGroupType;
import com.nokia.sdt.emf.component.TemplateType;
import com.nokia.sdt.emf.component.TwoWayMappingType;
import com.nokia.sdt.emf.component.UseExpandMacroType;
import com.nokia.sdt.emf.component.UseTemplateGroupType;
import com.nokia.sdt.emf.component.UseTemplateType;
import com.nokia.sdt.emf.component.util.ComponentValidator;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import java.util.List;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ComponentPackageImpl extends EPackageImpl implements ComponentPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractPropertyTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass arrayPropertyTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributesTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass choiceTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass codeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass componentDefinitionTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass componentReferencePropertyTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass componentTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compoundPropertyDeclarationTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compoundPropertyTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass conditionalSourceGenEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass conditionalSourceGenStringEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass defineLocationTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass defineMacroTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass designerImagesTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass documentationTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass documentRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enumElementTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enumPropertyDeclarationTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enumPropertyTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventsTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass expandArgumentTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass expandMacroTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass extensionPropertiesTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass implementationsTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass implementationTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass importArgumentsTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass inlineTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass interfaceTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass macroArgumentTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass mapArrayMemberTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mapArrayTypeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass mapBitmaskElementTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass mapBitmaskMemberTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mapBitmaskTypeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass mapBitmaskValueTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mapElementFromTypeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass mapEnumElementTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass mapEnumMemberTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass mapEnumTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mapEnumTypeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mapFixedElementTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass mapFixedMemberTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mapFixedTypeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass mapIdentifierElementTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass mapIdentifierMemberTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mapIdentifierTypeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass mapInstanceElementTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass mapInstanceMemberTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mapInstanceTypeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mapIntoPropertyTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mapMemberFromTypeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass mappingArrayTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass mappingBitmaskTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass mappingEnumTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass mappingFixedTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass mappingIdentifierTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass mappingInstanceTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass mappingReferenceTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass mappingResourceTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass mappingSimpleTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass mapReferenceElementTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass mapReferenceMemberTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mapReferenceTypeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass mapResourceElementTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass mapResourceMemberTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass mapResourceTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mapResourceTypeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass mapSimpleElementTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass mapSimpleMemberTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mapSimpleTypeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertiesTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyOverridesTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyOverrideTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass scriptTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass selectTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simplePropertyTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass sourceGenTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass sourceMappingTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sourceTypeMappingTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass symbianTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass templateGroupTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass templateTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass twoWayMappingTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass useTemplateGroupTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass useTemplateTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum propertyDataTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum referenceScopeTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum standaloneTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType listOfStringsEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType propertyDataTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType referenceScopeTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType standaloneTypeObjectEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.nokia.sdt.emf.component.ComponentPackage#eNS_URI
	 * @see #init()
	 * @generated NOT
	 */
	private ComponentPackageImpl() {
		super(eNS_URI, ComponentFactory.eINSTANCE);
        
        MapResourceTypeImpl.uniqueId = 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this
	 * model, and for any others upon which it depends.  Simple
	 * dependencies are satisfied by calling this method on all
	 * dependent packages before doing anything else.  This method drives
	 * initialization for interdependent packages directly, in parallel
	 * with this package, itself.
	 * <p>Of this package and its interdependencies, all packages which
	 * have not yet been registered by their URI values are first created
	 * and registered.  The packages are then initialized in two steps:
	 * meta-model objects for all of the packages are created before any
	 * are initialized, since one package's meta-model objects may refer to
	 * those of another.
	 * <p>Invocation of this method will not affect any packages that have
	 * already been initialized.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ComponentPackage init() {
		if (isInited) return (ComponentPackage)EPackage.Registry.INSTANCE.getEPackage(ComponentPackage.eNS_URI);

		// Obtain or create and register package
		ComponentPackageImpl theComponentPackage = (ComponentPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof ComponentPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new ComponentPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theComponentPackage.createPackageContents();

		// Initialize created meta-data
		theComponentPackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(theComponentPackage, 
			 new EValidator.Descriptor() {
				 public EValidator getEValidator() {
					 return ComponentValidator.INSTANCE;
				 }
			 });

		// Mark meta-data to indicate it can't be changed
		theComponentPackage.freeze();

		return theComponentPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAbstractPropertyType() {
		return abstractPropertyTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getAbstractPropertyType_Category() {
		return (EAttribute)abstractPropertyTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getAbstractPropertyType_DescriptionKey() {
		return (EAttribute)abstractPropertyTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getAbstractPropertyType_DisplayName() {
		return (EAttribute)abstractPropertyTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbstractPropertyType_EditorClass() {
		return (EAttribute)abstractPropertyTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getAbstractPropertyType_HelpKey() {
		return (EAttribute)abstractPropertyTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getAbstractPropertyType_Name() {
		return (EAttribute)abstractPropertyTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getAbstractPropertyType_ReadOnly() {
		return (EAttribute)abstractPropertyTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbstractPropertyType_Resettable() {
		return (EAttribute)abstractPropertyTypeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArrayPropertyType() {
		return arrayPropertyTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArrayPropertyType_Type() {
		return (EAttribute)arrayPropertyTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributesType() {
		return attributesTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttributesType_Attribute() {
		return (EReference)attributesTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributeType() {
		return attributeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributeType_Value() {
		return (EAttribute)attributeTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getAttributeType_Key() {
		return (EAttribute)attributeTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getChoiceType() {
		return choiceTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getChoiceType_Group() {
		return (EAttribute)choiceTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getChoiceType_TwoWayMappingGroup() {
		return (EAttribute)choiceTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getChoiceType_TwoWayMapping() {
		return (EReference)choiceTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getChoiceType_MapResource() {
		return (EReference)choiceTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getChoiceType_Select() {
		return (EReference)choiceTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getChoiceType_Value() {
		return (EAttribute)choiceTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCodeType() {
		return codeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getCodeType_Class() {
		return (EAttribute)codeTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getCodeType_Plugin() {
		return (EAttribute)codeTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getComponentDefinitionType() {
		return componentDefinitionTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponentDefinitionType_CompoundPropertyDeclaration() {
		return (EReference)componentDefinitionTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponentDefinitionType_EnumPropertyDeclaration() {
		return (EReference)componentDefinitionTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponentDefinitionType_Component() {
		return (EReference)componentDefinitionTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getComponentReferencePropertyType() {
		return componentReferencePropertyTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponentReferencePropertyType_Constraint() {
		return (EAttribute)componentReferencePropertyTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponentReferencePropertyType_CreationKeys() {
		return (EAttribute)componentReferencePropertyTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponentReferencePropertyType_PromoteReferenceProperties() {
		return (EAttribute)componentReferencePropertyTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponentReferencePropertyType_Scope() {
		return (EAttribute)componentReferencePropertyTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getComponentType() {
		return componentTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponentType_Documentation() {
		return (EReference)componentTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponentType_Symbian() {
		return (EReference)componentTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponentType_DesignerImages() {
		return (EReference)componentTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponentType_Attributes() {
		return (EReference)componentTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponentType_Properties() {
		return (EReference)componentTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponentType_ExtensionProperties() {
		return (EReference)componentTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponentType_PropertyOverrides() {
		return (EReference)componentTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponentType_Events() {
		return (EReference)componentTypeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getComponentType_SourceGen() {
		return (EReference)componentTypeEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getComponentType_SourceMapping() {
		return (EReference)componentTypeEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponentType_Implementations() {
		return (EReference)componentTypeEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getComponentType_Abstract() {
		return (EAttribute)componentTypeEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getComponentType_BaseComponent() {
		return (EAttribute)componentTypeEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getComponentType_Category() {
		return (EAttribute)componentTypeEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getComponentType_FriendlyName() {
		return (EAttribute)componentTypeEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getComponentType_InstanceNameRoot() {
		return (EAttribute)componentTypeEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getComponentType_QualifiedName() {
		return (EAttribute)componentTypeEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getComponentType_Version() {
		return (EAttribute)componentTypeEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompoundPropertyDeclarationType() {
		return compoundPropertyDeclarationTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCompoundPropertyDeclarationType_AbstractPropertyGroup() {
		return (EAttribute)compoundPropertyDeclarationTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompoundPropertyDeclarationType_AbstractProperty() {
		return (EReference)compoundPropertyDeclarationTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompoundPropertyDeclarationType_SourceTypeMapping() {
		return (EReference)compoundPropertyDeclarationTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCompoundPropertyDeclarationType_ConverterClass() {
		return (EAttribute)compoundPropertyDeclarationTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCompoundPropertyDeclarationType_EditableType() {
		return (EAttribute)compoundPropertyDeclarationTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCompoundPropertyDeclarationType_EditorClass() {
		return (EAttribute)compoundPropertyDeclarationTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getCompoundPropertyDeclarationType_QualifiedName() {
		return (EAttribute)compoundPropertyDeclarationTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompoundPropertyType() {
		return compoundPropertyTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCompoundPropertyType_Default() {
		return (EAttribute)compoundPropertyTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCompoundPropertyType_Type() {
		return (EAttribute)compoundPropertyTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConditionalSourceGen() {
		return conditionalSourceGenEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConditionalSourceGen_Forms() {
		return (EAttribute)conditionalSourceGenEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConditionalSourceGen_IfEvents() {
		return (EAttribute)conditionalSourceGenEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConditionalSourceGen_IfExpr() {
		return (EAttribute)conditionalSourceGenEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConditionalSourceGenString() {
		return conditionalSourceGenStringEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConditionalSourceGenString_Value() {
		return (EAttribute)conditionalSourceGenStringEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConditionalSourceGenString_Forms() {
		return (EAttribute)conditionalSourceGenStringEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConditionalSourceGenString_IfEvents() {
		return (EAttribute)conditionalSourceGenStringEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConditionalSourceGenString_IfExpr() {
		return (EAttribute)conditionalSourceGenStringEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getDefineLocationType() {
		return defineLocationTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getDefineLocationType_Group() {
		return (EAttribute)defineLocationTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDefineLocationType_Template() {
		return (EReference)defineLocationTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDefineLocationType_Inline() {
		return (EReference)defineLocationTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDefineLocationType_Script() {
		return (EReference)defineLocationTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDefineLocationType_ExpandMacro() {
		return (EReference)defineLocationTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getDefineLocationType_BaseLocation() {
		return (EAttribute)defineLocationTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getDefineLocationType_Dir() {
		return (EAttribute)defineLocationTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getDefineLocationType_Domain() {
		return (EAttribute)defineLocationTypeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getDefineLocationType_File() {
		return (EAttribute)defineLocationTypeEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getDefineLocationType_Filter() {
		return (EAttribute)defineLocationTypeEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getDefineLocationType_Id() {
		return (EAttribute)defineLocationTypeEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDefineLocationType_IfEvents() {
		return (EAttribute)defineLocationTypeEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDefineLocationType_IsEventHandler() {
		return (EAttribute)defineLocationTypeEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getDefineLocationType_Location() {
		return (EAttribute)defineLocationTypeEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getDefineLocationType_Owned() {
		return (EAttribute)defineLocationTypeEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDefineLocationType_Realize() {
		return (EAttribute)defineLocationTypeEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDefineMacroType() {
		return defineMacroTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDefineMacroType_ImportArguments() {
		return (EReference)defineMacroTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDefineMacroType_MacroArgument() {
		return (EReference)defineMacroTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDefineMacroType_Group() {
		return (EAttribute)defineMacroTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDefineMacroType_Template() {
		return (EReference)defineMacroTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDefineMacroType_Inline() {
		return (EReference)defineMacroTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDefineMacroType_DefineLocation() {
		return (EReference)defineMacroTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDefineMacroType_ExpandMacro() {
		return (EReference)defineMacroTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDefineMacroType_Help() {
		return (EAttribute)defineMacroTypeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDefineMacroType_Id() {
		return (EAttribute)defineMacroTypeEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDesignerImagesType() {
		return designerImagesTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getDesignerImagesType_LargeIconFile() {
		return (EAttribute)designerImagesTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getDesignerImagesType_LayoutImageFile() {
		return (EAttribute)designerImagesTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getDesignerImagesType_SmallIconFile() {
		return (EAttribute)designerImagesTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDesignerImagesType_ThumbnailFile() {
		return (EAttribute)designerImagesTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDocumentationType() {
		return documentationTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentationType_Information() {
		return (EAttribute)documentationTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentationType_HelpTopic() {
		return (EAttribute)documentationTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentationType_WizardDescription() {
		return (EAttribute)documentationTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDocumentRoot() {
		return documentRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_Mixed() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_XMLNSPrefixMap() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_XSISchemaLocation() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_AbstractProperty() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_ArrayProperty() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_Attribute() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Attributes() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_Choice() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_Code() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Component() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_ComponentDefinition() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_ComponentReferenceProperty() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_CompoundProperty() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_CompoundPropertyDeclaration() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_DefineLocation() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_DefineMacro() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_DesignerImages() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Documentation() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_EnumProperty() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_EnumPropertyDeclaration() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Event() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(20);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Events() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(21);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_ExpandArgument() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(22);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_ExpandMacro() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(23);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_ExtensionProperties() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(24);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Implementation() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(25);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Implementations() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(26);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_ImportArguments() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(27);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_Inline() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(28);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_MacroArgument() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(29);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_MapArrayMember() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(30);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_MapEnum() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(38);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_MapEnumElement() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(39);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_TwoWayMapping() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(31);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_MapArrayType() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(32);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_MapBitmaskElement() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(33);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_MapBitmaskMember() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(34);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_MapBitmaskType() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(35);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_MapBitmaskValue() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(36);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_MapElementFromType() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(37);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_MapEnumMember() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(40);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_MapEnumType() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(41);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_MapFixedElement() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(42);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_MapFixedMember() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(43);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_MapFixedType() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(44);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_MapIdentifierElement() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(45);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_MapIdentifierMember() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(46);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_MapIdentifierType() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(47);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_MapInstanceElement() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(48);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_MapInstanceMember() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(49);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_MapInstanceType() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(50);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_MapIntoProperty() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(51);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_MapMemberFromType() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(52);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_MapReferenceElement() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(53);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_MapReferenceMember() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(54);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_MapReferenceType() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(55);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_MapResource() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(56);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_MapResourceElement() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(57);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_MapResourceMember() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(58);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_MapResourceType() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(59);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_MapSimpleElement() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(60);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_MapSimpleMember() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(61);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_MapSimpleType() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(62);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Properties() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(63);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Property() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(64);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_PropertyOverrides() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(65);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_Script() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(66);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_Select() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(67);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_SourceGen() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(68);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_SourceMapping() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(69);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_SourceTypeMapping() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(70);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Symbian() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(71);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_Template() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(72);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_TemplateGroup() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(73);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_UseTemplate() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(74);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_UseTemplateGroup() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(75);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEnumElementType() {
		return enumElementTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getEnumElementType_DisplayValue() {
		return (EAttribute)enumElementTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getEnumElementType_Value() {
		return (EAttribute)enumElementTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEnumPropertyDeclarationType() {
		return enumPropertyDeclarationTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEnumPropertyDeclarationType_EnumElement() {
		return (EReference)enumPropertyDeclarationTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEnumPropertyDeclarationType_SourceTypeMapping() {
		return (EReference)enumPropertyDeclarationTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getEnumPropertyDeclarationType_DefaultValue() {
		return (EAttribute)enumPropertyDeclarationTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getEnumPropertyDeclarationType_QualifiedName() {
		return (EAttribute)enumPropertyDeclarationTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEnumPropertyType() {
		return enumPropertyTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnumPropertyType_Default() {
		return (EAttribute)enumPropertyTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnumPropertyType_ExtendWithEnum() {
		return (EAttribute)enumPropertyTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnumPropertyType_Type() {
		return (EAttribute)enumPropertyTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEventsType() {
		return eventsTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEventsType_Event() {
		return (EReference)eventsTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEventsType_DefaultEventName() {
		return (EAttribute)eventsTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEventType() {
		return eventTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEventType_Category() {
		return (EAttribute)eventTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEventType_DescriptionKey() {
		return (EAttribute)eventTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getEventType_DisplayName() {
		return (EAttribute)eventTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEventType_Group() {
		return (EAttribute)eventTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEventType_HandlerNameTemplate() {
		return (EAttribute)eventTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEventType_HelpKey() {
		return (EAttribute)eventTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getEventType_Name() {
		return (EAttribute)eventTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExpandArgumentType() {
		return expandArgumentTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExpandArgumentType_Value() {
		return (EAttribute)expandArgumentTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExpandArgumentType_Help() {
		return (EAttribute)expandArgumentTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExpandArgumentType_Name() {
		return (EAttribute)expandArgumentTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExpandMacroType() {
		return expandMacroTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExpandMacroType_ExpandArgument() {
		return (EReference)expandMacroTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExpandMacroType_DontPassArguments() {
		return (EAttribute)expandMacroTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExpandMacroType_Help() {
		return (EAttribute)expandMacroTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExpandMacroType_Name() {
		return (EAttribute)expandMacroTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExpandMacroType_PassArguments() {
		return (EAttribute)expandMacroTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExpandMacroType_AnyAttribute() {
		return (EAttribute)expandMacroTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExtensionPropertiesType() {
		return extensionPropertiesTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExtensionPropertiesType_AbstractPropertyGroup() {
		return (EAttribute)extensionPropertiesTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExtensionPropertiesType_AbstractProperty() {
		return (EReference)extensionPropertiesTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExtensionPropertiesType_Name() {
		return (EAttribute)extensionPropertiesTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getImplementationsType() {
		return implementationsTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getImplementationsType_Implementation() {
		return (EReference)implementationsTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getImplementationType() {
		return implementationTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getImplementationType_Interface() {
		return (EReference)implementationTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getImplementationType_Code() {
		return (EReference)implementationTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getImplementationType_Script() {
		return (EReference)implementationTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getImportArgumentsType() {
		return importArgumentsTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImportArgumentsType_Arguments() {
		return (EAttribute)importArgumentsTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImportArgumentsType_ExceptArguments() {
		return (EAttribute)importArgumentsTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImportArgumentsType_Help() {
		return (EAttribute)importArgumentsTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImportArgumentsType_MacroName() {
		return (EAttribute)importArgumentsTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPropertiesType() {
		return propertiesTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertiesType_AbstractPropertyGroup() {
		return (EAttribute)propertiesTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPropertiesType_AbstractProperty() {
		return (EReference)propertiesTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPropertyOverridesType() {
		return propertyOverridesTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPropertyOverridesType_PropertyOverride() {
		return (EReference)propertyOverridesTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPropertyOverrideType() {
		return propertyOverrideTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyOverrideType_Category() {
		return (EAttribute)propertyOverrideTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyOverrideType_Default() {
		return (EAttribute)propertyOverrideTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyOverrideType_Name() {
		return (EAttribute)propertyOverrideTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyOverrideType_ReadOnly() {
		return (EAttribute)propertyOverrideTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getScriptType() {
		return scriptTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getScriptType_File() {
		return (EAttribute)scriptTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getScriptType_Prototype() {
		return (EAttribute)scriptTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getSelectType() {
		return selectTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getSelectType_Choice() {
		return (EReference)selectTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getSelectType_Attribute() {
		return (EAttribute)selectTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSelectType_IsComponentInstanceOf() {
		return (EAttribute)selectTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getSelectType_Property() {
		return (EAttribute)selectTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSelectType_PropertyExists() {
		return (EAttribute)selectTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSimplePropertyType() {
		return simplePropertyTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimplePropertyType_Default() {
		return (EAttribute)simplePropertyTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimplePropertyType_ExtendWithEnum() {
		return (EAttribute)simplePropertyTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimplePropertyType_MaxValue() {
		return (EAttribute)simplePropertyTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimplePropertyType_MinValue() {
		return (EAttribute)simplePropertyTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimplePropertyType_Type() {
		return (EAttribute)simplePropertyTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getSourceGenType() {
		return sourceGenTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getSourceGenType_DefineLocation() {
		return (EReference)sourceGenTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getSourceGenType_Group() {
		return (EAttribute)sourceGenTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getSourceGenType_Template() {
		return (EReference)sourceGenTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getSourceGenType_TemplateGroup() {
		return (EReference)sourceGenTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getSourceGenType_UseTemplate() {
		return (EReference)sourceGenTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getSourceGenType_UseTemplateGroup() {
		return (EReference)sourceGenTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getSourceGenType_Inline() {
		return (EReference)sourceGenTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getSourceGenType_Script() {
		return (EReference)sourceGenTypeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSourceGenType_DefineMacro() {
		return (EReference)sourceGenTypeEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSourceGenType_ExpandMacro() {
		return (EReference)sourceGenTypeEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getSourceGenType_Debug() {
		return (EAttribute)sourceGenTypeEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getSourceGenType_Forms() {
		return (EAttribute)sourceGenTypeEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getSourceMappingType() {
		return sourceMappingTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getSourceMappingType_Group() {
		return (EAttribute)sourceMappingTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getSourceMappingType_MapResource() {
		return (EReference)sourceMappingTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getSourceMappingType_Select() {
		return (EReference)sourceMappingTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSourceTypeMappingType() {
		return sourceTypeMappingTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSourceTypeMappingType_Group() {
		return (EAttribute)sourceTypeMappingTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSourceTypeMappingType_MapResourceType() {
		return (EReference)sourceTypeMappingTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSourceTypeMappingType_MapEnumType() {
		return (EReference)sourceTypeMappingTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSourceTypeMappingType_MapSimpleType() {
		return (EReference)sourceTypeMappingTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSourceTypeMappingType_MapFixedType() {
		return (EReference)sourceTypeMappingTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSourceTypeMappingType_MapBitmaskType() {
		return (EReference)sourceTypeMappingTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSourceTypeMappingType_MapIdentifierType() {
		return (EReference)sourceTypeMappingTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSourceTypeMappingType_MapReferenceType() {
		return (EReference)sourceTypeMappingTypeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSourceTypeMappingType_MapArrayType() {
		return (EReference)sourceTypeMappingTypeEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSourceTypeMappingType_Select() {
		return (EReference)sourceTypeMappingTypeEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSymbianType() {
		return symbianTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getSymbianType_ClassHelpTopic() {
		return (EAttribute)symbianTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getSymbianType_ClassName() {
		return (EAttribute)symbianTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getSymbianType_MaxSDKVersion() {
		return (EAttribute)symbianTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getSymbianType_MinSDKVersion() {
		return (EAttribute)symbianTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getSymbianType_ResourceHelpTopic() {
		return (EAttribute)symbianTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getSymbianType_ResourceType() {
		return (EAttribute)symbianTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getSymbianType_SdkName() {
		return (EAttribute)symbianTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getTemplateGroupType() {
		return templateGroupTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTemplateGroupType_Group() {
		return (EAttribute)templateGroupTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTemplateGroupType_DefineLocation() {
		return (EReference)templateGroupTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getTemplateGroupType_Template() {
		return (EReference)templateGroupTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTemplateGroupType_Inline() {
		return (EReference)templateGroupTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTemplateGroupType_UseTemplate() {
		return (EReference)templateGroupTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTemplateGroupType_UseTemplateGroup() {
		return (EReference)templateGroupTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTemplateGroupType_ExpandMacro() {
		return (EReference)templateGroupTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getTemplateGroupType_Form() {
		return (EAttribute)templateGroupTypeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getTemplateGroupType_Id() {
		return (EAttribute)templateGroupTypeEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getTemplateGroupType_Location() {
		return (EAttribute)templateGroupTypeEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getTemplateGroupType_Mode() {
		return (EAttribute)templateGroupTypeEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getTemplateGroupType_Phase() {
		return (EAttribute)templateGroupTypeEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getTemplateType() {
		return templateTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getTemplateType_Form() {
		return (EAttribute)templateTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getTemplateType_Id() {
		return (EAttribute)templateTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getTemplateType_Location() {
		return (EAttribute)templateTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getTemplateType_Mode() {
		return (EAttribute)templateTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getTemplateType_Phase() {
		return (EAttribute)templateTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getTwoWayMappingType() {
		return twoWayMappingTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getUseTemplateGroupType() {
		return useTemplateGroupTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getUseTemplateGroupType_UseTemplate() {
		return (EReference)useTemplateGroupTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getUseTemplateGroupType_Ids() {
		return (EAttribute)useTemplateGroupTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getUseTemplateType() {
		return useTemplateTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getUseTemplateType_Ids() {
		return (EAttribute)useTemplateTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getPropertyDataType() {
		return propertyDataTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getReferenceScopeType() {
		return referenceScopeTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getStandaloneType() {
		return standaloneTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getListOfStrings() {
		return listOfStringsEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getInlineType() {
		return inlineTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInlineType_Id() {
		return (EAttribute)inlineTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getInlineType_Scope() {
		return (EAttribute)inlineTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInterfaceType() {
		return interfaceTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInterfaceType_Id() {
		return (EAttribute)interfaceTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMacroArgumentType() {
		return macroArgumentTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMacroArgumentType_Value() {
		return (EAttribute)macroArgumentTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMacroArgumentType_Default() {
		return (EAttribute)macroArgumentTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMacroArgumentType_Help() {
		return (EAttribute)macroArgumentTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMacroArgumentType_Name() {
		return (EAttribute)macroArgumentTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMacroArgumentType_Optional() {
		return (EAttribute)macroArgumentTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getMapArrayMemberType() {
		return mapArrayMemberTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMapArrayMemberType_Member() {
		return (EAttribute)mapArrayMemberTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMapArrayMemberType_Property() {
		return (EAttribute)mapArrayMemberTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapArrayMemberType_SuppressDefault() {
		return (EAttribute)mapArrayMemberTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMapArrayTypeType() {
		return mapArrayTypeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapArrayTypeType_TypeId() {
		return (EAttribute)mapArrayTypeTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getMapBitmaskElementType() {
		return mapBitmaskElementTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getMapBitmaskMemberType() {
		return mapBitmaskMemberTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMapBitmaskMemberType_Member() {
		return (EAttribute)mapBitmaskMemberTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMapBitmaskMemberType_Property() {
		return (EAttribute)mapBitmaskMemberTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapBitmaskMemberType_SuppressDefault() {
		return (EAttribute)mapBitmaskMemberTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMapBitmaskTypeType() {
		return mapBitmaskTypeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapBitmaskTypeType_TypeId() {
		return (EAttribute)mapBitmaskTypeTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getMapBitmaskValueType() {
		return mapBitmaskValueTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMapBitmaskValueType_Properties() {
		return (EAttribute)mapBitmaskValueTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMapBitmaskValueType_Value() {
		return (EAttribute)mapBitmaskValueTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMapElementFromTypeType() {
		return mapElementFromTypeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapElementFromTypeType_TypeId() {
		return (EAttribute)mapElementFromTypeTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getMapEnumElementType() {
		return mapEnumElementTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getMapEnumMemberType() {
		return mapEnumMemberTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMapEnumMemberType_Member() {
		return (EAttribute)mapEnumMemberTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMapEnumMemberType_Property() {
		return (EAttribute)mapEnumMemberTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapEnumMemberType_SuppressDefault() {
		return (EAttribute)mapEnumMemberTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getMapEnumType() {
		return mapEnumTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMapEnumType_Enumerator() {
		return (EAttribute)mapEnumTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMapEnumType_Value() {
		return (EAttribute)mapEnumTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMapEnumTypeType() {
		return mapEnumTypeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapEnumTypeType_TypeId() {
		return (EAttribute)mapEnumTypeTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMapFixedElementType() {
		return mapFixedElementTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getMapFixedMemberType() {
		return mapFixedMemberTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMapFixedMemberType_Member() {
		return (EAttribute)mapFixedMemberTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapFixedMemberType_SuppressDefault() {
		return (EAttribute)mapFixedMemberTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMapFixedTypeType() {
		return mapFixedTypeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapFixedTypeType_TypeId() {
		return (EAttribute)mapFixedTypeTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getMapIdentifierElementType() {
		return mapIdentifierElementTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getMapIdentifierMemberType() {
		return mapIdentifierMemberTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMapIdentifierMemberType_Member() {
		return (EAttribute)mapIdentifierMemberTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMapIdentifierMemberType_Property() {
		return (EAttribute)mapIdentifierMemberTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapIdentifierMemberType_SuppressDefault() {
		return (EAttribute)mapIdentifierMemberTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMapIdentifierTypeType() {
		return mapIdentifierTypeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapIdentifierTypeType_TypeId() {
		return (EAttribute)mapIdentifierTypeTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getMapInstanceElementType() {
		return mapInstanceElementTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getMapInstanceMemberType() {
		return mapInstanceMemberTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMapInstanceMemberType_Member() {
		return (EAttribute)mapInstanceMemberTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMapInstanceMemberType_Property() {
		return (EAttribute)mapInstanceMemberTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapInstanceMemberType_SuppressDefault() {
		return (EAttribute)mapInstanceMemberTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMapInstanceTypeType() {
		return mapInstanceTypeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapInstanceTypeType_TypeId() {
		return (EAttribute)mapInstanceTypeTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMapIntoPropertyType() {
		return mapIntoPropertyTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapIntoPropertyType_TwoWayMappingGroup() {
		return (EAttribute)mapIntoPropertyTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMapIntoPropertyType_TwoWayMapping() {
		return (EReference)mapIntoPropertyTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapIntoPropertyType_Property() {
		return (EAttribute)mapIntoPropertyTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMapMemberFromTypeType() {
		return mapMemberFromTypeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapMemberFromTypeType_Member() {
		return (EAttribute)mapMemberFromTypeTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapMemberFromTypeType_Property() {
		return (EAttribute)mapMemberFromTypeTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapMemberFromTypeType_SuppressDefault() {
		return (EAttribute)mapMemberFromTypeTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapMemberFromTypeType_TypeId() {
		return (EAttribute)mapMemberFromTypeTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getMappingArrayType() {
		return mappingArrayTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMappingArrayType_TwoWayMappingGroup() {
		return (EAttribute)mappingArrayTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getMappingArrayType_TwoWayMapping() {
		return (EReference)mappingArrayTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getMappingArrayType_Select() {
		return (EReference)mappingArrayTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getMappingBitmaskType() {
		return mappingBitmaskTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMappingBitmaskType_Group() {
		return (EAttribute)mappingBitmaskTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getMappingBitmaskType_MapBitmaskValue() {
		return (EReference)mappingBitmaskTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMappingBitmaskType_IncludedProperties() {
		return (EAttribute)mappingBitmaskTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getMappingEnumType() {
		return mappingEnumTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMappingEnumType_Group() {
		return (EAttribute)mappingEnumTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getMappingEnumType_MapEnum() {
		return (EReference)mappingEnumTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMappingEnumType_Enumeration() {
		return (EAttribute)mappingEnumTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMappingEnumType_Headers() {
		return (EAttribute)mappingEnumTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMappingEnumType_NameAlgorithm() {
		return (EAttribute)mappingEnumTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMappingEnumType_UniqueValue() {
		return (EAttribute)mappingEnumTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMappingEnumType_Validate() {
		return (EAttribute)mappingEnumTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getMappingFixedType() {
		return mappingFixedTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMappingFixedType_Value() {
		return (EAttribute)mappingFixedTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getMappingIdentifierType() {
		return mappingIdentifierTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getMappingInstanceType() {
		return mappingInstanceTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMappingInstanceType_RsrcId() {
		return (EAttribute)mappingInstanceTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getMappingReferenceType() {
		return mappingReferenceTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMappingReferenceType_RsrcId() {
		return (EAttribute)mappingReferenceTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getMappingResourceType() {
		return mappingResourceTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMappingResourceType_Group() {
		return (EAttribute)mappingResourceTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getMappingResourceType_MapSimpleMember() {
		return (EReference)mappingResourceTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getMappingResourceType_MapInstanceMember() {
		return (EReference)mappingResourceTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getMappingResourceType_MapReferenceMember() {
		return (EReference)mappingResourceTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getMappingResourceType_MapFixedMember() {
		return (EReference)mappingResourceTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getMappingResourceType_MapEnumMember() {
		return (EReference)mappingResourceTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getMappingResourceType_MapIdentifierMember() {
		return (EReference)mappingResourceTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getMappingResourceType_MapArrayMember() {
		return (EReference)mappingResourceTypeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getMappingResourceType_MapResourceMember() {
		return (EReference)mappingResourceTypeEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getMappingResourceType_MapBitmaskMember() {
		return (EReference)mappingResourceTypeEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMappingResourceType_MapMemberFromType() {
		return (EReference)mappingResourceTypeEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMappingResourceType_MapIntoProperty() {
		return (EReference)mappingResourceTypeEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getMappingResourceType_Select() {
		return (EReference)mappingResourceTypeEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMappingResourceType_Headers() {
		return (EAttribute)mappingResourceTypeEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMappingResourceType_Id() {
		return (EAttribute)mappingResourceTypeEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMappingResourceType_Struct() {
		return (EAttribute)mappingResourceTypeEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getMappingSimpleType() {
		return mappingSimpleTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getMapReferenceElementType() {
		return mapReferenceElementTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getMapReferenceMemberType() {
		return mapReferenceMemberTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMapReferenceMemberType_Member() {
		return (EAttribute)mapReferenceMemberTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMapReferenceMemberType_Property() {
		return (EAttribute)mapReferenceMemberTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapReferenceMemberType_SuppressDefault() {
		return (EAttribute)mapReferenceMemberTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMapReferenceTypeType() {
		return mapReferenceTypeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapReferenceTypeType_TypeId() {
		return (EAttribute)mapReferenceTypeTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getMapResourceElementType() {
		return mapResourceElementTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapResourceElementType_InstanceIdentifyingMember() {
		return (EAttribute)mapResourceElementTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getMapResourceMemberType() {
		return mapResourceMemberTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMapResourceMemberType_Member() {
		return (EAttribute)mapResourceMemberTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMapResourceMemberType_Property() {
		return (EAttribute)mapResourceMemberTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapResourceMemberType_SuppressDefault() {
		return (EAttribute)mapResourceMemberTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getMapResourceType() {
		return mapResourceTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapResourceType_BaseName() {
		return (EAttribute)mapResourceTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapResourceType_RssFile() {
		return (EAttribute)mapResourceTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMapResourceType_Standalone() {
		return (EAttribute)mapResourceTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMapResourceType_Unnamed() {
		return (EAttribute)mapResourceTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMapResourceTypeType() {
		return mapResourceTypeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapResourceTypeType_TypeId() {
		return (EAttribute)mapResourceTypeTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getMapSimpleElementType() {
		return mapSimpleElementTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getMapSimpleMemberType() {
		return mapSimpleMemberTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMapSimpleMemberType_Member() {
		return (EAttribute)mapSimpleMemberTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getMapSimpleMemberType_Property() {
		return (EAttribute)mapSimpleMemberTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapSimpleMemberType_SuppressDefault() {
		return (EAttribute)mapSimpleMemberTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMapSimpleTypeType() {
		return mapSimpleTypeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapSimpleTypeType_TypeId() {
		return (EAttribute)mapSimpleTypeTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getPropertyDataTypeObject() {
		return propertyDataTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getReferenceScopeTypeObject() {
		return referenceScopeTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getStandaloneTypeObject() {
		return standaloneTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentFactory getComponentFactory() {
		return (ComponentFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		abstractPropertyTypeEClass = createEClass(ABSTRACT_PROPERTY_TYPE);
		createEAttribute(abstractPropertyTypeEClass, ABSTRACT_PROPERTY_TYPE__CATEGORY);
		createEAttribute(abstractPropertyTypeEClass, ABSTRACT_PROPERTY_TYPE__DESCRIPTION_KEY);
		createEAttribute(abstractPropertyTypeEClass, ABSTRACT_PROPERTY_TYPE__DISPLAY_NAME);
		createEAttribute(abstractPropertyTypeEClass, ABSTRACT_PROPERTY_TYPE__EDITOR_CLASS);
		createEAttribute(abstractPropertyTypeEClass, ABSTRACT_PROPERTY_TYPE__HELP_KEY);
		createEAttribute(abstractPropertyTypeEClass, ABSTRACT_PROPERTY_TYPE__NAME);
		createEAttribute(abstractPropertyTypeEClass, ABSTRACT_PROPERTY_TYPE__READ_ONLY);
		createEAttribute(abstractPropertyTypeEClass, ABSTRACT_PROPERTY_TYPE__RESETTABLE);

		arrayPropertyTypeEClass = createEClass(ARRAY_PROPERTY_TYPE);
		createEAttribute(arrayPropertyTypeEClass, ARRAY_PROPERTY_TYPE__TYPE);

		attributesTypeEClass = createEClass(ATTRIBUTES_TYPE);
		createEReference(attributesTypeEClass, ATTRIBUTES_TYPE__ATTRIBUTE);

		attributeTypeEClass = createEClass(ATTRIBUTE_TYPE);
		createEAttribute(attributeTypeEClass, ATTRIBUTE_TYPE__VALUE);
		createEAttribute(attributeTypeEClass, ATTRIBUTE_TYPE__KEY);

		choiceTypeEClass = createEClass(CHOICE_TYPE);
		createEAttribute(choiceTypeEClass, CHOICE_TYPE__GROUP);
		createEAttribute(choiceTypeEClass, CHOICE_TYPE__TWO_WAY_MAPPING_GROUP);
		createEReference(choiceTypeEClass, CHOICE_TYPE__TWO_WAY_MAPPING);
		createEReference(choiceTypeEClass, CHOICE_TYPE__MAP_RESOURCE);
		createEReference(choiceTypeEClass, CHOICE_TYPE__SELECT);
		createEAttribute(choiceTypeEClass, CHOICE_TYPE__VALUE);

		codeTypeEClass = createEClass(CODE_TYPE);
		createEAttribute(codeTypeEClass, CODE_TYPE__CLASS);
		createEAttribute(codeTypeEClass, CODE_TYPE__PLUGIN);

		componentDefinitionTypeEClass = createEClass(COMPONENT_DEFINITION_TYPE);
		createEReference(componentDefinitionTypeEClass, COMPONENT_DEFINITION_TYPE__COMPOUND_PROPERTY_DECLARATION);
		createEReference(componentDefinitionTypeEClass, COMPONENT_DEFINITION_TYPE__ENUM_PROPERTY_DECLARATION);
		createEReference(componentDefinitionTypeEClass, COMPONENT_DEFINITION_TYPE__COMPONENT);

		componentReferencePropertyTypeEClass = createEClass(COMPONENT_REFERENCE_PROPERTY_TYPE);
		createEAttribute(componentReferencePropertyTypeEClass, COMPONENT_REFERENCE_PROPERTY_TYPE__CONSTRAINT);
		createEAttribute(componentReferencePropertyTypeEClass, COMPONENT_REFERENCE_PROPERTY_TYPE__CREATION_KEYS);
		createEAttribute(componentReferencePropertyTypeEClass, COMPONENT_REFERENCE_PROPERTY_TYPE__PROMOTE_REFERENCE_PROPERTIES);
		createEAttribute(componentReferencePropertyTypeEClass, COMPONENT_REFERENCE_PROPERTY_TYPE__SCOPE);

		componentTypeEClass = createEClass(COMPONENT_TYPE);
		createEReference(componentTypeEClass, COMPONENT_TYPE__DOCUMENTATION);
		createEReference(componentTypeEClass, COMPONENT_TYPE__SYMBIAN);
		createEReference(componentTypeEClass, COMPONENT_TYPE__DESIGNER_IMAGES);
		createEReference(componentTypeEClass, COMPONENT_TYPE__ATTRIBUTES);
		createEReference(componentTypeEClass, COMPONENT_TYPE__PROPERTIES);
		createEReference(componentTypeEClass, COMPONENT_TYPE__EXTENSION_PROPERTIES);
		createEReference(componentTypeEClass, COMPONENT_TYPE__PROPERTY_OVERRIDES);
		createEReference(componentTypeEClass, COMPONENT_TYPE__EVENTS);
		createEReference(componentTypeEClass, COMPONENT_TYPE__SOURCE_GEN);
		createEReference(componentTypeEClass, COMPONENT_TYPE__SOURCE_MAPPING);
		createEReference(componentTypeEClass, COMPONENT_TYPE__IMPLEMENTATIONS);
		createEAttribute(componentTypeEClass, COMPONENT_TYPE__ABSTRACT);
		createEAttribute(componentTypeEClass, COMPONENT_TYPE__BASE_COMPONENT);
		createEAttribute(componentTypeEClass, COMPONENT_TYPE__CATEGORY);
		createEAttribute(componentTypeEClass, COMPONENT_TYPE__FRIENDLY_NAME);
		createEAttribute(componentTypeEClass, COMPONENT_TYPE__INSTANCE_NAME_ROOT);
		createEAttribute(componentTypeEClass, COMPONENT_TYPE__QUALIFIED_NAME);
		createEAttribute(componentTypeEClass, COMPONENT_TYPE__VERSION);

		compoundPropertyDeclarationTypeEClass = createEClass(COMPOUND_PROPERTY_DECLARATION_TYPE);
		createEAttribute(compoundPropertyDeclarationTypeEClass, COMPOUND_PROPERTY_DECLARATION_TYPE__ABSTRACT_PROPERTY_GROUP);
		createEReference(compoundPropertyDeclarationTypeEClass, COMPOUND_PROPERTY_DECLARATION_TYPE__ABSTRACT_PROPERTY);
		createEReference(compoundPropertyDeclarationTypeEClass, COMPOUND_PROPERTY_DECLARATION_TYPE__SOURCE_TYPE_MAPPING);
		createEAttribute(compoundPropertyDeclarationTypeEClass, COMPOUND_PROPERTY_DECLARATION_TYPE__CONVERTER_CLASS);
		createEAttribute(compoundPropertyDeclarationTypeEClass, COMPOUND_PROPERTY_DECLARATION_TYPE__EDITABLE_TYPE);
		createEAttribute(compoundPropertyDeclarationTypeEClass, COMPOUND_PROPERTY_DECLARATION_TYPE__EDITOR_CLASS);
		createEAttribute(compoundPropertyDeclarationTypeEClass, COMPOUND_PROPERTY_DECLARATION_TYPE__QUALIFIED_NAME);

		compoundPropertyTypeEClass = createEClass(COMPOUND_PROPERTY_TYPE);
		createEAttribute(compoundPropertyTypeEClass, COMPOUND_PROPERTY_TYPE__DEFAULT);
		createEAttribute(compoundPropertyTypeEClass, COMPOUND_PROPERTY_TYPE__TYPE);

		conditionalSourceGenEClass = createEClass(CONDITIONAL_SOURCE_GEN);
		createEAttribute(conditionalSourceGenEClass, CONDITIONAL_SOURCE_GEN__FORMS);
		createEAttribute(conditionalSourceGenEClass, CONDITIONAL_SOURCE_GEN__IF_EVENTS);
		createEAttribute(conditionalSourceGenEClass, CONDITIONAL_SOURCE_GEN__IF_EXPR);

		conditionalSourceGenStringEClass = createEClass(CONDITIONAL_SOURCE_GEN_STRING);
		createEAttribute(conditionalSourceGenStringEClass, CONDITIONAL_SOURCE_GEN_STRING__VALUE);
		createEAttribute(conditionalSourceGenStringEClass, CONDITIONAL_SOURCE_GEN_STRING__FORMS);
		createEAttribute(conditionalSourceGenStringEClass, CONDITIONAL_SOURCE_GEN_STRING__IF_EVENTS);
		createEAttribute(conditionalSourceGenStringEClass, CONDITIONAL_SOURCE_GEN_STRING__IF_EXPR);

		defineLocationTypeEClass = createEClass(DEFINE_LOCATION_TYPE);
		createEAttribute(defineLocationTypeEClass, DEFINE_LOCATION_TYPE__GROUP);
		createEReference(defineLocationTypeEClass, DEFINE_LOCATION_TYPE__TEMPLATE);
		createEReference(defineLocationTypeEClass, DEFINE_LOCATION_TYPE__INLINE);
		createEReference(defineLocationTypeEClass, DEFINE_LOCATION_TYPE__SCRIPT);
		createEReference(defineLocationTypeEClass, DEFINE_LOCATION_TYPE__EXPAND_MACRO);
		createEAttribute(defineLocationTypeEClass, DEFINE_LOCATION_TYPE__BASE_LOCATION);
		createEAttribute(defineLocationTypeEClass, DEFINE_LOCATION_TYPE__DIR);
		createEAttribute(defineLocationTypeEClass, DEFINE_LOCATION_TYPE__DOMAIN);
		createEAttribute(defineLocationTypeEClass, DEFINE_LOCATION_TYPE__FILE);
		createEAttribute(defineLocationTypeEClass, DEFINE_LOCATION_TYPE__FILTER);
		createEAttribute(defineLocationTypeEClass, DEFINE_LOCATION_TYPE__ID);
		createEAttribute(defineLocationTypeEClass, DEFINE_LOCATION_TYPE__IF_EVENTS);
		createEAttribute(defineLocationTypeEClass, DEFINE_LOCATION_TYPE__IS_EVENT_HANDLER);
		createEAttribute(defineLocationTypeEClass, DEFINE_LOCATION_TYPE__LOCATION);
		createEAttribute(defineLocationTypeEClass, DEFINE_LOCATION_TYPE__OWNED);
		createEAttribute(defineLocationTypeEClass, DEFINE_LOCATION_TYPE__REALIZE);

		defineMacroTypeEClass = createEClass(DEFINE_MACRO_TYPE);
		createEReference(defineMacroTypeEClass, DEFINE_MACRO_TYPE__IMPORT_ARGUMENTS);
		createEReference(defineMacroTypeEClass, DEFINE_MACRO_TYPE__MACRO_ARGUMENT);
		createEAttribute(defineMacroTypeEClass, DEFINE_MACRO_TYPE__GROUP);
		createEReference(defineMacroTypeEClass, DEFINE_MACRO_TYPE__TEMPLATE);
		createEReference(defineMacroTypeEClass, DEFINE_MACRO_TYPE__INLINE);
		createEReference(defineMacroTypeEClass, DEFINE_MACRO_TYPE__DEFINE_LOCATION);
		createEReference(defineMacroTypeEClass, DEFINE_MACRO_TYPE__EXPAND_MACRO);
		createEAttribute(defineMacroTypeEClass, DEFINE_MACRO_TYPE__HELP);
		createEAttribute(defineMacroTypeEClass, DEFINE_MACRO_TYPE__ID);

		designerImagesTypeEClass = createEClass(DESIGNER_IMAGES_TYPE);
		createEAttribute(designerImagesTypeEClass, DESIGNER_IMAGES_TYPE__LARGE_ICON_FILE);
		createEAttribute(designerImagesTypeEClass, DESIGNER_IMAGES_TYPE__LAYOUT_IMAGE_FILE);
		createEAttribute(designerImagesTypeEClass, DESIGNER_IMAGES_TYPE__SMALL_ICON_FILE);
		createEAttribute(designerImagesTypeEClass, DESIGNER_IMAGES_TYPE__THUMBNAIL_FILE);

		documentationTypeEClass = createEClass(DOCUMENTATION_TYPE);
		createEAttribute(documentationTypeEClass, DOCUMENTATION_TYPE__INFORMATION);
		createEAttribute(documentationTypeEClass, DOCUMENTATION_TYPE__HELP_TOPIC);
		createEAttribute(documentationTypeEClass, DOCUMENTATION_TYPE__WIZARD_DESCRIPTION);

		documentRootEClass = createEClass(DOCUMENT_ROOT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ABSTRACT_PROPERTY);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ARRAY_PROPERTY);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ATTRIBUTE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ATTRIBUTES);
		createEReference(documentRootEClass, DOCUMENT_ROOT__CHOICE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__CODE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__COMPONENT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__COMPONENT_DEFINITION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__COMPONENT_REFERENCE_PROPERTY);
		createEReference(documentRootEClass, DOCUMENT_ROOT__COMPOUND_PROPERTY);
		createEReference(documentRootEClass, DOCUMENT_ROOT__COMPOUND_PROPERTY_DECLARATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__DEFINE_LOCATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__DEFINE_MACRO);
		createEReference(documentRootEClass, DOCUMENT_ROOT__DESIGNER_IMAGES);
		createEReference(documentRootEClass, DOCUMENT_ROOT__DOCUMENTATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ENUM_PROPERTY);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ENUM_PROPERTY_DECLARATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__EVENT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__EVENTS);
		createEReference(documentRootEClass, DOCUMENT_ROOT__EXPAND_ARGUMENT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__EXPAND_MACRO);
		createEReference(documentRootEClass, DOCUMENT_ROOT__EXTENSION_PROPERTIES);
		createEReference(documentRootEClass, DOCUMENT_ROOT__IMPLEMENTATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__IMPLEMENTATIONS);
		createEReference(documentRootEClass, DOCUMENT_ROOT__IMPORT_ARGUMENTS);
		createEReference(documentRootEClass, DOCUMENT_ROOT__INLINE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MACRO_ARGUMENT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_ARRAY_MEMBER);
		createEReference(documentRootEClass, DOCUMENT_ROOT__TWO_WAY_MAPPING);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_ARRAY_TYPE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_BITMASK_ELEMENT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_BITMASK_MEMBER);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_BITMASK_TYPE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_BITMASK_VALUE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_ELEMENT_FROM_TYPE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_ENUM);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_ENUM_ELEMENT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_ENUM_MEMBER);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_ENUM_TYPE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_FIXED_ELEMENT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_FIXED_MEMBER);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_FIXED_TYPE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_IDENTIFIER_ELEMENT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_IDENTIFIER_MEMBER);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_IDENTIFIER_TYPE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_INSTANCE_ELEMENT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_INSTANCE_MEMBER);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_INSTANCE_TYPE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_INTO_PROPERTY);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_MEMBER_FROM_TYPE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_REFERENCE_ELEMENT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_REFERENCE_MEMBER);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_REFERENCE_TYPE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_RESOURCE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_RESOURCE_ELEMENT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_RESOURCE_MEMBER);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_RESOURCE_TYPE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_SIMPLE_ELEMENT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_SIMPLE_MEMBER);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAP_SIMPLE_TYPE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__PROPERTIES);
		createEReference(documentRootEClass, DOCUMENT_ROOT__PROPERTY);
		createEReference(documentRootEClass, DOCUMENT_ROOT__PROPERTY_OVERRIDES);
		createEReference(documentRootEClass, DOCUMENT_ROOT__SCRIPT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__SELECT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__SOURCE_GEN);
		createEReference(documentRootEClass, DOCUMENT_ROOT__SOURCE_MAPPING);
		createEReference(documentRootEClass, DOCUMENT_ROOT__SOURCE_TYPE_MAPPING);
		createEReference(documentRootEClass, DOCUMENT_ROOT__SYMBIAN);
		createEReference(documentRootEClass, DOCUMENT_ROOT__TEMPLATE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__TEMPLATE_GROUP);
		createEReference(documentRootEClass, DOCUMENT_ROOT__USE_TEMPLATE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__USE_TEMPLATE_GROUP);

		enumElementTypeEClass = createEClass(ENUM_ELEMENT_TYPE);
		createEAttribute(enumElementTypeEClass, ENUM_ELEMENT_TYPE__DISPLAY_VALUE);
		createEAttribute(enumElementTypeEClass, ENUM_ELEMENT_TYPE__VALUE);

		enumPropertyDeclarationTypeEClass = createEClass(ENUM_PROPERTY_DECLARATION_TYPE);
		createEReference(enumPropertyDeclarationTypeEClass, ENUM_PROPERTY_DECLARATION_TYPE__ENUM_ELEMENT);
		createEReference(enumPropertyDeclarationTypeEClass, ENUM_PROPERTY_DECLARATION_TYPE__SOURCE_TYPE_MAPPING);
		createEAttribute(enumPropertyDeclarationTypeEClass, ENUM_PROPERTY_DECLARATION_TYPE__DEFAULT_VALUE);
		createEAttribute(enumPropertyDeclarationTypeEClass, ENUM_PROPERTY_DECLARATION_TYPE__QUALIFIED_NAME);

		enumPropertyTypeEClass = createEClass(ENUM_PROPERTY_TYPE);
		createEAttribute(enumPropertyTypeEClass, ENUM_PROPERTY_TYPE__DEFAULT);
		createEAttribute(enumPropertyTypeEClass, ENUM_PROPERTY_TYPE__EXTEND_WITH_ENUM);
		createEAttribute(enumPropertyTypeEClass, ENUM_PROPERTY_TYPE__TYPE);

		eventsTypeEClass = createEClass(EVENTS_TYPE);
		createEReference(eventsTypeEClass, EVENTS_TYPE__EVENT);
		createEAttribute(eventsTypeEClass, EVENTS_TYPE__DEFAULT_EVENT_NAME);

		eventTypeEClass = createEClass(EVENT_TYPE);
		createEAttribute(eventTypeEClass, EVENT_TYPE__CATEGORY);
		createEAttribute(eventTypeEClass, EVENT_TYPE__DESCRIPTION_KEY);
		createEAttribute(eventTypeEClass, EVENT_TYPE__DISPLAY_NAME);
		createEAttribute(eventTypeEClass, EVENT_TYPE__GROUP);
		createEAttribute(eventTypeEClass, EVENT_TYPE__HANDLER_NAME_TEMPLATE);
		createEAttribute(eventTypeEClass, EVENT_TYPE__HELP_KEY);
		createEAttribute(eventTypeEClass, EVENT_TYPE__NAME);

		expandArgumentTypeEClass = createEClass(EXPAND_ARGUMENT_TYPE);
		createEAttribute(expandArgumentTypeEClass, EXPAND_ARGUMENT_TYPE__VALUE);
		createEAttribute(expandArgumentTypeEClass, EXPAND_ARGUMENT_TYPE__HELP);
		createEAttribute(expandArgumentTypeEClass, EXPAND_ARGUMENT_TYPE__NAME);

		expandMacroTypeEClass = createEClass(EXPAND_MACRO_TYPE);
		createEReference(expandMacroTypeEClass, EXPAND_MACRO_TYPE__EXPAND_ARGUMENT);
		createEAttribute(expandMacroTypeEClass, EXPAND_MACRO_TYPE__DONT_PASS_ARGUMENTS);
		createEAttribute(expandMacroTypeEClass, EXPAND_MACRO_TYPE__HELP);
		createEAttribute(expandMacroTypeEClass, EXPAND_MACRO_TYPE__NAME);
		createEAttribute(expandMacroTypeEClass, EXPAND_MACRO_TYPE__PASS_ARGUMENTS);
		createEAttribute(expandMacroTypeEClass, EXPAND_MACRO_TYPE__ANY_ATTRIBUTE);

		extensionPropertiesTypeEClass = createEClass(EXTENSION_PROPERTIES_TYPE);
		createEAttribute(extensionPropertiesTypeEClass, EXTENSION_PROPERTIES_TYPE__ABSTRACT_PROPERTY_GROUP);
		createEReference(extensionPropertiesTypeEClass, EXTENSION_PROPERTIES_TYPE__ABSTRACT_PROPERTY);
		createEAttribute(extensionPropertiesTypeEClass, EXTENSION_PROPERTIES_TYPE__NAME);

		implementationsTypeEClass = createEClass(IMPLEMENTATIONS_TYPE);
		createEReference(implementationsTypeEClass, IMPLEMENTATIONS_TYPE__IMPLEMENTATION);

		implementationTypeEClass = createEClass(IMPLEMENTATION_TYPE);
		createEReference(implementationTypeEClass, IMPLEMENTATION_TYPE__INTERFACE);
		createEReference(implementationTypeEClass, IMPLEMENTATION_TYPE__CODE);
		createEReference(implementationTypeEClass, IMPLEMENTATION_TYPE__SCRIPT);

		importArgumentsTypeEClass = createEClass(IMPORT_ARGUMENTS_TYPE);
		createEAttribute(importArgumentsTypeEClass, IMPORT_ARGUMENTS_TYPE__ARGUMENTS);
		createEAttribute(importArgumentsTypeEClass, IMPORT_ARGUMENTS_TYPE__EXCEPT_ARGUMENTS);
		createEAttribute(importArgumentsTypeEClass, IMPORT_ARGUMENTS_TYPE__HELP);
		createEAttribute(importArgumentsTypeEClass, IMPORT_ARGUMENTS_TYPE__MACRO_NAME);

		inlineTypeEClass = createEClass(INLINE_TYPE);
		createEAttribute(inlineTypeEClass, INLINE_TYPE__ID);
		createEAttribute(inlineTypeEClass, INLINE_TYPE__SCOPE);

		interfaceTypeEClass = createEClass(INTERFACE_TYPE);
		createEAttribute(interfaceTypeEClass, INTERFACE_TYPE__ID);

		macroArgumentTypeEClass = createEClass(MACRO_ARGUMENT_TYPE);
		createEAttribute(macroArgumentTypeEClass, MACRO_ARGUMENT_TYPE__VALUE);
		createEAttribute(macroArgumentTypeEClass, MACRO_ARGUMENT_TYPE__DEFAULT);
		createEAttribute(macroArgumentTypeEClass, MACRO_ARGUMENT_TYPE__HELP);
		createEAttribute(macroArgumentTypeEClass, MACRO_ARGUMENT_TYPE__NAME);
		createEAttribute(macroArgumentTypeEClass, MACRO_ARGUMENT_TYPE__OPTIONAL);

		mapArrayMemberTypeEClass = createEClass(MAP_ARRAY_MEMBER_TYPE);
		createEAttribute(mapArrayMemberTypeEClass, MAP_ARRAY_MEMBER_TYPE__MEMBER);
		createEAttribute(mapArrayMemberTypeEClass, MAP_ARRAY_MEMBER_TYPE__PROPERTY);
		createEAttribute(mapArrayMemberTypeEClass, MAP_ARRAY_MEMBER_TYPE__SUPPRESS_DEFAULT);

		mapArrayTypeTypeEClass = createEClass(MAP_ARRAY_TYPE_TYPE);
		createEAttribute(mapArrayTypeTypeEClass, MAP_ARRAY_TYPE_TYPE__TYPE_ID);

		mapBitmaskElementTypeEClass = createEClass(MAP_BITMASK_ELEMENT_TYPE);

		mapBitmaskMemberTypeEClass = createEClass(MAP_BITMASK_MEMBER_TYPE);
		createEAttribute(mapBitmaskMemberTypeEClass, MAP_BITMASK_MEMBER_TYPE__MEMBER);
		createEAttribute(mapBitmaskMemberTypeEClass, MAP_BITMASK_MEMBER_TYPE__PROPERTY);
		createEAttribute(mapBitmaskMemberTypeEClass, MAP_BITMASK_MEMBER_TYPE__SUPPRESS_DEFAULT);

		mapBitmaskTypeTypeEClass = createEClass(MAP_BITMASK_TYPE_TYPE);
		createEAttribute(mapBitmaskTypeTypeEClass, MAP_BITMASK_TYPE_TYPE__TYPE_ID);

		mapBitmaskValueTypeEClass = createEClass(MAP_BITMASK_VALUE_TYPE);
		createEAttribute(mapBitmaskValueTypeEClass, MAP_BITMASK_VALUE_TYPE__PROPERTIES);
		createEAttribute(mapBitmaskValueTypeEClass, MAP_BITMASK_VALUE_TYPE__VALUE);

		mapElementFromTypeTypeEClass = createEClass(MAP_ELEMENT_FROM_TYPE_TYPE);
		createEAttribute(mapElementFromTypeTypeEClass, MAP_ELEMENT_FROM_TYPE_TYPE__TYPE_ID);

		mapEnumElementTypeEClass = createEClass(MAP_ENUM_ELEMENT_TYPE);

		mapEnumMemberTypeEClass = createEClass(MAP_ENUM_MEMBER_TYPE);
		createEAttribute(mapEnumMemberTypeEClass, MAP_ENUM_MEMBER_TYPE__MEMBER);
		createEAttribute(mapEnumMemberTypeEClass, MAP_ENUM_MEMBER_TYPE__PROPERTY);
		createEAttribute(mapEnumMemberTypeEClass, MAP_ENUM_MEMBER_TYPE__SUPPRESS_DEFAULT);

		mapEnumTypeEClass = createEClass(MAP_ENUM_TYPE);
		createEAttribute(mapEnumTypeEClass, MAP_ENUM_TYPE__ENUMERATOR);
		createEAttribute(mapEnumTypeEClass, MAP_ENUM_TYPE__VALUE);

		mapEnumTypeTypeEClass = createEClass(MAP_ENUM_TYPE_TYPE);
		createEAttribute(mapEnumTypeTypeEClass, MAP_ENUM_TYPE_TYPE__TYPE_ID);

		mapFixedElementTypeEClass = createEClass(MAP_FIXED_ELEMENT_TYPE);

		mapFixedMemberTypeEClass = createEClass(MAP_FIXED_MEMBER_TYPE);
		createEAttribute(mapFixedMemberTypeEClass, MAP_FIXED_MEMBER_TYPE__MEMBER);
		createEAttribute(mapFixedMemberTypeEClass, MAP_FIXED_MEMBER_TYPE__SUPPRESS_DEFAULT);

		mapFixedTypeTypeEClass = createEClass(MAP_FIXED_TYPE_TYPE);
		createEAttribute(mapFixedTypeTypeEClass, MAP_FIXED_TYPE_TYPE__TYPE_ID);

		mapIdentifierElementTypeEClass = createEClass(MAP_IDENTIFIER_ELEMENT_TYPE);

		mapIdentifierMemberTypeEClass = createEClass(MAP_IDENTIFIER_MEMBER_TYPE);
		createEAttribute(mapIdentifierMemberTypeEClass, MAP_IDENTIFIER_MEMBER_TYPE__MEMBER);
		createEAttribute(mapIdentifierMemberTypeEClass, MAP_IDENTIFIER_MEMBER_TYPE__PROPERTY);
		createEAttribute(mapIdentifierMemberTypeEClass, MAP_IDENTIFIER_MEMBER_TYPE__SUPPRESS_DEFAULT);

		mapIdentifierTypeTypeEClass = createEClass(MAP_IDENTIFIER_TYPE_TYPE);
		createEAttribute(mapIdentifierTypeTypeEClass, MAP_IDENTIFIER_TYPE_TYPE__TYPE_ID);

		mapInstanceElementTypeEClass = createEClass(MAP_INSTANCE_ELEMENT_TYPE);

		mapInstanceMemberTypeEClass = createEClass(MAP_INSTANCE_MEMBER_TYPE);
		createEAttribute(mapInstanceMemberTypeEClass, MAP_INSTANCE_MEMBER_TYPE__MEMBER);
		createEAttribute(mapInstanceMemberTypeEClass, MAP_INSTANCE_MEMBER_TYPE__PROPERTY);
		createEAttribute(mapInstanceMemberTypeEClass, MAP_INSTANCE_MEMBER_TYPE__SUPPRESS_DEFAULT);

		mapInstanceTypeTypeEClass = createEClass(MAP_INSTANCE_TYPE_TYPE);
		createEAttribute(mapInstanceTypeTypeEClass, MAP_INSTANCE_TYPE_TYPE__TYPE_ID);

		mapIntoPropertyTypeEClass = createEClass(MAP_INTO_PROPERTY_TYPE);
		createEAttribute(mapIntoPropertyTypeEClass, MAP_INTO_PROPERTY_TYPE__TWO_WAY_MAPPING_GROUP);
		createEReference(mapIntoPropertyTypeEClass, MAP_INTO_PROPERTY_TYPE__TWO_WAY_MAPPING);
		createEAttribute(mapIntoPropertyTypeEClass, MAP_INTO_PROPERTY_TYPE__PROPERTY);

		mapMemberFromTypeTypeEClass = createEClass(MAP_MEMBER_FROM_TYPE_TYPE);
		createEAttribute(mapMemberFromTypeTypeEClass, MAP_MEMBER_FROM_TYPE_TYPE__MEMBER);
		createEAttribute(mapMemberFromTypeTypeEClass, MAP_MEMBER_FROM_TYPE_TYPE__PROPERTY);
		createEAttribute(mapMemberFromTypeTypeEClass, MAP_MEMBER_FROM_TYPE_TYPE__SUPPRESS_DEFAULT);
		createEAttribute(mapMemberFromTypeTypeEClass, MAP_MEMBER_FROM_TYPE_TYPE__TYPE_ID);

		mappingArrayTypeEClass = createEClass(MAPPING_ARRAY_TYPE);
		createEAttribute(mappingArrayTypeEClass, MAPPING_ARRAY_TYPE__TWO_WAY_MAPPING_GROUP);
		createEReference(mappingArrayTypeEClass, MAPPING_ARRAY_TYPE__TWO_WAY_MAPPING);
		createEReference(mappingArrayTypeEClass, MAPPING_ARRAY_TYPE__SELECT);

		mappingBitmaskTypeEClass = createEClass(MAPPING_BITMASK_TYPE);
		createEAttribute(mappingBitmaskTypeEClass, MAPPING_BITMASK_TYPE__GROUP);
		createEReference(mappingBitmaskTypeEClass, MAPPING_BITMASK_TYPE__MAP_BITMASK_VALUE);
		createEAttribute(mappingBitmaskTypeEClass, MAPPING_BITMASK_TYPE__INCLUDED_PROPERTIES);

		mappingEnumTypeEClass = createEClass(MAPPING_ENUM_TYPE);
		createEAttribute(mappingEnumTypeEClass, MAPPING_ENUM_TYPE__GROUP);
		createEReference(mappingEnumTypeEClass, MAPPING_ENUM_TYPE__MAP_ENUM);
		createEAttribute(mappingEnumTypeEClass, MAPPING_ENUM_TYPE__ENUMERATION);
		createEAttribute(mappingEnumTypeEClass, MAPPING_ENUM_TYPE__HEADERS);
		createEAttribute(mappingEnumTypeEClass, MAPPING_ENUM_TYPE__NAME_ALGORITHM);
		createEAttribute(mappingEnumTypeEClass, MAPPING_ENUM_TYPE__UNIQUE_VALUE);
		createEAttribute(mappingEnumTypeEClass, MAPPING_ENUM_TYPE__VALIDATE);

		mappingFixedTypeEClass = createEClass(MAPPING_FIXED_TYPE);
		createEAttribute(mappingFixedTypeEClass, MAPPING_FIXED_TYPE__VALUE);

		mappingIdentifierTypeEClass = createEClass(MAPPING_IDENTIFIER_TYPE);

		mappingInstanceTypeEClass = createEClass(MAPPING_INSTANCE_TYPE);
		createEAttribute(mappingInstanceTypeEClass, MAPPING_INSTANCE_TYPE__RSRC_ID);

		mappingReferenceTypeEClass = createEClass(MAPPING_REFERENCE_TYPE);
		createEAttribute(mappingReferenceTypeEClass, MAPPING_REFERENCE_TYPE__RSRC_ID);

		mappingResourceTypeEClass = createEClass(MAPPING_RESOURCE_TYPE);
		createEAttribute(mappingResourceTypeEClass, MAPPING_RESOURCE_TYPE__GROUP);
		createEReference(mappingResourceTypeEClass, MAPPING_RESOURCE_TYPE__MAP_SIMPLE_MEMBER);
		createEReference(mappingResourceTypeEClass, MAPPING_RESOURCE_TYPE__MAP_INSTANCE_MEMBER);
		createEReference(mappingResourceTypeEClass, MAPPING_RESOURCE_TYPE__MAP_REFERENCE_MEMBER);
		createEReference(mappingResourceTypeEClass, MAPPING_RESOURCE_TYPE__MAP_FIXED_MEMBER);
		createEReference(mappingResourceTypeEClass, MAPPING_RESOURCE_TYPE__MAP_ENUM_MEMBER);
		createEReference(mappingResourceTypeEClass, MAPPING_RESOURCE_TYPE__MAP_IDENTIFIER_MEMBER);
		createEReference(mappingResourceTypeEClass, MAPPING_RESOURCE_TYPE__MAP_ARRAY_MEMBER);
		createEReference(mappingResourceTypeEClass, MAPPING_RESOURCE_TYPE__MAP_RESOURCE_MEMBER);
		createEReference(mappingResourceTypeEClass, MAPPING_RESOURCE_TYPE__MAP_BITMASK_MEMBER);
		createEReference(mappingResourceTypeEClass, MAPPING_RESOURCE_TYPE__MAP_MEMBER_FROM_TYPE);
		createEReference(mappingResourceTypeEClass, MAPPING_RESOURCE_TYPE__MAP_INTO_PROPERTY);
		createEReference(mappingResourceTypeEClass, MAPPING_RESOURCE_TYPE__SELECT);
		createEAttribute(mappingResourceTypeEClass, MAPPING_RESOURCE_TYPE__HEADERS);
		createEAttribute(mappingResourceTypeEClass, MAPPING_RESOURCE_TYPE__ID);
		createEAttribute(mappingResourceTypeEClass, MAPPING_RESOURCE_TYPE__STRUCT);

		mappingSimpleTypeEClass = createEClass(MAPPING_SIMPLE_TYPE);

		mapReferenceElementTypeEClass = createEClass(MAP_REFERENCE_ELEMENT_TYPE);

		mapReferenceMemberTypeEClass = createEClass(MAP_REFERENCE_MEMBER_TYPE);
		createEAttribute(mapReferenceMemberTypeEClass, MAP_REFERENCE_MEMBER_TYPE__MEMBER);
		createEAttribute(mapReferenceMemberTypeEClass, MAP_REFERENCE_MEMBER_TYPE__PROPERTY);
		createEAttribute(mapReferenceMemberTypeEClass, MAP_REFERENCE_MEMBER_TYPE__SUPPRESS_DEFAULT);

		mapReferenceTypeTypeEClass = createEClass(MAP_REFERENCE_TYPE_TYPE);
		createEAttribute(mapReferenceTypeTypeEClass, MAP_REFERENCE_TYPE_TYPE__TYPE_ID);

		mapResourceElementTypeEClass = createEClass(MAP_RESOURCE_ELEMENT_TYPE);
		createEAttribute(mapResourceElementTypeEClass, MAP_RESOURCE_ELEMENT_TYPE__INSTANCE_IDENTIFYING_MEMBER);

		mapResourceMemberTypeEClass = createEClass(MAP_RESOURCE_MEMBER_TYPE);
		createEAttribute(mapResourceMemberTypeEClass, MAP_RESOURCE_MEMBER_TYPE__MEMBER);
		createEAttribute(mapResourceMemberTypeEClass, MAP_RESOURCE_MEMBER_TYPE__PROPERTY);
		createEAttribute(mapResourceMemberTypeEClass, MAP_RESOURCE_MEMBER_TYPE__SUPPRESS_DEFAULT);

		mapResourceTypeEClass = createEClass(MAP_RESOURCE_TYPE);
		createEAttribute(mapResourceTypeEClass, MAP_RESOURCE_TYPE__BASE_NAME);
		createEAttribute(mapResourceTypeEClass, MAP_RESOURCE_TYPE__RSS_FILE);
		createEAttribute(mapResourceTypeEClass, MAP_RESOURCE_TYPE__STANDALONE);
		createEAttribute(mapResourceTypeEClass, MAP_RESOURCE_TYPE__UNNAMED);

		mapResourceTypeTypeEClass = createEClass(MAP_RESOURCE_TYPE_TYPE);
		createEAttribute(mapResourceTypeTypeEClass, MAP_RESOURCE_TYPE_TYPE__TYPE_ID);

		mapSimpleElementTypeEClass = createEClass(MAP_SIMPLE_ELEMENT_TYPE);

		mapSimpleMemberTypeEClass = createEClass(MAP_SIMPLE_MEMBER_TYPE);
		createEAttribute(mapSimpleMemberTypeEClass, MAP_SIMPLE_MEMBER_TYPE__MEMBER);
		createEAttribute(mapSimpleMemberTypeEClass, MAP_SIMPLE_MEMBER_TYPE__PROPERTY);
		createEAttribute(mapSimpleMemberTypeEClass, MAP_SIMPLE_MEMBER_TYPE__SUPPRESS_DEFAULT);

		mapSimpleTypeTypeEClass = createEClass(MAP_SIMPLE_TYPE_TYPE);
		createEAttribute(mapSimpleTypeTypeEClass, MAP_SIMPLE_TYPE_TYPE__TYPE_ID);

		propertiesTypeEClass = createEClass(PROPERTIES_TYPE);
		createEAttribute(propertiesTypeEClass, PROPERTIES_TYPE__ABSTRACT_PROPERTY_GROUP);
		createEReference(propertiesTypeEClass, PROPERTIES_TYPE__ABSTRACT_PROPERTY);

		propertyOverridesTypeEClass = createEClass(PROPERTY_OVERRIDES_TYPE);
		createEReference(propertyOverridesTypeEClass, PROPERTY_OVERRIDES_TYPE__PROPERTY_OVERRIDE);

		propertyOverrideTypeEClass = createEClass(PROPERTY_OVERRIDE_TYPE);
		createEAttribute(propertyOverrideTypeEClass, PROPERTY_OVERRIDE_TYPE__CATEGORY);
		createEAttribute(propertyOverrideTypeEClass, PROPERTY_OVERRIDE_TYPE__DEFAULT);
		createEAttribute(propertyOverrideTypeEClass, PROPERTY_OVERRIDE_TYPE__NAME);
		createEAttribute(propertyOverrideTypeEClass, PROPERTY_OVERRIDE_TYPE__READ_ONLY);

		scriptTypeEClass = createEClass(SCRIPT_TYPE);
		createEAttribute(scriptTypeEClass, SCRIPT_TYPE__FILE);
		createEAttribute(scriptTypeEClass, SCRIPT_TYPE__PROTOTYPE);

		selectTypeEClass = createEClass(SELECT_TYPE);
		createEReference(selectTypeEClass, SELECT_TYPE__CHOICE);
		createEAttribute(selectTypeEClass, SELECT_TYPE__ATTRIBUTE);
		createEAttribute(selectTypeEClass, SELECT_TYPE__IS_COMPONENT_INSTANCE_OF);
		createEAttribute(selectTypeEClass, SELECT_TYPE__PROPERTY);
		createEAttribute(selectTypeEClass, SELECT_TYPE__PROPERTY_EXISTS);

		simplePropertyTypeEClass = createEClass(SIMPLE_PROPERTY_TYPE);
		createEAttribute(simplePropertyTypeEClass, SIMPLE_PROPERTY_TYPE__DEFAULT);
		createEAttribute(simplePropertyTypeEClass, SIMPLE_PROPERTY_TYPE__EXTEND_WITH_ENUM);
		createEAttribute(simplePropertyTypeEClass, SIMPLE_PROPERTY_TYPE__MAX_VALUE);
		createEAttribute(simplePropertyTypeEClass, SIMPLE_PROPERTY_TYPE__MIN_VALUE);
		createEAttribute(simplePropertyTypeEClass, SIMPLE_PROPERTY_TYPE__TYPE);

		sourceGenTypeEClass = createEClass(SOURCE_GEN_TYPE);
		createEAttribute(sourceGenTypeEClass, SOURCE_GEN_TYPE__GROUP);
		createEReference(sourceGenTypeEClass, SOURCE_GEN_TYPE__DEFINE_LOCATION);
		createEReference(sourceGenTypeEClass, SOURCE_GEN_TYPE__TEMPLATE);
		createEReference(sourceGenTypeEClass, SOURCE_GEN_TYPE__TEMPLATE_GROUP);
		createEReference(sourceGenTypeEClass, SOURCE_GEN_TYPE__USE_TEMPLATE);
		createEReference(sourceGenTypeEClass, SOURCE_GEN_TYPE__USE_TEMPLATE_GROUP);
		createEReference(sourceGenTypeEClass, SOURCE_GEN_TYPE__INLINE);
		createEReference(sourceGenTypeEClass, SOURCE_GEN_TYPE__SCRIPT);
		createEReference(sourceGenTypeEClass, SOURCE_GEN_TYPE__DEFINE_MACRO);
		createEReference(sourceGenTypeEClass, SOURCE_GEN_TYPE__EXPAND_MACRO);
		createEAttribute(sourceGenTypeEClass, SOURCE_GEN_TYPE__DEBUG);
		createEAttribute(sourceGenTypeEClass, SOURCE_GEN_TYPE__FORMS);

		sourceMappingTypeEClass = createEClass(SOURCE_MAPPING_TYPE);
		createEAttribute(sourceMappingTypeEClass, SOURCE_MAPPING_TYPE__GROUP);
		createEReference(sourceMappingTypeEClass, SOURCE_MAPPING_TYPE__MAP_RESOURCE);
		createEReference(sourceMappingTypeEClass, SOURCE_MAPPING_TYPE__SELECT);

		sourceTypeMappingTypeEClass = createEClass(SOURCE_TYPE_MAPPING_TYPE);
		createEAttribute(sourceTypeMappingTypeEClass, SOURCE_TYPE_MAPPING_TYPE__GROUP);
		createEReference(sourceTypeMappingTypeEClass, SOURCE_TYPE_MAPPING_TYPE__MAP_RESOURCE_TYPE);
		createEReference(sourceTypeMappingTypeEClass, SOURCE_TYPE_MAPPING_TYPE__MAP_ENUM_TYPE);
		createEReference(sourceTypeMappingTypeEClass, SOURCE_TYPE_MAPPING_TYPE__MAP_SIMPLE_TYPE);
		createEReference(sourceTypeMappingTypeEClass, SOURCE_TYPE_MAPPING_TYPE__MAP_FIXED_TYPE);
		createEReference(sourceTypeMappingTypeEClass, SOURCE_TYPE_MAPPING_TYPE__MAP_BITMASK_TYPE);
		createEReference(sourceTypeMappingTypeEClass, SOURCE_TYPE_MAPPING_TYPE__MAP_IDENTIFIER_TYPE);
		createEReference(sourceTypeMappingTypeEClass, SOURCE_TYPE_MAPPING_TYPE__MAP_REFERENCE_TYPE);
		createEReference(sourceTypeMappingTypeEClass, SOURCE_TYPE_MAPPING_TYPE__MAP_ARRAY_TYPE);
		createEReference(sourceTypeMappingTypeEClass, SOURCE_TYPE_MAPPING_TYPE__SELECT);

		symbianTypeEClass = createEClass(SYMBIAN_TYPE);
		createEAttribute(symbianTypeEClass, SYMBIAN_TYPE__CLASS_HELP_TOPIC);
		createEAttribute(symbianTypeEClass, SYMBIAN_TYPE__CLASS_NAME);
		createEAttribute(symbianTypeEClass, SYMBIAN_TYPE__MAX_SDK_VERSION);
		createEAttribute(symbianTypeEClass, SYMBIAN_TYPE__MIN_SDK_VERSION);
		createEAttribute(symbianTypeEClass, SYMBIAN_TYPE__RESOURCE_HELP_TOPIC);
		createEAttribute(symbianTypeEClass, SYMBIAN_TYPE__RESOURCE_TYPE);
		createEAttribute(symbianTypeEClass, SYMBIAN_TYPE__SDK_NAME);

		templateGroupTypeEClass = createEClass(TEMPLATE_GROUP_TYPE);
		createEAttribute(templateGroupTypeEClass, TEMPLATE_GROUP_TYPE__GROUP);
		createEReference(templateGroupTypeEClass, TEMPLATE_GROUP_TYPE__DEFINE_LOCATION);
		createEReference(templateGroupTypeEClass, TEMPLATE_GROUP_TYPE__TEMPLATE);
		createEReference(templateGroupTypeEClass, TEMPLATE_GROUP_TYPE__INLINE);
		createEReference(templateGroupTypeEClass, TEMPLATE_GROUP_TYPE__USE_TEMPLATE);
		createEReference(templateGroupTypeEClass, TEMPLATE_GROUP_TYPE__USE_TEMPLATE_GROUP);
		createEReference(templateGroupTypeEClass, TEMPLATE_GROUP_TYPE__EXPAND_MACRO);
		createEAttribute(templateGroupTypeEClass, TEMPLATE_GROUP_TYPE__FORM);
		createEAttribute(templateGroupTypeEClass, TEMPLATE_GROUP_TYPE__ID);
		createEAttribute(templateGroupTypeEClass, TEMPLATE_GROUP_TYPE__LOCATION);
		createEAttribute(templateGroupTypeEClass, TEMPLATE_GROUP_TYPE__MODE);
		createEAttribute(templateGroupTypeEClass, TEMPLATE_GROUP_TYPE__PHASE);

		templateTypeEClass = createEClass(TEMPLATE_TYPE);
		createEAttribute(templateTypeEClass, TEMPLATE_TYPE__FORM);
		createEAttribute(templateTypeEClass, TEMPLATE_TYPE__ID);
		createEAttribute(templateTypeEClass, TEMPLATE_TYPE__LOCATION);
		createEAttribute(templateTypeEClass, TEMPLATE_TYPE__MODE);
		createEAttribute(templateTypeEClass, TEMPLATE_TYPE__PHASE);

		twoWayMappingTypeEClass = createEClass(TWO_WAY_MAPPING_TYPE);

		useTemplateGroupTypeEClass = createEClass(USE_TEMPLATE_GROUP_TYPE);
		createEReference(useTemplateGroupTypeEClass, USE_TEMPLATE_GROUP_TYPE__USE_TEMPLATE);
		createEAttribute(useTemplateGroupTypeEClass, USE_TEMPLATE_GROUP_TYPE__IDS);

		useTemplateTypeEClass = createEClass(USE_TEMPLATE_TYPE);
		createEAttribute(useTemplateTypeEClass, USE_TEMPLATE_TYPE__IDS);

		// Create enums
		propertyDataTypeEEnum = createEEnum(PROPERTY_DATA_TYPE);
		referenceScopeTypeEEnum = createEEnum(REFERENCE_SCOPE_TYPE);
		standaloneTypeEEnum = createEEnum(STANDALONE_TYPE);

		// Create data types
		listOfStringsEDataType = createEDataType(LIST_OF_STRINGS);
		propertyDataTypeObjectEDataType = createEDataType(PROPERTY_DATA_TYPE_OBJECT);
		referenceScopeTypeObjectEDataType = createEDataType(REFERENCE_SCOPE_TYPE_OBJECT);
		standaloneTypeObjectEDataType = createEDataType(STANDALONE_TYPE_OBJECT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

		// Add supertypes to classes
		arrayPropertyTypeEClass.getESuperTypes().add(this.getAbstractPropertyType());
		componentReferencePropertyTypeEClass.getESuperTypes().add(this.getAbstractPropertyType());
		compoundPropertyTypeEClass.getESuperTypes().add(this.getAbstractPropertyType());
		enumPropertyTypeEClass.getESuperTypes().add(this.getAbstractPropertyType());
		expandMacroTypeEClass.getESuperTypes().add(this.getConditionalSourceGen());
		inlineTypeEClass.getESuperTypes().add(this.getConditionalSourceGenString());
		mapArrayMemberTypeEClass.getESuperTypes().add(this.getMappingArrayType());
		mapArrayTypeTypeEClass.getESuperTypes().add(this.getMappingArrayType());
		mapBitmaskElementTypeEClass.getESuperTypes().add(this.getMappingBitmaskType());
		mapBitmaskMemberTypeEClass.getESuperTypes().add(this.getMappingBitmaskType());
		mapBitmaskTypeTypeEClass.getESuperTypes().add(this.getMappingBitmaskType());
		mapElementFromTypeTypeEClass.getESuperTypes().add(this.getTwoWayMappingType());
		mapEnumElementTypeEClass.getESuperTypes().add(this.getMappingEnumType());
		mapEnumMemberTypeEClass.getESuperTypes().add(this.getMappingEnumType());
		mapEnumTypeTypeEClass.getESuperTypes().add(this.getMappingEnumType());
		mapFixedElementTypeEClass.getESuperTypes().add(this.getMappingFixedType());
		mapFixedMemberTypeEClass.getESuperTypes().add(this.getMappingFixedType());
		mapFixedTypeTypeEClass.getESuperTypes().add(this.getMappingFixedType());
		mapIdentifierElementTypeEClass.getESuperTypes().add(this.getMappingIdentifierType());
		mapIdentifierMemberTypeEClass.getESuperTypes().add(this.getMappingIdentifierType());
		mapIdentifierTypeTypeEClass.getESuperTypes().add(this.getMappingIdentifierType());
		mapInstanceElementTypeEClass.getESuperTypes().add(this.getMappingInstanceType());
		mapInstanceMemberTypeEClass.getESuperTypes().add(this.getMappingInstanceType());
		mapInstanceTypeTypeEClass.getESuperTypes().add(this.getMappingInstanceType());
		mapIntoPropertyTypeEClass.getESuperTypes().add(this.getTwoWayMappingType());
		mapMemberFromTypeTypeEClass.getESuperTypes().add(this.getTwoWayMappingType());
		mappingArrayTypeEClass.getESuperTypes().add(this.getTwoWayMappingType());
		mappingBitmaskTypeEClass.getESuperTypes().add(this.getTwoWayMappingType());
		mappingEnumTypeEClass.getESuperTypes().add(this.getTwoWayMappingType());
		mappingFixedTypeEClass.getESuperTypes().add(this.getTwoWayMappingType());
		mappingIdentifierTypeEClass.getESuperTypes().add(this.getTwoWayMappingType());
		mappingInstanceTypeEClass.getESuperTypes().add(this.getTwoWayMappingType());
		mappingReferenceTypeEClass.getESuperTypes().add(this.getTwoWayMappingType());
		mappingResourceTypeEClass.getESuperTypes().add(this.getTwoWayMappingType());
		mappingSimpleTypeEClass.getESuperTypes().add(this.getTwoWayMappingType());
		mapReferenceElementTypeEClass.getESuperTypes().add(this.getMappingReferenceType());
		mapReferenceMemberTypeEClass.getESuperTypes().add(this.getMappingReferenceType());
		mapReferenceTypeTypeEClass.getESuperTypes().add(this.getMappingReferenceType());
		mapResourceElementTypeEClass.getESuperTypes().add(this.getMappingResourceType());
		mapResourceMemberTypeEClass.getESuperTypes().add(this.getMappingResourceType());
		mapResourceTypeEClass.getESuperTypes().add(this.getMappingResourceType());
		mapResourceTypeTypeEClass.getESuperTypes().add(this.getMappingResourceType());
		mapSimpleElementTypeEClass.getESuperTypes().add(this.getMappingSimpleType());
		mapSimpleMemberTypeEClass.getESuperTypes().add(this.getMappingSimpleType());
		mapSimpleTypeTypeEClass.getESuperTypes().add(this.getMappingSimpleType());
		simplePropertyTypeEClass.getESuperTypes().add(this.getAbstractPropertyType());
		templateGroupTypeEClass.getESuperTypes().add(this.getConditionalSourceGen());
		templateTypeEClass.getESuperTypes().add(this.getConditionalSourceGenString());

		// Initialize classes and features; add operations and parameters
		initEClass(abstractPropertyTypeEClass, AbstractPropertyType.class, "AbstractPropertyType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbstractPropertyType_Category(), theXMLTypePackage.getString(), "category", null, 0, 1, AbstractPropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractPropertyType_DescriptionKey(), theXMLTypePackage.getString(), "descriptionKey", null, 0, 1, AbstractPropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractPropertyType_DisplayName(), theXMLTypePackage.getString(), "displayName", null, 0, 1, AbstractPropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractPropertyType_EditorClass(), theXMLTypePackage.getString(), "editorClass", null, 0, 1, AbstractPropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractPropertyType_HelpKey(), theXMLTypePackage.getString(), "helpKey", null, 0, 1, AbstractPropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractPropertyType_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, AbstractPropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractPropertyType_ReadOnly(), theXMLTypePackage.getBoolean(), "readOnly", "false", 0, 1, AbstractPropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractPropertyType_Resettable(), theXMLTypePackage.getBoolean(), "resettable", "true", 0, 1, AbstractPropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(arrayPropertyTypeEClass, ArrayPropertyType.class, "ArrayPropertyType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getArrayPropertyType_Type(), theXMLTypePackage.getString(), "type", null, 1, 1, ArrayPropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributesTypeEClass, AttributesType.class, "AttributesType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAttributesType_Attribute(), this.getAttributeType(), null, "attribute", null, 1, -1, AttributesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributeTypeEClass, AttributeType.class, "AttributeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAttributeType_Value(), theXMLTypePackage.getString(), "value", null, 0, 1, AttributeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAttributeType_Key(), theXMLTypePackage.getString(), "key", null, 1, 1, AttributeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(choiceTypeEClass, ChoiceType.class, "ChoiceType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getChoiceType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, ChoiceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getChoiceType_TwoWayMappingGroup(), ecorePackage.getEFeatureMapEntry(), "twoWayMappingGroup", null, 0, -1, ChoiceType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getChoiceType_TwoWayMapping(), this.getTwoWayMappingType(), null, "twoWayMapping", null, 0, -1, ChoiceType.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getChoiceType_MapResource(), this.getMapResourceType(), null, "mapResource", null, 0, -1, ChoiceType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getChoiceType_Select(), this.getSelectType(), null, "select", null, 0, -1, ChoiceType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getChoiceType_Value(), theXMLTypePackage.getString(), "value", null, 0, 1, ChoiceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(codeTypeEClass, CodeType.class, "CodeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCodeType_Class(), theXMLTypePackage.getString(), "class", null, 1, 1, CodeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCodeType_Plugin(), theXMLTypePackage.getString(), "plugin", null, 0, 1, CodeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(componentDefinitionTypeEClass, ComponentDefinitionType.class, "ComponentDefinitionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getComponentDefinitionType_CompoundPropertyDeclaration(), this.getCompoundPropertyDeclarationType(), null, "compoundPropertyDeclaration", null, 0, -1, ComponentDefinitionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponentDefinitionType_EnumPropertyDeclaration(), this.getEnumPropertyDeclarationType(), null, "enumPropertyDeclaration", null, 0, -1, ComponentDefinitionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponentDefinitionType_Component(), this.getComponentType(), null, "component", null, 0, 1, ComponentDefinitionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(componentReferencePropertyTypeEClass, ComponentReferencePropertyType.class, "ComponentReferencePropertyType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getComponentReferencePropertyType_Constraint(), theXMLTypePackage.getString(), "constraint", null, 0, 1, ComponentReferencePropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponentReferencePropertyType_CreationKeys(), theXMLTypePackage.getString(), "creationKeys", null, 0, 1, ComponentReferencePropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponentReferencePropertyType_PromoteReferenceProperties(), theXMLTypePackage.getBoolean(), "promoteReferenceProperties", null, 0, 1, ComponentReferencePropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponentReferencePropertyType_Scope(), this.getReferenceScopeType(), "scope", "model", 1, 1, ComponentReferencePropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(componentTypeEClass, ComponentType.class, "ComponentType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getComponentType_Documentation(), this.getDocumentationType(), null, "documentation", null, 0, 1, ComponentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponentType_Symbian(), this.getSymbianType(), null, "symbian", null, 0, 1, ComponentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponentType_DesignerImages(), this.getDesignerImagesType(), null, "designerImages", null, 0, 1, ComponentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponentType_Attributes(), this.getAttributesType(), null, "attributes", null, 0, 1, ComponentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponentType_Properties(), this.getPropertiesType(), null, "properties", null, 0, 1, ComponentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponentType_ExtensionProperties(), this.getExtensionPropertiesType(), null, "extensionProperties", null, 0, -1, ComponentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponentType_PropertyOverrides(), this.getPropertyOverridesType(), null, "propertyOverrides", null, 0, 1, ComponentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponentType_Events(), this.getEventsType(), null, "events", null, 0, 1, ComponentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponentType_SourceGen(), this.getSourceGenType(), null, "sourceGen", null, 0, 1, ComponentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponentType_SourceMapping(), this.getSourceMappingType(), null, "sourceMapping", null, 0, 1, ComponentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponentType_Implementations(), this.getImplementationsType(), null, "implementations", null, 0, 1, ComponentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponentType_Abstract(), theXMLTypePackage.getBoolean(), "abstract", null, 0, 1, ComponentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponentType_BaseComponent(), theXMLTypePackage.getString(), "baseComponent", null, 0, 1, ComponentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponentType_Category(), theXMLTypePackage.getString(), "category", null, 0, 1, ComponentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponentType_FriendlyName(), theXMLTypePackage.getString(), "friendlyName", null, 1, 1, ComponentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponentType_InstanceNameRoot(), theXMLTypePackage.getString(), "instanceNameRoot", null, 0, 1, ComponentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponentType_QualifiedName(), theXMLTypePackage.getString(), "qualifiedName", null, 1, 1, ComponentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponentType_Version(), theXMLTypePackage.getString(), "version", null, 0, 1, ComponentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(compoundPropertyDeclarationTypeEClass, CompoundPropertyDeclarationType.class, "CompoundPropertyDeclarationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCompoundPropertyDeclarationType_AbstractPropertyGroup(), ecorePackage.getEFeatureMapEntry(), "abstractPropertyGroup", null, 0, -1, CompoundPropertyDeclarationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCompoundPropertyDeclarationType_AbstractProperty(), this.getAbstractPropertyType(), null, "abstractProperty", null, 0, -1, CompoundPropertyDeclarationType.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getCompoundPropertyDeclarationType_SourceTypeMapping(), this.getSourceTypeMappingType(), null, "sourceTypeMapping", null, 0, 1, CompoundPropertyDeclarationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCompoundPropertyDeclarationType_ConverterClass(), theXMLTypePackage.getString(), "converterClass", null, 0, 1, CompoundPropertyDeclarationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCompoundPropertyDeclarationType_EditableType(), theXMLTypePackage.getString(), "editableType", "", 0, 1, CompoundPropertyDeclarationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCompoundPropertyDeclarationType_EditorClass(), theXMLTypePackage.getString(), "editorClass", null, 0, 1, CompoundPropertyDeclarationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCompoundPropertyDeclarationType_QualifiedName(), theXMLTypePackage.getString(), "qualifiedName", null, 1, 1, CompoundPropertyDeclarationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(compoundPropertyTypeEClass, CompoundPropertyType.class, "CompoundPropertyType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCompoundPropertyType_Default(), theXMLTypePackage.getString(), "default", null, 0, 1, CompoundPropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCompoundPropertyType_Type(), theXMLTypePackage.getString(), "type", null, 1, 1, CompoundPropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(conditionalSourceGenEClass, ConditionalSourceGen.class, "ConditionalSourceGen", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConditionalSourceGen_Forms(), this.getListOfStrings(), "forms", null, 0, 1, ConditionalSourceGen.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConditionalSourceGen_IfEvents(), this.getListOfStrings(), "ifEvents", null, 0, 1, ConditionalSourceGen.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConditionalSourceGen_IfExpr(), theXMLTypePackage.getString(), "ifExpr", null, 0, 1, ConditionalSourceGen.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(conditionalSourceGenStringEClass, ConditionalSourceGenString.class, "ConditionalSourceGenString", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConditionalSourceGenString_Value(), theXMLTypePackage.getString(), "value", null, 0, 1, ConditionalSourceGenString.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConditionalSourceGenString_Forms(), this.getListOfStrings(), "forms", null, 0, 1, ConditionalSourceGenString.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConditionalSourceGenString_IfEvents(), this.getListOfStrings(), "ifEvents", null, 0, 1, ConditionalSourceGenString.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConditionalSourceGenString_IfExpr(), theXMLTypePackage.getString(), "ifExpr", null, 0, 1, ConditionalSourceGenString.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(defineLocationTypeEClass, DefineLocationType.class, "DefineLocationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDefineLocationType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, DefineLocationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDefineLocationType_Template(), this.getTemplateType(), null, "template", null, 0, -1, DefineLocationType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDefineLocationType_Inline(), this.getInlineType(), null, "inline", null, 0, -1, DefineLocationType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDefineLocationType_Script(), this.getScriptType(), null, "script", null, 0, -1, DefineLocationType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDefineLocationType_ExpandMacro(), this.getExpandMacroType(), null, "expandMacro", null, 0, -1, DefineLocationType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDefineLocationType_BaseLocation(), theXMLTypePackage.getString(), "baseLocation", null, 0, 1, DefineLocationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDefineLocationType_Dir(), theXMLTypePackage.getString(), "dir", null, 0, 1, DefineLocationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDefineLocationType_Domain(), theXMLTypePackage.getString(), "domain", null, 0, 1, DefineLocationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDefineLocationType_File(), theXMLTypePackage.getString(), "file", null, 0, 1, DefineLocationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDefineLocationType_Filter(), theXMLTypePackage.getString(), "filter", "default", 0, 1, DefineLocationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDefineLocationType_Id(), theXMLTypePackage.getString(), "id", null, 1, 1, DefineLocationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDefineLocationType_IfEvents(), this.getListOfStrings(), "ifEvents", null, 0, 1, DefineLocationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDefineLocationType_IsEventHandler(), theXMLTypePackage.getString(), "isEventHandler", null, 0, 1, DefineLocationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDefineLocationType_Location(), theXMLTypePackage.getString(), "location", null, 1, 1, DefineLocationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDefineLocationType_Owned(), theXMLTypePackage.getString(), "owned", "true", 0, 1, DefineLocationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDefineLocationType_Realize(), theXMLTypePackage.getString(), "realize", "false", 0, 1, DefineLocationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(defineMacroTypeEClass, DefineMacroType.class, "DefineMacroType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDefineMacroType_ImportArguments(), this.getImportArgumentsType(), null, "importArguments", null, 0, -1, DefineMacroType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDefineMacroType_MacroArgument(), this.getMacroArgumentType(), null, "macroArgument", null, 0, -1, DefineMacroType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDefineMacroType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, DefineMacroType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDefineMacroType_Template(), this.getTemplateType(), null, "template", null, 0, -1, DefineMacroType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDefineMacroType_Inline(), this.getInlineType(), null, "inline", null, 0, -1, DefineMacroType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDefineMacroType_DefineLocation(), this.getDefineLocationType(), null, "defineLocation", null, 0, -1, DefineMacroType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDefineMacroType_ExpandMacro(), this.getExpandMacroType(), null, "expandMacro", null, 0, -1, DefineMacroType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDefineMacroType_Help(), theXMLTypePackage.getString(), "help", null, 0, 1, DefineMacroType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDefineMacroType_Id(), theXMLTypePackage.getString(), "id", null, 0, 1, DefineMacroType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(designerImagesTypeEClass, DesignerImagesType.class, "DesignerImagesType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDesignerImagesType_LargeIconFile(), theXMLTypePackage.getString(), "largeIconFile", null, 0, 1, DesignerImagesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDesignerImagesType_LayoutImageFile(), theXMLTypePackage.getString(), "layoutImageFile", null, 0, 1, DesignerImagesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDesignerImagesType_SmallIconFile(), theXMLTypePackage.getString(), "smallIconFile", null, 0, 1, DesignerImagesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDesignerImagesType_ThumbnailFile(), theXMLTypePackage.getString(), "thumbnailFile", null, 0, 1, DesignerImagesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(documentationTypeEClass, DocumentationType.class, "DocumentationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDocumentationType_Information(), theXMLTypePackage.getString(), "information", null, 1, 1, DocumentationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentationType_HelpTopic(), theXMLTypePackage.getString(), "helpTopic", null, 1, 1, DocumentationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentationType_WizardDescription(), theXMLTypePackage.getString(), "wizardDescription", null, 1, 1, DocumentationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_AbstractProperty(), this.getAbstractPropertyType(), null, "abstractProperty", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_ArrayProperty(), this.getArrayPropertyType(), null, "arrayProperty", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Attribute(), this.getAttributeType(), null, "attribute", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Attributes(), this.getAttributesType(), null, "attributes", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Choice(), this.getChoiceType(), null, "choice", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Code(), this.getCodeType(), null, "code", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Component(), this.getComponentType(), null, "component", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_ComponentDefinition(), this.getComponentDefinitionType(), null, "componentDefinition", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_ComponentReferenceProperty(), this.getComponentReferencePropertyType(), null, "componentReferenceProperty", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_CompoundProperty(), this.getCompoundPropertyType(), null, "compoundProperty", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_CompoundPropertyDeclaration(), this.getCompoundPropertyDeclarationType(), null, "compoundPropertyDeclaration", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_DefineLocation(), this.getDefineLocationType(), null, "defineLocation", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_DefineMacro(), this.getDefineMacroType(), null, "defineMacro", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_DesignerImages(), this.getDesignerImagesType(), null, "designerImages", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Documentation(), this.getDocumentationType(), null, "documentation", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_EnumProperty(), this.getEnumPropertyType(), null, "enumProperty", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_EnumPropertyDeclaration(), this.getEnumPropertyDeclarationType(), null, "enumPropertyDeclaration", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Event(), this.getEventType(), null, "event", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Events(), this.getEventsType(), null, "events", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_ExpandArgument(), this.getExpandArgumentType(), null, "expandArgument", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_ExpandMacro(), this.getExpandMacroType(), null, "expandMacro", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_ExtensionProperties(), this.getExtensionPropertiesType(), null, "extensionProperties", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Implementation(), this.getImplementationType(), null, "implementation", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Implementations(), this.getImplementationsType(), null, "implementations", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_ImportArguments(), this.getImportArgumentsType(), null, "importArguments", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Inline(), this.getInlineType(), null, "inline", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MacroArgument(), this.getMacroArgumentType(), null, "macroArgument", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapArrayMember(), this.getMapArrayMemberType(), null, "mapArrayMember", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_TwoWayMapping(), this.getTwoWayMappingType(), null, "twoWayMapping", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapArrayType(), this.getMapArrayTypeType(), null, "mapArrayType", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapBitmaskElement(), this.getMapBitmaskElementType(), null, "mapBitmaskElement", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapBitmaskMember(), this.getMapBitmaskMemberType(), null, "mapBitmaskMember", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapBitmaskType(), this.getMapBitmaskTypeType(), null, "mapBitmaskType", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapBitmaskValue(), this.getMapBitmaskValueType(), null, "mapBitmaskValue", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapElementFromType(), this.getMapElementFromTypeType(), null, "mapElementFromType", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapEnum(), this.getMapEnumType(), null, "mapEnum", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapEnumElement(), this.getMapEnumElementType(), null, "mapEnumElement", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapEnumMember(), this.getMapEnumMemberType(), null, "mapEnumMember", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapEnumType(), this.getMapEnumTypeType(), null, "mapEnumType", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapFixedElement(), this.getMapFixedElementType(), null, "mapFixedElement", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapFixedMember(), this.getMapFixedMemberType(), null, "mapFixedMember", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapFixedType(), this.getMapFixedTypeType(), null, "mapFixedType", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapIdentifierElement(), this.getMapIdentifierElementType(), null, "mapIdentifierElement", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapIdentifierMember(), this.getMapIdentifierMemberType(), null, "mapIdentifierMember", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapIdentifierType(), this.getMapIdentifierTypeType(), null, "mapIdentifierType", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapInstanceElement(), this.getMapInstanceElementType(), null, "mapInstanceElement", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapInstanceMember(), this.getMapInstanceMemberType(), null, "mapInstanceMember", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapInstanceType(), this.getMapInstanceTypeType(), null, "mapInstanceType", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapIntoProperty(), this.getMapIntoPropertyType(), null, "mapIntoProperty", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapMemberFromType(), this.getMapMemberFromTypeType(), null, "mapMemberFromType", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapReferenceElement(), this.getMapReferenceElementType(), null, "mapReferenceElement", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapReferenceMember(), this.getMapReferenceMemberType(), null, "mapReferenceMember", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapReferenceType(), this.getMapReferenceTypeType(), null, "mapReferenceType", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapResource(), this.getMapResourceType(), null, "mapResource", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapResourceElement(), this.getMapResourceElementType(), null, "mapResourceElement", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapResourceMember(), this.getMapResourceMemberType(), null, "mapResourceMember", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapResourceType(), this.getMapResourceTypeType(), null, "mapResourceType", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapSimpleElement(), this.getMapSimpleElementType(), null, "mapSimpleElement", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapSimpleMember(), this.getMapSimpleMemberType(), null, "mapSimpleMember", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MapSimpleType(), this.getMapSimpleTypeType(), null, "mapSimpleType", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Properties(), this.getPropertiesType(), null, "properties", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Property(), this.getSimplePropertyType(), null, "property", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_PropertyOverrides(), this.getPropertyOverridesType(), null, "propertyOverrides", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Script(), this.getScriptType(), null, "script", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Select(), this.getSelectType(), null, "select", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_SourceGen(), this.getSourceGenType(), null, "sourceGen", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_SourceMapping(), this.getSourceMappingType(), null, "sourceMapping", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_SourceTypeMapping(), this.getSourceTypeMappingType(), null, "sourceTypeMapping", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Symbian(), this.getSymbianType(), null, "symbian", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Template(), this.getTemplateType(), null, "template", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_TemplateGroup(), this.getTemplateGroupType(), null, "templateGroup", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_UseTemplate(), this.getUseTemplateType(), null, "useTemplate", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_UseTemplateGroup(), this.getUseTemplateGroupType(), null, "useTemplateGroup", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(enumElementTypeEClass, EnumElementType.class, "EnumElementType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEnumElementType_DisplayValue(), theXMLTypePackage.getAnySimpleType(), "displayValue", null, 0, 1, EnumElementType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEnumElementType_Value(), theXMLTypePackage.getAnySimpleType(), "value", null, 1, 1, EnumElementType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(enumPropertyDeclarationTypeEClass, EnumPropertyDeclarationType.class, "EnumPropertyDeclarationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEnumPropertyDeclarationType_EnumElement(), this.getEnumElementType(), null, "enumElement", null, 1, -1, EnumPropertyDeclarationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEnumPropertyDeclarationType_SourceTypeMapping(), this.getSourceTypeMappingType(), null, "sourceTypeMapping", null, 0, 1, EnumPropertyDeclarationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEnumPropertyDeclarationType_DefaultValue(), theXMLTypePackage.getString(), "defaultValue", null, 0, 1, EnumPropertyDeclarationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEnumPropertyDeclarationType_QualifiedName(), theXMLTypePackage.getString(), "qualifiedName", null, 1, 1, EnumPropertyDeclarationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(enumPropertyTypeEClass, EnumPropertyType.class, "EnumPropertyType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEnumPropertyType_Default(), theXMLTypePackage.getString(), "default", null, 0, 1, EnumPropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEnumPropertyType_ExtendWithEnum(), theXMLTypePackage.getString(), "extendWithEnum", null, 0, 1, EnumPropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEnumPropertyType_Type(), theXMLTypePackage.getString(), "type", null, 1, 1, EnumPropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eventsTypeEClass, EventsType.class, "EventsType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEventsType_Event(), this.getEventType(), null, "event", null, 0, -1, EventsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEventsType_DefaultEventName(), theXMLTypePackage.getString(), "defaultEventName", null, 0, 1, EventsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eventTypeEClass, EventType.class, "EventType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEventType_Category(), theXMLTypePackage.getString(), "category", null, 0, 1, EventType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEventType_DescriptionKey(), theXMLTypePackage.getString(), "descriptionKey", null, 0, 1, EventType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEventType_DisplayName(), theXMLTypePackage.getString(), "displayName", null, 0, 1, EventType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEventType_Group(), theXMLTypePackage.getString(), "group", null, 0, 1, EventType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEventType_HandlerNameTemplate(), theXMLTypePackage.getString(), "handlerNameTemplate", null, 1, 1, EventType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEventType_HelpKey(), theXMLTypePackage.getString(), "helpKey", null, 0, 1, EventType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEventType_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, EventType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(expandArgumentTypeEClass, ExpandArgumentType.class, "ExpandArgumentType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExpandArgumentType_Value(), theXMLTypePackage.getString(), "value", null, 0, 1, ExpandArgumentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExpandArgumentType_Help(), theXMLTypePackage.getString(), "help", null, 0, 1, ExpandArgumentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExpandArgumentType_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, ExpandArgumentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(expandMacroTypeEClass, ExpandMacroType.class, "ExpandMacroType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExpandMacroType_ExpandArgument(), this.getExpandArgumentType(), null, "expandArgument", null, 0, -1, ExpandMacroType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExpandMacroType_DontPassArguments(), this.getListOfStrings(), "dontPassArguments", null, 0, 1, ExpandMacroType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExpandMacroType_Help(), theXMLTypePackage.getString(), "help", null, 0, 1, ExpandMacroType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExpandMacroType_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, ExpandMacroType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExpandMacroType_PassArguments(), this.getListOfStrings(), "passArguments", null, 0, 1, ExpandMacroType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExpandMacroType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, ExpandMacroType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(extensionPropertiesTypeEClass, ExtensionPropertiesType.class, "ExtensionPropertiesType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExtensionPropertiesType_AbstractPropertyGroup(), ecorePackage.getEFeatureMapEntry(), "abstractPropertyGroup", null, 0, -1, ExtensionPropertiesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getExtensionPropertiesType_AbstractProperty(), this.getAbstractPropertyType(), null, "abstractProperty", null, 0, -1, ExtensionPropertiesType.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getExtensionPropertiesType_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, ExtensionPropertiesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(implementationsTypeEClass, ImplementationsType.class, "ImplementationsType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getImplementationsType_Implementation(), this.getImplementationType(), null, "implementation", null, 1, -1, ImplementationsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(implementationTypeEClass, ImplementationType.class, "ImplementationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getImplementationType_Interface(), this.getInterfaceType(), null, "interface", null, 1, -1, ImplementationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getImplementationType_Code(), this.getCodeType(), null, "code", null, 0, 1, ImplementationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getImplementationType_Script(), this.getScriptType(), null, "script", null, 0, 1, ImplementationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(importArgumentsTypeEClass, ImportArgumentsType.class, "ImportArgumentsType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getImportArgumentsType_Arguments(), this.getListOfStrings(), "arguments", null, 1, 1, ImportArgumentsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getImportArgumentsType_ExceptArguments(), this.getListOfStrings(), "exceptArguments", null, 1, 1, ImportArgumentsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getImportArgumentsType_Help(), theXMLTypePackage.getString(), "help", null, 0, 1, ImportArgumentsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getImportArgumentsType_MacroName(), theXMLTypePackage.getString(), "macroName", null, 1, 1, ImportArgumentsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(inlineTypeEClass, InlineType.class, "InlineType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInlineType_Id(), theXMLTypePackage.getString(), "id", null, 0, 1, InlineType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInlineType_Scope(), theXMLTypePackage.getString(), "scope", "function", 0, 1, InlineType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(interfaceTypeEClass, InterfaceType.class, "InterfaceType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInterfaceType_Id(), theXMLTypePackage.getString(), "id", null, 1, 1, InterfaceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(macroArgumentTypeEClass, MacroArgumentType.class, "MacroArgumentType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMacroArgumentType_Value(), theXMLTypePackage.getString(), "value", null, 0, 1, MacroArgumentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMacroArgumentType_Default(), theXMLTypePackage.getString(), "default", null, 0, 1, MacroArgumentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMacroArgumentType_Help(), theXMLTypePackage.getString(), "help", null, 0, 1, MacroArgumentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMacroArgumentType_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, MacroArgumentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMacroArgumentType_Optional(), theXMLTypePackage.getBoolean(), "optional", null, 0, 1, MacroArgumentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapArrayMemberTypeEClass, MapArrayMemberType.class, "MapArrayMemberType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapArrayMemberType_Member(), theXMLTypePackage.getString(), "member", null, 1, 1, MapArrayMemberType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMapArrayMemberType_Property(), theXMLTypePackage.getString(), "property", null, 1, 1, MapArrayMemberType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMapArrayMemberType_SuppressDefault(), theXMLTypePackage.getBoolean(), "suppressDefault", "true", 0, 1, MapArrayMemberType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapArrayTypeTypeEClass, MapArrayTypeType.class, "MapArrayTypeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapArrayTypeType_TypeId(), theXMLTypePackage.getString(), "typeId", null, 0, 1, MapArrayTypeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapBitmaskElementTypeEClass, MapBitmaskElementType.class, "MapBitmaskElementType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(mapBitmaskMemberTypeEClass, MapBitmaskMemberType.class, "MapBitmaskMemberType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapBitmaskMemberType_Member(), theXMLTypePackage.getString(), "member", null, 1, 1, MapBitmaskMemberType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMapBitmaskMemberType_Property(), theXMLTypePackage.getString(), "property", null, 1, 1, MapBitmaskMemberType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMapBitmaskMemberType_SuppressDefault(), theXMLTypePackage.getBoolean(), "suppressDefault", "true", 0, 1, MapBitmaskMemberType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapBitmaskTypeTypeEClass, MapBitmaskTypeType.class, "MapBitmaskTypeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapBitmaskTypeType_TypeId(), theXMLTypePackage.getString(), "typeId", null, 0, 1, MapBitmaskTypeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapBitmaskValueTypeEClass, MapBitmaskValueType.class, "MapBitmaskValueType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapBitmaskValueType_Properties(), this.getListOfStrings(), "properties", null, 1, 1, MapBitmaskValueType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMapBitmaskValueType_Value(), theXMLTypePackage.getString(), "value", null, 1, 1, MapBitmaskValueType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapElementFromTypeTypeEClass, MapElementFromTypeType.class, "MapElementFromTypeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapElementFromTypeType_TypeId(), theXMLTypePackage.getString(), "typeId", null, 0, 1, MapElementFromTypeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapEnumElementTypeEClass, MapEnumElementType.class, "MapEnumElementType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(mapEnumMemberTypeEClass, MapEnumMemberType.class, "MapEnumMemberType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapEnumMemberType_Member(), theXMLTypePackage.getString(), "member", null, 1, 1, MapEnumMemberType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMapEnumMemberType_Property(), theXMLTypePackage.getString(), "property", null, 1, 1, MapEnumMemberType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMapEnumMemberType_SuppressDefault(), theXMLTypePackage.getBoolean(), "suppressDefault", "true", 0, 1, MapEnumMemberType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapEnumTypeEClass, MapEnumType.class, "MapEnumType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapEnumType_Enumerator(), theXMLTypePackage.getString(), "enumerator", null, 1, 1, MapEnumType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMapEnumType_Value(), theXMLTypePackage.getString(), "value", null, 1, 1, MapEnumType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapEnumTypeTypeEClass, MapEnumTypeType.class, "MapEnumTypeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapEnumTypeType_TypeId(), theXMLTypePackage.getString(), "typeId", null, 0, 1, MapEnumTypeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapFixedElementTypeEClass, MapFixedElementType.class, "MapFixedElementType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(mapFixedMemberTypeEClass, MapFixedMemberType.class, "MapFixedMemberType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapFixedMemberType_Member(), theXMLTypePackage.getString(), "member", null, 1, 1, MapFixedMemberType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMapFixedMemberType_SuppressDefault(), theXMLTypePackage.getBoolean(), "suppressDefault", "true", 0, 1, MapFixedMemberType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapFixedTypeTypeEClass, MapFixedTypeType.class, "MapFixedTypeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapFixedTypeType_TypeId(), theXMLTypePackage.getString(), "typeId", null, 0, 1, MapFixedTypeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapIdentifierElementTypeEClass, MapIdentifierElementType.class, "MapIdentifierElementType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(mapIdentifierMemberTypeEClass, MapIdentifierMemberType.class, "MapIdentifierMemberType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapIdentifierMemberType_Member(), theXMLTypePackage.getString(), "member", null, 1, 1, MapIdentifierMemberType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMapIdentifierMemberType_Property(), theXMLTypePackage.getString(), "property", null, 1, 1, MapIdentifierMemberType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMapIdentifierMemberType_SuppressDefault(), theXMLTypePackage.getBoolean(), "suppressDefault", "true", 0, 1, MapIdentifierMemberType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapIdentifierTypeTypeEClass, MapIdentifierTypeType.class, "MapIdentifierTypeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapIdentifierTypeType_TypeId(), theXMLTypePackage.getString(), "typeId", null, 0, 1, MapIdentifierTypeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapInstanceElementTypeEClass, MapInstanceElementType.class, "MapInstanceElementType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(mapInstanceMemberTypeEClass, MapInstanceMemberType.class, "MapInstanceMemberType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapInstanceMemberType_Member(), theXMLTypePackage.getString(), "member", null, 1, 1, MapInstanceMemberType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMapInstanceMemberType_Property(), theXMLTypePackage.getString(), "property", null, 1, 1, MapInstanceMemberType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMapInstanceMemberType_SuppressDefault(), theXMLTypePackage.getBoolean(), "suppressDefault", "true", 0, 1, MapInstanceMemberType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapInstanceTypeTypeEClass, MapInstanceTypeType.class, "MapInstanceTypeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapInstanceTypeType_TypeId(), theXMLTypePackage.getString(), "typeId", null, 0, 1, MapInstanceTypeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapIntoPropertyTypeEClass, MapIntoPropertyType.class, "MapIntoPropertyType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapIntoPropertyType_TwoWayMappingGroup(), ecorePackage.getEFeatureMapEntry(), "twoWayMappingGroup", null, 1, 1, MapIntoPropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMapIntoPropertyType_TwoWayMapping(), this.getTwoWayMappingType(), null, "twoWayMapping", null, 1, 1, MapIntoPropertyType.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getMapIntoPropertyType_Property(), theXMLTypePackage.getString(), "property", null, 1, 1, MapIntoPropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapMemberFromTypeTypeEClass, MapMemberFromTypeType.class, "MapMemberFromTypeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapMemberFromTypeType_Member(), theXMLTypePackage.getString(), "member", null, 1, 1, MapMemberFromTypeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMapMemberFromTypeType_Property(), theXMLTypePackage.getString(), "property", null, 1, 1, MapMemberFromTypeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMapMemberFromTypeType_SuppressDefault(), theXMLTypePackage.getBoolean(), "suppressDefault", "true", 0, 1, MapMemberFromTypeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMapMemberFromTypeType_TypeId(), theXMLTypePackage.getString(), "typeId", null, 0, 1, MapMemberFromTypeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mappingArrayTypeEClass, MappingArrayType.class, "MappingArrayType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMappingArrayType_TwoWayMappingGroup(), ecorePackage.getEFeatureMapEntry(), "twoWayMappingGroup", null, 0, 1, MappingArrayType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMappingArrayType_TwoWayMapping(), this.getTwoWayMappingType(), null, "twoWayMapping", null, 0, 1, MappingArrayType.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getMappingArrayType_Select(), this.getSelectType(), null, "select", null, 0, 1, MappingArrayType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mappingBitmaskTypeEClass, MappingBitmaskType.class, "MappingBitmaskType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMappingBitmaskType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, MappingBitmaskType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMappingBitmaskType_MapBitmaskValue(), this.getMapBitmaskValueType(), null, "mapBitmaskValue", null, 0, -1, MappingBitmaskType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getMappingBitmaskType_IncludedProperties(), this.getListOfStrings(), "includedProperties", null, 0, 1, MappingBitmaskType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mappingEnumTypeEClass, MappingEnumType.class, "MappingEnumType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMappingEnumType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, MappingEnumType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMappingEnumType_MapEnum(), this.getMapEnumType(), null, "mapEnum", null, 0, -1, MappingEnumType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getMappingEnumType_Enumeration(), theXMLTypePackage.getString(), "enumeration", null, 0, 1, MappingEnumType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMappingEnumType_Headers(), this.getListOfStrings(), "headers", null, 0, 1, MappingEnumType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMappingEnumType_NameAlgorithm(), theXMLTypePackage.getString(), "nameAlgorithm", null, 0, 1, MappingEnumType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMappingEnumType_UniqueValue(), theXMLTypePackage.getString(), "uniqueValue", null, 0, 1, MappingEnumType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMappingEnumType_Validate(), theXMLTypePackage.getBoolean(), "validate", "true", 0, 1, MappingEnumType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mappingFixedTypeEClass, MappingFixedType.class, "MappingFixedType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMappingFixedType_Value(), theXMLTypePackage.getString(), "value", null, 1, 1, MappingFixedType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mappingIdentifierTypeEClass, MappingIdentifierType.class, "MappingIdentifierType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(mappingInstanceTypeEClass, MappingInstanceType.class, "MappingInstanceType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMappingInstanceType_RsrcId(), theXMLTypePackage.getString(), "rsrcId", null, 0, 1, MappingInstanceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mappingReferenceTypeEClass, MappingReferenceType.class, "MappingReferenceType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMappingReferenceType_RsrcId(), theXMLTypePackage.getString(), "rsrcId", null, 0, 1, MappingReferenceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mappingResourceTypeEClass, MappingResourceType.class, "MappingResourceType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMappingResourceType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, MappingResourceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMappingResourceType_MapSimpleMember(), this.getMapSimpleMemberType(), null, "mapSimpleMember", null, 0, -1, MappingResourceType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getMappingResourceType_MapInstanceMember(), this.getMapInstanceMemberType(), null, "mapInstanceMember", null, 0, -1, MappingResourceType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getMappingResourceType_MapReferenceMember(), this.getMapReferenceMemberType(), null, "mapReferenceMember", null, 0, -1, MappingResourceType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getMappingResourceType_MapFixedMember(), this.getMapFixedMemberType(), null, "mapFixedMember", null, 0, -1, MappingResourceType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getMappingResourceType_MapEnumMember(), this.getMapEnumMemberType(), null, "mapEnumMember", null, 0, -1, MappingResourceType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getMappingResourceType_MapIdentifierMember(), this.getMapIdentifierMemberType(), null, "mapIdentifierMember", null, 0, -1, MappingResourceType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getMappingResourceType_MapArrayMember(), this.getMapArrayMemberType(), null, "mapArrayMember", null, 0, -1, MappingResourceType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getMappingResourceType_MapResourceMember(), this.getMapResourceMemberType(), null, "mapResourceMember", null, 0, -1, MappingResourceType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getMappingResourceType_MapBitmaskMember(), this.getMapBitmaskMemberType(), null, "mapBitmaskMember", null, 0, -1, MappingResourceType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getMappingResourceType_MapMemberFromType(), this.getMapMemberFromTypeType(), null, "mapMemberFromType", null, 0, -1, MappingResourceType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getMappingResourceType_MapIntoProperty(), this.getMapIntoPropertyType(), null, "mapIntoProperty", null, 0, -1, MappingResourceType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getMappingResourceType_Select(), this.getSelectType(), null, "select", null, 0, -1, MappingResourceType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getMappingResourceType_Headers(), this.getListOfStrings(), "headers", null, 0, 1, MappingResourceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMappingResourceType_Id(), theXMLTypePackage.getString(), "id", null, 0, 1, MappingResourceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMappingResourceType_Struct(), theXMLTypePackage.getString(), "struct", null, 0, 1, MappingResourceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mappingSimpleTypeEClass, MappingSimpleType.class, "MappingSimpleType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(mapReferenceElementTypeEClass, MapReferenceElementType.class, "MapReferenceElementType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(mapReferenceMemberTypeEClass, MapReferenceMemberType.class, "MapReferenceMemberType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapReferenceMemberType_Member(), theXMLTypePackage.getString(), "member", null, 1, 1, MapReferenceMemberType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMapReferenceMemberType_Property(), theXMLTypePackage.getString(), "property", null, 1, 1, MapReferenceMemberType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMapReferenceMemberType_SuppressDefault(), theXMLTypePackage.getBoolean(), "suppressDefault", "true", 0, 1, MapReferenceMemberType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapReferenceTypeTypeEClass, MapReferenceTypeType.class, "MapReferenceTypeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapReferenceTypeType_TypeId(), theXMLTypePackage.getString(), "typeId", null, 0, 1, MapReferenceTypeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapResourceElementTypeEClass, MapResourceElementType.class, "MapResourceElementType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapResourceElementType_InstanceIdentifyingMember(), theXMLTypePackage.getString(), "instanceIdentifyingMember", null, 0, 1, MapResourceElementType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapResourceMemberTypeEClass, MapResourceMemberType.class, "MapResourceMemberType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapResourceMemberType_Member(), theXMLTypePackage.getString(), "member", null, 1, 1, MapResourceMemberType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMapResourceMemberType_Property(), theXMLTypePackage.getString(), "property", null, 1, 1, MapResourceMemberType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMapResourceMemberType_SuppressDefault(), theXMLTypePackage.getBoolean(), "suppressDefault", "true", 0, 1, MapResourceMemberType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapResourceTypeEClass, MapResourceType.class, "MapResourceType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapResourceType_BaseName(), theXMLTypePackage.getString(), "baseName", null, 0, 1, MapResourceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMapResourceType_RssFile(), theXMLTypePackage.getString(), "rssFile", null, 0, 1, MapResourceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMapResourceType_Standalone(), this.getStandaloneType(), "standalone", "default", 0, 1, MapResourceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMapResourceType_Unnamed(), theXMLTypePackage.getBoolean(), "unnamed", "false", 0, 1, MapResourceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapResourceTypeTypeEClass, MapResourceTypeType.class, "MapResourceTypeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapResourceTypeType_TypeId(), theXMLTypePackage.getString(), "typeId", null, 0, 1, MapResourceTypeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapSimpleElementTypeEClass, MapSimpleElementType.class, "MapSimpleElementType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(mapSimpleMemberTypeEClass, MapSimpleMemberType.class, "MapSimpleMemberType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapSimpleMemberType_Member(), theXMLTypePackage.getString(), "member", null, 1, 1, MapSimpleMemberType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMapSimpleMemberType_Property(), theXMLTypePackage.getString(), "property", null, 1, 1, MapSimpleMemberType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMapSimpleMemberType_SuppressDefault(), theXMLTypePackage.getBoolean(), "suppressDefault", "true", 0, 1, MapSimpleMemberType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapSimpleTypeTypeEClass, MapSimpleTypeType.class, "MapSimpleTypeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapSimpleTypeType_TypeId(), theXMLTypePackage.getString(), "typeId", null, 0, 1, MapSimpleTypeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(propertiesTypeEClass, PropertiesType.class, "PropertiesType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPropertiesType_AbstractPropertyGroup(), ecorePackage.getEFeatureMapEntry(), "abstractPropertyGroup", null, 0, -1, PropertiesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPropertiesType_AbstractProperty(), this.getAbstractPropertyType(), null, "abstractProperty", null, 0, -1, PropertiesType.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(propertyOverridesTypeEClass, PropertyOverridesType.class, "PropertyOverridesType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPropertyOverridesType_PropertyOverride(), this.getPropertyOverrideType(), null, "propertyOverride", null, 0, -1, PropertyOverridesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(propertyOverrideTypeEClass, PropertyOverrideType.class, "PropertyOverrideType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPropertyOverrideType_Category(), theXMLTypePackage.getString(), "category", null, 0, 1, PropertyOverrideType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPropertyOverrideType_Default(), theXMLTypePackage.getString(), "default", null, 0, 1, PropertyOverrideType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPropertyOverrideType_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, PropertyOverrideType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPropertyOverrideType_ReadOnly(), theXMLTypePackage.getBoolean(), "readOnly", null, 0, 1, PropertyOverrideType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(scriptTypeEClass, ScriptType.class, "ScriptType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getScriptType_File(), theXMLTypePackage.getString(), "file", null, 1, 1, ScriptType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScriptType_Prototype(), theXMLTypePackage.getString(), "prototype", null, 1, 1, ScriptType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(selectTypeEClass, SelectType.class, "SelectType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSelectType_Choice(), this.getChoiceType(), null, "choice", null, 0, -1, SelectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSelectType_Attribute(), theXMLTypePackage.getString(), "attribute", null, 0, 1, SelectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSelectType_IsComponentInstanceOf(), theXMLTypePackage.getString(), "isComponentInstanceOf", null, 0, 1, SelectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSelectType_Property(), theXMLTypePackage.getString(), "property", null, 0, 1, SelectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSelectType_PropertyExists(), theXMLTypePackage.getString(), "propertyExists", null, 0, 1, SelectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(simplePropertyTypeEClass, SimplePropertyType.class, "SimplePropertyType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSimplePropertyType_Default(), theXMLTypePackage.getString(), "default", null, 0, 1, SimplePropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimplePropertyType_ExtendWithEnum(), theXMLTypePackage.getString(), "extendWithEnum", null, 0, 1, SimplePropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimplePropertyType_MaxValue(), theXMLTypePackage.getString(), "maxValue", null, 0, 1, SimplePropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimplePropertyType_MinValue(), theXMLTypePackage.getString(), "minValue", null, 0, 1, SimplePropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimplePropertyType_Type(), this.getPropertyDataType(), "type", "void", 1, 1, SimplePropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sourceGenTypeEClass, SourceGenType.class, "SourceGenType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSourceGenType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, SourceGenType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSourceGenType_DefineLocation(), this.getDefineLocationType(), null, "defineLocation", null, 0, -1, SourceGenType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getSourceGenType_Template(), this.getTemplateType(), null, "template", null, 0, -1, SourceGenType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getSourceGenType_TemplateGroup(), this.getTemplateGroupType(), null, "templateGroup", null, 0, -1, SourceGenType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getSourceGenType_UseTemplate(), this.getUseTemplateType(), null, "useTemplate", null, 0, -1, SourceGenType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getSourceGenType_UseTemplateGroup(), this.getUseTemplateGroupType(), null, "useTemplateGroup", null, 0, -1, SourceGenType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getSourceGenType_Inline(), this.getInlineType(), null, "inline", null, 0, -1, SourceGenType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getSourceGenType_Script(), this.getScriptType(), null, "script", null, 0, -1, SourceGenType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getSourceGenType_DefineMacro(), this.getDefineMacroType(), null, "defineMacro", null, 0, -1, SourceGenType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getSourceGenType_ExpandMacro(), this.getExpandMacroType(), null, "expandMacro", null, 0, -1, SourceGenType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getSourceGenType_Debug(), theXMLTypePackage.getBoolean(), "debug", "false", 0, 1, SourceGenType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSourceGenType_Forms(), theXMLTypePackage.getString(), "forms", null, 0, 1, SourceGenType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sourceMappingTypeEClass, SourceMappingType.class, "SourceMappingType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSourceMappingType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, SourceMappingType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSourceMappingType_MapResource(), this.getMapResourceType(), null, "mapResource", null, 0, -1, SourceMappingType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getSourceMappingType_Select(), this.getSelectType(), null, "select", null, 0, -1, SourceMappingType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(sourceTypeMappingTypeEClass, SourceTypeMappingType.class, "SourceTypeMappingType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSourceTypeMappingType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, SourceTypeMappingType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSourceTypeMappingType_MapResourceType(), this.getMapResourceTypeType(), null, "mapResourceType", null, 0, -1, SourceTypeMappingType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getSourceTypeMappingType_MapEnumType(), this.getMapEnumTypeType(), null, "mapEnumType", null, 0, -1, SourceTypeMappingType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getSourceTypeMappingType_MapSimpleType(), this.getMapSimpleTypeType(), null, "mapSimpleType", null, 0, -1, SourceTypeMappingType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getSourceTypeMappingType_MapFixedType(), this.getMapFixedTypeType(), null, "mapFixedType", null, 0, -1, SourceTypeMappingType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getSourceTypeMappingType_MapBitmaskType(), this.getMapBitmaskTypeType(), null, "mapBitmaskType", null, 0, -1, SourceTypeMappingType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getSourceTypeMappingType_MapIdentifierType(), this.getMapIdentifierTypeType(), null, "mapIdentifierType", null, 0, -1, SourceTypeMappingType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getSourceTypeMappingType_MapReferenceType(), this.getMapReferenceTypeType(), null, "mapReferenceType", null, 0, -1, SourceTypeMappingType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getSourceTypeMappingType_MapArrayType(), this.getMapArrayTypeType(), null, "mapArrayType", null, 0, -1, SourceTypeMappingType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getSourceTypeMappingType_Select(), this.getSelectType(), null, "select", null, 0, -1, SourceTypeMappingType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(symbianTypeEClass, SymbianType.class, "SymbianType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSymbianType_ClassHelpTopic(), theXMLTypePackage.getString(), "classHelpTopic", null, 0, 1, SymbianType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSymbianType_ClassName(), theXMLTypePackage.getString(), "className", null, 0, 1, SymbianType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSymbianType_MaxSDKVersion(), theXMLTypePackage.getString(), "maxSDKVersion", null, 0, 1, SymbianType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSymbianType_MinSDKVersion(), theXMLTypePackage.getString(), "minSDKVersion", null, 1, 1, SymbianType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSymbianType_ResourceHelpTopic(), theXMLTypePackage.getString(), "resourceHelpTopic", null, 0, 1, SymbianType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSymbianType_ResourceType(), theXMLTypePackage.getString(), "resourceType", null, 0, 1, SymbianType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSymbianType_SdkName(), theXMLTypePackage.getAnySimpleType(), "sdkName", null, 1, 1, SymbianType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(templateGroupTypeEClass, TemplateGroupType.class, "TemplateGroupType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTemplateGroupType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, TemplateGroupType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTemplateGroupType_DefineLocation(), this.getDefineLocationType(), null, "defineLocation", null, 0, -1, TemplateGroupType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getTemplateGroupType_Template(), this.getTemplateType(), null, "template", null, 0, -1, TemplateGroupType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getTemplateGroupType_Inline(), this.getInlineType(), null, "inline", null, 0, -1, TemplateGroupType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getTemplateGroupType_UseTemplate(), this.getUseTemplateType(), null, "useTemplate", null, 0, -1, TemplateGroupType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getTemplateGroupType_UseTemplateGroup(), this.getUseTemplateGroupType(), null, "useTemplateGroup", null, 0, -1, TemplateGroupType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getTemplateGroupType_ExpandMacro(), this.getExpandMacroType(), null, "expandMacro", null, 0, -1, TemplateGroupType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getTemplateGroupType_Form(), theXMLTypePackage.getString(), "form", null, 0, 1, TemplateGroupType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTemplateGroupType_Id(), theXMLTypePackage.getString(), "id", null, 0, 1, TemplateGroupType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTemplateGroupType_Location(), theXMLTypePackage.getString(), "location", null, 0, 1, TemplateGroupType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTemplateGroupType_Mode(), theXMLTypePackage.getString(), "mode", null, 0, 1, TemplateGroupType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTemplateGroupType_Phase(), theXMLTypePackage.getString(), "phase", null, 0, 1, TemplateGroupType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(templateTypeEClass, TemplateType.class, "TemplateType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTemplateType_Form(), theXMLTypePackage.getString(), "form", null, 0, 1, TemplateType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTemplateType_Id(), theXMLTypePackage.getString(), "id", null, 0, 1, TemplateType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTemplateType_Location(), theXMLTypePackage.getString(), "location", null, 0, 1, TemplateType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTemplateType_Mode(), theXMLTypePackage.getString(), "mode", null, 0, 1, TemplateType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTemplateType_Phase(), theXMLTypePackage.getString(), "phase", null, 0, 1, TemplateType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(twoWayMappingTypeEClass, TwoWayMappingType.class, "TwoWayMappingType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(useTemplateGroupTypeEClass, UseTemplateGroupType.class, "UseTemplateGroupType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getUseTemplateGroupType_UseTemplate(), this.getUseTemplateType(), null, "useTemplate", null, 0, -1, UseTemplateGroupType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUseTemplateGroupType_Ids(), theXMLTypePackage.getString(), "ids", null, 1, 1, UseTemplateGroupType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(useTemplateTypeEClass, UseTemplateType.class, "UseTemplateType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUseTemplateType_Ids(), theXMLTypePackage.getString(), "ids", null, 1, 1, UseTemplateType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(propertyDataTypeEEnum, PropertyDataType.class, "PropertyDataType");
		addEEnumLiteral(propertyDataTypeEEnum, PropertyDataType.VOID_LITERAL);
		addEEnumLiteral(propertyDataTypeEEnum, PropertyDataType.BOOLEAN_LITERAL);
		addEEnumLiteral(propertyDataTypeEEnum, PropertyDataType.INTEGER_LITERAL);
		addEEnumLiteral(propertyDataTypeEEnum, PropertyDataType.FLOAT_LITERAL);
		addEEnumLiteral(propertyDataTypeEEnum, PropertyDataType.STRING_LITERAL);
		addEEnumLiteral(propertyDataTypeEEnum, PropertyDataType.LOCALIZED_STRING_LITERAL);
		addEEnumLiteral(propertyDataTypeEEnum, PropertyDataType.UNIQUE_NAME_LITERAL);

		initEEnum(referenceScopeTypeEEnum, ReferenceScopeType.class, "ReferenceScopeType");
		addEEnumLiteral(referenceScopeTypeEEnum, ReferenceScopeType.MODEL_LITERAL);
		addEEnumLiteral(referenceScopeTypeEEnum, ReferenceScopeType.CHILDREN_LITERAL);
		addEEnumLiteral(referenceScopeTypeEEnum, ReferenceScopeType.SIBLINGS_LITERAL);

		initEEnum(standaloneTypeEEnum, StandaloneType.class, "StandaloneType");
		addEEnumLiteral(standaloneTypeEEnum, StandaloneType.FALSE_LITERAL);
		addEEnumLiteral(standaloneTypeEEnum, StandaloneType.TRUE_LITERAL);
		addEEnumLiteral(standaloneTypeEEnum, StandaloneType.NEVER_LITERAL);
		addEEnumLiteral(standaloneTypeEEnum, StandaloneType.DEFAULT_LITERAL);
		addEEnumLiteral(standaloneTypeEEnum, StandaloneType.ALWAYS_LITERAL);

		// Initialize data types
		initEDataType(listOfStringsEDataType, List.class, "ListOfStrings", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(propertyDataTypeObjectEDataType, PropertyDataType.class, "PropertyDataTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(referenceScopeTypeObjectEDataType, ReferenceScopeType.class, "ReferenceScopeTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(standaloneTypeObjectEDataType, StandaloneType.class, "StandaloneTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";				
		addAnnotation
		  (abstractPropertyTypeEClass, 
		   source, 
		   new String[] {
			 "name", "abstractPropertyType",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getAbstractPropertyType_Category(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "category"
		   });			
		addAnnotation
		  (getAbstractPropertyType_DescriptionKey(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "descriptionKey"
		   });			
		addAnnotation
		  (getAbstractPropertyType_DisplayName(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "displayName"
		   });			
		addAnnotation
		  (getAbstractPropertyType_EditorClass(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "editorClass"
		   });			
		addAnnotation
		  (getAbstractPropertyType_HelpKey(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "helpKey"
		   });			
		addAnnotation
		  (getAbstractPropertyType_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name"
		   });			
		addAnnotation
		  (getAbstractPropertyType_ReadOnly(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "readOnly"
		   });			
		addAnnotation
		  (getAbstractPropertyType_Resettable(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "resettable"
		   });			
		addAnnotation
		  (arrayPropertyTypeEClass, 
		   source, 
		   new String[] {
			 "name", "arrayPropertyType",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getArrayPropertyType_Type(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "type"
		   });		
		addAnnotation
		  (attributesTypeEClass, 
		   source, 
		   new String[] {
			 "name", "attributes_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getAttributesType_Attribute(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "attribute",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (attributeTypeEClass, 
		   source, 
		   new String[] {
			 "name", "attribute_._type",
			 "kind", "simple"
		   });		
		addAnnotation
		  (getAttributeType_Value(), 
		   source, 
		   new String[] {
			 "name", ":0",
			 "kind", "simple"
		   });			
		addAnnotation
		  (getAttributeType_Key(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "key"
		   });		
		addAnnotation
		  (choiceTypeEClass, 
		   source, 
		   new String[] {
			 "name", "choice_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getChoiceType_Group(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "group:0"
		   });		
		addAnnotation
		  (getChoiceType_TwoWayMappingGroup(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "twoWayMapping:group",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (getChoiceType_TwoWayMapping(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "twoWayMapping",
			 "namespace", "##targetNamespace",
			 "group", "twoWayMapping:group"
		   });			
		addAnnotation
		  (getChoiceType_MapResource(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapResource",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getChoiceType_Select(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "select",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getChoiceType_Value(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "value"
		   });		
		addAnnotation
		  (codeTypeEClass, 
		   source, 
		   new String[] {
			 "name", "code_._type",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getCodeType_Class(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "class"
		   });			
		addAnnotation
		  (getCodeType_Plugin(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "plugin"
		   });		
		addAnnotation
		  (componentDefinitionTypeEClass, 
		   source, 
		   new String[] {
			 "name", "componentDefinition_._type",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getComponentDefinitionType_CompoundPropertyDeclaration(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "compoundPropertyDeclaration",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getComponentDefinitionType_EnumPropertyDeclaration(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "enumPropertyDeclaration",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getComponentDefinitionType_Component(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "component",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (componentReferencePropertyTypeEClass, 
		   source, 
		   new String[] {
			 "name", "componentReferencePropertyType",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getComponentReferencePropertyType_Constraint(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "constraint"
		   });			
		addAnnotation
		  (getComponentReferencePropertyType_CreationKeys(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "creationKeys"
		   });			
		addAnnotation
		  (getComponentReferencePropertyType_PromoteReferenceProperties(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "promoteReferenceProperties"
		   });			
		addAnnotation
		  (getComponentReferencePropertyType_Scope(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "scope"
		   });		
		addAnnotation
		  (componentTypeEClass, 
		   source, 
		   new String[] {
			 "name", "component_._type",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getComponentType_Documentation(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "documentation",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getComponentType_Symbian(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "symbian",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getComponentType_DesignerImages(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "designerImages",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getComponentType_Attributes(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "attributes",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getComponentType_Properties(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "properties",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getComponentType_ExtensionProperties(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "extensionProperties",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getComponentType_PropertyOverrides(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "propertyOverrides",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getComponentType_Events(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "events",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getComponentType_SourceGen(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "sourceGen",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getComponentType_SourceMapping(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "sourceMapping",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getComponentType_Implementations(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "implementations",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getComponentType_Abstract(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "abstract"
		   });			
		addAnnotation
		  (getComponentType_BaseComponent(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "baseComponent"
		   });			
		addAnnotation
		  (getComponentType_Category(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "category"
		   });			
		addAnnotation
		  (getComponentType_FriendlyName(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "friendlyName"
		   });			
		addAnnotation
		  (getComponentType_InstanceNameRoot(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "instanceNameRoot"
		   });			
		addAnnotation
		  (getComponentType_QualifiedName(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "qualifiedName"
		   });			
		addAnnotation
		  (getComponentType_Version(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "version"
		   });		
		addAnnotation
		  (compoundPropertyDeclarationTypeEClass, 
		   source, 
		   new String[] {
			 "name", "compoundPropertyDeclaration_._type",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getCompoundPropertyDeclarationType_AbstractPropertyGroup(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "abstractProperty:group",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getCompoundPropertyDeclarationType_AbstractProperty(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "abstractProperty",
			 "namespace", "##targetNamespace",
			 "group", "abstractProperty:group"
		   });			
		addAnnotation
		  (getCompoundPropertyDeclarationType_SourceTypeMapping(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "sourceTypeMapping",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getCompoundPropertyDeclarationType_ConverterClass(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "converterClass"
		   });			
		addAnnotation
		  (getCompoundPropertyDeclarationType_EditableType(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "editableType"
		   });			
		addAnnotation
		  (getCompoundPropertyDeclarationType_EditorClass(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "editorClass"
		   });			
		addAnnotation
		  (getCompoundPropertyDeclarationType_QualifiedName(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "qualifiedName"
		   });			
		addAnnotation
		  (compoundPropertyTypeEClass, 
		   source, 
		   new String[] {
			 "name", "compoundPropertyType",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getCompoundPropertyType_Default(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "default"
		   });			
		addAnnotation
		  (getCompoundPropertyType_Type(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "type"
		   });		
		addAnnotation
		  (conditionalSourceGenEClass, 
		   source, 
		   new String[] {
			 "name", "ConditionalSourceGen",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getConditionalSourceGen_Forms(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "forms"
		   });			
		addAnnotation
		  (getConditionalSourceGen_IfEvents(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "ifEvents"
		   });			
		addAnnotation
		  (getConditionalSourceGen_IfExpr(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "ifExpr"
		   });			
		addAnnotation
		  (conditionalSourceGenStringEClass, 
		   source, 
		   new String[] {
			 "name", "ConditionalSourceGenString",
			 "kind", "simple"
		   });		
		addAnnotation
		  (getConditionalSourceGenString_Value(), 
		   source, 
		   new String[] {
			 "name", ":0",
			 "kind", "simple"
		   });			
		addAnnotation
		  (getConditionalSourceGenString_Forms(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "forms"
		   });			
		addAnnotation
		  (getConditionalSourceGenString_IfEvents(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "ifEvents"
		   });			
		addAnnotation
		  (getConditionalSourceGenString_IfExpr(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "ifExpr"
		   });		
		addAnnotation
		  (defineLocationTypeEClass, 
		   source, 
		   new String[] {
			 "name", "defineLocation_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getDefineLocationType_Group(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "group:0"
		   });			
		addAnnotation
		  (getDefineLocationType_Template(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "template",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getDefineLocationType_Inline(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "inline",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getDefineLocationType_Script(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "script",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getDefineLocationType_ExpandMacro(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "expandMacro",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getDefineLocationType_BaseLocation(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "baseLocation"
		   });			
		addAnnotation
		  (getDefineLocationType_Dir(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "dir"
		   });			
		addAnnotation
		  (getDefineLocationType_Domain(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "domain"
		   });			
		addAnnotation
		  (getDefineLocationType_File(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "file"
		   });			
		addAnnotation
		  (getDefineLocationType_Filter(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "filter"
		   });			
		addAnnotation
		  (getDefineLocationType_Id(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "id"
		   });			
		addAnnotation
		  (getDefineLocationType_IfEvents(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "ifEvents"
		   });			
		addAnnotation
		  (getDefineLocationType_IsEventHandler(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "isEventHandler"
		   });			
		addAnnotation
		  (getDefineLocationType_Location(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "location"
		   });			
		addAnnotation
		  (getDefineLocationType_Owned(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "owned"
		   });			
		addAnnotation
		  (getDefineLocationType_Realize(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "realize"
		   });		
		addAnnotation
		  (defineMacroTypeEClass, 
		   source, 
		   new String[] {
			 "name", "defineMacro_._type",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getDefineMacroType_ImportArguments(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "importArguments",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDefineMacroType_MacroArgument(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "macroArgument",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDefineMacroType_Group(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "group:2"
		   });			
		addAnnotation
		  (getDefineMacroType_Template(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "template",
			 "namespace", "##targetNamespace",
			 "group", "#group:2"
		   });			
		addAnnotation
		  (getDefineMacroType_Inline(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "inline",
			 "namespace", "##targetNamespace",
			 "group", "#group:2"
		   });			
		addAnnotation
		  (getDefineMacroType_DefineLocation(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "defineLocation",
			 "namespace", "##targetNamespace",
			 "group", "#group:2"
		   });			
		addAnnotation
		  (getDefineMacroType_ExpandMacro(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "expandMacro",
			 "namespace", "##targetNamespace",
			 "group", "#group:2"
		   });			
		addAnnotation
		  (getDefineMacroType_Help(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "help"
		   });			
		addAnnotation
		  (getDefineMacroType_Id(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "id"
		   });		
		addAnnotation
		  (designerImagesTypeEClass, 
		   source, 
		   new String[] {
			 "name", "designerImages_._type",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getDesignerImagesType_LargeIconFile(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "largeIconFile"
		   });			
		addAnnotation
		  (getDesignerImagesType_LayoutImageFile(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "layoutImageFile"
		   });			
		addAnnotation
		  (getDesignerImagesType_SmallIconFile(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "smallIconFile"
		   });			
		addAnnotation
		  (getDesignerImagesType_ThumbnailFile(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "thumbnailFile"
		   });		
		addAnnotation
		  (documentationTypeEClass, 
		   source, 
		   new String[] {
			 "name", "documentation_._type",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getDocumentationType_Information(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "information",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentationType_HelpTopic(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "helpTopic",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentationType_WizardDescription(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "wizardDescription",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (documentRootEClass, 
		   source, 
		   new String[] {
			 "name", "",
			 "kind", "mixed"
		   });		
		addAnnotation
		  (getDocumentRoot_Mixed(), 
		   source, 
		   new String[] {
			 "kind", "elementWildcard",
			 "name", ":mixed"
		   });		
		addAnnotation
		  (getDocumentRoot_XMLNSPrefixMap(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xmlns:prefix"
		   });		
		addAnnotation
		  (getDocumentRoot_XSISchemaLocation(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xsi:schemaLocation"
		   });		
		addAnnotation
		  (getDocumentRoot_AbstractProperty(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "abstractProperty",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_ArrayProperty(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "arrayProperty",
			 "namespace", "##targetNamespace",
			 "affiliation", "abstractProperty"
		   });		
		addAnnotation
		  (getDocumentRoot_Attribute(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "attribute",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_Attributes(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "attributes",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_Choice(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "choice",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_Code(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "code",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_Component(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "component",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_ComponentDefinition(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "componentDefinition",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_ComponentReferenceProperty(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "componentReferenceProperty",
			 "namespace", "##targetNamespace",
			 "affiliation", "abstractProperty"
		   });		
		addAnnotation
		  (getDocumentRoot_CompoundProperty(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "compoundProperty",
			 "namespace", "##targetNamespace",
			 "affiliation", "abstractProperty"
		   });			
		addAnnotation
		  (getDocumentRoot_CompoundPropertyDeclaration(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "compoundPropertyDeclaration",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_DefineLocation(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "defineLocation",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_DefineMacro(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "defineMacro",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_DesignerImages(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "designerImages",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_Documentation(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "documentation",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_EnumProperty(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "enumProperty",
			 "namespace", "##targetNamespace",
			 "affiliation", "abstractProperty"
		   });			
		addAnnotation
		  (getDocumentRoot_EnumPropertyDeclaration(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "enumPropertyDeclaration",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_Event(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "event",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_Events(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "events",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_ExpandArgument(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "expandArgument",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_ExpandMacro(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "expandMacro",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_ExtensionProperties(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "extensionProperties",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Implementation(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "implementation",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_Implementations(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "implementations",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_ImportArguments(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "importArguments",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_Inline(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "inline",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_MacroArgument(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "macroArgument",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_MapArrayMember(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapArrayMember",
			 "namespace", "##targetNamespace",
			 "affiliation", "twoWayMapping"
		   });		
		addAnnotation
		  (getDocumentRoot_TwoWayMapping(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "twoWayMapping",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_MapArrayType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapArrayType",
			 "namespace", "##targetNamespace",
			 "affiliation", "twoWayMapping"
		   });			
		addAnnotation
		  (getDocumentRoot_MapBitmaskElement(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapBitmaskElement",
			 "namespace", "##targetNamespace",
			 "affiliation", "twoWayMapping"
		   });			
		addAnnotation
		  (getDocumentRoot_MapBitmaskMember(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapBitmaskMember",
			 "namespace", "##targetNamespace",
			 "affiliation", "twoWayMapping"
		   });			
		addAnnotation
		  (getDocumentRoot_MapBitmaskType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapBitmaskType",
			 "namespace", "##targetNamespace",
			 "affiliation", "twoWayMapping"
		   });			
		addAnnotation
		  (getDocumentRoot_MapBitmaskValue(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapBitmaskValue",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_MapElementFromType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapElementFromType",
			 "namespace", "##targetNamespace",
			 "affiliation", "twoWayMapping"
		   });			
		addAnnotation
		  (getDocumentRoot_MapEnum(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapEnum",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_MapEnumElement(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapEnumElement",
			 "namespace", "##targetNamespace",
			 "affiliation", "twoWayMapping"
		   });			
		addAnnotation
		  (getDocumentRoot_MapEnumMember(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapEnumMember",
			 "namespace", "##targetNamespace",
			 "affiliation", "twoWayMapping"
		   });			
		addAnnotation
		  (getDocumentRoot_MapEnumType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapEnumType",
			 "namespace", "##targetNamespace",
			 "affiliation", "twoWayMapping"
		   });			
		addAnnotation
		  (getDocumentRoot_MapFixedElement(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapFixedElement",
			 "namespace", "##targetNamespace",
			 "affiliation", "twoWayMapping"
		   });			
		addAnnotation
		  (getDocumentRoot_MapFixedMember(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapFixedMember",
			 "namespace", "##targetNamespace",
			 "affiliation", "twoWayMapping"
		   });			
		addAnnotation
		  (getDocumentRoot_MapFixedType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapFixedType",
			 "namespace", "##targetNamespace",
			 "affiliation", "twoWayMapping"
		   });			
		addAnnotation
		  (getDocumentRoot_MapIdentifierElement(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapIdentifierElement",
			 "namespace", "##targetNamespace",
			 "affiliation", "twoWayMapping"
		   });			
		addAnnotation
		  (getDocumentRoot_MapIdentifierMember(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapIdentifierMember",
			 "namespace", "##targetNamespace",
			 "affiliation", "twoWayMapping"
		   });			
		addAnnotation
		  (getDocumentRoot_MapIdentifierType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapIdentifierType",
			 "namespace", "##targetNamespace",
			 "affiliation", "twoWayMapping"
		   });			
		addAnnotation
		  (getDocumentRoot_MapInstanceElement(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapInstanceElement",
			 "namespace", "##targetNamespace",
			 "affiliation", "twoWayMapping"
		   });			
		addAnnotation
		  (getDocumentRoot_MapInstanceMember(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapInstanceMember",
			 "namespace", "##targetNamespace",
			 "affiliation", "twoWayMapping"
		   });			
		addAnnotation
		  (getDocumentRoot_MapInstanceType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapInstanceType",
			 "namespace", "##targetNamespace",
			 "affiliation", "twoWayMapping"
		   });			
		addAnnotation
		  (getDocumentRoot_MapIntoProperty(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapIntoProperty",
			 "namespace", "##targetNamespace",
			 "affiliation", "twoWayMapping"
		   });			
		addAnnotation
		  (getDocumentRoot_MapMemberFromType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapMemberFromType",
			 "namespace", "##targetNamespace",
			 "affiliation", "twoWayMapping"
		   });			
		addAnnotation
		  (getDocumentRoot_MapReferenceElement(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapReferenceElement",
			 "namespace", "##targetNamespace",
			 "affiliation", "twoWayMapping"
		   });			
		addAnnotation
		  (getDocumentRoot_MapReferenceMember(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapReferenceMember",
			 "namespace", "##targetNamespace",
			 "affiliation", "twoWayMapping"
		   });			
		addAnnotation
		  (getDocumentRoot_MapReferenceType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapReferenceType",
			 "namespace", "##targetNamespace",
			 "affiliation", "twoWayMapping"
		   });			
		addAnnotation
		  (getDocumentRoot_MapResource(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapResource",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_MapResourceElement(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapResourceElement",
			 "namespace", "##targetNamespace",
			 "affiliation", "twoWayMapping"
		   });			
		addAnnotation
		  (getDocumentRoot_MapResourceMember(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapResourceMember",
			 "namespace", "##targetNamespace",
			 "affiliation", "twoWayMapping"
		   });			
		addAnnotation
		  (getDocumentRoot_MapResourceType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapResourceType",
			 "namespace", "##targetNamespace",
			 "affiliation", "twoWayMapping"
		   });			
		addAnnotation
		  (getDocumentRoot_MapSimpleElement(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapSimpleElement",
			 "namespace", "##targetNamespace",
			 "affiliation", "twoWayMapping"
		   });			
		addAnnotation
		  (getDocumentRoot_MapSimpleMember(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapSimpleMember",
			 "namespace", "##targetNamespace",
			 "affiliation", "twoWayMapping"
		   });			
		addAnnotation
		  (getDocumentRoot_MapSimpleType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapSimpleType",
			 "namespace", "##targetNamespace",
			 "affiliation", "twoWayMapping"
		   });			
		addAnnotation
		  (getDocumentRoot_Properties(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "properties",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Property(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "property",
			 "namespace", "##targetNamespace",
			 "affiliation", "abstractProperty"
		   });			
		addAnnotation
		  (getDocumentRoot_PropertyOverrides(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "propertyOverrides",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_Script(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "script",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_Select(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "select",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_SourceGen(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "sourceGen",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_SourceMapping(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "sourceMapping",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_SourceTypeMapping(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "sourceTypeMapping",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_Symbian(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "symbian",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_Template(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "template",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_TemplateGroup(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "templateGroup",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_UseTemplate(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "useTemplate",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getDocumentRoot_UseTemplateGroup(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "useTemplateGroup",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (enumElementTypeEClass, 
		   source, 
		   new String[] {
			 "name", "enumElement_._type",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getEnumElementType_DisplayValue(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "displayValue"
		   });			
		addAnnotation
		  (getEnumElementType_Value(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "value"
		   });		
		addAnnotation
		  (enumPropertyDeclarationTypeEClass, 
		   source, 
		   new String[] {
			 "name", "enumPropertyDeclaration_._type",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getEnumPropertyDeclarationType_EnumElement(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "enumElement",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getEnumPropertyDeclarationType_SourceTypeMapping(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "sourceTypeMapping",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getEnumPropertyDeclarationType_DefaultValue(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "defaultValue"
		   });			
		addAnnotation
		  (getEnumPropertyDeclarationType_QualifiedName(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "qualifiedName"
		   });			
		addAnnotation
		  (enumPropertyTypeEClass, 
		   source, 
		   new String[] {
			 "name", "enumPropertyType",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getEnumPropertyType_Default(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "default"
		   });			
		addAnnotation
		  (getEnumPropertyType_ExtendWithEnum(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "extendWithEnum"
		   });			
		addAnnotation
		  (getEnumPropertyType_Type(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "type"
		   });		
		addAnnotation
		  (eventsTypeEClass, 
		   source, 
		   new String[] {
			 "name", "events_._type",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getEventsType_Event(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "event",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getEventsType_DefaultEventName(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "defaultEventName"
		   });		
		addAnnotation
		  (eventTypeEClass, 
		   source, 
		   new String[] {
			 "name", "event_._type",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getEventType_Category(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "category"
		   });			
		addAnnotation
		  (getEventType_DescriptionKey(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "descriptionKey"
		   });			
		addAnnotation
		  (getEventType_DisplayName(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "displayName"
		   });			
		addAnnotation
		  (getEventType_Group(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "group"
		   });			
		addAnnotation
		  (getEventType_HandlerNameTemplate(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "handlerNameTemplate"
		   });			
		addAnnotation
		  (getEventType_HelpKey(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "helpKey"
		   });			
		addAnnotation
		  (getEventType_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name"
		   });		
		addAnnotation
		  (expandArgumentTypeEClass, 
		   source, 
		   new String[] {
			 "name", "expandArgument_._type",
			 "kind", "simple"
		   });		
		addAnnotation
		  (getExpandArgumentType_Value(), 
		   source, 
		   new String[] {
			 "name", ":0",
			 "kind", "simple"
		   });			
		addAnnotation
		  (getExpandArgumentType_Help(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "help"
		   });			
		addAnnotation
		  (getExpandArgumentType_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name"
		   });		
		addAnnotation
		  (expandMacroTypeEClass, 
		   source, 
		   new String[] {
			 "name", "expandMacro_._type",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getExpandMacroType_ExpandArgument(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "expandArgument",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getExpandMacroType_DontPassArguments(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "dontPassArguments"
		   });			
		addAnnotation
		  (getExpandMacroType_Help(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "help"
		   });			
		addAnnotation
		  (getExpandMacroType_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name"
		   });			
		addAnnotation
		  (getExpandMacroType_PassArguments(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "passArguments"
		   });			
		addAnnotation
		  (getExpandMacroType_AnyAttribute(), 
		   source, 
		   new String[] {
			 "kind", "attributeWildcard",
			 "wildcards", "##any",
			 "name", ":8",
			 "processing", "lax"
		   });		
		addAnnotation
		  (extensionPropertiesTypeEClass, 
		   source, 
		   new String[] {
			 "name", "extensionProperties_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getExtensionPropertiesType_AbstractPropertyGroup(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "abstractProperty:group",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getExtensionPropertiesType_AbstractProperty(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "abstractProperty",
			 "namespace", "##targetNamespace",
			 "group", "abstractProperty:group"
		   });			
		addAnnotation
		  (getExtensionPropertiesType_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name"
		   });		
		addAnnotation
		  (implementationsTypeEClass, 
		   source, 
		   new String[] {
			 "name", "implementations_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getImplementationsType_Implementation(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "implementation",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (implementationTypeEClass, 
		   source, 
		   new String[] {
			 "name", "implementation_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getImplementationType_Interface(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "interface",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getImplementationType_Code(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "code",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getImplementationType_Script(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "script",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (importArgumentsTypeEClass, 
		   source, 
		   new String[] {
			 "name", "importArguments_._type",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getImportArgumentsType_Arguments(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "arguments"
		   });			
		addAnnotation
		  (getImportArgumentsType_ExceptArguments(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "exceptArguments"
		   });			
		addAnnotation
		  (getImportArgumentsType_Help(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "help"
		   });			
		addAnnotation
		  (getImportArgumentsType_MacroName(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "macroName"
		   });		
		addAnnotation
		  (inlineTypeEClass, 
		   source, 
		   new String[] {
			 "name", "inline_._type",
			 "kind", "simple"
		   });			
		addAnnotation
		  (getInlineType_Id(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "id"
		   });			
		addAnnotation
		  (getInlineType_Scope(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "scope"
		   });		
		addAnnotation
		  (interfaceTypeEClass, 
		   source, 
		   new String[] {
			 "name", "interface_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getInterfaceType_Id(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "id"
		   });		
		addAnnotation
		  (listOfStringsEDataType, 
		   source, 
		   new String[] {
			 "name", "listOfStrings",
			 "itemType", "http://www.eclipse.org/emf/2003/XMLType#string"
		   });		
		addAnnotation
		  (macroArgumentTypeEClass, 
		   source, 
		   new String[] {
			 "name", "macroArgument_._type",
			 "kind", "simple"
		   });		
		addAnnotation
		  (getMacroArgumentType_Value(), 
		   source, 
		   new String[] {
			 "name", ":0",
			 "kind", "simple"
		   });			
		addAnnotation
		  (getMacroArgumentType_Default(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "default"
		   });			
		addAnnotation
		  (getMacroArgumentType_Help(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "help"
		   });			
		addAnnotation
		  (getMacroArgumentType_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name"
		   });			
		addAnnotation
		  (getMacroArgumentType_Optional(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "optional"
		   });		
		addAnnotation
		  (mapArrayMemberTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapArrayMember_._type",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getMapArrayMemberType_Member(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "member"
		   });			
		addAnnotation
		  (getMapArrayMemberType_Property(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "property"
		   });			
		addAnnotation
		  (getMapArrayMemberType_SuppressDefault(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "suppressDefault"
		   });		
		addAnnotation
		  (mapArrayTypeTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapArrayType_._type",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getMapArrayTypeType_TypeId(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "typeId"
		   });		
		addAnnotation
		  (mapBitmaskElementTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapBitmaskElement_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (mapBitmaskMemberTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapBitmaskMember_._type",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getMapBitmaskMemberType_Member(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "member"
		   });			
		addAnnotation
		  (getMapBitmaskMemberType_Property(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "property"
		   });			
		addAnnotation
		  (getMapBitmaskMemberType_SuppressDefault(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "suppressDefault"
		   });		
		addAnnotation
		  (mapBitmaskTypeTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapBitmaskType_._type",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getMapBitmaskTypeType_TypeId(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "typeId"
		   });		
		addAnnotation
		  (mapBitmaskValueTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapBitmaskValue_._type",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getMapBitmaskValueType_Properties(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "properties"
		   });			
		addAnnotation
		  (getMapBitmaskValueType_Value(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "value"
		   });		
		addAnnotation
		  (mapElementFromTypeTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapElementFromType_._type",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getMapElementFromTypeType_TypeId(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "typeId"
		   });		
		addAnnotation
		  (mapEnumElementTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapEnumElement_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (mapEnumMemberTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapEnumMember_._type",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getMapEnumMemberType_Member(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "member"
		   });			
		addAnnotation
		  (getMapEnumMemberType_Property(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "property"
		   });			
		addAnnotation
		  (getMapEnumMemberType_SuppressDefault(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "suppressDefault"
		   });		
		addAnnotation
		  (mapEnumTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapEnum_._type",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getMapEnumType_Enumerator(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "enumerator"
		   });			
		addAnnotation
		  (getMapEnumType_Value(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "value"
		   });		
		addAnnotation
		  (mapEnumTypeTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapEnumType_._type",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getMapEnumTypeType_TypeId(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "typeId"
		   });		
		addAnnotation
		  (mapFixedElementTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapFixedElement_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (mapFixedMemberTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapFixedMember_._type",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getMapFixedMemberType_Member(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "member"
		   });			
		addAnnotation
		  (getMapFixedMemberType_SuppressDefault(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "suppressDefault"
		   });		
		addAnnotation
		  (mapFixedTypeTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapFixedType_._type",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getMapFixedTypeType_TypeId(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "typeId"
		   });		
		addAnnotation
		  (mapIdentifierElementTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapIdentifierElement_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (mapIdentifierMemberTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapIdentifierMember_._type",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getMapIdentifierMemberType_Member(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "member"
		   });			
		addAnnotation
		  (getMapIdentifierMemberType_Property(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "property"
		   });			
		addAnnotation
		  (getMapIdentifierMemberType_SuppressDefault(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "suppressDefault"
		   });		
		addAnnotation
		  (mapIdentifierTypeTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapIdentifierType_._type",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getMapIdentifierTypeType_TypeId(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "typeId"
		   });		
		addAnnotation
		  (mapInstanceElementTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapInstanceElement_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (mapInstanceMemberTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapInstanceMember_._type",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getMapInstanceMemberType_Member(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "member"
		   });			
		addAnnotation
		  (getMapInstanceMemberType_Property(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "property"
		   });			
		addAnnotation
		  (getMapInstanceMemberType_SuppressDefault(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "suppressDefault"
		   });		
		addAnnotation
		  (mapInstanceTypeTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapInstanceType_._type",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getMapInstanceTypeType_TypeId(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "typeId"
		   });		
		addAnnotation
		  (mapIntoPropertyTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapIntoProperty_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getMapIntoPropertyType_TwoWayMappingGroup(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "twoWayMapping:group",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getMapIntoPropertyType_TwoWayMapping(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "twoWayMapping",
			 "namespace", "##targetNamespace",
			 "group", "twoWayMapping:group"
		   });			
		addAnnotation
		  (getMapIntoPropertyType_Property(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "property"
		   });		
		addAnnotation
		  (mapMemberFromTypeTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapMemberFromType_._type",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getMapMemberFromTypeType_Member(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "member"
		   });			
		addAnnotation
		  (getMapMemberFromTypeType_Property(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "property"
		   });			
		addAnnotation
		  (getMapMemberFromTypeType_SuppressDefault(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "suppressDefault"
		   });			
		addAnnotation
		  (getMapMemberFromTypeType_TypeId(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "typeId"
		   });			
		addAnnotation
		  (mappingArrayTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mappingArrayType",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getMappingArrayType_TwoWayMappingGroup(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "twoWayMapping:group",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getMappingArrayType_TwoWayMapping(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "twoWayMapping",
			 "namespace", "##targetNamespace",
			 "group", "twoWayMapping:group"
		   });			
		addAnnotation
		  (getMappingArrayType_Select(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "select",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (mappingBitmaskTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mappingBitmaskType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getMappingBitmaskType_Group(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "group:0"
		   });			
		addAnnotation
		  (getMappingBitmaskType_MapBitmaskValue(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapBitmaskValue",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getMappingBitmaskType_IncludedProperties(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "includedProperties"
		   });			
		addAnnotation
		  (mappingEnumTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mappingEnumType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getMappingEnumType_Group(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "group:0"
		   });			
		addAnnotation
		  (getMappingEnumType_MapEnum(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapEnum",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getMappingEnumType_Enumeration(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "enumeration"
		   });			
		addAnnotation
		  (getMappingEnumType_Headers(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "headers"
		   });			
		addAnnotation
		  (getMappingEnumType_NameAlgorithm(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "nameAlgorithm"
		   });			
		addAnnotation
		  (getMappingEnumType_UniqueValue(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "uniqueValue"
		   });			
		addAnnotation
		  (getMappingEnumType_Validate(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "validate"
		   });			
		addAnnotation
		  (mappingFixedTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mappingFixedType",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getMappingFixedType_Value(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "value"
		   });			
		addAnnotation
		  (mappingIdentifierTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mappingIdentifierType",
			 "kind", "empty"
		   });			
		addAnnotation
		  (mappingInstanceTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mappingInstanceType",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getMappingInstanceType_RsrcId(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "rsrcId"
		   });			
		addAnnotation
		  (mappingReferenceTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mappingReferenceType",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getMappingReferenceType_RsrcId(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "rsrcId"
		   });			
		addAnnotation
		  (mappingResourceTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mappingResourceType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getMappingResourceType_Group(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "group:0"
		   });			
		addAnnotation
		  (getMappingResourceType_MapSimpleMember(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapSimpleMember",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getMappingResourceType_MapInstanceMember(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapInstanceMember",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getMappingResourceType_MapReferenceMember(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapReferenceMember",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getMappingResourceType_MapFixedMember(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapFixedMember",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getMappingResourceType_MapEnumMember(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapEnumMember",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getMappingResourceType_MapIdentifierMember(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapIdentifierMember",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getMappingResourceType_MapArrayMember(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapArrayMember",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getMappingResourceType_MapResourceMember(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapResourceMember",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getMappingResourceType_MapBitmaskMember(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapBitmaskMember",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getMappingResourceType_MapMemberFromType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapMemberFromType",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getMappingResourceType_MapIntoProperty(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapIntoProperty",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getMappingResourceType_Select(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "select",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getMappingResourceType_Headers(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "headers"
		   });			
		addAnnotation
		  (getMappingResourceType_Id(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "id"
		   });			
		addAnnotation
		  (getMappingResourceType_Struct(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "struct"
		   });			
		addAnnotation
		  (mappingSimpleTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mappingSimpleType",
			 "kind", "empty"
		   });		
		addAnnotation
		  (mapReferenceElementTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapReferenceElement_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (mapReferenceMemberTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapReferenceMember_._type",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getMapReferenceMemberType_Member(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "member"
		   });			
		addAnnotation
		  (getMapReferenceMemberType_Property(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "property"
		   });			
		addAnnotation
		  (getMapReferenceMemberType_SuppressDefault(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "suppressDefault"
		   });		
		addAnnotation
		  (mapReferenceTypeTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapReferenceType_._type",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getMapReferenceTypeType_TypeId(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "typeId"
		   });		
		addAnnotation
		  (mapResourceElementTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapResourceElement_._type",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getMapResourceElementType_InstanceIdentifyingMember(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "instanceIdentifyingMember"
		   });		
		addAnnotation
		  (mapResourceMemberTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapResourceMember_._type",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getMapResourceMemberType_Member(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "member"
		   });			
		addAnnotation
		  (getMapResourceMemberType_Property(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "property"
		   });			
		addAnnotation
		  (getMapResourceMemberType_SuppressDefault(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "suppressDefault"
		   });		
		addAnnotation
		  (mapResourceTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapResource_._type",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getMapResourceType_BaseName(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "baseName"
		   });			
		addAnnotation
		  (getMapResourceType_RssFile(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "rssFile"
		   });			
		addAnnotation
		  (getMapResourceType_Standalone(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "standalone"
		   });			
		addAnnotation
		  (getMapResourceType_Unnamed(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "unnamed"
		   });		
		addAnnotation
		  (mapResourceTypeTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapResourceType_._type",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getMapResourceTypeType_TypeId(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "typeId"
		   });		
		addAnnotation
		  (mapSimpleElementTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapSimpleElement_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (mapSimpleMemberTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapSimpleMember_._type",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getMapSimpleMemberType_Member(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "member"
		   });			
		addAnnotation
		  (getMapSimpleMemberType_Property(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "property"
		   });			
		addAnnotation
		  (getMapSimpleMemberType_SuppressDefault(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "suppressDefault"
		   });		
		addAnnotation
		  (mapSimpleTypeTypeEClass, 
		   source, 
		   new String[] {
			 "name", "mapSimpleType_._type",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getMapSimpleTypeType_TypeId(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "typeId"
		   });		
		addAnnotation
		  (propertiesTypeEClass, 
		   source, 
		   new String[] {
			 "name", "properties_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getPropertiesType_AbstractPropertyGroup(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "abstractProperty:group",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getPropertiesType_AbstractProperty(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "abstractProperty",
			 "namespace", "##targetNamespace",
			 "group", "abstractProperty:group"
		   });			
		addAnnotation
		  (propertyDataTypeEEnum, 
		   source, 
		   new String[] {
			 "name", "propertyDataType"
		   });									
		addAnnotation
		  (propertyDataTypeObjectEDataType, 
		   source, 
		   new String[] {
			 "name", "propertyDataType:Object",
			 "baseType", "propertyDataType"
		   });		
		addAnnotation
		  (propertyOverridesTypeEClass, 
		   source, 
		   new String[] {
			 "name", "propertyOverrides_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getPropertyOverridesType_PropertyOverride(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "propertyOverride",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (propertyOverrideTypeEClass, 
		   source, 
		   new String[] {
			 "name", "propertyOverride_._type",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getPropertyOverrideType_Category(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "category"
		   });			
		addAnnotation
		  (getPropertyOverrideType_Default(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "default"
		   });			
		addAnnotation
		  (getPropertyOverrideType_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name"
		   });			
		addAnnotation
		  (getPropertyOverrideType_ReadOnly(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "readOnly"
		   });			
		addAnnotation
		  (referenceScopeTypeEEnum, 
		   source, 
		   new String[] {
			 "name", "referenceScopeType"
		   });					
		addAnnotation
		  (referenceScopeTypeObjectEDataType, 
		   source, 
		   new String[] {
			 "name", "referenceScopeType:Object",
			 "baseType", "referenceScopeType"
		   });		
		addAnnotation
		  (scriptTypeEClass, 
		   source, 
		   new String[] {
			 "name", "script_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getScriptType_File(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "file"
		   });		
		addAnnotation
		  (getScriptType_Prototype(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "prototype"
		   });		
		addAnnotation
		  (selectTypeEClass, 
		   source, 
		   new String[] {
			 "name", "select_._type",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getSelectType_Choice(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "choice",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getSelectType_Attribute(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "attribute"
		   });			
		addAnnotation
		  (getSelectType_IsComponentInstanceOf(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "isComponentInstanceOf"
		   });			
		addAnnotation
		  (getSelectType_Property(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "property"
		   });			
		addAnnotation
		  (getSelectType_PropertyExists(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "propertyExists"
		   });			
		addAnnotation
		  (simplePropertyTypeEClass, 
		   source, 
		   new String[] {
			 "name", "simplePropertyType",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getSimplePropertyType_Default(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "default"
		   });			
		addAnnotation
		  (getSimplePropertyType_ExtendWithEnum(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "extendWithEnum"
		   });			
		addAnnotation
		  (getSimplePropertyType_MaxValue(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "maxValue"
		   });			
		addAnnotation
		  (getSimplePropertyType_MinValue(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "minValue"
		   });			
		addAnnotation
		  (getSimplePropertyType_Type(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "type"
		   });		
		addAnnotation
		  (sourceGenTypeEClass, 
		   source, 
		   new String[] {
			 "name", "sourceGen_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getSourceGenType_Group(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "group:0"
		   });			
		addAnnotation
		  (getSourceGenType_DefineLocation(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "defineLocation",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getSourceGenType_Template(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "template",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getSourceGenType_TemplateGroup(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "templateGroup",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getSourceGenType_UseTemplate(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "useTemplate",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getSourceGenType_UseTemplateGroup(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "useTemplateGroup",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getSourceGenType_Inline(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "inline",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getSourceGenType_Script(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "script",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getSourceGenType_DefineMacro(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "defineMacro",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getSourceGenType_ExpandMacro(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "expandMacro",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (getSourceGenType_Debug(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "debug"
		   });		
		addAnnotation
		  (getSourceGenType_Forms(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "forms"
		   });		
		addAnnotation
		  (sourceMappingTypeEClass, 
		   source, 
		   new String[] {
			 "name", "sourceMapping_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getSourceMappingType_Group(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "group:0"
		   });			
		addAnnotation
		  (getSourceMappingType_MapResource(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapResource",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getSourceMappingType_Select(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "select",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (sourceTypeMappingTypeEClass, 
		   source, 
		   new String[] {
			 "name", "sourceTypeMapping_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getSourceTypeMappingType_Group(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "group:0"
		   });			
		addAnnotation
		  (getSourceTypeMappingType_MapResourceType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapResourceType",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getSourceTypeMappingType_MapEnumType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapEnumType",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getSourceTypeMappingType_MapSimpleType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapSimpleType",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getSourceTypeMappingType_MapFixedType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapFixedType",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getSourceTypeMappingType_MapBitmaskType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapBitmaskType",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getSourceTypeMappingType_MapIdentifierType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapIdentifierType",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getSourceTypeMappingType_MapReferenceType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapReferenceType",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getSourceTypeMappingType_MapArrayType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mapArrayType",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (getSourceTypeMappingType_Select(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "select",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });			
		addAnnotation
		  (standaloneTypeEEnum, 
		   source, 
		   new String[] {
			 "name", "standaloneType"
		   });							
		addAnnotation
		  (standaloneTypeObjectEDataType, 
		   source, 
		   new String[] {
			 "name", "standaloneType:Object",
			 "baseType", "standaloneType"
		   });		
		addAnnotation
		  (symbianTypeEClass, 
		   source, 
		   new String[] {
			 "name", "symbian_._type",
			 "kind", "empty"
		   });			
		addAnnotation
		  (getSymbianType_ClassHelpTopic(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "classHelpTopic"
		   });			
		addAnnotation
		  (getSymbianType_ClassName(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "className"
		   });			
		addAnnotation
		  (getSymbianType_MaxSDKVersion(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "maxSDKVersion"
		   });			
		addAnnotation
		  (getSymbianType_MinSDKVersion(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "minSDKVersion"
		   });			
		addAnnotation
		  (getSymbianType_ResourceHelpTopic(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "resourceHelpTopic"
		   });			
		addAnnotation
		  (getSymbianType_ResourceType(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "resourceType"
		   });			
		addAnnotation
		  (getSymbianType_SdkName(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "sdkName"
		   });		
		addAnnotation
		  (templateGroupTypeEClass, 
		   source, 
		   new String[] {
			 "name", "templateGroup_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getTemplateGroupType_Group(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "group:3"
		   });			
		addAnnotation
		  (getTemplateGroupType_DefineLocation(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "defineLocation",
			 "namespace", "##targetNamespace",
			 "group", "#group:3"
		   });			
		addAnnotation
		  (getTemplateGroupType_Template(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "template",
			 "namespace", "##targetNamespace",
			 "group", "#group:3"
		   });			
		addAnnotation
		  (getTemplateGroupType_Inline(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "inline",
			 "namespace", "##targetNamespace",
			 "group", "#group:3"
		   });			
		addAnnotation
		  (getTemplateGroupType_UseTemplate(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "useTemplate",
			 "namespace", "##targetNamespace",
			 "group", "#group:3"
		   });			
		addAnnotation
		  (getTemplateGroupType_UseTemplateGroup(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "useTemplateGroup",
			 "namespace", "##targetNamespace",
			 "group", "#group:3"
		   });			
		addAnnotation
		  (getTemplateGroupType_ExpandMacro(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "expandMacro",
			 "namespace", "##targetNamespace",
			 "group", "#group:3"
		   });		
		addAnnotation
		  (getTemplateGroupType_Form(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "form"
		   });		
		addAnnotation
		  (getTemplateGroupType_Id(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "id"
		   });		
		addAnnotation
		  (getTemplateGroupType_Location(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "location"
		   });		
		addAnnotation
		  (getTemplateGroupType_Mode(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "mode"
		   });		
		addAnnotation
		  (getTemplateGroupType_Phase(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "phase"
		   });		
		addAnnotation
		  (templateTypeEClass, 
		   source, 
		   new String[] {
			 "name", "template_._type",
			 "kind", "simple"
		   });			
		addAnnotation
		  (getTemplateType_Form(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "form"
		   });			
		addAnnotation
		  (getTemplateType_Id(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "id"
		   });			
		addAnnotation
		  (getTemplateType_Location(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "location"
		   });			
		addAnnotation
		  (getTemplateType_Mode(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "mode"
		   });			
		addAnnotation
		  (getTemplateType_Phase(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "phase"
		   });			
		addAnnotation
		  (twoWayMappingTypeEClass, 
		   source, 
		   new String[] {
			 "name", "twoWayMappingType",
			 "kind", "empty"
		   });		
		addAnnotation
		  (useTemplateGroupTypeEClass, 
		   source, 
		   new String[] {
			 "name", "useTemplateGroup_._type",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getUseTemplateGroupType_UseTemplate(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "useTemplate",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getUseTemplateGroupType_Ids(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "ids"
		   });		
		addAnnotation
		  (useTemplateTypeEClass, 
		   source, 
		   new String[] {
			 "name", "useTemplate_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getUseTemplateType_Ids(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "ids"
		   });
	}

} //ComponentPackageImpl
