/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Definition Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.ComponentDefinitionType#getCompoundPropertyDeclaration <em>Compound Property Declaration</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ComponentDefinitionType#getEnumPropertyDeclaration <em>Enum Property Declaration</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ComponentDefinitionType#getComponent <em>Component</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getComponentDefinitionType()
 * @model extendedMetaData="name='componentDefinition_._type' kind='elementOnly'"
 * @generated
 */
public interface ComponentDefinitionType extends EObject{
	/**
	 * Returns the value of the '<em><b>Compound Property Declaration</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.CompoundPropertyDeclarationType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Compound Property Declaration</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 			Declares a compound type that may be referenced from one or more
	 * 			compound property declarations in this or other component documents. All compound property declarations are in a global 
	 * 			namespace and must be declared as global elements.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Compound Property Declaration</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getComponentDefinitionType_CompoundPropertyDeclaration()
	 * @model type="com.nokia.sdt.emf.component.CompoundPropertyDeclarationType" containment="true"
	 *        extendedMetaData="kind='element' name='compoundPropertyDeclaration' namespace='##targetNamespace'"
	 * @generated
	 */
	EList getCompoundPropertyDeclaration();

	/**
	 * Returns the value of the '<em><b>Enum Property Declaration</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.EnumPropertyDeclarationType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enum Property Declaration</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 			Declares an enumerated type that may be referenced from one or more
	 * 			enumerated property declarations in this or other component documents. All enum property declarations are in a global 
	 * 			namespace and must be declared as global elements.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Enum Property Declaration</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getComponentDefinitionType_EnumPropertyDeclaration()
	 * @model type="com.nokia.sdt.emf.component.EnumPropertyDeclarationType" containment="true"
	 *        extendedMetaData="kind='element' name='enumPropertyDeclaration' namespace='##targetNamespace'"
	 * @generated
	 */
	EList getEnumPropertyDeclaration();

	/**
	 * Returns the value of the '<em><b>Component</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 *         This is the container element for all the information about a single component.
	 *         
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Component</em>' containment reference.
	 * @see #setComponent(ComponentType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getComponentDefinitionType_Component()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='component' namespace='##targetNamespace'"
	 * @generated
	 */
	ComponentType getComponent();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ComponentDefinitionType#getComponent <em>Component</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component</em>' containment reference.
	 * @see #getComponent()
	 * @generated
	 */
	void setComponent(ComponentType value);

} // ComponentDefinitionType
