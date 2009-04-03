/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.dm.util;

import com.nokia.sdt.emf.dm.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import java.util.Map;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.nokia.sdt.emf.dm.DmPackage
 * @generated
 */
public class DmAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static DmPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DmAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = DmPackage.eINSTANCE;
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
	protected DmSwitch modelSwitch=
		new DmSwitch() {
			public Object caseIDesignerData(IDesignerData object) {
				return createIDesignerDataAdapter();
			}
			public Object caseINode(INode object) {
				return createINodeAdapter();
			}
			public Object caseIPropertyContainer(IPropertyContainer object) {
				return createIPropertyContainerAdapter();
			}
			public Object caseEStringToIPropertyValueMapEntry(Map.Entry object) {
				return createEStringToIPropertyValueMapEntryAdapter();
			}
			public Object caseIPropertyValue(IPropertyValue object) {
				return createIPropertyValueAdapter();
			}
			public Object caseILocalizedStringBundle(ILocalizedStringBundle object) {
				return createILocalizedStringBundleAdapter();
			}
			public Object caseILocalizedStringTable(ILocalizedStringTable object) {
				return createILocalizedStringTableAdapter();
			}
			public Object caseIMacroStringTable(IMacroStringTable object) {
				return createIMacroStringTableAdapter();
			}
			public Object caseEStringToStringMapEntry(Map.Entry object) {
				return createEStringToStringMapEntryAdapter();
			}
			public Object caseIEventBinding(IEventBinding object) {
				return createIEventBindingAdapter();
			}
			public Object caseISourceGenMappingState(ISourceGenMappingState object) {
				return createISourceGenMappingStateAdapter();
			}
			public Object caseIResourceMappings(IResourceMappings object) {
				return createIResourceMappingsAdapter();
			}
			public Object caseIResourceMapping(IResourceMapping object) {
				return createIResourceMappingAdapter();
			}
			public Object caseIEnumMappings(IEnumMappings object) {
				return createIEnumMappingsAdapter();
			}
			public Object caseIEnumMapping(IEnumMapping object) {
				return createIEnumMappingAdapter();
			}
			public Object caseIArrayMappings(IArrayMappings object) {
				return createIArrayMappingsAdapter();
			}
			public Object caseIArrayMapping(IArrayMapping object) {
				return createIArrayMappingAdapter();
			}
			public Object caseIElementMapping(IElementMapping object) {
				return createIElementMappingAdapter();
			}
			public Object caseIComponentManifest(IComponentManifest object) {
				return createIComponentManifestAdapter();
			}
			public Object caseIComponentManifestEntry(IComponentManifestEntry object) {
				return createIComponentManifestEntryAdapter();
			}
			public Object caseIGeneratedFiles(IGeneratedFiles object) {
				return createIGeneratedFilesAdapter();
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
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.dm.IDesignerData <em>IDesigner Data</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.dm.IDesignerData
	 * @generated
	 */
	public Adapter createIDesignerDataAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.dm.INode <em>INode</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.dm.INode
	 * @generated
	 */
	public Adapter createINodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.dm.IPropertyContainer <em>IProperty Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.dm.IPropertyContainer
	 * @generated
	 */
	public Adapter createIPropertyContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>EString To IProperty Value Map Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createEStringToIPropertyValueMapEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.dm.IPropertyValue <em>IProperty Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.dm.IPropertyValue
	 * @generated
	 */
	public Adapter createIPropertyValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.dm.ILocalizedStringBundle <em>ILocalized String Bundle</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.dm.ILocalizedStringBundle
	 * @generated
	 */
	public Adapter createILocalizedStringBundleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.dm.ILocalizedStringTable <em>ILocalized String Table</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.dm.ILocalizedStringTable
	 * @generated
	 */
	public Adapter createILocalizedStringTableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.dm.IMacroStringTable <em>IMacro String Table</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.dm.IMacroStringTable
	 * @generated
	 */
	public Adapter createIMacroStringTableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>EString To String Map Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createEStringToStringMapEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.dm.IEventBinding <em>IEvent Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.dm.IEventBinding
	 * @generated
	 */
	public Adapter createIEventBindingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.dm.ISourceGenMappingState <em>ISource Gen Mapping State</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.dm.ISourceGenMappingState
	 * @generated
	 */
	public Adapter createISourceGenMappingStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.dm.IResourceMappings <em>IResource Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.dm.IResourceMappings
	 * @generated
	 */
	public Adapter createIResourceMappingsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.dm.IResourceMapping <em>IResource Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.dm.IResourceMapping
	 * @generated
	 */
	public Adapter createIResourceMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.dm.IEnumMappings <em>IEnum Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.dm.IEnumMappings
	 * @generated
	 */
	public Adapter createIEnumMappingsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.dm.IEnumMapping <em>IEnum Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.dm.IEnumMapping
	 * @generated
	 */
	public Adapter createIEnumMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.dm.IArrayMappings <em>IArray Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.dm.IArrayMappings
	 * @generated
	 */
	public Adapter createIArrayMappingsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.dm.IArrayMapping <em>IArray Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.dm.IArrayMapping
	 * @generated
	 */
	public Adapter createIArrayMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.dm.IElementMapping <em>IElement Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.dm.IElementMapping
	 * @generated
	 */
	public Adapter createIElementMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.dm.IComponentManifest <em>IComponent Manifest</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.dm.IComponentManifest
	 * @generated
	 */
	public Adapter createIComponentManifestAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.dm.IComponentManifestEntry <em>IComponent Manifest Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.dm.IComponentManifestEntry
	 * @generated
	 */
	public Adapter createIComponentManifestEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.sdt.emf.dm.IGeneratedFiles <em>IGenerated Files</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.sdt.emf.dm.IGeneratedFiles
	 * @generated
	 */
	public Adapter createIGeneratedFilesAdapter() {
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

} //DmAdapterFactory
