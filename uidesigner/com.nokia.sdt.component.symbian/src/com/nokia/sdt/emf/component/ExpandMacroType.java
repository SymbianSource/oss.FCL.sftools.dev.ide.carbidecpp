/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.FeatureMap;

import java.util.List;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Expand Macro Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.ExpandMacroType#getExpandArgument <em>Expand Argument</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ExpandMacroType#getDontPassArguments <em>Dont Pass Arguments</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ExpandMacroType#getHelp <em>Help</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ExpandMacroType#getName <em>Name</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ExpandMacroType#getPassArguments <em>Pass Arguments</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ExpandMacroType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getExpandMacroType()
 * @model extendedMetaData="name='expandMacro_._type' kind='elementOnly'"
 * @generated
 */
public interface ExpandMacroType extends ConditionalSourceGen{
	/**
	 * Returns the value of the '<em><b>Expand Argument</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.ExpandArgumentType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expand Argument</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Added post 1.1.  
	 * 		Provides multiline text content for an argument.
	 * 		This is semantically identical to setting an attribute of the same
	 * 		name="..." value in the expandMacro element, with any standalone 
	 * 		leading whitespace and standalone trailing whitespace removed
	 * 		(as with &lt;template&gt;).
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Expand Argument</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getExpandMacroType_ExpandArgument()
	 * @model type="com.nokia.sdt.emf.component.ExpandArgumentType" containment="true"
	 *        extendedMetaData="kind='element' name='expandArgument' namespace='##targetNamespace'"
	 * @generated
	 */
	EList getExpandArgument();

	/**
	 * Returns the value of the '<em><b>Dont Pass Arguments</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						This is primarily used when passArguments is not specified.
	 * 						It specifies which arguments not to pass to the invoked macro,
	 * 						which become undefined in the expansion of that macro.
	 * 						This is useful when this macro takes over the work of one or more
	 * 						arguments from the invoked macro. 
	 * 						
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Dont Pass Arguments</em>' attribute.
	 * @see #setDontPassArguments(List)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getExpandMacroType_DontPassArguments()
	 * @model unique="false" dataType="com.nokia.sdt.emf.component.ListOfStrings" many="false"
	 *        extendedMetaData="kind='attribute' name='dontPassArguments'"
	 * @generated
	 */
	List getDontPassArguments();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ExpandMacroType#getDontPassArguments <em>Dont Pass Arguments</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dont Pass Arguments</em>' attribute.
	 * @see #getDontPassArguments()
	 * @generated
	 */
	void setDontPassArguments(List value);

	/**
	 * Returns the value of the '<em><b>Help</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Help/usage text for a documentation generating tool.
	 * 						
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Help</em>' attribute.
	 * @see #setHelp(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getExpandMacroType_Help()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='help'"
	 * @generated
	 */
	String getHelp();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ExpandMacroType#getHelp <em>Help</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Help</em>' attribute.
	 * @see #getHelp()
	 * @generated
	 */
	void setHelp(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						The macro to invoke (matching the id from defineMacro).
	 * 						
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getExpandMacroType_Name()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='name'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ExpandMacroType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Pass Arguments</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						A list of arguments defined in the current calling macro to
	 * 						pass unchanged to the called macro, excluding any arguments
	 * 						that are not defined in the current call.
	 * 						
	 * 						This attribute is only valid in expandMacro called from
	 * 						a defineMacro.  
	 * 						
	 * 						Passing arguments is different from adding attributes
	 * 						argName="$(argName)" because it avoids defining otherwise 
	 * 						undefined arguments.  (A missing optional argument is null,
	 * 						not the empty string.  The '::is-defined' modifier can be
	 * 						used to check this.)
	 * 						
	 * 						Elements in the list of strings are names of arguments, or
	 * 						renames of the form targetArgumentName=hostArgumentName which
	 * 						passes hostArgumentName from the hosting macro with the name
	 * 						targetArgumentName (again, only if the argument is actually
	 * 						defined in the call).
	 * 							
	 * 						If this argument is not specified, all the arguments in the
	 * 						invoked macro are passed (zero or more may have defaults which
	 * 						are overridden in this macro). 
	 * 						
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Pass Arguments</em>' attribute.
	 * @see #setPassArguments(List)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getExpandMacroType_PassArguments()
	 * @model unique="false" dataType="com.nokia.sdt.emf.component.ListOfStrings" many="false"
	 *        extendedMetaData="kind='attribute' name='passArguments'"
	 * @generated
	 */
	List getPassArguments();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ExpandMacroType#getPassArguments <em>Pass Arguments</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pass Arguments</em>' attribute.
	 * @see #getPassArguments()
	 * @generated
	 */
	void setPassArguments(List value);

	/**
	 * Returns the value of the '<em><b>Any Attribute</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Any attribute matching the variables used in the given named macro may appear here.
	 * 						
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Any Attribute</em>' attribute list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getExpandMacroType_AnyAttribute()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='attributeWildcard' wildcards='##any' name=':8' processing='lax'"
	 * @generated
	 */
	FeatureMap getAnyAttribute();

} // ExpandMacroType
