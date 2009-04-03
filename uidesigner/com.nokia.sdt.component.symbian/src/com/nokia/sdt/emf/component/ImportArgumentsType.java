/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;

import org.eclipse.emf.ecore.EObject;

import java.util.List;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Import Arguments Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.ImportArgumentsType#getArguments <em>Arguments</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ImportArgumentsType#getExceptArguments <em>Except Arguments</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ImportArgumentsType#getHelp <em>Help</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ImportArgumentsType#getMacroName <em>Macro Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getImportArgumentsType()
 * @model extendedMetaData="name='importArguments_._type' kind='empty'"
 * @generated
 */
public interface ImportArgumentsType extends EObject {
	/**
	 * Returns the value of the '<em><b>Arguments</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 				The list of argument names to import.  This is mutually exclusive with 'exceptArguments'.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Arguments</em>' attribute.
	 * @see #setArguments(List)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getImportArgumentsType_Arguments()
	 * @model unique="false" dataType="com.nokia.sdt.emf.component.ListOfStrings" required="true" many="false"
	 *        extendedMetaData="kind='attribute' name='arguments'"
	 * @generated
	 */
	List getArguments();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ImportArgumentsType#getArguments <em>Arguments</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Arguments</em>' attribute.
	 * @see #getArguments()
	 * @generated
	 */
	void setArguments(List value);

	/**
	 * Returns the value of the '<em><b>Except Arguments</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 				The list of argument names NOT to import.  All the arguments are imported
	 * 				except these.  This is mutually exclusive with 'arguments'.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Except Arguments</em>' attribute.
	 * @see #setExceptArguments(List)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getImportArgumentsType_ExceptArguments()
	 * @model unique="false" dataType="com.nokia.sdt.emf.component.ListOfStrings" required="true" many="false"
	 *        extendedMetaData="kind='attribute' name='exceptArguments'"
	 * @generated
	 */
	List getExceptArguments();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ImportArgumentsType#getExceptArguments <em>Except Arguments</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Except Arguments</em>' attribute.
	 * @see #getExceptArguments()
	 * @generated
	 */
	void setExceptArguments(List value);

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
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getImportArgumentsType_Help()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='help'"
	 * @generated
	 */
	String getHelp();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ImportArgumentsType#getHelp <em>Help</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Help</em>' attribute.
	 * @see #getHelp()
	 * @generated
	 */
	void setHelp(String value);

	/**
	 * Returns the value of the '<em><b>Macro Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 				The macro whose arguments to import.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Macro Name</em>' attribute.
	 * @see #setMacroName(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getImportArgumentsType_MacroName()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='macroName'"
	 * @generated
	 */
	String getMacroName();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ImportArgumentsType#getMacroName <em>Macro Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Macro Name</em>' attribute.
	 * @see #getMacroName()
	 * @generated
	 */
	void setMacroName(String value);

} // ImportArgumentsType
