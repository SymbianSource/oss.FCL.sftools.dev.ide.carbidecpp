/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.dm;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IEnum Mappings</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.dm.IEnumMappings#getMappings <em>Mappings</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.dm.DmPackage#getIEnumMappings()
 * @model
 * @generated
 */
public interface IEnumMappings extends EObject {
	/**
	 * Returns the value of the '<em><b>Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.dm.IEnumMapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mappings</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mappings</em>' containment reference list.
	 * @see com.nokia.sdt.emf.dm.DmPackage#getIEnumMappings_Mappings()
	 * @model type="com.nokia.sdt.emf.dm.IEnumMapping" containment="true"
	 * @generated
	 */
	EList getMappings();

} // IEnumMappings
