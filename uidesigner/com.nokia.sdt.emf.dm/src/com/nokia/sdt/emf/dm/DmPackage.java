/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.dm;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
 * @see com.nokia.sdt.emf.dm.DmFactory
 * @model kind="package"
 * @generated
 */
public interface DmPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "dm";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/nokia/sdt/emf/dm.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "com.nokia.sdt.emf.dm";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DmPackage eINSTANCE = com.nokia.sdt.emf.dm.impl.DmPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.dm.impl.IDesignerDataImpl <em>IDesigner Data</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.dm.impl.IDesignerDataImpl
	 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIDesignerData()
	 * @generated
	 */
	int IDESIGNER_DATA = 0;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDESIGNER_DATA__VERSION = 0;

	/**
	 * The feature id for the '<em><b>Component Manifest</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDESIGNER_DATA__COMPONENT_MANIFEST = 1;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDESIGNER_DATA__PROPERTIES = 2;

	/**
	 * The feature id for the '<em><b>Root Containers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDESIGNER_DATA__ROOT_CONTAINERS = 3;

	/**
	 * The feature id for the '<em><b>String Bundle</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDESIGNER_DATA__STRING_BUNDLE = 4;

	/**
	 * The feature id for the '<em><b>Macro Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDESIGNER_DATA__MACRO_TABLE = 5;

	/**
	 * The feature id for the '<em><b>Source Mapping State</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDESIGNER_DATA__SOURCE_MAPPING_STATE = 6;

	/**
	 * The feature id for the '<em><b>Generated Files</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDESIGNER_DATA__GENERATED_FILES = 7;

	/**
	 * The number of structural features of the '<em>IDesigner Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDESIGNER_DATA_FEATURE_COUNT = 8;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.dm.impl.INodeImpl <em>INode</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.dm.impl.INodeImpl
	 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getINode()
	 * @generated
	 */
	int INODE = 1;

	/**
	 * The feature id for the '<em><b>Component Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INODE__COMPONENT_ID = 0;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INODE__PROPERTIES = 1;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INODE__CHILDREN = 2;

	/**
	 * The feature id for the '<em><b>Event Bindings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INODE__EVENT_BINDINGS = 3;

	/**
	 * The number of structural features of the '<em>INode</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INODE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.dm.impl.IPropertyContainerImpl <em>IProperty Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.dm.impl.IPropertyContainerImpl
	 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIPropertyContainer()
	 * @generated
	 */
	int IPROPERTY_CONTAINER = 2;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IPROPERTY_CONTAINER__PROPERTIES = 0;

	/**
	 * The number of structural features of the '<em>IProperty Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IPROPERTY_CONTAINER_FEATURE_COUNT = 1;


	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.dm.impl.EStringToIPropertyValueMapEntryImpl <em>EString To IProperty Value Map Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.dm.impl.EStringToIPropertyValueMapEntryImpl
	 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getEStringToIPropertyValueMapEntry()
	 * @generated
	 */
	int ESTRING_TO_IPROPERTY_VALUE_MAP_ENTRY = 3;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_IPROPERTY_VALUE_MAP_ENTRY__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_IPROPERTY_VALUE_MAP_ENTRY__VALUE = 1;

	/**
	 * The number of structural features of the '<em>EString To IProperty Value Map Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_IPROPERTY_VALUE_MAP_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.dm.impl.IPropertyValueImpl <em>IProperty Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.dm.impl.IPropertyValueImpl
	 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIPropertyValue()
	 * @generated
	 */
	int IPROPERTY_VALUE = 4;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IPROPERTY_VALUE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>String Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IPROPERTY_VALUE__STRING_VALUE = 1;

	/**
	 * The feature id for the '<em><b>Compound Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IPROPERTY_VALUE__COMPOUND_VALUE = 2;

	/**
	 * The feature id for the '<em><b>Sequence Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IPROPERTY_VALUE__SEQUENCE_VALUE = 3;

	/**
	 * The number of structural features of the '<em>IProperty Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IPROPERTY_VALUE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.dm.impl.ILocalizedStringBundleImpl <em>ILocalized String Bundle</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.dm.impl.ILocalizedStringBundleImpl
	 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getILocalizedStringBundle()
	 * @generated
	 */
	int ILOCALIZED_STRING_BUNDLE = 5;

	/**
	 * The feature id for the '<em><b>Localized String Tables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ILOCALIZED_STRING_BUNDLE__LOCALIZED_STRING_TABLES = 0;

	/**
	 * The number of structural features of the '<em>ILocalized String Bundle</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ILOCALIZED_STRING_BUNDLE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.dm.impl.ILocalizedStringTableImpl <em>ILocalized String Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.dm.impl.ILocalizedStringTableImpl
	 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getILocalizedStringTable()
	 * @generated
	 */
	int ILOCALIZED_STRING_TABLE = 6;

	/**
	 * The feature id for the '<em><b>Strings</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ILOCALIZED_STRING_TABLE__STRINGS = 0;

	/**
	 * The feature id for the '<em><b>Language</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ILOCALIZED_STRING_TABLE__LANGUAGE = 1;

	/**
	 * The number of structural features of the '<em>ILocalized String Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ILOCALIZED_STRING_TABLE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.dm.impl.IMacroStringTableImpl <em>IMacro String Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.dm.impl.IMacroStringTableImpl
	 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIMacroStringTable()
	 * @generated
	 */
	int IMACRO_STRING_TABLE = 7;

	/**
	 * The feature id for the '<em><b>String Macros</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMACRO_STRING_TABLE__STRING_MACROS = 0;

	/**
	 * The number of structural features of the '<em>IMacro String Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMACRO_STRING_TABLE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.dm.impl.EStringToStringMapEntryImpl <em>EString To String Map Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.dm.impl.EStringToStringMapEntryImpl
	 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getEStringToStringMapEntry()
	 * @generated
	 */
	int ESTRING_TO_STRING_MAP_ENTRY = 8;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_STRING_MAP_ENTRY__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_STRING_MAP_ENTRY__VALUE = 1;

	/**
	 * The number of structural features of the '<em>EString To String Map Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_STRING_MAP_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.dm.impl.IEventBindingImpl <em>IEvent Binding</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.dm.impl.IEventBindingImpl
	 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIEventBinding()
	 * @generated
	 */
	int IEVENT_BINDING = 9;

	/**
	 * The feature id for the '<em><b>Event ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IEVENT_BINDING__EVENT_ID = 0;

	/**
	 * The feature id for the '<em><b>Event Handler Display Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IEVENT_BINDING__EVENT_HANDLER_DISPLAY_TEXT = 1;

	/**
	 * The feature id for the '<em><b>Event Handler Info</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IEVENT_BINDING__EVENT_HANDLER_INFO = 2;

	/**
	 * The number of structural features of the '<em>IEvent Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IEVENT_BINDING_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.dm.impl.ISourceGenMappingStateImpl <em>ISource Gen Mapping State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.dm.impl.ISourceGenMappingStateImpl
	 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getISourceGenMappingState()
	 * @generated
	 */
	int ISOURCE_GEN_MAPPING_STATE = 10;

	/**
	 * The feature id for the '<em><b>Resource Mappings</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISOURCE_GEN_MAPPING_STATE__RESOURCE_MAPPINGS = 0;

	/**
	 * The feature id for the '<em><b>Enum Mappings</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISOURCE_GEN_MAPPING_STATE__ENUM_MAPPINGS = 1;

	/**
	 * The feature id for the '<em><b>Array Mappings</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISOURCE_GEN_MAPPING_STATE__ARRAY_MAPPINGS = 2;

	/**
	 * The number of structural features of the '<em>ISource Gen Mapping State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISOURCE_GEN_MAPPING_STATE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.dm.impl.IResourceMappingsImpl <em>IResource Mappings</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.dm.impl.IResourceMappingsImpl
	 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIResourceMappings()
	 * @generated
	 */
	int IRESOURCE_MAPPINGS = 11;

	/**
	 * The feature id for the '<em><b>Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IRESOURCE_MAPPINGS__MAPPINGS = 0;

	/**
	 * The number of structural features of the '<em>IResource Mappings</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IRESOURCE_MAPPINGS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.dm.impl.IResourceMappingImpl <em>IResource Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.dm.impl.IResourceMappingImpl
	 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIResourceMapping()
	 * @generated
	 */
	int IRESOURCE_MAPPING = 12;

	/**
	 * The feature id for the '<em><b>Instance Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IRESOURCE_MAPPING__INSTANCE_NAME = 0;

	/**
	 * The feature id for the '<em><b>Rsrc File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IRESOURCE_MAPPING__RSRC_FILE = 1;

	/**
	 * The feature id for the '<em><b>Rsrc Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IRESOURCE_MAPPING__RSRC_ID = 2;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IRESOURCE_MAPPING__VALUE = 3;

	/**
	 * The number of structural features of the '<em>IResource Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IRESOURCE_MAPPING_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.dm.impl.IEnumMappingsImpl <em>IEnum Mappings</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.dm.impl.IEnumMappingsImpl
	 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIEnumMappings()
	 * @generated
	 */
	int IENUM_MAPPINGS = 13;

	/**
	 * The feature id for the '<em><b>Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IENUM_MAPPINGS__MAPPINGS = 0;

	/**
	 * The number of structural features of the '<em>IEnum Mappings</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IENUM_MAPPINGS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.dm.impl.IEnumMappingImpl <em>IEnum Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.dm.impl.IEnumMappingImpl
	 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIEnumMapping()
	 * @generated
	 */
	int IENUM_MAPPING = 14;

	/**
	 * The feature id for the '<em><b>Instance Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IENUM_MAPPING__INSTANCE_NAME = 0;

	/**
	 * The feature id for the '<em><b>Property Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IENUM_MAPPING__PROPERTY_ID = 1;

	/**
	 * The feature id for the '<em><b>Name Algorithm</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IENUM_MAPPING__NAME_ALGORITHM = 2;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IENUM_MAPPING__VALUE = 3;

	/**
	 * The number of structural features of the '<em>IEnum Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IENUM_MAPPING_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.dm.impl.IArrayMappingsImpl <em>IArray Mappings</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.dm.impl.IArrayMappingsImpl
	 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIArrayMappings()
	 * @generated
	 */
	int IARRAY_MAPPINGS = 15;

	/**
	 * The feature id for the '<em><b>Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IARRAY_MAPPINGS__MAPPINGS = 0;

	/**
	 * The number of structural features of the '<em>IArray Mappings</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IARRAY_MAPPINGS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.dm.impl.IArrayMappingImpl <em>IArray Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.dm.impl.IArrayMappingImpl
	 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIArrayMapping()
	 * @generated
	 */
	int IARRAY_MAPPING = 16;

	/**
	 * The feature id for the '<em><b>Instance Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IARRAY_MAPPING__INSTANCE_NAME = 0;

	/**
	 * The feature id for the '<em><b>Property Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IARRAY_MAPPING__PROPERTY_ID = 1;

	/**
	 * The feature id for the '<em><b>Member Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IARRAY_MAPPING__MEMBER_ID = 2;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IARRAY_MAPPING__ELEMENTS = 3;

	/**
	 * The number of structural features of the '<em>IArray Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IARRAY_MAPPING_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.dm.impl.IElementMappingImpl <em>IElement Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.dm.impl.IElementMappingImpl
	 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIElementMapping()
	 * @generated
	 */
	int IELEMENT_MAPPING = 17;

	/**
	 * The feature id for the '<em><b>Unique Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IELEMENT_MAPPING__UNIQUE_VALUE = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IELEMENT_MAPPING__VALUE = 1;

	/**
	 * The number of structural features of the '<em>IElement Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IELEMENT_MAPPING_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.dm.impl.IComponentManifestImpl <em>IComponent Manifest</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.dm.impl.IComponentManifestImpl
	 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIComponentManifest()
	 * @generated
	 */
	int ICOMPONENT_MANIFEST = 18;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICOMPONENT_MANIFEST__ENTRIES = 0;

	/**
	 * The number of structural features of the '<em>IComponent Manifest</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICOMPONENT_MANIFEST_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.dm.impl.IComponentManifestEntryImpl <em>IComponent Manifest Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.dm.impl.IComponentManifestEntryImpl
	 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIComponentManifestEntry()
	 * @generated
	 */
	int ICOMPONENT_MANIFEST_ENTRY = 19;

	/**
	 * The feature id for the '<em><b>Component ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICOMPONENT_MANIFEST_ENTRY__COMPONENT_ID = 0;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICOMPONENT_MANIFEST_ENTRY__VERSION = 1;

	/**
	 * The number of structural features of the '<em>IComponent Manifest Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICOMPONENT_MANIFEST_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.nokia.sdt.emf.dm.impl.IGeneratedFilesImpl <em>IGenerated Files</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.dm.impl.IGeneratedFilesImpl
	 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIGeneratedFiles()
	 * @generated
	 */
	int IGENERATED_FILES = 20;

	/**
	 * The feature id for the '<em><b>Files</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IGENERATED_FILES__FILES = 0;

	/**
	 * The number of structural features of the '<em>IGenerated Files</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IGENERATED_FILES_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '<em>Language</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.dm.Language
	 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getLanguage()
	 * @generated
	 */
	int LANGUAGE = 21;


	/**
	 * The meta object id for the '<em>String Value</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.emf.dm.StringValue
	 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getStringValue()
	 * @generated
	 */
	int STRING_VALUE = 22;


	/**
	 * The meta object id for the '<em>Version</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.osgi.framework.Version
	 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getVersion()
	 * @generated
	 */
	int VERSION = 23;

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.dm.IDesignerData <em>IDesigner Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IDesigner Data</em>'.
	 * @see com.nokia.sdt.emf.dm.IDesignerData
	 * @generated
	 */
	EClass getIDesignerData();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.dm.IDesignerData#getStringBundle <em>String Bundle</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>String Bundle</em>'.
	 * @see com.nokia.sdt.emf.dm.IDesignerData#getStringBundle()
	 * @see #getIDesignerData()
	 * @generated
	 */
	EReference getIDesignerData_StringBundle();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.dm.IDesignerData#getMacroTable <em>Macro Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Macro Table</em>'.
	 * @see com.nokia.sdt.emf.dm.IDesignerData#getMacroTable()
	 * @see #getIDesignerData()
	 * @generated
	 */
	EReference getIDesignerData_MacroTable();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.dm.IDesignerData#getSourceMappingState <em>Source Mapping State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Source Mapping State</em>'.
	 * @see com.nokia.sdt.emf.dm.IDesignerData#getSourceMappingState()
	 * @see #getIDesignerData()
	 * @generated
	 */
	EReference getIDesignerData_SourceMappingState();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.dm.IDesignerData#getGeneratedFiles <em>Generated Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Generated Files</em>'.
	 * @see com.nokia.sdt.emf.dm.IDesignerData#getGeneratedFiles()
	 * @see #getIDesignerData()
	 * @generated
	 */
	EReference getIDesignerData_GeneratedFiles();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.dm.IDesignerData#getComponentManifest <em>Component Manifest</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Component Manifest</em>'.
	 * @see com.nokia.sdt.emf.dm.IDesignerData#getComponentManifest()
	 * @see #getIDesignerData()
	 * @generated
	 */
	EReference getIDesignerData_ComponentManifest();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.dm.IDesignerData#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see com.nokia.sdt.emf.dm.IDesignerData#getVersion()
	 * @see #getIDesignerData()
	 * @generated
	 */
	EAttribute getIDesignerData_Version();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.dm.IDesignerData#getRootContainers <em>Root Containers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Root Containers</em>'.
	 * @see com.nokia.sdt.emf.dm.IDesignerData#getRootContainers()
	 * @see #getIDesignerData()
	 * @generated
	 */
	EReference getIDesignerData_RootContainers();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.dm.IDesignerData#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Properties</em>'.
	 * @see com.nokia.sdt.emf.dm.IDesignerData#getProperties()
	 * @see #getIDesignerData()
	 * @generated
	 */
	EReference getIDesignerData_Properties();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.dm.INode <em>INode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>INode</em>'.
	 * @see com.nokia.sdt.emf.dm.INode
	 * @generated
	 */
	EClass getINode();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.dm.INode#getComponentId <em>Component Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Component Id</em>'.
	 * @see com.nokia.sdt.emf.dm.INode#getComponentId()
	 * @see #getINode()
	 * @generated
	 */
	EAttribute getINode_ComponentId();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.dm.INode#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Properties</em>'.
	 * @see com.nokia.sdt.emf.dm.INode#getProperties()
	 * @see #getINode()
	 * @generated
	 */
	EReference getINode_Properties();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.dm.INode#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Children</em>'.
	 * @see com.nokia.sdt.emf.dm.INode#getChildren()
	 * @see #getINode()
	 * @generated
	 */
	EReference getINode_Children();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.dm.INode#getEventBindings <em>Event Bindings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Event Bindings</em>'.
	 * @see com.nokia.sdt.emf.dm.INode#getEventBindings()
	 * @see #getINode()
	 * @generated
	 */
	EReference getINode_EventBindings();

	/**
	 * @model keyType="java.lang.String"
	 *        valueType="java.lang.String"
	 */
	EClass getEStringToStringMapEntry();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEStringToStringMapEntry()
	 * @generated
	 */
	EAttribute getEStringToStringMapEntry_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEStringToStringMapEntry()
	 * @generated
	 */
	EAttribute getEStringToStringMapEntry_Value();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.dm.IEventBinding <em>IEvent Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IEvent Binding</em>'.
	 * @see com.nokia.sdt.emf.dm.IEventBinding
	 * @generated
	 */
	EClass getIEventBinding();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.dm.IEventBinding#getEventID <em>Event ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Event ID</em>'.
	 * @see com.nokia.sdt.emf.dm.IEventBinding#getEventID()
	 * @see #getIEventBinding()
	 * @generated
	 */
	EAttribute getIEventBinding_EventID();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.dm.IEventBinding#getEventHandlerDisplayText <em>Event Handler Display Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Event Handler Display Text</em>'.
	 * @see com.nokia.sdt.emf.dm.IEventBinding#getEventHandlerDisplayText()
	 * @see #getIEventBinding()
	 * @generated
	 */
	EAttribute getIEventBinding_EventHandlerDisplayText();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.dm.IEventBinding#getEventHandlerInfo <em>Event Handler Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Event Handler Info</em>'.
	 * @see com.nokia.sdt.emf.dm.IEventBinding#getEventHandlerInfo()
	 * @see #getIEventBinding()
	 * @generated
	 */
	EAttribute getIEventBinding_EventHandlerInfo();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.dm.ISourceGenMappingState <em>ISource Gen Mapping State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ISource Gen Mapping State</em>'.
	 * @see com.nokia.sdt.emf.dm.ISourceGenMappingState
	 * @generated
	 */
	EClass getISourceGenMappingState();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.dm.ISourceGenMappingState#getResourceMappings <em>Resource Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Resource Mappings</em>'.
	 * @see com.nokia.sdt.emf.dm.ISourceGenMappingState#getResourceMappings()
	 * @see #getISourceGenMappingState()
	 * @generated
	 */
	EReference getISourceGenMappingState_ResourceMappings();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.dm.ISourceGenMappingState#getEnumMappings <em>Enum Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Enum Mappings</em>'.
	 * @see com.nokia.sdt.emf.dm.ISourceGenMappingState#getEnumMappings()
	 * @see #getISourceGenMappingState()
	 * @generated
	 */
	EReference getISourceGenMappingState_EnumMappings();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.dm.ISourceGenMappingState#getArrayMappings <em>Array Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Array Mappings</em>'.
	 * @see com.nokia.sdt.emf.dm.ISourceGenMappingState#getArrayMappings()
	 * @see #getISourceGenMappingState()
	 * @generated
	 */
	EReference getISourceGenMappingState_ArrayMappings();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.dm.IResourceMappings <em>IResource Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IResource Mappings</em>'.
	 * @see com.nokia.sdt.emf.dm.IResourceMappings
	 * @generated
	 */
	EClass getIResourceMappings();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.dm.IResourceMappings#getMappings <em>Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mappings</em>'.
	 * @see com.nokia.sdt.emf.dm.IResourceMappings#getMappings()
	 * @see #getIResourceMappings()
	 * @generated
	 */
	EReference getIResourceMappings_Mappings();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.dm.IResourceMapping <em>IResource Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IResource Mapping</em>'.
	 * @see com.nokia.sdt.emf.dm.IResourceMapping
	 * @generated
	 */
	EClass getIResourceMapping();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.dm.IResourceMapping#getInstanceName <em>Instance Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Instance Name</em>'.
	 * @see com.nokia.sdt.emf.dm.IResourceMapping#getInstanceName()
	 * @see #getIResourceMapping()
	 * @generated
	 */
	EAttribute getIResourceMapping_InstanceName();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.dm.IResourceMapping#getRsrcFile <em>Rsrc File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rsrc File</em>'.
	 * @see com.nokia.sdt.emf.dm.IResourceMapping#getRsrcFile()
	 * @see #getIResourceMapping()
	 * @generated
	 */
	EAttribute getIResourceMapping_RsrcFile();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.dm.IResourceMapping#getRsrcId <em>Rsrc Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rsrc Id</em>'.
	 * @see com.nokia.sdt.emf.dm.IResourceMapping#getRsrcId()
	 * @see #getIResourceMapping()
	 * @generated
	 */
	EAttribute getIResourceMapping_RsrcId();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.dm.IResourceMapping#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.nokia.sdt.emf.dm.IResourceMapping#getValue()
	 * @see #getIResourceMapping()
	 * @generated
	 */
	EAttribute getIResourceMapping_Value();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.dm.IEnumMappings <em>IEnum Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IEnum Mappings</em>'.
	 * @see com.nokia.sdt.emf.dm.IEnumMappings
	 * @generated
	 */
	EClass getIEnumMappings();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.dm.IEnumMappings#getMappings <em>Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mappings</em>'.
	 * @see com.nokia.sdt.emf.dm.IEnumMappings#getMappings()
	 * @see #getIEnumMappings()
	 * @generated
	 */
	EReference getIEnumMappings_Mappings();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.dm.IEnumMapping <em>IEnum Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IEnum Mapping</em>'.
	 * @see com.nokia.sdt.emf.dm.IEnumMapping
	 * @generated
	 */
	EClass getIEnumMapping();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.dm.IEnumMapping#getInstanceName <em>Instance Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Instance Name</em>'.
	 * @see com.nokia.sdt.emf.dm.IEnumMapping#getInstanceName()
	 * @see #getIEnumMapping()
	 * @generated
	 */
	EAttribute getIEnumMapping_InstanceName();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.dm.IEnumMapping#getPropertyId <em>Property Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property Id</em>'.
	 * @see com.nokia.sdt.emf.dm.IEnumMapping#getPropertyId()
	 * @see #getIEnumMapping()
	 * @generated
	 */
	EAttribute getIEnumMapping_PropertyId();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.dm.IEnumMapping#getNameAlgorithm <em>Name Algorithm</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name Algorithm</em>'.
	 * @see com.nokia.sdt.emf.dm.IEnumMapping#getNameAlgorithm()
	 * @see #getIEnumMapping()
	 * @generated
	 */
	EAttribute getIEnumMapping_NameAlgorithm();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.dm.IEnumMapping#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.nokia.sdt.emf.dm.IEnumMapping#getValue()
	 * @see #getIEnumMapping()
	 * @generated
	 */
	EAttribute getIEnumMapping_Value();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.dm.IArrayMappings <em>IArray Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IArray Mappings</em>'.
	 * @see com.nokia.sdt.emf.dm.IArrayMappings
	 * @generated
	 */
	EClass getIArrayMappings();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.dm.IArrayMappings#getMappings <em>Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mappings</em>'.
	 * @see com.nokia.sdt.emf.dm.IArrayMappings#getMappings()
	 * @see #getIArrayMappings()
	 * @generated
	 */
	EReference getIArrayMappings_Mappings();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.dm.IArrayMapping <em>IArray Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IArray Mapping</em>'.
	 * @see com.nokia.sdt.emf.dm.IArrayMapping
	 * @generated
	 */
	EClass getIArrayMapping();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.dm.IArrayMapping#getInstanceName <em>Instance Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Instance Name</em>'.
	 * @see com.nokia.sdt.emf.dm.IArrayMapping#getInstanceName()
	 * @see #getIArrayMapping()
	 * @generated
	 */
	EAttribute getIArrayMapping_InstanceName();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.dm.IArrayMapping#getPropertyId <em>Property Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property Id</em>'.
	 * @see com.nokia.sdt.emf.dm.IArrayMapping#getPropertyId()
	 * @see #getIArrayMapping()
	 * @generated
	 */
	EAttribute getIArrayMapping_PropertyId();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.dm.IArrayMapping#getMemberId <em>Member Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Member Id</em>'.
	 * @see com.nokia.sdt.emf.dm.IArrayMapping#getMemberId()
	 * @see #getIArrayMapping()
	 * @generated
	 */
	EAttribute getIArrayMapping_MemberId();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.dm.IArrayMapping#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Elements</em>'.
	 * @see com.nokia.sdt.emf.dm.IArrayMapping#getElements()
	 * @see #getIArrayMapping()
	 * @generated
	 */
	EReference getIArrayMapping_Elements();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.dm.IElementMapping <em>IElement Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IElement Mapping</em>'.
	 * @see com.nokia.sdt.emf.dm.IElementMapping
	 * @generated
	 */
	EClass getIElementMapping();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.dm.IElementMapping#getUniqueValue <em>Unique Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Value</em>'.
	 * @see com.nokia.sdt.emf.dm.IElementMapping#getUniqueValue()
	 * @see #getIElementMapping()
	 * @generated
	 */
	EAttribute getIElementMapping_UniqueValue();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.dm.IElementMapping#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.nokia.sdt.emf.dm.IElementMapping#getValue()
	 * @see #getIElementMapping()
	 * @generated
	 */
	EAttribute getIElementMapping_Value();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.dm.IComponentManifest <em>IComponent Manifest</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IComponent Manifest</em>'.
	 * @see com.nokia.sdt.emf.dm.IComponentManifest
	 * @generated
	 */
	EClass getIComponentManifest();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.dm.IComponentManifest#getEntries <em>Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entries</em>'.
	 * @see com.nokia.sdt.emf.dm.IComponentManifest#getEntries()
	 * @see #getIComponentManifest()
	 * @generated
	 */
	EReference getIComponentManifest_Entries();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.dm.IComponentManifestEntry <em>IComponent Manifest Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IComponent Manifest Entry</em>'.
	 * @see com.nokia.sdt.emf.dm.IComponentManifestEntry
	 * @generated
	 */
	EClass getIComponentManifestEntry();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.dm.IComponentManifestEntry#getComponentID <em>Component ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Component ID</em>'.
	 * @see com.nokia.sdt.emf.dm.IComponentManifestEntry#getComponentID()
	 * @see #getIComponentManifestEntry()
	 * @generated
	 */
	EAttribute getIComponentManifestEntry_ComponentID();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.dm.IComponentManifestEntry#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see com.nokia.sdt.emf.dm.IComponentManifestEntry#getVersion()
	 * @see #getIComponentManifestEntry()
	 * @generated
	 */
	EAttribute getIComponentManifestEntry_Version();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.dm.IGeneratedFiles <em>IGenerated Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IGenerated Files</em>'.
	 * @see com.nokia.sdt.emf.dm.IGeneratedFiles
	 * @generated
	 */
	EClass getIGeneratedFiles();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.sdt.emf.dm.IGeneratedFiles#getFiles <em>Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Files</em>'.
	 * @see com.nokia.sdt.emf.dm.IGeneratedFiles#getFiles()
	 * @see #getIGeneratedFiles()
	 * @generated
	 */
	EAttribute getIGeneratedFiles_Files();

	/**
	 * @model keyType="java.lang.String"
	 *        valueType="com.nokia.sdt.emf.dm.IPropertyValue"
	 *        
	 */
	EClass getEStringToIPropertyValueMapEntry();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEStringToIPropertyValueMapEntry()
	 * @generated
	 */
	EReference getEStringToIPropertyValueMapEntry_Value();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.dm.IPropertyValue <em>IProperty Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IProperty Value</em>'.
	 * @see com.nokia.sdt.emf.dm.IPropertyValue
	 * @generated
	 */
	EClass getIPropertyValue();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.dm.IPropertyValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.nokia.sdt.emf.dm.IPropertyValue#getValue()
	 * @see #getIPropertyValue()
	 * @generated
	 */
	EAttribute getIPropertyValue_Value();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.dm.IPropertyValue#getStringValue <em>String Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>String Value</em>'.
	 * @see com.nokia.sdt.emf.dm.IPropertyValue#getStringValue()
	 * @see #getIPropertyValue()
	 * @generated
	 */
	EAttribute getIPropertyValue_StringValue();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.emf.dm.IPropertyValue#getCompoundValue <em>Compound Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Compound Value</em>'.
	 * @see com.nokia.sdt.emf.dm.IPropertyValue#getCompoundValue()
	 * @see #getIPropertyValue()
	 * @generated
	 */
	EReference getIPropertyValue_CompoundValue();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.dm.IPropertyValue#getSequenceValue <em>Sequence Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sequence Value</em>'.
	 * @see com.nokia.sdt.emf.dm.IPropertyValue#getSequenceValue()
	 * @see #getIPropertyValue()
	 * @generated
	 */
	EReference getIPropertyValue_SequenceValue();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.dm.ILocalizedStringBundle <em>ILocalized String Bundle</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ILocalized String Bundle</em>'.
	 * @see com.nokia.sdt.emf.dm.ILocalizedStringBundle
	 * @generated
	 */
	EClass getILocalizedStringBundle();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.emf.dm.ILocalizedStringBundle#getLocalizedStringTables <em>Localized String Tables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Localized String Tables</em>'.
	 * @see com.nokia.sdt.emf.dm.ILocalizedStringBundle#getLocalizedStringTables()
	 * @see #getILocalizedStringBundle()
	 * @generated
	 */
	EReference getILocalizedStringBundle_LocalizedStringTables();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.dm.ILocalizedStringTable <em>ILocalized String Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ILocalized String Table</em>'.
	 * @see com.nokia.sdt.emf.dm.ILocalizedStringTable
	 * @generated
	 */
	EClass getILocalizedStringTable();

	/**
	 * Returns the meta object for the map '{@link com.nokia.sdt.emf.dm.ILocalizedStringTable#getStrings <em>Strings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Strings</em>'.
	 * @see com.nokia.sdt.emf.dm.ILocalizedStringTable#getStrings()
	 * @see #getILocalizedStringTable()
	 * @generated
	 */
	EReference getILocalizedStringTable_Strings();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.emf.dm.ILocalizedStringTable#getLanguage <em>Language</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Language</em>'.
	 * @see com.nokia.sdt.emf.dm.ILocalizedStringTable#getLanguage()
	 * @see #getILocalizedStringTable()
	 * @generated
	 */
	EAttribute getILocalizedStringTable_Language();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEStringToIPropertyValueMapEntry()
	 * @generated
	 */
	EAttribute getEStringToIPropertyValueMapEntry_Key();

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.dm.IPropertyContainer <em>IProperty Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IProperty Container</em>'.
	 * @see com.nokia.sdt.emf.dm.IPropertyContainer
	 * @generated
	 */
	EClass getIPropertyContainer();

	/**
	 * Returns the meta object for the map '{@link com.nokia.sdt.emf.dm.IPropertyContainer#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Properties</em>'.
	 * @see com.nokia.sdt.emf.dm.IPropertyContainer#getProperties()
	 * @see #getIPropertyContainer()
	 * @generated
	 */
	EReference getIPropertyContainer_Properties();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DmFactory getDmFactory();

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
	interface Literals  {
		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.dm.impl.IDesignerDataImpl <em>IDesigner Data</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.dm.impl.IDesignerDataImpl
		 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIDesignerData()
		 * @generated
		 */
		EClass IDESIGNER_DATA = eINSTANCE.getIDesignerData();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDESIGNER_DATA__VERSION = eINSTANCE.getIDesignerData_Version();

		/**
		 * The meta object literal for the '<em><b>Component Manifest</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IDESIGNER_DATA__COMPONENT_MANIFEST = eINSTANCE.getIDesignerData_ComponentManifest();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IDESIGNER_DATA__PROPERTIES = eINSTANCE.getIDesignerData_Properties();

		/**
		 * The meta object literal for the '<em><b>Root Containers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IDESIGNER_DATA__ROOT_CONTAINERS = eINSTANCE.getIDesignerData_RootContainers();

		/**
		 * The meta object literal for the '<em><b>String Bundle</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IDESIGNER_DATA__STRING_BUNDLE = eINSTANCE.getIDesignerData_StringBundle();

		/**
		 * The meta object literal for the '<em><b>Macro Table</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IDESIGNER_DATA__MACRO_TABLE = eINSTANCE.getIDesignerData_MacroTable();

		/**
		 * The meta object literal for the '<em><b>Source Mapping State</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IDESIGNER_DATA__SOURCE_MAPPING_STATE = eINSTANCE.getIDesignerData_SourceMappingState();

		/**
		 * The meta object literal for the '<em><b>Generated Files</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IDESIGNER_DATA__GENERATED_FILES = eINSTANCE.getIDesignerData_GeneratedFiles();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.dm.impl.INodeImpl <em>INode</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.dm.impl.INodeImpl
		 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getINode()
		 * @generated
		 */
		EClass INODE = eINSTANCE.getINode();

		/**
		 * The meta object literal for the '<em><b>Component Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INODE__COMPONENT_ID = eINSTANCE.getINode_ComponentId();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INODE__PROPERTIES = eINSTANCE.getINode_Properties();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INODE__CHILDREN = eINSTANCE.getINode_Children();

		/**
		 * The meta object literal for the '<em><b>Event Bindings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INODE__EVENT_BINDINGS = eINSTANCE.getINode_EventBindings();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.dm.impl.IPropertyContainerImpl <em>IProperty Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.dm.impl.IPropertyContainerImpl
		 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIPropertyContainer()
		 * @generated
		 */
		EClass IPROPERTY_CONTAINER = eINSTANCE.getIPropertyContainer();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IPROPERTY_CONTAINER__PROPERTIES = eINSTANCE.getIPropertyContainer_Properties();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.dm.impl.EStringToIPropertyValueMapEntryImpl <em>EString To IProperty Value Map Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.dm.impl.EStringToIPropertyValueMapEntryImpl
		 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getEStringToIPropertyValueMapEntry()
		 * @generated
		 */
		EClass ESTRING_TO_IPROPERTY_VALUE_MAP_ENTRY = eINSTANCE.getEStringToIPropertyValueMapEntry();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ESTRING_TO_IPROPERTY_VALUE_MAP_ENTRY__KEY = eINSTANCE.getEStringToIPropertyValueMapEntry_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ESTRING_TO_IPROPERTY_VALUE_MAP_ENTRY__VALUE = eINSTANCE.getEStringToIPropertyValueMapEntry_Value();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.dm.impl.IPropertyValueImpl <em>IProperty Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.dm.impl.IPropertyValueImpl
		 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIPropertyValue()
		 * @generated
		 */
		EClass IPROPERTY_VALUE = eINSTANCE.getIPropertyValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IPROPERTY_VALUE__VALUE = eINSTANCE.getIPropertyValue_Value();

		/**
		 * The meta object literal for the '<em><b>String Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IPROPERTY_VALUE__STRING_VALUE = eINSTANCE.getIPropertyValue_StringValue();

		/**
		 * The meta object literal for the '<em><b>Compound Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IPROPERTY_VALUE__COMPOUND_VALUE = eINSTANCE.getIPropertyValue_CompoundValue();

		/**
		 * The meta object literal for the '<em><b>Sequence Value</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IPROPERTY_VALUE__SEQUENCE_VALUE = eINSTANCE.getIPropertyValue_SequenceValue();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.dm.impl.ILocalizedStringBundleImpl <em>ILocalized String Bundle</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.dm.impl.ILocalizedStringBundleImpl
		 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getILocalizedStringBundle()
		 * @generated
		 */
		EClass ILOCALIZED_STRING_BUNDLE = eINSTANCE.getILocalizedStringBundle();

		/**
		 * The meta object literal for the '<em><b>Localized String Tables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ILOCALIZED_STRING_BUNDLE__LOCALIZED_STRING_TABLES = eINSTANCE.getILocalizedStringBundle_LocalizedStringTables();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.dm.impl.ILocalizedStringTableImpl <em>ILocalized String Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.dm.impl.ILocalizedStringTableImpl
		 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getILocalizedStringTable()
		 * @generated
		 */
		EClass ILOCALIZED_STRING_TABLE = eINSTANCE.getILocalizedStringTable();

		/**
		 * The meta object literal for the '<em><b>Strings</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ILOCALIZED_STRING_TABLE__STRINGS = eINSTANCE.getILocalizedStringTable_Strings();

		/**
		 * The meta object literal for the '<em><b>Language</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ILOCALIZED_STRING_TABLE__LANGUAGE = eINSTANCE.getILocalizedStringTable_Language();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.dm.impl.IMacroStringTableImpl <em>IMacro String Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.dm.impl.IMacroStringTableImpl
		 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIMacroStringTable()
		 * @generated
		 */
		EClass IMACRO_STRING_TABLE = eINSTANCE.getIMacroStringTable();

		/**
		 * The meta object literal for the '<em><b>String Macros</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IMACRO_STRING_TABLE__STRING_MACROS = eINSTANCE.getIMacroStringTable_StringMacros();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.dm.impl.EStringToStringMapEntryImpl <em>EString To String Map Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.dm.impl.EStringToStringMapEntryImpl
		 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getEStringToStringMapEntry()
		 * @generated
		 */
		EClass ESTRING_TO_STRING_MAP_ENTRY = eINSTANCE.getEStringToStringMapEntry();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ESTRING_TO_STRING_MAP_ENTRY__KEY = eINSTANCE.getEStringToStringMapEntry_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ESTRING_TO_STRING_MAP_ENTRY__VALUE = eINSTANCE.getEStringToStringMapEntry_Value();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.dm.impl.IEventBindingImpl <em>IEvent Binding</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.dm.impl.IEventBindingImpl
		 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIEventBinding()
		 * @generated
		 */
		EClass IEVENT_BINDING = eINSTANCE.getIEventBinding();

		/**
		 * The meta object literal for the '<em><b>Event ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IEVENT_BINDING__EVENT_ID = eINSTANCE.getIEventBinding_EventID();

		/**
		 * The meta object literal for the '<em><b>Event Handler Display Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IEVENT_BINDING__EVENT_HANDLER_DISPLAY_TEXT = eINSTANCE.getIEventBinding_EventHandlerDisplayText();

		/**
		 * The meta object literal for the '<em><b>Event Handler Info</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IEVENT_BINDING__EVENT_HANDLER_INFO = eINSTANCE.getIEventBinding_EventHandlerInfo();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.dm.impl.ISourceGenMappingStateImpl <em>ISource Gen Mapping State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.dm.impl.ISourceGenMappingStateImpl
		 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getISourceGenMappingState()
		 * @generated
		 */
		EClass ISOURCE_GEN_MAPPING_STATE = eINSTANCE.getISourceGenMappingState();

		/**
		 * The meta object literal for the '<em><b>Resource Mappings</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ISOURCE_GEN_MAPPING_STATE__RESOURCE_MAPPINGS = eINSTANCE.getISourceGenMappingState_ResourceMappings();

		/**
		 * The meta object literal for the '<em><b>Enum Mappings</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ISOURCE_GEN_MAPPING_STATE__ENUM_MAPPINGS = eINSTANCE.getISourceGenMappingState_EnumMappings();

		/**
		 * The meta object literal for the '<em><b>Array Mappings</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ISOURCE_GEN_MAPPING_STATE__ARRAY_MAPPINGS = eINSTANCE.getISourceGenMappingState_ArrayMappings();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.dm.impl.IResourceMappingsImpl <em>IResource Mappings</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.dm.impl.IResourceMappingsImpl
		 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIResourceMappings()
		 * @generated
		 */
		EClass IRESOURCE_MAPPINGS = eINSTANCE.getIResourceMappings();

		/**
		 * The meta object literal for the '<em><b>Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IRESOURCE_MAPPINGS__MAPPINGS = eINSTANCE.getIResourceMappings_Mappings();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.dm.impl.IResourceMappingImpl <em>IResource Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.dm.impl.IResourceMappingImpl
		 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIResourceMapping()
		 * @generated
		 */
		EClass IRESOURCE_MAPPING = eINSTANCE.getIResourceMapping();

		/**
		 * The meta object literal for the '<em><b>Instance Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IRESOURCE_MAPPING__INSTANCE_NAME = eINSTANCE.getIResourceMapping_InstanceName();

		/**
		 * The meta object literal for the '<em><b>Rsrc File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IRESOURCE_MAPPING__RSRC_FILE = eINSTANCE.getIResourceMapping_RsrcFile();

		/**
		 * The meta object literal for the '<em><b>Rsrc Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IRESOURCE_MAPPING__RSRC_ID = eINSTANCE.getIResourceMapping_RsrcId();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IRESOURCE_MAPPING__VALUE = eINSTANCE.getIResourceMapping_Value();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.dm.impl.IEnumMappingsImpl <em>IEnum Mappings</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.dm.impl.IEnumMappingsImpl
		 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIEnumMappings()
		 * @generated
		 */
		EClass IENUM_MAPPINGS = eINSTANCE.getIEnumMappings();

		/**
		 * The meta object literal for the '<em><b>Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IENUM_MAPPINGS__MAPPINGS = eINSTANCE.getIEnumMappings_Mappings();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.dm.impl.IEnumMappingImpl <em>IEnum Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.dm.impl.IEnumMappingImpl
		 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIEnumMapping()
		 * @generated
		 */
		EClass IENUM_MAPPING = eINSTANCE.getIEnumMapping();

		/**
		 * The meta object literal for the '<em><b>Instance Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IENUM_MAPPING__INSTANCE_NAME = eINSTANCE.getIEnumMapping_InstanceName();

		/**
		 * The meta object literal for the '<em><b>Property Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IENUM_MAPPING__PROPERTY_ID = eINSTANCE.getIEnumMapping_PropertyId();

		/**
		 * The meta object literal for the '<em><b>Name Algorithm</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IENUM_MAPPING__NAME_ALGORITHM = eINSTANCE.getIEnumMapping_NameAlgorithm();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IENUM_MAPPING__VALUE = eINSTANCE.getIEnumMapping_Value();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.dm.impl.IArrayMappingsImpl <em>IArray Mappings</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.dm.impl.IArrayMappingsImpl
		 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIArrayMappings()
		 * @generated
		 */
		EClass IARRAY_MAPPINGS = eINSTANCE.getIArrayMappings();

		/**
		 * The meta object literal for the '<em><b>Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IARRAY_MAPPINGS__MAPPINGS = eINSTANCE.getIArrayMappings_Mappings();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.dm.impl.IArrayMappingImpl <em>IArray Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.dm.impl.IArrayMappingImpl
		 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIArrayMapping()
		 * @generated
		 */
		EClass IARRAY_MAPPING = eINSTANCE.getIArrayMapping();

		/**
		 * The meta object literal for the '<em><b>Instance Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IARRAY_MAPPING__INSTANCE_NAME = eINSTANCE.getIArrayMapping_InstanceName();

		/**
		 * The meta object literal for the '<em><b>Property Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IARRAY_MAPPING__PROPERTY_ID = eINSTANCE.getIArrayMapping_PropertyId();

		/**
		 * The meta object literal for the '<em><b>Member Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IARRAY_MAPPING__MEMBER_ID = eINSTANCE.getIArrayMapping_MemberId();

		/**
		 * The meta object literal for the '<em><b>Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IARRAY_MAPPING__ELEMENTS = eINSTANCE.getIArrayMapping_Elements();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.dm.impl.IElementMappingImpl <em>IElement Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.dm.impl.IElementMappingImpl
		 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIElementMapping()
		 * @generated
		 */
		EClass IELEMENT_MAPPING = eINSTANCE.getIElementMapping();

		/**
		 * The meta object literal for the '<em><b>Unique Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IELEMENT_MAPPING__UNIQUE_VALUE = eINSTANCE.getIElementMapping_UniqueValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IELEMENT_MAPPING__VALUE = eINSTANCE.getIElementMapping_Value();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.dm.impl.IComponentManifestImpl <em>IComponent Manifest</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.dm.impl.IComponentManifestImpl
		 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIComponentManifest()
		 * @generated
		 */
		EClass ICOMPONENT_MANIFEST = eINSTANCE.getIComponentManifest();

		/**
		 * The meta object literal for the '<em><b>Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ICOMPONENT_MANIFEST__ENTRIES = eINSTANCE.getIComponentManifest_Entries();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.dm.impl.IComponentManifestEntryImpl <em>IComponent Manifest Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.dm.impl.IComponentManifestEntryImpl
		 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIComponentManifestEntry()
		 * @generated
		 */
		EClass ICOMPONENT_MANIFEST_ENTRY = eINSTANCE.getIComponentManifestEntry();

		/**
		 * The meta object literal for the '<em><b>Component ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ICOMPONENT_MANIFEST_ENTRY__COMPONENT_ID = eINSTANCE.getIComponentManifestEntry_ComponentID();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ICOMPONENT_MANIFEST_ENTRY__VERSION = eINSTANCE.getIComponentManifestEntry_Version();

		/**
		 * The meta object literal for the '<em><b>Files</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IGENERATED_FILES__FILES = eINSTANCE.getIGeneratedFiles_Files();

		/**
		 * The meta object literal for the '<em>Language</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.dm.Language
		 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getLanguage()
		 * @generated
		 */
		EDataType LANGUAGE = eINSTANCE.getLanguage();

		/**
		 * The meta object literal for the '<em>String Value</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.dm.StringValue
		 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getStringValue()
		 * @generated
		 */
		EDataType STRING_VALUE = eINSTANCE.getStringValue();

		/**
		 * The meta object literal for the '<em>Version</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.osgi.framework.Version
		 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getVersion()
		 * @generated
		 */
		EDataType VERSION = eINSTANCE.getVersion();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.emf.dm.impl.IGeneratedFilesImpl <em>IGenerated Files</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.emf.dm.impl.IGeneratedFilesImpl
		 * @see com.nokia.sdt.emf.dm.impl.DmPackageImpl#getIGeneratedFiles()
		 * @generated
		 */
		EClass IGENERATED_FILES = eINSTANCE.getIGeneratedFiles();

	}

	/**
	 * Returns the meta object for class '{@link com.nokia.sdt.emf.dm.IMacroStringTable <em>IMacro String Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IMacro String Table</em>'.
	 * @see com.nokia.sdt.emf.dm.IMacroStringTable
	 * @generated
	 */
	EClass getIMacroStringTable();

	/**
	 * Returns the meta object for the map '{@link com.nokia.sdt.emf.dm.IMacroStringTable#getStringMacros <em>String Macros</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>String Macros</em>'.
	 * @see com.nokia.sdt.emf.dm.IMacroStringTable#getStringMacros()
	 * @see #getIMacroStringTable()
	 * @generated
	 */
	EReference getIMacroStringTable_StringMacros();

	/**
	 * Returns the meta object for data type '{@link com.nokia.sdt.emf.dm.Language <em>Language</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Language</em>'.
	 * @see com.nokia.sdt.emf.dm.Language
	 * @model instanceClass="com.nokia.sdt.emf.dm.Language"
	 * @generated
	 */
	EDataType getLanguage();

	/**
	 * Returns the meta object for data type '{@link com.nokia.sdt.emf.dm.StringValue <em>String Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>String Value</em>'.
	 * @see com.nokia.sdt.emf.dm.StringValue
	 * @model instanceClass="com.nokia.sdt.emf.dm.StringValue"
	 * @generated
	 */
	EDataType getStringValue();

	/**
	 * Returns the meta object for data type '{@link org.osgi.framework.Version <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Version</em>'.
	 * @see org.osgi.framework.Version
	 * @model instanceClass="org.osgi.framework.Version"
	 * @generated
	 */
	EDataType getVersion();

} //DmPackage
