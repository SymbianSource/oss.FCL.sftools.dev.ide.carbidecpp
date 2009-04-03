/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.dm.impl;

import com.nokia.sdt.emf.dm.*;

import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.osgi.framework.Version;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DmFactoryImpl extends EFactoryImpl implements DmFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DmFactory init() {
		try {
			DmFactory theDmFactory = (DmFactory)EPackage.Registry.INSTANCE.getEFactory("http:///com/nokia/sdt/emf/dm.ecore"); 
			if (theDmFactory != null) {
				return theDmFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DmFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DmFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case DmPackage.IDESIGNER_DATA: return createIDesignerData();
			case DmPackage.INODE: return createINode();
			case DmPackage.IPROPERTY_CONTAINER: return createIPropertyContainer();
			case DmPackage.ESTRING_TO_IPROPERTY_VALUE_MAP_ENTRY: return (EObject)createEStringToIPropertyValueMapEntry();
			case DmPackage.IPROPERTY_VALUE: return createIPropertyValue();
			case DmPackage.ILOCALIZED_STRING_BUNDLE: return createILocalizedStringBundle();
			case DmPackage.ILOCALIZED_STRING_TABLE: return createILocalizedStringTable();
			case DmPackage.IMACRO_STRING_TABLE: return createIMacroStringTable();
			case DmPackage.ESTRING_TO_STRING_MAP_ENTRY: return (EObject)createEStringToStringMapEntry();
			case DmPackage.IEVENT_BINDING: return createIEventBinding();
			case DmPackage.ISOURCE_GEN_MAPPING_STATE: return createISourceGenMappingState();
			case DmPackage.IRESOURCE_MAPPINGS: return createIResourceMappings();
			case DmPackage.IRESOURCE_MAPPING: return createIResourceMapping();
			case DmPackage.IENUM_MAPPINGS: return createIEnumMappings();
			case DmPackage.IENUM_MAPPING: return createIEnumMapping();
			case DmPackage.IARRAY_MAPPINGS: return createIArrayMappings();
			case DmPackage.IARRAY_MAPPING: return createIArrayMapping();
			case DmPackage.IELEMENT_MAPPING: return createIElementMapping();
			case DmPackage.ICOMPONENT_MANIFEST: return createIComponentManifest();
			case DmPackage.ICOMPONENT_MANIFEST_ENTRY: return createIComponentManifestEntry();
			case DmPackage.IGENERATED_FILES: return createIGeneratedFiles();
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
			case DmPackage.LANGUAGE:
				return createLanguageFromString(eDataType, initialValue);
			case DmPackage.STRING_VALUE:
				return createStringValueFromString(eDataType, initialValue);
			case DmPackage.VERSION:
				return createVersionFromString(eDataType, initialValue);
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
			case DmPackage.LANGUAGE:
				return convertLanguageToString(eDataType, instanceValue);
			case DmPackage.STRING_VALUE:
				return convertStringValueToString(eDataType, instanceValue);
			case DmPackage.VERSION:
				return convertVersionToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IDesignerData createIDesignerData() {
		IDesignerDataImpl iDesignerData = new IDesignerDataImpl();
		return iDesignerData;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public INode createINode() {
		INodeImpl iNode = new INodeImpl();
		return iNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IPropertyContainer createIPropertyContainer() {
		IPropertyContainerImpl iPropertyContainer = new IPropertyContainerImpl();
		return iPropertyContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry createEStringToIPropertyValueMapEntry() {
		EStringToIPropertyValueMapEntryImpl eStringToIPropertyValueMapEntry = new EStringToIPropertyValueMapEntryImpl();
		return eStringToIPropertyValueMapEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IPropertyValue createIPropertyValue() {
		IPropertyValueImpl iPropertyValue = new IPropertyValueImpl();
		return iPropertyValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ILocalizedStringBundle createILocalizedStringBundle() {
		ILocalizedStringBundleImpl iLocalizedStringBundle = new ILocalizedStringBundleImpl();
		return iLocalizedStringBundle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ILocalizedStringTable createILocalizedStringTable() {
		ILocalizedStringTableImpl iLocalizedStringTable = new ILocalizedStringTableImpl();
		return iLocalizedStringTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IMacroStringTable createIMacroStringTable() {
		IMacroStringTableImpl iMacroStringTable = new IMacroStringTableImpl();
		return iMacroStringTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry createEStringToStringMapEntry() {
		EStringToStringMapEntryImpl eStringToStringMapEntry = new EStringToStringMapEntryImpl();
		return eStringToStringMapEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IEventBinding createIEventBinding() {
		IEventBindingImpl iEventBinding = new IEventBindingImpl();
		return iEventBinding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISourceGenMappingState createISourceGenMappingState() {
		ISourceGenMappingStateImpl iSourceGenMappingState = new ISourceGenMappingStateImpl();
		return iSourceGenMappingState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IResourceMappings createIResourceMappings() {
		IResourceMappingsImpl iResourceMappings = new IResourceMappingsImpl();
		return iResourceMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IResourceMapping createIResourceMapping() {
		IResourceMappingImpl iResourceMapping = new IResourceMappingImpl();
		return iResourceMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IEnumMappings createIEnumMappings() {
		IEnumMappingsImpl iEnumMappings = new IEnumMappingsImpl();
		return iEnumMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IEnumMapping createIEnumMapping() {
		IEnumMappingImpl iEnumMapping = new IEnumMappingImpl();
		return iEnumMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IArrayMappings createIArrayMappings() {
		IArrayMappingsImpl iArrayMappings = new IArrayMappingsImpl();
		return iArrayMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IArrayMapping createIArrayMapping() {
		IArrayMappingImpl iArrayMapping = new IArrayMappingImpl();
		return iArrayMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IElementMapping createIElementMapping() {
		IElementMappingImpl iElementMapping = new IElementMappingImpl();
		return iElementMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IComponentManifest createIComponentManifest() {
		IComponentManifestImpl iComponentManifest = new IComponentManifestImpl();
		return iComponentManifest;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IComponentManifestEntry createIComponentManifestEntry() {
		IComponentManifestEntryImpl iComponentManifestEntry = new IComponentManifestEntryImpl();
		return iComponentManifestEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IGeneratedFiles createIGeneratedFiles() {
		IGeneratedFilesImpl iGeneratedFiles = new IGeneratedFilesImpl();
		return iGeneratedFiles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Language createLanguageFromString(EDataType eDataType, String initialValue) {
		Language result = Language.createFromString(initialValue);
		if (result == null)
			result = (Language)super.createFromString(eDataType, initialValue);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertLanguageToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringValue createStringValueFromString(EDataType eDataType, String initialValue) {
		return (StringValue)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertStringValueToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Version createVersionFromString(EDataType eDataType, String initialValue) {
		return (Version)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertVersionToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DmPackage getDmPackage() {
		return (DmPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	public static DmPackage getPackage() {
		return DmPackage.eINSTANCE;
	}

} //DmFactoryImpl
