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
 * A representation of the model object '<em><b>Code Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.CodeType#getClass_ <em>Class</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.CodeType#getPlugin <em>Plugin</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getCodeType()
 * @model extendedMetaData="name='code_._type' kind='empty'"
 * @generated
 */
public interface CodeType extends EObject{
	/**
	 * Returns the value of the '<em><b>Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Class</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 				Class in plugin.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Class</em>' attribute.
	 * @see #setClass(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getCodeType_Class()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='class'"
	 * @generated
	 */
    String getClass_();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.CodeType#getClass_ <em>Class</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Class</em>' attribute.
	 * @see #getClass_()
	 * @generated
	 */
    void setClass(String value);

	/**
	 * Returns the value of the '<em><b>Plugin</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Plugin</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 				Plugin id
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Plugin</em>' attribute.
	 * @see #setPlugin(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getCodeType_Plugin()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='plugin'"
	 * @generated
	 */
    String getPlugin();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.CodeType#getPlugin <em>Plugin</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Plugin</em>' attribute.
	 * @see #getPlugin()
	 * @generated
	 */
    void setPlugin(String value);

} // CodeType
