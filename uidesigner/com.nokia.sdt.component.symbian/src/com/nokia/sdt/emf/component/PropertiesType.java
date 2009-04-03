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
 * A representation of the model object '<em><b>Properties Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.PropertiesType#getAbstractPropertyGroup <em>Abstract Property Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.PropertiesType#getAbstractProperty <em>Abstract Property</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getPropertiesType()
 * @model extendedMetaData="name='properties_._type' kind='elementOnly'"
 * @generated
 */
public interface PropertiesType extends EObject{
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
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getPropertiesType_AbstractPropertyGroup()
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
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getPropertiesType_AbstractProperty()
	 * @model type="com.nokia.sdt.emf.component.AbstractPropertyType" containment="true" transient="true" changeable="false" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='abstractProperty' namespace='##targetNamespace' group='abstractProperty:group'"
	 * @generated
	 */
	EList getAbstractProperty();

} // PropertiesType
