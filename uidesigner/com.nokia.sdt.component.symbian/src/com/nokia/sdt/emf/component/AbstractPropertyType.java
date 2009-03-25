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
 * A representation of the model object '<em><b>Abstract Property Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A base schema type for further property type declarations. Not used directly in components.
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.AbstractPropertyType#getCategory <em>Category</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.AbstractPropertyType#getDescriptionKey <em>Description Key</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.AbstractPropertyType#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.AbstractPropertyType#getEditorClass <em>Editor Class</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.AbstractPropertyType#getHelpKey <em>Help Key</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.AbstractPropertyType#getName <em>Name</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.AbstractPropertyType#isReadOnly <em>Read Only</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.AbstractPropertyType#isResettable <em>Resettable</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getAbstractPropertyType()
 * @model extendedMetaData="name='abstractPropertyType' kind='empty'"
 * @generated
 */
public interface AbstractPropertyType extends EObject{
	/**
	 * Returns the value of the '<em><b>Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Category</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The category grouping to be used in the property sheet. 
	 * 			 If this value matches the key of a known group then the localized name is looked up within the Symbian component provider. Otherwise it is treated like a
	 * 			 potentially localized string, i.e. if it is %-prefixed a string is looked up in the properties file.
	 * 			 The special value of "hidden" will cause the property to be filtered from the property sheet.
	 * 			
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Category</em>' attribute.
	 * @see #setCategory(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getAbstractPropertyType_Category()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='category'"
	 * @generated
	 */
    String getCategory();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.AbstractPropertyType#getCategory <em>Category</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Category</em>' attribute.
	 * @see #getCategory()
	 * @generated
	 */
    void setCategory(String value);

	/**
	 * Returns the value of the '<em><b>Description Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Description Key</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A property key to descriptive text for the property to be displayed in the status bar
	 * 			when the property is selected in the property sheet.
	 * 			
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Description Key</em>' attribute.
	 * @see #setDescriptionKey(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getAbstractPropertyType_DescriptionKey()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='descriptionKey'"
	 * @generated
	 */
    String getDescriptionKey();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.AbstractPropertyType#getDescriptionKey <em>Description Key</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description Key</em>' attribute.
	 * @see #getDescriptionKey()
	 * @generated
	 */
    void setDescriptionKey(String value);

	/**
	 * Returns the value of the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Display Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The property name to be display in the user interface. Can contain spaces and other characters.
	 * 			This value should be localized.
	 * 			
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Display Name</em>' attribute.
	 * @see #setDisplayName(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getAbstractPropertyType_DisplayName()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='displayName'"
	 * @generated
	 */
    String getDisplayName();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.AbstractPropertyType#getDisplayName <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Name</em>' attribute.
	 * @see #getDisplayName()
	 * @generated
	 */
    void setDisplayName(String value);

	/**
	 * Returns the value of the '<em><b>Editor Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Editor Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The fully qualified name of a class implementing com.nokia.sdt.component.property.IPropertyEditorFactory.
	 * 			This allows Java code to provide a label provider, cell editor, and validator for the property.
	 * 			
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Editor Class</em>' attribute.
	 * @see #setEditorClass(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getAbstractPropertyType_EditorClass()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='editorClass'"
	 * @generated
	 */
	String getEditorClass();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.AbstractPropertyType#getEditorClass <em>Editor Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Editor Class</em>' attribute.
	 * @see #getEditorClass()
	 * @generated
	 */
	void setEditorClass(String value);

	/**
	 * Returns the value of the '<em><b>Help Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Help Key</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A key to more detailed help for the property.
	 * 			
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Help Key</em>' attribute.
	 * @see #setHelpKey(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getAbstractPropertyType_HelpKey()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='helpKey'"
	 * @generated
	 */
    String getHelpKey();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.AbstractPropertyType#getHelpKey <em>Help Key</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Help Key</em>' attribute.
	 * @see #getHelpKey()
	 * @generated
	 */
    void setHelpKey(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The internal name for the property. It must be unique within the component and its base
	 * 			components. Since it may be used as an identifier in scripts it should be a legal JavaScript identifier.
	 * 			
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getAbstractPropertyType_Name()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='name'"
	 * @generated
	 */
    String getName();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.AbstractPropertyType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
    void setName(String value);

	/**
	 * Returns the value of the '<em><b>Read Only</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Read Only</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If true the property will be read-only in the property sheet.
	 * 			
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Read Only</em>' attribute.
	 * @see #isSetReadOnly()
	 * @see #unsetReadOnly()
	 * @see #setReadOnly(boolean)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getAbstractPropertyType_ReadOnly()
	 * @model default="false" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='readOnly'"
	 * @generated
	 */
    boolean isReadOnly();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.AbstractPropertyType#isReadOnly <em>Read Only</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Read Only</em>' attribute.
	 * @see #isSetReadOnly()
	 * @see #unsetReadOnly()
	 * @see #isReadOnly()
	 * @generated
	 */
    void setReadOnly(boolean value);

	/**
	 * Unsets the value of the '{@link com.nokia.sdt.emf.component.AbstractPropertyType#isReadOnly <em>Read Only</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isSetReadOnly()
	 * @see #isReadOnly()
	 * @see #setReadOnly(boolean)
	 * @generated
	 */
    void unsetReadOnly();

	/**
	 * Returns whether the value of the '{@link com.nokia.sdt.emf.component.AbstractPropertyType#isReadOnly <em>Read Only</em>}' attribute is set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Read Only</em>' attribute is set.
	 * @see #unsetReadOnly()
	 * @see #isReadOnly()
	 * @see #setReadOnly(boolean)
	 * @generated
	 */
    boolean isSetReadOnly();

	/**
	 * Returns the value of the '<em><b>Resettable</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If false the reset button will be disabled, inhibiting the user from reverting to the default value
	 * 			
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Resettable</em>' attribute.
	 * @see #isSetResettable()
	 * @see #unsetResettable()
	 * @see #setResettable(boolean)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getAbstractPropertyType_Resettable()
	 * @model default="true" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='resettable'"
	 * @generated
	 */
	boolean isResettable();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.AbstractPropertyType#isResettable <em>Resettable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resettable</em>' attribute.
	 * @see #isSetResettable()
	 * @see #unsetResettable()
	 * @see #isResettable()
	 * @generated
	 */
	void setResettable(boolean value);

	/**
	 * Unsets the value of the '{@link com.nokia.sdt.emf.component.AbstractPropertyType#isResettable <em>Resettable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetResettable()
	 * @see #isResettable()
	 * @see #setResettable(boolean)
	 * @generated
	 */
	void unsetResettable();

	/**
	 * Returns whether the value of the '{@link com.nokia.sdt.emf.component.AbstractPropertyType#isResettable <em>Resettable</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Resettable</em>' attribute is set.
	 * @see #unsetResettable()
	 * @see #isResettable()
	 * @see #setResettable(boolean)
	 * @generated
	 */
	boolean isSetResettable();

} // AbstractPropertyType
