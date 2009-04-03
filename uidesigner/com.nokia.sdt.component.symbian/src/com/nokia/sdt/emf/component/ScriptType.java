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
 * A representation of the model object '<em><b>Script Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.ScriptType#getFile <em>File</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ScriptType#getPrototype <em>Prototype</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getScriptType()
 * @model extendedMetaData="name='script_._type' kind='empty'"
 * @generated
 */
public interface ScriptType extends EObject{
	/**
	 * Returns the value of the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>File</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>File</em>' attribute.
	 * @see #setFile(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getScriptType_File()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='file'"
	 * @generated
	 */
    String getFile();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ScriptType#getFile <em>File</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>File</em>' attribute.
	 * @see #getFile()
	 * @generated
	 */
    void setFile(String value);

	/**
	 * Returns the value of the '<em><b>Prototype</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Prototype</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Prototype</em>' attribute.
	 * @see #setPrototype(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getScriptType_Prototype()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='prototype'"
	 * @generated
	 */
    String getPrototype();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ScriptType#getPrototype <em>Prototype</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Prototype</em>' attribute.
	 * @see #getPrototype()
	 * @generated
	 */
    void setPrototype(String value);

} // ScriptType
