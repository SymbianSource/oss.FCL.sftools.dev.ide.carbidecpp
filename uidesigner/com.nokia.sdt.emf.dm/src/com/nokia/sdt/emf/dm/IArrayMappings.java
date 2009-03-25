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
 * A representation of the model object '<em><b>IArray Mappings</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.dm.IArrayMappings#getMappings <em>Mappings</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.dm.DmPackage#getIArrayMappings()
 * @model
 * @generated
 */
public interface IArrayMappings extends EObject {
	/**
	 * Returns the value of the '<em><b>Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.dm.IArrayMapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mappings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mappings</em>' containment reference list.
	 * @see com.nokia.sdt.emf.dm.DmPackage#getIArrayMappings_Mappings()
	 * @model type="com.nokia.sdt.emf.dm.IArrayMapping" containment="true"
	 * @generated
	 */
	EList getMappings();

} // IArrayMappings
