/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Use Expand Macro Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.UseExpandMacroType#getIds <em>Ids</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getUseExpandMacroType()
 * @model extendedMetaData="name='useExpandMacro_._type' kind='empty'"
 * @generated
 */
public interface UseExpandMacroType extends EObject {
	/**
	 * Returns the value of the '<em><b>Ids</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ids</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ids</em>' attribute.
	 * @see #setIds(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getUseExpandMacroType_Ids()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='ids'"
	 * @generated
	 */
	String getIds();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.UseExpandMacroType#getIds <em>Ids</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ids</em>' attribute.
	 * @see #getIds()
	 * @generated
	 */
	void setIds(String value);

} // UseExpandMacroType
