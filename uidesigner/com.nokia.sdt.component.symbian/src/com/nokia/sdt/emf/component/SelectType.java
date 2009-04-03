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
 * A representation of the model object '<em><b>Select Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.SelectType#getChoice <em>Choice</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SelectType#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SelectType#getIsComponentInstanceOf <em>Is Component Instance Of</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SelectType#getProperty <em>Property</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SelectType#getPropertyExists <em>Property Exists</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getSelectType()
 * @model extendedMetaData="name='select_._type' kind='elementOnly'"
 * @generated
 */
public interface SelectType extends EObject{
	/**
	 * Returns the value of the '<em><b>Choice</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.ChoiceType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Choice</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		This defines a decision in the select element.  If the "value" attribute
	 * 		matches the value obtained in the select element, the choice is matched
	 * 		and its mapping elements instantiated.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Choice</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSelectType_Choice()
	 * @model type="com.nokia.sdt.emf.component.ChoiceType" containment="true"
	 *        extendedMetaData="kind='element' name='choice' namespace='##targetNamespace'"
	 * @generated
	 */
    EList getChoice();

	/**
	 * Returns the value of the '<em><b>Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Attribute</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 					Specifies the attribute value to test.  Results in blank ("") or the value of the attribute to test.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Attribute</em>' attribute.
	 * @see #setAttribute(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSelectType_Attribute()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='attribute'"
	 * @generated
	 */
    String getAttribute();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.SelectType#getAttribute <em>Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute</em>' attribute.
	 * @see #getAttribute()
	 * @generated
	 */
    void setAttribute(String value);

	/**
	 * Returns the value of the '<em><b>Is Component Instance Of</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 					Specifies the property path to test for existence, e.g., whether the
	 * 						property may be set or queried.  This is independent of whether the
	 * 						property is actually set.  The attribute takes a property path (e.g. "flag", "[parent].flag", ...)
	 * 					Results in a 'true' or 'false' value to test. 
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Is Component Instance Of</em>' attribute.
	 * @see #setIsComponentInstanceOf(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSelectType_IsComponentInstanceOf()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='isComponentInstanceOf'"
	 * @generated
	 */
	String getIsComponentInstanceOf();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.SelectType#getIsComponentInstanceOf <em>Is Component Instance Of</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Component Instance Of</em>' attribute.
	 * @see #getIsComponentInstanceOf()
	 * @generated
	 */
	void setIsComponentInstanceOf(String value);

	/**
	 * Returns the value of the '<em><b>Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Property</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 					Specifies the property path to test.  (E.g. "flag", "[parent].flag", ...)
	 * 						Results in an error if the property does not exist, else the string version of the property to test.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Property</em>' attribute.
	 * @see #setProperty(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSelectType_Property()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='property'"
	 * @generated
	 */
    String getProperty();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.SelectType#getProperty <em>Property</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property</em>' attribute.
	 * @see #getProperty()
	 * @generated
	 */
    void setProperty(String value);

	/**
	 * Returns the value of the '<em><b>Property Exists</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 					Specifies the property path to test for existence.  (E.g. "flag", "[parent].flag", ...)
	 * 						Results in a 'true' or 'false' value to test. 
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Property Exists</em>' attribute.
	 * @see #setPropertyExists(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSelectType_PropertyExists()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='propertyExists'"
	 * @generated
	 */
	String getPropertyExists();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.SelectType#getPropertyExists <em>Property Exists</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Exists</em>' attribute.
	 * @see #getPropertyExists()
	 * @generated
	 */
	void setPropertyExists(String value);

} // SelectType
