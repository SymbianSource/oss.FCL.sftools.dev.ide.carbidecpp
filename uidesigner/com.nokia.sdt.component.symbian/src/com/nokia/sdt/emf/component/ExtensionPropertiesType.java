/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Extension Properties Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.ExtensionPropertiesType#getAbstractPropertyGroup <em>Abstract Property Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ExtensionPropertiesType#getAbstractProperty <em>Abstract Property</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ExtensionPropertiesType#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getExtensionPropertiesType()
 * @model extendedMetaData="name='extensionProperties_._type' kind='elementOnly'"
 * @generated
 */
public interface ExtensionPropertiesType extends EObject{
	/**
	 * Returns the value of the '<em><b>Abstract Property Group</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Abstract Property Group</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Abstract Property Group</em>' attribute list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getExtensionPropertiesType_AbstractPropertyGroup()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='abstractProperty:group' namespace='##targetNamespace'"
	 * @generated
	 */
	FeatureMap getAbstractPropertyGroup();

	/**
	 * Returns the value of the '<em><b>Abstract Property</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.AbstractPropertyType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Abstract Property</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Abstract Property</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getExtensionPropertiesType_AbstractProperty()
	 * @model type="com.nokia.sdt.emf.component.AbstractPropertyType" containment="true" transient="true" changeable="false" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='abstractProperty' namespace='##targetNamespace' group='abstractProperty:group'"
	 * @generated
	 */
	EList getAbstractProperty();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The unique name of this extension property set. Used to select properties
	 * 				to add at runtime.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getExtensionPropertiesType_Name()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='name'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ExtensionPropertiesType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // ExtensionPropertiesType
