/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Compound Property Declaration Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.CompoundPropertyDeclarationType#getAbstractPropertyGroup <em>Abstract Property Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.CompoundPropertyDeclarationType#getAbstractProperty <em>Abstract Property</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.CompoundPropertyDeclarationType#getSourceTypeMapping <em>Source Type Mapping</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.CompoundPropertyDeclarationType#getConverterClass <em>Converter Class</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.CompoundPropertyDeclarationType#getEditableType <em>Editable Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.CompoundPropertyDeclarationType#getEditorClass <em>Editor Class</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.CompoundPropertyDeclarationType#getQualifiedName <em>Qualified Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getCompoundPropertyDeclarationType()
 * @model extendedMetaData="name='compoundPropertyDeclaration_._type' kind='elementOnly'"
 * @generated
 */
public interface CompoundPropertyDeclarationType extends EObject{
	/**
	 * Returns the value of the '<em><b>Abstract Property Group</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Abstract Property Group</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Any property type may be used here, i.e. simple, compound, array, reference.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Abstract Property Group</em>' attribute list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getCompoundPropertyDeclarationType_AbstractPropertyGroup()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='abstractProperty:group' namespace='##targetNamespace'"
	 * @generated
	 */
	FeatureMap getAbstractPropertyGroup();

	/**
	 * Returns the value of the '<em><b>Abstract Property</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.AbstractPropertyType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Abstract Property</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Any property type may be used here, i.e. simple, compound, array, reference.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Abstract Property</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getCompoundPropertyDeclarationType_AbstractProperty()
	 * @model type="com.nokia.sdt.emf.component.AbstractPropertyType" containment="true" transient="true" changeable="false" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='abstractProperty' namespace='##targetNamespace' group='abstractProperty:group'"
	 * @generated
	 */
	EList getAbstractProperty();

	/**
	 * Returns the value of the '<em><b>Source Type Mapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 					This element provides source mapping for the type, for use by
	 * 					map*FromType elements.  Added post 1.2.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Source Type Mapping</em>' containment reference.
	 * @see #setSourceTypeMapping(SourceTypeMappingType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getCompoundPropertyDeclarationType_SourceTypeMapping()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='sourceTypeMapping' namespace='##targetNamespace'"
	 * @generated
	 */
	SourceTypeMappingType getSourceTypeMapping();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.CompoundPropertyDeclarationType#getSourceTypeMapping <em>Source Type Mapping</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Type Mapping</em>' containment reference.
	 * @see #getSourceTypeMapping()
	 * @generated
	 */
	void setSourceTypeMapping(SourceTypeMappingType value);

	/**
	 * Returns the value of the '<em><b>Converter Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Converter Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If specified, this must be the fully qualified name of an implementation of
	 * 				com.nokia.sdt.component.property.ICompoundPropertyValueConverter. It is used to convert back and forth between
	 * 				the editable value and the compound value. The editable value is a single value, display as the parent value in the
	 * 				property sheet and used with cell editors.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Converter Class</em>' attribute.
	 * @see #setConverterClass(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getCompoundPropertyDeclarationType_ConverterClass()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='converterClass'"
	 * @generated
	 */
	String getConverterClass();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.CompoundPropertyDeclarationType#getConverterClass <em>Converter Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Converter Class</em>' attribute.
	 * @see #getConverterClass()
	 * @generated
	 */
	void setConverterClass(String value);

	/**
	 * Returns the value of the '<em><b>Editable Type</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Editable Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The property type of the editable value. In addition to editing each
	 * 				child value, the user may be able to edit the property as a whole. For example, an RGB color value may be
	 * 				editable as a string. This value can be a propertyDataType value such as "integer" or "string", or can be
	 * 				the name of an enumPropertyDeclaration.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Editable Type</em>' attribute.
	 * @see #isSetEditableType()
	 * @see #unsetEditableType()
	 * @see #setEditableType(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getCompoundPropertyDeclarationType_EditableType()
	 * @model default="" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='editableType'"
	 * @generated
	 */
	String getEditableType();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.CompoundPropertyDeclarationType#getEditableType <em>Editable Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Editable Type</em>' attribute.
	 * @see #isSetEditableType()
	 * @see #unsetEditableType()
	 * @see #getEditableType()
	 * @generated
	 */
	void setEditableType(String value);

	/**
	 * Unsets the value of the '{@link com.nokia.sdt.emf.component.CompoundPropertyDeclarationType#getEditableType <em>Editable Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetEditableType()
	 * @see #getEditableType()
	 * @see #setEditableType(String)
	 * @generated
	 */
	void unsetEditableType();

	/**
	 * Returns whether the value of the '{@link com.nokia.sdt.emf.component.CompoundPropertyDeclarationType#getEditableType <em>Editable Type</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Editable Type</em>' attribute is set.
	 * @see #unsetEditableType()
	 * @see #getEditableType()
	 * @see #setEditableType(String)
	 * @generated
	 */
	boolean isSetEditableType();

	/**
	 * Returns the value of the '<em><b>Editor Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The fully qualified name of a class implementing 
	 * 			com.nokia.sdt.component.property.IPropertyEditorFactory.
	 * 			This allows Java code to provide a label provider, cell editor, and validator for the property.
	 * 			Specify a factory class here provides a default for all compound properties of this type. The factory
	 * 			may be overriden by a compound property declaration
	 * 			
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Editor Class</em>' attribute.
	 * @see #setEditorClass(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getCompoundPropertyDeclarationType_EditorClass()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='editorClass'"
	 * @generated
	 */
	String getEditorClass();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.CompoundPropertyDeclarationType#getEditorClass <em>Editor Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Editor Class</em>' attribute.
	 * @see #getEditorClass()
	 * @generated
	 */
	void setEditorClass(String value);

	/**
	 * Returns the value of the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Qualified Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A globally unique name for the property. By convention this is a dotted name, e.g.
	 * 				com.example.MyPropertyType. This identifier is used in compound property declarations.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Qualified Name</em>' attribute.
	 * @see #setQualifiedName(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getCompoundPropertyDeclarationType_QualifiedName()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='qualifiedName'"
	 * @generated
	 */
    String getQualifiedName();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.CompoundPropertyDeclarationType#getQualifiedName <em>Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Qualified Name</em>' attribute.
	 * @see #getQualifiedName()
	 * @generated
	 */
    void setQualifiedName(String value);

} // CompoundPropertyDeclarationType
