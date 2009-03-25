/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Template Group Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.TemplateGroupType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.TemplateGroupType#getDefineLocation <em>Define Location</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.TemplateGroupType#getTemplate <em>Template</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.TemplateGroupType#getInline <em>Inline</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.TemplateGroupType#getUseTemplate <em>Use Template</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.TemplateGroupType#getUseTemplateGroup <em>Use Template Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.TemplateGroupType#getExpandMacro <em>Expand Macro</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.TemplateGroupType#getForm <em>Form</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.TemplateGroupType#getId <em>Id</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.TemplateGroupType#getLocation <em>Location</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.TemplateGroupType#getMode <em>Mode</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.TemplateGroupType#getPhase <em>Phase</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getTemplateGroupType()
 * @model extendedMetaData="name='templateGroup_._type' kind='elementOnly'"
 * @generated
 */
public interface TemplateGroupType extends ConditionalSourceGen {
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
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getTemplateGroupType_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:3'"
	 * @generated
	 */
	FeatureMap getGroup();

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
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getTemplateGroupType_DefineLocation()
	 * @model type="com.nokia.sdt.emf.component.DefineLocationType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='defineLocation' namespace='##targetNamespace' group='#group:3'"
	 * @generated
	 */
	EList getDefineLocation();

	/**
	 * Returns the value of the '<em><b>Template</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.TemplateType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Template</em>' containment reference isn't clear,
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
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getTemplateGroupType_Template()
	 * @model type="com.nokia.sdt.emf.component.TemplateType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='template' namespace='##targetNamespace' group='#group:3'"
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
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getTemplateGroupType_Inline()
	 * @model type="com.nokia.sdt.emf.component.InlineType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='inline' namespace='##targetNamespace' group='#group:3'"
	 * @generated
	 */
	EList getInline();

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
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getTemplateGroupType_UseTemplate()
	 * @model type="com.nokia.sdt.emf.component.UseTemplateType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='useTemplate' namespace='##targetNamespace' group='#group:3'"
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
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getTemplateGroupType_UseTemplateGroup()
	 * @model type="com.nokia.sdt.emf.component.UseTemplateGroupType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='useTemplateGroup' namespace='##targetNamespace' group='#group:3'"
	 * @generated
	 */
	EList getUseTemplateGroup();

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
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getTemplateGroupType_ExpandMacro()
	 * @model type="com.nokia.sdt.emf.component.ExpandMacroType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='expandMacro' namespace='##targetNamespace' group='#group:3'"
	 * @generated
	 */
	EList getExpandMacro();

	/**
	 * Returns the value of the '<em><b>Form</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Form</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Form</em>' attribute.
	 * @see #setForm(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getTemplateGroupType_Form()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='form'"
	 * @generated
	 */
    String getForm();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.TemplateGroupType#getForm <em>Form</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Form</em>' attribute.
	 * @see #getForm()
	 * @generated
	 */
    void setForm(String value);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getTemplateGroupType_Id()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='id'"
	 * @generated
	 */
    String getId();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.TemplateGroupType#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
    void setId(String value);

	/**
	 * Returns the value of the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Location</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Location</em>' attribute.
	 * @see #setLocation(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getTemplateGroupType_Location()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='location'"
	 * @generated
	 */
    String getLocation();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.TemplateGroupType#getLocation <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location</em>' attribute.
	 * @see #getLocation()
	 * @generated
	 */
    void setLocation(String value);

	/**
	 * Returns the value of the '<em><b>Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Mode</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Mode</em>' attribute.
	 * @see #setMode(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getTemplateGroupType_Mode()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='mode'"
	 * @generated
	 */
    String getMode();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.TemplateGroupType#getMode <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mode</em>' attribute.
	 * @see #getMode()
	 * @generated
	 */
    void setMode(String value);

	/**
	 * Returns the value of the '<em><b>Phase</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Phase</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Phase</em>' attribute.
	 * @see #setPhase(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getTemplateGroupType_Phase()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='phase'"
	 * @generated
	 */
    String getPhase();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.TemplateGroupType#getPhase <em>Phase</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Phase</em>' attribute.
	 * @see #getPhase()
	 * @generated
	 */
    void setPhase(String value);

} // TemplateGroupType
