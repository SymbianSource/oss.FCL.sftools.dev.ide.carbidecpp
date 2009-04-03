/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simple Property Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Concrete type used for basic properties.
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.SimplePropertyType#getDefault <em>Default</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SimplePropertyType#getExtendWithEnum <em>Extend With Enum</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SimplePropertyType#getMaxValue <em>Max Value</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SimplePropertyType#getMinValue <em>Min Value</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SimplePropertyType#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getSimplePropertyType()
 * @model extendedMetaData="name='simplePropertyType' kind='empty'"
 * @generated
 */
public interface SimplePropertyType extends AbstractPropertyType{
	/**
	 * Returns the value of the '<em><b>Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A default value for the property. Must be a string convertible to the
	 * 					particular property type.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Default</em>' attribute.
	 * @see #setDefault(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSimplePropertyType_Default()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='default'"
	 * @generated
	 */
	String getDefault();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.SimplePropertyType#getDefault <em>Default</em>}' attribute.
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
	 * Optional extension of the enum with the enums provided by an enimPropertyDeclaration.
	 * 					If the value of the property matches any in an extension enumProperty's 'value' attribute, the 'displayValue' will be shown
	 * 					as the current value in the property sheet.  Additionally, the editor for the value (if not overridden with
	 * 					a custom editorClass) will be a combo that allows typing in the literal value or selecting one of 
	 * 					the extended enums.
	 * 					<p xmlns="http://www.nokia.com/sdt/emf/component">
	 * 					The 'value' of each of the extending enums must match the type being extended, or else the attempt to set the value
	 * 					to that enum will fail.  Note that this means that it is useless to extend booleans (since all extensions
	 * 					must map to 'true' or 'false'). 							
	 * 					</p>
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Extend With Enum</em>' attribute.
	 * @see #setExtendWithEnum(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSimplePropertyType_ExtendWithEnum()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='extendWithEnum'"
	 * @generated
	 */
	String getExtendWithEnum();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.SimplePropertyType#getExtendWithEnum <em>Extend With Enum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extend With Enum</em>' attribute.
	 * @see #getExtendWithEnum()
	 * @generated
	 */
	void setExtendWithEnum(String value);

	/**
	 * Returns the value of the '<em><b>Max Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Optional maximum value constraint. Applies only to integers and floats.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Max Value</em>' attribute.
	 * @see #setMaxValue(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSimplePropertyType_MaxValue()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='maxValue'"
	 * @generated
	 */
	String getMaxValue();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.SimplePropertyType#getMaxValue <em>Max Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Value</em>' attribute.
	 * @see #getMaxValue()
	 * @generated
	 */
	void setMaxValue(String value);

	/**
	 * Returns the value of the '<em><b>Min Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Min Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Optional minimum value constraint. Applies only to integers and floats.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Min Value</em>' attribute.
	 * @see #setMinValue(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSimplePropertyType_MinValue()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='minValue'"
	 * @generated
	 */
	String getMinValue();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.SimplePropertyType#getMinValue <em>Min Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Value</em>' attribute.
	 * @see #getMinValue()
	 * @generated
	 */
	void setMinValue(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The default value is <code>"void"</code>.
	 * The literals are from the enumeration {@link com.nokia.sdt.emf.component.PropertyDataType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Selects the data type of the property.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see com.nokia.sdt.emf.component.PropertyDataType
	 * @see #isSetType()
	 * @see #unsetType()
	 * @see #setType(PropertyDataType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSimplePropertyType_Type()
	 * @model default="void" unique="false" unsettable="true" required="true"
	 *        extendedMetaData="kind='attribute' name='type'"
	 * @generated
	 */
	PropertyDataType getType();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.SimplePropertyType#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see com.nokia.sdt.emf.component.PropertyDataType
	 * @see #isSetType()
	 * @see #unsetType()
	 * @see #getType()
	 * @generated
	 */
	void setType(PropertyDataType value);

	/**
	 * Unsets the value of the '{@link com.nokia.sdt.emf.component.SimplePropertyType#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetType()
	 * @see #getType()
	 * @see #setType(PropertyDataType)
	 * @generated
	 */
	void unsetType();

	/**
	 * Returns whether the value of the '{@link com.nokia.sdt.emf.component.SimplePropertyType#getType <em>Type</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Type</em>' attribute is set.
	 * @see #unsetType()
	 * @see #getType()
	 * @see #setType(PropertyDataType)
	 * @generated
	 */
	boolean isSetType();

} // SimplePropertyType
