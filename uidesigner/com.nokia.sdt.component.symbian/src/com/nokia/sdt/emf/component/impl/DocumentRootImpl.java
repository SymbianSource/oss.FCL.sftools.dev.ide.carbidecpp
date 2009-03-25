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
import com.nokia.sdt.emf.component.PropertiesType;
import com.nokia.sdt.emf.component.PropertyOverridesType;
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

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getAbstractProperty <em>Abstract Property</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getArrayProperty <em>Array Property</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getChoice <em>Choice</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getCode <em>Code</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getComponent <em>Component</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getComponentDefinition <em>Component Definition</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getComponentReferenceProperty <em>Component Reference Property</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getCompoundProperty <em>Compound Property</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getCompoundPropertyDeclaration <em>Compound Property Declaration</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getDefineLocation <em>Define Location</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getDefineMacro <em>Define Macro</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getDesignerImages <em>Designer Images</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getEnumProperty <em>Enum Property</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getEnumPropertyDeclaration <em>Enum Property Declaration</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getEvent <em>Event</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getEvents <em>Events</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getExpandArgument <em>Expand Argument</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getExpandMacro <em>Expand Macro</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getExtensionProperties <em>Extension Properties</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getImplementation <em>Implementation</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getImplementations <em>Implementations</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getImportArguments <em>Import Arguments</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getInline <em>Inline</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMacroArgument <em>Macro Argument</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapArrayMember <em>Map Array Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getTwoWayMapping <em>Two Way Mapping</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapArrayType <em>Map Array Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapBitmaskElement <em>Map Bitmask Element</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapBitmaskMember <em>Map Bitmask Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapBitmaskType <em>Map Bitmask Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapBitmaskValue <em>Map Bitmask Value</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapElementFromType <em>Map Element From Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapEnum <em>Map Enum</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapEnumElement <em>Map Enum Element</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapEnumMember <em>Map Enum Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapEnumType <em>Map Enum Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapFixedElement <em>Map Fixed Element</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapFixedMember <em>Map Fixed Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapFixedType <em>Map Fixed Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapIdentifierElement <em>Map Identifier Element</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapIdentifierMember <em>Map Identifier Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapIdentifierType <em>Map Identifier Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapInstanceElement <em>Map Instance Element</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapInstanceMember <em>Map Instance Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapInstanceType <em>Map Instance Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapIntoProperty <em>Map Into Property</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapMemberFromType <em>Map Member From Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapReferenceElement <em>Map Reference Element</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapReferenceMember <em>Map Reference Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapReferenceType <em>Map Reference Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapResource <em>Map Resource</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapResourceElement <em>Map Resource Element</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapResourceMember <em>Map Resource Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapResourceType <em>Map Resource Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapSimpleElement <em>Map Simple Element</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapSimpleMember <em>Map Simple Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getMapSimpleType <em>Map Simple Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getProperty <em>Property</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getPropertyOverrides <em>Property Overrides</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getScript <em>Script</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getSelect <em>Select</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getSourceGen <em>Source Gen</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getSourceMapping <em>Source Mapping</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getSourceTypeMapping <em>Source Type Mapping</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getSymbian <em>Symbian</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getTemplate <em>Template</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getTemplateGroup <em>Template Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getUseTemplate <em>Use Template</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl#getUseTemplateGroup <em>Use Template Group</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DocumentRootImpl extends EObjectImpl implements DocumentRoot {
	/**
	 * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMixed()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap mixed;

	/**
	 * The cached value of the '{@link #getXMLNSPrefixMap() <em>XMLNS Prefix Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXMLNSPrefixMap()
	 * @generated
	 * @ordered
	 */
	protected EMap xMLNSPrefixMap;

	/**
	 * The cached value of the '{@link #getXSISchemaLocation() <em>XSI Schema Location</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXSISchemaLocation()
	 * @generated
	 * @ordered
	 */
	protected EMap xSISchemaLocation;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DocumentRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.DOCUMENT_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, ComponentPackage.DOCUMENT_ROOT__MIXED);
		}
		return mixed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap getXMLNSPrefixMap() {
		if (xMLNSPrefixMap == null) {
			xMLNSPrefixMap = new EcoreEMap(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, ComponentPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		}
		return xMLNSPrefixMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap getXSISchemaLocation() {
		if (xSISchemaLocation == null) {
			xSISchemaLocation = new EcoreEMap(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, ComponentPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		}
		return xSISchemaLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractPropertyType getAbstractProperty() {
		return (AbstractPropertyType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__ABSTRACT_PROPERTY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAbstractProperty(AbstractPropertyType newAbstractProperty, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__ABSTRACT_PROPERTY, newAbstractProperty, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayPropertyType getArrayProperty() {
		return (ArrayPropertyType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__ARRAY_PROPERTY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetArrayProperty(ArrayPropertyType newArrayProperty, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__ARRAY_PROPERTY, newArrayProperty, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArrayProperty(ArrayPropertyType newArrayProperty) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__ARRAY_PROPERTY, newArrayProperty);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public AttributeType getAttribute() {
		return (AttributeType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__ATTRIBUTE, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetAttribute(AttributeType newAttribute, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__ATTRIBUTE, newAttribute, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setAttribute(AttributeType newAttribute) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__ATTRIBUTE, newAttribute);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributesType getAttributes() {
		return (AttributesType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__ATTRIBUTES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAttributes(AttributesType newAttributes, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__ATTRIBUTES, newAttributes, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttributes(AttributesType newAttributes) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__ATTRIBUTES, newAttributes);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public ChoiceType getChoice() {
		return (ChoiceType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__CHOICE, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetChoice(ChoiceType newChoice, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__CHOICE, newChoice, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setChoice(ChoiceType newChoice) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__CHOICE, newChoice);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public CodeType getCode() {
		return (CodeType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__CODE, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetCode(CodeType newCode, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__CODE, newCode, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setCode(CodeType newCode) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__CODE, newCode);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentType getComponent() {
		return (ComponentType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__COMPONENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetComponent(ComponentType newComponent, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__COMPONENT, newComponent, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponent(ComponentType newComponent) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__COMPONENT, newComponent);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentDefinitionType getComponentDefinition() {
		return (ComponentDefinitionType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__COMPONENT_DEFINITION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetComponentDefinition(ComponentDefinitionType newComponentDefinition, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__COMPONENT_DEFINITION, newComponentDefinition, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponentDefinition(ComponentDefinitionType newComponentDefinition) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__COMPONENT_DEFINITION, newComponentDefinition);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentReferencePropertyType getComponentReferenceProperty() {
		return (ComponentReferencePropertyType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__COMPONENT_REFERENCE_PROPERTY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetComponentReferenceProperty(ComponentReferencePropertyType newComponentReferenceProperty, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__COMPONENT_REFERENCE_PROPERTY, newComponentReferenceProperty, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponentReferenceProperty(ComponentReferencePropertyType newComponentReferenceProperty) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__COMPONENT_REFERENCE_PROPERTY, newComponentReferenceProperty);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompoundPropertyType getCompoundProperty() {
		return (CompoundPropertyType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__COMPOUND_PROPERTY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCompoundProperty(CompoundPropertyType newCompoundProperty, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__COMPOUND_PROPERTY, newCompoundProperty, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCompoundProperty(CompoundPropertyType newCompoundProperty) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__COMPOUND_PROPERTY, newCompoundProperty);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompoundPropertyDeclarationType getCompoundPropertyDeclaration() {
		return (CompoundPropertyDeclarationType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__COMPOUND_PROPERTY_DECLARATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCompoundPropertyDeclaration(CompoundPropertyDeclarationType newCompoundPropertyDeclaration, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__COMPOUND_PROPERTY_DECLARATION, newCompoundPropertyDeclaration, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCompoundPropertyDeclaration(CompoundPropertyDeclarationType newCompoundPropertyDeclaration) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__COMPOUND_PROPERTY_DECLARATION, newCompoundPropertyDeclaration);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public DefineLocationType getDefineLocation() {
		return (DefineLocationType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__DEFINE_LOCATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetDefineLocation(DefineLocationType newDefineLocation, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__DEFINE_LOCATION, newDefineLocation, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setDefineLocation(DefineLocationType newDefineLocation) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__DEFINE_LOCATION, newDefineLocation);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DefineMacroType getDefineMacro() {
		return (DefineMacroType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__DEFINE_MACRO, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDefineMacro(DefineMacroType newDefineMacro, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__DEFINE_MACRO, newDefineMacro, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefineMacro(DefineMacroType newDefineMacro) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__DEFINE_MACRO, newDefineMacro);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DesignerImagesType getDesignerImages() {
		return (DesignerImagesType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__DESIGNER_IMAGES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDesignerImages(DesignerImagesType newDesignerImages, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__DESIGNER_IMAGES, newDesignerImages, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDesignerImages(DesignerImagesType newDesignerImages) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__DESIGNER_IMAGES, newDesignerImages);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocumentationType getDocumentation() {
		return (DocumentationType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__DOCUMENTATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDocumentation(DocumentationType newDocumentation, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__DOCUMENTATION, newDocumentation, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDocumentation(DocumentationType newDocumentation) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__DOCUMENTATION, newDocumentation);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumPropertyType getEnumProperty() {
		return (EnumPropertyType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__ENUM_PROPERTY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEnumProperty(EnumPropertyType newEnumProperty, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__ENUM_PROPERTY, newEnumProperty, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnumProperty(EnumPropertyType newEnumProperty) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__ENUM_PROPERTY, newEnumProperty);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumPropertyDeclarationType getEnumPropertyDeclaration() {
		return (EnumPropertyDeclarationType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__ENUM_PROPERTY_DECLARATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEnumPropertyDeclaration(EnumPropertyDeclarationType newEnumPropertyDeclaration, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__ENUM_PROPERTY_DECLARATION, newEnumPropertyDeclaration, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnumPropertyDeclaration(EnumPropertyDeclarationType newEnumPropertyDeclaration) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__ENUM_PROPERTY_DECLARATION, newEnumPropertyDeclaration);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventType getEvent() {
		return (EventType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__EVENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEvent(EventType newEvent, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__EVENT, newEvent, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEvent(EventType newEvent) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__EVENT, newEvent);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventsType getEvents() {
		return (EventsType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__EVENTS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEvents(EventsType newEvents, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__EVENTS, newEvents, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEvents(EventsType newEvents) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__EVENTS, newEvents);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExpandArgumentType getExpandArgument() {
		return (ExpandArgumentType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__EXPAND_ARGUMENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExpandArgument(ExpandArgumentType newExpandArgument, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__EXPAND_ARGUMENT, newExpandArgument, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExpandArgument(ExpandArgumentType newExpandArgument) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__EXPAND_ARGUMENT, newExpandArgument);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExpandMacroType getExpandMacro() {
		return (ExpandMacroType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__EXPAND_MACRO, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExpandMacro(ExpandMacroType newExpandMacro, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__EXPAND_MACRO, newExpandMacro, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExpandMacro(ExpandMacroType newExpandMacro) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__EXPAND_MACRO, newExpandMacro);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExtensionPropertiesType getExtensionProperties() {
		return (ExtensionPropertiesType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__EXTENSION_PROPERTIES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExtensionProperties(ExtensionPropertiesType newExtensionProperties, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__EXTENSION_PROPERTIES, newExtensionProperties, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtensionProperties(ExtensionPropertiesType newExtensionProperties) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__EXTENSION_PROPERTIES, newExtensionProperties);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImplementationType getImplementation() {
		return (ImplementationType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__IMPLEMENTATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetImplementation(ImplementationType newImplementation, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__IMPLEMENTATION, newImplementation, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImplementation(ImplementationType newImplementation) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__IMPLEMENTATION, newImplementation);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImplementationsType getImplementations() {
		return (ImplementationsType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__IMPLEMENTATIONS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetImplementations(ImplementationsType newImplementations, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__IMPLEMENTATIONS, newImplementations, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImplementations(ImplementationsType newImplementations) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__IMPLEMENTATIONS, newImplementations);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImportArgumentsType getImportArguments() {
		return (ImportArgumentsType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__IMPORT_ARGUMENTS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetImportArguments(ImportArgumentsType newImportArguments, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__IMPORT_ARGUMENTS, newImportArguments, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImportArguments(ImportArgumentsType newImportArguments) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__IMPORT_ARGUMENTS, newImportArguments);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public InlineType getInline() {
		return (InlineType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__INLINE, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetInline(InlineType newInline, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__INLINE, newInline, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setInline(InlineType newInline) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__INLINE, newInline);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MacroArgumentType getMacroArgument() {
		return (MacroArgumentType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MACRO_ARGUMENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMacroArgument(MacroArgumentType newMacroArgument, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MACRO_ARGUMENT, newMacroArgument, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMacroArgument(MacroArgumentType newMacroArgument) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MACRO_ARGUMENT, newMacroArgument);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapArrayMemberType getMapArrayMember() {
		return (MapArrayMemberType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_ARRAY_MEMBER, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetMapArrayMember(MapArrayMemberType newMapArrayMember, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_ARRAY_MEMBER, newMapArrayMember, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setMapArrayMember(MapArrayMemberType newMapArrayMember) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_ARRAY_MEMBER, newMapArrayMember);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapEnumType getMapEnum() {
		return (MapEnumType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_ENUM, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetMapEnum(MapEnumType newMapEnum, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_ENUM, newMapEnum, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setMapEnum(MapEnumType newMapEnum) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_ENUM, newMapEnum);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapEnumElementType getMapEnumElement() {
		return (MapEnumElementType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_ENUM_ELEMENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetMapEnumElement(MapEnumElementType newMapEnumElement, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_ENUM_ELEMENT, newMapEnumElement, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setMapEnumElement(MapEnumElementType newMapEnumElement) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_ENUM_ELEMENT, newMapEnumElement);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public TwoWayMappingType getTwoWayMapping() {
		return (TwoWayMappingType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__TWO_WAY_MAPPING, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetTwoWayMapping(TwoWayMappingType newTwoWayMapping, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__TWO_WAY_MAPPING, newTwoWayMapping, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MapArrayTypeType getMapArrayType() {
		return (MapArrayTypeType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_ARRAY_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMapArrayType(MapArrayTypeType newMapArrayType, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_ARRAY_TYPE, newMapArrayType, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMapArrayType(MapArrayTypeType newMapArrayType) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_ARRAY_TYPE, newMapArrayType);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapBitmaskElementType getMapBitmaskElement() {
		return (MapBitmaskElementType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_BITMASK_ELEMENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetMapBitmaskElement(MapBitmaskElementType newMapBitmaskElement, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_BITMASK_ELEMENT, newMapBitmaskElement, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setMapBitmaskElement(MapBitmaskElementType newMapBitmaskElement) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_BITMASK_ELEMENT, newMapBitmaskElement);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapBitmaskMemberType getMapBitmaskMember() {
		return (MapBitmaskMemberType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_BITMASK_MEMBER, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetMapBitmaskMember(MapBitmaskMemberType newMapBitmaskMember, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_BITMASK_MEMBER, newMapBitmaskMember, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setMapBitmaskMember(MapBitmaskMemberType newMapBitmaskMember) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_BITMASK_MEMBER, newMapBitmaskMember);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MapBitmaskTypeType getMapBitmaskType() {
		return (MapBitmaskTypeType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_BITMASK_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMapBitmaskType(MapBitmaskTypeType newMapBitmaskType, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_BITMASK_TYPE, newMapBitmaskType, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMapBitmaskType(MapBitmaskTypeType newMapBitmaskType) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_BITMASK_TYPE, newMapBitmaskType);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapBitmaskValueType getMapBitmaskValue() {
		return (MapBitmaskValueType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_BITMASK_VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetMapBitmaskValue(MapBitmaskValueType newMapBitmaskValue, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_BITMASK_VALUE, newMapBitmaskValue, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setMapBitmaskValue(MapBitmaskValueType newMapBitmaskValue) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_BITMASK_VALUE, newMapBitmaskValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MapElementFromTypeType getMapElementFromType() {
		return (MapElementFromTypeType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_ELEMENT_FROM_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMapElementFromType(MapElementFromTypeType newMapElementFromType, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_ELEMENT_FROM_TYPE, newMapElementFromType, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMapElementFromType(MapElementFromTypeType newMapElementFromType) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_ELEMENT_FROM_TYPE, newMapElementFromType);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapEnumMemberType getMapEnumMember() {
		return (MapEnumMemberType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_ENUM_MEMBER, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetMapEnumMember(MapEnumMemberType newMapEnumMember, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_ENUM_MEMBER, newMapEnumMember, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setMapEnumMember(MapEnumMemberType newMapEnumMember) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_ENUM_MEMBER, newMapEnumMember);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MapEnumTypeType getMapEnumType() {
		return (MapEnumTypeType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_ENUM_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMapEnumType(MapEnumTypeType newMapEnumType, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_ENUM_TYPE, newMapEnumType, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMapEnumType(MapEnumTypeType newMapEnumType) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_ENUM_TYPE, newMapEnumType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MapFixedElementType getMapFixedElement() {
		return (MapFixedElementType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_FIXED_ELEMENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMapFixedElement(MapFixedElementType newMapFixedElement, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_FIXED_ELEMENT, newMapFixedElement, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMapFixedElement(MapFixedElementType newMapFixedElement) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_FIXED_ELEMENT, newMapFixedElement);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapFixedMemberType getMapFixedMember() {
		return (MapFixedMemberType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_FIXED_MEMBER, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetMapFixedMember(MapFixedMemberType newMapFixedMember, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_FIXED_MEMBER, newMapFixedMember, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setMapFixedMember(MapFixedMemberType newMapFixedMember) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_FIXED_MEMBER, newMapFixedMember);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MapFixedTypeType getMapFixedType() {
		return (MapFixedTypeType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_FIXED_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMapFixedType(MapFixedTypeType newMapFixedType, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_FIXED_TYPE, newMapFixedType, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMapFixedType(MapFixedTypeType newMapFixedType) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_FIXED_TYPE, newMapFixedType);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapIdentifierElementType getMapIdentifierElement() {
		return (MapIdentifierElementType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_IDENTIFIER_ELEMENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetMapIdentifierElement(MapIdentifierElementType newMapIdentifierElement, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_IDENTIFIER_ELEMENT, newMapIdentifierElement, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setMapIdentifierElement(MapIdentifierElementType newMapIdentifierElement) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_IDENTIFIER_ELEMENT, newMapIdentifierElement);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapIdentifierMemberType getMapIdentifierMember() {
		return (MapIdentifierMemberType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_IDENTIFIER_MEMBER, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetMapIdentifierMember(MapIdentifierMemberType newMapIdentifierMember, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_IDENTIFIER_MEMBER, newMapIdentifierMember, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setMapIdentifierMember(MapIdentifierMemberType newMapIdentifierMember) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_IDENTIFIER_MEMBER, newMapIdentifierMember);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MapIdentifierTypeType getMapIdentifierType() {
		return (MapIdentifierTypeType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_IDENTIFIER_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMapIdentifierType(MapIdentifierTypeType newMapIdentifierType, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_IDENTIFIER_TYPE, newMapIdentifierType, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMapIdentifierType(MapIdentifierTypeType newMapIdentifierType) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_IDENTIFIER_TYPE, newMapIdentifierType);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapInstanceElementType getMapInstanceElement() {
		return (MapInstanceElementType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_INSTANCE_ELEMENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetMapInstanceElement(MapInstanceElementType newMapInstanceElement, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_INSTANCE_ELEMENT, newMapInstanceElement, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setMapInstanceElement(MapInstanceElementType newMapInstanceElement) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_INSTANCE_ELEMENT, newMapInstanceElement);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapInstanceMemberType getMapInstanceMember() {
		return (MapInstanceMemberType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_INSTANCE_MEMBER, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetMapInstanceMember(MapInstanceMemberType newMapInstanceMember, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_INSTANCE_MEMBER, newMapInstanceMember, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setMapInstanceMember(MapInstanceMemberType newMapInstanceMember) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_INSTANCE_MEMBER, newMapInstanceMember);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MapInstanceTypeType getMapInstanceType() {
		return (MapInstanceTypeType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_INSTANCE_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMapInstanceType(MapInstanceTypeType newMapInstanceType, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_INSTANCE_TYPE, newMapInstanceType, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMapInstanceType(MapInstanceTypeType newMapInstanceType) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_INSTANCE_TYPE, newMapInstanceType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MapIntoPropertyType getMapIntoProperty() {
		return (MapIntoPropertyType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_INTO_PROPERTY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMapIntoProperty(MapIntoPropertyType newMapIntoProperty, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_INTO_PROPERTY, newMapIntoProperty, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMapIntoProperty(MapIntoPropertyType newMapIntoProperty) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_INTO_PROPERTY, newMapIntoProperty);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MapMemberFromTypeType getMapMemberFromType() {
		return (MapMemberFromTypeType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_MEMBER_FROM_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMapMemberFromType(MapMemberFromTypeType newMapMemberFromType, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_MEMBER_FROM_TYPE, newMapMemberFromType, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMapMemberFromType(MapMemberFromTypeType newMapMemberFromType) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_MEMBER_FROM_TYPE, newMapMemberFromType);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapReferenceElementType getMapReferenceElement() {
		return (MapReferenceElementType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_REFERENCE_ELEMENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetMapReferenceElement(MapReferenceElementType newMapReferenceElement, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_REFERENCE_ELEMENT, newMapReferenceElement, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setMapReferenceElement(MapReferenceElementType newMapReferenceElement) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_REFERENCE_ELEMENT, newMapReferenceElement);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapReferenceMemberType getMapReferenceMember() {
		return (MapReferenceMemberType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_REFERENCE_MEMBER, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetMapReferenceMember(MapReferenceMemberType newMapReferenceMember, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_REFERENCE_MEMBER, newMapReferenceMember, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setMapReferenceMember(MapReferenceMemberType newMapReferenceMember) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_REFERENCE_MEMBER, newMapReferenceMember);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MapReferenceTypeType getMapReferenceType() {
		return (MapReferenceTypeType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_REFERENCE_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMapReferenceType(MapReferenceTypeType newMapReferenceType, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_REFERENCE_TYPE, newMapReferenceType, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMapReferenceType(MapReferenceTypeType newMapReferenceType) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_REFERENCE_TYPE, newMapReferenceType);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapResourceType getMapResource() {
		return (MapResourceType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_RESOURCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetMapResource(MapResourceType newMapResource, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_RESOURCE, newMapResource, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setMapResource(MapResourceType newMapResource) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_RESOURCE, newMapResource);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapResourceElementType getMapResourceElement() {
		return (MapResourceElementType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_RESOURCE_ELEMENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetMapResourceElement(MapResourceElementType newMapResourceElement, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_RESOURCE_ELEMENT, newMapResourceElement, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setMapResourceElement(MapResourceElementType newMapResourceElement) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_RESOURCE_ELEMENT, newMapResourceElement);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapResourceMemberType getMapResourceMember() {
		return (MapResourceMemberType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_RESOURCE_MEMBER, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetMapResourceMember(MapResourceMemberType newMapResourceMember, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_RESOURCE_MEMBER, newMapResourceMember, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setMapResourceMember(MapResourceMemberType newMapResourceMember) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_RESOURCE_MEMBER, newMapResourceMember);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MapResourceTypeType getMapResourceType() {
		return (MapResourceTypeType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_RESOURCE_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMapResourceType(MapResourceTypeType newMapResourceType, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_RESOURCE_TYPE, newMapResourceType, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMapResourceType(MapResourceTypeType newMapResourceType) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_RESOURCE_TYPE, newMapResourceType);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapSimpleElementType getMapSimpleElement() {
		return (MapSimpleElementType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_SIMPLE_ELEMENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetMapSimpleElement(MapSimpleElementType newMapSimpleElement, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_SIMPLE_ELEMENT, newMapSimpleElement, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setMapSimpleElement(MapSimpleElementType newMapSimpleElement) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_SIMPLE_ELEMENT, newMapSimpleElement);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MapSimpleMemberType getMapSimpleMember() {
		return (MapSimpleMemberType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_SIMPLE_MEMBER, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetMapSimpleMember(MapSimpleMemberType newMapSimpleMember, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_SIMPLE_MEMBER, newMapSimpleMember, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setMapSimpleMember(MapSimpleMemberType newMapSimpleMember) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_SIMPLE_MEMBER, newMapSimpleMember);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MapSimpleTypeType getMapSimpleType() {
		return (MapSimpleTypeType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_SIMPLE_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMapSimpleType(MapSimpleTypeType newMapSimpleType, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_SIMPLE_TYPE, newMapSimpleType, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMapSimpleType(MapSimpleTypeType newMapSimpleType) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__MAP_SIMPLE_TYPE, newMapSimpleType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertiesType getProperties() {
		return (PropertiesType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__PROPERTIES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProperties(PropertiesType newProperties, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__PROPERTIES, newProperties, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProperties(PropertiesType newProperties) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__PROPERTIES, newProperties);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimplePropertyType getProperty() {
		return (SimplePropertyType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__PROPERTY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProperty(SimplePropertyType newProperty, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__PROPERTY, newProperty, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProperty(SimplePropertyType newProperty) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__PROPERTY, newProperty);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyOverridesType getPropertyOverrides() {
		return (PropertyOverridesType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__PROPERTY_OVERRIDES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPropertyOverrides(PropertyOverridesType newPropertyOverrides, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__PROPERTY_OVERRIDES, newPropertyOverrides, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropertyOverrides(PropertyOverridesType newPropertyOverrides) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__PROPERTY_OVERRIDES, newPropertyOverrides);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public ScriptType getScript() {
		return (ScriptType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__SCRIPT, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetScript(ScriptType newScript, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__SCRIPT, newScript, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setScript(ScriptType newScript) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__SCRIPT, newScript);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public SelectType getSelect() {
		return (SelectType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__SELECT, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetSelect(SelectType newSelect, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__SELECT, newSelect, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setSelect(SelectType newSelect) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__SELECT, newSelect);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public SourceGenType getSourceGen() {
		return (SourceGenType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__SOURCE_GEN, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetSourceGen(SourceGenType newSourceGen, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__SOURCE_GEN, newSourceGen, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setSourceGen(SourceGenType newSourceGen) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__SOURCE_GEN, newSourceGen);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public SourceMappingType getSourceMapping() {
		return (SourceMappingType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__SOURCE_MAPPING, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetSourceMapping(SourceMappingType newSourceMapping, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__SOURCE_MAPPING, newSourceMapping, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setSourceMapping(SourceMappingType newSourceMapping) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__SOURCE_MAPPING, newSourceMapping);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SourceTypeMappingType getSourceTypeMapping() {
		return (SourceTypeMappingType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__SOURCE_TYPE_MAPPING, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSourceTypeMapping(SourceTypeMappingType newSourceTypeMapping, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__SOURCE_TYPE_MAPPING, newSourceTypeMapping, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceTypeMapping(SourceTypeMappingType newSourceTypeMapping) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__SOURCE_TYPE_MAPPING, newSourceTypeMapping);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymbianType getSymbian() {
		return (SymbianType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__SYMBIAN, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSymbian(SymbianType newSymbian, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__SYMBIAN, newSymbian, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSymbian(SymbianType newSymbian) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__SYMBIAN, newSymbian);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public TemplateType getTemplate() {
		return (TemplateType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__TEMPLATE, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetTemplate(TemplateType newTemplate, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__TEMPLATE, newTemplate, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setTemplate(TemplateType newTemplate) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__TEMPLATE, newTemplate);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public TemplateGroupType getTemplateGroup() {
		return (TemplateGroupType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__TEMPLATE_GROUP, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetTemplateGroup(TemplateGroupType newTemplateGroup, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__TEMPLATE_GROUP, newTemplateGroup, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setTemplateGroup(TemplateGroupType newTemplateGroup) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__TEMPLATE_GROUP, newTemplateGroup);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public UseTemplateType getUseTemplate() {
		return (UseTemplateType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__USE_TEMPLATE, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetUseTemplate(UseTemplateType newUseTemplate, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__USE_TEMPLATE, newUseTemplate, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setUseTemplate(UseTemplateType newUseTemplate) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__USE_TEMPLATE, newUseTemplate);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public UseTemplateGroupType getUseTemplateGroup() {
		return (UseTemplateGroupType)getMixed().get(ComponentPackage.Literals.DOCUMENT_ROOT__USE_TEMPLATE_GROUP, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetUseTemplateGroup(UseTemplateGroupType newUseTemplateGroup, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ComponentPackage.Literals.DOCUMENT_ROOT__USE_TEMPLATE_GROUP, newUseTemplateGroup, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setUseTemplateGroup(UseTemplateGroupType newUseTemplateGroup) {
		((FeatureMap.Internal)getMixed()).set(ComponentPackage.Literals.DOCUMENT_ROOT__USE_TEMPLATE_GROUP, newUseTemplateGroup);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.DOCUMENT_ROOT__MIXED:
				return ((InternalEList)getMixed()).basicRemove(otherEnd, msgs);
			case ComponentPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return ((InternalEList)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
			case ComponentPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return ((InternalEList)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
			case ComponentPackage.DOCUMENT_ROOT__ABSTRACT_PROPERTY:
				return basicSetAbstractProperty(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__ARRAY_PROPERTY:
				return basicSetArrayProperty(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__ATTRIBUTE:
				return basicSetAttribute(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__ATTRIBUTES:
				return basicSetAttributes(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__CHOICE:
				return basicSetChoice(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__CODE:
				return basicSetCode(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__COMPONENT:
				return basicSetComponent(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__COMPONENT_DEFINITION:
				return basicSetComponentDefinition(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__COMPONENT_REFERENCE_PROPERTY:
				return basicSetComponentReferenceProperty(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__COMPOUND_PROPERTY:
				return basicSetCompoundProperty(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__COMPOUND_PROPERTY_DECLARATION:
				return basicSetCompoundPropertyDeclaration(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__DEFINE_LOCATION:
				return basicSetDefineLocation(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__DEFINE_MACRO:
				return basicSetDefineMacro(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__DESIGNER_IMAGES:
				return basicSetDesignerImages(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__DOCUMENTATION:
				return basicSetDocumentation(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__ENUM_PROPERTY:
				return basicSetEnumProperty(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__ENUM_PROPERTY_DECLARATION:
				return basicSetEnumPropertyDeclaration(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__EVENT:
				return basicSetEvent(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__EVENTS:
				return basicSetEvents(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__EXPAND_ARGUMENT:
				return basicSetExpandArgument(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__EXPAND_MACRO:
				return basicSetExpandMacro(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__EXTENSION_PROPERTIES:
				return basicSetExtensionProperties(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__IMPLEMENTATION:
				return basicSetImplementation(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__IMPLEMENTATIONS:
				return basicSetImplementations(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__IMPORT_ARGUMENTS:
				return basicSetImportArguments(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__INLINE:
				return basicSetInline(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MACRO_ARGUMENT:
				return basicSetMacroArgument(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_ARRAY_MEMBER:
				return basicSetMapArrayMember(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__TWO_WAY_MAPPING:
				return basicSetTwoWayMapping(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_ARRAY_TYPE:
				return basicSetMapArrayType(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_BITMASK_ELEMENT:
				return basicSetMapBitmaskElement(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_BITMASK_MEMBER:
				return basicSetMapBitmaskMember(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_BITMASK_TYPE:
				return basicSetMapBitmaskType(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_BITMASK_VALUE:
				return basicSetMapBitmaskValue(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_ELEMENT_FROM_TYPE:
				return basicSetMapElementFromType(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_ENUM:
				return basicSetMapEnum(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_ENUM_ELEMENT:
				return basicSetMapEnumElement(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_ENUM_MEMBER:
				return basicSetMapEnumMember(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_ENUM_TYPE:
				return basicSetMapEnumType(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_FIXED_ELEMENT:
				return basicSetMapFixedElement(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_FIXED_MEMBER:
				return basicSetMapFixedMember(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_FIXED_TYPE:
				return basicSetMapFixedType(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_IDENTIFIER_ELEMENT:
				return basicSetMapIdentifierElement(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_IDENTIFIER_MEMBER:
				return basicSetMapIdentifierMember(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_IDENTIFIER_TYPE:
				return basicSetMapIdentifierType(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_INSTANCE_ELEMENT:
				return basicSetMapInstanceElement(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_INSTANCE_MEMBER:
				return basicSetMapInstanceMember(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_INSTANCE_TYPE:
				return basicSetMapInstanceType(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_INTO_PROPERTY:
				return basicSetMapIntoProperty(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_MEMBER_FROM_TYPE:
				return basicSetMapMemberFromType(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_REFERENCE_ELEMENT:
				return basicSetMapReferenceElement(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_REFERENCE_MEMBER:
				return basicSetMapReferenceMember(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_REFERENCE_TYPE:
				return basicSetMapReferenceType(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_RESOURCE:
				return basicSetMapResource(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_RESOURCE_ELEMENT:
				return basicSetMapResourceElement(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_RESOURCE_MEMBER:
				return basicSetMapResourceMember(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_RESOURCE_TYPE:
				return basicSetMapResourceType(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_SIMPLE_ELEMENT:
				return basicSetMapSimpleElement(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_SIMPLE_MEMBER:
				return basicSetMapSimpleMember(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__MAP_SIMPLE_TYPE:
				return basicSetMapSimpleType(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__PROPERTIES:
				return basicSetProperties(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__PROPERTY:
				return basicSetProperty(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__PROPERTY_OVERRIDES:
				return basicSetPropertyOverrides(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__SCRIPT:
				return basicSetScript(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__SELECT:
				return basicSetSelect(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__SOURCE_GEN:
				return basicSetSourceGen(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__SOURCE_MAPPING:
				return basicSetSourceMapping(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__SOURCE_TYPE_MAPPING:
				return basicSetSourceTypeMapping(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__SYMBIAN:
				return basicSetSymbian(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__TEMPLATE:
				return basicSetTemplate(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__TEMPLATE_GROUP:
				return basicSetTemplateGroup(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__USE_TEMPLATE:
				return basicSetUseTemplate(null, msgs);
			case ComponentPackage.DOCUMENT_ROOT__USE_TEMPLATE_GROUP:
				return basicSetUseTemplateGroup(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ComponentPackage.DOCUMENT_ROOT__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case ComponentPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				if (coreType) return getXMLNSPrefixMap();
				else return getXMLNSPrefixMap().map();
			case ComponentPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				if (coreType) return getXSISchemaLocation();
				else return getXSISchemaLocation().map();
			case ComponentPackage.DOCUMENT_ROOT__ABSTRACT_PROPERTY:
				return getAbstractProperty();
			case ComponentPackage.DOCUMENT_ROOT__ARRAY_PROPERTY:
				return getArrayProperty();
			case ComponentPackage.DOCUMENT_ROOT__ATTRIBUTE:
				return getAttribute();
			case ComponentPackage.DOCUMENT_ROOT__ATTRIBUTES:
				return getAttributes();
			case ComponentPackage.DOCUMENT_ROOT__CHOICE:
				return getChoice();
			case ComponentPackage.DOCUMENT_ROOT__CODE:
				return getCode();
			case ComponentPackage.DOCUMENT_ROOT__COMPONENT:
				return getComponent();
			case ComponentPackage.DOCUMENT_ROOT__COMPONENT_DEFINITION:
				return getComponentDefinition();
			case ComponentPackage.DOCUMENT_ROOT__COMPONENT_REFERENCE_PROPERTY:
				return getComponentReferenceProperty();
			case ComponentPackage.DOCUMENT_ROOT__COMPOUND_PROPERTY:
				return getCompoundProperty();
			case ComponentPackage.DOCUMENT_ROOT__COMPOUND_PROPERTY_DECLARATION:
				return getCompoundPropertyDeclaration();
			case ComponentPackage.DOCUMENT_ROOT__DEFINE_LOCATION:
				return getDefineLocation();
			case ComponentPackage.DOCUMENT_ROOT__DEFINE_MACRO:
				return getDefineMacro();
			case ComponentPackage.DOCUMENT_ROOT__DESIGNER_IMAGES:
				return getDesignerImages();
			case ComponentPackage.DOCUMENT_ROOT__DOCUMENTATION:
				return getDocumentation();
			case ComponentPackage.DOCUMENT_ROOT__ENUM_PROPERTY:
				return getEnumProperty();
			case ComponentPackage.DOCUMENT_ROOT__ENUM_PROPERTY_DECLARATION:
				return getEnumPropertyDeclaration();
			case ComponentPackage.DOCUMENT_ROOT__EVENT:
				return getEvent();
			case ComponentPackage.DOCUMENT_ROOT__EVENTS:
				return getEvents();
			case ComponentPackage.DOCUMENT_ROOT__EXPAND_ARGUMENT:
				return getExpandArgument();
			case ComponentPackage.DOCUMENT_ROOT__EXPAND_MACRO:
				return getExpandMacro();
			case ComponentPackage.DOCUMENT_ROOT__EXTENSION_PROPERTIES:
				return getExtensionProperties();
			case ComponentPackage.DOCUMENT_ROOT__IMPLEMENTATION:
				return getImplementation();
			case ComponentPackage.DOCUMENT_ROOT__IMPLEMENTATIONS:
				return getImplementations();
			case ComponentPackage.DOCUMENT_ROOT__IMPORT_ARGUMENTS:
				return getImportArguments();
			case ComponentPackage.DOCUMENT_ROOT__INLINE:
				return getInline();
			case ComponentPackage.DOCUMENT_ROOT__MACRO_ARGUMENT:
				return getMacroArgument();
			case ComponentPackage.DOCUMENT_ROOT__MAP_ARRAY_MEMBER:
				return getMapArrayMember();
			case ComponentPackage.DOCUMENT_ROOT__TWO_WAY_MAPPING:
				return getTwoWayMapping();
			case ComponentPackage.DOCUMENT_ROOT__MAP_ARRAY_TYPE:
				return getMapArrayType();
			case ComponentPackage.DOCUMENT_ROOT__MAP_BITMASK_ELEMENT:
				return getMapBitmaskElement();
			case ComponentPackage.DOCUMENT_ROOT__MAP_BITMASK_MEMBER:
				return getMapBitmaskMember();
			case ComponentPackage.DOCUMENT_ROOT__MAP_BITMASK_TYPE:
				return getMapBitmaskType();
			case ComponentPackage.DOCUMENT_ROOT__MAP_BITMASK_VALUE:
				return getMapBitmaskValue();
			case ComponentPackage.DOCUMENT_ROOT__MAP_ELEMENT_FROM_TYPE:
				return getMapElementFromType();
			case ComponentPackage.DOCUMENT_ROOT__MAP_ENUM:
				return getMapEnum();
			case ComponentPackage.DOCUMENT_ROOT__MAP_ENUM_ELEMENT:
				return getMapEnumElement();
			case ComponentPackage.DOCUMENT_ROOT__MAP_ENUM_MEMBER:
				return getMapEnumMember();
			case ComponentPackage.DOCUMENT_ROOT__MAP_ENUM_TYPE:
				return getMapEnumType();
			case ComponentPackage.DOCUMENT_ROOT__MAP_FIXED_ELEMENT:
				return getMapFixedElement();
			case ComponentPackage.DOCUMENT_ROOT__MAP_FIXED_MEMBER:
				return getMapFixedMember();
			case ComponentPackage.DOCUMENT_ROOT__MAP_FIXED_TYPE:
				return getMapFixedType();
			case ComponentPackage.DOCUMENT_ROOT__MAP_IDENTIFIER_ELEMENT:
				return getMapIdentifierElement();
			case ComponentPackage.DOCUMENT_ROOT__MAP_IDENTIFIER_MEMBER:
				return getMapIdentifierMember();
			case ComponentPackage.DOCUMENT_ROOT__MAP_IDENTIFIER_TYPE:
				return getMapIdentifierType();
			case ComponentPackage.DOCUMENT_ROOT__MAP_INSTANCE_ELEMENT:
				return getMapInstanceElement();
			case ComponentPackage.DOCUMENT_ROOT__MAP_INSTANCE_MEMBER:
				return getMapInstanceMember();
			case ComponentPackage.DOCUMENT_ROOT__MAP_INSTANCE_TYPE:
				return getMapInstanceType();
			case ComponentPackage.DOCUMENT_ROOT__MAP_INTO_PROPERTY:
				return getMapIntoProperty();
			case ComponentPackage.DOCUMENT_ROOT__MAP_MEMBER_FROM_TYPE:
				return getMapMemberFromType();
			case ComponentPackage.DOCUMENT_ROOT__MAP_REFERENCE_ELEMENT:
				return getMapReferenceElement();
			case ComponentPackage.DOCUMENT_ROOT__MAP_REFERENCE_MEMBER:
				return getMapReferenceMember();
			case ComponentPackage.DOCUMENT_ROOT__MAP_REFERENCE_TYPE:
				return getMapReferenceType();
			case ComponentPackage.DOCUMENT_ROOT__MAP_RESOURCE:
				return getMapResource();
			case ComponentPackage.DOCUMENT_ROOT__MAP_RESOURCE_ELEMENT:
				return getMapResourceElement();
			case ComponentPackage.DOCUMENT_ROOT__MAP_RESOURCE_MEMBER:
				return getMapResourceMember();
			case ComponentPackage.DOCUMENT_ROOT__MAP_RESOURCE_TYPE:
				return getMapResourceType();
			case ComponentPackage.DOCUMENT_ROOT__MAP_SIMPLE_ELEMENT:
				return getMapSimpleElement();
			case ComponentPackage.DOCUMENT_ROOT__MAP_SIMPLE_MEMBER:
				return getMapSimpleMember();
			case ComponentPackage.DOCUMENT_ROOT__MAP_SIMPLE_TYPE:
				return getMapSimpleType();
			case ComponentPackage.DOCUMENT_ROOT__PROPERTIES:
				return getProperties();
			case ComponentPackage.DOCUMENT_ROOT__PROPERTY:
				return getProperty();
			case ComponentPackage.DOCUMENT_ROOT__PROPERTY_OVERRIDES:
				return getPropertyOverrides();
			case ComponentPackage.DOCUMENT_ROOT__SCRIPT:
				return getScript();
			case ComponentPackage.DOCUMENT_ROOT__SELECT:
				return getSelect();
			case ComponentPackage.DOCUMENT_ROOT__SOURCE_GEN:
				return getSourceGen();
			case ComponentPackage.DOCUMENT_ROOT__SOURCE_MAPPING:
				return getSourceMapping();
			case ComponentPackage.DOCUMENT_ROOT__SOURCE_TYPE_MAPPING:
				return getSourceTypeMapping();
			case ComponentPackage.DOCUMENT_ROOT__SYMBIAN:
				return getSymbian();
			case ComponentPackage.DOCUMENT_ROOT__TEMPLATE:
				return getTemplate();
			case ComponentPackage.DOCUMENT_ROOT__TEMPLATE_GROUP:
				return getTemplateGroup();
			case ComponentPackage.DOCUMENT_ROOT__USE_TEMPLATE:
				return getUseTemplate();
			case ComponentPackage.DOCUMENT_ROOT__USE_TEMPLATE_GROUP:
				return getUseTemplateGroup();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ComponentPackage.DOCUMENT_ROOT__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__ARRAY_PROPERTY:
				setArrayProperty((ArrayPropertyType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__ATTRIBUTE:
				setAttribute((AttributeType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__ATTRIBUTES:
				setAttributes((AttributesType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__CHOICE:
				setChoice((ChoiceType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__CODE:
				setCode((CodeType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__COMPONENT:
				setComponent((ComponentType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__COMPONENT_DEFINITION:
				setComponentDefinition((ComponentDefinitionType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__COMPONENT_REFERENCE_PROPERTY:
				setComponentReferenceProperty((ComponentReferencePropertyType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__COMPOUND_PROPERTY:
				setCompoundProperty((CompoundPropertyType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__COMPOUND_PROPERTY_DECLARATION:
				setCompoundPropertyDeclaration((CompoundPropertyDeclarationType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__DEFINE_LOCATION:
				setDefineLocation((DefineLocationType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__DEFINE_MACRO:
				setDefineMacro((DefineMacroType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__DESIGNER_IMAGES:
				setDesignerImages((DesignerImagesType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__DOCUMENTATION:
				setDocumentation((DocumentationType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__ENUM_PROPERTY:
				setEnumProperty((EnumPropertyType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__ENUM_PROPERTY_DECLARATION:
				setEnumPropertyDeclaration((EnumPropertyDeclarationType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__EVENT:
				setEvent((EventType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__EVENTS:
				setEvents((EventsType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__EXPAND_ARGUMENT:
				setExpandArgument((ExpandArgumentType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__EXPAND_MACRO:
				setExpandMacro((ExpandMacroType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__EXTENSION_PROPERTIES:
				setExtensionProperties((ExtensionPropertiesType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__IMPLEMENTATION:
				setImplementation((ImplementationType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__IMPLEMENTATIONS:
				setImplementations((ImplementationsType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__IMPORT_ARGUMENTS:
				setImportArguments((ImportArgumentsType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__INLINE:
				setInline((InlineType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MACRO_ARGUMENT:
				setMacroArgument((MacroArgumentType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_ARRAY_MEMBER:
				setMapArrayMember((MapArrayMemberType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_ARRAY_TYPE:
				setMapArrayType((MapArrayTypeType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_BITMASK_ELEMENT:
				setMapBitmaskElement((MapBitmaskElementType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_BITMASK_MEMBER:
				setMapBitmaskMember((MapBitmaskMemberType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_BITMASK_TYPE:
				setMapBitmaskType((MapBitmaskTypeType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_BITMASK_VALUE:
				setMapBitmaskValue((MapBitmaskValueType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_ELEMENT_FROM_TYPE:
				setMapElementFromType((MapElementFromTypeType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_ENUM:
				setMapEnum((MapEnumType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_ENUM_ELEMENT:
				setMapEnumElement((MapEnumElementType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_ENUM_MEMBER:
				setMapEnumMember((MapEnumMemberType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_ENUM_TYPE:
				setMapEnumType((MapEnumTypeType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_FIXED_ELEMENT:
				setMapFixedElement((MapFixedElementType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_FIXED_MEMBER:
				setMapFixedMember((MapFixedMemberType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_FIXED_TYPE:
				setMapFixedType((MapFixedTypeType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_IDENTIFIER_ELEMENT:
				setMapIdentifierElement((MapIdentifierElementType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_IDENTIFIER_MEMBER:
				setMapIdentifierMember((MapIdentifierMemberType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_IDENTIFIER_TYPE:
				setMapIdentifierType((MapIdentifierTypeType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_INSTANCE_ELEMENT:
				setMapInstanceElement((MapInstanceElementType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_INSTANCE_MEMBER:
				setMapInstanceMember((MapInstanceMemberType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_INSTANCE_TYPE:
				setMapInstanceType((MapInstanceTypeType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_INTO_PROPERTY:
				setMapIntoProperty((MapIntoPropertyType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_MEMBER_FROM_TYPE:
				setMapMemberFromType((MapMemberFromTypeType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_REFERENCE_ELEMENT:
				setMapReferenceElement((MapReferenceElementType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_REFERENCE_MEMBER:
				setMapReferenceMember((MapReferenceMemberType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_REFERENCE_TYPE:
				setMapReferenceType((MapReferenceTypeType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_RESOURCE:
				setMapResource((MapResourceType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_RESOURCE_ELEMENT:
				setMapResourceElement((MapResourceElementType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_RESOURCE_MEMBER:
				setMapResourceMember((MapResourceMemberType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_RESOURCE_TYPE:
				setMapResourceType((MapResourceTypeType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_SIMPLE_ELEMENT:
				setMapSimpleElement((MapSimpleElementType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_SIMPLE_MEMBER:
				setMapSimpleMember((MapSimpleMemberType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_SIMPLE_TYPE:
				setMapSimpleType((MapSimpleTypeType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__PROPERTIES:
				setProperties((PropertiesType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__PROPERTY:
				setProperty((SimplePropertyType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__PROPERTY_OVERRIDES:
				setPropertyOverrides((PropertyOverridesType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__SCRIPT:
				setScript((ScriptType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__SELECT:
				setSelect((SelectType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__SOURCE_GEN:
				setSourceGen((SourceGenType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__SOURCE_MAPPING:
				setSourceMapping((SourceMappingType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__SOURCE_TYPE_MAPPING:
				setSourceTypeMapping((SourceTypeMappingType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__SYMBIAN:
				setSymbian((SymbianType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__TEMPLATE:
				setTemplate((TemplateType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__TEMPLATE_GROUP:
				setTemplateGroup((TemplateGroupType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__USE_TEMPLATE:
				setUseTemplate((UseTemplateType)newValue);
				return;
			case ComponentPackage.DOCUMENT_ROOT__USE_TEMPLATE_GROUP:
				setUseTemplateGroup((UseTemplateGroupType)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case ComponentPackage.DOCUMENT_ROOT__MIXED:
				getMixed().clear();
				return;
			case ComponentPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				getXMLNSPrefixMap().clear();
				return;
			case ComponentPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				getXSISchemaLocation().clear();
				return;
			case ComponentPackage.DOCUMENT_ROOT__ARRAY_PROPERTY:
				setArrayProperty((ArrayPropertyType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__ATTRIBUTE:
				setAttribute((AttributeType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__ATTRIBUTES:
				setAttributes((AttributesType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__CHOICE:
				setChoice((ChoiceType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__CODE:
				setCode((CodeType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__COMPONENT:
				setComponent((ComponentType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__COMPONENT_DEFINITION:
				setComponentDefinition((ComponentDefinitionType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__COMPONENT_REFERENCE_PROPERTY:
				setComponentReferenceProperty((ComponentReferencePropertyType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__COMPOUND_PROPERTY:
				setCompoundProperty((CompoundPropertyType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__COMPOUND_PROPERTY_DECLARATION:
				setCompoundPropertyDeclaration((CompoundPropertyDeclarationType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__DEFINE_LOCATION:
				setDefineLocation((DefineLocationType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__DEFINE_MACRO:
				setDefineMacro((DefineMacroType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__DESIGNER_IMAGES:
				setDesignerImages((DesignerImagesType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__DOCUMENTATION:
				setDocumentation((DocumentationType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__ENUM_PROPERTY:
				setEnumProperty((EnumPropertyType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__ENUM_PROPERTY_DECLARATION:
				setEnumPropertyDeclaration((EnumPropertyDeclarationType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__EVENT:
				setEvent((EventType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__EVENTS:
				setEvents((EventsType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__EXPAND_ARGUMENT:
				setExpandArgument((ExpandArgumentType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__EXPAND_MACRO:
				setExpandMacro((ExpandMacroType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__EXTENSION_PROPERTIES:
				setExtensionProperties((ExtensionPropertiesType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__IMPLEMENTATION:
				setImplementation((ImplementationType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__IMPLEMENTATIONS:
				setImplementations((ImplementationsType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__IMPORT_ARGUMENTS:
				setImportArguments((ImportArgumentsType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__INLINE:
				setInline((InlineType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MACRO_ARGUMENT:
				setMacroArgument((MacroArgumentType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_ARRAY_MEMBER:
				setMapArrayMember((MapArrayMemberType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_ARRAY_TYPE:
				setMapArrayType((MapArrayTypeType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_BITMASK_ELEMENT:
				setMapBitmaskElement((MapBitmaskElementType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_BITMASK_MEMBER:
				setMapBitmaskMember((MapBitmaskMemberType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_BITMASK_TYPE:
				setMapBitmaskType((MapBitmaskTypeType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_BITMASK_VALUE:
				setMapBitmaskValue((MapBitmaskValueType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_ELEMENT_FROM_TYPE:
				setMapElementFromType((MapElementFromTypeType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_ENUM:
				setMapEnum((MapEnumType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_ENUM_ELEMENT:
				setMapEnumElement((MapEnumElementType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_ENUM_MEMBER:
				setMapEnumMember((MapEnumMemberType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_ENUM_TYPE:
				setMapEnumType((MapEnumTypeType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_FIXED_ELEMENT:
				setMapFixedElement((MapFixedElementType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_FIXED_MEMBER:
				setMapFixedMember((MapFixedMemberType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_FIXED_TYPE:
				setMapFixedType((MapFixedTypeType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_IDENTIFIER_ELEMENT:
				setMapIdentifierElement((MapIdentifierElementType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_IDENTIFIER_MEMBER:
				setMapIdentifierMember((MapIdentifierMemberType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_IDENTIFIER_TYPE:
				setMapIdentifierType((MapIdentifierTypeType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_INSTANCE_ELEMENT:
				setMapInstanceElement((MapInstanceElementType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_INSTANCE_MEMBER:
				setMapInstanceMember((MapInstanceMemberType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_INSTANCE_TYPE:
				setMapInstanceType((MapInstanceTypeType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_INTO_PROPERTY:
				setMapIntoProperty((MapIntoPropertyType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_MEMBER_FROM_TYPE:
				setMapMemberFromType((MapMemberFromTypeType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_REFERENCE_ELEMENT:
				setMapReferenceElement((MapReferenceElementType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_REFERENCE_MEMBER:
				setMapReferenceMember((MapReferenceMemberType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_REFERENCE_TYPE:
				setMapReferenceType((MapReferenceTypeType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_RESOURCE:
				setMapResource((MapResourceType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_RESOURCE_ELEMENT:
				setMapResourceElement((MapResourceElementType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_RESOURCE_MEMBER:
				setMapResourceMember((MapResourceMemberType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_RESOURCE_TYPE:
				setMapResourceType((MapResourceTypeType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_SIMPLE_ELEMENT:
				setMapSimpleElement((MapSimpleElementType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_SIMPLE_MEMBER:
				setMapSimpleMember((MapSimpleMemberType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__MAP_SIMPLE_TYPE:
				setMapSimpleType((MapSimpleTypeType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__PROPERTIES:
				setProperties((PropertiesType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__PROPERTY:
				setProperty((SimplePropertyType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__PROPERTY_OVERRIDES:
				setPropertyOverrides((PropertyOverridesType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__SCRIPT:
				setScript((ScriptType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__SELECT:
				setSelect((SelectType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__SOURCE_GEN:
				setSourceGen((SourceGenType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__SOURCE_MAPPING:
				setSourceMapping((SourceMappingType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__SOURCE_TYPE_MAPPING:
				setSourceTypeMapping((SourceTypeMappingType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__SYMBIAN:
				setSymbian((SymbianType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__TEMPLATE:
				setTemplate((TemplateType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__TEMPLATE_GROUP:
				setTemplateGroup((TemplateGroupType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__USE_TEMPLATE:
				setUseTemplate((UseTemplateType)null);
				return;
			case ComponentPackage.DOCUMENT_ROOT__USE_TEMPLATE_GROUP:
				setUseTemplateGroup((UseTemplateGroupType)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ComponentPackage.DOCUMENT_ROOT__MIXED:
				return mixed != null && !mixed.isEmpty();
			case ComponentPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
			case ComponentPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
			case ComponentPackage.DOCUMENT_ROOT__ABSTRACT_PROPERTY:
				return getAbstractProperty() != null;
			case ComponentPackage.DOCUMENT_ROOT__ARRAY_PROPERTY:
				return getArrayProperty() != null;
			case ComponentPackage.DOCUMENT_ROOT__ATTRIBUTE:
				return getAttribute() != null;
			case ComponentPackage.DOCUMENT_ROOT__ATTRIBUTES:
				return getAttributes() != null;
			case ComponentPackage.DOCUMENT_ROOT__CHOICE:
				return getChoice() != null;
			case ComponentPackage.DOCUMENT_ROOT__CODE:
				return getCode() != null;
			case ComponentPackage.DOCUMENT_ROOT__COMPONENT:
				return getComponent() != null;
			case ComponentPackage.DOCUMENT_ROOT__COMPONENT_DEFINITION:
				return getComponentDefinition() != null;
			case ComponentPackage.DOCUMENT_ROOT__COMPONENT_REFERENCE_PROPERTY:
				return getComponentReferenceProperty() != null;
			case ComponentPackage.DOCUMENT_ROOT__COMPOUND_PROPERTY:
				return getCompoundProperty() != null;
			case ComponentPackage.DOCUMENT_ROOT__COMPOUND_PROPERTY_DECLARATION:
				return getCompoundPropertyDeclaration() != null;
			case ComponentPackage.DOCUMENT_ROOT__DEFINE_LOCATION:
				return getDefineLocation() != null;
			case ComponentPackage.DOCUMENT_ROOT__DEFINE_MACRO:
				return getDefineMacro() != null;
			case ComponentPackage.DOCUMENT_ROOT__DESIGNER_IMAGES:
				return getDesignerImages() != null;
			case ComponentPackage.DOCUMENT_ROOT__DOCUMENTATION:
				return getDocumentation() != null;
			case ComponentPackage.DOCUMENT_ROOT__ENUM_PROPERTY:
				return getEnumProperty() != null;
			case ComponentPackage.DOCUMENT_ROOT__ENUM_PROPERTY_DECLARATION:
				return getEnumPropertyDeclaration() != null;
			case ComponentPackage.DOCUMENT_ROOT__EVENT:
				return getEvent() != null;
			case ComponentPackage.DOCUMENT_ROOT__EVENTS:
				return getEvents() != null;
			case ComponentPackage.DOCUMENT_ROOT__EXPAND_ARGUMENT:
				return getExpandArgument() != null;
			case ComponentPackage.DOCUMENT_ROOT__EXPAND_MACRO:
				return getExpandMacro() != null;
			case ComponentPackage.DOCUMENT_ROOT__EXTENSION_PROPERTIES:
				return getExtensionProperties() != null;
			case ComponentPackage.DOCUMENT_ROOT__IMPLEMENTATION:
				return getImplementation() != null;
			case ComponentPackage.DOCUMENT_ROOT__IMPLEMENTATIONS:
				return getImplementations() != null;
			case ComponentPackage.DOCUMENT_ROOT__IMPORT_ARGUMENTS:
				return getImportArguments() != null;
			case ComponentPackage.DOCUMENT_ROOT__INLINE:
				return getInline() != null;
			case ComponentPackage.DOCUMENT_ROOT__MACRO_ARGUMENT:
				return getMacroArgument() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_ARRAY_MEMBER:
				return getMapArrayMember() != null;
			case ComponentPackage.DOCUMENT_ROOT__TWO_WAY_MAPPING:
				return getTwoWayMapping() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_ARRAY_TYPE:
				return getMapArrayType() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_BITMASK_ELEMENT:
				return getMapBitmaskElement() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_BITMASK_MEMBER:
				return getMapBitmaskMember() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_BITMASK_TYPE:
				return getMapBitmaskType() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_BITMASK_VALUE:
				return getMapBitmaskValue() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_ELEMENT_FROM_TYPE:
				return getMapElementFromType() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_ENUM:
				return getMapEnum() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_ENUM_ELEMENT:
				return getMapEnumElement() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_ENUM_MEMBER:
				return getMapEnumMember() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_ENUM_TYPE:
				return getMapEnumType() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_FIXED_ELEMENT:
				return getMapFixedElement() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_FIXED_MEMBER:
				return getMapFixedMember() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_FIXED_TYPE:
				return getMapFixedType() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_IDENTIFIER_ELEMENT:
				return getMapIdentifierElement() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_IDENTIFIER_MEMBER:
				return getMapIdentifierMember() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_IDENTIFIER_TYPE:
				return getMapIdentifierType() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_INSTANCE_ELEMENT:
				return getMapInstanceElement() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_INSTANCE_MEMBER:
				return getMapInstanceMember() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_INSTANCE_TYPE:
				return getMapInstanceType() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_INTO_PROPERTY:
				return getMapIntoProperty() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_MEMBER_FROM_TYPE:
				return getMapMemberFromType() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_REFERENCE_ELEMENT:
				return getMapReferenceElement() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_REFERENCE_MEMBER:
				return getMapReferenceMember() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_REFERENCE_TYPE:
				return getMapReferenceType() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_RESOURCE:
				return getMapResource() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_RESOURCE_ELEMENT:
				return getMapResourceElement() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_RESOURCE_MEMBER:
				return getMapResourceMember() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_RESOURCE_TYPE:
				return getMapResourceType() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_SIMPLE_ELEMENT:
				return getMapSimpleElement() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_SIMPLE_MEMBER:
				return getMapSimpleMember() != null;
			case ComponentPackage.DOCUMENT_ROOT__MAP_SIMPLE_TYPE:
				return getMapSimpleType() != null;
			case ComponentPackage.DOCUMENT_ROOT__PROPERTIES:
				return getProperties() != null;
			case ComponentPackage.DOCUMENT_ROOT__PROPERTY:
				return getProperty() != null;
			case ComponentPackage.DOCUMENT_ROOT__PROPERTY_OVERRIDES:
				return getPropertyOverrides() != null;
			case ComponentPackage.DOCUMENT_ROOT__SCRIPT:
				return getScript() != null;
			case ComponentPackage.DOCUMENT_ROOT__SELECT:
				return getSelect() != null;
			case ComponentPackage.DOCUMENT_ROOT__SOURCE_GEN:
				return getSourceGen() != null;
			case ComponentPackage.DOCUMENT_ROOT__SOURCE_MAPPING:
				return getSourceMapping() != null;
			case ComponentPackage.DOCUMENT_ROOT__SOURCE_TYPE_MAPPING:
				return getSourceTypeMapping() != null;
			case ComponentPackage.DOCUMENT_ROOT__SYMBIAN:
				return getSymbian() != null;
			case ComponentPackage.DOCUMENT_ROOT__TEMPLATE:
				return getTemplate() != null;
			case ComponentPackage.DOCUMENT_ROOT__TEMPLATE_GROUP:
				return getTemplateGroup() != null;
			case ComponentPackage.DOCUMENT_ROOT__USE_TEMPLATE:
				return getUseTemplate() != null;
			case ComponentPackage.DOCUMENT_ROOT__USE_TEMPLATE_GROUP:
				return getUseTemplateGroup() != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (mixed: ");
		result.append(mixed);
		result.append(')');
		return result.toString();
	}

} //DocumentRootImpl
