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
 * A representation of the model object '<em><b>Define Macro Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.DefineMacroType#getImportArguments <em>Import Arguments</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DefineMacroType#getMacroArgument <em>Macro Argument</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DefineMacroType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DefineMacroType#getTemplate <em>Template</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DefineMacroType#getInline <em>Inline</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DefineMacroType#getDefineLocation <em>Define Location</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DefineMacroType#getExpandMacro <em>Expand Macro</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DefineMacroType#getHelp <em>Help</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DefineMacroType#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getDefineMacroType()
 * @model extendedMetaData="name='defineMacro_._type' kind='elementOnly'"
 * @generated
 */
public interface DefineMacroType extends EObject{
	/**
	 * Returns the value of the '<em><b>Import Arguments</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.ImportArgumentsType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Import Arguments</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Added post 1.1.  
	 * 		This element specifies that a list of arguments (name, type, defaults,
	 * 		optional flags) will be imported from another macro. 
	 * 		If this element is omitted, then all the arguments from the macro
	 * 		are imported.  
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Import Arguments</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDefineMacroType_ImportArguments()
	 * @model type="com.nokia.sdt.emf.component.ImportArgumentsType" containment="true"
	 *        extendedMetaData="kind='element' name='importArguments' namespace='##targetNamespace'"
	 * @generated
	 */
	EList getImportArguments();

	/**
	 * Returns the value of the '<em><b>Macro Argument</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.MacroArgumentType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Macro Argument</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Added post 1.1.
	 * 		Defines an argument for use with the macro.
	 * 		The default value may be specified in the 'default' attribute or in the text of the element.
	 * 		The text supercedes the attribute.  
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Macro Argument</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDefineMacroType_MacroArgument()
	 * @model type="com.nokia.sdt.emf.component.MacroArgumentType" containment="true"
	 *        extendedMetaData="kind='element' name='macroArgument' namespace='##targetNamespace'"
	 * @generated
	 */
	EList getMacroArgument();

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
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDefineMacroType_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:2'"
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
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDefineMacroType_Template()
	 * @model type="com.nokia.sdt.emf.component.TemplateType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='template' namespace='##targetNamespace' group='#group:2'"
	 * @generated
	 */
	EList getTemplate();

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
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDefineMacroType_Inline()
	 * @model type="com.nokia.sdt.emf.component.InlineType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='inline' namespace='##targetNamespace' group='#group:2'"
	 * @generated
	 */
	EList getInline();

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
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDefineMacroType_DefineLocation()
	 * @model type="com.nokia.sdt.emf.component.DefineLocationType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='defineLocation' namespace='##targetNamespace' group='#group:2'"
	 * @generated
	 */
	EList getDefineLocation();

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
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDefineMacroType_ExpandMacro()
	 * @model type="com.nokia.sdt.emf.component.ExpandMacroType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='expandMacro' namespace='##targetNamespace' group='#group:2'"
	 * @generated
	 */
	EList getExpandMacro();

	/**
	 * Returns the value of the '<em><b>Help</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 				Help/usage text for a documentation generating tool.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Help</em>' attribute.
	 * @see #setHelp(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDefineMacroType_Help()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='help'"
	 * @generated
	 */
	String getHelp();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DefineMacroType#getHelp <em>Help</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Help</em>' attribute.
	 * @see #getHelp()
	 * @generated
	 */
	void setHelp(String value);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 				Unique id for the macro within a component.  
	 * 				Ids may be shared for different
	 * 				forms.  Derived components inherit macros automatically
	 * 				but may redefine a macro with the same id.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDefineMacroType_Id()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='id'"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DefineMacroType#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

} // DefineMacroType
