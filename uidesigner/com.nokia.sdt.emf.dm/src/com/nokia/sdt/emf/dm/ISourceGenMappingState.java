/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.dm;


import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ISource Gen Mapping State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.dm.ISourceGenMappingState#getResourceMappings <em>Resource Mappings</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.ISourceGenMappingState#getEnumMappings <em>Enum Mappings</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.ISourceGenMappingState#getArrayMappings <em>Array Mappings</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.dm.DmPackage#getISourceGenMappingState()
 * @model
 * @generated
 */
public interface ISourceGenMappingState extends EObject {
	/**
	 * Returns the value of the '<em><b>Resource Mappings</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resource Mappings</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resource Mappings</em>' containment reference.
	 * @see #setResourceMappings(IResourceMappings)
	 * @see com.nokia.sdt.emf.dm.DmPackage#getISourceGenMappingState_ResourceMappings()
	 * @model containment="true"
	 * @generated
	 */
	IResourceMappings getResourceMappings();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.dm.ISourceGenMappingState#getResourceMappings <em>Resource Mappings</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resource Mappings</em>' containment reference.
	 * @see #getResourceMappings()
	 * @generated
	 */
	void setResourceMappings(IResourceMappings value);

	/**
	 * Returns the value of the '<em><b>Enum Mappings</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enum Mappings</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enum Mappings</em>' containment reference.
	 * @see #setEnumMappings(IEnumMappings)
	 * @see com.nokia.sdt.emf.dm.DmPackage#getISourceGenMappingState_EnumMappings()
	 * @model containment="true"
	 * @generated
	 */
	IEnumMappings getEnumMappings();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.dm.ISourceGenMappingState#getEnumMappings <em>Enum Mappings</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enum Mappings</em>' containment reference.
	 * @see #getEnumMappings()
	 * @generated
	 */
	void setEnumMappings(IEnumMappings value);

	/**
	 * Returns the value of the '<em><b>Array Mappings</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Array Mappings</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Array Mappings</em>' containment reference.
	 * @see #setArrayMappings(IArrayMappings)
	 * @see com.nokia.sdt.emf.dm.DmPackage#getISourceGenMappingState_ArrayMappings()
	 * @model containment="true"
	 * @generated
	 */
	IArrayMappings getArrayMappings();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.dm.ISourceGenMappingState#getArrayMappings <em>Array Mappings</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Array Mappings</em>' containment reference.
	 * @see #getArrayMappings()
	 * @generated
	 */
	void setArrayMappings(IArrayMappings value);

} // ISourceGenMappingState
