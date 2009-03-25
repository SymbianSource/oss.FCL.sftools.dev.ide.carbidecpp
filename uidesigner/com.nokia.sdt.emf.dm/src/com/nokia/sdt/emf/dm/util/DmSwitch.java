/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.dm.util;

import com.nokia.sdt.emf.dm.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import java.util.List;
import java.util.Map;

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
 * @see com.nokia.sdt.emf.dm.DmPackage
 * @generated
 */
public class DmSwitch {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static DmPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DmSwitch() {
		if (modelPackage == null) {
			modelPackage = DmPackage.eINSTANCE;
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
			case DmPackage.IDESIGNER_DATA: {
				IDesignerData iDesignerData = (IDesignerData)theEObject;
				Object result = caseIDesignerData(iDesignerData);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DmPackage.INODE: {
				INode iNode = (INode)theEObject;
				Object result = caseINode(iNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DmPackage.IPROPERTY_CONTAINER: {
				IPropertyContainer iPropertyContainer = (IPropertyContainer)theEObject;
				Object result = caseIPropertyContainer(iPropertyContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DmPackage.ESTRING_TO_IPROPERTY_VALUE_MAP_ENTRY: {
				Map.Entry eStringToIPropertyValueMapEntry = (Map.Entry)theEObject;
				Object result = caseEStringToIPropertyValueMapEntry(eStringToIPropertyValueMapEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DmPackage.IPROPERTY_VALUE: {
				IPropertyValue iPropertyValue = (IPropertyValue)theEObject;
				Object result = caseIPropertyValue(iPropertyValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DmPackage.ILOCALIZED_STRING_BUNDLE: {
				ILocalizedStringBundle iLocalizedStringBundle = (ILocalizedStringBundle)theEObject;
				Object result = caseILocalizedStringBundle(iLocalizedStringBundle);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DmPackage.ILOCALIZED_STRING_TABLE: {
				ILocalizedStringTable iLocalizedStringTable = (ILocalizedStringTable)theEObject;
				Object result = caseILocalizedStringTable(iLocalizedStringTable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DmPackage.IMACRO_STRING_TABLE: {
				IMacroStringTable iMacroStringTable = (IMacroStringTable)theEObject;
				Object result = caseIMacroStringTable(iMacroStringTable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DmPackage.ESTRING_TO_STRING_MAP_ENTRY: {
				Map.Entry eStringToStringMapEntry = (Map.Entry)theEObject;
				Object result = caseEStringToStringMapEntry(eStringToStringMapEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DmPackage.IEVENT_BINDING: {
				IEventBinding iEventBinding = (IEventBinding)theEObject;
				Object result = caseIEventBinding(iEventBinding);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DmPackage.ISOURCE_GEN_MAPPING_STATE: {
				ISourceGenMappingState iSourceGenMappingState = (ISourceGenMappingState)theEObject;
				Object result = caseISourceGenMappingState(iSourceGenMappingState);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DmPackage.IRESOURCE_MAPPINGS: {
				IResourceMappings iResourceMappings = (IResourceMappings)theEObject;
				Object result = caseIResourceMappings(iResourceMappings);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DmPackage.IRESOURCE_MAPPING: {
				IResourceMapping iResourceMapping = (IResourceMapping)theEObject;
				Object result = caseIResourceMapping(iResourceMapping);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DmPackage.IENUM_MAPPINGS: {
				IEnumMappings iEnumMappings = (IEnumMappings)theEObject;
				Object result = caseIEnumMappings(iEnumMappings);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DmPackage.IENUM_MAPPING: {
				IEnumMapping iEnumMapping = (IEnumMapping)theEObject;
				Object result = caseIEnumMapping(iEnumMapping);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DmPackage.IARRAY_MAPPINGS: {
				IArrayMappings iArrayMappings = (IArrayMappings)theEObject;
				Object result = caseIArrayMappings(iArrayMappings);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DmPackage.IARRAY_MAPPING: {
				IArrayMapping iArrayMapping = (IArrayMapping)theEObject;
				Object result = caseIArrayMapping(iArrayMapping);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DmPackage.IELEMENT_MAPPING: {
				IElementMapping iElementMapping = (IElementMapping)theEObject;
				Object result = caseIElementMapping(iElementMapping);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DmPackage.ICOMPONENT_MANIFEST: {
				IComponentManifest iComponentManifest = (IComponentManifest)theEObject;
				Object result = caseIComponentManifest(iComponentManifest);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DmPackage.ICOMPONENT_MANIFEST_ENTRY: {
				IComponentManifestEntry iComponentManifestEntry = (IComponentManifestEntry)theEObject;
				Object result = caseIComponentManifestEntry(iComponentManifestEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DmPackage.IGENERATED_FILES: {
				IGeneratedFiles iGeneratedFiles = (IGeneratedFiles)theEObject;
				Object result = caseIGeneratedFiles(iGeneratedFiles);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IDesigner Data</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IDesigner Data</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseIDesignerData(IDesignerData object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>INode</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>INode</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseINode(INode object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IProperty Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IProperty Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseIPropertyContainer(IPropertyContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>EString To IProperty Value Map Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>EString To IProperty Value Map Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseEStringToIPropertyValueMapEntry(Map.Entry object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IProperty Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IProperty Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseIPropertyValue(IPropertyValue object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>ILocalized String Bundle</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>ILocalized String Bundle</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseILocalizedStringBundle(ILocalizedStringBundle object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>ILocalized String Table</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>ILocalized String Table</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseILocalizedStringTable(ILocalizedStringTable object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IMacro String Table</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IMacro String Table</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseIMacroStringTable(IMacroStringTable object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>EString To String Map Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>EString To String Map Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseEStringToStringMapEntry(Map.Entry object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IEvent Binding</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IEvent Binding</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseIEventBinding(IEventBinding object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>ISource Gen Mapping State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>ISource Gen Mapping State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseISourceGenMappingState(ISourceGenMappingState object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IResource Mappings</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IResource Mappings</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseIResourceMappings(IResourceMappings object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IResource Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IResource Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseIResourceMapping(IResourceMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IEnum Mappings</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IEnum Mappings</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseIEnumMappings(IEnumMappings object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IEnum Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IEnum Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseIEnumMapping(IEnumMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IArray Mappings</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IArray Mappings</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseIArrayMappings(IArrayMappings object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IArray Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IArray Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseIArrayMapping(IArrayMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IElement Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IElement Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseIElementMapping(IElementMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IComponent Manifest</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IComponent Manifest</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseIComponentManifest(IComponentManifest object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IComponent Manifest Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IComponent Manifest Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseIComponentManifestEntry(IComponentManifestEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IGenerated Files</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IGenerated Files</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseIGeneratedFiles(IGeneratedFiles object) {
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

} //DmSwitch
