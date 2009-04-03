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
 * A representation of the model object '<em><b>Source Gen Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.SourceGenType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SourceGenType#getDefineLocation <em>Define Location</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SourceGenType#getTemplate <em>Template</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SourceGenType#getTemplateGroup <em>Template Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SourceGenType#getUseTemplate <em>Use Template</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SourceGenType#getUseTemplateGroup <em>Use Template Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SourceGenType#getInline <em>Inline</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SourceGenType#getScript <em>Script</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SourceGenType#getDefineMacro <em>Define Macro</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SourceGenType#getExpandMacro <em>Expand Macro</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SourceGenType#isDebug <em>Debug</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SourceGenType#getForms <em>Forms</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getSourceGenType()
 * @model extendedMetaData="name='sourceGen_._type' kind='elementOnly'"
 * @generated
 */
public interface SourceGenType extends EObject{
	/**
	 * Returns the value of the '<em><b>Define Location</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.DefineLocationType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Define Location</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		This element defines a location in source, such as a function in the main 
	 * 		file or a region of text in a class declaration.  
	 * 		This is a slight misnomer as this element does not by its presence realize
	 * 		the location.  Only when a contribution references
	 * 		location id (or post 1.1: when realize="true" is used)
	 * 		will the location be added to source.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Define Location</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSourceGenType_DefineLocation()
	 * @model type="com.nokia.sdt.emf.component.DefineLocationType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='defineLocation' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
    EList getDefineLocation();

	/**
	 * Returns the value of the '<em><b>Group</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Group</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Group</em>' attribute list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSourceGenType_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:0'"
	 * @generated
	 */
    FeatureMap getGroup();

	/**
	 * Returns the value of the '<em><b>Template</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.TemplateType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Template</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		This defines a contribution.  The text element is template-expanded 
	 * 		with ${ ... } expression escapes and may contain Javascript in &lt;% ... %&gt; sections.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Template</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSourceGenType_Template()
	 * @model type="com.nokia.sdt.emf.component.TemplateType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='template' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
    EList getTemplate();

	/**
	 * Returns the value of the '<em><b>Template Group</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.TemplateGroupType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Template Group</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		A logical grouping of templates and locations.  Attributes
	 * 		present on the group are automatically applied to children.
	 * 		Groups may not be nested.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Template Group</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSourceGenType_TemplateGroup()
	 * @model type="com.nokia.sdt.emf.component.TemplateGroupType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='templateGroup' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
    EList getTemplateGroup();

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
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSourceGenType_UseTemplate()
	 * @model type="com.nokia.sdt.emf.component.UseTemplateType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='useTemplate' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
    EList getUseTemplate();

	/**
	 * Returns the value of the '<em><b>Use Template Group</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.UseTemplateGroupType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Use Template Group</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		In a derived component, selects a template group from the base by id.
	 * 		If no subelements are specified, all the named templates (those with ids)
	 * 		are inherited.  No templates without ids are inherited.
	 * 		
	 * 		If subelements are specified, these describe the specific templates
	 * 		to inherit from the group.  The id "*" may be used to bring in all
	 * 		named templates.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Use Template Group</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSourceGenType_UseTemplateGroup()
	 * @model type="com.nokia.sdt.emf.component.UseTemplateGroupType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='useTemplateGroup' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
    EList getUseTemplateGroup();

	/**
	 * Returns the value of the '<em><b>Inline</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.InlineType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Inline</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Provide Javascript in-line with templates and other sourceGen elements.
	 * 		Be sure to supply a "forms" attribute, otherwise the code may execute multiple times
	 * 		during the source generation contribution-gathering phase.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Inline</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSourceGenType_Inline()
	 * @model type="com.nokia.sdt.emf.component.InlineType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='inline' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
    EList getInline();

	/**
	 * Returns the value of the '<em><b>Script</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.ScriptType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Script</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Define a reference to an interface implemented in Javascript.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Script</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSourceGenType_Script()
	 * @model type="com.nokia.sdt.emf.component.ScriptType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='script' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
    EList getScript();

	/**
	 * Returns the value of the '<em><b>Define Macro</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.DefineMacroType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Define Macro</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Added post 1.1.  
	 * 		This element defines a macro consisting of a set of templates 
	 * 		and inlines which may be variable-substituted.
	 * 			
	 * 		If a given defineMacro imports multiple arguments
	 * 		of the same name, then they must be explicitly redefined,
	 * 		to resolve any ambiguities about the default value.  
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Define Macro</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSourceGenType_DefineMacro()
	 * @model type="com.nokia.sdt.emf.component.DefineMacroType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='defineMacro' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getDefineMacro();

	/**
	 * Returns the value of the '<em><b>Expand Macro</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.ExpandMacroType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expand Macro</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		<p xmlns="http://www.nokia.com/sdt/emf/component">
	 * 		Added post 1.1.
	 * 		Expands a given macro into the sourceGen of the caller.  
	 * 		This has the same effect as inserting the same templates and inlines 
	 * 		from the macro's definition at the point of call.  
	 * 		Variable references from those templates and inlines
	 * 		are substituted with the values provided in attributes 
	 * 		(e.g. variableName="value") or expandArgument child elements.  
	 * 		The latter may be preferred for cases where code is substituted,
	 * 		so the formatting may be retained.
	 * 		</p>
	 * 		<p xmlns="http://www.nokia.com/sdt/emf/component">
	 * 		As a special case, variable references may have modifiers to
	 * 		modify the formatting of a variable when it is expanded.  
	 * 		Modifiers are appended
	 * 		to the variable name as in: $(varName::modifier).  
	 * 		</p>
	 * 		<p xmlns="http://www.nokia.com/sdt/emf/component">
	 * 		Currently supported modifiers are all for tweaking canonical
	 * 		function argument lists, which match the format of 
	 * 		declaration, with spaces, argument names, default values, etc:
	 * 		</p>
	 * 		<ul xmlns="http://www.nokia.com/sdt/emf/component">
	 * 			<li>as-function-declaration-args: identity
	 * 			</li>
	 * 			<li>as-function-definition-args: removes default argument values
	 * 			</li>
	 * 			<li>as-function-location-args: removes argument names, default arguments, and spaces
	 * 			</li>
	 * 		</ul>
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Expand Macro</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSourceGenType_ExpandMacro()
	 * @model type="com.nokia.sdt.emf.component.ExpandMacroType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='expandMacro' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getExpandMacro();

	/**
	 * Returns the value of the '<em><b>Debug</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Debug</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Debug</em>' attribute.
	 * @see #isSetDebug()
	 * @see #unsetDebug()
	 * @see #setDebug(boolean)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSourceGenType_Debug()
	 * @model default="false" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='debug'"
	 * @generated
	 */
    boolean isDebug();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.SourceGenType#isDebug <em>Debug</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Debug</em>' attribute.
	 * @see #isSetDebug()
	 * @see #unsetDebug()
	 * @see #isDebug()
	 * @generated
	 */
    void setDebug(boolean value);

	/**
	 * Unsets the value of the '{@link com.nokia.sdt.emf.component.SourceGenType#isDebug <em>Debug</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isSetDebug()
	 * @see #isDebug()
	 * @see #setDebug(boolean)
	 * @generated
	 */
    void unsetDebug();

	/**
	 * Returns whether the value of the '{@link com.nokia.sdt.emf.component.SourceGenType#isDebug <em>Debug</em>}' attribute is set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Debug</em>' attribute is set.
	 * @see #unsetDebug()
	 * @see #isDebug()
	 * @see #setDebug(boolean)
	 * @generated
	 */
    boolean isSetDebug();

	/**
	 * Returns the value of the '<em><b>Forms</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Forms</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Forms</em>' attribute.
	 * @see #setForms(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSourceGenType_Forms()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='forms'"
	 * @generated
	 */
    String getForms();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.SourceGenType#getForms <em>Forms</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Forms</em>' attribute.
	 * @see #getForms()
	 * @generated
	 */
    void setForms(String value);

} // SourceGenType
