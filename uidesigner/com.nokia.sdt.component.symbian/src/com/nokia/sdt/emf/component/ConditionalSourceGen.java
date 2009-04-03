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
 * A representation of the model object '<em><b>Conditional Source Gen</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.ConditionalSourceGen#getForms <em>Forms</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ConditionalSourceGen#getIfEvents <em>If Events</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ConditionalSourceGen#getIfExpr <em>If Expr</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getConditionalSourceGen()
 * @model abstract="true"
 *        extendedMetaData="name='ConditionalSourceGen' kind='empty'"
 * @generated
 */
public interface ConditionalSourceGen extends EObject {
	/**
	 * Returns the value of the '<em><b>Forms</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 			Added post 1.1 to inline.
	 * 
	 * 			A list of tokens used to select a particular kind of source for a parent.
	 * 			The namespace is determined implicitly by what a parent requests.
	 * 			
	 * 			For instance, a parent may have special sourcegen, and pass
	 * 			the form "SpecialCase" to its children.  Elements specifying
	 * 			form="SpecialCase" will be selected.  Other parents will not see
	 * 			such elements if they do not specify that form.
	 * 			
	 * 			If the form does not match, the element is not invoked.
	 * 			
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Forms</em>' attribute.
	 * @see #setForms(List)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getConditionalSourceGen_Forms()
	 * @model unique="false" dataType="com.nokia.sdt.emf.component.ListOfStrings" many="false"
	 *        extendedMetaData="kind='attribute' name='forms'"
	 * @generated
	 */
	List getForms();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ConditionalSourceGen#getForms <em>Forms</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Forms</em>' attribute.
	 * @see #getForms()
	 * @generated
	 */
	void setForms(List value);

	/**
	 * Returns the value of the '<em><b>If Events</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 			Added post 1.1 to inline.
	 * 			If defined, a list of events, any of which must be bound for the
	 * 			element to be invoked.
	 * 			
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>If Events</em>' attribute.
	 * @see #setIfEvents(List)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getConditionalSourceGen_IfEvents()
	 * @model unique="false" dataType="com.nokia.sdt.emf.component.ListOfStrings" many="false"
	 *        extendedMetaData="kind='attribute' name='ifEvents'"
	 * @generated
	 */
	List getIfEvents();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ConditionalSourceGen#getIfEvents <em>If Events</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>If Events</em>' attribute.
	 * @see #getIfEvents()
	 * @generated
	 */
	void setIfEvents(List value);

	/**
	 * Returns the value of the '<em><b>If Expr</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 			Added post 1.1.
	 * 			If defined, a Javascript expression which must evaluate to
	 * 			true (or non-zero) for the element to be invoked.
	 * 			
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>If Expr</em>' attribute.
	 * @see #setIfExpr(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getConditionalSourceGen_IfExpr()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='ifExpr'"
	 * @generated
	 */
	String getIfExpr();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ConditionalSourceGen#getIfExpr <em>If Expr</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>If Expr</em>' attribute.
	 * @see #getIfExpr()
	 * @generated
	 */
	void setIfExpr(String value);

} // ConditionalSourceGen
