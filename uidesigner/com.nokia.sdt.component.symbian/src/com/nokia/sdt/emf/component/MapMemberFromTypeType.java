/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Map Member From Type Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.MapMemberFromTypeType#getMember <em>Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MapMemberFromTypeType#getProperty <em>Property</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MapMemberFromTypeType#isSuppressDefault <em>Suppress Default</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MapMemberFromTypeType#getTypeId <em>Type Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getMapMemberFromTypeType()
 * @model extendedMetaData="name='mapMemberFromType_._type' kind='empty'"
 * @generated
 */
public interface MapMemberFromTypeType extends TwoWayMappingType {
	/**
	 * Returns the value of the '<em><b>Member</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 				The struct member.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Member</em>' attribute.
	 * @see #setMember(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMapMemberFromTypeType_Member()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='member'"
	 * @generated
	 */
	String getMember();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.MapMemberFromTypeType#getMember <em>Member</em>}' attribute.
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
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						The property path providing the value.
	 * 						
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Property</em>' attribute.
	 * @see #setProperty(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMapMemberFromTypeType_Property()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='property'"
	 * @generated
	 */
	String getProperty();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.MapMemberFromTypeType#getProperty <em>Property</em>}' attribute.
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
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMapMemberFromTypeType_SuppressDefault()
	 * @model default="true" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='suppressDefault'"
	 * @generated
	 */
	boolean isSuppressDefault();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.MapMemberFromTypeType#isSuppressDefault <em>Suppress Default</em>}' attribute.
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
	 * Unsets the value of the '{@link com.nokia.sdt.emf.component.MapMemberFromTypeType#isSuppressDefault <em>Suppress Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetSuppressDefault()
	 * @see #isSuppressDefault()
	 * @see #setSuppressDefault(boolean)
	 * @generated
	 */
	void unsetSuppressDefault();

	/**
	 * Returns whether the value of the '{@link com.nokia.sdt.emf.component.MapMemberFromTypeType#isSuppressDefault <em>Suppress Default</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Suppress Default</em>' attribute is set.
	 * @see #unsetSuppressDefault()
	 * @see #isSuppressDefault()
	 * @see #setSuppressDefault(boolean)
	 * @generated
	 */
	boolean isSetSuppressDefault();

	/**
	 * Returns the value of the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 					This attribute tells which of a set of type mappings to use
	 * 					from the given type (as named in the id="..." attributes on
	 * 					the mapXXXType children of sourceTypeMapping).   
	 * 						
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type Id</em>' attribute.
	 * @see #setTypeId(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMapMemberFromTypeType_TypeId()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='typeId'"
	 * @generated
	 */
	String getTypeId();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.MapMemberFromTypeType#getTypeId <em>Type Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Id</em>' attribute.
	 * @see #getTypeId()
	 * @generated
	 */
	void setTypeId(String value);

} // MapMemberFromTypeType
