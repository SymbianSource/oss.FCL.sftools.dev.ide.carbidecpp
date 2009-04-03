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
 * A representation of the model object '<em><b>Documentation Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentationType#getInformation <em>Information</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentationType#getHelpTopic <em>Help Topic</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DocumentationType#getWizardDescription <em>Wizard Description</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentationType()
 * @model extendedMetaData="name='documentation_._type' kind='elementOnly'"
 * @generated
 */
public interface DocumentationType extends EObject{
	/**
	 * Returns the value of the '<em><b>Information</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Information</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Brief description of the component, shown in a tooltip. This value should be localized.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Information</em>' attribute.
	 * @see #setInformation(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentationType_Information()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='information' namespace='##targetNamespace'"
	 * @generated
	 */
	String getInformation();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentationType#getInformation <em>Information</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Information</em>' attribute.
	 * @see #getInformation()
	 * @generated
	 */
	void setInformation(String value);

	/**
	 * Returns the value of the '<em><b>Help Topic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Help Topic</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A link to help information for this component.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Help Topic</em>' attribute.
	 * @see #setHelpTopic(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentationType_HelpTopic()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='helpTopic' namespace='##targetNamespace'"
	 * @generated
	 */
	String getHelpTopic();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentationType#getHelpTopic <em>Help Topic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Help Topic</em>' attribute.
	 * @see #getHelpTopic()
	 * @generated
	 */
	void setHelpTopic(String value);

	/**
	 * Returns the value of the '<em><b>Wizard Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Wizard Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Text displayed in the UI design wizard. Applies only to containers and top-level content. This
	 * 					value should be localized.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Wizard Description</em>' attribute.
	 * @see #setWizardDescription(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDocumentationType_WizardDescription()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='wizardDescription' namespace='##targetNamespace'"
	 * @generated
	 */
	String getWizardDescription();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DocumentationType#getWizardDescription <em>Wizard Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Wizard Description</em>' attribute.
	 * @see #getWizardDescription()
	 * @generated
	 */
	void setWizardDescription(String value);

} // DocumentationType
