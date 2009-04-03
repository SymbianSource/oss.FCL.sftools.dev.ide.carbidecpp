/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Map Resource Element Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.MapResourceElementType#getInstanceIdentifyingMember <em>Instance Identifying Member</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getMapResourceElementType()
 * @model extendedMetaData="name='mapResourceElement_._type' kind='elementOnly'"
 * @generated
 */
public interface MapResourceElementType extends MappingResourceType{
	/**
	 * Returns the value of the '<em><b>Instance Identifying Member</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Instance Identifying Member</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 					This attribute provides a means to distinguish resource
	 * 					elements that come from different instances.  
	 * 					
	 * 					It tells which resource member to use as a key, so that the 
	 * 					array mapper can match up elements to instances when importing,
	 * 					to allow unknown items to be inserted (or for known items to 
	 * 					be reordered, ideally).
	 * 					
	 * 					If not set, the array is assumed to contain entries whose
	 * 					positions uniquely identify them (as is the case with
	 * 					arrays of integers, references, etc.).
	 * 						
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Instance Identifying Member</em>' attribute.
	 * @see #setInstanceIdentifyingMember(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMapResourceElementType_InstanceIdentifyingMember()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='instanceIdentifyingMember'"
	 * @generated
	 */
	String getInstanceIdentifyingMember();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.MapResourceElementType#getInstanceIdentifyingMember <em>Instance Identifying Member</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Instance Identifying Member</em>' attribute.
	 * @see #getInstanceIdentifyingMember()
	 * @generated
	 */
	void setInstanceIdentifyingMember(String value);

} // MapResourceElementType
