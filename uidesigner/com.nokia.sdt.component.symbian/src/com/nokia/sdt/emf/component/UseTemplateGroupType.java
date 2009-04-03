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
 * A representation of the model object '<em><b>Use Template Group Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.UseTemplateGroupType#getUseTemplate <em>Use Template</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.UseTemplateGroupType#getIds <em>Ids</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getUseTemplateGroupType()
 * @model extendedMetaData="name='useTemplateGroup_._type' kind='elementOnly'"
 * @generated
 */
public interface UseTemplateGroupType extends EObject{
	/**
	 * Returns the value of the '<em><b>Use Template</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.UseTemplateType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Use Template</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		In a derived component, selects a template from the base by id.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Use Template</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getUseTemplateGroupType_UseTemplate()
	 * @model type="com.nokia.sdt.emf.component.UseTemplateType" containment="true"
	 *        extendedMetaData="kind='element' name='useTemplate' namespace='##targetNamespace'"
	 * @generated
	 */
    EList getUseTemplate();

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
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getUseTemplateGroupType_Ids()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='ids'"
	 * @generated
	 */
    String getIds();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.UseTemplateGroupType#getIds <em>Ids</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ids</em>' attribute.
	 * @see #getIds()
	 * @generated
	 */
    void setIds(String value);

} // UseTemplateGroupType
