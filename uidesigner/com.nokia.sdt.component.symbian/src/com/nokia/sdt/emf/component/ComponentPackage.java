/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * 
 * Type mappings are used to store mappings along with a type.  Added post 1.2		
 * 		
 * 
 * Member mappings are used to map a property to a member of a resource.		
 * 		
 * <!-- end-model-doc -->
 * @see com.nokia.sdt.emf.component.ComponentFactory
 * @model kind="package"
 * @generated
 */
public interface ComponentPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "component";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.nokia.com/sdt/emf/component";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "component";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ComponentPackage eINSTANCE = com.nokia.sdt.emf.component.impl.ComponentPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.AbstractPropertyTypeImpl <em>Abstract Property Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.AbstractPropertyTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getAbstractPropertyType()
	 * @generated
	 */
	int ABSTRACT_PROPERTY_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PROPERTY_TYPE__CATEGORY = 0;

	/**
	 * The feature id for the '<em><b>Description Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PROPERTY_TYPE__DESCRIPTION_KEY = 1;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PROPERTY_TYPE__DISPLAY_NAME = 2;

	/**
	 * The feature id for the '<em><b>Editor Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PROPERTY_TYPE__EDITOR_CLASS = 3;

	/**
	 * The feature id for the '<em><b>Help Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PROPERTY_TYPE__HELP_KEY = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PROPERTY_TYPE__NAME = 5;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PROPERTY_TYPE__READ_ONLY = 6;

	/**
	 * The feature id for the '<em><b>Resettable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PROPERTY_TYPE__RESETTABLE = 7;

	/**
	 * The number of structural features of the '<em>Abstract Property Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PROPERTY_TYPE_FEATURE_COUNT = 8;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.ArrayPropertyTypeImpl <em>Array Property Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.ArrayPropertyTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getArrayPropertyType()
	 * @generated
	 */
	int ARRAY_PROPERTY_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_PROPERTY_TYPE__CATEGORY = ABSTRACT_PROPERTY_TYPE__CATEGORY;

	/**
	 * The feature id for the '<em><b>Description Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_PROPERTY_TYPE__DESCRIPTION_KEY = ABSTRACT_PROPERTY_TYPE__DESCRIPTION_KEY;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_PROPERTY_TYPE__DISPLAY_NAME = ABSTRACT_PROPERTY_TYPE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Editor Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_PROPERTY_TYPE__EDITOR_CLASS = ABSTRACT_PROPERTY_TYPE__EDITOR_CLASS;

	/**
	 * The feature id for the '<em><b>Help Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_PROPERTY_TYPE__HELP_KEY = ABSTRACT_PROPERTY_TYPE__HELP_KEY;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_PROPERTY_TYPE__NAME = ABSTRACT_PROPERTY_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_PROPERTY_TYPE__READ_ONLY = ABSTRACT_PROPERTY_TYPE__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Resettable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_PROPERTY_TYPE__RESETTABLE = ABSTRACT_PROPERTY_TYPE__RESETTABLE;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_PROPERTY_TYPE__TYPE = ABSTRACT_PROPERTY_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Array Property Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_PROPERTY_TYPE_FEATURE_COUNT = ABSTRACT_PROPERTY_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.AttributesTypeImpl <em>Attributes Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.AttributesTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getAttributesType()
	 * @generated
	 */
	int ATTRIBUTES_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTES_TYPE__ATTRIBUTE = 0;

	/**
	 * The number of structural features of the '<em>Attributes Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTES_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.AttributeTypeImpl <em>Attribute Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.AttributeTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getAttributeType()
	 * @generated
	 */
	int ATTRIBUTE_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_TYPE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_TYPE__KEY = 1;

	/**
	 * The number of structural features of the '<em>Attribute Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.ChoiceTypeImpl <em>Choice Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.ChoiceTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getChoiceType()
	 * @generated
	 */
	int CHOICE_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE_TYPE__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Two Way Mapping Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE_TYPE__TWO_WAY_MAPPING_GROUP = 1;

	/**
	 * The feature id for the '<em><b>Two Way Mapping</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE_TYPE__TWO_WAY_MAPPING = 2;

	/**
	 * The feature id for the '<em><b>Map Resource</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE_TYPE__MAP_RESOURCE = 3;

	/**
	 * The feature id for the '<em><b>Select</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE_TYPE__SELECT = 4;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE_TYPE__VALUE = 5;

	/**
	 * The number of structural features of the '<em>Choice Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE_TYPE_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.CodeTypeImpl <em>Code Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.CodeTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getCodeType()
	 * @generated
	 */
	int CODE_TYPE = 5;

	/**
	 * The feature id for the '<em><b>Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODE_TYPE__CLASS = 0;

	/**
	 * The feature id for the '<em><b>Plugin</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODE_TYPE__PLUGIN = 1;

	/**
	 * The number of structural features of the '<em>Code Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODE_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.ComponentDefinitionTypeImpl <em>Definition Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.ComponentDefinitionTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getComponentDefinitionType()
	 * @generated
	 */
	int COMPONENT_DEFINITION_TYPE = 6;

	/**
	 * The feature id for the '<em><b>Compound Property Declaration</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_DEFINITION_TYPE__COMPOUND_PROPERTY_DECLARATION = 0;

	/**
	 * The feature id for the '<em><b>Enum Property Declaration</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_DEFINITION_TYPE__ENUM_PROPERTY_DECLARATION = 1;

	/**
	 * The feature id for the '<em><b>Component</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_DEFINITION_TYPE__COMPONENT = 2;

	/**
	 * The number of structural features of the '<em>Definition Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_DEFINITION_TYPE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.ComponentReferencePropertyTypeImpl <em>Reference Property Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.ComponentReferencePropertyTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getComponentReferencePropertyType()
	 * @generated
	 */
	int COMPONENT_REFERENCE_PROPERTY_TYPE = 7;

	/**
	 * The feature id for the '<em><b>Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_REFERENCE_PROPERTY_TYPE__CATEGORY = ABSTRACT_PROPERTY_TYPE__CATEGORY;

	/**
	 * The feature id for the '<em><b>Description Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_REFERENCE_PROPERTY_TYPE__DESCRIPTION_KEY = ABSTRACT_PROPERTY_TYPE__DESCRIPTION_KEY;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_REFERENCE_PROPERTY_TYPE__DISPLAY_NAME = ABSTRACT_PROPERTY_TYPE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Editor Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_REFERENCE_PROPERTY_TYPE__EDITOR_CLASS = ABSTRACT_PROPERTY_TYPE__EDITOR_CLASS;

	/**
	 * The feature id for the '<em><b>Help Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_REFERENCE_PROPERTY_TYPE__HELP_KEY = ABSTRACT_PROPERTY_TYPE__HELP_KEY;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_REFERENCE_PROPERTY_TYPE__NAME = ABSTRACT_PROPERTY_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_REFERENCE_PROPERTY_TYPE__READ_ONLY = ABSTRACT_PROPERTY_TYPE__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Resettable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_REFERENCE_PROPERTY_TYPE__RESETTABLE = ABSTRACT_PROPERTY_TYPE__RESETTABLE;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_REFERENCE_PROPERTY_TYPE__CONSTRAINT = ABSTRACT_PROPERTY_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Creation Keys</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_REFERENCE_PROPERTY_TYPE__CREATION_KEYS = ABSTRACT_PROPERTY_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Promote Reference Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_REFERENCE_PROPERTY_TYPE__PROMOTE_REFERENCE_PROPERTIES = ABSTRACT_PROPERTY_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_REFERENCE_PROPERTY_TYPE__SCOPE = ABSTRACT_PROPERTY_TYPE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Reference Property Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_REFERENCE_PROPERTY_TYPE_FEATURE_COUNT = ABSTRACT_PROPERTY_TYPE_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.ComponentTypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.ComponentTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getComponentType()
	 * @generated
	 */
	int COMPONENT_TYPE = 8;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_TYPE__DOCUMENTATION = 0;

	/**
	 * The feature id for the '<em><b>Symbian</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_TYPE__SYMBIAN = 1;

	/**
	 * The feature id for the '<em><b>Designer Images</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_TYPE__DESIGNER_IMAGES = 2;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_TYPE__ATTRIBUTES = 3;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_TYPE__PROPERTIES = 4;

	/**
	 * The feature id for the '<em><b>Extension Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_TYPE__EXTENSION_PROPERTIES = 5;

	/**
	 * The feature id for the '<em><b>Property Overrides</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_TYPE__PROPERTY_OVERRIDES = 6;

	/**
	 * The feature id for the '<em><b>Events</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_TYPE__EVENTS = 7;

	/**
	 * The feature id for the '<em><b>Source Gen</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_TYPE__SOURCE_GEN = 8;

	/**
	 * The feature id for the '<em><b>Source Mapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_TYPE__SOURCE_MAPPING = 9;

	/**
	 * The feature id for the '<em><b>Implementations</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_TYPE__IMPLEMENTATIONS = 10;

	/**
	 * The feature id for the '<em><b>Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_TYPE__ABSTRACT = 11;

	/**
	 * The feature id for the '<em><b>Base Component</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_TYPE__BASE_COMPONENT = 12;

	/**
	 * The feature id for the '<em><b>Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_TYPE__CATEGORY = 13;

	/**
	 * The feature id for the '<em><b>Friendly Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_TYPE__FRIENDLY_NAME = 14;

	/**
	 * The feature id for the '<em><b>Instance Name Root</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_TYPE__INSTANCE_NAME_ROOT = 15;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_TYPE__QUALIFIED_NAME = 16;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_TYPE__VERSION = 17;

	/**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_TYPE_FEATURE_COUNT = 18;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.CompoundPropertyDeclarationTypeImpl <em>Compound Property Declaration Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.CompoundPropertyDeclarationTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getCompoundPropertyDeclarationType()
	 * @generated
	 */
	int COMPOUND_PROPERTY_DECLARATION_TYPE = 9;

	/**
	 * The feature id for the '<em><b>Abstract Property Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_PROPERTY_DECLARATION_TYPE__ABSTRACT_PROPERTY_GROUP = 0;

	/**
	 * The feature id for the '<em><b>Abstract Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_PROPERTY_DECLARATION_TYPE__ABSTRACT_PROPERTY = 1;

	/**
	 * The feature id for the '<em><b>Source Type Mapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_PROPERTY_DECLARATION_TYPE__SOURCE_TYPE_MAPPING = 2;

	/**
	 * The feature id for the '<em><b>Converter Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_PROPERTY_DECLARATION_TYPE__CONVERTER_CLASS = 3;

	/**
	 * The feature id for the '<em><b>Editable Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_PROPERTY_DECLARATION_TYPE__EDITABLE_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Editor Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_PROPERTY_DECLARATION_TYPE__EDITOR_CLASS = 5;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_PROPERTY_DECLARATION_TYPE__QUALIFIED_NAME = 6;

	/**
	 * The number of structural features of the '<em>Compound Property Declaration Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_PROPERTY_DECLARATION_TYPE_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.CompoundPropertyTypeImpl <em>Compound Property Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.CompoundPropertyTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getCompoundPropertyType()
	 * @generated
	 */
	int COMPOUND_PROPERTY_TYPE = 10;

	/**
	 * The feature id for the '<em><b>Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_PROPERTY_TYPE__CATEGORY = ABSTRACT_PROPERTY_TYPE__CATEGORY;

	/**
	 * The feature id for the '<em><b>Description Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_PROPERTY_TYPE__DESCRIPTION_KEY = ABSTRACT_PROPERTY_TYPE__DESCRIPTION_KEY;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_PROPERTY_TYPE__DISPLAY_NAME = ABSTRACT_PROPERTY_TYPE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Editor Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_PROPERTY_TYPE__EDITOR_CLASS = ABSTRACT_PROPERTY_TYPE__EDITOR_CLASS;

	/**
	 * The feature id for the '<em><b>Help Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_PROPERTY_TYPE__HELP_KEY = ABSTRACT_PROPERTY_TYPE__HELP_KEY;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_PROPERTY_TYPE__NAME = ABSTRACT_PROPERTY_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_PROPERTY_TYPE__READ_ONLY = ABSTRACT_PROPERTY_TYPE__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Resettable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_PROPERTY_TYPE__RESETTABLE = ABSTRACT_PROPERTY_TYPE__RESETTABLE;

	/**
	 * The feature id for the '<em><b>Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_PROPERTY_TYPE__DEFAULT = ABSTRACT_PROPERTY_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_PROPERTY_TYPE__TYPE = ABSTRACT_PROPERTY_TYPE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Compound Property Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_PROPERTY_TYPE_FEATURE_COUNT = ABSTRACT_PROPERTY_TYPE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.ConditionalSourceGenImpl <em>Conditional Source Gen</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.ConditionalSourceGenImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getConditionalSourceGen()
	 * @generated
	 */
	int CONDITIONAL_SOURCE_GEN = 11;

	/**
	 * The feature id for the '<em><b>Forms</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_SOURCE_GEN__FORMS = 0;

	/**
	 * The feature id for the '<em><b>If Events</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_SOURCE_GEN__IF_EVENTS = 1;

	/**
	 * The feature id for the '<em><b>If Expr</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_SOURCE_GEN__IF_EXPR = 2;

	/**
	 * The number of structural features of the '<em>Conditional Source Gen</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_SOURCE_GEN_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.ConditionalSourceGenStringImpl <em>Conditional Source Gen String</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.ConditionalSourceGenStringImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getConditionalSourceGenString()
	 * @generated
	 */
	int CONDITIONAL_SOURCE_GEN_STRING = 12;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_SOURCE_GEN_STRING__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Forms</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_SOURCE_GEN_STRING__FORMS = 1;

	/**
	 * The feature id for the '<em><b>If Events</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_SOURCE_GEN_STRING__IF_EVENTS = 2;

	/**
	 * The feature id for the '<em><b>If Expr</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_SOURCE_GEN_STRING__IF_EXPR = 3;

	/**
	 * The number of structural features of the '<em>Conditional Source Gen String</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_SOURCE_GEN_STRING_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.DefineLocationTypeImpl <em>Define Location Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.DefineLocationTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getDefineLocationType()
	 * @generated
	 */
	int DEFINE_LOCATION_TYPE = 13;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINE_LOCATION_TYPE__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Template</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINE_LOCATION_TYPE__TEMPLATE = 1;

	/**
	 * The feature id for the '<em><b>Inline</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINE_LOCATION_TYPE__INLINE = 2;

	/**
	 * The feature id for the '<em><b>Script</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINE_LOCATION_TYPE__SCRIPT = 3;

	/**
	 * The feature id for the '<em><b>Expand Macro</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINE_LOCATION_TYPE__EXPAND_MACRO = 4;

	/**
	 * The feature id for the '<em><b>Base Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINE_LOCATION_TYPE__BASE_LOCATION = 5;

	/**
	 * The feature id for the '<em><b>Dir</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINE_LOCATION_TYPE__DIR = 6;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINE_LOCATION_TYPE__DOMAIN = 7;

	/**
	 * The feature id for the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINE_LOCATION_TYPE__FILE = 8;

	/**
	 * The feature id for the '<em><b>Filter</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINE_LOCATION_TYPE__FILTER = 9;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINE_LOCATION_TYPE__ID = 10;

	/**
	 * The feature id for the '<em><b>If Events</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINE_LOCATION_TYPE__IF_EVENTS = 11;

	/**
	 * The feature id for the '<em><b>Is Event Handler</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINE_LOCATION_TYPE__IS_EVENT_HANDLER = 12;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINE_LOCATION_TYPE__LOCATION = 13;

	/**
	 * The feature id for the '<em><b>Owned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINE_LOCATION_TYPE__OWNED = 14;

	/**
	 * The feature id for the '<em><b>Realize</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINE_LOCATION_TYPE__REALIZE = 15;

	/**
	 * The number of structural features of the '<em>Define Location Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINE_LOCATION_TYPE_FEATURE_COUNT = 16;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.DefineMacroTypeImpl <em>Define Macro Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.DefineMacroTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getDefineMacroType()
	 * @generated
	 */
	int DEFINE_MACRO_TYPE = 14;

	/**
	 * The feature id for the '<em><b>Import Arguments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINE_MACRO_TYPE__IMPORT_ARGUMENTS = 0;

	/**
	 * The feature id for the '<em><b>Macro Argument</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINE_MACRO_TYPE__MACRO_ARGUMENT = 1;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINE_MACRO_TYPE__GROUP = 2;

	/**
	 * The feature id for the '<em><b>Template</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINE_MACRO_TYPE__TEMPLATE = 3;

	/**
	 * The feature id for the '<em><b>Inline</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINE_MACRO_TYPE__INLINE = 4;

	/**
	 * The feature id for the '<em><b>Define Location</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINE_MACRO_TYPE__DEFINE_LOCATION = 5;

	/**
	 * The feature id for the '<em><b>Expand Macro</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINE_MACRO_TYPE__EXPAND_MACRO = 6;

	/**
	 * The feature id for the '<em><b>Help</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINE_MACRO_TYPE__HELP = 7;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINE_MACRO_TYPE__ID = 8;

	/**
	 * The number of structural features of the '<em>Define Macro Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINE_MACRO_TYPE_FEATURE_COUNT = 9;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.DesignerImagesTypeImpl <em>Designer Images Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.DesignerImagesTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getDesignerImagesType()
	 * @generated
	 */
	int DESIGNER_IMAGES_TYPE = 15;

	/**
	 * The feature id for the '<em><b>Large Icon File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_IMAGES_TYPE__LARGE_ICON_FILE = 0;

	/**
	 * The feature id for the '<em><b>Layout Image File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_IMAGES_TYPE__LAYOUT_IMAGE_FILE = 1;

	/**
	 * The feature id for the '<em><b>Small Icon File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_IMAGES_TYPE__SMALL_ICON_FILE = 2;

	/**
	 * The feature id for the '<em><b>Thumbnail File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_IMAGES_TYPE__THUMBNAIL_FILE = 3;

	/**
	 * The number of structural features of the '<em>Designer Images Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_IMAGES_TYPE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.DocumentationTypeImpl <em>Documentation Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.DocumentationTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getDocumentationType()
	 * @generated
	 */
	int DOCUMENTATION_TYPE = 16;

	/**
	 * The feature id for the '<em><b>Information</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENTATION_TYPE__INFORMATION = 0;

	/**
	 * The feature id for the '<em><b>Help Topic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENTATION_TYPE__HELP_TOPIC = 1;

	/**
	 * The feature id for the '<em><b>Wizard Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENTATION_TYPE__WIZARD_DESCRIPTION = 2;

	/**
	 * The number of structural features of the '<em>Documentation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENTATION_TYPE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.DocumentRootImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 17;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MIXED = 0;

	/**
	 * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

	/**
	 * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Abstract Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ABSTRACT_PROPERTY = 3;

	/**
	 * The feature id for the '<em><b>Array Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ARRAY_PROPERTY = 4;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ATTRIBUTE = 5;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ATTRIBUTES = 6;

	/**
	 * The feature id for the '<em><b>Choice</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CHOICE = 7;

	/**
	 * The feature id for the '<em><b>Code</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CODE = 8;

	/**
	 * The feature id for the '<em><b>Component</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__COMPONENT = 9;

	/**
	 * The feature id for the '<em><b>Component Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__COMPONENT_DEFINITION = 10;

	/**
	 * The feature id for the '<em><b>Component Reference Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__COMPONENT_REFERENCE_PROPERTY = 11;

	/**
	 * The feature id for the '<em><b>Compound Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__COMPOUND_PROPERTY = 12;

	/**
	 * The feature id for the '<em><b>Compound Property Declaration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__COMPOUND_PROPERTY_DECLARATION = 13;

	/**
	 * The feature id for the '<em><b>Define Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DEFINE_LOCATION = 14;

	/**
	 * The feature id for the '<em><b>Define Macro</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DEFINE_MACRO = 15;

	/**
	 * The feature id for the '<em><b>Designer Images</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DESIGNER_IMAGES = 16;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DOCUMENTATION = 17;

	/**
	 * The feature id for the '<em><b>Enum Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ENUM_PROPERTY = 18;

	/**
	 * The feature id for the '<em><b>Enum Property Declaration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ENUM_PROPERTY_DECLARATION = 19;

	/**
	 * The feature id for the '<em><b>Event</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EVENT = 20;

	/**
	 * The feature id for the '<em><b>Events</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EVENTS = 21;

	/**
	 * The feature id for the '<em><b>Expand Argument</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EXPAND_ARGUMENT = 22;

	/**
	 * The feature id for the '<em><b>Expand Macro</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EXPAND_MACRO = 23;

	/**
	 * The feature id for the '<em><b>Extension Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EXTENSION_PROPERTIES = 24;

	/**
	 * The feature id for the '<em><b>Implementation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__IMPLEMENTATION = 25;

	/**
	 * The feature id for the '<em><b>Implementations</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__IMPLEMENTATIONS = 26;

	/**
	 * The feature id for the '<em><b>Import Arguments</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__IMPORT_ARGUMENTS = 27;

	/**
	 * The feature id for the '<em><b>Inline</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__INLINE = 28;

	/**
	 * The feature id for the '<em><b>Macro Argument</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MACRO_ARGUMENT = 29;

	/**
	 * The feature id for the '<em><b>Map Array Member</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_ARRAY_MEMBER = 30;

	/**
	 * The feature id for the '<em><b>Two Way Mapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TWO_WAY_MAPPING = 31;

	/**
	 * The feature id for the '<em><b>Map Array Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_ARRAY_TYPE = 32;

	/**
	 * The feature id for the '<em><b>Map Bitmask Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_BITMASK_ELEMENT = 33;

	/**
	 * The feature id for the '<em><b>Map Bitmask Member</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_BITMASK_MEMBER = 34;

	/**
	 * The feature id for the '<em><b>Map Bitmask Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_BITMASK_TYPE = 35;

	/**
	 * The feature id for the '<em><b>Map Bitmask Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_BITMASK_VALUE = 36;

	/**
	 * The feature id for the '<em><b>Map Element From Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_ELEMENT_FROM_TYPE = 37;

	/**
	 * The feature id for the '<em><b>Map Enum</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_ENUM = 38;

	/**
	 * The feature id for the '<em><b>Map Enum Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_ENUM_ELEMENT = 39;

	/**
	 * The feature id for the '<em><b>Map Enum Member</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_ENUM_MEMBER = 40;

	/**
	 * The feature id for the '<em><b>Map Enum Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_ENUM_TYPE = 41;

	/**
	 * The feature id for the '<em><b>Map Fixed Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_FIXED_ELEMENT = 42;

	/**
	 * The feature id for the '<em><b>Map Fixed Member</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_FIXED_MEMBER = 43;

	/**
	 * The feature id for the '<em><b>Map Fixed Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_FIXED_TYPE = 44;

	/**
	 * The feature id for the '<em><b>Map Identifier Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_IDENTIFIER_ELEMENT = 45;

	/**
	 * The feature id for the '<em><b>Map Identifier Member</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_IDENTIFIER_MEMBER = 46;

	/**
	 * The feature id for the '<em><b>Map Identifier Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_IDENTIFIER_TYPE = 47;

	/**
	 * The feature id for the '<em><b>Map Instance Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_INSTANCE_ELEMENT = 48;

	/**
	 * The feature id for the '<em><b>Map Instance Member</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_INSTANCE_MEMBER = 49;

	/**
	 * The feature id for the '<em><b>Map Instance Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_INSTANCE_TYPE = 50;

	/**
	 * The feature id for the '<em><b>Map Into Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_INTO_PROPERTY = 51;

	/**
	 * The feature id for the '<em><b>Map Member From Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_MEMBER_FROM_TYPE = 52;

	/**
	 * The feature id for the '<em><b>Map Reference Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_REFERENCE_ELEMENT = 53;

	/**
	 * The feature id for the '<em><b>Map Reference Member</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_REFERENCE_MEMBER = 54;

	/**
	 * The feature id for the '<em><b>Map Reference Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_REFERENCE_TYPE = 55;

	/**
	 * The feature id for the '<em><b>Map Resource</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_RESOURCE = 56;

	/**
	 * The feature id for the '<em><b>Map Resource Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_RESOURCE_ELEMENT = 57;

	/**
	 * The feature id for the '<em><b>Map Resource Member</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_RESOURCE_MEMBER = 58;

	/**
	 * The feature id for the '<em><b>Map Resource Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_RESOURCE_TYPE = 59;

	/**
	 * The feature id for the '<em><b>Map Simple Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_SIMPLE_ELEMENT = 60;

	/**
	 * The feature id for the '<em><b>Map Simple Member</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_SIMPLE_MEMBER = 61;

	/**
	 * The feature id for the '<em><b>Map Simple Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAP_SIMPLE_TYPE = 62;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PROPERTIES = 63;

	/**
	 * The feature id for the '<em><b>Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PROPERTY = 64;

	/**
	 * The feature id for the '<em><b>Property Overrides</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PROPERTY_OVERRIDES = 65;

	/**
	 * The feature id for the '<em><b>Script</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SCRIPT = 66;

	/**
	 * The feature id for the '<em><b>Select</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SELECT = 67;

	/**
	 * The feature id for the '<em><b>Source Gen</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SOURCE_GEN = 68;

	/**
	 * The feature id for the '<em><b>Source Mapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SOURCE_MAPPING = 69;

	/**
	 * The feature id for the '<em><b>Source Type Mapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SOURCE_TYPE_MAPPING = 70;

	/**
	 * The feature id for the '<em><b>Symbian</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SYMBIAN = 71;

	/**
	 * The feature id for the '<em><b>Template</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TEMPLATE = 72;

	/**
	 * The feature id for the '<em><b>Template Group</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TEMPLATE_GROUP = 73;

	/**
	 * The feature id for the '<em><b>Use Template</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__USE_TEMPLATE = 74;

	/**
	 * The feature id for the '<em><b>Use Template Group</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__USE_TEMPLATE_GROUP = 75;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 76;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.EnumElementTypeImpl <em>Enum Element Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.EnumElementTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getEnumElementType()
	 * @generated
	 */
	int ENUM_ELEMENT_TYPE = 18;

	/**
	 * The feature id for the '<em><b>Display Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_ELEMENT_TYPE__DISPLAY_VALUE = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_ELEMENT_TYPE__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Enum Element Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_ELEMENT_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.EnumPropertyDeclarationTypeImpl <em>Enum Property Declaration Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.EnumPropertyDeclarationTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getEnumPropertyDeclarationType()
	 * @generated
	 */
	int ENUM_PROPERTY_DECLARATION_TYPE = 19;

	/**
	 * The feature id for the '<em><b>Enum Element</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_PROPERTY_DECLARATION_TYPE__ENUM_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Source Type Mapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_PROPERTY_DECLARATION_TYPE__SOURCE_TYPE_MAPPING = 1;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_PROPERTY_DECLARATION_TYPE__DEFAULT_VALUE = 2;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_PROPERTY_DECLARATION_TYPE__QUALIFIED_NAME = 3;

	/**
	 * The number of structural features of the '<em>Enum Property Declaration Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_PROPERTY_DECLARATION_TYPE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.EnumPropertyTypeImpl <em>Enum Property Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.EnumPropertyTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getEnumPropertyType()
	 * @generated
	 */
	int ENUM_PROPERTY_TYPE = 20;

	/**
	 * The feature id for the '<em><b>Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_PROPERTY_TYPE__CATEGORY = ABSTRACT_PROPERTY_TYPE__CATEGORY;

	/**
	 * The feature id for the '<em><b>Description Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_PROPERTY_TYPE__DESCRIPTION_KEY = ABSTRACT_PROPERTY_TYPE__DESCRIPTION_KEY;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_PROPERTY_TYPE__DISPLAY_NAME = ABSTRACT_PROPERTY_TYPE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Editor Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_PROPERTY_TYPE__EDITOR_CLASS = ABSTRACT_PROPERTY_TYPE__EDITOR_CLASS;

	/**
	 * The feature id for the '<em><b>Help Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_PROPERTY_TYPE__HELP_KEY = ABSTRACT_PROPERTY_TYPE__HELP_KEY;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_PROPERTY_TYPE__NAME = ABSTRACT_PROPERTY_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_PROPERTY_TYPE__READ_ONLY = ABSTRACT_PROPERTY_TYPE__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Resettable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_PROPERTY_TYPE__RESETTABLE = ABSTRACT_PROPERTY_TYPE__RESETTABLE;

	/**
	 * The feature id for the '<em><b>Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_PROPERTY_TYPE__DEFAULT = ABSTRACT_PROPERTY_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Extend With Enum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_PROPERTY_TYPE__EXTEND_WITH_ENUM = ABSTRACT_PROPERTY_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_PROPERTY_TYPE__TYPE = ABSTRACT_PROPERTY_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Enum Property Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_PROPERTY_TYPE_FEATURE_COUNT = ABSTRACT_PROPERTY_TYPE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.EventsTypeImpl <em>Events Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.EventsTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getEventsType()
	 * @generated
	 */
	int EVENTS_TYPE = 21;

	/**
	 * The feature id for the '<em><b>Event</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENTS_TYPE__EVENT = 0;

	/**
	 * The feature id for the '<em><b>Default Event Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENTS_TYPE__DEFAULT_EVENT_NAME = 1;

	/**
	 * The number of structural features of the '<em>Events Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENTS_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.EventTypeImpl <em>Event Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.EventTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getEventType()
	 * @generated
	 */
	int EVENT_TYPE = 22;

	/**
	 * The feature id for the '<em><b>Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE__CATEGORY = 0;

	/**
	 * The feature id for the '<em><b>Description Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE__DESCRIPTION_KEY = 1;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE__DISPLAY_NAME = 2;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE__GROUP = 3;

	/**
	 * The feature id for the '<em><b>Handler Name Template</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE__HANDLER_NAME_TEMPLATE = 4;

	/**
	 * The feature id for the '<em><b>Help Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE__HELP_KEY = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE__NAME = 6;

	/**
	 * The number of structural features of the '<em>Event Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.ExpandArgumentTypeImpl <em>Expand Argument Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.ExpandArgumentTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getExpandArgumentType()
	 * @generated
	 */
	int EXPAND_ARGUMENT_TYPE = 23;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPAND_ARGUMENT_TYPE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Help</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPAND_ARGUMENT_TYPE__HELP = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPAND_ARGUMENT_TYPE__NAME = 2;

	/**
	 * The number of structural features of the '<em>Expand Argument Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPAND_ARGUMENT_TYPE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.ExpandMacroTypeImpl <em>Expand Macro Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.ExpandMacroTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getExpandMacroType()
	 * @generated
	 */
	int EXPAND_MACRO_TYPE = 24;

	/**
	 * The feature id for the '<em><b>Forms</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPAND_MACRO_TYPE__FORMS = CONDITIONAL_SOURCE_GEN__FORMS;

	/**
	 * The feature id for the '<em><b>If Events</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPAND_MACRO_TYPE__IF_EVENTS = CONDITIONAL_SOURCE_GEN__IF_EVENTS;

	/**
	 * The feature id for the '<em><b>If Expr</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPAND_MACRO_TYPE__IF_EXPR = CONDITIONAL_SOURCE_GEN__IF_EXPR;

	/**
	 * The feature id for the '<em><b>Expand Argument</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPAND_MACRO_TYPE__EXPAND_ARGUMENT = CONDITIONAL_SOURCE_GEN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Dont Pass Arguments</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPAND_MACRO_TYPE__DONT_PASS_ARGUMENTS = CONDITIONAL_SOURCE_GEN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Help</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPAND_MACRO_TYPE__HELP = CONDITIONAL_SOURCE_GEN_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPAND_MACRO_TYPE__NAME = CONDITIONAL_SOURCE_GEN_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Pass Arguments</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPAND_MACRO_TYPE__PASS_ARGUMENTS = CONDITIONAL_SOURCE_GEN_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPAND_MACRO_TYPE__ANY_ATTRIBUTE = CONDITIONAL_SOURCE_GEN_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Expand Macro Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPAND_MACRO_TYPE_FEATURE_COUNT = CONDITIONAL_SOURCE_GEN_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.ExtensionPropertiesTypeImpl <em>Extension Properties Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.ExtensionPropertiesTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getExtensionPropertiesType()
	 * @generated
	 */
	int EXTENSION_PROPERTIES_TYPE = 25;

	/**
	 * The feature id for the '<em><b>Abstract Property Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_PROPERTIES_TYPE__ABSTRACT_PROPERTY_GROUP = 0;

	/**
	 * The feature id for the '<em><b>Abstract Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_PROPERTIES_TYPE__ABSTRACT_PROPERTY = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_PROPERTIES_TYPE__NAME = 2;

	/**
	 * The number of structural features of the '<em>Extension Properties Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_PROPERTIES_TYPE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.ImplementationsTypeImpl <em>Implementations Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.ImplementationsTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getImplementationsType()
	 * @generated
	 */
	int IMPLEMENTATIONS_TYPE = 26;

	/**
	 * The feature id for the '<em><b>Implementation</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPLEMENTATIONS_TYPE__IMPLEMENTATION = 0;

	/**
	 * The number of structural features of the '<em>Implementations Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPLEMENTATIONS_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.ImplementationTypeImpl <em>Implementation Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.ImplementationTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getImplementationType()
	 * @generated
	 */
	int IMPLEMENTATION_TYPE = 27;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPLEMENTATION_TYPE__INTERFACE = 0;

	/**
	 * The feature id for the '<em><b>Code</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPLEMENTATION_TYPE__CODE = 1;

	/**
	 * The feature id for the '<em><b>Script</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPLEMENTATION_TYPE__SCRIPT = 2;

	/**
	 * The number of structural features of the '<em>Implementation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPLEMENTATION_TYPE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.ImportArgumentsTypeImpl <em>Import Arguments Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.ImportArgumentsTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getImportArgumentsType()
	 * @generated
	 */
	int IMPORT_ARGUMENTS_TYPE = 28;

	/**
	 * The feature id for the '<em><b>Arguments</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_ARGUMENTS_TYPE__ARGUMENTS = 0;

	/**
	 * The feature id for the '<em><b>Except Arguments</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_ARGUMENTS_TYPE__EXCEPT_ARGUMENTS = 1;

	/**
	 * The feature id for the '<em><b>Help</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_ARGUMENTS_TYPE__HELP = 2;

	/**
	 * The feature id for the '<em><b>Macro Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_ARGUMENTS_TYPE__MACRO_NAME = 3;

	/**
	 * The number of structural features of the '<em>Import Arguments Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_ARGUMENTS_TYPE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.InlineTypeImpl <em>Inline Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.InlineTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getInlineType()
	 * @generated
	 */
	int INLINE_TYPE = 29;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_TYPE__VALUE = CONDITIONAL_SOURCE_GEN_STRING__VALUE;

	/**
	 * The feature id for the '<em><b>Forms</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_TYPE__FORMS = CONDITIONAL_SOURCE_GEN_STRING__FORMS;

	/**
	 * The feature id for the '<em><b>If Events</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_TYPE__IF_EVENTS = CONDITIONAL_SOURCE_GEN_STRING__IF_EVENTS;

	/**
	 * The feature id for the '<em><b>If Expr</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_TYPE__IF_EXPR = CONDITIONAL_SOURCE_GEN_STRING__IF_EXPR;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_TYPE__ID = CONDITIONAL_SOURCE_GEN_STRING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_TYPE__SCOPE = CONDITIONAL_SOURCE_GEN_STRING_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Inline Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_TYPE_FEATURE_COUNT = CONDITIONAL_SOURCE_GEN_STRING_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.InterfaceTypeImpl <em>Interface Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.InterfaceTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getInterfaceType()
	 * @generated
	 */
	int INTERFACE_TYPE = 30;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE_TYPE__ID = 0;

	/**
	 * The number of structural features of the '<em>Interface Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MacroArgumentTypeImpl <em>Macro Argument Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MacroArgumentTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMacroArgumentType()
	 * @generated
	 */
	int MACRO_ARGUMENT_TYPE = 31;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_ARGUMENT_TYPE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_ARGUMENT_TYPE__DEFAULT = 1;

	/**
	 * The feature id for the '<em><b>Help</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_ARGUMENT_TYPE__HELP = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_ARGUMENT_TYPE__NAME = 3;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_ARGUMENT_TYPE__OPTIONAL = 4;

	/**
	 * The number of structural features of the '<em>Macro Argument Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_ARGUMENT_TYPE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.TwoWayMappingTypeImpl <em>Two Way Mapping Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.TwoWayMappingTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getTwoWayMappingType()
	 * @generated
	 */
	int TWO_WAY_MAPPING_TYPE = 85;

	/**
	 * The number of structural features of the '<em>Two Way Mapping Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_WAY_MAPPING_TYPE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MappingArrayTypeImpl <em>Mapping Array Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MappingArrayTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMappingArrayType()
	 * @generated
	 */
	int MAPPING_ARRAY_TYPE = 54;

	/**
	 * The feature id for the '<em><b>Two Way Mapping Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_ARRAY_TYPE__TWO_WAY_MAPPING_GROUP = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Two Way Mapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_ARRAY_TYPE__TWO_WAY_MAPPING = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Select</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_ARRAY_TYPE__SELECT = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Mapping Array Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_ARRAY_TYPE_FEATURE_COUNT = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapArrayMemberTypeImpl <em>Map Array Member Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapArrayMemberTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapArrayMemberType()
	 * @generated
	 */
	int MAP_ARRAY_MEMBER_TYPE = 32;

	/**
	 * The feature id for the '<em><b>Two Way Mapping Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ARRAY_MEMBER_TYPE__TWO_WAY_MAPPING_GROUP = MAPPING_ARRAY_TYPE__TWO_WAY_MAPPING_GROUP;

	/**
	 * The feature id for the '<em><b>Two Way Mapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ARRAY_MEMBER_TYPE__TWO_WAY_MAPPING = MAPPING_ARRAY_TYPE__TWO_WAY_MAPPING;

	/**
	 * The feature id for the '<em><b>Select</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ARRAY_MEMBER_TYPE__SELECT = MAPPING_ARRAY_TYPE__SELECT;

	/**
	 * The feature id for the '<em><b>Member</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ARRAY_MEMBER_TYPE__MEMBER = MAPPING_ARRAY_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ARRAY_MEMBER_TYPE__PROPERTY = MAPPING_ARRAY_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Suppress Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ARRAY_MEMBER_TYPE__SUPPRESS_DEFAULT = MAPPING_ARRAY_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Map Array Member Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ARRAY_MEMBER_TYPE_FEATURE_COUNT = MAPPING_ARRAY_TYPE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapArrayTypeTypeImpl <em>Map Array Type Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapArrayTypeTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapArrayTypeType()
	 * @generated
	 */
	int MAP_ARRAY_TYPE_TYPE = 33;

	/**
	 * The feature id for the '<em><b>Two Way Mapping Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ARRAY_TYPE_TYPE__TWO_WAY_MAPPING_GROUP = MAPPING_ARRAY_TYPE__TWO_WAY_MAPPING_GROUP;

	/**
	 * The feature id for the '<em><b>Two Way Mapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ARRAY_TYPE_TYPE__TWO_WAY_MAPPING = MAPPING_ARRAY_TYPE__TWO_WAY_MAPPING;

	/**
	 * The feature id for the '<em><b>Select</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ARRAY_TYPE_TYPE__SELECT = MAPPING_ARRAY_TYPE__SELECT;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ARRAY_TYPE_TYPE__TYPE_ID = MAPPING_ARRAY_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Map Array Type Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ARRAY_TYPE_TYPE_FEATURE_COUNT = MAPPING_ARRAY_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MappingBitmaskTypeImpl <em>Mapping Bitmask Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MappingBitmaskTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMappingBitmaskType()
	 * @generated
	 */
	int MAPPING_BITMASK_TYPE = 55;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_BITMASK_TYPE__GROUP = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Map Bitmask Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_BITMASK_TYPE__MAP_BITMASK_VALUE = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Included Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_BITMASK_TYPE__INCLUDED_PROPERTIES = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Mapping Bitmask Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_BITMASK_TYPE_FEATURE_COUNT = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapBitmaskElementTypeImpl <em>Map Bitmask Element Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapBitmaskElementTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapBitmaskElementType()
	 * @generated
	 */
	int MAP_BITMASK_ELEMENT_TYPE = 34;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_BITMASK_ELEMENT_TYPE__GROUP = MAPPING_BITMASK_TYPE__GROUP;

	/**
	 * The feature id for the '<em><b>Map Bitmask Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_BITMASK_ELEMENT_TYPE__MAP_BITMASK_VALUE = MAPPING_BITMASK_TYPE__MAP_BITMASK_VALUE;

	/**
	 * The feature id for the '<em><b>Included Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_BITMASK_ELEMENT_TYPE__INCLUDED_PROPERTIES = MAPPING_BITMASK_TYPE__INCLUDED_PROPERTIES;

	/**
	 * The number of structural features of the '<em>Map Bitmask Element Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_BITMASK_ELEMENT_TYPE_FEATURE_COUNT = MAPPING_BITMASK_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapBitmaskMemberTypeImpl <em>Map Bitmask Member Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapBitmaskMemberTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapBitmaskMemberType()
	 * @generated
	 */
	int MAP_BITMASK_MEMBER_TYPE = 35;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_BITMASK_MEMBER_TYPE__GROUP = MAPPING_BITMASK_TYPE__GROUP;

	/**
	 * The feature id for the '<em><b>Map Bitmask Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_BITMASK_MEMBER_TYPE__MAP_BITMASK_VALUE = MAPPING_BITMASK_TYPE__MAP_BITMASK_VALUE;

	/**
	 * The feature id for the '<em><b>Included Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_BITMASK_MEMBER_TYPE__INCLUDED_PROPERTIES = MAPPING_BITMASK_TYPE__INCLUDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Member</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_BITMASK_MEMBER_TYPE__MEMBER = MAPPING_BITMASK_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_BITMASK_MEMBER_TYPE__PROPERTY = MAPPING_BITMASK_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Suppress Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_BITMASK_MEMBER_TYPE__SUPPRESS_DEFAULT = MAPPING_BITMASK_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Map Bitmask Member Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_BITMASK_MEMBER_TYPE_FEATURE_COUNT = MAPPING_BITMASK_TYPE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapBitmaskTypeTypeImpl <em>Map Bitmask Type Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapBitmaskTypeTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapBitmaskTypeType()
	 * @generated
	 */
	int MAP_BITMASK_TYPE_TYPE = 36;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_BITMASK_TYPE_TYPE__GROUP = MAPPING_BITMASK_TYPE__GROUP;

	/**
	 * The feature id for the '<em><b>Map Bitmask Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_BITMASK_TYPE_TYPE__MAP_BITMASK_VALUE = MAPPING_BITMASK_TYPE__MAP_BITMASK_VALUE;

	/**
	 * The feature id for the '<em><b>Included Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_BITMASK_TYPE_TYPE__INCLUDED_PROPERTIES = MAPPING_BITMASK_TYPE__INCLUDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_BITMASK_TYPE_TYPE__TYPE_ID = MAPPING_BITMASK_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Map Bitmask Type Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_BITMASK_TYPE_TYPE_FEATURE_COUNT = MAPPING_BITMASK_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapBitmaskValueTypeImpl <em>Map Bitmask Value Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapBitmaskValueTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapBitmaskValueType()
	 * @generated
	 */
	int MAP_BITMASK_VALUE_TYPE = 37;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_BITMASK_VALUE_TYPE__PROPERTIES = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_BITMASK_VALUE_TYPE__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Map Bitmask Value Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_BITMASK_VALUE_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapElementFromTypeTypeImpl <em>Map Element From Type Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapElementFromTypeTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapElementFromTypeType()
	 * @generated
	 */
	int MAP_ELEMENT_FROM_TYPE_TYPE = 38;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ELEMENT_FROM_TYPE_TYPE__TYPE_ID = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Map Element From Type Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ELEMENT_FROM_TYPE_TYPE_FEATURE_COUNT = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MappingEnumTypeImpl <em>Mapping Enum Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MappingEnumTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMappingEnumType()
	 * @generated
	 */
	int MAPPING_ENUM_TYPE = 56;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_ENUM_TYPE__GROUP = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Map Enum</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_ENUM_TYPE__MAP_ENUM = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Enumeration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_ENUM_TYPE__ENUMERATION = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Headers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_ENUM_TYPE__HEADERS = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Name Algorithm</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_ENUM_TYPE__NAME_ALGORITHM = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Unique Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_ENUM_TYPE__UNIQUE_VALUE = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Validate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_ENUM_TYPE__VALIDATE = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Mapping Enum Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_ENUM_TYPE_FEATURE_COUNT = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapEnumElementTypeImpl <em>Map Enum Element Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapEnumElementTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapEnumElementType()
	 * @generated
	 */
	int MAP_ENUM_ELEMENT_TYPE = 39;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_ELEMENT_TYPE__GROUP = MAPPING_ENUM_TYPE__GROUP;

	/**
	 * The feature id for the '<em><b>Map Enum</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_ELEMENT_TYPE__MAP_ENUM = MAPPING_ENUM_TYPE__MAP_ENUM;

	/**
	 * The feature id for the '<em><b>Enumeration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_ELEMENT_TYPE__ENUMERATION = MAPPING_ENUM_TYPE__ENUMERATION;

	/**
	 * The feature id for the '<em><b>Headers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_ELEMENT_TYPE__HEADERS = MAPPING_ENUM_TYPE__HEADERS;

	/**
	 * The feature id for the '<em><b>Name Algorithm</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_ELEMENT_TYPE__NAME_ALGORITHM = MAPPING_ENUM_TYPE__NAME_ALGORITHM;

	/**
	 * The feature id for the '<em><b>Unique Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_ELEMENT_TYPE__UNIQUE_VALUE = MAPPING_ENUM_TYPE__UNIQUE_VALUE;

	/**
	 * The feature id for the '<em><b>Validate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_ELEMENT_TYPE__VALIDATE = MAPPING_ENUM_TYPE__VALIDATE;

	/**
	 * The number of structural features of the '<em>Map Enum Element Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_ELEMENT_TYPE_FEATURE_COUNT = MAPPING_ENUM_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapEnumMemberTypeImpl <em>Map Enum Member Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapEnumMemberTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapEnumMemberType()
	 * @generated
	 */
	int MAP_ENUM_MEMBER_TYPE = 40;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_MEMBER_TYPE__GROUP = MAPPING_ENUM_TYPE__GROUP;

	/**
	 * The feature id for the '<em><b>Map Enum</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_MEMBER_TYPE__MAP_ENUM = MAPPING_ENUM_TYPE__MAP_ENUM;

	/**
	 * The feature id for the '<em><b>Enumeration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_MEMBER_TYPE__ENUMERATION = MAPPING_ENUM_TYPE__ENUMERATION;

	/**
	 * The feature id for the '<em><b>Headers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_MEMBER_TYPE__HEADERS = MAPPING_ENUM_TYPE__HEADERS;

	/**
	 * The feature id for the '<em><b>Name Algorithm</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_MEMBER_TYPE__NAME_ALGORITHM = MAPPING_ENUM_TYPE__NAME_ALGORITHM;

	/**
	 * The feature id for the '<em><b>Unique Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_MEMBER_TYPE__UNIQUE_VALUE = MAPPING_ENUM_TYPE__UNIQUE_VALUE;

	/**
	 * The feature id for the '<em><b>Validate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_MEMBER_TYPE__VALIDATE = MAPPING_ENUM_TYPE__VALIDATE;

	/**
	 * The feature id for the '<em><b>Member</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_MEMBER_TYPE__MEMBER = MAPPING_ENUM_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_MEMBER_TYPE__PROPERTY = MAPPING_ENUM_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Suppress Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_MEMBER_TYPE__SUPPRESS_DEFAULT = MAPPING_ENUM_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Map Enum Member Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_MEMBER_TYPE_FEATURE_COUNT = MAPPING_ENUM_TYPE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapEnumTypeImpl <em>Map Enum Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapEnumTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapEnumType()
	 * @generated
	 */
	int MAP_ENUM_TYPE = 41;

	/**
	 * The feature id for the '<em><b>Enumerator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_TYPE__ENUMERATOR = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_TYPE__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Map Enum Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapEnumTypeTypeImpl <em>Map Enum Type Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapEnumTypeTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapEnumTypeType()
	 * @generated
	 */
	int MAP_ENUM_TYPE_TYPE = 42;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_TYPE_TYPE__GROUP = MAPPING_ENUM_TYPE__GROUP;

	/**
	 * The feature id for the '<em><b>Map Enum</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_TYPE_TYPE__MAP_ENUM = MAPPING_ENUM_TYPE__MAP_ENUM;

	/**
	 * The feature id for the '<em><b>Enumeration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_TYPE_TYPE__ENUMERATION = MAPPING_ENUM_TYPE__ENUMERATION;

	/**
	 * The feature id for the '<em><b>Headers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_TYPE_TYPE__HEADERS = MAPPING_ENUM_TYPE__HEADERS;

	/**
	 * The feature id for the '<em><b>Name Algorithm</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_TYPE_TYPE__NAME_ALGORITHM = MAPPING_ENUM_TYPE__NAME_ALGORITHM;

	/**
	 * The feature id for the '<em><b>Unique Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_TYPE_TYPE__UNIQUE_VALUE = MAPPING_ENUM_TYPE__UNIQUE_VALUE;

	/**
	 * The feature id for the '<em><b>Validate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_TYPE_TYPE__VALIDATE = MAPPING_ENUM_TYPE__VALIDATE;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_TYPE_TYPE__TYPE_ID = MAPPING_ENUM_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Map Enum Type Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ENUM_TYPE_TYPE_FEATURE_COUNT = MAPPING_ENUM_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MappingFixedTypeImpl <em>Mapping Fixed Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MappingFixedTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMappingFixedType()
	 * @generated
	 */
	int MAPPING_FIXED_TYPE = 57;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_FIXED_TYPE__VALUE = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Mapping Fixed Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_FIXED_TYPE_FEATURE_COUNT = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapFixedElementTypeImpl <em>Map Fixed Element Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapFixedElementTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapFixedElementType()
	 * @generated
	 */
	int MAP_FIXED_ELEMENT_TYPE = 43;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_FIXED_ELEMENT_TYPE__VALUE = MAPPING_FIXED_TYPE__VALUE;

	/**
	 * The number of structural features of the '<em>Map Fixed Element Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_FIXED_ELEMENT_TYPE_FEATURE_COUNT = MAPPING_FIXED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapFixedMemberTypeImpl <em>Map Fixed Member Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapFixedMemberTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapFixedMemberType()
	 * @generated
	 */
	int MAP_FIXED_MEMBER_TYPE = 44;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_FIXED_MEMBER_TYPE__VALUE = MAPPING_FIXED_TYPE__VALUE;

	/**
	 * The feature id for the '<em><b>Member</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_FIXED_MEMBER_TYPE__MEMBER = MAPPING_FIXED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Suppress Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_FIXED_MEMBER_TYPE__SUPPRESS_DEFAULT = MAPPING_FIXED_TYPE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Map Fixed Member Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_FIXED_MEMBER_TYPE_FEATURE_COUNT = MAPPING_FIXED_TYPE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapFixedTypeTypeImpl <em>Map Fixed Type Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapFixedTypeTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapFixedTypeType()
	 * @generated
	 */
	int MAP_FIXED_TYPE_TYPE = 45;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_FIXED_TYPE_TYPE__VALUE = MAPPING_FIXED_TYPE__VALUE;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_FIXED_TYPE_TYPE__TYPE_ID = MAPPING_FIXED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Map Fixed Type Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_FIXED_TYPE_TYPE_FEATURE_COUNT = MAPPING_FIXED_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MappingIdentifierTypeImpl <em>Mapping Identifier Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MappingIdentifierTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMappingIdentifierType()
	 * @generated
	 */
	int MAPPING_IDENTIFIER_TYPE = 58;

	/**
	 * The number of structural features of the '<em>Mapping Identifier Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_IDENTIFIER_TYPE_FEATURE_COUNT = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapIdentifierElementTypeImpl <em>Map Identifier Element Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapIdentifierElementTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapIdentifierElementType()
	 * @generated
	 */
	int MAP_IDENTIFIER_ELEMENT_TYPE = 46;

	/**
	 * The number of structural features of the '<em>Map Identifier Element Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_IDENTIFIER_ELEMENT_TYPE_FEATURE_COUNT = MAPPING_IDENTIFIER_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapIdentifierMemberTypeImpl <em>Map Identifier Member Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapIdentifierMemberTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapIdentifierMemberType()
	 * @generated
	 */
	int MAP_IDENTIFIER_MEMBER_TYPE = 47;

	/**
	 * The feature id for the '<em><b>Member</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_IDENTIFIER_MEMBER_TYPE__MEMBER = MAPPING_IDENTIFIER_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_IDENTIFIER_MEMBER_TYPE__PROPERTY = MAPPING_IDENTIFIER_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Suppress Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_IDENTIFIER_MEMBER_TYPE__SUPPRESS_DEFAULT = MAPPING_IDENTIFIER_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Map Identifier Member Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_IDENTIFIER_MEMBER_TYPE_FEATURE_COUNT = MAPPING_IDENTIFIER_TYPE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapIdentifierTypeTypeImpl <em>Map Identifier Type Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapIdentifierTypeTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapIdentifierTypeType()
	 * @generated
	 */
	int MAP_IDENTIFIER_TYPE_TYPE = 48;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_IDENTIFIER_TYPE_TYPE__TYPE_ID = MAPPING_IDENTIFIER_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Map Identifier Type Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_IDENTIFIER_TYPE_TYPE_FEATURE_COUNT = MAPPING_IDENTIFIER_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MappingInstanceTypeImpl <em>Mapping Instance Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MappingInstanceTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMappingInstanceType()
	 * @generated
	 */
	int MAPPING_INSTANCE_TYPE = 59;

	/**
	 * The feature id for the '<em><b>Rsrc Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_INSTANCE_TYPE__RSRC_ID = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Mapping Instance Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_INSTANCE_TYPE_FEATURE_COUNT = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapInstanceElementTypeImpl <em>Map Instance Element Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapInstanceElementTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapInstanceElementType()
	 * @generated
	 */
	int MAP_INSTANCE_ELEMENT_TYPE = 49;

	/**
	 * The feature id for the '<em><b>Rsrc Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_INSTANCE_ELEMENT_TYPE__RSRC_ID = MAPPING_INSTANCE_TYPE__RSRC_ID;

	/**
	 * The number of structural features of the '<em>Map Instance Element Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_INSTANCE_ELEMENT_TYPE_FEATURE_COUNT = MAPPING_INSTANCE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapInstanceMemberTypeImpl <em>Map Instance Member Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapInstanceMemberTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapInstanceMemberType()
	 * @generated
	 */
	int MAP_INSTANCE_MEMBER_TYPE = 50;

	/**
	 * The feature id for the '<em><b>Rsrc Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_INSTANCE_MEMBER_TYPE__RSRC_ID = MAPPING_INSTANCE_TYPE__RSRC_ID;

	/**
	 * The feature id for the '<em><b>Member</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_INSTANCE_MEMBER_TYPE__MEMBER = MAPPING_INSTANCE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_INSTANCE_MEMBER_TYPE__PROPERTY = MAPPING_INSTANCE_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Suppress Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_INSTANCE_MEMBER_TYPE__SUPPRESS_DEFAULT = MAPPING_INSTANCE_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Map Instance Member Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_INSTANCE_MEMBER_TYPE_FEATURE_COUNT = MAPPING_INSTANCE_TYPE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapInstanceTypeTypeImpl <em>Map Instance Type Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapInstanceTypeTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapInstanceTypeType()
	 * @generated
	 */
	int MAP_INSTANCE_TYPE_TYPE = 51;

	/**
	 * The feature id for the '<em><b>Rsrc Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_INSTANCE_TYPE_TYPE__RSRC_ID = MAPPING_INSTANCE_TYPE__RSRC_ID;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_INSTANCE_TYPE_TYPE__TYPE_ID = MAPPING_INSTANCE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Map Instance Type Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_INSTANCE_TYPE_TYPE_FEATURE_COUNT = MAPPING_INSTANCE_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapIntoPropertyTypeImpl <em>Map Into Property Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapIntoPropertyTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapIntoPropertyType()
	 * @generated
	 */
	int MAP_INTO_PROPERTY_TYPE = 52;

	/**
	 * The feature id for the '<em><b>Two Way Mapping Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_INTO_PROPERTY_TYPE__TWO_WAY_MAPPING_GROUP = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Two Way Mapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_INTO_PROPERTY_TYPE__TWO_WAY_MAPPING = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_INTO_PROPERTY_TYPE__PROPERTY = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Map Into Property Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_INTO_PROPERTY_TYPE_FEATURE_COUNT = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapMemberFromTypeTypeImpl <em>Map Member From Type Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapMemberFromTypeTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapMemberFromTypeType()
	 * @generated
	 */
	int MAP_MEMBER_FROM_TYPE_TYPE = 53;

	/**
	 * The feature id for the '<em><b>Member</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_MEMBER_FROM_TYPE_TYPE__MEMBER = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_MEMBER_FROM_TYPE_TYPE__PROPERTY = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Suppress Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_MEMBER_FROM_TYPE_TYPE__SUPPRESS_DEFAULT = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_MEMBER_FROM_TYPE_TYPE__TYPE_ID = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Map Member From Type Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_MEMBER_FROM_TYPE_TYPE_FEATURE_COUNT = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MappingReferenceTypeImpl <em>Mapping Reference Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MappingReferenceTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMappingReferenceType()
	 * @generated
	 */
	int MAPPING_REFERENCE_TYPE = 60;

	/**
	 * The feature id for the '<em><b>Rsrc Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_REFERENCE_TYPE__RSRC_ID = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Mapping Reference Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_REFERENCE_TYPE_FEATURE_COUNT = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MappingResourceTypeImpl <em>Mapping Resource Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MappingResourceTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMappingResourceType()
	 * @generated
	 */
	int MAPPING_RESOURCE_TYPE = 61;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_RESOURCE_TYPE__GROUP = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Map Simple Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_RESOURCE_TYPE__MAP_SIMPLE_MEMBER = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Map Instance Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_RESOURCE_TYPE__MAP_INSTANCE_MEMBER = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Map Reference Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_RESOURCE_TYPE__MAP_REFERENCE_MEMBER = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Map Fixed Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_RESOURCE_TYPE__MAP_FIXED_MEMBER = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Map Enum Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_RESOURCE_TYPE__MAP_ENUM_MEMBER = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Map Identifier Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_RESOURCE_TYPE__MAP_IDENTIFIER_MEMBER = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Map Array Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_RESOURCE_TYPE__MAP_ARRAY_MEMBER = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Map Resource Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_RESOURCE_TYPE__MAP_RESOURCE_MEMBER = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Map Bitmask Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_RESOURCE_TYPE__MAP_BITMASK_MEMBER = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Map Member From Type</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_RESOURCE_TYPE__MAP_MEMBER_FROM_TYPE = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Map Into Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_RESOURCE_TYPE__MAP_INTO_PROPERTY = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Select</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_RESOURCE_TYPE__SELECT = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Headers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_RESOURCE_TYPE__HEADERS = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_RESOURCE_TYPE__ID = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Struct</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_RESOURCE_TYPE__STRUCT = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 15;

	/**
	 * The number of structural features of the '<em>Mapping Resource Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_RESOURCE_TYPE_FEATURE_COUNT = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 16;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MappingSimpleTypeImpl <em>Mapping Simple Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MappingSimpleTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMappingSimpleType()
	 * @generated
	 */
	int MAPPING_SIMPLE_TYPE = 62;

	/**
	 * The number of structural features of the '<em>Mapping Simple Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_SIMPLE_TYPE_FEATURE_COUNT = TWO_WAY_MAPPING_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapReferenceElementTypeImpl <em>Map Reference Element Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapReferenceElementTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapReferenceElementType()
	 * @generated
	 */
	int MAP_REFERENCE_ELEMENT_TYPE = 63;

	/**
	 * The feature id for the '<em><b>Rsrc Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_REFERENCE_ELEMENT_TYPE__RSRC_ID = MAPPING_REFERENCE_TYPE__RSRC_ID;

	/**
	 * The number of structural features of the '<em>Map Reference Element Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_REFERENCE_ELEMENT_TYPE_FEATURE_COUNT = MAPPING_REFERENCE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapReferenceMemberTypeImpl <em>Map Reference Member Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapReferenceMemberTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapReferenceMemberType()
	 * @generated
	 */
	int MAP_REFERENCE_MEMBER_TYPE = 64;

	/**
	 * The feature id for the '<em><b>Rsrc Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_REFERENCE_MEMBER_TYPE__RSRC_ID = MAPPING_REFERENCE_TYPE__RSRC_ID;

	/**
	 * The feature id for the '<em><b>Member</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_REFERENCE_MEMBER_TYPE__MEMBER = MAPPING_REFERENCE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_REFERENCE_MEMBER_TYPE__PROPERTY = MAPPING_REFERENCE_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Suppress Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_REFERENCE_MEMBER_TYPE__SUPPRESS_DEFAULT = MAPPING_REFERENCE_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Map Reference Member Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_REFERENCE_MEMBER_TYPE_FEATURE_COUNT = MAPPING_REFERENCE_TYPE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapReferenceTypeTypeImpl <em>Map Reference Type Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapReferenceTypeTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapReferenceTypeType()
	 * @generated
	 */
	int MAP_REFERENCE_TYPE_TYPE = 65;

	/**
	 * The feature id for the '<em><b>Rsrc Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_REFERENCE_TYPE_TYPE__RSRC_ID = MAPPING_REFERENCE_TYPE__RSRC_ID;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_REFERENCE_TYPE_TYPE__TYPE_ID = MAPPING_REFERENCE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Map Reference Type Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_REFERENCE_TYPE_TYPE_FEATURE_COUNT = MAPPING_REFERENCE_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapResourceElementTypeImpl <em>Map Resource Element Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapResourceElementTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapResourceElementType()
	 * @generated
	 */
	int MAP_RESOURCE_ELEMENT_TYPE = 66;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_ELEMENT_TYPE__GROUP = MAPPING_RESOURCE_TYPE__GROUP;

	/**
	 * The feature id for the '<em><b>Map Simple Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_ELEMENT_TYPE__MAP_SIMPLE_MEMBER = MAPPING_RESOURCE_TYPE__MAP_SIMPLE_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Instance Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_ELEMENT_TYPE__MAP_INSTANCE_MEMBER = MAPPING_RESOURCE_TYPE__MAP_INSTANCE_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Reference Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_ELEMENT_TYPE__MAP_REFERENCE_MEMBER = MAPPING_RESOURCE_TYPE__MAP_REFERENCE_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Fixed Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_ELEMENT_TYPE__MAP_FIXED_MEMBER = MAPPING_RESOURCE_TYPE__MAP_FIXED_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Enum Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_ELEMENT_TYPE__MAP_ENUM_MEMBER = MAPPING_RESOURCE_TYPE__MAP_ENUM_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Identifier Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_ELEMENT_TYPE__MAP_IDENTIFIER_MEMBER = MAPPING_RESOURCE_TYPE__MAP_IDENTIFIER_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Array Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_ELEMENT_TYPE__MAP_ARRAY_MEMBER = MAPPING_RESOURCE_TYPE__MAP_ARRAY_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Resource Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_ELEMENT_TYPE__MAP_RESOURCE_MEMBER = MAPPING_RESOURCE_TYPE__MAP_RESOURCE_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Bitmask Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_ELEMENT_TYPE__MAP_BITMASK_MEMBER = MAPPING_RESOURCE_TYPE__MAP_BITMASK_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Member From Type</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_ELEMENT_TYPE__MAP_MEMBER_FROM_TYPE = MAPPING_RESOURCE_TYPE__MAP_MEMBER_FROM_TYPE;

	/**
	 * The feature id for the '<em><b>Map Into Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_ELEMENT_TYPE__MAP_INTO_PROPERTY = MAPPING_RESOURCE_TYPE__MAP_INTO_PROPERTY;

	/**
	 * The feature id for the '<em><b>Select</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_ELEMENT_TYPE__SELECT = MAPPING_RESOURCE_TYPE__SELECT;

	/**
	 * The feature id for the '<em><b>Headers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_ELEMENT_TYPE__HEADERS = MAPPING_RESOURCE_TYPE__HEADERS;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_ELEMENT_TYPE__ID = MAPPING_RESOURCE_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Struct</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_ELEMENT_TYPE__STRUCT = MAPPING_RESOURCE_TYPE__STRUCT;

	/**
	 * The feature id for the '<em><b>Instance Identifying Member</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_ELEMENT_TYPE__INSTANCE_IDENTIFYING_MEMBER = MAPPING_RESOURCE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Map Resource Element Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_ELEMENT_TYPE_FEATURE_COUNT = MAPPING_RESOURCE_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapResourceMemberTypeImpl <em>Map Resource Member Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapResourceMemberTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapResourceMemberType()
	 * @generated
	 */
	int MAP_RESOURCE_MEMBER_TYPE = 67;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_MEMBER_TYPE__GROUP = MAPPING_RESOURCE_TYPE__GROUP;

	/**
	 * The feature id for the '<em><b>Map Simple Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_MEMBER_TYPE__MAP_SIMPLE_MEMBER = MAPPING_RESOURCE_TYPE__MAP_SIMPLE_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Instance Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_MEMBER_TYPE__MAP_INSTANCE_MEMBER = MAPPING_RESOURCE_TYPE__MAP_INSTANCE_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Reference Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_MEMBER_TYPE__MAP_REFERENCE_MEMBER = MAPPING_RESOURCE_TYPE__MAP_REFERENCE_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Fixed Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_MEMBER_TYPE__MAP_FIXED_MEMBER = MAPPING_RESOURCE_TYPE__MAP_FIXED_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Enum Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_MEMBER_TYPE__MAP_ENUM_MEMBER = MAPPING_RESOURCE_TYPE__MAP_ENUM_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Identifier Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_MEMBER_TYPE__MAP_IDENTIFIER_MEMBER = MAPPING_RESOURCE_TYPE__MAP_IDENTIFIER_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Array Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_MEMBER_TYPE__MAP_ARRAY_MEMBER = MAPPING_RESOURCE_TYPE__MAP_ARRAY_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Resource Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_MEMBER_TYPE__MAP_RESOURCE_MEMBER = MAPPING_RESOURCE_TYPE__MAP_RESOURCE_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Bitmask Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_MEMBER_TYPE__MAP_BITMASK_MEMBER = MAPPING_RESOURCE_TYPE__MAP_BITMASK_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Member From Type</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_MEMBER_TYPE__MAP_MEMBER_FROM_TYPE = MAPPING_RESOURCE_TYPE__MAP_MEMBER_FROM_TYPE;

	/**
	 * The feature id for the '<em><b>Map Into Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_MEMBER_TYPE__MAP_INTO_PROPERTY = MAPPING_RESOURCE_TYPE__MAP_INTO_PROPERTY;

	/**
	 * The feature id for the '<em><b>Select</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_MEMBER_TYPE__SELECT = MAPPING_RESOURCE_TYPE__SELECT;

	/**
	 * The feature id for the '<em><b>Headers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_MEMBER_TYPE__HEADERS = MAPPING_RESOURCE_TYPE__HEADERS;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_MEMBER_TYPE__ID = MAPPING_RESOURCE_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Struct</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_MEMBER_TYPE__STRUCT = MAPPING_RESOURCE_TYPE__STRUCT;

	/**
	 * The feature id for the '<em><b>Member</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_MEMBER_TYPE__MEMBER = MAPPING_RESOURCE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_MEMBER_TYPE__PROPERTY = MAPPING_RESOURCE_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Suppress Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_MEMBER_TYPE__SUPPRESS_DEFAULT = MAPPING_RESOURCE_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Map Resource Member Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_MEMBER_TYPE_FEATURE_COUNT = MAPPING_RESOURCE_TYPE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapResourceTypeImpl <em>Map Resource Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapResourceTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapResourceType()
	 * @generated
	 */
	int MAP_RESOURCE_TYPE = 68;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE__GROUP = MAPPING_RESOURCE_TYPE__GROUP;

	/**
	 * The feature id for the '<em><b>Map Simple Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE__MAP_SIMPLE_MEMBER = MAPPING_RESOURCE_TYPE__MAP_SIMPLE_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Instance Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE__MAP_INSTANCE_MEMBER = MAPPING_RESOURCE_TYPE__MAP_INSTANCE_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Reference Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE__MAP_REFERENCE_MEMBER = MAPPING_RESOURCE_TYPE__MAP_REFERENCE_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Fixed Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE__MAP_FIXED_MEMBER = MAPPING_RESOURCE_TYPE__MAP_FIXED_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Enum Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE__MAP_ENUM_MEMBER = MAPPING_RESOURCE_TYPE__MAP_ENUM_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Identifier Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE__MAP_IDENTIFIER_MEMBER = MAPPING_RESOURCE_TYPE__MAP_IDENTIFIER_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Array Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE__MAP_ARRAY_MEMBER = MAPPING_RESOURCE_TYPE__MAP_ARRAY_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Resource Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE__MAP_RESOURCE_MEMBER = MAPPING_RESOURCE_TYPE__MAP_RESOURCE_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Bitmask Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE__MAP_BITMASK_MEMBER = MAPPING_RESOURCE_TYPE__MAP_BITMASK_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Member From Type</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE__MAP_MEMBER_FROM_TYPE = MAPPING_RESOURCE_TYPE__MAP_MEMBER_FROM_TYPE;

	/**
	 * The feature id for the '<em><b>Map Into Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE__MAP_INTO_PROPERTY = MAPPING_RESOURCE_TYPE__MAP_INTO_PROPERTY;

	/**
	 * The feature id for the '<em><b>Select</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE__SELECT = MAPPING_RESOURCE_TYPE__SELECT;

	/**
	 * The feature id for the '<em><b>Headers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE__HEADERS = MAPPING_RESOURCE_TYPE__HEADERS;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE__ID = MAPPING_RESOURCE_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Struct</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE__STRUCT = MAPPING_RESOURCE_TYPE__STRUCT;

	/**
	 * The feature id for the '<em><b>Base Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE__BASE_NAME = MAPPING_RESOURCE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Rss File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE__RSS_FILE = MAPPING_RESOURCE_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Standalone</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE__STANDALONE = MAPPING_RESOURCE_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Unnamed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE__UNNAMED = MAPPING_RESOURCE_TYPE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Map Resource Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE_FEATURE_COUNT = MAPPING_RESOURCE_TYPE_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapResourceTypeTypeImpl <em>Map Resource Type Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapResourceTypeTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapResourceTypeType()
	 * @generated
	 */
	int MAP_RESOURCE_TYPE_TYPE = 69;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE_TYPE__GROUP = MAPPING_RESOURCE_TYPE__GROUP;

	/**
	 * The feature id for the '<em><b>Map Simple Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE_TYPE__MAP_SIMPLE_MEMBER = MAPPING_RESOURCE_TYPE__MAP_SIMPLE_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Instance Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE_TYPE__MAP_INSTANCE_MEMBER = MAPPING_RESOURCE_TYPE__MAP_INSTANCE_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Reference Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE_TYPE__MAP_REFERENCE_MEMBER = MAPPING_RESOURCE_TYPE__MAP_REFERENCE_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Fixed Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE_TYPE__MAP_FIXED_MEMBER = MAPPING_RESOURCE_TYPE__MAP_FIXED_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Enum Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE_TYPE__MAP_ENUM_MEMBER = MAPPING_RESOURCE_TYPE__MAP_ENUM_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Identifier Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE_TYPE__MAP_IDENTIFIER_MEMBER = MAPPING_RESOURCE_TYPE__MAP_IDENTIFIER_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Array Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE_TYPE__MAP_ARRAY_MEMBER = MAPPING_RESOURCE_TYPE__MAP_ARRAY_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Resource Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE_TYPE__MAP_RESOURCE_MEMBER = MAPPING_RESOURCE_TYPE__MAP_RESOURCE_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Bitmask Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE_TYPE__MAP_BITMASK_MEMBER = MAPPING_RESOURCE_TYPE__MAP_BITMASK_MEMBER;

	/**
	 * The feature id for the '<em><b>Map Member From Type</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE_TYPE__MAP_MEMBER_FROM_TYPE = MAPPING_RESOURCE_TYPE__MAP_MEMBER_FROM_TYPE;

	/**
	 * The feature id for the '<em><b>Map Into Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE_TYPE__MAP_INTO_PROPERTY = MAPPING_RESOURCE_TYPE__MAP_INTO_PROPERTY;

	/**
	 * The feature id for the '<em><b>Select</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE_TYPE__SELECT = MAPPING_RESOURCE_TYPE__SELECT;

	/**
	 * The feature id for the '<em><b>Headers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE_TYPE__HEADERS = MAPPING_RESOURCE_TYPE__HEADERS;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE_TYPE__ID = MAPPING_RESOURCE_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Struct</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE_TYPE__STRUCT = MAPPING_RESOURCE_TYPE__STRUCT;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE_TYPE__TYPE_ID = MAPPING_RESOURCE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Map Resource Type Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_RESOURCE_TYPE_TYPE_FEATURE_COUNT = MAPPING_RESOURCE_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapSimpleElementTypeImpl <em>Map Simple Element Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapSimpleElementTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapSimpleElementType()
	 * @generated
	 */
	int MAP_SIMPLE_ELEMENT_TYPE = 70;

	/**
	 * The number of structural features of the '<em>Map Simple Element Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_SIMPLE_ELEMENT_TYPE_FEATURE_COUNT = MAPPING_SIMPLE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapSimpleMemberTypeImpl <em>Map Simple Member Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapSimpleMemberTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapSimpleMemberType()
	 * @generated
	 */
	int MAP_SIMPLE_MEMBER_TYPE = 71;

	/**
	 * The feature id for the '<em><b>Member</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_SIMPLE_MEMBER_TYPE__MEMBER = MAPPING_SIMPLE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_SIMPLE_MEMBER_TYPE__PROPERTY = MAPPING_SIMPLE_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Suppress Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_SIMPLE_MEMBER_TYPE__SUPPRESS_DEFAULT = MAPPING_SIMPLE_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Map Simple Member Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_SIMPLE_MEMBER_TYPE_FEATURE_COUNT = MAPPING_SIMPLE_TYPE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.MapSimpleTypeTypeImpl <em>Map Simple Type Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.MapSimpleTypeTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapSimpleTypeType()
	 * @generated
	 */
	int MAP_SIMPLE_TYPE_TYPE = 72;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_SIMPLE_TYPE_TYPE__TYPE_ID = MAPPING_SIMPLE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Map Simple Type Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_SIMPLE_TYPE_TYPE_FEATURE_COUNT = MAPPING_SIMPLE_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.PropertiesTypeImpl <em>Properties Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.PropertiesTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getPropertiesType()
	 * @generated
	 */
	int PROPERTIES_TYPE = 73;

	/**
	 * The feature id for the '<em><b>Abstract Property Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTIES_TYPE__ABSTRACT_PROPERTY_GROUP = 0;

	/**
	 * The feature id for the '<em><b>Abstract Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTIES_TYPE__ABSTRACT_PROPERTY = 1;

	/**
	 * The number of structural features of the '<em>Properties Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTIES_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.PropertyOverridesTypeImpl <em>Property Overrides Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.PropertyOverridesTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getPropertyOverridesType()
	 * @generated
	 */
	int PROPERTY_OVERRIDES_TYPE = 74;

	/**
	 * The feature id for the '<em><b>Property Override</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_OVERRIDES_TYPE__PROPERTY_OVERRIDE = 0;

	/**
	 * The number of structural features of the '<em>Property Overrides Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_OVERRIDES_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.PropertyOverrideTypeImpl <em>Property Override Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.PropertyOverrideTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getPropertyOverrideType()
	 * @generated
	 */
	int PROPERTY_OVERRIDE_TYPE = 75;

	/**
	 * The feature id for the '<em><b>Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_OVERRIDE_TYPE__CATEGORY = 0;

	/**
	 * The feature id for the '<em><b>Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_OVERRIDE_TYPE__DEFAULT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_OVERRIDE_TYPE__NAME = 2;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_OVERRIDE_TYPE__READ_ONLY = 3;

	/**
	 * The number of structural features of the '<em>Property Override Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_OVERRIDE_TYPE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.ScriptTypeImpl <em>Script Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.ScriptTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getScriptType()
	 * @generated
	 */
	int SCRIPT_TYPE = 76;

	/**
	 * The feature id for the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPT_TYPE__FILE = 0;

	/**
	 * The feature id for the '<em><b>Prototype</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPT_TYPE__PROTOTYPE = 1;

	/**
	 * The number of structural features of the '<em>Script Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPT_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.SelectTypeImpl <em>Select Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.SelectTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getSelectType()
	 * @generated
	 */
	int SELECT_TYPE = 77;

	/**
	 * The feature id for the '<em><b>Choice</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECT_TYPE__CHOICE = 0;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECT_TYPE__ATTRIBUTE = 1;

	/**
	 * The feature id for the '<em><b>Is Component Instance Of</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECT_TYPE__IS_COMPONENT_INSTANCE_OF = 2;

	/**
	 * The feature id for the '<em><b>Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECT_TYPE__PROPERTY = 3;

	/**
	 * The feature id for the '<em><b>Property Exists</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECT_TYPE__PROPERTY_EXISTS = 4;

	/**
	 * The number of structural features of the '<em>Select Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECT_TYPE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.SimplePropertyTypeImpl <em>Simple Property Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.SimplePropertyTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getSimplePropertyType()
	 * @generated
	 */
	int SIMPLE_PROPERTY_TYPE = 78;

	/**
	 * The feature id for the '<em><b>Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY_TYPE__CATEGORY = ABSTRACT_PROPERTY_TYPE__CATEGORY;

	/**
	 * The feature id for the '<em><b>Description Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY_TYPE__DESCRIPTION_KEY = ABSTRACT_PROPERTY_TYPE__DESCRIPTION_KEY;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY_TYPE__DISPLAY_NAME = ABSTRACT_PROPERTY_TYPE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Editor Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY_TYPE__EDITOR_CLASS = ABSTRACT_PROPERTY_TYPE__EDITOR_CLASS;

	/**
	 * The feature id for the '<em><b>Help Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY_TYPE__HELP_KEY = ABSTRACT_PROPERTY_TYPE__HELP_KEY;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY_TYPE__NAME = ABSTRACT_PROPERTY_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY_TYPE__READ_ONLY = ABSTRACT_PROPERTY_TYPE__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Resettable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY_TYPE__RESETTABLE = ABSTRACT_PROPERTY_TYPE__RESETTABLE;

	/**
	 * The feature id for the '<em><b>Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY_TYPE__DEFAULT = ABSTRACT_PROPERTY_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Extend With Enum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY_TYPE__EXTEND_WITH_ENUM = ABSTRACT_PROPERTY_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Max Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY_TYPE__MAX_VALUE = ABSTRACT_PROPERTY_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Min Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY_TYPE__MIN_VALUE = ABSTRACT_PROPERTY_TYPE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY_TYPE__TYPE = ABSTRACT_PROPERTY_TYPE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Simple Property Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY_TYPE_FEATURE_COUNT = ABSTRACT_PROPERTY_TYPE_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.SourceGenTypeImpl <em>Source Gen Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.SourceGenTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getSourceGenType()
	 * @generated
	 */
	int SOURCE_GEN_TYPE = 79;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_GEN_TYPE__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Define Location</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_GEN_TYPE__DEFINE_LOCATION = 1;

	/**
	 * The feature id for the '<em><b>Template</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_GEN_TYPE__TEMPLATE = 2;

	/**
	 * The feature id for the '<em><b>Template Group</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_GEN_TYPE__TEMPLATE_GROUP = 3;

	/**
	 * The feature id for the '<em><b>Use Template</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_GEN_TYPE__USE_TEMPLATE = 4;

	/**
	 * The feature id for the '<em><b>Use Template Group</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_GEN_TYPE__USE_TEMPLATE_GROUP = 5;

	/**
	 * The feature id for the '<em><b>Inline</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_GEN_TYPE__INLINE = 6;

	/**
	 * The feature id for the '<em><b>Script</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_GEN_TYPE__SCRIPT = 7;

	/**
	 * The feature id for the '<em><b>Define Macro</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_GEN_TYPE__DEFINE_MACRO = 8;

	/**
	 * The feature id for the '<em><b>Expand Macro</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_GEN_TYPE__EXPAND_MACRO = 9;

	/**
	 * The feature id for the '<em><b>Debug</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_GEN_TYPE__DEBUG = 10;

	/**
	 * The feature id for the '<em><b>Forms</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_GEN_TYPE__FORMS = 11;

	/**
	 * The number of structural features of the '<em>Source Gen Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_GEN_TYPE_FEATURE_COUNT = 12;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.SourceMappingTypeImpl <em>Source Mapping Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.SourceMappingTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getSourceMappingType()
	 * @generated
	 */
	int SOURCE_MAPPING_TYPE = 80;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_MAPPING_TYPE__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Map Resource</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_MAPPING_TYPE__MAP_RESOURCE = 1;

	/**
	 * The feature id for the '<em><b>Select</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_MAPPING_TYPE__SELECT = 2;

	/**
	 * The number of structural features of the '<em>Source Mapping Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_MAPPING_TYPE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.SourceTypeMappingTypeImpl <em>Source Type Mapping Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.SourceTypeMappingTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getSourceTypeMappingType()
	 * @generated
	 */
	int SOURCE_TYPE_MAPPING_TYPE = 81;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_TYPE_MAPPING_TYPE__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Map Resource Type</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_TYPE_MAPPING_TYPE__MAP_RESOURCE_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Map Enum Type</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_TYPE_MAPPING_TYPE__MAP_ENUM_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Map Simple Type</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_TYPE_MAPPING_TYPE__MAP_SIMPLE_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Map Fixed Type</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_TYPE_MAPPING_TYPE__MAP_FIXED_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Map Bitmask Type</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_TYPE_MAPPING_TYPE__MAP_BITMASK_TYPE = 5;

	/**
	 * The feature id for the '<em><b>Map Identifier Type</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_TYPE_MAPPING_TYPE__MAP_IDENTIFIER_TYPE = 6;

	/**
	 * The feature id for the '<em><b>Map Reference Type</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_TYPE_MAPPING_TYPE__MAP_REFERENCE_TYPE = 7;

	/**
	 * The feature id for the '<em><b>Map Array Type</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_TYPE_MAPPING_TYPE__MAP_ARRAY_TYPE = 8;

	/**
	 * The feature id for the '<em><b>Select</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_TYPE_MAPPING_TYPE__SELECT = 9;

	/**
	 * The number of structural features of the '<em>Source Type Mapping Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_TYPE_MAPPING_TYPE_FEATURE_COUNT = 10;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.SymbianTypeImpl <em>Symbian Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.SymbianTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getSymbianType()
	 * @generated
	 */
	int SYMBIAN_TYPE = 82;

	/**
	 * The feature id for the '<em><b>Class Help Topic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBIAN_TYPE__CLASS_HELP_TOPIC = 0;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBIAN_TYPE__CLASS_NAME = 1;

	/**
	 * The feature id for the '<em><b>Max SDK Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBIAN_TYPE__MAX_SDK_VERSION = 2;

	/**
	 * The feature id for the '<em><b>Min SDK Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBIAN_TYPE__MIN_SDK_VERSION = 3;

	/**
	 * The feature id for the '<em><b>Resource Help Topic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBIAN_TYPE__RESOURCE_HELP_TOPIC = 4;

	/**
	 * The feature id for the '<em><b>Resource Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBIAN_TYPE__RESOURCE_TYPE = 5;

	/**
	 * The feature id for the '<em><b>Sdk Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBIAN_TYPE__SDK_NAME = 6;

	/**
	 * The number of structural features of the '<em>Symbian Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBIAN_TYPE_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.TemplateGroupTypeImpl <em>Template Group Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.TemplateGroupTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getTemplateGroupType()
	 * @generated
	 */
	int TEMPLATE_GROUP_TYPE = 83;

	/**
	 * The feature id for the '<em><b>Forms</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_GROUP_TYPE__FORMS = CONDITIONAL_SOURCE_GEN__FORMS;

	/**
	 * The feature id for the '<em><b>If Events</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_GROUP_TYPE__IF_EVENTS = CONDITIONAL_SOURCE_GEN__IF_EVENTS;

	/**
	 * The feature id for the '<em><b>If Expr</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_GROUP_TYPE__IF_EXPR = CONDITIONAL_SOURCE_GEN__IF_EXPR;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_GROUP_TYPE__GROUP = CONDITIONAL_SOURCE_GEN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Define Location</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_GROUP_TYPE__DEFINE_LOCATION = CONDITIONAL_SOURCE_GEN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Template</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_GROUP_TYPE__TEMPLATE = CONDITIONAL_SOURCE_GEN_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Inline</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_GROUP_TYPE__INLINE = CONDITIONAL_SOURCE_GEN_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Use Template</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_GROUP_TYPE__USE_TEMPLATE = CONDITIONAL_SOURCE_GEN_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Use Template Group</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_GROUP_TYPE__USE_TEMPLATE_GROUP = CONDITIONAL_SOURCE_GEN_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Expand Macro</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_GROUP_TYPE__EXPAND_MACRO = CONDITIONAL_SOURCE_GEN_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Form</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_GROUP_TYPE__FORM = CONDITIONAL_SOURCE_GEN_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_GROUP_TYPE__ID = CONDITIONAL_SOURCE_GEN_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_GROUP_TYPE__LOCATION = CONDITIONAL_SOURCE_GEN_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_GROUP_TYPE__MODE = CONDITIONAL_SOURCE_GEN_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Phase</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_GROUP_TYPE__PHASE = CONDITIONAL_SOURCE_GEN_FEATURE_COUNT + 11;

	/**
	 * The number of structural features of the '<em>Template Group Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_GROUP_TYPE_FEATURE_COUNT = CONDITIONAL_SOURCE_GEN_FEATURE_COUNT + 12;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.TemplateTypeImpl <em>Template Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.TemplateTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getTemplateType()
	 * @generated
	 */
	int TEMPLATE_TYPE = 84;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_TYPE__VALUE = CONDITIONAL_SOURCE_GEN_STRING__VALUE;

	/**
	 * The feature id for the '<em><b>Forms</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_TYPE__FORMS = CONDITIONAL_SOURCE_GEN_STRING__FORMS;

	/**
	 * The feature id for the '<em><b>If Events</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_TYPE__IF_EVENTS = CONDITIONAL_SOURCE_GEN_STRING__IF_EVENTS;

	/**
	 * The feature id for the '<em><b>If Expr</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_TYPE__IF_EXPR = CONDITIONAL_SOURCE_GEN_STRING__IF_EXPR;

	/**
	 * The feature id for the '<em><b>Form</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_TYPE__FORM = CONDITIONAL_SOURCE_GEN_STRING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_TYPE__ID = CONDITIONAL_SOURCE_GEN_STRING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_TYPE__LOCATION = CONDITIONAL_SOURCE_GEN_STRING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_TYPE__MODE = CONDITIONAL_SOURCE_GEN_STRING_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Phase</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_TYPE__PHASE = CONDITIONAL_SOURCE_GEN_STRING_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Template Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_TYPE_FEATURE_COUNT = CONDITIONAL_SOURCE_GEN_STRING_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.UseTemplateGroupTypeImpl <em>Use Template Group Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.UseTemplateGroupTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getUseTemplateGroupType()
	 * @generated
	 */
	int USE_TEMPLATE_GROUP_TYPE = 86;

	/**
	 * The feature id for the '<em><b>Use Template</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USE_TEMPLATE_GROUP_TYPE__USE_TEMPLATE = 0;

	/**
	 * The feature id for the '<em><b>Ids</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USE_TEMPLATE_GROUP_TYPE__IDS = 1;

	/**
	 * The number of structural features of the '<em>Use Template Group Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USE_TEMPLATE_GROUP_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.impl.UseTemplateTypeImpl <em>Use Template Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.impl.UseTemplateTypeImpl
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getUseTemplateType()
	 * @generated
	 */
	int USE_TEMPLATE_TYPE = 87;

	/**
	 * The feature id for the '<em><b>Ids</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USE_TEMPLATE_TYPE__IDS = 0;

	/**
	 * The number of structural features of the '<em>Use Template Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USE_TEMPLATE_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.PropertyDataType <em>Property Data Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.PropertyDataType
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getPropertyDataType()
	 * @generated
	 */
	int PROPERTY_DATA_TYPE = 88;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.ReferenceScopeType <em>Reference Scope Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.ReferenceScopeType
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getReferenceScopeType()
	 * @generated
	 */
	int REFERENCE_SCOPE_TYPE = 89;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.component.StandaloneType <em>Standalone Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.StandaloneType
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getStandaloneType()
	 * @generated
	 */
	int STANDALONE_TYPE = 90;

	/**
	 * The meta object id for the '<em>List Of Strings</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.util.List
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getListOfStrings()
	 * @generated
	 */
	int LIST_OF_STRINGS = 91;

	/**
	 * The meta object id for the '<em>Property Data Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.PropertyDataType
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getPropertyDataTypeObject()
	 * @generated
	 */
	int PROPERTY_DATA_TYPE_OBJECT = 92;

	/**
	 * The meta object id for the '<em>Reference Scope Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.ReferenceScopeType
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getReferenceScopeTypeObject()
	 * @generated
	 */
	int REFERENCE_SCOPE_TYPE_OBJECT = 93;


	/**
	 * The meta object id for the '<em>Standalone Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.component.StandaloneType
	 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getStandaloneTypeObject()
	 * @generated
	 */
	int STANDALONE_TYPE_OBJECT = 94;


	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.AbstractPropertyType <em>Abstract Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Property Type</em>'.
	 * @see com.nokia.sdt.emf.component.AbstractPropertyType
	 * @generated
	 */
	EClass getAbstractPropertyType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.AbstractPropertyType#getCategory <em>Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Category</em>'.
	 * @see com.nokia.sdt.emf.component.AbstractPropertyType#getCategory()
	 * @see #getAbstractPropertyType()
	 * @generated
	 */
	EAttribute getAbstractPropertyType_Category();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.AbstractPropertyType#getDescriptionKey <em>Description Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description Key</em>'.
	 * @see com.nokia.sdt.emf.component.AbstractPropertyType#getDescriptionKey()
	 * @see #getAbstractPropertyType()
	 * @generated
	 */
	EAttribute getAbstractPropertyType_DescriptionKey();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.AbstractPropertyType#getDisplayName <em>Display Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Name</em>'.
	 * @see com.nokia.sdt.emf.component.AbstractPropertyType#getDisplayName()
	 * @see #getAbstractPropertyType()
	 * @generated
	 */
	EAttribute getAbstractPropertyType_DisplayName();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.AbstractPropertyType#getEditorClass <em>Editor Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Editor Class</em>'.
	 * @see com.nokia.sdt.emf.component.AbstractPropertyType#getEditorClass()
	 * @see #getAbstractPropertyType()
	 * @generated
	 */
	EAttribute getAbstractPropertyType_EditorClass();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.AbstractPropertyType#getHelpKey <em>Help Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Help Key</em>'.
	 * @see com.nokia.sdt.emf.component.AbstractPropertyType#getHelpKey()
	 * @see #getAbstractPropertyType()
	 * @generated
	 */
	EAttribute getAbstractPropertyType_HelpKey();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.AbstractPropertyType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.nokia.sdt.emf.component.AbstractPropertyType#getName()
	 * @see #getAbstractPropertyType()
	 * @generated
	 */
	EAttribute getAbstractPropertyType_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.AbstractPropertyType#isReadOnly <em>Read Only</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Read Only</em>'.
	 * @see com.nokia.sdt.emf.component.AbstractPropertyType#isReadOnly()
	 * @see #getAbstractPropertyType()
	 * @generated
	 */
	EAttribute getAbstractPropertyType_ReadOnly();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.AbstractPropertyType#isResettable <em>Resettable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Resettable</em>'.
	 * @see com.nokia.sdt.emf.component.AbstractPropertyType#isResettable()
	 * @see #getAbstractPropertyType()
	 * @generated
	 */
	EAttribute getAbstractPropertyType_Resettable();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.ArrayPropertyType <em>Array Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Array Property Type</em>'.
	 * @see com.nokia.sdt.emf.component.ArrayPropertyType
	 * @generated
	 */
	EClass getArrayPropertyType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ArrayPropertyType#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.nokia.sdt.emf.component.ArrayPropertyType#getType()
	 * @see #getArrayPropertyType()
	 * @generated
	 */
	EAttribute getArrayPropertyType_Type();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.AttributesType <em>Attributes Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attributes Type</em>'.
	 * @see com.nokia.sdt.emf.component.AttributesType
	 * @generated
	 */
	EClass getAttributesType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.AttributesType#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute</em>'.
	 * @see com.nokia.sdt.emf.component.AttributesType#getAttribute()
	 * @see #getAttributesType()
	 * @generated
	 */
	EReference getAttributesType_Attribute();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.AttributeType <em>Attribute Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Type</em>'.
	 * @see com.nokia.sdt.emf.component.AttributeType
	 * @generated
	 */
	EClass getAttributeType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.AttributeType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.nokia.sdt.emf.component.AttributeType#getValue()
	 * @see #getAttributeType()
	 * @generated
	 */
	EAttribute getAttributeType_Value();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.AttributeType#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see com.nokia.sdt.emf.component.AttributeType#getKey()
	 * @see #getAttributeType()
	 * @generated
	 */
	EAttribute getAttributeType_Key();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.ChoiceType <em>Choice Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Choice Type</em>'.
	 * @see com.nokia.sdt.emf.component.ChoiceType
	 * @generated
	 */
	EClass getChoiceType();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.sdt.emf.component.ChoiceType#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.nokia.sdt.emf.component.ChoiceType#getGroup()
	 * @see #getChoiceType()
	 * @generated
	 */
	EAttribute getChoiceType_Group();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.sdt.emf.component.ChoiceType#getTwoWayMappingGroup <em>Two Way Mapping Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Two Way Mapping Group</em>'.
	 * @see com.nokia.sdt.emf.component.ChoiceType#getTwoWayMappingGroup()
	 * @see #getChoiceType()
	 * @generated
	 */
	EAttribute getChoiceType_TwoWayMappingGroup();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.ChoiceType#getTwoWayMapping <em>Two Way Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Two Way Mapping</em>'.
	 * @see com.nokia.sdt.emf.component.ChoiceType#getTwoWayMapping()
	 * @see #getChoiceType()
	 * @generated
	 */
	EReference getChoiceType_TwoWayMapping();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.ChoiceType#getMapResource <em>Map Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Resource</em>'.
	 * @see com.nokia.sdt.emf.component.ChoiceType#getMapResource()
	 * @see #getChoiceType()
	 * @generated
	 */
	EReference getChoiceType_MapResource();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.ChoiceType#getSelect <em>Select</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Select</em>'.
	 * @see com.nokia.sdt.emf.component.ChoiceType#getSelect()
	 * @see #getChoiceType()
	 * @generated
	 */
	EReference getChoiceType_Select();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ChoiceType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.nokia.sdt.emf.component.ChoiceType#getValue()
	 * @see #getChoiceType()
	 * @generated
	 */
	EAttribute getChoiceType_Value();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.CodeType <em>Code Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Code Type</em>'.
	 * @see com.nokia.sdt.emf.component.CodeType
	 * @generated
	 */
	EClass getCodeType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.CodeType#getClass_ <em>Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class</em>'.
	 * @see com.nokia.sdt.emf.component.CodeType#getClass_()
	 * @see #getCodeType()
	 * @generated
	 */
	EAttribute getCodeType_Class();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.CodeType#getPlugin <em>Plugin</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Plugin</em>'.
	 * @see com.nokia.sdt.emf.component.CodeType#getPlugin()
	 * @see #getCodeType()
	 * @generated
	 */
	EAttribute getCodeType_Plugin();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.ComponentDefinitionType <em>Definition Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Definition Type</em>'.
	 * @see com.nokia.sdt.emf.component.ComponentDefinitionType
	 * @generated
	 */
	EClass getComponentDefinitionType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.ComponentDefinitionType#getCompoundPropertyDeclaration <em>Compound Property Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Compound Property Declaration</em>'.
	 * @see com.nokia.sdt.emf.component.ComponentDefinitionType#getCompoundPropertyDeclaration()
	 * @see #getComponentDefinitionType()
	 * @generated
	 */
	EReference getComponentDefinitionType_CompoundPropertyDeclaration();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.ComponentDefinitionType#getEnumPropertyDeclaration <em>Enum Property Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Enum Property Declaration</em>'.
	 * @see com.nokia.sdt.emf.component.ComponentDefinitionType#getEnumPropertyDeclaration()
	 * @see #getComponentDefinitionType()
	 * @generated
	 */
	EReference getComponentDefinitionType_EnumPropertyDeclaration();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.ComponentDefinitionType#getComponent <em>Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Component</em>'.
	 * @see com.nokia.sdt.emf.component.ComponentDefinitionType#getComponent()
	 * @see #getComponentDefinitionType()
	 * @generated
	 */
	EReference getComponentDefinitionType_Component();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.ComponentReferencePropertyType <em>Reference Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Reference Property Type</em>'.
	 * @see com.nokia.sdt.emf.component.ComponentReferencePropertyType
	 * @generated
	 */
	EClass getComponentReferencePropertyType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ComponentReferencePropertyType#getConstraint <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Constraint</em>'.
	 * @see com.nokia.sdt.emf.component.ComponentReferencePropertyType#getConstraint()
	 * @see #getComponentReferencePropertyType()
	 * @generated
	 */
	EAttribute getComponentReferencePropertyType_Constraint();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ComponentReferencePropertyType#getCreationKeys <em>Creation Keys</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Creation Keys</em>'.
	 * @see com.nokia.sdt.emf.component.ComponentReferencePropertyType#getCreationKeys()
	 * @see #getComponentReferencePropertyType()
	 * @generated
	 */
	EAttribute getComponentReferencePropertyType_CreationKeys();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ComponentReferencePropertyType#isPromoteReferenceProperties <em>Promote Reference Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Promote Reference Properties</em>'.
	 * @see com.nokia.sdt.emf.component.ComponentReferencePropertyType#isPromoteReferenceProperties()
	 * @see #getComponentReferencePropertyType()
	 * @generated
	 */
	EAttribute getComponentReferencePropertyType_PromoteReferenceProperties();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ComponentReferencePropertyType#getScope <em>Scope</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Scope</em>'.
	 * @see com.nokia.sdt.emf.component.ComponentReferencePropertyType#getScope()
	 * @see #getComponentReferencePropertyType()
	 * @generated
	 */
	EAttribute getComponentReferencePropertyType_Scope();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.ComponentType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type</em>'.
	 * @see com.nokia.sdt.emf.component.ComponentType
	 * @generated
	 */
	EClass getComponentType();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.ComponentType#getDocumentation <em>Documentation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Documentation</em>'.
	 * @see com.nokia.sdt.emf.component.ComponentType#getDocumentation()
	 * @see #getComponentType()
	 * @generated
	 */
	EReference getComponentType_Documentation();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.ComponentType#getSymbian <em>Symbian</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Symbian</em>'.
	 * @see com.nokia.sdt.emf.component.ComponentType#getSymbian()
	 * @see #getComponentType()
	 * @generated
	 */
	EReference getComponentType_Symbian();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.ComponentType#getDesignerImages <em>Designer Images</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Designer Images</em>'.
	 * @see com.nokia.sdt.emf.component.ComponentType#getDesignerImages()
	 * @see #getComponentType()
	 * @generated
	 */
	EReference getComponentType_DesignerImages();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.ComponentType#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Attributes</em>'.
	 * @see com.nokia.sdt.emf.component.ComponentType#getAttributes()
	 * @see #getComponentType()
	 * @generated
	 */
	EReference getComponentType_Attributes();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.ComponentType#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Properties</em>'.
	 * @see com.nokia.sdt.emf.component.ComponentType#getProperties()
	 * @see #getComponentType()
	 * @generated
	 */
	EReference getComponentType_Properties();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.ComponentType#getExtensionProperties <em>Extension Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Extension Properties</em>'.
	 * @see com.nokia.sdt.emf.component.ComponentType#getExtensionProperties()
	 * @see #getComponentType()
	 * @generated
	 */
	EReference getComponentType_ExtensionProperties();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.ComponentType#getPropertyOverrides <em>Property Overrides</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Property Overrides</em>'.
	 * @see com.nokia.sdt.emf.component.ComponentType#getPropertyOverrides()
	 * @see #getComponentType()
	 * @generated
	 */
	EReference getComponentType_PropertyOverrides();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.ComponentType#getEvents <em>Events</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Events</em>'.
	 * @see com.nokia.sdt.emf.component.ComponentType#getEvents()
	 * @see #getComponentType()
	 * @generated
	 */
	EReference getComponentType_Events();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.ComponentType#getSourceGen <em>Source Gen</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Source Gen</em>'.
	 * @see com.nokia.sdt.emf.component.ComponentType#getSourceGen()
	 * @see #getComponentType()
	 * @generated
	 */
	EReference getComponentType_SourceGen();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.ComponentType#getSourceMapping <em>Source Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Source Mapping</em>'.
	 * @see com.nokia.sdt.emf.component.ComponentType#getSourceMapping()
	 * @see #getComponentType()
	 * @generated
	 */
	EReference getComponentType_SourceMapping();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.ComponentType#getImplementations <em>Implementations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Implementations</em>'.
	 * @see com.nokia.sdt.emf.component.ComponentType#getImplementations()
	 * @see #getComponentType()
	 * @generated
	 */
	EReference getComponentType_Implementations();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ComponentType#isAbstract <em>Abstract</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Abstract</em>'.
	 * @see com.nokia.sdt.emf.component.ComponentType#isAbstract()
	 * @see #getComponentType()
	 * @generated
	 */
	EAttribute getComponentType_Abstract();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ComponentType#getBaseComponent <em>Base Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Base Component</em>'.
	 * @see com.nokia.sdt.emf.component.ComponentType#getBaseComponent()
	 * @see #getComponentType()
	 * @generated
	 */
	EAttribute getComponentType_BaseComponent();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ComponentType#getCategory <em>Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Category</em>'.
	 * @see com.nokia.sdt.emf.component.ComponentType#getCategory()
	 * @see #getComponentType()
	 * @generated
	 */
	EAttribute getComponentType_Category();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ComponentType#getFriendlyName <em>Friendly Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Friendly Name</em>'.
	 * @see com.nokia.sdt.emf.component.ComponentType#getFriendlyName()
	 * @see #getComponentType()
	 * @generated
	 */
	EAttribute getComponentType_FriendlyName();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ComponentType#getInstanceNameRoot <em>Instance Name Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Instance Name Root</em>'.
	 * @see com.nokia.sdt.emf.component.ComponentType#getInstanceNameRoot()
	 * @see #getComponentType()
	 * @generated
	 */
	EAttribute getComponentType_InstanceNameRoot();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ComponentType#getQualifiedName <em>Qualified Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Qualified Name</em>'.
	 * @see com.nokia.sdt.emf.component.ComponentType#getQualifiedName()
	 * @see #getComponentType()
	 * @generated
	 */
	EAttribute getComponentType_QualifiedName();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ComponentType#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see com.nokia.sdt.emf.component.ComponentType#getVersion()
	 * @see #getComponentType()
	 * @generated
	 */
	EAttribute getComponentType_Version();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.CompoundPropertyDeclarationType <em>Compound Property Declaration Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Compound Property Declaration Type</em>'.
	 * @see com.nokia.sdt.emf.component.CompoundPropertyDeclarationType
	 * @generated
	 */
	EClass getCompoundPropertyDeclarationType();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.sdt.emf.component.CompoundPropertyDeclarationType#getAbstractPropertyGroup <em>Abstract Property Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Abstract Property Group</em>'.
	 * @see com.nokia.sdt.emf.component.CompoundPropertyDeclarationType#getAbstractPropertyGroup()
	 * @see #getCompoundPropertyDeclarationType()
	 * @generated
	 */
	EAttribute getCompoundPropertyDeclarationType_AbstractPropertyGroup();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.CompoundPropertyDeclarationType#getAbstractProperty <em>Abstract Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Abstract Property</em>'.
	 * @see com.nokia.sdt.emf.component.CompoundPropertyDeclarationType#getAbstractProperty()
	 * @see #getCompoundPropertyDeclarationType()
	 * @generated
	 */
	EReference getCompoundPropertyDeclarationType_AbstractProperty();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.CompoundPropertyDeclarationType#getSourceTypeMapping <em>Source Type Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Source Type Mapping</em>'.
	 * @see com.nokia.sdt.emf.component.CompoundPropertyDeclarationType#getSourceTypeMapping()
	 * @see #getCompoundPropertyDeclarationType()
	 * @generated
	 */
	EReference getCompoundPropertyDeclarationType_SourceTypeMapping();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.CompoundPropertyDeclarationType#getConverterClass <em>Converter Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Converter Class</em>'.
	 * @see com.nokia.sdt.emf.component.CompoundPropertyDeclarationType#getConverterClass()
	 * @see #getCompoundPropertyDeclarationType()
	 * @generated
	 */
	EAttribute getCompoundPropertyDeclarationType_ConverterClass();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.CompoundPropertyDeclarationType#getEditableType <em>Editable Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Editable Type</em>'.
	 * @see com.nokia.sdt.emf.component.CompoundPropertyDeclarationType#getEditableType()
	 * @see #getCompoundPropertyDeclarationType()
	 * @generated
	 */
	EAttribute getCompoundPropertyDeclarationType_EditableType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.CompoundPropertyDeclarationType#getEditorClass <em>Editor Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Editor Class</em>'.
	 * @see com.nokia.sdt.emf.component.CompoundPropertyDeclarationType#getEditorClass()
	 * @see #getCompoundPropertyDeclarationType()
	 * @generated
	 */
	EAttribute getCompoundPropertyDeclarationType_EditorClass();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.CompoundPropertyDeclarationType#getQualifiedName <em>Qualified Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Qualified Name</em>'.
	 * @see com.nokia.sdt.emf.component.CompoundPropertyDeclarationType#getQualifiedName()
	 * @see #getCompoundPropertyDeclarationType()
	 * @generated
	 */
	EAttribute getCompoundPropertyDeclarationType_QualifiedName();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.CompoundPropertyType <em>Compound Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Compound Property Type</em>'.
	 * @see com.nokia.sdt.emf.component.CompoundPropertyType
	 * @generated
	 */
	EClass getCompoundPropertyType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.CompoundPropertyType#getDefault <em>Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default</em>'.
	 * @see com.nokia.sdt.emf.component.CompoundPropertyType#getDefault()
	 * @see #getCompoundPropertyType()
	 * @generated
	 */
	EAttribute getCompoundPropertyType_Default();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.CompoundPropertyType#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.nokia.sdt.emf.component.CompoundPropertyType#getType()
	 * @see #getCompoundPropertyType()
	 * @generated
	 */
	EAttribute getCompoundPropertyType_Type();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.ConditionalSourceGen <em>Conditional Source Gen</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Conditional Source Gen</em>'.
	 * @see com.nokia.sdt.emf.component.ConditionalSourceGen
	 * @generated
	 */
	EClass getConditionalSourceGen();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ConditionalSourceGen#getForms <em>Forms</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Forms</em>'.
	 * @see com.nokia.sdt.emf.component.ConditionalSourceGen#getForms()
	 * @see #getConditionalSourceGen()
	 * @generated
	 */
	EAttribute getConditionalSourceGen_Forms();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ConditionalSourceGen#getIfEvents <em>If Events</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>If Events</em>'.
	 * @see com.nokia.sdt.emf.component.ConditionalSourceGen#getIfEvents()
	 * @see #getConditionalSourceGen()
	 * @generated
	 */
	EAttribute getConditionalSourceGen_IfEvents();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ConditionalSourceGen#getIfExpr <em>If Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>If Expr</em>'.
	 * @see com.nokia.sdt.emf.component.ConditionalSourceGen#getIfExpr()
	 * @see #getConditionalSourceGen()
	 * @generated
	 */
	EAttribute getConditionalSourceGen_IfExpr();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.ConditionalSourceGenString <em>Conditional Source Gen String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Conditional Source Gen String</em>'.
	 * @see com.nokia.sdt.emf.component.ConditionalSourceGenString
	 * @generated
	 */
	EClass getConditionalSourceGenString();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ConditionalSourceGenString#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.nokia.sdt.emf.component.ConditionalSourceGenString#getValue()
	 * @see #getConditionalSourceGenString()
	 * @generated
	 */
	EAttribute getConditionalSourceGenString_Value();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ConditionalSourceGenString#getForms <em>Forms</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Forms</em>'.
	 * @see com.nokia.sdt.emf.component.ConditionalSourceGenString#getForms()
	 * @see #getConditionalSourceGenString()
	 * @generated
	 */
	EAttribute getConditionalSourceGenString_Forms();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ConditionalSourceGenString#getIfEvents <em>If Events</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>If Events</em>'.
	 * @see com.nokia.sdt.emf.component.ConditionalSourceGenString#getIfEvents()
	 * @see #getConditionalSourceGenString()
	 * @generated
	 */
	EAttribute getConditionalSourceGenString_IfEvents();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ConditionalSourceGenString#getIfExpr <em>If Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>If Expr</em>'.
	 * @see com.nokia.sdt.emf.component.ConditionalSourceGenString#getIfExpr()
	 * @see #getConditionalSourceGenString()
	 * @generated
	 */
	EAttribute getConditionalSourceGenString_IfExpr();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.DefineLocationType <em>Define Location Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Define Location Type</em>'.
	 * @see com.nokia.sdt.emf.component.DefineLocationType
	 * @generated
	 */
	EClass getDefineLocationType();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.sdt.emf.component.DefineLocationType#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.nokia.sdt.emf.component.DefineLocationType#getGroup()
	 * @see #getDefineLocationType()
	 * @generated
	 */
	EAttribute getDefineLocationType_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.DefineLocationType#getTemplate <em>Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Template</em>'.
	 * @see com.nokia.sdt.emf.component.DefineLocationType#getTemplate()
	 * @see #getDefineLocationType()
	 * @generated
	 */
	EReference getDefineLocationType_Template();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.DefineLocationType#getInline <em>Inline</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Inline</em>'.
	 * @see com.nokia.sdt.emf.component.DefineLocationType#getInline()
	 * @see #getDefineLocationType()
	 * @generated
	 */
	EReference getDefineLocationType_Inline();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.DefineLocationType#getScript <em>Script</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Script</em>'.
	 * @see com.nokia.sdt.emf.component.DefineLocationType#getScript()
	 * @see #getDefineLocationType()
	 * @generated
	 */
	EReference getDefineLocationType_Script();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.DefineLocationType#getExpandMacro <em>Expand Macro</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Expand Macro</em>'.
	 * @see com.nokia.sdt.emf.component.DefineLocationType#getExpandMacro()
	 * @see #getDefineLocationType()
	 * @generated
	 */
	EReference getDefineLocationType_ExpandMacro();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.DefineLocationType#getBaseLocation <em>Base Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Base Location</em>'.
	 * @see com.nokia.sdt.emf.component.DefineLocationType#getBaseLocation()
	 * @see #getDefineLocationType()
	 * @generated
	 */
	EAttribute getDefineLocationType_BaseLocation();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.DefineLocationType#getDir <em>Dir</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Dir</em>'.
	 * @see com.nokia.sdt.emf.component.DefineLocationType#getDir()
	 * @see #getDefineLocationType()
	 * @generated
	 */
	EAttribute getDefineLocationType_Dir();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.DefineLocationType#getDomain <em>Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Domain</em>'.
	 * @see com.nokia.sdt.emf.component.DefineLocationType#getDomain()
	 * @see #getDefineLocationType()
	 * @generated
	 */
	EAttribute getDefineLocationType_Domain();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.DefineLocationType#getFile <em>File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File</em>'.
	 * @see com.nokia.sdt.emf.component.DefineLocationType#getFile()
	 * @see #getDefineLocationType()
	 * @generated
	 */
	EAttribute getDefineLocationType_File();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.DefineLocationType#getFilter <em>Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Filter</em>'.
	 * @see com.nokia.sdt.emf.component.DefineLocationType#getFilter()
	 * @see #getDefineLocationType()
	 * @generated
	 */
	EAttribute getDefineLocationType_Filter();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.DefineLocationType#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.nokia.sdt.emf.component.DefineLocationType#getId()
	 * @see #getDefineLocationType()
	 * @generated
	 */
	EAttribute getDefineLocationType_Id();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.DefineLocationType#getIfEvents <em>If Events</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>If Events</em>'.
	 * @see com.nokia.sdt.emf.component.DefineLocationType#getIfEvents()
	 * @see #getDefineLocationType()
	 * @generated
	 */
	EAttribute getDefineLocationType_IfEvents();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.DefineLocationType#getIsEventHandler <em>Is Event Handler</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Event Handler</em>'.
	 * @see com.nokia.sdt.emf.component.DefineLocationType#getIsEventHandler()
	 * @see #getDefineLocationType()
	 * @generated
	 */
	EAttribute getDefineLocationType_IsEventHandler();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.DefineLocationType#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Location</em>'.
	 * @see com.nokia.sdt.emf.component.DefineLocationType#getLocation()
	 * @see #getDefineLocationType()
	 * @generated
	 */
	EAttribute getDefineLocationType_Location();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.DefineLocationType#getOwned <em>Owned</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Owned</em>'.
	 * @see com.nokia.sdt.emf.component.DefineLocationType#getOwned()
	 * @see #getDefineLocationType()
	 * @generated
	 */
	EAttribute getDefineLocationType_Owned();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.DefineLocationType#getRealize <em>Realize</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Realize</em>'.
	 * @see com.nokia.sdt.emf.component.DefineLocationType#getRealize()
	 * @see #getDefineLocationType()
	 * @generated
	 */
	EAttribute getDefineLocationType_Realize();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.DefineMacroType <em>Define Macro Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Define Macro Type</em>'.
	 * @see com.nokia.sdt.emf.component.DefineMacroType
	 * @generated
	 */
	EClass getDefineMacroType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.DefineMacroType#getImportArguments <em>Import Arguments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Import Arguments</em>'.
	 * @see com.nokia.sdt.emf.component.DefineMacroType#getImportArguments()
	 * @see #getDefineMacroType()
	 * @generated
	 */
	EReference getDefineMacroType_ImportArguments();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.DefineMacroType#getMacroArgument <em>Macro Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Macro Argument</em>'.
	 * @see com.nokia.sdt.emf.component.DefineMacroType#getMacroArgument()
	 * @see #getDefineMacroType()
	 * @generated
	 */
	EReference getDefineMacroType_MacroArgument();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.sdt.emf.component.DefineMacroType#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.nokia.sdt.emf.component.DefineMacroType#getGroup()
	 * @see #getDefineMacroType()
	 * @generated
	 */
	EAttribute getDefineMacroType_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.DefineMacroType#getTemplate <em>Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Template</em>'.
	 * @see com.nokia.sdt.emf.component.DefineMacroType#getTemplate()
	 * @see #getDefineMacroType()
	 * @generated
	 */
	EReference getDefineMacroType_Template();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.DefineMacroType#getInline <em>Inline</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Inline</em>'.
	 * @see com.nokia.sdt.emf.component.DefineMacroType#getInline()
	 * @see #getDefineMacroType()
	 * @generated
	 */
	EReference getDefineMacroType_Inline();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.DefineMacroType#getDefineLocation <em>Define Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Define Location</em>'.
	 * @see com.nokia.sdt.emf.component.DefineMacroType#getDefineLocation()
	 * @see #getDefineMacroType()
	 * @generated
	 */
	EReference getDefineMacroType_DefineLocation();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.DefineMacroType#getExpandMacro <em>Expand Macro</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Expand Macro</em>'.
	 * @see com.nokia.sdt.emf.component.DefineMacroType#getExpandMacro()
	 * @see #getDefineMacroType()
	 * @generated
	 */
	EReference getDefineMacroType_ExpandMacro();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.DefineMacroType#getHelp <em>Help</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Help</em>'.
	 * @see com.nokia.sdt.emf.component.DefineMacroType#getHelp()
	 * @see #getDefineMacroType()
	 * @generated
	 */
	EAttribute getDefineMacroType_Help();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.DefineMacroType#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.nokia.sdt.emf.component.DefineMacroType#getId()
	 * @see #getDefineMacroType()
	 * @generated
	 */
	EAttribute getDefineMacroType_Id();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.DesignerImagesType <em>Designer Images Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Designer Images Type</em>'.
	 * @see com.nokia.sdt.emf.component.DesignerImagesType
	 * @generated
	 */
	EClass getDesignerImagesType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.DesignerImagesType#getLargeIconFile <em>Large Icon File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Large Icon File</em>'.
	 * @see com.nokia.sdt.emf.component.DesignerImagesType#getLargeIconFile()
	 * @see #getDesignerImagesType()
	 * @generated
	 */
	EAttribute getDesignerImagesType_LargeIconFile();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.DesignerImagesType#getLayoutImageFile <em>Layout Image File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Layout Image File</em>'.
	 * @see com.nokia.sdt.emf.component.DesignerImagesType#getLayoutImageFile()
	 * @see #getDesignerImagesType()
	 * @generated
	 */
	EAttribute getDesignerImagesType_LayoutImageFile();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.DesignerImagesType#getSmallIconFile <em>Small Icon File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Small Icon File</em>'.
	 * @see com.nokia.sdt.emf.component.DesignerImagesType#getSmallIconFile()
	 * @see #getDesignerImagesType()
	 * @generated
	 */
	EAttribute getDesignerImagesType_SmallIconFile();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.DesignerImagesType#getThumbnailFile <em>Thumbnail File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Thumbnail File</em>'.
	 * @see com.nokia.sdt.emf.component.DesignerImagesType#getThumbnailFile()
	 * @see #getDesignerImagesType()
	 * @generated
	 */
	EAttribute getDesignerImagesType_ThumbnailFile();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.DocumentationType <em>Documentation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Documentation Type</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentationType
	 * @generated
	 */
	EClass getDocumentationType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.DocumentationType#getInformation <em>Information</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Information</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentationType#getInformation()
	 * @see #getDocumentationType()
	 * @generated
	 */
	EAttribute getDocumentationType_Information();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.DocumentationType#getHelpTopic <em>Help Topic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Help Topic</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentationType#getHelpTopic()
	 * @see #getDocumentationType()
	 * @generated
	 */
	EAttribute getDocumentationType_HelpTopic();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.DocumentationType#getWizardDescription <em>Wizard Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Wizard Description</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentationType#getWizardDescription()
	 * @see #getDocumentationType()
	 * @generated
	 */
	EAttribute getDocumentationType_WizardDescription();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.sdt.emf.component.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link com.nokia.sdt.emf.component.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link com.nokia.sdt.emf.component.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getAbstractProperty <em>Abstract Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Abstract Property</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getAbstractProperty()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_AbstractProperty();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getArrayProperty <em>Array Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Array Property</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getArrayProperty()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ArrayProperty();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Attribute</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getAttribute()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Attribute();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Attributes</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getAttributes()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Attributes();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getChoice <em>Choice</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Choice</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getChoice()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Choice();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getCode <em>Code</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Code</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getCode()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Code();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getComponent <em>Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Component</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getComponent()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Component();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getComponentDefinition <em>Component Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Component Definition</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getComponentDefinition()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ComponentDefinition();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getComponentReferenceProperty <em>Component Reference Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Component Reference Property</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getComponentReferenceProperty()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ComponentReferenceProperty();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getCompoundProperty <em>Compound Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Compound Property</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getCompoundProperty()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_CompoundProperty();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getCompoundPropertyDeclaration <em>Compound Property Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Compound Property Declaration</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getCompoundPropertyDeclaration()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_CompoundPropertyDeclaration();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getDefineLocation <em>Define Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Define Location</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getDefineLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_DefineLocation();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getDefineMacro <em>Define Macro</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Define Macro</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getDefineMacro()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_DefineMacro();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getDesignerImages <em>Designer Images</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Designer Images</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getDesignerImages()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_DesignerImages();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getDocumentation <em>Documentation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Documentation</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getDocumentation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Documentation();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getEnumProperty <em>Enum Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Enum Property</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getEnumProperty()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_EnumProperty();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getEnumPropertyDeclaration <em>Enum Property Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Enum Property Declaration</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getEnumPropertyDeclaration()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_EnumPropertyDeclaration();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getEvent <em>Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Event</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getEvent()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Event();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getEvents <em>Events</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Events</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getEvents()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Events();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getExpandArgument <em>Expand Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expand Argument</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getExpandArgument()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ExpandArgument();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getExpandMacro <em>Expand Macro</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expand Macro</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getExpandMacro()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ExpandMacro();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getExtensionProperties <em>Extension Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Extension Properties</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getExtensionProperties()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ExtensionProperties();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getImplementation <em>Implementation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Implementation</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getImplementation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Implementation();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getImplementations <em>Implementations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Implementations</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getImplementations()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Implementations();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getImportArguments <em>Import Arguments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Import Arguments</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getImportArguments()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ImportArguments();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getInline <em>Inline</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Inline</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getInline()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Inline();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMacroArgument <em>Macro Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Macro Argument</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMacroArgument()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MacroArgument();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapArrayMember <em>Map Array Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Array Member</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapArrayMember()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapArrayMember();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getTwoWayMapping <em>Two Way Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Two Way Mapping</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getTwoWayMapping()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_TwoWayMapping();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapArrayType <em>Map Array Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Array Type</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapArrayType()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapArrayType();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapBitmaskElement <em>Map Bitmask Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Bitmask Element</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapBitmaskElement()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapBitmaskElement();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapBitmaskMember <em>Map Bitmask Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Bitmask Member</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapBitmaskMember()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapBitmaskMember();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapBitmaskType <em>Map Bitmask Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Bitmask Type</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapBitmaskType()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapBitmaskType();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapBitmaskValue <em>Map Bitmask Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Bitmask Value</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapBitmaskValue()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapBitmaskValue();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapElementFromType <em>Map Element From Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Element From Type</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapElementFromType()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapElementFromType();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapEnum <em>Map Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Enum</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapEnum()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapEnum();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapEnumElement <em>Map Enum Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Enum Element</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapEnumElement()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapEnumElement();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapEnumMember <em>Map Enum Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Enum Member</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapEnumMember()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapEnumMember();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapEnumType <em>Map Enum Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Enum Type</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapEnumType()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapEnumType();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapFixedElement <em>Map Fixed Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Fixed Element</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapFixedElement()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapFixedElement();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapFixedMember <em>Map Fixed Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Fixed Member</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapFixedMember()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapFixedMember();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapFixedType <em>Map Fixed Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Fixed Type</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapFixedType()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapFixedType();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapIdentifierElement <em>Map Identifier Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Identifier Element</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapIdentifierElement()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapIdentifierElement();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapIdentifierMember <em>Map Identifier Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Identifier Member</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapIdentifierMember()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapIdentifierMember();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapIdentifierType <em>Map Identifier Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Identifier Type</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapIdentifierType()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapIdentifierType();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapInstanceElement <em>Map Instance Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Instance Element</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapInstanceElement()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapInstanceElement();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapInstanceMember <em>Map Instance Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Instance Member</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapInstanceMember()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapInstanceMember();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapInstanceType <em>Map Instance Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Instance Type</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapInstanceType()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapInstanceType();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapIntoProperty <em>Map Into Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Into Property</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapIntoProperty()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapIntoProperty();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapMemberFromType <em>Map Member From Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Member From Type</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapMemberFromType()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapMemberFromType();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapReferenceElement <em>Map Reference Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Reference Element</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapReferenceElement()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapReferenceElement();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapReferenceMember <em>Map Reference Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Reference Member</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapReferenceMember()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapReferenceMember();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapReferenceType <em>Map Reference Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Reference Type</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapReferenceType()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapReferenceType();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapResource <em>Map Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Resource</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapResource()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapResource();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapResourceElement <em>Map Resource Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Resource Element</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapResourceElement()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapResourceElement();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapResourceMember <em>Map Resource Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Resource Member</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapResourceMember()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapResourceMember();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapResourceType <em>Map Resource Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Resource Type</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapResourceType()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapResourceType();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapSimpleElement <em>Map Simple Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Simple Element</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapSimpleElement()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapSimpleElement();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapSimpleMember <em>Map Simple Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Simple Member</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapSimpleMember()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapSimpleMember();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getMapSimpleType <em>Map Simple Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Map Simple Type</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getMapSimpleType()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MapSimpleType();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Properties</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getProperties()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Properties();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Property</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getProperty()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Property();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getPropertyOverrides <em>Property Overrides</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Property Overrides</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getPropertyOverrides()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_PropertyOverrides();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getScript <em>Script</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Script</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getScript()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Script();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getSelect <em>Select</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Select</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getSelect()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Select();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getSourceGen <em>Source Gen</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Source Gen</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getSourceGen()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_SourceGen();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getSourceMapping <em>Source Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Source Mapping</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getSourceMapping()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_SourceMapping();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getSourceTypeMapping <em>Source Type Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Source Type Mapping</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getSourceTypeMapping()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_SourceTypeMapping();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getSymbian <em>Symbian</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Symbian</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getSymbian()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Symbian();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getTemplate <em>Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Template</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getTemplate()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Template();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getTemplateGroup <em>Template Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Template Group</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getTemplateGroup()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_TemplateGroup();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getUseTemplate <em>Use Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Use Template</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getUseTemplate()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_UseTemplate();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.DocumentRoot#getUseTemplateGroup <em>Use Template Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Use Template Group</em>'.
	 * @see com.nokia.sdt.emf.component.DocumentRoot#getUseTemplateGroup()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_UseTemplateGroup();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.EnumElementType <em>Enum Element Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enum Element Type</em>'.
	 * @see com.nokia.sdt.emf.component.EnumElementType
	 * @generated
	 */
	EClass getEnumElementType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.EnumElementType#getDisplayValue <em>Display Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Value</em>'.
	 * @see com.nokia.sdt.emf.component.EnumElementType#getDisplayValue()
	 * @see #getEnumElementType()
	 * @generated
	 */
	EAttribute getEnumElementType_DisplayValue();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.EnumElementType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.nokia.sdt.emf.component.EnumElementType#getValue()
	 * @see #getEnumElementType()
	 * @generated
	 */
	EAttribute getEnumElementType_Value();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.EnumPropertyDeclarationType <em>Enum Property Declaration Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enum Property Declaration Type</em>'.
	 * @see com.nokia.sdt.emf.component.EnumPropertyDeclarationType
	 * @generated
	 */
	EClass getEnumPropertyDeclarationType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.EnumPropertyDeclarationType#getEnumElement <em>Enum Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Enum Element</em>'.
	 * @see com.nokia.sdt.emf.component.EnumPropertyDeclarationType#getEnumElement()
	 * @see #getEnumPropertyDeclarationType()
	 * @generated
	 */
	EReference getEnumPropertyDeclarationType_EnumElement();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.EnumPropertyDeclarationType#getSourceTypeMapping <em>Source Type Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Source Type Mapping</em>'.
	 * @see com.nokia.sdt.emf.component.EnumPropertyDeclarationType#getSourceTypeMapping()
	 * @see #getEnumPropertyDeclarationType()
	 * @generated
	 */
	EReference getEnumPropertyDeclarationType_SourceTypeMapping();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.EnumPropertyDeclarationType#getDefaultValue <em>Default Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Value</em>'.
	 * @see com.nokia.sdt.emf.component.EnumPropertyDeclarationType#getDefaultValue()
	 * @see #getEnumPropertyDeclarationType()
	 * @generated
	 */
	EAttribute getEnumPropertyDeclarationType_DefaultValue();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.EnumPropertyDeclarationType#getQualifiedName <em>Qualified Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Qualified Name</em>'.
	 * @see com.nokia.sdt.emf.component.EnumPropertyDeclarationType#getQualifiedName()
	 * @see #getEnumPropertyDeclarationType()
	 * @generated
	 */
	EAttribute getEnumPropertyDeclarationType_QualifiedName();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.EnumPropertyType <em>Enum Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enum Property Type</em>'.
	 * @see com.nokia.sdt.emf.component.EnumPropertyType
	 * @generated
	 */
	EClass getEnumPropertyType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.EnumPropertyType#getDefault <em>Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default</em>'.
	 * @see com.nokia.sdt.emf.component.EnumPropertyType#getDefault()
	 * @see #getEnumPropertyType()
	 * @generated
	 */
	EAttribute getEnumPropertyType_Default();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.EnumPropertyType#getExtendWithEnum <em>Extend With Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Extend With Enum</em>'.
	 * @see com.nokia.sdt.emf.component.EnumPropertyType#getExtendWithEnum()
	 * @see #getEnumPropertyType()
	 * @generated
	 */
	EAttribute getEnumPropertyType_ExtendWithEnum();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.EnumPropertyType#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.nokia.sdt.emf.component.EnumPropertyType#getType()
	 * @see #getEnumPropertyType()
	 * @generated
	 */
	EAttribute getEnumPropertyType_Type();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.EventsType <em>Events Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Events Type</em>'.
	 * @see com.nokia.sdt.emf.component.EventsType
	 * @generated
	 */
	EClass getEventsType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.EventsType#getEvent <em>Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Event</em>'.
	 * @see com.nokia.sdt.emf.component.EventsType#getEvent()
	 * @see #getEventsType()
	 * @generated
	 */
	EReference getEventsType_Event();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.EventsType#getDefaultEventName <em>Default Event Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Event Name</em>'.
	 * @see com.nokia.sdt.emf.component.EventsType#getDefaultEventName()
	 * @see #getEventsType()
	 * @generated
	 */
	EAttribute getEventsType_DefaultEventName();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.EventType <em>Event Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event Type</em>'.
	 * @see com.nokia.sdt.emf.component.EventType
	 * @generated
	 */
	EClass getEventType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.EventType#getCategory <em>Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Category</em>'.
	 * @see com.nokia.sdt.emf.component.EventType#getCategory()
	 * @see #getEventType()
	 * @generated
	 */
	EAttribute getEventType_Category();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.EventType#getDescriptionKey <em>Description Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description Key</em>'.
	 * @see com.nokia.sdt.emf.component.EventType#getDescriptionKey()
	 * @see #getEventType()
	 * @generated
	 */
	EAttribute getEventType_DescriptionKey();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.EventType#getDisplayName <em>Display Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Name</em>'.
	 * @see com.nokia.sdt.emf.component.EventType#getDisplayName()
	 * @see #getEventType()
	 * @generated
	 */
	EAttribute getEventType_DisplayName();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.EventType#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Group</em>'.
	 * @see com.nokia.sdt.emf.component.EventType#getGroup()
	 * @see #getEventType()
	 * @generated
	 */
	EAttribute getEventType_Group();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.EventType#getHandlerNameTemplate <em>Handler Name Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Handler Name Template</em>'.
	 * @see com.nokia.sdt.emf.component.EventType#getHandlerNameTemplate()
	 * @see #getEventType()
	 * @generated
	 */
	EAttribute getEventType_HandlerNameTemplate();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.EventType#getHelpKey <em>Help Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Help Key</em>'.
	 * @see com.nokia.sdt.emf.component.EventType#getHelpKey()
	 * @see #getEventType()
	 * @generated
	 */
	EAttribute getEventType_HelpKey();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.EventType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.nokia.sdt.emf.component.EventType#getName()
	 * @see #getEventType()
	 * @generated
	 */
	EAttribute getEventType_Name();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.ExpandArgumentType <em>Expand Argument Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expand Argument Type</em>'.
	 * @see com.nokia.sdt.emf.component.ExpandArgumentType
	 * @generated
	 */
	EClass getExpandArgumentType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ExpandArgumentType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.nokia.sdt.emf.component.ExpandArgumentType#getValue()
	 * @see #getExpandArgumentType()
	 * @generated
	 */
	EAttribute getExpandArgumentType_Value();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ExpandArgumentType#getHelp <em>Help</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Help</em>'.
	 * @see com.nokia.sdt.emf.component.ExpandArgumentType#getHelp()
	 * @see #getExpandArgumentType()
	 * @generated
	 */
	EAttribute getExpandArgumentType_Help();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ExpandArgumentType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.nokia.sdt.emf.component.ExpandArgumentType#getName()
	 * @see #getExpandArgumentType()
	 * @generated
	 */
	EAttribute getExpandArgumentType_Name();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.ExpandMacroType <em>Expand Macro Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expand Macro Type</em>'.
	 * @see com.nokia.sdt.emf.component.ExpandMacroType
	 * @generated
	 */
	EClass getExpandMacroType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.ExpandMacroType#getExpandArgument <em>Expand Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Expand Argument</em>'.
	 * @see com.nokia.sdt.emf.component.ExpandMacroType#getExpandArgument()
	 * @see #getExpandMacroType()
	 * @generated
	 */
	EReference getExpandMacroType_ExpandArgument();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ExpandMacroType#getDontPassArguments <em>Dont Pass Arguments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Dont Pass Arguments</em>'.
	 * @see com.nokia.sdt.emf.component.ExpandMacroType#getDontPassArguments()
	 * @see #getExpandMacroType()
	 * @generated
	 */
	EAttribute getExpandMacroType_DontPassArguments();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ExpandMacroType#getHelp <em>Help</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Help</em>'.
	 * @see com.nokia.sdt.emf.component.ExpandMacroType#getHelp()
	 * @see #getExpandMacroType()
	 * @generated
	 */
	EAttribute getExpandMacroType_Help();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ExpandMacroType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.nokia.sdt.emf.component.ExpandMacroType#getName()
	 * @see #getExpandMacroType()
	 * @generated
	 */
	EAttribute getExpandMacroType_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ExpandMacroType#getPassArguments <em>Pass Arguments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pass Arguments</em>'.
	 * @see com.nokia.sdt.emf.component.ExpandMacroType#getPassArguments()
	 * @see #getExpandMacroType()
	 * @generated
	 */
	EAttribute getExpandMacroType_PassArguments();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.sdt.emf.component.ExpandMacroType#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see com.nokia.sdt.emf.component.ExpandMacroType#getAnyAttribute()
	 * @see #getExpandMacroType()
	 * @generated
	 */
	EAttribute getExpandMacroType_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.ExtensionPropertiesType <em>Extension Properties Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Extension Properties Type</em>'.
	 * @see com.nokia.sdt.emf.component.ExtensionPropertiesType
	 * @generated
	 */
	EClass getExtensionPropertiesType();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.sdt.emf.component.ExtensionPropertiesType#getAbstractPropertyGroup <em>Abstract Property Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Abstract Property Group</em>'.
	 * @see com.nokia.sdt.emf.component.ExtensionPropertiesType#getAbstractPropertyGroup()
	 * @see #getExtensionPropertiesType()
	 * @generated
	 */
	EAttribute getExtensionPropertiesType_AbstractPropertyGroup();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.ExtensionPropertiesType#getAbstractProperty <em>Abstract Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Abstract Property</em>'.
	 * @see com.nokia.sdt.emf.component.ExtensionPropertiesType#getAbstractProperty()
	 * @see #getExtensionPropertiesType()
	 * @generated
	 */
	EReference getExtensionPropertiesType_AbstractProperty();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ExtensionPropertiesType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.nokia.sdt.emf.component.ExtensionPropertiesType#getName()
	 * @see #getExtensionPropertiesType()
	 * @generated
	 */
	EAttribute getExtensionPropertiesType_Name();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.ImplementationsType <em>Implementations Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Implementations Type</em>'.
	 * @see com.nokia.sdt.emf.component.ImplementationsType
	 * @generated
	 */
	EClass getImplementationsType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.ImplementationsType#getImplementation <em>Implementation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Implementation</em>'.
	 * @see com.nokia.sdt.emf.component.ImplementationsType#getImplementation()
	 * @see #getImplementationsType()
	 * @generated
	 */
	EReference getImplementationsType_Implementation();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.ImplementationType <em>Implementation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Implementation Type</em>'.
	 * @see com.nokia.sdt.emf.component.ImplementationType
	 * @generated
	 */
	EClass getImplementationType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.ImplementationType#getInterface <em>Interface</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Interface</em>'.
	 * @see com.nokia.sdt.emf.component.ImplementationType#getInterface()
	 * @see #getImplementationType()
	 * @generated
	 */
	EReference getImplementationType_Interface();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.ImplementationType#getCode <em>Code</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Code</em>'.
	 * @see com.nokia.sdt.emf.component.ImplementationType#getCode()
	 * @see #getImplementationType()
	 * @generated
	 */
	EReference getImplementationType_Code();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.ImplementationType#getScript <em>Script</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Script</em>'.
	 * @see com.nokia.sdt.emf.component.ImplementationType#getScript()
	 * @see #getImplementationType()
	 * @generated
	 */
	EReference getImplementationType_Script();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.ImportArgumentsType <em>Import Arguments Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Import Arguments Type</em>'.
	 * @see com.nokia.sdt.emf.component.ImportArgumentsType
	 * @generated
	 */
	EClass getImportArgumentsType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ImportArgumentsType#getArguments <em>Arguments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Arguments</em>'.
	 * @see com.nokia.sdt.emf.component.ImportArgumentsType#getArguments()
	 * @see #getImportArgumentsType()
	 * @generated
	 */
	EAttribute getImportArgumentsType_Arguments();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ImportArgumentsType#getExceptArguments <em>Except Arguments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Except Arguments</em>'.
	 * @see com.nokia.sdt.emf.component.ImportArgumentsType#getExceptArguments()
	 * @see #getImportArgumentsType()
	 * @generated
	 */
	EAttribute getImportArgumentsType_ExceptArguments();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ImportArgumentsType#getHelp <em>Help</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Help</em>'.
	 * @see com.nokia.sdt.emf.component.ImportArgumentsType#getHelp()
	 * @see #getImportArgumentsType()
	 * @generated
	 */
	EAttribute getImportArgumentsType_Help();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ImportArgumentsType#getMacroName <em>Macro Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Macro Name</em>'.
	 * @see com.nokia.sdt.emf.component.ImportArgumentsType#getMacroName()
	 * @see #getImportArgumentsType()
	 * @generated
	 */
	EAttribute getImportArgumentsType_MacroName();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.InlineType <em>Inline Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inline Type</em>'.
	 * @see com.nokia.sdt.emf.component.InlineType
	 * @generated
	 */
	EClass getInlineType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.InlineType#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.nokia.sdt.emf.component.InlineType#getId()
	 * @see #getInlineType()
	 * @generated
	 */
	EAttribute getInlineType_Id();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.InlineType#getScope <em>Scope</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Scope</em>'.
	 * @see com.nokia.sdt.emf.component.InlineType#getScope()
	 * @see #getInlineType()
	 * @generated
	 */
	EAttribute getInlineType_Scope();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.InterfaceType <em>Interface Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Interface Type</em>'.
	 * @see com.nokia.sdt.emf.component.InterfaceType
	 * @generated
	 */
	EClass getInterfaceType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.InterfaceType#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.nokia.sdt.emf.component.InterfaceType#getId()
	 * @see #getInterfaceType()
	 * @generated
	 */
	EAttribute getInterfaceType_Id();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MacroArgumentType <em>Macro Argument Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Macro Argument Type</em>'.
	 * @see com.nokia.sdt.emf.component.MacroArgumentType
	 * @generated
	 */
	EClass getMacroArgumentType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MacroArgumentType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.nokia.sdt.emf.component.MacroArgumentType#getValue()
	 * @see #getMacroArgumentType()
	 * @generated
	 */
	EAttribute getMacroArgumentType_Value();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MacroArgumentType#getDefault <em>Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default</em>'.
	 * @see com.nokia.sdt.emf.component.MacroArgumentType#getDefault()
	 * @see #getMacroArgumentType()
	 * @generated
	 */
	EAttribute getMacroArgumentType_Default();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MacroArgumentType#getHelp <em>Help</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Help</em>'.
	 * @see com.nokia.sdt.emf.component.MacroArgumentType#getHelp()
	 * @see #getMacroArgumentType()
	 * @generated
	 */
	EAttribute getMacroArgumentType_Help();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MacroArgumentType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.nokia.sdt.emf.component.MacroArgumentType#getName()
	 * @see #getMacroArgumentType()
	 * @generated
	 */
	EAttribute getMacroArgumentType_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MacroArgumentType#isOptional <em>Optional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Optional</em>'.
	 * @see com.nokia.sdt.emf.component.MacroArgumentType#isOptional()
	 * @see #getMacroArgumentType()
	 * @generated
	 */
	EAttribute getMacroArgumentType_Optional();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapArrayMemberType <em>Map Array Member Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Array Member Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapArrayMemberType
	 * @generated
	 */
	EClass getMapArrayMemberType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapArrayMemberType#getMember <em>Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Member</em>'.
	 * @see com.nokia.sdt.emf.component.MapArrayMemberType#getMember()
	 * @see #getMapArrayMemberType()
	 * @generated
	 */
	EAttribute getMapArrayMemberType_Member();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapArrayMemberType#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property</em>'.
	 * @see com.nokia.sdt.emf.component.MapArrayMemberType#getProperty()
	 * @see #getMapArrayMemberType()
	 * @generated
	 */
	EAttribute getMapArrayMemberType_Property();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapArrayMemberType#isSuppressDefault <em>Suppress Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Suppress Default</em>'.
	 * @see com.nokia.sdt.emf.component.MapArrayMemberType#isSuppressDefault()
	 * @see #getMapArrayMemberType()
	 * @generated
	 */
	EAttribute getMapArrayMemberType_SuppressDefault();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapArrayTypeType <em>Map Array Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Array Type Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapArrayTypeType
	 * @generated
	 */
	EClass getMapArrayTypeType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapArrayTypeType#getTypeId <em>Type Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Id</em>'.
	 * @see com.nokia.sdt.emf.component.MapArrayTypeType#getTypeId()
	 * @see #getMapArrayTypeType()
	 * @generated
	 */
	EAttribute getMapArrayTypeType_TypeId();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapBitmaskElementType <em>Map Bitmask Element Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Bitmask Element Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapBitmaskElementType
	 * @generated
	 */
	EClass getMapBitmaskElementType();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapBitmaskMemberType <em>Map Bitmask Member Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Bitmask Member Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapBitmaskMemberType
	 * @generated
	 */
	EClass getMapBitmaskMemberType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapBitmaskMemberType#getMember <em>Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Member</em>'.
	 * @see com.nokia.sdt.emf.component.MapBitmaskMemberType#getMember()
	 * @see #getMapBitmaskMemberType()
	 * @generated
	 */
	EAttribute getMapBitmaskMemberType_Member();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapBitmaskMemberType#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property</em>'.
	 * @see com.nokia.sdt.emf.component.MapBitmaskMemberType#getProperty()
	 * @see #getMapBitmaskMemberType()
	 * @generated
	 */
	EAttribute getMapBitmaskMemberType_Property();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapBitmaskMemberType#isSuppressDefault <em>Suppress Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Suppress Default</em>'.
	 * @see com.nokia.sdt.emf.component.MapBitmaskMemberType#isSuppressDefault()
	 * @see #getMapBitmaskMemberType()
	 * @generated
	 */
	EAttribute getMapBitmaskMemberType_SuppressDefault();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapBitmaskTypeType <em>Map Bitmask Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Bitmask Type Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapBitmaskTypeType
	 * @generated
	 */
	EClass getMapBitmaskTypeType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapBitmaskTypeType#getTypeId <em>Type Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Id</em>'.
	 * @see com.nokia.sdt.emf.component.MapBitmaskTypeType#getTypeId()
	 * @see #getMapBitmaskTypeType()
	 * @generated
	 */
	EAttribute getMapBitmaskTypeType_TypeId();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapBitmaskValueType <em>Map Bitmask Value Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Bitmask Value Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapBitmaskValueType
	 * @generated
	 */
	EClass getMapBitmaskValueType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapBitmaskValueType#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Properties</em>'.
	 * @see com.nokia.sdt.emf.component.MapBitmaskValueType#getProperties()
	 * @see #getMapBitmaskValueType()
	 * @generated
	 */
	EAttribute getMapBitmaskValueType_Properties();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapBitmaskValueType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.nokia.sdt.emf.component.MapBitmaskValueType#getValue()
	 * @see #getMapBitmaskValueType()
	 * @generated
	 */
	EAttribute getMapBitmaskValueType_Value();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapElementFromTypeType <em>Map Element From Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Element From Type Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapElementFromTypeType
	 * @generated
	 */
	EClass getMapElementFromTypeType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapElementFromTypeType#getTypeId <em>Type Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Id</em>'.
	 * @see com.nokia.sdt.emf.component.MapElementFromTypeType#getTypeId()
	 * @see #getMapElementFromTypeType()
	 * @generated
	 */
	EAttribute getMapElementFromTypeType_TypeId();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapEnumElementType <em>Map Enum Element Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Enum Element Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapEnumElementType
	 * @generated
	 */
	EClass getMapEnumElementType();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapEnumMemberType <em>Map Enum Member Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Enum Member Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapEnumMemberType
	 * @generated
	 */
	EClass getMapEnumMemberType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapEnumMemberType#getMember <em>Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Member</em>'.
	 * @see com.nokia.sdt.emf.component.MapEnumMemberType#getMember()
	 * @see #getMapEnumMemberType()
	 * @generated
	 */
	EAttribute getMapEnumMemberType_Member();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapEnumMemberType#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property</em>'.
	 * @see com.nokia.sdt.emf.component.MapEnumMemberType#getProperty()
	 * @see #getMapEnumMemberType()
	 * @generated
	 */
	EAttribute getMapEnumMemberType_Property();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapEnumMemberType#isSuppressDefault <em>Suppress Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Suppress Default</em>'.
	 * @see com.nokia.sdt.emf.component.MapEnumMemberType#isSuppressDefault()
	 * @see #getMapEnumMemberType()
	 * @generated
	 */
	EAttribute getMapEnumMemberType_SuppressDefault();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapEnumType <em>Map Enum Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Enum Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapEnumType
	 * @generated
	 */
	EClass getMapEnumType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapEnumType#getEnumerator <em>Enumerator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Enumerator</em>'.
	 * @see com.nokia.sdt.emf.component.MapEnumType#getEnumerator()
	 * @see #getMapEnumType()
	 * @generated
	 */
	EAttribute getMapEnumType_Enumerator();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapEnumType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.nokia.sdt.emf.component.MapEnumType#getValue()
	 * @see #getMapEnumType()
	 * @generated
	 */
	EAttribute getMapEnumType_Value();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapEnumTypeType <em>Map Enum Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Enum Type Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapEnumTypeType
	 * @generated
	 */
	EClass getMapEnumTypeType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapEnumTypeType#getTypeId <em>Type Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Id</em>'.
	 * @see com.nokia.sdt.emf.component.MapEnumTypeType#getTypeId()
	 * @see #getMapEnumTypeType()
	 * @generated
	 */
	EAttribute getMapEnumTypeType_TypeId();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapFixedElementType <em>Map Fixed Element Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Fixed Element Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapFixedElementType
	 * @generated
	 */
	EClass getMapFixedElementType();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapFixedMemberType <em>Map Fixed Member Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Fixed Member Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapFixedMemberType
	 * @generated
	 */
	EClass getMapFixedMemberType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapFixedMemberType#getMember <em>Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Member</em>'.
	 * @see com.nokia.sdt.emf.component.MapFixedMemberType#getMember()
	 * @see #getMapFixedMemberType()
	 * @generated
	 */
	EAttribute getMapFixedMemberType_Member();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapFixedMemberType#isSuppressDefault <em>Suppress Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Suppress Default</em>'.
	 * @see com.nokia.sdt.emf.component.MapFixedMemberType#isSuppressDefault()
	 * @see #getMapFixedMemberType()
	 * @generated
	 */
	EAttribute getMapFixedMemberType_SuppressDefault();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapFixedTypeType <em>Map Fixed Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Fixed Type Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapFixedTypeType
	 * @generated
	 */
	EClass getMapFixedTypeType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapFixedTypeType#getTypeId <em>Type Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Id</em>'.
	 * @see com.nokia.sdt.emf.component.MapFixedTypeType#getTypeId()
	 * @see #getMapFixedTypeType()
	 * @generated
	 */
	EAttribute getMapFixedTypeType_TypeId();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapIdentifierElementType <em>Map Identifier Element Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Identifier Element Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapIdentifierElementType
	 * @generated
	 */
	EClass getMapIdentifierElementType();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapIdentifierMemberType <em>Map Identifier Member Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Identifier Member Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapIdentifierMemberType
	 * @generated
	 */
	EClass getMapIdentifierMemberType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapIdentifierMemberType#getMember <em>Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Member</em>'.
	 * @see com.nokia.sdt.emf.component.MapIdentifierMemberType#getMember()
	 * @see #getMapIdentifierMemberType()
	 * @generated
	 */
	EAttribute getMapIdentifierMemberType_Member();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapIdentifierMemberType#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property</em>'.
	 * @see com.nokia.sdt.emf.component.MapIdentifierMemberType#getProperty()
	 * @see #getMapIdentifierMemberType()
	 * @generated
	 */
	EAttribute getMapIdentifierMemberType_Property();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapIdentifierMemberType#isSuppressDefault <em>Suppress Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Suppress Default</em>'.
	 * @see com.nokia.sdt.emf.component.MapIdentifierMemberType#isSuppressDefault()
	 * @see #getMapIdentifierMemberType()
	 * @generated
	 */
	EAttribute getMapIdentifierMemberType_SuppressDefault();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapIdentifierTypeType <em>Map Identifier Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Identifier Type Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapIdentifierTypeType
	 * @generated
	 */
	EClass getMapIdentifierTypeType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapIdentifierTypeType#getTypeId <em>Type Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Id</em>'.
	 * @see com.nokia.sdt.emf.component.MapIdentifierTypeType#getTypeId()
	 * @see #getMapIdentifierTypeType()
	 * @generated
	 */
	EAttribute getMapIdentifierTypeType_TypeId();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapInstanceElementType <em>Map Instance Element Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Instance Element Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapInstanceElementType
	 * @generated
	 */
	EClass getMapInstanceElementType();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapInstanceMemberType <em>Map Instance Member Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Instance Member Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapInstanceMemberType
	 * @generated
	 */
	EClass getMapInstanceMemberType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapInstanceMemberType#getMember <em>Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Member</em>'.
	 * @see com.nokia.sdt.emf.component.MapInstanceMemberType#getMember()
	 * @see #getMapInstanceMemberType()
	 * @generated
	 */
	EAttribute getMapInstanceMemberType_Member();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapInstanceMemberType#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property</em>'.
	 * @see com.nokia.sdt.emf.component.MapInstanceMemberType#getProperty()
	 * @see #getMapInstanceMemberType()
	 * @generated
	 */
	EAttribute getMapInstanceMemberType_Property();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapInstanceMemberType#isSuppressDefault <em>Suppress Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Suppress Default</em>'.
	 * @see com.nokia.sdt.emf.component.MapInstanceMemberType#isSuppressDefault()
	 * @see #getMapInstanceMemberType()
	 * @generated
	 */
	EAttribute getMapInstanceMemberType_SuppressDefault();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapInstanceTypeType <em>Map Instance Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Instance Type Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapInstanceTypeType
	 * @generated
	 */
	EClass getMapInstanceTypeType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapInstanceTypeType#getTypeId <em>Type Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Id</em>'.
	 * @see com.nokia.sdt.emf.component.MapInstanceTypeType#getTypeId()
	 * @see #getMapInstanceTypeType()
	 * @generated
	 */
	EAttribute getMapInstanceTypeType_TypeId();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapIntoPropertyType <em>Map Into Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Into Property Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapIntoPropertyType
	 * @generated
	 */
	EClass getMapIntoPropertyType();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.sdt.emf.component.MapIntoPropertyType#getTwoWayMappingGroup <em>Two Way Mapping Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Two Way Mapping Group</em>'.
	 * @see com.nokia.sdt.emf.component.MapIntoPropertyType#getTwoWayMappingGroup()
	 * @see #getMapIntoPropertyType()
	 * @generated
	 */
	EAttribute getMapIntoPropertyType_TwoWayMappingGroup();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.MapIntoPropertyType#getTwoWayMapping <em>Two Way Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Two Way Mapping</em>'.
	 * @see com.nokia.sdt.emf.component.MapIntoPropertyType#getTwoWayMapping()
	 * @see #getMapIntoPropertyType()
	 * @generated
	 */
	EReference getMapIntoPropertyType_TwoWayMapping();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapIntoPropertyType#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property</em>'.
	 * @see com.nokia.sdt.emf.component.MapIntoPropertyType#getProperty()
	 * @see #getMapIntoPropertyType()
	 * @generated
	 */
	EAttribute getMapIntoPropertyType_Property();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapMemberFromTypeType <em>Map Member From Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Member From Type Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapMemberFromTypeType
	 * @generated
	 */
	EClass getMapMemberFromTypeType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapMemberFromTypeType#getMember <em>Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Member</em>'.
	 * @see com.nokia.sdt.emf.component.MapMemberFromTypeType#getMember()
	 * @see #getMapMemberFromTypeType()
	 * @generated
	 */
	EAttribute getMapMemberFromTypeType_Member();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapMemberFromTypeType#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property</em>'.
	 * @see com.nokia.sdt.emf.component.MapMemberFromTypeType#getProperty()
	 * @see #getMapMemberFromTypeType()
	 * @generated
	 */
	EAttribute getMapMemberFromTypeType_Property();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapMemberFromTypeType#isSuppressDefault <em>Suppress Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Suppress Default</em>'.
	 * @see com.nokia.sdt.emf.component.MapMemberFromTypeType#isSuppressDefault()
	 * @see #getMapMemberFromTypeType()
	 * @generated
	 */
	EAttribute getMapMemberFromTypeType_SuppressDefault();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapMemberFromTypeType#getTypeId <em>Type Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Id</em>'.
	 * @see com.nokia.sdt.emf.component.MapMemberFromTypeType#getTypeId()
	 * @see #getMapMemberFromTypeType()
	 * @generated
	 */
	EAttribute getMapMemberFromTypeType_TypeId();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MappingArrayType <em>Mapping Array Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mapping Array Type</em>'.
	 * @see com.nokia.sdt.emf.component.MappingArrayType
	 * @generated
	 */
	EClass getMappingArrayType();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.sdt.emf.component.MappingArrayType#getTwoWayMappingGroup <em>Two Way Mapping Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Two Way Mapping Group</em>'.
	 * @see com.nokia.sdt.emf.component.MappingArrayType#getTwoWayMappingGroup()
	 * @see #getMappingArrayType()
	 * @generated
	 */
	EAttribute getMappingArrayType_TwoWayMappingGroup();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.MappingArrayType#getTwoWayMapping <em>Two Way Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Two Way Mapping</em>'.
	 * @see com.nokia.sdt.emf.component.MappingArrayType#getTwoWayMapping()
	 * @see #getMappingArrayType()
	 * @generated
	 */
	EReference getMappingArrayType_TwoWayMapping();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.component.MappingArrayType#getSelect <em>Select</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Select</em>'.
	 * @see com.nokia.sdt.emf.component.MappingArrayType#getSelect()
	 * @see #getMappingArrayType()
	 * @generated
	 */
	EReference getMappingArrayType_Select();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MappingBitmaskType <em>Mapping Bitmask Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mapping Bitmask Type</em>'.
	 * @see com.nokia.sdt.emf.component.MappingBitmaskType
	 * @generated
	 */
	EClass getMappingBitmaskType();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.sdt.emf.component.MappingBitmaskType#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.nokia.sdt.emf.component.MappingBitmaskType#getGroup()
	 * @see #getMappingBitmaskType()
	 * @generated
	 */
	EAttribute getMappingBitmaskType_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.MappingBitmaskType#getMapBitmaskValue <em>Map Bitmask Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Bitmask Value</em>'.
	 * @see com.nokia.sdt.emf.component.MappingBitmaskType#getMapBitmaskValue()
	 * @see #getMappingBitmaskType()
	 * @generated
	 */
	EReference getMappingBitmaskType_MapBitmaskValue();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MappingBitmaskType#getIncludedProperties <em>Included Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Included Properties</em>'.
	 * @see com.nokia.sdt.emf.component.MappingBitmaskType#getIncludedProperties()
	 * @see #getMappingBitmaskType()
	 * @generated
	 */
	EAttribute getMappingBitmaskType_IncludedProperties();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MappingEnumType <em>Mapping Enum Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mapping Enum Type</em>'.
	 * @see com.nokia.sdt.emf.component.MappingEnumType
	 * @generated
	 */
	EClass getMappingEnumType();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.sdt.emf.component.MappingEnumType#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.nokia.sdt.emf.component.MappingEnumType#getGroup()
	 * @see #getMappingEnumType()
	 * @generated
	 */
	EAttribute getMappingEnumType_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.MappingEnumType#getMapEnum <em>Map Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Enum</em>'.
	 * @see com.nokia.sdt.emf.component.MappingEnumType#getMapEnum()
	 * @see #getMappingEnumType()
	 * @generated
	 */
	EReference getMappingEnumType_MapEnum();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MappingEnumType#getEnumeration <em>Enumeration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Enumeration</em>'.
	 * @see com.nokia.sdt.emf.component.MappingEnumType#getEnumeration()
	 * @see #getMappingEnumType()
	 * @generated
	 */
	EAttribute getMappingEnumType_Enumeration();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MappingEnumType#getHeaders <em>Headers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Headers</em>'.
	 * @see com.nokia.sdt.emf.component.MappingEnumType#getHeaders()
	 * @see #getMappingEnumType()
	 * @generated
	 */
	EAttribute getMappingEnumType_Headers();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MappingEnumType#getNameAlgorithm <em>Name Algorithm</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name Algorithm</em>'.
	 * @see com.nokia.sdt.emf.component.MappingEnumType#getNameAlgorithm()
	 * @see #getMappingEnumType()
	 * @generated
	 */
	EAttribute getMappingEnumType_NameAlgorithm();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MappingEnumType#getUniqueValue <em>Unique Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Value</em>'.
	 * @see com.nokia.sdt.emf.component.MappingEnumType#getUniqueValue()
	 * @see #getMappingEnumType()
	 * @generated
	 */
	EAttribute getMappingEnumType_UniqueValue();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MappingEnumType#isValidate <em>Validate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Validate</em>'.
	 * @see com.nokia.sdt.emf.component.MappingEnumType#isValidate()
	 * @see #getMappingEnumType()
	 * @generated
	 */
	EAttribute getMappingEnumType_Validate();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MappingFixedType <em>Mapping Fixed Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mapping Fixed Type</em>'.
	 * @see com.nokia.sdt.emf.component.MappingFixedType
	 * @generated
	 */
	EClass getMappingFixedType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MappingFixedType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.nokia.sdt.emf.component.MappingFixedType#getValue()
	 * @see #getMappingFixedType()
	 * @generated
	 */
	EAttribute getMappingFixedType_Value();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MappingIdentifierType <em>Mapping Identifier Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mapping Identifier Type</em>'.
	 * @see com.nokia.sdt.emf.component.MappingIdentifierType
	 * @generated
	 */
	EClass getMappingIdentifierType();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MappingInstanceType <em>Mapping Instance Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mapping Instance Type</em>'.
	 * @see com.nokia.sdt.emf.component.MappingInstanceType
	 * @generated
	 */
	EClass getMappingInstanceType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MappingInstanceType#getRsrcId <em>Rsrc Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rsrc Id</em>'.
	 * @see com.nokia.sdt.emf.component.MappingInstanceType#getRsrcId()
	 * @see #getMappingInstanceType()
	 * @generated
	 */
	EAttribute getMappingInstanceType_RsrcId();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MappingReferenceType <em>Mapping Reference Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mapping Reference Type</em>'.
	 * @see com.nokia.sdt.emf.component.MappingReferenceType
	 * @generated
	 */
	EClass getMappingReferenceType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MappingReferenceType#getRsrcId <em>Rsrc Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rsrc Id</em>'.
	 * @see com.nokia.sdt.emf.component.MappingReferenceType#getRsrcId()
	 * @see #getMappingReferenceType()
	 * @generated
	 */
	EAttribute getMappingReferenceType_RsrcId();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MappingResourceType <em>Mapping Resource Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mapping Resource Type</em>'.
	 * @see com.nokia.sdt.emf.component.MappingResourceType
	 * @generated
	 */
	EClass getMappingResourceType();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.sdt.emf.component.MappingResourceType#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.nokia.sdt.emf.component.MappingResourceType#getGroup()
	 * @see #getMappingResourceType()
	 * @generated
	 */
	EAttribute getMappingResourceType_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.MappingResourceType#getMapSimpleMember <em>Map Simple Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Simple Member</em>'.
	 * @see com.nokia.sdt.emf.component.MappingResourceType#getMapSimpleMember()
	 * @see #getMappingResourceType()
	 * @generated
	 */
	EReference getMappingResourceType_MapSimpleMember();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.MappingResourceType#getMapInstanceMember <em>Map Instance Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Instance Member</em>'.
	 * @see com.nokia.sdt.emf.component.MappingResourceType#getMapInstanceMember()
	 * @see #getMappingResourceType()
	 * @generated
	 */
	EReference getMappingResourceType_MapInstanceMember();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.MappingResourceType#getMapReferenceMember <em>Map Reference Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Reference Member</em>'.
	 * @see com.nokia.sdt.emf.component.MappingResourceType#getMapReferenceMember()
	 * @see #getMappingResourceType()
	 * @generated
	 */
	EReference getMappingResourceType_MapReferenceMember();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.MappingResourceType#getMapFixedMember <em>Map Fixed Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Fixed Member</em>'.
	 * @see com.nokia.sdt.emf.component.MappingResourceType#getMapFixedMember()
	 * @see #getMappingResourceType()
	 * @generated
	 */
	EReference getMappingResourceType_MapFixedMember();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.MappingResourceType#getMapEnumMember <em>Map Enum Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Enum Member</em>'.
	 * @see com.nokia.sdt.emf.component.MappingResourceType#getMapEnumMember()
	 * @see #getMappingResourceType()
	 * @generated
	 */
	EReference getMappingResourceType_MapEnumMember();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.MappingResourceType#getMapIdentifierMember <em>Map Identifier Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Identifier Member</em>'.
	 * @see com.nokia.sdt.emf.component.MappingResourceType#getMapIdentifierMember()
	 * @see #getMappingResourceType()
	 * @generated
	 */
	EReference getMappingResourceType_MapIdentifierMember();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.MappingResourceType#getMapArrayMember <em>Map Array Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Array Member</em>'.
	 * @see com.nokia.sdt.emf.component.MappingResourceType#getMapArrayMember()
	 * @see #getMappingResourceType()
	 * @generated
	 */
	EReference getMappingResourceType_MapArrayMember();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.MappingResourceType#getMapResourceMember <em>Map Resource Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Resource Member</em>'.
	 * @see com.nokia.sdt.emf.component.MappingResourceType#getMapResourceMember()
	 * @see #getMappingResourceType()
	 * @generated
	 */
	EReference getMappingResourceType_MapResourceMember();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.MappingResourceType#getMapBitmaskMember <em>Map Bitmask Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Bitmask Member</em>'.
	 * @see com.nokia.sdt.emf.component.MappingResourceType#getMapBitmaskMember()
	 * @see #getMappingResourceType()
	 * @generated
	 */
	EReference getMappingResourceType_MapBitmaskMember();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.MappingResourceType#getMapMemberFromType <em>Map Member From Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Member From Type</em>'.
	 * @see com.nokia.sdt.emf.component.MappingResourceType#getMapMemberFromType()
	 * @see #getMappingResourceType()
	 * @generated
	 */
	EReference getMappingResourceType_MapMemberFromType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.MappingResourceType#getMapIntoProperty <em>Map Into Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Into Property</em>'.
	 * @see com.nokia.sdt.emf.component.MappingResourceType#getMapIntoProperty()
	 * @see #getMappingResourceType()
	 * @generated
	 */
	EReference getMappingResourceType_MapIntoProperty();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.MappingResourceType#getSelect <em>Select</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Select</em>'.
	 * @see com.nokia.sdt.emf.component.MappingResourceType#getSelect()
	 * @see #getMappingResourceType()
	 * @generated
	 */
	EReference getMappingResourceType_Select();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MappingResourceType#getHeaders <em>Headers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Headers</em>'.
	 * @see com.nokia.sdt.emf.component.MappingResourceType#getHeaders()
	 * @see #getMappingResourceType()
	 * @generated
	 */
	EAttribute getMappingResourceType_Headers();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MappingResourceType#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.nokia.sdt.emf.component.MappingResourceType#getId()
	 * @see #getMappingResourceType()
	 * @generated
	 */
	EAttribute getMappingResourceType_Id();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MappingResourceType#getStruct <em>Struct</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Struct</em>'.
	 * @see com.nokia.sdt.emf.component.MappingResourceType#getStruct()
	 * @see #getMappingResourceType()
	 * @generated
	 */
	EAttribute getMappingResourceType_Struct();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MappingSimpleType <em>Mapping Simple Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mapping Simple Type</em>'.
	 * @see com.nokia.sdt.emf.component.MappingSimpleType
	 * @generated
	 */
	EClass getMappingSimpleType();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapReferenceElementType <em>Map Reference Element Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Reference Element Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapReferenceElementType
	 * @generated
	 */
	EClass getMapReferenceElementType();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapReferenceMemberType <em>Map Reference Member Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Reference Member Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapReferenceMemberType
	 * @generated
	 */
	EClass getMapReferenceMemberType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapReferenceMemberType#getMember <em>Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Member</em>'.
	 * @see com.nokia.sdt.emf.component.MapReferenceMemberType#getMember()
	 * @see #getMapReferenceMemberType()
	 * @generated
	 */
	EAttribute getMapReferenceMemberType_Member();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapReferenceMemberType#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property</em>'.
	 * @see com.nokia.sdt.emf.component.MapReferenceMemberType#getProperty()
	 * @see #getMapReferenceMemberType()
	 * @generated
	 */
	EAttribute getMapReferenceMemberType_Property();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapReferenceMemberType#isSuppressDefault <em>Suppress Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Suppress Default</em>'.
	 * @see com.nokia.sdt.emf.component.MapReferenceMemberType#isSuppressDefault()
	 * @see #getMapReferenceMemberType()
	 * @generated
	 */
	EAttribute getMapReferenceMemberType_SuppressDefault();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapReferenceTypeType <em>Map Reference Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Reference Type Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapReferenceTypeType
	 * @generated
	 */
	EClass getMapReferenceTypeType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapReferenceTypeType#getTypeId <em>Type Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Id</em>'.
	 * @see com.nokia.sdt.emf.component.MapReferenceTypeType#getTypeId()
	 * @see #getMapReferenceTypeType()
	 * @generated
	 */
	EAttribute getMapReferenceTypeType_TypeId();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapResourceElementType <em>Map Resource Element Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Resource Element Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapResourceElementType
	 * @generated
	 */
	EClass getMapResourceElementType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapResourceElementType#getInstanceIdentifyingMember <em>Instance Identifying Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Instance Identifying Member</em>'.
	 * @see com.nokia.sdt.emf.component.MapResourceElementType#getInstanceIdentifyingMember()
	 * @see #getMapResourceElementType()
	 * @generated
	 */
	EAttribute getMapResourceElementType_InstanceIdentifyingMember();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapResourceMemberType <em>Map Resource Member Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Resource Member Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapResourceMemberType
	 * @generated
	 */
	EClass getMapResourceMemberType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapResourceMemberType#getMember <em>Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Member</em>'.
	 * @see com.nokia.sdt.emf.component.MapResourceMemberType#getMember()
	 * @see #getMapResourceMemberType()
	 * @generated
	 */
	EAttribute getMapResourceMemberType_Member();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapResourceMemberType#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property</em>'.
	 * @see com.nokia.sdt.emf.component.MapResourceMemberType#getProperty()
	 * @see #getMapResourceMemberType()
	 * @generated
	 */
	EAttribute getMapResourceMemberType_Property();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapResourceMemberType#isSuppressDefault <em>Suppress Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Suppress Default</em>'.
	 * @see com.nokia.sdt.emf.component.MapResourceMemberType#isSuppressDefault()
	 * @see #getMapResourceMemberType()
	 * @generated
	 */
	EAttribute getMapResourceMemberType_SuppressDefault();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapResourceType <em>Map Resource Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Resource Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapResourceType
	 * @generated
	 */
	EClass getMapResourceType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapResourceType#getBaseName <em>Base Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Base Name</em>'.
	 * @see com.nokia.sdt.emf.component.MapResourceType#getBaseName()
	 * @see #getMapResourceType()
	 * @generated
	 */
	EAttribute getMapResourceType_BaseName();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapResourceType#getRssFile <em>Rss File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rss File</em>'.
	 * @see com.nokia.sdt.emf.component.MapResourceType#getRssFile()
	 * @see #getMapResourceType()
	 * @generated
	 */
	EAttribute getMapResourceType_RssFile();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapResourceType#getStandalone <em>Standalone</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Standalone</em>'.
	 * @see com.nokia.sdt.emf.component.MapResourceType#getStandalone()
	 * @see #getMapResourceType()
	 * @generated
	 */
	EAttribute getMapResourceType_Standalone();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapResourceType#isUnnamed <em>Unnamed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unnamed</em>'.
	 * @see com.nokia.sdt.emf.component.MapResourceType#isUnnamed()
	 * @see #getMapResourceType()
	 * @generated
	 */
	EAttribute getMapResourceType_Unnamed();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapResourceTypeType <em>Map Resource Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Resource Type Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapResourceTypeType
	 * @generated
	 */
	EClass getMapResourceTypeType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapResourceTypeType#getTypeId <em>Type Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Id</em>'.
	 * @see com.nokia.sdt.emf.component.MapResourceTypeType#getTypeId()
	 * @see #getMapResourceTypeType()
	 * @generated
	 */
	EAttribute getMapResourceTypeType_TypeId();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapSimpleElementType <em>Map Simple Element Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Simple Element Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapSimpleElementType
	 * @generated
	 */
	EClass getMapSimpleElementType();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapSimpleMemberType <em>Map Simple Member Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Simple Member Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapSimpleMemberType
	 * @generated
	 */
	EClass getMapSimpleMemberType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapSimpleMemberType#getMember <em>Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Member</em>'.
	 * @see com.nokia.sdt.emf.component.MapSimpleMemberType#getMember()
	 * @see #getMapSimpleMemberType()
	 * @generated
	 */
	EAttribute getMapSimpleMemberType_Member();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapSimpleMemberType#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property</em>'.
	 * @see com.nokia.sdt.emf.component.MapSimpleMemberType#getProperty()
	 * @see #getMapSimpleMemberType()
	 * @generated
	 */
	EAttribute getMapSimpleMemberType_Property();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapSimpleMemberType#isSuppressDefault <em>Suppress Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Suppress Default</em>'.
	 * @see com.nokia.sdt.emf.component.MapSimpleMemberType#isSuppressDefault()
	 * @see #getMapSimpleMemberType()
	 * @generated
	 */
	EAttribute getMapSimpleMemberType_SuppressDefault();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.MapSimpleTypeType <em>Map Simple Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Simple Type Type</em>'.
	 * @see com.nokia.sdt.emf.component.MapSimpleTypeType
	 * @generated
	 */
	EClass getMapSimpleTypeType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.MapSimpleTypeType#getTypeId <em>Type Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Id</em>'.
	 * @see com.nokia.sdt.emf.component.MapSimpleTypeType#getTypeId()
	 * @see #getMapSimpleTypeType()
	 * @generated
	 */
	EAttribute getMapSimpleTypeType_TypeId();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.PropertiesType <em>Properties Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Properties Type</em>'.
	 * @see com.nokia.sdt.emf.component.PropertiesType
	 * @generated
	 */
	EClass getPropertiesType();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.sdt.emf.component.PropertiesType#getAbstractPropertyGroup <em>Abstract Property Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Abstract Property Group</em>'.
	 * @see com.nokia.sdt.emf.component.PropertiesType#getAbstractPropertyGroup()
	 * @see #getPropertiesType()
	 * @generated
	 */
	EAttribute getPropertiesType_AbstractPropertyGroup();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.PropertiesType#getAbstractProperty <em>Abstract Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Abstract Property</em>'.
	 * @see com.nokia.sdt.emf.component.PropertiesType#getAbstractProperty()
	 * @see #getPropertiesType()
	 * @generated
	 */
	EReference getPropertiesType_AbstractProperty();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.PropertyOverridesType <em>Property Overrides Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Overrides Type</em>'.
	 * @see com.nokia.sdt.emf.component.PropertyOverridesType
	 * @generated
	 */
	EClass getPropertyOverridesType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.PropertyOverridesType#getPropertyOverride <em>Property Override</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Property Override</em>'.
	 * @see com.nokia.sdt.emf.component.PropertyOverridesType#getPropertyOverride()
	 * @see #getPropertyOverridesType()
	 * @generated
	 */
	EReference getPropertyOverridesType_PropertyOverride();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.PropertyOverrideType <em>Property Override Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Override Type</em>'.
	 * @see com.nokia.sdt.emf.component.PropertyOverrideType
	 * @generated
	 */
	EClass getPropertyOverrideType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.PropertyOverrideType#getCategory <em>Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Category</em>'.
	 * @see com.nokia.sdt.emf.component.PropertyOverrideType#getCategory()
	 * @see #getPropertyOverrideType()
	 * @generated
	 */
	EAttribute getPropertyOverrideType_Category();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.PropertyOverrideType#getDefault <em>Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default</em>'.
	 * @see com.nokia.sdt.emf.component.PropertyOverrideType#getDefault()
	 * @see #getPropertyOverrideType()
	 * @generated
	 */
	EAttribute getPropertyOverrideType_Default();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.PropertyOverrideType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.nokia.sdt.emf.component.PropertyOverrideType#getName()
	 * @see #getPropertyOverrideType()
	 * @generated
	 */
	EAttribute getPropertyOverrideType_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.PropertyOverrideType#isReadOnly <em>Read Only</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Read Only</em>'.
	 * @see com.nokia.sdt.emf.component.PropertyOverrideType#isReadOnly()
	 * @see #getPropertyOverrideType()
	 * @generated
	 */
	EAttribute getPropertyOverrideType_ReadOnly();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.ScriptType <em>Script Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Script Type</em>'.
	 * @see com.nokia.sdt.emf.component.ScriptType
	 * @generated
	 */
	EClass getScriptType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ScriptType#getFile <em>File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File</em>'.
	 * @see com.nokia.sdt.emf.component.ScriptType#getFile()
	 * @see #getScriptType()
	 * @generated
	 */
	EAttribute getScriptType_File();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.ScriptType#getPrototype <em>Prototype</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Prototype</em>'.
	 * @see com.nokia.sdt.emf.component.ScriptType#getPrototype()
	 * @see #getScriptType()
	 * @generated
	 */
	EAttribute getScriptType_Prototype();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.SelectType <em>Select Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Select Type</em>'.
	 * @see com.nokia.sdt.emf.component.SelectType
	 * @generated
	 */
	EClass getSelectType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.SelectType#getChoice <em>Choice</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Choice</em>'.
	 * @see com.nokia.sdt.emf.component.SelectType#getChoice()
	 * @see #getSelectType()
	 * @generated
	 */
	EReference getSelectType_Choice();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.SelectType#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Attribute</em>'.
	 * @see com.nokia.sdt.emf.component.SelectType#getAttribute()
	 * @see #getSelectType()
	 * @generated
	 */
	EAttribute getSelectType_Attribute();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.SelectType#getIsComponentInstanceOf <em>Is Component Instance Of</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Component Instance Of</em>'.
	 * @see com.nokia.sdt.emf.component.SelectType#getIsComponentInstanceOf()
	 * @see #getSelectType()
	 * @generated
	 */
	EAttribute getSelectType_IsComponentInstanceOf();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.SelectType#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property</em>'.
	 * @see com.nokia.sdt.emf.component.SelectType#getProperty()
	 * @see #getSelectType()
	 * @generated
	 */
	EAttribute getSelectType_Property();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.SelectType#getPropertyExists <em>Property Exists</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property Exists</em>'.
	 * @see com.nokia.sdt.emf.component.SelectType#getPropertyExists()
	 * @see #getSelectType()
	 * @generated
	 */
	EAttribute getSelectType_PropertyExists();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.SimplePropertyType <em>Simple Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simple Property Type</em>'.
	 * @see com.nokia.sdt.emf.component.SimplePropertyType
	 * @generated
	 */
	EClass getSimplePropertyType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.SimplePropertyType#getDefault <em>Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default</em>'.
	 * @see com.nokia.sdt.emf.component.SimplePropertyType#getDefault()
	 * @see #getSimplePropertyType()
	 * @generated
	 */
	EAttribute getSimplePropertyType_Default();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.SimplePropertyType#getExtendWithEnum <em>Extend With Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Extend With Enum</em>'.
	 * @see com.nokia.sdt.emf.component.SimplePropertyType#getExtendWithEnum()
	 * @see #getSimplePropertyType()
	 * @generated
	 */
	EAttribute getSimplePropertyType_ExtendWithEnum();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.SimplePropertyType#getMaxValue <em>Max Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Value</em>'.
	 * @see com.nokia.sdt.emf.component.SimplePropertyType#getMaxValue()
	 * @see #getSimplePropertyType()
	 * @generated
	 */
	EAttribute getSimplePropertyType_MaxValue();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.SimplePropertyType#getMinValue <em>Min Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min Value</em>'.
	 * @see com.nokia.sdt.emf.component.SimplePropertyType#getMinValue()
	 * @see #getSimplePropertyType()
	 * @generated
	 */
	EAttribute getSimplePropertyType_MinValue();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.SimplePropertyType#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.nokia.sdt.emf.component.SimplePropertyType#getType()
	 * @see #getSimplePropertyType()
	 * @generated
	 */
	EAttribute getSimplePropertyType_Type();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.SourceGenType <em>Source Gen Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Source Gen Type</em>'.
	 * @see com.nokia.sdt.emf.component.SourceGenType
	 * @generated
	 */
	EClass getSourceGenType();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.sdt.emf.component.SourceGenType#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.nokia.sdt.emf.component.SourceGenType#getGroup()
	 * @see #getSourceGenType()
	 * @generated
	 */
	EAttribute getSourceGenType_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.SourceGenType#getDefineLocation <em>Define Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Define Location</em>'.
	 * @see com.nokia.sdt.emf.component.SourceGenType#getDefineLocation()
	 * @see #getSourceGenType()
	 * @generated
	 */
	EReference getSourceGenType_DefineLocation();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.SourceGenType#getTemplate <em>Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Template</em>'.
	 * @see com.nokia.sdt.emf.component.SourceGenType#getTemplate()
	 * @see #getSourceGenType()
	 * @generated
	 */
	EReference getSourceGenType_Template();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.SourceGenType#getTemplateGroup <em>Template Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Template Group</em>'.
	 * @see com.nokia.sdt.emf.component.SourceGenType#getTemplateGroup()
	 * @see #getSourceGenType()
	 * @generated
	 */
	EReference getSourceGenType_TemplateGroup();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.SourceGenType#getUseTemplate <em>Use Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Use Template</em>'.
	 * @see com.nokia.sdt.emf.component.SourceGenType#getUseTemplate()
	 * @see #getSourceGenType()
	 * @generated
	 */
	EReference getSourceGenType_UseTemplate();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.SourceGenType#getUseTemplateGroup <em>Use Template Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Use Template Group</em>'.
	 * @see com.nokia.sdt.emf.component.SourceGenType#getUseTemplateGroup()
	 * @see #getSourceGenType()
	 * @generated
	 */
	EReference getSourceGenType_UseTemplateGroup();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.SourceGenType#getInline <em>Inline</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Inline</em>'.
	 * @see com.nokia.sdt.emf.component.SourceGenType#getInline()
	 * @see #getSourceGenType()
	 * @generated
	 */
	EReference getSourceGenType_Inline();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.SourceGenType#getScript <em>Script</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Script</em>'.
	 * @see com.nokia.sdt.emf.component.SourceGenType#getScript()
	 * @see #getSourceGenType()
	 * @generated
	 */
	EReference getSourceGenType_Script();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.SourceGenType#getDefineMacro <em>Define Macro</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Define Macro</em>'.
	 * @see com.nokia.sdt.emf.component.SourceGenType#getDefineMacro()
	 * @see #getSourceGenType()
	 * @generated
	 */
	EReference getSourceGenType_DefineMacro();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.SourceGenType#getExpandMacro <em>Expand Macro</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Expand Macro</em>'.
	 * @see com.nokia.sdt.emf.component.SourceGenType#getExpandMacro()
	 * @see #getSourceGenType()
	 * @generated
	 */
	EReference getSourceGenType_ExpandMacro();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.SourceGenType#isDebug <em>Debug</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Debug</em>'.
	 * @see com.nokia.sdt.emf.component.SourceGenType#isDebug()
	 * @see #getSourceGenType()
	 * @generated
	 */
	EAttribute getSourceGenType_Debug();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.SourceGenType#getForms <em>Forms</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Forms</em>'.
	 * @see com.nokia.sdt.emf.component.SourceGenType#getForms()
	 * @see #getSourceGenType()
	 * @generated
	 */
	EAttribute getSourceGenType_Forms();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.SourceMappingType <em>Source Mapping Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Source Mapping Type</em>'.
	 * @see com.nokia.sdt.emf.component.SourceMappingType
	 * @generated
	 */
	EClass getSourceMappingType();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.sdt.emf.component.SourceMappingType#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.nokia.sdt.emf.component.SourceMappingType#getGroup()
	 * @see #getSourceMappingType()
	 * @generated
	 */
	EAttribute getSourceMappingType_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.SourceMappingType#getMapResource <em>Map Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Resource</em>'.
	 * @see com.nokia.sdt.emf.component.SourceMappingType#getMapResource()
	 * @see #getSourceMappingType()
	 * @generated
	 */
	EReference getSourceMappingType_MapResource();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.SourceMappingType#getSelect <em>Select</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Select</em>'.
	 * @see com.nokia.sdt.emf.component.SourceMappingType#getSelect()
	 * @see #getSourceMappingType()
	 * @generated
	 */
	EReference getSourceMappingType_Select();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.SourceTypeMappingType <em>Source Type Mapping Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Source Type Mapping Type</em>'.
	 * @see com.nokia.sdt.emf.component.SourceTypeMappingType
	 * @generated
	 */
	EClass getSourceTypeMappingType();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.sdt.emf.component.SourceTypeMappingType#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.nokia.sdt.emf.component.SourceTypeMappingType#getGroup()
	 * @see #getSourceTypeMappingType()
	 * @generated
	 */
	EAttribute getSourceTypeMappingType_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.SourceTypeMappingType#getMapResourceType <em>Map Resource Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Resource Type</em>'.
	 * @see com.nokia.sdt.emf.component.SourceTypeMappingType#getMapResourceType()
	 * @see #getSourceTypeMappingType()
	 * @generated
	 */
	EReference getSourceTypeMappingType_MapResourceType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.SourceTypeMappingType#getMapEnumType <em>Map Enum Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Enum Type</em>'.
	 * @see com.nokia.sdt.emf.component.SourceTypeMappingType#getMapEnumType()
	 * @see #getSourceTypeMappingType()
	 * @generated
	 */
	EReference getSourceTypeMappingType_MapEnumType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.SourceTypeMappingType#getMapSimpleType <em>Map Simple Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Simple Type</em>'.
	 * @see com.nokia.sdt.emf.component.SourceTypeMappingType#getMapSimpleType()
	 * @see #getSourceTypeMappingType()
	 * @generated
	 */
	EReference getSourceTypeMappingType_MapSimpleType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.SourceTypeMappingType#getMapFixedType <em>Map Fixed Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Fixed Type</em>'.
	 * @see com.nokia.sdt.emf.component.SourceTypeMappingType#getMapFixedType()
	 * @see #getSourceTypeMappingType()
	 * @generated
	 */
	EReference getSourceTypeMappingType_MapFixedType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.SourceTypeMappingType#getMapBitmaskType <em>Map Bitmask Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Bitmask Type</em>'.
	 * @see com.nokia.sdt.emf.component.SourceTypeMappingType#getMapBitmaskType()
	 * @see #getSourceTypeMappingType()
	 * @generated
	 */
	EReference getSourceTypeMappingType_MapBitmaskType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.SourceTypeMappingType#getMapIdentifierType <em>Map Identifier Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Identifier Type</em>'.
	 * @see com.nokia.sdt.emf.component.SourceTypeMappingType#getMapIdentifierType()
	 * @see #getSourceTypeMappingType()
	 * @generated
	 */
	EReference getSourceTypeMappingType_MapIdentifierType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.SourceTypeMappingType#getMapReferenceType <em>Map Reference Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Reference Type</em>'.
	 * @see com.nokia.sdt.emf.component.SourceTypeMappingType#getMapReferenceType()
	 * @see #getSourceTypeMappingType()
	 * @generated
	 */
	EReference getSourceTypeMappingType_MapReferenceType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.SourceTypeMappingType#getMapArrayType <em>Map Array Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Map Array Type</em>'.
	 * @see com.nokia.sdt.emf.component.SourceTypeMappingType#getMapArrayType()
	 * @see #getSourceTypeMappingType()
	 * @generated
	 */
	EReference getSourceTypeMappingType_MapArrayType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.SourceTypeMappingType#getSelect <em>Select</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Select</em>'.
	 * @see com.nokia.sdt.emf.component.SourceTypeMappingType#getSelect()
	 * @see #getSourceTypeMappingType()
	 * @generated
	 */
	EReference getSourceTypeMappingType_Select();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.SymbianType <em>Symbian Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Symbian Type</em>'.
	 * @see com.nokia.sdt.emf.component.SymbianType
	 * @generated
	 */
	EClass getSymbianType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.SymbianType#getClassHelpTopic <em>Class Help Topic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Help Topic</em>'.
	 * @see com.nokia.sdt.emf.component.SymbianType#getClassHelpTopic()
	 * @see #getSymbianType()
	 * @generated
	 */
	EAttribute getSymbianType_ClassHelpTopic();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.SymbianType#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see com.nokia.sdt.emf.component.SymbianType#getClassName()
	 * @see #getSymbianType()
	 * @generated
	 */
	EAttribute getSymbianType_ClassName();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.SymbianType#getMaxSDKVersion <em>Max SDK Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max SDK Version</em>'.
	 * @see com.nokia.sdt.emf.component.SymbianType#getMaxSDKVersion()
	 * @see #getSymbianType()
	 * @generated
	 */
	EAttribute getSymbianType_MaxSDKVersion();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.SymbianType#getMinSDKVersion <em>Min SDK Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min SDK Version</em>'.
	 * @see com.nokia.sdt.emf.component.SymbianType#getMinSDKVersion()
	 * @see #getSymbianType()
	 * @generated
	 */
	EAttribute getSymbianType_MinSDKVersion();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.SymbianType#getResourceHelpTopic <em>Resource Help Topic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Resource Help Topic</em>'.
	 * @see com.nokia.sdt.emf.component.SymbianType#getResourceHelpTopic()
	 * @see #getSymbianType()
	 * @generated
	 */
	EAttribute getSymbianType_ResourceHelpTopic();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.SymbianType#getResourceType <em>Resource Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Resource Type</em>'.
	 * @see com.nokia.sdt.emf.component.SymbianType#getResourceType()
	 * @see #getSymbianType()
	 * @generated
	 */
	EAttribute getSymbianType_ResourceType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.SymbianType#getSdkName <em>Sdk Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sdk Name</em>'.
	 * @see com.nokia.sdt.emf.component.SymbianType#getSdkName()
	 * @see #getSymbianType()
	 * @generated
	 */
	EAttribute getSymbianType_SdkName();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.TemplateGroupType <em>Template Group Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Template Group Type</em>'.
	 * @see com.nokia.sdt.emf.component.TemplateGroupType
	 * @generated
	 */
	EClass getTemplateGroupType();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.sdt.emf.component.TemplateGroupType#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.nokia.sdt.emf.component.TemplateGroupType#getGroup()
	 * @see #getTemplateGroupType()
	 * @generated
	 */
	EAttribute getTemplateGroupType_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.TemplateGroupType#getDefineLocation <em>Define Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Define Location</em>'.
	 * @see com.nokia.sdt.emf.component.TemplateGroupType#getDefineLocation()
	 * @see #getTemplateGroupType()
	 * @generated
	 */
	EReference getTemplateGroupType_DefineLocation();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.TemplateGroupType#getTemplate <em>Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Template</em>'.
	 * @see com.nokia.sdt.emf.component.TemplateGroupType#getTemplate()
	 * @see #getTemplateGroupType()
	 * @generated
	 */
	EReference getTemplateGroupType_Template();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.TemplateGroupType#getInline <em>Inline</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Inline</em>'.
	 * @see com.nokia.sdt.emf.component.TemplateGroupType#getInline()
	 * @see #getTemplateGroupType()
	 * @generated
	 */
	EReference getTemplateGroupType_Inline();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.TemplateGroupType#getUseTemplate <em>Use Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Use Template</em>'.
	 * @see com.nokia.sdt.emf.component.TemplateGroupType#getUseTemplate()
	 * @see #getTemplateGroupType()
	 * @generated
	 */
	EReference getTemplateGroupType_UseTemplate();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.TemplateGroupType#getUseTemplateGroup <em>Use Template Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Use Template Group</em>'.
	 * @see com.nokia.sdt.emf.component.TemplateGroupType#getUseTemplateGroup()
	 * @see #getTemplateGroupType()
	 * @generated
	 */
	EReference getTemplateGroupType_UseTemplateGroup();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.TemplateGroupType#getExpandMacro <em>Expand Macro</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Expand Macro</em>'.
	 * @see com.nokia.sdt.emf.component.TemplateGroupType#getExpandMacro()
	 * @see #getTemplateGroupType()
	 * @generated
	 */
	EReference getTemplateGroupType_ExpandMacro();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.TemplateGroupType#getForm <em>Form</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Form</em>'.
	 * @see com.nokia.sdt.emf.component.TemplateGroupType#getForm()
	 * @see #getTemplateGroupType()
	 * @generated
	 */
	EAttribute getTemplateGroupType_Form();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.TemplateGroupType#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.nokia.sdt.emf.component.TemplateGroupType#getId()
	 * @see #getTemplateGroupType()
	 * @generated
	 */
	EAttribute getTemplateGroupType_Id();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.TemplateGroupType#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Location</em>'.
	 * @see com.nokia.sdt.emf.component.TemplateGroupType#getLocation()
	 * @see #getTemplateGroupType()
	 * @generated
	 */
	EAttribute getTemplateGroupType_Location();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.TemplateGroupType#getMode <em>Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mode</em>'.
	 * @see com.nokia.sdt.emf.component.TemplateGroupType#getMode()
	 * @see #getTemplateGroupType()
	 * @generated
	 */
	EAttribute getTemplateGroupType_Mode();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.TemplateGroupType#getPhase <em>Phase</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Phase</em>'.
	 * @see com.nokia.sdt.emf.component.TemplateGroupType#getPhase()
	 * @see #getTemplateGroupType()
	 * @generated
	 */
	EAttribute getTemplateGroupType_Phase();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.TemplateType <em>Template Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Template Type</em>'.
	 * @see com.nokia.sdt.emf.component.TemplateType
	 * @generated
	 */
	EClass getTemplateType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.TemplateType#getForm <em>Form</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Form</em>'.
	 * @see com.nokia.sdt.emf.component.TemplateType#getForm()
	 * @see #getTemplateType()
	 * @generated
	 */
	EAttribute getTemplateType_Form();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.TemplateType#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.nokia.sdt.emf.component.TemplateType#getId()
	 * @see #getTemplateType()
	 * @generated
	 */
	EAttribute getTemplateType_Id();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.TemplateType#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Location</em>'.
	 * @see com.nokia.sdt.emf.component.TemplateType#getLocation()
	 * @see #getTemplateType()
	 * @generated
	 */
	EAttribute getTemplateType_Location();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.TemplateType#getMode <em>Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mode</em>'.
	 * @see com.nokia.sdt.emf.component.TemplateType#getMode()
	 * @see #getTemplateType()
	 * @generated
	 */
	EAttribute getTemplateType_Mode();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.TemplateType#getPhase <em>Phase</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Phase</em>'.
	 * @see com.nokia.sdt.emf.component.TemplateType#getPhase()
	 * @see #getTemplateType()
	 * @generated
	 */
	EAttribute getTemplateType_Phase();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.TwoWayMappingType <em>Two Way Mapping Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Two Way Mapping Type</em>'.
	 * @see com.nokia.sdt.emf.component.TwoWayMappingType
	 * @generated
	 */
	EClass getTwoWayMappingType();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.UseTemplateGroupType <em>Use Template Group Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Use Template Group Type</em>'.
	 * @see com.nokia.sdt.emf.component.UseTemplateGroupType
	 * @generated
	 */
	EClass getUseTemplateGroupType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.component.UseTemplateGroupType#getUseTemplate <em>Use Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Use Template</em>'.
	 * @see com.nokia.sdt.emf.component.UseTemplateGroupType#getUseTemplate()
	 * @see #getUseTemplateGroupType()
	 * @generated
	 */
	EReference getUseTemplateGroupType_UseTemplate();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.UseTemplateGroupType#getIds <em>Ids</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ids</em>'.
	 * @see com.nokia.sdt.emf.component.UseTemplateGroupType#getIds()
	 * @see #getUseTemplateGroupType()
	 * @generated
	 */
	EAttribute getUseTemplateGroupType_Ids();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.component.UseTemplateType <em>Use Template Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Use Template Type</em>'.
	 * @see com.nokia.sdt.emf.component.UseTemplateType
	 * @generated
	 */
	EClass getUseTemplateType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.component.UseTemplateType#getIds <em>Ids</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ids</em>'.
	 * @see com.nokia.sdt.emf.component.UseTemplateType#getIds()
	 * @see #getUseTemplateType()
	 * @generated
	 */
	EAttribute getUseTemplateType_Ids();

	/**
	 * Returns the meta object for enum '{@link com.nokia.sdt.emf.component.PropertyDataType <em>Property Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Property Data Type</em>'.
	 * @see com.nokia.sdt.emf.component.PropertyDataType
	 * @generated
	 */
	EEnum getPropertyDataType();

	/**
	 * Returns the meta object for enum '{@link com.nokia.sdt.emf.component.ReferenceScopeType <em>Reference Scope Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Reference Scope Type</em>'.
	 * @see com.nokia.sdt.emf.component.ReferenceScopeType
	 * @generated
	 */
	EEnum getReferenceScopeType();

	/**
	 * Returns the meta object for enum '{@link com.nokia.sdt.emf.component.StandaloneType <em>Standalone Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Standalone Type</em>'.
	 * @see com.nokia.sdt.emf.component.StandaloneType
	 * @generated
	 */
	EEnum getStandaloneType();

	/**
	 * Returns the meta object for data type '{@link java.util.List <em>List Of Strings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>List Of Strings</em>'.
	 * @see java.util.List
	 * @model instanceClass="java.util.List"
	 *        extendedMetaData="name='listOfStrings' itemType='http://www.eclipse.org/emf/2003/XMLType#string'" 
	 * @generated
	 */
	EDataType getListOfStrings();

	/**
	 * Returns the meta object for data type '{@link com.nokia.sdt.emf.component.PropertyDataType <em>Property Data Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Property Data Type Object</em>'.
	 * @see com.nokia.sdt.emf.component.PropertyDataType
	 * @model instanceClass="com.nokia.sdt.emf.component.PropertyDataType"
	 *        extendedMetaData="name='propertyDataType:Object' baseType='propertyDataType'" 
	 * @generated
	 */
	EDataType getPropertyDataTypeObject();

	/**
	 * Returns the meta object for data type '{@link com.nokia.sdt.emf.component.ReferenceScopeType <em>Reference Scope Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Reference Scope Type Object</em>'.
	 * @see com.nokia.sdt.emf.component.ReferenceScopeType
	 * @model instanceClass="com.nokia.sdt.emf.component.ReferenceScopeType"
	 *        extendedMetaData="name='referenceScopeType:Object' baseType='referenceScopeType'" 
	 * @generated
	 */
	EDataType getReferenceScopeTypeObject();

	/**
	 * Returns the meta object for data type '{@link com.nokia.sdt.emf.component.StandaloneType <em>Standalone Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Standalone Type Object</em>'.
	 * @see com.nokia.sdt.emf.component.StandaloneType
	 * @model instanceClass="com.nokia.sdt.emf.component.StandaloneType"
	 *        extendedMetaData="name='standaloneType:Object' baseType='standaloneType'" 
	 * @generated
	 */
	EDataType getStandaloneTypeObject();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ComponentFactory getComponentFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.AbstractPropertyTypeImpl <em>Abstract Property Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.AbstractPropertyTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getAbstractPropertyType()
		 * @generated
		 */
		EClass ABSTRACT_PROPERTY_TYPE = eINSTANCE.getAbstractPropertyType();

		/**
		 * The meta object literal for the '<em><b>Category</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_PROPERTY_TYPE__CATEGORY = eINSTANCE.getAbstractPropertyType_Category();

		/**
		 * The meta object literal for the '<em><b>Description Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_PROPERTY_TYPE__DESCRIPTION_KEY = eINSTANCE.getAbstractPropertyType_DescriptionKey();

		/**
		 * The meta object literal for the '<em><b>Display Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_PROPERTY_TYPE__DISPLAY_NAME = eINSTANCE.getAbstractPropertyType_DisplayName();

		/**
		 * The meta object literal for the '<em><b>Editor Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_PROPERTY_TYPE__EDITOR_CLASS = eINSTANCE.getAbstractPropertyType_EditorClass();

		/**
		 * The meta object literal for the '<em><b>Help Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_PROPERTY_TYPE__HELP_KEY = eINSTANCE.getAbstractPropertyType_HelpKey();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_PROPERTY_TYPE__NAME = eINSTANCE.getAbstractPropertyType_Name();

		/**
		 * The meta object literal for the '<em><b>Read Only</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_PROPERTY_TYPE__READ_ONLY = eINSTANCE.getAbstractPropertyType_ReadOnly();

		/**
		 * The meta object literal for the '<em><b>Resettable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_PROPERTY_TYPE__RESETTABLE = eINSTANCE.getAbstractPropertyType_Resettable();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.ArrayPropertyTypeImpl <em>Array Property Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.ArrayPropertyTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getArrayPropertyType()
		 * @generated
		 */
		EClass ARRAY_PROPERTY_TYPE = eINSTANCE.getArrayPropertyType();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARRAY_PROPERTY_TYPE__TYPE = eINSTANCE.getArrayPropertyType_Type();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.AttributesTypeImpl <em>Attributes Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.AttributesTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getAttributesType()
		 * @generated
		 */
		EClass ATTRIBUTES_TYPE = eINSTANCE.getAttributesType();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTES_TYPE__ATTRIBUTE = eINSTANCE.getAttributesType_Attribute();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.AttributeTypeImpl <em>Attribute Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.AttributeTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getAttributeType()
		 * @generated
		 */
		EClass ATTRIBUTE_TYPE = eINSTANCE.getAttributeType();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE_TYPE__VALUE = eINSTANCE.getAttributeType_Value();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE_TYPE__KEY = eINSTANCE.getAttributeType_Key();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.ChoiceTypeImpl <em>Choice Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.ChoiceTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getChoiceType()
		 * @generated
		 */
		EClass CHOICE_TYPE = eINSTANCE.getChoiceType();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHOICE_TYPE__GROUP = eINSTANCE.getChoiceType_Group();

		/**
		 * The meta object literal for the '<em><b>Two Way Mapping Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHOICE_TYPE__TWO_WAY_MAPPING_GROUP = eINSTANCE.getChoiceType_TwoWayMappingGroup();

		/**
		 * The meta object literal for the '<em><b>Two Way Mapping</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHOICE_TYPE__TWO_WAY_MAPPING = eINSTANCE.getChoiceType_TwoWayMapping();

		/**
		 * The meta object literal for the '<em><b>Map Resource</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHOICE_TYPE__MAP_RESOURCE = eINSTANCE.getChoiceType_MapResource();

		/**
		 * The meta object literal for the '<em><b>Select</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHOICE_TYPE__SELECT = eINSTANCE.getChoiceType_Select();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHOICE_TYPE__VALUE = eINSTANCE.getChoiceType_Value();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.CodeTypeImpl <em>Code Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.CodeTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getCodeType()
		 * @generated
		 */
		EClass CODE_TYPE = eINSTANCE.getCodeType();

		/**
		 * The meta object literal for the '<em><b>Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CODE_TYPE__CLASS = eINSTANCE.getCodeType_Class();

		/**
		 * The meta object literal for the '<em><b>Plugin</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CODE_TYPE__PLUGIN = eINSTANCE.getCodeType_Plugin();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.ComponentDefinitionTypeImpl <em>Definition Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.ComponentDefinitionTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getComponentDefinitionType()
		 * @generated
		 */
		EClass COMPONENT_DEFINITION_TYPE = eINSTANCE.getComponentDefinitionType();

		/**
		 * The meta object literal for the '<em><b>Compound Property Declaration</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT_DEFINITION_TYPE__COMPOUND_PROPERTY_DECLARATION = eINSTANCE.getComponentDefinitionType_CompoundPropertyDeclaration();

		/**
		 * The meta object literal for the '<em><b>Enum Property Declaration</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT_DEFINITION_TYPE__ENUM_PROPERTY_DECLARATION = eINSTANCE.getComponentDefinitionType_EnumPropertyDeclaration();

		/**
		 * The meta object literal for the '<em><b>Component</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT_DEFINITION_TYPE__COMPONENT = eINSTANCE.getComponentDefinitionType_Component();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.ComponentReferencePropertyTypeImpl <em>Reference Property Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.ComponentReferencePropertyTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getComponentReferencePropertyType()
		 * @generated
		 */
		EClass COMPONENT_REFERENCE_PROPERTY_TYPE = eINSTANCE.getComponentReferencePropertyType();

		/**
		 * The meta object literal for the '<em><b>Constraint</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT_REFERENCE_PROPERTY_TYPE__CONSTRAINT = eINSTANCE.getComponentReferencePropertyType_Constraint();

		/**
		 * The meta object literal for the '<em><b>Creation Keys</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT_REFERENCE_PROPERTY_TYPE__CREATION_KEYS = eINSTANCE.getComponentReferencePropertyType_CreationKeys();

		/**
		 * The meta object literal for the '<em><b>Promote Reference Properties</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT_REFERENCE_PROPERTY_TYPE__PROMOTE_REFERENCE_PROPERTIES = eINSTANCE.getComponentReferencePropertyType_PromoteReferenceProperties();

		/**
		 * The meta object literal for the '<em><b>Scope</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT_REFERENCE_PROPERTY_TYPE__SCOPE = eINSTANCE.getComponentReferencePropertyType_Scope();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.ComponentTypeImpl <em>Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.ComponentTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getComponentType()
		 * @generated
		 */
		EClass COMPONENT_TYPE = eINSTANCE.getComponentType();

		/**
		 * The meta object literal for the '<em><b>Documentation</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT_TYPE__DOCUMENTATION = eINSTANCE.getComponentType_Documentation();

		/**
		 * The meta object literal for the '<em><b>Symbian</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT_TYPE__SYMBIAN = eINSTANCE.getComponentType_Symbian();

		/**
		 * The meta object literal for the '<em><b>Designer Images</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT_TYPE__DESIGNER_IMAGES = eINSTANCE.getComponentType_DesignerImages();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT_TYPE__ATTRIBUTES = eINSTANCE.getComponentType_Attributes();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT_TYPE__PROPERTIES = eINSTANCE.getComponentType_Properties();

		/**
		 * The meta object literal for the '<em><b>Extension Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT_TYPE__EXTENSION_PROPERTIES = eINSTANCE.getComponentType_ExtensionProperties();

		/**
		 * The meta object literal for the '<em><b>Property Overrides</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT_TYPE__PROPERTY_OVERRIDES = eINSTANCE.getComponentType_PropertyOverrides();

		/**
		 * The meta object literal for the '<em><b>Events</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT_TYPE__EVENTS = eINSTANCE.getComponentType_Events();

		/**
		 * The meta object literal for the '<em><b>Source Gen</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT_TYPE__SOURCE_GEN = eINSTANCE.getComponentType_SourceGen();

		/**
		 * The meta object literal for the '<em><b>Source Mapping</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT_TYPE__SOURCE_MAPPING = eINSTANCE.getComponentType_SourceMapping();

		/**
		 * The meta object literal for the '<em><b>Implementations</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT_TYPE__IMPLEMENTATIONS = eINSTANCE.getComponentType_Implementations();

		/**
		 * The meta object literal for the '<em><b>Abstract</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT_TYPE__ABSTRACT = eINSTANCE.getComponentType_Abstract();

		/**
		 * The meta object literal for the '<em><b>Base Component</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT_TYPE__BASE_COMPONENT = eINSTANCE.getComponentType_BaseComponent();

		/**
		 * The meta object literal for the '<em><b>Category</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT_TYPE__CATEGORY = eINSTANCE.getComponentType_Category();

		/**
		 * The meta object literal for the '<em><b>Friendly Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT_TYPE__FRIENDLY_NAME = eINSTANCE.getComponentType_FriendlyName();

		/**
		 * The meta object literal for the '<em><b>Instance Name Root</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT_TYPE__INSTANCE_NAME_ROOT = eINSTANCE.getComponentType_InstanceNameRoot();

		/**
		 * The meta object literal for the '<em><b>Qualified Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT_TYPE__QUALIFIED_NAME = eINSTANCE.getComponentType_QualifiedName();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT_TYPE__VERSION = eINSTANCE.getComponentType_Version();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.CompoundPropertyDeclarationTypeImpl <em>Compound Property Declaration Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.CompoundPropertyDeclarationTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getCompoundPropertyDeclarationType()
		 * @generated
		 */
		EClass COMPOUND_PROPERTY_DECLARATION_TYPE = eINSTANCE.getCompoundPropertyDeclarationType();

		/**
		 * The meta object literal for the '<em><b>Abstract Property Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPOUND_PROPERTY_DECLARATION_TYPE__ABSTRACT_PROPERTY_GROUP = eINSTANCE.getCompoundPropertyDeclarationType_AbstractPropertyGroup();

		/**
		 * The meta object literal for the '<em><b>Abstract Property</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPOUND_PROPERTY_DECLARATION_TYPE__ABSTRACT_PROPERTY = eINSTANCE.getCompoundPropertyDeclarationType_AbstractProperty();

		/**
		 * The meta object literal for the '<em><b>Source Type Mapping</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPOUND_PROPERTY_DECLARATION_TYPE__SOURCE_TYPE_MAPPING = eINSTANCE.getCompoundPropertyDeclarationType_SourceTypeMapping();

		/**
		 * The meta object literal for the '<em><b>Converter Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPOUND_PROPERTY_DECLARATION_TYPE__CONVERTER_CLASS = eINSTANCE.getCompoundPropertyDeclarationType_ConverterClass();

		/**
		 * The meta object literal for the '<em><b>Editable Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPOUND_PROPERTY_DECLARATION_TYPE__EDITABLE_TYPE = eINSTANCE.getCompoundPropertyDeclarationType_EditableType();

		/**
		 * The meta object literal for the '<em><b>Editor Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPOUND_PROPERTY_DECLARATION_TYPE__EDITOR_CLASS = eINSTANCE.getCompoundPropertyDeclarationType_EditorClass();

		/**
		 * The meta object literal for the '<em><b>Qualified Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPOUND_PROPERTY_DECLARATION_TYPE__QUALIFIED_NAME = eINSTANCE.getCompoundPropertyDeclarationType_QualifiedName();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.CompoundPropertyTypeImpl <em>Compound Property Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.CompoundPropertyTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getCompoundPropertyType()
		 * @generated
		 */
		EClass COMPOUND_PROPERTY_TYPE = eINSTANCE.getCompoundPropertyType();

		/**
		 * The meta object literal for the '<em><b>Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPOUND_PROPERTY_TYPE__DEFAULT = eINSTANCE.getCompoundPropertyType_Default();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPOUND_PROPERTY_TYPE__TYPE = eINSTANCE.getCompoundPropertyType_Type();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.ConditionalSourceGenImpl <em>Conditional Source Gen</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.ConditionalSourceGenImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getConditionalSourceGen()
		 * @generated
		 */
		EClass CONDITIONAL_SOURCE_GEN = eINSTANCE.getConditionalSourceGen();

		/**
		 * The meta object literal for the '<em><b>Forms</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONDITIONAL_SOURCE_GEN__FORMS = eINSTANCE.getConditionalSourceGen_Forms();

		/**
		 * The meta object literal for the '<em><b>If Events</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONDITIONAL_SOURCE_GEN__IF_EVENTS = eINSTANCE.getConditionalSourceGen_IfEvents();

		/**
		 * The meta object literal for the '<em><b>If Expr</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONDITIONAL_SOURCE_GEN__IF_EXPR = eINSTANCE.getConditionalSourceGen_IfExpr();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.ConditionalSourceGenStringImpl <em>Conditional Source Gen String</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.ConditionalSourceGenStringImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getConditionalSourceGenString()
		 * @generated
		 */
		EClass CONDITIONAL_SOURCE_GEN_STRING = eINSTANCE.getConditionalSourceGenString();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONDITIONAL_SOURCE_GEN_STRING__VALUE = eINSTANCE.getConditionalSourceGenString_Value();

		/**
		 * The meta object literal for the '<em><b>Forms</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONDITIONAL_SOURCE_GEN_STRING__FORMS = eINSTANCE.getConditionalSourceGenString_Forms();

		/**
		 * The meta object literal for the '<em><b>If Events</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONDITIONAL_SOURCE_GEN_STRING__IF_EVENTS = eINSTANCE.getConditionalSourceGenString_IfEvents();

		/**
		 * The meta object literal for the '<em><b>If Expr</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONDITIONAL_SOURCE_GEN_STRING__IF_EXPR = eINSTANCE.getConditionalSourceGenString_IfExpr();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.DefineLocationTypeImpl <em>Define Location Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.DefineLocationTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getDefineLocationType()
		 * @generated
		 */
		EClass DEFINE_LOCATION_TYPE = eINSTANCE.getDefineLocationType();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEFINE_LOCATION_TYPE__GROUP = eINSTANCE.getDefineLocationType_Group();

		/**
		 * The meta object literal for the '<em><b>Template</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEFINE_LOCATION_TYPE__TEMPLATE = eINSTANCE.getDefineLocationType_Template();

		/**
		 * The meta object literal for the '<em><b>Inline</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEFINE_LOCATION_TYPE__INLINE = eINSTANCE.getDefineLocationType_Inline();

		/**
		 * The meta object literal for the '<em><b>Script</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEFINE_LOCATION_TYPE__SCRIPT = eINSTANCE.getDefineLocationType_Script();

		/**
		 * The meta object literal for the '<em><b>Expand Macro</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEFINE_LOCATION_TYPE__EXPAND_MACRO = eINSTANCE.getDefineLocationType_ExpandMacro();

		/**
		 * The meta object literal for the '<em><b>Base Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEFINE_LOCATION_TYPE__BASE_LOCATION = eINSTANCE.getDefineLocationType_BaseLocation();

		/**
		 * The meta object literal for the '<em><b>Dir</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEFINE_LOCATION_TYPE__DIR = eINSTANCE.getDefineLocationType_Dir();

		/**
		 * The meta object literal for the '<em><b>Domain</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEFINE_LOCATION_TYPE__DOMAIN = eINSTANCE.getDefineLocationType_Domain();

		/**
		 * The meta object literal for the '<em><b>File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEFINE_LOCATION_TYPE__FILE = eINSTANCE.getDefineLocationType_File();

		/**
		 * The meta object literal for the '<em><b>Filter</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEFINE_LOCATION_TYPE__FILTER = eINSTANCE.getDefineLocationType_Filter();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEFINE_LOCATION_TYPE__ID = eINSTANCE.getDefineLocationType_Id();

		/**
		 * The meta object literal for the '<em><b>If Events</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEFINE_LOCATION_TYPE__IF_EVENTS = eINSTANCE.getDefineLocationType_IfEvents();

		/**
		 * The meta object literal for the '<em><b>Is Event Handler</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEFINE_LOCATION_TYPE__IS_EVENT_HANDLER = eINSTANCE.getDefineLocationType_IsEventHandler();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEFINE_LOCATION_TYPE__LOCATION = eINSTANCE.getDefineLocationType_Location();

		/**
		 * The meta object literal for the '<em><b>Owned</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEFINE_LOCATION_TYPE__OWNED = eINSTANCE.getDefineLocationType_Owned();

		/**
		 * The meta object literal for the '<em><b>Realize</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEFINE_LOCATION_TYPE__REALIZE = eINSTANCE.getDefineLocationType_Realize();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.DefineMacroTypeImpl <em>Define Macro Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.DefineMacroTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getDefineMacroType()
		 * @generated
		 */
		EClass DEFINE_MACRO_TYPE = eINSTANCE.getDefineMacroType();

		/**
		 * The meta object literal for the '<em><b>Import Arguments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEFINE_MACRO_TYPE__IMPORT_ARGUMENTS = eINSTANCE.getDefineMacroType_ImportArguments();

		/**
		 * The meta object literal for the '<em><b>Macro Argument</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEFINE_MACRO_TYPE__MACRO_ARGUMENT = eINSTANCE.getDefineMacroType_MacroArgument();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEFINE_MACRO_TYPE__GROUP = eINSTANCE.getDefineMacroType_Group();

		/**
		 * The meta object literal for the '<em><b>Template</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEFINE_MACRO_TYPE__TEMPLATE = eINSTANCE.getDefineMacroType_Template();

		/**
		 * The meta object literal for the '<em><b>Inline</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEFINE_MACRO_TYPE__INLINE = eINSTANCE.getDefineMacroType_Inline();

		/**
		 * The meta object literal for the '<em><b>Define Location</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEFINE_MACRO_TYPE__DEFINE_LOCATION = eINSTANCE.getDefineMacroType_DefineLocation();

		/**
		 * The meta object literal for the '<em><b>Expand Macro</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEFINE_MACRO_TYPE__EXPAND_MACRO = eINSTANCE.getDefineMacroType_ExpandMacro();

		/**
		 * The meta object literal for the '<em><b>Help</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEFINE_MACRO_TYPE__HELP = eINSTANCE.getDefineMacroType_Help();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEFINE_MACRO_TYPE__ID = eINSTANCE.getDefineMacroType_Id();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.DesignerImagesTypeImpl <em>Designer Images Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.DesignerImagesTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getDesignerImagesType()
		 * @generated
		 */
		EClass DESIGNER_IMAGES_TYPE = eINSTANCE.getDesignerImagesType();

		/**
		 * The meta object literal for the '<em><b>Large Icon File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DESIGNER_IMAGES_TYPE__LARGE_ICON_FILE = eINSTANCE.getDesignerImagesType_LargeIconFile();

		/**
		 * The meta object literal for the '<em><b>Layout Image File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DESIGNER_IMAGES_TYPE__LAYOUT_IMAGE_FILE = eINSTANCE.getDesignerImagesType_LayoutImageFile();

		/**
		 * The meta object literal for the '<em><b>Small Icon File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DESIGNER_IMAGES_TYPE__SMALL_ICON_FILE = eINSTANCE.getDesignerImagesType_SmallIconFile();

		/**
		 * The meta object literal for the '<em><b>Thumbnail File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DESIGNER_IMAGES_TYPE__THUMBNAIL_FILE = eINSTANCE.getDesignerImagesType_ThumbnailFile();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.DocumentationTypeImpl <em>Documentation Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.DocumentationTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getDocumentationType()
		 * @generated
		 */
		EClass DOCUMENTATION_TYPE = eINSTANCE.getDocumentationType();

		/**
		 * The meta object literal for the '<em><b>Information</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENTATION_TYPE__INFORMATION = eINSTANCE.getDocumentationType_Information();

		/**
		 * The meta object literal for the '<em><b>Help Topic</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENTATION_TYPE__HELP_TOPIC = eINSTANCE.getDocumentationType_HelpTopic();

		/**
		 * The meta object literal for the '<em><b>Wizard Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENTATION_TYPE__WIZARD_DESCRIPTION = eINSTANCE.getDocumentationType_WizardDescription();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.DocumentRootImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getDocumentRoot()
		 * @generated
		 */
		EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

		/**
		 * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

		/**
		 * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getDocumentRoot_XMLNSPrefixMap();

		/**
		 * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getDocumentRoot_XSISchemaLocation();

		/**
		 * The meta object literal for the '<em><b>Abstract Property</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ABSTRACT_PROPERTY = eINSTANCE.getDocumentRoot_AbstractProperty();

		/**
		 * The meta object literal for the '<em><b>Array Property</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ARRAY_PROPERTY = eINSTANCE.getDocumentRoot_ArrayProperty();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ATTRIBUTE = eINSTANCE.getDocumentRoot_Attribute();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ATTRIBUTES = eINSTANCE.getDocumentRoot_Attributes();

		/**
		 * The meta object literal for the '<em><b>Choice</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__CHOICE = eINSTANCE.getDocumentRoot_Choice();

		/**
		 * The meta object literal for the '<em><b>Code</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__CODE = eINSTANCE.getDocumentRoot_Code();

		/**
		 * The meta object literal for the '<em><b>Component</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__COMPONENT = eINSTANCE.getDocumentRoot_Component();

		/**
		 * The meta object literal for the '<em><b>Component Definition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__COMPONENT_DEFINITION = eINSTANCE.getDocumentRoot_ComponentDefinition();

		/**
		 * The meta object literal for the '<em><b>Component Reference Property</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__COMPONENT_REFERENCE_PROPERTY = eINSTANCE.getDocumentRoot_ComponentReferenceProperty();

		/**
		 * The meta object literal for the '<em><b>Compound Property</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__COMPOUND_PROPERTY = eINSTANCE.getDocumentRoot_CompoundProperty();

		/**
		 * The meta object literal for the '<em><b>Compound Property Declaration</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__COMPOUND_PROPERTY_DECLARATION = eINSTANCE.getDocumentRoot_CompoundPropertyDeclaration();

		/**
		 * The meta object literal for the '<em><b>Define Location</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__DEFINE_LOCATION = eINSTANCE.getDocumentRoot_DefineLocation();

		/**
		 * The meta object literal for the '<em><b>Define Macro</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__DEFINE_MACRO = eINSTANCE.getDocumentRoot_DefineMacro();

		/**
		 * The meta object literal for the '<em><b>Designer Images</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__DESIGNER_IMAGES = eINSTANCE.getDocumentRoot_DesignerImages();

		/**
		 * The meta object literal for the '<em><b>Documentation</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__DOCUMENTATION = eINSTANCE.getDocumentRoot_Documentation();

		/**
		 * The meta object literal for the '<em><b>Enum Property</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ENUM_PROPERTY = eINSTANCE.getDocumentRoot_EnumProperty();

		/**
		 * The meta object literal for the '<em><b>Enum Property Declaration</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ENUM_PROPERTY_DECLARATION = eINSTANCE.getDocumentRoot_EnumPropertyDeclaration();

		/**
		 * The meta object literal for the '<em><b>Event</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__EVENT = eINSTANCE.getDocumentRoot_Event();

		/**
		 * The meta object literal for the '<em><b>Events</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__EVENTS = eINSTANCE.getDocumentRoot_Events();

		/**
		 * The meta object literal for the '<em><b>Expand Argument</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__EXPAND_ARGUMENT = eINSTANCE.getDocumentRoot_ExpandArgument();

		/**
		 * The meta object literal for the '<em><b>Expand Macro</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__EXPAND_MACRO = eINSTANCE.getDocumentRoot_ExpandMacro();

		/**
		 * The meta object literal for the '<em><b>Extension Properties</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__EXTENSION_PROPERTIES = eINSTANCE.getDocumentRoot_ExtensionProperties();

		/**
		 * The meta object literal for the '<em><b>Implementation</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__IMPLEMENTATION = eINSTANCE.getDocumentRoot_Implementation();

		/**
		 * The meta object literal for the '<em><b>Implementations</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__IMPLEMENTATIONS = eINSTANCE.getDocumentRoot_Implementations();

		/**
		 * The meta object literal for the '<em><b>Import Arguments</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__IMPORT_ARGUMENTS = eINSTANCE.getDocumentRoot_ImportArguments();

		/**
		 * The meta object literal for the '<em><b>Inline</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__INLINE = eINSTANCE.getDocumentRoot_Inline();

		/**
		 * The meta object literal for the '<em><b>Macro Argument</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MACRO_ARGUMENT = eINSTANCE.getDocumentRoot_MacroArgument();

		/**
		 * The meta object literal for the '<em><b>Map Array Member</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_ARRAY_MEMBER = eINSTANCE.getDocumentRoot_MapArrayMember();

		/**
		 * The meta object literal for the '<em><b>Two Way Mapping</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__TWO_WAY_MAPPING = eINSTANCE.getDocumentRoot_TwoWayMapping();

		/**
		 * The meta object literal for the '<em><b>Map Array Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_ARRAY_TYPE = eINSTANCE.getDocumentRoot_MapArrayType();

		/**
		 * The meta object literal for the '<em><b>Map Bitmask Element</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_BITMASK_ELEMENT = eINSTANCE.getDocumentRoot_MapBitmaskElement();

		/**
		 * The meta object literal for the '<em><b>Map Bitmask Member</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_BITMASK_MEMBER = eINSTANCE.getDocumentRoot_MapBitmaskMember();

		/**
		 * The meta object literal for the '<em><b>Map Bitmask Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_BITMASK_TYPE = eINSTANCE.getDocumentRoot_MapBitmaskType();

		/**
		 * The meta object literal for the '<em><b>Map Bitmask Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_BITMASK_VALUE = eINSTANCE.getDocumentRoot_MapBitmaskValue();

		/**
		 * The meta object literal for the '<em><b>Map Element From Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_ELEMENT_FROM_TYPE = eINSTANCE.getDocumentRoot_MapElementFromType();

		/**
		 * The meta object literal for the '<em><b>Map Enum</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_ENUM = eINSTANCE.getDocumentRoot_MapEnum();

		/**
		 * The meta object literal for the '<em><b>Map Enum Element</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_ENUM_ELEMENT = eINSTANCE.getDocumentRoot_MapEnumElement();

		/**
		 * The meta object literal for the '<em><b>Map Enum Member</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_ENUM_MEMBER = eINSTANCE.getDocumentRoot_MapEnumMember();

		/**
		 * The meta object literal for the '<em><b>Map Enum Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_ENUM_TYPE = eINSTANCE.getDocumentRoot_MapEnumType();

		/**
		 * The meta object literal for the '<em><b>Map Fixed Element</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_FIXED_ELEMENT = eINSTANCE.getDocumentRoot_MapFixedElement();

		/**
		 * The meta object literal for the '<em><b>Map Fixed Member</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_FIXED_MEMBER = eINSTANCE.getDocumentRoot_MapFixedMember();

		/**
		 * The meta object literal for the '<em><b>Map Fixed Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_FIXED_TYPE = eINSTANCE.getDocumentRoot_MapFixedType();

		/**
		 * The meta object literal for the '<em><b>Map Identifier Element</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_IDENTIFIER_ELEMENT = eINSTANCE.getDocumentRoot_MapIdentifierElement();

		/**
		 * The meta object literal for the '<em><b>Map Identifier Member</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_IDENTIFIER_MEMBER = eINSTANCE.getDocumentRoot_MapIdentifierMember();

		/**
		 * The meta object literal for the '<em><b>Map Identifier Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_IDENTIFIER_TYPE = eINSTANCE.getDocumentRoot_MapIdentifierType();

		/**
		 * The meta object literal for the '<em><b>Map Instance Element</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_INSTANCE_ELEMENT = eINSTANCE.getDocumentRoot_MapInstanceElement();

		/**
		 * The meta object literal for the '<em><b>Map Instance Member</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_INSTANCE_MEMBER = eINSTANCE.getDocumentRoot_MapInstanceMember();

		/**
		 * The meta object literal for the '<em><b>Map Instance Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_INSTANCE_TYPE = eINSTANCE.getDocumentRoot_MapInstanceType();

		/**
		 * The meta object literal for the '<em><b>Map Into Property</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_INTO_PROPERTY = eINSTANCE.getDocumentRoot_MapIntoProperty();

		/**
		 * The meta object literal for the '<em><b>Map Member From Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_MEMBER_FROM_TYPE = eINSTANCE.getDocumentRoot_MapMemberFromType();

		/**
		 * The meta object literal for the '<em><b>Map Reference Element</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_REFERENCE_ELEMENT = eINSTANCE.getDocumentRoot_MapReferenceElement();

		/**
		 * The meta object literal for the '<em><b>Map Reference Member</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_REFERENCE_MEMBER = eINSTANCE.getDocumentRoot_MapReferenceMember();

		/**
		 * The meta object literal for the '<em><b>Map Reference Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_REFERENCE_TYPE = eINSTANCE.getDocumentRoot_MapReferenceType();

		/**
		 * The meta object literal for the '<em><b>Map Resource</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_RESOURCE = eINSTANCE.getDocumentRoot_MapResource();

		/**
		 * The meta object literal for the '<em><b>Map Resource Element</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_RESOURCE_ELEMENT = eINSTANCE.getDocumentRoot_MapResourceElement();

		/**
		 * The meta object literal for the '<em><b>Map Resource Member</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_RESOURCE_MEMBER = eINSTANCE.getDocumentRoot_MapResourceMember();

		/**
		 * The meta object literal for the '<em><b>Map Resource Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_RESOURCE_TYPE = eINSTANCE.getDocumentRoot_MapResourceType();

		/**
		 * The meta object literal for the '<em><b>Map Simple Element</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_SIMPLE_ELEMENT = eINSTANCE.getDocumentRoot_MapSimpleElement();

		/**
		 * The meta object literal for the '<em><b>Map Simple Member</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_SIMPLE_MEMBER = eINSTANCE.getDocumentRoot_MapSimpleMember();

		/**
		 * The meta object literal for the '<em><b>Map Simple Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAP_SIMPLE_TYPE = eINSTANCE.getDocumentRoot_MapSimpleType();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__PROPERTIES = eINSTANCE.getDocumentRoot_Properties();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__PROPERTY = eINSTANCE.getDocumentRoot_Property();

		/**
		 * The meta object literal for the '<em><b>Property Overrides</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__PROPERTY_OVERRIDES = eINSTANCE.getDocumentRoot_PropertyOverrides();

		/**
		 * The meta object literal for the '<em><b>Script</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__SCRIPT = eINSTANCE.getDocumentRoot_Script();

		/**
		 * The meta object literal for the '<em><b>Select</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__SELECT = eINSTANCE.getDocumentRoot_Select();

		/**
		 * The meta object literal for the '<em><b>Source Gen</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__SOURCE_GEN = eINSTANCE.getDocumentRoot_SourceGen();

		/**
		 * The meta object literal for the '<em><b>Source Mapping</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__SOURCE_MAPPING = eINSTANCE.getDocumentRoot_SourceMapping();

		/**
		 * The meta object literal for the '<em><b>Source Type Mapping</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__SOURCE_TYPE_MAPPING = eINSTANCE.getDocumentRoot_SourceTypeMapping();

		/**
		 * The meta object literal for the '<em><b>Symbian</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__SYMBIAN = eINSTANCE.getDocumentRoot_Symbian();

		/**
		 * The meta object literal for the '<em><b>Template</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__TEMPLATE = eINSTANCE.getDocumentRoot_Template();

		/**
		 * The meta object literal for the '<em><b>Template Group</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__TEMPLATE_GROUP = eINSTANCE.getDocumentRoot_TemplateGroup();

		/**
		 * The meta object literal for the '<em><b>Use Template</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__USE_TEMPLATE = eINSTANCE.getDocumentRoot_UseTemplate();

		/**
		 * The meta object literal for the '<em><b>Use Template Group</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__USE_TEMPLATE_GROUP = eINSTANCE.getDocumentRoot_UseTemplateGroup();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.EnumElementTypeImpl <em>Enum Element Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.EnumElementTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getEnumElementType()
		 * @generated
		 */
		EClass ENUM_ELEMENT_TYPE = eINSTANCE.getEnumElementType();

		/**
		 * The meta object literal for the '<em><b>Display Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENUM_ELEMENT_TYPE__DISPLAY_VALUE = eINSTANCE.getEnumElementType_DisplayValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENUM_ELEMENT_TYPE__VALUE = eINSTANCE.getEnumElementType_Value();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.EnumPropertyDeclarationTypeImpl <em>Enum Property Declaration Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.EnumPropertyDeclarationTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getEnumPropertyDeclarationType()
		 * @generated
		 */
		EClass ENUM_PROPERTY_DECLARATION_TYPE = eINSTANCE.getEnumPropertyDeclarationType();

		/**
		 * The meta object literal for the '<em><b>Enum Element</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENUM_PROPERTY_DECLARATION_TYPE__ENUM_ELEMENT = eINSTANCE.getEnumPropertyDeclarationType_EnumElement();

		/**
		 * The meta object literal for the '<em><b>Source Type Mapping</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENUM_PROPERTY_DECLARATION_TYPE__SOURCE_TYPE_MAPPING = eINSTANCE.getEnumPropertyDeclarationType_SourceTypeMapping();

		/**
		 * The meta object literal for the '<em><b>Default Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENUM_PROPERTY_DECLARATION_TYPE__DEFAULT_VALUE = eINSTANCE.getEnumPropertyDeclarationType_DefaultValue();

		/**
		 * The meta object literal for the '<em><b>Qualified Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENUM_PROPERTY_DECLARATION_TYPE__QUALIFIED_NAME = eINSTANCE.getEnumPropertyDeclarationType_QualifiedName();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.EnumPropertyTypeImpl <em>Enum Property Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.EnumPropertyTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getEnumPropertyType()
		 * @generated
		 */
		EClass ENUM_PROPERTY_TYPE = eINSTANCE.getEnumPropertyType();

		/**
		 * The meta object literal for the '<em><b>Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENUM_PROPERTY_TYPE__DEFAULT = eINSTANCE.getEnumPropertyType_Default();

		/**
		 * The meta object literal for the '<em><b>Extend With Enum</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENUM_PROPERTY_TYPE__EXTEND_WITH_ENUM = eINSTANCE.getEnumPropertyType_ExtendWithEnum();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENUM_PROPERTY_TYPE__TYPE = eINSTANCE.getEnumPropertyType_Type();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.EventsTypeImpl <em>Events Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.EventsTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getEventsType()
		 * @generated
		 */
		EClass EVENTS_TYPE = eINSTANCE.getEventsType();

		/**
		 * The meta object literal for the '<em><b>Event</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENTS_TYPE__EVENT = eINSTANCE.getEventsType_Event();

		/**
		 * The meta object literal for the '<em><b>Default Event Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENTS_TYPE__DEFAULT_EVENT_NAME = eINSTANCE.getEventsType_DefaultEventName();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.EventTypeImpl <em>Event Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.EventTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getEventType()
		 * @generated
		 */
		EClass EVENT_TYPE = eINSTANCE.getEventType();

		/**
		 * The meta object literal for the '<em><b>Category</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT_TYPE__CATEGORY = eINSTANCE.getEventType_Category();

		/**
		 * The meta object literal for the '<em><b>Description Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT_TYPE__DESCRIPTION_KEY = eINSTANCE.getEventType_DescriptionKey();

		/**
		 * The meta object literal for the '<em><b>Display Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT_TYPE__DISPLAY_NAME = eINSTANCE.getEventType_DisplayName();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT_TYPE__GROUP = eINSTANCE.getEventType_Group();

		/**
		 * The meta object literal for the '<em><b>Handler Name Template</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT_TYPE__HANDLER_NAME_TEMPLATE = eINSTANCE.getEventType_HandlerNameTemplate();

		/**
		 * The meta object literal for the '<em><b>Help Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT_TYPE__HELP_KEY = eINSTANCE.getEventType_HelpKey();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT_TYPE__NAME = eINSTANCE.getEventType_Name();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.ExpandArgumentTypeImpl <em>Expand Argument Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.ExpandArgumentTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getExpandArgumentType()
		 * @generated
		 */
		EClass EXPAND_ARGUMENT_TYPE = eINSTANCE.getExpandArgumentType();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPAND_ARGUMENT_TYPE__VALUE = eINSTANCE.getExpandArgumentType_Value();

		/**
		 * The meta object literal for the '<em><b>Help</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPAND_ARGUMENT_TYPE__HELP = eINSTANCE.getExpandArgumentType_Help();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPAND_ARGUMENT_TYPE__NAME = eINSTANCE.getExpandArgumentType_Name();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.ExpandMacroTypeImpl <em>Expand Macro Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.ExpandMacroTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getExpandMacroType()
		 * @generated
		 */
		EClass EXPAND_MACRO_TYPE = eINSTANCE.getExpandMacroType();

		/**
		 * The meta object literal for the '<em><b>Expand Argument</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPAND_MACRO_TYPE__EXPAND_ARGUMENT = eINSTANCE.getExpandMacroType_ExpandArgument();

		/**
		 * The meta object literal for the '<em><b>Dont Pass Arguments</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPAND_MACRO_TYPE__DONT_PASS_ARGUMENTS = eINSTANCE.getExpandMacroType_DontPassArguments();

		/**
		 * The meta object literal for the '<em><b>Help</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPAND_MACRO_TYPE__HELP = eINSTANCE.getExpandMacroType_Help();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPAND_MACRO_TYPE__NAME = eINSTANCE.getExpandMacroType_Name();

		/**
		 * The meta object literal for the '<em><b>Pass Arguments</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPAND_MACRO_TYPE__PASS_ARGUMENTS = eINSTANCE.getExpandMacroType_PassArguments();

		/**
		 * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPAND_MACRO_TYPE__ANY_ATTRIBUTE = eINSTANCE.getExpandMacroType_AnyAttribute();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.ExtensionPropertiesTypeImpl <em>Extension Properties Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.ExtensionPropertiesTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getExtensionPropertiesType()
		 * @generated
		 */
		EClass EXTENSION_PROPERTIES_TYPE = eINSTANCE.getExtensionPropertiesType();

		/**
		 * The meta object literal for the '<em><b>Abstract Property Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTENSION_PROPERTIES_TYPE__ABSTRACT_PROPERTY_GROUP = eINSTANCE.getExtensionPropertiesType_AbstractPropertyGroup();

		/**
		 * The meta object literal for the '<em><b>Abstract Property</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTENSION_PROPERTIES_TYPE__ABSTRACT_PROPERTY = eINSTANCE.getExtensionPropertiesType_AbstractProperty();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTENSION_PROPERTIES_TYPE__NAME = eINSTANCE.getExtensionPropertiesType_Name();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.ImplementationsTypeImpl <em>Implementations Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.ImplementationsTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getImplementationsType()
		 * @generated
		 */
		EClass IMPLEMENTATIONS_TYPE = eINSTANCE.getImplementationsType();

		/**
		 * The meta object literal for the '<em><b>Implementation</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IMPLEMENTATIONS_TYPE__IMPLEMENTATION = eINSTANCE.getImplementationsType_Implementation();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.ImplementationTypeImpl <em>Implementation Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.ImplementationTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getImplementationType()
		 * @generated
		 */
		EClass IMPLEMENTATION_TYPE = eINSTANCE.getImplementationType();

		/**
		 * The meta object literal for the '<em><b>Interface</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IMPLEMENTATION_TYPE__INTERFACE = eINSTANCE.getImplementationType_Interface();

		/**
		 * The meta object literal for the '<em><b>Code</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IMPLEMENTATION_TYPE__CODE = eINSTANCE.getImplementationType_Code();

		/**
		 * The meta object literal for the '<em><b>Script</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IMPLEMENTATION_TYPE__SCRIPT = eINSTANCE.getImplementationType_Script();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.ImportArgumentsTypeImpl <em>Import Arguments Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.ImportArgumentsTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getImportArgumentsType()
		 * @generated
		 */
		EClass IMPORT_ARGUMENTS_TYPE = eINSTANCE.getImportArgumentsType();

		/**
		 * The meta object literal for the '<em><b>Arguments</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORT_ARGUMENTS_TYPE__ARGUMENTS = eINSTANCE.getImportArgumentsType_Arguments();

		/**
		 * The meta object literal for the '<em><b>Except Arguments</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORT_ARGUMENTS_TYPE__EXCEPT_ARGUMENTS = eINSTANCE.getImportArgumentsType_ExceptArguments();

		/**
		 * The meta object literal for the '<em><b>Help</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORT_ARGUMENTS_TYPE__HELP = eINSTANCE.getImportArgumentsType_Help();

		/**
		 * The meta object literal for the '<em><b>Macro Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORT_ARGUMENTS_TYPE__MACRO_NAME = eINSTANCE.getImportArgumentsType_MacroName();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.InlineTypeImpl <em>Inline Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.InlineTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getInlineType()
		 * @generated
		 */
		EClass INLINE_TYPE = eINSTANCE.getInlineType();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INLINE_TYPE__ID = eINSTANCE.getInlineType_Id();

		/**
		 * The meta object literal for the '<em><b>Scope</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INLINE_TYPE__SCOPE = eINSTANCE.getInlineType_Scope();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.InterfaceTypeImpl <em>Interface Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.InterfaceTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getInterfaceType()
		 * @generated
		 */
		EClass INTERFACE_TYPE = eINSTANCE.getInterfaceType();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTERFACE_TYPE__ID = eINSTANCE.getInterfaceType_Id();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MacroArgumentTypeImpl <em>Macro Argument Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MacroArgumentTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMacroArgumentType()
		 * @generated
		 */
		EClass MACRO_ARGUMENT_TYPE = eINSTANCE.getMacroArgumentType();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MACRO_ARGUMENT_TYPE__VALUE = eINSTANCE.getMacroArgumentType_Value();

		/**
		 * The meta object literal for the '<em><b>Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MACRO_ARGUMENT_TYPE__DEFAULT = eINSTANCE.getMacroArgumentType_Default();

		/**
		 * The meta object literal for the '<em><b>Help</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MACRO_ARGUMENT_TYPE__HELP = eINSTANCE.getMacroArgumentType_Help();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MACRO_ARGUMENT_TYPE__NAME = eINSTANCE.getMacroArgumentType_Name();

		/**
		 * The meta object literal for the '<em><b>Optional</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MACRO_ARGUMENT_TYPE__OPTIONAL = eINSTANCE.getMacroArgumentType_Optional();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapArrayMemberTypeImpl <em>Map Array Member Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapArrayMemberTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapArrayMemberType()
		 * @generated
		 */
		EClass MAP_ARRAY_MEMBER_TYPE = eINSTANCE.getMapArrayMemberType();

		/**
		 * The meta object literal for the '<em><b>Member</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_ARRAY_MEMBER_TYPE__MEMBER = eINSTANCE.getMapArrayMemberType_Member();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_ARRAY_MEMBER_TYPE__PROPERTY = eINSTANCE.getMapArrayMemberType_Property();

		/**
		 * The meta object literal for the '<em><b>Suppress Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_ARRAY_MEMBER_TYPE__SUPPRESS_DEFAULT = eINSTANCE.getMapArrayMemberType_SuppressDefault();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapArrayTypeTypeImpl <em>Map Array Type Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapArrayTypeTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapArrayTypeType()
		 * @generated
		 */
		EClass MAP_ARRAY_TYPE_TYPE = eINSTANCE.getMapArrayTypeType();

		/**
		 * The meta object literal for the '<em><b>Type Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_ARRAY_TYPE_TYPE__TYPE_ID = eINSTANCE.getMapArrayTypeType_TypeId();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapBitmaskElementTypeImpl <em>Map Bitmask Element Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapBitmaskElementTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapBitmaskElementType()
		 * @generated
		 */
		EClass MAP_BITMASK_ELEMENT_TYPE = eINSTANCE.getMapBitmaskElementType();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapBitmaskMemberTypeImpl <em>Map Bitmask Member Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapBitmaskMemberTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapBitmaskMemberType()
		 * @generated
		 */
		EClass MAP_BITMASK_MEMBER_TYPE = eINSTANCE.getMapBitmaskMemberType();

		/**
		 * The meta object literal for the '<em><b>Member</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_BITMASK_MEMBER_TYPE__MEMBER = eINSTANCE.getMapBitmaskMemberType_Member();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_BITMASK_MEMBER_TYPE__PROPERTY = eINSTANCE.getMapBitmaskMemberType_Property();

		/**
		 * The meta object literal for the '<em><b>Suppress Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_BITMASK_MEMBER_TYPE__SUPPRESS_DEFAULT = eINSTANCE.getMapBitmaskMemberType_SuppressDefault();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapBitmaskTypeTypeImpl <em>Map Bitmask Type Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapBitmaskTypeTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapBitmaskTypeType()
		 * @generated
		 */
		EClass MAP_BITMASK_TYPE_TYPE = eINSTANCE.getMapBitmaskTypeType();

		/**
		 * The meta object literal for the '<em><b>Type Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_BITMASK_TYPE_TYPE__TYPE_ID = eINSTANCE.getMapBitmaskTypeType_TypeId();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapBitmaskValueTypeImpl <em>Map Bitmask Value Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapBitmaskValueTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapBitmaskValueType()
		 * @generated
		 */
		EClass MAP_BITMASK_VALUE_TYPE = eINSTANCE.getMapBitmaskValueType();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_BITMASK_VALUE_TYPE__PROPERTIES = eINSTANCE.getMapBitmaskValueType_Properties();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_BITMASK_VALUE_TYPE__VALUE = eINSTANCE.getMapBitmaskValueType_Value();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapElementFromTypeTypeImpl <em>Map Element From Type Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapElementFromTypeTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapElementFromTypeType()
		 * @generated
		 */
		EClass MAP_ELEMENT_FROM_TYPE_TYPE = eINSTANCE.getMapElementFromTypeType();

		/**
		 * The meta object literal for the '<em><b>Type Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_ELEMENT_FROM_TYPE_TYPE__TYPE_ID = eINSTANCE.getMapElementFromTypeType_TypeId();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapEnumElementTypeImpl <em>Map Enum Element Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapEnumElementTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapEnumElementType()
		 * @generated
		 */
		EClass MAP_ENUM_ELEMENT_TYPE = eINSTANCE.getMapEnumElementType();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapEnumMemberTypeImpl <em>Map Enum Member Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapEnumMemberTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapEnumMemberType()
		 * @generated
		 */
		EClass MAP_ENUM_MEMBER_TYPE = eINSTANCE.getMapEnumMemberType();

		/**
		 * The meta object literal for the '<em><b>Member</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_ENUM_MEMBER_TYPE__MEMBER = eINSTANCE.getMapEnumMemberType_Member();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_ENUM_MEMBER_TYPE__PROPERTY = eINSTANCE.getMapEnumMemberType_Property();

		/**
		 * The meta object literal for the '<em><b>Suppress Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_ENUM_MEMBER_TYPE__SUPPRESS_DEFAULT = eINSTANCE.getMapEnumMemberType_SuppressDefault();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapEnumTypeImpl <em>Map Enum Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapEnumTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapEnumType()
		 * @generated
		 */
		EClass MAP_ENUM_TYPE = eINSTANCE.getMapEnumType();

		/**
		 * The meta object literal for the '<em><b>Enumerator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_ENUM_TYPE__ENUMERATOR = eINSTANCE.getMapEnumType_Enumerator();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_ENUM_TYPE__VALUE = eINSTANCE.getMapEnumType_Value();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapEnumTypeTypeImpl <em>Map Enum Type Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapEnumTypeTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapEnumTypeType()
		 * @generated
		 */
		EClass MAP_ENUM_TYPE_TYPE = eINSTANCE.getMapEnumTypeType();

		/**
		 * The meta object literal for the '<em><b>Type Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_ENUM_TYPE_TYPE__TYPE_ID = eINSTANCE.getMapEnumTypeType_TypeId();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapFixedElementTypeImpl <em>Map Fixed Element Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapFixedElementTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapFixedElementType()
		 * @generated
		 */
		EClass MAP_FIXED_ELEMENT_TYPE = eINSTANCE.getMapFixedElementType();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapFixedMemberTypeImpl <em>Map Fixed Member Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapFixedMemberTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapFixedMemberType()
		 * @generated
		 */
		EClass MAP_FIXED_MEMBER_TYPE = eINSTANCE.getMapFixedMemberType();

		/**
		 * The meta object literal for the '<em><b>Member</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_FIXED_MEMBER_TYPE__MEMBER = eINSTANCE.getMapFixedMemberType_Member();

		/**
		 * The meta object literal for the '<em><b>Suppress Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_FIXED_MEMBER_TYPE__SUPPRESS_DEFAULT = eINSTANCE.getMapFixedMemberType_SuppressDefault();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapFixedTypeTypeImpl <em>Map Fixed Type Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapFixedTypeTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapFixedTypeType()
		 * @generated
		 */
		EClass MAP_FIXED_TYPE_TYPE = eINSTANCE.getMapFixedTypeType();

		/**
		 * The meta object literal for the '<em><b>Type Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_FIXED_TYPE_TYPE__TYPE_ID = eINSTANCE.getMapFixedTypeType_TypeId();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapIdentifierElementTypeImpl <em>Map Identifier Element Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapIdentifierElementTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapIdentifierElementType()
		 * @generated
		 */
		EClass MAP_IDENTIFIER_ELEMENT_TYPE = eINSTANCE.getMapIdentifierElementType();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapIdentifierMemberTypeImpl <em>Map Identifier Member Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapIdentifierMemberTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapIdentifierMemberType()
		 * @generated
		 */
		EClass MAP_IDENTIFIER_MEMBER_TYPE = eINSTANCE.getMapIdentifierMemberType();

		/**
		 * The meta object literal for the '<em><b>Member</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_IDENTIFIER_MEMBER_TYPE__MEMBER = eINSTANCE.getMapIdentifierMemberType_Member();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_IDENTIFIER_MEMBER_TYPE__PROPERTY = eINSTANCE.getMapIdentifierMemberType_Property();

		/**
		 * The meta object literal for the '<em><b>Suppress Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_IDENTIFIER_MEMBER_TYPE__SUPPRESS_DEFAULT = eINSTANCE.getMapIdentifierMemberType_SuppressDefault();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapIdentifierTypeTypeImpl <em>Map Identifier Type Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapIdentifierTypeTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapIdentifierTypeType()
		 * @generated
		 */
		EClass MAP_IDENTIFIER_TYPE_TYPE = eINSTANCE.getMapIdentifierTypeType();

		/**
		 * The meta object literal for the '<em><b>Type Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_IDENTIFIER_TYPE_TYPE__TYPE_ID = eINSTANCE.getMapIdentifierTypeType_TypeId();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapInstanceElementTypeImpl <em>Map Instance Element Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapInstanceElementTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapInstanceElementType()
		 * @generated
		 */
		EClass MAP_INSTANCE_ELEMENT_TYPE = eINSTANCE.getMapInstanceElementType();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapInstanceMemberTypeImpl <em>Map Instance Member Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapInstanceMemberTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapInstanceMemberType()
		 * @generated
		 */
		EClass MAP_INSTANCE_MEMBER_TYPE = eINSTANCE.getMapInstanceMemberType();

		/**
		 * The meta object literal for the '<em><b>Member</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_INSTANCE_MEMBER_TYPE__MEMBER = eINSTANCE.getMapInstanceMemberType_Member();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_INSTANCE_MEMBER_TYPE__PROPERTY = eINSTANCE.getMapInstanceMemberType_Property();

		/**
		 * The meta object literal for the '<em><b>Suppress Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_INSTANCE_MEMBER_TYPE__SUPPRESS_DEFAULT = eINSTANCE.getMapInstanceMemberType_SuppressDefault();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapInstanceTypeTypeImpl <em>Map Instance Type Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapInstanceTypeTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapInstanceTypeType()
		 * @generated
		 */
		EClass MAP_INSTANCE_TYPE_TYPE = eINSTANCE.getMapInstanceTypeType();

		/**
		 * The meta object literal for the '<em><b>Type Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_INSTANCE_TYPE_TYPE__TYPE_ID = eINSTANCE.getMapInstanceTypeType_TypeId();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapIntoPropertyTypeImpl <em>Map Into Property Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapIntoPropertyTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapIntoPropertyType()
		 * @generated
		 */
		EClass MAP_INTO_PROPERTY_TYPE = eINSTANCE.getMapIntoPropertyType();

		/**
		 * The meta object literal for the '<em><b>Two Way Mapping Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_INTO_PROPERTY_TYPE__TWO_WAY_MAPPING_GROUP = eINSTANCE.getMapIntoPropertyType_TwoWayMappingGroup();

		/**
		 * The meta object literal for the '<em><b>Two Way Mapping</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAP_INTO_PROPERTY_TYPE__TWO_WAY_MAPPING = eINSTANCE.getMapIntoPropertyType_TwoWayMapping();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_INTO_PROPERTY_TYPE__PROPERTY = eINSTANCE.getMapIntoPropertyType_Property();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapMemberFromTypeTypeImpl <em>Map Member From Type Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapMemberFromTypeTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapMemberFromTypeType()
		 * @generated
		 */
		EClass MAP_MEMBER_FROM_TYPE_TYPE = eINSTANCE.getMapMemberFromTypeType();

		/**
		 * The meta object literal for the '<em><b>Member</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_MEMBER_FROM_TYPE_TYPE__MEMBER = eINSTANCE.getMapMemberFromTypeType_Member();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_MEMBER_FROM_TYPE_TYPE__PROPERTY = eINSTANCE.getMapMemberFromTypeType_Property();

		/**
		 * The meta object literal for the '<em><b>Suppress Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_MEMBER_FROM_TYPE_TYPE__SUPPRESS_DEFAULT = eINSTANCE.getMapMemberFromTypeType_SuppressDefault();

		/**
		 * The meta object literal for the '<em><b>Type Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_MEMBER_FROM_TYPE_TYPE__TYPE_ID = eINSTANCE.getMapMemberFromTypeType_TypeId();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MappingArrayTypeImpl <em>Mapping Array Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MappingArrayTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMappingArrayType()
		 * @generated
		 */
		EClass MAPPING_ARRAY_TYPE = eINSTANCE.getMappingArrayType();

		/**
		 * The meta object literal for the '<em><b>Two Way Mapping Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAPPING_ARRAY_TYPE__TWO_WAY_MAPPING_GROUP = eINSTANCE.getMappingArrayType_TwoWayMappingGroup();

		/**
		 * The meta object literal for the '<em><b>Two Way Mapping</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING_ARRAY_TYPE__TWO_WAY_MAPPING = eINSTANCE.getMappingArrayType_TwoWayMapping();

		/**
		 * The meta object literal for the '<em><b>Select</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING_ARRAY_TYPE__SELECT = eINSTANCE.getMappingArrayType_Select();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MappingBitmaskTypeImpl <em>Mapping Bitmask Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MappingBitmaskTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMappingBitmaskType()
		 * @generated
		 */
		EClass MAPPING_BITMASK_TYPE = eINSTANCE.getMappingBitmaskType();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAPPING_BITMASK_TYPE__GROUP = eINSTANCE.getMappingBitmaskType_Group();

		/**
		 * The meta object literal for the '<em><b>Map Bitmask Value</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING_BITMASK_TYPE__MAP_BITMASK_VALUE = eINSTANCE.getMappingBitmaskType_MapBitmaskValue();

		/**
		 * The meta object literal for the '<em><b>Included Properties</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAPPING_BITMASK_TYPE__INCLUDED_PROPERTIES = eINSTANCE.getMappingBitmaskType_IncludedProperties();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MappingEnumTypeImpl <em>Mapping Enum Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MappingEnumTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMappingEnumType()
		 * @generated
		 */
		EClass MAPPING_ENUM_TYPE = eINSTANCE.getMappingEnumType();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAPPING_ENUM_TYPE__GROUP = eINSTANCE.getMappingEnumType_Group();

		/**
		 * The meta object literal for the '<em><b>Map Enum</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING_ENUM_TYPE__MAP_ENUM = eINSTANCE.getMappingEnumType_MapEnum();

		/**
		 * The meta object literal for the '<em><b>Enumeration</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAPPING_ENUM_TYPE__ENUMERATION = eINSTANCE.getMappingEnumType_Enumeration();

		/**
		 * The meta object literal for the '<em><b>Headers</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAPPING_ENUM_TYPE__HEADERS = eINSTANCE.getMappingEnumType_Headers();

		/**
		 * The meta object literal for the '<em><b>Name Algorithm</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAPPING_ENUM_TYPE__NAME_ALGORITHM = eINSTANCE.getMappingEnumType_NameAlgorithm();

		/**
		 * The meta object literal for the '<em><b>Unique Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAPPING_ENUM_TYPE__UNIQUE_VALUE = eINSTANCE.getMappingEnumType_UniqueValue();

		/**
		 * The meta object literal for the '<em><b>Validate</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAPPING_ENUM_TYPE__VALIDATE = eINSTANCE.getMappingEnumType_Validate();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MappingFixedTypeImpl <em>Mapping Fixed Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MappingFixedTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMappingFixedType()
		 * @generated
		 */
		EClass MAPPING_FIXED_TYPE = eINSTANCE.getMappingFixedType();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAPPING_FIXED_TYPE__VALUE = eINSTANCE.getMappingFixedType_Value();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MappingIdentifierTypeImpl <em>Mapping Identifier Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MappingIdentifierTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMappingIdentifierType()
		 * @generated
		 */
		EClass MAPPING_IDENTIFIER_TYPE = eINSTANCE.getMappingIdentifierType();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MappingInstanceTypeImpl <em>Mapping Instance Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MappingInstanceTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMappingInstanceType()
		 * @generated
		 */
		EClass MAPPING_INSTANCE_TYPE = eINSTANCE.getMappingInstanceType();

		/**
		 * The meta object literal for the '<em><b>Rsrc Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAPPING_INSTANCE_TYPE__RSRC_ID = eINSTANCE.getMappingInstanceType_RsrcId();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MappingReferenceTypeImpl <em>Mapping Reference Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MappingReferenceTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMappingReferenceType()
		 * @generated
		 */
		EClass MAPPING_REFERENCE_TYPE = eINSTANCE.getMappingReferenceType();

		/**
		 * The meta object literal for the '<em><b>Rsrc Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAPPING_REFERENCE_TYPE__RSRC_ID = eINSTANCE.getMappingReferenceType_RsrcId();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MappingResourceTypeImpl <em>Mapping Resource Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MappingResourceTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMappingResourceType()
		 * @generated
		 */
		EClass MAPPING_RESOURCE_TYPE = eINSTANCE.getMappingResourceType();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAPPING_RESOURCE_TYPE__GROUP = eINSTANCE.getMappingResourceType_Group();

		/**
		 * The meta object literal for the '<em><b>Map Simple Member</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING_RESOURCE_TYPE__MAP_SIMPLE_MEMBER = eINSTANCE.getMappingResourceType_MapSimpleMember();

		/**
		 * The meta object literal for the '<em><b>Map Instance Member</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING_RESOURCE_TYPE__MAP_INSTANCE_MEMBER = eINSTANCE.getMappingResourceType_MapInstanceMember();

		/**
		 * The meta object literal for the '<em><b>Map Reference Member</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING_RESOURCE_TYPE__MAP_REFERENCE_MEMBER = eINSTANCE.getMappingResourceType_MapReferenceMember();

		/**
		 * The meta object literal for the '<em><b>Map Fixed Member</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING_RESOURCE_TYPE__MAP_FIXED_MEMBER = eINSTANCE.getMappingResourceType_MapFixedMember();

		/**
		 * The meta object literal for the '<em><b>Map Enum Member</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING_RESOURCE_TYPE__MAP_ENUM_MEMBER = eINSTANCE.getMappingResourceType_MapEnumMember();

		/**
		 * The meta object literal for the '<em><b>Map Identifier Member</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING_RESOURCE_TYPE__MAP_IDENTIFIER_MEMBER = eINSTANCE.getMappingResourceType_MapIdentifierMember();

		/**
		 * The meta object literal for the '<em><b>Map Array Member</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING_RESOURCE_TYPE__MAP_ARRAY_MEMBER = eINSTANCE.getMappingResourceType_MapArrayMember();

		/**
		 * The meta object literal for the '<em><b>Map Resource Member</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING_RESOURCE_TYPE__MAP_RESOURCE_MEMBER = eINSTANCE.getMappingResourceType_MapResourceMember();

		/**
		 * The meta object literal for the '<em><b>Map Bitmask Member</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING_RESOURCE_TYPE__MAP_BITMASK_MEMBER = eINSTANCE.getMappingResourceType_MapBitmaskMember();

		/**
		 * The meta object literal for the '<em><b>Map Member From Type</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING_RESOURCE_TYPE__MAP_MEMBER_FROM_TYPE = eINSTANCE.getMappingResourceType_MapMemberFromType();

		/**
		 * The meta object literal for the '<em><b>Map Into Property</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING_RESOURCE_TYPE__MAP_INTO_PROPERTY = eINSTANCE.getMappingResourceType_MapIntoProperty();

		/**
		 * The meta object literal for the '<em><b>Select</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING_RESOURCE_TYPE__SELECT = eINSTANCE.getMappingResourceType_Select();

		/**
		 * The meta object literal for the '<em><b>Headers</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAPPING_RESOURCE_TYPE__HEADERS = eINSTANCE.getMappingResourceType_Headers();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAPPING_RESOURCE_TYPE__ID = eINSTANCE.getMappingResourceType_Id();

		/**
		 * The meta object literal for the '<em><b>Struct</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAPPING_RESOURCE_TYPE__STRUCT = eINSTANCE.getMappingResourceType_Struct();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MappingSimpleTypeImpl <em>Mapping Simple Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MappingSimpleTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMappingSimpleType()
		 * @generated
		 */
		EClass MAPPING_SIMPLE_TYPE = eINSTANCE.getMappingSimpleType();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapReferenceElementTypeImpl <em>Map Reference Element Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapReferenceElementTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapReferenceElementType()
		 * @generated
		 */
		EClass MAP_REFERENCE_ELEMENT_TYPE = eINSTANCE.getMapReferenceElementType();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapReferenceMemberTypeImpl <em>Map Reference Member Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapReferenceMemberTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapReferenceMemberType()
		 * @generated
		 */
		EClass MAP_REFERENCE_MEMBER_TYPE = eINSTANCE.getMapReferenceMemberType();

		/**
		 * The meta object literal for the '<em><b>Member</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_REFERENCE_MEMBER_TYPE__MEMBER = eINSTANCE.getMapReferenceMemberType_Member();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_REFERENCE_MEMBER_TYPE__PROPERTY = eINSTANCE.getMapReferenceMemberType_Property();

		/**
		 * The meta object literal for the '<em><b>Suppress Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_REFERENCE_MEMBER_TYPE__SUPPRESS_DEFAULT = eINSTANCE.getMapReferenceMemberType_SuppressDefault();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapReferenceTypeTypeImpl <em>Map Reference Type Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapReferenceTypeTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapReferenceTypeType()
		 * @generated
		 */
		EClass MAP_REFERENCE_TYPE_TYPE = eINSTANCE.getMapReferenceTypeType();

		/**
		 * The meta object literal for the '<em><b>Type Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_REFERENCE_TYPE_TYPE__TYPE_ID = eINSTANCE.getMapReferenceTypeType_TypeId();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapResourceElementTypeImpl <em>Map Resource Element Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapResourceElementTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapResourceElementType()
		 * @generated
		 */
		EClass MAP_RESOURCE_ELEMENT_TYPE = eINSTANCE.getMapResourceElementType();

		/**
		 * The meta object literal for the '<em><b>Instance Identifying Member</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_RESOURCE_ELEMENT_TYPE__INSTANCE_IDENTIFYING_MEMBER = eINSTANCE.getMapResourceElementType_InstanceIdentifyingMember();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapResourceMemberTypeImpl <em>Map Resource Member Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapResourceMemberTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapResourceMemberType()
		 * @generated
		 */
		EClass MAP_RESOURCE_MEMBER_TYPE = eINSTANCE.getMapResourceMemberType();

		/**
		 * The meta object literal for the '<em><b>Member</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_RESOURCE_MEMBER_TYPE__MEMBER = eINSTANCE.getMapResourceMemberType_Member();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_RESOURCE_MEMBER_TYPE__PROPERTY = eINSTANCE.getMapResourceMemberType_Property();

		/**
		 * The meta object literal for the '<em><b>Suppress Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_RESOURCE_MEMBER_TYPE__SUPPRESS_DEFAULT = eINSTANCE.getMapResourceMemberType_SuppressDefault();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapResourceTypeImpl <em>Map Resource Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapResourceTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapResourceType()
		 * @generated
		 */
		EClass MAP_RESOURCE_TYPE = eINSTANCE.getMapResourceType();

		/**
		 * The meta object literal for the '<em><b>Base Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_RESOURCE_TYPE__BASE_NAME = eINSTANCE.getMapResourceType_BaseName();

		/**
		 * The meta object literal for the '<em><b>Rss File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_RESOURCE_TYPE__RSS_FILE = eINSTANCE.getMapResourceType_RssFile();

		/**
		 * The meta object literal for the '<em><b>Standalone</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_RESOURCE_TYPE__STANDALONE = eINSTANCE.getMapResourceType_Standalone();

		/**
		 * The meta object literal for the '<em><b>Unnamed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_RESOURCE_TYPE__UNNAMED = eINSTANCE.getMapResourceType_Unnamed();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapResourceTypeTypeImpl <em>Map Resource Type Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapResourceTypeTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapResourceTypeType()
		 * @generated
		 */
		EClass MAP_RESOURCE_TYPE_TYPE = eINSTANCE.getMapResourceTypeType();

		/**
		 * The meta object literal for the '<em><b>Type Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_RESOURCE_TYPE_TYPE__TYPE_ID = eINSTANCE.getMapResourceTypeType_TypeId();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapSimpleElementTypeImpl <em>Map Simple Element Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapSimpleElementTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapSimpleElementType()
		 * @generated
		 */
		EClass MAP_SIMPLE_ELEMENT_TYPE = eINSTANCE.getMapSimpleElementType();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapSimpleMemberTypeImpl <em>Map Simple Member Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapSimpleMemberTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapSimpleMemberType()
		 * @generated
		 */
		EClass MAP_SIMPLE_MEMBER_TYPE = eINSTANCE.getMapSimpleMemberType();

		/**
		 * The meta object literal for the '<em><b>Member</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_SIMPLE_MEMBER_TYPE__MEMBER = eINSTANCE.getMapSimpleMemberType_Member();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_SIMPLE_MEMBER_TYPE__PROPERTY = eINSTANCE.getMapSimpleMemberType_Property();

		/**
		 * The meta object literal for the '<em><b>Suppress Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_SIMPLE_MEMBER_TYPE__SUPPRESS_DEFAULT = eINSTANCE.getMapSimpleMemberType_SuppressDefault();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.MapSimpleTypeTypeImpl <em>Map Simple Type Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.MapSimpleTypeTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getMapSimpleTypeType()
		 * @generated
		 */
		EClass MAP_SIMPLE_TYPE_TYPE = eINSTANCE.getMapSimpleTypeType();

		/**
		 * The meta object literal for the '<em><b>Type Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_SIMPLE_TYPE_TYPE__TYPE_ID = eINSTANCE.getMapSimpleTypeType_TypeId();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.PropertiesTypeImpl <em>Properties Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.PropertiesTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getPropertiesType()
		 * @generated
		 */
		EClass PROPERTIES_TYPE = eINSTANCE.getPropertiesType();

		/**
		 * The meta object literal for the '<em><b>Abstract Property Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTIES_TYPE__ABSTRACT_PROPERTY_GROUP = eINSTANCE.getPropertiesType_AbstractPropertyGroup();

		/**
		 * The meta object literal for the '<em><b>Abstract Property</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTIES_TYPE__ABSTRACT_PROPERTY = eINSTANCE.getPropertiesType_AbstractProperty();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.PropertyOverridesTypeImpl <em>Property Overrides Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.PropertyOverridesTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getPropertyOverridesType()
		 * @generated
		 */
		EClass PROPERTY_OVERRIDES_TYPE = eINSTANCE.getPropertyOverridesType();

		/**
		 * The meta object literal for the '<em><b>Property Override</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_OVERRIDES_TYPE__PROPERTY_OVERRIDE = eINSTANCE.getPropertyOverridesType_PropertyOverride();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.PropertyOverrideTypeImpl <em>Property Override Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.PropertyOverrideTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getPropertyOverrideType()
		 * @generated
		 */
		EClass PROPERTY_OVERRIDE_TYPE = eINSTANCE.getPropertyOverrideType();

		/**
		 * The meta object literal for the '<em><b>Category</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_OVERRIDE_TYPE__CATEGORY = eINSTANCE.getPropertyOverrideType_Category();

		/**
		 * The meta object literal for the '<em><b>Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_OVERRIDE_TYPE__DEFAULT = eINSTANCE.getPropertyOverrideType_Default();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_OVERRIDE_TYPE__NAME = eINSTANCE.getPropertyOverrideType_Name();

		/**
		 * The meta object literal for the '<em><b>Read Only</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_OVERRIDE_TYPE__READ_ONLY = eINSTANCE.getPropertyOverrideType_ReadOnly();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.ScriptTypeImpl <em>Script Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.ScriptTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getScriptType()
		 * @generated
		 */
		EClass SCRIPT_TYPE = eINSTANCE.getScriptType();

		/**
		 * The meta object literal for the '<em><b>File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCRIPT_TYPE__FILE = eINSTANCE.getScriptType_File();

		/**
		 * The meta object literal for the '<em><b>Prototype</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCRIPT_TYPE__PROTOTYPE = eINSTANCE.getScriptType_Prototype();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.SelectTypeImpl <em>Select Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.SelectTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getSelectType()
		 * @generated
		 */
		EClass SELECT_TYPE = eINSTANCE.getSelectType();

		/**
		 * The meta object literal for the '<em><b>Choice</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SELECT_TYPE__CHOICE = eINSTANCE.getSelectType_Choice();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SELECT_TYPE__ATTRIBUTE = eINSTANCE.getSelectType_Attribute();

		/**
		 * The meta object literal for the '<em><b>Is Component Instance Of</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SELECT_TYPE__IS_COMPONENT_INSTANCE_OF = eINSTANCE.getSelectType_IsComponentInstanceOf();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SELECT_TYPE__PROPERTY = eINSTANCE.getSelectType_Property();

		/**
		 * The meta object literal for the '<em><b>Property Exists</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SELECT_TYPE__PROPERTY_EXISTS = eINSTANCE.getSelectType_PropertyExists();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.SimplePropertyTypeImpl <em>Simple Property Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.SimplePropertyTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getSimplePropertyType()
		 * @generated
		 */
		EClass SIMPLE_PROPERTY_TYPE = eINSTANCE.getSimplePropertyType();

		/**
		 * The meta object literal for the '<em><b>Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_PROPERTY_TYPE__DEFAULT = eINSTANCE.getSimplePropertyType_Default();

		/**
		 * The meta object literal for the '<em><b>Extend With Enum</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_PROPERTY_TYPE__EXTEND_WITH_ENUM = eINSTANCE.getSimplePropertyType_ExtendWithEnum();

		/**
		 * The meta object literal for the '<em><b>Max Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_PROPERTY_TYPE__MAX_VALUE = eINSTANCE.getSimplePropertyType_MaxValue();

		/**
		 * The meta object literal for the '<em><b>Min Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_PROPERTY_TYPE__MIN_VALUE = eINSTANCE.getSimplePropertyType_MinValue();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_PROPERTY_TYPE__TYPE = eINSTANCE.getSimplePropertyType_Type();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.SourceGenTypeImpl <em>Source Gen Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.SourceGenTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getSourceGenType()
		 * @generated
		 */
		EClass SOURCE_GEN_TYPE = eINSTANCE.getSourceGenType();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOURCE_GEN_TYPE__GROUP = eINSTANCE.getSourceGenType_Group();

		/**
		 * The meta object literal for the '<em><b>Define Location</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_GEN_TYPE__DEFINE_LOCATION = eINSTANCE.getSourceGenType_DefineLocation();

		/**
		 * The meta object literal for the '<em><b>Template</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_GEN_TYPE__TEMPLATE = eINSTANCE.getSourceGenType_Template();

		/**
		 * The meta object literal for the '<em><b>Template Group</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_GEN_TYPE__TEMPLATE_GROUP = eINSTANCE.getSourceGenType_TemplateGroup();

		/**
		 * The meta object literal for the '<em><b>Use Template</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_GEN_TYPE__USE_TEMPLATE = eINSTANCE.getSourceGenType_UseTemplate();

		/**
		 * The meta object literal for the '<em><b>Use Template Group</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_GEN_TYPE__USE_TEMPLATE_GROUP = eINSTANCE.getSourceGenType_UseTemplateGroup();

		/**
		 * The meta object literal for the '<em><b>Inline</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_GEN_TYPE__INLINE = eINSTANCE.getSourceGenType_Inline();

		/**
		 * The meta object literal for the '<em><b>Script</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_GEN_TYPE__SCRIPT = eINSTANCE.getSourceGenType_Script();

		/**
		 * The meta object literal for the '<em><b>Define Macro</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_GEN_TYPE__DEFINE_MACRO = eINSTANCE.getSourceGenType_DefineMacro();

		/**
		 * The meta object literal for the '<em><b>Expand Macro</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_GEN_TYPE__EXPAND_MACRO = eINSTANCE.getSourceGenType_ExpandMacro();

		/**
		 * The meta object literal for the '<em><b>Debug</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOURCE_GEN_TYPE__DEBUG = eINSTANCE.getSourceGenType_Debug();

		/**
		 * The meta object literal for the '<em><b>Forms</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOURCE_GEN_TYPE__FORMS = eINSTANCE.getSourceGenType_Forms();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.SourceMappingTypeImpl <em>Source Mapping Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.SourceMappingTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getSourceMappingType()
		 * @generated
		 */
		EClass SOURCE_MAPPING_TYPE = eINSTANCE.getSourceMappingType();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOURCE_MAPPING_TYPE__GROUP = eINSTANCE.getSourceMappingType_Group();

		/**
		 * The meta object literal for the '<em><b>Map Resource</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_MAPPING_TYPE__MAP_RESOURCE = eINSTANCE.getSourceMappingType_MapResource();

		/**
		 * The meta object literal for the '<em><b>Select</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_MAPPING_TYPE__SELECT = eINSTANCE.getSourceMappingType_Select();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.SourceTypeMappingTypeImpl <em>Source Type Mapping Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.SourceTypeMappingTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getSourceTypeMappingType()
		 * @generated
		 */
		EClass SOURCE_TYPE_MAPPING_TYPE = eINSTANCE.getSourceTypeMappingType();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOURCE_TYPE_MAPPING_TYPE__GROUP = eINSTANCE.getSourceTypeMappingType_Group();

		/**
		 * The meta object literal for the '<em><b>Map Resource Type</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_TYPE_MAPPING_TYPE__MAP_RESOURCE_TYPE = eINSTANCE.getSourceTypeMappingType_MapResourceType();

		/**
		 * The meta object literal for the '<em><b>Map Enum Type</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_TYPE_MAPPING_TYPE__MAP_ENUM_TYPE = eINSTANCE.getSourceTypeMappingType_MapEnumType();

		/**
		 * The meta object literal for the '<em><b>Map Simple Type</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_TYPE_MAPPING_TYPE__MAP_SIMPLE_TYPE = eINSTANCE.getSourceTypeMappingType_MapSimpleType();

		/**
		 * The meta object literal for the '<em><b>Map Fixed Type</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_TYPE_MAPPING_TYPE__MAP_FIXED_TYPE = eINSTANCE.getSourceTypeMappingType_MapFixedType();

		/**
		 * The meta object literal for the '<em><b>Map Bitmask Type</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_TYPE_MAPPING_TYPE__MAP_BITMASK_TYPE = eINSTANCE.getSourceTypeMappingType_MapBitmaskType();

		/**
		 * The meta object literal for the '<em><b>Map Identifier Type</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_TYPE_MAPPING_TYPE__MAP_IDENTIFIER_TYPE = eINSTANCE.getSourceTypeMappingType_MapIdentifierType();

		/**
		 * The meta object literal for the '<em><b>Map Reference Type</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_TYPE_MAPPING_TYPE__MAP_REFERENCE_TYPE = eINSTANCE.getSourceTypeMappingType_MapReferenceType();

		/**
		 * The meta object literal for the '<em><b>Map Array Type</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_TYPE_MAPPING_TYPE__MAP_ARRAY_TYPE = eINSTANCE.getSourceTypeMappingType_MapArrayType();

		/**
		 * The meta object literal for the '<em><b>Select</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_TYPE_MAPPING_TYPE__SELECT = eINSTANCE.getSourceTypeMappingType_Select();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.SymbianTypeImpl <em>Symbian Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.SymbianTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getSymbianType()
		 * @generated
		 */
		EClass SYMBIAN_TYPE = eINSTANCE.getSymbianType();

		/**
		 * The meta object literal for the '<em><b>Class Help Topic</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYMBIAN_TYPE__CLASS_HELP_TOPIC = eINSTANCE.getSymbianType_ClassHelpTopic();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYMBIAN_TYPE__CLASS_NAME = eINSTANCE.getSymbianType_ClassName();

		/**
		 * The meta object literal for the '<em><b>Max SDK Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYMBIAN_TYPE__MAX_SDK_VERSION = eINSTANCE.getSymbianType_MaxSDKVersion();

		/**
		 * The meta object literal for the '<em><b>Min SDK Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYMBIAN_TYPE__MIN_SDK_VERSION = eINSTANCE.getSymbianType_MinSDKVersion();

		/**
		 * The meta object literal for the '<em><b>Resource Help Topic</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYMBIAN_TYPE__RESOURCE_HELP_TOPIC = eINSTANCE.getSymbianType_ResourceHelpTopic();

		/**
		 * The meta object literal for the '<em><b>Resource Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYMBIAN_TYPE__RESOURCE_TYPE = eINSTANCE.getSymbianType_ResourceType();

		/**
		 * The meta object literal for the '<em><b>Sdk Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYMBIAN_TYPE__SDK_NAME = eINSTANCE.getSymbianType_SdkName();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.TemplateGroupTypeImpl <em>Template Group Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.TemplateGroupTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getTemplateGroupType()
		 * @generated
		 */
		EClass TEMPLATE_GROUP_TYPE = eINSTANCE.getTemplateGroupType();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEMPLATE_GROUP_TYPE__GROUP = eINSTANCE.getTemplateGroupType_Group();

		/**
		 * The meta object literal for the '<em><b>Define Location</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEMPLATE_GROUP_TYPE__DEFINE_LOCATION = eINSTANCE.getTemplateGroupType_DefineLocation();

		/**
		 * The meta object literal for the '<em><b>Template</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEMPLATE_GROUP_TYPE__TEMPLATE = eINSTANCE.getTemplateGroupType_Template();

		/**
		 * The meta object literal for the '<em><b>Inline</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEMPLATE_GROUP_TYPE__INLINE = eINSTANCE.getTemplateGroupType_Inline();

		/**
		 * The meta object literal for the '<em><b>Use Template</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEMPLATE_GROUP_TYPE__USE_TEMPLATE = eINSTANCE.getTemplateGroupType_UseTemplate();

		/**
		 * The meta object literal for the '<em><b>Use Template Group</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEMPLATE_GROUP_TYPE__USE_TEMPLATE_GROUP = eINSTANCE.getTemplateGroupType_UseTemplateGroup();

		/**
		 * The meta object literal for the '<em><b>Expand Macro</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEMPLATE_GROUP_TYPE__EXPAND_MACRO = eINSTANCE.getTemplateGroupType_ExpandMacro();

		/**
		 * The meta object literal for the '<em><b>Form</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEMPLATE_GROUP_TYPE__FORM = eINSTANCE.getTemplateGroupType_Form();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEMPLATE_GROUP_TYPE__ID = eINSTANCE.getTemplateGroupType_Id();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEMPLATE_GROUP_TYPE__LOCATION = eINSTANCE.getTemplateGroupType_Location();

		/**
		 * The meta object literal for the '<em><b>Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEMPLATE_GROUP_TYPE__MODE = eINSTANCE.getTemplateGroupType_Mode();

		/**
		 * The meta object literal for the '<em><b>Phase</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEMPLATE_GROUP_TYPE__PHASE = eINSTANCE.getTemplateGroupType_Phase();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.TemplateTypeImpl <em>Template Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.TemplateTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getTemplateType()
		 * @generated
		 */
		EClass TEMPLATE_TYPE = eINSTANCE.getTemplateType();

		/**
		 * The meta object literal for the '<em><b>Form</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEMPLATE_TYPE__FORM = eINSTANCE.getTemplateType_Form();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEMPLATE_TYPE__ID = eINSTANCE.getTemplateType_Id();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEMPLATE_TYPE__LOCATION = eINSTANCE.getTemplateType_Location();

		/**
		 * The meta object literal for the '<em><b>Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEMPLATE_TYPE__MODE = eINSTANCE.getTemplateType_Mode();

		/**
		 * The meta object literal for the '<em><b>Phase</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEMPLATE_TYPE__PHASE = eINSTANCE.getTemplateType_Phase();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.TwoWayMappingTypeImpl <em>Two Way Mapping Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.TwoWayMappingTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getTwoWayMappingType()
		 * @generated
		 */
		EClass TWO_WAY_MAPPING_TYPE = eINSTANCE.getTwoWayMappingType();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.UseTemplateGroupTypeImpl <em>Use Template Group Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.UseTemplateGroupTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getUseTemplateGroupType()
		 * @generated
		 */
		EClass USE_TEMPLATE_GROUP_TYPE = eINSTANCE.getUseTemplateGroupType();

		/**
		 * The meta object literal for the '<em><b>Use Template</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference USE_TEMPLATE_GROUP_TYPE__USE_TEMPLATE = eINSTANCE.getUseTemplateGroupType_UseTemplate();

		/**
		 * The meta object literal for the '<em><b>Ids</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USE_TEMPLATE_GROUP_TYPE__IDS = eINSTANCE.getUseTemplateGroupType_Ids();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.impl.UseTemplateTypeImpl <em>Use Template Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.impl.UseTemplateTypeImpl
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getUseTemplateType()
		 * @generated
		 */
		EClass USE_TEMPLATE_TYPE = eINSTANCE.getUseTemplateType();

		/**
		 * The meta object literal for the '<em><b>Ids</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USE_TEMPLATE_TYPE__IDS = eINSTANCE.getUseTemplateType_Ids();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.PropertyDataType <em>Property Data Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.PropertyDataType
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getPropertyDataType()
		 * @generated
		 */
		EEnum PROPERTY_DATA_TYPE = eINSTANCE.getPropertyDataType();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.ReferenceScopeType <em>Reference Scope Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.ReferenceScopeType
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getReferenceScopeType()
		 * @generated
		 */
		EEnum REFERENCE_SCOPE_TYPE = eINSTANCE.getReferenceScopeType();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.component.StandaloneType <em>Standalone Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.StandaloneType
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getStandaloneType()
		 * @generated
		 */
		EEnum STANDALONE_TYPE = eINSTANCE.getStandaloneType();

		/**
		 * The meta object literal for the '<em>List Of Strings</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.util.List
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getListOfStrings()
		 * @generated
		 */
		EDataType LIST_OF_STRINGS = eINSTANCE.getListOfStrings();

		/**
		 * The meta object literal for the '<em>Property Data Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.PropertyDataType
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getPropertyDataTypeObject()
		 * @generated
		 */
		EDataType PROPERTY_DATA_TYPE_OBJECT = eINSTANCE.getPropertyDataTypeObject();

		/**
		 * The meta object literal for the '<em>Reference Scope Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.ReferenceScopeType
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getReferenceScopeTypeObject()
		 * @generated
		 */
		EDataType REFERENCE_SCOPE_TYPE_OBJECT = eINSTANCE.getReferenceScopeTypeObject();

		/**
		 * The meta object literal for the '<em>Standalone Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.component.StandaloneType
		 * @see com.nokia.sdt.emf.component.impl.ComponentPackageImpl#getStandaloneTypeObject()
		 * @generated
		 */
		EDataType STANDALONE_TYPE_OBJECT = eINSTANCE.getStandaloneTypeObject();

	}

} //ComponentPackage
