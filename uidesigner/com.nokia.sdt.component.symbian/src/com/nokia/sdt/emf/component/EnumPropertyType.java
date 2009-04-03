/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Enum Property Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Concrete type used for enumerated value properties.
 * 		
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.EnumPropertyType#getDefault <em>Default</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.EnumPropertyType#getExtendWithEnum <em>Extend With Enum</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.EnumPropertyType#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getEnumPropertyType()
 * @model extendedMetaData="name='enumPropertyType' kind='empty'"
 * @generated
 */
public interface EnumPropertyType extends AbstractPropertyType{
	/**
	 * Returns the value of the '<em><b>Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The default enumerated value, in case it needs to be different from 
	 * 					the default declared in the enumeratedPropertyDeclaration.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Default</em>' attribute.
	 * @see #setDefault(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getEnumPropertyType_Default()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='default'"
	 * @generated
	 */
	String getDefault();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.EnumPropertyType#getDefault <em>Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default</em>' attribute.
	 * @see #getDefault()
	 * @generated
	 */
	void setDefault(String value);

	/**
	 * Returns the value of the '<em><b>Extend With Enum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Optional extension of the simple values with the enums provided by an enimPropertyDeclaration.
	 * 					If the value of the property matches any in an enumProperty's 'value' attribute, the 'displayValue' will be shown
	 * 					as the current value in the property sheet.  Additionally, the editor for the value (if not overridden with
	 * 					a custom editorClass) will be a combo that allows typing in the literal value or selecting one of the
	 * 					union of base and extended enums.
	 * 					<p xmlns="http://www.nokia.com/sdt/emf/component"> 
	 * 					The extending enum may provide 'value' elements which are different from the base enum (even integers, say),
	 * 					if appropriate.  These values are exposed to script, source mapping, and source gen, so ensure that all
	 * 					those clients are prepared to deal with the extra values.
	 * 					</p>
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Extend With Enum</em>' attribute.
	 * @see #setExtendWithEnum(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getEnumPropertyType_ExtendWithEnum()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='extendWithEnum'"
	 * @generated
	 */
	String getExtendWithEnum();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.EnumPropertyType#getExtendWithEnum <em>Extend With Enum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extend With Enum</em>' attribute.
	 * @see #getExtendWithEnum()
	 * @generated
	 */
	void setExtendWithEnum(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The fully qualifed name of the enumerated property type. See
	 * 					enumPropertyDeclaration.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getEnumPropertyType_Type()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='type'"
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.EnumPropertyType#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

} // EnumPropertyType
