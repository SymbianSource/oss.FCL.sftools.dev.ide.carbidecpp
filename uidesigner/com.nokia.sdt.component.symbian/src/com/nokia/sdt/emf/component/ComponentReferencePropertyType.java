/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reference Property Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A concrete property type that maintains a reference to a component
 * 			instance in the same model. The reference is kept by name, and is maintained across renames of the target
 * 			instance. It is cleared automatically if the target instance is removed.
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.ComponentReferencePropertyType#getConstraint <em>Constraint</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ComponentReferencePropertyType#getCreationKeys <em>Creation Keys</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ComponentReferencePropertyType#isPromoteReferenceProperties <em>Promote Reference Properties</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ComponentReferencePropertyType#getScope <em>Scope</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getComponentReferencePropertyType()
 * @model extendedMetaData="name='componentReferencePropertyType' kind='empty'"
 * @generated
 */
public interface ComponentReferencePropertyType extends AbstractPropertyType{
	/**
	 * Returns the value of the '<em><b>Constraint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constraint</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If specified, this must be the fully qualified name of a component type. Values
	 * 					are then constrained to be instances of this type, and the property sheet user interface displays a list of eligible
	 * 					instances.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Constraint</em>' attribute.
	 * @see #setConstraint(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getComponentReferencePropertyType_Constraint()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='constraint'"
	 * @generated
	 */
	String getConstraint();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ComponentReferencePropertyType#getConstraint <em>Constraint</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Constraint</em>' attribute.
	 * @see #getConstraint()
	 * @generated
	 */
	void setConstraint(String value);

	/**
	 * Returns the value of the '<em><b>Creation Keys</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * An optional comma-delimited string containing a list of keys. When
	 * 					present these keys are used to add "create new xxx" items to the dropdown menu for the property, in addition to
	 * 					the "None" and existing instances populated into the menu. A component using this feature must also provide
	 * 					an ISetValueCommandExtender implementation to handle the creation of these items.
	 * 					Each key is used for two purposes:
	 * 					- As a resource key for the dropdown menu item. The resource string should be the full command, e.g. "Create new Foo".
	 * 					- When selected, the key is provided as a parameter to a com.nokia.sdt.component.NewComponentReferenceParameter instance. 
	 * 					This instance is then available from the SetValueCommand passed to ISetValueCommandExtender.getExtendedCommand. 
	 * 					The implementation should retrieve the key, and return a Command object that will create the appropriate new instance 
	 * 					and set the target value to the new instance.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Creation Keys</em>' attribute.
	 * @see #setCreationKeys(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getComponentReferencePropertyType_CreationKeys()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='creationKeys'"
	 * @generated
	 */
	String getCreationKeys();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ComponentReferencePropertyType#getCreationKeys <em>Creation Keys</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Creation Keys</em>' attribute.
	 * @see #getCreationKeys()
	 * @generated
	 */
	void setCreationKeys(String value);

	/**
	 * Returns the value of the '<em><b>Promote Reference Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If specified, the properties of the instance referenced by this property are promoted
	 * 					into this property source, excluding the 'name' property and other properties that already exist in this component.  
	 * 					This property and the referenced component instance's properties will be at the same level.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Promote Reference Properties</em>' attribute.
	 * @see #isSetPromoteReferenceProperties()
	 * @see #unsetPromoteReferenceProperties()
	 * @see #setPromoteReferenceProperties(boolean)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getComponentReferencePropertyType_PromoteReferenceProperties()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='promoteReferenceProperties'"
	 * @generated
	 */
	boolean isPromoteReferenceProperties();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ComponentReferencePropertyType#isPromoteReferenceProperties <em>Promote Reference Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Promote Reference Properties</em>' attribute.
	 * @see #isSetPromoteReferenceProperties()
	 * @see #unsetPromoteReferenceProperties()
	 * @see #isPromoteReferenceProperties()
	 * @generated
	 */
	void setPromoteReferenceProperties(boolean value);

	/**
	 * Unsets the value of the '{@link com.nokia.sdt.emf.component.ComponentReferencePropertyType#isPromoteReferenceProperties <em>Promote Reference Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetPromoteReferenceProperties()
	 * @see #isPromoteReferenceProperties()
	 * @see #setPromoteReferenceProperties(boolean)
	 * @generated
	 */
	void unsetPromoteReferenceProperties();

	/**
	 * Returns whether the value of the '{@link com.nokia.sdt.emf.component.ComponentReferencePropertyType#isPromoteReferenceProperties <em>Promote Reference Properties</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Promote Reference Properties</em>' attribute is set.
	 * @see #unsetPromoteReferenceProperties()
	 * @see #isPromoteReferenceProperties()
	 * @see #setPromoteReferenceProperties(boolean)
	 * @generated
	 */
	boolean isSetPromoteReferenceProperties();

	/**
	 * Returns the value of the '<em><b>Scope</b></em>' attribute.
	 * The default value is <code>"model"</code>.
	 * The literals are from the enumeration {@link com.nokia.sdt.emf.component.ReferenceScopeType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Enumeration defining the scope for permissable values.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Scope</em>' attribute.
	 * @see com.nokia.sdt.emf.component.ReferenceScopeType
	 * @see #isSetScope()
	 * @see #unsetScope()
	 * @see #setScope(ReferenceScopeType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getComponentReferencePropertyType_Scope()
	 * @model default="model" unique="false" unsettable="true" required="true"
	 *        extendedMetaData="kind='attribute' name='scope'"
	 * @generated
	 */
	ReferenceScopeType getScope();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ComponentReferencePropertyType#getScope <em>Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scope</em>' attribute.
	 * @see com.nokia.sdt.emf.component.ReferenceScopeType
	 * @see #isSetScope()
	 * @see #unsetScope()
	 * @see #getScope()
	 * @generated
	 */
	void setScope(ReferenceScopeType value);

	/**
	 * Unsets the value of the '{@link com.nokia.sdt.emf.component.ComponentReferencePropertyType#getScope <em>Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetScope()
	 * @see #getScope()
	 * @see #setScope(ReferenceScopeType)
	 * @generated
	 */
	void unsetScope();

	/**
	 * Returns whether the value of the '{@link com.nokia.sdt.emf.component.ComponentReferencePropertyType#getScope <em>Scope</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Scope</em>' attribute is set.
	 * @see #unsetScope()
	 * @see #getScope()
	 * @see #setScope(ReferenceScopeType)
	 * @generated
	 */
	boolean isSetScope();

} // ComponentReferencePropertyType
