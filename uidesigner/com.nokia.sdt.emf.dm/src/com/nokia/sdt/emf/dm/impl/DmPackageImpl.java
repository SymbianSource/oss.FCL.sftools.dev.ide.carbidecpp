/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.dm.impl;

import com.nokia.sdt.emf.dm.DmFactory;
import com.nokia.sdt.emf.dm.DmPackage;
import com.nokia.sdt.emf.dm.IArrayMapping;
import com.nokia.sdt.emf.dm.IArrayMappings;
import com.nokia.sdt.emf.dm.IComponentManifest;
import com.nokia.sdt.emf.dm.IComponentManifestEntry;
import com.nokia.sdt.emf.dm.IDesignerData;
import com.nokia.sdt.emf.dm.IElementMapping;
import com.nokia.sdt.emf.dm.IEnumMapping;
import com.nokia.sdt.emf.dm.IEnumMappings;
import com.nokia.sdt.emf.dm.IEventBinding;
import com.nokia.sdt.emf.dm.IGeneratedFiles;
import com.nokia.sdt.emf.dm.ILocalizedStringBundle;
import com.nokia.sdt.emf.dm.ILocalizedStringTable;
import com.nokia.sdt.emf.dm.IMacroStringTable;
import com.nokia.sdt.emf.dm.INode;
import com.nokia.sdt.emf.dm.IPropertyContainer;
import com.nokia.sdt.emf.dm.IPropertyValue;
import com.nokia.sdt.emf.dm.IResourceMapping;
import com.nokia.sdt.emf.dm.IResourceMappings;
import com.nokia.sdt.emf.dm.ISourceGenMappingState;
import com.nokia.sdt.emf.dm.Language;
import com.nokia.sdt.emf.dm.StringValue;

import com.nokia.sdt.emf.dm.*;

import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.osgi.framework.Version;

import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DmPackageImpl extends EPackageImpl implements DmPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iDesignerDataEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iPropertyContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eStringToIPropertyValueMapEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iPropertyValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iLocalizedStringBundleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iLocalizedStringTableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iMacroStringTableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eStringToStringMapEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iEventBindingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iSourceGenMappingStateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iResourceMappingsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iResourceMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iEnumMappingsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iEnumMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iArrayMappingsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iArrayMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iElementMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iComponentManifestEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iComponentManifestEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iGeneratedFilesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType languageEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType stringValueEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType versionEDataType = null;

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
	 * @see com.nokia.sdt.emf.dm.DmPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DmPackageImpl() {
		super(eNS_URI, DmFactory.eINSTANCE);
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
	public static DmPackage init() {
		if (isInited) return (DmPackage)EPackage.Registry.INSTANCE.getEPackage(DmPackage.eNS_URI);

		// Obtain or create and register package
		DmPackageImpl theDmPackage = (DmPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof DmPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new DmPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theDmPackage.createPackageContents();

		// Initialize created meta-data
		theDmPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDmPackage.freeze();

		return theDmPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIDesignerData() {
		return iDesignerDataEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIDesignerData_StringBundle() {
		return (EReference)iDesignerDataEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIDesignerData_MacroTable() {
		return (EReference)iDesignerDataEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIDesignerData_SourceMappingState() {
		return (EReference)iDesignerDataEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIDesignerData_GeneratedFiles() {
		return (EReference)iDesignerDataEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIDesignerData_ComponentManifest() {
		return (EReference)iDesignerDataEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIDesignerData_Version() {
		return (EAttribute)iDesignerDataEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIDesignerData_RootContainers() {
		return (EReference)iDesignerDataEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIDesignerData_Properties() {
		return (EReference)iDesignerDataEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getINode() {
		return iNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getINode_ComponentId() {
		return (EAttribute)iNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getINode_Properties() {
		return (EReference)iNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getINode_Children() {
		return (EReference)iNodeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getINode_EventBindings() {
		return (EReference)iNodeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIPropertyContainer() {
		return iPropertyContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIPropertyContainer_Properties() {
		return (EReference)iPropertyContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEStringToIPropertyValueMapEntry() {
		return eStringToIPropertyValueMapEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEStringToIPropertyValueMapEntry_Value() {
		return (EReference)eStringToIPropertyValueMapEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIPropertyValue() {
		return iPropertyValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIPropertyValue_Value() {
		return (EAttribute)iPropertyValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIPropertyValue_StringValue() {
		return (EAttribute)iPropertyValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIPropertyValue_CompoundValue() {
		return (EReference)iPropertyValueEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIPropertyValue_SequenceValue() {
		return (EReference)iPropertyValueEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getILocalizedStringBundle() {
		return iLocalizedStringBundleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getILocalizedStringBundle_LocalizedStringTables() {
		return (EReference)iLocalizedStringBundleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getILocalizedStringTable() {
		return iLocalizedStringTableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getILocalizedStringTable_Strings() {
		return (EReference)iLocalizedStringTableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getILocalizedStringTable_Language() {
		return (EAttribute)iLocalizedStringTableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIMacroStringTable() {
		return iMacroStringTableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIMacroStringTable_StringMacros() {
		return (EReference)iMacroStringTableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEStringToStringMapEntry() {
		return eStringToStringMapEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEStringToStringMapEntry_Key() {
		return (EAttribute)eStringToStringMapEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEStringToStringMapEntry_Value() {
		return (EAttribute)eStringToStringMapEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIEventBinding() {
		return iEventBindingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIEventBinding_EventID() {
		return (EAttribute)iEventBindingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIEventBinding_EventHandlerDisplayText() {
		return (EAttribute)iEventBindingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIEventBinding_EventHandlerInfo() {
		return (EAttribute)iEventBindingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getISourceGenMappingState() {
		return iSourceGenMappingStateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getISourceGenMappingState_ResourceMappings() {
		return (EReference)iSourceGenMappingStateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getISourceGenMappingState_EnumMappings() {
		return (EReference)iSourceGenMappingStateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getISourceGenMappingState_ArrayMappings() {
		return (EReference)iSourceGenMappingStateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIResourceMappings() {
		return iResourceMappingsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIResourceMappings_Mappings() {
		return (EReference)iResourceMappingsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIResourceMapping() {
		return iResourceMappingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIResourceMapping_InstanceName() {
		return (EAttribute)iResourceMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIResourceMapping_RsrcFile() {
		return (EAttribute)iResourceMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIResourceMapping_RsrcId() {
		return (EAttribute)iResourceMappingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIResourceMapping_Value() {
		return (EAttribute)iResourceMappingEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIEnumMappings() {
		return iEnumMappingsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIEnumMappings_Mappings() {
		return (EReference)iEnumMappingsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIEnumMapping() {
		return iEnumMappingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIEnumMapping_InstanceName() {
		return (EAttribute)iEnumMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIEnumMapping_PropertyId() {
		return (EAttribute)iEnumMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIEnumMapping_NameAlgorithm() {
		return (EAttribute)iEnumMappingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIEnumMapping_Value() {
		return (EAttribute)iEnumMappingEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIArrayMappings() {
		return iArrayMappingsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIArrayMappings_Mappings() {
		return (EReference)iArrayMappingsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIArrayMapping() {
		return iArrayMappingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIArrayMapping_InstanceName() {
		return (EAttribute)iArrayMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIArrayMapping_PropertyId() {
		return (EAttribute)iArrayMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIArrayMapping_MemberId() {
		return (EAttribute)iArrayMappingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIArrayMapping_Elements() {
		return (EReference)iArrayMappingEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIElementMapping() {
		return iElementMappingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIElementMapping_UniqueValue() {
		return (EAttribute)iElementMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIElementMapping_Value() {
		return (EAttribute)iElementMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIComponentManifest() {
		return iComponentManifestEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIComponentManifest_Entries() {
		return (EReference)iComponentManifestEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIComponentManifestEntry() {
		return iComponentManifestEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIComponentManifestEntry_ComponentID() {
		return (EAttribute)iComponentManifestEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIComponentManifestEntry_Version() {
		return (EAttribute)iComponentManifestEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIGeneratedFiles() {
		return iGeneratedFilesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIGeneratedFiles_Files() {
		return (EAttribute)iGeneratedFilesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getLanguage() {
		return languageEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getStringValue() {
		return stringValueEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getVersion() {
		return versionEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEStringToIPropertyValueMapEntry_Key() {
		return (EAttribute)eStringToIPropertyValueMapEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DmFactory getDmFactory() {
		return (DmFactory)getEFactoryInstance();
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
		iDesignerDataEClass = createEClass(IDESIGNER_DATA);
		createEAttribute(iDesignerDataEClass, IDESIGNER_DATA__VERSION);
		createEReference(iDesignerDataEClass, IDESIGNER_DATA__COMPONENT_MANIFEST);
		createEReference(iDesignerDataEClass, IDESIGNER_DATA__PROPERTIES);
		createEReference(iDesignerDataEClass, IDESIGNER_DATA__ROOT_CONTAINERS);
		createEReference(iDesignerDataEClass, IDESIGNER_DATA__STRING_BUNDLE);
		createEReference(iDesignerDataEClass, IDESIGNER_DATA__MACRO_TABLE);
		createEReference(iDesignerDataEClass, IDESIGNER_DATA__SOURCE_MAPPING_STATE);
		createEReference(iDesignerDataEClass, IDESIGNER_DATA__GENERATED_FILES);

		iNodeEClass = createEClass(INODE);
		createEAttribute(iNodeEClass, INODE__COMPONENT_ID);
		createEReference(iNodeEClass, INODE__PROPERTIES);
		createEReference(iNodeEClass, INODE__CHILDREN);
		createEReference(iNodeEClass, INODE__EVENT_BINDINGS);

		iPropertyContainerEClass = createEClass(IPROPERTY_CONTAINER);
		createEReference(iPropertyContainerEClass, IPROPERTY_CONTAINER__PROPERTIES);

		eStringToIPropertyValueMapEntryEClass = createEClass(ESTRING_TO_IPROPERTY_VALUE_MAP_ENTRY);
		createEAttribute(eStringToIPropertyValueMapEntryEClass, ESTRING_TO_IPROPERTY_VALUE_MAP_ENTRY__KEY);
		createEReference(eStringToIPropertyValueMapEntryEClass, ESTRING_TO_IPROPERTY_VALUE_MAP_ENTRY__VALUE);

		iPropertyValueEClass = createEClass(IPROPERTY_VALUE);
		createEAttribute(iPropertyValueEClass, IPROPERTY_VALUE__VALUE);
		createEAttribute(iPropertyValueEClass, IPROPERTY_VALUE__STRING_VALUE);
		createEReference(iPropertyValueEClass, IPROPERTY_VALUE__COMPOUND_VALUE);
		createEReference(iPropertyValueEClass, IPROPERTY_VALUE__SEQUENCE_VALUE);

		iLocalizedStringBundleEClass = createEClass(ILOCALIZED_STRING_BUNDLE);
		createEReference(iLocalizedStringBundleEClass, ILOCALIZED_STRING_BUNDLE__LOCALIZED_STRING_TABLES);

		iLocalizedStringTableEClass = createEClass(ILOCALIZED_STRING_TABLE);
		createEReference(iLocalizedStringTableEClass, ILOCALIZED_STRING_TABLE__STRINGS);
		createEAttribute(iLocalizedStringTableEClass, ILOCALIZED_STRING_TABLE__LANGUAGE);

		iMacroStringTableEClass = createEClass(IMACRO_STRING_TABLE);
		createEReference(iMacroStringTableEClass, IMACRO_STRING_TABLE__STRING_MACROS);

		eStringToStringMapEntryEClass = createEClass(ESTRING_TO_STRING_MAP_ENTRY);
		createEAttribute(eStringToStringMapEntryEClass, ESTRING_TO_STRING_MAP_ENTRY__KEY);
		createEAttribute(eStringToStringMapEntryEClass, ESTRING_TO_STRING_MAP_ENTRY__VALUE);

		iEventBindingEClass = createEClass(IEVENT_BINDING);
		createEAttribute(iEventBindingEClass, IEVENT_BINDING__EVENT_ID);
		createEAttribute(iEventBindingEClass, IEVENT_BINDING__EVENT_HANDLER_DISPLAY_TEXT);
		createEAttribute(iEventBindingEClass, IEVENT_BINDING__EVENT_HANDLER_INFO);

		iSourceGenMappingStateEClass = createEClass(ISOURCE_GEN_MAPPING_STATE);
		createEReference(iSourceGenMappingStateEClass, ISOURCE_GEN_MAPPING_STATE__RESOURCE_MAPPINGS);
		createEReference(iSourceGenMappingStateEClass, ISOURCE_GEN_MAPPING_STATE__ENUM_MAPPINGS);
		createEReference(iSourceGenMappingStateEClass, ISOURCE_GEN_MAPPING_STATE__ARRAY_MAPPINGS);

		iResourceMappingsEClass = createEClass(IRESOURCE_MAPPINGS);
		createEReference(iResourceMappingsEClass, IRESOURCE_MAPPINGS__MAPPINGS);

		iResourceMappingEClass = createEClass(IRESOURCE_MAPPING);
		createEAttribute(iResourceMappingEClass, IRESOURCE_MAPPING__INSTANCE_NAME);
		createEAttribute(iResourceMappingEClass, IRESOURCE_MAPPING__RSRC_FILE);
		createEAttribute(iResourceMappingEClass, IRESOURCE_MAPPING__RSRC_ID);
		createEAttribute(iResourceMappingEClass, IRESOURCE_MAPPING__VALUE);

		iEnumMappingsEClass = createEClass(IENUM_MAPPINGS);
		createEReference(iEnumMappingsEClass, IENUM_MAPPINGS__MAPPINGS);

		iEnumMappingEClass = createEClass(IENUM_MAPPING);
		createEAttribute(iEnumMappingEClass, IENUM_MAPPING__INSTANCE_NAME);
		createEAttribute(iEnumMappingEClass, IENUM_MAPPING__PROPERTY_ID);
		createEAttribute(iEnumMappingEClass, IENUM_MAPPING__NAME_ALGORITHM);
		createEAttribute(iEnumMappingEClass, IENUM_MAPPING__VALUE);

		iArrayMappingsEClass = createEClass(IARRAY_MAPPINGS);
		createEReference(iArrayMappingsEClass, IARRAY_MAPPINGS__MAPPINGS);

		iArrayMappingEClass = createEClass(IARRAY_MAPPING);
		createEAttribute(iArrayMappingEClass, IARRAY_MAPPING__INSTANCE_NAME);
		createEAttribute(iArrayMappingEClass, IARRAY_MAPPING__PROPERTY_ID);
		createEAttribute(iArrayMappingEClass, IARRAY_MAPPING__MEMBER_ID);
		createEReference(iArrayMappingEClass, IARRAY_MAPPING__ELEMENTS);

		iElementMappingEClass = createEClass(IELEMENT_MAPPING);
		createEAttribute(iElementMappingEClass, IELEMENT_MAPPING__UNIQUE_VALUE);
		createEAttribute(iElementMappingEClass, IELEMENT_MAPPING__VALUE);

		iComponentManifestEClass = createEClass(ICOMPONENT_MANIFEST);
		createEReference(iComponentManifestEClass, ICOMPONENT_MANIFEST__ENTRIES);

		iComponentManifestEntryEClass = createEClass(ICOMPONENT_MANIFEST_ENTRY);
		createEAttribute(iComponentManifestEntryEClass, ICOMPONENT_MANIFEST_ENTRY__COMPONENT_ID);
		createEAttribute(iComponentManifestEntryEClass, ICOMPONENT_MANIFEST_ENTRY__VERSION);

		iGeneratedFilesEClass = createEClass(IGENERATED_FILES);
		createEAttribute(iGeneratedFilesEClass, IGENERATED_FILES__FILES);

		// Create data types
		languageEDataType = createEDataType(LANGUAGE);
		stringValueEDataType = createEDataType(STRING_VALUE);
		versionEDataType = createEDataType(VERSION);
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

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(iDesignerDataEClass, IDesignerData.class, "IDesignerData", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIDesignerData_Version(), this.getVersion(), "version", null, 0, 1, IDesignerData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIDesignerData_ComponentManifest(), this.getIComponentManifest(), null, "componentManifest", null, 0, 1, IDesignerData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIDesignerData_Properties(), this.getIPropertyContainer(), null, "properties", null, 0, 1, IDesignerData.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIDesignerData_RootContainers(), this.getINode(), null, "rootContainers", null, 0, -1, IDesignerData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIDesignerData_StringBundle(), this.getILocalizedStringBundle(), null, "stringBundle", null, 0, 1, IDesignerData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIDesignerData_MacroTable(), this.getIMacroStringTable(), null, "macroTable", null, 0, 1, IDesignerData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIDesignerData_SourceMappingState(), this.getISourceGenMappingState(), null, "sourceMappingState", null, 0, 1, IDesignerData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIDesignerData_GeneratedFiles(), this.getIGeneratedFiles(), null, "generatedFiles", null, 0, 1, IDesignerData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iNodeEClass, INode.class, "INode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getINode_ComponentId(), ecorePackage.getEString(), "componentId", null, 0, 1, INode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getINode_Properties(), this.getIPropertyContainer(), null, "properties", null, 0, 1, INode.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getINode_Children(), this.getINode(), null, "children", null, 0, -1, INode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getINode_EventBindings(), this.getIEventBinding(), null, "eventBindings", null, 0, -1, INode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iPropertyContainerEClass, IPropertyContainer.class, "IPropertyContainer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIPropertyContainer_Properties(), this.getEStringToIPropertyValueMapEntry(), null, "properties", null, 0, -1, IPropertyContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eStringToIPropertyValueMapEntryEClass, Map.Entry.class, "EStringToIPropertyValueMapEntry", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEStringToIPropertyValueMapEntry_Key(), ecorePackage.getEString(), "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEStringToIPropertyValueMapEntry_Value(), this.getIPropertyValue(), null, "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iPropertyValueEClass, IPropertyValue.class, "IPropertyValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIPropertyValue_Value(), ecorePackage.getEJavaObject(), "value", null, 0, 1, IPropertyValue.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIPropertyValue_StringValue(), this.getStringValue(), "stringValue", null, 0, 1, IPropertyValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIPropertyValue_CompoundValue(), this.getIPropertyContainer(), null, "compoundValue", null, 0, 1, IPropertyValue.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIPropertyValue_SequenceValue(), this.getIPropertyValue(), null, "sequenceValue", null, 0, -1, IPropertyValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iLocalizedStringBundleEClass, ILocalizedStringBundle.class, "ILocalizedStringBundle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getILocalizedStringBundle_LocalizedStringTables(), this.getILocalizedStringTable(), null, "localizedStringTables", null, 0, -1, ILocalizedStringBundle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iLocalizedStringTableEClass, ILocalizedStringTable.class, "ILocalizedStringTable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getILocalizedStringTable_Strings(), this.getEStringToStringMapEntry(), null, "strings", null, 0, -1, ILocalizedStringTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getILocalizedStringTable_Language(), this.getLanguage(), "language", null, 0, 1, ILocalizedStringTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iMacroStringTableEClass, IMacroStringTable.class, "IMacroStringTable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIMacroStringTable_StringMacros(), this.getEStringToStringMapEntry(), null, "stringMacros", null, 0, -1, IMacroStringTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eStringToStringMapEntryEClass, Map.Entry.class, "EStringToStringMapEntry", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEStringToStringMapEntry_Key(), ecorePackage.getEString(), "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEStringToStringMapEntry_Value(), ecorePackage.getEString(), "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iEventBindingEClass, IEventBinding.class, "IEventBinding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIEventBinding_EventID(), ecorePackage.getEString(), "eventID", null, 0, 1, IEventBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIEventBinding_EventHandlerDisplayText(), ecorePackage.getEString(), "eventHandlerDisplayText", null, 0, 1, IEventBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIEventBinding_EventHandlerInfo(), ecorePackage.getEString(), "eventHandlerInfo", null, 0, 1, IEventBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iSourceGenMappingStateEClass, ISourceGenMappingState.class, "ISourceGenMappingState", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getISourceGenMappingState_ResourceMappings(), this.getIResourceMappings(), null, "resourceMappings", null, 0, 1, ISourceGenMappingState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getISourceGenMappingState_EnumMappings(), this.getIEnumMappings(), null, "enumMappings", null, 0, 1, ISourceGenMappingState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getISourceGenMappingState_ArrayMappings(), this.getIArrayMappings(), null, "arrayMappings", null, 0, 1, ISourceGenMappingState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iResourceMappingsEClass, IResourceMappings.class, "IResourceMappings", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIResourceMappings_Mappings(), this.getIResourceMapping(), null, "mappings", null, 0, -1, IResourceMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iResourceMappingEClass, IResourceMapping.class, "IResourceMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIResourceMapping_InstanceName(), ecorePackage.getEString(), "instanceName", null, 0, 1, IResourceMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIResourceMapping_RsrcFile(), ecorePackage.getEString(), "rsrcFile", null, 0, 1, IResourceMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIResourceMapping_RsrcId(), ecorePackage.getEString(), "rsrcId", null, 0, 1, IResourceMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIResourceMapping_Value(), ecorePackage.getEString(), "value", null, 0, 1, IResourceMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iEnumMappingsEClass, IEnumMappings.class, "IEnumMappings", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIEnumMappings_Mappings(), this.getIEnumMapping(), null, "mappings", null, 0, -1, IEnumMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iEnumMappingEClass, IEnumMapping.class, "IEnumMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIEnumMapping_InstanceName(), ecorePackage.getEString(), "instanceName", null, 0, 1, IEnumMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIEnumMapping_PropertyId(), ecorePackage.getEString(), "propertyId", null, 0, 1, IEnumMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIEnumMapping_NameAlgorithm(), ecorePackage.getEString(), "nameAlgorithm", null, 0, 1, IEnumMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIEnumMapping_Value(), ecorePackage.getEString(), "value", null, 0, 1, IEnumMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iArrayMappingsEClass, IArrayMappings.class, "IArrayMappings", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIArrayMappings_Mappings(), this.getIArrayMapping(), null, "mappings", null, 0, -1, IArrayMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iArrayMappingEClass, IArrayMapping.class, "IArrayMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIArrayMapping_InstanceName(), ecorePackage.getEString(), "instanceName", null, 0, 1, IArrayMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIArrayMapping_PropertyId(), ecorePackage.getEString(), "propertyId", null, 0, 1, IArrayMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIArrayMapping_MemberId(), ecorePackage.getEString(), "memberId", null, 0, 1, IArrayMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIArrayMapping_Elements(), this.getIElementMapping(), null, "elements", null, 0, -1, IArrayMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iElementMappingEClass, IElementMapping.class, "IElementMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIElementMapping_UniqueValue(), ecorePackage.getEString(), "uniqueValue", null, 0, 1, IElementMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIElementMapping_Value(), ecorePackage.getEString(), "value", null, 0, 1, IElementMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iComponentManifestEClass, IComponentManifest.class, "IComponentManifest", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIComponentManifest_Entries(), this.getIComponentManifestEntry(), null, "entries", null, 0, -1, IComponentManifest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iComponentManifestEntryEClass, IComponentManifestEntry.class, "IComponentManifestEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIComponentManifestEntry_ComponentID(), ecorePackage.getEString(), "componentID", null, 0, 1, IComponentManifestEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIComponentManifestEntry_Version(), this.getVersion(), "version", null, 0, 1, IComponentManifestEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iGeneratedFilesEClass, IGeneratedFiles.class, "IGeneratedFiles", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIGeneratedFiles_Files(), ecorePackage.getEString(), "files", null, 0, -1, IGeneratedFiles.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize data types
		initEDataType(languageEDataType, Language.class, "Language", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(stringValueEDataType, StringValue.class, "StringValue", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(versionEDataType, Version.class, "Version", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //DmPackageImpl
