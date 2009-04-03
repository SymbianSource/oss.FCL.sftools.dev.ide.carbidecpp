/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Inline Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.InlineType#getId <em>Id</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.InlineType#getScope <em>Scope</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getInlineType()
 * @model extendedMetaData="name='inline_._type' kind='simple'"
 * @generated
 */
public interface InlineType extends ConditionalSourceGenString{
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Added post 1.1: the identifier for the inline section,
	 * 						which makes it inheritable.  Inline sections are inherited as templates,
	 * 						accessible with "useTemplate" of the same id.
	 * 						If unspecified, a default id is assigned.  Use a blank id to avoid inheriting.
	 * 						
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getInlineType_Id()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='id'"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.InlineType#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Scope</b></em>' attribute.
	 * The default value is <code>"function"</code>.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Scope</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Tells where the inline code appears.  By default, it appears
	 * 						in the primary function into which templates are generated, thus is
	 * 						executed in line with them.  
	 * 						
	 * 						Specifying "file" or "prototype" means the content is intended to modify the 
	 * 						Javascript prototype, thus it is placed at the top level of the file.  
	 * 						${jsObject}.prototype may be used to access the prototype.
	 * 						
	 * 						Note: include() and includeFrom() calls should be placed in the prototype scope,
	 * 						or their contents will not be visible to derived components.
	 * 
	 * 						Added post 1.1: "file" is a synonym for "prototype".
	 * 						
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Scope</em>' attribute.
	 * @see #isSetScope()
	 * @see #unsetScope()
	 * @see #setScope(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getInlineType_Scope()
	 * @model default="function" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='scope'"
	 * @generated
	 */
    String getScope();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.InlineType#getScope <em>Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scope</em>' attribute.
	 * @see #isSetScope()
	 * @see #unsetScope()
	 * @see #getScope()
	 * @generated
	 */
    void setScope(String value);

	/**
	 * Unsets the value of the '{@link com.nokia.sdt.emf.component.InlineType#getScope <em>Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isSetScope()
	 * @see #getScope()
	 * @see #setScope(String)
	 * @generated
	 */
    void unsetScope();

	/**
	 * Returns whether the value of the '{@link com.nokia.sdt.emf.component.InlineType#getScope <em>Scope</em>}' attribute is set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Scope</em>' attribute is set.
	 * @see #unsetScope()
	 * @see #getScope()
	 * @see #setScope(String)
	 * @generated
	 */
    boolean isSetScope();

} // InlineType
