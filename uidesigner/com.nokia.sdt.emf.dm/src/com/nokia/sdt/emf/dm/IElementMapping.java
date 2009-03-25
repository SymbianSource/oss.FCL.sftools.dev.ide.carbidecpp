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
 * A representation of the model object '<em><b>IElement Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.dm.IElementMapping#getUniqueValue <em>Unique Value</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.IElementMapping#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.dm.DmPackage#getIElementMapping()
 * @model
 * @generated
 */
public interface IElementMapping extends EObject {
	/**
	 * Returns the value of the '<em><b>Unique Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unique Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unique Value</em>' attribute.
	 * @see #setUniqueValue(String)
	 * @see com.nokia.sdt.emf.dm.DmPackage#getIElementMapping_UniqueValue()
	 * @model unique="false"
	 * @generated
	 */
	String getUniqueValue();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.dm.IElementMapping#getUniqueValue <em>Unique Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unique Value</em>' attribute.
	 * @see #getUniqueValue()
	 * @generated
	 */
	void setUniqueValue(String value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see com.nokia.sdt.emf.dm.DmPackage#getIElementMapping_Value()
	 * @model unique="false"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.dm.IElementMapping#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

} // IElementMapping
