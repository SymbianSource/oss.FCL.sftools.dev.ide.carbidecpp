/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.dm;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.nokia.sdt.emf.dm.DmPackage
 * @generated
 */
public interface DmFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DmFactory eINSTANCE = com.nokia.sdt.emf.dm.impl.DmFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>IDesigner Data</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>IDesigner Data</em>'.
	 * @generated
	 */
	IDesignerData createIDesignerData();

	/**
	 * Returns a new object of class '<em>INode</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>INode</em>'.
	 * @generated
	 */
	INode createINode();

	/**
	 * Returns a new object of class '<em>IProperty Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>IProperty Container</em>'.
	 * @generated
	 */
	IPropertyContainer createIPropertyContainer();

	/**
	 * Returns a new object of class '<em>IProperty Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>IProperty Value</em>'.
	 * @generated
	 */
	IPropertyValue createIPropertyValue();

	/**
	 * Returns a new object of class '<em>ILocalized String Bundle</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ILocalized String Bundle</em>'.
	 * @generated
	 */
	ILocalizedStringBundle createILocalizedStringBundle();

	/**
	 * Returns a new object of class '<em>ILocalized String Table</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ILocalized String Table</em>'.
	 * @generated
	 */
	ILocalizedStringTable createILocalizedStringTable();

	/**
	 * Returns a new object of class '<em>IMacro String Table</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>IMacro String Table</em>'.
	 * @generated
	 */
	IMacroStringTable createIMacroStringTable();

	/**
	 * Returns a new object of class '<em>IEvent Binding</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>IEvent Binding</em>'.
	 * @generated
	 */
	IEventBinding createIEventBinding();

	/**
	 * Returns a new object of class '<em>ISource Gen Mapping State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ISource Gen Mapping State</em>'.
	 * @generated
	 */
	ISourceGenMappingState createISourceGenMappingState();

	/**
	 * Returns a new object of class '<em>IResource Mappings</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>IResource Mappings</em>'.
	 * @generated
	 */
	IResourceMappings createIResourceMappings();

	/**
	 * Returns a new object of class '<em>IResource Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>IResource Mapping</em>'.
	 * @generated
	 */
	IResourceMapping createIResourceMapping();

	/**
	 * Returns a new object of class '<em>IEnum Mappings</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>IEnum Mappings</em>'.
	 * @generated
	 */
	IEnumMappings createIEnumMappings();

	/**
	 * Returns a new object of class '<em>IEnum Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>IEnum Mapping</em>'.
	 * @generated
	 */
	IEnumMapping createIEnumMapping();

	/**
	 * Returns a new object of class '<em>IArray Mappings</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>IArray Mappings</em>'.
	 * @generated
	 */
	IArrayMappings createIArrayMappings();

	/**
	 * Returns a new object of class '<em>IArray Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>IArray Mapping</em>'.
	 * @generated
	 */
	IArrayMapping createIArrayMapping();

	/**
	 * Returns a new object of class '<em>IElement Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>IElement Mapping</em>'.
	 * @generated
	 */
	IElementMapping createIElementMapping();

	/**
	 * Returns a new object of class '<em>IComponent Manifest</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>IComponent Manifest</em>'.
	 * @generated
	 */
	IComponentManifest createIComponentManifest();

	/**
	 * Returns a new object of class '<em>IComponent Manifest Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>IComponent Manifest Entry</em>'.
	 * @generated
	 */
	IComponentManifestEntry createIComponentManifestEntry();

	/**
	 * Returns a new object of class '<em>IGenerated Files</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>IGenerated Files</em>'.
	 * @generated
	 */
	IGeneratedFiles createIGeneratedFiles();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	DmPackage getDmPackage();

} //DmFactory
