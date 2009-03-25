/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Map Identifier Member Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.MapIdentifierMemberType#getMember <em>Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MapIdentifierMemberType#getProperty <em>Property</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MapIdentifierMemberType#isSuppressDefault <em>Suppress Default</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getMapIdentifierMemberType()
 * @model extendedMetaData="name='mapIdentifierMember_._type' kind='empty'"
 * @generated
 */
public interface MapIdentifierMemberType extends MappingIdentifierType{
	/**
	 * Returns the value of the '<em><b>Member</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Member</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 				The struct member.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Member</em>' attribute.
	 * @see #setMember(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMapIdentifierMemberType_Member()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='member'"
	 * @generated
	 */
    String getMember();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.MapIdentifierMemberType#getMember <em>Member</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Member</em>' attribute.
	 * @see #getMember()
	 * @generated
	 */
    void setMember(String value);

	/**
	 * Returns the value of the '<em><b>Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Property</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						The property path providing the value.
	 * 						
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Property</em>' attribute.
	 * @see #setProperty(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMapIdentifierMemberType_Property()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='property'"
	 * @generated
	 */
    String getProperty();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.MapIdentifierMemberType#getProperty <em>Property</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property</em>' attribute.
	 * @see #getProperty()
	 * @generated
	 */
    void setProperty(String value);

	/**
	 * Returns the value of the '<em><b>Suppress Default</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Suppress Default</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 				If true, do not emit the member initializer if the property matches the default
	 * 				specified in the STRUCT.  If may be useful to set this to false if the
	 * 				default changes between SDK releases (to avoid branching components).
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Suppress Default</em>' attribute.
	 * @see #isSetSuppressDefault()
	 * @see #unsetSuppressDefault()
	 * @see #setSuppressDefault(boolean)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMapIdentifierMemberType_SuppressDefault()
	 * @model default="true" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='suppressDefault'"
	 * @generated
	 */
	boolean isSuppressDefault();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.MapIdentifierMemberType#isSuppressDefault <em>Suppress Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Suppress Default</em>' attribute.
	 * @see #isSetSuppressDefault()
	 * @see #unsetSuppressDefault()
	 * @see #isSuppressDefault()
	 * @generated
	 */
	void setSuppressDefault(boolean value);

	/**
	 * Unsets the value of the '{@link com.nokia.sdt.emf.component.MapIdentifierMemberType#isSuppressDefault <em>Suppress Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetSuppressDefault()
	 * @see #isSuppressDefault()
	 * @see #setSuppressDefault(boolean)
	 * @generated
	 */
	void unsetSuppressDefault();

	/**
	 * Returns whether the value of the '{@link com.nokia.sdt.emf.component.MapIdentifierMemberType#isSuppressDefault <em>Suppress Default</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Suppress Default</em>' attribute is set.
	 * @see #unsetSuppressDefault()
	 * @see #isSuppressDefault()
	 * @see #setSuppressDefault(boolean)
	 * @generated
	 */
	boolean isSetSuppressDefault();

} // MapIdentifierMemberType
