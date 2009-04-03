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
 * A representation of the model object '<em><b>Property Overrides Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.PropertyOverridesType#getPropertyOverride <em>Property Override</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getPropertyOverridesType()
 * @model extendedMetaData="name='propertyOverrides_._type' kind='elementOnly'"
 * @generated
 */
public interface PropertyOverridesType extends EObject {
	/**
	 * Returns the value of the '<em><b>Property Override</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.PropertyOverrideType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Override</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Override</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getPropertyOverridesType_PropertyOverride()
	 * @model type="com.nokia.sdt.emf.component.PropertyOverrideType" containment="true"
	 *        extendedMetaData="kind='element' name='propertyOverride' namespace='##targetNamespace'"
	 * @generated
	 */
	EList getPropertyOverride();

} // PropertyOverridesType
